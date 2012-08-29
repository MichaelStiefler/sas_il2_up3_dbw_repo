package com.maddox.il2.gui;

import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.RenderContext;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.effects.Cinema;
import com.maddox.il2.objects.effects.LightsGlare;
import com.maddox.il2.objects.effects.SunGlare;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.opengl.Provider;
import com.maddox.rts.BackgroundTask;
import com.maddox.rts.CfgFlags;
import com.maddox.rts.CfgInt;
import com.maddox.rts.CfgTools;
import com.maddox.rts.MsgAction;

class GUIBWDemoPlay$2 extends MsgAction
{
  private final GUIBWDemoPlay.1 this$1;

  public void doAction()
  {
    if (this.this$1._bCancel) return;
    this.this$1._bStarting = true;
    GUIBWDemoPlay.access$100(GUIBWDemoPlay.1.access$000(this.this$1)).showModal();
    Main.closeAllNetChannels();
    GUIBWDemoPlay.MissionListener localMissionListener = new GUIBWDemoPlay.MissionListener(GUIBWDemoPlay.1.access$000(this.this$1));

    Mat.setGrayScaleLoading(true);
    Provider.setEnableBW(true);
    for (int i = 0; i < 3; i++) {
      Main3D.cur3D()._cinema[i].setShow(true);
      Main3D.cur3D()._cinema[i].resetGame();
      Main3D.cur3D()._lightsGlare[i].enterBWmode();
      Main3D.cur3D()._sunGlare[i].enterBWmode();
    }
    GUIBWDemoPlay.access$202(GUIBWDemoPlay.1.access$000(this.this$1), Main3D.cur3D().hud.bDrawAllMessages);
    Main3D.cur3D().hud.bDrawAllMessages = false;
    GUIBWDemoPlay.access$302(GUIBWDemoPlay.1.access$000(this.this$1), World.cur().isArcade());
    World.cur().setArcade(false);
    GUIBWDemoPlay.access$402(GUIBWDemoPlay.1.access$000(this.this$1), Voice.isEnableVoices());
    Voice.setEnableVoices(false);
    GUIBWDemoPlay.access$502(GUIBWDemoPlay.1.access$000(this.this$1), RenderContext.cfgTexFlags.get(GUIBWDemoPlay.access$600(GUIBWDemoPlay.1.access$000(this.this$1), 512)));
    GUIBWDemoPlay.access$702(GUIBWDemoPlay.1.access$000(this.this$1), RenderContext.cfgTexFlags.get(GUIBWDemoPlay.access$600(GUIBWDemoPlay.1.access$000(this.this$1), 16384)));
    RenderContext.cfgTexFlags.set(GUIBWDemoPlay.access$600(GUIBWDemoPlay.1.access$000(this.this$1), 512), false);
    RenderContext.cfgTexFlags.set(GUIBWDemoPlay.access$600(GUIBWDemoPlay.1.access$000(this.this$1), 16384), false);
    int j = RenderContext.cfgTexFlags.apply();
    GUIBWDemoPlay.access$802(GUIBWDemoPlay.1.access$000(this.this$1), RenderContext.cfgHardwareShaders.get());
    RenderContext.cfgHardwareShaders.set(0);
    j |= RenderContext.cfgHardwareShaders.apply();
    Engine.land().UnLoadMap();

    CfgFlags localCfgFlags = (CfgFlags)CfgTools.get("MusState");
    for (int k = 0; k < GUIBWDemoPlay.access$900(GUIBWDemoPlay.1.access$000(this.this$1)).length; k++) {
      GUIBWDemoPlay.access$900(GUIBWDemoPlay.1.access$000(this.this$1))[k] = localCfgFlags.get(k);
      localCfgFlags.set(k, false);
    }
    localCfgFlags.apply();

    String str = Main3D.cur3D().playRecordedMission(GUIBWDemoPlay.demoFile);
    if (str != null) {
      BackgroundTask.removeListener(localMissionListener);
      GUIBWDemoPlay.access$1000(GUIBWDemoPlay.1.access$000(this.this$1), str);
    } else if (Main3D.cur3D().playRecordedStreams() != null) {
      Main.cur().netMissionListener = localMissionListener;
    }
  }
}