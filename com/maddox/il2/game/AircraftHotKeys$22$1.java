package com.maddox.il2.game;

import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.MsgAction;

final class AircraftHotKeys$22$1 extends MsgAction
{
  private final AircraftHotKeys.22 this$1;

  public void doAction()
  {
    if ((!Main3D.cur3D().isDemoPlaying()) || (!HotKeyEnv.isEnabled("aircraftView")))
    {
      HotKeyCmd.exec("aircraftView", "OutsideView");
    }
  }
}