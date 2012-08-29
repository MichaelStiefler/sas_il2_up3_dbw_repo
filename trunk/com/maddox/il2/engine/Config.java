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
import java.util.ArrayList;

public class Config
{
  public static final String JRE = "JRE";
  public static final boolean AEROMASH = false;
  public static final String PRODUCT = "FB_PF";
  private static final String _VERSION = "4.10.1";
  private static final int _TRACK_SINGLE_VERSION = 30;
  public static final int TRACK_NET_VERSION = 103;
  public static final boolean TRACK_CHECK = false;
  public static final String VERSION = "4.10.1m";
  public static final String NET_VERSION = "FB_PF_v_4.10.1m";
  public static final int TRACK_SINGLE_VERSION = 130;
  public static String LOCALE = "PE";
  public static final boolean bCHECK_LOCALE = true;
  public static final String RELEASE = "ON";
  public static final String PROTECT = "OFF";
  public static final boolean bCHECK_EXPIRED = false;
  public static final int EXPIRED_YEAR = 2005;
  public static final int EXPIRED_MONTH = 11;
  public static final int EXPIRED_DAY = 1;
  public static Config cur;
  public static final int ADAPTER_INTERPOLATOR_TICK_POS = -1000;
  public static final int ADAPTER_COLLISION_TICK_POS = -999;
  public static final int ADAPTER_RENDER_TICK_POS = 2147483647;
  public static final String CONSOLE = "Console";
  public static final String C_WRAP = "WRAP";
  public static final String C_HISTORY = "HISTORY";
  public static final String C_HISTORYCMD = "HISTORYCMD";
  public static final String C_PAGE = "PAGE";
  public static final String C_PAUSE = "PAUSE";
  public static final String C_LOG = "LOG";
  public static final String C_LOGKEEP = "LOGKEEP";
  public static final String C_LOGTIME = "LOGTIME";
  public static final String C_LOGFILE = "LOGFILE";
  public static final String C_LOAD = "LOAD";
  public static final String C_SAVE = "SAVE";
  public static final String WINDOW = "window";
  public static final String WIDTH = "width";
  public static final String HEIGHT = "height";
  public static final String COLOURBITS = "ColourBits";
  public static final String DEPTHBITS = "DepthBits";
  public static final String STENCILBITS = "StencilBits";
  public static final String FULLSCREEN = "FullScreen";
  public static final String CHANGESCREENRES = "ChangeScreenRes";
  public static final String ENABLERESIZE = "EnableResize";
  public static final String ENABLECLOSE = "EnableClose";
  public static final String SAVEASPECT = "SaveAspect";
  public static final String USE3RENDERS = "Use3Renders";
  public static final String GLPROVIDER = "GLPROVIDER";
  public static final String GL_LIB = "GL";
  public static final String GLU_LIB = "GLU";
  public static final String SOUND = "sound";
  public static final String SOUND_USE = "SoundUse";
  public static final String SOUND_DEBUG = "DebugSound";
  public static final String NET = "NET";
  public static final String NET_LOCAL_PORT = "localPort";
  public static final String NET_LOCAL_HOST = "localHost";
  public static final String NET_REMOTE_PORT = "remotePort";
  public static final String NET_REMOTE_HOST = "remoteHost";
  public static final String NET_SPEED = "speed";
  public static final String NET_ROUTE_CHANNELS = "routeChannels";
  public static final String NET_SERVER_CHANNELS = "serverChannels";
  public static final String NET_SKIN_DOWNLOAD = "SkinDownload";
  public static final String NET_SERVER_NAME = "serverName";
  public static final String NET_SERVER_DESCRIPTION = "serverDescription";
  public static final String NET_SOCKS_HOST = "socksHost";
  public static final String NET_SOCKS_PORT = "socksPort";
  public static final String NET_SOCKS_USER = "socksUser";
  public static final String NET_SOCKS_PWD = "socksPwd";
  public static final String GAME = "game";
  public static final String GAME_3DGUNNERS = "3dgunners";
  public static final String GAME_CLEARCACHE = "ClearCache";
  public static final String GAME_TYPECLOUDS = "TypeClouds";
  public IniFile ini = null;
  public String mainSection = "il2";
  public String windowTitle = "Il2";

  public int windowWidth = 640;
  public int windowHeight = 480;
  public int windowColourBits = 16;
  public int windowDepthBits = 16;
  public int windowStencilBits = 0;
  public boolean windowFullScreen = true;
  public boolean windowChangeScreenRes = true;
  public boolean windowEnableResize = false;
  public boolean windowEnableClose = false;
  public boolean windowSaveAspect = true;
  public boolean windowUse3Renders = false;

  public String glLib = "opengl32.dll";
  public String gluLib = null;

  public int netLocalPort = 21000;
  public int netRemotePort = 21000;
  public String netLocalHost = null;
  public String netRemoteHost = "";
  public int netSpeed = 5000;
  public int netRouteChannels = 0;
  public int netServerChannels = 8;
  public boolean netSkinDownload = true;
  public String netServerName = "";
  public String netServerDescription = "";

  public boolean b3dgunners = false;
  public boolean clear_cache = true;
  public boolean newCloudsRender = true;

  private static boolean bUseRender = false;

  public ArrayList zutiServerNames = null;

  protected boolean bSoundUse = false;
  private boolean bDebugSound = false;

  public boolean bAppEditor = false;

  public boolean isUse3Renders()
  {
    return (this.windowUse3Renders) && (this.windowSaveAspect);
  }
  public void checkWindowUse3Renders() { if (!this.windowUse3Renders) return;
    this.windowUse3Renders = (this.windowWidth == this.windowHeight * 4);
  }

  public static boolean isUSE_RENDER()
  {
    return bUseRender;
  }

  private void loadGlProvider() {
    String str = this.ini.get("GLPROVIDER", "GL", (String)null);
    if (str != null) {
      this.glLib = str;
    }
    this.gluLib = this.ini.get("GLPROVIDER", "GLU", (String)null);
  }

  public void load()
    throws GLContextException, ProviderException
  {
    loadNet();
    loadGame();
    loadConsole();
    if (isUSE_RENDER()) {
      this.windowWidth = this.ini.get("window", "width", this.windowWidth);
      this.windowHeight = this.ini.get("window", "height", this.windowHeight);
      this.windowColourBits = this.ini.get("window", "ColourBits", this.windowColourBits);
      this.windowDepthBits = this.ini.get("window", "DepthBits", this.windowDepthBits);
      this.windowStencilBits = this.ini.get("window", "StencilBits", this.windowStencilBits);
      this.windowFullScreen = this.ini.get("window", "FullScreen", this.windowFullScreen);
      this.windowChangeScreenRes = this.ini.get("window", "ChangeScreenRes", this.windowChangeScreenRes);
      this.windowEnableResize = this.ini.get("window", "EnableResize", this.windowEnableResize);
      this.windowEnableClose = this.ini.get("window", "EnableClose", this.windowEnableClose);
      this.windowSaveAspect = this.ini.get("window", "SaveAspect", this.windowSaveAspect);
      this.windowUse3Renders = this.ini.get("window", "Use3Renders", this.windowUse3Renders);
      checkWindowUse3Renders();
      if (this.windowChangeScreenRes) {
        this.windowFullScreen = true;
      } else {
        ScreenMode localScreenMode = ScreenMode.startup();
        if (this.windowColourBits != localScreenMode.colourBits()) this.windowColourBits = localScreenMode.colourBits();
      }
      loadGlProvider();
      loadEngine();
      Provider.GLload(this.glLib);
      if (this.gluLib != null)
        Provider.GLUload(this.gluLib);
      beforeLoadSound();
    }
  }

  public void save() {
    if (isUSE_RENDER()) {
      this.ini.setValue("window", "width", Integer.toString(this.windowWidth));
      this.ini.setValue("window", "height", Integer.toString(this.windowHeight));
      this.ini.setValue("window", "ColourBits", Integer.toString(this.windowColourBits));
      this.ini.setValue("window", "DepthBits", Integer.toString(this.windowDepthBits));
      this.ini.setValue("window", "StencilBits", Integer.toString(this.windowStencilBits));
      this.ini.setValue("window", "FullScreen", this.windowFullScreen ? "1" : "0");
      this.ini.setValue("window", "ChangeScreenRes", this.windowChangeScreenRes ? "1" : "0");
      this.ini.setValue("window", "EnableResize", this.windowEnableResize ? "1" : "0");
      this.ini.setValue("window", "EnableClose", this.windowEnableClose ? "1" : "0");
      this.ini.setValue("window", "SaveAspect", this.windowSaveAspect ? "1" : "0");
      this.ini.setValue("window", "Use3Renders", this.windowUse3Renders ? "1" : "0");

      this.ini.setValue("GLPROVIDER", "GL", this.glLib);
      if (this.gluLib != null) {
        this.ini.setValue("GLPROVIDER", "GLU", this.gluLib);
      }
      saveSound();
      saveEngine();
    }
    saveConsole();
    saveNet();
    this.ini.saveFile();
  }

  private void loadNet() {
    this.netLocalPort = this.ini.get("NET", "localPort", this.netLocalPort, 1000, 65000);
    this.netRemotePort = this.ini.get("NET", "remotePort", this.netRemotePort, 1000, 65000);
    this.netSpeed = this.ini.get("NET", "speed", this.netSpeed, 300, 1000000);
    this.netLocalHost = this.ini.get("NET", "localHost", this.netLocalHost);
    if (this.netLocalHost != null) {
      try {
        InetAddress localInetAddress = InetAddress.getByName(this.netLocalHost);
        DatagramSocket localDatagramSocket = new DatagramSocket(this.netLocalPort, localInetAddress);
        localDatagramSocket.close();
      } catch (Exception localException) {
        System.out.println("Unknown net address: " + this.netLocalHost);
        this.netLocalHost = null;
      }
    }
    if (isUSE_RENDER()) {
      this.netRemotePort = this.ini.get("NET", "remotePort", this.netRemotePort, 1000, 65000);
      this.netRemoteHost = this.ini.get("NET", "remoteHost", this.netRemoteHost);
      this.netRouteChannels = this.ini.get("NET", "routeChannels", this.netRouteChannels, 0, 16);
      this.netServerChannels = this.ini.get("NET", "serverChannels", this.netServerChannels, 1, 31);

      zutiLoadServers();
    }
    else {
      this.netServerChannels = this.ini.get("NET", "serverChannels", this.netServerChannels, 1, 128);
    }
    this.netSkinDownload = this.ini.get("NET", "SkinDownload", this.netSkinDownload);
    this.netServerName = UnicodeTo8bit.load(this.ini.get("NET", "serverName", ""));
    this.netServerDescription = UnicodeTo8bit.load(this.ini.get("NET", "serverDescription", ""));

    NetChannel.bCheckServerTimeSpeed = this.ini.get("NET", "checkServerTimeSpeed", NetChannel.bCheckServerTimeSpeed);
    NetChannel.bCheckClientTimeSpeed = this.ini.get("NET", "checkClientTimeSpeed", NetChannel.bCheckClientTimeSpeed);

    NetChannel.checkTimeSpeedDifferense = this.ini.get("NET", "checkTimeSpeedDifferense", (float)NetChannel.checkTimeSpeedDifferense, 0.01F, 1000.0F);
    NetChannel.checkTimeSpeedInterval = this.ini.get("NET", "checkTimeSpeedInterval", NetChannel.checkTimeSpeedInterval, 1, 1000);

    SocksUdpSocket.setProxyHost(this.ini.get("NET", "socksHost", (String)null));
    SocksUdpSocket.setProxyPort(this.ini.get("NET", "socksPort", 1080));
    SocksUdpSocket.setProxyUser(this.ini.get("NET", "socksUser", (String)null));
    SocksUdpSocket.setProxyPassword(this.ini.get("NET", "socksPwd", (String)null));
  }
  private void saveNet() {
    this.ini.setValue("NET", "localPort", "" + this.netLocalPort);
    this.ini.setValue("NET", "speed", "" + this.netSpeed);
    if (isUSE_RENDER()) {
      this.ini.setValue("NET", "remotePort", "" + this.netRemotePort);
      this.ini.setValue("NET", "remoteHost", this.netRemoteHost);
      this.ini.setValue("NET", "routeChannels", "" + this.netRouteChannels);

      for (int i = 0; i < this.zutiServerNames.size(); i++)
      {
        String str = "";
        if (i < 10)
          str = "00" + new Integer(i).toString();
        else if ((i > 9) && (i < 100))
          str = "0" + new Integer(i).toString();
        else if (i > 99) str = new Integer(i).toString();

        this.ini.setValue("NET", "remoteHost_" + str, (String)this.zutiServerNames.get(i));
      }
    }

    this.ini.setValue("NET", "serverChannels", "" + this.netServerChannels);
    this.ini.setValue("NET", "SkinDownload", this.netSkinDownload ? "1" : "0");
    this.ini.setValue("NET", "serverName", UnicodeTo8bit.save(this.netServerName, false));
    this.ini.setValue("NET", "serverDescription", UnicodeTo8bit.save(this.netServerDescription, false));

    if (SocksUdpSocket.getProxyHost() != null)
      this.ini.setValue("NET", "socksHost", SocksUdpSocket.getProxyHost());
    else
      this.ini.setValue("NET", "socksHost", "");
    if (SocksUdpSocket.getProxyPort() != 1080)
      this.ini.setValue("NET", "socksPort", "" + SocksUdpSocket.getProxyPort());
    else
      this.ini.deleteValue("NET", "socksPort");
    if (SocksUdpSocket.getProxyUser() != null)
      this.ini.setValue("NET", "socksUser", SocksUdpSocket.getProxyUser());
    else
      this.ini.deleteValue("NET", "socksUser");
    if (SocksUdpSocket.getProxyPassword() != null)
      this.ini.setValue("NET", "socksPwd", SocksUdpSocket.getProxyPassword());
    else
      this.ini.deleteValue("NET", "socksPwd");
  }

  private void loadGame() {
    this.b3dgunners = this.ini.get("game", "3dgunners", this.b3dgunners);
    this.clear_cache = this.ini.get("game", "ClearCache", this.clear_cache);
    this.newCloudsRender = this.ini.get("game", "TypeClouds", this.newCloudsRender);
  }

  private void loadConsole() {
    RTSConf.cur.console.bWrap = (this.ini.get("Console", "WRAP", 1, 0, 1) == 1);
    RTSConf.cur.console.setMaxHistoryOut(this.ini.get("Console", "HISTORY", 128, 0, 10000));
    RTSConf.cur.console.setMaxHistoryCmd(this.ini.get("Console", "HISTORYCMD", 128, 0, 10000));
    RTSConf.cur.console.setPageHistoryOut(this.ini.get("Console", "PAGE", 20, 1, 100));
    RTSConf.cur.console.setPause(this.ini.get("Console", "PAUSE", 1, 0, 1) == 1);
    RTSConf.cur.console.setLogKeep(this.ini.get("Console", "LOGKEEP", 1, 0, 1) == 1);
    String str = this.ini.getValue("Console", "LOGFILE");
    if (str.length() > 0)
      RTSConf.cur.console.setLogFileName(str);
    else
      RTSConf.cur.console.setLogFileName("log.lst");
    RTSConf.cur.console.log(this.ini.get("Console", "LOG", 0, 0, 1) == 1);
    Console.setTypeTimeInLogFile(this.ini.get("Console", "LOGTIME", 0, 0, 1) == 1);
    str = this.ini.getValue("Console", "LOAD");
    if (str.length() > 0)
      RTSConf.cur.console.load(str);
  }

  private void saveConsole() {
    if (RTSConf.cur.console.getEnv().levelAccess() == 0) {
      this.ini.setValue("Console", "WRAP", Integer.toString(RTSConf.cur.console.bWrap ? 1 : 0));
      this.ini.setValue("Console", "PAUSE", Integer.toString(RTSConf.cur.console.isPaused() ? 1 : 0));
      this.ini.setValue("Console", "PAGE", Integer.toString(RTSConf.cur.console.pageHistoryOut()));
    }
    this.ini.setValue("Console", "HISTORY", Integer.toString(RTSConf.cur.console.maxHistoryOut()));
    this.ini.setValue("Console", "HISTORYCMD", Integer.toString(RTSConf.cur.console.maxHistoryCmd()));
    this.ini.setValue("Console", "LOGFILE", RTSConf.cur.console.logFileName());
    this.ini.setValue("Console", "LOG", Integer.toString(RTSConf.cur.console.isLog() ? 1 : 0));
    this.ini.setValue("Console", "LOGKEEP", Integer.toString(RTSConf.cur.console.isLogKeep() ? 1 : 0));
    this.ini.setValue("Console", "LOGTIME", Integer.toString(Console.isTypeTimeInLogFile() ? 1 : 0));
    String str = this.ini.getValue("Console", "SAVE");
    if (str.length() > 0)
      RTSConf.cur.console.save(str);
    RTSConf.cur.console.log(false);
  }

  public void loadEngine() {
    loadEngine(this.ini.get("GLPROVIDER", "GL", (String)null));
  }
  public void loadEngine(String paramString) {
    if (paramString == null)
      paramString = this.glLib;
    String str1 = this.ini.get("GLPROVIDERS", "DirectX", "");
    String str2 = "Render_OpenGL";
    if ((str1 != null) && (paramString.equalsIgnoreCase(str1)))
      str2 = "Render_DirectX";
    RenderContext.loadConfig(this.ini, str2);
  }

  private void saveEngine() {
    RenderContext.saveConfig();
  }

  public boolean isDebugSound()
  {
    return this.bDebugSound;
  }
  public boolean isSoundUse() {
    return this.bSoundUse;
  }

  private void beforeLoadSound() {
    this.bSoundUse = this.ini.get("sound", "SoundUse", false);
    this.bDebugSound = this.ini.get("sound", "DebugSound", this.bDebugSound);
  }

  private void saveSound() {
    this.ini.setValue("sound", "SoundUse", this.bSoundUse ? "1" : "0");
    AudioDevice.saveControls(this.ini, "sound");
  }

  public void beginSound() {
    AudioDevice.setMessageMode(this.bDebugSound ? 1 : 0);
    AudioDevice.loadControls(this.ini, "sound");

    if (this.bSoundUse) {
      this.bSoundUse = false;
      if ((RTSConf.cur != null) && ((RTSConf.cur instanceof RTSConfWin))) {
        int i = RTSConf.cur.mainWindow.hWnd();
        if (i != 0)
          if (AudioDevice.initialize(i, engineDllName() + ".dll"))
            this.bSoundUse = true;
          else
            System.err.println("AudioDevice NOT initialized");
      }
    }
    else
    {
      AudioDevice.initialize(0, engineDllName() + ".dll");
    }

    AudioDevice.resetControls();
  }

  public void endSound() {
    if (this.bSoundUse)
      AudioDevice.done();
  }

  public GLInitCaps getGLCaps()
  {
    GLInitCaps localGLInitCaps = new GLInitCaps();
    localGLInitCaps.setDoubleBuffered(true);
    localGLInitCaps.setColourBits(this.windowColourBits);
    localGLInitCaps.setDepthBits(this.windowDepthBits);
    localGLInitCaps.setStencilBits(this.windowStencilBits);
    return localGLInitCaps;
  }

  public void setGLCaps(GLCaps paramGLCaps) {
    this.windowColourBits = paramGLCaps.getColourBits();
    this.windowDepthBits = paramGLCaps.getDepthBits();
    this.windowStencilBits = paramGLCaps.getStencilBits();
  }

  public GLContext createGlContext(GLContext paramGLContext, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    throws GLContextException
  {
    ScreenMode localScreenMode = ScreenMode.readCurrent();
    if (paramBoolean1) {
      if (ScreenMode.set(paramInt1, paramInt2, paramInt3)) {
        paramInt1 = ScreenMode.current().width();
        paramInt2 = ScreenMode.current().height();
        paramInt3 = ScreenMode.current().colourBits();
      } else {
        throw new GLContextException("ScreenMode NOT changed");
      }
    }
    else if (ScreenMode.current() != ScreenMode.startup()) {
      ScreenMode.restore();
    }
    boolean bool = false;
    if (paramBoolean2)
      bool = ((MainWin32)RTSConf.cur.mainWindow).create(this.windowTitle, paramInt1, paramInt2);
    else {
      bool = ((MainWin32)RTSConf.cur.mainWindow).create(this.windowTitle, this.windowEnableClose, this.windowEnableResize, paramInt1, paramInt2);
    }
    if (!bool) {
      ScreenMode.set(localScreenMode);
      throw new GLContextException("Window NOT created");
    }
    paramInt1 = RTSConf.cur.mainWindow.width();
    paramInt2 = RTSConf.cur.mainWindow.height();

    GLInitCaps localGLInitCaps = new GLInitCaps();
    localGLInitCaps.setDoubleBuffered(true);
    localGLInitCaps.setColourBits(paramInt3);
    localGLInitCaps.setDepthBits(paramInt4);
    localGLInitCaps.setStencilBits(paramInt5);
    try
    {
      if (paramGLContext != null) {
        paramGLContext.changeWin32(localGLInitCaps, RTSConf.cur.mainWindow.hWnd(), true, paramInt1, paramInt2);
      } else {
        paramGLContext = new GLContext(localGLInitCaps);
        paramGLContext.createWin32(RTSConf.cur.mainWindow.hWnd(), true, paramInt1, paramInt2);
      }
    } catch (GLContextException localGLContextException) {
      ((MainWin32)RTSConf.cur.mainWindow).destroy();
      ScreenMode.set(localScreenMode);
      throw localGLContextException;
    }

    this.windowFullScreen = paramBoolean2;
    this.windowChangeScreenRes = paramBoolean1;
    this.windowWidth = RTSConf.cur.mainWindow.width();
    this.windowHeight = RTSConf.cur.mainWindow.height();
    this.windowColourBits = paramGLContext.getCaps().getColourBits();
    this.windowDepthBits = paramGLContext.getCaps().getDepthBits();
    this.windowStencilBits = paramGLContext.getCaps().getStencilBits();
    checkWindowUse3Renders();
    return paramGLContext;
  }

  public GLContext createGlContext(String paramString)
    throws GLContextException
  {
    this.windowTitle = paramString;
    return createGlContext(null, this.windowChangeScreenRes, this.windowFullScreen, this.windowWidth, this.windowHeight, this.windowColourBits, this.windowDepthBits, this.windowStencilBits);
  }

  public static void typeCurrentScreenMode()
  {
    ScreenMode localScreenMode = ScreenMode.current();
    System.err.println("Current screen mode: " + localScreenMode.width() + "x" + localScreenMode.height() + "x" + localScreenMode.colourBits());
  }

  public static void typeScreenModes() {
    ScreenMode[] arrayOfScreenMode = ScreenMode.all();
    System.err.print("Screen modes: ");
    for (int i = 0; i < arrayOfScreenMode.length; i++) {
      if (i % 4 == 0) System.err.println("");
      System.err.print("\t" + i + " " + arrayOfScreenMode[i].width() + "x" + arrayOfScreenMode[i].height() + "x" + arrayOfScreenMode[i].colourBits());
    }
    System.err.println("");
  }

  public static void typeGLCaps() {
    GLCaps[] arrayOfGLCaps = Provider.getGLCaps();
    System.err.println("Caps OpenGL library:");
    for (int i = 0; i < arrayOfGLCaps.length; i++) {
      System.err.print(i);
      System.err.print(arrayOfGLCaps[i].getDevice() == 1 ? "  DRAW_TO_WINDOW" : "  DRAW_TO_BITMAP");
      System.err.print(arrayOfGLCaps[i].isDoubleBuffered() ? "  DOUBLEBUFFER" : "  SINGLEBUFFER");
      System.err.print(arrayOfGLCaps[i].isStereo() ? "  STEREO" : "  NOSTEREO");
      System.err.println(arrayOfGLCaps[i].getPixelType() == 1 ? "  TYPE_RGBA" : "  TYPE_COLOURINDEX");
      System.err.print("  ColourBits: " + arrayOfGLCaps[i].getColourBits());
      System.err.print("  AlphaBits: " + arrayOfGLCaps[i].getAlphaBits());
      System.err.print("  AccumBits: " + arrayOfGLCaps[i].getAccumBits());
      System.err.print("  DepthBits: " + arrayOfGLCaps[i].getDepthBits());
      System.err.println("  StencilBits: " + arrayOfGLCaps[i].getStencilBits());
    }
  }

  public static void typeGlStrings() {
    System.err.println("OpenGL library:");
    System.err.println("  Vendor: " + gl.GetString(7936));
    System.err.println("  Render: " + gl.GetString(7937));
    System.err.println("  Version: " + gl.GetString(7938));
    System.err.println("  Extensions: " + gl.GetString(7939));
  }

  public static void typeProvider() {
    System.err.println("OpenGL provider: " + Provider.GLname());
    try {
      System.err.println("GLU provider: " + Provider.GLUname());
    }
    catch (Exception localException) {
    }
  }

  public void typeContextSettings(GLContext paramGLContext) {
    System.err.println("Size: " + paramGLContext.width() + "x" + paramGLContext.height());
    System.err.println("ColorBits: " + paramGLContext.getCaps().getColourBits());
    System.err.println("DepthBits: " + paramGLContext.getCaps().getDepthBits());
    System.err.println("StencilBits: " + paramGLContext.getCaps().getStencilBits());
    System.err.println("isDoubleBuffered: " + paramGLContext.getCaps().isDoubleBuffered());
  }

  public static boolean isAppEditor()
  {
    return cur.bAppEditor;
  }

  public static String engineDllName() {
    if (CLASS.ser() != 0) {
      if ((Cpu86ID.getVendor() == 1) && (Cpu86ID.isSSE2()))
      {
        return "il2_coreP4";
      }
      return "il2_core";
    }
    Cpu86ID.getVendor();
    return "il2_server";
  }

  public Config(IniFile paramIniFile, boolean paramBoolean)
  {
    this.zutiServerNames = new ArrayList();

    bUseRender = paramBoolean;
    GObj.loadNative();
    this.ini = paramIniFile;
    load();
  }

  private void zutiLoadServers()
  {
    for (int i = 0; i < 255; i++)
    {
      String str1 = "";
      if (i < 10)
        str1 = "00" + new Integer(i).toString();
      else if ((i > 9) && (i < 100))
        str1 = "0" + new Integer(i).toString();
      else if (i > 99) str1 = new Integer(i).toString();

      String str2 = this.ini.get("NET", "remoteHost_" + str1, "");
      if (str2.trim().length() <= 0) continue; this.zutiServerNames.add(str2);
    }
  }

  public ArrayList zutiGetServerNames()
  {
    return this.zutiServerNames;
  }

  public void zutiAddServerName(String paramString)
  {
    if (!this.zutiServerNames.contains(paramString)) this.zutiServerNames.add(paramString);
  }
}