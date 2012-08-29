// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUISwitch7.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.il2.engine.Config;
import com.maddox.rts.CfgInt;

// Referenced classes of package com.maddox.il2.gui:
//            GUISwitchN

public class GUISwitch7 extends com.maddox.il2.gui.GUISwitchN
{

    private static void init()
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(st[0] != null)
                return;
            com.maddox.gwindow.GTexture gtexture = com.maddox.gwindow.GTexture.New("GUI/game/switches1.mat");
            for(int i = 0; i < 4; i++)
                st[i] = new GTexRegion(gtexture, i * 64, 0.0F, 64F, 64F);

            for(int j = 0; j < 3; j++)
                st[j + 4] = new GTexRegion(gtexture, j * 64, 64F, 64F, 64F);

        }
    }

    public void render()
    {
        setCanvasColorWHITE();
        draw(0.0F, 0.0F, win.dx, win.dy, st[pos[state]]);
    }

    public void created()
    {
        texDx = 64F;
        texDy = 64F;
        resolutionChanged();
    }

    public GUISwitch7(com.maddox.gwindow.GWindow gwindow, int ai[], boolean aflag[])
    {
        super(gwindow, 180F, 30F, ai, aflag);
        com.maddox.il2.gui.GUISwitch7.init();
    }

    public GUISwitch7(com.maddox.gwindow.GWindow gwindow, int ai[], com.maddox.rts.CfgInt cfgint, boolean flag)
    {
        super(gwindow, 180F, 30F, ai, cfgint, flag);
        com.maddox.il2.gui.GUISwitch7.init();
    }

    static com.maddox.gwindow.GTexRegion st[] = new com.maddox.gwindow.GTexRegion[7];

}
