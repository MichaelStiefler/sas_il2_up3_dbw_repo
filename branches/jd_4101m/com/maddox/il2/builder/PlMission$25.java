package com.maddox.il2.builder;

import com.maddox.gwindow.GFileFilter;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowFileSaveAs;
import com.maddox.gwindow.GWindowRootMenu;
import com.maddox.gwindow.GWindowStatusBar;

class PlMission$25 extends GWindowFileSaveAs
{
  private final PlMission.24 this$1;

  public void result(String paramString)
  {
    if (paramString != null) {
      paramString = PlMission.access$300(PlMission.24.access$1500(this.this$1), paramString);
      PlMission.access$102(PlMission.24.access$1500(this.this$1), paramString);
      ((GWindowRootMenu)Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(PlMission.access$100(PlMission.24.access$1500(this.this$1)));
      PlMission.access$402(PlMission.24.access$1500(this.this$1), paramString);
      PlMission.24.access$1500(this.this$1).save("missions/" + paramString);
    }
    PlMission.access$1402(PlMission.24.access$1500(this.this$1), false);
    ((PlMapLoad)Plugin.getPlugin("MapLoad")).guiMapLoad();
  }
}