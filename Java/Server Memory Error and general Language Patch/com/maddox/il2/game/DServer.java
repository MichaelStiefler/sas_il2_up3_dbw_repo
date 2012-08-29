package com.maddox.il2.game;

import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.Connect;
import com.maddox.il2.net.GameSpy;
import com.maddox.il2.net.NetLocalControl;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUSGSControl;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.USGS;
import com.maddox.il2.objects.air.PaintScheme;
import com.maddox.rts.BackgroundLoop;
import com.maddox.rts.BackgroundTask;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Console;
import com.maddox.rts.IniFile;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetHost;
import com.maddox.rts.NetSocket;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;
import com.maddox.sound.BaseObject;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

public class DServer extends Main
{
  private static String cmdFile = "server.cmd";
  private static int localPort = 0;
  private static String[] args;
//  public static java.util.Vector myVector = new java.util.Vector();
  public int myInt = 0;
  

  public boolean beginApp(String paramString1, String paramString2, int paramInt)
  {
    IniFile localIniFile = new IniFile(paramString1);
    RTSConf.cur = new RTSConf(localIniFile, "rts", paramInt);
    Config.cur = new Config(localIniFile, false);
    Config.cur.mainSection = paramString2;
    Engine.cur = new Engine();
    new Background();

    RTSConf.cur.start();
    PaintScheme.init();
    NetEnv.cur().connect = new Connect();

    NetEnv.cur(); NetEnv.host().destroy(); new NetUser("Server");
    NetEnv.active(true);
    Config.cur.beginSound();

    CmdEnv.top().exec("CmdLoad com.maddox.rts.cmd.CmdLoad");
    CmdEnv.top().exec("load com.maddox.rts.cmd.CmdFile PARAM CURENV on");

    CmdEnv.top().exec("file .rcs");

    Regiment.loadAll();

    preloadNetClasses();
    preloadAirClasses();
    preloadChiefClasses();
    preloadStationaryClasses();

    Time.setPause(false);
    RTSConf.cur.loopMsgs();
    Time.setPause(true);

    CmdEnv.top().exec("socket LISTENER 1");
    if (USGS.isUsed()) {
      Config.cur.netServerChannels = USGS.maxclients;
    }
    if (localPort == 0) {
      localPort = Config.cur.netLocalPort;
    }
    String str = "socket udp CREATE LOCALPORT " + localPort + " SPEED " + Config.cur.netSpeed + " CHANNELS " + Config.cur.netServerChannels;

    if ((Config.cur.netLocalHost != null) && (Config.cur.netLocalHost.length() > 0))
      str = str + " LOCALHOST " + Config.cur.netLocalHost;
    CmdEnv.top().exec(str);

    new NetServerParams();
    if (USGS.isUsed())
      new NetUSGSControl();
    else {
      new NetLocalControl();
    }
    Main.cur().netServerParams.setMode(0);
    if (USGS.isUsed())
      Main.cur().netServerParams.setServerName(USGS.room);
    else
      Main.cur().netServerParams.setServerName(Config.cur.netServerName);
    if ("".equals(Main.cur().netServerParams.serverName()))
      Main.cur().netServerParams.setServerName("Server");
    Main.cur().netServerParams.serverDescription = Config.cur.netServerDescription;
    int i = Config.cur.ini.get("NET", "difficulty", 193791);
    if (Config.cur.newCloudsRender)
      i |= 16777216;
    else
      i &= -16777217;
    Main.cur().netServerParams.setDifficulty(i);
    Main.cur().netServerParams.setMaxUsers(Config.cur.netServerChannels);
    if (USGS.isUsed()) {
      Main.cur().netServerParams.setType(48);
    }

    if (this.netGameSpy != null) {
      Main.cur().netServerParams.setType(32);
      List localList = NetEnv.socketsBlock();
      if ((localList != null) && (localList.size() > 0))
        this.netGameSpy.set(null, (NetSocket)localList.get(0), Config.cur.netLocalPort);
    } else {
      this.netGameSpyListener = new GameSpy();
      if (USGS.isUsed())
        this.netGameSpyListener.setListenerOnly(USGS.room);
      else {
        this.netGameSpyListener.setListenerOnly(null);
      }
    }

    new Chat();
    CmdEnv.top().setCommand(new CmdExit(), "exit", "exit game");

    onBeginApp();

    RTSConf.cur.console.getEnv().exec("file rcu");
    RTSConf.cur.console.getEnv().exec("file " + cmdFile);
    new ConsoleIO().start();
    createConsoleServer();
    return true;
  }

  public void endApp() {
    USGS.stopDedicated();
    if (RTSConf.cur != null)
      RTSConf.cur.stop();
    Config.cur.ini.setValue("NET", "difficulty", "" + World.cur().diffCur.get());
    Config.cur.save();
  }

  public void loopApp() {
    long l1 = Time.real();
    IniFile inifile = new IniFile("il2server.ini", 0);
    int iMaxRam = inifile.get("Settings", "RamSize", 512);
    iMaxRam = (int)((float)iMaxRam * 0.9);
    int iMaxRamGC = (int)((float)iMaxRam * 0.8);
//    System.out.println("Will Exit if allocated RAM > " + iMaxRam + "MB.");

    while (!RTSConf.isRequestExitApp())
      synchronized (RTSConf.lockObject()) {
//        myVector.add(new byte[1024*1024]);
        
//        if ((myInt % 10) == 0) {
          Runtime runtime = Runtime.getRuntime();  

          long allocatedMemory = runtime.totalMemory();  
          long freeMemory = runtime.freeMemory();  

//          System.out.println("free memory: " + freeMemory / 1024);  
//          System.out.println("allocated memory: " + allocatedMemory / 1024);  
          if (allocatedMemory > 1024*1024*iMaxRam) {
            System.out.println("Starting manual Garbage collection ...");
            System.gc();
            long allocatedMemoryGC = runtime.totalMemory();
            System.out.println("Manual Garbage collection freed " + (allocatedMemory - allocatedMemoryGC) / 1024 + " KB RAM.");
            if (allocatedMemory > 1024*1024*iMaxRamGC) {
              iExitCode = -1;
              RTSConf.setRequestExitApp(true);
            }
          }
//        }
        if (BackgroundTask.isExecuted()) {
          BackgroundTask.doRun();
        }
        else
        {
          boolean bool1;
          boolean bool2;
          String str;
          synchronized (this.oCommandSync) {
            bool1 = this.bCommand;
            bool2 = this.bCommandNet;
            str = this.sCommand;
            this.bCommand = false;
          }
          if (bool1) {
            if (bool2) {
              if (this.consoleServer != null) this.consoleServer.bEnableType = false;
              System.out.println(str);
              if (this.consoleServer != null) this.consoleServer.bEnableType = true; 
            }
            else {
              if (this.consoleServer != null) this.consoleServer.type(str + "\n");
              RTSConf.cur.console.logFilePrintln(str);
            }

            RTSConf.cur.console.getEnv().exec(str);

            if (bool2) {
              if (this.consoleServer != null) this.consoleServer.bEnableType = false;
              System.out.print(RTSConf.cur.console._getPrompt());
              System.out.flush();
              if (this.consoleServer != null) this.consoleServer.bEnableType = true;
            }
            if (this.consoleServer != null) this.consoleServer.typeNum();
          }

          RTSConf.cur.loopMsgs();
          USGS.engine();

          long l2 = 1L + Time.endReal() - Time.real();
          if ((l2 <= 0L) && (l1 + 1000L < Time.real()))
            l2 = 1L;
          if (l2 > 0L) {
            try { Thread.sleep(l2); } catch (Exception localException) { }
            l1 = Time.real();
          }
        }
      }
  }

  public void onBeginApp()
  {
  }

  private static boolean tryStartGS()
  {
    if (args == null) return true;
    int i = -1;
    for (int j = 0; j < args.length; j++)
      if ("-GS".equals(args[j])) {
        i = j;
        break;
      }
    if (i == -1) return true;

    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    for (int k = i + 1; k < args.length - 1; k++) {
      if ("-name".equals(args[k])) {
        str1 = args[(k + 1)];
        k++;
      } else if ("-room".equals(args[k])) {
        str2 = args[(k + 1)];
        k++;
      } else if ("-port".equals(args[k])) {
        str3 = args[(k + 1)];
        k++;
        try {
          localPort = Integer.parseInt(str3);
          if (localPort < 0) localPort = 0; 
        } catch (Exception localException) {
        }
      } else if ("-maxclients".equals(args[k])) {
        str4 = args[(k + 1)];
        k++;
      } else if ("-lobbyaddr".equals(args[k])) {
        str5 = args[(k + 1)];
        k++;
      } else if ("-lobbyport".equals(args[k])) {
        str6 = args[(k + 1)];
        k++;
      } else if ("-groupid".equals(args[k])) {
        str7 = args[(k + 1)];
        k++;
      }
    }
    return USGS.tryStartDedicated(str1, str2, str4, str5, str6, str7);
  }

  public static void main(String[] paramArrayOfString)
  {
    System.out.println("IL2 FB dedicated server v4.10.1m");

    args = paramArrayOfString;

    String str1 = "confs.ini";

    if (args != null) {
      for (int i = 0; i < args.length - 1; i++) {
        if ("-conf".equals(args[i])) { str1 = args[(i + 1)]; } else {
          if (!"-cmd".equals(args[i])) continue; cmdFile = args[(i + 1)];
        }
      }
    }
    if (!tryStartGS()) {
      return;
    }
    BaseObject.setEnabled(false);
    DServer localDServer = new DServer();

    if ((!USGS.isUsed()) && 
      (args != null)) {
      for (int j = 0; j < args.length; j++) {
        if ("-room".equals(args[j])) {
          j++;
          if (j < args.length) {
            localDServer.netGameSpy = new GameSpy();
            localDServer.netGameSpy.roomName = args[j];
            localDServer.netGameSpy.set(args[j], null, 0);
          }
        } else if ("-master".equals(args[j])) {
          j++;
          if (j < args.length) {
            String str2 = args[j];
            int k = str2.indexOf(":");
            if (k >= 0) {
              if (k > 0)
                GameSpy.MASTER_ADDR = str2.substring(0, k);
              try {
                GameSpy.MASTER_PORT = Integer.parseInt(str2.substring(k + 1)); } catch (Exception localException) {
              }
            } else {
              GameSpy.MASTER_ADDR = str2;
            }
          }
        }

      }

    }

    Main.exec(localDServer, str1, "il2 server", 1);
  }

  class ConsoleIO extends Thread
  {
    byte[] inputBuf = new byte[2048];

    ConsoleIO() {  } 
    public void run() { int i = 1;
      while ((RTSConf.cur != null) && (!RTSConf.isRequestExitApp())) {
        try {
          if (i != 0) {
            if (DServer.this.consoleServer != null) DServer.this.consoleServer.bEnableType = false;
            System.out.print(RTSConf.cur.console.getEnv().curNumCmd() + 1 + ">");
            System.out.flush();
            if (DServer.this.consoleServer != null) DServer.this.consoleServer.bEnableType = true;
            i = 0;
          }
          int j = 0;
          try {
            j = System.in.read(this.inputBuf);
          } catch (Exception localException3) {
            break;
          }
          if (j < 0)
            break;
          while ((j > 0) && 
            (this.inputBuf[(j - 1)] < 32)) j--;

          if (j > 0) {
            while (true) {
              synchronized (DServer.this.oCommandSync) {
                if (RTSConf.isRequestExitApp()) break;
                if (!DServer.this.bCommand) {
                  DServer.this.sCommand = new String(this.inputBuf, 0, j);
                  DServer.this.bCommandNet = false;
                  DServer.this.bCommand = true;
                  break;
                }
              }
              try {
                Thread.sleep(10L); } catch (Exception localException3) {
              }
            }
            while (true) {
              synchronized (DServer.this.oCommandSync) {
                if (RTSConf.isRequestExitApp()) break;
                if (!DServer.this.bCommand)
                  break; 
              }
              try {
                Thread.sleep(10L); } catch (Exception localException4) {
              }
            }
            i = 1;
          } else {
            RTSConf.cur.console.logFilePrintln("");
            if (DServer.this.consoleServer != null)
              DServer.this.consoleServer.type("\n");
            System.out.print(RTSConf.cur.console.getEnv().curNumCmd() + 1 + ">");
            System.out.flush();
          } } catch (Exception localException1) {
        }
        try {
          Thread.sleep(1L);
        }
        catch (Exception localException2)
        {
        }
      }
    }
  }

  private class Background extends BackgroundLoop
  {
    protected void step()
    {
      RTSConf.cur.loopMsgs();
    }
    public Background() { setThisAsCurrent();
    }
  }

  class CmdExit extends Cmd
  {
    CmdExit()
    {
      this._levelAccess = 1;
    }

    public Object exec(CmdEnv paramCmdEnv, Map paramMap)
    {
      if (DServer.this.netGameSpy != null) {
        DServer.this.netGameSpy.sendExiting();
      }
      Main.doGameExit();
      return null;
    }
  }
}