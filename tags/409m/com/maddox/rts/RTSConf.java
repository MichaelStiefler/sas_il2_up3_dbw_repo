// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RTSConf.java

package com.maddox.rts;

import com.maddox.util.HashMapExt;
import java.util.Locale;

// Referenced classes of package com.maddox.rts:
//            MainWindow, MessageQueue, BackgroundLoop, RTSProfile, 
//            Time, Timer, Keyboard, Mouse, 
//            Joy, TrackIR, NetEnv, CmdEnv, 
//            HotKeyCmdEnvs, HotKeyEnvs, Console, Property, 
//            HotKeyCmdEnv, IniFile, HotKeyEnv, Message

public class RTSConf
{

    public static java.lang.Object lockObject()
    {
        return lockObject;
    }

    public void loopMsgs()
    {
        time.loopMessages();
        profile.endFrame();
    }

    public static boolean isRequestExitApp()
    {
        return cur.bRequestExitApp;
    }

    public static void setRequestExitApp(boolean flag)
    {
        cur.bRequestExitApp = flag;
    }

    public static boolean isResetGame()
    {
        return cur.bResetGame;
    }

    public void setFlagResetGame(boolean flag)
    {
        bResetGame = flag;
    }

    public void resetGameClear()
    {
        keyboard._clear();
        mouse._clear();
        joy._clear();
        trackIR.clear();
        cur.hotKeyEnvs.resetGameClear();
        cur.timer.resetGameClear();
        cur.queue.clear();
        cur.queueNextTick.clear();
        cur.time.resetGameClear();
    }

    public void resetGameCreate()
    {
        cur.time.resetGameCreate();
        cur.timer.resetGameCreate();
        cur.hotKeyEnvs.resetGameCreate();
    }

    public boolean isStarted()
    {
        return bStarted;
    }

    public void start()
    {
        bStarted = true;
    }

    public void stop()
    {
        bStarted = false;
    }

    public int getUseMouse()
    {
        return useMouse;
    }

    public void setUseMouse(int i)
    {
        useMouse = i;
    }

    public boolean isUseJoy()
    {
        return bUseJoy;
    }

    public void useJoy(boolean flag)
    {
        bUseJoy = flag;
    }

    public boolean isUseTrackIR()
    {
        return bUseTrackIR;
    }

    public void useTrackIR(boolean flag)
    {
        bUseTrackIR = flag;
    }

    private void setNetProperty()
    {
        com.maddox.rts.Property.set("netProtocol", "udp", 1);
        com.maddox.rts.Property.set("udp", "className", "UdpSocket");
    }

    public static boolean OSisWIN32()
    {
        return true;
    }

    public RTSConf()
    {
        this(new MainWindow());
    }

    public RTSConf(com.maddox.rts.MainWindow mainwindow)
    {
        queue = new MessageQueue();
        queueNextTick = new MessageQueue();
        queueRealTime = new MessageQueue();
        queueRealTimeNextTick = new MessageQueue();
        cfgMap = new HashMapExt();
        backgroundLoop = new BackgroundLoop();
        profile = new RTSProfile();
        bRequestExitApp = false;
        bResetGame = false;
        bStarted = false;
        useMouse = 1;
        bUseJoy = true;
        bUseTrackIR = true;
        cur = this;
        locale = java.util.Locale.getDefault();
        time = new Time(30, 20);
        timer = new Timer(false, -1000);
        realTimer = new Timer(true, -1000);
        mainWindow = new MainWindow();
        keyboard = new Keyboard();
        mouse = new Mouse(null, null);
        joy = new Joy(null, null);
        trackIR = new TrackIR(null, null);
        netEnv = new NetEnv();
        cmdEnv = new CmdEnv();
        hotKeyCmdEnvs = new HotKeyCmdEnvs();
        hotKeyEnvs = new HotKeyEnvs();
        com.maddox.rts.HotKeyCmdEnv.setCurrentEnv("default");
        console = new Console(0);
        setNetProperty();
    }

    public RTSConf(com.maddox.rts.IniFile inifile, java.lang.String s, int i)
    {
        this(new MainWindow(), inifile, s, i);
    }

    public RTSConf(com.maddox.rts.MainWindow mainwindow, com.maddox.rts.IniFile inifile, java.lang.String s, int i)
    {
        queue = new MessageQueue();
        queueNextTick = new MessageQueue();
        queueRealTime = new MessageQueue();
        queueRealTimeNextTick = new MessageQueue();
        cfgMap = new HashMapExt();
        backgroundLoop = new BackgroundLoop();
        profile = new RTSProfile();
        bRequestExitApp = false;
        bResetGame = false;
        bStarted = false;
        useMouse = 1;
        bUseJoy = true;
        bUseTrackIR = true;
        cur = this;
        int j = inifile.get(s, "ProcessAffinityMask", 0);
        if(j != 0)
            com.maddox.rts.RTSConf.SetProcessAffinityMask(j);
        locale = java.util.Locale.getDefault();
        time = new Time(30, 20);
        timer = new Timer(false, -1000);
        realTimer = new Timer(true, -1000);
        mainWindow = mainwindow;
        keyboard = new Keyboard();
        mouse = new Mouse(inifile, s + "_mouse");
        joy = new Joy(inifile, s + "_joystick");
        trackIR = new TrackIR(inifile, s + "_trackIR");
        netEnv = new NetEnv();
        cmdEnv = new CmdEnv();
        hotKeyCmdEnvs = new HotKeyCmdEnvs();
        hotKeyEnvs = new HotKeyEnvs();
        com.maddox.rts.HotKeyCmdEnv.setCurrentEnv("default");
        com.maddox.rts.HotKeyEnv.setCurrentEnv("default");
        console = new Console(i);
        setNetProperty();
    }

    private static native void SetProcessAffinityMask(int i);

    public static final boolean RELEASE = true;
    public static final int MESSAGE_INPUT_TICK_POS = 0x7ffffffe;
    public static final int MESSAGE_KEYRECORD_TICK_POS = 0x80000001;
    public static com.maddox.rts.RTSConf cur = null;
    public java.util.Locale locale;
    public static java.lang.String charEncoding = "Cp1252";
    public com.maddox.rts.Time time;
    public com.maddox.rts.Timer timer;
    public com.maddox.rts.Timer realTimer;
    public com.maddox.rts.MainWindow mainWindow;
    public com.maddox.rts.Keyboard keyboard;
    public com.maddox.rts.Mouse mouse;
    public com.maddox.rts.Joy joy;
    public com.maddox.rts.TrackIR trackIR;
    public com.maddox.rts.CmdEnv cmdEnv;
    public com.maddox.rts.HotKeyCmdEnvs hotKeyCmdEnvs;
    public com.maddox.rts.HotKeyEnvs hotKeyEnvs;
    public com.maddox.rts.Console console;
    public com.maddox.rts.NetEnv netEnv;
    public com.maddox.rts.MessageQueue queue;
    public com.maddox.rts.MessageQueue queueNextTick;
    public com.maddox.rts.MessageQueue queueRealTime;
    public com.maddox.rts.MessageQueue queueRealTimeNextTick;
    public com.maddox.rts.Message message;
    public com.maddox.util.HashMapExt cfgMap;
    public com.maddox.rts.BackgroundLoop backgroundLoop;
    public static java.lang.Object lockObject = new Object();
    protected com.maddox.rts.RTSProfile profile;
    public java.lang.String execPostProcessCmd;
    private boolean bRequestExitApp;
    private boolean bResetGame;
    protected boolean bStarted;
    protected int useMouse;
    protected boolean bUseJoy;
    protected boolean bUseTrackIR;

}
