// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUISingleMission.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.Front;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.rts.CmdEnv;

// Referenced classes of package com.maddox.il2.gui:
//            GUIMission, GUIInfoMenu

public class GUISingleMission extends com.maddox.il2.gui.GUIMission
{

    protected void doExit()
    {
        com.maddox.il2.ai.Front.checkAllCaptured();
        com.maddox.rts.CmdEnv.top().exec("mission END");
        com.maddox.il2.game.Main.stateStack().change(6);
    }

    public GUISingleMission(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(5);
        init(gwindowroot);
        infoMenu.info = i18n("miss.SingleInfo");
    }
}
