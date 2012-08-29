// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUINetClientCBrief.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetEnv;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import java.util.ResourceBundle;

// Referenced classes of package com.maddox.il2.gui:
//            GUIBriefing, GUINetClientGuard, GUINetAircraft, GUIButton, 
//            GUIBriefingGeneric

public class GUINetClientCBrief extends com.maddox.il2.gui.GUIBriefing
{

    public void enter(com.maddox.il2.game.GameState gamestate)
    {
        super.enter(gamestate);
        if(gamestate != null && briefSound != null)
            if(gamestate.id() == 36)
            {
                com.maddox.rts.CmdEnv.top().exec("music PUSH");
                com.maddox.rts.CmdEnv.top().exec("music LIST " + briefSound);
                com.maddox.rts.CmdEnv.top().exec("music PLAY");
                _briefSound = briefSound;
            } else
            if(gamestate.id() == 44)
            {
                java.lang.String s = com.maddox.il2.game.Main.cur().currentMissionFile.get("MAIN", "briefSound" + ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getArmy());
                if(s == null)
                    s = com.maddox.il2.game.Main.cur().currentMissionFile.get("MAIN", "briefSound");
                if(s != null && !s.equals(_briefSound))
                {
                    _briefSound = s;
                    com.maddox.rts.CmdEnv.top().exec("music LIST " + _briefSound);
                }
            }
    }

    public void leave(com.maddox.il2.game.GameState gamestate)
    {
        if(gamestate != null && gamestate.id() == 48 && briefSound != null)
        {
            com.maddox.rts.CmdEnv.top().exec("music POP");
            com.maddox.rts.CmdEnv.top().exec("music STOP");
            briefSound = null;
            _briefSound = null;
        }
        super.leave(gamestate);
    }

    public void leavePop(com.maddox.il2.game.GameState gamestate)
    {
        if(gamestate != null && gamestate.id() == 2 && briefSound != null)
        {
            com.maddox.rts.CmdEnv.top().exec("music POP");
            com.maddox.rts.CmdEnv.top().exec("music PLAY");
        }
        super.leavePop(gamestate);
    }

    protected void fillTextDescription()
    {
    }

    public boolean isExistTextDescription()
    {
        return textDescription != null;
    }

    public void clearTextDescription()
    {
        textDescription = null;
    }

    public void setTextDescription(java.lang.String s)
    {
        try
        {
            java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle(s, com.maddox.rts.RTSConf.cur.locale);
            textDescription = resourcebundle.getString("Description");
            prepareTextDescription(com.maddox.il2.ai.Army.amountSingle());
        }
        catch(java.lang.Exception exception)
        {
            textDescription = null;
            textArmyDescription = null;
        }
        wScrollDescription.resized();
    }

    protected java.lang.String textDescription()
    {
        if(textArmyDescription == null)
            return null;
        if(com.maddox.il2.gui.GUINetAircraft.isSelectedValid())
            return textArmyDescription[com.maddox.il2.gui.GUINetAircraft.selectedRegiment().getArmy()];
        else
            return textArmyDescription[0];
    }

    private boolean isValidAircraft()
    {
        return false;
    }

    protected void doNext()
    {
        if(!com.maddox.il2.gui.GUINetAircraft.isSelectedValid())
        {
            com.maddox.il2.game.Main.stateStack().change(44);
            return;
        } else
        {
            com.maddox.il2.gui.GUINetAircraft.doFly();
            return;
        }
    }

    protected void doLoodout()
    {
        com.maddox.il2.game.Main.stateStack().change(44);
    }

    protected void doDiff()
    {
        com.maddox.il2.game.Main.stateStack().push(41);
    }

    protected void doBack()
    {
        com.maddox.il2.gui.GUINetClientGuard guinetclientguard = (com.maddox.il2.gui.GUINetClientGuard)com.maddox.il2.game.Main.cur().netChannelListener;
        guinetclientguard.dlgDestroy(new com.maddox.il2.gui.GUINetClientGuard.DestroyExec() {

            public void destroy(com.maddox.il2.gui.GUINetClientGuard guinetclientguard1)
            {
                guinetclientguard1.destroy(true);
            }

        }
);
    }

    protected void clientRender()
    {
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient dialogclient = dialogClient;
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp = dialogclient;
        dialogclient.draw(dialogclient.x1024(144F), dialogclient.y1024(656F), dialogclient.x1024(160F), dialogclient.y1024(48F), 0, i18n("brief.Disconnect"));
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp1 = dialogclient;
        dialogclient.draw(dialogclient.x1024(256F), dialogclient.y1024(656F), dialogclient.x1024(208F), dialogclient.y1024(48F), 2, i18n("brief.Difficulty"));
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp2 = dialogclient;
        dialogclient.draw(dialogclient.x1024(528F), dialogclient.y1024(656F), dialogclient.x1024(176F), dialogclient.y1024(48F), 2, i18n("brief.Aircraft"));
        super.clientRender();
    }

    protected void clientSetPosSize()
    {
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient dialogclient = dialogClient;
        bLoodout.setPosC(dialogclient.x1024(742F), dialogclient.y1024(680F));
    }

    public GUINetClientCBrief(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(46);
        _briefSound = null;
        init(gwindowroot);
    }

    private java.lang.String _briefSound;
}
