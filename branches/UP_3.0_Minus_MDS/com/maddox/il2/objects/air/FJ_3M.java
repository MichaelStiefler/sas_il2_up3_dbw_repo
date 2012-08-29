// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   FJ_3M.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.il2.objects.weapons.GuidedMissileUtils;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.objects.air:
//            F_86F, Aircraft, TypeTankerDrogue, TypeDockable, 
//            PaintSchemeFMPar06, TypeGuidedMissileCarrier, TypeCountermeasure, TypeThreatDetector, 
//            TypeGSuit, NetAircraft

public class FJ_3M extends com.maddox.il2.objects.air.F_86F
    implements com.maddox.il2.objects.air.TypeDockable, com.maddox.il2.objects.air.TypeGuidedMissileCarrier, com.maddox.il2.objects.air.TypeCountermeasure, com.maddox.il2.objects.air.TypeThreatDetector, com.maddox.il2.objects.air.TypeGSuit
{

    public FJ_3M()
    {
        guidedMissileUtils = null;
        trgtPk = 0.0F;
        trgtAI = null;
        hasChaff = false;
        hasFlare = false;
        lastChaffDeployed = 0L;
        lastFlareDeployed = 0L;
        lastCommonThreatActive = 0L;
        intervalCommonThreat = 1000L;
        lastRadarLockThreatActive = 0L;
        intervalRadarLockThreat = 1000L;
        lastMissileLaunchThreatActive = 0L;
        intervalMissileLaunchThreat = 1000L;
        rocketsList = new ArrayList();
        bToFire = false;
        tX4Prev = 0L;
        guidedMissileUtils = new GuidedMissileUtils(((com.maddox.il2.engine.Actor) (this)));
        arrestor = 0.0F;
    }

    public long getChaffDeployed()
    {
        if(hasChaff)
            return lastChaffDeployed;
        else
            return 0L;
    }

    public long getFlareDeployed()
    {
        if(hasFlare)
            return lastFlareDeployed;
        else
            return 0L;
    }

    public void setCommonThreatActive()
    {
        long l = com.maddox.rts.Time.current();
        if(l - lastCommonThreatActive > intervalCommonThreat)
        {
            lastCommonThreatActive = l;
            doDealCommonThreat();
        }
    }

    public void setRadarLockThreatActive()
    {
        long l = com.maddox.rts.Time.current();
        if(l - lastRadarLockThreatActive > intervalRadarLockThreat)
        {
            lastRadarLockThreatActive = l;
            doDealRadarLockThreat();
        }
    }

    public void setMissileLaunchThreatActive()
    {
        long l = com.maddox.rts.Time.current();
        if(l - lastMissileLaunchThreatActive > intervalMissileLaunchThreat)
        {
            lastMissileLaunchThreatActive = l;
            doDealMissileLaunchThreat();
        }
    }

    private void doDealCommonThreat()
    {
    }

    private void doDealRadarLockThreat()
    {
    }

    private void doDealMissileLaunchThreat()
    {
    }

    public void getGFactors(com.maddox.il2.objects.air.TypeGSuit.GFactors gfactors)
    {
        gfactors.setGFactors(1.5F, 1.5F, 1.0F, 2.0F, 2.0F, 2.0F);
    }

    public com.maddox.il2.engine.Actor getMissileTarget()
    {
        return guidedMissileUtils.getMissileTarget();
    }

    public com.maddox.JGP.Point3f getMissileTargetOffset()
    {
        return guidedMissileUtils.getSelectedActorOffset();
    }

    public boolean hasMissiles()
    {
        return !rocketsList.isEmpty();
    }

    public void shotMissile()
    {
        if(hasMissiles())
        {
            if(com.maddox.il2.net.NetMissionTrack.isPlaying() && (!(FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)FM).isRealMode()))
                ((com.maddox.il2.objects.weapons.RocketGun)rocketsList.get(0)).loadBullets(0);
            if(com.maddox.il2.ai.World.cur().diffCur.Limited_Ammo || this != com.maddox.il2.ai.World.getPlayerAircraft())
                rocketsList.remove(0);
        }
    }

    public int getMissileLockState()
    {
        return guidedMissileUtils.getMissileLockState();
    }

    private float getMissilePk()
    {
        float f = 0.0F;
        guidedMissileUtils.setMissileTarget(guidedMissileUtils.lookForGuidedMissileTarget(FM.actor, guidedMissileUtils.getMaxPOVfrom(), guidedMissileUtils.getMaxPOVto(), guidedMissileUtils.getPkMaxDist()));
        if(com.maddox.il2.engine.Actor.isValid(guidedMissileUtils.getMissileTarget()))
            f = guidedMissileUtils.Pk(FM.actor, guidedMissileUtils.getMissileTarget());
        return f;
    }

    private void checkAIlaunchMissile()
    {
        if((FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)FM).isRealMode() || !(FM instanceof com.maddox.il2.ai.air.Pilot))
            return;
        if(rocketsList.isEmpty())
            return;
        com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)FM;
        if((pilot.get_maneuver() == 27 || pilot.get_maneuver() == 62 || pilot.get_maneuver() == 63) && pilot.target != null)
        {
            trgtAI = pilot.target.actor;
            if(!com.maddox.il2.engine.Actor.isValid(trgtAI) || !(trgtAI instanceof com.maddox.il2.objects.air.Aircraft))
                return;
            bToFire = false;
            if(trgtPk > 25F && com.maddox.il2.engine.Actor.isValid(guidedMissileUtils.getMissileTarget()) && (guidedMissileUtils.getMissileTarget() instanceof com.maddox.il2.objects.air.Aircraft) && guidedMissileUtils.getMissileTarget().getArmy() != FM.actor.getArmy() && com.maddox.rts.Time.current() > tX4Prev + 10000L)
            {
                bToFire = true;
                tX4Prev = com.maddox.rts.Time.current();
                shootRocket();
                bToFire = false;
            }
        }
    }

    public void shootRocket()
    {
        if(rocketsList.isEmpty())
        {
            return;
        } else
        {
            ((com.maddox.il2.objects.weapons.RocketGun)rocketsList.get(0)).shots(1);
            return;
        }
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        rocketsList.clear();
        guidedMissileUtils.createMissileList(rocketsList);
    }

    public void update(float f)
    {
        if(bNeedSetup)
            checkAsDrone();
        int i = aircIndex();
        if(super.FM instanceof com.maddox.il2.ai.air.Maneuver)
            if(typeDockableIsDocked())
            {
                if(!(super.FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)super.FM).isRealMode())
                {
                    ((com.maddox.il2.ai.air.Maneuver)super.FM).unblock();
                    ((com.maddox.il2.ai.air.Maneuver)super.FM).set_maneuver(48);
                    for(int j = 0; j < i; j++)
                        ((com.maddox.il2.ai.air.Maneuver)super.FM).push(48);

                    if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AP.way.curr().Action != 3)
                        ((com.maddox.il2.fm.FlightModelMain) ((com.maddox.il2.ai.air.Maneuver)super.FM)).AP.way.setCur(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)queen_)).FM)).AP.way.Cur());
                    ((com.maddox.il2.ai.air.Pilot)super.FM).setDumbTime(3000L);
                }
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).M.fuel < ((com.maddox.il2.fm.FlightModelMain) (super.FM)).M.maxFuel)
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).M.fuel += 20F * f;
            } else
            if(!(super.FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)super.FM).isRealMode())
            {
                if(FM.CT.GearControl == 0.0F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getStage() == 0)
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.setEngineRunning();
                if(dtime > 0L && ((com.maddox.il2.ai.air.Maneuver)super.FM).Group != null)
                {
                    ((com.maddox.il2.ai.air.Maneuver)super.FM).Group.leaderGroup = null;
                    ((com.maddox.il2.ai.air.Maneuver)super.FM).set_maneuver(22);
                    ((com.maddox.il2.ai.air.Pilot)super.FM).setDumbTime(3000L);
                    if(com.maddox.rts.Time.current() > dtime + 3000L)
                    {
                        dtime = -1L;
                        ((com.maddox.il2.ai.air.Maneuver)super.FM).clear_stack();
                        ((com.maddox.il2.ai.air.Maneuver)super.FM).set_maneuver(0);
                        ((com.maddox.il2.ai.air.Pilot)super.FM).setDumbTime(0L);
                    }
                } else
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AP.way.curr().Action == 0)
                {
                    com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)super.FM;
                    if(maneuver.Group != null && maneuver.Group.airc[0] == this && maneuver.Group.clientGroup != null)
                        maneuver.Group.setGroupTask(2);
                }
            }
        if(FM.CT.getArrestor() > 0.2F)
            if(FM.Gears.arrestorVAngle != 0.0F)
            {
                float f1 = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.arrestorVAngle, -50F, 7F, 1.0F, 0.0F);
                arrestor = 0.8F * arrestor + 0.2F * f1;
                moveArrestorHook(arrestor);
            } else
            {
                float f2 = (-33F * FM.Gears.arrestorVSink) / 57F;
                if(f2 < 0.0F && FM.getSpeedKMH() > 60F)
                    com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (this)), ((com.maddox.il2.engine.Hook) (FM.Gears.arrestorHook)), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
                if(f2 > 0.0F && FM.CT.getArrestor() < 0.95F)
                    f2 = 0.0F;
                if(f2 > 0.2F)
                    f2 = 0.2F;
                if(f2 > 0.0F)
                    arrestor = 0.7F * arrestor + 0.3F * (arrestor + f2);
                else
                    arrestor = 0.3F * arrestor + 0.7F * (arrestor + f2);
                if(arrestor < 0.0F)
                    arrestor = 0.0F;
                else
                if(arrestor > 1.0F)
                    arrestor = 1.0F;
                moveArrestorHook(arrestor);
            }
        super.update(f);
        trgtPk = getMissilePk();
        guidedMissileUtils.checkLockStatus();
        checkAIlaunchMissile();
    }

    public void msgCollisionRequest(com.maddox.il2.engine.Actor actor, boolean aflag[])
    {
        super.msgCollisionRequest(actor, aflag);
        if(queen_last != null && queen_last == actor && (queen_time == 0L || com.maddox.rts.Time.current() < queen_time + 5000L))
            aflag[0] = false;
        else
            aflag[0] = true;
    }

    public void missionStarting()
    {
        checkAsDrone();
    }

    private void checkAsDrone()
    {
        if(target_ == null)
        {
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AP.way.curr().getTarget() == null)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AP.way.next();
            target_ = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AP.way.curr().getTarget();
            if(com.maddox.il2.engine.Actor.isValid(target_) && (target_ instanceof com.maddox.il2.ai.Wing))
            {
                com.maddox.il2.ai.Wing wing = (com.maddox.il2.ai.Wing)target_;
                int i = aircIndex();
                if(com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Actor) (wing.airc[i / 2]))))
                    target_ = ((com.maddox.il2.engine.Actor) (wing.airc[i / 2]));
                else
                    target_ = null;
            }
        }
        if(com.maddox.il2.engine.Actor.isValid(target_) && (target_ instanceof com.maddox.il2.objects.air.TypeTankerDrogue))
        {
            queen_last = target_;
            queen_time = com.maddox.rts.Time.current();
            if(isNetMaster())
                ((com.maddox.il2.objects.air.TypeDockable)target_).typeDockableRequestAttach(((com.maddox.il2.engine.Actor) (this)), aircIndex() % 2, true);
        }
        bNeedSetup = false;
        target_ = null;
    }

    public int typeDockableGetDockport()
    {
        if(typeDockableIsDocked())
            return dockport_;
        else
            return -1;
    }

    public com.maddox.il2.engine.Actor typeDockableGetQueen()
    {
        return queen_;
    }

    public boolean typeDockableIsDocked()
    {
        return com.maddox.il2.engine.Actor.isValid(queen_);
    }

    public void typeDockableAttemptAttach()
    {
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.isMaster() && !typeDockableIsDocked())
        {
            com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.War.getNearestFriend(((com.maddox.il2.objects.air.Aircraft) (this)));
            if(aircraft instanceof com.maddox.il2.objects.air.TypeTankerDrogue)
                ((com.maddox.il2.objects.air.TypeDockable)aircraft).typeDockableRequestAttach(((com.maddox.il2.engine.Actor) (this)));
        }
    }

    public void typeDockableAttemptDetach()
    {
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.isMaster() && typeDockableIsDocked() && com.maddox.il2.engine.Actor.isValid(queen_))
            ((com.maddox.il2.objects.air.TypeDockable)queen_).typeDockableRequestDetach(((com.maddox.il2.engine.Actor) (this)));
    }

    public void typeDockableRequestAttach(com.maddox.il2.engine.Actor actor)
    {
    }

    public void typeDockableRequestDetach(com.maddox.il2.engine.Actor actor)
    {
    }

    public void typeDockableRequestAttach(com.maddox.il2.engine.Actor actor, int i, boolean flag)
    {
    }

    public void typeDockableRequestDetach(com.maddox.il2.engine.Actor actor, int i, boolean flag)
    {
    }

    public void typeDockableDoAttachToDrone(com.maddox.il2.engine.Actor actor, int i)
    {
    }

    public void typeDockableDoDetachFromDrone(int i)
    {
    }

    public void typeDockableDoAttachToQueen(com.maddox.il2.engine.Actor actor, int i)
    {
        queen_ = actor;
        dockport_ = i;
        queen_last = queen_;
        queen_time = 0L;
        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.setEngineRunning();
        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.setGearAirborne();
        moveGear(0.0F);
        com.maddox.il2.fm.FlightModel flightmodel = ((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)queen_)).FM;
        if(aircIndex() == 0 && (super.FM instanceof com.maddox.il2.ai.air.Maneuver) && (flightmodel instanceof com.maddox.il2.ai.air.Maneuver))
        {
            com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)flightmodel;
            com.maddox.il2.ai.air.Maneuver maneuver1 = (com.maddox.il2.ai.air.Maneuver)super.FM;
            if(maneuver.Group != null && maneuver1.Group != null && maneuver1.Group.numInGroup(((com.maddox.il2.objects.air.Aircraft) (this))) == maneuver1.Group.nOfAirc - 1)
            {
                com.maddox.il2.ai.air.AirGroup airgroup = new AirGroup(maneuver1.Group);
                maneuver1.Group.delAircraft(((com.maddox.il2.objects.air.Aircraft) (this)));
                airgroup.addAircraft(((com.maddox.il2.objects.air.Aircraft) (this)));
                airgroup.attachGroup(maneuver.Group);
                airgroup.rejoinGroup = null;
                airgroup.leaderGroup = null;
                airgroup.clientGroup = maneuver.Group;
            }
        }
    }

    public void typeDockableDoDetachFromQueen(int i)
    {
        if(dockport_ == i)
        {
            queen_last = queen_;
            queen_time = com.maddox.rts.Time.current();
            queen_ = null;
            dockport_ = 0;
        }
    }

    public void typeDockableReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        if(typeDockableIsDocked())
        {
            netmsgguaranted.writeByte(1);
            com.maddox.il2.engine.ActorNet actornet = null;
            if(com.maddox.il2.engine.Actor.isValid(queen_))
            {
                actornet = queen_.net;
                if(actornet.countNoMirrors() > 0)
                    actornet = null;
            }
            netmsgguaranted.writeByte(dockport_);
            netmsgguaranted.writeNetObj(((com.maddox.rts.NetObj) (actornet)));
        } else
        {
            netmsgguaranted.writeByte(0);
        }
    }

    public void typeDockableReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        if(netmsginput.readByte() == 1)
        {
            dockport_ = ((int) (netmsginput.readByte()));
            com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
            if(netobj != null)
            {
                com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)netobj.superObj();
                ((com.maddox.il2.objects.air.TypeDockable)actor).typeDockableDoAttachToDrone(((com.maddox.il2.engine.Actor) (this)), dockport_);
            }
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if((super.FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)super.FM).isRealMode() || !flag || !(super.FM instanceof com.maddox.il2.ai.air.Pilot))
            return;
        if(flag && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AP.way.curr().Action == 3 && typeDockableIsDocked() && java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)queen_)).FM)).Or.getKren()) < 3F)
            if(super.FM.isPlayers())
            {
                if((super.FM instanceof com.maddox.il2.fm.RealFlightModel) && !((com.maddox.il2.fm.RealFlightModel)super.FM).isRealMode())
                {
                    typeDockableAttemptDetach();
                    ((com.maddox.il2.ai.air.Maneuver)super.FM).set_maneuver(22);
                    ((com.maddox.il2.ai.air.Maneuver)super.FM).setCheckStrike(false);
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Vwld.z -= 5D;
                    dtime = com.maddox.rts.Time.current();
                }
            } else
            {
                typeDockableAttemptDetach();
                ((com.maddox.il2.ai.air.Maneuver)super.FM).set_maneuver(22);
                ((com.maddox.il2.ai.air.Maneuver)super.FM).setCheckStrike(false);
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Vwld.z -= 5D;
                dtime = com.maddox.rts.Time.current();
            }
    }

    protected void moveWingFold(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("WingLFold", 0.0F * f, 0.0F * f, -22F * f);
        hiermesh.chunkSetAngles("WingRFold", 0.0F * f, 0.0F * f, -22F * f);
        hiermesh.chunkSetAngles("WingLOut_D0", 0.0F * f, 90F * f, -22F * f);
        hiermesh.chunkSetAngles("WingROut_D0", 0.0F * f, -90F * f, -22F * f);
    }

    public void moveWingFold(float f)
    {
        if(f < 0.001F)
        {
            setGunPodsOn(true);
            hideWingWeapons(false);
        } else
        {
            setGunPodsOn(false);
            FM.CT.WeaponControl[0] = false;
            hideWingWeapons(true);
        }
        moveWingFold(hierMesh(), f);
    }

    public void moveArrestorHook(float f)
    {
        hierMesh().chunkSetAngles("Hook1_D0", 0.0F, 45F * f, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.1385F * f;
        arrestor = f;
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 19: // '\023'
            FM.CT.bHasArrestorControl = false;
            break;
        }
        return super.cutFM(i, j, actor);
    }

    public void checkHydraulicStatus()
    {
        if(FM.EI.engines[0].getStage() < 6 && FM.Gears.nOfGearsOnGr > 0)
        {
            hasHydraulicPressure = false;
            FM.CT.bHasAileronControl = false;
            FM.CT.bHasElevatorControl = false;
            FM.CT.AirBrakeControl = 1.0F;
            FM.CT.bHasArrestorControl = false;
        } else
        if(!hasHydraulicPressure)
        {
            hasHydraulicPressure = true;
            FM.CT.bHasAileronControl = true;
            FM.CT.bHasElevatorControl = true;
            FM.CT.bHasAirBrakeControl = true;
            FM.CT.bHasArrestorControl = true;
        }
    }

    public static boolean bChangedPit = false;
    private com.maddox.il2.engine.Actor queen_last;
    private long queen_time;
    private boolean bNeedSetup;
    private long dtime;
    private com.maddox.il2.engine.Actor target_;
    private com.maddox.il2.engine.Actor queen_;
    private int dockport_;
    private com.maddox.il2.objects.weapons.GuidedMissileUtils guidedMissileUtils;
    private float trgtPk;
    private com.maddox.il2.engine.Actor trgtAI;
    private boolean hasChaff;
    private boolean hasFlare;
    private long lastChaffDeployed;
    private long lastFlareDeployed;
    private long lastCommonThreatActive;
    private long intervalCommonThreat;
    private long lastRadarLockThreatActive;
    private long intervalRadarLockThreat;
    private long lastMissileLaunchThreatActive;
    private long intervalMissileLaunchThreat;
    private static final float NEG_G_TOLERANCE_FACTOR = 1.5F;
    private static final float NEG_G_TIME_FACTOR = 1.5F;
    private static final float NEG_G_RECOVERY_FACTOR = 1F;
    private static final float POS_G_TOLERANCE_FACTOR = 2F;
    private static final float POS_G_TIME_FACTOR = 2F;
    private static final float POS_G_RECOVERY_FACTOR = 2F;
    private java.util.ArrayList rocketsList;
    public boolean bToFire;
    private long tX4Prev;
    private float arrestor;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "FJ-3M");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/FJ_3M(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar06())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1949.9F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1960.3F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/FJ-3M.fmd");
        try
        {
            com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
                java.lang.Class.forName("com.maddox.il2.objects.air.CockpitF_86Flate")
            })));
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.ai.EventLog.type("Exception in FJ-3M Cockpit init, " + exception.getMessage());
        }
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.725F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 9, 9, 9, 9, 9, 3, 
            3, 9, 3, 3, 9, 2, 2, 9, 2, 2, 
            9, 9, 9, 9, 9, 3, 3, 9, 3, 3, 
            9, 9, 2, 2, 2, 2, 2, 2, 2, 2
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalBomb01", 
            "_ExternalBomb01", "_ExternalDev06", "_ExternalBomb02", "_ExternalBomb02", "_ExternalDev07", "_ExternalRock01", "_ExternalRock01", "_ExternalDev08", "_ExternalRock02", "_ExternalRock02", 
            "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalDev13", "_ExternalBomb03", "_ExternalBomb03", "_ExternalDev14", "_ExternalBomb04", "_ExternalBomb04", 
            "_ExternalDev17", "_ExternalDev18", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x200dt", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x75nap", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonF86_Outboard 1", "BombGun75Napalm 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun75Napalm 1", "BombGunNull 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x75nap+2x200dt", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonF86_Outboard 1", "BombGun75Napalm 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun75Napalm 1", "BombGunNull 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonF86_Outboard 1", "BombGun500lbs 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun500lbs 1", "BombGunNull 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500+2x200dt", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonF86_Outboard 1", "BombGun500lbs 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun500lbs 1", "BombGunNull 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500+2x750", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "PylonF86_Outboard 1", "PylonF86_Outboard 1", null, null, null, "BombGun500lbs 1", 
            "BombGunNull 1", null, "BombGun500lbs 1", "BombGunNull 1", null, null, null, null, null, null, 
            null, null, null, null, "PylonF86_Outboard 1", "BombGun750lbs 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun750lbs 1", "BombGunNull 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500+2xM117", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "PylonF86_Outboard 1", "PylonF86_Outboard 1", null, null, null, "BombGun500lbs 1", 
            "BombGunNull 1", null, "BombGun500lbs 1", "BombGunNull 1", null, null, null, null, null, null, 
            null, null, null, null, "PylonF86_Outboard 1", "BombGun750lbsM117 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun750lbsM117 1", "BombGunNull 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4x500", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "PylonF86_Outboard 1", "PylonF86_Outboard 1", null, null, null, "BombGun500lbs 1", 
            "BombGunNull 1", null, "BombGun500lbs 1", "BombGunNull 1", null, null, null, null, null, null, 
            null, null, null, null, "PylonF86_Outboard 1", "BombGun500lbs 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun500lbs 1", "BombGunNull 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x750", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonF86_Outboard 1", "BombGun750lbs 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun750lbs 1", "BombGunNull 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x750+2x200dt", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonF86_Outboard 1", "BombGun750lbs 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun750lbs 1", "BombGunNull 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xM117", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonF86_Outboard 1", "BombGun750lbsM117 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun750lbsM117 1", "BombGunNull 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xM117+2x200dt", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonF86_Outboard 1", "BombGun750lbsM117 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun750lbsM117 1", "BombGunNull 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x1000", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonF86_Outboard 1", "BombGun1000lbs 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun1000lbs 1", "BombGunNull 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x1000+2x200dt", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonF86_Outboard 1", "BombGun1000lbs 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun1000lbs 1", "BombGunNull 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xM114", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonF86_Outboard 1", "BombGun1000lbs_M114 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun1000lbs_M114 1", "BombGunNull 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xM114+2x200dt", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonF86_Outboard 1", "BombGun1000lbs_M114 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun1000lbs_M114 1", "BombGunNull 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xLAU10", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonF86_Outboard 1", null, null, "PylonF86_Outboard 1", null, null, 
            "Pylon_Zuni 1", "Pylon_Zuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xLAU10+2x200dt", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonF86_Outboard 1", null, null, "PylonF86_Outboard 1", null, null, 
            "Pylon_Zuni 1", "Pylon_Zuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xAIM9B", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", null, null, null, null, null, null, 
            null, null, null, null, "PylonF86_Sidewinder 1", "RocketGunAIM9B 1", "BombGunNull 1", "PylonF86_Sidewinder 1", "RocketGunAIM9B 1", "BombGunNull 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xAIM9B+2x200dt", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, 
            null, null, null, null, "PylonF86_Sidewinder 1", "RocketGunAIM9B 1", "BombGunNull 1", "PylonF86_Sidewinder 1", "RocketGunAIM9B 1", "BombGunNull 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xAIM9D", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", null, null, null, null, null, null, 
            null, null, null, null, "PylonF86_Sidewinder 1", "RocketGunAIM9D 1", "BombGunNull 1", "PylonF86_Sidewinder 1", "RocketGunAIM9D 1", "BombGunNull 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xAIM9D+2x200dt", new java.lang.String[] {
            "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "MGunColtMk12ki 162", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, 
            null, null, null, null, "PylonF86_Sidewinder 1", "RocketGunAIM9D 1", "BombGunNull 1", "PylonF86_Sidewinder 1", "RocketGunAIM9D 1", "BombGunNull 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
