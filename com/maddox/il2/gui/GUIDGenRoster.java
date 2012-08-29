// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIDGenRoster.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.campaign.Awards;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.il2.objects.air.PaintScheme;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSReader;
import com.maddox.util.SharedTokenizer;
import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUIDGenDocs, GUIDialogClient, GUISeparate

public class GUIDGenRoster extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bBack)
            {
                com.maddox.il2.game.Main.stateStack().pop();
                return true;
            }
            if(gwindow == bDocs)
            {
                com.maddox.il2.game.Main.stateStack().push(70);
                return true;
            }
            if(gwindow == bProfile)
            {
                int k = wTable.selectRow;
                if(k >= 0)
                {
                    pilotCur = (com.maddox.il2.gui.Pilot)pilots.get(k);
                } else
                {
                    int l = wGone.selectRow;
                    if(l < 0)
                        return true;
                    pilotCur = (com.maddox.il2.gui.Pilot)gones.get(l);
                }
                com.maddox.il2.game.Main.stateStack().push(66);
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            setCanvasColorWHITE();
            draw(x1024(80F), y1024(32F), x1024(64F), y1024(64F), texRegiment);
            setCanvasFont(1);
            draw(x1024(160F), y1024(48F), x1024(786F), y1024(32F), 0, com.maddox.il2.game.I18N.regimentInfo(regiment.info()));
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(112F), y1024(354F), x1024(512F), y1024(32F), 0, i18n("dgenroster.Gone"));
            draw(x1024(176F), y1024(560F), x1024(384F), y1024(32F), 2, i18n("dgenroster.Flight"));
            draw(x1024(176F), y1024(592F), x1024(384F), y1024(32F), 2, i18n("dgenroster.Kills"));
            draw(x1024(576F), y1024(560F), x1024(208F), y1024(32F), 0, "" + sorties);
            draw(x1024(576F), y1024(592F), x1024(208F), y1024(32F), 0, "" + kills);
            draw(x1024(96F), y1024(656F), x1024(320F), y1024(48F), 0, i18n("camps.Back"));
            if(bDocs.isVisible())
                draw(x1024(560F), y1024(656F), x1024(320F), y1024(48F), 0, i18n("camps.Docs"));
            if(bProfile.isVisible())
                draw(x1024(432F), y1024(656F), x1024(498F), y1024(48F), 2, i18n("dgenroster.PilotProfile"));
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(640F), x1024(962F), 2.0F);
        }

        public void setPosSize()
        {
            set1024PosSize(0.0F, 32F, 1024F, 736F);
            bBack.setPosC(x1024(56F), y1024(680F));
            bDocs.setPosC(x1024(512F), y1024(680F));
            bProfile.setPosC(x1024(968F), y1024(680F));
            wTable.set1024PosSize(96F, 112F, 832F, 240F);
            wGone.set1024PosSize(96F, 384F, 832F, 160F);
        }

        public DialogClient()
        {
        }
    }

    public class Gone extends com.maddox.gwindow.GWindowTable
    {

        public int countRows()
        {
            return playerList == null ? 0 : gones.size();
        }

        public void renderCell(int i, int j, boolean flag, float f, float f1)
        {
            setCanvasFont(0);
            if(flag)
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, lookAndFeel().regionWhite);
            }
            com.maddox.il2.gui.Pilot pilot = (com.maddox.il2.gui.Pilot)gones.get(i);
            java.lang.String s = null;
            int k = 0;
            switch(j)
            {
            case 0: // '\0'
                s = pilot.sRank;
                k = 0;
                break;

            case 1: // '\001'
                s = pilot.lastName;
                k = 0;
                break;

            case 2: // '\002'
                s = "";
                if(pilot.events.size() > 0)
                    s = (java.lang.String)pilot.events.get(pilot.events.size() - 1);
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

        public void setSelect(int i, int j)
        {
            super.setSelect(i, j);
            if(i >= 0)
            {
                wTable.setSelect(-1, -1);
                bProfile.showWindow();
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
            addColumn(com.maddox.il2.game.I18N.gui("dgenroster.Rank"), null);
            addColumn(com.maddox.il2.game.I18N.gui("dgenroster.Name"), null);
            addColumn(com.maddox.il2.game.I18N.gui("dgenroster.To"), null);
            vSB.scroll = rowHeight(0);
            getColumn(0).setRelativeDx(17F);
            getColumn(1).setRelativeDx(17F);
            getColumn(2).setRelativeDx(46F);
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

        public java.util.ArrayList playerList;

        public Gone(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
            playerList = gones;
        }
    }

    public class Table extends com.maddox.gwindow.GWindowTable
    {

        public int countRows()
        {
            return playerList == null ? 0 : pilots.size();
        }

        public void renderCell(int i, int j, boolean flag, float f, float f1)
        {
            setCanvasFont(0);
            com.maddox.il2.gui.Pilot pilot = (com.maddox.il2.gui.Pilot)pilots.get(i);
            if(flag)
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, lookAndFeel().regionWhite);
            } else
            if(pilot == pilotPlayer)
            {
                setCanvasColor(playerColor);
                draw(0.0F, 0.0F, f, f1, lookAndFeel().regionWhite);
            }
            java.lang.String s = null;
            int k = 0;
            switch(j)
            {
            case 0: // '\0'
                s = pilot.sRank;
                k = 0;
                break;

            case 1: // '\001'
                s = pilot.lastName;
                k = 0;
                break;

            case 2: // '\002'
                s = "" + pilot.sorties;
                k = 1;
                break;

            case 3: // '\003'
                s = "" + pilot.kills;
                k = 1;
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

        public void setSelect(int i, int j)
        {
            super.setSelect(i, j);
            if(i >= 0)
            {
                wGone.setSelect(-1, -1);
                bProfile.showWindow();
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
            addColumn(com.maddox.il2.game.I18N.gui("dgenroster.Rank"), null);
            addColumn(com.maddox.il2.game.I18N.gui("dgenroster.Name"), null);
            addColumn(com.maddox.il2.game.I18N.gui("dgenroster.Sorties"), null);
            addColumn(com.maddox.il2.game.I18N.gui("dgenroster.Kills"), null);
            vSB.scroll = rowHeight(0);
            getColumn(0).setRelativeDx(17F);
            getColumn(1).setRelativeDx(17F);
            getColumn(2).setRelativeDx(23F);
            getColumn(3).setRelativeDx(23F);
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

        public java.util.ArrayList playerList;
        com.maddox.gwindow.GColor playerColor;

        public Table(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
            playerList = pilots;
            playerColor = new GColor(35, 117, 137);
        }
    }

    static class PilotComparator
        implements java.util.Comparator
    {

        public int compare(java.lang.Object obj, java.lang.Object obj1)
        {
            com.maddox.il2.gui.Pilot pilot = (com.maddox.il2.gui.Pilot)obj;
            com.maddox.il2.gui.Pilot pilot1 = (com.maddox.il2.gui.Pilot)obj1;
            int i = pilot1.rank - pilot.rank;
            if(i == 0)
                i = pilot.lastName.compareToIgnoreCase(pilot1.lastName);
            return i;
        }

        PilotComparator()
        {
        }
    }

    public static class Pilot
    {

        public boolean bPlayer;
        public java.lang.String firstName;
        public java.lang.String lastName;
        public java.lang.String dateBirth;
        public java.lang.String placeBirth;
        public java.lang.String photo;
        public int rank;
        public java.lang.String sRank;
        public int sorties;
        public int kills;
        public int ground;
        public int nmedals;
        public int medals[];
        public java.util.ArrayList events;

        public Pilot()
        {
            events = new ArrayList();
        }
    }


    public void enterPush(com.maddox.il2.game.GameState gamestate)
    {
        loadPilotList();
        loadGoneList();
        loadRegiment();
        pilotCur = null;
        wTable.setSelect(-1, -1);
        wGone.setSelect(-1, -1);
        bProfile.hideWindow();
        if(com.maddox.il2.gui.GUIDGenDocs.isExist())
            bDocs.showWindow();
        else
            bDocs.hideWindow();
        client.activateWindow();
        wTable.resized();
        wGone.resized();
    }

    public void enterPop(com.maddox.il2.game.GameState gamestate)
    {
        client.activateWindow();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    public void loadPilotList()
    {
        java.util.TreeMap treemap = new TreeMap(new PilotComparator());
        java.util.ResourceBundle resourcebundle = null;
        pilotPlayer = null;
        try
        {
            resourcebundle = java.util.ResourceBundle.getBundle("missions/campaign/" + com.maddox.il2.game.Main.cur().campaign.branch() + "/" + "rank", com.maddox.rts.RTSConf.cur.locale);
        }
        catch(java.lang.Exception exception) { }
        try
        {
            com.maddox.il2.game.campaign.Campaign campaign = com.maddox.il2.game.Main.cur().campaign;
            pilots.clear();
            sorties = 0;
            kills = 0;
            com.maddox.il2.gui.Pilot pilot = null;
            java.lang.String s = "missions/campaign/" + campaign.branch() + "/" + campaign.missionsDir() + "/squadron.dat";
            java.io.BufferedReader bufferedreader = new BufferedReader(new SFSReader(s, com.maddox.rts.RTSConf.charEncoding));
            while((pilot = loadPilot(bufferedreader)) != null) 
            {
                if(pilotPlayer == null)
                {
                    pilot.bPlayer = true;
                    pilotPlayer = pilot;
                } else
                {
                    treemap.put(pilot, pilot);
                }
                if(resourcebundle != null)
                    pilot.sRank = resourcebundle.getString("" + pilot.rank);
                else
                    pilot.sRank = "";
                sorties += pilot.sorties;
                kills += pilot.kills;
            }
            bufferedreader.close();
        }
        catch(java.lang.Exception exception1)
        {
            java.lang.System.out.println("Squadron file load failed: " + exception1.getMessage());
            exception1.printStackTrace();
            com.maddox.il2.game.Main.stateStack().pop();
        }
        if(pilotPlayer == null)
            return;
        treemap.put(pilotPlayer, pilotPlayer);
        for(java.util.Iterator iterator = treemap.keySet().iterator(); iterator.hasNext(); pilots.add(iterator.next()));
    }

    private void loadGoneList()
    {
        java.util.ResourceBundle resourcebundle = null;
        try
        {
            resourcebundle = java.util.ResourceBundle.getBundle("missions/campaign/" + com.maddox.il2.game.Main.cur().campaign.branch() + "/" + "rank", com.maddox.rts.RTSConf.cur.locale);
        }
        catch(java.lang.Exception exception) { }
        try
        {
            com.maddox.il2.game.campaign.Campaign campaign = com.maddox.il2.game.Main.cur().campaign;
            gones.clear();
            java.lang.String s = "missions/campaign/" + campaign.branch() + "/" + campaign.missionsDir() + "/archive.dat";
            java.io.BufferedReader bufferedreader = new BufferedReader(new SFSReader(s, com.maddox.rts.RTSConf.charEncoding));
            for(com.maddox.il2.gui.Pilot pilot = null; (pilot = loadPilot(bufferedreader)) != null;)
            {
                gones.add(pilot);
                if(resourcebundle != null)
                    pilot.sRank = resourcebundle.getString("" + pilot.rank);
                else
                    pilot.sRank = "";
                sorties += pilot.sorties;
                kills += pilot.kills;
            }

            bufferedreader.close();
        }
        catch(java.lang.Exception exception1) { }
    }

    public com.maddox.il2.gui.Pilot loadPilot(java.io.BufferedReader bufferedreader)
        throws java.io.IOException
    {
        com.maddox.il2.gui.Pilot pilot = new Pilot();
        boolean flag;
label0:
        do
        {
            java.lang.String s;
            int i;
            do
            {
                if(!bufferedreader.ready())
                    break label0;
                s = bufferedreader.readLine();
                if(s == null)
                    break label0;
                i = s.length();
            } while(i == 0);
            flag = false;
            if(s.startsWith("--------"))
                flag = true;
            else
            if(s.startsWith("FIRST NAME:"))
                pilot.firstName = readArgStr(com.maddox.util.UnicodeTo8bit.load(s, false));
            else
            if(s.startsWith("LAST NAME:"))
                pilot.lastName = readArgStr(com.maddox.util.UnicodeTo8bit.load(s, false));
            else
            if(s.startsWith("DATE OF BIRTH:"))
                pilot.dateBirth = readArgStr(com.maddox.util.UnicodeTo8bit.load(s, false));
            else
            if(s.startsWith("PLACE OF BIRTH:"))
                pilot.placeBirth = readArgStr(com.maddox.util.UnicodeTo8bit.load(s, false));
            else
            if(s.startsWith("PHOTO:"))
                pilot.photo = readArgStr(s);
            else
            if(s.startsWith("RANK:"))
                pilot.rank = readArgInt(s, 0);
            else
            if(s.startsWith("SORTIES:"))
                pilot.sorties = readArgInt(s, 0);
            else
            if(s.startsWith("KILLS:"))
                pilot.kills = readArgInt(s, 0);
            else
            if(s.startsWith("GROUND:"))
                pilot.ground = readArgInt(s, 0);
            else
            if(s.startsWith("NMEDALS:"))
                pilot.nmedals = readArgInt(s, 0);
            else
            if(s.startsWith("MEDALS:"))
            {
                com.maddox.util.SharedTokenizer.set(s);
                int j = com.maddox.il2.game.Main.cur().campaign._awards.count(0xf4240) - 1;
                int k = com.maddox.util.SharedTokenizer.countTokens() - 1;
                if(k > 0)
                {
                    pilot.medals = new int[k];
                    com.maddox.util.SharedTokenizer.next();
                    for(int l = 0; l < k; l++)
                        pilot.medals[l] = com.maddox.util.SharedTokenizer.next(0, 0, j);

                }
            } else
            if(s.startsWith("EVENT:"))
                pilot.events.add(readArgStr(com.maddox.util.UnicodeTo8bit.load(s, false)));
        } while(!flag);
        if(pilot.firstName == null || pilot.lastName == null)
            return null;
        else
            return pilot;
    }

    public int readArgInt(java.lang.String s, int i)
    {
        java.lang.String s1;
        s1 = readArgStr(s);
        if("".equals(s1))
            return i;
        return java.lang.Integer.parseInt(s1);
        java.lang.Exception exception;
        exception;
        return i;
    }

    public java.lang.String readArgStr(java.lang.String s)
    {
        int i = s.indexOf(":") + 1;
        int j;
        for(j = s.length(); i < j && s.charAt(i) <= ' '; i++);
        if(i >= j)
            return "";
        for(; i < j && s.charAt(j - 1) <= ' '; j--);
        if(i == j)
        {
            return "";
        } else
        {
            java.lang.String s1 = s.substring(i, j);
            return s1;
        }
    }

    private void loadRegiment()
    {
        try
        {
            com.maddox.il2.game.campaign.Campaign campaign = com.maddox.il2.game.Main.cur().campaign;
            java.lang.String s = "missions/campaign/" + campaign.branch() + "/" + campaign.missionsDir() + "/conf.dat";
            java.io.BufferedReader bufferedreader = new BufferedReader(new SFSReader(s, com.maddox.rts.RTSConf.charEncoding));
            bufferedreader.readLine();
            java.lang.String s1 = bufferedreader.readLine();
            s1 = s1.trim();
            bufferedreader.close();
            regiment = (com.maddox.il2.ai.Regiment)com.maddox.il2.engine.Actor.getByName(s1);
            com.maddox.il2.engine.Mat mat = com.maddox.il2.objects.air.PaintScheme.makeMat(regiment.name(), regiment.fileNameTga(), 1.0F, 1.0F, 1.0F);
            texRegiment = com.maddox.gwindow.GTexture.New(mat.Name());
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("Conf file load failed: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    public GUIDGenRoster(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(65);
        pilots = new ArrayList();
        gones = new ArrayList();
        pilotPlayer = null;
        pilotCur = null;
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("dgenroster.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        wTable = new Table(dialogClient);
        wGone = new Gone(dialogClient);
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bBack = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bDocs = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bProfile = (com.maddox.il2.gui.GUIButton)dialogClient.addDefault(new GUIButton(dialogClient, gtexture, 0.0F, 192F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public java.util.ArrayList pilots;
    public java.util.ArrayList gones;
    public com.maddox.il2.gui.Pilot pilotPlayer;
    public com.maddox.il2.gui.Pilot pilotCur;
    private com.maddox.il2.ai.Regiment regiment;
    private com.maddox.gwindow.GTexture texRegiment;
    private int sorties;
    private int kills;
    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.Table wTable;
    public com.maddox.il2.gui.Gone wGone;
    public com.maddox.il2.gui.GUIButton bBack;
    public com.maddox.il2.gui.GUIButton bDocs;
    public com.maddox.il2.gui.GUIButton bProfile;




}
