// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   A1H_Tanker.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgOutput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            AD_Tanker, Aircraft, TypeDockable, PaintSchemeFMPar06, 
//            Scheme1, NetAircraft

public class A1H_Tanker extends com.maddox.il2.objects.air.AD_Tanker
    implements com.maddox.il2.objects.air.TypeDockable
{

    public A1H_Tanker()
    {
    }

    public boolean typeDockableIsDocked()
    {
        return true;
    }

    public void typeDockableAttemptAttach()
    {
    }

    public void typeDockableAttemptDetach()
    {
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.isMaster())
        {
            for(int i = 0; i < drones.length; i++)
                if(com.maddox.il2.engine.Actor.isValid(drones[i]))
                    typeDockableRequestDetach(drones[i], i, true);

        }
    }

    public void typeDockableRequestAttach(com.maddox.il2.engine.Actor actor)
    {
        if(actor instanceof com.maddox.il2.objects.air.Aircraft)
        {
            com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)actor;
            if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).AS.isMaster() && ((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM.getSpeedKMH() > 10F && super.FM.getSpeedKMH() > 10F)
            {
                for(int i = 0; i < drones.length; i++)
                {
                    if(com.maddox.il2.engine.Actor.isValid(drones[i]))
                        continue;
                    com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(this, "_Dockport" + i);
                    com.maddox.il2.engine.Loc loc = new Loc();
                    com.maddox.il2.engine.Loc loc1 = new Loc();
                    super.pos.getAbs(loc1);
                    hooknamed.computePos(this, loc1, loc);
                    actor.pos.getAbs(loc1);
                    if(loc.getPoint().distance(loc1.getPoint()) >= 7.5D)
                        continue;
                    if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.isMaster())
                        typeDockableRequestAttach(actor, i, true);
                    else
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.netToMaster(32, i, 0, actor);
                    break;
                }

            }
        }
    }

    public void typeDockableRequestDetach(com.maddox.il2.engine.Actor actor)
    {
        for(int i = 0; i < drones.length; i++)
        {
            if(actor != drones[i])
                continue;
            com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)actor;
            if(!((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).AS.isMaster())
                continue;
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.isMaster())
                typeDockableRequestDetach(actor, i, true);
            else
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.netToMaster(33, i, 1, actor);
        }

    }

    public void typeDockableRequestAttach(com.maddox.il2.engine.Actor actor, int i, boolean flag)
    {
        if(i >= 0 && i <= 1)
            if(flag)
            {
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.isMaster())
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.netToMirrors(34, i, 1, actor);
                    typeDockableDoAttachToDrone(actor, i);
                } else
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.netToMaster(34, i, 1, actor);
                }
            } else
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.isMaster())
            {
                if(!com.maddox.il2.engine.Actor.isValid(drones[i]))
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.netToMirrors(34, i, 1, actor);
                    typeDockableDoAttachToDrone(actor, i);
                }
            } else
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.netToMaster(34, i, 0, actor);
            }
    }

    public void typeDockableRequestDetach(com.maddox.il2.engine.Actor actor, int i, boolean flag)
    {
        if(flag)
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.isMaster())
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.netToMirrors(35, i, 1, actor);
                typeDockableDoDetachFromDrone(i);
            } else
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.netToMaster(35, i, 1, actor);
            }
    }

    public void typeDockableDoAttachToDrone(com.maddox.il2.engine.Actor actor, int i)
    {
        if(!com.maddox.il2.engine.Actor.isValid(drones[i]))
        {
            com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(this, "_Dockport" + i);
            com.maddox.il2.engine.Loc loc = new Loc();
            com.maddox.il2.engine.Loc loc1 = new Loc();
            super.pos.getAbs(loc1);
            hooknamed.computePos(this, loc1, loc);
            com.maddox.il2.engine.HookNamed probe = new HookNamed((com.maddox.il2.engine.ActorMesh)actor, "_Probe");
            com.maddox.il2.engine.Loc loc2 = new Loc();
            probe.computePos(this, loc, loc2);
            actor.pos.setAbs(loc2);
            actor.pos.setBase(this, null, true);
            actor.pos.resetAsBase();
            drones[i] = actor;
            ((com.maddox.il2.objects.air.TypeDockable)drones[i]).typeDockableDoAttachToQueen(this, i);
        }
    }

    public void typeDockableDoDetachFromDrone(int i)
    {
        if(com.maddox.il2.engine.Actor.isValid(drones[i]))
        {
            drones[i].pos.setBase(null, null, true);
            ((com.maddox.il2.objects.air.TypeDockable)drones[i]).typeDockableDoDetachFromQueen(i);
            drones[i] = null;
        }
    }

    public void typeDockableDoAttachToQueen(com.maddox.il2.engine.Actor actor1, int j)
    {
    }

    public void typeDockableDoDetachFromQueen(int j)
    {
    }

    public void typeDockableReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        for(int i = 0; i < drones.length; i++)
            if(com.maddox.il2.engine.Actor.isValid(drones[i]))
            {
                netmsgguaranted.writeByte(1);
                com.maddox.il2.engine.ActorNet actornet = drones[i].net;
                if(actornet.countNoMirrors() == 0)
                    netmsgguaranted.writeNetObj(actornet);
                else
                    netmsgguaranted.writeNetObj(null);
            } else
            {
                netmsgguaranted.writeByte(0);
            }

    }

    public void typeDockableReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        for(int i = 0; i < drones.length; i++)
        {
            if(netmsginput.readByte() != 1)
                continue;
            com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
            if(netobj != null)
                typeDockableDoAttachToDrone((com.maddox.il2.engine.Actor)netobj.superObj(), i);
        }

    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.isMaster())
            switch(i)
            {
            case 33: // '!'
            case 34: // '"'
            case 35: // '#'
                typeDockableRequestDetach(drones[0], 0, true);
                break;

            case 36: // '$'
            case 37: // '%'
            case 38: // '&'
                typeDockableRequestDetach(drones[1], 1, true);
                break;
            }
        return super.cutFM(i, j, actor);
    }

    static java.lang.Class _mthclass$(java.lang.String x0)
    {
        return java.lang.Class.forName(x0);
        java.lang.ClassNotFoundException x1;
        x1;
        throw new NoClassDefFoundError(x1.getMessage());
    }

    private com.maddox.il2.engine.Actor drones[] = {
        null, null
    };

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.A1H_Tanker.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "A1H");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/A1H_Tanker(multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "yearService", 1945F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/A1H.fmd");
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.0585F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 1, 1, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev17", "_ExternalDev18"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x DT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "FuelTankGun_TankAD4 1", "FuelTankGun_TankAD4 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
