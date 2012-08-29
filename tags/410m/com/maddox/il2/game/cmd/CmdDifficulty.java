// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdDifficulty.java

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

public class CmdDifficulty extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return null;
        if(com.maddox.il2.game.Main.cur().netServerParams.isMirror())
            return null;
        com.maddox.il2.ai.DifficultySettings difficultysettings = new DifficultySettings();
        difficultysettings.set(com.maddox.il2.ai.World.cur().diffCur.get());
        bSet = false;
        if(!com.maddox.il2.game.Mission.isPlaying())
        {
            difficultysettings.Wind_N_Turbulence = get(map, "WindTurbulence", difficultysettings.Wind_N_Turbulence);
            difficultysettings.Flutter_Effect = get(map, "FlutterEffect", difficultysettings.Flutter_Effect);
            difficultysettings.Stalls_N_Spins = get(map, "StallsSpins", difficultysettings.Stalls_N_Spins);
            difficultysettings.Blackouts_N_Redouts = get(map, "BlackoutsRedouts", difficultysettings.Blackouts_N_Redouts);
            difficultysettings.Engine_Overheat = get(map, "EngineOverheat", difficultysettings.Engine_Overheat);
            difficultysettings.Torque_N_Gyro_Effects = get(map, "TorqueGyroEffects", difficultysettings.Torque_N_Gyro_Effects);
            difficultysettings.Realistic_Landings = get(map, "RealisticLandings", difficultysettings.Realistic_Landings);
            difficultysettings.Takeoff_N_Landing = get(map, "TakeoffLanding", difficultysettings.Takeoff_N_Landing);
            difficultysettings.Cockpit_Always_On = get(map, "CockpitAlwaysOn", difficultysettings.Cockpit_Always_On);
            difficultysettings.No_Outside_Views = get(map, "NoOutsideViews", difficultysettings.No_Outside_Views);
            difficultysettings.Head_Shake = get(map, "HeadShake", difficultysettings.Head_Shake);
            difficultysettings.No_Icons = get(map, "NoIcons", difficultysettings.No_Icons);
            difficultysettings.Realistic_Gunnery = get(map, "RealisticGunnery", difficultysettings.Realistic_Gunnery);
            difficultysettings.Limited_Ammo = get(map, "LimitedAmmo", difficultysettings.Limited_Ammo);
            difficultysettings.Limited_Fuel = get(map, "LimitedFuel", difficultysettings.Limited_Fuel);
            difficultysettings.Vulnerability = get(map, "Vulnerability", difficultysettings.Vulnerability);
            difficultysettings.No_Padlock = get(map, "NoPadlock", difficultysettings.No_Padlock);
            difficultysettings.Clouds = get(map, "Clouds", difficultysettings.Clouds);
            difficultysettings.No_Map_Icons = get(map, "NoMapIcons", difficultysettings.No_Map_Icons);
            difficultysettings.SeparateEStart = get(map, "SeparateEStart", difficultysettings.SeparateEStart);
            difficultysettings.NoInstantSuccess = get(map, "NoInstantSuccess", difficultysettings.NoInstantSuccess);
            difficultysettings.NoMinimapPath = get(map, "NoMinimapPath", difficultysettings.NoMinimapPath);
            difficultysettings.NoSpeedBar = get(map, "NoSpeedBar", difficultysettings.NoSpeedBar);
            difficultysettings.ComplexEManagement = get(map, "ComplexEManagement", difficultysettings.ComplexEManagement);
            difficultysettings.Reliability = get(map, "Reliability", difficultysettings.Reliability);
            difficultysettings.G_Limits = get(map, "GLimits", difficultysettings.G_Limits);
            difficultysettings.RealisticPilotVulnerability = get(map, "RealisticPilotVulnerability", difficultysettings.RealisticPilotVulnerability);
            difficultysettings.RealisticNavigationInstruments = get(map, "RealisticNavigationInstruments", difficultysettings.RealisticNavigationInstruments);
            difficultysettings.No_Player_Icon = get(map, "NoPlayerIcon", difficultysettings.No_Player_Icon);
            difficultysettings.No_Fog_Of_War_Icons = get(map, "NoFogOfWarIcons", difficultysettings.No_Fog_Of_War_Icons);
            if(bSet)
            {
                com.maddox.il2.game.Main.cur().netServerParams.setDifficulty(difficultysettings.get());
                return com.maddox.rts.CmdEnv.RETURN_OK;
            }
        }
        INFO_HARD("  SeparateEStart    " + (difficultysettings.SeparateEStart ? "1" : "0"));
        INFO_HARD("  ComplexEManagement" + (difficultysettings.ComplexEManagement ? "1" : "0"));
        INFO_HARD("  EngineOverheat    " + (difficultysettings.Engine_Overheat ? "1" : "0"));
        INFO_HARD("  TorqueGyroEffects " + (difficultysettings.Torque_N_Gyro_Effects ? "1" : "0"));
        INFO_HARD("  FlutterEffect     " + (difficultysettings.Flutter_Effect ? "1" : "0"));
        INFO_HARD("  WindTurbulence    " + (difficultysettings.Wind_N_Turbulence ? "1" : "0"));
        INFO_HARD("  StallsSpins       " + (difficultysettings.Stalls_N_Spins ? "1" : "0"));
        INFO_HARD("  Vulnerability     " + (difficultysettings.Vulnerability ? "1" : "0"));
        INFO_HARD("  BlackoutsRedouts  " + (difficultysettings.Blackouts_N_Redouts ? "1" : "0"));
        INFO_HARD("  RealisticGunnery  " + (difficultysettings.Realistic_Gunnery ? "1" : "0"));
        INFO_HARD("  LimitedAmmo       " + (difficultysettings.Limited_Ammo ? "1" : "0"));
        INFO_HARD("  LimitedFuel       " + (difficultysettings.Limited_Fuel ? "1" : "0"));
        INFO_HARD("  CockpitAlwaysOn   " + (difficultysettings.Cockpit_Always_On ? "1" : "0"));
        INFO_HARD("  NoOutsideViews    " + (difficultysettings.No_Outside_Views ? "1" : "0"));
        INFO_HARD("  HeadShake         " + (difficultysettings.Head_Shake ? "1" : "0"));
        INFO_HARD("  NoIcons           " + (difficultysettings.No_Icons ? "1" : "0"));
        INFO_HARD("  NoPadlock         " + (difficultysettings.No_Padlock ? "1" : "0"));
        INFO_HARD("  Clouds            " + (difficultysettings.Clouds ? "1" : "0"));
        INFO_HARD("  NoInstantSuccess  " + (difficultysettings.NoInstantSuccess ? "1" : "0"));
        INFO_HARD("  TakeoffLanding    " + (difficultysettings.Takeoff_N_Landing ? "1" : "0"));
        INFO_HARD("  RealisticLandings " + (difficultysettings.Realistic_Landings ? "1" : "0"));
        INFO_HARD("  NoMapIcons        " + (difficultysettings.No_Map_Icons ? "1" : "0"));
        INFO_HARD("  NoMinimapPath     " + (difficultysettings.NoMinimapPath ? "1" : "0"));
        INFO_HARD("  NoSpeedBar        " + (difficultysettings.NoSpeedBar ? "1" : "0"));
        INFO_HARD("  Reliability  \t\t\t\t\t" + (difficultysettings.Reliability ? "1" : "0"));
        INFO_HARD("  GLimits    \t\t\t\t\t" + (difficultysettings.G_Limits ? "1" : "0"));
        INFO_HARD("  RealisticPilotVulnerability \t" + (difficultysettings.RealisticPilotVulnerability ? "1" : "0"));
        INFO_HARD("  RealisticNavigationInstruments " + (difficultysettings.RealisticNavigationInstruments ? "1" : "0"));
        INFO_HARD("  NoPlayerIcon     \t\t\t\t" + (difficultysettings.No_Player_Icon ? "1" : "0"));
        INFO_HARD("  NoFogOfWarIcons        \t\t" + (difficultysettings.No_Fog_Of_War_Icons ? "1" : "0"));
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    private boolean get(java.util.Map map, java.lang.String s, boolean flag)
    {
        if(com.maddox.il2.game.cmd.CmdDifficulty.nargs(map, s) != 1)
        {
            return flag;
        } else
        {
            bSet = true;
            return com.maddox.il2.game.cmd.CmdDifficulty.arg(map, s, 0, flag ? 1 : 0) != 0;
        }
    }

    public CmdDifficulty()
    {
        bSet = false;
        param.put("WindTurbulence", null);
        param.put("FlutterEffect", null);
        param.put("StallsSpins", null);
        param.put("BlackoutsRedouts", null);
        param.put("EngineOverheat", null);
        param.put("TorqueGyroEffects", null);
        param.put("RealisticLandings", null);
        param.put("TakeoffLanding", null);
        param.put("CockpitAlwaysOn", null);
        param.put("NoOutsideViews", null);
        param.put("HeadShake", null);
        param.put("NoIcons", null);
        param.put("RealisticGunnery", null);
        param.put("LimitedAmmo", null);
        param.put("LimitedFuel", null);
        param.put("Vulnerability", null);
        param.put("NoPadlock", null);
        param.put("Clouds", null);
        param.put("NoMapIcons", null);
        param.put("SeparateEStart", null);
        param.put("NoInstantSuccess", null);
        param.put("NoMinimapPath", null);
        param.put("NoSpeedBar", null);
        param.put("ComplexEManagement", null);
        param.put("Reliability", null);
        param.put("GLimits", null);
        param.put("RealisticPilotVulnerability", null);
        param.put("RealisticNavigationInstruments", null);
        param.put("NoPlayerIcon", null);
        param.put("NoFogOfWarIcons", null);
        _properties.put("NAME", "difficulty");
        _levelAccess = 1;
    }

    public static final java.lang.String WindTurbulence = "WindTurbulence";
    public static final java.lang.String FlutterEffect = "FlutterEffect";
    public static final java.lang.String StallsSpins = "StallsSpins";
    public static final java.lang.String BlackoutsRedouts = "BlackoutsRedouts";
    public static final java.lang.String EngineOverheat = "EngineOverheat";
    public static final java.lang.String TorqueGyroEffects = "TorqueGyroEffects";
    public static final java.lang.String RealisticLandings = "RealisticLandings";
    public static final java.lang.String TakeoffLanding = "TakeoffLanding";
    public static final java.lang.String CockpitAlwaysOn = "CockpitAlwaysOn";
    public static final java.lang.String NoOutsideViews = "NoOutsideViews";
    public static final java.lang.String HeadShake = "HeadShake";
    public static final java.lang.String NoIcons = "NoIcons";
    public static final java.lang.String RealisticGunnery = "RealisticGunnery";
    public static final java.lang.String LimitedAmmo = "LimitedAmmo";
    public static final java.lang.String LimitedFuel = "LimitedFuel";
    public static final java.lang.String Vulnerability = "Vulnerability";
    public static final java.lang.String NoPadlock = "NoPadlock";
    public static final java.lang.String Clouds = "Clouds";
    public static final java.lang.String NoMapIcons = "NoMapIcons";
    public static final java.lang.String SeparateEStart = "SeparateEStart";
    public static final java.lang.String NoInstantSuccess = "NoInstantSuccess";
    public static final java.lang.String NoMinimapPath = "NoMinimapPath";
    public static final java.lang.String NoSpeedBar = "NoSpeedBar";
    public static final java.lang.String ComplexEManagement = "ComplexEManagement";
    public static final java.lang.String Reliability = "Reliability";
    public static final java.lang.String GLimits = "GLimits";
    public static final java.lang.String RealisticPilotVulnerability = "RealisticPilotVulnerability";
    public static final java.lang.String RealisticNavigationInstruments = "RealisticNavigationInstruments";
    public static final java.lang.String NoPlayerIcon = "NoPlayerIcon";
    public static final java.lang.String NoFogOfWarIcons = "NoFogOfWarIcons";
    private boolean bSet;
}
