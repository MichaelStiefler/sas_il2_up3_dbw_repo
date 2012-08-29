package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.game.ZutiTimer_Refly;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.NetEnv;
import com.maddox.sound.AudioDevice;

public class GUINetServerDMission extends GUINetMission
{
  protected void clientRender()
  {
    GUINetMission.DialogClient localDialogClient = this.dialogClient;
    if (this.bEnableReFly) {
      localDialogClient.draw(localDialogClient.x1024(96.0F), localDialogClient.y1024(400.0F), localDialogClient.x1024(224.0F), localDialogClient.y1024(48.0F), 0, i18n("miss.ReFly"));
    }
    else if ((Main.cur().mission.zutiMisc_EnableReflyOnlyIfBailedOrDied) || (Main.cur().mission.zutiMisc_DisableReflyForMissionDuration))
    {
      if ((ZutiSupportMethods.ZUTI_KIA_COUNTER > Main.cur().mission.zutiMisc_MaxAllowedKIA) || (Main.cur().mission.zutiMisc_DisableReflyForMissionDuration))
      {
        localDialogClient.draw(localDialogClient.x1024(96.0F), localDialogClient.y1024(400.0F), localDialogClient.x1024(224.0F), localDialogClient.y1024(48.0F), 0, i18n("miss.NoReFly"));
      }
      else if ((!ZutiSupportMethods.ZUTI_KIA_DELAY_CLEARED) && (reflyTimer != null) && (World.isPlayerDead()))
      {
        String str = reflyTimer.getRemaimingTime();
        localDialogClient.draw(localDialogClient.x1024(96.0F), localDialogClient.y1024(400.0F), localDialogClient.x1024(224.0F), localDialogClient.y1024(48.0F), 0, i18n("miss.ReFlyWait") + "   " + str);
        if (str.equals("00:00"))
        {
          this.bEnableReFly = true;
          this.bReFly.showWindow();
          reflyTimer = null;
        }
      }
    }
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