package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GRegion;

public class GUIInfoMenu extends GUIInfoTop
{
  public String info;

  public void render()
  {
    super.render();
    setCanvasColor(GColor.Gray);
    setCanvasFont(0);
    draw(M(2.0F), 0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - M(2.0F), this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, 0, this.info);
  }

  public void setPosSize() {
    set1024PosSize(0.0F, 0.0F, 300.0F, 32.0F);
  }
}