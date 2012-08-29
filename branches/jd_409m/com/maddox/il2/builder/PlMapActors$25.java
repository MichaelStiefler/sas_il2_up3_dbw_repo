package com.maddox.il2.builder;

import com.maddox.gwindow.GFileFilter;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowFileSaveAs;

class PlMapActors$25 extends GWindowFileSaveAs
{
  private final PlMapActors.24 this$1;

  public void result(String paramString)
  {
    if (paramString != null)
      PlMapActors.24.access$1200(this.this$1).saveSpawn("maps/" + paramString);
  }
}