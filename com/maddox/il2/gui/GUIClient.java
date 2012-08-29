// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIClient.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;

public class GUIClient extends com.maddox.gwindow.GWindow
{

    public GUIClient()
    {
    }

    public void setPosSize()
    {
        setPos(0.0F, 0.0F);
        setSize(parentWindow.win.dx, parentWindow.win.dy);
    }

    public void resolutionChanged()
    {
        setPosSize();
        super.resolutionChanged();
    }

    public void windowShown()
    {
        resolutionChanged();
        super.windowShown();
    }
}
