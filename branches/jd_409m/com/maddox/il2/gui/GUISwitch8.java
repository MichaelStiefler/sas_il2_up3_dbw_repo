package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.il2.engine.Config;

public class GUISwitch8 extends GUISwitchN
{
  static GTexRegion[] st = new GTexRegion[8];

  private static void init() { if (Config.isUSE_RENDER()) {
      if (st[0] != null)
        return;
      GTexture localGTexture = GTexture.New("GUI/game/switches1.mat");
      for (int i = 0; i < 4; i++)
        st[i] = new GTexRegion(localGTexture, i * 48, 160.0F, 48.0F, 48.0F);
      for (int j = 0; j < 4; j++)
        st[(j + 4)] = new GTexRegion(localGTexture, j * 48, 208.0F, 48.0F, 48.0F);
    } }

  public void render()
  {
    setCanvasColorWHITE();
    draw(0.0F, 0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, st[this.pos[this.state]]);
  }

  public void created() {
    this.texDx = 48.0F;
    this.texDy = 48.0F;
    resolutionChanged();
  }

  public GUISwitch8(GWindow paramGWindow, int[] paramArrayOfInt, boolean[] paramArrayOfBoolean) {
    super(paramGWindow, 0.0F, -45.0F, paramArrayOfInt, paramArrayOfBoolean);
    init();
  }
}