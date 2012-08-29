package com.maddox.il2.objects.vehicles.stationary;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
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

public abstract class OBOEGeneric extends com.maddox.il2.engine.ActorHMesh
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
                        engineSTimer = (int)com.maddox.il2.objects.vehicles.stationary.OBOEGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.OBOEGeneric.Rnd(1.0F, 1.0F));
                        if(!danger())
                            engineSTimer = -engineSTimer;
                    }
                } else
                if(++engineSTimer >= 0)
                {
                    engineSTimer = -(int)com.maddox.il2.objects.vehicles.stationary.OBOEGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.OBOEGeneric.Rnd(1.0F, 1.0F));
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
            com.maddox.il2.objects.vehicles.stationary.OBOEGeneric oboegeneric = null;
            try
            {
                com.maddox.il2.objects.vehicles.stationary.OBOEGeneric.constr_arg2 = actorspawnarg;
                oboegeneric = (com.maddox.il2.objects.vehicles.stationary.OBOEGeneric)cls.newInstance();
                com.maddox.il2.objects.vehicles.stationary.OBOEGeneric.constr_arg2 = null;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.vehicles.stationary.OBOEGeneric.constr_arg2 = null;
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("SPAWN: Can't create OBOEGeneric object [class:" + cls.getName() + "]");
                return null;
            }
            return oboegeneric;
        }

        public java.lang.Class cls;

        public SPAWN(java.lang.Class class1)
        {
            com.maddox.rts.Property.set(class1, "iconName", "icons/unknown.mat");
            com.maddox.rts.Property.set(class1, "meshName", com.maddox.il2.objects.vehicles.stationary.OBOEGeneric.mesh_name);
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

    protected OBOEGeneric()
    {
        this(constr_arg2);
    }

    private OBOEGeneric(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
    {
        super(mesh_name);
        flag2 = false;
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
            engineSTimer = -(int)com.maddox.il2.objects.vehicles.stationary.OBOEGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.OBOEGeneric.Rnd(5F, 5F));
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
        boolean flag = ((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.air.Aircraft)aircraft).FM).hasBombs();
        if(aircraft.getSpeed(vector3d) > 20D && ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().z >= 500D && flag)
        {
            pos.getAbs(point3d);
            double d = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + ((com.maddox.JGP.Tuple3d) (point3d)).x;
            double d1 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + ((com.maddox.JGP.Tuple3d) (point3d)).y;
            double d2 = com.maddox.il2.ai.World.land().HQ(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y);
            double d3 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().x;
            double d4 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().y;
            double d5 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().z;
            double d6 = aircraft.getSpeed(vector3d);
            double d7 = ((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.air.Aircraft)aircraft).FM).getAltitude();
            double d8 = d - d3;
            double d9 = d1 - d4;
            float f = 57.32484F * (float)java.lang.Math.atan2(d9, -d8);
            double d10 = java.lang.Math.floor((int)f) - 90D;
            if(d10 < 0.0D)
                d10 = 360D + d10;
            double d11 = (double)(-((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsOrient().getYaw()) + 90D;
            if(d11 < 0.0D)
                d11 += 360D;
            double d12 = d3 - d;
            double d13 = d4 - d1;
            double d14 = java.lang.Math.sqrt(d13 * d13 + d12 * d12);
            double d15 = d7 - com.maddox.il2.ai.World.land().HQ(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y);
            if(d15 < 0.0D)
                d15 = 0.0D;
            double d16 = d10 - d11;
            java.lang.String s = "<<<";
            if(d16 > 0.0D)
                s = ">>>";
            double d17 = d6 * java.lang.Math.sqrt(d15 * 0.20386999845504761D);
            int i = (int)((d14 - d17) / d6 / 60D);
            java.lang.String s1 = "|" + i + "|";
            if(i < 1)
            {
                i = (int)((d14 - d17) / d6);
                s1 = "[" + i + "]";
            }
            if(d16 <= 1.0D || d16 >= -1D)
                com.maddox.il2.game.HUD.logCenter("                                                                             " + s1);
            if(d16 >= 1.0D || d16 <= -1D)
                com.maddox.il2.game.HUD.logCenter("                                                                             " + s);
            if((d16 <= 1.0D || d16 >= -1D) && (d14 <= d17 || i == 0))
                com.maddox.il2.game.HUD.logCenter("                                                                             Drop!");
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
    private boolean flag2;





}
