// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowButton.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindowDialogControl, GCaption, GWindowLookAndFeel, GWindow, 
//            GSize, GRegion

public class GWindowButton extends com.maddox.gwindow.GWindowDialogControl
{

    public void render()
    {
        if(!_bNoRender)
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

    public GWindowButton(com.maddox.gwindow.GWindow gwindow)
    {
        bDrawOnlyUP = false;
        bDrawActive = true;
        _bNoRender = false;
        doNew(gwindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
    }

    public GWindowButton(com.maddox.gwindow.GWindow gwindow, java.lang.String s, java.lang.String s1)
    {
        bDrawOnlyUP = false;
        bDrawActive = true;
        _bNoRender = false;
        cap = new GCaption(s);
        toolTip = s1;
        align = 1;
        doNew(gwindow, 0.0F, 0.0F, 50F, 2.0F * gwindow.lookAndFeel().metric(), false);
    }

    public GWindowButton(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2, float f3, java.lang.String s, java.lang.String s1)
    {
        bDrawOnlyUP = false;
        bDrawActive = true;
        _bNoRender = false;
        cap = new GCaption(s);
        toolTip = s1;
        align = 1;
        doNew(gwindow, f, f1, f2, f3, true);
    }

    public boolean bDrawOnlyUP;
    public boolean bDrawActive;
    public boolean _bNoRender;
}
