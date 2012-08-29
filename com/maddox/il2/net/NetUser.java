package com.maddox.il2.net;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.TargetsGuard;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Airdrome;
import com.maddox.il2.ai.air.Point_Stay;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BmpUtils;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.game.DotRange;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.Selector;
import com.maddox.il2.game.order.OrdersTree;
import com.maddox.il2.gui.GUIAirArming;
import com.maddox.il2.gui.GUINetAircraft;
import com.maddox.il2.gui.GUINetAircraft.Item;
import com.maddox.il2.gui.GUINetClientCBrief;
import com.maddox.il2.gui.GUINetClientDBrief;
import com.maddox.il2.net.client.ProfileUser;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.air.NetAircraft.AircraftNet;
import com.maddox.il2.objects.air.NetGunner;
import com.maddox.il2.objects.air.Paratrooper;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.Finger;
import com.maddox.rts.HomePath;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgInvokeMethod_Object;
import com.maddox.rts.MsgNet;
import com.maddox.rts.NetAddress;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetChannelOutStream;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetFilter;
import com.maddox.rts.NetHost;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.NetUpdate;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.rts.net.NetFileClient;
import com.maddox.rts.net.NetFileRequest;
import com.maddox.sound.RadioChannelSpawn;
import com.maddox.util.HashMapExt;
import com.maddox.util.IntHashtable;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

public class NetUser extends NetHost
  implements NetFileClient, NetUpdate
{
  public static final int MSG_READY = 1;
  public static final int MSG_BORNPLACE = 2;
  public static final int MSG_AIRDROMESTAY = 3;
  public static final int MSG_STAT = 4;
  public static final int MSG_STAT_INC = 5;
  public static final int MSG_CURSTAT = 6;
  public static final int MSG_CURSTAT_INC = 7;
  public static final int MSG_PING = 8;
  public static final int MSG_PING_INC = 9;
  public static final int MSG_REGIMENT = 10;
  public static final int MSG_SKIN = 11;
  public static final int MSG_PILOT = 12;
  public static final int MSG_REQUEST_PLACE = 13;
  public static final int MSG_PLACE = 14;
  public static final int MSG_REQUEST_WAIT_START = 15;
  public static final int MSG_WAIT_START = 16;
  public static final int MSG_KICK = 17;
  public static final int MSG_MISSION_COMPLETE = 18;
  public static final int MSG_CAMERA = 19;
  public static final int MSG_ORDER_CMD = 20;
  public static final int MSG_RADIO = 21;
  public static final int MSG_VOICE = 22;
  public static final int MSG_TASK_COMPLETE = 23;
  public static final int MSG_HOUSE_DIE = 24;
  public static final int MSG_HOUSE_SYNC = 25;
  public static final int MSG_BRIDGE_RDIE = 26;
  public static final int MSG_BRIDGE_DIE = 27;
  public static final int MSG_BRIDGE_SYNC = 28;
  public static final int MSG_DOT_RANGE_FRIENDLY = 29;
  public static final int MSG_DOT_RANGE_FOE = 30;
  public static final int MSG_EVENTLOG = 31;
  public static final int MSG_NOISEART = 32;
  private NetUserStat stat = new NetUserStat();
  private NetUserStat curstat = new NetUserStat();

  private static NetUserStat _st = new NetUserStat();
  private static NetUserStat __st = new NetUserStat();
  private NetUserStat fullStat;
  private ProfileUser profile;
  private String address;
  private int port;
  private String sessionID;
  private int profileID;
  private int idChannelFirst;
  public int ping = 0;

  private int army = 0;

  private boolean bTrackWriter = false;

  private int bornPlace = -1;
  private int airdromeStay = -1;
  private String uniqueName = null;

  private int place = -1;
  private boolean bWaitStartCoopMission = false;
  public int syncCoopStart;
  private int localRequestPlace = -1;

  private static int armyCoopWinner = 0;
  public NetUserRegiment netUserRegiment;
  protected NetMaxLag netMaxLag;
  public OrdersTree ordersTree;
  private NetFileRequest netFileRequestMissProp;
  private NetFileRequest netFileRequestMissPropLocale;
  private String ownerSkinBmp = "";
  private String localSkinBmp;
  private String skinDir;
  private NetFileRequest netSkinRequest;
  private Mat[] cacheSkinMat = new Mat[3];

  private String ownerPilotBmp = "";
  private String localPilotBmp;
  private String localPilotTga;
  private NetFileRequest netPilotRequest;
  private Mat[] cachePilotMat = new Mat[1];

  private String ownerNoseartBmp = "";
  private String localNoseartBmp;
  private String localNoseartTga;
  private NetFileRequest netNoseartRequest;
  private Mat[] cacheNoseartMat = new Mat[2];

  private String radio = null;
  private int curCodec = 0;
  private Actor viewActor;
  private AircraftNetFilter airNetFilter;
  private long lastTimeUpdate = 0L;

  private boolean bPingUpdateStarted = false;

  public NetUserStat stat()
  {
    return this.stat; } 
  public NetUserStat curstat() { return this.curstat;
  }

  public void reset()
  {
    this.army = 0;
    this.stat.clear();
    this.curstat.clear();
    this.netMaxLag = null;
  }
  public String uniqueName() {
    return this.uniqueName;
  }
  public boolean isTrackWriter() { return this.bTrackWriter; }

  public static NetUser findTrackWriter() {
    List localList = NetEnv.hosts();
    int i = localList.size();
    for (int j = 0; j < i; j++) {
      NetUser localNetUser = (NetUser)localList.get(j);
      if (localNetUser.isTrackWriter())
        return localNetUser;
    }
    return null;
  }

  public void setShortName(String paramString) {
    if (paramString == null) paramString = "";
    super.setShortName(paramString);
    if ((isMaster()) && (!isMirrored()))
      makeUniqueName();
  }

  private void makeUniqueName() {
    String str1 = shortName();
    ArrayList localArrayList = new ArrayList(NetEnv.hosts());
    localArrayList.add(NetEnv.host());
    int i = localArrayList.size();
    int j = 0;
    while (true) {
      int k = 0;
      for (int m = 0; m < i; m++) {
        NetUser localNetUser = (NetUser)localArrayList.get(m);
        String str2 = localNetUser.uniqueName();
        if ((str1.equals(str2)) && (localNetUser != this)) {
          k = 1;
          break;
        }
      }
      if (k == 0)
        break;
      str1 = shortName() + j;
      j++;
    }
    this.uniqueName = str1;
  }

  private void pingUpdateInc() {
    new MsgAction(64, 10.0D, this) {
      public void doAction(Object paramObject) { NetUser localNetUser = (NetUser)paramObject;
        if (localNetUser.isDestroyed()) return;
        if ((Main.cur().netServerParams != null) && (!Main.cur().netServerParams.isDestroyed()) && (!Main.cur().netServerParams.isMaster()) && (!(Main.cur().netServerParams.masterChannel() instanceof NetChannelInStream)))
        {
          try
          {
            NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted(1);
            localNetMsgGuaranted.writeByte(9);
            NetUser.this.postTo(Main.cur().netServerParams.masterChannel(), localNetMsgGuaranted); } catch (Exception localException) {
            NetUser.access$000(localException);
          }
        }
        NetUser.this.pingUpdateInc(); }  } ;
  }

  public int getArmy() {
    return this.army;
  }
  public void setArmy(int paramInt) {
    this.army = paramInt;
    radio_onArmyChanged();
  }

  public void sendStatInc()
  {
    if (!isMaster()) return;
    if (Main.cur().netServerParams == null) return;
    _st.clear();
    _st.fillFromScoreCounter(true);
    if (_st.isEmpty()) return;
    _sendStatInc(false);
  }
  private void sendCurStatInc() {
    if (!isMaster()) return;
    if (Main.cur().netServerParams == null) return;
    _st.clear();
    _st.fillFromScoreCounter(false);
    if (_st.isEmpty()) return;
    __st.set(this.stat);
    __st.inc(_st);
    if (__st.isEqualsCurrent(this.curstat)) return;
    _sendStatInc(true);
  }
  public void netUpdate() {
    if (!isMaster()) return;
    checkCameraBaseChanged();
    long l = Time.real();
    if (this.lastTimeUpdate + 20000L > l) return;

    this.lastTimeUpdate = l;
    if (!Mission.isNet()) return;
    if (!Mission.isPlaying()) return;
    if (Main.cur().netServerParams == null) return;
    if ((Main.cur().netServerParams.masterChannel() instanceof NetChannelInStream)) return;
    sendCurStatInc();
  }

  private void _sendStatInc(boolean paramBoolean)
  {
    if (Main.cur().netServerParams.isMaster())
    {
      if (Main.cur().netServerParams.isCoop())
      {
        if (paramBoolean) {
          this.curstat.set(_st);
        } else {
          this.stat.set(_st);
          this.curstat.set(_st);
        }

      }
      else if (paramBoolean) {
        this.curstat.set(this.stat);
        this.curstat.inc(_st);
        _st.set(this.curstat);
      } else {
        this.stat.inc(_st);
        this.curstat.set(this.stat);
        _st.set(this.stat);
      }

      ((NetUser)NetEnv.host()).replicateStat(this, paramBoolean);
    } else {
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(paramBoolean ? 7 : 5);
        _st.write(localNetMsgGuaranted);
        postTo(Main.cur().netServerParams.masterChannel(), localNetMsgGuaranted); } catch (Exception localException) {
        NetObj.printDebug(localException);
      }
    }
  }

  private void replicateStat(NetUser paramNetUser, boolean paramBoolean) {
    replicateStat(paramNetUser, paramBoolean, null);
  }
  private void replicateStat(NetUser paramNetUser, boolean paramBoolean, NetChannel paramNetChannel) {
    try {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(paramBoolean ? 6 : 4);
      localNetMsgGuaranted.writeNetObj(paramNetUser);
      _st.write(localNetMsgGuaranted);
      if (paramNetChannel != null)
        postTo(paramNetChannel, localNetMsgGuaranted);
      else
        post(localNetMsgGuaranted); 
    } catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }
  private void getIncStat(NetMsgInput paramNetMsgInput, boolean paramBoolean) throws IOException {
    _st.read(paramNetMsgInput);
    _sendStatInc(paramBoolean);
  }
  private void getStat(NetMsgInput paramNetMsgInput, boolean paramBoolean) throws IOException {
    _st.read(paramNetMsgInput);
    NetUser localNetUser = (NetUser)paramNetMsgInput.readNetObj();
    if (localNetUser == null) return;
    if (paramBoolean) {
      localNetUser.curstat.set(_st);
    } else {
      localNetUser.stat.set(_st);
      localNetUser.curstat.set(_st);
    }

    replicateStat(localNetUser, paramBoolean);
  }

  public int getAirdromeStay()
  {
    return this.airdromeStay; } 
  public int getBornPlace() { return this.bornPlace; } 
  public void setBornPlace(int paramInt) {
    if (this.bornPlace == paramInt)
      return;
    this.bornPlace = paramInt;
    this.airdromeStay = -1;
    if (isMirrored())
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted(2);
        localNetMsgGuaranted.writeByte(2);
        localNetMsgGuaranted.writeByte(this.bornPlace);
        post(localNetMsgGuaranted); } catch (Exception localException) {
        NetObj.printDebug(localException);
      }
    if ((this.bornPlace >= 0) && (World.cur().bornPlaces != null) && (this.bornPlace < World.cur().bornPlaces.size()) && (Main.cur().netServerParams != null))
    {
      BornPlace localBornPlace = (BornPlace)World.cur().bornPlaces.get(this.bornPlace);
      setArmy(localBornPlace.army);
      if (Main.cur().netServerParams.isMaster())
      {
        double d1 = localBornPlace.r * localBornPlace.r;

        Point_Stay[][] arrayOfPoint_Stay = World.cur().airdrome.stay;
        for (int i = 0; i < arrayOfPoint_Stay.length; i++) {
          if (arrayOfPoint_Stay[i] != null) {
            Point_Stay localPoint_Stay = arrayOfPoint_Stay[i][(arrayOfPoint_Stay[i].length - 1)];
            double d2 = (localPoint_Stay.jdField_x_of_type_Float - localBornPlace.place.jdField_x_of_type_Double) * (localPoint_Stay.jdField_x_of_type_Float - localBornPlace.place.jdField_x_of_type_Double) + (localPoint_Stay.jdField_y_of_type_Float - localBornPlace.place.jdField_y_of_type_Double) * (localPoint_Stay.jdField_y_of_type_Float - localBornPlace.place.jdField_y_of_type_Double);

            if ((d2 > d1) || 
              (((NetUser)NetEnv.host()).airdromeStay == i)) continue;
            List localList = NetEnv.hosts();
            int j = 0;
            for (int k = 0; k < localList.size(); k++) {
              NetUser localNetUser = (NetUser)localList.get(k);
              if (localNetUser.airdromeStay == i) {
                j = 1;
                break;
              }
            }
            if (j == 0) {
              this.airdromeStay = i;
              d1 = d2;
            }
          }
        }
        if (isMirror())
          sendAirdromeStay(this.bornPlace, this.airdromeStay);
      }
    }
  }

  private void sendAirdromeStay(int paramInt1, int paramInt2) {
    try {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted(6);
      localNetMsgGuaranted.writeByte(3);
      localNetMsgGuaranted.writeByte(paramInt1);
      localNetMsgGuaranted.writeInt(paramInt2);
      postTo(this.jdField_masterChannel_of_type_ComMaddoxRtsNetChannel, localNetMsgGuaranted); } catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  public void onConnectReady(NetChannel paramNetChannel)
  {
    try {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted(1);
      localNetMsgGuaranted.writeByte(1);
      postTo(paramNetChannel, localNetMsgGuaranted);
      paramNetChannel.userState = 1; } catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  public int getPlace()
  {
    if ((isMaster()) && (this.localRequestPlace != this.place))
      return -1;
    return this.place;
  }
  private int _getPlace() {
    return this.place;
  }
  public void requestPlace(int paramInt) {
    armyCoopWinner = 0;
    if (isMaster()) {
      if (this.localRequestPlace == paramInt)
        return;
      this.localRequestPlace = paramInt;
      this.place = -1;
    }
    NetServerParams localNetServerParams = Main.cur().netServerParams;
    Object localObject;
    if (localNetServerParams.isMaster()) {
      if (paramInt != -1) {
        localObject = (NetUser)NetEnv.host();
        if (((NetUser)localObject)._getPlace() == paramInt) {
          paramInt = -1;
        } else {
          List localList = NetEnv.hosts();
          for (int j = 0; j < localList.size(); j++) {
            localObject = (NetUser)localList.get(j);
            if (((NetUser)localObject)._getPlace() == paramInt) {
              paramInt = -1;
              break;
            }
          }
        }
      }
      this.place = paramInt;
      if (this.place >= 0)
        setArmy(GUINetAircraft.getItem(this.place).reg.getArmy());
      this.bWaitStartCoopMission = false;
      if (NetEnv.host().isMirrored()) {
        localObject = NetEnv.channels();
        for (int i = 0; i < ((List)localObject).size(); i++) {
          NetChannel localNetChannel = (NetChannel)((List)localObject).get(i);
          if ((!localNetChannel.isMirrored(this)) && (localNetChannel != masterChannel())) continue;
          try {
            NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
            localNetMsgGuaranted.writeByte(14);
            localNetMsgGuaranted.writeByte(this.place);
            localNetMsgGuaranted.writeNetObj(this);
            NetEnv.host().postTo(localNetChannel, localNetMsgGuaranted); } catch (Exception localException2) {
            NetObj.printDebug(localException2);
          }
        }
      }
    }
    else {
      try {
        localObject = new NetMsgGuaranted();
        ((NetMsgGuaranted)localObject).writeByte(13);
        ((NetMsgGuaranted)localObject).writeByte(paramInt);
        postTo(localNetServerParams.masterChannel(), (NetMsgGuaranted)localObject); } catch (Exception localException1) {
        NetObj.printDebug(localException1);
      }
    }
  }

  public void resetAllPlaces() {
    NetServerParams localNetServerParams = Main.cur().netServerParams;
    if ((localNetServerParams == null) || (!localNetServerParams.isMaster()))
      return;
    ((NetUser)NetEnv.host()).requestPlace(-1);
    List localList = NetEnv.hosts();
    if (localList == null) return;
    for (int i = 0; i < localList.size(); i++) {
      NetUser localNetUser = (NetUser)localList.get(i);
      localNetUser.requestPlace(-1);
    }
  }

  public void missionLoaded() {
    if (!Mission.isCoop()) return;
    if (!(Mission.cur().netObj().masterChannel() instanceof NetChannelInStream)) {
      List localList = NetEnv.hosts();
      if (localList == null) return;
      for (int i = 0; i < localList.size(); i++) {
        NetUser localNetUser = (NetUser)localList.get(i);
        if (localNetUser.place >= 0)
          localNetUser.setArmy(GUINetAircraft.getItem(localNetUser.place).reg.getArmy());
      }
    }
  }

  public boolean isWaitStartCoopMission()
  {
    return (this.bWaitStartCoopMission) && (getPlace() >= 0);
  }
  public void doWaitStartCoopMission() {
    NetServerParams localNetServerParams = Main.cur().netServerParams;
    if (localNetServerParams.isMaster()) {
      this.bWaitStartCoopMission = true;
      if (NetEnv.host().isMirrored())
        try {
          NetMsgGuaranted localNetMsgGuaranted1 = new NetMsgGuaranted();
          localNetMsgGuaranted1.writeByte(16);
          localNetMsgGuaranted1.writeNetObj(this);
          NetEnv.host().post(localNetMsgGuaranted1); } catch (Exception localException1) {
          NetObj.printDebug(localException1);
        }
    }
    else {
      try {
        NetMsgGuaranted localNetMsgGuaranted2 = new NetMsgGuaranted();
        localNetMsgGuaranted2.writeByte(15);
        postTo(localNetServerParams.masterChannel(), localNetMsgGuaranted2); } catch (Exception localException2) {
        NetObj.printDebug(localException2);
      }
    }
  }

  public void kick(NetUser paramNetUser)
  {
    if ((paramNetUser == null) || (paramNetUser.isDestroyed())) return;
    NetServerParams localNetServerParams = Main.cur().netServerParams;
    if (!localNetServerParams.isMaster())
      return;
    if (paramNetUser.isMaster())
      return;
    _kick(paramNetUser);
  }
  private void _kick(NetUser paramNetUser) {
    if ((paramNetUser == null) || (paramNetUser.isDestroyed())) return;
    new MsgAction(72, 0.0D, paramNetUser) {
      public void doAction(Object paramObject) {
        NetUser localNetUser = (NetUser)paramObject;
        if ((localNetUser == null) || (localNetUser.isDestroyed())) return;
        if (localNetUser.path() == null)
          localNetUser.masterChannel().destroy("You have been kicked from the server.");
        else
          try {
            NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
            localNetMsgGuaranted.writeByte(17);
            localNetMsgGuaranted.writeNetObj(localNetUser);
            NetEnv.host().postTo(localNetUser.masterChannel(), localNetMsgGuaranted); } catch (Exception localException) {
            NetUser.access$200(localException);
          }
      }
    };
  }

  public void coopMissionComplete(boolean paramBoolean)
  {
    if (isMirrored())
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(18);
        localNetMsgGuaranted.writeByte(paramBoolean ? 1 : 0);
        post(localNetMsgGuaranted); } catch (Exception localException) {
        NetObj.printDebug(localException);
      }
    setArmyCoopWinner(paramBoolean);
  }
  private void setClientMissionComplete(boolean paramBoolean) {
    coopMissionComplete(paramBoolean);

    World.cur().targetsGuard.doMissionComplete();
  }

  private void setArmyCoopWinner(boolean paramBoolean) {
    armyCoopWinner = World.getMissionArmy();
    if (!paramBoolean)
      armyCoopWinner = armyCoopWinner % 2 + 1; 
  }

  public static int getArmyCoopWinner() {
    return armyCoopWinner;
  }

  public void speekVoice(int paramInt1, int paramInt2, int paramInt3, String paramString, int[] paramArrayOfInt, int paramInt4, boolean paramBoolean)
  {
    if (isMirrored())
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(22);
        localNetMsgGuaranted.writeShort(paramInt1);
        localNetMsgGuaranted.writeShort(paramInt2);
        if ((paramString != null) && (paramString.length() == 2))
          paramInt3 |= 32768;
        localNetMsgGuaranted.writeShort(paramInt3);
        if ((paramString != null) && (paramString.length() == 2))
          localNetMsgGuaranted.write255(paramString);
        localNetMsgGuaranted.writeBoolean(paramBoolean);
        int i = paramArrayOfInt.length;
        for (int j = 0; j < i; j++) {
          int k = paramArrayOfInt[j];
          if (k == 0) break;
          localNetMsgGuaranted.writeShort(k);
        }
        post(localNetMsgGuaranted); } catch (Exception localException) {
        NetObj.printDebug(localException);
      }
  }

  private void getVoice(NetMsgInput paramNetMsgInput) {
    try {
      int i = paramNetMsgInput.readUnsignedShort();
      int j = paramNetMsgInput.readUnsignedShort();
      int k = paramNetMsgInput.readUnsignedShort();
      String str = null;
      if ((k & 0x8000) != 0) {
        k &= -32769;
        str = paramNetMsgInput.read255();
      }
      boolean bool = paramNetMsgInput.readBoolean();
      int m = paramNetMsgInput.available() / 2;
      if (m == 0)
        return;
      int[] arrayOfInt = new int[m + 1];
      for (int n = 0; n < m; n++) arrayOfInt[n] = paramNetMsgInput.readUnsignedShort();
      arrayOfInt[m] = 0;
      speekVoice(i, j, k, str, arrayOfInt, 1, bool);

      Voice.setSyncMode(i);
      Voice.speak(j, k, str, arrayOfInt, 1, false, bool); } catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  private void checkCameraBaseChanged()
  {
    if (!Config.isUSE_RENDER()) return;
    if (!Mission.isNet()) return;
    NetServerParams localNetServerParams = Main.cur().netServerParams;
    if ((localNetServerParams == null) || (localNetServerParams.isMaster())) return;
    Actor localActor = Main3D.cur3D().viewActor();
    if (this.viewActor == localActor) return;
    this.viewActor = localActor;
    ActorNet localActorNet = null;
    if (Actor.isValid(localActor))
      localActorNet = localActor.net;
    replicateCameraBaseChanged(localActorNet);
  }

  private void replicateCameraBaseChanged(NetObj paramNetObj) {
    NetServerParams localNetServerParams = Main.cur().netServerParams;
    if (localNetServerParams.isMaster()) {
      if (paramNetObj != null) {
        Object localObject = paramNetObj.superObj();
        if ((localObject != null) && ((localObject instanceof Actor))) {
          this.viewActor = ((Actor)localObject);
          return;
        }
      }
      this.viewActor = null;
    } else {
      doReplicateCameraBaseChanged(paramNetObj);
    }
  }

  public void doReplicateCameraBaseChanged(Object paramObject) {
    if (isDestroyed()) return;
    NetServerParams localNetServerParams = Main.cur().netServerParams;
    if (localNetServerParams == null) return;
    NetObj localNetObj = null;
    if (paramObject != null) {
      localNetObj = (NetObj)paramObject;
      if (localNetObj.isDestroyed()) {
        localNetObj = null;
      } else if ((localNetObj.masterChannel() != localNetServerParams.masterChannel()) && (!localNetServerParams.masterChannel().isMirrored(localNetObj)))
      {
        new MsgInvokeMethod_Object("doReplicateCameraBaseChanged", localNetObj).post(72, this);

        return;
      }
    }
    if ((localNetServerParams.masterChannel() instanceof NetChannelInStream)) return; try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(19);
      localNetMsgGuaranted.writeNetObj(localNetObj);
      postTo(localNetServerParams.masterChannel(), localNetMsgGuaranted); } catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  public void orderCmd(int paramInt)
  {
    NetServerParams localNetServerParams = Main.cur().netServerParams;
    if (localNetServerParams.isMaster()) {
      if (paramInt == -1) this.ordersTree.activate();
      else if (paramInt == -2) this.ordersTree.unactivate(); else
        this.ordersTree.execCmd(paramInt);
    }
    else try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(20);
        localNetMsgGuaranted.writeByte(paramInt);
        postTo(localNetServerParams.masterChannel(), localNetMsgGuaranted); } catch (Exception localException) {
        NetObj.printDebug(localException);
      } 
  }

  public void orderCmd(int paramInt, Actor paramActor)
  {
    NetServerParams localNetServerParams = Main.cur().netServerParams;
    Object localObject;
    if (localNetServerParams.isMaster()) {
      if (paramInt == -1) { this.ordersTree.activate();
      } else if (paramInt == -2) { this.ordersTree.unactivate();
      } else {
        localObject = Selector.getTarget();
        Selector.setTarget(paramActor);
        this.ordersTree.execCmd(paramInt);
        Selector.setTarget((Actor)localObject);
      }
    }
    else try {
        localObject = new NetMsgGuaranted();
        ((NetMsgGuaranted)localObject).writeByte(20);
        ((NetMsgGuaranted)localObject).writeByte(paramInt);
        ((NetMsgGuaranted)localObject).writeNetObj(paramActor.net);
        postTo(localNetServerParams.masterChannel(), (NetMsgGuaranted)localObject); } catch (Exception localException) {
        NetObj.printDebug(localException);
      }
  }

  public void postTaskComplete(Actor paramActor)
  {
    if (!Actor.isValid(paramActor)) return;
    World.onTaskComplete(paramActor);
    if (paramActor.net.countMirrors() == 0) return; try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(23);
      localNetMsgGuaranted.writeNetObj(paramActor.net);
      post(localNetMsgGuaranted); } catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  public void replicateDotRange()
  {
    replicateDotRange(true);
    replicateDotRange(false);
  }
  public void replicateDotRange(boolean paramBoolean) {
    replicateDotRange(paramBoolean, null);
  }

  private void replicateDotRange(NetChannel paramNetChannel) {
    replicateDotRange(true, paramNetChannel);
    replicateDotRange(false, paramNetChannel);
  }
  private void replicateDotRange(boolean paramBoolean, NetChannel paramNetChannel) {
    if (Main.cur().netServerParams == null) return;
    if (this != Main.cur().netServerParams.host()) return;
    if ((isMirrored()) || (paramNetChannel != null))
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(paramBoolean ? 29 : 30);
        if (paramBoolean) Main.cur().dotRangeFriendly.netOutput(localNetMsgGuaranted); else
          Main.cur().dotRangeFoe.netOutput(localNetMsgGuaranted);
        if (paramNetChannel == null) post(localNetMsgGuaranted); else
          postTo(paramNetChannel, localNetMsgGuaranted); 
      } catch (Exception localException) {
        NetObj.printDebug(localException);
      }
  }

  public void replicateEventLog(int paramInt1, float paramFloat1, String paramString1, String paramString2, int paramInt2, float paramFloat2, float paramFloat3)
  {
    if (Main.cur().netServerParams == null) return;
    if (!isMirrored()) return; try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(31);
      localNetMsgGuaranted.writeByte(paramInt1);
      localNetMsgGuaranted.writeFloat(paramFloat1);
      localNetMsgGuaranted.write255(paramString1);
      localNetMsgGuaranted.write255(paramString2);
      localNetMsgGuaranted.writeByte(paramInt2);
      localNetMsgGuaranted.writeFloat(paramFloat2);
      localNetMsgGuaranted.writeFloat(paramFloat3);
      post(localNetMsgGuaranted); } catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  private void getEventLog(NetMsgInput paramNetMsgInput) {
    try {
      int i = paramNetMsgInput.readByte();
      float f1 = paramNetMsgInput.readFloat();
      String str1 = paramNetMsgInput.read255();
      String str2 = paramNetMsgInput.read255();
      int j = paramNetMsgInput.readByte();
      float f2 = paramNetMsgInput.readFloat();
      float f3 = paramNetMsgInput.readFloat();
      EventLog.type(i, f1, str1, str2, j, f2, f3, false);
      replicateEventLog(i, f1, str1, str2, j, f2, f3); } catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException
  {
    if (super.netInput(paramNetMsgInput))
      return true;
    paramNetMsgInput.reset();
    int i = paramNetMsgInput.readByte();
    int i1;
    if ((isMirror()) && (paramNetMsgInput.channel() == this.jdField_masterChannel_of_type_ComMaddoxRtsNetChannel))
    {
      Object localObject2;
      NetUser localNetUser;
      int k;
      Object localObject3;
      Object localObject1;
      switch (i)
      {
      case 1:
        if (paramNetMsgInput.channel().userState == -1) {
          paramNetMsgInput.channel().userState = 1;
          if ((Mission.cur() != null) && (Mission.cur().netObj() != null)) {
            MsgNet.postRealNewChannel(Mission.cur().netObj(), this.jdField_masterChannel_of_type_ComMaddoxRtsNetChannel);
          }
        }
        return true;
      case 14:
        int j = paramNetMsgInput.readUnsignedByte();
        if (j == 255) j = -1;
        localObject2 = (NetUser)paramNetMsgInput.readNetObj();
        if (localObject2 == null)
          return true;
        ((NetUser)localObject2).place = j;
        if ((j >= 0) && (Mission.cur() != null) && (Main.cur().missionLoading == null))
          ((NetUser)localObject2).setArmy(GUINetAircraft.getItem(j).reg.getArmy());
        ((NetUser)localObject2).bWaitStartCoopMission = false;
        if (isMirrored())
          try {
            NetMsgGuaranted localNetMsgGuaranted1 = new NetMsgGuaranted();
            localNetMsgGuaranted1.writeByte(14);
            localNetMsgGuaranted1.writeByte(j);
            localNetMsgGuaranted1.writeNetObj((NetObj)localObject2);
            post(localNetMsgGuaranted1); } catch (Exception localException3) {
            NetObj.printDebug(localException3);
          }
        return true;
      case 16:
        localNetUser = (NetUser)paramNetMsgInput.readNetObj();
        if (localNetUser == null)
          return true;
        localNetUser.bWaitStartCoopMission = true;
        if (isMirrored())
          try {
            localObject2 = new NetMsgGuaranted();
            ((NetMsgGuaranted)localObject2).writeByte(16);
            ((NetMsgGuaranted)localObject2).writeNetObj(localNetUser);
            post((NetMsgGuaranted)localObject2); } catch (Exception localException1) {
            NetObj.printDebug(localException1);
          }
        return true;
      case 17:
        localNetUser = (NetUser)paramNetMsgInput.readNetObj();
        if (localNetUser == null)
          return true;
        _kick(localNetUser);
        return true;
      case 20:
        k = paramNetMsgInput.readByte();
        if (paramNetMsgInput.available() > 0) {
          localObject3 = null;
          NetObj localNetObj = paramNetMsgInput.readNetObj();
          if (localNetObj != null)
            localObject3 = (Actor)localNetObj.superObj();
          orderCmd(k, (Actor)localObject3);
        } else {
          orderCmd(k);
        }
        return true;
      case 18:
        k = paramNetMsgInput.readByte() != 0 ? 1 : 0;
        if (isMirrored())
          try {
            localObject3 = new NetMsgGuaranted();
            ((NetMsgGuaranted)localObject3).writeByte(18);
            ((NetMsgGuaranted)localObject3).writeByte(k != 0 ? 1 : 0);
            post((NetMsgGuaranted)localObject3); } catch (Exception localException2) {
            NetObj.printDebug(localException2);
          }
        setClientMissionComplete(k);
        return true;
      case 2:
        int m = paramNetMsgInput.readUnsignedByte();
        if (m == 255) m = -1;
        setBornPlace(m);
        return true;
      case 10:
        localObject1 = paramNetMsgInput.read255();
        char[] arrayOfChar = new char[2];
        arrayOfChar[0] = paramNetMsgInput.readChar();
        arrayOfChar[1] = paramNetMsgInput.readChar();
        int i2 = paramNetMsgInput.readUnsignedByte();
        String str = paramNetMsgInput.read255();
        setUserRegiment((String)localObject1, str, arrayOfChar, i2);
        replicateNetUserRegiment();
        return true;
      case 11:
        localObject1 = paramNetMsgInput.read255();
        setSkin((String)localObject1);
        replicateSkin();
        return true;
      case 12:
        localObject1 = paramNetMsgInput.read255();
        setPilot((String)localObject1);
        replicatePilot();
        return true;
      case 32:
        localObject1 = paramNetMsgInput.read255();
        setNoseart((String)localObject1);
        replicateNoseart();
        return true;
      case 21:
        localObject1 = null;
        i1 = 0;
        if (paramNetMsgInput.available() > 0) {
          localObject1 = paramNetMsgInput.read255();
          if (paramNetMsgInput.available() > 0) { i1 = paramNetMsgInput.readInt();
          } else {
            i1 = -1;
            System.out.println("ERROR: Radio channel message has old format");
          }
        }
        if (i1 != -1) replicateRadio((String)localObject1, i1);
        return true;
      case 22:
        getVoice(paramNetMsgInput);
        return true;
      case 19:
        replicateCameraBaseChanged(paramNetMsgInput.readNetObj());
        return true;
      case 23:
        localObject1 = paramNetMsgInput.readNetObj();
        if (localObject1 == null) return true;
        postTaskComplete((Actor)((NetObj)localObject1).superObj());
        return true;
      case 29:
        Main.cur().dotRangeFriendly.netInput(paramNetMsgInput);
        replicateDotRange(true);
        return true;
      case 30:
        Main.cur().dotRangeFoe.netInput(paramNetMsgInput);
        replicateDotRange(false);
        return true;
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 13:
      case 15:
      case 24:
      case 25:
      case 26:
      case 27:
      case 28:
      case 31:
      }
    }
    int n;
    Object localObject4;
    switch (i)
    {
    case 13:
      n = paramNetMsgInput.readUnsignedByte();
      if (n == 255) n = -1;
      requestPlace(n);
      return true;
    case 15:
      doWaitStartCoopMission();
      return true;
    case 3:
      n = paramNetMsgInput.readUnsignedByte();
      if (n == 255) n = -1;
      i1 = paramNetMsgInput.readInt();
      if (n == this.bornPlace) {
        if (isMirror())
          sendAirdromeStay(n, i1);
        else {
          this.airdromeStay = i1;
        }
      }
      return true;
    case 4:
    case 6:
      getStat(paramNetMsgInput, i == 6);
      return true;
    case 5:
    case 7:
      getIncStat(paramNetMsgInput, i == 7);
      return true;
    case 9:
      n = 0;
      if (paramNetMsgInput.available() == 4)
        n = paramNetMsgInput.readInt();
      n += paramNetMsgInput.channel().ping();
      if (Main.cur().netServerParams.isMaster()) {
        this.ping = n;
        localObject4 = new NetMsgGuaranted();
        ((NetMsgGuaranted)localObject4).writeByte(8);
        ((NetMsgGuaranted)localObject4).writeInt(n);
        ((NetMsgGuaranted)localObject4).writeNetObj(this);
        ((NetUser)NetEnv.host()).post((NetMsgGuaranted)localObject4);
      } else {
        localObject4 = new NetMsgGuaranted();
        ((NetMsgGuaranted)localObject4).writeByte(9);
        ((NetMsgGuaranted)localObject4).writeInt(n);
        postTo(Main.cur().netServerParams.masterChannel(), (NetMsgGuaranted)localObject4);
      }
      return true;
    case 8:
      n = paramNetMsgInput.readInt();
      localObject4 = (NetUser)paramNetMsgInput.readNetObj();
      if (localObject4 != null) {
        ((NetUser)localObject4).ping = n;
        NetMsgGuaranted localNetMsgGuaranted2 = new NetMsgGuaranted();
        localNetMsgGuaranted2.writeByte(8);
        localNetMsgGuaranted2.writeInt(n);
        localNetMsgGuaranted2.writeNetObj((NetObj)localObject4);
        post(localNetMsgGuaranted2);
      }
      return true;
    case 24:
      if (World.cur().statics != null)
        World.cur().statics.netMsgHouseDie(this, paramNetMsgInput);
      return true;
    case 25:
      if (World.cur().statics != null)
        World.cur().statics.netMsgHouseSync(paramNetMsgInput);
      return true;
    case 26:
      if (World.cur().statics != null)
        World.cur().statics.netMsgBridgeRDie(paramNetMsgInput);
      return true;
    case 27:
      if (World.cur().statics != null)
        World.cur().statics.netMsgBridgeDie(this, paramNetMsgInput);
      return true;
    case 28:
      if (World.cur().statics != null)
        World.cur().statics.netMsgBridgeSync(paramNetMsgInput);
      return true;
    case 31:
      getEventLog(paramNetMsgInput);
      return true;
    case 10:
    case 11:
    case 12:
    case 14:
    case 16:
    case 17:
    case 18:
    case 19:
    case 20:
    case 21:
    case 22:
    case 23:
    case 29:
    case 30: } return false;
  }

  public void netFileAnswer(NetFileRequest paramNetFileRequest)
  {
    Object localObject1;
    Object localObject2;
    if (this.netUserRegiment.netFileRequest == paramNetFileRequest) {
      this.netUserRegiment.netFileRequest = null;
      if ((paramNetFileRequest.state() != 0) && 
        (!NetFilesTrack.existFile(paramNetFileRequest))) {
        return;
      }
      this.netUserRegiment.setLocalFileNameBmp(paramNetFileRequest.localFileName());
      if (this.netUserRegiment.localFileNameBmp != null) {
        NetFilesTrack.recordFile(Main.cur().netFileServerReg, this, this.netUserRegiment.ownerFileNameBmp, this.netUserRegiment.localFileNameBmp);
      }
      if (!this.netUserRegiment.isEmpty()) {
        localObject1 = findAircraft();
        if (localObject1 != null) {
          localObject2 = ((Aircraft)localObject1).netName();
          int j = ((String)localObject2).length();
          int k = 0;
          try {
            k = Integer.parseInt(((String)localObject2).substring(j - 2, j)); } catch (Exception localException) {
          }
          ((Aircraft)localObject1).preparePaintScheme(k);
        }
      }
    } else if (this.netSkinRequest == paramNetFileRequest) {
      this.netSkinRequest = null;
      if ((paramNetFileRequest.state() != 0) && 
        (!NetFilesTrack.existFile(paramNetFileRequest))) {
        return;
      }
      this.localSkinBmp = paramNetFileRequest.localFileName();
      if (this.localSkinBmp.length() == 0) {
        this.localSkinBmp = null;
      } else {
        tryPrepareSkin(findAircraft());
        NetFilesTrack.recordFile(Main.cur().netFileServerSkin, this, this.ownerSkinBmp, this.localSkinBmp);
      }
    }
    else if (this.netNoseartRequest == paramNetFileRequest) {
      this.netNoseartRequest = null;
      if ((paramNetFileRequest.state() != 0) && 
        (!NetFilesTrack.existFile(paramNetFileRequest))) {
        return;
      }
      this.localNoseartBmp = paramNetFileRequest.localFileName();
      if (this.localNoseartBmp.length() == 0) {
        this.localNoseartBmp = null;
      } else {
        tryPrepareNoseart(findAircraft());
        NetFilesTrack.recordFile(Main.cur().netFileServerNoseart, this, this.ownerNoseartBmp, this.localNoseartBmp);
      }
    }
    else if (this.netPilotRequest == paramNetFileRequest) {
      this.netPilotRequest = null;
      if ((paramNetFileRequest.state() != 0) && 
        (!NetFilesTrack.existFile(paramNetFileRequest))) {
        return;
      }
      this.localPilotBmp = paramNetFileRequest.localFileName();
      if (this.localPilotBmp.length() == 0) {
        this.localPilotBmp = null;
      } else {
        localObject1 = findGunner();
        localObject2 = findAircraft();
        if (localObject1 == null)
          tryPreparePilot((NetAircraft)localObject2);
        else if (Actor.isValid((Actor)localObject2)) {
          tryPreparePilot((NetAircraft)localObject2, ((Aircraft)localObject2).netCockpitAstatePilotIndx(((NetGunner)localObject1).getCockpitNum()));
        }
        NetFilesTrack.recordFile(Main.cur().netFileServerPilot, this, this.ownerPilotBmp, this.localPilotBmp);
      }
    }
    else
    {
      int i;
      Object localObject3;
      if (this.netFileRequestMissProp == paramNetFileRequest) {
        if (paramNetFileRequest.state() != 0) {
          this.netFileRequestMissProp = null;
          return;
        }
        localObject1 = paramNetFileRequest.localFileName();
        if (((String)localObject1).equals(paramNetFileRequest.ownerFileName()))
          localObject1 = Main.cur().netFileServerMissProp.primaryPath() + "/" + (String)localObject1;
        else
          localObject1 = Main.cur().netFileServerMissProp.alternativePath() + "/" + (String)localObject1;
        this.netFileRequestMissProp = null;
        i = ((String)localObject1).lastIndexOf(".properties");
        if (i < 0)
          return;
        localObject1 = ((String)localObject1).substring(0, i);
        if (Main.cur().netServerParams.isCoop()) {
          localObject3 = (GUINetClientCBrief)GameState.get(46);
          if (!((GUINetClientCBrief)localObject3).isExistTextDescription())
            ((GUINetClientCBrief)localObject3).setTextDescription((String)localObject1);
        } else if (Main.cur().netServerParams.isDogfight()) {
          localObject3 = (GUINetClientDBrief)GameState.get(40);
          if (!((GUINetClientDBrief)localObject3).isExistTextDescription())
            ((GUINetClientDBrief)localObject3).setTextDescription((String)localObject1);
        }
      }
      else if (this.netFileRequestMissPropLocale == paramNetFileRequest) {
        if (paramNetFileRequest.state() != 0) {
          this.netFileRequestMissPropLocale = null;
          return;
        }
        localObject1 = paramNetFileRequest.localFileName();
        if (((String)localObject1).equals(paramNetFileRequest.ownerFileName()))
          localObject1 = Main.cur().netFileServerMissProp.primaryPath() + "/" + (String)localObject1;
        else
          localObject1 = Main.cur().netFileServerMissProp.alternativePath() + "/" + (String)localObject1;
        this.netFileRequestMissPropLocale = null;
        i = ((String)localObject1).lastIndexOf(".properties");
        if (i < 0)
          return;
        localObject1 = ((String)localObject1).substring(0, i);
        i = ((String)localObject1).lastIndexOf("_" + RTSConf.cur.locale.getLanguage());
        if (i > 0)
          localObject1 = ((String)localObject1).substring(0, i);
        if (Main.cur().netServerParams.isCoop()) {
          localObject3 = (GUINetClientCBrief)GameState.get(46);
          ((GUINetClientCBrief)localObject3).setTextDescription((String)localObject1);
        } else if (Main.cur().netServerParams.isDogfight()) {
          localObject3 = (GUINetClientDBrief)GameState.get(40);
          ((GUINetClientDBrief)localObject3).setTextDescription((String)localObject1);
        }
      }
    }
  }

  public void recordNetFiles() {
    if (this.netUserRegiment.localFileNameBmp != null) {
      NetFilesTrack.recordFile(Main.cur().netFileServerReg, this, this.netUserRegiment.ownerFileNameBmp, this.netUserRegiment.localFileNameBmp);
    }
    if (this.localSkinBmp != null) {
      NetFilesTrack.recordFile(Main.cur().netFileServerSkin, this, this.ownerSkinBmp, this.localSkinBmp);
    }
    if (this.localPilotBmp != null) {
      NetFilesTrack.recordFile(Main.cur().netFileServerPilot, this, this.ownerPilotBmp, this.localPilotBmp);
    }
    if (this.localNoseartBmp != null)
      NetFilesTrack.recordFile(Main.cur().netFileServerNoseart, this, this.ownerNoseartBmp, this.localNoseartBmp);
  }

  public void setMissProp(String paramString)
  {
    if (!Config.isUSE_RENDER()) return;
    if (Main.cur().netServerParams.isMaster())
      return;
    Object localObject;
    if (Main.cur().netServerParams.isCoop()) {
      localObject = (GUINetClientCBrief)GameState.get(46);
      ((GUINetClientCBrief)localObject).clearTextDescription();
    } else if (Main.cur().netServerParams.isDogfight()) {
      localObject = (GUINetClientDBrief)GameState.get(40);
      ((GUINetClientDBrief)localObject).clearTextDescription();
    }
    if (!paramString.startsWith("missions/"))
      return;
    paramString = paramString.substring("missions/".length());
    for (int i = paramString.length() - 1; i > 0; i--) {
      int j = paramString.charAt(i);
      if ((j == 92) || (j == 47)) break;
      if (j == 46) {
        paramString = paramString.substring(0, i);
        break;
      }
    }
    if (this.netFileRequestMissProp != null) {
      this.netFileRequestMissProp.doCancel();
      this.netFileRequestMissProp = null;
    }
    if (this.netFileRequestMissPropLocale != null) {
      this.netFileRequestMissPropLocale.doCancel();
      this.netFileRequestMissPropLocale = null;
    }
    if (!RTSConf.cur.locale.equals(Locale.US)) {
      this.netFileRequestMissPropLocale = new NetFileRequest(this, Main.cur().netFileServerMissProp, 220, Main.cur().netServerParams, paramString + "_" + RTSConf.cur.locale.getLanguage() + ".properties");

      this.netFileRequestMissPropLocale.doRequest();
    }
    this.netFileRequestMissProp = new NetFileRequest(this, Main.cur().netFileServerMissProp, 210, Main.cur().netServerParams, paramString + ".properties");

    this.netFileRequestMissProp.doRequest();
  }

  public void setUserRegiment(String paramString1, String paramString2, char[] paramArrayOfChar, int paramInt)
  {
    if (this.netUserRegiment.equals(paramString1, paramString2, paramArrayOfChar, paramInt))
      return;
    this.netUserRegiment.set(paramString1, paramString2, paramArrayOfChar, paramInt);
    if (this.netUserRegiment.netFileRequest != null) {
      this.netUserRegiment.netFileRequest.doCancel();
      this.netUserRegiment.netFileRequest = null;
    }
    if (isMaster()) {
      this.netUserRegiment.setLocalFileNameBmp(this.netUserRegiment.ownerFileNameBmp());
      if (NetMissionTrack.isRecording())
        NetFilesTrack.recordFile(Main.cur().netFileServerReg, this, this.netUserRegiment.ownerFileNameBmp, this.netUserRegiment.localFileNameBmp);
    }
    else if ((this.netUserRegiment.ownerFileNameBmp().length() > 0) && (
      (Config.cur.netSkinDownload) || ((masterChannel() instanceof NetChannelInStream)))) {
      this.netUserRegiment.netFileRequest = new NetFileRequest(this, Main.cur().netFileServerReg, 200, this, this.netUserRegiment.ownerFileNameBmp());

      this.netUserRegiment.netFileRequest.doRequest();
    }
  }

  public void replicateNetUserRegiment()
  {
    replicateNetUserRegiment(null);
  }
  private void replicateNetUserRegiment(NetChannel paramNetChannel) {
    if ((!isMirrored()) && (paramNetChannel == null)) return; try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted(1);
      localNetMsgGuaranted.writeByte(10);
      localNetMsgGuaranted.write255(this.netUserRegiment.branch());
      localNetMsgGuaranted.writeChar(this.netUserRegiment.aid()[0]);
      localNetMsgGuaranted.writeChar(this.netUserRegiment.aid()[1]);
      localNetMsgGuaranted.writeByte(this.netUserRegiment.gruppeNumber());
      localNetMsgGuaranted.write255(this.netUserRegiment.ownerFileNameBmp);
      if (paramNetChannel == null)
        post(localNetMsgGuaranted);
      else
        postTo(paramNetChannel, localNetMsgGuaranted); 
    } catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  public void tryPrepareSkin(NetAircraft paramNetAircraft)
  {
    if (!Config.isUSE_RENDER()) return;
    if (!Actor.isValid(paramNetAircraft)) return;
    if (this.localSkinBmp == null) return;
    Aircraft localAircraft = (Aircraft)paramNetAircraft;
    Class localClass = localAircraft.getClass();
    Regiment localRegiment = localAircraft.getRegiment();
    String str1 = localRegiment.country();
    String str2 = Aircraft.getPropertyMesh(localClass, str1);
    if (this.skinDir == null) {
      String str3 = str2;
      int i = str3.lastIndexOf('/');
      if (i >= 0)
        str3 = str3.substring(0, i + 1) + "summer";
      else {
        str3 = str3 + "summer";
      }
      NetFileServerSkin localNetFileServerSkin = Main.cur().netFileServerSkin;
      String str4;
      if (this.ownerSkinBmp.equals(this.localSkinBmp)) {
        str4 = localNetFileServerSkin.primaryPath() + "/" + this.localSkinBmp;

        this.skinDir = ("" + Finger.file(0L, str4, -1));
      } else {
        str4 = localNetFileServerSkin.alternativePath() + "/" + this.localSkinBmp;
        i = this.localSkinBmp.lastIndexOf('.');
        if (i >= 0) this.skinDir = this.localSkinBmp.substring(0, i); else
          this.skinDir = this.localSkinBmp;
      }
      this.skinDir = ("PaintSchemes/Cache/" + this.skinDir);
      try {
        File localFile = new File(HomePath.toFileSystemName(this.skinDir, 0));
        if (!localFile.isDirectory())
          localFile.mkdir();
      } catch (Exception localException) {
        this.skinDir = null;
      }
      if (!BmpUtils.bmp8PalTo4TGA4(str4, str3, this.skinDir))
        this.skinDir = null;
    }
    if (this.skinDir == null)
      return;
    Aircraft.prepareMeshCamouflage(str2, localAircraft.hierMesh(), this.skinDir, this.cacheSkinMat);
  }

  public void setSkin(String paramString) {
    if (paramString == null)
      paramString = "";
    if (paramString.equals(this.ownerSkinBmp))
      return;
    this.ownerSkinBmp = paramString;
    this.localSkinBmp = null;
    this.skinDir = null;
    if (this.netSkinRequest != null) {
      this.netSkinRequest.doCancel();
      this.netSkinRequest = null;
    }
    if (isMaster()) {
      if (paramString.length() > 0) {
        this.localSkinBmp = this.ownerSkinBmp;
        if (NetMissionTrack.isRecording())
          NetFilesTrack.recordFile(Main.cur().netFileServerSkin, this, this.ownerSkinBmp, this.localSkinBmp);
      }
      else {
        this.localSkinBmp = null;
      }
    } else if ((paramString.length() > 0) && (
      (Config.cur.netSkinDownload) || ((masterChannel() instanceof NetChannelInStream)))) {
      this.netSkinRequest = new NetFileRequest(this, Main.cur().netFileServerSkin, 100, this, this.ownerSkinBmp);

      this.netSkinRequest.doRequest();
    }
  }

  public void replicateSkin()
  {
    replicateSkin(null);
  }

  private void replicateSkin(NetChannel paramNetChannel) {
    if ((!isMirrored()) && (paramNetChannel == null)) return; try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(11);
      localNetMsgGuaranted.write255(this.ownerSkinBmp);
      if (paramNetChannel == null)
        post(localNetMsgGuaranted);
      else
        postTo(paramNetChannel, localNetMsgGuaranted); 
    } catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  public void checkReplicateSkin(String paramString) {
    if (!isMaster()) return;
    if (!"".equals(this.ownerSkinBmp)) return;
    UserCfg localUserCfg = World.cur().userCfg;
    String str = localUserCfg.getSkin(paramString);
    if (str == null) return;
    setSkin(GUIAirArming.validateFileName(paramString) + "/" + str);
    replicateSkin();
  }

  public void tryPreparePilot(NetAircraft paramNetAircraft)
  {
    tryPreparePilotSkin(paramNetAircraft, 0);
  }
  public void tryPreparePilot(NetAircraft paramNetAircraft, int paramInt) {
    tryPreparePilotSkin(paramNetAircraft, paramInt);
  }
  public void tryPreparePilot(Paratrooper paramParatrooper) {
    tryPreparePilotSkin(paramParatrooper, 0);
  }
  public void tryPreparePilotSkin(Actor paramActor, int paramInt) {
    if (!Config.isUSE_RENDER()) return;
    if (!Actor.isValid(paramActor)) return;
    if (paramInt < 0) return;
    if (this.localPilotBmp == null) return;
    if (this.localPilotTga == null) {
      localObject = Main.cur().netFileServerPilot;

      if (this.ownerPilotBmp.equals(this.localPilotBmp))
        str = ((NetFileServerPilot)localObject).primaryPath() + "/" + this.localPilotBmp;
      else {
        str = ((NetFileServerPilot)localObject).alternativePath() + "/" + this.localPilotBmp;
      }
      this.localPilotTga = this.localPilotBmp.substring(0, this.localPilotBmp.length() - 4);
      if (!BmpUtils.bmp8PalToTGA3(str, "PaintSchemes/Cache/Pilot" + this.localPilotTga + ".tga"))
      {
        this.localPilotTga = null;
        return;
      }
    }
    if (this.localPilotTga == null)
      return;
    Object localObject = "PaintSchemes/Cache/Pilot" + this.localPilotTga + ".mat";
    String str = "PaintSchemes/Cache/Pilot" + this.localPilotTga + ".tga";
    if ((paramActor instanceof NetAircraft))
      Aircraft.prepareMeshPilot(((NetAircraft)paramActor).hierMesh(), paramInt, (String)localObject, str, this.cachePilotMat);
    else if ((paramActor instanceof Paratrooper))
      ((Paratrooper)paramActor).prepareSkin((String)localObject, str, this.cachePilotMat);
  }

  public void tryPreparePilotDefaultSkin(Aircraft paramAircraft, int paramInt) {
    if (!Config.isUSE_RENDER()) return;
    if (!Actor.isValid(paramAircraft)) return;
    if (paramInt < 0) return;
    String str1 = Aircraft.getPropertyMesh(paramAircraft.getClass(), paramAircraft.getRegiment().country());
    String str2 = HomePath.concatNames(str1, "pilot" + (1 + paramInt) + ".mat");
    Aircraft.prepareMeshPilot(paramAircraft.hierMesh(), paramInt, str2, "3do/plane/textures/pilot" + (1 + paramInt) + ".tga");
  }

  public void setPilot(String paramString) {
    if (paramString == null)
      paramString = "";
    if (paramString.equals(this.ownerPilotBmp))
      return;
    this.ownerPilotBmp = paramString;
    this.localPilotBmp = null;
    this.localPilotTga = null;
    if (this.netPilotRequest != null) {
      this.netPilotRequest.doCancel();
      this.netPilotRequest = null;
    }
    if (isMaster()) {
      if (paramString.length() > 0) {
        this.localPilotBmp = this.ownerPilotBmp;
        if (NetMissionTrack.isRecording())
          NetFilesTrack.recordFile(Main.cur().netFileServerPilot, this, this.ownerPilotBmp, this.localPilotBmp);
      }
      else {
        this.localPilotBmp = null;
      }
    } else if ((paramString.length() > 0) && (
      (Config.cur.netSkinDownload) || ((masterChannel() instanceof NetChannelInStream)))) {
      this.netPilotRequest = new NetFileRequest(this, Main.cur().netFileServerPilot, 150, this, this.ownerPilotBmp);

      this.netPilotRequest.doRequest();
    }
  }

  public void replicatePilot()
  {
    replicatePilot(null);
  }
  private void replicatePilot(NetChannel paramNetChannel) {
    if ((!isMirrored()) && (paramNetChannel == null)) return; try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(12);
      localNetMsgGuaranted.write255(this.ownerPilotBmp);
      if (paramNetChannel == null)
        post(localNetMsgGuaranted);
      else
        postTo(paramNetChannel, localNetMsgGuaranted); 
    } catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  public void checkReplicatePilot() {
    if (!isMaster()) return;
    if (!"".equals(this.ownerPilotBmp)) return;
    UserCfg localUserCfg = World.cur().userCfg;
    String str = localUserCfg.netPilot;
    if (str == null) return;
    setPilot(str);
    replicatePilot();
  }

  public void tryPrepareNoseart(NetAircraft paramNetAircraft)
  {
    if (!Config.isUSE_RENDER()) return;
    if (!Actor.isValid(paramNetAircraft)) return;
    if (this.localNoseartBmp == null) return;
    if (this.localNoseartTga == null) {
      NetFileServerNoseart localNetFileServerNoseart = Main.cur().netFileServerNoseart;
      String str;
      if (this.ownerNoseartBmp.equals(this.localNoseartBmp))
        str = localNetFileServerNoseart.primaryPath() + "/" + this.localNoseartBmp;
      else {
        str = localNetFileServerNoseart.alternativePath() + "/" + this.localNoseartBmp;
      }
      this.localNoseartTga = this.localNoseartBmp.substring(0, this.localNoseartBmp.length() - 4);
      if (!BmpUtils.bmp8PalTo2TGA4(str, "PaintSchemes/Cache/Noseart0" + this.localNoseartTga + ".tga", "PaintSchemes/Cache/Noseart1" + this.localNoseartTga + ".tga"))
      {
        this.localNoseartTga = null;
        return;
      }
    }
    if (this.localNoseartTga == null)
      return;
    Aircraft.prepareMeshNoseart(paramNetAircraft.hierMesh(), "PaintSchemes/Cache/Noseart0" + this.localNoseartTga + ".mat", "PaintSchemes/Cache/Noseart1" + this.localNoseartTga + ".mat", "PaintSchemes/Cache/Noseart0" + this.localNoseartTga + ".tga", "PaintSchemes/Cache/Noseart1" + this.localNoseartTga + ".tga", this.cacheNoseartMat);
  }

  public void setNoseart(String paramString)
  {
    if (paramString == null)
      paramString = "";
    if (paramString.equals(this.ownerNoseartBmp))
      return;
    this.ownerNoseartBmp = paramString;
    this.localNoseartBmp = null;
    this.localNoseartTga = null;
    if (this.netNoseartRequest != null) {
      this.netNoseartRequest.doCancel();
      this.netNoseartRequest = null;
    }
    if (isMaster()) {
      if (paramString.length() > 0) {
        this.localNoseartBmp = this.ownerNoseartBmp;
        if (NetMissionTrack.isRecording())
          NetFilesTrack.recordFile(Main.cur().netFileServerNoseart, this, this.ownerNoseartBmp, this.localNoseartBmp);
      }
      else {
        this.localNoseartBmp = null;
      }
    } else if ((paramString.length() > 0) && (
      (Config.cur.netSkinDownload) || ((masterChannel() instanceof NetChannelInStream)))) {
      this.netNoseartRequest = new NetFileRequest(this, Main.cur().netFileServerNoseart, 175, this, this.ownerNoseartBmp);

      this.netNoseartRequest.doRequest();
    }
  }

  public void replicateNoseart()
  {
    replicateNoseart(null);
  }
  private void replicateNoseart(NetChannel paramNetChannel) {
    if ((!isMirrored()) && (paramNetChannel == null)) return; try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(32);
      localNetMsgGuaranted.write255(this.ownerNoseartBmp);
      if (paramNetChannel == null)
        post(localNetMsgGuaranted);
      else
        postTo(paramNetChannel, localNetMsgGuaranted); 
    } catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  public void checkReplicateNoseart(String paramString) {
    if (!isMaster()) return;
    if (!"".equals(this.ownerNoseartBmp)) return;
    UserCfg localUserCfg = World.cur().userCfg;
    String str = localUserCfg.getNoseart(paramString);
    if (str == null) return;
    setNoseart(str);
    replicateNoseart();
  }

  public String radio() {
    return this.radio; } 
  public int curCodec() { return this.curCodec; } 
  public boolean isRadioNone() {
    return this.radio == null; } 
  public boolean isRadioCommon() { return " 0".equals(this.radio); } 
  public boolean isRadioArmy() {
    if (this.radio == null) return false;
    if (this.radio.length() < 2) return false;
    if (this.radio.charAt(0) != ' ') return false;
    return this.radio.charAt(1) != '0';
  }

  public boolean isRadioPrivate() {
    return (!isRadioNone()) && (!isRadioCommon()) && (!isRadioArmy());
  }

  public void setRadio(String paramString, int paramInt) {
    replicateRadio(paramString, paramInt);
  }

  public void radio_onCreated(String paramString) {
    if (!Chat.USE_NET_PHONE) return;
    if ((this.radio != null) && (this.radio.equals(paramString)))
      Chat.radioSpawn.set(this.radio);
  }

  private void radio_onArmyChanged() {
    if (isMirror()) return;
    if (!isRadioArmy()) return;
    replicateRadio(" " + getArmy(), 1);
  }

  private void replicateRadio(String paramString, int paramInt) {
    if (this.radio == paramString) return;
    if ((paramString != null) && (paramString.equals(this.radio))) return;
    this.radio = paramString;
    this.curCodec = paramInt;

    if (!Chat.USE_NET_PHONE) return;

    if (Main.cur().netServerParams == null) return;
    if ((Main.cur().netServerParams.isMaster()) && 
      (this.radio != null) && (!Chat.radioSpawn.isExistChannel(this.radio))) {
      Chat.radioSpawn.create(this.radio, this.curCodec);
    }

    if (isMaster())
      if (this.radio == null)
        Chat.radioSpawn.set(null);
      else if (Chat.radioSpawn.isExistChannel(this.radio))
        Chat.radioSpawn.set(this.radio);
    Object localObject;
    if (Main.cur().netServerParams.isMaster()) {
      localObject = null;
      int i = Chat.radioSpawn.getNumChannels();
      for (int j = 0; j < i; j++) {
        String str = Chat.radioSpawn.getChannelName(j);
        if (str.equals(((NetUser)NetEnv.host()).radio()))
          continue;
        int m = 0;
        List localList = NetEnv.hosts();
        for (int n = 0; n < localList.size(); n++) {
          NetUser localNetUser = (NetUser)localList.get(n);
          if (str.equals(localNetUser.radio)) {
            m = 1;
            break;
          }
        }
        if (m != 0)
          continue;
        if (localObject == null)
          localObject = new ArrayList();
        ((ArrayList)localObject).add(str);
      }
      if (localObject != null) {
        for (int k = 0; k < ((ArrayList)localObject).size(); k++) {
          Chat.radioSpawn.kill((String)((ArrayList)localObject).get(k));
        }
      }
    }

    if (!isMirrored()) return; try
    {
      localObject = new NetMsgGuaranted();
      ((NetMsgGuaranted)localObject).writeByte(21);
      if (this.radio != null) {
        ((NetMsgGuaranted)localObject).write255(this.radio);
        ((NetMsgGuaranted)localObject).writeInt(this.curCodec);
      }
      post((NetMsgGuaranted)localObject);
    } catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  private void tryPostChatTimeSpeed()
  {
    try
    {
      if (!NetChannel.bCheckServerTimeSpeed) return;
      if (isDestroyed()) return;
      if (Main.cur().chat == null) return;
      if (masterChannel() == null) return;
      if (masterChannel().isMirrored(Main.cur().chat)) {
        ArrayList localArrayList = new ArrayList(1);
        localArrayList.add(this);
        Main.cur().chat.send(null, "checkTimeSpeed " + NetChannel.checkTimeSpeedInterval + "sec" + " " + (int)Math.round(NetChannel.checkTimeSpeedDifferense * 100.0D) + "%", localArrayList, 0, false);
      }
      else
      {
        new MsgAction(64, 1.0D, this) {
          public void doAction(Object paramObject) { NetUser localNetUser = (NetUser)paramObject;
            localNetUser.tryPostChatTimeSpeed(); } } ;
      }
    } catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  public Aircraft findAircraft()
  {
    Map.Entry localEntry = Engine.name2Actor().nextEntry(null);
    while (localEntry != null) {
      Actor localActor = (Actor)localEntry.getValue();
      if (((localActor instanceof Aircraft)) && (((Aircraft)localActor).netUser() == this))
        return (Aircraft)localActor;
      localEntry = Engine.name2Actor().nextEntry(localEntry);
    }
    return null;
  }
  public NetGunner findGunner() {
    Map.Entry localEntry = Engine.name2Actor().nextEntry(null);
    while (localEntry != null) {
      Actor localActor = (Actor)localEntry.getValue();
      if (((localActor instanceof NetGunner)) && (((NetGunner)localActor).getUser() == this))
        return (NetGunner)localActor;
      localEntry = Engine.name2Actor().nextEntry(localEntry);
    }
    return null;
  }

  public void destroy() {
    super.destroy();
    if ((isMirror()) && (NetEnv.isServer()) && 
      (!((Connect)NetEnv.cur().connect).banned.isExist(this.jdField_shortName_of_type_JavaLangString)))
      Chat.sendLog(0, "user_leaves", shortName(), null);
    if (Actor.isValid(this.netUserRegiment))
      this.netUserRegiment.destroy();
    if (this.airNetFilter != null) {
      NetChannel localNetChannel = masterChannel();
      if ((localNetChannel != null) && (!localNetChannel.isDestroying()))
        localNetChannel.filterRemove(this.airNetFilter);
      this.airNetFilter = null;
    }
    if (Mission.isPlaying()) {
      if ((Mission.isCoop()) && (Time.current() > 1L))
        new NetUserLeft(uniqueName(), this.army, this.curstat);
      EventLog.onDisconnected(uniqueName());
    }
  }

  public void msgNetNewChannel(NetChannel paramNetChannel)
  {
    try
    {
      if (paramNetChannel.isMirrored(this)) return;
      int i = 0;
      if (((Main.cur() instanceof Main3D)) && (Main3D.cur3D().isDemoPlaying())) {
        if (isMaster()) {
          if (NetMissionTrack.isPlaying())
            return;
        }
        else i = 1;
      }

      NetMsgSpawn localNetMsgSpawn = new NetMsgSpawn(this);
      localNetMsgSpawn.write255(this.jdField_shortName_of_type_JavaLangString);
      localNetMsgSpawn.writeByte(this.bornPlace);
      localNetMsgSpawn.writeByte(this.place);
      localNetMsgSpawn.writeShort(this.idChannelFirst);
      if (isMirror()) {
        if (this.jdField_path_of_type_ArrayOfComMaddoxRtsNetHost != null) {
          for (int j = 0; j < this.jdField_path_of_type_ArrayOfComMaddoxRtsNetHost.length; j++)
            localNetMsgSpawn.writeNetObj(this.jdField_path_of_type_ArrayOfComMaddoxRtsNetHost[j]);
        }
        if (i == 0)
          localNetMsgSpawn.writeNetObj(NetEnv.host());
      } else if ((!NetEnv.isServer()) && 
        (!this.bPingUpdateStarted)) {
        this.bPingUpdateStarted = true;
        pingUpdateInc();
      }

      if (((paramNetChannel instanceof NetChannelOutStream)) && ((Main.cur() instanceof Main3D)) && (Main3D.cur3D().isDemoPlaying()) && (this.bTrackWriter))
      {
        localNetMsgSpawn.writeBoolean(true);
      }
      else localNetMsgSpawn.writeBoolean(false);
      postTo(paramNetChannel, localNetMsgSpawn);

      if (!"".equals(this.netUserRegiment.ownerFileNameBmp)) {
        replicateNetUserRegiment(paramNetChannel);
      }
      if (!"".equals(this.ownerSkinBmp)) {
        replicateSkin(paramNetChannel);
      }
      if (!"".equals(this.ownerPilotBmp)) {
        replicatePilot(paramNetChannel);
      }
      if (!"".equals(this.ownerNoseartBmp)) {
        replicateNoseart(paramNetChannel);
      }
      if (this.radio != null) {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(21);
        localNetMsgGuaranted.write255(this.radio);
        localNetMsgGuaranted.writeInt(this.curCodec);
        postTo(paramNetChannel, localNetMsgGuaranted);
      }

      replicateDotRange(paramNetChannel); } catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  public NetUser(String paramString) {
    super(paramString);
    makeUniqueName();
    this.netUserRegiment = new NetUserRegiment();
  }

  public NetUser(NetChannel paramNetChannel, int paramInt, String paramString, NetHost[] paramArrayOfNetHost) {
    super(paramNetChannel, paramInt, paramString, paramArrayOfNetHost);
    if (NetEnv.isServer()) {
      if (((Connect)NetEnv.cur().connect).banned.isExist(paramString)) {
        kick(this);
        System.out.println("User '" + paramString + "' [" + paramNetChannel.remoteAddress().getHostAddress() + "] banned");
      } else {
        Chat.sendLog(0, "user_joins", shortName(), null);
      }
      this.airNetFilter = new AircraftNetFilter();
      paramNetChannel.filterAdd(this.airNetFilter);

      _st.clear();
      ((NetUser)NetEnv.host()).replicateStat(this, false, paramNetChannel);
    }
    makeUniqueName();
    this.netUserRegiment = new NetUserRegiment();

    if (NetEnv.isServer()) {
      System.out.println("socket channel '" + paramNetChannel.id() + "', ip " + paramNetChannel.remoteAddress().getHostAddress() + ":" + paramNetChannel.remotePort() + ", " + uniqueName() + ", is complete created");

      new MsgAction(64, 1.0D, this) {
        public void doAction(Object paramObject) { NetUser localNetUser = (NetUser)paramObject;
          localNetUser.tryPostChatTimeSpeed(); } } ;
    }
    EventLog.onConnected(uniqueName());
  }

  static {
    Spawn.add(NetUser.class, new SPAWN());
  }

  class AircraftNetFilter
    implements NetFilter
  {
    AircraftNetFilter()
    {
    }

    public float filterNetMessage(NetChannel paramNetChannel, NetMsgFiltered paramNetMsgFiltered)
    {
      Object localObject1 = paramNetMsgFiltered.filterArg();
      if (localObject1 == null) return -1.0F;
      IntHashtable localIntHashtable = null;
      ActorPos localActorPos = null;
      Object localObject2;
      if ((localObject1 instanceof NetAircraft)) {
        localObject2 = (NetAircraft)localObject1;
        localIntHashtable = ((NetAircraft.AircraftNet)((NetAircraft)localObject2).jdField_net_of_type_ComMaddoxIl2EngineActorNet).filterTable;
        localActorPos = ((NetAircraft)localObject2).pos;
      } else if ((localObject1 instanceof NetGunner)) {
        localObject2 = (NetGunner)localObject1;
        localIntHashtable = ((NetGunner)localObject2).getFilterTable();
        localActorPos = ((NetGunner)localObject2).pos;
      } else {
        return -1.0F;
      }

      if (Time.isPaused()) return 0.0F;

      if (!Actor.isValid(NetUser.this.viewActor)) {
        return 0.5F;
      }
      int i = localIntHashtable.get(paramNetChannel.id());
      if (i == -1) {
        localIntHashtable.put(paramNetChannel.id(), (int)(Time.current() & 0x7FFFFFF));
        return 1.0F;
      }
      double d1 = (int)(Time.current() & 0x7FFFFFF) - i;
      if (d1 < 0.0D) {
        localIntHashtable.put(paramNetChannel.id(), (int)(Time.current() & 0x7FFFFFF));
        return 1.0F;
      }
      double d2 = NetUser.this.viewActor.pos.getAbsPoint().distance(localActorPos.getAbsPoint());
      if (d2 > 10000.0D)
        d2 = 10000.0D;
      if (d2 < 1.0D)
        d2 = 1.0D;
      double d3 = d2 * 5000.0D / 10000.0D;
      float f = (float)(d1 / d3);
      if (f >= 1.0F)
        return 1.0F;
      return f * f;
    }

    public void filterNetMessagePosting(NetChannel paramNetChannel, NetMsgFiltered paramNetMsgFiltered) {
      Object localObject1 = paramNetMsgFiltered.filterArg();
      if (localObject1 == null) return;
      IntHashtable localIntHashtable = null;
      Object localObject2;
      if ((localObject1 instanceof NetAircraft)) {
        localObject2 = (NetAircraft)localObject1;
        localIntHashtable = ((NetAircraft.AircraftNet)((NetAircraft)localObject2).jdField_net_of_type_ComMaddoxIl2EngineActorNet).filterTable;
      }
      else if ((localObject1 instanceof NetGunner)) {
        localObject2 = (NetGunner)localObject1;
        localIntHashtable = ((NetGunner)localObject2).getFilterTable();
      } else {
        return;
      }
      localIntHashtable.put(paramNetChannel.id(), (int)(Time.current() & 0x7FFFFFF));
    }

    public boolean filterEnableAdd(NetChannel paramNetChannel, NetFilter paramNetFilter) {
      return true;
    }
  }

  static class SPAWN
    implements NetSpawn
  {
    public void netSpawn(int paramInt, NetMsgInput paramNetMsgInput)
    {
      try
      {
        String str = paramNetMsgInput.read255();
        int i = paramNetMsgInput.readUnsignedByte();
        int j = paramNetMsgInput.readUnsignedByte();
        if (j == 255)
          j = -1;
        int k = paramNetMsgInput.readUnsignedShort();
        boolean bool = paramNetMsgInput.readBoolean();
        NetHost[] arrayOfNetHost = null;
        int m = paramNetMsgInput.available() / NetMsgInput.netObjReferenceLen();
        if (m > 0) {
          arrayOfNetHost = new NetHost[m];
          for (int n = 0; n < m; n++)
            arrayOfNetHost[n] = ((NetHost)paramNetMsgInput.readNetObj());
        } else {
          k = paramNetMsgInput.channel().id();
        }
        NetUser localNetUser = new NetUser(paramNetMsgInput.channel(), paramInt, str, arrayOfNetHost);
        NetUser.access$402(localNetUser, i);
        NetUser.access$502(localNetUser, j);
        NetUser.access$602(localNetUser, k);
        NetUser.access$702(localNetUser, bool);
        NetUser.access$802(localNetUser, false);
        if ((Main.cur().netServerParams != null) && (Main.cur().netServerParams.isMaster()) && (Main.cur().netServerParams.isCoop()))
        {
          localNetUser.requestPlace(-1);
        }

        if ((m == 0) && ((paramNetMsgInput.channel() instanceof NetChannelInStream)))
          NetUser.access$702(localNetUser, true);
      } catch (Exception localException) {
        NetUser.access$900(localException);
      }
    }
  }
}