package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.il2.engine.Config;
import com.maddox.rts.CfgInt;

public class GUISwitch16 extends GUISwitchN
{
  static GTexRegion[] st = new GTexRegion[16];
  private static boolean bInited = false;

  private static void init() { if (Config.isUSE_RENDER()) {
      if (bInited)
        return;
      GTexture localGTexture = GTexture.New("GUI/game/switches3.mat");
      int i = 0;
      for (int j = 0; j < 4; j++)
        for (int k = 0; k < 4; k++)
          st[(i++)] = new GTexRegion(localGTexture, k * 64, j * 64, 64.0F, 64.0F);
      bInited = true;
    } }

  public void render()
  {
    setCanvasColorWHITE();
    draw(0.0F, 0.0F, this.win.dx, this.win.dy, st[this.pos[this.state]]);
  }

  public void created() {
    this.texDx = 64.0F;
    this.texDy = 64.0F;
    resolutionChanged();
  }

  public GUISwitch16(GWindow paramGWindow, int[] paramArrayOfInt, boolean[] paramArrayOfBoolean) {
    super(paramGWindow, 135.0F, 18.0F, paramArrayOfInt, paramArrayOfBoolean);
    init();
  }
  public GUISwitch16(GWindow paramGWindow, int[] paramArrayOfInt, CfgInt paramCfgInt, boolean paramBoolean) {
    super(paramGWindow, 135.0F, 18.0F, paramArrayOfInt, paramCfgInt, paramBoolean);
    init();
  }
}