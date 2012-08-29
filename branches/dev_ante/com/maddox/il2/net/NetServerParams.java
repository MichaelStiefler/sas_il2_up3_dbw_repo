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
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
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
  public static final int MSG_CHECK_BEGIN = 8;
  public static final int MSG_CHECK_FIRST = 8;
  public static final int MSG_CHECK_SECOND = 9;
  public static final int MSG_CHECK_STEP = 10;
  public static final int MSG_CHECK_END = 10;
  private static long timeofday = -1L;
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
  long _lastCheckMaxLag = -1L;
  private int checkRuntime = 0;
  private long checkTimeUpdate = 0L;
  private HashMapExt checkUsers;
  private int checkPublicKey;
  private int checkKey;
  private int checkSecond2;
  static Class class$com$maddox$il2$net$NetServerParams;
  static Class class$java$lang$ClassLoader;
  private static String PATH_SEPARATOR = System.getProperty("file.separator");

  public static long getServerTime()
  {
    long l1;
    if (NetMissionTrack.isPlaying()) {
      if ((Main.cur() != null) && (Main.cur().netServerParams != null) && (Main.cur().netServerParams.isCoop()))
      {
        l1 = Time.current() - Main.cur().netServerParams.serverDeltaTime;

        if (l1 < 0L)
          l1 = 0L;
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

      if (l2 < 0L)
        l2 = 0L;
      if (l2 > Main.cur().netServerParams.lastServerTime)
        Main.cur().netServerParams.lastServerTime = l2;
      return Main.cur().netServerParams.lastServerTime;
    }
    return Time.current();
  }

  public NetHost host() {
    return this.host;
  }

  public boolean isDedicated() {
    return (this.flags & 0x8) != 0;
  }

  public boolean isBBGC() {
    return (this.flags & 0x30) == 16;
  }

  public boolean isGAMESPY() {
    return (this.flags & 0x30) == 32;
  }

  public boolean isUSGS() {
    return (this.flags & 0x30) == 48;
  }

  public int getType() {
    return this.flags & 0x30;
  }

  public void setType(int paramInt) {
    this.flags = (this.flags & 0xFFFFFFCF | paramInt & 0x30);
  }

  public boolean isDogfight() {
    return (this.flags & 0x7) == 0;
  }

  public boolean isCoop() {
    return (this.flags & 0x7) == 1;
  }

  public boolean isSingle() {
    return (this.flags & 0x7) == 2;
  }

  public void setMode(int paramInt) {
    if (isMaster()) {
      this.flags = (this.flags & 0xFFFFFFF8 | paramInt & 0x7);
      mirrorsUpdate();
    }
  }

  public boolean isShowSpeedBar() {
    return (this.flags & 0x1000) != 0;
  }

  public void setShowSpeedBar(boolean paramBoolean) {
    if ((isMaster()) && (paramBoolean != isShowSpeedBar())) {
      if (paramBoolean)
        this.flags |= 4096;
      else
        this.flags &= -4097;
      mirrorsUpdate();
    }
  }

  public boolean isExtraOcclusion() {
    return (this.flags & 0x2000) != 0;
  }

  public void setExtraOcclusion(boolean paramBoolean) {
    if ((isMaster()) && (paramBoolean != isExtraOcclusion())) {
      if (paramBoolean)
        this.flags |= 8192;
      else
        this.flags &= -8193;
      synkExtraOcclusion();
      mirrorsUpdate();
    }
  }

  public void synkExtraOcclusion() {
    if (!isDedicated())
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
    return this.farMaxLagTime;
  }

  public float nearMaxLagTime() {
    return this.nearMaxLagTime;
  }

  public float cheaterWarningDelay() {
    return this.cheaterWarningDelay;
  }

  public int cheaterWarningNum() {
    return this.cheaterWarningNum;
  }

  public int getDifficulty() {
    return this.difficulty;
  }

  public void setDifficulty(int paramInt) {
    if (isMaster()) {
      this.difficulty = paramInt;
      World.cur().diffCur.set(this.difficulty);
      setClouds();
      mirrorsUpdate();
    }
  }

  public String serverName() {
    return this.serverName;
  }

  public void setServerName(String paramString) {
    if ((USGS.isUsed()) && (isMaster())) {
      this.serverName = USGS.room;
      if (this.serverName == null)
        this.serverName = "Server";
    } else if (Main.cur().netGameSpy != null) {
      this.serverName = Main.cur().netGameSpy.roomName;
    } else {
      this.serverName = paramString;
      mirrorsUpdate();
    }
  }

  public boolean isProtected() {
    return (this.flags & 0x80) != 0;
  }

  public String getPassword() {
    return this.serverPassword;
  }

  public void setPassword(String paramString) {
    this.serverPassword = paramString;
    if (this.serverPassword != null)
      this.flags |= 128;
    else
      this.flags &= -129;
    mirrorsUpdate();
  }

  private void setClouds() {
    if (Config.isUSE_RENDER())
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

  public void setMaxUsers(int paramInt) {
    if (isMaster()) {
      this.maxUsers = paramInt;
      mirrorsUpdate();
    }
  }

  private void mirrorsUpdate() {
    USGSupdate();
    if (Main.cur().netGameSpy != null)
      Main.cur().netGameSpy.sendStatechanged();
    if (isMirrored())
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(0);
        localNetMsgGuaranted.writeInt(this.flags);
        localNetMsgGuaranted.writeInt(this.difficulty);
        localNetMsgGuaranted.writeByte(this.maxUsers);
        localNetMsgGuaranted.write255(this.serverName);
        post(localNetMsgGuaranted);
      } catch (Exception localException) {
        NetObj.printDebug(localException);
      }
  }

  public void USGSupdate()
  {
    if ((isMaster()) && (USGS.isUsed()))
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
    } else if (Main.cur().netMissionListener != null) {
      Main.cur().netMissionListener.netMissionCoopEnter();
    }if (isMirrored()) {
      try {
        localObject = new NetMsgGuaranted();
        ((NetMsgGuaranted)localObject).writeByte(1);
        ((NetMsgGuaranted)localObject).writeByte(this.syncStamp);
        post((NetMsgGuaranted)localObject);
      } catch (Exception localException1) {
        NetObj.printDebug(localException1);
      }
    }
    if (!isMaster())
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(2);
        localNetMsgGuaranted.writeNetObj(NetEnv.host());
        postTo(masterChannel(), localNetMsgGuaranted);
      } catch (Exception localException2) {
        NetObj.printDebug(localException2);
      }
  }

  public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException
  {
    paramNetMsgInput.reset();
    int i = paramNetMsgInput.readByte();
    int m;
    int k;
    switch (i) {
    case 0:
      int j = paramNetMsgInput.readInt();
      m = paramNetMsgInput.readInt();
      int n = paramNetMsgInput.readByte();
      this.serverName = paramNetMsgInput.read255();
      this.flags = j;
      this.difficulty = m;
      this.maxUsers = n;
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
        if (localNetUser1 == null) break;
        localNetUser1.syncCoopStart = this.syncStamp;
      } else {
        postTo(masterChannel(), new NetMsgGuaranted(paramNetMsgInput, 1));
      }
      break;
    case 3:
      k = paramNetMsgInput.readUnsignedByte();
      m = paramNetMsgInput.readInt();
      if (this.syncStamp != k) {
        NetMsgGuaranted localNetMsgGuaranted2 = new NetMsgGuaranted();
        localNetMsgGuaranted2.writeByte(4);
        localNetMsgGuaranted2.writeByte(k);
        localNetMsgGuaranted2.writeNetObj(NetEnv.host());
        postTo(masterChannel(), localNetMsgGuaranted2);
        this.syncStamp = k;
        this.syncTime = (m + Message.currentRealTime());
      } else {
        long l = m + Message.currentRealTime();
        if (this.syncTime > l)
          this.syncTime = l;
      }
      if (!isMirrored()) break;
      this.outMsgF.unLockAndClear();
      this.outMsgF.writeByte(3);
      this.outMsgF.writeByte(this.syncStamp);
      this.outMsgF.writeInt((int)(this.syncTime - Time.currentReal()));
      postReal(Time.currentReal(), this.outMsgF); break;
    case 4:
      if (isMaster()) {
        k = paramNetMsgInput.readUnsignedByte();
        NetUser localNetUser2 = (NetUser)paramNetMsgInput.readNetObj();
        if ((localNetUser2 == null) || (k != this.syncStamp)) break;
        localNetUser2.syncCoopStart = this.syncStamp;
        List localList = NetEnv.hosts();
        for (int i1 = 0; i1 < localList.size(); i1++)
          if (((NetUser)localList.get(i1)).syncCoopStart != this.syncStamp)
          {
            return true;
          }
        this.bDoSync = false;
        doStartCoopGame();
      }
      else {
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
        postTo(NetMissionTrack.netChannelOut(), localNetMsgGuaranted1);
      }
      catch (Exception localException) {
        NetObj.printDebug(localException);
      }

    default:
      return checkInput(i, paramNetMsgInput);
    }
    return true;
  }

  public void netUpdate() {
    if (!NetMissionTrack.isPlaying()) {
      doCheckMaxLag();
      if (isMaster())
        checkUpdate();
    }
    if ((isMirror()) && (isCoop()) && (!Time.isPaused()) && (NetMissionTrack.isRecording()) && (!NetMissionTrack.isPlaying()))
    {
      long l1 = Time.current();
      if (l1 > this.serverDeltaTime_lastUpdate + 3000L) {
        this.serverDeltaTime_lastUpdate = l1;
        try {
          NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
          localNetMsgGuaranted.writeByte(6);
          long l2 = Main.cur().netServerParams.masterChannel().remoteClockOffset();

          long l3 = l2 - Main.cur().netServerParams.serverClockOffset0;

          localNetMsgGuaranted.writeLong(l3);
          postTo(NetMissionTrack.netChannelOut(), localNetMsgGuaranted);
        }
        catch (Exception localException2) {
          NetObj.printDebug(localException2);
        }
      }
    }
    if (((this.bDoSync) || (this.bCheckStartSync)) && (isMaster()))
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
            if (localNetUser.syncCoopStart != this.syncStamp)
              return;
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
      } else {
        if (Time.currentReal() > this.syncTime - this.syncDelta / 2L)
          if (this.syncDelta < 32000L) {
            this.syncStamp = (this.syncStamp + 1 & 0xFF);
            this.syncDelta *= 2L;
            this.syncTime = (Time.currentReal() + this.syncDelta);
          } else {
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
          postReal(Time.currentReal(), this.outMsgF);
        } catch (Exception localException1) {
          NetObj.printDebug(localException1);
        }
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
        post(localNetMsgGuaranted);
      } catch (Exception localException) {
        NetObj.printDebug(localException);
      }
    }
    HUD.logCoopTimeStart(this.syncTime);
    new MsgAction(64, this.syncTime, this) {
      public void doAction(Object paramObject) {
        if (paramObject == Main.cur().netServerParams) {
          NetServerParams.this.prepareHidenAircraft();
          NetServerParams.this.startCoopGame();
        }
      } } ;
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
        if ((Mission.cur() == paramObject) && (Mission.isPlaying()))
          if (Main.state().id() != 49)
            NetServerParams.this.startTimeoutNGEN(500L);
          else
            ((GUINetServerCMission)Main.state()).tryExit();
      }
    };
  }

  private void startLandedNGEN(long paramLong) {
    new MsgAction(0, Time.current() + paramLong, Mission.cur()) {
      public void doAction(Object paramObject) {
        if ((Mission.cur() == paramObject) && (Mission.isPlaying()))
          if (Main.state().id() != 49) {
            NetServerParams.this.startLandedNGEN(2000L);
          } else {
            int i = 1;
            Map.Entry localEntry = Engine.name2Actor().nextEntry(null);

            while (localEntry != null)
            {
              Actor localActor = (Actor)localEntry.getValue();
              if (((localActor instanceof Aircraft)) && (Actor.isAlive(localActor)))
              {
                Aircraft localAircraft = (Aircraft)localActor;
                if (localAircraft.isNetPlayer()) {
                  if (!localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isWasAirborne()) {
                    i = 0;
                    break;
                  }
                  if (!localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isStationedOnGround()) {
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
      }
    };
  }

  public void prepareHidenAircraft() {
    ArrayList localArrayList = new ArrayList();
    Map.Entry localEntry = Engine.name2Actor().nextEntry(null);
    for (; localEntry != null; localEntry = Engine.name2Actor().nextEntry(localEntry)) {
      Actor localActor = (Actor)localEntry.getValue();
      if (((localActor instanceof Aircraft)) && (localActor.name().charAt(0) == ' '))
        localArrayList.add(localActor);
    }
    Object localObject1;
    Object localObject2;
    for (int i = 0; i < localArrayList.size(); i++) {
      localObject1 = (Aircraft)localArrayList.get(i);
      localObject2 = ((Aircraft)localObject1).name().substring(1);
      if (Actor.getByName((String)localObject2) != null) {
        ((Aircraft)localObject1).destroy();
      } else {
        ((Aircraft)localObject1).setName((String)localObject2);
        ((Aircraft)localObject1).collide(true);
        ((Aircraft)localObject1).restoreLinksInCoopWing();
      }
    }
    if (World.isPlayerGunner())
      World.getPlayerGunner().getAircraft();
    if (!isMaster()) {
      localObject1 = Engine.name2Actor().nextEntry(null);
      Aircraft localAircraft;
      for (; localObject1 != null; localObject1 = Engine.name2Actor().nextEntry((Map.Entry)localObject1)) {
        localObject2 = (Actor)((Map.Entry)localObject1).getValue();
        if ((localObject2 instanceof Aircraft)) {
          localAircraft = (Aircraft)localObject2;
          if ((!localAircraft.isNetPlayer()) && (!localAircraft.isNet()))
            localArrayList.add(localObject2);
        }
      }
      for (int j = 0; j < localArrayList.size(); j++) {
        localAircraft = (Aircraft)localArrayList.get(j);
        localAircraft.destroy();
      }
    }
  }

  private void prepareOrdersTree() {
    if (World.isPlayerGunner())
      World.getPlayerGunner().getAircraft();
    else {
      ((Main3D)Main.cur()).ordersTree.netMissionLoaded(World.getPlayerAircraft());
    }
    if (!isMirror()) {
      List localList = NetEnv.hosts();
      for (int i = 0; i < localList.size(); i++) {
        NetUser localNetUser = (NetUser)localList.get(i);
        localNetUser.ordersTree = new OrdersTree(false);
        localNetUser.ordersTree.netMissionLoaded(localNetUser.findAircraft());
      }
    }
  }

  private void doCheckMaxLag() {
    long l = Time.real();
    if ((this._lastCheckMaxLag <= 0L) || (l - this._lastCheckMaxLag >= 1000L)) {
      this._lastCheckMaxLag = l;
      if (Mission.isPlaying())
      {
        Object localObject;
        if (isMaster()) {
          localObject = Engine.targets();
          int i = ((List)localObject).size();
          for (int j = 0; j < i; j++) {
            Actor localActor = (Actor)((List)localObject).get(j);
            if ((!(localActor instanceof Aircraft)) || (!Actor.isAlive(localActor)) || (localActor.net.isMaster()))
              continue;
            NetUser localNetUser = ((Aircraft)localActor).netUser();
            if (localNetUser != null) {
              if (localNetUser.netMaxLag == null)
                localNetUser.netMaxLag = new NetMaxLag();
              localNetUser.netMaxLag.doServerCheck((Aircraft)localActor);
            }
          }
        }
        else
        {
          localObject = (NetUser)NetEnv.host();
          if (((NetUser)localObject).netMaxLag == null)
            ((NetUser)localObject).netMaxLag = new NetMaxLag();
          ((NetUser)localObject).netMaxLag.doClientCheck();
        }
      }
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
    this.checkUsers = new HashMapExt();
    this.checkPublicKey = 0;
    this.checkKey = 0;
    this.checkSecond2 = 0;
    this.host = NetEnv.host();
    this.serverName = this.host.shortName();
    Main.cur().netServerParams = this;
    this.outMsgF = new NetMsgFiltered();
    try {
      this.outMsgF.setIncludeTime(true);
    }
    catch (Exception localException) {
    }
    if (!Config.isUSE_RENDER())
      this.flags |= 8;
    synkExtraOcclusion();
    this.autoLogDetail = Config.cur.ini.get("chat", "autoLogDetail", this.autoLogDetail, 0, 3);

    this.nearMaxLagTime = Config.cur.ini.get("MaxLag", "nearMaxLagTime", this.nearMaxLagTime, 0.1F, 30.0F);

    this.farMaxLagTime = Config.cur.ini.get("MaxLag", "farMaxLagTime", this.farMaxLagTime, this.nearMaxLagTime, 30.0F);

    this.cheaterWarningDelay = Config.cur.ini.get("MaxLag", "cheaterWarningDelay", this.cheaterWarningDelay, 1.0F, 30.0F);

    this.cheaterWarningNum = Config.cur.ini.get("MaxLag", "cheaterWarningNum", this.cheaterWarningNum);

    this.checkRuntime = Config.cur.ini.get("NET", "checkRuntime", 0, 0, 2);
    this.eventlogHouse = Config.cur.ini.get("game", "eventlogHouse", false);
    this.eventlogClient = Config.cur.ini.get("game", "eventlogClient", -1);
  }

  public NetServerParams(NetChannel paramNetChannel, int paramInt, NetHost paramNetHost) {
    super(null, paramNetChannel, paramInt);
    this.checkUsers = new HashMapExt();
    this.checkPublicKey = 0;
    this.checkKey = 0;
    this.checkSecond2 = 0;
    this.host = paramNetHost;
    Main.cur().netServerParams = this;
    this.outMsgF = new NetMsgFiltered();
    try {
      this.outMsgF.setIncludeTime(true);
    }
    catch (Exception localException) {
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

  private int crcSFSFile(String paramString, int paramInt) {
    try {
      SFSInputStream localSFSInputStream = new SFSInputStream(Finger.LongFN(0L, paramString));

      paramInt = localSFSInputStream.crc(paramInt);
      localSFSInputStream.close();
    } catch (Exception localException) {
      NetObj.printDebug(localException);
      return 0;
    }
    return paramInt;
  }

  private int checkFirst(int paramInt) {
    if (paramInt != 0) {
      long l = Finger.file(paramInt, MainWin32.GetCDDrive("jvm.dll"), -1);

      l = Finger.file(l, MainWin32.GetCDDrive("java.dll"), -1);
      l = Finger.file(l, MainWin32.GetCDDrive("net.dll"), -1);
      l = Finger.file(l, MainWin32.GetCDDrive("verify.dll"), -1);
      l = Finger.file(l, MainWin32.GetCDDrive("zip.dll"), -1);
      l = Finger.file(l, "lib/rt.jar", -1);
      l += timeofday;
      ArrayList localArrayList = Main.cur().airClasses;
      for (int i = 0; i < localArrayList.size(); i++) {
        Class localClass = (Class)localArrayList.get(i);
        l = FlightModelMain.finger(l, Property.stringValue(localClass, "FlightModel", null));
      }

      try
      {
        l = Statics.getShipsFile().finger(l);
      }
      catch (Exception localException1) {
      }
      try {
        l = Statics.getTechnicsFile().finger(l);
      }
      catch (Exception localException2) {
      }
      try {
        l = Statics.getBuildingsFile().finger(l);
      }
      catch (Exception localException3) {
      }
      try {
        l = Statics.getChiefsFile().finger(l);
      }
      catch (Exception localException4) {
      }
      try {
        l = Statics.getAirFile().finger(l);
      }
      catch (Exception localException5) {
      }
      try {
        l = Statics.getStationaryFile().finger(l);
      }
      catch (Exception localException6) {
      }
      try {
        l = Statics.getRocketsFile().finger(l);
      }
      catch (Exception localException7) {
      }
      try {
        l = Statics.getVehiclesFile().finger(l);
      }
      catch (Exception localException8) {
      }
      try {
        l = Statics.getShips1File().finger(l);
      }
      catch (Exception localException9) {
      }
      try {
        l = Statics.getTechnics1File().finger(l);
      }
      catch (Exception localException10) {
      }
      try {
        l = Statics.getBuildings1File().finger(l);
      }
      catch (Exception localException11) {
      }
      try {
        l = Statics.getChiefs1File().finger(l);
      }
      catch (Exception localException12) {
      }
      try {
        l = Statics.getAir1File().finger(l);
      }
      catch (Exception localException13) {
      }
      try {
        l = Statics.getStationary1File().finger(l);
      }
      catch (Exception localException14) {
      }
      try {
        l = Statics.getRockets1File().finger(l);
      }
      catch (Exception localException15) {
      }
      try {
        l = Statics.getVehicles1File().finger(l);
      }
      catch (Exception localException16) {
      }
      try {
        l = Statics.getShips2File().finger(l);
      }
      catch (Exception localException17) {
      }
      try {
        l = Statics.getTechnics2File().finger(l);
      }
      catch (Exception localException18) {
      }
      try {
        l = Statics.getBuildings2File().finger(l);
      }
      catch (Exception localException19) {
      }
      try {
        l += Statics.getSize(new File("mods"), 0L, 0L);
      }
      catch (Exception localException20) {
      }
      String str = "files" + PATH_SEPARATOR + "3do" + PATH_SEPARATOR + "plane";
      try
      {
        l += Statics.getSize(new File(str), 0L, 0L);
      }
      catch (Exception localException21) {
      }
      str = "files" + PATH_SEPARATOR + "3do" + PATH_SEPARATOR + "cockpit";
      try
      {
        l += Statics.getSize(new File(str), 0L, 0L);
      }
      catch (Exception localException22) {
      }
      str = "files" + PATH_SEPARATOR + "samples";
      try {
        l += Statics.getSize(new File(str), 0L, 0L);
      }
      catch (Exception localException23) {
      }
      str = "files" + PATH_SEPARATOR + "presets" + PATH_SEPARATOR + "sounds";
      try
      {
        l += Statics.getSize(new File(str), 0L, 0L);
      }
      catch (Exception localException24) {
      }
      str = "files" + PATH_SEPARATOR + "effects";
      try {
        l += Statics.getSize(new File(str), 0L, 0L);
      }
      catch (Exception localException25)
      {
      }
      str = "files" + PATH_SEPARATOR + "3do" + PATH_SEPARATOR + "effects";
      try
      {
        l += Statics.getSize(new File(str), 0L, 0L);
      }
      catch (Exception localException26) {
      }
      paramInt = (int)l;
    }
    return paramInt;
  }

  private int checkSecond(int paramInt1, int paramInt2) {
    this.checkSecond2 = paramInt2;
    try {
      ClassLoader localClassLoader = getClass().getClassLoader();
      Field[] arrayOfField = (class$java$lang$ClassLoader == null ? (NetServerParams.class$java$lang$ClassLoader = class$ZutiNetServerParams("java.lang.ClassLoader")) : class$java$lang$ClassLoader).getDeclaredFields();

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
        if ((localClass.toString().indexOf(".builder.PlMisHouse") > -1) || (localClass.toString().indexOf(".builder.PlMission") > -1) || (localClass.toString().indexOf(".builder.PlMisBorn") > -1) || (localClass.toString().endsWith(".engine.Loc")) || (localClass.toString().endsWith(".engine.Config")) || (localClass.toString().indexOf(".Zuti") > -1) || (localClass.toString().indexOf(".ScrShot") > -1) || (localClass.toString().indexOf(".gui.GUIQuick") > -1) || (localClass.toString().indexOf(".rts.TrackIRWin") > -1) || (localClass.toString().indexOf(".rts.TrackIR") > -1) || (localClass.toString().indexOf(".hotkey.HookPilot") > -1) || (localClass.toString().indexOf(".engine.Camera3D") > -1) || (localClass.toString().indexOf(".engine.GUIRenders") > -1) || (localClass.toString().indexOf(".engine.Renders") > -1))
        {
          continue;
        }

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
    catch (Exception localException)
    {
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
      j = (int)localAircraft.finger(i); break;
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

  private boolean checkInput(int paramInt, NetMsgInput paramNetMsgInput) throws IOException
  {
    NetUser localNetUser = (NetUser)paramNetMsgInput.readNetObj();
    Object localObject;
    if (isMaster()) {
      localObject = (CheckUser)this.checkUsers.get(localNetUser);
      if (localObject != null)
        return ((CheckUser)localObject).checkInput(paramInt, paramNetMsgInput);
    } else {
      if (NetEnv.host() == localNetUser)
        return CheckUserInput(paramInt, paramNetMsgInput);
      paramNetMsgInput.reset();
      localObject = new NetMsgGuaranted();
      ((NetMsgGuaranted)localObject).writeMsg(paramNetMsgInput, 1);
      if (paramNetMsgInput.channel() == masterChannel())
        postTo(localNetUser.masterChannel(), (NetMsgGuaranted)localObject);
      else
        postTo(masterChannel(), (NetMsgGuaranted)localObject);
      return true;
    }
    return false;
  }

  private void checkUpdate() {
    if (!isSingle()) {
      long l = Time.currentReal();
      if (l >= this.checkTimeUpdate) {
        this.checkTimeUpdate = (l + 1000L);
        List localList = NetEnv.hosts();
        int i = localList.size();
        for (int j = 0; j < i; j++) {
          NetUser localNetUser1 = (NetUser)localList.get(j);
          if (!this.checkUsers.containsKey(localNetUser1))
            this.checkUsers.put(localNetUser1, new CheckUser(localNetUser1));
        }
        Object localObject;
        if (i != this.checkUsers.size()) {
          int k;
          do { k = 0;
            localObject = this.checkUsers.nextEntry(null);
            while (localObject != null)
            {
              NetUser localNetUser2 = (NetUser)((Map.Entry)localObject).getKey();
              if (localNetUser2.isDestroyed()) {
                this.checkUsers.remove(localNetUser2);
                k = 1;
                break;
              }
              localObject = this.checkUsers.nextEntry((Map.Entry)localObject);
            }

          }

          while (k != 0);
        }
        Map.Entry localEntry = this.checkUsers.nextEntry(null);
        for (; localEntry != null; localEntry = this.checkUsers.nextEntry(localEntry)) {
          if ((this.checkPublicKey == 0) && (this.checkRuntime >= 1))
            this.checkPublicKey = (int)(Math.random() * 4294967295.0D);
          localObject = (CheckUser)localEntry.getValue();
          ((CheckUser)localObject).checkUpdate(l);
        }
      }
    }
  }

  static Class class$ZutiNetServerParams(String paramString) {
    Class localClass;
    try {
      localClass = Class.forName(paramString);
    } catch (ClassNotFoundException localClassNotFoundException) {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }

    return localClass;
  }

  static {
    Spawn.add(class$com$maddox$il2$net$NetServerParams == null ? (NetServerParams.class$com$maddox$il2$net$NetServerParams = class$ZutiNetServerParams("com.maddox.il2.net.NetServerParams")) : class$com$maddox$il2$net$NetServerParams, new SPAWN());
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
      String str1 = "";
      int k;
      Object localObject;
      switch (paramInt) {
      case 8:
        if (NetServerParams.this.checkKey == 0)
          NetServerParams.access$1202(NetServerParams.this, NetServerParams.this.checkFirst(NetServerParams.this.checkPublicKey));
        i = NetServerParams.this.checkKey == paramNetMsgInput.readInt() ? 1 : 0;
        if (i == 0) break;
        this.state += 1; break;
      case 9:
        int j = 0;
        str1 = "( CRT=2 Failed )";
        if (NetServerParams.this.checkRuntime == 2)
          j = this.publicKey;
        k = paramNetMsgInput.readInt();
        i = k == NetServerParams.this.checkSecond(this.publicKey, j) ? 1 : 0;

        if (i != 0) {
          k = paramNetMsgInput.readInt();
          System.out.println("CRT=2 passed for connecting user!");
          i = k == NetServerParams.this.checkSecond2 ? 1 : 0;
        }
        if (i == 0) break;
        this.state += 1; break;
      case 10:
        str1 = "( Plane Check Failed )";
        localObject = this.user.findAircraft();
        if (Actor.isValid((Actor)localObject)) {
          k = Finger.incInt(this.publicKey, this.diff);
          i = paramNetMsgInput.readInt() == (int)((Aircraft)localObject).finger(k) ? 1 : 0;

          if (i != 0) {
            this.classAircraft = localObject.getClass();
          } else {
            this.classAircraft = null;
            System.out.println("User (" + this.user.fullName() + ") Failed Plane Check (" + this.user.findAircraft().toString() + ")");
          }

        }
        else
        {
          this.classAircraft = null;
          i = 1;
        }
        break;
      default:
        return false;
      }
      this.timeSended = 0L;
      if (i == 0) {
        localObject = paramNetMsgInput.channel();
        if (!((NetChannel)localObject).isDestroying()) {
          String str2 = str1 + "Timeout ";
          str2 = str2 + (paramInt - 8);
          ((NetChannel)localObject).destroy(str2);
        }
      }
      return true;
    }

    public void checkUpdate(long paramLong) {
      if (this.state <= 10)
      {
        Object localObject;
        if (this.timeSended != 0L) {
          if (paramLong >= this.timeSended + 150000L) {
            NetChannel localNetChannel = this.user.masterChannel();
            if (!localNetChannel.isDestroying()) {
              localObject = "Timeout .";
              localObject = (String)localObject + (this.state - 8);
              localNetChannel.destroy((String)localObject);
            }
          }
        }
        else try {
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
              if ((!Actor.isValid((Actor)localObject)) || (localObject.getClass().equals(this.classAircraft))) {
                break;
              }
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

        NetServerParams.access$002(localNetServerParams, i);
        NetServerParams.access$102(localNetServerParams, j);
        NetServerParams.access$202(localNetServerParams, k);
        NetServerParams.access$302(localNetServerParams, str);
        NetServerParams.access$402(localNetServerParams, paramNetMsgInput.readByte());
        NetServerParams.access$502(localNetServerParams, paramNetMsgInput.readFloat());
        NetServerParams.access$602(localNetServerParams, paramNetMsgInput.readFloat());
        NetServerParams.access$702(localNetServerParams, paramNetMsgInput.readFloat());
        NetServerParams.access$802(localNetServerParams, paramNetMsgInput.readInt());
        if ((paramNetMsgInput.channel() instanceof NetChannelInStream)) {
          NetServerParams.access$102(localNetServerParams, World.cur().diffCur.get());
          if (paramNetMsgInput.available() >= 8) {
            NetServerParams.access$902(localNetServerParams, paramNetMsgInput.readLong());
          }
          else
            NetServerParams.access$902(localNetServerParams, 0L);
        } else {
          World.cur().diffCur.set(j);
        }localNetServerParams.synkExtraOcclusion();
        if (paramNetMsgInput.available() >= 4)
          NetServerParams.access$1002(localNetServerParams, paramNetMsgInput.readInt());
        else
          NetServerParams.access$1002(localNetServerParams, -1);
      } catch (Exception localException) {
        NetObj.access$1101(localException);
      }
    }
  }
}