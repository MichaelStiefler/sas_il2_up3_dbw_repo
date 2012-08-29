// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   AImkXVGeneric.java

package com.maddox.il2.objects.vehicles.stationary;

import com.maddox.JGP.Point3d;
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
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public abstract class AImkXVGeneric extends com.maddox.il2.engine.ActorHMesh
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
                        engineSTimer = (int)com.maddox.il2.objects.vehicles.stationary.AImkXVGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.AImkXVGeneric.Rnd(5F, 5F));
                        if(!danger())
                            engineSTimer = -engineSTimer;
                    }
                } else
                if(++engineSTimer >= 0)
                {
                    engineSTimer = -(int)com.maddox.il2.objects.vehicles.stationary.AImkXVGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.AImkXVGeneric.Rnd(5F, 5F));
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
            com.maddox.il2.objects.vehicles.stationary.AImkXVGeneric aimkxvgeneric = null;
            try
            {
                com.maddox.il2.objects.vehicles.stationary.AImkXVGeneric.constr_arg2 = actorspawnarg;
                aimkxvgeneric = (com.maddox.il2.objects.vehicles.stationary.AImkXVGeneric)cls.newInstance();
                com.maddox.il2.objects.vehicles.stationary.AImkXVGeneric.constr_arg2 = null;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.vehicles.stationary.AImkXVGeneric.constr_arg2 = null;
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("SPAWN: Can't create AImkXVGeneric object [class:" + cls.getName() + "]");
                return null;
            }
            return ((com.maddox.il2.engine.Actor) (aimkxvgeneric));
        }

        public java.lang.Class cls;

        public SPAWN(java.lang.Class class1)
        {
            com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconName", "icons/unknown.mat");
            com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", com.maddox.il2.objects.vehicles.stationary.AImkXVGeneric.mesh_name);
            cls = class1;
            com.maddox.rts.Spawn.add(cls, ((java.lang.Object) (this)));
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
        return ((java.lang.Object) (this));
    }

    public boolean isStaticPos()
    {
        return true;
    }

    protected AImkXVGeneric()
    {
        this(constr_arg2);
    }

    private AImkXVGeneric(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
    {
        super(mesh_name);
        engineSFX = null;
        engineSTimer = 0x98967f;
        outCommand = new NetMsgFiltered();
        actorspawnarg.setStationary(((com.maddox.il2.engine.Actor) (this)));
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
            interpPut(((com.maddox.il2.engine.Interpolate) (new Move())), "move", com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
            engineSFX = newSound("objects.siren", false);
            engineSTimer = -(int)com.maddox.il2.objects.vehicles.stationary.AImkXVGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.AImkXVGeneric.Rnd(5F, 5F));
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
        com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
        double d = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().x;
        double d1 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().y;
        double d2 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().z;
        int i = (int)(-((double)((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsOrient().getYaw() - 90D));
        if(i < 0)
            i = 360 + i;
        int j = (int)(-((double)((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsOrient().getPitch() - 90D));
        if(j < 0)
            j = 360 + j;
        boolean flag = false;
        boolean flag1 = false;
        float f = 100F;
        Object obj = null;
        do
        {
            com.maddox.JGP.Point3d point3d1 = ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint();
            java.util.List list = com.maddox.il2.engine.Engine.targets();
            int k = list.size();
            for(int l = 0; l < k; l++)
            {
                com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(l);
                if((actor instanceof com.maddox.il2.objects.air.Aircraft) && actor != com.maddox.il2.ai.World.getPlayerAircraft() && actor.getArmy() != myArmy)
                {
                    double d3 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + actor.pos.getAbsPoint().x;
                    double d4 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + actor.pos.getAbsPoint().y;
                    double d5 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + actor.pos.getAbsPoint().z;
                    double d6 = d3 - d;
                    double d7 = d4 - d1;
                    double d8 = (int)(java.lang.Math.ceil((d2 - d5) / 10D) * 10D);
                    float f1 = 57.32484F * (float)java.lang.Math.atan2(d7, -d6);
                    int i1 = (int)(java.lang.Math.floor((int)f1) - 90D);
                    if(i1 < 0)
                        i1 = 360 + i1;
                    int j1 = i1 - i;
                    double d9 = d - d3;
                    double d10 = d1 - d4;
                    double d11 = java.lang.Math.sqrt(d8 * d8);
                    int k1 = (int)(java.lang.Math.ceil(java.lang.Math.sqrt(d10 * d10 + d9 * d9) / 10D) * 10D);
                    float f2 = 57.32484F * (float)java.lang.Math.atan2(k1, d11);
                    int l1 = (int)(java.lang.Math.floor((int)f2) - 90D);
                    if(l1 < 0)
                        l1 = 360 + l1;
                    int i2 = l1 - j;
                    if((float)k1 <= f && (double)k1 >= 76D && i2 >= 255 && i2 <= 285 && java.lang.Math.sqrt(j1 * j1) <= 35D && actor.getSpeed(vector3d) > 20D)
                    {
                        java.lang.String s = "level with us";
                        if(d2 - d5 - 200D >= 0.0D)
                            s = "below us";
                        if((d2 - d5) + 200D < 0.0D)
                            s = "above us";
                        int j2 = (int)(java.lang.Math.ceil(((double)k1 * 3.2808399000000001D) / 100D) * 100D);
                        java.lang.String s1 = "ft";
                        if(j2 >= 5280)
                        {
                            j2 = (int)java.lang.Math.floor(j2 / 5280);
                            s1 = " miles";
                            if(j2 == 1)
                                s1 = " mile";
                        }
                        com.maddox.il2.game.HUD.logCenter("                                          RO: Target bearing " + i1 + "\260" + ", range " + j2 + s1 + ", " + s);
                        flag1 = true;
                    }
                }
                f += 100F;
            }

        } while(!flag1 && f <= 6400F);
        return true;
    }

    public void createNetObject(com.maddox.rts.NetChannel netchannel, int i)
    {
        if(netchannel == null)
            net = ((com.maddox.il2.engine.ActorNet) (new Master(((com.maddox.il2.engine.Actor) (this)))));
        else
            net = ((com.maddox.il2.engine.ActorNet) (new Mirror(((com.maddox.il2.engine.Actor) (this)), netchannel, i)));
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
