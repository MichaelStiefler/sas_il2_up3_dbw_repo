package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.il2.engine.Config;

public class GUISeparate extends GWindow
{
  static GTexRegion tex;
  public GColor color;

  private static void init()
  {
    if (Config.isUSE_RENDER()) {
      if (tex != null)
        return;
      tex = new GTexRegion("GUI/game/basicelements.mat", 0.0F, 0.0F, 1.0F, 1.0F);
    }
  }

  public static void draw(GWindow paramGWindow, GColor paramGColor, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    init();
    paramGWindow.setCanvasColor(paramGColor.color);
    paramGWindow.draw(paramFloat1, paramFloat2, paramFloat3, paramFloat4, tex);
  }
  public static void draw(GWindow paramGWindow, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    init();
    paramGWindow.setCanvasColor(paramInt);
    paramGWindow.draw(paramFloat1, paramFloat2, paramFloat3, paramFloat4, tex);
  }

  public void render()
  {
    setCanvasColor(this.color.color);
    draw(0.0F, 0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, tex);
  }

  public boolean isMousePassThrough(float paramFloat1, float paramFloat2)
  {
    return true;
  }

  public void created() {
    this.bAlwaysBehind = true;
    this.bAcceptsKeyFocus = false;
    this.bTransient = true;
  }

  public GUISeparate(GWindow paramGWindow, int paramInt1, int paramInt2, int paramInt3) {
    init();
    this.color = new GColor(paramInt1, paramInt2, paramInt3);
    doNew(paramGWindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
  }
}