// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowRootMenu.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindowRoot, GWindowStatusBar, GWindowMenuBar, GRegion, 
//            GSize, GWindow

public class GWindowRootMenu extends com.maddox.gwindow.GWindowRoot
{

    public GWindowRootMenu()
    {
    }

    public void toolTip(java.lang.String s)
    {
        statusBar.setHelp(s);
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GRegion gregion, float f)
    {
        gregion.x = f;
        if(menuBar.isVisible())
            gregion.y = menuBar.win.dy + f;
        else
            gregion.y = f;
        gregion.dx = win.dx - 2.0F * f;
        gregion.dy = win.dy - 2.0F * f;
        if(menuBar.isVisible())
            gregion.dy -= menuBar.win.dy;
        if(statusBar.isVisible())
            gregion.dy -= statusBar.win.dy;
        return gregion;
    }

    public void resized()
    {
        super.resized();
        float f = menuBar.getMinSize().dy;
        menuBar.setPos(0.0F, 0.0F);
        menuBar.setSize(win.dx, f);
        f = statusBar.getMinSize().dy;
        statusBar.setPos(0.0F, win.dy - f);
        statusBar.setSize(win.dx, f);
        if(clientWindow != null)
            clientWindow.resized();
    }

    public void created()
    {
        super.created();
        statusBar = (com.maddox.gwindow.GWindowStatusBar)create(new GWindowStatusBar());
        menuBar = (com.maddox.gwindow.GWindowMenuBar)create(new GWindowMenuBar());
        resized();
    }

    public com.maddox.gwindow.GWindowStatusBar statusBar;
    public com.maddox.gwindow.GWindowMenuBar menuBar;
    public com.maddox.gwindow.GWindow clientWindow;
}
