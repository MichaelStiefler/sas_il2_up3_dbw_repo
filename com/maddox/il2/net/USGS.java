package com.maddox.il2.net;

import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;

public class USGS
{
  public static int version = 2;

  public static boolean bGameDfight = true;

  public static String name = null;
  public static String room = null;
  public static int maxclients = 16;
  public static String serverIP = null;
  public static final int MODE_NONE = 0;
  public static final int MODE_CLIENT = 1;
  public static final int MODE_LOCAL_SERVER = 2;
  public static final int MODE_SERVER = 3;
  public static final int MODE_DISABLED = 4;
  private static int mode = 0;
  private static int prev_mode = 0;
  public static final int CSP_STATE_DEAD = 0;
  public static final int CSP_STATE_PRELOAD = 1;
  public static final int CSP_STATE_INITMASTER = 2;
  public static final int CSP_STATE_MASTER = 3;
  public static final int CSP_STATE_INITCLIENT = 4;
  public static final int CSP_STATE_CLIENT = 5;
  private static int csp_state = 0;
  private static final int MSGFGS_CHSTA_INITMASTERSESSION = 65537;
  private static final int MSGFGS_CHSTA_MASTERSESSION = 65538;
  private static final int MSGFGS_CHSTA_INITCLIENTSESSION = 65539;
  private static final int MSGFGS_CHSTA_CLIENTSESSION = 65540;
  private static final int MSGFGS_CHSTA_TERMINATE = 65541;
  private static final int MSGFGS_SWITCHTOGS_AK = 65792;
  private static final int MSGFGS_READYTORECEIVECONNECTIONS_AK = 66048;
  private static final int MSGFGS_ERROR = 69632;
  private static final int MSGFG_CHSTA_INITMASTERSESSION_AK = 1;
  private static final int MSGFG_CHSTA_MASTERSESSION_AK = 2;
  private static final int MSGFG_CHSTA_INITCLIENTSESSION_AK = 3;
  private static final int MSGFG_CHSTA_CLIENTSESSION_AK = 4;
  private static final int MSGFG_SWITCHTOGS = 256;
  private static final int MSGFG_READYTORECEIVECONNECTIONS = 512;
  private static final int MSGFG_LOGOUTANDKILLUBICLIENT = 768;

  public static int mode()
  {
    return mode; } 
  public static boolean isUsed() { return (mode != 0) && (mode != 4); } 
  public static boolean isUsing() { return (isUsed()) || (prev_mode != 0); } 
  public static void setMode(int paramInt) {
    mode = paramInt;
  }

  public static void serverReady(int paramInt) {
    if ((csp_state == 3) && (version == 3)) { int i = cspPostMessage(512, paramInt);
      int j;
      do { try { Thread.sleep(10L); } catch (Exception localException) {
        }j = cspGetMessage(); }
      while (j <= 0);
    }
  }

  public static boolean tryStartCSP()
  {
    int i = 0;
    i = cspInitialize();

    if (i != 0)
      return false;
    i = cspConnect(5000);

    if (i != 0) {
      cspUninitialize();
      return false;
    }

    csp_state = 1;
    while (true) {
      try { Thread.sleep(10L); } catch (Exception localException) {
      }int j = cspGetMessage();

      if (j > 0)
        switch (csp_state) {
        case 1:
          if (j == 65541) {
            csp_state = 0;
            return false;
          }if (j == 65539) {
            name = cspUserName();
            room = cspRoomName();
            int k = cspGamePort();
            if (k == 0)
              k = 21000;
            serverIP = cspPrimaryGameServerIP() + ":" + k;

            csp_state = 4;
            cspPostMessage(3, 0); } else {
            if (j != 65537) break;
            name = cspUserName();
            room = cspRoomName();
            String str = cspGameName();
            if (str.indexOf("COOP") > 0) bGameDfight = false; else
              bGameDfight = true;
            csp_state = 2;
            cspPostMessage(1, 0); } break;
        case 2:
          if (j != 65538) break;
          csp_state = 3;
          version = 3;
          setMode(2);
          maxclients = cspMaxPlayer();
          if (maxclients < 2) maxclients = 2;
          if (maxclients > 32) maxclients = 32;
          return true;
        case 3:
          break;
        case 4:
          if (j != 65540) break;
          csp_state = 5;
          version = 3;
          setMode(1);
          return true;
        case 5:
        }
    }
  }

  public static boolean tryStart(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    if (paramString1 != null) {
      if ((paramString2 != null) || (paramString3 != null) || (paramString4 != null)) {
        version = 2;
        setMode(2);
        name = paramString1;
        room = paramString2;
        if ("coop".equals(paramString3)) bGameDfight = false; else
          bGameDfight = true;
        try {
          maxclients = Integer.parseInt(paramString4);
          if (maxclients < 2) maxclients = 2;
          if (maxclients > 32) maxclients = 32; 
        } catch (Exception localException) {
        }
      }
      else if (paramString5 != null) {
        version = 2;
        setMode(1);
        name = paramString1;
        serverIP = paramString5;
      }
      return true;
    }
    return false; } 
  private static native String cspUserName();

  private static native String cspUserPassword();

  private static native String cspGameName();

  private static native int cspLobbyServerID();

  private static native int cspGroupID();

  private static native String cspRoomName();

  private static native String cspRoomPassword();

  private static native int cspMaxPlayer();

  private static native String cspPrimaryGameServerIP();

  private static native String cspSecondaryGameServerIP();

  private static native int cspGamePort();

  private static native int cspErrorMsgIDSource();

  private static native int cspErrorCode();

  private static native int cspGetMessage();

  private static native int cspPostMessage(int paramInt1, int paramInt2);

  private static native int cspInitialize();

  private static native void cspUninitialize();

  private static native int cspConnect(int paramInt);

  private static native void cspDisconnect();

  public static boolean tryStartDedicated(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) { int i;
    try { i = Integer.parseInt(paramString5);
    } catch (Exception localException1) {
      return false;
    }int j;
    try { j = Integer.parseInt(paramString6);
    } catch (Exception localException2) {
      return false;
    }
    if (!rsInitialize()) {
      return false;
    }
    if (!rsLobbyServerConnection(paramString4, i)) {
      return false;
    }
    rsEngine();
    if (!rsLobbyServerLogin(paramString1, j))
      return false;
    while (true)
    {
      int k = rsResLobbyServerLogin();
      if (k != 0) {
        if (k == 1)
          break;
        if (k == 2) {
          rsLobbyServerClose();
          rsUninitialize();
          return false;
        }
      }
      rsEngine();
      try { Thread.sleep(10L); } catch (Exception localException4) {
      }
    }
    name = paramString1;
    room = paramString2;
    bGameDfight = true;
    try {
      maxclients = Integer.parseInt(paramString3);
      if (maxclients < 2) maxclients = 2;
      if (maxclients > 32) maxclients = 32; 
    } catch (Exception localException3) {
    }
    version = 2;
    setMode(3);
    return true; }

  public static void engine() {
    if (mode() == 3)
      rsEngine(); 
  }

  public static void stopDedicated() {
    if (mode() == 3) {
      rsLobbyServerClose();
      rsUninitialize();
      setMode(4);
    }
  }

  public static void update() {
    if (mode() != 3)
      return;
    String str1 = "";
    if (Mission.isPlaying())
      str1 = Mission.cur().name();
    String str2 = "dogfight";
    String str3 = "openplaying";
    DifficultySettings localDifficultySettings = new DifficultySettings();
    localDifficultySettings.set(Main.cur().netServerParams.getDifficulty());
    rsUpdateGroup(str1, str2, str3, localDifficultySettings.Wind_N_Turbulence, localDifficultySettings.Flutter_Effect, localDifficultySettings.Stalls_N_Spins, localDifficultySettings.Blackouts_N_Redouts, localDifficultySettings.Engine_Overheat, localDifficultySettings.Torque_N_Gyro_Effects, localDifficultySettings.Realistic_Landings, localDifficultySettings.Takeoff_N_Landing, localDifficultySettings.Cockpit_Always_On, localDifficultySettings.No_Outside_Views, localDifficultySettings.No_Padlock, localDifficultySettings.Head_Shake, localDifficultySettings.No_Icons, localDifficultySettings.No_Map_Icons, localDifficultySettings.Realistic_Gunnery, localDifficultySettings.Limited_Ammo, localDifficultySettings.Limited_Fuel, localDifficultySettings.Vulnerability, localDifficultySettings.Clouds, localDifficultySettings.NoMinimapPath, localDifficultySettings.NoSpeedBar, localDifficultySettings.NoInstantSuccess, localDifficultySettings.SeparateEStart, localDifficultySettings.ComplexEManagement);
  }

  private static native boolean rsInitialize();

  private static native boolean rsUninitialize();

  private static native void rsEngine();

  private static native boolean rsLobbyServerConnection(String paramString, int paramInt);

  private static native boolean rsLobbyServerLogin(String paramString, int paramInt);

  private static native int rsResLobbyServerLogin();

  private static native void rsLobbyServerClose();

  private static native void rsUpdateGroup(String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7, boolean paramBoolean8, boolean paramBoolean9, boolean paramBoolean10, boolean paramBoolean11, boolean paramBoolean12, boolean paramBoolean13, boolean paramBoolean14, boolean paramBoolean15, boolean paramBoolean16, boolean paramBoolean17, boolean paramBoolean18, boolean paramBoolean19, boolean paramBoolean20, boolean paramBoolean21, boolean paramBoolean22, boolean paramBoolean23, boolean paramBoolean24);

  static
  {
    System.loadLibrary("il2_usgs2");
  }
}