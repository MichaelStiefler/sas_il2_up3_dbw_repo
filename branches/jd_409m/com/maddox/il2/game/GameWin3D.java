package com.maddox.il2.game;

import com.maddox.gwindow.GCaption;
import com.maddox.il2.engine.Config;
import com.maddox.il2.gui.GUIMainMenu;
import com.maddox.il2.net.GameSpy;
import com.maddox.il2.net.USGS;
import com.maddox.rts.IniFile;

public class GameWin3D extends MainWin3D
{
  private static String[] args;

  public void onBeginApp()
  {
    int i = 1;
    int j = 0;
    if (USGS.mode() == 1) {
      Main.stateStack().push(34);
    } else if (USGS.mode() == 2) {
      Main.stateStack().push(35);
    }
    else if (this.jdField_netGameSpy_of_type_ComMaddoxIl2NetGameSpy != null) {
      if (this.jdField_netGameSpy_of_type_ComMaddoxIl2NetGameSpy.isServer())
        Main.stateStack().push(35);
      else
        Main.stateStack().push(34);
      i = 0;
      GUIMainMenu localGUIMainMenu1 = (GUIMainMenu)GameState.get(2);
      localGUIMainMenu1.pPilotName.jdField_cap_of_type_ComMaddoxGwindowGCaption = new GCaption(this.jdField_netGameSpy_of_type_ComMaddoxIl2NetGameSpy.userName);
      com.maddox.il2.gui.GUIInfoName.nickName = this.jdField_netGameSpy_of_type_ComMaddoxIl2NetGameSpy.userName;
    }
    else {
      int k = Config.cur.ini.get("game", "Intro", 0);
      if (k == 1) {
        Main.stateStack().push(58);
        j = 1;
      }
      i = 0;
    }

    if (this.jdField_netGameSpy_of_type_ComMaddoxIl2NetGameSpy == null) {
      this.jdField_netGameSpyListener_of_type_ComMaddoxIl2NetGameSpy = new GameSpy();
      if (USGS.isUsed())
        this.jdField_netGameSpyListener_of_type_ComMaddoxIl2NetGameSpy.setListenerOnly(USGS.room);
      else {
        this.jdField_netGameSpyListener_of_type_ComMaddoxIl2NetGameSpy.setListenerOnly(null);
      }
    }
    if (i != 0) {
      GUIMainMenu localGUIMainMenu2 = (GUIMainMenu)GameState.get(2);
      localGUIMainMenu2.pPilotName.jdField_cap_of_type_ComMaddoxGwindowGCaption = new GCaption(USGS.name);
      com.maddox.il2.gui.GUIInfoName.nickName = USGS.name;
    }
    if (j == 0)
      Main3D.menuMusicPlay();
  }

  private static boolean tryStartGS() {
    if (args == null) return true;

    if ((args.length > 0) && ("/GS:StartedByGS".equals(args[0]))) {
      return USGS.tryStartCSP();
    }
    int i = -1;
    for (int j = 0; j < args.length; j++)
      if ("GS".equals(args[j])) {
        i = j;
        break;
      }
    if (i == -1) return true;

    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    for (int k = i + 1; k < args.length - 1; k++) {
      if ("-name".equals(args[k])) {
        str1 = args[(k + 1)];
        k++;
      } else if ("-room".equals(args[k])) {
        str2 = args[(k + 1)];
        k++;
      } else if ("-type".equals(args[k])) {
        str3 = args[(k + 1)];
        k++;
      } else if ("-maxclients".equals(args[k])) {
        str4 = args[(k + 1)];
        k++;
      }
    }
    if ((i + 1 < args.length) && (!args[(i + 1)].startsWith("-"))) {
      str5 = args[(i + 1)];
    }
    return USGS.tryStart(str1, str2, str3, str4, str5);
  }

  private void startServerGameSpy()
  {
    GameSpy localGameSpy = new GameSpy();
    for (int i = 0; i < args.length; i++) {
      if (("-room".equals(args[i])) && (i + 1 < args.length))
        localGameSpy.roomName = args[(i + 1)];
      if (("-name".equals(args[i])) && (i + 1 < args.length))
        localGameSpy.userName = args[(i + 1)];
      if (("-type".equals(args[i])) && (i + 1 < args.length))
        localGameSpy.gameType = args[(i + 1)];
      if (("-maxclients".equals(args[i])) && (i + 1 < args.length)) {
        try {
          localGameSpy.maxClients = Integer.parseInt(args[(i + 1)]);
          if (localGameSpy.maxClients < 2) localGameSpy.maxClients = 2;
          if (localGameSpy.maxClients > 32) localGameSpy.maxClients = 32; 
        }
        catch (Exception localException1) {
          localGameSpy.maxClients = 16;
        }
      }
      if (("-master".equals(args[i])) && (i + 1 < args.length)) {
        String str = args[(i + 1)];
        int j = str.indexOf(":");
        if (j >= 0) {
          if (j > 0)
            GameSpy.MASTER_ADDR = str.substring(0, j);
          try {
            GameSpy.MASTER_PORT = Integer.parseInt(str.substring(j + 1)); } catch (Exception localException2) {
          }
        } else {
          GameSpy.MASTER_ADDR = str;
        }
      }
    }
    if (localGameSpy.userName == null)
      localGameSpy.userName = args[0];
    this.jdField_netGameSpy_of_type_ComMaddoxIl2NetGameSpy = localGameSpy;
  }

  private void startClientGameSpy() {
    GameSpy localGameSpy = new GameSpy();
    for (int i = 0; i < args.length; i++) {
      if (("-connect".equals(args[i])) && (i + 1 < args.length))
        localGameSpy.serverIP = args[(i + 1)];
      if (("-name".equals(args[i])) && (i + 1 < args.length))
        localGameSpy.userName = args[(i + 1)];
    }
    if (localGameSpy.serverIP == null)
      localGameSpy.serverIP = args[0];
    this.jdField_netGameSpy_of_type_ComMaddoxIl2NetGameSpy = localGameSpy;
  }

  public static void main(String[] paramArrayOfString)
  {
    args = paramArrayOfString;

    if (!tryStartGS()) {
      return;
    }
    GameWin3D localGameWin3D = new GameWin3D();

    if ((!USGS.isUsed()) && (args != null)) {
      int i = 0;
      int j = 0;
      for (int k = 0; k < args.length; k++) {
        if ("-room".equals(args[k])) j = 1;
        if (!"-name".equals(args[k])) continue; i = 1;
      }
      if (i != 0) ((GameWin3D)localGameWin3D).startClientGameSpy();
      else if (j != 0) ((GameWin3D)localGameWin3D).startServerGameSpy();
    }

    Main.exec(localGameWin3D, "conf.ini", "il2", 1);
  }
}