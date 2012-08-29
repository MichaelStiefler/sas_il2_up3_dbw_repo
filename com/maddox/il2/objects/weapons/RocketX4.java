// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RocketX4.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.NearestTargets;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Orient;
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
//            Rocket

public class RocketX4 extends com.maddox.il2.objects.weapons.Rocket
{
    static class SPAWN
        implements com.maddox.rts.NetSpawn
    {

        public void netSpawn(int i, com.maddox.rts.NetMsgInput netmsginput)
        {
            com.maddox.rts.NetObj netobj;
            netobj = netmsginput.readNetObj();
            if(netobj == null)
                return;
            try
            {
                com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)netobj.superObj();
                com.maddox.JGP.Point3d point3d = new Point3d(netmsginput.readFloat(), netmsginput.readFloat(), netmsginput.readFloat());
                com.maddox.il2.engine.Orient orient = new Orient(netmsginput.readFloat(), netmsginput.readFloat(), 0.0F);
                float f = netmsginput.readFloat();
                com.maddox.il2.objects.weapons.RocketX4 rocketx4 = new RocketX4(actor, netmsginput.channel(), i, point3d, orient, f);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
            return;
        }

        SPAWN()
        {
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
                com.maddox.il2.objects.weapons.Mirror.printDebug(exception);
            }
            return;
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
            com.maddox.il2.objects.weapons.RocketX4.p.x = netmsginput.readFloat();
            com.maddox.il2.objects.weapons.RocketX4.p.y = netmsginput.readFloat();
            com.maddox.il2.objects.weapons.RocketX4.p.z = netmsginput.readFloat();
            int i = netmsginput.readShort();
            int j = netmsginput.readShort();
            float f = -(((float)i * 180F) / 32000F);
            float f1 = ((float)j * 90F) / 32000F;
            com.maddox.il2.objects.weapons.RocketX4.or.set(f, f1, 0.0F);
            pos.setAbs(com.maddox.il2.objects.weapons.RocketX4.p, com.maddox.il2.objects.weapons.RocketX4.or);
            return true;
        }

        com.maddox.rts.NetMsgFiltered out;

        public Mirror(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i)
        {
            super(actor, netchannel, i);
            out = new NetMsgFiltered();
        }
    }

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
                com.maddox.il2.objects.weapons.Master.printDebug(exception);
            }
            return;
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
                pos.getAbs(com.maddox.il2.objects.weapons.RocketX4.p, com.maddox.il2.objects.weapons.RocketX4.or);
                out.writeFloat((float)com.maddox.il2.objects.weapons.RocketX4.p.x);
                out.writeFloat((float)com.maddox.il2.objects.weapons.RocketX4.p.y);
                out.writeFloat((float)com.maddox.il2.objects.weapons.RocketX4.p.z);
                com.maddox.il2.objects.weapons.RocketX4.or.wrap();
                int i = (int)((com.maddox.il2.objects.weapons.RocketX4.or.getYaw() * 32000F) / 180F);
                int j = (int)((com.maddox.il2.objects.weapons.RocketX4.or.tangage() * 32000F) / 90F);
                out.writeShort(i);
                out.writeShort(j);
                post(com.maddox.rts.Time.current(), out);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.weapons.Master.printDebug(exception);
            }
        }

        com.maddox.rts.NetMsgFiltered out;

        public Master(com.maddox.il2.engine.Actor actor)
        {
            super(actor);
            out = new NetMsgFiltered();
        }
    }


    public boolean interpolateStep()
    {
        float f = com.maddox.rts.Time.tickLenFs();
        float f1 = (float)getSpeed(null);
        f1 += (320F - f1) * 0.1F * f;
        pos.getAbs(p, or);
        v.set(1.0D, 0.0D, 0.0D);
        or.transform(v);
        v.scale(f1);
        setSpeed(v);
        p.x += v.x * (double)f;
        p.y += v.y * (double)f;
        p.z += v.z * (double)f;
        if(isNet() && isNetMirror())
        {
            pos.setAbs(p, or);
            return false;
        }
        if(com.maddox.il2.engine.Actor.isValid(getOwner()))
        {
            if((getOwner() != com.maddox.il2.ai.World.getPlayerAircraft() || !((com.maddox.il2.fm.RealFlightModel)fm).isRealMode()) && (fm instanceof com.maddox.il2.ai.air.Pilot))
            {
                com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)fm;
                if(pilot.target != null)
                {
                    pilot.target.Loc.get(pT);
                    pT.sub(p);
                    or.transformInv(pT);
                    if(pT.x > -10D)
                    {
                        double d = com.maddox.il2.objects.air.Aircraft.cvt(fm.Skill, 0.0F, 3F, 15F, 0.0F);
                        if(pT.y > d)
                            ((com.maddox.il2.objects.air.TypeX4Carrier)fm.actor).typeX4CAdjSideMinus();
                        if(pT.y < -d)
                            ((com.maddox.il2.objects.air.TypeX4Carrier)fm.actor).typeX4CAdjSidePlus();
                        if(pT.z < -d)
                            ((com.maddox.il2.objects.air.TypeX4Carrier)fm.actor).typeX4CAdjAttitudeMinus();
                        if(pT.z > d)
                            ((com.maddox.il2.objects.air.TypeX4Carrier)fm.actor).typeX4CAdjAttitudePlus();
                    }
                }
            }
            or.increment(50F * f * ((com.maddox.il2.objects.air.TypeX4Carrier)fm.actor).typeX4CgetdeltaAzimuth(), 50F * f * ((com.maddox.il2.objects.air.TypeX4Carrier)fm.actor).typeX4CgetdeltaTangage(), 0.0F);
            or.setYPR(or.getYaw(), or.getPitch(), 0.0F);
            ((com.maddox.il2.objects.air.TypeX4Carrier)fm.actor).typeX4CResetControls();
        }
        pos.setAbs(p, or);
        if(com.maddox.rts.Time.current() > tStart + 500L)
        {
            hunted = com.maddox.il2.ai.air.NearestTargets.getEnemy(0, -1, p, 800D, 0);
            if(com.maddox.il2.engine.Actor.isValid(hunted))
            {
                float f2 = (float)p.distance(hunted.pos.getAbsPoint());
                if((hunted instanceof com.maddox.il2.objects.air.Aircraft) && (f2 < 20F || f2 < 40F && f2 > prevd && prevd != 1000F))
                {
                    doExplosionAir();
                    postDestroy();
                    collide(false);
                    drawing(false);
                }
                prevd = f2;
            } else
            {
                prevd = 1000F;
            }
        }
        if(!com.maddox.il2.engine.Actor.isValid(getOwner()) || !(getOwner() instanceof com.maddox.il2.objects.air.Aircraft))
        {
            doExplosionAir();
            postDestroy();
            collide(false);
            drawing(false);
            return false;
        } else
        {
            return false;
        }
    }

    public RocketX4()
    {
        fm = null;
        tStart = 0L;
        prevd = 1000F;
    }

    public RocketX4(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i, com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient, float f)
    {
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

    public void start(float f, int i)
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
        super.start(-1F, 0);
        fm = ((com.maddox.il2.objects.air.Aircraft)getOwner()).FM;
        tStart = com.maddox.rts.Time.current();
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            fl1 = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_NavLightR"), null, 1.0F, "3DO/Effects/Fireworks/FlareRed.eff", -1F);
            fl2 = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_NavLightG"), null, 1.0F, "3DO/Effects/Fireworks/FlareGreen.eff", -1F);
            flame.drawing(false);
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
        }
        super.destroy();
    }

    protected void doExplosion(com.maddox.il2.engine.Actor actor, java.lang.String s)
    {
        pos.getTime(com.maddox.rts.Time.current(), p);
        com.maddox.il2.ai.MsgExplosion.send(actor, s, p, getOwner(), 45F, 2.0F, 1, 550F);
        super.doExplosion(actor, s);
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

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private com.maddox.il2.fm.FlightModel fm;
    private com.maddox.il2.engine.Eff3DActor fl1;
    private com.maddox.il2.engine.Eff3DActor fl2;
    private static com.maddox.il2.engine.Orient or = new Orient();
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.JGP.Point3d pT = new Point3d();
    private static com.maddox.JGP.Vector3d v = new Vector3d();
    private static com.maddox.il2.engine.Actor hunted = null;
    private long tStart;
    private float prevd;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketX4.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/X-4/mono.sim");
        com.maddox.rts.Property.set(class1, "sprite", "3DO/Effects/Tracers/GuidedRocket/Black.eff");
        com.maddox.rts.Property.set(class1, "flame", "3do/effects/rocket/mono.sim");
        com.maddox.rts.Property.set(class1, "smoke", "3DO/Effects/Tracers/GuidedRocket/White.eff");
        com.maddox.rts.Property.set(class1, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
        com.maddox.rts.Property.set(class1, "emitLen", 50F);
        com.maddox.rts.Property.set(class1, "emitMax", 1.0F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.rocket_132");
        com.maddox.rts.Property.set(class1, "radius", 40F);
        com.maddox.rts.Property.set(class1, "timeLife", 30F);
        com.maddox.rts.Property.set(class1, "timeFire", 33F);
        com.maddox.rts.Property.set(class1, "force", 15712F);
        com.maddox.rts.Property.set(class1, "power", 2.0F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 1.049F);
        com.maddox.rts.Property.set(class1, "massa", 60F);
        com.maddox.rts.Property.set(class1, "massaEnd", 45F);
        com.maddox.rts.Spawn.add(class1, new SPAWN());
    }


}
