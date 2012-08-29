package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.AirportCarrier;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Airdrome;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiNetSendMethods;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetSquadron;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.NetWing;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Message;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgNet;
import com.maddox.rts.NetAddress;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetChannelOutStream;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.NetUpdate;
import com.maddox.rts.Property;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.util.IntHashtable;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public abstract class NetAircraft extends SndAircraft
{
  public static final int FM_AI = 0;
  public static final int FM_REAL = 1;
  public static final int FM_NET_CLIENT = 2;
  static long timeOfPrevSpawn = -1L;
  public static String loadingCountry;
  public static boolean loadingCoopPlane;
  protected String thisWeaponsName = null;
  protected boolean bPaintShemeNumberOn = true;
  private boolean bCoopPlane;
  public static boolean ZUTI_REFLY_OWERRIDE = false;
  private FlightModelTrack fmTrack;
  public static final int NETG_ID_TOMASTER = 128;
  public static final int NETG_ID_CODE_ASTATE = 0;
  public static final int NETG_ID_CODE_UPDATE_WAY = 1;
  public static final int NETG_ID_CODE_UPDATE_WEAPONS = 2;
  public static final int NETG_ID_CODE_GUNPODS_ON = 3;
  public static final int NETG_ID_CODE_GUNPODS_OFF = 4;
  public static final int NETG_ID_CODE_DROP_FUEL_TANKS = 5;
  public static final int NETG_ID_CODE_HIT = 6;
  public static final int NETG_ID_CODE_HIT_PROP = 7;
  public static final int NETG_ID_CODE_CUT = 8;
  public static final int NETG_ID_CODE_EXPLODE = 9;
  public static final int NETG_ID_CODE_DEAD = 10;
  public static final int NETG_ID_CODE_FIRST_UPDATE = 11;
  public static final int NETG_ID_CODE_COCKPIT_ENTER = 12;
  public static final int NETG_ID_CODE_COCKPIT_AUTO = 13;
  public static final int NETG_ID_CODE_COCKPIT_DRIVER = 14;
  public static final int NETG_ID_CODE_DROP_EXTERNAL_STORES = 15;
  protected boolean bGunPodsExist = false;
  protected boolean bGunPodsOn = true;

  private int netCockpitIndxPilot = 0;
  private int netCockpitWeaponControlNum = 0;
  private int netCockpitTuretNum = -1;
  private boolean netCockpitValid = false;
  private NetMsgGuaranted netCockpitMsg = null;

  private boolean bWeaponsEventLog = false;

  private Actor[] netCockpitDrivers = null;

  private static Point3d corn = new Point3d();
  private static Point3d corn1 = new Point3d();
  private static Point3d pship = new Point3d();
  private static Loc lCorn = new Loc();

  static ClipFilter clipFilter = new ClipFilter();

  private ArrayList damagers = new ArrayList();
  private Actor damagerExclude = null;

  private Actor damager_ = null;

  private boolean isCoopPlane()
  {
    return this.bCoopPlane;
  }

  protected static String[] partNames()
  {
    return Aircraft.partNames();
  }
  protected int curDMGLevel(int paramInt) { return 0; } 
  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor) {
  }
  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor) {
  }
  protected void netHits(int paramInt1, int paramInt2, int paramInt3, Actor paramActor) {
  }
  protected void doExplosion() {
  }
  public int curDMGProp(int paramInt) {
    return 0;
  }
  protected void weaponsLoad(String paramString) throws Exception {
  }
  protected void createCockpits() {
  }
  public void setFM(int paramInt, boolean paramBoolean) {
  }
  public void preparePaintScheme() {
  }
  public void prepareCamouflage() {
  }
  public int aircIndex() { return -1; } 
  public Wing getWing() {
    return null;
  }

  public void onAircraftLoaded() {
  }

  public NetUser netUser() {
    if (!isNet()) return null;
    return ((AircraftNet)this.net).netUser;
  }
  public String netName() {
    if (!isNet()) return null;
    return ((AircraftNet)this.net).netName;
  }
  public boolean isNetPlayer() {
    if (!isNet()) return false;
    return ((AircraftNet)this.net).netUser != null;
  }

  public void moveSteering(float paramFloat)
  {
  }

  public void moveWheelSink() {
  }

  public void setFMTrack(FlightModelTrack paramFlightModelTrack) {
    this.fmTrack = paramFlightModelTrack;
  }
  public FlightModelTrack fmTrack() {
    return this.fmTrack;
  }
  public boolean isFMTrackMirror() {
    return (this.fmTrack != null) && (this.fmTrack.isMirror());
  }

  public boolean netNewAState_isEnable(boolean paramBoolean)
  {
    if (!isNet()) return false;
    if ((paramBoolean) && (this.net.isMaster())) return false;
    if ((!paramBoolean) && (!this.net.isMirrored())) return false;
    return (!paramBoolean) || (!(this.net.masterChannel() instanceof NetChannelInStream));
  }

  public NetMsgGuaranted netNewAStateMsg(boolean paramBoolean)
    throws IOException
  {
    if (!isNet()) return null;
    if ((paramBoolean) && (this.net.isMaster())) return null;
    if ((!paramBoolean) && (!this.net.isMirrored())) return null;
    if ((paramBoolean) && ((this.net.masterChannel() instanceof NetChannelInStream))) return null;
    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    if (paramBoolean) localNetMsgGuaranted.writeByte(128); else
      localNetMsgGuaranted.writeByte(0);
    return localNetMsgGuaranted;
  }

  public void netSendAStateMsg(boolean paramBoolean, NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
    if (paramBoolean) this.net.postTo(this.net.masterChannel(), paramNetMsgGuaranted); else
      this.net.post(paramNetMsgGuaranted);
  }

  public void netUpdateWayPoint()
  {
    if ((this.net == null) || (Main.cur().netServerParams == null) || (!Main.cur().netServerParams.isCoop()) || (Main.cur().netServerParams.isMaster()) || (!this.net.isMaster()) || (!this.net.isMirrored()))
    {
      return;
    }Master localMaster = (Master)this.net;
    if (localMaster.curWayPoint != this.FM.AP.way.Cur()) {
      localMaster.curWayPoint = this.FM.AP.way.Cur();
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(1);
        localNetMsgGuaranted.writeShort(localMaster.curWayPoint);
        localMaster.postTo(Main.cur().netServerParams.masterChannel(), localNetMsgGuaranted); } catch (Exception localException) {
        printDebug(localException);
      }
    }
  }

  private boolean netGetUpdateWayPoint(NetMsgInput paramNetMsgInput, boolean paramBoolean1, boolean paramBoolean2) throws IOException {
    if (paramBoolean1)
      return false;
    int i = paramNetMsgInput.readUnsignedShort();
    if (Main.cur().netServerParams.isMaster()) {
      this.FM.AP.way.setCur(i);
      if (i == this.FM.AP.way.size() - 1)
        this.FM.AP.way.next();
    } else {
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(1);
        localNetMsgGuaranted.writeShort(i);
        this.net.postTo(Main.cur().netServerParams.masterChannel(), localNetMsgGuaranted); } catch (Exception localException) {
        printDebug(localException);
      }
    }
    return true;
  }

  private int getWeaponsAmount()
  {
    int i = this.FM.CT.Weapons.length;
    if (this.FM.CT.Weapons.length == 0) return 0;
    int j = 0;
    for (int k = 0; k < i; k++) {
      BulletEmitter[] arrayOfBulletEmitter = this.FM.CT.Weapons[k];
      if (arrayOfBulletEmitter != null)
        for (int m = 0; m < arrayOfBulletEmitter.length; m++) {
          if (arrayOfBulletEmitter[m] == null) continue; j++;
        }
    }
    return j;
  }
  private boolean isWeaponsAllEmpty() {
    int i = this.FM.CT.Weapons.length;
    if (this.FM.CT.Weapons.length == 0) return true;
    for (int j = 0; j < i; j++) {
      BulletEmitter[] arrayOfBulletEmitter = this.FM.CT.Weapons[j];
      if (arrayOfBulletEmitter != null)
        for (int k = 0; k < arrayOfBulletEmitter.length; k++)
          if ((arrayOfBulletEmitter[k] != null) && (arrayOfBulletEmitter[k].countBullets() != 0))
            return false;
    }
    return true;
  }
  private byte[] getWeaponsBitStatesBuf(byte[] paramArrayOfByte) {
    int i = getWeaponsAmount();
    if (i == 0) return null;
    int j = (i + 7) / 8;
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length != j))
      paramArrayOfByte = new byte[j];
    for (int k = 0; k < j; k++) paramArrayOfByte[k] = 0;
    return paramArrayOfByte;
  }
  private byte[] getWeaponsBitStates(byte[] paramArrayOfByte) {
    paramArrayOfByte = getWeaponsBitStatesBuf(paramArrayOfByte);
    if (paramArrayOfByte == null) return null;
    int i = 0;
    int j = this.FM.CT.Weapons.length;
    for (int k = 0; k < j; k++) {
      BulletEmitter[] arrayOfBulletEmitter = this.FM.CT.Weapons[k];
      if (arrayOfBulletEmitter != null)
        for (int m = 0; m < arrayOfBulletEmitter.length; m++)
          if (arrayOfBulletEmitter[m] != null) {
            if (arrayOfBulletEmitter[m].countBullets() != 0)
            {
              int tmp98_97 = (i / 8);
              byte[] tmp98_93 = paramArrayOfByte; tmp98_93[tmp98_97] = (byte)(tmp98_93[tmp98_97] | 1 << i % 8);
            }i++;
          }
    }
    return paramArrayOfByte;
  }
  private void setWeaponsBitStates(byte[] paramArrayOfByte) {
    int i = 0;
    int j = this.FM.CT.Weapons.length;
    for (int k = 0; k < j; k++) {
      BulletEmitter[] arrayOfBulletEmitter = this.FM.CT.Weapons[k];
      if (arrayOfBulletEmitter != null)
        for (int m = 0; m < arrayOfBulletEmitter.length; m++)
          if (arrayOfBulletEmitter[m] != null) {
            if ((paramArrayOfByte[(i / 8)] & 1 << i % 8) == 0)
              arrayOfBulletEmitter[m]._loadBullets(0);
            i++;
          }
    }
  }

  private boolean isWeaponsChanged(byte[] paramArrayOfByte) {
    if (getWeaponsAmount() == 0) return false;
    if (paramArrayOfByte == null) return true;
    int i = 0;
    int j = this.FM.CT.Weapons.length;
    for (int k = 0; k < j; k++) {
      BulletEmitter[] arrayOfBulletEmitter = this.FM.CT.Weapons[k];
      if (arrayOfBulletEmitter != null)
        for (int m = 0; m < arrayOfBulletEmitter.length; m++)
          if (arrayOfBulletEmitter[m] != null) {
            if (((paramArrayOfByte[(i / 8)] & 1 << i % 8) == 0 ? 1 : 0) != (arrayOfBulletEmitter[m].countBullets() == 0 ? 1 : 0))
              return true;
            i++;
          }
    }
    return false;
  }
  private void netPutWeaponsBitStates(byte[] paramArrayOfByte) {
    if ((!isNet()) || (this.net.countMirrors() == 0)) return; try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(2);
      localNetMsgGuaranted.write(paramArrayOfByte);
      this.net.post(localNetMsgGuaranted); } catch (Exception localException) {
      printDebug(localException);
    }
  }

  private boolean netGetWeaponsBitStates(NetMsgInput paramNetMsgInput, boolean paramBoolean1, boolean paramBoolean2) throws IOException {
    if ((paramBoolean1) || (paramBoolean2))
      return false;
    byte[] arrayOfByte = getWeaponsBitStatesBuf(null);
    for (int i = 0; i < arrayOfByte.length; i++)
      arrayOfByte[i] = (byte)paramNetMsgInput.readUnsignedByte();
    setWeaponsBitStates(arrayOfByte);
    netPutWeaponsBitStates(arrayOfByte);
    return true;
  }

  public boolean isGunPodsExist()
  {
    return this.bGunPodsExist; } 
  public boolean isGunPodsOn() { return this.bGunPodsOn; }

  public void setGunPodsOn(boolean paramBoolean) {
    if (this.bGunPodsOn == paramBoolean) return;
    for (int i = 0; i < this.FM.CT.Weapons.length; i++) {
      BulletEmitter[] arrayOfBulletEmitter = this.FM.CT.Weapons[i];
      if (arrayOfBulletEmitter != null) {
        for (int j = 0; j < arrayOfBulletEmitter.length; j++)
          if (arrayOfBulletEmitter[j] != null)
            arrayOfBulletEmitter[j].setPause(!paramBoolean);
      }
    }
    this.bGunPodsOn = paramBoolean;

    if ((!isNet()) || (this.net.countMirrors() == 0)) return; try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      if (paramBoolean) localNetMsgGuaranted.writeByte(3); else
        localNetMsgGuaranted.writeByte(4);
      this.net.post(localNetMsgGuaranted); } catch (Exception localException) {
      printDebug(localException);
    }
  }

  public void replicateDropFuelTanks()
  {
    if ((!isNet()) || (this.net.countMirrors() == 0)) return; try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(5);
      this.net.post(localNetMsgGuaranted); } catch (Exception localException) {
      printDebug(localException);
    }
  }

  public void replicateDropExternalStores()
  {
    if ((isNet()) && (this.net.countMirrors() != 0))
    {
      try
      {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(15);
        this.net.post(localNetMsgGuaranted);
      }
      catch (Exception localException)
      {
        printDebug(localException);
      }
    }
  }

  protected void netPutHits(boolean paramBoolean, NetChannel paramNetChannel, int paramInt1, int paramInt2, int paramInt3, Actor paramActor)
  {
    if ((!paramBoolean) && (this.net.countMirrors() == 0))
      return;
    if ((!Actor.isValid(paramActor)) || (!paramActor.isNet()))
      return;
    try {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      if (paramBoolean) localNetMsgGuaranted.writeByte(134); else
        localNetMsgGuaranted.writeByte(6);
      localNetMsgGuaranted.writeByte(paramInt1 & 0xF | paramInt2 << 4 & 0xF0);
      localNetMsgGuaranted.writeByte(paramInt3);
      localNetMsgGuaranted.writeNetObj(paramActor.net);

      if (paramBoolean) this.net.postTo(this.net.masterChannel(), localNetMsgGuaranted); else
        this.net.postExclude(paramNetChannel, localNetMsgGuaranted); 
    } catch (Exception localException) {
      printDebug(localException);
    }
  }

  private boolean netGetHits(NetMsgInput paramNetMsgInput, boolean paramBoolean1, boolean paramBoolean2) throws IOException {
    if ((paramBoolean1) && (!paramBoolean2))
      return false;
    int i = paramNetMsgInput.readUnsignedByte();
    int j = i >> 4 & 0xF;
    i &= 15;
    int k = paramNetMsgInput.readUnsignedByte();
    if (k >= 44)
      return false;
    NetObj localNetObj = paramNetMsgInput.readNetObj();
    if (localNetObj == null)
      return true;
    Actor localActor = (Actor)localNetObj.superObj();
    if (!Actor.isValid(localActor)) {
      return true;
    }

    if ((!paramBoolean1) && (paramBoolean2))
      netPutHits(true, null, i, j, k, localActor);
    if (this.net.countMirrors() > 1) {
      netPutHits(false, paramNetMsgInput.channel(), i, j, k, localActor);
    }
    netHits(i, j, k, localActor);
    return true;
  }

  public void hitProp(int paramInt1, int paramInt2, Actor paramActor)
  {
    if ((isNet()) && (this.net.isMirrored()))
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(7);
        localNetMsgGuaranted.writeByte(paramInt1);
        localNetMsgGuaranted.writeByte(paramInt2);
        localNetMsgGuaranted.writeNetObj(paramActor != null ? paramActor.net : null);
        this.net.post(localNetMsgGuaranted); } catch (Exception localException) {
        printDebug(localException);
      }
  }

  private boolean netGetHitProp(NetMsgInput paramNetMsgInput, boolean paramBoolean1, boolean paramBoolean2) throws IOException
  {
    if ((paramBoolean1) || (paramBoolean2))
      return false;
    int i = paramNetMsgInput.readUnsignedByte();
    int j = paramNetMsgInput.readUnsignedByte();
    NetObj localNetObj = paramNetMsgInput.readNetObj();
    hitProp(i, j, localNetObj != null ? (Actor)(Actor)localNetObj.superObj() : null);
    return true;
  }

  protected void netPutCut(int paramInt1, int paramInt2, Actor paramActor)
  {
    if ((!isNet()) || (this.net.countMirrors() == 0)) return; try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(8);
      localNetMsgGuaranted.writeByte(paramInt1);
      localNetMsgGuaranted.writeByte(paramInt2);
      localNetMsgGuaranted.writeNetObj(paramActor != null ? paramActor.net : null);
      this.net.post(localNetMsgGuaranted); } catch (Exception localException) {
      printDebug(localException);
    }
  }

  private boolean netGetCut(NetMsgInput paramNetMsgInput, boolean paramBoolean1, boolean paramBoolean2) throws IOException {
    if ((paramBoolean1) || (paramBoolean2))
      return false;
    int i = paramNetMsgInput.readUnsignedByte();
    if (i >= 44)
      return false;
    int j = paramNetMsgInput.readUnsignedByte();
    NetObj localNetObj = paramNetMsgInput.readNetObj();
    nextCUTLevel(partNames()[i] + "_D0", j, localNetObj != null ? (Actor)(Actor)localNetObj.superObj() : null);
    return true;
  }

  public void netExplode()
  {
    if ((!isNet()) || (this.net.countMirrors() == 0)) return; try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(9);
      this.net.post(localNetMsgGuaranted); } catch (Exception localException) {
      printDebug(localException);
    }
  }

  private boolean netGetExplode(NetMsgInput paramNetMsgInput, boolean paramBoolean1, boolean paramBoolean2) throws IOException {
    if ((paramBoolean1) || (paramBoolean2))
      return false;
    netExplode();
    doExplosion();
    return true;
  }

  public void setDiedFlag(boolean paramBoolean)
  {
    if ((isAlive()) && (paramBoolean) && (isNet()) && (Actor.isValid(getDamager())) && (getDamager().isNet()) && (this.net.countMirrors() > 0))
    {
      try
      {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(10);
        localNetMsgGuaranted.writeNetObj(getDamager().net);
        this.net.post(localNetMsgGuaranted); } catch (Exception localException) {
        printDebug(localException);
      }
    }
    super.setDiedFlag(paramBoolean);
  }

  private boolean netGetDead(NetMsgInput paramNetMsgInput, boolean paramBoolean1, boolean paramBoolean2) throws IOException
  {
    NetObj localNetObj = paramNetMsgInput.readNetObj();
    if (localNetObj != null) {
      if (isAlive())
        World.onActorDied(this, (Actor)localNetObj.superObj());
      if (this.net.countMirrors() > 0)
        try {
          NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
          localNetMsgGuaranted.writeByte(10);
          localNetMsgGuaranted.writeNetObj(localNetObj);
          this.net.post(localNetMsgGuaranted); } catch (Exception localException) {
          printDebug(localException);
        }
    }
    return true;
  }

  public void netFirstUpdate(NetChannel paramNetChannel)
    throws IOException
  {
    if (((!Mission.isCoop()) || (!isNetPlayer())) && (
      ((paramNetChannel instanceof NetChannelOutStream)) || (Mission.isDogfight()))) {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(11);
      netReplicateFirstUpdate(paramNetChannel, localNetMsgGuaranted);
      this.net.postTo(paramNetChannel, localNetMsgGuaranted);
      if ((Mission.isSingle()) && 
        (World.getPlayerAircraft() == this)) {
        if (fmTrack() == null) {
          if (isNetMaster())
            new MsgAction(true, this) {
              public void doAction(Object paramObject) { new FlightModelTrack((Aircraft)paramObject); } } ;
        }
        else MsgNet.postRealNewChannel(fmTrack(), paramNetChannel);

      }

    }

    netCockpitFirstUpdate(this, paramNetChannel);
  }
  private boolean netGetFirstUpdate(NetMsgInput paramNetMsgInput) throws IOException {
    ActorSpawnArg localActorSpawnArg = new ActorSpawnArg();
    try {
      netSpawnCommon(paramNetMsgInput, localActorSpawnArg);
      this.pos.setAbs(localActorSpawnArg.point, localActorSpawnArg.orient);
      this.pos.reset();
      setSpeed(localActorSpawnArg.speed);
      netSpawnCommon(paramNetMsgInput, localActorSpawnArg, this); } catch (Exception localException) {
      printDebug(localException);
    }return true;
  }

  public int netCockpitAstatePilotIndx(int paramInt)
  {
    return netCockpitAstatePilotIndx(getClass(), paramInt);
  }

  public static int netCockpitAstatePilotIndx(Class paramClass, int paramInt) {
    if (paramInt < 0) return -1;
    Object localObject = Property.value(paramClass, "cockpitClass");
    if (localObject == null) return -1;
    if ((localObject instanceof Class)) {
      if (paramInt > 0) return -1;
      return Property.intValue((Class)localObject, "astatePilotIndx", 0);
    }
    Class[] arrayOfClass = (Class[])(Class[])localObject;
    if (paramInt >= arrayOfClass.length) return -1;
    return Property.intValue(arrayOfClass[paramInt], "astatePilotIndx", 0);
  }

  public void netCockpitAuto(Actor paramActor, int paramInt, boolean paramBoolean)
  {
    short[] arrayOfShort = null;
    int i = 0;
    Object localObject1 = Property.value(getClass(), "cockpitClass");
    if (localObject1 == null) return;
    Object localObject2;
    if ((localObject1 instanceof Class)) {
      if (paramInt > 0) return;
      i = Property.intValue((Class)localObject1, "weaponControlNum", 10);
    } else {
      localObject2 = (Class[])(Class[])localObject1;
      if (paramInt >= localObject2.length) return;
      i = Property.intValue(localObject2[paramInt], "weaponControlNum", 10);
    }
    if (World.cur().diffCur.Limited_Ammo) {
      localObject2 = this.FM.CT.Weapons[i];
      if (localObject2 != null) {
        arrayOfShort = new short[localObject2.length];
        for (int j = 0; j < localObject2.length; j++) {
          int k = localObject2[j].countBullets();
          if (k < 0) arrayOfShort[j] = -1; else
            arrayOfShort[j] = (short)k;
        }
      }
    }
    netCockpitAuto(paramActor, paramInt, paramBoolean, arrayOfShort, null);
  }

  private void netCockpitAuto(Actor paramActor, int paramInt, boolean paramBoolean, short[] paramArrayOfShort, NetChannel paramNetChannel) {
    Object localObject1 = Property.value(getClass(), "cockpitClass");
    if (localObject1 == null) return;
    Class localClass = null;
    if ((localObject1 instanceof Class)) {
      if (paramInt > 0) return;
      localClass = (Class)localObject1;
    } else {
      Class[] arrayOfClass = (Class[])(Class[])localObject1;
      if (paramInt >= arrayOfClass.length) return;
      localClass = arrayOfClass[paramInt];
    }
    if (!CockpitPilot.class.isAssignableFrom(localClass)) {
      i = Property.intValue(localClass, "weaponControlNum", 10);
      int j = Property.intValue(localClass, "aiTuretNum", 0);
      if (this == World.getPlayerAircraft()) {
        localObject2 = (CockpitGunner)Main3D.cur3D().cockpits[paramInt];
        ((CockpitGunner)localObject2).setRealMode(!paramBoolean);
      } else {
        localObject2 = this.FM.turret[j];
        ((Turret)localObject2).bIsAIControlled = paramBoolean;
      }
      Object localObject2 = this.FM.CT.Weapons[i];
      if (localObject2 != null) {
        boolean bool = (!paramActor.net.isMaster()) || (World.cur().diffCur.Realistic_Gunnery);
        if (paramBoolean) bool = true;
        for (int m = 0; m < localObject2.length; m++) {
          if ((localObject2[m] instanceof Actor)) {
            ((Actor)localObject2[m]).setOwner(paramBoolean ? this : paramActor);
            if ((localObject2[m] instanceof Gun)) {
              ((Gun)localObject2[m]).initRealisticGunnery(bool);
            }
          }
          if (paramArrayOfShort != null) {
            int n = paramArrayOfShort[m];
            if (n == 65535) n = -1;
            localObject2[m]._loadBullets(n);
          } else if (!World.cur().diffCur.Limited_Ammo) {
            localObject2[m].loadBullets(-1);
          }
        }
      }
      if ((paramActor instanceof NetGunner)) {
        ((NetGunner)paramActor).netCockpitTuretNum = (paramBoolean ? -1 : j);
        ((NetGunner)paramActor).netCockpitWeaponControlNum = i;
      } else {
        this.netCockpitTuretNum = (paramBoolean ? -1 : j);
        this.netCockpitWeaponControlNum = i;
      }
    }
    else if ((paramActor instanceof NetGunner)) { ((NetGunner)paramActor).netCockpitTuretNum = -1; } else {
      this.netCockpitTuretNum = -1;
    }

    int i = this.net.countMirrors();
    if (this.net.isMirror()) i++;
    if (paramNetChannel != null) i--;
    if (i > 0) {
      if ((paramActor instanceof NetGunner)) ((NetGunner)paramActor).netCockpitValid = false; else
        this.netCockpitValid = false;
      try {
        2 local2 = new NetMsgGuaranted() {
          public void unLocking() {
            try {
              Actor localActor = (Actor)((NetObj)objects().get(0)).superObj();
              if ((localActor instanceof NetGunner)) {
                if (((NetGunner)localActor).netCockpitMsg == this)
                  ((NetGunner)localActor).netCockpitValid = true;
              }
              else if (NetAircraft.this.netCockpitMsg == this)
                NetAircraft.access$102(NetAircraft.this, true);
            } catch (Exception localException) {
              NetAircraft.printDebug(localException);
            }
          }
        };
        if ((paramActor instanceof NetGunner)) ((NetGunner)paramActor).netCockpitMsg = local2; else
          this.netCockpitMsg = local2;
        local2.writeByte(13);
        local2.writeNetObj(paramActor.net);
        if (paramBoolean) paramInt |= 128;
        local2.writeByte(paramInt);
        if (paramArrayOfShort != null)
          for (int k = 0; k < paramArrayOfShort.length; k++)
            local2.writeShort(paramArrayOfShort[k]);
        this.net.postExclude(paramNetChannel, local2); } catch (Exception localException) {
        printDebug(localException);
      }
    } else if ((paramActor instanceof NetGunner)) { ((NetGunner)paramActor).netCockpitValid = true; } else {
      this.netCockpitValid = true;
    }
  }

  private boolean netGetCockpitAuto(NetMsgInput paramNetMsgInput, boolean paramBoolean1, boolean paramBoolean2) throws IOException
  {
    NetObj localNetObj = paramNetMsgInput.readNetObj();
    if (localNetObj == null) return false;
    Actor localActor = (Actor)localNetObj.superObj();
    int i = paramNetMsgInput.readUnsignedByte();
    boolean bool = (i & 0x80) != 0;
    i &= -129;
    short[] arrayOfShort = null;
    int j = paramNetMsgInput.available() / 2;
    if (j > 0) {
      arrayOfShort = new short[j];
      for (int k = 0; k < arrayOfShort.length; k++)
        arrayOfShort[k] = (short)paramNetMsgInput.readUnsignedShort();
    }
    netCockpitAuto(localActor, i, bool, arrayOfShort, paramNetMsgInput.channel());
    return true;
  }

  public void netCockpitEnter(Actor paramActor, int paramInt) {
    netCockpitEnter(paramActor, paramInt, true);
  }
  public void netCockpitEnter(Actor paramActor, int paramInt, boolean paramBoolean) {
    if (paramBoolean) {
      if ((paramActor instanceof NetGunner)) {
        EventLog.onOccupied((Aircraft)this, ((NetGunner)paramActor).getUser(), netCockpitAstatePilotIndx(paramInt));
      } else {
        EventLog.onOccupied((Aircraft)this, ((Aircraft)paramActor).netUser(), netCockpitAstatePilotIndx(paramInt));

        if ((paramActor == World.getPlayerAircraft()) && (paramActor.isNetMaster()) && (paramInt == 0) && (!this.bWeaponsEventLog))
        {
          this.bWeaponsEventLog = true;
          EventLog.onWeaponsLoad(paramActor, this.thisWeaponsName, (int)(this.FM.M.fuel * 100.0F / this.FM.M.maxFuel));
        }
      }
    }
    netCockpitEnter(paramActor, paramInt, null);
  }

  private void netCockpitEnter(Actor paramActor, int paramInt, NetChannel paramNetChannel)
  {
    int i = this.netCockpitIndxPilot;
    if ((paramActor instanceof NetGunner)) {
      i = ((NetGunner)paramActor).netCockpitIndxPilot;
    }
    Class localClass1 = null;
    Class localClass2 = null;
    Object localObject = Property.value(getClass(), "cockpitClass");
    if (localObject == null) return;
    if ((localObject instanceof Class)) {
      if (i > 0) return;
      if (paramInt > 0) return;
      localClass1 = localClass2 = (Class)localObject;
    } else {
      Class[] arrayOfClass = (Class[])(Class[])localObject;
      if (i >= arrayOfClass.length) return;
      if (paramInt >= arrayOfClass.length) return;
      localClass1 = arrayOfClass[i];
      localClass2 = arrayOfClass[paramInt];
    }
    Turret localTurret1;
    if (!CockpitPilot.class.isAssignableFrom(localClass1)) {
      j = Property.intValue(localClass1, "aiTuretNum", 0);
      localTurret1 = this.FM.turret[j];
      localTurret1.bIsNetMirror = false;
    }
    if (!CockpitPilot.class.isAssignableFrom(localClass2)) {
      j = Property.intValue(localClass2, "aiTuretNum", 0);
      localTurret1 = this.FM.turret[j];
      localTurret1.bIsNetMirror = paramActor.net.isMirror();
    }
    if ((paramActor instanceof NetGunner))
      ((NetGunner)paramActor).netCockpitIndxPilot = paramInt;
    else {
      this.netCockpitIndxPilot = paramInt;
    }
    netCockpitDriverSet(paramActor, paramInt);

    int j = 0;
    int k = -1;
    if (!CockpitPilot.class.isAssignableFrom(localClass2)) {
      k = Property.intValue(localClass2, "aiTuretNum", 0);
      Turret localTurret2 = this.FM.turret[k];
      if (localTurret2.bIsAIControlled)
        k = -1;
      else {
        j = Property.intValue(localClass2, "weaponControlNum", 10);
      }
    }
    if ((paramActor instanceof NetGunner)) {
      ((NetGunner)paramActor).netCockpitTuretNum = k;
      ((NetGunner)paramActor).netCockpitWeaponControlNum = j;
    } else {
      this.netCockpitTuretNum = k;
      this.netCockpitWeaponControlNum = j;
    }

    int m = this.net.countMirrors();
    if (this.net.isMirror()) m++;
    if (paramNetChannel != null) m--;
    if (m > 0)
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(12);
        localNetMsgGuaranted.writeNetObj(paramActor.net);
        localNetMsgGuaranted.writeByte(paramInt);
        this.net.postExclude(paramNetChannel, localNetMsgGuaranted); } catch (Exception localException) {
        printDebug(localException);
      }
  }

  private boolean netGetCockpitEnter(NetMsgInput paramNetMsgInput, boolean paramBoolean1, boolean paramBoolean2) throws IOException
  {
    NetObj localNetObj = paramNetMsgInput.readNetObj();
    if (localNetObj == null) return false;
    Actor localActor = (Actor)localNetObj.superObj();
    int i = paramNetMsgInput.readUnsignedByte();
    netCockpitEnter(localActor, i, paramNetMsgInput.channel());
    return true;
  }

  protected void netCockpitFirstUpdate(Actor paramActor, NetChannel paramNetChannel) throws IOException {
    int i = this.netCockpitIndxPilot;
    int j = this.netCockpitTuretNum;
    if ((paramActor instanceof NetGunner)) {
      i = ((NetGunner)paramActor).netCockpitIndxPilot;
      j = ((NetGunner)paramActor).netCockpitTuretNum;
    }
    Object localObject1;
    if (i != 0) {
      localObject1 = new NetMsgGuaranted();
      ((NetMsgGuaranted)localObject1).writeByte(12);
      ((NetMsgGuaranted)localObject1).writeNetObj(paramActor.net);
      ((NetMsgGuaranted)localObject1).writeByte(i);
      this.net.postTo(paramNetChannel, (NetMsgGuaranted)localObject1);
    }
    if (j >= 0) {
      localObject1 = null;
      Object localObject2 = Property.value(getClass(), "cockpitClass");
      if (localObject2 == null) return;
      Class localClass = null;
      if ((localObject2 instanceof Class)) {
        if (i > 0) return;
        localClass = (Class)localObject2;
      } else {
        Class[] arrayOfClass = (Class[])(Class[])localObject2;
        if (i >= arrayOfClass.length) return;
        localClass = arrayOfClass[i];
      }
      int k = Property.intValue(localClass, "weaponControlNum", 10);
      int m;
      if (World.cur().diffCur.Limited_Ammo) {
        localObject3 = this.FM.CT.Weapons[k];
        if (localObject3 != null) {
          localObject1 = new short[localObject3.length];
          for (m = 0; m < localObject3.length; m++) {
            int n = localObject3[m].countBullets();
            if (n < 0) localObject1[m] = -1; else
              localObject1[m] = (short)n;
          }
        }
      }
      Object localObject3 = new NetMsgGuaranted() {
        public void unLocking() {
          Actor localActor = (Actor)((NetObj)objects().get(0)).superObj();
          if ((localActor instanceof NetGunner)) {
            if (((NetGunner)localActor).netCockpitMsg == this)
              ((NetGunner)localActor).netCockpitValid = true;
          }
          else if (NetAircraft.this.netCockpitMsg == this)
            NetAircraft.access$102(NetAircraft.this, true);
        }
      };
      if ((paramActor instanceof NetGunner)) {
        if (!((NetGunner)paramActor).netCockpitValid)
          ((NetGunner)paramActor).netCockpitMsg = ((NetMsgGuaranted)localObject3);
      }
      else if (!this.netCockpitValid) {
        this.netCockpitMsg = ((NetMsgGuaranted)localObject3);
      }
      ((NetMsgGuaranted)localObject3).writeByte(13);
      ((NetMsgGuaranted)localObject3).writeNetObj(paramActor.net);
      ((NetMsgGuaranted)localObject3).writeByte(i);
      if (localObject1 != null)
        for (m = 0; m < localObject1.length; m++)
          ((NetMsgGuaranted)localObject3).writeShort(localObject1[m]);
      this.net.postTo(paramNetChannel, (NetMsgGuaranted)localObject3);
    }
  }

  private boolean netCockpitCheckDrivers()
  {
    if (this.netCockpitDrivers != null) return true;
    Object localObject = Property.value(getClass(), "cockpitClass");
    if (localObject == null) return false;
    if ((localObject instanceof Class)) {
      this.netCockpitDrivers = new Actor[1];
    } else {
      Class[] arrayOfClass = (Class[])(Class[])localObject;
      this.netCockpitDrivers = new Actor[arrayOfClass.length];
    }
    return true;
  }

  public Actor netCockpitGetDriver(int paramInt) {
    if (!netCockpitCheckDrivers())
      return null;
    if ((paramInt < 0) || (paramInt >= this.netCockpitDrivers.length))
      return null;
    return this.netCockpitDrivers[paramInt];
  }

  private void netCockpitDriverSet(Actor paramActor, int paramInt) {
    if (!netCockpitCheckDrivers())
      return;
    NetUser localNetUser = netUser();
    if ((paramActor instanceof NetGunner))
      localNetUser = ((NetGunner)paramActor).getUser();
    if (localNetUser == null) {
      localNetUser = (NetUser)NetEnv.host();
    }
    for (int i = 0; i < this.netCockpitDrivers.length; i++)
      if (this.netCockpitDrivers[i] == paramActor) {
        this.netCockpitDrivers[i] = null;
        localNetUser.tryPreparePilotDefaultSkin((Aircraft)this, netCockpitAstatePilotIndx(i));
      }
    this.netCockpitDrivers[paramInt] = paramActor;
    localNetUser.tryPreparePilotSkin(this, netCockpitAstatePilotIndx(paramInt));
  }

  public void netCockpitDriverRequest(Actor paramActor, int paramInt) {
    if (!netCockpitCheckDrivers())
      return;
    if ((paramInt < 0) || (paramInt >= this.netCockpitDrivers.length)) return;
    if (this.net.isMaster()) {
      if (this.netCockpitDrivers[paramInt] != null)
        return;
      netCockpitDriverSet(paramActor, paramInt);
      Main3D.cur3D().aircraftHotKeys.netSwitchToCockpit(paramInt);
    } else {
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(14);
        localNetMsgGuaranted.writeNetObj(paramActor.net);
        localNetMsgGuaranted.writeByte(paramInt);
        this.net.postTo(this.net.masterChannel(), localNetMsgGuaranted); } catch (Exception localException) {
        printDebug(localException);
      }
    }
  }

  private boolean netGetCockpitDriver(NetMsgInput paramNetMsgInput, boolean paramBoolean1, boolean paramBoolean2) throws IOException {
    NetObj localNetObj = paramNetMsgInput.readNetObj();
    if (localNetObj == null) return false;
    Actor localActor = (Actor)localNetObj.superObj();
    int i = paramNetMsgInput.readUnsignedByte();
    if (!netCockpitCheckDrivers())
      return false;
    if ((i < 0) || (i >= this.netCockpitDrivers.length)) return true;
    if (paramBoolean1) {
      if (this.netCockpitDrivers[i] != null)
        return true;
      netCockpitDriverSet(localActor, i);
    }
    else if (paramBoolean2) {
      try {
        NetMsgGuaranted localNetMsgGuaranted1 = new NetMsgGuaranted();
        localNetMsgGuaranted1.writeByte(14);
        localNetMsgGuaranted1.writeNetObj(localActor.net);
        localNetMsgGuaranted1.writeByte(i);
        this.net.postTo(this.net.masterChannel(), localNetMsgGuaranted1); } catch (Exception localException1) {
        printDebug(localException1);
      }
    } else {
      netCockpitDriverSet(localActor, i);
      if (localActor.net.isMaster()) {
        Main3D.cur3D().aircraftHotKeys.netSwitchToCockpit(i);
      }
    }
    if (this.net.countMirrors() > 0)
      try {
        NetMsgGuaranted localNetMsgGuaranted2 = new NetMsgGuaranted();
        localNetMsgGuaranted2.writeByte(14);
        localNetMsgGuaranted2.writeNetObj(localActor.net);
        localNetMsgGuaranted2.writeByte(i);
        this.net.post(localNetMsgGuaranted2); } catch (Exception localException2) {
        printDebug(localException2);
      }
    return true;
  }

  boolean netGetGMsg(NetMsgInput paramNetMsgInput, boolean paramBoolean)
    throws IOException
  {
    int i = paramNetMsgInput.readUnsignedByte();
    boolean bool = (i & 0x80) == 128;
    i &= -129;
    switch (i) {
    case 0:
      this.FM.AS.netUpdate(paramBoolean, bool, paramNetMsgInput);
      return true;
    case 1:
      return netGetUpdateWayPoint(paramNetMsgInput, paramBoolean, bool);
    case 2:
      return netGetWeaponsBitStates(paramNetMsgInput, paramBoolean, bool);
    case 3:
      setGunPodsOn(true);
      return true;
    case 4:
      setGunPodsOn(false);
      return true;
    case 5:
      this.FM.CT.dropFuelTanks();
      return true;
    case 6:
      return netGetHits(paramNetMsgInput, paramBoolean, bool);
    case 7:
      return netGetHitProp(paramNetMsgInput, paramBoolean, bool);
    case 8:
      return netGetCut(paramNetMsgInput, paramBoolean, bool);
    case 9:
      return netGetExplode(paramNetMsgInput, paramBoolean, bool);
    case 10:
      return netGetDead(paramNetMsgInput, paramBoolean, bool);
    case 11:
      return netGetFirstUpdate(paramNetMsgInput);
    case 12:
      return netGetCockpitEnter(paramNetMsgInput, paramBoolean, bool);
    case 13:
      return netGetCockpitAuto(paramNetMsgInput, paramBoolean, bool);
    case 14:
      return netGetCockpitDriver(paramNetMsgInput, paramBoolean, bool);
    case 15:
      this.FM.CT.dropExternalStores(false);
      return true;
    }
    return false;
  }

  protected void sendMsgSndShot(Shot paramShot)
  {
    int i = paramShot.mass > 0.05F ? 1 : 0;
    _tmpPoint.set(this.pos.getAbsPoint());
    _tmpPoint.sub(paramShot.p);
    int j = (int)(_tmpPoint.x / 0.25D) & 0xFE;
    int k = (int)(_tmpPoint.y / 0.25D) & 0xFE;
    i &= 3;
    try {
      NetMsgFiltered localNetMsgFiltered = new NetMsgFiltered();
      localNetMsgFiltered.writeByte(j | i >> 1);
      localNetMsgFiltered.writeByte(k | i & 0x1);
      this.net.postTo(Time.current(), this.net.masterChannel(), localNetMsgFiltered);
    }
    catch (Exception localException)
    {
    }
  }

  protected void msgSndShot(float paramFloat, double paramDouble1, double paramDouble2, double paramDouble3)
  {
  }

  public void makeMirrorCarrierRelPos()
  {
    if ((this.net == null) || (this.net.isMaster())) return;
    ((Mirror)this.net).makeFirstUnderDeck();
  }

  public boolean isMirrorUnderDeck() {
    if ((this.net == null) || (this.net.isMaster())) return false;
    return ((Mirror)this.net).bUnderDeck;
  }

  public void destroy()
  {
    if (isDestroyed()) return;
    if ((isNetMaster()) && (this.fmTrack != null) && (!this.fmTrack.isDestroyed()))
      this.fmTrack.destroy();
    this.fmTrack = null;
    super.destroy();
    this.damagers.clear();
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

  public void restoreLinksInCoopWing()
  {
    if ((Main.cur().netServerParams == null) || (!Main.cur().netServerParams.isCoop()))
      return;
    Wing localWing = getWing();
    Aircraft[] arrayOfAircraft = localWing.airc;

    int i = 0;
    while ((i < arrayOfAircraft.length) && 
      (!Actor.isValid(arrayOfAircraft[i]))) {
      i++;
    }

    if (i == arrayOfAircraft.length)
      return;
    arrayOfAircraft[i].FM.Leader = null;
    for (int j = i + 1; j < arrayOfAircraft.length; j++)
      if (Actor.isValid(arrayOfAircraft[j])) {
        arrayOfAircraft[i].FM.Wingman = arrayOfAircraft[j].FM;
        arrayOfAircraft[j].FM.Leader = arrayOfAircraft[i].FM;
        i = j;
      }
  }

  public void restoreLinksInDogWing()
  {
    if (Main.cur().netServerParams == null) return;

    Wing localWing = getWing();
    Aircraft[] arrayOfAircraft = localWing.airc;

    int i = 0;
    while ((i < arrayOfAircraft.length) && 
      (!Actor.isValid(arrayOfAircraft[i]))) {
      i++;
    }

    if (i == arrayOfAircraft.length)
      return;
    arrayOfAircraft[i].FM.Leader = null;
    for (int j = i + 1; j < arrayOfAircraft.length; j++)
      if (Actor.isValid(arrayOfAircraft[j])) {
        arrayOfAircraft[i].FM.Wingman = arrayOfAircraft[j].FM;
        arrayOfAircraft[j].FM.Leader = arrayOfAircraft[i].FM;
        i = j;
      }
  }

  private static void netSpawnCommon(NetMsgInput paramNetMsgInput, ActorSpawnArg paramActorSpawnArg)
    throws Exception
  {
    paramActorSpawnArg.point = new Point3d(paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat());
    paramActorSpawnArg.orient = new Orient(paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat());
    paramActorSpawnArg.speed = new Vector3d(paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat());
  }

  private static void netSpawnCommon(NetMsgInput paramNetMsgInput, ActorSpawnArg paramActorSpawnArg, NetAircraft paramNetAircraft) throws Exception {
    Mirror localMirror = (Mirror)paramNetAircraft.net;
    localMirror.netFirstUpdate((float)paramActorSpawnArg.point.x, (float)paramActorSpawnArg.point.y, (float)paramActorSpawnArg.point.z, paramActorSpawnArg.orient.azimut(), paramActorSpawnArg.orient.tangage(), paramActorSpawnArg.orient.kren(), (float)paramActorSpawnArg.speed.x, (float)paramActorSpawnArg.speed.y, (float)paramActorSpawnArg.speed.z);

    int i = 0;
    for (int j = 0; j < 44; j++) {
      int k = 0;
      if ((j & 0x1) == 0) {
        i = paramNetMsgInput.readUnsignedByte();
        k = i & 0xFF;
      } else {
        k = i >> 8 & 0xFF;
      }
      while (k-- > 0) {
        paramNetAircraft.nextDMGLevel(partNames()[j] + "_D0", 0, null);
      }
    }
    long l1 = paramNetMsgInput.readLong();
    if (l1 != paramNetAircraft.FM.Operate)
    {
      m = 0; for (long l2 = 1L; m < 44; l2 <<= 1) {
        if (((l1 & l2) == 0L) && ((paramNetAircraft.FM.Operate & l2) != 0L))
          paramNetAircraft.nextCUTLevel(partNames()[m] + "_D0", 0, null);
        m++;
      }

    }

    int m = paramNetMsgInput.readByte();
    for (int n = 0; n < 4; n++)
      if ((m & 1 << n) != 0)
        paramNetAircraft.hitProp(n, 0, null);
    if ((m & 0x10) != 0) {
      paramNetAircraft.setGunPodsOn(false);
    }
    byte[] arrayOfByte = paramNetAircraft.getWeaponsBitStatesBuf(null);
    if (arrayOfByte != null) {
      for (int i1 = 0; i1 < arrayOfByte.length; i1++)
        arrayOfByte[i1] = (byte)paramNetMsgInput.readUnsignedByte();
      paramNetAircraft.setWeaponsBitStates(arrayOfByte);
    }

    try
    {
      paramNetAircraft.FM.AS.netFirstUpdate(paramNetMsgInput);
    }
    catch (Exception localException)
    {
      System.out.println("NetAircraft error, ID_03: " + localException.toString());
    }
  }

  private void netReplicateCommon(NetChannel paramNetChannel, NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
    paramNetMsgGuaranted.writeFloat(this.FM.M.fuel / this.FM.M.maxFuel);
    paramNetMsgGuaranted.writeBoolean(this.bPaintShemeNumberOn);
    paramNetMsgGuaranted.write255(((AircraftNet)this.net).netName);
    paramNetMsgGuaranted.write255(this.thisWeaponsName);
    paramNetMsgGuaranted.writeNetObj(((AircraftNet)this.net).netUser);
  }

  private void netReplicateFirstUpdate(NetChannel paramNetChannel, NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
    Point3d localPoint3d = this.pos.getAbsPoint();
    paramNetMsgGuaranted.writeFloat((float)localPoint3d.x); paramNetMsgGuaranted.writeFloat((float)localPoint3d.y); paramNetMsgGuaranted.writeFloat((float)localPoint3d.z);
    Orient localOrient = this.pos.getAbsOrient();
    paramNetMsgGuaranted.writeFloat(localOrient.azimut()); paramNetMsgGuaranted.writeFloat(localOrient.tangage()); paramNetMsgGuaranted.writeFloat(localOrient.kren());
    Vector3d localVector3d = new Vector3d();
    getSpeed(localVector3d);
    paramNetMsgGuaranted.writeFloat((float)localVector3d.x); paramNetMsgGuaranted.writeFloat((float)localVector3d.y); paramNetMsgGuaranted.writeFloat((float)localVector3d.z);

    int i = 0;
    int j = 0;
    for (; j < 44; j++) {
      if ((j & 0x1) == 0) {
        i = curDMGLevel(j) & 0xFF;
      } else {
        i |= (curDMGLevel(j) & 0xFF) << 8;
        paramNetMsgGuaranted.writeByte(i);
      }
    }
    if ((j & 0x1) == 1) {
      paramNetMsgGuaranted.writeByte(i);
    }

    paramNetMsgGuaranted.writeLong(this.FM.Operate);

    int k = curDMGProp(0) | curDMGProp(1) << 1 | curDMGProp(2) << 2 | curDMGProp(3) << 3;

    if (!isGunPodsOn()) k |= 16;
    paramNetMsgGuaranted.writeByte(k);

    byte[] arrayOfByte = getWeaponsBitStates(null);
    if (arrayOfByte != null)
      paramNetMsgGuaranted.write(arrayOfByte);
    this.FM.AS.netReplicate(paramNetMsgGuaranted);
  }

  private NetMsgSpawn netReplicateCoop(NetChannel paramNetChannel) throws IOException {
    NetMsgSpawn localNetMsgSpawn = super.netReplicate(paramNetChannel);
    localNetMsgSpawn.writeByte(1);
    netReplicateCommon(paramNetChannel, localNetMsgSpawn);
    netReplicateFirstUpdate(paramNetChannel, localNetMsgSpawn);
    return localNetMsgSpawn;
  }

  private NetMsgSpawn _netReplicate(NetChannel paramNetChannel) throws IOException {
    NetMsgSpawn localNetMsgSpawn = super.netReplicate(paramNetChannel);
    localNetMsgSpawn.writeByte(0);
    localNetMsgSpawn.writeByte(getArmy());
    netReplicateCommon(paramNetChannel, localNetMsgSpawn);
    netReplicateFirstUpdate(paramNetChannel, localNetMsgSpawn);
    return localNetMsgSpawn;
  }

  public NetMsgSpawn netReplicate(NetChannel paramNetChannel) throws IOException {
    if (Main.cur().netServerParams == null) return null;
    if (paramNetChannel.isMirrored(this.net)) return null;
    NetMsgSpawn localNetMsgSpawn = null;
    if (isCoopPlane())
      localNetMsgSpawn = netReplicateCoop(paramNetChannel);
    else
      localNetMsgSpawn = _netReplicate(paramNetChannel);
    if ((World.getPlayerAircraft() == this) && ((paramNetChannel instanceof NetChannelOutStream))) {
      if (fmTrack() == null) {
        if (isNetMaster())
          new MsgAction(true, this) {
            public void doAction(Object paramObject) { new FlightModelTrack((Aircraft)paramObject); } } ;
      }
      else MsgNet.postRealNewChannel(fmTrack(), paramNetChannel);
    }

    return localNetMsgSpawn;
  }

  public NetAircraft()
  {
    this.bCoopPlane = loadingCoopPlane;
  }

  public void setDamagerExclude(Actor paramActor)
  {
    this.damagerExclude = paramActor;
    if (this.damager_ == paramActor)
      this.damager_ = null;
  }

  public void setDamager(Actor paramActor) {
    setDamager(paramActor, 1);
  }

  public void setDamager(Actor paramActor, int paramInt) {
    if ((!Actor.isValid(paramActor)) || (this == paramActor))
    {
      return;
    }if (paramInt <= 0)
      return;
    if (paramInt > 4)
      paramInt = 4;
    this.damager_ = null;
    int i = this.damagers.size();
    DamagerItem localDamagerItem;
    for (int j = 0; j < i; j++) {
      localDamagerItem = (DamagerItem)this.damagers.get(j);
      if (localDamagerItem.damager == paramActor) {
        localDamagerItem.damage += paramInt;
        localDamagerItem.lastTime = Time.current();
        return;
      }
    }
    this.damagers.add(new DamagerItem(paramActor, paramInt));

    if (World.cur().isDebugFM()) {
      Aircraft.debugprintln(this, "Printing Registered Damagers: *****");
      for (j = 0; j < i; j++) {
        localDamagerItem = (DamagerItem)this.damagers.get(j);
        if (Actor.isValid(localDamagerItem.damager))
          Aircraft.debugprintln(localDamagerItem.damager, "inflicted " + localDamagerItem.damage + " puntos..");
      }
    }
  }

  public Actor getDamager()
  {
    if (Actor.isValid(this.damager_)) return this.damager_;

    this.damager_ = null;

    long l1 = 0L;
    Actor localActor1 = null;
    long l2 = 0L;
    Actor localActor2 = null;
    Actor localActor3 = null;
    int i = this.damagers.size();
    for (int j = 0; j < i; j++) {
      DamagerItem localDamagerItem = (DamagerItem)this.damagers.get(j);
      if (localDamagerItem.damager == this.damagerExclude)
        continue;
      if (Actor.isValid(localDamagerItem.damager)) {
        if ((localDamagerItem.damager instanceof Aircraft)) {
          if (localDamagerItem.lastTime > l2) {
            l2 = localDamagerItem.lastTime;
            localActor2 = localDamagerItem.damager;
          }
        } else if (localDamagerItem.damager == Engine.actorLand()) {
          localActor3 = localDamagerItem.damager;
        }
        else if (localDamagerItem.lastTime > l1) {
          l1 = localDamagerItem.lastTime;
          localActor1 = localDamagerItem.damager;
        }
      }
    }

    if (localActor2 != null)
      this.damager_ = localActor2;
    else if (localActor1 != null)
      this.damager_ = localActor1;
    else if (localActor3 != null) {
      this.damager_ = localActor3;
    }

    return this.damager_;
  }

  public boolean isDamagerExclusive() {
    int i = 0;
    for (int j = 0; j < this.damagers.size(); j++)
      if (this.damagerExclude != this.damagers.get(j))
        i++;
    return i == 1;
  }

  protected static void printDebug(Exception paramException) {
    System.out.println(paramException.getMessage());
    paramException.printStackTrace();
  }

  public int getPilotsCount() {
    return this.FM.crew;
  }

  public static boolean isOnCarrierDeck(AirportCarrier paramAirportCarrier, Loc paramLoc)
  {
    return isOnCarrierDeck(paramAirportCarrier, paramLoc.getPoint());
  }

  public static boolean isOnCarrierDeck(AirportCarrier paramAirportCarrier, Point3d paramPoint3d)
  {
    Point3d localPoint3d1 = new Point3d();
    Point3d localPoint3d2 = new Point3d();
    localPoint3d1.set(paramPoint3d);
    localPoint3d2.set(paramPoint3d);
    localPoint3d1.z = Engine.cur.land.HQ(paramPoint3d.x, paramPoint3d.y);
    localPoint3d1.z += 40.0D;
    Actor localActor = Engine.collideEnv().getLine(localPoint3d1, localPoint3d2, false, clipFilter, pship);

    return paramAirportCarrier.ship() == localActor;
  }

  private boolean zutiCheckIfSelectedPlaneIsAvailable(NetUser paramNetUser)
  {
    if (paramNetUser == null) {
      return false;
    }
    try
    {
      String str = Property.stringValue(((Aircraft)this).getClass(), "keyName");

      BornPlace localBornPlace = (BornPlace)World.cur().bornPlaces.get(paramNetUser.getBornPlace());
      if (localBornPlace != null)
      {
        if ((!localBornPlace.zutiEnablePlaneLimits) || (localBornPlace.zutiIsAcAvailable(str)))
          return true;
      }
    } catch (Exception localException) {
      System.out.println("NetAircraft error, ID_04: " + localException.toString());
    }return false;
  }

  static class DamagerItem
  {
    public Actor damager;
    public int damage;
    public long lastTime;

    public DamagerItem(Actor paramActor, int paramInt)
    {
      this.damager = paramActor;
      this.damage = paramInt;
      this.lastTime = Time.current();
    }
  }

  public static class SPAWN
    implements ActorSpawn, NetSpawn
  {
    public Class cls;
    private NetUser _netUser;

    public SPAWN(Class paramClass)
    {
      this.cls = paramClass;
      Spawn.add(this.cls, this);
    }

    private Actor actorSpawnCoop(ActorSpawnArg paramActorSpawnArg)
    {
      if (paramActorSpawnArg.name == null)
        return null;
      String str = paramActorSpawnArg.name.substring(3);
      int i = 0;
      NetAircraft localNetAircraft1 = (NetAircraft)Actor.getByName(str);
      if (localNetAircraft1 == null) {
        localNetAircraft1 = (NetAircraft)Actor.getByName(" " + str);
        if (localNetAircraft1 != null)
          i = 1;
      }
      if (localNetAircraft1 == null) {
        return null;
      }
      paramActorSpawnArg.name = null;
      Wing localWing = localNetAircraft1.getWing();
      NetAircraft localNetAircraft2 = null;
      NetAircraft.loadingCountry = localWing.regiment().country();
      try {
        localNetAircraft2 = (NetAircraft)this.cls.newInstance();
      } catch (Exception localException1) {
        NetAircraft.loadingCountry = null;
        NetAircraft.printDebug(localException1);
        return null;
      }
      NetAircraft.access$1502(localNetAircraft2, true);

      int j = localNetAircraft1.aircIndex();
      if (i == 0) {
        localNetAircraft1.setName(" " + str);
        localNetAircraft1.collide(false);
      }
      localNetAircraft2.setName(str);

      if ((paramActorSpawnArg.bPlayer) && (paramActorSpawnArg.netChannel == null)) {
        World.setPlayerAircraft((Aircraft)localNetAircraft2);
        localNetAircraft2.setFM(1, true);
        World.setPlayerFM();
      }
      else if (Mission.isServer()) {
        localNetAircraft2.setFM(1, paramActorSpawnArg.netChannel == null);
      } else if ((this._netUser != null) && (this._netUser.isTrackWriter())) {
        World.setPlayerAircraft((Aircraft)localNetAircraft2);
        localNetAircraft2.setFM(1, false);
        World.setPlayerFM();
      } else {
        localNetAircraft2.setFM(2, paramActorSpawnArg.netChannel == null);
      }

      localNetAircraft2.FM.M.fuel = (paramActorSpawnArg.fuel * localNetAircraft2.FM.M.maxFuel);
      localNetAircraft2.bPaintShemeNumberOn = paramActorSpawnArg.bNumberOn;
      localNetAircraft2.FM.AS.bIsEnableToBailout = localNetAircraft1.FM.AS.bIsEnableToBailout;
      localNetAircraft2.createNetObject(paramActorSpawnArg.netChannel, paramActorSpawnArg.netIdRemote);
      ((NetAircraft.AircraftNet)localNetAircraft2.net).netUser = this._netUser;
      ((NetAircraft.AircraftNet)localNetAircraft2.net).netName = str;
      localNetAircraft2.FM.setSkill(localNetAircraft1.FM.Skill);
      try
      {
        localNetAircraft2.weaponsLoad(paramActorSpawnArg.weapons);
        localNetAircraft2.thisWeaponsName = paramActorSpawnArg.weapons; } catch (Exception localException2) {
        NetAircraft.printDebug(localException2);
      }
      if ((this._netUser != null) && ((localNetAircraft2.net.isMaster()) || (this._netUser.isTrackWriter()))) {
        localNetAircraft2.createCockpits();
      }
      localNetAircraft2.FM.AP.way = new Way(localNetAircraft1.FM.AP.way);

      localNetAircraft2.onAircraftLoaded();

      localWing.airc[j] = ((Aircraft)localNetAircraft2);
      localNetAircraft2.setArmy(localNetAircraft1.getArmy());
      localNetAircraft2.setOwner(localWing);
      if ((this._netUser != null) && ((localNetAircraft2.net.isMaster()) || (this._netUser.isTrackWriter())))
        World.setPlayerRegiment();
      if (Mission.isServer())
        ((Maneuver)localNetAircraft1.FM).Group.changeAircraft((Aircraft)localNetAircraft1, (Aircraft)localNetAircraft2);
      localNetAircraft2.FM.CT.set(localNetAircraft1.FM.CT);
      localNetAircraft2.FM.CT.forceGear(localNetAircraft2.FM.CT.GearControl);
      ((Aircraft)localNetAircraft2); Aircraft.forceGear(localNetAircraft2.getClass(), localNetAircraft2.hierMesh(), localNetAircraft2.FM.CT.getGear());
      localNetAircraft2.pos.setAbs(localNetAircraft1.pos.getAbs());
      localNetAircraft2.pos.reset();
      Vector3d localVector3d = new Vector3d();
      localNetAircraft1.getSpeed(localVector3d);
      localNetAircraft2.setSpeed(localVector3d);
      if (localNetAircraft1.FM.brakeShoe) {
        ((Aircraft)localNetAircraft2).FM.AP.way.takeoffAirport = localNetAircraft1.FM.AP.way.takeoffAirport;
        ((Aircraft)localNetAircraft2).FM.brakeShoe = true;
        ((Aircraft)localNetAircraft2).FM.turnOffCollisions = true;
        ((Aircraft)localNetAircraft2).FM.brakeShoeLoc.set(localNetAircraft1.FM.brakeShoeLoc);
        ((Aircraft)localNetAircraft2).FM.brakeShoeLastCarrier = localNetAircraft1.FM.brakeShoeLastCarrier;
        ((Aircraft)localNetAircraft2).FM.Gears.bFlatTopGearCheck = true;
        ((Aircraft)localNetAircraft2).makeMirrorCarrierRelPos();
      }
      if (localNetAircraft1.FM.CT.bHasWingControl) {
        ((Aircraft)localNetAircraft2).FM.CT.wingControl = localNetAircraft1.FM.CT.wingControl;
        ((Aircraft)localNetAircraft2).FM.CT.forceWing(localNetAircraft1.FM.CT.wingControl);
      }

      localNetAircraft2.preparePaintScheme();
      localNetAircraft2.prepareCamouflage();
      NetAircraft.loadingCountry = null;

      if (this._netUser != null) {
        this._netUser.tryPrepareSkin(localNetAircraft2);
        this._netUser.tryPrepareNoseart(localNetAircraft2);
        this._netUser.tryPreparePilot(localNetAircraft2);
        this._netUser.setArmy(localNetAircraft2.getArmy());
      } else if (Config.isUSE_RENDER()) {
        Mission.cur().prepareSkinAI((Aircraft)localNetAircraft2);
      }

      localNetAircraft2.restoreLinksInCoopWing();
      if ((localNetAircraft2.net.isMaster()) && ((!World.cur().diffCur.Takeoff_N_Landing) || (localNetAircraft1.FM.AP.way.get(0).Action != 1) || (!localNetAircraft1.FM.isStationedOnGround())))
      {
        localNetAircraft2.FM.EI.setCurControlAll(true);
        localNetAircraft2.FM.EI.setEngineRunning();
        localNetAircraft2.FM.CT.setPowerControl(0.75F);
        localNetAircraft2.FM.setStationedOnGround(false);
        localNetAircraft2.FM.setWasAirborne(true);
      }

      return localNetAircraft2;
    }
    private void netSpawnCoop(int paramInt, NetMsgInput paramNetMsgInput) {
      try {
        ActorSpawnArg localActorSpawnArg = new ActorSpawnArg();
        localActorSpawnArg.fuel = paramNetMsgInput.readFloat();
        localActorSpawnArg.bNumberOn = paramNetMsgInput.readBoolean();
        localActorSpawnArg.name = ("net" + paramNetMsgInput.read255());
        localActorSpawnArg.weapons = paramNetMsgInput.read255();
        this._netUser = ((NetUser)paramNetMsgInput.readNetObj());
        localActorSpawnArg.netChannel = paramNetMsgInput.channel();
        localActorSpawnArg.netIdRemote = paramInt;
        NetAircraft localNetAircraft = (NetAircraft)actorSpawnCoop(localActorSpawnArg);
        NetAircraft.access$1600(paramNetMsgInput, localActorSpawnArg);
        localNetAircraft.pos.setAbs(localActorSpawnArg.point, localActorSpawnArg.orient);
        localNetAircraft.pos.reset();
        localNetAircraft.setSpeed(localActorSpawnArg.speed);
        NetAircraft.access$1700(paramNetMsgInput, localActorSpawnArg, localNetAircraft); } catch (Exception localException) {
        NetAircraft.printDebug(localException);
      }
    }

    private Actor _actorSpawn(ActorSpawnArg paramActorSpawnArg) {
      int i = 0;
      Object localObject1 = null;
      NetSquadron localNetSquadron = null;
      NetWing localNetWing = null;
      int j = 0;
      NetAircraft localNetAircraft = null;
      try
      {
        int k = paramActorSpawnArg.name.length();
        int n;
        int i1;
        String str2;
        if (this._netUser != null) {
          j = Integer.parseInt(paramActorSpawnArg.name.substring(k - 2, k));
          n = paramActorSpawnArg.name.charAt(k - 3) - '0';
          i1 = paramActorSpawnArg.name.charAt(k - 4) - '0';
          if (k == 4) {
            localObject1 = this._netUser.netUserRegiment;
          } else {
            str2 = paramActorSpawnArg.name.substring(0, k - 4);
            localObject1 = (Regiment)Actor.getByName(str2);
          }
          localNetSquadron = new NetSquadron((Regiment)localObject1, i1);
          localNetWing = new NetWing(localNetSquadron, n);
        } else {
          j = Integer.parseInt(paramActorSpawnArg.name.substring(k - 1, k)) + 1;
          n = paramActorSpawnArg.name.charAt(k - 2) - '0';
          i1 = paramActorSpawnArg.name.charAt(k - 3) - '0';
          j += i1 * 16 + n * 4;
          str2 = paramActorSpawnArg.name.substring(0, k - 3);
          localObject1 = (Regiment)Actor.getByName(str2);
          localNetSquadron = new NetSquadron((Regiment)localObject1, i1);
          localNetWing = new NetWing(localNetSquadron, n);
        }
        NetAircraft.loadingCountry = localNetSquadron.regiment().country();
        localNetAircraft = (NetAircraft)this.cls.newInstance();
      } catch (Exception localException1) {
        if (localNetSquadron != null) localNetSquadron.destroy();
        if (localNetWing != null) localNetWing.destroy();
        NetAircraft.loadingCountry = null;
        NetAircraft.printDebug(localException1);
        return null;
      }
      NetAircraft.access$1502(localNetAircraft, false);

      localNetAircraft.createNetObject(paramActorSpawnArg.netChannel, paramActorSpawnArg.netIdRemote);
      ((NetAircraft.AircraftNet)localNetAircraft.net).netUser = this._netUser;
      ((NetAircraft.AircraftNet)localNetAircraft.net).netName = paramActorSpawnArg.name;
      Object localObject2;
      if (this._netUser != null)
      {
        if ((Main.cur().netServerParams != null) && (Main.cur().netServerParams.isMaster()))
        {
          try
          {
            if (this._netUser.masterChannel() != null)
            {
              String str1 = ((NetUser)NetEnv.host()).uniqueName();
              localObject2 = this._netUser.masterChannel().remoteAddress().getHostAddress().toString();

              if (ZutiSupportMethods.isPlayerBanned(str1, (String)localObject2))
              {
                ZutiNetSendMethods.playerBanned(this._netUser);
                i = 1;
              }
            }
          }
          catch (Exception localException2)
          {
            System.out.println("NetAircraft error ID_01: " + localException2.toString());
          }

          if (!localNetAircraft.zutiCheckIfSelectedPlaneIsAvailable(this._netUser))
          {
            ZutiNetSendMethods.aircraftNotAvailable(this._netUser);
            i = 1;
          }

        }

        paramActorSpawnArg.name = null;
        makeName(localNetAircraft);
      }

      if (((paramActorSpawnArg.bPlayer) && (paramActorSpawnArg.netChannel == null)) || ((this._netUser != null) && (this._netUser.isTrackWriter())))
      {
        World.cur().resetUser();
        World.setPlayerAircraft((Aircraft)localNetAircraft);
        localNetAircraft.setFM(1, paramActorSpawnArg.netChannel == null);
        World.setPlayerFM();
        paramActorSpawnArg.bPlayer = false;
      }
      else if (Mission.isServer()) {
        localNetAircraft.setFM(1, paramActorSpawnArg.netChannel == null);
      }
      else {
        localNetAircraft.setFM(2, paramActorSpawnArg.netChannel == null);
      }

      localNetAircraft.FM.setSkill(3);
      localNetAircraft.FM.M.fuel = (paramActorSpawnArg.fuel * localNetAircraft.FM.M.maxFuel);
      localNetAircraft.bPaintShemeNumberOn = paramActorSpawnArg.bNumberOn;
      try
      {
        localNetAircraft.weaponsLoad(paramActorSpawnArg.weapons);
        localNetAircraft.thisWeaponsName = paramActorSpawnArg.weapons; } catch (Exception localException3) {
        NetAircraft.printDebug(localException3);
      }
      if ((localNetAircraft.net.isMaster()) || ((this._netUser != null) && (this._netUser.isTrackWriter()))) {
        localNetAircraft.createCockpits();
      }
      localNetAircraft.onAircraftLoaded();

      NetAircraft.loadingCountry = null;

      int m = 0;
      Object localObject3;
      if (paramActorSpawnArg.bornPlaceExist) {
        localObject2 = (BornPlace)World.cur().bornPlaces.get(paramActorSpawnArg.bornPlace);
        localObject3 = ((BornPlace)localObject2).getAircraftPlace((Aircraft)localNetAircraft, paramActorSpawnArg.stayPlace);
        paramActorSpawnArg.point = ((Loc)localObject3).getPoint();
        paramActorSpawnArg.orient = ((Loc)localObject3).getOrient();
        paramActorSpawnArg.armyExist = true;
        paramActorSpawnArg.army = ((BornPlace)localObject2).army;
        paramActorSpawnArg.speed = new Vector3d();

        if ((((BornPlace)localObject2).zutiAirspawnOnly) || (!World.cur().diffCur.Takeoff_N_Landing) || ((paramActorSpawnArg.stayPlace >= World.cur().airdrome.stayHold.length) && (!localNetAircraft.FM.brakeShoe)))
        {
          paramActorSpawnArg.point.z = ((BornPlace)localObject2).zutiSpawnHeight;

          paramActorSpawnArg.orient = new Orient(((BornPlace)localObject2).zutiSpawnOrient - 86.0F, 0.0F, 0.0F);

          paramActorSpawnArg.speed.x = 1.0D;
          paramActorSpawnArg.orient.transform(paramActorSpawnArg.speed);
          paramActorSpawnArg.speed.normalize();
          paramActorSpawnArg.speed.scale(((BornPlace)localObject2).zutiSpawnSpeed / 3.4D);
          m = 1;
        } else {
          localNetAircraft.FM.CT.setLanded();
          ((Aircraft)localNetAircraft); Aircraft.forceGear(localNetAircraft.getClass(), localNetAircraft.hierMesh(), localNetAircraft.FM.CT.getGear());
        }
        localNetAircraft.FM.AS.bIsEnableToBailout = ((BornPlace)localObject2).bParachute;
      }
      else if ((Mission.isDogfight()) && (World.cur().diffCur.Takeoff_N_Landing))
      {
        if (Main.cur().netServerParams.isMaster())
        {
          localObject2 = new Loc(paramActorSpawnArg.point.x, paramActorSpawnArg.point.y, 0.0D, 0.0F, 0.0F, 0.0F);
          localObject3 = (AirportCarrier)Airport.nearest(((Loc)localObject2).getPoint(), -1, 4);
          if (localObject3 != null)
          {
            if (!NetAircraft.isOnCarrierDeck((AirportCarrier)localObject3, (Loc)localObject2))
            {
              localObject3 = null;
            }
          }
          if (localObject3 != null)
            ((AirportCarrier)localObject3).setCellUsed((Aircraft)localNetAircraft);
        }
      }
      paramActorSpawnArg.set(localNetAircraft);

      localNetWing.setPlane(localNetAircraft, j);

      if (m != 0) {
        localNetAircraft.FM.EI.setCurControlAll(true);
        localNetAircraft.FM.EI.setEngineRunning();
        localNetAircraft.FM.CT.setPowerControl(0.75F);
        localNetAircraft.FM.setStationedOnGround(false);
        localNetAircraft.FM.setWasAirborne(true);
      }
      if (paramActorSpawnArg.speed == null) {
        localNetAircraft.setSpeed(new Vector3d());
      }
      if (this._netUser != null) {
        this._netUser.tryPrepareSkin(localNetAircraft);
        this._netUser.tryPrepareNoseart(localNetAircraft);
        this._netUser.tryPreparePilot(localNetAircraft);
        this._netUser.setArmy(localNetAircraft.getArmy());
      } else if (Config.isUSE_RENDER()) {
        Mission.cur().prepareSkinAI((Aircraft)localNetAircraft);
      }

      if ((localNetAircraft.net.isMaster()) || ((this._netUser != null) && (this._netUser.isTrackWriter()))) {
        World.setPlayerRegiment();
      }

      if (i != 0) {
        localNetAircraft.destroy();
      }
      return (Actor)(Actor)(Actor)localNetAircraft;
    }

    private void makeName(NetAircraft paramNetAircraft) {
      String str = ((NetAircraft.AircraftNet)paramNetAircraft.net).netUser.uniqueName();
      int i = 0;
      while (Actor.getByName(str + "_" + i) != null)
        i++;
      paramNetAircraft.setName(str + "_" + i);
    }

    private void _netSpawn(int paramInt, NetMsgInput paramNetMsgInput) {
      try {
        ActorSpawnArg localActorSpawnArg = new ActorSpawnArg();
        localActorSpawnArg.army = paramNetMsgInput.readByte();
        localActorSpawnArg.armyExist = true;
        localActorSpawnArg.fuel = paramNetMsgInput.readFloat();
        localActorSpawnArg.bNumberOn = paramNetMsgInput.readBoolean();
        localActorSpawnArg.name = paramNetMsgInput.read255();
        localActorSpawnArg.weapons = paramNetMsgInput.read255();
        this._netUser = ((NetUser)paramNetMsgInput.readNetObj());
        localActorSpawnArg.netChannel = paramNetMsgInput.channel();
        localActorSpawnArg.netIdRemote = paramInt;
        NetAircraft.access$1600(paramNetMsgInput, localActorSpawnArg);
        NetAircraft localNetAircraft = (NetAircraft)_actorSpawn(localActorSpawnArg);
        if (localNetAircraft != null)
          NetAircraft.access$1700(paramNetMsgInput, localActorSpawnArg, localNetAircraft); 
      } catch (Exception localException) {
        NetAircraft.printDebug(localException);
      }
    }

    public Actor actorSpawn(ActorSpawnArg paramActorSpawnArg) {
      if (!Mission.isNet()) return null;
      if (Main.cur().netServerParams == null) return null;
      if ((paramActorSpawnArg.netChannel == null) && (paramActorSpawnArg.bPlayer))
        this._netUser = ((NetUser)NetEnv.host());
      Actor localActor = null;
      if (Main.cur().netServerParams.isDogfight())
        localActor = _actorSpawn(paramActorSpawnArg);
      else if (Main.cur().netServerParams.isCoop()) {
        localActor = actorSpawnCoop(paramActorSpawnArg);
      }
      this._netUser = null;

      if ((localActor != null) && (localActor == World.getPlayerAircraft()))
      {
        if ((NetMissionTrack.isRecording()) && (Main.cur().netServerParams.isDogfight())) {
          try {
            NetMsgSpawn localNetMsgSpawn = localActor.netReplicate(NetMissionTrack.netChannelOut());
            localActor.net.postTo(NetMissionTrack.netChannelOut(), localNetMsgSpawn);
          } catch (Exception localException) {
            NetAircraft.printDebug(localException);
          }
        }
      }

      return localActor;
    }

    public void netSpawn(int paramInt, NetMsgInput paramNetMsgInput) {
      if (Main.cur().netServerParams == null) return;
      if (((paramNetMsgInput.channel() instanceof NetChannelInStream)) && (NetMissionTrack.playingVersion() == 100)) {
        if (Main.cur().netServerParams.isCoop())
          netSpawnCoop(paramInt, paramNetMsgInput);
        else
          _netSpawn(paramInt, paramNetMsgInput);
      }
      else
        try {
          int i = paramNetMsgInput.readByte();
          if ((i & 0x1) == 1)
            netSpawnCoop(paramInt, paramNetMsgInput);
          else
            _netSpawn(paramInt, paramNetMsgInput);
        } catch (Exception localException) {
          NetAircraft.printDebug(localException); return;
        }
      this._netUser = null;
    }
  }

  public class Mirror extends NetAircraft.AircraftNet
  {
    NetMsgFiltered out = new NetMsgFiltered();
    private long tupdate = -1L;
    private long _t;
    private long tcur;
    private Point3f _p = new Point3f();
    private Vector3f _v = new Vector3f();
    private Orient _o = new Orient();
    private Vector3f _w = new Vector3f();
    private Vector3f TmpV = new Vector3f();
    private Vector3d TmpVd = new Vector3d();
    private float save_dt = 0.001F;
    private float saveCoeff = 1.0F;
    private boolean bGround = false;
    private boolean bUnderDeck = false;
    private long tint;
    private long tlag;
    private boolean bFirstUpdate = true;
    private Loc _lRel = new Loc();

    public void makeFirstUnderDeck() {
      if (NetAircraft.this.FM.brakeShoe) {
        NetAircraft.corn.set(NetAircraft.this.pos.getAbsPoint());
        NetAircraft.corn1.set(NetAircraft.this.pos.getAbsPoint());
        NetAircraft.corn1.z -= 20.0D;
        Actor localActor = Engine.collideEnv().getLine(NetAircraft.corn, NetAircraft.corn1, false, NetAircraft.clipFilter, NetAircraft.pship);
        if ((!(localActor instanceof BigshipGeneric)) && (((Mission.isCoop()) && (Time.current() < 60000L)) || (Mission.isDogfight())))
          localActor = NetAircraft.this.FM.brakeShoeLastCarrier;
        if ((localActor instanceof BigshipGeneric)) {
          this.bUnderDeck = true;
          this._lRel.set(NetAircraft.this.pos.getAbs());
          this._lRel.sub(localActor.pos.getAbs());
        }
      }
    }

    public void netFirstUpdate(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
    {
      NetAircraft.this.FM.Vwld.set(paramFloat7, paramFloat8, paramFloat9);
      NetAircraft.this.FM.getAccel().set(0.0D, 0.0D, 0.0D);
      this._t = (this.tcur = this.tupdate = Time.current());
      this._p.set(paramFloat1, paramFloat2, paramFloat3);
      this._v.set(NetAircraft.this.FM.Vwld);

      this._o.set(paramFloat4, paramFloat5, paramFloat6);
      this._w.set(0.0F, 0.0F, 0.0F);

      NetAircraft.this.FM.Loc.set(paramFloat1, paramFloat2, paramFloat3);
      NetAircraft.this.FM.Or.set(paramFloat4, paramFloat5, paramFloat6);
      this.tint = this.tcur;
      this.tlag = 0L;
    }

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      if (paramNetMsgInput.isGuaranted()) {
        return NetAircraft.this.netGetGMsg(paramNetMsgInput, false);
      }
      if ((Time.isPaused()) && (!NetMissionTrack.isPlaying()))
        return true;
      if (paramNetMsgInput.channel() != masterChannel())
      {
        postRealTo(Message.currentTime(true), masterChannel(), new NetMsgFiltered(paramNetMsgInput, 0));
        return true;
      }
      if (isMirrored()) {
        this.out.unLockAndSet(paramNetMsgInput, 0);
        postReal(Message.currentTime(true), this.out);
      }
      int i = paramNetMsgInput.readByte();
      int j = paramNetMsgInput.readUnsignedByte();
      this.bGround = ((i & 0x20) != 0);
      this.bUnderDeck = ((i & 0x40) != 0);
      if (NetAircraft.this.isFMTrackMirror()) {
        paramNetMsgInput.readUnsignedByte();
        paramNetMsgInput.readUnsignedByte();
      } else {
        netControls(paramNetMsgInput.readUnsignedByte());
        netWeaponControl(paramNetMsgInput.readUnsignedByte());
        if (this.bFirstUpdate) {
          Aircraft localAircraft = (Aircraft)superObj();
          localAircraft.FM.CT.forceGear(localAircraft.FM.CT.GearControl);
          Aircraft.forceGear(localAircraft.getClass(), localAircraft.hierMesh(), localAircraft.FM.CT.getGear());
        }
      }

      float f1 = paramNetMsgInput.readFloat();
      float f2 = paramNetMsgInput.readFloat();
      float f3 = paramNetMsgInput.readFloat();

      int k = paramNetMsgInput.readShort();
      int m = paramNetMsgInput.readShort();
      int n = paramNetMsgInput.readShort();
      float f4 = -(k * 180.0F / 32000.0F);
      float f5 = m * 90.0F / 32000.0F;
      float f6 = n * 180.0F / 32000.0F;

      int i1 = paramNetMsgInput.readShort();
      int i2 = paramNetMsgInput.readShort();
      int i3 = paramNetMsgInput.readShort();
      float f7 = i1 * 50.0F / 32000.0F;
      float f8 = i2 * 50.0F / 32000.0F;
      float f9 = i3 * 50.0F / 32000.0F;
      if (this.bUnderDeck) {
        f7 = f8 = f9 = 0.0F;
      }

      int i4 = paramNetMsgInput.readShort();
      int i5 = paramNetMsgInput.readShort();
      int i6 = paramNetMsgInput.readShort();
      float f10 = i4 * 400.0F / 32000.0F;
      float f11 = i5 * 400.0F / 32000.0F;
      float f12 = i6 * 400.0F / 32000.0F;
      if ((this.bGround) && (!this.bUnderDeck)) {
        f12 = 0.0F;
      }
      int i7 = paramNetMsgInput.readShort();
      int i8 = paramNetMsgInput.readShort();
      int i9 = paramNetMsgInput.readShort();
      float f13 = i7 * 2000.0F / 32000.0F;
      float f14 = i8 * 2000.0F / 32000.0F;
      float f15 = i9 * 2000.0F / 32000.0F;
      if ((this.bGround) || (this.bUnderDeck)) {
        f13 = 0.0F;
        f14 = 0.0F;
        f15 = 0.0F;
      }
      if (this.bUnderDeck) {
        this._lRel.set(i7 * 200.0D / 32000.0D, i8 * 200.0D / 32000.0D, i9 * 200.0D / 32000.0D, -(i1 * 180.0F / 32000.0F), i2 * 90.0F / 32000.0F, i3 * 180.0F / 32000.0F);
      }

      long l1 = Message.currentTime(false) + j;
      this._t = l1;

      if (NetEnv.testLag) {
        long l2 = Time.tickNext() - l1;
        if (l2 < 0L)
          l2 = 0L;
        if ((this.bFirstUpdate) || (this.tlag >= l2)) {
          this.bFirstUpdate = false;
          this.tlag = l2;
        }
        else if (l1 > this.tupdate) {
          double d = (l2 - (this.tcur - this.tint)) / (l1 - this.tupdate);

          if (d > 0.015D)
            d = 0.015D;
          long l3 = ()((l1 - this.tupdate) * d);
          if (l3 > Time.tickConstLen() / 2)
            l3 = Time.tickConstLen() / 2;
          this.tlag = (this.tcur - this.tint + l3);
          if (this.tlag >= l2)
            this.tlag = l2;
        }
      } else {
        this.bFirstUpdate = false;
      }
      this.tupdate = this._t;

      NetAircraft.this.FM.Vwld.set(f10, f11, f12);
      NetAircraft.this.FM.getAccel().set(f13, f14, f15);

      this._p.set(f1, f2, f3);
      this._v.set(NetAircraft.this.FM.Vwld);

      this._o.set(f4, f5, f6);
      this._o.transformInv(NetAircraft.this.FM.Vwld, NetAircraft.this.FM.getVflow());
      this._w.set(f7, f8, f9);
      NetAircraft.this.FM.getW().set(f7, f8, f9);

      int i10 = i & 0xF;
      float f17;
      int i11;
      float f18;
      if (i10 == 1) {
        float f16 = paramNetMsgInput.readUnsignedByte() / 255.0F * 640.0F;
        f17 = paramNetMsgInput.readUnsignedByte() / 255.0F * 1.6F;
        i10 = NetAircraft.this.FM.EI.getNum();
        for (int i13 = 0; i13 < i10; i13++)
          if (!NetAircraft.this.isFMTrackMirror()) {
            NetAircraft.this.FM.EI.engines[i13].setw(f16);
            NetAircraft.this.FM.EI.engines[i13].setPropPhi(f17);
          }
      }
      else {
        for (i11 = 0; i11 < i10; i11++) {
          f17 = paramNetMsgInput.readUnsignedByte() / 255.0F * 640.0F;
          f18 = paramNetMsgInput.readUnsignedByte() / 255.0F * 1.6F;
          if (!NetAircraft.this.isFMTrackMirror()) {
            NetAircraft.this.FM.EI.engines[i11].setw(f17);
            NetAircraft.this.FM.EI.engines[i11].setPropPhi(f18);
          }
        }
      }

      if (((i & 0x10) != 0) && (NetAircraft.this.netCockpitTuretNum >= 0)) {
        i11 = paramNetMsgInput.readUnsignedShort();
        int i12 = paramNetMsgInput.readUnsignedShort();
        f18 = unpackSY(i11);
        float f19 = unpackSP(i12 & 0x7FFF);
        NetAircraft.this.FM.CT.WeaponControl[NetAircraft.this.netCockpitWeaponControlNum] = ((i12 & 0x8000) != 0 ? 1 : false);
        if (superObj() == World.getPlayerAircraft()) {
          Actor._tmpOrient.set(f18, f19, 0.0F);
          ((CockpitGunner)Main3D.cur3D().cockpits[NetAircraft.this.netCockpitIndxPilot]).moveGun(Actor._tmpOrient);
        } else {
          Turret localTurret = NetAircraft.this.FM.turret[NetAircraft.this.netCockpitTuretNum];
          localTurret.tu[0] = f18;
          localTurret.tu[1] = f19;
        }
      }
      return true;
    }

    float unpackSY(int paramInt)
    {
      return (float)(paramInt * 360.0D / 65000.0D - 180.0D);
    }
    float unpackSP(int paramInt) {
      return (float)(paramInt * 360.0D / 32000.0D - 180.0D);
    }

    public void fmUpdate(float paramFloat) {
      if (this.tupdate < 0L) {
        netFirstUpdate((float)NetAircraft.this.FM.Loc.x, (float)NetAircraft.this.FM.Loc.y, (float)NetAircraft.this.FM.Loc.z, NetAircraft.this.FM.Or.getAzimut(), NetAircraft.this.FM.Or.getTangage(), NetAircraft.this.FM.Or.getKren(), (float)NetAircraft.this.FM.Vwld.x, (float)NetAircraft.this.FM.Vwld.y, (float)NetAircraft.this.FM.Vwld.z);
      }

      paramFloat = (float)(Time.tickNext() - this.tcur) * 0.001F;
      if (paramFloat < 0.001F) return;
      this.tcur = Time.tickNext();

      NetAircraft.this.FM.CT.update(paramFloat, 50.0F, NetAircraft.this.FM.EI, false, NetAircraft.this.isFMTrackMirror());

      NetAircraft.this.FM.Gears.ground(NetAircraft.this.FM, false, this.bGround);
      NetAircraft.this.FM.Gears.bFlatTopGearCheck = false;

      for (int i = 0; i < 3; i++) {
        NetAircraft.this.FM.Gears.gWheelAngles[i] = ((NetAircraft.this.FM.Gears.gWheelAngles[i] + (float)Math.toDegrees(Math.atan(NetAircraft.this.FM.Gears.gVelocity[i] * paramFloat / 0.375D))) % 360.0F);

        NetAircraft.this.FM.Gears.gVelocity[i] *= 0.949999988079071D;
      }

      NetAircraft.this.hierMesh().chunkSetAngles("GearL1_D0", 0.0F, -NetAircraft.this.FM.Gears.gWheelAngles[0], 0.0F);
      NetAircraft.this.hierMesh().chunkSetAngles("GearR1_D0", 0.0F, -NetAircraft.this.FM.Gears.gWheelAngles[1], 0.0F);
      NetAircraft.this.hierMesh().chunkSetAngles("GearC1_D0", 0.0F, -NetAircraft.this.FM.Gears.gWheelAngles[2], 0.0F);

      float f1 = NetAircraft.this.FM.Gears.getSteeringAngle();

      NetAircraft.this.moveSteering(f1);
      if (NetAircraft.this.FM.Gears.nearGround()) {
        NetAircraft.this.moveWheelSink();
      }

      NetAircraft.this.FM.EI.netupdate(paramFloat, NetAircraft.this.isFMTrackMirror());
      NetAircraft.this.FM.FMupdate(paramFloat);
      this.tint = (this.tcur - this.tlag);

      while (this.tint > this._t) {
        long l = this.tint - this._t;
        if (l > Time.tickConstLen())
          l = Time.tickConstLen();
        f4 = (float)l * 0.001F;
        this._p.x += this._v.x * f4;
        this._p.y += this._v.y * f4;
        this._p.z += this._v.z * f4;
        Vector3f tmp627_624 = this._v; tmp627_624.x = (float)(tmp627_624.x + NetAircraft.this.FM.getAccel().x * f4);
        Vector3f tmp658_655 = this._v; tmp658_655.y = (float)(tmp658_655.y + NetAircraft.this.FM.getAccel().y * f4);
        Vector3f tmp689_686 = this._v; tmp689_686.z = (float)(tmp689_686.z + NetAircraft.this.FM.getAccel().z * f4);
        this.TmpV.scale(f4, this._w);
        this._o.increment((float)(-Math.toDegrees(this.TmpV.z)), (float)(-Math.toDegrees(this.TmpV.y)), (float)Math.toDegrees(this.TmpV.x));

        this._t += l;
      }
      World.land(); float f2 = Landscape.HQ(this._p.x, this._p.y);
      if (World.land().isWater(this._p.x, this._p.y)) {
        if (this._p.z < f2 - 20.0F) this._p.z = (f2 - 20.0F);
      }
      else if (this._p.z < f2 + 1.0F) this._p.z = (f2 + 1.0F);

      this.TmpVd.set(this._p);
      this.save_dt = (0.98F * this.save_dt + 0.02F * ((float)(this.tint - this.tupdate) * 0.001F));

      float f3 = 0.03F;
      if (this._v.length() > 0.0F) {
        f3 = 1.08F - this.save_dt * 2.0F;
        if (f3 > 1.0F) f3 = 1.0F;
        if (f3 < 0.03F) f3 = 0.03F;
      }
      this.saveCoeff = (0.98F * this.saveCoeff + 0.02F * f3);

      NetAircraft.this.FM.Loc.interpolate(this.TmpVd, this.saveCoeff);

      float f4 = this.saveCoeff * 2.0F;
      if (NetMissionTrack.isPlaying())
        f4 = this.saveCoeff / 4.0F;
      if (f4 > 1.0F) f4 = 1.0F;
      NetAircraft.this.FM.Or.interpolate(this._o, f4);

      if (this.bUnderDeck) {
        NetAircraft.corn.set(NetAircraft.this.FM.Loc);
        NetAircraft.corn1.set(NetAircraft.this.FM.Loc);
        NetAircraft.corn1.z -= 20.0D;
        Actor localActor = Engine.collideEnv().getLine(NetAircraft.corn, NetAircraft.corn1, false, NetAircraft.clipFilter, NetAircraft.pship);
        if ((!(localActor instanceof BigshipGeneric)) && (Mission.isCoop()) && (Time.current() < 60000L))
          localActor = NetAircraft.this.FM.brakeShoeLastCarrier;
        if ((localActor instanceof BigshipGeneric)) {
          NetAircraft.lCorn.set(this._lRel);
          NetAircraft.lCorn.add(localActor.pos.getAbs());
          NetAircraft.this.FM.Loc.set(NetAircraft.lCorn.getPoint());
          NetAircraft.this.FM.Or.set(NetAircraft.lCorn.getOrient());
          this.saveCoeff = 1.0F;
          this._p.set(NetAircraft.this.FM.Loc);
          this._o.set(NetAircraft.this.FM.Or);
          localActor.getSpeed(NetAircraft.this.FM.Vwld);
          this._v.x = (float)NetAircraft.this.FM.Vwld.x;
          this._v.y = (float)NetAircraft.this.FM.Vwld.y;
          this._v.z = (float)NetAircraft.this.FM.Vwld.z;
        }

      }

      if (NetAircraft.this.isFMTrackMirror()) {
        NetAircraft.this.fmTrack.FMupdate(NetAircraft.this.FM);
      }

      if (NetAircraft.this.FM.isTick(44, 0)) {
        NetAircraft.this.FM.AS.update(paramFloat * 44.0F);
        ((Aircraft)superObj()).rareAction(paramFloat * 44.0F, false);
        if (NetAircraft.this.FM.Loc.z - Engine.land().HQ_Air(NetAircraft.this.FM.Loc.x, NetAircraft.this.FM.Loc.y) > 40.0D) {
          NetAircraft.this.FM.setWasAirborne(true);
          NetAircraft.this.FM.setStationedOnGround(false);
        } else if (NetAircraft.this.FM.Vwld.length() < 1.0D) {
          NetAircraft.this.FM.setStationedOnGround(true);
        }
      }
    }

    public void netControls(int paramInt)
    {
      NetAircraft.this.FM.CT.GearControl = ((paramInt & 0x1) != 0 ? 1.0F : 0.0F);
      NetAircraft.this.FM.CT.FlapsControl = ((paramInt & 0x2) != 0 ? 1.0F : 0.0F);
      NetAircraft.this.FM.CT.BrakeControl = ((paramInt & 0x4) != 0 ? 1.0F : 0.0F);
      NetAircraft.this.FM.CT.setRadiatorControl((paramInt & 0x8) != 0 ? 1.0F : 0.0F);
      NetAircraft.this.FM.CT.BayDoorControl = ((paramInt & 0x10) != 0 ? 1.0F : 0.0F);
      NetAircraft.this.FM.CT.AirBrakeControl = ((paramInt & 0x20) != 0 ? 1.0F : 0.0F);
    }
    public void netWeaponControl(int paramInt) {
      int k = NetAircraft.this.FM.CT.WeaponControl.length;
      int i = 0; for (int j = 1; (i < k) && (j < 256); j <<= 1) {
        NetAircraft.this.FM.CT.WeaponControl[i] = ((paramInt & j) != 0 ? 1 : false);

        i++;
      }
    }

    public Mirror(Actor paramNetChannel, NetChannel paramInt, int arg4)
    {
      super(paramNetChannel, paramInt, i);
      try {
        this.out.setIncludeTime(true);
        this.out.setFilterArg(paramNetChannel);
      }
      catch (Exception localException)
      {
      }
    }
  }

  static class ClipFilter
    implements ActorFilter
  {
    public boolean isUse(Actor paramActor, double paramDouble)
    {
      return paramActor instanceof BigshipGeneric;
    }
  }

  class Master extends NetAircraft.AircraftNet
    implements NetUpdate
  {
    NetMsgFiltered out = new NetMsgFiltered();
    public byte[] weaponsBitStates;
    public boolean weaponsIsEmpty = false;
    public boolean weaponsCheck = false;
    public long weaponsSyncTime = 0L;

    public int curWayPoint = 0;
    private Vector3f vec3f = new Vector3f();
    private Point3d p = new Point3d();
    private Orient o = new Orient();
    private int countUpdates = 0;

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      if (paramNetMsgInput.isGuaranted()) {
        return NetAircraft.this.netGetGMsg(paramNetMsgInput, true);
      }

      if (!Config.isUSE_RENDER()) return true;
      int i = paramNetMsgInput.readByte();
      int j = paramNetMsgInput.readByte();
      int k = (i & 0x1) << 1 | j & 0x1;
      i &= -2;
      j &= -2;
      NetAircraft.this.msgSndShot(k * 0.05F + 0.01F, i * 0.25D, j * 0.25D, 0.0D);

      return true;
    }

    public void netUpdate() {
      if (Time.tickCounter() <= 2) return;
      if ((this.netUser == null) && (NetAircraft.this.FM.brakeShoe)) {
        i = NetAircraft.this.FM.actor.hashCode() & 0xF;
        if ((this.countUpdates++ & 0xF) != i)
          return;
      } else {
        this.countUpdates = 0;
      }

      if (this.weaponsIsEmpty) NetAircraft.this.FM.CT.WCT = 0;
      int i = (NetAircraft.this.FM.CT.WCT & 0xF) != 0 ? 1 : 0;
      try {
        this.out.unLockAndClear();

        int j = 0;

        int k = 0;
        int n = 0;
        int i1 = 0;
        for (int i2 = 0; i2 < NetAircraft.this.FM.EI.getNum(); i2++) {
          i3 = (int)(NetAircraft.this.FM.EI.engines[i2].getw() / 640.0F * 255.0F);
          if (i2 == 0) i1 = i3;
          else if (i1 != i3) n = 1;
          if (i3 != NetAircraft.this.FM.EI.engines[i2].wNetPrev) {
            k = 1;
            NetAircraft.this.FM.EI.engines[i2].wNetPrev = i3;
          }
        }
        if (k != 0) {
          if (n != 0) { j = 1; } else {
            j = NetAircraft.this.FM.EI.getNum(); if (j > 15) j = 15;
          }
        }
        if ((NetAircraft.this.netCockpitValid) && (NetAircraft.this.netCockpitTuretNum >= 0))
          j |= 16;
        if (NetAircraft.this.FM.Gears.onGround())
          j |= 32;
        if ((NetAircraft.this.FM.Gears.isUnderDeck()) && (NetAircraft.this.FM.Vrel.lengthSquared() < 2.0D)) {
          NetAircraft.corn.set(NetAircraft.this.FM.Loc);
          NetAircraft.corn1.set(NetAircraft.this.FM.Loc);
          NetAircraft.corn1.z -= 20.0D;
          Actor localActor = Engine.collideEnv().getLine(NetAircraft.corn, NetAircraft.corn1, false, NetAircraft.clipFilter, NetAircraft.pship);
          if ((!(localActor instanceof BigshipGeneric)) && (Mission.isCoop()) && (Time.current() < 60000L))
            localActor = NetAircraft.this.FM.brakeShoeLastCarrier;
          if ((localActor instanceof BigshipGeneric)) {
            NetAircraft.lCorn.set(NetAircraft.this.pos.getAbs());
            NetAircraft.lCorn.sub(localActor.pos.getAbs());
            if ((Math.abs(NetAircraft.lCorn.getX()) < 200.0D) && (Math.abs(NetAircraft.lCorn.getY()) < 200.0D) && (Math.abs(NetAircraft.lCorn.getZ()) < 200.0D))
            {
              j |= 64;
            }
          }
        }
        this.out.writeByte(j);

        int m = (int)(Time.tickNext() - Time.current());
        if (m > 255) m = 255;
        this.out.writeByte(m);
        this.out.writeByte(NetAircraft.this.FM.CT.CTL);
        this.out.writeByte(NetAircraft.this.FM.CT.WCT);
        Controls tmp653_650 = NetAircraft.this.FM.CT; tmp653_650.WCT = (byte)(tmp653_650.WCT & 0x3);

        NetAircraft.this.pos.getAbs(this.p, this.o);
        this.out.writeFloat((float)this.p.x); this.out.writeFloat((float)this.p.y); this.out.writeFloat((float)this.p.z);

        this.o.wrap();
        n = (int)(this.o.getYaw() * 32000.0F / 180.0F);
        i1 = (int)(this.o.tangage() * 32000.0F / 90.0F);
        i2 = (int)(this.o.kren() * 32000.0F / 180.0F);
        this.out.writeShort(n); this.out.writeShort(i1); this.out.writeShort(i2);

        if ((j & 0x40) == 0) {
          this.vec3f.set(NetAircraft.this.FM.getW());
          i3 = (int)(this.vec3f.x * 32000.0F / 50.0F);
          i4 = (int)(this.vec3f.y * 32000.0F / 50.0F);
          i5 = (int)(this.vec3f.z * 32000.0F / 50.0F);
          this.out.writeShort(i3); this.out.writeShort(i4); this.out.writeShort(i5);
        } else {
          NetAircraft.lCorn.get(this.o);
          this.o.wrap();
          n = (int)(this.o.getYaw() * 32000.0F / 180.0F);
          i1 = (int)(this.o.tangage() * 32000.0F / 90.0F);
          i2 = (int)(this.o.kren() * 32000.0F / 180.0F);
          this.out.writeShort(n); this.out.writeShort(i1); this.out.writeShort(i2);
        }

        this.vec3f.set(NetAircraft.this.FM.Vwld);
        int i3 = (int)(this.vec3f.x * 32000.0F / 400.0F);
        int i4 = (int)(this.vec3f.y * 32000.0F / 400.0F);
        int i5 = (int)(this.vec3f.z * 32000.0F / 400.0F);
        this.out.writeShort(i3); this.out.writeShort(i4); this.out.writeShort(i5);
        int i7;
        int i8;
        if ((j & 0x40) == 0) {
          this.vec3f.set(NetAircraft.this.FM.getAccel());
          i6 = (int)(this.vec3f.x * 32000.0F / 2000.0F);
          i7 = (int)(this.vec3f.y * 32000.0F / 2000.0F);
          i8 = (int)(this.vec3f.z * 32000.0F / 2000.0F);
          this.out.writeShort(i6); this.out.writeShort(i7); this.out.writeShort(i8);
        } else {
          i6 = (int)(NetAircraft.lCorn.getX() * 32000.0D / 200.0D);
          i7 = (int)(NetAircraft.lCorn.getY() * 32000.0D / 200.0D);
          i8 = (int)(NetAircraft.lCorn.getZ() * 32000.0D / 200.0D);
          this.out.writeShort(i6); this.out.writeShort(i7); this.out.writeShort(i8);
        }

        for (int i6 = 0; i6 < (j & 0xF); i6++) {
          this.out.writeByte((byte)(int)(NetAircraft.this.FM.EI.engines[i6].getw() / 640.0F * 255.0F));
          this.out.writeByte((byte)(int)(NetAircraft.this.FM.EI.engines[i6].getPropPhi() / 1.6F * 255.0F));
        }

        if ((NetAircraft.this.netCockpitValid) && (NetAircraft.this.netCockpitTuretNum >= 0)) {
          Turret localTurret = NetAircraft.this.FM.turret[NetAircraft.this.netCockpitTuretNum];
          i7 = NetAircraft.this.FM.CT.WeaponControl[NetAircraft.this.netCockpitWeaponControlNum];
          this.out.writeShort(packSY(localTurret.tu[0]));
          this.out.writeShort(packSP(localTurret.tu[1]) | (i7 != 0 ? 32768 : 0));
        }

        post(Time.current(), this.out); } catch (Exception localException) {
        NetObj.printDebug(localException);
      }
      if ((this.weaponsCheck) && (Time.current() > this.weaponsSyncTime)) {
        this.weaponsSyncTime = (Time.current() + 5000L);
        this.weaponsCheck = false;
        if (NetAircraft.this.isWeaponsChanged(this.weaponsBitStates)) {
          this.weaponsBitStates = NetAircraft.this.getWeaponsBitStates(this.weaponsBitStates);
          NetAircraft.this.netPutWeaponsBitStates(this.weaponsBitStates);
          this.weaponsIsEmpty = NetAircraft.this.isWeaponsAllEmpty();
        }
      }
      if (i != 0)
        this.weaponsCheck = true;
    }

    int packSY(float paramFloat) {
      return 0xFFFF & (int)((paramFloat % 360.0D + 180.0D) * 65000.0D / 360.0D);
    }
    int packSP(float paramFloat) {
      return 0x7FFF & (int)((paramFloat % 360.0D + 180.0D) * 32000.0D / 360.0D);
    }

    public Master(Actor arg2) {
      super(localActor);
      try {
        this.out.setIncludeTime(true);
        this.out.setFilterArg(localActor);
      }
      catch (Exception localException)
      {
      }
    }
  }

  public class AircraftNet extends ActorNet
  {
    public NetUser netUser;
    public String netName;
    public IntHashtable filterTable;

    private void createFilterTable()
    {
      if (Main.cur().netServerParams == null) return;
      if (Main.cur().netServerParams.isMirror()) return;
      this.filterTable = new IntHashtable();
    }

    public AircraftNet(Actor arg2) {
      super();
      createFilterTable();
    }
    public AircraftNet(Actor paramNetChannel, NetChannel paramInt, int arg4) {
      super(paramInt, i);
      createFilterTable();
    }
  }
}