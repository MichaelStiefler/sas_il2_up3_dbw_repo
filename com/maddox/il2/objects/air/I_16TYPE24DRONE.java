// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   I_16TYPE24DRONE.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            I_16, Aircraft, TB_3_4M_34R_SPB, TypeDockable, 
//            PaintSchemeFMPar01, PaintSchemeFCSPar01, TypeTNBFighter, TypeStormovik, 
//            NetAircraft

public class I_16TYPE24DRONE extends com.maddox.il2.objects.air.I_16
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.objects.air.TypeTNBFighter, com.maddox.il2.objects.air.TypeStormovik, com.maddox.il2.objects.air.TypeDockable
{

    public I_16TYPE24DRONE()
    {
        queen_last = null;
        queen_time = 0L;
        bNeedSetup = true;
        dtime = -1L;
        target_ = null;
        queen_ = null;
    }

    public void msgCollisionRequest(com.maddox.il2.engine.Actor actor, boolean aflag[])
    {
        super.msgCollisionRequest(actor, aflag);
        if(queen_last != null && queen_last == actor && (queen_time == 0L || com.maddox.rts.Time.current() < queen_time + 5000L))
            aflag[0] = false;
        else
            aflag[0] = true;
    }

    public void update(float f)
    {
        if(bNeedSetup)
            checkAsDrone();
        int i = aircIndex();
        if(FM instanceof com.maddox.il2.ai.air.Maneuver)
            if(typeDockableIsDocked())
            {
                if(!(FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)FM).isRealMode())
                {
                    ((com.maddox.il2.ai.air.Maneuver)FM).unblock();
                    ((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(48);
                    for(int j = 0; j < i; j++)
                        ((com.maddox.il2.ai.air.Maneuver)FM).push(48);

                    if(FM.AP.way.curr().Action != 3)
                        ((com.maddox.il2.ai.air.Maneuver)FM).AP.way.setCur(((com.maddox.il2.objects.air.Aircraft)queen_).FM.AP.way.Cur());
                    ((com.maddox.il2.ai.air.Pilot)FM).setDumbTime(3000L);
                }
                if(FM.M.fuel < FM.M.maxFuel)
                    FM.M.fuel += 0.06F * f;
            } else
            if(!(FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)FM).isRealMode())
            {
                if(FM.EI.engines[0].getStage() == 0)
                    FM.EI.setEngineRunning();
                if(dtime > 0L && ((com.maddox.il2.ai.air.Maneuver)FM).Group != null)
                {
                    ((com.maddox.il2.ai.air.Maneuver)FM).Group.leaderGroup = null;
                    ((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(22);
                    ((com.maddox.il2.ai.air.Pilot)FM).setDumbTime(3000L);
                    if(com.maddox.rts.Time.current() > dtime + 3000L)
                    {
                        dtime = -1L;
                        ((com.maddox.il2.ai.air.Maneuver)FM).clear_stack();
                        ((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(0);
                        ((com.maddox.il2.ai.air.Pilot)FM).setDumbTime(0L);
                    }
                } else
                if(FM.AP.way.curr().Action == 0)
                {
                    com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)FM;
                    if(maneuver.Group != null && maneuver.Group.airc[0] == this && maneuver.Group.clientGroup != null)
                        maneuver.Group.setGroupTask(2);
                }
            }
        super.update(f);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag && FM.AP.way.curr().Action == 3 && typeDockableIsDocked() && java.lang.Math.abs(((com.maddox.il2.objects.air.Aircraft)queen_).FM.Or.getKren()) < 3F)
            if(FM.isPlayers())
            {
                if((FM instanceof com.maddox.il2.fm.RealFlightModel) && !((com.maddox.il2.fm.RealFlightModel)FM).isRealMode())
                {
                    typeDockableAttemptDetach();
                    ((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(22);
                    ((com.maddox.il2.ai.air.Maneuver)FM).setCheckStrike(false);
                    FM.Vwld.z -= 5D;
                    dtime = com.maddox.rts.Time.current();
                }
            } else
            {
                typeDockableAttemptDetach();
                ((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(22);
                ((com.maddox.il2.ai.air.Maneuver)FM).setCheckStrike(false);
                FM.Vwld.z -= 5D;
                dtime = com.maddox.rts.Time.current();
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
            if(com.maddox.il2.engine.Actor.isValid(target_) && (target_ instanceof com.maddox.il2.ai.Wing))
            {
                com.maddox.il2.ai.Wing wing = (com.maddox.il2.ai.Wing)target_;
                int i = aircIndex();
                if(com.maddox.il2.engine.Actor.isValid(wing.airc[i / 2]))
                    target_ = wing.airc[i / 2];
                else
                    target_ = null;
            }
        }
        if(com.maddox.il2.engine.Actor.isValid(target_) && (target_ instanceof com.maddox.il2.objects.air.TB_3_4M_34R_SPB))
        {
            queen_last = target_;
            queen_time = com.maddox.rts.Time.current();
            if(isNetMaster())
                ((com.maddox.il2.objects.air.TypeDockable)target_).typeDockableRequestAttach(this, aircIndex() % 2, true);
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
        if(!FM.AS.isMaster())
            return;
        if(!typeDockableIsDocked())
        {
            com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.War.getNearestFriend(this);
            if(aircraft instanceof com.maddox.il2.objects.air.TB_3_4M_34R_SPB)
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
        queen_last = queen_;
        queen_time = 0L;
        FM.EI.setEngineRunning();
        FM.CT.setGearAirborne();
        moveGear(0.0F);
        com.maddox.il2.fm.FlightModel flightmodel = ((com.maddox.il2.objects.air.Aircraft)queen_).FM;
        if(aircIndex() == 0 && (FM instanceof com.maddox.il2.ai.air.Maneuver) && (flightmodel instanceof com.maddox.il2.ai.air.Maneuver))
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
                airgroup.leaderGroup = null;
                airgroup.clientGroup = maneuver.Group;
            }
        }
    }

    public void typeDockableDoDetachFromQueen(int i)
    {
        if(dockport_ != i)
        {
            return;
        } else
        {
            queen_last = queen_;
            queen_time = com.maddox.rts.Time.current();
            queen_ = null;
            dockport_ = 0;
            return;
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

    private com.maddox.il2.engine.Actor queen_last;
    private long queen_time;
    private boolean bNeedSetup;
    private long dtime;
    private com.maddox.il2.engine.Actor target_;
    private com.maddox.il2.engine.Actor queen_;
    private int dockport_;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.I_16TYPE24DRONE.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "I-16");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/I-16type24(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "meshName_ru", "3DO/Plane/I-16type24/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ru", new PaintSchemeFCSPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1939F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/I-16type24.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitI_16TYPE24_SPB.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.82595F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 
            3, 3, 9, 9, 9, 9, 9, 9, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", 
            "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASsi 650", "MGunShKASsi 650", "MGunShVAKk 120", "MGunShVAKk 120", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2fab250", new java.lang.String[] {
            "MGunShKASsi 650", "MGunShKASsi 650", "MGunShVAKk 120", "MGunShVAKk 120", null, null, null, null, null, null, 
            "BombGunFAB250", "BombGunFAB250", null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
