package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;

class GUICampaignNew$3 extends GWindowMessageBox
{
  private final GUICampaignNew.DialogClient this$1;

  public void result(int paramInt)
  {
    if (paramInt == 3)
      GUICampaignNew.access$400(GUICampaignNew.DialogClient.access$300(this.this$1));
    else
      GUICampaignNew.DialogClient.access$300(this.this$1).client.activateWindow();
  }
}