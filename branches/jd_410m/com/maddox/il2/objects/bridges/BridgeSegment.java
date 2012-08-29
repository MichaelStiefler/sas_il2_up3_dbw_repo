package com.maddox.il2.objects.bridges;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector2d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.ChiefGround;
import com.maddox.il2.ai.ground.TgtShip;
import com.maddox.il2.ai.ground.UnitInterface;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.Statics;
import com.maddox.rts.Message;
import java.io.PrintStream;

public class BridgeSegment extends ActorMesh
  implements MsgCollisionRequestListener, MsgCollisionListener, MsgExplosionListener, MsgShotListener
{
  private int ID;
  private int brID;
  private int dirOct;
  private Vector2d dir2d;
  private float[] life = new float[2];
  private float maxLife;
  private float ignoreTNT = 0.01F;

  public boolean isStaticPos() {
    return true;
  }

  public int Code() {
    return (this.brID << 16) + this.ID;
  }

  public boolean IsDamaged() {
    return (this.life[0] <= 0.0F) || (this.life[1] <= 0.0F);
  }

  private boolean IsDead() {
    return (this.life[0] <= 0.0F) && (this.life[1] <= 0.0F);
  }

  boolean IsDead(int paramInt) {
    return this.life[paramInt] <= 0.0F;
  }

  private static String NameByIdx(int paramInt1, int paramInt2)
  {
    return " Bridge" + paramInt1 + "Seg" + paramInt2;
  }

  public static BridgeSegment getByIdx(int paramInt1, int paramInt2) {
    return (BridgeSegment)(BridgeSegment)Actor.getByName(NameByIdx(paramInt1, paramInt2));
  }

  public static boolean isEncodedSegmentDamaged(int paramInt) {
    return getByIdx(paramInt >> 16, paramInt & 0x7FFF).IsDamaged();
  }

  private double getNearestDistToSegment(Point3d paramPoint3d)
  {
    Point3d localPoint3d1 = new Point3d();
    Point3d localPoint3d2 = new Point3d();
    Point3d localPoint3d3 = new Point3d();
    Point3d localPoint3d4 = new Point3d();
    ((LongBridge)getOwner()).ComputeSegmentKeyPoints(this.ID, localPoint3d1, localPoint3d2, localPoint3d3, localPoint3d4);

    Vector3d localVector3d1 = new Vector3d();
    localVector3d1.sub(paramPoint3d, localPoint3d2);
    Vector3d localVector3d2 = new Vector3d();
    localVector3d2.sub(localPoint3d4, localPoint3d2);

    double d1 = localVector3d1.dot(localVector3d2);
    if (d1 <= 0.0D) {
      return localPoint3d2.distance(paramPoint3d);
    }
    double d2 = localVector3d2.lengthSquared();
    if (d1 >= d2) {
      return localPoint3d4.distance(paramPoint3d);
    }
    d1 = localVector3d1.lengthSquared() - d1 * d1 / d2;
    if (d1 <= 0.0D) {
      return 0.0D;
    }
    return Math.sqrt(d1);
  }

  public void msgCollisionRequest(Actor paramActor, boolean[] paramArrayOfBoolean)
  {
    if ((paramActor instanceof UnitInterface)) {
      Actor localActor = paramActor.getOwner();
      if ((localActor != null) && ((localActor instanceof ChiefGround)))
        paramArrayOfBoolean[0] = false;
    }
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    if (!(paramActor instanceof TgtShip)) {
      return;
    }

    if (IsDead()) {
      return;
    }

    damagedByTNT(paramActor.pos.getAbsPoint(), this.maxLife + 1.0F, paramActor);
  }

  private void damagedByTNT(Point3d paramPoint3d, float paramFloat, Actor paramActor)
  {
    if (paramFloat <= this.ignoreTNT) {
      return;
    }

    float f = World.Rnd().nextFloat();
    f *= f;
    paramFloat = f * paramFloat * 2.0F + (paramFloat * 0.8F - this.ignoreTNT / 2.0F);

    Point3d localPoint3d = this.pos.getAbsPoint();
    int i = this.dir2d.x * (paramPoint3d.x - localPoint3d.x) + this.dir2d.y * (paramPoint3d.y - localPoint3d.y) < 0.0D ? 0 : 1;

    if (this.life[i] <= 0.0F) {
      return;
    }

    if (((LongBridge)getOwner()).isNetMirror()) {
      LongBridge localLongBridge = (LongBridge)getOwner();
      localLongBridge.sendLifeChanged(this.brID, this.ID, i, this.life[i] - paramFloat, paramActor, true);
      return;
    }

    LifeChanged(i, this.life[i] - paramFloat, paramActor, true);
  }

  public void msgShot(Shot paramShot)
  {
    paramShot.bodyMaterial = ((LongBridge)getOwner()).bodyMaterial;

    if (IsDead()) {
      return;
    }

    if (paramShot.power <= 0.0F) {
      return;
    }

    if (paramShot.powerType == 1) {
      return;
    }

    damagedByTNT(paramShot.p, paramShot.powerToTNT(), paramShot.initiator);
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    if (IsDead()) {
      return;
    }

    float f = (float)getNearestDistToSegment(paramExplosion.p);
    f -= ((LongBridge)getOwner()).getWidth();
    if (f <= 0.0F) f = 0.0F;

    damagedByTNT(paramExplosion.p, paramExplosion.receivedTNT_1meter(f), paramExplosion.initiator);
  }

  void ForcePartState(int paramInt, boolean paramBoolean)
  {
    this.life[paramInt] = (paramBoolean ? this.maxLife : 0.0F);

    LongBridge localLongBridge = (LongBridge)getOwner();
    localLongBridge.SetSegmentDamageState(false, this, this.ID, this.life[0], this.life[1], null);
  }

  void ForcePartDestroing(int paramInt, Actor paramActor)
  {
    if (this.life[paramInt] <= 0.0F) {
      return;
    }

    LifeChanged(paramInt, 0.0F, paramActor, false);
  }

  public void netForcePartDestroing(int paramInt, Actor paramActor)
  {
    if (this.life[paramInt] <= 0.0F) {
      return;
    }

    LifeChanged(paramInt, 0.0F, paramActor, Mission.isServer());
  }

  void netLifeChanged(int paramInt, float paramFloat, Actor paramActor, boolean paramBoolean) {
    LifeChanged(paramInt, paramFloat, paramActor, paramBoolean);
  }
  private void LifeChanged(int paramInt, float paramFloat, Actor paramActor, boolean paramBoolean) {
    if (paramFloat <= 0.0F) {
      paramFloat = 0.0F;
    }

    if (paramInt < 0)
    {
      float tmp23_22 = paramFloat; this.life[1] = tmp23_22; this.life[0] = tmp23_22;

      LongBridge localLongBridge1 = (LongBridge)getOwner();

      localLongBridge1.SetSegmentDamageState(false, this, this.ID, this.life[0], this.life[1], paramActor);
    } else {
      if ((paramFloat <= 0.0F) && (paramBoolean) && 
        (!World.cur().statics.onBridgeDied(this.brID, this.ID, paramInt, paramActor)))
        return;
      boolean bool = IsDead(paramInt);
      this.life[paramInt] = paramFloat;
      if (IsDead(paramInt) != bool)
      {
        LongBridge localLongBridge2 = (LongBridge)getOwner();

        if (IsDead(paramInt)) {
          localLongBridge2.ShowSegmentExplosion(this, this.ID, paramInt);
        }

        localLongBridge2.SetSegmentDamageState(true, this, this.ID, this.life[0], this.life[1], paramActor);
      }
    }
  }

  public void destroy()
  {
    super.destroy();
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  public BridgeSegment(LongBridge paramLongBridge, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, Point3d paramPoint3d, int paramInt3)
  {
    setOwner(paramLongBridge);

    if (paramFloat1 <= 0.0F) {
      System.out.println("*** Internal error in BridgeSegment");
      float f = (1.0F / 1.0F);
    }

    this.brID = paramInt1;
    this.ID = paramInt2;

    setName(NameByIdx(this.brID, this.ID));

    setArmy(0);

    this.pos.setAbs(paramPoint3d);
    this.pos.reset();

    this.dirOct = paramInt3;
    switch (this.dirOct) { case 0:
      this.dir2d = new Vector2d(1.0D, 0.0D); break;
    case 1:
      this.dir2d = new Vector2d(1.0D, 1.0D); break;
    case 2:
      this.dir2d = new Vector2d(0.0D, 1.0D); break;
    case 3:
      this.dir2d = new Vector2d(-1.0D, 1.0D); break;
    case 4:
      this.dir2d = new Vector2d(-1.0D, 0.0D); break;
    case 5:
      this.dir2d = new Vector2d(-1.0D, -1.0D); break;
    case 6:
      this.dir2d = new Vector2d(0.0D, -1.0D); break;
    case 7:
      this.dir2d = new Vector2d(1.0D, -1.0D); break;
    default:
      throw new RuntimeException("Bad bridge's direction");
    }

    this.ignoreTNT = (paramFloat2 <= 0.0F ? 0.0F : paramFloat2);
    this.maxLife = paramFloat1;
    LifeChanged(-1, this.maxLife, null, false);
  }
}