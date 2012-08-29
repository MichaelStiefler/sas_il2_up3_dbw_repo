// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIRecordPlay.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.engine.hotkey.HookView;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetMissionListener;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetServerParams;
import com.maddox.rts.BackgroundTask;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.KeyRecord;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgBackgroundTaskListener;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetObj;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.sound.AudioDevice;

// Referenced classes of package com.maddox.il2.gui:
//            GUIRecordSelect, GUIClient, GUIInfoMenu, GUIInfoName, 
//            GUILookAndFeel, GUIButton, GUISwitchBox2, GUI, 
//            GUIChatDialog, GUIDialogClient, GUISeparate

public class GUIRecordPlay extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bVideo)
            {
                com.maddox.il2.game.Main.stateStack().push(12);
                return true;
            }
            if(gwindow == b3d)
            {
                com.maddox.il2.game.Main.stateStack().push(11);
                return true;
            }
            if(gwindow == bSound)
            {
                com.maddox.il2.game.Main.stateStack().push(13);
                return true;
            }
            if(gwindow == sTimeCompression)
            {
                bDemoChanges = true;
                return true;
            }
            if(gwindow == sViewControls)
            {
                bDemoChanges = true;
                return true;
            }
            if(gwindow == sViewMessages)
            {
                com.maddox.il2.game.Main3D.cur3D().hud.bDrawAllMessages = sViewMessages.isChecked();
                com.maddox.il2.gui.GUIRecordSelect guirecordselect = (com.maddox.il2.gui.GUIRecordSelect)com.maddox.il2.game.GameState.get(7);
                guirecordselect.bDrawAllMessages = sViewMessages.isChecked();
                return true;
            }
            if(gwindow == bExit)
            {
                doExit();
                return true;
            }
            if(gwindow == bBack)
            {
                com.maddox.il2.gui.GUIRecordSelect guirecordselect1 = (com.maddox.il2.gui.GUIRecordSelect)com.maddox.il2.game.GameState.get(7);
                guirecordselect1.bManualTimeCompression = sTimeCompression.isChecked();
                guirecordselect1.bManualViewControls = sViewControls.isChecked();
                com.maddox.rts.HotKeyEnv.enable("timeCompression", guirecordselect1.bManualTimeCompression);
                com.maddox.rts.HotKeyEnv.enable("aircraftView", guirecordselect1.bManualViewControls);
                com.maddox.rts.HotKeyEnv.enable("HookView", guirecordselect1.bManualViewControls);
                com.maddox.rts.HotKeyEnv.enable("PanView", guirecordselect1.bManualViewControls);
                com.maddox.rts.HotKeyEnv.enable("SnapView", guirecordselect1.bManualViewControls);
                com.maddox.il2.game.Main3D.cur3D().keyRecord.setEnablePlayArgs(!guirecordselect1.bManualViewControls);
                client.hideWindow();
                com.maddox.il2.gui.GUI.unActivate();
                if(com.maddox.il2.game.Main3D.cur3D().playRecordedStreams() != null)
                    com.maddox.il2.gui.GUI.chatDlg.showWindow();
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(240F), x1024(320F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(512F), x1024(320F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(624F), x1024(320F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(96F), y1024(32F), x1024(224F), y1024(48F), 0, i18n("record.VideoModes"));
            draw(x1024(96F), y1024(96F), x1024(224F), y1024(48F), 0, i18n("record.VideoOptions"));
            draw(x1024(96F), y1024(160F), x1024(224F), y1024(48F), 0, i18n("record.SoundSetup"));
            if(com.maddox.rts.RTSConf.cur.time.isEnableChangePause1())
                draw(x1024(96F), y1024(272F), x1024(224F), y1024(48F), 0, i18n("record.ManualTime"));
            draw(x1024(96F), y1024(352F), x1024(224F), y1024(48F), 0, i18n("record.ManualView"));
            draw(x1024(96F), y1024(432F), x1024(224F), y1024(48F), 0, i18n("record.InflightMessages"));
            draw(x1024(96F), y1024(544F), x1024(224F), y1024(48F), 0, i18n("record.Return2track"));
            draw(x1024(96F), y1024(656F), x1024(224F), y1024(48F), 0, i18n("record.Stop"));
        }

        public void setPosSize()
        {
            set1024PosSize(334F, 32F, 384F, 736F);
            bVideo.setPosC(x1024(56F), y1024(56F));
            b3d.setPosC(x1024(56F), y1024(120F));
            bSound.setPosC(x1024(56F), y1024(184F));
            sTimeCompression.setPosC(x1024(64F), y1024(296F));
            sViewControls.setPosC(x1024(64F), y1024(376F));
            sViewMessages.setPosC(x1024(64F), y1024(458F));
            bBack.setPosC(x1024(56F), y1024(568F));
            bExit.setPosC(x1024(56F), y1024(682F));
        }

        public DialogClient()
        {
        }
    }

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


    public void enterPush(com.maddox.il2.game.GameState gamestate)
    {
        _enter();
        bDemoChanges = sTimeCompression.isChecked() || sViewControls.isChecked();
    }

    public void enterPop(com.maddox.il2.game.GameState gamestate)
    {
        client.activateWindow();
    }

    public void _enter()
    {
        com.maddox.il2.gui.GUIRecordSelect guirecordselect = (com.maddox.il2.gui.GUIRecordSelect)com.maddox.il2.game.GameState.get(7);
        sTimeCompression.showWindow();
        sTimeCompression.setChecked(guirecordselect.bManualTimeCompression, false);
        sViewControls.setChecked(guirecordselect.bManualViewControls, false);
        sViewMessages.setChecked(guirecordselect.bDrawAllMessages, false);
        loadMessageBox = new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("record.StandBy"), i18n("record.LoadingTrack"), 5, 0.0F) {

            public void result(int i)
            {
                if(i == 1)
                    com.maddox.rts.BackgroundTask.cancel(i18n("miss.UserCancel"));
            }

        }
;
        new com.maddox.rts.MsgAction(72, 0.0D) {

            public void doAction()
            {
                com.maddox.il2.game.Main.closeAllNetChannels();
                com.maddox.il2.gui.GUIRecordSelect guirecordselect1 = (com.maddox.il2.gui.GUIRecordSelect)com.maddox.il2.game.GameState.get(7);
                com.maddox.il2.gui.MissionListener missionlistener = new MissionListener();
                selectedFileName = guirecordselect1.selectedFile;
                java.lang.String s = com.maddox.il2.game.Main3D.cur3D().playRecordedMission("Records/" + guirecordselect1.selectedFile);
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

    public void doReplayMission(java.lang.String s, boolean flag)
    {
        com.maddox.il2.gui.GUI.activate();
        com.maddox.il2.gui.GUIRecordSelect guirecordselect = (com.maddox.il2.gui.GUIRecordSelect)com.maddox.il2.game.GameState.get(7);
        if(!guirecordselect.bCycle && flag)
        {
            doExit(true);
            return;
        }
        if(com.maddox.il2.net.NetMissionTrack.isRecording())
        {
            com.maddox.il2.net.NetMissionTrack.stopRecording();
            com.maddox.il2.gui.GUI.activate();
        }
        if(com.maddox.il2.game.Main.cur().netServerParams != null)
            com.maddox.il2.game.Main.cur().netServerParams.destroy();
        if(com.maddox.rts.RTSConf.cur.netEnv.control != null)
            com.maddox.rts.RTSConf.cur.netEnv.control.destroy();
        loadMessageBox = new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("record.StandBy"), i18n("record.LoadingTrack"), 5, 0.0F) {

            public void result(int i)
            {
                if(i == 1)
                    com.maddox.rts.BackgroundTask.cancel(i18n("miss.UserCancel"));
            }

        }
;
        _replayRecordFile = s;
        new com.maddox.rts.MsgAction(72, 0.0D) {

            public void doAction()
            {
                com.maddox.il2.game.Main.closeAllNetChannels();
                com.maddox.il2.gui.MissionListener missionlistener = new MissionListener();
                java.lang.String s1 = com.maddox.il2.game.Main3D.cur3D().playRecordedMission(_replayRecordFile, false);
                if(s1 != null)
                {
                    com.maddox.rts.BackgroundTask.removeListener(missionlistener);
                    recordBad(s1);
                } else
                if(com.maddox.il2.game.Main3D.cur3D().playRecordedStreams() != null)
                    com.maddox.il2.game.Main.cur().netMissionListener = missionlistener;
            }

        }
;
    }

    private void doExit()
    {
        doExit(false);
    }

    private void doExit(boolean flag)
    {
        boolean flag1 = com.maddox.il2.game.Main3D.cur3D().playRecordedStreams() != null;
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
        if(bDemoChanges && com.maddox.il2.net.NetMissionTrack.countRecorded == 0 && !flag1)
        {
            com.maddox.il2.game.Main.stateStack().change(9);
        } else
        {
            com.maddox.il2.engine.hotkey.HookView.loadConfig();
            com.maddox.il2.game.Main.stateStack().pop();
        }
    }

    private void recordBad(java.lang.String s)
    {
        loadMessageBox.close(false);
        new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("record.Error"), s, 3, 0.0F) {

            public void result(int i)
            {
                bDemoChanges = false;
                doExit();
            }

        }
;
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
                gameBeginEnvs();
            }

        }
;
    }

    private void gameBeginEnvs()
    {
        com.maddox.il2.gui.GUIRecordSelect guirecordselect = (com.maddox.il2.gui.GUIRecordSelect)com.maddox.il2.game.GameState.get(7);
        com.maddox.rts.HotKeyEnv.enable("timeCompression", guirecordselect.bManualTimeCompression);
        com.maddox.rts.HotKeyEnv.enable("aircraftView", guirecordselect.bManualViewControls);
        com.maddox.rts.HotKeyEnv.enable("HookView", guirecordselect.bManualViewControls);
        com.maddox.rts.HotKeyEnv.enable("PanView", guirecordselect.bManualViewControls);
        com.maddox.rts.HotKeyEnv.enable("SnapView", guirecordselect.bManualViewControls);
        com.maddox.il2.game.Main3D.cur3D().keyRecord.setEnablePlayArgs(!guirecordselect.bManualViewControls);
    }

    public void _leave()
    {
        client.hideWindow();
    }

    public void doQuitMission()
    {
        if(com.maddox.il2.game.Main3D.cur3D().playRecordedStreams() != null)
            com.maddox.il2.gui.GUI.chatDlg.hideWindow();
        com.maddox.il2.gui.GUI.activate();
        com.maddox.rts.Time _tmp = com.maddox.rts.RTSConf.cur.time;
        if(com.maddox.rts.Time.isEnableChangeSpeed())
            sTimeCompression.showWindow();
        else
            sTimeCompression.hideWindow();
        client.activateWindow();
    }

    public GUIRecordPlay(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(8);
        bDemoChanges = false;
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("record.infoPlay");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bVideo = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        b3d = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bSound = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bBack = (com.maddox.il2.gui.GUIButton)dialogClient.addDefault(new GUIButton(dialogClient, gtexture, 0.0F, 192F, 48F, 48F));
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        sTimeCompression = (com.maddox.il2.gui.GUISwitchBox2)dialogClient.addControl(new GUISwitchBox2(dialogClient));
        sViewControls = (com.maddox.il2.gui.GUISwitchBox2)dialogClient.addControl(new GUISwitchBox2(dialogClient));
        sViewMessages = (com.maddox.il2.gui.GUISwitchBox2)dialogClient.addControl(new GUISwitchBox2(dialogClient));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton bVideo;
    public com.maddox.il2.gui.GUIButton b3d;
    public com.maddox.il2.gui.GUIButton bSound;
    public com.maddox.il2.gui.GUIButton bExit;
    public com.maddox.il2.gui.GUIButton bBack;
    public com.maddox.il2.gui.GUISwitchBox2 sTimeCompression;
    public com.maddox.il2.gui.GUISwitchBox2 sViewControls;
    public com.maddox.il2.gui.GUISwitchBox2 sViewMessages;
    private boolean bDemoChanges;
    private com.maddox.gwindow.GWindowMessageBox loadMessageBox;
    private java.lang.String selectedFileName;
    private java.lang.String _replayRecordFile;

    static 
    {
        com.maddox.rts.Spawn.get("com.maddox.il2.fm.FlightModelTrack");
        com.maddox.rts.Spawn.get("com.maddox.il2.game.GameTrack");
    }









}
