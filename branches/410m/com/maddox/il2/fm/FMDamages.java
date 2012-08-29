// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FMDamages.java

package com.maddox.il2.fm;


public class FMDamages
{

    public FMDamages()
    {
    }

    public static final boolean readyToReturn(long l)
    {
        return (l & 0x7f81fe787bL) != 0x7f81fe787bL;
    }

    public static final boolean readyToDeath(long l)
    {
        return (l & 0x3780180078L) != 0x3780180078L;
    }

    public static final long _AILERON_L = 1L;
    public static final long _AILERON_R = 2L;
    public static final long _FUSELAGE = 4L;
    public static final long _ENGINE_1 = 8L;
    public static final long _ENGINE_2 = 16L;
    public static final long _ENGINE_3 = 32L;
    public static final long _ENGINE_4 = 64L;
    public static final long _GEAR_C = 128L;
    public static final long _FLAP_R = 256L;
    public static final long _GEAR_L = 512L;
    public static final long _GEAR_R = 1024L;
    public static final long _VER_STAB_1 = 2048L;
    public static final long _VER_STAB_2 = 4096L;
    public static final long _NOSE = 8192L;
    public static final long _OIL = 16384L;
    public static final long _RUDDER_1 = 32768L;
    public static final long _RUDDER_2 = 0x10000L;
    public static final long _HOR_STAB_L = 0x20000L;
    public static final long _HOR_STAB_R = 0x40000L;
    public static final long _TAIL_1 = 0x80000L;
    public static final long _TAIL_2 = 0x100000L;
    public static final long _TANK_1 = 0x200000L;
    public static final long _TANK_2 = 0x400000L;
    public static final long _TANK_3 = 0x800000L;
    public static final long _TANK_4 = 0x1000000L;
    public static final long _TURRET_1 = 0x2000000L;
    public static final long _TURRET_2 = 0x4000000L;
    public static final long _TURRET_3 = 0x8000000L;
    public static final long _TURRET_4 = 0x10000000L;
    public static final long _TURRET_5 = 0x20000000L;
    public static final long _TURRET_6 = 0x40000000L;
    public static final long _ELEVATOR_L = 0x80000000L;
    public static final long _ELEVATOR_R = 0x100000000L;
    public static final long _WING_ROOT_L = 0x200000000L;
    public static final long _WING_MIDDLE_L = 0x400000000L;
    public static final long _WING_END_L = 0x800000000L;
    public static final long _WING_ROOT_R = 0x1000000000L;
    public static final long _WING_MIDDLE_R = 0x2000000000L;
    public static final long _WING_END_R = 0x4000000000L;
    public static final long _NOMOREPARTS = 0x100000000000L;
    public static final long _RETURN_MASK = 0x7f81fe787bL;
    public static final long _DEATH_MASK = 0x3780180078L;
}
