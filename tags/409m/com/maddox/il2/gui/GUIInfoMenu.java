// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIInfoMenu.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GRegion;

// Referenced classes of package com.maddox.il2.gui:
//            GUIInfoTop

public class GUIInfoMenu extends com.maddox.il2.gui.GUIInfoTop
{

    public GUIInfoMenu()
    {
    }

    public void render()
    {
        super.render();
        setCanvasColor(com.maddox.gwindow.GColor.Gray);
        setCanvasFont(0);
        draw(M(2.0F), 0.0F, win.dx - M(2.0F), win.dy, 0, info);
    }

    public void setPosSize()
    {
        set1024PosSize(0.0F, 0.0F, 300F, 32F);
    }

    public java.lang.String info;
}
