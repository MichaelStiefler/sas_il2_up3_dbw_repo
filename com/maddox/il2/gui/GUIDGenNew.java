// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIDGenNew.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCellEdit;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.campaign.Awards;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.il2.game.campaign.CampaignDGen;
import com.maddox.rts.HomePath;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SFSReader;
import com.maddox.rts.SectFile;
import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUIBWDemoPlay, GUIDialogClient, GUISeparate

public class GUIDGenNew extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bBack)
            {
                com.maddox.il2.game.Main.cur().campaign = null;
                com.maddox.il2.game.Main.stateStack().pop();
                return true;
            }
            if(gwindow == bStart)
            {
                java.lang.String s = fullFileNameCampaignIni();
                if(exestFile(s))
                {
                    new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("campnew.Confirm"), i18n("campnew.Exist"), 1, 0.0F) {

                        public void result(int k)
                        {
                            if(k == 3)
                            {
                                delCampaign();
                                doGenerateCampaign();
                            } else
                            {
                                client.activateWindow();
                            }
                        }

                    }
;
                    return true;
                } else
                {
                    doGenerateCampaign();
                    return true;
                }
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
            draw(x1024(96F), y1024(32F), x1024(224F), y1024(32F), 1, i18n("dgennew.You"));
            draw(x1024(64F), y1024(96F), x1024(306F), y1024(32F), 2, i18n("dgennew.Place"));
            draw(x1024(64F), y1024(144F), x1024(306F), y1024(32F), 2, i18n("dgennew.Year"));
            draw(x1024(64F), y1024(240F), x1024(306F), y1024(32F), 2, i18n("dgennew.Squadron"));
            draw(x1024(96F), y1024(658F), x1024(240F), y1024(48F), 0, i18n("dgennew.MainMenu"));
            draw(x1024(400F), y1024(658F), x1024(240F), y1024(48F), 2, i18n("dgennew.Generate"));
            if(campaigns.size() > 0 && wTable.selectRow >= 0)
                draw(x1024(32F), y1024(576F), x1024(674F), y1024(32F), 0, i18n("dgennew.First") + " " + ((com.maddox.il2.gui.Camp)campaigns.get(wTable.selectRow)).info);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(624F), x1024(674F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(48F), x1024(48F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(336F), y1024(48F), x1024(368F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(208F), x1024(674F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(48F), 2.0F, y1024(162F));
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(704F), y1024(48F), 2.0F, y1024(162F));
        }

        public void setPosSize()
        {
            set1024PosSize(144F, 32F, 736F, 736F);
            wPBirth.set1024PosSize(384F, 96F, 288F, 32F);
            wYBirth.set1024PosSize(384F, 144F, 288F, 32F);
            wSquadron.set1024PosSize(384F, 240F, 288F, 32F);
            bBack.setPosC(x1024(56F), y1024(682F));
            bStart.setPosC(x1024(682F), y1024(682F));
            wTable.set1024PosSize(32F, 304F, 674F, 240F);
        }


        public DialogClient()
        {
        }
    }

    public class Table extends com.maddox.gwindow.GWindowTable
    {

        public int countRows()
        {
            return campaignsList == null ? 0 : campaigns.size();
        }

        public boolean isCellEditable(int i, int j)
        {
            return j == 1;
        }

        public float rowHeight(int i)
        {
            return (float)(int)(root.textFonts[0].height * 1.6F);
        }

        public com.maddox.gwindow.GWindowCellEdit getCellEdit(int i, int j)
        {
            if(!isCellEditable(i, j))
                return null;
            indxCamp = i;
            com.maddox.gwindow.GWindowComboControl gwindowcombocontrol;
            com.maddox.gwindow.GWindowCellEdit gwindowcelledit = (com.maddox.gwindow.GWindowCellEdit)wClient.create(gwindowcombocontrol = new com.maddox.gwindow.GWindowComboControl() {

                public boolean notify(int l, int i1)
                {
                    boolean flag = super.notify(l, i1);
                    if(l == 2)
                        camp.select = i1;
                    return flag;
                }

                com.maddox.il2.gui.Camp camp;

                
                {
                    camp = (com.maddox.il2.gui.Camp)campaigns.get(indxCamp);
                }
            }
);
            gwindowcombocontrol.setEditable(false);
            com.maddox.il2.gui.Camp camp = (com.maddox.il2.gui.Camp)campaigns.get(i);
            for(int k = 0; k < camp.air.size(); k++)
                gwindowcombocontrol.add((java.lang.String)camp.airInfo.get(k));

            gwindowcombocontrol.setSelected(camp.select, true, false);
            return gwindowcelledit;
        }

        public void renderCell(int i, int j, boolean flag, float f, float f1)
        {
            setCanvasFont(0);
            if(flag)
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, lookAndFeel().regionWhite);
            }
            com.maddox.il2.gui.Camp camp = (com.maddox.il2.gui.Camp)campaigns.get(i);
            java.lang.String s = null;
            int k = 0;
            switch(j)
            {
            case 0: // '\0'
                s = camp.info;
                k = 0;
                break;

            case 1: // '\001'
                s = (java.lang.String)camp.airInfo.get(camp.select);
                k = 0;
                break;
            }
            if(flag)
            {
                setCanvasColorWHITE();
                draw(0.0F, 0.0F, f, f1, k, s);
            } else
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, k, s);
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
            addColumn(com.maddox.il2.game.I18N.gui("dgennew.Operation"), null);
            addColumn(com.maddox.il2.game.I18N.gui("dgennew.Plane"), null);
            vSB.scroll = rowHeight(0);
            getColumn(0).setRelativeDx(20F);
            getColumn(1).setRelativeDx(10F);
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

        public java.util.ArrayList campaignsList;
        int indxCamp;


        public Table(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
            campaignsList = campaigns;
        }
    }

    private static class Camp
    {

        java.lang.String key;
        java.lang.String info;
        java.util.ArrayList air;
        java.util.ArrayList airInfo;
        int select;

        private Camp()
        {
            air = new ArrayList();
            airInfo = new ArrayList();
            select = 0;
        }

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
                    }

                }
;
                return;
            } else
            {
                com.maddox.il2.game.Main.stateStack().change(62);
                return;
            }
        } else
        {
            client.activateWindow();
            return;
        }
    }

    public void _enter()
    {
        cdgen = (com.maddox.il2.game.campaign.CampaignDGen)com.maddox.il2.game.Main.cur().campaign;
        if(!fillTable())
        {
            com.maddox.il2.game.Main.stateStack().pop();
            return;
        }
        fillSquadrons();
        if(com.maddox.il2.ai.World.cur().userCfg.placeBirth != null)
            wPBirth.setValue(com.maddox.il2.ai.World.cur().userCfg.placeBirth, false);
        wYBirth.setValue("" + com.maddox.il2.ai.World.cur().userCfg.yearBirth, false);
        client.activateWindow();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    private boolean exestFile(java.lang.String s)
    {
        try
        {
            com.maddox.rts.SFSInputStream sfsinputstream = new SFSInputStream(s);
            sfsinputstream.close();
        }
        catch(java.lang.Exception exception)
        {
            return false;
        }
        return true;
    }

    private boolean fillTable()
    {
        campaigns.clear();
        java.lang.String s = "dgen/" + cdgen.dgenFileName();
        if(!exestFile(s))
            return false;
        java.io.BufferedReader bufferedreader = null;
        try
        {
            bufferedreader = new BufferedReader(new SFSReader(s, com.maddox.rts.RTSConf.charEncoding));
            bufferedreader.readLine();
            bufferedreader.readLine();
            do
            {
                if(!bufferedreader.ready())
                    break;
                java.lang.String s1 = bufferedreader.readLine();
                if(s1 == null)
                    break;
                s1 = s1.trim();
                int j = s1.length();
                if(j != 0)
                {
                    s1 = com.maddox.util.UnicodeTo8bit.load(s1, false);
                    int k = s1.indexOf(" ");
                    if(k > 0 && k != s1.length() - 1)
                    {
                        com.maddox.il2.gui.Camp camp = new Camp();
                        camp.key = s1.substring(0, k);
                        camp.info = s1.substring(k + 1);
                        campaigns.add(camp);
                    }
                }
            } while(true);
            bufferedreader.close();
        }
        catch(java.lang.Exception exception)
        {
            if(bufferedreader != null)
                try
                {
                    bufferedreader.close();
                }
                catch(java.lang.Exception exception1) { }
            return false;
        }
        int i = campaigns.size();
        if(i > 0)
        {
            com.maddox.rts.SectFile sectfile = new SectFile("dgen/planes" + cdgen.branch() + cdgen.prefix() + ".dat", 0);
            for(int l = 0; l < i; l++)
            {
                com.maddox.il2.gui.Camp camp1 = (com.maddox.il2.gui.Camp)campaigns.get(l);
                int i1 = sectfile.sectionIndex(camp1.key);
                if(i1 >= 0)
                {
                    int j1 = sectfile.vars(i1);
                    for(int k1 = 0; k1 < j1; k1++)
                    {
                        java.lang.String s2 = sectfile.var(i1, k1);
                        try
                        {
                            java.lang.Class class1 = com.maddox.rts.ObjIO.classForName("air." + s2);
                            camp1.air.add(s2);
                            java.lang.String s3 = com.maddox.rts.Property.stringValue(class1, "keyName", null);
                            camp1.airInfo.add(com.maddox.il2.game.I18N.plane(s3));
                        }
                        catch(java.lang.Exception exception2)
                        {
                            java.lang.System.out.println("Section [" + camp1.key + "] in a file " + sectfile.fileName() + " contains unknown aircraft " + s2);
                        }
                    }

                } else
                {
                    java.lang.System.out.println("The Section [" + camp1.key + "] in a file " + sectfile.fileName() + " is NOT found");
                }
                if(camp1.air.size() == 0)
                    return false;
            }

            wTable.setSelect(0, 0);
            wTable.resolutionChanged();
            return true;
        } else
        {
            return false;
        }
    }

    private void fillSquadrons()
    {
        wSquadron.clear(false);
        regimentList.clear();
        java.io.BufferedReader bufferedreader = null;
        try
        {
            bufferedreader = new BufferedReader(new SFSReader("dgen/squadrons" + cdgen.branch() + cdgen.prefix() + ".dat"));
            do
            {
                if(!bufferedreader.ready())
                    break;
                java.lang.String s = bufferedreader.readLine();
                if(s == null)
                    break;
                s = s.trim();
                int j = s.length();
                if(j != 0)
                {
                    com.maddox.il2.engine.Actor actor = com.maddox.il2.engine.Actor.getByName(s);
                    if(actor != null && (actor instanceof com.maddox.il2.ai.Regiment))
                        regimentList.add(actor);
                }
            } while(true);
            bufferedreader.close();
        }
        catch(java.lang.Exception exception)
        {
            if(bufferedreader != null)
                try
                {
                    bufferedreader.close();
                }
                catch(java.lang.Exception exception1) { }
        }
        wSquadron.clear(false);
        wSquadron.setSelected(-1, false, false);
        int i = regimentList.size();
        for(int k = 0; k < i; k++)
        {
            com.maddox.il2.ai.Regiment regiment = (com.maddox.il2.ai.Regiment)regimentList.get(k);
            wSquadron.add(com.maddox.il2.game.I18N.regimentShort(regiment.shortInfo()));
        }

        if(i > 0)
            wSquadron.setSelected(0, true, false);
        else
            wSquadron.setSelected(-1, true, false);
    }

    private java.lang.String fullFileNameCampaignIni()
    {
        com.maddox.il2.gui.Camp camp = (com.maddox.il2.gui.Camp)campaigns.get(wTable.selectRow);
        return "missions/campaign/" + cdgen.branch() + "/" + dirNameCampaign() + "/campaign.ini";
    }

    private java.lang.String dirNameCampaign()
    {
        com.maddox.il2.gui.Camp camp = (com.maddox.il2.gui.Camp)campaigns.get(wTable.selectRow);
        java.lang.String s = "DGen_" + cdgen.prefix() + "_" + camp.key + com.maddox.il2.ai.World.cur().userCfg.sId + cdgen.rank();
        return s;
    }

    private java.lang.String validString(java.lang.String s)
    {
        if(s == null || s.length() == 0 || " ".equals(s))
            return "_";
        do
        {
            int i = s.indexOf(",");
            if(i >= 0)
            {
                if(i + 1 <= s.length() - 1)
                    s = s.substring(0, i) + "_" + s.substring(i + 1);
                else
                    s = s.substring(0, i) + "_";
            } else
            {
                return s;
            }
        } while(true);
    }

    private void doGenerateCampaign()
    {
        java.lang.String s = "dgen/conf" + cdgen.branch() + ".ini";
        java.lang.String s1 = validString(com.maddox.il2.ai.World.cur().userCfg.surname);
        java.lang.String s2 = validString(com.maddox.il2.ai.World.cur().userCfg.name);
        java.lang.String s3 = validString(wPBirth.getValue());
        com.maddox.il2.ai.World.cur().userCfg.placeBirth = s3;
        try
        {
            com.maddox.il2.ai.World.cur().userCfg.yearBirth = java.lang.Integer.parseInt(wYBirth.getValue());
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.ai.World.cur().userCfg.yearBirth = 1910;
        }
        if(com.maddox.il2.ai.World.cur().userCfg.yearBirth < 1850)
            com.maddox.il2.ai.World.cur().userCfg.yearBirth = 1850;
        if(com.maddox.il2.ai.World.cur().userCfg.yearBirth > 2050)
            com.maddox.il2.ai.World.cur().userCfg.yearBirth = 2050;
        try
        {
            java.io.PrintWriter printwriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(com.maddox.rts.HomePath.toFileSystemName(s, 0)), com.maddox.rts.RTSConf.charEncoding)));
            printwriter.println(s1 + "," + s2 + "," + s3 + "," + com.maddox.il2.ai.World.cur().userCfg.yearBirth);
            printwriter.println(((com.maddox.il2.ai.Regiment)(com.maddox.il2.ai.Regiment)regimentList.get(wSquadron.getSelected())).name());
            java.lang.String s4 = com.maddox.rts.RTSConf.cur.locale.getLanguage();
            java.lang.String s5 = "English";
            if("ru".equalsIgnoreCase(s4))
                s5 = "Russian";
            else
            if("de".equalsIgnoreCase(s4))
                s5 = "German";
            else
            if("fr".equalsIgnoreCase(s4))
                s5 = "French";
            else
            if("cs".equalsIgnoreCase(s4))
                s5 = "Czech";
            else
            if("pl".equalsIgnoreCase(s4))
                s5 = "Polish";
            else
            if("hu".equalsIgnoreCase(s4))
                s5 = "Hungarian";
            else
            if("lt".equalsIgnoreCase(s4))
                s5 = "Lithuanian";
            else
            if("ja".equalsIgnoreCase(s4))
                s5 = "Japanese";
            printwriter.println("dir=missions\\campaign\\" + cdgen.branch() + "\\" + dirNameCampaign());
            printwriter.println("Language=" + s5);
            printwriter.println("instant=false");
            for(int i = wTable.selectRow; i < campaigns.size(); i++)
            {
                com.maddox.il2.gui.Camp camp1 = (com.maddox.il2.gui.Camp)campaigns.get(i);
                printwriter.println(camp1.key + " " + camp1.air.get(camp1.select));
            }

            printwriter.close();
        }
        catch(java.io.IOException ioexception)
        {
            java.lang.System.out.println("File: " + s + " save failed: " + ioexception.getMessage());
            ioexception.printStackTrace();
            return;
        }
        com.maddox.il2.ai.World.cur().userCfg.saveConf();
        com.maddox.il2.gui.Camp camp = (com.maddox.il2.gui.Camp)campaigns.get(wTable.selectRow);
        cdgen.doExternalCampaignGenerator(camp.key);
        com.maddox.il2.game.campaign.Campaign campaign = null;
        try
        {
            java.lang.String s6 = cdgen.branch() + dirNameCampaign();
            java.lang.String s8 = "users/" + com.maddox.il2.ai.World.cur().userCfg.sId + "/campaigns.ini";
            com.maddox.rts.SectFile sectfile = new SectFile(s8, 1, false, com.maddox.il2.ai.World.cur().userCfg.krypto());
            com.maddox.rts.SectFile sectfile1 = new SectFile(fullFileNameCampaignIni(), 0);
            java.lang.String s9 = sectfile1.get("Main", "Class", (java.lang.String)null);
            java.lang.Class class1 = com.maddox.rts.ObjIO.classForName(s9);
            campaign = (com.maddox.il2.game.campaign.Campaign)class1.newInstance();
            try
            {
                class1 = com.maddox.rts.ObjIO.classForName(sectfile1.get("Main", "awardsClass", (java.lang.String)null));
            }
            catch(java.lang.Exception exception2)
            {
                class1 = com.maddox.il2.game.campaign.AwardsRUfighter.class;
            }
            com.maddox.il2.game.campaign.Awards awards = (com.maddox.il2.game.campaign.Awards)class1.newInstance();
            campaign.init(awards, cdgen.branch(), dirNameCampaign(), cdgen.difficulty(), cdgen.rank());
            campaign._nawards = 0;
            campaign._epilogueTrack = sectfile1.get("Main", "EpilogueTrack", (java.lang.String)null);
            sectfile.set("list", s6, campaign, true);
            campaign.clearSavedStatics(sectfile);
            sectfile.saveFile();
        }
        catch(java.lang.Exception exception1)
        {
            java.lang.System.out.println(exception1.getMessage());
            exception1.printStackTrace();
            return;
        }
        com.maddox.il2.game.Main.cur().campaign = campaign;
        java.lang.String s7 = com.maddox.il2.game.Main.cur().campaign.nextIntro();
        if(s7 != null)
        {
            com.maddox.il2.gui.GUIBWDemoPlay.demoFile = s7;
            com.maddox.il2.gui.GUIBWDemoPlay.soundFile = null;
            com.maddox.il2.game.Main.stateStack().push(58);
            return;
        }
        com.maddox.il2.game.Main.cur().currentMissionFile = campaign.nextMission();
        if(com.maddox.il2.game.Main.cur().currentMissionFile == null)
        {
            new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("miss.Error"), i18n("miss.LoadFailed"), 3, 0.0F) {

                public void result(int j)
                {
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

    private void delCampaign()
    {
        try
        {
            java.lang.String s = "users/" + com.maddox.il2.ai.World.cur().userCfg.sId + "/campaigns.ini";
            com.maddox.rts.SectFile sectfile = new SectFile(s, 1, false, com.maddox.il2.ai.World.cur().userCfg.krypto());
            int i = sectfile.sectionIndex("list");
            java.lang.String s1 = cdgen.branch() + dirNameCampaign();
            int j = sectfile.varIndex(i, s1);
            com.maddox.il2.game.campaign.Campaign campaign = (com.maddox.il2.game.campaign.Campaign)com.maddox.rts.ObjIO.fromString(sectfile.value(i, j));
            java.lang.String s2 = "missions/campaign/" + campaign.branch() + "/" + campaign.missionsDir();
            java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName(s2, 0));
            java.io.File afile[] = file.listFiles();
            if(afile != null)
            {
                for(int k = 0; k < afile.length; k++)
                {
                    java.io.File file1 = afile[k];
                    java.lang.String s3 = file1.getName();
                    if(!".".equals(s3) && !"..".equals(s3))
                        file1.delete();
                }

            }
            file.delete();
            campaign.clearSavedStatics(sectfile);
            sectfile.lineRemove(i, j);
            sectfile.saveFile();
        }
        catch(java.lang.Exception exception) { }
    }

    public GUIDGenNew(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(61);
        campaigns = new ArrayList();
        regimentList = new ArrayList();
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("dgennew.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        wPBirth = (com.maddox.gwindow.GWindowEditControl)dialogClient.addControl(new GWindowEditControl(dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null));
        wPBirth.maxLength = 74;
        if("ja".equals(java.util.Locale.getDefault().getLanguage()))
            wPBirth.maxLength /= 6;
        wYBirth = (com.maddox.gwindow.GWindowEditControl)dialogClient.addControl(new GWindowEditControl(dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null));
        wYBirth.bNumericOnly = true;
        wYBirth.maxLength = 4;
        wSquadron = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 0.0F, 0.0F, 1.0F));
        wSquadron.setEditable(false);
        wTable = new Table(dialogClient);
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bBack = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bStart = (com.maddox.il2.gui.GUIButton)dialogClient.addDefault(new GUIButton(dialogClient, gtexture, 0.0F, 192F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.gwindow.GWindowEditControl wPBirth;
    public com.maddox.gwindow.GWindowEditControl wYBirth;
    public com.maddox.gwindow.GWindowComboControl wSquadron;
    public com.maddox.il2.gui.Table wTable;
    public com.maddox.il2.gui.GUIButton bBack;
    public com.maddox.il2.gui.GUIButton bStart;
    private com.maddox.il2.game.campaign.CampaignDGen cdgen;
    private java.util.ArrayList campaigns;
    private java.util.ArrayList regimentList;





}
