// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FW_190A8MSTL.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.Mission;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.air:
//            FW_190, Aircraft, JU_88MSTL, TypeDockable, 
//            PaintSchemeFMPar01, NetAircraft

public class FW_190A8MSTL extends com.maddox.il2.objects.air.FW_190
    implements com.maddox.il2.objects.air.TypeDockable
{

    public FW_190A8MSTL()
    {
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
            if(oldProp[j] < 2)
            {
                i = java.lang.Math.abs((int)(FM.EI.engines[j].getw() * 0.12F * 1.5F));
                if(i >= 1)
                    i = 1;
                if(i != oldProp[j])
                {
                    hierMesh().chunkVisible(com.maddox.il2.objects.air.Aircraft.Props[j][oldProp[j]], false);
                    oldProp[j] = i;
                    hierMesh().chunkVisible(com.maddox.il2.objects.air.Aircraft.Props[j][i], true);
                }
            }
            if(i == 0)
            {
                propPos[j] = (propPos[j] + 57.3F * FM.EI.engines[j].getw() * f) % 360F;
            } else
            {
                float f1 = 57.3F * FM.EI.engines[j].getw();
                f1 %= 2880F;
                f1 /= 2880F;
                if(f1 <= 0.5F)
                    f1 *= 2.0F;
                else
                    f1 = f1 * 2.0F - 2.0F;
                f1 *= 1200F;
                propPos[j] = (propPos[j] + f1 * f) % 360F;
            }
            hierMesh().chunkSetAngles(com.maddox.il2.objects.air.Aircraft.Props[j][0], 0.0F, -propPos[j], 0.0F);
        }

    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 77F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 77F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 157F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 157F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC99_D0", 20F * f, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
        float f1 = java.lang.Math.max(-f * 1500F, -94F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        if(typeDockableIsDocked())
            com.maddox.il2.objects.air.FW_190A8MSTL.moveGear(hierMesh(), 0.0F);
        else
            com.maddox.il2.objects.air.FW_190A8MSTL.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        if(FM.CT.getGear() < 0.98F)
        {
            return;
        } else
        {
            hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -f, 0.0F);
            return;
        }
    }

    public void update(float f)
    {
        if(bNeedSetup)
            checkAsDrone();
        if(FM instanceof com.maddox.il2.ai.air.Maneuver)
            if(typeDockableIsDocked())
            {
                if(!(FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)FM).isRealMode())
                {
                    ((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(48);
                    ((com.maddox.il2.ai.air.Maneuver)FM).AP.way.setCur(((com.maddox.il2.objects.air.Aircraft)queen_).FM.AP.way.Cur());
                    ((com.maddox.il2.ai.air.Pilot)FM).setDumbTime(3000L);
                }
            } else
            if(!(FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)FM).isRealMode())
                if(dtime > 0L)
                {
                    ((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(22);
                    ((com.maddox.il2.ai.air.Pilot)FM).setDumbTime(3000L);
                    if(com.maddox.rts.Time.current() > dtime + 3000L)
                    {
                        dtime = -1L;
                        ((com.maddox.il2.ai.air.Maneuver)FM).clear_stack();
                        ((com.maddox.il2.ai.air.Maneuver)FM).pop();
                        ((com.maddox.il2.ai.air.Pilot)FM).setDumbTime(0L);
                    }
                } else
                if(FM.AP.way.curr().Action == 3 && ((com.maddox.il2.ai.air.Maneuver)FM).get_maneuver() == 24)
                {
                    ((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(21);
                    ((com.maddox.il2.ai.air.Pilot)FM).setDumbTime(30000L);
                }
        if(typeDockableIsDocked())
        {
            com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)typeDockableGetQueen();
            aircraft.FM.CT.AileronControl = FM.CT.AileronControl;
            aircraft.FM.CT.ElevatorControl = FM.CT.ElevatorControl;
            aircraft.FM.CT.RudderControl = FM.CT.RudderControl;
            aircraft.FM.CT.GearControl = FM.CT.GearControl;
        }
        if(FM.CT.saveWeaponControl[3])
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
        if(com.maddox.il2.game.Mission.isCoop() && !com.maddox.il2.game.Mission.isServer() && !isSpawnFromMission() && net.isMaster())
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
        java.lang.String s = null;
        if(FM.AP.way.curr().getTargetName() == null)
            FM.AP.way.next();
        s = FM.AP.way.curr().getTargetName();
        if(s != null)
            actor = com.maddox.il2.engine.Actor.getByName(s);
        if(com.maddox.il2.engine.Actor.isValid(actor) && (actor instanceof com.maddox.il2.ai.Wing) && actor.getOwnerAttachedCount() > 0)
            actor = (com.maddox.il2.engine.Actor)actor.getOwnerAttached(0);
        FM.AP.way.setCur(0);
        if(com.maddox.il2.engine.Actor.isValid(actor) && (actor instanceof com.maddox.il2.objects.air.JU_88MSTL))
            try
            {
                com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)actor;
                float f = 100F;
                if(aircraft.FM.M.maxFuel > 0.0F)
                    f = (aircraft.FM.M.fuel / aircraft.FM.M.maxFuel) * 100F;
                java.lang.String s1 = "spawn " + actor.getClass().getName() + " NAME net" + actor.name() + " FUEL " + f + " WEAPONS " + aircraft.thisWeaponsName + (aircraft.bPaintShemeNumberOn ? "" : " NUMBEROFF") + " OVR";
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
            if(FM.AP.way.curr().getTarget() == null)
                FM.AP.way.next();
            target_ = FM.AP.way.curr().getTarget();
            if(com.maddox.il2.engine.Actor.isValid(target_))
                target_ = FM.AP.way.curr().getTargetActorRandom();
        }
        if(com.maddox.il2.engine.Actor.isValid(target_) && (target_ instanceof com.maddox.il2.objects.air.JU_88MSTL) && isNetMaster())
            ((com.maddox.il2.objects.air.TypeDockable)target_).typeDockableRequestAttach(this, 0, true);
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
        if(!FM.AS.isMaster())
            return;
        if(!typeDockableIsDocked())
        {
            com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.War.getNearestFriend(this);
            if(aircraft instanceof com.maddox.il2.objects.air.JU_88MSTL)
                ((com.maddox.il2.objects.air.TypeDockable)aircraft).typeDockableRequestAttach(this);
        }
    }

    public void typeDockableAttemptDetach()
    {
        if(FM.AS.isMaster() && typeDockableIsDocked() && com.maddox.il2.engine.Actor.isValid(queen_))
            ((com.maddox.il2.objects.air.TypeDockable)queen_).typeDockableRequestDetach(this);
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
        if(FM.EI.getNum() == 1)
        {
            FM.Scheme = 2;
            com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)actor;
            FM.EI.setNum(3);
            com.maddox.il2.fm.Motor motor = FM.EI.engines[0];
            FM.EI.engines = new com.maddox.il2.fm.Motor[3];
            FM.EI.engines[0] = motor;
            FM.EI.engines[1] = aircraft.FM.EI.engines[0];
            FM.EI.engines[2] = aircraft.FM.EI.engines[1];
            FM.EI.bCurControl = (new boolean[] {
                true, true, true
            });
            aircraft.FM.EI.bCurControl[0] = false;
            aircraft.FM.EI.bCurControl[1] = false;
        }
        FM.EI.setEngineRunning();
        FM.CT.setGearAirborne();
        moveGear(0.0F);
        FM.CT.GearControl = ((com.maddox.il2.objects.air.Aircraft)actor).FM.CT.GearControl;
        com.maddox.il2.fm.FlightModel flightmodel = ((com.maddox.il2.objects.air.Aircraft)queen_).FM;
        if((FM instanceof com.maddox.il2.ai.air.Maneuver) && (flightmodel instanceof com.maddox.il2.ai.air.Maneuver))
        {
            com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)flightmodel;
            com.maddox.il2.ai.air.Maneuver maneuver1 = (com.maddox.il2.ai.air.Maneuver)FM;
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
        if(dockport_ != i)
            return;
        queen_ = null;
        dockport_ = 0;
        FM.CT.setTrimElevatorControl(0.51F);
        FM.CT.trimElevator = 0.51F;
        FM.CT.setGearAirborne();
        if(FM.EI.getNum() == 3)
        {
            FM.Scheme = 1;
            FM.EI.setNum(1);
            com.maddox.il2.fm.Motor motor = FM.EI.engines[0];
            FM.EI.engines = new com.maddox.il2.fm.Motor[1];
            FM.EI.engines[0] = motor;
            FM.EI.bCurControl = (new boolean[] {
                true
            });
            for(int j = 1; j < 3; j++)
            {
                if(FM.Gears.clpEngineEff[j][0] != null)
                {
                    com.maddox.il2.engine.Eff3DActor.finish(FM.Gears.clpEngineEff[j][0]);
                    FM.Gears.clpEngineEff[j][0] = null;
                }
                if(FM.Gears.clpEngineEff[j][1] != null)
                {
                    com.maddox.il2.engine.Eff3DActor.finish(FM.Gears.clpEngineEff[j][1]);
                    FM.Gears.clpEngineEff[j][1] = null;
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
                ((com.maddox.il2.objects.air.TypeDockable)actor).typeDockableDoAttachToDrone(this, dockport_);
            }
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private boolean bNeedSetup;
    private long dtime;
    private com.maddox.il2.engine.Actor target_;
    private com.maddox.il2.engine.Actor queen_;
    private int dockport_;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.FW_190A8MSTL.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "FW190");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Fw-190A-8(Beta)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Fw-190A-8.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitFW_190F8MSTL.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.764106F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 3, 9, 9, 1, 1, 9, 
            9, 1, 1, 1, 1, 9, 9, 1, 1, 9, 
            9, 1, 1, 9, 9, 2, 2
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalDev01", "_ExternalDev02", "_CANNON03", "_CANNON04", "_ExternalDev03", 
            "_ExternalDev04", "_CANNON05", "_CANNON06", "_CANNON07", "_CANNON08", "_ExternalDev05", "_ExternalDev06", "_CANNON09", "_CANNON10", "_ExternalDev07", 
            "_ExternalDev08", "_CANNON11", "_CANNON12", "_ExternalDev09", "_ExternalDev10", "_ExternalRock01", "_ExternalRock02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, "PylonMG15120Internal", "PylonMG15120Internal", "MGunMG15120kh 125", "MGunMG15120kh 125", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
    }

}
