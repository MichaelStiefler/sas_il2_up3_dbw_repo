// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RocketBat.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.ground.TgtShip;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeX4Carrier;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.ships.Ship;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.NetUpdate;
import com.maddox.rts.Property;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            RocketBomb

public class RocketBat extends com.maddox.il2.objects.weapons.RocketBomb
{
    class Master extends com.maddox.il2.engine.ActorNet
        implements com.maddox.rts.NetUpdate
    {

        public void msgNetNewChannel(com.maddox.rts.NetChannel netchannel)
        {
            if(!com.maddox.il2.engine.Actor.isValid(actor()))
                return;
            if(netchannel.isMirrored(this))
                return;
            try
            {
                if(netchannel.userState == 0)
                {
                    com.maddox.rts.NetMsgSpawn netmsgspawn = actor().netReplicate(netchannel);
                    if(netmsgspawn != null)
                    {
                        postTo(netchannel, netmsgspawn);
                        actor().netFirstUpdate(netchannel);
                    }
                }
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
            }
        }

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            return false;
        }

        public void netUpdate()
        {
            try
            {
                out.unLockAndClear();
                getSpeed(com.maddox.il2.objects.weapons.RocketBat.v);
                out.writeFloat((float)com.maddox.il2.objects.weapons.RocketBat.v.x);
                out.writeFloat((float)com.maddox.il2.objects.weapons.RocketBat.v.y);
                out.writeFloat((float)com.maddox.il2.objects.weapons.RocketBat.v.z);
                post(com.maddox.rts.Time.current(), out);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
            }
        }

        com.maddox.rts.NetMsgFiltered out;

        public Master(com.maddox.il2.engine.Actor actor)
        {
            super(actor);
            out = new NetMsgFiltered();
        }
    }

    class Mirror extends com.maddox.il2.engine.ActorNet
    {

        public void msgNetNewChannel(com.maddox.rts.NetChannel netchannel)
        {
            if(!com.maddox.il2.engine.Actor.isValid(actor()))
                return;
            if(netchannel.isMirrored(this))
                return;
            try
            {
                if(netchannel.userState == 0)
                {
                    com.maddox.rts.NetMsgSpawn netmsgspawn = actor().netReplicate(netchannel);
                    if(netmsgspawn != null)
                    {
                        postTo(netchannel, netmsgspawn);
                        actor().netFirstUpdate(netchannel);
                    }
                }
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
            }
        }

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            if(netmsginput.isGuaranted())
                return false;
            if(isMirrored())
            {
                out.unLockAndSet(netmsginput, 0);
                postReal(com.maddox.rts.Message.currentTime(true), out);
            }
            com.maddox.il2.objects.weapons.RocketBat.v.x = netmsginput.readFloat();
            com.maddox.il2.objects.weapons.RocketBat.v.y = netmsginput.readFloat();
            com.maddox.il2.objects.weapons.RocketBat.v.z = netmsginput.readFloat();
            setSpeed(com.maddox.il2.objects.weapons.RocketBat.v);
            return true;
        }

        com.maddox.rts.NetMsgFiltered out;

        public Mirror(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i)
        {
            super(actor, netchannel, i);
            out = new NetMsgFiltered();
        }
    }

    static class SPAWN
        implements com.maddox.rts.NetSpawn
    {

        public void netSpawn(int i, com.maddox.rts.NetMsgInput netmsginput)
        {
            com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
            if(netobj == null)
                return;
            try
            {
                com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)netobj.superObj();
                com.maddox.JGP.Point3d point3d = new Point3d(netmsginput.readFloat(), netmsginput.readFloat(), netmsginput.readFloat());
                com.maddox.il2.engine.Orient orient = new Orient(netmsginput.readFloat(), netmsginput.readFloat(), 0.0F);
                float f = netmsginput.readFloat();
                com.maddox.il2.objects.weapons.RocketBat rocketbat = new RocketBat(actor, netmsginput.channel(), i, point3d, orient, f);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        }

        SPAWN()
        {
        }
    }


    public boolean interpolateStep()
    {
        float f = com.maddox.rts.Time.tickLenFs();
        pos.getAbs(p, or);
        if(first)
            first = false;
        if(com.maddox.il2.engine.Actor.isValid(getOwner()))
        {
            ((com.maddox.il2.objects.air.TypeX4Carrier)fm.actor).typeX4CResetControls();
            if(target != null)
            {
                pT = target.pos.getAbsPoint();
                com.maddox.JGP.Point3d point3d = new Point3d();
                point3d.x = pT.x;
                point3d.y = pT.y;
                point3d.z = pT.z + 2.5D;
                point3d.sub(p);
                or.transformInv(point3d);
                if(point3d.x > -10D)
                {
                    double d;
                    if(target instanceof com.maddox.il2.ai.ground.TgtShip)
                        d = com.maddox.il2.objects.air.Aircraft.cvt(fm.Skill, 0.0F, 3F, 4F / targetRCSMax, 1.0F / targetRCSMax);
                    else
                        d = com.maddox.il2.objects.air.Aircraft.cvt(fm.Skill, 0.0F, 3F, 20F, 4F);
                    if(point3d.y > d)
                        ((com.maddox.il2.objects.air.TypeX4Carrier)fm.actor).typeX4CAdjSideMinus();
                    if(point3d.y < -d)
                        ((com.maddox.il2.objects.air.TypeX4Carrier)fm.actor).typeX4CAdjSidePlus();
                    if(point3d.z < -d)
                        ((com.maddox.il2.objects.air.TypeX4Carrier)fm.actor).typeX4CAdjAttitudeMinus();
                    if(point3d.z > d)
                        ((com.maddox.il2.objects.air.TypeX4Carrier)fm.actor).typeX4CAdjAttitudePlus();
                }
            }
            getSpeed(spd);
            float f1 = (float)spd.length();
            com.maddox.JGP.Vector3d vector3d = new Vector3d(0.0D, 0.0D, 0.0D);
            vector3d.y = -azimuthControlScaleFact * (double)f1 * (double)((com.maddox.il2.objects.air.TypeX4Carrier)fm.actor).typeX4CgetdeltaAzimuth();
            vector3d.z = tangageControlScaleFact * (double)f1 * (double)((com.maddox.il2.objects.air.TypeX4Carrier)fm.actor).typeX4CgetdeltaTangage();
            if(vector3d.y != 0.0D || vector3d.z != 0.0D)
                f1 *= 0.9992F;
            pos.getAbs(Or);
            Or.transform(vector3d);
            vector3d.z += (double)f1 * 0.0070000000000000001D * (double)f * (double)com.maddox.il2.fm.Atmosphere.g();
            spd.add(vector3d);
            float f2 = (float)spd.length();
            float f3 = f1 / f2;
            spd.scale(f3);
            setSpeed(spd);
            ((com.maddox.il2.objects.air.TypeX4Carrier)fm.actor).typeX4CResetControls();
        }
        if(!com.maddox.il2.engine.Actor.isValid(getOwner()) || !(getOwner() instanceof com.maddox.il2.objects.air.Aircraft))
        {
            doExplosionAir();
            postDestroy();
            collide(false);
            drawing(false);
            return true;
        } else
        {
            return true;
        }
    }

    public RocketBat()
    {
        first = true;
        targetRCSMax = 0.0F;
        fm = null;
        tStart = 0L;
        prevd = 1000F;
    }

    public RocketBat(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i, com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient, float f)
    {
        first = true;
        targetRCSMax = 0.0F;
        fm = null;
        tStart = 0L;
        prevd = 1000F;
        net = new Mirror(this, netchannel, i);
        pos.setAbs(point3d, orient);
        pos.reset();
        pos.setBase(actor, null, true);
        doStart(-1F);
        v.set(1.0D, 0.0D, 0.0D);
        orient.transform(v);
        v.scale(f);
        setSpeed(v);
        collide(false);
    }

    public void start(float f)
    {
        com.maddox.il2.engine.Actor actor = pos.base();
        if(com.maddox.il2.engine.Actor.isValid(actor) && (actor instanceof com.maddox.il2.objects.air.Aircraft))
        {
            if(actor.isNetMirror())
            {
                destroy();
                return;
            }
            net = new Master(this);
        }
        doStart(f);
    }

    private void doStart(float f)
    {
        super.start(-1F);
        tStart = com.maddox.rts.Time.current();
        pos.getAbs(p, or);
        or.setYPR(or.getYaw(), or.getPitch(), 0.0F);
        pos.setAbs(p, or);
        fm = ((com.maddox.il2.objects.air.Aircraft)getOwner()).FM;
        java.util.List list = com.maddox.il2.engine.Engine.targets();
        int i = list.size();
        float f1 = 0.0F;
        com.maddox.il2.engine.Actor actor = null;
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)list.get(j);
            if(!(actor1 instanceof com.maddox.il2.ai.ground.TgtShip) && !(actor1 instanceof com.maddox.il2.objects.bridges.BridgeSegment))
                continue;
            com.maddox.JGP.Point3d point3d = new Point3d();
            com.maddox.JGP.Point3d point3d1 = actor1.pos.getAbsPoint();
            point3d.x = point3d1.x;
            point3d.y = point3d1.y;
            point3d.z = point3d1.z;
            pos.getAbs(p, or);
            point3d.sub(p);
            or.transformInv(point3d);
            float f2 = antennaPattern(point3d, actor1);
            if(f2 > f1 && (double)f2 > 0.001D)
            {
                f1 = f2;
                actor = actor1;
                targetRCSMax = estimateRCS(actor);
            }
        }

        target = actor;
    }

    private float antennaPattern(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Actor actor)
    {
        float f = (float)java.lang.Math.sqrt(point3d.x * point3d.x + point3d.y * point3d.y + point3d.z * point3d.z);
        if(f > 32000F)
            return 0.0F;
        float f1 = (float)java.lang.Math.atan2(point3d.y, point3d.x);
        float f2 = (float)java.lang.Math.sqrt(point3d.x * point3d.x + point3d.y * point3d.y);
        float f3 = (float)java.lang.Math.atan2(point3d.z, f2);
        f3 += 0.2617992F;
        f /= 1000F;
        double d;
        if(java.lang.Math.cos(f1) > 0.0D && java.lang.Math.cos(f3) > 0.0D)
            d = (java.lang.Math.cos(f1) * java.lang.Math.cos(f3)) / (double)(f * f);
        else
            d = 0.0D;
        if(d > 0.0D && (actor instanceof com.maddox.il2.ai.ground.TgtShip))
        {
            float f4 = estimateRCS(actor);
            d *= f4;
        }
        return (float)d;
    }

    private float estimateRCS(com.maddox.il2.engine.Actor actor)
    {
        if((actor instanceof com.maddox.il2.objects.ships.Ship.PilotWater_US) || (actor instanceof com.maddox.il2.objects.ships.Ship.PilotBoatWater_US) || (actor instanceof com.maddox.il2.objects.ships.Ship.PilotWater_JA) || (actor instanceof com.maddox.il2.objects.ships.Ship.RwyCon) || (actor instanceof com.maddox.il2.objects.ships.Ship.RwySteel) || (actor instanceof com.maddox.il2.objects.ships.Ship.RwySteelLow) || (actor instanceof com.maddox.il2.objects.ships.Ship.RwyTransp) || (actor instanceof com.maddox.il2.objects.ships.Ship.RwyTranspWide) || (actor instanceof com.maddox.il2.objects.ships.Ship.RwyTranspSqr))
            return 0.0F;
        float f = 0.0F;
        f = actor.collisionR();
        if(f < 5F)
            f = 5F;
        return f / 40F;
    }

    public void destroy()
    {
        if(isNet() && isNetMirror())
            doExplosionAir();
        super.destroy();
    }

    protected void doExplosionAir()
    {
        pos.getTime(com.maddox.rts.Time.current(), p);
        com.maddox.il2.ai.MsgExplosion.send(null, null, p, getOwner(), 45F, 2.0F, 1, 550F);
        super.doExplosionAir();
    }

    public com.maddox.rts.NetMsgSpawn netReplicate(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
        com.maddox.rts.NetMsgSpawn netmsgspawn = super.netReplicate(netchannel);
        netmsgspawn.writeNetObj(getOwner().net);
        com.maddox.JGP.Point3d point3d = pos.getAbsPoint();
        netmsgspawn.writeFloat((float)point3d.x);
        netmsgspawn.writeFloat((float)point3d.y);
        netmsgspawn.writeFloat((float)point3d.z);
        com.maddox.il2.engine.Orient orient = pos.getAbsOrient();
        netmsgspawn.writeFloat(orient.azimut());
        netmsgspawn.writeFloat(orient.tangage());
        float f = (float)getSpeed(null);
        netmsgspawn.writeFloat(f);
        return netmsgspawn;
    }

    protected void mydebug(java.lang.String s)
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private com.maddox.il2.fm.FlightModel fm;
    private com.maddox.il2.engine.Actor target;
    private static com.maddox.il2.engine.Orient or = new Orient();
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.JGP.Point3d pT = new Point3d();
    private static com.maddox.JGP.Vector3d v = new Vector3d();
    private static com.maddox.JGP.Vector3d pOld = new Vector3d();
    private static com.maddox.JGP.Vector3d pNew = new Vector3d();
    private static com.maddox.il2.engine.Actor hunted = null;
    private long tStart;
    private float prevd;
    private static double azimuthControlScaleFact = 0.90000000000000002D;
    private static double tangageControlScaleFact = 0.90000000000000002D;
    private boolean first;
    private float targetRCSMax;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketBat.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/Bat/mono.sim");
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_std");
        com.maddox.rts.Property.set(class1, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
        com.maddox.rts.Property.set(class1, "emitLen", 0.0F);
        com.maddox.rts.Property.set(class1, "emitMax", 0.0F);
        com.maddox.rts.Property.set(class1, "radius", 100F);
        com.maddox.rts.Property.set(class1, "timeLife", 1000F);
        com.maddox.rts.Property.set(class1, "timeFire", 12F);
        com.maddox.rts.Property.set(class1, "force", 2.0F);
        com.maddox.rts.Property.set(class1, "power", 250F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 1.1F);
        com.maddox.rts.Property.set(class1, "massa", 853F);
        com.maddox.rts.Property.set(class1, "massaEnd", 853F);
        com.maddox.rts.Spawn.add(class1, new SPAWN());
    }

}
