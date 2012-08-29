// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RCGCIGeneric.java

package com.maddox.il2.objects.vehicles.stationary;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
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
import com.maddox.il2.objects.air.TypeBomber;
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

public abstract class RCGCIGeneric extends com.maddox.il2.engine.ActorHMesh
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
                        engineSTimer = (int)com.maddox.il2.objects.vehicles.stationary.RCGCIGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.RCGCIGeneric.Rnd(30F, 30F));
                        if(!danger())
                            engineSTimer = -engineSTimer;
                    }
                } else
                if(++engineSTimer >= 0)
                {
                    engineSTimer = -(int)com.maddox.il2.objects.vehicles.stationary.RCGCIGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.RCGCIGeneric.Rnd(30F, 30F));
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
            com.maddox.il2.objects.vehicles.stationary.RCGCIGeneric rcgcigeneric = null;
            try
            {
                com.maddox.il2.objects.vehicles.stationary.RCGCIGeneric.constr_arg2 = actorspawnarg;
                rcgcigeneric = (com.maddox.il2.objects.vehicles.stationary.RCGCIGeneric)cls.newInstance();
                com.maddox.il2.objects.vehicles.stationary.RCGCIGeneric.constr_arg2 = null;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.vehicles.stationary.RCGCIGeneric.constr_arg2 = null;
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("SPAWN: Can't create RCGCIGeneric object [class:" + cls.getName() + "]");
                return null;
            }
            return rcgcigeneric;
        }

        public java.lang.Class cls;

        public SPAWN(java.lang.Class class1)
        {
            com.maddox.rts.Property.set(class1, "iconName", "icons/unknown.mat");
            com.maddox.rts.Property.set(class1, "meshName", com.maddox.il2.objects.vehicles.stationary.RCGCIGeneric.mesh_name);
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

    protected RCGCIGeneric()
    {
        this(constr_arg2);
    }

    private RCGCIGeneric(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
    {
        super(mesh_name);
        mainforcefound = false;
        mainforcewrong = false;
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
            engineSTimer = -(int)com.maddox.il2.objects.vehicles.stationary.RCGCIGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.RCGCIGeneric.Rnd(60F, 60F));
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

    public boolean danger()
    {
        com.maddox.JGP.Point3d point3d = new Point3d();
        pos.getAbs(point3d);
        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.War.GetNearestEnemyAircraft(com.maddox.il2.ai.World.getPlayerAircraft(), 500000F, 9);
        for(int i = 0; !(aircraft instanceof com.maddox.il2.objects.air.TypeBomber) && i < 99;)
        {
            aircraft = com.maddox.il2.ai.War.GetNearestEnemyAircraft(com.maddox.il2.ai.World.getPlayerAircraft(), 500000F, 9);
            if(++i == 98)
                destroy();
        }

        int j = 0;
        if(mainforcefound)
            while(j < 99 && ((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.air.Aircraft)aircraft).FM).Skill != 2) 
            {
                aircraft = com.maddox.il2.ai.War.GetNearestEnemyAircraft(com.maddox.il2.ai.World.getPlayerAircraft(), 500000F, 9);
                if(++j == 98)
                    destroy();
            }
        for(int k = 0; k < 99 && ((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.air.Aircraft)aircraft).FM).Skill == 3 && !((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.air.Aircraft)aircraft).FM).hasBombs();)
        {
            aircraft = com.maddox.il2.ai.War.GetNearestEnemyAircraft(com.maddox.il2.ai.World.getPlayerAircraft(), 500000F, 9);
            if(++k == 98)
                destroy();
        }

        if(((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.air.Aircraft)aircraft).FM).Skill >= 2 && (aircraft instanceof com.maddox.il2.objects.air.TypeBomber) && ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().distance(point3d) < 200000D && aircraft.getSpeed(vector3d) > 20D && ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().z >= 150D)
        {
            pos.getAbs(point3d);
            double d = com.maddox.il2.game.Main3D.cur3D().land2D.mapSizeX() / 1000D;
            double d1 = (com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().x) / 10000D;
            double d2 = (com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().y) / 10000D;
            double d3 = (com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().x) / 1000D;
            double d4 = (com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().y) / 1000D;
            char c = (char)(int)(65D + java.lang.Math.floor((d1 / 676D - java.lang.Math.floor(d1 / 676D)) * 26D));
            char c1 = (char)(int)(65D + java.lang.Math.floor((d1 / 26D - java.lang.Math.floor(d1 / 26D)) * 26D));
            new String();
            java.lang.String s;
            if(d > 260D)
                s = "" + c + c1;
            else
                s = "" + c1;
            new String();
            double d5 = java.lang.Math.ceil(d2);
            int l = (int)(-((double)((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsOrient().getYaw() - 90D));
            if(l < 0)
                l = 360 + l;
            if(mainforcefound)
                com.maddox.il2.game.HUD.logCenter("                                          Confirmed Mainforce at " + s + "-" + d5 + ", heading " + l + "\260");
            else
            if(!mainforcefound)
                com.maddox.il2.game.HUD.logCenter("                                          Incoming Raid at " + s + "-" + d5 + ", heading " + l + "\260");
            if(!mainforcefound && !((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.air.Aircraft)aircraft).FM).hasBombs() && ((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.air.Aircraft)aircraft).FM).Skill == 2)
                mainforcefound = true;
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
    private boolean mainforcefound;
    private boolean mainforcewrong;




}
