// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUINetDifficulty.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetServerParams;

// Referenced classes of package com.maddox.il2.gui:
//            GUIDifficulty, GUINetServerNGenSelect

public class GUINetDifficulty extends com.maddox.il2.gui.GUIDifficulty
{

    public void enterPush(com.maddox.il2.game.GameState gamestate)
    {
        if(com.maddox.il2.game.Main.cur().netServerParams.isMirror())
            bEnable = false;
        else
        if(gamestate.id() == 69)
            bEnable = com.maddox.il2.gui.GUINetServerNGenSelect.cur.missions == 0;
        else
            bEnable = gamestate.id() == 38;
        _enter();
    }

    public void _enter()
    {
        super._enter();
    }

    protected com.maddox.il2.ai.DifficultySettings settings()
    {
        return com.maddox.il2.ai.World.cur().diffCur;
    }

    public GUINetDifficulty(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(gwindowroot, 41);
    }
}
