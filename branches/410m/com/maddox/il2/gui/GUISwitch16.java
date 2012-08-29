// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUISwitch16.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.il2.engine.Config;
import com.maddox.rts.CfgInt;

// Referenced classes of package com.maddox.il2.gui:
//            GUISwitchN

public class GUISwitch16 extends com.maddox.il2.gui.GUISwitchN
{

    private static void init()
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(bInited)
                return;
            com.maddox.gwindow.GTexture gtexture = com.maddox.gwindow.GTexture.New("GUI/game/switches3.mat");
            int i = 0;
            for(int j = 0; j < 4; j++)
            {
                for(int k = 0; k < 4; k++)
                    st[i++] = new GTexRegion(gtexture, k * 64, j * 64, 64F, 64F);

            }

            bInited = true;
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

    public GUISwitch16(com.maddox.gwindow.GWindow gwindow, int ai[], boolean aflag[])
    {
        super(gwindow, 135F, 18F, ai, aflag);
        com.maddox.il2.gui.GUISwitch16.init();
    }

    public GUISwitch16(com.maddox.gwindow.GWindow gwindow, int ai[], com.maddox.rts.CfgInt cfgint, boolean flag)
    {
        super(gwindow, 135F, 18F, ai, cfgint, flag);
        com.maddox.il2.gui.GUISwitch16.init();
    }

    static com.maddox.gwindow.GTexRegion st[] = new com.maddox.gwindow.GTexRegion[16];
    private static boolean bInited = false;

}
