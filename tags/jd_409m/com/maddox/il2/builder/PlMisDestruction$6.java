package com.maddox.il2.builder;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowHSliderInt;

class PlMisDestruction$6 extends GWindowHSliderInt
{
  private final PlMisDestruction.WDialog this$1;

  public boolean notify(int paramInt1, int paramInt2)
  {
    PlMisDestruction.access$402(PlMisDestruction.WDialog.access$300(this.this$1), (int)Math.pow(2.0D, pos()));
    return super.notify(paramInt1, paramInt2);
  }
  public void created() { this.bSlidingNotify = true;
  }
}