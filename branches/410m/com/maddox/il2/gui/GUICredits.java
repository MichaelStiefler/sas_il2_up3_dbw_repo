// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUICredits.java

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
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUIDialogClient

public class GUICredits extends com.maddox.il2.game.GameState
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
            draw(x1024(96F), y1024(648F), x1024(264F), y1024(48F), 0, i18n("credits.Back"));
            setCanvasColorWHITE();
            draw(x1024(336F), y1024(640F), x1024(656F), y1024(32F), 0, com.maddox.il2.game.I18N.credits("copyright1"));
            draw(x1024(336F), y1024(672F), x1024(656F), y1024(32F), 0, com.maddox.il2.game.I18N.credits("copyright2"));
        }

        public void setPosSize()
        {
            set1024PosSize(0.0F, 32F, 1024F, 736F);
            bExit.setPosC(x1024(56F), y1024(672F));
            wScrollDescription.setPosSize(x1024(32F), y1024(32F), x1024(960F), y1024(576F));
        }

        public DialogClient()
        {
        }
    }

    public class Descript extends com.maddox.gwindow.GWindowDialogClient
    {

        public void render()
        {
            if(text != null)
            {
                com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
                com.maddox.gwindow.GFont gfont = root.C.font;
                root.C.font = fontCredits;
                setCanvasColorBLACK();
                root.C.clip.y += gbevel.T.dy;
                root.C.clip.dy -= gbevel.T.dy + gbevel.B.dy;
                drawLines(gbevel.L.dx + 2.0F, gbevel.T.dy + 2.0F, text, 0, text.length(), win.dx - gbevel.L.dx - gbevel.R.dx - 4F, root.C.font.height);
                root.C.font = gfont;
            }
        }

        public void computeSize()
        {
            if(text != null)
            {
                com.maddox.gwindow.GFont gfont = root.C.font;
                root.C.font = fontCredits;
                win.dx = parentWindow.win.dx;
                com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
                int i = computeLines(text, 0, text.length(), win.dx - gbevel.L.dx - gbevel.R.dx - 4F);
                win.dy = root.C.font.height * (float)i + gbevel.T.dy + gbevel.B.dy + 4F;
                if(win.dy > parentWindow.win.dy)
                {
                    win.dx = parentWindow.win.dx - lookAndFeel().getVScrollBarW();
                    int j = computeLines(text, 0, text.length(), win.dx - gbevel.L.dx - gbevel.R.dx - 4F);
                    win.dy = root.C.font.height * (float)j + gbevel.T.dy + gbevel.B.dy + 4F;
                }
                root.C.font = gfont;
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


    public void _enter()
    {
        text = com.maddox.il2.game.I18N.credits("credits");
        client.showWindow();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    public GUICredits(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(16);
        text = "credits";
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("credits.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        fontCredits = com.maddox.gwindow.GFont.New("courb10");
        dialogClient.create(wScrollDescription = new ScrollDescript());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton bExit;
    public com.maddox.il2.gui.ScrollDescript wScrollDescription;
    public com.maddox.il2.gui.Descript wDescript;
    java.lang.String text;
    com.maddox.gwindow.GFont fontCredits;
}
