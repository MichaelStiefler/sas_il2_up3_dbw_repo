// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUITrainingPlay.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.World;
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
import com.maddox.rts.HotKeyCmdEnvs;
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
//            GUITrainingSelect, GUIClient, GUIInfoMenu, GUIInfoName, 
//            GUILookAndFeel, GUIButton, GUI, GUIChatDialog, 
//            GUIDialogClient, GUISeparate

public class GUITrainingPlay extends com.maddox.il2.game.GameState
    implements com.maddox.rts.MsgTimeOutListener
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
            if(gwindow == bControls)
            {
                com.maddox.il2.game.Main.stateStack().push(20);
                return true;
            }
            if(gwindow == bFly)
            {
                doFly();
                return true;
            }
            if(gwindow == bExit)
            {
                doExit();
                return true;
            }
            if(gwindow == bBack)
            {
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
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(304F), x1024(288F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(416F), x1024(288F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(528F), x1024(288F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(96F), y1024(32F), x1024(224F), y1024(48F), 0, i18n("training.VideoModes"));
            draw(x1024(96F), y1024(96F), x1024(224F), y1024(48F), 0, i18n("training.VideoOptions"));
            draw(x1024(96F), y1024(160F), x1024(224F), y1024(48F), 0, i18n("training.SoundSetup"));
            draw(x1024(96F), y1024(224F), x1024(224F), y1024(48F), 0, i18n("training.Controls"));
            if(!bFreeFly && bFly.isVisible())
                draw(x1024(96F), y1024(336F), x1024(224F), y1024(48F), 0, i18n("training.FreeFly"));
            draw(x1024(96F), y1024(448F), x1024(224F), y1024(48F), 0, i18n("training.Return2track"));
            draw(x1024(96F), y1024(560F), x1024(224F), y1024(48F), 0, i18n("training.Stop"));
        }

        public void setPosSize()
        {
            set1024PosSize(352F, 80F, 352F, 640F);
            bVideo.setPosC(x1024(56F), y1024(56F));
            b3d.setPosC(x1024(56F), y1024(120F));
            bSound.setPosC(x1024(56F), y1024(184F));
            bControls.setPosC(x1024(56F), y1024(248F));
            bFly.setPosC(x1024(56F), y1024(360F));
            bBack.setPosC(x1024(56F), y1024(472F));
            bExit.setPosC(x1024(56F), y1024(584F));
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


    public void msgTimeOut(java.lang.Object obj)
    {
        if(obj != msgFile || obj == null)
            return;
        if(msgSect < 0)
            return;
        if(msgRecord >= 0)
        {
            java.lang.String s = msgFile.value(msgSect, msgRecord);
            if(s != null)
                s = com.maddox.util.UnicodeTo8bit.load(s);
            com.maddox.il2.game.HUD.training(s);
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

    public void enterPush(com.maddox.il2.game.GameState gamestate)
    {
        bFreeFly = false;
        bFly.showWindow();
        bArcade = com.maddox.il2.ai.World.cur().isArcade();
        com.maddox.il2.ai.World.cur().setArcade(false);
        _enter();
    }

    public void enterPop(com.maddox.il2.game.GameState gamestate)
    {
        client.activateWindow();
    }

    public void _enter()
    {
        com.maddox.il2.game.Main3D.cur3D().hud.bDrawVoiceMessages = false;
        loadMessageBox = new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("training.StandBy"), i18n("training.LoadingTrack"), 5, 0.0F) {

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
                com.maddox.il2.gui.GUITrainingSelect guitrainingselect = (com.maddox.il2.gui.GUITrainingSelect)com.maddox.il2.game.GameState.get(56);
                com.maddox.il2.gui.MissionListener missionlistener = new MissionListener();
                java.lang.String s = com.maddox.il2.game.Main3D.cur3D().playRecordedMission("Training/" + guitrainingselect.selectedTrack);
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

    public void leavePop(com.maddox.il2.game.GameState gamestate)
    {
        com.maddox.il2.ai.World.cur().setArcade(bArcade);
        _leave();
    }

    public void doExit()
    {
        msgFile = null;
        com.maddox.il2.game.HUD.training(null);
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

    public void doFly()
    {
        bFreeFly = true;
        bFly.hideWindow();
        msgFile = null;
        com.maddox.il2.game.HUD.training(null);
        client.hideWindow();
        com.maddox.il2.gui.GUI.unActivate();
        com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.endAllCmd();
        com.maddox.il2.game.Main3D.cur3D().flyRecordedMission();
    }

    private void recordBad(java.lang.String s)
    {
        loadMessageBox.close(false);
        new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("training.Error"), s, 3, 0.0F) {

            public void result(int i)
            {
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
        bFly.hideWindow();
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
                bFly.showWindow();
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
        com.maddox.il2.gui.GUITrainingSelect guitrainingselect = (com.maddox.il2.gui.GUITrainingSelect)com.maddox.il2.game.GameState.get(56);
        msgRecord = -1;
        msgFile = null;
        java.lang.String s = java.util.Locale.getDefault().getLanguage();
        if(!"us".equals(s))
        {
            msgFile = new SectFile("Training/" + guitrainingselect.selectedTrack + "_" + s + ".msg");
            msgSect = msgFile.sectionIndex("all");
            if(msgSect < 0)
                msgFile = null;
        }
        if(msgFile == null)
            msgFile = new SectFile("Training/" + guitrainingselect.selectedTrack + ".msg");
        msgSect = msgFile.sectionIndex("all");
        if(msgSect < 0)
            msgFile = null;
        msgTimeOut(msgFile);
    }

    public void _leave()
    {
        com.maddox.il2.game.Main3D.cur3D().hud.bDrawVoiceMessages = true;
        client.hideWindow();
    }

    public void doQuitMission()
    {
        if(com.maddox.il2.game.Main3D.cur3D().playRecordedStreams() != null)
            com.maddox.il2.gui.GUI.chatDlg.hideWindow();
        com.maddox.il2.gui.GUI.activate();
        client.activateWindow();
    }

    public GUITrainingPlay(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(57);
        bArcade = false;
        bFreeFly = false;
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("training.infoPlay");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bVideo = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        b3d = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bSound = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bControls = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bFly = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bBack = (com.maddox.il2.gui.GUIButton)dialogClient.addDefault(new GUIButton(dialogClient, gtexture, 0.0F, 192F, 48F, 48F));
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
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
    public com.maddox.il2.gui.GUIButton bControls;
    public com.maddox.il2.gui.GUIButton bFly;
    public com.maddox.il2.gui.GUIButton bExit;
    public com.maddox.il2.gui.GUIButton bBack;
    private com.maddox.gwindow.GWindowMessageBox loadMessageBox;
    private com.maddox.rts.SectFile msgFile;
    private int msgSect;
    private int msgRecord;
    private boolean bArcade;
    public boolean bFreeFly;






}
