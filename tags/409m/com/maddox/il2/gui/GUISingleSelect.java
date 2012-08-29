// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUISingleSelect.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.HomePath;
import com.maddox.rts.LDRres;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUIDialogClient, GUIRoot, GUISeparate

public class GUISingleSelect extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == wPrev)
            {
                com.maddox.il2.game.Main.stateStack().pop();
                return true;
            }
            if(gwindow == wCountry)
            {
                fillDirs();
                int k = wCountry.getSelected();
                if(k >= 0)
                {
                    com.maddox.il2.game.Main3D.menuMusicPlay((java.lang.String)countryLst.get(k));
                    ((com.maddox.il2.gui.GUIRoot)root).setBackCountry("single", (java.lang.String)countryLst.get(k));
                }
                return true;
            }
            if(gwindow == wDirs)
            {
                fillFiles();
                return true;
            }
            if(gwindow == wNext)
            {
                if(wDirs.getValue() == null)
                    return true;
                int l = wTable.selectRow;
                if(l < 0 || l >= wTable.files.size())
                {
                    return true;
                } else
                {
                    com.maddox.il2.gui.FileMission filemission = (com.maddox.il2.gui.FileMission)wTable.files.get(l);
                    int i1 = wCountry.getSelected();
                    com.maddox.il2.game.Main.cur().currentMissionFile = new SectFile("missions/single/" + country + "/" + wDirs.getValue() + "/" + filemission.fileName, 0);
                    com.maddox.il2.game.Main.stateStack().push(4);
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
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(432F), y1024(546F), x1024(384F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(416F), y1024(32F), 2.0F, y1024(608F));
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(64F), y1024(40F), x1024(240F), y1024(32F), 0, i18n("singleSelect.Country"));
            draw(x1024(64F), y1024(156F), x1024(240F), y1024(32F), 0, i18n("singleSelect.MissType"));
            draw(x1024(64F), y1024(264F), x1024(240F), y1024(32F), 0, i18n("singleSelect.Miss"));
            draw(x1024(464F), y1024(264F), x1024(248F), y1024(32F), 0, i18n("singleSelect.Desc"));
            draw(x1024(104F), y1024(592F), x1024(192F), y1024(48F), 0, i18n("singleSelect.MainMenu"));
            draw(x1024(528F), y1024(592F), x1024(216F), y1024(48F), 2, i18n("singleSelect.Brief"));
        }

        public void setPosSize()
        {
            set1024PosSize(80F, 64F, 848F, 672F);
            wPrev.setPosC(x1024(56F), y1024(616F));
            wNext.setPosC(x1024(792F), y1024(616F));
            wCountry.setPosSize(x1024(48F), y1024(80F), x1024(336F), M(2.0F));
            wDirs.setPosSize(x1024(48F), y1024(192F), x1024(336F), M(2.0F));
            wTable.setPosSize(x1024(48F), y1024(304F), x1024(336F), y1024(256F));
            wDescript.setPosSize(x1024(448F), y1024(312F), x1024(354F), y1024(212F));
        }

        public DialogClient()
        {
        }
    }

    public class WDescript extends com.maddox.gwindow.GWindow
    {

        public void render()
        {
            java.lang.String s = null;
            if(wTable.selectRow >= 0)
            {
                s = ((com.maddox.il2.gui.FileMission)wTable.files.get(wTable.selectRow)).description;
                if(s != null && s.length() == 0)
                    s = null;
            }
            if(s != null)
            {
                setCanvasFont(0);
                setCanvasColorBLACK();
                drawLines(0.0F, -root.C.font.descender, s, 0, s.length(), win.dx, root.C.font.height);
            }
        }

        public WDescript()
        {
        }
    }

    public class Table extends com.maddox.gwindow.GWindowTable
    {

        public int countRows()
        {
            return files == null ? 0 : files.size();
        }

        public void renderCell(int i, int j, boolean flag, float f, float f1)
        {
            setCanvasFont(0);
            java.lang.String s = ((com.maddox.il2.gui.FileMission)files.get(i)).name;
            if(flag)
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, lookAndFeel().regionWhite);
                setCanvasColorWHITE();
                draw(0.0F, 0.0F, f, f1, 0, s);
            } else
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, 0, s);
            }
        }

        public void afterCreated()
        {
            super.afterCreated();
            bColumnsSizable = false;
            addColumn(com.maddox.il2.game.I18N.gui("singleSelect.MissFiles"), null);
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

        public java.util.ArrayList files;

        public Table(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow, 2.0F, 4F, 20F, 16F);
            files = new ArrayList();
            bNotify = true;
            wClient.bNotify = true;
        }
    }

    static class FileMission
    {

        public java.lang.String fileName;
        public java.lang.String name;
        public java.lang.String description;

        public FileMission(java.lang.String s, java.lang.String s1)
        {
            fileName = s1;
            try
            {
                java.lang.String s2 = s1;
                int i = s2.lastIndexOf(".");
                if(i >= 0)
                    s2 = s2.substring(0, i);
                java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle(s + "/" + s2, com.maddox.rts.RTSConf.cur.locale);
                name = resourcebundle.getString("Name");
                description = resourcebundle.getString("Short");
            }
            catch(java.lang.Exception exception)
            {
                name = s1;
                description = null;
            }
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

    private void init()
    {
        if(bInited)
            return;
        resCountry = java.util.ResourceBundle.getBundle("i18n/country", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
        _scanMap.put(resCountry.getString("ru"), "ru");
        _scanMap.put(resCountry.getString("de"), "de");
        java.io.File file = new File(com.maddox.rts.HomePath.get(0), "missions/single");
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

    public void fillDirs()
    {
        countryIcon = null;
        country = null;
        int i = wCountry.getSelected();
        if(i < 0)
        {
            wDirs.clear(false);
            wTable.files.clear();
            wTable.setSelect(-1, 0);
            return;
        }
        country = (java.lang.String)countryLst.get(i);
        java.io.File file = new File(com.maddox.rts.HomePath.get(0), "missions/single/" + country);
        java.io.File afile[] = file.listFiles();
        wDirs.clear(false);
        if(afile == null || afile.length == 0)
        {
            wTable.files.clear();
            wTable.setSelect(-1, 0);
            return;
        }
        for(int j = 0; j < afile.length; j++)
            if(afile[j].isDirectory() && !afile[j].isHidden() && !".".equals(afile[j].getName()) && !"..".equals(afile[j].getName()))
                _scanMap.put(afile[j].getName(), null);

        for(java.util.Iterator iterator = _scanMap.keySet().iterator(); iterator.hasNext(); wDirs.add((java.lang.String)iterator.next()));
        if(_scanMap.size() > 0)
            wDirs.setSelected(0, true, false);
        _scanMap.clear();
        fillFiles();
    }

    public void fillFiles()
    {
        wTable.files.clear();
        java.lang.String s = wDirs.getValue();
        int i = wCountry.getSelected();
        if(s != null)
        {
            java.lang.String s1 = "missions/single/" + country + "/" + s;
            java.io.File file = new File(com.maddox.rts.HomePath.get(0), s1);
            java.io.File afile[] = file.listFiles();
            if(afile != null && afile.length > 0)
            {
                for(int j = 0; j < afile.length; j++)
                    if(!afile[j].isDirectory() && !afile[j].isHidden() && afile[j].getName().toLowerCase().lastIndexOf(".properties") < 0)
                    {
                        com.maddox.il2.gui.FileMission filemission = new FileMission(s1, afile[j].getName());
                        _scanMap.put(filemission.fileName, filemission);
                    }

                for(java.util.Iterator iterator = _scanMap.keySet().iterator(); iterator.hasNext(); wTable.files.add(_scanMap.get(iterator.next())));
                if(_scanMap.size() > 0)
                    wTable.setSelect(0, 0);
                else
                    wTable.setSelect(-1, 0);
                _scanMap.clear();
            } else
            {
                wTable.setSelect(-1, 0);
            }
        } else
        {
            wTable.setSelect(-1, 0);
        }
        wTable.resized();
    }

    public GUISingleSelect(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(3);
        bInited = false;
        countryLst = new ArrayList();
        _scanMap = new TreeMap();
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("singleSelect.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        wCountry = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wCountry.setEditable(false);
        wDirs = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wDirs.setEditable(false);
        wTable = new Table(dialogClient);
        dialogClient.create(wDescript = new WDescript());
        wDescript.bNotify = true;
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        wPrev = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        wNext = (com.maddox.il2.gui.GUIButton)dialogClient.addDefault(new GUIButton(dialogClient, gtexture, 0.0F, 192F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public static final java.lang.String HOME_DIR = "missions/single";
    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton wPrev;
    public com.maddox.il2.gui.GUIButton wNext;
    public com.maddox.gwindow.GWindowComboControl wCountry;
    public com.maddox.gwindow.GTexture countryIcon;
    public com.maddox.gwindow.GWindowComboControl wDirs;
    public com.maddox.il2.gui.Table wTable;
    public com.maddox.il2.gui.WDescript wDescript;
    public java.lang.String country;
    public boolean bInited;
    public java.util.ResourceBundle resCountry;
    public java.util.ArrayList countryLst;
    public java.util.TreeMap _scanMap;
}
