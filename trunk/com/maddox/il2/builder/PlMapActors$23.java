package com.maddox.il2.builder;

import com.maddox.gwindow.GFileFilter;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowFileSaveAs;

class PlMapActors$23 extends GWindowFileSaveAs
{
  private final PlMapActors.22 this$1;

  public void result(String paramString)
  {
    if (paramString != null)
      PlMapActors.22.access$1000(this.this$1).saveAs("maps/" + paramString);
  }
}