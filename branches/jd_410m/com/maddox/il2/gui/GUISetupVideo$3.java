package com.maddox.il2.gui;

import com.maddox.rts.CmdEnv;
import com.maddox.rts.MsgAction;

class GUISetupVideo$3 extends MsgAction
{
  private final GUISetupVideo.DialogClient this$1;

  public void doAction()
  {
    String str = GUISetupVideo.access$200(GUISetupVideo.DialogClient.access$300(this.this$1));
    if (str == null) return;
    if ((str.equals(GUISetupVideo.DialogClient.access$300(this.this$1).enterCmdLine)) && (GUISetupVideo.DialogClient.access$300(this.this$1).enterUse3Renders == GUISetupVideo.access$400(GUISetupVideo.DialogClient.access$300(this.this$1)))) return;
    if (CmdEnv.top().exec(str) == CmdEnv.RETURN_OK)
      GUISetupVideo.access$500(GUISetupVideo.DialogClient.access$300(this.this$1));
    else
      GUISetupVideo.DialogClient.access$300(this.this$1).update();
  }
}