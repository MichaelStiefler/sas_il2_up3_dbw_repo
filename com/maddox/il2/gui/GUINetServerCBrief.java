// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUINetServerCBrief.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.USGS;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetEnv;
import com.maddox.rts.SectFile;

// Referenced classes of package com.maddox.il2.gui:
//            GUIBriefing, GUINetAircraft, GUINetServer, GUIButton, 
//            GUIBriefingGeneric

public class GUINetServerCBrief extends com.maddox.il2.gui.GUIBriefing
{

    public void enter(com.maddox.il2.game.GameState gamestate)
    {
        super.enter(gamestate);
        if(gamestate == null || gamestate.id() != 44)
        {
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setPilot(null);
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setSkin(null);
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setNoseart(null);
        }
        if(gamestate != null && briefSound != null)
            if(gamestate.id() == 69 || gamestate.id() == 38)
            {
                if(((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getArmy() != 0)
                {
                    java.lang.String s = com.maddox.il2.game.Main.cur().currentMissionFile.get("MAIN", "briefSound" + ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getArmy());
                    if(s != null)
                        briefSound = s;
                }
                com.maddox.rts.CmdEnv.top().exec("music PUSH");
                com.maddox.rts.CmdEnv.top().exec("music LIST " + briefSound);
                com.maddox.rts.CmdEnv.top().exec("music PLAY");
                _briefSound = briefSound;
            } else
            if(gamestate.id() == 44)
            {
                java.lang.String s1 = com.maddox.il2.game.Main.cur().currentMissionFile.get("MAIN", "briefSound" + ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getArmy());
                if(s1 == null)
                    s1 = com.maddox.il2.game.Main.cur().currentMissionFile.get("MAIN", "briefSound");
                if(s1 != null && !s1.equals(_briefSound))
                {
                    _briefSound = s1;
                    com.maddox.rts.CmdEnv.top().exec("music LIST " + _briefSound);
                }
            }
    }

    public void leave(com.maddox.il2.game.GameState gamestate)
    {
        if(gamestate != null && gamestate.id() == 47 && briefSound != null)
        {
            com.maddox.rts.CmdEnv.top().exec("music POP");
            com.maddox.rts.CmdEnv.top().exec("music STOP");
            briefSound = null;
            _briefSound = null;
        }
        super.leave(gamestate);
    }

    public void leavePop(com.maddox.il2.game.GameState gamestate)
    {
        if(gamestate != null && gamestate.id() == 2 && briefSound != null)
        {
            com.maddox.rts.CmdEnv.top().exec("music POP");
            com.maddox.rts.CmdEnv.top().exec("music PLAY");
        }
        super.leavePop(gamestate);
    }

    protected void fillTextDescription()
    {
        super.fillTextDescription();
        prepareTextDescription(com.maddox.il2.ai.Army.amountSingle());
    }

    protected java.lang.String textDescription()
    {
        if(textArmyDescription == null)
            return null;
        if(com.maddox.il2.gui.GUINetAircraft.isSelectedValid())
            return textArmyDescription[com.maddox.il2.gui.GUINetAircraft.selectedRegiment().getArmy()];
        else
            return textArmyDescription[0];
    }

    protected void doNext()
    {
        if(!com.maddox.il2.gui.GUINetAircraft.isSelectedValid())
        {
            com.maddox.il2.game.Main.stateStack().change(44);
            return;
        } else
        {
            com.maddox.il2.gui.GUINetAircraft.doFly();
            return;
        }
    }

    protected void doDiff()
    {
        com.maddox.il2.game.Main.stateStack().push(41);
    }

    protected void doLoodout()
    {
        com.maddox.il2.game.Main.stateStack().change(44);
    }

    protected void doBack()
    {
        com.maddox.il2.gui.GUINetServer.exitServer(true);
    }

    protected void clientRender()
    {
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient dialogclient = dialogClient;
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp = dialogclient;
        dialogclient.draw(dialogclient.x1024(15F), dialogclient.y1024(633F), dialogclient.x1024(140F), dialogclient.y1024(48F), 1, !com.maddox.il2.net.USGS.isUsed() && com.maddox.il2.game.Main.cur().netGameSpy == null ? i18n("brief.MainMenu") : i18n("main.Quit"));
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp1 = dialogclient;
        dialogclient.draw(dialogclient.x1024(194F), dialogclient.y1024(633F), dialogclient.x1024(208F), dialogclient.y1024(48F), 1, i18n("brief.Difficulty"));
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp2 = dialogclient;
        dialogclient.draw(dialogclient.x1024(680F), dialogclient.y1024(633F), dialogclient.x1024(176F), dialogclient.y1024(48F), 1, i18n("brief.Aircraft"));
        super.clientRender();
    }

    protected void clientSetPosSize()
    {
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient dialogclient = dialogClient;
        bLoodout.setPosC(dialogclient.x1024(768F), dialogclient.y1024(689F));
    }

    public GUINetServerCBrief(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(45);
        _briefSound = null;
        init(gwindowroot);
    }

    private java.lang.String _briefSound;
}
