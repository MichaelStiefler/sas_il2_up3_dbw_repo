package com.maddox.il2.fm;


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
