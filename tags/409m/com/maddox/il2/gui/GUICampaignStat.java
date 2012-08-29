// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUICampaignStat.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.Scores;
import com.maddox.il2.ai.TargetsGuard;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.rts.KeyRecord;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.util.ResourceBundle;

// Referenced classes of package com.maddox.il2.gui:
//            GUIStat, GUILookAndFeel, GUIButton, GUIClient, 
//            GUIBWDemoPlay, GUISeparate, GUIInfoMenu, GUIAwards

public class GUICampaignStat extends com.maddox.il2.gui.GUIStat
{
    class WAwardButton extends com.maddox.gwindow.GWindowButton
    {

        public boolean notify(int i, int j)
        {
            if(i != 2)
                return super.notify(i, j);
            if(bMissComplete)
                com.maddox.il2.gui.GUIAwards.indexIcons = com.maddox.il2.game.Main.cur().campaign.getAwards(com.maddox.il2.ai.Scores.score);
            else
                com.maddox.il2.gui.GUIAwards.indexIcons = com.maddox.il2.game.Main.cur().campaign.getAwards(com.maddox.il2.game.Main.cur().campaign.score());
            com.maddox.il2.game.Main.stateStack().push(32);
            return true;
        }

        public void render()
        {
            super.render();
            if(lastAward != null)
            {
                setCanvasColorWHITE();
                int i = root.C.alpha;
                root.C.alpha = 255;
                if(bDown)
                    draw(win.dx / 5F + 1.0F, win.dy / 5F + 1.0F, (3F * win.dx) / 5F, (3F * win.dy) / 5F, lastAward);
                else
                    draw(win.dx / 5F, win.dy / 5F, (3F * win.dx) / 5F, (3F * win.dy) / 5F, lastAward);
                root.C.alpha = i;
            }
        }

        public WAwardButton(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
            cap = new GCaption(i18n("campstat.View"));
            align = 1;
        }
    }


    public void enterPush(com.maddox.il2.game.GameState gamestate)
    {
        enter(gamestate);
    }

    public void enterPop(com.maddox.il2.game.GameState gamestate)
    {
        enter(gamestate);
    }

    public void enter(com.maddox.il2.game.GameState gamestate)
    {
        if(gamestate != null && gamestate.id() == 58)
        {
            com.maddox.il2.game.Main.cur().currentMissionFile = com.maddox.il2.game.Main.cur().campaign.nextMission();
            if(com.maddox.il2.game.Main.cur().currentMissionFile == null)
            {
                new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("miss.Error"), i18n("miss.LoadFailed"), 3, 0.0F) {

                    public void result(int l1)
                    {
                        doExit();
                    }

                }
;
                return;
            } else
            {
                com.maddox.il2.game.Main.stateStack().change(28);
                return;
            }
        }
        if(gamestate != null && gamestate.id() != 29 && gamestate.id() != 63)
        {
            _enter();
            return;
        }
        lastAward = null;
        com.maddox.il2.ai.Scores.compute();
        updateScrollSizes();
        com.maddox.il2.ai.Scores.score += com.maddox.il2.game.Main.cur().campaign.score();
        com.maddox.il2.ai.Scores.enemyAirKill += com.maddox.il2.game.Main.cur().campaign.enemyAirDestroyed();
        com.maddox.il2.ai.Scores.friendlyKill += com.maddox.il2.game.Main.cur().campaign.friendDestroyed();
        completeMissions = com.maddox.il2.game.Main.cur().campaign.completeMissions();
        int ai[] = com.maddox.il2.game.Main.cur().campaign.enemyGroundDestroyed();
        if(ai != null)
        {
            if(com.maddox.il2.ai.Scores.arrayEnemyGroundKill != null)
            {
                int ai1[] = new int[ai.length + com.maddox.il2.ai.Scores.enemyGroundKill];
                int j = 0;
                for(int l = 0; l < ai.length; l++)
                    ai1[j++] = ai[l];

                for(int j1 = 0; j1 < com.maddox.il2.ai.Scores.arrayEnemyGroundKill.length; j1++)
                    ai1[j++] = com.maddox.il2.ai.Scores.arrayEnemyGroundKill[j1];

                com.maddox.il2.ai.Scores.arrayEnemyGroundKill = ai1;
            } else
            {
                com.maddox.il2.ai.Scores.arrayEnemyGroundKill = ai;
            }
            com.maddox.il2.ai.Scores.enemyGroundKill = com.maddox.il2.ai.Scores.arrayEnemyGroundKill.length;
        }
        if(!com.maddox.il2.engine.Actor.isAlive(com.maddox.il2.ai.World.getPlayerAircraft()))
            com.maddox.il2.game.Main.cur().campaign.incAircraftLost();
        int i = com.maddox.il2.game.Main.cur().campaign.newRank(com.maddox.il2.ai.Scores.score);
        int k = -1;
        if(!com.maddox.il2.ai.World.isPlayerDead() && !com.maddox.il2.ai.World.isPlayerCaptured() && (com.maddox.il2.ai.World.cur().targetsGuard.isTaskComplete() || (double)com.maddox.rts.Time.current() / 1000D / 60D > 20D || !com.maddox.il2.ai.World.cur().diffCur.NoInstantSuccess))
        {
            bMissComplete = true;
            bNext.showWindow();
            completeMissions++;
            int i1 = com.maddox.il2.game.Main.cur().campaign.rank();
            bNewRank = i != i1;
            awards = com.maddox.il2.game.Main.cur().campaign.awards(com.maddox.il2.ai.Scores.score);
            int k1 = com.maddox.il2.game.Main.cur().campaign.awards(com.maddox.il2.game.Main.cur().campaign.score());
            if(awards > k1)
                k = awards - 1;
        } else
        {
            bMissComplete = false;
            bNext.hideWindow();
            i = com.maddox.il2.game.Main.cur().campaign.rank();
            bNewRank = false;
            awards = com.maddox.il2.game.Main.cur().campaign.awards(com.maddox.il2.game.Main.cur().campaign.score());
        }
        blickTime = com.maddox.rts.Time.currentReal();
        bBlick = false;
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
        if(awards == 0)
            bViewAward.hideWindow();
        else
            bViewAward.showWindow();
        if(k >= 0)
        {
            int ai2[] = com.maddox.il2.game.Main.cur().campaign.getAwards(com.maddox.il2.ai.Scores.score);
            if(com.maddox.il2.game.Main.cur().campaign.branch().equals("de") && com.maddox.il2.ai.World.cur().isHakenAllowed())
                lastAward = com.maddox.gwindow.GTexture.New("missions/campaign/de/awardh" + ai2[k] + ".mat");
            else
            if(com.maddox.il2.game.Main.cur().campaign.branch().equals("fi") && com.maddox.il2.ai.World.cur().isHakenAllowed())
                lastAward = com.maddox.gwindow.GTexture.New("missions/campaign/fi/awardh" + ai2[k] + ".mat");
            else
                lastAward = com.maddox.gwindow.GTexture.New("missions/campaign/" + com.maddox.il2.game.Main.cur().campaign.branch() + "/award" + ai2[k] + ".mat");
        }
        updateScrollSizes();
        _enter();
        tryShowCapturedMessage();
    }

    public void _enter()
    {
        client.activateWindow();
    }

    private void saveCampaign()
    {
        com.maddox.il2.game.campaign.Campaign campaign = com.maddox.il2.game.Main.cur().campaign;
        try
        {
            java.lang.String s = campaign.branch() + campaign.missionsDir();
            java.lang.String s1 = "users/" + com.maddox.il2.ai.World.cur().userCfg.sId + "/campaigns.ini";
            com.maddox.rts.SectFile sectfile = new SectFile(s1, 1, false, com.maddox.il2.ai.World.cur().userCfg.krypto());
            if(bMissComplete)
                if(campaign.isComplete())
                    campaign.clearSavedStatics(sectfile);
                else
                    campaign.saveStatics(sectfile);
            sectfile.set("list", s, campaign, true);
            sectfile.saveFile();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
            return;
        }
    }

    protected void doRefly()
    {
        lastAward = null;
        com.maddox.il2.game.Main3D.cur3D().keyRecord.clearRecorded();
        com.maddox.il2.game.Main.stateStack().change(29);
    }

    protected void doNext()
    {
        if(!bMissComplete)
            return;
        lastAward = null;
        com.maddox.il2.game.Main.cur().campaign.currentMissionComplete(com.maddox.il2.ai.Scores.score, com.maddox.il2.ai.Scores.enemyAirKill, com.maddox.il2.ai.Scores.arrayEnemyGroundKill, com.maddox.il2.ai.Scores.friendlyKill);
        if(com.maddox.il2.game.Main.cur().campaign.isComplete())
        {
            java.lang.String s = com.maddox.il2.game.Main.cur().campaign.epilogueTrack();
            doExit();
            if(s != null)
            {
                com.maddox.il2.gui.GUIBWDemoPlay.demoFile = s;
                com.maddox.il2.gui.GUIBWDemoPlay.soundFile = null;
                com.maddox.il2.game.Main.stateStack().push(58);
            }
        } else
        {
            saveCampaign();
            com.maddox.il2.game.Main3D.cur3D().keyRecord.clearRecorded();
            if(com.maddox.il2.game.Mission.cur() != null && !com.maddox.il2.game.Mission.cur().isDestroyed())
                com.maddox.il2.game.Mission.cur().destroy();
            com.maddox.il2.game.Main.cur().campaign.doExternalGenerator();
            java.lang.String s1 = com.maddox.il2.game.Main.cur().campaign.nextIntro();
            if(s1 != null)
            {
                com.maddox.il2.gui.GUIBWDemoPlay.demoFile = s1;
                com.maddox.il2.gui.GUIBWDemoPlay.soundFile = null;
                com.maddox.il2.game.Main.stateStack().push(58);
                return;
            }
            com.maddox.il2.game.Main.cur().currentMissionFile = com.maddox.il2.game.Main.cur().campaign.nextMission();
            if(com.maddox.il2.game.Main.cur().currentMissionFile == null)
            {
                new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("miss.Error"), i18n("miss.LoadFailed"), 3, 0.0F) {

                    public void result(int i)
                    {
                        doExit();
                    }

                }
;
                return;
            }
            com.maddox.il2.game.Main.stateStack().change(28);
        }
    }

    protected void doExit()
    {
        lastAward = null;
        saveCampaign();
        com.maddox.il2.game.Main3D.cur3D().keyRecord.clearRecorded();
        if(com.maddox.il2.game.Mission.cur() != null && !com.maddox.il2.game.Mission.cur().isDestroyed())
            com.maddox.il2.game.Mission.cur().destroy();
        com.maddox.il2.game.Main.cur().campaign = null;
        com.maddox.il2.game.Main.cur().currentMissionFile = null;
        com.maddox.il2.game.Main.stateStack().pop();
    }

    protected void clientRender()
    {
        com.maddox.il2.gui.GUIStat.DialogClient dialogclient = dialogClient;
        com.maddox.il2.gui.GUILookAndFeel guilookandfeel = (com.maddox.il2.gui.GUILookAndFeel)dialogclient.lookAndFeel();
        com.maddox.il2.gui.GUISeparate.draw(dialogclient, com.maddox.gwindow.GColor.Gray, dialogclient.x1024(32F), dialogclient.y1024(448F), dialogclient.x1024(672F), 2.0F);
        com.maddox.il2.gui.GUISeparate.draw(dialogclient, com.maddox.gwindow.GColor.Gray, dialogclient.x1024(416F), dialogclient.y1024(464F), 2.0F, dialogclient.y1024(240F));
        dialogclient.setCanvasColor(com.maddox.gwindow.GColor.Gray);
        com.maddox.il2.gui.GUIStat.DialogClient _tmp = dialogclient;
        dialogclient.setCanvasFont(0);
        com.maddox.il2.gui.GUIStat.DialogClient _tmp1 = dialogclient;
        dialogclient.draw(dialogclient.x1024(48F), dialogclient.y1024(32F), dialogclient.x1024(640F), dialogclient.y1024(32F), 1, i18n("campstat.AirKills") + " " + com.maddox.il2.ai.Scores.enemyAirKill);
        com.maddox.il2.gui.GUIStat.DialogClient _tmp2 = dialogclient;
        dialogclient.draw(dialogclient.x1024(48F), dialogclient.y1024(160F), dialogclient.x1024(640F), dialogclient.y1024(32F), 1, i18n("campstat.GroundKills") + " " + com.maddox.il2.ai.Scores.enemyGroundKill);
        com.maddox.il2.gui.GUIStat.DialogClient _tmp3 = dialogclient;
        dialogclient.draw(dialogclient.x1024(80F), dialogclient.y1024(288F), dialogclient.x1024(256F), dialogclient.y1024(32F), 1, i18n("campstat.RANK"));
        guilookandfeel.drawBevel(dialogclient, dialogclient.x1024(32F), dialogclient.y1024(320F), dialogclient.x1024(352F), dialogclient.y1024(32F), guilookandfeel.bevelComboDown, guilookandfeel.basicelements);
        if(bNewRank)
        {
            long l = com.maddox.rts.Time.currentReal();
            long l1 = l - blickTime;
            if(l1 > 1000L)
            {
                bBlick = !bBlick;
                blickTime = l;
            }
            if(bBlick)
                dialogclient.setCanvasColor(65535);
        }
        com.maddox.il2.gui.GUIStat.DialogClient _tmp4 = dialogclient;
        dialogclient.draw(dialogclient.x1024(32F) + guilookandfeel.bevelComboDown.L.dx, dialogclient.y1024(320F), dialogclient.x1024(352F) - guilookandfeel.bevelComboDown.L.dx - guilookandfeel.bevelComboDown.R.dx, dialogclient.y1024(32F), 1, rank);
        if(bBlick)
            dialogclient.setCanvasColor(com.maddox.gwindow.GColor.Gray);
        com.maddox.il2.gui.GUIStat.DialogClient _tmp5 = dialogclient;
        dialogclient.draw(dialogclient.x1024(80F), dialogclient.y1024(368F), dialogclient.x1024(256F), dialogclient.y1024(32F), 1, i18n("campstat.SCORE"));
        guilookandfeel.drawBevel(dialogclient, dialogclient.x1024(32F), dialogclient.y1024(400F), dialogclient.x1024(352F), dialogclient.y1024(32F), guilookandfeel.bevelComboDown, guilookandfeel.basicelements);
        com.maddox.il2.gui.GUIStat.DialogClient _tmp6 = dialogclient;
        dialogclient.draw(dialogclient.x1024(32F), dialogclient.y1024(400F), dialogclient.x1024(352F), dialogclient.y1024(32F), 1, "" + com.maddox.il2.ai.Scores.score);
        com.maddox.il2.gui.GUIStat.DialogClient _tmp7 = dialogclient;
        dialogclient.draw(dialogclient.x1024(416F), dialogclient.y1024(288F), dialogclient.x1024(288F), dialogclient.y1024(32F), 1, i18n("campstat.AWARDS"));
        if(awards == 0)
            guilookandfeel.drawBevel(dialogclient, dialogclient.x1024(480F), dialogclient.y1024(320F), dialogclient.x1024(160F), dialogclient.y1024(112F), guilookandfeel.bevelComboDown, guilookandfeel.basicelements);
        com.maddox.il2.gui.GUIStat.DialogClient _tmp8 = dialogclient;
        dialogclient.draw(dialogclient.x1024(32F), dialogclient.y1024(498F), dialogclient.x1024(256F), dialogclient.y1024(32F), 2, i18n("campstat.Total"));
        com.maddox.il2.gui.GUIStat.DialogClient _tmp9 = dialogclient;
        dialogclient.draw(dialogclient.x1024(304F), dialogclient.y1024(496F), dialogclient.x1024(96F), dialogclient.y1024(32F), 0, "" + com.maddox.il2.game.Main.cur().campaign.completeMissions());
        com.maddox.il2.gui.GUIStat.DialogClient _tmp10 = dialogclient;
        dialogclient.draw(dialogclient.x1024(32F), dialogclient.y1024(544F), dialogclient.x1024(256F), dialogclient.y1024(32F), 2, i18n("campstat.Lost"));
        com.maddox.il2.gui.GUIStat.DialogClient _tmp11 = dialogclient;
        dialogclient.draw(dialogclient.x1024(304F), dialogclient.y1024(544F), dialogclient.x1024(96F), dialogclient.y1024(32F), 0, "" + com.maddox.il2.game.Main.cur().campaign.aircraftLost());
        com.maddox.il2.gui.GUIStat.DialogClient _tmp12 = dialogclient;
        dialogclient.draw(dialogclient.x1024(32F), dialogclient.y1024(592F), dialogclient.x1024(256F), dialogclient.y1024(32F), 2, i18n("campstat.Friendly_kills"));
        com.maddox.il2.gui.GUIStat.DialogClient _tmp13 = dialogclient;
        dialogclient.draw(dialogclient.x1024(304F), dialogclient.y1024(592F), dialogclient.x1024(96F), dialogclient.y1024(32F), 0, "" + com.maddox.il2.ai.Scores.friendlyKill);
        com.maddox.il2.gui.GUIStat.DialogClient _tmp14 = dialogclient;
        dialogclient.draw(dialogclient.x1024(32F), dialogclient.y1024(640F), dialogclient.x1024(160F), dialogclient.y1024(32F), 2, i18n("campstat.Difficulty"));
        guilookandfeel.drawBevel(dialogclient, dialogclient.x1024(208F), dialogclient.y1024(640F), dialogclient.x1024(192F), dialogclient.y1024(32F), guilookandfeel.bevelComboDown, guilookandfeel.basicelements);
        com.maddox.il2.gui.GUIStat.DialogClient _tmp15 = dialogclient;
        dialogclient.draw(dialogclient.x1024(208F) + guilookandfeel.bevelComboDown.L.dx, dialogclient.y1024(640F), dialogclient.x1024(192F) - guilookandfeel.bevelComboDown.L.dx - guilookandfeel.bevelComboDown.R.dx, dialogclient.y1024(32F), 0, diff);
        clientRender2();
    }

    protected void clientRender2()
    {
        com.maddox.il2.gui.GUIStat.DialogClient dialogclient = dialogClient;
        if(com.maddox.il2.net.NetMissionTrack.countRecorded == 0)
        {
            com.maddox.il2.gui.GUIStat.DialogClient _tmp = dialogclient;
            dialogclient.draw(dialogclient.x1024(496F), dialogclient.y1024(464F), dialogclient.x1024(208F), dialogclient.y1024(48F), 0, i18n("campstat.SaveTrack"));
        }
        if(bMissComplete)
        {
            com.maddox.il2.gui.GUIStat.DialogClient _tmp1 = dialogclient;
            dialogclient.draw(dialogclient.x1024(496F), dialogclient.y1024(528F), dialogclient.x1024(208F), dialogclient.y1024(48F), 0, i18n("campstat.Apply"));
        }
        com.maddox.il2.gui.GUIStat.DialogClient _tmp2 = dialogclient;
        dialogclient.draw(dialogclient.x1024(496F), dialogclient.y1024(592F), dialogclient.x1024(208F), dialogclient.y1024(48F), 0, i18n("campstat.Refly"));
        com.maddox.il2.gui.GUIStat.DialogClient _tmp3 = dialogclient;
        dialogclient.draw(dialogclient.x1024(496F), dialogclient.y1024(656F), dialogclient.x1024(208F), dialogclient.y1024(48F), 0, i18n("campstat.MainMenu"));
    }

    protected void clientSetPosSize()
    {
        com.maddox.il2.gui.GUIStat.DialogClient dialogclient = dialogClient;
        dialogclient.set1024PosSize(144F, 32F, 736F, 736F);
        airScroll.set1024PosSize(32F, 64F, 672F, 80F);
        groundScroll.set1024PosSize(32F, 192F, 672F, 80F);
        bViewAward.set1024PosSize(480F, 320F, 160F, 112F);
        bSave.setPosC(dialogclient.x1024(456F), dialogclient.y1024(488F));
        bNext.setPosC(dialogclient.x1024(456F), dialogclient.y1024(552F));
        bRefly.setPosC(dialogclient.x1024(456F), dialogclient.y1024(617F));
        bExit.setPosC(dialogclient.x1024(456F), dialogclient.y1024(680F));
    }

    public GUICampaignStat(int i)
    {
        super(i);
        bMissComplete = false;
        rank = "";
        bNewRank = false;
        bBlick = false;
    }

    public GUICampaignStat(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(30);
        bMissComplete = false;
        rank = "";
        bNewRank = false;
        bBlick = false;
        init(gwindowroot);
        infoMenu.info = i18n("campstat.info");
        bViewAward = (com.maddox.il2.gui.WAwardButton)dialogClient.addControl(new WAwardButton(dialogClient));
    }

    public com.maddox.il2.gui.WAwardButton bViewAward;
    protected com.maddox.gwindow.GTexture lastAward;
    protected boolean bMissComplete;
    protected int completeMissions;
    protected java.lang.String rank;
    protected java.lang.String diff;
    protected int awards;
    protected boolean bNewRank;
    protected boolean bBlick;
    protected long blickTime;
}
