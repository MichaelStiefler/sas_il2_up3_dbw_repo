// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ArtilleryGeneric.java

package com.maddox.il2.objects.vehicles.artillery;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Aimer;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.AnglesForkExtended;
import com.maddox.il2.ai.AnglesRange;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.TableFunctions;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.AirGroupList;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.ground.Aim;
import com.maddox.il2.ai.ground.HunterInterface;
import com.maddox.il2.ai.ground.NearestEnemies;
import com.maddox.il2.ai.ground.Obstacle;
import com.maddox.il2.ai.ground.Predator;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.ai.ground.TgtTank;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.vehicles.tanks.TankGeneric;
import com.maddox.il2.objects.weapons.CannonMidrangeGeneric;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.ObjState;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.util.TableFunction2;
import java.io.IOException;
import java.io.PrintStream;

public abstract class ArtilleryGeneric extends com.maddox.il2.engine.ActorHMesh
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener, com.maddox.il2.ai.ground.Predator, com.maddox.il2.ai.ground.Obstacle, com.maddox.il2.objects.ActorAlign, com.maddox.il2.ai.ground.HunterInterface
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
                    java.lang.System.out.println("Artillery: Parameter [" + s + "]:<" + s1 + "> " + "not found");
                else
                    java.lang.System.out.println("Artillery: Value of [" + s + "]:<" + s1 + "> (" + f2 + ")" + " is out of range (" + f + ";" + f1 + ")");
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
                java.lang.System.out.print("Artillery: Parameter [" + s + "]:<" + s1 + "> ");
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

        private static com.maddox.il2.objects.vehicles.artillery.ArtilleryProperties LoadArtilleryProperties(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.Class class1)
        {
            com.maddox.il2.objects.vehicles.artillery.ArtilleryProperties artilleryproperties = new ArtilleryProperties();
            java.lang.String s1 = com.maddox.il2.objects.vehicles.artillery.SPAWN.getS(sectfile, s, "PanzerType", null);
            if(s1 == null)
                s1 = "Tank";
            artilleryproperties.fnShotPanzer = com.maddox.il2.ai.TableFunctions.GetFunc2(s1 + "ShotPanzer");
            artilleryproperties.fnExplodePanzer = com.maddox.il2.ai.TableFunctions.GetFunc2(s1 + "ExplodePanzer");
            artilleryproperties.PANZER_TNT_TYPE = com.maddox.il2.objects.vehicles.artillery.SPAWN.getF(sectfile, s, "PanzerSubtype", 0.0F, 100F);
            artilleryproperties.meshSummer = com.maddox.il2.objects.vehicles.artillery.SPAWN.getS(sectfile, s, "MeshSummer");
            artilleryproperties.meshDesert = com.maddox.il2.objects.vehicles.artillery.SPAWN.getS(sectfile, s, "MeshDesert", artilleryproperties.meshSummer);
            artilleryproperties.meshWinter = com.maddox.il2.objects.vehicles.artillery.SPAWN.getS(sectfile, s, "MeshWinter", artilleryproperties.meshSummer);
            artilleryproperties.meshSummer1 = com.maddox.il2.objects.vehicles.artillery.SPAWN.getS(sectfile, s, "MeshSummerDamage", null);
            artilleryproperties.meshDesert1 = com.maddox.il2.objects.vehicles.artillery.SPAWN.getS(sectfile, s, "MeshDesertDamage", artilleryproperties.meshSummer1);
            artilleryproperties.meshWinter1 = com.maddox.il2.objects.vehicles.artillery.SPAWN.getS(sectfile, s, "MeshWinterDamage", artilleryproperties.meshSummer1);
            float f = (artilleryproperties.meshSummer1 != null ? 0 : 1) + (artilleryproperties.meshDesert1 != null ? 0 : 1) + (artilleryproperties.meshWinter1 != null ? 0 : 1);
            if(f != 0 && f != 3)
            {
                java.lang.System.out.println("Artillery: Uncomplete set of damage meshes for '" + s + "'");
                throw new RuntimeException("Can't register artillery object");
            }
            artilleryproperties.PANZER_BODY_FRONT = com.maddox.il2.objects.vehicles.artillery.SPAWN.getF(sectfile, s, "PanzerBodyFront", 0.001F, 9.999F);
            if(sectfile.get(s, "PanzerBodyBack", -9865.345F) == -9865.345F)
            {
                artilleryproperties.PANZER_BODY_BACK = artilleryproperties.PANZER_BODY_FRONT;
                artilleryproperties.PANZER_BODY_SIDE = artilleryproperties.PANZER_BODY_FRONT;
                artilleryproperties.PANZER_BODY_TOP = artilleryproperties.PANZER_BODY_FRONT;
            } else
            {
                artilleryproperties.PANZER_BODY_BACK = com.maddox.il2.objects.vehicles.artillery.SPAWN.getF(sectfile, s, "PanzerBodyBack", 0.001F, 9.999F);
                artilleryproperties.PANZER_BODY_SIDE = com.maddox.il2.objects.vehicles.artillery.SPAWN.getF(sectfile, s, "PanzerBodySide", 0.001F, 9.999F);
                artilleryproperties.PANZER_BODY_TOP = com.maddox.il2.objects.vehicles.artillery.SPAWN.getF(sectfile, s, "PanzerBodyTop", 0.001F, 9.999F);
            }
            if(sectfile.get(s, "PanzerHead", -9865.345F) == -9865.345F)
                artilleryproperties.PANZER_HEAD = artilleryproperties.PANZER_BODY_FRONT;
            else
                artilleryproperties.PANZER_HEAD = com.maddox.il2.objects.vehicles.artillery.SPAWN.getF(sectfile, s, "PanzerHead", 0.001F, 9.999F);
            if(sectfile.get(s, "PanzerHeadTop", -9865.345F) == -9865.345F)
                artilleryproperties.PANZER_HEAD_TOP = artilleryproperties.PANZER_BODY_TOP;
            else
                artilleryproperties.PANZER_HEAD_TOP = com.maddox.il2.objects.vehicles.artillery.SPAWN.getF(sectfile, s, "PanzerHeadTop", 0.001F, 9.999F);
            f = java.lang.Math.min(java.lang.Math.min(artilleryproperties.PANZER_BODY_BACK, artilleryproperties.PANZER_BODY_TOP), java.lang.Math.min(artilleryproperties.PANZER_BODY_SIDE, artilleryproperties.PANZER_HEAD_TOP));
            artilleryproperties.HITBY_MASK = f <= 0.015F ? -1 : -2;
            artilleryproperties.explodeName = com.maddox.il2.objects.vehicles.artillery.SPAWN.getS(sectfile, s, "Explode", "Artillery");
            java.lang.String s2 = "com.maddox.il2.objects.weapons." + com.maddox.il2.objects.vehicles.artillery.SPAWN.getS(sectfile, s, "Gun");
            try
            {
                artilleryproperties.gunClass = java.lang.Class.forName(s2);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println("Artillery: Can't find gun class '" + s2 + "'");
                throw new RuntimeException("Can't register artillery object");
            }
            artilleryproperties.WEAPONS_MASK = com.maddox.il2.objects.weapons.Gun.getProperties(artilleryproperties.gunClass).weaponType;
            if(artilleryproperties.WEAPONS_MASK == 0)
            {
                java.lang.System.out.println("Artillery: Undefined weapon type in gun class '" + s2 + "'");
                throw new RuntimeException("Can't register artillery object");
            }
            artilleryproperties.ATTACK_FAST_TARGETS = true;
            float f1 = sectfile.get(s, "FireFastTargets", -9865.345F);
            if(f1 != -9865.345F)
                artilleryproperties.ATTACK_FAST_TARGETS = f1 > 0.5F;
            else
            if(s1.equals("Tank"))
                artilleryproperties.ATTACK_FAST_TARGETS = false;
            artilleryproperties.ATTACK_MAX_DISTANCE = com.maddox.il2.objects.vehicles.artillery.SPAWN.getF(sectfile, s, "AttackMaxDistance", 6F, 12000F);
            artilleryproperties.ATTACK_MAX_RADIUS = com.maddox.il2.objects.vehicles.artillery.SPAWN.getF(sectfile, s, "AttackMaxRadius", 6F, 12000F);
            artilleryproperties.ATTACK_MAX_HEIGHT = com.maddox.il2.objects.vehicles.artillery.SPAWN.getF(sectfile, s, "AttackMaxHeight", 6F, 12000F);
            float f2 = com.maddox.il2.objects.vehicles.artillery.SPAWN.getF(sectfile, s, "HeadYawHalfRange", 0.0F, 180F);
            artilleryproperties.HEAD_YAW_RANGE.set(-f2, f2);
            artilleryproperties.GUN_MIN_PITCH = com.maddox.il2.objects.vehicles.artillery.SPAWN.getF(sectfile, s, "GunMinPitch", -15F, 85F);
            artilleryproperties.GUN_STD_PITCH = com.maddox.il2.objects.vehicles.artillery.SPAWN.getF(sectfile, s, "GunStdPitch", -15F, 89.9F);
            artilleryproperties.GUN_MAX_PITCH = com.maddox.il2.objects.vehicles.artillery.SPAWN.getF(sectfile, s, "GunMaxPitch", 0.0F, 89.9F);
            artilleryproperties.HEAD_MAX_YAW_SPEED = com.maddox.il2.objects.vehicles.artillery.SPAWN.getF(sectfile, s, "HeadMaxYawSpeed", 0.1F, 999F);
            artilleryproperties.GUN_MAX_PITCH_SPEED = com.maddox.il2.objects.vehicles.artillery.SPAWN.getF(sectfile, s, "GunMaxPitchSpeed", 0.1F, 999F);
            artilleryproperties.DELAY_AFTER_SHOOT = com.maddox.il2.objects.vehicles.artillery.SPAWN.getF(sectfile, s, "DelayAfterShoot", 0.0F, 999F);
            artilleryproperties.CHAINFIRE_TIME = com.maddox.il2.objects.vehicles.artillery.SPAWN.getF(sectfile, s, "ChainfireTime", 0.0F, 600F);
            float f3 = sectfile.get(s, "FastTargetsAngleError", -9865.345F);
            if(f3 <= 0.0F)
                f3 = 0.0F;
            else
            if(f3 >= 45F)
                f3 = 45F;
            artilleryproperties.FAST_TARGETS_ANGLE_ERROR = f3;
            com.maddox.rts.Property.set(class1, "iconName", "icons/" + com.maddox.il2.objects.vehicles.artillery.SPAWN.getS(sectfile, s, "Icon") + ".mat");
            com.maddox.rts.Property.set(class1, "meshName", artilleryproperties.meshSummer);
            return artilleryproperties;
        }

        public com.maddox.il2.engine.Actor actorSpawn(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            switch(com.maddox.il2.ai.World.cur().camouflage)
            {
            case 1: // '\001'
                proper.meshName = proper.meshWinter;
                proper.meshName2 = proper.meshWinter1;
                break;

            case 2: // '\002'
                proper.meshName = proper.meshDesert;
                proper.meshName2 = proper.meshDesert1;
                break;

            default:
                proper.meshName = proper.meshSummer;
                proper.meshName2 = proper.meshSummer1;
                break;
            }
            com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric artillerygeneric = null;
            try
            {
                com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.constr_arg1 = proper;
                com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.constr_arg2 = actorspawnarg;
                artillerygeneric = (com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric)cls.newInstance();
                com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.constr_arg1 = null;
                com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.constr_arg2 = null;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.constr_arg1 = null;
                com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.constr_arg2 = null;
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("SPAWN: Can't create Artillery object [class:" + cls.getName() + "]");
                return null;
            }
            return artillerygeneric;
        }

        public java.lang.Class cls;
        public com.maddox.il2.objects.vehicles.artillery.ArtilleryProperties proper;

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
                proper = com.maddox.il2.objects.vehicles.artillery.SPAWN.LoadArtilleryProperties(com.maddox.il2.objects.Statics.getTechnicsFile(), s1, class1);
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
                    float f = netmsginput.readFloat();
                    float f2 = netmsginput.readFloat();
                    if(word0 <= 0)
                    {
                        if(dying != 1)
                        {
                            aime.forgetAiming();
                            setGunAngles(f, f2);
                        }
                    } else
                    if(dying != 1)
                    {
                        setGunAngles(f, f2);
                        Die(null, word0, false);
                    }
                    return true;

                case 82: // 'R'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted(netmsginput, 0);
                        post(netmsgguaranted1);
                    }
                    dying = 0;
                    setDiedFlag(false);
                    aime.forgetAiming();
                    setMesh(prop.meshName);
                    setDefaultLivePose();
                    return true;

                case 68: // 'D'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted2 = new NetMsgGuaranted(netmsginput, 1);
                        post(netmsgguaranted2);
                    }
                    short word1 = netmsginput.readShort();
                    float f1 = netmsginput.readFloat();
                    float f3 = netmsginput.readFloat();
                    if(word1 > 0 && dying != 1)
                    {
                        setGunAngles(f1, f3);
                        com.maddox.rts.NetObj netobj2 = netmsginput.readNetObj();
                        com.maddox.il2.engine.Actor actor2 = netobj2 != null ? ((com.maddox.il2.engine.ActorNet)netobj2).actor() : null;
                        Die(actor2, word1, true);
                    }
                    return true;
                }
                return false;
            }
            switch(netmsginput.readByte())
            {
            default:
                break;

            case 84: // 'T'
                if(isMirrored())
                {
                    out.unLockAndSet(netmsginput, 1);
                    out.setIncludeTime(false);
                    postReal(com.maddox.rts.Message.currentRealTime(), out);
                }
                com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                com.maddox.il2.engine.Actor actor = netobj != null ? ((com.maddox.il2.engine.ActorNet)netobj).actor() : null;
                int i = netmsginput.readUnsignedByte();
                Track_Mirror(actor, i);
                break;

            case 70: // 'F'
                if(isMirrored())
                {
                    out.unLockAndSet(netmsginput, 1);
                    out.setIncludeTime(true);
                    postReal(com.maddox.rts.Message.currentRealTime(), out);
                }
                com.maddox.rts.NetObj netobj1 = netmsginput.readNetObj();
                com.maddox.il2.engine.Actor actor1 = netobj1 != null ? ((com.maddox.il2.engine.ActorNet)netobj1).actor() : null;
                float f4 = netmsginput.readFloat();
                float f5 = 0.001F * (float)(com.maddox.rts.Message.currentGameTime() - com.maddox.rts.Time.current()) + f4;
                int j = netmsginput.readUnsignedByte();
                Fire_Mirror(actor1, j, f5);
                break;

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

    class Move extends com.maddox.il2.engine.Interpolate
    {

        public boolean tick()
        {
            if(dying == 1)
                if(respawnDelay-- <= 0L)
                {
                    if(!com.maddox.il2.game.Mission.isDeathmatch())
                    {
                        if(aime != null)
                        {
                            aime.forgetAll();
                            aime = null;
                        }
                        if(gun != null)
                        {
                            com.maddox.rts.ObjState.destroy(gun);
                            gun = null;
                        }
                        return false;
                    }
                    if(!isNetMaster())
                    {
                        respawnDelay = 10000L;
                        return true;
                    }
                    dying = 0;
                    hideTmr = 0L;
                    if(!isNetMirror() && RADIUS_HIDE > 0.0F)
                        hideTmr = -1L;
                    setDiedFlag(false);
                    aime.forgetAiming();
                    setMesh(prop.meshName);
                    setDefaultLivePose();
                    send_RespawnCommand();
                    dontShoot = false;
                    time_lastCheckShoot = com.maddox.rts.Time.current() - 12000L;
                    return true;
                } else
                {
                    return true;
                }
            aime.tick_();
            if(RADIUS_HIDE > 0.0F && hideTmr >= 0L && !isNetMirror())
                if(aime.getEnemy() != null)
                    hideTmr = 0L;
                else
                if(++hideTmr > com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.delay_hide_ticks)
                    hideTmr = -1L;
            return true;
        }

        Move()
        {
        }
    }

    public static class ArtilleryProperties
    {

        public java.lang.String meshName;
        public java.lang.String meshName2;
        public java.lang.String meshSummer;
        public java.lang.String meshDesert;
        public java.lang.String meshWinter;
        public java.lang.String meshSummer1;
        public java.lang.String meshDesert1;
        public java.lang.String meshWinter1;
        public java.lang.Class gunClass;
        public com.maddox.util.TableFunction2 fnShotPanzer;
        public com.maddox.util.TableFunction2 fnExplodePanzer;
        public float PANZER_BODY_FRONT;
        public float PANZER_BODY_BACK;
        public float PANZER_BODY_SIDE;
        public float PANZER_BODY_TOP;
        public float PANZER_HEAD;
        public float PANZER_HEAD_TOP;
        public float PANZER_TNT_TYPE;
        public java.lang.String explodeName;
        public int WEAPONS_MASK;
        public int HITBY_MASK;
        public boolean ATTACK_FAST_TARGETS;
        public float ATTACK_MAX_DISTANCE;
        public float ATTACK_MAX_RADIUS;
        public float ATTACK_MAX_HEIGHT;
        public com.maddox.il2.ai.AnglesRange HEAD_YAW_RANGE;
        public float GUN_MIN_PITCH;
        public float GUN_STD_PITCH;
        public float GUN_MAX_PITCH;
        public float HEAD_MAX_YAW_SPEED;
        public float GUN_MAX_PITCH_SPEED;
        public float DELAY_AFTER_SHOOT;
        public float CHAINFIRE_TIME;
        public float FAST_TARGETS_ANGLE_ERROR;

        public ArtilleryProperties()
        {
            meshName = null;
            meshName2 = null;
            meshSummer = null;
            meshDesert = null;
            meshWinter = null;
            meshSummer1 = null;
            meshDesert1 = null;
            meshWinter1 = null;
            gunClass = null;
            fnShotPanzer = null;
            fnExplodePanzer = null;
            PANZER_BODY_FRONT = 0.001F;
            PANZER_BODY_BACK = 0.001F;
            PANZER_BODY_SIDE = 0.001F;
            PANZER_BODY_TOP = 0.001F;
            PANZER_HEAD = 0.001F;
            PANZER_HEAD_TOP = 0.001F;
            PANZER_TNT_TYPE = 1.0F;
            explodeName = null;
            WEAPONS_MASK = 4;
            HITBY_MASK = -2;
            ATTACK_FAST_TARGETS = true;
            ATTACK_MAX_DISTANCE = 1.0F;
            ATTACK_MAX_RADIUS = 1.0F;
            ATTACK_MAX_HEIGHT = 1.0F;
            HEAD_YAW_RANGE = new AnglesRange(-1F, 1.0F);
            GUN_MIN_PITCH = -20F;
            GUN_STD_PITCH = -18F;
            GUN_MAX_PITCH = -15F;
            HEAD_MAX_YAW_SPEED = 720F;
            GUN_MAX_PITCH_SPEED = 60F;
            DELAY_AFTER_SHOOT = 1.0F;
            CHAINFIRE_TIME = 0.0F;
            FAST_TARGETS_ANGLE_ERROR = 0.0F;
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

    private boolean friendPlanesAreNear(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        time_lastCheckShoot = com.maddox.rts.Time.current() - (long)com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.Rnd(0.0F, 1200F);
        dontShoot = false;
        com.maddox.JGP.Point3d point3d = aircraft.pos.getAbsPoint();
        double d = 16000000D;
        if(!(aircraft.FM instanceof com.maddox.il2.ai.air.Maneuver))
            return false;
        com.maddox.il2.ai.air.AirGroup airgroup = ((com.maddox.il2.ai.air.Maneuver)(com.maddox.il2.ai.air.Maneuver)aircraft.FM).Group;
        if(airgroup == null)
            return false;
        int i = com.maddox.il2.ai.air.AirGroupList.length(airgroup.enemies[0]);
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.air.AirGroup airgroup1 = com.maddox.il2.ai.air.AirGroupList.getGroup(airgroup.enemies[0], j);
            if(airgroup1.nOfAirc <= 0)
                continue;
            double d1 = airgroup1.Pos.x - point3d.x;
            double d2 = airgroup1.Pos.y - point3d.y;
            double d3 = airgroup1.Pos.z - point3d.z;
            if(d1 * d1 + d2 * d2 + d3 * d3 > d)
                continue;
            d3 = point3d.z - com.maddox.il2.engine.Engine.land().HQ(point3d.x, point3d.y);
            if(d3 <= 50D)
                continue;
            dontShoot = true;
            break;
        }

        return dontShoot;
    }

    protected final boolean Head360()
    {
        return prop.HEAD_YAW_RANGE.fullcircle();
    }

    public void msgCollisionRequest(com.maddox.il2.engine.Actor actor, boolean aflag[])
    {
        if((actor instanceof com.maddox.il2.engine.ActorMesh) && ((com.maddox.il2.engine.ActorMesh)actor).isStaticPos())
        {
            aflag[0] = false;
            return;
        } else
        {
            return;
        }
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
        f *= com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.Rnd(0.93F, 1.07F);
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
            if(com.maddox.il2.objects.vehicles.tanks.TankGeneric.splintersKill(explosion, prop.fnShotPanzer, com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.Rnd(0.0F, 1.0F), com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.Rnd(0.0F, 1.0F), this, 0.7F, 0.25F, prop.PANZER_BODY_FRONT, prop.PANZER_BODY_SIDE, prop.PANZER_BODY_BACK, prop.PANZER_BODY_TOP, prop.PANZER_HEAD, prop.PANZER_HEAD_TOP))
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
        f *= com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.Rnd(0.95F, 1.05F);
        float f1 = prop.fnExplodePanzer.Value(f, prop.PANZER_TNT_TYPE);
        if(f1 < 1000F && (f1 <= 1.0F || RndB(1.0F / f1)))
            Die(explosion.initiator, (short)0, true);
    }

    private void ShowExplode(float f)
    {
        if(f > 0.0F)
            f = com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.Rnd(f, f * 1.6F);
        com.maddox.il2.objects.effects.Explosions.runByName(prop.explodeName, this, "SmokeHead", "", f);
    }

    private float[] computeDeathPose(short word0)
    {
        com.maddox.il2.ai.RangeRandom rangerandom = new RangeRandom(word0);
        float af[] = new float[10];
        af[0] = headYaw + rangerandom.nextFloat(-15F, 15F);
        af[1] = (rangerandom.nextBoolean() ? 1.0F : -1F) * rangerandom.nextFloat(4F, 9F);
        af[2] = (rangerandom.nextBoolean() ? 1.0F : -1F) * rangerandom.nextFloat(4F, 9F);
        af[3] = -gunPitch + rangerandom.nextFloat(-15F, 15F);
        af[4] = (rangerandom.nextBoolean() ? 1.0F : -1F) * rangerandom.nextFloat(2.0F, 5F);
        af[5] = (rangerandom.nextBoolean() ? 1.0F : -1F) * rangerandom.nextFloat(5F, 9F);
        af[6] = 0.0F;
        af[7] = (rangerandom.nextBoolean() ? 1.0F : -1F) * rangerandom.nextFloat(4F, 8F);
        af[8] = (rangerandom.nextBoolean() ? 1.0F : -1F) * rangerandom.nextFloat(7F, 12F);
        af[9] = -rangerandom.nextFloat(0.0F, 0.25F);
        return af;
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
            word0 = (short)(int)com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.Rnd(1.0F, 30000F);
        }
        deathSeed = word0;
        dying = 1;
        com.maddox.il2.ai.World.onActorDied(this, actor);
        if(aime != null)
            aime.forgetAiming();
        float af[] = computeDeathPose(word0);
        hierMesh().chunkSetAngles("Head", af[0], af[1], af[2]);
        hierMesh().chunkSetAngles("Gun", af[3], af[4], af[5]);
        hierMesh().chunkSetAngles("Body", af[6], af[7], af[8]);
        if(prop.meshName2 == null)
        {
            mesh().makeAllMaterialsDarker(0.22F, 0.35F);
            heightAboveLandSurface2 = heightAboveLandSurface;
            heightAboveLandSurface = heightAboveLandSurface2 + af[9];
        } else
        {
            setMesh(prop.meshName2);
            heightAboveLandSurface2 = 0.0F;
            int i = mesh().hookFind("Ground_Level");
            if(i != -1)
            {
                com.maddox.JGP.Matrix4d matrix4d = new Matrix4d();
                mesh().hookMatrix(i, matrix4d);
                heightAboveLandSurface2 = (float)(-matrix4d.m23);
            }
            heightAboveLandSurface = heightAboveLandSurface2;
        }
        Align();
        if(flag)
            ShowExplode(15F);
        if(flag)
            send_DeathCommand(actor);
    }

    private void setGunAngles(float f, float f1)
    {
        headYaw = f;
        gunPitch = f1;
        hierMesh().chunkSetAngles("Head", headYaw, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("Gun", -gunPitch, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("Body", 0.0F, 0.0F, 0.0F);
        pos.inValidate(false);
    }

    public void destroy()
    {
        if(isDestroyed())
            return;
        if(aime != null)
        {
            aime.forgetAll();
            aime = null;
        }
        if(gun != null)
        {
            com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.destroy(((com.maddox.rts.Destroy) (gun)));
            gun = null;
        }
        super.destroy();
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public boolean isStaticPos()
    {
        return true;
    }

    private void setDefaultLivePose()
    {
        heightAboveLandSurface = 0.0F;
        int i = hierMesh().hookFind("Ground_Level");
        if(i != -1)
        {
            com.maddox.JGP.Matrix4d matrix4d = new Matrix4d();
            hierMesh().hookMatrix(i, matrix4d);
            heightAboveLandSurface = (float)(-matrix4d.m23);
        }
        setGunAngles(0.0F, prop.GUN_STD_PITCH);
        Align();
    }

    protected ArtilleryGeneric()
    {
        this(constr_arg1, constr_arg2);
    }

    public void setMesh(java.lang.String s)
    {
        super.setMesh(s);
        if(com.maddox.il2.engine.Config.cur.b3dgunners)
        {
            return;
        } else
        {
            mesh().materialReplaceToNull("Pilot1");
            return;
        }
    }

    private ArtilleryGeneric(com.maddox.il2.objects.vehicles.artillery.ArtilleryProperties artilleryproperties, com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
    {
        super(artilleryproperties.meshName);
        prop = null;
        nearAirfield = false;
        dontShoot = false;
        time_lastCheckShoot = 0L;
        dying = 0;
        respawnDelay = 0L;
        hideTmr = 0L;
        RADIUS_HIDE = 0.0F;
        outCommand = new NetMsgFiltered();
        prop = artilleryproperties;
        delay_hide_ticks = com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.SecsToTicks(240F);
        actorspawnarg.setStationary(this);
        collide(true);
        drawing(true);
        createNetObject(actorspawnarg.netChannel, actorspawnarg.netIdRemote);
        startDelay = 0L;
        if(actorspawnarg.timeLenExist)
        {
            startDelay = (long)(actorspawnarg.timeLen * 60F * 1000F + 0.5F);
            if(startDelay < 0L)
                startDelay = 0L;
        }
        RADIUS_HIDE = new_RADIUS_HIDE;
        hideTmr = 0L;
        gun = null;
        try
        {
            gun = (com.maddox.il2.objects.weapons.Gun)prop.gunClass.newInstance();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
            java.lang.System.out.println("Artillery: Can't create gun '" + prop.gunClass.getName() + "'");
        }
        gun.set(this, "Gun");
        gun.loadBullets(-1);
        headYaw = 0.0F;
        gunPitch = 0.0F;
        if(!isNetMirror() && RADIUS_HIDE > 0.0F)
            hideTmr = -1L;
        setDefaultLivePose();
        startMove();
        com.maddox.JGP.Point3d point3d = pos.getAbsPoint();
        com.maddox.il2.ai.Airport airport = com.maddox.il2.ai.Airport.nearest(point3d, -1, 7);
        if(airport != null)
        {
            float f = (float)airport.pos.getAbsPoint().distance(point3d);
            nearAirfield = f <= 2000F;
        } else
        {
            nearAirfield = false;
        }
        dontShoot = false;
        time_lastCheckShoot = com.maddox.rts.Time.current() - (long)com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.Rnd(0.0F, 12000F);
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

    public void startMove()
    {
        if(!interpEnd("move"))
        {
            if(aime != null)
            {
                aime.forgetAll();
                aime = null;
            }
            aime = new Aim(this, isNetMirror());
            interpPut(new Move(), "move", com.maddox.rts.Time.current(), null);
        }
    }

    public int WeaponsMask()
    {
        return prop.WEAPONS_MASK;
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

    public float AttackMaxDistance()
    {
        return prop.ATTACK_MAX_DISTANCE;
    }

    public float AttackMaxRadius()
    {
        return prop.ATTACK_MAX_RADIUS;
    }

    public float AttackMaxHeight()
    {
        return prop.ATTACK_MAX_HEIGHT;
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
        if(com.maddox.il2.game.Mission.isDeathmatch())
        {
            float f = com.maddox.il2.game.Mission.respawnTime("Artillery");
            respawnDelay = com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.Rnd(f, f * 1.2F));
        } else
        {
            respawnDelay = 0L;
        }
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        try
        {
            netmsgguaranted.writeByte(68);
            netmsgguaranted.writeShort(deathSeed);
            netmsgguaranted.writeFloat(headYaw);
            netmsgguaranted.writeFloat(gunPitch);
            netmsgguaranted.writeNetObj(actor != null ? ((com.maddox.rts.NetObj) (actor.net)) : null);
            net.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void send_RespawnCommand()
    {
        if(!isNetMaster() || !com.maddox.il2.game.Mission.isDeathmatch())
            return;
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        try
        {
            netmsgguaranted.writeByte(82);
            net.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void send_FireCommand(com.maddox.il2.engine.Actor actor, int i, float f)
    {
        if(!isNetMaster())
            return;
        if(!net.isMirrored())
            return;
        if(!com.maddox.il2.engine.Actor.isValid(actor) || !actor.isNet())
            return;
        i &= 0xff;
        if(f < 0.0F)
            try
            {
                outCommand.unLockAndClear();
                outCommand.writeByte(84);
                outCommand.writeNetObj(actor.net);
                outCommand.writeByte(i);
                outCommand.setIncludeTime(false);
                net.post(com.maddox.rts.Time.current(), outCommand);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        else
            try
            {
                outCommand.unLockAndClear();
                outCommand.writeByte(70);
                outCommand.writeFloat(f);
                outCommand.writeNetObj(actor.net);
                outCommand.writeByte(i);
                outCommand.setIncludeTime(true);
                net.post(com.maddox.rts.Time.current(), outCommand);
            }
            catch(java.lang.Exception exception1)
            {
                java.lang.System.out.println(exception1.getMessage());
                exception1.printStackTrace();
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
            netmsgguaranted.writeShort(deathSeed);
        netmsgguaranted.writeFloat(headYaw);
        netmsgguaranted.writeFloat(gunPitch);
        net.postTo(netchannel, netmsgguaranted);
    }

    public float getReloadingTime(com.maddox.il2.ai.ground.Aim aim)
    {
        return prop.DELAY_AFTER_SHOOT;
    }

    public float chainFireTime(com.maddox.il2.ai.ground.Aim aim)
    {
        return prop.CHAINFIRE_TIME > 0.0F ? prop.CHAINFIRE_TIME * com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.Rnd(0.75F, 1.25F) : 0.0F;
    }

    public float probabKeepSameEnemy(com.maddox.il2.engine.Actor actor)
    {
        if(nearAirfield || isNetMirror() || actor == null || !(actor instanceof com.maddox.il2.objects.air.Aircraft) || java.lang.Math.abs(time_lastCheckShoot - com.maddox.rts.Time.current()) < 12000L || (float)actor.getSpeed(null) < 10F)
            return 0.75F;
        return !friendPlanesAreNear((com.maddox.il2.objects.air.Aircraft)actor) ? 0.75F : 0.0F;
    }

    public float minTimeRelaxAfterFight()
    {
        return 0.0F;
    }

    public void gunStartParking(com.maddox.il2.ai.ground.Aim aim)
    {
        aim.setRotationForParking(headYaw, gunPitch, 0.0F, prop.GUN_STD_PITCH, prop.HEAD_YAW_RANGE, prop.HEAD_MAX_YAW_SPEED, prop.GUN_MAX_PITCH_SPEED);
    }

    public void gunInMove(boolean flag, com.maddox.il2.ai.ground.Aim aim)
    {
        float f = aim.t();
        float f1 = aim.anglesYaw.getDeg(f);
        float f2 = aim.anglesPitch.getDeg(f);
        setGunAngles(f1, f2);
    }

    public static final float KmHourToMSec(float f)
    {
        return f * 0.27778F;
    }

    public com.maddox.il2.engine.Actor findEnemy(com.maddox.il2.ai.ground.Aim aim)
    {
        if(isNetMirror())
            return null;
        if(com.maddox.rts.Time.current() < startDelay)
            return null;
        com.maddox.il2.engine.Actor actor = null;
        if(prop.ATTACK_FAST_TARGETS)
            com.maddox.il2.ai.ground.NearestEnemies.set(WeaponsMask());
        else
            com.maddox.il2.ai.ground.NearestEnemies.set(WeaponsMask(), -9999.9F, com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.KmHourToMSec(100F));
        actor = com.maddox.il2.ai.ground.NearestEnemies.getAFoundEnemy(pos.getAbsPoint(), AttackMaxRadius(), getArmy());
        if(actor == null)
            return null;
        if(!(actor instanceof com.maddox.il2.ai.ground.Prey))
        {
            java.lang.System.out.println("arti: nearest enemies: non-Prey");
            return null;
        }
        boolean flag = true;
        if(!nearAirfield && !isNetMirror() && (actor instanceof com.maddox.il2.objects.air.Aircraft) && (float)actor.getSpeed(null) >= 10F)
            if(java.lang.Math.abs(time_lastCheckShoot - com.maddox.rts.Time.current()) < 12000L)
            {
                if(dontShoot)
                    return null;
            } else
            if(friendPlanesAreNear((com.maddox.il2.objects.air.Aircraft)actor))
                return null;
        com.maddox.il2.engine.BulletProperties bulletproperties = null;
        if(gun.prop != null)
        {
            int i = ((com.maddox.il2.ai.ground.Prey)actor).chooseBulletType(gun.prop.bullet);
            if(i < 0)
                return null;
            bulletproperties = gun.prop.bullet[i];
        }
        int j = ((com.maddox.il2.ai.ground.Prey)actor).chooseShotpoint(bulletproperties);
        if(j < 0)
        {
            return null;
        } else
        {
            aim.shotpoint_idx = j;
            return actor;
        }
    }

    public boolean enterToFireMode(int i, com.maddox.il2.engine.Actor actor, float f, com.maddox.il2.ai.ground.Aim aim)
    {
        if(i == 1 && hideTmr < 0L)
        {
            float f1 = (float)actor.pos.getAbsPoint().distanceSquared(pos.getAbsPoint());
            if(f1 > RADIUS_HIDE * RADIUS_HIDE)
                return false;
            hideTmr = 0L;
        }
        if(!isNetMirror())
            send_FireCommand(actor, aim.shotpoint_idx, i != 0 ? f : -1F);
        return true;
    }

    private void Track_Mirror(com.maddox.il2.engine.Actor actor, int i)
    {
        if(dying == 1)
            return;
        if(actor == null)
            return;
        if(aime == null)
        {
            return;
        } else
        {
            aime.passive_StartFiring(0, actor, i, 0.0F);
            return;
        }
    }

    private void Fire_Mirror(com.maddox.il2.engine.Actor actor, int i, float f)
    {
        if(dying == 1)
            return;
        if(actor == null)
            return;
        if(aime == null)
            return;
        if(f <= 0.2F)
            f = 0.2F;
        if(f >= 15F)
            f = 15F;
        aime.passive_StartFiring(1, actor, i, f);
    }

    public int targetGun(com.maddox.il2.ai.ground.Aim aim, com.maddox.il2.engine.Actor actor, float f, boolean flag)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor) || !actor.isAlive() || actor.getArmy() == 0)
            return 0;
        if(gun instanceof com.maddox.il2.objects.weapons.CannonMidrangeGeneric)
        {
            int i = ((com.maddox.il2.ai.ground.Prey)actor).chooseBulletType(gun.prop.bullet);
            if(i < 0)
                return 0;
            ((com.maddox.il2.objects.weapons.CannonMidrangeGeneric)gun).setBulletType(i);
        }
        boolean flag1 = ((com.maddox.il2.ai.ground.Prey)actor).getShotpointOffset(aim.shotpoint_idx, p1);
        if(!flag1)
            return 0;
        float f1 = f * com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.Rnd(0.8F, 1.2F);
        if(!com.maddox.il2.ai.Aimer.Aim((com.maddox.il2.ai.BulletAimer)gun, actor, this, f1, p1, null))
            return 0;
        com.maddox.JGP.Point3d point3d = new Point3d();
        com.maddox.il2.ai.Aimer.GetPredictedTargetPosition(point3d);
        com.maddox.JGP.Point3d point3d1 = com.maddox.il2.ai.Aimer.GetHunterFirePoint();
        float f2 = 0.19F;
        double d = point3d.distance(point3d1);
        double d1 = point3d.z;
        point3d.sub(point3d1);
        point3d.scale(com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.Rnd(0.95999999999999996D, 1.04D));
        point3d.add(point3d1);
        if(f1 > 0.001F)
        {
            com.maddox.JGP.Point3d point3d2 = new Point3d();
            actor.pos.getAbs(point3d2);
            tmpv.sub(point3d, point3d2);
            double d2 = tmpv.length();
            if(d2 > 0.001D)
            {
                float f7 = (float)d2 / f1;
                if(f7 > 200F)
                    f7 = 200F;
                float f8 = f7 * 0.015F;
                point3d2.sub(point3d1);
                double d3 = point3d2.x * point3d2.x + point3d2.y * point3d2.y + point3d2.z * point3d2.z;
                if(d3 > 0.01D)
                {
                    float f9 = (float)tmpv.dot(point3d2);
                    f9 /= (float)(d2 * java.lang.Math.sqrt(d3));
                    f9 = (float)java.lang.Math.sqrt(1.0F - f9 * f9);
                    f8 *= 0.4F + 0.6F * f9;
                }
                f8 *= 1.1F;
                int k = 0;
                k = com.maddox.il2.game.Mission.curCloudsType();
                if(k > 2)
                {
                    float f10 = k <= 4 ? 500F : 300F;
                    float f11 = (float)(d / (double)f10);
                    if(f11 > 1.0F)
                    {
                        if(f11 > 10F)
                            return 0;
                        f11 = ((f11 - 1.0F) / 9F) * 2.0F + 1.0F;
                        f8 *= f11;
                    }
                }
                if(k >= 3 && d1 > (double)com.maddox.il2.game.Mission.curCloudsHeight())
                    f8 *= 1.25F;
                f2 += f8;
            }
        }
        if(com.maddox.il2.ai.World.Sun().ToSun.z < -0.15F)
        {
            float f4 = (-com.maddox.il2.ai.World.Sun().ToSun.z - 0.15F) / 0.13F;
            if(f4 >= 1.0F)
                f4 = 1.0F;
            if((actor instanceof com.maddox.il2.objects.air.Aircraft) && com.maddox.rts.Time.current() - ((com.maddox.il2.objects.air.Aircraft)actor).tmSearchlighted < 1000L)
                f4 = 0.0F;
            f2 += 12F * f4;
        }
        float f5 = (float)actor.getSpeed(null) - 10F;
        if(f5 > 0.0F)
        {
            float f6 = 83.33334F;
            f5 = f5 < f6 ? f5 / f6 : 1.0F;
            f2 += f5 * prop.FAST_TARGETS_ANGLE_ERROR;
        }
        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        if(!((com.maddox.il2.ai.BulletAimer)gun).FireDirection(point3d1, point3d, vector3d))
            return 0;
        float f3;
        if(flag)
        {
            f3 = 99999F;
            d1 = 99999F;
        } else
        {
            f3 = prop.HEAD_MAX_YAW_SPEED;
            d1 = prop.GUN_MAX_PITCH_SPEED;
        }
        int j = aim.setRotationForTargeting(this, pos.getAbs().getOrient(), point3d1, headYaw, gunPitch, vector3d, f2, f1, prop.HEAD_YAW_RANGE, prop.GUN_MIN_PITCH, prop.GUN_MAX_PITCH, f3, d1, 0.0F);
        return j;
    }

    public void singleShot(com.maddox.il2.ai.ground.Aim aim)
    {
        gun.shots(1);
    }

    public void startFire(com.maddox.il2.ai.ground.Aim aim)
    {
        gun.shots(-1);
    }

    public void continueFire(com.maddox.il2.ai.ground.Aim aim)
    {
    }

    public void stopFire(com.maddox.il2.ai.ground.Aim aim)
    {
        gun.shots(0);
    }

    private com.maddox.il2.objects.vehicles.artillery.ArtilleryProperties prop;
    private boolean nearAirfield;
    private boolean dontShoot;
    private long time_lastCheckShoot;
    private static final int DELAY_CHECK_SHOOT = 12000;
    private static final int DIST_TO_FRIEND_PLANES = 4000;
    private static final int DIST_TO_AIRFIELD = 2000;
    private float heightAboveLandSurface;
    private float heightAboveLandSurface2;
    protected com.maddox.il2.objects.weapons.Gun gun;
    private com.maddox.il2.ai.ground.Aim aime;
    private float headYaw;
    private float gunPitch;
    private long startDelay;
    public int dying;
    static final int DYING_NONE = 0;
    static final int DYING_DEAD = 1;
    private short deathSeed;
    private long respawnDelay;
    private long hideTmr;
    private static long delay_hide_ticks = 0L;
    public float RADIUS_HIDE;
    public static float new_RADIUS_HIDE = 0.0F;
    private static com.maddox.il2.objects.vehicles.artillery.ArtilleryProperties constr_arg1 = null;
    private static com.maddox.il2.engine.ActorSpawnArg constr_arg2 = null;
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.JGP.Point3d p1 = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();
    private static com.maddox.JGP.Vector3f n = new Vector3f();
    private static com.maddox.JGP.Vector3d tmpv = new Vector3d();
    private com.maddox.rts.NetMsgFiltered outCommand;




















}
