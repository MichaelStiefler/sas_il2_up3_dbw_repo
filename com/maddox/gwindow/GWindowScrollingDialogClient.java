// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowScrollingDialogClient.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindowDialogClient, GWindowHScrollBar, GWindowVScrollBar, GWindowButton, 
//            GRegion, GWindowRoot, GSize, GWindowLookAndFeel, 
//            GWindow

public class GWindowScrollingDialogClient extends com.maddox.gwindow.GWindowDialogClient
{

    public void updateScrollsPos()
    {
        if(vScroll.isVisible())
            vScroll.setPos(-fixed.win.y, false);
        if(hScroll.isVisible())
            hScroll.setPos(-fixed.win.x, false);
    }

    public void mouseButton(int i, boolean flag, float f, float f1)
    {
        super.mouseButton(i, flag, f, f1);
        if(i != 0)
        {
            return;
        } else
        {
            mousePressed(flag);
            return;
        }
    }

    public void mouseMove(float f, float f1)
    {
        super.mouseMove(f, f1);
        mouseMoved();
    }

    public void mousePressed(boolean flag)
    {
        if(!vScroll.isVisible() && !hScroll.isVisible())
        {
            return;
        } else
        {
            mouseCapture(flag);
            return;
        }
    }

    public void mouseMoved()
    {
        if(fixed == null)
            return;
        if(isMouseCaptured())
        {
            mouseCursor = 7;
            float f = fixed.win.x;
            float f1 = fixed.win.y;
            if(vScroll.isVisible())
            {
                f1 += root.mouseStep.dy;
                if(f1 > 0.0F)
                    f1 = 0.0F;
                float f2 = win.dy;
                if(hScroll.isVisible())
                    f2 -= lookAndFeel().getHScrollBarH();
                if(f1 + fixed.win.dy < f2)
                    f1 = f2 - fixed.win.dy;
            }
            if(hScroll.isVisible())
            {
                f += root.mouseStep.dx;
                if(f > 0.0F)
                    f = 0.0F;
                float f3 = win.dx;
                if(vScroll.isVisible())
                    f3 -= lookAndFeel().getVScrollBarW();
                if(f + fixed.win.dx < f3)
                    f = f3 - fixed.win.dx;
            }
            fixed.setPos(f, f1);
            updateScrollsPos();
        } else
        if(vScroll.isVisible() || hScroll.isVisible())
        {
            mouseCursor = 3;
            fixed.mouseCursor = 3;
        } else
        {
            mouseCursor = 1;
            fixed.mouseCursor = 1;
        }
    }

    public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
    {
        if(i == 2)
        {
            if(gwindow == vScroll)
            {
                if(fixed != null)
                {
                    fixed.setPos(fixed.win.x, -vScroll.pos());
                    updateScrollsPos();
                }
                return true;
            }
            if(gwindow == hScroll)
            {
                if(fixed != null)
                {
                    fixed.setPos(-hScroll.pos(), fixed.win.y);
                    updateScrollsPos();
                }
                return true;
            }
        }
        if(i == 17)
        {
            if(vScroll.isVisible() && fixed != null)
                vScroll.scrollDz(root.mouseRelMoveZ);
            return true;
        }
        if(gwindow == fixed)
            switch(i)
            {
            default:
                break;

            case 8: // '\b'
                mouseMoved();
                return true;

            case 3: // '\003'
                if(j == 0)
                {
                    mousePressed(true);
                    return true;
                }
                break;

            case 4: // '\004'
                if(j == 0)
                {
                    mousePressed(false);
                    return true;
                }
                break;
            }
        return super.notify(gwindow, i, j);
    }

    public void resized()
    {
        if(fixed == null)
            return;
        boolean flag = false;
        boolean flag1 = false;
        float f = (int)(win.dx + 0.5F);
        float f1 = (int)(win.dy + 0.5F);
        for(int i = 0; i < 2; i++)
        {
            f = win.dx;
            if(flag1)
                f -= lookAndFeel().getVScrollBarW();
            f1 = win.dy;
            if(flag)
                f1 -= lookAndFeel().getHScrollBarH();
            flag = fixed.win.dx > f;
            flag1 = fixed.win.dy > f1;
        }

        float f2 = fixed.win.x;
        float f3 = fixed.win.y;
        if(flag1)
        {
            vScroll.setPos(win.dx - lookAndFeel().getVScrollBarW(), 0.0F);
            vScroll.setSize(lookAndFeel().getVScrollBarW(), f1);
            if(f3 + fixed.win.dy < f1)
                f3 = f1 - fixed.win.dy;
            vScroll.setRange(0.0F, fixed.win.dy, f1, lookAndFeel().metric(), -f3);
            vScroll.showWindow();
        } else
        {
            vScroll.hideWindow();
            f3 = 0.0F;
        }
        if(flag)
        {
            hScroll.setPos(0.0F, win.dy - lookAndFeel().getHScrollBarH());
            hScroll.setSize(f, lookAndFeel().getHScrollBarH());
            if(f2 + fixed.win.dx < f)
                f2 = f - fixed.win.dx;
            hScroll.setRange(0.0F, fixed.win.dx, f, lookAndFeel().metric(), -f2);
            hScroll.showWindow();
        } else
        {
            hScroll.hideWindow();
            f2 = 0.0F;
        }
        fixed.setPos(f2, f3);
        if(flag && flag1)
        {
            button.setPos(f, f1);
            button.setSize(lookAndFeel().getVScrollBarW(), lookAndFeel().getHScrollBarH());
            button.showWindow();
        } else
        {
            button.hideWindow();
        }
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GSize gsize)
    {
        if(fixed != null)
            return fixed.getMinSize(gsize);
        else
            return super.getMinSize(gsize);
    }

    public void afterCreated()
    {
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

    public GWindowScrollingDialogClient()
    {
    }

    public GWindowScrollingDialogClient(com.maddox.gwindow.GWindow gwindow)
    {
        doNew(gwindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
    }

    public com.maddox.gwindow.GWindowDialogClient fixed;
    public com.maddox.gwindow.GWindowVScrollBar vScroll;
    public com.maddox.gwindow.GWindowHScrollBar hScroll;
    public com.maddox.gwindow.GWindowButton button;
}
