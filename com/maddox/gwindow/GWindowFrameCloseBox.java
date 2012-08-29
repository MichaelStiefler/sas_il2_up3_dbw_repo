// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowFrameCloseBox.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindowButtonTexture, GWindowLookAndFeel, GWindow

public class GWindowFrameCloseBox extends com.maddox.gwindow.GWindowButtonTexture
{

    public void created()
    {
        lookAndFeel().setupFrameCloseBox(this);
        toolTip = lAF().i18n("Close_window");
    }

    public void mouseClick(int i, float f, float f1)
    {
        if(i == 0)
            parentWindow.close(false);
    }

    public GWindowFrameCloseBox()
    {
        bNotify = false;
        bAcceptsKeyFocus = false;
        bTransient = true;
    }
}
