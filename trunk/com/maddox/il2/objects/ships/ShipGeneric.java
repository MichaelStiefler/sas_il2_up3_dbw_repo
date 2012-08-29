package com.maddox.il2.objects.ships;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Aimer;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.AnglesForkExtended;
import com.maddox.il2.ai.AnglesRange;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.StrengthProperties;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Aim;
import com.maddox.il2.ai.ground.HunterInterface;
import com.maddox.il2.ai.ground.NearestEnemies;
import com.maddox.il2.ai.ground.Predator;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorException;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.engine.VisibilityLong;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.weapons.CannonMidrangeGeneric;
import com.maddox.il2.objects.weapons.Gun;
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
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ShipGeneric extends ActorHMesh
  implements MsgCollisionRequestListener, MsgCollisionListener, MsgExplosionListener, MsgShotListener, Predator, ActorAlign, HunterInterface, VisibilityLong
{
  private ShipProperties prop = null;

  private FiringDevice[] arms = null;
  private ArrayList path;
  private int cachedSeg = 0;
  private float bodyDepth;
  private float bodyYaw;
  private float bodyPitch;
  private float bodyRoll;
  private float seaDepth;
  private long timeOfDeath;
  private long timeForRotation;
  private float drownBodyPitch;
  private float drownBodyRoll;
  private float sinkingDepthSpeed;
  private float life = 1.0F;
  private int dying = 0;
  static final int DYING_NONE = 0;
  static final int DYING_SINK = 1;
  static final int DYING_DEAD = 2;
  private long respawnDelay = 0L;

  private long wakeupTmr = 0L;
  public float DELAY_WAKEUP = 0.0F;

  public int SKILL_IDX = 2;

  public float SLOWFIRE_K = 1.0F;

  private Eff3DActor pipe = null;

  private Eff3DActor[] wake = { null, null, null };

  private Eff3DActor nose = null;
  private Eff3DActor tail = null;

  private static ShipProperties constr_arg1 = null;
  private static ActorSpawnArg constr_arg2 = null;

  private static Point3d p = new Point3d();
  private static Point3d p1 = new Point3d();
  private static Point3d p2 = new Point3d();
  private static Orient o = new Orient();
  private static Vector3f n = new Vector3f();
  private static Vector3d tmpv = new Vector3d();

  private NetMsgFiltered outCommand = new NetMsgFiltered();

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
  private static final long SecsToTicks(float paramFloat) {
    long l = ()(0.5D + paramFloat / Time.tickLenFs());
    return l < 1L ? 1L : l;
  }

  protected final boolean Head360(FiringDevice paramFiringDevice)
  {
    return this.prop.guns[paramFiringDevice.id].HEAD_YAW_RANGE.fullcircle();
  }

  public void msgCollisionRequest(Actor paramActor, boolean[] paramArrayOfBoolean)
  {
    if ((paramActor instanceof BridgeSegment)) {
      if (this.dying != 0) {
        paramArrayOfBoolean[0] = false;
      }
      return;
    }

    if ((this.path == null) && ((paramActor instanceof ActorMesh)) && (((ActorMesh)paramActor).isStaticPos()))
    {
      paramArrayOfBoolean[0] = false;
      return;
    }
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    if (this.dying != 0) {
      return;
    }

    if (isNetMirror()) {
      return;
    }

    if ((paramActor instanceof WeakBody)) {
      return;
    }

    if (((paramActor instanceof ShipGeneric)) || ((paramActor instanceof BigshipGeneric)) || ((paramActor instanceof BridgeSegment)))
    {
      Die(null, -1L, true);
    }
  }

  public void msgShot(Shot paramShot)
  {
    paramShot.bodyMaterial = 2;

    if (this.dying != 0) {
      return;
    }

    if (paramShot.power <= 0.0F) {
      return;
    }

    if ((isNetMirror()) && 
      (paramShot.isMirage())) {
      return;
    }

    if (this.wakeupTmr < 0L)
      this.wakeupTmr = SecsToTicks(Rnd(this.DELAY_WAKEUP, this.DELAY_WAKEUP * 1.2F));
    float f1;
    float f2;
    if (paramShot.powerType == 1)
    {
      f1 = this.prop.stre.EXPLHIT_MAX_TNT;
      f2 = this.prop.stre.EXPLHIT_MAX_TNT;
    } else {
      f1 = this.prop.stre.SHOT_MIN_ENERGY;
      f2 = this.prop.stre.SHOT_MAX_ENERGY;
    }

    float f3 = paramShot.power * Rnd(1.0F, 1.1F);
    if (f3 < f1)
    {
      return;
    }

    float f4 = f3 / f2;
    this.life -= f4;
    if (this.life > 0.0F) {
      return;
    }

    Die(paramShot.initiator, -1L, true);
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    if (this.dying != 0) {
      return;
    }

    if ((isNetMirror()) && 
      (paramExplosion.isMirage())) {
      return;
    }

    if (this.wakeupTmr < 0L) {
      this.wakeupTmr = SecsToTicks(Rnd(this.DELAY_WAKEUP, this.DELAY_WAKEUP * 1.2F));
    }

    float f1 = paramExplosion.power;

    if ((paramExplosion.powerType == 2) && (paramExplosion.chunkName != null))
      f1 *= 0.45F;
    float f3;
    float f2;
    if (paramExplosion.chunkName != null) {
      f3 = f1;

      f3 *= Rnd(1.0F, 1.1F);
      if (f3 < this.prop.stre.EXPLHIT_MIN_TNT) {
        return;
      }
      f2 = f3 / this.prop.stre.EXPLHIT_MAX_TNT;
    } else {
      f3 = paramExplosion.receivedTNT_1meter(this);

      f3 *= Rnd(1.0F, 1.1F);
      if (f3 < this.prop.stre.EXPLNEAR_MIN_TNT) {
        return;
      }
      f2 = f3 / this.prop.stre.EXPLNEAR_MAX_TNT;
    }

    this.life -= f2;
    if (this.life > 0.0F) {
      return;
    }

    Die(paramExplosion.initiator, -1L, true);
  }

  private float computeSeaDepth(Point3d paramPoint3d)
  {
    for (float f1 = 5.0F; f1 <= 355.0F; f1 += 10.0F) {
      for (float f2 = 0.0F; f2 < 360.0F; f2 += 30.0F) {
        float f3 = f1 * Geom.cosDeg(f2);
        float f4 = f1 * Geom.sinDeg(f2);
        f3 += (float)paramPoint3d.x;
        f4 += (float)paramPoint3d.y;
        if (!World.land().isWater(f3, f4)) {
          return 50.0F * (f1 / 355.0F);
        }
      }
    }
    return 1000.0F;
  }

  private void computeSinkingParams(long paramLong)
  {
    RangeRandom localRangeRandom = new RangeRandom(paramLong % 11073L);

    this.timeForRotation = (40000L + ()(localRangeRandom.nextFloat() * 0.0F));
    this.drownBodyPitch = (50.0F - localRangeRandom.nextFloat() * 20.0F);
    if (localRangeRandom.nextFloat() < 0.5F) this.drownBodyPitch = (-this.drownBodyPitch);
    this.drownBodyRoll = (30.0F - localRangeRandom.nextFloat() * 60.0F);

    this.sinkingDepthSpeed = (0.55F + localRangeRandom.nextFloat() * 0.0F);

    this.seaDepth = computeSeaDepth(this.pos.getAbsPoint());
    this.seaDepth *= (1.0F + localRangeRandom.nextFloat() * 0.2F);
  }

  private void showExplode()
  {
    if ((mesh() instanceof HierMesh))
    {
      Explosions.Antiaircraft_Explode(this.pos.getAbsPoint());
    }
  }

  private void Die(Actor paramActor, long paramLong, boolean paramBoolean)
  {
    if (this.dying != 0) {
      return;
    }

    if (paramLong < 0L) {
      if (isNetMirror()) {
        send_DeathRequest(paramActor);
        return;
      }

      paramLong = NetServerParams.getServerTime();
    }

    this.life = 0.0F;
    this.dying = 1;
    World.onActorDied(this, paramActor);
    forgetAllAiming();

    SetEffectsIntens(-1.0F);

    if (this.path != null) {
      this.bodyDepth = 0.0F;
      this.bodyPitch = (this.bodyRoll = 0.0F);
      setMovablePosition(paramLong);
    } else {
      this.bodyDepth = 0.0F;
      this.bodyPitch = (this.bodyRoll = 0.0F);
      setPosition();
    }
    this.pos.reset();

    computeSinkingParams(paramLong);

    if (Mission.isDeathmatch())
    {
      this.timeOfDeath = Time.current();
      if (!paramBoolean)
        this.timeOfDeath = 0L;
    }
    else {
      this.timeOfDeath = paramLong;
    }

    if (paramBoolean) {
      showExplode();
    }

    if (paramBoolean)
      send_DeathCommand(paramActor);
  }

  public void destroy()
  {
    if (isDestroyed()) {
      return;
    }
    eraseGuns();
    super.destroy();
  }

  private boolean isAnyEnemyNear()
  {
    NearestEnemies.set(WeaponsMask());
    Actor localActor = NearestEnemies.getAFoundEnemy(this.pos.getAbsPoint(), 2000.0D, getArmy());

    return localActor != null;
  }

  private final FiringDevice GetFiringDevice(Aim paramAim)
  {
    for (int i = 0; i < this.prop.nGuns; i++) {
      if ((this.arms[i] != null) && (this.arms[i].aime == paramAim)) {
        return this.arms[i];
      }
    }
    System.out.println("Internal error 1: Can't find ship gun.");
    return null;
  }

  private final ShipGunProperties GetGunProperties(Aim paramAim) {
    for (int i = 0; i < this.prop.nGuns; i++) {
      if (this.arms[i].aime == paramAim) {
        return this.prop.guns[this.arms[i].id];
      }
    }
    System.out.println("Internal error 2: Can't find ship gun.");
    return null;
  }

  private void setGunAngles(FiringDevice paramFiringDevice, float paramFloat1, float paramFloat2) {
    FiringDevice.access$202(paramFiringDevice, paramFloat1);
    FiringDevice.access$302(paramFiringDevice, paramFloat2);
    hierMesh().chunkSetAngles("Head" + paramFiringDevice.id, paramFiringDevice.headYaw, 0.0F, 0.0F);
    hierMesh().chunkSetAngles("Gun" + paramFiringDevice.id, -paramFiringDevice.gunPitch, 0.0F, 0.0F);
  }

  private void eraseGuns()
  {
    if (this.arms != null) {
      for (int i = 0; i < this.prop.nGuns; i++) {
        if (this.arms[i] != null) {
          if (this.arms[i].aime != null) {
            this.arms[i].aime.forgetAll();
            FiringDevice.access$102(this.arms[i], null);
          }
          if (this.arms[i].gun != null) {
            destroy(this.arms[i].gun);
            FiringDevice.access$402(this.arms[i], null);
          }
          this.arms[i] = null;
        }
      }
      this.arms = null;
    }
  }

  private void forgetAllAiming() {
    if (this.arms != null)
      for (int i = 0; i < this.prop.nGuns; i++)
        if ((this.arms[i] != null) && (this.arms[i].aime != null))
          this.arms[i].aime.forgetAiming();
  }

  private void CreateGuns()
  {
    this.arms = new FiringDevice[this.prop.nGuns];
    for (int i = 0; i < this.prop.nGuns; i++) {
      this.arms[i] = new FiringDevice();

      FiringDevice.access$002(this.arms[i], i);

      FiringDevice.access$402(this.arms[i], null);
      try {
        FiringDevice.access$402(this.arms[i], (Gun)this.prop.guns[i].gunClass.newInstance());
      } catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("Ship: Can't create gun '" + this.prop.guns[i].gunClass.getName() + "'");
      }

      this.arms[i].gun.set(this, "ShellStart" + i);
      this.arms[i].gun.loadBullets(-1);

      Loc localLoc = new Loc();

      hierMesh().setCurChunk("Head" + i);
      hierMesh().getChunkLocObj(localLoc);

      this.prop.guns[i].fireOffset = new Point3d();
      localLoc.get(this.prop.guns[i].fireOffset);

      this.prop.guns[i].fireOrient = new Orient();
      localLoc.get(this.prop.guns[i].fireOrient);

      FiringDevice.access$102(this.arms[i], new Aim(this, isNetMirror(), this.SLOWFIRE_K * this.prop.guns[i].DELAY_AFTER_SHOOT));
    }
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  private void setDefaultLivePose()
  {
    int i = mesh().hookFind("Ground_Level");
    if (i != -1) {
      Matrix4d localMatrix4d = new Matrix4d();
      hierMesh().hookMatrix(i, localMatrix4d);
    }

    if ((mesh() instanceof HierMesh)) {
      hierMesh().chunkSetAngles("Body", 0.0F, 0.0F, 0.0F);
    }
    for (int j = 0; j < this.prop.nGuns; j++) {
      setGunAngles(this.arms[j], this.prop.guns[j].HEAD_STD_YAW, this.prop.guns[j].GUN_STD_PITCH);
    }

    this.bodyDepth = 0.0F;
    align();
  }

  protected ShipGeneric()
  {
    this(constr_arg1, constr_arg2);
  }

  private ShipGeneric(ShipProperties paramShipProperties, ActorSpawnArg paramActorSpawnArg)
  {
    super(paramShipProperties.meshName);
    this.prop = paramShipProperties;

    paramActorSpawnArg.setStationary(this);

    this.path = null;

    collide(true);
    drawing(true);

    this.bodyDepth = 0.0F;
    this.bodyPitch = (this.bodyRoll = 0.0F);
    this.bodyYaw = paramActorSpawnArg.orient.getYaw();
    setPosition();
    this.pos.reset();

    createNetObject(paramActorSpawnArg.netChannel, paramActorSpawnArg.netIdRemote);

    this.SKILL_IDX = Chief.new_SKILL_IDX;
    this.SLOWFIRE_K = Chief.new_SLOWFIRE_K;
    this.DELAY_WAKEUP = Chief.new_DELAY_WAKEUP;
    this.wakeupTmr = 0L;
    CreateGuns();

    setDefaultLivePose();

    if ((!isNetMirror()) && (this.prop.nGuns > 0) && (this.DELAY_WAKEUP > 0.0F))
    {
      this.wakeupTmr = (-SecsToTicks(Rnd(2.0F, 7.0F)));
    }

    if (!interpEnd("move"))
      interpPut(new Move(), "move", Time.current(), null);
  }

  public void setMesh(String paramString)
  {
    super.setMesh(paramString);
    if (Config.cur.b3dgunners) return;
    mesh().materialReplaceToNull("Pilot1");
  }

  public ShipGeneric(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
  {
    try
    {
      int i = paramSectFile1.sectionIndex(paramString2);
      String str = paramSectFile1.var(i, 0);

      Object localObject2 = Spawn.get(str);
      if (localObject2 == null) {
        throw new ActorException("Ship: Unknown class of ship (" + str + ")");
      }

      this.prop = ((SPAWN)localObject2).proper;
      try
      {
        setMesh(this.prop.meshName);
      } catch (RuntimeException localRuntimeException) {
        super.destroy();
        throw localRuntimeException;
      }
      if (this.prop.soundName != null) newSound(this.prop.soundName, true);

      setName(paramString1);
      setArmy(paramInt);

      LoadPath(paramSectFile2, paramString3);

      this.cachedSeg = 0;
      this.bodyDepth = 0.0F;
      this.bodyPitch = (this.bodyRoll = 0.0F);

      setMovablePosition(NetServerParams.getServerTime());

      this.pos.reset();

      collide(true);
      drawing(true);

      this.pipe = null;
      Object localObject1;
      if (mesh().hookFind("Vapor") >= 0) {
        localObject1 = new HookNamed(this, "Vapor");
        this.pipe = Eff3DActor.New(this, (Hook)localObject1, null, 1.0F, "Effects/Smokes/SmokePipeShip.eff", -1.0F);
      }
       tmp345_344 = (this.wake[0] =  = null); this.wake[1] = tmp345_344; this.wake[2] = tmp345_344;
      this.tail = null;
      this.nose = null;
      int j = this.prop.SLIDER_DIST / 2.5F < 90.0F ? 1 : 0;
      if (mesh().hookFind("_Prop") >= 0) {
        localObject1 = new HookNamedZ0(this, "_Prop");
        this.tail = Eff3DActor.New(this, (Hook)localObject1, null, 1.0F, j != 0 ? "3DO/Effects/Tracers/ShipTrail/PropWakeBoat.eff" : "3DO/Effects/Tracers/ShipTrail/PropWake.eff", -1.0F);
      }

      if (mesh().hookFind("_Centre") >= 0)
      {
        Loc localLoc1 = new Loc();
        Loc localLoc2 = new Loc();
        HookNamed localHookNamed = new HookNamed(this, "_Left");
        localHookNamed.computePos(this, new Loc(), localLoc1);
        localObject3 = new HookNamed(this, "_Right");
        ((HookNamed)localObject3).computePos(this, new Loc(), localLoc2);
        float f1 = (float)localLoc1.getPoint().distance(localLoc2.getPoint());

        localObject1 = new HookNamedZ0(this, "_Centre");
        if (mesh().hookFind("_Prop") >= 0) {
          HookNamedZ0 localHookNamedZ0 = new HookNamedZ0(this, "_Prop");

          Loc localLoc3 = new Loc();
          ((HookNamed)localObject1).computePos(this, new Loc(), localLoc3);
          Loc localLoc4 = new Loc();
          localHookNamedZ0.computePos(this, new Loc(), localLoc4);
          float f2 = (float)localLoc3.getPoint().distance(localLoc4.getPoint());

          this.wake[0] = Eff3DActor.New(this, localHookNamedZ0, new Loc(-f2 * 0.33D, 0.0D, 0.0D, 0.0F, 30.0F, 0.0F), f1, j != 0 ? "3DO/Effects/Tracers/ShipTrail/WakeBoat.eff" : "3DO/Effects/Tracers/ShipTrail/Wake.eff", -1.0F);

          this.wake[1] = Eff3DActor.New(this, (Hook)localObject1, new Loc(f2 * 0.15D, 0.0D, 0.0D, 0.0F, 30.0F, 0.0F), f1, j != 0 ? "3DO/Effects/Tracers/ShipTrail/WakeBoatS.eff" : "3DO/Effects/Tracers/ShipTrail/WakeS.eff", -1.0F);

          this.wake[2] = Eff3DActor.New(this, (Hook)localObject1, new Loc(-f2 * 0.15D, 0.0D, 0.0D, 0.0F, 30.0F, 0.0F), f1, j != 0 ? "3DO/Effects/Tracers/ShipTrail/WakeBoatS.eff" : "3DO/Effects/Tracers/ShipTrail/WakeS.eff", -1.0F);
        }
        else
        {
          this.wake[0] = Eff3DActor.New(this, (Hook)localObject1, new Loc(-f1 * 0.3D, 0.0D, 0.0D, 0.0F, 30.0F, 0.0F), f1, this.prop.SLIDER_DIST / 2.5D < 50.0D ? "3DO/Effects/Tracers/ShipTrail/WakeBoat.eff" : "3DO/Effects/Tracers/ShipTrail/Wake.eff", -1.0F);
        }

      }

      if (mesh().hookFind("_Nose") >= 0) {
        localObject1 = new HookNamedZ0(this, "_Nose");
        this.nose = Eff3DActor.New(this, (Hook)localObject1, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 30.0F, 0.0F), 1.0F, "3DO/Effects/Tracers/ShipTrail/SideWave.eff", -1.0F);
      }

      SetEffectsIntens(0.0F);

      int k = Mission.cur().getUnitNetIdRemote(this);
      Object localObject3 = Mission.cur().getNetMasterChannel();
      if (localObject3 == null)
        this.net = new Master(this);
      else if (k != 0) {
        this.net = new Mirror(this, (NetChannel)localObject3, k);
      }

      this.SKILL_IDX = Chief.new_SKILL_IDX;
      this.SLOWFIRE_K = Chief.new_SLOWFIRE_K;
      this.DELAY_WAKEUP = Chief.new_DELAY_WAKEUP;
      this.wakeupTmr = 0L;
      CreateGuns();

      setDefaultLivePose();

      if ((!isNetMirror()) && (this.prop.nGuns > 0) && (this.DELAY_WAKEUP > 0.0F))
      {
        this.wakeupTmr = (-SecsToTicks(Rnd(2.0F, 7.0F)));
      }

      if (!interpEnd("move"))
        interpPut(new Move(), "move", Time.current(), null);
    }
    catch (Exception localException)
    {
      System.out.println("Ship creation failure:");
      System.out.println(localException.getMessage());
      localException.printStackTrace();
      throw new ActorException();
    }
  }

  private void SetEffectsIntens(float paramFloat)
  {
    if (this.dying != 0) {
      paramFloat = -1.0F;
    }

    if (this.pipe != null) {
      if (paramFloat >= 0.0F) {
        this.pipe._setIntesity(paramFloat);
      } else {
        this.pipe._finish();
        this.pipe = null;
      }
    }

    for (int i = 0; i < 3; i++) {
      if (this.wake[i] != null) {
        if (paramFloat >= 0.0F) {
          this.wake[i]._setIntesity(paramFloat);
        }
        else {
          this.wake[i]._finish();
          this.wake[i] = null;
        }
      }
    }

    if (this.nose != null) {
      if (paramFloat >= 0.0F) {
        this.nose._setIntesity(paramFloat);
      } else {
        this.nose._finish();
        this.nose = null;
      }
    }

    if (this.tail != null)
      if (paramFloat >= 0.0F) {
        this.tail._setIntesity(paramFloat);
      } else {
        this.tail._finish();
        this.tail = null;
      }
  }

  private void LoadPath(SectFile paramSectFile, String paramString)
  {
    int i = paramSectFile.sectionIndex(paramString);
    if (i < 0) {
      throw new ActorException("Ship path: Section [" + paramString + "] not found");
    }
    int j = paramSectFile.vars(i);
    if (j < 1) {
      throw new ActorException("Ship path must contain at least 2 nodes");
    }

    this.path = new ArrayList();
    Object localObject;
    float f5;
    for (int k = 0; k < j; k++) {
      localObject = new StringTokenizer(paramSectFile.line(i, k));
      float f2 = Float.valueOf(((StringTokenizer)localObject).nextToken()).floatValue();
      float f3 = Float.valueOf(((StringTokenizer)localObject).nextToken()).floatValue();
      f5 = Float.valueOf(((StringTokenizer)localObject).nextToken()).floatValue();
      double d = 0.0D;
      float f7 = 0.0F;
      if (((StringTokenizer)localObject).hasMoreTokens()) {
        d = Double.valueOf(((StringTokenizer)localObject).nextToken()).doubleValue();
        if (((StringTokenizer)localObject).hasMoreTokens()) {
          Double.valueOf(((StringTokenizer)localObject).nextToken()).doubleValue();
          if (((StringTokenizer)localObject).hasMoreTokens()) {
            f7 = Float.valueOf(((StringTokenizer)localObject).nextToken()).floatValue();
          }
        }
      }
      if (k >= j - 1) d = 1.0D;

      Segment localSegment6 = new Segment(null);
      localSegment6.posIn = new Point3d(f2, f3, 0.0D);

      if (Math.abs(d) < 0.1D)
        localSegment6.timeIn = 0L;
      else {
        localSegment6.timeIn = ()(d * 60.0D * 1000.0D + (d > 0.0D ? 0.5D : -0.5D));
      }
      if ((f7 <= 0.0F) && ((k == 0) || (k == j - 1) || (localSegment6.timeIn == 0L))) {
        f7 = this.prop.SPEED;
      }
      localSegment6.speedIn = f7;

      this.path.add(localSegment6);
    }
    Segment localSegment2;
    for (k = 0; k < this.path.size() - 1; k++) {
      localObject = (Segment)this.path.get(k);
      Segment localSegment1 = (Segment)this.path.get(k + 1);

      if ((((Segment)localObject).timeIn > 0L) && (localSegment1.timeIn > 0L)) {
        localSegment2 = new Segment(null);
        localSegment2.posIn = new Point3d(((Segment)localObject).posIn);
        localSegment2.posIn.add(localSegment1.posIn);
        localSegment2.posIn.scale(0.5D);
        localSegment2.timeIn = 0L;
        localSegment2.speedIn = ((((Segment)localObject).speedIn + localSegment1.speedIn) * 0.5F);
        this.path.add(k + 1, localSegment2);
      }

    }

    k = 0;
    float f1 = ((Segment)this.path.get(k)).length;
    float f9;
    while (k < this.path.size() - 1) {
      int m = k + 1;
      while (true) {
        localSegment2 = (Segment)this.path.get(m);
        if (localSegment2.speedIn > 0.0D) {
          break;
        }
        f1 += localSegment2.length;
        m++;
      }

      if (m - k > 1) {
        float f4 = ((Segment)this.path.get(k)).length;
        f5 = ((Segment)this.path.get(k)).speedIn;
        float f6 = ((Segment)this.path.get(m)).speedIn;
        for (int i2 = k + 1; i2 < m; i2++) {
          Segment localSegment5 = (Segment)this.path.get(i2);
          f9 = f4 / f1;
          localSegment5.speedIn = (f5 * (1.0F - f9) + f6 * f9);
          f1 += localSegment5.length;
        }
      }

      k = m;
    }

    long l = 0L;
    for (int i1 = 0; i1 < this.path.size() - 1; i1++) {
      Segment localSegment3 = (Segment)this.path.get(i1);
      Segment localSegment4 = (Segment)this.path.get(i1 + 1);

      if (i1 == 0) {
        l = localSegment3.timeIn;
      }

      localSegment3.posOut = new Point3d(localSegment4.posIn);
      localSegment4.posIn = localSegment3.posOut;
      localSegment3.length = (float)localSegment3.posIn.distance(localSegment4.posIn);

      float f8 = localSegment3.speedIn;
      f9 = localSegment4.speedIn;
      float f10 = (f8 + f9) * 0.5F;

      if (localSegment3.timeIn > 0L) {
        if (localSegment3.timeIn > l)
          localSegment3.timeIn -= l;
        else {
          localSegment3.timeIn = 0L;
        }
      }

      if ((localSegment3.timeIn == 0L) && (localSegment4.timeIn > 0L)) {
        int i3 = (int)(2.0F * localSegment3.length / f8 * 1000.0F + 0.5F);
        i3 = (int)(i3 + l);
        if (localSegment4.timeIn > i3)
          localSegment4.timeIn -= i3;
        else
          localSegment4.timeIn = 0L;
      }
      float f11;
      if (localSegment3.timeIn > 0L) {
        localSegment3.speedIn = 0.0F;
        localSegment3.speedOut = f9;
        f11 = 2.0F * localSegment3.length / f9 * 1000.0F + 0.5F;
        localSegment3.timeIn = (l + localSegment3.timeIn);
        localSegment3.timeOut = (localSegment3.timeIn + (int)f11);
        l = localSegment3.timeOut;
      } else if (localSegment4.timeIn > 0L) {
        localSegment3.speedIn = f8;
        localSegment3.speedOut = 0.0F;
        f11 = 2.0F * localSegment3.length / f8 * 1000.0F + 0.5F;
        localSegment3.timeIn = (l + localSegment3.timeIn);
        localSegment3.timeOut = (localSegment3.timeIn + (int)f11);
        l = localSegment3.timeOut + localSegment4.timeIn;
      } else {
        localSegment3.speedIn = f8;
        localSegment3.speedOut = f9;
        f11 = localSegment3.length / f10 * 1000.0F + 0.5F;
        localSegment3.timeIn = l;
        localSegment3.timeOut = (localSegment3.timeIn + (int)f11);
        l = localSegment3.timeOut;
      }

    }

    this.path.remove(this.path.size() - 1);
  }

  public void align()
  {
    this.pos.getAbs(p);
    p.z = (Engine.land().HQ(p.x, p.y) - this.bodyDepth);
    this.pos.setAbs(p);
  }

  private void setMovablePosition(long paramLong)
  {
    if (this.cachedSeg < 0)
      this.cachedSeg = 0;
    else if (this.cachedSeg >= this.path.size()) {
      this.cachedSeg = (this.path.size() - 1);
    }
    Segment localSegment = (Segment)this.path.get(this.cachedSeg);

    if ((localSegment.timeIn <= paramLong) && (paramLong <= localSegment.timeOut))
    {
      SetEffectsIntens(1.0F);
      setMovablePosition((float)(paramLong - localSegment.timeIn) / (float)(localSegment.timeOut - localSegment.timeIn));
      return;
    }

    if (paramLong > localSegment.timeOut) {
      while (this.cachedSeg + 1 < this.path.size()) {
        localSegment = (Segment)this.path.get(++this.cachedSeg);
        if (paramLong <= localSegment.timeIn) {
          SetEffectsIntens(0.0F);
          setMovablePosition(0.0F);
          return;
        }
        if (paramLong <= localSegment.timeOut) {
          SetEffectsIntens(1.0F);
          setMovablePosition((float)(paramLong - localSegment.timeIn) / (float)(localSegment.timeOut - localSegment.timeIn));
          return;
        }
      }

      SetEffectsIntens(-1.0F);
      setMovablePosition(1.0F);
      return;
    }

    while (this.cachedSeg > 0) {
      localSegment = (Segment)this.path.get(--this.cachedSeg);
      if (paramLong >= localSegment.timeOut) {
        SetEffectsIntens(0.0F);
        setMovablePosition(1.0F);
        return;
      }
      if (paramLong >= localSegment.timeIn) {
        SetEffectsIntens(1.0F);
        setMovablePosition((float)(paramLong - localSegment.timeIn) / (float)(localSegment.timeOut - localSegment.timeIn));
        return;
      }
    }

    SetEffectsIntens(0.0F);
    setMovablePosition(0.0F);
  }

  private void setMovablePosition(float paramFloat)
  {
    Segment localSegment = (Segment)this.path.get(this.cachedSeg);

    float f1 = (float)(localSegment.timeOut - localSegment.timeIn) * 0.001F;
    float f2 = localSegment.speedIn;
    float f3 = localSegment.speedOut;
    float f4 = (f3 - f2) / f1;

    paramFloat *= f1;
    float f5 = f2 * paramFloat + f4 * paramFloat * paramFloat * 0.5F;

    int i = this.cachedSeg;
    float f6 = this.prop.SLIDER_DIST - (localSegment.length - f5);
    if (f6 <= 0.0F)
      p1.interpolate(localSegment.posIn, localSegment.posOut, (f5 + this.prop.SLIDER_DIST) / localSegment.length);
    else {
      while (true) {
        if (i + 1 >= this.path.size()) {
          p1.interpolate(localSegment.posIn, localSegment.posOut, 1.0F + f6 / localSegment.length);
          break;
        }
        i++; localSegment = (Segment)this.path.get(i);
        if (f6 <= localSegment.length) {
          p1.interpolate(localSegment.posIn, localSegment.posOut, f6 / localSegment.length);
          break;
        }
        f6 -= localSegment.length;
      }

    }

    i = this.cachedSeg;
    localSegment = (Segment)this.path.get(i);
    f6 = this.prop.SLIDER_DIST - f5;
    if (f6 <= 0.0F)
      p2.interpolate(localSegment.posIn, localSegment.posOut, (f5 - this.prop.SLIDER_DIST) / localSegment.length);
    else {
      while (true) {
        if (i <= 0) {
          p2.interpolate(localSegment.posIn, localSegment.posOut, 0.0F - f6 / localSegment.length);
          break;
        }
        i--; localSegment = (Segment)this.path.get(i);
        if (f6 <= localSegment.length) {
          p2.interpolate(localSegment.posIn, localSegment.posOut, 1.0F - f6 / localSegment.length);
          break;
        }
        f6 -= localSegment.length;
      }

    }

    p.interpolate(p1, p2, 0.5F);

    tmpv.sub(p1, p2);
    if (tmpv.lengthSquared() < 0.001000000047497451D) {
      localSegment = (Segment)this.path.get(this.cachedSeg);
      tmpv.sub(localSegment.posOut, localSegment.posIn);
    }
    float f7 = (float)(Math.atan2(tmpv.y, tmpv.x) * 57.295779513082323D);

    setPosition(p, f7);
  }

  private void setPosition(Point3d paramPoint3d, float paramFloat)
  {
    this.bodyYaw = paramFloat;

    o.setYPR(this.bodyYaw, this.bodyPitch, this.bodyRoll);

    paramPoint3d.z = (-this.bodyDepth);
    this.pos.setAbs(paramPoint3d, o);
  }

  private void setPosition()
  {
    o.setYPR(this.bodyYaw, this.bodyPitch, this.bodyRoll);
    this.pos.setAbs(o);

    align();
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

    if (paramArrayOfBulletProperties[0].powerType == 0)
    {
      return 0;
    }
    if (paramArrayOfBulletProperties[1].powerType == 0)
    {
      return 1;
    }

    if (paramArrayOfBulletProperties[0].powerType == 1)
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

  private void send_DeathCommand(Actor paramActor)
  {
    if (!isNetMaster()) {
      return;
    }

    if (Mission.isDeathmatch()) {
      float f = Mission.respawnTime("Ship");
      this.respawnDelay = SecsToTicks(Rnd(f, f * 1.2F));
    } else {
      this.respawnDelay = 0L;
    }

    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    try {
      localNetMsgGuaranted.writeByte(68);
      localNetMsgGuaranted.writeLong(this.timeOfDeath);
      localNetMsgGuaranted.writeNetObj(paramActor == null ? null : paramActor.net);
      this.net.post(localNetMsgGuaranted);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void send_RespawnCommand()
  {
    if ((!isNetMaster()) || (!Mission.isDeathmatch())) {
      return;
    }
    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    try {
      localNetMsgGuaranted.writeByte(82);
      this.net.post(localNetMsgGuaranted);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void send_FireCommand(int paramInt1, Actor paramActor, int paramInt2, float paramFloat)
  {
    if (!isNetMaster()) {
      return;
    }
    if (!this.net.isMirrored()) {
      return;
    }
    if ((!Actor.isValid(paramActor)) || (!paramActor.isNet())) {
      return;
    }

    paramInt2 &= 255;

    if (paramFloat < 0.0F)
      try {
        this.outCommand.unLockAndClear();
        this.outCommand.writeByte(84);
        this.outCommand.writeByte(paramInt1);
        this.outCommand.writeNetObj(paramActor.net);
        this.outCommand.writeByte(paramInt2);
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
        this.outCommand.writeByte(paramInt1);
        this.outCommand.writeFloat(paramFloat);
        this.outCommand.writeNetObj(paramActor.net);
        this.outCommand.writeByte(paramInt2);
        this.outCommand.setIncludeTime(true);
        this.net.post(Time.current(), this.outCommand);
      } catch (Exception localException2) {
        System.out.println(localException2.getMessage());
        localException2.printStackTrace();
      }
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

  public void createNetObject(NetChannel paramNetChannel, int paramInt)
  {
    if (paramNetChannel == null)
    {
      this.net = new Master(this);
    }
    else
      this.net = new Mirror(this, paramNetChannel, paramInt);
  }

  public void netFirstUpdate(NetChannel paramNetChannel)
    throws IOException
  {
    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    localNetMsgGuaranted.writeByte(73);
    if (this.dying == 0)
      localNetMsgGuaranted.writeLong(-1L);
    else {
      localNetMsgGuaranted.writeLong(this.timeOfDeath);
    }
    this.net.postTo(paramNetChannel, localNetMsgGuaranted);
  }

  public float getReloadingTime(Aim paramAim)
  {
    return this.SLOWFIRE_K * GetGunProperties(paramAim).DELAY_AFTER_SHOOT;
  }

  public float chainFireTime(Aim paramAim)
  {
    float f = GetGunProperties(paramAim).CHAINFIRE_TIME;
    return f <= 0.0F ? 0.0F : f * Rnd(0.75F, 1.25F);
  }

  public float probabKeepSameEnemy(Actor paramActor)
  {
    return 0.75F;
  }

  public float minTimeRelaxAfterFight()
  {
    return 0.1F;
  }

  public void gunStartParking(Aim paramAim)
  {
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);
    ShipGunProperties localShipGunProperties = this.prop.guns[localFiringDevice.id];
    paramAim.setRotationForParking(localFiringDevice.headYaw, localFiringDevice.gunPitch, localShipGunProperties.HEAD_STD_YAW, localShipGunProperties.GUN_STD_PITCH, localShipGunProperties.HEAD_YAW_RANGE, localShipGunProperties.HEAD_MAX_YAW_SPEED, localShipGunProperties.GUN_MAX_PITCH_SPEED);
  }

  public void gunInMove(boolean paramBoolean, Aim paramAim)
  {
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);

    float f1 = paramAim.t();
    float f2 = paramAim.anglesYaw.getDeg(f1);
    float f3 = paramAim.anglesPitch.getDeg(f1);

    setGunAngles(localFiringDevice, f2, f3);

    this.pos.inValidate(false);
  }

  public Actor findEnemy(Aim paramAim)
  {
    if (isNetMirror()) {
      return null;
    }

    ShipGunProperties localShipGunProperties = GetGunProperties(paramAim);

    Actor localActor = null;

    switch (localShipGunProperties.ATTACK_FAST_TARGETS) {
    case 0:
      NearestEnemies.set(localShipGunProperties.WEAPONS_MASK, -9999.9004F, KmHourToMSec(100.0F));
      break;
    case 1:
      NearestEnemies.set(localShipGunProperties.WEAPONS_MASK);
      break;
    default:
      NearestEnemies.set(localShipGunProperties.WEAPONS_MASK, KmHourToMSec(100.0F), 9999.9004F);
    }

    localActor = NearestEnemies.getAFoundEnemy(this.pos.getAbsPoint(), localShipGunProperties.ATTACK_MAX_RADIUS, getArmy());

    if (localActor == null) {
      return null;
    }

    if (!(localActor instanceof Prey)) {
      System.out.println("ship: nearest enemies: non-Prey");
      return null;
    }

    FiringDevice localFiringDevice = GetFiringDevice(paramAim);

    BulletProperties localBulletProperties = null;

    if (localFiringDevice.gun.prop != null) {
      i = ((Prey)localActor).chooseBulletType(localFiringDevice.gun.prop.bullet);

      if (i < 0)
      {
        return null;
      }

      localBulletProperties = localFiringDevice.gun.prop.bullet[i];
    }

    int i = ((Prey)localActor).chooseShotpoint(localBulletProperties);

    if (i < 0) {
      return null;
    }

    paramAim.shotpoint_idx = i;

    return localActor;
  }

  public boolean enterToFireMode(int paramInt, Actor paramActor, float paramFloat, Aim paramAim)
  {
    if (!isNetMirror()) {
      FiringDevice localFiringDevice = GetFiringDevice(paramAim);
      send_FireCommand(localFiringDevice.id, paramActor, paramAim.shotpoint_idx, paramInt == 0 ? -1.0F : paramFloat);
    }

    return true;
  }

  private void Track_Mirror(int paramInt1, Actor paramActor, int paramInt2)
  {
    if (paramActor == null) {
      return;
    }

    if ((this.arms == null) || (this.arms[paramInt1].aime == null))
    {
      return;
    }

    this.arms[paramInt1].aime.passive_StartFiring(0, paramActor, paramInt2, 0.0F);
  }

  private void Fire_Mirror(int paramInt1, Actor paramActor, int paramInt2, float paramFloat)
  {
    if (paramActor == null) {
      return;
    }

    if ((this.arms == null) || (this.arms[paramInt1].aime == null))
    {
      return;
    }

    if (paramFloat <= 0.2F) {
      paramFloat = 0.2F;
    }

    if (paramFloat >= 15.0F) {
      paramFloat = 15.0F;
    }

    this.arms[paramInt1].aime.passive_StartFiring(1, paramActor, paramInt2, paramFloat);
  }

  public int targetGun(Aim paramAim, Actor paramActor, float paramFloat, boolean paramBoolean)
  {
    if ((!Actor.isValid(paramActor)) || (!paramActor.isAlive()) || (paramActor.getArmy() == 0)) {
      return 0;
    }

    FiringDevice localFiringDevice = GetFiringDevice(paramAim);

    if ((localFiringDevice.gun instanceof CannonMidrangeGeneric)) {
      int i = ((Prey)paramActor).chooseBulletType(localFiringDevice.gun.prop.bullet);
      if (i < 0) {
        return 0;
      }
      ((CannonMidrangeGeneric)localFiringDevice.gun).setBulletType(i);
    }

    boolean bool = ((Prey)paramActor).getShotpointOffset(paramAim.shotpoint_idx, p1);
    if (!bool) {
      return 0;
    }

    ShipGunProperties localShipGunProperties = this.prop.guns[localFiringDevice.id];

    float f1 = paramFloat * Rnd(0.8F, 1.2F);

    if (!Aimer.Aim((BulletAimer)localFiringDevice.gun, paramActor, this, f1, p1, localShipGunProperties.fireOffset)) {
      return 0;
    }

    Point3d localPoint3d1 = new Point3d();
    Aimer.GetPredictedTargetPosition(localPoint3d1);

    Point3d localPoint3d2 = Aimer.GetHunterFirePoint();

    float f2 = 0.05F;

    double d1 = localPoint3d1.distance(localPoint3d2);
    double d2 = localPoint3d1.z;

    localPoint3d1.sub(localPoint3d2);
    localPoint3d1.scale(Rnd(0.995D, 1.005D));
    localPoint3d1.add(localPoint3d2);

    if (f1 > 0.001F) {
      Point3d localPoint3d3 = new Point3d();
      paramActor.pos.getAbs(localPoint3d3);

      tmpv.sub(localPoint3d1, localPoint3d3);
      double d3 = tmpv.length();

      if (d3 > 0.001D) {
        float f7 = (float)d3 / f1;
        if (f7 > 200.0F) {
          f7 = 200.0F;
        }
        float f8 = f7 * 0.01F;

        localPoint3d3.sub(localPoint3d2);
        double d4 = localPoint3d3.x * localPoint3d3.x + localPoint3d3.y * localPoint3d3.y + localPoint3d3.z * localPoint3d3.z;

        if (d4 > 0.01D) {
          float f9 = (float)tmpv.dot(localPoint3d3);
          f9 /= (float)(d3 * Math.sqrt(d4));

          f9 = (float)Math.sqrt(1.0F - f9 * f9);

          f8 *= (0.4F + 0.6F * f9);
        }
        f8 *= 1.3F;
        f8 *= Aim.AngleErrorKoefForSkill[this.SKILL_IDX];

        int k = Mission.curCloudsType();
        if (k > 2) {
          float f10 = k > 4 ? 400.0F : 800.0F;
          float f11 = (float)(d1 / f10);
          if (f11 > 1.0F) {
            if (f11 > 10.0F) {
              return 0;
            }
            f11 = (f11 - 1.0F) / 9.0F;
            f8 *= (f11 + 1.0F);
          }
        }

        if ((k >= 3) && (d2 > Mission.curCloudsHeight())) {
          f8 *= 1.25F;
        }

        f2 += f8;
      }

    }

    if (World.Sun().ToSun.z < -0.15F) {
      f5 = (-World.Sun().ToSun.z - 0.15F) / 0.13F;
      if (f5 >= 1.0F) {
        f5 = 1.0F;
      }

      if (((paramActor instanceof Aircraft)) && (Time.current() - ((Aircraft)paramActor).tmSearchlighted < 1000L))
      {
        f5 = 0.0F;
      }
      f2 += 10.0F * f5;
    }

    float f5 = (float)paramActor.getSpeed(null) - 10.0F;
    if (f5 > 0.0F) {
      float f6 = 83.333336F;
      f5 = f5 >= f6 ? 1.0F : f5 / f6;
      f2 += f5 * localShipGunProperties.FAST_TARGETS_ANGLE_ERROR;
    }

    Vector3d localVector3d = new Vector3d();
    if (!((BulletAimer)localFiringDevice.gun).FireDirection(localPoint3d2, localPoint3d1, localVector3d))
    {
      return 0;
    }
    float f3;
    float f4;
    if (paramBoolean) {
      f3 = 99999.0F;
      f4 = 99999.0F;
    } else {
      f3 = localShipGunProperties.HEAD_MAX_YAW_SPEED;
      f4 = localShipGunProperties.GUN_MAX_PITCH_SPEED;
    }

    o.add(localShipGunProperties.fireOrient, this.pos.getAbs().getOrient());
    int j = paramAim.setRotationForTargeting(this, o, localPoint3d2, localFiringDevice.headYaw, localFiringDevice.gunPitch, localVector3d, f2, f1, localShipGunProperties.HEAD_YAW_RANGE, localShipGunProperties.GUN_MIN_PITCH, localShipGunProperties.GUN_MAX_PITCH, f3, f4, 0.0F);

    return j;
  }

  public void singleShot(Aim paramAim)
  {
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);
    if (!this.prop.guns[localFiringDevice.id].TRACKING_ONLY)
      localFiringDevice.gun.shots(1);
  }

  public void startFire(Aim paramAim)
  {
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);
    if (!this.prop.guns[localFiringDevice.id].TRACKING_ONLY)
      localFiringDevice.gun.shots(-1);
  }

  public void continueFire(Aim paramAim)
  {
  }

  public void stopFire(Aim paramAim)
  {
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);
    if (!this.prop.guns[localFiringDevice.id].TRACKING_ONLY)
      localFiringDevice.gun.shots(0);
  }

  public boolean isVisibilityLong() {
    return true;
  }

  public int zutiGetDying()
  {
    return this.dying;
  }

  public boolean zutiIsStatic()
  {
    return (this.path == null) || (this.path.size() <= 0);
  }

  public static class SPAWN
    implements ActorSpawn
  {
    public Class cls;
    public ShipGeneric.ShipProperties proper;

    private static float getF(SectFile paramSectFile, String paramString1, String paramString2, float paramFloat1, float paramFloat2)
    {
      float f = paramSectFile.get(paramString1, paramString2, -9865.3447F);
      if ((f == -9865.3447F) || (f < paramFloat1) || (f > paramFloat2)) {
        if (f == -9865.3447F) {
          System.out.println("Ship: Value of [" + paramString1 + "]:<" + paramString2 + "> " + "not found");
        }
        else {
          System.out.println("Ship: Value of [" + paramString1 + "]:<" + paramString2 + "> (" + f + ")" + " is out of range (" + paramFloat1 + ";" + paramFloat2 + ")");
        }

        throw new RuntimeException("Can't set property");
      }
      return f;
    }

    private static String getS(SectFile paramSectFile, String paramString1, String paramString2) {
      String str = paramSectFile.get(paramString1, paramString2);
      if ((str == null) || (str.length() <= 0)) {
        System.out.print("Ship: Value of [" + paramString1 + "]:<" + paramString2 + "> not found");
        throw new RuntimeException("Can't set property");
      }
      return new String(str);
    }

    private static String getS(SectFile paramSectFile, String paramString1, String paramString2, String paramString3) {
      String str = paramSectFile.get(paramString1, paramString2);
      if ((str == null) || (str.length() <= 0)) {
        return paramString3;
      }
      return new String(str);
    }

    private static ShipGeneric.ShipProperties LoadShipProperties(SectFile paramSectFile, String paramString, Class paramClass)
    {
      ShipGeneric.ShipProperties localShipProperties = new ShipGeneric.ShipProperties();

      localShipProperties.meshName = getS(paramSectFile, paramString, "Mesh");
      localShipProperties.soundName = getS(paramSectFile, paramString, "SoundMove");
      if (localShipProperties.soundName.equalsIgnoreCase("none")) localShipProperties.soundName = null;

      if (!localShipProperties.stre.read("Ship", paramSectFile, null, paramString)) {
        throw new RuntimeException("Can't register Ship object");
      }

      int i = 0;
      while (paramSectFile.sectionIndex(paramString + ":Gun" + i) >= 0) {
        i++;
      }

      localShipProperties.guns = new ShipGeneric.ShipGunProperties[i];

      localShipProperties.WEAPONS_MASK = 0;
      localShipProperties.ATTACK_MAX_DISTANCE = 1.0F;

      for (int j = 0; j < i; j++) {
        localShipProperties.guns[j] = new ShipGeneric.ShipGunProperties();
        ShipGeneric.ShipGunProperties localShipGunProperties = localShipProperties.guns[j];

        String str1 = paramString + ":Gun" + j;

        String str2 = "com.maddox.il2.objects.weapons." + getS(paramSectFile, str1, "Gun");
        try
        {
          localShipGunProperties.gunClass = Class.forName(str2);
        } catch (Exception localException) {
          System.out.println("Ship: Can't find gun class '" + str2 + "'");

          throw new RuntimeException("Can't register Ship object");
        }

        localShipGunProperties.WEAPONS_MASK = Gun.getProperties(localShipGunProperties.gunClass).weaponType;
        if (localShipGunProperties.WEAPONS_MASK == 0) {
          System.out.println("Ship: Undefined weapon type in gun class '" + str2 + "'");

          throw new RuntimeException("Can't register Ship object");
        }

        localShipProperties.WEAPONS_MASK |= localShipGunProperties.WEAPONS_MASK;

        localShipGunProperties.ATTACK_MAX_DISTANCE = getF(paramSectFile, str1, "AttackMaxDistance", 6.0F, 50000.0F);
        localShipGunProperties.ATTACK_MAX_RADIUS = getF(paramSectFile, str1, "AttackMaxRadius", 6.0F, 50000.0F);
        localShipGunProperties.ATTACK_MAX_HEIGHT = getF(paramSectFile, str1, "AttackMaxHeight", 6.0F, 15000.0F);

        if (localShipGunProperties.ATTACK_MAX_DISTANCE > localShipProperties.ATTACK_MAX_DISTANCE) {
          localShipProperties.ATTACK_MAX_DISTANCE = localShipGunProperties.ATTACK_MAX_DISTANCE;
        }

        localShipGunProperties.TRACKING_ONLY = false;
        if (paramSectFile.exist(str1, "TrackingOnly")) {
          localShipGunProperties.TRACKING_ONLY = true;
        }

        localShipGunProperties.ATTACK_FAST_TARGETS = 1;
        if (paramSectFile.exist(str1, "FireFastTargets")) {
          f1 = getF(paramSectFile, str1, "FireFastTargets", 0.0F, 2.0F);
          localShipGunProperties.ATTACK_FAST_TARGETS = (int)(f1 + 0.5F);
          if (localShipGunProperties.ATTACK_FAST_TARGETS > 2) {
            localShipGunProperties.ATTACK_FAST_TARGETS = 2;
          }
        }

        localShipGunProperties.FAST_TARGETS_ANGLE_ERROR = 0.0F;
        if (paramSectFile.exist(str1, "FastTargetsAngleError")) {
          f1 = getF(paramSectFile, str1, "FastTargetsAngleError", 0.0F, 45.0F);
          localShipGunProperties.FAST_TARGETS_ANGLE_ERROR = f1;
        }

        float f1 = getF(paramSectFile, str1, "HeadMinYaw", -360.0F, 360.0F);
        float f2 = getF(paramSectFile, str1, "HeadStdYaw", -360.0F, 360.0F);
        float f3 = getF(paramSectFile, str1, "HeadMaxYaw", -360.0F, 360.0F);

        if (f1 > f3) {
          System.out.println("Ship: Wrong yaw angles in gun #" + j + " of '" + paramString + "'");

          throw new RuntimeException("Can't register Ship object");
        }
        if ((f2 < f1) || (f2 > f3))
        {
          System.out.println("Ship: Wrong std yaw angle in gun #" + j + " of '" + paramString + "'");
        }

        localShipGunProperties.HEAD_YAW_RANGE.set(f1, f3);
        localShipGunProperties.HEAD_STD_YAW = f2;

        localShipGunProperties.GUN_MIN_PITCH = getF(paramSectFile, str1, "GunMinPitch", -15.0F, 85.0F);
        localShipGunProperties.GUN_STD_PITCH = getF(paramSectFile, str1, "GunStdPitch", -14.0F, 89.900002F);
        localShipGunProperties.GUN_MAX_PITCH = getF(paramSectFile, str1, "GunMaxPitch", 0.0F, 89.900002F);
        localShipGunProperties.HEAD_MAX_YAW_SPEED = getF(paramSectFile, str1, "HeadMaxYawSpeed", 0.1F, 999.0F);
        localShipGunProperties.GUN_MAX_PITCH_SPEED = getF(paramSectFile, str1, "GunMaxPitchSpeed", 0.1F, 999.0F);
        localShipGunProperties.DELAY_AFTER_SHOOT = getF(paramSectFile, str1, "DelayAfterShoot", 0.0F, 999.0F);
        localShipGunProperties.CHAINFIRE_TIME = getF(paramSectFile, str1, "ChainfireTime", 0.0F, 600.0F);
      }
      localShipProperties.nGuns = i;

      localShipProperties.SLIDER_DIST = getF(paramSectFile, paramString, "SliderDistance", 5.0F, 1000.0F);

      localShipProperties.SPEED = ShipGeneric.KmHourToMSec(getF(paramSectFile, paramString, "Speed", 0.5F, 200.0F));

      localShipProperties.DELAY_RESPAWN_MIN = 15.0F;
      localShipProperties.DELAY_RESPAWN_MAX = 30.0F;

      Property.set(paramClass, "iconName", "icons/" + getS(paramSectFile, paramString, "Icon") + ".mat");
      Property.set(paramClass, "meshName", localShipProperties.meshName);
      Property.set(paramClass, "speed", localShipProperties.SPEED);

      return localShipProperties;
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
        this.proper = LoadShipProperties(Statics.getTechnicsFile(), str2, paramClass);
      }
      catch (Exception localException)
      {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("Problem in spawn: " + paramClass.getName());
      }

      this.cls = paramClass;
      Spawn.add(this.cls, this);
    }

    public Actor actorSpawn(ActorSpawnArg paramActorSpawnArg)
    {
      ShipGeneric localShipGeneric = null;
      try
      {
        ShipGeneric.access$3302(this.proper);
        ShipGeneric.access$3402(paramActorSpawnArg);
        localShipGeneric = (ShipGeneric)this.cls.newInstance();
        ShipGeneric.access$3302(null);
        ShipGeneric.access$3402(null);
      } catch (Exception localException) {
        ShipGeneric.access$3302(null);
        ShipGeneric.access$3402(null);
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("SPAWN: Can't create Ship object [class:" + this.cls.getName() + "]");

        return null;
      }
      return localShipGeneric;
    }
  }

  class Mirror extends ActorNet
  {
    NetMsgFiltered out = new NetMsgFiltered();

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      if (paramNetMsgInput.isGuaranted())
      {
        Object localObject;
        switch (paramNetMsgInput.readByte()) {
        case 73:
          if (isMirrored()) {
            localObject = new NetMsgGuaranted(paramNetMsgInput, 0);
            post((NetMsgGuaranted)localObject);
          }
          ShipGeneric.access$2402(ShipGeneric.this, paramNetMsgInput.readLong());
          if (ShipGeneric.this.timeOfDeath < 0L)
          {
            if (ShipGeneric.this.dying == 0) {
              ShipGeneric.access$1902(ShipGeneric.this, 1.0F);
              ShipGeneric.this.setDefaultLivePose();
              ShipGeneric.this.forgetAllAiming();
            }
          }
          else if (ShipGeneric.this.dying == 0) {
            ShipGeneric.this.Die(null, ShipGeneric.this.timeOfDeath, false);
          }

          return true;
        case 82:
          if (isMirrored()) {
            localObject = new NetMsgGuaranted(paramNetMsgInput, 0);
            post((NetMsgGuaranted)localObject);
          }
          ShipGeneric.access$1902(ShipGeneric.this, 1.0F);
          ShipGeneric.access$602(ShipGeneric.this, 0);

          ShipGeneric.this.setDiedFlag(false);

          ShipGeneric.this.setMesh(ShipGeneric.this.prop.meshName);
          ShipGeneric.this.setDefaultLivePose();
          ShipGeneric.this.forgetAllAiming();
          ShipGeneric.access$802(ShipGeneric.this, 0.0F);
          ShipGeneric.access$902(ShipGeneric.this, ShipGeneric.access$1002(ShipGeneric.this, 0.0F));
          ShipGeneric.this.setPosition();
          ShipGeneric.this.pos.reset();

          return true;
        case 68:
          if (isMirrored()) {
            localObject = new NetMsgGuaranted(paramNetMsgInput, 1);
            post((NetMsgGuaranted)localObject);
          }
          ShipGeneric.access$2402(ShipGeneric.this, paramNetMsgInput.readLong());
          if ((ShipGeneric.this.timeOfDeath >= 0L) && (ShipGeneric.this.dying == 0)) {
            localObject = paramNetMsgInput.readNetObj();
            Actor localActor2 = localObject == null ? null : ((ActorNet)localObject).actor();
            ShipGeneric.this.Die(localActor2, ShipGeneric.this.timeOfDeath, true);
          }
          return true;
        }
        return false;
      }
      int i;
      NetObj localNetObj;
      Actor localActor1;
      switch (paramNetMsgInput.readByte())
      {
      case 84:
        if (isMirrored()) {
          this.out.unLockAndSet(paramNetMsgInput, 1);
          this.out.setIncludeTime(false);
          postReal(Message.currentRealTime(), this.out);
        }

        i = paramNetMsgInput.readByte();

        localNetObj = paramNetMsgInput.readNetObj();
        localActor1 = localNetObj == null ? null : ((ActorNet)localNetObj).actor();

        int j = paramNetMsgInput.readUnsignedByte();

        ShipGeneric.this.Track_Mirror(i, localActor1, j);

        break;
      case 70:
        if (isMirrored()) {
          this.out.unLockAndSet(paramNetMsgInput, 1);
          this.out.setIncludeTime(true);
          postReal(Message.currentRealTime(), this.out);
        }

        i = paramNetMsgInput.readByte();

        localNetObj = paramNetMsgInput.readNetObj();
        localActor1 = localNetObj == null ? null : ((ActorNet)localNetObj).actor();

        float f1 = paramNetMsgInput.readFloat();
        float f2 = 0.001F * (float)(Message.currentGameTime() - Time.current()) + f1;

        int k = paramNetMsgInput.readUnsignedByte();

        ShipGeneric.this.Fire_Mirror(i, localActor1, k, f2);

        break;
      case 68:
        this.out.unLockAndSet(paramNetMsgInput, 1);
        this.out.setIncludeTime(false);
        postRealTo(Message.currentRealTime(), masterChannel(), this.out);
      }

      return true;
    }

    public Mirror(Actor paramNetChannel, NetChannel paramInt, int arg4) {
      super(paramInt, i);
    }
  }

  class Master extends ActorNet
  {
    public Master(Actor arg2)
    {
      super();
    }

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      if (paramNetMsgInput.isGuaranted()) {
        return true;
      }
      if (paramNetMsgInput.readByte() != 68)
        return false;
      if (ShipGeneric.this.dying != 0) {
        return true;
      }
      NetObj localNetObj = paramNetMsgInput.readNetObj();
      Actor localActor = localNetObj == null ? null : ((ActorNet)localNetObj).actor();
      ShipGeneric.this.Die(localActor, -1L, true);
      return true;
    }
  }

  class Move extends Interpolate
  {
    Move()
    {
    }

    public boolean tick()
    {
      if (ShipGeneric.this.dying == 0)
      {
        if (ShipGeneric.this.path != null) {
          ShipGeneric.access$802(ShipGeneric.this, 0.0F);
          ShipGeneric.access$902(ShipGeneric.this, ShipGeneric.access$1002(ShipGeneric.this, 0.0F));

          ShipGeneric.this.setMovablePosition(NetServerParams.getServerTime());
        }

        if (ShipGeneric.this.wakeupTmr == 0L)
        {
          for (int i = 0; i < ShipGeneric.this.prop.nGuns; i++) {
            ShipGeneric.FiringDevice.access$100(ShipGeneric.this.arms[i]).tick_();
          }
        }
        else if (ShipGeneric.this.wakeupTmr > 0L) {
          ShipGeneric.access$1210(ShipGeneric.this);
        }
        else if (ShipGeneric.access$1204(ShipGeneric.this) == 0L) {
          if (ShipGeneric.this.isAnyEnemyNear())
          {
            ShipGeneric.access$1202(ShipGeneric.this, ShipGeneric.access$1600(ShipGeneric.Rnd(ShipGeneric.this.DELAY_WAKEUP, ShipGeneric.this.DELAY_WAKEUP * 1.2F)));
          }
          else {
            ShipGeneric.access$1202(ShipGeneric.this, -ShipGeneric.access$1600(ShipGeneric.Rnd(4.0F, 7.0F)));
          }

        }

        return true;
      }

      if (ShipGeneric.this.dying == 2)
      {
        if ((ShipGeneric.this.path != null) || (!Mission.isDeathmatch()))
        {
          ShipGeneric.this.eraseGuns();
          return false;
        }

        if (ShipGeneric.access$1810(ShipGeneric.this) > 0L) {
          return true;
        }

        if (!ShipGeneric.this.isNetMaster()) {
          ShipGeneric.access$1802(ShipGeneric.this, 10000L);
          return true;
        }

        ShipGeneric.access$1902(ShipGeneric.this, 1.0F);
        ShipGeneric.access$602(ShipGeneric.this, 0);

        ShipGeneric.access$1202(ShipGeneric.this, 0L);

        ShipGeneric.this.setDiedFlag(false);

        ShipGeneric.this.forgetAllAiming();

        ShipGeneric.this.setDefaultLivePose();

        ShipGeneric.access$802(ShipGeneric.this, 0.0F);
        ShipGeneric.access$902(ShipGeneric.this, ShipGeneric.access$1002(ShipGeneric.this, 0.0F));
        ShipGeneric.this.setPosition();
        ShipGeneric.this.pos.reset();

        ShipGeneric.this.send_RespawnCommand();
        return true;
      }

      long l = NetServerParams.getServerTime() - ShipGeneric.this.timeOfDeath;

      if (l <= 0L) l = 0L;

      if (l >= ShipGeneric.this.timeForRotation) {
        ShipGeneric.access$902(ShipGeneric.this, ShipGeneric.this.drownBodyPitch);
        ShipGeneric.access$1002(ShipGeneric.this, ShipGeneric.this.drownBodyRoll);
      } else {
        ShipGeneric.access$902(ShipGeneric.this, ShipGeneric.this.drownBodyPitch * ((float)l / (float)ShipGeneric.this.timeForRotation));
        ShipGeneric.access$1002(ShipGeneric.this, ShipGeneric.this.drownBodyRoll * ((float)l / (float)ShipGeneric.this.timeForRotation));
      }

      ShipGeneric.access$802(ShipGeneric.this, ShipGeneric.this.sinkingDepthSpeed * (float)l * 0.001F * 5.0F);

      if (ShipGeneric.this.bodyDepth >= 5.0F) {
        float f = Math.abs(Geom.sinDeg(ShipGeneric.this.bodyPitch) * ShipGeneric.this.collisionR());
        f += ShipGeneric.this.bodyDepth;
        if (f + ShipGeneric.this.bodyDepth >= ShipGeneric.this.seaDepth) {
          ShipGeneric.access$602(ShipGeneric.this, 2);
        }
        if (ShipGeneric.this.bodyDepth > ShipGeneric.this.mesh().visibilityR()) {
          ShipGeneric.access$602(ShipGeneric.this, 2);
        }
      }

      if (ShipGeneric.this.path != null)
        ShipGeneric.this.setMovablePosition(ShipGeneric.this.timeOfDeath);
      else {
        ShipGeneric.this.setPosition();
      }

      return true;
    }
  }

  static class HookNamedZ0 extends HookNamed
  {
    public void computePos(Actor paramActor, Loc paramLoc1, Loc paramLoc2)
    {
      super.computePos(paramActor, paramLoc1, paramLoc2);
      paramLoc2.getPoint().z = 0.25D;
    }
    public HookNamedZ0(ActorMesh paramActorMesh, String paramString) { super(paramString); } 
    public HookNamedZ0(Mesh paramMesh, String paramString) { super(paramString);
    }
  }

  private class Segment
  {
    public Point3d posIn;
    public Point3d posOut;
    public float length;
    public long timeIn;
    public long timeOut;
    public float speedIn;
    public float speedOut;
    private final ShipGeneric this$0;

    private Segment()
    {
      this.this$0 = this$1; } 
    Segment(ShipGeneric.1 arg2) { this();
    }
  }

  public class FiringDevice
  {
    private int id;
    private Gun gun;
    private Aim aime;
    private float headYaw;
    private float gunPitch;

    public FiringDevice()
    {
    }
  }

  public static class ShipProperties
  {
    public String meshName = null;
    public String soundName = null;

    public StrengthProperties stre = new StrengthProperties();

    public int WEAPONS_MASK = 4;
    public int HITBY_MASK = -2;

    public float ATTACK_MAX_DISTANCE = 1.0F;

    public float SLIDER_DIST = 1.0F;

    public float SPEED = 1.0F;

    public float DELAY_RESPAWN_MIN = 15.0F;
    public float DELAY_RESPAWN_MAX = 30.0F;

    public ShipGeneric.ShipGunProperties[] guns = null;
    public int nGuns;
  }

  public static class ShipGunProperties
  {
    public Class gunClass = null;

    public int WEAPONS_MASK = 4;

    public boolean TRACKING_ONLY = false;

    public float ATTACK_MAX_DISTANCE = 1.0F;
    public float ATTACK_MAX_RADIUS = 1.0F;
    public float ATTACK_MAX_HEIGHT = 1.0F;

    public int ATTACK_FAST_TARGETS = 1;
    public float FAST_TARGETS_ANGLE_ERROR = 0.0F;

    public AnglesRange HEAD_YAW_RANGE = new AnglesRange(-1.0F, 1.0F);
    public float HEAD_STD_YAW = 0.0F;

    public float GUN_MIN_PITCH = -20.0F;
    public float GUN_STD_PITCH = -18.0F;
    public float GUN_MAX_PITCH = -15.0F;
    public float HEAD_MAX_YAW_SPEED = 720.0F;
    public float GUN_MAX_PITCH_SPEED = 60.0F;
    public float DELAY_AFTER_SHOOT = 1.0F;
    public float CHAINFIRE_TIME = 0.0F;
    public Point3d fireOffset;
    public Orient fireOrient;
  }
}