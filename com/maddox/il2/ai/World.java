package com.maddox.il2.ai;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.air.Airdrome;
import com.maddox.il2.ai.air.NearestAircraft;
import com.maddox.il2.ai.ground.NearestEnemies;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorException;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.engine.hotkey.HookView;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Wind;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiAircraft;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.game.ZutiTimer_Refly;
import com.maddox.il2.game.order.OrdersTree;
import com.maddox.il2.gui.GUINetMission;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.ActorViewPoint;
import com.maddox.il2.objects.ScoreRegister;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.air.NetGunner;
import com.maddox.il2.objects.air.Paratrooper;
import com.maddox.il2.objects.air.Runaway;
import com.maddox.il2.objects.buildings.HouseManager;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.vehicles.planes.PlaneGeneric;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnvs;
import com.maddox.rts.IniFile;
import com.maddox.rts.NetEnv;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class World
{
  public static final float NORD = 270.0F;
  public static final float PIXEL = 200.0F;
  public static float MaxVisualDistance = 5000.0F;
  public static float MaxStaticVisualDistance = 4000.0F;
  public static float MaxLongVisualDistance = 10000.0F;
  public static float MaxPlateVisualDistance = 16000.0F;

  public boolean blockMorseChat = false;
  public boolean smallMapWPLabels = false;

  public RangeRandom rnd = new RangeRandom();

  public int camouflage = 0;
  public static final int CAMOUFLAGE_SUMMER = 0;
  public static final int CAMOUFLAGE_WINTER = 1;
  public static final int CAMOUFLAGE_DESERT = 2;
  public static final int CAMOUFLAGE_PACIFIC = 3;
  public static final int CAMOUFLAGE_ETO = 4;
  public static final int CAMOUFLAGE_MTO = 5;
  public static final int CAMOUFLAGE_CBI = 6;
  public DifficultySettings diffCur = new DifficultySettings();

  public DifficultySettings diffUser = new DifficultySettings();
  public UserCfg userCfg;
  public float userCoverMashineGun = 500.0F;

  public float userCoverCannon = 500.0F;

  public float userCoverRocket = 500.0F;

  public float userRocketDelay = 10.0F;

  public float userBombDelay = 0.0F;

  private boolean bArcade = false;

  private boolean bHighGore = false;

  private boolean bHakenAllowed = false;

  private boolean bDebugFM = false;

  private boolean bTimeOfDayConstant = false;

  private boolean bWeaponsConstant = false;
  protected War war;
  protected ArrayList airports;
  public ArrayList bornPlaces;
  public HouseManager houseManager;
  public Runaway runawayList;
  public Airdrome airdrome;
  private int missionArmy = 1;
  private Aircraft PlayerAircraft;
  private NetGunner PlayerGunner;
  private int PlayerArmy = 1;
  private FlightModel PlayerFM;
  private Regiment PlayerRegiment;
  private String PlayerLastCountry;
  private boolean bPlayerParatrooper = false;
  private boolean bPlayerDead = false;
  private boolean bPlayerCaptured = false;
  private boolean bPlayerRemoved = false;

  public static Actor remover = new Remover();

  static ClipFilter clipFilter = new ClipFilter();

  public TargetsGuard targetsGuard = new TargetsGuard();

  public ScoreCounter scoreCounter = new ScoreCounter();

  private Wind wind = new Wind();

  protected Front front = new Front();
  public Statics statics;
  private int startTimeofDay = 43200;

  public Atmosphere Atm = new Atmosphere();
  public float[] fogColor = { 0.53F, 0.64F, 0.8F, 1.0F };
  public float[] beachColor = { 0.6F, 0.6F, 0.6F };
  public float[] lodColor = { 0.7F, 0.7F, 0.7F };

  public ChiefManager ChiefMan = new ChiefManager();
  private Sun sun = new Sun();
  public Voice voicebase = new Voice();

  private BornPlace zutiCurrentBornPlace = null;

  public static RangeRandom Rnd()
  {
    return cur().rnd;
  }

  public void setCamouflage(String paramString)
  {
    if ("SUMMER".equalsIgnoreCase(paramString)) this.camouflage = 0;
    else if ("WINTER".equalsIgnoreCase(paramString)) this.camouflage = 1;
    else if ("DESERT".equalsIgnoreCase(paramString)) this.camouflage = 2;
    else if ("PACIFIC".equalsIgnoreCase(paramString)) this.camouflage = 3;
    else if ("ETO".equalsIgnoreCase(paramString)) this.camouflage = 4;
    else if ("MTO".equalsIgnoreCase(paramString)) this.camouflage = 5;
    else if ("CBI".equalsIgnoreCase(paramString)) this.camouflage = 6;
    else
      this.camouflage = 0;
  }

  public void setUserCovers()
  {
    this.userCoverMashineGun = this.userCfg.coverMashineGun;
    this.userCoverCannon = this.userCfg.coverCannon;
    this.userCoverRocket = this.userCfg.coverRocket;
    this.userRocketDelay = this.userCfg.rocketDelay;
    this.userBombDelay = this.userCfg.bombDelay;
  }

  public boolean isArcade() {
    return (Mission.isSingle()) && (this.bArcade) && (!NetMissionTrack.isPlaying());
  }
  public void setArcade(boolean paramBoolean) { this.bArcade = paramBoolean; }

  public boolean isHighGore() {
    return this.bHighGore;
  }

  public boolean isHakenAllowed() {
    return this.bHakenAllowed;
  }

  public boolean isDebugFM() {
    return this.bDebugFM;
  }

  public boolean isTimeOfDayConstant()
  {
    return this.bTimeOfDayConstant;
  }

  public void setTimeOfDayConstant(boolean paramBoolean) {
    this.bTimeOfDayConstant = paramBoolean;
  }

  public boolean isWeaponsConstant()
  {
    return this.bWeaponsConstant;
  }

  public void setWeaponsConstant(boolean paramBoolean) {
    this.bWeaponsConstant = paramBoolean;
  }

  public static void getAirports(List paramList)
  {
    if (cur().airports != null)
      paramList.addAll(cur().airports); 
  }

  public static int getMissionArmy() {
    return cur().missionArmy; } 
  public static void setMissionArmy(int paramInt) { cur().missionArmy = paramInt; } 
  public static Aircraft getPlayerAircraft() {
    return cur().PlayerAircraft; } 
  public static int getPlayerArmy() { return cur().PlayerArmy; } 
  public static FlightModel getPlayerFM() { return cur().PlayerFM; } 
  public static Regiment getPlayerRegiment() { return cur().PlayerRegiment; } 
  public static String getPlayerLastCountry() {
    Regiment localRegiment = getPlayerRegiment();
    if (localRegiment != null)
      cur().PlayerLastCountry = localRegiment.country();
    return cur().PlayerLastCountry;
  }
  public static boolean isPlayerGunner() { return Actor.isValid(cur().PlayerGunner); } 
  public static NetGunner getPlayerGunner() { return cur().PlayerGunner; } 
  public static boolean isPlayerParatrooper() {
    return cur().bPlayerParatrooper; } 
  public static boolean isPlayerDead() { return cur().bPlayerDead; } 
  public static boolean isPlayerCaptured() { return cur().bPlayerCaptured; } 
  public static boolean isPlayerRemoved() { return cur().bPlayerRemoved; }

  public static void setPlayerAircraft(Aircraft paramAircraft) {
    cur().PlayerAircraft = paramAircraft;
    if (paramAircraft != null) {
      cur().PlayerFM = paramAircraft.FM;
      cur().scoreCounter.playerStartAir(paramAircraft);
    } else {
      cur().PlayerFM = null;
    }
  }

  public static void setPlayerFM() {
    if (Actor.isValid(cur().PlayerAircraft))
      cur().PlayerFM = cur().PlayerAircraft.FM;
  }

  public static void setPlayerRegiment()
  {
    if (Actor.isValid(cur().PlayerAircraft)) {
      Aircraft localAircraft = cur().PlayerAircraft;
      if (localAircraft.getOwner() != null)
        cur().PlayerRegiment = ((Wing)localAircraft.getOwner()).regiment();
      else
        cur().PlayerRegiment = null;
      cur().PlayerArmy = localAircraft.getArmy();
      if (Mission.isSingle())
        cur().missionArmy = cur().PlayerArmy;
    }
  }

  public static void doPlayerParatrooper(Paratrooper paramParatrooper)
  {
    FlightModel localFlightModel = getPlayerFM();

    if (!isPlayerParatrooper())
    {
      if (Config.isUSE_RENDER())
        RTSConf.cur.hotKeyEnvs.endAllActiveCmd(false);
      cur().bPlayerParatrooper = true;
      if (ZutiAircraft.isPlaneLandedAndDamaged(localFlightModel))
        cur().scoreCounter.playerParatrooper();
      if (Config.isUSE_RENDER()) {
        if (Main3D.cur3D().viewActor() == getPlayerAircraft())
          Main3D.cur3D().setViewFlow10(paramParatrooper, false);
        Main3D.cur3D().ordersTree.unactivate();
        ForceFeedback.stopMission();
      }

    }

    if ((Main.cur().mission.zutiMisc_EnableReflyOnlyIfBailedOrDied) && (Mission.isDogfight()))
    {
      ZutiSupportMethods.ZUTI_KIA_DELAY_CLEARED = false;
      GUINetMission.setPlayerParatrooper(paramParatrooper);
    }
  }

  public static void doGunnerParatrooper(Paratrooper paramParatrooper)
  {
    if (isPlayerParatrooper()) return;
    if (Config.isUSE_RENDER())
      RTSConf.cur.hotKeyEnvs.endAllActiveCmd(false);
    cur().bPlayerParatrooper = true;
    cur().scoreCounter.playerParatrooper();
    if (Config.isUSE_RENDER()) {
      if (Main3D.cur3D().viewActor() == getPlayerAircraft())
        Main3D.cur3D().setViewFlow10(paramParatrooper, false);
      ForceFeedback.stopMission();

      if ((Main.cur().mission.zutiMisc_EnableReflyOnlyIfBailedOrDied) && (Mission.isDogfight()))
      {
        ZutiSupportMethods.ZUTI_KIA_DELAY_CLEARED = false;
        GUINetMission.setPlayerParatrooper(paramParatrooper);
      }
    }
  }

  public static void doPlayerUnderWater() {
    if ((Config.isUSE_RENDER()) && 
      (Main3D.cur3D().viewActor() == getPlayerAircraft()) && (!Main3D.cur3D().isViewOutside()))
    {
      Main3D.cur3D().setViewFlow10(getPlayerAircraft(), false);
    }
  }

  public static void setPlayerDead() {
    if (Config.isUSE_RENDER())
      RTSConf.cur.hotKeyEnvs.endAllActiveCmd(false);
    cur().bPlayerDead = true;
    cur().scoreCounter.playerDead();

    if ((Main.cur().mission.zutiMisc_EnableReflyOnlyIfBailedOrDied) && (Mission.isDogfight()))
    {
      if (getPlayerFM().Gears.nOfGearsOnGr < 3)
      {
        ZutiSupportMethods.ZUTI_KIA_COUNTER += 1;
      }

      ZutiSupportMethods.ZUTI_KIA_DELAY_CLEARED = false;
      float f = Main.cur().mission.zutiMisc_ReflyKIADelay + ZutiSupportMethods.ZUTI_KIA_COUNTER * Main.cur().mission.zutiMisc_ReflyKIADelayMultiplier;
      ZutiSupportMethods.setPlayerBanDuration(()f);
      GUINetMission.setReflyTimer(new ZutiTimer_Refly(f));
      System.out.println(((NetUser)NetEnv.host()).uniqueName() + " has died for " + ZutiSupportMethods.ZUTI_KIA_COUNTER + " times. Refly penalty is " + f + "s.");
      EventLog.type(true, ((NetUser)NetEnv.host()).uniqueName() + " has died for " + ZutiSupportMethods.ZUTI_KIA_COUNTER + " times. Refly penalty is " + f + "s.");
    }
  }

  public static void setPlayerCaptured()
  {
    cur().bPlayerCaptured = true;
    cur().scoreCounter.playerCaptured();
  }
  public static void setPlayerGunner(NetGunner paramNetGunner) {
    cur().PlayerGunner = paramNetGunner;
    cur().scoreCounter.playerStartGunner();
  }

  public static void onActorDied(Actor paramActor1, Actor paramActor2)
  {
    onActorDied(paramActor1, paramActor2, true);
  }

  public static void onActorDied(Actor paramActor1, Actor paramActor2, boolean paramBoolean) {
    if (paramActor1.getDiedFlag())
    {
      throw new ActorException("actor " + paramActor1.getClass() + ":" + paramActor1.name() + " alredy dead");
    }

    if ((paramActor1 instanceof PlaneGeneric))
    {
      cur().zutiManagePilotsBornPlacePlaneCounter((PlaneGeneric)paramActor1);
    }
    if (((paramActor1 instanceof NetAircraft)) && (Main.cur().netServerParams != null) && (Main.cur().netServerParams.isMaster()))
    {
      try
      {
        boolean bool1 = false;
        boolean bool2 = false;
        if (paramActor2 != null)
        {
          bool1 = paramActor1.name().equals(paramActor2.name());
          bool2 = paramActor2.name().equals("NONAME");
        }

        boolean bool3 = ZutiSupportMethods.isPlaneStationary(((NetAircraft)paramActor1).FM);

        if ((paramActor2 != null) && (!bool2) && (!bool1) && (!bool3)) {
          ZutiSupportMethods.managePilotBornPlacePlaneCounter((NetAircraft)paramActor1, false);
        }
        else
        {
          ZutiSupportMethods.managePilotBornPlacePlaneCounter((NetAircraft)paramActor1, true);
        }
      }
      catch (Exception localException)
      {
        System.out.println("onActorDied Exception: " + localException);
        System.out.println(localException.getMessage());
        localException.printStackTrace();
      }

    }

    if (!Mission.isDogfight()) {
      Voice.testTargDestr(paramActor1, paramActor2 == remover ? null : paramActor2);
    }
    trySendChatMsgDied(paramActor1, paramActor2 == remover ? paramActor1 : paramActor2);

    paramActor1.setDiedFlag(true);
    if ((paramActor2 == remover) && (paramActor1 == cur().PlayerAircraft))
      cur().bPlayerRemoved = true;
    if (paramBoolean)
      EventLog.onActorDied(paramActor1, paramActor2);
    Engine.cur.world.war.onActorDied(paramActor1, paramActor2 == remover ? null : paramActor2);
    Engine.cur.world.targetsGuard.checkActorDied(paramActor1);

    Object localObject = cur().PlayerAircraft;
    cur(); if (isPlayerGunner())
      localObject = cur().PlayerGunner;
    if ((paramActor1.getArmy() != 0) && (paramActor1 != localObject) && (localObject != null) && (paramActor2 == localObject)) {
      if (paramActor1.getArmy() != ((Actor)localObject).getArmy()) cur().scoreCounter.enemyDestroyed(paramActor1); else
        cur().scoreCounter.friendDestroyed(paramActor1);
    }
    if (paramActor1 == cur().PlayerAircraft) {
      cur().checkViewOnPlayerDied(paramActor1);
      if (Config.isUSE_RENDER()) {
        CmdEnv.top().exec("music RAND music/crash");
        ForceFeedback.stopMission();
      }

      if (paramActor2 != cur().PlayerAircraft) { cur(); if (!isPlayerParatrooper())
          cur().scoreCounter.playerDead(); }
    }
  }

  public void checkViewOnPlayerDied(Actor paramActor) {
    Point3d localPoint3d1 = new Point3d();
    Point3d localPoint3d2 = new Point3d();
    Point3d localPoint3d3 = new Point3d();
    localPoint3d1.set(paramActor.pos.getAbsPoint());
    localPoint3d2.set(paramActor.pos.getAbsPoint());
    localPoint3d1.z -= 40.0D;
    localPoint3d2.z += 40.0D;
    Actor localActor = Engine.collideEnv().getLine(localPoint3d2, localPoint3d1, false, clipFilter, localPoint3d3);
    if (Actor.isValid(localActor)) {
      if ((Config.isUSE_RENDER()) && 
        (Main3D.cur3D().viewActor() == paramActor)) {
        Main3D.cur3D().setViewFlow10(localActor, false);
      }

      return;
    }
    ActorViewPoint localActorViewPoint = new ActorViewPoint();
    localActorViewPoint.pos.setAbs(paramActor.pos.getAbs()); localActorViewPoint.pos.reset();
    localActorViewPoint.dreamFire(true);
    if ((Config.isUSE_RENDER()) && 
      (Main3D.cur3D().viewActor() == paramActor)) {
      Main3D.cur3D().hookView.set(localActorViewPoint, 3.0F * HookView.defaultLen(), 10.0F, -10.0F);
      Main3D.cur3D().setView(localActorViewPoint, true);
    }
  }

  public static void onTaskComplete(Actor paramActor)
  {
    if (paramActor.isTaskComplete()) return;

    paramActor.setTaskCompleteFlag(true);
    Engine.cur.world.targetsGuard.checkTaskComplete(paramActor);
    if (paramActor.isNetMaster())
      ((NetUser)NetEnv.host()).postTaskComplete(paramActor);
  }

  private static void trySendChatMsgDied(Actor paramActor1, Actor paramActor2) {
    if (!Actor.isValid(paramActor2)) return;
    if (Mission.isSingle()) return;

    if (Main.cur().chat == null) return;
    if (!(paramActor1 instanceof Aircraft)) return;
    if (paramActor1.net == null) return;
    if (!paramActor1.net.isMaster()) return;
    Aircraft localAircraft = (Aircraft)paramActor1;
    NetUser localNetUser = localAircraft.netUser();
    if (localNetUser == null) return;
    if (paramActor1 == paramActor2)
    {
      return;
    }
    int i = Engine.cur.world.scoreCounter.getRegisteredType(paramActor2);
    if (!localAircraft.FM.isSentBuryNote())
    {
      localAircraft.FM.setSentBuryNote(true);
      switch (i) {
      case 0:
        if (((paramActor2 instanceof Aircraft)) && (((Aircraft)paramActor2).netUser() != null)) {
          Chat.sendLog(1, "gore_kill" + Rnd().nextInt(1, 5), (Aircraft)paramActor2, localAircraft);
          if ((!localAircraft.FM.isWasAirborne()) && (localAircraft.isDamagerExclusive()))
            Chat.sendLogRnd(2, "gore_vulcher", (Aircraft)paramActor2, null);
        } else {
          Chat.sendLogRnd(1, "gore_ai", localAircraft, null);
        }
        return;
      case 1:
        Chat.sendLogRnd(2, "gore_tank", localAircraft, null);
        return;
      case 2:
        Chat.sendLogRnd(2, "gore_gun", localAircraft, null);
        return;
      case 3:
        Chat.sendLogRnd(2, "gore_gun", localAircraft, null);
        return;
      case 4:
        Chat.sendLogRnd(2, "gore_killaaa", localAircraft, null);
        return;
      case 7:
        Chat.sendLogRnd(2, "gore_ship", localAircraft, null);
        return;
      case 6:
        Chat.sendLogRnd(2, "gore_killaaa", localAircraft, null);
        return;
      case 5:
      case 8:
      case 9:
      }

      Chat.sendLogRnd(1, "gore_crashes", localAircraft, null);
    }
  }

  public static Landscape land()
  {
    return Engine.land();
  }

  public static Wind wind() {
    return cur().wind;
  }

  public static World cur()
  {
    return Engine.cur.world;
  }
  public void resetGameClear() {
    EventLog.resetGameClear();
    this.front.resetGameClear();
    this.war.resetGameClear();
    this.bTimeOfDayConstant = false;
    if (this.statics != null)
      this.statics.resetGame();
    if (this.airports != null) {
      for (int i = 0; i < this.airports.size(); i++)
        ((Airport)this.airports.get(i)).destroy();
      this.airports.clear();
      this.airports = null;
    }
    while (this.runawayList != null) {
      Runaway localRunaway = this.runawayList;
      this.runawayList = localRunaway.next();
      if (Actor.isValid(localRunaway))
        localRunaway.destroy();
    }
    this.targetsGuard.resetGame();
    this.scoreCounter.resetGame();
    NearestEnemies.resetGameClear();
    NearestAircraft.resetGameClear();
    Aircraft.resetGameClear();
    MsgExplosion.resetGame();
    MsgShot.resetGame();
    Regiment.resetGame();
    this.bornPlaces = null;
    this.bPlayerParatrooper = false;
    this.bPlayerDead = false;
    this.bPlayerCaptured = false;
    this.bPlayerRemoved = false;
    if (Actor.isValid(this.houseManager))
      this.houseManager.destroy();
    this.houseManager = null;
  }

  public void resetGameCreate() {
    if (this.statics == null)
      this.statics = new Statics();
    this.ChiefMan = new ChiefManager();
    setPlayerAircraft(null);
    this.PlayerArmy = 1;
    this.rnd = new RangeRandom();
    this.voicebase = new Voice();
    setTimeofDay(12.0F);
    this.airports = new ArrayList();
    this.war.resetGameCreate();
    this.front.resetGameCreate();
    EventLog.resetGameCreate();
  }

  public void resetUser() {
    this.bPlayerParatrooper = false;
    this.bPlayerDead = false;

    ZutiSupportMethods.ZUTI_KIA_DELAY_CLEARED = false;
  }

  public World()
  {
    this.war = new War();
    this.bArcade = Config.cur.ini.get("game", "Arcade", this.bArcade);
    if (Config.LOCALE.equals("RU")) {
      this.bHighGore = Config.cur.ini.get("game", "HighGore", this.bHighGore);
      this.bHakenAllowed = Config.cur.ini.get("game", "HakenAllowed", this.bHakenAllowed);
    }

    this.blockMorseChat = Config.cur.ini.get("game", "BlockMorseChat", this.blockMorseChat);
    this.smallMapWPLabels = Config.cur.ini.get("game", "SmallMapWPLabels", this.smallMapWPLabels);
  }

  public static float getTimeofDay()
  {
    if (cur().bTimeOfDayConstant) {
      return cur().startTimeofDay * 0.0002777778F % 24.0F;
    }
    return (float)(Time.current() / 1000L + cur().startTimeofDay) * 0.0002777778F % 24.0F;
  }

  public static void setTimeofDay(float paramFloat) {
    int i = (int)(paramFloat * 3600.0F % 86400.0F);
    if (cur().bTimeOfDayConstant)
      cur().startTimeofDay = i;
    else
      cur().startTimeofDay = (i - (int)(Time.current() / 1000L)); 
  }

  public static float g() {
    return Atmosphere.g(); } 
  public static Sun Sun() { return cur().sun; } 
  public Sun sun() {
    return this.sun;
  }

  private void zutiManagePilotsBornPlacePlaneCounter(PlaneGeneric paramPlaneGeneric)
  {
    String str = ZutiAircraft.getStaticAcNameFromActor(paramPlaneGeneric.toString());
    Point3d localPoint3d = paramPlaneGeneric.pos.getAbsPoint();
    double d1 = localPoint3d.x;
    double d2 = localPoint3d.y;
    try
    {
      int i = 0;
      if (this.zutiCurrentBornPlace != null)
      {
        double d3 = Math.sqrt(Math.pow(this.zutiCurrentBornPlace.place.x - d1, 2.0D) + Math.pow(this.zutiCurrentBornPlace.place.y - d2, 2.0D));
        if (d3 <= this.zutiCurrentBornPlace.r)
        {
          this.zutiCurrentBornPlace.zutiReleaseAircraft(null, str, false, true, false);

          i = 1;
        }
      }
      if ((i == 0) && (this.bornPlaces != null))
      {
        BornPlace localBornPlace = null;
        for (int j = 0; j < this.bornPlaces.size(); j++)
        {
          localBornPlace = (BornPlace)this.bornPlaces.get(j);

          if (!localBornPlace.zutiIncludeStaticPlanes)
          {
            continue;
          }
          double d4 = Math.pow(localBornPlace.place.x - d1, 2.0D) + Math.pow(localBornPlace.place.y - d2, 2.0D);
          if (d4 > localBornPlace.r * localBornPlace.r) {
            continue;
          }
          localBornPlace.zutiReleaseAircraft(null, str, false, true, false);

          this.zutiCurrentBornPlace = localBornPlace;
        }
      }
    }
    catch (Exception localException) {
      localException.printStackTrace();
    }
  }

  static
  {
    ScoreRegister.load();
  }

  static class ClipFilter
    implements ActorFilter
  {
    public boolean isUse(Actor paramActor, double paramDouble)
    {
      return paramActor instanceof BigshipGeneric;
    }
  }

  static class Remover extends Actor
  {
    protected void createActorHashCode()
    {
      makeActorRealHashCode();
    }
  }
}