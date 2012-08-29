package com.maddox.il2.game;

import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.MsgAction;

class AircraftHotKeys$68 extends MsgAction
{
  private final AircraftHotKeys.67 this$1;

  public void doAction(Object paramObject)
  {
    int i = ((Integer)paramObject).intValue();
    HotKeyCmd.exec("aircraftView", "fov" + i);
  }
}