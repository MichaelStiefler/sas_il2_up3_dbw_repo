// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RocketHS_293.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeX4Carrier;
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

// Referenced classes of package com.maddox.il2.objects.weapons:
//            RocketBomb

public class RocketHS_293 extends com.maddox.il2.objects.weapons.RocketBomb
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
                getSpeed(com.maddox.il2.objects.weapons.RocketHS_293.v);
                out.writeFloat((float)com.maddox.il2.objects.weapons.RocketHS_293.v.x);
                out.writeFloat((float)com.maddox.il2.objects.weapons.RocketHS_293.v.y);
                out.writeFloat((float)com.maddox.il2.objects.weapons.RocketHS_293.v.z);
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
            com.maddox.il2.objects.weapons.RocketHS_293.v.x = netmsginput.readFloat();
            com.maddox.il2.objects.weapons.RocketHS_293.v.y = netmsginput.readFloat();
            com.maddox.il2.objects.weapons.RocketHS_293.v.z = netmsginput.readFloat();
            setSpeed(com.maddox.il2.objects.weapons.RocketHS_293.v);
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
                com.maddox.il2.objects.weapons.RocketHS_293 rockeths_293 = new RocketHS_293(actor, netmsginput.channel(), i, point3d, orient, f);
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
        if(tEStart > 0L && com.maddox.rts.Time.current() > tEStart)
        {
            tEStart = -1L;
            setThrust(17500F);
            if(com.maddox.il2.engine.Config.isUSE_RENDER())
            {
                if(smoke != null)
                    com.maddox.il2.engine.Eff3DActor.setIntesity(smoke, 1.0F);
                if(sprite != null)
                    com.maddox.il2.engine.Eff3DActor.setIntesity(sprite, 1.0F);
                if(flame != null)
                    flame.drawing(true);
                light.light.setEmit(2.0F, 100F);
            }
        }
        if(com.maddox.il2.ai.World.Rnd().nextFloat() > 0.8F && fl1 != null)
            com.maddox.il2.engine.Eff3DActor.setIntesity(fl1, com.maddox.il2.ai.World.Rnd().nextFloat(0.1F, 2.5F));
        if(com.maddox.il2.ai.World.Rnd().nextFloat() > 0.8F && fl2 != null)
            com.maddox.il2.engine.Eff3DActor.setIntesity(fl2, com.maddox.il2.ai.World.Rnd().nextFloat(0.1F, 2.5F));
        float f = com.maddox.rts.Time.tickLenFs();
        pos.getAbs(p, or);
        if(first)
            first = false;
        if(com.maddox.il2.engine.Actor.isValid(getOwner()))
        {
            if((getOwner() != com.maddox.il2.ai.World.getPlayerAircraft() || !((com.maddox.il2.fm.RealFlightModel)fm).isRealMode()) && (fm instanceof com.maddox.il2.ai.air.Maneuver) && target != null)
            {
                pT = target.pos.getAbsPoint();
                com.maddox.JGP.Point3d point3d = new Point3d();
                point3d.x = pT.x;
                point3d.y = pT.y;
                point3d.z = pT.z + 2.5D;
                point3d.sub(p);
                or.transformInv(point3d);
                com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)getOwner();
                boolean flag = aircraft.FM.isCapableOfACM() && !aircraft.FM.isReadyToDie() && !aircraft.FM.isTakenMortalDamage() && !aircraft.FM.AS.bIsAboutToBailout && !aircraft.FM.AS.isPilotDead(1);
                if(point3d.x > -10D && flag)
                {
                    double d = com.maddox.il2.objects.air.Aircraft.cvt((float)fm.Skill * aircraft.FM.AS.getPilotHealth(1), 0.0F, 3F, 10F, 2.0F);
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
            vector3d.z += ((double)f1 * 0.0018D * (double)f * (double)com.maddox.il2.fm.Atmosphere.g() * (double)Minit) / (double)M;
            if(isThrust)
                vector3d.z += (0.032000000000000001D * (double)f * (double)com.maddox.il2.fm.Atmosphere.g() * (double)Minit) / (double)M;
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

    public RocketHS_293()
    {
        first = true;
        fm = null;
        tStart = 0L;
        prevd = 1000F;
    }

    public RocketHS_293(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i, com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient, float f)
    {
        first = true;
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
        fm = ((com.maddox.il2.objects.air.Aircraft)getOwner()).FM;
        if(fm instanceof com.maddox.il2.ai.air.Maneuver)
        {
            maneuver = (com.maddox.il2.ai.air.Maneuver)fm;
            target = maneuver.target_ground;
        }
        tStart = com.maddox.rts.Time.current();
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            fl1 = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_NavLightR"), null, 1.0F, "3DO/Effects/Fireworks/FlareBlue1.eff", -1F);
            fl2 = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_NavLightG"), null, 1.0F, "3DO/Effects/Fireworks/FlareBlue1.eff", -1F);
            if(flame != null)
                flame.drawing(false);
            if(fl1 != null)
                com.maddox.il2.engine.Eff3DActor.setIntesity(fl1, 10F);
            com.maddox.il2.engine.Hook hook = null;
            hook = findHook("_SMOKE01");
            smoke01 = com.maddox.il2.engine.Eff3DActor.New(this, hook, null, 1.0F, "Effects/Smokes/SmokeBlack_planeTrail.eff", -1F);
            if(smoke01 != null)
                smoke01.pos.changeHookToRel();
        }
        noGDelay = -1L;
        tEStart = com.maddox.rts.Time.current() + 1000L;
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(smoke != null)
                com.maddox.il2.engine.Eff3DActor.setIntesity(smoke, 0.0F);
            if(sprite != null)
                com.maddox.il2.engine.Eff3DActor.setIntesity(sprite, 0.0F);
            if(flame != null)
                flame.drawing(false);
            light.light.setEmit(0.0F, 0.0F);
        }
        pos.getAbs(p, or);
        or.setYPR(or.getYaw(), or.getPitch(), 0.0F);
        pos.setAbs(p, or);
    }

    public void destroy()
    {
        if(isNet() && isNetMirror())
            doExplosionAir();
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            com.maddox.il2.engine.Eff3DActor.finish(fl1);
            com.maddox.il2.engine.Eff3DActor.finish(fl2);
            if(smoke01 != null)
                com.maddox.il2.engine.Eff3DActor.finish(smoke01);
        }
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
    private com.maddox.il2.ai.air.Maneuver maneuver;
    private com.maddox.il2.engine.Actor target;
    private com.maddox.il2.engine.Eff3DActor fl1;
    private com.maddox.il2.engine.Eff3DActor fl2;
    private com.maddox.il2.engine.Eff3DActor smoke01;
    private static com.maddox.il2.engine.Orient or = new Orient();
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.JGP.Point3d pT = new Point3d();
    private static com.maddox.JGP.Vector3d v = new Vector3d();
    private static com.maddox.il2.engine.Actor hunted = null;
    private long tStart;
    private long tEStart;
    private float prevd;
    private static double azimuthControlScaleFact = 1.0D;
    private static double tangageControlScaleFact = 1.0D;
    private boolean first;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketHS_293.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/HS-293/mono.sim");
        com.maddox.rts.Property.set(class1, "smoke", "3DO/Effects/Rocket/rocketsmokewhite.eff");
        com.maddox.rts.Property.set(class1, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
        com.maddox.rts.Property.set(class1, "emitLen", 50F);
        com.maddox.rts.Property.set(class1, "emitMax", 0.5F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_std");
        com.maddox.rts.Property.set(class1, "radius", 100F);
        com.maddox.rts.Property.set(class1, "timeLife", 1000F);
        com.maddox.rts.Property.set(class1, "timeFire", 12F);
        com.maddox.rts.Property.set(class1, "force", 0.0F);
        com.maddox.rts.Property.set(class1, "power", 210F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 1.1F);
        com.maddox.rts.Property.set(class1, "massa", 1045F);
        com.maddox.rts.Property.set(class1, "massaEnd", 700F);
        com.maddox.rts.Spawn.add(class1, new SPAWN());
    }

}
