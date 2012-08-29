package com.maddox.il2.net;

public abstract interface NetMissionListener
{
  public static final int ID_NONE = -1;
  public static final int ID_NAME = 0;
  public static final int ID_LOAD_BODY = 1;
  public static final int ID_LOAD_ACTORS = 2;
  public static final int ID_LOADING = 3;
  public static final int ID_LOADED_ERR = 4;
  public static final int ID_LOADED = 5;
  public static final int ID_BEGIN = 6;
  public static final int ID_END = 7;
  public static final int ID_DESTROY = 8;
  public static final int ID_START = 9;

  public abstract void netMissionState(int paramInt, float paramFloat, String paramString);

  public abstract void netMissionCoopEnter();
}