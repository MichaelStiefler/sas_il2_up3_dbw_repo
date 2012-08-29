// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIBWDemoPlay.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.RenderContext;
import com.maddox.il2.engine.hotkey.HookView;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetMissionListener;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.objects.effects.Cinema;
import com.maddox.il2.objects.effects.LightsGlare;
import com.maddox.il2.objects.effects.SunGlare;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.opengl.Provider;
import com.maddox.rts.BackgroundTask;
import com.maddox.rts.CfgFlags;
import com.maddox.rts.CfgInt;
import com.maddox.rts.CfgTools;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.KeyRecord;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgBackgroundTaskListener;
import com.maddox.rts.MsgTimeOut;
import com.maddox.rts.MsgTimeOutListener;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetObj;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import com.maddox.sound.AudioDevice;
import com.maddox.util.UnicodeTo8bit;
import java.io.PrintStream;
import java.util.Locale;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIDialogClient, GUI, GUIChatDialog

public class GUIBWDemoPlay extends com.maddox.il2.game.GameState
    implements com.maddox.rts.MsgTimeOutListener
{
    class MissionListener
        implements com.maddox.il2.net.NetMissionListener, com.maddox.rts.MsgBackgroundTaskListener
    {

        public void netMissionState(int i, float f, java.lang.String s)
        {
            switch(i)
            {
            case 6: // '\006'
                gameBeginEnvs();
                break;

            case 9: // '\t'
                gameBeginNet();
                break;

            case 7: // '\007'
            case 8: // '\b'
                com.maddox.rts.BackgroundTask.removeListener(this);
                break;
            }
        }

        public void netMissionCoopEnter()
        {
        }

        public void msgBackgroundTaskStarted(com.maddox.rts.BackgroundTask backgroundtask)
        {
        }

        public void msgBackgroundTaskStep(com.maddox.rts.BackgroundTask backgroundtask)
        {
            loadMessageBox.message = (int)backgroundtask.percentComplete() + "% " + i18n(backgroundtask.messageComplete());
        }

        public void msgBackgroundTaskStoped(com.maddox.rts.BackgroundTask backgroundtask)
        {
            com.maddox.rts.BackgroundTask.removeListener(this);
            if(backgroundtask.isComplete())
            {
                if(com.maddox.il2.game.Main3D.cur3D().playRecordedStreams() == null)
                {
                    java.lang.String s = com.maddox.il2.game.Main3D.cur3D().startPlayRecordedMission();
                    if(s == null)
                        gameBeginSingle();
                    else
                        recordBad(i18n("miss.LoadBad") + " " + s);
                }
            } else
            {
                recordBad(i18n("miss.LoadBad") + " " + backgroundtask.messageCancel());
            }
        }

        public MissionListener()
        {
            com.maddox.rts.BackgroundTask.addListener(this);
        }
    }


    private int flagNum(int i)
    {
        for(int j = 0; j < 32; j++)
            if((1 << j & i) == i)
                return j;

        return 0;
    }

    public void msgTimeOut(java.lang.Object obj)
    {
        if(obj != msgFile || obj == null)
            return;
        if(msgRecord >= 0)
        {
            java.lang.String s = msgFile.value(msgSect, msgRecord);
            if(s != null)
                s = com.maddox.util.UnicodeTo8bit.load(s);
            com.maddox.il2.game.HUD.intro(s);
        }
        msgRecord++;
        if(msgRecord >= msgFile.vars(msgSect))
            return;
        java.lang.String s1 = msgFile.var(msgSect, msgRecord);
        int i = s1.indexOf(":");
        int j = 0;
        if(i > 0)
            try
            {
                j = java.lang.Integer.parseInt(s1.substring(0, i)) * 60;
                j += java.lang.Integer.parseInt(s1.substring(i + 1));
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.err.println("Bad format messages file: " + msgFile.fileName());
                return;
            }
        else
            try
            {
                j = java.lang.Integer.parseInt(s1);
            }
            catch(java.lang.Exception exception1)
            {
                java.lang.System.err.println("Bad format messages file: " + msgFile.fileName());
                return;
            }
        long l = (long)j * 1000L;
        if(com.maddox.il2.net.NetMissionTrack.isPlaying())
            l += com.maddox.il2.net.NetMissionTrack.playingStartTime;
        com.maddox.rts.MsgTimeOut.post(0, l, this, msgFile);
    }

    public void _enter()
    {
        loadMessageBox = new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("record.StandBy"), i18n("record.LoadingTrack"), 5, 0.0F) {

            public void result(int i)
            {
                if(i == 1)
                {
                    _bCancel = true;
                    if(_bStarting)
                        com.maddox.rts.BackgroundTask.cancel(i18n("miss.UserCancel"));
                }
            }

            public void preRender()
            {
                super.preRender();
                if(_bStart)
                {
                    _bStart = false;
                    new com.maddox.rts.MsgAction(72, 0.0D) {

                        public void doAction()
                        {
                            if(_bCancel)
                                return;
                            _bStarting = true;
                            loadMessageBox.showModal();
                            com.maddox.il2.game.Main.closeAllNetChannels();
                            com.maddox.il2.gui.MissionListener missionlistener = new MissionListener();
                            com.maddox.il2.engine.Mat.setGrayScaleLoading(true);
                            com.maddox.opengl.Provider.setEnableBW(true);
                            for(int i = 0; i < 3; i++)
                            {
                                com.maddox.il2.game.Main3D.cur3D()._cinema[i].setShow(true);
                                com.maddox.il2.game.Main3D.cur3D()._cinema[i].resetGame();
                                com.maddox.il2.game.Main3D.cur3D()._lightsGlare[i].enterBWmode();
                                com.maddox.il2.game.Main3D.cur3D()._sunGlare[i].enterBWmode();
                            }

                            bDrawAllMessages = com.maddox.il2.game.Main3D.cur3D().hud.bDrawAllMessages;
                            com.maddox.il2.game.Main3D.cur3D().hud.bDrawAllMessages = false;
                            bArcade = com.maddox.il2.ai.World.cur().isArcade();
                            com.maddox.il2.ai.World.cur().setArcade(false);
                            bEnableVoice = com.maddox.il2.objects.sounds.Voice.isEnableVoices();
                            com.maddox.il2.objects.sounds.Voice.setEnableVoices(false);
                            flagTEX_VERTEX_ARRAYS = com.maddox.il2.engine.RenderContext.cfgTexFlags.get(flagNum(512));
                            flagTEX_SECONDARY_COLOR_EXT = com.maddox.il2.engine.RenderContext.cfgTexFlags.get(flagNum(16384));
                            com.maddox.il2.engine.RenderContext.cfgTexFlags.set(flagNum(512), false);
                            com.maddox.il2.engine.RenderContext.cfgTexFlags.set(flagNum(16384), false);
                            int j = com.maddox.il2.engine.RenderContext.cfgTexFlags.apply();
                            intHardwareShaders = com.maddox.il2.engine.RenderContext.cfgHardwareShaders.get();
                            com.maddox.il2.engine.RenderContext.cfgHardwareShaders.set(0);
                            j |= com.maddox.il2.engine.RenderContext.cfgHardwareShaders.apply();
                            com.maddox.il2.engine.Engine.land().UnLoadMap();
                            com.maddox.rts.CfgFlags cfgflags = (com.maddox.rts.CfgFlags)com.maddox.rts.CfgTools.get("MusState");
                            for(int k = 0; k < flagMusic.length; k++)
                            {
                                flagMusic[k] = cfgflags.get(k);
                                cfgflags.set(k, false);
                            }

                            cfgflags.apply();
                            java.lang.String s = com.maddox.il2.game.Main3D.cur3D().playRecordedMission(com.maddox.il2.gui.GUIBWDemoPlay.demoFile);
                            if(s != null)
                            {
                                com.maddox.rts.BackgroundTask.removeListener(missionlistener);
                                recordBad(s);
                            } else
                            if(com.maddox.il2.game.Main3D.cur3D().playRecordedStreams() != null)
                                com.maddox.il2.game.Main.cur().netMissionListener = missionlistener;
                        }

                    }
;
                }
            }

            boolean _bStart;
            boolean _bStarting;
            boolean _bCancel;


            
            {
                _bStart = true;
                _bStarting = false;
                _bCancel = false;
            }
        }
;
    }

    public void _leave()
    {
        msgFile = null;
        com.maddox.il2.game.HUD.intro(null);
        com.maddox.il2.game.HUD.introESC(null);
        client.hideWindow();
        com.maddox.opengl.Provider.setEnableBW(false);
        com.maddox.il2.engine.Mat.setGrayScaleLoading(false);
        for(int i = 0; i < 3; i++)
        {
            com.maddox.il2.game.Main3D.cur3D()._cinema[i].setShow(false);
            com.maddox.il2.game.Main3D.cur3D()._lightsGlare[i].leaveBWmode();
            com.maddox.il2.game.Main3D.cur3D()._sunGlare[i].leaveBWmode();
        }

        com.maddox.il2.game.Main3D.cur3D().hud.bDrawAllMessages = bDrawAllMessages;
        com.maddox.il2.ai.World.cur().setArcade(bArcade);
        com.maddox.il2.objects.sounds.Voice.setEnableVoices(bEnableVoice);
        com.maddox.il2.game.Main3D.cur3D().viewSet_Load();
        com.maddox.il2.engine.RenderContext.cfgTexFlags.set(flagNum(512), flagTEX_VERTEX_ARRAYS);
        com.maddox.il2.engine.RenderContext.cfgTexFlags.set(flagNum(16384), flagTEX_SECONDARY_COLOR_EXT);
        int j = com.maddox.il2.engine.RenderContext.cfgTexFlags.apply();
        com.maddox.il2.engine.RenderContext.cfgHardwareShaders.set(intHardwareShaders);
        j |= com.maddox.il2.engine.RenderContext.cfgHardwareShaders.apply();
        com.maddox.il2.engine.Engine.land().UnLoadMap();
        com.maddox.rts.CfgFlags cfgflags = (com.maddox.rts.CfgFlags)com.maddox.rts.CfgTools.get("MusState");
        for(int k = 0; k < flagMusic.length; k++)
            cfgflags.set(k, flagMusic[k]);

        cfgflags.apply();
        com.maddox.il2.game.Main3D.menuMusicPlay();
    }

    public void doExit()
    {
        boolean flag = com.maddox.il2.game.Main3D.cur3D().playRecordedStreams() != null;
        if(com.maddox.il2.net.NetMissionTrack.isRecording())
        {
            com.maddox.il2.net.NetMissionTrack.stopRecording();
            com.maddox.il2.gui.GUI.activate();
        }
        com.maddox.il2.game.Main.cur().netMissionListener = null;
        com.maddox.il2.game.Main3D.cur3D().stopPlayRecordedMission();
        if(com.maddox.il2.game.Mission.cur() != null && !com.maddox.il2.game.Mission.cur().isDestroyed())
            com.maddox.rts.CmdEnv.top().exec("mission END");
        if(com.maddox.il2.game.Main.cur().netServerParams != null)
            com.maddox.il2.game.Main.cur().netServerParams.destroy();
        if(com.maddox.rts.RTSConf.cur.netEnv.control != null)
            com.maddox.rts.RTSConf.cur.netEnv.control.destroy();
        com.maddox.il2.engine.hotkey.HookView.loadConfig();
        com.maddox.il2.game.Main.stateStack().pop();
    }

    private void recordBad(java.lang.String s)
    {
        loadMessageBox.close(false);
        com.maddox.il2.game.Main3D.cur3D().stopPlayRecordedMission();
        doExit();
    }

    private void gameBeginNet()
    {
        com.maddox.il2.engine.GUIWindowManager guiwindowmanager = com.maddox.il2.game.Main3D.cur3D().guiManager;
        loadMessageBox.close(false);
        com.maddox.il2.gui.GUI.unActivate();
        com.maddox.rts.RTSConf.cur.time.setEnableChangePause1(true);
        com.maddox.rts.RTSConf.cur.time.setEnableChangeSpeed(true);
        com.maddox.il2.gui.GUI.chatDlg.showWindow();
        if(com.maddox.il2.game.Mission.isNet())
            com.maddox.sound.AudioDevice.soundsOn();
        gameBegin();
    }

    private void gameBeginSingle()
    {
        new com.maddox.rts.MsgAction(72, 0.0D) {

            public void doAction()
            {
                com.maddox.il2.engine.GUIWindowManager guiwindowmanager = com.maddox.il2.game.Main3D.cur3D().guiManager;
                loadMessageBox.close(false);
                guiwindowmanager.setTimeGameActive(false);
                com.maddox.il2.gui.GUI.unActivate();
                com.maddox.rts.CmdEnv.top().exec("mission BEGIN");
                com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
                if(com.maddox.il2.engine.Actor.isValid(aircraft))
                    gameBeginEnvs();
                gameBegin();
            }

        }
;
    }

    private void gameBeginEnvs()
    {
        com.maddox.rts.HotKeyEnv.enable("timeCompression", false);
        com.maddox.rts.HotKeyEnv.enable("aircraftView", false);
        com.maddox.rts.HotKeyEnv.enable("HookView", false);
        com.maddox.rts.HotKeyEnv.enable("PanView", false);
        com.maddox.rts.HotKeyEnv.enable("SnapView", false);
        com.maddox.il2.game.Main3D.cur3D().keyRecord.setEnablePlayArgs(true);
    }

    private void gameBegin()
    {
        msgRecord = -1;
        msgFile = null;
        java.lang.String s = java.util.Locale.getDefault().getLanguage();
        if(!"us".equals(s))
        {
            msgFile = new SectFile(demoFile + "_" + s + ".msg");
            msgSect = msgFile.sectionIndex("all");
            if(msgSect < 0)
                msgFile = null;
        }
        if(msgFile == null)
            msgFile = new SectFile(demoFile + ".msg");
        msgSect = msgFile.sectionIndex("all");
        if(msgSect < 0)
            msgFile = null;
        msgTimeOut(msgFile);
        if(soundFile != null)
            com.maddox.rts.CmdEnv.top().exec("music FILE " + soundFile);
        com.maddox.il2.game.HUD.introESC(com.maddox.il2.game.I18N.gui("intro.msgESC"));
    }

    public void doQuitMission()
    {
        if(com.maddox.il2.game.Main3D.cur3D().playRecordedStreams() != null)
            com.maddox.il2.gui.GUI.chatDlg.hideWindow();
        com.maddox.il2.gui.GUI.activate();
        client.activateWindow();
        doExit();
    }

    public GUIBWDemoPlay(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(58);
        bDrawAllMessages = true;
        bArcade = false;
        bEnableVoice = true;
        flagMusic = new boolean[3];
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.GUIDialogClient)client.create(new GUIDialogClient());
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public static java.lang.String demoFile = "Intros/Intro";
    public static java.lang.String soundFile = "music/intro/intro";
    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.GUIDialogClient dialogClient;
    private com.maddox.gwindow.GWindowMessageBox loadMessageBox;
    private com.maddox.rts.SectFile msgFile;
    private int msgSect;
    private int msgRecord;
    private boolean bDrawAllMessages;
    private boolean flagTEX_VERTEX_ARRAYS;
    private boolean flagTEX_SECONDARY_COLOR_EXT;
    private int intHardwareShaders;
    private boolean bArcade;
    private boolean bEnableVoice;
    private boolean flagMusic[];















}
