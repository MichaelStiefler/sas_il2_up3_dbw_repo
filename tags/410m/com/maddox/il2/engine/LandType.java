// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LandType.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            Engine, Landscape

public class LandType
{

    public LandType()
    {
    }

    public static final boolean isLOWLAND(int i)
    {
        return (i & 0x1c) == 0;
    }

    public static final boolean isMIDLAND(int i)
    {
        return (i & 0x1c) == 4;
    }

    public static final boolean isMOUNT(int i)
    {
        return (i & 0x1c) == 8;
    }

    public static final boolean isCOUNTRY(int i)
    {
        return (i & 0x1c) == 12;
    }

    public static final boolean isCITY(int i)
    {
        return (i & 0x1c) == 16;
    }

    public static final boolean isAIRFIELD(int i)
    {
        return (i & 0x1c) == 20;
    }

    public static final boolean isWOOD(int i)
    {
        return (i & 0x1c) == 24;
    }

    public static final boolean isWATER(int i)
    {
        return (i & 0x1c) == 28;
    }

    public static final boolean isHIGHWAY(int i)
    {
        return (i & 0x80) != 0;
    }

    public static final boolean isRAIL(int i)
    {
        return (i & 0x40) != 0;
    }

    public static final boolean isROAD(int i)
    {
        return (i & 0x20) != 0;
    }

    public int getSizeXpix()
    {
        com.maddox.il2.engine.Engine.land();
        return com.maddox.il2.engine.Landscape.getSizeXpix();
    }

    public int getSizeYpix()
    {
        com.maddox.il2.engine.Engine.land();
        return com.maddox.il2.engine.Landscape.getSizeYpix();
    }

    public float getSizeX()
    {
        return com.maddox.il2.engine.Engine.land().getSizeX();
    }

    public float getSizeY()
    {
        return com.maddox.il2.engine.Engine.land().getSizeY();
    }

    public static final int HIGHWAY = 128;
    public static final int RAIL = 64;
    public static final int ROAD = 32;
    public static final int LOWLAND = 0;
    public static final int MIDLAND = 4;
    public static final int MOUNT = 8;
    public static final int COUNTRY = 12;
    public static final int CITY = 16;
    public static final int AIRFIELD = 20;
    public static final int WOOD = 24;
    public static final int WATER = 28;
    public static final int WATER_PURE = 28;
    public static final int WATER_COAST_MASK = 30;
    public static final int WATER_COAST_RIVER = 30;
    public static final int WATER_COAST_SEA = 31;
    public static final int RTYPE = 224;
    public static final int LTYPE = 28;
    public static final int OBJECTS = 3;
}
