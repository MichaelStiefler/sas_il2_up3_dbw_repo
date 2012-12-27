// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) deadcode 
// Source File Name:   AircraftState.java

package com.maddox.il2.fm;

import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.*;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.*;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.game.*;
import com.maddox.il2.game.order.OrdersTree;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.air.*;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.vehicles.radios.*;
import com.maddox.il2.objects.weapons.*;
import com.maddox.rts.*;
import com.maddox.sound.CmdMusic;
import com.maddox.util.HashMapExt;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

// Referenced classes of package com.maddox.il2.fm:
//            FlightModelMain, EnginesInterface, Motor, Controls, 
//            Gear, Mass, FlightModel, RealFlightModel, 
//            Autopilotage, Pitot

public class AircraftState
{
    static class Item
    {

        void set(int i, int i_0_, Actor actor)
        {
            msgDestination = i;
            msgContext = i_0_;
            initiator = actor;
        }

        boolean equals(int i, int i_1_, Actor actor)
        {
            return msgDestination == i && msgContext == i_1_ && initiator == actor;
        }

        int msgDestination;
        int msgContext;
        Actor initiator;

        Item()
        {
        }
    }


    public AircraftState()
    {
        astateOilStates = new byte[8];
        astateTankStates = new byte[4];
        astateEngineStates = new byte[8];
        astateSootStates = new byte[8];
        astateCondensateEffects = new Eff3DActor[8];
        astateStallEffects = new Eff3DActor[2];
        // TODO: ++ Added Code for Net Replication ++
        // apparently due to a mistake this array had a size of "2" before which caused a "NetAircraft error, ID_03:" error on each aircraft spawn.
        astateAirShowEffects = new Eff3DActor[3];
        // TODO: ++ Added Code for Net Replication --
        astateDumpFuelEffects = new Eff3DActor[3];
        astateNavLightsEffects = new Eff3DActor[6];
        astateNavLightsLights = new LightPointActor[6];
        astateLandingLightEffects = new Eff3DActor[4];
        astateLandingLightLights = new LightPointActor[4];
        astateEffectChunks = new String[30];
        bleedingTime = 0L;
        astateBleedingTimes = new long[9];
        astateBleedingNext = new long[9];
        legsWounded = false;
        armsWounded = false;
        torpedoGyroAngle = 0;
        torpedoSpreadAngle = 0;
        beacon = 0;
        bWantBeaconsNet = false;
        listenLorenzBlindLanding = false;
        isAAFIAS = false;
        listenYGBeacon = false;
        listenNDBeacon = false;
        listenRadioStation = false;
        hayrakeCarrier = null;
        hayrakeCode = null;
        externalStoresDropped = false;
        armingSeed = World.Rnd().nextInt(0, 65535);
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
        iAirShowSmoke = 0;
        bAirShowSmokeEnhanced = false;
        iAirShowSmoke = Config.cur.ini.get("Mods", "AirShowSmoke", 0);
        if(iAirShowSmoke < 1 || iAirShowSmoke > 3)
            iAirShowSmoke = 0;
        if(Config.cur.ini.get("Mods", "AirShowSmokeEnhanced", 0) > 0)
            bAirShowSmokeEnhanced = true;
        bDumpFuel = false;
        COCKPIT_DOOR = 1;
        SIDE_DOOR = 2;
    }

    public void set(Actor actor1, boolean flag)
    {
        Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        Loc loc1 = new Loc();
        actor = actor1;
        if(actor1 instanceof Aircraft)
            aircraft = (AircraftLH)actor1;
        else
            throw new RuntimeException("Can not cast aircraft structure into a non-aircraft entity.");
        bIsMaster = flag;
        for(int i = 0; i < 4; i++)
            try
            {
                astateEffectChunks[i + 0] = actor.findHook("_Tank" + (i + 1) + "Burn").chunkName();
                astateEffectChunks[i + 0] = astateEffectChunks[i + 0].substring(0, astateEffectChunks[i + 0].length() - 1);
                Aircraft.debugprintln(aircraft, "AS: Tank " + i + " FX attached to '" + astateEffectChunks[i + 0] + "' substring..");
            }
            catch(Exception exception) { }

        for(int j = 0; j < ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.getNum(); j++)
            try
            {
                astateEffectChunks[j + 4] = actor.findHook("_Engine" + (j + 1) + "Smoke").chunkName();
                astateEffectChunks[j + 4] = astateEffectChunks[j + 4].substring(0, astateEffectChunks[j + 4].length() - 1);
                Aircraft.debugprintln(aircraft, "AS: Engine " + j + " FX attached to '" + astateEffectChunks[j + 4] + "' substring..");
            }
            catch(Exception exception1) { }

        for(int k = 0; k < astateNavLightsEffects.length; k++)
            try
            {
                astateEffectChunks[k + 12] = actor.findHook("_NavLight" + k).chunkName();
                astateEffectChunks[k + 12] = astateEffectChunks[k + 12].substring(0, astateEffectChunks[k + 12].length() - 1);
                Aircraft.debugprintln(aircraft, "AS: Nav. Lamp #" + k + " attached to '" + astateEffectChunks[k + 12] + "' substring..");
                HookNamed hooknamed = new HookNamed(aircraft, "_NavLight" + k);
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
            catch(Exception exception2) { }

        for(int l = 0; l < 4; l++)
            try
            {
                astateEffectChunks[l + 18] = actor.findHook("_LandingLight0" + l).chunkName();
                astateEffectChunks[l + 18] = astateEffectChunks[l + 18].substring(0, astateEffectChunks[l + 18].length() - 1);
                Aircraft.debugprintln(aircraft, "AS: Landing Lamp #" + l + " attached to '" + astateEffectChunks[l + 18] + "' substring..");
                HookNamed hooknamed1 = new HookNamed(aircraft, "_LandingLight0" + l);
                loc1.set(1.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
                hooknamed1.computePos(actor, loc, loc1);
                com.maddox.JGP.Point3d point3d1 = loc1.getPoint();
                astateLandingLightLights[l] = new LightPointActor(new LightPoint(), point3d1);
                astateLandingLightLights[l].light.setColor(0.4941176F, 0.9098039F, 0.9607843F);
                astateLandingLightLights[l].light.setEmit(0.0F, 0.0F);
                actor.draw.lightMap().put("_LandingLight0" + l, astateLandingLightLights[l]);
            }
            catch(Exception exception3) { }

        for(int i1 = 0; i1 < ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.getNum(); i1++)
            try
            {
                astateEffectChunks[i1 + 22] = actor.findHook("_Engine" + (i1 + 1) + "Oil").chunkName();
                astateEffectChunks[i1 + 22] = astateEffectChunks[i1 + 22].substring(0, astateEffectChunks[i1 + 22].length() - 1);
                Aircraft.debugprintln(aircraft, "AS: Oilfilter " + i1 + " FX attached to '" + astateEffectChunks[i1 + 22] + "' substring..");
            }
            catch(Exception exception4) { }

        for(int j1 = 0; j1 < astateEffectChunks.length; j1++)
            if(astateEffectChunks[j1] == null)
                astateEffectChunks[j1] = "AChunkNameYouCanNeverFind";

    }

    public boolean isMaster()
    {
        return bIsMaster;
    }

    public void setOilState(Actor actor1, int i, int j)
    {
        if(!Actor.isValid(actor1))
            return;
        if(j < 0 || j > 1 || astateOilStates[i] == j)
            return;
        if(bIsMaster)
        {
            aircraft.setDamager(actor1);
            doSetOilState(i, j);
            int k = 0;
            for(int l = 0; l < ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.getNum(); l++)
                if(astateOilStates[l] == 1)
                    k++;

            if(k == ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.getNum())
                setCockpitState(actor, astateCockpitState | 0x80);
            netToMirrors(11, i, j);
        } else
        {
            netToMaster(11, i, j, actor1);
        }
    }

    public void hitOil(Actor actor1, int i)
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
        Aircraft.debugprintln(aircraft, "AS: Checking '" + astateEffectChunks[i + 22] + "' visibility..");
        boolean flag = aircraft.isChunkAnyDamageVisible(astateEffectChunks[i + 22]);
        Aircraft.debugprintln(aircraft, "AS: '" + astateEffectChunks[i + 22] + "' is " + (flag ? "visible" : "invisible") + "..");
        Aircraft.debugprintln(aircraft, "Stating OilFilter " + i + " to state " + j + (flag ? ".." : " rejected (missing part).."));
        if(!flag)
            return;
        Aircraft.debugprintln(aircraft, "Stating OilFilter " + i + " to state " + j + "..");
        astateOilStates[i] = (byte)j;
        byte byte0 = 0;
        if(!bIsAboveCriticalSpeed)
            byte0 = 2;
        if(astateOilEffects[i][0] != null)
            Eff3DActor.finish(astateOilEffects[i][0]);
        astateOilEffects[i][0] = null;
        if(astateOilEffects[i][1] != null)
            Eff3DActor.finish(astateOilEffects[i][1]);
        astateOilEffects[i][1] = null;
        switch(astateOilStates[i])
        {
        case 1: // '\001'
            String s = astateOilStrings[byte0];
            if(s != null)
                try
                {
                    astateOilEffects[i][0] = Eff3DActor.New(actor, actor.findHook("_Engine" + (i + 1) + "Oil"), null, 1.0F, s, -1F);
                }
                catch(Exception exception) { }
            s = astateOilStrings[byte0 + 1];
            if(s != null)
                try
                {
                    astateOilEffects[i][1] = Eff3DActor.New(actor, actor.findHook("_Engine" + (i + 1) + "Oil"), null, 1.0F, s, -1F);
                }
                catch(Exception exception1) { }
            if(World.Rnd().nextFloat() < 0.25F)
                ((SndAircraft) (aircraft)).FM.setReadyToReturn(true);
            break;
        }
    }

    public void changeOilEffectBase(int i, Actor actor1)
    {
        for(int j = 0; j < 2; j++)
            if(astateOilEffects[i][j] != null)
                ((Actor) (astateOilEffects[i][j])).pos.changeBase(actor1, null, true);

    }

    public void setTankState(Actor actor1, int i, int j)
    {
        if(!Actor.isValid(actor1))
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

            if(((SndAircraft) (aircraft)).FM.isPlayers() && actor1 != actor && (actor1 instanceof Aircraft) && ((Aircraft)actor1).isNetPlayer() && j > 5 && astateTankStates[0] < 5 && astateTankStates[1] < 5 && astateTankStates[2] < 5 && astateTankStates[3] < 5 && !((SndAircraft) (aircraft)).FM.isSentBuryNote())
                Chat.sendLogRnd(3, "gore_lightfuel", (Aircraft)actor1, aircraft);
            netToMirrors(9, i, j);
        } else
        {
            netToMaster(9, i, j, actor1);
        }
    }

    public void hitTank(Actor actor1, int i, int j)
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

    public boolean doSetTankState(Actor actor1, int i, int j)
    {
        boolean flag = aircraft.isChunkAnyDamageVisible(astateEffectChunks[i + 0]);
        Aircraft.debugprintln(aircraft, "Stating Tank " + i + " to state " + j + (flag ? ".." : " rejected (missing part).."));
        if(!flag)
            return false;
        if(World.getPlayerAircraft() == actor)
        {
            if(astateTankStates[i] == 0 && (j == 1 || j == 2))
                HUD.log("FailedTank");
            if(astateTankStates[i] < 5 && j >= 5)
                HUD.log("FailedTankOnFire");
        }
        astateTankStates[i] = (byte)j;
        if(astateTankStates[i] < 5 && j >= 5)
        {
            ((SndAircraft) (aircraft)).FM.setTakenMortalDamage(true, actor1);
            ((SndAircraft) (aircraft)).FM.setCapableOfACM(false);
        }
        if(j < 4 && ((SndAircraft) (aircraft)).FM.isCapableOfBMP())
            ((SndAircraft) (aircraft)).FM.setTakenMortalDamage(false, actor1);
        byte byte0 = 0;
        if(!bIsAboveCriticalSpeed)
            byte0 = 21;
        for(int k = 0; k < 3; k++)
        {
            if(astateTankEffects[i][k] != null)
                Eff3DActor.finish(astateTankEffects[i][k]);
            astateTankEffects[i][k] = null;
            String s = astateTankStrings[byte0 + k + j * 3];
            if(s != null)
                if(j > 2)
                    astateTankEffects[i][k] = Eff3DActor.New(actor, actor.findHook("_Tank" + (i + 1) + "Burn"), null, 1.0F, s, -1F);
                else
                    astateTankEffects[i][k] = Eff3DActor.New(actor, actor.findHook("_Tank" + (i + 1) + "Leak"), null, 1.0F, s, -1F);
        }

        aircraft.sfxSmokeState(2, i, j > 4);
        return true;
    }

    private void doHitTank(Actor actor1, int i)
    {
        if((World.Rnd().nextInt(0, 99) < 75 || (actor instanceof Scheme1)) && astateTankStates[i] == 6)
        {
            ((SndAircraft) (aircraft)).FM.setReadyToDie(true);
            ((SndAircraft) (aircraft)).FM.setTakenMortalDamage(true, actor1);
            ((SndAircraft) (aircraft)).FM.setCapableOfACM(false);
            Voice.speakMayday(aircraft);
            Aircraft.debugprintln(aircraft, "I'm on fire, going down!.");
            Explosions.generateComicBulb(actor, "OnFire", 12F);
            if(World.Rnd().nextInt(0, 99) < 75 && ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).Skill > 1)
            {
                Aircraft.debugprintln(aircraft, "BAILING OUT - Tank " + i + " is on fire!.");
                hitDaSilk();
            }
        } else
        if(World.Rnd().nextInt(0, 99) < 12)
        {
            ((SndAircraft) (aircraft)).FM.setReadyToReturn(true);
            Aircraft.debugprintln(aircraft, "Tank " + i + " hit, RTB..");
            Explosions.generateComicBulb(actor, "RTB", 12F);
        }
    }

    public void changeTankEffectBase(int i, Actor actor1)
    {
        for(int j = 0; j < 3; j++)
            if(astateTankEffects[i][j] != null)
                ((Actor) (astateTankEffects[i][j])).pos.changeBase(actor1, null, true);

        aircraft.sfxSmokeState(2, i, false);
    }

    public void explodeTank(Actor actor1, int i)
    {
        if(!Actor.isValid(actor1))
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
        Aircraft.debugprintln(aircraft, "Tank " + i + " explodes..");
        Eff3DActor.New(actor, actor.findHook("_Tank" + (i + 1) + "Burn"), null, 1.0F, "3DO/Effects/Fireworks/Tank_Burn.eff", -1F);
        Eff3DActor.New(actor, actor.findHook("_Tank" + (i + 1) + "Burn"), null, 1.0F, "3DO/Effects/Fireworks/Tank_SmokeBoiling.eff", -1F);
        Eff3DActor.New(actor, actor.findHook("_Tank" + (i + 1) + "Burn"), null, 1.0F, "3DO/Effects/Fireworks/Tank_Sparks.eff", -1F);
        Eff3DActor.New(actor, actor.findHook("_Tank" + (i + 1) + "Burn"), null, 1.0F, "3DO/Effects/Fireworks/Tank_SparksP.eff", -1F);
        aircraft.msgCollision(actor, astateEffectChunks[i + 0] + "0", astateEffectChunks[i + 0] + "0");
        if((actor instanceof Scheme1) && aircraft.isChunkAnyDamageVisible(astateEffectChunks[i + 0]))
            aircraft.msgCollision(actor, "CF_D0", "CF_D0");
        HookNamed hooknamed = new HookNamed((ActorMesh)actor, "_Tank" + (i + 1) + "Burn");
        Loc loc = new Loc();
        Loc loc1 = new Loc();
        actor.pos.getCurrent(loc);
        hooknamed.computePos(actor, loc, loc1);
        if(World.getPlayerAircraft() == actor)
            HUD.log("FailedTankExplodes");
    }

    public void setEngineState(Actor actor1, int i, int j)
    {
        if(!Actor.isValid(actor1))
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

            if(((SndAircraft) (aircraft)).FM.isPlayers() && actor1 != actor && (actor1 instanceof Aircraft) && ((Aircraft)actor1).isNetPlayer() && j > 3 && astateEngineStates[0] < 3 && astateEngineStates[1] < 3 && astateEngineStates[2] < 3 && astateEngineStates[3] < 3 && !((SndAircraft) (aircraft)).FM.isSentBuryNote())
                Chat.sendLogRnd(3, "gore_lighteng", (Aircraft)actor1, aircraft);
            int l = 0;
            for(int i1 = 0; i1 < ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.getNum(); i1++)
                if(astateEngineStates[i1] > 2)
                    l++;

            if(l == ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.getNum())
                setCockpitState(actor, astateCockpitState | 0x80);
            netToMirrors(1, i, j);
        } else
        {
            netToMaster(1, i, j, actor1);
        }
    }

    public void hitEngine(Actor actor1, int i, int j)
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

    public boolean doSetEngineState(Actor actor1, int i, int j)
    {
        Aircraft.debugprintln(aircraft, "AS: Checking '" + astateEffectChunks[i + 4] + "' visibility..");
        boolean flag = aircraft.isChunkAnyDamageVisible(astateEffectChunks[i + 4]);
        Aircraft.debugprintln(aircraft, "AS: '" + astateEffectChunks[i + 4] + "' is " + (flag ? "visible" : "invisible") + "..");
        Aircraft.debugprintln(aircraft, "Stating Engine " + i + " to state " + j + (flag ? ".." : " rejected (missing part).."));
        if(!flag)
            return false;
        if(astateEngineStates[i] < 4 && j >= 4)
        {
            if(World.getPlayerAircraft() == actor)
                HUD.log("FailedEngineOnFire");
            ((SndAircraft) (aircraft)).FM.setTakenMortalDamage(true, actor1);
            ((SndAircraft) (aircraft)).FM.setCapableOfACM(false);
            ((SndAircraft) (aircraft)).FM.setCapableOfTaxiing(false);
        }
        astateEngineStates[i] = (byte)j;
        if(j < 2 && ((SndAircraft) (aircraft)).FM.isCapableOfBMP())
            ((SndAircraft) (aircraft)).FM.setTakenMortalDamage(false, actor1);
        byte byte0 = 0;
        if(!bIsAboveCriticalSpeed)
            byte0 = 15;
        for(int k = 0; k < 3; k++)
        {
            if(astateEngineEffects[i][k] != null)
                Eff3DActor.finish(astateEngineEffects[i][k]);
            astateEngineEffects[i][k] = null;
            String s = astateEngineStrings[byte0 + k + j * 3];
            if(s != null)
                astateEngineEffects[i][k] = Eff3DActor.New(actor, actor.findHook("_Engine" + (i + 1) + "Smoke"), null, 1.0F, s, -1F);
        }

        aircraft.sfxSmokeState(1, i, j > 3);
        return true;
    }

    private void doHitEngine(Actor actor1, int i)
    {
        if(World.Rnd().nextInt(0, 99) < 12)
        {
            ((SndAircraft) (aircraft)).FM.setReadyToReturn(true);
            Aircraft.debugprintln(aircraft, "Engines out, RTB..");
            Explosions.generateComicBulb(actor, "RTB", 12F);
        }
        if(astateEngineStates[i] >= 2 && !(actor instanceof Scheme1) && World.Rnd().nextInt(0, 99) < 25)
        {
            ((SndAircraft) (aircraft)).FM.setReadyToReturn(true);
            Aircraft.debugprintln(aircraft, "One of the engines out, RTB..");
        }
        if(astateEngineStates[i] == 4)
            if(actor instanceof Scheme1)
            {
                if(World.Rnd().nextBoolean())
                {
                    Aircraft.debugprintln(aircraft, "BAILING OUT - Engine " + i + " is on fire..");
                    aircraft.hitDaSilk();
                }
                ((SndAircraft) (aircraft)).FM.setReadyToDie(true);
                ((SndAircraft) (aircraft)).FM.setTakenMortalDamage(true, actor1);
            } else
            if(World.Rnd().nextInt(0, 99) < 50)
            {
                ((SndAircraft) (aircraft)).FM.setReadyToDie(true);
                if(World.Rnd().nextInt(0, 99) < 25)
                    ((SndAircraft) (aircraft)).FM.setTakenMortalDamage(true, actor1);
                ((SndAircraft) (aircraft)).FM.setCapableOfACM(false);
                Aircraft.debugprintln(aircraft, "Engines on fire, ditching..");
                Explosions.generateComicBulb(actor, "OnFire", 12F);
            }
    }

    public void changeEngineEffectBase(int i, Actor actor1)
    {
        for(int j = 0; j < 3; j++)
            if(astateEngineEffects[i][j] != null)
                ((Actor) (astateEngineEffects[i][j])).pos.changeBase(actor1, null, true);

        aircraft.sfxSmokeState(1, i, false);
    }

    public void explodeEngine(Actor actor1, int i)
    {
        if(!Actor.isValid(actor1))
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
        Aircraft.debugprintln(aircraft, "Engine " + i + " explodes..");
        Eff3DActor.New(actor, actor.findHook("_Engine" + (i + 1) + "Smoke"), null, 1.0F, "3DO/Effects/Fireworks/Tank_Burn.eff", -1F);
        Eff3DActor.New(actor, actor.findHook("_Engine" + (i + 1) + "Smoke"), null, 1.0F, "3DO/Effects/Fireworks/Tank_SmokeBoiling.eff", -1F);
        Eff3DActor.New(actor, actor.findHook("_Engine" + (i + 1) + "Smoke"), null, 1.0F, "3DO/Effects/Fireworks/Tank_Sparks.eff", -1F);
        Eff3DActor.New(actor, actor.findHook("_Engine" + (i + 1) + "Smoke"), null, 1.0F, "3DO/Effects/Fireworks/Tank_SparksP.eff", -1F);
        aircraft.msgCollision(aircraft, astateEffectChunks[i + 4] + "0", astateEffectChunks[i + 4] + "0");
        Actor actor1;
        if(aircraft.getDamager() != null)
            actor1 = aircraft.getDamager();
        else
            actor1 = actor;
        HookNamed hooknamed = new HookNamed((ActorMesh)actor, "_Engine" + (i + 1) + "Smoke");
        Loc loc = new Loc();
        Loc loc1 = new Loc();
        actor.pos.getCurrent(loc);
        hooknamed.computePos(actor, loc, loc1);
        MsgExplosion.send(null, astateEffectChunks[4 + i] + "0", loc1.getPoint(), actor1, 1.248F, 0.026F, 1, 75F);
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

    public void setEngineDies(Actor actor1, int i)
    {
        if(!Actor.isValid(actor1))
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

    public void setEngineStuck(Actor actor1, int i)
    {
        if(!Actor.isValid(actor1))
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

    public void setEngineSpecificDamage(Actor actor1, int i, int j)
    {
        if(!Actor.isValid(actor1))
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

    public void setEngineReadyness(Actor actor1, int i, int j)
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

    public void setEngineStage(Actor actor1, int i, int j)
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

    public void setEngineCylinderKnockOut(Actor actor1, int i, int j)
    {
        if(!Actor.isValid(actor1))
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

    public void setEngineMagnetoKnockOut(Actor actor1, int i, int j)
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
        ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[i].doSetEngineStarts();
    }

    private void doSetEngineRunning(int i)
    {
        ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[i].doSetEngineRunning();
    }

    private void doSetEngineStops(int i)
    {
        ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[i].doSetEngineStops();
    }

    private void doSetEngineDies(int i)
    {
        ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[i].doSetEngineDies();
    }

    private void doSetEngineStuck(int i)
    {
        ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[i].doSetEngineStuck();
    }

    private void doSetEngineSpecificDamage(int i, int j)
    {
        switch(j)
        {
        case 0: // '\0'
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[i].doSetKillCompressor();
            break;

        case 3: // '\003'
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[i].doSetKillPropAngleDevice();
            break;

        case 4: // '\004'
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[i].doSetKillPropAngleDeviceSpeeds();
            break;

        case 5: // '\005'
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[i].doSetExtinguisherFire();
            break;

        case 2: // '\002'
            throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unimplemented feature (E.S.D./H.)");

        case 1: // '\001'
            if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[i].isHasControlThrottle())
                ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[i].doSetKillControlThrottle();
            break;

        case 6: // '\006'
            if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[i].isHasControlProp())
                ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[i].doSetKillControlProp();
            break;

        case 7: // '\007'
            if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[i].isHasControlMix())
                ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[i].doSetKillControlMix();
            break;

        default:
            throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Corrupt data in (E.S.D./null)");
        }
    }

    private void doSetEngineReadyness(int i, int j)
    {
        ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[i].doSetReadyness(0.01F * (float)j);
    }

    private void doSetEngineStage(int i, int j)
    {
        ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[i].doSetStage(j);
    }

    private void doSetEngineCylinderKnockOut(int i, int j)
    {
        ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[i].doSetCyliderKnockOut(j);
    }

    private void doSetEngineMagnetoKnockOut(int i, int j)
    {
        ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[i].doSetMagnetoKnockOut(j);
    }

    public void doSetEngineExtinguisherVisuals(int i)
    {
        Aircraft.debugprintln(aircraft, "AS: Checking '" + astateEffectChunks[i + 4] + "' visibility..");
        boolean flag = aircraft.isChunkAnyDamageVisible(astateEffectChunks[i + 4]);
        Aircraft.debugprintln(aircraft, "AS: '" + astateEffectChunks[i + 4] + "' is " + (flag ? "visible" : "invisible") + "..");
        Aircraft.debugprintln(aircraft, "Firing Extinguisher on Engine " + i + (flag ? ".." : " rejected (missing part).."));
        if(!flag)
        {
            return;
        } else
        {
            Eff3DActor.New(actor, actor.findHook("_Engine" + (i + 1) + "Smoke"), null, 1.0F, "3DO/Effects/Aircraft/EngineExtinguisher1.eff", 3F);
            return;
        }
    }

    public void setSootState(Actor actor1, int i, int j)
    {
        if(!Actor.isValid(actor1))
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
        Aircraft.debugprintln(aircraft, "AS: Checking '" + astateEffectChunks[i + 4] + "' visibility..");
        boolean flag = aircraft.isChunkAnyDamageVisible(astateEffectChunks[i + 4]);
        Aircraft.debugprintln(aircraft, "AS: '" + astateEffectChunks[i + 4] + "' is " + (flag ? "visible" : "invisible") + "..");
        Aircraft.debugprintln(aircraft, "Stating Engine " + i + " to state " + j + (flag ? ".." : " rejected (missing part).."));
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

    public void changeSootEffectBase(int i, Actor actor1)
    {
        for(int j = 0; j < 2; j++)
            if(astateSootEffects[i][j] != null)
                ((Actor) (astateSootEffects[i][j])).pos.changeBase(actor1, null, true);

    }

    public void setCockpitState(Actor actor1, int i)
    {
        if(!Actor.isValid(actor1))
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

    public void setControlsDamage(Actor actor1, int i)
    {
        if(!Actor.isValid(actor1))
            return;
        if(bIsMaster)
        {
            aircraft.setDamager(actor1);
            if(((SndAircraft) (aircraft)).FM.isPlayers() && actor1 != actor && (actor1 instanceof Aircraft) && ((Aircraft)actor1).isNetPlayer() && !((SndAircraft) (aircraft)).FM.isSentControlsOutNote() && !((SndAircraft) (aircraft)).FM.isSentBuryNote())
            {
                Chat.sendLogRnd(3, "gore_hitctrls", (Aircraft)actor1, aircraft);
                ((SndAircraft) (aircraft)).FM.setSentControlsOutNote(true);
            }
            doSetControlsDamage(i, actor1);
        } else
        {
            netToMaster(21, 0, i, actor1);
        }
    }

    public void setInternalDamage(Actor actor1, int i)
    {
        if(!Actor.isValid(actor1))
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

    private void doSetControlsDamage(int i, Actor actor1)
    {
        switch(i)
        {
        case 0: // '\0'
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.resetControl(0);
            if(((SndAircraft) (aircraft)).FM.isPlayers() && ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.bHasAileronControl)
                HUD.log("FailedAroneAU");
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.bHasAileronControl = false;
            ((SndAircraft) (aircraft)).FM.setCapableOfACM(false);
            break;

        case 1: // '\001'
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.resetControl(1);
            if(((SndAircraft) (aircraft)).FM.isPlayers() && ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.bHasElevatorControl)
                HUD.log("FailedVatorAU");
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.bHasElevatorControl = false;
            ((SndAircraft) (aircraft)).FM.setCapableOfACM(false);
            if(Math.abs(((Tuple3d) (((FlightModelMain) (((SndAircraft) (aircraft)).FM)).Vwld)).z) > 7D)
                ((SndAircraft) (aircraft)).FM.setCapableOfBMP(false, actor1);
            break;

        case 2: // '\002'
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.resetControl(2);
            if(((SndAircraft) (aircraft)).FM.isPlayers() && ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.bHasRudderControl)
                HUD.log("FailedRudderAU");
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.bHasRudderControl = false;
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
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).Gears.setHydroOperable(false);
            break;

        case 1: // '\001'
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.bHasFlapsControl = false;
            break;

        case 2: // '\002'
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).M.nitro = 0.0F;
            break;

        case 3: // '\003'
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).Gears.setHydroOperable(false);
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).Gears.setOperable(false);
            break;

        case 4: // '\004'
            if(aircraft instanceof DO_335A0)
            {
                ((DO_335A0)aircraft).doKeelShutoff();
                break;
            }
            if(aircraft instanceof DO_335V13)
                ((DO_335V13)aircraft).doKeelShutoff();
            else
                throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unexpected command (I.K.S.)");
            break;

        case 5: // '\005'
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[1].setPropReductorValue(0.007F);
            break;

        default:
            throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Corrupt data in (I.D./null)");
        }
    }

    private void doSetGliderBoostOn()
    {
        if(actor instanceof Scheme0)
            ((Scheme0)actor).doFireBoosters();
        else
        if(actor instanceof AR_234B2)
            ((AR_234B2)actor).doFireBoosters();
        else
            throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unexpected command (G.B./On not in Nul)");
    }

    private void doSetGliderBoostOff()
    {
        if(actor instanceof Scheme0)
            ((Scheme0)actor).doCutBoosters();
        else
        if(actor instanceof AR_234B2)
            ((AR_234B2)actor).doCutBoosters();
        else
            throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unexpected command (G.B./Off not in Nul)");
    }

    private void doSetGliderCutCart()
    {
        if(actor instanceof Scheme0)
            ((Scheme0)actor).doCutCart();
        else
            throw new RuntimeException("(" + aircraft.typedName() + ") A.S.: Unexpected command (G.C.C./Off not in Nul)");
    }

    private void doSetCondensateState(boolean flag)
    {
        for(int i = 0; i < ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.getNum(); i++)
        {
            if(astateCondensateEffects[i] != null)
                Eff3DActor.finish(astateCondensateEffects[i]);
            astateCondensateEffects[i] = null;
            if(flag)
            {
                String s = astateCondensateStrings[1];
                if(s != null)
                    try
                    {
                        astateCondensateEffects[i] = Eff3DActor.New(actor, actor.findHook("_Engine" + (i + 1) + "Smoke"), astateCondensateDispVector, 1.0F, s, -1F);
                    }
                    catch(Exception exception)
                    {
                        Aircraft.debugprintln(aircraft, "Above condensate failed - probably a glider..");
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
                Eff3DActor.finish(astateStallEffects[i]);

        if(flag)
        {
            String s = astateStallStrings[1];
            if(s != null)
            {
                if(bWingTipLExists)
                    astateStallEffects[0] = Eff3DActor.New(actor, actor.findHook("_WingTipL"), null, 1.0F, s, -1F);
                if(bWingTipRExists)
                    astateStallEffects[1] = Eff3DActor.New(actor, actor.findHook("_WingTipR"), null, 1.0F, s, -1F);
            }
        }
    }

    public void wantBeaconsNet(boolean flag)
    {
        if(flag == bWantBeaconsNet)
            return;
        bWantBeaconsNet = flag;
        if(World.getPlayerAircraft() != actor)
            return;
        if(bWantBeaconsNet)
            setBeacon(actor, beacon, 0, false);
    }

    public int getBeacon()
    {
        return beacon;
    }

    public void beaconPlus()
    {
        if(Main.cur().mission.hasBeacons(actor.getArmy()))
        {
            int i = Main.cur().mission.getBeacons(actor.getArmy()).size();
            if(beacon < i)
                setBeacon(beacon + 1);
            else
                setBeacon(0);
        }
    }

    public void beaconMinus()
    {
        if(Main.cur().mission.hasBeacons(actor.getArmy()))
        {
            int i = Main.cur().mission.getBeacons(actor.getArmy()).size();
            if(beacon >= 1)
                setBeacon(beacon - 1);
            else
                setBeacon(i);
        }
    }

    public void setBeacon(int i)
    {
        for(; i < 0; i += 32);
        for(; i > 32; i -= 32);
        if(i == beacon)
        {
            return;
        } else
        {
            setBeacon(actor, i, 0, false);
            return;
        }
    }

    private void setBeacon(Actor actor1, int i, int j, boolean flag)
    {
        doSetBeacon(actor1, i, j);
        if(Mission.isSingle() || !bWantBeaconsNet)
            return;
        if(!Actor.isValid(actor1))
            return;
        if(bIsMaster)
            netToMirrors(41, i, j, actor1);
        else
        if(!flag)
            netToMaster(41, i, j, actor1);
    }

    private void doSetBeacon(Actor actor1, int i, int j)
    {
        if(actor != actor1)
            return;
        if(World.getPlayerAircraft() != actor)
            return;
        if(i > 0)
        {
            Actor actor2 = (Actor)Main.cur().mission.getBeacons(actor.getArmy()).get(i - 1);
            boolean flag = Aircraft.hasPlaneZBReceiver(aircraft);
            if(!flag && ((actor2 instanceof TypeHasYGBeacon) || (actor2 instanceof TypeHasHayRake)))
            {
                int k = i - beacon;
                beacon = i;
                if(k > 0)
                    beaconPlus();
                else
                    beaconMinus();
                return;
            }
            String s = Beacon.getBeaconID(i - 1);
            if(aircraft.getPilotsCount() == 1 || ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines.length == 1)
                Main3D.cur3D().ordersTree.setFrequency(null);
            if(actor2 instanceof TypeHasYGBeacon)
            {
                Main3D.cur3D().ordersTree.setFrequency(OrdersTree.FREQ_FRIENDLY);
                HUD.log(hudLogBeaconId, "BeaconYG", new Object[] {
                    s
                });
                startListeningYGBeacon();
            } else
            if(actor2 instanceof TypeHasHayRake)
            {
                Main3D.cur3D().ordersTree.setFrequency(OrdersTree.FREQ_FRIENDLY);
                HUD.log(hudLogBeaconId, "BeaconYE", new Object[] {
                    s
                });
                String s1 = Main.cur().mission.getHayrakeCodeOfCarrier(actor2);
                startListeningHayrake(actor2, s1);
            } else
            if(actor2 instanceof TypeHasLorenzBlindLanding)
            {
                Main3D.cur3D().ordersTree.setFrequency(OrdersTree.FREQ_FRIENDLY);
                HUD.log(hudLogBeaconId, "BeaconBA", new Object[] {
                    s
                });
                startListeningLorenzBlindLanding();
                isAAFIAS = false;
                if(actor2 instanceof TypeHasAAFIAS)
                    isAAFIAS = true;
            } else
            if(actor2 instanceof TypeHasRadioStation)
            {
                TypeHasRadioStation typehasradiostation = (TypeHasRadioStation)actor2;
                s = typehasradiostation.getStationID();
                String s2 = Property.stringValue(actor2.getClass(), "i18nName", s);
                HUD.log(hudLogBeaconId, "BeaconRS", new Object[] {
                    s2
                });
                startListeningRadioStation(s);
            } else
            {
                HUD.log(hudLogBeaconId, "BeaconND", new Object[] {
                    s
                });
                startListeningNDBeacon();
            }
        } else
        {
            HUD.log(hudLogBeaconId, "BeaconNONE");
            stopListeningBeacons();
            Main3D.cur3D().ordersTree.setFrequency(OrdersTree.FREQ_FRIENDLY);
        }
        beacon = i;
    }

    public void startListeningYGBeacon()
    {
        stopListeningLorenzBlindLanding();
        stopListeningHayrake();
        listenYGBeacon = true;
        listenNDBeacon = false;
        listenRadioStation = false;
        aircraft.playBeaconCarrier(false, 0.0F);
        CmdMusic.setCurrentVolume(0.001F);
    }

    public void startListeningNDBeacon()
    {
        stopListeningLorenzBlindLanding();
        listenYGBeacon = false;
        listenNDBeacon = true;
        listenRadioStation = false;
        stopListeningHayrake();
        CmdMusic.setCurrentVolume(0.001F);
    }

    public void startListeningRadioStation(String s)
    {
        stopListeningLorenzBlindLanding();
        listenYGBeacon = false;
        listenNDBeacon = false;
        listenRadioStation = true;
        stopListeningHayrake();
        String s1 = s.replace(' ', '_');
        int i = Mission.curYear();
        int j = Mission.curMonth();
        int k = Mission.curDay();
        String s2 = "" + i;
        String s3 = "";
        String s4 = "";
        if(j < 10)
            s3 = "0" + j;
        else
            s3 = "" + j;
        if(k < 10)
            s4 = "0" + k;
        else
            s4 = "" + k;
        String as[] = {
            s2 + s3 + s4, s2 + s3 + "XX", s2 + "XX" + s4, s2 + "XXXX"
        };
        for(int l = 0; l < as.length; l++)
        {
            File file = new File("./samples/Music/Radio/" + s1 + "/" + as[l]);
            if(file.exists())
            {
                CmdMusic.setPath("Music/Radio/" + s1 + "/" + as[l], true);
                CmdMusic.play();
                return;
            }
        }

        CmdMusic.setPath("Music/Radio/" + s1, true);
        CmdMusic.play();
    }

    public void stopListeningBeacons()
    {
        stopListeningLorenzBlindLanding();
        listenYGBeacon = false;
        listenNDBeacon = false;
        listenRadioStation = false;
        stopListeningHayrake();
        aircraft.stopMorseSounds();
        CmdMusic.setCurrentVolume(0.001F);
    }

    public void startListeningHayrake(Actor actor1, String s)
    {
        stopListeningLorenzBlindLanding();
        hayrakeCarrier = actor1;
        hayrakeCode = s;
        listenYGBeacon = false;
        listenNDBeacon = false;
        listenRadioStation = false;
        aircraft.stopMorseSounds();
        CmdMusic.setCurrentVolume(0.001F);
    }

    public void stopListeningHayrake()
    {
        hayrakeCarrier = null;
        hayrakeCode = null;
    }

    public void startListeningLorenzBlindLanding()
    {
        listenLorenzBlindLanding = true;
        stopListeningHayrake();
        listenYGBeacon = false;
        listenNDBeacon = false;
        listenRadioStation = false;
        aircraft.stopMorseSounds();
        CmdMusic.setCurrentVolume(0.001F);
    }

    public void stopListeningLorenzBlindLanding()
    {
        listenLorenzBlindLanding = false;
        isAAFIAS = false;
        aircraft.stopMorseSounds();
    }

    public void preLoadRadioStation(Actor actor1)
    {
        TypeHasRadioStation typehasradiostation = (TypeHasRadioStation)actor1;
        String s = typehasradiostation.getStationID();
        startListeningRadioStation(s);
    }

    public void setAirShowSmokeType(int i)
    {
        iAirShowSmoke = i;
    }

    public void setAirShowSmokeEnhanced(boolean flag)
    {
        bAirShowSmokeEnhanced = flag;
    }

    public void setAirShowState(boolean flag)
    {
        if(bShowSmokesOn == flag)
            return;
        if(bIsMaster)
        {
            doSetAirShowState(flag);
            netToMirrors(42, iAirShowSmoke, iAirShowSmoke);
            netToMirrors(43, bAirShowSmokeEnhanced ? 1 : 0, bAirShowSmokeEnhanced ? 1 : 0);
            netToMirrors(15, flag ? 1 : 0, flag ? 1 : 0);
        }
    }

    private void doSetAirShowState(boolean flag)
    {
        bShowSmokesOn = flag;
        for(int i = 0; i < 3; i++)
            if(astateAirShowEffects[i] != null)
                Eff3DActor.finish(astateAirShowEffects[i]);

        if(flag)
        {
            Hook hook = null;
            try
            {
                hook = actor.findHook("_ClipCGear");
            }
            catch(Exception exception)
            {
                hook = null;
            }
            if(iAirShowSmoke < 1 || iAirShowSmoke > 3 || hook == null)
            {
                if(bWingTipLExists)
                    astateAirShowEffects[0] = Eff3DActor.New(actor, actor.findHook("_WingTipL"), null, 1.0F, "3DO/Effects/Aircraft/AirShowRedTSPD.eff", -1F);
                if(bWingTipRExists)
                    astateAirShowEffects[1] = Eff3DActor.New(actor, actor.findHook("_WingTipR"), null, 1.0F, "3DO/Effects/Aircraft/AirShowGreenTSPD.eff", -1F);
            } else
            if(iAirShowSmoke == 1)
            {
                astateAirShowEffects[0] = Eff3DActor.New(actor, hook, null, 1.0F, "3DO/Effects/Aircraft/AirShowSmoke1.eff", -1F);
                if(bAirShowSmokeEnhanced)
                {
                    astateAirShowEffects[1] = Eff3DActor.New(actor, hook, null, 1.0F, "3DO/Effects/Aircraft/AirShowSmoke1_2.eff", -1F);
                    astateAirShowEffects[2] = Eff3DActor.New(actor, hook, null, 1.0F, "3DO/Effects/Aircraft/AirShowSmoke1_3.eff", -1F);
                }
            } else
            if(iAirShowSmoke == 2)
            {
                astateAirShowEffects[0] = Eff3DActor.New(actor, hook, null, 1.0F, "3DO/Effects/Aircraft/AirShowSmoke2.eff", -1F);
                if(bAirShowSmokeEnhanced)
                {
                    astateAirShowEffects[1] = Eff3DActor.New(actor, hook, null, 1.0F, "3DO/Effects/Aircraft/AirShowSmoke2_2.eff", -1F);
                    astateAirShowEffects[2] = Eff3DActor.New(actor, hook, null, 1.0F, "3DO/Effects/Aircraft/AirShowSmoke2_3.eff", -1F);
                }
            } else
            {
                astateAirShowEffects[0] = Eff3DActor.New(actor, hook, null, 1.0F, "3DO/Effects/Aircraft/AirShowSmoke3.eff", -1F);
                if(bAirShowSmokeEnhanced)
                {
                    astateAirShowEffects[1] = Eff3DActor.New(actor, hook, null, 1.0F, "3DO/Effects/Aircraft/AirShowSmoke3_2.eff", -1F);
                    astateAirShowEffects[2] = Eff3DActor.New(actor, hook, null, 1.0F, "3DO/Effects/Aircraft/AirShowSmoke3_3.eff", -1F);
                }
            }
        }
    }

    public void setDumpFuelState(boolean flag)
    {
        if(bDumpFuel == flag)
            return;
        if(bIsMaster)
        {
            doSetDumpFuelState(flag);
            netToMirrors(50, flag ? 1 : 0, flag ? 1 : 0);
        }
    }

    private void doSetDumpFuelState(boolean flag)
    {
        bDumpFuel = flag;
        for(int i = 0; i < 2; i++)
            if(astateDumpFuelEffects[i] != null)
                Eff3DActor.finish(astateDumpFuelEffects[i]);

        if(flag && (actor instanceof TypeFuelDump))
        {
            for(int j = 0; j < 2; j++)
                switch(j)
                {
                default:
                    break;

                case 0: // '\0'
                    for(int k = 0; k < 2; k++)
                    {
                        try
                        {
                            if(actor.findHook("_FuelDump0" + (k + 1)) != null)
                                astateDumpFuelEffects[k] = Eff3DActor.New(actor, actor.findHook("_FuelDump0" + (k + 1)), null, 1.0F, "3DO/Effects/Aircraft/DumpFuelTSPD.eff", -1F);
                            continue;
                        }
                        catch(Exception exception) { }
                        break;
                    }

                    if(astateDumpFuelEffects[0] != null)
                        break;
                    // fall through

                case 1: // '\001'
                    if(astateDumpFuelEffects[0] == null)
                    {
                        if(bWingTipLExists)
                            astateDumpFuelEffects[0] = Eff3DActor.New(actor, actor.findHook("_WingTipL"), null, 1.0F, "3DO/Effects/Aircraft/DumpFuelTSPD.eff", -1F);
                        if(bWingTipRExists)
                            astateDumpFuelEffects[1] = Eff3DActor.New(actor, actor.findHook("_WingTipR"), null, 1.0F, "3DO/Effects/Aircraft/DumpFuelTSPD.eff", -1F);
                    }
                    break;
                }

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
                Eff3DActor.finish(astateNavLightsEffects[i]);
                astateNavLightsLights[i].light.setEmit(0.0F, 0.0F);
            }
            astateNavLightsEffects[i] = null;
        }

        if(flag)
        {
            for(int j = 0; j < astateNavLightsEffects.length; j++)
            {
                Aircraft.debugprintln(aircraft, "AS: Checking '" + astateEffectChunks[j + 12] + "' visibility..");
                boolean flag1 = aircraft.isChunkAnyDamageVisible(astateEffectChunks[j + 12]);
                Aircraft.debugprintln(aircraft, "AS: '" + astateEffectChunks[j + 12] + "' is " + (flag1 ? "visible" : "invisible") + "..");
                if(flag1)
                {
                    bNavLightsOn = flag;
                    String s = "3DO/Effects/Fireworks/Flare" + (j <= 1 ? "Red" : j <= 3 ? "Green" : "White") + ".eff";
                    astateNavLightsEffects[j] = Eff3DActor.New(actor, actor.findHook("_NavLight" + j), null, 1.0F, s, -1F, false);
                    astateNavLightsLights[j].light.setEmit(0.35F, 8F);
                }
            }

        } else
        {
            bNavLightsOn = flag;
        }
    }

    public void changeNavLightEffectBase(int i, Actor actor1)
    {
        if(astateNavLightsEffects[i] != null)
        {
            Eff3DActor.finish(astateNavLightsEffects[i]);
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
                Eff3DActor.finish(astateLandingLightEffects[i]);
                astateLandingLightLights[i].light.setEmit(0.0F, 0.0F);
            }
            astateLandingLightEffects[i] = null;
        }

        if(flag)
        {
            for(int j = 0; j < astateLandingLightEffects.length; j++)
            {
                Aircraft.debugprintln(aircraft, "AS: Checking '" + astateEffectChunks[j + 18] + "' visibility..");
                boolean flag1 = aircraft.isChunkAnyDamageVisible(astateEffectChunks[j + 18]);
                Aircraft.debugprintln(aircraft, "AS: '" + astateEffectChunks[j + 18] + "' is " + (flag1 ? "visible" : "invisible") + "..");
                if(flag1)
                {
                    String s = "3DO/Effects/Fireworks/FlareWhiteWide.eff";
                    astateLandingLightEffects[j] = Eff3DActor.New(actor, actor.findHook("_LandingLight0" + j), null, 1.0F, s, -1F);
                    astateLandingLightLights[j].light.setEmit(1.2F, 8F);
                }
            }

        }
    }

    public void changeLandingLightEffectBase(int i, Actor actor1)
    {
        if(astateLandingLightEffects[i] != null)
        {
            Eff3DActor.finish(astateLandingLightEffects[i]);
            astateLandingLightLights[i].light.setEmit(0.0F, 0.0F);
            astateLandingLightEffects[i] = null;
            bLandingLightOn = false;
        }
    }

    public void disablePilot(int i)
    {
        astatePilotStates[i] = 100;
    }

    public void setPilotState(Actor actor1, int i, int j)
    {
        setPilotState(actor1, i, j, true);
    }

    public void setPilotState(Actor actor1, int i, int j, boolean flag)
    {
        if(!Actor.isValid(actor1))
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
            if(flag && ((SndAircraft) (aircraft)).FM.isPlayers() && i == astatePlayerIndex && !World.isPlayerDead() && actor1 != actor && (actor1 instanceof Aircraft) && ((Aircraft)actor1).isNetPlayer() && j == 100)
                Chat.sendLogRnd(1, "gore_pk", (Aircraft)actor1, aircraft);
            if(j > 0 && flag)
                netToMirrors(17, i, j);
        } else
        if(flag)
            netToMaster(17, i, j, actor1);
    }

    public void hitPilot(Actor actor1, int i, int j)
    {
        setPilotState(actor1, i, astatePilotStates[i] + j);
    }

    public void setBleedingPilot(Actor actor1, int i, int j)
    {
        setBleedingPilot(actor1, i, j, true);
    }

    public void setBleedingPilot(Actor actor1, int i, int j, boolean flag)
    {
        if(!Actor.isValid(actor1))
            return;
        if(bIsMaster)
        {
            doSetBleedingPilot(i, j, actor1);
            if(flag)
                netToMirrors(45, i, j);
        } else
        if(flag)
            netToMaster(45, i, j, actor1);
    }

    private void doSetBleedingPilot(int i, int j, Actor actor1)
    {
        if(Mission.isSingle() || ((SndAircraft) (aircraft)).FM.isPlayers() && !Mission.isServer() || Mission.isNet() && Mission.isServer() && !aircraft.isNetPlayer())
        {
            if(j == 0)
                return;
            long l = 0x1d4c0 / j;
            if(astateBleedingNext[i] == 0L)
            {
                astateBleedingNext[i] = l;
                if(World.getPlayerAircraft() == actor && Config.isUSE_RENDER() && (!bIsAboutToBailout || astateBailoutStep <= 11) && astatePilotStates[i] < 100)
                {
                    boolean flag = i == astatePlayerIndex && !World.isPlayerDead();
                    if(j < 10)
                        HUD.log(flag ? "PlayerBLEED0" : astateHUDPilotHits[astatePilotFunctions[i]] + "BLEED0");
                    else
                        HUD.log(flag ? "PlayerBLEED1" : astateHUDPilotHits[astatePilotFunctions[i]] + "BLEED1");
                }
            } else
            {
                int k = 0x1d4c0 / (int)astateBleedingNext[i];
                long l1 = 0x1d4c0 / (k + j);
                if(l1 < 100L)
                    l1 = 100L;
                astateBleedingNext[i] = l1;
            }
            setBleedingTime(i);
        }
    }

    public boolean bleedingTest(int i)
    {
        return Time.current() > astateBleedingTimes[i];
    }

    public void setBleedingTime(int i)
    {
        astateBleedingTimes[i] = Time.current() + astateBleedingNext[i];
    }

    public void doSetWoundPilot(int i)
    {
        switch(i)
        {
        default:
            break;

        case 1: // '\001'
            ((SndAircraft) (aircraft)).FM.SensRoll *= 0.6F;
            ((SndAircraft) (aircraft)).FM.SensPitch *= 0.6F;
            if(((SndAircraft) (aircraft)).FM.isPlayers() && Config.isUSE_RENDER())
                HUD.log("PlayerArmHit");
            break;

        case 2: // '\002'
            ((SndAircraft) (aircraft)).FM.SensYaw *= 0.2F;
            if(((SndAircraft) (aircraft)).FM.isPlayers() && Config.isUSE_RENDER())
                HUD.log("PlayerLegHit");
            break;
        }
    }

    public void setPilotWound(Actor actor1, int i, int j)
    {
        setPilotWound(actor1, j, true);
    }

    public void setPilotWound(Actor actor1, int i, boolean flag)
    {
        if(!Actor.isValid(actor1))
            return;
        if(bIsMaster)
        {
            doSetWoundPilot(i);
            if(flag)
                netToMirrors(44, 0, i);
        } else
        if(flag)
            netToMaster(44, 0, i, actor1);
    }

    public void woundedPilot(Actor actor1, int i, int j)
    {
        switch(i)
        {
        default:
            break;

        case 1: // '\001'
            if(World.Rnd().nextFloat() < 0.18F && j > 4 && !armsWounded)
            {
                setPilotWound(actor1, i, true);
                armsWounded = true;
            }
            break;

        case 2: // '\002'
            if(j > 4 && !legsWounded)
            {
                setPilotWound(actor1, i, true);
                legsWounded = true;
            }
            break;
        }
    }

    private void doSetPilotState(int i, int j, Actor actor1)
    {
        if(j > 95)
            j = 100;
        if(World.getPlayerAircraft() == actor && Config.isUSE_RENDER() && (!bIsAboutToBailout || astateBailoutStep <= 11))
        {
            boolean flag = i == astatePlayerIndex && !World.isPlayerDead();
            if(astatePilotStates[i] < 100 && j == 100)
            {
                HUD.log(flag ? "PlayerHIT2" : astateHUDPilotHits[astatePilotFunctions[i]] + "HIT2");
                if(flag)
                {
                    World.setPlayerDead();
                    if(Mission.isNet())
                        Chat.sendLog(0, "gore_killed", (NetUser)NetEnv.host(), (NetUser)NetEnv.host());
                    if(Main3D.cur3D().cockpits != null && !World.isPlayerGunner())
                    {
                        int k = Main3D.cur3D().cockpits.length;
                        for(int l = 0; l < k; l++)
                        {
                            Cockpit cockpit = Main3D.cur3D().cockpits[l];
                            if(Actor.isValid(cockpit) && cockpit.astatePilotIndx() != i && !isPilotDead(cockpit.astatePilotIndx()) && !Mission.isNet() && AircraftHotKeys.isCockpitRealMode(l))
                                AircraftHotKeys.setCockpitRealMode(l, false);
                        }

                    }
                }
            } else
            if(astatePilotStates[i] < 66 && j > 66)
                HUD.log(flag ? "PlayerHIT1" : astateHUDPilotHits[astatePilotFunctions[i]] + "HIT1");
            else
            if(astatePilotStates[i] < 25 && j > 25)
                HUD.log(flag ? "PlayerHIT0" : astateHUDPilotHits[astatePilotFunctions[i]] + "HIT0");
        }
        byte byte0 = astatePilotStates[i];
        astatePilotStates[i] = (byte)j;
        if(bIsAboutToBailout && astateBailoutStep > i + 11)
        {
            aircraft.doWoundPilot(i, 0.0F);
            return;
        }
        if(j > 99)
        {
            aircraft.doWoundPilot(i, 0.0F);
            if(World.cur().isHighGore())
                aircraft.doMurderPilot(i);
            if(i == 0)
            {
                if(!bIsAboutToBailout)
                    Explosions.generateComicBulb(actor, "PK", 9F);
                FlightModel flightmodel = ((SndAircraft) (aircraft)).FM;
                if(flightmodel instanceof Maneuver)
                {
                    ((Maneuver)flightmodel).set_maneuver(44);
                    ((Maneuver)flightmodel).set_task(2);
                    flightmodel.setCapableOfTaxiing(false);
                }
            }
            if(i > 0 && !bIsAboutToBailout)
                Explosions.generateComicBulb(actor, "GunnerDown", 9F);
            EventLog.onPilotKilled(aircraft, i, actor1 != aircraft ? actor1 : null);
        } else
        if(byte0 < 66 && j > 66)
            EventLog.onPilotHeavilyWounded(aircraft, i);
        else
        if(byte0 < 25 && j > 25)
            EventLog.onPilotWounded(aircraft, i);
        if(j <= 99 && i > 0 && World.cur().diffCur.RealisticPilotVulnerability)
            aircraft.doWoundPilot(i, getPilotHealth(i));
    }

    private void doRemoveBodyFromPlane(int i)
    {
        aircraft.doRemoveBodyFromPlane(i);
    }

    public float getPilotHealth(int i)
    {
        if(i < 0 || i > ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).crew - 1)
            return 0.0F;
        else
            return 1.0F - (float)astatePilotStates[i] * 0.01F;
    }

    public boolean isPilotDead(int i)
    {
        if(i < 0 || i > ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).crew - 1)
            return true;
        return astatePilotStates[i] == 100;
    }

    public boolean isPilotParatrooper(int i)
    {
        if(i < 0 || i > ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).crew - 1)
            return true;
        return astatePilotStates[i] == 100 && astateBailoutStep > 11 + i;
    }

    public void setJamBullets(int i, int j)
    {
        if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[i] == null || ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[i].length <= j || ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[i][j] == null)
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
        if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons != null && ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[i] != null && ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[i][j] != null && ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[i][j].haveBullets())
        {
            if(actor == World.getPlayerAircraft() && ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[i][j].haveBullets())
                if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[i][j].bulletMassa() < 0.095F)
                    HUD.log(i <= 9 ? "FailedMGun" : "FailedTMGun");
                else
                    HUD.log("FailedCannon");
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[i][j].loadBullets(0);
        }
    }

    public boolean hitDaSilk()
    {
        if(!bIsMaster)
            return false;
        if(bIsAboutToBailout)
            return false;
        if(bCheckPlayerAircraft && actor == World.getPlayerAircraft())
            return false;
        if(!bIsEnableToBailout)
            return false;
        bIsAboutToBailout = true;
        FlightModel flightmodel = ((SndAircraft) (aircraft)).FM;
        Aircraft.debugprintln(aircraft, "I've had it, bailing out..");
        Explosions.generateComicBulb(actor, "Bailing", 5F);
        if(flightmodel instanceof Maneuver)
        {
            ((Maneuver)flightmodel).set_maneuver(44);
            ((Maneuver)flightmodel).set_task(2);
            flightmodel.setTakenMortalDamage(true, null);
        }
        return true;
    }

    public void setFlatTopString(Actor actor1, int i)
    {
        if(bIsMaster)
            netToMirrors(36, i, i, actor1);
    }

    private void doSetFlatTopString(Actor actor1, int i)
    {
        if(Actor.isValid(actor1) && (actor1 instanceof BigshipGeneric) && ((BigshipGeneric)actor1).getAirport() != null)
        {
            BigshipGeneric bigshipgeneric = (BigshipGeneric)actor1;
            if(i >= 0 && i < 255)
                bigshipgeneric.forceTowAircraft(aircraft, i);
            else
                bigshipgeneric.requestDetowAircraft(aircraft);
        }
    }

    public void setFMSFX(Actor actor1, int i, int j)
    {
        if(!Actor.isValid(actor1))
            return;
        if(bIsMaster)
            doSetFMSFX(i, j);
        else
            netToMaster(37, i, j, actor1);
    }

    private void doSetFMSFX(int i, int j)
    {
        ((SndAircraft) (aircraft)).FM.doRequestFMSFX(i, j);
    }

    public void setWingFold(Actor actor1, int i)
    {
        if(!Actor.isValid(actor1))
            return;
        if(actor1 != aircraft)
            return;
        if(!((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.bHasWingControl)
            return;
        if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.wingControl == (float)i)
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
        ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.wingControl = i;
    }

    public void setCockpitDoor(Actor actor1, int i)
    {
        if(!Actor.isValid(actor1))
            return;
        if(actor1 != aircraft)
            return;
        if(!((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.bHasCockpitDoorControl)
            return;
        if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.cockpitDoorControl == (float)i)
            return;
        if(!bIsMaster)
            return;
        if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.bMoveSideDoor)
        {
            doSetCockpitDoor(i, SIDE_DOOR);
            netToMirrors(44, SIDE_DOOR, i);
        } else
        {
            doSetCockpitDoor(i, COCKPIT_DOOR);
            netToMirrors(39, COCKPIT_DOOR, i);
        }
    }

    private void doSetCockpitDoor(int i, int j)
    {
        ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.setActiveDoor(j);
        ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.cockpitDoorControl = i;
        if(i == 0 && (Main.cur() instanceof Main3D) && aircraft == World.getPlayerAircraft() && HookPilot.current != null)
            HookPilot.current.doUp(false);
    }

    public void setArrestor(Actor actor1, int i)
    {
        if(!Actor.isValid(actor1))
            return;
        if(actor1 != aircraft)
            return;
        if(!((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.bHasArrestorControl)
            return;
        if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.arrestorControl == (float)i)
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
        ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.arrestorControl = i;
    }

    public void update(float f)
    {
        if(World.cur().diffCur.RealisticPilotVulnerability)
        {
            for(int i = 0; i < ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).crew; i++)
            {
                if(astateBleedingNext[i] > 0L && bleedingTest(i))
                {
                    hitPilot(actor, i, 1);
                    if(astatePilotStates[i] > 96)
                        astateBleedingNext[i] = 0L;
                    setBleedingTime(i);
                }
                if(astatePilotStates[0] > 60)
                    ((SndAircraft) (aircraft)).FM.setCapableOfBMP(false, actor);
            }

        }
        bCriticalStatePassed = bIsAboveCriticalSpeed ^ (aircraft.getSpeed(null) > 10D);
        if(bCriticalStatePassed)
        {
            bIsAboveCriticalSpeed = ((SndAircraft) (aircraft)).FM.getSpeed() > 10F;
            for(int j = 0; j < 4; j++)
                doSetTankState(null, j, astateTankStates[j]);

            for(int k = 0; k < ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.getNum(); k++)
                doSetEngineState(null, k, astateEngineStates[k]);

            for(int i1 = 0; i1 < ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.getNum(); i1++)
                doSetOilState(i1, astateOilStates[i1]);

        }
        bCriticalStatePassed = bIsAboveCondensateAlt ^ (((SndAircraft) (aircraft)).FM.getAltitude() > 7000F);
        if(bCriticalStatePassed)
        {
            bIsAboveCondensateAlt = ((SndAircraft) (aircraft)).FM.getAltitude() > 7000F;
            doSetCondensateState(bIsAboveCondensateAlt);
        }
        bCriticalStatePassed = bIsOnInadequateAOA ^ (((SndAircraft) (aircraft)).FM.getSpeed() > 17F && ((SndAircraft) (aircraft)).FM.getAOA() > 15F - ((SndAircraft) (aircraft)).FM.getAltitude() * 0.001F);
        if(bCriticalStatePassed)
        {
            bIsOnInadequateAOA = ((SndAircraft) (aircraft)).FM.getSpeed() > 17F && ((SndAircraft) (aircraft)).FM.getAOA() > 15F - ((SndAircraft) (aircraft)).FM.getAltitude() * 0.001F;
            setStallState(bIsOnInadequateAOA);
        }
        if(bIsMaster)
        {
            float f1 = 0.0F;
            for(int l = 0; l < 4; l++)
                f1 += astateTankStates[l] * astateTankStates[l];

            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).M.requestFuel(f1 * 0.12F * f);
            if(bDumpFuel && (aircraft instanceof TypeFuelDump))
                if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).M.fuel > ((TypeFuelDump)aircraft).getFuelReserve())
                    ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).M.fuel -= ((TypeFuelDump)aircraft).getFlowRate() * f;
                else
                    setDumpFuelState(false);
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).M.requestFuel(f1 * 0.12F * f);
            for(int j1 = 0; j1 < 4; j1++)
                switch(astateTankStates[j1])
                {
                case 3: // '\003'
                default:
                    break;

                case 1: // '\001'
                    if(World.Rnd().nextFloat(0.0F, 1.0F) < 0.0125F)
                    {
                        repairTank(j1);
                        Aircraft.debugprintln(aircraft, "Tank " + j1 + " protector clothes the hole - leak stops..");
                    }
                    // fall through

                case 2: // '\002'
                    if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).M.fuel <= 0.0F)
                    {
                        repairTank(j1);
                        Aircraft.debugprintln(aircraft, "Tank " + j1 + " runs out of fuel - leak stops..");
                    }
                    break;

                case 4: // '\004'
                    if(World.Rnd().nextFloat(0.0F, 1.0F) < 0.00333F)
                    {
                        hitTank(aircraft, j1, 1);
                        Aircraft.debugprintln(aircraft, "Tank " + j1 + " catches fire..");
                    }
                    break;

                case 5: // '\005'
                    if(((SndAircraft) (aircraft)).FM.getSpeed() > 111F && World.Rnd().nextFloat(0.0F, 1.0F) < 0.2F)
                    {
                        repairTank(j1);
                        Aircraft.debugprintln(aircraft, "Tank " + j1 + " cuts fire..");
                    }
                    if(World.Rnd().nextFloat() < 0.0048F)
                    {
                        Aircraft.debugprintln(aircraft, "Tank " + j1 + " fires up to the next stage..");
                        hitTank(aircraft, j1, 1);
                    }
                    if(!(actor instanceof Scheme1) || astateTankEffects[j1][0] == null || Math.abs(((Tuple3d) (((Actor) (astateTankEffects[j1][0])).pos.getRelPoint())).y) >= 1.8999999761581421D || ((Tuple3d) (((Actor) (astateTankEffects[j1][0])).pos.getRelPoint())).x <= -2.5999999046325684D || astatePilotStates[0] >= 96)
                        break;
                    hitPilot(actor, 0, 5);
                    if(astatePilotStates[0] < 96)
                        break;
                    hitPilot(actor, 0, 101 - astatePilotStates[0]);
                    if(((SndAircraft) (aircraft)).FM.isPlayers() && Mission.isNet() && !((SndAircraft) (aircraft)).FM.isSentBuryNote())
                        Chat.sendLogRnd(3, "gore_burnedcpt", aircraft, null);
                    break;

                case 6: // '\006'
                    if(((SndAircraft) (aircraft)).FM.getSpeed() > 140F && World.Rnd().nextFloat(0.0F, 1.0F) < 0.05F)
                    {
                        repairTank(j1);
                        Aircraft.debugprintln(aircraft, "Tank " + j1 + " cuts fire..");
                    }
                    if(World.Rnd().nextFloat() < 0.02F)
                    {
                        Aircraft.debugprintln(aircraft, "Tank " + j1 + " EXPLODES!.");
                        explodeTank(aircraft, j1);
                    }
                    if(!(actor instanceof Scheme1) || astateTankEffects[j1][0] == null || Math.abs(((Tuple3d) (((Actor) (astateTankEffects[j1][0])).pos.getRelPoint())).y) >= 1.8999999761581421D || ((Tuple3d) (((Actor) (astateTankEffects[j1][0])).pos.getRelPoint())).x <= -2.5999999046325684D || astatePilotStates[0] >= 96)
                        break;
                    hitPilot(actor, 0, 7);
                    if(astatePilotStates[0] < 96)
                        break;
                    hitPilot(actor, 0, 101 - astatePilotStates[0]);
                    if(((SndAircraft) (aircraft)).FM.isPlayers() && Mission.isNet() && !((SndAircraft) (aircraft)).FM.isSentBuryNote())
                        Chat.sendLogRnd(3, "gore_burnedcpt", aircraft, null);
                    break;
                }

            for(int k1 = 0; k1 < ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.getNum(); k1++)
            {
                if(astateEngineStates[k1] > 1)
                {
                    ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[k1].setReadyness(actor, ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[k1].getReadyness() - (float)astateEngineStates[k1] * 0.00025F * f);
                    if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[k1].getReadyness() < 0.2F && ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[k1].getReadyness() != 0.0F)
                        ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[k1].setEngineDies(actor);
                }
                switch(astateEngineStates[k1])
                {
                case 3: // '\003'
                    if(World.Rnd().nextFloat(0.0F, 1.0F) < 0.01F)
                    {
                        hitEngine(aircraft, k1, 1);
                        Aircraft.debugprintln(aircraft, "Engine " + k1 + " catches fire..");
                    }
                    break;

                case 4: // '\004'
                    if(((SndAircraft) (aircraft)).FM.getSpeed() > 111F && World.Rnd().nextFloat(0.0F, 1.0F) < 0.15F)
                    {
                        repairEngine(k1);
                        Aircraft.debugprintln(aircraft, "Engine " + k1 + " cuts fire..");
                    }
                    if((actor instanceof Scheme1) && World.Rnd().nextFloat() < 0.06F)
                    {
                        Aircraft.debugprintln(aircraft, "Engine 0 detonates and explodes, fatal damage level forced..");
                        aircraft.msgCollision(actor, "CF_D0", "CF_D0");
                    }
                    if((actor instanceof Scheme1) && astatePilotStates[0] < 96)
                    {
                        hitPilot(actor, 0, 4);
                        if(astatePilotStates[0] >= 96)
                        {
                            hitPilot(actor, 0, 101 - astatePilotStates[0]);
                            if(((SndAircraft) (aircraft)).FM.isPlayers() && Mission.isNet() && !((SndAircraft) (aircraft)).FM.isSentBuryNote())
                                Chat.sendLogRnd(3, "gore_burnedcpt", aircraft, null);
                        }
                    }
                    break;
                }
                if(astateOilStates[k1] > 0)
                    ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[k1].setReadyness(aircraft, ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[k1].getReadyness() - 0.001875F * f);
            }

            if(!(actor instanceof TypeFighter) || !(actor instanceof TypeBayDoor))
            {
                if(World.Rnd().nextFloat() < 0.25F && !((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.saveWeaponControl[3] && (!(actor instanceof TypeBomber) || ((SndAircraft) (aircraft)).FM.isReadyToReturn() || ((SndAircraft) (aircraft)).FM.isPlayers() && (((SndAircraft) (aircraft)).FM instanceof RealFlightModel) && ((RealFlightModel)((SndAircraft) (aircraft)).FM).isRealMode() && !((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.bHasBayDoors))
                    ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.BayDoorControl = 0.0F;
                bailout();
            } else
            if(World.Rnd().nextFloat() < 0.125F && !((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.saveWeaponControl[3] && (!(actor instanceof TypeBomber) || ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).AP.way.curr().Action != 3) && !((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.bHasBayDoors)
                ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.BayDoorControl = 0.0F;
        }
    }

    private void bailout()
    {
        if(bIsAboutToBailout)
            if(astateBailoutStep >= 0 && astateBailoutStep < 2)
            {
                if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.cockpitDoorControl > 0.5F && ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.getCockpitDoor() > 0.5F)
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
                    if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.cockpitDoorControl < 0.5F)
                        doRemoveBlister1();
                    break;

                case 3: // '\003'
                    doRemoveBlisters();
                    break;
                }
                if(bIsMaster)
                    netToMirrors(20, astateBailoutStep, 1);
                astateBailoutStep = (byte)(astateBailoutStep + 1);
                if(astateBailoutStep == 3 && (actor instanceof P_39))
                    astateBailoutStep = (byte)(astateBailoutStep + 1);
                if(astateBailoutStep == 4)
                    astateBailoutStep = 11;
            } else
            if(astateBailoutStep >= 11 && astateBailoutStep <= 19)
            {
                float f1 = ((SndAircraft) (aircraft)).FM.getSpeed();
                float f2 = (float)((Tuple3d) (((FlightModelMain) (((SndAircraft) (aircraft)).FM)).Loc)).z;
                float f3 = 140F;
                if((aircraft instanceof HE_162) || (aircraft instanceof GO_229) || (aircraft instanceof ME_262HGII) || (aircraft instanceof DO_335))
                    f3 = 9999.9F;
                if(Pitot.Indicator(f2, f1) < f3 && ((SndAircraft) (aircraft)).FM.getOverload() < 2.0F || !bIsMaster)
                {
                    int i = astateBailoutStep;
                    if(bIsMaster)
                        netToMirrors(20, astateBailoutStep, 1);
                    astateBailoutStep = (byte)(astateBailoutStep + 1);
                    if(i == 11)
                    {
                        ((SndAircraft) (aircraft)).FM.setTakenMortalDamage(true, null);
                        if((((SndAircraft) (aircraft)).FM instanceof Maneuver) && ((Maneuver)((SndAircraft) (aircraft)).FM).get_maneuver() != 44)
                        {
                            World.cur();
                            if(actor != World.getPlayerAircraft())
                                ((Maneuver)((SndAircraft) (aircraft)).FM).set_maneuver(44);
                        }
                    }
                    if(astatePilotStates[i - 11] < 99)
                    {
                        doRemoveBodyFromPlane(i - 10);
                        if(i == 11)
                        {
                            if(aircraft instanceof HE_162)
                            {
                                ((HE_162)aircraft).doEjectCatapult();
                                astateBailoutStep = 51;
                                ((SndAircraft) (aircraft)).FM.setTakenMortalDamage(true, null);
                                ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.WeaponControl[0] = false;
                                ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.WeaponControl[1] = false;
                                astateBailoutStep = -1;
                                return;
                            }
                            if(aircraft instanceof GO_229)
                            {
                                ((GO_229)aircraft).doEjectCatapult();
                                astateBailoutStep = 51;
                                ((SndAircraft) (aircraft)).FM.setTakenMortalDamage(true, null);
                                ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.WeaponControl[0] = false;
                                ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.WeaponControl[1] = false;
                                astateBailoutStep = -1;
                                return;
                            }
                            if(aircraft instanceof DO_335)
                            {
                                ((DO_335)aircraft).doEjectCatapult();
                                astateBailoutStep = 51;
                                ((SndAircraft) (aircraft)).FM.setTakenMortalDamage(true, null);
                                ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.WeaponControl[0] = false;
                                ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.WeaponControl[1] = false;
                                astateBailoutStep = -1;
                                return;
                            }
                            if(aircraft instanceof ME_262HGII)
                            {
                                ((ME_262HGII)aircraft).doEjectCatapult();
                                astateBailoutStep = 51;
                                ((SndAircraft) (aircraft)).FM.setTakenMortalDamage(true, null);
                                ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.WeaponControl[0] = false;
                                ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.WeaponControl[1] = false;
                                astateBailoutStep = -1;
                                return;
                            }
                        }
                        setPilotState(aircraft, i - 11, 100, false);
                        if(!actor.isNet() || actor.isNetMaster())
                        {
                            try
                            {
                                Hook localHook = actor.findHook("_ExternalBail0" + (i - 10));
                                if(localHook != null)
                                {
                                    Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, World.Rnd().nextFloat(-45F, 45F), 0.0F, 0.0F);
                                    localHook.computePos(actor, actor.pos.getAbs(), localLoc);
                                    new Paratrooper(actor, actor.getArmy(), i - 11, localLoc, ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).Vwld);
                                    ((SndAircraft) (aircraft)).FM.setTakenMortalDamage(true, null);
                                    if(i == 11)
                                    {
                                        ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.WeaponControl[0] = false;
                                        ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.WeaponControl[1] = false;
                                    }
                                    if(i > 10 && i <= 19)
                                        EventLog.onBailedOut(aircraft, i - 11);
                                }
                            }
                            catch(Exception exception) { }
                            if(astateBailoutStep == 19 && actor == World.getPlayerAircraft() && !World.isPlayerGunner() && ((SndAircraft) (aircraft)).FM.brakeShoe)
                                MsgDestroy.Post(Time.current() + 1000L, aircraft);
                        }
                    }
                }
            }
    }

    private final void doRemoveBlister1()
    {
        if(aircraft.hierMesh().chunkFindCheck("Blister1_D0") != -1 && getPilotHealth(0) > 0.0F)
        {
            if(aircraft instanceof JU_88NEW)
            {
                float f = ((SndAircraft) (aircraft)).FM.getAltitude() - Landscape.HQ_Air((float)((Tuple3d) (((FlightModelMain) (((SndAircraft) (aircraft)).FM)).Loc)).x, (float)((Tuple3d) (((FlightModelMain) (((SndAircraft) (aircraft)).FM)).Loc)).y);
                if(f < 0.3F)
                {
                    aircraft.blisterRemoved(1);
                    return;
                }
            }
            aircraft.hierMesh().hideSubTrees("Blister1_D0");
            Wreckage wreckage = new Wreckage((ActorHMesh)actor, aircraft.hierMesh().chunkFind("Blister1_D0"));
            wreckage.collide(false);
            Vector3d vector3d = new Vector3d();
            vector3d.set(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).Vwld);
            wreckage.setSpeed(vector3d);
            aircraft.blisterRemoved(1);
        }
    }

    private final void doRemoveBlisters()
    {
        for(int i = 2; i < 10; i++)
            if(aircraft.hierMesh().chunkFindCheck("Blister" + i + "_D0") != -1 && getPilotHealth(i - 1) > 0.0F)
            {
                aircraft.hierMesh().hideSubTrees("Blister" + i + "_D0");
                Wreckage wreckage = new Wreckage((ActorHMesh)actor, aircraft.hierMesh().chunkFind("Blister" + i + "_D0"));
                wreckage.collide(false);
                Vector3d vector3d = new Vector3d();
                vector3d.set(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).Vwld);
                wreckage.setSpeed(vector3d);
                aircraft.blisterRemoved(i);
            }

    }

    public void netUpdate(boolean flag, boolean flag1, NetMsgInput netmsginput)
        throws IOException
    {
        if(flag1)
        {
            if(flag)
            {
                int i = netmsginput.readUnsignedByte();
                int k = netmsginput.readUnsignedByte();
                int i1 = netmsginput.readUnsignedByte();
                Actor actor1 = null;
                if(netmsginput.available() > 0)
                {
                    NetObj netobj = netmsginput.readNetObj();
                    if(netobj != null)
                        actor1 = (Actor)netobj.superObj();
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

                case 44: // ','
                    setPilotWound(actor1, k, i1);
                    break;

                case 45: // '-'
                    setBleedingPilot(actor1, k, i1);
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
                    ((TypeDockable)aircraft).typeDockableRequestAttach(actor1, k, true);
                    break;

                case 35: // '#'
                    ((TypeDockable)aircraft).typeDockableRequestDetach(actor1, k, true);
                    break;

                case 32: // ' '
                    ((TypeDockable)aircraft).typeDockableRequestAttach(actor1, k, i1 > 0);
                    break;

                case 33: // '!'
                    ((TypeDockable)aircraft).typeDockableRequestDetach(actor1, k, i1 > 0);
                    break;

                case 37: // '%'
                    setFMSFX(actor1, k, i1);
                    break;

                case 41: // ')'
                    setBeacon(actor1, k, i1, true);
                    break;

                case 42: // '*'
                    setGyroAngle(actor1, k, i1, true);
                    break;

                case 43: // '+'
                    setSpreadAngle(actor1, k, i1, true);
                    break;

                case 46: // '.'
                    setFuzeStates(actor1, k, i1, true);
                    break;
                    
                // TODO: ++ Added Code for Net Replication ++
                case _AS_SELECTED_ROCKET_HOOK:
                	this.setRocketHook(actor1, k, true);
                    break;
                    
                case _AS_WEAPON_FIRE_MODE:
                	this.setWeaponFireMode(actor1, k, true);
                    break;
                    
                case _AS_WEAPON_RELEASE_DELAY:
                    int releaseDelayLow = k;
                    int releaseDelayHigh = i1 << 8;
                	this.setWeaponReleaseDelay(actor1, releaseDelayLow + releaseDelayHigh, true);
                    break;
                // TODO: -- Added Code for Net Replication --
                    
                }
            } else
            {
                ((Actor) (aircraft)).net.postTo(((Actor) (aircraft)).net.masterChannel(), new NetMsgGuaranted(netmsginput, netmsginput.available() <= 3 ? 0 : 1));
            }
        } else
        {
            if(((Actor) (aircraft)).net.isMirrored())
                ((Actor) (aircraft)).net.post(new NetMsgGuaranted(netmsginput, netmsginput.available() <= 3 ? 0 : 1));
            int j = netmsginput.readUnsignedByte();
            int l = netmsginput.readUnsignedByte();
            int j1 = netmsginput.readUnsignedByte();
            Actor actor2 = null;
            if(netmsginput.available() > 0)
            {
                NetObj netobj1 = netmsginput.readNetObj();
                if(netobj1 != null)
                    actor2 = (Actor)netobj1.superObj();
            }
            switch(j)
            {
            case 32: // ' '
            case 33: // '!'
            case 47: // '/'
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

            case 44: // ','
                doSetWoundPilot(j1);
                break;

            case 45: // '-'
                doSetBleedingPilot(l, j1, actor2);
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
                ((TypeDockable)aircraft).typeDockableDoAttachToDrone(actor2, l);
                break;

            case 35: // '#'
                ((TypeDockable)aircraft).typeDockableDoDetachFromDrone(l);
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
                doSetCockpitDoor(l, j1);
                break;

            case 40: // '('
                doSetArrestor(j1);
                break;

            case 48: // '0'
                setAirShowSmokeType(l);
                break;

            case 49: // '1'
                setAirShowSmokeEnhanced(l == 1);
                break;

            case 50: // '2'
                doSetDumpFuelState(l == 1);
                break;

            case 41: // ')'
                doSetBeacon(actor2, l, j1);
                break;

            case 42: // '*'
                doSetGyroAngle(actor2, l, j1);
                break;

            case 43: // '+'
                doSetSpreadAngle(actor2, l, j1);
                break;

            case 46: // '.'
                doSetFuzeStates(actor2, l, j1);
                break;
                
            // TODO: ++ Added Code for Net Replication ++
            case _AS_SELECTED_ROCKET_HOOK:
            	this.doSetRocketHook(actor2, l);
                break;
            case _AS_WEAPON_FIRE_MODE:
            	this.doSetWeaponFireMode(actor2, l);
                break;
            case _AS_WEAPON_RELEASE_DELAY:
                int releaseDelayLow = l;
                int releaseDelayHigh = j1 << 8;
            	this.doSetWeaponReleaseDelay(actor2, releaseDelayLow + releaseDelayHigh);
                break;
            // TODO: -- Added Code for Net Replication --

            }
        }
    }

    public void netReplicate(NetMsgGuaranted netmsgguaranted)
        throws IOException
    {
        int i;
        if(aircraft instanceof FW_190A8MSTL)
            i = 1;
        else
            i = ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.getNum();
        for(int j = 0; j < i; j++)
        {
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[j].replicateToNet(netmsgguaranted);
            netmsgguaranted.writeByte(astateEngineStates[j]);
        }

        for(int k = 0; k < 4; k++)
            netmsgguaranted.writeByte(astateTankStates[k]);

        for(int l = 0; l < i; l++)
            netmsgguaranted.writeByte(astateOilStates[l]);

        netmsgguaranted.writeByte((bShowSmokesOn ? 1 : 0) | (bNavLightsOn ? 2 : 0) | (bLandingLightOn ? 4 : 0));
        netmsgguaranted.writeByte(astateCockpitState);
        netmsgguaranted.writeByte(astateBailoutStep);
        if(aircraft instanceof TypeBomber)
            ((TypeBomber)aircraft).typeBomberReplicateToNet(netmsgguaranted);
        if(aircraft instanceof TypeDockable)
            ((TypeDockable)aircraft).typeDockableReplicateToNet(netmsgguaranted);
        if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.bHasWingControl)
        {
            netmsgguaranted.writeByte((int)((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.wingControl);
            netmsgguaranted.writeByte((int)(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.getWing() * 255F));
        }
        if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.bHasCockpitDoorControl)
            netmsgguaranted.writeByte((int)((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.cockpitDoorControl);
        netmsgguaranted.writeByte(bIsEnableToBailout ? 1 : 0);
        if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.bHasArrestorControl)
        {
            netmsgguaranted.writeByte((int)((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.arrestorControl);
            netmsgguaranted.writeByte((int)(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.getArrestor() * 255F));
        }
        for(int i1 = 0; i1 < i; i1++)
            netmsgguaranted.writeByte(astateSootStates[i1]);

        if(bWantBeaconsNet)
            netmsgguaranted.writeByte(beacon);
        if(aircraft instanceof TypeHasToKG)
        {
            netmsgguaranted.writeByte(torpedoGyroAngle);
            netmsgguaranted.writeByte(torpedoSpreadAngle);
        }
        netmsgguaranted.writeShort(armingSeed);
        setArmingSeeds();
        if(aircraft.isNetPlayer())
        {
            int j1 = (int)Math.sqrt(World.cur().userCoverMashineGun - 100F) / 6;
            j1 += 6 * ((int)Math.sqrt(World.cur().userCoverCannon - 100F) / 6);
            j1 += 36 * ((int)Math.sqrt(World.cur().userCoverRocket - 100F) / 6);
            netmsgguaranted.writeByte(j1);
        }
        if(externalStoresDropped)
            netmsgguaranted.writeByte(1);
        else
            netmsgguaranted.writeByte(0);
        if(World.getPlayerAircraft() == aircraft)
            replicateFuzeStatesToNet(World.cur().userCfg.fuzeType, Fuze_EL_AZ.getFuzeMode(), World.cur().userCfg.bombDelay);
        netmsgguaranted.writeByte(iAirShowSmoke);
        netmsgguaranted.writeByte(bAirShowSmokeEnhanced ? 1 : 0);
        netmsgguaranted.writeByte(bDumpFuel ? 1 : 0);
        // TODO: ++ Added Code for Net Replication ++
        netmsgguaranted.writeByte(aircraft.FM.CT.rocketHookSelected);
        netmsgguaranted.writeByte(aircraft.FM.CT.weaponFireMode);
        int releaseDelayLow = (byte)aircraft.FM.CT.weaponReleaseDelay;
        int releaseDelayHigh = (byte)((int)aircraft.FM.CT.weaponReleaseDelay >> 8);
        netmsgguaranted.writeByte(releaseDelayLow);
        netmsgguaranted.writeByte(releaseDelayHigh);
        // TODO: -- Added Code for Net Replication --
    }

    public void netFirstUpdate(NetMsgInput netmsginput)
        throws IOException
    {
        int i;
        if(aircraft instanceof FW_190A8MSTL)
            i = 1;
        else
            i = ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.getNum();
        for(int j = 0; j < i; j++)
        {
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[j].replicateFromNet(netmsginput);
            int k = netmsginput.readUnsignedByte();
            doSetEngineState(null, j, k);
        }

        for(int l = 0; l < 4; l++)
        {
            int i1 = netmsginput.readUnsignedByte();
            doSetTankState(null, l, i1);
        }

        for(int j1 = 0; j1 < i; j1++)
        {
            int k1 = netmsginput.readUnsignedByte();
            doSetOilState(j1, k1);
        }

        int l1 = netmsginput.readUnsignedByte();
        doSetAirShowState((l1 & 1) != 0);
        doSetNavLightsState((l1 & 2) != 0);
        doSetLandingLightState((l1 & 4) != 0);
        l1 = netmsginput.readUnsignedByte();
        doSetCockpitState(l1);
        l1 = netmsginput.readUnsignedByte();
        if(l1 != 0)
        {
            bIsAboutToBailout = true;
            astateBailoutStep = (byte)l1;
            for(int i2 = 1; i2 <= Math.min(astateBailoutStep, 3); i2++)
                if(aircraft.hierMesh().chunkFindCheck("Blister" + (i2 - 1) + "_D0") != -1)
                    aircraft.hierMesh().hideSubTrees("Blister" + (i2 - 1) + "_D0");

            if(astateBailoutStep >= 11 && astateBailoutStep <= 20)
            {
                int k2 = astateBailoutStep;
                if(astateBailoutStep == 20)
                    k2 = 19;
                k2 -= 11;
                for(int i3 = 0; i3 <= k2; i3++)
                    doRemoveBodyFromPlane(i3 + 1);

            }
        }
        if(netmsginput.available() == 0)
            return;
        netmsginput.fixed();
        try
        {
            if(aircraft instanceof TypeBomber)
                ((TypeBomber)aircraft).typeBomberReplicateFromNet(netmsginput);
        }
        catch(Exception e)
        {
            netmsginput.reset();
        }
        if(netmsginput.available() == 0)
            return;
        netmsginput.fixed();
        try
        {
            if(aircraft instanceof TypeDockable)
                ((TypeDockable)aircraft).typeDockableReplicateFromNet(netmsginput);
        }
        catch(Exception e)
        {
            netmsginput.reset();
        }
        if(netmsginput.available() == 0)
            return;
        if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.bHasWingControl)
        {
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.wingControl = netmsginput.readUnsignedByte();
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.forceWing((float)netmsginput.readUnsignedByte() / 255F);
            aircraft.wingfold_ = ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.getWing();
        }
        if(netmsginput.available() == 0)
            return;
        if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.bHasCockpitDoorControl)
        {
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.cockpitDoorControl = netmsginput.readUnsignedByte();
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.forceCockpitDoor(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.cockpitDoorControl);
        }
        if(netmsginput.available() == 0)
            return;
        bIsEnableToBailout = netmsginput.readUnsignedByte() == 1;
        if(netmsginput.available() == 0)
            return;
        if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.bHasArrestorControl)
        {
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.arrestorControl = netmsginput.readUnsignedByte();
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.forceArrestor((float)netmsginput.readUnsignedByte() / 255F);
            aircraft.arrestor_ = ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.getArrestor();
        }
        if(netmsginput.available() == 0)
            return;
        for(int j2 = 0; j2 < i; j2++)
        {
            l1 = netmsginput.readUnsignedByte();
            doSetSootState(j2, l1);
        }

        if(netmsginput.available() == 0)
            return;
        if(bWantBeaconsNet)
            beacon = netmsginput.readUnsignedByte();
        if(aircraft instanceof TypeHasToKG)
        {
            torpedoGyroAngle = netmsginput.readUnsignedByte();
            torpedoSpreadAngle = netmsginput.readUnsignedByte();
        }
        armingSeed = netmsginput.readUnsignedShort();
        setArmingSeeds();
        if(aircraft.isNetPlayer())
        {
            int l2 = netmsginput.readUnsignedByte();
            int j3 = l2 % 6;
            float f = Property.floatValue(aircraft.getClass(), "LOSElevation", 0.75F);
            updConvDist(j3, 0, f);
            l2 = (l2 - j3) / 6;
            j3 = l2 % 6;
            updConvDist(j3, 1, f);
            l2 = (l2 - j3) / 6;
            updConvDist(l2, 2, f);
        }
        l1 = netmsginput.readUnsignedByte();
        if(!externalStoresDropped && l1 > 0)
        {
            externalStoresDropped = true;
            aircraft.dropExternalStores(false);
        }
        setAirShowSmokeType(netmsginput.readUnsignedByte());
        setAirShowSmokeEnhanced(netmsginput.readUnsignedByte() == 1);
        doSetDumpFuelState(netmsginput.readUnsignedByte() == 1);
        // TODO: ++ Added Code for Net Replication ++
        if(netmsginput.available() == 0)
            return;
        this.doSetRocketHook(aircraft, netmsginput.readUnsignedByte());
        this.doSetWeaponFireMode(aircraft, netmsginput.readUnsignedByte());
        int releaseDelayLow = netmsginput.readUnsignedByte();
        int releaseDelayHigh = netmsginput.readUnsignedByte() << 8;
        this.doSetWeaponReleaseDelay(aircraft, releaseDelayLow + releaseDelayHigh);
        // TODO: -- Added Code for Net Replication --
    }

    void updConvDist(int i, int j, float f)
    {
        float f1 = (float)i * (float)i * 36F + 100F;
        try
        {
            if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[j] != null)
                if(j == 2)
                {
                    for(int k = 0; k < ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[j].length; k++)
                        if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[j][k] instanceof RocketGun)
                            ((RocketGun)((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[j][k]).setConvDistance(f1, f);

                } else
                {
                    for(int l = 0; l < ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[j].length; l++)
                        if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[j][l] instanceof MGunAircraftGeneric)
                            ((MGunAircraftGeneric)((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[j][l]).setConvDistance(f1, f);

                }
        }
        catch(Exception exception)
        {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void setArmingSeeds()
    {
        armingRnd = new RangeRandom(armingSeed);
        try
        {
            if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[2] != null)
            {
                for(int i = 0; i < ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[2].length; i++)
                    if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[2][i] instanceof RocketGun)
                        ((RocketGun)((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[2][i]).setSpreadRnd(armingRnd.nextInt());

            }
            if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[3] != null)
            {
                for(int j = 0; j < ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[3].length; j++)
                    if((((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[3][j] instanceof BombGun) && !(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[3][j] instanceof FuelTankGun))
                    {
                        int l = armingRnd.nextInt();
                        ((BombGun)((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[3][j]).setRnd(l);
                    }

            }
        }
        catch(Exception exception)
        {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void netToMaster(int i, int j, int k)
    {
        netToMaster(i, j, k, null);
    }

    public void netToMaster(int i, int j, int k, Actor actor1)
    {
        if(!bIsMaster)
        {
            if(!aircraft.netNewAState_isEnable(true))
                return;
            if(itemsToMaster == null)
                itemsToMaster = new Item[47];
            if(sendedMsg(itemsToMaster, i, j, k, actor1))
                return;
            try
            {
                NetMsgGuaranted netmsgguaranted = aircraft.netNewAStateMsg(true);
                if(netmsgguaranted != null)
                {
                    netmsgguaranted.writeByte((byte)i);
                    netmsgguaranted.writeByte((byte)j);
                    netmsgguaranted.writeByte((byte)k);
                    com.maddox.il2.engine.ActorNet actornet = null;
                    if(Actor.isValid(actor1))
                        actornet = actor1.net;
                    if(actornet != null)
                        netmsgguaranted.writeNetObj(actornet);
                    aircraft.netSendAStateMsg(true, netmsgguaranted);
                    return;
                }
            }
            catch(Exception exception)
            {
                System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        }
    }

    private void netToMirrors(int i, int j, int k)
    {
        netToMirrors(i, j, k, null);
    }

    public void netToMirrors(int i, int j, int k, Actor actor1)
    {
        if(!aircraft.netNewAState_isEnable(false))
            return;
        if(itemsToMirrors == null)
            itemsToMirrors = new Item[47];
        if(sendedMsg(itemsToMirrors, i, j, k, actor1))
            return;
        try
        {
            NetMsgGuaranted netmsgguaranted = aircraft.netNewAStateMsg(false);
            if(netmsgguaranted != null)
            {
                netmsgguaranted.writeByte((byte)i);
                netmsgguaranted.writeByte((byte)j);
                netmsgguaranted.writeByte((byte)k);
                com.maddox.il2.engine.ActorNet actornet = null;
                if(Actor.isValid(actor1))
                    actornet = actor1.net;
                if(actornet != null)
                    netmsgguaranted.writeNetObj(actornet);
                aircraft.netSendAStateMsg(false, netmsgguaranted);
                return;
            }
        }
        catch(Exception exception)
        {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private boolean sendedMsg(Item aitem[], int i, int j, int k, Actor actor1)
    {
        if(i < 0 || i >= aitem.length)
            return false;
        Item item = aitem[i];
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

    private int cvt(float f, float f1, float f2, int i, int j)
    {
        f = Math.min(Math.max(f, f1), f2);
        return (int)((float)i + ((float)(j - i) * (f - f1)) / (f2 - f1));
    }

    private float cvt(int i, int j, int k, float f, float f1)
    {
        i = Math.min(Math.max(i, j), k);
        return f + ((f1 - f) * (float)(i - j)) / (float)(k - j);
    }

    public float getGyroAngle()
    {
        if(torpedoGyroAngle == 0)
            return 0.0F;
        else
            return cvt(torpedoGyroAngle, 1, 65535, -50F, 50F);
    }

    public void setGyroAngle(float f)
    {
        int i = 0;
        if(f != 0.0F)
            i = cvt(f, -50F, 50F, 1, 65535);
        torpedoGyroAngle = i;
    }

    public void replicateGyroAngleToNet()
    {
        int i = cvt(getGyroAngle(), -50F, 50F, 1, 65535);
        int j = i & 0xff00;
        j >>= 8;
        int k = i & 0xff;
        setGyroAngle(actor, j, k, false);
    }

    private void setGyroAngle(Actor actor1, int i, int j, boolean flag)
    {
        if(!Actor.isValid(actor1))
            return;
        if(flag)
            doSetGyroAngle(actor1, i, j);
        if(bIsMaster)
            netToMirrors(42, i, j);
        else
            netToMaster(42, i, j, actor1);
    }

    private void doSetGyroAngle(Actor actor1, int i, int j)
    {
        torpedoGyroAngle = i << 8 | j;
    }

    public int getSpreadAngle()
    {
        return torpedoSpreadAngle;
    }

    public void setSpreadAngle(int i)
    {
        torpedoSpreadAngle = i;
        if(torpedoSpreadAngle < 0)
            torpedoSpreadAngle = 0;
        if(torpedoSpreadAngle > 30)
            torpedoSpreadAngle = 30;
    }

    public void replicateSpreadAngleToNet()
    {
        setSpreadAngle(actor, getSpreadAngle(), 0, false);
    }

    private void setSpreadAngle(Actor actor1, int i, int j, boolean flag)
    {
        if(!Actor.isValid(actor1))
            return;
        if(flag)
            doSetSpreadAngle(actor1, i, 0);
        if(bIsMaster)
            netToMirrors(43, i, 0);
        else
            netToMaster(43, i, 0, actor1);
    }

    private void doSetSpreadAngle(Actor actor1, int i, int j)
    {
        setSpreadAngle(i);
    }

    public void replicateFuzeStatesToNet(int i, int j, float f)
    {
        int k = (int)(f * 2.0F);
        int l = i & 0xf;
        l <<= 4;
        l += j & 0xf;
        setFuzeStates(actor, l, k, false);
    }

    private void setFuzeStates(Actor actor1, int i, int j, boolean flag)
    {
        if(!Actor.isValid(actor1))
            return;
        if(flag)
            doSetFuzeStates(actor1, i, j);
        if(bIsMaster)
            netToMirrors(46, i, j);
        else
            netToMaster(46, i, j, actor1);
    }

    private void doSetFuzeStates(Actor actor1, int i, int j)
    {
        float f = (float)j / 2.0F;
        int k = (i & 0xf0) >> 4;
        int l = i & 0xf;
        if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[3] != null)
        {
            for(int i1 = 0; i1 < ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[3].length; i1++)
                if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[3][i1] != null && (((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[3][i1] instanceof BombGun) && ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[3][i1].countBullets() != 0)
                {
                    BombGun bombgun = (BombGun)((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.Weapons[3][i1];
                    bombgun.setFuzeForMirror(k, l, f);
                }

        }
    }
    
    // TODO: ++ Added Code for Net Replication ++
    public void replicateRocketHookToNet(int theRocketHook)
    {
    	setRocketHook(actor, theRocketHook, false);
    }

    private void setRocketHook(Actor theActor, int theRocketHook, boolean applySetting)
    {
        if(!Actor.isValid(theActor))
            return;
        if(applySetting)
        	doSetRocketHook(theActor, theRocketHook);
        if(bIsMaster)
            netToMirrors(_AS_SELECTED_ROCKET_HOOK, theRocketHook, 0);
        else
            netToMaster(_AS_SELECTED_ROCKET_HOOK, theRocketHook, 0, theActor);
    }

    private void doSetRocketHook(Actor theActor, int theRocketHook)
    {
    	aircraft.FM.CT.doSetRocketHook(theRocketHook);
    }
    
    public void replicateWeaponFireModeToNet(int theWeaponFireMode)
    {
    	setWeaponFireMode(actor, theWeaponFireMode, false);
    }

    private void setWeaponFireMode(Actor theActor, int theWeaponFireMode, boolean applySetting)
    {
        if(!Actor.isValid(theActor))
            return;
        if(applySetting)
        	doSetWeaponFireMode(theActor, theWeaponFireMode);
        if(bIsMaster)
            netToMirrors(_AS_WEAPON_FIRE_MODE, theWeaponFireMode, 0);
        else
            netToMaster(_AS_WEAPON_FIRE_MODE, theWeaponFireMode, 0, theActor);
    }

    private void doSetWeaponFireMode(Actor theActor, int theWeaponFireMode)
    {
    	aircraft.FM.CT.doSetWeaponFireMode(theWeaponFireMode);
    }

    public void replicateWeaponReleaseDelay(long theWeaponReleaseDelay)
    {
    	setWeaponReleaseDelay(actor, theWeaponReleaseDelay, false);
    }

    private void setWeaponReleaseDelay(Actor theActor, long theWeaponReleaseDelay, boolean applySetting)
    {
        if(!Actor.isValid(theActor))
            return;
        if(applySetting)
        	doSetWeaponReleaseDelay(theActor, theWeaponReleaseDelay);
        int releaseDelayLow = (byte)aircraft.FM.CT.weaponReleaseDelay;
        int releaseDelayHigh = (byte)((int)aircraft.FM.CT.weaponReleaseDelay >> 8);
        if(bIsMaster)
            netToMirrors(_AS_WEAPON_RELEASE_DELAY, releaseDelayLow, releaseDelayHigh);
        else
            netToMaster(_AS_WEAPON_RELEASE_DELAY, releaseDelayLow, releaseDelayHigh, theActor);
    }

    private void doSetWeaponReleaseDelay(Actor theActor, long theWeaponReleaseDelay)
    {
    	aircraft.FM.CT.doSetWeaponReleaseDelay(theWeaponReleaseDelay);
    }
    // TODO: -- Added Code for Net Replication --
    
    public static final boolean __DEBUG_SPREAD__ = false;
    private static final Loc astateCondensateDispVector = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
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
    public static final int _AS_BEACONS = 41;
    public static final int _AS_GYROANGLE = 42;
    public static final int _AS_SPREADANGLE = 43;
    public static final int _AS_PILOT_WOUNDED = 44;
    public static final int _AS_PILOT_BLEEDING = 45;
    public static final int _AS_FUZE_STATES = 46;
    public static final int _AS_COUNT_CODES = 47;
    // TODO: ++ Added Code for Net Replication ++
    // The following 3 net codes were in use before but undocumented
    public static final int _AS_AIRSHOW_SMOKE = 48;
    public static final int _AS_AIRSHOW_SMOKE_ENHANCED = 49;
    public static final int _AS_DUMP_FUEL = 50;
    // The following 3 net codes are new
    public static final int _AS_SELECTED_ROCKET_HOOK = 51;
    public static final int _AS_WEAPON_FIRE_MODE = 52;
    public static final int _AS_WEAPON_RELEASE_DELAY = 53;
    // TODO: -- Added Code for Net Replication --
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
    protected long bleedingTime;
    public long astateBleedingTimes[];
    public long astateBleedingNext[];
    private boolean legsWounded;
    private boolean armsWounded;
    public int torpedoGyroAngle;
    public int torpedoSpreadAngle;
    public static final int spreadAngleLimit = 30;
    private int beacon;
    private boolean bWantBeaconsNet;
    public static final int MAX_NUMBER_OF_BEACONS = 32;
    public boolean listenLorenzBlindLanding;
    public boolean isAAFIAS;
    public boolean listenYGBeacon;
    public boolean listenNDBeacon;
    public boolean listenRadioStation;
    public Actor hayrakeCarrier;
    public String hayrakeCode;
    public static int hudLogBeaconId = HUD.makeIdLog();
    public boolean externalStoresDropped;
    public int armingSeed;
    public RangeRandom armingRnd;
    private static final String astateOilStrings[] = {
        "3DO/Effects/Aircraft/OilSmokeSPD.eff", "3DO/Effects/Aircraft/OilSmokeTSPD.eff", null, null
    };
    private static final String astateTankStrings[] = {
        null, null, null, "3DO/Effects/Aircraft/RedLeakTSPD.eff", null, null, "3DO/Effects/Aircraft/RedLeakTSPD.eff", null, null, "3DO/Effects/Aircraft/TankSmokeSPD.eff", 
        "3DO/Effects/Aircraft/TankSmokeTSPD.eff", null, "3DO/Effects/Aircraft/BlackHeavySPD.eff", "3DO/Effects/Aircraft/BlackHeavyTSPD.eff", null, "3DO/Effects/Aircraft/FireSPD.eff", "3DO/Effects/Aircraft/BlackHeavySPD.eff", "3DO/Effects/Aircraft/BlackHeavyTSPD.eff", "3DO/Effects/Aircraft/FireSPD.eff", "3DO/Effects/Aircraft/BlackHeavySPD.eff", 
        "3DO/Effects/Aircraft/BlackHeavyTSPD.eff", null, null, null, "3DO/Effects/Aircraft/RedLeakGND.eff", null, null, "3DO/Effects/Aircraft/RedLeakGND.eff", null, null, 
        "3DO/Effects/Aircraft/BlackMediumGND.eff", null, null, "3DO/Effects/Aircraft/BlackHeavyGND.eff", null, null, "3DO/Effects/Aircraft/FireGND.eff", "3DO/Effects/Aircraft/BlackHeavyGND.eff", null, "3DO/Effects/Aircraft/FireGND.eff", 
        "3DO/Effects/Aircraft/BlackHeavyGND.eff", null
    };
    private static final String astateEngineStrings[] = {
        null, null, null, "3DO/Effects/Aircraft/GraySmallSPD.eff", "3DO/Effects/Aircraft/GraySmallTSPD.eff", null, "3DO/Effects/Aircraft/EngineSmokeSPD.eff", "3DO/Effects/Aircraft/EngineSmokeTSPD.eff", null, "3DO/Effects/Aircraft/EngineHeavySPD.eff", 
        "3DO/Effects/Aircraft/EngineHeavyTSPD.eff", null, "3DO/Effects/Aircraft/FireSPD.eff", "3DO/Effects/Aircraft/EngineHeavySPD.eff", "3DO/Effects/Aircraft/EngineHeavyTSPD.eff", null, null, null, "3DO/Effects/Aircraft/GraySmallGND.eff", null, 
        null, "3DO/Effects/Aircraft/BlackMediumGND.eff", null, null, "3DO/Effects/Aircraft/BlackHeavyGND.eff", null, null, "3DO/Effects/Aircraft/FireGND.eff", "3DO/Effects/Aircraft/BlackHeavyGND.eff", null
    };
    private static final String astateCondensateStrings[] = {
        null, "3DO/Effects/Aircraft/CondensateTSPD.eff"
    };
    private static final String astateStallStrings[] = {
        null, "3DO/Effects/Aircraft/StallTSPD.eff"
    };
    public static final String astateHUDPilotHits[] = {
        "Player", "Pilot", "CPilot", "NGunner", "TGunner", "WGunner", "VGunner", "RGunner", "EngMas", "BombMas", 
        "RadMas", "ObsMas"
    };
    private static boolean bCriticalStatePassed = false;
    private boolean bIsAboveCriticalSpeed;
    private boolean bIsAboveCondensateAlt;
    private boolean bIsOnInadequateAOA;
    public boolean bShowSmokesOn;
    public boolean bNavLightsOn;
    public boolean bLandingLightOn;
    public boolean bWingTipLExists;
    public boolean bWingTipRExists;
    private boolean bIsMaster;
    public Actor actor;
    public AircraftLH aircraft;
    public byte astatePilotStates[];
    public byte astatePilotFunctions[] = {
        1, 7, 7, 7, 7, 7, 7, 7, 7
    };
    public int astatePlayerIndex;
    public boolean bIsAboutToBailout;
    public boolean bIsEnableToBailout;
    public byte astateBailoutStep;
    public int astateCockpitState;
    public byte astateOilStates[];
    private Eff3DActor astateOilEffects[][] = {
        new Eff3DActor[2], new Eff3DActor[2], new Eff3DActor[2], new Eff3DActor[2], new Eff3DActor[2], new Eff3DActor[2], new Eff3DActor[2], new Eff3DActor[2]
    };
    public byte astateTankStates[];
    private Eff3DActor astateTankEffects[][] = {
        new Eff3DActor[3], new Eff3DActor[3], new Eff3DActor[3], new Eff3DActor[3]
    };
    public byte astateEngineStates[];
    private Eff3DActor astateEngineEffects[][] = {
        new Eff3DActor[3], new Eff3DActor[3], new Eff3DActor[3], new Eff3DActor[3], new Eff3DActor[3], new Eff3DActor[3], new Eff3DActor[3], new Eff3DActor[3]
    };
    public byte astateSootStates[];
    public Eff3DActor astateSootEffects[][] = {
        new Eff3DActor[2], new Eff3DActor[2], new Eff3DActor[2], new Eff3DActor[2], new Eff3DActor[2], new Eff3DActor[2], new Eff3DActor[2], new Eff3DActor[2]
    };
    public Eff3DActor astateCondensateEffects[];
    private Eff3DActor astateStallEffects[];
    private Eff3DActor astateAirShowEffects[];
    private Eff3DActor astateDumpFuelEffects[];
    private Eff3DActor astateNavLightsEffects[];
    private LightPointActor astateNavLightsLights[];
    public Eff3DActor astateLandingLightEffects[];
    private LightPointActor astateLandingLightLights[];
    public String astateEffectChunks[];
    public static final int astateEffectsDispTanks = 0;
    public static final int astateEffectsDispEngines = 4;
    public static final int astateEffectsDispLights = 12;
    public static final int astateEffectsDispLandingLights = 18;
    public static final int astateEffectsDispOilfilters = 22;
    public static boolean bCheckPlayerAircraft = true;
    private Item itemsToMaster[];
    private Item itemsToMirrors[];
    private int iAirShowSmoke;
    private boolean bAirShowSmokeEnhanced;
    private boolean bDumpFuel;
    private int COCKPIT_DOOR;
    private int SIDE_DOOR;

}
