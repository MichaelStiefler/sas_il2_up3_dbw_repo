package com.maddox.il2.objects.vehicles.cars;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector2d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.TableFunctions;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.ChiefGround;
import com.maddox.il2.ai.ground.Coward;
import com.maddox.il2.ai.ground.Moving;
import com.maddox.il2.ai.ground.Obstacle;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.ai.ground.StaticObstacle;
import com.maddox.il2.ai.ground.UnitData;
import com.maddox.il2.ai.ground.UnitInPackedForm;
import com.maddox.il2.ai.ground.UnitInterface;
import com.maddox.il2.ai.ground.UnitMove;
import com.maddox.il2.ai.ground.UnitSpawn;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.bridges.LongBridge;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.humans.Soldier;
import com.maddox.il2.objects.vehicles.tanks.TankGeneric;
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

public abstract class CarGeneric extends ActorHMesh
  implements MsgCollisionRequestListener, MsgCollisionListener, MsgExplosionListener, MsgShotListener, Coward, Prey, Obstacle, UnitInterface
{
  private CarProperties prop = null;
  private int codeName;
  private float heightAboveLandSurface;
  public UnitData udata = new UnitData();

  private Moving mov = new Moving();

  protected SoundFX engineSFX = null;
  protected int engineSTimer = 9999999;
  protected int ticksIn8secs = (int)(8.0F / Time.tickConstLenFs());

  private int collisionStage = 0;
  static final int COLLIS_NO_COLLISION = 0;
  static final int COLLIS_JUST_COLLIDED = 1;
  static final int COLLIS_MOVING_FROM_COLLISION = 2;
  private Vector2d collisVector = new Vector2d();
  private Actor collidee;
  private StaticObstacle obs = new StaticObstacle();
  private long timeHumanLaunch;
  private int dying = 0;
  static final int DYING_NONE = 0;
  static final int DYING_DEAD = 1;
  private int codeOfUnderlyingBridgeSegment = -1;

  private static CarProperties constr_arg1 = null;
  private static Actor constr_arg2 = null;

  private static Point3d p = new Point3d();
  private static Orient o = new Orient();
  private static Vector3f n = new Vector3f();

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

  public void SetTimerToLaunchHumans()
  {
    if (this.timeHumanLaunch > 0L) {
      return;
    }
    if ((this.timeHumanLaunch == 0L) || (-this.timeHumanLaunch <= Time.current()))
      this.timeHumanLaunch = (Time.current() + (int)Rnd(500.0F, 1500.0F));
  }

  public void LaunchHumans()
  {
    if (this.prop.NUM_HUMANS == 0) {
      return;
    }

    Object localObject = new Loc();
    Loc localLoc1 = new Loc();
    ((Loc)localObject).set(0.0D, 0.0D, 0.0D, 170.0F - Rnd(0.0F, 130.0F), Rnd(-5.0F, 2.0F), 0.0F);
    localLoc1.set(0.0D, 0.0D, 0.0D, 190.0F + Rnd(0.0F, 130.0F), Rnd(-5.0F, 2.0F), 0.0F);

    Loc localLoc2 = this.pos.getAbs();

    ((Loc)localObject).add(localLoc2);
    localLoc1.add(localLoc2);

    if (this.prop.NUM_HUMANS == 1) {
      if (RndB(0.5F)) {
        localObject = localLoc1;
      }
      new Soldier(this, getArmy(), (Loc)localObject);
    } else {
      new Soldier(this, getArmy(), (Loc)localObject);
      new Soldier(this, getArmy(), localLoc1);
    }
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
      SetTimerToLaunchHumans();
      return;
    }

    if (paramShot.power <= 0.0F) {
      SetTimerToLaunchHumans();
      return;
    }

    if (paramShot.powerType == 1) {
      if (RndB(0.05F)) {
        SetTimerToLaunchHumans();
        return;
      }

      Die(paramShot.initiator);
      return;
    }

    float f1 = Shot.panzerThickness(this.pos.getAbsOrient(), paramShot.v, false, this.prop.PANZER_BODY_FRONT, this.prop.PANZER_BODY_SIDE, this.prop.PANZER_BODY_BACK, this.prop.PANZER_BODY_TOP, this.prop.PANZER_BODY_FRONT, this.prop.PANZER_BODY_TOP);

    f1 *= Rnd(0.93F, 1.07F);

    float f2 = this.prop.fnShotPanzer.Value(paramShot.power, f1);

    if ((f2 < 1000.0F) && ((f2 <= 1.0F) || (RndB(1.0F / f2))))
      Die(paramShot.initiator);
    else
      SetTimerToLaunchHumans();
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    if (this.dying != 0) {
      return;
    }

    if ((isNetMirror()) && (paramExplosion.isMirage())) {
      SetTimerToLaunchHumans();
      return;
    }

    if (paramExplosion.power <= 0.0F) {
      SetTimerToLaunchHumans();
      return;
    }

    if (paramExplosion.powerType == 1) {
      if (TankGeneric.splintersKill(paramExplosion, this.prop.fnShotPanzer, Rnd(0.0F, 1.0F), Rnd(0.0F, 1.0F), this, 0.7F, 0.0F, this.prop.PANZER_BODY_FRONT, this.prop.PANZER_BODY_SIDE, this.prop.PANZER_BODY_BACK, this.prop.PANZER_BODY_TOP, this.prop.PANZER_BODY_FRONT, this.prop.PANZER_BODY_TOP))
      {
        Die(paramExplosion.initiator);
      }
      else SetTimerToLaunchHumans();

      return;
    }

    if ((paramExplosion.powerType == 2) && (paramExplosion.chunkName != null)) {
      Die(paramExplosion.initiator);
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
      Die(paramExplosion.initiator);
    else
      SetTimerToLaunchHumans();
  }

  public void scare()
  {
    SetTimerToLaunchHumans();
  }

  private void ShowExplode(float paramFloat) {
    if (paramFloat > 0.0F) {
      paramFloat = Rnd(paramFloat, paramFloat * 1.6F);
    }
    Explosions.runByName(this.prop.explodeName, this, "Smoke", "", paramFloat);
  }

  private void ActivateMesh()
  {
    int i = this.dying == 1 ? 1 : 0;

    if (i == 0) {
      setMesh(this.prop.MESH0_NAME);
    }
    else if (this.prop.MESH1_NAME == null) {
      setMesh(this.prop.MESH0_NAME);

      mesh().makeAllMaterialsDarker(0.22F, 0.35F);
    } else {
      setMesh(this.prop.MESH1_NAME);
    }

    int j = mesh().hookFind("Ground_Level");

    float f = this.heightAboveLandSurface;
    Object localObject;
    if (j != -1) {
      localObject = new Matrix4d();
      mesh().hookMatrix(j, (Matrix4d)localObject);
      this.heightAboveLandSurface = (float)(-((Matrix4d)localObject).m23);
    }
    else {
      localObject = new float[6];
      mesh().getBoundBox(localObject);
      this.heightAboveLandSurface = (-localObject[2]);
    }

    if (i != 0) {
      localObject = this.pos.getAbsPoint();
      localObject.z += this.heightAboveLandSurface - f;
      this.pos.setAbs((Point3d)localObject);
      this.pos.reset();
    }
  }

  private void MakeCrush()
  {
    this.engineSFX = null;
    this.engineSTimer = 99999999;
    breakSounds();

    this.dying = 1;

    ActivateMesh();
  }

  private void Die(Actor paramActor)
  {
    if (isNetMirror()) {
      send_DeathRequest(paramActor);
      return;
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

    ShowExplode(16.0F);
    if (isNetMaster()) {
      send_DeathCommand(paramActor);

      Point3d localPoint3d = simplifyPos(this.pos.getAbsPoint());
      Orient localOrient = simplifyOri(this.pos.getAbsOrient());
      setPosition(localPoint3d, localOrient);
    }
    MakeCrush();
  }

  private void DieMirror(Actor paramActor, boolean paramBoolean)
  {
    if (!isNetMirror()) {
      System.out.println("Internal error in CarGeneric: DieMirror");
    }

    this.collisionStage = 1;

    ((ChiefGround)getOwner()).Detach(this, paramActor);

    World.onActorDied(this, paramActor);

    if (paramBoolean) {
      ShowExplode(16.0F);
    }
    MakeCrush();
  }

  public void destroy()
  {
    this.engineSFX = null;
    this.engineSTimer = 99999999;
    breakSounds();

    if (this.codeOfUnderlyingBridgeSegment >= 0) {
      LongBridge.DelTraveller(this.codeOfUnderlyingBridgeSegment, this);
    }
    super.destroy();
  }

  private void setPosition(Point3d paramPoint3d, Orient paramOrient)
  {
    this.pos.setAbs(paramPoint3d, paramOrient);
    this.pos.reset();
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }

  protected CarGeneric()
  {
    this(constr_arg1, constr_arg2);
  }

  public void setMesh(String paramString) {
    super.setMesh(paramString);
    if (Config.cur.b3dgunners) return;
    mesh().materialReplaceToNull("Pilot1");
  }

  public CarGeneric(CarProperties paramCarProperties, Actor paramActor) {
    super(paramCarProperties.MESH0_NAME);
    this.prop = paramCarProperties;

    this.timeHumanLaunch = (-(Time.current() + (int)Rnd(2000.0F, 8000.0F)));

    collide(true);
    drawing(true);
    setOwner(paramActor);

    this.codeName = paramCarProperties.codeName;
    setName(paramActor.name() + this.codeName);

    setArmy(paramActor.getArmy());

    new HookNamed(this, "Smoke");

    ActivateMesh();

    int i = Mission.cur().getUnitNetIdRemote(this);
    NetChannel localNetChannel = Mission.cur().getNetMasterChannel();
    if (localNetChannel == null)
      this.net = new Master(this);
    else if (i != 0)
      this.net = new Mirror(this, localNetChannel, i);
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
      localNetMsgFiltered.unLockAndClear();
      localNetMsgFiltered.writeByte(67);
      localNetMsgFiltered.setIncludeTime(false);
      this.net.postTo(Time.current(), this.net.masterChannel(), localNetMsgFiltered);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
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

  static int packNormal(float paramFloat)
  {
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

  static Point3d simplifyPos(Point3d paramPoint3d) {
    return unpackPos(packPos(paramPoint3d));
  }
  static Orient simplifyOri(Orient paramOrient) {
    return unpackOri(packOri(paramOrient));
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
    return new UnitInPackedForm(this.codeName, i, j, SpeedAverage(), BestSpace(), 0, HitbyMask());
  }

  public int HitbyMask()
  {
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

    if (paramArrayOfBulletProperties[0].powerType == 1)
    {
      return 0;
    }
    if (paramArrayOfBulletProperties[1].powerType == 1)
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

    Explosions.Car_ExplodeCollapse(this.pos.getAbsPoint());

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
      Explosions.Car_ExplodeCollapse(this.pos.getAbsPoint());
    else {
      Explosions.Car_ExplodeCollapse(this.pos.getAbsPoint());
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

  public static class SPAWN
    implements UnitSpawn
  {
    public Class cls;
    public CarGeneric.CarProperties proper;

    private static float getF(SectFile paramSectFile, String paramString1, String paramString2, float paramFloat1, float paramFloat2)
    {
      float f = paramSectFile.get(paramString1, paramString2, -9865.3447F);
      if ((f == -9865.3447F) || (f < paramFloat1) || (f > paramFloat2)) {
        if (f == -9865.3447F) {
          System.out.println("Car: Parameter [" + paramString1 + "]:<" + paramString2 + "> " + "not found");
        }
        else {
          System.out.println("Car: Value of [" + paramString1 + "]:<" + paramString2 + "> (" + f + ")" + " is out of range (" + paramFloat1 + ";" + paramFloat2 + ")");
        }

        throw new RuntimeException("Can't set property");
      }
      return f;
    }

    private static String getS(SectFile paramSectFile, String paramString1, String paramString2) {
      String str = paramSectFile.get(paramString1, paramString2);
      if ((str == null) || (str.length() <= 0)) {
        System.out.print("Car: Parameter [" + paramString1 + "]:<" + paramString2 + "> ");
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

    private static CarGeneric.CarProperties LoadCarProperties(SectFile paramSectFile, String paramString, Class paramClass)
    {
      CarGeneric.CarProperties localCarProperties = new CarGeneric.CarProperties();

      String str = getS(paramSectFile, paramString, "PanzerType", null);
      if (str == null) {
        str = "Tank";
      }
      localCarProperties.fnShotPanzer = TableFunctions.GetFunc2(str + "ShotPanzer");
      localCarProperties.fnExplodePanzer = TableFunctions.GetFunc2(str + "ExplodePanzer");

      localCarProperties.PANZER_TNT_TYPE = getF(paramSectFile, paramString, "PanzerSubtype", 0.0F, 100.0F);

      localCarProperties.meshSummer = getS(paramSectFile, paramString, "MeshSummer");
      localCarProperties.meshDesert = getS(paramSectFile, paramString, "MeshDesert", localCarProperties.meshSummer);
      localCarProperties.meshWinter = getS(paramSectFile, paramString, "MeshWinter", localCarProperties.meshSummer);
      localCarProperties.meshSummer1 = getS(paramSectFile, paramString, "MeshSummerDamage", null);
      localCarProperties.meshDesert1 = getS(paramSectFile, paramString, "MeshDesertDamage", localCarProperties.meshSummer1);
      localCarProperties.meshWinter1 = getS(paramSectFile, paramString, "MeshWinterDamage", localCarProperties.meshSummer1);

      int i = (localCarProperties.meshSummer1 == null ? 1 : 0) + (localCarProperties.meshDesert1 == null ? 1 : 0) + (localCarProperties.meshWinter1 == null ? 1 : 0);

      if ((i != 0) && (i != 3)) {
        System.out.println("Car: Uncomplete set of damage meshes for '" + paramString + "'");

        throw new RuntimeException("Can't register car object");
      }

      localCarProperties.PANZER_BODY_FRONT = getF(paramSectFile, paramString, "PanzerBodyFront", 0.001F, 9.999F);

      if (paramSectFile.get(paramString, "PanzerBodyBack", -9865.3447F) == -9865.3447F) {
        localCarProperties.PANZER_BODY_BACK = localCarProperties.PANZER_BODY_FRONT;
        localCarProperties.PANZER_BODY_SIDE = localCarProperties.PANZER_BODY_FRONT;
        localCarProperties.PANZER_BODY_TOP = localCarProperties.PANZER_BODY_FRONT;
      } else {
        localCarProperties.PANZER_BODY_BACK = getF(paramSectFile, paramString, "PanzerBodyBack", 0.001F, 9.999F);
        localCarProperties.PANZER_BODY_SIDE = getF(paramSectFile, paramString, "PanzerBodySide", 0.001F, 9.999F);
        localCarProperties.PANZER_BODY_TOP = getF(paramSectFile, paramString, "PanzerBodyTop", 0.001F, 9.999F);
      }

      localCarProperties.explodeName = getS(paramSectFile, paramString, "Explode", "Car");

      float f = Math.min(Math.min(localCarProperties.PANZER_BODY_BACK, localCarProperties.PANZER_BODY_TOP), Math.min(localCarProperties.PANZER_BODY_SIDE, localCarProperties.PANZER_BODY_FRONT));

      localCarProperties.HITBY_MASK = (f > 0.015F ? -2 : -1);

      localCarProperties.SPEED_AVERAGE = CarGeneric.KmHourToMSec(getF(paramSectFile, paramString, "SpeedAverage", 1.0F, 100.0F));
      localCarProperties.SPEED_MAX = CarGeneric.KmHourToMSec(getF(paramSectFile, paramString, "SpeedMax", 1.0F, 100.0F));
      localCarProperties.SPEED_BACK = CarGeneric.KmHourToMSec(getF(paramSectFile, paramString, "SpeedBack", 0.5F, 100.0F));
      localCarProperties.ROT_SPEED_MAX = getF(paramSectFile, paramString, "RotSpeedMax", 0.1F, 800.0F);
      localCarProperties.ROT_INVIS_ANG = getF(paramSectFile, paramString, "RotInvisAng", 0.0F, 360.0F);

      localCarProperties.BEST_SPACE = getF(paramSectFile, paramString, "BestSpace", 0.1F, 100.0F);
      localCarProperties.AFTER_COLLISION_DIST = getF(paramSectFile, paramString, "AfterCollisionDist", 0.1F, 80.0F);

      localCarProperties.COMMAND_INTERVAL = getF(paramSectFile, paramString, "CommandInterval", 0.5F, 100.0F);
      localCarProperties.STAY_INTERVAL = getF(paramSectFile, paramString, "StayInterval", 0.1F, 200.0F);

      localCarProperties.soundMove = getS(paramSectFile, paramString, "SoundMove");
      if ("none".equals(localCarProperties.soundMove)) {
        localCarProperties.soundMove = null;
      }

      Property.set(paramClass, "meshName", localCarProperties.meshSummer);
      Property.set(paramClass, "speed", localCarProperties.SPEED_AVERAGE);

      if (paramSectFile.get(paramString, "Soldiers", -9865.3447F) == -9865.3447F)
        localCarProperties.NUM_HUMANS = 0;
      else {
        localCarProperties.NUM_HUMANS = (int)getF(paramSectFile, paramString, "Soldiers", 1.0F, 2.0F);
      }

      return localCarProperties;
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
        this.proper = LoadCarProperties(Statics.getTechnicsFile(), str2, paramClass);
      }
      catch (Exception localException)
      {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("Problem in car spawn: " + paramClass.getName());
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
        this.proper.MESH0_NAME = this.proper.meshWinter;
        this.proper.MESH1_NAME = this.proper.meshWinter1;
        break;
      case 2:
        this.proper.MESH0_NAME = this.proper.meshDesert;
        this.proper.MESH1_NAME = this.proper.meshDesert1;
        break;
      default:
        this.proper.MESH0_NAME = this.proper.meshSummer;
        this.proper.MESH1_NAME = this.proper.meshSummer1;
      }

      CarGeneric localCarGeneric = null;
      try
      {
        CarGeneric.access$1802(this.proper);
        CarGeneric.access$1902(paramActor);
        localCarGeneric = (CarGeneric)this.cls.newInstance();
        CarGeneric.access$1802(null);
        CarGeneric.access$1902(null);
      } catch (Exception localException) {
        CarGeneric.access$1802(null);
        CarGeneric.access$1902(null);
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("SPAWN: Can't create Car object [class:" + this.cls.getName() + "]");

        return null;
      }

      return localCarGeneric;
    }
  }

  class Move extends Interpolate
  {
    Move()
    {
    }

    public boolean tick()
    {
      if (CarGeneric.this.dying != 0) {
        return false;
      }

      if ((CarGeneric.this.timeHumanLaunch > 0L) && (Time.current() >= CarGeneric.this.timeHumanLaunch)) {
        ChiefGround localChiefGround1 = (ChiefGround)this.actor.getOwner();
        if (localChiefGround1.getCodeOfBridgeSegment((UnitInterface)this.actor) < 0) {
          CarGeneric.this.LaunchHumans();
          CarGeneric.access$802(CarGeneric.this, Time.current() + (int)CarGeneric.Rnd(12000.0F, 17000.0F));
        } else {
          CarGeneric.access$802(CarGeneric.this, Time.current() + (int)CarGeneric.Rnd(3000.0F, 5000.0F));
        }
        CarGeneric.access$802(CarGeneric.this, -CarGeneric.this.timeHumanLaunch);
      }

      int i = (CarGeneric.this.mov.moveCurTime < 0L) && (CarGeneric.this.mov.rotatCurTime < 0L) ? 1 : 0;
      if ((CarGeneric.this.isNetMirror()) && (i != 0))
      {
        CarGeneric.this.mov.switchToStay(30.0F);
        i = 0;
      }
      if (i != 0) {
        ChiefGround localChiefGround2 = (ChiefGround)CarGeneric.this.getOwner();

        float f2 = -1.0F;
        UnitMove localUnitMove;
        if (CarGeneric.this.collisionStage == 0) {
          localUnitMove = localChiefGround2.AskMoveCommand(this.actor, null, CarGeneric.this.obs);
        }
        else
        {
          float f3;
          Vector2d localVector2d;
          if (CarGeneric.this.collisionStage == 1)
          {
            CarGeneric.this.obs.collision(CarGeneric.this.collidee, localChiefGround2, CarGeneric.this.udata);
            CarGeneric.access$1102(CarGeneric.this, null);

            f3 = CarGeneric.Rnd(-70.0F, 70.0F);
            localVector2d = CarGeneric.Rotate(CarGeneric.this.collisVector, f3);
            localVector2d.scale(CarGeneric.this.prop.AFTER_COLLISION_DIST * CarGeneric.Rnd(0.87D, 1.75D));
            CarGeneric.p.set(localVector2d.x, localVector2d.y, -1.0D);
            localUnitMove = localChiefGround2.AskMoveCommand(this.actor, CarGeneric.p, CarGeneric.this.obs);
            CarGeneric.access$902(CarGeneric.this, 2);
            f2 = CarGeneric.this.prop.SPEED_BACK;
          }
          else {
            f3 = CarGeneric.Rnd(0.0F, 359.98999F);
            localVector2d = CarGeneric.Rotate(CarGeneric.this.collisVector, f3);
            localVector2d.scale(CarGeneric.this.prop.AFTER_COLLISION_DIST * CarGeneric.Rnd(0.2D, 0.6D));
            CarGeneric.p.set(localVector2d.x, localVector2d.y, 1.0D);
            localUnitMove = localChiefGround2.AskMoveCommand(this.actor, CarGeneric.p, CarGeneric.this.obs);
            CarGeneric.access$902(CarGeneric.this, 0);
          }
        }

        CarGeneric.this.mov.set(localUnitMove, this.actor, CarGeneric.this.prop.SPEED_MAX, f2, CarGeneric.this.prop.ROT_SPEED_MAX, CarGeneric.this.prop.ROT_INVIS_ANG);

        if (CarGeneric.this.isNetMaster()) {
          CarGeneric.this.send_MoveCommand(CarGeneric.this.mov, f2);
        }

      }

      if (CarGeneric.this.mov.dstPos == null) {
        CarGeneric.this.mov.moveCurTime -= 1L;
        if ((CarGeneric.this.engineSFX != null) && 
          (CarGeneric.this.engineSTimer > 0)) {
          if (--CarGeneric.this.engineSTimer == 0) {
            CarGeneric.this.engineSFX.stop();
          }
        }

        return true;
      }

      if (CarGeneric.this.engineSFX != null) {
        if (CarGeneric.this.engineSTimer == 0) {
          CarGeneric.this.engineSFX.play();
          CarGeneric.this.engineSTimer = (int)CarGeneric.access$1500(CarGeneric.Rnd(10.0F, 12.0F));
        } else if (CarGeneric.this.engineSTimer < CarGeneric.this.ticksIn8secs) {
          CarGeneric.this.engineSTimer = (int)CarGeneric.access$1500(CarGeneric.Rnd(10.0F, 12.0F));
        }

      }

      CarGeneric.this.pos.getAbs(CarGeneric.o);
      int j = 0;

      if (CarGeneric.this.mov.rotatCurTime > 0L) {
        CarGeneric.this.mov.rotatCurTime -= 1L;

        float f1 = 1.0F - (float)CarGeneric.this.mov.rotatCurTime / (float)CarGeneric.this.mov.rotatTotTime;
        CarGeneric.o.setYaw(CarGeneric.this.mov.angles.getDeg(f1));
        j = 1;
        if (CarGeneric.this.mov.rotatCurTime <= 0L) {
          CarGeneric.this.mov.rotatCurTime = -1L;
          CarGeneric.this.mov.rotatingInPlace = false;
        }

      }

      if ((!CarGeneric.this.mov.rotatingInPlace) && (CarGeneric.this.mov.moveCurTime > 0L)) {
        CarGeneric.this.mov.moveCurTime -= 1L;

        double d = 1.0D - CarGeneric.this.mov.moveCurTime / CarGeneric.this.mov.moveTotTime;

        CarGeneric.p.x = (CarGeneric.this.mov.srcPos.x * (1.0D - d) + CarGeneric.this.mov.dstPos.x * d);
        CarGeneric.p.y = (CarGeneric.this.mov.srcPos.y * (1.0D - d) + CarGeneric.this.mov.dstPos.y * d);

        if (CarGeneric.this.mov.normal.z < 0.0F) {
          CarGeneric.p.z = (Engine.land().HQ(CarGeneric.p.x, CarGeneric.p.y) + CarGeneric.this.HeightAboveLandSurface());
          Engine.land().N(CarGeneric.p.x, CarGeneric.p.y, CarGeneric.n);
        }
        else {
          CarGeneric.p.z = (CarGeneric.this.mov.srcPos.z * (1.0D - d) + CarGeneric.this.mov.dstPos.z * d);
        }
        j = 0;
        CarGeneric.this.pos.setAbs(CarGeneric.p);
        if (CarGeneric.this.mov.moveCurTime <= 0L) {
          CarGeneric.this.mov.moveCurTime = -1L;
        }
      }

      if (CarGeneric.this.mov.normal.z < 0.0F)
      {
        if (j != 0) Engine.land().N(CarGeneric.this.mov.srcPos.x, CarGeneric.this.mov.srcPos.y, CarGeneric.n);

        CarGeneric.o.orient(CarGeneric.n);
      }
      else {
        CarGeneric.o.orient(CarGeneric.this.mov.normal);
      }
      CarGeneric.this.pos.setAbs(CarGeneric.o);
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

      Object localObject1 = CarGeneric.readPackedPos(paramNetMsgInput);
      Object localObject2 = CarGeneric.readPackedOri(paramNetMsgInput);

      CarGeneric.this.setPosition((Point3d)localObject1, (Orient)localObject2);
      CarGeneric.this.mov.switchToStay(20.0F);

      switch (i)
      {
      case 73:
      case 105:
        if (CarGeneric.this.dying != 0) {
          System.out.println("Car is dead at init stage");
        }
        if (i != 105) break;
        CarGeneric.this.DieMirror(null, false); break;
      case 67:
        CarGeneric.this.doCollisionDeath();
        break;
      case 65:
        localObject1 = paramNetMsgInput.readNetObj();
        localObject2 = localObject1 == null ? null : ((ActorNet)localObject1).actor();
        CarGeneric.this.doAbsoluteDeath((Actor)localObject2);

        break;
      case 68:
        if (CarGeneric.this.dying != 0) break;
        localObject1 = paramNetMsgInput.readNetObj();
        localObject2 = localObject1 == null ? null : ((ActorNet)localObject1).actor();
        CarGeneric.this.DieMirror((Actor)localObject2, true);
        break;
      default:
        System.out.println("CarGeneric: Unknown G message (" + i + ")");
        return false;
      }

      return true;
    }

    private boolean handleNonguaranted(NetMsgInput paramNetMsgInput) throws IOException
    {
      int i = paramNetMsgInput.readByte();
      switch (i)
      {
      case 68:
        this.out.unLockAndSet(paramNetMsgInput, 1);
        this.out.setIncludeTime(false);
        postRealTo(Message.currentRealTime(), masterChannel(), this.out);
        break;
      case 83:
        if (isMirrored()) {
          this.out.unLockAndSet(paramNetMsgInput, 0);
          this.out.setIncludeTime(false);
          postReal(Message.currentRealTime(), this.out);
        }

        CarGeneric.this.mov.switchToStay(10.0F);
        break;
      case 77:
      case 109:
        int j = i == 77 ? 1 : 0;

        if (isMirrored()) {
          this.out.unLockAndSet(paramNetMsgInput, 0);
          this.out.setIncludeTime(true);
          postReal(Message.currentRealTime(), this.out);
        }

        Point3d localPoint3d = CarGeneric.readPackedPos(paramNetMsgInput);
        Vector3f localVector3f = new Vector3f(0.0F, 0.0F, 0.0F);
        localVector3f.z = CarGeneric.readPackedNormal(paramNetMsgInput);
        if (localVector3f.z >= 0.0F) {
          localVector3f.x = CarGeneric.readPackedNormal(paramNetMsgInput);
          localVector3f.y = CarGeneric.readPackedNormal(paramNetMsgInput);
          float f1 = localVector3f.length();
          if (f1 > 0.001F)
            localVector3f.scale(1.0F / f1);
          else {
            localVector3f.set(0.0F, 0.0F, 1.0F);
          }
        }
        int k = paramNetMsgInput.readUnsignedShort();
        float f2 = 0.001F * (float)(Message.currentGameTime() - Time.current() + k);

        if (f2 <= 0.0F) {
          f2 = 0.1F;
        }

        UnitMove localUnitMove = new UnitMove(0.0F, localPoint3d, f2, localVector3f, -1.0F);

        if (CarGeneric.this.dying != 0) break;
        CarGeneric.this.mov.set(localUnitMove, actor(), 2.0F * CarGeneric.this.prop.SPEED_MAX, j != 0 ? 2.0F * CarGeneric.this.prop.SPEED_BACK : -1.0F, 1.3F * CarGeneric.this.prop.ROT_SPEED_MAX, 1.1F * CarGeneric.this.prop.ROT_INVIS_ANG); break;
      default:
        System.out.println("CarGeneric: Unknown NG message");
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
        if (CarGeneric.this.dying != 0) {
          break;
        }
        NetObj localNetObj = paramNetMsgInput.readNetObj();
        Actor localActor = localNetObj == null ? null : ((ActorNet)localNetObj).actor();
        CarGeneric.this.Die(localActor);
        break;
      case 67:
        CarGeneric.this.collisionDeath();
        break;
      default:
        System.out.println("CarGeneric: Unknown M message (" + i + ")");
        return false;
      }

      return true;
    }
  }

  protected static class CarProperties
    implements Cloneable
  {
    public int codeName = 0;

    public String MESH0_NAME = "3do/cars/None.him";
    public String MESH1_NAME = null;

    public String meshSummer = null;
    public String meshDesert = null;
    public String meshWinter = null;

    public String meshSummer1 = null;
    public String meshDesert1 = null;
    public String meshWinter1 = null;

    public String soundMove = "models.Car";

    public TableFunction2 fnShotPanzer = null;
    public TableFunction2 fnExplodePanzer = null;

    public float PANZER_BODY_FRONT = 0.001F;
    public float PANZER_BODY_BACK = 0.001F;
    public float PANZER_BODY_SIDE = 0.001F;
    public float PANZER_BODY_TOP = 0.001F;

    public float PANZER_TNT_TYPE = 1.0F;

    public String explodeName = null;

    public int HITBY_MASK = -1;

    public float SPEED_AVERAGE = CarGeneric.KmHourToMSec(1.0F);
    public float SPEED_MAX = CarGeneric.KmHourToMSec(2.0F);
    public float SPEED_BACK = CarGeneric.KmHourToMSec(1.0F);
    public float ROT_SPEED_MAX = 3.6F;
    public float ROT_INVIS_ANG = 360.0F;

    public float BEST_SPACE = 2.0F;

    public float AFTER_COLLISION_DIST = 0.1F;

    public float COMMAND_INTERVAL = 20.0F;

    public float STAY_INTERVAL = 30.0F;

    public int NUM_HUMANS = 0;

    public Object clone() {
      try {
        return super.clone(); } catch (Exception localException) {
      }
      return null;
    }
  }
}