// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 9/06/2011 8:38:22 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   BF_109F4MSTL.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.*;
import com.maddox.il2.ai.air.*;
import com.maddox.il2.engine.*;
import com.maddox.il2.fm.*;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.*;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            BF_109, CockpitBF_109F2, Aircraft, JU_88MSTL, 
//            TypeDockable, PaintSchemeFMPar03, NetAircraft, Cockpit

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
                i = Math.abs((int)(((FlightModelMain) (super.FM)).EI.engines[j].getw() * 0.12F * 1.5F));
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
                super.propPos[j] = (super.propPos[j] + 57.3F * ((FlightModelMain) (super.FM)).EI.engines[j].getw() * f) % 360F;
            } else
            {
                float f1 = 57.3F * ((FlightModelMain) (super.FM)).EI.engines[j].getw();
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

    public static void moveGear(HierMesh hiermesh, float f)
    {
        float f1 = 0.8F;
        float f2 = -0.5F * (float)Math.cos((double)(f / f1) * 3.1415926535897931D) + 0.5F;
        if(f <= f1 || f == 1.0F)
        {
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f2, 0.0F);
            hiermesh.chunkSetAngles("GearL2_D0", -33.5F * f2, 0.0F, 0.0F);
        }
        f2 = -0.5F * (float)Math.cos((double)((f - (1.0F - f1)) / f1) * 3.1415926535897931D) + 0.5F;
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
            moveGear(hierMesh(), 0.0F);
        else
            moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        if(((FlightModelMain) (super.FM)).CT.getGear() >= 0.98F)
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
        kangle = 0.95F * kangle + 0.05F * ((FlightModelMain) (super.FM)).EI.engines[0].getControlRadiator();
        if(kangle > 1.0F)
            kangle = 1.0F;
        super.update(f);
        if((double)((FlightModelMain) (super.FM)).CT.getCockpitDoor() > 0.20000000000000001D && bHasBlister && super.FM.getSpeedKMH() > fMaxKMHSpeedForOpenCanopy && hierMesh().chunkFindCheck("Blister1_D0") != -1)
        {
            try
            {
                if(this == World.getPlayerAircraft())
                    ((CockpitBF_109F2)Main3D.cur3D().cockpitCur).removeCanopy();
            }
            catch(Exception exception) { }
            hierMesh().hideSubTrees("Blister1_D0");
            Wreckage wreckage = new Wreckage(this, hierMesh().chunkFind("Blister1_D0"));
            wreckage.collide(true);
            Vector3d vector3d = new Vector3d();
            vector3d.set(((FlightModelMain) (super.FM)).Vwld);
            wreckage.setSpeed(vector3d);
            bHasBlister = false;
            ((FlightModelMain) (super.FM)).CT.bHasCockpitDoorControl = false;
            super.FM.setGCenter(-0.5F);
        }
        if(super.FM.isPlayers())
            if(!Main3D.cur3D().isViewOutside())
                hierMesh().chunkVisible("CF_D0", false);
            else
                hierMesh().chunkVisible("CF_D0", true);
        if(super.FM.isPlayers())
        {
            if(!Main3D.cur3D().isViewOutside())
                hierMesh().chunkVisible("CF_D1", false);
            hierMesh().chunkVisible("CF_D2", false);
            hierMesh().chunkVisible("CF_D3", false);
        }
        if(super.FM.isPlayers())
        {
            if(!Main3D.cur3D().isViewOutside())
                hierMesh().chunkVisible("Blister1_D0", false);
            else
            if(bHasBlister)
                hierMesh().chunkVisible("Blister1_D0", true);
            com.maddox.JGP.Point3d point3d = ((Actor) (World.getPlayerAircraft())).pos.getAbsPoint();
            if(((Tuple3d) (point3d)).z - World.land().HQ(((Tuple3d) (point3d)).x, ((Tuple3d) (point3d)).y) < 0.0099999997764825821D)
                hierMesh().chunkVisible("CF_D0", true);
            if(((FlightModelMain) (super.FM)).AS.bIsAboutToBailout)
                hierMesh().chunkVisible("Blister1_D0", false);
        }
        if(bNeedSetup)
            checkAsDrone();
        if(super.FM instanceof Maneuver)
            if(typeDockableIsDocked())
            {
                if(!(super.FM instanceof RealFlightModel) || !((RealFlightModel)super.FM).isRealMode())
                {
                    ((Maneuver)super.FM).set_maneuver(48);
                    ((FlightModelMain) ((Maneuver)super.FM)).AP.way.setCur(((FlightModelMain) (((SndAircraft) ((Aircraft)queen_)).FM)).AP.way.Cur());
                    ((Pilot)super.FM).setDumbTime(3000L);
                }
            } else
            if(!(super.FM instanceof RealFlightModel) || !((RealFlightModel)super.FM).isRealMode())
                if(dtime > 0L)
                {
                    ((Maneuver)super.FM).set_maneuver(22);
                    ((Pilot)super.FM).setDumbTime(3000L);
                    if(Time.current() > dtime + 3000L)
                    {
                        dtime = -1L;
                        ((Maneuver)super.FM).clear_stack();
                        ((Maneuver)super.FM).pop();
                        ((Pilot)super.FM).setDumbTime(0L);
                    }
                } else
                if(((FlightModelMain) (super.FM)).AP.way.curr().Action == 3 && ((Maneuver)super.FM).get_maneuver() == 24)
                {
                    ((Maneuver)super.FM).set_maneuver(21);
                    ((Pilot)super.FM).setDumbTime(30000L);
                }
        if(typeDockableIsDocked())
        {
            Aircraft aircraft = (Aircraft)typeDockableGetQueen();
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.AileronControl = ((FlightModelMain) (super.FM)).CT.AileronControl;
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.ElevatorControl = ((FlightModelMain) (super.FM)).CT.ElevatorControl;
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.RudderControl = ((FlightModelMain) (super.FM)).CT.RudderControl;
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).CT.GearControl = ((FlightModelMain) (super.FM)).CT.GearControl;
        }
        if(((FlightModelMain) (super.FM)).CT.saveWeaponControl[3])
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
        if(Mission.isCoop() && !Mission.isServer() && !isSpawnFromMission() && super.net.isMaster())
            new MsgAction(64, 0.0D, this) {

                public void doAction()
                {
                    onCoopMasterSpawned();
                }

            }
;
    }

    private void onCoopMasterSpawned()
    {
        Actor actor = null;
        if(((FlightModelMain) (super.FM)).AP.way.curr().getTargetName() == null)
            ((FlightModelMain) (super.FM)).AP.way.next();
        String s = ((FlightModelMain) (super.FM)).AP.way.curr().getTargetName();
        if(s != null)
            actor = Actor.getByName(s);
        if(Actor.isValid(actor) && (actor instanceof Wing) && actor.getOwnerAttachedCount() > 0)
            actor = (Actor)actor.getOwnerAttached(0);
        ((FlightModelMain) (super.FM)).AP.way.setCur(0);
        if(Actor.isValid(actor) && (actor instanceof JU_88MSTL))
            try
            {
                Aircraft aircraft = (Aircraft)actor;
                float f = 100F;
                if(((FlightModelMain) (((SndAircraft) (aircraft)).FM)).M.maxFuel > 0.0F)
                    f = (((FlightModelMain) (((SndAircraft) (aircraft)).FM)).M.fuel / ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).M.maxFuel) * 100F;
                String s1 = "spawn " + actor.getClass().getName() + " NAME net" + actor.name() + " FUEL " + f + " WEAPONS " + ((NetAircraft) (aircraft)).thisWeaponsName + (((NetAircraft) (aircraft)).bPaintShemeNumberOn ? "" : " NUMBEROFF") + " OVR";
                CmdEnv.top().exec(s1);
            }
            catch(Exception exception)
            {
                System.out.println(exception.getMessage());
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
            if(((FlightModelMain) (super.FM)).AP.way.curr().getTargetActorRandom() == null)
                ((FlightModelMain) (super.FM)).AP.way.curr().getTargetActorRandom();
            target_ = ((FlightModelMain) (super.FM)).AP.way.curr().getTargetActorRandom();
            if(Actor.isValid(target_))
                target_ = ((FlightModelMain) (super.FM)).AP.way.curr().getTargetActorRandom();
        }
        if(Actor.isValid(target_) && (target_ instanceof JU_88MSTL) && isNetMaster())
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

    public Actor typeDockableGetQueen()
    {
        return queen_;
    }

    public boolean typeDockableIsDocked()
    {
        return Actor.isValid(queen_);
    }

    public void typeDockableAttemptAttach()
    {
        if(((FlightModelMain) (super.FM)).AS.isMaster() && !typeDockableIsDocked())
        {
            Aircraft aircraft = War.getNearestFriend(this);
            if(aircraft instanceof JU_88MSTL)
                ((TypeDockable)aircraft).typeDockableRequestAttach(this);
        }
    }

    public void typeDockableAttemptDetach()
    {
        if(((FlightModelMain) (super.FM)).AS.isMaster() && typeDockableIsDocked() && Actor.isValid(queen_))
            ((TypeDockable)queen_).typeDockableRequestDetach(this);
    }

    public void typeDockableRequestAttach(Actor actor)
    {
    }

    public void typeDockableRequestDetach(Actor actor)
    {
    }

    public void typeDockableRequestAttach(Actor actor, int i, boolean flag)
    {
    }

    public void typeDockableRequestDetach(Actor actor, int i, boolean flag)
    {
    }

    public void typeDockableDoAttachToDrone(Actor actor, int i)
    {
    }

    public void typeDockableDoDetachFromDrone(int i)
    {
    }

    public void typeDockableDoAttachToQueen(Actor actor, int i)
    {
        queen_ = actor;
        dockport_ = i;
        if(((FlightModelMain) (super.FM)).EI.getNum() == 1)
        {
            super.FM.Scheme = 2;
            Aircraft aircraft = (Aircraft)actor;
            ((FlightModelMain) (super.FM)).EI.setNum(3);
            Motor motor = ((FlightModelMain) (super.FM)).EI.engines[0];
            ((FlightModelMain) (super.FM)).EI.engines = new Motor[3];
            ((FlightModelMain) (super.FM)).EI.engines[0] = motor;
            ((FlightModelMain) (super.FM)).EI.engines[1] = ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[0];
            ((FlightModelMain) (super.FM)).EI.engines[2] = ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.engines[1];
            ((FlightModelMain) (super.FM)).EI.bCurControl = (new boolean[] {
                true, true, true
            });
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.bCurControl[0] = false;
            ((FlightModelMain) (((SndAircraft) (aircraft)).FM)).EI.bCurControl[1] = false;
        }
        ((FlightModelMain) (super.FM)).EI.setEngineRunning();
        ((FlightModelMain) (super.FM)).CT.setGearAirborne();
        moveGear(0.0F);
        ((FlightModelMain) (super.FM)).CT.GearControl = ((FlightModelMain) (((SndAircraft) ((Aircraft)actor)).FM)).CT.GearControl;
        FlightModel flightmodel = ((SndAircraft) ((Aircraft)queen_)).FM;
        if((super.FM instanceof Maneuver) && (flightmodel instanceof Maneuver))
        {
            Maneuver maneuver = (Maneuver)flightmodel;
            Maneuver maneuver1 = (Maneuver)super.FM;
            if(maneuver.Group != null && maneuver1.Group != null && maneuver1.Group.numInGroup(this) == maneuver1.Group.nOfAirc - 1)
            {
                AirGroup airgroup = new AirGroup(maneuver1.Group);
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
            ((FlightModelMain) (super.FM)).CT.setTrimElevatorControl(0.51F);
            ((FlightModelMain) (super.FM)).CT.trimElevator = 0.51F;
            ((FlightModelMain) (super.FM)).CT.setGearAirborne();
            if(((FlightModelMain) (super.FM)).EI.getNum() == 3)
            {
                super.FM.Scheme = 1;
                ((FlightModelMain) (super.FM)).EI.setNum(1);
                Motor motor = ((FlightModelMain) (super.FM)).EI.engines[0];
                ((FlightModelMain) (super.FM)).EI.engines = new Motor[1];
                ((FlightModelMain) (super.FM)).EI.engines[0] = motor;
                ((FlightModelMain) (super.FM)).EI.bCurControl = (new boolean[] {
                    true
                });
                for(int j = 1; j < 3; j++)
                {
                    if(((FlightModelMain) (super.FM)).Gears.clpEngineEff[j][0] != null)
                    {
                        Eff3DActor.finish(((FlightModelMain) (super.FM)).Gears.clpEngineEff[j][0]);
                        ((FlightModelMain) (super.FM)).Gears.clpEngineEff[j][0] = null;
                    }
                    if(((FlightModelMain) (super.FM)).Gears.clpEngineEff[j][1] != null)
                    {
                        Eff3DActor.finish(((FlightModelMain) (super.FM)).Gears.clpEngineEff[j][1]);
                        ((FlightModelMain) (super.FM)).Gears.clpEngineEff[j][1] = null;
                    }
                }

            }
        }
    }

    public void typeDockableReplicateToNet(NetMsgGuaranted netmsgguaranted)
        throws IOException
    {
        if(typeDockableIsDocked())
        {
            netmsgguaranted.writeByte(1);
            ActorNet actornet = null;
            if(Actor.isValid(queen_))
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

    public void typeDockableReplicateFromNet(NetMsgInput netmsginput)
        throws IOException
    {
        if(netmsginput.readByte() == 1)
        {
            dockport_ = netmsginput.readByte();
            NetObj netobj = netmsginput.readNetObj();
            if(netobj != null)
            {
                Actor actor = (Actor)netobj.superObj();
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
            if(Config.isUSE_RENDER())
            {
                if(Main3D.cur3D().cockpits != null && Main3D.cur3D().cockpits[0] != null)
                    Main3D.cur3D().cockpits[0].onDoorMoved(f);
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
    private Actor target_;
    private Actor queen_;
    private int dockport_;

    static 
    {
        Class class1 = com.maddox.il2.objects.air.BF_109F4MSTL.class;
        new NetAircraft.SPAWN(class1);
        Property.set(class1, "iconFar_shortClassName", "Bf109");
        Property.set(class1, "meshName", "3DO/Plane/Bf-109F-4/hier.him");
        Property.set(class1, "PaintScheme", new PaintSchemeFMPar03());
        Property.set(class1, "meshName_sk", "3DO/Plane/Bf-109F-4(sk)/hier.him");
        Property.set(class1, "PaintScheme_sk", new PaintSchemeFMPar03());
        Property.set(class1, "yearService", 1941F);
        Property.set(class1, "yearExpired", 1944.5F);
        Property.set(class1, "FlightModel", "FlightModels/Bf-109F-4_Mod.fmd");
        Property.set(class1, "cockpitClass", new Class[] {
            com.maddox.il2.objects.air.CockpitBF_109F2.class
        });
        Property.set(class1, "LOSElevation", 0.74205F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1
        });
        weaponHooksRegister(class1, new String[] {
            "_MGUN01", "_MGUN02", "_CANNON01"
        });
        weaponsRegister(class1, "default", new String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG15120MGki 200"
        });
        weaponsRegister(class1, "none", new String[] {
            null, null, null
        });
    }

}