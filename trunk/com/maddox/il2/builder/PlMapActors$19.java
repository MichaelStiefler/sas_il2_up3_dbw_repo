package com.maddox.il2.builder;

import com.maddox.gwindow.GFileFilter;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowFileOpen;

class PlMapActors$19 extends GWindowFileOpen
{
  private final PlMapActors.18 this$1;

  public void result(String paramString)
  {
    if (paramString != null)
      PlMapActors.18.access$600(this.this$1).loadAs("maps/" + paramString);
  }
}