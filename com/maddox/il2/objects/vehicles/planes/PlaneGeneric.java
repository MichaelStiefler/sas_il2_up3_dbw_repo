// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PlaneGeneric.java

package com.maddox.il2.objects.vehicles.planes;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Line2f;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point2f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.TableFunctions;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Obstacle;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.game.I18N;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.PaintScheme;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.vehicles.tanks.TankGeneric;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.util.TableFunction2;
import java.io.IOException;
import java.io.PrintStream;

public abstract class PlaneGeneric extends com.maddox.il2.engine.ActorHMesh
    implements com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener, com.maddox.il2.ai.ground.Prey, com.maddox.il2.ai.ground.Obstacle, com.maddox.il2.objects.ActorAlign
{
    public static class SPAWN
        implements com.maddox.il2.engine.ActorSpawn
    {

        private static float getF(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.String s1, float f, float f1)
        {
            float f2 = sectfile.get(s, s1, -9865.345F);
            if(f2 == -9865.345F || f2 < f || f2 > f1)
            {
                if(f2 == -9865.345F)
                    java.lang.System.out.println("Plane: Parameter [" + s + "]:<" + s1 + "> " + "not found");
                else
                    java.lang.System.out.println("Plane: Value of [" + s + "]:<" + s1 + "> (" + f2 + ")" + " is out of range (" + f + ";" + f1 + ")");
                throw new RuntimeException("Can't set property");
            } else
            {
                return f2;
            }
        }

        private static java.lang.String getS(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.String s1)
        {
            java.lang.String s2 = sectfile.get(s, s1);
            if(s2 == null || s2.length() <= 0)
            {
                java.lang.System.out.print("Plane: Parameter [" + s + "]:<" + s1 + "> ");
                java.lang.System.out.println(s2 != null ? "is empty" : "not found");
                throw new RuntimeException("Can't set property");
            } else
            {
                return s2;
            }
        }

        private static java.lang.String getS(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.String s1, java.lang.String s2)
        {
            java.lang.String s3 = sectfile.get(s, s1);
            if(s3 == null || s3.length() <= 0)
                return s2;
            else
                return s3;
        }

        private static com.maddox.il2.objects.vehicles.planes.PlaneProperties LoadPlaneProperties(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.Class class1)
        {
            com.maddox.il2.objects.vehicles.planes.PlaneProperties planeproperties = new PlaneProperties();
            planeproperties.fnShotPanzer = com.maddox.il2.ai.TableFunctions.GetFunc2("PlaneShotPanzer");
            planeproperties.fnExplodePanzer = com.maddox.il2.ai.TableFunctions.GetFunc2("PlaneExplodePanzer");
            java.lang.String s1 = com.maddox.il2.objects.vehicles.planes.SPAWN.getS(sectfile, s, "Class");
            planeproperties.clazz = null;
            try
            {
                planeproperties.clazz = com.maddox.rts.ObjIO.classForName(s1);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println("*** Plane: class '" + s1 + "' not found");
                return null;
            }
            com.maddox.rts.Property.set(class1, "airClass", planeproperties.clazz);
            java.lang.String s2 = com.maddox.rts.Property.stringValue(planeproperties.clazz, "keyName", null);
            if(s2 == null)
                com.maddox.rts.Property.set(class1, "i18nName", "Plane");
            else
                com.maddox.rts.Property.set(class1, "i18nName", com.maddox.il2.game.I18N.plane(s2));
            planeproperties.explodeName = com.maddox.il2.objects.vehicles.planes.SPAWN.getS(sectfile, s, "Explode", "Aircraft");
            planeproperties.PANZER = com.maddox.il2.objects.vehicles.planes.SPAWN.getF(sectfile, s, "PanzerBodyFront", 0.0001F, 50F);
            planeproperties.HITBY_MASK = planeproperties.PANZER <= 0.015F ? -1 : -2;
            com.maddox.rts.Property.set(class1, "iconName", "icons/" + com.maddox.il2.objects.vehicles.planes.SPAWN.getS(sectfile, s, "Icon") + ".mat");
            return planeproperties;
        }

        public com.maddox.il2.engine.Actor actorSpawn(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            com.maddox.il2.objects.vehicles.planes.PlaneGeneric planegeneric = null;
            try
            {
                com.maddox.il2.objects.vehicles.planes.PlaneGeneric.constr_arg1 = proper;
                com.maddox.il2.objects.vehicles.planes.PlaneGeneric.constr_arg2 = actorspawnarg;
                planegeneric = (com.maddox.il2.objects.vehicles.planes.PlaneGeneric)cls.newInstance();
                com.maddox.il2.objects.vehicles.planes.PlaneGeneric.constr_arg1 = null;
                com.maddox.il2.objects.vehicles.planes.PlaneGeneric.constr_arg2 = null;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.vehicles.planes.PlaneGeneric.constr_arg1 = null;
                com.maddox.il2.objects.vehicles.planes.PlaneGeneric.constr_arg2 = null;
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("SPAWN: Can't create stationary Plane object [class:" + cls.getName() + "]");
                return null;
            }
            return planegeneric;
        }

        public java.lang.Class cls;
        public com.maddox.il2.objects.vehicles.planes.PlaneProperties proper;

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
                proper = com.maddox.il2.objects.vehicles.planes.SPAWN.LoadPlaneProperties(com.maddox.il2.objects.Statics.getTechnicsFile(), s1, class1);
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
            if(netmsginput.isGuaranted())
            {
                switch(netmsginput.readByte())
                {
                case 73: // 'I'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(netmsginput, 0);
                        post(netmsgguaranted);
                    }
                    short word0 = netmsginput.readShort();
                    if(word0 > 0 && dying != 1)
                        Die(null, (short)1, false);
                    return true;

                case 68: // 'D'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted(netmsginput, 1);
                        post(netmsgguaranted1);
                    }
                    if(dying != 1)
                    {
                        com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                        com.maddox.il2.engine.Actor actor = netobj != null ? ((com.maddox.il2.engine.ActorNet)netobj).actor() : null;
                        Die(actor, (short)1, true);
                    }
                    return true;
                }
                return false;
            }
            switch(netmsginput.readByte())
            {
            case 68: // 'D'
                out.unLockAndSet(netmsginput, 1);
                out.setIncludeTime(false);
                postRealTo(com.maddox.rts.Message.currentRealTime(), masterChannel(), out);
                return true;
            }
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
            if(netmsginput.isGuaranted())
                return true;
            if(netmsginput.readByte() != 68)
                return false;
            if(dying == 1)
            {
                return true;
            } else
            {
                com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                com.maddox.il2.engine.Actor actor = netobj != null ? ((com.maddox.il2.engine.ActorNet)netobj).actor() : null;
                Die(actor, (short)0, true);
                return true;
            }
        }

        public Master(com.maddox.il2.engine.Actor actor)
        {
            super(actor);
        }
    }

    public static class PlaneProperties
    {

        public java.lang.Class clazz;
        public float height;
        public float pitch;
        public com.maddox.util.TableFunction2 fnShotPanzer;
        public com.maddox.util.TableFunction2 fnExplodePanzer;
        public float PANZER;
        public int HITBY_MASK;
        public java.lang.String explodeName;

        public PlaneProperties()
        {
            clazz = null;
            height = 0.0F;
            pitch = 0.0F;
            fnShotPanzer = null;
            fnExplodePanzer = null;
            PANZER = 0.001F;
            HITBY_MASK = -2;
            explodeName = null;
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
        return l >= 1L ? l : 1L;
    }

    public boolean isStaticPos()
    {
        return true;
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        shot.bodyMaterial = 2;
        if(dying != 0)
            return;
        if(shot.power <= 0.0F)
            return;
        if(isNetMirror() && shot.isMirage())
            return;
        if(shot.powerType == 1)
            if(RndB(0.15F))
            {
                return;
            } else
            {
                Die(shot.initiator, (short)0, true);
                return;
            }
        float f = prop.PANZER * com.maddox.il2.objects.vehicles.planes.PlaneGeneric.Rnd(0.93F, 1.07F);
        float f1 = prop.fnShotPanzer.Value(shot.power, f);
        if(f1 < 1000F && (f1 <= 1.0F || RndB(1.0F / f1)))
            Die(shot.initiator, (short)0, true);
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        if(dying != 0)
            return;
        if(isNetMirror() && explosion.isMirage())
            return;
        if(explosion.power <= 0.0F)
            return;
        com.maddox.il2.ai.Explosion _tmp = explosion;
        if(explosion.powerType == 1)
        {
            if(com.maddox.il2.objects.vehicles.tanks.TankGeneric.splintersKill(explosion, prop.fnShotPanzer, com.maddox.il2.objects.vehicles.planes.PlaneGeneric.Rnd(0.0F, 1.0F), com.maddox.il2.objects.vehicles.planes.PlaneGeneric.Rnd(0.0F, 1.0F), this, 0.6F, 0.0F, prop.PANZER, prop.PANZER, prop.PANZER, prop.PANZER, prop.PANZER, prop.PANZER))
                Die(explosion.initiator, (short)0, true);
            return;
        }
        com.maddox.il2.ai.Explosion _tmp1 = explosion;
        if(explosion.powerType == 2 && explosion.chunkName != null)
        {
            Die(explosion.initiator, (short)0, true);
            return;
        }
        float f;
        if(explosion.chunkName != null)
            f = 0.5F * explosion.power;
        else
            f = explosion.receivedTNTpower(this);
        f *= com.maddox.il2.objects.vehicles.planes.PlaneGeneric.Rnd(0.95F, 1.05F);
        float f1 = prop.PANZER;
        float f2 = prop.fnExplodePanzer.Value(f, f1);
        if(f2 < 1000F && (f2 <= 1.0F || RndB(1.0F / f2)))
            Die(explosion.initiator, (short)0, true);
    }

    private void ShowExplode(float f)
    {
        if(f > 0.0F)
            f = com.maddox.il2.objects.vehicles.planes.PlaneGeneric.Rnd(f, f * 1.6F);
        com.maddox.il2.objects.effects.Explosions.runByName(prop.explodeName, this, "", "", f);
    }

    private void Die(com.maddox.il2.engine.Actor actor, short word0, boolean flag)
    {
        if(dying != 0)
            return;
        if(word0 <= 0)
        {
            if(isNetMirror())
            {
                send_DeathRequest(actor);
                return;
            }
            word0 = 1;
        }
        dying = 1;
        com.maddox.il2.ai.World.onActorDied(this, actor);
        activateMesh(false);
        Align(false, true);
        if(flag)
            ShowExplode(17F);
        if(flag)
            send_DeathCommand(actor);
    }

    public void destroy()
    {
        if(isDestroyed())
        {
            return;
        } else
        {
            super.destroy();
            return;
        }
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    protected PlaneGeneric()
    {
        this(constr_arg1, constr_arg2);
    }

    private PlaneGeneric(com.maddox.il2.objects.vehicles.planes.PlaneProperties planeproperties, com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
    {
        prop = null;
        country = null;
        dying = 0;
        prop = planeproperties;
        actorspawnarg.setStationary(this);
        country = actorspawnarg.country;
        try
        {
            activateMesh(true);
        }
        catch(java.lang.RuntimeException runtimeexception)
        {
            super.destroy();
            throw runtimeexception;
        }
        collide(true);
        drawing(true);
        createNetObject(actorspawnarg.netChannel, actorspawnarg.netIdRemote);
        if(prop.height == 0.0F && prop.pitch == 0.0F)
        {
            pnti[0] = hierMesh().hookFind("_ClipLGear");
            pnti[1] = hierMesh().hookFind("_ClipRGear");
            pnti[2] = hierMesh().hookFind("_ClipCGear");
            java.lang.String s = com.maddox.rts.Property.stringValue(prop.clazz, "FlightModel", null);
            com.maddox.rts.SectFile sectfile = com.maddox.il2.fm.FlightModelMain.sectFile(s);
            if(pnti[0] >= 0 && pnti[1] >= 0 && pnti[2] >= 0 && sectfile.get("Gear", "FromIni", 0) == 0)
            {
                hierMesh().hookMatrix(pnti[2], M4);
                double d = M4.m03;
                double d1 = M4.m23;
                hierMesh().hookMatrix(pnti[0], M4);
                double d2 = M4.m03;
                double d3 = M4.m23;
                hierMesh().hookMatrix(pnti[1], M4);
                d2 = (d2 + M4.m03) * 0.5D;
                d3 = (d3 + M4.m23) * 0.5D;
                double d4 = d2 - d;
                double d5 = d3 - d1;
                prop.pitch = -com.maddox.JGP.Geom.RAD2DEG((float)java.lang.Math.atan2(d5, d4));
                if(d4 < 0.0D)
                    prop.pitch += 180F;
                com.maddox.JGP.Line2f line2f = new Line2f();
                line2f.set(new Point2f((float)d2, (float)d3), new Point2f((float)d, (float)d1));
                prop.height = line2f.distance(new Point2f(0.0F, 0.0F));
            } else
            {
                prop.height = sectfile.get("Gear", "H", -0.5F);
                prop.pitch = sectfile.get("Gear", "Pitch", -0.5F);
            }
        }
        Align(true, false);
    }

    public void activateMesh(boolean flag)
    {
        if(flag)
        {
            com.maddox.il2.ai.Regiment regiment = com.maddox.il2.ai.Regiment.findFirst(country, getArmy());
            java.lang.String s = com.maddox.il2.objects.air.Aircraft.getPropertyMesh(prop.clazz, regiment.country());
            setMesh(s);
            com.maddox.il2.objects.air.Aircraft.prepareMeshCamouflage(s, hierMesh());
            com.maddox.il2.objects.air.PaintScheme paintscheme = com.maddox.il2.objects.air.Aircraft.getPropertyPaintScheme(prop.clazz, regiment.country());
            paintscheme.prepareNum(prop.clazz, hierMesh(), regiment, (int)(java.lang.Math.random() * 3D), (int)(java.lang.Math.random() * 3D), (int)(java.lang.Math.random() * 98D + 1.0D));
        }
        java.lang.String as[] = com.maddox.il2.objects.air.Aircraft.partNames();
        com.maddox.il2.engine.HierMesh hiermesh = hierMesh();
        for(int i = 1; i < 10; i++)
        {
            if(hiermesh.chunkFindCheck("Pilot" + i + "_D0") < 0)
                break;
            hiermesh.chunkVisible("Pilot" + i + "_D0", false);
            if(hiermesh.chunkFindCheck("Head" + i + "_D0") >= 0)
                hiermesh.chunkVisible("Head" + i + "_D0", false);
            if(hiermesh.chunkFindCheck("HMask" + i + "_D0") >= 0)
                hiermesh.chunkVisible("HMask" + i + "_D0", false);
            if(hiermesh.chunkFindCheck("Pilot" + i + "a_D0") >= 0)
                hiermesh.chunkVisible("Pilot" + i + "a_D0", false);
            if(hiermesh.chunkFindCheck("Head" + i + "a_D0") >= 0)
                hiermesh.chunkVisible("Head" + i + "a_D0", false);
            if(hiermesh.chunkFindCheck("Pilot" + i + "_FAK") >= 0)
                hiermesh.chunkVisible("Pilot" + i + "_FAK", false);
            if(hiermesh.chunkFindCheck("Pilot" + i + "_FAL") >= 0)
                hiermesh.chunkVisible("Pilot" + i + "_FAL", false);
            if(hiermesh.chunkFindCheck("Head" + i + "_FAK") >= 0)
                hiermesh.chunkVisible("Head" + i + "_FAK", false);
            if(hiermesh.chunkFindCheck("Head" + i + "_FAL") >= 0)
                hiermesh.chunkVisible("Head" + i + "_FAL", false);
        }

        if(!flag)
        {
            for(int j = 0; j < as.length; j++)
                if(hiermesh.chunkFindCheck(as[j] + "_D0") >= 0)
                {
                    hiermesh.chunkVisible(as[j] + "_D0", false);
                    for(int k = 3; k >= 0; k--)
                    {
                        if(hiermesh.chunkFindCheck(as[j] + "_D" + k) < 0)
                            continue;
                        hiermesh.chunkVisible(as[j] + "_D" + k, true);
                        break;
                    }

                }

        }
        com.maddox.il2.objects.air.Aircraft.forceGear(prop.clazz, hierMesh(), 1.0F);
        if(!flag)
            mesh().makeAllMaterialsDarker(0.32F, 0.45F);
    }

    private void Align(boolean flag, boolean flag1)
    {
        pos.getAbs(p, o);
        p.z = com.maddox.il2.engine.Engine.land().HQ(p.x, p.y) + (double)prop.height;
        if(!flag)
            o.increment(0.0F, -prop.pitch, 0.0F);
        com.maddox.il2.engine.Engine.land().N(p.x, p.y, n);
        o.orient(n);
        o.increment(0.0F, prop.pitch, 0.0F);
        if(flag1)
        {
            long l = (long)((p.x % 2.2999999999999998D) * 221D + (p.y % 3.3999999999999999D) * 211D * 211D);
            com.maddox.il2.ai.RangeRandom rangerandom = new RangeRandom(l);
            p.z -= rangerandom.nextFloat(0.1F, 0.4F);
            float f = rangerandom.nextFloat(-2F, 2.0F);
            float f1 = (rangerandom.nextBoolean() ? 1.0F : -1F) * rangerandom.nextFloat(7F, 18F);
            o.increment(f, 0.0F, f1);
        }
        pos.setAbs(p, o);
    }

    public void align()
    {
        Align(false, false);
    }

    public int HitbyMask()
    {
        return prop.HITBY_MASK;
    }

    public int chooseBulletType(com.maddox.il2.engine.BulletProperties abulletproperties[])
    {
        if(dying != 0)
            return -1;
        if(abulletproperties.length == 1)
            return 0;
        if(abulletproperties.length <= 0)
            return -1;
        if(abulletproperties[0].power <= 0.0F)
            return 0;
        if(abulletproperties[1].power <= 0.0F)
            return 1;
        if(abulletproperties[0].cumulativePower > 0.0F)
            return 0;
        if(abulletproperties[1].cumulativePower > 0.0F)
            return 1;
        if(abulletproperties[0].powerType == 1)
            return 0;
        if(abulletproperties[1].powerType == 1)
            return 1;
        return abulletproperties[0].powerType != 2 ? 0 : 1;
    }

    public int chooseShotpoint(com.maddox.il2.engine.BulletProperties bulletproperties)
    {
        return dying == 0 ? 0 : -1;
    }

    public boolean getShotpointOffset(int i, com.maddox.JGP.Point3d point3d)
    {
        if(dying != 0)
            return false;
        if(i != 0)
            return false;
        if(point3d != null)
            point3d.set(0.0D, 0.0D, 0.0D);
        return true;
    }

    public boolean unmovableInFuture()
    {
        return true;
    }

    public void collisionDeath()
    {
        if(isNet())
        {
            return;
        } else
        {
            ShowExplode(-1F);
            destroy();
            return;
        }
    }

    public float futurePosition(float f, com.maddox.JGP.Point3d point3d)
    {
        pos.getAbs(point3d);
        return f > 0.0F ? f : 0.0F;
    }

    private void send_DeathCommand(com.maddox.il2.engine.Actor actor)
    {
        if(!isNetMaster())
            return;
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        try
        {
            netmsgguaranted.writeByte(68);
            netmsgguaranted.writeNetObj(actor != null ? ((com.maddox.rts.NetObj) (actor.net)) : null);
            net.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void send_DeathRequest(com.maddox.il2.engine.Actor actor)
    {
        if(!isNetMirror())
            return;
        if(net.masterChannel() instanceof com.maddox.rts.NetChannelInStream)
            return;
        try
        {
            com.maddox.rts.NetMsgFiltered netmsgfiltered = new NetMsgFiltered();
            netmsgfiltered.writeByte(68);
            netmsgfiltered.writeNetObj(actor != null ? ((com.maddox.rts.NetObj) (actor.net)) : null);
            netmsgfiltered.setIncludeTime(false);
            net.postTo(com.maddox.rts.Time.current(), net.masterChannel(), netmsgfiltered);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
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
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        netmsgguaranted.writeByte(73);
        if(dying == 0)
            netmsgguaranted.writeShort(0);
        else
            netmsgguaranted.writeShort(1);
        net.postTo(netchannel, netmsgguaranted);
    }

    private com.maddox.il2.objects.vehicles.planes.PlaneProperties prop;
    public java.lang.String country;
    private int dying;
    static final int DYING_NONE = 0;
    static final int DYING_DEAD = 1;
    private static com.maddox.il2.objects.vehicles.planes.PlaneProperties constr_arg1 = null;
    private static com.maddox.il2.engine.ActorSpawnArg constr_arg2 = null;
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();
    private static com.maddox.JGP.Vector3f n = new Vector3f();
    private static com.maddox.JGP.Vector3d tmpv = new Vector3d();
    private static int pnti[] = new int[3];
    private static com.maddox.JGP.Matrix4d M4 = new Matrix4d();





}
