// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ManString.java

package com.maddox.il2.ai.air;


public class ManString
{

    public ManString()
    {
    }

    public static java.lang.String name(int i)
    {
        if(i >= MList.length)
            return "<-Special->";
        else
            return MList[i];
    }

    public static java.lang.String tname(int i)
    {
        return TList[i];
    }

    public static java.lang.String sMName(int i)
    {
        return sMList[i];
    }

    public static java.lang.String wpname(int i)
    {
        return WPList[i];
    }

    private static java.lang.String MList[] = {
        "NONE         ", "HOLD         ", "PULL_UP      ", "LEVEL_PLANE  ", "ROLL         ", "ROLL_90      ", "ROLL_180     ", "SPIRAL_BRAKE ", "SPIRAL_UP    ", "SPIRAL_DOWN  ", 
        "CLIMB        ", "DIVING_0_RPM ", "DIVING_30DEG ", "DIVING_45DEG ", "TURN         ", "MIL_TURN     ", "LOOP         ", "LOOP_DOWN    ", "HALF_LOOP_UP ", "HALF_LOOP_DN ", 
        "STALL        ", "WAYPOINT     ", "SPEEDUP      ", "BELL         ", "FOLLOW       ", "LANDING      ", "TAKEOFF      ", "ATTACK       ", "WAVEOUT      ", "SINUS        ", 
        "ZIGZAG_UP    ", "ZIGZAG_DOWN  ", "ZIGZAG_SPIT  ", "HLF_LP_DN135 ", "HARTMANN     ", "ROLL_360     ", "STALL_PKRSN  ", "BARREL_PKRSN ", "SLIDE_LEVEL  ", "SLIDE_DESCNT ", 
        "RANVERSMAN   ", "CUBAN        ", "CUBAN_INVERT ", "GATTACK      ", "PLT_OFFLINE  ", "HANG_ON      ", "KAMIKAZE     ", "ATTACK_B_HAR ", "DELAY        ", "DITCH        ", 
        "DIVE_BOMBING ", "TORPEDO_DROP ", "CASSETTE_BMB ", "FAR_FOLLOW   ", "SPIRAL_DN_SL ", "SPIRALUPSYNC ", "SINUS_SHALOW ", "GAIN         ", "SEPARATE     ", "BE_NEAR      ", 
        "EVADE_UP     ", "EVADE_DOWN   ", "ENERGY ATTACK", "ATTACK BOMBER", "ENGINE RUNUP ", "COVER        ", "TAXI         ", "RUN_AWAY     ", "FAR_COVER    ", "VTOL_TAKEOFF ", 
        "VTOL_TD      "
    };
    private static java.lang.String TList[] = {
        "NO_TASK       ", "WAIT          ", "STAY_FORMATION", "FLY_WAYPOINT  ", "DEFENCE       ", "DEFENDING     ", "ATTACK_AIR    ", "ATTACK_GROUND "
    };
    private static java.lang.String sMList[] = {
        "NONE", "STAY_ON_THE_TAIL", "NOT_TOO_FAST", "FROM_WAYPOINT", "CONST_SPEED", "MIN_SPEED", "MAX_SPEED", "CONST_POWER", "ZERO_POWER", "BOOST_ON"
    };
    private static java.lang.String WPList[] = {
        "NORMFLY", "TAKEOFF", "LANDING", "GATTACK"
    };

}
