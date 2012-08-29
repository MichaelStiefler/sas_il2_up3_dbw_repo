// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIRootMenu.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindowRootMenu;
import com.maddox.il2.game.Mission;

public class GUIRootMenu extends com.maddox.gwindow.GWindowRootMenu
{

    public GUIRootMenu()
    {
    }

    public void render()
    {
        if(com.maddox.il2.game.Mission.cur() == null || com.maddox.il2.game.Mission.cur().isDestroyed())
        {
            setCanvasColorWHITE();
            draw(0.0F, 0.0F, win.dx, win.dy, background);
        }
    }

    public void created()
    {
        background = com.maddox.gwindow.GTexture.New("GUI/background.mat");
        super.created();
    }

    com.maddox.gwindow.GTexture background;
}
