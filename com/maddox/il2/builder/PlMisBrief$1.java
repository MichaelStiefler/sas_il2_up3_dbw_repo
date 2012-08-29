package com.maddox.il2.builder;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowEditControl;

class PlMisBrief$1 extends GWindowEditControl
{
  private final PlMisBrief.WEditor this$1;

  public boolean notify(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 2) && (paramInt2 == 0))
      PlMission.setChanged();
    return super.notify(paramInt1, paramInt2);
  }
}