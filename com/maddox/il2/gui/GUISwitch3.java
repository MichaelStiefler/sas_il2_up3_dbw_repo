// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUISwitch3.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.il2.engine.Config;
import com.maddox.rts.CfgInt;

// Referenced classes of package com.maddox.il2.gui:
//            GUISwitchN

public class GUISwitch3 extends com.maddox.il2.gui.GUISwitchN
{

    private static void init()
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(st[0] != null)
                return;
            com.maddox.gwindow.GTexture gtexture = com.maddox.gwindow.GTexture.New("GUI/game/buttons.mat");
            st[0] = new GTexRegion(gtexture, 0.0F, 176F, 48F, 80F);
            st[1] = new GTexRegion(gtexture, 48F, 176F, 48F, 80F);
            st[2] = new GTexRegion(gtexture, 96F, 176F, 48F, 80F);
        }
    }

    public void mouseButton(int i, boolean flag, float f, float f1)
    {
        if(i != 0 || !flag || !bEnable)
            return;
        int j = state;
        if(states == 2)
            j = (j + 1) % 2;
        else
        if(f1 > win.dy / 2.0F)
        {
            j = state - 1;
            if(j < 0)
                j = state;
        } else
        {
            j = state + 1;
            if(j >= states)
                j = state;
        }
        setState(j, true);
    }

    public void render()
    {
        setCanvasColorWHITE();
        draw(0.0F, 0.0F, win.dx, win.dy, st[pos[state]]);
    }

    public void created()
    {
        texDx = 48F;
        texDy = 80F;
        resolutionChanged();
    }

    public GUISwitch3(com.maddox.gwindow.GWindow gwindow, int ai[], boolean aflag[])
    {
        super(gwindow, 0.0F, 30F, ai, aflag);
        com.maddox.il2.gui.GUISwitch3.init();
    }

    public GUISwitch3(com.maddox.gwindow.GWindow gwindow, int ai[], com.maddox.rts.CfgInt cfgint, boolean flag)
    {
        super(gwindow, 0.0F, 30F, ai, cfgint, flag);
        com.maddox.il2.gui.GUISwitch3.init();
    }

    static com.maddox.gwindow.GTexRegion st[] = new com.maddox.gwindow.GTexRegion[3];

}
