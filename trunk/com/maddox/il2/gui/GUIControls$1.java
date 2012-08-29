package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;

class GUIControls$1 extends GWindowMessageBox
{
  private final GUIControls.ItemControl this$1;

  public void result(int paramInt)
  {
    GUIControls.ItemControl.access$200(this.this$1).messageBox = null;
    if (paramInt == 3) {
      GUIControls.ItemControl.access$300(this.this$1, true);
    }
    else
    {
      int tmp50_49 = 0; GUIControls.ItemControl.access$200(this.this$1).curKey[1] = tmp50_49; GUIControls.ItemControl.access$200(this.this$1).curKey[0] = tmp50_49;
      this.this$1.fillCaption();
      this.parentWindow.setKeyFocus();
    }
  }
}