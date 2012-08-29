// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIDGenDeBriefing.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GPoint;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.Scores;
import com.maddox.il2.ai.TargetsGuard;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.GUIRenders;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.KeyRecord;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSReader;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import com.maddox.util.SharedTokenizer;
import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Referenced classes of package com.maddox.il2.gui:
//            GUIDeBriefing, GUIDGenRoster, GUIClient, GUIButton, 
//            GUIBWDemoPlay, GUIBriefingGeneric

public class GUIDGenDeBriefing extends com.maddox.il2.gui.GUIDeBriefing
{

    public void enter(com.maddox.il2.game.GameState gamestate)
    {
        super.enter(gamestate);
        if(sound != null)
        {
            com.maddox.rts.CmdEnv.top().exec("music PUSH");
            com.maddox.rts.CmdEnv.top().exec("music LIST " + sound);
            com.maddox.rts.CmdEnv.top().exec("music PLAY");
        }
    }

    public void leave(com.maddox.il2.game.GameState gamestate)
    {
        if(sound != null)
        {
            com.maddox.rts.CmdEnv.top().exec("music POP");
            com.maddox.rts.CmdEnv.top().exec("music STOP");
            sound = null;
        }
        super.leave(gamestate);
    }

    public void enterPop(com.maddox.il2.game.GameState gamestate)
    {
        if(gamestate.id() == 58)
        {
            com.maddox.il2.game.Main.cur().currentMissionFile = com.maddox.il2.game.Main.cur().campaign.nextMission();
            if(com.maddox.il2.game.Main.cur().currentMissionFile == null)
            {
                new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("miss.Error"), i18n("miss.LoadFailed"), 3, 0.0F) {

                    public void result(int i)
                    {
                        doBack();
                    }

                }
;
                return;
            } else
            {
                com.maddox.il2.game.Main.stateStack().change(62);
                return;
            }
        }
        if(sound != null)
        {
            com.maddox.rts.CmdEnv.top().exec("music POP");
            com.maddox.rts.CmdEnv.top().exec("music PLAY");
            sound = null;
        }
        client.activateWindow();
    }

    public void _enter()
    {
        preparePilotAssociation();
        prepareTypeAssociation();
        if(com.maddox.il2.net.NetMissionTrack.countRecorded == 0)
            bDifficulty.showWindow();
        else
            bDifficulty.hideWindow();
        com.maddox.il2.ai.Scores.compute();
        doExternalDebrifingGenerator();
        com.maddox.il2.ai.Scores.score += com.maddox.il2.game.Main.cur().campaign.score();
        com.maddox.il2.ai.Scores.enemyAirKill += com.maddox.il2.game.Main.cur().campaign.enemyAirDestroyed();
        com.maddox.il2.ai.Scores.friendlyKill += com.maddox.il2.game.Main.cur().campaign.friendDestroyed();
        int ai[] = com.maddox.il2.game.Main.cur().campaign.enemyGroundDestroyed();
        if(ai != null)
        {
            if(com.maddox.il2.ai.Scores.arrayEnemyGroundKill != null)
            {
                int ai1[] = new int[ai.length + com.maddox.il2.ai.Scores.enemyGroundKill];
                int i = 0;
                for(int j = 0; j < ai.length; j++)
                    ai1[i++] = ai[j];

                for(int k = 0; k < com.maddox.il2.ai.Scores.arrayEnemyGroundKill.length; k++)
                    ai1[i++] = com.maddox.il2.ai.Scores.arrayEnemyGroundKill[k];

                com.maddox.il2.ai.Scores.arrayEnemyGroundKill = ai1;
            } else
            {
                com.maddox.il2.ai.Scores.arrayEnemyGroundKill = ai;
            }
            com.maddox.il2.ai.Scores.enemyGroundKill = com.maddox.il2.ai.Scores.arrayEnemyGroundKill.length;
        }
        if(!com.maddox.il2.engine.Actor.isAlive(com.maddox.il2.ai.World.getPlayerAircraft()))
            com.maddox.il2.game.Main.cur().campaign.incAircraftLost();
        if(!com.maddox.il2.ai.World.isPlayerDead() && !com.maddox.il2.ai.World.isPlayerCaptured() && (com.maddox.il2.ai.World.cur().targetsGuard.isTaskComplete() || (double)com.maddox.rts.Time.current() / 1000D / 60D > 20D || !com.maddox.il2.ai.World.cur().diffCur.NoInstantSuccess))
        {
            bMissComplete = true;
            bNext.showWindow();
        } else
        {
            bMissComplete = false;
            bNext.hideWindow();
        }
        super._enter();
    }

    private void doExternalDebrifingGenerator()
    {
        java.lang.String s = "DGen.exe";
        com.maddox.il2.game.campaign.Campaign campaign = com.maddox.il2.game.Main.cur().campaign;
        try
        {
            java.lang.String s1 = "debrief missions/campaign/" + campaign.branch() + "/" + campaign.missionsDir() + "/ " + campaign.difficulty() + " " + com.maddox.il2.ai.Scores.score + " " + campaign.rank() + " " + "dgen/debrifing.txt";
            java.lang.Runtime runtime = java.lang.Runtime.getRuntime();
            java.lang.Process process = runtime.exec(s + " " + s1);
            process.waitFor();
        }
        catch(java.lang.Throwable throwable)
        {
            java.lang.System.out.println(throwable.getMessage());
            throwable.printStackTrace();
        }
    }

    private void saveCampaign()
    {
        saveCampaign(true);
    }

    private void saveCampaign(boolean flag)
    {
        com.maddox.il2.game.campaign.Campaign campaign = com.maddox.il2.game.Main.cur().campaign;
        try
        {
            java.lang.String s = campaign.branch() + campaign.missionsDir();
            java.lang.String s1 = "users/" + com.maddox.il2.ai.World.cur().userCfg.sId + "/campaigns.ini";
            com.maddox.rts.SectFile sectfile = new SectFile(s1, 1, false, com.maddox.il2.ai.World.cur().userCfg.krypto());
            if(flag && bMissComplete)
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

    protected void fillTextDescription()
    {
        java.io.BufferedReader bufferedreader = null;
        textDescription = null;
        sound = null;
        try
        {
            bufferedreader = new BufferedReader(new SFSReader("dgen/debrifing.txt", com.maddox.rts.RTSConf.charEncoding));
            java.lang.StringBuffer stringbuffer = null;
            do
            {
                java.lang.String s = bufferedreader.readLine();
                if(s == null)
                    break;
                int i = s.length();
                if(i != 0)
                    if(s.startsWith("SOUND "))
                    {
                        sound = s.substring("SOUND ".length());
                    } else
                    {
                        s = com.maddox.util.UnicodeTo8bit.load(s, false);
                        if(stringbuffer == null)
                        {
                            stringbuffer = new StringBuffer(s);
                        } else
                        {
                            stringbuffer.append('\n');
                            stringbuffer.append(s);
                        }
                    }
            } while(true);
            bufferedreader.close();
            if(stringbuffer != null)
                textDescription = stringbuffer.toString();
        }
        catch(java.lang.Exception exception)
        {
            if(bufferedreader != null)
                try
                {
                    bufferedreader.close();
                }
                catch(java.lang.Exception exception1) { }
            java.lang.System.out.println("Debrifing text load failed: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void updatePlayerScore()
    {
        java.io.BufferedReader bufferedreader = null;
        try
        {
            com.maddox.il2.game.campaign.Campaign campaign = com.maddox.il2.game.Main.cur().campaign;
            java.lang.String s = "missions/campaign/" + campaign.branch() + "/" + campaign.missionsDir() + "/squadron.dat";
            bufferedreader = new BufferedReader(new SFSReader(s, com.maddox.rts.RTSConf.charEncoding));
            com.maddox.il2.gui.GUIDGenRoster guidgenroster = (com.maddox.il2.gui.GUIDGenRoster)com.maddox.il2.game.GameState.get(65);
            com.maddox.il2.gui.GUIDGenRoster.Pilot pilot = guidgenroster.loadPilot(bufferedreader);
            bufferedreader.close();
            campaign._rank = pilot.rank;
            campaign._nawards = pilot.nmedals;
            saveCampaign(false);
        }
        catch(java.lang.Exception exception)
        {
            if(bufferedreader != null)
                try
                {
                    bufferedreader.close();
                }
                catch(java.lang.Exception exception1) { }
            java.lang.System.out.println("Squadron file load failed: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void preparePilotAssociation()
    {
        assPilot.clear();
        com.maddox.il2.gui.GUIDGenRoster guidgenroster = (com.maddox.il2.gui.GUIDGenRoster)com.maddox.il2.game.GameState.get(65);
        guidgenroster.loadPilotList();
        if(guidgenroster.pilotPlayer == null)
            return;
        try
        {
            com.maddox.il2.game.campaign.Campaign campaign = com.maddox.il2.game.Main.cur().campaign;
            java.lang.String s = "missions/campaign/" + campaign.branch() + "/" + campaign.missionsDir() + "/status.dat";
            java.io.BufferedReader bufferedreader = new BufferedReader(new SFSReader(s, com.maddox.rts.RTSConf.charEncoding));
            for(int i = 0; i < 9; i++)
                bufferedreader.readLine();

            do
            {
                if(!bufferedreader.ready())
                    break;
                java.lang.String s1 = bufferedreader.readLine();
                if(s1 == null)
                    break;
                int j = s1.length();
                if(j != 0)
                {
                    s1 = com.maddox.util.UnicodeTo8bit.load(s1, false);
                    com.maddox.util.SharedTokenizer.set(s1);
                    java.lang.String s2 = com.maddox.util.SharedTokenizer.next();
                    java.lang.String s3 = com.maddox.util.SharedTokenizer.next();
                    java.lang.String s4 = com.maddox.util.SharedTokenizer.next();
                    java.lang.String s5 = com.maddox.util.SharedTokenizer.getGap();
                    if(s5 != null && !"None,None".equals(s5))
                    {
                        for(int k = 0; k < guidgenroster.pilots.size(); k++)
                        {
                            com.maddox.il2.gui.GUIDGenRoster.Pilot pilot = (com.maddox.il2.gui.GUIDGenRoster.Pilot)guidgenroster.pilots.get(k);
                            java.lang.String s6 = pilot.lastName + "," + pilot.firstName;
                            if(s5.equals(s6))
                                assPilot.put(s3, pilot.sRank + " " + pilot.lastName);
                        }

                    }
                }
            } while(true);
            bufferedreader.close();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("Status file load failed: " + exception.getMessage());
            exception.printStackTrace();
            com.maddox.il2.game.Main.stateStack().pop();
        }
    }

    private void prepareTypeAssociation()
    {
        assType.clear();
        com.maddox.rts.SectFile sectfile = com.maddox.il2.game.Mission.cur().sectFile();
        int i = sectfile.sectionIndex("Wing");
        if(i < 0)
            return;
        int j = sectfile.vars(i);
        for(int k = 0; k < j; k++)
        {
            java.lang.String s = sectfile.var(i, k);
            int l = sectfile.sectionIndex(s);
            if(l < 0)
                continue;
            int i1 = sectfile.get(s, "Planes", 1, 1, 4);
            java.lang.String s1 = sectfile.get(s, "Class", (java.lang.String)null);
            if(s1 == null)
                continue;
            java.lang.Class class1 = null;
            try
            {
                class1 = com.maddox.rts.ObjIO.classForName(s1);
            }
            catch(java.lang.Exception exception)
            {
                continue;
            }
            java.lang.String s2 = com.maddox.rts.Property.stringValue(class1, "keyName", null);
            if(s2 != null)
            {
                java.lang.String s3 = com.maddox.il2.game.I18N.plane(s2);
                for(int j1 = 0; j1 < i1; j1++)
                    assType.put(s + j1, s3);

            }
        }

    }

    private void drawEvents()
    {
        com.maddox.gwindow.GPoint gpoint = renders.getMouseXY();
        int i = -1;
        float f = gpoint.x;
        float f1 = renders.win.dy - 1.0F - gpoint.y;
        float f2 = com.maddox.il2.engine.IconDraw.scrSizeX() / 2;
        float f3 = f;
        float f4 = f1;
        java.util.ArrayList arraylist = com.maddox.il2.ai.EventLog.actions;
        int j = arraylist.size();
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.engine.Mat mat = null;
            com.maddox.il2.ai.EventLog.Action action1 = (com.maddox.il2.ai.EventLog.Action)arraylist.get(k);
            switch(action1.event)
            {
            case 0: // '\0'
                assPilot.put(action1.arg0, action1.arg1);
                break;

            case 2: // '\002'
            case 3: // '\003'
            case 4: // '\004'
                switch(action1.scoreItem0)
                {
                case 0: // '\0'
                    mat = iconAction[4];
                    break;

                case 8: // '\b'
                    mat = iconAction[12];
                    break;

                case 1: // '\001'
                    mat = iconAction[9];
                    break;

                case 2: // '\002'
                    mat = iconAction[3];
                    break;

                case 3: // '\003'
                    mat = iconAction[5];
                    break;

                case 4: // '\004'
                    mat = iconAction[0];
                    break;

                case 5: // '\005'
                    mat = iconAction[2];
                    break;

                case 6: // '\006'
                    mat = iconAction[10];
                    break;

                case 7: // '\007'
                    mat = iconAction[8];
                    break;
                }
                break;

            case 5: // '\005'
                if(action1.argi == 0 && assPilot.get(action1.arg0) != null)
                    mat = iconAction[1];
                break;

            case 6: // '\006'
                if(action1.argi == 0 && assPilot.get(action1.arg0) != null)
                    mat = iconAction[11];
                break;

            case 7: // '\007'
                if(action1.argi == 0 && assPilot.get(action1.arg0) != null)
                    mat = iconAction[6];
                break;
            }
            if(mat != null)
            {
                float f5 = (float)(((double)action1.x - cameraMap2D.worldXOffset) * cameraMap2D.worldScale);
                float f7 = (float)(((double)action1.y - cameraMap2D.worldYOffset) * cameraMap2D.worldScale);
                com.maddox.il2.engine.IconDraw.setColor(0xff000000 | com.maddox.il2.ai.Army.color(action1.army0));
                com.maddox.il2.engine.IconDraw.render(mat, f5, f7);
                if(f5 >= f - f2 && f5 <= f + f2 && f7 >= f1 - f2 && f7 <= f1 + f2)
                {
                    i = k;
                    f3 = f5;
                    f4 = f7;
                }
            }
        }

        if(i >= 0)
        {
            com.maddox.il2.ai.EventLog.Action action = (com.maddox.il2.ai.EventLog.Action)arraylist.get(i);
            for(int l = 0; l < 4; l++)
                tip[l] = null;

            switch(action.event)
            {
            case 2: // '\002'
                if(action.scoreItem0 == 0)
                    tip[1] = getAss(action.arg0);
                else
                    tip[1] = getByScoreItem(action.scoreItem0, action.arg0);
                tip[2] = i18n("debrief.Crashed");
                break;

            case 3: // '\003'
                tip[1] = getAss(action.arg0);
                java.lang.String s = getAss(action.arg1);
                if(s != null)
                {
                    tip[2] = i18n("debrief.ShotDownBy");
                    tip[3] = s;
                } else
                {
                    tip[2] = i18n("debrief.ShotDown");
                }
                break;

            case 4: // '\004'
                tip[1] = getByScoreItem(action.scoreItem0, action.arg0);
                java.lang.String s1 = (java.lang.String)assPilot.get(action.arg1);
                if(s1 != null)
                {
                    tip[2] = i18n("debrief.DestroyedBy");
                    tip[3] = s1;
                } else
                {
                    tip[2] = i18n("debrief.Destroyed");
                }
                break;

            case 5: // '\005'
                java.lang.String s2 = getAss(action.arg0);
                if(s2 != null)
                {
                    tip[1] = s2;
                    tip[2] = i18n("debrief.BailedOut");
                }
                break;

            case 6: // '\006'
                java.lang.String s3 = getAss(action.arg0);
                if(s3 != null)
                {
                    tip[1] = s3;
                    tip[2] = i18n("debrief.WasKilled");
                }
                break;

            case 7: // '\007'
                java.lang.String s4 = getAss(action.arg0);
                if(s4 != null)
                {
                    tip[1] = s4;
                    tip[2] = i18n("debrief.Landed");
                }
                break;
            }
            if(tip[1] != null)
            {
                tip[0] = com.maddox.il2.ai.EventLog.logOnTime(action.time).toString();
                float f6 = gridFont.width(tip[0]);
                int i1 = 1;
                for(int j1 = 1; j1 < 4; j1++)
                {
                    if(tip[j1] == null)
                        break;
                    i1 = j1;
                    float f8 = gridFont.width(tip[j1]);
                    if(f6 < f8)
                        f6 = f8;
                }

                float f9 = -gridFont.descender();
                float f10 = (float)gridFont.height() + f9;
                f6 += 2.0F * f9;
                float f11 = f10 * (float)(i1 + 1) + 2.0F * f9;
                float f12 = f3 - f6 / 2.0F;
                float f13 = f4 + f2;
                if(f12 + f6 > renders.win.dx)
                    f12 = renders.win.dx - f6;
                if(f13 + f11 > renders.win.dy)
                    f13 = renders.win.dy - f11;
                if(f12 < 0.0F)
                    f12 = 0.0F;
                if(f13 < 0.0F)
                    f13 = 0.0F;
                com.maddox.il2.engine.Render.drawTile(f12, f13, f6, f11, 0.0F, emptyMat, 0xcf7fffff, 0.0F, 0.0F, 1.0F, 1.0F);
                com.maddox.il2.engine.Render.drawEnd();
                for(int k1 = 0; k1 <= i1; k1++)
                    gridFont.output(0xff000000, f12 + f9, f13 + f9 + (float)(i1 - k1) * f10 + f9, 0.0F, tip[k1]);

            }
        }
    }

    private java.lang.String getAss(java.lang.String s)
    {
        java.lang.String s1 = (java.lang.String)assPilot.get(s);
        if(s1 == null)
            s1 = (java.lang.String)assType.get(s);
        return s1;
    }

    private java.lang.String getByScoreItem(int i, java.lang.String s)
    {
        switch(i)
        {
        case 8: // '\b'
            return i18n("debrief.Airstatic");

        case 1: // '\001'
            return i18n("debrief.Tank");

        case 2: // '\002'
            return i18n("debrief.Car");

        case 3: // '\003'
            return i18n("debrief.Artillery");

        case 4: // '\004'
            return i18n("debrief.AAA");

        case 5: // '\005'
            return i18n("debrief.Bridge");

        case 6: // '\006'
            return i18n("debrief.Train");

        case 7: // '\007'
            return i18n("debrief.Ship");
        }
        return s;
    }

    private int colorPath()
    {
        long l = com.maddox.rts.Time.currentReal();
        long l1 = 1000L;
        double d = (2D * (double)(l % l1)) / (double)l1;
        if(d >= 1.0D)
            d = 2D - d;
        int i = (int)(255D * d);
        return 0xff000000 | i << 16 | i << 8 | i;
    }

    private void drawPlayerPath()
    {
        com.maddox.il2.engine.Render.drawBeginLines(-1);
        double d = 5D / cameraMap2D.worldScale;
        d *= d;
        java.util.ArrayList arraylist = com.maddox.il2.ai.EventLog.actions;
        int i = arraylist.size();
        com.maddox.il2.ai.EventLog.Action action = null;
        int j = colorPath();
        for(int k = 0; k < i; k++)
        {
            com.maddox.il2.ai.EventLog.Action action1 = (com.maddox.il2.ai.EventLog.Action)arraylist.get(k);
            if(action1.event == 11)
                if(action == null)
                {
                    action = action1;
                } else
                {
                    double d1 = (action.x - action1.x) * (action.x - action1.x) + (action.y - action1.y) * (action.y - action1.y);
                    if(d1 >= d)
                    {
                        lineXYZ[0] = (float)(((double)action.x - cameraMap2D.worldXOffset) * cameraMap2D.worldScale);
                        lineXYZ[1] = (float)(((double)action.y - cameraMap2D.worldYOffset) * cameraMap2D.worldScale);
                        lineXYZ[2] = 0.0F;
                        lineXYZ[3] = (float)(((double)action1.x - cameraMap2D.worldXOffset) * cameraMap2D.worldScale);
                        lineXYZ[4] = (float)(((double)action1.y - cameraMap2D.worldYOffset) * cameraMap2D.worldScale);
                        lineXYZ[5] = 0.0F;
                        com.maddox.il2.engine.Render.drawLines(lineXYZ, 2, 3F, j, com.maddox.il2.engine.Mat.NOWRITEZ | com.maddox.il2.engine.Mat.MODULATE | com.maddox.il2.engine.Mat.NOTEXTURE | com.maddox.il2.engine.Mat.BLEND, 1);
                        action = action1;
                    }
                }
        }

        com.maddox.il2.engine.Render.drawEnd();
    }

    protected void doRenderMap2D()
    {
        drawPlayerPath();
        drawEvents();
    }

    protected void doBack()
    {
        saveCampaign();
        com.maddox.il2.game.Main3D.cur3D().keyRecord.clearRecorded();
        if(com.maddox.il2.game.Mission.cur() != null && !com.maddox.il2.game.Mission.cur().isDestroyed())
            com.maddox.il2.game.Mission.cur().destroy();
        com.maddox.il2.game.Main.cur().campaign = null;
        com.maddox.il2.game.Main.cur().currentMissionFile = null;
        com.maddox.il2.game.Main.stateStack().pop();
    }

    protected void doDiff()
    {
        com.maddox.il2.game.Main.stateStack().push(9);
    }

    protected void doLoodout()
    {
        com.maddox.il2.game.Main3D.cur3D().keyRecord.clearRecorded();
        com.maddox.il2.game.Main.stateStack().change(63);
    }

    protected void doNext()
    {
        if(!bMissComplete)
            return;
        int i = com.maddox.il2.game.Main.cur().campaign._rank;
        com.maddox.il2.game.Main.cur().campaign.currentMissionComplete(com.maddox.il2.ai.Scores.score, com.maddox.il2.ai.Scores.enemyAirKill, com.maddox.il2.ai.Scores.arrayEnemyGroundKill, com.maddox.il2.ai.Scores.friendlyKill);
        com.maddox.il2.game.Main.cur().campaign._rank = i;
        if(com.maddox.il2.game.Main.cur().campaign.isComplete())
        {
            java.lang.String s = com.maddox.il2.game.Main.cur().campaign.epilogueTrack();
            doBack();
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
            updatePlayerScore();
            if(com.maddox.il2.game.Main.cur().campaign.isComplete())
            {
                java.lang.String s1 = com.maddox.il2.game.Main.cur().campaign.epilogueTrack();
                doBack();
                if(s1 != null)
                {
                    com.maddox.il2.gui.GUIBWDemoPlay.demoFile = s1;
                    com.maddox.il2.gui.GUIBWDemoPlay.soundFile = null;
                    com.maddox.il2.game.Main.stateStack().push(58);
                }
                return;
            }
            java.lang.String s2 = com.maddox.il2.game.Main.cur().campaign.nextIntro();
            if(s2 != null)
            {
                com.maddox.il2.gui.GUIBWDemoPlay.demoFile = s2;
                com.maddox.il2.gui.GUIBWDemoPlay.soundFile = null;
                com.maddox.il2.game.Main.stateStack().push(58);
                return;
            }
            com.maddox.il2.game.Main.cur().currentMissionFile = com.maddox.il2.game.Main.cur().campaign.nextMission();
            if(com.maddox.il2.game.Main.cur().currentMissionFile == null)
            {
                new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("miss.Error"), i18n("miss.LoadFailed"), 3, 0.0F) {

                    public void result(int j)
                    {
                        doBack();
                    }

                }
;
                return;
            }
            com.maddox.il2.game.Main.stateStack().change(62);
        }
    }

    protected void clientRender()
    {
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient dialogclient = dialogClient;
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp = dialogclient;
        dialogclient.draw(dialogclient.x1024(144F), dialogclient.y1024(656F), dialogclient.x1024(160F), dialogclient.y1024(48F), 0, i18n("debrief.MainMenu"));
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp1 = dialogclient;
        dialogclient.draw(dialogclient.x1024(256F), dialogclient.y1024(656F), dialogclient.x1024(208F), dialogclient.y1024(48F), 2, i18n("debrief.SaveTrack"));
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp2 = dialogclient;
        dialogclient.draw(dialogclient.x1024(528F), dialogclient.y1024(656F), dialogclient.x1024(176F), dialogclient.y1024(48F), 2, i18n("debrief.ReFly"));
        if(bNext.isVisible())
        {
            com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp3 = dialogclient;
            dialogclient.draw(dialogclient.x1024(768F), dialogclient.y1024(656F), dialogclient.x1024(160F), dialogclient.y1024(48F), 2, i18n("debrief.Apply"));
        }
    }

    public GUIDGenDeBriefing(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(64);
        sound = null;
        bMissComplete = false;
        iconAction = new com.maddox.il2.engine.Mat[13];
        assPilot = new HashMap();
        assType = new HashMap();
        tip = new java.lang.String[4];
        lineXYZ = new float[6];
        init(gwindowroot);
        iconAction[0] = com.maddox.il2.engine.Mat.New("icons/objActAAA.mat");
        iconAction[1] = com.maddox.il2.engine.Mat.New("icons/objActBailed.mat");
        iconAction[2] = com.maddox.il2.engine.Mat.New("icons/objActBridge.mat");
        iconAction[3] = com.maddox.il2.engine.Mat.New("icons/objActCar.mat");
        iconAction[4] = com.maddox.il2.engine.Mat.New("icons/objActCrashed.mat");
        iconAction[5] = com.maddox.il2.engine.Mat.New("icons/objActGun.mat");
        iconAction[6] = com.maddox.il2.engine.Mat.New("icons/objActLanded.mat");
        iconAction[7] = com.maddox.il2.engine.Mat.New("icons/objActPlane.mat");
        iconAction[8] = com.maddox.il2.engine.Mat.New("icons/objActShip.mat");
        iconAction[9] = com.maddox.il2.engine.Mat.New("icons/objActTank.mat");
        iconAction[10] = com.maddox.il2.engine.Mat.New("icons/objActTrain.mat");
        iconAction[11] = com.maddox.il2.engine.Mat.New("icons/objActPilot.mat");
        iconAction[12] = com.maddox.il2.engine.Mat.New("icons/objActAirstatic.mat");
    }

    private static final java.lang.String fileNameDebrifing = "dgen/debrifing.txt";
    private java.lang.String sound;
    protected boolean bMissComplete;
    private static final int ICON_AAA = 0;
    private static final int ICON_Bailed = 1;
    private static final int ICON_Bridge = 2;
    private static final int ICON_Car = 3;
    private static final int ICON_Crashed = 4;
    private static final int ICON_Gun = 5;
    private static final int ICON_Landed = 6;
    private static final int ICON_Plane = 7;
    private static final int ICON_Ship = 8;
    private static final int ICON_Tank = 9;
    private static final int ICON_Train = 10;
    private static final int ICON_Pilot = 11;
    private static final int ICON_Airstatic = 12;
    private com.maddox.il2.engine.Mat iconAction[];
    private java.util.HashMap assPilot;
    private java.util.HashMap assType;
    private java.lang.String tip[];
    private float lineXYZ[];
}
