// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   FACGeneric.java

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
import com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric;
import com.maddox.il2.objects.vehicles.cars.CarGeneric;
import com.maddox.il2.objects.vehicles.tanks.TankGeneric;
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
import java.util.Random;

public abstract class FACGeneric extends com.maddox.il2.engine.ActorHMesh
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
                        engineSTimer = (int)com.maddox.il2.objects.vehicles.stationary.FACGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.FACGeneric.Rnd(60F, 60F));
                        if(!danger())
                            engineSTimer = -engineSTimer;
                    }
                } else
                if(++engineSTimer >= 0)
                {
                    engineSTimer = -(int)com.maddox.il2.objects.vehicles.stationary.FACGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.FACGeneric.Rnd(60F, 60F));
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
            com.maddox.il2.objects.vehicles.stationary.FACGeneric facgeneric = null;
            try
            {
                com.maddox.il2.objects.vehicles.stationary.FACGeneric.constr_arg2 = actorspawnarg;
                facgeneric = (com.maddox.il2.objects.vehicles.stationary.FACGeneric)cls.newInstance();
                com.maddox.il2.objects.vehicles.stationary.FACGeneric.constr_arg2 = null;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.vehicles.stationary.FACGeneric.constr_arg2 = null;
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("SPAWN: Can't create FACGeneric object [class:" + cls.getName() + "]");
                return null;
            }
            return ((com.maddox.il2.engine.Actor) (facgeneric));
        }

        public java.lang.Class cls;

        public SPAWN(java.lang.Class class1)
        {
            com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconName", "icons/unknown.mat");
            com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", com.maddox.il2.objects.vehicles.stationary.FACGeneric.mesh_name);
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

    protected FACGeneric()
    {
        this(constr_arg2);
    }

    private FACGeneric(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
    {
        super(mesh_name);
        flag2 = false;
        spread = 0;
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
            engineSTimer = -(int)com.maddox.il2.objects.vehicles.stationary.FACGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.FACGeneric.Rnd(30F, 30F));
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
        boolean flag1 = false;
        for(java.util.Map.Entry entry = com.maddox.il2.engine.Engine.name2Actor().nextEntry(((java.util.Map.Entry) (null))); entry != null; entry = com.maddox.il2.engine.Engine.name2Actor().nextEntry(entry))
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getValue();
            if(actor == com.maddox.il2.ai.World.getPlayerAircraft() && actor.pos.getAbsPoint().distance(point3d) < 10000D)
                flag = true;
        }

        for(java.util.Map.Entry entry1 = com.maddox.il2.engine.Engine.name2Actor().nextEntry(((java.util.Map.Entry) (null))); entry1 != null; entry1 = com.maddox.il2.engine.Engine.name2Actor().nextEntry(entry1))
        {
            com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)entry1.getValue();
            if(com.maddox.il2.engine.Actor.isAlive(actor1) && ((actor1 instanceof com.maddox.il2.objects.vehicles.tanks.TankGeneric) || (actor1 instanceof com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric) || (actor1 instanceof com.maddox.il2.objects.vehicles.cars.CarGeneric)) && actor1.getArmy() != myArmy && actor1.pos.getAbsPoint().distance(point3d) < 500D)
            {
                pos.getAbs(point3d);
                double d = (com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + ((com.maddox.JGP.Tuple3d) (point3d)).x) / 10000D;
                double d1 = (com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + ((com.maddox.JGP.Tuple3d) (point3d)).y) / 10000D;
                double d2 = com.maddox.il2.game.Main3D.cur3D().land2D.mapSizeX() / 1000D;
                double d3 = actor1.pos.getAbsPoint().distance(point3d);
                double d4 = (com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + actor1.pos.getAbsPoint().x) / 10000D;
                double d5 = (com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + actor1.pos.getAbsPoint().y) / 10000D;
                double d6 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + actor1.pos.getAbsPoint().z;
                char c = (char)(int)(65D + java.lang.Math.floor((d / 676D - java.lang.Math.floor(d / 676D)) * 26D));
                char c1 = (char)(int)(65D + java.lang.Math.floor((d / 26D - java.lang.Math.floor(d / 26D)) * 26D));
                new String();
                java.lang.String s;
                if(d2 > 260D)
                    s = "" + c + c1;
                else
                    s = "" + c1;
                double d7 = d4 - d;
                double d8 = d5 - d1;
                float f = 57.32484F * (float)java.lang.Math.atan2(d8, d7);
                int i = (int)f;
                i = (i + 180) % 360;
                new String();
                java.lang.String s1 = "West";
                if((double)i <= 315D && (double)i >= 225D)
                    s1 = "North";
                if((double)i <= 135D && (double)i >= 45D)
                    s1 = "South";
                if((double)i <= 44D && (double)i >= 316D)
                    s1 = "West";
                if((double)i <= 224D && (double)i >= 136D)
                    s1 = "East";
                new String();
                java.lang.String s2 = "units";
                if(actor1 instanceof com.maddox.il2.objects.vehicles.tanks.TankGeneric)
                    s2 = "armor";
                if(actor1 instanceof com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric)
                    s2 = "guns";
                if(actor1 instanceof com.maddox.il2.objects.vehicles.cars.CarGeneric)
                    s2 = "vehicles";
                new String();
                int j = (int)(java.lang.Math.ceil(d3 / 10D) * 10D);
                double d9 = java.lang.Math.ceil(d1);
                float f1 = com.maddox.il2.ai.World.getTimeofDay();
                boolean flag3 = false;
                if(f1 >= 0.0F && f1 <= 5F || f1 >= 21F && f1 <= 24F)
                    flag3 = true;
                if(flag)
                {
                    if(!flag2)
                    {
                        if(!flag3)
                        {
                            com.maddox.il2.game.HUD.logCenter("                                                                             Popping Smoke!");
                            com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (this)), ((com.maddox.il2.engine.Hook) (null)), new Loc(0.0D, 0.0D, 0.0D, 0.0F, 90F, 0.0F), 1.0F, "Effects/Smokes/Redsmoke.eff", -1F);
                        } else
                        {
                            com.maddox.il2.game.HUD.logCenter("                                                                             Popping Flare!");
                            com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (this)), ((com.maddox.il2.engine.Hook) (null)), new Loc(0.0D, 0.0D, 0.0D, 0.0F, 90F, 0.0F), 1.0F, "3DO/Effects/Fireworks/FlareWhiteWide.eff", -1F);
                        }
                        flag2 = true;
                    } else
                    {
                        com.maddox.il2.game.HUD.logCenter("                                                                             Enemy " + s2 + " " + j + " yards " + s1 + " of my mark");
                        if(!flag1)
                        {
                            java.util.Random random = new Random();
                            int k = random.nextInt(40);
                            spread = k - 20;
                            int l = (int)((d4 - d) * 10000D);
                            int i1 = (int)((d5 - d1) * 10000D);
                            com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (this)), ((com.maddox.il2.engine.Hook) (null)), new Loc(l + spread, i1 + spread, d6, 0.0F, 90F, 0.0F), 1.0F, "Effects/Smokes/SmokeWhite.eff", 60F);
                            flag1 = true;
                        }
                    }
                } else
                {
                    com.maddox.il2.game.HUD.logCenter("                                                                             Request CAS at map grid " + s + "-" + d9);
                }
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
    private boolean flag2;
    private int spread;





}
