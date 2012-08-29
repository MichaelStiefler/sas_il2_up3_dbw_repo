package com.maddox.il2.game;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.Front;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Target;
import com.maddox.il2.ai.TargetsGuard;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Airdrome;
import com.maddox.il2.ai.air.Point_Stay;
import com.maddox.il2.ai.ground.ChiefGround;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.BmpUtils;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.DrawEnv;
import com.maddox.il2.engine.EffClouds;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Land2D;
import com.maddox.il2.engine.Land2DText;
import com.maddox.il2.engine.Land2Dn;
import com.maddox.il2.engine.LandConf;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.RenderContext;
import com.maddox.il2.fm.Wind;
import com.maddox.il2.game.order.OrdersTree;
import com.maddox.il2.gui.GUI;
import com.maddox.il2.gui.GUIAirArming;
import com.maddox.il2.gui.GUIPad;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetFileServerNoseart;
import com.maddox.il2.net.NetFileServerPilot;
import com.maddox.il2.net.NetFileServerSkin;
import com.maddox.il2.net.NetFilesTrack;
import com.maddox.il2.net.NetMissionListener;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.ActorViewPoint;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.Paratrooper;
import com.maddox.il2.objects.bridges.LongBridge;
import com.maddox.il2.objects.buildings.HouseManager;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.il2.objects.effects.LightsGlare;
import com.maddox.il2.objects.effects.SpritesFog;
import com.maddox.il2.objects.effects.SunGlare;
import com.maddox.il2.objects.effects.Zip;
import com.maddox.il2.objects.humans.Soldier;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.vehicles.artillery.RocketryGeneric;
import com.maddox.rts.BackgroundTask;
import com.maddox.rts.CfgInt;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Destroy;
import com.maddox.rts.Finger;
import com.maddox.rts.HomePath;
import com.maddox.rts.IniFile;
import com.maddox.rts.Joy;
import com.maddox.rts.KeyRecord;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgInvokeMethod_Object;
import com.maddox.rts.MsgNet;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelCallbackStream;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetChannelOutStream;
import com.maddox.rts.NetChannelStream;
import com.maddox.rts.NetControl;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgDestroy;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.rts.net.NetFileServerDef;
import com.maddox.sound.AudioDevice;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import com.maddox.util.NumberTokenizer;
import com.maddox.util.SharedTokenizer;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mission
  implements Destroy
{
  public static final String DIR = "missions/";
  public static final String DIRNET = "missions/Net/Cache/";
  public static final float CLOUD_HEIGHT = 8000.0F;
  private String name;
  private SectFile sectFile;
  private long sectFinger;
  private ArrayList actors;
  private int curActor;
  private boolean bPlaying;
  private int curCloudsType;
  private float curCloudsHeight;
  protected static int viewSet = 0;
  protected static int iconTypes = 0;

  private static HashMap respawnMap = new HashMap();
  private String player;
  private boolean _loadPlayer;
  private int playerNum;
  private HashMap mapWingTakeoff;
  private static SectFile chiefsIni;
  private static Point3d Loc = new Point3d();
  private static Orient Or = new Orient();
  private static Vector3f Spd = new Vector3f();
  private static Vector3d Spdd = new Vector3d();

  private static ActorSpawnArg spawnArg = new ActorSpawnArg();
  private static Point3d p = new Point3d();
  private static Orient o = new Orient();
  public static final int NET_MSG_ID_NAME = 0;
  public static final int NET_MSG_ID_BODY = 1;
  public static final int NET_MSG_ID_BODY_END = 2;
  public static final int NET_MSG_ID_ACTORS = 3;
  public static final int NET_MSG_ID_ACTORS_END = 4;
  public static final int NET_MSG_ID_LOADED = 5;
  public static final int NET_MSG_ID_BEGIN = 10;
  public static final int NET_MSG_ID_TOD = 11;
  public static final int NET_MSG_ID_START = 12;
  public static final int NET_MSG_ID_TIME = 13;
  public static final int NET_MSG_ID_END = 20;
  protected NetObj net;

  public Mission()
  {
    this.name = null;

    this.sectFinger = 0L;
    this.actors = new ArrayList();
    this.curActor = 0;
    this.bPlaying = false;

    this.curCloudsType = 0;
    this.curCloudsHeight = 1000.0F;

    this._loadPlayer = false;
    this.playerNum = 0;
  }

  public static float respawnTime(String paramString)
  {
    Object localObject = respawnMap.get(paramString);
    if (localObject == null)
      return 1800.0F;
    return ((Float)localObject).floatValue();
  }

  public static boolean isPlaying() {
    if (Main.cur() == null)
      return false;
    if (Main.cur().mission == null)
      return false;
    if (Main.cur().mission.isDestroyed())
      return false;
    return Main.cur().mission.bPlaying;
  }

  public static boolean isSingle() {
    if (Main.cur().mission == null)
      return false;
    if (Main.cur().mission.isDestroyed())
      return false;
    if (Main.cur().mission.net == null)
      return true;
    if (Main.cur().netServerParams == null)
      return true;
    return Main.cur().netServerParams.isSingle();
  }

  public static boolean isNet() {
    if (Main.cur().mission == null)
      return false;
    if (Main.cur().mission.isDestroyed())
      return false;
    if (Main.cur().mission.net == null)
      return false;
    if (Main.cur().netServerParams == null)
      return false;
    return !Main.cur().netServerParams.isSingle();
  }

  public NetChannel getNetMasterChannel() {
    if (this.net == null) return null;
    return this.net.masterChannel();
  }

  public static boolean isServer() {
    return NetEnv.isServer();
  }

  public static boolean isDeathmatch() {
    return isDogfight();
  }
  public static boolean isDogfight() {
    if (Main.cur().mission == null)
      return false;
    if (Main.cur().mission.isDestroyed())
      return false;
    if (Main.cur().mission.net == null)
      return false;
    if (Main.cur().netServerParams == null)
      return false;
    return Main.cur().netServerParams.isDogfight();
  }

  public static boolean isCoop() {
    if (Main.cur().mission == null)
      return false;
    if (Main.cur().mission.isDestroyed())
      return false;
    if (Main.cur().mission.net == null)
      return false;
    if (Main.cur().netServerParams == null)
      return false;
    return Main.cur().netServerParams.isCoop();
  }

  public static int curCloudsType() {
    if (Main.cur().mission == null) return 0;
    return Main.cur().mission.curCloudsType;
  }
  public static float curCloudsHeight() {
    if (Main.cur().mission == null) return 1000.0F;
    return Main.cur().mission.curCloudsHeight;
  }

  public static Mission cur() {
    return Main.cur().mission;
  }

  public static void BreakP() {
    System.out.print("");
  }

  public static void load(String paramString) throws Exception
  {
    load(paramString, false);
  }
  public static void load(String paramString, boolean paramBoolean) throws Exception {
    load("missions/", paramString, paramBoolean);
  }

  public static void load(String paramString1, String paramString2) throws Exception {
    load(paramString1, paramString2, false);
  }
  public static void load(String paramString1, String paramString2, boolean paramBoolean) throws Exception {
    Mission localMission = new Mission();
    if (cur() != null)
      cur().destroy();
    else
      localMission.clear();
    localMission.sectFile = new SectFile(paramString1 + paramString2);
    localMission.load(paramString2, localMission.sectFile, paramBoolean);
  }

  public static void loadFromSect(SectFile paramSectFile) throws Exception {
    loadFromSect(paramSectFile, false);
  }
  public static void loadFromSect(SectFile paramSectFile, boolean paramBoolean) throws Exception {
    Mission localMission = new Mission();
    String str = paramSectFile.fileName();
    if ((str != null) && 
      (str.toLowerCase().startsWith("missions/"))) {
      str = str.substring("missions/".length());
    }
    if (cur() != null)
      cur().destroy();
    else
      localMission.clear();
    localMission.sectFile = paramSectFile;
    localMission.load(str, localMission.sectFile, paramBoolean);
  }
  public String name() {
    return this.name;
  }
  public SectFile sectFile() { return this.sectFile; } 
  public long finger() {
    return this.sectFinger;
  }
  public boolean isDestroyed() {
    return this.name == null;
  }

  public void destroy() {
    if (isDestroyed())
      return;
    if (this.bPlaying)
      doEnd();
    this.bPlaying = false;
    clear();
    this.name = null;
    Main.cur().mission = null;
    if (Main.cur().netMissionListener != null) {
      Main.cur().netMissionListener.netMissionState(8, 0.0F, null);
    }
    if ((this.net != null) && (!this.net.isDestroyed()))
      this.net.destroy();
    this.net = null;
  }

  private void clear() {
    if (this.net != null) {
      doReplicateNotMissionActors(false);
      if (this.net.masterChannel() != null)
        doReplicateNotMissionActors(this.net.masterChannel(), false);
    }
    this.actors.clear();
    this.curActor = 0;
    Main.cur().resetGame();
  }
  private void Mission() {
  }

  private void load(String paramString, SectFile paramSectFile, boolean paramBoolean) throws Exception {
    if (paramBoolean)
      BackgroundTask.execute(new BackgroundLoader(paramString, paramSectFile));
    else
      _load(paramString, paramSectFile, paramBoolean);
  }

  private void LOADING_STEP(float paramFloat, String paramString)
  {
    if ((this.net != null) && (Main.cur().netMissionListener != null)) {
      Main.cur().netMissionListener.netMissionState(3, paramFloat, paramString);
    }

    if (!BackgroundTask.step(paramFloat, paramString))
      throw new RuntimeException(BackgroundTask.executed().messageCancel());
  }

  private void _load(String paramString, SectFile paramSectFile, boolean paramBoolean) throws Exception {
    AudioDevice.soundsOff();
    if (paramString != null) System.out.println("Loading mission " + paramString + "..."); else
      System.out.println("Loading mission ...");
    EventLog.checkState();
    Main.cur().missionLoading = this;
    RTSConf.cur.time.setEnableChangePause1(false);
    Actor.setSpawnFromMission(true);
    try {
      Main.cur().mission = this;
      this.name = paramString;
      if (this.net == null)
        createNetObject(null, 0);
      loadMain(paramSectFile);
      loadRespawnTime(paramSectFile);
      Front.loadMission(paramSectFile);
      List localList = null;
      if ((Main.cur().netServerParams.isCoop()) || (Main.cur().netServerParams.isSingle()))
      {
        localList = loadWings(paramSectFile);
        loadChiefs(paramSectFile);
      }
      loadHouses(paramSectFile);
      loadNStationary(paramSectFile);
      loadStationary(paramSectFile);
      loadRocketry(paramSectFile);
      loadViewPoint(paramSectFile);
      if (Main.cur().netServerParams.isDogfight()) {
        loadBornPlaces(paramSectFile);
      }
      if ((Main.cur().netServerParams.isMaster()) && ((Main.cur().netServerParams.isCoop()) || (Main.cur().netServerParams.isSingle())))
      {
        loadTargets(paramSectFile);
      }if (localList != null) {
        int i = localList.size();
        for (int j = 0; j < i; j++) {
          Wing localWing = (Wing)localList.get(j);
          if (Actor.isValid(localWing))
            localWing.setOnAirport();
        }
      }
    }
    catch (Exception localException1) {
      if ((this.net != null) && (Main.cur().netMissionListener != null)) {
        Main.cur().netMissionListener.netMissionState(4, 0.0F, localException1.getMessage());
      }

      printDebug(localException1);
      clear();
      if ((this.net != null) && (!this.net.isDestroyed()))
        this.net.destroy();
      this.net = null;
      Main.cur().mission = null;
      this.name = null;
      Actor.setSpawnFromMission(false);
      Main.cur().missionLoading = null;
      setTime(false);
      throw localException1;
    }
    if (Config.isUSE_RENDER()) {
      if (Actor.isValid(World.getPlayerAircraft()))
        World.land().cubeFullUpdate((float)World.getPlayerAircraft().jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().z);
      else
        World.land().cubeFullUpdate(1000.0F);
      GUI.pad.fillAirports();
    }
    Actor.setSpawnFromMission(false);
    Main.cur().missionLoading = null;
    Main.cur().missionCounter += 1;
    setTime(!Main.cur().netServerParams.isSingle());
    LOADING_STEP(90.0F, "task.Load_humans");
    Paratrooper.PRELOAD();
    LOADING_STEP(95.0F, "task.Load_humans");
    if ((Main.cur().netServerParams.isSingle()) || (Main.cur().netServerParams.isCoop()))
      Soldier.PRELOAD();
    LOADING_STEP(100.0F, "");
    if (Main.cur().netMissionListener != null) {
      Main.cur().netMissionListener.netMissionState(5, 0.0F, null);
    }

    if (this.net.isMirror()) {
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(4);
        this.net.masterChannel().userState = 4;
        this.net.postTo(this.net.masterChannel(), localNetMsgGuaranted);
      } catch (Exception localException2) {
        printDebug(localException2);
      }
      ((NetUser)NetEnv.host()).missionLoaded();
    }
    else if (Main.cur().netServerParams.isSingle()) {
      if ((Main.cur() instanceof Main3D)) {
        ((Main3D)Main.cur()).ordersTree.missionLoaded();
      }
      Main.cur().dotRangeFriendly.setDefault();
      Main.cur().dotRangeFoe.setDefault();
      Main.cur().dotRangeFoe.set(-1.0D, -1.0D, -1.0D, 5.0D, -1.0D, -1.0D);
    } else {
      ((NetUser)NetEnv.host()).replicateDotRange();
    }

    NetObj.tryReplicate(this.net, false);
    War.cur().missionLoaded();
  }

  private void setTime(boolean paramBoolean) {
    Time.setSpeed(1.0F);
    Time.setSpeedReal(1.0F);
    if (paramBoolean) {
      RTSConf.cur.time.setEnableChangePause1(false);
      RTSConf.cur.time.setEnableChangeSpeed(false);
      RTSConf.cur.time.setEnableChangeTickLen(true);
    } else {
      RTSConf.cur.time.setEnableChangePause1(true);
      RTSConf.cur.time.setEnableChangeSpeed(true);
      RTSConf.cur.time.setEnableChangeTickLen(false);
    }
  }

  private void loadMain(SectFile paramSectFile)
    throws Exception
  {
    int i = paramSectFile.get("MAIN", "TIMECONSTANT", 0, 0, 1);
    World.cur().setTimeOfDayConstant(i == 1);
    World.setTimeofDay(paramSectFile.get("MAIN", "TIME", 12.0F, 0.0F, 23.99F));

    int j = paramSectFile.get("MAIN", "WEAPONSCONSTANT", 0, 0, 1);
    World.cur().setWeaponsConstant(j == 1);

    String str1 = paramSectFile.get("MAIN", "MAP");
    if (str1 == null) {
      throw new Exception("No MAP in mission file ");
    }
    String str2 = null;
    int[] arrayOfInt = null;

    SectFile localSectFile = new SectFile("maps/" + str1);
    int k = localSectFile.sectionIndex("static");
    if ((k >= 0) && (localSectFile.vars(k) > 0)) {
      str2 = localSectFile.var(k, 0);
      if ((str2 == null) || (str2.length() <= 0)) {
        str2 = null;
      } else {
        str2 = HomePath.concatNames("maps/" + str1, str2);
        arrayOfInt = Statics.readBridgesEndPoints(str2);
      }
    }

    LOADING_STEP(0.0F, "task.Load_landscape");

    World.land().LoadMap(str1, arrayOfInt);

    World.cur().setCamouflage(World.land().config.camouflage);
    Main3D localMain3D;
    int m;
    if (Config.isUSE_RENDER())
    {
      if (Main3D.cur3D().land2D != null) {
        if (!Main3D.cur3D().land2D.isDestroyed())
          Main3D.cur3D().land2D.destroy();
        Main3D.cur3D().land2D = null;
      }
      localMain3D = null;
      m = localSectFile.sectionIndex("MAP2D");
      if (m >= 0) {
        n = localSectFile.vars(m);
        if (n > 0) {
          LOADING_STEP(20.0F, "task.Load_map");
          Main3D.cur3D().land2D = new Land2Dn(str1, World.land().getSizeX(), World.land().getSizeY());
        }

      }

      if (Main3D.cur3D().land2DText == null)
        Main3D.cur3D().land2DText = new Land2DText();
      else
        Main3D.cur3D().land2DText.clear();
      int n = localSectFile.sectionIndex("text");
      if ((n >= 0) && (localSectFile.vars(n) > 0)) {
        LOADING_STEP(22.0F, "task.Load_landscape_texts");
        String str3 = localSectFile.var(n, 0);
        Main3D.cur3D().land2DText.load(HomePath.concatNames("maps/" + str1, str3));
      }

    }

    if (str2 != null) {
      LOADING_STEP(23.0F, "task.Load_static_objects");
      Statics.load(str2, World.cur().statics.bridges);
      Engine.drawEnv().staticTrimToSize();
    }
    Statics.trim();

    if ((Main.cur().netServerParams.isSingle()) || (Main.cur().netServerParams.isCoop())) {
      World.cur().statics.loadStateBridges(paramSectFile, false);
      World.cur().statics.loadStateHouses(paramSectFile, false);
      World.cur().statics.loadStateBridges(paramSectFile, true);
      World.cur().statics.loadStateHouses(paramSectFile, true);
      checkBridgesAndHouses(paramSectFile);
    }

    if (Main.cur().netServerParams.isSingle()) {
      this.player = paramSectFile.get("MAIN", "player");
      this.playerNum = paramSectFile.get("MAIN", "playerNum", 0, 0, 3);
    } else {
      this.player = null;
    }
    World.setMissionArmy(paramSectFile.get("MAIN", "army", 1, 1, 2));

    if (Config.isUSE_RENDER())
    {
      Main3D.cur3D().ordersTree.setFrequency(true);
    }

    if (Config.isUSE_RENDER()) {
      LOADING_STEP(29.0F, "task.Load_landscape_effects");
      localMain3D = Main3D.cur3D();
      m = paramSectFile.get("MAIN", "CloudType", 0, 0, 6);
      float f = paramSectFile.get("MAIN", "CloudHeight", 1000.0F, 500.0F, 1500.0F);
      createClouds(m, f);
      if (!Config.cur.ini.get("game", "NoLensFlare", false)) {
        localMain3D.sunFlareCreate();
        localMain3D.sunFlareShow(true);
      } else {
        localMain3D.sunFlareShow(false);
      }

      World.wind().set(m, f, str1);

      for (int i1 = 0; i1 < 3; i1++) {
        Main3D.cur3D()._lightsGlare[i1].setShow(true);
        Main3D.cur3D()._sunGlare[i1].setShow(true);
      }
    }
  }

  public static void createClouds(int paramInt, float paramFloat)
  {
    if (paramInt < 0) paramInt = 0;
    if (paramInt > 6) paramInt = 6;
    if (cur() != null) {
      cur().curCloudsType = paramInt;
      cur().curCloudsHeight = paramFloat;
    }
    if (!Config.isUSE_RENDER()) return;
    Main3D localMain3D = Main3D.cur3D();
    Camera3D localCamera3D = (Camera3D)Actor.getByName("camera");

    if (localMain3D.clouds != null)
      localMain3D.clouds.destroy();
    localMain3D.clouds = new EffClouds(World.cur().diffCur.NewCloudsRender, paramInt, paramFloat);

    if (paramInt > 5) {
      try {
        if (localMain3D.zip != null)
          localMain3D.zip.destroy();
        localMain3D.zip = new Zip(paramFloat);
      } catch (Exception localException) {
        System.out.println("Zip load error: " + localException);
      }
    }

    int i = 5 - paramInt;
    if (paramInt == 6) i = 1;
    if (i > 4) i = 4;
    RenderContext.cfgLandFogHaze.set(i);
    RenderContext.cfgLandFogHaze.apply();
    RenderContext.cfgLandFogHaze.reset();

    RenderContext.cfgLandFogLow.set(0);
    RenderContext.cfgLandFogLow.apply();
    RenderContext.cfgLandFogLow.reset();

    if (Actor.isValid(localMain3D.spritesFog))
      localMain3D.spritesFog.destroy();
    localMain3D.spritesFog = new SpritesFog(localCamera3D, 0.7F, 7000.0F, 7500.0F);
  }
  public static void setCloudsType(int paramInt) {
    if (paramInt < 0) paramInt = 0;
    if (paramInt > 6) paramInt = 6;
    if (cur() != null)
      cur().curCloudsType = paramInt;
    if (!Config.isUSE_RENDER()) return;
    Main3D localMain3D = Main3D.cur3D();
    if (localMain3D.clouds != null)
      localMain3D.clouds.setType(paramInt);
    int i = 5 - paramInt;
    if (paramInt == 6) i = 1;
    if (i > 4) i = 4;
    RenderContext.cfgLandFogHaze.set(i);
    RenderContext.cfgLandFogHaze.apply();
    RenderContext.cfgLandFogHaze.reset();

    RenderContext.cfgLandFogLow.set(0);
    RenderContext.cfgLandFogLow.apply();
    RenderContext.cfgLandFogLow.reset();
  }
  public static void setCloudsHeight(float paramFloat) {
    if (cur() != null)
      cur().curCloudsHeight = paramFloat;
    if (!Config.isUSE_RENDER()) return;
    Main3D localMain3D = Main3D.cur3D();
    if (localMain3D.clouds != null)
      localMain3D.clouds.setHeight(paramFloat);
  }

  private void loadRespawnTime(SectFile paramSectFile)
  {
    respawnMap.clear();
    int i = paramSectFile.sectionIndex("RespawnTime");
    if (i < 0)
      return;
    int j = paramSectFile.vars(i);
    for (int k = 0; k < j; k++) {
      String str = paramSectFile.var(i, k);
      float f = paramSectFile.get("RespawnTime", str, 1800.0F, 20.0F, 1000000.0F);
      respawnMap.put(str, new Float(f));
    }
  }

  private List loadWings(SectFile paramSectFile) throws Exception {
    int i = paramSectFile.sectionIndex("Wing");
    if (i < 0)
      return null;
    if (!World.cur().diffCur.Takeoff_N_Landing)
      prepareTakeoff(paramSectFile, !Main.cur().netServerParams.isSingle());
    NetChannel localNetChannel = null;
    if (!isServer())
      localNetChannel = this.net.masterChannel();
    int j = paramSectFile.vars(i);
    ArrayList localArrayList = null;
    if (j > 0)
      localArrayList = new ArrayList(j);
    for (int k = 0; k < j; k++) {
      LOADING_STEP(30 + Math.round(k / j * 30.0F), "task.Load_aircraft");
      String str = paramSectFile.var(i, k);
      this._loadPlayer = str.equals(this.player);
      int m = paramSectFile.get(str, "StartTime", 0);
      if ((m > 0) && (!this._loadPlayer)) {
        if (localNetChannel != null)
          continue;
        double d = m * 60L;
        new MsgAction(0, d, new TimeOutWing(str)) {
          public void doAction(Object paramObject) {
            Mission.TimeOutWing localTimeOutWing = (Mission.TimeOutWing)paramObject;
            localTimeOutWing.start();
          }
        };
      }
      else {
        com.maddox.il2.objects.air.NetAircraft.loadingCoopPlane = (Main.cur().netServerParams != null) && (Main.cur().netServerParams.isCoop());

        Wing localWing = new Wing();
        localWing.load(paramSectFile, str, localNetChannel);
        if ((localNetChannel != null) && (!Main.cur().netServerParams.isCoop())) {
          Aircraft[] arrayOfAircraft = localWing.airc;
          for (int n = 0; n < arrayOfAircraft.length; n++) {
            if ((!Actor.isValid(arrayOfAircraft[n])) || 
              (arrayOfAircraft[n].net != null)) continue;
            arrayOfAircraft[n].destroy();
            arrayOfAircraft[n] = null;
          }
        }

        localArrayList.add(localWing);
        prepareSkinInWing(paramSectFile, localWing);
      }
    }
    LOADING_STEP(60.0F, "task.Load_aircraft");
    return localArrayList;
  }

  private void prepareSkinInWing(SectFile paramSectFile, Wing paramWing)
  {
    if (!Config.isUSE_RENDER()) return;
    Aircraft[] arrayOfAircraft = paramWing.airc;
    for (int i = 0; i < arrayOfAircraft.length; i++)
      if (Actor.isValid(arrayOfAircraft[i])) {
        Aircraft localAircraft = arrayOfAircraft[i];
        prepareSkinInWing(paramSectFile, localAircraft, paramWing.name(), i);
      }
  }

  private void prepareSkinInWing(SectFile paramSectFile, Aircraft paramAircraft, String paramString, int paramInt) {
    if (!Config.isUSE_RENDER()) return;
    String str1;
    String str2;
    String str3;
    String str4;
    if (World.getPlayerAircraft() == paramAircraft) {
      if (isSingle())
        if (NetMissionTrack.isPlaying()) {
          ((NetUser)Main.cur().netServerParams.host()).tryPrepareSkin(paramAircraft);
          ((NetUser)Main.cur().netServerParams.host()).tryPrepareNoseart(paramAircraft);
          ((NetUser)Main.cur().netServerParams.host()).tryPreparePilot(paramAircraft);
        } else {
          str1 = Property.stringValue(paramAircraft.getClass(), "keyName", null);
          str2 = World.cur().userCfg.getSkin(str1);
          if (str2 != null) {
            str3 = GUIAirArming.validateFileName(str1);
            ((NetUser)NetEnv.host()).setSkin(str3 + "/" + str2);
            ((NetUser)NetEnv.host()).tryPrepareSkin(paramAircraft);
          } else {
            ((NetUser)NetEnv.host()).setSkin(null);
          }

          str3 = World.cur().userCfg.getNoseart(str1);
          if (str3 != null) {
            ((NetUser)NetEnv.host()).setNoseart(str3);
            ((NetUser)NetEnv.host()).tryPrepareNoseart(paramAircraft);
          } else {
            ((NetUser)NetEnv.host()).setNoseart(null);
          }

          str4 = World.cur().userCfg.netPilot;
          ((NetUser)NetEnv.host()).setPilot(str4);
          if (str4 != null)
            ((NetUser)NetEnv.host()).tryPreparePilot(paramAircraft);
        }
    }
    else
    {
      str1 = paramSectFile.get(paramString, "skin" + paramInt, (String)null);
      if (str1 != null) {
        str2 = Aircraft.getPropertyMesh(paramAircraft.getClass(), paramAircraft.getRegiment().country());
        str1 = GUIAirArming.validateFileName(Property.stringValue(paramAircraft.getClass(), "keyName", null)) + "/" + str1;

        if (NetMissionTrack.isPlaying()) {
          str1 = NetFilesTrack.getLocalFileName(Main.cur().netFileServerSkin, Main.cur().netServerParams.host(), str1);
          if (str1 != null)
            str1 = Main.cur().netFileServerSkin.alternativePath() + "/" + str1;
        } else {
          str1 = Main.cur().netFileServerSkin.primaryPath() + "/" + str1;
        }
        if (str1 != null) {
          str3 = "PaintSchemes/Cache/" + Finger.file(0L, str1, -1);
          Aircraft.prepareMeshSkin(str2, paramAircraft.hierMesh(), str1, str3);
        }
      }

      str2 = paramSectFile.get(paramString, "noseart" + paramInt, (String)null);
      String str5;
      String str6;
      String str7;
      if (str2 != null) {
        str3 = Main.cur().netFileServerNoseart.primaryPath() + "/" + str2;
        str4 = str2.substring(0, str2.length() - 4);
        if (NetMissionTrack.isPlaying()) {
          str3 = NetFilesTrack.getLocalFileName(Main.cur().netFileServerNoseart, Main.cur().netServerParams.host(), str2);
          if (str3 != null) {
            str4 = str3.substring(0, str3.length() - 4);
            str3 = Main.cur().netFileServerNoseart.alternativePath() + "/" + str3;
          }
        }
        if (str3 != null) {
          str5 = "PaintSchemes/Cache/Noseart0" + str4 + ".tga";
          str6 = "PaintSchemes/Cache/Noseart0" + str4 + ".mat";
          str7 = "PaintSchemes/Cache/Noseart1" + str4 + ".tga";
          String str8 = "PaintSchemes/Cache/Noseart1" + str4 + ".mat";
          if (BmpUtils.bmp8PalTo2TGA4(str3, str5, str7)) {
            Aircraft.prepareMeshNoseart(paramAircraft.hierMesh(), str6, str8, str5, str7, null);
          }
        }
      }
      str3 = paramSectFile.get(paramString, "pilot" + paramInt, (String)null);
      if (str3 != null) {
        str4 = Main.cur().netFileServerPilot.primaryPath() + "/" + str3;
        str5 = str3.substring(0, str3.length() - 4);
        if (NetMissionTrack.isPlaying()) {
          str4 = NetFilesTrack.getLocalFileName(Main.cur().netFileServerPilot, Main.cur().netServerParams.host(), str3);
          if (str4 != null) {
            str5 = str4.substring(0, str4.length() - 4);
            str4 = Main.cur().netFileServerPilot.alternativePath() + "/" + str4;
          }
        }
        if (str4 != null) {
          str6 = "PaintSchemes/Cache/Pilot" + str5 + ".tga";
          str7 = "PaintSchemes/Cache/Pilot" + str5 + ".mat";
          if (BmpUtils.bmp8PalToTGA3(str4, str6))
            Aircraft.prepareMeshPilot(paramAircraft.hierMesh(), 0, str7, str6, null);
        }
      }
    }
  }

  public void prepareSkinAI(Aircraft paramAircraft) {
    String str1 = paramAircraft.name();
    if (str1.length() < 4) return;
    String str2 = str1.substring(0, str1.length() - 1);
    int i = 0;
    try {
      i = Integer.parseInt(str1.substring(str1.length() - 1, str1.length()));
    } catch (Exception localException) {
      return;
    }
    prepareSkinInWing(this.sectFile, paramAircraft, str2, i);
  }

  public void recordNetFiles() {
    if (isDogfight()) return;
    int i = this.sectFile.sectionIndex("Wing");
    if (i < 0)
      return;
    int j = this.sectFile.vars(i);
    for (int k = 0; k < j; k++)
      try {
        String str1 = this.sectFile.var(i, k);
        String str2 = this.sectFile.get(str1, "Class", (String)null);
        Class localClass = ObjIO.classForName(str2);
        String str3 = GUIAirArming.validateFileName(Property.stringValue(localClass, "keyName", null));
        for (int m = 0; m < 4; m++) {
          String str4 = this.sectFile.get(str1, "skin" + m, (String)null);
          if (str4 != null)
            recordNetFile(Main.cur().netFileServerSkin, str3 + "/" + str4);
          recordNetFile(Main.cur().netFileServerNoseart, this.sectFile.get(str1, "noseart" + m, (String)null));
          recordNetFile(Main.cur().netFileServerPilot, this.sectFile.get(str1, "pilot" + m, (String)null));
        }
      } catch (Exception localException) {
        printDebug(localException);
      }
  }

  private void recordNetFile(NetFileServerDef paramNetFileServerDef, String paramString) {
    if (paramString == null) return;
    String str = paramString;
    if (NetMissionTrack.isPlaying()) {
      str = NetFilesTrack.getLocalFileName(paramNetFileServerDef, Main.cur().netServerParams.host(), paramString);
      if (str == null) return;
    }
    NetFilesTrack.recordFile(paramNetFileServerDef, (NetUser)Main.cur().netServerParams.host(), paramString, str);
  }

  public Aircraft loadAir(SectFile paramSectFile, String paramString1, String paramString2, String paramString3, int paramInt)
    throws Exception
  {
    int i = !isServer() ? 1 : 0;

    Class localClass = ObjIO.classForName(paramString1);
    Aircraft localAircraft = (Aircraft)localClass.newInstance();

    if ((Main.cur().netServerParams.isSingle()) && (this._loadPlayer)) {
      if (Property.value(localClass, "cockpitClass", null) == null) {
        throw new Exception("One of selected aircraft has no cockpit.");
      }
      if (this.playerNum == 0) {
        World.setPlayerAircraft(localAircraft);
        this._loadPlayer = false;
      } else {
        this.playerNum -= 1;
      }
    }
    localAircraft.setName(paramString3);

    int j = 0;
    if (i != 0) {
      j = ((Integer)this.actors.get(this.curActor)).intValue();
      if (j == 0)
      {
        localAircraft.load(paramSectFile, paramString2, paramInt, null, 0);
      }
      else localAircraft.load(paramSectFile, paramString2, paramInt, this.net.masterChannel(), j); 
    }
    else
    {
      localAircraft.load(paramSectFile, paramString2, paramInt, null, 0);
    }

    if (localAircraft.isSpawnFromMission()) {
      if (this.net.isMirror()) {
        if (j == 0) this.actors.set(this.curActor++, null); else
          this.actors.set(this.curActor++, localAircraft);
      }
      else this.actors.add(localAircraft);

    }

    localAircraft.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();

    return localAircraft;
  }

  public static void preparePlayerNumberOn(SectFile paramSectFile) {
    UserCfg localUserCfg = World.cur().userCfg;
    String str1 = paramSectFile.get("MAIN", "player", "");
    if ("".equals(str1)) return;
    String str2 = paramSectFile.get("MAIN", "playerNum", "");
    paramSectFile.set(str1, "numberOn" + str2, localUserCfg.netNumberOn ? "1" : "0");
  }

  private void prepareTakeoff(SectFile paramSectFile, boolean paramBoolean)
  {
    if (paramBoolean) {
      int i = paramSectFile.sectionIndex("Wing");
      if (i < 0)
        return;
      int j = paramSectFile.vars(i);
      for (int k = 0; k < j; k++)
        prepareWingTakeoff(paramSectFile, paramSectFile.var(i, k));
    }
    else {
      String str = paramSectFile.get("MAIN", "player", (String)null);
      if (str == null)
        return;
      prepareWingTakeoff(paramSectFile, str);
    }
    this.sectFinger = paramSectFile.fingerExcludeSectPrefix("$$$");
  }

  private void prepareWingTakeoff(SectFile paramSectFile, String paramString)
  {
    String str1 = paramString + "_Way";
    int i = paramSectFile.sectionIndex(str1);
    if (i < 0) return;
    int j = paramSectFile.vars(i);
    if (j == 0) return;
    ArrayList localArrayList = new ArrayList(j);
    for (int k = 0; k < j; k++)
      localArrayList.add(paramSectFile.line(i, k));
    String str2 = (String)localArrayList.get(0);
    if (!str2.startsWith("TAKEOFF")) {
      return;
    }
    StringBuffer localStringBuffer = new StringBuffer("NORMFLY");
    NumberTokenizer localNumberTokenizer = new NumberTokenizer(str2);
    localNumberTokenizer.next((String)null);
    double d1 = localNumberTokenizer.next(1000.0D);
    double d2 = localNumberTokenizer.next(1000.0D);
    WingTakeoffPos localWingTakeoffPos = new WingTakeoffPos(d1, d2);
    Object localObject;
    if (this.mapWingTakeoff == null) {
      this.mapWingTakeoff = new HashMap();
      this.mapWingTakeoff.put(localWingTakeoffPos, localWingTakeoffPos);
    } else {
      while (true) {
        localObject = (WingTakeoffPos)this.mapWingTakeoff.get(localWingTakeoffPos);
        if (localObject == null) {
          this.mapWingTakeoff.put(localWingTakeoffPos, localWingTakeoffPos);
          break;
        }
        localWingTakeoffPos.x += 200;
      }
    }
    d1 = localWingTakeoffPos.x;
    d2 = localWingTakeoffPos.y;

    localStringBuffer.append(" "); localStringBuffer.append(d1);
    localStringBuffer.append(" "); localStringBuffer.append(d2);

    if (j > 1) {
      localObject = (String)localArrayList.get(1);
      if ((!((String)localObject).startsWith("TAKEOFF")) && (!((String)localObject).startsWith("LANDING")))
      {
        localNumberTokenizer = new NumberTokenizer((String)localObject);
        localNumberTokenizer.next((String)null);
        localNumberTokenizer.next((String)null);
        localNumberTokenizer.next((String)null);
        localStringBuffer.append(" ");
        localStringBuffer.append(localNumberTokenizer.next("1000.0"));
        localStringBuffer.append(" ");
        localStringBuffer.append(localNumberTokenizer.next("300.0"));
        localArrayList.set(0, localStringBuffer.toString());
        break label476;
      }
    }
    localStringBuffer.append(" 1000 300");
    localArrayList.set(0, localStringBuffer.toString());

    label476: paramSectFile.sectionClear(i);
    for (int m = 0; m < j; m++)
      paramSectFile.lineAdd(i, (String)localArrayList.get(m));
  }

  private void loadChiefs(SectFile paramSectFile)
    throws Exception
  {
    int i = paramSectFile.sectionIndex("Chiefs");
    if (i < 0)
      return;
    if (chiefsIni == null)
    {
      chiefsIni = new SectFile("com/maddox/il2/objects/chief.ini");
    }

    int j = paramSectFile.vars(i);
    for (int k = 0; k < j; k++) {
      LOADING_STEP(60 + Math.round(k / j * 20.0F), "task.Load_tanks");
      NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, k));
      String str1 = localNumberTokenizer.next();
      String str2 = localNumberTokenizer.next();
      int m = localNumberTokenizer.next(-1);
      if (m < 0) {
        System.out.println("Mission: Wrong chief's army [" + m + "]");
      }
      else {
        Chief.new_DELAY_WAKEUP = localNumberTokenizer.next(0.0F);
        Chief.new_SKILL_IDX = localNumberTokenizer.next(2);
        if ((Chief.new_SKILL_IDX < 0) || (Chief.new_SKILL_IDX > 3)) {
          System.out.println("Mission: Wrong chief's skill [" + Chief.new_SKILL_IDX + "]");
        }
        else {
          Chief.new_SLOWFIRE_K = localNumberTokenizer.next(1.0F);
          if ((Chief.new_SLOWFIRE_K < 0.5F) || (Chief.new_SLOWFIRE_K > 100.0F)) {
            System.out.println("Mission: Wrong chief's slowfire [" + Chief.new_SLOWFIRE_K + "]");
          }
          else if (chiefsIni.sectionIndex(str2) < 0) {
            System.out.println("Mission: Wrong chief's type [" + str2 + "]");
          }
          else
          {
            int n = str2.indexOf('.');
            if (n <= 0) {
              System.out.println("Mission: Wrong chief's type [" + str2 + "]");
            }
            else {
              String str3 = str2.substring(0, n);
              String str4 = str2.substring(n + 1);

              String str5 = chiefsIni.get(str3, str4);
              if (str5 == null) {
                System.out.println("Mission: Wrong chief's type [" + str2 + "]");
              }
              else {
                localNumberTokenizer = new NumberTokenizer(str5);
                str5 = localNumberTokenizer.nextToken();
                localNumberTokenizer.nextToken();
                String str6 = null;
                if (localNumberTokenizer.hasMoreTokens()) {
                  str6 = localNumberTokenizer.nextToken();
                }

                Class localClass = ObjIO.classForName(str5);

                if (localClass == null) {
                  System.out.println("Mission: Unknown chief's class [" + str5 + "]");
                }
                else
                {
                  Constructor localConstructor;
                  try
                  {
                    Class[] arrayOfClass = new Class[6];
                    arrayOfClass[0] = String.class;
                    arrayOfClass[1] = Integer.TYPE;
                    arrayOfClass[2] = SectFile.class;
                    arrayOfClass[3] = String.class;
                    arrayOfClass[4] = SectFile.class;
                    arrayOfClass[5] = String.class;
                    localConstructor = localClass.getConstructor(arrayOfClass);
                  } catch (Exception localException1) {
                    System.out.println("Mission: No required constructor in chief's class [" + str5 + "]");

                    continue;
                  }

                  Exception localException2 = this.curActor;
                  Object localObject;
                  try {
                    Object[] arrayOfObject = new Object[6];
                    arrayOfObject[0] = str1;
                    arrayOfObject[1] = new Integer(m);
                    arrayOfObject[2] = chiefsIni;
                    arrayOfObject[3] = str2;
                    arrayOfObject[4] = paramSectFile;
                    arrayOfObject[5] = (str1 + "_Road");
                    localObject = localConstructor.newInstance(arrayOfObject);
                  } catch (Exception localException3) {
                    System.out.println("Mission: Can't create chief '" + str1 + "' [class:" + str5 + "]");

                    continue;
                  }

                  if (str6 != null) {
                    ((Actor)localObject).icon = IconDraw.get(str6);
                  }
                  if ((localException2 != this.curActor) && (this.net != null) && (this.net.isMirror())) {
                    for (localException3 = localException2; localException3 < this.curActor; localException3++) {
                      Actor localActor = (Actor)this.actors.get(localException3);
                      if ((localActor.net == null) || (localActor.net.isMaster())) {
                        if (Actor.isValid(localActor)) {
                          if ((localObject instanceof ChiefGround))
                            ((ChiefGround)localObject).Detach(localActor, localActor);
                          localActor.destroy();
                        }
                        this.actors.set(localException3, null);
                      }
                    }
                  }
                  if ((localObject instanceof ChiefGround))
                    ((ChiefGround)localObject).dreamFire(true); 
                }
              }
            }
          }
        }
      }
    }
  }

  public int getUnitNetIdRemote(Actor paramActor) {
    if (this.net.isMaster()) {
      this.actors.add(paramActor);
      return 0;
    }
    Integer localInteger = (Integer)this.actors.get(this.curActor);
    this.actors.set(this.curActor, paramActor);
    this.curActor += 1;
    return localInteger.intValue();
  }

  private Actor loadStationaryActor(String paramString1, String paramString2, int paramInt, double paramDouble1, double paramDouble2, float paramFloat1, float paramFloat2, String paramString3, String paramString4, String paramString5)
  {
    Class localClass = null;
    try { localClass = ObjIO.classForName(paramString2);
    } catch (Exception localException1) {
      System.out.println("Mission: class '" + paramString2 + "' not found");
      return null;
    }
    ActorSpawn localActorSpawn = (ActorSpawn)Spawn.get(localClass.getName(), false);
    if (localActorSpawn == null) {
      System.out.println("Mission: ActorSpawn for '" + paramString2 + "' not found");
      return null;
    }
    spawnArg.clear();
    if (paramString1 != null) {
      if ("NONAME".equals(paramString1)) {
        System.out.println("Mission: 'NONAME' - not valid actor name");
        return null;
      }
      if (Actor.getByName(paramString1) != null) {
        System.out.println("Mission: actor '" + paramString1 + "' alredy exist");
        return null;
      }
      spawnArg.name = paramString1;
    }
    spawnArg.army = paramInt; spawnArg.armyExist = true;
    spawnArg.country = paramString3;

    Chief.new_DELAY_WAKEUP = 0.0F;
    com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.new_RADIUS_HIDE = 0.0F;
    if (paramString3 != null)
      try {
        Chief.new_DELAY_WAKEUP = Integer.parseInt(paramString3);
        com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.new_RADIUS_HIDE = Chief.new_DELAY_WAKEUP;
      }
      catch (Exception localException2) {
      }
    Chief.new_SKILL_IDX = 2;
    if (paramString4 != null) {
      try {
        Chief.new_SKILL_IDX = Integer.parseInt(paramString4); } catch (Exception localException3) {
      }
      if ((Chief.new_SKILL_IDX < 0) || (Chief.new_SKILL_IDX > 3)) {
        System.out.println("Mission: Wrong actor skill '" + Chief.new_SKILL_IDX + "'");
        return null;
      }
    }

    Chief.new_SLOWFIRE_K = 1.0F;
    if (paramString5 != null) {
      try {
        Chief.new_SLOWFIRE_K = Float.parseFloat(paramString5); } catch (Exception localException4) {
      }
      if ((Chief.new_SLOWFIRE_K < 0.5F) || (Chief.new_SLOWFIRE_K > 100.0F)) {
        System.out.println("Mission: Wrong actor slowfire '" + Chief.new_SLOWFIRE_K + "'");
        return null;
      }
    }

    p.set(paramDouble1, paramDouble2, 0.0D);
    spawnArg.point = p;
    o.set(paramFloat1, 0.0F, 0.0F);
    spawnArg.orient = o;
    if (paramFloat2 > 0.0F) {
      spawnArg.timeLenExist = true;
      spawnArg.timeLen = paramFloat2;
    }

    spawnArg.netChannel = null;
    spawnArg.netIdRemote = 0;
    if (this.net.isMirror()) {
      spawnArg.netChannel = this.net.masterChannel();
      spawnArg.netIdRemote = ((Integer)this.actors.get(this.curActor)).intValue();
      if (spawnArg.netIdRemote == 0) {
        this.actors.set(this.curActor++, null);
        return null;
      }
    }
    Actor localActor = null;
    try {
      localActor = localActorSpawn.actorSpawn(spawnArg);
    } catch (Exception localException5) {
      System.out.println(localException5.getMessage());
      localException5.printStackTrace();
    }
    if (this.net.isMirror())
      this.actors.set(this.curActor++, localActor);
    else {
      this.actors.add(localActor);
    }
    return localActor;
  }

  private void loadStationary(SectFile paramSectFile)
  {
    int i = paramSectFile.sectionIndex("Stationary");
    if (i < 0)
      return;
    int j = paramSectFile.vars(i);
    for (int k = 0; k < j; k++) {
      LOADING_STEP(80 + Math.round(k / j * 5.0F), "task.Load_stationary_objects");
      NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, k));
      loadStationaryActor(null, localNumberTokenizer.next(""), localNumberTokenizer.next(0), localNumberTokenizer.next(0.0D), localNumberTokenizer.next(0.0D), localNumberTokenizer.next(0.0F), localNumberTokenizer.next(0.0F), localNumberTokenizer.next((String)null), localNumberTokenizer.next((String)null), localNumberTokenizer.next((String)null));
    }
  }

  private void loadNStationary(SectFile paramSectFile) {
    int i = paramSectFile.sectionIndex("NStationary");
    if (i < 0)
      return;
    int j = paramSectFile.vars(i);
    for (int k = 0; k < j; k++) {
      LOADING_STEP(85 + Math.round(k / j * 5.0F), "task.Load_stationary_objects");
      NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, k));
      loadStationaryActor(localNumberTokenizer.next(""), localNumberTokenizer.next(""), localNumberTokenizer.next(0), localNumberTokenizer.next(0.0D), localNumberTokenizer.next(0.0D), localNumberTokenizer.next(0.0F), localNumberTokenizer.next(0.0F), localNumberTokenizer.next((String)null), localNumberTokenizer.next((String)null), localNumberTokenizer.next((String)null));
    }
  }

  private void loadRocketry(SectFile paramSectFile)
  {
    int i = paramSectFile.sectionIndex("Rocket");
    if (i < 0)
      return;
    int j = paramSectFile.vars(i);
    for (int k = 0; k < j; k++) {
      NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, k)); if (localNumberTokenizer.hasMoreTokens()) {
        String str1 = localNumberTokenizer.next(""); if (localNumberTokenizer.hasMoreTokens()) {
          String str2 = localNumberTokenizer.next(""); if (localNumberTokenizer.hasMoreTokens()) {
            int m = localNumberTokenizer.next(1, 1, 2);
            double d1 = localNumberTokenizer.next(0.0D); if (localNumberTokenizer.hasMoreTokens()) {
              double d2 = localNumberTokenizer.next(0.0D); if (localNumberTokenizer.hasMoreTokens()) {
                float f1 = localNumberTokenizer.next(0.0F); if (localNumberTokenizer.hasMoreTokens()) {
                  float f2 = localNumberTokenizer.next(0.0F);
                  int n = localNumberTokenizer.next(1);
                  float f3 = localNumberTokenizer.next(20.0F);
                  Point2d localPoint2d = null;
                  if (localNumberTokenizer.hasMoreTokens()) {
                    localPoint2d = new Point2d(localNumberTokenizer.next(0.0D), localNumberTokenizer.next(0.0D));
                  }
                  NetChannel localNetChannel = null;
                  int i1 = 0;
                  if (this.net.isMirror()) {
                    localNetChannel = this.net.masterChannel();
                    i1 = ((Integer)this.actors.get(this.curActor)).intValue();
                    if (i1 == 0) {
                      this.actors.set(this.curActor++, null);
                      continue;
                    }
                  }
                  RocketryGeneric localRocketryGeneric = null;
                  try {
                    localRocketryGeneric = RocketryGeneric.New(str1, str2, localNetChannel, i1, m, d1, d2, f1, f2, n, f3, localPoint2d);
                  }
                  catch (Exception localException) {
                    System.out.println(localException.getMessage());
                    localException.printStackTrace();
                  }
                  if (this.net.isMirror())
                    this.actors.set(this.curActor++, localRocketryGeneric);
                  else
                    this.actors.add(localRocketryGeneric); 
                }
              }
            }
          }
        }
      }
    }
  }

  private void loadHouses(SectFile paramSectFile) {
    int i = paramSectFile.sectionIndex("Buildings");
    if (i < 0)
      return;
    int j = paramSectFile.vars(i);
    if (j == 0)
      return;
    HouseManager localHouseManager;
    if (this.net.isMirror()) {
      spawnArg.netChannel = this.net.masterChannel();
      spawnArg.netIdRemote = ((Integer)this.actors.get(this.curActor)).intValue();
      localHouseManager = new HouseManager(paramSectFile, "Buildings", this.net.masterChannel(), ((Integer)this.actors.get(this.curActor)).intValue());
      this.actors.set(this.curActor++, localHouseManager);
    } else {
      localHouseManager = new HouseManager(paramSectFile, "Buildings", null, 0);
      this.actors.add(localHouseManager);
    }
  }

  private void loadBornPlaces(SectFile paramSectFile)
  {
    int i = paramSectFile.sectionIndex("BornPlace");
    if (i < 0)
      return;
    int j = paramSectFile.vars(i);
    if (j == 0)
      return;
    if (World.cur().airdrome == null)
      return;
    if (World.cur().airdrome.stay == null)
      return;
    World.cur().bornPlaces = new ArrayList(j);
    for (int k = 0; k < j; k++) {
      NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, k));
      int m = localNumberTokenizer.next(0, 0, Army.amountNet() - 1);
      float f1 = localNumberTokenizer.next(1000, 500, 10000);
      double d1 = f1 * f1;
      float f2 = localNumberTokenizer.next(0);
      float f3 = localNumberTokenizer.next(0);
      boolean bool = localNumberTokenizer.next(1) == 1;

      int n = 0;
      Point_Stay[][] arrayOfPoint_Stay = World.cur().airdrome.stay;
      for (int i1 = 0; i1 < arrayOfPoint_Stay.length; i1++)
        if (arrayOfPoint_Stay[i1] != null) {
          localObject1 = arrayOfPoint_Stay[i1][(arrayOfPoint_Stay[i1].length - 1)];
          if ((((Point_Stay)localObject1).jdField_x_of_type_Float - f2) * (((Point_Stay)localObject1).jdField_x_of_type_Float - f2) + (((Point_Stay)localObject1).jdField_y_of_type_Float - f3) * (((Point_Stay)localObject1).jdField_y_of_type_Float - f3) <= d1) {
            n = 1;
            break;
          }
        }
      if (n == 0)
        continue;
      Object localObject1 = new BornPlace(f2, f3, m, f1);
      ((BornPlace)localObject1).bParachute = bool;
      World.cur().bornPlaces.add(localObject1);
      int i3;
      Object localObject2;
      if (this.actors != null) {
        i2 = this.actors.size();
        for (i3 = 0; i3 < i2; i3++) {
          Actor localActor = (Actor)this.actors.get(i3);
          if ((Actor.isValid(localActor)) && (localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos != null)) {
            localObject2 = localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
            double d2 = (((Point3d)localObject2).jdField_x_of_type_Double - f2) * (((Point3d)localObject2).jdField_x_of_type_Double - f2) + (((Point3d)localObject2).jdField_y_of_type_Double - f3) * (((Point3d)localObject2).jdField_y_of_type_Double - f3);
            if (d2 <= d1) {
              localActor.setArmy(((BornPlace)localObject1).army);
            }
          }
        }
      }
      int i2 = paramSectFile.sectionIndex("BornPlace" + k);
      if (i2 >= 0) {
        i3 = paramSectFile.vars(i2);
        for (int i4 = 0; i4 < i3; i4++) {
          localObject2 = paramSectFile.var(i2, i4);
          if (localObject2 != null) {
            localObject2 = ((String)localObject2).intern();
            Class localClass = (Class)Property.value(localObject2, "airClass", null);
            if (localClass != null) {
              if (((BornPlace)localObject1).airNames == null)
                ((BornPlace)localObject1).airNames = new ArrayList();
              ((BornPlace)localObject1).airNames.add(localObject2);
            }
          }
        }
      }
    }
  }

  private void loadTargets(SectFile paramSectFile) {
    if (!Main.cur().netServerParams.isSingle()) {
      if (!Main.cur().netServerParams.isCoop()) return;
      if (!Main.cur().netServerParams.isMaster()) return;
    }
    int i = paramSectFile.sectionIndex("Target");
    if (i < 0)
      return;
    int j = paramSectFile.vars(i);
    for (int k = 0; k < j; k++)
      Target.create(paramSectFile.line(i, k));
  }

  private void loadViewPoint(SectFile paramSectFile)
  {
    int i = paramSectFile.sectionIndex("StaticCamera");
    if (i < 0)
      return;
    int j = paramSectFile.vars(i);
    for (int k = 0; k < j; k++) {
      NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, k));
      float f1 = localNumberTokenizer.next(0);
      float f2 = localNumberTokenizer.next(0);
      float f3 = localNumberTokenizer.next(100, 2, 10000);
      ActorViewPoint localActorViewPoint = new ActorViewPoint();
      World.land(); Point3d localPoint3d = new Point3d(f1, f2, f3 + Landscape.HQ_Air(f1, f2));
      localActorViewPoint.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(localPoint3d); localActorViewPoint.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
      localActorViewPoint.dreamFire(true);
      localActorViewPoint.setName("StaticCamera_" + k);
      if (this.net.isMirror()) {
        localActorViewPoint.createNetObject(this.net.masterChannel(), ((Integer)this.actors.get(this.curActor)).intValue());

        this.actors.set(this.curActor++, localActorViewPoint);
      } else {
        localActorViewPoint.createNetObject(null, 0);
        this.actors.add(localActorViewPoint);
      }
    }
  }

  private void checkBridgesAndHouses(SectFile paramSectFile)
  {
    int i = paramSectFile.sections();
    int i1;
    for (int j = 0; j < i; j++) {
      String str1 = paramSectFile.sectionName(j);
      Object localObject;
      if (str1.endsWith("_Way")) {
        m = paramSectFile.vars(j);
        for (n = 0; n < m; n++) {
          String str2 = paramSectFile.var(j, n);
          if (str2.equals("GATTACK")) {
            SharedTokenizer.set(paramSectFile.value(j, n));
            SharedTokenizer.next((String)null);
            SharedTokenizer.next((String)null);
            SharedTokenizer.next((String)null);
            SharedTokenizer.next((String)null);
            localObject = SharedTokenizer.next((String)null);
            if ((localObject == null) || 
              (!((String)localObject).startsWith("Bridge"))) continue;
            LongBridge localLongBridge1 = (LongBridge)Actor.getByName(" " + (String)localObject);
            if ((localLongBridge1 == null) || 
              (localLongBridge1.isAlive())) continue;
            localLongBridge1.BeLive();
          }
        }
      } else if (str1.endsWith("_Road")) {
        m = paramSectFile.vars(j);
        for (n = 0; n < m; n++) {
          SharedTokenizer.set(paramSectFile.value(j, n));
          SharedTokenizer.next((String)null);
          i1 = (int)SharedTokenizer.next(1.0D);
          if (i1 < 0) {
            i1 = -i1 - 1;
            localObject = LongBridge.getByIdx(i1);
            if ((localObject == null) || 
              (((LongBridge)localObject).isAlive())) continue;
            ((LongBridge)localObject).BeLive();
          }
        }
      }
    }
    int k = paramSectFile.sectionIndex("Target");
    if (k < 0) return;
    int m = paramSectFile.vars(k);
    for (int n = 0; n < m; n++) {
      SharedTokenizer.set(paramSectFile.line(k, n));
      i1 = SharedTokenizer.next(0, 0, 7);
      if ((i1 != 1) && (i1 != 2) && (i1 != 6) && (i1 != 7))
      {
        continue;
      }
      SharedTokenizer.next(0);
      SharedTokenizer.next(0);
      SharedTokenizer.next(0);
      SharedTokenizer.next(0);
      int i2 = SharedTokenizer.next(0);
      int i3 = SharedTokenizer.next(0);
      int i4 = SharedTokenizer.next(1000, 50, 3000);
      int i5 = SharedTokenizer.next(0);
      String str3 = SharedTokenizer.next(null);
      if ((str3 != null) && (str3.startsWith("Bridge")))
        str3 = " " + str3;
      switch (i1) {
      case 1:
      case 6:
        World.cur().statics.restoreAllHouses(i2, i3, i4);
        break;
      case 2:
      case 7:
        if (str3 == null) continue;
        LongBridge localLongBridge2 = (LongBridge)Actor.getByName(str3);
        if ((localLongBridge2 == null) || 
          (localLongBridge2.isAlive())) continue;
        localLongBridge2.BeLive();
      case 3:
      case 4:
      case 5:
      }
    }
  }

  public static void doMissionStarting()
  {
    ArrayList localArrayList = new ArrayList(Engine.targets());
    int i = localArrayList.size();
    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)localArrayList.get(j);
      if (!Actor.isValid(localActor)) continue;
      try {
        localActor.missionStarting();
      } catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
      }
    }
  }

  public void doBegin()
  {
    if (this.bPlaying) return;

    if (Config.isUSE_RENDER()) {
      Main3D.cur3D().setDrawLand(true);
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
      CmdEnv.top().exec("fov 70");
      if (Main3D.cur3D().keyRecord != null) {
        Main3D.cur3D().keyRecord.clearPrevStates();
        Main3D.cur3D().keyRecord.clearRecorded();
        Main3D.cur3D().keyRecord.stopRecording(false);
        if (Main.cur().netServerParams.isSingle())
          Main3D.cur3D().keyRecord.startRecording();
      }
      NetMissionTrack.countRecorded = 0;
      if (Main3D.cur3D().guiManager != null) {
        Main3D.cur3D().guiManager.setTimeGameActive(true);
        GUIPad.bStartMission = true;
      }

      if (!Main.cur().netServerParams.isCoop()) {
        doMissionStarting();
      }
      if (Main.cur().netServerParams.isDogfight()) {
        Time.setPause(false);
        RTSConf.cur.time.setEnableChangePause1(false);
      }

      CmdEnv.top().exec("music PUSH");
      CmdEnv.top().exec("music STOP");
      if (!Main3D.cur3D().isDemoPlaying()) {
        ForceFeedback.startMission();
      }
      Joy.adapter().rePostMoves();

      viewSet = Main3D.cur3D().viewSet_Get();
      iconTypes = Main3D.cur3D().iconTypes();
    } else {
      doMissionStarting();
      Time.setPause(false);
    }

    if (this.net.isMaster()) {
      sendCmd(10);
      doReplicateNotMissionActors(true);
    }
    if (Main.cur().netServerParams.isSingle()) {
      Main.cur().netServerParams.setExtraOcclusion(false);
      AudioDevice.soundsOn();
    }
    if ((Main.cur().netServerParams.isMaster()) && ((Main.cur().netServerParams.isCoop()) || (Main.cur().netServerParams.isSingle())))
    {
      World.cur().targetsGuard.activate();
    }EventLog.type(true, "Mission: " + name() + " is Playing");
    EventLog.type("Mission BEGIN");
    this.bPlaying = true;
    if (Main.cur().netServerParams != null)
      Main.cur().netServerParams.USGSupdate();
  }

  public void doEnd() {
    if (!this.bPlaying) return;
    EventLog.type("Mission END");

    if (Config.isUSE_RENDER()) {
      ForceFeedback.stopMission();
      if (Main3D.cur3D().guiManager != null)
        Main3D.cur3D().guiManager.setTimeGameActive(false);
      NetMissionTrack.stopRecording();
      if (Main3D.cur3D().keyRecord != null) {
        if (Main3D.cur3D().keyRecord.isPlaying()) {
          Main3D.cur3D().keyRecord.stopPlay();
          Main3D.cur3D().guiManager.setKeyboardGameActive(true);
          Main3D.cur3D().guiManager.setMouseGameActive(true);
          Main3D.cur3D().guiManager.setJoyGameActive(true);
        }
        Main3D.cur3D().keyRecord.stopRecording(true);
      }
      CmdEnv.top().exec("music POP");
      CmdEnv.top().exec("music PLAY");
    }

    RTSConf.cur.time.setEnableChangePause1(true);
    Time.setPause(true);

    if (this.net.isMaster()) {
      sendCmd(20);
    }
    AudioDevice.soundsOff();
    Voice.endSession();
    this.bPlaying = false;
    if (Main.cur().netServerParams != null)
      Main.cur().netServerParams.USGSupdate();
  }

  public NetObj netObj()
  {
    return this.net;
  }

  private void sendCmd(int paramInt) {
    if (this.net.isMirrored())
      try {
        List localList = NetEnv.channels();
        int i = localList.size();
        for (int j = 0; j < i; j++) {
          NetChannel localNetChannel = (NetChannel)localList.get(j);
          if ((localNetChannel == this.net.masterChannel()) || (!localNetChannel.isReady()) || (!localNetChannel.isMirrored(this.net)) || ((localNetChannel.userState != 4) && (localNetChannel.userState != 0)))
          {
            continue;
          }

          NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
          localNetMsgGuaranted.writeByte(paramInt);
          this.net.postTo(localNetChannel, localNetMsgGuaranted);
        }
      } catch (Exception localException) {
        printDebug(localException);
      }
  }

  private void doReplicateNotMissionActors(boolean paramBoolean) {
    if (this.net.isMirrored()) {
      List localList = NetEnv.channels();
      int i = localList.size();
      for (int j = 0; j < i; j++) {
        NetChannel localNetChannel = (NetChannel)localList.get(j);
        if ((localNetChannel == this.net.masterChannel()) || (!localNetChannel.isReady()) || (!localNetChannel.isMirrored(this.net)))
          continue;
        if (paramBoolean) {
          if (localNetChannel.userState == 4)
            doReplicateNotMissionActors(localNetChannel, true);
        }
        else localNetChannel.userState = 1;
      }
    }
  }

  private void doReplicateNotMissionActors(NetChannel paramNetChannel, boolean paramBoolean)
  {
    if (paramBoolean) {
      paramNetChannel.userState = 0;

      HashMapInt localHashMapInt = NetEnv.cur().objects;
      HashMapIntEntry localHashMapIntEntry = localHashMapInt.nextEntry(null);
      while (localHashMapIntEntry != null) {
        NetObj localNetObj = (NetObj)localHashMapIntEntry.getValue();
        if (((localNetObj instanceof ActorNet)) && (!paramNetChannel.isMirrored(localNetObj))) {
          ActorNet localActorNet = (ActorNet)localNetObj;
          if ((Actor.isValid(localActorNet.actor())) && (!localActorNet.actor().isSpawnFromMission()))
            MsgNet.postRealNewChannel(localNetObj, paramNetChannel);
        }
        localHashMapIntEntry = localHashMapInt.nextEntry(localHashMapIntEntry);
      }
    } else {
      paramNetChannel.userState = 1;
    }
  }

  private void doResvMission(NetMsgInput paramNetMsgInput) {
    try {
      while (paramNetMsgInput.available() > 0) {
        int i = paramNetMsgInput.readInt();
        if (i < 0) {
          String str = paramNetMsgInput.read255();
          this.sectFile.sectionAdd(str);
        } else {
          this.sectFile.lineAdd(i, paramNetMsgInput.read255(), paramNetMsgInput.read255());
        }
      }
    } catch (Exception localException) {
      printDebug(localException);
      System.out.println("Bad format reseived missiion");
    }
  }

  private void doSendMission(NetChannel paramNetChannel, int paramInt) {
    try {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(paramInt);
      int i = this.sectFile.sections();
      for (int j = 0; j < i; j++) {
        String str = this.sectFile.sectionName(j);
        if (!str.startsWith("$$$")) {
          if (localNetMsgGuaranted.size() >= 128) {
            this.net.postTo(paramNetChannel, localNetMsgGuaranted);
            localNetMsgGuaranted = new NetMsgGuaranted();
            localNetMsgGuaranted.writeByte(paramInt);
          }
          localNetMsgGuaranted.writeInt(-1);
          localNetMsgGuaranted.write255(str);
          int k = this.sectFile.vars(j);
          for (int m = 0; m < k; m++) {
            if (localNetMsgGuaranted.size() >= 128) {
              this.net.postTo(paramNetChannel, localNetMsgGuaranted);
              localNetMsgGuaranted = new NetMsgGuaranted();
              localNetMsgGuaranted.writeByte(paramInt);
            }
            localNetMsgGuaranted.writeInt(j);
            localNetMsgGuaranted.write255(this.sectFile.var(j, m));
            localNetMsgGuaranted.write255(this.sectFile.value(j, m));
          }
        }
      }
      if (localNetMsgGuaranted.size() > 1)
        this.net.postTo(paramNetChannel, localNetMsgGuaranted);
      localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(paramInt + 1);
      this.net.postTo(paramNetChannel, localNetMsgGuaranted);
    } catch (Exception localException) {
      printDebug(localException);
    }
  }

  public void replicateTimeofDay() {
    if (!isServer()) return; try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(11);
      localNetMsgGuaranted.writeFloat(World.getTimeofDay());
      this.net.post(localNetMsgGuaranted);
    }
    catch (Exception localException)
    {
    }
  }

  private boolean isExistFile(String paramString)
  {
    int i = 0;
    try {
      SFSInputStream localSFSInputStream = new SFSInputStream(paramString);
      localSFSInputStream.close();
      i = 1; } catch (Exception localException) {
    }
    return i;
  }

  private void netInput(NetMsgInput paramNetMsgInput) throws IOException {
    int i = 0;
    if (((this.net instanceof Master)) || (paramNetMsgInput.channel() != this.net.masterChannel()))
      i = 1;
    boolean bool = paramNetMsgInput.channel() instanceof NetChannelStream;
    NetMsgGuaranted localNetMsgGuaranted = null;
    int j = paramNetMsgInput.readUnsignedByte();
    int k;
    switch (j) {
    case 0:
      paramNetMsgInput.channel().userState = 2;
      localNetMsgGuaranted = new NetMsgGuaranted();
      Object localObject;
      if (i != 0) {
        if (bool) {
          localObject = new NetMsgGuaranted();
          ((NetMsgGuaranted)localObject).writeByte(13);
          ((NetMsgGuaranted)localObject).writeLong(Time.current());
          this.net.postTo(paramNetMsgInput.channel(), (NetMsgGuaranted)localObject);
        }
        localNetMsgGuaranted.writeByte(0);
        localNetMsgGuaranted.write255(this.name);
        localNetMsgGuaranted.writeLong(this.sectFinger);
      } else {
        this.name = paramNetMsgInput.read255();
        this.sectFinger = paramNetMsgInput.readLong();
        Main.cur().netMissionListener.netMissionState(0, 0.0F, this.name);

        if (!bool) {
          ((NetUser)NetEnv.host()).setMissProp("missions/" + this.name);
        }
        localObject = "missions/" + this.name;
        if ((!bool) && (isExistFile((String)localObject))) {
          this.sectFile = new SectFile((String)localObject, 0, false);
          if (this.sectFinger == this.sectFile.fingerExcludeSectPrefix("$$$")) {
            localNetMsgGuaranted.writeByte(3);
            break;
          }
        }

        localObject = "missions/Net/Cache/" + this.sectFinger + ".mis";
        int[] arrayOfInt = getSwTbl((String)localObject, this.sectFinger);
        this.sectFile = new SectFile((String)localObject, 0, false, arrayOfInt);
        if ((!bool) && (this.sectFinger == this.sectFile.fingerExcludeSectPrefix("$$$")))
        {
          localNetMsgGuaranted.writeByte(3);
        }
        else {
          this.sectFile = new SectFile((String)localObject, 1, false, arrayOfInt);
          this.sectFile.clear();
          localNetMsgGuaranted.writeByte(1);
        }
      }
      break;
    case 13:
      if (i != 0) {
        break;
      }
      long l = paramNetMsgInput.readLong();
      RTSConf.cur.time.setCurrent(l);
      NetMissionTrack.playingStartTime = l;

      break;
    case 1:
      if (i != 0) {
        doSendMission(paramNetMsgInput.channel(), 1);
      }
      else {
        Main.cur().netMissionListener.netMissionState(1, 0.0F, null);

        doResvMission(paramNetMsgInput);
      }
      break;
    case 2:
      if (i != 0) {
        break;
      }
      this.sectFile.saveFile();
      localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(3);

      break;
    case 3:
      if (i != 0) {
        k = this.actors.size();
        int m = 0;
        while (m < k) {
          localNetMsgGuaranted = new NetMsgGuaranted();
          localNetMsgGuaranted.writeByte(3);
          int n = 64;
          while ((n-- > 0) && (m < k)) {
            Actor localActor2 = (Actor)this.actors.get(m++);
            if (Actor.isValid(localActor2)) localNetMsgGuaranted.writeShort(localActor2.net.idLocal()); else
              localNetMsgGuaranted.writeShort(0);
          }
          this.net.postTo(paramNetMsgInput.channel(), localNetMsgGuaranted);
        }
        localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(4);
        paramNetMsgInput.channel().userState = 3;
      } else {
        Main.cur().netMissionListener.netMissionState(2, 0.0F, null);

        while (paramNetMsgInput.available() > 0)
          this.actors.add(new Integer(paramNetMsgInput.readUnsignedShort()));
      }
      break;
    case 4:
      if (i != 0) {
        if ((isDogfight()) || ((paramNetMsgInput.channel() instanceof NetChannelOutStream))) {
          World.cur().statics.netBridgeSync(paramNetMsgInput.channel());
          World.cur().statics.netHouseSync(paramNetMsgInput.channel());
        }
        for (k = 0; k < this.actors.size(); k++) {
          Actor localActor1 = (Actor)this.actors.get(k);
          if (!Actor.isValid(localActor1)) continue;
          try {
            NetChannel localNetChannel = paramNetMsgInput.channel();
            localNetChannel.setMirrored(localActor1.net);
            localActor1.netFirstUpdate(paramNetMsgInput.channel());
          } catch (Exception localException2) {
            printDebug(localException2);
          }
        }

        if (Actor.isValid(World.cur().houseManager))
          World.cur().houseManager.fullUpdateChannel(paramNetMsgInput.channel());
        localNetMsgGuaranted = new NetMsgGuaranted();
        if (isPlaying()) {
          localNetMsgGuaranted.writeByte(10);
          this.net.postTo(paramNetMsgInput.channel(), localNetMsgGuaranted);
          localNetMsgGuaranted = new NetMsgGuaranted();
          localNetMsgGuaranted.writeByte(11);
          localNetMsgGuaranted.writeFloat(World.getTimeofDay());
          this.net.postTo(paramNetMsgInput.channel(), localNetMsgGuaranted);
          localNetMsgGuaranted = null;
          doReplicateNotMissionActors(paramNetMsgInput.channel(), true);

          trySendMsgStart(paramNetMsgInput.channel());
        } else {
          localNetMsgGuaranted.writeByte(5);
          paramNetMsgInput.channel().userState = 4;
        }
      } else {
        paramNetMsgInput.channel().userState = 3;
        try {
          load(this.name, this.sectFile, true);
        }
        catch (Exception localException1)
        {
          printDebug(localException1);
          Main.cur().netMissionListener.netMissionState(4, 0.0F, localException1.getMessage());
        }

      }

    case 5:
    case 10:
      if ((i == 0) || (goto 1420) || 
        ((this.net instanceof Master)))
        break;
      if (paramNetMsgInput.channel() != this.net.masterChannel())
        break;
      if (this.net.isMirrored()) {
        localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(10);
        this.net.post(localNetMsgGuaranted);
        localNetMsgGuaranted = null;
      }
      doReplicateNotMissionActors(true);
      doReplicateNotMissionActors(paramNetMsgInput.channel(), true);
      doBegin();
      Main.cur().netMissionListener.netMissionState(6, 0.0F, null); break;
    case 11:
      if ((this.net instanceof Master))
        break;
      if (paramNetMsgInput.channel() != this.net.masterChannel()) break;
      float f = paramNetMsgInput.readFloat();
      World.setTimeofDay(f);
      World.land().cubeFullUpdate(); break;
    case 20:
      if ((this.net instanceof Master))
        break;
      if (paramNetMsgInput.channel() != this.net.masterChannel())
        break;
      Main.cur().netMissionListener.netMissionState(7, 0.0F, null);

      doReplicateNotMissionActors(false);
      doReplicateNotMissionActors(paramNetMsgInput.channel(), false);
      doEnd();
      if (!this.net.isMirrored()) break;
      localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(20);
      this.net.post(localNetMsgGuaranted);
      localNetMsgGuaranted = null; break;
    case 12:
      if ((this.net instanceof Master))
        break;
      if (paramNetMsgInput.channel() != this.net.masterChannel()) break;
      Main.cur().netMissionListener.netMissionState(9, 0.0F, null); break;
    case 6:
    case 7:
    case 8:
    case 9:
    case 14:
    case 15:
    case 16:
    case 17:
    case 18:
    case 19: } if ((localNetMsgGuaranted != null) && (localNetMsgGuaranted.size() > 0))
      this.net.postTo(paramNetMsgInput.channel(), localNetMsgGuaranted);
  }

  public void trySendMsgStart(Object paramObject) {
    if (isDestroyed()) return;
    NetChannel localNetChannel = (NetChannel)paramObject;
    if (localNetChannel.isDestroyed()) return;
    HashMapInt localHashMapInt = RTSConf.cur.netEnv.objects;
    HashMapIntEntry localHashMapIntEntry = null;
    Object localObject1;
    while ((localHashMapIntEntry = localHashMapInt.nextEntry(localHashMapIntEntry)) != null) {
      localObject1 = (NetObj)localHashMapIntEntry.getValue();
      if ((localObject1 == null) || (((NetObj)localObject1).isDestroyed()) || (((NetObj)localObject1).isCommon()) || (localNetChannel.isMirrored((NetObj)localObject1)) || (((NetObj)localObject1).masterChannel() == localNetChannel)) {
        continue;
      }
      if (((localNetChannel instanceof NetChannelOutStream)) && (
        ((localObject1 instanceof NetControl)) || (
        ((localObject1 instanceof NetUser)) && (((NetObj)localObject1).isMaster()) && (NetMissionTrack.isPlaying())))) {
        continue;
      }
      if (((localObject1 instanceof GameTrack)) && (((NetObj)localObject1).isMirror()))
        continue;
      Object localObject2 = ((NetObj)localObject1).superObj();
      if (((localObject2 instanceof Destroy)) && (((Destroy)localObject2).isDestroyed()))
        continue;
      new MsgInvokeMethod_Object("trySendMsgStart", localNetChannel).post(72, this, 0.0D);
      return;
    }
    try
    {
      localObject1 = new NetMsgGuaranted();
      ((NetMsgGuaranted)localObject1).writeByte(12);
      this.net.postTo(localNetChannel, (NetMsgGuaranted)localObject1);
    } catch (Exception localException) {
      printDebug(localException);
    }
  }

  private void createNetObject(NetChannel paramNetChannel, int paramInt)
  {
    setTime(true);
    if (paramNetChannel == null)
    {
      this.net = new Master(this);
      doReplicateNotMissionActors(false);
    }
    else {
      this.net = new Mirror(this, paramNetChannel, paramInt);
      doReplicateNotMissionActors(paramNetChannel, false);
    }
  }

  protected static void printDebug(Exception paramException)
  {
    System.out.println(paramException.getMessage());
    paramException.printStackTrace();
  }

  private int[] getSwTbl(String paramString, long paramLong)
  {
    int i = (int)paramLong;
    int j = Finger.Int(paramString);
    if (i < 0) i = -i;
    if (j < 0) j = -j;
    int k = (j + i / 7) % 16 + 15;
    int m = (j + i / 21) % Finger.kTable.length;
    if (k < 0)
      k = -k % 16;
    if (k < 10)
      k = 10;
    if (m < 0)
      m = -m % Finger.kTable.length;
    int[] arrayOfInt = new int[k];
    for (int n = 0; n < k; n++)
      arrayOfInt[n] = Finger.kTable[((m + n) % Finger.kTable.length)];
    return arrayOfInt;
  }

  static
  {
    Spawn.add(Mission.class, new SPAWN());
  }
  static class SPAWN implements NetSpawn {
    public void netSpawn(int paramInt, NetMsgInput paramNetMsgInput) {
      try {
        if (Main.cur().mission != null)
        {
          Main.cur().mission.destroy();
        }

        Mission localMission = new Mission();
        if (Mission.cur() != null)
          Mission.cur().destroy();
        localMission.clear();
        Main.cur().mission = localMission;
        localMission.createNetObject(paramNetMsgInput.channel(), paramInt);
        Main.cur().missionLoading = localMission; } catch (Exception localException) {
        Mission.printDebug(localException);
      }
    }
  }

  class Mirror extends Mission.NetMissionObj
  {
    public Mirror(Mission paramNetChannel, NetChannel paramInt, int arg4)
    {
      super(paramNetChannel, paramInt, i);
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(0);
        postTo(paramInt, localNetMsgGuaranted); } catch (Exception localException) {
        Mission.printDebug(localException);
      }
    }
  }

  class Master extends Mission.NetMissionObj
  {
    public Master(Mission arg2)
    {
      super(localObject);
      Mission.access$402(localObject, localObject.sectFile.fingerExcludeSectPrefix("$$$"));
    }
  }

  class NetMissionObj extends NetObj
    implements NetChannelCallbackStream
  {
    private void msgCallback(NetChannel paramNetChannel, int paramInt)
    {
      try
      {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted(1);
        localNetMsgGuaranted.writeByte(paramInt);
        NetMsgInput localNetMsgInput = new NetMsgInput();
        localNetMsgInput.setData(paramNetChannel, true, localNetMsgGuaranted.data(), 0, localNetMsgGuaranted.size());
        MsgNet.postReal(Time.currentReal(), this, localNetMsgInput); } catch (Exception localException) {
        printDebug(localException);
      }
    }
    public boolean netChannelCallback(NetChannelOutStream paramNetChannelOutStream, NetMsgGuaranted paramNetMsgGuaranted) {
      if ((paramNetMsgGuaranted instanceof NetMsgSpawn))
        msgCallback(paramNetChannelOutStream, 0);
      else if (!(paramNetMsgGuaranted instanceof NetMsgDestroy))
        try
        {
          NetMsgInput localNetMsgInput = new NetMsgInput();
          localNetMsgInput.setData(paramNetChannelOutStream, true, paramNetMsgGuaranted.data(), 0, paramNetMsgGuaranted.size());
          int i = localNetMsgInput.readUnsignedByte();
          switch (i) { case 0:
            msgCallback(paramNetChannelOutStream, 1); break;
          case 2:
            msgCallback(paramNetChannelOutStream, 3); break;
          case 4:
            msgCallback(paramNetChannelOutStream, 4);
            break;
          case 12:
            Main3D.cur3D().gameTrackRecord().startKeyRecord(paramNetMsgGuaranted);
            return false; }
        } catch (Exception localException) {
          printDebug(localException);
        }
      return true;
    }

    public boolean netChannelCallback(NetChannelInStream paramNetChannelInStream, NetMsgInput paramNetMsgInput) {
      try {
        int i = paramNetMsgInput.readUnsignedByte();
        if (i == 4) {
          paramNetChannelInStream.setPause(true);
        } else if (i == 12) {
          paramNetChannelInStream.setGameTime();
          if (Mission.isCoop()) {
            Main.cur().netServerParams.prepareHidenAircraft();
            Mission.doMissionStarting();
            Time.setPause(false);
          }
          Main3D.cur3D().gameTrackPlay().startKeyPlay();
        }
        paramNetMsgInput.reset(); } catch (Exception localException) {
        printDebug(localException);
      }return true;
    }
    public void netChannelCallback(NetChannelInStream paramNetChannelInStream, NetMsgGuaranted paramNetMsgGuaranted) {
      if ((!(paramNetMsgGuaranted instanceof NetMsgSpawn)) && (!(paramNetMsgGuaranted instanceof NetMsgDestroy)))
        try {
          NetMsgInput localNetMsgInput = new NetMsgInput();
          localNetMsgInput.setData(paramNetChannelInStream, true, paramNetMsgGuaranted.data(), 0, paramNetMsgGuaranted.size());
          int i = localNetMsgInput.readUnsignedByte();
          if (i == 4)
            paramNetChannelInStream.setPause(false);
        } catch (Exception localException) {
          printDebug(localException);
        }
    }

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      Mission localMission = (Mission)superObj();
      localMission.netInput(paramNetMsgInput);
      return true;
    }
    public void msgNetNewChannel(NetChannel paramNetChannel) {
      if (Main.cur().missionLoading != null)
        return;
      tryReplicate(paramNetChannel);
    }
    private void tryReplicate(NetChannel paramNetChannel) {
      if ((paramNetChannel.isReady()) && (paramNetChannel.isPublic()) && (paramNetChannel != this.masterChannel) && (!paramNetChannel.isMirrored(this)) && (paramNetChannel.userState == 1))
      {
        try
        {
          postTo(paramNetChannel, new NetMsgSpawn(this)); } catch (Exception localException) {
          printDebug(localException);
        }
      }
    }

    public NetMissionObj(Object arg2) {
      super();
    }
    public NetMissionObj(Object paramNetChannel, NetChannel paramInt, int arg4) {
      super(paramInt, i);
    }
  }

  static class WingTakeoffPos
  {
    public int x;
    public int y;

    public WingTakeoffPos(double paramDouble1, double paramDouble2)
    {
      this.x = ((int)(paramDouble1 / 100.0D) * 100);
      this.y = ((int)(paramDouble2 / 100.0D) * 100);
    }
    public boolean equals(Object paramObject) {
      if (paramObject == null) return false;
      if (!(paramObject instanceof WingTakeoffPos)) return false;
      WingTakeoffPos localWingTakeoffPos = (WingTakeoffPos)paramObject;
      return (this.x == localWingTakeoffPos.x) && (this.y == localWingTakeoffPos.y);
    }
    public int hashCode() { return this.x + this.y;
    }
  }

  class TimeOutWing
  {
    String wingName;

    public void start()
    {
      if (Mission.this.isDestroyed()) return; try
      {
        com.maddox.il2.objects.air.NetAircraft.loadingCoopPlane = false;
        Wing localWing = new Wing();
        localWing.load(Mission.this.sectFile, this.wingName, null);
        Mission.this.prepareSkinInWing(Mission.this.sectFile, localWing);
        localWing.setOnAirport();
      } catch (Exception localException) {
        Mission.printDebug(localException);
      }
    }

    public TimeOutWing(String arg2)
    {
      Object localObject;
      this.wingName = localObject;
    }
  }

  public class BackgroundLoader extends BackgroundTask
  {
    private String _name;
    private SectFile _in;

    public void run()
      throws Exception
    {
      Mission.this._load(this._name, this._in, true);
    }
    public BackgroundLoader(String paramSectFile, SectFile arg3) {
      this._name = paramSectFile;
      Object localObject;
      this._in = localObject;
    }
  }
}