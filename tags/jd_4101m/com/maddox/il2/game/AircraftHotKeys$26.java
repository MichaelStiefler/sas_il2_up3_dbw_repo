package com.maddox.il2.game;

import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.MsgAction;

class AircraftHotKeys$26 extends MsgAction
{
  private final AircraftHotKeys.25 this$1;

  public void doAction()
  {
    if ((!Main3D.cur3D().isDemoPlaying()) || (!HotKeyEnv.isEnabled("aircraftView")))
      HotKeyCmd.exec("aircraftView", "OutsideView");
  }
}