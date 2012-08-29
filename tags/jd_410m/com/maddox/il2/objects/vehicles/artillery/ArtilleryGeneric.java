package com.maddox.il2.objects.vehicles.artillery;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Aimer;
import com.maddox.il2.ai.Airport;
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
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.AirGroupList;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.ground.Aim;
import com.maddox.il2.ai.ground.HunterInterface;
import com.maddox.il2.ai.ground.NearestEnemies;
import com.maddox.il2.ai.ground.Obstacle;
import com.maddox.il2.ai.ground.Predator;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.ai.ground.TgtTank;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.vehicles.tanks.TankGeneric;
import com.maddox.il2.objects.weapons.CannonMidrangeGeneric;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.ObjState;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.util.TableFunction2;
import java.io.IOException;
import java.io.PrintStream;

public abstract class ArtilleryGeneric extends ActorHMesh
  implements MsgCollisionRequestListener, MsgExplosionListener, MsgShotListener, Predator, Obstacle, ActorAlign, HunterInterface
{
  private ArtilleryProperties prop = null;

  private boolean nearAirfield = false;
  private boolean dontShoot = false;
  private long time_lastCheckShoot = 0L;
  private static final int DELAY_CHECK_SHOOT = 12000;
  private static final int DIST_TO_FRIEND_PLANES = 4000;
  private static final int DIST_TO_AIRFIELD = 2000;
  private float heightAboveLandSurface;
  private float heightAboveLandSurface2;
  protected Gun gun;
  private Aim aime;
  private float headYaw;
  private float gunPitch;
  private long startDelay;
  public int dying = 0;
  static final int DYING_NONE = 0;
  static final int DYING_DEAD = 1;
  private short deathSeed;
  private long respawnDelay = 0L;

  private long hideTmr = 0L;
  private static long delay_hide_ticks = 0L;
  public float RADIUS_HIDE = 0.0F;

  public static float new_RADIUS_HIDE = 0.0F;

  private static ArtilleryProperties constr_arg1 = null;
  private static ActorSpawnArg constr_arg2 = null;

  private static Point3d p = new Point3d();
  private static Point3d p1 = new Point3d();
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

  private static final long SecsToTicks(float paramFloat) {
    long l = ()(0.5D + paramFloat / Time.tickLenFs());
    return l < 1L ? 1L : l;
  }

  private boolean friendPlanesAreNear(Aircraft paramAircraft)
  {
    this.time_lastCheckShoot = (Time.current() - ()Rnd(0.0F, 1200.0F));
    this.dontShoot = false;

    Point3d localPoint3d = paramAircraft.pos.getAbsPoint();
    double d1 = 16000000.0D;

    if (!(paramAircraft.FM instanceof Maneuver))
      return false;
    AirGroup localAirGroup1 = ((Maneuver)(Maneuver)paramAircraft.FM).Group;
    if (localAirGroup1 == null)
      return false;
    int i = AirGroupList.length(localAirGroup1.enemies[0]);
    for (int j = 0; j < i; j++) {
      AirGroup localAirGroup2 = AirGroupList.getGroup(localAirGroup1.enemies[0], j);
      if (localAirGroup2.nOfAirc > 0) {
        double d2 = localAirGroup2.Pos.x - localPoint3d.x;
        double d3 = localAirGroup2.Pos.y - localPoint3d.y;
        double d4 = localAirGroup2.Pos.z - localPoint3d.z;
        if (d2 * d2 + d3 * d3 + d4 * d4 > d1)
          continue;
        d4 = localPoint3d.z - Engine.land().HQ(localPoint3d.x, localPoint3d.y);
        if (d4 <= 50.0D)
          continue;
        this.dontShoot = true;
        break;
      }

    }

    return this.dontShoot;
  }

  protected final boolean Head360()
  {
    return this.prop.HEAD_YAW_RANGE.fullcircle();
  }

  public void msgCollisionRequest(Actor paramActor, boolean[] paramArrayOfBoolean)
  {
    if (((paramActor instanceof ActorMesh)) && (((ActorMesh)paramActor).isStaticPos())) {
      paramArrayOfBoolean[0] = false;
      return;
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

    if (paramShot.powerType == 1) {
      if (RndB(0.15F)) {
        return;
      }

      Die(paramShot.initiator, 0, true);
      return;
    }

    float f1 = Shot.panzerThickness(this.pos.getAbsOrient(), paramShot.v, paramShot.chunkName.equalsIgnoreCase("Head"), this.prop.PANZER_BODY_FRONT, this.prop.PANZER_BODY_SIDE, this.prop.PANZER_BODY_BACK, this.prop.PANZER_BODY_TOP, this.prop.PANZER_HEAD, this.prop.PANZER_HEAD_TOP);

    f1 *= Rnd(0.93F, 1.07F);

    float f2 = this.prop.fnShotPanzer.Value(paramShot.power, f1);

    if ((f2 < 1000.0F) && ((f2 <= 1.0F) || (RndB(1.0F / f2))))
      Die(paramShot.initiator, 0, true);
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
      if (TankGeneric.splintersKill(paramExplosion, this.prop.fnShotPanzer, Rnd(0.0F, 1.0F), Rnd(0.0F, 1.0F), this, 0.7F, 0.25F, this.prop.PANZER_BODY_FRONT, this.prop.PANZER_BODY_SIDE, this.prop.PANZER_BODY_BACK, this.prop.PANZER_BODY_TOP, this.prop.PANZER_HEAD, this.prop.PANZER_HEAD_TOP))
      {
        Die(paramExplosion.initiator, 0, true);
      }
      return;
    }

    if ((paramExplosion.powerType == 2) && (paramExplosion.chunkName != null)) {
      Die(paramExplosion.initiator, 0, true);
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
      Die(paramExplosion.initiator, 0, true);
  }

  private void ShowExplode(float paramFloat)
  {
    if (paramFloat > 0.0F) {
      paramFloat = Rnd(paramFloat, paramFloat * 1.6F);
    }
    Explosions.runByName(this.prop.explodeName, this, "SmokeHead", "", paramFloat);
  }

  private float[] computeDeathPose(short paramShort)
  {
    RangeRandom localRangeRandom = new RangeRandom(paramShort);

    float[] arrayOfFloat = new float[10];

    arrayOfFloat[0] = (this.headYaw + localRangeRandom.nextFloat(-15.0F, 15.0F));
    arrayOfFloat[1] = ((localRangeRandom.nextBoolean() ? 1.0F : -1.0F) * localRangeRandom.nextFloat(4.0F, 9.0F));
    arrayOfFloat[2] = ((localRangeRandom.nextBoolean() ? 1.0F : -1.0F) * localRangeRandom.nextFloat(4.0F, 9.0F));

    arrayOfFloat[3] = (-this.gunPitch + localRangeRandom.nextFloat(-15.0F, 15.0F));
    arrayOfFloat[4] = ((localRangeRandom.nextBoolean() ? 1.0F : -1.0F) * localRangeRandom.nextFloat(2.0F, 5.0F));
    arrayOfFloat[5] = ((localRangeRandom.nextBoolean() ? 1.0F : -1.0F) * localRangeRandom.nextFloat(5.0F, 9.0F));

    arrayOfFloat[6] = 0.0F;
    arrayOfFloat[7] = ((localRangeRandom.nextBoolean() ? 1.0F : -1.0F) * localRangeRandom.nextFloat(4.0F, 8.0F));
    arrayOfFloat[8] = ((localRangeRandom.nextBoolean() ? 1.0F : -1.0F) * localRangeRandom.nextFloat(7.0F, 12.0F));

    arrayOfFloat[9] = (-localRangeRandom.nextFloat(0.0F, 0.25F));

    return arrayOfFloat;
  }

  private void Die(Actor paramActor, short paramShort, boolean paramBoolean) {
    if (this.dying != 0) {
      return;
    }

    if (paramShort <= 0) {
      if (isNetMirror()) {
        send_DeathRequest(paramActor);
        return;
      }

      paramShort = (short)(int)Rnd(1.0F, 30000.0F);
    }
    this.deathSeed = paramShort;

    this.dying = 1;

    World.onActorDied(this, paramActor);

    if (this.aime != null) {
      this.aime.forgetAiming();
    }

    float[] arrayOfFloat = computeDeathPose(paramShort);

    hierMesh().chunkSetAngles("Head", arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2]);
    hierMesh().chunkSetAngles("Gun", arrayOfFloat[3], arrayOfFloat[4], arrayOfFloat[5]);
    hierMesh().chunkSetAngles("Body", arrayOfFloat[6], arrayOfFloat[7], arrayOfFloat[8]);

    if (this.prop.meshName2 == null)
    {
      mesh().makeAllMaterialsDarker(0.22F, 0.35F);
      this.heightAboveLandSurface2 = this.heightAboveLandSurface;
      this.heightAboveLandSurface = (this.heightAboveLandSurface2 + arrayOfFloat[9]);
    } else {
      setMesh(this.prop.meshName2);

      this.heightAboveLandSurface2 = 0.0F;

      int i = mesh().hookFind("Ground_Level");
      if (i != -1) {
        Matrix4d localMatrix4d = new Matrix4d();
        mesh().hookMatrix(i, localMatrix4d);
        this.heightAboveLandSurface2 = (float)(-localMatrix4d.m23);
      }

      this.heightAboveLandSurface = this.heightAboveLandSurface2;
    }

    Align();

    if (paramBoolean) {
      ShowExplode(15.0F);
    }

    if (paramBoolean)
      send_DeathCommand(paramActor);
  }

  private void setGunAngles(float paramFloat1, float paramFloat2)
  {
    this.headYaw = paramFloat1;
    this.gunPitch = paramFloat2;
    hierMesh().chunkSetAngles("Head", this.headYaw, 0.0F, 0.0F);
    hierMesh().chunkSetAngles("Gun", -this.gunPitch, 0.0F, 0.0F);
    hierMesh().chunkSetAngles("Body", 0.0F, 0.0F, 0.0F);
    this.pos.inValidate(false);
  }

  public void destroy()
  {
    if (isDestroyed())
      return;
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

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  public boolean isStaticPos() {
    return true;
  }

  private void setDefaultLivePose()
  {
    this.heightAboveLandSurface = 0.0F;
    int i = hierMesh().hookFind("Ground_Level");
    if (i != -1) {
      Matrix4d localMatrix4d = new Matrix4d();
      hierMesh().hookMatrix(i, localMatrix4d);
      this.heightAboveLandSurface = (float)(-localMatrix4d.m23);
    }
    setGunAngles(0.0F, this.prop.GUN_STD_PITCH);
    Align();
  }

  protected ArtilleryGeneric()
  {
    this(constr_arg1, constr_arg2);
  }

  public void setMesh(String paramString) {
    super.setMesh(paramString);
    if (Config.cur.b3dgunners) return;
    mesh().materialReplaceToNull("Pilot1");
  }

  private ArtilleryGeneric(ArtilleryProperties paramArtilleryProperties, ActorSpawnArg paramActorSpawnArg)
  {
    super(paramArtilleryProperties.meshName);
    this.prop = paramArtilleryProperties;

    delay_hide_ticks = SecsToTicks(240.0F);

    paramActorSpawnArg.setStationary(this);

    collide(true);
    drawing(true);

    createNetObject(paramActorSpawnArg.netChannel, paramActorSpawnArg.netIdRemote);

    this.startDelay = 0L;
    if (paramActorSpawnArg.timeLenExist) {
      this.startDelay = ()(paramActorSpawnArg.timeLen * 60.0F * 1000.0F + 0.5F);
      if (this.startDelay < 0L) this.startDelay = 0L;
    }

    this.RADIUS_HIDE = new_RADIUS_HIDE;
    this.hideTmr = 0L;

    this.gun = null;
    try {
      this.gun = ((Gun)this.prop.gunClass.newInstance());
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
      System.out.println("Artillery: Can't create gun '" + this.prop.gunClass.getName() + "'");
    }

    this.gun.set(this, "Gun");
    this.gun.loadBullets(-1);

    this.headYaw = 0.0F;
    this.gunPitch = 0.0F;

    if ((!isNetMirror()) && (this.RADIUS_HIDE > 0.0F)) {
      this.hideTmr = -1L;
    }

    setDefaultLivePose();
    startMove();

    Point3d localPoint3d = this.pos.getAbsPoint();
    Airport localAirport = Airport.nearest(localPoint3d, -1, 7);
    if (localAirport != null) {
      float f = (float)localAirport.pos.getAbsPoint().distance(localPoint3d);
      this.nearAirfield = (f <= 2000.0F);
    } else {
      this.nearAirfield = false;
    }

    this.dontShoot = false;
    this.time_lastCheckShoot = (Time.current() - ()Rnd(0.0F, 12000.0F));
  }

  private void Align() {
    this.pos.getAbs(p);
    p.z = (Engine.land().HQ(p.x, p.y) + this.heightAboveLandSurface);
    o.setYPR(this.pos.getAbsOrient().getYaw(), 0.0F, 0.0F);
    Engine.land().N(p.x, p.y, n);
    o.orient(n);
    this.pos.setAbs(p, o);
  }

  public void align()
  {
    Align();
  }

  public void startMove()
  {
    if (!interpEnd("move")) {
      if (this.aime != null) {
        this.aime.forgetAll();
        this.aime = null;
      }
      this.aime = new Aim(this, isNetMirror());
      interpPut(new Move(), "move", Time.current(), null);
    }
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

  public float AttackMaxRadius()
  {
    return this.prop.ATTACK_MAX_RADIUS;
  }

  public float AttackMaxHeight() {
    return this.prop.ATTACK_MAX_HEIGHT;
  }

  public boolean unmovableInFuture()
  {
    return true;
  }

  public void collisionDeath()
  {
    if (isNet()) {
      return;
    }

    ShowExplode(-1.0F);

    destroy();
  }

  public float futurePosition(float paramFloat, Point3d paramPoint3d)
  {
    this.pos.getAbs(paramPoint3d);
    return paramFloat <= 0.0F ? 0.0F : paramFloat;
  }

  private void send_DeathCommand(Actor paramActor)
  {
    if (!isNetMaster()) {
      return;
    }

    if (Mission.isDeathmatch()) {
      float f = Mission.respawnTime("Artillery");
      this.respawnDelay = SecsToTicks(Rnd(f, f * 1.2F));
    } else {
      this.respawnDelay = 0L;
    }

    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    try {
      localNetMsgGuaranted.writeByte(68);
      localNetMsgGuaranted.writeShort(this.deathSeed);
      localNetMsgGuaranted.writeFloat(this.headYaw);
      localNetMsgGuaranted.writeFloat(this.gunPitch);
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

  private void send_FireCommand(Actor paramActor, int paramInt, float paramFloat)
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
      localNetMsgGuaranted.writeShort(0);
    else {
      localNetMsgGuaranted.writeShort(this.deathSeed);
    }
    localNetMsgGuaranted.writeFloat(this.headYaw);
    localNetMsgGuaranted.writeFloat(this.gunPitch);
    this.net.postTo(paramNetChannel, localNetMsgGuaranted);
  }

  public float getReloadingTime(Aim paramAim)
  {
    return this.prop.DELAY_AFTER_SHOOT;
  }

  public float chainFireTime(Aim paramAim)
  {
    return this.prop.CHAINFIRE_TIME <= 0.0F ? 0.0F : this.prop.CHAINFIRE_TIME * Rnd(0.75F, 1.25F);
  }

  public float probabKeepSameEnemy(Actor paramActor)
  {
    if ((this.nearAirfield) || (isNetMirror()) || (paramActor == null) || (!(paramActor instanceof Aircraft)) || (Math.abs(this.time_lastCheckShoot - Time.current()) < 12000L) || ((float)paramActor.getSpeed(null) < 10.0F))
    {
      return 0.75F;
    }

    if (friendPlanesAreNear((Aircraft)paramActor)) {
      return 0.0F;
    }

    return 0.75F;
  }

  public float minTimeRelaxAfterFight()
  {
    return 0.0F;
  }

  public void gunStartParking(Aim paramAim)
  {
    paramAim.setRotationForParking(this.headYaw, this.gunPitch, 0.0F, this.prop.GUN_STD_PITCH, this.prop.HEAD_YAW_RANGE, this.prop.HEAD_MAX_YAW_SPEED, this.prop.GUN_MAX_PITCH_SPEED);
  }

  public void gunInMove(boolean paramBoolean, Aim paramAim)
  {
    float f1 = paramAim.t();
    float f2 = paramAim.anglesYaw.getDeg(f1);
    float f3 = paramAim.anglesPitch.getDeg(f1);
    setGunAngles(f2, f3);
  }

  public static final float KmHourToMSec(float paramFloat)
  {
    return paramFloat * 0.27778F;
  }

  public Actor findEnemy(Aim paramAim)
  {
    if (isNetMirror()) {
      return null;
    }

    if (Time.current() < this.startDelay) {
      return null;
    }

    Actor localActor = null;

    if (this.prop.ATTACK_FAST_TARGETS)
      NearestEnemies.set(WeaponsMask());
    else {
      NearestEnemies.set(WeaponsMask(), -9999.9004F, KmHourToMSec(100.0F));
    }

    localActor = NearestEnemies.getAFoundEnemy(this.pos.getAbsPoint(), AttackMaxRadius(), getArmy());

    if (localActor == null) {
      return null;
    }

    if (!(localActor instanceof Prey)) {
      System.out.println("arti: nearest enemies: non-Prey");
      return null;
    }

    int i = 1;
    if ((!this.nearAirfield) && (!isNetMirror()) && ((localActor instanceof Aircraft)) && ((float)localActor.getSpeed(null) >= 10.0F))
    {
      if (Math.abs(this.time_lastCheckShoot - Time.current()) < 12000L)
      {
        if (this.dontShoot) {
          return null;
        }

      }
      else if (friendPlanesAreNear((Aircraft)localActor)) {
        return null;
      }

    }

    BulletProperties localBulletProperties = null;

    if (this.gun.prop != null) {
      j = ((Prey)localActor).chooseBulletType(this.gun.prop.bullet);

      if (j < 0)
      {
        return null;
      }

      localBulletProperties = this.gun.prop.bullet[j];
    }

    int j = ((Prey)localActor).chooseShotpoint(localBulletProperties);

    if (j < 0) {
      return null;
    }

    paramAim.shotpoint_idx = j;

    return localActor;
  }

  public boolean enterToFireMode(int paramInt, Actor paramActor, float paramFloat, Aim paramAim)
  {
    if ((paramInt == 1) && (this.hideTmr < 0L))
    {
      float f = (float)paramActor.pos.getAbsPoint().distanceSquared(this.pos.getAbsPoint());
      if (f > this.RADIUS_HIDE * this.RADIUS_HIDE) {
        return false;
      }
      this.hideTmr = 0L;
    }

    if (!isNetMirror()) {
      send_FireCommand(paramActor, paramAim.shotpoint_idx, paramInt == 0 ? -1.0F : paramFloat);
    }

    return true;
  }

  private void Track_Mirror(Actor paramActor, int paramInt)
  {
    if (this.dying == 1) {
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
    if (this.dying == 1) {
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
    if ((!Actor.isValid(paramActor)) || (!paramActor.isAlive()) || (paramActor.getArmy() == 0)) {
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

    if (!Aimer.Aim((BulletAimer)this.gun, paramActor, this, f1, p1, null)) {
      return 0;
    }

    Point3d localPoint3d1 = new Point3d();
    Aimer.GetPredictedTargetPosition(localPoint3d1);

    Point3d localPoint3d2 = Aimer.GetHunterFirePoint();

    float f2 = 0.19F;

    double d1 = localPoint3d1.distance(localPoint3d2);
    double d2 = localPoint3d1.z;

    localPoint3d1.sub(localPoint3d2);
    localPoint3d1.scale(Rnd(0.96D, 1.04D));
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
        float f8 = f7 * 0.015F;

        localPoint3d3.sub(localPoint3d2);
        double d4 = localPoint3d3.x * localPoint3d3.x + localPoint3d3.y * localPoint3d3.y + localPoint3d3.z * localPoint3d3.z;

        if (d4 > 0.01D) {
          float f9 = (float)tmpv.dot(localPoint3d3);
          f9 /= (float)(d3 * Math.sqrt(d4));

          f9 = (float)Math.sqrt(1.0F - f9 * f9);

          f8 *= (0.4F + 0.6F * f9);
        }
        f8 *= 1.1F;

        int k = 0;

        k = Mission.curCloudsType();
        if (k > 2) {
          float f10 = k > 4 ? 300.0F : 500.0F;
          float f11 = (float)(d1 / f10);
          if (f11 > 1.0F) {
            if (f11 > 10.0F) {
              return 0;
            }
            f11 = (f11 - 1.0F) / 9.0F * 2.0F + 1.0F;
            f8 *= f11;
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
      f2 += 12.0F * f5;
    }

    float f5 = (float)paramActor.getSpeed(null) - 10.0F;
    if (f5 > 0.0F) {
      float f6 = 83.333336F;
      f5 = f5 >= f6 ? 1.0F : f5 / f6;
      f2 += f5 * this.prop.FAST_TARGETS_ANGLE_ERROR;
    }

    Vector3d localVector3d = new Vector3d();
    if (!((BulletAimer)this.gun).FireDirection(localPoint3d2, localPoint3d1, localVector3d))
    {
      return 0;
    }
    float f3;
    float f4;
    if (paramBoolean) {
      f3 = 99999.0F;
      f4 = 99999.0F;
    } else {
      f3 = this.prop.HEAD_MAX_YAW_SPEED;
      f4 = this.prop.GUN_MAX_PITCH_SPEED;
    }

    int j = paramAim.setRotationForTargeting(this, this.pos.getAbs().getOrient(), localPoint3d2, this.headYaw, this.gunPitch, localVector3d, f2, f1, this.prop.HEAD_YAW_RANGE, this.prop.GUN_MIN_PITCH, this.prop.GUN_MAX_PITCH, f3, f4, 0.0F);

    return j;
  }

  public void singleShot(Aim paramAim)
  {
    this.gun.shots(1);
  }

  public void startFire(Aim paramAim)
  {
    this.gun.shots(-1);
  }

  public void continueFire(Aim paramAim)
  {
  }

  public void stopFire(Aim paramAim)
  {
    this.gun.shots(0);
  }

  public static class SPAWN
    implements ActorSpawn
  {
    public Class cls;
    public ArtilleryGeneric.ArtilleryProperties proper;

    private static float getF(SectFile paramSectFile, String paramString1, String paramString2, float paramFloat1, float paramFloat2)
    {
      float f = paramSectFile.get(paramString1, paramString2, -9865.3447F);
      if ((f == -9865.3447F) || (f < paramFloat1) || (f > paramFloat2)) {
        if (f == -9865.3447F) {
          System.out.println("Artillery: Parameter [" + paramString1 + "]:<" + paramString2 + "> " + "not found");
        }
        else {
          System.out.println("Artillery: Value of [" + paramString1 + "]:<" + paramString2 + "> (" + f + ")" + " is out of range (" + paramFloat1 + ";" + paramFloat2 + ")");
        }

        throw new RuntimeException("Can't set property");
      }
      return f;
    }

    private static String getS(SectFile paramSectFile, String paramString1, String paramString2) {
      String str = paramSectFile.get(paramString1, paramString2);
      if ((str == null) || (str.length() <= 0)) {
        System.out.print("Artillery: Parameter [" + paramString1 + "]:<" + paramString2 + "> ");
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

    private static ArtilleryGeneric.ArtilleryProperties LoadArtilleryProperties(SectFile paramSectFile, String paramString, Class paramClass)
    {
      ArtilleryGeneric.ArtilleryProperties localArtilleryProperties = new ArtilleryGeneric.ArtilleryProperties();

      String str1 = getS(paramSectFile, paramString, "PanzerType", null);
      if (str1 == null) {
        str1 = "Tank";
      }
      localArtilleryProperties.fnShotPanzer = TableFunctions.GetFunc2(str1 + "ShotPanzer");
      localArtilleryProperties.fnExplodePanzer = TableFunctions.GetFunc2(str1 + "ExplodePanzer");

      localArtilleryProperties.PANZER_TNT_TYPE = getF(paramSectFile, paramString, "PanzerSubtype", 0.0F, 100.0F);

      localArtilleryProperties.meshSummer = getS(paramSectFile, paramString, "MeshSummer");
      localArtilleryProperties.meshDesert = getS(paramSectFile, paramString, "MeshDesert", localArtilleryProperties.meshSummer);
      localArtilleryProperties.meshWinter = getS(paramSectFile, paramString, "MeshWinter", localArtilleryProperties.meshSummer);
      localArtilleryProperties.meshSummer1 = getS(paramSectFile, paramString, "MeshSummerDamage", null);
      localArtilleryProperties.meshDesert1 = getS(paramSectFile, paramString, "MeshDesertDamage", localArtilleryProperties.meshSummer1);
      localArtilleryProperties.meshWinter1 = getS(paramSectFile, paramString, "MeshWinterDamage", localArtilleryProperties.meshSummer1);

      int i = (localArtilleryProperties.meshSummer1 == null ? 1 : 0) + (localArtilleryProperties.meshDesert1 == null ? 1 : 0) + (localArtilleryProperties.meshWinter1 == null ? 1 : 0);

      if ((i != 0) && (i != 3)) {
        System.out.println("Artillery: Uncomplete set of damage meshes for '" + paramString + "'");

        throw new RuntimeException("Can't register artillery object");
      }

      localArtilleryProperties.PANZER_BODY_FRONT = getF(paramSectFile, paramString, "PanzerBodyFront", 0.001F, 9.999F);

      if (paramSectFile.get(paramString, "PanzerBodyBack", -9865.3447F) == -9865.3447F) {
        localArtilleryProperties.PANZER_BODY_BACK = localArtilleryProperties.PANZER_BODY_FRONT;
        localArtilleryProperties.PANZER_BODY_SIDE = localArtilleryProperties.PANZER_BODY_FRONT;
        localArtilleryProperties.PANZER_BODY_TOP = localArtilleryProperties.PANZER_BODY_FRONT;
      } else {
        localArtilleryProperties.PANZER_BODY_BACK = getF(paramSectFile, paramString, "PanzerBodyBack", 0.001F, 9.999F);
        localArtilleryProperties.PANZER_BODY_SIDE = getF(paramSectFile, paramString, "PanzerBodySide", 0.001F, 9.999F);
        localArtilleryProperties.PANZER_BODY_TOP = getF(paramSectFile, paramString, "PanzerBodyTop", 0.001F, 9.999F);
      }

      if (paramSectFile.get(paramString, "PanzerHead", -9865.3447F) == -9865.3447F)
        localArtilleryProperties.PANZER_HEAD = localArtilleryProperties.PANZER_BODY_FRONT;
      else {
        localArtilleryProperties.PANZER_HEAD = getF(paramSectFile, paramString, "PanzerHead", 0.001F, 9.999F);
      }

      if (paramSectFile.get(paramString, "PanzerHeadTop", -9865.3447F) == -9865.3447F)
        localArtilleryProperties.PANZER_HEAD_TOP = localArtilleryProperties.PANZER_BODY_TOP;
      else {
        localArtilleryProperties.PANZER_HEAD_TOP = getF(paramSectFile, paramString, "PanzerHeadTop", 0.001F, 9.999F);
      }

      float f1 = Math.min(Math.min(localArtilleryProperties.PANZER_BODY_BACK, localArtilleryProperties.PANZER_BODY_TOP), Math.min(localArtilleryProperties.PANZER_BODY_SIDE, localArtilleryProperties.PANZER_HEAD_TOP));

      localArtilleryProperties.HITBY_MASK = (f1 > 0.015F ? -2 : -1);

      localArtilleryProperties.explodeName = getS(paramSectFile, paramString, "Explode", "Artillery");

      String str2 = "com.maddox.il2.objects.weapons." + getS(paramSectFile, paramString, "Gun");
      try
      {
        localArtilleryProperties.gunClass = Class.forName(str2);
      } catch (Exception localException) {
        System.out.println("Artillery: Can't find gun class '" + str2 + "'");

        throw new RuntimeException("Can't register artillery object");
      }

      localArtilleryProperties.WEAPONS_MASK = Gun.getProperties(localArtilleryProperties.gunClass).weaponType;
      if (localArtilleryProperties.WEAPONS_MASK == 0) {
        System.out.println("Artillery: Undefined weapon type in gun class '" + str2 + "'");

        throw new RuntimeException("Can't register artillery object");
      }

      localArtilleryProperties.ATTACK_FAST_TARGETS = true;
      float f2 = paramSectFile.get(paramString, "FireFastTargets", -9865.3447F);
      if (f2 != -9865.3447F) {
        localArtilleryProperties.ATTACK_FAST_TARGETS = (f2 > 0.5F);
      }
      else if (str1.equals("Tank"))
      {
        localArtilleryProperties.ATTACK_FAST_TARGETS = false;
      }

      localArtilleryProperties.ATTACK_MAX_DISTANCE = getF(paramSectFile, paramString, "AttackMaxDistance", 6.0F, 12000.0F);
      localArtilleryProperties.ATTACK_MAX_RADIUS = getF(paramSectFile, paramString, "AttackMaxRadius", 6.0F, 12000.0F);
      localArtilleryProperties.ATTACK_MAX_HEIGHT = getF(paramSectFile, paramString, "AttackMaxHeight", 6.0F, 12000.0F);

      float f3 = getF(paramSectFile, paramString, "HeadYawHalfRange", 0.0F, 180.0F);
      localArtilleryProperties.HEAD_YAW_RANGE.set(-f3, f3);
      localArtilleryProperties.GUN_MIN_PITCH = getF(paramSectFile, paramString, "GunMinPitch", -15.0F, 85.0F);
      localArtilleryProperties.GUN_STD_PITCH = getF(paramSectFile, paramString, "GunStdPitch", -15.0F, 89.900002F);
      localArtilleryProperties.GUN_MAX_PITCH = getF(paramSectFile, paramString, "GunMaxPitch", 0.0F, 89.900002F);
      localArtilleryProperties.HEAD_MAX_YAW_SPEED = getF(paramSectFile, paramString, "HeadMaxYawSpeed", 0.1F, 999.0F);
      localArtilleryProperties.GUN_MAX_PITCH_SPEED = getF(paramSectFile, paramString, "GunMaxPitchSpeed", 0.1F, 999.0F);
      localArtilleryProperties.DELAY_AFTER_SHOOT = getF(paramSectFile, paramString, "DelayAfterShoot", 0.0F, 999.0F);
      localArtilleryProperties.CHAINFIRE_TIME = getF(paramSectFile, paramString, "ChainfireTime", 0.0F, 600.0F);

      float f4 = paramSectFile.get(paramString, "FastTargetsAngleError", -9865.3447F);
      if (f4 <= 0.0F)
        f4 = 0.0F;
      else if (f4 >= 45.0F) {
        f4 = 45.0F;
      }
      localArtilleryProperties.FAST_TARGETS_ANGLE_ERROR = f4;

      Property.set(paramClass, "iconName", "icons/" + getS(paramSectFile, paramString, "Icon") + ".mat");
      Property.set(paramClass, "meshName", localArtilleryProperties.meshSummer);

      return localArtilleryProperties;
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
        this.proper = LoadArtilleryProperties(Statics.getTechnicsFile(), str2, paramClass);
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

      ArtilleryGeneric localArtilleryGeneric = null;
      try
      {
        ArtilleryGeneric.access$1302(this.proper);
        ArtilleryGeneric.access$1402(paramActorSpawnArg);
        localArtilleryGeneric = (ArtilleryGeneric)this.cls.newInstance();
        ArtilleryGeneric.access$1302(null);
        ArtilleryGeneric.access$1402(null);
      } catch (Exception localException) {
        ArtilleryGeneric.access$1302(null);
        ArtilleryGeneric.access$1402(null);
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("SPAWN: Can't create Artillery object [class:" + this.cls.getName() + "]");

        return null;
      }
      return localArtilleryGeneric;
    }
  }

  class Mirror extends ActorNet
  {
    NetMsgFiltered out = new NetMsgFiltered();

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      if (paramNetMsgInput.isGuaranted())
      {
        Object localObject;
        short s;
        float f1;
        float f2;
        switch (paramNetMsgInput.readByte()) {
        case 73:
          if (isMirrored()) {
            localObject = new NetMsgGuaranted(paramNetMsgInput, 0);
            post((NetMsgGuaranted)localObject);
          }
          s = paramNetMsgInput.readShort();
          f1 = paramNetMsgInput.readFloat();
          f2 = paramNetMsgInput.readFloat();
          if (s <= 0)
          {
            if (ArtilleryGeneric.this.dying != 1) {
              ArtilleryGeneric.this.aime.forgetAiming();
              ArtilleryGeneric.this.setGunAngles(f1, f2);
            }
          }
          else if (ArtilleryGeneric.this.dying != 1) {
            ArtilleryGeneric.this.setGunAngles(f1, f2);
            ArtilleryGeneric.this.Die(null, s, false);
          }

          return true;
        case 82:
          if (isMirrored()) {
            localObject = new NetMsgGuaranted(paramNetMsgInput, 0);
            post((NetMsgGuaranted)localObject);
          }
          ArtilleryGeneric.this.dying = 0;

          ArtilleryGeneric.this.setDiedFlag(false);

          ArtilleryGeneric.this.aime.forgetAiming();

          ArtilleryGeneric.this.setMesh(ArtilleryGeneric.this.prop.meshName);
          ArtilleryGeneric.this.setDefaultLivePose();
          return true;
        case 68:
          if (isMirrored()) {
            localObject = new NetMsgGuaranted(paramNetMsgInput, 1);
            post((NetMsgGuaranted)localObject);
          }
          s = paramNetMsgInput.readShort();
          f1 = paramNetMsgInput.readFloat();
          f2 = paramNetMsgInput.readFloat();
          if ((s > 0) && (ArtilleryGeneric.this.dying != 1)) {
            ArtilleryGeneric.this.setGunAngles(f1, f2);
            localObject = paramNetMsgInput.readNetObj();
            Actor localActor2 = localObject == null ? null : ((ActorNet)localObject).actor();
            ArtilleryGeneric.this.Die(localActor2, s, true);
          }
          return true;
        }
        return false;
      }
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

        localNetObj = paramNetMsgInput.readNetObj();
        localActor1 = localNetObj == null ? null : ((ActorNet)localNetObj).actor();

        int i = paramNetMsgInput.readUnsignedByte();

        ArtilleryGeneric.this.Track_Mirror(localActor1, i);

        break;
      case 70:
        if (isMirrored()) {
          this.out.unLockAndSet(paramNetMsgInput, 1);
          this.out.setIncludeTime(true);
          postReal(Message.currentRealTime(), this.out);
        }

        localNetObj = paramNetMsgInput.readNetObj();
        localActor1 = localNetObj == null ? null : ((ActorNet)localNetObj).actor();

        float f3 = paramNetMsgInput.readFloat();
        float f4 = 0.001F * (float)(Message.currentGameTime() - Time.current()) + f3;

        int j = paramNetMsgInput.readUnsignedByte();

        ArtilleryGeneric.this.Fire_Mirror(localActor1, j, f4);

        break;
      case 68:
        this.out.unLockAndSet(paramNetMsgInput, 1);
        this.out.setIncludeTime(false);
        postRealTo(Message.currentRealTime(), masterChannel(), this.out);
        return true;
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
      if (ArtilleryGeneric.this.dying == 1) {
        return true;
      }
      NetObj localNetObj = paramNetMsgInput.readNetObj();
      Actor localActor = localNetObj == null ? null : ((ActorNet)localNetObj).actor();
      ArtilleryGeneric.this.Die(localActor, 0, true);
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
      if (ArtilleryGeneric.this.dying == 1) {
        if (ArtilleryGeneric.access$010(ArtilleryGeneric.this) <= 0L) {
          if (!Mission.isDeathmatch())
          {
            if (ArtilleryGeneric.this.aime != null) {
              ArtilleryGeneric.this.aime.forgetAll();
              ArtilleryGeneric.access$102(ArtilleryGeneric.this, null);
            }
            if (ArtilleryGeneric.this.gun != null) {
              ObjState.destroy(ArtilleryGeneric.this.gun);
              ArtilleryGeneric.this.gun = null;
            }
            return false;
          }
          if (!ArtilleryGeneric.this.isNetMaster()) {
            ArtilleryGeneric.access$002(ArtilleryGeneric.this, 10000L);
            return true;
          }

          ArtilleryGeneric.this.dying = 0;

          ArtilleryGeneric.access$202(ArtilleryGeneric.this, 0L);
          if ((!ArtilleryGeneric.this.isNetMirror()) && (ArtilleryGeneric.this.RADIUS_HIDE > 0.0F)) {
            ArtilleryGeneric.access$202(ArtilleryGeneric.this, -1L);
          }

          ArtilleryGeneric.this.setDiedFlag(false);

          ArtilleryGeneric.this.aime.forgetAiming();

          ArtilleryGeneric.this.setMesh(ArtilleryGeneric.this.prop.meshName);
          ArtilleryGeneric.this.setDefaultLivePose();
          ArtilleryGeneric.this.send_RespawnCommand();

          ArtilleryGeneric.access$602(ArtilleryGeneric.this, false);
          ArtilleryGeneric.access$702(ArtilleryGeneric.this, Time.current() - 12000L);
          return true;
        }
        return true;
      }

      ArtilleryGeneric.this.aime.tick_();

      if ((ArtilleryGeneric.this.RADIUS_HIDE > 0.0F) && (ArtilleryGeneric.this.hideTmr >= 0L) && (!ArtilleryGeneric.this.isNetMirror()))
      {
        if (ArtilleryGeneric.this.aime.getEnemy() != null)
        {
          ArtilleryGeneric.access$202(ArtilleryGeneric.this, 0L);
        }
        else if (ArtilleryGeneric.access$204(ArtilleryGeneric.this) > ArtilleryGeneric.delay_hide_ticks) {
          ArtilleryGeneric.access$202(ArtilleryGeneric.this, -1L);
        }

      }

      return true;
    }
  }

  public static class ArtilleryProperties
  {
    public String meshName = null;
    public String meshName2 = null;

    public String meshSummer = null;
    public String meshDesert = null;
    public String meshWinter = null;

    public String meshSummer1 = null;
    public String meshDesert1 = null;
    public String meshWinter1 = null;

    public Class gunClass = null;

    public TableFunction2 fnShotPanzer = null;
    public TableFunction2 fnExplodePanzer = null;

    public float PANZER_BODY_FRONT = 0.001F;
    public float PANZER_BODY_BACK = 0.001F;
    public float PANZER_BODY_SIDE = 0.001F;
    public float PANZER_BODY_TOP = 0.001F;
    public float PANZER_HEAD = 0.001F;
    public float PANZER_HEAD_TOP = 0.001F;

    public float PANZER_TNT_TYPE = 1.0F;

    public String explodeName = null;

    public int WEAPONS_MASK = 4;
    public int HITBY_MASK = -2;

    public boolean ATTACK_FAST_TARGETS = true;

    public float ATTACK_MAX_DISTANCE = 1.0F;
    public float ATTACK_MAX_RADIUS = 1.0F;
    public float ATTACK_MAX_HEIGHT = 1.0F;

    public AnglesRange HEAD_YAW_RANGE = new AnglesRange(-1.0F, 1.0F);
    public float GUN_MIN_PITCH = -20.0F;
    public float GUN_STD_PITCH = -18.0F;
    public float GUN_MAX_PITCH = -15.0F;
    public float HEAD_MAX_YAW_SPEED = 720.0F;
    public float GUN_MAX_PITCH_SPEED = 60.0F;
    public float DELAY_AFTER_SHOOT = 1.0F;
    public float CHAINFIRE_TIME = 0.0F;
    public float FAST_TARGETS_ANGLE_ERROR = 0.0F;
  }
}