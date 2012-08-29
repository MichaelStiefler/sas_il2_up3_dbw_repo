// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FlightModelMainEx.java

package com.maddox.il2.fm;


// Referenced classes of package com.maddox.il2.fm:
//            FlightModelMain, Arm

public class FlightModelMainEx
{

    private FlightModelMainEx()
    {
    }

    public static com.maddox.il2.fm.Arm getFmArm(com.maddox.il2.fm.FlightModelMain theFM)
    {
        return theFM.Arms;
    }

    public static float getFmGCenter(com.maddox.il2.fm.FlightModelMain theFM)
    {
        return theFM.Arms.GCENTER;
    }
}
