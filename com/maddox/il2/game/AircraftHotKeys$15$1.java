package com.maddox.il2.game;

import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.MsgAction;

final class AircraftHotKeys$15$1 extends MsgAction
{
  private final AircraftHotKeys.15 this$1;

  public void doAction()
  {
    HotKeyCmd.exec("misc", "autopilotAuto_");
  }
}