// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUICampaigns.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.rts.HomePath;
import com.maddox.rts.ObjIO;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.ResourceBundle;

// Referenced classes of package com.maddox.il2.gui:
//            GUIRoot, GUIClient, GUIInfoMenu, GUIInfoName, 
//            GUILookAndFeel, GUIButton, GUIBWDemoPlay, GUIDialogClient, 
//            GUISeparate

public class GUICampaigns extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bExit)
            {
                com.maddox.il2.game.Main.stateStack().pop();
                return true;
            }
            if(gwindow == bNew)
            {
                com.maddox.il2.game.Main.stateStack().change(26);
                return true;
            }
            if(gwindow == bStat)
            {
                int k = wTable.selectRow;
                if(k < 0)
                    return true;
                com.maddox.il2.gui.Item item = (com.maddox.il2.gui.Item)wTable.campList.get(k);
                com.maddox.il2.game.Main.cur().campaign = item.camp;
                if(item.camp.isDGen())
                    com.maddox.il2.game.Main.stateStack().push(65);
                else
                    com.maddox.il2.game.Main.stateStack().push(31);
                return true;
            }
            if(gwindow == bDiff)
            {
                int l = wTable.selectRow;
                if(l < 0)
                {
                    return true;
                } else
                {
                    com.maddox.il2.gui.Item item1 = (com.maddox.il2.gui.Item)wTable.campList.get(l);
                    com.maddox.il2.ai.World.cur().diffUser.set(item1.camp.difficulty());
                    com.maddox.il2.game.Main.stateStack().push(17);
                    return true;
                }
            }
            if(gwindow == bDel)
            {
                removeItem();
                return true;
            }
            if(gwindow == bStart)
            {
                doStart();
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            if(wTable.selectRow >= 0 && ((com.maddox.il2.gui.Item)wTable.campList.get(wTable.selectRow)).camp.isDGen())
                draw(x1024(80F), y1024(544F), x1024(336F), y1024(48F), 2, i18n("camps.Roster"));
            else
                draw(x1024(80F), y1024(544F), x1024(336F), y1024(48F), 2, i18n("camps.Statistics"));
            draw(x1024(728F), y1024(544F), x1024(200F), y1024(48F), 2, i18n("camps.View_Difficulty"));
            draw(x1024(96F), y1024(658F), x1024(128F), y1024(48F), 0, i18n("camps.Back"));
            draw(x1024(256F), y1024(658F), x1024(160F), y1024(48F), 2, i18n("camps.Delete"));
            draw(x1024(496F), y1024(658F), x1024(160F), y1024(48F), 2, i18n("camps.New"));
            draw(x1024(766F), y1024(658F), x1024(160F), y1024(48F), 2, i18n("camps.Load"));
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(624F), x1024(960F), 2.0F);
        }

        public void setPosSize()
        {
            set1024PosSize(0.0F, 32F, 1024F, 736F);
            bStat.setPosC(x1024(456F), y1024(568F));
            bDiff.setPosC(x1024(968F), y1024(568F));
            bExit.setPosC(x1024(56F), y1024(682F));
            bDel.setPosC(x1024(456F), y1024(682F));
            bNew.setPosC(x1024(696F), y1024(682F));
            bStart.setPosC(x1024(968F), y1024(682F));
            wTable.set1024PosSize(32F, 32F, 960F, 480F);
        }

        public DialogClient()
        {
        }
    }

    public class Table extends com.maddox.gwindow.GWindowTable
    {

        public int countRows()
        {
            return campList == null ? 0 : campList.size();
        }

        public void renderCell(int i, int j, boolean flag, float f, float f1)
        {
            setCanvasFont(0);
            if(flag)
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, lookAndFeel().regionWhite);
            }
            com.maddox.il2.gui.Item item = (com.maddox.il2.gui.Item)campList.get(i);
            float f2 = 0.0F;
            java.lang.String s = null;
            int k = 0;
            switch(j)
            {
            case 0: // '\0'
                setCanvasColorWHITE();
                draw(0.0F, 0.0F, f1, f1, item.icon);
                f2 = 1.5F * f1;
                f -= f2;
                s = item.name;
                break;

            case 1: // '\001'
                s = item.rank;
                break;

            case 2: // '\002'
                if(item.camp._nawards >= 0)
                    s = "" + item.camp._nawards;
                else
                    s = "" + item.camp.awards(item.camp.score());
                k = 1;
                break;

            case 3: // '\003'
                if(item.camp.isComplete())
                    s = "100%";
                else
                    s = "" + item.camp.completeMissions();
                k = 1;
                break;

            case 4: // '\004'
                s = item.diff;
                break;
            }
            if(flag)
            {
                setCanvasColorWHITE();
                draw(f2, 0.0F, f, f1, k, s);
            } else
            {
                setCanvasColorBLACK();
                draw(f2, 0.0F, f, f1, k, s);
            }
        }

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(super.notify(gwindow, i, j))
            {
                return true;
            } else
            {
                notify(i, j);
                return false;
            }
        }

        public void afterCreated()
        {
            super.afterCreated();
            bColumnsSizable = true;
            bSelectRow = true;
            addColumn(com.maddox.il2.game.I18N.gui("camps.Career"), null);
            addColumn(com.maddox.il2.game.I18N.gui("camps.Rank"), null);
            addColumn(com.maddox.il2.game.I18N.gui("camps.Awards"), null);
            addColumn(com.maddox.il2.game.I18N.gui("camps.Completed"), null);
            addColumn(com.maddox.il2.game.I18N.gui("camps.Difficulty"), null);
            vSB.scroll = rowHeight(0);
            getColumn(0).setRelativeDx(11F);
            getColumn(1).setRelativeDx(6F);
            getColumn(2).setRelativeDx(5F);
            getColumn(3).setRelativeDx(5F);
            getColumn(4).setRelativeDx(6F);
            alignColumns();
            bNotify = true;
            wClient.bNotify = true;
            resized();
        }

        public void resolutionChanged()
        {
            vSB.scroll = rowHeight(0);
            super.resolutionChanged();
        }

        public java.util.ArrayList campList;

        public Table(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
            campList = new ArrayList();
        }
    }

    static class Item
    {

        public java.lang.String key;
        public com.maddox.il2.game.campaign.Campaign camp;
        public com.maddox.gwindow.GTexture icon;
        public java.lang.String name;
        public java.lang.String rank;
        public java.lang.String diff;

        Item()
        {
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

                    public void result(int i)
                    {
                    }

                }
;
                return;
            }
            if(com.maddox.il2.game.Main.cur().campaign.isDGen())
                com.maddox.il2.game.Main.stateStack().change(62);
            else
                com.maddox.il2.game.Main.stateStack().change(28);
            return;
        } else
        {
            _enter();
            return;
        }
    }

    public void _enter()
    {
        fillCampList();
        if(wTable.campList.size() == 0)
        {
            com.maddox.il2.game.Main.stateStack().change(26);
            return;
        } else
        {
            wTable.resized();
            client.activateWindow();
            return;
        }
    }

    public void _leave()
    {
        wTable.campList.clear();
        client.hideWindow();
    }

    private void fillCampList()
    {
        com.maddox.il2.ai.DifficultySettings difficultysettings;
        com.maddox.rts.SectFile sectfile;
        int i;
        wTable.campList.clear();
        difficultysettings = new DifficultySettings();
        sectfile = null;
        i = 0;
        java.lang.String s = "users/" + com.maddox.il2.ai.World.cur().userCfg.sId + "/campaigns.ini";
        sectfile = new SectFile(s, 0, false, com.maddox.il2.ai.World.cur().userCfg.krypto());
        i = sectfile.sectionIndex("list");
        if(i < 0)
            return;
        try
        {
            int j = sectfile.vars(i);
            for(int k = 0; k < j; k++)
            {
                com.maddox.il2.gui.Item item = new Item();
                item.key = sectfile.var(i, k);
                item.camp = (com.maddox.il2.game.campaign.Campaign)com.maddox.rts.ObjIO.fromString(sectfile.value(i, k));
                item.icon = com.maddox.gwindow.GTexture.New("missions/campaign/" + item.camp.branch() + "/icon.mat");
                java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle("missions/campaign/" + item.camp.branch() + "/" + item.camp.missionsDir() + "/info", com.maddox.rts.RTSConf.cur.locale);
                item.name = resourcebundle.getString("Name");
                java.util.ResourceBundle resourcebundle1 = java.util.ResourceBundle.getBundle("missions/campaign/" + item.camp.branch() + "/" + "rank", com.maddox.rts.RTSConf.cur.locale);
                item.rank = resourcebundle1.getString("" + item.camp.rank());
                difficultysettings.set(item.camp.difficulty());
                if(difficultysettings.isRealistic())
                    item.diff = i18n("camps.realistic");
                else
                if(difficultysettings.isNormal())
                    item.diff = i18n("camps.normal");
                else
                if(difficultysettings.isEasy())
                    item.diff = i18n("camps.easy");
                else
                    item.diff = i18n("camps.custom");
                wTable.campList.add(item);
            }

            if(wTable.campList.size() > 0)
                wTable.setSelect(0, 0);
        }
        catch(java.lang.Exception exception)
        {
            wTable.campList.clear();
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
            try
            {
                sectfile.sectionClear(i);
                sectfile.saveFile(sectfile.fileName());
            }
            catch(java.lang.Exception exception1) { }
        }
        return;
    }

    private void removeItem()
    {
        int i = wTable.selectRow;
        if(i < 0)
            return;
        java.lang.String s = "users/" + com.maddox.il2.ai.World.cur().userCfg.sId + "/campaigns.ini";
        com.maddox.rts.SectFile sectfile = new SectFile(s, 1, true, com.maddox.il2.ai.World.cur().userCfg.krypto());
        int j = sectfile.sectionIndex("list");
        int k = sectfile.varIndex(j, ((com.maddox.il2.gui.Item)wTable.campList.get(i)).key);
        try
        {
            com.maddox.il2.game.campaign.Campaign campaign = (com.maddox.il2.game.campaign.Campaign)com.maddox.rts.ObjIO.fromString(sectfile.value(j, k));
            if(campaign.isDGen())
            {
                java.lang.String s1 = "missions/campaign/" + campaign.branch() + "/" + campaign.missionsDir();
                java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName(s1, 0));
                java.io.File afile[] = file.listFiles();
                if(afile != null)
                {
                    for(int i1 = 0; i1 < afile.length; i1++)
                    {
                        java.io.File file1 = afile[i1];
                        java.lang.String s2 = file1.getName();
                        if(!".".equals(s2) && !"..".equals(s2))
                            file1.delete();
                    }

                }
                file.delete();
            }
            campaign.clearSavedStatics(sectfile);
        }
        catch(java.lang.Exception exception) { }
        sectfile.lineRemove(j, k);
        sectfile.saveFile();
        wTable.campList.remove(i);
        int l = wTable.campList.size();
        if(l == 0)
        {
            wTable.setSelect(-1, 0);
            com.maddox.il2.game.Main.stateStack().change(26);
        } else
        if(i == l)
            wTable.setSelect(i - 1, 0);
    }

    private void doStart()
    {
        int i = wTable.selectRow;
        if(i < 0)
            return;
        com.maddox.il2.gui.Item item = (com.maddox.il2.gui.Item)wTable.campList.get(i);
        if(item.camp.isComplete())
            return;
        wTable.campList.clear();
        com.maddox.il2.game.Main.cur().campaign = item.camp;
        com.maddox.il2.game.Main3D.menuMusicPlay(com.maddox.il2.game.Main.cur().campaign.country());
        java.lang.String s = com.maddox.il2.game.Main.cur().campaign.nextIntro();
        if(s != null)
        {
            com.maddox.il2.gui.GUIBWDemoPlay.demoFile = s;
            com.maddox.il2.gui.GUIBWDemoPlay.soundFile = null;
            com.maddox.il2.game.Main.stateStack().push(58);
            return;
        }
        com.maddox.il2.game.Main.cur().currentMissionFile = item.camp.nextMission();
        if(com.maddox.il2.game.Main.cur().currentMissionFile == null)
        {
            new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("miss.Error"), i18n("miss.LoadFailed"), 3, 0.0F) {

                public void result(int j)
                {
                }

            }
;
            return;
        }
        ((com.maddox.il2.gui.GUIRoot)dialogClient.root).setBackCountry("campaign", com.maddox.il2.game.Main.cur().campaign.branch());
        if(com.maddox.il2.game.Main.cur().campaign.isDGen())
            com.maddox.il2.game.Main.stateStack().change(62);
        else
            com.maddox.il2.game.Main.stateStack().change(28);
    }

    public GUICampaigns(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(27);
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("camps.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        wTable = new Table(dialogClient);
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bStat = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bDiff = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bDel = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bNew = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bStart = (com.maddox.il2.gui.GUIButton)dialogClient.addDefault(new GUIButton(dialogClient, gtexture, 0.0F, 192F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.Table wTable;
    public com.maddox.il2.gui.GUIButton bDiff;
    public com.maddox.il2.gui.GUIButton bStat;
    public com.maddox.il2.gui.GUIButton bDel;
    public com.maddox.il2.gui.GUIButton bNew;
    public com.maddox.il2.gui.GUIButton bExit;
    public com.maddox.il2.gui.GUIButton bStart;


}
