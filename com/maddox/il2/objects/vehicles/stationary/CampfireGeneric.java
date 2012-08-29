// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CampfireGeneric.java

package com.maddox.il2.objects.vehicles.stationary;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RandomVector;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMeshDraw;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.LightPointWorld;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;
import java.io.IOException;
import java.io.PrintStream;

public abstract class CampfireGeneric extends com.maddox.il2.engine.ActorHMesh
    implements com.maddox.il2.objects.ActorAlign
{
    public static class SPAWN
        implements com.maddox.il2.engine.ActorSpawn
    {

        public com.maddox.il2.engine.Actor actorSpawn(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            com.maddox.il2.objects.vehicles.stationary.CampfireGeneric campfiregeneric = null;
            try
            {
                com.maddox.il2.objects.vehicles.stationary.CampfireGeneric.constr_arg2 = actorspawnarg;
                campfiregeneric = (com.maddox.il2.objects.vehicles.stationary.CampfireGeneric)cls.newInstance();
                com.maddox.il2.objects.vehicles.stationary.CampfireGeneric.constr_arg2 = null;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.vehicles.stationary.CampfireGeneric.constr_arg2 = null;
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("SPAWN: Can't create CampfireGeneric object [class:" + cls.getName() + "]");
                return null;
            }
            return campfiregeneric;
        }

        public java.lang.Class cls;

        public SPAWN(java.lang.Class class1)
        {
            com.maddox.rts.Property.set(class1, "iconName", "icons/unknown.mat");
            com.maddox.rts.Property.set(class1, "meshName", com.maddox.il2.objects.vehicles.stationary.CampfireGeneric.mesh_name);
            cls = class1;
            com.maddox.rts.Spawn.add(cls, this);
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

    class MyDraw extends com.maddox.il2.engine.ActorMeshDraw
    {

        public void lightUpdate(com.maddox.il2.engine.Loc loc, boolean flag)
        {
            if(flag)
            {
                com.maddox.il2.ai.RandomVector.getTimed(com.maddox.rts.Time.currentReal() * 24L, com.maddox.il2.objects.vehicles.stationary.CampfireGeneric.tmpv, hashCode);
                float f = java.lang.Math.abs((float)com.maddox.il2.objects.vehicles.stationary.CampfireGeneric.tmpv.x);
                light.light.setEmit(0.3F + f * 0.9F, 27F + f * 8F);
            }
            super.lightUpdate(loc, flag);
        }

        MyDraw()
        {
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

    protected CampfireGeneric()
    {
        this(constr_arg2);
    }

    private CampfireGeneric(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
    {
        super(mesh_name);
        outCommand = new NetMsgFiltered();
        actorspawnarg.setStationary(this);
        setArmy(0);
        collide(false);
        drawing(true);
        draw = new MyDraw();
        light = new LightPointActor(new LightPointWorld(), new Point3d(0.0D, 0.0D, 1.5D));
        light.light.setColor(new Color3f(1.0F, 0.95F, 0.66F));
        light.light.setEmit(1.0F, 22F);
        draw.lightMap().put("light", light);
        createNetObject(actorspawnarg.netChannel, actorspawnarg.netIdRemote);
        heightAboveLandSurface = 0.0F;
        Align();
        hashCode = rnd.nextInt(0, 4095);
    }

    private void Align()
    {
        pos.getAbs(p);
        p.z = com.maddox.il2.engine.Engine.land().HQ(p.x, p.y) + (double)heightAboveLandSurface;
        o.setYPR(pos.getAbsOrient().getYaw(), 0.0F, 0.0F);
        pos.setAbs(p, o);
    }

    public void align()
    {
        Align();
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

    private static java.lang.String mesh_name = "3do/buildings/furniture/campfire/mono.sim";
    private float heightAboveLandSurface;
    private com.maddox.il2.engine.LightPointActor light;
    private int hashCode;
    private static com.maddox.il2.ai.RangeRandom rnd = new RangeRandom();
    private static com.maddox.il2.engine.ActorSpawnArg constr_arg2 = null;
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();
    private static com.maddox.JGP.Vector3f n = new Vector3f();
    private static com.maddox.JGP.Vector3d tmpv = new Vector3d();
    private com.maddox.rts.NetMsgFiltered outCommand;






}
