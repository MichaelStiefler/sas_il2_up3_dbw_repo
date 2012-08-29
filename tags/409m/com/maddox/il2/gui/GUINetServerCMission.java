// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUINetServerCMission.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.Front;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.NetEnv;

// Referenced classes of package com.maddox.il2.gui:
//            GUINetMission, GUI, GUIPad, GUIInfoMenu

public class GUINetServerCMission extends com.maddox.il2.gui.GUINetMission
{

    protected void clientRender()
    {
        com.maddox.il2.gui.GUINetMission.DialogClient dialogclient = dialogClient;
        com.maddox.il2.gui.GUINetMission.DialogClient _tmp = dialogclient;
        dialogclient.draw(dialogclient.x1024(96F), dialogclient.y1024(464F), dialogclient.x1024(224F), dialogclient.y1024(48F), 0, i18n("miss.QuitMiss"));
    }

    protected void doReFly()
    {
    }

    protected void doExit()
    {
        com.maddox.il2.ai.Front.checkAllCaptured();
        com.maddox.il2.game.Mission.cur().doEnd();
        ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).sendStatInc();
        com.maddox.il2.gui.GUI.activate();
        com.maddox.il2.gui.GUI.pad.leave(true);
        com.maddox.il2.game.Main.stateStack().change(51);
    }

    public void tryExit()
    {
        doExit();
    }

    public GUINetServerCMission(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(49);
        init(gwindowroot);
        infoMenu.info = i18n("miss.NetSCInfo");
    }
}
