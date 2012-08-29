package com.maddox.il2.net;

import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.RenderContext;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.order.OrdersTree;
import com.maddox.il2.gui.GUINetServerCMission;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetGunner;
import com.maddox.rts.CLASS;
import com.maddox.rts.CfgInt;
import com.maddox.rts.Finger;
import com.maddox.rts.IniFile;
import com.maddox.rts.MainWin32;
import com.maddox.rts.Message;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetChannelOutStream;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetHost;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.NetUpdate;
import com.maddox.rts.Property;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.sound.AudioDevice;
import com.maddox.util.HashMapExt;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

public class NetServerParams extends NetObj
  implements NetUpdate
{
  public static final int MIN_SYNC_DELTA = 4000;
  public static final int MAX_SYNC_DELTA = 32000;
  public static final int MODE_DOGFIGHT = 0;
  public static final int MODE_COOP = 1;
  public static final int MODE_SINGLE = 2;
  public static final int MODE_MASK = 7;
  public static final int TYPE_LOCAL = 0;
  public static final int TYPE_BBGC = 16;
  public static final int TYPE_BBGC_DEMO = 32;
  public static final int TYPE_GAMESPY = 32;
  public static final int TYPE_USGS = 48;
  public static final int TYPE_MASK = 48;
  public static final int TYPE_SHIFT = 4;
  public static final int PROTECTED = 128;
  public static final int DEDICATED = 8;
  public static final int SHOW_SPEED_BAR = 4096;
  public static final int EXTRA_OCCLUSION = 8192;
  public static final int MSG_UPDATE = 0;
  public static final int MSG_COOP_ENTER = 1;
  public static final int MSG_COOP_ENTER_ASK = 2;
  public static final int MSG_SYNC = 3;
  public static final int MSG_SYNC_ASK = 4;
  public static final int MSG_SYNC_START = 5;
  public static final int MSG_TIME = 6;
  public static final int MSG_MDS_TIME = 7;
  public static final int MSG_CHECK_BEGIN = 8;
  public static final int MSG_CHECK_FIRST = 8;
  public static final int MSG_CHECK_SECOND = 9;
  public static final int MSG_CHECK_STEP = 10;
  public static final int MSG_CHECK_END = 10;
  private String serverName;
  public String serverDescription;
  private String serverPassword;
  private NetHost host;
  private int flags = 4096;
  private int difficulty;
  private int maxUsers;
  private int autoLogDetail = 3;
  private boolean eventlogHouse = false;
  private int eventlogClient = -1;
  public boolean bNGEN = false;
  public long timeoutNGEN = 0L;
  public boolean bLandedNGEN = false;

  private float farMaxLagTime = 10.0F;
  private float nearMaxLagTime = 2.0F;
  private float cheaterWarningDelay = 10.0F;
  private int cheaterWarningNum = 3;
  private NetMsgFiltered outMsgF;
  private int syncStamp = 0;
  private long syncTime;
  private long syncDelta;
  private boolean bCheckStartSync = false;
  private boolean bDoSync = false;

  private long serverDeltaTime = 0L;
  private long serverDeltaTime_lastUpdate = 0L;

  private long serverClockOffset0 = 0L;
  private long lastServerTime = 0L;

  public boolean netStat_DisableStatistics = false;
  public boolean netStat_ShowPilotNumber = true;
  public boolean netStat_ShowPilotPing = true;
  public boolean netStat_ShowPilotName = true;
  public boolean netStat_ShowPilotScore = true;
  public boolean netStat_ShowPilotArmy = true;
  public boolean netStat_ShowPilotACDesignation = true;
  public boolean netStat_ShowPilotACType = true;

  public int reflyKIADelay = 0;
  public int maxAllowedKIA = -1;
  public float reflyKIADelayMultiplier = 0.0F;
  public boolean reflyDisabled = false;

  public boolean allowMorseAsText = true;

  public boolean filterUserNames = false;

  private static long previousTime = 0L;
  private static final int ZUTI_RESYNC_INTERVAL = 2000;
  private long previousDeltaTime = 0L;
  private int syncCounter = 0;
  private static boolean inSync = false;

  long _lastCheckMaxLag = -1L;

  private int checkRuntime = 0;
  private long checkTimeUpdate = 0L;
  private HashMapExt checkUsers = new HashMapExt();
  private int checkPublicKey = 0;
  private int checkKey = 0;

  private int checkSecond2 = 0;

  public static boolean isSynched()
  {
    if (Main.cur().netServerParams.isMaster())
      return true;
    return inSync;
  }

  public static long getServerTime()
  {
    long l1;
    if ((Main.cur() != null) && (Main.cur().netServerParams != null) && (Main.cur().netServerParams.isDogfight()))
    {
      if ((NetMissionTrack.isPlaying()) || ((Main.cur().netServerParams.isMirror()) && (!Time.isPaused())))
      {
        l1 = Time.current() + Main.cur().netServerParams.serverDeltaTime;
        if (l1 > previousTime)
        {
          previousTime = l1;
          return l1;
        }
        return previousTime;
      }

    }

    if (NetMissionTrack.isPlaying()) {
      if ((Main.cur() != null) && (Main.cur().netServerParams != null) && (Main.cur().netServerParams.isCoop()))
      {
        l1 = Time.current() - Main.cur().netServerParams.serverDeltaTime;
        if (l1 < 0L) l1 = 0L;
        if (l1 > Main.cur().netServerParams.lastServerTime)
          Main.cur().netServerParams.lastServerTime = l1;
        return Main.cur().netServerParams.lastServerTime;
      }
      return Time.current();
    }

    if ((Main.cur() != null) && (Main.cur().netServerParams != null) && (Main.cur().netServerParams.isCoop()) && (Main.cur().netServerParams.isMirror()) && (!Time.isPaused()) && (Main.cur().netServerParams.serverClockOffset0 != 0L))
    {
      l1 = Main.cur().netServerParams.masterChannel().remoteClockOffset();
      long l2 = Time.current() - (l1 - Main.cur().netServerParams.serverClockOffset0);
      if (l2 < 0L) l2 = 0L;
      if (l2 > Main.cur().netServerParams.lastServerTime)
        Main.cur().netServerParams.lastServerTime = l2;
      return Main.cur().netServerParams.lastServerTime;
    }
    return Time.current();
  }

  public NetHost host() {
    return this.host;
  }
  public boolean isDedicated() { return (this.flags & 0x8) != 0; } 
  public boolean isBBGC() {
    return (this.flags & 0x30) == 16;
  }
  public boolean isGAMESPY() { return (this.flags & 0x30) == 32; } 
  public boolean isUSGS() {
    return (this.flags & 0x30) == 48; } 
  public int getType() { return this.flags & 0x30; } 
  public void setType(int paramInt) {
    this.flags = (this.flags & 0xFFFFFFCF | paramInt & 0x30);
  }
  public boolean isDogfight() {
    return (this.flags & 0x7) == 0; } 
  public boolean isCoop() { return (this.flags & 0x7) == 1; } 
  public boolean isSingle() { return (this.flags & 0x7) == 2; } 
  public void setMode(int paramInt) {
    if (!isMaster())
      return;
    this.flags = (this.flags & 0xFFFFFFF8 | paramInt & 0x7);
    mirrorsUpdate();
  }
  public boolean isShowSpeedBar() {
    return (this.flags & 0x1000) != 0;
  }
  public void setShowSpeedBar(boolean paramBoolean) { if (!isMaster()) return;
    if (paramBoolean == isShowSpeedBar()) return;
    if (paramBoolean) this.flags |= 4096; else
      this.flags &= -4097;
    mirrorsUpdate(); }

  public boolean isExtraOcclusion() {
    return (this.flags & 0x2000) != 0;
  }
  public void setExtraOcclusion(boolean paramBoolean) { if (!isMaster()) return;
    if (paramBoolean == isExtraOcclusion()) return;
    if (paramBoolean) this.flags |= 8192; else
      this.flags &= -8193;
    synkExtraOcclusion();
    mirrorsUpdate(); }

  public void synkExtraOcclusion() {
    if (isDedicated()) return;
    AudioDevice.setExtraOcclusion(isExtraOcclusion());
  }
  public int autoLogDetail() {
    return this.autoLogDetail;
  }
  public boolean eventlogHouse() {
    return (this.eventlogHouse) && (isMaster());
  }
  public int eventlogClient() {
    return this.eventlogClient;
  }
  public float farMaxLagTime() {
    return this.farMaxLagTime; } 
  public float nearMaxLagTime() { return this.nearMaxLagTime; } 
  public float cheaterWarningDelay() { return this.cheaterWarningDelay; } 
  public int cheaterWarningNum() { return this.cheaterWarningNum; } 
  public int getDifficulty() {
    return this.difficulty;
  }
  public void setDifficulty(int paramInt) { if (!isMaster())
      return;
    this.difficulty = paramInt;
    World.cur().diffCur.set(this.difficulty);
    setClouds();
    mirrorsUpdate(); }

  public String serverName() {
    return this.serverName;
  }
  public void setServerName(String paramString) { if ((USGS.isUsed()) && (isMaster())) {
      this.serverName = USGS.room;
      if (this.serverName == null)
        this.serverName = "Server";
      return;
    }

    if (Main.cur().netGameSpy != null) {
      this.serverName = Main.cur().netGameSpy.roomName;
      return;
    }

    this.serverName = paramString;
    mirrorsUpdate(); }

  public boolean isProtected() {
    return (this.flags & 0x80) != 0; } 
  public String getPassword() { return this.serverPassword; } 
  public void setPassword(String paramString) {
    this.serverPassword = paramString;
    if (this.serverPassword != null) this.flags |= 128; else
      this.flags &= -129;
    mirrorsUpdate();
  }

  private void setClouds() {
    if (!Config.isUSE_RENDER()) return;
    if (World.cur().diffCur.Clouds) {
      Main3D.cur3D().bDrawClouds = true;
      if (RenderContext.cfgSky.get() == 0) {
        RenderContext.cfgSky.set(1);
        RenderContext.cfgSky.apply();
        RenderContext.cfgSky.reset();
      }
    } else {
      Main3D.cur3D().bDrawClouds = false;
    }
  }

  public int getMaxUsers() {
    return this.maxUsers;
  }
  public void setMaxUsers(int paramInt) { if (!isMaster())
      return;
    this.maxUsers = paramInt;
    mirrorsUpdate(); }

  private void mirrorsUpdate()
  {
    USGSupdate();

    if (Main.cur().netGameSpy != null) {
      Main.cur().netGameSpy.sendStatechanged();
    }
    if (!isMirrored())
      return;
    try {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(0);
      localNetMsgGuaranted.writeInt(this.flags);
      localNetMsgGuaranted.writeInt(this.difficulty);
      localNetMsgGuaranted.writeByte(this.maxUsers);
      localNetMsgGuaranted.write255(this.serverName);
      post(localNetMsgGuaranted); } catch (Exception localException) {
      printDebug(localException);
    }
  }

  public void USGSupdate() {
    if (!isMaster()) return;
    if (!USGS.isUsed()) return;
    USGS.update();
  }

  public void doMissionCoopEnter()
  {
    Object localObject;
    if (isMaster()) {
      localObject = NetEnv.hosts();
      if (((List)localObject).size() == 0) {
        prepareHidenAircraft();
        startCoopGame();
        return;
      }
      for (int i = 0; i < ((List)localObject).size(); i++)
        ((NetUser)((List)localObject).get(i)).syncCoopStart = -1;
      this.bCheckStartSync = true;
      this.syncTime = (Time.currentReal() + 32000L);
    }
    else if (Main.cur().netMissionListener != null) {
      Main.cur().netMissionListener.netMissionCoopEnter();
    }
    if (isMirrored())
      try {
        localObject = new NetMsgGuaranted();
        ((NetMsgGuaranted)localObject).writeByte(1);
        ((NetMsgGuaranted)localObject).writeByte(this.syncStamp);
        post((NetMsgGuaranted)localObject); } catch (Exception localException1) {
        printDebug(localException1);
      }
    if (!isMaster())
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(2);
        localNetMsgGuaranted.writeNetObj(NetEnv.host());
        postTo(masterChannel(), localNetMsgGuaranted); } catch (Exception localException2) {
        printDebug(localException2);
      }
  }

  public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
    paramNetMsgInput.reset();
    int i = paramNetMsgInput.readByte();
    int n;
    Object localObject;
    switch (i) {
    case 0:
      int j = paramNetMsgInput.readInt();
      int k = paramNetMsgInput.readInt();
      int m = paramNetMsgInput.readByte();
      this.serverName = paramNetMsgInput.read255();
      this.flags = j;
      this.difficulty = k;
      this.maxUsers = m;
      World.cur().diffCur.set(this.difficulty);
      setClouds();
      synkExtraOcclusion();
      if (!isMirrored()) break;
      post(new NetMsgGuaranted(paramNetMsgInput, 0)); break;
    case 1:
      this.syncStamp = paramNetMsgInput.readUnsignedByte();
      doMissionCoopEnter();
      break;
    case 2:
      if (isMaster()) {
        NetUser localNetUser1 = (NetUser)paramNetMsgInput.readNetObj();
        if (localNetUser1 != null)
          localNetUser1.syncCoopStart = this.syncStamp;
      } else {
        postTo(masterChannel(), new NetMsgGuaranted(paramNetMsgInput, 1));
      }
      break;
    case 3:
      n = paramNetMsgInput.readUnsignedByte();
      int i1 = paramNetMsgInput.readInt();
      if (this.syncStamp != n) {
        NetMsgGuaranted localNetMsgGuaranted2 = new NetMsgGuaranted();
        localNetMsgGuaranted2.writeByte(4);
        localNetMsgGuaranted2.writeByte(n);
        localNetMsgGuaranted2.writeNetObj(NetEnv.host());
        postTo(masterChannel(), localNetMsgGuaranted2);
        this.syncStamp = n;
        this.syncTime = (i1 + Message.currentRealTime());
      } else {
        long l2 = i1 + Message.currentRealTime();
        if (this.syncTime > l2)
          this.syncTime = l2;
      }
      if (isMirrored()) {
        this.outMsgF.unLockAndClear();
        this.outMsgF.writeByte(3);
        this.outMsgF.writeByte(this.syncStamp);
        this.outMsgF.writeInt((int)(this.syncTime - Time.currentReal()));
        postReal(Time.currentReal(), this.outMsgF);
      }

      break;
    case 4:
      if (isMaster()) {
        n = paramNetMsgInput.readUnsignedByte();
        NetUser localNetUser2 = (NetUser)paramNetMsgInput.readNetObj();
        if ((localNetUser2 != null) && (n == this.syncStamp)) {
          localNetUser2.syncCoopStart = this.syncStamp;
          localObject = NetEnv.hosts();
          for (int i2 = 0; i2 < ((List)localObject).size(); i2++)
            if (((NetUser)((List)localObject).get(i2)).syncCoopStart != this.syncStamp)
              return true;
          this.bDoSync = false;
          doStartCoopGame();
        }
      } else {
        postTo(masterChannel(), new NetMsgGuaranted(paramNetMsgInput, 1));
      }
      break;
    case 5:
      doStartCoopGame();
      break;
    case 6:
      this.serverDeltaTime = paramNetMsgInput.readLong();
      if (!NetMissionTrack.isRecording()) break;
      try {
        NetMsgGuaranted localNetMsgGuaranted1 = new NetMsgGuaranted();
        localNetMsgGuaranted1.writeByte(6);
        localNetMsgGuaranted1.writeLong(this.serverDeltaTime);
        postTo(NetMissionTrack.netChannelOut(), localNetMsgGuaranted1); } catch (Exception localException1) {
        printDebug(localException1);
      }

    case 7:
      if ((inSync) && (!NetMissionTrack.isRecording())) {
        return true;
      }
      this.serverDeltaTime = (((NetUser)NetEnv.host()).ping / 2 + paramNetMsgInput.readLong() - Time.current());

      long l1 = Math.abs(this.previousDeltaTime - this.serverDeltaTime);

      if (l1 < 500L)
      {
        this.syncCounter += 1;
        if ((this.syncCounter > 4) && (l1 < this.syncCounter * 2))
        {
          inSync = true;
        }
      }
      else {
        this.syncCounter = 0;
      }this.previousDeltaTime = this.serverDeltaTime;

      if (!NetMissionTrack.isRecording())
        break;
      try
      {
        localObject = new NetMsgGuaranted();
        ((NetMsgGuaranted)localObject).writeByte(7);
        ((NetMsgGuaranted)localObject).writeLong(this.serverDeltaTime);
        postTo(NetMissionTrack.netChannelOut(), (NetMsgGuaranted)localObject);
      }
      catch (Exception localException2)
      {
        NetObj.printDebug(localException2);
      }

    default:
      return checkInput(i, paramNetMsgInput);
    }
    return true;
  }

  public void netUpdate()
  {
    long l1;
    if (!NetMissionTrack.isPlaying())
    {
      if ((isMaster()) && (isDogfight()))
      {
        l1 = Time.current();
        if (l1 > this.serverDeltaTime_lastUpdate + 2000L)
        {
          this.serverDeltaTime_lastUpdate = l1;
          try
          {
            NetMsgGuaranted localNetMsgGuaranted2 = new NetMsgGuaranted();
            localNetMsgGuaranted2.writeByte(7);
            localNetMsgGuaranted2.writeLong(this.serverDeltaTime_lastUpdate);
            post(localNetMsgGuaranted2);
          }
          catch (IOException localIOException)
          {
            NetObj.printDebug(localIOException);
          }
        }

      }

      doCheckMaxLag();
      if (isMaster()) {
        checkUpdate();
      }
    }
    if ((isMirror()) && (isCoop()) && (!Time.isPaused()) && (NetMissionTrack.isRecording()) && (!NetMissionTrack.isPlaying()))
    {
      l1 = Time.current();
      if (l1 > this.serverDeltaTime_lastUpdate + 3000L) {
        this.serverDeltaTime_lastUpdate = l1;
        try {
          NetMsgGuaranted localNetMsgGuaranted3 = new NetMsgGuaranted();
          localNetMsgGuaranted3.writeByte(6);
          long l2 = Main.cur().netServerParams.masterChannel().remoteClockOffset();
          long l3 = l2 - Main.cur().netServerParams.serverClockOffset0;
          localNetMsgGuaranted3.writeLong(l3);
          postTo(NetMissionTrack.netChannelOut(), localNetMsgGuaranted3); } catch (Exception localException3) {
          printDebug(localException3);
        }
      }

    }

    if ((isMirror()) && (isDogfight()) && (!Time.isPaused()) && (NetMissionTrack.isRecording()) && (!NetMissionTrack.isPlaying()))
    {
      try
      {
        NetMsgGuaranted localNetMsgGuaranted1 = new NetMsgGuaranted();
        localNetMsgGuaranted1.writeByte(7);
        localNetMsgGuaranted1.writeLong(Time.current() + Main.cur().netServerParams.serverDeltaTime);
        postTo(NetMissionTrack.netChannelOut(), localNetMsgGuaranted1);
      }
      catch (Exception localException1)
      {
        NetObj.printDebug(localException1);
      }

    }

    if ((!this.bDoSync) && (!this.bCheckStartSync)) return;
    if (isMaster())
    {
      List localList;
      int i;
      NetUser localNetUser;
      if (this.bCheckStartSync) {
        localList = NetEnv.hosts();
        if (localList.size() == 0) {
          prepareHidenAircraft();
          startCoopGame();
          this.bCheckStartSync = false;
          return;
        }
        if (Time.currentReal() > this.syncTime)
          for (i = 0; i < localList.size(); i++) {
            localNetUser = (NetUser)localList.get(i);
            if (localNetUser.syncCoopStart != this.syncStamp)
              ((NetUser)NetEnv.host()).kick(localNetUser);
          }
        else {
          for (i = 0; i < localList.size(); i++) {
            localNetUser = (NetUser)localList.get(i);
            if (localNetUser.syncCoopStart != this.syncStamp) {
              return;
            }
          }
        }
        this.syncStamp = (this.syncStamp + 1 & 0xFF);
        this.syncDelta = 4000L;
        this.syncTime = (Time.currentReal() + this.syncDelta);
        this.bCheckStartSync = false;
        this.bDoSync = true;
      }
      if (NetEnv.hosts().size() == 0) {
        prepareHidenAircraft();
        startCoopGame();
        this.bDoSync = false;
        return;
      }
      if (Time.currentReal() > this.syncTime - this.syncDelta / 2L)
        if (this.syncDelta < 32000L)
        {
          this.syncStamp = (this.syncStamp + 1 & 0xFF);
          this.syncDelta *= 2L;
          this.syncTime = (Time.currentReal() + this.syncDelta);
        }
        else {
          localList = NetEnv.hosts();
          for (i = 0; i < localList.size(); i++) {
            localNetUser = (NetUser)localList.get(i);
            if (localNetUser.syncCoopStart != this.syncStamp)
              ((NetUser)NetEnv.host()).kick(localNetUser);
          }
          this.bDoSync = false;
          doStartCoopGame();
          return;
        }
      try
      {
        this.outMsgF.unLockAndClear();
        this.outMsgF.writeByte(3);
        this.outMsgF.writeByte(this.syncStamp);
        this.outMsgF.writeInt((int)(this.syncTime - Time.currentReal()));
        postReal(Time.currentReal(), this.outMsgF); } catch (Exception localException2) {
        printDebug(localException2);
      }
    }
  }

  public void msgNetDelChannel(NetChannel paramNetChannel) {
    netUpdate();
  }

  private void doStartCoopGame() {
    if (isMirrored()) {
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(5);
        post(localNetMsgGuaranted); } catch (Exception localException) {
        printDebug(localException);
      }
    }

    HUD.logCoopTimeStart(this.syncTime);

    new MsgAction(64, this.syncTime, this) {
      public void doAction(Object paramObject) {
        if (paramObject != Main.cur().netServerParams) return;
        NetServerParams.this.prepareHidenAircraft();
        NetServerParams.this.startCoopGame();
      }
    };
  }

  private void startCoopGame() {
    prepareOrdersTree();
    Mission.doMissionStarting();
    Time.setPause(false);
    AudioDevice.soundsOn();

    if ((isMaster()) && (this.bNGEN)) {
      if (this.timeoutNGEN > 0L)
        startTimeoutNGEN(this.timeoutNGEN);
      if (this.bLandedNGEN)
        startLandedNGEN(2000L);
    } else {
      if (masterChannel() != null)
        this.serverClockOffset0 = masterChannel().remoteClockOffset();
      else
        this.serverClockOffset0 = 0L;
      this.lastServerTime = 0L;
      this.serverDeltaTime_lastUpdate = -100000L;
    }
  }

  private void startTimeoutNGEN(long paramLong) {
    new MsgAction(0, Time.current() + paramLong, Mission.cur()) {
      public void doAction(Object paramObject) {
        if (Mission.cur() != paramObject) return;
        if (!Mission.isPlaying()) return;
        if (Main.state().id() != 49)
          NetServerParams.this.startTimeoutNGEN(500L);
        else
          ((GUINetServerCMission)Main.state()).tryExit(); 
      } } ;
  }

  private void startLandedNGEN(long paramLong) {
    new MsgAction(0, Time.current() + paramLong, Mission.cur()) {
      public void doAction(Object paramObject) {
        if (Mission.cur() != paramObject) return;
        if (!Mission.isPlaying()) return;
        if (Main.state().id() != 49) {
          NetServerParams.this.startLandedNGEN(2000L);
        } else {
          int i = 1;
          Map.Entry localEntry = Engine.name2Actor().nextEntry(null);
          while (localEntry != null) {
            Actor localActor = (Actor)localEntry.getValue();
            if (((localActor instanceof Aircraft)) && (Actor.isAlive(localActor))) {
              Aircraft localAircraft = (Aircraft)localActor;
              if (localAircraft.isNetPlayer()) {
                if (!localAircraft.FM.isWasAirborne()) {
                  i = 0;
                  break;
                }
                if (!localAircraft.FM.isStationedOnGround()) {
                  i = 0;
                  break;
                }
              }
            }
            localEntry = Engine.name2Actor().nextEntry(localEntry);
          }
          if (i != 0)
            ((GUINetServerCMission)Main.state()).tryExit();
          else
            NetServerParams.this.startLandedNGEN(2000L);
        }
      } } ;
  }

  public void prepareHidenAircraft() {
    ArrayList localArrayList = new ArrayList();
    Map.Entry localEntry = Engine.name2Actor().nextEntry(null);
    while (localEntry != null) {
      Actor localActor1 = (Actor)localEntry.getValue();
      if (((localActor1 instanceof Aircraft)) && 
        (localActor1.name().charAt(0) == ' ')) {
        localArrayList.add(localActor1);
      }
      localEntry = Engine.name2Actor().nextEntry(localEntry);
    }
    Aircraft localAircraft;
    for (int i = 0; i < localArrayList.size(); i++) {
      localAircraft = (Aircraft)localArrayList.get(i);
      String str = localAircraft.name().substring(1);
      if (Actor.getByName(str) != null) {
        localAircraft.destroy();
      } else {
        localAircraft.setName(str);
        localAircraft.collide(true);
        localAircraft.restoreLinksInCoopWing();
      }
    }
    if (World.isPlayerGunner()) {
      World.getPlayerGunner().getAircraft();
    }

    if (isMaster()) return;

    localEntry = Engine.name2Actor().nextEntry(null);
    while (localEntry != null) {
      Actor localActor2 = (Actor)localEntry.getValue();
      if ((localActor2 instanceof Aircraft)) {
        localAircraft = (Aircraft)localActor2;
        if ((!localAircraft.isNetPlayer()) && (!localAircraft.isNet()))
          localArrayList.add(localActor2);
      }
      localEntry = Engine.name2Actor().nextEntry(localEntry);
    }
    for (int j = 0; j < localArrayList.size(); j++) {
      localAircraft = (Aircraft)localArrayList.get(j);
      localAircraft.destroy();
    }
  }

  private void prepareOrdersTree() {
    if (World.isPlayerGunner())
      World.getPlayerGunner().getAircraft();
    else {
      ((Main3D)Main.cur()).ordersTree.netMissionLoaded(World.getPlayerAircraft());
    }
    if (isMirror()) return;
    List localList = NetEnv.hosts();
    for (int i = 0; i < localList.size(); i++) {
      NetUser localNetUser = (NetUser)localList.get(i);
      localNetUser.ordersTree = new OrdersTree(false);
      localNetUser.ordersTree.netMissionLoaded(localNetUser.findAircraft());
    }
  }

  private void doCheckMaxLag()
  {
    long l = Time.real();
    if ((this._lastCheckMaxLag > 0L) && (l - this._lastCheckMaxLag < 1000L))
      return;
    this._lastCheckMaxLag = l;
    if (!Mission.isPlaying()) return;
    Object localObject;
    if (isMaster()) {
      localObject = Engine.targets();
      int i = ((List)localObject).size();
      for (int j = 0; j < i; j++) {
        Actor localActor = (Actor)((List)localObject).get(j);
        if ((!(localActor instanceof Aircraft)) || 
          (!Actor.isAlive(localActor)) || 
          (localActor.net == null) || (localActor.net.isMaster())) continue;
        NetUser localNetUser = ((Aircraft)localActor).netUser();
        if (localNetUser != null) {
          if (localNetUser.netMaxLag == null)
            localNetUser.netMaxLag = new NetMaxLag();
          localNetUser.netMaxLag.doServerCheck((Aircraft)localActor);
        }
      }
    } else {
      localObject = (NetUser)NetEnv.host();
      if (((NetUser)localObject).netMaxLag == null)
        ((NetUser)localObject).netMaxLag = new NetMaxLag();
      ((NetUser)localObject).netMaxLag.doClientCheck();
    }
  }

  public void destroy() {
    super.destroy();
    this.bCheckStartSync = false;
    this.bDoSync = false;
    Main.cur().netServerParams = null;
  }

  public NetServerParams() {
    super(null);
    this.host = NetEnv.host();
    this.serverName = this.host.shortName();
    Main.cur().netServerParams = this;
    this.outMsgF = new NetMsgFiltered();
    try { this.outMsgF.setIncludeTime(true); } catch (Exception localException) {
    }if (!Config.isUSE_RENDER()) {
      this.flags |= 8;
    }
    synkExtraOcclusion();
    this.autoLogDetail = Config.cur.ini.get("chat", "autoLogDetail", this.autoLogDetail, 0, 3);
    this.nearMaxLagTime = Config.cur.ini.get("MaxLag", "nearMaxLagTime", this.nearMaxLagTime, 0.1F, 30.0F);
    this.farMaxLagTime = Config.cur.ini.get("MaxLag", "farMaxLagTime", this.farMaxLagTime, this.nearMaxLagTime, 30.0F);
    this.cheaterWarningDelay = Config.cur.ini.get("MaxLag", "cheaterWarningDelay", this.cheaterWarningDelay, 1.0F, 30.0F);
    this.cheaterWarningNum = Config.cur.ini.get("MaxLag", "cheaterWarningNum", this.cheaterWarningNum);
    this.checkRuntime = Config.cur.ini.get("NET", "checkRuntime", 0, 0, 2);
    this.eventlogHouse = Config.cur.ini.get("game", "eventlogHouse", false);
    this.eventlogClient = Config.cur.ini.get("game", "eventlogClient", -1);

    this.netStat_DisableStatistics = Config.cur.ini.get("NET", "disableNetStatStatistics", false);
    this.netStat_ShowPilotNumber = Config.cur.ini.get("NET", "showPilotNumber", true);
    this.netStat_ShowPilotPing = Config.cur.ini.get("NET", "showPilotPing", true);
    this.netStat_ShowPilotName = Config.cur.ini.get("NET", "showPilotName", true);
    this.netStat_ShowPilotScore = Config.cur.ini.get("NET", "showPilotScore", true);
    this.netStat_ShowPilotArmy = Config.cur.ini.get("NET", "showPilotArmy", true);
    this.netStat_ShowPilotACDesignation = Config.cur.ini.get("NET", "showPilotACDesignation", true);
    this.netStat_ShowPilotACType = Config.cur.ini.get("NET", "showPilotACType", true);
    this.filterUserNames = Config.cur.ini.get("NET", "filterUserNames", false);

    this.reflyKIADelay = Config.cur.ini.get("NET", "reflyKIADelay", 0);
    this.maxAllowedKIA = Config.cur.ini.get("NET", "maxAllowedKIA", -1);
    this.reflyKIADelayMultiplier = Config.cur.ini.get("NET", "reflyKIADelayMultiplier", 0.0F);
    this.reflyDisabled = Config.cur.ini.get("NET", "reflyDisabled", false);
    this.allowMorseAsText = Config.cur.ini.get("NET", "allowMorseAsText", true);
  }

  public NetServerParams(NetChannel paramNetChannel, int paramInt, NetHost paramNetHost) {
    super(null, paramNetChannel, paramInt);

    previousTime = 0L;
    this.serverDeltaTime_lastUpdate = 0L;
    inSync = false;
    this.syncCounter = 0;
    this.previousDeltaTime = 0L;

    this.host = paramNetHost;
    Main.cur().netServerParams = this;
    this.outMsgF = new NetMsgFiltered();
    try { this.outMsgF.setIncludeTime(true); } catch (Exception localException) {
    }
  }

  public NetMsgSpawn netReplicate(NetChannel paramNetChannel) throws IOException {
    NetMsgSpawn localNetMsgSpawn = new NetMsgSpawn(this);
    localNetMsgSpawn.writeNetObj(this.host);
    localNetMsgSpawn.writeInt(this.flags);
    localNetMsgSpawn.writeInt(this.difficulty);
    localNetMsgSpawn.writeByte(this.maxUsers);
    localNetMsgSpawn.write255(this.serverName);
    localNetMsgSpawn.writeByte(this.autoLogDetail);
    localNetMsgSpawn.writeFloat(this.farMaxLagTime);
    localNetMsgSpawn.writeFloat(this.nearMaxLagTime);
    localNetMsgSpawn.writeFloat(this.cheaterWarningDelay);
    localNetMsgSpawn.writeInt(this.cheaterWarningNum);

    localNetMsgSpawn.writeBoolean(this.netStat_DisableStatistics);
    localNetMsgSpawn.writeBoolean(this.netStat_ShowPilotNumber);
    localNetMsgSpawn.writeBoolean(this.netStat_ShowPilotPing);
    localNetMsgSpawn.writeBoolean(this.netStat_ShowPilotName);
    localNetMsgSpawn.writeBoolean(this.netStat_ShowPilotScore);
    localNetMsgSpawn.writeBoolean(this.netStat_ShowPilotArmy);
    localNetMsgSpawn.writeBoolean(this.netStat_ShowPilotACDesignation);
    localNetMsgSpawn.writeBoolean(this.netStat_ShowPilotACType);
    localNetMsgSpawn.writeBoolean(this.filterUserNames);

    localNetMsgSpawn.writeInt(this.reflyKIADelay);
    localNetMsgSpawn.writeInt(this.maxAllowedKIA);
    localNetMsgSpawn.writeFloat(this.reflyKIADelayMultiplier);
    localNetMsgSpawn.writeBoolean(this.reflyDisabled);
    localNetMsgSpawn.writeBoolean(this.allowMorseAsText);

    if (((paramNetChannel instanceof NetChannelOutStream)) && (isCoop())) {
      if (NetMissionTrack.isPlaying()) {
        localNetMsgSpawn.writeLong(this.serverDeltaTime);
      } else {
        long l1 = 0L;
        if (isMirror()) {
          long l2 = Main.cur().netServerParams.masterChannel().remoteClockOffset();
          l1 = l2 - Main.cur().netServerParams.serverClockOffset0;
        }
        localNetMsgSpawn.writeLong(l1);
      }
    }
    localNetMsgSpawn.writeInt(this.eventlogClient);
    return localNetMsgSpawn;
  }

  private int crcSFSFile(String paramString, int paramInt)
  {
    try
    {
      SFSInputStream localSFSInputStream = new SFSInputStream(Finger.LongFN(0L, paramString));
      paramInt = localSFSInputStream.crc(paramInt);
      localSFSInputStream.close();
    } catch (Exception localException) {
      printDebug(localException);
      return 0;
    }
    return paramInt;
  }

  private int checkFirst(int paramInt)
  {
    if (paramInt != 0) {
      long l = Finger.file(paramInt, MainWin32.GetCDDrive("jvm.dll"), -1);
      l = Finger.file(l, MainWin32.GetCDDrive("java.dll"), -1);
      l = Finger.file(l, MainWin32.GetCDDrive("net.dll"), -1);
      l = Finger.file(l, MainWin32.GetCDDrive("verify.dll"), -1);
      l = Finger.file(l, MainWin32.GetCDDrive("zip.dll"), -1);
      l = Finger.file(l, "lib/rt.jar", -1);
      ArrayList localArrayList = Main.cur().airClasses;
      for (int i = 0; i < localArrayList.size(); i++) {
        Class localClass = (Class)localArrayList.get(i);
        l = FlightModelMain.finger(l, Property.stringValue(localClass, "FlightModel", null));
      }
      l = Statics.getShipsFile().finger(l);
      l = Statics.getTechnicsFile().finger(l);
      l = Statics.getBuildingsFile().finger(l);

      paramInt = (int)l;
    }

    return paramInt;
  }

  private int checkSecond(int paramInt1, int paramInt2)
  {
    this.checkSecond2 = paramInt2;
    try {
      ClassLoader localClassLoader = getClass().getClassLoader();
      Field[] arrayOfField = ClassLoader.class.getDeclaredFields();
      Field localField = null;
      for (int i = 0; i < arrayOfField.length; i++) {
        if ("classes".equals(arrayOfField[i].getName())) {
          localField = arrayOfField[i];
          break;
        }
      }
      Vector localVector = (Vector)CLASS.field(localClassLoader, localField);
      for (int j = 0; j < localVector.size(); j++) {
        Class localClass = (Class)localVector.get(j);
        arrayOfField = localClass.getDeclaredFields();
        if (arrayOfField != null) {
          for (int k = 0; k < arrayOfField.length; k++)
            paramInt1 = Finger.incInt(paramInt1, arrayOfField[k].getName());
        }
        Method[] arrayOfMethod = localClass.getDeclaredMethods();
        if (arrayOfMethod != null) {
          for (int m = 0; m < arrayOfMethod.length; m++)
            paramInt1 = Finger.incInt(paramInt1, arrayOfMethod[m].getName());
        }
        if (this.checkSecond2 != 0)
          paramInt2 = CLASS.method(localClass, paramInt2);
      }
    }
    catch (Exception localException) {
    }
    this.checkSecond2 = paramInt2;

    return paramInt1;
  }

  private boolean CheckUserInput(int paramInt, NetMsgInput paramNetMsgInput)
    throws IOException
  {
    int i;
    int j;
    switch (paramInt) {
    case 8:
      i = checkFirst(paramNetMsgInput.readInt());
      break;
    case 9:
      i = checkSecond(paramNetMsgInput.readInt(), paramNetMsgInput.readInt());
      break;
    case 10:
      i = paramNetMsgInput.readInt();
      localObject = (NetUser)NetEnv.host();
      Aircraft localAircraft = ((NetUser)localObject).findAircraft();
      if (!Actor.isValid(localAircraft)) break;
      i = Finger.incInt(i, World.cur().diffCur.get());
      j = (int)localAircraft.finger(i) + SFSInputStream.oo; break;
    default:
      return false;
    }
    Object localObject = new NetMsgGuaranted();
    ((NetMsgGuaranted)localObject).writeByte(paramInt);
    ((NetMsgGuaranted)localObject).writeNetObj(NetEnv.host());
    ((NetMsgGuaranted)localObject).writeInt(j);
    if (paramInt == 9)
      ((NetMsgGuaranted)localObject).writeInt(this.checkSecond2);
    postTo(paramNetMsgInput.channel(), (NetMsgGuaranted)localObject);
    return true;
  }

  private boolean checkInput(int paramInt, NetMsgInput paramNetMsgInput)
    throws IOException
  {
    NetUser localNetUser = (NetUser)paramNetMsgInput.readNetObj();
    Object localObject;
    if (isMaster()) {
      localObject = (CheckUser)this.checkUsers.get(localNetUser);
      if (localObject != null)
        return ((CheckUser)localObject).checkInput(paramInt, paramNetMsgInput);
    } else {
      if (NetEnv.host() == localNetUser) {
        return CheckUserInput(paramInt, paramNetMsgInput);
      }

      paramNetMsgInput.reset();
      localObject = new NetMsgGuaranted();
      ((NetMsgGuaranted)localObject).writeMsg(paramNetMsgInput, 1);

      if (paramNetMsgInput.channel() == masterChannel())
        postTo(localNetUser.masterChannel(), (NetMsgGuaranted)localObject);
      else {
        postTo(masterChannel(), (NetMsgGuaranted)localObject);
      }
      return true;
    }

    return false;
  }

  private void checkUpdate() {
    if (isSingle())
      return;
    long l = Time.currentReal();
    if (l < this.checkTimeUpdate)
      return;
    this.checkTimeUpdate = (l + 1000L);

    List localList = NetEnv.hosts();
    int i = localList.size();
    Object localObject;
    for (int j = 0; j < i; j++) {
      localObject = (NetUser)localList.get(j);
      if (!this.checkUsers.containsKey(localObject)) {
        this.checkUsers.put(localObject, new CheckUser((NetUser)localObject));
      }
    }

    if (i != this.checkUsers.size()) {
      while (true) {
        j = 0;
        localObject = this.checkUsers.nextEntry(null);
        while (localObject != null) {
          NetUser localNetUser = (NetUser)((Map.Entry)localObject).getKey();
          if (localNetUser.isDestroyed()) {
            this.checkUsers.remove(localNetUser);
            j = 1;
            break;
          }
          localObject = this.checkUsers.nextEntry((Map.Entry)localObject);
        }
        if (j == 0) {
          break;
        }
      }
    }
    Map.Entry localEntry = this.checkUsers.nextEntry(null);
    while (localEntry != null) {
      if ((this.checkPublicKey == 0) && (this.checkRuntime >= 1))
        this.checkPublicKey = (int)(Math.random() * 4294967295.0D);
      localObject = (CheckUser)localEntry.getValue();
      ((CheckUser)localObject).checkUpdate(l);
      localEntry = this.checkUsers.nextEntry(localEntry);
    }
  }

  public void zutiResetServerTime()
  {
    this.serverDeltaTime = 0L;
    this.serverDeltaTime_lastUpdate = 0L;
    inSync = false;
    this.syncCounter = 0;
    this.previousDeltaTime = 0L;
    previousTime = 0L;
  }

  static
  {
    Spawn.add(NetServerParams.class, new SPAWN());
  }

  private class CheckUser
  {
    public NetUser user;
    public int state = 8;
    public long timeSended = 0L;
    public Class classAircraft = null;
    public int publicKey = 0;
    public int diff;

    public boolean checkInput(int paramInt, NetMsgInput paramNetMsgInput)
      throws IOException
    {
      int i = 0;
      Object localObject;
      switch (paramInt) {
      case 8:
        if (NetServerParams.this.checkKey == 0)
          NetServerParams.access$1502(NetServerParams.this, NetServerParams.this.checkFirst(NetServerParams.this.checkPublicKey));
        i = NetServerParams.this.checkKey == paramNetMsgInput.readInt() ? 1 : 0;
        if (i == 0) break;
        this.state += 1; break;
      case 9:
        int j = 0;
        if (NetServerParams.this.checkRuntime == 2)
          j = this.publicKey;
        i = paramNetMsgInput.readInt() == NetServerParams.this.checkSecond(this.publicKey, j) ? 1 : 0;
        if (i != 0)
          i = paramNetMsgInput.readInt() == NetServerParams.this.checkSecond2 ? 1 : 0;
        if (i == 0) break;
        this.state += 1; break;
      case 10:
        localObject = this.user.findAircraft();
        if (Actor.isValid((Actor)localObject)) {
          int k = Finger.incInt(this.publicKey, this.diff);
          i = paramNetMsgInput.readInt() == (int)((Aircraft)localObject).finger(k) + SFSInputStream.oo ? 1 : 0;
          if (i != 0)
            this.classAircraft = localObject.getClass();
          else
            this.classAircraft = null;
        } else {
          this.classAircraft = null;
          i = 1;
        }
        break;
      default:
        return false;
      }
      this.timeSended = 0L;
      if (i == 0) {
        NetChannel localNetChannel = paramNetMsgInput.channel();
        if (!localNetChannel.isDestroying()) {
          localObject = "Timeout ";
          localObject = (String)localObject + (paramInt - 8);
          localNetChannel.destroy((String)localObject);
        }
      }
      return true;
    }

    public void checkUpdate(long paramLong) {
      if (this.state > 10)
        return;
      Object localObject;
      if (this.timeSended != 0L) {
        if (paramLong < this.timeSended + 150000L)
          return;
        NetChannel localNetChannel = this.user.masterChannel();
        if (!localNetChannel.isDestroying()) {
          localObject = "Timeout .";
          localObject = (String)localObject + (this.state - 8);
          localNetChannel.destroy((String)localObject);
        }
        return;
      }
      try
      {
        int i = 0;
        switch (this.state) {
        case 8:
          i = NetServerParams.this.checkPublicKey;
          break;
        case 9:
          i = this.publicKey = (int)(Math.random() * 4294967295.0D);
          break;
        case 10:
          localObject = this.user.findAircraft();
          if ((!Actor.isValid((Actor)localObject)) || 
            (localObject.getClass().equals(this.classAircraft))) break;
          i = this.publicKey = (int)(Math.random() * 4294967295.0D);
          this.diff = World.cur().diffCur.get();
        }

        if (i != 0) {
          localObject = new NetMsgGuaranted();
          ((NetMsgGuaranted)localObject).writeByte(this.state);
          ((NetMsgGuaranted)localObject).writeNetObj(this.user);
          ((NetMsgGuaranted)localObject).writeInt(i);
          if (this.state == 9) {
            if (NetServerParams.this.checkRuntime == 2)
              ((NetMsgGuaranted)localObject).writeInt(i);
            else
              ((NetMsgGuaranted)localObject).writeInt(0);
          }
          NetServerParams.this.postTo(this.user.masterChannel(), (NetMsgGuaranted)localObject);
          this.timeSended = paramLong;
        }
      }
      catch (Exception localException)
      {
      }
    }

    public CheckUser(NetUser arg2)
    {
      Object localObject;
      this.user = localObject;
    }
  }

  static class SPAWN
    implements NetSpawn
  {
    public void netSpawn(int paramInt, NetMsgInput paramNetMsgInput)
    {
      try
      {
        NetObj localNetObj = paramNetMsgInput.readNetObj();
        int i = paramNetMsgInput.readInt();
        int j = paramNetMsgInput.readInt();
        int k = paramNetMsgInput.readByte();
        String str = paramNetMsgInput.read255();
        NetServerParams localNetServerParams = new NetServerParams(paramNetMsgInput.channel(), paramInt, (NetHost)localNetObj);
        NetServerParams.access$302(localNetServerParams, i);
        NetServerParams.access$402(localNetServerParams, j);
        NetServerParams.access$502(localNetServerParams, k);
        NetServerParams.access$602(localNetServerParams, str);
        NetServerParams.access$702(localNetServerParams, paramNetMsgInput.readByte());
        NetServerParams.access$802(localNetServerParams, paramNetMsgInput.readFloat());
        NetServerParams.access$902(localNetServerParams, paramNetMsgInput.readFloat());
        NetServerParams.access$1002(localNetServerParams, paramNetMsgInput.readFloat());
        NetServerParams.access$1102(localNetServerParams, paramNetMsgInput.readInt());

        if ((!NetMissionTrack.isPlaying()) || (NetMissionTrack.playingOriginalVersion() > 102)) {
          localNetServerParams.netStat_DisableStatistics = paramNetMsgInput.readBoolean();
          localNetServerParams.netStat_ShowPilotNumber = paramNetMsgInput.readBoolean();
          localNetServerParams.netStat_ShowPilotPing = paramNetMsgInput.readBoolean();
          localNetServerParams.netStat_ShowPilotName = paramNetMsgInput.readBoolean();
          localNetServerParams.netStat_ShowPilotScore = paramNetMsgInput.readBoolean();
          localNetServerParams.netStat_ShowPilotArmy = paramNetMsgInput.readBoolean();
          localNetServerParams.netStat_ShowPilotACDesignation = paramNetMsgInput.readBoolean();
          localNetServerParams.netStat_ShowPilotACType = paramNetMsgInput.readBoolean();
          localNetServerParams.filterUserNames = paramNetMsgInput.readBoolean();

          localNetServerParams.reflyKIADelay = paramNetMsgInput.readInt();
          localNetServerParams.maxAllowedKIA = paramNetMsgInput.readInt();
          localNetServerParams.reflyKIADelayMultiplier = paramNetMsgInput.readFloat();
          localNetServerParams.reflyDisabled = paramNetMsgInput.readBoolean();
          localNetServerParams.allowMorseAsText = paramNetMsgInput.readBoolean();
        }

        if ((paramNetMsgInput.channel() instanceof NetChannelInStream)) {
          NetServerParams.access$402(localNetServerParams, World.cur().diffCur.get());
          if (paramNetMsgInput.available() >= 8)
            NetServerParams.access$1202(localNetServerParams, paramNetMsgInput.readLong());
          else
            NetServerParams.access$1202(localNetServerParams, 0L);
        }
        else {
          World.cur().diffCur.set(j);
        }
        localNetServerParams.synkExtraOcclusion();
        if (paramNetMsgInput.available() >= 4)
          NetServerParams.access$1302(localNetServerParams, paramNetMsgInput.readInt());
        else
          NetServerParams.access$1302(localNetServerParams, -1);
      } catch (Exception localException) {
        NetServerParams.access$1400(localException);
      }
    }
  }
}