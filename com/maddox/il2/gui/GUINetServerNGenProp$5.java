package com.maddox.il2.gui;

import com.maddox.gwindow.GWindowComboControl;
import java.util.ArrayList;

class GUINetServerNGenProp$5 extends GWindowComboControl
{
  GUINetServerNGenProp.MenuItem item;
  private final GUINetServerNGenProp.Menu this$1;

  public boolean notify(int paramInt1, int paramInt2)
  {
    boolean bool = super.notify(paramInt1, paramInt2);
    if (paramInt1 == 2)
      this.item.select = paramInt2;
    return bool;
  }
}