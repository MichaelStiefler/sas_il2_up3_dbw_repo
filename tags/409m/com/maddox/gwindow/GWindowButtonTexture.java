// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowButtonTexture.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindowDialogControl, GWindowLookAndFeel, GTexRegion, GSize, 
//            GWindow

public class GWindowButtonTexture extends com.maddox.gwindow.GWindowDialogControl
{

    public void render()
    {
        lookAndFeel().render(this);
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GSize gsize)
    {
        gsize.set(texUP.dx, texUP.dy);
        return gsize;
    }

    public GWindowButtonTexture()
    {
        bStrech = true;
    }

    public GWindowButtonTexture(com.maddox.gwindow.GWindow gwindow)
    {
        super(gwindow);
        bStrech = true;
    }

    public boolean bStrech;
    public com.maddox.gwindow.GTexRegion texUP;
    public com.maddox.gwindow.GTexRegion texDOWN;
    public com.maddox.gwindow.GTexRegion texDISABLE;
    public com.maddox.gwindow.GTexRegion texOVER;
}
