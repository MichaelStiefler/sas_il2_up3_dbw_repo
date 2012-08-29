// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIDGenPilotDetail.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSReader;
import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.gui:
//            GUIDGenRoster, GUIClient, GUIInfoMenu, GUIInfoName, 
//            GUILookAndFeel, GUIButton, GUIDialogClient, GUISeparate

public class GUIDGenPilotDetail extends com.maddox.il2.game.GameState
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
            draw(x1024(96F), y1024(658F), x1024(288F), y1024(48F), 0, i18n("camps.Back"));
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(624F), x1024(960F), 2.0F);
        }

        public void setPosSize()
        {
            set1024PosSize(0.0F, 32F, 1024F, 736F);
            bBack.setPosC(x1024(56F), y1024(682F));
            wTable.set1024PosSize(32F, 32F, 960F, 560F);
        }

        public DialogClient()
        {
        }
    }

    public class Table extends com.maddox.gwindow.GWindowTable
    {

        private void computeHeight(int i)
        {
            com.maddox.il2.gui.Event event = (com.maddox.il2.gui.Event)events.get(i);
            event.h = (int)(fnt.height * 1.2F);
            float f = fnt.height - fnt.descender;
            int j = (int)((float)computeLines(event.mission, 0, event.mission.length(), c[2]) * f);
            if(event.h < j)
                event.h = j;
            j = 0;
            for(int k = 0; k < event.actions.size(); k++)
            {
                java.lang.String s = (java.lang.String)event.actions.get(k);
                j += (int)((float)computeLines(s, 0, s.length(), c[4]) * f);
            }

            if(event.h < j)
                event.h = j;
        }

        private void computeHeights()
        {
            root.C.font = fnt;
            for(int i = 0; i < 5; i++)
                c[i] = (int)((com.maddox.gwindow.GWindowTable.Column)columns.get(i)).win.dx;

            int j = events.size();
            for(int k = 0; k < j; k++)
                computeHeight(k);

        }

        public float rowHeight(int i)
        {
            if(_events == null)
                return super.rowHeight(i);
            if(i >= events.size())
                return 0.0F;
            if(events.size() == 0)
                return 0.0F;
            boolean flag = false;
            for(int j = 0; j < 5; j++)
                if((float)c[j] != ((com.maddox.gwindow.GWindowTable.Column)columns.get(j)).win.dx)
                    flag = true;

            if(((com.maddox.il2.gui.Event)events.get(i)).h == 0)
                flag = true;
            if(flag)
                computeHeights();
            return (float)(((com.maddox.il2.gui.Event)events.get(i)).h + 2);
        }

        public float fullClientHeight()
        {
            if(_events == null)
                return super.fullClientHeight();
            int i = 0;
            int j = countRows();
            for(int k = 0; k < j; k++)
                i = (int)((float)i + rowHeight(k));

            return (float)i;
        }

        public int countRows()
        {
            if(_events == null)
                return 0;
            else
                return events.size();
        }

        public void renderCell(int i, int j, boolean flag, float f, float f1)
        {
            if(i > 0)
                com.maddox.il2.gui.GUISeparate.draw(this, myBrass, 0.0F, 0.0F, f, 1.0F);
            setCanvasColorBLACK();
            root.C.font = fnt;
            com.maddox.il2.gui.Event event = (com.maddox.il2.gui.Event)events.get(i);
            java.lang.String s = null;
            int k = 0;
            float f2 = fnt.height - fnt.descender;
            switch(j)
            {
            default:
                break;

            case 0: // '\0'
                s = event.date;
                k = 0;
                break;

            case 1: // '\001'
                s = event.plane;
                k = 0;
                break;

            case 2: // '\002'
                s = event.mission;
                drawLines(0.0F, 2.0F, s, 0, s.length(), c[2], f2);
                return;

            case 3: // '\003'
                if(event.flightTime == null)
                    s = "";
                else
                    s = event.flightTime;
                k = 1;
                break;

            case 4: // '\004'
                if(event.actions.size() > 0)
                {
                    int l = 2;
                    for(int i1 = 0; i1 < event.actions.size(); i1++)
                    {
                        s = (java.lang.String)event.actions.get(i1);
                        l += (int)((float)drawLines(0.0F, l, s, 0, s.length(), c[4], f2) * f2);
                    }

                }
                return;
            }
            setCanvasColorBLACK();
            draw(0.0F, 2.0F, f, (int)(fnt.height * 1.2F) - 2, k, s);
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
            fnt = com.maddox.gwindow.GFont.New("courSmall");
            bColumnsSizable = true;
            bSelecting = false;
            bSelectRow = false;
            addColumn(com.maddox.il2.game.I18N.gui("dgendetail.Date"), null);
            addColumn(com.maddox.il2.game.I18N.gui("dgendetail.Plane"), null);
            addColumn(com.maddox.il2.game.I18N.gui("dgendetail.Mission"), null);
            addColumn(com.maddox.il2.game.I18N.gui("dgendetail.FlightTime"), null);
            addColumn(com.maddox.il2.game.I18N.gui("dgendetail.Notes"), null);
            vSB.scroll = rowHeight(0);
            getColumn(0).setRelativeDx(10F);
            getColumn(1).setRelativeDx(10F);
            getColumn(2).setRelativeDx(20F);
            getColumn(3).setRelativeDx(10F);
            getColumn(4).setRelativeDx(20F);
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

        private java.util.ArrayList _events;
        private int c[];
        private com.maddox.gwindow.GFont fnt;
        private com.maddox.gwindow.GColor myBrass;

        public Table(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
            _events = events;
            c = new int[5];
            myBrass = new GColor(99, 89, 74);
        }
    }

    private static class Event
    {

        public java.lang.String date;
        public java.lang.String plane;
        public java.lang.String flightTime;
        public java.lang.String mission;
        public java.util.ArrayList actions;
        public int h;

        private Event()
        {
            actions = new ArrayList();
        }

    }


    public void _enter()
    {
        loadEvents();
        client.activateWindow();
        wTable.resized();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    private void loadEvents()
    {
        try
        {
            roster = (com.maddox.il2.gui.GUIDGenRoster)com.maddox.il2.game.GameState.get(65);
            com.maddox.il2.game.campaign.Campaign campaign = com.maddox.il2.game.Main.cur().campaign;
            java.lang.String s = "missions/campaign/" + campaign.branch() + "/" + campaign.missionsDir() + "/logbook.dat";
            java.io.BufferedReader bufferedreader = new BufferedReader(new SFSReader(s, com.maddox.rts.RTSConf.charEncoding));
            events.clear();
            for(com.maddox.il2.gui.Event event = null; (event = loadEvent(bufferedreader)) != null;)
                events.add(event);

            bufferedreader.close();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("Squadron file load failed: " + exception.getMessage());
            exception.printStackTrace();
            com.maddox.il2.game.Main.stateStack().pop();
        }
    }

    private com.maddox.il2.gui.Event loadEvent(java.io.BufferedReader bufferedreader)
        throws java.io.IOException
    {
        com.maddox.il2.gui.Event event = new Event();
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
            if(s.startsWith("DATE:"))
                event.date = roster.readArgStr(com.maddox.util.UnicodeTo8bit.load(s, false));
            else
            if(s.startsWith("PLANE:"))
            {
                event.plane = roster.readArgStr(s);
                try
                {
                    java.lang.Class class1 = com.maddox.rts.ObjIO.classForName("air." + event.plane);
                    java.lang.String s1 = com.maddox.rts.Property.stringValue(class1, "keyName", null);
                    if(s1 != null)
                        event.plane = com.maddox.il2.game.I18N.plane(s1);
                }
                catch(java.lang.Throwable throwable) { }
            } else
            if(s.startsWith("MISSION:"))
                event.mission = roster.readArgStr(com.maddox.util.UnicodeTo8bit.load(s, false));
            else
            if(s.startsWith("EVENT:"))
                event.actions.add(roster.readArgStr(com.maddox.util.UnicodeTo8bit.load(s, false)));
            else
            if(s.startsWith("FLIGHT TIME:"))
            {
                event.flightTime = roster.readArgStr(s);
                flag = true;
            }
        } while(!flag);
        if(event.date == null || event.plane == null || event.mission == null)
            return null;
        else
            return event;
    }

    public GUIDGenPilotDetail(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(67);
        events = new ArrayList();
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("dgendetail.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        wTable = new Table(dialogClient);
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bBack = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.Table wTable;
    public com.maddox.il2.gui.GUIButton bBack;
    private java.util.ArrayList events;
    private com.maddox.il2.gui.GUIDGenRoster roster;

}
