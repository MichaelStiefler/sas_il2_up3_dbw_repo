package com.maddox.il2.builder;

import com.maddox.gwindow.GFileFilter;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowFileOpen;

class PlMapActors$21 extends GWindowFileOpen
{
  private final PlMapActors.20 this$1;

  public void result(String paramString)
  {
    if (paramString != null)
      PlMapActors.20.access$800(this.this$1).loadSpawn("maps/" + paramString);
  }
}