// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIObjectInspector.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowScrollingDialogClient;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.LDRres;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.util.ArrayList;
import java.util.ResourceBundle;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUIDialogClient, GUISeparate

public class GUIObjectInspector extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == wCountry)
            {
                fillObjects();
                int k = wCountry.getSelected();
                if(k >= 0)
                {
                    com.maddox.il2.game.Main3D.menuMusicPlay(k != 0 ? "de" : "ru");
                    com.maddox.il2.gui.GUIObjectInspector.s_country = wCountry.getSelected();
                    if(wTable.countRows() > 0)
                    {
                        com.maddox.il2.gui.GUIObjectInspector.s_object = 0;
                        com.maddox.il2.gui.GUIObjectInspector.s_scroll = 0.0F;
                        wTable.setSelect(com.maddox.il2.gui.GUIObjectInspector.s_object, 0);
                        if(wTable.vSB.isVisible())
                            wTable.vSB.setPos(com.maddox.il2.gui.GUIObjectInspector.s_scroll, true);
                    }
                }
                return true;
            }
            if(gwindow == wPrev)
            {
                com.maddox.il2.game.Main.stateStack().pop();
                return true;
            }
            if(gwindow == wView)
            {
                com.maddox.il2.gui.GUIObjectInspector.type = com.maddox.il2.gui.GUIObjectInspector.type;
                com.maddox.il2.gui.GUIObjectInspector.s_object = wTable.selectRow;
                com.maddox.il2.gui.GUIObjectInspector.s_scroll = wTable.vSB.pos();
                com.maddox.il2.game.Main.stateStack().change(23);
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(40F), y1024(620F), x1024(250F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(320F), y1024(32F), 2.0F, y1024(655F));
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(40F), y1024(40F), x1024(240F), y1024(32F), 0, i18n("obj.SelectCountry"));
            draw(x1024(360F), y1024(40F), x1024(248F), y1024(32F), 0, i18n("obj.Description"));
            draw(x1024(104F), y1024(652F), x1024(192F), y1024(48F), 0, i18n("obj.Back"));
            draw(x1024(730F), y1024(652F), x1024(192F), y1024(48F), 2, i18n("obj.View"));
            setCanvasColorWHITE();
        }

        public void setPosSize()
        {
            set1024PosSize(0.0F, 32F, 1024F, 736F);
            wPrev.setPosC(x1024(64F), y1024(676F));
            wView.setPosC(x1024(960F), y1024(676F));
            wCountry.setPosSize(x1024(40F), y1024(82F), x1024(246F), M(2.0F));
            wTable.setPosSize(x1024(40F), y1024(194F), x1024(246F), y1024(400F));
            wScrollDescription.setPosSize(x1024(360F), y1024(80F), x1024(625F), y1024(514F));
        }

        public DialogClient()
        {
        }
    }

    public class Descript extends com.maddox.gwindow.GWindowDialogClient
    {

        public void render()
        {
            java.lang.String s = null;
            if(wTable.selectRow >= 0)
            {
                s = ((com.maddox.il2.gui.ObjectInfo)wTable.objects.get(wTable.selectRow)).info;
                if(s != null && s.length() == 0)
                    s = null;
            }
            if(s != null)
            {
                com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
                setCanvasFont(0);
                setCanvasColorBLACK();
                root.C.clip.y += gbevel.T.dy;
                root.C.clip.dy -= gbevel.T.dy + gbevel.B.dy;
                drawLines(gbevel.L.dx + 2.0F, gbevel.T.dy + 2.0F, s, 0, s.length(), win.dx - gbevel.L.dx - gbevel.R.dx - 4F, root.C.font.height);
            }
        }

        public void computeSize()
        {
            java.lang.String s = null;
            if(wTable.selectRow >= 0)
            {
                s = ((com.maddox.il2.gui.ObjectInfo)wTable.objects.get(wTable.selectRow)).info;
                if(s != null && s.length() == 0)
                    s = null;
            }
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

    public class Table extends com.maddox.gwindow.GWindowTable
    {

        public int countRows()
        {
            return objects == null ? 0 : objects.size();
        }

        public void renderCell(int i, int j, boolean flag, float f, float f1)
        {
            setCanvasFont(0);
            java.lang.String s = ((com.maddox.il2.gui.ObjectInfo)objects.get(i)).name;
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

        public void setSelect(int i, int j)
        {
            super.setSelect(i, j);
            wScrollDescription.resized();
            if(wScrollDescription.vScroll.isVisible())
                wScrollDescription.vScroll.setPos(0.0F, true);
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
            bColumnsSizable = false;
            addColumn(com.maddox.il2.game.I18N.gui("obj.ObjectTypesList"), null);
            vSB.scroll = rowHeight(0);
            bNotify = true;
            wClient.bNotify = true;
            resized();
        }

        public void resolutionChanged()
        {
            vSB.scroll = rowHeight(0);
            super.resolutionChanged();
        }

        public java.util.ArrayList objects;

        public Table(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow, 2.0F, 4F, 20F, 16F);
            objects = new ArrayList();
        }
    }

    static class ObjectInfo
    {

        public java.lang.String key;
        public java.lang.String name;
        public java.lang.String info;
        public boolean bGround;
        public com.maddox.il2.ai.Regiment reg;

        public ObjectInfo(java.lang.String s, java.lang.String s1, boolean flag, java.lang.String s2)
        {
            key = s1;
            bGround = flag;
            if(!flag)
            {
                name = com.maddox.il2.game.I18N.plane(s1);
                try
                {
                    java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle(s, com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
                    info = resourcebundle.getString(s1);
                }
                catch(java.lang.Exception exception)
                {
                    info = "";
                }
            } else
            {
                try
                {
                    java.util.ResourceBundle resourcebundle1 = java.util.ResourceBundle.getBundle(s, com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
                    name = resourcebundle1.getString(s1 + "_NAME");
                    info = resourcebundle1.getString(s1 + "_INFO");
                }
                catch(java.lang.Exception exception1)
                {
                    name = s1;
                    info = "";
                }
            }
            if(s2 != null)
                reg = (com.maddox.il2.ai.Regiment)com.maddox.il2.engine.Actor.getByName(s2);
        }
    }


    public void enterPush(com.maddox.il2.game.GameState gamestate)
    {
        s_object = 0;
        s_scroll = 0.0F;
        _enter();
    }

    public void _enter()
    {
        com.maddox.il2.ai.World.cur().camouflage = 0;
        wCountry.setSelected(s_country, true, false);
        com.maddox.il2.game.Main3D.menuMusicPlay(s_country != 0 ? "de" : "ru");
        fillObjects();
        client.activateWindow();
        wTable.resolutionChanged();
        if(wTable.countRows() > 0)
        {
            wTable.setSelect(s_object, 0);
            if(wTable.vSB.isVisible())
                wTable.vSB.setPos(s_scroll, true);
        }
    }

    public void _leave()
    {
        client.hideWindow();
    }

    public void fillCountries()
    {
        wCountry.clear();
        java.lang.String s = "NoName";
        for(int i = 0; i < cnt.length; i++)
        {
            java.lang.String s1;
            try
            {
                java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle("i18n/country", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
                s1 = resourcebundle.getString(cnt[i]);
            }
            catch(java.lang.Exception exception)
            {
                s1 = cnt[i];
            }
            wCountry.add(s1);
        }

    }

    public void fillObjects()
    {
        wTable.objects.clear();
        int i = wCountry.getSelected() + 1;
        if("air".equals(type))
        {
            java.lang.String s = "com/maddox/il2/objects/air.ini";
            com.maddox.rts.SectFile sectfile = new SectFile(s, 0);
            int j = sectfile.sectionIndex("AIR");
            int k = sectfile.vars(j);
            for(int i1 = 0; i1 < k; i1++)
            {
                java.lang.String s3 = sectfile.var(j, i1);
                com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.value(j, i1));
                java.lang.String s5 = "i18n/air";
                java.lang.String s6 = numbertokenizer.next();
                int l1 = numbertokenizer.next(0);
                boolean flag = true;
                java.lang.String s8 = null;
                while(numbertokenizer.hasMoreTokens()) 
                {
                    java.lang.String s9 = numbertokenizer.next();
                    if("NOINFO".equals(s9))
                    {
                        flag = false;
                        break;
                    }
                    if(!"NOQUICK".equals(s9))
                        s8 = s9;
                }
                if(flag && l1 == i)
                {
                    com.maddox.il2.gui.ObjectInfo objectinfo1 = new ObjectInfo(s5, s3, false, s8);
                    wTable.objects.add(objectinfo1);
                }
            }

        } else
        {
            java.lang.String s1 = "i18n/" + type + ".ini";
            com.maddox.rts.SectFile sectfile1 = new SectFile(s1, 0);
            java.lang.String s2 = "i18n/" + type;
            int l = sectfile1.sectionIndex("ALL");
            int j1 = sectfile1.vars(l);
            for(int k1 = 0; k1 < j1; k1++)
            {
                java.lang.String s4 = sectfile1.var(l, k1);
                com.maddox.util.NumberTokenizer numbertokenizer1 = new NumberTokenizer(sectfile1.value(l, k1));
                java.lang.String s7 = numbertokenizer1.next();
                int i2 = numbertokenizer1.next(0);
                if(i2 == i)
                {
                    com.maddox.il2.gui.ObjectInfo objectinfo = new ObjectInfo(s2, s4, true, null);
                    wTable.objects.add(objectinfo);
                }
            }

        }
        wTable.resized();
    }

    public GUIObjectInspector(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(22);
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("obj.infoI");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        wCountry = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wCountry.setEditable(false);
        fillCountries();
        wCountry.setSelected(s_country, true, false);
        wTable = new Table(dialogClient);
        dialogClient.create(wScrollDescription = new ScrollDescript());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        wPrev = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        wView = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public static java.lang.String type;
    public static int s_country = 0;
    public static int s_object = 0;
    public static float s_scroll = 0.0F;
    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton wPrev;
    public com.maddox.il2.gui.GUIButton wView;
    public com.maddox.gwindow.GWindowComboControl wCountry;
    public com.maddox.il2.gui.Table wTable;
    public com.maddox.il2.gui.ScrollDescript wScrollDescription;
    public com.maddox.il2.gui.Descript wDescript;
    public com.maddox.gwindow.GWindowVScrollBar wDistance;
    public static java.lang.String cnt[] = {
        "", ""
    };

    static 
    {
        cnt[0] = "allies";
        cnt[1] = "axis";
    }
}
