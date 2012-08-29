// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   DifficultySettings.java

package com.maddox.il2.ai;


public class DifficultySettings
{

    public DifficultySettings()
    {
        set(-1);
        Cockpit_Always_On = false;
        No_Icons = false;
        No_Outside_Views = false;
        No_Padlock = false;
        No_Map_Icons = false;
    }

    public boolean isRealistic()
    {
        return ((get() | 0x1000000) ^ REALISTIC_MASK) == 0;
    }

    public boolean isNormal()
    {
        return ((get() | 0x1000000) ^ NORMAL_MASK) == 0;
    }

    public boolean isEasy()
    {
        return ((get() | 0x1000000) ^ EASY_MASK) == 0;
    }

    public boolean isCustom()
    {
        return !isRealistic() && !isNormal() && !isEasy();
    }

    public void setRealistic()
    {
        boolean flag = NewCloudsRender;
        set(REALISTIC_MASK);
        NewCloudsRender = flag;
    }

    public void setNormal()
    {
        boolean flag = NewCloudsRender;
        set(NORMAL_MASK);
        NewCloudsRender = flag;
    }

    public void setEasy()
    {
        boolean flag = NewCloudsRender;
        set(EASY_MASK);
        NewCloudsRender = flag;
    }

    public void set(com.maddox.il2.ai.DifficultySettings difficultysettings)
    {
        set(difficultysettings.get());
    }

    public void set(int i)
    {
        Wind_N_Turbulence = (i & 1) != 0;
        Flutter_Effect = (i & 2) != 0;
        Stalls_N_Spins = (i & 4) != 0;
        Blackouts_N_Redouts = (i & 8) != 0;
        Engine_Overheat = (i & 0x10) != 0;
        Torque_N_Gyro_Effects = (i & 0x20) != 0;
        Realistic_Landings = (i & 0x40) != 0;
        Takeoff_N_Landing = (i & 0x80) != 0;
        Cockpit_Always_On = (i & 0x100) != 0;
        No_Outside_Views = (i & 0x200) != 0;
        Head_Shake = (i & 0x400) != 0;
        No_Icons = (i & 0x800) != 0;
        Realistic_Gunnery = (i & 0x1000) != 0;
        Limited_Ammo = (i & 0x2000) != 0;
        Limited_Fuel = (i & 0x4000) != 0;
        Vulnerability = (i & 0x8000) != 0;
        No_Padlock = (i & 0x10000) != 0;
        Clouds = (i & 0x20000) != 0;
        No_Map_Icons = (i & 0x40000) != 0;
        SeparateEStart = (i & 0x80000) != 0;
        NoInstantSuccess = (i & 0x100000) != 0;
        NoMinimapPath = (i & 0x200000) != 0;
        NoSpeedBar = (i & 0x400000) != 0;
        ComplexEManagement = (i & 0x800000) != 0;
        NewCloudsRender = (i & 0x1000000) != 0;
    }

    public int get()
    {
        int i = 0;
        if(Wind_N_Turbulence)
            i |= 1;
        if(Flutter_Effect)
            i |= 2;
        if(Stalls_N_Spins)
            i |= 4;
        if(Blackouts_N_Redouts)
            i |= 8;
        if(Engine_Overheat)
            i |= 0x10;
        if(Torque_N_Gyro_Effects)
            i |= 0x20;
        if(Realistic_Landings)
            i |= 0x40;
        if(Takeoff_N_Landing)
            i |= 0x80;
        if(Cockpit_Always_On)
            i |= 0x100;
        if(No_Outside_Views)
            i |= 0x200;
        if(Head_Shake)
            i |= 0x400;
        if(No_Icons)
            i |= 0x800;
        if(Realistic_Gunnery)
            i |= 0x1000;
        if(Limited_Ammo)
            i |= 0x2000;
        if(Limited_Fuel)
            i |= 0x4000;
        if(Vulnerability)
            i |= 0x8000;
        if(No_Padlock)
            i |= 0x10000;
        if(Clouds)
            i |= 0x20000;
        if(No_Map_Icons)
            i |= 0x40000;
        if(SeparateEStart)
            i |= 0x80000;
        if(NoInstantSuccess)
            i |= 0x100000;
        if(NoMinimapPath)
            i |= 0x200000;
        if(NoSpeedBar)
            i |= 0x400000;
        if(ComplexEManagement)
            i |= 0x800000;
        if(NewCloudsRender)
            i |= 0x1000000;
        return i;
    }

    public boolean Wind_N_Turbulence;
    public boolean Flutter_Effect;
    public boolean Stalls_N_Spins;
    public boolean Blackouts_N_Redouts;
    public boolean Engine_Overheat;
    public boolean Torque_N_Gyro_Effects;
    public boolean Realistic_Landings;
    public boolean Takeoff_N_Landing;
    public boolean Cockpit_Always_On;
    public boolean No_Outside_Views;
    public boolean Head_Shake;
    public boolean No_Icons;
    public boolean Realistic_Gunnery;
    public boolean Limited_Ammo;
    public boolean Limited_Fuel;
    public boolean Vulnerability;
    public boolean No_Padlock;
    public boolean Clouds;
    public boolean No_Map_Icons;
    public boolean SeparateEStart;
    public boolean NoInstantSuccess;
    public boolean NoMinimapPath;
    public boolean NoSpeedBar;
    public boolean ComplexEManagement;
    public boolean NewCloudsRender;
    public static final int REALISTIC_MASK = java.lang.Integer.parseInt("1111111111111111111111111", 2);
    public static final int EASY_MASK = java.lang.Integer.parseInt("1000000101000000000000000", 2);
    public static final int NORMAL_MASK = java.lang.Integer.parseInt("1100101101111110011111111", 2);

}
