// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   DZGeneric.java

package com.maddox.il2.objects.vehicles.stationary;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Land2D;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.ObjState;
import com.maddox.rts.Property;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;
import com.maddox.util.HashMapExt;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

public abstract class DZGeneric extends com.maddox.il2.engine.ActorHMesh
    implements com.maddox.il2.objects.ActorAlign
{
    class Move extends com.maddox.il2.engine.Interpolate
    {

        public boolean tick()
        {
            if(engineSFX != null)
                if(engineSTimer >= 0)
                {
                    if(--engineSTimer <= 0)
                    {
                        engineSTimer = (int)com.maddox.il2.objects.vehicles.stationary.DZGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.DZGeneric.Rnd(5F, 5F));
                        if(!danger())
                            engineSTimer = -engineSTimer;
                    }
                } else
                if(++engineSTimer >= 0)
                {
                    engineSTimer = -(int)com.maddox.il2.objects.vehicles.stationary.DZGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.DZGeneric.Rnd(5F, 5F));
                    if(danger())
                        engineSTimer = -engineSTimer;
                }
            return true;
        }

        Move()
        {
        }
    }

    class Master extends com.maddox.il2.engine.ActorNet
    {

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            return true;
        }

        public Master(com.maddox.il2.engine.Actor actor)
        {
            super(actor);
        }
    }

    class Mirror extends com.maddox.il2.engine.ActorNet
    {

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            return true;
        }

        com.maddox.rts.NetMsgFiltered out;

        public Mirror(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i)
        {
            super(actor, netchannel, i);
            out = new NetMsgFiltered();
        }
    }

    public static class SPAWN
        implements com.maddox.il2.engine.ActorSpawn
    {

        public com.maddox.il2.engine.Actor actorSpawn(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            com.maddox.il2.objects.vehicles.stationary.DZGeneric dzgeneric = null;
            try
            {
                com.maddox.il2.objects.vehicles.stationary.DZGeneric.constr_arg2 = actorspawnarg;
                dzgeneric = (com.maddox.il2.objects.vehicles.stationary.DZGeneric)cls.newInstance();
                com.maddox.il2.objects.vehicles.stationary.DZGeneric.constr_arg2 = null;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.vehicles.stationary.DZGeneric.constr_arg2 = null;
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("SPAWN: Can't create DZGeneric object [class:" + cls.getName() + "]");
                return null;
            }
            return dzgeneric;
        }

        public java.lang.Class cls;

        public SPAWN(java.lang.Class class1)
        {
            com.maddox.rts.Property.set(class1, "iconName", "icons/unknown.mat");
            com.maddox.rts.Property.set(class1, "meshName", com.maddox.il2.objects.vehicles.stationary.DZGeneric.mesh_name);
            cls = class1;
            com.maddox.rts.Spawn.add(cls, this);
        }
    }


    public static final double Rnd(double d, double d1)
    {
        return com.maddox.il2.ai.World.Rnd().nextDouble(d, d1);
    }

    public static final float Rnd(float f, float f1)
    {
        return com.maddox.il2.ai.World.Rnd().nextFloat(f, f1);
    }

    private boolean RndB(float f)
    {
        return com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < f;
    }

    private static final long SecsToTicks(float f)
    {
        long l = (long)(0.5D + (double)(f / com.maddox.rts.Time.tickLenFs()));
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

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public boolean isStaticPos()
    {
        return true;
    }

    protected DZGeneric()
    {
        this(constr_arg2);
    }

    private DZGeneric(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
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
            interpPut(new Move(), "move", com.maddox.rts.Time.current(), null);
            engineSFX = newSound("objects.siren", false);
            engineSTimer = -(int)com.maddox.il2.objects.vehicles.stationary.DZGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.DZGeneric.Rnd(30F, 30F));
        }
    }

    private void Align()
    {
        pos.getAbs(p);
        p.z = com.maddox.il2.engine.Engine.land().HQ(p.x, p.y) + (double)heightAboveLandSurface;
        o.setYPR(pos.getAbsOrient().getYaw(), 0.0F, 0.0F);
        com.maddox.il2.engine.Engine.land().N(p.x, p.y, n);
        o.orient(n);
        pos.setAbs(p, o);
    }

    public void align()
    {
        Align();
    }

    private boolean danger()
    {
        com.maddox.JGP.Point3d point3d = new Point3d();
        pos.getAbs(point3d);
        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        boolean flag = false;
        for(java.util.Map.Entry entry = com.maddox.il2.engine.Engine.name2Actor().nextEntry(null); entry != null; entry = com.maddox.il2.engine.Engine.name2Actor().nextEntry(entry))
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getValue();
            if(com.maddox.il2.engine.Actor.isAlive(actor) && actor != com.maddox.il2.ai.World.getPlayerAircraft() && (actor instanceof com.maddox.il2.objects.air.Aircraft) && actor.getArmy() == myArmy)
            {
                pos.getAbs(point3d);
                double d = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + ((com.maddox.JGP.Tuple3d) (point3d)).x;
                double d1 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + ((com.maddox.JGP.Tuple3d) (point3d)).y;
                double d2 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + actor.pos.getAbsPoint().x;
                double d3 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + actor.pos.getAbsPoint().y;
                double d4 = d2 - d;
                double d5 = d3 - d1;
                int i = (int)java.lang.Math.ceil(java.lang.Math.sqrt(d5 * d5 + d4 * d4));
                if(i <= 1000)
                    com.maddox.rts.ObjState.destroy(actor);
            }
        }

        return true;
    }

    public void createNetObject(com.maddox.rts.NetChannel netchannel, int i)
    {
        if(netchannel == null)
            net = new Master(this);
        else
            net = new Mirror(this, netchannel, i);
    }

    public void netFirstUpdate(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
    }

    private static java.lang.String mesh_name = "3do/primitive/siren/mono.sim";
    private float heightAboveLandSurface;
    protected com.maddox.sound.SoundFX engineSFX;
    protected int engineSTimer;
    private int myArmy;
    private static com.maddox.il2.engine.ActorSpawnArg constr_arg2 = null;
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();
    private static com.maddox.JGP.Vector3f n = new Vector3f();
    private static com.maddox.JGP.Vector3d tmpv = new Vector3d();
    private com.maddox.rts.NetMsgFiltered outCommand;





}
