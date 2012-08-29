package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.NetEnv;
import com.maddox.sound.AudioDevice;

public class GUINetServerDMission extends GUINetMission
{
  protected void clientRender()
  {
    GUINetMission.DialogClient localDialogClient = this.dialogClient;
    if (this.bEnableReFly)
      localDialogClient.draw(localDialogClient.x1024(96.0F), localDialogClient.y1024(400.0F), localDialogClient.x1024(224.0F), localDialogClient.y1024(48.0F), 0, i18n("miss.ReFly"));
    localDialogClient.draw(localDialogClient.x1024(96.0F), localDialogClient.y1024(464.0F), localDialogClient.x1024(224.0F), localDialogClient.y1024(48.0F), 0, i18n("miss.QuitMiss"));
  }

  protected void doReFly() {
    checkCaptured();
    destroyPlayerActor();
    ((NetUser)NetEnv.host()).sendStatInc();
    EventLog.onRefly(((NetUser)NetEnv.host()).shortName());
    AudioDevice.soundsOff();

    Main.stateStack().change(39);
  }

  protected void doExit() {
    checkCaptured();
    destroyPlayerActor();
    ((NetUser)NetEnv.host()).sendStatInc();

    Mission.cur().destroy();
    GUI.activate();
    Main.stateStack().change(38);
  }

  public GUINetServerDMission(GWindowRoot paramGWindowRoot) {
    super(42);
    init(paramGWindowRoot);
    this.infoMenu.info = i18n("miss.NetSDInfo");
  }
}