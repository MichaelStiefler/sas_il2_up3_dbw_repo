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
import com.maddox.il2.objects.air.Aircraft;
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

public class GUIBWDemoPlay extends GameState
  implements MsgTimeOutListener
{
  public static String demoFile = "Intros/Intro";
  public static String soundFile = "music/intro/intro";
  public GUIClient client;
  public GUIDialogClient dialogClient;
  private GWindowMessageBox loadMessageBox;
  private SectFile msgFile;
  private int msgSect;
  private int msgRecord;
  private boolean bDrawAllMessages = true;
  private boolean flagTEX_VERTEX_ARRAYS;
  private boolean flagTEX_SECONDARY_COLOR_EXT;
  private int intHardwareShaders;
  private boolean bArcade = false;
  private boolean bEnableVoice = true;

  private boolean[] flagMusic = new boolean[3];

  private int flagNum(int paramInt) {
    for (int i = 0; i < 32; i++) {
      if ((1 << i & paramInt) == paramInt)
        return i;
    }
    return 0;
  }

  public void msgTimeOut(Object paramObject) {
    if ((paramObject != this.msgFile) || (paramObject == null)) return;
    if (this.msgRecord >= 0) {
      str = this.msgFile.value(this.msgSect, this.msgRecord);
      if (str != null)
        str = UnicodeTo8bit.load(str);
      HUD.intro(str);
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

  public void _enter() {
    this.loadMessageBox = new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, i18n("record.StandBy"), i18n("record.LoadingTrack"), 5, 0.0F)
    {
      boolean _bStart = true;
      boolean _bStarting = false;
      boolean _bCancel = false;

      public void result(int paramInt) { if (paramInt == 1) {
          this._bCancel = true;
          if (this._bStarting)
            BackgroundTask.cancel(GUIBWDemoPlay.this.i18n("miss.UserCancel"));
        } }

      public void preRender() {
        super.preRender();
        if (this._bStart) {
          this._bStart = false;
          new MsgAction(72, 0.0D) {
            public void doAction() { if (GUIBWDemoPlay.1.this._bCancel) return;
              GUIBWDemoPlay.1.this._bStarting = true;
              GUIBWDemoPlay.this.loadMessageBox.showModal();
              Main.closeAllNetChannels();
              GUIBWDemoPlay.MissionListener localMissionListener = new GUIBWDemoPlay.MissionListener(GUIBWDemoPlay.this);

              Mat.setGrayScaleLoading(true);
              Provider.setEnableBW(true);
              for (int i = 0; i < 3; i++) {
                Main3D.cur3D()._cinema[i].setShow(true);
                Main3D.cur3D()._cinema[i].resetGame();
                Main3D.cur3D()._lightsGlare[i].enterBWmode();
                Main3D.cur3D()._sunGlare[i].enterBWmode();
              }
              GUIBWDemoPlay.access$202(GUIBWDemoPlay.this, Main3D.cur3D().hud.bDrawAllMessages);
              Main3D.cur3D().hud.bDrawAllMessages = false;
              GUIBWDemoPlay.access$302(GUIBWDemoPlay.this, World.cur().isArcade());
              World.cur().setArcade(false);
              GUIBWDemoPlay.access$402(GUIBWDemoPlay.this, Voice.isEnableVoices());
              Voice.setEnableVoices(false);
              GUIBWDemoPlay.access$502(GUIBWDemoPlay.this, RenderContext.cfgTexFlags.get(GUIBWDemoPlay.this.flagNum(512)));
              GUIBWDemoPlay.access$702(GUIBWDemoPlay.this, RenderContext.cfgTexFlags.get(GUIBWDemoPlay.this.flagNum(16384)));
              RenderContext.cfgTexFlags.set(GUIBWDemoPlay.this.flagNum(512), false);
              RenderContext.cfgTexFlags.set(GUIBWDemoPlay.this.flagNum(16384), false);
              i = RenderContext.cfgTexFlags.apply();
              GUIBWDemoPlay.access$802(GUIBWDemoPlay.this, RenderContext.cfgHardwareShaders.get());
              RenderContext.cfgHardwareShaders.set(0);
              i |= RenderContext.cfgHardwareShaders.apply();
              Engine.land().UnLoadMap();

              CfgFlags localCfgFlags = (CfgFlags)CfgTools.get("MusState");
              for (int j = 0; j < GUIBWDemoPlay.this.flagMusic.length; j++) {
                GUIBWDemoPlay.this.flagMusic[j] = localCfgFlags.get(j);
                localCfgFlags.set(j, false);
              }
              localCfgFlags.apply();

              String str = Main3D.cur3D().playRecordedMission(GUIBWDemoPlay.demoFile);
              if (str != null) {
                BackgroundTask.removeListener(localMissionListener);
                GUIBWDemoPlay.this.recordBad(str);
              } else if (Main3D.cur3D().playRecordedStreams() != null) {
                Main.cur().netMissionListener = localMissionListener;
              } } } ;
        }
      } } ;
  }

  public void _leave() {
    this.msgFile = null;
    HUD.intro(null);
    HUD.introESC(null);
    this.client.hideWindow();
    Provider.setEnableBW(false);
    Mat.setGrayScaleLoading(false);
    for (int i = 0; i < 3; i++) {
      Main3D.cur3D()._cinema[i].setShow(false);
      Main3D.cur3D()._lightsGlare[i].leaveBWmode();
      Main3D.cur3D()._sunGlare[i].leaveBWmode();
    }
    Main3D.cur3D().hud.bDrawAllMessages = this.bDrawAllMessages;
    World.cur().setArcade(this.bArcade);
    Voice.setEnableVoices(this.bEnableVoice);
    Main3D.cur3D().viewSet_Load();
    RenderContext.cfgTexFlags.set(flagNum(512), this.flagTEX_VERTEX_ARRAYS);
    RenderContext.cfgTexFlags.set(flagNum(16384), this.flagTEX_SECONDARY_COLOR_EXT);
    i = RenderContext.cfgTexFlags.apply();
    RenderContext.cfgHardwareShaders.set(this.intHardwareShaders);
    i |= RenderContext.cfgHardwareShaders.apply();
    Engine.land().UnLoadMap();

    CfgFlags localCfgFlags = (CfgFlags)CfgTools.get("MusState");
    for (int j = 0; j < this.flagMusic.length; j++) {
      localCfgFlags.set(j, this.flagMusic[j]);
    }
    localCfgFlags.apply();

    Main3D.menuMusicPlay();
  }

  public void doExit()
  {
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

  private void recordBad(String paramString) {
    this.loadMessageBox.close(false);
    Main3D.cur3D().stopPlayRecordedMission();
    doExit();
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
    gameBegin();
  }

  private void gameBeginSingle() {
    new MsgAction(72, 0.0D) {
      public void doAction() { GUIWindowManager localGUIWindowManager = Main3D.cur3D().guiManager;
        GUIBWDemoPlay.this.loadMessageBox.close(false);
        localGUIWindowManager.setTimeGameActive(false);
        GUI.unActivate();
        CmdEnv.top().exec("mission BEGIN");
        Aircraft localAircraft = World.getPlayerAircraft();
        if (Actor.isValid(localAircraft))
        {
          GUIBWDemoPlay.this.gameBeginEnvs();
        }
        GUIBWDemoPlay.this.gameBegin(); } } ;
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
    this.msgRecord = -1;
    this.msgFile = null;
    String str = Locale.getDefault().getLanguage();
    if (!"us".equals(str)) {
      this.msgFile = new SectFile(demoFile + "_" + str + ".msg");
      this.msgSect = this.msgFile.sectionIndex("all");
      if (this.msgSect < 0)
        this.msgFile = null;
    }
    if (this.msgFile == null)
      this.msgFile = new SectFile(demoFile + ".msg");
    this.msgSect = this.msgFile.sectionIndex("all");
    if (this.msgSect < 0) {
      this.msgFile = null;
    }
    msgTimeOut(this.msgFile);

    if (soundFile != null)
      CmdEnv.top().exec("music FILE " + soundFile);
    HUD.introESC(I18N.gui("intro.msgESC"));
  }

  public void doQuitMission() {
    if (Main3D.cur3D().playRecordedStreams() != null)
      GUI.chatDlg.hideWindow();
    GUI.activate();
    this.client.activateWindow();
    doExit();
  }

  public GUIBWDemoPlay(GWindowRoot paramGWindowRoot) {
    super(58);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((GUIDialogClient)this.client.create(new GUIDialogClient()));
    this.dialogClient.activateWindow();
    this.client.hideWindow();
  }

  class MissionListener
    implements NetMissionListener, MsgBackgroundTaskListener
  {
    public void netMissionState(int paramInt, float paramFloat, String paramString)
    {
      switch (paramInt) {
      case 6:
        GUIBWDemoPlay.this.gameBeginEnvs();
        break;
      case 9:
        GUIBWDemoPlay.this.gameBeginNet();
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
      GUIBWDemoPlay.this.loadMessageBox.message = ((int)paramBackgroundTask.percentComplete() + "% " + GUIBWDemoPlay.this.i18n(paramBackgroundTask.messageComplete()));
    }

    public void msgBackgroundTaskStoped(BackgroundTask paramBackgroundTask)
    {
      BackgroundTask.removeListener(this);
      if (paramBackgroundTask.isComplete()) {
        if (Main3D.cur3D().playRecordedStreams() == null) {
          String str = Main3D.cur3D().startPlayRecordedMission();
          if (str == null)
            GUIBWDemoPlay.this.gameBeginSingle();
          else
            GUIBWDemoPlay.this.recordBad(GUIBWDemoPlay.this.i18n("miss.LoadBad") + " " + str);
        }
      }
      else GUIBWDemoPlay.this.recordBad(GUIBWDemoPlay.this.i18n("miss.LoadBad") + " " + paramBackgroundTask.messageCancel()); 
    }

    public MissionListener()
    {
      BackgroundTask.addListener(this);
    }
  }
}