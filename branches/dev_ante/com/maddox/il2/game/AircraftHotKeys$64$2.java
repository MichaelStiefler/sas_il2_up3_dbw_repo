package com.maddox.il2.game;

import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.MsgAction;

final class AircraftHotKeys$64$2 extends MsgAction
{
  private final AircraftHotKeys.64 this$1;

  public void doAction(Object paramObject)
  {
    int i = ((Integer)paramObject).intValue();
    HotKeyCmd.exec("misc", "cockpitRealOn" + i);
  }
}