package com.maddox.il2.game;

import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.MsgAction;

final class AircraftHotKeys$46$1 extends MsgAction
{
  private final AircraftHotKeys.46 this$1;

  public void doAction(Object paramObject)
  {
    int i = ((Integer)paramObject).intValue();
    HotKeyCmd.exec("aircraftView", "cockpitSwitch" + i);
  }
}