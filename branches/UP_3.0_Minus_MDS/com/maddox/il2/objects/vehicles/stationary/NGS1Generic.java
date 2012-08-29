// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   NGS1Generic.java

package com.maddox.il2.objects.vehicles.stationary;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.MsgExplosion;
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
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.Selector;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.effects.Explosions;
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
import java.util.Random;

public abstract class NGS1Generic extends com.maddox.il2.engine.ActorHMesh
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
                        engineSTimer = (int)com.maddox.il2.objects.vehicles.stationary.NGS1Generic.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.NGS1Generic.Rnd(1.0F, 1.0F));
                        if(!danger())
                            engineSTimer = -engineSTimer;
                    }
                } else
                if(++engineSTimer >= 0)
                {
                    engineSTimer = -(int)com.maddox.il2.objects.vehicles.stationary.NGS1Generic.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.NGS1Generic.Rnd(1.0F, 1.0F));
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

        public Master(com.maddox.il2.engine.Actor actor1)
        {
            super(actor1);
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

        public Mirror(com.maddox.il2.engine.Actor actor1, com.maddox.rts.NetChannel netchannel, int i)
        {
            super(actor1, netchannel, i);
            out = new NetMsgFiltered();
        }
    }

    public static class SPAWN
        implements com.maddox.il2.engine.ActorSpawn
    {

        public com.maddox.il2.engine.Actor actorSpawn(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            com.maddox.il2.objects.vehicles.stationary.NGS1Generic ngs1generic = null;
            try
            {
                com.maddox.il2.objects.vehicles.stationary.NGS1Generic.constr_arg2 = actorspawnarg;
                ngs1generic = (com.maddox.il2.objects.vehicles.stationary.NGS1Generic)cls.newInstance();
                com.maddox.il2.objects.vehicles.stationary.NGS1Generic.constr_arg2 = null;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.vehicles.stationary.NGS1Generic.constr_arg2 = null;
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("SPAWN: Can't create NGS1Generic object [class:" + cls.getName() + "]");
                return null;
            }
            return ((com.maddox.il2.engine.Actor) (ngs1generic));
        }

        public java.lang.Class cls;

        public SPAWN(java.lang.Class class1)
        {
            com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconName", "icons/unknown.mat");
            com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", com.maddox.il2.objects.vehicles.stationary.NGS1Generic.mesh_name);
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

    protected NGS1Generic()
    {
        this(constr_arg2);
    }

    private NGS1Generic(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
    {
        super(mesh_name);
        counter = 0;
        hadtarget = false;
        actor = null;
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
            engineSTimer = -(int)com.maddox.il2.objects.vehicles.stationary.NGS1Generic.SecsToTicks(com.maddox.il2.objects.vehicles.stationary.NGS1Generic.Rnd(5F, 5F));
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
        if(!hadtarget)
        {
            actor = com.maddox.il2.game.Selector.getTarget();
            if(actor != null && !(actor instanceof com.maddox.il2.objects.air.Aircraft))
            {
                hadtarget = true;
                counter = 0;
            }
        }
        if(hadtarget)
        {
            if(counter >= 50)
            {
                java.lang.String s = "weapon.bomb_std";
                com.maddox.JGP.Point3d point3d = new Point3d();
                actor.pos.getAbs(point3d);
                java.util.Random random = new Random();
                int i = random.nextInt(400);
                int j = i - 200;
                point3d.x += j;
                i = random.nextInt(400);
                j = i - 200;
                point3d.y += j;
                float f = 25F;
                float f1 = 136F;
                i = random.nextInt(100);
                if(i > 50)
                {
                    f = 50F;
                    f1 = 210F;
                }
                com.maddox.il2.objects.effects.Explosions.generate(actor, point3d, f, 0, f1, !com.maddox.il2.game.Mission.isNet());
                com.maddox.il2.ai.MsgExplosion.send(actor, s, point3d, getOwner(), 0.0F, f, 0, f1);
            }
            if(counter >= 43 && counter < 45)
                com.maddox.il2.game.HUD.logCenter("                                                                             Splash!");
            if(counter > 21 && counter < 25)
                com.maddox.il2.game.HUD.logCenter("                                                                             Rounds Complete.");
            if(counter > 15 && counter < 25)
                com.maddox.il2.game.HUD.logCenter("                                                                             Firing.");
            if(counter > 5 && counter < 10)
                com.maddox.il2.game.HUD.logCenter("                                                                             Target Received.");
            counter++;
        }
        if(counter > 70)
        {
            hadtarget = false;
            counter = 0;
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
    private int counter;
    private boolean hadtarget;
    com.maddox.il2.engine.Actor actor;





}
