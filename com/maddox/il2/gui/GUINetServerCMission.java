package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.Front;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.NetEnv;

public class GUINetServerCMission extends GUINetMission
{
  protected void clientRender()
  {
    GUINetMission.DialogClient localDialogClient = this.dialogClient;
    localDialogClient.draw(localDialogClient.x1024(96.0F), localDialogClient.y1024(464.0F), localDialogClient.x1024(224.0F), localDialogClient.y1024(48.0F), 0, i18n("miss.QuitMiss"));
  }
  protected void doReFly() {
  }

  protected void doExit() {
    Front.checkAllCaptured();
    Mission.cur().doEnd();
    ((NetUser)NetEnv.host()).sendStatInc();
    GUI.activate();
    GUI.pad.leave(true);
    Main.stateStack().change(51);
  }

  public void tryExit() {
    doExit();
  }

  public GUINetServerCMission(GWindowRoot paramGWindowRoot) {
    super(49);
    init(paramGWindowRoot);
    this.infoMenu.info = i18n("miss.NetSCInfo");
  }
}