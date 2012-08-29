package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogClient;

class GUINetNewServer$DlgPassword$1 extends GWindowDialogClient
{
  private final GUINetNewServer.DlgPassword this$1;

  public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
  {
    if (paramInt1 != 2)
      return super.notify(paramGWindow, paramInt1, paramInt2);
    if (paramGWindow == this.this$1.bOk)
    {
      if (this.this$1.doOk())
        close(false);
      return true;
    }
    if (paramGWindow == this.this$1.bCancel)
    {
      this.this$1.doCancel();
      close(false);
      return true;
    }

    return super.notify(paramGWindow, paramInt1, paramInt2);
  }
}