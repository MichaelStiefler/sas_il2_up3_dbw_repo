// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUINetMission.java

package com.maddox.il2.gui;

import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.Front;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.Paratrooper;
import com.maddox.rts.NetEnv;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUI, GUIDialogClient, GUISeparate

public class GUINetMission extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bVideo)
            {
                com.maddox.il2.game.Main.stateStack().push(12);
                return true;
            }
            if(gwindow == b3d)
            {
                com.maddox.il2.game.Main.stateStack().push(11);
                return true;
            }
            if(gwindow == bSound)
            {
                com.maddox.il2.game.Main.stateStack().push(13);
                return true;
            }
            if(gwindow == bControls)
            {
                com.maddox.il2.game.Main.stateStack().push(20);
                return true;
            }
            if(gwindow == bNet)
            {
                com.maddox.il2.game.Main.stateStack().push(52);
                return true;
            }
            if(gwindow == bReFly)
            {
                doReFly();
                return true;
            }
            if(gwindow == bExit)
            {
                doExit();
                return true;
            }
            if(gwindow == bBack)
            {
                client.hideWindow();
                com.maddox.il2.gui.GUI.unActivate();
                return true;
            }
            if(gwindow == bTrack)
            {
                if(com.maddox.il2.net.NetMissionTrack.isRecording())
                {
                    com.maddox.il2.net.NetMissionTrack.stopRecording();
                    client.hideWindow();
                    com.maddox.il2.gui.GUI.unActivate();
                } else
                {
                    com.maddox.il2.game.Main.stateStack().push(59);
                }
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(368F), x1024(288F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(544F), x1024(288F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(96F), y1024(32F), x1024(224F), y1024(48F), 0, i18n("miss.VideoModes"));
            draw(x1024(96F), y1024(96F), x1024(224F), y1024(48F), 0, i18n("miss.VideoOptions"));
            draw(x1024(96F), y1024(160F), x1024(224F), y1024(48F), 0, i18n("miss.SoundSetup"));
            draw(x1024(96F), y1024(224F), x1024(224F), y1024(48F), 0, i18n("miss.Controls"));
            draw(x1024(96F), y1024(288F), x1024(224F), y1024(48F), 0, i18n("miss.Network"));
            draw(x1024(96F), y1024(576F), x1024(224F), y1024(48F), 0, i18n("miss.Return2Miss"));
            if(com.maddox.il2.net.NetMissionTrack.isRecording())
                draw(x1024(96F), y1024(640F), x1024(224F), y1024(48F), 0, i18n("miss.StopRecording"));
            else
                draw(x1024(96F), y1024(640F), x1024(224F), y1024(48F), 0, i18n("miss.StartRecording"));
            clientRender();
        }

        public void setPosSize()
        {
            set1024PosSize(352F, 32F, 352F, 720F);
            bVideo.setPosC(x1024(56F), y1024(56F));
            b3d.setPosC(x1024(56F), y1024(120F));
            bSound.setPosC(x1024(56F), y1024(184F));
            bControls.setPosC(x1024(56F), y1024(248F));
            bNet.setPosC(x1024(56F), y1024(312F));
            bReFly.setPosC(x1024(56F), y1024(424F));
            bExit.setPosC(x1024(56F), y1024(488F));
            bBack.setPosC(x1024(56F), y1024(600F));
            bTrack.setPosC(x1024(56F), y1024(666F));
        }

        public DialogClient()
        {
        }
    }


    public static boolean isEnableReFly()
    {
        if(com.maddox.il2.game.Main.cur().netServerParams.isCoop())
            return false;
        if(!com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.getPlayerAircraft()))
            return true;
        if(!com.maddox.il2.ai.World.getPlayerAircraft().isAlive())
            return true;
        if(!com.maddox.il2.ai.World.cur().diffCur.Takeoff_N_Landing)
            return true;
        if(com.maddox.il2.ai.World.getPlayerFM().Gears.onGround() && com.maddox.il2.ai.World.getPlayerAircraft().getSpeed(null) < 1.0D)
            return true;
        if(com.maddox.il2.ai.World.isPlayerDead())
            return true;
        return com.maddox.il2.ai.World.isPlayerParatrooper();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    public void enterPop(com.maddox.il2.game.GameState gamestate)
    {
        if(gamestate.id() == 59)
        {
            client.hideWindow();
            com.maddox.il2.gui.GUI.unActivate();
        } else
        {
            client.activateWindow();
        }
    }

    protected void checkCaptured()
    {
        if(com.maddox.il2.ai.World.isPlayerDead())
            return;
        if(com.maddox.il2.ai.World.isPlayerParatrooper())
        {
            if(com.maddox.il2.engine.Actor.isAlive(com.maddox.il2.engine.Actor.getByName("_paraplayer_")))
            {
                com.maddox.il2.objects.air.Paratrooper paratrooper = (com.maddox.il2.objects.air.Paratrooper)com.maddox.il2.engine.Actor.getByName("_paraplayer_");
                if(!paratrooper.isChecksCaptured())
                    paratrooper.checkCaptured();
            }
        } else
        if(com.maddox.il2.engine.Actor.isAlive(com.maddox.il2.ai.World.getPlayerAircraft()))
        {
            com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
            if(!aircraft.FM.isOk() && com.maddox.il2.ai.Front.isCaptured(aircraft))
            {
                com.maddox.il2.ai.World.setPlayerCaptured();
                if(com.maddox.il2.engine.Config.isUSE_RENDER())
                    com.maddox.il2.game.HUD.log("PlayerCAPT");
                com.maddox.il2.net.Chat.sendLog(1, "gore_captured", (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host(), null);
            }
        }
    }

    protected void destroyPlayerActor()
    {
        if(com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.getPlayerAircraft()) && !com.maddox.il2.ai.World.isPlayerGunner())
        {
            if(com.maddox.il2.ai.World.getPlayerAircraft().isAlive())
            {
                com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
                java.lang.Object obj = com.maddox.il2.ai.World.getPlayerAircraft();
                com.maddox.il2.fm.FlightModel flightmodel = com.maddox.il2.ai.World.getPlayerAircraft().FM;
                boolean flag;
                for(flag = false; flightmodel.isWasAirborne();)
                {
                    if(flightmodel.isStationedOnGround())
                    {
                        if(flightmodel.isNearAirdrome())
                        {
                            if(flightmodel.isTakenMortalDamage())
                                com.maddox.il2.net.Chat.sendLogRnd(2, "gore_crashland", aircraft, null);
                            else
                                com.maddox.il2.net.Chat.sendLogRnd(2, "gore_lands", aircraft, null);
                        } else
                        {
                            obj = aircraft.getDamager();
                            flag = true;
                            if(!com.maddox.il2.ai.World.isPlayerParatrooper())
                                if(com.maddox.il2.engine.Engine.cur.land.isWater(flightmodel.Loc.x, flightmodel.Loc.y))
                                    com.maddox.il2.net.Chat.sendLogRnd(2, "gore_swim", aircraft, null);
                                else
                                    com.maddox.il2.net.Chat.sendLogRnd(2, "gore_ditch", aircraft, null);
                        }
                    } else
                    if(flightmodel.isTakenMortalDamage() || !flightmodel.isCapableOfBMP())
                    {
                        obj = aircraft.getDamager();
                        flag = true;
                    }
                    break;
                }

                if(!com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Actor) (obj))))
                    obj = com.maddox.il2.ai.World.getPlayerAircraft();
                com.maddox.il2.ai.World.onActorDied(com.maddox.il2.ai.World.getPlayerAircraft(), ((com.maddox.il2.engine.Actor) (obj)), flag);
            }
            com.maddox.il2.engine.Actor.destroy(com.maddox.il2.ai.World.getPlayerAircraft());
        }
        if(com.maddox.il2.engine.Actor.isAlive(com.maddox.il2.engine.Actor.getByName("_paraplayer_")))
            com.maddox.il2.engine.Actor.getByName("_paraplayer_").setName(null);
    }

    public void doQuitMission()
    {
        com.maddox.il2.gui.GUI.activate();
        client.activateWindow();
        bEnableReFly = com.maddox.il2.gui.GUINetMission.isEnableReFly();
        if(bEnableReFly)
            bReFly.showWindow();
        else
            bReFly.hideWindow();
    }

    protected void doReFly()
    {
    }

    protected void doExit()
    {
    }

    protected void clientRender()
    {
    }

    protected void init(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = "";
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bVideo = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        b3d = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bSound = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bControls = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bNet = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bReFly = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bBack = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 192F, 48F, 48F));
        bTrack = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public GUINetMission(int i)
    {
        super(i);
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton bVideo;
    public com.maddox.il2.gui.GUIButton b3d;
    public com.maddox.il2.gui.GUIButton bSound;
    public com.maddox.il2.gui.GUIButton bControls;
    public com.maddox.il2.gui.GUIButton bNet;
    public com.maddox.il2.gui.GUIButton bReFly;
    public com.maddox.il2.gui.GUIButton bExit;
    public com.maddox.il2.gui.GUIButton bBack;
    public com.maddox.il2.gui.GUIButton bTrack;
    protected boolean bEnableReFly;
}
