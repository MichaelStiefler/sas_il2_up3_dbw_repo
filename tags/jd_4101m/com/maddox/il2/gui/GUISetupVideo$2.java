package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.il2.engine.Config;
import com.maddox.rts.CmdEnv;

class GUISetupVideo$2 extends GWindowMessageBox
{
  private final GUISetupVideo.1 this$1;

  public void result(int paramInt)
  {
    if ((paramInt == 4) || (paramInt == 5)) {
      CmdEnv.top().exec(GUISetupVideo.1.access$000(this.this$1).enterCmdLine);
      GUISetupVideo.access$100(GUISetupVideo.1.access$000(this.this$1), false);
      GUISetupVideo.1.access$000(this.this$1).update();
    } else {
      GUISetupVideo.1.access$000(this.this$1).enterCmdLine = GUISetupVideo.access$200(GUISetupVideo.1.access$000(this.this$1));
      GUISetupVideo.1.access$000(this.this$1).enterSaveAspect = Config.cur.windowSaveAspect;
      GUISetupVideo.1.access$000(this.this$1).enterUse3Renders = Config.cur.windowUse3Renders;
    }
  }
}