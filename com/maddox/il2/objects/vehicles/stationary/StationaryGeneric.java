// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   StationaryGeneric.java

package com.maddox.il2.objects.vehicles.stationary;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.TableFunctions;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ZutiTargetsSupportMethods;
import com.maddox.il2.ai.ground.Obstacle;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.ai.ground.TgtTank;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.vehicles.tanks.TankGeneric;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.util.TableFunction2;
import java.io.IOException;
import java.io.PrintStream;

public abstract class StationaryGeneric extends com.maddox.il2.engine.ActorHMesh
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
                    java.lang.System.out.println("Stationary: Parameter [" + s + "]:<" + s1 + "> " + "not found");
                else
                    java.lang.System.out.println("Stationary: Value of [" + s + "]:<" + s1 + "> (" + f2 + ")" + " is out of range (" + f + ";" + f1 + ")");
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
                java.lang.System.out.print("Stationary: Parameter [" + s + "]:<" + s1 + "> ");
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

        private static com.maddox.il2.objects.vehicles.stationary.StationaryProperties LoadStationaryProperties(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.Class class1)
        {
            com.maddox.il2.objects.vehicles.stationary.StationaryProperties stationaryproperties = new StationaryProperties();
            java.lang.String s1 = com.maddox.il2.objects.vehicles.stationary.SPAWN.getS(sectfile, s, "PanzerType", null);
            if(s1 == null)
                s1 = "Tank";
            stationaryproperties.fnShotPanzer = com.maddox.il2.ai.TableFunctions.GetFunc2(s1 + "ShotPanzer");
            stationaryproperties.fnExplodePanzer = com.maddox.il2.ai.TableFunctions.GetFunc2(s1 + "ExplodePanzer");
            stationaryproperties.PANZER_TNT_TYPE = com.maddox.il2.objects.vehicles.stationary.SPAWN.getF(sectfile, s, "PanzerSubtype", 0.0F, 100F);
            stationaryproperties.meshSummer = com.maddox.il2.objects.vehicles.stationary.SPAWN.getS(sectfile, s, "MeshSummer");
            stationaryproperties.meshDesert = com.maddox.il2.objects.vehicles.stationary.SPAWN.getS(sectfile, s, "MeshDesert", stationaryproperties.meshSummer);
            stationaryproperties.meshWinter = com.maddox.il2.objects.vehicles.stationary.SPAWN.getS(sectfile, s, "MeshWinter", stationaryproperties.meshSummer);
            stationaryproperties.meshSummer1 = com.maddox.il2.objects.vehicles.stationary.SPAWN.getS(sectfile, s, "MeshSummerDamage", null);
            stationaryproperties.meshDesert1 = com.maddox.il2.objects.vehicles.stationary.SPAWN.getS(sectfile, s, "MeshDesertDamage", stationaryproperties.meshSummer1);
            stationaryproperties.meshWinter1 = com.maddox.il2.objects.vehicles.stationary.SPAWN.getS(sectfile, s, "MeshWinterDamage", stationaryproperties.meshSummer1);
            float f = (stationaryproperties.meshSummer1 != null ? 0 : 1) + (stationaryproperties.meshDesert1 != null ? 0 : 1) + (stationaryproperties.meshWinter1 != null ? 0 : 1);
            if(f != 0 && f != 3)
            {
                java.lang.System.out.println("Stationary: Uncomplete set of damage meshes for '" + s + "'");
                throw new RuntimeException("Can't register stationary object");
            }
            stationaryproperties.explodeName = com.maddox.il2.objects.vehicles.stationary.SPAWN.getS(sectfile, s, "Explode", "Stationary");
            stationaryproperties.PANZER_BODY_FRONT = com.maddox.il2.objects.vehicles.stationary.SPAWN.getF(sectfile, s, "PanzerBodyFront", 0.001F, 9.999F);
            if(sectfile.get(s, "PanzerBodyBack", -9865.345F) == -9865.345F)
            {
                stationaryproperties.PANZER_BODY_BACK = stationaryproperties.PANZER_BODY_FRONT;
                stationaryproperties.PANZER_BODY_SIDE = stationaryproperties.PANZER_BODY_FRONT;
                stationaryproperties.PANZER_BODY_TOP = stationaryproperties.PANZER_BODY_FRONT;
            } else
            {
                stationaryproperties.PANZER_BODY_BACK = com.maddox.il2.objects.vehicles.stationary.SPAWN.getF(sectfile, s, "PanzerBodyBack", 0.001F, 9.999F);
                stationaryproperties.PANZER_BODY_SIDE = com.maddox.il2.objects.vehicles.stationary.SPAWN.getF(sectfile, s, "PanzerBodySide", 0.001F, 9.999F);
                stationaryproperties.PANZER_BODY_TOP = com.maddox.il2.objects.vehicles.stationary.SPAWN.getF(sectfile, s, "PanzerBodyTop", 0.001F, 9.999F);
            }
            if(sectfile.get(s, "PanzerHead", -9865.345F) == -9865.345F)
                stationaryproperties.PANZER_HEAD = stationaryproperties.PANZER_BODY_FRONT;
            else
                stationaryproperties.PANZER_HEAD = com.maddox.il2.objects.vehicles.stationary.SPAWN.getF(sectfile, s, "PanzerHead", 0.001F, 9.999F);
            if(sectfile.get(s, "PanzerHeadTop", -9865.345F) == -9865.345F)
                stationaryproperties.PANZER_HEAD_TOP = stationaryproperties.PANZER_BODY_TOP;
            else
                stationaryproperties.PANZER_HEAD_TOP = com.maddox.il2.objects.vehicles.stationary.SPAWN.getF(sectfile, s, "PanzerHeadTop", 0.001F, 9.999F);
            f = java.lang.Math.min(java.lang.Math.min(stationaryproperties.PANZER_BODY_BACK, stationaryproperties.PANZER_BODY_TOP), java.lang.Math.min(stationaryproperties.PANZER_BODY_SIDE, stationaryproperties.PANZER_HEAD_TOP));
            stationaryproperties.HITBY_MASK = f <= 0.015F ? -1 : -2;
            com.maddox.rts.Property.set(class1, "iconName", "icons/" + com.maddox.il2.objects.vehicles.stationary.SPAWN.getS(sectfile, s, "Icon") + ".mat");
            com.maddox.rts.Property.set(class1, "meshName", stationaryproperties.meshSummer);
            return stationaryproperties;
        }

        public com.maddox.il2.engine.Actor actorSpawn(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            switch(com.maddox.il2.ai.World.cur().camouflage)
            {
            case 1: // '\001'
                proper.meshName = proper.meshWinter;
                proper.meshName1 = proper.meshWinter1;
                break;

            case 2: // '\002'
                proper.meshName = proper.meshDesert;
                proper.meshName1 = proper.meshDesert1;
                break;

            default:
                proper.meshName = proper.meshSummer;
                proper.meshName1 = proper.meshSummer1;
                break;
            }
            com.maddox.il2.objects.vehicles.stationary.StationaryGeneric stationarygeneric = null;
            try
            {
                com.maddox.il2.objects.vehicles.stationary.StationaryGeneric.constr_arg1 = proper;
                com.maddox.il2.objects.vehicles.stationary.StationaryGeneric.constr_arg2 = actorspawnarg;
                stationarygeneric = (com.maddox.il2.objects.vehicles.stationary.StationaryGeneric)cls.newInstance();
                com.maddox.il2.objects.vehicles.stationary.StationaryGeneric.constr_arg1 = null;
                com.maddox.il2.objects.vehicles.stationary.StationaryGeneric.constr_arg2 = null;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.vehicles.stationary.StationaryGeneric.constr_arg1 = null;
                com.maddox.il2.objects.vehicles.stationary.StationaryGeneric.constr_arg2 = null;
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("SPAWN: Can't create Stationary object [class:" + cls.getName() + "]");
                return null;
            }
            return stationarygeneric;
        }

        public java.lang.Class cls;
        public com.maddox.il2.objects.vehicles.stationary.StationaryProperties proper;

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
                proper = com.maddox.il2.objects.vehicles.stationary.SPAWN.LoadStationaryProperties(com.maddox.il2.objects.Statics.getTechnicsFile(), s1, class1);
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
                    {
                        Die(null, (short)1, false);
                        try
                        {
                            com.maddox.il2.ai.ZutiTargetsSupportMethods.staticActorDied(_fld0);
                        }
                        catch(java.lang.Exception exception)
                        {
                            java.lang.System.out.println("StationaryGeneric error, ID_01: " + exception.toString());
                        }
                    }
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
                        try
                        {
                            com.maddox.il2.ai.ZutiTargetsSupportMethods.staticActorDied(_fld0);
                        }
                        catch(java.lang.Exception exception1)
                        {
                            java.lang.System.out.println("StationaryGeneric error, ID_02: " + exception1.toString());
                        }
                    }
                    return true;

                case 100: // 'd'
                    com.maddox.rts.NetMsgGuaranted netmsgguaranted2 = new NetMsgGuaranted(netmsginput, 1);
                    postTo(masterChannel(), netmsgguaranted2);
                    return true;
                }
                return false;
            } else
            {
                return true;
            }
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
            {
                if(netmsginput.readByte() != 100)
                    return false;
            } else
            {
                return false;
            }
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

    public static class StationaryProperties
    {

        public java.lang.String meshName;
        public java.lang.String meshName1;
        public java.lang.String meshSummer;
        public java.lang.String meshDesert;
        public java.lang.String meshWinter;
        public java.lang.String meshSummer1;
        public java.lang.String meshDesert1;
        public java.lang.String meshWinter1;
        public com.maddox.util.TableFunction2 fnShotPanzer;
        public com.maddox.util.TableFunction2 fnExplodePanzer;
        public float PANZER_BODY_FRONT;
        public float PANZER_BODY_BACK;
        public float PANZER_BODY_SIDE;
        public float PANZER_BODY_TOP;
        public float PANZER_HEAD;
        public float PANZER_HEAD_TOP;
        public float PANZER_TNT_TYPE;
        public int HITBY_MASK;
        public java.lang.String explodeName;

        public StationaryProperties()
        {
            meshName = null;
            meshName1 = null;
            meshSummer = null;
            meshDesert = null;
            meshWinter = null;
            meshSummer1 = null;
            meshDesert1 = null;
            meshWinter1 = null;
            fnShotPanzer = null;
            fnExplodePanzer = null;
            PANZER_BODY_FRONT = 0.001F;
            PANZER_BODY_BACK = 0.001F;
            PANZER_BODY_SIDE = 0.001F;
            PANZER_BODY_TOP = 0.001F;
            PANZER_HEAD = 0.001F;
            PANZER_HEAD_TOP = 0.001F;
            PANZER_TNT_TYPE = 1.0F;
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
        float f = com.maddox.il2.ai.Shot.panzerThickness(pos.getAbsOrient(), shot.v, shot.chunkName.equalsIgnoreCase("Head"), prop.PANZER_BODY_FRONT, prop.PANZER_BODY_SIDE, prop.PANZER_BODY_BACK, prop.PANZER_BODY_TOP, prop.PANZER_HEAD, prop.PANZER_HEAD_TOP);
        f *= com.maddox.il2.objects.vehicles.stationary.StationaryGeneric.Rnd(0.93F, 1.07F);
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
            if(com.maddox.il2.objects.vehicles.tanks.TankGeneric.splintersKill(explosion, prop.fnShotPanzer, com.maddox.il2.objects.vehicles.stationary.StationaryGeneric.Rnd(0.0F, 1.0F), com.maddox.il2.objects.vehicles.stationary.StationaryGeneric.Rnd(0.0F, 1.0F), this, 0.7F, 0.25F, prop.PANZER_BODY_FRONT, prop.PANZER_BODY_SIDE, prop.PANZER_BODY_BACK, prop.PANZER_BODY_TOP, prop.PANZER_HEAD, prop.PANZER_HEAD_TOP))
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
        f *= com.maddox.il2.objects.vehicles.stationary.StationaryGeneric.Rnd(0.95F, 1.05F);
        float f1 = prop.fnExplodePanzer.Value(f, prop.PANZER_TNT_TYPE);
        if(f1 < 1000F && (f1 <= 1.0F || RndB(1.0F / f1)))
            Die(explosion.initiator, (short)0, true);
    }

    private void ShowExplode(float f, com.maddox.il2.engine.Actor actor)
    {
        if(f > 0.0F)
            f = com.maddox.il2.objects.vehicles.stationary.StationaryGeneric.Rnd(f, f * 1.6F);
        com.maddox.il2.objects.effects.Explosions.runByName(prop.explodeName, this, "Smoke", "SmokeHead", f, actor);
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
        if(prop.meshName1 == null)
            mesh().makeAllMaterialsDarker(0.22F, 0.35F);
        else
            setMesh(prop.meshName1);
        int i = mesh().hookFind("Ground_Level");
        if(i != -1)
        {
            com.maddox.JGP.Matrix4d matrix4d = new Matrix4d();
            mesh().hookMatrix(i, matrix4d);
            heightAboveLandSurface = (float)(-matrix4d.m23);
        }
        Align();
        if(flag)
            ShowExplode(15F, actor);
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

    protected StationaryGeneric()
    {
        this(constr_arg1, constr_arg2);
    }

    private StationaryGeneric(com.maddox.il2.objects.vehicles.stationary.StationaryProperties stationaryproperties, com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
    {
        super(stationaryproperties.meshName);
        prop = null;
        dying = 0;
        prop = stationaryproperties;
        actorspawnarg.setStationary(this);
        collide(true);
        drawing(true);
        createNetObject(actorspawnarg.netChannel, actorspawnarg.netIdRemote);
        heightAboveLandSurface = 0.0F;
        int i = mesh().hookFind("Ground_Level");
        if(i != -1)
        {
            com.maddox.JGP.Matrix4d matrix4d = new Matrix4d();
            mesh().hookMatrix(i, matrix4d);
            heightAboveLandSurface = (float)(-matrix4d.m23);
        } else
        {
            java.lang.System.out.println("Stationary " + getClass().getName() + ": hook Ground_Level not found");
        }
        Align();
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
        if(this instanceof com.maddox.il2.ai.ground.TgtTank)
        {
            if(abulletproperties[0].cumulativePower > 0.0F)
                return 0;
            if(abulletproperties[1].cumulativePower > 0.0F)
                return 1;
            if(abulletproperties[0].power <= 0.0F)
                return 0;
            if(abulletproperties[1].power <= 0.0F)
                return 1;
        } else
        {
            if(abulletproperties[0].power <= 0.0F)
                return 0;
            if(abulletproperties[1].power <= 0.0F)
                return 1;
            if(abulletproperties[0].cumulativePower > 0.0F)
                return 0;
            if(abulletproperties[1].cumulativePower > 0.0F)
                return 1;
        }
        if(abulletproperties[0].powerType == 1)
            return 0;
        if(abulletproperties[1].powerType == 1)
            return 1;
        return abulletproperties[0].powerType != 0 ? 0 : 1;
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
            ShowExplode(-1F, null);
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
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(100);
            netmsgguaranted.writeNetObj(actor != null ? ((com.maddox.rts.NetObj) (actor.net)) : null);
            net.postTo(net.masterChannel(), netmsgguaranted);
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

    private com.maddox.il2.objects.vehicles.stationary.StationaryProperties prop;
    private float heightAboveLandSurface;
    private int dying;
    static final int DYING_NONE = 0;
    static final int DYING_DEAD = 1;
    private static com.maddox.il2.objects.vehicles.stationary.StationaryProperties constr_arg1 = null;
    private static com.maddox.il2.engine.ActorSpawnArg constr_arg2 = null;
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();
    private static com.maddox.JGP.Vector3f n = new Vector3f();
    private static com.maddox.JGP.Vector3d tmpv = new Vector3d();





}
