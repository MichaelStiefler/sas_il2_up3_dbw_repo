package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;

public class GUIClient extends GWindow
{
  public void setPosSize()
  {
    setPos(0.0F, 0.0F);
    setSize(this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow.win.dx, this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow.win.dy);
  }
  public void resolutionChanged() {
    setPosSize();
    super.resolutionChanged();
  }
  public void windowShown() {
    resolutionChanged();
    super.windowShown();
  }
}