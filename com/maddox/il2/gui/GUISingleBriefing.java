// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUISingleBriefing.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.rts.CmdEnv;

// Referenced classes of package com.maddox.il2.gui:
//            GUIBriefing, GUIClient, GUIButton, GUIBriefingGeneric

public class GUISingleBriefing extends com.maddox.il2.gui.GUIBriefing
{

    public void enterPush(com.maddox.il2.game.GameState gamestate)
    {
        com.maddox.il2.ai.World.cur().diffUser.set(com.maddox.il2.ai.World.cur().userCfg.singleDifficulty);
        super.enterPush(gamestate);
        if(briefSound != null)
        {
            com.maddox.rts.CmdEnv.top().exec("music PUSH");
            com.maddox.rts.CmdEnv.top().exec("music LIST " + briefSound);
            com.maddox.rts.CmdEnv.top().exec("music PLAY");
        }
    }

    public void enterPop(com.maddox.il2.game.GameState gamestate)
    {
        if(gamestate.id() == 17)
        {
            com.maddox.il2.ai.World.cur().userCfg.singleDifficulty = com.maddox.il2.ai.World.cur().diffUser.get();
            com.maddox.il2.ai.World.cur().userCfg.saveConf();
        }
        client.activateWindow();
    }

    protected void doNext()
    {
        if(briefSound != null)
        {
            com.maddox.rts.CmdEnv.top().exec("music POP");
            com.maddox.rts.CmdEnv.top().exec("music STOP");
        }
        com.maddox.il2.game.Main.stateStack().change(5);
    }

    protected void doLoodout()
    {
        com.maddox.il2.game.Main.stateStack().push(54);
    }

    protected void doDiff()
    {
        com.maddox.il2.game.Main.stateStack().push(17);
    }

    protected void doBack()
    {
        if(briefSound != null)
        {
            com.maddox.rts.CmdEnv.top().exec("music POP");
            com.maddox.rts.CmdEnv.top().exec("music PLAY");
        }
        com.maddox.il2.game.Main.stateStack().pop();
    }

    protected void clientRender()
    {
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient dialogclient = dialogClient;
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp = dialogclient;
        dialogclient.draw(dialogclient.x1024(0.0F), dialogclient.y1024(633F), dialogclient.x1024(170F), dialogclient.y1024(48F), 1, i18n("brief.Back"));
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp1 = dialogclient;
        dialogclient.draw(dialogclient.x1024(194F), dialogclient.y1024(633F), dialogclient.x1024(208F), dialogclient.y1024(48F), 1, i18n("brief.Difficulty"));
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp2 = dialogclient;
        dialogclient.draw(dialogclient.x1024(680F), dialogclient.y1024(633F), dialogclient.x1024(176F), dialogclient.y1024(48F), 1, i18n("brief.Arming"));
        super.clientRender();
    }

    protected void clientSetPosSize()
    {
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient dialogclient = dialogClient;
        bLoodout.setPosC(dialogclient.x1024(768F), dialogclient.y1024(689F));
    }

    public GUISingleBriefing(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(4);
        init(gwindowroot);
    }
}
