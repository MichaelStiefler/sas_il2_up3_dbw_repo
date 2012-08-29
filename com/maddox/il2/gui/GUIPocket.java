package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogControl;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.il2.engine.Config;

public class GUIPocket extends GWindowDialogControl
{
  static GTexture texture;
  static GBevel bevel;
  static GBevel bevelEmpty;

  private static void init()
  {
    if (Config.isUSE_RENDER()) {
      if (texture != null)
        return;
      texture = GTexture.New("GUI/game/staticelements.mat");
      bevel = new GBevel();
      bevel.set(new GRegion(0.0F, 0.0F, 256.0F, 32.0F), new GRegion(9.0F, 0.0F, 240.0F, 32.0F));
      bevelEmpty = new GBevel();
      bevelEmpty.set(new GRegion(0.0F, 224.0F, 256.0F, 32.0F), new GRegion(9.0F, 224.0F, 240.0F, 32.0F));
    }
  }

  public void render() {
    setCanvasColorWHITE();
    if (this.bEnable) {
      lookAndFeel().drawBevel(this, 0.0F, 0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, bevel, texture);
      lookAndFeel().renderTextDialogControl(this, 9.0F, 0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - 9.0F - 7.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.color, false);
    } else {
      lookAndFeel().drawBevel(this, 0.0F, 0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, bevelEmpty, texture);
    }
  }

  public GUIPocket(GWindow paramGWindow, String paramString) {
    super(paramGWindow);
    init();
    this.cap = new GCaption(paramString);
    this.jdField_toolTip_of_type_JavaLangString = this.jdField_toolTip_of_type_JavaLangString;
    this.align = 1;
  }
}