package com.maddox.il2.engine;

public class LandType
{
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

  public static final boolean isLOWLAND(int paramInt)
  {
    return (paramInt & 0x1C) == 0; } 
  public static final boolean isMIDLAND(int paramInt) { return (paramInt & 0x1C) == 4; } 
  public static final boolean isMOUNT(int paramInt) { return (paramInt & 0x1C) == 8; } 
  public static final boolean isCOUNTRY(int paramInt) { return (paramInt & 0x1C) == 12; } 
  public static final boolean isCITY(int paramInt) { return (paramInt & 0x1C) == 16; } 
  public static final boolean isAIRFIELD(int paramInt) { return (paramInt & 0x1C) == 20; } 
  public static final boolean isWOOD(int paramInt) { return (paramInt & 0x1C) == 24; } 
  public static final boolean isWATER(int paramInt) { return (paramInt & 0x1C) == 28; } 
  public static final boolean isHIGHWAY(int paramInt) {
    return (paramInt & 0x80) != 0; } 
  public static final boolean isRAIL(int paramInt) { return (paramInt & 0x40) != 0; } 
  public static final boolean isROAD(int paramInt) { return (paramInt & 0x20) != 0; }

  public int getSizeXpix() {
    Engine.land(); return Landscape.getSizeXpix();
  }
  public int getSizeYpix() {
    Engine.land(); return Landscape.getSizeYpix();
  }
  public float getSizeX() {
    return Engine.land().getSizeX();
  }
  public float getSizeY() {
    return Engine.land().getSizeY();
  }
}