// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIInfoName.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GRegion;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;

// Referenced classes of package com.maddox.il2.gui:
//            GUIInfoTop

public class GUIInfoName extends com.maddox.il2.gui.GUIInfoTop
{

    public GUIInfoName()
    {
    }

    public void render()
    {
        super.render();
        setCanvasColor(com.maddox.gwindow.GColor.Gray);
        setCanvasFont(0);
        com.maddox.il2.ai.UserCfg usercfg = com.maddox.il2.ai.World.cur().userCfg;
        if(nickName != null)
            draw(0.0F, 0.0F, win.dx - M(2.0F), win.dy, 2, nickName);
        else
            draw(0.0F, 0.0F, win.dx - M(2.0F), win.dy, 2, usercfg.name + " '" + usercfg.callsign + "' " + usercfg.surname);
    }

    public void setPosSize()
    {
        set1024PosSize(300F, 0.0F, 724F, 32F);
    }

    public static java.lang.String nickName = null;

}
