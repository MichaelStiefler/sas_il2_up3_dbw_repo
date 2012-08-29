package com.maddox.il2.objects.air;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.Front;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.UnitInterface;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorMeshDraw;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.FObj;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.ShipGeneric;
import com.maddox.rts.Message;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.Property;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import java.io.IOException;
import java.io.PrintStream;

public class Paratrooper extends ActorMesh
  implements MsgCollisionRequestListener, MsgCollisionListener, MsgExplosionListener, MsgShotListener
{
  private static final int FPS = 30;
  private static final int FREEFLY_START_FRAME = 0;
  private static final int FREEFLY_LAST_FRAME = 19;
  private static final int FREEFLY_N_FRAMES = 20;
  private static final int FREEFLY_CYCLE_TIME = 633;
  private static final int FREEFLY_ROT_TIME = 2500;
  private static final int PARAUP1_START_FRAME = 19;
  private static final int PARAUP1_LAST_FRAME = 34;
  private static final int PARAUP1_N_FRAMES = 16;
  private static final int PARAUP1_CYCLE_TIME = 500;
  private static final int PARAUP2_START_FRAME = 34;
  private static final int PARAUP2_LAST_FRAME = 54;
  private static final int PARAUP2_N_FRAMES = 21;
  private static final int PARAUP2_CYCLE_TIME = 666;
  private static final int RUN_START_FRAME = 55;
  private static final int RUN_LAST_FRAME = 77;
  private static final int RUN_N_FRAMES = 23;
  private static final int RUN_CYCLE_TIME = 733;
  private static final int FALL_START_FRAME = 77;
  private static final int FALL_LAST_FRAME = 109;
  private static final int FALL_N_FRAMES = 33;
  private static final int FALL_CYCLE_TIME = 1066;
  private static final int LIE_START_FRAME = 109;
  private static final int LIE_LAST_FRAME = 128;
  private static final int LIE_N_FRAMES = 20;
  private static final int LIE_CYCLE_TIME = 633;
  private static final int LIEDEAD_START_FRAME = 129;
  private static final int LIEDEAD_N_FRAMES = 4;
  private static final int PARADEAD_FRAME = 133;
  private static final int FREEFLYDEAD_FRAME = 134;
  private static final float FREE_SPEED = 50.0F;
  private static final float PARA_SPEED = 5.0F;
  private static final float RUN_SPEED = 6.545455F;
  public static final String playerParaName = "_paraplayer_";
  private String logAircraftName = null;
  private int idxOfPilotPlace;
  private NetUser driver;
  private int swimMeshCode = -1;
  private Vector3d speed;
  private Orient startOrient;
  private Orient faceOrient;
  private static final int ST_FREEFLY = 0;
  private static final int ST_PARAUP1 = 1;
  private static final int ST_PARAUP2 = 2;
  private static final int ST_PARATANGLED = 3;
  private static final int ST_RUN = 4;
  private static final int ST_FALL = 5;
  private static final int ST_LIE = 6;
  private static final int ST_LIEDEAD = 7;
  private static final int ST_SWIM = 8;
  private static final int ST_DISAPPEAR = 9;
  private int st = 0;

  private int dying = 0;
  static final int DYING_NONE = 0;
  static final int DYING_DEAD = 1;
  private int idxOfDeadPose;
  private long animStartTime;
  private long disappearTime;
  private int nRunCycles;
  private float turn_para_on_height;
  private static int _counter = 0;

  private static Mesh preload1 = null;
  private static Mesh preload2 = null;
  private static Mesh preload3 = null;
  private static Mesh preload4 = null;
  private static Mesh preload5 = null;
  private static Mesh preload6 = null;

  private static Point3d p = new Point3d();
  private static Orient o = new Orient();
  private static Vector3f n = new Vector3f();

  private boolean bCheksCaptured = false;

  public boolean isChuteSafelyOpened()
  {
    return (this.st == 2) || (this.st == 6) || (this.st == 8) || (this.st == 9);
  }

  public static void resetGame()
  {
    _counter = 0;
    preload1 = Paratrooper.preload2 = Paratrooper.preload3 = null;
  }

  public static void PRELOAD() {
    preload1 = new Mesh(GetMeshName(1));
    preload2 = new Mesh(GetMeshName(2));
    preload3 = new Mesh(Chute.GetMeshName());

    preload4 = new Mesh(GetMeshName_Water(0));
    preload5 = new Mesh(GetMeshName_Water(1));
    preload6 = new Mesh(GetMeshName_Water(2));
  }

  public void msgCollisionRequest(Actor paramActor, boolean[] paramArrayOfBoolean)
  {
    if (((paramActor instanceof Aircraft)) && (paramActor.isNet()) && (paramActor.isNetMirror()))
    {
      paramArrayOfBoolean[0] = false;
    }
    if (((paramActor == getOwner()) || (getOwner() == null)) && (Time.current() - this.animStartTime < 2800L))
    {
      paramArrayOfBoolean[0] = false;
    }
    if ((this.dying != 0) && (
      (paramActor == null) || ((!(paramActor instanceof ShipGeneric)) && (!(paramActor instanceof BigshipGeneric)))))
      paramArrayOfBoolean[0] = false;
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    if (this.st == 9) {
      return;
    }

    if (this.dying != 0) {
      if ((paramActor != null) && (((paramActor instanceof ShipGeneric)) || ((paramActor instanceof BigshipGeneric))))
      {
        this.st = 9;
      }

      return;
    }

    if ((paramActor != null) && (((paramActor instanceof ShipGeneric)) || ((paramActor instanceof BigshipGeneric))))
    {
      int i = Math.abs(this.speed.z) > 10.0D ? 1 : 0;
      if (i != 0) {
        Die(paramActor);
      }
      this.st = 9;
      return;
    }

    Point3d localPoint3d1 = p;
    this.pos.getAbs(p);
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

    this.speed.z *= 0.5D;

    localPoint3d1.add(localVector3d);
    this.pos.setAbs(localPoint3d1);

    if (this.st == 4) {
      this.st = 5;
      this.animStartTime = Time.current();
    }

    if ((this.st == 6) && (this.dying == 0) && ((paramActor instanceof UnitInterface)) && (paramActor.getSpeed(null) > 0.5D))
    {
      Die(paramActor);
    }
  }

  public void msgShot(Shot paramShot)
  {
    if (this.st == 9) {
      return;
    }

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
    if (this.st == 9) {
      return;
    }

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

  public void checkCaptured()
  {
    this.bCheksCaptured = true;
    if ((this.logAircraftName != null) && (((this.driver == null) && (isNetMaster())) || ((this.driver != null) && (this.driver.isMaster()))))
    {
      EventLog.onParaLanded(this, this.logAircraftName, this.idxOfPilotPlace);
    }
    if (Front.isCaptured(this)) {
      if (name().equals("_paraplayer_")) {
        World.setPlayerCaptured();
        if (Config.isUSE_RENDER()) HUD.log("PlayerCAPT");
        if (Mission.isNet())
          Chat.sendLog(1, "gore_captured", (NetUser)NetEnv.host(), null);
      }
      if ((this.logAircraftName != null) && (((this.driver == null) && (isNetMaster())) || ((this.driver != null) && (this.driver.isMaster()))))
      {
        EventLog.onCaptured(this, this.logAircraftName, this.idxOfPilotPlace);
      }
    }
  }

  public boolean isChecksCaptured() {
    if (this.dying != 0) return true;
    return this.bCheksCaptured;
  }

  private void Die(Actor paramActor)
  {
    Die(paramActor, true);
  }

  private void Die(Actor paramActor, boolean paramBoolean) {
    if (this.dying != 0) {
      return;
    }

    World.onActorDied(this, paramActor);
    if (paramActor != this) {
      if (name().equals("_paraplayer_")) {
        World.setPlayerDead();
        if (Config.isUSE_RENDER()) HUD.log("Player_Killed");
        if (Mission.isNet()) {
          if (((paramActor instanceof Aircraft)) && (((Aircraft)paramActor).isNetPlayer()) && (Actor.isAlive(World.getPlayerAircraft())))
          {
            Chat.sendLogRnd(1, "gore_pkonchute", (Aircraft)paramActor, World.getPlayerAircraft());
          }Chat.sendLog(0, "gore_killed", (NetUser)NetEnv.host(), (NetUser)NetEnv.host());
        }
      }
      if ((this.logAircraftName != null) && (((this.driver == null) && (isNetMaster())) || ((this.driver != null) && (this.driver.isMaster()))))
      {
        if ((Actor.isValid(paramActor)) && (paramActor != Engine.actorLand()))
          EventLog.onParaKilled(this, this.logAircraftName, this.idxOfPilotPlace, paramActor);
        else {
          EventLog.onPilotKilled(this, this.logAircraftName, this.idxOfPilotPlace);
        }
      }
    }
    this.dying = 1;

    if ((isNet()) && (paramBoolean))
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(68);
        if (paramActor != null)
          localNetMsgGuaranted.writeNetObj(paramActor.net);
        else
          localNetMsgGuaranted.writeNetObj(null);
        this.net.postExclude(null, localNetMsgGuaranted);
      }
      catch (Exception localException) {
      }
  }

  public void destroy() {
    Object[] arrayOfObject = getOwnerAttached();
    for (int i = 0; i < arrayOfObject.length; i++) {
      Chute localChute = (Chute)arrayOfObject[i];
      if (Actor.isValid(localChute)) {
        localChute.destroy();
      }
    }

    if ((Mission.isPlaying()) && (World.cur() != null) && (this.driver != null) && ((this.driver.isMaster()) || (this.driver.isTrackWriter())))
    {
      World.cur().checkViewOnPlayerDied(this);
    }

    super.destroy();
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  void chuteTangled(Actor paramActor, boolean paramBoolean)
  {
    if ((this.st == 1) || (this.st == 2))
    {
      this.st = 3;
      this.animStartTime = Time.current();
      this.pos.setAbs(this.faceOrient);

      if ((this.logAircraftName != null) && (((this.driver == null) && (isNetMaster())) || ((this.driver != null) && (this.driver.isMaster()))))
      {
        EventLog.onChuteKilled(this, this.logAircraftName, this.idxOfPilotPlace, paramActor);
      }
      if ((isNet()) && (paramBoolean))
        try {
          NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
          localNetMsgGuaranted.writeByte(83);
          if (paramActor != null)
            localNetMsgGuaranted.writeNetObj(paramActor.net);
          else
            localNetMsgGuaranted.writeNetObj(null);
          this.net.postExclude(null, localNetMsgGuaranted);
        }
        catch (Exception localException)
        {
        }
    }
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
      i = 0;
      j = 19;
      k = 633;
      d = paramDouble - this.animStartTime;
      if (d <= 0.0D)
        f = 0.0F;
      else if (d >= k)
        f = 1.0F;
      else {
        f = (float)(d / k);
      }
      if ((f < 1.0F) || (this.dying == 0)) break;
      i = j = '';
      f = 0.0F; break;
    case 1:
      i = 19;
      j = 34;
      k = 500;
      d = paramDouble - this.animStartTime;
      if (d <= 0.0D)
        f = 0.0F;
      else if (d >= k)
        f = 1.0F;
      else {
        f = (float)(d / k);
      }
      break;
    case 2:
    case 3:
      i = 34;
      j = 54;
      k = 666;
      d = paramDouble - this.animStartTime;
      if (d <= 0.0D)
        f = 0.0F;
      else if (d >= k)
        f = 1.0F;
      else {
        f = (float)(d / k);
      }
      if ((f < 1.0F) || (this.dying == 0)) break;
      i = j = '';
      f = 0.0F; break;
    case 4:
      i = 55;
      j = 77;
      k = 733;
      d = paramDouble - this.animStartTime;
      d %= k;
      if (d < 0.0D) {
        d += k;
      }
      f = (float)(d / k);
      break;
    case 5:
      i = 77;
      j = 109;
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
    case 6:
      i = 109;
      j = 128;
      k = 633;
      d = paramDouble - this.animStartTime;

      if (d <= 0.0D)
        f = 0.0F;
      else if (d >= k)
        f = 1.0F;
      else {
        f = (float)(d / k);
      }
      break;
    case 8:
      return;
    case 9:
      return;
    case 7:
    default:
      i = j = 129 + this.idxOfDeadPose;
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
    return "3do/humans/Paratroopers/" + (paramInt == 2 ? "Germany" : "Russia") + "/mono.sim";
  }

  private static String GetMeshName_Water(int paramInt)
  {
    return "3do/humans/Paratroopers/Water/" + (paramInt == 1 ? "US_Jacket" : paramInt == 0 ? "JN_Jacket" : "US_Dinghy") + "/live.sim";
  }

  public void prepareSkin(String paramString1, String paramString2, Mat[] paramArrayOfMat)
  {
    if (!Config.isUSE_RENDER()) return;
    String str = "Pilot";
    int i = mesh().materialFind(str);

    if (i < 0)
      return;
    Mat localMat1;
    if (FObj.Exist(paramString1)) {
      localMat1 = (Mat)FObj.Get(paramString1);
    } else {
      Mat localMat2 = mesh().material(i);
      localMat1 = (Mat)localMat2.Clone();
      localMat1.Rename(paramString1);
      localMat1.setLayer(0);
      localMat1.set('\000', paramString2);
    }
    if (paramArrayOfMat != null)
      paramArrayOfMat[0] = localMat1;
    mesh().materialReplace(str, localMat1);
  }

  public Paratrooper(Actor paramActor, int paramInt1, int paramInt2, Loc paramLoc, Vector3d paramVector3d, NetMsgInput paramNetMsgInput, int paramInt3)
  {
    super(GetMeshName(paramInt1));

    this.startOrient = new Orient();
    paramLoc.get(this.startOrient);

    this.faceOrient = new Orient();
    this.faceOrient.set(this.startOrient);

    this.faceOrient.setYPR(this.faceOrient.getYaw(), 0.0F, 0.0F);
    Vector3d localVector3d = new Vector3d();
    localVector3d.set(1.0D, 0.0D, 0.0D);
    this.faceOrient.transform(localVector3d);

    this.speed = new Vector3d();
    this.speed.set(paramVector3d);

    setOwner(paramActor);
    this.idxOfPilotPlace = paramInt2;

    setArmy(paramInt1);

    this.swimMeshCode = -1;
    if ((Actor.isValid(paramActor)) && ((paramActor instanceof Aircraft))) {
      String str = ((Aircraft)paramActor).getRegiment().country();
      if (("us".equals(str)) || ("gb".equals(str)))
        this.swimMeshCode = (paramInt2 == 0 ? 2 : 1);
      else if ("ja".equals(str)) {
        this.swimMeshCode = 0;
      }
    }

    o.setAT0(this.speed);
    o.set(o.azimut(), 0.0F, 0.0F);
    this.pos.setAbs(paramLoc);
    this.pos.reset();

    this.st = 0;
    this.animStartTime = Time.tick();

    this.dying = 0;

    setName("_para_" + _counter++);

    collide(true);
    this.draw = new SoldDraw(null);
    dreamFire(true);
    drawing(true);

    if (!interpEnd("move")) {
      interpPut(new Move(), "move", Time.current(), null);
    }
    if (Actor.isValid(paramActor)) {
      this.logAircraftName = EventLog.name(paramActor);
    }

    if (paramNetMsgInput == null)
      this.net = new Master(this);
    else
      this.net = new Mirror(this, paramNetMsgInput, paramInt3);
  }

  private void testDriver() {
    if ((this.driver != null) && ((this.driver.isMaster()) || (this.driver.isTrackWriter())) && (Actor.isValid(getOwner()))) {
      if (World.isPlayerGunner())
        World.doGunnerParatrooper(this);
      else {
        World.doPlayerParatrooper(this);
      }
      setName("_paraplayer_");
      if (Mission.isNet())
        Chat.sendLog(1, "gore_bailedout", (NetUser)NetEnv.host(), (NetUser)null);
    }
    if (this.driver != null)
      this.driver.tryPreparePilot(this);
  }

  public Paratrooper(Actor paramActor, int paramInt1, int paramInt2, Loc paramLoc, Vector3d paramVector3d)
  {
    this(paramActor, paramInt1, paramInt2, paramLoc, paramVector3d, null, 0);
  }

  public NetMsgSpawn netReplicate(NetChannel paramNetChannel)
    throws IOException
  {
    NetMsgSpawn localNetMsgSpawn = new NetMsgSpawn(this.net);
    Point3d localPoint3d = this.pos.getAbsPoint();
    localNetMsgSpawn.writeFloat((float)localPoint3d.x);
    localNetMsgSpawn.writeFloat((float)localPoint3d.y);
    localNetMsgSpawn.writeFloat((float)localPoint3d.z);
    Orient localOrient = this.pos.getAbsOrient();
    localNetMsgSpawn.writeFloat(localOrient.getAzimut());
    localNetMsgSpawn.writeFloat(localOrient.getTangage());
    localNetMsgSpawn.writeFloat(localOrient.getKren());
    localNetMsgSpawn.writeFloat((float)this.speed.x);
    localNetMsgSpawn.writeFloat((float)this.speed.y);
    localNetMsgSpawn.writeFloat((float)this.speed.z);
    localNetMsgSpawn.writeByte(getArmy());
    if ((getOwner() != null) && (paramNetChannel != null) && (paramNetChannel.isMirrored(getOwner().net)))
      localNetMsgSpawn.writeNetObj(getOwner().net);
    else
      localNetMsgSpawn.writeNetObj(null);
    localNetMsgSpawn.writeByte(this.idxOfPilotPlace);
    localNetMsgSpawn.writeFloat(this.turn_para_on_height);
    localNetMsgSpawn.writeByte(this.nRunCycles);
    localNetMsgSpawn.writeNetObj(this.driver);
    return localNetMsgSpawn;
  }

  static
  {
    Spawn.add(Paratrooper.class, new SPAWN());
  }

  public static class SPAWN
    implements NetSpawn
  {
    public void netSpawn(int paramInt, NetMsgInput paramNetMsgInput)
    {
      try
      {
        Loc localLoc = new Loc(paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat());

        Vector3d localVector3d = new Vector3d(paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat());
        Actor localActor = null;
        NetObj localNetObj = paramNetMsgInput.readNetObj();
        if (localNetObj != null) localActor = (Actor)localNetObj.superObj();
        Paratrooper localParatrooper = new Paratrooper(localActor, paramNetMsgInput.readUnsignedByte(), paramNetMsgInput.readUnsignedByte(), localLoc, localVector3d, paramNetMsgInput, paramInt);
      } catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
      }
    }
  }

  class Mirror extends Paratrooper.ParaNet
  {
    public Mirror(Actor paramNetMsgInput, NetMsgInput paramInt, int arg4)
    {
      super(paramNetMsgInput, paramInt, i);
      try {
        Paratrooper.access$702(Paratrooper.this, paramInt.readFloat());
        Paratrooper.access$1402(Paratrooper.this, paramInt.readByte());
        Paratrooper.access$1802(Paratrooper.this, (NetUser)paramInt.readNetObj());
        Paratrooper.this.testDriver();
      }
      catch (Exception localException)
      {
      }
    }
  }

  class Master extends Paratrooper.ParaNet
  {
    public Master(Actor arg2)
    {
      super(localActor1);
      localActor1.pos.getAbs(Paratrooper.p);
      Engine.land(); float f = (float)Paratrooper.p.z - Landscape.HQ((float)Paratrooper.p.x, (float)Paratrooper.p.y);
      if (f <= 500.0F)
        Paratrooper.access$702(Paratrooper.this, 500.0F);
      else if (f >= 4000.0F)
        Paratrooper.access$702(Paratrooper.this, 2000.0F);
      else {
        Paratrooper.access$702(Paratrooper.this, 500.0F + 1500.0F * ((f - 500.0F) / 3500.0F));
      }
      Paratrooper.access$732(Paratrooper.this, World.Rnd().nextFloat(1.0F, 1.2F));
      Paratrooper.access$1402(Paratrooper.this, World.Rnd().nextInt(6, 12));

      Class localClass = localActor1.getOwner().getClass();
      Object localObject = Property.value(localClass, "cockpitClass");
      if (localObject != null) {
        Class[] arrayOfClass = null;
        if ((localObject instanceof Class)) {
          arrayOfClass = new Class[1];
          arrayOfClass[0] = ((Class)localObject);
        } else {
          arrayOfClass = (Class[])(Class[])localObject;
        }
        for (int i = 0; i < arrayOfClass.length; i++) {
          int j = Property.intValue(arrayOfClass[i], "astatePilotIndx", 0);
          if (j == Paratrooper.this.idxOfPilotPlace) {
            Actor localActor2 = ((Aircraft)localActor1.getOwner()).netCockpitGetDriver(i);
            if (localActor2 != null) {
              if (Mission.isSingle()) {
                Paratrooper.access$1802(Paratrooper.this, (NetUser)NetEnv.host()); break;
              }
              if ((localActor2 instanceof NetGunner)) {
                Paratrooper.access$1802(Paratrooper.this, ((NetGunner)localActor2).getUser()); break;
              }
              Paratrooper.access$1802(Paratrooper.this, ((NetAircraft)localActor2).netUser());

              break;
            }
          }
        }
      }
      Paratrooper.this.testDriver();
    }
  }

  class ParaNet extends ActorNet
  {
    public boolean netInput(NetMsgInput paramNetMsgInput)
      throws IOException
    {
      if (!paramNetMsgInput.isGuaranted())
        return false;
      int i = paramNetMsgInput.readByte();
      int j = -1;
      Object localObject;
      Actor localActor;
      switch (i) {
      case 68:
        j = 1;
        localObject = paramNetMsgInput.readNetObj();
        localActor = null;
        if (localObject != null) localActor = (Actor)((NetObj)localObject).superObj();
        Paratrooper.this.Die(localActor, false);

        break;
      case 83:
        j = 1;
        localObject = paramNetMsgInput.readNetObj();
        localActor = null;
        if (localObject != null) localActor = (Actor)((NetObj)localObject).superObj();
        Object[] arrayOfObject = Paratrooper.this.getOwnerAttached();
        for (int k = 0; k < arrayOfObject.length; k++) {
          Chute localChute = (Chute)arrayOfObject[k];
          if (Actor.isValid(localChute)) {
            localChute.tangleChute(localActor);
          }
        }

      }

      if (j >= 0) {
        localObject = new NetMsgGuaranted(paramNetMsgInput, j);
        postExclude(paramNetMsgInput.channel(), (NetMsgGuaranted)localObject);
        return true;
      }
      return false;
    }

    public ParaNet(Actor arg2) {
      super();
    }
    public ParaNet(Actor paramNetMsgInput, NetMsgInput paramInt, int arg4) {
      super(paramInt.channel(), i);
    }
  }

  class Move extends Interpolate
  {
    Move()
    {
    }

    public boolean tick()
    {
      if (Paratrooper.this.st == 9) {
        if (Paratrooper.this.dying == 0) {
          Paratrooper.this.checkCaptured();
        }
        Paratrooper.this.postDestroy();
        return false;
      }
      if (((Paratrooper.this.st == 6) || (Paratrooper.this.st == 7) || (Paratrooper.this.st == 8)) && 
        (Time.current() >= Paratrooper.this.disappearTime)) {
        Paratrooper.this.postDestroy();
        return false;
      }

      if (Paratrooper.this.dying != 0)
      {
        switch (Paratrooper.this.st) {
        case 4:
          Paratrooper.access$002(Paratrooper.this, 5);
          Paratrooper.access$302(Paratrooper.this, Time.current());
          break;
        case 6:
          Paratrooper.access$002(Paratrooper.this, 7);
          Paratrooper.access$402(Paratrooper.this, World.Rnd().nextInt(0, 3));
        }

      }

      long l = Time.tickNext() - Paratrooper.this.animStartTime;

      switch (Paratrooper.this.st) {
      case 0:
      case 1:
      case 2:
      case 3:
        Paratrooper.this.pos.getAbs(Paratrooper.p);
        Engine.land(); float f1 = Landscape.HQ((float)Paratrooper.p.x, (float)Paratrooper.p.y);

        if (Paratrooper.this.st == 0)
        {
          if (l >= 2500L) {
            Paratrooper.this.pos.setAbs(Paratrooper.this.faceOrient);
            if ((Paratrooper.this.dying == 0) && 
              ((float)Paratrooper.p.z - f1 <= Paratrooper.this.turn_para_on_height) && (Paratrooper.this.speed.z < -5.0D))
            {
              Paratrooper.access$002(Paratrooper.this, 1);
              Paratrooper.access$302(Paratrooper.this, Time.current());
              l = Time.tickNext() - Paratrooper.this.animStartTime;
              new Chute(this.actor);
            }
          }
          else {
            Paratrooper.this.pos.getAbs(Paratrooper.o);
            float f2 = (float)l / 2500.0F;
            if (f2 <= 0.0F) f2 = 0.0F;
            if (f2 >= 1.0F) f2 = 1.0F;
            Paratrooper.o.interpolate(Paratrooper.this.startOrient, Paratrooper.this.faceOrient, f2);
            Paratrooper.this.pos.setAbs(Paratrooper.o);
          }
        }

        if ((Paratrooper.this.st == 1) && 
          (l >= 500L)) {
          Paratrooper.access$002(Paratrooper.this, 2);
          Paratrooper.access$302(Paratrooper.this, Time.current());
          l = Time.tickNext() - Paratrooper.this.animStartTime;
        }

        Paratrooper.p.scaleAdd(Time.tickLenFs(), Paratrooper.this.speed, Paratrooper.p);
        Paratrooper.this.speed.z -= Time.tickLenFs() * World.g();
        if (Paratrooper.this.st == 2) {
          if (Paratrooper.this.speed.x != 0.0D) Paratrooper.this.speed.x -= Math.abs(Paratrooper.this.speed.x) / Paratrooper.this.speed.x * 0.009999999776482582D * (Paratrooper.this.speed.x * Paratrooper.this.speed.x) * Time.tickLenFs();
          if (Paratrooper.this.speed.y != 0.0D) Paratrooper.this.speed.y -= Math.abs(Paratrooper.this.speed.y) / Paratrooper.this.speed.y * 0.009999999776482582D * (Paratrooper.this.speed.y * Paratrooper.this.speed.y) * Time.tickLenFs(); 
        }
        else {
          if (Paratrooper.this.speed.x != 0.0D) Paratrooper.this.speed.x -= Math.abs(Paratrooper.this.speed.x) / Paratrooper.this.speed.x * 0.001000000047497451D * (Paratrooper.this.speed.x * Paratrooper.this.speed.x) * Time.tickLenFs();
          if (Paratrooper.this.speed.y != 0.0D) Paratrooper.this.speed.y -= Math.abs(Paratrooper.this.speed.y) / Paratrooper.this.speed.y * 0.001000000047497451D * (Paratrooper.this.speed.y * Paratrooper.this.speed.y) * Time.tickLenFs();

        }

        double d1 = Paratrooper.this.st == 2 ? 5.0F : 50.0F;
        if (-Paratrooper.this.speed.z > d1) {
          double d2 = -Paratrooper.this.speed.z - d1;
          if (d2 > Time.tickLenFs() * 20.0F) {
            d2 = Time.tickLenFs() * 20.0F;
          }
          Paratrooper.this.speed.z += d2;
        }

        if (Paratrooper.p.z <= f1) {
          int i = Paratrooper.this.speed.length() > 10.0D ? 1 : 0;

          Vector3d localVector3d = new Vector3d();
          localVector3d.set(1.0D, 0.0D, 0.0D);
          Paratrooper.this.faceOrient.transform(localVector3d);
          Paratrooper.this.speed.set(localVector3d);
          Paratrooper.this.speed.z = 0.0D;
          Paratrooper.this.speed.normalize();
          Paratrooper.this.speed.scale(6.545454502105713D);

          Paratrooper.p.z = f1;

          if ((i != 0) || (Paratrooper.this.dying != 0)) {
            Paratrooper.access$002(Paratrooper.this, 7);
            Paratrooper.access$302(Paratrooper.this, Time.current());
            Paratrooper.access$202(Paratrooper.this, Time.tickNext() + 1000 * World.Rnd().nextInt(25, 35));

            Paratrooper.access$402(Paratrooper.this, World.Rnd().nextInt(0, 3));
            new MsgAction(0.0D, this.actor) {
              public void doAction(Object paramObject) {
                Paratrooper localParatrooper = (Paratrooper)paramObject;
                localParatrooper.Die(Engine.actorLand());
              } } ;
          } else {
            Paratrooper.access$002(Paratrooper.this, 4);
            Paratrooper.access$302(Paratrooper.this, Time.current());
            if ((Paratrooper.this.name().equals("_paraplayer_")) && (Mission.isNet()) && (World.getPlayerFM() != null) && (Actor.isValid(World.getPlayerAircraft())) && (World.getPlayerAircraft().isNetPlayer()))
            {
              localObject = World.getPlayerFM();
              if ((((FlightModel)localObject).isWasAirborne()) && (((FlightModel)localObject).isStationedOnGround()) && (!((FlightModel)localObject).isNearAirdrome())) {
                Chat.sendLogRnd(2, "gore_walkaway", World.getPlayerAircraft(), null);
              }
            }
          }
          Paratrooper.this.pos.setAbs(Paratrooper.this.faceOrient);

          Object localObject = Paratrooper.this.getOwnerAttached();
          for (int j = 0; j < localObject.length; j++) {
            Chute localChute = (Chute)localObject[j];
            if (Actor.isValid(localChute)) {
              localChute.landing();
            }
          }
        }
        Paratrooper.this.pos.setAbs(Paratrooper.p);
        break;
      case 4:
        Paratrooper.this.pos.getAbs(Paratrooper.p);
        Paratrooper.p.scaleAdd(Time.tickLenFs(), Paratrooper.this.speed, Paratrooper.p);
        Paratrooper.p.z = Engine.land().HQ(Paratrooper.p.x, Paratrooper.p.y);
        Paratrooper.this.pos.setAbs(Paratrooper.p);

        if (World.land().isWater(Paratrooper.p.x, Paratrooper.p.y)) {
          if (Paratrooper.this.swimMeshCode < 0) {
            Paratrooper.access$002(Paratrooper.this, 5);
            Paratrooper.access$302(Paratrooper.this, Time.current());
          } else {
            Paratrooper.this.setMesh(Paratrooper.access$1300(Paratrooper.this.swimMeshCode));
            Paratrooper.this.pos.getAbs(Paratrooper.p);
            Paratrooper.p.z = Engine.land().HQ(Paratrooper.p.x, Paratrooper.p.y);
            Paratrooper.this.pos.setAbs(Paratrooper.p);
            Paratrooper.access$002(Paratrooper.this, 8);
            Paratrooper.access$302(Paratrooper.this, Time.current());
            Paratrooper.access$202(Paratrooper.this, Time.tickNext() + 1000 * World.Rnd().nextInt(25, 35));

            Paratrooper.this.checkCaptured();
          }
        } else {
          if (l / 733L < Paratrooper.this.nRunCycles) break;
          Paratrooper.access$002(Paratrooper.this, 5);
          Paratrooper.access$302(Paratrooper.this, Time.current()); } break;
      case 5:
        Paratrooper.this.pos.getAbs(Paratrooper.p);
        Paratrooper.p.scaleAdd(Time.tickLenFs(), Paratrooper.this.speed, Paratrooper.p);
        Paratrooper.p.z = Engine.land().HQ(Paratrooper.p.x, Paratrooper.p.y);
        if (World.land().isWater(Paratrooper.p.x, Paratrooper.p.y)) {
          if (Paratrooper.this.swimMeshCode < 0) {
            Paratrooper.p.z -= 0.5D;
          } else {
            Paratrooper.this.setMesh(Paratrooper.access$1300(Paratrooper.this.swimMeshCode));
            Paratrooper.this.pos.getAbs(Paratrooper.p);
            Paratrooper.p.z = Engine.land().HQ(Paratrooper.p.x, Paratrooper.p.y);
            Paratrooper.this.pos.setAbs(Paratrooper.p);
            Paratrooper.access$002(Paratrooper.this, 8);
            Paratrooper.access$302(Paratrooper.this, Time.current());
            Paratrooper.access$202(Paratrooper.this, Time.tickNext() + 1000 * World.Rnd().nextInt(25, 35));

            Paratrooper.this.checkCaptured();
            break;
          }
        }
        Paratrooper.this.pos.setAbs(Paratrooper.p);

        if (l < 1066L) break;
        Paratrooper.access$002(Paratrooper.this, 6);
        Paratrooper.access$302(Paratrooper.this, Time.current());
        Paratrooper.access$202(Paratrooper.this, Time.tickNext() + 1000 * World.Rnd().nextInt(25, 35));

        Paratrooper.this.checkCaptured(); break;
      case 6:
      case 7:
        Paratrooper.this.pos.getAbs(Paratrooper.p);
        Paratrooper.p.z = Engine.land().HQ(Paratrooper.p.x, Paratrooper.p.y);
        if (World.land().isWater(Paratrooper.p.x, Paratrooper.p.y)) {
          Paratrooper.p.z -= 3.0D;
        }
        Paratrooper.this.pos.setAbs(Paratrooper.p);
      }

      Paratrooper.this.setAnimFrame(Time.tickNext());

      return true;
    }
  }

  private class SoldDraw extends ActorMeshDraw
  {
    private final Paratrooper this$0;

    private SoldDraw()
    {
      this.this$0 = this$1;
    }
    public int preRender(Actor paramActor) { this.this$0.setAnimFrame(Time.current());
      return super.preRender(paramActor);
    }

    SoldDraw(Paratrooper.1 arg2)
    {
      this();
    }
  }
}