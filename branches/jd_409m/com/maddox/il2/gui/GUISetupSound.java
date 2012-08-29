package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.rts.CfgFlags;
import com.maddox.rts.CfgInt;
import com.maddox.rts.CfgTools;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.LDRres;
import com.maddox.rts.RTSConf;
import com.maddox.rts.VK;
import com.maddox.sound.AudioDevice;
import com.maddox.sound.CfgNpFlags;
import com.maddox.sound.RadioChannel;
import java.util.ResourceBundle;

public class GUISetupSound extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  private static final int GENERAL = 0;
  private static final int AUDIO = 1;
  private static final int MUSIC = 2;
  private static final int RADIO = 3;
  private int page = 0;
  public GUIButton bGeneral;
  public GUIButton bAudio;
  public GUIButton bMusic;
  public GUIButton bRadio;
  public GUIButton bBack;
  public GUIPocket pGeneral;
  public GUISwitchN sMasterVolume;
  public GUISwitchN sObjectsVolume;
  public GUISwitchN sMusicVolume;
  public GUISwitchN sVoiceVolume;
  public GUISwitchBox2 sMusicOn;
  public GUIPocket pAudio;
  public GUIComboCfgInt cEngine;
  public GUIComboCfgInt cChannels;
  public GUIComboCfgInt cRate;
  public GUIComboCfgInt cType;
  public GUISwitchBox2 sHardware;
  public GUISwitchBox2 sReverseStereo;
  public GUIPocket pMusic;
  public GUISwitchBox2 sPlayListTakeOff;
  public GUISwitchBox2 sPlayListInflight;
  public GUISwitchBox2 sPlayListCrash;
  public GUIPocket pRadio;
  public GUISwitchBox2 sPhoneOn;
  public GUISwitchN sMicLevel;
  public GUISwitchBox2 sClicks;
  public GWindowComboControl cTransMode;
  public GUISwitchN sMicSens;
  public GUISwitchBox2 sTest;
  public GTexture texIndicator;
  public GTexRegion texFull;
  private boolean npRun = false;
  private RadioChannel testChannel = null;
  private RadioChannel prevChannel = null;

  public void _enter()
  {
    update();
    this.client.activateWindow();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  public void update() {
    this.sMasterVolume.update();
    this.sObjectsVolume.update();
    this.sMusicVolume.update();
    this.sVoiceVolume.update();
    this.sMusicOn.update();

    this.cEngine.update();
    this.cRate.update();
    this.cType.update();
    this.sHardware.update();
    this.sReverseStereo.update();

    this.sPlayListTakeOff.update();
    this.sPlayListInflight.update();
    this.sPlayListCrash.update();

    this.sPhoneOn.refresh();
    this.sMicLevel.update();
    this.sClicks.refresh();
    this.sMicSens.update();
    this.sTest.refresh();
  }

  private void set(GWindow paramGWindow, int paramInt) {
    if (this.page == paramInt) paramGWindow.showWindow(); else
      paramGWindow.hideWindow();
  }

  private void setPage(int paramInt) {
    endedPage();
    this.page = paramInt;
    beginingPage();

    set(this.pGeneral, 0);
    set(this.sMasterVolume, 0);
    set(this.sObjectsVolume, 0);
    set(this.sMusicVolume, 0);
    set(this.sVoiceVolume, 0);
    set(this.sMusicOn, 0);

    set(this.pAudio, 1);
    set(this.cEngine, 1);
    set(this.cChannels, 1);
    set(this.cRate, 1);
    set(this.cType, 1);
    set(this.sHardware, 1);
    set(this.sReverseStereo, 1);

    set(this.pMusic, 2);
    set(this.sPlayListTakeOff, 2);
    set(this.sPlayListInflight, 2);
    set(this.sPlayListCrash, 2);

    set(this.pRadio, 3);
    set(this.sPhoneOn, 3);
    set(this.sMicLevel, 3);
    set(this.sClicks, 3);
    set(this.cTransMode, 3);
    set(this.sMicSens, 3);
    set(this.sTest, 3);
  }

  private void hideShowTransMode() {
    if (this.page != 3) return;
    if (isTransMode_PressMuteKey())
      this.sMicSens.hideWindow();
    else
      this.sMicSens.showWindow();
  }

  private void drawU(GWindow paramGWindow, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
  {
    float f1 = paramGWindow.x1024(10.0F);
    float f2 = paramGWindow.y1024(16.0F);
    float f3 = paramGWindow.x1024(12.0F);
    if (paramInt1 > 8) paramInt1 = 8;
    if (paramInt1 > 0) {
      for (int i = 0; i < paramInt1; i++) {
        GColor localGColor1 = GColor.Green;
        if (i >= 6)
          localGColor1 = GColor.Yellow;
        GUISeparate.draw(paramGWindow, localGColor1, paramFloat1 + i * f3, paramFloat2, f1, f2);
      }
    }
    if (paramInt2 > 5) paramInt2 = 5;
    if (paramInt2 > 0) {
      float f4 = 9.0F * f3;
      for (int j = 0; j < paramInt2; j++) {
        GColor localGColor2 = GColor.Yellow;
        if (j >= 1)
          localGColor2 = GColor.Red;
        GUISeparate.draw(paramGWindow, localGColor2, f4 + paramFloat1 + j * f3, paramFloat2, f1, f2);
      }
    }
  }

  private void drawInd(GWindow paramGWindow) {
    int i = AudioDevice.getRadioStatus();
    int j = Math.round(AudioDevice.getRadioLevel() / 100.0F * 8.0F);
    int k = Math.round(AudioDevice.getRadioOverflow() / 100.0F * 5.0F);
    int m = (AudioDevice.getRadioStatus() & 0x10) != 0 ? 1 : 0;

    paramGWindow.setCanvasColorWHITE();
    paramGWindow.draw(paramGWindow.x1024(736.0F), paramGWindow.y1024(212.0F), paramGWindow.x1024(48.0F), paramGWindow.y1024(48.0F), this.texIndicator, m != 0 ? 176.0F : 128.0F, 0.0F, 48.0F, 48.0F);
    paramGWindow.setCanvasColorWHITE();
    int n = (int)paramGWindow.x1024(540.0F);
    int i1 = (int)paramGWindow.y1024(228.0F);
    int i2 = (int)paramGWindow.x1024(12.0F) * 14;
    int i3 = (int)paramGWindow.y1024(16.0F);
    GTexture localGTexture = ((GUILookAndFeel)paramGWindow.lAF()).basicelements;
    GBevel localGBevel = ((GUILookAndFeel)paramGWindow.lAF()).bevelComboUp;
    paramGWindow.lAF().drawBevel(paramGWindow, n - localGBevel.L.dx, i1 - localGBevel.T.dy, i2 + localGBevel.L.dx + localGBevel.R.dx, i3 + localGBevel.T.dy + localGBevel.B.dy, localGBevel, localGTexture);

    drawU(paramGWindow, j, k, n, i1);
  }

  private void beginingPage()
  {
    switch (this.page) {
    case 3:
      this.cTransMode.setSelected(AudioDevice.getPTTMode() ? 1 : 0, true, false);
      hideShowTransMode();
      this.sPhoneOn.setChecked(AudioDevice.npFlags.get(0), false);
      this.sPhoneOn.setEnable(Main.cur().netServerParams == null);
      break;
    }
  }

  private void endedPage()
  {
    switch (this.page) {
    case 3:
      doSwitchTest(false);
      this.sTest.setChecked(false, false);
      break;
    }
  }

  private void doSwitchPhoneOn(boolean paramBoolean)
  {
    AudioDevice.npFlags.set(0, paramBoolean);
  }

  private void doSwitchTest(boolean paramBoolean)
  {
    if (paramBoolean) {
      if (!RadioChannel.tstLoop) {
        this.npRun = AudioDevice.isNetPhoneRunning();
        if (!this.npRun) AudioDevice.beginNetPhone();
        this.prevChannel = RadioChannel.activeChannel();
        if (this.prevChannel == null) {
          this.testChannel = new RadioChannel("$tst$", null, 1);
          this.testChannel.setActive(true);
        }
        RadioChannel.tstLoop = true;
        this.sPhoneOn.setEnable(false);
      }
    } else {
      if (RadioChannel.tstLoop) {
        if (this.testChannel != null) {
          this.testChannel.setActive(false);
          this.testChannel.destroy();
          this.testChannel = null;
        }
        if (this.prevChannel != null) this.prevChannel.setActive(true);
        if (!this.npRun) AudioDevice.endNetPhone();
        RadioChannel.tstLoop = false;
      }
      this.sPhoneOn.setEnable(true);
    }
  }

  private void doSwitchTransMode(boolean paramBoolean) {
    AudioDevice.npFlags.set(1, paramBoolean);
    AudioDevice.setPTTMode(paramBoolean);
  }

  private boolean isTransMode_PressMuteKey() {
    return this.cTransMode.getSelected() == 1;
  }

  public GUISetupSound(GWindowRoot paramGWindowRoot)
  {
    super(13);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));
    this.dialogClient.initResource();

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("sound.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;
    this.texFull = new GTexRegion("GUI/game/staticelements.mat", 192.0F, 160.0F, 64.0F, 64.0F);

    this.pGeneral = new GUIPocket(this.dialogClient, i18n("sound.General"));
    this.pAudio = new GUIPocket(this.dialogClient, i18n("sound.Audio"));
    this.pMusic = new GUIPocket(this.dialogClient, i18n("sound.Music"));
    this.pRadio = new GUIPocket(this.dialogClient, i18n("sound.Radio"));

    this.sMasterVolume = ((GUISwitchN)this.dialogClient.addControl(new GUISwitch16(this.dialogClient, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 }, (CfgInt)CfgTools.get("MasterVolume"), true)));

    this.sObjectsVolume = ((GUISwitchN)this.dialogClient.addControl(new GUISwitch16(this.dialogClient, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 }, (CfgInt)CfgTools.get("ObjectVolume"), true)));

    this.sMusicVolume = ((GUISwitchN)this.dialogClient.addControl(new GUISwitch16(this.dialogClient, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 }, (CfgInt)CfgTools.get("MusicVolume"), true)));

    this.sVoiceVolume = ((GUISwitchN)this.dialogClient.addControl(new GUISwitch16(this.dialogClient, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 }, (CfgInt)CfgTools.get("VoiceVolume"), true)));

    this.sMusicOn = ((GUISwitchBox2)this.dialogClient.addControl(new GUISwitchBox2(this.dialogClient, (CfgFlags)CfgTools.get("MusFlags"), 0, true)));

    this.cEngine = ((GUIComboCfgInt)this.dialogClient.addControl(new GUIComboCfgInt(this.dialogClient, (CfgInt)CfgTools.get("SoundEngine"), true, "sound.")));
    this.cChannels = ((GUIComboCfgInt)this.dialogClient.addControl(new GUIComboCfgInt(this.dialogClient, (CfgInt)CfgTools.get("NumChannels"), true, "sound.")));
    this.sHardware = ((GUISwitchBox2)this.dialogClient.addControl(new GUISwitchBox2(this.dialogClient, (CfgFlags)CfgTools.get("SoundFlags"), 1, true)));
    this.cType = ((GUIComboCfgInt)this.dialogClient.addControl(new GUIComboCfgInt(this.dialogClient, (CfgInt)CfgTools.get("Speakers"), true, "sound.")));
    this.cRate = ((GUIComboCfgInt)this.dialogClient.addControl(new GUIComboCfgInt(this.dialogClient, (CfgInt)CfgTools.get("SamplingRate"), true, "sound.")));
    this.sReverseStereo = ((GUISwitchBox2)this.dialogClient.addControl(new GUISwitchBox2(this.dialogClient, (CfgFlags)CfgTools.get("SoundFlags"), 0, true)));

    this.sPlayListTakeOff = ((GUISwitchBox2)this.dialogClient.addControl(new GUISwitchBox2(this.dialogClient, (CfgFlags)CfgTools.get("MusState"), 0, true)));
    this.sPlayListInflight = ((GUISwitchBox2)this.dialogClient.addControl(new GUISwitchBox2(this.dialogClient, (CfgFlags)CfgTools.get("MusState"), 1, true)));
    this.sPlayListCrash = ((GUISwitchBox2)this.dialogClient.addControl(new GUISwitchBox2(this.dialogClient, (CfgFlags)CfgTools.get("MusState"), 2, true)));

    this.sPhoneOn = ((GUISwitchBox2)this.dialogClient.addControl(new GUISwitchBox2(this.dialogClient, (CfgFlags)null, 0, true)));
    this.sMicLevel = ((GUISwitchN)this.dialogClient.addControl(new GUISwitch16(this.dialogClient, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 }, (CfgInt)CfgTools.get("MicLevel"), true)));

    this.sClicks = ((GUISwitchBox2)this.dialogClient.addControl(new GUISwitchBox2(this.dialogClient, (CfgFlags)CfgTools.get("RadioFlags"), 2, true)));
    this.cTransMode = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 0.0F, 0.0F, 1.0F)));
    this.cTransMode.add(i18n("sound._Sensitivity"));
    this.cTransMode.add(i18n("sound._MuteKey"));
    this.cTransMode.setEditable(false);
    this.cTransMode.setSelected(0, true, false);
    this.sMicSens = ((GUISwitchN)this.dialogClient.addControl(new GUISwitch16(this.dialogClient, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 }, (CfgInt)CfgTools.get("ActLevel"), true)));

    this.sTest = ((GUISwitchBox2)this.dialogClient.addControl(new GUISwitchBox2(this.dialogClient, (CfgFlags)null, 0, true)));
    this.texIndicator = GTexture.New("GUI/game/buttons.mat");

    setPage(0);

    this.bGeneral = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bAudio = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bMusic = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bRadio = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bBack = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));

    this.dialogClient.activateWindow();
    this.client.hideWindow();
  }

  public class DialogClient extends GUIDialogClient
  {
    ResourceBundle resource;

    public DialogClient()
    {
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);

      if (paramGWindow == GUISetupSound.this.bGeneral) {
        GUISetupSound.this.setPage(0);
        return true;
      }if (paramGWindow == GUISetupSound.this.bAudio) {
        GUISetupSound.this.setPage(1);
        return true;
      }if (paramGWindow == GUISetupSound.this.bMusic) {
        GUISetupSound.this.setPage(2);
        return true;
      }if (paramGWindow == GUISetupSound.this.bRadio) {
        GUISetupSound.this.setPage(3);
        return true;
      }if (paramGWindow == GUISetupSound.this.bBack) {
        GUISetupSound.this.endedPage();
        AudioDevice.applySettings();
        Main.stateStack().pop();
        return true;
      }
      if (paramGWindow == GUISetupSound.this.sPhoneOn) {
        GUISetupSound.this.doSwitchPhoneOn(GUISetupSound.this.sPhoneOn.isChecked());
        return true;
      }if (paramGWindow == GUISetupSound.this.sTest) {
        GUISetupSound.this.doSwitchTest(GUISetupSound.this.sTest.isChecked());
        return true;
      }if (paramGWindow == GUISetupSound.this.cTransMode) {
        GUISetupSound.this.doSwitchTransMode(GUISetupSound.this.cTransMode.getSelected() == 1);
        GUISetupSound.this.hideShowTransMode();
        return true;
      }

      GUISetupSound.this.update();

      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      setCanvasFont(0);

      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(624.0F), x1024(768.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(272.0F), y1024(32.0F), 2.0F, y1024(560.0F));
      if (GUISetupSound.this.page == 1) {
        GUISeparate.draw(this, GColor.Gray, x1024(304.0F), y1024(336.0F), x1024(496.0F), 2.0F);
      } else if (GUISetupSound.this.page == 3) {
        GUISeparate.draw(this, GColor.Gray, x1024(304.0F), y1024(208.0F), x1024(496.0F), 2.0F);
        GUISeparate.draw(this, GColor.Gray, x1024(304.0F), y1024(432.0F), x1024(496.0F), 2.0F);
      }

      setCanvasColor(GColor.Gray);

      draw(x1024(96.0F), y1024(80.0F), x1024(160.0F), y1024(48.0F), 0, GUISetupSound.this.i18n("sound.General"));
      draw(x1024(96.0F), y1024(144.0F), x1024(160.0F), y1024(48.0F), 0, GUISetupSound.this.i18n("sound.Audio"));
      draw(x1024(96.0F), y1024(208.0F), x1024(160.0F), y1024(48.0F), 0, GUISetupSound.this.i18n("sound.Music"));
      draw(x1024(96.0F), y1024(272.0F), x1024(160.0F), y1024(48.0F), 0, GUISetupSound.this.i18n("sound.Radio"));

      if (GUISetupSound.this.page == 0) {
        setCanvasColorWHITE();
        draw(x1024(520.0F), y1024(80.0F), x1024(64.0F), y1024(64.0F), GUISetupSound.this.texFull);
        draw(x1024(520.0F), y1024(208.0F), x1024(64.0F), y1024(64.0F), GUISetupSound.this.texFull);
        draw(x1024(520.0F), y1024(304.0F), x1024(64.0F), y1024(64.0F), GUISetupSound.this.texFull);
        draw(x1024(520.0F), y1024(400.0F), x1024(64.0F), y1024(64.0F), GUISetupSound.this.texFull);
        setCanvasColor(GColor.Gray);

        draw(x1024(368.0F), y1024(144.0F), x1024(368.0F), y1024(32.0F), 1, GUISetupSound.this.i18n("sound.Master_volume"));
        draw(x1024(368.0F), y1024(272.0F), x1024(368.0F), y1024(32.0F), 1, GUISetupSound.this.i18n("sound.Objects_volume"));
        draw(x1024(368.0F), y1024(368.0F), x1024(368.0F), y1024(32.0F), 1, GUISetupSound.this.i18n("sound.Music_volume"));
        draw(x1024(368.0F), y1024(464.0F), x1024(368.0F), y1024(32.0F), 1, GUISetupSound.this.i18n("sound.Voice_volume"));
        draw(x1024(384.0F), y1024(528.0F), x1024(336.0F), y1024(48.0F), 0, GUISetupSound.this.i18n("sound.Play_music"));
      } else if (GUISetupSound.this.page == 1) {
        draw(x1024(384.0F), y1024(80.0F), x1024(336.0F), y1024(32.0F), 1, GUISetupSound.this.i18n("sound.Sound_engine"));
        draw(x1024(384.0F), y1024(160.0F), x1024(336.0F), y1024(32.0F), 1, GUISetupSound.this.i18n("sound.Playback_channels"));
        draw(x1024(384.0F), y1024(240.0F), x1024(336.0F), y1024(32.0F), 1, GUISetupSound.this.i18n("sound.Audio_quality"));
        draw(x1024(384.0F), y1024(352.0F), x1024(336.0F), y1024(32.0F), 1, GUISetupSound.this.i18n("sound.Speakers_type"));
        draw(x1024(480.0F), y1024(448.0F), x1024(240.0F), y1024(48.0F), 0, GUISetupSound.this.i18n("sound.Enable_hardware"));
        draw(x1024(480.0F), y1024(528.0F), x1024(240.0F), y1024(48.0F), 0, GUISetupSound.this.i18n("sound.Reverse_stereo"));
      } else if (GUISetupSound.this.page == 2) {
        draw(x1024(400.0F), y1024(192.0F), x1024(352.0F), y1024(48.0F), 0, GUISetupSound.this.i18n("sound.Play_take-off"));
        draw(x1024(400.0F), y1024(304.0F), x1024(352.0F), y1024(48.0F), 0, GUISetupSound.this.i18n("sound.Play_in-flight"));
        draw(x1024(400.0F), y1024(416.0F), x1024(352.0F), y1024(48.0F), 0, GUISetupSound.this.i18n("sound.Play_crash"));
      } else if (GUISetupSound.this.page == 3) {
        setCanvasColorWHITE();
        draw(x1024(312.0F), y1024(272.0F), x1024(64.0F), y1024(64.0F), GUISetupSound.this.texFull);
        if (!GUISetupSound.this.isTransMode_PressMuteKey())
          draw(x1024(312.0F), y1024(496.0F), x1024(64.0F), y1024(64.0F), GUISetupSound.this.texFull);
        GUISetupSound.this.drawInd(this);
        setCanvasColor(GColor.Gray);
        draw(x1024(392.0F), y1024(128.0F), x1024(408.0F), y1024(32.0F), 0, GUISetupSound.this.i18n("setupNet.Voice"));
        draw(x1024(392.0F), y1024(288.0F), x1024(200.0F), y1024(32.0F), 0, GUISetupSound.this.i18n("sound.MicLevel"));
        draw(x1024(392.0F), y1024(360.0F), x1024(200.0F), y1024(32.0F), 0, GUISetupSound.this.i18n("sound.PlayClicks"));
        draw(x1024(328.0F), y1024(448.0F), x1024(200.0F), y1024(32.0F), 2, GUISetupSound.this.i18n("sound.TransMode"));
        if (GUISetupSound.this.isTransMode_PressMuteKey()) {
          int i = HotKeyEnv.env("$$$misc").find("radioMuteKey");
          if (i != 0)
            draw(x1024(328.0F), y1024(512.0F), x1024(400.0F), y1024(32.0F), 0, GUISetupSound.this.i18n("sound.MuteKey") + " : " + keyToStr(i));
        } else {
          draw(x1024(392.0F), y1024(512.0F), x1024(200.0F), y1024(32.0F), 0, GUISetupSound.this.i18n("sound.Sensitivity"));
        }
        draw(x1024(376.0F), y1024(568.0F), x1024(354.0F), y1024(32.0F), 2, GUISetupSound.this.i18n("sound.Test"));
      }
      draw(x1024(96.0F), y1024(656.0F), x1024(288.0F), y1024(48.0F), 0, GUISetupSound.this.i18n("sound.Apply_Back"));
    }

    private String keyToStr(int paramInt) {
      if (paramInt == 0) return "";
      if ((paramInt & 0xFFFF0000) == 0) {
        return resName(VK.getKeyText(paramInt));
      }
      return resName(VK.getKeyText(paramInt >> 16 & 0xFFFF)) + " " + resName(VK.getKeyText(paramInt & 0xFFFF));
    }

    private void initResource()
    {
      try {
        this.resource = ResourceBundle.getBundle("i18n/controls", RTSConf.cur.locale, LDRres.loader()); } catch (Exception localException) {
      }
    }

    private String resName(String paramString) {
      if (this.resource == null) return paramString; try
      {
        return this.resource.getString(paramString); } catch (Exception localException) {
      }
      return paramString;
    }

    public void setPosSize() {
      set1024PosSize(96.0F, 32.0F, 832.0F, 736.0F);

      GUISetupSound.this.bGeneral.setPosC(x1024(56.0F), y1024(104.0F));
      GUISetupSound.this.bAudio.setPosC(x1024(56.0F), y1024(168.0F));
      GUISetupSound.this.bMusic.setPosC(x1024(56.0F), y1024(232.0F));
      GUISetupSound.this.bRadio.setPosC(x1024(56.0F), y1024(296.0F));
      GUISetupSound.this.bBack.setPosC(x1024(56.0F), y1024(680.0F));

      GUISetupSound.this.pGeneral.setPosSize(x1024(400.0F), y1024(32.0F), x1024(304.0F), y1024(32.0F));
      GUISetupSound.this.pAudio.setPosSize(x1024(400.0F), y1024(32.0F), x1024(304.0F), y1024(32.0F));
      GUISetupSound.this.pMusic.setPosSize(x1024(400.0F), y1024(32.0F), x1024(304.0F), y1024(32.0F));
      GUISetupSound.this.pRadio.setPosSize(x1024(400.0F), y1024(32.0F), x1024(304.0F), y1024(32.0F));

      GUISetupSound.this.sMasterVolume.setPosC(x1024(552.0F), y1024(112.0F));
      GUISetupSound.this.sObjectsVolume.setPosC(x1024(552.0F), y1024(240.0F));
      GUISetupSound.this.sMusicVolume.setPosC(x1024(552.0F), y1024(336.0F));
      GUISetupSound.this.sVoiceVolume.setPosC(x1024(552.0F), y1024(432.0F));
      GUISetupSound.this.sMusicOn.setPosC(x1024(344.0F), y1024(552.0F));

      GUISetupSound.this.cEngine.setPosSize(x1024(416.0F), y1024(112.0F), x1024(272.0F), y1024(32.0F));
      GUISetupSound.this.cChannels.setPosSize(x1024(416.0F), y1024(192.0F), x1024(272.0F), y1024(32.0F));
      GUISetupSound.this.cRate.setPosSize(x1024(416.0F), y1024(272.0F), x1024(272.0F), y1024(32.0F));
      GUISetupSound.this.cType.setPosSize(x1024(416.0F), y1024(384.0F), x1024(272.0F), y1024(32.0F));
      GUISetupSound.this.sHardware.setPosC(x1024(456.0F), y1024(472.0F));
      GUISetupSound.this.sReverseStereo.setPosC(x1024(456.0F), y1024(552.0F));

      GUISetupSound.this.sPlayListTakeOff.setPosC(x1024(360.0F), y1024(216.0F));
      GUISetupSound.this.sPlayListInflight.setPosC(x1024(360.0F), y1024(328.0F));
      GUISetupSound.this.sPlayListCrash.setPosC(x1024(360.0F), y1024(440.0F));

      GUISetupSound.this.sPhoneOn.setPosC(x1024(352.0F), y1024(144.0F));
      GUISetupSound.this.sMicLevel.setPosC(x1024(344.0F), y1024(304.0F));
      GUISetupSound.this.sClicks.setPosC(x1024(352.0F), y1024(376.0F));
      GUISetupSound.this.cTransMode.setPosSize(x1024(536.0F), y1024(448.0F), x1024(256.0F), y1024(32.0F));
      GUISetupSound.this.sMicSens.setPosC(x1024(344.0F), y1024(528.0F));
      GUISetupSound.this.sTest.setPosC(x1024(768.0F), y1024(584.0F));
    }
  }
}