// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowClient.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindow, GSize, GWindowLookAndFeel

public class GWindowClient extends com.maddox.gwindow.GWindow
{

    public GWindowClient()
    {
    }

    public void close(boolean flag)
    {
        if(!flag)
            parentWindow.close(flag);
        super.close(flag);
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GSize gsize)
    {
        gsize.dx = 50F;
        gsize.dy = 50F;
        return gsize;
    }

    public void render()
    {
        lookAndFeel().render(this);
    }
}
