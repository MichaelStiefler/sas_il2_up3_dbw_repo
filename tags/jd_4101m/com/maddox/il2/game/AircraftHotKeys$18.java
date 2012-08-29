package com.maddox.il2.game;

import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.MsgAction;

class AircraftHotKeys$18 extends MsgAction
{
  private final AircraftHotKeys.17 this$1;

  public void doAction()
  {
    HotKeyCmd.exec("misc", "autopilotAuto_");
  }
}