// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GBlackMetalLookAndFeel.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GCursorTexRegion;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GPoint;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWin95LookAndFeel;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowClient;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowEditBox;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowHScrollBar;
import com.maddox.gwindow.GWindowHSliderInt;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuBar;
import com.maddox.gwindow.GWindowMenuBarItem;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowStatusBar;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.gwindow.GWindowVScrollBar;

public class GBlackMetalLookAndFeel extends com.maddox.gwindow.GWin95LookAndFeel
{

    public GBlackMetalLookAndFeel()
    {
        bevelTabOVER = new GBevel();
        bevelButtonUP = new GBevel();
        bevelButtonOVER = new GBevel();
        bevelButtonDOWN = new GBevel();
        bevelMenu = new GBevel();
        spaceMenuBarItem = 0.1666667F;
        spaceMenuItem = 0.1666667F;
        _titleRegion = new GRegion();
        spaceFramedTitle = 0.25F;
        spaceComboList = 3F;
        spaceStatusBar = 0.08333334F;
        spaceTab = 0.08333334F;
        tabReg = new GRegion();
    }

    public void drawSeparateH(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2)
    {
        gwindow.setCanvasColorWHITE();
        gwindow.draw(f, f1, f2, 2.0F, elements, 68F, 78F, 1.0F, 2.0F);
    }

    public void drawSeparateW(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2)
    {
        gwindow.setCanvasColorWHITE();
        gwindow.draw(f, f1, 2.0F, f2, elements, 78F, 68F, 2.0F, 1.0F);
    }

    public void render(com.maddox.gwindow.GWindowButton gwindowbutton)
    {
        gwindowbutton.setCanvasColorWHITE();
        if(!gwindowbutton.bEnable)
        {
            drawBevel(gwindowbutton, 0.0F, 0.0F, gwindowbutton.win.dx, gwindowbutton.win.dy, bevelButtonUP, elementsStreched);
            if(gwindowbutton.cap != null)
            {
                com.maddox.gwindow.GRegion gregion = gwindowbutton.getClientRegion();
                float f = gregion.dx;
                float f2 = gregion.dy;
                if(gwindowbutton.pushClipRegion(gregion, gwindowbutton.bClip, 0.0F))
                {
                    renderTextDialogControl(gwindowbutton, 1.0F, 1.0F, f, f2, 0x7f7f7f, false);
                    renderTextDialogControl(gwindowbutton, 0.0F, 0.0F, f, f2, 0x618894, false);
                    gwindowbutton.popClip();
                }
            }
            return;
        }
        if(gwindowbutton.bDown && !gwindowbutton.bDrawOnlyUP)
        {
            drawBevel(gwindowbutton, 0.0F, 0.0F, gwindowbutton.win.dx, gwindowbutton.win.dy, bevelButtonDOWN, elementsStreched);
        } else
        {
            drawBevel(gwindowbutton, 0.0F, 0.0F, gwindowbutton.win.dx, gwindowbutton.win.dy, bevelButtonUP, elementsStreched);
            if(gwindowbutton.isMouseOver())
                drawBevel(gwindowbutton, 0.0F, 0.0F, gwindowbutton.win.dx, gwindowbutton.win.dy, bevelButtonOVER, elementsStreched);
        }
        if(gwindowbutton.bDrawActive && gwindowbutton.isActivated())
            drawBevel(gwindowbutton, 0.0F, 0.0F, gwindowbutton.win.dx, gwindowbutton.win.dy, bevelBlack, elements);
        if(gwindowbutton.cap != null)
        {
            com.maddox.gwindow.GRegion gregion1 = gwindowbutton.getClientRegion();
            float f1 = gregion1.dx;
            float f3 = gregion1.dy;
            if(gwindowbutton.pushClipRegion(gregion1, gwindowbutton.bClip, 0.0F))
            {
                if(gwindowbutton.bDown)
                    renderTextDialogControl(gwindowbutton, 1.0F, 2.0F, f1, f3, 0x50d7ff, gwindowbutton.isDefault());
                else
                    renderTextDialogControl(gwindowbutton, 0.0F, 0.0F, f1, f3, 0x50d7ff, gwindowbutton.isDefault());
                gwindowbutton.popClip();
            }
        }
    }

    public void render(com.maddox.gwindow.GWindowMenuBar gwindowmenubar)
    {
        gwindowmenubar.setCanvasColorWHITE();
        com.maddox.gwindow.GRegion gregion = bevelMenu.B;
        gwindowmenubar.draw(0.0F, gwindowmenubar.win.dy - bevelMenu.B.dy, gwindowmenubar.win.dx, bevelMenu.B.dy, elementsStreched, gregion.x, gregion.y, gregion.dx, gregion.dy);
        gregion = bevelMenu.Area;
        gwindowmenubar.draw(0.0F, 0.0F, gwindowmenubar.win.dx, gwindowmenubar.win.dy - bevelMenu.B.dy, elementsStreched, gregion.x, gregion.y, gregion.dx, gregion.dy);
    }

    public void render(com.maddox.gwindow.GWindowMenuBarItem gwindowmenubaritem)
    {
        if(gwindowmenubaritem == gwindowmenubaritem.menuBar().selected)
        {
            gwindowmenubaritem.setCanvasColorWHITE();
            drawBevel(gwindowmenubaritem, 0.0F, 0.0F, gwindowmenubaritem.win.dx, gwindowmenubaritem.win.dy, bevelDOWNsmall, elementsStreched);
        } else
        if(gwindowmenubaritem == gwindowmenubaritem.menuBar().over)
        {
            gwindowmenubaritem.setCanvasColorWHITE();
            drawBevel(gwindowmenubaritem, 0.0F, 0.0F, gwindowmenubaritem.win.dx, gwindowmenubaritem.win.dy, bevelUPsmall, elementsStreched);
        }
        if(gwindowmenubaritem.pushClipRegion(gwindowmenubaritem.getClientRegion(), gwindowmenubaritem.bClip, 0.0F))
        {
            gwindowmenubaritem.setCanvasColorBLACK();
            float f = metric(spaceMenuBarItem);
            float f1 = metric(spaceMenuBarItem);
            if(gwindowmenubaritem == gwindowmenubaritem.menuBar().selected)
            {
                f = f1 = metric(spaceMenuBarItem) + 1.0F;
                gwindowmenubaritem.root.C.color.set(255, 255, 0);
            } else
            if(gwindowmenubaritem == gwindowmenubaritem.menuBar().over)
                f = f1 = metric(spaceMenuBarItem) - 1.0F;
            gwindowmenubaritem.cap.draw(gwindowmenubaritem, f, f1, gwindowmenubaritem.root.textFonts[0], gwindowmenubaritem.menuBar().bAltDown && gwindowmenubaritem.menuBar().over == null);
            gwindowmenubaritem.popClip();
        }
    }

    public void render(com.maddox.gwindow.GWindowMenu gwindowmenu)
    {
        gwindowmenu.setCanvasColorWHITE();
        drawBevel(gwindowmenu, 0.0F, 0.0F, gwindowmenu.win.dx, gwindowmenu.win.dy, bevelDOWNsmall, elementsStreched);
    }

    public void render(com.maddox.gwindow.GWindowMenuItem gwindowmenuitem)
    {
        if("-".equals(gwindowmenuitem.cap.caption))
        {
            drawSeparateH(gwindowmenuitem, 0.0F, gwindowmenuitem.win.dy / 2.0F, gwindowmenuitem.win.dx);
            return;
        }
        gwindowmenuitem.setCanvasFont(0);
        if(gwindowmenuitem.bEnable)
        {
            if(gwindowmenuitem == gwindowmenuitem.menu().selected)
            {
                gwindowmenuitem.root.C.color.set(255, 255, 128);
                gwindowmenuitem.draw(0.0F, 0.0F, gwindowmenuitem.win.dx, gwindowmenuitem.win.dy, elementsStreched, 65F, 81F, 14F, 14F);
                gwindowmenuitem.root.C.color.set(255, 255, 0);
            } else
            {
                gwindowmenuitem.root.C.color.set(255, 215, 80);
            }
            renderMenuItem(gwindowmenuitem, 0.0F, 0.0F);
        } else
        {
            gwindowmenuitem.root.C.color.set(127, 127, 127);
            renderMenuItem(gwindowmenuitem, 1.0F, 1.0F);
            gwindowmenuitem.root.C.color.set(148, 136, 97);
            renderMenuItem(gwindowmenuitem, 0.0F, 0.0F);
        }
    }

    private void renderMenuItem(com.maddox.gwindow.GWindowMenuItem gwindowmenuitem, float f, float f1)
    {
        if(gwindowmenuitem.bChecked)
            gwindowmenuitem.draw(f, (gwindowmenuitem.win.dy - metric()) / 2.0F + f1, metric(), metric(), selectMenuIcon);
        gwindowmenuitem.cap.draw(gwindowmenuitem, metric() + metric(spaceMenuItem) + f, (gwindowmenuitem.win.dy - metric(spaceMenuItem) - gwindowmenuitem.root.textFonts[0].height) + f1, gwindowmenuitem.root.textFonts[0]);
        if(gwindowmenuitem.subMenu() != null)
            gwindowmenuitem.draw((gwindowmenuitem.win.dx - metric()) + f, (gwindowmenuitem.win.dy - metric()) / 2.0F + f1, metric(), metric(), subMenuIcon);
    }

    public void render(com.maddox.gwindow.GWindowFramed gwindowframed)
    {
        float f = framedTitleHeight(gwindowframed);
        gwindowframed.setCanvasColorWHITE();
        drawBevel(gwindowframed, 0.0F, f, gwindowframed.win.dx, gwindowframed.win.dy - f, bevelFW, elementsStreched);
        com.maddox.gwindow.GBevel gbevel = bevelTitleActive;
        if(!gwindowframed.isActivated())
            gbevel = bevelTitleInactive;
        gwindowframed.setCanvasColorWHITE();
        drawBevel(gwindowframed, 0.0F, 0.0F, gwindowframed.win.dx, f, gbevel, elementsStreched);
        if(gwindowframed.title != null)
        {
            gwindowframed.root.C.color.set(255, 255, 0);
            if(!gwindowframed.isActivated())
                gwindowframed.setCanvasColor(0xc0c0c0);
            gwindowframed.setCanvasFont(1);
            _titleRegion.x = bevelTitleActive.L.dx;
            _titleRegion.y = bevelTitleActive.T.dy;
            _titleRegion.dx = gwindowframed.win.dx - bevelTitleActive.L.dx - bevelTitleActive.R.dx - metric();
            _titleRegion.dy = f - bevelTitleActive.T.dy - bevelTitleActive.B.dy;
            if(gwindowframed.pushClipRegion(_titleRegion, gwindowframed.bClip, metric(spaceFramedTitle)))
            {
                gwindowframed.draw(0.0F, 0.0F, gwindowframed.title);
                gwindowframed.popClip();
            }
        }
    }

    private float framedTitleHeight(com.maddox.gwindow.GWindowFramed gwindowframed)
    {
        float f = metric();
        if(f > gwindowframed.root.textFonts[1].height)
            f = gwindowframed.root.textFonts[1].height;
        return f + 2.0F * metric(spaceFramedTitle) + bevelTitleActive.T.dy + bevelTitleActive.B.dy;
    }

    public void render(com.maddox.gwindow.GWindowClient gwindowclient)
    {
        gwindowclient.setCanvasColorWHITE();
        gwindowclient.draw(0.0F, 0.0F, gwindowclient.win.dx, gwindowclient.win.dy, elementsStreched, 65F, 33F, 30F, 14F);
    }

    public void render(com.maddox.gwindow.GWindowLabel gwindowlabel)
    {
        if(!gwindowlabel.bEnable)
        {
            com.maddox.gwindow.GRegion gregion = gwindowlabel.getClientRegion();
            float f = gregion.dx;
            float f2 = gregion.dy;
            if(gwindowlabel.pushClipRegion(gregion, gwindowlabel.bClip, 0.0F))
            {
                renderTextDialogControl(gwindowlabel, 1.0F, 1.0F, f, f2, 0x7f7f7f, false);
                renderTextDialogControl(gwindowlabel, 0.0F, 0.0F, f, f2, 0x618894, false);
                gwindowlabel.popClip();
            }
            return;
        }
        if(gwindowlabel.isActivated())
        {
            gwindowlabel.setCanvasColorWHITE();
            drawBevel(gwindowlabel, 0.0F, 0.0F, gwindowlabel.win.dx, gwindowlabel.win.dy, bevelBlack50, elements);
        }
        com.maddox.gwindow.GRegion gregion1 = gwindowlabel.getClientRegion();
        float f1 = gregion1.dx;
        float f3 = gregion1.dy;
        if(gwindowlabel.pushClipRegion(gregion1, gwindowlabel.bClip, 0.0F))
        {
            renderTextDialogControl(gwindowlabel, 0.0F, 0.0F, f1, f3, 0x50d7ff, gwindowlabel.isDefault());
            gwindowlabel.popClip();
        }
    }

    public void renderComboList(com.maddox.gwindow.GWindowComboControl gwindowcombocontrol)
    {
        com.maddox.gwindow.GWindowComboControl.ListArea listarea = gwindowcombocontrol.listArea;
        listarea.setCanvasColorWHITE();
        listarea.draw(bevelDOWNsmall.L.dx, bevelDOWNsmall.T.dy, ((com.maddox.gwindow.GWindow) (listarea)).win.dx - bevelDOWNsmall.R.dx - bevelDOWNsmall.L.dx, ((com.maddox.gwindow.GWindow) (listarea)).win.dy - bevelDOWNsmall.B.dy - bevelDOWNsmall.T.dy, elementsStreched, 114F, 34F, 12F, 12F);
        drawBevel(listarea, 0.0F, 0.0F, ((com.maddox.gwindow.GWindow) (listarea)).win.dx, ((com.maddox.gwindow.GWindow) (listarea)).win.dy, bevelDOWNsmall, elementsStreched);
        com.maddox.gwindow.GRegion gregion = listarea.getClientRegion();
        gregion.x += bevelDOWNsmall.L.dx + spaceComboList;
        gregion.y += bevelDOWNsmall.T.dy;
        gregion.dx -= bevelDOWNsmall.L.dx + bevelDOWNsmall.R.dx + 2.0F * spaceComboList;
        gregion.dy -= bevelDOWNsmall.T.dy + bevelDOWNsmall.B.dy;
        if(gwindowcombocontrol.scrollBar.isVisible())
            gregion.dx -= gwindowcombocontrol.scrollBar.win.dx;
        if(listarea.pushClipRegion(gregion, true, 0.0F))
        {
            listarea.setCanvasColor(0x50d7ff);
            listarea.setCanvasFont(gwindowcombocontrol.font);
            com.maddox.gwindow.GFont gfont = gwindowcombocontrol.root.C.font;
            float f = (getComboHline() - gfont.height) / 2.0F;
            int i = gwindowcombocontrol.listStartLine;
            for(int j = 0; j < gwindowcombocontrol.listCountLines; j++)
            {
                if(i == gwindowcombocontrol.listSelected)
                {
                    com.maddox.gwindow.GSize gsize = gfont.size(gwindowcombocontrol.get(i));
                    listarea.draw(0.0F, f, ((com.maddox.gwindow.GWindow) (listarea)).win.dx, gsize.dy, elementsStreched, 65F, 81F, 14F, 14F);
                    listarea.setCanvasColor(65535);
                    listarea.draw(0.0F, f, gwindowcombocontrol.get(i));
                    listarea.setCanvasColor(0x50d7ff);
                } else
                if(gwindowcombocontrol.posEnable != null && !gwindowcombocontrol.posEnable[i])
                {
                    com.maddox.gwindow.GSize gsize1 = gfont.size(gwindowcombocontrol.get(i));
                    listarea.setCanvasColor(0x7f7f7f);
                    listarea.draw(1.0F, f + 1.0F, gwindowcombocontrol.get(i));
                    listarea.setCanvasColor(0x618894);
                    listarea.draw(0.0F, f, gwindowcombocontrol.get(i));
                    listarea.setCanvasColor(65535);
                } else
                {
                    listarea.draw(0.0F, f, gwindowcombocontrol.get(i));
                }
                i++;
                f += getComboHline();
            }

            listarea.popClip();
        }
    }

    public void setupComboList(com.maddox.gwindow.GWindowComboControl gwindowcombocontrol)
    {
        gwindowcombocontrol.listArea.win.dx = gwindowcombocontrol.win.dx;
        gwindowcombocontrol.listCountLines = gwindowcombocontrol.listVisibleLines;
        if(gwindowcombocontrol.listCountLines > gwindowcombocontrol.size())
            gwindowcombocontrol.listCountLines = gwindowcombocontrol.size();
        gwindowcombocontrol.listArea.win.dy = (float)gwindowcombocontrol.listCountLines * getComboHline() + bevelDOWNsmall.B.dy + bevelDOWNsmall.T.dy;
        com.maddox.gwindow.GPoint gpoint = gwindowcombocontrol.windowToGlobal(0.0F, gwindowcombocontrol.win.dy);
        if(gpoint.y + gwindowcombocontrol.listArea.win.dy > gwindowcombocontrol.root.win.dy)
            gwindowcombocontrol.listArea.win.y = -gwindowcombocontrol.listArea.win.dy;
        else
            gwindowcombocontrol.listArea.win.y = gwindowcombocontrol.win.dy;
        gwindowcombocontrol.listArea.win.x = 0.0F;
        if(gwindowcombocontrol.listCountLines < gwindowcombocontrol.size())
        {
            gwindowcombocontrol.scrollBar.win.dx = getVScrollBarW();
            gwindowcombocontrol.scrollBar.win.dy = (float)gwindowcombocontrol.listCountLines * getComboHline();
            gwindowcombocontrol.scrollBar.win.x = gwindowcombocontrol.listArea.win.dx - gwindowcombocontrol.scrollBar.win.dx - bevelDOWNsmall.R.dx;
            gwindowcombocontrol.scrollBar.win.y = bevelDOWNsmall.T.dy;
            if(gwindowcombocontrol.iSelected >= 0)
            {
                gwindowcombocontrol.listStartLine = gwindowcombocontrol.iSelected;
                if(gwindowcombocontrol.listStartLine + gwindowcombocontrol.listCountLines > gwindowcombocontrol.size())
                    gwindowcombocontrol.listStartLine = gwindowcombocontrol.size() - gwindowcombocontrol.listCountLines;
            } else
            {
                gwindowcombocontrol.listStartLine = 0;
            }
            gwindowcombocontrol.scrollBar.setRange(0.0F, gwindowcombocontrol.size(), gwindowcombocontrol.listCountLines, 1.0F, gwindowcombocontrol.listStartLine);
            gwindowcombocontrol.scrollBar.showWindow();
        } else
        {
            gwindowcombocontrol.scrollBar.hideWindow();
            gwindowcombocontrol.listStartLine = 0;
        }
        gwindowcombocontrol.listSelected = gwindowcombocontrol.iSelected;
    }

    public void render(com.maddox.gwindow.GWindowComboControl gwindowcombocontrol)
    {
        gwindowcombocontrol.setCanvasColorWHITE();
        if(gwindowcombocontrol.bEnable)
            gwindowcombocontrol.draw(bevelDOWN.L.dx, bevelDOWN.T.dy, gwindowcombocontrol.win.dx - bevelDOWN.R.dx - bevelDOWN.L.dx, gwindowcombocontrol.win.dy - bevelDOWN.B.dy - bevelDOWN.T.dy, elementsStreched, 100F, 52F, 8F, 8F);
        else
            gwindowcombocontrol.draw(bevelDOWN.L.dx, bevelDOWN.T.dy, gwindowcombocontrol.win.dx - bevelDOWN.R.dx - bevelDOWN.L.dx, gwindowcombocontrol.win.dy - bevelDOWN.B.dy - bevelDOWN.T.dy, elementsStreched, 116F, 52F, 8F, 8F);
        drawBevel(gwindowcombocontrol, 0.0F, 0.0F, gwindowcombocontrol.win.dx, gwindowcombocontrol.win.dy, bevelDOWN, elementsStreched, false);
    }

    public void render(com.maddox.gwindow.GWindowEditBox gwindoweditbox, float f)
    {
        gwindoweditbox.color = 0x50d7ff;
        super.render(gwindoweditbox, f);
    }

    public void render(com.maddox.gwindow.GWindowEditControl gwindoweditcontrol)
    {
        gwindoweditcontrol.setCanvasColorWHITE();
        gwindoweditcontrol.draw(bevelDOWN.L.dx, bevelDOWN.T.dy, gwindoweditcontrol.win.dx - bevelDOWN.R.dx - bevelDOWN.L.dx, gwindoweditcontrol.win.dy - bevelDOWN.B.dy - bevelDOWN.T.dy, elementsStreched, 100F, 52F, 8F, 8F);
        drawBevel(gwindoweditcontrol, 0.0F, 0.0F, gwindoweditcontrol.win.dx, gwindoweditcontrol.win.dy, bevelDOWN, elementsStreched, false);
        render(((com.maddox.gwindow.GWindowEditBox) (gwindoweditcontrol)), bevelDOWN.L.dx);
    }

    public void render(com.maddox.gwindow.GWindowHScrollBar gwindowhscrollbar)
    {
        float f = gwindowhscrollbar.xM - gwindowhscrollbar.lButton.win.dx;
        float f1 = gwindowhscrollbar.rButton.win.x - gwindowhscrollbar.xM - gwindowhscrollbar.dxM;
        if(f < 0.0F && f1 < 0.0F)
            return;
        gwindowhscrollbar.setCanvasColorWHITE();
        if(f > 0.0F)
        {
            com.maddox.gwindow.GWindowHScrollBar _tmp = gwindowhscrollbar;
            if(gwindowhscrollbar.downState == 1)
                gwindowhscrollbar.draw(gwindowhscrollbar.lButton.win.dx, 0.0F, f, gwindowhscrollbar.win.dy, elements, 85F, 81F, 2.0F, 6F);
            else
                gwindowhscrollbar.draw(gwindowhscrollbar.lButton.win.dx, 0.0F, f, gwindowhscrollbar.win.dy, elements, 81F, 81F, 2.0F, 6F);
        }
        if(f1 > 0.0F)
        {
            com.maddox.gwindow.GWindowHScrollBar _tmp1 = gwindowhscrollbar;
            if(gwindowhscrollbar.downState == 2)
                gwindowhscrollbar.draw(gwindowhscrollbar.xM + gwindowhscrollbar.dxM, 0.0F, f1, gwindowhscrollbar.win.dy, elements, 85F, 81F, 2.0F, 6F);
            else
                gwindowhscrollbar.draw(gwindowhscrollbar.xM + gwindowhscrollbar.dxM, 0.0F, f1, gwindowhscrollbar.win.dy, elements, 81F, 81F, 2.0F, 6F);
        }
    }

    public void render(com.maddox.gwindow.GWindowVScrollBar gwindowvscrollbar)
    {
        float f = gwindowvscrollbar.yM - gwindowvscrollbar.uButton.win.dy;
        float f1 = gwindowvscrollbar.dButton.win.y - gwindowvscrollbar.yM - gwindowvscrollbar.dyM;
        if(f < 0.0F && f1 < 0.0F)
            return;
        gwindowvscrollbar.setCanvasColorWHITE();
        if(f > 0.0F)
        {
            com.maddox.gwindow.GWindowVScrollBar _tmp = gwindowvscrollbar;
            if(gwindowvscrollbar.downState == 1)
                gwindowvscrollbar.draw(0.0F, gwindowvscrollbar.uButton.win.dy, gwindowvscrollbar.win.dx, f, elements, 89F, 85F, 6F, 2.0F);
            else
                gwindowvscrollbar.draw(0.0F, gwindowvscrollbar.uButton.win.dy, gwindowvscrollbar.win.dx, f, elements, 89F, 81F, 6F, 2.0F);
        }
        if(f1 > 0.0F)
        {
            com.maddox.gwindow.GWindowVScrollBar _tmp1 = gwindowvscrollbar;
            if(gwindowvscrollbar.downState == 2)
                gwindowvscrollbar.draw(0.0F, gwindowvscrollbar.yM + gwindowvscrollbar.dyM, gwindowvscrollbar.win.dx, f1, elements, 89F, 85F, 6F, 2.0F);
            else
                gwindowvscrollbar.draw(0.0F, gwindowvscrollbar.yM + gwindowvscrollbar.dyM, gwindowvscrollbar.win.dx, f1, elements, 89F, 81F, 6F, 2.0F);
        }
    }

    public void render(com.maddox.gwindow.GWindowHSliderInt gwindowhsliderint)
    {
        gwindowhsliderint.setCanvasColorWHITE();
        drawSeparateH(gwindowhsliderint, 0.0F, gwindowhsliderint.win.dy - (float)(int)(metric() / 2.0F), gwindowhsliderint.win.dx);
        if(gwindowhsliderint.bEnable && gwindowhsliderint.isActivated())
            drawBevel(gwindowhsliderint, 0.0F, 0.0F, gwindowhsliderint.win.dx, gwindowhsliderint.win.dy, bevelBlack50, elementsStreched);
        drawBevel(gwindowhsliderint, gwindowhsliderint.xM, gwindowhsliderint.win.dy - metric(), gwindowhsliderint.dxM, metric(), bevelUP, elementsStreched);
        float f = (float)(int)(metric() / 4F) - 1.0F;
        float f1 = (gwindowhsliderint.win.dx - gwindowhsliderint.dxM) / (float)(gwindowhsliderint.posCount - 1);
        if(f1 < 3F)
            return;
        for(int i = 0; i < gwindowhsliderint.posCount; i++)
        {
            boolean flag = gwindowhsliderint.bEnable;
            if(flag && gwindowhsliderint.posEnable != null)
                flag = gwindowhsliderint.posEnable[i];
            if(flag)
                gwindowhsliderint.draw(f, 1.0F, 2.0F, 2.0F, elements, 126F, 106F, 1.0F, 1.0F);
            else
                gwindowhsliderint.draw(f, 1.0F, 2.0F, 2.0F, elements, 81F, 65F, 1.0F, 1.0F);
            f += f1;
        }

    }

    public void render(com.maddox.gwindow.GWindowStatusBar gwindowstatusbar)
    {
        gwindowstatusbar.setCanvasColorWHITE();
        drawBevel(gwindowstatusbar, 0.0F, 0.0F, gwindowstatusbar.win.dx, gwindowstatusbar.win.dy, bevelUP, elementsStreched);
        drawBevel(gwindowstatusbar, bevelUP.L.dx + metric(spaceStatusBar), bevelUP.T.dy + metric(spaceStatusBar), gwindowstatusbar.win.dx - 2.0F * metric(spaceStatusBar) - bevelUP.L.dx - bevelUP.R.dx, gwindowstatusbar.win.dy - 2.0F * metric(spaceStatusBar) - bevelUP.T.dy - bevelUP.B.dy, bevelDOWNsmall, elementsStreched);
        if(gwindowstatusbar.pushClipRegion(gwindowstatusbar.getClientRegion(), gwindowstatusbar.bClip, 0.0F))
        {
            gwindowstatusbar.setCanvasColor(0x50d7ff);
            gwindowstatusbar.setCanvasFont(0);
            if(gwindowstatusbar.help != null)
                gwindowstatusbar.draw(0.0F, 0.0F, gwindowstatusbar.help);
            else
                gwindowstatusbar.draw(0.0F, 0.0F, gwindowstatusbar.defaultHelp);
            gwindowstatusbar.popClip();
        }
    }

    public void render(com.maddox.gwindow.GWindowTabDialogClient gwindowtabdialogclient)
    {
        float f = 0.0F;
        if(gwindowtabdialogclient.sizeTabs() > 0)
        {
            com.maddox.gwindow.GSize gsize = gwindowtabdialogclient.getTab(0).getMinSize();
            f = gsize.dy;
        }
        gwindowtabdialogclient.setCanvasColorWHITE();
        drawBevel(gwindowtabdialogclient, 0.0F, f, gwindowtabdialogclient.win.dx, gwindowtabdialogclient.win.dy - f, bevelTabDialogClient, elementsStreched, false);
    }

    public void render(com.maddox.gwindow.GWindowTabDialogClient.Tab tab)
    {
        com.maddox.gwindow.GBevel gbevel = bevelTab;
        boolean flag = tab.isCurrent();
        if(flag)
            gbevel = bevelTabCUR;
        if(tab.isMouseOver())
            gbevel = bevelTabOVER;
        tab.setCanvasColorWHITE();
        drawBevel(tab, 0.0F, 0.0F, tab.win.dx, tab.win.dy, gbevel, elementsStreched);
        tabReg.x = gbevel.L.dx + metric(spaceTab);
        tabReg.y = gbevel.T.dy + metric(spaceTab);
        tabReg.dx = tab.win.dx - gbevel.L.dx - gbevel.R.dx;
        tabReg.dy = tab.win.dy - gbevel.T.dy - gbevel.B.dy;
        if(tab.pushClipRegion(tabReg, tab.bClip, 0.0F))
        {
            if(flag)
                renderTextDialogControl(tab, metric(spaceTab), metric(spaceTab), tabReg.dx - 2.0F * metric(spaceTab), tabReg.dy - 2.0F * metric(spaceTab), 65535, false);
            else
                renderTextDialogControl(tab, metric(spaceTab), metric(spaceTab), tabReg.dx - 2.0F * metric(spaceTab), tabReg.dy - 2.0F * metric(spaceTab), 0x50d7ff, false);
            tab.popClip();
        }
    }

    public void setMessageBoxTextColor(com.maddox.gwindow.GWindowClient gwindowclient)
    {
        gwindowclient.setCanvasColor(0x50d7ff);
    }

    public void resolutionChanged(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super.resolutionChanged(gwindowroot);
    }

    public void init(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super.init(gwindowroot);
        elements = com.maddox.gwindow.GTexture.New("GUI/blackmetal/elements.mat");
        elementsStreched = com.maddox.gwindow.GTexture.New("GUI/blackmetal/elementss.mat");
        cursors = com.maddox.gwindow.GTexture.New("GUI/blackmetal/cursors.mat");
        cursorsStreched = com.maddox.gwindow.GTexture.New("GUI/blackmetal/cursorss.mat");
        regionWhite = new GTexRegion(elements, 124F, 100F, 1.0F, 1.0F);
        bevelUP.set(new GRegion(96F, 64F, 16F, 16F), new GRegion(100F, 68F, 8F, 8F));
        bevelDOWN.set(new GRegion(112F, 64F, 16F, 16F), new GRegion(116F, 68F, 8F, 8F));
        bevelUPsmall.set(new GRegion(96F, 32F, 16F, 16F), new GRegion(98F, 34F, 12F, 12F));
        bevelDOWNsmall.set(new GRegion(112F, 32F, 16F, 16F), new GRegion(114F, 34F, 12F, 12F));
        bevelButtonUP.set(new GRegion(64F, 0.0F, 32F, 16F), new GRegion(68F, 4F, 24F, 8F));
        bevelButtonOVER.set(new GRegion(96F, 0.0F, 32F, 16F), new GRegion(100F, 4F, 24F, 8F));
        bevelButtonDOWN.set(new GRegion(64F, 16F, 32F, 16F), new GRegion(68F, 20F, 24F, 8F));
        bevelMenu.set(new GRegion(64F, 48F, 32F, 16F), new GRegion(68F, 52F, 24F, 8F));
        bevelFW.set(new GRegion(64F, 4F, 32F, 12F), new GRegion(68F, 4F, 24F, 8F));
        bevelTitleActive.set(new GRegion(96F, 48F, 16F, 16F), new GRegion(100F, 52F, 8F, 8F));
        bevelTitleInactive.set(new GRegion(112F, 48F, 16F, 16F), new GRegion(116F, 52F, 8F, 8F));
        bevelBlack.set(new GRegion(64F, 64F, 16F, 16F), new GRegion(66F, 66F, 12F, 12F));
        bevelBlack.Area.dx = 0.0F;
        bevelBlack50.set(new GRegion(80F, 64F, 16F, 16F), new GRegion(82F, 66F, 12F, 12F));
        bevelBlack50.Area.dx = 0.0F;
        bevelSeparate.set(new GRegion(64F, 64F, 16F, 16F), new GRegion(66F, 66F, 12F, 12F));
        bevelSeparate.Area.dx = 0.0F;
        bevelTabDialogClient = new GBevel();
        bevelTabDialogClient.set(new GRegion(96F, 16F, 32F, 16F), new GRegion(100F, 20F, 24F, 8F));
        bevelTabCUR.set(new GRegion(64F, 96F, 16F, 16F), new GRegion(68F, 100F, 8F, 8F));
        bevelTabOVER.set(new GRegion(80F, 96F, 16F, 16F), new GRegion(84F, 100F, 8F, 8F));
        bevelTab.set(new GRegion(96F, 96F, 16F, 14F), new GRegion(100F, 100F, 8F, 6F));
        closeBoxUP = new GTexRegion(elementsStreched, 0.0F, 16F, 16F, 16F);
        closeBoxOVER = new GTexRegion(elementsStreched, 16F, 16F, 16F, 16F);
        closeBoxDOWN = new GTexRegion(elementsStreched, 32F, 16F, 16F, 16F);
        closeBoxDISABLE = new GTexRegion(elementsStreched, 48F, 16F, 16F, 16F);
        SBupButtonUP = new GTexRegion(elementsStreched, 0.0F, 64F, 16F, 16F);
        SBupButtonOVER = new GTexRegion(elementsStreched, 16F, 64F, 16F, 16F);
        SBupButtonDOWN = new GTexRegion(elementsStreched, 32F, 64F, 16F, 16F);
        SBupButtonDISABLE = new GTexRegion(elementsStreched, 48F, 64F, 16F, 16F);
        SBdownButtonUP = new GTexRegion(elementsStreched, 0.0F, 80F, 16F, 16F);
        SBdownButtonOVER = new GTexRegion(elementsStreched, 16F, 80F, 16F, 16F);
        SBdownButtonDOWN = new GTexRegion(elementsStreched, 32F, 80F, 16F, 16F);
        SBdownButtonDISABLE = new GTexRegion(elementsStreched, 48F, 80F, 16F, 16F);
        SBleftButtonUP = new GTexRegion(elementsStreched, 0.0F, 96F, 16F, 16F);
        SBleftButtonOVER = new GTexRegion(elementsStreched, 16F, 96F, 16F, 16F);
        SBleftButtonDOWN = new GTexRegion(elementsStreched, 32F, 96F, 16F, 16F);
        SBleftButtonDISABLE = new GTexRegion(elementsStreched, 48F, 96F, 16F, 16F);
        SBrightButtonUP = new GTexRegion(elementsStreched, 0.0F, 112F, 16F, 16F);
        SBrightButtonOVER = new GTexRegion(elementsStreched, 16F, 112F, 16F, 16F);
        SBrightButtonDOWN = new GTexRegion(elementsStreched, 32F, 112F, 16F, 16F);
        SBrightButtonDISABLE = new GTexRegion(elementsStreched, 48F, 112F, 16F, 16F);
        checkBoxUnCheckEnable = new GTexRegion(elementsStreched, 64F, 112F, 16F, 16F);
        checkBoxCheckEnable = new GTexRegion(elementsStreched, 80F, 112F, 16F, 16F);
        checkBoxUnCheckDisable = new GTexRegion(elementsStreched, 96F, 112F, 16F, 16F);
        checkBoxCheckDisable = new GTexRegion(elementsStreched, 112F, 112F, 16F, 16F);
        gwindowroot.textFonts[0] = com.maddox.gwindow.GFont.New("arial10");
        gwindowroot.textFonts[1] = com.maddox.gwindow.GFont.New("arialb10");
        gwindowroot.mouseCursors[0] = new GCursorTexRegion(cursors, 32F, 96F, 0.0F, 0.0F, 0.0F, 0.0F, 0);
        gwindowroot.mouseCursors[1] = new GCursorTexRegion(cursors, 0.0F, 0.0F, 32F, 32F, 5F, 4F, 1);
        gwindowroot.mouseCursors[2] = new GCursorTexRegion(cursors, 32F, 0.0F, 32F, 32F, 15F, 15F, 2);
        gwindowroot.mouseCursors[3] = new GCursorTexRegion(cursors, 64F, 0.0F, 32F, 32F, 5F, 4F, 3);
        gwindowroot.mouseCursors[4] = new GCursorTexRegion(cursors, 96F, 0.0F, 32F, 32F, 5F, 4F, 4);
        gwindowroot.mouseCursors[5] = new GCursorTexRegion(cursors, 0.0F, 32F, 32F, 32F, 14F, 15F, 5);
        gwindowroot.mouseCursors[6] = new GCursorTexRegion(cursors, 32F, 32F, 32F, 32F, 5F, 4F, 6);
        gwindowroot.mouseCursors[7] = new GCursorTexRegion(cursors, 64F, 32F, 32F, 32F, 5F, 4F, 7);
        gwindowroot.mouseCursors[8] = new GCursorTexRegion(cursors, 96F, 32F, 32F, 32F, 5F, 4F, 8);
        gwindowroot.mouseCursors[9] = new GCursorTexRegion(cursors, 0.0F, 64F, 32F, 32F, 5F, 4F, 9);
        gwindowroot.mouseCursors[10] = new GCursorTexRegion(cursors, 32F, 64F, 32F, 32F, 5F, 4F, 10);
        gwindowroot.mouseCursors[11] = new GCursorTexRegion(cursors, 64F, 64F, 32F, 32F, 5F, 4F, 11);
        gwindowroot.mouseCursors[12] = new GCursorTexRegion(cursors, 96F, 64F, 32F, 32F, 5F, 4F, 12);
        gwindowroot.mouseCursors[13] = new GCursorTexRegion(cursors, 0.0F, 96F, 32F, 32F, 5F, 4F, 13);
        selectMenuIcon = new GTexRegion(cursors, 116F, 104F, 12F, 12F);
        subMenuIcon = new GTexRegion(cursors, 116F, 116F, 12F, 12F);
    }

    public com.maddox.gwindow.GBevel bevelTabOVER;
    public com.maddox.gwindow.GBevel bevelButtonUP;
    public com.maddox.gwindow.GBevel bevelButtonOVER;
    public com.maddox.gwindow.GBevel bevelButtonDOWN;
    public com.maddox.gwindow.GBevel bevelMenu;
    float spaceMenuBarItem;
    float spaceMenuItem;
    private com.maddox.gwindow.GRegion _titleRegion;
    float spaceFramedTitle;
    float spaceComboList;
    float spaceStatusBar;
    float spaceTab;
    com.maddox.gwindow.GRegion tabReg;
}
