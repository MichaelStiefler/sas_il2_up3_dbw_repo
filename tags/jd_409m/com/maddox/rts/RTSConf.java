package com.maddox.rts;

import com.maddox.util.HashMapExt;
import java.util.Locale;

public class RTSConf
{
  public static final boolean RELEASE = true;
  public static final int MESSAGE_INPUT_TICK_POS = 2147483646;
  public static final int MESSAGE_KEYRECORD_TICK_POS = -2147483647;
  public static RTSConf cur = null;
  public Locale locale;
  public static String charEncoding = "Cp1252";
  public Time time;
  public Timer timer;
  public Timer realTimer;
  public MainWindow mainWindow;
  public Keyboard keyboard;
  public Mouse mouse;
  public Joy joy;
  public TrackIR trackIR;
  public CmdEnv cmdEnv;
  public HotKeyCmdEnvs hotKeyCmdEnvs;
  public HotKeyEnvs hotKeyEnvs;
  public Console console;
  public NetEnv netEnv;
  public MessageQueue queue = new MessageQueue();

  public MessageQueue queueNextTick = new MessageQueue();

  public MessageQueue queueRealTime = new MessageQueue();

  public MessageQueue queueRealTimeNextTick = new MessageQueue();
  public Message message;
  public HashMapExt cfgMap = new HashMapExt();

  public BackgroundLoop backgroundLoop = new BackgroundLoop();

  public static Object lockObject = new Object();

  protected RTSProfile profile = new RTSProfile();
  public String execPostProcessCmd;
  private boolean bRequestExitApp = false;

  private boolean bResetGame = false;

  protected boolean bStarted = false;

  protected int useMouse = 1;

  protected boolean bUseJoy = true;

  protected boolean bUseTrackIR = true;

  public static Object lockObject()
  {
    return lockObject;
  }

  public void loopMsgs()
  {
    this.time.loopMessages();
    this.profile.endFrame();
  }

  public static boolean isRequestExitApp()
  {
    return cur.bRequestExitApp;
  }
  public static void setRequestExitApp(boolean paramBoolean) { cur.bRequestExitApp = paramBoolean; }

  public static boolean isResetGame() {
    return cur.bResetGame;
  }
  public void setFlagResetGame(boolean paramBoolean) { this.bResetGame = paramBoolean; }

  public void resetGameClear() {
    this.keyboard._clear();
    this.mouse._clear();
    this.joy._clear();
    this.trackIR.clear();
    cur.hotKeyEnvs.resetGameClear();
    cur.timer.resetGameClear();
    cur.queue.clear();
    cur.queueNextTick.clear();
    cur.time.resetGameClear();
  }
  public void resetGameCreate() {
    cur.time.resetGameCreate();
    cur.timer.resetGameCreate();
    cur.hotKeyEnvs.resetGameCreate();
  }

  public boolean isStarted() {
    return this.bStarted; } 
  public void start() { this.bStarted = true; } 
  public void stop() { this.bStarted = false; }

  public int getUseMouse()
  {
    return this.useMouse; } 
  public void setUseMouse(int paramInt) { this.useMouse = paramInt; }

  public boolean isUseJoy() {
    return this.bUseJoy; } 
  public void useJoy(boolean paramBoolean) { this.bUseJoy = paramBoolean; }

  public boolean isUseTrackIR() {
    return this.bUseTrackIR; } 
  public void useTrackIR(boolean paramBoolean) { this.bUseTrackIR = paramBoolean; }

  private void setNetProperty() {
    Property.set("netProtocol", "udp", 1);

    Property.set("udp", "className", "UdpSocket");
  }

  public static boolean OSisWIN32() {
    return true;
  }

  public RTSConf() {
    this(new MainWindow());
  }
  public RTSConf(MainWindow paramMainWindow) {
    cur = this;
    this.locale = Locale.getDefault();
    this.time = new Time(30, 20);
    this.timer = new Timer(false, -1000);
    this.realTimer = new Timer(true, -1000);
    this.mainWindow = new MainWindow();
    this.keyboard = new Keyboard();
    this.mouse = new Mouse(null, null);
    this.joy = new Joy(null, null);
    this.trackIR = new TrackIR(null, null);
    this.netEnv = new NetEnv();
    this.cmdEnv = new CmdEnv();
    this.hotKeyCmdEnvs = new HotKeyCmdEnvs();
    this.hotKeyEnvs = new HotKeyEnvs();
    HotKeyCmdEnv.setCurrentEnv("default");
    this.console = new Console(0);
    setNetProperty();
  }
  public RTSConf(IniFile paramIniFile, String paramString, int paramInt) {
    this(new MainWindow(), paramIniFile, paramString, paramInt);
  }
  public RTSConf(MainWindow paramMainWindow, IniFile paramIniFile, String paramString, int paramInt) {
    cur = this;
    int i = paramIniFile.get(paramString, "ProcessAffinityMask", 0);
    if (i != 0)
      SetProcessAffinityMask(i);
    this.locale = Locale.getDefault();

    this.time = new Time(30, 20);

    this.timer = new Timer(false, -1000);
    this.realTimer = new Timer(true, -1000);
    this.mainWindow = paramMainWindow;
    this.keyboard = new Keyboard();
    this.mouse = new Mouse(paramIniFile, paramString + "_mouse");
    this.joy = new Joy(paramIniFile, paramString + "_joystick");
    this.trackIR = new TrackIR(paramIniFile, paramString + "_trackIR");
    this.netEnv = new NetEnv();
    this.cmdEnv = new CmdEnv();
    this.hotKeyCmdEnvs = new HotKeyCmdEnvs();
    this.hotKeyEnvs = new HotKeyEnvs();
    HotKeyCmdEnv.setCurrentEnv("default");
    HotKeyEnv.setCurrentEnv("default");
    this.console = new Console(paramInt);
    setNetProperty();
  }

  private static native void SetProcessAffinityMask(int paramInt);
}