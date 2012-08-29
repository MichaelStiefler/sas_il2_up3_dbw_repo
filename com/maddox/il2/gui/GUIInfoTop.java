package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.il2.engine.Config;

public class GUIInfoTop extends GWindowDialogClient
{
  static GTexture texture;
  static GBevel bevel;

  private static void init()
  {
    if (Config.isUSE_RENDER()) {
      if (texture != null)
        return;
      texture = GTexture.New("GUI/game/basicelements.mat");
      bevel = new GBevel();
      bevel.set(new GRegion(48.0F, 240.0F, 16.0F, 16.0F), new GRegion(55.0F, 247.0F, 2.0F, 2.0F));
    }
  }

  public GUIInfoTop() {
    init();
  }

  public float M(float paramFloat) {
    return lookAndFeel().metric(paramFloat);
  }

  public void render() {
    setCanvasColorWHITE();
    lookAndFeel().drawBevel(this, 0.0F, 0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, bevel, texture);
  }

  public void setPosSize() {
    set1024PosSize(0.0F, 0.0F, 1024.0F, 32.0F);
  }

  public void resolutionChanged() {
    setPosSize();
    super.resolutionChanged();
  }
}