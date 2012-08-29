package com.maddox.il2.builder;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowHSliderInt;

class PlMisDestruction$7 extends GWindowHSliderInt
{
  private final PlMisDestruction.WDialog this$1;

  public boolean notify(int paramInt1, int paramInt2)
  {
    PlMisDestruction.access$502(PlMisDestruction.WDialog.access$300(this.this$1), pos());
    return super.notify(paramInt1, paramInt2);
  }
  public void created() { this.bSlidingNotify = true;
  }
}