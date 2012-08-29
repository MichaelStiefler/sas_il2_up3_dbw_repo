package com.maddox.il2.builder;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowEditControl;

class PlMission$26 extends GWindowEditControl
{
  private final PlMission.WConditions this$1;

  public void afterCreated()
  {
    super.afterCreated();
    this.bNumericOnly = true;
    this.bDelayedNotify = true;
  }
  public boolean notify(int paramInt1, int paramInt2) {
    if (paramInt1 != 2) return false;
    this.this$1.getTime();
    return false;
  }
}