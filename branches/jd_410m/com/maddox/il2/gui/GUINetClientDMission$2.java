package com.maddox.il2.gui;

import com.maddox.il2.game.Main;
import com.maddox.rts.MsgAction;

class GUINetClientDMission$2 extends MsgAction
{
  private final GUINetClientDMission.1 this$1;

  public void doAction()
  {
    GUINetClientGuard localGUINetClientGuard = (GUINetClientGuard)Main.cur().netChannelListener;
    if (localGUINetClientGuard != null)
      localGUINetClientGuard.destroy(true);
  }
}