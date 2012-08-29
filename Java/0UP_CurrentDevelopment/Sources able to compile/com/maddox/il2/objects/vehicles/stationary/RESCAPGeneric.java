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
import com.maddox.il2.objects.air.BELL_47;
import com.maddox.il2.objects.air.H19D;
import com.maddox.il2.objects.air.HRS3;
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

public abstract class RESCAPGeneric extends com.maddox.il2.engine.ActorHMesh
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
                        engineSTimer = (int)com.maddox.il2.objects.vehicles.stationary.RESCAPGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.RESCAPGeneric.Rnd(15F, 15F));
                        if(!danger())
                            engineSTimer = -engineSTimer;
                    }
                } else
                if(++engineSTimer >= 0)
                {
                    engineSTimer = -(int)com.maddox.il2.objects.vehicles.stationary.RESCAPGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.RESCAPGeneric.Rnd(15F, 15F));
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
            com.maddox.il2.objects.vehicles.stationary.RESCAPGeneric rescapgeneric = null;
            try
            {
                com.maddox.il2.objects.vehicles.stationary.RESCAPGeneric.constr_arg2 = actorspawnarg;
                rescapgeneric = (com.maddox.il2.objects.vehicles.stationary.RESCAPGeneric)cls.newInstance();
                com.maddox.il2.objects.vehicles.stationary.RESCAPGeneric.constr_arg2 = null;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.vehicles.stationary.RESCAPGeneric.constr_arg2 = null;
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("SPAWN: Can't create RESCAPGeneric object [class:" + cls.getName() + "]");
                return null;
            }
            return rescapgeneric;
        }

        public java.lang.Class cls;

        public SPAWN(java.lang.Class class1)
        {
            com.maddox.rts.Property.set(class1, "iconName", "icons/unknown.mat");
            com.maddox.rts.Property.set(class1, "meshName", com.maddox.il2.objects.vehicles.stationary.RESCAPGeneric.mesh_name);
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

    protected RESCAPGeneric()
    {
        this(constr_arg2);
    }

    private RESCAPGeneric(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
    {
        super(mesh_name);
        pickup = false;
        popped = false;
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
            engineSTimer = -(int)com.maddox.il2.objects.vehicles.stationary.RESCAPGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.RESCAPGeneric.Rnd(30F, 30F));
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
        for(java.util.Map.Entry entry = com.maddox.il2.engine.Engine.name2Actor().nextEntry(null); entry != null; entry = com.maddox.il2.engine.Engine.name2Actor().nextEntry(entry))
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getValue();
            pos.getAbs(point3d);
            if((actor instanceof com.maddox.il2.objects.air.HRS3) || (actor instanceof com.maddox.il2.objects.air.H19D) || (actor instanceof com.maddox.il2.objects.air.BELL_47))
            {
                if(actor.pos.getAbsPoint().distance(point3d) <= 1000D && !popped)
                {
                    com.maddox.il2.engine.Eff3DActor.New(this, null, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 90F, 0.0F), 1.0F, s, -1F);
                    popped = true;
                }
                if(actor.pos.getAbsPoint().distance(point3d) <= 300D)
                {
                    com.maddox.il2.game.HUD.logCenter("                                                                             We got him! Heading home!");
                    pickup = true;
                    destroy();
                }
            }
            if(!pickup)
            {
                boolean flag = false;
                com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
                if(((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().distance(point3d) < 3000D)
                    flag = true;
                pos.getAbs(point3d);
                double d = (com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + ((com.maddox.JGP.Tuple3d) (point3d)).x) / 10000D;
                double d1 = (com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + ((com.maddox.JGP.Tuple3d) (point3d)).y) / 10000D;
                double d2 = com.maddox.il2.game.Main3D.cur3D().land2D.mapSizeX() / 1000D;
                double d3 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().x / 10000D;
                double d4 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().y / 10000D;
                char c = (char)(int)(65D + java.lang.Math.floor((d / 676D - java.lang.Math.floor(d / 676D)) * 26D));
                char c1 = (char)(int)(65D + java.lang.Math.floor((d / 26D - java.lang.Math.floor(d / 26D)) * 26D));
                new String();
                java.lang.String s1;
                if(d2 > 260D)
                    s1 = "" + c + c1;
                else
                    s1 = "" + c1;
                double d5 = d3 - d;
                double d6 = d4 - d1;
                float f1 = 57.32484F * (float)java.lang.Math.atan2(d6, d5);
                int i = (int)f1;
                i = (i + 180) % 360;
                new String();
                java.lang.String s2 = "east";
                if((double)i <= 315D && (double)i >= 225D)
                    s2 = "south";
                if((double)i <= 135D && (double)i >= 45D)
                    s2 = "north";
                if((double)i <= 44D && (double)i >= 316D)
                    s2 = "east";
                if((double)i <= 224D && (double)i >= 136D)
                    s2 = "west";
                new String();
                double d7 = java.lang.Math.ceil(d1);
                if(flag)
                    com.maddox.il2.game.HUD.logCenter("                                                                             Got you in sight! I'm to the " + s2 + "!");
                else
                    com.maddox.il2.game.HUD.logCenter("                                                                             RESCAP required at map grid " + s1 + "-" + d7);
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

    private static java.lang.String mesh_name = "3do/Buildings/addobjects/Human_02/live.sim";
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
