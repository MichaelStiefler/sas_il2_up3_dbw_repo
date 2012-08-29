// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowStatusBar.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindow, GWindowLookAndFeel, GSize, GRegion

public class GWindowStatusBar extends com.maddox.gwindow.GWindow
{

    public GWindowStatusBar()
    {
        defaultHelp = "";
    }

    public void setHelp(java.lang.String s)
    {
        help = s;
    }

    public void setDefaultHelp(java.lang.String s)
    {
        defaultHelp = s;
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

    public void created()
    {
        super.created();
        bAlwaysOnTop = true;
        bTransient = true;
        defaultHelp = lAF().i18n("Press_ESC_to_return_to_the_game");
    }

    public java.lang.String help;
    public java.lang.String defaultHelp;
}
