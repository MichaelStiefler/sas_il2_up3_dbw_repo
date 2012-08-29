package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;

class GUIDGenNew$4 extends GWindowMessageBox
{
  private final GUIDGenNew.DialogClient this$1;

  public void result(int paramInt)
  {
    if (paramInt == 3) {
      GUIDGenNew.access$600(GUIDGenNew.DialogClient.access$500(this.this$1));
      GUIDGenNew.access$700(GUIDGenNew.DialogClient.access$500(this.this$1));
    } else {
      GUIDGenNew.DialogClient.access$500(this.this$1).client.activateWindow();
    }
  }
}