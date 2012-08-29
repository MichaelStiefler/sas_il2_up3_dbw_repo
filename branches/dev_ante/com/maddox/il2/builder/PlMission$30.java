package com.maddox.il2.builder;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.il2.ai.World;

class PlMission$30 extends GWindowCheckBox
{
  private final PlMission.WConditions this$1;

  public void preRender()
  {
    super.preRender();
    setChecked(World.cur().isTimeOfDayConstant(), false);
  }
  public boolean notify(int paramInt1, int paramInt2) {
    if (paramInt1 != 2) return false;
    World.cur().setTimeOfDayConstant(isChecked());
    PlMission.setChanged();
    return false;
  }
}