// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   SARGeneric.java

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
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Land2D;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
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
import com.maddox.util.HashMapExt;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

public abstract class SARGeneric extends com.maddox.il2.engine.ActorHMesh
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
                        engineSTimer = (int)com.maddox.il2.objects.vehicles.stationary.SARGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.SARGeneric.Rnd(15F, 15F));
                        if(!danger())
                            engineSTimer = -engineSTimer;
                    }
                } else
                if(++engineSTimer >= 0)
                {
                    engineSTimer = -(int)com.maddox.il2.objects.vehicles.stationary.SARGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.SARGeneric.Rnd(15F, 15F));
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
            com.maddox.il2.objects.vehicles.stationary.SARGeneric sargeneric = null;
            try
            {
                com.maddox.il2.objects.vehicles.stationary.SARGeneric.constr_arg2 = actorspawnarg;
                sargeneric = (com.maddox.il2.objects.vehicles.stationary.SARGeneric)cls.newInstance();
                com.maddox.il2.objects.vehicles.stationary.SARGeneric.constr_arg2 = null;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.vehicles.stationary.SARGeneric.constr_arg2 = null;
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("SPAWN: Can't create SARGeneric object [class:" + cls.getName() + "]");
                return null;
            }
            return ((com.maddox.il2.engine.Actor) (sargeneric));
        }

        public java.lang.Class cls;

        public SPAWN(java.lang.Class class1)
        {
            com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconName", "icons/unknown.mat");
            com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", com.maddox.il2.objects.vehicles.stationary.SARGeneric.mesh_name);
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

    protected SARGeneric()
    {
        this(constr_arg2);
    }

    private SARGeneric(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
    {
        super(mesh_name);
        pickup = false;
        popped = false;
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
            engineSTimer = -(int)com.maddox.il2.objects.vehicles.stationary.SARGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.SARGeneric.Rnd(30F, 30F));
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
        float f = com.maddox.il2.ai.World.getTimeofDay();
        java.lang.String s = "Effects/Smokes/Yellowsmoke.eff";
        if(f >= 0.0F && f <= 5F || f >= 21F && f <= 24F)
            s = "3DO/Effects/Fireworks/FlareWhiteWide.eff";
        com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
        double d = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().x;
        double d1 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().y;
        int i = (int)(-((double)((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsOrient().getYaw() - 90D));
        if(i < 0)
            i = 360 + i;
        for(java.util.Map.Entry entry = com.maddox.il2.engine.Engine.name2Actor().nextEntry(((java.util.Map.Entry) (null))); entry != null; entry = com.maddox.il2.engine.Engine.name2Actor().nextEntry(entry))
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getValue();
            pos.getAbs(point3d);
            if((actor instanceof com.maddox.il2.objects.air.Aircraft) && actor.pos.getAbsPoint().distance(point3d) <= 1000D)
            {
                if(!popped)
                {
                    com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (this)), ((com.maddox.il2.engine.Hook) (null)), new Loc(0.0D, 0.0D, 0.0D, 0.0F, 90F, 0.0F), 1.0F, s, -1F);
                    popped = true;
                }
                if((actor instanceof com.maddox.il2.objects.air.Aircraft) && actor.pos.getAbsPoint().distance(point3d) <= 300D && actor.getSpeed(vector3d) < 10D)
                {
                    com.maddox.il2.game.HUD.logCenter("                                                                             We got him! Heading home!");
                    pickup = true;
                    destroy();
                }
            }
            if(!pickup)
            {
                boolean flag = false;
                com.maddox.il2.objects.air.Aircraft aircraft1 = com.maddox.il2.ai.World.getPlayerAircraft();
                if(((com.maddox.il2.engine.Actor) (aircraft1)).pos.getAbsPoint().distance(point3d) < 3000D)
                    flag = true;
                pos.getAbs(point3d);
                double d2 = (com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + ((com.maddox.JGP.Tuple3d) (point3d)).x) / 10000D;
                double d3 = (com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + ((com.maddox.JGP.Tuple3d) (point3d)).y) / 10000D;
                double d4 = com.maddox.il2.game.Main3D.cur3D().land2D.mapSizeX() / 1000D;
                double d5 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + ((com.maddox.il2.engine.Actor) (aircraft1)).pos.getAbsPoint().x / 10000D;
                double d6 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + ((com.maddox.il2.engine.Actor) (aircraft1)).pos.getAbsPoint().y / 10000D;
                char c = (char)(int)(65D + java.lang.Math.floor((d2 / 676D - java.lang.Math.floor(d2 / 676D)) * 26D));
                char c1 = (char)(int)(65D + java.lang.Math.floor((d2 / 26D - java.lang.Math.floor(d2 / 26D)) * 26D));
                double d7 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + ((com.maddox.JGP.Tuple3d) (point3d)).x;
                double d8 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + ((com.maddox.JGP.Tuple3d) (point3d)).y;
                new String();
                java.lang.String s1;
                if(d4 > 260D)
                    s1 = "" + c + c1;
                else
                    s1 = "" + c1;
                double d9 = d7 - d;
                double d10 = d8 - d1;
                float f1 = 57.32484F * (float)java.lang.Math.atan2(d10, -d9);
                double d11 = java.lang.Math.floor((int)f1) - 90D;
                if(d11 < 0.0D)
                    d11 = 360D + d11;
                int j = (int)(d11 - (double)i);
                if(j < 0)
                    j = 360 + j;
                int k = (int)(java.lang.Math.ceil((double)(j + 15) / 30D) - 1.0D);
                if(k == 0)
                    k = 12;
                new String();
                double d12 = java.lang.Math.ceil(d3);
                if(flag && ((com.maddox.il2.engine.Actor) (aircraft1)).pos.getAbsPoint().distance(point3d) < 2000D)
                    com.maddox.il2.game.HUD.logCenter("                                                                             Man in the water! " + k + " o'clock!");
                else
                    com.maddox.il2.game.HUD.logCenter("                                                                             SAR required at map grid " + s1 + "-" + d12);
            }
        }

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

    private static java.lang.String mesh_name = "3do/humans/Paratroopers/Water/US_Dinghy/live.sim";
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
    public boolean pickup;
    public boolean popped;





}
