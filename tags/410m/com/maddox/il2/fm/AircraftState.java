// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AircraftState.java

package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.DifficultySettings;
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
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.order.OrdersTree;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.air.AR_234B2;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.AircraftLH;
import com.maddox.il2.objects.air.Cockpit;
import com.maddox.il2.objects.air.DO_335;
import com.maddox.il2.objects.air.DO_335A0;
import com.maddox.il2.objects.air.DO_335V13;
import com.maddox.il2.objects.air.FW_190A8MSTL;
import com.maddox.il2.objects.air.GO_229;
import com.maddox.il2.objects.air.HE_162;
import com.maddox.il2.objects.air.JU_88NEW;
import com.maddox.il2.objects.air.ME_262HGII;
import com.maddox.il2.objects.air.P_39;
import com.maddox.il2.objects.air.Paratrooper;
import com.maddox.il2.objects.air.Scheme0;
import com.maddox.il2.objects.air.Scheme1;
import com.maddox.il2.objects.air.TypeBomber;
import com.maddox.il2.objects.air.TypeDockable;
import com.maddox.il2.objects.air.TypeHasToKG;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.vehicles.radios.Beacon;
import com.maddox.il2.objects.vehicles.radios.TypeHasAAFIAS;
import com.maddox.il2.objects.vehicles.radios.TypeHasHayRake;
import com.maddox.il2.objects.vehicles.radios.TypeHasLorenzBlindLanding;
import com.maddox.il2.objects.vehicles.radios.TypeHasRadioStation;
import com.maddox.il2.objects.vehicles.radios.TypeHasYGBeacon;
import com.maddox.il2.objects.weapons.MGunAircraftGeneric;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.rts.MsgDestroy;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.CmdMusic;
import com.maddox.util.HashMapExt;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

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
        com.maddox.il2.engine.Loc loc;
        com.maddox.il2.engine.Loc loc1;
        int i;
        loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        loc1 = new Loc();
        actor = actor1;
        if(actor1 instanceof com.maddox.il2.objects.air.Aircraft)
            aircraft = (com.maddox.il2.objects.air.AircraftLH)actor1;
        else
            throw new RuntimeException("Can not cast aircraft structure into a non-aircraft entity.");
        bIsMaster = flag;
        i = 0;
_L3:
        if(i >= 4) goto _L2; else goto _L1
_L1:
        try
        {
            astateEffectChunks[i + 0] = actor.findHook("_Tank" + (i + 1) + "Burn").chunkName();
            astateEffectChunks[i + 0] = astateEffectChunks[i + 0].substring(0, astateEffectChunks[i + 0].length() - 1);
            com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: Tank " + i + " FX attached to '" + astateEffectChunks[i + 0] + "' substring..");
        }
        catch(java.lang.Exception exception) { }
        continue; /* Loop/switch isn't completed */
        java.lang.Exception exception5;
        exception5;
        throw exception5;
        i++;
          goto _L3
_L2:
        i = 0;
_L6:
        if(i >= aircraft.FM.EI.getNum()) goto _L5; else goto _L4
_L4:
        try
        {
            astateEffectChunks[i + 4] = actor.findHook("_Engine" + (i + 1) + "Smoke").chunkName();
            astateEffectChunks[i + 4] = astateEffectChunks[i + 4].substring(0, astateEffectChunks[i + 4].length() - 1);
            com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: Engine " + i + " FX attached to '" + astateEffectChunks[i + 4] + "' substring..");
        }
        catch(java.lang.Exception exception1) { }
        continue; /* Loop/switch isn't completed */
        java.lang.Exception exception6;
        exception6;
        throw exception6;
        i++;
          goto _L6
_L5:
        i = 0;
_L9:
        if(i >= astateNavLightsEffects.length) goto _L8; else goto _L7
_L7:
        try
        {
            astateEffectChunks[i + 12] = actor.findHook("_NavLight" + i).chunkName();
            astateEffectChunks[i + 12] = astateEffectChunks[i + 12].substring(0, astateEffectChunks[i + 12].length() - 1);
            com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: Nav. Lamp #" + i + " attached to '" + astateEffectChunks[i + 12] + "' substring..");
            com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(aircraft, "_NavLight" + i);
            loc1.set(1.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
            hooknamed.computePos(actor, loc, loc1);
            com.maddox.JGP.Point3d point3d = loc1.getPoint();
            astateNavLightsLights[i] = new LightPointActor(new LightPoint(), point3d);
            if(i < 2)
                astateNavLightsLights[i].light.setColor(1.0F, 0.1F, 0.1F);
            else
            if(i < 4)
                astateNavLightsLights[i].light.setColor(0.0F, 1.0F, 0.0F);
            else
                astateNavLightsLights[i].light.setColor(0.7F, 0.7F, 0.7F);
            astateNavLightsLights[i].light.setEmit(0.0F, 0.0F);
            actor.draw.lightMap().put("_NavLight" + i, astateNavLightsLights[i]);
        }
        catch(java.lang.Exception exception2) { }
        continue; /* Loop/switch isn't completed */
        java.lang.Exception exception7;
        exception7;
        throw exception7;
        i++;
          goto _L9
_L8:
        i = 0;
_L12:
        if(i >= 4) goto _L11; else goto _L10
_L10:
        try
        {
            astateEffectChunks[i + 18] = actor.findHook("_LandingLight0" + i).chunkName();
            astateEffectChunks[i + 18] = astateEffectChunks[i + 18].substring(0, astateEffectChunks[i + 18].length() - 1);
            com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: Landing Lamp #" + i + " attached to '" + astateEffectChunks[i + 18] + "' substring..");
            com.maddox.il2.engine.HookNamed hooknamed1 = new HookNamed(aircraft, "_LandingLight0" + i);
            loc1.set(1.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
            hooknamed1.computePos(actor, loc, loc1);
            com.maddox.JGP.Point3d point3d1 = loc1.getPoint();
            astateLandingLightLights[i] = new LightPointActor(new LightPoint(), point3d1);
            astateLandingLightLights[i].light.setColor(0.4941176F, 0.9098039F, 0.9607843F);
            astateLandingLightLights[i].light.setEmit(0.0F, 0.0F);
            actor.draw.lightMap().put("_LandingLight0" + i, astateLandingLightLights[i]);
        }
        catch(java.lang.Exception exception3) { }
        continue; /* Loop/switch isn't completed */
        java.lang.Exception exception8;
        exception8;
        throw exception8;
        i++;
          goto _L12
_L11:
        i = 0;
_L15:
        if(i >= aircraft.FM.EI.getNum()) goto _L14; else goto _L13
_L13:
        try
        {
            astateEffectChunks[i + 22] = actor.findHook("_Engine" + (i + 1) + "Oil").chunkName();
            astateEffectChunks[i + 22] = astateEffectChunks[i + 22].substring(0, astateEffectChunks[i + 22].length() - 1);
            com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "AS: Oilfilter " + i + " FX attached to '" + astateEffectChunks[i + 22] + "' substring..");
        }
        catch(java.lang.Exception exception4) { }
        continue; /* Loop/switch isn't completed */
        java.lang.Exception exception9;
        exception9;
        throw exception9;
        i++;
          goto _L15
_L14:
        for(int j = 0; j < astateEffectChunks.length; j++)
            if(astateEffectChunks[j] == null)
                astateEffectChunks[j] = "AChunkNameYouCanNeverFind";

        return;
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
            if(s == null)
                continue;
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
            if(!flag)
                continue;
            java.lang.String s = astateCondensateStrings[1];
            if(s == null)
                continue;
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

    public void wantBeaconsNet(boolean flag)
    {
        if(flag == bWantBeaconsNet)
            return;
        bWantBeaconsNet = flag;
        if(com.maddox.il2.ai.World.getPlayerAircraft() != actor)
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
        if(com.maddox.il2.game.Main.cur().mission.hasBeacons(actor.getArmy()))
        {
            int i = com.maddox.il2.game.Main.cur().mission.getBeacons(actor.getArmy()).size();
            if(beacon < i)
                setBeacon(beacon + 1);
            else
                setBeacon(0);
        }
    }

    public void beaconMinus()
    {
        if(com.maddox.il2.game.Main.cur().mission.hasBeacons(actor.getArmy()))
        {
            int i = com.maddox.il2.game.Main.cur().mission.getBeacons(actor.getArmy()).size();
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

    private void setBeacon(com.maddox.il2.engine.Actor actor1, int i, int j, boolean flag)
    {
        doSetBeacon(actor1, i, j);
        if(com.maddox.il2.game.Mission.isSingle() || !bWantBeaconsNet)
            return;
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(bIsMaster)
            netToMirrors(42, i, j, actor1);
        else
        if(!flag)
            netToMaster(42, i, j, actor1);
    }

    private void doSetBeacon(com.maddox.il2.engine.Actor actor1, int i, int j)
    {
        if(actor != actor1)
            return;
        if(com.maddox.il2.ai.World.getPlayerAircraft() != actor)
            return;
        if(i > 0)
        {
            com.maddox.il2.engine.Actor actor2 = (com.maddox.il2.engine.Actor)com.maddox.il2.game.Main.cur().mission.getBeacons(actor.getArmy()).get(i - 1);
            boolean flag = com.maddox.il2.objects.air.Aircraft.hasPlaneZBReceiver(aircraft);
            if(!flag && ((actor2 instanceof com.maddox.il2.objects.vehicles.radios.TypeHasYGBeacon) || (actor2 instanceof com.maddox.il2.objects.vehicles.radios.TypeHasHayRake)))
            {
                int k = i - beacon;
                beacon = i;
                if(k > 0)
                    beaconPlus();
                else
                    beaconMinus();
                return;
            }
            java.lang.String s = com.maddox.il2.objects.vehicles.radios.Beacon.getBeaconID(i - 1);
            if(aircraft.getPilotsCount() == 1 || aircraft.FM.EI.engines.length == 1)
                com.maddox.il2.game.Main3D.cur3D().ordersTree.setFrequency(null);
            if(actor2 instanceof com.maddox.il2.objects.vehicles.radios.TypeHasYGBeacon)
            {
                com.maddox.il2.game.Main3D.cur3D().ordersTree.setFrequency(com.maddox.il2.game.order.OrdersTree.FREQ_FRIENDLY);
                com.maddox.il2.game.HUD.log(hudLogBeaconId, "BeaconYG", new java.lang.Object[] {
                    s
                });
                startListeningYGBeacon();
            } else
            if(actor2 instanceof com.maddox.il2.objects.vehicles.radios.TypeHasHayRake)
            {
                com.maddox.il2.game.Main3D.cur3D().ordersTree.setFrequency(com.maddox.il2.game.order.OrdersTree.FREQ_FRIENDLY);
                com.maddox.il2.game.HUD.log(hudLogBeaconId, "BeaconYE", new java.lang.Object[] {
                    s
                });
                java.lang.String s1 = com.maddox.il2.game.Main.cur().mission.getHayrakeCodeOfCarrier(actor2);
                startListeningHayrake(actor2, s1);
            } else
            if(actor2 instanceof com.maddox.il2.objects.vehicles.radios.TypeHasLorenzBlindLanding)
            {
                com.maddox.il2.game.Main3D.cur3D().ordersTree.setFrequency(com.maddox.il2.game.order.OrdersTree.FREQ_FRIENDLY);
                com.maddox.il2.game.HUD.log(hudLogBeaconId, "BeaconBA", new java.lang.Object[] {
                    s
                });
                startListeningLorenzBlindLanding();
                isAAFIAS = false;
                if(actor2 instanceof com.maddox.il2.objects.vehicles.radios.TypeHasAAFIAS)
                    isAAFIAS = true;
            } else
            if(actor2 instanceof com.maddox.il2.objects.vehicles.radios.TypeHasRadioStation)
            {
                com.maddox.il2.objects.vehicles.radios.TypeHasRadioStation typehasradiostation = (com.maddox.il2.objects.vehicles.radios.TypeHasRadioStation)actor2;
                s = typehasradiostation.getStationID();
                java.lang.String s2 = com.maddox.rts.Property.stringValue(actor2.getClass(), "i18nName", s);
                com.maddox.il2.game.HUD.log(hudLogBeaconId, "BeaconRS", new java.lang.Object[] {
                    s2
                });
                startListeningRadioStation(s);
            } else
            {
                com.maddox.il2.game.HUD.log(hudLogBeaconId, "BeaconND", new java.lang.Object[] {
                    s
                });
                startListeningNDBeacon();
            }
        } else
        {
            com.maddox.il2.game.HUD.log(hudLogBeaconId, "BeaconNONE");
            stopListeningBeacons();
            com.maddox.il2.game.Main3D.cur3D().ordersTree.setFrequency(com.maddox.il2.game.order.OrdersTree.FREQ_FRIENDLY);
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
        com.maddox.sound.CmdMusic.setCurrentVolume(0.0F);
    }

    public void startListeningNDBeacon()
    {
        stopListeningLorenzBlindLanding();
        listenYGBeacon = false;
        listenNDBeacon = true;
        listenRadioStation = false;
        stopListeningHayrake();
        com.maddox.sound.CmdMusic.setCurrentVolume(0.0F);
    }

    public void startListeningRadioStation(java.lang.String s)
    {
        stopListeningLorenzBlindLanding();
        listenYGBeacon = false;
        listenNDBeacon = false;
        listenRadioStation = true;
        stopListeningHayrake();
        java.lang.String s1 = s.replace(' ', '_');
        int i = com.maddox.il2.game.Mission.curYear();
        int j = com.maddox.il2.game.Mission.curMonth();
        int k = com.maddox.il2.game.Mission.curDay();
        java.lang.String s2 = "" + i;
        java.lang.String s3 = "";
        java.lang.String s4 = "";
        if(j < 10)
            s3 = "0" + j;
        else
            s3 = "" + j;
        if(k < 10)
            s4 = "0" + k;
        else
            s4 = "" + k;
        java.lang.String as[] = {
            s2 + s3 + s4, s2 + s3 + "XX", s2 + "XX" + s4, s2 + "XXXX"
        };
        for(int l = 0; l < as.length; l++)
        {
            java.io.File file = new File("./samples/Music/Radio/" + s1 + "/" + as[l]);
            if(file.exists())
            {
                com.maddox.sound.CmdMusic.setPath("Music/Radio/" + s1 + "/" + as[l], true);
                com.maddox.sound.CmdMusic.play();
                return;
            }
        }

        com.maddox.sound.CmdMusic.setPath("Music/Radio/" + s1, true);
        com.maddox.sound.CmdMusic.play();
    }

    public void stopListeningBeacons()
    {
        stopListeningLorenzBlindLanding();
        listenYGBeacon = false;
        listenNDBeacon = false;
        listenRadioStation = false;
        stopListeningHayrake();
        aircraft.stopMorseSounds();
        com.maddox.sound.CmdMusic.setCurrentVolume(0.0F);
    }

    public void startListeningHayrake(com.maddox.il2.engine.Actor actor1, java.lang.String s)
    {
        stopListeningLorenzBlindLanding();
        hayrakeCarrier = actor1;
        hayrakeCode = s;
        listenYGBeacon = false;
        listenNDBeacon = false;
        listenRadioStation = false;
        aircraft.stopMorseSounds();
        com.maddox.sound.CmdMusic.setCurrentVolume(0.0F);
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
        com.maddox.sound.CmdMusic.setCurrentVolume(0.0F);
    }

    public void stopListeningLorenzBlindLanding()
    {
        listenLorenzBlindLanding = false;
        isAAFIAS = false;
        aircraft.stopMorseSounds();
    }

    public void preLoadRadioStation(com.maddox.il2.engine.Actor actor1)
    {
        com.maddox.il2.objects.vehicles.radios.TypeHasRadioStation typehasradiostation = (com.maddox.il2.objects.vehicles.radios.TypeHasRadioStation)actor1;
        java.lang.String s = typehasradiostation.getStationID();
        startListeningRadioStation(s);
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

    public void setBleedingPilot(com.maddox.il2.engine.Actor actor1, int i, int j)
    {
        setBleedingPilot(actor1, i, j, true);
    }

    public void setBleedingPilot(com.maddox.il2.engine.Actor actor1, int i, int j, boolean flag)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(bIsMaster)
        {
            doSetBleedingPilot(i, j, actor1);
            if(flag)
                netToMirrors(47, i, j);
        } else
        if(flag)
            netToMaster(47, i, j, actor1);
    }

    private void doSetBleedingPilot(int i, int j, com.maddox.il2.engine.Actor actor1)
    {
        if(com.maddox.il2.game.Mission.isSingle() || aircraft.FM.isPlayers() && !com.maddox.il2.game.Mission.isServer() || com.maddox.il2.game.Mission.isNet() && com.maddox.il2.game.Mission.isServer() && !aircraft.isNetPlayer())
        {
            long l = 0x1d4c0 / j;
            if(astateBleedingNext[i] == 0L)
            {
                astateBleedingNext[i] = l;
                if(com.maddox.il2.ai.World.getPlayerAircraft() == actor && com.maddox.il2.engine.Config.isUSE_RENDER() && (!bIsAboutToBailout || astateBailoutStep <= 11) && astatePilotStates[i] < 100)
                {
                    boolean flag = i == astatePlayerIndex && !com.maddox.il2.ai.World.isPlayerDead();
                    if(j > 10)
                        com.maddox.il2.game.HUD.log(flag ? "PlayerBLEED0" : astateHUDPilotHits[astatePilotFunctions[i]] + "BLEED0");
                    else
                        com.maddox.il2.game.HUD.log(flag ? "PlayerBLEED1" : astateHUDPilotHits[astatePilotFunctions[i]] + "BLEED1");
                }
            } else
            {
                int k = 30000 / (int)astateBleedingNext[i];
                long l1 = 30000 / (k + j);
                if(l1 < 100L)
                    l1 = 100L;
                astateBleedingNext[i] = l1;
            }
            setBleedingTime(i);
        }
    }

    public boolean bleedingTest(int i)
    {
        return com.maddox.rts.Time.current() > astateBleedingTimes[i];
    }

    public void setBleedingTime(int i)
    {
        astateBleedingTimes[i] = com.maddox.rts.Time.current() + astateBleedingNext[i];
    }

    public void doSetWoundPilot(int i)
    {
        switch(i)
        {
        default:
            break;

        case 1: // '\001'
            aircraft.FM.SensRoll *= 0.6F;
            aircraft.FM.SensPitch *= 0.6F;
            if(aircraft.FM.isPlayers() && com.maddox.il2.engine.Config.isUSE_RENDER())
                com.maddox.il2.game.HUD.log("PlayerArmHit");
            break;

        case 2: // '\002'
            aircraft.FM.SensYaw *= 0.2F;
            if(aircraft.FM.isPlayers() && com.maddox.il2.engine.Config.isUSE_RENDER())
                com.maddox.il2.game.HUD.log("PlayerLegHit");
            break;
        }
    }

    public void setPilotWound(com.maddox.il2.engine.Actor actor1, int i, int j)
    {
        setPilotWound(actor1, j, true);
    }

    public void setPilotWound(com.maddox.il2.engine.Actor actor1, int i, boolean flag)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(bIsMaster)
        {
            doSetWoundPilot(i);
            if(flag)
                netToMirrors(46, 0, i);
        } else
        if(flag)
            netToMaster(46, 0, i, actor1);
    }

    public void woundedPilot(com.maddox.il2.engine.Actor actor1, int i, int j)
    {
        switch(i)
        {
        default:
            break;

        case 1: // '\001'
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.18F && j > 4 && !armsWounded)
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
            aircraft.doWoundPilot(i, 0.0F);
            return;
        }
        if(j > 99)
        {
            aircraft.doWoundPilot(i, 0.0F);
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
        if(j <= 99 && i > 0 && com.maddox.il2.ai.World.cur().diffCur.RealisticPilotVulnerability)
            aircraft.doWoundPilot(i, getPilotHealth(i));
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
        if(com.maddox.il2.ai.World.cur().diffCur.RealisticPilotVulnerability)
        {
            for(int i = 0; i < aircraft.FM.crew; i++)
            {
                if(astateBleedingNext[i] > 0L && bleedingTest(i))
                {
                    hitPilot(actor, i, 1);
                    if(astatePilotStates[i] > 96)
                        astateBleedingNext[i] = 0L;
                    setBleedingTime(i);
                }
                if(astatePilotStates[0] > 60)
                    aircraft.FM.setCapableOfBMP(false, actor);
            }

        }
        bCriticalStatePassed = bIsAboveCriticalSpeed != (aircraft.getSpeed(null) > 10D);
        if(bCriticalStatePassed)
        {
            bIsAboveCriticalSpeed = aircraft.FM.getSpeed() > 10F;
            for(int j = 0; j < 4; j++)
                doSetTankState(null, j, astateTankStates[j]);

            for(int k = 0; k < aircraft.FM.EI.getNum(); k++)
                doSetEngineState(null, k, astateEngineStates[k]);

            for(int l = 0; l < aircraft.FM.EI.getNum(); l++)
                doSetOilState(l, astateOilStates[l]);

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
            for(int i1 = 0; i1 < 4; i1++)
                f1 += astateTankStates[i1] * astateTankStates[i1];

            aircraft.FM.M.requestFuel(f1 * 0.12F * f);
            for(int j1 = 0; j1 < 4; j1++)
                switch(astateTankStates[j1])
                {
                case 3: // '\003'
                default:
                    break;

                case 1: // '\001'
                    if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.0125F)
                    {
                        repairTank(j1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Tank " + j1 + " protector clothes the hole - leak stops..");
                    }
                    // fall through

                case 2: // '\002'
                    if(aircraft.FM.M.fuel <= 0.0F)
                    {
                        repairTank(j1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Tank " + j1 + " runs out of fuel - leak stops..");
                    }
                    break;

                case 4: // '\004'
                    if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.00333F)
                    {
                        hitTank(aircraft, j1, 1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Tank " + j1 + " catches fire..");
                    }
                    break;

                case 5: // '\005'
                    if(aircraft.FM.getSpeed() > 111F && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.2F)
                    {
                        repairTank(j1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Tank " + j1 + " cuts fire..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.0048F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Tank " + j1 + " fires up to the next stage..");
                        hitTank(aircraft, j1, 1);
                    }
                    if(!(actor instanceof com.maddox.il2.objects.air.Scheme1) || astateTankEffects[j1][0] == null || java.lang.Math.abs(astateTankEffects[j1][0].pos.getRelPoint().y) >= 1.8999999761581421D || astateTankEffects[j1][0].pos.getRelPoint().x <= -2.5999999046325684D || astatePilotStates[0] >= 96)
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
                        repairTank(j1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Tank " + j1 + " cuts fire..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Tank " + j1 + " EXPLODES!.");
                        explodeTank(aircraft, j1);
                    }
                    if(!(actor instanceof com.maddox.il2.objects.air.Scheme1) || astateTankEffects[j1][0] == null || java.lang.Math.abs(astateTankEffects[j1][0].pos.getRelPoint().y) >= 1.8999999761581421D || astateTankEffects[j1][0].pos.getRelPoint().x <= -2.5999999046325684D || astatePilotStates[0] >= 96)
                        break;
                    hitPilot(actor, 0, 7);
                    if(astatePilotStates[0] < 96)
                        break;
                    hitPilot(actor, 0, 101 - astatePilotStates[0]);
                    if(aircraft.FM.isPlayers() && com.maddox.il2.game.Mission.isNet() && !aircraft.FM.isSentBuryNote())
                        com.maddox.il2.net.Chat.sendLogRnd(3, "gore_burnedcpt", aircraft, null);
                    break;
                }

            for(int k1 = 0; k1 < aircraft.FM.EI.getNum(); k1++)
            {
                if(astateEngineStates[k1] > 1)
                {
                    aircraft.FM.EI.engines[k1].setReadyness(actor, aircraft.FM.EI.engines[k1].getReadyness() - (float)astateEngineStates[k1] * 0.00025F * f);
                    if(aircraft.FM.EI.engines[k1].getReadyness() < 0.2F && aircraft.FM.EI.engines[k1].getReadyness() != 0.0F)
                        aircraft.FM.EI.engines[k1].setEngineDies(actor);
                }
                switch(astateEngineStates[k1])
                {
                case 3: // '\003'
                    if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.01F)
                    {
                        hitEngine(aircraft, k1, 1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Engine " + k1 + " catches fire..");
                    }
                    break;

                case 4: // '\004'
                    if(aircraft.FM.getSpeed() > 111F && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.15F)
                    {
                        repairEngine(k1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(aircraft, "Engine " + k1 + " cuts fire..");
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
                if(astateOilStates[k1] > 0)
                    aircraft.FM.EI.engines[k1].setReadyness(aircraft, aircraft.FM.EI.engines[k1].getReadyness() - 0.001875F * f);
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
        byte byte0;
        if(!bIsAboutToBailout)
            break MISSING_BLOCK_LABEL_1107;
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
            break MISSING_BLOCK_LABEL_1107;
        }
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
            break MISSING_BLOCK_LABEL_1107;
        }
        if(astateBailoutStep < 11 || astateBailoutStep > 19)
            break MISSING_BLOCK_LABEL_1107;
        float f = aircraft.FM.getSpeed();
        float f1 = (float)aircraft.FM.Loc.z;
        float f2 = 140F;
        if((aircraft instanceof com.maddox.il2.objects.air.HE_162) || (aircraft instanceof com.maddox.il2.objects.air.GO_229) || (aircraft instanceof com.maddox.il2.objects.air.ME_262HGII) || (aircraft instanceof com.maddox.il2.objects.air.DO_335))
            f2 = 9999.9F;
        if((com.maddox.il2.fm.Pitot.Indicator(f1, f) >= f2 || aircraft.FM.getOverload() >= 2.0F) && bIsMaster)
            break MISSING_BLOCK_LABEL_1107;
        byte0 = astateBailoutStep;
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
        if(astatePilotStates[byte0 - 11] >= 99)
            break MISSING_BLOCK_LABEL_1107;
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
        if(actor.isNet() && !actor.isNetMaster())
            break MISSING_BLOCK_LABEL_1107;
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
        break MISSING_BLOCK_LABEL_1055;
        java.lang.Exception exception1;
        exception1;
        throw exception1;
        if(astateBailoutStep == 19 && actor == com.maddox.il2.ai.World.getPlayerAircraft() && !com.maddox.il2.ai.World.isPlayerGunner() && aircraft.FM.brakeShoe)
            com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current() + 1000L, aircraft);
    }

    private final void doRemoveBlister1()
    {
        if(aircraft.hierMesh().chunkFindCheck("Blister1_D0") != -1 && getPilotHealth(0) > 0.0F)
        {
            if(aircraft instanceof com.maddox.il2.objects.air.JU_88NEW)
            {
                float f = aircraft.FM.getAltitude() - com.maddox.il2.engine.Landscape.HQ_Air((float)aircraft.FM.Loc.x, (float)aircraft.FM.Loc.y);
                if(f < 0.3F)
                {
                    aircraft.blisterRemoved(1);
                    return;
                }
            }
            aircraft.hierMesh().hideSubTrees("Blister1_D0");
            com.maddox.il2.objects.Wreckage wreckage = new Wreckage((com.maddox.il2.engine.ActorHMesh)actor, aircraft.hierMesh().chunkFind("Blister1_D0"));
            wreckage.collide(false);
            com.maddox.JGP.Vector3d vector3d = new Vector3d();
            vector3d.set(aircraft.FM.Vwld);
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
                com.maddox.il2.objects.Wreckage wreckage = new Wreckage((com.maddox.il2.engine.ActorHMesh)actor, aircraft.hierMesh().chunkFind("Blister" + i + "_D0"));
                wreckage.collide(false);
                com.maddox.JGP.Vector3d vector3d = new Vector3d();
                vector3d.set(aircraft.FM.Vwld);
                wreckage.setSpeed(vector3d);
                aircraft.blisterRemoved(i);
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

                case 46: // '.'
                    setPilotWound(actor1, k, i1);
                    break;

                case 47: // '/'
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

                case 42: // '*'
                    setBeacon(actor1, k, i1, true);
                    break;

                case 44: // ','
                    setGyroAngle(actor1, k, i1, true);
                    break;

                case 45: // '-'
                    setSpreadAngle(actor1, k, i1, true);
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
            case 41: // ')'
            case 43: // '+'
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

            case 46: // '.'
                doSetWoundPilot(j1);
                break;

            case 47: // '/'
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

            case 42: // '*'
                doSetBeacon(actor2, l, j1);
                break;

            case 44: // ','
                doSetGyroAngle(actor2, l, j1);
                break;

            case 45: // '-'
                doSetSpreadAngle(actor2, l, j1);
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

        if(bWantBeaconsNet)
            netmsgguaranted.writeByte(beacon);
        if(aircraft instanceof com.maddox.il2.objects.air.TypeHasToKG)
        {
            netmsgguaranted.writeByte(torpedoGyroAngle);
            netmsgguaranted.writeByte(torpedoSpreadAngle);
        }
        netmsgguaranted.writeShort(aircraft.armingSeed);
        setArmingSeeds(aircraft.armingSeed);
        if(aircraft.isNetPlayer())
        {
            int j1 = (int)java.lang.Math.sqrt(com.maddox.il2.ai.World.cur().userCoverMashineGun - 100F) / 6;
            j1 += 6 * ((int)java.lang.Math.sqrt(com.maddox.il2.ai.World.cur().userCoverCannon - 100F) / 6);
            j1 += 36 * ((int)java.lang.Math.sqrt(com.maddox.il2.ai.World.cur().userCoverRocket - 100F) / 6);
            netmsgguaranted.writeByte(j1);
        }
        if(externalStoresDropped)
            netmsgguaranted.writeByte(1);
        else
            netmsgguaranted.writeByte(0);
    }

    public void netFirstUpdate(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        int k2;
        if(aircraft instanceof com.maddox.il2.objects.air.FW_190A8MSTL)
            k2 = 1;
        else
            k2 = aircraft.FM.EI.getNum();
        for(int i = 0; i < k2; i++)
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

        for(int k = 0; k < k2; k++)
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
                int l2 = astateBailoutStep;
                if(astateBailoutStep == 20)
                    l2 = 19;
                l2 -= 11;
                for(int i1 = 0; i1 <= l2; i1++)
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
        for(int j1 = 0; j1 < k2; j1++)
        {
            j2 = netmsginput.readUnsignedByte();
            doSetSootState(j1, j2);
        }

        if(bWantBeaconsNet)
            beacon = netmsginput.readUnsignedByte();
        if(aircraft instanceof com.maddox.il2.objects.air.TypeHasToKG)
        {
            torpedoGyroAngle = netmsginput.readUnsignedByte();
            torpedoSpreadAngle = netmsginput.readUnsignedByte();
        }
        aircraft.armingSeed = netmsginput.readUnsignedShort();
        setArmingSeeds(aircraft.armingSeed);
        if(aircraft.isNetPlayer())
        {
            int i3 = netmsginput.readUnsignedByte();
            int j3 = i3 % 6;
            float f = com.maddox.rts.Property.floatValue(aircraft.getClass(), "LOSElevation", 0.75F);
            updConvDist(j3, 0, f);
            i3 = (i3 - j3) / 6;
            j3 = i3 % 6;
            updConvDist(j3, 1, f);
            i3 = (i3 - j3) / 6;
            updConvDist(i3, 2, f);
        }
        j2 = netmsginput.readUnsignedByte();
        if(!externalStoresDropped && j2 > 0)
        {
            externalStoresDropped = true;
            aircraft.dropExternalStores(false);
        }
    }

    void updConvDist(int i, int j, float f)
    {
        float f1 = (float)i * (float)i * 36F + 100F;
        try
        {
            if(aircraft.FM.CT.Weapons[j] != null)
                if(j == 2)
                {
                    for(int k = 0; k < aircraft.FM.CT.Weapons[j].length; k++)
                        ((com.maddox.il2.objects.weapons.RocketGun)(com.maddox.il2.objects.weapons.RocketGun)aircraft.FM.CT.Weapons[j][k]).setConvDistance(f1, f);

                } else
                {
                    for(int l = 0; l < aircraft.FM.CT.Weapons[j].length; l++)
                        ((com.maddox.il2.objects.weapons.MGunAircraftGeneric)(com.maddox.il2.objects.weapons.MGunAircraftGeneric)aircraft.FM.CT.Weapons[j][l]).setConvDistance(f1, f);

                }
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    void setArmingSeeds(int i)
    {
        aircraft.armingRnd = new RangeRandom(aircraft.armingSeed);
        try
        {
            if(aircraft.FM.CT.Weapons[2] != null)
            {
                for(int j = 0; j < aircraft.FM.CT.Weapons[2].length; j++)
                    ((com.maddox.il2.objects.weapons.RocketGun)aircraft.FM.CT.Weapons[2][j]).setSpreadRnd(aircraft.armingRnd.nextInt());

            }
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
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

    private int cvt(float f, float f1, float f2, int i, int j)
    {
        f = java.lang.Math.min(java.lang.Math.max(f, f1), f2);
        return (int)((float)i + ((float)(j - i) * (f - f1)) / (f2 - f1));
    }

    private float cvt(int i, int j, int k, float f, float f1)
    {
        i = java.lang.Math.min(java.lang.Math.max(i, j), k);
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

    private void setGyroAngle(com.maddox.il2.engine.Actor actor1, int i, int j, boolean flag)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(flag)
            doSetGyroAngle(actor1, i, j);
        if(bIsMaster)
            netToMirrors(44, i, j);
        else
            netToMaster(44, i, j, actor1);
    }

    private void doSetGyroAngle(com.maddox.il2.engine.Actor actor1, int i, int j)
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

    private void setSpreadAngle(com.maddox.il2.engine.Actor actor1, int i, int j, boolean flag)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(flag)
            doSetSpreadAngle(actor1, i, 0);
        if(bIsMaster)
            netToMirrors(45, i, 0);
        else
            netToMaster(45, i, 0, actor1);
    }

    private void doSetSpreadAngle(com.maddox.il2.engine.Actor actor1, int i, int j)
    {
        setSpreadAngle(i);
    }

    public static final boolean __DEBUG_SPREAD__ = false;
    private static final float astateEffectCriticalSpeed = 10F;
    private static final float astateCondensateCriticalAlt = 7000F;
    private static final com.maddox.il2.engine.Loc astateCondensateDispVector = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
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
    public static final int _AS_BEACONS = 42;
    public static final int _AS_GYROANGLE = 44;
    public static final int _AS_SPREADANGLE = 45;
    public static final int _AS_PILOT_WOUNDED = 46;
    public static final int _AS_PILOT_BLEEDING = 47;
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
    private static final float gyroAngleLimit = 50F;
    public static final int spreadAngleLimit = 30;
    private int beacon;
    private boolean bWantBeaconsNet;
    public static final int MAX_NUMBER_OF_BEACONS = 32;
    public boolean listenLorenzBlindLanding;
    public boolean isAAFIAS;
    public boolean listenYGBeacon;
    public boolean listenNDBeacon;
    public boolean listenRadioStation;
    public com.maddox.il2.engine.Actor hayrakeCarrier;
    public java.lang.String hayrakeCode;
    public static int hudLogBeaconId = com.maddox.il2.game.HUD.makeIdLog();
    public boolean externalStoresDropped;
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
    public com.maddox.il2.objects.air.AircraftLH aircraft;
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
