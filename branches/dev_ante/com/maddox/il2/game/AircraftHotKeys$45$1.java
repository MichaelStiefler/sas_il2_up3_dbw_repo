package com.maddox.il2.game;

import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.MsgAction;

final class AircraftHotKeys$45$1 extends MsgAction
{
  private final AircraftHotKeys.45 this$1;

  public void doAction(Object paramObject)
  {
    int i = ((Integer)paramObject).intValue();
    HotKeyCmd.exec("aircraftView", "cockpitSwitch" + i);
  }
}