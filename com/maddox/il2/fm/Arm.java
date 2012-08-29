// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Arm.java

package com.maddox.il2.fm;

import com.maddox.rts.SectFile;

public class Arm
{

    public Arm()
    {
    }

    public void set(com.maddox.il2.fm.Arm arm)
    {
        AILERON = arm.AILERON;
        FLAP = arm.FLAP;
        HOR_STAB = arm.HOR_STAB;
        VER_STAB = arm.VER_STAB;
        ELEVATOR = arm.ELEVATOR;
        RUDDER = arm.RUDDER;
        WING_ROOT = arm.WING_ROOT;
        WING_MIDDLE = arm.WING_MIDDLE;
        WING_END = arm.WING_END;
        WING_V = arm.WING_V;
        GCENTER = arm.GCENTER;
        GCENTER_Z = arm.GCENTER_Z;
        GC_AOA_SHIFT = arm.GC_AOA_SHIFT;
        GC_FLAPS_SHIFT = arm.GC_FLAPS_SHIFT;
        GC_GEAR_SHIFT = arm.GC_GEAR_SHIFT;
    }

    private float getFloat(com.maddox.rts.SectFile sectfile, java.lang.String s)
    {
        return sectfile.get("Arm", s, 0.0F);
    }

    public void load(com.maddox.rts.SectFile sectfile)
    {
        AILERON = getFloat(sectfile, "Aileron");
        FLAP = getFloat(sectfile, "Flap");
        HOR_STAB = getFloat(sectfile, "Stabilizer");
        VER_STAB = getFloat(sectfile, "Keel");
        ELEVATOR = getFloat(sectfile, "Elevator");
        RUDDER = getFloat(sectfile, "Rudder");
        WING_ROOT = getFloat(sectfile, "Wing_In");
        WING_MIDDLE = getFloat(sectfile, "Wing_Mid");
        WING_END = getFloat(sectfile, "Wing_Out");
        WING_V = getFloat(sectfile, "Wing_V");
        GCENTER = getFloat(sectfile, "GCenter");
        GCENTER_Z = getFloat(sectfile, "GCenterZ");
        GC_AOA_SHIFT = getFloat(sectfile, "GC_AOA_Shift");
        GC_FLAPS_SHIFT = getFloat(sectfile, "GC_Flaps_Shift");
        GC_GEAR_SHIFT = getFloat(sectfile, "GC_Gear_Shift");
    }

    float AILERON;
    float FLAP;
    float HOR_STAB;
    float VER_STAB;
    float ELEVATOR;
    float RUDDER;
    float WING_ROOT;
    float WING_MIDDLE;
    float WING_END;
    float WING_V;
    float GCENTER;
    float GCENTER_Z;
    float GC_AOA_SHIFT;
    float GC_FLAPS_SHIFT;
    float GC_GEAR_SHIFT;
}
