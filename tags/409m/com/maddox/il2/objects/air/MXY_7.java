// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MXY_7.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
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
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme2a, Aircraft, G4M2E, TypeDockable, 
//            PaintSchemeSpecial, NetAircraft, PaintScheme

public class MXY_7 extends com.maddox.il2.objects.air.Scheme2a
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.objects.air.TypeDockable
{

    public MXY_7()
    {
        bNeedSetup = true;
        dtime = -1L;
        queen_last = null;
        queen_time = 0L;
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

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            for(int i = 0; i < 3; i++)
            {
                flame[i] = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine" + (i + 1) + "EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboJRD1100F.eff", -1F);
                dust[i] = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine" + (i + 1) + "EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboJRD1100D.eff", -1F);
                trail[i] = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine" + (i + 1) + "EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboJRD1100T.eff", -1F);
                sprite[i] = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine" + (i + 1) + "EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboJRD1100S.eff", -1F);
                com.maddox.il2.engine.Eff3DActor.setIntesity(flame[i], 0.0F);
                com.maddox.il2.engine.Eff3DActor.setIntesity(dust[i], 0.0F);
                com.maddox.il2.engine.Eff3DActor.setIntesity(trail[i], 0.0F);
                com.maddox.il2.engine.Eff3DActor.setIntesity(sprite[i], 0.0F);
            }

        }
    }

    public void doMurderPilot(int i)
    {
        if(i != 0)
        {
            return;
        } else
        {
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            hierMesh().chunkVisible("HMask1_D0", false);
            return;
        }
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xcf"))
            hitChunk("CF", shot);
        else
        if(s.startsWith("xtail"))
        {
            if(chunkDamageVisible("Tail1") < 1)
                hitChunk("Tail1", shot);
        } else
        if(s.startsWith("xkeel1"))
            hitChunk("Keel1", shot);
        else
        if(s.startsWith("xkeel2"))
            hitChunk("Keel2", shot);
        else
        if(s.startsWith("xrudder1"))
            hitChunk("Rudder1", shot);
        else
        if(s.startsWith("xrudder2"))
            hitChunk("Rudder2", shot);
        else
        if(s.startsWith("xstabl"))
            hitChunk("StabL", shot);
        else
        if(s.startsWith("xvator"))
            hitChunk("VatorL", shot);
        else
        if(s.startsWith("xwing"))
        {
            if(s.startsWith("xwinglin"))
                hitChunk("WingLIn", shot);
            if(s.startsWith("xwingrin"))
                hitChunk("WingRIn", shot);
        } else
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int i;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                i = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte0 = 2;
                i = s.charAt(6) - 49;
            } else
            {
                i = s.charAt(5) - 49;
            }
            hitFlesh(i, shot, byte0);
        }
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 3: // '\003'
        case 19: // '\023'
            FM.AS.setEngineDies(this, 0);
            return false;
        }
        return super.cutFM(i, j, actor);
    }

    protected void moveElevator(float f)
    {
        hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -30F * f, 0.0F);
    }

    public void msgEndAction(java.lang.Object obj, int i)
    {
        super.msgEndAction(obj, i);
        switch(i)
        {
        case 2: // '\002'
            com.maddox.il2.engine.Actor actor = null;
            if(com.maddox.il2.engine.Actor.isValid(queen_last))
                actor = queen_last;
            else
                actor = com.maddox.il2.engine.Engine.cur.actorLand;
            com.maddox.il2.ai.MsgExplosion.send(this, null, FM.Loc, actor, 0.0F, 600F, 0, 600F);
            break;
        }
    }

    protected void doExplosion()
    {
        super.doExplosion();
        if(FM.Loc.z - 10D < com.maddox.il2.ai.World.cur().land().HQ_Air(FM.Loc.x, FM.Loc.y))
            if(com.maddox.il2.engine.Engine.land().isWater(FM.Loc.x, FM.Loc.y))
                com.maddox.il2.objects.effects.Explosions.BOMB250_Water(FM.Loc, 1.0F, 1.0F);
            else
                com.maddox.il2.objects.effects.Explosions.BOMB250_Land(FM.Loc, 1.0F, 1.0F);
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
                    ((com.maddox.il2.ai.air.Maneuver)FM).unblock();
                    ((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(48);
                    ((com.maddox.il2.ai.air.Maneuver)FM).AP.way.setCur(((com.maddox.il2.objects.air.Aircraft)queen_).FM.AP.way.Cur());
                    ((com.maddox.il2.ai.air.Pilot)FM).setDumbTime(3000L);
                }
            } else
            if(!(FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)FM).isRealMode())
            {
                if(FM.EI.engines[0].getStage() == 0)
                    FM.EI.setEngineRunning();
                if(dtime > 0L)
                {
                    ((com.maddox.il2.ai.air.Maneuver)FM).setBusy(false);
                    ((com.maddox.il2.ai.air.Maneuver)FM).Group.leaderGroup = null;
                    ((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(22);
                    ((com.maddox.il2.ai.air.Pilot)FM).setDumbTime(3000L);
                    if(com.maddox.rts.Time.current() > dtime + 3000L)
                    {
                        dtime = -1L;
                        ((com.maddox.il2.ai.air.Maneuver)FM).clear_stack();
                        ((com.maddox.il2.ai.air.Maneuver)FM).pop();
                        ((com.maddox.il2.ai.air.Pilot)FM).setDumbTime(0L);
                    }
                }
            }
        super.update(f);
        if(FM.AS.isMaster())
        {
            for(int i = 0; i < 3; i++)
            {
                if(FM.CT.PowerControl > 0.77F && FM.EI.engines[i].getStage() == 0 && FM.M.fuel > 0.0F && !typeDockableIsDocked())
                    FM.EI.engines[i].setStage(this, 6);
                if(FM.CT.PowerControl < 0.77F && FM.EI.engines[i].getStage() > 0 || FM.M.fuel == 0.0F)
                    FM.EI.engines[i].setEngineStops(this);
            }

            if(com.maddox.il2.engine.Config.isUSE_RENDER())
            {
                for(int j = 0; j < 3; j++)
                    if(FM.EI.engines[j].getw() > 50F && FM.EI.engines[j].getStage() == 6)
                        FM.AS.setSootState(this, j, 1);
                    else
                        FM.AS.setSootState(this, j, 0);

            }
        }
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
                if(com.maddox.il2.engine.Actor.isValid(wing.airc[i]))
                    target_ = wing.airc[i];
                else
                    target_ = null;
            }
        }
        if(com.maddox.il2.engine.Actor.isValid(target_) && (target_ instanceof com.maddox.il2.objects.air.G4M2E))
        {
            queen_last = target_;
            queen_time = com.maddox.rts.Time.current();
            if(isNetMaster())
                ((com.maddox.il2.objects.air.TypeDockable)target_).typeDockableRequestAttach(this, 0, true);
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
            if(aircraft instanceof com.maddox.il2.objects.air.G4M2E)
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

    public void doSetSootState(int i, int j)
    {
        switch(j)
        {
        case 0: // '\0'
            com.maddox.il2.engine.Eff3DActor.setIntesity(flame[i], 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(dust[i], 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(trail[i], 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(sprite[i], 0.0F);
            break;

        case 1: // '\001'
            com.maddox.il2.engine.Eff3DActor.setIntesity(flame[i], 1.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(dust[i], 0.5F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(trail[i], 1.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(sprite[i], 1.0F);
            break;
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private com.maddox.il2.engine.Eff3DActor flame[] = {
        null, null, null
    };
    private com.maddox.il2.engine.Eff3DActor dust[] = {
        null, null, null
    };
    private com.maddox.il2.engine.Eff3DActor trail[] = {
        null, null, null
    };
    private com.maddox.il2.engine.Eff3DActor sprite[] = {
        null, null, null
    };
    private boolean bNeedSetup;
    private long dtime;
    private com.maddox.il2.engine.Actor queen_last;
    private long queen_time;
    private com.maddox.il2.engine.Actor target_;
    private com.maddox.il2.engine.Actor queen_;
    private int dockport_;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.MXY_7.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "MXY");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/MXY-7-11(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeSpecial());
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryJapan);
        com.maddox.rts.Property.set(class1, "yearService", 1945F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/MXY-7-11.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_Clip00"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null
        });
    }
}
