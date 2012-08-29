package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
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
  public static String loadingCountry;
  public static boolean loadingCoopPlane;
  protected String thisWeaponsName = null;
  protected boolean bPaintShemeNumberOn = true;
  private boolean bCoopPlane;
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
  protected static String[] partNames() {
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
    return ((AircraftNet)this.jdField_net_of_type_ComMaddoxIl2EngineActorNet).netUser;
  }
  public String netName() {
    if (!isNet()) return null;
    return ((AircraftNet)this.jdField_net_of_type_ComMaddoxIl2EngineActorNet).netName;
  }
  public boolean isNetPlayer() {
    if (!isNet()) return false;
    return ((AircraftNet)this.jdField_net_of_type_ComMaddoxIl2EngineActorNet).netUser != null;
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
    if ((paramBoolean) && (this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMaster())) return false;
    if ((!paramBoolean) && (!this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMirrored())) return false;
    return (!paramBoolean) || (!(this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.masterChannel() instanceof NetChannelInStream));
  }

  public NetMsgGuaranted netNewAStateMsg(boolean paramBoolean)
    throws IOException
  {
    if (!isNet()) return null;
    if ((paramBoolean) && (this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMaster())) return null;
    if ((!paramBoolean) && (!this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMirrored())) return null;
    if ((paramBoolean) && ((this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.masterChannel() instanceof NetChannelInStream))) return null;
    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    if (paramBoolean) localNetMsgGuaranted.writeByte(128); else
      localNetMsgGuaranted.writeByte(0);
    return localNetMsgGuaranted;
  }

  public void netSendAStateMsg(boolean paramBoolean, NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
    if (paramBoolean) this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.postTo(this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.masterChannel(), paramNetMsgGuaranted); else
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.post(paramNetMsgGuaranted);
  }

  public void netUpdateWayPoint()
  {
    if ((this.jdField_net_of_type_ComMaddoxIl2EngineActorNet == null) || (Main.cur().netServerParams == null) || (!Main.cur().netServerParams.isCoop()) || (Main.cur().netServerParams.isMaster()) || (!this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMaster()) || (!this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMirrored()))
    {
      return;
    }Master localMaster = (Master)this.jdField_net_of_type_ComMaddoxIl2EngineActorNet;
    if (localMaster.curWayPoint != this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur()) {
      localMaster.curWayPoint = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur();
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
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.setCur(i);
      if (i == this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.size() - 1)
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
    } else {
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(1);
        localNetMsgGuaranted.writeShort(i);
        this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.postTo(Main.cur().netServerParams.masterChannel(), localNetMsgGuaranted); } catch (Exception localException) {
        printDebug(localException);
      }
    }
    return true;
  }

  private int getWeaponsAmount()
  {
    int i = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons.length;
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons.length == 0) return 0;
    int j = 0;
    for (int k = 0; k < i; k++) {
      BulletEmitter[] arrayOfBulletEmitter = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[k];
      if (arrayOfBulletEmitter != null)
        for (int m = 0; m < arrayOfBulletEmitter.length; m++) {
          if (arrayOfBulletEmitter[m] == null) continue; j++;
        }
    }
    return j;
  }
  private boolean isWeaponsAllEmpty() {
    int i = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons.length;
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons.length == 0) return true;
    for (int j = 0; j < i; j++) {
      BulletEmitter[] arrayOfBulletEmitter = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[j];
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
    int j = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons.length;
    for (int k = 0; k < j; k++) {
      BulletEmitter[] arrayOfBulletEmitter = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[k];
      if (arrayOfBulletEmitter != null)
        for (int m = 0; m < arrayOfBulletEmitter.length; m++)
          if (arrayOfBulletEmitter[m] != null) {
            if (arrayOfBulletEmitter[m].countBullets() != 0)
            {
              int tmp90_89 = (i / 8);
              byte[] tmp90_85 = paramArrayOfByte; tmp90_85[tmp90_89] = (byte)(tmp90_85[tmp90_89] | 1 << i % 8);
            }i++;
          }
    }
    return paramArrayOfByte;
  }
  private void setWeaponsBitStates(byte[] paramArrayOfByte) {
    int i = 0;
    int j = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons.length;
    for (int k = 0; k < j; k++) {
      BulletEmitter[] arrayOfBulletEmitter = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[k];
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
    int j = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons.length;
    for (int k = 0; k < j; k++) {
      BulletEmitter[] arrayOfBulletEmitter = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[k];
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
    if ((!isNet()) || (this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.countMirrors() == 0)) return; try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(2);
      localNetMsgGuaranted.write(paramArrayOfByte);
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.post(localNetMsgGuaranted); } catch (Exception localException) {
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
    Object localObject;
    for (int i = 0; i < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons.length; i++) {
      localObject = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i];
      if (localObject != null) {
        for (int j = 0; j < localObject.length; j++)
          if (localObject[j] != null)
            localObject[j].setPause(!paramBoolean);
      }
    }
    this.bGunPodsOn = paramBoolean;

    if ((!isNet()) || (this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.countMirrors() == 0)) return; try
    {
      localObject = new NetMsgGuaranted();
      if (paramBoolean) ((NetMsgGuaranted)localObject).writeByte(3); else
        ((NetMsgGuaranted)localObject).writeByte(4);
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.post((NetMsgGuaranted)localObject); } catch (Exception localException) {
      printDebug(localException);
    }
  }

  public void replicateDropFuelTanks()
  {
    if ((!isNet()) || (this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.countMirrors() == 0)) return; try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(5);
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.post(localNetMsgGuaranted); } catch (Exception localException) {
      printDebug(localException);
    }
  }

  protected void netPutHits(boolean paramBoolean, NetChannel paramNetChannel, int paramInt1, int paramInt2, int paramInt3, Actor paramActor)
  {
    if ((!paramBoolean) && (this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.countMirrors() == 0))
      return;
    if ((!Actor.isValid(paramActor)) || (!paramActor.isNet()))
      return;
    try {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      if (paramBoolean) localNetMsgGuaranted.writeByte(134); else
        localNetMsgGuaranted.writeByte(6);
      localNetMsgGuaranted.writeByte(paramInt1 & 0xF | paramInt2 << 4 & 0xF0);
      localNetMsgGuaranted.writeByte(paramInt3);
      localNetMsgGuaranted.writeNetObj(paramActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet);

      if (paramBoolean) this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.postTo(this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.masterChannel(), localNetMsgGuaranted); else
        this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.postExclude(paramNetChannel, localNetMsgGuaranted); 
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
    if (this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.countMirrors() > 1) {
      netPutHits(false, paramNetMsgInput.channel(), i, j, k, localActor);
    }
    netHits(i, j, k, localActor);
    return true;
  }

  public void hitProp(int paramInt1, int paramInt2, Actor paramActor)
  {
    if ((isNet()) && (this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMirrored()))
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(7);
        localNetMsgGuaranted.writeByte(paramInt1);
        localNetMsgGuaranted.writeByte(paramInt2);
        localNetMsgGuaranted.writeNetObj(paramActor != null ? paramActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet : null);
        this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.post(localNetMsgGuaranted); } catch (Exception localException) {
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
    hitProp(i, j, localNetObj != null ? (Actor)localNetObj.superObj() : null);
    return true;
  }

  protected void netPutCut(int paramInt1, int paramInt2, Actor paramActor)
  {
    if ((!isNet()) || (this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.countMirrors() == 0)) return; try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(8);
      localNetMsgGuaranted.writeByte(paramInt1);
      localNetMsgGuaranted.writeByte(paramInt2);
      localNetMsgGuaranted.writeNetObj(paramActor != null ? paramActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet : null);
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.post(localNetMsgGuaranted); } catch (Exception localException) {
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
    nextCUTLevel(partNames()[i] + "_D0", j, localNetObj != null ? (Actor)localNetObj.superObj() : null);
    return true;
  }

  public void netExplode()
  {
    if ((!isNet()) || (this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.countMirrors() == 0)) return; try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(9);
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.post(localNetMsgGuaranted); } catch (Exception localException) {
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
    if ((isAlive()) && (paramBoolean) && (isNet()) && (Actor.isValid(getDamager())) && (getDamager().isNet()) && (this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.countMirrors() > 0))
    {
      try
      {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(10);
        localNetMsgGuaranted.writeNetObj(getDamager().jdField_net_of_type_ComMaddoxIl2EngineActorNet);
        this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.post(localNetMsgGuaranted); } catch (Exception localException) {
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
      if (this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.countMirrors() > 0)
        try {
          NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
          localNetMsgGuaranted.writeByte(10);
          localNetMsgGuaranted.writeNetObj(localNetObj);
          this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.post(localNetMsgGuaranted); } catch (Exception localException) {
          printDebug(localException);
        }
    }
    return true;
  }

  public void netFirstUpdate(NetChannel paramNetChannel)
    throws IOException
  {
    if ((!Mission.isDogfight()) && 
      ((!Mission.isCoop()) || (!isNetPlayer())) && 
      ((paramNetChannel instanceof NetChannelOutStream))) {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(11);
      netReplicateFirstUpdate(paramNetChannel, localNetMsgGuaranted);
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.postTo(paramNetChannel, localNetMsgGuaranted);
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
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(localActorSpawnArg.point, localActorSpawnArg.orient);
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
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
    Class[] arrayOfClass = (Class[])localObject;
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
      localObject2 = (Class[])localObject1;
      if (paramInt >= localObject2.length) return;
      i = Property.intValue(localObject2[paramInt], "weaponControlNum", 10);
    }
    if (World.cur().diffCur.Limited_Ammo) {
      localObject2 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i];
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
      Class[] arrayOfClass = (Class[])localObject1;
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
        localObject2 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[j];
        ((Turret)localObject2).bIsAIControlled = paramBoolean;
      }
      Object localObject2 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i];
      if (localObject2 != null) {
        boolean bool = (!paramActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMaster()) || (World.cur().diffCur.Realistic_Gunnery);
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

    int i = this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.countMirrors();
    if (this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMirror()) i++;
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
        local2.writeNetObj(paramActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet);
        if (paramBoolean) paramInt |= 128;
        local2.writeByte(paramInt);
        if (paramArrayOfShort != null)
          for (int k = 0; k < paramArrayOfShort.length; k++)
            local2.writeShort(paramArrayOfShort[k]);
        this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.postExclude(paramNetChannel, local2); } catch (Exception localException) {
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
          EventLog.onWeaponsLoad(paramActor, this.thisWeaponsName, (int)(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel * 100.0F / this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.maxFuel));
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
      Class[] arrayOfClass = (Class[])localObject;
      if (i >= arrayOfClass.length) return;
      if (paramInt >= arrayOfClass.length) return;
      localClass1 = arrayOfClass[i];
      localClass2 = arrayOfClass[paramInt];
    }
    Turret localTurret1;
    if (!CockpitPilot.class.isAssignableFrom(localClass1)) {
      j = Property.intValue(localClass1, "aiTuretNum", 0);
      localTurret1 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[j];
      localTurret1.bIsNetMirror = false;
    }
    if (!CockpitPilot.class.isAssignableFrom(localClass2)) {
      j = Property.intValue(localClass2, "aiTuretNum", 0);
      localTurret1 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[j];
      localTurret1.bIsNetMirror = paramActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMirror();
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
      Turret localTurret2 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[k];
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

    int m = this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.countMirrors();
    if (this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMirror()) m++;
    if (paramNetChannel != null) m--;
    if (m > 0)
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(12);
        localNetMsgGuaranted.writeNetObj(paramActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet);
        localNetMsgGuaranted.writeByte(paramInt);
        this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.postExclude(paramNetChannel, localNetMsgGuaranted); } catch (Exception localException) {
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
      ((NetMsgGuaranted)localObject1).writeNetObj(paramActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet);
      ((NetMsgGuaranted)localObject1).writeByte(i);
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.postTo(paramNetChannel, (NetMsgGuaranted)localObject1);
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
        Class[] arrayOfClass = (Class[])localObject2;
        if (i >= arrayOfClass.length) return;
        localClass = arrayOfClass[i];
      }
      int k = Property.intValue(localClass, "weaponControlNum", 10);
      int m;
      if (World.cur().diffCur.Limited_Ammo) {
        localObject3 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[k];
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
      ((NetMsgGuaranted)localObject3).writeNetObj(paramActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet);
      ((NetMsgGuaranted)localObject3).writeByte(i);
      if (localObject1 != null)
        for (m = 0; m < localObject1.length; m++)
          ((NetMsgGuaranted)localObject3).writeShort(localObject1[m]);
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.postTo(paramNetChannel, (NetMsgGuaranted)localObject3);
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
      Class[] arrayOfClass = (Class[])localObject;
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
    if (this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMaster()) {
      if (this.netCockpitDrivers[paramInt] != null)
        return;
      netCockpitDriverSet(paramActor, paramInt);
      Main3D.cur3D().aircraftHotKeys.netSwitchToCockpit(paramInt);
    } else {
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(14);
        localNetMsgGuaranted.writeNetObj(paramActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet);
        localNetMsgGuaranted.writeByte(paramInt);
        this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.postTo(this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.masterChannel(), localNetMsgGuaranted); } catch (Exception localException) {
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
        localNetMsgGuaranted1.writeNetObj(localActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet);
        localNetMsgGuaranted1.writeByte(i);
        this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.postTo(this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.masterChannel(), localNetMsgGuaranted1); } catch (Exception localException1) {
        printDebug(localException1);
      }
    } else {
      netCockpitDriverSet(localActor, i);
      if (localActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMaster()) {
        Main3D.cur3D().aircraftHotKeys.netSwitchToCockpit(i);
      }
    }
    if (this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.countMirrors() > 0)
      try {
        NetMsgGuaranted localNetMsgGuaranted2 = new NetMsgGuaranted();
        localNetMsgGuaranted2.writeByte(14);
        localNetMsgGuaranted2.writeNetObj(localActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet);
        localNetMsgGuaranted2.writeByte(i);
        this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.post(localNetMsgGuaranted2); } catch (Exception localException2) {
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
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.netUpdate(paramBoolean, bool, paramNetMsgInput);
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
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.dropFuelTanks();
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
    }
    return false;
  }

  protected void sendMsgSndShot(Shot paramShot)
  {
    int i = paramShot.mass > 0.05F ? 1 : 0;
    Actor._tmpPoint.set(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
    Actor._tmpPoint.sub(paramShot.p);
    int j = (int)(Actor._tmpPoint.jdField_x_of_type_Double / 0.25D) & 0xFE;
    int k = (int)(Actor._tmpPoint.jdField_y_of_type_Double / 0.25D) & 0xFE;
    i &= 3;
    try {
      NetMsgFiltered localNetMsgFiltered = new NetMsgFiltered();
      localNetMsgFiltered.writeByte(j | i >> 1);
      localNetMsgFiltered.writeByte(k | i & 0x1);
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.postTo(Time.current(), this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.masterChannel(), localNetMsgFiltered);
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
    if ((this.jdField_net_of_type_ComMaddoxIl2EngineActorNet == null) || (this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMaster())) return;
    ((Mirror)this.jdField_net_of_type_ComMaddoxIl2EngineActorNet).makeFirstUnderDeck();
  }

  public boolean isMirrorUnderDeck() {
    if ((this.jdField_net_of_type_ComMaddoxIl2EngineActorNet == null) || (this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMaster())) return false;
    return ((Mirror)this.jdField_net_of_type_ComMaddoxIl2EngineActorNet).bUnderDeck;
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
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet = new Master(this);
    }
    else
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet = new Mirror(this, paramNetChannel, paramInt);
  }

  public void restoreLinksInCoopWing()
  {
    if ((Main.cur().netServerParams == null) || (!Main.cur().netServerParams.isCoop()))
      return;
    Wing localWing = getWing();
    Aircraft[] arrayOfAircraft = localWing.airc;

    int i = 0;
    for (; i < arrayOfAircraft.length; i++) {
      if (Actor.isValid(arrayOfAircraft[i]))
        break;
    }
    if (i == arrayOfAircraft.length)
      return;
    arrayOfAircraft[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel = null;
    for (int j = i + 1; j < arrayOfAircraft.length; j++)
      if (Actor.isValid(arrayOfAircraft[j])) {
        arrayOfAircraft[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Wingman = arrayOfAircraft[j].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
        arrayOfAircraft[j].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel = arrayOfAircraft[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
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
    Mirror localMirror = (Mirror)paramNetAircraft.jdField_net_of_type_ComMaddoxIl2EngineActorNet;
    localMirror.netFirstUpdate((float)paramActorSpawnArg.point.jdField_x_of_type_Double, (float)paramActorSpawnArg.point.jdField_y_of_type_Double, (float)paramActorSpawnArg.point.jdField_z_of_type_Double, paramActorSpawnArg.orient.azimut(), paramActorSpawnArg.orient.tangage(), paramActorSpawnArg.orient.kren(), (float)paramActorSpawnArg.speed.jdField_x_of_type_Double, (float)paramActorSpawnArg.speed.jdField_y_of_type_Double, (float)paramActorSpawnArg.speed.jdField_z_of_type_Double);

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
    if (l1 != paramNetAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Operate_of_type_Long)
    {
      m = 0; for (long l2 = 1L; m < 44; l2 <<= 1) {
        if (((l1 & l2) == 0L) && ((paramNetAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Operate_of_type_Long & l2) != 0L))
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
    paramNetAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.netFirstUpdate(paramNetMsgInput);
  }

  private void netReplicateCommon(NetChannel paramNetChannel, NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
    paramNetMsgGuaranted.writeFloat(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel / this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.maxFuel);
    paramNetMsgGuaranted.writeBoolean(this.bPaintShemeNumberOn);
    paramNetMsgGuaranted.write255(((AircraftNet)this.jdField_net_of_type_ComMaddoxIl2EngineActorNet).netName);
    paramNetMsgGuaranted.write255(this.thisWeaponsName);
    paramNetMsgGuaranted.writeNetObj(((AircraftNet)this.jdField_net_of_type_ComMaddoxIl2EngineActorNet).netUser);
  }

  private void netReplicateFirstUpdate(NetChannel paramNetChannel, NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
    Point3d localPoint3d = this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
    paramNetMsgGuaranted.writeFloat((float)localPoint3d.jdField_x_of_type_Double); paramNetMsgGuaranted.writeFloat((float)localPoint3d.jdField_y_of_type_Double); paramNetMsgGuaranted.writeFloat((float)localPoint3d.jdField_z_of_type_Double);
    Orient localOrient = this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsOrient();
    paramNetMsgGuaranted.writeFloat(localOrient.azimut()); paramNetMsgGuaranted.writeFloat(localOrient.tangage()); paramNetMsgGuaranted.writeFloat(localOrient.kren());
    Vector3d localVector3d = new Vector3d();
    getSpeed(localVector3d);
    paramNetMsgGuaranted.writeFloat((float)localVector3d.jdField_x_of_type_Double); paramNetMsgGuaranted.writeFloat((float)localVector3d.jdField_y_of_type_Double); paramNetMsgGuaranted.writeFloat((float)localVector3d.jdField_z_of_type_Double);

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

    paramNetMsgGuaranted.writeLong(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Operate_of_type_Long);

    int k = curDMGProp(0) | curDMGProp(1) << 1 | curDMGProp(2) << 2 | curDMGProp(3) << 3;

    if (!isGunPodsOn()) k |= 16;
    paramNetMsgGuaranted.writeByte(k);

    byte[] arrayOfByte = getWeaponsBitStates(null);
    if (arrayOfByte != null)
      paramNetMsgGuaranted.write(arrayOfByte);
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.netReplicate(paramNetMsgGuaranted);
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
    if (paramNetChannel.isMirrored(this.jdField_net_of_type_ComMaddoxIl2EngineActorNet)) return null;
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
    for (int j = 0; j < i; j++) {
      DamagerItem localDamagerItem1 = (DamagerItem)this.damagers.get(j);
      if (localDamagerItem1.damager == paramActor) {
        localDamagerItem1.damage += paramInt;
        localDamagerItem1.lastTime = Time.current();
        return;
      }
    }
    this.damagers.add(new DamagerItem(paramActor, paramInt));

    if (World.cur().isDebugFM()) {
      Aircraft.debugprintln(this, "Printing Registered Damagers: *****");
      for (int k = 0; k < i; k++) {
        DamagerItem localDamagerItem2 = (DamagerItem)this.damagers.get(k);
        if (Actor.isValid(localDamagerItem2.damager))
          Aircraft.debugprintln(localDamagerItem2.damager, "inflicted " + localDamagerItem2.damage + " puntos..");
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
    return this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.crew;
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

      localNetAircraft2.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel = (paramActorSpawnArg.fuel * localNetAircraft2.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.maxFuel);
      localNetAircraft2.bPaintShemeNumberOn = paramActorSpawnArg.bNumberOn;
      localNetAircraft2.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsEnableToBailout = localNetAircraft1.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsEnableToBailout;
      localNetAircraft2.createNetObject(paramActorSpawnArg.netChannel, paramActorSpawnArg.netIdRemote);
      ((NetAircraft.AircraftNet)localNetAircraft2.jdField_net_of_type_ComMaddoxIl2EngineActorNet).netUser = this._netUser;
      ((NetAircraft.AircraftNet)localNetAircraft2.jdField_net_of_type_ComMaddoxIl2EngineActorNet).netName = str;
      localNetAircraft2.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setSkill(localNetAircraft1.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Skill);
      try
      {
        localNetAircraft2.weaponsLoad(paramActorSpawnArg.weapons);
        localNetAircraft2.thisWeaponsName = paramActorSpawnArg.weapons; } catch (Exception localException2) {
        NetAircraft.printDebug(localException2);
      }
      if ((this._netUser != null) && ((localNetAircraft2.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMaster()) || (this._netUser.isTrackWriter()))) {
        localNetAircraft2.createCockpits();
      }
      localNetAircraft2.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way = new Way(localNetAircraft1.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way);

      localNetAircraft2.onAircraftLoaded();

      localWing.airc[j] = ((Aircraft)localNetAircraft2);
      localNetAircraft2.setArmy(localNetAircraft1.getArmy());
      localNetAircraft2.setOwner(localWing);
      if ((this._netUser != null) && ((localNetAircraft2.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMaster()) || (this._netUser.isTrackWriter())))
        World.setPlayerRegiment();
      if (Mission.isServer())
        ((Maneuver)localNetAircraft1.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).Group.changeAircraft((Aircraft)localNetAircraft1, (Aircraft)localNetAircraft2);
      localNetAircraft2.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.set(localNetAircraft1.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls);
      localNetAircraft2.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.forceGear(localNetAircraft2.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl);
      ((Aircraft)localNetAircraft2); Aircraft.forceGear(localNetAircraft2.getClass(), localNetAircraft2.hierMesh(), localNetAircraft2.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear());
      localNetAircraft2.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(localNetAircraft1.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
      localNetAircraft2.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
      Vector3d localVector3d = new Vector3d();
      localNetAircraft1.getSpeed(localVector3d);
      localNetAircraft2.setSpeed(localVector3d);
      if (localNetAircraft1.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.brakeShoe) {
        ((Aircraft)localNetAircraft2).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport = localNetAircraft1.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport;
        ((Aircraft)localNetAircraft2).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.brakeShoe = true;
        ((Aircraft)localNetAircraft2).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turnOffCollisions = true;
        ((Aircraft)localNetAircraft2).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.brakeShoeLoc.set(localNetAircraft1.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.brakeShoeLoc);
        ((Aircraft)localNetAircraft2).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.brakeShoeLastCarrier = localNetAircraft1.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.brakeShoeLastCarrier;
        ((Aircraft)localNetAircraft2).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Gears.bFlatTopGearCheck = true;
        ((Aircraft)localNetAircraft2).makeMirrorCarrierRelPos();
      }
      if (localNetAircraft1.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasWingControl) {
        ((Aircraft)localNetAircraft2).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.wingControl = localNetAircraft1.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.wingControl;
        ((Aircraft)localNetAircraft2).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.forceWing(localNetAircraft1.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.wingControl);
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
      if ((localNetAircraft2.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMaster()) && ((!World.cur().diffCur.Takeoff_N_Landing) || (localNetAircraft1.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.get(0).Action != 1) || (!localNetAircraft1.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isStationedOnGround())))
      {
        localNetAircraft2.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setCurControlAll(true);
        localNetAircraft2.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setEngineRunning();
        localNetAircraft2.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.setPowerControl(0.75F);
        localNetAircraft2.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setStationedOnGround(false);
        localNetAircraft2.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setWasAirborne(true);
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
        localNetAircraft.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(localActorSpawnArg.point, localActorSpawnArg.orient);
        localNetAircraft.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
        localNetAircraft.setSpeed(localActorSpawnArg.speed);
        NetAircraft.access$1700(paramNetMsgInput, localActorSpawnArg, localNetAircraft); } catch (Exception localException) {
        NetAircraft.printDebug(localException);
      }
    }

    private Actor _actorSpawn(ActorSpawnArg paramActorSpawnArg) {
      Object localObject = null;
      NetSquadron localNetSquadron = null;
      NetWing localNetWing = null;
      int i = 0;
      NetAircraft localNetAircraft = null;
      try
      {
        int j = paramActorSpawnArg.name.length();
        int m;
        int n;
        String str;
        if (this._netUser != null) {
          i = Integer.parseInt(paramActorSpawnArg.name.substring(j - 2, j));
          m = paramActorSpawnArg.name.charAt(j - 3) - '0';
          n = paramActorSpawnArg.name.charAt(j - 4) - '0';
          if (j == 4) {
            localObject = this._netUser.netUserRegiment;
          } else {
            str = paramActorSpawnArg.name.substring(0, j - 4);
            localObject = (Regiment)Actor.getByName(str);
          }
          localNetSquadron = new NetSquadron((Regiment)localObject, n);
          localNetWing = new NetWing(localNetSquadron, m);
        } else {
          i = Integer.parseInt(paramActorSpawnArg.name.substring(j - 1, j)) + 1;
          m = paramActorSpawnArg.name.charAt(j - 2) - '0';
          n = paramActorSpawnArg.name.charAt(j - 3) - '0';
          i += n * 16 + m * 4;
          str = paramActorSpawnArg.name.substring(0, j - 3);
          localObject = (Regiment)Actor.getByName(str);
          localNetSquadron = new NetSquadron((Regiment)localObject, n);
          localNetWing = new NetWing(localNetSquadron, m);
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
      ((NetAircraft.AircraftNet)localNetAircraft.jdField_net_of_type_ComMaddoxIl2EngineActorNet).netUser = this._netUser;
      ((NetAircraft.AircraftNet)localNetAircraft.jdField_net_of_type_ComMaddoxIl2EngineActorNet).netName = paramActorSpawnArg.name;
      if (this._netUser != null) {
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
      } else {
        localNetAircraft.setFM(2, paramActorSpawnArg.netChannel == null);
      }
      localNetAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setSkill(3);
      localNetAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel = (paramActorSpawnArg.fuel * localNetAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.maxFuel);
      localNetAircraft.bPaintShemeNumberOn = paramActorSpawnArg.bNumberOn;
      try
      {
        localNetAircraft.weaponsLoad(paramActorSpawnArg.weapons);
        localNetAircraft.thisWeaponsName = paramActorSpawnArg.weapons; } catch (Exception localException2) {
        NetAircraft.printDebug(localException2);
      }
      if ((localNetAircraft.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMaster()) || ((this._netUser != null) && (this._netUser.isTrackWriter()))) {
        localNetAircraft.createCockpits();
      }
      localNetAircraft.onAircraftLoaded();

      localNetWing.setPlane(localNetAircraft, i);
      NetAircraft.loadingCountry = null;

      int k = 0;
      if (paramActorSpawnArg.bornPlaceExist) {
        BornPlace localBornPlace = (BornPlace)World.cur().bornPlaces.get(paramActorSpawnArg.bornPlace);
        Loc localLoc = localBornPlace.getAircraftPlace((Aircraft)localNetAircraft, paramActorSpawnArg.stayPlace);
        paramActorSpawnArg.point = localLoc.getPoint();
        paramActorSpawnArg.orient = localLoc.getOrient();
        paramActorSpawnArg.armyExist = true;
        paramActorSpawnArg.army = localBornPlace.army;
        paramActorSpawnArg.speed = new Vector3d();
        if ((!World.cur().diffCur.Takeoff_N_Landing) || ((paramActorSpawnArg.stayPlace >= World.cur().airdrome.stayHold.length) && (!localNetAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.brakeShoe)))
        {
          paramActorSpawnArg.point.z += 1000.0D;
          paramActorSpawnArg.speed.x = 100.0D;
          paramActorSpawnArg.orient.transform(paramActorSpawnArg.speed);
          k = 1;
        } else {
          localNetAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.setLanded();
          ((Aircraft)localNetAircraft); Aircraft.forceGear(localNetAircraft.getClass(), localNetAircraft.hierMesh(), localNetAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear());
        }
        localNetAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsEnableToBailout = localBornPlace.bParachute;
      }
      paramActorSpawnArg.set(localNetAircraft);
      if (k != 0) {
        localNetAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setCurControlAll(true);
        localNetAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setEngineRunning();
        localNetAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.setPowerControl(0.75F);
        localNetAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setStationedOnGround(false);
        localNetAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setWasAirborne(true);
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
      if ((localNetAircraft.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMaster()) || ((this._netUser != null) && (this._netUser.isTrackWriter()))) {
        World.setPlayerRegiment();
      }

      return (Actor)localNetAircraft;
    }

    private void makeName(NetAircraft paramNetAircraft) {
      String str = ((NetAircraft.AircraftNet)paramNetAircraft.jdField_net_of_type_ComMaddoxIl2EngineActorNet).netUser.uniqueName();
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
            localActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet.postTo(NetMissionTrack.netChannelOut(), localNetMsgSpawn);
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
      if (NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.brakeShoe) {
        NetAircraft.corn.set(NetAircraft.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
        NetAircraft.corn1.set(NetAircraft.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
        NetAircraft.corn1.jdField_z_of_type_Double -= 20.0D;
        Actor localActor = Engine.collideEnv().getLine(NetAircraft.corn, NetAircraft.corn1, false, NetAircraft.clipFilter, NetAircraft.pship);
        if ((!(localActor instanceof BigshipGeneric)) && (Mission.isCoop()) && (Time.current() < 60000L))
          localActor = NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.brakeShoeLastCarrier;
        if ((localActor instanceof BigshipGeneric)) {
          this.bUnderDeck = true;
          this._lRel.set(NetAircraft.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
          this._lRel.sub(localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
        }
      }
    }

    public void netFirstUpdate(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
    {
      NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.set(paramFloat7, paramFloat8, paramFloat9);
      NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAccel().set(0.0D, 0.0D, 0.0D);
      this._t = (this.tcur = this.tupdate = Time.current());
      this._p.set(paramFloat1, paramFloat2, paramFloat3);
      this._v.set(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d);

      this._o.set(paramFloat4, paramFloat5, paramFloat6);
      this._w.set(0.0F, 0.0F, 0.0F);

      NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.set(paramFloat1, paramFloat2, paramFloat3);
      NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.set(paramFloat4, paramFloat5, paramFloat6);
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
          localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.forceGear(localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl);
          Aircraft.forceGear(localAircraft.getClass(), localAircraft.hierMesh(), localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear());
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

      NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.set(f10, f11, f12);
      NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAccel().set(f13, f14, f15);

      this._p.set(f1, f2, f3);
      this._v.set(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d);

      this._o.set(f4, f5, f6);
      this._o.transformInv(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d, NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getVflow());
      this._w.set(f7, f8, f9);
      NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getW().set(f7, f8, f9);

      int i10 = i & 0xF;
      float f17;
      int i11;
      float f18;
      if (i10 == 1) {
        float f16 = paramNetMsgInput.readUnsignedByte() / 255.0F * 640.0F;
        f17 = paramNetMsgInput.readUnsignedByte() / 255.0F * 1.6F;
        i10 = NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum();
        for (int i13 = 0; i13 < i10; i13++)
          if (!NetAircraft.this.isFMTrackMirror()) {
            NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i13].setw(f16);
            NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i13].setPropPhi(f17);
          }
      }
      else {
        for (i11 = 0; i11 < i10; i11++) {
          f17 = paramNetMsgInput.readUnsignedByte() / 255.0F * 640.0F;
          f18 = paramNetMsgInput.readUnsignedByte() / 255.0F * 1.6F;
          if (!NetAircraft.this.isFMTrackMirror()) {
            NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i11].setw(f17);
            NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i11].setPropPhi(f18);
          }
        }
      }

      if (((i & 0x10) != 0) && (NetAircraft.this.netCockpitTuretNum >= 0)) {
        i11 = paramNetMsgInput.readUnsignedShort();
        int i12 = paramNetMsgInput.readUnsignedShort();
        f18 = unpackSY(i11);
        float f19 = unpackSP(i12 & 0x7FFF);
        NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[NetAircraft.this.netCockpitWeaponControlNum] = ((i12 & 0x8000) != 0 ? 1 : false);
        if (superObj() == World.getPlayerAircraft()) {
          Actor._tmpOrient.set(f18, f19, 0.0F);
          ((CockpitGunner)Main3D.cur3D().cockpits[NetAircraft.this.netCockpitIndxPilot]).moveGun(Actor._tmpOrient);
        } else {
          Turret localTurret = NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[NetAircraft.this.netCockpitTuretNum];
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
        netFirstUpdate((float)NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, (float)NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double, (float)NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double, NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getAzimut(), NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage(), NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren(), (float)NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double, (float)NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double, (float)NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double);
      }

      paramFloat = (float)(Time.tickNext() - this.tcur) * 0.001F;
      if (paramFloat < 0.001F) return;
      this.tcur = Time.tickNext();

      NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.update(paramFloat, 50.0F, NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface, false, NetAircraft.this.isFMTrackMirror());

      NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.ground(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel, false, this.bGround);
      NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.bFlatTopGearCheck = false;

      for (int i = 0; i < 3; i++) {
        NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelAngles[i] = ((NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelAngles[i] + (float)Math.toDegrees(Math.atan(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gVelocity[i] * paramFloat / 0.375D))) % 360.0F);

        NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gVelocity[i] *= 0.949999988079071D;
      }

      NetAircraft.this.hierMesh().chunkSetAngles("GearL1_D0", 0.0F, -NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelAngles[0], 0.0F);
      NetAircraft.this.hierMesh().chunkSetAngles("GearR1_D0", 0.0F, -NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelAngles[1], 0.0F);
      NetAircraft.this.hierMesh().chunkSetAngles("GearC1_D0", 0.0F, -NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelAngles[2], 0.0F);

      float f2 = NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.getSteeringAngle();

      NetAircraft.this.moveSteering(f2);
      if (NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.nearGround()) {
        NetAircraft.this.moveWheelSink();
      }

      NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.netupdate(paramFloat, NetAircraft.this.isFMTrackMirror());
      NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.FMupdate(paramFloat);
      this.tint = (this.tcur - this.tlag);

      while (this.tint > this._t) {
        long l = this.tint - this._t;
        if (l > Time.tickConstLen())
          l = Time.tickConstLen();
        f3 = (float)l * 0.001F;
        this._p.jdField_x_of_type_Float += this._v.jdField_x_of_type_Float * f3;
        this._p.jdField_y_of_type_Float += this._v.jdField_y_of_type_Float * f3;
        this._p.jdField_z_of_type_Float += this._v.jdField_z_of_type_Float * f3;
        Vector3f tmp618_615 = this._v; tmp618_615.jdField_x_of_type_Float = (float)(tmp618_615.jdField_x_of_type_Float + NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAccel().jdField_x_of_type_Double * f3);
        Vector3f tmp649_646 = this._v; tmp649_646.jdField_y_of_type_Float = (float)(tmp649_646.jdField_y_of_type_Float + NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAccel().jdField_y_of_type_Double * f3);
        Vector3f tmp680_677 = this._v; tmp680_677.jdField_z_of_type_Float = (float)(tmp680_677.jdField_z_of_type_Float + NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAccel().jdField_z_of_type_Double * f3);
        this.TmpV.scale(f3, this._w);
        this._o.increment((float)(-Math.toDegrees(this.TmpV.jdField_z_of_type_Float)), (float)(-Math.toDegrees(this.TmpV.jdField_y_of_type_Float)), (float)Math.toDegrees(this.TmpV.jdField_x_of_type_Float));

        this._t += l;
      }
      World.land(); float f1 = Landscape.HQ(this._p.jdField_x_of_type_Float, this._p.jdField_y_of_type_Float);
      if (World.land().isWater(this._p.jdField_x_of_type_Float, this._p.jdField_y_of_type_Float)) {
        if (this._p.jdField_z_of_type_Float < f1 - 20.0F) this._p.jdField_z_of_type_Float = (f1 - 20.0F);
      }
      else if (this._p.jdField_z_of_type_Float < f1 + 1.0F) this._p.jdField_z_of_type_Float = (f1 + 1.0F);

      this.TmpVd.set(this._p);
      this.save_dt = (0.98F * this.save_dt + 0.02F * ((float)(this.tint - this.tupdate) * 0.001F));

      f2 = 0.03F;
      if (this._v.length() > 0.0F) {
        f2 = 1.08F - this.save_dt * 2.0F;
        if (f2 > 1.0F) f2 = 1.0F;
        if (f2 < 0.03F) f2 = 0.03F;
      }
      this.saveCoeff = (0.98F * this.saveCoeff + 0.02F * f2);

      NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.interpolate(this.TmpVd, this.saveCoeff);

      float f3 = this.saveCoeff * 2.0F;
      if (NetMissionTrack.isPlaying())
        f3 = this.saveCoeff / 4.0F;
      if (f3 > 1.0F) f3 = 1.0F;
      NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.interpolate(this._o, f3);

      if (this.bUnderDeck) {
        NetAircraft.corn.set(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        NetAircraft.corn1.set(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        NetAircraft.corn1.jdField_z_of_type_Double -= 20.0D;
        Actor localActor = Engine.collideEnv().getLine(NetAircraft.corn, NetAircraft.corn1, false, NetAircraft.clipFilter, NetAircraft.pship);
        if ((!(localActor instanceof BigshipGeneric)) && (Mission.isCoop()) && (Time.current() < 60000L))
          localActor = NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.brakeShoeLastCarrier;
        if ((localActor instanceof BigshipGeneric)) {
          NetAircraft.lCorn.set(this._lRel);
          NetAircraft.lCorn.add(localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
          NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.set(NetAircraft.lCorn.getPoint());
          NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.set(NetAircraft.lCorn.getOrient());
          this.saveCoeff = 1.0F;
          this._p.set(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
          this._o.set(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation);
          localActor.getSpeed(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
          this._v.jdField_x_of_type_Float = (float)NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double;
          this._v.jdField_y_of_type_Float = (float)NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double;
          this._v.jdField_z_of_type_Float = (float)NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double;
        }

      }

      if (NetAircraft.this.isFMTrackMirror()) {
        NetAircraft.this.fmTrack.FMupdate(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel);
      }

      if (NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isTick(44, 0)) {
        NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.update(paramFloat * 44.0F);
        ((Aircraft)superObj()).rareAction(paramFloat * 44.0F, false);
        if (NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - Engine.land().HQ_Air(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double) > 40.0D) {
          NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setWasAirborne(true);
          NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setStationedOnGround(false);
        } else if (NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length() < 1.0D) {
          NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setStationedOnGround(true);
        }
      }
    }

    public void netControls(int paramInt)
    {
      NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl = ((paramInt & 0x1) != 0 ? 1.0F : 0.0F);
      NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = ((paramInt & 0x2) != 0 ? 1.0F : 0.0F);
      NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.BrakeControl = ((paramInt & 0x4) != 0 ? 1.0F : 0.0F);
      NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.setRadiatorControl((paramInt & 0x8) != 0 ? 1.0F : 0.0F);
      NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl = ((paramInt & 0x10) != 0 ? 1.0F : 0.0F);
      NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AirBrakeControl = ((paramInt & 0x20) != 0 ? 1.0F : 0.0F);
    }
    public void netWeaponControl(int paramInt) {
      int k = NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl.length;
      int i = 0; for (int j = 1; (i < k) && (j < 256); j <<= 1) {
        NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[i] = ((paramInt & j) != 0 ? 1 : false);

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
      if ((this.netUser == null) && (NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.brakeShoe)) {
        i = NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.actor.hashCode() & 0xF;
        if ((this.countUpdates++ & 0xF) != i)
          return;
      } else {
        this.countUpdates = 0;
      }

      if (this.weaponsIsEmpty) NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WCT = 0;
      int i = (NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WCT & 0xF) != 0 ? 1 : 0;
      try {
        this.out.unLockAndClear();

        int j = 0;

        int k = 0;
        int n = 0;
        int i1 = 0;
        for (int i2 = 0; i2 < NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum(); i2++) {
          i3 = (int)(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i2].getw() / 640.0F * 255.0F);
          if (i2 == 0) i1 = i3;
          else if (i1 != i3) n = 1;
          if (i3 != NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i2].wNetPrev) {
            k = 1;
            NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i2].wNetPrev = i3;
          }
        }
        if (k != 0) {
          if (n != 0) { j = 1; } else {
            j = NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum(); if (j > 15) j = 15;
          }
        }
        if ((NetAircraft.this.netCockpitValid) && (NetAircraft.this.netCockpitTuretNum >= 0))
          j |= 16;
        if (NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround())
          j |= 32;
        if ((NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.isUnderDeck()) && (NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Vrel.lengthSquared() < 2.0D)) {
          NetAircraft.corn.set(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
          NetAircraft.corn1.set(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
          NetAircraft.corn1.jdField_z_of_type_Double -= 20.0D;
          Actor localActor = Engine.collideEnv().getLine(NetAircraft.corn, NetAircraft.corn1, false, NetAircraft.clipFilter, NetAircraft.pship);
          if ((!(localActor instanceof BigshipGeneric)) && (Mission.isCoop()) && (Time.current() < 60000L))
            localActor = NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.brakeShoeLastCarrier;
          if ((localActor instanceof BigshipGeneric)) {
            NetAircraft.lCorn.set(NetAircraft.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
            NetAircraft.lCorn.sub(localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
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
        this.out.writeByte(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.CTL);
        this.out.writeByte(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WCT);
        Controls tmp650_647 = NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls; tmp650_647.WCT = (byte)(tmp650_647.WCT & 0x3);

        NetAircraft.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this.p, this.o);
        this.out.writeFloat((float)this.p.x); this.out.writeFloat((float)this.p.y); this.out.writeFloat((float)this.p.jdField_z_of_type_Double);

        this.o.wrap();
        n = (int)(this.o.getYaw() * 32000.0F / 180.0F);
        i1 = (int)(this.o.tangage() * 32000.0F / 90.0F);
        i2 = (int)(this.o.kren() * 32000.0F / 180.0F);
        this.out.writeShort(n); this.out.writeShort(i1); this.out.writeShort(i2);

        if ((j & 0x40) == 0) {
          this.vec3f.set(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getW());
          i3 = (int)(this.vec3f.jdField_x_of_type_Float * 32000.0F / 50.0F);
          i4 = (int)(this.vec3f.jdField_y_of_type_Float * 32000.0F / 50.0F);
          i5 = (int)(this.vec3f.jdField_z_of_type_Float * 32000.0F / 50.0F);
          this.out.writeShort(i3); this.out.writeShort(i4); this.out.writeShort(i5);
        } else {
          NetAircraft.lCorn.get(this.o);
          this.o.wrap();
          n = (int)(this.o.getYaw() * 32000.0F / 180.0F);
          i1 = (int)(this.o.tangage() * 32000.0F / 90.0F);
          i2 = (int)(this.o.kren() * 32000.0F / 180.0F);
          this.out.writeShort(n); this.out.writeShort(i1); this.out.writeShort(i2);
        }

        this.vec3f.set(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Vwld);
        int i3 = (int)(this.vec3f.jdField_x_of_type_Float * 32000.0F / 400.0F);
        int i4 = (int)(this.vec3f.jdField_y_of_type_Float * 32000.0F / 400.0F);
        int i5 = (int)(this.vec3f.jdField_z_of_type_Float * 32000.0F / 400.0F);
        this.out.writeShort(i3); this.out.writeShort(i4); this.out.writeShort(i5);
        int i7;
        int i8;
        if ((j & 0x40) == 0) {
          this.vec3f.set(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAccel());
          i6 = (int)(this.vec3f.jdField_x_of_type_Float * 32000.0F / 2000.0F);
          i7 = (int)(this.vec3f.jdField_y_of_type_Float * 32000.0F / 2000.0F);
          i8 = (int)(this.vec3f.jdField_z_of_type_Float * 32000.0F / 2000.0F);
          this.out.writeShort(i6); this.out.writeShort(i7); this.out.writeShort(i8);
        } else {
          i6 = (int)(NetAircraft.lCorn.getX() * 32000.0D / 200.0D);
          i7 = (int)(NetAircraft.lCorn.getY() * 32000.0D / 200.0D);
          i8 = (int)(NetAircraft.lCorn.getZ() * 32000.0D / 200.0D);
          this.out.writeShort(i6); this.out.writeShort(i7); this.out.writeShort(i8);
        }

        for (int i6 = 0; i6 < (j & 0xF); i6++) {
          this.out.writeByte((byte)(int)(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i6].getw() / 640.0F * 255.0F));
          this.out.writeByte((byte)(int)(NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i6].getPropPhi() / 1.6F * 255.0F));
        }

        if ((NetAircraft.this.netCockpitValid) && (NetAircraft.this.netCockpitTuretNum >= 0)) {
          Turret localTurret = NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[NetAircraft.this.netCockpitTuretNum];
          i8 = NetAircraft.this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[NetAircraft.this.netCockpitWeaponControlNum];
          this.out.writeShort(packSY(localTurret.tu[0]));
          this.out.writeShort(packSP(localTurret.tu[1]) | (i8 != 0 ? 32768 : 0));
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