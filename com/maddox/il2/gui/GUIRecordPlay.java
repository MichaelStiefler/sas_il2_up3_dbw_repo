package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.engine.hotkey.HookView;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
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

public class GUIRecordPlay extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton bVideo;
  public GUIButton b3d;
  public GUIButton bSound;
  public GUIButton bExit;
  public GUIButton bBack;
  public GUISwitchBox2 sTimeCompression;
  public GUISwitchBox2 sViewControls;
  public GUISwitchBox2 sViewMessages;
  private boolean bDemoChanges = false;
  private GWindowMessageBox loadMessageBox;
  private String selectedFileName;
  private String _replayRecordFile;

  public void enterPush(GameState paramGameState)
  {
    _enter();
    this.bDemoChanges = ((this.sTimeCompression.isChecked()) || (this.sViewControls.isChecked()));
  }
  public void enterPop(GameState paramGameState) {
    this.client.activateWindow();
  }

  public void _enter() {
    GUIRecordSelect localGUIRecordSelect = (GUIRecordSelect)GameState.get(7);
    this.sTimeCompression.showWindow();
    this.sTimeCompression.setChecked(localGUIRecordSelect.bManualTimeCompression, false);
    this.sViewControls.setChecked(localGUIRecordSelect.bManualViewControls, false);
    this.sViewMessages.setChecked(localGUIRecordSelect.bDrawAllMessages, false);
    this.loadMessageBox = new GWindowMessageBox(Main3D.cur3D().guiManager.jdField_root_of_type_ComMaddoxGwindowGWindowRoot, 20.0F, true, i18n("record.StandBy"), i18n("record.LoadingTrack"), 5, 0.0F)
    {
      public void result(int paramInt)
      {
        if (paramInt == 1)
          BackgroundTask.cancel(GUIRecordPlay.this.i18n("miss.UserCancel"));
      }
    };
    new MsgAction(72, 0.0D) {
      public void doAction() { Main.closeAllNetChannels();
        GUIRecordSelect localGUIRecordSelect = (GUIRecordSelect)GameState.get(7);
        GUIRecordPlay.MissionListener localMissionListener = new GUIRecordPlay.MissionListener(GUIRecordPlay.this);
        GUIRecordPlay.access$002(GUIRecordPlay.this, localGUIRecordSelect.selectedFile);
        String str = Main3D.cur3D().playRecordedMission("Records/" + localGUIRecordSelect.selectedFile);
        if (str != null) {
          BackgroundTask.removeListener(localMissionListener);
          GUIRecordPlay.this.recordBad(str);
        } else if (Main3D.cur3D().playRecordedStreams() != null) {
          Main.cur().netMissionListener = localMissionListener;
        } } } ;
  }

  public void doReplayMission(String paramString, boolean paramBoolean) {
    GUI.activate();
    GUIRecordSelect localGUIRecordSelect = (GUIRecordSelect)GameState.get(7);
    if ((!localGUIRecordSelect.bCycle) && (paramBoolean)) {
      doExit(true);
      return;
    }
    if (NetMissionTrack.isRecording()) {
      NetMissionTrack.stopRecording();
      GUI.activate();
    }
    if (Main.cur().netServerParams != null)
      Main.cur().netServerParams.destroy();
    if (RTSConf.cur.netEnv.control != null)
      RTSConf.cur.netEnv.control.destroy();
    this.loadMessageBox = new GWindowMessageBox(Main3D.cur3D().guiManager.jdField_root_of_type_ComMaddoxGwindowGWindowRoot, 20.0F, true, i18n("record.StandBy"), i18n("record.LoadingTrack"), 5, 0.0F)
    {
      public void result(int paramInt)
      {
        if (paramInt == 1)
          BackgroundTask.cancel(GUIRecordPlay.this.i18n("miss.UserCancel"));
      }
    };
    this._replayRecordFile = paramString;
    new MsgAction(72, 0.0D) {
      public void doAction() { Main.closeAllNetChannels();
        GUIRecordPlay.MissionListener localMissionListener = new GUIRecordPlay.MissionListener(GUIRecordPlay.this);
        String str = Main3D.cur3D().playRecordedMission(GUIRecordPlay.this._replayRecordFile, false);
        if (str != null) {
          BackgroundTask.removeListener(localMissionListener);
          GUIRecordPlay.this.recordBad(str);
        } else if (Main3D.cur3D().playRecordedStreams() != null) {
          Main.cur().netMissionListener = localMissionListener;
        }
      }
    };
  }

  private void doExit()
  {
    doExit(false);
  }
  private void doExit(boolean paramBoolean) {
    int i = Main3D.cur3D().playRecordedStreams() != null ? 1 : 0;
    if (NetMissionTrack.isRecording()) {
      NetMissionTrack.stopRecording();
      GUI.activate();
    }
    Main.cur().netMissionListener = null;
    Main3D.cur3D().stopPlayRecordedMission();
    if ((Mission.cur() != null) && (!Mission.cur().isDestroyed())) {
      CmdEnv.top().exec("mission END");
    }
    if (Main.cur().netServerParams != null)
      Main.cur().netServerParams.destroy();
    if (RTSConf.cur.netEnv.control != null) {
      RTSConf.cur.netEnv.control.destroy();
    }
    if ((this.bDemoChanges) && (NetMissionTrack.countRecorded == 0) && (i == 0)) {
      Main.stateStack().change(9);
    } else {
      HookView.loadConfig();
      Main.stateStack().pop();
    }
  }

  private void recordBad(String paramString) {
    this.loadMessageBox.close(false);
    new GWindowMessageBox(Main3D.cur3D().guiManager.jdField_root_of_type_ComMaddoxGwindowGWindowRoot, 20.0F, true, i18n("record.Error"), paramString, 3, 0.0F)
    {
      public void result(int paramInt) {
        GUIRecordPlay.access$702(GUIRecordPlay.this, false);
        GUIRecordPlay.this.doExit();
      } } ;
  }

  private void gameBeginNet() {
    GUIWindowManager localGUIWindowManager = Main3D.cur3D().guiManager;
    this.loadMessageBox.close(false);
    GUI.unActivate();
    RTSConf.cur.time.setEnableChangePause1(true);
    RTSConf.cur.time.setEnableChangeSpeed(true);
    GUI.chatDlg.showWindow();
    if (Mission.isNet())
      AudioDevice.soundsOn();
  }

  private void gameBeginSingle() {
    new MsgAction(72, 0.0D) {
      public void doAction() { GUIWindowManager localGUIWindowManager = Main3D.cur3D().guiManager;
        GUIRecordPlay.this.loadMessageBox.close(false);
        localGUIWindowManager.setTimeGameActive(false);
        GUI.unActivate();
        CmdEnv.top().exec("mission BEGIN");
        GUIRecordPlay.this.gameBeginEnvs(); } } ;
  }

  private void gameBeginEnvs() {
    GUIRecordSelect localGUIRecordSelect = (GUIRecordSelect)GameState.get(7);
    HotKeyEnv.enable("timeCompression", localGUIRecordSelect.bManualTimeCompression);
    HotKeyEnv.enable("aircraftView", localGUIRecordSelect.bManualViewControls);
    HotKeyEnv.enable("HookView", localGUIRecordSelect.bManualViewControls);
    HotKeyEnv.enable("PanView", localGUIRecordSelect.bManualViewControls);
    HotKeyEnv.enable("SnapView", localGUIRecordSelect.bManualViewControls);
    Main3D.cur3D().keyRecord.setEnablePlayArgs(!localGUIRecordSelect.bManualViewControls);
  }

  public void _leave() {
    this.client.hideWindow();
  }

  public void doQuitMission() {
    if (Main3D.cur3D().playRecordedStreams() != null)
      GUI.chatDlg.hideWindow();
    GUI.activate();

    if (Time.isEnableChangeSpeed()) this.sTimeCompression.showWindow(); else
      this.sTimeCompression.hideWindow();
    this.client.activateWindow();
  }

  public GUIRecordPlay(GWindowRoot paramGWindowRoot)
  {
    super(8);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("record.infoPlay");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.bVideo = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.b3d = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bSound = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bBack = ((GUIButton)this.dialogClient.addDefault(new GUIButton(this.dialogClient, localGTexture, 0.0F, 192.0F, 48.0F, 48.0F)));
    this.bExit = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.sTimeCompression = ((GUISwitchBox2)this.dialogClient.addControl(new GUISwitchBox2(this.dialogClient)));
    this.sViewControls = ((GUISwitchBox2)this.dialogClient.addControl(new GUISwitchBox2(this.dialogClient)));
    this.sViewMessages = ((GUISwitchBox2)this.dialogClient.addControl(new GUISwitchBox2(this.dialogClient)));

    this.dialogClient.activateWindow();
    this.client.hideWindow();
  }

  static {
    Spawn.get("com.maddox.il2.fm.FlightModelTrack");
    Spawn.get("com.maddox.il2.game.GameTrack");
  }

  public class DialogClient extends GUIDialogClient
  {
    public DialogClient()
    {
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);

      if (paramGWindow == GUIRecordPlay.this.bVideo) {
        Main.stateStack().push(12);
        return true;
      }
      if (paramGWindow == GUIRecordPlay.this.b3d) {
        Main.stateStack().push(11);
        return true;
      }
      if (paramGWindow == GUIRecordPlay.this.bSound) {
        Main.stateStack().push(13);
        return true;
      }
      if (paramGWindow == GUIRecordPlay.this.sTimeCompression) {
        GUIRecordPlay.access$702(GUIRecordPlay.this, true);
        return true;
      }if (paramGWindow == GUIRecordPlay.this.sViewControls) {
        GUIRecordPlay.access$702(GUIRecordPlay.this, true);
        return true;
      }
      GUIRecordSelect localGUIRecordSelect;
      if (paramGWindow == GUIRecordPlay.this.sViewMessages) {
        Main3D.cur3D().hud.bDrawAllMessages = GUIRecordPlay.this.sViewMessages.isChecked();
        localGUIRecordSelect = (GUIRecordSelect)GameState.get(7);
        localGUIRecordSelect.bDrawAllMessages = GUIRecordPlay.this.sViewMessages.isChecked();
        return true;
      }
      if (paramGWindow == GUIRecordPlay.this.bExit) {
        GUIRecordPlay.this.doExit();
        return true;
      }
      if (paramGWindow == GUIRecordPlay.this.bBack) {
        localGUIRecordSelect = (GUIRecordSelect)GameState.get(7);
        localGUIRecordSelect.bManualTimeCompression = GUIRecordPlay.this.sTimeCompression.isChecked();
        localGUIRecordSelect.bManualViewControls = GUIRecordPlay.this.sViewControls.isChecked();
        HotKeyEnv.enable("timeCompression", localGUIRecordSelect.bManualTimeCompression);
        HotKeyEnv.enable("aircraftView", localGUIRecordSelect.bManualViewControls);
        HotKeyEnv.enable("HookView", localGUIRecordSelect.bManualViewControls);
        HotKeyEnv.enable("PanView", localGUIRecordSelect.bManualViewControls);
        HotKeyEnv.enable("SnapView", localGUIRecordSelect.bManualViewControls);
        Main3D.cur3D().keyRecord.setEnablePlayArgs(!localGUIRecordSelect.bManualViewControls);

        GUIRecordPlay.this.client.hideWindow();
        GUI.unActivate();
        if (Main3D.cur3D().playRecordedStreams() != null)
          GUI.chatDlg.showWindow();
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(240.0F), x1024(320.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(512.0F), x1024(320.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(624.0F), x1024(320.0F), 2.0F);
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(96.0F), y1024(32.0F), x1024(224.0F), y1024(48.0F), 0, GUIRecordPlay.this.i18n("record.VideoModes"));
      draw(x1024(96.0F), y1024(96.0F), x1024(224.0F), y1024(48.0F), 0, GUIRecordPlay.this.i18n("record.VideoOptions"));
      draw(x1024(96.0F), y1024(160.0F), x1024(224.0F), y1024(48.0F), 0, GUIRecordPlay.this.i18n("record.SoundSetup"));
      if (RTSConf.cur.time.isEnableChangePause1())
        draw(x1024(96.0F), y1024(272.0F), x1024(224.0F), y1024(48.0F), 0, GUIRecordPlay.this.i18n("record.ManualTime"));
      draw(x1024(96.0F), y1024(352.0F), x1024(224.0F), y1024(48.0F), 0, GUIRecordPlay.this.i18n("record.ManualView"));
      draw(x1024(96.0F), y1024(432.0F), x1024(224.0F), y1024(48.0F), 0, GUIRecordPlay.this.i18n("record.InflightMessages"));
      draw(x1024(96.0F), y1024(544.0F), x1024(224.0F), y1024(48.0F), 0, GUIRecordPlay.this.i18n("record.Return2track"));
      draw(x1024(96.0F), y1024(656.0F), x1024(224.0F), y1024(48.0F), 0, GUIRecordPlay.this.i18n("record.Stop"));
    }

    public void setPosSize()
    {
      set1024PosSize(334.0F, 32.0F, 384.0F, 736.0F);

      GUIRecordPlay.this.bVideo.setPosC(x1024(56.0F), y1024(56.0F));
      GUIRecordPlay.this.b3d.setPosC(x1024(56.0F), y1024(120.0F));
      GUIRecordPlay.this.bSound.setPosC(x1024(56.0F), y1024(184.0F));
      GUIRecordPlay.this.sTimeCompression.setPosC(x1024(64.0F), y1024(296.0F));
      GUIRecordPlay.this.sViewControls.setPosC(x1024(64.0F), y1024(376.0F));
      GUIRecordPlay.this.sViewMessages.setPosC(x1024(64.0F), y1024(458.0F));
      GUIRecordPlay.this.bBack.setPosC(x1024(56.0F), y1024(568.0F));
      GUIRecordPlay.this.bExit.setPosC(x1024(56.0F), y1024(682.0F));
    }
  }

  class MissionListener
    implements NetMissionListener, MsgBackgroundTaskListener
  {
    public void netMissionState(int paramInt, float paramFloat, String paramString)
    {
      switch (paramInt) {
      case 6:
        GUIRecordPlay.this.gameBeginEnvs();
        break;
      case 9:
        GUIRecordPlay.this.gameBeginNet();
        break;
      case 7:
      case 8:
        BackgroundTask.removeListener(this);
        break;
      }
    }
    public void netMissionCoopEnter() {
    }

    public void msgBackgroundTaskStarted(BackgroundTask paramBackgroundTask) {
    }

    public void msgBackgroundTaskStep(BackgroundTask paramBackgroundTask) {
      GUIRecordPlay.this.loadMessageBox.message = ((int)paramBackgroundTask.percentComplete() + "% " + GUIRecordPlay.this.i18n(paramBackgroundTask.messageComplete()));
    }

    public void msgBackgroundTaskStoped(BackgroundTask paramBackgroundTask)
    {
      BackgroundTask.removeListener(this);
      if (paramBackgroundTask.isComplete()) {
        if (Main3D.cur3D().playRecordedStreams() == null) {
          String str = Main3D.cur3D().startPlayRecordedMission();
          if (str == null)
            GUIRecordPlay.this.gameBeginSingle();
          else
            GUIRecordPlay.this.recordBad(GUIRecordPlay.this.i18n("miss.LoadBad") + " " + str);
        }
      }
      else
        GUIRecordPlay.this.recordBad(GUIRecordPlay.this.i18n("miss.LoadBad") + " " + paramBackgroundTask.messageCancel());
    }

    public MissionListener() {
      BackgroundTask.addListener(this);
    }
  }
}