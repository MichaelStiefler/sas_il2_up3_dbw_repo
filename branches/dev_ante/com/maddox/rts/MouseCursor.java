package com.maddox.rts;

public abstract interface MouseCursor
{
  public static final int NONE = 0;
  public static final int NORMAL = 1;
  public static final int CROSS = 2;
  public static final int HAND = 3;
  public static final int HELP = 4;
  public static final int IBEAM = 5;
  public static final int NO = 6;
  public static final int SIZEALL = 7;
  public static final int SIZENESW = 8;
  public static final int SIZENS = 9;
  public static final int SIZENWSE = 10;
  public static final int SIZEWE = 11;
  public static final int UP = 12;
  public static final int WAIT = 13;

  public abstract void setCursor(int paramInt);
}