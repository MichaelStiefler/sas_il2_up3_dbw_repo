// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIDGenBriefing.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.rts.CmdEnv;

// Referenced classes of package com.maddox.il2.gui:
//            GUIBriefing, GUIBriefingGeneric

public class GUIDGenBriefing extends com.maddox.il2.gui.GUIBriefing
{

    public void enter(com.maddox.il2.game.GameState gamestate)
    {
        curMissionNum = -1;
        super.enter(gamestate);
        if(gamestate != null && (gamestate.id() == 61 || gamestate.id() == 27 || gamestate.id() == 64) && briefSound != null)
        {
            com.maddox.rts.CmdEnv.top().exec("music PUSH");
            com.maddox.rts.CmdEnv.top().exec("music LIST " + briefSound);
            com.maddox.rts.CmdEnv.top().exec("music PLAY");
        }
    }

    protected void doLoodout()
    {
        com.maddox.il2.game.Main.stateStack().push(54);
    }

    protected void doDiff()
    {
        com.maddox.il2.game.Main.stateStack().push(65);
    }

    protected void doNext()
    {
        if(briefSound != null)
        {
            com.maddox.rts.CmdEnv.top().exec("music POP");
            com.maddox.rts.CmdEnv.top().exec("music STOP");
        }
        com.maddox.il2.ai.World.cur().diffUser.set(com.maddox.il2.game.Main.cur().campaign.difficulty());
        com.maddox.il2.game.Main.stateStack().change(63);
    }

    protected void doBack()
    {
        if(briefSound != null)
        {
            com.maddox.rts.CmdEnv.top().exec("music POP");
            com.maddox.rts.CmdEnv.top().exec("music PLAY");
        }
        com.maddox.il2.game.Main.cur().campaign = null;
        com.maddox.il2.game.Main.cur().currentMissionFile = null;
        com.maddox.il2.game.Main.stateStack().pop();
    }

    protected void clientRender()
    {
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient dialogclient = dialogClient;
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp = dialogclient;
        dialogclient.draw(dialogclient.x1024(0.0F), dialogclient.y1024(633F), dialogclient.x1024(170F), dialogclient.y1024(48F), 1, i18n("brief.MainMenu"));
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp1 = dialogclient;
        dialogclient.draw(dialogclient.x1024(194F), dialogclient.y1024(633F), dialogclient.x1024(208F), dialogclient.y1024(48F), 1, i18n("brief.Roster"));
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp2 = dialogclient;
        dialogclient.draw(dialogclient.x1024(680F), dialogclient.y1024(633F), dialogclient.x1024(176F), dialogclient.y1024(48F), 1, i18n("brief.Arming"));
        super.clientRender();
    }

    protected void clientSetPosSize()
    {
    }

    public GUIDGenBriefing(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(62);
        init(gwindowroot);
    }
}
