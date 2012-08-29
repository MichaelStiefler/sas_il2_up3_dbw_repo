// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GCIGeneric.java

package com.maddox.il2.objects.vehicles.stationary;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.War;
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

public abstract class GCIGeneric extends com.maddox.il2.engine.ActorHMesh
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
                        engineSTimer = (int)com.maddox.il2.objects.vehicles.stationary.GCIGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.GCIGeneric.Rnd(30F, 50F));
                        if(!danger())
                            engineSTimer = -engineSTimer;
                    }
                } else
                if(++engineSTimer >= 0)
                {
                    engineSTimer = -(int)com.maddox.il2.objects.vehicles.stationary.GCIGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.GCIGeneric.Rnd(30F, 50F));
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
            com.maddox.il2.objects.vehicles.stationary.GCIGeneric gcigeneric = null;
            try
            {
                com.maddox.il2.objects.vehicles.stationary.GCIGeneric.constr_arg2 = actorspawnarg;
                gcigeneric = (com.maddox.il2.objects.vehicles.stationary.GCIGeneric)cls.newInstance();
                com.maddox.il2.objects.vehicles.stationary.GCIGeneric.constr_arg2 = null;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.vehicles.stationary.GCIGeneric.constr_arg2 = null;
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("SPAWN: Can't create GCIGeneric object [class:" + cls.getName() + "]");
                return null;
            }
            return gcigeneric;
        }

        public java.lang.Class cls;

        public SPAWN(java.lang.Class class1)
        {
            com.maddox.rts.Property.set(class1, "iconName", "icons/unknown.mat");
            com.maddox.rts.Property.set(class1, "meshName", com.maddox.il2.objects.vehicles.stationary.GCIGeneric.mesh_name);
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

    protected GCIGeneric()
    {
        this(constr_arg2);
    }

    private GCIGeneric(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
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
            engineSTimer = -(int)com.maddox.il2.objects.vehicles.stationary.GCIGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.GCIGeneric.Rnd(30F, 30F));
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
        float f = 1000F;
        com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.War.GetNearestEnemyAircraft(com.maddox.il2.ai.World.getPlayerAircraft(), 1000F, 9);
        for(int i = 0; aircraft == null && i < 100; i++)
        {
            aircraft = com.maddox.il2.ai.War.GetNearestEnemyAircraft(com.maddox.il2.ai.World.getPlayerAircraft(), 1000F + f, 9);
            f += 1000F;
        }

        boolean flag1 = false;
        if(aircraft.getSpeed(vector3d) > 20D && ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().distance(point3d) < 100000D && ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().z >= 150D && aircraft != com.maddox.il2.ai.World.getPlayerAircraft() && aircraft.getArmy() != myArmy)
        {
            pos.getAbs(point3d);
            com.maddox.il2.objects.air.Aircraft aircraft1 = com.maddox.il2.ai.World.getPlayerAircraft();
            if(((com.maddox.il2.engine.Actor) (aircraft1)).pos.getAbsPoint().distance(point3d) < 100000D && ((com.maddox.il2.engine.Actor) (aircraft1)).pos.getAbsPoint().z >= 150D)
                flag = true;
            double d = (com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + ((com.maddox.il2.engine.Actor) (aircraft1)).pos.getAbsPoint().x) / 10000D;
            double d1 = (com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + ((com.maddox.il2.engine.Actor) (aircraft1)).pos.getAbsPoint().y) / 10000D;
            double d2 = (com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + ((com.maddox.il2.engine.Actor) (aircraft1)).pos.getAbsPoint().x) / 1000D;
            double d3 = (com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + ((com.maddox.il2.engine.Actor) (aircraft1)).pos.getAbsPoint().y) / 1000D;
            double d4 = com.maddox.il2.game.Main3D.cur3D().land2D.mapSizeX() / 1000D;
            double d5 = (com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().x) / 10000D;
            double d6 = (com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().y) / 10000D;
            double d7 = (com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().x) / 1000D;
            double d8 = (com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().y) / 1000D;
            char c = (char)(int)(65D + java.lang.Math.floor((d5 / 676D - java.lang.Math.floor(d5 / 676D)) * 26D));
            char c1 = (char)(int)(65D + java.lang.Math.floor((d5 / 26D - java.lang.Math.floor(d5 / 26D)) * 26D));
            new String();
            java.lang.String s;
            if(d4 > 260D)
                s = "" + c + c1;
            else
                s = "" + c1;
            new String();
            int j = (int)(java.lang.Math.floor(((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().z * 0.10000000000000001D) * 10D);
            int k = (int)(java.lang.Math.floor((aircraft.getSpeed(vector3d) * 60D * 60D) / 10000D) * 10D);
            new String();
            double d9 = java.lang.Math.ceil(d6);
            double d10 = d5 - d;
            double d11 = d6 - d1;
            float f1 = 57.32484F * (float)java.lang.Math.atan2(d11, -d10);
            double d12 = java.lang.Math.floor((int)f1) - 90D;
            if(d12 < 0.0D)
                d12 = 360D + d12;
            int l = (int)d12;
            double d13 = d2 - d7;
            double d14 = d3 - d8;
            int i1 = (int)(-((double)((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsOrient().getYaw() - 90D));
            if(i1 < 0)
                i1 = 360 + i1;
            int j1 = (int)java.lang.Math.ceil(java.lang.Math.sqrt(d14 * d14 + d13 * d13));
            if(flag)
            {
                if(j1 > 4)
                    com.maddox.il2.game.HUD.logCenter("                                          Target bearing " + l + "\260" + ", range " + j1 + "km, height " + j + "m, heading " + i1 + "\260");
                else
                    com.maddox.il2.game.HUD.logCenter(" ");
            } else
            {
                com.maddox.il2.game.HUD.logCenter("                                                                             Target at " + s + "-" + d9 + ", height " + j + "m, heading " + i1 + "\260");
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
