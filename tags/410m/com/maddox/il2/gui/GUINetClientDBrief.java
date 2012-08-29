// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUINetClientDBrief.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.AirportCarrier;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.NetUserRegiment;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.NetEnv;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.sound.AudioDevice;
import java.util.ArrayList;
import java.util.ResourceBundle;

// Referenced classes of package com.maddox.il2.gui:
//            GUIBriefing, GUINetClientGuard, GUIAirArming, GUI, 
//            GUIButton, GUIBriefingGeneric

public class GUINetClientDBrief extends com.maddox.il2.gui.GUIBriefing
{

    public void enter(com.maddox.il2.game.GameState gamestate)
    {
        super.enter(gamestate);
        if(gamestate != null && (gamestate.id() == 43 || gamestate.id() == 36) && briefSound != null)
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
        if(gamestate != null && (gamestate.id() == 36 || gamestate.id() == 43) && briefSound != null)
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
            prepareTextDescription(com.maddox.il2.ai.Army.amountNet());
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
        com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
        int i = netuser.getBornPlace();
        if(i < 0 || com.maddox.il2.ai.World.cur().bornPlaces == null)
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
            com.maddox.il2.gui.GUINetClientGuard guinetclientguard = (com.maddox.il2.gui.GUINetClientGuard)com.maddox.il2.game.Main.cur().netChannelListener;
            guinetclientguard.curMessageBox = new GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("brief.BornPlace"), i18n("brief.BornPlaceSelect"), 3, 0.0F);
            return false;
        }
        int j = netuser.getAirdromeStay();
        if(j < 0)
        {
            com.maddox.il2.gui.GUINetClientGuard guinetclientguard1 = (com.maddox.il2.gui.GUINetClientGuard)com.maddox.il2.game.Main.cur().netChannelListener;
            guinetclientguard1.curMessageBox = new GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("brief.StayPlace"), i18n("brief.StayPlaceWait"), 3, 0.0F);
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
        if(!isValidArming())
        {
            com.maddox.il2.gui.GUIAirArming.stateId = 2;
            com.maddox.il2.game.Main.stateStack().push(55);
            return;
        }
        com.maddox.il2.ai.AirportCarrier airportcarrier = getCarrier((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host());
        com.maddox.il2.net.BornPlace bornplace = (com.maddox.il2.net.BornPlace)com.maddox.il2.ai.World.cur().bornPlaces.get(((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getBornPlace());
        if(airportcarrier != null && !bornplace.zutiAirspawnOnly && com.maddox.il2.ai.World.cur().diffCur.Takeoff_N_Landing)
        {
            if(!com.maddox.il2.net.NetServerParams.isSynched() && !airportcarrier.ship().zutiIsStatic())
            {
                com.maddox.il2.gui.GUINetClientGuard guinetclientguard = (com.maddox.il2.gui.GUINetClientGuard)com.maddox.il2.game.Main.cur().netChannelListener;
                guinetclientguard.curMessageBox = new GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("brief.StayPlace"), i18n("brief.StayPlaceWait"), 3, 0.0F);
                return;
            }
            com.maddox.il2.ai.UserCfg usercfg = com.maddox.il2.ai.World.cur().userCfg;
            java.lang.Class class1 = (java.lang.Class)com.maddox.rts.Property.value(usercfg.netAirName, "airClass", null);
            airportcarrier.setGuiCallback(this);
            airportcarrier.ship().requestLocationOnCarrierDeck((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host(), class1.getName());
            com.maddox.il2.gui.GUINetClientGuard guinetclientguard1 = (com.maddox.il2.gui.GUINetClientGuard)com.maddox.il2.game.Main.cur().netChannelListener;
            guinetclientguard1.curMessageBox = new GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("miss.ReFlyWait"), i18n("miss.ReFlyWait"), 4, 30F);
        } else
        {
            fly();
        }
    }

    public void flyFromCarrier(boolean flag)
    {
        com.maddox.il2.gui.GUINetClientGuard guinetclientguard = (com.maddox.il2.gui.GUINetClientGuard)com.maddox.il2.game.Main.cur().netChannelListener;
        guinetclientguard.curMessageBox.close(false);
        com.maddox.il2.net.BornPlace bornplace = (com.maddox.il2.net.BornPlace)com.maddox.il2.ai.World.cur().bornPlaces.get(((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getBornPlace());
        if(flag || bornplace.zutiAirspawnIfCarrierFull)
            fly();
        else
            guinetclientguard.curMessageBox = new GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("brief.CarrierDeckFull"), i18n("brief.CarrierDeckFullWait"), 3, 0.0F);
    }

    protected void fly()
    {
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
            com.maddox.il2.game.Main.stateStack().change(43);
            return;
        }
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

    protected void doDiff()
    {
        com.maddox.il2.game.Main.stateStack().push(41);
    }

    protected void doBack()
    {
        com.maddox.il2.gui.GUINetClientGuard guinetclientguard = (com.maddox.il2.gui.GUINetClientGuard)com.maddox.il2.game.Main.cur().netChannelListener;
        if(guinetclientguard == null)
        {
            return;
        } else
        {
            guinetclientguard.dlgDestroy(new com.maddox.il2.gui.GUINetClientGuard.DestroyExec() {

                public void destroy(com.maddox.il2.gui.GUINetClientGuard guinetclientguard1)
                {
                    guinetclientguard1.destroy(true);
                }

            }
);
            return;
        }
    }

    protected void clientRender()
    {
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient dialogclient = dialogClient;
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp = dialogclient;
        dialogclient.draw(dialogclient.x1024(5F), dialogclient.y1024(633F), dialogclient.x1024(160F), dialogclient.y1024(48F), 1, i18n("brief.Disconnect"));
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

    public GUINetClientDBrief(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(40);
        init(gwindowroot);
    }
}
