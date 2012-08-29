package com.maddox.il2.gui;

import com.maddox.il2.game.Main;
import com.maddox.il2.net.USGS;

class GUINetClient$1 extends GUINetClientGuard.DestroyExec
{
  private final GUINetClient.DialogClient this$1;

  public void destroy(GUINetClientGuard paramGUINetClientGuard)
  {
    if ((USGS.isUsed()) || (Main.cur().netGameSpy != null))
    {
      Main.doGameExit();
    }
    else paramGUINetClientGuard.destroy(true);
  }
}