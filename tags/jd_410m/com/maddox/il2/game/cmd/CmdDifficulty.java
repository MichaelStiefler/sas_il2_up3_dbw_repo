package com.maddox.il2.game.cmd;

import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetServerParams;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdDifficulty extends Cmd
{
  public static final String WindTurbulence = "WindTurbulence";
  public static final String FlutterEffect = "FlutterEffect";
  public static final String StallsSpins = "StallsSpins";
  public static final String BlackoutsRedouts = "BlackoutsRedouts";
  public static final String EngineOverheat = "EngineOverheat";
  public static final String TorqueGyroEffects = "TorqueGyroEffects";
  public static final String RealisticLandings = "RealisticLandings";
  public static final String TakeoffLanding = "TakeoffLanding";
  public static final String CockpitAlwaysOn = "CockpitAlwaysOn";
  public static final String NoOutsideViews = "NoOutsideViews";
  public static final String HeadShake = "HeadShake";
  public static final String NoIcons = "NoIcons";
  public static final String RealisticGunnery = "RealisticGunnery";
  public static final String LimitedAmmo = "LimitedAmmo";
  public static final String LimitedFuel = "LimitedFuel";
  public static final String Vulnerability = "Vulnerability";
  public static final String NoPadlock = "NoPadlock";
  public static final String Clouds = "Clouds";
  public static final String NoMapIcons = "NoMapIcons";
  public static final String SeparateEStart = "SeparateEStart";
  public static final String NoInstantSuccess = "NoInstantSuccess";
  public static final String NoMinimapPath = "NoMinimapPath";
  public static final String NoSpeedBar = "NoSpeedBar";
  public static final String ComplexEManagement = "ComplexEManagement";
  public static final String Reliability = "Reliability";
  public static final String GLimits = "GLimits";
  public static final String RealisticPilotVulnerability = "RealisticPilotVulnerability";
  public static final String RealisticNavigationInstruments = "RealisticNavigationInstruments";
  public static final String NoPlayerIcon = "NoPlayerIcon";
  public static final String NoFogOfWarIcons = "NoFogOfWarIcons";
  private boolean bSet = false;

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    if (Main.cur().netServerParams == null) return null;
    if (Main.cur().netServerParams.isMirror()) return null;

    DifficultySettings localDifficultySettings = new DifficultySettings();
    localDifficultySettings.set(World.cur().diffCur.get());
    this.bSet = false;
    if (!Mission.isPlaying()) {
      localDifficultySettings.Wind_N_Turbulence = get(paramMap, "WindTurbulence", localDifficultySettings.Wind_N_Turbulence);
      localDifficultySettings.Flutter_Effect = get(paramMap, "FlutterEffect", localDifficultySettings.Flutter_Effect);
      localDifficultySettings.Stalls_N_Spins = get(paramMap, "StallsSpins", localDifficultySettings.Stalls_N_Spins);
      localDifficultySettings.Blackouts_N_Redouts = get(paramMap, "BlackoutsRedouts", localDifficultySettings.Blackouts_N_Redouts);
      localDifficultySettings.Engine_Overheat = get(paramMap, "EngineOverheat", localDifficultySettings.Engine_Overheat);
      localDifficultySettings.Torque_N_Gyro_Effects = get(paramMap, "TorqueGyroEffects", localDifficultySettings.Torque_N_Gyro_Effects);
      localDifficultySettings.Realistic_Landings = get(paramMap, "RealisticLandings", localDifficultySettings.Realistic_Landings);
      localDifficultySettings.Takeoff_N_Landing = get(paramMap, "TakeoffLanding", localDifficultySettings.Takeoff_N_Landing);
      localDifficultySettings.Cockpit_Always_On = get(paramMap, "CockpitAlwaysOn", localDifficultySettings.Cockpit_Always_On);
      localDifficultySettings.No_Outside_Views = get(paramMap, "NoOutsideViews", localDifficultySettings.No_Outside_Views);
      localDifficultySettings.Head_Shake = get(paramMap, "HeadShake", localDifficultySettings.Head_Shake);
      localDifficultySettings.No_Icons = get(paramMap, "NoIcons", localDifficultySettings.No_Icons);
      localDifficultySettings.Realistic_Gunnery = get(paramMap, "RealisticGunnery", localDifficultySettings.Realistic_Gunnery);
      localDifficultySettings.Limited_Ammo = get(paramMap, "LimitedAmmo", localDifficultySettings.Limited_Ammo);
      localDifficultySettings.Limited_Fuel = get(paramMap, "LimitedFuel", localDifficultySettings.Limited_Fuel);
      localDifficultySettings.Vulnerability = get(paramMap, "Vulnerability", localDifficultySettings.Vulnerability);
      localDifficultySettings.No_Padlock = get(paramMap, "NoPadlock", localDifficultySettings.No_Padlock);
      localDifficultySettings.Clouds = get(paramMap, "Clouds", localDifficultySettings.Clouds);
      localDifficultySettings.No_Map_Icons = get(paramMap, "NoMapIcons", localDifficultySettings.No_Map_Icons);
      localDifficultySettings.SeparateEStart = get(paramMap, "SeparateEStart", localDifficultySettings.SeparateEStart);
      localDifficultySettings.NoInstantSuccess = get(paramMap, "NoInstantSuccess", localDifficultySettings.NoInstantSuccess);
      localDifficultySettings.NoMinimapPath = get(paramMap, "NoMinimapPath", localDifficultySettings.NoMinimapPath);
      localDifficultySettings.NoSpeedBar = get(paramMap, "NoSpeedBar", localDifficultySettings.NoSpeedBar);
      localDifficultySettings.ComplexEManagement = get(paramMap, "ComplexEManagement", localDifficultySettings.ComplexEManagement);

      localDifficultySettings.Reliability = get(paramMap, "Reliability", localDifficultySettings.Reliability);
      localDifficultySettings.G_Limits = get(paramMap, "GLimits", localDifficultySettings.G_Limits);
      localDifficultySettings.RealisticPilotVulnerability = get(paramMap, "RealisticPilotVulnerability", localDifficultySettings.RealisticPilotVulnerability);
      localDifficultySettings.RealisticNavigationInstruments = get(paramMap, "RealisticNavigationInstruments", localDifficultySettings.RealisticNavigationInstruments);
      localDifficultySettings.No_Player_Icon = get(paramMap, "NoPlayerIcon", localDifficultySettings.No_Player_Icon);
      localDifficultySettings.No_Fog_Of_War_Icons = get(paramMap, "NoFogOfWarIcons", localDifficultySettings.No_Fog_Of_War_Icons);

      if (this.bSet) {
        Main.cur().netServerParams.setDifficulty(localDifficultySettings.get());
        return CmdEnv.RETURN_OK;
      }
    }

    INFO_HARD("  SeparateEStart    " + (localDifficultySettings.SeparateEStart ? "1" : "0"));
    INFO_HARD("  ComplexEManagement" + (localDifficultySettings.ComplexEManagement ? "1" : "0"));
    INFO_HARD("  EngineOverheat    " + (localDifficultySettings.Engine_Overheat ? "1" : "0"));
    INFO_HARD("  TorqueGyroEffects " + (localDifficultySettings.Torque_N_Gyro_Effects ? "1" : "0"));
    INFO_HARD("  FlutterEffect     " + (localDifficultySettings.Flutter_Effect ? "1" : "0"));
    INFO_HARD("  WindTurbulence    " + (localDifficultySettings.Wind_N_Turbulence ? "1" : "0"));
    INFO_HARD("  StallsSpins       " + (localDifficultySettings.Stalls_N_Spins ? "1" : "0"));
    INFO_HARD("  Vulnerability     " + (localDifficultySettings.Vulnerability ? "1" : "0"));
    INFO_HARD("  BlackoutsRedouts  " + (localDifficultySettings.Blackouts_N_Redouts ? "1" : "0"));
    INFO_HARD("  RealisticGunnery  " + (localDifficultySettings.Realistic_Gunnery ? "1" : "0"));
    INFO_HARD("  LimitedAmmo       " + (localDifficultySettings.Limited_Ammo ? "1" : "0"));
    INFO_HARD("  LimitedFuel       " + (localDifficultySettings.Limited_Fuel ? "1" : "0"));
    INFO_HARD("  CockpitAlwaysOn   " + (localDifficultySettings.Cockpit_Always_On ? "1" : "0"));
    INFO_HARD("  NoOutsideViews    " + (localDifficultySettings.No_Outside_Views ? "1" : "0"));
    INFO_HARD("  HeadShake         " + (localDifficultySettings.Head_Shake ? "1" : "0"));
    INFO_HARD("  NoIcons           " + (localDifficultySettings.No_Icons ? "1" : "0"));
    INFO_HARD("  NoPadlock         " + (localDifficultySettings.No_Padlock ? "1" : "0"));
    INFO_HARD("  Clouds            " + (localDifficultySettings.Clouds ? "1" : "0"));
    INFO_HARD("  NoInstantSuccess  " + (localDifficultySettings.NoInstantSuccess ? "1" : "0"));
    INFO_HARD("  TakeoffLanding    " + (localDifficultySettings.Takeoff_N_Landing ? "1" : "0"));
    INFO_HARD("  RealisticLandings " + (localDifficultySettings.Realistic_Landings ? "1" : "0"));
    INFO_HARD("  NoMapIcons        " + (localDifficultySettings.No_Map_Icons ? "1" : "0"));
    INFO_HARD("  NoMinimapPath     " + (localDifficultySettings.NoMinimapPath ? "1" : "0"));
    INFO_HARD("  NoSpeedBar        " + (localDifficultySettings.NoSpeedBar ? "1" : "0"));

    INFO_HARD("  Reliability  \t\t\t\t\t" + (localDifficultySettings.Reliability ? "1" : "0"));
    INFO_HARD("  GLimits    \t\t\t\t\t" + (localDifficultySettings.G_Limits ? "1" : "0"));
    INFO_HARD("  RealisticPilotVulnerability \t" + (localDifficultySettings.RealisticPilotVulnerability ? "1" : "0"));
    INFO_HARD("  RealisticNavigationInstruments " + (localDifficultySettings.RealisticNavigationInstruments ? "1" : "0"));
    INFO_HARD("  NoPlayerIcon     \t\t\t\t" + (localDifficultySettings.No_Player_Icon ? "1" : "0"));
    INFO_HARD("  NoFogOfWarIcons        \t\t" + (localDifficultySettings.No_Fog_Of_War_Icons ? "1" : "0"));

    return CmdEnv.RETURN_OK;
  }

  private boolean get(Map paramMap, String paramString, boolean paramBoolean)
  {
    if (nargs(paramMap, paramString) != 1) return paramBoolean;
    this.bSet = true;
    return arg(paramMap, paramString, 0, paramBoolean ? 1 : 0) != 0;
  }

  public CmdDifficulty() {
    this.param.put("WindTurbulence", null);
    this.param.put("FlutterEffect", null);
    this.param.put("StallsSpins", null);
    this.param.put("BlackoutsRedouts", null);
    this.param.put("EngineOverheat", null);
    this.param.put("TorqueGyroEffects", null);
    this.param.put("RealisticLandings", null);
    this.param.put("TakeoffLanding", null);
    this.param.put("CockpitAlwaysOn", null);
    this.param.put("NoOutsideViews", null);
    this.param.put("HeadShake", null);
    this.param.put("NoIcons", null);
    this.param.put("RealisticGunnery", null);
    this.param.put("LimitedAmmo", null);
    this.param.put("LimitedFuel", null);
    this.param.put("Vulnerability", null);
    this.param.put("NoPadlock", null);
    this.param.put("Clouds", null);
    this.param.put("NoMapIcons", null);
    this.param.put("SeparateEStart", null);
    this.param.put("NoInstantSuccess", null);
    this.param.put("NoMinimapPath", null);
    this.param.put("NoSpeedBar", null);
    this.param.put("ComplexEManagement", null);

    this.param.put("Reliability", null);
    this.param.put("GLimits", null);
    this.param.put("RealisticPilotVulnerability", null);
    this.param.put("RealisticNavigationInstruments", null);
    this.param.put("NoPlayerIcon", null);
    this.param.put("NoFogOfWarIcons", null);

    this._properties.put("NAME", "difficulty");
    this._levelAccess = 1;
  }
}