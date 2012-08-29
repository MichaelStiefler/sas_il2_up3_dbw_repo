package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindowRootMenu;
import com.maddox.il2.game.Mission;

public class GUIRootMenu extends GWindowRootMenu
{
  GTexture background;

  public void render()
  {
    if ((Mission.cur() == null) || (Mission.cur().isDestroyed())) {
      setCanvasColorWHITE();
      draw(0.0F, 0.0F, this.win.dx, this.win.dy, this.background);
    }
  }

  public void created() {
    this.background = GTexture.New("GUI/background.mat");
    super.created();
  }
}