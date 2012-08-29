package com.maddox.il2.game;

import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.MsgAction;

class AircraftHotKeys$15 extends MsgAction
{
  private final AircraftHotKeys.14 this$1;

  public void doAction(Object paramObject)
  {
    int i = ((Integer)paramObject).intValue();
    HotKeyCmd.exec("misc", "cockpitRealOff" + i);
  }
}