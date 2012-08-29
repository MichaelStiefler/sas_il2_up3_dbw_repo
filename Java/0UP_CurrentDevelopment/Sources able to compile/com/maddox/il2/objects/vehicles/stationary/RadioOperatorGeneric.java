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
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeFighter;
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

public abstract class RadioOperatorGeneric extends com.maddox.il2.engine.ActorHMesh
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
                        engineSTimer = (int)com.maddox.il2.objects.vehicles.stationary.RadioOperatorGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.RadioOperatorGeneric.Rnd(5F, 5F));
                        if(!danger())
                            engineSTimer = -engineSTimer;
                    }
                } else
                if(++engineSTimer >= 0)
                {
                    engineSTimer = -(int)com.maddox.il2.objects.vehicles.stationary.RadioOperatorGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.RadioOperatorGeneric.Rnd(5F, 5F));
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
            com.maddox.il2.objects.vehicles.stationary.RadioOperatorGeneric radiooperatorgeneric = null;
            try
            {
                com.maddox.il2.objects.vehicles.stationary.RadioOperatorGeneric.constr_arg2 = actorspawnarg;
                radiooperatorgeneric = (com.maddox.il2.objects.vehicles.stationary.RadioOperatorGeneric)cls.newInstance();
                com.maddox.il2.objects.vehicles.stationary.RadioOperatorGeneric.constr_arg2 = null;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.vehicles.stationary.RadioOperatorGeneric.constr_arg2 = null;
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("SPAWN: Can't create RadioOperatorGeneric object [class:" + cls.getName() + "]");
                return null;
            }
            return radiooperatorgeneric;
        }

        public java.lang.Class cls;

        public SPAWN(java.lang.Class class1)
        {
            com.maddox.rts.Property.set(class1, "iconName", "icons/unknown.mat");
            com.maddox.rts.Property.set(class1, "meshName", com.maddox.il2.objects.vehicles.stationary.RadioOperatorGeneric.mesh_name);
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

    protected RadioOperatorGeneric()
    {
        this(constr_arg2);
    }

    private RadioOperatorGeneric(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
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
            engineSTimer = -(int)com.maddox.il2.objects.vehicles.stationary.RadioOperatorGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.RadioOperatorGeneric.Rnd(5F, 5F));
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
            if((actor instanceof com.maddox.il2.objects.air.TypeFighter) && actor.getArmy() == myArmy && actor.getSpeed(vector3d) > 20D)
            {
                if(actor == com.maddox.il2.ai.World.getPlayerAircraft())
                    flag = true;
                pos.getAbs(point3d);
                double d = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + actor.pos.getAbsPoint().x;
                double d1 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + actor.pos.getAbsPoint().y;
                double d2 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + actor.pos.getAbsPoint().z;
                int i = (int)(-((double)actor.pos.getAbsOrient().getYaw() - 90D));
                if(i < 0)
                    i = 360 + i;
                boolean flag1 = false;
                com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.War.GetNearestEnemyAircraft(actor, 650F, 9);
                if((aircraft instanceof com.maddox.il2.objects.air.TypeFighter) && aircraft.getSpeed(vector3d) > 20D)
                {
                    double d3 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() + ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().x;
                    double d4 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().y;
                    double d5 = com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() + ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbsPoint().z;
                    new String();
                    double d6 = (int)java.lang.Math.ceil(d2 - d5);
                    new String();
                    double d7 = d3 - d;
                    double d8 = d4 - d1;
                    float f = 57.32484F * (float)java.lang.Math.atan2(d8, -d7);
                    int j = (int)(java.lang.Math.floor((int)f) - 90D);
                    if(j < 0)
                        j = 360 + j;
                    int k = j - i;
                    if(k < 0)
                        k = 360 + k;
                    boolean flag2 = false;
                    if(k >= 90 && k <= 270)
                        flag2 = true;
                    java.lang.String s = "left";
                    if(k <= 180 && k >= 90)
                        s = "right";
                    double d9 = d - d3;
                    double d10 = d1 - d4;
                    java.util.Random random = new Random();
                    int l = random.nextInt(100);
                    int i1 = (int)java.lang.Math.ceil(java.lang.Math.sqrt(d10 * d10 + d9 * d9));
                    if((double)i1 <= 650D && java.lang.Math.sqrt(d6 * d6) <= 650D && flag2 && flag && l <= 50)
                        com.maddox.il2.game.HUD.logCenter("                                          Nightfighter! Break " + s + "!");
                }
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
