package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
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

public class GUITrainingPlay extends GameState
  implements MsgTimeOutListener
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton bVideo;
  public GUIButton b3d;
  public GUIButton bSound;
  public GUIButton bControls;
  public GUIButton bFly;
  public GUIButton bExit;
  public GUIButton bBack;
  private GWindowMessageBox loadMessageBox;
  private SectFile msgFile;
  private int msgSect;
  private int msgRecord;
  private boolean bArcade = false;

  public boolean bFreeFly = false;

  public void msgTimeOut(Object paramObject) {
    if ((paramObject != this.msgFile) || (paramObject == null)) return;
    if (this.msgSect < 0) return;
    if (this.msgRecord >= 0) {
      str = this.msgFile.value(this.msgSect, this.msgRecord);
      if (str != null)
        str = UnicodeTo8bit.load(str);
      HUD.training(str);
    }
    this.msgRecord += 1;
    if (this.msgRecord >= this.msgFile.vars(this.msgSect)) return;
    String str = this.msgFile.var(this.msgSect, this.msgRecord);
    int i = str.indexOf(":");
    int j = 0;
    if (i > 0)
      try {
        j = Integer.parseInt(str.substring(0, i)) * 60;
        j += Integer.parseInt(str.substring(i + 1));
      } catch (Exception localException1) {
        System.err.println("Bad format messages file: " + this.msgFile.fileName());
        return;
      }
    else {
      try {
        j = Integer.parseInt(str);
      } catch (Exception localException2) {
        System.err.println("Bad format messages file: " + this.msgFile.fileName());
        return;
      }
    }
    long l = j * 1000L;
    if (NetMissionTrack.isPlaying())
      l += NetMissionTrack.playingStartTime;
    MsgTimeOut.post(0, l, this, this.msgFile);
  }

  public void enterPush(GameState paramGameState) {
    this.bFreeFly = false;
    this.bFly.showWindow();
    this.bArcade = World.cur().isArcade();
    World.cur().setArcade(false);
    _enter();
  }
  public void enterPop(GameState paramGameState) {
    this.client.activateWindow();
  }

  public void _enter() {
    Main3D.cur3D().hud.bDrawVoiceMessages = false;
    this.loadMessageBox = new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, i18n("training.StandBy"), i18n("training.LoadingTrack"), 5, 0.0F)
    {
      public void result(int paramInt)
      {
        if (paramInt == 1)
          BackgroundTask.cancel(GUITrainingPlay.this.i18n("miss.UserCancel"));
      }
    };
    new MsgAction(72, 0.0D) {
      public void doAction() { Main.closeAllNetChannels();
        GUITrainingSelect localGUITrainingSelect = (GUITrainingSelect)GameState.get(56);
        GUITrainingPlay.MissionListener localMissionListener = new GUITrainingPlay.MissionListener(GUITrainingPlay.this);
        String str = Main3D.cur3D().playRecordedMission("Training/" + localGUITrainingSelect.selectedTrack);
        if (str != null) {
          BackgroundTask.removeListener(localMissionListener);
          GUITrainingPlay.this.recordBad(str);
        } else if (Main3D.cur3D().playRecordedStreams() != null) {
          Main.cur().netMissionListener = localMissionListener;
        } } } ;
  }

  public void leavePop(GameState paramGameState) {
    World.cur().setArcade(this.bArcade);
    _leave();
  }

  public void doExit()
  {
    this.msgFile = null;
    HUD.training(null);
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
    if (RTSConf.cur.netEnv.control != null)
      RTSConf.cur.netEnv.control.destroy();
    HookView.loadConfig();
    Main.stateStack().pop();
  }
  public void doFly() {
    this.bFreeFly = true;
    this.bFly.hideWindow();
    this.msgFile = null;
    HUD.training(null);
    this.client.hideWindow();
    GUI.unActivate();
    RTSConf.cur.hotKeyCmdEnvs.endAllCmd();
    Main3D.cur3D().flyRecordedMission();
  }

  private void recordBad(String paramString) {
    this.loadMessageBox.close(false);
    new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, i18n("training.Error"), paramString, 3, 0.0F)
    {
      public void result(int paramInt) {
        GUITrainingPlay.this.doExit();
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
    this.bFly.hideWindow();
    gameBegin();
  }

  private void gameBeginSingle() {
    new MsgAction(72, 0.0D) {
      public void doAction() { GUIWindowManager localGUIWindowManager = Main3D.cur3D().guiManager;
        GUITrainingPlay.this.loadMessageBox.close(false);
        localGUIWindowManager.setTimeGameActive(false);
        GUI.unActivate();
        CmdEnv.top().exec("mission BEGIN");
        GUITrainingPlay.this.bFly.showWindow();
        GUITrainingPlay.this.gameBeginEnvs();
        GUITrainingPlay.this.gameBegin(); } } ;
  }

  private void gameBeginEnvs() {
    HotKeyEnv.enable("timeCompression", false);
    HotKeyEnv.enable("aircraftView", false);
    HotKeyEnv.enable("HookView", false);
    HotKeyEnv.enable("PanView", false);
    HotKeyEnv.enable("SnapView", false);
    Main3D.cur3D().keyRecord.setEnablePlayArgs(true);
  }

  private void gameBegin() {
    GUITrainingSelect localGUITrainingSelect = (GUITrainingSelect)GameState.get(56);
    this.msgRecord = -1;
    this.msgFile = null;
    String str = Locale.getDefault().getLanguage();
    if (!"us".equals(str)) {
      this.msgFile = new SectFile("Training/" + localGUITrainingSelect.selectedTrack + "_" + str + ".msg");
      this.msgSect = this.msgFile.sectionIndex("all");
      if (this.msgSect < 0)
        this.msgFile = null;
    }
    if (this.msgFile == null)
      this.msgFile = new SectFile("Training/" + localGUITrainingSelect.selectedTrack + ".msg");
    this.msgSect = this.msgFile.sectionIndex("all");
    if (this.msgSect < 0) {
      this.msgFile = null;
    }
    msgTimeOut(this.msgFile);
  }

  public void _leave() {
    Main3D.cur3D().hud.bDrawVoiceMessages = true;
    this.client.hideWindow();
  }

  public void doQuitMission() {
    if (Main3D.cur3D().playRecordedStreams() != null)
      GUI.chatDlg.hideWindow();
    GUI.activate();
    this.client.activateWindow();
  }

  public GUITrainingPlay(GWindowRoot paramGWindowRoot)
  {
    super(57);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("training.infoPlay");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.bVideo = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.b3d = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bSound = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bControls = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bFly = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bBack = ((GUIButton)this.dialogClient.addDefault(new GUIButton(this.dialogClient, localGTexture, 0.0F, 192.0F, 48.0F, 48.0F)));
    this.bExit = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));

    this.dialogClient.activateWindow();
    this.client.hideWindow();
  }

  public class DialogClient extends GUIDialogClient
  {
    public DialogClient()
    {
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);

      if (paramGWindow == GUITrainingPlay.this.bVideo) {
        Main.stateStack().push(12);
        return true;
      }
      if (paramGWindow == GUITrainingPlay.this.b3d) {
        Main.stateStack().push(11);
        return true;
      }
      if (paramGWindow == GUITrainingPlay.this.bSound) {
        Main.stateStack().push(13);
        return true;
      }
      if (paramGWindow == GUITrainingPlay.this.bControls) {
        Main.stateStack().push(20);
        return true;
      }
      if (paramGWindow == GUITrainingPlay.this.bFly) {
        GUITrainingPlay.this.doFly();
        return true;
      }
      if (paramGWindow == GUITrainingPlay.this.bExit) {
        GUITrainingPlay.this.doExit();
        return true;
      }
      if (paramGWindow == GUITrainingPlay.this.bBack) {
        GUITrainingPlay.this.client.hideWindow();
        GUI.unActivate();
        if (Main3D.cur3D().playRecordedStreams() != null)
          GUI.chatDlg.showWindow();
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(304.0F), x1024(288.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(416.0F), x1024(288.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(528.0F), x1024(288.0F), 2.0F);
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(96.0F), y1024(32.0F), x1024(224.0F), y1024(48.0F), 0, GUITrainingPlay.this.i18n("training.VideoModes"));
      draw(x1024(96.0F), y1024(96.0F), x1024(224.0F), y1024(48.0F), 0, GUITrainingPlay.this.i18n("training.VideoOptions"));
      draw(x1024(96.0F), y1024(160.0F), x1024(224.0F), y1024(48.0F), 0, GUITrainingPlay.this.i18n("training.SoundSetup"));
      draw(x1024(96.0F), y1024(224.0F), x1024(224.0F), y1024(48.0F), 0, GUITrainingPlay.this.i18n("training.Controls"));
      if ((!GUITrainingPlay.this.bFreeFly) && (GUITrainingPlay.this.bFly.isVisible()))
        draw(x1024(96.0F), y1024(336.0F), x1024(224.0F), y1024(48.0F), 0, GUITrainingPlay.this.i18n("training.FreeFly"));
      draw(x1024(96.0F), y1024(448.0F), x1024(224.0F), y1024(48.0F), 0, GUITrainingPlay.this.i18n("training.Return2track"));
      draw(x1024(96.0F), y1024(560.0F), x1024(224.0F), y1024(48.0F), 0, GUITrainingPlay.this.i18n("training.Stop"));
    }

    public void setPosSize()
    {
      set1024PosSize(352.0F, 80.0F, 352.0F, 640.0F);

      GUITrainingPlay.this.bVideo.setPosC(x1024(56.0F), y1024(56.0F));
      GUITrainingPlay.this.b3d.setPosC(x1024(56.0F), y1024(120.0F));
      GUITrainingPlay.this.bSound.setPosC(x1024(56.0F), y1024(184.0F));
      GUITrainingPlay.this.bControls.setPosC(x1024(56.0F), y1024(248.0F));
      GUITrainingPlay.this.bFly.setPosC(x1024(56.0F), y1024(360.0F));
      GUITrainingPlay.this.bBack.setPosC(x1024(56.0F), y1024(472.0F));
      GUITrainingPlay.this.bExit.setPosC(x1024(56.0F), y1024(584.0F));
    }
  }

  class MissionListener
    implements NetMissionListener, MsgBackgroundTaskListener
  {
    public void netMissionState(int paramInt, float paramFloat, String paramString)
    {
      switch (paramInt) {
      case 6:
        GUITrainingPlay.this.gameBeginEnvs();
        break;
      case 9:
        GUITrainingPlay.this.gameBeginNet();
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
      GUITrainingPlay.this.loadMessageBox.message = ((int)paramBackgroundTask.percentComplete() + "% " + GUITrainingPlay.this.i18n(paramBackgroundTask.messageComplete()));
    }

    public void msgBackgroundTaskStoped(BackgroundTask paramBackgroundTask)
    {
      BackgroundTask.removeListener(this);
      if (paramBackgroundTask.isComplete()) {
        if (Main3D.cur3D().playRecordedStreams() == null) {
          String str = Main3D.cur3D().startPlayRecordedMission();
          if (str == null)
            GUITrainingPlay.this.gameBeginSingle();
          else
            GUITrainingPlay.this.recordBad(GUITrainingPlay.this.i18n("miss.LoadBad") + " " + str);
        }
      }
      else GUITrainingPlay.this.recordBad(GUITrainingPlay.this.i18n("miss.LoadBad") + " " + paramBackgroundTask.messageCancel()); 
    }

    public MissionListener()
    {
      BackgroundTask.addListener(this);
    }
  }
}