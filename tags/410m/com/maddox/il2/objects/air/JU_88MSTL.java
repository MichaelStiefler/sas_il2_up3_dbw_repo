// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   JU_88MSTL.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            JU_88, Aircraft, TypeDockable, PaintSchemeBMPar02, 
//            NetAircraft

public class JU_88MSTL extends com.maddox.il2.objects.air.JU_88
    implements com.maddox.il2.objects.air.TypeDockable
{

    public JU_88MSTL()
    {
        droneInitiator = null;
    }

    public void msgEndAction(java.lang.Object obj, int i)
    {
        super.msgEndAction(obj, i);
        switch(i)
        {
        case 2: // '\002'
            com.maddox.il2.ai.MsgExplosion.send(this, null, FM.Loc, droneInitiator, 0.0F, 4550F, 0, 890F);
            break;
        }
    }

    protected void doExplosion()
    {
        super.doExplosion();
        if(FM.Loc.z - 300D < com.maddox.il2.ai.World.cur().land().HQ_Air(FM.Loc.x, FM.Loc.y))
            if(com.maddox.il2.engine.Engine.land().isWater(FM.Loc.x, FM.Loc.y))
                com.maddox.il2.objects.effects.Explosions.bomb1000_water(FM.Loc, 1.0F, 1.0F);
            else
                com.maddox.il2.objects.effects.Explosions.bomb1000_land(FM.Loc, 1.0F, 1.0F, true);
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        setShot(shot);
        if(shot.chunkName.startsWith("WingLMid") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitTank(shot.initiator, 0, 1);
        if(shot.chunkName.startsWith("WingRMid") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitTank(shot.initiator, 3, 1);
        if(shot.chunkName.startsWith("WingLIn") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitTank(shot.initiator, 1, 1);
        if(shot.chunkName.startsWith("WingRIn") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitTank(shot.initiator, 2, 1);
        if(shot.chunkName.startsWith("Engine1") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitEngine(shot.initiator, 0, 1);
        if(shot.chunkName.startsWith("Engine2") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitEngine(shot.initiator, 1, 1);
        super.msgShot(shot);
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
        if(FM.AS.isMaster())
        {
            for(int i = 0; i < drones.length; i++)
                if(com.maddox.il2.engine.Actor.isValid(drones[i]))
                    typeDockableRequestDetach(drones[i], i, true);

        }
    }

    public void typeDockableRequestAttach(com.maddox.il2.engine.Actor actor)
    {
    }

    public void typeDockableRequestDetach(com.maddox.il2.engine.Actor actor)
    {
        for(int i = 0; i < drones.length; i++)
        {
            if(actor != drones[i])
                continue;
            com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)actor;
            if(!aircraft.FM.AS.isMaster())
                continue;
            if(FM.AS.isMaster())
                typeDockableRequestDetach(actor, i, true);
            else
                FM.AS.netToMaster(33, i, 1, actor);
        }

    }

    public void typeDockableRequestAttach(com.maddox.il2.engine.Actor actor, int i, boolean flag)
    {
        if(i != 0)
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
            droneInitiator = actor;
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
        if(FM.AS.isMaster())
        {
            if(i == 2)
                typeDockableRequestDetach(drones[0], 0, true);
            if(i == 13 && j == 0)
            {
                nextDMGLevels(4, 1, "CF_D0", this);
                return true;
            }
        }
        return super.cutFM(i, j, actor);
    }

    public void update(float f)
    {
        if(FM instanceof com.maddox.il2.ai.air.Pilot)
            ((com.maddox.il2.ai.air.Pilot)FM).setDumbTime(9999L);
        if((FM instanceof com.maddox.il2.ai.air.Maneuver) && FM.EI.engines[0].getStage() == 6 && FM.EI.engines[1].getStage() == 6)
        {
            ((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(44);
            ((com.maddox.il2.ai.air.Maneuver)FM).setSpeedMode(-1);
        }
        FM.CT.bHasGearControl = !FM.Gears.onGround();
        super.update(f);
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
    private com.maddox.il2.engine.Actor droneInitiator;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.JU_88MSTL.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ju-88");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Ju-88MSTL/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ju-88A-4Mistel.fmd");
        com.maddox.il2.objects.air.JU_88MSTL.weaponTriggersRegister(class1, new int[] {
            9
        });
        com.maddox.il2.objects.air.JU_88MSTL.weaponHooksRegister(class1, new java.lang.String[] {
            "_Dockport0"
        });
        com.maddox.il2.objects.air.JU_88MSTL.weaponsRegister(class1, "default", new java.lang.String[] {
            null
        });
        com.maddox.il2.objects.air.JU_88MSTL.weaponsRegister(class1, "none", new java.lang.String[] {
            null
        });
    }
}
