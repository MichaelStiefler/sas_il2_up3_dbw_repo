// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUINetServerDMission.java

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

// Referenced classes of package com.maddox.il2.gui:
//            GUINetMission, GUIButton, GUI, GUIInfoMenu

public class GUINetServerDMission extends com.maddox.il2.gui.GUINetMission
{

    protected void clientRender()
    {
        com.maddox.il2.gui.GUINetMission.DialogClient dialogclient = dialogClient;
        if(bEnableReFly)
        {
            com.maddox.il2.gui.GUINetMission.DialogClient _tmp = dialogclient;
            dialogclient.draw(dialogclient.x1024(96F), dialogclient.y1024(400F), dialogclient.x1024(224F), dialogclient.y1024(48F), 0, i18n("miss.ReFly"));
        } else
        if(com.maddox.il2.game.Main.cur().mission.zutiMisc_EnableReflyOnlyIfBailedOrDied || com.maddox.il2.game.Main.cur().mission.zutiMisc_DisableReflyForMissionDuration)
            if(com.maddox.il2.game.ZutiSupportMethods.ZUTI_KIA_COUNTER > com.maddox.il2.game.Main.cur().mission.zutiMisc_MaxAllowedKIA || com.maddox.il2.game.Main.cur().mission.zutiMisc_DisableReflyForMissionDuration)
            {
                com.maddox.il2.gui.GUINetMission.DialogClient _tmp1 = dialogclient;
                dialogclient.draw(dialogclient.x1024(96F), dialogclient.y1024(400F), dialogclient.x1024(224F), dialogclient.y1024(48F), 0, i18n("miss.NoReFly"));
            } else
            if(!com.maddox.il2.game.ZutiSupportMethods.ZUTI_KIA_DELAY_CLEARED && reflyTimer != null && com.maddox.il2.ai.World.isPlayerDead())
            {
                java.lang.String s = reflyTimer.getRemaimingTime();
                com.maddox.il2.gui.GUINetMission.DialogClient _tmp2 = dialogclient;
                dialogclient.draw(dialogclient.x1024(96F), dialogclient.y1024(400F), dialogclient.x1024(224F), dialogclient.y1024(48F), 0, i18n("miss.ReFlyWait") + "   " + s);
                if(s.equals("00:00"))
                {
                    bEnableReFly = true;
                    bReFly.showWindow();
                    reflyTimer = null;
                }
            }
        com.maddox.il2.gui.GUINetMission.DialogClient _tmp3 = dialogclient;
        dialogclient.draw(dialogclient.x1024(96F), dialogclient.y1024(464F), dialogclient.x1024(224F), dialogclient.y1024(48F), 0, i18n("miss.QuitMiss"));
    }

    protected void doReFly()
    {
        checkCaptured();
        destroyPlayerActor();
        ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).sendStatInc();
        com.maddox.il2.ai.EventLog.onRefly(((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).shortName());
        com.maddox.sound.AudioDevice.soundsOff();
        com.maddox.il2.game.Main.stateStack().change(39);
    }

    protected void doExit()
    {
        checkCaptured();
        destroyPlayerActor();
        ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).sendStatInc();
        com.maddox.il2.game.Mission.cur().destroy();
        com.maddox.il2.gui.GUI.activate();
        com.maddox.il2.game.Main.stateStack().change(38);
    }

    public GUINetServerDMission(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(42);
        init(gwindowroot);
        infoMenu.info = i18n("miss.NetSDInfo");
    }
}
