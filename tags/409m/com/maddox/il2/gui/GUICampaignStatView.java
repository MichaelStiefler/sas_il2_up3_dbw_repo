// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUICampaignStatView.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.Scores;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.rts.RTSConf;
import java.util.ResourceBundle;

// Referenced classes of package com.maddox.il2.gui:
//            GUICampaignStat, GUIClient, GUIInfoMenu, GUIButton, 
//            GUIStat

public class GUICampaignStatView extends com.maddox.il2.gui.GUICampaignStat
{

    public void _enter()
    {
        com.maddox.il2.ai.Scores.enemyAirKill = com.maddox.il2.game.Main.cur().campaign.enemyAirDestroyed();
        com.maddox.il2.ai.Scores.friendlyKill = com.maddox.il2.game.Main.cur().campaign.friendDestroyed();
        completeMissions = com.maddox.il2.game.Main.cur().campaign.completeMissions();
        com.maddox.il2.ai.Scores.arrayEnemyGroundKill = com.maddox.il2.game.Main.cur().campaign.enemyGroundDestroyed();
        com.maddox.il2.ai.Scores.enemyGroundKill = 0;
        if(com.maddox.il2.ai.Scores.arrayEnemyGroundKill != null)
            com.maddox.il2.ai.Scores.enemyGroundKill = com.maddox.il2.ai.Scores.arrayEnemyGroundKill.length;
        com.maddox.il2.ai.Scores.score = com.maddox.il2.game.Main.cur().campaign.score();
        awards = com.maddox.il2.game.Main.cur().campaign.awards(com.maddox.il2.ai.Scores.score);
        if(awards == 0)
            bViewAward.hideWindow();
        else
            bViewAward.showWindow();
        int i = com.maddox.il2.game.Main.cur().campaign.rank();
        rank = "";
        try
        {
            java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle("missions/campaign/" + com.maddox.il2.game.Main.cur().campaign.branch() + "/" + "rank", com.maddox.rts.RTSConf.cur.locale);
            rank = resourcebundle.getString("" + i);
        }
        catch(java.lang.Exception exception) { }
        com.maddox.il2.ai.DifficultySettings difficultysettings = new DifficultySettings();
        difficultysettings.set(com.maddox.il2.game.Main.cur().campaign.difficulty());
        if(difficultysettings.isRealistic())
            diff = i18n("campstat.realistic");
        else
        if(difficultysettings.isNormal())
            diff = i18n("campstat.normal");
        else
        if(difficultysettings.isEasy())
            diff = i18n("campstat.easy");
        else
            diff = i18n("campstat.custom");
        iArmy = com.maddox.il2.game.Main.cur().campaign.army() - 1;
        updateScrollSizes();
        client.activateWindow();
    }

    public void leavePop(com.maddox.il2.game.GameState gamestate)
    {
        com.maddox.il2.game.Main.cur().campaign = null;
        _leave();
    }

    protected void doRecordSave()
    {
    }

    protected void doRefly()
    {
    }

    protected void doNext()
    {
    }

    protected void doExit()
    {
        com.maddox.il2.game.Main.stateStack().pop();
    }

    protected void clientRender2()
    {
        com.maddox.il2.gui.GUIStat.DialogClient dialogclient = dialogClient;
        com.maddox.il2.gui.GUIStat.DialogClient _tmp = dialogclient;
        dialogclient.draw(dialogclient.x1024(496F), dialogclient.y1024(656F), dialogclient.x1024(208F), dialogclient.y1024(48F), 0, i18n("campstat.Back"));
    }

    public GUICampaignStatView(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(31);
        init(gwindowroot);
        infoMenu.info = i18n("campstat.info");
        bViewAward = (com.maddox.il2.gui.GUICampaignStat.WAwardButton)dialogClient.addControl(new GUICampaignStat.WAwardButton(this, dialogClient));
        bSave.hideWindow();
        bNext.hideWindow();
        bRefly.hideWindow();
    }
}
