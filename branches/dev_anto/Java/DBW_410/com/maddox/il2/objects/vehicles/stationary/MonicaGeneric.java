// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 23/05/2011 10:04:37 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   MonicaGeneric.java

package com.maddox.il2.objects.vehicles.stationary;

import com.maddox.JGP.*;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.*;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeFastJet;
import com.maddox.il2.objects.air.TypeFighterAceMaker;
import com.maddox.il2.objects.air.TypeSupersonic;
import com.maddox.rts.*;
import com.maddox.sound.SoundFX;
import java.io.IOException;

public abstract class MonicaGeneric extends ActorHMesh
    implements ActorAlign
{
    class Move extends Interpolate
    {

        public boolean tick()
        {
            if(engineSFX != null)
                if(engineSTimer >= 0)
                {
                    if(--engineSTimer <= 0)
                    {
                        engineSTimer = (int)MonicaGeneric.SecsToTicks(MonicaGeneric.Rnd(10F, 10F));
                        if(!danger())
                            engineSTimer = -engineSTimer;
                    }
                } else
                if(++engineSTimer >= 0)
                {
                    engineSTimer = -(int)MonicaGeneric.SecsToTicks(MonicaGeneric.Rnd(10F, 10F));
                    if(danger())
                        engineSTimer = -engineSTimer;
                }
            return true;
        }

        Move()
        {
        }
    }

    class Master extends ActorNet
    {

        public boolean netInput(NetMsgInput netmsginput)
            throws IOException
        {
            return true;
        }

        public Master(Actor actor)
        {
            super(actor);
        }
    }

    class Mirror extends ActorNet
    {

        public boolean netInput(NetMsgInput netmsginput)
            throws IOException
        {
            return true;
        }

        NetMsgFiltered out;

        public Mirror(Actor actor, NetChannel netchannel, int i)
        {
            super(actor, netchannel, i);
            out = new NetMsgFiltered();
        }
    }

    public static class SPAWN
        implements ActorSpawn
    {

        public Actor actorSpawn(ActorSpawnArg actorspawnarg)
        {
            MonicaGeneric monicageneric = null;
            try
            {
                MonicaGeneric.constr_arg2 = actorspawnarg;
                monicageneric = (MonicaGeneric)cls.newInstance();
                MonicaGeneric.constr_arg2 = null;
            }
            catch(Exception exception)
            {
                MonicaGeneric.constr_arg2 = null;
                System.out.println(exception.getMessage());
                exception.printStackTrace();
                System.out.println("SPAWN: Can't create MonicaGeneric object [class:" + cls.getName() + "]");
                return null;
            }
            return monicageneric;
        }

        public Class cls;

        public SPAWN(Class class1)
        {
            Property.set(class1, "iconName", "icons/unknown.mat");
            Property.set(class1, "meshName", MonicaGeneric.mesh_name);
            cls = class1;
            Spawn.add(cls, this);
        }
    }


    public static final double Rnd(double d, double d1)
    {
        return World.Rnd().nextDouble(d, d1);
    }

    public static final float Rnd(float f, float f1)
    {
        return World.Rnd().nextFloat(f, f1);
    }

    private boolean RndB(float f)
    {
        return World.Rnd().nextFloat(0.0F, 1.0F) < f;
    }

    private static final long SecsToTicks(float f)
    {
        long l = (long)(0.5D + (double)(f / Time.tickLenFs()));
        return l < 1L ? 1L : l;
    }

    public void destroy()
    {
        if(isDestroyed())
        {
            return;
        } else
        {
            engineSFX = null;
            engineSTimer = 0xfa0a1f01;
            breakSounds();
            super.destroy();
            return;
        }
    }

    public Object getSwitchListener(Message message)
    {
        return this;
    }

    public boolean isStaticPos()
    {
        return true;
    }

    protected MonicaGeneric()
    {
        this(constr_arg2);
    }

    private MonicaGeneric(ActorSpawnArg actorspawnarg)
    {
        super(mesh_name);
        engineSFX = null;
        engineSTimer = 0x98967f;
        outCommand = new NetMsgFiltered();
        actorspawnarg.setStationary(this);
        myArmy = getArmy();
        collide(false);
        drawing(true);
        createNetObject(actorspawnarg.netChannel, actorspawnarg.netIdRemote);
        heightAboveLandSurface = 0.0F;
        Align();
        startMove();
    }

    public void startMove()
    {
        if(!interpEnd("move"))
        {
            interpPut(new Move(), "move", Time.current(), null);
            engineSFX = newSound("objects.siren", false);
            engineSTimer = -(int)SecsToTicks(Rnd(5F, 5F));
        }
    }

    private void Align()
    {
        pos.getAbs(p);
        p.z = Engine.land().HQ(p.x, p.y) + (double)heightAboveLandSurface;
        o.setYPR(pos.getAbsOrient().getYaw(), 0.0F, 0.0F);
        Engine.land().N(p.x, p.y, n);
        o.orient(n);
        pos.setAbs(p, o);
    }

    public void align()
    {
        Align();
    }

    private boolean danger()
    {
        Point3d point3d = new Point3d();
        pos.getAbs(point3d);
        Vector3d vector3d = new Vector3d();
        boolean flag = false;
        Aircraft aircraft = World.getPlayerAircraft();
        double d = Main3D.cur3D().land2D.worldOfsX() + ((Actor) (aircraft)).pos.getAbsPoint().x;
        double d1 = Main3D.cur3D().land2D.worldOfsY() + ((Actor) (aircraft)).pos.getAbsPoint().y;
        double d2 = Main3D.cur3D().land2D.worldOfsY() + ((Actor) (aircraft)).pos.getAbsPoint().z;
        int i = (int)(-((double)((Actor) (aircraft)).pos.getAbsOrient().getYaw() - 90D));
        if(i < 0)
            i = 360 + i;
        int j = (int)(-((double)((Actor) (aircraft)).pos.getAbsOrient().getPitch() - 90D));
        if(j < 0)
            j = 360 + j;
        for(java.util.Map.Entry entry = Engine.name2Actor().nextEntry(null); entry != null; entry = Engine.name2Actor().nextEntry(entry))
        {
            Actor actor = (Actor)entry.getValue();
            if((actor instanceof Aircraft) && ((actor instanceof TypeFighterAceMaker) && (actor instanceof TypeSupersonic))&& actor != World.getPlayerAircraft() && actor.getSpeed(vector3d) > 20D)
            {
                pos.getAbs(point3d);
                double d3 = Main3D.cur3D().land2D.worldOfsX() + actor.pos.getAbsPoint().x;
                double d4 = Main3D.cur3D().land2D.worldOfsY() + actor.pos.getAbsPoint().y;
                double d5 = Main3D.cur3D().land2D.worldOfsY() + actor.pos.getAbsPoint().z;
                new String();
                new String();
                int k = (int)(Math.floor(actor.pos.getAbsPoint().z * 0.10000000000000001D) * 10D);
                int l = (int)(Math.floor((actor.getSpeed(vector3d) * 60D * 60D) / 10000D) * 10D);
                double d6 = (int)(Math.ceil((d2 - d5) / 10D) * 10D);
                new String();
                double d7 = d3 - d;
                double d8 = d4 - d1;
                float f = 57.32484F * (float)Math.atan2(d8, -d7);
                int i1 = (int)(Math.floor((int)f) - 90D);
                if(i1 < 0)
                    i1 = 360 + i1;
                int j1 = i1 - i;
                double d9 = d - d3;
                double d10 = d1 - d4;
                double d11 = Math.sqrt(d6 * d6);
                int k1 = (int)(Math.ceil(Math.sqrt(d10 * d10 + d9 * d9) / 10D) * 10D);
                float f1 = 57.32484F * (float)Math.atan2(k1, d11);
                int l1 = (int)(Math.floor((int)f1) - 90D);
                if(l1 < 0)
                    l1 = 360 + l1;
                int i2 = l1 - j;
                int j2 = (int)(Math.ceil(((double)k1 * 3.2808399000000001D) / 100D) * 100D);
                String s = "ft";
                if(j2 >= 5280)
                {
                    j2 = (int)Math.floor(j2 / 5280);
                    s = "mi";
                }
                int k2 = (int)Math.ceil((double)l * 0.62137119200000002D);
                if((double)k1 <= 500D && (double)k1 >= 50D && i2 >= 255 && i2 <= 285 && Math.sqrt(j1 * j1) >= 120D)
                    HUD.logCenter("Enemy on Six! Evade!");
            }
        }

        return true;
    }

    public void createNetObject(NetChannel netchannel, int i)
    {
        if(netchannel == null)
            net = new Master(this);
        else
            net = new Mirror(this, netchannel, i);
    }

    public void netFirstUpdate(NetChannel netchannel)
        throws IOException
    {
    }

    private static String mesh_name = "3do/primitive/siren/mono.sim";
    private float heightAboveLandSurface;
    protected SoundFX engineSFX;
    protected int engineSTimer;
    private int myArmy;
    private static ActorSpawnArg constr_arg2 = null;
    private static Point3d p = new Point3d();
    private static Orient o = new Orient();
    private static Vector3f n = new Vector3f();
    private static Vector3d tmpv = new Vector3d();
    private NetMsgFiltered outCommand;
}