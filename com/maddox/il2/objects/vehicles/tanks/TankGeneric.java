// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TankGeneric.java

package com.maddox.il2.objects.vehicles.tanks;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector2d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Aimer;
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
import com.maddox.il2.ai.ground.Aim;
import com.maddox.il2.ai.ground.ChiefGround;
import com.maddox.il2.ai.ground.HunterInterface;
import com.maddox.il2.ai.ground.Moving;
import com.maddox.il2.ai.ground.Obstacle;
import com.maddox.il2.ai.ground.Predator;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.ai.ground.StaticObstacle;
import com.maddox.il2.ai.ground.TgtTank;
import com.maddox.il2.ai.ground.UnitData;
import com.maddox.il2.ai.ground.UnitInPackedForm;
import com.maddox.il2.ai.ground.UnitInterface;
import com.maddox.il2.ai.ground.UnitMove;
import com.maddox.il2.ai.ground.UnitSpawn;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.bridges.LongBridge;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.humans.Soldier;
import com.maddox.il2.objects.weapons.CannonMidrangeGeneric;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Finger;
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
import com.maddox.sound.SoundFX;
import com.maddox.util.TableFunction2;
import java.io.IOException;
import java.io.PrintStream;

public abstract class TankGeneric extends com.maddox.il2.engine.ActorHMesh
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.engine.MsgCollisionListener, com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener, com.maddox.il2.ai.ground.Predator, com.maddox.il2.ai.ground.Obstacle, com.maddox.il2.ai.ground.UnitInterface, com.maddox.il2.ai.ground.HunterInterface
{
    public static class SPAWN
        implements com.maddox.il2.ai.ground.UnitSpawn
    {

        private static float getF(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.String s1, float f, float f1)
        {
            float f2 = sectfile.get(s, s1, -9865.345F);
            if(f2 == -9865.345F || f2 < f || f2 > f1)
            {
                if(f2 == -9865.345F)
                    java.lang.System.out.println("Tank: Parameter [" + s + "]:<" + s1 + "> " + "not found");
                else
                    java.lang.System.out.println("Tank: Value of [" + s + "]:<" + s1 + "> (" + f2 + ")" + " is out of range (" + f + ";" + f1 + ")");
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
                java.lang.System.out.print("Tank: Parameter [" + s + "]:<" + s1 + "> ");
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

        private static com.maddox.il2.objects.vehicles.tanks.TankProperties LoadTankProperties(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.Class class1)
        {
            com.maddox.il2.objects.vehicles.tanks.TankProperties tankproperties = new TankProperties();
            java.lang.String s1 = com.maddox.il2.objects.vehicles.tanks.SPAWN.getS(sectfile, s, "PanzerType", null);
            if(s1 == null)
                s1 = "Tank";
            tankproperties.fnShotPanzer = com.maddox.il2.ai.TableFunctions.GetFunc2(s1 + "ShotPanzer");
            tankproperties.fnExplodePanzer = com.maddox.il2.ai.TableFunctions.GetFunc2(s1 + "ExplodePanzer");
            tankproperties.PANZER_TNT_TYPE = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "PanzerSubtype", 0.0F, 100F);
            tankproperties.meshSummer = com.maddox.il2.objects.vehicles.tanks.SPAWN.getS(sectfile, s, "MeshSummer");
            tankproperties.meshDesert = com.maddox.il2.objects.vehicles.tanks.SPAWN.getS(sectfile, s, "MeshDesert", tankproperties.meshSummer);
            tankproperties.meshWinter = com.maddox.il2.objects.vehicles.tanks.SPAWN.getS(sectfile, s, "MeshWinter", tankproperties.meshSummer);
            tankproperties.meshSummer1 = com.maddox.il2.objects.vehicles.tanks.SPAWN.getS(sectfile, s, "MeshSummerDamage", null);
            tankproperties.meshDesert1 = com.maddox.il2.objects.vehicles.tanks.SPAWN.getS(sectfile, s, "MeshDesertDamage", tankproperties.meshSummer1);
            tankproperties.meshWinter1 = com.maddox.il2.objects.vehicles.tanks.SPAWN.getS(sectfile, s, "MeshWinterDamage", tankproperties.meshSummer1);
            float f = (tankproperties.meshSummer1 != null ? 0 : 1) + (tankproperties.meshDesert1 != null ? 0 : 1) + (tankproperties.meshWinter1 != null ? 0 : 1);
            if(f != 0 && f != 3)
            {
                java.lang.System.out.println("Tank: Uncomplete set of damage meshes for '" + s + "'");
                throw new RuntimeException("Can't register tank object");
            }
            tankproperties.explodeName = com.maddox.il2.objects.vehicles.tanks.SPAWN.getS(sectfile, s, "Explode", "Tank");
            tankproperties.PANZER_BODY_FRONT = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "PanzerBodyFront", 0.001F, 9.999F);
            if(sectfile.get(s, "PanzerBodyBack", -9865.345F) == -9865.345F)
            {
                tankproperties.PANZER_BODY_BACK = tankproperties.PANZER_BODY_FRONT;
                tankproperties.PANZER_BODY_SIDE = tankproperties.PANZER_BODY_FRONT;
                tankproperties.PANZER_BODY_TOP = tankproperties.PANZER_BODY_FRONT;
            } else
            {
                tankproperties.PANZER_BODY_BACK = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "PanzerBodyBack", 0.001F, 9.999F);
                tankproperties.PANZER_BODY_SIDE = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "PanzerBodySide", 0.001F, 9.999F);
                tankproperties.PANZER_BODY_TOP = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "PanzerBodyTop", 0.001F, 9.999F);
            }
            if(sectfile.get(s, "PanzerHead", -9865.345F) == -9865.345F)
                tankproperties.PANZER_HEAD = tankproperties.PANZER_BODY_FRONT;
            else
                tankproperties.PANZER_HEAD = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "PanzerHead", 0.001F, 9.999F);
            if(sectfile.get(s, "PanzerHeadTop", -9865.345F) == -9865.345F)
                tankproperties.PANZER_HEAD_TOP = tankproperties.PANZER_BODY_TOP;
            else
                tankproperties.PANZER_HEAD_TOP = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "PanzerHeadTop", 0.001F, 9.999F);
            f = java.lang.Math.min(java.lang.Math.min(tankproperties.PANZER_BODY_BACK, tankproperties.PANZER_BODY_TOP), java.lang.Math.min(tankproperties.PANZER_BODY_SIDE, tankproperties.PANZER_HEAD_TOP));
            tankproperties.HITBY_MASK = f <= 0.015F ? -1 : -2;
            java.lang.String s2 = "com.maddox.il2.objects.weapons." + com.maddox.il2.objects.vehicles.tanks.SPAWN.getS(sectfile, s, "Gun");
            try
            {
                tankproperties.gunClass = java.lang.Class.forName(s2);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println("Tank: Can't find gun class '" + s2 + "'");
                throw new RuntimeException("Can't register tank object");
            }
            tankproperties.WEAPONS_MASK = com.maddox.il2.objects.weapons.Gun.getProperties(tankproperties.gunClass).weaponType;
            if(tankproperties.WEAPONS_MASK == 0)
            {
                java.lang.System.out.println("Tank: Undefined weapon type in gun class '" + s2 + "'");
                throw new RuntimeException("Can't register tank object");
            }
            tankproperties.MAX_SHELLS = (int)com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "NumShells", -1F, 30000F);
            tankproperties.ATTACK_MAX_DISTANCE = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "AttackMaxDistance", 6F, 12000F);
            float f1 = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "HeadYawHalfRange", 0.0F, 180F);
            tankproperties.HEAD_YAW_RANGE.set(-f1, f1);
            tankproperties.GUN_MIN_PITCH = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "GunMinPitch", -15F, 85F);
            tankproperties.GUN_STD_PITCH = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "GunStdPitch", -15F, 89.9F);
            tankproperties.GUN_MAX_PITCH = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "GunMaxPitch", 0.0F, 89.9F);
            tankproperties.HEAD_MAX_YAW_SPEED = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "HeadMaxYawSpeed", 0.1F, 999F);
            tankproperties.GUN_MAX_PITCH_SPEED = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "GunMaxPitchSpeed", 0.1F, 999F);
            tankproperties.DELAY_AFTER_SHOOT = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "DelayAfterShoot", 0.0F, 999F);
            tankproperties.CHAINFIRE_TIME = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "ChainfireTime", 0.0F, 600F);
            tankproperties.ATTACK_FAST_TARGETS = false;
            float f2 = sectfile.get(s, "FireFastTargets", -9865.345F);
            if(f2 != -9865.345F)
                tankproperties.ATTACK_FAST_TARGETS = f2 > 0.5F;
            float f3 = sectfile.get(s, "FastTargetsAngleError", -9865.345F);
            if(f3 <= 0.0F)
                f3 = 0.0F;
            else
            if(f3 >= 45F)
                f3 = 45F;
            tankproperties.FAST_TARGETS_ANGLE_ERROR = f3;
            tankproperties.STAY_WHEN_FIRE = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "StayWhenFire", 0.0F, 1.0F) > 0.0F;
            tankproperties.SPEED_AVERAGE = com.maddox.il2.objects.vehicles.tanks.TankGeneric.KmHourToMSec(com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "SpeedAverage", 1.0F, 100F));
            tankproperties.SPEED_MAX = com.maddox.il2.objects.vehicles.tanks.TankGeneric.KmHourToMSec(com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "SpeedMax", 1.0F, 100F));
            tankproperties.SPEED_BACK = com.maddox.il2.objects.vehicles.tanks.TankGeneric.KmHourToMSec(com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "SpeedBack", 0.5F, 100F));
            tankproperties.ROT_SPEED_MAX = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "RotSpeedMax", 0.1F, 800F);
            tankproperties.ROT_INVIS_ANG = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "RotInvisAng", 0.0F, 360F);
            tankproperties.BEST_SPACE = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "BestSpace", 0.1F, 100F);
            tankproperties.AFTER_COLLISION_DIST = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "AfterCollisionDist", 0.1F, 80F);
            tankproperties.COMMAND_INTERVAL = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "CommandInterval", 0.5F, 100F);
            tankproperties.STAY_INTERVAL = com.maddox.il2.objects.vehicles.tanks.SPAWN.getF(sectfile, s, "StayInterval", 0.1F, 200F);
            tankproperties.soundMove = com.maddox.il2.objects.vehicles.tanks.SPAWN.getS(sectfile, s, "SoundMove");
            com.maddox.rts.Property.set(class1, "iconName", "icons/" + com.maddox.il2.objects.vehicles.tanks.SPAWN.getS(sectfile, s, "Icon") + ".mat");
            com.maddox.rts.Property.set(class1, "meshName", tankproperties.meshSummer);
            com.maddox.rts.Property.set(class1, "speed", tankproperties.SPEED_AVERAGE);
            return tankproperties;
        }

        public java.lang.Class unitClass()
        {
            return cls;
        }

        public com.maddox.il2.engine.Actor unitSpawn(int i, int j, com.maddox.il2.engine.Actor actor)
        {
            proper.codeName = i;
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
            com.maddox.il2.objects.vehicles.tanks.TankGeneric tankgeneric = null;
            try
            {
                com.maddox.il2.objects.vehicles.tanks.TankGeneric.constr_arg1 = proper;
                com.maddox.il2.objects.vehicles.tanks.TankGeneric.constr_arg2 = actor;
                tankgeneric = (com.maddox.il2.objects.vehicles.tanks.TankGeneric)cls.newInstance();
                com.maddox.il2.objects.vehicles.tanks.TankGeneric.constr_arg1 = null;
                com.maddox.il2.objects.vehicles.tanks.TankGeneric.constr_arg2 = null;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.vehicles.tanks.TankGeneric.constr_arg1 = null;
                com.maddox.il2.objects.vehicles.tanks.TankGeneric.constr_arg2 = null;
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("SPAWN: Can't create Tank object [class:" + cls.getName() + "]");
                return null;
            }
            return tankgeneric;
        }

        public java.lang.Class cls;
        public com.maddox.il2.objects.vehicles.tanks.TankProperties proper;

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
                proper = com.maddox.il2.objects.vehicles.tanks.SPAWN.LoadTankProperties(com.maddox.il2.objects.Statics.getTechnicsFile(), s1, class1);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("Problem in tank spawn: " + class1.getName());
            }
            cls = class1;
            com.maddox.rts.Spawn.add(cls, this);
        }
    }

    class Move extends com.maddox.il2.engine.Interpolate
    {

        public boolean tick()
        {
            if(dying != 0)
            {
                neverDust();
                if(dying == 2)
                    return false;
                if(dyingDelay-- <= 0L)
                {
                    ShowExplode();
                    MakeCrush();
                    return false;
                }
                if(mov.rotatCurTime > 0L)
                {
                    mov.rotatCurTime--;
                    float f = 1.0F - (float)mov.rotatCurTime / (float)mov.rotatTotTime;
                    pos.getAbs(com.maddox.il2.objects.vehicles.tanks.TankGeneric.o);
                    com.maddox.il2.objects.vehicles.tanks.TankGeneric.o.setYaw(mov.angles.getDeg(f));
                    if(mov.normal.z < 0.0F)
                    {
                        com.maddox.il2.engine.Engine.land().N(mov.srcPos.x, mov.srcPos.y, com.maddox.il2.objects.vehicles.tanks.TankGeneric.n);
                        com.maddox.il2.objects.vehicles.tanks.TankGeneric.o.orient(com.maddox.il2.objects.vehicles.tanks.TankGeneric.n);
                    } else
                    {
                        com.maddox.il2.objects.vehicles.tanks.TankGeneric.o.orient(mov.normal);
                    }
                    pos.setAbs(com.maddox.il2.objects.vehicles.tanks.TankGeneric.o);
                }
                return true;
            }
            boolean flag = mov.moveCurTime < 0L && mov.rotatCurTime < 0L;
            if(isNetMirror() && flag)
            {
                mov.switchToStay(30F);
                flag = false;
            }
            if(flag)
            {
                com.maddox.il2.ai.ground.ChiefGround chiefground = (com.maddox.il2.ai.ground.ChiefGround)getOwner();
                float f2 = -1F;
                com.maddox.il2.ai.ground.UnitMove unitmove;
                if(collisionStage == 0)
                {
                    if(prop.meshName2 != null)
                    {
                        com.maddox.il2.objects.vehicles.tanks.TankGeneric.p.x = com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rnd(-0.29999999999999999D, 0.29999999999999999D);
                        com.maddox.il2.objects.vehicles.tanks.TankGeneric.p.y = com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rnd(-0.29999999999999999D, 0.29999999999999999D);
                        com.maddox.il2.objects.vehicles.tanks.TankGeneric.p.z = 1.0D;
                        unitmove = chiefground.AskMoveCommand(actor, com.maddox.il2.objects.vehicles.tanks.TankGeneric.p, obs);
                    } else
                    {
                        unitmove = chiefground.AskMoveCommand(actor, null, obs);
                    }
                } else
                if(collisionStage == 1)
                {
                    obs.collision(collidee, chiefground, udata);
                    collidee = null;
                    float f3 = com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rnd(-70F, 70F);
                    com.maddox.JGP.Vector2d vector2d = com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rotate(collisVector, f3);
                    vector2d.scale((double)prop.AFTER_COLLISION_DIST * com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rnd(0.87D, 1.75D));
                    com.maddox.il2.objects.vehicles.tanks.TankGeneric.p.set(vector2d.x, vector2d.y, -1D);
                    unitmove = chiefground.AskMoveCommand(actor, com.maddox.il2.objects.vehicles.tanks.TankGeneric.p, obs);
                    collisionStage = 2;
                    f2 = prop.SPEED_BACK;
                } else
                {
                    float f4 = com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rnd(0.0F, 359.99F);
                    com.maddox.JGP.Vector2d vector2d1 = com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rotate(collisVector, f4);
                    vector2d1.scale((double)prop.AFTER_COLLISION_DIST * com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rnd(0.20000000000000001D, 0.59999999999999998D));
                    com.maddox.il2.objects.vehicles.tanks.TankGeneric.p.set(vector2d1.x, vector2d1.y, 1.0D);
                    unitmove = chiefground.AskMoveCommand(actor, com.maddox.il2.objects.vehicles.tanks.TankGeneric.p, obs);
                    collisionStage = 0;
                }
                mov.set(unitmove, actor, prop.SPEED_MAX, f2, prop.ROT_SPEED_MAX, prop.ROT_INVIS_ANG);
                if(StayWhenFire())
                    if(Head360())
                    {
                        if(aime.isInFiringMode())
                            mov.switchToStay(1.3F);
                    } else
                    if(aime.isInAimingMode())
                        mov.switchToStay(1.3F);
                if(isNetMaster())
                    send_MoveCommand(mov, f2);
            }
            aime.tick_();
            if(dust != null)
                dust._setIntesity(mov.movingForward ? 1.0F : 0.0F);
            if(mov.dstPos == null)
            {
                mov.moveCurTime--;
                if(engineSFX != null && engineSTimer > 0 && --engineSTimer == 0)
                    engineSFX.stop();
                return true;
            }
            if(engineSFX != null)
                if(engineSTimer == 0)
                {
                    engineSFX.play();
                    engineSTimer = (int)com.maddox.il2.objects.vehicles.tanks.TankGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rnd(10F, 12F));
                } else
                if(engineSTimer < ticksIn8secs)
                    engineSTimer = (int)com.maddox.il2.objects.vehicles.tanks.TankGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rnd(10F, 12F));
            pos.getAbs(com.maddox.il2.objects.vehicles.tanks.TankGeneric.o);
            boolean flag1 = false;
            if(mov.rotatCurTime > 0L)
            {
                mov.rotatCurTime--;
                float f1 = 1.0F - (float)mov.rotatCurTime / (float)mov.rotatTotTime;
                com.maddox.il2.objects.vehicles.tanks.TankGeneric.o.setYaw(mov.angles.getDeg(f1));
                flag1 = true;
                if(mov.rotatCurTime <= 0L)
                {
                    mov.rotatCurTime = -1L;
                    mov.rotatingInPlace = false;
                }
            }
            if(!mov.rotatingInPlace && mov.moveCurTime > 0L)
            {
                mov.moveCurTime--;
                double d = 1.0D - (double)mov.moveCurTime / (double)mov.moveTotTime;
                com.maddox.il2.objects.vehicles.tanks.TankGeneric.p.x = mov.srcPos.x * (1.0D - d) + mov.dstPos.x * d;
                com.maddox.il2.objects.vehicles.tanks.TankGeneric.p.y = mov.srcPos.y * (1.0D - d) + mov.dstPos.y * d;
                if(mov.normal.z < 0.0F)
                {
                    com.maddox.il2.objects.vehicles.tanks.TankGeneric.p.z = com.maddox.il2.engine.Engine.land().HQ(com.maddox.il2.objects.vehicles.tanks.TankGeneric.p.x, com.maddox.il2.objects.vehicles.tanks.TankGeneric.p.y) + (double)HeightAboveLandSurface();
                    com.maddox.il2.engine.Engine.land().N(com.maddox.il2.objects.vehicles.tanks.TankGeneric.p.x, com.maddox.il2.objects.vehicles.tanks.TankGeneric.p.y, com.maddox.il2.objects.vehicles.tanks.TankGeneric.n);
                } else
                {
                    com.maddox.il2.objects.vehicles.tanks.TankGeneric.p.z = mov.srcPos.z * (1.0D - d) + mov.dstPos.z * d;
                }
                flag1 = false;
                pos.setAbs(com.maddox.il2.objects.vehicles.tanks.TankGeneric.p);
                if(mov.moveCurTime <= 0L)
                    mov.moveCurTime = -1L;
            }
            if(mov.normal.z < 0.0F)
            {
                if(flag1)
                    com.maddox.il2.engine.Engine.land().N(mov.srcPos.x, mov.srcPos.y, com.maddox.il2.objects.vehicles.tanks.TankGeneric.n);
                com.maddox.il2.objects.vehicles.tanks.TankGeneric.o.orient(com.maddox.il2.objects.vehicles.tanks.TankGeneric.n);
            } else
            {
                com.maddox.il2.objects.vehicles.tanks.TankGeneric.o.orient(mov.normal);
            }
            pos.setAbs(com.maddox.il2.objects.vehicles.tanks.TankGeneric.o);
            return true;
        }

        Move()
        {
        }
    }

    class Mirror extends com.maddox.il2.engine.ActorNet
    {

        private boolean handleGuaranted(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            byte byte0 = netmsginput.readByte();
            if(isMirrored())
            {
                int i = 0;
                if(byte0 == 68 || byte0 == 65)
                    i = 1;
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(netmsginput, i);
                post(netmsgguaranted);
            }
            com.maddox.JGP.Point3d point3d = com.maddox.il2.objects.vehicles.tanks.TankGeneric.readPackedPos(netmsginput);
            com.maddox.il2.engine.Orient orient = com.maddox.il2.objects.vehicles.tanks.TankGeneric.readPackedOri(netmsginput);
            float f = com.maddox.il2.objects.vehicles.tanks.TankGeneric.readPackedAng(netmsginput);
            float f1 = com.maddox.il2.objects.vehicles.tanks.TankGeneric.readPackedAng(netmsginput);
            setPosition(point3d, orient, f, f1);
            mov.switchToStay(20F);
            switch(byte0)
            {
            case 73: // 'I'
            case 105: // 'i'
                if(dying != 0)
                    java.lang.System.out.println("Tank is dead at init stage");
                if(byte0 == 105)
                    DieMirror(null, false);
                break;

            case 65: // 'A'
                com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                com.maddox.il2.engine.Actor actor = netobj != null ? ((com.maddox.il2.engine.ActorNet)netobj).actor() : null;
                doAbsoluteDeath(actor);
                break;

            case 67: // 'C'
                doCollisionDeath();
                break;

            case 68: // 'D'
                if(dying == 0)
                {
                    com.maddox.rts.NetObj netobj1 = netmsginput.readNetObj();
                    com.maddox.il2.engine.Actor actor1 = netobj1 != null ? ((com.maddox.il2.engine.ActorNet)netobj1).actor() : null;
                    DieMirror(actor1, true);
                }
                break;

            default:
                java.lang.System.out.println("TankGeneric: Unknown G message (" + byte0 + ")");
                return false;
            }
            return true;
        }

        private boolean handleNonguaranted(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            byte byte0 = netmsginput.readByte();
            switch(byte0)
            {
            case 68: // 'D'
                out.unLockAndSet(netmsginput, 1);
                out.setIncludeTime(false);
                postRealTo(com.maddox.rts.Message.currentRealTime(), masterChannel(), out);
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
                float f = netmsginput.readFloat();
                float f1 = 0.001F * (float)(com.maddox.rts.Message.currentGameTime() - com.maddox.rts.Time.current()) + f;
                int k = netmsginput.readUnsignedByte();
                Fire_Mirror(actor1, k, f1);
                break;

            case 83: // 'S'
                if(isMirrored())
                {
                    out.unLockAndSet(netmsginput, 0);
                    out.setIncludeTime(false);
                    postReal(com.maddox.rts.Message.currentRealTime(), out);
                }
                mov.switchToStay(10F);
                break;

            case 77: // 'M'
            case 109: // 'm'
                boolean flag = byte0 == 77;
                if(isMirrored())
                {
                    out.unLockAndSet(netmsginput, 0);
                    out.setIncludeTime(true);
                    postReal(com.maddox.rts.Message.currentRealTime(), out);
                }
                com.maddox.JGP.Point3d point3d = com.maddox.il2.objects.vehicles.tanks.TankGeneric.readPackedPos(netmsginput);
                com.maddox.JGP.Vector3f vector3f = new Vector3f(0.0F, 0.0F, 0.0F);
                vector3f.z = com.maddox.il2.objects.vehicles.tanks.TankGeneric.readPackedNormal(netmsginput);
                if(vector3f.z >= 0.0F)
                {
                    vector3f.x = com.maddox.il2.objects.vehicles.tanks.TankGeneric.readPackedNormal(netmsginput);
                    vector3f.y = com.maddox.il2.objects.vehicles.tanks.TankGeneric.readPackedNormal(netmsginput);
                    float f2 = vector3f.length();
                    if(f2 > 0.001F)
                        vector3f.scale(1.0F / f2);
                    else
                        vector3f.set(0.0F, 0.0F, 1.0F);
                }
                int j = netmsginput.readUnsignedShort();
                float f3 = 0.001F * (float)((com.maddox.rts.Message.currentGameTime() - com.maddox.rts.Time.current()) + (long)j);
                if(f3 <= 0.0F)
                    f3 = 0.1F;
                com.maddox.il2.ai.ground.UnitMove unitmove = new UnitMove(0.0F, point3d, f3, vector3f, -1F);
                if(dying == 0)
                    mov.set(unitmove, actor(), 2.0F * prop.SPEED_MAX, flag ? 2.0F * prop.SPEED_BACK : -1F, 1.3F * prop.ROT_SPEED_MAX, 1.1F * prop.ROT_INVIS_ANG);
                break;

            default:
                java.lang.System.out.println("TankGeneric: Unknown NG message");
                return false;
            }
            return true;
        }

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            if(netmsginput.isGuaranted())
                return handleGuaranted(netmsginput);
            else
                return handleNonguaranted(netmsginput);
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
                return false;
            byte byte0 = netmsginput.readByte();
            switch(byte0)
            {
            case 68: // 'D'
                if(dying == 0)
                {
                    com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                    com.maddox.il2.engine.Actor actor = netobj != null ? ((com.maddox.il2.engine.ActorNet)netobj).actor() : null;
                    Die(actor, true);
                }
                break;

            case 67: // 'C'
                collisionDeath();
                break;

            default:
                java.lang.System.out.println("TankGeneric: Unknown M message (" + byte0 + ")");
                return false;
            }
            return true;
        }

        public Master(com.maddox.il2.engine.Actor actor)
        {
            super(actor);
        }
    }

    protected static class TankProperties
        implements java.lang.Cloneable
    {

        public java.lang.Object clone()
        {
            return super.clone();
            java.lang.Exception exception;
            exception;
            return null;
        }

        public int codeName;
        public java.lang.String meshName;
        public java.lang.String meshName2;
        public java.lang.String meshSummer;
        public java.lang.String meshDesert;
        public java.lang.String meshWinter;
        public java.lang.String meshSummer1;
        public java.lang.String meshDesert1;
        public java.lang.String meshWinter1;
        public java.lang.Class gunClass;
        public java.lang.String soundMove;
        public com.maddox.util.TableFunction2 fnShotPanzer;
        public com.maddox.util.TableFunction2 fnExplodePanzer;
        public float PANZER_BODY_FRONT;
        public float PANZER_BODY_BACK;
        public float PANZER_BODY_SIDE;
        public float PANZER_BODY_TOP;
        public float PANZER_HEAD;
        public float PANZER_HEAD_TOP;
        public float PANZER_TNT_TYPE;
        public int WEAPONS_MASK;
        public int HITBY_MASK;
        public java.lang.String explodeName;
        public int MAX_SHELLS;
        public float ATTACK_MAX_DISTANCE;
        public float GUN_MIN_PITCH;
        public float GUN_STD_PITCH;
        public float GUN_MAX_PITCH;
        public com.maddox.il2.ai.AnglesRange HEAD_YAW_RANGE;
        public float HEAD_MAX_YAW_SPEED;
        public float GUN_MAX_PITCH_SPEED;
        public float DELAY_AFTER_SHOOT;
        public float CHAINFIRE_TIME;
        public boolean ATTACK_FAST_TARGETS;
        public float FAST_TARGETS_ANGLE_ERROR;
        public boolean STAY_WHEN_FIRE;
        public float SPEED_AVERAGE;
        public float SPEED_MAX;
        public float SPEED_BACK;
        public float ROT_SPEED_MAX;
        public float ROT_INVIS_ANG;
        public float BEST_SPACE;
        public float AFTER_COLLISION_DIST;
        public float COMMAND_INTERVAL;
        public float STAY_INTERVAL;

        protected TankProperties()
        {
            codeName = 0;
            meshName = "3do/tanks/NameNotSpecified.him";
            meshName2 = null;
            meshSummer = null;
            meshDesert = null;
            meshWinter = null;
            meshSummer1 = null;
            meshDesert1 = null;
            meshWinter1 = null;
            gunClass = null;
            soundMove = "models.Tank";
            fnShotPanzer = null;
            fnExplodePanzer = null;
            PANZER_BODY_FRONT = 0.001F;
            PANZER_BODY_BACK = 0.001F;
            PANZER_BODY_SIDE = 0.001F;
            PANZER_BODY_TOP = 0.001F;
            PANZER_HEAD = 0.001F;
            PANZER_HEAD_TOP = 0.001F;
            PANZER_TNT_TYPE = 1.0F;
            WEAPONS_MASK = 4;
            HITBY_MASK = -2;
            explodeName = null;
            MAX_SHELLS = 1;
            ATTACK_MAX_DISTANCE = 1.0F;
            GUN_MIN_PITCH = -30F;
            GUN_STD_PITCH = -29.5F;
            GUN_MAX_PITCH = -29F;
            HEAD_YAW_RANGE = new AnglesRange(-1F, 1.0F);
            HEAD_MAX_YAW_SPEED = 3600F;
            GUN_MAX_PITCH_SPEED = 300F;
            DELAY_AFTER_SHOOT = 0.5F;
            CHAINFIRE_TIME = 0.0F;
            ATTACK_FAST_TARGETS = true;
            FAST_TARGETS_ANGLE_ERROR = 0.0F;
            STAY_WHEN_FIRE = true;
            SPEED_AVERAGE = com.maddox.il2.objects.vehicles.tanks.TankGeneric.KmHourToMSec(1.0F);
            SPEED_MAX = com.maddox.il2.objects.vehicles.tanks.TankGeneric.KmHourToMSec(2.0F);
            SPEED_BACK = com.maddox.il2.objects.vehicles.tanks.TankGeneric.KmHourToMSec(1.0F);
            ROT_SPEED_MAX = 3.6F;
            ROT_INVIS_ANG = 360F;
            BEST_SPACE = 2.0F;
            AFTER_COLLISION_DIST = 0.1F;
            COMMAND_INTERVAL = 20F;
            STAY_INTERVAL = 30F;
        }
    }


    public int getCRC(int i)
    {
        i = super.getCRC(i);
        i = com.maddox.rts.Finger.incInt(i, heightAboveLandSurface);
        i = com.maddox.rts.Finger.incInt(i, headYaw);
        i = com.maddox.rts.Finger.incInt(i, gunPitch);
        i = com.maddox.rts.Finger.incInt(i, collisionStage);
        i = com.maddox.rts.Finger.incInt(i, dying);
        i = com.maddox.rts.Finger.incInt(i, codeOfUnderlyingBridgeSegment);
        if(mov != null)
        {
            i = com.maddox.rts.Finger.incInt(i, mov.rotatingInPlace);
            i = com.maddox.rts.Finger.incInt(i, mov.srcPos.x);
            i = com.maddox.rts.Finger.incInt(i, mov.srcPos.y);
            i = com.maddox.rts.Finger.incInt(i, mov.srcPos.z);
            if(mov.dstPos != null)
            {
                i = com.maddox.rts.Finger.incInt(i, mov.dstPos.x);
                i = com.maddox.rts.Finger.incInt(i, mov.dstPos.y);
                i = com.maddox.rts.Finger.incInt(i, mov.dstPos.z);
            }
        }
        if(aime != null)
        {
            i = com.maddox.rts.Finger.incInt(i, aime.timeTot);
            i = com.maddox.rts.Finger.incInt(i, aime.timeCur);
        }
        return i;
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

    public static final float KmHourToMSec(float f)
    {
        return f / 3.6F;
    }

    private static final float TicksToSecs(long l)
    {
        if(l < 0L)
            l = 0L;
        return (float)l * com.maddox.rts.Time.tickLenFs();
    }

    private static final long SecsToTicks(float f)
    {
        long l = (long)(0.5D + (double)(f / com.maddox.rts.Time.tickLenFs()));
        return l >= 1L ? l : 1L;
    }

    public static final com.maddox.JGP.Vector2d Rotate(com.maddox.JGP.Vector2d vector2d, float f)
    {
        float f1 = com.maddox.JGP.Geom.sinDeg(f);
        float f2 = com.maddox.JGP.Geom.cosDeg(f);
        return new Vector2d((double)f2 * vector2d.x - (double)f1 * vector2d.y, (double)f1 * vector2d.x + (double)f2 * vector2d.y);
    }

    protected final boolean Head360()
    {
        return prop.HEAD_YAW_RANGE.fullcircle();
    }

    protected final boolean StayWhenFire()
    {
        return prop.STAY_WHEN_FIRE;
    }

    public void msgCollisionRequest(com.maddox.il2.engine.Actor actor, boolean aflag[])
    {
        if(actor instanceof com.maddox.il2.objects.bridges.BridgeSegment)
            aflag[0] = false;
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if(dying != 0)
            return;
        if(actor instanceof com.maddox.il2.objects.humans.Soldier)
            return;
        if(isNetMirror())
            return;
        if(collisionStage != 0)
            return;
        if(aime.isInFiringMode())
            return;
        mov.switchToAsk();
        collisionStage = 1;
        collidee = actor;
        com.maddox.JGP.Point3d point3d = pos.getAbsPoint();
        com.maddox.JGP.Point3d point3d1 = actor.pos.getAbsPoint();
        collisVector.set(point3d.x - point3d1.x, point3d.y - point3d1.y);
        if(collisVector.length() >= 9.9999999999999995E-007D)
        {
            collisVector.normalize();
        } else
        {
            float f = com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rnd(0.0F, 359.99F);
            collisVector.set(com.maddox.JGP.Geom.sinDeg(f), com.maddox.JGP.Geom.cosDeg(f));
        }
        ((com.maddox.il2.ai.ground.ChiefGround)getOwner()).CollisionOccured(this, actor);
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        shot.bodyMaterial = 2;
        if(dying != 0)
            return;
        if(isNetMirror() && shot.isMirage())
            return;
        if(shot.power <= 0.0F)
            return;
        if(shot.powerType == 1)
            if(RndB(0.07692308F))
            {
                return;
            } else
            {
                Die(shot.initiator, false);
                return;
            }
        float f = com.maddox.il2.ai.Shot.panzerThickness(pos.getAbsOrient(), shot.v, shot.chunkName.equalsIgnoreCase("Head"), prop.PANZER_BODY_FRONT, prop.PANZER_BODY_SIDE, prop.PANZER_BODY_BACK, prop.PANZER_BODY_TOP, prop.PANZER_HEAD, prop.PANZER_HEAD_TOP);
        f *= com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rnd(0.93F, 1.07F);
        float f1 = prop.fnShotPanzer.Value(shot.power, f);
        if(f1 < 1000F && (f1 <= 1.0F || RndB(1.0F / f1)))
            Die(shot.initiator, false);
    }

    public static boolean splintersKill(com.maddox.il2.ai.Explosion explosion, com.maddox.util.TableFunction2 tablefunction2, float f, float f1, com.maddox.il2.engine.ActorMesh actormesh, float f2, float f3, float f4, 
            float f5, float f6, float f7, float f8, float f9)
    {
        if(explosion.power <= 0.0F)
            return false;
        actormesh.pos.getAbs(p);
        float af[] = new float[2];
        explosion.computeSplintersHit(p, actormesh.mesh().collisionR(), f2, af);
        af[0] *= f * 0.85F + (1.0F - f) * 1.15F;
        int i = (int)af[0];
        if(i <= 0)
            return false;
        com.maddox.JGP.Vector3d vector3d = new Vector3d(p.x - explosion.p.x, p.y - explosion.p.y, p.z - explosion.p.z);
        double d = vector3d.length();
        if(d < 0.0010000000474974513D)
            vector3d.set(1.0D, 0.0D, 0.0D);
        else
            vector3d.scale(1.0D / d);
        float f10 = com.maddox.il2.ai.Shot.panzerThickness(actormesh.pos.getAbsOrient(), vector3d, false, f4, f5, f6, f7, f8, f9);
        float f11 = com.maddox.il2.ai.Shot.panzerThickness(actormesh.pos.getAbsOrient(), vector3d, true, f4, f5, f6, f7, f8, f9);
        int j = (int)((float)i * f3 + 0.5F);
        int k = i - j;
        if(k < 0)
            k = 0;
        com.maddox.il2.ai.Explosion _tmp = explosion;
        float f12 = 0.015F * af[1] * af[1] * 0.5F;
        float f13 = tablefunction2.Value(f12, f10);
        float f14 = tablefunction2.Value(f12, f11);
        if(k > 0 && f13 <= 1.0F || j > 0 && f14 <= 1.0F)
            return true;
        float f15 = 0.0F;
        if(f13 < 1000F)
        {
            float f16 = 1.0F / f13;
            while(k-- > 0) 
                f15 += (1.0F - f15) * f16;
        }
        if(f14 < 1000F)
        {
            float f17 = 1.0F / f14;
            while(j-- > 0) 
                f15 += (1.0F - f15) * f17;
        }
        return f15 > 0.001F && f15 >= f1;
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
            if(com.maddox.il2.objects.vehicles.tanks.TankGeneric.splintersKill(explosion, prop.fnShotPanzer, com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rnd(0.0F, 1.0F), com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rnd(0.0F, 1.0F), this, 0.7F, 0.25F, prop.PANZER_BODY_FRONT, prop.PANZER_BODY_SIDE, prop.PANZER_BODY_BACK, prop.PANZER_BODY_TOP, prop.PANZER_HEAD, prop.PANZER_HEAD_TOP))
                Die(explosion.initiator, false);
            return;
        }
        com.maddox.il2.ai.Explosion _tmp1 = explosion;
        if(explosion.powerType == 2 && explosion.chunkName != null)
        {
            Die(explosion.initiator, false);
            return;
        }
        float f;
        if(explosion.chunkName != null)
            f = 0.5F * explosion.power;
        else
            f = explosion.receivedTNTpower(this);
        f *= com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rnd(0.95F, 1.05F);
        float f1 = prop.fnExplodePanzer.Value(f, prop.PANZER_TNT_TYPE);
        if(f1 < 1000F && (f1 <= 1.0F || RndB(1.0F / f1)))
            Die(explosion.initiator, true);
    }

    private void neverDust()
    {
        if(dust != null)
        {
            dust._finish();
            dust = null;
        }
    }

    private void RunSmoke(float f, float f1)
    {
        boolean flag = RndB(f);
        java.lang.String s = flag ? "SmokeHead" : "SmokeEngine";
        com.maddox.il2.objects.effects.Explosions.runByName("_TankSmoke_", this, s, "", com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rnd(f1, f1 * 1.6F));
    }

    private void ShowExplode()
    {
        com.maddox.il2.objects.effects.Explosions.runByName(prop.explodeName, this, "", "", -1F);
    }

    private void MakeCrush()
    {
        dying = 2;
        com.maddox.JGP.Point3d point3d = pos.getAbsPoint();
        long l = (long)((point3d.x % 2.1000000000000001D) * 221D + (point3d.y % 3.1000000000000001D) * 211D * 211D);
        com.maddox.il2.ai.RangeRandom rangerandom = new RangeRandom(l);
        float af[] = new float[3];
        float af1[] = new float[3];
        af[0] = af[1] = af[2] = 0.0F;
        af1[0] = af1[1] = af1[2] = 0.0F;
        af1[0] = headYaw + rangerandom.nextFloat(-45F, 45F);
        af1[1] = rangerandom.nextFloat(-3F, 0.0F);
        af1[2] = rangerandom.nextFloat(-9F, 9F);
        af[2] = -rangerandom.nextFloat(0.0F, 0.1F);
        hierMesh().chunkSetLocate("Head", af, af1);
        af[0] = af[1] = af[2] = 0.0F;
        af1[0] = af1[1] = af1[2] = 0.0F;
        af1[0] = -(prop.GUN_MIN_PITCH - rangerandom.nextFloat(6F, 10F));
        af1[1] = (rangerandom.nextBoolean() ? 1.0F : -1F) * rangerandom.nextFloat(14F, 25F);
        hierMesh().chunkSetLocate("Gun", af, af1);
        af[0] = af[1] = af[2] = 0.0F;
        af1[0] = af1[1] = af1[2] = 0.0F;
        af1[1] = (rangerandom.nextBoolean() ? 1.0F : -1F) * rangerandom.nextFloat(1.0F, 5F);
        af1[2] = (rangerandom.nextBoolean() ? 1.0F : -1F) * rangerandom.nextFloat(7F, 13F);
        hierMesh().chunkSetLocate("Body", af, af1);
        engineSFX = null;
        engineSTimer = 0x5f5e0ff;
        breakSounds();
        neverDust();
        if(prop.meshName2 == null)
        {
            mesh().makeAllMaterialsDarker(0.22F, 0.35F);
            heightAboveLandSurface2 = heightAboveLandSurface;
            point3d.z -= rangerandom.nextFloat(0.3F, 0.6F);
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
            point3d.z += heightAboveLandSurface2 - heightAboveLandSurface;
        }
        pos.setAbs(point3d);
        pos.reset();
    }

    private void Die(com.maddox.il2.engine.Actor actor, boolean flag)
    {
        if(isNetMirror())
        {
            send_DeathRequest(actor);
            return;
        }
        if(aime != null)
        {
            aime.forgetAll();
            aime = null;
        }
        if(gun != null)
        {
            com.maddox.il2.objects.vehicles.tanks.TankGeneric.destroy(gun);
            gun = null;
        }
        collisionStage = 1;
        int i = ((com.maddox.il2.ai.ground.ChiefGround)getOwner()).getCodeOfBridgeSegment(this);
        if(i >= 0)
        {
            if(com.maddox.il2.objects.bridges.BridgeSegment.isEncodedSegmentDamaged(i))
            {
                absoluteDeath(actor);
                return;
            }
            com.maddox.il2.objects.bridges.LongBridge.AddTraveller(i, this);
            codeOfUnderlyingBridgeSegment = i;
        }
        ((com.maddox.il2.ai.ground.ChiefGround)getOwner()).Detach(this, actor);
        com.maddox.il2.ai.World.onActorDied(this, actor);
        if(isNet() || prop.meshName2 == null)
            flag = true;
        if(!flag)
            flag = RndB(0.35F);
        if(flag)
        {
            ShowExplode();
            RunSmoke(0.3F, 15F);
            if(isNetMaster())
            {
                send_DeathCommand(actor);
                com.maddox.JGP.Point3d point3d = com.maddox.il2.objects.vehicles.tanks.TankGeneric.simplifyPos(pos.getAbsPoint());
                com.maddox.il2.engine.Orient orient = com.maddox.il2.objects.vehicles.tanks.TankGeneric.simplifyOri(pos.getAbsOrient());
                float f = com.maddox.il2.objects.vehicles.tanks.TankGeneric.simplifyAng(headYaw);
                float f1 = com.maddox.il2.objects.vehicles.tanks.TankGeneric.simplifyAng(gunPitch);
                setPosition(point3d, orient, f, f1);
            }
            MakeCrush();
        } else
        {
            dying = 1;
            dyingDelay = com.maddox.il2.objects.vehicles.tanks.TankGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rnd(6F, 12F));
            mov.switchToRotate(this, (RndB(0.5F) ? 1.0F : -1F) * com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rnd(70F, 170F), prop.ROT_SPEED_MAX);
            RunSmoke(0.2F, 17F);
        }
    }

    private void DieMirror(com.maddox.il2.engine.Actor actor, boolean flag)
    {
        if(!isNetMirror())
            java.lang.System.out.println("Internal error in TankGeneric: DieMirror");
        if(aime != null)
        {
            aime.forgetAll();
            aime = null;
        }
        if(gun != null)
        {
            com.maddox.il2.objects.vehicles.tanks.TankGeneric.destroy(gun);
            gun = null;
        }
        collisionStage = 1;
        ((com.maddox.il2.ai.ground.ChiefGround)getOwner()).Detach(this, actor);
        com.maddox.il2.ai.World.onActorDied(this, actor);
        if(flag)
        {
            ShowExplode();
            RunSmoke(0.3F, 15F);
        }
        MakeCrush();
    }

    public void destroy()
    {
        if(dust != null && !dust.isDestroyed())
            dust._finish();
        dust = null;
        engineSFX = null;
        engineSTimer = 0x5f5e0ff;
        breakSounds();
        if(codeOfUnderlyingBridgeSegment >= 0)
            com.maddox.il2.objects.bridges.LongBridge.DelTraveller(codeOfUnderlyingBridgeSegment, this);
        if(aime != null)
        {
            aime.forgetAll();
            aime = null;
        }
        if(gun != null)
        {
            com.maddox.il2.objects.vehicles.tanks.TankGeneric.destroy(((com.maddox.rts.Destroy) (gun)));
            gun = null;
        }
        super.destroy();
    }

    private void setPosition(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient, float f, float f1)
    {
        headYaw = f;
        gunPitch = f1;
        hierMesh().chunkSetAngles("Head", headYaw, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("Gun", -gunPitch, 0.0F, 0.0F);
        pos.setAbs(point3d, orient);
        pos.reset();
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    protected TankGeneric()
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

    private TankGeneric(com.maddox.il2.objects.vehicles.tanks.TankProperties tankproperties, com.maddox.il2.engine.Actor actor)
    {
        super(tankproperties.meshName);
        prop = null;
        udata = new UnitData();
        mov = new Moving();
        engineSFX = null;
        engineSTimer = 0x98967f;
        ticksIn8secs = (int)(8F / com.maddox.rts.Time.tickConstLenFs());
        collisionStage = 0;
        collisVector = new Vector2d();
        obs = new StaticObstacle();
        dying = 0;
        dyingDelay = 0L;
        codeOfUnderlyingBridgeSegment = -1;
        outCommand = new NetMsgFiltered();
        prop = tankproperties;
        collide(true);
        drawing(true);
        setOwner(actor);
        codeName = tankproperties.codeName;
        setName(actor.name() + codeName);
        setArmy(actor.getArmy());
        if(mesh().hookFind("SmokeHead") < 0)
            java.lang.System.out.println("Tank " + getClass().getName() + ": hook SmokeHead not found");
        if(mesh().hookFind("SmokeEngine") < 0)
            java.lang.System.out.println("Tank " + getClass().getName() + ": hook SmokeEngine not found");
        if(mesh().hookFind("Ground_Level") < 0)
            java.lang.System.out.println("Tank " + getClass().getName() + ": hook Ground_Level not found");
        heightAboveLandSurface = 0.0F;
        int i = mesh().hookFind("Ground_Level");
        if(i != -1)
        {
            com.maddox.JGP.Matrix4d matrix4d = new Matrix4d();
            mesh().hookMatrix(i, matrix4d);
            heightAboveLandSurface = (float)(-matrix4d.m23);
        }
        com.maddox.il2.engine.HookNamed hooknamed = null;
        com.maddox.il2.engine.HookNamed hooknamed1 = null;
        try
        {
            hooknamed = new HookNamed(this, "DustL");
            hooknamed1 = new HookNamed(this, "DustR");
        }
        catch(java.lang.Exception exception1)
        {
            hooknamed = hooknamed1 = null;
        }
        if(hooknamed == null || hooknamed1 == null)
        {
            hooknamed = hooknamed1 = null;
            dust = null;
        } else
        {
            com.maddox.il2.engine.Loc loc = new Loc();
            com.maddox.il2.engine.Loc loc1 = new Loc();
            com.maddox.il2.engine.Loc loc2 = new Loc();
            hooknamed.computePos(this, loc, loc1);
            hooknamed1.computePos(this, loc, loc2);
            com.maddox.il2.engine.Loc loc3 = new Loc();
            loc3.interpolate(loc1, loc2, 0.5F);
            dust = com.maddox.il2.engine.Eff3DActor.New(this, null, loc3, 1.0F, "Effects/Smokes/TankDust.eff", -1F);
            if(dust != null)
                dust._setIntesity(0.0F);
        }
        if(!com.maddox.il2.net.NetMissionTrack.isPlaying() || com.maddox.il2.net.NetMissionTrack.playingOriginalVersion() > 101)
        {
            int j = com.maddox.il2.game.Mission.cur().getUnitNetIdRemote(this);
            com.maddox.rts.NetChannel netchannel = com.maddox.il2.game.Mission.cur().getNetMasterChannel();
            if(netchannel == null)
                net = new Master(this);
            else
            if(j != 0)
                net = new Mirror(this, netchannel, j);
        }
        gun = null;
        try
        {
            gun = (com.maddox.il2.objects.weapons.Gun)prop.gunClass.newInstance();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
            java.lang.System.out.println("Tank: Can't create gun '" + prop.gunClass.getName() + "'");
        }
        gun.set(this, "Gun");
        gun.loadBullets(isNetMirror() ? -1 : prop.MAX_SHELLS);
        headYaw = 0.0F;
        gunPitch = prop.GUN_STD_PITCH;
        hierMesh().chunkSetAngles("Head", headYaw, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("Gun", -gunPitch, 0.0F, 0.0F);
        aime = new Aim(this, isNetMirror());
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

    private void send_CollisionDeathRequest()
    {
        if(!isNetMirror())
            return;
        if(net.masterChannel() instanceof com.maddox.rts.NetChannelInStream)
            return;
        try
        {
            com.maddox.rts.NetMsgFiltered netmsgfiltered = new NetMsgFiltered();
            netmsgfiltered.writeByte(67);
            netmsgfiltered.setIncludeTime(false);
            net.postTo(com.maddox.rts.Time.current(), net.masterChannel(), netmsgfiltered);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void send_FireCommand(com.maddox.il2.engine.Actor actor, int i, float f)
    {
        if(!isNetMaster() || !net.isMirrored())
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

    private void send_AnByteAndPoseCommand(boolean flag, com.maddox.il2.engine.Actor actor, int i)
    {
        if(!isNetMaster() || !net.isMirrored())
            return;
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        try
        {
            netmsgguaranted.writeByte(i);
            sendPose(netmsgguaranted);
            if(flag)
                netmsgguaranted.writeNetObj(actor != null ? ((com.maddox.rts.NetObj) (actor.net)) : null);
            net.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void send_DeathCommand(com.maddox.il2.engine.Actor actor)
    {
        send_AnByteAndPoseCommand(true, actor, 68);
    }

    private void send_AbsoluteDeathCommand(com.maddox.il2.engine.Actor actor)
    {
        send_AnByteAndPoseCommand(true, actor, 65);
    }

    private void send_CollisionDeathCommand()
    {
        send_AnByteAndPoseCommand(false, null, 67);
    }

    private void send_MoveCommand(com.maddox.il2.ai.ground.Moving moving, float f)
    {
        if(!isNetMaster() || !net.isMirrored())
            return;
        if(moving.moveCurTime < 0L && moving.rotatCurTime < 0L)
            return;
        try
        {
            outCommand.unLockAndClear();
            if(moving.dstPos == null || moving.moveTotTime <= 0L || moving.normal == null)
            {
                outCommand.writeByte(83);
                outCommand.setIncludeTime(false);
                net.post(com.maddox.rts.Time.current(), outCommand);
            } else
            {
                if(f > 0.0F)
                    outCommand.writeByte(77);
                else
                    outCommand.writeByte(109);
                outCommand.write(com.maddox.il2.objects.vehicles.tanks.TankGeneric.packPos(moving.dstPos));
                outCommand.writeByte(com.maddox.il2.objects.vehicles.tanks.TankGeneric.packNormal(moving.normal.z));
                if(moving.normal.z >= 0.0F)
                {
                    outCommand.writeByte(com.maddox.il2.objects.vehicles.tanks.TankGeneric.packNormal(moving.normal.x));
                    outCommand.writeByte(com.maddox.il2.objects.vehicles.tanks.TankGeneric.packNormal(moving.normal.y));
                }
                int i = (int)((long)com.maddox.rts.Time.tickLen() * moving.moveTotTime);
                if(i >= 0x10000)
                    i = 65535;
                outCommand.writeShort(i);
                outCommand.setIncludeTime(true);
                net.post(com.maddox.rts.Time.current(), outCommand);
            }
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    static int packAng(float f)
    {
        return 0xff & (int)((f * 256F) / 360F);
    }

    static int packNormal(float f)
    {
        f++;
        f *= 0.5F;
        f *= 254F;
        int i = (int)(f + 0.5F);
        if(i < 0)
            i = 0;
        if(i > 254)
            i = 254;
        return i - 127;
    }

    static byte[] packPos(com.maddox.JGP.Point3d point3d)
    {
        byte abyte0[] = new byte[8];
        int i = (int)(point3d.x * 20D + 0.5D);
        int j = (int)(point3d.y * 20D + 0.5D);
        int k = (int)(point3d.z * 10D + 0.5D);
        abyte0[0] = (byte)(i >> 0 & 0xff);
        abyte0[1] = (byte)(i >> 8 & 0xff);
        abyte0[2] = (byte)(i >> 16 & 0xff);
        abyte0[3] = (byte)(j >> 0 & 0xff);
        abyte0[4] = (byte)(j >> 8 & 0xff);
        abyte0[5] = (byte)(j >> 16 & 0xff);
        abyte0[6] = (byte)(k >> 0 & 0xff);
        abyte0[7] = (byte)(k >> 8 & 0xff);
        return abyte0;
    }

    static byte[] packOri(com.maddox.il2.engine.Orient orient)
    {
        byte abyte0[] = new byte[3];
        int i = (int)((orient.getYaw() * 256F) / 360F);
        int j = (int)((orient.getPitch() * 256F) / 360F);
        int k = (int)((orient.getRoll() * 256F) / 360F);
        abyte0[0] = (byte)(i & 0xff);
        abyte0[1] = (byte)(j & 0xff);
        abyte0[2] = (byte)(k & 0xff);
        return abyte0;
    }

    static float unpackAng(int i)
    {
        return ((float)i * 360F) / 256F;
    }

    static float unpackNormal(int i)
    {
        return (float)i / 127F;
    }

    static com.maddox.JGP.Point3d unpackPos(byte abyte0[])
    {
        int i = ((abyte0[2] & 0xff) << 16) + ((abyte0[1] & 0xff) << 8) + ((abyte0[0] & 0xff) << 0);
        int j = ((abyte0[5] & 0xff) << 16) + ((abyte0[4] & 0xff) << 8) + ((abyte0[3] & 0xff) << 0);
        int k = ((abyte0[7] & 0xff) << 8) + ((abyte0[6] & 0xff) << 0);
        return new Point3d((double)i * 0.050000000000000003D, (double)j * 0.050000000000000003D, (double)k * 0.10000000000000001D);
    }

    static com.maddox.il2.engine.Orient unpackOri(byte abyte0[])
    {
        int i = abyte0[0] & 0xff;
        int j = abyte0[1] & 0xff;
        int k = abyte0[2] & 0xff;
        com.maddox.il2.engine.Orient orient = new Orient();
        orient.setYPR(((float)i * 360F) / 256F, ((float)j * 360F) / 256F, ((float)k * 360F) / 256F);
        return orient;
    }

    static float simplifyAng(float f)
    {
        return com.maddox.il2.objects.vehicles.tanks.TankGeneric.unpackAng(com.maddox.il2.objects.vehicles.tanks.TankGeneric.packAng(f));
    }

    static com.maddox.JGP.Point3d simplifyPos(com.maddox.JGP.Point3d point3d)
    {
        return com.maddox.il2.objects.vehicles.tanks.TankGeneric.unpackPos(com.maddox.il2.objects.vehicles.tanks.TankGeneric.packPos(point3d));
    }

    static com.maddox.il2.engine.Orient simplifyOri(com.maddox.il2.engine.Orient orient)
    {
        return com.maddox.il2.objects.vehicles.tanks.TankGeneric.unpackOri(com.maddox.il2.objects.vehicles.tanks.TankGeneric.packOri(orient));
    }

    static float readPackedAng(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        return com.maddox.il2.objects.vehicles.tanks.TankGeneric.unpackAng(netmsginput.readByte());
    }

    static float readPackedNormal(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        return com.maddox.il2.objects.vehicles.tanks.TankGeneric.unpackNormal(netmsginput.readByte());
    }

    static com.maddox.JGP.Point3d readPackedPos(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        byte abyte0[] = new byte[8];
        netmsginput.read(abyte0);
        return com.maddox.il2.objects.vehicles.tanks.TankGeneric.unpackPos(abyte0);
    }

    static com.maddox.il2.engine.Orient readPackedOri(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        byte abyte0[] = new byte[3];
        netmsginput.read(abyte0);
        return com.maddox.il2.objects.vehicles.tanks.TankGeneric.unpackOri(abyte0);
    }

    private void sendPose(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        netmsgguaranted.write(com.maddox.il2.objects.vehicles.tanks.TankGeneric.packPos(pos.getAbsPoint()));
        netmsgguaranted.write(com.maddox.il2.objects.vehicles.tanks.TankGeneric.packOri(pos.getAbsOrient()));
        netmsgguaranted.writeByte(com.maddox.il2.objects.vehicles.tanks.TankGeneric.packAng(headYaw));
        netmsgguaranted.writeByte(com.maddox.il2.objects.vehicles.tanks.TankGeneric.packAng(gunPitch));
    }

    public void netFirstUpdate(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        byte byte0 = ((byte)(dying != 0 ? 105 : 73));
        netmsgguaranted.writeByte(byte0);
        sendPose(netmsgguaranted);
        net.postTo(netchannel, netmsgguaranted);
    }

    public void startMove()
    {
        if(!interpEnd("move"))
        {
            mov = new Moving();
            if(aime != null)
            {
                aime.forgetAll();
                aime = null;
            }
            aime = new Aim(this, isNetMirror());
            collisionStage = 0;
            interpPut(new Move(), "move", com.maddox.rts.Time.current(), null);
            engineSFX = newSound(prop.soundMove, true);
            engineSTimer = (int)com.maddox.il2.objects.vehicles.tanks.TankGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rnd(5F, 7F));
        }
    }

    public void forceReaskMove()
    {
        if(isNetMirror())
            return;
        if(collisionStage != 0)
            return;
        if(dying != 0)
            return;
        if(mov == null || mov.normal == null)
        {
            return;
        } else
        {
            mov.switchToAsk();
            return;
        }
    }

    public com.maddox.il2.ai.ground.UnitData GetUnitData()
    {
        return udata;
    }

    public float HeightAboveLandSurface()
    {
        return heightAboveLandSurface;
    }

    public float SpeedAverage()
    {
        return prop.SPEED_AVERAGE;
    }

    public float BestSpace()
    {
        return prop.BEST_SPACE;
    }

    public float CommandInterval()
    {
        return prop.COMMAND_INTERVAL;
    }

    public float StayInterval()
    {
        return prop.STAY_INTERVAL;
    }

    public com.maddox.il2.ai.ground.UnitInPackedForm Pack()
    {
        int i = com.maddox.rts.Finger.Int(getClass().getName());
        int j = 0;
        return new UnitInPackedForm(codeName, i, j, SpeedAverage(), BestSpace(), WeaponsMask(), HitbyMask());
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

    public void absoluteDeath(com.maddox.il2.engine.Actor actor)
    {
        if(isNetMirror())
            return;
        if(isNetMaster())
            send_AbsoluteDeathCommand(actor);
        doAbsoluteDeath(actor);
    }

    private void doAbsoluteDeath(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.ai.ground.ChiefGround chiefground = (com.maddox.il2.ai.ground.ChiefGround)getOwner();
        if(chiefground != null)
            chiefground.Detach(this, actor);
        if(!getDiedFlag())
            com.maddox.il2.ai.World.onActorDied(this, actor);
        com.maddox.il2.objects.effects.Explosions.Tank_ExplodeCollapse(pos.getAbsPoint());
        destroy();
    }

    public boolean unmovableInFuture()
    {
        return dying != 0;
    }

    public void collisionDeath()
    {
        if(isNetMirror())
        {
            send_CollisionDeathRequest();
            return;
        }
        if(isNetMaster())
            send_CollisionDeathCommand();
        doCollisionDeath();
    }

    private void doCollisionDeath()
    {
        com.maddox.il2.ai.ground.ChiefGround chiefground = (com.maddox.il2.ai.ground.ChiefGround)getOwner();
        boolean flag = chiefground == null && codeOfUnderlyingBridgeSegment >= 0 || chiefground != null && chiefground.getCodeOfBridgeSegment(this) >= 0;
        if(chiefground != null)
            chiefground.Detach(this, null);
        if(flag)
            com.maddox.il2.objects.effects.Explosions.Tank_ExplodeCollapse(pos.getAbsPoint());
        else
            com.maddox.il2.objects.effects.Explosions.Tank_ExplodeCollapse(pos.getAbsPoint());
        destroy();
    }

    public float futurePosition(float f, com.maddox.JGP.Point3d point3d)
    {
        pos.getAbs(point3d);
        if(f <= 0.0F)
            return 0.0F;
        if(mov.moveCurTime < 0L && mov.rotatCurTime < 0L)
            return 0.0F;
        float f1 = com.maddox.il2.objects.vehicles.tanks.TankGeneric.TicksToSecs(mov.moveCurTime);
        if(mov.dstPos == null)
            if(f1 >= f)
                return f;
            else
                return f1;
        float f2 = 0.0F;
        if(mov.rotatingInPlace)
        {
            f2 = com.maddox.il2.objects.vehicles.tanks.TankGeneric.TicksToSecs(mov.rotatCurTime);
            if(f2 >= f)
                return f;
        }
        if(f1 <= 0.0F)
            return f2;
        if(f2 + f1 <= f)
        {
            point3d.set(mov.dstPos);
            return f2 + f1;
        }
        com.maddox.JGP.Point3d point3d1 = new Point3d();
        point3d1.set(mov.dstPos);
        double d = (f - f2) / f1;
        p.x = point3d.x * (1.0D - d) + point3d1.x * d;
        p.y = point3d.y * (1.0D - d) + point3d1.y * d;
        if(mov.normal.z < 0.0F)
            p.z = com.maddox.il2.engine.Engine.land().HQ(p.x, p.y) + (double)HeightAboveLandSurface();
        else
            p.z = point3d.z * (1.0D - d) + point3d1.z * d;
        point3d.set(p);
        return f;
    }

    public float getReloadingTime(com.maddox.il2.ai.ground.Aim aim)
    {
        if(isNetMirror())
            return 1.0F;
        if(gun.haveBullets())
        {
            return prop.DELAY_AFTER_SHOOT;
        } else
        {
            gun.loadBullets(prop.MAX_SHELLS);
            return 120F;
        }
    }

    public float chainFireTime(com.maddox.il2.ai.ground.Aim aim)
    {
        return prop.CHAINFIRE_TIME > 0.0F ? prop.CHAINFIRE_TIME * com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rnd(0.75F, 1.25F) : 0.0F;
    }

    public float probabKeepSameEnemy(com.maddox.il2.engine.Actor actor)
    {
        return Head360() ? 0.8F : 0.0F;
    }

    public float minTimeRelaxAfterFight()
    {
        return Head360() ? 0.0F : 10F;
    }

    public void gunStartParking(com.maddox.il2.ai.ground.Aim aim)
    {
        aim.setRotationForParking(headYaw, gunPitch, 0.0F, prop.GUN_STD_PITCH, prop.HEAD_YAW_RANGE, prop.HEAD_MAX_YAW_SPEED, prop.GUN_MAX_PITCH_SPEED);
    }

    public void gunInMove(boolean flag, com.maddox.il2.ai.ground.Aim aim)
    {
        float f = aim.t();
        if(Head360() || flag || !aim.bodyRotation)
        {
            headYaw = aim.anglesYaw.getDeg(f);
            gunPitch = aim.anglesPitch.getDeg(f);
            hierMesh().chunkSetAngles("Head", headYaw, 0.0F, 0.0F);
            hierMesh().chunkSetAngles("Gun", -gunPitch, 0.0F, 0.0F);
            pos.inValidate(false);
            return;
        }
        float f1 = aim.anglesYaw.getDeg(f);
        pos.getAbs(o);
        o.setYaw(f1);
        if(mov.normal.z < 0.0F)
        {
            com.maddox.il2.engine.Engine.land().N(mov.srcPos.x, mov.srcPos.y, n);
            o.orient(n);
        } else
        {
            o.orient(mov.normal);
        }
        pos.setAbs(o);
        gunPitch = aim.anglesPitch.getDeg(f);
        hierMesh().chunkSetAngles("Gun", -gunPitch, 0.0F, 0.0F);
        pos.inValidate(false);
    }

    public com.maddox.il2.engine.Actor findEnemy(com.maddox.il2.ai.ground.Aim aim)
    {
        if(isNetMirror())
            return null;
        com.maddox.il2.engine.Actor actor = null;
        java.lang.Object obj = (com.maddox.il2.ai.ground.ChiefGround)getOwner();
        if(obj != null)
        {
            if(((com.maddox.il2.ai.ground.ChiefGround) (obj)).getCodeOfBridgeSegment(this) >= 0)
                return null;
            actor = ((com.maddox.il2.ai.ground.ChiefGround) (obj)).GetNearestEnemy(pos.getAbsPoint(), AttackMaxDistance(), WeaponsMask(), prop.ATTACK_FAST_TARGETS ? -1F : com.maddox.il2.objects.vehicles.tanks.TankGeneric.KmHourToMSec(100F));
        }
        if(actor == null)
            return null;
        obj = null;
        if(gun.prop != null)
        {
            int i = ((com.maddox.il2.ai.ground.Prey)actor).chooseBulletType(gun.prop.bullet);
            if(i < 0)
                return null;
            obj = gun.prop.bullet[i];
        }
        int j = ((com.maddox.il2.ai.ground.Prey)actor).chooseShotpoint(((com.maddox.il2.engine.BulletProperties) (obj)));
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
        if(i == 1 || !Head360() && aim.bodyRotation)
        {
            if(collisionStage != 0)
                return false;
            if(StayWhenFire())
                mov.switchToStay(1.5F);
        }
        if(!isNetMirror())
            send_FireCommand(actor, aim.shotpoint_idx, i != 0 ? f : -1F);
        return true;
    }

    private void Track_Mirror(com.maddox.il2.engine.Actor actor, int i)
    {
        if(dying != 0)
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
        if(dying != 0)
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
        float f1 = f * com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rnd(0.8F, 1.2F);
        if(!com.maddox.il2.ai.Aimer.Aim((com.maddox.il2.ai.BulletAimer)gun, actor, this, f1, p1, null))
            return 0;
        com.maddox.JGP.Point3d point3d = new Point3d();
        com.maddox.il2.ai.Aimer.GetPredictedTargetPosition(point3d);
        com.maddox.JGP.Point3d point3d1 = com.maddox.il2.ai.Aimer.GetHunterFirePoint();
        float f2 = 0.19F;
        double d = point3d.distance(point3d1);
        double d1 = point3d.z;
        point3d.sub(point3d1);
        point3d.scale(com.maddox.il2.objects.vehicles.tanks.TankGeneric.Rnd(0.94999999999999996D, 1.05D));
        point3d.add(point3d1);
        if(f1 > 0.001F)
        {
            com.maddox.JGP.Point3d point3d2 = new Point3d();
            actor.pos.getAbs(point3d2);
            tmpv.sub(point3d, point3d2);
            double d2 = tmpv.length();
            if(d2 > 0.001D)
            {
                float f8 = (float)d2 / f1;
                if(f8 > 200F)
                    f8 = 200F;
                float f9 = f8 * 0.02F;
                point3d2.sub(point3d1);
                double d3 = point3d2.x * point3d2.x + point3d2.y * point3d2.y + point3d2.z * point3d2.z;
                if(d3 > 0.01D)
                {
                    float f10 = (float)tmpv.dot(point3d2);
                    f10 /= (float)(d2 * java.lang.Math.sqrt(d3));
                    f10 = (float)java.lang.Math.sqrt(1.0F - f10 * f10);
                    f9 *= 0.4F + 0.6F * f10;
                }
                int k = com.maddox.il2.game.Mission.curCloudsType();
                if(k >= 3)
                {
                    float f11 = k < 5 ? 500F : 250F;
                    float f12 = (float)(d / (double)f11);
                    if(f12 > 1.0F)
                    {
                        if(f12 > 10F)
                            return 0;
                        f12 = ((f12 - 1.0F) / 9F) * 2.0F + 1.0F;
                        f9 *= f12;
                    }
                }
                if(k >= 3 && d1 > (double)com.maddox.il2.game.Mission.curCloudsHeight())
                    f9 *= 1.25F;
                f2 += f9;
            }
        }
        if(com.maddox.il2.ai.World.Sun().ToSun.z < -0.15F)
        {
            float f5 = (-com.maddox.il2.ai.World.Sun().ToSun.z - 0.15F) / 0.13F;
            if(f5 >= 1.0F)
                f5 = 1.0F;
            if((actor instanceof com.maddox.il2.objects.air.Aircraft) && com.maddox.rts.Time.current() - ((com.maddox.il2.objects.air.Aircraft)actor).tmSearchlighted < 1000L)
                f5 = 0.0F;
            f2 += 12F * f5;
        }
        float f6 = (float)actor.getSpeed(null);
        float f7 = 83.33334F;
        f6 = f6 < f7 ? f6 / f7 : 1.0F;
        f2 += f6 * prop.FAST_TARGETS_ANGLE_ERROR;
        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        if(!((com.maddox.il2.ai.BulletAimer)gun).FireDirection(point3d1, point3d, vector3d))
            return 0;
        float f3;
        float f4;
        if(flag)
        {
            f3 = 99999F;
            d1 = 99999F;
            f4 = 99999F;
        } else
        {
            f3 = prop.HEAD_MAX_YAW_SPEED;
            d1 = prop.GUN_MAX_PITCH_SPEED;
            f4 = prop.ROT_SPEED_MAX;
        }
        com.maddox.il2.engine.Orient orient = pos.getAbs().getOrient();
        f7 = 0.0F;
        if(!Head360())
            f7 = orient.getYaw();
        int j = aim.setRotationForTargeting(this, orient, point3d1, headYaw, gunPitch, vector3d, f2, f1, prop.HEAD_YAW_RANGE, prop.GUN_MIN_PITCH, prop.GUN_MAX_PITCH, f3, d1, f4);
        if(!Head360() && j != 0 && aim.bodyRotation)
            aim.anglesYaw.rotateDeg(f7);
        return j;
    }

    public void singleShot(com.maddox.il2.ai.ground.Aim aim)
    {
        if(StayWhenFire())
            mov.switchToStay(1.5F);
        gun.shots(1);
    }

    public void startFire(com.maddox.il2.ai.ground.Aim aim)
    {
        if(StayWhenFire())
            mov.switchToStay(1.5F);
        gun.shots(-1);
    }

    public void continueFire(com.maddox.il2.ai.ground.Aim aim)
    {
        if(StayWhenFire())
            mov.switchToStay(1.5F);
    }

    public void stopFire(com.maddox.il2.ai.ground.Aim aim)
    {
        if(StayWhenFire())
            mov.switchToStay(1.5F);
        gun.shots(0);
    }

    private static float Thicknesses[] = null;
    private static float Energies[] = null;
    private static float NumShots_Thickness_Energy[][] = (float[][])null;
    private com.maddox.il2.objects.vehicles.tanks.TankProperties prop;
    private int codeName;
    private float heightAboveLandSurface;
    private float heightAboveLandSurface2;
    public com.maddox.il2.ai.ground.UnitData udata;
    private com.maddox.il2.ai.ground.Moving mov;
    protected com.maddox.il2.engine.Eff3DActor dust;
    protected com.maddox.sound.SoundFX engineSFX;
    protected int engineSTimer;
    protected int ticksIn8secs;
    protected com.maddox.il2.objects.weapons.Gun gun;
    private com.maddox.il2.ai.ground.Aim aime;
    private float headYaw;
    private float gunPitch;
    private int collisionStage;
    static final int COLLIS_NO_COLLISION = 0;
    static final int COLLIS_JUST_COLLIDED = 1;
    static final int COLLIS_MOVING_FROM_COLLISION = 2;
    private com.maddox.JGP.Vector2d collisVector;
    private com.maddox.il2.engine.Actor collidee;
    private com.maddox.il2.ai.ground.StaticObstacle obs;
    private int dying;
    static final int DYING_NONE = 0;
    static final int DYING_SMOKE = 1;
    static final int DYING_DEAD = 2;
    private long dyingDelay;
    private int codeOfUnderlyingBridgeSegment;
    private static com.maddox.il2.objects.vehicles.tanks.TankProperties constr_arg1 = null;
    private static com.maddox.il2.engine.Actor constr_arg2 = null;
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.JGP.Point3d p1 = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();
    private static com.maddox.JGP.Vector3f n = new Vector3f();
    private static com.maddox.JGP.Vector3d tmpv = new Vector3d();
    private com.maddox.rts.NetMsgFiltered outCommand;





























}
