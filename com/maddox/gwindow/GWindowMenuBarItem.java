// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowMenuBarItem.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindow, GWindowMenuBar, GCaption, GWindowLookAndFeel, 
//            GRegion, GWindowMenu, GSize

public class GWindowMenuBarItem extends com.maddox.gwindow.GWindow
{

    public com.maddox.gwindow.GWindowMenuBar menuBar()
    {
        return (com.maddox.gwindow.GWindowMenuBar)parentWindow;
    }

    public com.maddox.gwindow.GWindowMenu subMenu()
    {
        return subMenu;
    }

    public void mouseEnter()
    {
        super.mouseEnter();
        menuBar().setOver(this);
        if(menuBar().selected != null)
            menuBar().setSelected(this);
    }

    public void mouseLeave()
    {
        super.mouseLeave();
        if(menuBar().over == this)
            menuBar().setOver(menuBar().selected);
    }

    public void mouseButton(int i, boolean flag, float f, float f1)
    {
        super.mouseButton(i, flag, f, f1);
        if(flag && i == 0)
        {
            menuBar().setOver(this);
            menuBar().setSelected(menuBar().selected != this ? this : null);
        }
    }

    public void select()
    {
        activateWindow();
        if(subMenu == null)
        {
            return;
        } else
        {
            lAF().soundPlay("MenuPoolDown");
            subMenu.setPos(0.0F, win.dy);
            subMenu.activateWindow();
            return;
        }
    }

    public void unSelect()
    {
        if(subMenu == null)
        {
            return;
        } else
        {
            lAF().soundPlay("MenuCloseUp");
            subMenu.setSelected(null);
            subMenu.hideWindow();
            return;
        }
    }

    public void keyboardKey(int i, boolean flag)
    {
        menuBar().keyboardKey(i, flag);
    }

    public void keyboardChar(char c)
    {
        menuBar().keyboardChar(c);
    }

    public void render()
    {
        lookAndFeel().render(this);
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GSize gsize)
    {
        return lookAndFeel().getMinSize(this, gsize);
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GRegion gregion, float f)
    {
        return lookAndFeel().getClientRegion(this, gregion, f);
    }

    public void resolutionChanged()
    {
        if(cap != null && cap.offsetHotKey > 0)
            cap.offsetHotKey = -1;
        com.maddox.gwindow.GSize gsize = getMinSize();
        win.dx = gsize.dx;
        win.dy = gsize.dy;
        if(subMenu != null)
            subMenu.resolutionChanged();
    }

    public void created()
    {
        super.created();
        com.maddox.gwindow.GSize gsize = getMinSize();
        win.dx = gsize.dx;
        win.dy = gsize.dy;
    }

    public GWindowMenuBarItem(com.maddox.gwindow.GWindowMenuBar gwindowmenubar, java.lang.String s, java.lang.String s1)
    {
        cap = new GCaption(s);
        toolTip = s1;
        doNew(gwindowmenubar, 0.0F, 0.0F, 1.0F, 1.0F, false);
    }

    public com.maddox.gwindow.GWindowMenu subMenu;
    public com.maddox.gwindow.GCaption cap;
    public java.lang.String toolTip;
}
