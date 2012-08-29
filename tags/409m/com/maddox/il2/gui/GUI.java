// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUI.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.RendersMain;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.rts.BackgroundTask;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.HotKeyEnvs;
import com.maddox.rts.Keyboard;
import com.maddox.rts.Mouse;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.gui:
//            GUIRoot, GUILookAndFeel, GUIPlayerSelect, GUIMainMenu, 
//            GUISingleSelect, GUISingleBriefing, GUISingleMission, GUISingleComplete, 
//            GUISingleStat, GUIRecordSelect, GUIRecordPlay, GUIRecordSave, 
//            GUIRecordNetSave, GUISetup, GUISetup3D1, GUISetupVideo, 
//            GUISetupSound, GUISetupNet, GUISetupInput, GUIQuick, 
//            GUIView, GUICredits, GUIDifficulty, GUIBuilder, 
//            GUIControls, GUIPad, GUIObjectInspector, GUIObjectView, 
//            GUIQuickLoad, GUIQuickSave, GUICampaigns, GUICampaignNew, 
//            GUICampaignBriefing, GUICampaignMission, GUICampaignStat, GUICampaignStatView, 
//            GUIAwards, GUINet, GUINetNewClient, GUINetNewServer, 
//            GUINetClient, GUINetServer, GUINetServerMisSelect, GUINetServerDBrief, 
//            GUINetClientDBrief, GUINetDifficulty, GUINetServerDMission, GUINetClientDMission, 
//            GUINetServerCBrief, GUINetClientCBrief, GUINetServerCMission, GUINetClientCMission, 
//            GUINetAircraft, GUINetCScore, GUINetCStart, GUIArming, 
//            GUIAirArming, GUITrainingSelect, GUITrainingPlay, GUIBWDemoPlay, 
//            GUIDGenNew, GUIDGenBriefing, GUIDGenMission, GUIDGenDeBriefing, 
//            GUIDGenRoster, GUIDGenDocs, GUIDGenPilot, GUIDGenPilotDetail, 
//            GUINetServerNGenSelect, GUINetServerNGenProp, GUIChatDialog, GUIPocket

public class GUI
{

    public GUI()
    {
    }

    public static void activate()
    {
        com.maddox.il2.gui.GUI.activate(true);
    }

    public static void activate(boolean flag)
    {
        com.maddox.il2.engine.GUIWindowManager guiwindowmanager = com.maddox.il2.game.Main3D.cur3D().guiManager;
        guiwindowmanager.activateKeyboard(true);
        guiwindowmanager.activateMouse(true);
        if(!com.maddox.il2.game.Mission.isPlaying() || com.maddox.il2.game.Mission.isSingle() || com.maddox.il2.game.Main3D.cur3D().isDemoPlaying())
        {
            guiwindowmanager.activateTime(true);
            if(com.maddox.rts.RTSConf.cur.time.isEnableChangePause1())
                com.maddox.rts.Time.setPause(true);
            guiwindowmanager.activateJoy(true);
            com.maddox.il2.engine.RendersMain.setRenderFocus((com.maddox.il2.engine.Render)com.maddox.il2.engine.Actor.getByName("renderGUI"));
        }
        if(flag)
            com.maddox.il2.game.Main3D.cur3D().disableAllHotKeyCmdEnv();
    }

    public static void activateJoy()
    {
        com.maddox.il2.engine.GUIWindowManager guiwindowmanager = com.maddox.il2.game.Main3D.cur3D().guiManager;
        guiwindowmanager.activateJoy(true);
    }

    public static void activate(boolean flag, boolean flag1)
    {
        com.maddox.il2.engine.GUIWindowManager guiwindowmanager = com.maddox.il2.game.Main3D.cur3D().guiManager;
        guiwindowmanager.activateTime(true);
        if(flag)
        {
            guiwindowmanager.activateMouse(true);
            com.maddox.rts.Mouse.adapter().setFocus(guiwindowmanager);
        }
        if(flag1)
        {
            guiwindowmanager.activateKeyboard(true);
            com.maddox.rts.Keyboard.adapter().setFocus(guiwindowmanager);
        }
    }

    public static void unActivate()
    {
        com.maddox.il2.engine.GUIWindowManager guiwindowmanager = com.maddox.il2.game.Main3D.cur3D().guiManager;
        if(com.maddox.rts.Mouse.adapter().focus() == guiwindowmanager)
            com.maddox.rts.Mouse.adapter().setFocus(null);
        if(com.maddox.rts.Keyboard.adapter().focus() == guiwindowmanager)
            com.maddox.rts.Keyboard.adapter().setFocus(null);
        guiwindowmanager.unActivateAll();
        com.maddox.il2.engine.RendersMain.setRenderFocus(null);
        com.maddox.il2.game.Main3D.cur3D().enableOnlyGameHotKeyCmdEnvs();
    }

    public static void chatActivate()
    {
        if(chatDlg.isVisible())
        {
            if(com.maddox.il2.game.Main3D.cur3D().isDemoPlaying())
                return;
            com.maddox.il2.engine.GUIWindowManager guiwindowmanager = com.maddox.il2.game.Main3D.cur3D().guiManager;
            chatDlg.wEdit.setEditable(true);
            com.maddox.il2.game.Main3D.cur3D().hud.stopNetStat();
            chatDlg.wEdit.activateWindow();
            if(com.maddox.rts.Keyboard.adapter().focus() == guiwindowmanager)
                return;
            if(guiwindowmanager.isKeyboardActive())
                return;
            com.maddox.il2.gui.GUI.activate(true, true);
        }
    }

    public static void chatUnactivate()
    {
        if(chatDlg.isVisible())
        {
            com.maddox.il2.engine.GUIWindowManager guiwindowmanager = com.maddox.il2.game.Main3D.cur3D().guiManager;
            if(com.maddox.rts.Mouse.adapter().focus() == guiwindowmanager && com.maddox.rts.Keyboard.adapter().focus() == guiwindowmanager)
            {
                if(pad.isActive())
                {
                    guiwindowmanager.activateKeyboard(false);
                    com.maddox.rts.Keyboard.adapter().setFocus(null);
                } else
                {
                    com.maddox.il2.gui.GUI.unActivate();
                }
                chatDlg.wEdit.setEditable(false);
            }
        }
    }

    private static void initHotKeys()
    {
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(true, "activate") {

            public void end()
            {
                com.maddox.il2.engine.GUIWindowManager guiwindowmanager = com.maddox.il2.game.Main3D.cur3D().guiManager;
                com.maddox.il2.game.GameState gamestate = com.maddox.il2.game.Main.state();
                if(gamestate == null)
                    return;
                if(com.maddox.rts.BackgroundTask.isExecuted())
                    return;
                if(com.maddox.il2.gui.GUI.pad.isActive())
                {
                    com.maddox.il2.gui.GUI.pad.leave(false);
                    return;
                } else
                {
                    com.maddox.rts.RTSConf.cur.hotKeyEnvs.endAllActiveCmd(false);
                    gamestate.doQuitMission();
                    return;
                }
            }

        }
);
    }

    public static com.maddox.il2.engine.GUIWindowManager create(java.lang.String s)
    {
        envName = s;
        com.maddox.rts.HotKeyEnv.fromIni(envName, com.maddox.il2.engine.Config.cur.ini, "HotKey " + envName);
        com.maddox.il2.gui.GUI.initHotKeys();
        com.maddox.il2.gui.GUIRoot guiroot = new GUIRoot();
        com.maddox.il2.engine.GUIWindowManager guiwindowmanager = new GUIWindowManager(-2F, guiroot, new GUILookAndFeel(), "renderGUI");
        guiwindowmanager.activateKeyboard(true);
        guiwindowmanager.activateMouse(true);
        guiwindowmanager.activateTime(true);
        com.maddox.rts.Time.setPause(true);
        guiwindowmanager.activateJoy(true);
        com.maddox.il2.engine.RendersMain.setRenderFocus((com.maddox.il2.engine.Render)com.maddox.il2.engine.Actor.getByName("renderGUI"));
        com.maddox.il2.game.Main3D.cur3D().beginStep(50);
        new GUIPlayerSelect(guiroot);
        com.maddox.il2.gui.GUIMainMenu guimainmenu = new GUIMainMenu(guiroot);
        new GUISingleSelect(guiroot);
        new GUISingleBriefing(guiroot);
        new GUISingleMission(guiroot);
        new GUISingleComplete(guiroot);
        com.maddox.il2.game.Main3D.cur3D().beginStep(55);
        new GUISingleStat(guiroot);
        new GUIRecordSelect(guiroot);
        new GUIRecordPlay(guiroot);
        new GUIRecordSave(guiroot);
        new GUIRecordNetSave(guiroot);
        new GUISetup(guiroot);
        com.maddox.il2.game.Main3D.cur3D().beginStep(60);
        new GUISetup3D1(guiroot);
        new GUISetupVideo(guiroot);
        new GUISetupSound(guiroot);
        new GUISetupNet(guiroot);
        new GUISetupInput(guiroot);
        new GUIQuick(guiroot);
        com.maddox.il2.game.Main3D.cur3D().beginStep(65);
        new GUIView(guiroot);
        new GUICredits(guiroot);
        new GUIDifficulty(guiroot);
        com.maddox.il2.game.Main3D.cur3D().beginStep(70);
        new GUIBuilder(guiroot);
        com.maddox.il2.game.Main3D.cur3D().beginStep(75);
        new GUIControls(guiroot);
        pad = new GUIPad(guiroot);
        new GUIObjectInspector(guiroot);
        com.maddox.il2.game.Main3D.cur3D().beginStep(80);
        new GUIObjectView(guiroot);
        new GUIQuickLoad(guiroot);
        new GUIQuickSave(guiroot);
        new GUICampaigns(guiroot);
        new GUICampaignNew(guiroot);
        com.maddox.il2.game.Main3D.cur3D().beginStep(85);
        new GUICampaignBriefing(guiroot);
        new GUICampaignMission(guiroot);
        new GUICampaignStat(guiroot);
        new GUICampaignStatView(guiroot);
        new GUIAwards(guiroot);
        new GUINet(guiroot);
        new GUINetNewClient(guiroot);
        new GUINetNewServer(guiroot);
        new GUINetClient(guiroot);
        new GUINetServer(guiroot);
        new GUINetServerMisSelect(guiroot);
        new GUINetServerDBrief(guiroot);
        new GUINetClientDBrief(guiroot);
        new GUINetDifficulty(guiroot);
        new GUINetServerDMission(guiroot);
        new GUINetClientDMission(guiroot);
        new GUINetServerCBrief(guiroot);
        new GUINetClientCBrief(guiroot);
        new GUINetServerCMission(guiroot);
        new GUINetClientCMission(guiroot);
        new GUINetAircraft(guiroot);
        new GUINetCScore(guiroot);
        new GUINetCStart(47, guiroot);
        new GUINetCStart(48, guiroot);
        new GUIArming(guiroot);
        new GUIAirArming(guiroot);
        new GUITrainingSelect(guiroot);
        new GUITrainingPlay(guiroot);
        new GUIBWDemoPlay(guiroot);
        new GUIDGenNew(guiroot);
        new GUIDGenBriefing(guiroot);
        new GUIDGenMission(guiroot);
        new GUIDGenDeBriefing(guiroot);
        new GUIDGenRoster(guiroot);
        new GUIDGenDocs(guiroot);
        new GUIDGenPilot(guiroot);
        new GUIDGenPilotDetail(guiroot);
        new GUINetServerNGenSelect(guiroot);
        new GUINetServerNGenProp(guiroot);
        chatDlg = new GUIChatDialog(guiroot);
        chatDlg.hideWindow();
        com.maddox.il2.game.Main3D.cur3D().beginStep(90);
        guiroot.lAF().bSoundEnable = true;
        com.maddox.il2.game.Main3D.cur3D().enableOnlyHotKeyCmdEnv(envName);
        com.maddox.il2.gui.GUIMainMenu guimainmenu1 = (com.maddox.il2.gui.GUIMainMenu)com.maddox.il2.game.GameState.get(2);
        guimainmenu1.pPilotName.cap = new GCaption(com.maddox.il2.ai.World.cur().userCfg.name + " '" + com.maddox.il2.ai.World.cur().userCfg.callsign + "' " + com.maddox.il2.ai.World.cur().userCfg.surname);
        com.maddox.il2.game.Main.stateStack().push(guimainmenu);
        guiroot.C.alpha = 224;
        return guiwindowmanager;
    }

    public static java.lang.String envName;
    public static com.maddox.il2.gui.GUIPad pad;
    public static com.maddox.il2.gui.GUIChatDialog chatDlg;
}
