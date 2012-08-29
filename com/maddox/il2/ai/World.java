// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   World.java

package com.maddox.il2.ai;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.air.Airdrome;
import com.maddox.il2.ai.air.NearestAircraft;
import com.maddox.il2.ai.ground.NearestEnemies;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorException;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.engine.hotkey.HookView;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Wind;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiAircraft;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.game.ZutiTimer_Refly;
import com.maddox.il2.game.order.OrdersTree;
import com.maddox.il2.gui.GUINetMission;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.ActorViewPoint;
import com.maddox.il2.objects.ScoreRegister;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.air.NetGunner;
import com.maddox.il2.objects.air.Paratrooper;
import com.maddox.il2.objects.air.Runaway;
import com.maddox.il2.objects.buildings.HouseManager;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.vehicles.planes.PlaneGeneric;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnvs;
import com.maddox.rts.IniFile;
import com.maddox.rts.NetEnv;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.il2.ai:
//            Wing, Airport, ChiefManager, RangeRandom, 
//            DifficultySettings, TargetsGuard, ScoreCounter, Front, 
//            War, UserCfg, Regiment, EventLog, 
//            MsgExplosion, MsgShot

public class World
{
    static class ClipFilter
        implements com.maddox.il2.engine.ActorFilter
    {

        public boolean isUse(com.maddox.il2.engine.Actor actor, double d)
        {
            return actor instanceof com.maddox.il2.objects.ships.BigshipGeneric;
        }

        ClipFilter()
        {
        }
    }

    static class Remover extends com.maddox.il2.engine.Actor
    {

        protected void createActorHashCode()
        {
            makeActorRealHashCode();
        }

        Remover()
        {
        }
    }


    public static com.maddox.il2.ai.RangeRandom Rnd()
    {
        return com.maddox.il2.ai.World.cur().rnd;
    }

    public void setCamouflage(java.lang.String s)
    {
        if("SUMMER".equalsIgnoreCase(s))
            camouflage = 0;
        else
        if("WINTER".equalsIgnoreCase(s))
            camouflage = 1;
        else
        if("DESERT".equalsIgnoreCase(s))
            camouflage = 2;
        else
        if("PACIFIC".equalsIgnoreCase(s))
            camouflage = 3;
        else
        if("ETO".equalsIgnoreCase(s))
            camouflage = 4;
        else
        if("MTO".equalsIgnoreCase(s))
            camouflage = 5;
        else
        if("CBI".equalsIgnoreCase(s))
            camouflage = 6;
        else
            camouflage = 0;
    }

    public void setUserCovers()
    {
        userCoverMashineGun = userCfg.coverMashineGun;
        userCoverCannon = userCfg.coverCannon;
        userCoverRocket = userCfg.coverRocket;
        userRocketDelay = userCfg.rocketDelay;
        userBombDelay = userCfg.bombDelay;
    }

    public boolean isArcade()
    {
        return com.maddox.il2.game.Mission.isSingle() && bArcade && !com.maddox.il2.net.NetMissionTrack.isPlaying();
    }

    public void setArcade(boolean flag)
    {
        bArcade = flag;
    }

    public boolean isHighGore()
    {
        return bHighGore;
    }

    public boolean isHakenAllowed()
    {
        return bHakenAllowed;
    }

    public boolean isDebugFM()
    {
        return bDebugFM;
    }

    public boolean isTimeOfDayConstant()
    {
        return bTimeOfDayConstant;
    }

    public void setTimeOfDayConstant(boolean flag)
    {
        bTimeOfDayConstant = flag;
    }

    public boolean isWeaponsConstant()
    {
        return bWeaponsConstant;
    }

    public void setWeaponsConstant(boolean flag)
    {
        bWeaponsConstant = flag;
    }

    public static void getAirports(java.util.List list)
    {
        if(com.maddox.il2.ai.World.cur().airports != null)
            list.addAll(com.maddox.il2.ai.World.cur().airports);
    }

    public static int getMissionArmy()
    {
        return com.maddox.il2.ai.World.cur().missionArmy;
    }

    public static void setMissionArmy(int i)
    {
        com.maddox.il2.ai.World.cur().missionArmy = i;
    }

    public static com.maddox.il2.objects.air.Aircraft getPlayerAircraft()
    {
        return com.maddox.il2.ai.World.cur().PlayerAircraft;
    }

    public static int getPlayerArmy()
    {
        return com.maddox.il2.ai.World.cur().PlayerArmy;
    }

    public static com.maddox.il2.fm.FlightModel getPlayerFM()
    {
        return com.maddox.il2.ai.World.cur().PlayerFM;
    }

    public static com.maddox.il2.ai.Regiment getPlayerRegiment()
    {
        return com.maddox.il2.ai.World.cur().PlayerRegiment;
    }

    public static java.lang.String getPlayerLastCountry()
    {
        com.maddox.il2.ai.Regiment regiment = com.maddox.il2.ai.World.getPlayerRegiment();
        if(regiment != null)
            com.maddox.il2.ai.World.cur().PlayerLastCountry = regiment.country();
        return com.maddox.il2.ai.World.cur().PlayerLastCountry;
    }

    public static boolean isPlayerGunner()
    {
        return com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.cur().PlayerGunner);
    }

    public static com.maddox.il2.objects.air.NetGunner getPlayerGunner()
    {
        return com.maddox.il2.ai.World.cur().PlayerGunner;
    }

    public static boolean isPlayerParatrooper()
    {
        return com.maddox.il2.ai.World.cur().bPlayerParatrooper;
    }

    public static boolean isPlayerDead()
    {
        return com.maddox.il2.ai.World.cur().bPlayerDead;
    }

    public static boolean isPlayerCaptured()
    {
        return com.maddox.il2.ai.World.cur().bPlayerCaptured;
    }

    public static boolean isPlayerRemoved()
    {
        return com.maddox.il2.ai.World.cur().bPlayerRemoved;
    }

    public static void setPlayerAircraft(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        com.maddox.il2.ai.World.cur().PlayerAircraft = aircraft;
        if(aircraft != null)
        {
            com.maddox.il2.ai.World.cur().PlayerFM = aircraft.FM;
            com.maddox.il2.ai.World.cur().scoreCounter.playerStartAir(aircraft);
        } else
        {
            com.maddox.il2.ai.World.cur().PlayerFM = null;
        }
    }

    public static void setPlayerFM()
    {
        if(com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.cur().PlayerAircraft))
            com.maddox.il2.ai.World.cur().PlayerFM = com.maddox.il2.ai.World.cur().PlayerAircraft.FM;
    }

    public static void setPlayerRegiment()
    {
        if(com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.cur().PlayerAircraft))
        {
            com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.cur().PlayerAircraft;
            if(aircraft.getOwner() != null)
                com.maddox.il2.ai.World.cur().PlayerRegiment = ((com.maddox.il2.ai.Wing)aircraft.getOwner()).regiment();
            else
                com.maddox.il2.ai.World.cur().PlayerRegiment = null;
            com.maddox.il2.ai.World.cur().PlayerArmy = aircraft.getArmy();
            if(com.maddox.il2.game.Mission.isSingle())
                com.maddox.il2.ai.World.cur().missionArmy = com.maddox.il2.ai.World.cur().PlayerArmy;
        }
    }

    public static void doPlayerParatrooper(com.maddox.il2.objects.air.Paratrooper paratrooper)
    {
        com.maddox.il2.fm.FlightModel flightmodel = com.maddox.il2.ai.World.getPlayerFM();
        if(!com.maddox.il2.ai.World.isPlayerParatrooper())
        {
            if(com.maddox.il2.engine.Config.isUSE_RENDER())
                com.maddox.rts.RTSConf.cur.hotKeyEnvs.endAllActiveCmd(false);
            com.maddox.il2.ai.World.cur().bPlayerParatrooper = true;
            if(com.maddox.il2.game.ZutiAircraft.isPlaneLandedAndDamaged(flightmodel))
                com.maddox.il2.ai.World.cur().scoreCounter.playerParatrooper();
            if(com.maddox.il2.engine.Config.isUSE_RENDER())
            {
                if(com.maddox.il2.game.Main3D.cur3D().viewActor() == com.maddox.il2.ai.World.getPlayerAircraft())
                    com.maddox.il2.game.Main3D.cur3D().setViewFlow10(paratrooper, false);
                com.maddox.il2.game.Main3D.cur3D().ordersTree.unactivate();
                com.maddox.il2.objects.effects.ForceFeedback.stopMission();
            }
        }
        if(com.maddox.il2.game.Main.cur().mission.zutiMisc_EnableReflyOnlyIfBailedOrDied && com.maddox.il2.game.Mission.isDogfight())
        {
            com.maddox.il2.game.ZutiSupportMethods.ZUTI_KIA_DELAY_CLEARED = false;
            com.maddox.il2.gui.GUINetMission.setPlayerParatrooper(paratrooper);
        }
    }

    public static void doGunnerParatrooper(com.maddox.il2.objects.air.Paratrooper paratrooper)
    {
        if(com.maddox.il2.ai.World.isPlayerParatrooper())
            return;
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
            com.maddox.rts.RTSConf.cur.hotKeyEnvs.endAllActiveCmd(false);
        com.maddox.il2.ai.World.cur().bPlayerParatrooper = true;
        com.maddox.il2.ai.World.cur().scoreCounter.playerParatrooper();
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().viewActor() == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.Main3D.cur3D().setViewFlow10(paratrooper, false);
            com.maddox.il2.objects.effects.ForceFeedback.stopMission();
            if(com.maddox.il2.game.Main.cur().mission.zutiMisc_EnableReflyOnlyIfBailedOrDied && com.maddox.il2.game.Mission.isDogfight())
            {
                com.maddox.il2.game.ZutiSupportMethods.ZUTI_KIA_DELAY_CLEARED = false;
                com.maddox.il2.gui.GUINetMission.setPlayerParatrooper(paratrooper);
            }
        }
    }

    public static void doPlayerUnderWater()
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && com.maddox.il2.game.Main3D.cur3D().viewActor() == com.maddox.il2.ai.World.getPlayerAircraft() && !com.maddox.il2.game.Main3D.cur3D().isViewOutside())
            com.maddox.il2.game.Main3D.cur3D().setViewFlow10(com.maddox.il2.ai.World.getPlayerAircraft(), false);
    }

    public static void setPlayerDead()
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
            com.maddox.rts.RTSConf.cur.hotKeyEnvs.endAllActiveCmd(false);
        com.maddox.il2.ai.World.cur().bPlayerDead = true;
        com.maddox.il2.ai.World.cur().scoreCounter.playerDead();
        if(com.maddox.il2.game.Main.cur().mission.zutiMisc_EnableReflyOnlyIfBailedOrDied && com.maddox.il2.game.Mission.isDogfight())
        {
            if(com.maddox.il2.ai.World.getPlayerFM().Gears.nOfGearsOnGr < 3)
                com.maddox.il2.game.ZutiSupportMethods.ZUTI_KIA_COUNTER++;
            com.maddox.il2.game.ZutiSupportMethods.ZUTI_KIA_DELAY_CLEARED = false;
            float f = (float)com.maddox.il2.game.Main.cur().mission.zutiMisc_ReflyKIADelay + (float)com.maddox.il2.game.ZutiSupportMethods.ZUTI_KIA_COUNTER * com.maddox.il2.game.Main.cur().mission.zutiMisc_ReflyKIADelayMultiplier;
            com.maddox.il2.game.ZutiSupportMethods.setPlayerBanDuration((long)f);
            com.maddox.il2.gui.GUINetMission.setReflyTimer(new ZutiTimer_Refly(f));
            java.lang.System.out.println(((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).uniqueName() + " has died for " + com.maddox.il2.game.ZutiSupportMethods.ZUTI_KIA_COUNTER + " times. Refly penalty is " + f + "s.");
            com.maddox.il2.ai.EventLog.type(true, ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).uniqueName() + " has died for " + com.maddox.il2.game.ZutiSupportMethods.ZUTI_KIA_COUNTER + " times. Refly penalty is " + f + "s.");
        }
    }

    public static void setPlayerCaptured()
    {
        com.maddox.il2.ai.World.cur().bPlayerCaptured = true;
        com.maddox.il2.ai.World.cur().scoreCounter.playerCaptured();
    }

    public static void setPlayerGunner(com.maddox.il2.objects.air.NetGunner netgunner)
    {
        com.maddox.il2.ai.World.cur().PlayerGunner = netgunner;
        com.maddox.il2.ai.World.cur().scoreCounter.playerStartGunner();
    }

    public static void onActorDied(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1)
    {
        com.maddox.il2.ai.World.onActorDied(actor, actor1, true);
    }

    public static void onActorDied(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1, boolean flag)
    {
        if(actor.getDiedFlag())
            throw new ActorException("actor " + actor.getClass() + ":" + actor.name() + " alredy dead");
        if(actor instanceof com.maddox.il2.objects.vehicles.planes.PlaneGeneric)
            com.maddox.il2.ai.World.cur().zutiManagePilotsBornPlacePlaneCounter((com.maddox.il2.objects.vehicles.planes.PlaneGeneric)actor);
        if((actor instanceof com.maddox.il2.objects.air.NetAircraft) && com.maddox.il2.game.Main.cur().netServerParams != null && com.maddox.il2.game.Main.cur().netServerParams.isMaster())
            try
            {
                boolean flag1 = false;
                boolean flag2 = false;
                if(actor1 != null)
                {
                    flag1 = actor.name().equals(actor1.name());
                    flag2 = actor1.name().equals("NONAME");
                }
                boolean flag3 = com.maddox.il2.game.ZutiSupportMethods.isPlaneStationary(((com.maddox.il2.objects.air.NetAircraft)actor).FM);
                if(actor1 != null && !flag2 && !flag1 && !flag3)
                    com.maddox.il2.game.ZutiSupportMethods.managePilotBornPlacePlaneCounter((com.maddox.il2.objects.air.NetAircraft)actor, false);
                else
                    com.maddox.il2.game.ZutiSupportMethods.managePilotBornPlacePlaneCounter((com.maddox.il2.objects.air.NetAircraft)actor, true);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println("onActorDied Exception: " + exception);
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        if(!com.maddox.il2.game.Mission.isDogfight())
            com.maddox.il2.objects.sounds.Voice.testTargDestr(actor, actor1 != remover ? actor1 : null);
        com.maddox.il2.ai.World.trySendChatMsgDied(actor, actor1 != remover ? actor1 : actor);
        actor.setDiedFlag(true);
        if(actor1 == remover && actor == com.maddox.il2.ai.World.cur().PlayerAircraft)
            com.maddox.il2.ai.World.cur().bPlayerRemoved = true;
        if(flag)
            com.maddox.il2.ai.EventLog.onActorDied(actor, actor1);
        com.maddox.il2.engine.Engine.cur.world.war.onActorDied(actor, actor1 != remover ? actor1 : null);
        com.maddox.il2.engine.Engine.cur.world.targetsGuard.checkActorDied(actor);
        java.lang.Object obj = com.maddox.il2.ai.World.cur().PlayerAircraft;
        com.maddox.il2.ai.World.cur();
        if(com.maddox.il2.ai.World.isPlayerGunner())
            obj = com.maddox.il2.ai.World.cur().PlayerGunner;
        if(actor.getArmy() != 0 && actor != obj && obj != null && actor1 == obj)
            if(actor.getArmy() != ((com.maddox.il2.engine.Actor) (obj)).getArmy())
                com.maddox.il2.ai.World.cur().scoreCounter.enemyDestroyed(actor);
            else
                com.maddox.il2.ai.World.cur().scoreCounter.friendDestroyed(actor);
        if(actor == com.maddox.il2.ai.World.cur().PlayerAircraft)
        {
            com.maddox.il2.ai.World.cur().checkViewOnPlayerDied(actor);
            if(com.maddox.il2.engine.Config.isUSE_RENDER())
            {
                com.maddox.rts.CmdEnv.top().exec("music RAND music/crash");
                com.maddox.il2.objects.effects.ForceFeedback.stopMission();
            }
            if(actor1 != com.maddox.il2.ai.World.cur().PlayerAircraft)
            {
                com.maddox.il2.ai.World.cur();
                if(!com.maddox.il2.ai.World.isPlayerParatrooper())
                    com.maddox.il2.ai.World.cur().scoreCounter.playerDead();
            }
        }
    }

    public void checkViewOnPlayerDied(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.JGP.Point3d point3d = new Point3d();
        com.maddox.JGP.Point3d point3d1 = new Point3d();
        com.maddox.JGP.Point3d point3d2 = new Point3d();
        point3d.set(actor.pos.getAbsPoint());
        point3d1.set(actor.pos.getAbsPoint());
        point3d.z -= 40D;
        point3d1.z += 40D;
        com.maddox.il2.engine.Actor actor1 = com.maddox.il2.engine.Engine.collideEnv().getLine(point3d1, point3d, false, clipFilter, point3d2);
        if(com.maddox.il2.engine.Actor.isValid(actor1))
        {
            if(com.maddox.il2.engine.Config.isUSE_RENDER() && com.maddox.il2.game.Main3D.cur3D().viewActor() == actor)
                com.maddox.il2.game.Main3D.cur3D().setViewFlow10(actor1, false);
            return;
        }
        com.maddox.il2.objects.ActorViewPoint actorviewpoint = new ActorViewPoint();
        actorviewpoint.pos.setAbs(actor.pos.getAbs());
        actorviewpoint.pos.reset();
        actorviewpoint.dreamFire(true);
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && com.maddox.il2.game.Main3D.cur3D().viewActor() == actor)
        {
            com.maddox.il2.game.Main3D.cur3D().hookView.set(actorviewpoint, 3F * com.maddox.il2.game.Main3D.cur3D().hookView.defaultLen(), 10F, -10F);
            com.maddox.il2.game.Main3D.cur3D().setView(actorviewpoint, true);
        }
    }

    public static void onTaskComplete(com.maddox.il2.engine.Actor actor)
    {
        if(actor.isTaskComplete())
            return;
        actor.setTaskCompleteFlag(true);
        com.maddox.il2.engine.Engine.cur.world.targetsGuard.checkTaskComplete(actor);
        if(actor.isNetMaster())
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).postTaskComplete(actor);
    }

    private static void trySendChatMsgDied(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(com.maddox.il2.game.Mission.isSingle())
            return;
        if(com.maddox.il2.game.Main.cur().chat == null)
            return;
        if(!(actor instanceof com.maddox.il2.objects.air.Aircraft))
            return;
        if(actor.net == null)
            return;
        if(!actor.net.isMaster())
            return;
        com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)actor;
        com.maddox.il2.net.NetUser netuser = aircraft.netUser();
        if(netuser == null)
            return;
        if(actor == actor1)
            return;
        int i = com.maddox.il2.engine.Engine.cur.world.scoreCounter.getRegisteredType(actor1);
        if(!aircraft.FM.isSentBuryNote())
        {
            aircraft.FM.setSentBuryNote(true);
            switch(i)
            {
            case 0: // '\0'
                if((actor1 instanceof com.maddox.il2.objects.air.Aircraft) && ((com.maddox.il2.objects.air.Aircraft)actor1).netUser() != null)
                {
                    com.maddox.il2.net.Chat.sendLog(1, "gore_kill" + com.maddox.il2.ai.World.Rnd().nextInt(1, 5), (com.maddox.il2.objects.air.Aircraft)actor1, aircraft);
                    if(!aircraft.FM.isWasAirborne() && aircraft.isDamagerExclusive())
                        com.maddox.il2.net.Chat.sendLogRnd(2, "gore_vulcher", (com.maddox.il2.objects.air.Aircraft)actor1, null);
                } else
                {
                    com.maddox.il2.net.Chat.sendLogRnd(1, "gore_ai", aircraft, null);
                }
                return;

            case 1: // '\001'
                com.maddox.il2.net.Chat.sendLogRnd(2, "gore_tank", aircraft, null);
                return;

            case 2: // '\002'
                com.maddox.il2.net.Chat.sendLogRnd(2, "gore_gun", aircraft, null);
                return;

            case 3: // '\003'
                com.maddox.il2.net.Chat.sendLogRnd(2, "gore_gun", aircraft, null);
                return;

            case 4: // '\004'
                com.maddox.il2.net.Chat.sendLogRnd(2, "gore_killaaa", aircraft, null);
                return;

            case 7: // '\007'
                com.maddox.il2.net.Chat.sendLogRnd(2, "gore_ship", aircraft, null);
                return;

            case 6: // '\006'
                com.maddox.il2.net.Chat.sendLogRnd(2, "gore_killaaa", aircraft, null);
                return;

            case 5: // '\005'
            case 8: // '\b'
            case 9: // '\t'
            default:
                com.maddox.il2.net.Chat.sendLogRnd(1, "gore_crashes", aircraft, null);
                break;
            }
        }
    }

    public static com.maddox.il2.engine.Landscape land()
    {
        return com.maddox.il2.engine.Engine.land();
    }

    public static com.maddox.il2.fm.Wind wind()
    {
        return com.maddox.il2.ai.World.cur().wind;
    }

    public static com.maddox.il2.ai.World cur()
    {
        return com.maddox.il2.engine.Engine.cur.world;
    }

    public void resetGameClear()
    {
        com.maddox.il2.ai.EventLog.resetGameClear();
        front.resetGameClear();
        war.resetGameClear();
        bTimeOfDayConstant = false;
        if(statics != null)
            statics.resetGame();
        if(airports != null)
        {
            for(int i = 0; i < airports.size(); i++)
                ((com.maddox.il2.ai.Airport)airports.get(i)).destroy();

            airports.clear();
            airports = null;
        }
        do
        {
            if(runawayList == null)
                break;
            com.maddox.il2.objects.air.Runaway runaway = runawayList;
            runawayList = runaway.next();
            if(com.maddox.il2.engine.Actor.isValid(runaway))
                runaway.destroy();
        } while(true);
        targetsGuard.resetGame();
        scoreCounter.resetGame();
        com.maddox.il2.ai.ground.NearestEnemies.resetGameClear();
        com.maddox.il2.ai.air.NearestAircraft.resetGameClear();
        com.maddox.il2.objects.air.Aircraft.resetGameClear();
        com.maddox.il2.ai.MsgExplosion.resetGame();
        com.maddox.il2.ai.MsgShot.resetGame();
        com.maddox.il2.ai.Regiment.resetGame();
        bornPlaces = null;
        bPlayerParatrooper = false;
        bPlayerDead = false;
        bPlayerCaptured = false;
        bPlayerRemoved = false;
        if(com.maddox.il2.engine.Actor.isValid(houseManager))
            houseManager.destroy();
        houseManager = null;
    }

    public void resetGameCreate()
    {
        if(statics == null)
            statics = new Statics();
        ChiefMan = new ChiefManager();
        com.maddox.il2.ai.World.setPlayerAircraft(null);
        PlayerArmy = 1;
        rnd = new RangeRandom();
        voicebase = new Voice();
        com.maddox.il2.ai.World.setTimeofDay(12F);
        airports = new ArrayList();
        war.resetGameCreate();
        front.resetGameCreate();
        com.maddox.il2.ai.EventLog.resetGameCreate();
    }

    public void resetUser()
    {
        bPlayerParatrooper = false;
        bPlayerDead = false;
        com.maddox.il2.game.ZutiSupportMethods.ZUTI_KIA_DELAY_CLEARED = false;
    }

    public World()
    {
        blockMorseChat = false;
        smallMapWPLabels = false;
        rnd = new RangeRandom();
        camouflage = 0;
        diffCur = new DifficultySettings();
        diffUser = new DifficultySettings();
        userCoverMashineGun = 500F;
        userCoverCannon = 500F;
        userCoverRocket = 500F;
        userRocketDelay = 10F;
        userBombDelay = 0.0F;
        bArcade = false;
        bHighGore = false;
        bHakenAllowed = false;
        bDebugFM = false;
        bTimeOfDayConstant = false;
        bWeaponsConstant = false;
        missionArmy = 1;
        PlayerArmy = 1;
        bPlayerParatrooper = false;
        bPlayerDead = false;
        bPlayerCaptured = false;
        bPlayerRemoved = false;
        targetsGuard = new TargetsGuard();
        scoreCounter = new ScoreCounter();
        wind = new Wind();
        front = new Front();
        startTimeofDay = 43200;
        Atm = new Atmosphere();
        ChiefMan = new ChiefManager();
        sun = new Sun();
        voicebase = new Voice();
        zutiCurrentBornPlace = null;
        war = new War();
        bArcade = com.maddox.il2.engine.Config.cur.ini.get("game", "Arcade", bArcade);
        if(com.maddox.il2.engine.Config.LOCALE.equals("RU"))
        {
            bHighGore = com.maddox.il2.engine.Config.cur.ini.get("game", "HighGore", bHighGore);
            bHakenAllowed = com.maddox.il2.engine.Config.cur.ini.get("game", "HakenAllowed", bHakenAllowed);
        }
        blockMorseChat = com.maddox.il2.engine.Config.cur.ini.get("game", "BlockMorseChat", blockMorseChat);
        smallMapWPLabels = com.maddox.il2.engine.Config.cur.ini.get("game", "SmallMapWPLabels", smallMapWPLabels);
    }

    public static float getTimeofDay()
    {
        if(com.maddox.il2.ai.World.cur().bTimeOfDayConstant)
            return ((float)com.maddox.il2.ai.World.cur().startTimeofDay * 0.0002777778F) % 24F;
        else
            return ((float)(com.maddox.rts.Time.current() / 1000L + (long)com.maddox.il2.ai.World.cur().startTimeofDay) * 0.0002777778F) % 24F;
    }

    public static void setTimeofDay(float f)
    {
        int i = (int)((f * 3600F) % 86400F);
        if(com.maddox.il2.ai.World.cur().bTimeOfDayConstant)
            com.maddox.il2.ai.World.cur().startTimeofDay = i;
        else
            com.maddox.il2.ai.World.cur().startTimeofDay = i - (int)(com.maddox.rts.Time.current() / 1000L);
    }

    public static float g()
    {
        return com.maddox.il2.fm.Atmosphere.g();
    }

    public static com.maddox.il2.engine.Sun Sun()
    {
        return com.maddox.il2.ai.World.cur().sun;
    }

    public com.maddox.il2.engine.Sun sun()
    {
        return sun;
    }

    private void zutiManagePilotsBornPlacePlaneCounter(com.maddox.il2.objects.vehicles.planes.PlaneGeneric planegeneric)
    {
        java.lang.String s = com.maddox.il2.game.ZutiAircraft.getStaticAcNameFromActor(planegeneric.toString());
        com.maddox.JGP.Point3d point3d = planegeneric.pos.getAbsPoint();
        double d = point3d.x;
        double d1 = point3d.y;
        try
        {
            boolean flag = false;
            if(zutiCurrentBornPlace != null)
            {
                double d2 = java.lang.Math.sqrt(java.lang.Math.pow(zutiCurrentBornPlace.place.x - d, 2D) + java.lang.Math.pow(zutiCurrentBornPlace.place.y - d1, 2D));
                if(d2 <= (double)zutiCurrentBornPlace.r)
                {
                    zutiCurrentBornPlace.zutiReleaseAircraft(null, s, false, true, false);
                    flag = true;
                }
            }
            if(!flag && bornPlaces != null)
            {
                Object obj = null;
                for(int i = 0; i < bornPlaces.size(); i++)
                {
                    com.maddox.il2.net.BornPlace bornplace = (com.maddox.il2.net.BornPlace)bornPlaces.get(i);
                    if(bornplace.zutiIncludeStaticPlanes)
                    {
                        double d3 = java.lang.Math.pow(bornplace.place.x - d, 2D) + java.lang.Math.pow(bornplace.place.y - d1, 2D);
                        if(d3 <= (double)(bornplace.r * bornplace.r))
                        {
                            bornplace.zutiReleaseAircraft(null, s, false, true, false);
                            zutiCurrentBornPlace = bornplace;
                        }
                    }
                }

            }
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static final float NORD = 270F;
    public static final float PIXEL = 200F;
    public static float MaxVisualDistance = 5000F;
    public static float MaxStaticVisualDistance = 4000F;
    public static float MaxLongVisualDistance = 10000F;
    public static float MaxPlateVisualDistance = 16000F;
    public boolean blockMorseChat;
    public boolean smallMapWPLabels;
    public com.maddox.il2.ai.RangeRandom rnd;
    public int camouflage;
    public static final int CAMOUFLAGE_SUMMER = 0;
    public static final int CAMOUFLAGE_WINTER = 1;
    public static final int CAMOUFLAGE_DESERT = 2;
    public static final int CAMOUFLAGE_PACIFIC = 3;
    public static final int CAMOUFLAGE_ETO = 4;
    public static final int CAMOUFLAGE_MTO = 5;
    public static final int CAMOUFLAGE_CBI = 6;
    public com.maddox.il2.ai.DifficultySettings diffCur;
    public com.maddox.il2.ai.DifficultySettings diffUser;
    public com.maddox.il2.ai.UserCfg userCfg;
    public float userCoverMashineGun;
    public float userCoverCannon;
    public float userCoverRocket;
    public float userRocketDelay;
    public float userBombDelay;
    private boolean bArcade;
    private boolean bHighGore;
    private boolean bHakenAllowed;
    private boolean bDebugFM;
    private boolean bTimeOfDayConstant;
    private boolean bWeaponsConstant;
    protected com.maddox.il2.ai.War war;
    protected java.util.ArrayList airports;
    public java.util.ArrayList bornPlaces;
    public com.maddox.il2.objects.buildings.HouseManager houseManager;
    public com.maddox.il2.objects.air.Runaway runawayList;
    public com.maddox.il2.ai.air.Airdrome airdrome;
    private int missionArmy;
    private com.maddox.il2.objects.air.Aircraft PlayerAircraft;
    private com.maddox.il2.objects.air.NetGunner PlayerGunner;
    private int PlayerArmy;
    private com.maddox.il2.fm.FlightModel PlayerFM;
    private com.maddox.il2.ai.Regiment PlayerRegiment;
    private java.lang.String PlayerLastCountry;
    private boolean bPlayerParatrooper;
    private boolean bPlayerDead;
    private boolean bPlayerCaptured;
    private boolean bPlayerRemoved;
    public static com.maddox.il2.engine.Actor remover = new Remover();
    static com.maddox.il2.ai.ClipFilter clipFilter = new ClipFilter();
    public com.maddox.il2.ai.TargetsGuard targetsGuard;
    public com.maddox.il2.ai.ScoreCounter scoreCounter;
    private com.maddox.il2.fm.Wind wind;
    protected com.maddox.il2.ai.Front front;
    public com.maddox.il2.objects.Statics statics;
    private int startTimeofDay;
    public com.maddox.il2.fm.Atmosphere Atm;
    public float fogColor[] = {
        0.53F, 0.64F, 0.8F, 1.0F
    };
    public float beachColor[] = {
        0.6F, 0.6F, 0.6F
    };
    public float lodColor[] = {
        0.7F, 0.7F, 0.7F
    };
    public com.maddox.il2.ai.ChiefManager ChiefMan;
    private com.maddox.il2.engine.Sun sun;
    public com.maddox.il2.objects.sounds.Voice voicebase;
    private com.maddox.il2.net.BornPlace zutiCurrentBornPlace;

    static 
    {
        com.maddox.il2.objects.ScoreRegister.load();
    }
}
