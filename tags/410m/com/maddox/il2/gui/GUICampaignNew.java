// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUICampaignNew.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowScrollingDialogClient;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.campaign.Awards;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.il2.game.campaign.CampaignDGen;
import com.maddox.rts.HomePath;
import com.maddox.rts.LDRres;
import com.maddox.rts.ObjIO;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SFSReader;
import com.maddox.rts.SectFile;
import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUIBWDemoPlay, GUIDialogClient, GUISeparate, 
//            GUIRoot

public class GUICampaignNew extends com.maddox.il2.game.GameState
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
            if(gwindow == bDifficulty)
            {
                com.maddox.il2.ai.World.cur().diffUser.set(difficulty);
                com.maddox.il2.game.Main.stateStack().push(17);
                return true;
            }
            if(gwindow == bStart)
            {
                if(dgenCampaignPrefix == null)
                {
                    java.lang.String s = country + campaign;
                    java.lang.String s1 = "users/" + com.maddox.il2.ai.World.cur().userCfg.sId + "/campaigns.ini";
                    if(exestFile(s1))
                    {
                        com.maddox.rts.SectFile sectfile = new SectFile(s1, 0, true, com.maddox.il2.ai.World.cur().userCfg.krypto());
                        int k = sectfile.sectionIndex("list");
                        if(k >= 0 && sectfile.varExist(k, s))
                        {
                            new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("campnew.Confirm"), i18n("campnew.Exist"), 1, 0.0F) {

                                public void result(int l)
                                {
                                    if(l == 3)
                                        doStartCampaign();
                                    else
                                        client.activateWindow();
                                }

                            }
;
                            return true;
                        }
                    }
                }
                if(dgenCampaignPrefix == null)
                    doStartCampaign();
                else
                    doStartDGenCampaign();
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
            draw(x1024(112F), y1024(32F), x1024(272F), y1024(32F), 0, i18n("campnew.Country"));
            draw(x1024(112F), y1024(144F), x1024(272F), y1024(32F), 0, i18n("campnew.Rank"));
            draw(x1024(464F), y1024(32F), x1024(432F), y1024(32F), 0, i18n("campnew.Career"));
            draw(x1024(464F), y1024(208F), x1024(432F), y1024(32F), 0, i18n("campnew.Description"));
            draw(x1024(32F), y1024(292F), x1024(304F), y1024(48F), 2, i18n("campnew.Difficulty"));
            draw(x1024(96F), y1024(658F), x1024(224F), y1024(48F), 0, i18n("campnew.MainMenu"));
            draw(x1024(718F), y1024(658F), x1024(208F), y1024(48F), 2, i18n("campnew.Start"));
            if(countryIcon != null)
                draw(x1024(32F), y1024(64F), x1024(64F), y1024(64F), countryIcon);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(256F), x1024(368F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(624F), x1024(960F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(448F), y1024(192F), x1024(544F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(432F), y1024(32F), 2.0F, y1024(576F));
        }

        public void setPosSize()
        {
            set1024PosSize(0.0F, 32F, 1024F, 736F);
            wCountry.setPosSize(x1024(112F), y1024(80F), x1024(288F), M(1.7F));
            wCampaign.setPosSize(x1024(464F), y1024(80F), x1024(528F), M(1.7F));
            wRank.setPosSize(x1024(112F), y1024(192F), x1024(288F), M(1.7F));
            wScrollDescription.setPosSize(x1024(464F), y1024(256F), x1024(528F), y1024(336F));
            bExit.setPosC(x1024(56F), y1024(682F));
            bDifficulty.setPosC(x1024(375F), y1024(313F));
            bStart.setPosC(x1024(968F), y1024(682F));
        }


        public DialogClient()
        {
        }
    }

    public class WComboCampaign extends com.maddox.gwindow.GWindowComboControl
    {

        public boolean notify(int i, int j)
        {
            if(i == 2)
            {
                int k = getSelected();
                if(k < 0)
                    return true;
                java.lang.Object obj = campaignLst.get(k);
                if(obj instanceof java.lang.String)
                {
                    campaign = (java.lang.String)obj;
                    dgenCampaignPrefix = null;
                    fillInfo();
                } else
                {
                    com.maddox.il2.gui.DGenCampaign dgencampaign = (com.maddox.il2.gui.DGenCampaign)obj;
                    campaign = dgencampaign.name;
                    dgenCampaignPrefix = dgencampaign.prefix;
                    dgenCampaignFileName = dgencampaign.fileName;
                    textDescription = dgencampaign.description;
                    wScrollDescription.resized();
                }
                return true;
            } else
            {
                return super.notify(i, j);
            }
        }

        public WComboCampaign(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2)
        {
            super(gwindow, f, f1, f2);
        }
    }

    public class WComboCountry extends com.maddox.gwindow.GWindowComboControl
    {

        public boolean notify(int i, int j)
        {
            if(i == 2)
            {
                int k = getSelected();
                if(k < 0)
                {
                    return true;
                } else
                {
                    fillCountry(k);
                    com.maddox.il2.game.Main3D.menuMusicPlay((java.lang.String)countryLst.get(k));
                    ((com.maddox.il2.gui.GUIRoot)root).setBackCountry("campaign", (java.lang.String)countryLst.get(k));
                    return true;
                }
            } else
            {
                return super.notify(i, j);
            }
        }

        public WComboCountry(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2)
        {
            super(gwindow, f, f1, f2);
        }
    }

    public class Descript extends com.maddox.gwindow.GWindowDialogClient
    {

        public void render()
        {
            java.lang.String s = textDescription;
            if(s != null)
            {
                com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
                setCanvasFont(0);
                setCanvasColorBLACK();
                drawLines(gbevel.L.dx + 2.0F, gbevel.T.dy + 2.0F, s, 0, s.length(), win.dx - gbevel.L.dx - gbevel.R.dx - 4F, root.C.font.height);
            }
        }

        public void computeSize()
        {
            java.lang.String s = textDescription;
            if(s != null)
            {
                win.dx = parentWindow.win.dx;
                com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
                setCanvasFont(0);
                int i = computeLines(s, 0, s.length(), win.dx - gbevel.L.dx - gbevel.R.dx - 4F);
                win.dy = root.C.font.height * (float)i + gbevel.T.dy + gbevel.B.dy + 4F;
                if(win.dy > parentWindow.win.dy)
                {
                    win.dx = parentWindow.win.dx - lookAndFeel().getVScrollBarW();
                    int j = computeLines(s, 0, s.length(), win.dx - gbevel.L.dx - gbevel.R.dx - 4F);
                    win.dy = root.C.font.height * (float)j + gbevel.T.dy + gbevel.B.dy + 4F;
                }
            } else
            {
                win.dx = parentWindow.win.dx;
                win.dy = parentWindow.win.dy;
            }
        }

        public Descript()
        {
        }
    }

    public class ScrollDescript extends com.maddox.gwindow.GWindowScrollingDialogClient
    {

        public void created()
        {
            fixed = wDescript = (com.maddox.il2.gui.Descript)create(new Descript());
            fixed.bNotify = true;
            bNotify = true;
        }

        public void resized()
        {
            if(wDescript != null)
                wDescript.computeSize();
            super.resized();
            if(vScroll.isVisible())
            {
                com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
                vScroll.setPos(win.dx - lookAndFeel().getVScrollBarW() - gbevel.R.dx, gbevel.T.dy);
                vScroll.setSize(lookAndFeel().getVScrollBarW(), win.dy - gbevel.T.dy - gbevel.B.dy);
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

        public void render()
        {
            setCanvasColorWHITE();
            com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
            lookAndFeel().drawBevel(this, 0.0F, 0.0F, win.dx, win.dy, gbevel, ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).basicelements, true);
        }

        public ScrollDescript()
        {
        }
    }

    static class DGenCampaign
    {

        java.lang.String fileName;
        java.lang.String prefix;
        java.lang.String name;
        java.lang.String description;

        DGenCampaign()
        {
        }
    }


    public void enterPush(com.maddox.il2.game.GameState gamestate)
    {
        dgenCampaignPrefix = null;
        com.maddox.il2.ai.World.cur().diffUser.set(com.maddox.il2.ai.World.cur().userCfg.singleDifficulty);
        enter(gamestate);
    }

    public void enterPop(com.maddox.il2.game.GameState gamestate)
    {
        if(gamestate.id() == 17)
        {
            com.maddox.il2.ai.World.cur().userCfg.singleDifficulty = com.maddox.il2.ai.World.cur().diffUser.get();
            com.maddox.il2.ai.World.cur().userCfg.saveConf();
        }
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
            } else
            {
                com.maddox.il2.game.Main.stateStack().change(28);
                return;
            }
        } else
        {
            difficulty = com.maddox.il2.ai.World.cur().diffUser.get();
            _enter();
            return;
        }
    }

    public void _enter()
    {
        init();
        int i = wCountry.getSelected();
        if(i >= 0)
            com.maddox.il2.game.Main3D.menuMusicPlay((java.lang.String)countryLst.get(i));
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

    private void init()
    {
        if(bInited)
            return;
        resCountry = java.util.ResourceBundle.getBundle("i18n/country", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
        _scanMap.put(resCountry.getString("ru"), "ru");
        _scanMap.put(resCountry.getString("de"), "de");
        java.io.File file = new File(com.maddox.rts.HomePath.get(0), "missions/campaign");
        if(file != null)
        {
            java.io.File afile[] = file.listFiles();
            if(afile != null)
            {
                for(int i = 0; i < afile.length; i++)
                {
                    if(!afile[i].isDirectory() || afile[i].isHidden())
                        continue;
                    java.lang.String s1 = afile[i].getName().toLowerCase();
                    java.lang.String s2 = null;
                    try
                    {
                        s2 = resCountry.getString(s1);
                    }
                    catch(java.lang.Exception exception)
                    {
                        continue;
                    }
                    if(!_scanMap.containsKey(s2))
                        _scanMap.put(s2, s1);
                }

            }
        }
        java.lang.String s;
        for(java.util.Iterator iterator = _scanMap.keySet().iterator(); iterator.hasNext(); wCountry.add(s))
        {
            s = (java.lang.String)iterator.next();
            countryLst.add(_scanMap.get(s));
        }

        _scanMap.clear();
        wCountry.setSelected(-1, false, true);
        if(countryLst.size() > 0)
            wCountry.setSelected(0, true, true);
        bInited = true;
    }

    private boolean fillRank()
    {
        try
        {
            resRank = java.util.ResourceBundle.getBundle("missions/campaign/" + country + "/" + "rank", com.maddox.rts.RTSConf.cur.locale);
            wRank.add(resRank.getString("0"));
            wRank.add(resRank.getString("1"));
            wRank.add(resRank.getString("2"));
            wRank.add(resRank.getString("3"));
            wRank.add(resRank.getString("4"));
            wRank.add(resRank.getString("5"));
            wRank.add(resRank.getString("6"));
        }
        catch(java.lang.Exception exception)
        {
            return false;
        }
        wRank.setSelected(0, true, false);
        return true;
    }

    private boolean fillCampaign()
    {
        campaignLst.clear();
        wCampaign.clear(false);
        fillDGen();
        _scanMap.clear();
        java.lang.String s = "missions/campaign/" + country + "/all.ini";
        if(exestFile(s))
        {
            com.maddox.rts.SectFile sectfile = new SectFile(s, 0);
            int i = sectfile.sectionIndex("list");
            if(i >= 0)
            {
                int j = sectfile.vars(i);
                for(int l = 0; l < j; l++)
                {
                    java.lang.String s3 = sectfile.var(i, l);
                    _scanMap.put(s3.toLowerCase(), null);
                }

            }
        }
        java.io.File file = new File(com.maddox.rts.HomePath.get(0), "missions/campaign/" + country);
        if(file != null)
        {
            java.io.File afile[] = file.listFiles();
            if(afile != null)
            {
                for(int k = 0; k < afile.length; k++)
                {
                    if(!afile[k].isDirectory() || afile[k].isHidden())
                        continue;
                    java.lang.String s2 = afile[k].getName();
                    if(s2.indexOf(" ") < 0)
                        _scanMap.put(s2.toLowerCase(), null);
                }

            }
        }
        if(_scanMap.size() > 0)
        {
            java.util.Iterator iterator = _scanMap.keySet().iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                java.lang.String s1 = (java.lang.String)iterator.next();
                try
                {
                    java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle("missions/campaign/" + country + "/" + s1 + "/info", com.maddox.rts.RTSConf.cur.locale);
                    java.lang.String s4 = resourcebundle.getString("Name");
                    com.maddox.rts.SectFile sectfile1 = new SectFile("missions/campaign/" + country + "/" + s1 + "/campaign.ini", 0);
                    int i1 = sectfile1.sectionIndex("list");
                    if(i1 >= 0 && sectfile1.vars(i1) > 0 && sectfile1.get("Main", "ExecGenerator", (java.lang.String)null) == null)
                    {
                        campaignLst.add(s1);
                        wCampaign.add(s4);
                    }
                }
                catch(java.lang.Exception exception) { }
            } while(true);
            _scanMap.clear();
        }
        if(campaignLst.size() == 0)
        {
            return false;
        } else
        {
            wCampaign.setSelected(-1, false, true);
            wCampaign.setSelected(0, true, true);
            return true;
        }
    }

    private void fillDGen()
    {
        java.lang.String s = com.maddox.rts.RTSConf.cur.locale.getLanguage();
        java.lang.String s1 = "campaigns" + country;
        java.lang.String s2 = null;
        if(!"us".equals(s))
            s2 = "_" + s + ".dat";
        java.lang.String s3 = ".dat";
        java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName("dgen", 0));
        java.lang.String as[] = file.list();
        if(as == null || as.length == 0)
            return;
        java.util.HashMap hashmap = new HashMap();
        if(s2 != null)
        {
            for(int i = 0; i < as.length; i++)
            {
                java.lang.String s4 = as[i];
                if(s4 != null && s4.length() == s1.length() + 1 + s2.length() && s4.regionMatches(true, 0, s1, 0, s1.length()) && s4.regionMatches(true, s4.length() - s2.length(), s2, 0, s2.length()))
                {
                    java.lang.String s8 = s4.substring(s1.length(), s1.length() + 1);
                    com.maddox.il2.gui.DGenCampaign dgencampaign2 = new DGenCampaign();
                    dgencampaign2.fileName = s4;
                    dgencampaign2.prefix = s8;
                    hashmap.put(s8, dgencampaign2);
                }
            }

        }
        for(int j = 0; j < as.length; j++)
        {
            java.lang.String s5 = as[j];
            if(s5 == null || s5.length() != s1.length() + 1 + s3.length() || !s5.regionMatches(true, 0, s1, 0, s1.length()) || !s5.regionMatches(true, s5.length() - s3.length(), s3, 0, s3.length()))
                continue;
            java.lang.String s9 = s5.substring(s1.length(), s1.length() + 1);
            if(!hashmap.containsKey(s9))
            {
                com.maddox.il2.gui.DGenCampaign dgencampaign3 = new DGenCampaign();
                dgencampaign3.fileName = s5;
                dgencampaign3.prefix = s9;
                hashmap.put(s9, dgencampaign3);
            }
        }

        if(hashmap.size() == 0)
            return;
        _scanMap.clear();
        java.util.Iterator iterator = hashmap.keySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.lang.String s6 = (java.lang.String)iterator.next();
            com.maddox.il2.gui.DGenCampaign dgencampaign = (com.maddox.il2.gui.DGenCampaign)hashmap.get(s6);
            try
            {
                java.io.BufferedReader bufferedreader = new BufferedReader(new SFSReader("dgen/" + dgencampaign.fileName, com.maddox.rts.RTSConf.charEncoding));
                dgencampaign.name = com.maddox.util.UnicodeTo8bit.load(bufferedreader.readLine(), false);
                dgencampaign.description = com.maddox.util.UnicodeTo8bit.load(bufferedreader.readLine(), false);
                bufferedreader.close();
                if(dgencampaign.name != null && dgencampaign.name.length() > 0 && dgencampaign.description != null && dgencampaign.description.length() > 0)
                    _scanMap.put(dgencampaign.name, dgencampaign);
            }
            catch(java.lang.Exception exception) { }
        } while(true);
        hashmap.clear();
        com.maddox.il2.gui.DGenCampaign dgencampaign1;
        for(java.util.Iterator iterator1 = _scanMap.keySet().iterator(); iterator1.hasNext(); wCampaign.add(dgencampaign1.name))
        {
            java.lang.String s7 = (java.lang.String)iterator1.next();
            dgencampaign1 = (com.maddox.il2.gui.DGenCampaign)_scanMap.get(s7);
            campaignLst.add(dgencampaign1);
        }

        _scanMap.clear();
    }

    private void fillInfo()
    {
        textDescription = null;
        try
        {
            java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle("missions/campaign/" + country + "/" + campaign + "/info", com.maddox.rts.RTSConf.cur.locale);
            textDescription = resourcebundle.getString("Description");
        }
        catch(java.lang.Exception exception) { }
        wScrollDescription.resized();
    }

    private void fillCountry(int i)
    {
        wRank.clear(false);
        wCampaign.clear(false);
        country = (java.lang.String)countryLst.get(i);
        if(!fillRank())
        {
            wRank.clear(false);
            country = null;
            return;
        }
        if(!fillCampaign())
        {
            wRank.clear(false);
            wCampaign.clear(false);
            country = null;
            return;
        } else
        {
            countryIcon = com.maddox.gwindow.GTexture.New("missions/campaign/" + country + "/icon.mat");
            return;
        }
    }

    private void doStartDGenCampaign()
    {
        com.maddox.il2.game.Main.cur().campaign = new CampaignDGen(dgenCampaignFileName, country, difficulty, wRank.getSelected(), dgenCampaignPrefix);
        com.maddox.il2.game.Main.stateStack().change(61);
    }

    private void doStartCampaign()
    {
        com.maddox.il2.game.campaign.Campaign campaign1 = null;
        try
        {
            java.lang.String s = country + campaign;
            java.lang.String s2 = "users/" + com.maddox.il2.ai.World.cur().userCfg.sId + "/campaigns.ini";
            com.maddox.rts.SectFile sectfile = new SectFile(s2, 1, false, com.maddox.il2.ai.World.cur().userCfg.krypto());
            com.maddox.rts.SectFile sectfile1 = new SectFile("missions/campaign/" + country + "/" + campaign + "/campaign.ini", 0);
            java.lang.String s3 = sectfile1.get("Main", "Class", (java.lang.String)null);
            java.lang.Class class1 = com.maddox.rts.ObjIO.classForName(s3);
            campaign1 = (com.maddox.il2.game.campaign.Campaign)class1.newInstance();
            class1 = com.maddox.rts.ObjIO.classForName(sectfile1.get("Main", "awardsClass", (java.lang.String)null));
            com.maddox.il2.game.campaign.Awards awards = (com.maddox.il2.game.campaign.Awards)class1.newInstance();
            campaign1.init(awards, country, campaign, difficulty, wRank.getSelected());
            campaign1._epilogueTrack = sectfile1.get("Main", "EpilogueTrack", (java.lang.String)null);
            sectfile.set("list", s, campaign1, true);
            campaign1.clearSavedStatics(sectfile);
            sectfile.saveFile();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
            return;
        }
        com.maddox.il2.game.Main.cur().campaign = campaign1;
        java.lang.String s1 = com.maddox.il2.game.Main.cur().campaign.nextIntro();
        if(s1 != null)
        {
            com.maddox.il2.gui.GUIBWDemoPlay.demoFile = s1;
            com.maddox.il2.gui.GUIBWDemoPlay.soundFile = null;
            com.maddox.il2.game.Main.stateStack().push(58);
            return;
        }
        com.maddox.il2.game.Main.cur().currentMissionFile = campaign1.nextMission();
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
            com.maddox.il2.game.Main.stateStack().change(28);
            return;
        }
    }

    public GUICampaignNew(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(26);
        dgenCampaignPrefix = null;
        dgenCampaignFileName = null;
        bInited = false;
        countryLst = new ArrayList();
        campaignLst = new ArrayList();
        _scanMap = new TreeMap();
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("campnew.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        wCountry = (com.maddox.il2.gui.WComboCountry)dialogClient.addControl(new WComboCountry(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wCountry.setEditable(false);
        wCampaign = (com.maddox.il2.gui.WComboCampaign)dialogClient.addControl(new WComboCampaign(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wCampaign.setEditable(false);
        wRank = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wRank.setEditable(false);
        dialogClient.create(wScrollDescription = new ScrollDescript());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bDifficulty = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bStart = (com.maddox.il2.gui.GUIButton)dialogClient.addDefault(new GUIButton(dialogClient, gtexture, 0.0F, 192F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public static final java.lang.String HOME_DIR = "missions/campaign";
    public static final java.lang.String RANK_FILE = "rank";
    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.WComboCountry wCountry;
    public com.maddox.gwindow.GTexture countryIcon;
    public com.maddox.il2.gui.WComboCampaign wCampaign;
    public com.maddox.gwindow.GWindowComboControl wRank;
    public com.maddox.il2.gui.ScrollDescript wScrollDescription;
    public com.maddox.il2.gui.Descript wDescript;
    public com.maddox.il2.gui.GUIButton bExit;
    public com.maddox.il2.gui.GUIButton bDifficulty;
    public com.maddox.il2.gui.GUIButton bStart;
    public java.lang.String country;
    public java.lang.String campaign;
    public java.lang.String dgenCampaignPrefix;
    public java.lang.String dgenCampaignFileName;
    public java.lang.String textDescription;
    public int difficulty;
    public boolean bInited;
    public java.util.ResourceBundle resRank;
    public java.util.ResourceBundle resCountry;
    public java.util.ArrayList countryLst;
    public java.util.ArrayList campaignLst;
    private java.util.TreeMap _scanMap;





}
