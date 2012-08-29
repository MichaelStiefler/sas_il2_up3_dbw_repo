package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetEnv;

public class GUINetClientCMission extends GUINetMission
{
  protected void clientRender()
  {
    GUINetMission.DialogClient localDialogClient = this.dialogClient;
    localDialogClient.draw(localDialogClient.x1024(96.0F), localDialogClient.y1024(464.0F), localDialogClient.x1024(224.0F), localDialogClient.y1024(48.0F), 0, i18n("miss.Disconnect"));
  }
  protected void doReFly() {
  }

  protected void doExit() {
    GUINetClientGuard localGUINetClientGuard = (GUINetClientGuard)Main.cur().netChannelListener;
    if (localGUINetClientGuard == null) return;
    localGUINetClientGuard.dlgDestroy(new GUINetClientGuard.DestroyExec() {
      public void destroy(GUINetClientGuard paramGUINetClientGuard) { GUINetClientCMission.this.destroyPlayerActor();
        ((NetUser)NetEnv.host()).sendStatInc();
        new MsgAction(64, 2.0D) {
          public void doAction() { GUINetClientGuard localGUINetClientGuard = (GUINetClientGuard)Main.cur().netChannelListener;
            if (localGUINetClientGuard != null)
              localGUINetClientGuard.destroy(true); } } ; } } );
  }

  public GUINetClientCMission(GWindowRoot paramGWindowRoot) {
    super(50);
    init(paramGWindowRoot);
    this.infoMenu.info = i18n("miss.NetCCInfo");
  }
}