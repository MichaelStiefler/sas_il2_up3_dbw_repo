// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIMission.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetLocalControl;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetServerParams;
import com.maddox.rts.BackgroundTask;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgBackgroundTaskListener;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetObj;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUI, GUIDialogClient, GUISeparate

public class GUIMission extends com.maddox.il2.game.GameState
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
            if(gwindow == bExit)
            {
                if(com.maddox.il2.net.NetMissionTrack.isRecording())
                {
                    com.maddox.il2.net.NetMissionTrack.stopRecording();
                    com.maddox.il2.gui.GUI.activate();
                }
                com.maddox.il2.game.Main.cur().netServerParams.destroy();
                com.maddox.rts.RTSConf.cur.netEnv.control.destroy();
                doExit();
                return true;
            }
            if(gwindow == bBack)
            {
                client.hideWindow();
                com.maddox.il2.gui.GUI.unActivate();
                return true;
            }
            if(gwindow == bTrack)
            {
                if(com.maddox.il2.net.NetMissionTrack.isRecording())
                {
                    com.maddox.il2.net.NetMissionTrack.stopRecording();
                    client.hideWindow();
                    com.maddox.il2.gui.GUI.unActivate();
                } else
                {
                    com.maddox.il2.game.Main.stateStack().push(59);
                }
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(303F), x1024(288F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(416F), x1024(288F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(96F), y1024(32F), x1024(224F), y1024(48F), 0, i18n("miss.VideoModes"));
            draw(x1024(96F), y1024(96F), x1024(224F), y1024(48F), 0, i18n("miss.VideoOptions"));
            draw(x1024(96F), y1024(160F), x1024(224F), y1024(48F), 0, i18n("miss.SoundSetup"));
            draw(x1024(96F), y1024(224F), x1024(224F), y1024(48F), 0, i18n("miss.Controls"));
            draw(x1024(96F), y1024(336F), x1024(224F), y1024(48F), 0, i18n("miss.QuitMiss"));
            draw(x1024(96F), y1024(448F), x1024(224F), y1024(48F), 0, i18n("miss.Return2Miss"));
            if(com.maddox.il2.net.NetMissionTrack.isRecording())
                draw(x1024(96F), y1024(512F), x1024(224F), y1024(48F), 0, i18n("miss.StopRecording"));
            else
                draw(x1024(96F), y1024(512F), x1024(224F), y1024(48F), 0, i18n("miss.StartRecording"));
        }

        public void setPosSize()
        {
            set1024PosSize(350F, 80F, 352F, 592F);
            bVideo.setPosC(x1024(56F), y1024(56F));
            b3d.setPosC(x1024(56F), y1024(120F));
            bSound.setPosC(x1024(56F), y1024(184F));
            bControls.setPosC(x1024(56F), y1024(248F));
            bExit.setPosC(x1024(56F), y1024(360F));
            bBack.setPosC(x1024(56F), y1024(472F));
            bTrack.setPosC(x1024(56F), y1024(536F));
        }

        public DialogClient()
        {
        }
    }

    class MissionListener
        implements com.maddox.rts.MsgBackgroundTaskListener
    {

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
                gameBegin();
            else
                missionBad(i18n("miss.LoadBad") + " " + backgroundtask.messageCancel());
        }

        public MissionListener()
        {
            com.maddox.rts.BackgroundTask.addListener(this);
        }
    }


    public void enterPush(com.maddox.il2.game.GameState gamestate)
    {
        enter(gamestate);
    }

    public void enter(com.maddox.il2.game.GameState gamestate)
    {
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
        {
            new NetServerParams();
            com.maddox.il2.game.Main.cur().netServerParams.setMode(2);
            new NetLocalControl();
        }
        loadMessageBox = new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("miss.StandBy"), i18n("miss.Loading"), 5, 0.0F) {

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
                if(com.maddox.il2.game.Mission.cur() != null)
                    com.maddox.il2.game.Mission.cur().destroy();
                com.maddox.il2.ai.World.cur().diffCur.set(com.maddox.il2.ai.World.cur().diffUser);
                com.maddox.il2.game.Main.cur().netServerParams.setDifficulty(com.maddox.il2.ai.World.cur().diffCur.get());
                com.maddox.il2.gui.MissionListener missionlistener = new MissionListener();
                try
                {
                    com.maddox.il2.game.Mission.preparePlayerNumberOn(com.maddox.il2.game.Main.cur().currentMissionFile);
                    com.maddox.il2.game.Mission.loadFromSect(com.maddox.il2.game.Main.cur().currentMissionFile, true);
                }
                catch(java.lang.Exception exception)
                {
                    java.lang.System.out.println(exception.getMessage());
                    exception.printStackTrace();
                    com.maddox.rts.BackgroundTask.removeListener(missionlistener);
                    missionBad(i18n("miss.LoadFailed"));
                }
            }

        }
;
    }

    private void missionBad(java.lang.String s)
    {
        loadMessageBox.close(false);
        new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("miss.Error"), s, 3, 0.0F) {

            public void result(int i)
            {
                com.maddox.il2.game.Main.cur().netServerParams.destroy();
                com.maddox.rts.RTSConf.cur.netEnv.control.destroy();
                com.maddox.il2.game.Main.stateStack().pop();
            }

        }
;
    }

    public void gameBegin()
    {
        new com.maddox.rts.MsgAction(72, 0.0D) {

            public void doAction()
            {
                com.maddox.il2.engine.GUIWindowManager guiwindowmanager = com.maddox.il2.game.Main3D.cur3D().guiManager;
                loadMessageBox.close(false);
                guiwindowmanager.setTimeGameActive(false);
                com.maddox.il2.gui.GUI.unActivate();
                com.maddox.rts.CmdEnv.top().exec("mission BEGIN");
                com.maddox.rts.HotKeyCmd.exec("aircraftView", "CockpitView");
            }

        }
;
    }

    public void _leave()
    {
        client.hideWindow();
    }

    public void enterPop(com.maddox.il2.game.GameState gamestate)
    {
        if(gamestate.id() == 59)
        {
            client.hideWindow();
            com.maddox.il2.gui.GUI.unActivate();
            com.maddox.rts.Time.setPause(false);
        } else
        {
            client.activateWindow();
        }
    }

    public void doQuitMission()
    {
        com.maddox.il2.gui.GUI.activate();
        client.activateWindow();
    }

    protected void doExit()
    {
    }

    protected void init(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = "";
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bVideo = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        b3d = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bSound = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bControls = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bBack = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 192F, 48F, 48F));
        bTrack = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public GUIMission(int i)
    {
        super(i);
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton bVideo;
    public com.maddox.il2.gui.GUIButton b3d;
    public com.maddox.il2.gui.GUIButton bSound;
    public com.maddox.il2.gui.GUIButton bControls;
    public com.maddox.il2.gui.GUIButton bExit;
    public com.maddox.il2.gui.GUIButton bBack;
    public com.maddox.il2.gui.GUIButton bTrack;
    private com.maddox.gwindow.GWindowMessageBox loadMessageBox;


}
