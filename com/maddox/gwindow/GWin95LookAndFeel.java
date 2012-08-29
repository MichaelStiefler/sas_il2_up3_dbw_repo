// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWin95LookAndFeel.java

package com.maddox.gwindow;

import com.maddox.il2.game.I18N;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.gwindow:
//            GWindowLookAndFeel, GBevel, GRegion, GTreePath, 
//            GWindowMenuItem, GTexRegion, GCursorTexRegion, GWindow, 
//            GWindowTree, GTreeModel, GColor, GWindowRoot, 
//            GCanvas, GPoint, GWindowTable, GWindowTabDialogClient, 
//            GSize, GCaption, GFont, GWindowComboControl, 
//            GWindowVScrollBar, GWindowEditBox, GWindowButtonTexture, GWindowBoxSeparate, 
//            GWindowVSeparate, GWindowHSeparate, GWindowVSliderInt, GWindowHSliderInt, 
//            GWindowEditNumber, GWindowEditControl, GWindowEditText, GWindowEditTextControl, 
//            GWindowCheckBox, GWindowHScrollBar, GWindowLabel, GWindowButton, 
//            GWindowDialogControl, GWindowClient, GWindowFrameCloseBox, GWindowFramed, 
//            GWindowMenuBar, GWindowMenu, GWindowMenuBarItem, GWindowStatusBar, 
//            GTexture

public class GWin95LookAndFeel extends com.maddox.gwindow.GWindowLookAndFeel
{

    public GWin95LookAndFeel()
    {
        metric = 12F;
        bevelUP = new GBevel();
        bevelDOWN = new GBevel();
        bevelUPsmall = new GBevel();
        bevelDOWNsmall = new GBevel();
        bevelFW = new GBevel();
        bevelTitleActive = new GBevel();
        bevelTitleInactive = new GBevel();
        bevelBlack = new GBevel();
        bevelBlack50 = new GBevel();
        bevelSeparate = new GBevel();
        bevelTabCUR = new GBevel();
        bevelTab = new GBevel();
        spaceTab = 0.08333334F;
        tabReg = new GRegion();
        spaceComboList = 3F;
        minScrollMSize = 0.5F;
        spaceLabel = 0.25F;
        spaceButton = 0.1666667F;
        spaceFramedTitle = 0.25F;
        _titleRegion = new GRegion();
        spaceMenuItem = 0.1666667F;
        spaceMenu = 0.25F;
        spaceMenuBarItem = 0.1666667F;
        spaceMenuBar = 0.1666667F;
        spaceStatusBar = 0.08333334F;
    }

    public float metric(float f)
    {
        return (float)(int)(metric * f + 0.5F);
    }

    public float metric()
    {
        return metric;
    }

    public void soundPlay(java.lang.String s)
    {
        if(bSoundEnable)
            java.lang.System.out.println("LF.playSound: " + s);
    }

    public void fillRegion(com.maddox.gwindow.GWindow gwindow, int i, float f, float f1, float f2, float f3)
    {
        gwindow.setCanvasColor(i);
        gwindow.draw(f, f1, f2, f3, elements, 16F, 69F, 1.0F, 1.0F);
    }

    public void drawSeparateH(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2)
    {
        gwindow.setCanvasColorWHITE();
        gwindow.draw(f, f1, f2, 2.0F, elements, 19F, 69F, 1.0F, 2.0F);
    }

    public void drawSeparateW(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2)
    {
        gwindow.setCanvasColorWHITE();
        gwindow.draw(f, f1, 2.0F, f2, elements, 27F, 71F, 2.0F, 1.0F);
    }

    public void renderClient(com.maddox.gwindow.GWindowTree gwindowtree)
    {
        int i = gwindowtree.rows.size();
        if(i == 0 || gwindowtree.model == null)
            return;
        com.maddox.gwindow.GTreePath gtreepath = gwindowtree.model.getRoot();
        int j = gtreepath.getPathCount();
        float f = gwindowtree.getTabStep();
        float f1 = gwindowtree.getBorderSpace();
        if(gwindowtree.bDrawTreeLines)
        {
            int k = com.maddox.gwindow.GColor.Gray.color;
            for(int i1 = 0; i1 < i; i1++)
            {
                com.maddox.gwindow.GTreePath gtreepath1 = (com.maddox.gwindow.GTreePath)gwindowtree.rows.get(i1);
                int j1 = gtreepath1.getPathCount();
                float f3 = gwindowtree.computeHeight(gtreepath1);
                if(j1 == j)
                {
                    f1 += f3;
                    continue;
                }
                float f4 = gwindowtree.getBorderSpace();
                f4 += (float)(j1 - 1 - j) * f;
                boolean flag = true;
                boolean flag1 = true;
                float f5 = 0.0F;
                int k1 = i1 + 1;
                do
                {
                    if(k1 >= i)
                        break;
                    com.maddox.gwindow.GTreePath gtreepath3 = (com.maddox.gwindow.GTreePath)gwindowtree.rows.get(k1);
                    int l1 = gtreepath3.getPathCount();
                    if(k1 == i1 + 1)
                        flag1 = l1 != j1;
                    if(l1 == j1)
                        break;
                    if(l1 < j1)
                    {
                        flag = true;
                        break;
                    }
                    flag = false;
                    f5 += gwindowtree.computeHeight(gtreepath3);
                    k1++;
                } while(true);
                if(k1 == i)
                    flag = true;
                if(!flag)
                    flag1 = false;
                if(gwindowtree.model.isLeaf(gtreepath1))
                {
                    if(flag1)
                        fillRegion(gwindowtree.wClient, k, f4 + f / 2.0F, f1, 1.0F, f3 / 2.0F);
                    else
                        fillRegion(gwindowtree.wClient, k, f4 + f / 2.0F, f1, 1.0F, f3);
                    fillRegion(gwindowtree.wClient, k, f4 + f / 2.0F, f1 + f3 / 2.0F, f / 2.0F, 1.0F);
                } else
                {
                    fillRegion(gwindowtree.wClient, k, f4 + f / 4F, f1 + f3 / 4F, f / 2.0F, 1.0F);
                    fillRegion(gwindowtree.wClient, k, f4 + f / 4F, f1 + (3F * f3) / 4F, f / 2.0F + 1.0F, 1.0F);
                    fillRegion(gwindowtree.wClient, k, f4 + f / 4F, f1 + f3 / 4F, 1.0F, f3 / 2.0F);
                    fillRegion(gwindowtree.wClient, k, f4 + (3F * f) / 4F, f1 + f3 / 4F, 1.0F, f3 / 2.0F);
                    fillRegion(gwindowtree.wClient, k, f4 + f / 4F + 2.0F, f1 + f3 / 2.0F, f / 2.0F - 3F, 1.0F);
                    if(!gwindowtree.isExpanded(gtreepath1))
                        fillRegion(gwindowtree.wClient, k, f4 + f / 2.0F, f1 + f3 / 4F + 2.0F, 1.0F, f3 / 2.0F - 3F);
                    if(i1 != 0)
                        fillRegion(gwindowtree.wClient, k, f4 + f / 2.0F, f1, 1.0F, f3 / 4F);
                    fillRegion(gwindowtree.wClient, k, f4 + (3F * f) / 4F, f1 + f3 / 2.0F, f / 4F, 1.0F);
                    if(!flag1)
                        fillRegion(gwindowtree.wClient, k, f4 + f / 2.0F, f1 + (3F * f3) / 4F, 1.0F, f3 / 4F);
                }
                f1 += f3;
                if(!flag)
                    fillRegion(gwindowtree.wClient, k, f4 + f / 2.0F, f1, 1.0F, f5);
            }

            f1 = gwindowtree.getBorderSpace();
        }
        for(int l = 0; l < i; l++)
        {
            com.maddox.gwindow.GTreePath gtreepath2 = (com.maddox.gwindow.GTreePath)gwindowtree.rows.get(l);
            gwindowtree.computeSize(gtreepath2);
            com.maddox.gwindow.GPoint gpoint = gwindowtree.root.C.org;
            float f2 = gwindowtree.getBorderSpace();
            f2 += (float)(gtreepath2.getPathCount() - j) * f;
            gpoint.add(f2, f1);
            if(gwindowtree.bDrawIcons)
            {
                com.maddox.gwindow.GTexRegion gtexregion = gwindowtree.model.getIcon(gtreepath2, gwindowtree.isSelect(gtreepath2), gwindowtree.isExpanded(gtreepath2));
                if(gtexregion != null)
                {
                    gwindowtree.setCanvasColorWHITE();
                    gwindowtree.draw(0.0F, 0.0F, gwindowtree.getTabStep(), gwindowtree._sizePathDy, gtexregion);
                }
                f2 += f;
                gpoint.add(f, 0.0F);
            }
            if((!gtreepath2.equals(gwindowtree.selectPath) || gwindowtree.editor == null) && gwindowtree._sizePathDx > 0.0F && !gwindowtree.model.render(gtreepath2, gtreepath2.equals(gwindowtree.selectPath), gwindowtree.isExpanded(gtreepath2), gwindowtree._sizePathDx, gwindowtree._sizePathDy))
            {
                java.lang.String s = gwindowtree.model.getString(gtreepath2, gwindowtree.isSelect(gtreepath2), gwindowtree.isExpanded(gtreepath2));
                if(s != null)
                {
                    gwindowtree.setCanvasFont(gwindowtree.font);
                    if(gtreepath2.equals(gwindowtree.selectPath))
                    {
                        gwindowtree.setCanvasColorBLACK();
                        gwindowtree.wClient.draw(0.0F, 0.0F, gwindowtree._sizePathDx, gwindowtree._sizePathDy, regionWhite);
                        gwindowtree.setCanvasColorWHITE();
                    } else
                    {
                        gwindowtree.setCanvasColorBLACK();
                    }
                    com.maddox.gwindow.GWindowTree _tmp = gwindowtree;
                    gwindowtree.wClient.draw(0.0F, 0.0F, gwindowtree._sizePathDx, gwindowtree._sizePathDy, 0, s);
                }
            }
            gpoint.sub(f2, f1);
            f1 += gwindowtree._sizePathDy;
        }

    }

    public void render(com.maddox.gwindow.GWindowTree gwindowtree)
    {
        fillRegion(gwindowtree, -1, 0.0F, 0.0F, gwindowtree.win.dx, gwindowtree.win.dy);
        gwindowtree.setCanvasColorWHITE();
        drawBevel(gwindowtree, 0.0F, 0.0F, gwindowtree.win.dx, gwindowtree.win.dy, bevelDOWN, elements, false);
        com.maddox.gwindow.GRegion gregion = gwindowtree.root.C.clip;
        gregion.x += bevelDOWN.L.dx;
        gregion.y += bevelDOWN.T.dy;
        gregion.dx -= bevelDOWN.L.dx + bevelDOWN.R.dx;
        gregion.dy -= bevelDOWN.T.dy + bevelDOWN.B.dy;
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowTree gwindowtree, com.maddox.gwindow.GRegion gregion, float f)
    {
        gregion.x = bevelDOWN.L.dx + f;
        gregion.y = bevelDOWN.T.dy + f;
        gregion.dx = gwindowtree.win.dx - gregion.x - bevelDOWN.R.dx - f;
        gregion.dy = gwindowtree.win.dy - gregion.y - bevelDOWN.B.dy - f;
        return gregion;
    }

    public void render(com.maddox.gwindow.GWindowTable gwindowtable)
    {
        gwindowtable.setCanvasColorWHITE();
        drawBevel(gwindowtable, 0.0F, 0.0F, gwindowtable.win.dx, gwindowtable.win.dy, bevelDOWN, elements, true);
        com.maddox.gwindow.GRegion gregion = gwindowtable.root.C.clip;
        gregion.x += bevelDOWN.L.dx;
        gregion.y += bevelDOWN.T.dy;
        gregion.dx -= bevelDOWN.L.dx + bevelDOWN.R.dx;
        gregion.dy -= bevelDOWN.T.dy + bevelDOWN.B.dy;
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowTable gwindowtable, com.maddox.gwindow.GRegion gregion, float f)
    {
        gregion.x = bevelDOWN.L.dx + f;
        gregion.y = bevelDOWN.T.dy + f;
        gregion.dx = gwindowtable.win.dx - gregion.x - bevelDOWN.R.dx - f;
        gregion.dy = gwindowtable.win.dy - gregion.y - bevelDOWN.B.dy - f;
        return gregion;
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
        drawBevel(gwindowtabdialogclient, 0.0F, f, gwindowtabdialogclient.win.dx, gwindowtabdialogclient.win.dy - f, bevelTabDialogClient, elements, false);
    }

    public void render(com.maddox.gwindow.GWindowTabDialogClient.Tab tab)
    {
        boolean flag = tab.isCurrent();
        com.maddox.gwindow.GBevel gbevel = flag ? bevelTabCUR : bevelTab;
        tab.setCanvasColorWHITE();
        drawBevel(tab, 0.0F, 0.0F, tab.win.dx, tab.win.dy, gbevel, elements, false);
        tabReg.x = gbevel.L.dx + metric(spaceTab);
        tabReg.y = gbevel.T.dy + metric(spaceTab);
        tabReg.dx = tab.win.dx - gbevel.L.dx - gbevel.R.dx;
        tabReg.dy = tab.win.dy - gbevel.T.dy - gbevel.B.dy;
        if(tab.pushClipRegion(tabReg, tab.bClip, 0.0F))
        {
            if(flag)
                renderTextDialogControl(tab, metric(spaceTab), metric(spaceTab), tabReg.dx - 2.0F * metric(spaceTab), tabReg.dy - 2.0F * metric(spaceTab), 0xffffff, false);
            else
                renderTextDialogControl(tab, metric(spaceTab), metric(spaceTab), tabReg.dx - 2.0F * metric(spaceTab), tabReg.dy - 2.0F * metric(spaceTab), 0, false);
            tab.popClip();
        }
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowTabDialogClient.Tab tab, com.maddox.gwindow.GSize gsize)
    {
        gsize.dx = tab.root.textFonts[tab.font].size(tab.cap.caption).dx + bevelTab.L.dx + bevelTab.R.dx + 2.0F * metric(spaceTab);
        gsize.dy = tab.root.textFonts[tab.font].height + bevelTab.T.dy + bevelTab.B.dy + 2.0F * metric(spaceTab);
        return gsize;
    }

    public void renderComboList(com.maddox.gwindow.GWindowComboControl gwindowcombocontrol)
    {
        com.maddox.gwindow.GWindowComboControl.ListArea listarea = gwindowcombocontrol.listArea;
        listarea.setCanvasColorWHITE();
        listarea.draw(bevelBlack.L.dx, bevelBlack.T.dy, ((com.maddox.gwindow.GWindow) (listarea)).win.dx - bevelBlack.R.dx - bevelBlack.L.dx, ((com.maddox.gwindow.GWindow) (listarea)).win.dy - bevelBlack.B.dy - bevelBlack.T.dy, elements, 5F, 17F, 1.0F, 1.0F);
        drawBevel(listarea, 0.0F, 0.0F, ((com.maddox.gwindow.GWindow) (listarea)).win.dx, ((com.maddox.gwindow.GWindow) (listarea)).win.dy, bevelBlack, elements);
        com.maddox.gwindow.GRegion gregion = listarea.getClientRegion();
        gregion.x += bevelBlack.L.dx + spaceComboList;
        gregion.y += bevelBlack.T.dy;
        gregion.dx -= bevelBlack.L.dx + bevelBlack.R.dx + 2.0F * spaceComboList;
        gregion.dy -= bevelBlack.T.dy + bevelBlack.B.dy;
        if(gwindowcombocontrol.scrollBar.isVisible())
            gregion.dx -= gwindowcombocontrol.scrollBar.win.dx;
        if(listarea.pushClipRegion(gregion, true, 0.0F))
        {
            listarea.setCanvasColorBLACK();
            listarea.setCanvasFont(gwindowcombocontrol.font);
            com.maddox.gwindow.GFont gfont = gwindowcombocontrol.root.C.font;
            float f = (getComboHline() - gfont.height) / 2.0F;
            int i = gwindowcombocontrol.listStartLine;
            for(int j = 0; j < gwindowcombocontrol.listCountLines; j++)
            {
                if(i == gwindowcombocontrol.listSelected)
                {
                    com.maddox.gwindow.GSize gsize = gfont.size(gwindowcombocontrol.get(i));
                    listarea.draw(0.0F, f, ((com.maddox.gwindow.GWindow) (listarea)).win.dx, gsize.dy, elements, 4F, 98F, 1.0F, 1.0F);
                    listarea.setCanvasColorWHITE();
                    listarea.draw(0.0F, f, gwindowcombocontrol.get(i));
                    listarea.setCanvasColorBLACK();
                } else
                if(gwindowcombocontrol.posEnable != null && !gwindowcombocontrol.posEnable[i])
                {
                    com.maddox.gwindow.GSize gsize1 = gfont.size(gwindowcombocontrol.get(i));
                    listarea.setCanvasColorWHITE();
                    listarea.draw(0.0F, f, ((com.maddox.gwindow.GWindow) (listarea)).win.dx, gsize1.dy, elements, 2.0F, 16F, 1.0F, 1.0F);
                    listarea.draw(1.0F, f + 1.0F, gwindowcombocontrol.get(i));
                    listarea.setCanvasColor(0x7f7f7f);
                    listarea.draw(0.0F, f, gwindowcombocontrol.get(i));
                    listarea.setCanvasColorBLACK();
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
        gwindowcombocontrol.listArea.win.dy = (float)gwindowcombocontrol.listCountLines * getComboHline() + bevelBlack.B.dy + bevelBlack.T.dy;
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
            gwindowcombocontrol.scrollBar.win.x = gwindowcombocontrol.listArea.win.dx - gwindowcombocontrol.scrollBar.win.dx - bevelBlack.R.dx;
            gwindowcombocontrol.scrollBar.win.y = bevelBlack.T.dy;
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
            gwindowcombocontrol.draw(bevelDOWN.L.dx, bevelDOWN.T.dy, gwindowcombocontrol.win.dx - bevelDOWN.R.dx - bevelDOWN.L.dx, gwindowcombocontrol.win.dy - bevelDOWN.B.dy - bevelDOWN.T.dy, elements, 5F, 17F, 1.0F, 1.0F);
        else
            gwindowcombocontrol.draw(bevelDOWN.L.dx, bevelDOWN.T.dy, gwindowcombocontrol.win.dx - bevelDOWN.R.dx - bevelDOWN.L.dx, gwindowcombocontrol.win.dy - bevelDOWN.B.dy - bevelDOWN.T.dy, elements, 2.0F, 16F, 1.0F, 1.0F);
        drawBevel(gwindowcombocontrol, 0.0F, 0.0F, gwindowcombocontrol.win.dx, gwindowcombocontrol.win.dy, bevelDOWN, elements, false);
    }

    public void setupComboEditBox(com.maddox.gwindow.GWindowEditBox gwindoweditbox)
    {
        gwindoweditbox.win.x = bevelDOWN.L.dx;
        gwindoweditbox.win.y = bevelDOWN.T.dy;
        gwindoweditbox.win.dx = gwindoweditbox.parentWindow.win.dx - bevelDOWN.L.dx - bevelDOWN.R.dx - getVScrollBarW();
        gwindoweditbox.win.dy = gwindoweditbox.parentWindow.win.dy - bevelDOWN.T.dy - bevelDOWN.B.dy;
    }

    public void setupComboButton(com.maddox.gwindow.GWindowButtonTexture gwindowbuttontexture)
    {
        gwindowbuttontexture.texUP = SBdownButtonUP;
        gwindowbuttontexture.texDOWN = SBdownButtonDOWN;
        gwindowbuttontexture.texDISABLE = SBdownButtonDISABLE;
        gwindowbuttontexture.texOVER = SBdownButtonOVER;
        gwindowbuttontexture.win.dx = getVScrollBarW();
        gwindowbuttontexture.win.dy = getVScrollBarH();
        gwindowbuttontexture.win.x = gwindowbuttontexture.parentWindow.win.dx - gwindowbuttontexture.win.dx - bevelDOWN.R.dx;
        gwindowbuttontexture.win.y = bevelDOWN.T.dy;
    }

    public float getComboH()
    {
        return getVScrollBarH() + bevelDOWN.B.dy + bevelDOWN.T.dy;
    }

    public float getComboHmetric()
    {
        return getComboH() / metric();
    }

    public float getComboHline()
    {
        return metric(1.2F);
    }

    public void render(com.maddox.gwindow.GWindowBoxSeparate gwindowboxseparate)
    {
        gwindowboxseparate.setCanvasColorWHITE();
        if(gwindowboxseparate.exclude != null)
        {
            boolean flag = gwindowboxseparate.win.y >= gwindowboxseparate.exclude.win.y && gwindowboxseparate.win.y < gwindowboxseparate.exclude.win.y + gwindowboxseparate.exclude.win.dy;
            float f = 0.0F;
            if(flag)
            {
                float f1 = bevelSeparate.T.dx;
                bevelSeparate.T.dx = 0.0F;
                drawBevel(gwindowboxseparate, 0.0F, 0.0F, gwindowboxseparate.win.dx, gwindowboxseparate.win.dy, bevelSeparate, elements);
                bevelSeparate.T.dx = f1;
            } else
            {
                float f2 = bevelSeparate.B.dx;
                bevelSeparate.B.dx = 0.0F;
                drawBevel(gwindowboxseparate, 0.0F, 0.0F, gwindowboxseparate.win.dx, gwindowboxseparate.win.dy, bevelSeparate, elements);
                bevelSeparate.B.dx = f2;
                f = gwindowboxseparate.win.dy - 2.0F;
            }
            float f3 = gwindowboxseparate.exclude.win.x - gwindowboxseparate.border;
            if(f3 > gwindowboxseparate.win.x + 2.0F)
                drawSeparateH(gwindowboxseparate, 2.0F, f, f3 - gwindowboxseparate.win.x - 2.0F);
            f3 = gwindowboxseparate.exclude.win.x + gwindowboxseparate.exclude.win.dx + gwindowboxseparate.border;
            if(f3 < (gwindowboxseparate.win.x + gwindowboxseparate.win.dx) - 2.0F)
                drawSeparateH(gwindowboxseparate, f3 - gwindowboxseparate.win.x, f, (gwindowboxseparate.win.x + gwindowboxseparate.win.dx) - 2.0F - f3);
        } else
        {
            drawBevel(gwindowboxseparate, 0.0F, 0.0F, gwindowboxseparate.win.dx, gwindowboxseparate.win.dy, bevelSeparate, elements);
        }
    }

    public void render(com.maddox.gwindow.GWindowVSeparate gwindowvseparate)
    {
        drawSeparateW(gwindowvseparate, 0.0F, 0.0F, gwindowvseparate.win.dy);
    }

    public void render(com.maddox.gwindow.GWindowHSeparate gwindowhseparate)
    {
        drawSeparateH(gwindowhseparate, 0.0F, 0.0F, gwindowhseparate.win.dx);
    }

    public void render(com.maddox.gwindow.GWindowVSliderInt gwindowvsliderint)
    {
        gwindowvsliderint.setCanvasColorWHITE();
        gwindowvsliderint.draw(0.0F, 0.0F, gwindowvsliderint.win.dx, gwindowvsliderint.win.dy, elements, bevelUP.Area);
        drawSeparateW(gwindowvsliderint, (int)(metric() / 2.0F), 0.0F, gwindowvsliderint.win.dy);
        if(gwindowvsliderint.bEnable && gwindowvsliderint.isActivated())
            drawBevel(gwindowvsliderint, 0.0F, 0.0F, gwindowvsliderint.win.dx, gwindowvsliderint.win.dy, bevelBlack50, elements);
        drawBevel(gwindowvsliderint, 0.0F, gwindowvsliderint.yM, metric(), gwindowvsliderint.dyM, bevelUP, elements);
        float f = (float)(int)(metric() / 4F) - 1.0F;
        float f1 = (gwindowvsliderint.win.dy - gwindowvsliderint.dyM) / (float)(gwindowvsliderint.posCount - 1);
        if(f1 < 3F)
            return;
        float f2 = getVSliderIntW() - 3F;
        for(int i = 0; i < gwindowvsliderint.posCount; i++)
        {
            boolean flag = gwindowvsliderint.bEnable;
            if(flag && gwindowvsliderint.posEnable != null)
                flag = gwindowvsliderint.posEnable[i];
            if(flag)
                gwindowvsliderint.draw(f2, f, 2.0F, 2.0F, elements, 53F, 38F, 1.0F, 1.0F);
            else
                gwindowvsliderint.draw(f2, f, 2.0F, 2.0F, elements, 44F, 42F, 2.0F, 2.0F);
            f += f1;
        }

    }

    public void setupVSliderIntSizes(com.maddox.gwindow.GWindowVSliderInt gwindowvsliderint)
    {
        gwindowvsliderint.win.dx = getVSliderIntW();
        gwindowvsliderint.dyM = (int)(metric() / 4F) * 2;
        gwindowvsliderint.yM = gwindowvsliderint.win.dy - gwindowvsliderint.dyM - ((gwindowvsliderint.win.dy - gwindowvsliderint.dyM) / (float)(gwindowvsliderint.posCount - 1)) * (float)(gwindowvsliderint.pos - gwindowvsliderint.posStart);
    }

    public float getVSliderIntW()
    {
        return metric(1.3F);
    }

    public float getVSliderIntWmetric()
    {
        return 1.3F;
    }

    public void render(com.maddox.gwindow.GWindowHSliderInt gwindowhsliderint)
    {
        gwindowhsliderint.setCanvasColorWHITE();
        gwindowhsliderint.draw(0.0F, 0.0F, gwindowhsliderint.win.dx, gwindowhsliderint.win.dy, elements, bevelUP.Area);
        drawSeparateH(gwindowhsliderint, 0.0F, gwindowhsliderint.win.dy - (float)(int)(metric() / 2.0F), gwindowhsliderint.win.dx);
        if(gwindowhsliderint.bEnable && gwindowhsliderint.isActivated())
            drawBevel(gwindowhsliderint, 0.0F, 0.0F, gwindowhsliderint.win.dx, gwindowhsliderint.win.dy, bevelBlack50, elements);
        drawBevel(gwindowhsliderint, gwindowhsliderint.xM, gwindowhsliderint.win.dy - metric(), gwindowhsliderint.dxM, metric(), bevelUP, elements);
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
                gwindowhsliderint.draw(f, 1.0F, 2.0F, 2.0F, elements, 53F, 38F, 1.0F, 1.0F);
            else
                gwindowhsliderint.draw(f, 1.0F, 2.0F, 2.0F, elements, 44F, 42F, 2.0F, 2.0F);
            f += f1;
        }

    }

    public void setupHSliderIntSizes(com.maddox.gwindow.GWindowHSliderInt gwindowhsliderint)
    {
        gwindowhsliderint.win.dy = getHSliderIntH();
        gwindowhsliderint.dxM = (int)(metric() / 4F) * 2;
        gwindowhsliderint.xM = ((gwindowhsliderint.win.dx - gwindowhsliderint.dxM) / (float)(gwindowhsliderint.posCount - 1)) * (float)(gwindowhsliderint.pos - gwindowhsliderint.posStart);
    }

    public float getHSliderIntH()
    {
        return metric(1.3F);
    }

    public float getHSliderIntHmetric()
    {
        return 1.3F;
    }

    public void render(com.maddox.gwindow.GWindowEditNumber gwindoweditnumber)
    {
        if(gwindoweditnumber.bEnable)
            gwindoweditnumber.setCanvasColor(~gwindoweditnumber.color);
        else
            gwindoweditnumber.setCanvasColorWHITE();
        float f = gwindoweditnumber.win.dx;
        if(gwindoweditnumber.bar.isVisible())
            f -= getVScrollBarW();
        gwindoweditnumber.draw(bevelDOWN.L.dx, bevelDOWN.T.dy, f - bevelDOWN.R.dx - bevelDOWN.L.dx, gwindoweditnumber.win.dy - bevelDOWN.B.dy - bevelDOWN.T.dy, elements, 5F, 17F, 1.0F, 1.0F);
        gwindoweditnumber.setCanvasColorWHITE();
        drawBevel(gwindoweditnumber, 0.0F, 0.0F, f, gwindoweditnumber.win.dy, bevelDOWN, elements, false);
    }

    public void setupEditNumber(com.maddox.gwindow.GWindowEditNumber gwindoweditnumber)
    {
        if(gwindoweditnumber.bar.isVisible())
        {
            gwindoweditnumber.box.setPos(bevelDOWN.L.dx, bevelDOWN.T.dy);
            gwindoweditnumber.box.setSize(gwindoweditnumber.win.dx - bevelDOWN.R.dx - bevelDOWN.L.dx - getVScrollBarW(), gwindoweditnumber.win.dy - bevelDOWN.B.dy - bevelDOWN.T.dy);
            gwindoweditnumber.bar.setPos(gwindoweditnumber.win.dx - getVScrollBarW(), 0.0F);
            gwindoweditnumber.bar.setSize(getVScrollBarW(), gwindoweditnumber.win.dy);
        } else
        {
            gwindoweditnumber.box.setPos(bevelDOWN.L.dx, bevelDOWN.T.dy);
            gwindoweditnumber.box.setSize(gwindoweditnumber.win.dx - bevelDOWN.R.dx - bevelDOWN.L.dx, gwindoweditnumber.win.dy - bevelDOWN.B.dy - bevelDOWN.T.dy);
        }
    }

    public void render(com.maddox.gwindow.GWindowEditControl gwindoweditcontrol)
    {
        if(gwindoweditcontrol.bEnable)
            gwindoweditcontrol.setCanvasColor(~gwindoweditcontrol.color);
        else
            gwindoweditcontrol.setCanvasColorWHITE();
        gwindoweditcontrol.draw(bevelDOWN.L.dx, bevelDOWN.T.dy, gwindoweditcontrol.win.dx - bevelDOWN.R.dx - bevelDOWN.L.dx, gwindoweditcontrol.win.dy - bevelDOWN.B.dy - bevelDOWN.T.dy, elements, 5F, 17F, 1.0F, 1.0F);
        gwindoweditcontrol.setCanvasColorWHITE();
        drawBevel(gwindoweditcontrol, 0.0F, 0.0F, gwindoweditcontrol.win.dx, gwindoweditcontrol.win.dy, bevelDOWN, elements, false);
        render(((com.maddox.gwindow.GWindowEditBox) (gwindoweditcontrol)), bevelDOWN.L.dx);
    }

    public void render(com.maddox.gwindow.GWindowEditBox gwindoweditbox, float f)
    {
        if(gwindoweditbox.value == null || gwindoweditbox.value.length() == 0)
        {
            if(gwindoweditbox.bEnable && gwindoweditbox.bCanEdit && gwindoweditbox.bShowCaret)
            {
                gwindoweditbox.setCanvasFont(gwindoweditbox.font);
                gwindoweditbox.setCanvasColor(gwindoweditbox.color);
                gwindoweditbox.draw(f, (gwindoweditbox.win.dy - gwindoweditbox.root.C.font.height) / 2.0F, "|");
            }
            return;
        }
        java.lang.String s = gwindoweditbox.value.toString();
        if(gwindoweditbox.bPassword)
        {
            java.lang.StringBuffer stringbuffer = new StringBuffer(s.length());
            for(int i = 0; i < s.length(); i++)
                stringbuffer.append('*');

            s = stringbuffer.toString();
        }
        gwindoweditbox.setCanvasFont(gwindoweditbox.font);
        com.maddox.gwindow.GFont gfont = gwindoweditbox.root.C.font;
        com.maddox.gwindow.GSize gsize = gfont.size("|");
        float f1 = gsize.dx;
        gsize = gfont.size(s);
        float f2 = (gwindoweditbox.win.dy - gsize.dy) / 2.0F;
        float f3 = f;
        float f4 = 0.0F;
        if(gsize.dx + 2.0F * f1 >= gwindoweditbox.win.dx - 2.0F * f && gwindoweditbox.bEnable && gwindoweditbox.bCanEdit)
        {
            if(gwindoweditbox.caretOffset > 0)
            {
                gsize = gfont.size(s, 0, gwindoweditbox.caretOffset);
                f4 = gsize.dx + 2.0F * f1;
            }
            if(f4 > gwindoweditbox.win.dx - 2.0F * f)
                f3 -= f4 - (gwindoweditbox.win.dx - 2.0F * f);
        } else
        if(gwindoweditbox.align == 2)
            f3 = gwindoweditbox.win.dx - f - gsize.dx;
        else
        if(gwindoweditbox.align == 1)
            f3 = (gwindoweditbox.win.dx - gsize.dx) / 2.0F;
        _editBoxReg.x = f;
        _editBoxReg.y = 0.0F;
        _editBoxReg.dx = gwindoweditbox.win.dx - 2.0F * f;
        _editBoxReg.dy = gwindoweditbox.win.dy;
        f3 -= f;
        if(gwindoweditbox.pushClipRegion(_editBoxReg, gwindoweditbox.bClip, 0.0F))
        {
            if(gwindoweditbox.bEnable)
            {
                if(gwindoweditbox.bAllSelected || !gwindoweditbox.bCanEdit && gwindoweditbox.isKeyFocus())
                {
                    gwindoweditbox.setCanvasColor(gwindoweditbox.color);
                    com.maddox.gwindow.GSize gsize1 = gfont.size(s);
                    gwindoweditbox.draw(f3, f2, gsize1.dx + f1, gsize1.dy, elements, 4F, 98F, 1.0F, 1.0F);
                    gwindoweditbox.setCanvasColor(0xffffff ^ gwindoweditbox.color);
                } else
                {
                    gwindoweditbox.setCanvasColor(gwindoweditbox.color);
                }
                gwindoweditbox.draw(f3, f2, s);
                if(gwindoweditbox.bShowCaret)
                {
                    com.maddox.gwindow.GSize gsize2 = gfont.size(s, 0, gwindoweditbox.caretOffset);
                    gwindoweditbox.draw(f3 + gsize2.dx, f2, "|");
                }
            } else
            {
                gwindoweditbox.setCanvasColor(0xffffff);
                gwindoweditbox.draw(f3 + 1.0F, f2 + 1.0F, s);
                gwindoweditbox.setCanvasColor(0x7f7f7f);
                gwindoweditbox.draw(f3, f2, s);
            }
            gwindoweditbox.popClip();
        }
    }

    public void render(com.maddox.gwindow.GWindowEditText gwindowedittext)
    {
        gwindowedittext.setCanvasFont(gwindowedittext.font);
        com.maddox.gwindow.GFont gfont = gwindowedittext.root.C.font;
        com.maddox.gwindow.GSize gsize = gfont.size("|");
        float f = -gsize.dx / 2.0F;
        if(gwindowedittext.text.size() == 0)
        {
            if(gwindowedittext.bShowCaret)
            {
                gwindowedittext.setCanvasFont(gwindowedittext.font);
                gwindowedittext.setCanvasColor(gwindowedittext.color);
                gwindowedittext.draw(0.0F, 0.0F, "|");
            }
            return;
        }
        gwindowedittext.setCanvasColor(gwindowedittext.color);
        float f1 = gfont.height;
        int i = gwindowedittext.textPos.size();
        boolean flag = false;
        if(gwindowedittext.isSelected() && !gwindowedittext.posCaret.isEqual(gwindowedittext.posSelect))
        {
            com.maddox.gwindow.GWindowEditText.Pos pos = gwindowedittext.posSelect;
            com.maddox.gwindow.GWindowEditText.Pos pos1 = gwindowedittext.posCaret;
            if(pos1.isEqual(pos))
                return;
            if(pos1.isLess(pos))
            {
                com.maddox.gwindow.GWindowEditText.Pos pos2 = pos;
                pos = pos1;
                pos1 = pos2;
            }
            for(int k = 0; k < i; k++)
            {
                com.maddox.gwindow.GWindowEditText.PosLen poslen1 = gwindowedittext.itemPos(k);
                com.maddox.gwindow.GWindowEditText _tmp = gwindowedittext;
                com.maddox.gwindow.GWindowEditText.Pos pos3 = com.maddox.gwindow.GWindowEditText._tmpPos;
                pos3.set(poslen1.item, poslen1.ofs + poslen1.len);
                java.lang.StringBuffer stringbuffer1 = gwindowedittext.item(poslen1.item);
                com.maddox.gwindow.GWindowEditText _tmp1 = gwindowedittext;
                char ac1[] = com.maddox.gwindow.GWindowEditText._getArrayRenderBuffer(poslen1.len);
                stringbuffer1.getChars(poslen1.ofs, poslen1.ofs + poslen1.len, ac1, 0);
                float f3 = 0.0F;
                float f4 = (float)k * f1;
                if(poslen1.isLess(pos1) && pos.isLess(pos3))
                {
                    int l = poslen1.ofs;
                    int i1 = pos3.ofs;
                    if(poslen1.isLess(pos))
                        l = pos.ofs;
                    if(pos1.isLess(pos3))
                        i1 = pos1.ofs;
                    if(l != poslen1.ofs)
                    {
                        com.maddox.gwindow.GSize gsize1 = gfont.size(ac1, 0, l - poslen1.ofs);
                        gwindowedittext.draw(f3, f4, gsize1.dx, f1, 0, ac1, 0, l - poslen1.ofs);
                        f3 += gsize1.dx;
                    }
                    com.maddox.gwindow.GSize gsize2 = gfont.size(ac1, l - poslen1.ofs, i1 - l);
                    gwindowedittext.draw(f3, f4, gsize2.dx, f1, elements, 4F, 98F, 1.0F, 1.0F);
                    gwindowedittext.setCanvasColor(0xffffff ^ gwindowedittext.color);
                    gwindowedittext.draw(f3, f4, gsize2.dx, f1, 0, ac1, l - poslen1.ofs, i1 - l);
                    gwindowedittext.setCanvasColor(gwindowedittext.color);
                    f3 += gsize2.dx;
                    if(i1 != pos3.ofs)
                    {
                        com.maddox.gwindow.GSize gsize3 = gfont.size(ac1, i1 - poslen1.ofs, pos3.ofs - i1);
                        gwindowedittext.draw(f3, f4, gsize3.dx, f1, 0, ac1, i1 - poslen1.ofs, pos3.ofs - i1);
                    }
                } else
                {
                    gwindowedittext.draw(f3, f4, gwindowedittext.win.dx, f1, 0, ac1, 0, poslen1.len);
                }
                if(!flag && gwindowedittext.bShowCaret && poslen1.item == gwindowedittext.posCaret.item && poslen1.ofs <= gwindowedittext.posCaret.ofs && gwindowedittext.posCaret.ofs <= poslen1.ofs + poslen1.len)
                {
                    flag = true;
                    com.maddox.gwindow.GSize gsize4 = gfont.size(ac1, 0, gwindowedittext.posCaret.ofs - poslen1.ofs);
                    gwindowedittext.draw(gsize4.dx + f, f4, "|");
                }
            }

        } else
        {
            for(int j = 0; j < i; j++)
            {
                com.maddox.gwindow.GWindowEditText.PosLen poslen = gwindowedittext.itemPos(j);
                java.lang.StringBuffer stringbuffer = gwindowedittext.item(poslen.item);
                com.maddox.gwindow.GWindowEditText _tmp2 = gwindowedittext;
                char ac[] = com.maddox.gwindow.GWindowEditText._getArrayRenderBuffer(poslen.len);
                stringbuffer.getChars(poslen.ofs, poslen.ofs + poslen.len, ac, 0);
                float f2 = (float)j * f1;
                gwindowedittext.draw(0.0F, f2, gwindowedittext.win.dx, f1, 0, ac, 0, poslen.len);
                if(!flag && gwindowedittext.bShowCaret && poslen.item == gwindowedittext.posCaret.item && poslen.ofs <= gwindowedittext.posCaret.ofs && gwindowedittext.posCaret.ofs <= poslen.ofs + poslen.len)
                {
                    flag = true;
                    com.maddox.gwindow.GSize gsize5 = gfont.size(ac, 0, gwindowedittext.posCaret.ofs - poslen.ofs);
                    gwindowedittext.draw(gsize5.dx + f, f2, "|");
                }
            }

        }
    }

    public void render(com.maddox.gwindow.GWindowEditTextControl gwindowedittextcontrol)
    {
        if(gwindowedittextcontrol.bEnable)
            gwindowedittextcontrol.setCanvasColor(~gwindowedittextcontrol.color);
        else
            gwindowedittextcontrol.setCanvasColorWHITE();
        gwindowedittextcontrol.draw(bevelDOWN.L.dx, bevelDOWN.T.dy, gwindowedittextcontrol.win.dx - bevelDOWN.R.dx - bevelDOWN.L.dx, gwindowedittextcontrol.win.dy - bevelDOWN.B.dy - bevelDOWN.T.dy, elements, 5F, 17F, 1.0F, 1.0F);
        gwindowedittextcontrol.setCanvasColorWHITE();
        drawBevel(gwindowedittextcontrol, 0.0F, 0.0F, gwindowedittextcontrol.win.dx, gwindowedittextcontrol.win.dy, bevelDOWN, elements, false);
    }

    public float getBorderSizeEditTextControl()
    {
        return bevelDOWN.L.dx;
    }

    public void render(com.maddox.gwindow.GWindowCheckBox gwindowcheckbox)
    {
        gwindowcheckbox.setCanvasColorWHITE();
        if(gwindowcheckbox.bChecked)
        {
            if(gwindowcheckbox.bEnable)
                gwindowcheckbox.draw(0.0F, 0.0F, gwindowcheckbox.win.dx, gwindowcheckbox.win.dy, checkBoxCheckEnable);
            else
                gwindowcheckbox.draw(0.0F, 0.0F, gwindowcheckbox.win.dx, gwindowcheckbox.win.dy, checkBoxCheckDisable);
        } else
        if(gwindowcheckbox.bEnable)
            gwindowcheckbox.draw(0.0F, 0.0F, gwindowcheckbox.win.dx, gwindowcheckbox.win.dy, checkBoxUnCheckEnable);
        else
            gwindowcheckbox.draw(0.0F, 0.0F, gwindowcheckbox.win.dx, gwindowcheckbox.win.dy, checkBoxUnCheckDisable);
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowCheckBox gwindowcheckbox, com.maddox.gwindow.GSize gsize)
    {
        gsize.dx = metric();
        gsize.dy = metric();
        return gsize;
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
                gwindowvscrollbar.draw(0.0F, gwindowvscrollbar.uButton.win.dy, gwindowvscrollbar.win.dx, f, elements, 4F, 98F, 1.0F, 1.0F);
            else
                gwindowvscrollbar.draw(0.0F, gwindowvscrollbar.uButton.win.dy, gwindowvscrollbar.win.dx, f, elements, 19F, 64F, 11F, 1.0F);
        }
        if(f1 > 0.0F)
        {
            com.maddox.gwindow.GWindowVScrollBar _tmp1 = gwindowvscrollbar;
            if(gwindowvscrollbar.downState == 2)
                gwindowvscrollbar.draw(0.0F, gwindowvscrollbar.yM + gwindowvscrollbar.dyM, gwindowvscrollbar.win.dx, f1, elements, 4F, 98F, 1.0F, 1.0F);
            else
                gwindowvscrollbar.draw(0.0F, gwindowvscrollbar.yM + gwindowvscrollbar.dyM, gwindowvscrollbar.win.dx, f1, elements, 19F, 64F, 11F, 1.0F);
        }
    }

    public void setupVScrollBarSizes(com.maddox.gwindow.GWindowVScrollBar gwindowvscrollbar)
    {
        float f = gwindowvscrollbar.win.dy - 2.0F * getVScrollBarH();
        boolean flag = f >= 2.0F * metric(minScrollMSize);
        float f1 = getVScrollBarH();
        if(f <= 0.0F)
            f1 = gwindowvscrollbar.win.dy / 2.0F;
        gwindowvscrollbar.uButton.setSize(getVScrollBarW(), f1);
        gwindowvscrollbar.uButton.setPos(0.0F, 0.0F);
        gwindowvscrollbar.dButton.setSize(getVScrollBarW(), f1);
        gwindowvscrollbar.dButton.setPos(0.0F, gwindowvscrollbar.win.dy - f1);
        if(gwindowvscrollbar.posMin >= gwindowvscrollbar.posMax)
        {
            if(flag)
            {
                gwindowvscrollbar.mButton.setSize(getVScrollBarW(), f);
                gwindowvscrollbar.mButton.setPos(0.0F, f1);
                if(!gwindowvscrollbar.mButton.isVisible())
                    gwindowvscrollbar.mButton.showWindow();
                gwindowvscrollbar.yM = f1;
                gwindowvscrollbar.dyM = f;
            } else
            {
                gwindowvscrollbar.mButton.hideWindow();
                gwindowvscrollbar.yM = f1;
                gwindowvscrollbar.dyM = 0.0F;
            }
        } else
        {
            gwindowvscrollbar.yM = (gwindowvscrollbar.pos / ((gwindowvscrollbar.posMax + gwindowvscrollbar.posVisible) - gwindowvscrollbar.posMin)) * f;
            gwindowvscrollbar.dyM = (gwindowvscrollbar.posVisible / ((gwindowvscrollbar.posMax + gwindowvscrollbar.posVisible) - gwindowvscrollbar.posMin)) * f;
            if(gwindowvscrollbar.yM + gwindowvscrollbar.dyM > f)
                gwindowvscrollbar.yM = f - gwindowvscrollbar.dyM;
            if(gwindowvscrollbar.yM < 0.0F)
            {
                gwindowvscrollbar.dyM += gwindowvscrollbar.yM;
                if(gwindowvscrollbar.dyM < 0.0F)
                    gwindowvscrollbar.dyM = 0.0F;
                gwindowvscrollbar.yM = 0.0F;
            }
            if(flag)
            {
                if(gwindowvscrollbar.dyM < metric(minScrollMSize))
                {
                    float f2 = metric(minScrollMSize) - gwindowvscrollbar.dyM;
                    gwindowvscrollbar.yM -= f2;
                    gwindowvscrollbar.dyM = metric(minScrollMSize);
                    if(gwindowvscrollbar.yM < 0.0F)
                        gwindowvscrollbar.yM = 0.0F;
                }
                gwindowvscrollbar.mButton.setSize(getVScrollBarW(), gwindowvscrollbar.dyM);
                gwindowvscrollbar.mButton.setPos(0.0F, gwindowvscrollbar.yM + f1);
                if(!gwindowvscrollbar.mButton.isVisible())
                    gwindowvscrollbar.mButton.showWindow();
            } else
            {
                gwindowvscrollbar.mButton.hideWindow();
            }
            gwindowvscrollbar.yM += f1;
        }
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
                gwindowhscrollbar.draw(gwindowhscrollbar.lButton.win.dx, 0.0F, f, gwindowhscrollbar.win.dy, elements, 4F, 98F, 1.0F, 1.0F);
            else
                gwindowhscrollbar.draw(gwindowhscrollbar.lButton.win.dx, 0.0F, f, gwindowhscrollbar.win.dy, elements, 22F, 60F, 1.0F, 10F);
        }
        if(f1 > 0.0F)
        {
            com.maddox.gwindow.GWindowHScrollBar _tmp1 = gwindowhscrollbar;
            if(gwindowhscrollbar.downState == 2)
                gwindowhscrollbar.draw(gwindowhscrollbar.xM + gwindowhscrollbar.dxM, 0.0F, f1, gwindowhscrollbar.win.dy, elements, 4F, 98F, 1.0F, 1.0F);
            else
                gwindowhscrollbar.draw(gwindowhscrollbar.xM + gwindowhscrollbar.dxM, 0.0F, f1, gwindowhscrollbar.win.dy, elements, 22F, 60F, 1.0F, 10F);
        }
    }

    public void setupHScrollBarSizes(com.maddox.gwindow.GWindowHScrollBar gwindowhscrollbar)
    {
        float f = gwindowhscrollbar.win.dx - 2.0F * getHScrollBarW();
        boolean flag = f >= 2.0F * metric(minScrollMSize);
        float f1 = getHScrollBarW();
        if(f <= 0.0F)
            f1 = gwindowhscrollbar.win.dx / 2.0F;
        gwindowhscrollbar.lButton.setSize(f1, getHScrollBarH());
        gwindowhscrollbar.lButton.setPos(0.0F, 0.0F);
        gwindowhscrollbar.rButton.setSize(f1, getHScrollBarH());
        gwindowhscrollbar.rButton.setPos(gwindowhscrollbar.win.dx - f1, 0.0F);
        if(gwindowhscrollbar.posMin >= gwindowhscrollbar.posMax)
        {
            if(flag)
            {
                gwindowhscrollbar.mButton.setSize(f, getHScrollBarH());
                gwindowhscrollbar.mButton.setPos(f1, 0.0F);
                if(!gwindowhscrollbar.mButton.isVisible())
                    gwindowhscrollbar.mButton.showWindow();
                gwindowhscrollbar.xM = f1;
                gwindowhscrollbar.dxM = f;
            } else
            {
                gwindowhscrollbar.mButton.hideWindow();
                gwindowhscrollbar.xM = f1;
                gwindowhscrollbar.dxM = 0.0F;
            }
        } else
        {
            gwindowhscrollbar.xM = (gwindowhscrollbar.pos / ((gwindowhscrollbar.posMax + gwindowhscrollbar.posVisible) - gwindowhscrollbar.posMin)) * f;
            gwindowhscrollbar.dxM = (gwindowhscrollbar.posVisible / ((gwindowhscrollbar.posMax + gwindowhscrollbar.posVisible) - gwindowhscrollbar.posMin)) * f;
            if(gwindowhscrollbar.xM + gwindowhscrollbar.dxM > f)
                gwindowhscrollbar.xM = f - gwindowhscrollbar.dxM;
            if(gwindowhscrollbar.xM < 0.0F)
            {
                gwindowhscrollbar.dxM += gwindowhscrollbar.xM;
                if(gwindowhscrollbar.dxM < 0.0F)
                    gwindowhscrollbar.dxM = 0.0F;
                gwindowhscrollbar.xM = 0.0F;
            }
            if(flag)
            {
                if(gwindowhscrollbar.dxM < metric(minScrollMSize))
                {
                    float f2 = metric(minScrollMSize) - gwindowhscrollbar.dxM;
                    gwindowhscrollbar.xM -= f2;
                    gwindowhscrollbar.dxM = metric(minScrollMSize);
                    if(gwindowhscrollbar.xM < 0.0F)
                        gwindowhscrollbar.xM = 0.0F;
                }
                gwindowhscrollbar.mButton.setSize(gwindowhscrollbar.dxM, getHScrollBarH());
                gwindowhscrollbar.mButton.setPos(gwindowhscrollbar.xM + f1, 0.0F);
                if(!gwindowhscrollbar.mButton.isVisible())
                    gwindowhscrollbar.mButton.showWindow();
            } else
            {
                gwindowhscrollbar.mButton.hideWindow();
            }
            gwindowhscrollbar.xM += f1;
        }
    }

    public float getHScrollBarW()
    {
        return metric(1.2F);
    }

    public float getHScrollBarH()
    {
        return metric(1.2F);
    }

    public float getVScrollBarW()
    {
        return metric(1.2F);
    }

    public float getVScrollBarH()
    {
        return metric();
    }

    public void setupScrollButtonUP(com.maddox.gwindow.GWindowButtonTexture gwindowbuttontexture)
    {
        gwindowbuttontexture.texUP = SBupButtonUP;
        gwindowbuttontexture.texDOWN = SBupButtonDOWN;
        gwindowbuttontexture.texDISABLE = SBupButtonDISABLE;
        gwindowbuttontexture.texOVER = SBupButtonOVER;
    }

    public void setupScrollButtonDOWN(com.maddox.gwindow.GWindowButtonTexture gwindowbuttontexture)
    {
        gwindowbuttontexture.texUP = SBdownButtonUP;
        gwindowbuttontexture.texDOWN = SBdownButtonDOWN;
        gwindowbuttontexture.texDISABLE = SBdownButtonDISABLE;
        gwindowbuttontexture.texOVER = SBdownButtonOVER;
    }

    public void setupScrollButtonLEFT(com.maddox.gwindow.GWindowButtonTexture gwindowbuttontexture)
    {
        gwindowbuttontexture.texUP = SBleftButtonUP;
        gwindowbuttontexture.texDOWN = SBleftButtonDOWN;
        gwindowbuttontexture.texDISABLE = SBleftButtonDISABLE;
        gwindowbuttontexture.texOVER = SBleftButtonOVER;
    }

    public void setupScrollButtonRIGHT(com.maddox.gwindow.GWindowButtonTexture gwindowbuttontexture)
    {
        gwindowbuttontexture.texUP = SBrightButtonUP;
        gwindowbuttontexture.texDOWN = SBrightButtonDOWN;
        gwindowbuttontexture.texDISABLE = SBrightButtonDISABLE;
        gwindowbuttontexture.texOVER = SBrightButtonOVER;
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
                renderTextDialogControl(gwindowlabel, 1.0F, 1.0F, f, f2, 0xffffff, false);
                renderTextDialogControl(gwindowlabel, 0.0F, 0.0F, f, f2, 0x7f7f7f, false);
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
            renderTextDialogControl(gwindowlabel, 0.0F, 0.0F, f1, f3, gwindowlabel.color, gwindowlabel.isDefault());
            gwindowlabel.popClip();
        }
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowLabel gwindowlabel, com.maddox.gwindow.GSize gsize)
    {
        gsize = getMinSizeDialogControl(gwindowlabel, gsize);
        gsize.dy += 2.0F * metric(spaceLabel);
        return gsize;
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowLabel gwindowlabel, com.maddox.gwindow.GRegion gregion, float f)
    {
        return getClientRegionDialogControl(gwindowlabel, gregion, f);
    }

    public void render(com.maddox.gwindow.GWindowButton gwindowbutton)
    {
        gwindowbutton.setCanvasColorWHITE();
        if(!gwindowbutton.bEnable)
        {
            drawBevel(gwindowbutton, 0.0F, 0.0F, gwindowbutton.win.dx, gwindowbutton.win.dy, bevelUP, elements);
            if(gwindowbutton.cap != null)
            {
                com.maddox.gwindow.GRegion gregion = gwindowbutton.getClientRegion();
                float f = gregion.dx;
                float f2 = gregion.dy;
                if(gwindowbutton.pushClipRegion(gregion, gwindowbutton.bClip, 0.0F))
                {
                    renderTextDialogControl(gwindowbutton, 1.0F, 1.0F, f, f2, 0xffffff, false);
                    renderTextDialogControl(gwindowbutton, 0.0F, 0.0F, f, f2, 0x7f7f7f, false);
                    gwindowbutton.popClip();
                }
            }
            return;
        }
        if(gwindowbutton.bDown && !gwindowbutton.bDrawOnlyUP)
            drawBevel(gwindowbutton, 0.0F, 0.0F, gwindowbutton.win.dx, gwindowbutton.win.dy, bevelDOWN, elements);
        else
            drawBevel(gwindowbutton, 0.0F, 0.0F, gwindowbutton.win.dx, gwindowbutton.win.dy, bevelUP, elements);
        if(gwindowbutton.bDrawActive && gwindowbutton.isActivated())
        {
            drawBevel(gwindowbutton, 0.0F, 0.0F, gwindowbutton.win.dx, gwindowbutton.win.dy, bevelBlack, elements);
            if(gwindowbutton.bDown)
                drawBevel(gwindowbutton, 4F, 4F, gwindowbutton.win.dx - 6F, gwindowbutton.win.dy - 6F, bevelBlack50, elements);
            else
                drawBevel(gwindowbutton, 3F, 3F, gwindowbutton.win.dx - 6F, gwindowbutton.win.dy - 6F, bevelBlack50, elements);
        }
        if(gwindowbutton.cap != null)
        {
            com.maddox.gwindow.GRegion gregion1 = gwindowbutton.getClientRegion();
            float f1 = gregion1.dx;
            float f3 = gregion1.dy;
            if(gwindowbutton.pushClipRegion(gregion1, gwindowbutton.bClip, 0.0F))
            {
                if(gwindowbutton.bDown)
                    renderTextDialogControl(gwindowbutton, 1.0F, 1.0F, f1, f3, gwindowbutton.color, gwindowbutton.isDefault());
                else
                    renderTextDialogControl(gwindowbutton, 0.0F, 0.0F, f1, f3, gwindowbutton.color, gwindowbutton.isDefault());
                gwindowbutton.popClip();
            }
        }
    }

    public void render(com.maddox.gwindow.GWindowButtonTexture gwindowbuttontexture)
    {
        gwindowbuttontexture.setCanvasColorWHITE();
        if(gwindowbuttontexture.bEnable)
        {
            if(gwindowbuttontexture.isMouseDown(0))
            {
                if(gwindowbuttontexture.bStrech)
                    gwindowbuttontexture.draw(0.0F, 0.0F, gwindowbuttontexture.win.dx, gwindowbuttontexture.win.dy, gwindowbuttontexture.texDOWN);
                else
                    gwindowbuttontexture.draw(0.0F, 0.0F, gwindowbuttontexture.texDOWN);
            } else
            if(gwindowbuttontexture.isMouseOver())
            {
                if(gwindowbuttontexture.bStrech)
                    gwindowbuttontexture.draw(0.0F, 0.0F, gwindowbuttontexture.win.dx, gwindowbuttontexture.win.dy, gwindowbuttontexture.texOVER);
                else
                    gwindowbuttontexture.draw(0.0F, 0.0F, gwindowbuttontexture.texOVER);
            } else
            if(gwindowbuttontexture.bStrech)
                gwindowbuttontexture.draw(0.0F, 0.0F, gwindowbuttontexture.win.dx, gwindowbuttontexture.win.dy, gwindowbuttontexture.texUP);
            else
                gwindowbuttontexture.draw(0.0F, 0.0F, gwindowbuttontexture.texUP);
        } else
        if(gwindowbuttontexture.bStrech)
            gwindowbuttontexture.draw(0.0F, 0.0F, gwindowbuttontexture.win.dx, gwindowbuttontexture.win.dy, gwindowbuttontexture.texDISABLE);
        else
            gwindowbuttontexture.draw(0.0F, 0.0F, gwindowbuttontexture.texDISABLE);
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowButton gwindowbutton, com.maddox.gwindow.GSize gsize)
    {
        gsize = getMinSizeDialogControl(gwindowbutton, gsize);
        gsize.dx += bevelUP.L.dx + bevelUP.R.dx + 2.0F * metric(spaceButton);
        gsize.dy += bevelUP.T.dy + bevelUP.B.dy + 2.0F * metric(spaceButton);
        return gsize;
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowButton gwindowbutton, com.maddox.gwindow.GRegion gregion, float f)
    {
        gregion = getClientRegionDialogControl(gwindowbutton, gregion, f);
        gregion.x += bevelUP.L.dx;
        gregion.y += bevelUP.T.dy;
        gregion.dx -= bevelUP.L.dx + bevelUP.R.dx;
        gregion.dy -= bevelUP.T.dy + bevelUP.B.dy;
        return gregion;
    }

    public void renderTextDialogControl(com.maddox.gwindow.GWindowDialogControl gwindowdialogcontrol, float f, float f1, float f2, float f3, int i, boolean flag)
    {
        int j = gwindowdialogcontrol.font;
        if(flag && (j & 1) == 0)
            j++;
        com.maddox.gwindow.GFont gfont = gwindowdialogcontrol.root.textFonts[j];
        gwindowdialogcontrol.setCanvasColor(i);
        com.maddox.gwindow.GSize gsize = gfont.size(gwindowdialogcontrol.cap.caption);
        f1 += (f3 - gsize.dy) / 2.0F;
        if(gwindowdialogcontrol.align == 2)
            f += f2 - gsize.dx;
        else
        if(gwindowdialogcontrol.align == 1)
            f += (f2 - gsize.dx) / 2.0F;
        gwindowdialogcontrol.cap.draw(gwindowdialogcontrol, f, f1, gfont);
    }

    public com.maddox.gwindow.GSize getMinSizeDialogControl(com.maddox.gwindow.GWindowDialogControl gwindowdialogcontrol, com.maddox.gwindow.GSize gsize)
    {
        if(gwindowdialogcontrol.cap != null)
        {
            gsize.dx = gwindowdialogcontrol.root.textFonts[gwindowdialogcontrol.font].size(gwindowdialogcontrol.cap.caption).dx;
            gsize.dy = gwindowdialogcontrol.root.textFonts[gwindowdialogcontrol.font].height;
        } else
        {
            gsize.dx = 1.0F;
            gsize.dy = 1.0F;
        }
        return gsize;
    }

    public com.maddox.gwindow.GRegion getClientRegionDialogControl(com.maddox.gwindow.GWindowDialogControl gwindowdialogcontrol, com.maddox.gwindow.GRegion gregion, float f)
    {
        gregion.x = f;
        gregion.y = f;
        gregion.dx = gwindowdialogcontrol.win.dx - 2.0F * f;
        gregion.dy = gwindowdialogcontrol.win.dy - 2.0F * f;
        return gregion;
    }

    public void render(com.maddox.gwindow.GWindowClient gwindowclient)
    {
        gwindowclient.setCanvasColorWHITE();
        gwindowclient.draw(0.0F, 0.0F, gwindowclient.win.dx, gwindowclient.win.dy, elements, bevelUP.Area);
    }

    public void setupFrameCloseBox(com.maddox.gwindow.GWindowFrameCloseBox gwindowframeclosebox)
    {
        gwindowframeclosebox.texUP = closeBoxUP;
        gwindowframeclosebox.texDOWN = closeBoxDOWN;
        gwindowframeclosebox.texDISABLE = closeBoxDISABLE;
        gwindowframeclosebox.texOVER = closeBoxOVER;
    }

    public void frameSetCloseBoxPos(com.maddox.gwindow.GWindowFramed gwindowframed)
    {
        float f = metric();
        if(f > gwindowframed.root.textFonts[1].height)
            f = gwindowframed.root.textFonts[1].height;
        gwindowframed.closeBox.setPos(gwindowframed.win.dx - bevelTitleActive.R.dx - f - metric(spaceFramedTitle), bevelTitleActive.T.dy + metric(spaceFramedTitle));
        gwindowframed.closeBox.setSize(f, f);
    }

    public int frameHitTest(com.maddox.gwindow.GWindowFramed gwindowframed, float f, float f1)
    {
        com.maddox.gwindow.GRegion gregion = gwindowframed.getClientRegion();
        if(f < 0.0F || f > gwindowframed.win.dx || f1 < 0.0F || f1 > gwindowframed.win.dy)
            return 0;
        if(f >= gregion.x && f < gregion.x + gregion.dx && f1 >= gregion.y && f1 < gregion.y + gregion.dy)
            return 0;
        if(f < gregion.x)
        {
            if(f1 < bevelTitleActive.T.dy)
                return 1;
            return f1 < gregion.y + gregion.dy ? 4 : 6;
        }
        if(f >= gregion.x + gregion.dx)
        {
            if(f1 < bevelTitleActive.T.dy)
                return 3;
            return f1 < gregion.y + gregion.dy ? 5 : 8;
        }
        if(f1 < bevelTitleActive.T.dy)
            return 2;
        return f1 < gregion.y + gregion.dy ? 9 : 7;
    }

    public void render(com.maddox.gwindow.GWindowFramed gwindowframed)
    {
        float f = framedTitleHeight(gwindowframed);
        gwindowframed.setCanvasColorWHITE();
        drawBevel(gwindowframed, 0.0F, f, gwindowframed.win.dx, gwindowframed.win.dy - f, bevelFW, elements);
        com.maddox.gwindow.GBevel gbevel = bevelTitleActive;
        if(!gwindowframed.isActivated())
            gbevel = bevelTitleInactive;
        drawBevel(gwindowframed, 0.0F, 0.0F, gwindowframed.win.dx, f, gbevel, elements);
        if(gwindowframed.title != null)
        {
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

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowFramed gwindowframed, com.maddox.gwindow.GSize gsize)
    {
        float f = 50F;
        float f1 = 50F;
        if(gwindowframed.clientWindow != null)
        {
            com.maddox.gwindow.GSize gsize1 = gwindowframed.clientWindow.getMinSize();
            f = gsize1.dx;
            f1 = gsize1.dy;
        }
        if(gwindowframed.menuBar != null)
        {
            com.maddox.gwindow.GSize gsize2 = gwindowframed.menuBar.getMinSize();
            f1 += gsize2.dy;
        }
        f += bevelFW.L.dx + bevelFW.R.dx;
        f1 += bevelFW.T.dy + bevelFW.B.dy + framedTitleHeight(gwindowframed);
        gsize.set(f, f1);
        return gsize;
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowFramed gwindowframed, com.maddox.gwindow.GRegion gregion, float f)
    {
        gregion.x = bevelFW.L.dx + f;
        gregion.y = framedTitleHeight(gwindowframed) + bevelFW.T.dy + f;
        if(gwindowframed.menuBar != null)
            gregion.y += gwindowframed.menuBar.win.dy;
        gregion.dx = gwindowframed.win.dx - gregion.x - bevelFW.R.dx - f;
        gregion.dy = gwindowframed.win.dy - gregion.y - bevelFW.B.dy - f;
        return gregion;
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
                gwindowmenuitem.setCanvasColorWHITE();
                gwindowmenuitem.draw(0.0F, 0.0F, gwindowmenuitem.win.dx, gwindowmenuitem.win.dy, elements, 4F, 4F, 2.0F, 2.0F);
            } else
            {
                gwindowmenuitem.setCanvasColorBLACK();
            }
            renderMenuItem(gwindowmenuitem, 0.0F, 0.0F);
        } else
        {
            gwindowmenuitem.setCanvasColorWHITE();
            renderMenuItem(gwindowmenuitem, 1.0F, 1.0F);
            gwindowmenuitem.root.C.color.set(127, 127, 127);
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

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowMenuItem gwindowmenuitem, com.maddox.gwindow.GSize gsize)
    {
        gsize.dx = metric() + 2.0F * metric(spaceMenuItem) + gwindowmenuitem.root.textFonts[0].size(gwindowmenuitem.cap.caption).dx + metric();
        gsize.dy = 2.0F * metric(spaceMenuItem) + gwindowmenuitem.root.textFonts[0].height;
        if(gsize.dy < metric())
            gsize.dy = metric();
        return gsize;
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowMenuItem gwindowmenuitem, com.maddox.gwindow.GRegion gregion, float f)
    {
        return gregion;
    }

    public void render(com.maddox.gwindow.GWindowMenu gwindowmenu)
    {
        gwindowmenu.setCanvasColorWHITE();
        float f = gwindowmenu.win.dx / (float)gwindowmenu.columns;
        for(int i = 0; i < gwindowmenu.columns; i++)
            drawBevel(gwindowmenu, f * (float)i, 0.0F, f, gwindowmenu.win.dy, bevelUP, elements);

    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowMenu gwindowmenu, com.maddox.gwindow.GSize gsize)
    {
        gwindowmenu.columns = 1;
        float f = gwindowmenu.root.win.dy - (bevelUP.T.dy + 2.0F * metric(spaceMenu) + bevelUP.B.dy);
        float f1 = 0.0F;
        float f2 = 0.0F;
        int i = gwindowmenu.items.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.gwindow.GWindowMenuItem gwindowmenuitem = (com.maddox.gwindow.GWindowMenuItem)gwindowmenu.items.get(j);
            com.maddox.gwindow.GSize gsize1 = gwindowmenuitem.getMinSize();
            if(f1 < gsize1.dx)
                f1 = gsize1.dx;
            if(f2 + gsize1.dy > f)
            {
                if(gwindowmenu.columns == 1)
                    f = f2;
                gwindowmenu.columns++;
                f2 = 0.0F;
            } else
            {
                f2 += gsize1.dy;
            }
        }

        gsize.dx = (float)gwindowmenu.columns * (f1 + bevelUP.L.dx + 2.0F * metric(spaceMenu) + bevelUP.R.dx);
        if(gwindowmenu.columns > 1)
            f2 = f;
        gsize.dy = f2 + bevelUP.T.dy + 2.0F * metric(spaceMenu) + bevelUP.B.dy;
        return gsize;
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowMenu gwindowmenu, com.maddox.gwindow.GRegion gregion, float f)
    {
        gregion.x = bevelUP.L.dx + metric(spaceMenu) + f;
        gregion.y = bevelUP.T.dy + metric(spaceMenu) + f;
        gregion.dx = gwindowmenu.win.dx - gregion.x - bevelUP.R.dx - metric(spaceMenu) - f;
        gregion.dy = gwindowmenu.win.dy - gregion.y - bevelUP.B.dy - metric(spaceMenu) - f;
        return gregion;
    }

    public void render(com.maddox.gwindow.GWindowMenuBarItem gwindowmenubaritem)
    {
        if(gwindowmenubaritem == gwindowmenubaritem.menuBar().selected)
        {
            gwindowmenubaritem.setCanvasColorWHITE();
            drawBevel(gwindowmenubaritem, 0.0F, 0.0F, gwindowmenubaritem.win.dx, gwindowmenubaritem.win.dy, bevelDOWNsmall, elements);
        } else
        if(gwindowmenubaritem == gwindowmenubaritem.menuBar().over)
        {
            gwindowmenubaritem.setCanvasColorWHITE();
            drawBevel(gwindowmenubaritem, 0.0F, 0.0F, gwindowmenubaritem.win.dx, gwindowmenubaritem.win.dy, bevelUPsmall, elements);
        }
        if(gwindowmenubaritem.pushClipRegion(gwindowmenubaritem.getClientRegion(), gwindowmenubaritem.bClip, 0.0F))
        {
            gwindowmenubaritem.setCanvasColorBLACK();
            float f = metric(spaceMenuBarItem);
            float f1 = metric(spaceMenuBarItem);
            if(gwindowmenubaritem == gwindowmenubaritem.menuBar().selected)
                f = f1 = metric(spaceMenuBarItem) + 1.0F;
            else
            if(gwindowmenubaritem == gwindowmenubaritem.menuBar().over)
                f = f1 = metric(spaceMenuBarItem) - 1.0F;
            gwindowmenubaritem.cap.draw(gwindowmenubaritem, f, f1, gwindowmenubaritem.root.textFonts[0], gwindowmenubaritem.menuBar().bAltDown && gwindowmenubaritem.menuBar().over == null);
            gwindowmenubaritem.popClip();
        }
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowMenuBarItem gwindowmenubaritem, com.maddox.gwindow.GSize gsize)
    {
        gsize.dx = bevelUPsmall.L.dx + bevelUPsmall.R.dx + 2.0F * metric(spaceMenuBarItem) + gwindowmenubaritem.root.textFonts[0].size(gwindowmenubaritem.cap.caption).dx;
        gsize.dy = getMenuBarItemHeight(gwindowmenubaritem);
        return gsize;
    }

    protected float getMenuBarItemHeight(com.maddox.gwindow.GWindow gwindow)
    {
        return bevelUPsmall.T.dy + bevelUPsmall.B.dy + 2.0F * metric(spaceMenuBarItem) + gwindow.root.textFonts[0].height;
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowMenuBarItem gwindowmenubaritem, com.maddox.gwindow.GRegion gregion, float f)
    {
        gregion.x = bevelUPsmall.L.dx + f;
        gregion.y = bevelUPsmall.T.dy + f;
        com.maddox.gwindow.GSize gsize = gwindowmenubaritem.getMinSize();
        gregion.dx = gsize.dx - gregion.x - bevelUPsmall.R.dx - bevelUPsmall.R.dx - f;
        gregion.dy = gsize.dy - gregion.y - bevelUPsmall.B.dy - bevelUPsmall.B.dy - f;
        return gregion;
    }

    public void render(com.maddox.gwindow.GWindowMenuBar gwindowmenubar)
    {
        gwindowmenubar.setCanvasColorWHITE();
        com.maddox.gwindow.GRegion gregion = bevelUP.B;
        gwindowmenubar.draw(0.0F, gwindowmenubar.win.dy - bevelUP.B.dy, gwindowmenubar.win.dx, bevelUP.B.dy, elements, gregion.x, gregion.y, gregion.dx, gregion.dy);
        gregion = bevelUP.Area;
        gwindowmenubar.draw(0.0F, 0.0F, gwindowmenubar.win.dx, gwindowmenubar.win.dy - bevelUP.B.dy, elements, gregion.x, gregion.y, gregion.dx, gregion.dy);
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowMenuBar gwindowmenubar, com.maddox.gwindow.GSize gsize)
    {
        gsize.dx = 2.0F * metric(spaceMenuBar);
        gsize.dy = 2.0F * metric(spaceMenuBar) + getMenuBarItemHeight(gwindowmenubar) + bevelUP.B.dy;
        return gsize;
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowMenuBar gwindowmenubar, com.maddox.gwindow.GRegion gregion, float f)
    {
        gregion.x = metric(spaceMenuBar) + f;
        gregion.y = metric(spaceMenuBar) + f;
        com.maddox.gwindow.GSize gsize = gwindowmenubar.getMinSize();
        gregion.dx = gsize.dx - 2.0F * metric(spaceMenuBar) - 2.0F * f;
        gregion.dy = gsize.dy - 2.0F * metric(spaceMenuBar) - bevelUP.B.dy - 2.0F * f;
        return gregion;
    }

    public void render(com.maddox.gwindow.GWindowStatusBar gwindowstatusbar)
    {
        gwindowstatusbar.setCanvasColorWHITE();
        drawBevel(gwindowstatusbar, 0.0F, 0.0F, gwindowstatusbar.win.dx, gwindowstatusbar.win.dy, bevelUP, elements);
        drawBevel(gwindowstatusbar, bevelUP.L.dx + metric(spaceStatusBar), bevelUP.T.dy + metric(spaceStatusBar), gwindowstatusbar.win.dx - 2.0F * metric(spaceStatusBar) - bevelUP.L.dx - bevelUP.R.dx, gwindowstatusbar.win.dy - 2.0F * metric(spaceStatusBar) - bevelUP.T.dy - bevelUP.B.dy, bevelDOWNsmall, elements);
        if(gwindowstatusbar.pushClipRegion(gwindowstatusbar.getClientRegion(), gwindowstatusbar.bClip, 0.0F))
        {
            gwindowstatusbar.setCanvasColorBLACK();
            gwindowstatusbar.setCanvasFont(0);
            if(gwindowstatusbar.help != null && !"".equals(gwindowstatusbar.help))
                gwindowstatusbar.draw(0.0F, 0.0F, gwindowstatusbar.help);
            else
                gwindowstatusbar.draw(0.0F, 0.0F, gwindowstatusbar.defaultHelp);
            gwindowstatusbar.popClip();
        }
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowStatusBar gwindowstatusbar, com.maddox.gwindow.GSize gsize)
    {
        gsize.dx = bevelUP.L.dx + bevelUP.R.dx + 4F * metric(spaceStatusBar) + bevelDOWNsmall.L.dx + bevelDOWNsmall.R.dx;
        gsize.dy = bevelUP.T.dy + bevelUP.B.dy + 4F * metric(spaceStatusBar) + bevelDOWNsmall.T.dy + bevelDOWNsmall.B.dy + gwindowstatusbar.root.textFonts[0].height;
        return gsize;
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowStatusBar gwindowstatusbar, com.maddox.gwindow.GRegion gregion, float f)
    {
        gregion.x = bevelUP.L.dx + 2.0F * metric(spaceStatusBar) + bevelDOWNsmall.L.dx + f;
        gregion.y = bevelUP.T.dy + 2.0F * metric(spaceStatusBar) + bevelDOWNsmall.T.dy + f;
        gregion.dx = gwindowstatusbar.win.dx - gregion.x - bevelUP.R.dx - 2.0F * metric(spaceStatusBar) - bevelDOWNsmall.R.dx - f;
        gregion.dy = gwindowstatusbar.win.dy - gregion.y - bevelUP.B.dy - 2.0F * metric(spaceStatusBar) - bevelDOWNsmall.B.dy - f;
        return gregion;
    }

    public void resolutionChanged(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        gwindowroot.textFonts[0].resolutionChanged();
        gwindowroot.textFonts[1].resolutionChanged();
        metric = (int)(gwindowroot.textFonts[0].height + 0.5F);
    }

    public void init(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        elements = com.maddox.gwindow.GTexture.New("GUI/win95/elements.mat");
        elementsStreched = com.maddox.gwindow.GTexture.New("GUI/win95/elementss.mat");
        cursors = com.maddox.gwindow.GTexture.New("GUI/win95/cursors.mat");
        cursorsStreched = com.maddox.gwindow.GTexture.New("GUI/win95/cursorss.mat");
        regionWhite = new GTexRegion(elements, 5F, 17F, 1.0F, 1.0F);
        bevelUP.set(new GRegion(4F, 16F, 16F, 16F), new GRegion(6F, 18F, 12F, 12F));
        bevelTabDialogClient = bevelUP;
        bevelDOWN.set(new GRegion(52F, 37F, 16F, 16F), new GRegion(54F, 39F, 12F, 12F));
        bevelUPsmall.set(new GRegion(19F, 60F, 11F, 10F), new GRegion(20F, 61F, 9F, 8F));
        bevelDOWNsmall.set(new GRegion(19F, 71F, 10F, 9F), new GRegion(20F, 72F, 8F, 7F));
        bevelFW.set(new GRegion(0.0F, 16F, 128F, 112F), new GRegion(4F, 16F, 120F, 108F));
        bevelTitleActive.set(new GRegion(0.0F, 0.0F, 128F, 16F), new GRegion(4F, 4F, 120F, 11F));
        bevelTitleInactive.set(new GRegion(70F, 18F, 53F, 16F), new GRegion(74F, 22F, 45F, 11F));
        bevelFW.Area.dx = 0.0F;
        bevelBlack.set(new GRegion(4F, 98F, 4F, 4F), new GRegion(5F, 99F, 2.0F, 2.0F));
        bevelBlack.Area.dx = 0.0F;
        bevelBlack50.set(new GRegion(32F, 16F, 12F, 10F), new GRegion(33F, 17F, 10F, 8F));
        bevelBlack50.Area.dx = 0.0F;
        bevelSeparate.set(new GRegion(87F, 69F, 8F, 7F), new GRegion(89F, 71F, 4F, 3F));
        bevelSeparate.Area.dx = 0.0F;
        bevelTabCUR.set(new GRegion(4F, 82F, 53F, 15F), new GRegion(8F, 86F, 45F, 8F));
        bevelTabCUR.Area.dx = 0.0F;
        bevelTab.set(new GRegion(57F, 82F, 54F, 13F), new GRegion(61F, 87F, 46F, 8F));
        bevelTab.Area.dx = 0.0F;
        closeBoxUP = new GTexRegion(elementsStreched, 4F, 32F, 10F, 9F);
        closeBoxDOWN = new GTexRegion(elementsStreched, 4F, 43F, 10F, 9F);
        closeBoxDISABLE = closeBoxOVER = closeBoxUP;
        SBupButtonUP = new GTexRegion(elementsStreched, 20F, 16F, 12F, 10F);
        SBupButtonDOWN = new GTexRegion(elementsStreched, 32F, 16F, 12F, 10F);
        SBupButtonDISABLE = new GTexRegion(elementsStreched, 44F, 16F, 12F, 10F);
        SBupButtonOVER = SBupButtonUP;
        SBdownButtonUP = new GTexRegion(elementsStreched, 20F, 26F, 12F, 10F);
        SBdownButtonDOWN = new GTexRegion(elementsStreched, 32F, 26F, 12F, 10F);
        SBdownButtonDISABLE = new GTexRegion(elementsStreched, 44F, 26F, 12F, 10F);
        SBdownButtonOVER = SBdownButtonUP;
        SBleftButtonUP = new GTexRegion(elementsStreched, 20F, 48F, 10F, 12F);
        SBleftButtonDOWN = new GTexRegion(elementsStreched, 30F, 48F, 10F, 12F);
        SBleftButtonDISABLE = new GTexRegion(elementsStreched, 40F, 48F, 10F, 12F);
        SBleftButtonOVER = SBleftButtonUP;
        SBrightButtonUP = new GTexRegion(elementsStreched, 20F, 36F, 10F, 12F);
        SBrightButtonDOWN = new GTexRegion(elementsStreched, 30F, 36F, 10F, 12F);
        SBrightButtonDISABLE = new GTexRegion(elementsStreched, 40F, 36F, 10F, 12F);
        SBrightButtonOVER = SBrightButtonUP;
        checkBoxCheckEnable = new GTexRegion(elementsStreched, 32F, 64F, 13F, 13F);
        checkBoxCheckDisable = new GTexRegion(elementsStreched, 45F, 64F, 13F, 13F);
        checkBoxUnCheckEnable = new GTexRegion(elementsStreched, 58F, 64F, 13F, 13F);
        checkBoxUnCheckDisable = new GTexRegion(elementsStreched, 71F, 64F, 13F, 13F);
        gwindowroot.textFonts[0] = com.maddox.gwindow.GFont.New("arial10");
        gwindowroot.textFonts[1] = com.maddox.gwindow.GFont.New("arialb10");
        metric = (int)(gwindowroot.textFonts[0].height + 0.5F);
        gwindowroot.mouseCursors[0] = new GCursorTexRegion(cursors, 32F, 96F, 0.0F, 0.0F, 0.0F, 0.0F, 0);
        gwindowroot.mouseCursors[1] = new GCursorTexRegion(cursors, 0.0F, 0.0F, 32F, 32F, 5F, 4F, 1);
        gwindowroot.mouseCursors[2] = new GCursorTexRegion(cursors, 32F, 0.0F, 32F, 32F, 14F, 15F, 2);
        gwindowroot.mouseCursors[3] = new GCursorTexRegion(cursors, 64F, 0.0F, 32F, 32F, 14F, 17F, 3);
        gwindowroot.mouseCursors[4] = new GCursorTexRegion(cursors, 96F, 0.0F, 32F, 32F, 3F, 2.0F, 4);
        gwindowroot.mouseCursors[5] = new GCursorTexRegion(cursors, 0.0F, 32F, 32F, 32F, 14F, 15F, 5);
        gwindowroot.mouseCursors[6] = new GCursorTexRegion(cursors, 32F, 32F, 32F, 32F, 15F, 16F, 6);
        gwindowroot.mouseCursors[7] = new GCursorTexRegion(cursors, 64F, 32F, 32F, 32F, 15F, 15F, 7);
        gwindowroot.mouseCursors[8] = new GCursorTexRegion(cursors, 96F, 32F, 32F, 32F, 15F, 15F, 8);
        gwindowroot.mouseCursors[9] = new GCursorTexRegion(cursors, 0.0F, 64F, 32F, 32F, 15F, 16F, 9);
        gwindowroot.mouseCursors[10] = new GCursorTexRegion(cursors, 32F, 64F, 32F, 32F, 14F, 14F, 10);
        gwindowroot.mouseCursors[11] = new GCursorTexRegion(cursors, 64F, 64F, 32F, 32F, 16F, 15F, 11);
        gwindowroot.mouseCursors[12] = new GCursorTexRegion(cursors, 96F, 64F, 32F, 32F, 14F, 15F, 12);
        gwindowroot.mouseCursors[13] = new GCursorTexRegion(cursors, 0.0F, 96F, 32F, 32F, 15F, 15F, 13);
        selectMenuIcon = new GTexRegion(cursorsStreched, 116F, 104F, 12F, 12F);
        subMenuIcon = new GTexRegion(cursorsStreched, 116F, 116F, 12F, 12F);
    }

    public java.lang.String i18n(java.lang.String s)
    {
        return com.maddox.il2.game.I18N.gwindow(s);
    }

    public float metric;
    public com.maddox.gwindow.GBevel bevelUP;
    public com.maddox.gwindow.GBevel bevelDOWN;
    public com.maddox.gwindow.GBevel bevelUPsmall;
    public com.maddox.gwindow.GBevel bevelDOWNsmall;
    public com.maddox.gwindow.GBevel bevelFW;
    public com.maddox.gwindow.GBevel bevelTitleActive;
    public com.maddox.gwindow.GBevel bevelTitleInactive;
    public com.maddox.gwindow.GBevel bevelBlack;
    public com.maddox.gwindow.GBevel bevelBlack50;
    public com.maddox.gwindow.GBevel bevelSeparate;
    public com.maddox.gwindow.GBevel bevelTabCUR;
    public com.maddox.gwindow.GBevel bevelTab;
    public com.maddox.gwindow.GTexture elements;
    public com.maddox.gwindow.GTexture elementsStreched;
    public com.maddox.gwindow.GTexture cursors;
    public com.maddox.gwindow.GTexture cursorsStreched;
    public com.maddox.gwindow.GTexRegion selectMenuIcon;
    public com.maddox.gwindow.GTexRegion subMenuIcon;
    public com.maddox.gwindow.GTexRegion closeBoxUP;
    public com.maddox.gwindow.GTexRegion closeBoxDOWN;
    public com.maddox.gwindow.GTexRegion closeBoxDISABLE;
    public com.maddox.gwindow.GTexRegion closeBoxOVER;
    public com.maddox.gwindow.GTexRegion SBupButtonUP;
    public com.maddox.gwindow.GTexRegion SBupButtonDOWN;
    public com.maddox.gwindow.GTexRegion SBupButtonDISABLE;
    public com.maddox.gwindow.GTexRegion SBupButtonOVER;
    public com.maddox.gwindow.GTexRegion SBdownButtonUP;
    public com.maddox.gwindow.GTexRegion SBdownButtonDOWN;
    public com.maddox.gwindow.GTexRegion SBdownButtonDISABLE;
    public com.maddox.gwindow.GTexRegion SBdownButtonOVER;
    public com.maddox.gwindow.GTexRegion SBleftButtonUP;
    public com.maddox.gwindow.GTexRegion SBleftButtonDOWN;
    public com.maddox.gwindow.GTexRegion SBleftButtonDISABLE;
    public com.maddox.gwindow.GTexRegion SBleftButtonOVER;
    public com.maddox.gwindow.GTexRegion SBrightButtonUP;
    public com.maddox.gwindow.GTexRegion SBrightButtonDOWN;
    public com.maddox.gwindow.GTexRegion SBrightButtonDISABLE;
    public com.maddox.gwindow.GTexRegion SBrightButtonOVER;
    public com.maddox.gwindow.GTexRegion checkBoxCheckEnable;
    public com.maddox.gwindow.GTexRegion checkBoxCheckDisable;
    public com.maddox.gwindow.GTexRegion checkBoxUnCheckEnable;
    public com.maddox.gwindow.GTexRegion checkBoxUnCheckDisable;
    float spaceTab;
    com.maddox.gwindow.GRegion tabReg;
    float spaceComboList;
    private static com.maddox.gwindow.GRegion _editBoxReg = new GRegion();
    float minScrollMSize;
    float spaceLabel;
    float spaceButton;
    float spaceFramedTitle;
    private com.maddox.gwindow.GRegion _titleRegion;
    float spaceMenuItem;
    float spaceMenu;
    float spaceMenuBarItem;
    float spaceMenuBar;
    float spaceStatusBar;

}
