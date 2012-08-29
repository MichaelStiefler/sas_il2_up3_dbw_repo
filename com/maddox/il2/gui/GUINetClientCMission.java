// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUINetClientCMission.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetEnv;

// Referenced classes of package com.maddox.il2.gui:
//            GUINetMission, GUINetClientGuard, GUIInfoMenu

public class GUINetClientCMission extends com.maddox.il2.gui.GUINetMission
{

    protected void clientRender()
    {
        com.maddox.il2.gui.GUINetMission.DialogClient dialogclient = dialogClient;
        com.maddox.il2.gui.GUINetMission.DialogClient _tmp = dialogclient;
        dialogclient.draw(dialogclient.x1024(96F), dialogclient.y1024(464F), dialogclient.x1024(224F), dialogclient.y1024(48F), 0, i18n("miss.Disconnect"));
    }

    protected void doReFly()
    {
    }

    protected void doExit()
    {
        com.maddox.il2.gui.GUINetClientGuard guinetclientguard = (com.maddox.il2.gui.GUINetClientGuard)com.maddox.il2.game.Main.cur().netChannelListener;
        if(guinetclientguard == null)
        {
            return;
        } else
        {
            guinetclientguard.dlgDestroy(new com.maddox.il2.gui.GUINetClientGuard.DestroyExec() {

                public void destroy(com.maddox.il2.gui.GUINetClientGuard guinetclientguard1)
                {
                    destroyPlayerActor();
                    ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).sendStatInc();
                    new com.maddox.rts.MsgAction(64, 2D) {

                        public void doAction()
                        {
                            com.maddox.il2.gui.GUINetClientGuard guinetclientguard2 = (com.maddox.il2.gui.GUINetClientGuard)com.maddox.il2.game.Main.cur().netChannelListener;
                            if(guinetclientguard2 != null)
                                guinetclientguard2.destroy(true);
                        }

                    }
;
                }

            }
);
            return;
        }
    }

    public GUINetClientCMission(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(50);
        init(gwindowroot);
        infoMenu.info = i18n("miss.NetCCInfo");
    }
}
