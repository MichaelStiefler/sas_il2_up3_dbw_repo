package com.maddox.il2.gui;

import com.maddox.gwindow.GWindowComboControl;
import java.util.ArrayList;

class GUIDGenNew$2 extends GWindowComboControl
{
  GUIDGenNew.Camp camp;
  private final GUIDGenNew.Table this$1;

  public boolean notify(int paramInt1, int paramInt2)
  {
    boolean bool = super.notify(paramInt1, paramInt2);
    if (paramInt1 == 2)
      this.camp.select = paramInt2;
    return bool;
  }
}