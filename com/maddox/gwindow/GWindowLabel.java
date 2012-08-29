// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowLabel.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindowDialogControl, GCaption, GWindowLookAndFeel, GSize, 
//            GRegion, GWindow

public class GWindowLabel extends com.maddox.gwindow.GWindowDialogControl
{

    public boolean isMousePassThrough(float f, float f1)
    {
        return true;
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

    public GWindowLabel(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2, float f3, java.lang.String s, java.lang.String s1)
    {
        cap = new GCaption(s);
        toolTip = s1;
        align = 0;
        doNew(gwindow, f, f1, f2, f3, true);
    }

    public GWindowLabel(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2, float f3, java.lang.String s, java.lang.String s1, 
            int i)
    {
        cap = new GCaption(s);
        toolTip = s1;
        align = i;
        doNew(gwindow, f, f1, f2, f3, true);
    }
}
