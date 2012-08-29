package com.maddox.il2.objects.humans;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.ai.ground.UnitInterface;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorMeshDraw;
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
import com.maddox.rts.Message;
import com.maddox.rts.Time;

public class Soldier extends ActorMesh
  implements MsgCollisionRequestListener, MsgCollisionListener, MsgExplosionListener, MsgShotListener, Prey
{
  private static final int FPS = 30;
  private static final int RUN_START_FRAME = 0;
  private static final int RUN_LAST_FRAME = 22;
  private static final int RUN_N_FRAMES = 23;
  private static final int RUN_CYCLE_TIME = 733;
  private static final int FALL_START_FRAME = 22;
  private static final int FALL_LAST_FRAME = 54;
  private static final int FALL_N_FRAMES = 33;
  private static final int FALL_CYCLE_TIME = 1066;
  private static final int LIE_START_FRAME = 54;
  private static final int LIE_LAST_FRAME = 74;
  private static final int LIE_N_FRAMES = 21;
  private static final int LIE_CYCLE_TIME = 666;
  private static final int LIEDEAD_START_FRAME = 75;
  private static final int LIEDEAD_N_FRAMES = 4;
  private static final float RUN_SPEED = 6.545455F;
  private Vector3d speed;
  private static final int ST_FLY = 0;
  private static final int ST_RUN = 1;
  private static final int ST_FALL = 2;
  private static final int ST_LIE = 3;
  private static final int ST_LIEDEAD = 4;
  private int st = 0;

  private int dying = 0;
  static final int DYING_NONE = 0;
  static final int DYING_DEAD = 1;
  private int idxOfDeadPose;
  private long animStartTime;
  private long disappearTime;
  private int nRunCycles;
  private static Mesh preload1 = null;
  private static Mesh preload2 = null;

  private static Point3d p = new Point3d();
  private static Orient o = new Orient();
  private static Vector3f n = new Vector3f();

  public static void resetGame()
  {
    preload1 = Soldier.preload2 = null;
  }

  public static void PRELOAD() {
    preload1 = new Mesh(GetMeshName(1));
    preload2 = new Mesh(GetMeshName(2));
  }

  public void msgCollisionRequest(Actor paramActor, boolean[] paramArrayOfBoolean)
  {
    if (paramActor == getOwner()) {
      paramArrayOfBoolean[0] = false;
    }
    if ((paramActor instanceof Soldier)) {
      paramArrayOfBoolean[0] = false;
    }
    if (this.dying != 0)
      paramArrayOfBoolean[0] = false;
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    if (this.dying != 0) {
      return;
    }

    Point3d localPoint3d1 = p; this.pos.getAbs(p);
    Point3d localPoint3d2 = paramActor.pos.getAbsPoint();
    Vector3d localVector3d = new Vector3d();
    localVector3d.set(localPoint3d1.x - localPoint3d2.x, localPoint3d1.y - localPoint3d2.y, 0.0D);
    if (localVector3d.length() < 0.001D) {
      f1 = World.Rnd().nextFloat(0.0F, 359.98999F);
      localVector3d.set(Geom.sinDeg(f1), Geom.cosDeg(f1), 0.0D);
    }
    localVector3d.normalize();
    float f1 = 0.2F;
    localVector3d.add(World.Rnd().nextFloat(-f1, f1), World.Rnd().nextFloat(-f1, f1), World.Rnd().nextFloat(-f1, f1));

    localVector3d.normalize();

    float f2 = 13.090909F * Time.tickLenFs();
    localVector3d.scale(f2);

    localPoint3d1.add(localVector3d);
    this.pos.setAbs(localPoint3d1);

    if (this.st == 1) {
      this.st = 2;
      this.animStartTime = Time.current();
    }

    if ((this.st == 3) && (this.dying == 0) && ((paramActor instanceof UnitInterface)) && (paramActor.getSpeed(null) > 0.5D))
    {
      Die(paramActor);
    }
  }

  public void msgShot(Shot paramShot)
  {
    paramShot.bodyMaterial = 3;

    if (this.dying != 0) {
      return;
    }

    if (paramShot.power <= 0.0F) {
      return;
    }

    if (paramShot.powerType == 1)
    {
      Die(paramShot.initiator);
      return;
    }

    if (paramShot.v.length() < 20.0D) {
      return;
    }

    Die(paramShot.initiator);
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    if (this.dying != 0) {
      return;
    }

    float f1 = 0.005F;
    float f2 = 0.1F;

    if (Explosion.killable(this, paramExplosion.receivedTNT_1meter(this), f1, f2, 0.0F))
    {
      Die(paramExplosion.initiator);
    }
  }

  private void Die(Actor paramActor)
  {
    if (this.dying != 0) {
      return;
    }

    World.onActorDied(this, paramActor);

    this.dying = 1;
  }

  public void destroy()
  {
    super.destroy();
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  void setAnimFrame(double paramDouble)
  {
    int i;
    int j;
    int k;
    double d;
    float f;
    switch (this.st) {
    case 0:
    case 1:
      i = 0;
      j = 22;
      k = 733;
      d = paramDouble - this.animStartTime;
      d %= k;
      if (d < 0.0D) {
        d += k;
      }
      f = (float)(d / k);
      break;
    case 2:
      i = 22;
      j = 54;
      k = 1066;
      d = paramDouble - this.animStartTime;
      if (d <= 0.0D)
        f = 0.0F;
      else if (d >= k)
        f = 1.0F;
      else {
        f = (float)(d / k);
      }
      break;
    case 3:
      i = 54;
      j = 74;
      k = 666;
      d = paramDouble - this.animStartTime;

      if (d <= 0.0D)
        f = 0.0F;
      else if (d >= k)
        f = 1.0F;
      else {
        f = (float)(d / k);
      }
      break;
    default:
      i = j = 75 + this.idxOfDeadPose;
      f = 0.0F;
    }

    mesh().setFrameFromRange(i, j, f);
  }

  public int HitbyMask()
  {
    return -25;
  }

  public int chooseBulletType(BulletProperties[] paramArrayOfBulletProperties)
  {
    if (this.dying != 0) {
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
    if (this.dying != 0) {
      return -1;
    }
    return 0;
  }

  public boolean getShotpointOffset(int paramInt, Point3d paramPoint3d) {
    if (this.dying != 0) {
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

  private static String GetMeshName(int paramInt)
  {
    int i = paramInt == 2 ? 1 : 0;
    int j = World.cur().camouflage == 1 ? 1 : 0;
    return "3do/humans/soldiers/" + (i != 0 ? "Germany" : "Russia") + (j != 0 ? "Winter" : "Summer") + "/mono.sim";
  }

  public Soldier(Actor paramActor, int paramInt, Loc paramLoc)
  {
    super(GetMeshName(paramInt));

    setOwner(paramActor);

    setArmy(paramInt);

    Point3d localPoint3d = new Point3d();
    Orient localOrient = new Orient();
    paramLoc.get(localPoint3d, localOrient);
    Vector3d localVector3d = new Vector3d();
    localVector3d.set(1.0D, 0.0D, 0.0D);
    localOrient.transform(localVector3d);

    this.speed = new Vector3d();
    this.speed.set(localVector3d);
    if (this.speed.length() < 0.009999999776482582D) {
      this.speed.set(1.0D, 0.0D, 0.0D);
    }
    this.speed.normalize();
    if (Math.abs(this.speed.z) > 0.9D) {
      this.speed.set(1.0D, 0.0D, 0.0D);
      this.speed.normalize();
    }

    localOrient.setAT0(this.speed);
    localOrient.set(localOrient.azimut(), 0.0F, 0.0F);
    this.pos.setAbs(localPoint3d, localOrient);
    this.pos.reset();

    this.speed.scale(6.545454502105713D);

    this.st = 0;
    this.animStartTime = (Time.tick() + World.Rnd().nextInt(0, 2300));

    this.dying = 0;

    collide(true);
    this.draw = new SoldDraw(null);
    drawing(true);

    if (!interpEnd("move"))
      interpPut(new Move(), "move", Time.current(), null);
  }

  class Move extends Interpolate
  {
    Move()
    {
    }

    public boolean tick()
    {
      if (((Soldier.this.st == 3) || (Soldier.this.st == 4)) && 
        (Time.current() >= Soldier.this.disappearTime)) {
        Soldier.this.postDestroy();
        return false;
      }

      if (Soldier.this.dying != 0)
      {
        switch (Soldier.this.st) {
        case 1:
          Soldier.access$002(Soldier.this, 2);
          Soldier.access$302(Soldier.this, Time.current());
          break;
        case 3:
          Soldier.access$002(Soldier.this, 4);
          Soldier.access$402(Soldier.this, World.Rnd().nextInt(0, 3));
        }

        Soldier.this.setAnimFrame(Time.tickNext());
      }

      long l = Time.tickNext() - Soldier.this.animStartTime;

      switch (Soldier.this.st) {
      case 0:
        Soldier.this.pos.getAbs(Soldier.p);
        Soldier.p.scaleAdd(Time.tickLenFs(), Soldier.this.speed, Soldier.p);
        Soldier.this.speed.z -= Time.tickLenFs() * World.g();
        Engine.land(); float f = Landscape.HQ((float)Soldier.p.x, (float)Soldier.p.y);
        if (Soldier.p.z <= f) {
          Soldier.this.speed.z = 0.0D;
          Soldier.this.speed.normalize();
          Soldier.this.speed.scale(6.545454502105713D);
          Soldier.p.z = f;
          Soldier.access$002(Soldier.this, 1);
          Soldier.access$702(Soldier.this, World.Rnd().nextInt(9, 17));
        }

        Soldier.this.pos.setAbs(Soldier.p);
        break;
      case 1:
        Soldier.this.pos.getAbs(Soldier.p);
        Soldier.p.scaleAdd(Time.tickLenFs(), Soldier.this.speed, Soldier.p);
        Soldier.p.z = Engine.land().HQ(Soldier.p.x, Soldier.p.y);
        Soldier.this.pos.setAbs(Soldier.p);

        if ((l / 733L < Soldier.this.nRunCycles) && (!World.land().isWater(Soldier.p.x, Soldier.p.y)))
          break;
        Soldier.access$002(Soldier.this, 2);
        Soldier.access$302(Soldier.this, Time.current()); break;
      case 2:
        Soldier.this.pos.getAbs(Soldier.p);
        Soldier.p.scaleAdd(Time.tickLenFs(), Soldier.this.speed, Soldier.p);
        Soldier.p.z = Engine.land().HQ(Soldier.p.x, Soldier.p.y);
        if (World.land().isWater(Soldier.p.x, Soldier.p.y)) {
          Soldier.p.z -= 0.5D;
        }
        Soldier.this.pos.setAbs(Soldier.p);

        if (l < 1066L) break;
        Soldier.access$002(Soldier.this, 3);
        Soldier.access$302(Soldier.this, Time.current());

        Soldier.access$102(Soldier.this, Time.tickNext() + 1000 * World.Rnd().nextInt(25, 35)); break;
      case 3:
      case 4:
        Soldier.this.pos.getAbs(Soldier.p);
        Soldier.p.z = Engine.land().HQ(Soldier.p.x, Soldier.p.y);
        if (World.land().isWater(Soldier.p.x, Soldier.p.y)) {
          Soldier.p.z -= 3.0D;
        }
        Soldier.this.pos.setAbs(Soldier.p);
      }

      Soldier.this.setAnimFrame(Time.tickNext());

      return true;
    }
  }

  private class SoldDraw extends ActorMeshDraw
  {
    private final Soldier this$0;

    private SoldDraw()
    {
      this.this$0 = this$1;
    }

    public int preRender(Actor paramActor) {
      this.this$0.setAnimFrame(Time.current());
      return super.preRender(paramActor);
    }

    SoldDraw(Soldier.1 arg2)
    {
      this();
    }
  }
}