package com.maddox.il2.objects.vehicles.aeronautics;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.ai.ground.TgtFlak;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.vehicles.artillery.AAA;
import com.maddox.rts.Message;
import com.maddox.rts.Time;
import java.io.PrintStream;

public class Balloon extends ActorMesh
  implements MsgCollisionRequestListener, MsgCollisionListener, MsgExplosionListener, MsgShotListener, Prey, TgtFlak, AAA
{
  private AeroanchoredGeneric anchor = null;
  private static final int ST_STAY = 0;
  private static final int ST_GOUP = 1;
  private static final int ST_BURN = 2;
  private static final int ST_DEAD = 3;
  private int st = 0;

  private Point3d curPos = new Point3d();
  private double height_stay;
  private double height_top;
  private double speedOfGoUp;
  private long disappearTime;
  private float balloonCenterOffset = 0.0F;

  private static Orient o = new Orient();

  public void msgCollisionRequest(Actor paramActor, boolean[] paramArrayOfBoolean)
  {
    if ((this.st == 3) || (this.anchor == null))
      paramArrayOfBoolean[0] = false;
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    if ((this.st != 3) && (this.st != 2) && (this.anchor != null))
      this.anchor.balloonCollision(paramActor);
  }

  public void msgShot(Shot paramShot)
  {
    paramShot.bodyMaterial = 3;

    if ((this.st == 2) || (this.st == 3)) {
      return;
    }

    if (paramShot.power <= 0.0F) {
      return;
    }

    if (paramShot.powerType == 1)
    {
      if (this.anchor != null) {
        this.anchor.balloonShot(paramShot.initiator);
      }
      return;
    }

    if (paramShot.v.length() < 40.0D) {
      return;
    }

    if (this.anchor != null)
      this.anchor.balloonShot(paramShot.initiator);
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    if ((this.st == 2) || (this.st == 3)) {
      return;
    }

    float f1 = 0.01F;
    float f2 = 0.09F;

    if (Explosion.killable(this, paramExplosion.receivedTNT_1meter(this), f1, f2, 0.0F))
    {
      if (this.anchor != null)
        this.anchor.balloonExplosion(paramExplosion.initiator);
    }
  }

  public int HitbyMask()
  {
    return -1;
  }

  public int chooseBulletType(BulletProperties[] paramArrayOfBulletProperties)
  {
    if ((this.st == 2) || (this.st == 3)) {
      return -1;
    }

    if (paramArrayOfBulletProperties.length == 1) {
      return 0;
    }

    if (paramArrayOfBulletProperties.length <= 0) {
      return -1;
    }

    if (paramArrayOfBulletProperties[0].power <= 0.0F)
    {
      return 1;
    }

    if (paramArrayOfBulletProperties[0].powerType == 1)
    {
      return 0;
    }
    if (paramArrayOfBulletProperties[1].powerType == 1)
    {
      return 1;
    }

    if (paramArrayOfBulletProperties[0].cumulativePower > 0.0F)
    {
      return 1;
    }

    if (paramArrayOfBulletProperties[0].powerType == 2)
    {
      return 1;
    }

    return 0;
  }

  public int chooseShotpoint(BulletProperties paramBulletProperties) {
    if ((this.st == 2) || (this.st == 3)) {
      return -1;
    }
    return 0;
  }

  public boolean getShotpointOffset(int paramInt, Point3d paramPoint3d) {
    if ((this.st == 2) || (this.st == 3)) {
      return false;
    }

    if (paramInt != 0) {
      return false;
    }

    if (paramPoint3d != null) {
      paramPoint3d.set(0.0D, 0.0D, 0.0D);
    }
    return true;
  }

  void somebodyKilled(int paramInt)
  {
    if ((this.st == 2) || (this.st == 3)) {
      return;
    }

    if (paramInt == 98) {
      this.st = 2;

      Loc localLoc = new Loc();
      localLoc.set(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
      localLoc.getPoint().jdField_z_of_type_Double += this.balloonCenterOffset;

      Explosions.HydrogenBalloonExplosion(localLoc, null);

      this.disappearTime = (Time.current() + 800L);
      if (interpGet("move") == null) {
        interpPut(new Move(), "move", Time.current(), null);
      }
      return;
    }

    if (this.st == 0) {
      this.st = 1;
      if (interpGet("move") == null) {
        interpPut(new Move(), "move", Time.current(), null);
      }
      return;
    }
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  public Balloon(AeroanchoredGeneric paramAeroanchoredGeneric, float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    super(paramAeroanchoredGeneric.prop.meshBName);

    this.anchor = paramAeroanchoredGeneric;

    this.anchor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this.curPos);

    this.height_stay = (Engine.land().HQ(this.curPos.x, this.curPos.y) + paramFloat1);
    this.height_top = paramFloat2;

    if (paramBoolean) {
      this.st = 1;
      this.curPos.jdField_z_of_type_Double = this.height_top;
    } else {
      this.st = 0;
      this.curPos.jdField_z_of_type_Double = this.height_stay;
    }

    o.setYPR(this.anchor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsOrient().getYaw(), 0.0F, 0.0F);

    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(this.curPos, o);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();

    setArmy(this.anchor.getArmy());

    collide(true);
    drawing(true);

    this.speedOfGoUp = (5.0D * Time.tickLenFs());

    this.balloonCenterOffset = 0.0F;
    int i = mesh().hookFind("corner0");
    int j = mesh().hookFind("corner1");
    if ((i != -1) && (j != -1)) {
      Matrix4d localMatrix4d = new Matrix4d();

      mesh().hookMatrix(i, localMatrix4d);
      this.balloonCenterOffset = (float)localMatrix4d.m23;

      mesh().hookMatrix(j, localMatrix4d);
      this.balloonCenterOffset += (float)localMatrix4d.m23;

      this.balloonCenterOffset *= 0.5F;
    }
  }

  class Move extends Interpolate
  {
    Move()
    {
    }

    public boolean tick()
    {
      if (Balloon.this.st == 1) {
        Balloon.this.curPos.jdField_z_of_type_Double += Balloon.this.speedOfGoUp;
        if (Balloon.this.curPos.jdField_z_of_type_Double >= Balloon.this.height_top) {
          Balloon.this.curPos.jdField_z_of_type_Double = Balloon.this.height_top;
          return false;
        }
        Balloon.this.pos.setAbs(Balloon.this.curPos);
        return true;
      }

      if (Balloon.this.st == 2) {
        if (Time.current() < Balloon.this.disappearTime) {
          return true;
        }

        Balloon.this.anchor.balloonDisappeared();
        Balloon.access$502(Balloon.this, null);
        Balloon.access$002(Balloon.this, 3);
        Balloon.this.collide(false);
        Balloon.this.drawing(false);
        Balloon.this.postDestroy();
        return false;
      }

      if (Balloon.this.st == 0) {
        System.out.println("***balloon: unexpected stay");
        return true;
      }

      System.out.println("***balloon: unexpected dead");
      return true;
    }
  }
}