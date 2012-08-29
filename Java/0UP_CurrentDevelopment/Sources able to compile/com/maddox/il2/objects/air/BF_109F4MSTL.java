package com.maddox.il2.objects.air;

import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
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
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
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
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;
import java.io.PrintStream;

public class BF_109F4MSTL extends BF_109
    implements TypeDockable
{

    public BF_109F4MSTL()
    {
        cockpitDoor_ = 0.0F;
        fMaxKMHSpeedForOpenCanopy = 250F;
        kangle = 0.0F;
        bHasBlister = true;
        bNeedSetup = true;
        dtime = -1L;
        target_ = null;
        queen_ = null;
    }

    protected void moveFan(float f)
    {
        int i = 0;
        for(int j = 0; j < 1; j++)
        {
            if(super.oldProp[j] < 2)
            {
                i = java.lang.Math.abs((int)(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[j].getw() * 0.12F * 1.5F));
                if(i >= 1)
                    i = 1;
                if(i != super.oldProp[j])
                {
                    hierMesh().chunkVisible(Aircraft.Props[j][super.oldProp[j]], false);
                    super.oldProp[j] = i;
                    hierMesh().chunkVisible(Aircraft.Props[j][i], true);
                }
            }
            if(i == 0)
            {
                super.propPos[j] = (super.propPos[j] + 57.3F * ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[j].getw() * f) % 360F;
            } else
            {
                float f1 = 57.3F * ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[j].getw();
                f1 %= 2880F;
                f1 /= 2880F;
                if(f1 <= 0.5F)
                    f1 *= 2.0F;
                else
                    f1 = f1 * 2.0F - 2.0F;
                f1 *= 1200F;
                super.propPos[j] = (super.propPos[j] + f1 * f) % 360F;
            }
            hierMesh().chunkSetAngles(Aircraft.Props[j][0], 0.0F, -super.propPos[j], 0.0F);
        }

    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        float f1 = 0.8F;
        float f2 = -0.5F * (float)java.lang.Math.cos((double)(f / f1) * 3.1415926535897931D) + 0.5F;
        if(f <= f1 || f == 1.0F)
        {
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f2, 0.0F);
            hiermesh.chunkSetAngles("GearL2_D0", -33.5F * f2, 0.0F, 0.0F);
        }
        f2 = -0.5F * (float)java.lang.Math.cos((double)((f - (1.0F - f1)) / f1) * 3.1415926535897931D) + 0.5F;
        if(f >= 1.0F - f1)
        {
            hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F * f2, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 33.5F * f2, 0.0F, 0.0F);
        }
        hiermesh.chunkSetAngles("GearC3_D0", 70F * f, 0.0F, 0.0F);
        if(f > 0.99F)
        {
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F, 0.0F);
            hiermesh.chunkSetAngles("GearL2_D0", -33.5F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 33.5F, 0.0F, 0.0F);
        }
        if(f < 0.01F)
        {
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 0.0F, 0.0F);
        }
    }

    protected void moveGear(float f)
    {
        if(typeDockableIsDocked())
            BF_109F4MSTL.moveGear(hierMesh(), 0.0F);
        else
            BF_109F4MSTL.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getGear() >= 0.98F)
            hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -f, 0.0F);
    }

    public void update(float f)
    {
        if(super.FM.getSpeed() > 5F)
        {
            hierMesh().chunkSetAngles("SlatL_D0", 0.0F, Aircraft.cvt(super.FM.getAOA(), 6.8F, 11F, 0.0F, 1.5F), 0.0F);
            hierMesh().chunkSetAngles("SlatR_D0", 0.0F, Aircraft.cvt(super.FM.getAOA(), 6.8F, 11F, 0.0F, 1.5F), 0.0F);
        }
        hierMesh().chunkSetAngles("Flap01L_D0", 0.0F, -16F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap01U_D0", 0.0F, 16F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap02L_D0", 0.0F, -16F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap02U_D0", 0.0F, 16F * kangle, 0.0F);
        kangle = 0.95F * kangle + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getControlRadiator();
        if(kangle > 1.0F)
            kangle = 1.0F;
        super.update(f);
        if((double)((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getCockpitDoor() > 0.20000000000000001D && bHasBlister && super.FM.getSpeedKMH() > fMaxKMHSpeedForOpenCanopy && hierMesh().chunkFindCheck("Blister1_D0") != -1)
        {
            try
            {
                if(this == com.maddox.il2.ai.World.getPlayerAircraft())
                    ((CockpitBF_109F2)com.maddox.il2.game.Main3D.cur3D().cockpitCur).removeCanopy();
            }
            catch(java.lang.Exception exception) { }
            hierMesh().hideSubTrees("Blister1_D0");
            com.maddox.il2.objects.Wreckage wreckage = new Wreckage(this, hierMesh().chunkFind("Blister1_D0"));
            wreckage.collide(true);
            com.maddox.JGP.Vector3d vector3d = new Vector3d();
            vector3d.set(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Vwld);
            wreckage.setSpeed(vector3d);
            bHasBlister = false;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasCockpitDoorControl = false;
            super.FM.setGCenter(-0.5F);
        }
        if(super.FM.isPlayers())
            if(!com.maddox.il2.game.Main3D.cur3D().isViewOutside())
                hierMesh().chunkVisible("CF_D0", false);
            else
                hierMesh().chunkVisible("CF_D0", true);
        if(super.FM.isPlayers())
        {
            if(!com.maddox.il2.game.Main3D.cur3D().isViewOutside())
                hierMesh().chunkVisible("CF_D1", false);
            hierMesh().chunkVisible("CF_D2", false);
            hierMesh().chunkVisible("CF_D3", false);
        }
        if(super.FM.isPlayers())
        {
            if(!com.maddox.il2.game.Main3D.cur3D().isViewOutside())
                hierMesh().chunkVisible("Blister1_D0", false);
            else
            if(bHasBlister)
                hierMesh().chunkVisible("Blister1_D0", true);
            com.maddox.JGP.Point3d point3d = ((com.maddox.il2.engine.Actor) (com.maddox.il2.ai.World.getPlayerAircraft())).pos.getAbsPoint();
            if(((com.maddox.JGP.Tuple3d) (point3d)).z - com.maddox.il2.ai.World.land().HQ(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y) < 0.0099999997764825821D)
                hierMesh().chunkVisible("CF_D0", true);
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.bIsAboutToBailout)
                hierMesh().chunkVisible("Blister1_D0", false);
        }
        if(bNeedSetup)
            checkAsDrone();
        if(super.FM instanceof com.maddox.il2.ai.air.Maneuver)
            if(typeDockableIsDocked())
            {
                if(!(super.FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)super.FM).isRealMode())
                {
                    ((com.maddox.il2.ai.air.Maneuver)super.FM).set_maneuver(48);
                    ((com.maddox.il2.fm.FlightModelMain) ((com.maddox.il2.ai.air.Maneuver)super.FM)).AP.way.setCur(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) ((Aircraft)queen_)).FM)).AP.way.Cur());
                    ((com.maddox.il2.ai.air.Pilot)super.FM).setDumbTime(3000L);
                }
            } else
            if(!(super.FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)super.FM).isRealMode())
                if(dtime > 0L)
                {
                    ((com.maddox.il2.ai.air.Maneuver)super.FM).set_maneuver(22);
                    ((com.maddox.il2.ai.air.Pilot)super.FM).setDumbTime(3000L);
                    if(com.maddox.rts.Time.current() > dtime + 3000L)
                    {
                        dtime = -1L;
                        ((com.maddox.il2.ai.air.Maneuver)super.FM).clear_stack();
                        ((com.maddox.il2.ai.air.Maneuver)super.FM).pop();
                        ((com.maddox.il2.ai.air.Pilot)super.FM).setDumbTime(0L);
                    }
                } else
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AP.way.curr().Action == 3 && ((com.maddox.il2.ai.air.Maneuver)super.FM).get_maneuver() == 24)
                {
                    ((com.maddox.il2.ai.air.Maneuver)super.FM).set_maneuver(21);
                    ((com.maddox.il2.ai.air.Pilot)super.FM).setDumbTime(30000L);
                }
        if(typeDockableIsDocked())
        {
            Aircraft aircraft = (Aircraft)typeDockableGetQueen();
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).CT.AileronControl = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.AileronControl;
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).CT.ElevatorControl = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.ElevatorControl;
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).CT.RudderControl = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.RudderControl;
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).CT.GearControl = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.GearControl;
        }
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.saveWeaponControl[3])
            typeDockableAttemptDetach();
        super.update(f);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(!flag);
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(com.maddox.il2.game.Mission.isCoop() && !com.maddox.il2.game.Mission.isServer() && !isSpawnFromMission() && super.net.isMaster())
            new com.maddox.rts.MsgAction(64, 0.0D, this) {

                public void doAction()
                {
                    onCoopMasterSpawned();
                }

            }
;
    }

    private void onCoopMasterSpawned()
    {
        com.maddox.il2.engine.Actor actor = null;
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AP.way.curr().getTargetName() == null)
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AP.way.next();
        java.lang.String s = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AP.way.curr().getTargetName();
        if(s != null)
            actor = com.maddox.il2.engine.Actor.getByName(s);
        if(com.maddox.il2.engine.Actor.isValid(actor) && (actor instanceof com.maddox.il2.ai.Wing) && actor.getOwnerAttachedCount() > 0)
            actor = (com.maddox.il2.engine.Actor)actor.getOwnerAttached(0);
        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AP.way.setCur(0);
        if(com.maddox.il2.engine.Actor.isValid(actor) && (actor instanceof JU_88MSTL))
            try
            {
                Aircraft aircraft = (Aircraft)actor;
                float f = 100F;
                if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).M.maxFuel > 0.0F)
                    f = (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).M.fuel / ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).M.maxFuel) * 100F;
                java.lang.String s1 = "spawn " + actor.getClass().getName() + " NAME net" + actor.name() + " FUEL " + f + " WEAPONS " + ((NetAircraft) (aircraft)).thisWeaponsName + (((NetAircraft) (aircraft)).bPaintShemeNumberOn ? "" : " NUMBEROFF") + " OVR";
                com.maddox.rts.CmdEnv.top().exec(s1);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
    }

    public void missionStarting()
    {
        checkAsDrone();
    }

    private void checkAsDrone()
    {
        if(target_ == null)
        {
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AP.way.curr().getTargetActorRandom() == null)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AP.way.curr().getTargetActorRandom();
            target_ = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AP.way.curr().getTargetActorRandom();
            if(com.maddox.il2.engine.Actor.isValid(target_))
                target_ = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AP.way.curr().getTargetActorRandom();
        }
        if(com.maddox.il2.engine.Actor.isValid(target_) && (target_ instanceof JU_88MSTL) && isNetMaster())
            ((TypeDockable)target_).typeDockableRequestAttach(this, 0, true);
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
            Aircraft aircraft = com.maddox.il2.ai.War.getNearestFriend(this);
            if(aircraft instanceof JU_88MSTL)
                ((TypeDockable)aircraft).typeDockableRequestAttach(this);
        }
    }

    public void typeDockableAttemptDetach()
    {
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.isMaster() && typeDockableIsDocked() && com.maddox.il2.engine.Actor.isValid(queen_))
            ((TypeDockable)queen_).typeDockableRequestDetach(this);
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
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.getNum() == 1)
        {
            super.FM.Scheme = 2;
            Aircraft aircraft = (Aircraft)actor;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.setNum(3);
            com.maddox.il2.fm.Motor motor = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0];
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines = new com.maddox.il2.fm.Motor[3];
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0] = motor;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[1] = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).EI.engines[0];
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[2] = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).EI.engines[1];
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.bCurControl = (new boolean[] {
                true, true, true
            });
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).EI.bCurControl[0] = false;
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).EI.bCurControl[1] = false;
        }
        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.setEngineRunning();
        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.setGearAirborne();
        moveGear(0.0F);
        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.GearControl = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) ((Aircraft)actor)).FM)).CT.GearControl;
        com.maddox.il2.fm.FlightModel flightmodel = ((com.maddox.il2.objects.sounds.SndAircraft) ((Aircraft)queen_)).FM;
        if((super.FM instanceof com.maddox.il2.ai.air.Maneuver) && (flightmodel instanceof com.maddox.il2.ai.air.Maneuver))
        {
            com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)flightmodel;
            com.maddox.il2.ai.air.Maneuver maneuver1 = (com.maddox.il2.ai.air.Maneuver)super.FM;
            if(maneuver.Group != null && maneuver1.Group != null && maneuver1.Group.numInGroup(this) == maneuver1.Group.nOfAirc - 1)
            {
                com.maddox.il2.ai.air.AirGroup airgroup = new AirGroup(maneuver1.Group);
                maneuver1.Group.delAircraft(this);
                airgroup.addAircraft(this);
                airgroup.attachGroup(maneuver.Group);
                airgroup.rejoinGroup = null;
            }
        }
    }

    public void typeDockableDoDetachFromQueen(int i)
    {
        if(dockport_ == i)
        {
            queen_ = null;
            dockport_ = 0;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.setTrimElevatorControl(0.51F);
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.trimElevator = 0.51F;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.setGearAirborne();
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.getNum() == 3)
            {
                super.FM.Scheme = 1;
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.setNum(1);
                com.maddox.il2.fm.Motor motor = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0];
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines = new com.maddox.il2.fm.Motor[1];
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0] = motor;
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.bCurControl = (new boolean[] {
                    true
                });
                for(int j = 1; j < 3; j++)
                {
                    if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.clpEngineEff[j][0] != null)
                    {
                        com.maddox.il2.engine.Eff3DActor.finish(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.clpEngineEff[j][0]);
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.clpEngineEff[j][0] = null;
                    }
                    if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.clpEngineEff[j][1] != null)
                    {
                        com.maddox.il2.engine.Eff3DActor.finish(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.clpEngineEff[j][1]);
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.clpEngineEff[j][1] = null;
                    }
                }

            }
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
            netmsgguaranted.writeNetObj(actornet);
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
            dockport_ = netmsginput.readByte();
            com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
            if(netobj != null)
            {
                com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)netobj.superObj();
                ((TypeDockable)actor).typeDockableDoAttachToDrone(this, dockport_);
            }
        }
    }

    public void moveCockpitDoor(float f)
    {
        if(bHasBlister)
        {
            resetYPRmodifier();
            hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 100F * f, 0.0F);
            if(com.maddox.il2.engine.Config.isUSE_RENDER())
            {
                if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                    com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
                setDoorSnd(f);
            }
        }
    }

    public float cockpitDoor_;
    private float fMaxKMHSpeedForOpenCanopy;
    private float kangle;
    public boolean bHasBlister;
    private boolean bNeedSetup;
    private long dtime;
    private com.maddox.il2.engine.Actor target_;
    private com.maddox.il2.engine.Actor queen_;
    private int dockport_;

    static 
    {
        java.lang.Class class1 = BF_109F4MSTL.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Bf109");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Bf-109F-4/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar03());
        com.maddox.rts.Property.set(class1, "meshName_sk", "3DO/Plane/Bf-109F-4(sk)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_sk", new PaintSchemeFMPar03());
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1944.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Bf-109F-4_Mod.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitBF_109F2.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.74205F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1
        });
        BF_109F4MSTL.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01"
        });
        BF_109F4MSTL.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG15120MGki 200"
        });
        BF_109F4MSTL.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null
        });
    }

}
