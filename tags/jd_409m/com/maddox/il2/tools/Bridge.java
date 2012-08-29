package com.maddox.il2.tools;

public class Bridge
{
  public static final int HIGHWAY = 128;
  public static final int RAIL = 64;
  public static final int ROAD = 32;
  public int x1;
  public int y1;
  public int x2;
  public int y2;
  public int type;

  public String toString()
  {
    return "((" + this.x1 + "," + this.y1 + "),(" + this.x2 + "," + this.y2 + ")," + TypeString(this.type) + ")";
  }

  private String TypeString(int paramInt) {
    if ((paramInt & 0x80) != 0) return "HIGHWAY";
    if ((paramInt & 0x40) != 0) return "RAIL   ";
    if ((paramInt & 0x20) != 0) return "ROAD   ";
    return "************* ERROR *************";
  }
}