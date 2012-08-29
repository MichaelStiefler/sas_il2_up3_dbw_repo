// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SmokeGeneric.java

package com.maddox.il2.objects.vehicles.stationary;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3D;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.Main;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.Statics;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import java.io.IOException;
import java.io.PrintStream;

public abstract class SmokeGeneric extends com.maddox.il2.engine.ActorHMesh
    implements com.maddox.il2.objects.ActorAlign
{
    public static class SPAWN
        implements com.maddox.il2.engine.ActorSpawn
    {

        private static java.lang.String getS(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.String s1)
        {
            java.lang.String s2 = sectfile.get(s, s1);
            if(s2 == null || s2.length() <= 0)
            {
                java.lang.System.out.print("Smoke: Parameter [" + s + "]:<" + s1 + "> ");
                java.lang.System.out.println(s2 != null ? "is empty" : "not found");
                throw new RuntimeException("Can't set property");
            } else
            {
                return s2;
            }
        }

        private static com.maddox.il2.objects.vehicles.stationary.SmokeProperties LoadSmokeProperties(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.Class class1)
        {
            com.maddox.il2.objects.vehicles.stationary.SmokeProperties smokeproperties = new SmokeProperties();
            smokeproperties.meshName = com.maddox.il2.objects.vehicles.stationary.SPAWN.getS(sectfile, "Smokes", s + ":Mesh");
            smokeproperties.effectName = com.maddox.il2.objects.vehicles.stationary.SPAWN.getS(sectfile, "Smokes", s + ":Effect");
            com.maddox.rts.Property.set(class1, "iconName", "icons/unknown.mat");
            com.maddox.rts.Property.set(class1, "meshName", smokeproperties.meshName);
            return smokeproperties;
        }

        public com.maddox.il2.engine.Actor actorSpawn(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            com.maddox.il2.objects.vehicles.stationary.SmokeGeneric smokegeneric = null;
            try
            {
                com.maddox.il2.objects.vehicles.stationary.SmokeGeneric.constr_arg1 = proper;
                com.maddox.il2.objects.vehicles.stationary.SmokeGeneric.constr_arg2 = actorspawnarg;
                smokegeneric = (com.maddox.il2.objects.vehicles.stationary.SmokeGeneric)cls.newInstance();
                com.maddox.il2.objects.vehicles.stationary.SmokeGeneric.constr_arg1 = null;
                com.maddox.il2.objects.vehicles.stationary.SmokeGeneric.constr_arg2 = null;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.vehicles.stationary.SmokeGeneric.constr_arg1 = null;
                com.maddox.il2.objects.vehicles.stationary.SmokeGeneric.constr_arg2 = null;
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("SPAWN: Can't create SmokeGeneric object [class:" + cls.getName() + "]");
                return null;
            }
            return smokegeneric;
        }

        public java.lang.Class cls;
        public com.maddox.il2.objects.vehicles.stationary.SmokeProperties proper;

        public SPAWN(java.lang.Class class1)
        {
            try
            {
                java.lang.String s = class1.getName();
                int i = s.lastIndexOf('.');
                int j = s.lastIndexOf('$');
                if(i < j)
                    i = j;
                java.lang.String s1 = s.substring(i + 1);
                proper = com.maddox.il2.objects.vehicles.stationary.SPAWN.LoadSmokeProperties(com.maddox.il2.objects.Statics.getTechnicsFile(), s1, class1);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("Problem in spawn: " + class1.getName());
            }
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

    public static class SmokeProperties
    {

        public java.lang.String meshName;
        public java.lang.String effectName;

        public SmokeProperties()
        {
            meshName = null;
            effectName = null;
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

    protected SmokeGeneric()
    {
        this(constr_arg1, constr_arg2);
    }

    private SmokeGeneric(com.maddox.il2.objects.vehicles.stationary.SmokeProperties smokeproperties, com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
    {
        super(smokeproperties.meshName);
        prop = null;
        outCommand = new NetMsgFiltered();
        prop = smokeproperties;
        double d = 0.0D;
        if(actorspawnarg.timeLenExist)
        {
            d = actorspawnarg.point.z;
            actorspawnarg.point.z = actorspawnarg.timeLen;
        }
        actorspawnarg.setStationary(this);
        if(actorspawnarg.timeLenExist)
            actorspawnarg.point.z = d;
        setArmy(0);
        collide(false);
        drawing(true);
        createNetObject(actorspawnarg.netChannel, actorspawnarg.netIdRemote);
        heightAboveLandSurface = 0.0F;
        Align();
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        if(com.maddox.il2.game.Main.state() != null && com.maddox.il2.game.Main.state().id() == 18)
            com.maddox.il2.engine.Eff3D.initSetTypeTimer(true);
        com.maddox.il2.engine.Eff3DActor.New(this, null, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 90F, 0.0F), 1.0F, prop.effectName, -1F);
    }

    private void Align()
    {
        pos.getAbs(p);
        if(p.z < com.maddox.il2.engine.Engine.land().HQ(p.x, p.y) + (double)heightAboveLandSurface)
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

    private com.maddox.il2.objects.vehicles.stationary.SmokeProperties prop;
    private float heightAboveLandSurface;
    private static com.maddox.il2.objects.vehicles.stationary.SmokeProperties constr_arg1 = null;
    private static com.maddox.il2.engine.ActorSpawnArg constr_arg2 = null;
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();
    private static com.maddox.JGP.Vector3f n = new Vector3f();
    private static com.maddox.JGP.Vector3d tmpv = new Vector3d();
    private com.maddox.rts.NetMsgFiltered outCommand;



}
