package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.il2.game.Main;

class GUIMainMenu$1 extends GWindowMessageBox
{
  private final GUIMainMenu.DialogClient this$1;

  public void result(int paramInt)
  {
    if (paramInt == 3)
      Main.doGameExit();
    else
      GUIMainMenu.DialogClient.access$000(this.this$1).client.activateWindow();
  }
}