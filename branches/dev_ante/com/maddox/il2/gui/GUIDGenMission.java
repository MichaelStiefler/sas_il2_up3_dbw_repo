package com.maddox.il2.gui;

import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.Front;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.rts.CmdEnv;

public class GUIDGenMission extends GUIMission
{
  protected void doExit()
  {
    Front.checkAllCaptured();
    CmdEnv.top().exec("mission END");
    Main.stateStack().change(64);
  }

  public GUIDGenMission(GWindowRoot paramGWindowRoot) {
    super(63);
    init(paramGWindowRoot);
    this.infoMenu.info = i18n("miss.CInfo");
  }
}