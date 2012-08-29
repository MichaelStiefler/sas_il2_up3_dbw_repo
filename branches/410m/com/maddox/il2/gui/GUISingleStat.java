// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUISingleStat.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.ScoreCounter;
import com.maddox.il2.ai.Scores;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.rts.KeyRecord;

// Referenced classes of package com.maddox.il2.gui:
//            GUIStat, GUIQuick, GUIQuickStats, GUISeparate, 
//            GUIButton, GUIInfoMenu

public class GUISingleStat extends com.maddox.il2.gui.GUIStat
{

    public void _enter()
    {
        com.maddox.il2.ai.DifficultySettings difficultysettings = com.maddox.il2.ai.World.cur().diffCur;
        if(difficultysettings.isRealistic())
            diff = i18n("singleStat.realistic");
        else
        if(difficultysettings.isNormal())
            diff = i18n("singleStat.normal");
        else
        if(difficultysettings.isEasy())
            diff = i18n("singleStat.easy");
        else
            diff = i18n("singleStat.custom");
        iArmy = com.maddox.il2.ai.World.getPlayerArmy() - 1;
        super._enter();
        tryShowCapturedMessage();
    }

    protected void doRefly()
    {
        com.maddox.il2.game.Main3D.cur3D().keyRecord.clearRecorded();
        com.maddox.il2.game.Main.stateStack().change(5);
    }

    protected void doNext()
    {
        com.maddox.il2.game.Main3D.cur3D().keyRecord.clearRecorded();
        com.maddox.il2.ai.ScoreCounter scorecounter = com.maddox.il2.ai.World.cur().scoreCounter;
        if(com.maddox.il2.gui.GUIQuick.isQMB())
            com.maddox.il2.gui.GUIQuickStats.qmbMissionComplete(scorecounter.bulletsFire, scorecounter.bulletsHitAir, scorecounter.bulletsHit, com.maddox.il2.ai.Scores.score, com.maddox.il2.ai.Scores.enemyAirKill, com.maddox.il2.ai.Scores.enemyGroundKill, com.maddox.il2.ai.Scores.arrayEnemyGroundKill, scorecounter.bPlayerDead, scorecounter.bPlayerParatrooper, scorecounter.bombFire, scorecounter.rocketsFire, scorecounter.rocketsHit, scorecounter.bombHit);
        if(com.maddox.il2.game.Mission.cur() != null && !com.maddox.il2.game.Mission.cur().isDestroyed())
            com.maddox.il2.game.Mission.cur().destroy();
        com.maddox.il2.game.Main.stateStack().pop();
    }

    protected void doExit()
    {
    }

    protected void clientRender()
    {
        com.maddox.il2.gui.GUIStat.DialogClient dialogclient = dialogClient;
        com.maddox.il2.gui.GUISeparate.draw(dialogclient, com.maddox.gwindow.GColor.Gray, dialogclient.x1024(32F), dialogclient.y1024(512F), dialogclient.x1024(672F), 2.0F);
        com.maddox.il2.gui.GUISeparate.draw(dialogclient, com.maddox.gwindow.GColor.Gray, dialogclient.x1024(416F), dialogclient.y1024(529F), 2.0F, dialogclient.y1024(176F));
        com.maddox.il2.gui.GUIStat.DialogClient _tmp = dialogclient;
        dialogclient.setCanvasFont(0);
        com.maddox.il2.gui.GUIStat.DialogClient _tmp1 = dialogclient;
        dialogclient.draw(dialogclient.x1024(48F), dialogclient.y1024(32F), dialogclient.x1024(640F), dialogclient.y1024(32F), 1, i18n("singleStat.airKills") + " " + com.maddox.il2.ai.Scores.enemyAirKill);
        com.maddox.il2.gui.GUIStat.DialogClient _tmp2 = dialogclient;
        dialogclient.draw(dialogclient.x1024(48F), dialogclient.y1024(176F), dialogclient.x1024(640F), dialogclient.y1024(32F), 1, i18n("singleStat.groundKills") + " " + com.maddox.il2.ai.Scores.enemyGroundKill);
        com.maddox.il2.gui.GUIStat.DialogClient _tmp3 = dialogclient;
        dialogclient.draw(dialogclient.x1024(32F), dialogclient.y1024(352F), dialogclient.x1024(352F), dialogclient.y1024(32F), 2, i18n("singleStat.score"));
        com.maddox.il2.gui.GUIStat.DialogClient _tmp4 = dialogclient;
        dialogclient.draw(dialogclient.x1024(400F), dialogclient.y1024(352F), dialogclient.x1024(300F), dialogclient.y1024(32F), 0, "" + com.maddox.il2.ai.Scores.score);
        com.maddox.il2.gui.GUIStat.DialogClient _tmp5 = dialogclient;
        dialogclient.draw(dialogclient.x1024(32F), dialogclient.y1024(400F), dialogclient.x1024(352F), dialogclient.y1024(32F), 2, i18n("singleStat.friendKills"));
        com.maddox.il2.gui.GUIStat.DialogClient _tmp6 = dialogclient;
        dialogclient.draw(dialogclient.x1024(400F), dialogclient.y1024(400F), dialogclient.x1024(300F), dialogclient.y1024(32F), 0, "" + com.maddox.il2.ai.Scores.friendlyKill);
        com.maddox.il2.gui.GUIStat.DialogClient _tmp7 = dialogclient;
        dialogclient.draw(dialogclient.x1024(32F), dialogclient.y1024(448F), dialogclient.x1024(352F), dialogclient.y1024(32F), 2, i18n("singleStat.diff"));
        com.maddox.il2.gui.GUIStat.DialogClient _tmp8 = dialogclient;
        dialogclient.draw(dialogclient.x1024(400F), dialogclient.y1024(448F), dialogclient.x1024(300F), dialogclient.y1024(32F), 0, diff);
        if(com.maddox.il2.net.NetMissionTrack.countRecorded == 0)
        {
            com.maddox.il2.gui.GUIStat.DialogClient _tmp9 = dialogclient;
            dialogclient.draw(dialogclient.x1024(496F), dialogclient.y1024(528F), dialogclient.x1024(208F), dialogclient.y1024(48F), 0, i18n("singleStat.SaveTrack"));
        }
        com.maddox.il2.gui.GUIStat.DialogClient _tmp10 = dialogclient;
        dialogclient.draw(dialogclient.x1024(496F), dialogclient.y1024(592F), dialogclient.x1024(208F), dialogclient.y1024(48F), 0, i18n("singleStat.Done"));
        com.maddox.il2.gui.GUIStat.DialogClient _tmp11 = dialogclient;
        dialogclient.draw(dialogclient.x1024(496F), dialogclient.y1024(656F), dialogclient.x1024(208F), dialogclient.y1024(48F), 0, i18n("singleStat.Refly"));
        dialogclient.draw(dialogclient.x1024(48F), dialogclient.y1024(560F), dialogclient.x1024(64F), dialogclient.y1024(80F), texStat);
    }

    protected void clientSetPosSize()
    {
        com.maddox.il2.gui.GUIStat.DialogClient dialogclient = dialogClient;
        dialogclient.set1024PosSize(144F, 32F, 736F, 736F);
        bSave.setPosC(dialogclient.x1024(456F), dialogclient.y1024(554F));
        bNext.setPosC(dialogclient.x1024(456F), dialogclient.y1024(617F));
        bRefly.setPosC(dialogclient.x1024(456F), dialogclient.y1024(680F));
        airScroll.set1024PosSize(32F, 80F, 672F, 80F);
        groundScroll.set1024PosSize(32F, 224F, 672F, 80F);
    }

    public GUISingleStat(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(6);
        init(gwindowroot);
        infoMenu.info = i18n("singleStat.info");
        bExit.hideWindow();
    }

    protected java.lang.String diff;
}
