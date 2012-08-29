package com.maddox.il2.objects.vehicles.tanks;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector2d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Aimer;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.AnglesForkExtended;
import com.maddox.il2.ai.AnglesRange;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.TableFunctions;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Aim;
import com.maddox.il2.ai.ground.ChiefGround;
import com.maddox.il2.ai.ground.HunterInterface;
import com.maddox.il2.ai.ground.Moving;
import com.maddox.il2.ai.ground.Obstacle;
import com.maddox.il2.ai.ground.Predator;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.ai.ground.StaticObstacle;
import com.maddox.il2.ai.ground.TgtTank;
import com.maddox.il2.ai.ground.UnitData;
import com.maddox.il2.ai.ground.UnitInPackedForm;
import com.maddox.il2.ai.ground.UnitInterface;
import com.maddox.il2.ai.ground.UnitMove;
import com.maddox.il2.ai.ground.UnitSpawn;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.bridges.LongBridge;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.humans.Soldier;
import com.maddox.il2.objects.weapons.CannonMidrangeGeneric;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Finger;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;
import com.maddox.util.TableFunction2;
import java.io.IOException;
import java.io.PrintStream;

public abstract class TankGeneric extends ActorHMesh
  implements MsgCollisionRequestListener, MsgCollisionListener, MsgExplosionListener, MsgShotListener, Predator, Obstacle, UnitInterface, HunterInterface
{
  private static float[] Thicknesses = null;
  private static float[] Energies = null;
  private static float[][] NumShots_Thickness_Energy = (float[][])null;

  private TankProperties prop = null;
  private int codeName;
  private float heightAboveLandSurface;
  private float heightAboveLandSurface2;
  public UnitData udata = new UnitData();

  private Moving mov = new Moving();
  protected Eff3DActor dust;
  protected SoundFX engineSFX = null;
  protected int engineSTimer = 9999999;
  protected int ticksIn8secs = (int)(8.0F / Time.tickConstLenFs());
  protected Gun gun;
  private Aim aime;
  private float headYaw;
  private float gunPitch;
  private int collisionStage = 0;
  static final int COLLIS_NO_COLLISION = 0;
  static final int COLLIS_JUST_COLLIDED = 1;
  static final int COLLIS_MOVING_FROM_COLLISION = 2;
  private Vector2d collisVector = new Vector2d();
  private Actor collidee;
  private StaticObstacle obs = new StaticObstacle();

  private int dying = 0;
  static final int DYING_NONE = 0;
  static final int DYING_SMOKE = 1;
  static final int DYING_DEAD = 2;
  private long dyingDelay = 0L;

  private int codeOfUnderlyingBridgeSegment = -1;

  private static TankProperties constr_arg1 = null;
  private static Actor constr_arg2 = null;

  private static Point3d p = new Point3d();
  private static Point3d p1 = new Point3d();
  private static Orient o = new Orient();
  private static Vector3f n = new Vector3f();
  private static Vector3d tmpv = new Vector3d();

  private NetMsgFiltered outCommand = new NetMsgFiltered();

  public int getCRC(int paramInt)
  {
    paramInt = super.getCRC(paramInt);
    paramInt = Finger.incInt(paramInt, this.heightAboveLandSurface);
    paramInt = Finger.incInt(paramInt, this.headYaw);
    paramInt = Finger.incInt(paramInt, this.gunPitch);
    paramInt = Finger.incInt(paramInt, this.collisionStage);
    paramInt = Finger.incInt(paramInt, this.dying);
    paramInt = Finger.incInt(paramInt, this.codeOfUnderlyingBridgeSegment);
    if (this.mov != null) {
      paramInt = Finger.incInt(paramInt, this.mov.rotatingInPlace);
      paramInt = Finger.incInt(paramInt, this.mov.srcPos.x);
      paramInt = Finger.incInt(paramInt, this.mov.srcPos.y);
      paramInt = Finger.incInt(paramInt, this.mov.srcPos.z);
      if (this.mov.dstPos != null) {
        paramInt = Finger.incInt(paramInt, this.mov.dstPos.x);
        paramInt = Finger.incInt(paramInt, this.mov.dstPos.y);
        paramInt = Finger.incInt(paramInt, this.mov.dstPos.z);
      }
    }
    if (this.aime != null) {
      paramInt = Finger.incInt(paramInt, this.aime.timeTot);
      paramInt = Finger.incInt(paramInt, this.aime.timeCur);
    }
    return paramInt;
  }

  public static final double Rnd(double paramDouble1, double paramDouble2)
  {
    return World.Rnd().nextDouble(paramDouble1, paramDouble2);
  }
  public static final float Rnd(float paramFloat1, float paramFloat2) {
    return World.Rnd().nextFloat(paramFloat1, paramFloat2);
  }
  private boolean RndB(float paramFloat) {
    return World.Rnd().nextFloat(0.0F, 1.0F) < paramFloat;
  }

  public static final float KmHourToMSec(float paramFloat) {
    return paramFloat / 3.6F;
  }
  private static final float TicksToSecs(long paramLong) {
    if (paramLong < 0L) paramLong = 0L;
    return (float)paramLong * Time.tickLenFs();
  }
  private static final long SecsToTicks(float paramFloat) {
    long l = ()(0.5D + paramFloat / Time.tickLenFs());
    return l < 1L ? 1L : l;
  }
  public static final Vector2d Rotate(Vector2d paramVector2d, float paramFloat) {
    float f1 = Geom.sinDeg(paramFloat);
    float f2 = Geom.cosDeg(paramFloat);
    return new Vector2d(f2 * paramVector2d.x - f1 * paramVector2d.y, f1 * paramVector2d.x + f2 * paramVector2d.y);
  }

  protected final boolean Head360()
  {
    return this.prop.HEAD_YAW_RANGE.fullcircle();
  }

  protected final boolean StayWhenFire()
  {
    return this.prop.STAY_WHEN_FIRE;
  }

  public void msgCollisionRequest(Actor paramActor, boolean[] paramArrayOfBoolean)
  {
    if ((paramActor instanceof BridgeSegment))
      paramArrayOfBoolean[0] = false;
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    if (this.dying != 0) {
      return;
    }

    if ((paramActor instanceof Soldier)) {
      return;
    }

    if (isNetMirror()) {
      return;
    }

    if (this.collisionStage != 0) {
      return;
    }

    if (this.aime.isInFiringMode()) {
      return;
    }

    this.mov.switchToAsk();

    this.collisionStage = 1;
    this.collidee = paramActor;

    Point3d localPoint3d1 = this.pos.getAbsPoint();
    Point3d localPoint3d2 = paramActor.pos.getAbsPoint();
    this.collisVector.set(localPoint3d1.x - localPoint3d2.x, localPoint3d1.y - localPoint3d2.y);
    if (this.collisVector.length() >= 1.0E-006D) {
      this.collisVector.normalize();
    } else {
      float f = Rnd(0.0F, 359.98999F);
      this.collisVector.set(Geom.sinDeg(f), Geom.cosDeg(f));
    }

    ((ChiefGround)getOwner()).CollisionOccured(this, paramActor);
  }

  public void msgShot(Shot paramShot)
  {
    paramShot.bodyMaterial = 2;

    if (this.dying != 0) {
      return;
    }

    if ((isNetMirror()) && (paramShot.isMirage())) {
      return;
    }

    if (paramShot.power <= 0.0F) {
      return;
    }

    if (paramShot.powerType == 1) {
      if (RndB(0.0769231F)) {
        return;
      }

      Die(paramShot.initiator, false);
      return;
    }

    float f1 = Shot.panzerThickness(this.pos.getAbsOrient(), paramShot.v, paramShot.chunkName.equalsIgnoreCase("Head"), this.prop.PANZER_BODY_FRONT, this.prop.PANZER_BODY_SIDE, this.prop.PANZER_BODY_BACK, this.prop.PANZER_BODY_TOP, this.prop.PANZER_HEAD, this.prop.PANZER_HEAD_TOP);

    f1 *= Rnd(0.93F, 1.07F);

    float f2 = this.prop.fnShotPanzer.Value(paramShot.power, f1);

    if ((f2 < 1000.0F) && ((f2 <= 1.0F) || (RndB(1.0F / f2))))
      Die(paramShot.initiator, false);
  }

  public static boolean splintersKill(Explosion paramExplosion, TableFunction2 paramTableFunction2, float paramFloat1, float paramFloat2, ActorMesh paramActorMesh, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10)
  {
    if (paramExplosion.power <= 0.0F) {
      return false;
    }

    paramActorMesh.pos.getAbs(p);
    float[] arrayOfFloat = new float[2];
    paramExplosion.computeSplintersHit(p, paramActorMesh.mesh().collisionR(), paramFloat3, arrayOfFloat);

    arrayOfFloat[0] *= (paramFloat1 * 0.85F + (1.0F - paramFloat1) * 1.15F);
    int i = (int)arrayOfFloat[0];
    if (i <= 0) {
      return false;
    }

    Vector3d localVector3d = new Vector3d(p.x - paramExplosion.p.x, p.y - paramExplosion.p.y, p.z - paramExplosion.p.z);

    double d = localVector3d.length();
    if (d < 0.001000000047497451D)
      localVector3d.set(1.0D, 0.0D, 0.0D);
    else {
      localVector3d.scale(1.0D / d);
    }

    float f1 = Shot.panzerThickness(paramActorMesh.pos.getAbsOrient(), localVector3d, false, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10);

    float f2 = Shot.panzerThickness(paramActorMesh.pos.getAbsOrient(), localVector3d, true, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10);

    int j = (int)(i * paramFloat4 + 0.5F);
    int k = i - j;
    if (k < 0) k = 0;

    float f3 = 0.015F * arrayOfFloat[1] * arrayOfFloat[1] * 0.5F;

    float f4 = paramTableFunction2.Value(f3, f1);
    float f5 = paramTableFunction2.Value(f3, f2);

    if (((k > 0) && (f4 <= 1.0F)) || ((j > 0) && (f5 <= 1.0F)))
    {
      return true;
    }

    float f6 = 0.0F;
    float f7;
    if (f4 < 1000.0F) {
      f7 = 1.0F / f4;
      while (k-- > 0) {
        f6 += (1.0F - f6) * f7;
      }
    }

    if (f5 < 1000.0F) {
      f7 = 1.0F / f5;
      while (j-- > 0) {
        f6 += (1.0F - f6) * f7;
      }
    }

    return (f6 > 0.001F) && (f6 >= paramFloat2);
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    if (this.dying != 0) {
      return;
    }

    if ((isNetMirror()) && (paramExplosion.isMirage())) {
      return;
    }

    if (paramExplosion.power <= 0.0F) {
      return;
    }

    if (paramExplosion.powerType == 1) {
      if (splintersKill(paramExplosion, this.prop.fnShotPanzer, Rnd(0.0F, 1.0F), Rnd(0.0F, 1.0F), this, 0.7F, 0.25F, this.prop.PANZER_BODY_FRONT, this.prop.PANZER_BODY_SIDE, this.prop.PANZER_BODY_BACK, this.prop.PANZER_BODY_TOP, this.prop.PANZER_HEAD, this.prop.PANZER_HEAD_TOP))
      {
        Die(paramExplosion.initiator, false);
      }
      return;
    }

    if ((paramExplosion.powerType == 2) && (paramExplosion.chunkName != null)) {
      Die(paramExplosion.initiator, false);
      return;
    }
    float f1;
    if (paramExplosion.chunkName != null)
      f1 = 0.5F * paramExplosion.power;
    else {
      f1 = paramExplosion.receivedTNTpower(this);
    }
    f1 *= Rnd(0.95F, 1.05F);

    float f2 = this.prop.fnExplodePanzer.Value(f1, this.prop.PANZER_TNT_TYPE);

    if ((f2 < 1000.0F) && ((f2 <= 1.0F) || (RndB(1.0F / f2))))
      Die(paramExplosion.initiator, true);
  }

  private void neverDust()
  {
    if (this.dust != null) {
      this.dust._finish();
      this.dust = null;
    }
  }

  private void RunSmoke(float paramFloat1, float paramFloat2) {
    boolean bool = RndB(paramFloat1);
    String str = bool ? "SmokeHead" : "SmokeEngine";
    Explosions.runByName("_TankSmoke_", this, str, "", Rnd(paramFloat2, paramFloat2 * 1.6F));
  }

  private void ShowExplode() {
    Explosions.runByName(this.prop.explodeName, this, "", "", -1.0F);
  }

  private void MakeCrush() {
    this.dying = 2;

    Point3d localPoint3d = this.pos.getAbsPoint();
    long l = ()(localPoint3d.x % 2.1D * 221.0D + localPoint3d.y % 3.1D * 211.0D * 211.0D);
    RangeRandom localRangeRandom = new RangeRandom(l);

    float[] arrayOfFloat1 = new float[3];
    float[] arrayOfFloat2 = new float[3];
    float tmp76_75 = (arrayOfFloat1[2] = 0.0F); arrayOfFloat1[1] = tmp76_75; arrayOfFloat1[0] = tmp76_75;
    float tmp91_90 = (arrayOfFloat2[2] = 0.0F); arrayOfFloat2[1] = tmp91_90; arrayOfFloat2[0] = tmp91_90;
    arrayOfFloat2[0] = (this.headYaw + localRangeRandom.nextFloat(-45.0F, 45.0F));
    arrayOfFloat2[1] = localRangeRandom.nextFloat(-3.0F, 0.0F);
    arrayOfFloat2[2] = localRangeRandom.nextFloat(-9.0F, 9.0F);
    arrayOfFloat1[2] = (-localRangeRandom.nextFloat(0.0F, 0.1F));
    hierMesh().chunkSetLocate("Head", arrayOfFloat1, arrayOfFloat2);
    float tmp175_174 = (arrayOfFloat1[2] = 0.0F); arrayOfFloat1[1] = tmp175_174; arrayOfFloat1[0] = tmp175_174;
    float tmp190_189 = (arrayOfFloat2[2] = 0.0F); arrayOfFloat2[1] = tmp190_189; arrayOfFloat2[0] = tmp190_189;
    arrayOfFloat2[0] = (-(this.prop.GUN_MIN_PITCH - localRangeRandom.nextFloat(6.0F, 10.0F)));
    arrayOfFloat2[1] = ((localRangeRandom.nextBoolean() ? 1.0F : -1.0F) * localRangeRandom.nextFloat(14.0F, 25.0F));
    hierMesh().chunkSetLocate("Gun", arrayOfFloat1, arrayOfFloat2);
    float tmp268_267 = (arrayOfFloat1[2] = 0.0F); arrayOfFloat1[1] = tmp268_267; arrayOfFloat1[0] = tmp268_267;
    float tmp283_282 = (arrayOfFloat2[2] = 0.0F); arrayOfFloat2[1] = tmp283_282; arrayOfFloat2[0] = tmp283_282;
    arrayOfFloat2[1] = ((localRangeRandom.nextBoolean() ? 1.0F : -1.0F) * localRangeRandom.nextFloat(1.0F, 5.0F));
    arrayOfFloat2[2] = ((localRangeRandom.nextBoolean() ? 1.0F : -1.0F) * localRangeRandom.nextFloat(7.0F, 13.0F));
    hierMesh().chunkSetLocate("Body", arrayOfFloat1, arrayOfFloat2);

    this.engineSFX = null;
    this.engineSTimer = 99999999;
    breakSounds();

    neverDust();

    if (this.prop.meshName2 == null)
    {
      mesh().makeAllMaterialsDarker(0.22F, 0.35F);
      this.heightAboveLandSurface2 = this.heightAboveLandSurface;
      localPoint3d.z -= localRangeRandom.nextFloat(0.3F, 0.6F);
    } else {
      setMesh(this.prop.meshName2);

      this.heightAboveLandSurface2 = 0.0F;

      int i = mesh().hookFind("Ground_Level");
      if (i != -1) {
        Matrix4d localMatrix4d = new Matrix4d();
        mesh().hookMatrix(i, localMatrix4d);
        this.heightAboveLandSurface2 = (float)(-localMatrix4d.m23);
      }

      localPoint3d.z += this.heightAboveLandSurface2 - this.heightAboveLandSurface;
    }

    this.pos.setAbs(localPoint3d);
    this.pos.reset();
  }

  private void Die(Actor paramActor, boolean paramBoolean)
  {
    if (isNetMirror()) {
      send_DeathRequest(paramActor);
      return;
    }

    if (this.aime != null) {
      this.aime.forgetAll();
      this.aime = null;
    }
    if (this.gun != null) {
      destroy(this.gun);
      this.gun = null;
    }

    this.collisionStage = 1;

    int i = ((ChiefGround)getOwner()).getCodeOfBridgeSegment(this);
    if (i >= 0)
    {
      if (BridgeSegment.isEncodedSegmentDamaged(i)) {
        absoluteDeath(paramActor);
        return;
      }

      LongBridge.AddTraveller(i, this);
      this.codeOfUnderlyingBridgeSegment = i;
    }

    ((ChiefGround)getOwner()).Detach(this, paramActor);

    World.onActorDied(this, paramActor);

    if ((isNet()) || (this.prop.meshName2 == null)) {
      paramBoolean = true;
    }

    if (!paramBoolean) {
      paramBoolean = RndB(0.35F);
    }

    if (paramBoolean) {
      ShowExplode();
      RunSmoke(0.3F, 15.0F);
      if (isNetMaster()) {
        send_DeathCommand(paramActor);

        Point3d localPoint3d = simplifyPos(this.pos.getAbsPoint());
        Orient localOrient = simplifyOri(this.pos.getAbsOrient());
        float f1 = simplifyAng(this.headYaw);
        float f2 = simplifyAng(this.gunPitch);
        setPosition(localPoint3d, localOrient, f1, f2);
      }
      MakeCrush();
    } else {
      this.dying = 1;
      this.dyingDelay = SecsToTicks(Rnd(6.0F, 12.0F));

      this.mov.switchToRotate(this, (RndB(0.5F) ? 1.0F : -1.0F) * Rnd(70.0F, 170.0F), this.prop.ROT_SPEED_MAX);

      RunSmoke(0.2F, 17.0F);
    }
  }

  private void DieMirror(Actor paramActor, boolean paramBoolean)
  {
    if (!isNetMirror()) {
      System.out.println("Internal error in TankGeneric: DieMirror");
    }

    if (this.aime != null) {
      this.aime.forgetAll();
      this.aime = null;
    }
    if (this.gun != null) {
      destroy(this.gun);
      this.gun = null;
    }

    this.collisionStage = 1;

    ((ChiefGround)getOwner()).Detach(this, paramActor);

    World.onActorDied(this, paramActor);

    if (paramBoolean) {
      ShowExplode();
      RunSmoke(0.3F, 15.0F);
    }
    MakeCrush();
  }

  public void destroy()
  {
    if ((this.dust != null) && (!this.dust.isDestroyed())) {
      this.dust._finish();
    }
    this.dust = null;

    this.engineSFX = null;
    this.engineSTimer = 99999999;
    breakSounds();

    if (this.codeOfUnderlyingBridgeSegment >= 0) {
      LongBridge.DelTraveller(this.codeOfUnderlyingBridgeSegment, this);
    }
    if (this.aime != null) {
      this.aime.forgetAll();
      this.aime = null;
    }
    if (this.gun != null) {
      destroy(this.gun);
      this.gun = null;
    }
    super.destroy();
  }

  private void setPosition(Point3d paramPoint3d, Orient paramOrient, float paramFloat1, float paramFloat2)
  {
    this.headYaw = paramFloat1;
    this.gunPitch = paramFloat2;

    hierMesh().chunkSetAngles("Head", this.headYaw, 0.0F, 0.0F);
    hierMesh().chunkSetAngles("Gun", -this.gunPitch, 0.0F, 0.0F);

    this.pos.setAbs(paramPoint3d, paramOrient);
    this.pos.reset();
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }

  protected TankGeneric()
  {
    this(constr_arg1, constr_arg2);
  }

  public void setMesh(String paramString) {
    super.setMesh(paramString);
    if (Config.cur.b3dgunners) return;
    mesh().materialReplaceToNull("Pilot1");
  }

  private TankGeneric(TankProperties paramTankProperties, Actor paramActor) {
    super(paramTankProperties.meshName);
    this.prop = paramTankProperties;

    collide(true);
    drawing(true);
    setOwner(paramActor);

    this.codeName = paramTankProperties.codeName;
    setName(paramActor.name() + this.codeName);

    setArmy(paramActor.getArmy());

    if (mesh().hookFind("SmokeHead") < 0) {
      System.out.println("Tank " + getClass().getName() + ": hook SmokeHead not found");
    }
    if (mesh().hookFind("SmokeEngine") < 0) {
      System.out.println("Tank " + getClass().getName() + ": hook SmokeEngine not found");
    }
    if (mesh().hookFind("Ground_Level") < 0) {
      System.out.println("Tank " + getClass().getName() + ": hook Ground_Level not found");
    }

    this.heightAboveLandSurface = 0.0F;

    int i = mesh().hookFind("Ground_Level");
    if (i != -1) {
      localObject = new Matrix4d();
      mesh().hookMatrix(i, (Matrix4d)localObject);
      this.heightAboveLandSurface = (float)(-((Matrix4d)localObject).m23);
    }

    HookNamed localHookNamed = null;
    Object localObject = null;
    try {
      localHookNamed = new HookNamed(this, "DustL");
      localObject = new HookNamed(this, "DustR");
    } catch (Exception localException2) {
      localHookNamed = localObject = null;
    }

    if ((localHookNamed == null) || (localObject == null))
    {
      localHookNamed = localObject = null;
      this.dust = null;
    } else {
      Loc localLoc1 = new Loc();
      Loc localLoc2 = new Loc();
      Loc localLoc3 = new Loc();
      localHookNamed.computePos(this, localLoc1, localLoc2);
      ((HookNamed)localObject).computePos(this, localLoc1, localLoc3);
      Loc localLoc4 = new Loc();
      localLoc4.interpolate(localLoc2, localLoc3, 0.5F);

      this.dust = Eff3DActor.New(this, null, localLoc4, 1.0F, "Effects/Smokes/TankDust.eff", -1.0F);

      if (this.dust != null) {
        this.dust._setIntesity(0.0F);
      }

    }

    if ((!NetMissionTrack.isPlaying()) || (NetMissionTrack.playingOriginalVersion() > 101)) {
      int j = Mission.cur().getUnitNetIdRemote(this);
      localObject = Mission.cur().getNetMasterChannel();
      if (localObject == null)
        this.net = new Master(this);
      else if (j != 0) {
        this.net = new Mirror(this, (NetChannel)localObject, j);
      }

    }

    this.gun = null;
    try {
      this.gun = ((Gun)this.prop.gunClass.newInstance());
    } catch (Exception localException1) {
      System.out.println(localException1.getMessage());
      localException1.printStackTrace();
      System.out.println("Tank: Can't create gun '" + this.prop.gunClass.getName() + "'");
    }

    this.gun.set(this, "Gun");
    this.gun.loadBullets(isNetMirror() ? -1 : this.prop.MAX_SHELLS);

    this.headYaw = 0.0F;
    this.gunPitch = this.prop.GUN_STD_PITCH;
    hierMesh().chunkSetAngles("Head", this.headYaw, 0.0F, 0.0F);
    hierMesh().chunkSetAngles("Gun", -this.gunPitch, 0.0F, 0.0F);

    this.aime = new Aim(this, isNetMirror());
  }

  private void send_DeathRequest(Actor paramActor)
  {
    if (!isNetMirror()) {
      return;
    }

    if ((this.net.masterChannel() instanceof NetChannelInStream))
      return;
    try
    {
      NetMsgFiltered localNetMsgFiltered = new NetMsgFiltered();
      localNetMsgFiltered.writeByte(68);
      localNetMsgFiltered.writeNetObj(paramActor == null ? null : paramActor.net);
      localNetMsgFiltered.setIncludeTime(false);
      this.net.postTo(Time.current(), this.net.masterChannel(), localNetMsgFiltered);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void send_CollisionDeathRequest()
  {
    if (!isNetMirror()) {
      return;
    }

    if ((this.net.masterChannel() instanceof NetChannelInStream))
      return;
    try
    {
      NetMsgFiltered localNetMsgFiltered = new NetMsgFiltered();
      localNetMsgFiltered.writeByte(67);
      localNetMsgFiltered.setIncludeTime(false);
      this.net.postTo(Time.current(), this.net.masterChannel(), localNetMsgFiltered);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void send_FireCommand(Actor paramActor, int paramInt, float paramFloat)
  {
    if ((!isNetMaster()) || (!this.net.isMirrored())) {
      return;
    }

    if ((!Actor.isValid(paramActor)) || (!paramActor.isNet())) {
      return;
    }

    paramInt &= 255;

    if (paramFloat < 0.0F)
      try {
        this.outCommand.unLockAndClear();
        this.outCommand.writeByte(84);
        this.outCommand.writeNetObj(paramActor.net);
        this.outCommand.writeByte(paramInt);
        this.outCommand.setIncludeTime(false);
        this.net.post(Time.current(), this.outCommand);
      } catch (Exception localException1) {
        System.out.println(localException1.getMessage());
        localException1.printStackTrace();
      }
    else
      try {
        this.outCommand.unLockAndClear();
        this.outCommand.writeByte(70);
        this.outCommand.writeFloat(paramFloat);
        this.outCommand.writeNetObj(paramActor.net);
        this.outCommand.writeByte(paramInt);
        this.outCommand.setIncludeTime(true);
        this.net.post(Time.current(), this.outCommand);
      } catch (Exception localException2) {
        System.out.println(localException2.getMessage());
        localException2.printStackTrace();
      }
  }

  private void send_AnByteAndPoseCommand(boolean paramBoolean, Actor paramActor, int paramInt)
  {
    if ((!isNetMaster()) || (!this.net.isMirrored())) {
      return;
    }

    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    try {
      localNetMsgGuaranted.writeByte(paramInt);
      sendPose(localNetMsgGuaranted);
      if (paramBoolean) {
        localNetMsgGuaranted.writeNetObj(paramActor == null ? null : paramActor.net);
      }
      this.net.post(localNetMsgGuaranted);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void send_DeathCommand(Actor paramActor)
  {
    send_AnByteAndPoseCommand(true, paramActor, 68);
  }
  private void send_AbsoluteDeathCommand(Actor paramActor) {
    send_AnByteAndPoseCommand(true, paramActor, 65);
  }
  private void send_CollisionDeathCommand() {
    send_AnByteAndPoseCommand(false, null, 67);
  }

  private void send_MoveCommand(Moving paramMoving, float paramFloat)
  {
    if ((!isNetMaster()) || (!this.net.isMirrored())) {
      return;
    }

    if ((paramMoving.moveCurTime < 0L) && (paramMoving.rotatCurTime < 0L))
      return;
    try
    {
      this.outCommand.unLockAndClear();

      if ((paramMoving.dstPos == null) || (paramMoving.moveTotTime <= 0L) || (paramMoving.normal == null)) {
        this.outCommand.writeByte(83);
        this.outCommand.setIncludeTime(false);
        this.net.post(Time.current(), this.outCommand);
      } else {
        if (paramFloat > 0.0F)
          this.outCommand.writeByte(77);
        else {
          this.outCommand.writeByte(109);
        }
        this.outCommand.write(packPos(paramMoving.dstPos));
        this.outCommand.writeByte(packNormal(paramMoving.normal.z));
        if (paramMoving.normal.z >= 0.0F) {
          this.outCommand.writeByte(packNormal(paramMoving.normal.x));
          this.outCommand.writeByte(packNormal(paramMoving.normal.y));
        }
        int i = (int)(Time.tickLen() * paramMoving.moveTotTime);
        if (i >= 65536) {
          i = 65535;
        }
        this.outCommand.writeShort(i);
        this.outCommand.setIncludeTime(true);
        this.net.post(Time.current(), this.outCommand);
      }
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  static int packAng(float paramFloat)
  {
    return 0xFF & (int)(paramFloat * 256.0F / 360.0F);
  }
  static int packNormal(float paramFloat) {
    paramFloat += 1.0F;
    paramFloat *= 0.5F;
    paramFloat *= 254.0F;
    int i = (int)(paramFloat + 0.5F);
    if (i < 0) i = 0;
    if (i > 254) i = 254;
    return i - 127;
  }
  static byte[] packPos(Point3d paramPoint3d) {
    byte[] arrayOfByte = new byte[8];
    int i = (int)(paramPoint3d.x * 20.0D + 0.5D);
    int j = (int)(paramPoint3d.y * 20.0D + 0.5D);
    int k = (int)(paramPoint3d.z * 10.0D + 0.5D);
    arrayOfByte[0] = (byte)(i >> 0 & 0xFF);
    arrayOfByte[1] = (byte)(i >> 8 & 0xFF);
    arrayOfByte[2] = (byte)(i >> 16 & 0xFF);
    arrayOfByte[3] = (byte)(j >> 0 & 0xFF);
    arrayOfByte[4] = (byte)(j >> 8 & 0xFF);
    arrayOfByte[5] = (byte)(j >> 16 & 0xFF);
    arrayOfByte[6] = (byte)(k >> 0 & 0xFF);
    arrayOfByte[7] = (byte)(k >> 8 & 0xFF);
    return arrayOfByte;
  }
  static byte[] packOri(Orient paramOrient) {
    byte[] arrayOfByte = new byte[3];
    int i = (int)(paramOrient.getYaw() * 256.0F / 360.0F);
    int j = (int)(paramOrient.getPitch() * 256.0F / 360.0F);
    int k = (int)(paramOrient.getRoll() * 256.0F / 360.0F);
    arrayOfByte[0] = (byte)(i & 0xFF);
    arrayOfByte[1] = (byte)(j & 0xFF);
    arrayOfByte[2] = (byte)(k & 0xFF);
    return arrayOfByte;
  }

  static float unpackAng(int paramInt) {
    return paramInt * 360.0F / 256.0F;
  }
  static float unpackNormal(int paramInt) {
    return paramInt / 127.0F;
  }
  static Point3d unpackPos(byte[] paramArrayOfByte) {
    int i = ((paramArrayOfByte[2] & 0xFF) << 16) + ((paramArrayOfByte[1] & 0xFF) << 8) + ((paramArrayOfByte[0] & 0xFF) << 0);

    int j = ((paramArrayOfByte[5] & 0xFF) << 16) + ((paramArrayOfByte[4] & 0xFF) << 8) + ((paramArrayOfByte[3] & 0xFF) << 0);

    int k = ((paramArrayOfByte[7] & 0xFF) << 8) + ((paramArrayOfByte[6] & 0xFF) << 0);

    return new Point3d(i * 0.05D, j * 0.05D, k * 0.1D);
  }
  static Orient unpackOri(byte[] paramArrayOfByte) {
    int i = paramArrayOfByte[0] & 0xFF;
    int j = paramArrayOfByte[1] & 0xFF;
    int k = paramArrayOfByte[2] & 0xFF;
    Orient localOrient = new Orient();
    localOrient.setYPR(i * 360.0F / 256.0F, j * 360.0F / 256.0F, k * 360.0F / 256.0F);
    return localOrient;
  }

  static float simplifyAng(float paramFloat) {
    return unpackAng(packAng(paramFloat));
  }
  static Point3d simplifyPos(Point3d paramPoint3d) {
    return unpackPos(packPos(paramPoint3d));
  }
  static Orient simplifyOri(Orient paramOrient) {
    return unpackOri(packOri(paramOrient));
  }

  static float readPackedAng(NetMsgInput paramNetMsgInput) throws IOException {
    return unpackAng(paramNetMsgInput.readByte());
  }
  static float readPackedNormal(NetMsgInput paramNetMsgInput) throws IOException {
    return unpackNormal(paramNetMsgInput.readByte());
  }
  static Point3d readPackedPos(NetMsgInput paramNetMsgInput) throws IOException {
    byte[] arrayOfByte = new byte[8];
    paramNetMsgInput.read(arrayOfByte);
    return unpackPos(arrayOfByte);
  }
  static Orient readPackedOri(NetMsgInput paramNetMsgInput) throws IOException {
    byte[] arrayOfByte = new byte[3];
    paramNetMsgInput.read(arrayOfByte);
    return unpackOri(arrayOfByte);
  }

  private void sendPose(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
    paramNetMsgGuaranted.write(packPos(this.pos.getAbsPoint()));
    paramNetMsgGuaranted.write(packOri(this.pos.getAbsOrient()));
    paramNetMsgGuaranted.writeByte(packAng(this.headYaw));
    paramNetMsgGuaranted.writeByte(packAng(this.gunPitch));
  }

  public void netFirstUpdate(NetChannel paramNetChannel)
    throws IOException
  {
    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();

    int i = this.dying == 0 ? 73 : 105;

    localNetMsgGuaranted.writeByte(i);
    sendPose(localNetMsgGuaranted);

    this.net.postTo(paramNetChannel, localNetMsgGuaranted);
  }

  public void startMove()
  {
    if (!interpEnd("move")) {
      this.mov = new Moving();
      if (this.aime != null) {
        this.aime.forgetAll();
        this.aime = null;
      }
      this.aime = new Aim(this, isNetMirror());
      this.collisionStage = 0;
      interpPut(new Move(), "move", Time.current(), null);

      this.engineSFX = newSound(this.prop.soundMove, true);
      this.engineSTimer = (int)SecsToTicks(Rnd(5.0F, 7.0F));
    }
  }

  public void forceReaskMove()
  {
    if (isNetMirror())
      return;
    if (this.collisionStage != 0)
      return;
    if (this.dying != 0)
      return;
    if ((this.mov == null) || (this.mov.normal == null))
    {
      return;
    }this.mov.switchToAsk();
  }

  public UnitData GetUnitData()
  {
    return this.udata;
  }

  public float HeightAboveLandSurface() {
    return this.heightAboveLandSurface;
  }

  public float SpeedAverage()
  {
    return this.prop.SPEED_AVERAGE;
  }

  public float BestSpace()
  {
    return this.prop.BEST_SPACE;
  }

  public float CommandInterval() {
    return this.prop.COMMAND_INTERVAL;
  }
  public float StayInterval() {
    return this.prop.STAY_INTERVAL;
  }

  public UnitInPackedForm Pack()
  {
    int i = Finger.Int(getClass().getName());

    int j = 0;
    return new UnitInPackedForm(this.codeName, i, j, SpeedAverage(), BestSpace(), WeaponsMask(), HitbyMask());
  }

  public int WeaponsMask()
  {
    return this.prop.WEAPONS_MASK;
  }

  public int HitbyMask() {
    return this.prop.HITBY_MASK;
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

    if ((this instanceof TgtTank)) {
      if (paramArrayOfBulletProperties[0].cumulativePower > 0.0F)
      {
        return 0;
      }
      if (paramArrayOfBulletProperties[1].cumulativePower > 0.0F)
      {
        return 1;
      }

      if (paramArrayOfBulletProperties[0].power <= 0.0F)
      {
        return 0;
      }
      if (paramArrayOfBulletProperties[1].power <= 0.0F)
      {
        return 1;
      }
    } else {
      if (paramArrayOfBulletProperties[0].power <= 0.0F)
      {
        return 0;
      }
      if (paramArrayOfBulletProperties[1].power <= 0.0F)
      {
        return 1;
      }

      if (paramArrayOfBulletProperties[0].cumulativePower > 0.0F)
      {
        return 0;
      }
      if (paramArrayOfBulletProperties[1].cumulativePower > 0.0F)
      {
        return 1;
      }
    }

    if (paramArrayOfBulletProperties[0].powerType == 1)
    {
      return 0;
    }
    if (paramArrayOfBulletProperties[1].powerType == 1)
    {
      return 1;
    }

    if (paramArrayOfBulletProperties[0].powerType == 0)
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

  public float AttackMaxDistance()
  {
    return this.prop.ATTACK_MAX_DISTANCE;
  }

  public void absoluteDeath(Actor paramActor)
  {
    if (isNetMirror())
    {
      return;
    }

    if (isNetMaster()) {
      send_AbsoluteDeathCommand(paramActor);
    }

    doAbsoluteDeath(paramActor);
  }

  private void doAbsoluteDeath(Actor paramActor) {
    ChiefGround localChiefGround = (ChiefGround)getOwner();
    if (localChiefGround != null) {
      localChiefGround.Detach(this, paramActor);
    }

    if (!getDiedFlag()) {
      World.onActorDied(this, paramActor);
    }

    Explosions.Tank_ExplodeCollapse(this.pos.getAbsPoint());

    destroy();
  }

  public boolean unmovableInFuture()
  {
    return this.dying != 0;
  }

  public void collisionDeath()
  {
    if (isNetMirror()) {
      send_CollisionDeathRequest();
      return;
    }

    if (isNetMaster()) {
      send_CollisionDeathCommand();
    }

    doCollisionDeath();
  }

  private void doCollisionDeath() {
    ChiefGround localChiefGround = (ChiefGround)getOwner();
    int i = ((localChiefGround == null) && (this.codeOfUnderlyingBridgeSegment >= 0)) || ((localChiefGround != null) && (localChiefGround.getCodeOfBridgeSegment(this) >= 0)) ? 1 : 0;

    if (localChiefGround != null) {
      localChiefGround.Detach(this, null);
    }

    if (i != 0)
      Explosions.Tank_ExplodeCollapse(this.pos.getAbsPoint());
    else {
      Explosions.Tank_ExplodeCollapse(this.pos.getAbsPoint());
    }

    destroy();
  }

  public float futurePosition(float paramFloat, Point3d paramPoint3d)
  {
    this.pos.getAbs(paramPoint3d);
    if (paramFloat <= 0.0F) return 0.0F;

    if ((this.mov.moveCurTime < 0L) && (this.mov.rotatCurTime < 0L))
      return 0.0F;
    float f1 = TicksToSecs(this.mov.moveCurTime);

    if (this.mov.dstPos == null) {
      if (f1 >= paramFloat) return paramFloat;
      return f1;
    }

    float f2 = 0.0F;
    if (this.mov.rotatingInPlace) {
      f2 = TicksToSecs(this.mov.rotatCurTime);
      if (f2 >= paramFloat) return paramFloat;
    }

    if (f1 <= 0.0F) {
      return f2;
    }

    if (f2 + f1 <= paramFloat) {
      paramPoint3d.set(this.mov.dstPos);
      return f2 + f1;
    }

    Point3d localPoint3d = new Point3d();
    localPoint3d.set(this.mov.dstPos);
    double d = (paramFloat - f2) / f1;

    p.x = (paramPoint3d.x * (1.0D - d) + localPoint3d.x * d);
    p.y = (paramPoint3d.y * (1.0D - d) + localPoint3d.y * d);

    if (this.mov.normal.z < 0.0F) {
      p.z = (Engine.land().HQ(p.x, p.y) + HeightAboveLandSurface());
    }
    else {
      p.z = (paramPoint3d.z * (1.0D - d) + localPoint3d.z * d);
    }

    paramPoint3d.set(p);
    return paramFloat;
  }

  public float getReloadingTime(Aim paramAim)
  {
    if (isNetMirror())
    {
      return 1.0F;
    }
    if (this.gun.haveBullets()) {
      return this.prop.DELAY_AFTER_SHOOT;
    }
    this.gun.loadBullets(this.prop.MAX_SHELLS);
    return 120.0F;
  }

  public float chainFireTime(Aim paramAim)
  {
    return this.prop.CHAINFIRE_TIME <= 0.0F ? 0.0F : this.prop.CHAINFIRE_TIME * Rnd(0.75F, 1.25F);
  }

  public float probabKeepSameEnemy(Actor paramActor)
  {
    return Head360() ? 0.8F : 0.0F;
  }

  public float minTimeRelaxAfterFight()
  {
    return Head360() ? 0.0F : 10.0F;
  }

  public void gunStartParking(Aim paramAim)
  {
    paramAim.setRotationForParking(this.headYaw, this.gunPitch, 0.0F, this.prop.GUN_STD_PITCH, this.prop.HEAD_YAW_RANGE, this.prop.HEAD_MAX_YAW_SPEED, this.prop.GUN_MAX_PITCH_SPEED);
  }

  public void gunInMove(boolean paramBoolean, Aim paramAim)
  {
    float f1 = paramAim.t();

    if ((Head360()) || (paramBoolean) || (!paramAim.bodyRotation)) {
      this.headYaw = paramAim.anglesYaw.getDeg(f1);
      this.gunPitch = paramAim.anglesPitch.getDeg(f1);
      hierMesh().chunkSetAngles("Head", this.headYaw, 0.0F, 0.0F);
      hierMesh().chunkSetAngles("Gun", -this.gunPitch, 0.0F, 0.0F);
      this.pos.inValidate(false);

      return;
    }

    float f2 = paramAim.anglesYaw.getDeg(f1);
    this.pos.getAbs(o);
    o.setYaw(f2);

    if ((this.mov != null) && (this.mov.normal != null))
    {
      if (this.mov.normal.z < 0.0F) {
        Engine.land().N(this.mov.srcPos.x, this.mov.srcPos.y, n);
        o.orient(n);
      } else {
        o.orient(this.mov.normal);
      }
    }
    this.pos.setAbs(o);

    this.gunPitch = paramAim.anglesPitch.getDeg(f1);
    hierMesh().chunkSetAngles("Gun", -this.gunPitch, 0.0F, 0.0F);

    this.pos.inValidate(false);
  }

  public Actor findEnemy(Aim paramAim)
  {
    if (isNetMirror()) {
      return null;
    }

    Actor localActor = null;

    Object localObject = (ChiefGround)getOwner();
    if (localObject != null)
    {
      if (((ChiefGround)localObject).getCodeOfBridgeSegment(this) >= 0) {
        return null;
      }

      localActor = ((ChiefGround)localObject).GetNearestEnemy(this.pos.getAbsPoint(), AttackMaxDistance(), WeaponsMask(), this.prop.ATTACK_FAST_TARGETS ? -1.0F : KmHourToMSec(100.0F));
    }

    if (localActor == null) {
      return null;
    }

    localObject = null;

    if (this.gun.prop != null) {
      i = ((Prey)localActor).chooseBulletType(this.gun.prop.bullet);

      if (i < 0)
      {
        return null;
      }

      localObject = this.gun.prop.bullet[i];
    }

    int i = ((Prey)localActor).chooseShotpoint((BulletProperties)localObject);

    if (i < 0) {
      return null;
    }

    paramAim.shotpoint_idx = i;

    return (Actor)localActor;
  }

  public boolean enterToFireMode(int paramInt, Actor paramActor, float paramFloat, Aim paramAim)
  {
    if ((paramInt == 1) || ((!Head360()) && (paramAim.bodyRotation)))
    {
      if (this.collisionStage != 0) {
        return false;
      }

      if (StayWhenFire()) {
        this.mov.switchToStay(1.5F);
      }
    }

    if (!isNetMirror()) {
      send_FireCommand(paramActor, paramAim.shotpoint_idx, paramInt == 0 ? -1.0F : paramFloat);
    }

    return true;
  }

  private void Track_Mirror(Actor paramActor, int paramInt)
  {
    if (this.dying != 0) {
      return;
    }

    if (paramActor == null) {
      return;
    }

    if (this.aime == null) {
      return;
    }

    this.aime.passive_StartFiring(0, paramActor, paramInt, 0.0F);
  }

  private void Fire_Mirror(Actor paramActor, int paramInt, float paramFloat)
  {
    if (this.dying != 0) {
      return;
    }

    if (paramActor == null) {
      return;
    }

    if (this.aime == null) {
      return;
    }

    if (paramFloat <= 0.2F) {
      paramFloat = 0.2F;
    }

    if (paramFloat >= 15.0F) {
      paramFloat = 15.0F;
    }

    this.aime.passive_StartFiring(1, paramActor, paramInt, paramFloat);
  }

  public int targetGun(Aim paramAim, Actor paramActor, float paramFloat, boolean paramBoolean)
  {
    if ((!Actor.isValid(paramActor)) || (!paramActor.isAlive()) || (paramActor.getArmy() == 0))
    {
      return 0;
    }

    if ((this.gun instanceof CannonMidrangeGeneric)) {
      int i = ((Prey)paramActor).chooseBulletType(this.gun.prop.bullet);
      if (i < 0) {
        return 0;
      }
      ((CannonMidrangeGeneric)this.gun).setBulletType(i);
    }

    boolean bool = ((Prey)paramActor).getShotpointOffset(paramAim.shotpoint_idx, p1);
    if (!bool) {
      return 0;
    }

    float f1 = paramFloat * Rnd(0.8F, 1.2F);

    if (!Aimer.Aim((BulletAimer)this.gun, paramActor, this, f1, p1, null))
    {
      return 0;
    }

    Point3d localPoint3d1 = new Point3d();
    Aimer.GetPredictedTargetPosition(localPoint3d1);

    Point3d localPoint3d2 = Aimer.GetHunterFirePoint();

    float f2 = 0.19F;

    double d1 = localPoint3d1.distance(localPoint3d2);
    double d2 = localPoint3d1.z;

    localPoint3d1.sub(localPoint3d2);
    localPoint3d1.scale(Rnd(0.95D, 1.05D));
    localPoint3d1.add(localPoint3d2);

    if (f1 > 0.001F) {
      Point3d localPoint3d3 = new Point3d();
      paramActor.pos.getAbs(localPoint3d3);

      tmpv.sub(localPoint3d1, localPoint3d3);
      double d3 = tmpv.length();

      if (d3 > 0.001D) {
        float f8 = (float)d3 / f1;
        if (f8 > 200.0F) {
          f8 = 200.0F;
        }
        float f9 = f8 * 0.02F;

        localPoint3d3.sub(localPoint3d2);
        double d4 = localPoint3d3.x * localPoint3d3.x + localPoint3d3.y * localPoint3d3.y + localPoint3d3.z * localPoint3d3.z;

        if (d4 > 0.01D) {
          float f10 = (float)tmpv.dot(localPoint3d3);
          f10 /= (float)(d3 * Math.sqrt(d4));

          f10 = (float)Math.sqrt(1.0F - f10 * f10);

          f9 *= (0.4F + 0.6F * f10);
        }

        int k = Mission.curCloudsType();
        if (k >= 3) {
          float f11 = k >= 5 ? 250.0F : 500.0F;
          float f12 = (float)(d1 / f11);
          if (f12 > 1.0F) {
            if (f12 > 10.0F) {
              return 0;
            }
            f12 = (f12 - 1.0F) / 9.0F * 2.0F + 1.0F;
            f9 *= f12;
          }
        }

        if ((k >= 3) && (d2 > Mission.curCloudsHeight())) {
          f9 *= 1.25F;
        }

        f2 += f9;
      }

    }

    if (World.Sun().ToSun.z < -0.15F) {
      f6 = (-World.Sun().ToSun.z - 0.15F) / 0.13F;
      if (f6 >= 1.0F) {
        f6 = 1.0F;
      }

      if (((paramActor instanceof Aircraft)) && (Time.current() - ((Aircraft)paramActor).tmSearchlighted < 1000L))
      {
        f6 = 0.0F;
      }
      f2 += 12.0F * f6;
    }

    float f6 = (float)paramActor.getSpeed(null);
    float f7 = 83.333336F;
    f6 = f6 >= f7 ? 1.0F : f6 / f7;
    f2 += f6 * this.prop.FAST_TARGETS_ANGLE_ERROR;

    Vector3d localVector3d = new Vector3d();
    if (!((BulletAimer)this.gun).FireDirection(localPoint3d2, localPoint3d1, localVector3d))
    {
      return 0;
    }
    float f3;
    float f4;
    float f5;
    if (paramBoolean) {
      f3 = 99999.0F;
      f4 = 99999.0F;
      f5 = 99999.0F;
    } else {
      f3 = this.prop.HEAD_MAX_YAW_SPEED;
      f4 = this.prop.GUN_MAX_PITCH_SPEED;
      f5 = this.prop.ROT_SPEED_MAX;
    }

    Orient localOrient = this.pos.getAbs().getOrient();

    f7 = 0.0F;
    if (!Head360()) {
      f7 = localOrient.getYaw();
    }

    int j = paramAim.setRotationForTargeting(this, localOrient, localPoint3d2, this.headYaw, this.gunPitch, localVector3d, f2, f1, this.prop.HEAD_YAW_RANGE, this.prop.GUN_MIN_PITCH, this.prop.GUN_MAX_PITCH, f3, f4, f5);

    if ((!Head360()) && (j != 0) && (paramAim.bodyRotation))
    {
      paramAim.anglesYaw.rotateDeg(f7);
    }

    return j;
  }

  public void singleShot(Aim paramAim)
  {
    if (StayWhenFire()) {
      this.mov.switchToStay(1.5F);
    }
    this.gun.shots(1);
  }

  public void startFire(Aim paramAim)
  {
    if (StayWhenFire()) {
      this.mov.switchToStay(1.5F);
    }
    this.gun.shots(-1);
  }

  public void continueFire(Aim paramAim)
  {
    if (StayWhenFire())
      this.mov.switchToStay(1.5F);
  }

  public void stopFire(Aim paramAim)
  {
    if (StayWhenFire()) {
      this.mov.switchToStay(1.5F);
    }
    this.gun.shots(0);
  }

  public static class SPAWN
    implements UnitSpawn
  {
    public Class cls;
    public TankGeneric.TankProperties proper;

    private static float getF(SectFile paramSectFile, String paramString1, String paramString2, float paramFloat1, float paramFloat2)
    {
      float f = paramSectFile.get(paramString1, paramString2, -9865.3447F);
      if ((f == -9865.3447F) || (f < paramFloat1) || (f > paramFloat2)) {
        if (f == -9865.3447F) {
          System.out.println("Tank: Parameter [" + paramString1 + "]:<" + paramString2 + "> " + "not found");
        }
        else {
          System.out.println("Tank: Value of [" + paramString1 + "]:<" + paramString2 + "> (" + f + ")" + " is out of range (" + paramFloat1 + ";" + paramFloat2 + ")");
        }

        throw new RuntimeException("Can't set property");
      }
      return f;
    }

    private static String getS(SectFile paramSectFile, String paramString1, String paramString2) {
      String str = paramSectFile.get(paramString1, paramString2);
      if ((str == null) || (str.length() <= 0)) {
        System.out.print("Tank: Parameter [" + paramString1 + "]:<" + paramString2 + "> ");
        System.out.println(str == null ? "not found" : "is empty");
        throw new RuntimeException("Can't set property");
      }
      return str;
    }

    private static String getS(SectFile paramSectFile, String paramString1, String paramString2, String paramString3) {
      String str = paramSectFile.get(paramString1, paramString2);
      if ((str == null) || (str.length() <= 0)) {
        return paramString3;
      }
      return str;
    }

    private static TankGeneric.TankProperties LoadTankProperties(SectFile paramSectFile, String paramString, Class paramClass)
    {
      TankGeneric.TankProperties localTankProperties = new TankGeneric.TankProperties();

      String str1 = getS(paramSectFile, paramString, "PanzerType", null);
      if (str1 == null) {
        str1 = "Tank";
      }
      localTankProperties.fnShotPanzer = TableFunctions.GetFunc2(str1 + "ShotPanzer");
      localTankProperties.fnExplodePanzer = TableFunctions.GetFunc2(str1 + "ExplodePanzer");

      localTankProperties.PANZER_TNT_TYPE = getF(paramSectFile, paramString, "PanzerSubtype", 0.0F, 100.0F);

      localTankProperties.meshSummer = getS(paramSectFile, paramString, "MeshSummer");
      localTankProperties.meshDesert = getS(paramSectFile, paramString, "MeshDesert", localTankProperties.meshSummer);
      localTankProperties.meshWinter = getS(paramSectFile, paramString, "MeshWinter", localTankProperties.meshSummer);
      localTankProperties.meshSummer1 = getS(paramSectFile, paramString, "MeshSummerDamage", null);
      localTankProperties.meshDesert1 = getS(paramSectFile, paramString, "MeshDesertDamage", localTankProperties.meshSummer1);
      localTankProperties.meshWinter1 = getS(paramSectFile, paramString, "MeshWinterDamage", localTankProperties.meshSummer1);

      int i = (localTankProperties.meshSummer1 == null ? 1 : 0) + (localTankProperties.meshDesert1 == null ? 1 : 0) + (localTankProperties.meshWinter1 == null ? 1 : 0);

      if ((i != 0) && (i != 3)) {
        System.out.println("Tank: Uncomplete set of damage meshes for '" + paramString + "'");

        throw new RuntimeException("Can't register tank object");
      }

      localTankProperties.explodeName = getS(paramSectFile, paramString, "Explode", "Tank");

      localTankProperties.PANZER_BODY_FRONT = getF(paramSectFile, paramString, "PanzerBodyFront", 0.001F, 9.999F);

      if (paramSectFile.get(paramString, "PanzerBodyBack", -9865.3447F) == -9865.3447F) {
        localTankProperties.PANZER_BODY_BACK = localTankProperties.PANZER_BODY_FRONT;
        localTankProperties.PANZER_BODY_SIDE = localTankProperties.PANZER_BODY_FRONT;
        localTankProperties.PANZER_BODY_TOP = localTankProperties.PANZER_BODY_FRONT;
      } else {
        localTankProperties.PANZER_BODY_BACK = getF(paramSectFile, paramString, "PanzerBodyBack", 0.001F, 9.999F);
        localTankProperties.PANZER_BODY_SIDE = getF(paramSectFile, paramString, "PanzerBodySide", 0.001F, 9.999F);
        localTankProperties.PANZER_BODY_TOP = getF(paramSectFile, paramString, "PanzerBodyTop", 0.001F, 9.999F);
      }

      if (paramSectFile.get(paramString, "PanzerHead", -9865.3447F) == -9865.3447F)
        localTankProperties.PANZER_HEAD = localTankProperties.PANZER_BODY_FRONT;
      else {
        localTankProperties.PANZER_HEAD = getF(paramSectFile, paramString, "PanzerHead", 0.001F, 9.999F);
      }

      if (paramSectFile.get(paramString, "PanzerHeadTop", -9865.3447F) == -9865.3447F)
        localTankProperties.PANZER_HEAD_TOP = localTankProperties.PANZER_BODY_TOP;
      else {
        localTankProperties.PANZER_HEAD_TOP = getF(paramSectFile, paramString, "PanzerHeadTop", 0.001F, 9.999F);
      }

      float f1 = Math.min(Math.min(localTankProperties.PANZER_BODY_BACK, localTankProperties.PANZER_BODY_TOP), Math.min(localTankProperties.PANZER_BODY_SIDE, localTankProperties.PANZER_HEAD_TOP));

      localTankProperties.HITBY_MASK = (f1 > 0.015F ? -2 : -1);

      String str2 = "com.maddox.il2.objects.weapons." + getS(paramSectFile, paramString, "Gun");
      try
      {
        localTankProperties.gunClass = Class.forName(str2);
      } catch (Exception localException) {
        System.out.println("Tank: Can't find gun class '" + str2 + "'");

        throw new RuntimeException("Can't register tank object");
      }

      localTankProperties.WEAPONS_MASK = Gun.getProperties(localTankProperties.gunClass).weaponType;
      if (localTankProperties.WEAPONS_MASK == 0) {
        System.out.println("Tank: Undefined weapon type in gun class '" + str2 + "'");

        throw new RuntimeException("Can't register tank object");
      }

      localTankProperties.MAX_SHELLS = (int)getF(paramSectFile, paramString, "NumShells", -1.0F, 30000.0F);

      localTankProperties.ATTACK_MAX_DISTANCE = getF(paramSectFile, paramString, "AttackMaxDistance", 6.0F, 12000.0F);

      float f2 = getF(paramSectFile, paramString, "HeadYawHalfRange", 0.0F, 180.0F);
      localTankProperties.HEAD_YAW_RANGE.set(-f2, f2);
      localTankProperties.GUN_MIN_PITCH = getF(paramSectFile, paramString, "GunMinPitch", -15.0F, 85.0F);
      localTankProperties.GUN_STD_PITCH = getF(paramSectFile, paramString, "GunStdPitch", -15.0F, 89.900002F);
      localTankProperties.GUN_MAX_PITCH = getF(paramSectFile, paramString, "GunMaxPitch", 0.0F, 89.900002F);
      localTankProperties.HEAD_MAX_YAW_SPEED = getF(paramSectFile, paramString, "HeadMaxYawSpeed", 0.1F, 999.0F);
      localTankProperties.GUN_MAX_PITCH_SPEED = getF(paramSectFile, paramString, "GunMaxPitchSpeed", 0.1F, 999.0F);
      localTankProperties.DELAY_AFTER_SHOOT = getF(paramSectFile, paramString, "DelayAfterShoot", 0.0F, 999.0F);
      localTankProperties.CHAINFIRE_TIME = getF(paramSectFile, paramString, "ChainfireTime", 0.0F, 600.0F);

      localTankProperties.ATTACK_FAST_TARGETS = false;
      float f3 = paramSectFile.get(paramString, "FireFastTargets", -9865.3447F);
      if (f3 != -9865.3447F) {
        localTankProperties.ATTACK_FAST_TARGETS = (f3 > 0.5F);
      }

      float f4 = paramSectFile.get(paramString, "FastTargetsAngleError", -9865.3447F);
      if (f4 <= 0.0F)
        f4 = 0.0F;
      else if (f4 >= 45.0F) {
        f4 = 45.0F;
      }
      localTankProperties.FAST_TARGETS_ANGLE_ERROR = f4;

      localTankProperties.STAY_WHEN_FIRE = (getF(paramSectFile, paramString, "StayWhenFire", 0.0F, 1.0F) > 0.0F);

      localTankProperties.SPEED_AVERAGE = TankGeneric.KmHourToMSec(getF(paramSectFile, paramString, "SpeedAverage", 1.0F, 100.0F));
      localTankProperties.SPEED_MAX = TankGeneric.KmHourToMSec(getF(paramSectFile, paramString, "SpeedMax", 1.0F, 100.0F));
      localTankProperties.SPEED_BACK = TankGeneric.KmHourToMSec(getF(paramSectFile, paramString, "SpeedBack", 0.5F, 100.0F));
      localTankProperties.ROT_SPEED_MAX = getF(paramSectFile, paramString, "RotSpeedMax", 0.1F, 800.0F);
      localTankProperties.ROT_INVIS_ANG = getF(paramSectFile, paramString, "RotInvisAng", 0.0F, 360.0F);

      localTankProperties.BEST_SPACE = getF(paramSectFile, paramString, "BestSpace", 0.1F, 100.0F);
      localTankProperties.AFTER_COLLISION_DIST = getF(paramSectFile, paramString, "AfterCollisionDist", 0.1F, 80.0F);

      localTankProperties.COMMAND_INTERVAL = getF(paramSectFile, paramString, "CommandInterval", 0.5F, 100.0F);
      localTankProperties.STAY_INTERVAL = getF(paramSectFile, paramString, "StayInterval", 0.1F, 200.0F);

      localTankProperties.soundMove = getS(paramSectFile, paramString, "SoundMove");

      Property.set(paramClass, "iconName", "icons/" + getS(paramSectFile, paramString, "Icon") + ".mat");
      Property.set(paramClass, "meshName", localTankProperties.meshSummer);
      Property.set(paramClass, "speed", localTankProperties.SPEED_AVERAGE);

      return localTankProperties;
    }

    public SPAWN(Class paramClass)
    {
      try
      {
        String str1 = paramClass.getName();
        int i = str1.lastIndexOf('.');
        int j = str1.lastIndexOf('$');
        if (i < j) {
          i = j;
        }
        String str2 = str1.substring(i + 1);
        this.proper = LoadTankProperties(Statics.getTechnicsFile(), str2, paramClass);
      }
      catch (Exception localException)
      {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("Problem in tank spawn: " + paramClass.getName());
      }

      this.cls = paramClass;
      Spawn.add(this.cls, this);
    }

    public Class unitClass() {
      return this.cls;
    }

    public Actor unitSpawn(int paramInt1, int paramInt2, Actor paramActor)
    {
      this.proper.codeName = paramInt1;

      switch (World.cur().camouflage) {
      case 1:
        this.proper.meshName = this.proper.meshWinter;
        this.proper.meshName2 = this.proper.meshWinter1;
        break;
      case 2:
        this.proper.meshName = this.proper.meshDesert;
        this.proper.meshName2 = this.proper.meshDesert1;
        break;
      default:
        this.proper.meshName = this.proper.meshSummer;
        this.proper.meshName2 = this.proper.meshSummer1;
      }

      TankGeneric localTankGeneric = null;
      try
      {
        TankGeneric.access$2402(this.proper);
        TankGeneric.access$2502(paramActor);
        localTankGeneric = (TankGeneric)this.cls.newInstance();
        TankGeneric.access$2402(null);
        TankGeneric.access$2502(null);
      } catch (Exception localException) {
        TankGeneric.access$2402(null);
        TankGeneric.access$2502(null);
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("SPAWN: Can't create Tank object [class:" + this.cls.getName() + "]");

        return null;
      }

      return localTankGeneric;
    }
  }

  class Move extends Interpolate
  {
    Move()
    {
    }

    public boolean tick()
    {
      if (TankGeneric.this.dying != 0) {
        TankGeneric.this.neverDust();

        if (TankGeneric.this.dying == 2) {
          return false;
        }

        if (TankGeneric.access$1110(TankGeneric.this) <= 0L) {
          TankGeneric.this.ShowExplode();
          TankGeneric.this.MakeCrush();
          return false;
        }

        if (TankGeneric.this.mov.rotatCurTime > 0L) {
          TankGeneric.this.mov.rotatCurTime -= 1L;

          float f1 = 1.0F - (float)TankGeneric.this.mov.rotatCurTime / (float)TankGeneric.this.mov.rotatTotTime;
          TankGeneric.this.pos.getAbs(TankGeneric.o);
          TankGeneric.o.setYaw(TankGeneric.this.mov.angles.getDeg(f1));

          if (TankGeneric.this.mov.normal.z < 0.0F) {
            Engine.land().N(TankGeneric.this.mov.srcPos.x, TankGeneric.this.mov.srcPos.y, TankGeneric.n);
            TankGeneric.o.orient(TankGeneric.n);
          } else {
            TankGeneric.o.orient(TankGeneric.this.mov.normal);
          }TankGeneric.this.pos.setAbs(TankGeneric.o);
        }

        return true;
      }

      int i = (TankGeneric.this.mov.moveCurTime < 0L) && (TankGeneric.this.mov.rotatCurTime < 0L) ? 1 : 0;
      if ((TankGeneric.this.isNetMirror()) && (i != 0))
      {
        TankGeneric.this.mov.switchToStay(30.0F);
        i = 0;
      }
      if (i != 0) {
        ChiefGround localChiefGround = (ChiefGround)TankGeneric.this.getOwner();

        float f3 = -1.0F;
        UnitMove localUnitMove;
        if (TankGeneric.this.collisionStage == 0) {
          if (TankGeneric.this.prop.meshName2 != null)
          {
            TankGeneric.p.x = TankGeneric.Rnd(-0.3D, 0.3D);
            TankGeneric.p.y = TankGeneric.Rnd(-0.3D, 0.3D);
            TankGeneric.p.z = 1.0D;
            localUnitMove = localChiefGround.AskMoveCommand(this.actor, TankGeneric.p, TankGeneric.this.obs);
          } else {
            localUnitMove = localChiefGround.AskMoveCommand(this.actor, null, TankGeneric.this.obs);
          }
        }
        else
        {
          float f4;
          Vector2d localVector2d;
          if (TankGeneric.this.collisionStage == 1)
          {
            TankGeneric.this.obs.collision(TankGeneric.this.collidee, localChiefGround, TankGeneric.this.udata);
            TankGeneric.access$1902(TankGeneric.this, null);

            f4 = TankGeneric.Rnd(-70.0F, 70.0F);
            localVector2d = TankGeneric.Rotate(TankGeneric.this.collisVector, f4);
            localVector2d.scale(TankGeneric.this.prop.AFTER_COLLISION_DIST * TankGeneric.Rnd(0.87D, 1.75D));
            TankGeneric.p.set(localVector2d.x, localVector2d.y, -1.0D);
            localUnitMove = localChiefGround.AskMoveCommand(this.actor, TankGeneric.p, TankGeneric.this.obs);
            TankGeneric.access$1602(TankGeneric.this, 2);
            f3 = TankGeneric.this.prop.SPEED_BACK;
          }
          else {
            f4 = TankGeneric.Rnd(0.0F, 359.98999F);
            localVector2d = TankGeneric.Rotate(TankGeneric.this.collisVector, f4);
            localVector2d.scale(TankGeneric.this.prop.AFTER_COLLISION_DIST * TankGeneric.Rnd(0.2D, 0.6D));
            TankGeneric.p.set(localVector2d.x, localVector2d.y, 1.0D);
            localUnitMove = localChiefGround.AskMoveCommand(this.actor, TankGeneric.p, TankGeneric.this.obs);
            TankGeneric.access$1602(TankGeneric.this, 0);
          }
        }

        TankGeneric.this.mov.set(localUnitMove, this.actor, TankGeneric.this.prop.SPEED_MAX, f3, TankGeneric.this.prop.ROT_SPEED_MAX, TankGeneric.this.prop.ROT_INVIS_ANG);

        if (TankGeneric.this.StayWhenFire()) {
          if (TankGeneric.this.Head360()) {
            if (TankGeneric.this.aime.isInFiringMode()) {
              TankGeneric.this.mov.switchToStay(1.3F);
            }
          }
          else if (TankGeneric.this.aime.isInAimingMode()) {
            TankGeneric.this.mov.switchToStay(1.3F);
          }

        }

        if (TankGeneric.this.isNetMaster()) {
          TankGeneric.this.send_MoveCommand(TankGeneric.this.mov, f3);
        }

      }

      TankGeneric.this.aime.tick_();

      if (TankGeneric.this.dust != null) {
        TankGeneric.this.dust._setIntesity(TankGeneric.this.mov.movingForward ? 1.0F : 0.0F);
      }

      if (TankGeneric.this.mov.dstPos == null) {
        TankGeneric.this.mov.moveCurTime -= 1L;
        if ((TankGeneric.this.engineSFX != null) && 
          (TankGeneric.this.engineSTimer > 0)) {
          if (--TankGeneric.this.engineSTimer == 0) {
            TankGeneric.this.engineSFX.stop();
          }
        }

        return true;
      }

      if (TankGeneric.this.engineSFX != null) {
        if (TankGeneric.this.engineSTimer == 0) {
          TankGeneric.this.engineSFX.play();
          TankGeneric.this.engineSTimer = (int)TankGeneric.access$2300(TankGeneric.Rnd(10.0F, 12.0F));
        } else if (TankGeneric.this.engineSTimer < TankGeneric.this.ticksIn8secs) {
          TankGeneric.this.engineSTimer = (int)TankGeneric.access$2300(TankGeneric.Rnd(10.0F, 12.0F));
        }

      }

      TankGeneric.this.pos.getAbs(TankGeneric.o);
      int j = 0;

      if (TankGeneric.this.mov.rotatCurTime > 0L) {
        TankGeneric.this.mov.rotatCurTime -= 1L;

        float f2 = 1.0F - (float)TankGeneric.this.mov.rotatCurTime / (float)TankGeneric.this.mov.rotatTotTime;
        TankGeneric.o.setYaw(TankGeneric.this.mov.angles.getDeg(f2));
        j = 1;
        if (TankGeneric.this.mov.rotatCurTime <= 0L) {
          TankGeneric.this.mov.rotatCurTime = -1L;
          TankGeneric.this.mov.rotatingInPlace = false;
        }

      }

      if ((!TankGeneric.this.mov.rotatingInPlace) && (TankGeneric.this.mov.moveCurTime > 0L)) {
        TankGeneric.this.mov.moveCurTime -= 1L;

        double d = 1.0D - TankGeneric.this.mov.moveCurTime / TankGeneric.this.mov.moveTotTime;

        TankGeneric.p.x = (TankGeneric.this.mov.srcPos.x * (1.0D - d) + TankGeneric.this.mov.dstPos.x * d);
        TankGeneric.p.y = (TankGeneric.this.mov.srcPos.y * (1.0D - d) + TankGeneric.this.mov.dstPos.y * d);

        if (TankGeneric.this.mov.normal.z < 0.0F) {
          TankGeneric.p.z = (Engine.land().HQ(TankGeneric.p.x, TankGeneric.p.y) + TankGeneric.this.HeightAboveLandSurface());
          Engine.land().N(TankGeneric.p.x, TankGeneric.p.y, TankGeneric.n);
        }
        else {
          TankGeneric.p.z = (TankGeneric.this.mov.srcPos.z * (1.0D - d) + TankGeneric.this.mov.dstPos.z * d);
        }
        j = 0;
        TankGeneric.this.pos.setAbs(TankGeneric.p);
        if (TankGeneric.this.mov.moveCurTime <= 0L) {
          TankGeneric.this.mov.moveCurTime = -1L;
        }
      }

      if (TankGeneric.this.mov.normal.z < 0.0F)
      {
        if (j != 0) Engine.land().N(TankGeneric.this.mov.srcPos.x, TankGeneric.this.mov.srcPos.y, TankGeneric.n);

        TankGeneric.o.orient(TankGeneric.n);
      }
      else {
        TankGeneric.o.orient(TankGeneric.this.mov.normal);
      }
      TankGeneric.this.pos.setAbs(TankGeneric.o);
      return true;
    }
  }

  class Mirror extends ActorNet
  {
    NetMsgFiltered out = new NetMsgFiltered();

    private boolean handleGuaranted(NetMsgInput paramNetMsgInput) throws IOException {
      int i = paramNetMsgInput.readByte();

      if (isMirrored()) {
        int j = 0;
        if ((i == 68) || (i == 65)) {
          j = 1;
        }
        localObject2 = new NetMsgGuaranted(paramNetMsgInput, j);
        post((NetMsgGuaranted)localObject2);
      }

      Object localObject1 = TankGeneric.readPackedPos(paramNetMsgInput);
      Object localObject2 = TankGeneric.readPackedOri(paramNetMsgInput);
      float f1 = TankGeneric.readPackedAng(paramNetMsgInput);
      float f2 = TankGeneric.readPackedAng(paramNetMsgInput);

      TankGeneric.this.setPosition((Point3d)localObject1, (Orient)localObject2, f1, f2);
      TankGeneric.this.mov.switchToStay(20.0F);

      switch (i)
      {
      case 73:
      case 105:
        if (TankGeneric.this.dying != 0) {
          System.out.println("Tank is dead at init stage");
        }
        if (i != 105) break;
        TankGeneric.this.DieMirror(null, false); break;
      case 65:
        localObject1 = paramNetMsgInput.readNetObj();
        localObject2 = localObject1 == null ? null : ((ActorNet)localObject1).actor();
        TankGeneric.this.doAbsoluteDeath((Actor)localObject2);

        break;
      case 67:
        TankGeneric.this.doCollisionDeath();
        break;
      case 68:
        if (TankGeneric.this.dying != 0) break;
        localObject1 = paramNetMsgInput.readNetObj();
        localObject2 = localObject1 == null ? null : ((ActorNet)localObject1).actor();
        TankGeneric.this.DieMirror((Actor)localObject2, true);
        break;
      default:
        System.out.println("TankGeneric: Unknown G message (" + i + ")");
        return false;
      }

      return true;
    }

    private boolean handleNonguaranted(NetMsgInput paramNetMsgInput) throws IOException
    {
      int i = paramNetMsgInput.readByte();
      NetObj localNetObj;
      Object localObject;
      float f2;
      switch (i)
      {
      case 68:
        this.out.unLockAndSet(paramNetMsgInput, 1);
        this.out.setIncludeTime(false);
        postRealTo(Message.currentRealTime(), masterChannel(), this.out);
        break;
      case 84:
        if (isMirrored()) {
          this.out.unLockAndSet(paramNetMsgInput, 1);
          this.out.setIncludeTime(false);
          postReal(Message.currentRealTime(), this.out);
        }

        localNetObj = paramNetMsgInput.readNetObj();
        localObject = localNetObj == null ? null : ((ActorNet)localNetObj).actor();

        int k = paramNetMsgInput.readUnsignedByte();

        TankGeneric.this.Track_Mirror((Actor)localObject, k);

        break;
      case 70:
        if (isMirrored()) {
          this.out.unLockAndSet(paramNetMsgInput, 1);
          this.out.setIncludeTime(true);
          postReal(Message.currentRealTime(), this.out);
        }

        localNetObj = paramNetMsgInput.readNetObj();
        localObject = localNetObj == null ? null : ((ActorNet)localNetObj).actor();

        float f1 = paramNetMsgInput.readFloat();
        f2 = 0.001F * (float)(Message.currentGameTime() - Time.current()) + f1;

        int n = paramNetMsgInput.readUnsignedByte();

        TankGeneric.this.Fire_Mirror((Actor)localObject, n, f2);

        break;
      case 83:
        if (isMirrored()) {
          this.out.unLockAndSet(paramNetMsgInput, 0);
          this.out.setIncludeTime(false);
          postReal(Message.currentRealTime(), this.out);
        }

        TankGeneric.this.mov.switchToStay(10.0F);
        break;
      case 77:
      case 109:
        int j = i == 77 ? 1 : 0;

        if (isMirrored()) {
          this.out.unLockAndSet(paramNetMsgInput, 0);
          this.out.setIncludeTime(true);
          postReal(Message.currentRealTime(), this.out);
        }

        localObject = TankGeneric.readPackedPos(paramNetMsgInput);
        Vector3f localVector3f = new Vector3f(0.0F, 0.0F, 0.0F);
        localVector3f.z = TankGeneric.readPackedNormal(paramNetMsgInput);
        if (localVector3f.z >= 0.0F) {
          localVector3f.x = TankGeneric.readPackedNormal(paramNetMsgInput);
          localVector3f.y = TankGeneric.readPackedNormal(paramNetMsgInput);
          f2 = localVector3f.length();
          if (f2 > 0.001F)
            localVector3f.scale(1.0F / f2);
          else {
            localVector3f.set(0.0F, 0.0F, 1.0F);
          }
        }
        int m = paramNetMsgInput.readUnsignedShort();
        float f3 = 0.001F * (float)(Message.currentGameTime() - Time.current() + m);

        if (f3 <= 0.0F) {
          f3 = 0.1F;
        }

        UnitMove localUnitMove = new UnitMove(0.0F, (Point3d)localObject, f3, localVector3f, -1.0F);

        if (TankGeneric.this.dying != 0) break;
        TankGeneric.this.mov.set(localUnitMove, actor(), 2.0F * TankGeneric.this.prop.SPEED_MAX, j != 0 ? 2.0F * TankGeneric.this.prop.SPEED_BACK : -1.0F, 1.3F * TankGeneric.this.prop.ROT_SPEED_MAX, 1.1F * TankGeneric.this.prop.ROT_INVIS_ANG); break;
      default:
        System.out.println("TankGeneric: Unknown NG message");
        return false;
      }

      return true;
    }

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException
    {
      if (paramNetMsgInput.isGuaranted()) {
        return handleGuaranted(paramNetMsgInput);
      }
      return handleNonguaranted(paramNetMsgInput);
    }

    public Mirror(Actor paramNetChannel, NetChannel paramInt, int arg4)
    {
      super(paramInt, i);
    }
  }

  class Master extends ActorNet
  {
    public Master(Actor arg2)
    {
      super();
    }

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException
    {
      if (paramNetMsgInput.isGuaranted()) {
        return false;
      }

      int i = paramNetMsgInput.readByte();
      switch (i) {
      case 68:
        if (TankGeneric.this.dying != 0) {
          break;
        }
        NetObj localNetObj = paramNetMsgInput.readNetObj();
        Actor localActor = localNetObj == null ? null : ((ActorNet)localNetObj).actor();
        TankGeneric.this.Die(localActor, true);
        break;
      case 67:
        TankGeneric.this.collisionDeath();
        break;
      default:
        System.out.println("TankGeneric: Unknown M message (" + i + ")");
        return false;
      }

      return true;
    }
  }

  protected static class TankProperties
    implements Cloneable
  {
    public int codeName = 0;

    public String meshName = "3do/tanks/NameNotSpecified.him";
    public String meshName2 = null;

    public String meshSummer = null;
    public String meshDesert = null;
    public String meshWinter = null;

    public String meshSummer1 = null;
    public String meshDesert1 = null;
    public String meshWinter1 = null;

    public Class gunClass = null;

    public String soundMove = "models.Tank";

    public TableFunction2 fnShotPanzer = null;
    public TableFunction2 fnExplodePanzer = null;

    public float PANZER_BODY_FRONT = 0.001F;
    public float PANZER_BODY_BACK = 0.001F;
    public float PANZER_BODY_SIDE = 0.001F;
    public float PANZER_BODY_TOP = 0.001F;
    public float PANZER_HEAD = 0.001F;
    public float PANZER_HEAD_TOP = 0.001F;

    public float PANZER_TNT_TYPE = 1.0F;

    public int WEAPONS_MASK = 4;
    public int HITBY_MASK = -2;

    public String explodeName = null;

    public int MAX_SHELLS = 1;
    public float ATTACK_MAX_DISTANCE = 1.0F;
    public float GUN_MIN_PITCH = -30.0F;
    public float GUN_STD_PITCH = -29.5F;
    public float GUN_MAX_PITCH = -29.0F;
    public AnglesRange HEAD_YAW_RANGE = new AnglesRange(-1.0F, 1.0F);
    public float HEAD_MAX_YAW_SPEED = 3600.0F;
    public float GUN_MAX_PITCH_SPEED = 300.0F;
    public float DELAY_AFTER_SHOOT = 0.5F;
    public float CHAINFIRE_TIME = 0.0F;
    public boolean ATTACK_FAST_TARGETS = true;
    public float FAST_TARGETS_ANGLE_ERROR = 0.0F;

    public boolean STAY_WHEN_FIRE = true;

    public float SPEED_AVERAGE = TankGeneric.KmHourToMSec(1.0F);
    public float SPEED_MAX = TankGeneric.KmHourToMSec(2.0F);
    public float SPEED_BACK = TankGeneric.KmHourToMSec(1.0F);
    public float ROT_SPEED_MAX = 3.6F;
    public float ROT_INVIS_ANG = 360.0F;

    public float BEST_SPACE = 2.0F;

    public float AFTER_COLLISION_DIST = 0.1F;

    public float COMMAND_INTERVAL = 20.0F;

    public float STAY_INTERVAL = 30.0F;

    public Object clone() {
      try {
        return super.clone(); } catch (Exception localException) {
      }
      return null;
    }
  }
}