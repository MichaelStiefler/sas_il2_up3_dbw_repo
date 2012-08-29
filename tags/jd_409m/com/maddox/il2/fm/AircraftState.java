package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.air.AR_234B2;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.Cockpit;
import com.maddox.il2.objects.air.DO_335;
import com.maddox.il2.objects.air.DO_335A0;
import com.maddox.il2.objects.air.DO_335V13;
import com.maddox.il2.objects.air.FW_190A8MSTL;
import com.maddox.il2.objects.air.GO_229;
import com.maddox.il2.objects.air.HE_162;
import com.maddox.il2.objects.air.ME_262HGII;
import com.maddox.il2.objects.air.P_39;
import com.maddox.il2.objects.air.Paratrooper;
import com.maddox.il2.objects.air.Scheme0;
import com.maddox.il2.objects.air.Scheme1;
import com.maddox.il2.objects.air.TypeBomber;
import com.maddox.il2.objects.air.TypeDockable;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.MsgDestroy;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;
import java.io.IOException;
import java.io.PrintStream;

public class AircraftState
{
  private static final float astateEffectCriticalSpeed = 10.0F;
  private static final float astateCondensateCriticalAlt = 7000.0F;
  private static final Loc astateCondensateDispVector = new Loc(15.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
  public static final int _AS_RESERVED = 0;
  public static final int _AS_ENGINE_STATE = 1;
  public static final int _AS_ENGINE_SPECIFIC_DMG = 2;
  public static final int _AS_ENGINE_EXPLODES = 3;
  public static final int _AS_ENGINE_STARTS = 4;
  public static final int _AS_ENGINE_RUNS = 5;
  public static final int _AS_ENGINE_STOPS = 6;
  public static final int _AS_ENGINE_DIES = 7;
  public static final int _AS_ENGINE_SOOT_POWERS = 8;
  public static final int _AS_ENGINE_READYNESS = 25;
  public static final int _AS_ENGINE_STAGE = 26;
  public static final int _AS_ENGINE_CYL_KNOCKOUT = 27;
  public static final int _AS_ENGINE_MAG_KNOCKOUT = 28;
  public static final int _AS_ENGINE_STUCK = 29;
  public static final int _AS_TANK_STATE = 9;
  public static final int _AS_TANK_EXPLODES = 10;
  public static final int _AS_OIL_STATE = 11;
  public static final int _AS_GLIDER_BOOSTER = 12;
  public static final int _AS_GLIDER_BOOSTOFF = 13;
  public static final int _AS_GLIDER_CUTCART = 14;
  public static final int _AS_AIRSHOW_SMOKES_STATE = 15;
  public static final int _AS_WINGTIP_SMOKES_STATE = 16;
  public static final int _AS_PILOT_STATE = 17;
  public static final int _AS_KILLPILOT = 18;
  public static final int _AS_HEADSHOT = 19;
  public static final int _AS_BAILOUT = 20;
  public static final int _AS_CONTROLS_HURT = 21;
  public static final int _AS_INTERNALS_HURT = 22;
  public static final int _AS_COCKPIT_STATE_BYTE = 23;
  public static final int _AS_JAM_BULLETS = 24;
  public static final int _AS_NAVIGATION_LIGHTS_STATE = 30;
  public static final int _AS_LANDING_LIGHT_STATE = 31;
  public static final int _AS_TYPEDOCKABLE_REQ_ATTACHTODRONE = 32;
  public static final int _AS_TYPEDOCKABLE_REQ_DETACHFROMDRONE = 33;
  public static final int _AS_TYPEDOCKABLE_FORCE_ATTACHTODRONE = 34;
  public static final int _AS_TYPEDOCKABLE_FORCE_DETACHFROMDRONE = 35;
  public static final int _AS_FLATTOP_FORCESTRING = 36;
  public static final int _AS_FMSFX = 37;
  public static final int _AS_WINGFOLD = 38;
  public static final int _AS_COCKPITDOOR = 39;
  public static final int _AS_ARRESTOR = 40;
  public static final int _AS_COUNT_CODES = 41;
  public static final int _AS_COCKPIT_GLASS = 1;
  public static final int _AS_COCKPIT_ARMORGLASS = 2;
  public static final int _AS_COCKPIT_LEFT1 = 4;
  public static final int _AS_COCKPIT_LEFT2 = 8;
  public static final int _AS_COCKPIT_RIGHT1 = 16;
  public static final int _AS_COCKPIT_RIGHT2 = 32;
  public static final int _AS_COCKPIT_INSTRUMENTS = 64;
  public static final int _AS_COCKPIT_OIL = 128;
  public static final int _ENGINE_SPECIFIC_BOOSTER = 0;
  public static final int _ENGINE_SPECIFIC_THROTTLECTRL = 1;
  public static final int _ENGINE_SPECIFIC_HEATER = 2;
  public static final int _ENGINE_SPECIFIC_ANGLER = 3;
  public static final int _ENGINE_SPECIFIC_ANGLERSPEEDS = 4;
  public static final int _ENGINE_SPECIFIC_EXTINGUISHER = 5;
  public static final int _ENGINE_SPECIFIC_PROPCTRL = 6;
  public static final int _ENGINE_SPECIFIC_MIXCTRL = 7;
  public static final int _CONTROLS_AILERONS = 0;
  public static final int _CONTROLS_ELEVATORS = 1;
  public static final int _CONTROLS_RUDDERS = 2;
  public static final int _INTERNALS_HYDRO_OFFLINE = 0;
  public static final int _INTERNALS_PNEUMO_OFFLINE = 1;
  public static final int _INTERNALS_MW50_OFFLINE = 2;
  public static final int _INTERNALS_GEAR_STUCK = 3;
  public static final int _INTERNALS_KEEL_SHUTOFF = 4;
  public static final int _INTERNALS_SHAFT_SHUTOFF = 5;
  private static final String[] astateOilStrings = { "3DO/Effects/Aircraft/BlackMediumSPD.eff", "3DO/Effects/Aircraft/BlackMediumTSPD.eff", null, null };

  private static final String[] astateTankStrings = { null, null, null, "3DO/Effects/Aircraft/RedLeakTSPD.eff", null, null, "3DO/Effects/Aircraft/RedLeakTSPD.eff", null, null, "3DO/Effects/Aircraft/BlackMediumSPD.eff", "3DO/Effects/Aircraft/BlackMediumTSPD.eff", null, "3DO/Effects/Aircraft/BlackHeavySPD.eff", "3DO/Effects/Aircraft/BlackHeavyTSPD.eff", null, "3DO/Effects/Aircraft/FireSPD.eff", "3DO/Effects/Aircraft/BlackHeavySPD.eff", "3DO/Effects/Aircraft/BlackHeavyTSPD.eff", "3DO/Effects/Aircraft/FireSPD.eff", "3DO/Effects/Aircraft/BlackHeavySPD.eff", "3DO/Effects/Aircraft/BlackHeavyTSPD.eff", null, null, null, "3DO/Effects/Aircraft/RedLeakGND.eff", null, null, "3DO/Effects/Aircraft/RedLeakGND.eff", null, null, "3DO/Effects/Aircraft/BlackMediumGND.eff", null, null, "3DO/Effects/Aircraft/BlackHeavyGND.eff", null, null, "3DO/Effects/Aircraft/FireGND.eff", "3DO/Effects/Aircraft/BlackHeavyGND.eff", null, "3DO/Effects/Aircraft/FireGND.eff", "3DO/Effects/Aircraft/BlackHeavyGND.eff", null };

  private static final String[] astateEngineStrings = { null, null, null, "3DO/Effects/Aircraft/GraySmallSPD.eff", "3DO/Effects/Aircraft/GraySmallTSPD.eff", null, "3DO/Effects/Aircraft/BlackMediumSPD.eff", "3DO/Effects/Aircraft/BlackMediumTSPD.eff", null, "3DO/Effects/Aircraft/BlackHeavySPD.eff", "3DO/Effects/Aircraft/BlackHeavyTSPD.eff", null, "3DO/Effects/Aircraft/FireSPD.eff", "3DO/Effects/Aircraft/BlackHeavySPD.eff", "3DO/Effects/Aircraft/BlackHeavyTSPD.eff", null, null, null, "3DO/Effects/Aircraft/GraySmallGND.eff", null, null, "3DO/Effects/Aircraft/BlackMediumGND.eff", null, null, "3DO/Effects/Aircraft/BlackHeavyGND.eff", null, null, "3DO/Effects/Aircraft/FireGND.eff", "3DO/Effects/Aircraft/BlackHeavyGND.eff", null };

  private static final String[] astateCondensateStrings = { null, "3DO/Effects/Aircraft/CondensateTSPD.eff" };

  private static final String[] astateStallStrings = { null, "3DO/Effects/Aircraft/StallTSPD.eff" };

  public static final String[] astateHUDPilotHits = { "Player", "Pilot", "CPilot", "NGunner", "TGunner", "WGunner", "VGunner", "RGunner", "EngMas", "BombMas", "RadMas", "ObsMas" };

  private static boolean bCriticalStatePassed = false;
  private boolean bIsAboveCriticalSpeed;
  private boolean bIsAboveCondensateAlt;
  private boolean[] bIsBeyondSootPower;
  private boolean bIsOnInadequateAOA;
  public boolean bShowSmokesOn;
  public boolean bNavLightsOn;
  public boolean bLandingLightOn;
  public boolean bWingTipLExists;
  public boolean bWingTipRExists;
  private boolean bIsMaster;
  public Actor actor;
  public Aircraft aircraft;
  public byte[] astatePilotStates;
  public byte[] astatePilotFunctions;
  public int astatePlayerIndex;
  public boolean bIsAboutToBailout;
  public boolean bIsEnableToBailout;
  public byte astateBailoutStep;
  public int astateCockpitState;
  public byte[] astateOilStates;
  private Eff3DActor[][] astateOilEffects;
  public byte[] astateTankStates;
  private Eff3DActor[][] astateTankEffects;
  public byte[] astateEngineStates;
  private Eff3DActor[][] astateEngineEffects;
  public byte[] astateSootStates;
  public Eff3DActor[][] astateSootEffects;
  private Eff3DActor[] astateCondensateEffects;
  private Eff3DActor[] astateStallEffects;
  private Eff3DActor[] astateAirShowEffects;
  private Eff3DActor[] astateNavLightsEffects;
  private LightPointActor[] astateNavLightsLights;
  public Eff3DActor[] astateLandingLightEffects;
  private LightPointActor[] astateLandingLightLights;
  public String[] astateEffectChunks;
  public static final int astateEffectsDispTanks = 0;
  public static final int astateEffectsDispEngines = 4;
  public static final int astateEffectsDispLights = 12;
  public static final int astateEffectsDispLandingLights = 18;
  public static final int astateEffectsDispOilfilters = 22;
  public static boolean bCheckPlayerAircraft = true;
  private Item[] itemsToMaster;
  private Item[] itemsToMirrors;

  public AircraftState()
  {
    this.bIsAboveCriticalSpeed = false;

    this.bIsAboveCondensateAlt = false;

    this.bIsBeyondSootPower = new boolean[] { false, false, false, false };

    this.bIsOnInadequateAOA = false;
    this.bShowSmokesOn = false;
    this.bNavLightsOn = false;
    this.bLandingLightOn = false;
    this.bWingTipLExists = true;
    this.bWingTipRExists = true;

    this.actor = null;
    this.aircraft = null;

    this.astatePilotStates = new byte[9];
    this.astatePilotFunctions = new byte[] { 1, 7, 7, 7, 7, 7, 7, 7, 7 };

    this.astatePlayerIndex = 0;
    this.bIsAboutToBailout = false;
    this.bIsEnableToBailout = true;
    this.astateBailoutStep = 0;
    this.astateCockpitState = 0;

    this.astateOilStates = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 };
    this.astateOilEffects = new Eff3DActor[][] { { null, null }, { null, null }, { null, null }, { null, null }, { null, null }, { null, null }, { null, null }, { null, null } };

    this.astateTankStates = new byte[] { 0, 0, 0, 0 };
    this.astateTankEffects = new Eff3DActor[][] { { null, null, null }, { null, null, null }, { null, null, null }, { null, null, null } };

    this.astateEngineStates = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 };
    this.astateEngineEffects = new Eff3DActor[][] { { null, null, null }, { null, null, null }, { null, null, null }, { null, null, null }, { null, null, null }, { null, null, null }, { null, null, null }, { null, null, null } };

    this.astateSootStates = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 };
    this.astateSootEffects = new Eff3DActor[][] { { null, null }, { null, null }, { null, null }, { null, null }, { null, null }, { null, null }, { null, null }, { null, null } };

    this.astateCondensateEffects = new Eff3DActor[] { null, null, null, null, null, null, null, null };

    this.astateStallEffects = new Eff3DActor[] { null, null };

    this.astateAirShowEffects = new Eff3DActor[] { null, null };

    this.astateNavLightsEffects = new Eff3DActor[] { null, null, null, null, null, null };

    this.astateNavLightsLights = new LightPointActor[] { null, null, null, null, null, null };

    this.astateLandingLightEffects = new Eff3DActor[] { null, null, null, null };

    this.astateLandingLightLights = new LightPointActor[] { null, null, null, null };

    this.astateEffectChunks = new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null };

    this.itemsToMaster = null;
    this.itemsToMirrors = null;
  }

  public void set(Actor paramActor, boolean paramBoolean)
  {
    Loc localLoc1 = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    Loc localLoc2 = new Loc();

    this.actor = paramActor;
    if ((paramActor instanceof Aircraft))
      this.aircraft = ((Aircraft)paramActor);
    else
      throw new RuntimeException("Can not cast aircraft structure into a non-aircraft entity.");
    this.bIsMaster = paramBoolean;

    for (int i = 0; i < 4; i++)
      try {
        this.astateEffectChunks[(i + 0)] = this.actor.findHook("_Tank" + (i + 1) + "Burn").chunkName();
        this.astateEffectChunks[(i + 0)] = this.astateEffectChunks[(i + 0)].substring(0, this.astateEffectChunks[(i + 0)].length() - 1);
        Aircraft.debugprintln(this.aircraft, "AS: Tank " + i + " FX attached to '" + this.astateEffectChunks[(i + 0)] + "' substring.."); } catch (Exception localException1) {
      } finally {
      }
    for (i = 0; i < this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum(); i++)
      try {
        this.astateEffectChunks[(i + 4)] = this.actor.findHook("_Engine" + (i + 1) + "Smoke").chunkName();
        this.astateEffectChunks[(i + 4)] = this.astateEffectChunks[(i + 4)].substring(0, this.astateEffectChunks[(i + 4)].length() - 1);
        Aircraft.debugprintln(this.aircraft, "AS: Engine " + i + " FX attached to '" + this.astateEffectChunks[(i + 4)] + "' substring..");
      }
      catch (Exception localException2)
      {
      }
      finally
      {
      }
    Point3d localPoint3d;
    for (i = 0; i < this.astateNavLightsEffects.length; i++)
      try {
        this.astateEffectChunks[(i + 12)] = this.actor.findHook("_NavLight" + i).chunkName();
        this.astateEffectChunks[(i + 12)] = this.astateEffectChunks[(i + 12)].substring(0, this.astateEffectChunks[(i + 12)].length() - 1);
        Aircraft.debugprintln(this.aircraft, "AS: Nav. Lamp #" + i + " attached to '" + this.astateEffectChunks[(i + 12)] + "' substring..");

        HookNamed localHookNamed1 = new HookNamed(this.aircraft, "_NavLight" + i);
        localLoc2.set(1.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        localHookNamed1.computePos(this.actor, localLoc1, localLoc2);
        localPoint3d = localLoc2.getPoint();
        this.astateNavLightsLights[i] = new LightPointActor(new LightPoint(), localPoint3d);
        if (i < 2)
          this.astateNavLightsLights[i].light.setColor(1.0F, 0.1F, 0.1F);
        else if (i < 4)
          this.astateNavLightsLights[i].light.setColor(0.0F, 1.0F, 0.0F);
        else {
          this.astateNavLightsLights[i].light.setColor(0.7F, 0.7F, 0.7F);
        }
        this.astateNavLightsLights[i].light.setEmit(0.0F, 0.0F);
        this.actor.draw.lightMap().put("_NavLight" + i, this.astateNavLightsLights[i]);
      } catch (Exception localException3) {
      } finally {
      }
    for (i = 0; i < 4; i++)
      try {
        this.astateEffectChunks[(i + 18)] = this.actor.findHook("_LandingLight0" + i).chunkName();
        this.astateEffectChunks[(i + 18)] = this.astateEffectChunks[(i + 18)].substring(0, this.astateEffectChunks[(i + 18)].length() - 1);
        Aircraft.debugprintln(this.aircraft, "AS: Landing Lamp #" + i + " attached to '" + this.astateEffectChunks[(i + 18)] + "' substring..");

        HookNamed localHookNamed2 = new HookNamed(this.aircraft, "_LandingLight0" + i);
        localLoc2.set(1.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        localHookNamed2.computePos(this.actor, localLoc1, localLoc2);
        localPoint3d = localLoc2.getPoint();
        this.astateLandingLightLights[i] = new LightPointActor(new LightPoint(), localPoint3d);
        this.astateLandingLightLights[i].light.setColor(0.4941177F, 0.9098039F, 0.9607843F);
        this.astateLandingLightLights[i].light.setEmit(0.0F, 0.0F);
        this.actor.draw.lightMap().put("_LandingLight0" + i, this.astateLandingLightLights[i]);
      } catch (Exception localException4) {
      } finally {
      }
    for (i = 0; i < this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum(); i++)
      try {
        this.astateEffectChunks[(i + 22)] = this.actor.findHook("_Engine" + (i + 1) + "Oil").chunkName();
        this.astateEffectChunks[(i + 22)] = this.astateEffectChunks[(i + 22)].substring(0, this.astateEffectChunks[(i + 22)].length() - 1);
        Aircraft.debugprintln(this.aircraft, "AS: Oilfilter " + i + " FX attached to '" + this.astateEffectChunks[(i + 22)] + "' substring.."); } catch (Exception localException5) {
      } finally {
      }
    for (i = 0; i < this.astateEffectChunks.length; i++) {
      if (this.astateEffectChunks[i] != null) continue; this.astateEffectChunks[i] = "AChunkNameYouCanNeverFind";
    }
  }

  public boolean isMaster()
  {
    return this.bIsMaster;
  }

  public void setOilState(Actor paramActor, int paramInt1, int paramInt2)
  {
    if (!Actor.isValid(paramActor)) return;
    if ((paramInt2 < 0) || (paramInt2 > 1) || (this.astateOilStates[paramInt1] == paramInt2)) return;
    if (this.bIsMaster) {
      this.aircraft.setDamager(paramActor);
      doSetOilState(paramInt1, paramInt2);
      int i = 0;
      for (int j = 0; j < this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum(); j++) {
        if (this.astateOilStates[j] == 1) {
          i++;
        }
      }
      if (i == this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum()) {
        setCockpitState(this.actor, this.astateCockpitState | 0x80);
      }

      netToMirrors(11, paramInt1, paramInt2);
    }
    else {
      netToMaster(11, paramInt1, paramInt2, paramActor);
    }
  }

  public void hitOil(Actor paramActor, int paramInt)
  {
    if (this.astateOilStates[paramInt] > 0) return;
    if (this.astateOilStates[paramInt] < 1)
      setOilState(paramActor, paramInt, this.astateOilStates[paramInt] + 1);
  }

  public void repairOil(int paramInt)
  {
    if (!this.bIsMaster) return;
    if (this.astateOilStates[paramInt] > 0) setOilState(this.actor, paramInt, this.astateOilStates[paramInt] - 1);
  }

  private void doSetOilState(int paramInt1, int paramInt2)
  {
    if (this.astateOilStates[paramInt1] == paramInt2) return;

    Aircraft.debugprintln(this.aircraft, "AS: Checking '" + this.astateEffectChunks[(paramInt1 + 22)] + "' visibility..");
    boolean bool = this.aircraft.isChunkAnyDamageVisible(this.astateEffectChunks[(paramInt1 + 22)]);
    Aircraft.debugprintln(this.aircraft, "AS: '" + this.astateEffectChunks[(paramInt1 + 22)] + "' is " + (bool ? "visible" : "invisible") + "..");
    Aircraft.debugprintln(this.aircraft, "Stating OilFilter " + paramInt1 + " to state " + paramInt2 + (bool ? ".." : " rejected (missing part).."));
    if (!bool) {
      return;
    }

    Aircraft.debugprintln(this.aircraft, "Stating OilFilter " + paramInt1 + " to state " + paramInt2 + "..");
    this.astateOilStates[paramInt1] = (byte)paramInt2;
    int i = 0; if (!this.bIsAboveCriticalSpeed) i = 2;

    if (this.astateOilEffects[paramInt1][0] != null) Eff3DActor.finish(this.astateOilEffects[paramInt1][0]);
    this.astateOilEffects[paramInt1][0] = null;
    if (this.astateOilEffects[paramInt1][1] != null) Eff3DActor.finish(this.astateOilEffects[paramInt1][1]);
    this.astateOilEffects[paramInt1][1] = null;
    switch (this.astateOilStates[paramInt1]) {
    case 1:
      String str = astateOilStrings[i];
      if (str != null)
        try {
          this.astateOilEffects[paramInt1][0] = Eff3DActor.New(this.actor, this.actor.findHook("_Engine" + (paramInt1 + 1) + "Oil"), null, 1.0F, str, -1.0F);
        } catch (Exception localException1) {
        }
      str = astateOilStrings[(i + 1)];
      if (str != null)
        try {
          this.astateOilEffects[paramInt1][1] = Eff3DActor.New(this.actor, this.actor.findHook("_Engine" + (paramInt1 + 1) + "Oil"), null, 1.0F, str, -1.0F);
        } catch (Exception localException2) {
        }
      if (World.Rnd().nextFloat() >= 0.25F) break; this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setReadyToReturn(true);
    }
  }

  public void changeOilEffectBase(int paramInt, Actor paramActor)
  {
    for (int i = 0; i < 2; i++)
      if (this.astateOilEffects[paramInt][i] != null)
        this.astateOilEffects[paramInt][i].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.changeBase(paramActor, null, true);
  }

  public void setTankState(Actor paramActor, int paramInt1, int paramInt2)
  {
    if (!Actor.isValid(paramActor)) return;
    if ((paramInt2 < 0) || (paramInt2 > 6) || (this.astateTankStates[paramInt1] == paramInt2)) return;
    if (this.bIsMaster) {
      int i = this.astateTankStates[paramInt1];
      if (!doSetTankState(paramActor, paramInt1, paramInt2)) return;
      for (int j = i; j < paramInt2; j++) {
        if (j % 2 == 0) this.aircraft.setDamager(paramActor);
        doHitTank(paramActor, paramInt1);
      }
      if ((this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && (paramActor != this.actor) && ((paramActor instanceof Aircraft)) && (((Aircraft)paramActor).isNetPlayer()) && (paramInt2 > 5) && (this.astateTankStates[0] < 5) && (this.astateTankStates[1] < 5) && (this.astateTankStates[2] < 5) && (this.astateTankStates[3] < 5) && 
        (!this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isSentBuryNote())) {
        Chat.sendLogRnd(3, "gore_lightfuel", (Aircraft)paramActor, this.aircraft);
      }

      netToMirrors(9, paramInt1, paramInt2);
    }
    else {
      netToMaster(9, paramInt1, paramInt2, paramActor);
    }
  }

  public void hitTank(Actor paramActor, int paramInt1, int paramInt2)
  {
    if (this.astateTankStates[paramInt1] == 6) return;
    int i = this.astateTankStates[paramInt1] + paramInt2;
    if (i > 6) i = 6;
    setTankState(paramActor, paramInt1, i);
  }

  public void repairTank(int paramInt)
  {
    if (!this.bIsMaster) return;
    if (this.astateTankStates[paramInt] > 0) setTankState(this.actor, paramInt, this.astateTankStates[paramInt] - 1);
  }

  public boolean doSetTankState(Actor paramActor, int paramInt1, int paramInt2)
  {
    boolean bool = this.aircraft.isChunkAnyDamageVisible(this.astateEffectChunks[(paramInt1 + 0)]);
    Aircraft.debugprintln(this.aircraft, "Stating Tank " + paramInt1 + " to state " + paramInt2 + (bool ? ".." : " rejected (missing part).."));
    if (!bool) {
      return false;
    }

    if (World.getPlayerAircraft() == this.actor) {
      if ((this.astateTankStates[paramInt1] == 0) && ((paramInt2 == 1) || (paramInt2 == 2))) HUD.log("FailedTank");
      if ((this.astateTankStates[paramInt1] < 5) && (paramInt2 >= 5)) HUD.log("FailedTankOnFire");
    }
    this.astateTankStates[paramInt1] = (byte)paramInt2;

    if ((this.astateTankStates[paramInt1] < 5) && (paramInt2 >= 5)) {
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setTakenMortalDamage(true, paramActor);
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setCapableOfACM(false);
    }
    if ((paramInt2 < 4) && (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isCapableOfBMP())) {
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setTakenMortalDamage(false, paramActor);
    }

    int j = 0; if (!this.bIsAboveCriticalSpeed) j = 21;
    for (int i = 0; i < 3; i++) {
      if (this.astateTankEffects[paramInt1][i] != null) Eff3DActor.finish(this.astateTankEffects[paramInt1][i]);
      this.astateTankEffects[paramInt1][i] = null;
      String str = astateTankStrings[(j + i + paramInt2 * 3)];
      if (str != null) {
        if (paramInt2 > 2) this.astateTankEffects[paramInt1][i] = Eff3DActor.New(this.actor, this.actor.findHook("_Tank" + (paramInt1 + 1) + "Burn"), null, 1.0F, str, -1.0F); else
          this.astateTankEffects[paramInt1][i] = Eff3DActor.New(this.actor, this.actor.findHook("_Tank" + (paramInt1 + 1) + "Leak"), null, 1.0F, str, -1.0F);
      }
    }
    this.aircraft.sfxSmokeState(2, paramInt1, paramInt2 > 4);
    return true;
  }

  private void doHitTank(Actor paramActor, int paramInt)
  {
    if (((World.Rnd().nextInt(0, 99) < 75) || ((this.actor instanceof Scheme1))) && (this.astateTankStates[paramInt] == 6)) {
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setReadyToDie(true);
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setTakenMortalDamage(true, paramActor);
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setCapableOfACM(false);
      Voice.speakMayday(this.aircraft);
      Aircraft.debugprintln(this.aircraft, "I'm on fire, going down!.");
      Explosions.generateComicBulb(this.actor, "OnFire", 12.0F);
      if ((World.Rnd().nextInt(0, 99) < 75) && (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Skill > 1)) {
        Aircraft.debugprintln(this.aircraft, "BAILING OUT - Tank " + paramInt + " is on fire!.");
        hitDaSilk();
      }
    } else if (World.Rnd().nextInt(0, 99) < 12) {
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setReadyToReturn(true);
      Aircraft.debugprintln(this.aircraft, "Tank " + paramInt + " hit, RTB..");
      Explosions.generateComicBulb(this.actor, "RTB", 12.0F);
    }
  }

  public void changeTankEffectBase(int paramInt, Actor paramActor)
  {
    for (int i = 0; i < 3; i++) {
      if (this.astateTankEffects[paramInt][i] != null) {
        this.astateTankEffects[paramInt][i].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.changeBase(paramActor, null, true);
      }
    }
    this.aircraft.sfxSmokeState(2, paramInt, false);
  }

  public void explodeTank(Actor paramActor, int paramInt)
  {
    if (!Actor.isValid(paramActor)) return;
    if (this.bIsMaster) {
      if (!this.aircraft.isChunkAnyDamageVisible(this.astateEffectChunks[(paramInt + 0)])) return;

      netToMirrors(10, paramInt, 0);
      doExplodeTank(paramInt);
    }
    else {
      netToMaster(10, paramInt, 0, paramActor);
    }
  }

  private void doExplodeTank(int paramInt)
  {
    Aircraft.debugprintln(this.aircraft, "Tank " + paramInt + " explodes..");
    Eff3DActor.New(this.actor, this.actor.findHook("_Tank" + (paramInt + 1) + "Burn"), null, 1.0F, "3DO/Effects/Fireworks/Tank_Burn.eff", -1.0F);

    Eff3DActor.New(this.actor, this.actor.findHook("_Tank" + (paramInt + 1) + "Burn"), null, 1.0F, "3DO/Effects/Fireworks/Tank_SmokeBoiling.eff", -1.0F);

    Eff3DActor.New(this.actor, this.actor.findHook("_Tank" + (paramInt + 1) + "Burn"), null, 1.0F, "3DO/Effects/Fireworks/Tank_Sparks.eff", -1.0F);

    Eff3DActor.New(this.actor, this.actor.findHook("_Tank" + (paramInt + 1) + "Burn"), null, 1.0F, "3DO/Effects/Fireworks/Tank_SparksP.eff", -1.0F);

    this.aircraft.msgCollision(this.actor, this.astateEffectChunks[(paramInt + 0)] + "0", this.astateEffectChunks[(paramInt + 0)] + "0");
    if (((this.actor instanceof Scheme1)) && (this.aircraft.isChunkAnyDamageVisible(this.astateEffectChunks[(paramInt + 0)])))
      this.aircraft.msgCollision(this.actor, "CF_D0", "CF_D0");
    Actor localActor;
    if (this.aircraft.getDamager() != null)
      localActor = this.aircraft.getDamager();
    else {
      localActor = this.actor;
    }

    HookNamed localHookNamed = new HookNamed((ActorMesh)this.actor, "_Tank" + (paramInt + 1) + "Burn");
    Loc localLoc1 = new Loc();
    Loc localLoc2 = new Loc();
    this.actor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getCurrent(localLoc1);
    localHookNamed.computePos(this.actor, localLoc1, localLoc2);

    if (World.getPlayerAircraft() == this.actor) HUD.log("FailedTankExplodes");
  }

  public void setEngineState(Actor paramActor, int paramInt1, int paramInt2)
  {
    if (!Actor.isValid(paramActor)) return;
    if ((paramInt2 < 0) || (paramInt2 > 4) || (this.astateEngineStates[paramInt1] == paramInt2)) return;
    if (this.bIsMaster) {
      int i = this.astateEngineStates[paramInt1];
      if (!doSetEngineState(paramActor, paramInt1, paramInt2)) return;
      for (int j = i; j < paramInt2; j++) {
        this.aircraft.setDamager(paramActor);
        doHitEngine(paramActor, paramInt1);
      }
      if ((this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && (paramActor != this.actor) && ((paramActor instanceof Aircraft)) && (((Aircraft)paramActor).isNetPlayer()) && (paramInt2 > 3) && (this.astateEngineStates[0] < 3) && (this.astateEngineStates[1] < 3) && (this.astateEngineStates[2] < 3) && (this.astateEngineStates[3] < 3) && 
        (!this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isSentBuryNote())) {
        Chat.sendLogRnd(3, "gore_lighteng", (Aircraft)paramActor, this.aircraft);
      }
      int k = 0;
      for (int m = 0; m < this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum(); m++) {
        if (this.astateEngineStates[m] <= 2) continue; k++;
      }
      if (k == this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum()) {
        setCockpitState(this.actor, this.astateCockpitState | 0x80);
      }

      netToMirrors(1, paramInt1, paramInt2);
    }
    else {
      netToMaster(1, paramInt1, paramInt2, paramActor);
    }
  }

  public void hitEngine(Actor paramActor, int paramInt1, int paramInt2)
  {
    if (this.astateEngineStates[paramInt1] == 4) return;
    int i = this.astateEngineStates[paramInt1] + paramInt2;
    if (i > 4) i = 4;
    setEngineState(paramActor, paramInt1, i);
  }

  public void repairEngine(int paramInt)
  {
    if (!this.bIsMaster) return;
    if (this.astateEngineStates[paramInt] > 0) setEngineState(this.actor, paramInt, this.astateEngineStates[paramInt] - 1);
  }

  public boolean doSetEngineState(Actor paramActor, int paramInt1, int paramInt2)
  {
    Aircraft.debugprintln(this.aircraft, "AS: Checking '" + this.astateEffectChunks[(paramInt1 + 4)] + "' visibility..");
    boolean bool = this.aircraft.isChunkAnyDamageVisible(this.astateEffectChunks[(paramInt1 + 4)]);
    Aircraft.debugprintln(this.aircraft, "AS: '" + this.astateEffectChunks[(paramInt1 + 4)] + "' is " + (bool ? "visible" : "invisible") + "..");
    Aircraft.debugprintln(this.aircraft, "Stating Engine " + paramInt1 + " to state " + paramInt2 + (bool ? ".." : " rejected (missing part).."));
    if (!bool) {
      return false;
    }

    if ((this.astateEngineStates[paramInt1] < 4) && (paramInt2 >= 4)) {
      if (World.getPlayerAircraft() == this.actor) {
        HUD.log("FailedEngineOnFire");
      }
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setTakenMortalDamage(true, paramActor);
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setCapableOfACM(false);
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setCapableOfTaxiing(false);
    }
    this.astateEngineStates[paramInt1] = (byte)paramInt2;

    if ((paramInt2 < 2) && (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isCapableOfBMP())) {
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setTakenMortalDamage(false, paramActor);
    }

    int j = 0; if (!this.bIsAboveCriticalSpeed) j = 15;
    for (int i = 0; i < 3; i++) {
      if (this.astateEngineEffects[paramInt1][i] != null) Eff3DActor.finish(this.astateEngineEffects[paramInt1][i]);
      this.astateEngineEffects[paramInt1][i] = null;
      String str = astateEngineStrings[(j + i + paramInt2 * 3)];
      if (str != null) {
        this.astateEngineEffects[paramInt1][i] = Eff3DActor.New(this.actor, this.actor.findHook("_Engine" + (paramInt1 + 1) + "Smoke"), null, 1.0F, str, -1.0F);
      }
    }
    this.aircraft.sfxSmokeState(1, paramInt1, paramInt2 > 3);
    return true;
  }

  private void doHitEngine(Actor paramActor, int paramInt)
  {
    if (World.Rnd().nextInt(0, 99) < 12) {
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setReadyToReturn(true);
      Aircraft.debugprintln(this.aircraft, "Engines out, RTB..");
      Explosions.generateComicBulb(this.actor, "RTB", 12.0F);
    }
    if ((this.astateEngineStates[paramInt] >= 2) && (!(this.actor instanceof Scheme1)) && 
      (World.Rnd().nextInt(0, 99) < 25)) {
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setReadyToReturn(true);
      Aircraft.debugprintln(this.aircraft, "One of the engines out, RTB..");
    }

    if (this.astateEngineStates[paramInt] == 4)
      if ((this.actor instanceof Scheme1)) {
        if (World.Rnd().nextBoolean()) {
          Aircraft.debugprintln(this.aircraft, "BAILING OUT - Engine " + paramInt + " is on fire..");
          this.aircraft.hitDaSilk();
        }
        this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setReadyToDie(true);
        this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setTakenMortalDamage(true, paramActor);
      }
      else if (World.Rnd().nextInt(0, 99) < 50) {
        this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setReadyToDie(true);
        if (World.Rnd().nextInt(0, 99) < 25) this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setTakenMortalDamage(true, paramActor);
        this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setCapableOfACM(false);

        Aircraft.debugprintln(this.aircraft, "Engines on fire, ditching..");
        Explosions.generateComicBulb(this.actor, "OnFire", 12.0F);
      }
  }

  public void changeEngineEffectBase(int paramInt, Actor paramActor)
  {
    for (int i = 0; i < 3; i++) {
      if (this.astateEngineEffects[paramInt][i] != null) {
        this.astateEngineEffects[paramInt][i].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.changeBase(paramActor, null, true);
      }
    }
    this.aircraft.sfxSmokeState(1, paramInt, false);
  }

  public void explodeEngine(Actor paramActor, int paramInt)
  {
    if (!Actor.isValid(paramActor)) return;
    if (this.bIsMaster) {
      if (!this.aircraft.isChunkAnyDamageVisible(this.astateEffectChunks[(paramInt + 4)])) return;

      netToMirrors(3, paramInt, 0);
      doExplodeEngine(paramInt);
    }
    else {
      netToMaster(3, paramInt, 0, paramActor);
    }
  }

  private void doExplodeEngine(int paramInt)
  {
    Aircraft.debugprintln(this.aircraft, "Engine " + paramInt + " explodes..");
    Eff3DActor.New(this.actor, this.actor.findHook("_Engine" + (paramInt + 1) + "Smoke"), null, 1.0F, "3DO/Effects/Fireworks/Tank_Burn.eff", -1.0F);

    Eff3DActor.New(this.actor, this.actor.findHook("_Engine" + (paramInt + 1) + "Smoke"), null, 1.0F, "3DO/Effects/Fireworks/Tank_SmokeBoiling.eff", -1.0F);

    Eff3DActor.New(this.actor, this.actor.findHook("_Engine" + (paramInt + 1) + "Smoke"), null, 1.0F, "3DO/Effects/Fireworks/Tank_Sparks.eff", -1.0F);

    Eff3DActor.New(this.actor, this.actor.findHook("_Engine" + (paramInt + 1) + "Smoke"), null, 1.0F, "3DO/Effects/Fireworks/Tank_SparksP.eff", -1.0F);

    this.aircraft.msgCollision(this.aircraft, this.astateEffectChunks[(paramInt + 4)] + "0", this.astateEffectChunks[(paramInt + 4)] + "0");
    Actor localActor;
    if (this.aircraft.getDamager() != null)
      localActor = this.aircraft.getDamager();
    else {
      localActor = this.actor;
    }

    HookNamed localHookNamed = new HookNamed((ActorMesh)this.actor, "_Engine" + (paramInt + 1) + "Smoke");
    Loc localLoc1 = new Loc();
    Loc localLoc2 = new Loc();
    this.actor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getCurrent(localLoc1);
    localHookNamed.computePos(this.actor, localLoc1, localLoc2);

    MsgExplosion.send(null, this.astateEffectChunks[(4 + paramInt)] + "0", localLoc2.getPoint(), localActor, 1.248F, 0.026F, 1, 75.0F);
  }

  public void setEngineStarts(int paramInt)
  {
    if (!this.bIsMaster) return;
    doSetEngineStarts(paramInt);

    netToMirrors(4, paramInt, 96);
  }

  public void setEngineRunning(int paramInt)
  {
    if (!this.bIsMaster) return;
    doSetEngineRunning(paramInt);
    netToMirrors(5, paramInt, 81);
  }

  public void setEngineStops(int paramInt)
  {
    if (!this.bIsMaster) return;
    doSetEngineStops(paramInt);
    netToMirrors(6, paramInt, 2);
  }

  public void setEngineDies(Actor paramActor, int paramInt)
  {
    if (!Actor.isValid(paramActor)) return;
    if (this.bIsMaster) {
      this.aircraft.setDamager(paramActor);
      doSetEngineDies(paramInt);

      netToMirrors(7, paramInt, 77);
    }
    else {
      netToMaster(7, paramInt, 67, paramActor);
    }
  }

  public void setEngineStuck(Actor paramActor, int paramInt)
  {
    if (!Actor.isValid(paramActor)) return;
    if (this.bIsMaster) {
      doSetEngineStuck(paramInt);
      this.aircraft.setDamager(paramActor);

      netToMirrors(29, paramInt, 77);
    }
    else {
      netToMaster(29, paramInt, 67, paramActor);
    }
  }

  public void setEngineSpecificDamage(Actor paramActor, int paramInt1, int paramInt2)
  {
    if (!Actor.isValid(paramActor)) return;
    if (this.bIsMaster) {
      this.aircraft.setDamager(paramActor);
      doSetEngineSpecificDamage(paramInt1, paramInt2);

      netToMirrors(2, paramInt1, paramInt2);
    }
    else {
      netToMaster(2, paramInt1, paramInt2, paramActor);
    }
  }

  public void setEngineReadyness(Actor paramActor, int paramInt1, int paramInt2)
  {
    if (this.bIsMaster) {
      this.aircraft.setDamager(paramActor);
      doSetEngineReadyness(paramInt1, paramInt2);

      netToMirrors(25, paramInt1, paramInt2);
    }
    else {
      netToMaster(25, paramInt1, paramInt2, paramActor);
    }
  }

  public void setEngineStage(Actor paramActor, int paramInt1, int paramInt2)
  {
    if (this.bIsMaster) {
      doSetEngineStage(paramInt1, paramInt2);

      netToMirrors(26, paramInt1, paramInt2);
    }
    else {
      netToMaster(26, paramInt1, paramInt2, paramActor);
    }
  }

  public void setEngineCylinderKnockOut(Actor paramActor, int paramInt1, int paramInt2)
  {
    if (!Actor.isValid(paramActor)) return;
    if (this.bIsMaster) {
      this.aircraft.setDamager(paramActor);
      doSetEngineCylinderKnockOut(paramInt1, paramInt2);

      netToMirrors(27, paramInt1, paramInt2);
    }
    else {
      netToMaster(27, paramInt1, paramInt2, paramActor);
    }
  }

  public void setEngineMagnetoKnockOut(Actor paramActor, int paramInt1, int paramInt2)
  {
    if (this.bIsMaster) {
      this.aircraft.setDamager(paramActor);
      doSetEngineMagnetoKnockOut(paramInt1, paramInt2);

      netToMirrors(28, paramInt1, paramInt2);
    }
    else {
      netToMaster(28, paramInt1, paramInt2, paramActor);
    }
  }

  private void doSetEngineStarts(int paramInt)
  {
    this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[paramInt].doSetEngineStarts();
  }

  private void doSetEngineRunning(int paramInt)
  {
    this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[paramInt].doSetEngineRunning();
  }

  private void doSetEngineStops(int paramInt)
  {
    this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[paramInt].doSetEngineStops();
  }

  private void doSetEngineDies(int paramInt)
  {
    this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[paramInt].doSetEngineDies();
  }

  private void doSetEngineStuck(int paramInt)
  {
    this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[paramInt].doSetEngineStuck();
  }

  private void doSetEngineSpecificDamage(int paramInt1, int paramInt2)
  {
    switch (paramInt2) {
    case 0:
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[paramInt1].doSetKillCompressor();
      break;
    case 3:
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[paramInt1].doSetKillPropAngleDevice();
      break;
    case 4:
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[paramInt1].doSetKillPropAngleDeviceSpeeds();
      break;
    case 5:
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[paramInt1].doSetExtinguisherFire();
      break;
    case 2:
      throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Unimplemented feature (E.S.D./H.)");
    case 1:
      if (!this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[paramInt1].isHasControlThrottle()) break;
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[paramInt1].doSetKillControlThrottle(); break;
    case 6:
      if (!this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[paramInt1].isHasControlProp()) break;
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[paramInt1].doSetKillControlProp(); break;
    case 7:
      if (!this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[paramInt1].isHasControlMix()) break;
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[paramInt1].doSetKillControlMix(); break;
    default:
      throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Corrupt data in (E.S.D./null)");
    }
  }

  private void doSetEngineReadyness(int paramInt1, int paramInt2)
  {
    this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[paramInt1].doSetReadyness(0.01F * paramInt2);
  }

  private void doSetEngineStage(int paramInt1, int paramInt2)
  {
    this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[paramInt1].doSetStage(paramInt2);
  }

  private void doSetEngineCylinderKnockOut(int paramInt1, int paramInt2)
  {
    this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[paramInt1].doSetCyliderKnockOut(paramInt2);
  }

  private void doSetEngineMagnetoKnockOut(int paramInt1, int paramInt2)
  {
    this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[paramInt1].doSetMagnetoKnockOut(paramInt2);
  }

  public void doSetEngineExtinguisherVisuals(int paramInt)
  {
    Aircraft.debugprintln(this.aircraft, "AS: Checking '" + this.astateEffectChunks[(paramInt + 4)] + "' visibility..");
    boolean bool = this.aircraft.isChunkAnyDamageVisible(this.astateEffectChunks[(paramInt + 4)]);
    Aircraft.debugprintln(this.aircraft, "AS: '" + this.astateEffectChunks[(paramInt + 4)] + "' is " + (bool ? "visible" : "invisible") + "..");
    Aircraft.debugprintln(this.aircraft, "Firing Extinguisher on Engine " + paramInt + (bool ? ".." : " rejected (missing part).."));
    if (!bool) {
      return;
    }
    Eff3DActor.New(this.actor, this.actor.findHook("_Engine" + (paramInt + 1) + "Smoke"), null, 1.0F, "3DO/Effects/Aircraft/EngineExtinguisher1.eff", 3.0F);
  }

  public void setSootState(Actor paramActor, int paramInt1, int paramInt2)
  {
    if (!Actor.isValid(paramActor)) return;
    if (this.astateSootStates[paramInt1] == paramInt2) return;
    if (this.bIsMaster) {
      if (!doSetSootState(paramInt1, paramInt2)) return;

      netToMirrors(8, paramInt1, paramInt2);
    }
    else {
      netToMaster(8, paramInt1, paramInt2, paramActor);
    }
  }

  public boolean doSetSootState(int paramInt1, int paramInt2)
  {
    Aircraft.debugprintln(this.aircraft, "AS: Checking '" + this.astateEffectChunks[(paramInt1 + 4)] + "' visibility..");
    boolean bool = this.aircraft.isChunkAnyDamageVisible(this.astateEffectChunks[(paramInt1 + 4)]);
    Aircraft.debugprintln(this.aircraft, "AS: '" + this.astateEffectChunks[(paramInt1 + 4)] + "' is " + (bool ? "visible" : "invisible") + "..");
    Aircraft.debugprintln(this.aircraft, "Stating Engine " + paramInt1 + " to state " + paramInt2 + (bool ? ".." : " rejected (missing part).."));
    if (!bool) {
      return false;
    }

    this.astateSootStates[paramInt1] = (byte)paramInt2;

    this.aircraft.doSetSootState(paramInt1, paramInt2);
    return true;
  }

  public void changeSootEffectBase(int paramInt, Actor paramActor)
  {
    for (int i = 0; i < 2; i++)
      if (this.astateSootEffects[paramInt][i] != null)
        this.astateSootEffects[paramInt][i].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.changeBase(paramActor, null, true);
  }

  public void setCockpitState(Actor paramActor, int paramInt)
  {
    if (!Actor.isValid(paramActor)) return;
    if (this.astateCockpitState == paramInt) return;

    if (this.bIsMaster) {
      this.aircraft.setDamager(paramActor);
      doSetCockpitState(paramInt);

      netToMirrors(23, 0, paramInt);
    }
    else
    {
      netToMaster(23, 0, paramInt, paramActor);
    }
  }

  public void doSetCockpitState(int paramInt) {
    if (this.astateCockpitState == paramInt) return;

    this.astateCockpitState = paramInt;
    this.aircraft.setCockpitState(paramInt);
  }

  public void setControlsDamage(Actor paramActor, int paramInt)
  {
    if (!Actor.isValid(paramActor)) return;
    if (this.bIsMaster) {
      this.aircraft.setDamager(paramActor);
      if ((this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && (paramActor != this.actor) && ((paramActor instanceof Aircraft)) && (((Aircraft)paramActor).isNetPlayer()) && (!this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isSentControlsOutNote()) && (!this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isSentBuryNote()))
      {
        Chat.sendLogRnd(3, "gore_hitctrls", (Aircraft)paramActor, this.aircraft);
        this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setSentControlsOutNote(true);
      }
      doSetControlsDamage(paramInt, paramActor);
    }
    else
    {
      netToMaster(21, 0, paramInt, paramActor);
    }
  }

  public void setInternalDamage(Actor paramActor, int paramInt)
  {
    if (!Actor.isValid(paramActor)) return;
    if (this.bIsMaster) {
      this.aircraft.setDamager(paramActor);
      doSetInternalDamage(paramInt);

      netToMirrors(22, 0, paramInt);
    }
    else {
      netToMaster(22, 0, paramInt, paramActor);
    }
  }

  public void setGliderBoostOn()
  {
    if (!this.bIsMaster) return;
    netToMirrors(12, 5, 5);
  }

  public void setGliderBoostOff()
  {
    if (!this.bIsMaster) return;
    netToMirrors(13, 7, 7);
  }

  public void setGliderCutCart()
  {
    if (!this.bIsMaster) return;
    netToMirrors(14, 9, 9);
  }

  private void doSetControlsDamage(int paramInt, Actor paramActor)
  {
    switch (paramInt) {
    case 0:
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.resetControl(0);
      if ((this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasAileronControl)) HUD.log("FailedAroneAU");
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasAileronControl = false;
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setCapableOfACM(false);
      break;
    case 1:
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.resetControl(1);
      if ((this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasElevatorControl)) HUD.log("FailedVatorAU");
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasElevatorControl = false;
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setCapableOfACM(false);
      if (Math.abs(this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.z) <= 7.0D) break;
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setCapableOfBMP(false, paramActor); break;
    case 2:
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.resetControl(2);
      if ((this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasRudderControl)) HUD.log("FailedRudderAU");
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasRudderControl = false;
      break;
    default:
      throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Corrupt data in (C.A.D./null)");
    }
  }

  private void doSetInternalDamage(int paramInt)
  {
    switch (paramInt) {
    case 0:
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.setHydroOperable(false);
      break;
    case 1:
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasFlapsControl = false;
      break;
    case 2:
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.nitro = 0.0F;
      break;
    case 3:
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.setHydroOperable(false);
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.setOperable(false);
      break;
    case 4:
      if ((this.aircraft instanceof DO_335A0))
        ((DO_335A0)this.aircraft).doKeelShutoff();
      else if ((this.aircraft instanceof DO_335V13))
        ((DO_335V13)this.aircraft).doKeelShutoff();
      else {
        throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Unexpected command (I.K.S.)");
      }

    case 5:
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].setPropReductorValue(0.007F);
      break;
    default:
      throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Corrupt data in (I.D./null)");
    }
  }

  private void doSetGliderBoostOn()
  {
    if ((this.actor instanceof Scheme0))
      ((Scheme0)this.actor).doFireBoosters();
    else if ((this.actor instanceof AR_234B2))
      ((AR_234B2)this.actor).doFireBoosters();
    else
      throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Unexpected command (G.B./On not in Nul)");
  }

  private void doSetGliderBoostOff()
  {
    if ((this.actor instanceof Scheme0))
      ((Scheme0)this.actor).doCutBoosters();
    else if ((this.actor instanceof AR_234B2))
      ((AR_234B2)this.actor).doCutBoosters();
    else
      throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Unexpected command (G.B./Off not in Nul)");
  }

  private void doSetGliderCutCart()
  {
    if ((this.actor instanceof Scheme0))
      ((Scheme0)this.actor).doCutCart();
    else
      throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Unexpected command (G.C.C./Off not in Nul)");
  }

  private void doSetCondensateState(boolean paramBoolean)
  {
    for (int i = 0; i < this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum(); i++) {
      if (this.astateCondensateEffects[i] != null) Eff3DActor.finish(this.astateCondensateEffects[i]);
      this.astateCondensateEffects[i] = null;
      if (paramBoolean) {
        String str = astateCondensateStrings[1];
        if (str == null) continue;
        try {
          this.astateCondensateEffects[i] = Eff3DActor.New(this.actor, this.actor.findHook("_Engine" + (i + 1) + "Smoke"), astateCondensateDispVector, 1.0F, str, -1.0F);
        } catch (Exception localException) {
          Aircraft.debugprintln(this.aircraft, "Above condensate failed - probably a glider..");
        }
      }
    }
  }

  public void setStallState(boolean paramBoolean)
  {
    if (this.bIsMaster) {
      doSetStallState(paramBoolean);

      netToMirrors(16, paramBoolean ? 1 : 0, paramBoolean ? 1 : 0);
    }
  }

  private void doSetStallState(boolean paramBoolean)
  {
    for (int i = 0; i < 2; i++) {
      if (this.astateStallEffects[i] == null) continue; Eff3DActor.finish(this.astateStallEffects[i]);
    }
    if (paramBoolean) {
      String str = astateStallStrings[1];
      if (str != null) {
        if (this.bWingTipLExists) this.astateStallEffects[0] = Eff3DActor.New(this.actor, this.actor.findHook("_WingTipL"), null, 1.0F, str, -1.0F);
        if (this.bWingTipRExists) this.astateStallEffects[1] = Eff3DActor.New(this.actor, this.actor.findHook("_WingTipR"), null, 1.0F, str, -1.0F);
      }
    }
  }

  public void setAirShowState(boolean paramBoolean)
  {
    if (this.bShowSmokesOn == paramBoolean) return;
    if (this.bIsMaster) {
      doSetAirShowState(paramBoolean);

      netToMirrors(15, paramBoolean ? 1 : 0, paramBoolean ? 1 : 0);
    }
  }

  private void doSetAirShowState(boolean paramBoolean)
  {
    this.bShowSmokesOn = paramBoolean;
    for (int i = 0; i < 2; i++) {
      if (this.astateAirShowEffects[i] == null) continue; Eff3DActor.finish(this.astateAirShowEffects[i]);
    }
    if (paramBoolean) {
      if (this.bWingTipLExists) this.astateAirShowEffects[0] = Eff3DActor.New(this.actor, this.actor.findHook("_WingTipL"), null, 1.0F, "3DO/Effects/Aircraft/AirShowRedTSPD.eff", -1.0F);

      if (this.bWingTipRExists) this.astateAirShowEffects[1] = Eff3DActor.New(this.actor, this.actor.findHook("_WingTipR"), null, 1.0F, "3DO/Effects/Aircraft/AirShowGreenTSPD.eff", -1.0F);
    }
  }

  public void setNavLightsState(boolean paramBoolean)
  {
    if (this.bNavLightsOn == paramBoolean) return;
    if (this.bIsMaster) {
      doSetNavLightsState(paramBoolean);

      netToMirrors(30, paramBoolean ? 1 : 0, paramBoolean ? 1 : 0);
    }
  }

  private void doSetNavLightsState(boolean paramBoolean)
  {
    for (int i = 0; i < this.astateNavLightsEffects.length; i++) {
      if (this.astateNavLightsEffects[i] != null) {
        Eff3DActor.finish(this.astateNavLightsEffects[i]);
        this.astateNavLightsLights[i].light.setEmit(0.0F, 0.0F);
      }
      this.astateNavLightsEffects[i] = null;
    }
    if (paramBoolean) {
      Loc localLoc1 = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
      Loc localLoc2 = new Loc();
      for (i = 0; i < this.astateNavLightsEffects.length; i++) {
        Aircraft.debugprintln(this.aircraft, "AS: Checking '" + this.astateEffectChunks[(i + 12)] + "' visibility..");
        boolean bool = this.aircraft.isChunkAnyDamageVisible(this.astateEffectChunks[(i + 12)]);
        Aircraft.debugprintln(this.aircraft, "AS: '" + this.astateEffectChunks[(i + 12)] + "' is " + (bool ? "visible" : "invisible") + "..");
        if (bool) {
          this.bNavLightsOn = paramBoolean;
          String str = "3DO/Effects/Fireworks/Flare" + (i > 1 ? "Green" : i > 3 ? "White" : "Red") + ".eff";
          this.astateNavLightsEffects[i] = Eff3DActor.New(this.actor, this.actor.findHook("_NavLight" + i), null, 1.0F, str, -1.0F, false);
          this.astateNavLightsLights[i].light.setEmit(0.35F, 8.0F);
        }
      }
    } else {
      this.bNavLightsOn = paramBoolean;
    }
  }

  public void changeNavLightEffectBase(int paramInt, Actor paramActor)
  {
    if (this.astateNavLightsEffects[paramInt] != null) {
      Eff3DActor.finish(this.astateNavLightsEffects[paramInt]);
      this.astateNavLightsLights[paramInt].light.setEmit(0.0F, 0.0F);
      this.astateNavLightsEffects[paramInt] = null;
    }
  }

  public void setLandingLightState(boolean paramBoolean)
  {
    if (this.bLandingLightOn == paramBoolean) return;
    if (this.bIsMaster) {
      doSetLandingLightState(paramBoolean);
      int i = 0;
      for (int j = 0; j < this.astateLandingLightEffects.length; j++) {
        if (this.astateLandingLightEffects[j] == null) {
          i++;
        }
      }
      if (i == this.astateLandingLightEffects.length) {
        this.bLandingLightOn = false;
        paramBoolean = false;
      }

      netToMirrors(31, paramBoolean ? 1 : 0, paramBoolean ? 1 : 0);
    }
  }

  private void doSetLandingLightState(boolean paramBoolean)
  {
    this.bLandingLightOn = paramBoolean;
    for (int i = 0; i < this.astateLandingLightEffects.length; i++) {
      if (this.astateLandingLightEffects[i] != null) {
        Eff3DActor.finish(this.astateLandingLightEffects[i]);
        this.astateLandingLightLights[i].light.setEmit(0.0F, 0.0F);
      }
      this.astateLandingLightEffects[i] = null;
    }
    if (paramBoolean) {
      Loc localLoc1 = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
      Loc localLoc2 = new Loc();
      for (i = 0; i < this.astateLandingLightEffects.length; i++) {
        Aircraft.debugprintln(this.aircraft, "AS: Checking '" + this.astateEffectChunks[(i + 18)] + "' visibility..");
        boolean bool = this.aircraft.isChunkAnyDamageVisible(this.astateEffectChunks[(i + 18)]);
        Aircraft.debugprintln(this.aircraft, "AS: '" + this.astateEffectChunks[(i + 18)] + "' is " + (bool ? "visible" : "invisible") + "..");
        if (bool) {
          String str = "3DO/Effects/Fireworks/FlareWhiteWide.eff";
          this.astateLandingLightEffects[i] = Eff3DActor.New(this.actor, this.actor.findHook("_LandingLight0" + i), null, 1.0F, str, -1.0F);
          this.astateLandingLightLights[i].light.setEmit(1.2F, 8.0F);
        }
      }
    }
  }

  public void changeLandingLightEffectBase(int paramInt, Actor paramActor)
  {
    if (this.astateLandingLightEffects[paramInt] != null) {
      Eff3DActor.finish(this.astateLandingLightEffects[paramInt]);
      this.astateLandingLightLights[paramInt].light.setEmit(0.0F, 0.0F);
      this.astateLandingLightEffects[paramInt] = null;
      this.bLandingLightOn = false;
    }
  }

  public void setPilotState(Actor paramActor, int paramInt1, int paramInt2)
  {
    setPilotState(paramActor, paramInt1, paramInt2, true);
  }
  public void setPilotState(Actor paramActor, int paramInt1, int paramInt2, boolean paramBoolean) {
    if (!Actor.isValid(paramActor)) return;
    if (paramInt2 > 95) paramInt2 = 100;
    if (paramInt2 < 0) paramInt2 = 0;
    if (this.astatePilotStates[paramInt1] >= paramInt2) return;
    if (this.bIsMaster) {
      this.aircraft.setDamager(paramActor);
      doSetPilotState(paramInt1, paramInt2, paramActor);
      if ((paramBoolean) && (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && (paramInt1 == this.astatePlayerIndex) && (!World.isPlayerDead()) && (paramActor != this.actor) && ((paramActor instanceof Aircraft)) && (((Aircraft)paramActor).isNetPlayer()) && (paramInt2 == 100))
      {
        Chat.sendLogRnd(1, "gore_pk", (Aircraft)paramActor, this.aircraft);
      }
      if ((paramInt2 > 0) && (paramBoolean))
      {
        netToMirrors(17, paramInt1, paramInt2);
      }

    }
    else if (paramBoolean) {
      netToMaster(17, paramInt1, paramInt2, paramActor);
    }
  }

  public void hitPilot(Actor paramActor, int paramInt1, int paramInt2)
  {
    setPilotState(paramActor, paramInt1, this.astatePilotStates[paramInt1] + paramInt2);
  }

  private void doSetPilotState(int paramInt1, int paramInt2, Actor paramActor)
  {
    if (paramInt2 > 95) paramInt2 = 100;

    if ((World.getPlayerAircraft() == this.actor) && (Config.isUSE_RENDER()) && ((!this.bIsAboutToBailout) || (this.astateBailoutStep <= 11)))
    {
      i = (paramInt1 == this.astatePlayerIndex) && (!World.isPlayerDead()) ? 1 : 0;
      if ((this.astatePilotStates[paramInt1] < 100) && (paramInt2 == 100)) {
        HUD.log(astateHUDPilotHits[this.astatePilotFunctions[paramInt1]] + "HIT2");
        if (i != 0) {
          World.setPlayerDead();
          if (Mission.isNet()) {
            Chat.sendLog(0, "gore_killed", (NetUser)NetEnv.host(), (NetUser)NetEnv.host());
          }
          if ((Main3D.cur3D().cockpits != null) && (!World.isPlayerGunner())) {
            int j = Main3D.cur3D().cockpits.length;
            for (int k = 0; k < j; k++) {
              Cockpit localCockpit = Main3D.cur3D().cockpits[k];
              if ((!Actor.isValid(localCockpit)) || 
                (localCockpit.astatePilotIndx() == paramInt1) || 
                (isPilotDead(localCockpit.astatePilotIndx())) || 
                (Mission.isNet()) || 
                (!AircraftHotKeys.isCockpitRealMode(k))) continue;
              AircraftHotKeys.setCockpitRealMode(k, false);
            }
          }
        }
      } else if ((this.astatePilotStates[paramInt1] < 66) && (paramInt2 > 66)) {
        HUD.log(astateHUDPilotHits[this.astatePilotFunctions[paramInt1]] + "HIT1");
      } else if ((this.astatePilotStates[paramInt1] < 25) && (paramInt2 > 25)) {
        HUD.log(astateHUDPilotHits[this.astatePilotFunctions[paramInt1]] + "HIT0");
      }

    }

    int i = this.astatePilotStates[paramInt1];
    this.astatePilotStates[paramInt1] = (byte)paramInt2;
    if ((this.bIsAboutToBailout) && (this.astateBailoutStep > paramInt1 + 11)) {
      this.aircraft.doKillPilot(paramInt1);
      return;
    }
    if (paramInt2 > 99) {
      this.aircraft.doKillPilot(paramInt1);
      if (World.cur().isHighGore()) this.aircraft.doMurderPilot(paramInt1);
      if (paramInt1 == 0) {
        if (!this.bIsAboutToBailout) Explosions.generateComicBulb(this.actor, "PK", 9.0F);
        FlightModel localFlightModel = this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
        if ((localFlightModel instanceof Maneuver)) {
          ((Maneuver)localFlightModel).set_maneuver(44);
          ((Maneuver)localFlightModel).set_task(2);
          localFlightModel.setCapableOfTaxiing(false);
        }
      }
      if ((paramInt1 > 0) && 
        (!this.bIsAboutToBailout)) Explosions.generateComicBulb(this.actor, "GunnerDown", 9.0F);

      EventLog.onPilotKilled(this.aircraft, paramInt1, paramActor == this.aircraft ? null : paramActor);
    } else if ((i < 66) && (paramInt2 > 66)) {
      EventLog.onPilotHeavilyWounded(this.aircraft, paramInt1);
    } else if ((i < 25) && (paramInt2 > 25)) {
      EventLog.onPilotWounded(this.aircraft, paramInt1);
    }
  }

  private void doRemoveBodyFromPlane(int paramInt)
  {
    this.aircraft.doRemoveBodyFromPlane(paramInt);
  }

  public float getPilotHealth(int paramInt)
  {
    if ((paramInt < 0) || (paramInt > this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_crew_of_type_Int - 1)) return 0.0F;
    return 1.0F - this.astatePilotStates[paramInt] * 0.01F;
  }

  public boolean isPilotDead(int paramInt)
  {
    if ((paramInt < 0) || (paramInt > this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_crew_of_type_Int - 1)) return true;
    return this.astatePilotStates[paramInt] == 100;
  }

  public boolean isPilotParatrooper(int paramInt)
  {
    if ((paramInt < 0) || (paramInt > this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_crew_of_type_Int - 1)) return true;
    return (this.astatePilotStates[paramInt] == 100) && (this.astateBailoutStep > 11 + paramInt);
  }

  public void setJamBullets(int paramInt1, int paramInt2)
  {
    if ((this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[paramInt1] == null) || (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[paramInt1].length <= paramInt2) || (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[paramInt1][paramInt2] == null))
    {
      return;
    }
    if (this.bIsMaster) {
      doSetJamBullets(paramInt1, paramInt2);

      netToMirrors(24, paramInt1, paramInt2);
    }
    else {
      netToMaster(24, paramInt1, paramInt2);
    }
  }

  private void doSetJamBullets(int paramInt1, int paramInt2)
  {
    if ((this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons != null) && (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[paramInt1] != null) && (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[paramInt1][paramInt2] != null) && (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[paramInt1][paramInt2].haveBullets()))
    {
      if ((this.actor == World.getPlayerAircraft()) && (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[paramInt1][paramInt2].haveBullets())) {
        if (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[paramInt1][paramInt2].bulletMassa() < 0.095F)
          HUD.log(paramInt1 > 9 ? "FailedTMGun" : "FailedMGun");
        else
          HUD.log("FailedCannon");
      }
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[paramInt1][paramInt2].loadBullets(0);
    }
  }

  public boolean hitDaSilk()
  {
    if (!this.bIsMaster)
      return false;
    if (this.bIsAboutToBailout)
      return false;
    if ((bCheckPlayerAircraft) && (this.actor == World.getPlayerAircraft()))
      return false;
    if (!this.bIsEnableToBailout) {
      return false;
    }
    this.bIsAboutToBailout = true;
    FlightModel localFlightModel = this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
    Aircraft.debugprintln(this.aircraft, "I've had it, bailing out..");
    Explosions.generateComicBulb(this.actor, "Bailing", 5.0F);
    if ((localFlightModel instanceof Maneuver)) {
      ((Maneuver)localFlightModel).set_maneuver(44);
      ((Maneuver)localFlightModel).set_task(2);
      localFlightModel.setTakenMortalDamage(true, null);
    }
    return true;
  }

  public void setFlatTopString(Actor paramActor, int paramInt)
  {
    if (this.bIsMaster)
    {
      netToMirrors(36, paramInt, paramInt, paramActor);
    }
  }

  private void doSetFlatTopString(Actor paramActor, int paramInt)
  {
    if ((Actor.isValid(paramActor)) && ((paramActor instanceof BigshipGeneric)) && (((BigshipGeneric)paramActor).getAirport() != null))
    {
      BigshipGeneric localBigshipGeneric = (BigshipGeneric)paramActor;
      if ((paramInt >= 0) && (paramInt < 255))
        localBigshipGeneric.forceTowAircraft(this.aircraft, paramInt);
      else
        localBigshipGeneric.requestDetowAircraft(this.aircraft);
    }
  }

  public void setFMSFX(Actor paramActor, int paramInt1, int paramInt2)
  {
    if (!Actor.isValid(paramActor)) return;
    if (this.bIsMaster) {
      doSetFMSFX(paramInt1, paramInt2);
    }
    else
    {
      netToMaster(37, paramInt1, paramInt2, paramActor);
    }
  }

  private void doSetFMSFX(int paramInt1, int paramInt2)
  {
    this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.doRequestFMSFX(paramInt1, paramInt2);
  }

  public void setWingFold(Actor paramActor, int paramInt)
  {
    if (!Actor.isValid(paramActor)) {
      return;
    }
    if (paramActor != this.aircraft) {
      return;
    }
    if (!this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasWingControl) {
      return;
    }
    if (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.wingControl == paramInt) {
      return;
    }
    if (!this.bIsMaster) {
      return;
    }
    doSetWingFold(paramInt);

    netToMirrors(38, paramInt, paramInt);
  }

  private void doSetWingFold(int paramInt)
  {
    this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.wingControl = paramInt;
  }

  public void setCockpitDoor(Actor paramActor, int paramInt)
  {
    if (!Actor.isValid(paramActor)) {
      return;
    }
    if (paramActor != this.aircraft) {
      return;
    }
    if (!this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasCockpitDoorControl) {
      return;
    }
    if (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.cockpitDoorControl == paramInt) {
      return;
    }
    if (!this.bIsMaster) {
      return;
    }
    doSetCockpitDoor(paramInt);

    netToMirrors(39, paramInt, paramInt);
  }

  private void doSetCockpitDoor(int paramInt)
  {
    this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.cockpitDoorControl = paramInt;
    if ((paramInt == 0) && 
      ((Main.cur() instanceof Main3D)) && (this.aircraft == World.getPlayerAircraft()) && (HookPilot.current != null))
    {
      HookPilot.current.doUp(false);
    }
  }

  public void setArrestor(Actor paramActor, int paramInt)
  {
    if (!Actor.isValid(paramActor)) {
      return;
    }
    if (paramActor != this.aircraft) {
      return;
    }
    if (!this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasArrestorControl) {
      return;
    }
    if (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.arrestorControl == paramInt) {
      return;
    }
    if (!this.bIsMaster) {
      return;
    }
    doSetArrestor(paramInt);

    netToMirrors(40, paramInt, paramInt);
  }

  private void doSetArrestor(int paramInt)
  {
    this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.arrestorControl = paramInt;
  }

  public void update(float paramFloat)
  {
    bCriticalStatePassed = this.bIsAboveCriticalSpeed != this.aircraft.getSpeed(null) > 10.0D;
    int i;
    if (bCriticalStatePassed) {
      this.bIsAboveCriticalSpeed = (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed() > 10.0F);
      for (i = 0; i < 4; i++) doSetTankState(null, i, this.astateTankStates[i]);
      for (i = 0; i < this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum(); i++) doSetEngineState(null, i, this.astateEngineStates[i]);
      for (i = 0; i < this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum(); i++) doSetOilState(i, this.astateOilStates[i]);
    }

    bCriticalStatePassed = this.bIsAboveCondensateAlt != this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() > 7000.0F;

    if (bCriticalStatePassed) {
      this.bIsAboveCondensateAlt = (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() > 7000.0F);
      doSetCondensateState(this.bIsAboveCondensateAlt);
    }

    bCriticalStatePassed = this.bIsOnInadequateAOA != ((this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed() > 17.0F) && (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAOA() > 15.0F - this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() * 0.001F));

    if (bCriticalStatePassed) {
      this.bIsOnInadequateAOA = ((this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed() > 17.0F) && (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAOA() > 15.0F - this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() * 0.001F));
      setStallState(this.bIsOnInadequateAOA);
    }

    if (this.bIsMaster)
    {
      float f = 0.0F;
      for (i = 0; i < 4; i++) f += this.astateTankStates[i] * this.astateTankStates[i];

      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.requestFuel(f * 0.12F * paramFloat);

      for (i = 0; i < 4; i++) {
        switch (this.astateTankStates[i]) { case 1:
          if (World.Rnd().nextFloat(0.0F, 1.0F) >= 0.0125F) break;
          repairTank(i);
          Aircraft.debugprintln(this.aircraft, "Tank " + i + " protector clothes the hole - leak stops..");
        case 2:
          if (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel <= 0.0F) {
            repairTank(i);
            Aircraft.debugprintln(this.aircraft, "Tank " + i + " runs out of fuel - leak stops.."); } break;
        case 4:
          if (World.Rnd().nextFloat(0.0F, 1.0F) < 0.00333F) {
            hitTank(this.aircraft, i, 1);
            Aircraft.debugprintln(this.aircraft, "Tank " + i + " catches fire.."); } break;
        case 5:
          if ((this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed() > 111.0F) && (World.Rnd().nextFloat(0.0F, 1.0F) < 0.2F)) {
            repairTank(i);
            Aircraft.debugprintln(this.aircraft, "Tank " + i + " cuts fire..");
          }
          if (World.Rnd().nextFloat() < 0.0048F) {
            Aircraft.debugprintln(this.aircraft, "Tank " + i + " fires up to the next stage..");
            hitTank(this.aircraft, i, 1);
          }
          if ((!(this.actor instanceof Scheme1)) || 
            (this.astateTankEffects[i][0] == null) || (Math.abs(this.astateTankEffects[i][0].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRelPoint().jdField_y_of_type_Double) >= 1.899999976158142D) || (this.astateTankEffects[i][0].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRelPoint().jdField_x_of_type_Double <= -2.599999904632568D))
          {
            continue;
          }
          if (this.astatePilotStates[0] < 96) {
            hitPilot(this.actor, 0, 5);
            if (this.astatePilotStates[0] >= 96) {
              hitPilot(this.actor, 0, 101 - this.astatePilotStates[0]);
              if ((this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && (Mission.isNet()) && (!this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isSentBuryNote()))
                Chat.sendLogRnd(3, "gore_burnedcpt", this.aircraft, null); 
            }
          }break;
        case 6:
          if ((this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed() > 140.0F) && (World.Rnd().nextFloat(0.0F, 1.0F) < 0.05F)) {
            repairTank(i);
            Aircraft.debugprintln(this.aircraft, "Tank " + i + " cuts fire..");
          }
          if (World.Rnd().nextFloat() < 0.02F) {
            Aircraft.debugprintln(this.aircraft, "Tank " + i + " EXPLODES!.");
            explodeTank(this.aircraft, i);
          }
          if ((!(this.actor instanceof Scheme1)) || 
            (this.astateTankEffects[i][0] == null) || (Math.abs(this.astateTankEffects[i][0].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRelPoint().jdField_y_of_type_Double) >= 1.899999976158142D) || (this.astateTankEffects[i][0].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRelPoint().jdField_x_of_type_Double <= -2.599999904632568D))
          {
            continue;
          }
          if (this.astatePilotStates[0] < 96) {
            hitPilot(this.actor, 0, 7);
            if (this.astatePilotStates[0] >= 96) {
              hitPilot(this.actor, 0, 101 - this.astatePilotStates[0]);
              if ((this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && (Mission.isNet()) && (!this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isSentBuryNote())) {
                Chat.sendLogRnd(3, "gore_burnedcpt", this.aircraft, null);
              }
            }
          }
        case 3:
        }

      }

      for (i = 0; i < this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum(); i++) {
        if (this.astateEngineStates[i] > 1)
        {
          this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setReadyness(this.actor, this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getReadyness() - this.astateEngineStates[i] * 0.00025F * paramFloat);
          if ((this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getReadyness() < 0.2F) && 
            (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getReadyness() != 0.0F)) {
            this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setEngineDies(this.actor);
          }
        }

        switch (this.astateEngineStates[i]) { case 3:
          if (World.Rnd().nextFloat(0.0F, 1.0F) >= 0.01F) break;
          hitEngine(this.aircraft, i, 1);
          Aircraft.debugprintln(this.aircraft, "Engine " + i + " catches fire.."); break;
        case 4:
          if ((this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed() > 111.0F) && (World.Rnd().nextFloat(0.0F, 1.0F) < 0.15F)) {
            repairEngine(i);
            Aircraft.debugprintln(this.aircraft, "Engine " + i + " cuts fire..");
          }
          if (((this.actor instanceof Scheme1)) && (World.Rnd().nextFloat() < 0.06F)) {
            Aircraft.debugprintln(this.aircraft, "Engine 0 detonates and explodes, fatal damage level forced..");
            this.aircraft.msgCollision(this.actor, "CF_D0", "CF_D0");
          }
          if ((!(this.actor instanceof Scheme1)) || 
            (this.astatePilotStates[0] >= 96)) break;
          hitPilot(this.actor, 0, 4);
          if (this.astatePilotStates[0] < 96) break;
          hitPilot(this.actor, 0, 101 - this.astatePilotStates[0]);
          if ((!this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) || (!Mission.isNet()) || (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isSentBuryNote())) break;
          Chat.sendLogRnd(3, "gore_burnedcpt", this.aircraft, null);
        }

        if (this.astateOilStates[i] > 0) {
          this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setReadyness(this.aircraft, this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getReadyness() - 0.001875F * paramFloat);
        }

      }

      if ((World.Rnd().nextFloat() < 0.25F) && (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.saveWeaponControl[3] == 0) && (
        (!(this.actor instanceof TypeBomber)) || (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isReadyToReturn()) || ((this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && ((this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof RealFlightModel)) && (((RealFlightModel)this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).isRealMode())))) {
        this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl = 0.0F;
      }

      bailout();
    }
    else if ((World.Rnd().nextFloat() < 0.125F) && (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.saveWeaponControl[3] == 0) && (
      (!(this.actor instanceof TypeBomber)) || (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AP.way.curr().Action != 3))) {
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl = 0.0F;
    }
  }

  private void bailout()
  {
    if (this.bIsAboutToBailout)
      if ((this.astateBailoutStep >= 0) && (this.astateBailoutStep < 2)) {
        if ((this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.cockpitDoorControl > 0.5F) && (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getCockpitDoor() > 0.5F)) {
          this.astateBailoutStep = 11;
          doRemoveBlisters();
        } else {
          this.astateBailoutStep = 2;
        }
      } else if ((this.astateBailoutStep >= 2) && (this.astateBailoutStep <= 3)) {
        switch (this.astateBailoutStep) {
        case 2:
          if (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.cockpitDoorControl >= 0.5F) break;
          doRemoveBlister1(); break;
        case 3:
          doRemoveBlisters();
        }

        if (this.bIsMaster)
          netToMirrors(20, this.astateBailoutStep, 1);
        this.astateBailoutStep = (byte)(this.astateBailoutStep + 1);
        if ((this.astateBailoutStep == 3) && ((this.actor instanceof P_39))) this.astateBailoutStep = (byte)(this.astateBailoutStep + 1);
        if (this.astateBailoutStep == 4) this.astateBailoutStep = 11; 
      }
      else if ((this.astateBailoutStep >= 11) && (this.astateBailoutStep <= 19)) {
        float f1 = this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed();
        float f2 = (float)this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Loc.z;
        float f3 = 140.0F;
        if (((this.aircraft instanceof HE_162)) || ((this.aircraft instanceof GO_229)) || ((this.aircraft instanceof ME_262HGII)) || ((this.aircraft instanceof DO_335)))
        {
          f3 = 9999.9004F;
        }
        if (((Pitot.Indicator(f2, f1) < f3) && (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getOverload() < 2.0F)) || (!this.bIsMaster))
        {
          int i = this.astateBailoutStep;
          if (this.bIsMaster)
            netToMirrors(20, this.astateBailoutStep, 1);
          this.astateBailoutStep = (byte)(this.astateBailoutStep + 1);
          if (i == 11) {
            this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setTakenMortalDamage(true, null);
            if (((this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Maneuver)) && (((Maneuver)this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).get_maneuver() != 44)) { World.cur(); if (this.actor != World.getPlayerAircraft())
              {
                ((Maneuver)this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).set_maneuver(44);
              } }
          }
          if (this.astatePilotStates[(i - 11)] < 99) {
            doRemoveBodyFromPlane(i - 10);
            if (i == 11) {
              if ((this.aircraft instanceof HE_162)) {
                ((HE_162)this.aircraft).doEjectCatapult();
                this.astateBailoutStep = 51;
                this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setTakenMortalDamage(true, null);
                this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[0] = false;
                this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[1] = false;
                this.astateBailoutStep = -1;
                return;
              }
              if ((this.aircraft instanceof GO_229)) {
                ((GO_229)this.aircraft).doEjectCatapult();
                this.astateBailoutStep = 51;
                this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setTakenMortalDamage(true, null);
                this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[0] = false;
                this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[1] = false;
                this.astateBailoutStep = -1;
                return;
              }
              if ((this.aircraft instanceof DO_335)) {
                ((DO_335)this.aircraft).doEjectCatapult();
                this.astateBailoutStep = 51;
                this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setTakenMortalDamage(true, null);
                this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[0] = false;
                this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[1] = false;
                this.astateBailoutStep = -1;
                return;
              }
              if ((this.aircraft instanceof ME_262HGII)) {
                ((ME_262HGII)this.aircraft).doEjectCatapult();
                this.astateBailoutStep = 51;
                this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setTakenMortalDamage(true, null);
                this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[0] = false;
                this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[1] = false;
                this.astateBailoutStep = -1;
                return;
              }
            }
            setPilotState(this.aircraft, i - 11, 100, false);
            if ((!this.actor.isNet()) || (this.actor.isNetMaster())) {
              try {
                Hook localHook = this.actor.findHook("_ExternalBail0" + (i - 10));
                if (localHook != null) {
                  Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, World.Rnd().nextFloat(-45.0F, 45.0F), 0.0F, 0.0F);
                  localHook.computePos(this.actor, this.actor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(), localLoc);
                  Paratrooper localParatrooper = new Paratrooper(this.actor, this.actor.getArmy(), i - 11, localLoc, this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
                  this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setTakenMortalDamage(true, null);
                  if (i == 11) {
                    this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[0] = false;
                    this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[1] = false;
                  }
                  if ((i > 10) && (i <= 19))
                    EventLog.onBailedOut(this.aircraft, i - 11); 
                }
              } catch (Exception localException) {
              } finally {
              }
              if ((this.astateBailoutStep == 19) && (this.actor == World.getPlayerAircraft()) && (!World.isPlayerGunner()) && (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.brakeShoe))
              {
                MsgDestroy.Post(Time.current() + 1000L, this.aircraft);
              }
            }
          }
        }
      }
  }

  private final void doRemoveBlister1()
  {
    if ((this.aircraft.hierMesh().chunkFindCheck("Blister1_D0") != -1) && (getPilotHealth(0) > 0.0F)) {
      this.aircraft.hierMesh().hideSubTrees("Blister1_D0");
      Wreckage localWreckage = new Wreckage((ActorHMesh)this.actor, this.aircraft.hierMesh().chunkFind("Blister1_D0"));
      localWreckage.collide(false);
      Vector3d localVector3d = new Vector3d();
      localVector3d.set(this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d); localWreckage.setSpeed(localVector3d);
    }
  }

  private final void doRemoveBlisters() {
    for (int i = 2; i < 10; i++)
      if ((this.aircraft.hierMesh().chunkFindCheck("Blister" + i + "_D0") != -1) && (getPilotHealth(i - 1) > 0.0F)) {
        this.aircraft.hierMesh().hideSubTrees("Blister" + i + "_D0");
        Wreckage localWreckage = new Wreckage((ActorHMesh)this.actor, this.aircraft.hierMesh().chunkFind("Blister" + i + "_D0"));
        localWreckage.collide(false);
        Vector3d localVector3d = new Vector3d();
        localVector3d.set(this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d); localWreckage.setSpeed(localVector3d);
      }
  }

  public void netUpdate(boolean paramBoolean1, boolean paramBoolean2, NetMsgInput paramNetMsgInput)
    throws IOException
  {
    int i;
    int j;
    int k;
    Actor localActor;
    NetObj localNetObj;
    if (paramBoolean2) {
      if (paramBoolean1)
      {
        i = paramNetMsgInput.readUnsignedByte();
        j = paramNetMsgInput.readUnsignedByte();
        k = paramNetMsgInput.readUnsignedByte();
        localActor = null;
        if (paramNetMsgInput.available() > 0) {
          localNetObj = paramNetMsgInput.readNetObj();
          if (localNetObj != null)
            localActor = (Actor)localNetObj.superObj();
        }
        switch (i) {
        case 1:
          setEngineState(localActor, j, k);
          break;
        case 2:
          setEngineSpecificDamage(localActor, j, k);
          break;
        case 3:
          explodeEngine(localActor, j);
          break;
        case 4:
          throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Unexpected command (E.S.)");
        case 5:
          throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Unexpected command (E.R.)");
        case 6:
          throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Unexpected command (E.E.)");
        case 7:
          setEngineDies(localActor, j);
          break;
        case 29:
          setEngineStuck(localActor, j);
          break;
        case 27:
          setEngineCylinderKnockOut(localActor, j, k);
          break;
        case 28:
          setEngineMagnetoKnockOut(localActor, j, k);
          break;
        case 8:
          setSootState(localActor, j, k);
          break;
        case 25:
          setEngineReadyness(localActor, j, k);
          break;
        case 26:
          throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Unexpected command (EN.ST.)");
        case 9:
          setTankState(localActor, j, k);
          break;
        case 10:
          explodeTank(localActor, j);
          break;
        case 11:
          setOilState(localActor, j, k);
          break;
        case 12:
          throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Unexpected command (G.B./On)");
        case 13:
          throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Unexpected command (G.B./Off)");
        case 14:
          throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Unexpected command (G.C.C./Off)");
        case 15:
          throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Unexpected command (A.S.S.)");
        case 30:
          throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Unexpected command (NV.LT.ST.)");
        case 31:
          throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Unexpected command (LA.LT.ST.)");
        case 16:
          throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Unexpected command (W.T.S.)");
        case 17:
          setPilotState(localActor, j, k);
          break;
        case 18:
          throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Unimplemented feature (K.P.)");
        case 19:
          throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Unimplemented feature (H.S.)");
        case 20:
          throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Unexpected command (B.O.)");
        case 21:
          setControlsDamage(localActor, k);
          break;
        case 22:
          setInternalDamage(localActor, k);
          break;
        case 23:
          setCockpitState(localActor, k);
          break;
        case 24:
          setJamBullets(j, k);
          break;
        case 34:
          ((TypeDockable)this.aircraft).typeDockableRequestAttach(localActor, j, true);
          break;
        case 35:
          ((TypeDockable)this.aircraft).typeDockableRequestDetach(localActor, j, true);
          break;
        case 32:
          ((TypeDockable)this.aircraft).typeDockableRequestAttach(localActor, j, k > 0);
          break;
        case 33:
          ((TypeDockable)this.aircraft).typeDockableRequestDetach(localActor, j, k > 0);
          break;
        case 37:
          setFMSFX(localActor, j, k);
        case 36:
        }
      }
      else
      {
        this.aircraft.jdField_net_of_type_ComMaddoxIl2EngineActorNet.postTo(this.aircraft.jdField_net_of_type_ComMaddoxIl2EngineActorNet.masterChannel(), new NetMsgGuaranted(paramNetMsgInput, paramNetMsgInput.available() > 3 ? 1 : 0));
      }
    }
    else {
      if (this.aircraft.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMirrored())
      {
        this.aircraft.jdField_net_of_type_ComMaddoxIl2EngineActorNet.post(new NetMsgGuaranted(paramNetMsgInput, paramNetMsgInput.available() > 3 ? 1 : 0));
      }

      i = paramNetMsgInput.readUnsignedByte();
      j = paramNetMsgInput.readUnsignedByte();
      k = paramNetMsgInput.readUnsignedByte();
      localActor = null;
      if (paramNetMsgInput.available() > 0) {
        localNetObj = paramNetMsgInput.readNetObj();
        if (localNetObj != null)
          localActor = (Actor)localNetObj.superObj();
      }
      switch (i) {
      case 1:
        doSetEngineState(localActor, j, k);
        break;
      case 2:
        doSetEngineSpecificDamage(j, k);
        break;
      case 3:
        doExplodeEngine(j);
        break;
      case 4:
        doSetEngineStarts(j);
        break;
      case 5:
        doSetEngineRunning(j);
        break;
      case 6:
        doSetEngineStops(j);
        break;
      case 7:
        doSetEngineDies(j);
        break;
      case 29:
        doSetEngineStuck(j);
        break;
      case 27:
        doSetEngineCylinderKnockOut(j, k);
        break;
      case 28:
        doSetEngineMagnetoKnockOut(j, k);
        break;
      case 8:
        doSetSootState(j, k);
        break;
      case 25:
        doSetEngineReadyness(j, k);
        break;
      case 26:
        doSetEngineStage(j, k);
        break;
      case 9:
        doSetTankState(localActor, j, k);
        break;
      case 10:
        doExplodeTank(j);
        break;
      case 11:
        doSetOilState(j, k);
        break;
      case 12:
        if (k == 5)
          doSetGliderBoostOn();
        else {
          throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Corrupt data in signal (G.S.B./On)");
        }
      case 13:
        if (k == 7)
          doSetGliderBoostOff();
        else {
          throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Corrupt data in signal (G.S.B./Off)");
        }
      case 14:
        if (k == 9)
          doSetGliderCutCart();
        else {
          throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Corrupt data in signal (G.C.C./Off)");
        }

      case 15:
        doSetAirShowState(k == 1);
        break;
      case 30:
        doSetNavLightsState(k == 1);
        break;
      case 31:
        doSetLandingLightState(k == 1);
        break;
      case 16:
        doSetStallState(k == 1);
        break;
      case 17:
        doSetPilotState(j, k, localActor);
        break;
      case 18:
        throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Unimplemented signal (K.P.)");
      case 19:
        throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Unimplemented signal (H.S.)");
      case 20:
        this.bIsAboutToBailout = true;
        this.astateBailoutStep = (byte)j;
        bailout();
        break;
      case 21:
        throw new RuntimeException("(" + this.aircraft.typedName() + ") A.S.: Uniexpected signal (C.A.D.)");
      case 22:
        doSetInternalDamage(k);
        break;
      case 23:
        doSetCockpitState(k);
        break;
      case 24:
        doSetJamBullets(j, k);
        break;
      case 34:
        ((TypeDockable)this.aircraft).typeDockableDoAttachToDrone(localActor, j);
        break;
      case 35:
        ((TypeDockable)this.aircraft).typeDockableDoDetachFromDrone(j);
        break;
      case 32:
        break;
      case 33:
        break;
      case 36:
        doSetFlatTopString(localActor, k);
        break;
      case 37:
        doSetFMSFX(j, k);
        break;
      case 38:
        doSetWingFold(k);
        break;
      case 39:
        doSetCockpitDoor(k);
        break;
      case 40:
        doSetArrestor(k);
      }
    }
  }

  public void netReplicate(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
    int j;
    if ((this.aircraft instanceof FW_190A8MSTL))
      j = 1;
    else {
      j = this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum();
    }
    for (int i = 0; i < j; i++) {
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].replicateToNet(paramNetMsgGuaranted);
      paramNetMsgGuaranted.writeByte(this.astateEngineStates[i]);
    }

    for (i = 0; i < 4; i++) {
      paramNetMsgGuaranted.writeByte(this.astateTankStates[i]);
    }

    for (i = 0; i < j; i++) {
      paramNetMsgGuaranted.writeByte(this.astateOilStates[i]);
    }

    paramNetMsgGuaranted.writeByte((this.bShowSmokesOn ? 1 : 0) | (this.bNavLightsOn ? 2 : 0) | (this.bLandingLightOn ? 4 : 0));

    paramNetMsgGuaranted.writeByte(this.astateCockpitState);

    paramNetMsgGuaranted.writeByte(this.astateBailoutStep);

    if ((this.aircraft instanceof TypeBomber)) {
      ((TypeBomber)this.aircraft).typeBomberReplicateToNet(paramNetMsgGuaranted);
    }
    if ((this.aircraft instanceof TypeDockable)) {
      ((TypeDockable)this.aircraft).typeDockableReplicateToNet(paramNetMsgGuaranted);
    }

    if (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasWingControl) {
      paramNetMsgGuaranted.writeByte((int)this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.wingControl);
      paramNetMsgGuaranted.writeByte((int)(this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getWing() * 255.0F));
    }

    if (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasCockpitDoorControl) {
      paramNetMsgGuaranted.writeByte((int)this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.cockpitDoorControl);
    }

    paramNetMsgGuaranted.writeByte(this.bIsEnableToBailout ? 1 : 0);

    if (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasArrestorControl) {
      paramNetMsgGuaranted.writeByte((int)this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.arrestorControl);
      paramNetMsgGuaranted.writeByte((int)(this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getArrestor() * 255.0F));
    }

    for (i = 0; i < j; i++)
      paramNetMsgGuaranted.writeByte(this.astateSootStates[i]);
  }

  public void netFirstUpdate(NetMsgInput paramNetMsgInput)
    throws IOException
  {
    int k;
    if ((this.aircraft instanceof FW_190A8MSTL))
      k = 1;
    else {
      k = this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum();
    }
    for (int i = 0; i < k; i++) {
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].replicateFromNet(paramNetMsgInput);
      j = paramNetMsgInput.readUnsignedByte();
      doSetEngineState(null, i, j);
    }

    for (i = 0; i < 4; i++) {
      j = paramNetMsgInput.readUnsignedByte();
      doSetTankState(null, i, j);
    }

    for (i = 0; i < k; i++) {
      j = paramNetMsgInput.readUnsignedByte();
      doSetOilState(i, j);
    }

    int j = paramNetMsgInput.readUnsignedByte();
    doSetAirShowState((j & 0x1) != 0);
    doSetNavLightsState((j & 0x2) != 0);
    doSetLandingLightState((j & 0x4) != 0);

    j = paramNetMsgInput.readUnsignedByte();
    doSetCockpitState(j);

    j = paramNetMsgInput.readUnsignedByte();
    if (j != 0) {
      this.bIsAboutToBailout = true;
      this.astateBailoutStep = (byte)j;
      for (i = 1; i <= Math.min(this.astateBailoutStep, 3); i++) {
        if (this.aircraft.hierMesh().chunkFindCheck("Blister" + (i - 1) + "_D0") != -1) {
          this.aircraft.hierMesh().hideSubTrees("Blister" + (i - 1) + "_D0");
        }
      }
      if ((this.astateBailoutStep >= 11) && (this.astateBailoutStep <= 20)) {
        int m = this.astateBailoutStep;
        if (this.astateBailoutStep == 20) m = 19;
        m -= 11;
        for (i = 0; i <= m; i++) {
          doRemoveBodyFromPlane(i + 1);
        }
      }
    }

    if ((this.aircraft instanceof TypeBomber)) {
      ((TypeBomber)this.aircraft).typeBomberReplicateFromNet(paramNetMsgInput);
    }
    if ((this.aircraft instanceof TypeDockable)) {
      ((TypeDockable)this.aircraft).typeDockableReplicateFromNet(paramNetMsgInput);
    }

    if (paramNetMsgInput.available() == 0) {
      return;
    }

    if (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasWingControl) {
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.wingControl = paramNetMsgInput.readUnsignedByte();
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.forceWing(paramNetMsgInput.readUnsignedByte() / 255.0F);
      this.aircraft.wingfold_ = this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getWing();
    }
    if (paramNetMsgInput.available() == 0) {
      return;
    }
    if (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasCockpitDoorControl) {
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.cockpitDoorControl = paramNetMsgInput.readUnsignedByte();
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.forceCockpitDoor(this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.cockpitDoorControl);
    }
    if (paramNetMsgInput.available() == 0)
      return;
    this.bIsEnableToBailout = (paramNetMsgInput.readUnsignedByte() == 1);
    if (paramNetMsgInput.available() == 0) {
      return;
    }
    if (this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasArrestorControl) {
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.arrestorControl = paramNetMsgInput.readUnsignedByte();
      this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.forceArrestor(paramNetMsgInput.readUnsignedByte() / 255.0F);
      this.aircraft.arrestor_ = this.aircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getArrestor();
    }
    if (paramNetMsgInput.available() == 0) {
      return;
    }
    for (i = 0; i < k; i++) {
      j = paramNetMsgInput.readUnsignedByte();
      doSetSootState(i, j);
    }
  }

  private void netToMaster(int paramInt1, int paramInt2, int paramInt3)
  {
    netToMaster(paramInt1, paramInt2, paramInt3, null);
  }

  public void netToMaster(int paramInt1, int paramInt2, int paramInt3, Actor paramActor) {
    if (!this.bIsMaster) {
      if (!this.aircraft.netNewAState_isEnable(true))
        return;
      if (this.itemsToMaster == null)
        this.itemsToMaster = new Item[41];
      if (sendedMsg(this.itemsToMaster, paramInt1, paramInt2, paramInt3, paramActor))
        return;
      try
      {
        NetMsgGuaranted localNetMsgGuaranted = this.aircraft.netNewAStateMsg(true);
        if (localNetMsgGuaranted != null) {
          localNetMsgGuaranted.writeByte((byte)paramInt1);
          localNetMsgGuaranted.writeByte((byte)paramInt2);
          localNetMsgGuaranted.writeByte((byte)paramInt3);
          ActorNet localActorNet = null;
          if (Actor.isValid(paramActor))
            localActorNet = paramActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet;
          if (localActorNet != null)
            localNetMsgGuaranted.writeNetObj(localActorNet);
          this.aircraft.netSendAStateMsg(true, localNetMsgGuaranted);
          return;
        }
      } catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
      }
    }
  }

  private void netToMirrors(int paramInt1, int paramInt2, int paramInt3)
  {
    netToMirrors(paramInt1, paramInt2, paramInt3, null);
  }

  public void netToMirrors(int paramInt1, int paramInt2, int paramInt3, Actor paramActor) {
    if (!this.aircraft.netNewAState_isEnable(false))
      return;
    if (this.itemsToMirrors == null)
      this.itemsToMirrors = new Item[41];
    if (sendedMsg(this.itemsToMirrors, paramInt1, paramInt2, paramInt3, paramActor))
      return;
    try {
      NetMsgGuaranted localNetMsgGuaranted = this.aircraft.netNewAStateMsg(false);
      if (localNetMsgGuaranted != null)
      {
        localNetMsgGuaranted.writeByte((byte)paramInt1);
        localNetMsgGuaranted.writeByte((byte)paramInt2);
        localNetMsgGuaranted.writeByte((byte)paramInt3);
        ActorNet localActorNet = null;
        if (Actor.isValid(paramActor))
          localActorNet = paramActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet;
        if (localActorNet != null)
          localNetMsgGuaranted.writeNetObj(localActorNet);
        this.aircraft.netSendAStateMsg(false, localNetMsgGuaranted);
        return;
      }
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private boolean sendedMsg(Item[] paramArrayOfItem, int paramInt1, int paramInt2, int paramInt3, Actor paramActor)
  {
    if ((paramInt1 < 0) || (paramInt1 >= paramArrayOfItem.length))
      return false;
    Item localItem = paramArrayOfItem[paramInt1];
    if (localItem == null) {
      localItem = new Item();
      localItem.set(paramInt2, paramInt3, paramActor);
      paramArrayOfItem[paramInt1] = localItem;
      return false;
    }
    if (localItem.equals(paramInt2, paramInt3, paramActor))
      return true;
    localItem.set(paramInt2, paramInt3, paramActor);
    return false;
  }

  static class Item
  {
    int msgDestination;
    int msgContext;
    Actor initiator;

    void set(int paramInt1, int paramInt2, Actor paramActor)
    {
      this.msgDestination = paramInt1;
      this.msgContext = paramInt2;
      this.initiator = paramActor;
    }
    boolean equals(int paramInt1, int paramInt2, Actor paramActor) {
      return (this.msgDestination == paramInt1) && (this.msgContext == paramInt2) && (this.initiator == paramActor);
    }
  }
}