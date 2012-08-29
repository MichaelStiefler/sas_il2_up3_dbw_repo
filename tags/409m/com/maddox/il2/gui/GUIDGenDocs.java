// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIDGenDocs.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowScrollingDialogClient;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.rts.HomePath;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSReader;
import com.maddox.util.SharedTokenizer;
import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUIDialogClient, GUISeparate

public class GUIDGenDocs extends com.maddox.il2.game.GameState
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
            draw(x1024(96F), y1024(656F), x1024(320F), y1024(48F), 0, i18n("camps.Back"));
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(640F), x1024(962F), 2.0F);
        }

        public void setPosSize()
        {
            set1024PosSize(0.0F, 32F, 1024F, 736F);
            wTable.setPosSize(x1024(48F), y1024(48F), x1024(928F), y1024(132F));
            wScrollDescription.setPosSize(x1024(48F), y1024(212F), x1024(928F), y1024(396F));
            bBack.setPosC(x1024(56F), y1024(680F));
        }

        public DialogClient()
        {
        }
    }

    public class ScrollDescript extends com.maddox.gwindow.GWindowScrollingDialogClient
    {

        public void created()
        {
            fixed = wDescript = createDescript(this);
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

    public class Descript extends com.maddox.gwindow.GWindowDialogClient
    {

        public void render()
        {
            java.lang.String s = textDescription();
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
            java.lang.String s = textDescription();
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

    public class Table extends com.maddox.gwindow.GWindowTable
    {

        public int countRows()
        {
            return files == null ? 0 : files.size();
        }

        public void renderCell(int i, int j, boolean flag, float f, float f1)
        {
            setCanvasFont(0);
            java.lang.String s = (java.lang.String)names.get(i);
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

        public void fill()
        {
            files.clear();
            names.clear();
            com.maddox.il2.game.campaign.Campaign campaign = com.maddox.il2.game.Main.cur().campaign;
            java.lang.String s = "missions/campaign/" + campaign.branch() + "/" + campaign.missionsDir() + "/";
            java.lang.String s1 = s + "documents.dat";
            java.io.BufferedReader bufferedreader = null;
            try
            {
                bufferedreader = new BufferedReader(new SFSReader(s1, com.maddox.rts.RTSConf.charEncoding));
                do
                {
                    java.lang.String s2 = bufferedreader.readLine();
                    if(s2 == null)
                        break;
                    int i = s2.length();
                    if(i != 0)
                    {
                        s2 = com.maddox.util.UnicodeTo8bit.load(s2, false);
                        com.maddox.util.SharedTokenizer.set(s2);
                        java.lang.String s3 = com.maddox.util.SharedTokenizer.next();
                        java.lang.String s4 = com.maddox.util.SharedTokenizer.getGap();
                        if(s3 != null && s4 != null)
                        {
                            files.add(s + s3);
                            names.add(s4);
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
                java.lang.System.out.println("List docs load failed: " + exception.getMessage());
                exception.printStackTrace();
            }
            if(files.size() > 0)
                setSelect(0, 0);
            else
                setSelect(-1, 0);
            resized();
        }

        public void setSelect(int i, int j)
        {
            super.setSelect(i, j);
            textDescription = null;
            if(selectRow >= 0)
            {
                java.lang.String s = (java.lang.String)files.get(selectRow);
                java.io.BufferedReader bufferedreader = null;
                try
                {
                    bufferedreader = new BufferedReader(new SFSReader(s, com.maddox.rts.RTSConf.charEncoding));
                    java.lang.StringBuffer stringbuffer = null;
                    do
                    {
                        java.lang.String s1 = bufferedreader.readLine();
                        if(s1 == null)
                            break;
                        int k = s1.length();
                        if(k != 0)
                        {
                            s1 = com.maddox.util.UnicodeTo8bit.load(s1, false);
                            if(stringbuffer == null)
                            {
                                stringbuffer = new StringBuffer(s1);
                            } else
                            {
                                stringbuffer.append('\n');
                                stringbuffer.append(s1);
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
                    java.lang.System.out.println("Text load failed: " + exception.getMessage());
                    exception.printStackTrace();
                }
            }
            if(wScrollDescription != null)
            {
                wScrollDescription.resized();
                if(wScrollDescription.vScroll.isVisible())
                    wScrollDescription.vScroll.setPos(0.0F, true);
            }
        }

        public void afterCreated()
        {
            super.afterCreated();
            bColumnsSizable = false;
            addColumn(com.maddox.il2.game.I18N.gui("dgendocs.docs"), null);
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
        public java.util.ArrayList names;

        public Table(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow, 2.0F, 4F, 20F, 16F);
            files = new ArrayList();
            names = new ArrayList();
            bNotify = true;
            wClient.bNotify = true;
        }
    }


    public static boolean isExist()
    {
        com.maddox.il2.game.campaign.Campaign campaign = com.maddox.il2.game.Main.cur().campaign;
        if(campaign == null)
        {
            return false;
        } else
        {
            java.lang.String s = "missions/campaign/" + campaign.branch() + "/" + campaign.missionsDir() + "/documents.dat";
            java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName(s, 0));
            return file.exists();
        }
    }

    public void enterPush(com.maddox.il2.game.GameState gamestate)
    {
        client.activateWindow();
        wTable.fill();
        wScrollDescription.resized();
        if(wScrollDescription.vScroll.isVisible())
            wScrollDescription.vScroll.setPos(0.0F, true);
    }

    public void _leave()
    {
        client.hideWindow();
    }

    protected java.lang.String textDescription()
    {
        return textDescription;
    }

    protected com.maddox.il2.gui.Descript createDescript(com.maddox.gwindow.GWindow gwindow)
    {
        return (com.maddox.il2.gui.Descript)gwindow.create(new Descript());
    }

    public GUIDGenDocs(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(70);
        textDescription = null;
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("dgendocs.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        wTable = new Table(dialogClient);
        dialogClient.create(wScrollDescription = new ScrollDescript());
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
    public com.maddox.il2.gui.ScrollDescript wScrollDescription;
    public com.maddox.il2.gui.Descript wDescript;
    public com.maddox.il2.gui.GUIButton bBack;
    private java.lang.String textDescription;

}
