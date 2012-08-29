// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowEditControl.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindowEditBox, GWindowLookAndFeel, GWindow

public class GWindowEditControl extends com.maddox.gwindow.GWindowEditBox
{

    public void render()
    {
        lookAndFeel().render(this);
        checkCaretTimeout();
    }

    public GWindowEditControl()
    {
    }

    public GWindowEditControl(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2, float f3, java.lang.String s)
    {
        toolTip = s;
        align = 0;
        doNew(gwindow, f, f1, f2, f3, true);
    }
}
