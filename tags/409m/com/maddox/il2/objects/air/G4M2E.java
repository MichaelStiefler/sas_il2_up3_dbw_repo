// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   G4M2E.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            G4M, Aircraft, TypeDockable, PaintSchemeBMPar05, 
//            PaintSchemeFCSPar02, TypeBomber, TypeTransport, NetAircraft

public class G4M2E extends com.maddox.il2.objects.air.G4M
    implements com.maddox.il2.objects.air.TypeBomber, com.maddox.il2.objects.air.TypeTransport, com.maddox.il2.objects.air.TypeDockable
{

    public G4M2E()
    {
    }

    public boolean typeDockableIsDocked()
    {
        return true;
    }

    public com.maddox.il2.engine.Actor typeDockableGetDrone()
    {
        return drones[0];
    }

    public void typeDockableAttemptAttach()
    {
    }

    public void typeDockableAttemptDetach()
    {
        if(FM.AS.isMaster())
        {
            for(int i = 0; i < drones.length; i++)
                if(com.maddox.il2.engine.Actor.isValid(drones[i]))
                    typeDockableRequestDetach(drones[i], i, true);

        }
    }

    public void typeDockableRequestAttach(com.maddox.il2.engine.Actor actor)
    {
        if(!(actor instanceof com.maddox.il2.objects.air.Aircraft))
            return;
        com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)actor;
        if(aircraft.FM.AS.isMaster() && aircraft.FM.Gears.onGround() && aircraft.FM.getSpeedKMH() < 10F && FM.getSpeedKMH() < 10F)
        {
            for(int i = 0; i < drones.length; i++)
                if(!com.maddox.il2.engine.Actor.isValid(drones[i]))
                {
                    com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(this, "_Dockport" + i);
                    com.maddox.il2.engine.Loc loc = new Loc();
                    com.maddox.il2.engine.Loc loc1 = new Loc();
                    pos.getAbs(loc1);
                    hooknamed.computePos(this, loc1, loc);
                    actor.pos.getAbs(loc1);
                    if(loc.getPoint().distance(loc1.getPoint()) < 5D)
                        if(FM.AS.isMaster())
                        {
                            typeDockableRequestAttach(actor, i, true);
                            return;
                        } else
                        {
                            FM.AS.netToMaster(32, i, 0, actor);
                            return;
                        }
                }

        }
    }

    public void typeDockableRequestDetach(com.maddox.il2.engine.Actor actor)
    {
        for(int i = 0; i < drones.length; i++)
            if(actor == drones[i])
            {
                com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)actor;
                if(aircraft.FM.AS.isMaster())
                    if(FM.AS.isMaster())
                        typeDockableRequestDetach(actor, i, true);
                    else
                        FM.AS.netToMaster(33, i, 1, actor);
            }

    }

    public void typeDockableRequestAttach(com.maddox.il2.engine.Actor actor, int i, boolean flag)
    {
        if(i < 0 || i > 1)
            return;
        if(flag)
        {
            if(FM.AS.isMaster())
            {
                FM.AS.netToMirrors(34, i, 1, actor);
                typeDockableDoAttachToDrone(actor, i);
            } else
            {
                FM.AS.netToMaster(34, i, 1, actor);
            }
        } else
        if(FM.AS.isMaster())
        {
            if(!com.maddox.il2.engine.Actor.isValid(drones[i]))
            {
                FM.AS.netToMirrors(34, i, 1, actor);
                typeDockableDoAttachToDrone(actor, i);
            }
        } else
        {
            FM.AS.netToMaster(34, i, 0, actor);
        }
    }

    public void typeDockableRequestDetach(com.maddox.il2.engine.Actor actor, int i, boolean flag)
    {
        if(flag)
            if(FM.AS.isMaster())
            {
                FM.AS.netToMirrors(35, i, 1, actor);
                typeDockableDoDetachFromDrone(i);
            } else
            {
                FM.AS.netToMaster(35, i, 1, actor);
            }
    }

    public void typeDockableDoAttachToDrone(com.maddox.il2.engine.Actor actor, int i)
    {
        if(!com.maddox.il2.engine.Actor.isValid(drones[i]))
        {
            com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(this, "_Dockport" + i);
            com.maddox.il2.engine.Loc loc = new Loc();
            com.maddox.il2.engine.Loc loc1 = new Loc();
            pos.getAbs(loc1);
            hooknamed.computePos(this, loc1, loc);
            actor.pos.setAbs(loc);
            actor.pos.setBase(this, null, true);
            actor.pos.resetAsBase();
            drones[i] = actor;
            ((com.maddox.il2.objects.air.TypeDockable)drones[i]).typeDockableDoAttachToQueen(this, i);
        }
    }

    public void typeDockableDoDetachFromDrone(int i)
    {
        if(!com.maddox.il2.engine.Actor.isValid(drones[i]))
        {
            return;
        } else
        {
            drones[i].pos.setBase(null, null, true);
            ((com.maddox.il2.objects.air.TypeDockable)drones[i]).typeDockableDoDetachFromQueen(i);
            drones[i] = null;
            return;
        }
    }

    public void typeDockableDoAttachToQueen(com.maddox.il2.engine.Actor actor, int i)
    {
    }

    public void typeDockableDoDetachFromQueen(int i)
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
            if(netmsginput.readByte() == 1)
            {
                com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                if(netobj != null)
                    typeDockableDoAttachToDrone((com.maddox.il2.engine.Actor)netobj.superObj(), i);
            }

    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            if(f < -20F)
            {
                f = -20F;
                flag = false;
            }
            if(f > 20F)
            {
                f = 20F;
                flag = false;
            }
            if(f1 < -20F)
            {
                f1 = -20F;
                flag = false;
            }
            if(f1 > 20F)
            {
                f1 = 20F;
                flag = false;
            }
            break;

        case 1: // '\001'
            if(f < -30F)
            {
                f = -30F;
                flag = false;
            }
            if(f > 30F)
            {
                f = 30F;
                flag = false;
            }
            if(f1 < -30F)
            {
                f1 = -30F;
                flag = false;
            }
            if(f1 > 30F)
            {
                f1 = 30F;
                flag = false;
            }
            break;

        case 2: // '\002'
            if(f1 < -3F)
            {
                f1 = -3F;
                flag = false;
            }
            if(f1 > 70F)
            {
                f1 = 70F;
                flag = false;
            }
            break;

        case 3: // '\003'
            if(f < -10F)
            {
                f = -10F;
                flag = false;
            }
            if(f > 70F)
            {
                f = 70F;
                flag = false;
            }
            if(f1 < -40F)
            {
                f1 = -40F;
                flag = false;
            }
            if(f1 > 25F)
            {
                f1 = 25F;
                flag = false;
            }
            break;

        case 4: // '\004'
            if(f < -70F)
            {
                f = -70F;
                flag = false;
            }
            if(f > 45F)
            {
                f = 45F;
                flag = false;
            }
            if(f1 < -40F)
            {
                f1 = -40F;
                flag = false;
            }
            if(f1 > 25F)
            {
                f1 = 25F;
                flag = false;
            }
            break;

        case 5: // '\005'
            if(f < -30F)
            {
                f = -30F;
                flag = false;
            }
            if(f > 30F)
            {
                f = 30F;
                flag = false;
            }
            if(f1 < -70F)
            {
                f1 = -70F;
                flag = false;
            }
            if(f1 > 70F)
            {
                f1 = 70F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private com.maddox.il2.engine.Actor drones[] = {
        null
    };

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.G4M2E.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "G4M");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/G4M2E(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar05());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/G4M2E(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1945F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1946F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/G4M1-11.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 13, 14, 15
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunHo5t 500", "MGunHo5t 500", "MGunHo5t 500", "MGunHo5t 500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
