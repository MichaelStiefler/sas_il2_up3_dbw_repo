// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Config.java

package com.maddox.il2.engine;

import com.maddox.opengl.GLCaps;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.GLContextException;
import com.maddox.opengl.GLInitCaps;
import com.maddox.opengl.Provider;
import com.maddox.opengl.ProviderException;
import com.maddox.opengl.gl;
import com.maddox.rts.CLASS;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Console;
import com.maddox.rts.Cpu86ID;
import com.maddox.rts.IniFile;
import com.maddox.rts.MainWin32;
import com.maddox.rts.MainWindow;
import com.maddox.rts.NetChannel;
import com.maddox.rts.RTSConf;
import com.maddox.rts.RTSConfWin;
import com.maddox.rts.ScreenMode;
import com.maddox.rts.net.SocksUdpSocket;
import com.maddox.sound.AudioDevice;
import com.maddox.util.UnicodeTo8bit;
import java.io.PrintStream;
import java.net.DatagramSocket;
import java.net.InetAddress;

// Referenced classes of package com.maddox.il2.engine:
//            RenderContext, GObj

public class Config
{

    public boolean isUse3Renders()
    {
        return windowUse3Renders && windowSaveAspect;
    }

    public void checkWindowUse3Renders()
    {
        if(!windowUse3Renders)
        {
            return;
        } else
        {
            windowUse3Renders = windowWidth == windowHeight * 4;
            return;
        }
    }

    public static boolean isUSE_RENDER()
    {
        return bUseRender;
    }

    private void loadGlProvider()
    {
        java.lang.String s = ini.get("GLPROVIDER", "GL", (java.lang.String)null);
        if(s != null)
            glLib = s;
        gluLib = ini.get("GLPROVIDER", "GLU", (java.lang.String)null);
    }

    public void load()
        throws com.maddox.opengl.GLContextException, com.maddox.opengl.ProviderException
    {
        loadNet();
        loadGame();
        loadConsole();
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            windowWidth = ini.get("window", "width", windowWidth);
            windowHeight = ini.get("window", "height", windowHeight);
            windowColourBits = ini.get("window", "ColourBits", windowColourBits);
            windowDepthBits = ini.get("window", "DepthBits", windowDepthBits);
            windowStencilBits = ini.get("window", "StencilBits", windowStencilBits);
            windowFullScreen = ini.get("window", "FullScreen", windowFullScreen);
            windowChangeScreenRes = ini.get("window", "ChangeScreenRes", windowChangeScreenRes);
            windowEnableResize = ini.get("window", "EnableResize", windowEnableResize);
            windowEnableClose = ini.get("window", "EnableClose", windowEnableClose);
            windowSaveAspect = ini.get("window", "SaveAspect", windowSaveAspect);
            windowUse3Renders = ini.get("window", "Use3Renders", windowUse3Renders);
            checkWindowUse3Renders();
            if(windowChangeScreenRes)
            {
                windowFullScreen = true;
            } else
            {
                com.maddox.rts.ScreenMode screenmode = com.maddox.rts.ScreenMode.startup();
                if(windowColourBits != screenmode.colourBits())
                    windowColourBits = screenmode.colourBits();
            }
            loadGlProvider();
            loadEngine();
            com.maddox.opengl.Provider.GLload(glLib);
            if(gluLib != null)
                com.maddox.opengl.Provider.GLUload(gluLib);
            beforeLoadSound();
        }
    }

    public void save()
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            ini.setValue("window", "width", java.lang.Integer.toString(windowWidth));
            ini.setValue("window", "height", java.lang.Integer.toString(windowHeight));
            ini.setValue("window", "ColourBits", java.lang.Integer.toString(windowColourBits));
            ini.setValue("window", "DepthBits", java.lang.Integer.toString(windowDepthBits));
            ini.setValue("window", "StencilBits", java.lang.Integer.toString(windowStencilBits));
            ini.setValue("window", "FullScreen", windowFullScreen ? "1" : "0");
            ini.setValue("window", "ChangeScreenRes", windowChangeScreenRes ? "1" : "0");
            ini.setValue("window", "EnableResize", windowEnableResize ? "1" : "0");
            ini.setValue("window", "EnableClose", windowEnableClose ? "1" : "0");
            ini.setValue("window", "SaveAspect", windowSaveAspect ? "1" : "0");
            ini.setValue("window", "Use3Renders", windowUse3Renders ? "1" : "0");
            ini.setValue("GLPROVIDER", "GL", glLib);
            if(gluLib != null)
                ini.setValue("GLPROVIDER", "GLU", gluLib);
            saveSound();
            saveEngine();
        }
        saveConsole();
        saveNet();
        ini.saveFile();
    }

    private void loadNet()
    {
        netLocalPort = ini.get("NET", "localPort", netLocalPort, 1000, 65000);
        netRemotePort = ini.get("NET", "remotePort", netRemotePort, 1000, 65000);
        netSpeed = ini.get("NET", "speed", netSpeed, 300, 0xf4240);
        netLocalHost = ini.get("NET", "localHost", netLocalHost);
        if(netLocalHost != null)
            try
            {
                java.net.InetAddress inetaddress = java.net.InetAddress.getByName(netLocalHost);
                java.net.DatagramSocket datagramsocket = new DatagramSocket(netLocalPort, inetaddress);
                datagramsocket.close();
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println("Unknown net address: " + netLocalHost);
                netLocalHost = null;
            }
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            netRemotePort = ini.get("NET", "remotePort", netRemotePort, 1000, 65000);
            netRemoteHost = ini.get("NET", "remoteHost", netRemoteHost);
            netRouteChannels = ini.get("NET", "routeChannels", netRouteChannels, 0, 16);
            netServerChannels = ini.get("NET", "serverChannels", netServerChannels, 1, 31);
        } else
        {
            netServerChannels = ini.get("NET", "serverChannels", netServerChannels, 1, 128);
        }
        netSkinDownload = ini.get("NET", "SkinDownload", netSkinDownload);
        netServerName = com.maddox.util.UnicodeTo8bit.load(ini.get("NET", "serverName", ""));
        netServerDescription = com.maddox.util.UnicodeTo8bit.load(ini.get("NET", "serverDescription", ""));
        com.maddox.rts.NetChannel.bCheckServerTimeSpeed = ini.get("NET", "checkServerTimeSpeed", com.maddox.rts.NetChannel.bCheckServerTimeSpeed);
        com.maddox.rts.NetChannel.bCheckClientTimeSpeed = ini.get("NET", "checkClientTimeSpeed", com.maddox.rts.NetChannel.bCheckClientTimeSpeed);
        com.maddox.rts.NetChannel.checkTimeSpeedDifferense = ini.get("NET", "checkTimeSpeedDifferense", (float)com.maddox.rts.NetChannel.checkTimeSpeedDifferense, 0.01F, 1000F);
        com.maddox.rts.NetChannel.checkTimeSpeedInterval = ini.get("NET", "checkTimeSpeedInterval", com.maddox.rts.NetChannel.checkTimeSpeedInterval, 1, 1000);
        com.maddox.rts.net.SocksUdpSocket.setProxyHost(ini.get("NET", "socksHost", (java.lang.String)null));
        com.maddox.rts.net.SocksUdpSocket.setProxyPort(ini.get("NET", "socksPort", 1080));
        com.maddox.rts.net.SocksUdpSocket.setProxyUser(ini.get("NET", "socksUser", (java.lang.String)null));
        com.maddox.rts.net.SocksUdpSocket.setProxyPassword(ini.get("NET", "socksPwd", (java.lang.String)null));
    }

    private void saveNet()
    {
        ini.setValue("NET", "localPort", "" + netLocalPort);
        ini.setValue("NET", "speed", "" + netSpeed);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            ini.setValue("NET", "remotePort", "" + netRemotePort);
            ini.setValue("NET", "remoteHost", netRemoteHost);
            ini.setValue("NET", "routeChannels", "" + netRouteChannels);
        }
        ini.setValue("NET", "serverChannels", "" + netServerChannels);
        ini.setValue("NET", "SkinDownload", netSkinDownload ? "1" : "0");
        ini.setValue("NET", "serverName", com.maddox.util.UnicodeTo8bit.save(netServerName, false));
        ini.setValue("NET", "serverDescription", com.maddox.util.UnicodeTo8bit.save(netServerDescription, false));
        if(com.maddox.rts.net.SocksUdpSocket.getProxyHost() != null)
            ini.setValue("NET", "socksHost", com.maddox.rts.net.SocksUdpSocket.getProxyHost());
        else
            ini.setValue("NET", "socksHost", "");
        if(com.maddox.rts.net.SocksUdpSocket.getProxyPort() != 1080)
            ini.setValue("NET", "socksPort", "" + com.maddox.rts.net.SocksUdpSocket.getProxyPort());
        else
            ini.deleteValue("NET", "socksPort");
        if(com.maddox.rts.net.SocksUdpSocket.getProxyUser() != null)
            ini.setValue("NET", "socksUser", com.maddox.rts.net.SocksUdpSocket.getProxyUser());
        else
            ini.deleteValue("NET", "socksUser");
        if(com.maddox.rts.net.SocksUdpSocket.getProxyPassword() != null)
            ini.setValue("NET", "socksPwd", com.maddox.rts.net.SocksUdpSocket.getProxyPassword());
        else
            ini.deleteValue("NET", "socksPwd");
    }

    private void loadGame()
    {
        b3dgunners = ini.get("game", "3dgunners", b3dgunners);
        clear_cache = ini.get("game", "ClearCache", clear_cache);
        newCloudsRender = ini.get("game", "TypeClouds", newCloudsRender);
    }

    private void loadConsole()
    {
        com.maddox.rts.RTSConf.cur.console.bWrap = ini.get("Console", "WRAP", 1, 0, 1) == 1;
        com.maddox.rts.RTSConf.cur.console.setMaxHistoryOut(ini.get("Console", "HISTORY", 128, 0, 10000));
        com.maddox.rts.RTSConf.cur.console.setMaxHistoryCmd(ini.get("Console", "HISTORYCMD", 128, 0, 10000));
        com.maddox.rts.RTSConf.cur.console.setPageHistoryOut(ini.get("Console", "PAGE", 20, 1, 100));
        com.maddox.rts.RTSConf.cur.console.setPause(ini.get("Console", "PAUSE", 1, 0, 1) == 1);
        com.maddox.rts.RTSConf.cur.console.setLogKeep(ini.get("Console", "LOGKEEP", 1, 0, 1) == 1);
        java.lang.String s = ini.getValue("Console", "LOGFILE");
        if(s.length() > 0)
            com.maddox.rts.RTSConf.cur.console.setLogFileName(s);
        else
            com.maddox.rts.RTSConf.cur.console.setLogFileName("log.lst");
        com.maddox.rts.RTSConf.cur.console.log(ini.get("Console", "LOG", 0, 0, 1) == 1);
        com.maddox.rts.Console.setTypeTimeInLogFile(ini.get("Console", "LOGTIME", 0, 0, 1) == 1);
        s = ini.getValue("Console", "LOAD");
        if(s.length() > 0)
            com.maddox.rts.RTSConf.cur.console.load(s);
    }

    private void saveConsole()
    {
        if(com.maddox.rts.RTSConf.cur.console.getEnv().levelAccess() == 0)
        {
            ini.setValue("Console", "WRAP", java.lang.Integer.toString(com.maddox.rts.RTSConf.cur.console.bWrap ? 1 : 0));
            ini.setValue("Console", "PAUSE", java.lang.Integer.toString(com.maddox.rts.RTSConf.cur.console.isPaused() ? 1 : 0));
            ini.setValue("Console", "PAGE", java.lang.Integer.toString(com.maddox.rts.RTSConf.cur.console.pageHistoryOut()));
        }
        ini.setValue("Console", "HISTORY", java.lang.Integer.toString(com.maddox.rts.RTSConf.cur.console.maxHistoryOut()));
        ini.setValue("Console", "HISTORYCMD", java.lang.Integer.toString(com.maddox.rts.RTSConf.cur.console.maxHistoryCmd()));
        ini.setValue("Console", "LOGFILE", com.maddox.rts.RTSConf.cur.console.logFileName());
        ini.setValue("Console", "LOG", java.lang.Integer.toString(com.maddox.rts.RTSConf.cur.console.isLog() ? 1 : 0));
        ini.setValue("Console", "LOGKEEP", java.lang.Integer.toString(com.maddox.rts.RTSConf.cur.console.isLogKeep() ? 1 : 0));
        ini.setValue("Console", "LOGTIME", java.lang.Integer.toString(com.maddox.rts.Console.isTypeTimeInLogFile() ? 1 : 0));
        java.lang.String s = ini.getValue("Console", "SAVE");
        if(s.length() > 0)
            com.maddox.rts.RTSConf.cur.console.save(s);
        com.maddox.rts.RTSConf.cur.console.log(false);
    }

    public void loadEngine()
    {
        loadEngine(ini.get("GLPROVIDER", "GL", (java.lang.String)null));
    }

    public void loadEngine(java.lang.String s)
    {
        if(s == null)
            s = glLib;
        java.lang.String s1 = ini.get("GLPROVIDERS", "DirectX", "");
        java.lang.String s2 = "Render_OpenGL";
        if(s1 != null && s.equalsIgnoreCase(s1))
            s2 = "Render_DirectX";
        com.maddox.il2.engine.RenderContext.loadConfig(ini, s2);
    }

    private void saveEngine()
    {
        com.maddox.il2.engine.RenderContext.saveConfig();
    }

    public boolean isDebugSound()
    {
        return bDebugSound;
    }

    public boolean isSoundUse()
    {
        return bSoundUse;
    }

    private void beforeLoadSound()
    {
        bSoundUse = ini.get("sound", "SoundUse", false);
        bDebugSound = ini.get("sound", "DebugSound", bDebugSound);
    }

    private void saveSound()
    {
        ini.setValue("sound", "SoundUse", bSoundUse ? "1" : "0");
        com.maddox.sound.AudioDevice.saveControls(ini, "sound");
    }

    public void beginSound()
    {
        com.maddox.sound.AudioDevice.setMessageMode(bDebugSound ? 1 : 0);
        com.maddox.sound.AudioDevice.loadControls(ini, "sound");
        if(bSoundUse)
        {
            bSoundUse = false;
            if(com.maddox.rts.RTSConf.cur != null && (com.maddox.rts.RTSConf.cur instanceof com.maddox.rts.RTSConfWin))
            {
                int i = com.maddox.rts.RTSConf.cur.mainWindow.hWnd();
                if(i != 0)
                    if(com.maddox.sound.AudioDevice.initialize(i, com.maddox.il2.engine.Config.engineDllName() + ".dll"))
                        bSoundUse = true;
                    else
                        java.lang.System.err.println("AudioDevice NOT initialized");
            }
        } else
        {
            com.maddox.sound.AudioDevice.initialize(0, com.maddox.il2.engine.Config.engineDllName() + ".dll");
        }
        com.maddox.sound.AudioDevice.resetControls();
    }

    public void endSound()
    {
        if(bSoundUse)
            com.maddox.sound.AudioDevice.done();
    }

    public com.maddox.opengl.GLInitCaps getGLCaps()
    {
        com.maddox.opengl.GLInitCaps glinitcaps = new GLInitCaps();
        glinitcaps.setDoubleBuffered(true);
        glinitcaps.setColourBits(windowColourBits);
        glinitcaps.setDepthBits(windowDepthBits);
        glinitcaps.setStencilBits(windowStencilBits);
        return glinitcaps;
    }

    public void setGLCaps(com.maddox.opengl.GLCaps glcaps)
    {
        windowColourBits = glcaps.getColourBits();
        windowDepthBits = glcaps.getDepthBits();
        windowStencilBits = glcaps.getStencilBits();
    }

    public com.maddox.opengl.GLContext createGlContext(com.maddox.opengl.GLContext glcontext, boolean flag, boolean flag1, int i, int j, int k, int l, 
            int i1)
        throws com.maddox.opengl.GLContextException
    {
        com.maddox.rts.ScreenMode screenmode = com.maddox.rts.ScreenMode.readCurrent();
        if(flag)
        {
            if(com.maddox.rts.ScreenMode.set(i, j, k))
            {
                i = com.maddox.rts.ScreenMode.current().width();
                j = com.maddox.rts.ScreenMode.current().height();
                k = com.maddox.rts.ScreenMode.current().colourBits();
            } else
            {
                throw new GLContextException("ScreenMode NOT changed");
            }
        } else
        if(com.maddox.rts.ScreenMode.current() != com.maddox.rts.ScreenMode.startup())
            com.maddox.rts.ScreenMode.restore();
        boolean flag2 = false;
        if(flag1)
            flag2 = ((com.maddox.rts.MainWin32)com.maddox.rts.RTSConf.cur.mainWindow).create(windowTitle, i, j);
        else
            flag2 = ((com.maddox.rts.MainWin32)com.maddox.rts.RTSConf.cur.mainWindow).create(windowTitle, windowEnableClose, windowEnableResize, i, j);
        if(!flag2)
        {
            com.maddox.rts.ScreenMode.set(screenmode);
            throw new GLContextException("Window NOT created");
        }
        i = com.maddox.rts.RTSConf.cur.mainWindow.width();
        j = com.maddox.rts.RTSConf.cur.mainWindow.height();
        com.maddox.opengl.GLInitCaps glinitcaps = new GLInitCaps();
        glinitcaps.setDoubleBuffered(true);
        glinitcaps.setColourBits(k);
        glinitcaps.setDepthBits(l);
        glinitcaps.setStencilBits(i1);
        try
        {
            if(glcontext != null)
            {
                glcontext.changeWin32(glinitcaps, com.maddox.rts.RTSConf.cur.mainWindow.hWnd(), true, i, j);
            } else
            {
                glcontext = new GLContext(glinitcaps);
                glcontext.createWin32(com.maddox.rts.RTSConf.cur.mainWindow.hWnd(), true, i, j);
            }
        }
        catch(com.maddox.opengl.GLContextException glcontextexception)
        {
            ((com.maddox.rts.MainWin32)com.maddox.rts.RTSConf.cur.mainWindow).destroy();
            com.maddox.rts.ScreenMode.set(screenmode);
            throw glcontextexception;
        }
        windowFullScreen = flag1;
        windowChangeScreenRes = flag;
        windowWidth = com.maddox.rts.RTSConf.cur.mainWindow.width();
        windowHeight = com.maddox.rts.RTSConf.cur.mainWindow.height();
        windowColourBits = glcontext.getCaps().getColourBits();
        windowDepthBits = glcontext.getCaps().getDepthBits();
        windowStencilBits = glcontext.getCaps().getStencilBits();
        checkWindowUse3Renders();
        return glcontext;
    }

    public com.maddox.opengl.GLContext createGlContext(java.lang.String s)
        throws com.maddox.opengl.GLContextException
    {
        windowTitle = s;
        return createGlContext(null, windowChangeScreenRes, windowFullScreen, windowWidth, windowHeight, windowColourBits, windowDepthBits, windowStencilBits);
    }

    public static void typeCurrentScreenMode()
    {
        com.maddox.rts.ScreenMode screenmode = com.maddox.rts.ScreenMode.current();
        java.lang.System.err.println("Current screen mode: " + screenmode.width() + "x" + screenmode.height() + "x" + screenmode.colourBits());
    }

    public static void typeScreenModes()
    {
        com.maddox.rts.ScreenMode ascreenmode[] = com.maddox.rts.ScreenMode.all();
        java.lang.System.err.print("Screen modes: ");
        for(int i = 0; i < ascreenmode.length; i++)
        {
            if(i % 4 == 0)
                java.lang.System.err.println("");
            java.lang.System.err.print("\t" + i + " " + ascreenmode[i].width() + "x" + ascreenmode[i].height() + "x" + ascreenmode[i].colourBits());
        }

        java.lang.System.err.println("");
    }

    public static void typeGLCaps()
    {
        com.maddox.opengl.GLCaps aglcaps[] = com.maddox.opengl.Provider.getGLCaps();
        java.lang.System.err.println("Caps OpenGL library:");
        for(int i = 0; i < aglcaps.length; i++)
        {
            java.lang.System.err.print(i);
            java.lang.System.err.print(aglcaps[i].getDevice() != 1 ? "  DRAW_TO_BITMAP" : "  DRAW_TO_WINDOW");
            java.lang.System.err.print(aglcaps[i].isDoubleBuffered() ? "  DOUBLEBUFFER" : "  SINGLEBUFFER");
            java.lang.System.err.print(aglcaps[i].isStereo() ? "  STEREO" : "  NOSTEREO");
            java.lang.System.err.println(aglcaps[i].getPixelType() != 1 ? "  TYPE_COLOURINDEX" : "  TYPE_RGBA");
            java.lang.System.err.print("  ColourBits: " + aglcaps[i].getColourBits());
            java.lang.System.err.print("  AlphaBits: " + aglcaps[i].getAlphaBits());
            java.lang.System.err.print("  AccumBits: " + aglcaps[i].getAccumBits());
            java.lang.System.err.print("  DepthBits: " + aglcaps[i].getDepthBits());
            java.lang.System.err.println("  StencilBits: " + aglcaps[i].getStencilBits());
        }

    }

    public static void typeGlStrings()
    {
        java.lang.System.err.println("OpenGL library:");
        java.lang.System.err.println("  Vendor: " + com.maddox.opengl.gl.GetString(7936));
        java.lang.System.err.println("  Render: " + com.maddox.opengl.gl.GetString(7937));
        java.lang.System.err.println("  Version: " + com.maddox.opengl.gl.GetString(7938));
        java.lang.System.err.println("  Extensions: " + com.maddox.opengl.gl.GetString(7939));
    }

    public static void typeProvider()
    {
        java.lang.System.err.println("OpenGL provider: " + com.maddox.opengl.Provider.GLname());
        try
        {
            java.lang.System.err.println("GLU provider: " + com.maddox.opengl.Provider.GLUname());
        }
        catch(java.lang.Exception exception) { }
    }

    public void typeContextSettings(com.maddox.opengl.GLContext glcontext)
    {
        java.lang.System.err.println("Size: " + glcontext.width() + "x" + glcontext.height());
        java.lang.System.err.println("ColorBits: " + glcontext.getCaps().getColourBits());
        java.lang.System.err.println("DepthBits: " + glcontext.getCaps().getDepthBits());
        java.lang.System.err.println("StencilBits: " + glcontext.getCaps().getStencilBits());
        java.lang.System.err.println("isDoubleBuffered: " + glcontext.getCaps().isDoubleBuffered());
    }

    public static boolean isAppEditor()
    {
        return cur.bAppEditor;
    }

    public static java.lang.String engineDllName()
    {
        if(com.maddox.rts.CLASS.ser() != 0)
        {
            if(com.maddox.rts.Cpu86ID.getVendor() == 1 && com.maddox.rts.Cpu86ID.isSSE2())
                return "il2_coreP4";
            else
                return "il2_core";
        } else
        {
            com.maddox.rts.Cpu86ID.getVendor();
            return "il2_server";
        }
    }

    public Config(com.maddox.rts.IniFile inifile, boolean flag)
    {
        ini = null;
        mainSection = "il2";
        windowTitle = "Il2";
        windowWidth = 640;
        windowHeight = 480;
        windowColourBits = 16;
        windowDepthBits = 16;
        windowStencilBits = 0;
        windowFullScreen = true;
        windowChangeScreenRes = true;
        windowEnableResize = false;
        windowEnableClose = false;
        windowSaveAspect = true;
        windowUse3Renders = false;
        glLib = "opengl32.dll";
        gluLib = null;
        netLocalPort = 21000;
        netRemotePort = 21000;
        netLocalHost = null;
        netRemoteHost = "";
        netSpeed = 5000;
        netRouteChannels = 0;
        netServerChannels = 8;
        netSkinDownload = true;
        netServerName = "";
        netServerDescription = "";
        b3dgunners = false;
        clear_cache = true;
        newCloudsRender = true;
        bSoundUse = false;
        bDebugSound = false;
        bAppEditor = false;
        bUseRender = flag;
        com.maddox.il2.engine.GObj.loadNative();
        ini = inifile;
        load();
    }

    public static final java.lang.String JRE = "JRE";
    public static final boolean AEROMASH = false;
    public static final java.lang.String PRODUCT = "FB_PF";
    private static final java.lang.String _VERSION = "4.09";
    private static final int _TRACK_SINGLE_VERSION = 29;
    public static final int TRACK_NET_VERSION = 102;
    public static final boolean TRACK_CHECK = false;
    public static final java.lang.String VERSION = "4.09m";
    public static final java.lang.String NET_VERSION = "FB_PF_v_4.09m";
    public static final int TRACK_SINGLE_VERSION = 129;
    public static java.lang.String LOCALE = "PE";
    public static final boolean bCHECK_LOCALE = true;
    public static final java.lang.String RELEASE = "ON";
    public static final java.lang.String PROTECT = "OFF";
    public static final boolean bCHECK_EXPIRED = false;
    public static final int EXPIRED_YEAR = 2005;
    public static final int EXPIRED_MONTH = 11;
    public static final int EXPIRED_DAY = 1;
    public static com.maddox.il2.engine.Config cur;
    public static final int ADAPTER_INTERPOLATOR_TICK_POS = -1000;
    public static final int ADAPTER_COLLISION_TICK_POS = -999;
    public static final int ADAPTER_RENDER_TICK_POS = 0x7fffffff;
    public static final java.lang.String CONSOLE = "Console";
    public static final java.lang.String C_WRAP = "WRAP";
    public static final java.lang.String C_HISTORY = "HISTORY";
    public static final java.lang.String C_HISTORYCMD = "HISTORYCMD";
    public static final java.lang.String C_PAGE = "PAGE";
    public static final java.lang.String C_PAUSE = "PAUSE";
    public static final java.lang.String C_LOG = "LOG";
    public static final java.lang.String C_LOGKEEP = "LOGKEEP";
    public static final java.lang.String C_LOGTIME = "LOGTIME";
    public static final java.lang.String C_LOGFILE = "LOGFILE";
    public static final java.lang.String C_LOAD = "LOAD";
    public static final java.lang.String C_SAVE = "SAVE";
    public static final java.lang.String WINDOW = "window";
    public static final java.lang.String WIDTH = "width";
    public static final java.lang.String HEIGHT = "height";
    public static final java.lang.String COLOURBITS = "ColourBits";
    public static final java.lang.String DEPTHBITS = "DepthBits";
    public static final java.lang.String STENCILBITS = "StencilBits";
    public static final java.lang.String FULLSCREEN = "FullScreen";
    public static final java.lang.String CHANGESCREENRES = "ChangeScreenRes";
    public static final java.lang.String ENABLERESIZE = "EnableResize";
    public static final java.lang.String ENABLECLOSE = "EnableClose";
    public static final java.lang.String SAVEASPECT = "SaveAspect";
    public static final java.lang.String USE3RENDERS = "Use3Renders";
    public static final java.lang.String GLPROVIDER = "GLPROVIDER";
    public static final java.lang.String GL_LIB = "GL";
    public static final java.lang.String GLU_LIB = "GLU";
    public static final java.lang.String SOUND = "sound";
    public static final java.lang.String SOUND_USE = "SoundUse";
    public static final java.lang.String SOUND_DEBUG = "DebugSound";
    public static final java.lang.String NET = "NET";
    public static final java.lang.String NET_LOCAL_PORT = "localPort";
    public static final java.lang.String NET_LOCAL_HOST = "localHost";
    public static final java.lang.String NET_REMOTE_PORT = "remotePort";
    public static final java.lang.String NET_REMOTE_HOST = "remoteHost";
    public static final java.lang.String NET_SPEED = "speed";
    public static final java.lang.String NET_ROUTE_CHANNELS = "routeChannels";
    public static final java.lang.String NET_SERVER_CHANNELS = "serverChannels";
    public static final java.lang.String NET_SKIN_DOWNLOAD = "SkinDownload";
    public static final java.lang.String NET_SERVER_NAME = "serverName";
    public static final java.lang.String NET_SERVER_DESCRIPTION = "serverDescription";
    public static final java.lang.String NET_SOCKS_HOST = "socksHost";
    public static final java.lang.String NET_SOCKS_PORT = "socksPort";
    public static final java.lang.String NET_SOCKS_USER = "socksUser";
    public static final java.lang.String NET_SOCKS_PWD = "socksPwd";
    public static final java.lang.String GAME = "game";
    public static final java.lang.String GAME_3DGUNNERS = "3dgunners";
    public static final java.lang.String GAME_CLEARCACHE = "ClearCache";
    public static final java.lang.String GAME_TYPECLOUDS = "TypeClouds";
    public com.maddox.rts.IniFile ini;
    public java.lang.String mainSection;
    public java.lang.String windowTitle;
    public int windowWidth;
    public int windowHeight;
    public int windowColourBits;
    public int windowDepthBits;
    public int windowStencilBits;
    public boolean windowFullScreen;
    public boolean windowChangeScreenRes;
    public boolean windowEnableResize;
    public boolean windowEnableClose;
    public boolean windowSaveAspect;
    public boolean windowUse3Renders;
    public java.lang.String glLib;
    public java.lang.String gluLib;
    public int netLocalPort;
    public int netRemotePort;
    public java.lang.String netLocalHost;
    public java.lang.String netRemoteHost;
    public int netSpeed;
    public int netRouteChannels;
    public int netServerChannels;
    public boolean netSkinDownload;
    public java.lang.String netServerName;
    public java.lang.String netServerDescription;
    public boolean b3dgunners;
    public boolean clear_cache;
    public boolean newCloudsRender;
    private static boolean bUseRender = false;
    protected boolean bSoundUse;
    private boolean bDebugSound;
    public boolean bAppEditor;

}
