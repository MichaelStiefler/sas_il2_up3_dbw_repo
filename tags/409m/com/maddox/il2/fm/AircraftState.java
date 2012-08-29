// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AircraftState.java

package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.air.AR_234B2;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.Cockpit;
import com.maddox.il2.objects.air.DO_335;
import com.maddox.il2.objects.air.DO_335A0;
import com.maddox.il2.objects.air.DO_335V13;
import com.maddox.il2.objects.air.FW_190A8MSTL;
import com.maddox.il2.objects.air.GO_229;
import com.maddox.il2.objects.air.HE_162;
import com.maddox.il2.objects.air.ME_262HGII;
import com.maddox.il2.objects.air.P_39;
import com.maddox.il2.objects.air.Paratrooper;
import com.maddox.il2.objects.air.Scheme0;
import com.maddox.il2.objects.air.Scheme1;
import com.maddox.il2.objects.air.TypeBomber;
import com.maddox.il2.objects.air.TypeDockable;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.MsgDestroy;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;
import java.io.IOException;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.fm:
//            RealFlightModel, FlightModel, EnginesInterface, Motor, 
//            Controls, Gear, Mass, Autopilotage, 
//            Pitot

public class AircraftState
{
    static class Item
    {

        void set(int i, int j, com.maddox.il2.engine.Actor actor1)
        {
            msgDestination = i;
            msgContext = j;
            initiator = actor1;
        }

        boolean equals(int i, int j, com.maddox.il2.engine.Actor actor1)
        {
            return msgDestination == i && msgContext == j && initiator == actor1;
        }

        int msgDestination;
        int msgContext;
        com.maddox.il2.engine.Actor initiator;

        Item()
        {
        }
    }


    public AircraftState()
    {
        bIsAboveCriticalSpeed = false;
        bIsAboveCondensateAlt = false;
        bIsOnInadequateAOA = false;
        bShowSmokesOn = false;
        bNavLightsOn = false;
        bLandingLightOn = false;
        bWingTipLExists = true;
        bWingTipRExists = true;
        actor = null;
        aircraft = null;
        astatePilotStates = new byte[9];
        astatePlayerIndex = 0;
        bIsAboutToBailout = false;
        bIsEnableToBailout = true;
        astateBailoutStep = 0;
        astateCockpitState = 0;
        itemsToMaster = null;
        itemsToMirrors = null;
    }

    public void set(com.maddox.il2.engine.Actor actor1, boolean flag)
    {
        com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        com.maddox.il2.engine.Loc loc1 = new Loc();
        actor = actor1;
        if(actor1 instanceof com.maddox.il2.objects.air.Aircraft)
            aircraft = (com.maddox.il2.objects.air.Aircraft)actor1;
        else
            throw new RuntimeException("Can not cast aircraft structure into a non-aircraft entity.");
        bIsMaster = flag;
        for(int i = 0; i < 4; i++)
            try
            {
                astateEffectChunks[i + 0] = actor.findHook("_Tank" + (i + 1) + "Burn").chunkName();
                astateEffectChunks[i + 0] = astateEffectChunks[i + 0].substring(0, astateEffectChunks[i + 0].length() - 1);
                com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: Tank " + i + " FX attached to '" + astateEffectChunks[i + 0] + "' substring..");
            }
            catch(java.lang.Exception exception) { }
            finally { }

        for(int j = 0; j < aircraft.FM.EI.getNum(); j++)
            try
            {
                astateEffectChunks[j + 4] = actor.findHook("_Engine" + (j + 1) + "Smoke").chunkName();
                astateEffectChunks[j + 4] = astateEffectChunks[j + 4].substring(0, astateEffectChunks[j + 4].length() - 1);
                com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: Engine " + j + " FX attached to '" + astateEffectChunks[j + 4] + "' substring..");
            }
            catch(java.lang.Exception exception1) { }
            finally { }

        for(int k = 0; k < astateNavLightsEffects.length; k++)
            try
            {
                astateEffectChunks[k + 12] = actor.findHook("_NavLight" + k).chunkName();
                astateEffectChunks[k + 12] = astateEffectChunks[k + 12].substring(0, astateEffectChunks[k + 12].length() - 1);
                com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: Nav. Lamp #" + k + " attached to '" + astateEffectChunks[k + 12] + "' substring..");
                com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(aircraft, "_NavLight" + k);
                loc1.set(1.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
                hooknamed.computePos(actor, loc, loc1);
                com.maddox.JGP.Point3d point3d = loc1.getPoint();
                astateNavLightsLights[k] = new LightPointActor(new LightPoint(), point3d);
                if(k < 2)
                    astateNavLightsLights[k].light.setColor(1.0F, 0.1F, 0.1F);
                else
                if(k < 4)
                    astateNavLightsLights[k].light.setColor(0.0F, 1.0F, 0.0F);
                else
                    astateNavLightsLights[k].light.setColor(0.7F, 0.7F, 0.7F);
                astateNavLightsLights[k].light.setEmit(0.0F, 0.0F);
                actor.draw.lightMap().put("_NavLight" + k, astateNavLightsLights[k]);
            }
            catch(java.lang.Exception exception2) { }
            finally { }

        for(int l = 0; l < 4; l++)
            try
            {
                astateEffectChunks[l + 18] = actor.findHook("_LandingLight0" + l).chunkName();
                astateEffectChunks[l + 18] = astateEffectChunks[l + 18].substring(0, astateEffectChunks[l + 18].length() - 1);
                com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: Landing Lamp #" + l + " attached to '" + astateEffectChunks[l + 18] + "' substring..");
                com.maddox.il2.engine.HookNamed hooknamed1 = new HookNamed(aircraft, "_LandingLight0" + l);
                loc1.set(1.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
                hooknamed1.computePos(actor, loc, loc1);
                com.maddox.JGP.Point3d point3d1 = loc1.getPoint();
                astateLandingLightLights[l] = new LightPointActor(new LightPoint(), point3d1);
                astateLandingLightLights[l].light.setColor(0.4941176F, 0.9098039F, 0.9607843F);
                astateLandingLightLights[l].light.setEmit(0.0F, 0.0F);
                actor.draw.lightMap().put("_LandingLight0" + l, astateLandingLightLights[l]);
            }
            catch(java.lang.Exception exception3) { }
            finally { }

        for(int i1 = 0; i1 < aircraft.FM.EI.getNum(); i1++)
            try
            {
                astateEffectChunks[i1 + 22] = actor.findHook("_Engine" + (i1 + 1) + "Oil").chunkName();
                astateEffectChunks[i1 + 22] = astateEffectChunks[i1 + 22].substring(0, astateEffectChunks[i1 + 22].length() - 1);
                com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: Oilfilter " + i1 + " FX attached to '" + astateEffectChunks[i1 + 22] + "' substring..");
            }
            catch(java.lang.Exception exception4) { }
            finally { }

        for(int j1 = 0; j1 < astateEffectChunks.length; j1++)
            if(astateEffectChunks[j1] == null)
                astateEffectChunks[j1] = "AChunkNameYouCanNeverFind";

    }

    public boolean isMaster()
    {
        return bIsMaster;
    }

    public void setOilState(com.maddox.il2.engine.Actor actor1, int i, int j)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(j < 0 || j > 1 || astateOilStates[i] == j)
            return;
        if(bIsMaster)
        {
            aircraft.setDamager(actor1);
            doSetOilState(i, j);
            int k = 0;
            for(int l = 0; l < aircraft.FM.EI.getNum(); l++)
                if(astateOilStates[l] == 1)
                    k++;

            if(k == aircraft.FM.EI.getNum())
                setCockpitState(actor, astateCockpitState | 0x80);
            netToMirrors(11, i, j);
        } else
        {
            netToMaster(11, i, j, actor1);
        }
    }

    public void hitOil(com.maddox.il2.engine.Actor actor1, int i)
    {
        if(astateOilStates[i] > 0)
            return;
        if(astateOilStates[i] < 1)
            setOilState(actor1, i, astateOilStates[i] + 1);
    }

    public void repairOil(int i)
    {
        if(!bIsMaster)
            return;
        if(astateOilStates[i] > 0)
            setOilState(actor, i, astateOilStates[i] - 1);
    }

    private void doSetOilState(int i, int j)
    {
        if(astateOilStates[i] == j)
            return;
        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: Checking '" + astateEffectChunks[i + 22] + "' visibility..");
        boolean flag = aircraft.isChunkAnyDamageVisible(astateEffectChunks[i + 22]);
        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: '" + astateEffectChunks[i + 22] + "' is " + (flag ? "visible" : "invisible") + "..");
        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Stating OilFilter " + i + " to state " + j + (flag ? ".." : " rejected (missing part).."));
        if(!flag)
            return;
        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Stating OilFilter " + i + " to state " + j + "..");
        astateOilStates[i] = (byte)j;
        byte byte0 = 0;
        if(!bIsAboveCriticalSpeed)
            byte0 = 2;
        if(astateOilEffects[i][0] != null)
            com.maddox.il2.engine.Eff3DActor.finish(astateOilEffects[i][0]);
        astateOilEffects[i][0] = null;
        if(astateOilEffects[i][1] != null)
            com.maddox.il2.engine.Eff3DActor.finish(astateOilEffects[i][1]);
        astateOilEffects[i][1] = null;
        switch(astateOilStates[i])
        {
        case 1: // '\001'
            java.lang.String s = astateOilStrings[byte0];
            if(s != null)
                try
                {
                    astateOilEffects[i][0] = com.maddox.il2.engine.Eff3DActor.New(actor, actor.findHook("_Engine" + (i + 1) + "Oil"), null, 1.0F, s, -1F);
                }
                catch(java.lang.Exception exception) { }
            s = astateOilStrings[byte0 + 1];
            if(s != null)
                try
                {
                    astateOilEffects[i][1] = com.maddox.il2.engine.Eff3DActor.New(actor, actor.findHook("_Engine" + (i + 1) + "Oil"), null, 1.0F, s, -1F);
                }
                catch(java.lang.Exception exception1) { }
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                aircraft.FM.setReadyToReturn(true);
            break;
        }
    }

    public void changeOilEffectBase(int i, com.maddox.il2.engine.Actor actor1)
    {
        for(int j = 0; j < 2; j++)
            if(astateOilEffects[i][j] != null)
                astateOilEffects[i][j].pos.changeBase(actor1, null, true);

    }

    public void setTankState(com.maddox.il2.engine.Actor actor1, int i, int j)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(j < 0 || j > 6 || astateTankStates[i] == j)
            return;
        if(bIsMaster)
        {
            byte byte0 = astateTankStates[i];
            if(!doSetTankState(actor1, i, j))
                return;
            for(int k = byte0; k < j; k++)
            {
                if(k % 2 == 0)
                    aircraft.setDamager(actor1);
                doHitTank(actor1, i);
            }

            if(aircraft.FM.isPlayers() && actor1 != actor && (actor1 instanceof com.maddox.il2.objects.air.Aircraft) && ((com.maddox.il2.objects.air.Aircraft)actor1).isNetPlayer() && j > 5 && astateTankStates[0] < 5 && astateTankStates[1] < 5 && astateTankStates[2] < 5 && astateTankStates[3] < 5 && !aircraft.FM.isSentBuryNote())
                com.maddox.il2.net.Chat.sendLogRnd(3, "gore_lightfuel", (com.maddox.il2.objects.air.Aircraft)actor1, aircraft);
            netToMirrors(9, i, j);
        } else
        {
            netToMaster(9, i, j, actor1);
        }
    }

    public void hitTank(com.maddox.il2.engine.Actor actor1, int i, int j)
    {
        if(astateTankStates[i] == 6)
            return;
        int k = astateTankStates[i] + j;
        if(k > 6)
            k = 6;
        setTankState(actor1, i, k);
    }

    public void repairTank(int i)
    {
        if(!bIsMaster)
            return;
        if(astateTankStates[i] > 0)
            setTankState(actor, i, astateTankStates[i] - 1);
    }

    public boolean doSetTankState(com.maddox.il2.engine.Actor actor1, int i, int j)
    {
        boolean flag = aircraft.isChunkAnyDamageVisible(astateEffectChunks[i + 0]);
        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Stating Tank " + i + " to state " + j + (flag ? ".." : " rejected (missing part).."));
        if(!flag)
            return false;
        if(com.maddox.il2.ai.World.getPlayerAircraft() == actor)
        {
            if(astateTankStates[i] == 0 && (j == 1 || j == 2))
                com.maddox.il2.game.HUD.log("FailedTank");
            if(astateTankStates[i] < 5 && j >= 5)
                com.maddox.il2.game.HUD.log("FailedTankOnFire");
        }
        astateTankStates[i] = (byte)j;
        if(astateTankStates[i] < 5 && j >= 5)
        {
            aircraft.FM.setTakenMortalDamage(true, actor1);
            aircraft.FM.setCapableOfACM(false);
        }
        if(j < 4 && aircraft.FM.isCapableOfBMP())
            aircraft.FM.setTakenMortalDamage(false, actor1);
        byte byte0 = 0;
        if(!bIsAboveCriticalSpeed)
            byte0 = 21;
        for(int k = 0; k < 3; k++)
        {
            if(astateTankEffects[i][k] != null)
                com.maddox.il2.engine.Eff3DActor.finish(astateTankEffects[i][k]);
            astateTankEffects[i][k] = null;
            java.lang.String s = astateTankStrings[byte0 + k + j * 3];
            if(s != null)
                if(j > 2)
                    astateTankEffects[i][k] = com.maddox.il2.engine.Eff3DActor.New(actor, actor.findHook("_Tank" + (i + 1) + "Burn"), null, 1.0F, s, -1F);
                else
                    astateTankEffects[i][k] = com.maddox.il2.engine.Eff3DActor.New(actor, actor.findHook("_Tank" + (i + 1) + "Leak"), null, 1.0F, s, -1F);
        }

        aircraft.sfxSmokeState(2, i, j > 4);
        return true;
    }

    private void doHitTank(com.maddox.il2.engine.Actor actor1, int i)
    {
        if((com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 75 || (actor instanceof com.maddox.il2.objects.air.Scheme1)) && astateTankStates[i] == 6)
        {
            aircraft.FM.setReadyToDie(true);
            aircraft.FM.setTakenMortalDamage(true, actor1);
            aircraft.FM.setCapableOfACM(false);
            com.maddox.il2.objects.sounds.Voice.speakMayday(aircraft);
            com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "I'm on fire, going down!.");
            com.maddox.il2.objects.effects.Explosions.generateComicBulb(actor, "OnFire", 12F);
            if(com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 75 && aircraft.FM.Skill > 1)
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "BAILING OUT - Tank " + i + " is on fire!.");
                hitDaSilk();
            }
        } else
        if(com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 12)
        {
            aircraft.FM.setReadyToReturn(true);
            com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Tank " + i + " hit, RTB..");
            com.maddox.il2.objects.effects.Explosions.generateComicBulb(actor, "RTB", 12F);
        }
    }

    public void changeTankEffectBase(int i, com.maddox.il2.engine.Actor actor1)
    {
        for(int j = 0; j < 3; j++)
            if(astateTankEffects[i][j] != null)
                astateTankEffects[i][j].pos.changeBase(actor1, null, true);

        aircraft.sfxSmokeState(2, i, false);
    }

    public void explodeTank(com.maddox.il2.engine.Actor actor1, int i)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(bIsMaster)
        {
            if(!aircraft.isChunkAnyDamageVisible(astateEffectChunks[i + 0]))
                return;
            netToMirrors(10, i, 0);
            doExplodeTank(i);
        } else
        {
            netToMaster(10, i, 0, actor1);
        }
    }

    private void doExplodeTank(int i)
    {
        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Tank " + i + " explodes..");
        com.maddox.il2.engine.Eff3DActor.New(actor, actor.findHook("_Tank" + (i + 1) + "Burn"), null, 1.0F, "3DO/Effects/Fireworks/Tank_Burn.eff", -1F);
        com.maddox.il2.engine.Eff3DActor.New(actor, actor.findHook("_Tank" + (i + 1) + "Burn"), null, 1.0F, "3DO/Effects/Fireworks/Tank_SmokeBoiling.eff", -1F);
        com.maddox.il2.engine.Eff3DActor.New(actor, actor.findHook("_Tank" + (i + 1) + "Burn"), null, 1.0F, "3DO/Effects/Fireworks/Tank_Sparks.eff", -1F);
        com.maddox.il2.engine.Eff3DActor.New(actor, actor.findHook("_Tank" + (i + 1) + "Burn"), null, 1.0F, "3DO/Effects/Fireworks/Tank_SparksP.eff", -1F);
        aircraft.msgCollision(actor, astateEffectChunks[i + 0] + "0", astateEffectChunks[i + 0] + "0");
        if((actor instanceof com.maddox.il2.objects.air.Scheme1) && aircraft.isChunkAnyDamageVisible(astateEffectChunks[i + 0]))
            aircraft.msgCollision(actor, "CF_D0", "CF_D0");
        com.maddox.il2.engine.Actor actor1;
        if(aircraft.getDamager() != null)
            actor1 = aircraft.getDamager();
        else
            actor1 = actor;
        com.maddox.il2.engine.HookNamed hooknamed = new HookNamed((com.maddox.il2.engine.ActorMesh)actor, "_Tank" + (i + 1) + "Burn");
        com.maddox.il2.engine.Loc loc = new Loc();
        com.maddox.il2.engine.Loc loc1 = new Loc();
        actor.pos.getCurrent(loc);
        hooknamed.computePos(actor, loc, loc1);
        if(com.maddox.il2.ai.World.getPlayerAircraft() == actor)
            com.maddox.il2.game.HUD.log("FailedTankExplodes");
    }

    public void setEngineState(com.maddox.il2.engine.Actor actor1, int i, int j)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(j < 0 || j > 4 || astateEngineStates[i] == j)
            return;
        if(bIsMaster)
        {
            byte byte0 = astateEngineStates[i];
            if(!doSetEngineState(actor1, i, j))
                return;
            for(int k = byte0; k < j; k++)
            {
                aircraft.setDamager(actor1);
                doHitEngine(actor1, i);
            }

            if(aircraft.FM.isPlayers() && actor1 != actor && (actor1 instanceof com.maddox.il2.objects.air.Aircraft) && ((com.maddox.il2.objects.air.Aircraft)actor1).isNetPlayer() && j > 3 && astateEngineStates[0] < 3 && astateEngineStates[1] < 3 && astateEngineStates[2] < 3 && astateEngineStates[3] < 3 && !aircraft.FM.isSentBuryNote())
                com.maddox.il2.net.Chat.sendLogRnd(3, "gore_lighteng", (com.maddox.il2.objects.air.Aircraft)actor1, aircraft);
            int l = 0;
            for(int i1 = 0; i1 < aircraft.FM.EI.getNum(); i1++)
                if(astateEngineStates[i1] > 2)
                    l++;

            if(l == aircraft.FM.EI.getNum())
                setCockpitState(actor, astateCockpitState | 0x80);
            netToMirrors(1, i, j);
        } else
        {
            netToMaster(1, i, j, actor1);
        }
    }

    public void hitEngine(com.maddox.il2.engine.Actor actor1, int i, int j)
    {
        if(astateEngineStates[i] == 4)
            return;
        int k = astateEngineStates[i] + j;
        if(k > 4)
            k = 4;
        setEngineState(actor1, i, k);
    }

    public void repairEngine(int i)
    {
        if(!bIsMaster)
            return;
        if(astateEngineStates[i] > 0)
            setEngineState(actor, i, astateEngineStates[i] - 1);
    }

    public boolean doSetEngineState(com.maddox.il2.engine.Actor actor1, int i, int j)
    {
        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: Checking '" + astateEffectChunks[i + 4] + "' visibility..");
        boolean flag = aircraft.isChunkAnyDamageVisible(astateEffectChunks[i + 4]);
        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: '" + astateEffectChunks[i + 4] + "' is " + (flag ? "visible" : "invisible") + "..");
        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Stating Engine " + i + " to state " + j + (flag ? ".." : " rejected (missing part).."));
        if(!flag)
            return false;
        if(astateEngineStates[i] < 4 && j >= 4)
        {
            if(com.maddox.il2.ai.World.getPlayerAircraft() == actor)
                com.maddox.il2.game.HUD.log("FailedEngineOnFire");
            aircraft.FM.setTakenMortalDamage(true, actor1);
            aircraft.FM.setCapableOfACM(false);
            aircraft.FM.setCapableOfTaxiing(false);
        }
        astateEngineStates[i] = (byte)j;
        if(j < 2 && aircraft.FM.isCapableOfBMP())
            aircraft.FM.setTakenMortalDamage(false, actor1);
        byte byte0 = 0;
        if(!bIsAboveCriticalSpeed)
            byte0 = 15;
        for(int k = 0; k < 3; k++)
        {
            if(astateEngineEffects[i][k] != null)
                com.maddox.il2.engine.Eff3DActor.finish(astateEngineEffects[i][k]);
            astateEngineEffects[i][k] = null;
            java.lang.String s = astateEngineStrings[byte0 + k + j * 3];
            if(s != null)
                astateEngineEffects[i][k] = com.maddox.il2.engine.Eff3DActor.New(actor, actor.findHook("_Engine" + (i + 1) + "Smoke"), null, 1.0F, s, -1F);
        }

        aircraft.sfxSmokeState(1, i, j > 3);
        return true;
    }

    private void doHitEngine(com.maddox.il2.engine.Actor actor1, int i)
    {
        if(com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 12)
        {
            aircraft.FM.setReadyToReturn(true);
            com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Engines out, RTB..");
            com.maddox.il2.objects.effects.Explosions.generateComicBulb(actor, "RTB", 12F);
        }
        if(astateEngineStates[i] >= 2 && !(actor instanceof com.maddox.il2.objects.air.Scheme1) && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 25)
        {
            aircraft.FM.setReadyToReturn(true);
            com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "One of the engines out, RTB..");
        }
        if(astateEngineStates[i] == 4)
            if(actor instanceof com.maddox.il2.objects.air.Scheme1)
            {
                if(com.maddox.il2.ai.World.Rnd().nextBoolean())
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "BAILING OUT - Engine " + i + " is on fire..");
                    aircraft.hitDaSilk();
                }
                aircraft.FM.setReadyToDie(true);
                aircraft.FM.setTakenMortalDamage(true, actor1);
            } else
            if(com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 50)
            {
                aircraft.FM.setReadyToDie(true);
                if(com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 25)
                    aircraft.FM.setTakenMortalDamage(true, actor1);
                aircraft.FM.setCapableOfACM(false);
                com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Engines on fire, ditching..");
                com.maddox.il2.objects.effects.Explosions.generateComicBulb(actor, "OnFire", 12F);
            }
    }

    public void changeEngineEffectBase(int i, com.maddox.il2.engine.Actor actor1)
    {
        for(int j = 0; j < 3; j++)
            if(astateEngineEffects[i][j] != null)
                astateEngineEffects[i][j].pos.changeBase(actor1, null, true);

        aircraft.sfxSmokeState(1, i, false);
    }

    public void explodeEngine(com.maddox.il2.engine.Actor actor1, int i)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(bIsMaster)
        {
            if(!aircraft.isChunkAnyDamageVisible(astateEffectChunks[i + 4]))
                return;
            netToMirrors(3, i, 0);
            doExplodeEngine(i);
        } else
        {
            netToMaster(3, i, 0, actor1);
        }
    }

    private void doExplodeEngine(int i)
    {
        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Engine " + i + " explodes..");
        com.maddox.il2.engine.Eff3DActor.New(actor, actor.findHook("_Engine" + (i + 1) + "Smoke"), null, 1.0F, "3DO/Effects/Fireworks/Tank_Burn.eff", -1F);
        com.maddox.il2.engine.Eff3DActor.New(actor, actor.findHook("_Engine" + (i + 1) + "Smoke"), null, 1.0F, "3DO/Effects/Fireworks/Tank_SmokeBoiling.eff", -1F);
        com.maddox.il2.engine.Eff3DActor.New(actor, actor.findHook("_Engine" + (i + 1) + "Smoke"), null, 1.0F, "3DO/Effects/Fireworks/Tank_Sparks.eff", -1F);
        com.maddox.il2.engine.Eff3DActor.New(actor, actor.findHook("_Engine" + (i + 1) + "Smoke"), null, 1.0F, "3DO/Effects/Fireworks/Tank_SparksP.eff", -1F);
        aircraft.msgCollision(aircraft, astateEffectChunks[i + 4] + "0", astateEffectChunks[i + 4] + "0");
        com.maddox.il2.engine.Actor actor1;
        if(aircraft.getDamager() != null)
            actor1 = aircraft.getDamager();
        else
            actor1 = actor;
        com.maddox.il2.engine.HookNamed hooknamed = new HookNamed((com.maddox.il2.engine.ActorMesh)actor, "_Engine" + (i + 1) + "Smoke");
        com.maddox.il2.engine.Loc loc = new Loc();
        com.maddox.il2.engine.Loc loc1 = new Loc();
        actor.pos.getCurrent(loc);
        hooknamed.computePos(actor, loc, loc1);
        com.maddox.il2.ai.MsgExplosion.send(null, astateEffectChunks[4 + i] + "0", loc1.getPoint(), actor1, 1.248F, 0.026F, 1, 75F);
    }

    public void setEngineStarts(int i)
    {
        if(!bIsMaster)
        {
            return;
        } else
        {
            doSetEngineStarts(i);
            netToMirrors(4, i, 96);
            return;
        }
    }

    public void setEngineRunning(int i)
    {
        if(!bIsMaster)
        {
            return;
        } else
        {
            doSetEngineRunning(i);
            netToMirrors(5, i, 81);
            return;
        }
    }

    public void setEngineStops(int i)
    {
        if(!bIsMaster)
        {
            return;
        } else
        {
            doSetEngineStops(i);
            netToMirrors(6, i, 2);
            return;
        }
    }

    public void setEngineDies(com.maddox.il2.engine.Actor actor1, int i)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(bIsMaster)
        {
            aircraft.setDamager(actor1);
            doSetEngineDies(i);
            netToMirrors(7, i, 77);
        } else
        {
            netToMaster(7, i, 67, actor1);
        }
    }

    public void setEngineStuck(com.maddox.il2.engine.Actor actor1, int i)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(bIsMaster)
        {
            doSetEngineStuck(i);
            aircraft.setDamager(actor1);
            netToMirrors(29, i, 77);
        } else
        {
            netToMaster(29, i, 67, actor1);
        }
    }

    public void setEngineSpecificDamage(com.maddox.il2.engine.Actor actor1, int i, int j)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(bIsMaster)
        {
            aircraft.setDamager(actor1);
            doSetEngineSpecificDamage(i, j);
            netToMirrors(2, i, j);
        } else
        {
            netToMaster(2, i, j, actor1);
        }
    }

    public void setEngineReadyness(com.maddox.il2.engine.Actor actor1, int i, int j)
    {
        if(bIsMaster)
        {
            aircraft.setDamager(actor1);
            doSetEngineReadyness(i, j);
            netToMirrors(25, i, j);
        } else
        {
            netToMaster(25, i, j, actor1);
        }
    }

    public void setEngineStage(com.maddox.il2.engine.Actor actor1, int i, int j)
    {
        if(bIsMaster)
        {
            doSetEngineStage(i, j);
            netToMirrors(26, i, j);
        } else
        {
            netToMaster(26, i, j, actor1);
        }
    }

    public void setEngineCylinderKnockOut(com.maddox.il2.engine.Actor actor1, int i, int j)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(bIsMaster)
        {
            aircraft.setDamager(actor1);
            doSetEngineCylinderKnockOut(i, j);
            netToMirrors(27, i, j);
        } else
        {
            netToMaster(27, i, j, actor1);
        }
    }

    public void setEngineMagnetoKnockOut(com.maddox.il2.engine.Actor actor1, int i, int j)
    {
        if(bIsMaster)
        {
            aircraft.setDamager(actor1);
            doSetEngineMagnetoKnockOut(i, j);
            netToMirrors(28, i, j);
        } else
        {
            netToMaster(28, i, j, actor1);
        }
    }

    private void doSetEngineStarts(int i)
    {
        aircraft.FM.EI.engines[i].doSetEngineStarts();
    }

    private void doSetEngineRunning(int i)
    {
        aircraft.FM.EI.engines[i].doSetEngineRunning();
    }

    private void doSetEngineStops(int i)
    {
        aircraft.FM.EI.engines[i].doSetEngineStops();
    }

    private void doSetEngineDies(int i)
    {
        aircraft.FM.EI.engines[i].doSetEngineDies();
    }

    private void doSetEngineStuck(int i)
    {
        aircraft.FM.EI.engines[i].doSetEngineStuck();
    }

    private void doSetEngineSpecificDamage(int i, int j)
    {
        switch(j)
        {
        case 0: // '\0'
            aircraft.FM.EI.engines[i].doSetKillCompressor();
            break;

        case 3: // '\003'
            aircraft.FM.EI.engines[i].doSetKillPropAngleDevice();
            break;

        case 4: // '\004'
            aircraft.FM.EI.engines[i].doSetKillPropAngleDeviceSpeeds();
            break;

        case 5: // '\005'
            aircraft.FM.EI.engines[i].doSetExtinguisherFire();
            break;

        case 2: // '\002'
            throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unimplemented feature (E.S.D./H.)");

        case 1: // '\001'
            if(aircraft.FM.EI.engines[i].isHasControlThrottle())
                aircraft.FM.EI.engines[i].doSetKillControlThrottle();
            break;

        case 6: // '\006'
            if(aircraft.FM.EI.engines[i].isHasControlProp())
                aircraft.FM.EI.engines[i].doSetKillControlProp();
            break;

        case 7: // '\007'
            if(aircraft.FM.EI.engines[i].isHasControlMix())
                aircraft.FM.EI.engines[i].doSetKillControlMix();
            break;

        default:
            throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Corrupt data in (E.S.D./null)");
        }
    }

    private void doSetEngineReadyness(int i, int j)
    {
        aircraft.FM.EI.engines[i].doSetReadyness(0.01F * (float)j);
    }

    private void doSetEngineStage(int i, int j)
    {
        aircraft.FM.EI.engines[i].doSetStage(j);
    }

    private void doSetEngineCylinderKnockOut(int i, int j)
    {
        aircraft.FM.EI.engines[i].doSetCyliderKnockOut(j);
    }

    private void doSetEngineMagnetoKnockOut(int i, int j)
    {
        aircraft.FM.EI.engines[i].doSetMagnetoKnockOut(j);
    }

    public void doSetEngineExtinguisherVisuals(int i)
    {
        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: Checking '" + astateEffectChunks[i + 4] + "' visibility..");
        boolean flag = aircraft.isChunkAnyDamageVisible(astateEffectChunks[i + 4]);
        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: '" + astateEffectChunks[i + 4] + "' is " + (flag ? "visible" : "invisible") + "..");
        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Firing Extinguisher on Engine " + i + (flag ? ".." : " rejected (missing part).."));
        if(!flag)
        {
            return;
        } else
        {
            com.maddox.il2.engine.Eff3DActor.New(actor, actor.findHook("_Engine" + (i + 1) + "Smoke"), null, 1.0F, "3DO/Effects/Aircraft/EngineExtinguisher1.eff", 3F);
            return;
        }
    }

    public void setSootState(com.maddox.il2.engine.Actor actor1, int i, int j)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(astateSootStates[i] == j)
            return;
        if(bIsMaster)
        {
            if(!doSetSootState(i, j))
                return;
            netToMirrors(8, i, j);
        } else
        {
            netToMaster(8, i, j, actor1);
        }
    }

    public boolean doSetSootState(int i, int j)
    {
        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: Checking '" + astateEffectChunks[i + 4] + "' visibility..");
        boolean flag = aircraft.isChunkAnyDamageVisible(astateEffectChunks[i + 4]);
        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: '" + astateEffectChunks[i + 4] + "' is " + (flag ? "visible" : "invisible") + "..");
        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Stating Engine " + i + " to state " + j + (flag ? ".." : " rejected (missing part).."));
        if(!flag)
        {
            return false;
        } else
        {
            astateSootStates[i] = (byte)j;
            aircraft.doSetSootState(i, j);
            return true;
        }
    }

    public void changeSootEffectBase(int i, com.maddox.il2.engine.Actor actor1)
    {
        for(int j = 0; j < 2; j++)
            if(astateSootEffects[i][j] != null)
                astateSootEffects[i][j].pos.changeBase(actor1, null, true);

    }

    public void setCockpitState(com.maddox.il2.engine.Actor actor1, int i)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(astateCockpitState == i)
            return;
        if(bIsMaster)
        {
            aircraft.setDamager(actor1);
            doSetCockpitState(i);
            netToMirrors(23, 0, i);
        } else
        {
            netToMaster(23, 0, i, actor1);
        }
    }

    public void doSetCockpitState(int i)
    {
        if(astateCockpitState == i)
        {
            return;
        } else
        {
            astateCockpitState = i;
            aircraft.setCockpitState(i);
            return;
        }
    }

    public void setControlsDamage(com.maddox.il2.engine.Actor actor1, int i)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(bIsMaster)
        {
            aircraft.setDamager(actor1);
            if(aircraft.FM.isPlayers() && actor1 != actor && (actor1 instanceof com.maddox.il2.objects.air.Aircraft) && ((com.maddox.il2.objects.air.Aircraft)actor1).isNetPlayer() && !aircraft.FM.isSentControlsOutNote() && !aircraft.FM.isSentBuryNote())
            {
                com.maddox.il2.net.Chat.sendLogRnd(3, "gore_hitctrls", (com.maddox.il2.objects.air.Aircraft)actor1, aircraft);
                aircraft.FM.setSentControlsOutNote(true);
            }
            doSetControlsDamage(i, actor1);
        } else
        {
            netToMaster(21, 0, i, actor1);
        }
    }

    public void setInternalDamage(com.maddox.il2.engine.Actor actor1, int i)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(bIsMaster)
        {
            aircraft.setDamager(actor1);
            doSetInternalDamage(i);
            netToMirrors(22, 0, i);
        } else
        {
            netToMaster(22, 0, i, actor1);
        }
    }

    public void setGliderBoostOn()
    {
        if(!bIsMaster)
        {
            return;
        } else
        {
            netToMirrors(12, 5, 5);
            return;
        }
    }

    public void setGliderBoostOff()
    {
        if(!bIsMaster)
        {
            return;
        } else
        {
            netToMirrors(13, 7, 7);
            return;
        }
    }

    public void setGliderCutCart()
    {
        if(!bIsMaster)
        {
            return;
        } else
        {
            netToMirrors(14, 9, 9);
            return;
        }
    }

    private void doSetControlsDamage(int i, com.maddox.il2.engine.Actor actor1)
    {
        switch(i)
        {
        case 0: // '\0'
            aircraft.FM.CT.resetControl(0);
            if(aircraft.FM.isPlayers() && aircraft.FM.CT.bHasAileronControl)
                com.maddox.il2.game.HUD.log("FailedAroneAU");
            aircraft.FM.CT.bHasAileronControl = false;
            aircraft.FM.setCapableOfACM(false);
            break;

        case 1: // '\001'
            aircraft.FM.CT.resetControl(1);
            if(aircraft.FM.isPlayers() && aircraft.FM.CT.bHasElevatorControl)
                com.maddox.il2.game.HUD.log("FailedVatorAU");
            aircraft.FM.CT.bHasElevatorControl = false;
            aircraft.FM.setCapableOfACM(false);
            if(java.lang.Math.abs(aircraft.FM.Vwld.z) > 7D)
                aircraft.FM.setCapableOfBMP(false, actor1);
            break;

        case 2: // '\002'
            aircraft.FM.CT.resetControl(2);
            if(aircraft.FM.isPlayers() && aircraft.FM.CT.bHasRudderControl)
                com.maddox.il2.game.HUD.log("FailedRudderAU");
            aircraft.FM.CT.bHasRudderControl = false;
            break;

        default:
            throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Corrupt data in (C.A.D./null)");
        }
    }

    private void doSetInternalDamage(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            aircraft.FM.Gears.setHydroOperable(false);
            break;

        case 1: // '\001'
            aircraft.FM.CT.bHasFlapsControl = false;
            break;

        case 2: // '\002'
            aircraft.FM.M.nitro = 0.0F;
            break;

        case 3: // '\003'
            aircraft.FM.Gears.setHydroOperable(false);
            aircraft.FM.Gears.setOperable(false);
            break;

        case 4: // '\004'
            if(aircraft instanceof com.maddox.il2.objects.air.DO_335A0)
            {
                ((com.maddox.il2.objects.air.DO_335A0)aircraft).doKeelShutoff();
                break;
            }
            if(aircraft instanceof com.maddox.il2.objects.air.DO_335V13)
                ((com.maddox.il2.objects.air.DO_335V13)aircraft).doKeelShutoff();
            else
                throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unexpected command (I.K.S.)");
            break;

        case 5: // '\005'
            aircraft.FM.EI.engines[1].setPropReductorValue(0.007F);
            break;

        default:
            throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Corrupt data in (I.D./null)");
        }
    }

    private void doSetGliderBoostOn()
    {
        if(actor instanceof com.maddox.il2.objects.air.Scheme0)
            ((com.maddox.il2.objects.air.Scheme0)actor).doFireBoosters();
        else
        if(actor instanceof com.maddox.il2.objects.air.AR_234B2)
            ((com.maddox.il2.objects.air.AR_234B2)actor).doFireBoosters();
        else
            throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unexpected command (G.B./On not in Nul)");
    }

    private void doSetGliderBoostOff()
    {
        if(actor instanceof com.maddox.il2.objects.air.Scheme0)
            ((com.maddox.il2.objects.air.Scheme0)actor).doCutBoosters();
        else
        if(actor instanceof com.maddox.il2.objects.air.AR_234B2)
            ((com.maddox.il2.objects.air.AR_234B2)actor).doCutBoosters();
        else
            throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unexpected command (G.B./Off not in Nul)");
    }

    private void doSetGliderCutCart()
    {
        if(actor instanceof com.maddox.il2.objects.air.Scheme0)
            ((com.maddox.il2.objects.air.Scheme0)actor).doCutCart();
        else
            throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unexpected command (G.C.C./Off not in Nul)");
    }

    private void doSetCondensateState(boolean flag)
    {
        for(int i = 0; i < aircraft.FM.EI.getNum(); i++)
        {
            if(astateCondensateEffects[i] != null)
                com.maddox.il2.engine.Eff3DActor.finish(astateCondensateEffects[i]);
            astateCondensateEffects[i] = null;
            if(flag)
            {
                java.lang.String s = astateCondensateStrings[1];
                if(s != null)
                    try
                    {
                        astateCondensateEffects[i] = com.maddox.il2.engine.Eff3DActor.New(actor, actor.findHook("_Engine" + (i + 1) + "Smoke"), astateCondensateDispVector, 1.0F, s, -1F);
                    }
                    catch(java.lang.Exception exception)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Above condensate failed - probably a glider..");
                    }
            }
        }

    }

    public void setStallState(boolean flag)
    {
        if(bIsMaster)
        {
            doSetStallState(flag);
            netToMirrors(16, flag ? 1 : 0, flag ? 1 : 0);
        }
    }

    private void doSetStallState(boolean flag)
    {
        for(int i = 0; i < 2; i++)
            if(astateStallEffects[i] != null)
                com.maddox.il2.engine.Eff3DActor.finish(astateStallEffects[i]);

        if(flag)
        {
            java.lang.String s = astateStallStrings[1];
            if(s != null)
            {
                if(bWingTipLExists)
                    astateStallEffects[0] = com.maddox.il2.engine.Eff3DActor.New(actor, actor.findHook("_WingTipL"), null, 1.0F, s, -1F);
                if(bWingTipRExists)
                    astateStallEffects[1] = com.maddox.il2.engine.Eff3DActor.New(actor, actor.findHook("_WingTipR"), null, 1.0F, s, -1F);
            }
        }
    }

    public void setAirShowState(boolean flag)
    {
        if(bShowSmokesOn == flag)
            return;
        if(bIsMaster)
        {
            doSetAirShowState(flag);
            netToMirrors(15, flag ? 1 : 0, flag ? 1 : 0);
        }
    }

    private void doSetAirShowState(boolean flag)
    {
        bShowSmokesOn = flag;
        for(int i = 0; i < 2; i++)
            if(astateAirShowEffects[i] != null)
                com.maddox.il2.engine.Eff3DActor.finish(astateAirShowEffects[i]);

        if(flag)
        {
            if(bWingTipLExists)
                astateAirShowEffects[0] = com.maddox.il2.engine.Eff3DActor.New(actor, actor.findHook("_WingTipL"), null, 1.0F, "3DO/Effects/Aircraft/AirShowRedTSPD.eff", -1F);
            if(bWingTipRExists)
                astateAirShowEffects[1] = com.maddox.il2.engine.Eff3DActor.New(actor, actor.findHook("_WingTipR"), null, 1.0F, "3DO/Effects/Aircraft/AirShowGreenTSPD.eff", -1F);
        }
    }

    public void setNavLightsState(boolean flag)
    {
        if(bNavLightsOn == flag)
            return;
        if(bIsMaster)
        {
            doSetNavLightsState(flag);
            netToMirrors(30, flag ? 1 : 0, flag ? 1 : 0);
        }
    }

    private void doSetNavLightsState(boolean flag)
    {
        for(int i = 0; i < astateNavLightsEffects.length; i++)
        {
            if(astateNavLightsEffects[i] != null)
            {
                com.maddox.il2.engine.Eff3DActor.finish(astateNavLightsEffects[i]);
                astateNavLightsLights[i].light.setEmit(0.0F, 0.0F);
            }
            astateNavLightsEffects[i] = null;
        }

        if(flag)
        {
            com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
            com.maddox.il2.engine.Loc loc1 = new Loc();
            for(int j = 0; j < astateNavLightsEffects.length; j++)
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: Checking '" + astateEffectChunks[j + 12] + "' visibility..");
                boolean flag1 = aircraft.isChunkAnyDamageVisible(astateEffectChunks[j + 12]);
                com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: '" + astateEffectChunks[j + 12] + "' is " + (flag1 ? "visible" : "invisible") + "..");
                if(flag1)
                {
                    bNavLightsOn = flag;
                    java.lang.String s = "3DO/Effects/Fireworks/Flare" + (j <= 1 ? "Red" : j <= 3 ? "Green" : "White") + ".eff";
                    astateNavLightsEffects[j] = com.maddox.il2.engine.Eff3DActor.New(actor, actor.findHook("_NavLight" + j), null, 1.0F, s, -1F, false);
                    astateNavLightsLights[j].light.setEmit(0.35F, 8F);
                }
            }

        } else
        {
            bNavLightsOn = flag;
        }
    }

    public void changeNavLightEffectBase(int i, com.maddox.il2.engine.Actor actor1)
    {
        if(astateNavLightsEffects[i] != null)
        {
            com.maddox.il2.engine.Eff3DActor.finish(astateNavLightsEffects[i]);
            astateNavLightsLights[i].light.setEmit(0.0F, 0.0F);
            astateNavLightsEffects[i] = null;
        }
    }

    public void setLandingLightState(boolean flag)
    {
        if(bLandingLightOn == flag)
            return;
        if(bIsMaster)
        {
            doSetLandingLightState(flag);
            int i = 0;
            for(int j = 0; j < astateLandingLightEffects.length; j++)
                if(astateLandingLightEffects[j] == null)
                    i++;

            if(i == astateLandingLightEffects.length)
            {
                bLandingLightOn = false;
                flag = false;
            }
            netToMirrors(31, flag ? 1 : 0, flag ? 1 : 0);
        }
    }

    private void doSetLandingLightState(boolean flag)
    {
        bLandingLightOn = flag;
        for(int i = 0; i < astateLandingLightEffects.length; i++)
        {
            if(astateLandingLightEffects[i] != null)
            {
                com.maddox.il2.engine.Eff3DActor.finish(astateLandingLightEffects[i]);
                astateLandingLightLights[i].light.setEmit(0.0F, 0.0F);
            }
            astateLandingLightEffects[i] = null;
        }

        if(flag)
        {
            com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
            com.maddox.il2.engine.Loc loc1 = new Loc();
            for(int j = 0; j < astateLandingLightEffects.length; j++)
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: Checking '" + astateEffectChunks[j + 18] + "' visibility..");
                boolean flag1 = aircraft.isChunkAnyDamageVisible(astateEffectChunks[j + 18]);
                com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: '" + astateEffectChunks[j + 18] + "' is " + (flag1 ? "visible" : "invisible") + "..");
                if(flag1)
                {
                    java.lang.String s = "3DO/Effects/Fireworks/FlareWhiteWide.eff";
                    astateLandingLightEffects[j] = com.maddox.il2.engine.Eff3DActor.New(actor, actor.findHook("_LandingLight0" + j), null, 1.0F, s, -1F);
                    astateLandingLightLights[j].light.setEmit(1.2F, 8F);
                }
            }

        }
    }

    public void changeLandingLightEffectBase(int i, com.maddox.il2.engine.Actor actor1)
    {
        if(astateLandingLightEffects[i] != null)
        {
            com.maddox.il2.engine.Eff3DActor.finish(astateLandingLightEffects[i]);
            astateLandingLightLights[i].light.setEmit(0.0F, 0.0F);
            astateLandingLightEffects[i] = null;
            bLandingLightOn = false;
        }
    }

    public void setPilotState(com.maddox.il2.engine.Actor actor1, int i, int j)
    {
        setPilotState(actor1, i, j, true);
    }

    public void setPilotState(com.maddox.il2.engine.Actor actor1, int i, int j, boolean flag)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(j > 95)
            j = 100;
        if(j < 0)
            j = 0;
        if(astatePilotStates[i] >= j)
            return;
        if(bIsMaster)
        {
            aircraft.setDamager(actor1);
            doSetPilotState(i, j, actor1);
            if(flag && aircraft.FM.isPlayers() && i == astatePlayerIndex && !com.maddox.il2.ai.World.isPlayerDead() && actor1 != actor && (actor1 instanceof com.maddox.il2.objects.air.Aircraft) && ((com.maddox.il2.objects.air.Aircraft)actor1).isNetPlayer() && j == 100)
                com.maddox.il2.net.Chat.sendLogRnd(1, "gore_pk", (com.maddox.il2.objects.air.Aircraft)actor1, aircraft);
            if(j > 0 && flag)
                netToMirrors(17, i, j);
        } else
        if(flag)
            netToMaster(17, i, j, actor1);
    }

    public void hitPilot(com.maddox.il2.engine.Actor actor1, int i, int j)
    {
        setPilotState(actor1, i, astatePilotStates[i] + j);
    }

    private void doSetPilotState(int i, int j, com.maddox.il2.engine.Actor actor1)
    {
        if(j > 95)
            j = 100;
        if(com.maddox.il2.ai.World.getPlayerAircraft() == actor && com.maddox.il2.engine.Config.isUSE_RENDER() && (!bIsAboutToBailout || astateBailoutStep <= 11))
        {
            boolean flag = i == astatePlayerIndex && !com.maddox.il2.ai.World.isPlayerDead();
            if(astatePilotStates[i] < 100 && j == 100)
            {
                com.maddox.il2.game.HUD.log(flag ? "PlayerHIT2" : astateHUDPilotHits[astatePilotFunctions[i]] + "HIT2");
                if(flag)
                {
                    com.maddox.il2.ai.World.setPlayerDead();
                    if(com.maddox.il2.game.Mission.isNet())
                        com.maddox.il2.net.Chat.sendLog(0, "gore_killed", (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host(), (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host());
                    if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && !com.maddox.il2.ai.World.isPlayerGunner())
                    {
                        int k = com.maddox.il2.game.Main3D.cur3D().cockpits.length;
                        for(int l = 0; l < k; l++)
                        {
                            com.maddox.il2.objects.air.Cockpit cockpit = com.maddox.il2.game.Main3D.cur3D().cockpits[l];
                            if(com.maddox.il2.engine.Actor.isValid(cockpit) && cockpit.astatePilotIndx() != i && !isPilotDead(cockpit.astatePilotIndx()) && !com.maddox.il2.game.Mission.isNet() && com.maddox.il2.game.AircraftHotKeys.isCockpitRealMode(l))
                                com.maddox.il2.game.AircraftHotKeys.setCockpitRealMode(l, false);
                        }

                    }
                }
            } else
            if(astatePilotStates[i] < 66 && j > 66)
                com.maddox.il2.game.HUD.log(flag ? "PlayerHIT1" : astateHUDPilotHits[astatePilotFunctions[i]] + "HIT1");
            else
            if(astatePilotStates[i] < 25 && j > 25)
                com.maddox.il2.game.HUD.log(flag ? "PlayerHIT0" : astateHUDPilotHits[astatePilotFunctions[i]] + "HIT0");
        }
        byte byte0 = astatePilotStates[i];
        astatePilotStates[i] = (byte)j;
        if(bIsAboutToBailout && astateBailoutStep > i + 11)
        {
            aircraft.doKillPilot(i);
            return;
        }
        if(j > 99)
        {
            aircraft.doKillPilot(i);
            if(com.maddox.il2.ai.World.cur().isHighGore())
                aircraft.doMurderPilot(i);
            if(i == 0)
            {
                if(!bIsAboutToBailout)
                    com.maddox.il2.objects.effects.Explosions.generateComicBulb(actor, "PK", 9F);
                com.maddox.il2.fm.FlightModel flightmodel = aircraft.FM;
                if(flightmodel instanceof com.maddox.il2.ai.air.Maneuver)
                {
                    ((com.maddox.il2.ai.air.Maneuver)flightmodel).set_maneuver(44);
                    ((com.maddox.il2.ai.air.Maneuver)flightmodel).set_task(2);
                    flightmodel.setCapableOfTaxiing(false);
                }
            }
            if(i > 0 && !bIsAboutToBailout)
                com.maddox.il2.objects.effects.Explosions.generateComicBulb(actor, "GunnerDown", 9F);
            com.maddox.il2.ai.EventLog.onPilotKilled(aircraft, i, actor1 != aircraft ? actor1 : null);
        } else
        if(byte0 < 66 && j > 66)
            com.maddox.il2.ai.EventLog.onPilotHeavilyWounded(aircraft, i);
        else
        if(byte0 < 25 && j > 25)
            com.maddox.il2.ai.EventLog.onPilotWounded(aircraft, i);
    }

    private void doRemoveBodyFromPlane(int i)
    {
        aircraft.doRemoveBodyFromPlane(i);
    }

    public float getPilotHealth(int i)
    {
        if(i < 0 || i > aircraft.FM.crew - 1)
            return 0.0F;
        else
            return 1.0F - (float)astatePilotStates[i] * 0.01F;
    }

    public boolean isPilotDead(int i)
    {
        if(i < 0 || i > aircraft.FM.crew - 1)
            return true;
        else
            return astatePilotStates[i] == 100;
    }

    public boolean isPilotParatrooper(int i)
    {
        if(i < 0 || i > aircraft.FM.crew - 1)
            return true;
        else
            return astatePilotStates[i] == 100 && astateBailoutStep > 11 + i;
    }

    public void setJamBullets(int i, int j)
    {
        if(aircraft.FM.CT.Weapons[i] == null || aircraft.FM.CT.Weapons[i].length <= j || aircraft.FM.CT.Weapons[i][j] == null)
            return;
        if(bIsMaster)
        {
            doSetJamBullets(i, j);
            netToMirrors(24, i, j);
        } else
        {
            netToMaster(24, i, j);
        }
    }

    private void doSetJamBullets(int i, int j)
    {
        if(aircraft.FM.CT.Weapons != null && aircraft.FM.CT.Weapons[i] != null && aircraft.FM.CT.Weapons[i][j] != null && aircraft.FM.CT.Weapons[i][j].haveBullets())
        {
            if(actor == com.maddox.il2.ai.World.getPlayerAircraft() && aircraft.FM.CT.Weapons[i][j].haveBullets())
                if(aircraft.FM.CT.Weapons[i][j].bulletMassa() < 0.095F)
                    com.maddox.il2.game.HUD.log(i <= 9 ? "FailedMGun" : "FailedTMGun");
                else
                    com.maddox.il2.game.HUD.log("FailedCannon");
            aircraft.FM.CT.Weapons[i][j].loadBullets(0);
        }
    }

    public boolean hitDaSilk()
    {
        if(!bIsMaster)
            return false;
        if(bIsAboutToBailout)
            return false;
        if(bCheckPlayerAircraft && actor == com.maddox.il2.ai.World.getPlayerAircraft())
            return false;
        if(!bIsEnableToBailout)
            return false;
        bIsAboutToBailout = true;
        com.maddox.il2.fm.FlightModel flightmodel = aircraft.FM;
        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "I've had it, bailing out..");
        com.maddox.il2.objects.effects.Explosions.generateComicBulb(actor, "Bailing", 5F);
        if(flightmodel instanceof com.maddox.il2.ai.air.Maneuver)
        {
            ((com.maddox.il2.ai.air.Maneuver)flightmodel).set_maneuver(44);
            ((com.maddox.il2.ai.air.Maneuver)flightmodel).set_task(2);
            flightmodel.setTakenMortalDamage(true, null);
        }
        return true;
    }

    public void setFlatTopString(com.maddox.il2.engine.Actor actor1, int i)
    {
        if(bIsMaster)
            netToMirrors(36, i, i, actor1);
    }

    private void doSetFlatTopString(com.maddox.il2.engine.Actor actor1, int i)
    {
        if(com.maddox.il2.engine.Actor.isValid(actor1) && (actor1 instanceof com.maddox.il2.objects.ships.BigshipGeneric) && ((com.maddox.il2.objects.ships.BigshipGeneric)actor1).getAirport() != null)
        {
            com.maddox.il2.objects.ships.BigshipGeneric bigshipgeneric = (com.maddox.il2.objects.ships.BigshipGeneric)actor1;
            if(i >= 0 && i < 255)
                bigshipgeneric.forceTowAircraft(aircraft, i);
            else
                bigshipgeneric.requestDetowAircraft(aircraft);
        }
    }

    public void setFMSFX(com.maddox.il2.engine.Actor actor1, int i, int j)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(bIsMaster)
            doSetFMSFX(i, j);
        else
            netToMaster(37, i, j, actor1);
    }

    private void doSetFMSFX(int i, int j)
    {
        aircraft.FM.doRequestFMSFX(i, j);
    }

    public void setWingFold(com.maddox.il2.engine.Actor actor1, int i)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(actor1 != aircraft)
            return;
        if(!aircraft.FM.CT.bHasWingControl)
            return;
        if(aircraft.FM.CT.wingControl == (float)i)
            return;
        if(!bIsMaster)
        {
            return;
        } else
        {
            doSetWingFold(i);
            netToMirrors(38, i, i);
            return;
        }
    }

    private void doSetWingFold(int i)
    {
        aircraft.FM.CT.wingControl = i;
    }

    public void setCockpitDoor(com.maddox.il2.engine.Actor actor1, int i)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(actor1 != aircraft)
            return;
        if(!aircraft.FM.CT.bHasCockpitDoorControl)
            return;
        if(aircraft.FM.CT.cockpitDoorControl == (float)i)
            return;
        if(!bIsMaster)
        {
            return;
        } else
        {
            doSetCockpitDoor(i);
            netToMirrors(39, i, i);
            return;
        }
    }

    private void doSetCockpitDoor(int i)
    {
        aircraft.FM.CT.cockpitDoorControl = i;
        if(i == 0 && (com.maddox.il2.game.Main.cur() instanceof com.maddox.il2.game.Main3D) && aircraft == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.engine.hotkey.HookPilot.current != null)
            com.maddox.il2.engine.hotkey.HookPilot.current.doUp(false);
    }

    public void setArrestor(com.maddox.il2.engine.Actor actor1, int i)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(actor1 != aircraft)
            return;
        if(!aircraft.FM.CT.bHasArrestorControl)
            return;
        if(aircraft.FM.CT.arrestorControl == (float)i)
            return;
        if(!bIsMaster)
        {
            return;
        } else
        {
            doSetArrestor(i);
            netToMirrors(40, i, i);
            return;
        }
    }

    private void doSetArrestor(int i)
    {
        aircraft.FM.CT.arrestorControl = i;
    }

    public void update(float f)
    {
        bCriticalStatePassed = bIsAboveCriticalSpeed != (aircraft.getSpeed(null) > 10D);
        if(bCriticalStatePassed)
        {
            bIsAboveCriticalSpeed = aircraft.FM.getSpeed() > 10F;
            for(int i = 0; i < 4; i++)
                doSetTankState(null, i, astateTankStates[i]);

            for(int j = 0; j < aircraft.FM.EI.getNum(); j++)
                doSetEngineState(null, j, astateEngineStates[j]);

            for(int k = 0; k < aircraft.FM.EI.getNum(); k++)
                doSetOilState(k, astateOilStates[k]);

        }
        bCriticalStatePassed = bIsAboveCondensateAlt != (aircraft.FM.getAltitude() > 7000F);
        if(bCriticalStatePassed)
        {
            bIsAboveCondensateAlt = aircraft.FM.getAltitude() > 7000F;
            doSetCondensateState(bIsAboveCondensateAlt);
        }
        bCriticalStatePassed = bIsOnInadequateAOA != (aircraft.FM.getSpeed() > 17F && aircraft.FM.getAOA() > 15F - aircraft.FM.getAltitude() * 0.001F);
        if(bCriticalStatePassed)
        {
            bIsOnInadequateAOA = aircraft.FM.getSpeed() > 17F && aircraft.FM.getAOA() > 15F - aircraft.FM.getAltitude() * 0.001F;
            setStallState(bIsOnInadequateAOA);
        }
        if(bIsMaster)
        {
            float f1 = 0.0F;
            for(int l = 0; l < 4; l++)
                f1 += astateTankStates[l] * astateTankStates[l];

            aircraft.FM.M.requestFuel(f1 * 0.12F * f);
            for(int i1 = 0; i1 < 4; i1++)
                switch(astateTankStates[i1])
                {
                case 3: // '\003'
                default:
                    break;

                case 1: // '\001'
                    if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.0125F)
                    {
                        repairTank(i1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Tank " + i1 + " protector clothes the hole - leak stops..");
                    }
                    // fall through

                case 2: // '\002'
                    if(aircraft.FM.M.fuel <= 0.0F)
                    {
                        repairTank(i1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Tank " + i1 + " runs out of fuel - leak stops..");
                    }
                    break;

                case 4: // '\004'
                    if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.00333F)
                    {
                        hitTank(aircraft, i1, 1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Tank " + i1 + " catches fire..");
                    }
                    break;

                case 5: // '\005'
                    if(aircraft.FM.getSpeed() > 111F && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.2F)
                    {
                        repairTank(i1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Tank " + i1 + " cuts fire..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.0048F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Tank " + i1 + " fires up to the next stage..");
                        hitTank(aircraft, i1, 1);
                    }
                    if(!(actor instanceof com.maddox.il2.objects.air.Scheme1) || astateTankEffects[i1][0] == null || java.lang.Math.abs(astateTankEffects[i1][0].pos.getRelPoint().y) >= 1.8999999761581421D || astateTankEffects[i1][0].pos.getRelPoint().x <= -2.5999999046325684D || astatePilotStates[0] >= 96)
                        break;
                    hitPilot(actor, 0, 5);
                    if(astatePilotStates[0] < 96)
                        break;
                    hitPilot(actor, 0, 101 - astatePilotStates[0]);
                    if(aircraft.FM.isPlayers() && com.maddox.il2.game.Mission.isNet() && !aircraft.FM.isSentBuryNote())
                        com.maddox.il2.net.Chat.sendLogRnd(3, "gore_burnedcpt", aircraft, null);
                    break;

                case 6: // '\006'
                    if(aircraft.FM.getSpeed() > 140F && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.05F)
                    {
                        repairTank(i1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Tank " + i1 + " cuts fire..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Tank " + i1 + " EXPLODES!.");
                        explodeTank(aircraft, i1);
                    }
                    if(!(actor instanceof com.maddox.il2.objects.air.Scheme1) || astateTankEffects[i1][0] == null || java.lang.Math.abs(astateTankEffects[i1][0].pos.getRelPoint().y) >= 1.8999999761581421D || astateTankEffects[i1][0].pos.getRelPoint().x <= -2.5999999046325684D || astatePilotStates[0] >= 96)
                        break;
                    hitPilot(actor, 0, 7);
                    if(astatePilotStates[0] < 96)
                        break;
                    hitPilot(actor, 0, 101 - astatePilotStates[0]);
                    if(aircraft.FM.isPlayers() && com.maddox.il2.game.Mission.isNet() && !aircraft.FM.isSentBuryNote())
                        com.maddox.il2.net.Chat.sendLogRnd(3, "gore_burnedcpt", aircraft, null);
                    break;
                }

            for(int j1 = 0; j1 < aircraft.FM.EI.getNum(); j1++)
            {
                if(astateEngineStates[j1] > 1)
                {
                    aircraft.FM.EI.engines[j1].setReadyness(actor, aircraft.FM.EI.engines[j1].getReadyness() - (float)astateEngineStates[j1] * 0.00025F * f);
                    if(aircraft.FM.EI.engines[j1].getReadyness() < 0.2F && aircraft.FM.EI.engines[j1].getReadyness() != 0.0F)
                        aircraft.FM.EI.engines[j1].setEngineDies(actor);
                }
                switch(astateEngineStates[j1])
                {
                case 3: // '\003'
                    if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.01F)
                    {
                        hitEngine(aircraft, j1, 1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Engine " + j1 + " catches fire..");
                    }
                    break;

                case 4: // '\004'
                    if(aircraft.FM.getSpeed() > 111F && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.15F)
                    {
                        repairEngine(j1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Engine " + j1 + " cuts fire..");
                    }
                    if((actor instanceof com.maddox.il2.objects.air.Scheme1) && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.06F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Engine 0 detonates and explodes, fatal damage level forced..");
                        aircraft.msgCollision(actor, "CF_D0", "CF_D0");
                    }
                    if((actor instanceof com.maddox.il2.objects.air.Scheme1) && astatePilotStates[0] < 96)
                    {
                        hitPilot(actor, 0, 4);
                        if(astatePilotStates[0] >= 96)
                        {
                            hitPilot(actor, 0, 101 - astatePilotStates[0]);
                            if(aircraft.FM.isPlayers() && com.maddox.il2.game.Mission.isNet() && !aircraft.FM.isSentBuryNote())
                                com.maddox.il2.net.Chat.sendLogRnd(3, "gore_burnedcpt", aircraft, null);
                        }
                    }
                    break;
                }
                if(astateOilStates[j1] > 0)
                    aircraft.FM.EI.engines[j1].setReadyness(aircraft, aircraft.FM.EI.engines[j1].getReadyness() - 0.001875F * f);
            }

            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && !aircraft.FM.CT.saveWeaponControl[3] && (!(actor instanceof com.maddox.il2.objects.air.TypeBomber) || aircraft.FM.isReadyToReturn() || aircraft.FM.isPlayers() && (aircraft.FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)aircraft.FM).isRealMode()))
                aircraft.FM.CT.BayDoorControl = 0.0F;
            bailout();
        } else
        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F && !aircraft.FM.CT.saveWeaponControl[3] && (!(actor instanceof com.maddox.il2.objects.air.TypeBomber) || aircraft.FM.AP.way.curr().Action != 3))
            aircraft.FM.CT.BayDoorControl = 0.0F;
    }

    private void bailout()
    {
        if(bIsAboutToBailout)
            if(astateBailoutStep >= 0 && astateBailoutStep < 2)
            {
                if(aircraft.FM.CT.cockpitDoorControl > 0.5F && aircraft.FM.CT.getCockpitDoor() > 0.5F)
                {
                    astateBailoutStep = 11;
                    doRemoveBlisters();
                } else
                {
                    astateBailoutStep = 2;
                }
            } else
            if(astateBailoutStep >= 2 && astateBailoutStep <= 3)
            {
                switch(astateBailoutStep)
                {
                case 2: // '\002'
                    if(aircraft.FM.CT.cockpitDoorControl < 0.5F)
                        doRemoveBlister1();
                    break;

                case 3: // '\003'
                    doRemoveBlisters();
                    break;
                }
                if(bIsMaster)
                    netToMirrors(20, astateBailoutStep, 1);
                astateBailoutStep++;
                if(astateBailoutStep == 3 && (actor instanceof com.maddox.il2.objects.air.P_39))
                    astateBailoutStep++;
                if(astateBailoutStep == 4)
                    astateBailoutStep = 11;
            } else
            if(astateBailoutStep >= 11 && astateBailoutStep <= 19)
            {
                float f = aircraft.FM.getSpeed();
                float f1 = (float)aircraft.FM.Loc.z;
                float f2 = 140F;
                if((aircraft instanceof com.maddox.il2.objects.air.HE_162) || (aircraft instanceof com.maddox.il2.objects.air.GO_229) || (aircraft instanceof com.maddox.il2.objects.air.ME_262HGII) || (aircraft instanceof com.maddox.il2.objects.air.DO_335))
                    f2 = 9999.9F;
                if(com.maddox.il2.fm.Pitot.Indicator(f1, f) < f2 && aircraft.FM.getOverload() < 2.0F || !bIsMaster)
                {
                    byte byte0 = astateBailoutStep;
                    if(bIsMaster)
                        netToMirrors(20, astateBailoutStep, 1);
                    astateBailoutStep++;
                    if(byte0 == 11)
                    {
                        aircraft.FM.setTakenMortalDamage(true, null);
                        if((aircraft.FM instanceof com.maddox.il2.ai.air.Maneuver) && ((com.maddox.il2.ai.air.Maneuver)aircraft.FM).get_maneuver() != 44)
                        {
                            com.maddox.il2.ai.World.cur();
                            if(actor != com.maddox.il2.ai.World.getPlayerAircraft())
                                ((com.maddox.il2.ai.air.Maneuver)aircraft.FM).set_maneuver(44);
                        }
                    }
                    if(astatePilotStates[byte0 - 11] < 99)
                    {
                        doRemoveBodyFromPlane(byte0 - 10);
                        if(byte0 == 11)
                        {
                            if(aircraft instanceof com.maddox.il2.objects.air.HE_162)
                            {
                                ((com.maddox.il2.objects.air.HE_162)aircraft).doEjectCatapult();
                                astateBailoutStep = 51;
                                aircraft.FM.setTakenMortalDamage(true, null);
                                aircraft.FM.CT.WeaponControl[0] = false;
                                aircraft.FM.CT.WeaponControl[1] = false;
                                astateBailoutStep = -1;
                                return;
                            }
                            if(aircraft instanceof com.maddox.il2.objects.air.GO_229)
                            {
                                ((com.maddox.il2.objects.air.GO_229)aircraft).doEjectCatapult();
                                astateBailoutStep = 51;
                                aircraft.FM.setTakenMortalDamage(true, null);
                                aircraft.FM.CT.WeaponControl[0] = false;
                                aircraft.FM.CT.WeaponControl[1] = false;
                                astateBailoutStep = -1;
                                return;
                            }
                            if(aircraft instanceof com.maddox.il2.objects.air.DO_335)
                            {
                                ((com.maddox.il2.objects.air.DO_335)aircraft).doEjectCatapult();
                                astateBailoutStep = 51;
                                aircraft.FM.setTakenMortalDamage(true, null);
                                aircraft.FM.CT.WeaponControl[0] = false;
                                aircraft.FM.CT.WeaponControl[1] = false;
                                astateBailoutStep = -1;
                                return;
                            }
                            if(aircraft instanceof com.maddox.il2.objects.air.ME_262HGII)
                            {
                                ((com.maddox.il2.objects.air.ME_262HGII)aircraft).doEjectCatapult();
                                astateBailoutStep = 51;
                                aircraft.FM.setTakenMortalDamage(true, null);
                                aircraft.FM.CT.WeaponControl[0] = false;
                                aircraft.FM.CT.WeaponControl[1] = false;
                                astateBailoutStep = -1;
                                return;
                            }
                        }
                        setPilotState(aircraft, byte0 - 11, 100, false);
                        if(!actor.isNet() || actor.isNetMaster())
                        {
                            try
                            {
                                com.maddox.il2.engine.Hook hook = actor.findHook("_ExternalBail0" + (byte0 - 10));
                                if(hook != null)
                                {
                                    com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, com.maddox.il2.ai.World.Rnd().nextFloat(-45F, 45F), 0.0F, 0.0F);
                                    hook.computePos(actor, actor.pos.getAbs(), loc);
                                    com.maddox.il2.objects.air.Paratrooper paratrooper = new Paratrooper(actor, actor.getArmy(), byte0 - 11, loc, aircraft.FM.Vwld);
                                    aircraft.FM.setTakenMortalDamage(true, null);
                                    if(byte0 == 11)
                                    {
                                        aircraft.FM.CT.WeaponControl[0] = false;
                                        aircraft.FM.CT.WeaponControl[1] = false;
                                    }
                                    if(byte0 > 10 && byte0 <= 19)
                                        com.maddox.il2.ai.EventLog.onBailedOut(aircraft, byte0 - 11);
                                }
                            }
                            catch(java.lang.Exception exception) { }
                            finally { }
                            if(astateBailoutStep == 19 && actor == com.maddox.il2.ai.World.getPlayerAircraft() && !com.maddox.il2.ai.World.isPlayerGunner() && aircraft.FM.brakeShoe)
                                com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current() + 1000L, aircraft);
                        }
                    }
                }
            }
    }

    private final void doRemoveBlister1()
    {
        if(aircraft.hierMesh().chunkFindCheck("Blister1_D0") != -1 && getPilotHealth(0) > 0.0F)
        {
            aircraft.hierMesh().hideSubTrees("Blister1_D0");
            com.maddox.il2.objects.Wreckage wreckage = new Wreckage((com.maddox.il2.engine.ActorHMesh)actor, aircraft.hierMesh().chunkFind("Blister1_D0"));
            wreckage.collide(false);
            com.maddox.JGP.Vector3d vector3d = new Vector3d();
            vector3d.set(aircraft.FM.Vwld);
            wreckage.setSpeed(vector3d);
        }
    }

    private final void doRemoveBlisters()
    {
        for(int i = 2; i < 10; i++)
            if(aircraft.hierMesh().chunkFindCheck("Blister" + i + "_D0") != -1 && getPilotHealth(i - 1) > 0.0F)
            {
                aircraft.hierMesh().hideSubTrees("Blister" + i + "_D0");
                com.maddox.il2.objects.Wreckage wreckage = new Wreckage((com.maddox.il2.engine.ActorHMesh)actor, aircraft.hierMesh().chunkFind("Blister" + i + "_D0"));
                wreckage.collide(false);
                com.maddox.JGP.Vector3d vector3d = new Vector3d();
                vector3d.set(aircraft.FM.Vwld);
                wreckage.setSpeed(vector3d);
            }

    }

    public void netUpdate(boolean flag, boolean flag1, com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        if(flag1)
        {
            if(flag)
            {
                int i = netmsginput.readUnsignedByte();
                int k = netmsginput.readUnsignedByte();
                int i1 = netmsginput.readUnsignedByte();
                com.maddox.il2.engine.Actor actor1 = null;
                if(netmsginput.available() > 0)
                {
                    com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                    if(netobj != null)
                        actor1 = (com.maddox.il2.engine.Actor)netobj.superObj();
                }
                switch(i)
                {
                case 1: // '\001'
                    setEngineState(actor1, k, i1);
                    break;

                case 2: // '\002'
                    setEngineSpecificDamage(actor1, k, i1);
                    break;

                case 3: // '\003'
                    explodeEngine(actor1, k);
                    break;

                case 4: // '\004'
                    throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unexpected command (E.S.)");

                case 5: // '\005'
                    throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unexpected command (E.R.)");

                case 6: // '\006'
                    throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unexpected command (E.E.)");

                case 7: // '\007'
                    setEngineDies(actor1, k);
                    break;

                case 29: // '\035'
                    setEngineStuck(actor1, k);
                    break;

                case 27: // '\033'
                    setEngineCylinderKnockOut(actor1, k, i1);
                    break;

                case 28: // '\034'
                    setEngineMagnetoKnockOut(actor1, k, i1);
                    break;

                case 8: // '\b'
                    setSootState(actor1, k, i1);
                    break;

                case 25: // '\031'
                    setEngineReadyness(actor1, k, i1);
                    break;

                case 26: // '\032'
                    throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unexpected command (EN.ST.)");

                case 9: // '\t'
                    setTankState(actor1, k, i1);
                    break;

                case 10: // '\n'
                    explodeTank(actor1, k);
                    break;

                case 11: // '\013'
                    setOilState(actor1, k, i1);
                    break;

                case 12: // '\f'
                    throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unexpected command (G.B./On)");

                case 13: // '\r'
                    throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unexpected command (G.B./Off)");

                case 14: // '\016'
                    throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unexpected command (G.C.C./Off)");

                case 15: // '\017'
                    throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unexpected command (A.S.S.)");

                case 30: // '\036'
                    throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unexpected command (NV.LT.ST.)");

                case 31: // '\037'
                    throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unexpected command (LA.LT.ST.)");

                case 16: // '\020'
                    throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unexpected command (W.T.S.)");

                case 17: // '\021'
                    setPilotState(actor1, k, i1);
                    break;

                case 18: // '\022'
                    throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unimplemented feature (K.P.)");

                case 19: // '\023'
                    throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unimplemented feature (H.S.)");

                case 20: // '\024'
                    throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unexpected command (B.O.)");

                case 21: // '\025'
                    setControlsDamage(actor1, i1);
                    break;

                case 22: // '\026'
                    setInternalDamage(actor1, i1);
                    break;

                case 23: // '\027'
                    setCockpitState(actor1, i1);
                    break;

                case 24: // '\030'
                    setJamBullets(k, i1);
                    break;

                case 34: // '"'
                    ((com.maddox.il2.objects.air.TypeDockable)aircraft).typeDockableRequestAttach(actor1, k, true);
                    break;

                case 35: // '#'
                    ((com.maddox.il2.objects.air.TypeDockable)aircraft).typeDockableRequestDetach(actor1, k, true);
                    break;

                case 32: // ' '
                    ((com.maddox.il2.objects.air.TypeDockable)aircraft).typeDockableRequestAttach(actor1, k, i1 > 0);
                    break;

                case 33: // '!'
                    ((com.maddox.il2.objects.air.TypeDockable)aircraft).typeDockableRequestDetach(actor1, k, i1 > 0);
                    break;

                case 37: // '%'
                    setFMSFX(actor1, k, i1);
                    break;
                }
            } else
            {
                aircraft.net.postTo(aircraft.net.masterChannel(), new NetMsgGuaranted(netmsginput, netmsginput.available() <= 3 ? 0 : 1));
            }
        } else
        {
            if(aircraft.net.isMirrored())
                aircraft.net.post(new NetMsgGuaranted(netmsginput, netmsginput.available() <= 3 ? 0 : 1));
            int j = netmsginput.readUnsignedByte();
            int l = netmsginput.readUnsignedByte();
            int j1 = netmsginput.readUnsignedByte();
            com.maddox.il2.engine.Actor actor2 = null;
            if(netmsginput.available() > 0)
            {
                com.maddox.rts.NetObj netobj1 = netmsginput.readNetObj();
                if(netobj1 != null)
                    actor2 = (com.maddox.il2.engine.Actor)netobj1.superObj();
            }
            switch(j)
            {
            case 32: // ' '
            case 33: // '!'
            default:
                break;

            case 1: // '\001'
                doSetEngineState(actor2, l, j1);
                break;

            case 2: // '\002'
                doSetEngineSpecificDamage(l, j1);
                break;

            case 3: // '\003'
                doExplodeEngine(l);
                break;

            case 4: // '\004'
                doSetEngineStarts(l);
                break;

            case 5: // '\005'
                doSetEngineRunning(l);
                break;

            case 6: // '\006'
                doSetEngineStops(l);
                break;

            case 7: // '\007'
                doSetEngineDies(l);
                break;

            case 29: // '\035'
                doSetEngineStuck(l);
                break;

            case 27: // '\033'
                doSetEngineCylinderKnockOut(l, j1);
                break;

            case 28: // '\034'
                doSetEngineMagnetoKnockOut(l, j1);
                break;

            case 8: // '\b'
                doSetSootState(l, j1);
                break;

            case 25: // '\031'
                doSetEngineReadyness(l, j1);
                break;

            case 26: // '\032'
                doSetEngineStage(l, j1);
                break;

            case 9: // '\t'
                doSetTankState(actor2, l, j1);
                break;

            case 10: // '\n'
                doExplodeTank(l);
                break;

            case 11: // '\013'
                doSetOilState(l, j1);
                break;

            case 12: // '\f'
                if(j1 == 5)
                    doSetGliderBoostOn();
                else
                    throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Corrupt data in signal (G.S.B./On)");
                break;

            case 13: // '\r'
                if(j1 == 7)
                    doSetGliderBoostOff();
                else
                    throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Corrupt data in signal (G.S.B./Off)");
                break;

            case 14: // '\016'
                if(j1 == 9)
                    doSetGliderCutCart();
                else
                    throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Corrupt data in signal (G.C.C./Off)");
                break;

            case 15: // '\017'
                doSetAirShowState(j1 == 1);
                break;

            case 30: // '\036'
                doSetNavLightsState(j1 == 1);
                break;

            case 31: // '\037'
                doSetLandingLightState(j1 == 1);
                break;

            case 16: // '\020'
                doSetStallState(j1 == 1);
                break;

            case 17: // '\021'
                doSetPilotState(l, j1, actor2);
                break;

            case 18: // '\022'
                throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unimplemented signal (K.P.)");

            case 19: // '\023'
                throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unimplemented signal (H.S.)");

            case 20: // '\024'
                bIsAboutToBailout = true;
                astateBailoutStep = (byte)l;
                bailout();
                break;

            case 21: // '\025'
                throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Uniexpected signal (C.A.D.)");

            case 22: // '\026'
                doSetInternalDamage(j1);
                break;

            case 23: // '\027'
                doSetCockpitState(j1);
                break;

            case 24: // '\030'
                doSetJamBullets(l, j1);
                break;

            case 34: // '"'
                ((com.maddox.il2.objects.air.TypeDockable)aircraft).typeDockableDoAttachToDrone(actor2, l);
                break;

            case 35: // '#'
                ((com.maddox.il2.objects.air.TypeDockable)aircraft).typeDockableDoDetachFromDrone(l);
                break;

            case 36: // '$'
                doSetFlatTopString(actor2, j1);
                break;

            case 37: // '%'
                doSetFMSFX(l, j1);
                break;

            case 38: // '&'
                doSetWingFold(j1);
                break;

            case 39: // '\''
                doSetCockpitDoor(j1);
                break;

            case 40: // '('
                doSetArrestor(j1);
                break;
            }
        }
    }

    public void netReplicate(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        int i1;
        if(aircraft instanceof com.maddox.il2.objects.air.FW_190A8MSTL)
            i1 = 1;
        else
            i1 = aircraft.FM.EI.getNum();
        for(int i = 0; i < i1; i++)
        {
            aircraft.FM.EI.engines[i].replicateToNet(netmsgguaranted);
            netmsgguaranted.writeByte(astateEngineStates[i]);
        }

        for(int j = 0; j < 4; j++)
            netmsgguaranted.writeByte(astateTankStates[j]);

        for(int k = 0; k < i1; k++)
            netmsgguaranted.writeByte(astateOilStates[k]);

        netmsgguaranted.writeByte((bShowSmokesOn ? 1 : 0) | (bNavLightsOn ? 2 : 0) | (bLandingLightOn ? 4 : 0));
        netmsgguaranted.writeByte(astateCockpitState);
        netmsgguaranted.writeByte(astateBailoutStep);
        if(aircraft instanceof com.maddox.il2.objects.air.TypeBomber)
            ((com.maddox.il2.objects.air.TypeBomber)aircraft).typeBomberReplicateToNet(netmsgguaranted);
        if(aircraft instanceof com.maddox.il2.objects.air.TypeDockable)
            ((com.maddox.il2.objects.air.TypeDockable)aircraft).typeDockableReplicateToNet(netmsgguaranted);
        if(aircraft.FM.CT.bHasWingControl)
        {
            netmsgguaranted.writeByte((int)aircraft.FM.CT.wingControl);
            netmsgguaranted.writeByte((int)(aircraft.FM.CT.getWing() * 255F));
        }
        if(aircraft.FM.CT.bHasCockpitDoorControl)
            netmsgguaranted.writeByte((int)aircraft.FM.CT.cockpitDoorControl);
        netmsgguaranted.writeByte(bIsEnableToBailout ? 1 : 0);
        if(aircraft.FM.CT.bHasArrestorControl)
        {
            netmsgguaranted.writeByte((int)aircraft.FM.CT.arrestorControl);
            netmsgguaranted.writeByte((int)(aircraft.FM.CT.getArrestor() * 255F));
        }
        for(int l = 0; l < i1; l++)
            netmsgguaranted.writeByte(astateSootStates[l]);

    }

    public void netFirstUpdate(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        int l2;
        if(aircraft instanceof com.maddox.il2.objects.air.FW_190A8MSTL)
            l2 = 1;
        else
            l2 = aircraft.FM.EI.getNum();
        for(int i = 0; i < l2; i++)
        {
            aircraft.FM.EI.engines[i].replicateFromNet(netmsginput);
            int k1 = netmsginput.readUnsignedByte();
            doSetEngineState(null, i, k1);
        }

        for(int j = 0; j < 4; j++)
        {
            int l1 = netmsginput.readUnsignedByte();
            doSetTankState(null, j, l1);
        }

        for(int k = 0; k < l2; k++)
        {
            int i2 = netmsginput.readUnsignedByte();
            doSetOilState(k, i2);
        }

        int j2 = netmsginput.readUnsignedByte();
        doSetAirShowState((j2 & 1) != 0);
        doSetNavLightsState((j2 & 2) != 0);
        doSetLandingLightState((j2 & 4) != 0);
        j2 = netmsginput.readUnsignedByte();
        doSetCockpitState(j2);
        j2 = netmsginput.readUnsignedByte();
        if(j2 != 0)
        {
            bIsAboutToBailout = true;
            astateBailoutStep = (byte)j2;
            for(int l = 1; l <= java.lang.Math.min(astateBailoutStep, 3); l++)
                if(aircraft.hierMesh().chunkFindCheck("Blister" + (l - 1) + "_D0") != -1)
                    aircraft.hierMesh().hideSubTrees("Blister" + (l - 1) + "_D0");

            if(astateBailoutStep >= 11 && astateBailoutStep <= 20)
            {
                int i3 = astateBailoutStep;
                if(astateBailoutStep == 20)
                    i3 = 19;
                i3 -= 11;
                for(int i1 = 0; i1 <= i3; i1++)
                    doRemoveBodyFromPlane(i1 + 1);

            }
        }
        if(aircraft instanceof com.maddox.il2.objects.air.TypeBomber)
            ((com.maddox.il2.objects.air.TypeBomber)aircraft).typeBomberReplicateFromNet(netmsginput);
        if(aircraft instanceof com.maddox.il2.objects.air.TypeDockable)
            ((com.maddox.il2.objects.air.TypeDockable)aircraft).typeDockableReplicateFromNet(netmsginput);
        if(netmsginput.available() == 0)
            return;
        if(aircraft.FM.CT.bHasWingControl)
        {
            aircraft.FM.CT.wingControl = netmsginput.readUnsignedByte();
            aircraft.FM.CT.forceWing((float)netmsginput.readUnsignedByte() / 255F);
            aircraft.wingfold_ = aircraft.FM.CT.getWing();
        }
        if(netmsginput.available() == 0)
            return;
        if(aircraft.FM.CT.bHasCockpitDoorControl)
        {
            aircraft.FM.CT.cockpitDoorControl = netmsginput.readUnsignedByte();
            aircraft.FM.CT.forceCockpitDoor(aircraft.FM.CT.cockpitDoorControl);
        }
        if(netmsginput.available() == 0)
            return;
        bIsEnableToBailout = netmsginput.readUnsignedByte() == 1;
        if(netmsginput.available() == 0)
            return;
        if(aircraft.FM.CT.bHasArrestorControl)
        {
            aircraft.FM.CT.arrestorControl = netmsginput.readUnsignedByte();
            aircraft.FM.CT.forceArrestor((float)netmsginput.readUnsignedByte() / 255F);
            aircraft.arrestor_ = aircraft.FM.CT.getArrestor();
        }
        if(netmsginput.available() == 0)
            return;
        for(int j1 = 0; j1 < l2; j1++)
        {
            int k2 = netmsginput.readUnsignedByte();
            doSetSootState(j1, k2);
        }

    }

    private void netToMaster(int i, int j, int k)
    {
        netToMaster(i, j, k, null);
    }

    public void netToMaster(int i, int j, int k, com.maddox.il2.engine.Actor actor1)
    {
        if(!bIsMaster)
        {
            if(!aircraft.netNewAState_isEnable(true))
                return;
            if(itemsToMaster == null)
                itemsToMaster = new com.maddox.il2.fm.Item[41];
            if(sendedMsg(itemsToMaster, i, j, k, actor1))
                return;
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = aircraft.netNewAStateMsg(true);
                if(netmsgguaranted != null)
                {
                    netmsgguaranted.writeByte((byte)i);
                    netmsgguaranted.writeByte((byte)j);
                    netmsgguaranted.writeByte((byte)k);
                    com.maddox.il2.engine.ActorNet actornet = null;
                    if(com.maddox.il2.engine.Actor.isValid(actor1))
                        actornet = actor1.net;
                    if(actornet != null)
                        netmsgguaranted.writeNetObj(actornet);
                    aircraft.netSendAStateMsg(true, netmsgguaranted);
                    return;
                }
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        }
    }

    private void netToMirrors(int i, int j, int k)
    {
        netToMirrors(i, j, k, null);
    }

    public void netToMirrors(int i, int j, int k, com.maddox.il2.engine.Actor actor1)
    {
        if(!aircraft.netNewAState_isEnable(false))
            return;
        if(itemsToMirrors == null)
            itemsToMirrors = new com.maddox.il2.fm.Item[41];
        if(sendedMsg(itemsToMirrors, i, j, k, actor1))
            return;
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = aircraft.netNewAStateMsg(false);
            if(netmsgguaranted != null)
            {
                netmsgguaranted.writeByte((byte)i);
                netmsgguaranted.writeByte((byte)j);
                netmsgguaranted.writeByte((byte)k);
                com.maddox.il2.engine.ActorNet actornet = null;
                if(com.maddox.il2.engine.Actor.isValid(actor1))
                    actornet = actor1.net;
                if(actornet != null)
                    netmsgguaranted.writeNetObj(actornet);
                aircraft.netSendAStateMsg(false, netmsgguaranted);
                return;
            }
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private boolean sendedMsg(com.maddox.il2.fm.Item aitem[], int i, int j, int k, com.maddox.il2.engine.Actor actor1)
    {
        if(i < 0 || i >= aitem.length)
            return false;
        com.maddox.il2.fm.Item item = aitem[i];
        if(item == null)
        {
            item = new Item();
            item.set(j, k, actor1);
            aitem[i] = item;
            return false;
        }
        if(item.equals(j, k, actor1))
        {
            return true;
        } else
        {
            item.set(j, k, actor1);
            return false;
        }
    }

    private static final float astateEffectCriticalSpeed = 10F;
    private static final float astateCondensateCriticalAlt = 7000F;
    private static final com.maddox.il2.engine.Loc astateCondensateDispVector = new Loc(15D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    public static final int _AS_RESERVED = 0;
    public static final int _AS_ENGINE_STATE = 1;
    public static final int _AS_ENGINE_SPECIFIC_DMG = 2;
    public static final int _AS_ENGINE_EXPLODES = 3;
    public static final int _AS_ENGINE_STARTS = 4;
    public static final int _AS_ENGINE_RUNS = 5;
    public static final int _AS_ENGINE_STOPS = 6;
    public static final int _AS_ENGINE_DIES = 7;
    public static final int _AS_ENGINE_SOOT_POWERS = 8;
    public static final int _AS_ENGINE_READYNESS = 25;
    public static final int _AS_ENGINE_STAGE = 26;
    public static final int _AS_ENGINE_CYL_KNOCKOUT = 27;
    public static final int _AS_ENGINE_MAG_KNOCKOUT = 28;
    public static final int _AS_ENGINE_STUCK = 29;
    public static final int _AS_TANK_STATE = 9;
    public static final int _AS_TANK_EXPLODES = 10;
    public static final int _AS_OIL_STATE = 11;
    public static final int _AS_GLIDER_BOOSTER = 12;
    public static final int _AS_GLIDER_BOOSTOFF = 13;
    public static final int _AS_GLIDER_CUTCART = 14;
    public static final int _AS_AIRSHOW_SMOKES_STATE = 15;
    public static final int _AS_WINGTIP_SMOKES_STATE = 16;
    public static final int _AS_PILOT_STATE = 17;
    public static final int _AS_KILLPILOT = 18;
    public static final int _AS_HEADSHOT = 19;
    public static final int _AS_BAILOUT = 20;
    public static final int _AS_CONTROLS_HURT = 21;
    public static final int _AS_INTERNALS_HURT = 22;
    public static final int _AS_COCKPIT_STATE_BYTE = 23;
    public static final int _AS_JAM_BULLETS = 24;
    public static final int _AS_NAVIGATION_LIGHTS_STATE = 30;
    public static final int _AS_LANDING_LIGHT_STATE = 31;
    public static final int _AS_TYPEDOCKABLE_REQ_ATTACHTODRONE = 32;
    public static final int _AS_TYPEDOCKABLE_REQ_DETACHFROMDRONE = 33;
    public static final int _AS_TYPEDOCKABLE_FORCE_ATTACHTODRONE = 34;
    public static final int _AS_TYPEDOCKABLE_FORCE_DETACHFROMDRONE = 35;
    public static final int _AS_FLATTOP_FORCESTRING = 36;
    public static final int _AS_FMSFX = 37;
    public static final int _AS_WINGFOLD = 38;
    public static final int _AS_COCKPITDOOR = 39;
    public static final int _AS_ARRESTOR = 40;
    public static final int _AS_COUNT_CODES = 41;
    public static final int _AS_COCKPIT_GLASS = 1;
    public static final int _AS_COCKPIT_ARMORGLASS = 2;
    public static final int _AS_COCKPIT_LEFT1 = 4;
    public static final int _AS_COCKPIT_LEFT2 = 8;
    public static final int _AS_COCKPIT_RIGHT1 = 16;
    public static final int _AS_COCKPIT_RIGHT2 = 32;
    public static final int _AS_COCKPIT_INSTRUMENTS = 64;
    public static final int _AS_COCKPIT_OIL = 128;
    public static final int _ENGINE_SPECIFIC_BOOSTER = 0;
    public static final int _ENGINE_SPECIFIC_THROTTLECTRL = 1;
    public static final int _ENGINE_SPECIFIC_HEATER = 2;
    public static final int _ENGINE_SPECIFIC_ANGLER = 3;
    public static final int _ENGINE_SPECIFIC_ANGLERSPEEDS = 4;
    public static final int _ENGINE_SPECIFIC_EXTINGUISHER = 5;
    public static final int _ENGINE_SPECIFIC_PROPCTRL = 6;
    public static final int _ENGINE_SPECIFIC_MIXCTRL = 7;
    public static final int _CONTROLS_AILERONS = 0;
    public static final int _CONTROLS_ELEVATORS = 1;
    public static final int _CONTROLS_RUDDERS = 2;
    public static final int _INTERNALS_HYDRO_OFFLINE = 0;
    public static final int _INTERNALS_PNEUMO_OFFLINE = 1;
    public static final int _INTERNALS_MW50_OFFLINE = 2;
    public static final int _INTERNALS_GEAR_STUCK = 3;
    public static final int _INTERNALS_KEEL_SHUTOFF = 4;
    public static final int _INTERNALS_SHAFT_SHUTOFF = 5;
    private static final java.lang.String astateOilStrings[] = {
        "3DO/Effects/Aircraft/BlackMediumSPD.eff", "3DO/Effects/Aircraft/BlackMediumTSPD.eff", null, null
    };
    private static final java.lang.String astateTankStrings[] = {
        null, null, null, "3DO/Effects/Aircraft/RedLeakTSPD.eff", null, null, "3DO/Effects/Aircraft/RedLeakTSPD.eff", null, null, "3DO/Effects/Aircraft/BlackMediumSPD.eff", 
        "3DO/Effects/Aircraft/BlackMediumTSPD.eff", null, "3DO/Effects/Aircraft/BlackHeavySPD.eff", "3DO/Effects/Aircraft/BlackHeavyTSPD.eff", null, "3DO/Effects/Aircraft/FireSPD.eff", "3DO/Effects/Aircraft/BlackHeavySPD.eff", "3DO/Effects/Aircraft/BlackHeavyTSPD.eff", "3DO/Effects/Aircraft/FireSPD.eff", "3DO/Effects/Aircraft/BlackHeavySPD.eff", 
        "3DO/Effects/Aircraft/BlackHeavyTSPD.eff", null, null, null, "3DO/Effects/Aircraft/RedLeakGND.eff", null, null, "3DO/Effects/Aircraft/RedLeakGND.eff", null, null, 
        "3DO/Effects/Aircraft/BlackMediumGND.eff", null, null, "3DO/Effects/Aircraft/BlackHeavyGND.eff", null, null, "3DO/Effects/Aircraft/FireGND.eff", "3DO/Effects/Aircraft/BlackHeavyGND.eff", null, "3DO/Effects/Aircraft/FireGND.eff", 
        "3DO/Effects/Aircraft/BlackHeavyGND.eff", null
    };
    private static final java.lang.String astateEngineStrings[] = {
        null, null, null, "3DO/Effects/Aircraft/GraySmallSPD.eff", "3DO/Effects/Aircraft/GraySmallTSPD.eff", null, "3DO/Effects/Aircraft/BlackMediumSPD.eff", "3DO/Effects/Aircraft/BlackMediumTSPD.eff", null, "3DO/Effects/Aircraft/BlackHeavySPD.eff", 
        "3DO/Effects/Aircraft/BlackHeavyTSPD.eff", null, "3DO/Effects/Aircraft/FireSPD.eff", "3DO/Effects/Aircraft/BlackHeavySPD.eff", "3DO/Effects/Aircraft/BlackHeavyTSPD.eff", null, null, null, "3DO/Effects/Aircraft/GraySmallGND.eff", null, 
        null, "3DO/Effects/Aircraft/BlackMediumGND.eff", null, null, "3DO/Effects/Aircraft/BlackHeavyGND.eff", null, null, "3DO/Effects/Aircraft/FireGND.eff", "3DO/Effects/Aircraft/BlackHeavyGND.eff", null
    };
    private static final java.lang.String astateCondensateStrings[] = {
        null, "3DO/Effects/Aircraft/CondensateTSPD.eff"
    };
    private static final java.lang.String astateStallStrings[] = {
        null, "3DO/Effects/Aircraft/StallTSPD.eff"
    };
    public static final java.lang.String astateHUDPilotHits[] = {
        "Player", "Pilot", "CPilot", "NGunner", "TGunner", "WGunner", "VGunner", "RGunner", "EngMas", "BombMas", 
        "RadMas", "ObsMas"
    };
    private static boolean bCriticalStatePassed = false;
    private boolean bIsAboveCriticalSpeed;
    private boolean bIsAboveCondensateAlt;
    private boolean bIsBeyondSootPower[] = {
        false, false, false, false
    };
    private boolean bIsOnInadequateAOA;
    public boolean bShowSmokesOn;
    public boolean bNavLightsOn;
    public boolean bLandingLightOn;
    public boolean bWingTipLExists;
    public boolean bWingTipRExists;
    private boolean bIsMaster;
    public com.maddox.il2.engine.Actor actor;
    public com.maddox.il2.objects.air.Aircraft aircraft;
    public byte astatePilotStates[];
    public byte astatePilotFunctions[] = {
        1, 7, 7, 7, 7, 7, 7, 7, 7
    };
    public int astatePlayerIndex;
    public boolean bIsAboutToBailout;
    public boolean bIsEnableToBailout;
    public byte astateBailoutStep;
    public int astateCockpitState;
    public byte astateOilStates[] = {
        0, 0, 0, 0, 0, 0, 0, 0
    };
    private com.maddox.il2.engine.Eff3DActor astateOilEffects[][] = {
        {
            null, null
        }, {
            null, null
        }, {
            null, null
        }, {
            null, null
        }, {
            null, null
        }, {
            null, null
        }, {
            null, null
        }, {
            null, null
        }
    };
    public byte astateTankStates[] = {
        0, 0, 0, 0
    };
    private com.maddox.il2.engine.Eff3DActor astateTankEffects[][] = {
        {
            null, null, null
        }, {
            null, null, null
        }, {
            null, null, null
        }, {
            null, null, null
        }
    };
    public byte astateEngineStates[] = {
        0, 0, 0, 0, 0, 0, 0, 0
    };
    private com.maddox.il2.engine.Eff3DActor astateEngineEffects[][] = {
        {
            null, null, null
        }, {
            null, null, null
        }, {
            null, null, null
        }, {
            null, null, null
        }, {
            null, null, null
        }, {
            null, null, null
        }, {
            null, null, null
        }, {
            null, null, null
        }
    };
    public byte astateSootStates[] = {
        0, 0, 0, 0, 0, 0, 0, 0
    };
    public com.maddox.il2.engine.Eff3DActor astateSootEffects[][] = {
        {
            null, null
        }, {
            null, null
        }, {
            null, null
        }, {
            null, null
        }, {
            null, null
        }, {
            null, null
        }, {
            null, null
        }, {
            null, null
        }
    };
    private com.maddox.il2.engine.Eff3DActor astateCondensateEffects[] = {
        null, null, null, null, null, null, null, null
    };
    private com.maddox.il2.engine.Eff3DActor astateStallEffects[] = {
        null, null
    };
    private com.maddox.il2.engine.Eff3DActor astateAirShowEffects[] = {
        null, null
    };
    private com.maddox.il2.engine.Eff3DActor astateNavLightsEffects[] = {
        null, null, null, null, null, null
    };
    private com.maddox.il2.engine.LightPointActor astateNavLightsLights[] = {
        null, null, null, null, null, null
    };
    public com.maddox.il2.engine.Eff3DActor astateLandingLightEffects[] = {
        null, null, null, null
    };
    private com.maddox.il2.engine.LightPointActor astateLandingLightLights[] = {
        null, null, null, null
    };
    public java.lang.String astateEffectChunks[] = {
        null, null, null, null, null, null, null, null, null, null, 
        null, null, null, null, null, null, null, null, null, null, 
        null, null, null, null, null, null, null, null, null, null
    };
    public static final int astateEffectsDispTanks = 0;
    public static final int astateEffectsDispEngines = 4;
    public static final int astateEffectsDispLights = 12;
    public static final int astateEffectsDispLandingLights = 18;
    public static final int astateEffectsDispOilfilters = 22;
    public static boolean bCheckPlayerAircraft = true;
    private com.maddox.il2.fm.Item itemsToMaster[];
    private com.maddox.il2.fm.Item itemsToMirrors[];

}
