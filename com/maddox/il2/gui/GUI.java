package com.maddox.il2.gui;

import com.maddox.gwindow.GCaption;
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

public class GUI
{
  public static String envName;
  public static GUIPad pad;
  public static GUIChatDialog chatDlg;

  public static void activate()
  {
    activate(true);
  }
  public static void activate(boolean paramBoolean) {
    GUIWindowManager localGUIWindowManager = Main3D.cur3D().guiManager;
    localGUIWindowManager.activateKeyboard(true);
    localGUIWindowManager.activateMouse(true);
    if ((!Mission.isPlaying()) || (Mission.isSingle()) || (Main3D.cur3D().isDemoPlaying())) {
      localGUIWindowManager.activateTime(true);
      if (RTSConf.cur.time.isEnableChangePause1())
        Time.setPause(true);
      localGUIWindowManager.activateJoy(true);
      RendersMain.setRenderFocus((Render)Actor.getByName("renderGUI"));
    }
    if (paramBoolean)
      Main3D.cur3D().disableAllHotKeyCmdEnv();
  }

  public static void activateJoy() {
    GUIWindowManager localGUIWindowManager = Main3D.cur3D().guiManager;
    localGUIWindowManager.activateJoy(true);
  }

  public static void activate(boolean paramBoolean1, boolean paramBoolean2) {
    GUIWindowManager localGUIWindowManager = Main3D.cur3D().guiManager;
    localGUIWindowManager.activateTime(true);
    if (paramBoolean1) {
      localGUIWindowManager.activateMouse(true);
      Mouse.adapter().setFocus(localGUIWindowManager);
    }
    if (paramBoolean2) {
      localGUIWindowManager.activateKeyboard(true);
      Keyboard.adapter().setFocus(localGUIWindowManager);
    }
  }

  public static void unActivate() {
    GUIWindowManager localGUIWindowManager = Main3D.cur3D().guiManager;
    if (Mouse.adapter().focus() == localGUIWindowManager)
      Mouse.adapter().setFocus(null);
    if (Keyboard.adapter().focus() == localGUIWindowManager)
      Keyboard.adapter().setFocus(null);
    localGUIWindowManager.unActivateAll();
    RendersMain.setRenderFocus(null);
    Main3D.cur3D().enableOnlyGameHotKeyCmdEnvs();
  }

  public static void chatActivate() {
    if (chatDlg.isVisible()) {
      if (Main3D.cur3D().isDemoPlaying())
        return;
      GUIWindowManager localGUIWindowManager = Main3D.cur3D().guiManager;
      chatDlg.wEdit.setEditable(true);
      Main3D.cur3D().hud.stopNetStat();
      chatDlg.wEdit.activateWindow();
      if (Keyboard.adapter().focus() == localGUIWindowManager)
        return;
      if (localGUIWindowManager.isKeyboardActive())
        return;
      activate(true, true);
    }
  }

  public static void chatUnactivate() {
    if (chatDlg.isVisible()) {
      GUIWindowManager localGUIWindowManager = Main3D.cur3D().guiManager;
      if ((Mouse.adapter().focus() == localGUIWindowManager) && (Keyboard.adapter().focus() == localGUIWindowManager))
      {
        if (pad.isActive()) {
          localGUIWindowManager.activateKeyboard(false);
          Keyboard.adapter().setFocus(null);
        } else {
          unActivate();
        }
        chatDlg.wEdit.setEditable(false);
      }
    }
  }

  private static void initHotKeys() {
    HotKeyCmdEnv.addCmd(envName, new HotKeyCmd(true, "activate") {
      public void end() {
        GUIWindowManager localGUIWindowManager = Main3D.cur3D().guiManager;
        GameState localGameState = Main.state();
        if (localGameState == null) return;
        if (BackgroundTask.isExecuted()) return;

        if (GUI.pad.isActive()) {
          GUI.pad.leave(false);
          return;
        }
        RTSConf.cur.hotKeyEnvs.endAllActiveCmd(false);
        localGameState.doQuitMission();
      } } );
  }

  public static GUIWindowManager create(String paramString) {
    envName = paramString;
    HotKeyEnv.fromIni(envName, Config.cur.ini, "HotKey " + envName);
    initHotKeys();

    GUIRoot localGUIRoot = new GUIRoot();
    GUIWindowManager localGUIWindowManager = new GUIWindowManager(-2.0F, localGUIRoot, new GUILookAndFeel(), "renderGUI");

    localGUIWindowManager.activateKeyboard(true);
    localGUIWindowManager.activateMouse(true);
    localGUIWindowManager.activateTime(true);
    Time.setPause(true);
    localGUIWindowManager.activateJoy(true);
    RendersMain.setRenderFocus((Render)Actor.getByName("renderGUI"));

    Main3D.cur3D().beginStep(50);

    new GUIPlayerSelect(localGUIRoot);
    GUIMainMenu localGUIMainMenu1 = new GUIMainMenu(localGUIRoot);
    new GUISingleSelect(localGUIRoot);
    new GUISingleBriefing(localGUIRoot);
    new GUISingleMission(localGUIRoot);
    new GUISingleComplete(localGUIRoot);
    Main3D.cur3D().beginStep(55);
    new GUISingleStat(localGUIRoot);
    new GUIRecordSelect(localGUIRoot);
    new GUIRecordPlay(localGUIRoot);
    new GUIRecordSave(localGUIRoot);
    new GUIRecordNetSave(localGUIRoot);

    new GUISetup(localGUIRoot);
    Main3D.cur3D().beginStep(60);
    new GUISetup3D1(localGUIRoot);
    new GUISetupVideo(localGUIRoot);
    new GUISetupSound(localGUIRoot);
    new GUISetupNet(localGUIRoot);
    new GUISetupInput(localGUIRoot);
    new GUIQuick(localGUIRoot);
    Main3D.cur3D().beginStep(65);
    new GUIView(localGUIRoot);
    new GUICredits(localGUIRoot);
    new GUIDifficulty(localGUIRoot);
    Main3D.cur3D().beginStep(70);
    new GUIBuilder(localGUIRoot);
    Main3D.cur3D().beginStep(75);
    new GUIControls(localGUIRoot);
    pad = new GUIPad(localGUIRoot);
    new GUIObjectInspector(localGUIRoot);
    Main3D.cur3D().beginStep(80);
    new GUIObjectView(localGUIRoot);
    new GUIQuickLoad(localGUIRoot);
    new GUIQuickSave(localGUIRoot);
    new GUICampaigns(localGUIRoot);
    new GUICampaignNew(localGUIRoot);
    Main3D.cur3D().beginStep(85);
    new GUICampaignBriefing(localGUIRoot);
    new GUICampaignMission(localGUIRoot);
    new GUICampaignStat(localGUIRoot);
    new GUICampaignStatView(localGUIRoot);
    new GUIAwards(localGUIRoot);
    new GUIQuickStats(localGUIRoot);
    new GUINet(localGUIRoot);
    new GUINetNewClient(localGUIRoot);
    new GUINetNewServer(localGUIRoot);
    new GUINetClient(localGUIRoot);
    new GUINetServer(localGUIRoot);
    new GUINetServerMisSelect(localGUIRoot);
    new GUINetServerDBrief(localGUIRoot);
    new GUINetClientDBrief(localGUIRoot);
    new GUINetDifficulty(localGUIRoot);
    new GUINetServerDMission(localGUIRoot);
    new GUINetClientDMission(localGUIRoot);
    new GUINetServerCBrief(localGUIRoot);
    new GUINetClientCBrief(localGUIRoot);
    new GUINetServerCMission(localGUIRoot);
    new GUINetClientCMission(localGUIRoot);
    new GUINetAircraft(localGUIRoot);
    new GUINetCScore(localGUIRoot);
    new GUINetCStart(47, localGUIRoot);
    new GUINetCStart(48, localGUIRoot);
    new GUIArming(localGUIRoot);
    new GUIAirArming(localGUIRoot);
    new GUITrainingSelect(localGUIRoot);
    new GUITrainingPlay(localGUIRoot);
    new GUIBWDemoPlay(localGUIRoot);
    new GUIDGenNew(localGUIRoot);
    new GUIDGenBriefing(localGUIRoot);
    new GUIDGenMission(localGUIRoot);
    new GUIDGenDeBriefing(localGUIRoot);
    new GUIDGenRoster(localGUIRoot);
    new GUIDGenDocs(localGUIRoot);
    new GUIDGenPilot(localGUIRoot);
    new GUIDGenPilotDetail(localGUIRoot);
    new GUINetServerNGenSelect(localGUIRoot);
    new GUINetServerNGenProp(localGUIRoot);
    chatDlg = new GUIChatDialog(localGUIRoot);
    chatDlg.hideWindow();

    Main3D.cur3D().beginStep(90);

    localGUIRoot.lAF().bSoundEnable = true;

    Main3D.cur3D().enableOnlyHotKeyCmdEnv(envName);
    GUIMainMenu localGUIMainMenu2 = (GUIMainMenu)GameState.get(2);
    localGUIMainMenu2.pPilotName.cap = new GCaption(World.cur().userCfg.name + " '" + World.cur().userCfg.callsign + "' " + World.cur().userCfg.surname);

    Main.stateStack().push(localGUIMainMenu1);

    localGUIRoot.C.alpha = 224;

    return localGUIWindowManager;
  }
}