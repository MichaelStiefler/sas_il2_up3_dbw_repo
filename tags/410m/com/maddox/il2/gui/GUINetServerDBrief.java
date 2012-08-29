// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUINetServerDBrief.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.NetUserRegiment;
import com.maddox.il2.net.USGS;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.NetEnv;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.sound.AudioDevice;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.gui:
//            GUIBriefing, GUIAirArming, GUI, GUINetServer, 
//            GUIButton, GUIBriefingGeneric

public class GUINetServerDBrief extends com.maddox.il2.gui.GUIBriefing
{

    public void enter(com.maddox.il2.game.GameState gamestate)
    {
        super.enter(gamestate);
        if(gamestate != null && (gamestate.id() == 42 || gamestate.id() == 38) && briefSound != null)
        {
            java.lang.String s = com.maddox.il2.game.Main.cur().currentMissionFile.get("MAIN", "briefSound" + ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getArmy());
            if(s != null)
                briefSound = s;
            com.maddox.rts.CmdEnv.top().exec("music PUSH");
            com.maddox.rts.CmdEnv.top().exec("music LIST " + briefSound);
            com.maddox.rts.CmdEnv.top().exec("music PLAY");
        }
    }

    public void leave(com.maddox.il2.game.GameState gamestate)
    {
        if(gamestate != null && gamestate.id() == 42 && briefSound != null)
        {
            com.maddox.rts.CmdEnv.top().exec("music POP");
            com.maddox.rts.CmdEnv.top().exec("music STOP");
            briefSound = null;
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
        super.fillTextDescription();
        prepareTextDescription(com.maddox.il2.ai.Army.amountNet());
    }

    protected java.lang.String textDescription()
    {
        if(textArmyDescription == null)
            return null;
        com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
        int i = netuser.getBornPlace();
        if(i < 0)
        {
            return textArmyDescription[0];
        } else
        {
            com.maddox.il2.net.BornPlace bornplace = (com.maddox.il2.net.BornPlace)com.maddox.il2.ai.World.cur().bornPlaces.get(i);
            return textArmyDescription[bornplace.army];
        }
    }

    private boolean isValidBornPlace()
    {
        com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
        int i = netuser.getBornPlace();
        if(i < 0 || i >= com.maddox.il2.ai.World.cur().bornPlaces.size())
        {
            new GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("brief.BornPlace"), i18n("brief.BornPlaceSelect"), 3, 0.0F);
            return false;
        }
        int j = netuser.getAirdromeStay();
        if(j < 0)
        {
            new GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("brief.StayPlace"), i18n("brief.StayPlaceWait"), 3, 0.0F);
            return false;
        } else
        {
            return true;
        }
    }

    protected void doNext()
    {
        if(!isValidBornPlace())
            return;
        if(!isCarrierDeckFree((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()))
        {
            new GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("brief.CarrierDeckFull"), i18n("brief.CarrierDeckFullWait"), 3, 0.0F);
            return;
        }
        if(!isValidArming())
        {
            com.maddox.il2.gui.GUIAirArming.stateId = 2;
            com.maddox.il2.game.Main.stateStack().push(55);
            return;
        }
        com.maddox.il2.game.Main.cur().resetUser();
        com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
        int i = netuser.getBornPlace();
        int j = netuser.getAirdromeStay();
        com.maddox.il2.ai.UserCfg usercfg = com.maddox.il2.ai.World.cur().userCfg;
        netuser.checkReplicateSkin(usercfg.netAirName);
        netuser.checkReplicateNoseart(usercfg.netAirName);
        netuser.checkReplicatePilot();
        java.lang.String s;
        if(usercfg.netTacticalNumber < 10)
            s = "0" + usercfg.netTacticalNumber;
        else
            s = "" + usercfg.netTacticalNumber;
        com.maddox.rts.CmdEnv.top().exec("spawn " + ((java.lang.Class)com.maddox.rts.Property.value(usercfg.netAirName, "airClass", null)).getName() + " PLAYER NAME " + (((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).netUserRegiment.isEmpty() ? usercfg.netRegiment : "") + usercfg.netSquadron + "0" + s + " WEAPONS " + usercfg.getWeapon(usercfg.netAirName) + " BORNPLACE " + i + " STAYPLACE " + j + " FUEL " + usercfg.fuel + " OVR");
        com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
        if(!com.maddox.il2.engine.Actor.isValid(aircraft))
        {
            return;
        } else
        {
            com.maddox.il2.gui.GUI.unActivate();
            com.maddox.rts.HotKeyCmd.exec("aircraftView", "CockpitView");
            com.maddox.il2.objects.effects.ForceFeedback.startMission();
            com.maddox.sound.AudioDevice.soundsOn();
            com.maddox.il2.game.Main.stateStack().change(42);
            return;
        }
    }

    protected void doDiff()
    {
        com.maddox.il2.game.Main.stateStack().push(41);
    }

    protected void doLoodout()
    {
        if(!isValidBornPlace())
        {
            return;
        } else
        {
            com.maddox.il2.gui.GUIAirArming.stateId = 2;
            com.maddox.il2.game.Main.stateStack().push(55);
            return;
        }
    }

    protected void doBack()
    {
        com.maddox.il2.gui.GUINetServer.exitServer(true);
    }

    protected void clientRender()
    {
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient dialogclient = dialogClient;
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp = dialogclient;
        dialogclient.draw(dialogclient.x1024(15F), dialogclient.y1024(633F), dialogclient.x1024(140F), dialogclient.y1024(48F), 1, !com.maddox.il2.net.USGS.isUsed() && com.maddox.il2.game.Main.cur().netGameSpy == null ? i18n("brief.MainMenu") : i18n("main.Quit"));
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp1 = dialogclient;
        dialogclient.draw(dialogclient.x1024(194F), dialogclient.y1024(633F), dialogclient.x1024(208F), dialogclient.y1024(48F), 1, i18n("brief.Difficulty"));
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp2 = dialogclient;
        dialogclient.draw(dialogclient.x1024(680F), dialogclient.y1024(633F), dialogclient.x1024(176F), dialogclient.y1024(48F), 1, i18n("brief.Arming"));
        super.clientRender();
    }

    protected void clientSetPosSize()
    {
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient dialogclient = dialogClient;
        bLoodout.setPosC(dialogclient.x1024(768F), dialogclient.y1024(689F));
    }

    public GUINetServerDBrief(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(39);
        init(gwindowroot);
    }
}
