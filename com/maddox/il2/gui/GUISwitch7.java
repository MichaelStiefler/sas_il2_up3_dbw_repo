package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.il2.engine.Config;
import com.maddox.rts.CfgInt;

public class GUISwitch7 extends GUISwitchN
{
  static GTexRegion[] st = new GTexRegion[7];

  private static void init() { if (Config.isUSE_RENDER()) {
      if (st[0] != null)
        return;
      GTexture localGTexture = GTexture.New("GUI/game/switches1.mat");
      for (int i = 0; i < 4; i++)
        st[i] = new GTexRegion(localGTexture, i * 64, 0.0F, 64.0F, 64.0F);
      for (int j = 0; j < 3; j++)
        st[(j + 4)] = new GTexRegion(localGTexture, j * 64, 64.0F, 64.0F, 64.0F);
    } }

  public void render()
  {
    setCanvasColorWHITE();
    draw(0.0F, 0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, st[this.pos[this.state]]);
  }

  public void created() {
    this.texDx = 64.0F;
    this.texDy = 64.0F;
    resolutionChanged();
  }

  public GUISwitch7(GWindow paramGWindow, int[] paramArrayOfInt, boolean[] paramArrayOfBoolean) {
    super(paramGWindow, 180.0F, 30.0F, paramArrayOfInt, paramArrayOfBoolean);
    init();
  }
  public GUISwitch7(GWindow paramGWindow, int[] paramArrayOfInt, CfgInt paramCfgInt, boolean paramBoolean) {
    super(paramGWindow, 180.0F, 30.0F, paramArrayOfInt, paramCfgInt, paramBoolean);
    init();
  }
}