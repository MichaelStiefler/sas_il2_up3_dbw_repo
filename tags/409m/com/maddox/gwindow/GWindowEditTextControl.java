// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowEditTextControl.java

package com.maddox.gwindow;

import java.util.ArrayList;

// Referenced classes of package com.maddox.gwindow:
//            GWindowDialogControl, GWindowEditText, GWindowHScrollBar, GWindowVScrollBar, 
//            GWindowButton, GRegion, GWindowRoot, GFont, 
//            GSize, GWindowLookAndFeel, GWindow

public class GWindowEditTextControl extends com.maddox.gwindow.GWindowDialogControl
{

    public void updateScrollsPos()
    {
        if(vScroll.isVisible())
            vScroll.setPos(-edit.win.y, false);
        if(hScroll.isVisible())
            hScroll.setPos(-edit.win.x, false);
    }

    public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
    {
        if(i == 2)
        {
            if(gwindow == vScroll)
            {
                if(edit != null)
                    editSetPos(edit.win.x, -vScroll.pos());
                return true;
            }
            if(gwindow == hScroll)
            {
                if(edit != null)
                    editSetPos(-hScroll.pos(), edit.win.y);
                return true;
            }
            if(gwindow == edit)
            {
                if(j == 0)
                    resized();
                else
                if(j == 1)
                    clampCaretPos();
                return true;
            }
        }
        if(i == 17)
        {
            if(vScroll.isVisible())
                vScroll.scrollDz(root.mouseRelMoveZ);
            return true;
        } else
        {
            return super.notify(gwindow, i, j);
        }
    }

    private void computeSizeEdit(float f)
    {
        edit.win.dx = f;
        edit.updateTextPos();
        editDx = 0.0F;
        editDy = 0.0F;
        com.maddox.gwindow.GFont gfont = root.textFonts[edit.font];
        int i = edit.textPos.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.gwindow.GWindowEditText.PosLen poslen = (com.maddox.gwindow.GWindowEditText.PosLen)edit.textPos.get(j);
            editDy += gfont.height;
            if(editDx < poslen.width)
                editDx = poslen.width;
        }

    }

    public void editSetPos(float f, float f1)
    {
        com.maddox.gwindow.GFont gfont = root.textFonts[edit.font];
        int i = java.lang.Math.round(f1 / gfont.height);
        edit.setPos(f, (float)i * gfont.height);
    }

    public void clampCaretPos()
    {
        if(edit.isEmpty())
            return;
        com.maddox.gwindow.GFont gfont = root.textFonts[edit.font];
        int i = 0;
        int j = edit.posCaret.ofs;
        int k = edit.textPos.size();
        for(int l = 0; l < k; l++)
        {
            com.maddox.gwindow.GWindowEditText.PosLen poslen = (com.maddox.gwindow.GWindowEditText.PosLen)edit.textPos.get(l);
            if(edit.posCaret.item != poslen.item || j < poslen.ofs || j > poslen.ofs + poslen.len)
                continue;
            i = l;
            break;
        }

        float f = (float)i * gfont.height;
        float f1 = 0.0F;
        if(j > 0)
        {
            com.maddox.gwindow.GWindowEditText.PosLen poslen1 = (com.maddox.gwindow.GWindowEditText.PosLen)edit.textPos.get(i);
            java.lang.StringBuffer stringbuffer = edit.item(poslen1.item);
            com.maddox.gwindow.GWindowEditText _tmp = edit;
            char ac[] = com.maddox.gwindow.GWindowEditText._getArrayRenderBuffer(j - poslen1.ofs);
            stringbuffer.getChars(poslen1.ofs, j, ac, 0);
            com.maddox.gwindow.GSize gsize1 = gfont.size(ac, 0, j - poslen1.ofs);
            f1 = gsize1.dx;
        }
        float f2 = -edit.win.y;
        if(f < f2)
            f2 = f;
        else
        if(f > (f2 + e.win.dy) - gfont.height)
            f2 = (f + gfont.height) - e.win.dy;
        float f3 = -edit.win.x;
        if(f1 < f3)
        {
            f3 = f1 - lookAndFeel().metric();
            if(f3 < 0.0F)
                f3 = 0.0F;
        } else
        {
            com.maddox.gwindow.GSize gsize = gfont.size("|");
            if(f1 > (f3 + e.win.dx) - gsize.dx)
                f3 = (f1 - e.win.dx) + gsize.dx;
        }
        edit.setPos(-f3, -f2);
        updateScrollsPos();
    }

    public void resized()
    {
        if(edit == null)
            return;
        boolean flag = false;
        boolean flag1 = false;
        float f = 0.0F;
        float f1 = 0.0F;
        int i = 0;
        do
        {
            f = win.dx - 2.0F * (border + editBorder);
            if(flag1)
                f -= lookAndFeel().getVScrollBarW();
            f1 = win.dy - 2.0F * (border + editBorder);
            if(flag)
                f1 -= lookAndFeel().getHScrollBarH();
            computeSizeEdit(f);
            if(++i == 3)
                break;
            flag = editDx > f;
            flag1 = editDy > f1;
        } while(true);
        edit.win.dx = editDx;
        if(edit.win.dx < f)
            edit.win.dx = f;
        edit.win.dy = editDy;
        if(edit.win.dy < f1)
            edit.win.dy = f1;
        e.setPosSize(border + editBorder, border + editBorder, f, f1);
        float f2 = edit.win.x;
        float f3 = edit.win.y;
        if(flag1)
        {
            vScroll.setPos(win.dx - lookAndFeel().getVScrollBarW() - border, border);
            vScroll.setSize(lookAndFeel().getVScrollBarW(), f1 + 2.0F * editBorder);
            if(f3 + editDy < f1)
                f3 = f1 - editDy;
            vScroll.setRange(0.0F, editDy, f1, lookAndFeel().metric(), -f3);
            vScroll.showWindow();
        } else
        {
            vScroll.hideWindow();
            f3 = 0.0F;
        }
        if(flag)
        {
            hScroll.setPos(border, win.dy - lookAndFeel().getHScrollBarH() - border);
            hScroll.setSize(f + 2.0F * editBorder, lookAndFeel().getHScrollBarH());
            if(f2 + editDx < f)
                f2 = f - editDx;
            hScroll.setRange(0.0F, editDx, f, lookAndFeel().metric(), -f2);
            hScroll.showWindow();
        } else
        {
            hScroll.hideWindow();
            f2 = 0.0F;
        }
        if(flag && flag1)
        {
            button.setPos(win.dx - border - lookAndFeel().getVScrollBarW(), win.dy - border - lookAndFeel().getHScrollBarH());
            button.setSize(lookAndFeel().getVScrollBarW(), lookAndFeel().getHScrollBarH());
            button.showWindow();
        } else
        {
            button.hideWindow();
        }
        editSetPos(f2, f3);
        clampCaretPos();
    }

    public void resolutionChanged()
    {
        border = lookAndFeel().getBorderSizeEditTextControl();
        resized();
    }

    public void render()
    {
        lookAndFeel().render(this);
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GSize gsize)
    {
        gsize.dx = 2.0F * (lookAndFeel().getVScrollBarW() + border + editBorder);
        gsize.dy = 2.0F * (lookAndFeel().getHScrollBarH() + border + editBorder);
        return gsize;
    }

    public void afterCreated()
    {
        e = (com.maddox.gwindow.GWindowDialogControl)create(new GWindowDialogControl());
        edit = (com.maddox.gwindow.GWindowEditText)e.create(new GWindowEditText());
        edit.notifyWindow = this;
        border = lookAndFeel().getBorderSizeEditTextControl();
        super.afterCreated();
        hScroll = new GWindowHScrollBar(this);
        vScroll = new GWindowVScrollBar(this);
        hScroll.bAlwaysOnTop = true;
        vScroll.bAlwaysOnTop = true;
        hScroll.hideWindow();
        vScroll.hideWindow();
        button = new GWindowButton(this);
        button.bAcceptsKeyFocus = false;
        button.bAlwaysOnTop = true;
        button.bDrawOnlyUP = true;
        button.bDrawActive = false;
        button.hideWindow();
        resized();
    }

    public GWindowEditTextControl()
    {
        border = 4F;
        editBorder = 0.0F;
    }

    public GWindowEditTextControl(com.maddox.gwindow.GWindow gwindow)
    {
        border = 4F;
        editBorder = 0.0F;
        doNew(gwindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
    }

    public GWindowEditTextControl(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2, float f3, java.lang.String s)
    {
        border = 4F;
        editBorder = 0.0F;
        toolTip = s;
        doNew(gwindow, f, f1, f2, f3, true);
    }

    public float border;
    public float editBorder;
    public com.maddox.gwindow.GWindowEditText edit;
    public com.maddox.gwindow.GWindowDialogControl e;
    public com.maddox.gwindow.GWindowVScrollBar vScroll;
    public com.maddox.gwindow.GWindowHScrollBar hScroll;
    public com.maddox.gwindow.GWindowButton button;
    private float editDx;
    private float editDy;
}
