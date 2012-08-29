// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUISwitch8.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.il2.engine.Config;

// Referenced classes of package com.maddox.il2.gui:
//            GUISwitchN

public class GUISwitch8 extends com.maddox.il2.gui.GUISwitchN
{

    private static void init()
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(st[0] != null)
                return;
            com.maddox.gwindow.GTexture gtexture = com.maddox.gwindow.GTexture.New("GUI/game/switches1.mat");
            for(int i = 0; i < 4; i++)
                st[i] = new GTexRegion(gtexture, i * 48, 160F, 48F, 48F);

            for(int j = 0; j < 4; j++)
                st[j + 4] = new GTexRegion(gtexture, j * 48, 208F, 48F, 48F);

        }
    }

    public void render()
    {
        setCanvasColorWHITE();
        draw(0.0F, 0.0F, win.dx, win.dy, st[pos[state]]);
    }

    public void created()
    {
        texDx = 48F;
        texDy = 48F;
        resolutionChanged();
    }

    public GUISwitch8(com.maddox.gwindow.GWindow gwindow, int ai[], boolean aflag[])
    {
        super(gwindow, 0.0F, -45F, ai, aflag);
        com.maddox.il2.gui.GUISwitch8.init();
    }

    static com.maddox.gwindow.GTexRegion st[] = new com.maddox.gwindow.GTexRegion[8];

}
