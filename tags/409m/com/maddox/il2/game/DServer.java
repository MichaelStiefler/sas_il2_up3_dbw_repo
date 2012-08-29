// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   DServer.java

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

// Referenced classes of package com.maddox.il2.game:
//            Main

public class DServer extends com.maddox.il2.game.Main
{
    class ConsoleIO extends java.lang.Thread
    {

        public void run()
        {
            boolean flag = true;
              goto _L1
_L8:
            int i;
            if(flag)
            {
                if(consoleServer != null)
                    consoleServer.bEnableType = false;
                java.lang.System.out.print((com.maddox.rts.RTSConf.cur.console.getEnv().curNumCmd() + 1) + ">");
                java.lang.System.out.flush();
                if(consoleServer != null)
                    consoleServer.bEnableType = true;
                flag = false;
            }
            i = 0;
            try
            {
                i = java.lang.System.in.read(inputBuf);
            }
            catch(java.lang.Exception exception2)
            {
                break; /* Loop/switch isn't completed */
            }
            if(i < 0)
                break; /* Loop/switch isn't completed */
            while(i > 0) 
            {
                if(inputBuf[i - 1] >= 32)
                    break;
                i--;
            }
            if(i <= 0)
                break MISSING_BLOCK_LABEL_316;
_L4:
label0:
            {
                synchronized(oCommandSync)
                {
                    if(!com.maddox.rts.RTSConf.isRequestExitApp())
                        break label0;
                }
                break; /* Loop/switch isn't completed */
            }
            if(bCommand)
                break MISSING_BLOCK_LABEL_223;
            sCommand = new String(inputBuf, 0, i);
            bCommandNet = false;
            bCommand = true;
            obj;
            JVM INSTR monitorexit ;
            break; /* Loop/switch isn't completed */
            obj;
            JVM INSTR monitorexit ;
              goto _L2
            exception3;
            throw exception3;
_L2:
            try
            {
                java.lang.Thread.sleep(10L);
            }
            catch(java.lang.Exception exception4) { }
            if(true) goto _L4; else goto _L3
_L3:
label1:
            {
                synchronized(oCommandSync)
                {
                    if(!com.maddox.rts.RTSConf.isRequestExitApp())
                        break label1;
                }
                break; /* Loop/switch isn't completed */
            }
            if(bCommand)
                break MISSING_BLOCK_LABEL_285;
            obj1;
            JVM INSTR monitorexit ;
            break; /* Loop/switch isn't completed */
            obj1;
            JVM INSTR monitorexit ;
              goto _L5
            exception5;
            throw exception5;
_L5:
            try
            {
                java.lang.Thread.sleep(10L);
            }
            catch(java.lang.Exception exception6) { }
            if(true) goto _L3; else goto _L6
_L6:
            flag = true;
            break MISSING_BLOCK_LABEL_400;
            com.maddox.rts.RTSConf.cur.console.logFilePrintln("");
            if(consoleServer != null)
                consoleServer.type("\n");
            java.lang.System.out.print((com.maddox.rts.RTSConf.cur.console.getEnv().curNumCmd() + 1) + ">");
            java.lang.System.out.flush();
            break MISSING_BLOCK_LABEL_400;
            java.lang.Exception exception;
            exception;
            try
            {
                java.lang.Thread.sleep(1L);
            }
            catch(java.lang.Exception exception1) { }
_L1:
            if(com.maddox.rts.RTSConf.cur != null && !com.maddox.rts.RTSConf.isRequestExitApp()) goto _L8; else goto _L7
_L7:
        }

        byte inputBuf[];

        ConsoleIO()
        {
            inputBuf = new byte[2048];
        }
    }

    private class Background extends com.maddox.rts.BackgroundLoop
    {

        protected void step()
        {
            com.maddox.rts.RTSConf.cur.loopMsgs();
        }

        public Background()
        {
            setThisAsCurrent();
        }
    }

    class CmdExit extends com.maddox.rts.Cmd
    {

        public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
        {
            if(netGameSpy != null)
                netGameSpy.sendExiting();
            com.maddox.il2.game.Main.doGameExit();
            return null;
        }

        CmdExit()
        {
            _levelAccess = 1;
        }
    }


    public boolean beginApp(java.lang.String s, java.lang.String s1, int i)
    {
        com.maddox.rts.IniFile inifile = new IniFile(s);
        com.maddox.rts.RTSConf.cur = new RTSConf(inifile, "rts", i);
        com.maddox.il2.engine.Config.cur = new Config(inifile, false);
        com.maddox.il2.engine.Config.cur.mainSection = s1;
        com.maddox.il2.engine.Engine.cur = new Engine();
        new Background();
        com.maddox.rts.RTSConf.cur.start();
        com.maddox.il2.objects.air.PaintScheme.init();
        com.maddox.rts.NetEnv.cur().connect = new Connect();
        com.maddox.rts.NetEnv.cur();
        com.maddox.rts.NetEnv.host().destroy();
        new NetUser("Server");
        com.maddox.rts.NetEnv.active(true);
        com.maddox.il2.engine.Config.cur.beginSound();
        com.maddox.rts.CmdEnv.top().exec("CmdLoad com.maddox.rts.cmd.CmdLoad");
        com.maddox.rts.CmdEnv.top().exec("load com.maddox.rts.cmd.CmdFile PARAM CURENV on");
        com.maddox.rts.CmdEnv.top().exec("file .rcs");
        com.maddox.il2.ai.Regiment.loadAll();
        preloadNetClasses();
        preloadAirClasses();
        preloadChiefClasses();
        preloadStationaryClasses();
        com.maddox.rts.Time.setPause(false);
        com.maddox.rts.RTSConf.cur.loopMsgs();
        com.maddox.rts.Time.setPause(true);
        com.maddox.rts.CmdEnv.top().exec("socket LISTENER 1");
        if(com.maddox.il2.net.USGS.isUsed())
            com.maddox.il2.engine.Config.cur.netServerChannels = com.maddox.il2.net.USGS.maxclients;
        if(localPort == 0)
            localPort = com.maddox.il2.engine.Config.cur.netLocalPort;
        java.lang.String s2 = "socket udp CREATE LOCALPORT " + localPort + " SPEED " + com.maddox.il2.engine.Config.cur.netSpeed + " CHANNELS " + com.maddox.il2.engine.Config.cur.netServerChannels;
        if(com.maddox.il2.engine.Config.cur.netLocalHost != null && com.maddox.il2.engine.Config.cur.netLocalHost.length() > 0)
            s2 = s2 + " LOCALHOST " + com.maddox.il2.engine.Config.cur.netLocalHost;
        com.maddox.rts.CmdEnv.top().exec(s2);
        new NetServerParams();
        if(com.maddox.il2.net.USGS.isUsed())
            new NetUSGSControl();
        else
            new NetLocalControl();
        com.maddox.il2.game.Main.cur().netServerParams.setMode(0);
        if(com.maddox.il2.net.USGS.isUsed())
            com.maddox.il2.game.Main.cur().netServerParams.setServerName(com.maddox.il2.net.USGS.room);
        else
            com.maddox.il2.game.Main.cur().netServerParams.setServerName(com.maddox.il2.engine.Config.cur.netServerName);
        if("".equals(com.maddox.il2.game.Main.cur().netServerParams.serverName()))
            com.maddox.il2.game.Main.cur().netServerParams.setServerName("Server");
        com.maddox.il2.game.Main.cur().netServerParams.serverDescription = com.maddox.il2.engine.Config.cur.netServerDescription;
        int j = com.maddox.il2.engine.Config.cur.ini.get("NET", "difficulty", 0x2f4ff);
        if(com.maddox.il2.engine.Config.cur.newCloudsRender)
            j |= 0x1000000;
        else
            j &= 0xfeffffff;
        com.maddox.il2.game.Main.cur().netServerParams.setDifficulty(j);
        com.maddox.il2.game.Main.cur().netServerParams.setMaxUsers(com.maddox.il2.engine.Config.cur.netServerChannels);
        if(com.maddox.il2.net.USGS.isUsed())
            com.maddox.il2.game.Main.cur().netServerParams.setType(48);
        if(netGameSpy != null)
        {
            com.maddox.il2.game.Main.cur().netServerParams.setType(32);
            java.util.List list = com.maddox.rts.NetEnv.socketsBlock();
            if(list != null && list.size() > 0)
                netGameSpy.set(null, (com.maddox.rts.NetSocket)list.get(0), com.maddox.il2.engine.Config.cur.netLocalPort);
        } else
        {
            netGameSpyListener = new GameSpy();
            if(com.maddox.il2.net.USGS.isUsed())
                netGameSpyListener.setListenerOnly(com.maddox.il2.net.USGS.room);
            else
                netGameSpyListener.setListenerOnly(null);
        }
        new Chat();
        com.maddox.rts.CmdEnv.top().setCommand(new CmdExit(), "exit", "exit game");
        onBeginApp();
        com.maddox.rts.RTSConf.cur.console.getEnv().exec("file rcu");
        com.maddox.rts.RTSConf.cur.console.getEnv().exec("file " + cmdFile);
        (new ConsoleIO()).start();
        createConsoleServer();
        return true;
    }

    public void endApp()
    {
        com.maddox.il2.net.USGS.stopDedicated();
        if(com.maddox.rts.RTSConf.cur != null)
            com.maddox.rts.RTSConf.cur.stop();
        com.maddox.il2.engine.Config.cur.ini.setValue("NET", "difficulty", "" + com.maddox.il2.ai.World.cur().diffCur.get());
        com.maddox.il2.engine.Config.cur.save();
    }

    public void loopApp()
    {
        long l = com.maddox.rts.Time.real();
        while(!com.maddox.rts.RTSConf.isRequestExitApp()) 
            synchronized(com.maddox.rts.RTSConf.lockObject())
            {
                if(com.maddox.rts.BackgroundTask.isExecuted())
                {
                    com.maddox.rts.BackgroundTask.doRun();
                } else
                {
                    boolean flag;
                    boolean flag1;
                    java.lang.String s;
                    synchronized(oCommandSync)
                    {
                        flag = bCommand;
                        flag1 = bCommandNet;
                        s = sCommand;
                        bCommand = false;
                    }
                    if(flag)
                    {
                        if(flag1)
                        {
                            if(consoleServer != null)
                                consoleServer.bEnableType = false;
                            java.lang.System.out.println(s);
                            if(consoleServer != null)
                                consoleServer.bEnableType = true;
                        } else
                        {
                            if(consoleServer != null)
                                consoleServer.type(s + "\n");
                            com.maddox.rts.RTSConf.cur.console.logFilePrintln(s);
                        }
                        com.maddox.rts.RTSConf.cur.console.getEnv().exec(s);
                        if(flag1)
                        {
                            if(consoleServer != null)
                                consoleServer.bEnableType = false;
                            java.lang.System.out.print(com.maddox.rts.RTSConf.cur.console._getPrompt());
                            java.lang.System.out.flush();
                            if(consoleServer != null)
                                consoleServer.bEnableType = true;
                        }
                        if(consoleServer != null)
                            consoleServer.typeNum();
                    }
                    com.maddox.rts.RTSConf.cur.loopMsgs();
                    com.maddox.il2.net.USGS.engine();
                    long l1 = (1L + com.maddox.rts.Time.endReal()) - com.maddox.rts.Time.real();
                    if(l1 <= 0L && l + 1000L < com.maddox.rts.Time.real())
                        l1 = 1L;
                    if(l1 > 0L)
                    {
                        try
                        {
                            java.lang.Thread.sleep(l1);
                        }
                        catch(java.lang.Exception exception1) { }
                        l = com.maddox.rts.Time.real();
                    }
                }
            }
    }

    public void onBeginApp()
    {
    }

    private static boolean tryStartGS()
    {
        if(args == null)
            return true;
        int i = -1;
        for(int j = 0; j < args.length; j++)
        {
            if(!"-GS".equals(args[j]))
                continue;
            i = j;
            break;
        }

        if(i == -1)
            return true;
        java.lang.String s = null;
        java.lang.String s1 = null;
        Object obj = null;
        java.lang.String s3 = null;
        java.lang.String s4 = null;
        java.lang.String s5 = null;
        java.lang.String s6 = null;
        for(int k = i + 1; k < args.length - 1; k++)
            if("-name".equals(args[k]))
            {
                s = args[k + 1];
                k++;
            } else
            if("-room".equals(args[k]))
            {
                s1 = args[k + 1];
                k++;
            } else
            if("-port".equals(args[k]))
            {
                java.lang.String s2 = args[k + 1];
                k++;
                try
                {
                    localPort = java.lang.Integer.parseInt(s2);
                    if(localPort < 0)
                        localPort = 0;
                }
                catch(java.lang.Exception exception) { }
            } else
            if("-maxclients".equals(args[k]))
            {
                s3 = args[k + 1];
                k++;
            } else
            if("-lobbyaddr".equals(args[k]))
            {
                s4 = args[k + 1];
                k++;
            } else
            if("-lobbyport".equals(args[k]))
            {
                s5 = args[k + 1];
                k++;
            } else
            if("-groupid".equals(args[k]))
            {
                s6 = args[k + 1];
                k++;
            }

        return com.maddox.il2.net.USGS.tryStartDedicated(s, s1, s3, s4, s5, s6);
    }

    private DServer()
    {
    }

    public static void main(java.lang.String args1[])
    {
        java.lang.System.out.println("IL2 FB dedicated server v4.09m");
        args = args1;
        java.lang.String s = "confs.ini";
        if(args != null)
        {
            for(int i = 0; i < args.length - 1; i++)
                if("-conf".equals(args[i]))
                    s = args[i + 1];
                else
                if("-cmd".equals(args[i]))
                    cmdFile = args[i + 1];

        }
        if(!com.maddox.il2.game.DServer.tryStartGS())
            return;
        com.maddox.sound.BaseObject.setEnabled(false);
        com.maddox.il2.game.DServer dserver = new DServer();
        if(!com.maddox.il2.net.USGS.isUsed() && args != null)
        {
            for(int j = 0; j < args.length; j++)
                if("-room".equals(args[j]))
                {
                    if(++j < args.length)
                    {
                        dserver.netGameSpy = new GameSpy();
                        ((com.maddox.il2.game.Main) (dserver)).netGameSpy.roomName = args[j];
                        ((com.maddox.il2.game.Main) (dserver)).netGameSpy.set(args[j], null, 0);
                    }
                } else
                if("-master".equals(args[j]) && ++j < args.length)
                {
                    java.lang.String s1 = args[j];
                    int k = s1.indexOf(":");
                    if(k >= 0)
                    {
                        if(k > 0)
                            com.maddox.il2.net.GameSpy.MASTER_ADDR = s1.substring(0, k);
                        try
                        {
                            com.maddox.il2.net.GameSpy.MASTER_PORT = java.lang.Integer.parseInt(s1.substring(k + 1));
                        }
                        catch(java.lang.Exception exception) { }
                    } else
                    {
                        com.maddox.il2.net.GameSpy.MASTER_ADDR = s1;
                    }
                }

        }
        com.maddox.il2.game.DServer _tmp = dserver;
        com.maddox.il2.game.Main.exec(dserver, s, "il2 server", 1);
    }

    private static java.lang.String cmdFile = "server.cmd";
    private static int localPort = 0;
    private static java.lang.String args[];

}
