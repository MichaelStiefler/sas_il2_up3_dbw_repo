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
    return CmdEnv.RETURN_OK;
  }

  private boolean get(Map paramMap, String paramString, boolean paramBoolean)
  {
    if (Cmd.nargs(paramMap, paramString) != 1) return paramBoolean;
    this.bSet = true;
    return Cmd.arg(paramMap, paramString, 0, paramBoolean ? 1 : 0) != 0;
  }

  public CmdDifficulty() {
    this.jdField_param_of_type_JavaUtilTreeMap.put("WindTurbulence", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("FlutterEffect", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("StallsSpins", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("BlackoutsRedouts", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("EngineOverheat", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("TorqueGyroEffects", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("RealisticLandings", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("TakeoffLanding", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("CockpitAlwaysOn", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("NoOutsideViews", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("HeadShake", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("NoIcons", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("RealisticGunnery", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("LimitedAmmo", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("LimitedFuel", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("Vulnerability", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("NoPadlock", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("Clouds", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("NoMapIcons", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("SeparateEStart", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("NoInstantSuccess", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("NoMinimapPath", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("NoSpeedBar", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("ComplexEManagement", null);

    this._properties.put("NAME", "difficulty");
    this._levelAccess = 1;
  }
}