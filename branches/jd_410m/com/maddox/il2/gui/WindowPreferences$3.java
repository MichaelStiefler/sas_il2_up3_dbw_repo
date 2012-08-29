package com.maddox.il2.gui;

import com.maddox.rts.CmdEnv;
import com.maddox.rts.MsgAction;
import com.maddox.rts.ScreenMode;
import java.util.ArrayList;

class WindowPreferences$3 extends MsgAction
{
  private final WindowPreferences.2 this$0;

  public void doAction(Object paramObject)
  {
    int i = this.this$0.getSelected();
    ScreenMode localScreenMode = (ScreenMode)WindowPreferences.screenModes.get(i);
    CmdEnv.top().exec("window " + localScreenMode.width() + " " + localScreenMode.height() + " " + localScreenMode.colourBits() + " " + localScreenMode.colourBits() + " FULL");

    this.this$0.setSelected(WindowPreferences._findVideoMode(ScreenMode.current()), true, false);
  }
}