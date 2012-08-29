// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowMenuItem.java

package com.maddox.gwindow;

import java.io.PrintStream;

// Referenced classes of package com.maddox.gwindow:
//            GWindow, GWindowMenu, GCaption, GWindowLookAndFeel, 
//            GRegion, GSize

public class GWindowMenuItem extends com.maddox.gwindow.GWindow
{

    public com.maddox.gwindow.GWindowMenu menu()
    {
        return (com.maddox.gwindow.GWindowMenu)parentWindow;
    }

    public com.maddox.gwindow.GWindowMenu subMenu()
    {
        return subMenu;
    }

    public void execute()
    {
        java.lang.System.out.println("execute GWindowMenuItem: " + cap.caption);
    }

    public void mouseEnter()
    {
        super.mouseEnter();
        if(bEnable)
            menu().setSelected(this);
    }

    public void checkEnabling()
    {
    }

    public void windowShown()
    {
        checkEnabling();
        super.windowShown();
    }

    public void mouseButton(int i, boolean flag, float f, float f1)
    {
        super.mouseButton(i, flag, f, f1);
        if(!flag && i == 0 && bEnable && menu().selected == this)
            menu().doExecute(this);
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
            subMenu.setPos(win.dx, 0.0F);
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
        menu().keyboardKey(i, flag);
    }

    public void keyboardChar(char c)
    {
        menu().keyboardChar(c);
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
        if(subMenu != null)
            subMenu.resolutionChanged();
        getMinSize();
    }

    public void created()
    {
        super.created();
    }

    public GWindowMenuItem(com.maddox.gwindow.GWindowMenu gwindowmenu, java.lang.String s, java.lang.String s1)
    {
        bEnable = true;
        bChecked = false;
        cap = new GCaption(s);
        toolTip = s1;
        doNew(gwindowmenu, 0.0F, 0.0F, 1.0F, 1.0F, false);
    }

    public com.maddox.gwindow.GWindowMenu subMenu;
    public boolean bEnable;
    public boolean bChecked;
    public com.maddox.gwindow.GCaption cap;
    public java.lang.String toolTip;
}
