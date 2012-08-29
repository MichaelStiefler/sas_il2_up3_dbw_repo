package com.maddox.il2.builder;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;

class PlMission$28 extends GWindowComboControl
{
  private final PlMission.WConditions this$1;

  public boolean notify(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 2) {
      this.this$1.getCloudType();
    }
    return super.notify(paramInt1, paramInt2);
  }
}