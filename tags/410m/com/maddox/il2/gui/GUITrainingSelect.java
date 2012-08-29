// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUITrainingSelect.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.rts.KeyRecord;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import java.util.ArrayList;
import java.util.ResourceBundle;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUIDialogClient, GUISeparate

public class GUITrainingSelect extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == wPrev)
            {
                com.maddox.il2.game.Main3D.cur3D().viewSet_Load();
                com.maddox.il2.game.Main.stateStack().pop();
            } else
            if(gwindow == wPlay)
            {
                int k = wTable.selectRow;
                if(k < 0 || k >= wTable.tracks.size())
                {
                    return true;
                } else
                {
                    selectedTrack = (java.lang.String)wTable.tracks.get(k);
                    com.maddox.il2.game.Main.stateStack().push(57);
                    return true;
                }
            }
            return super.notify(gwindow, i, j);
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(300F), x1024(832F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(96F), y1024(332F), x1024(240F), y1024(48F), 0, i18n("training.MainMenu"));
            draw(x1024(512F), y1024(332F), x1024(288F), y1024(48F), 2, i18n("training.Play"));
        }

        public void setPosSize()
        {
            set1024PosSize(64F, 194F, 896F, 412F);
            wPrev.setPosC(x1024(56F), y1024(356F));
            wPlay.setPosC(x1024(840F), y1024(356F));
            wTable.setPosSize(x1024(32F), y1024(32F), x1024(832F), y1024(236F));
        }

        public DialogClient()
        {
        }
    }

    public class Table extends com.maddox.gwindow.GWindowTable
    {

        public int countRows()
        {
            return tracks == null ? 0 : tracks.size();
        }

        public void renderCell(int i, int j, boolean flag, float f, float f1)
        {
            setCanvasFont(0);
            if(flag)
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, lookAndFeel().regionWhite);
                setCanvasColorWHITE();
                draw(0.0F, 0.0F, f, f1, 0, (java.lang.String)names.get(i));
            } else
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, 0, (java.lang.String)names.get(i));
            }
        }

        public void afterCreated()
        {
            super.afterCreated();
            bColumnsSizable = false;
            addColumn(com.maddox.il2.game.I18N.gui("training.Tracks"), null);
            vSB.scroll = rowHeight(0);
            resized();
        }

        public void resolutionChanged()
        {
            vSB.scroll = rowHeight(0);
            super.resolutionChanged();
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

        public java.util.ArrayList tracks;
        public java.util.ArrayList names;

        public Table(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
            tracks = new ArrayList();
            names = new ArrayList();
            bNotify = true;
            wClient.bNotify = true;
        }
    }


    public void _enter()
    {
        if(com.maddox.il2.game.Mission.cur() != null && !com.maddox.il2.game.Mission.cur().isDestroyed())
            com.maddox.il2.game.Mission.cur().destroy();
        if(com.maddox.il2.game.Main3D.cur3D().keyRecord != null)
            com.maddox.il2.game.Main3D.cur3D().keyRecord.clearRecorded();
        fillTracks();
        client.activateWindow();
    }

    public void leavePop(com.maddox.il2.game.GameState gamestate)
    {
        com.maddox.il2.ai.World.cur().setUserCovers();
        super.leavePop(gamestate);
    }

    public void _leave()
    {
        client.hideWindow();
    }

    public void fillTracks()
    {
        wTable.tracks.clear();
        com.maddox.rts.SectFile sectfile = new SectFile("Training/all.ini", 0);
        int i = sectfile.sectionIndex("all");
        if(i >= 0)
        {
            java.util.ResourceBundle resourcebundle = null;
            try
            {
                resourcebundle = java.util.ResourceBundle.getBundle("Training/all", com.maddox.rts.RTSConf.cur.locale);
            }
            catch(java.lang.Exception exception) { }
            int j = sectfile.vars(i);
            for(int k = 0; k < j; k++)
            {
                java.lang.String s = sectfile.line(i, k);
                wTable.tracks.add(s);
                try
                {
                    wTable.names.add(resourcebundle.getString(s));
                }
                catch(java.lang.Exception exception1)
                {
                    wTable.names.add(s);
                }
            }

            if(j > 0)
                wTable.setSelect(0, 0);
        }
        wTable.resized();
    }

    public GUITrainingSelect(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(56);
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("training.infoSelect");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        wTable = new Table(dialogClient);
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        wPrev = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        wPlay = (com.maddox.il2.gui.GUIButton)dialogClient.addDefault(new GUIButton(dialogClient, gtexture, 0.0F, 192F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public java.lang.String selectedTrack;
    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton wPrev;
    public com.maddox.il2.gui.GUIButton wPlay;
    public com.maddox.il2.gui.Table wTable;
}
