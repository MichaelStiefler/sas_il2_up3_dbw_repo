package com.maddox.il2.builder;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowFileBox;
import com.maddox.gwindow.GWindowMessageBox;

class PlMission$18 extends GWindowMessageBox
{
  private final PlMission.DlgFileConfirmSave this$1;

  public void result(int paramInt)
  {
    if (paramInt != 3)
      this.this$1.bClose = false;
    this.this$1.box.endExec();
  }
}