package com.maddox.il2.builder;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowEditTextControl;

class PlMisBrief$2 extends GWindowEditTextControl
{
  private final PlMisBrief.WEditor this$1;

  public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
  {
    if ((paramGWindow == this.edit) && (paramInt1 == 2) && (paramInt2 == 0))
      PlMission.setChanged();
    return super.notify(paramGWindow, paramInt1, paramInt2);
  }
}