package com.maddox.il2.builder;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;

class PlMapActors$27 extends GWindowMessageBox
{
  private final PlMapActors.26 this$1;

  public void result(int paramInt)
  {
    if (paramInt == 3) {
      PlMapActors.26.access$1300(this.this$1).loadAs(null);
      PlMapLoad.bDrawNumberBridge = true;
    }
  }
}