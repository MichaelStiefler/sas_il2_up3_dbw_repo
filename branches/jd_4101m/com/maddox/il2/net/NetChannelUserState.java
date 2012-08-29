package com.maddox.il2.net;

public abstract interface NetChannelUserState
{
  public static final int CONNECT_INIT = -1;
  public static final int READY = 0;
  public static final int WAIT_MISSION = 1;
  public static final int SEND_MISSION = 2;
  public static final int LOADING_MISSION = 3;
  public static final int LOADED_MISSION = 4;
}