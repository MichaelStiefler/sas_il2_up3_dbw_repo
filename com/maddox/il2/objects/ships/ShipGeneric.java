// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ShipGeneric.java

package com.maddox.il2.objects.ships;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Aimer;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.AnglesForkExtended;
import com.maddox.il2.ai.AnglesRange;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.StrengthProperties;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Aim;
import com.maddox.il2.ai.ground.HunterInterface;
import com.maddox.il2.ai.ground.NearestEnemies;
import com.maddox.il2.ai.ground.Predator;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorException;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
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
import com.maddox.il2.engine.VisibilityLong;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.weapons.CannonMidrangeGeneric;
import com.maddox.il2.objects.weapons.Gun;
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
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

// Referenced classes of package com.maddox.il2.objects.ships:
//            WeakBody, BigshipGeneric

public class ShipGeneric extends com.maddox.il2.engine.ActorHMesh
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.engine.MsgCollisionListener, com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener, com.maddox.il2.ai.ground.Predator, com.maddox.il2.objects.ActorAlign, com.maddox.il2.ai.ground.HunterInterface, com.maddox.il2.engine.VisibilityLong
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
                    java.lang.System.out.println("Ship: Value of [" + s + "]:<" + s1 + "> " + "not found");
                else
                    java.lang.System.out.println("Ship: Value of [" + s + "]:<" + s1 + "> (" + f2 + ")" + " is out of range (" + f + ";" + f1 + ")");
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
                java.lang.System.out.print("Ship: Value of [" + s + "]:<" + s1 + "> not found");
                throw new RuntimeException("Can't set property");
            } else
            {
                return new String(s2);
            }
        }

        private static java.lang.String getS(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.String s1, java.lang.String s2)
        {
            java.lang.String s3 = sectfile.get(s, s1);
            if(s3 == null || s3.length() <= 0)
                return s2;
            else
                return new String(s3);
        }

        private static com.maddox.il2.objects.ships.ShipProperties LoadShipProperties(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.Class class1)
        {
            com.maddox.il2.objects.ships.ShipProperties shipproperties = new ShipProperties();
            shipproperties.meshName = com.maddox.il2.objects.ships.SPAWN.getS(sectfile, s, "Mesh");
            shipproperties.soundName = com.maddox.il2.objects.ships.SPAWN.getS(sectfile, s, "SoundMove");
            if(shipproperties.soundName.equalsIgnoreCase("none"))
                shipproperties.soundName = null;
            if(!shipproperties.stre.read("Ship", sectfile, null, s))
                throw new RuntimeException("Can't register Ship object");
            int i;
            for(i = 0; sectfile.sectionIndex(s + ":Gun" + i) >= 0; i++);
            shipproperties.guns = new com.maddox.il2.objects.ships.ShipGunProperties[i];
            shipproperties.WEAPONS_MASK = 0;
            shipproperties.ATTACK_MAX_DISTANCE = 1.0F;
            for(int j = 0; j < i; j++)
            {
                shipproperties.guns[j] = new ShipGunProperties();
                com.maddox.il2.objects.ships.ShipGunProperties shipgunproperties = shipproperties.guns[j];
                java.lang.String s1 = s + ":Gun" + j;
                java.lang.String s2 = "com.maddox.il2.objects.weapons." + com.maddox.il2.objects.ships.SPAWN.getS(sectfile, s1, "Gun");
                try
                {
                    shipgunproperties.gunClass = java.lang.Class.forName(s2);
                }
                catch(java.lang.Exception exception)
                {
                    java.lang.System.out.println("Ship: Can't find gun class '" + s2 + "'");
                    throw new RuntimeException("Can't register Ship object");
                }
                shipgunproperties.WEAPONS_MASK = com.maddox.il2.objects.weapons.Gun.getProperties(shipgunproperties.gunClass).weaponType;
                if(shipgunproperties.WEAPONS_MASK == 0)
                {
                    java.lang.System.out.println("Ship: Undefined weapon type in gun class '" + s2 + "'");
                    throw new RuntimeException("Can't register Ship object");
                }
                shipproperties.WEAPONS_MASK |= shipgunproperties.WEAPONS_MASK;
                shipgunproperties.ATTACK_MAX_DISTANCE = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s1, "AttackMaxDistance", 6F, 50000F);
                shipgunproperties.ATTACK_MAX_RADIUS = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s1, "AttackMaxRadius", 6F, 50000F);
                shipgunproperties.ATTACK_MAX_HEIGHT = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s1, "AttackMaxHeight", 6F, 15000F);
                if(shipgunproperties.ATTACK_MAX_DISTANCE > shipproperties.ATTACK_MAX_DISTANCE)
                    shipproperties.ATTACK_MAX_DISTANCE = shipgunproperties.ATTACK_MAX_DISTANCE;
                shipgunproperties.TRACKING_ONLY = false;
                if(sectfile.exist(s1, "TrackingOnly"))
                    shipgunproperties.TRACKING_ONLY = true;
                shipgunproperties.ATTACK_FAST_TARGETS = 1;
                if(sectfile.exist(s1, "FireFastTargets"))
                {
                    float f = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s1, "FireFastTargets", 0.0F, 2.0F);
                    shipgunproperties.ATTACK_FAST_TARGETS = (int)(f + 0.5F);
                    if(shipgunproperties.ATTACK_FAST_TARGETS > 2)
                        shipgunproperties.ATTACK_FAST_TARGETS = 2;
                }
                shipgunproperties.FAST_TARGETS_ANGLE_ERROR = 0.0F;
                if(sectfile.exist(s1, "FastTargetsAngleError"))
                {
                    float f1 = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s1, "FastTargetsAngleError", 0.0F, 45F);
                    shipgunproperties.FAST_TARGETS_ANGLE_ERROR = f1;
                }
                float f2 = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s1, "HeadMinYaw", -360F, 360F);
                float f3 = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s1, "HeadStdYaw", -360F, 360F);
                float f4 = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s1, "HeadMaxYaw", -360F, 360F);
                if(f2 > f4)
                {
                    java.lang.System.out.println("Ship: Wrong yaw angles in gun #" + j + " of '" + s + "'");
                    throw new RuntimeException("Can't register Ship object");
                }
                if(f3 < f2 || f3 > f4)
                    java.lang.System.out.println("Ship: Wrong std yaw angle in gun #" + j + " of '" + s + "'");
                shipgunproperties.HEAD_YAW_RANGE.set(f2, f4);
                shipgunproperties.HEAD_STD_YAW = f3;
                shipgunproperties.GUN_MIN_PITCH = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s1, "GunMinPitch", -15F, 85F);
                shipgunproperties.GUN_STD_PITCH = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s1, "GunStdPitch", -14F, 89.9F);
                shipgunproperties.GUN_MAX_PITCH = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s1, "GunMaxPitch", 0.0F, 89.9F);
                shipgunproperties.HEAD_MAX_YAW_SPEED = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s1, "HeadMaxYawSpeed", 0.1F, 999F);
                shipgunproperties.GUN_MAX_PITCH_SPEED = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s1, "GunMaxPitchSpeed", 0.1F, 999F);
                shipgunproperties.DELAY_AFTER_SHOOT = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s1, "DelayAfterShoot", 0.0F, 999F);
                shipgunproperties.CHAINFIRE_TIME = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s1, "ChainfireTime", 0.0F, 600F);
            }

            shipproperties.nGuns = i;
            shipproperties.SLIDER_DIST = com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s, "SliderDistance", 5F, 1000F);
            shipproperties.SPEED = com.maddox.il2.objects.ships.ShipGeneric.KmHourToMSec(com.maddox.il2.objects.ships.SPAWN.getF(sectfile, s, "Speed", 0.5F, 200F));
            shipproperties.DELAY_RESPAWN_MIN = 15F;
            shipproperties.DELAY_RESPAWN_MAX = 30F;
            com.maddox.rts.Property.set(class1, "iconName", "icons/" + com.maddox.il2.objects.ships.SPAWN.getS(sectfile, s, "Icon") + ".mat");
            com.maddox.rts.Property.set(class1, "meshName", shipproperties.meshName);
            com.maddox.rts.Property.set(class1, "speed", shipproperties.SPEED);
            return shipproperties;
        }

        public com.maddox.il2.engine.Actor actorSpawn(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            com.maddox.il2.objects.ships.ShipGeneric shipgeneric = null;
            try
            {
                com.maddox.il2.objects.ships.ShipGeneric.constr_arg1 = proper;
                com.maddox.il2.objects.ships.ShipGeneric.constr_arg2 = actorspawnarg;
                shipgeneric = (com.maddox.il2.objects.ships.ShipGeneric)cls.newInstance();
                com.maddox.il2.objects.ships.ShipGeneric.constr_arg1 = null;
                com.maddox.il2.objects.ships.ShipGeneric.constr_arg2 = null;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.ships.ShipGeneric.constr_arg1 = null;
                com.maddox.il2.objects.ships.ShipGeneric.constr_arg2 = null;
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("SPAWN: Can't create Ship object [class:" + cls.getName() + "]");
                return null;
            }
            return shipgeneric;
        }

        public java.lang.Class cls;
        public com.maddox.il2.objects.ships.ShipProperties proper;

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
                proper = com.maddox.il2.objects.ships.SPAWN.LoadShipProperties(com.maddox.il2.objects.Statics.getTechnicsFile(), s1, class1);
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
                    timeOfDeath = netmsginput.readLong();
                    if(timeOfDeath < 0L)
                    {
                        if(dying == 0)
                        {
                            life = 1.0F;
                            setDefaultLivePose();
                            forgetAllAiming();
                        }
                    } else
                    if(dying == 0)
                        Die(null, timeOfDeath, false);
                    return true;

                case 82: // 'R'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted(netmsginput, 0);
                        post(netmsgguaranted1);
                    }
                    life = 1.0F;
                    dying = 0;
                    setDiedFlag(false);
                    setMesh(prop.meshName);
                    setDefaultLivePose();
                    forgetAllAiming();
                    bodyDepth = 0.0F;
                    bodyPitch = bodyRoll = 0.0F;
                    setPosition();
                    pos.reset();
                    return true;

                case 68: // 'D'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted2 = new NetMsgGuaranted(netmsginput, 1);
                        post(netmsgguaranted2);
                    }
                    timeOfDeath = netmsginput.readLong();
                    if(timeOfDeath >= 0L && dying == 0)
                    {
                        com.maddox.rts.NetObj netobj2 = netmsginput.readNetObj();
                        com.maddox.il2.engine.Actor actor2 = netobj2 != null ? ((com.maddox.il2.engine.ActorNet)netobj2).actor() : null;
                        Die(actor2, timeOfDeath, true);
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
                byte byte0 = netmsginput.readByte();
                com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                com.maddox.il2.engine.Actor actor = netobj != null ? ((com.maddox.il2.engine.ActorNet)netobj).actor() : null;
                int i = netmsginput.readUnsignedByte();
                Track_Mirror(byte0, actor, i);
                break;

            case 70: // 'F'
                if(isMirrored())
                {
                    out.unLockAndSet(netmsginput, 1);
                    out.setIncludeTime(true);
                    postReal(com.maddox.rts.Message.currentRealTime(), out);
                }
                byte byte1 = netmsginput.readByte();
                com.maddox.rts.NetObj netobj1 = netmsginput.readNetObj();
                com.maddox.il2.engine.Actor actor1 = netobj1 != null ? ((com.maddox.il2.engine.ActorNet)netobj1).actor() : null;
                float f = netmsginput.readFloat();
                float f1 = 0.001F * (float)(com.maddox.rts.Message.currentGameTime() - com.maddox.rts.Time.current()) + f;
                int j = netmsginput.readUnsignedByte();
                Fire_Mirror(byte1, actor1, j, f1);
                break;

            case 68: // 'D'
                out.unLockAndSet(netmsginput, 1);
                out.setIncludeTime(false);
                postRealTo(com.maddox.rts.Message.currentRealTime(), masterChannel(), out);
                break;
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
            if(dying != 0)
            {
                return true;
            } else
            {
                com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                com.maddox.il2.engine.Actor actor = netobj != null ? ((com.maddox.il2.engine.ActorNet)netobj).actor() : null;
                Die(actor, -1L, true);
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
            if(dying == 0)
            {
                if(path != null)
                {
                    bodyDepth = 0.0F;
                    bodyPitch = bodyRoll = 0.0F;
                    setMovablePosition(com.maddox.il2.net.NetServerParams.getServerTime());
                }
                if(wakeupTmr == 0L)
                {
                    for(int i = 0; i < prop.nGuns; i++)
                        arms[i].aime.tick_();

                } else
                if(wakeupTmr > 0L)
                    wakeupTmr--;
                else
                if(++wakeupTmr == 0L)
                    if(isAnyEnemyNear())
                        wakeupTmr = com.maddox.il2.objects.ships.ShipGeneric.SecsToTicks(com.maddox.il2.objects.ships.ShipGeneric.Rnd(DELAY_WAKEUP, DELAY_WAKEUP * 1.2F));
                    else
                        wakeupTmr = -com.maddox.il2.objects.ships.ShipGeneric.SecsToTicks(com.maddox.il2.objects.ships.ShipGeneric.Rnd(4F, 7F));
                return true;
            }
            if(dying == 2)
            {
                if(path != null)
                {
                    eraseGuns();
                    return false;
                }
                if(respawnDelay-- > 0L)
                    return true;
                if(!isNetMaster())
                {
                    respawnDelay = 10000L;
                    return true;
                } else
                {
                    life = 1.0F;
                    dying = 0;
                    wakeupTmr = 0L;
                    setDiedFlag(false);
                    forgetAllAiming();
                    setDefaultLivePose();
                    bodyDepth = 0.0F;
                    bodyPitch = bodyRoll = 0.0F;
                    setPosition();
                    pos.reset();
                    send_RespawnCommand();
                    return true;
                }
            }
            long l = com.maddox.il2.net.NetServerParams.getServerTime() - timeOfDeath;
            if(l <= 0L)
                l = 0L;
            if(l >= timeForRotation)
            {
                bodyPitch = drownBodyPitch;
                bodyRoll = drownBodyRoll;
            } else
            {
                bodyPitch = drownBodyPitch * ((float)l / (float)timeForRotation);
                bodyRoll = drownBodyRoll * ((float)l / (float)timeForRotation);
            }
            bodyDepth = sinkingDepthSpeed * (float)l * 0.001F * 5F;
            if(bodyDepth >= 5F)
            {
                float f = java.lang.Math.abs(com.maddox.JGP.Geom.sinDeg(bodyPitch) * collisionR());
                f += bodyDepth;
                if(f + bodyDepth >= seaDepth)
                    dying = 2;
                if(bodyDepth > mesh().visibilityR())
                    dying = 2;
            }
            if(path != null)
                setMovablePosition(timeOfDeath);
            else
                setPosition();
            return true;
        }

        Move()
        {
        }
    }

    static class HookNamedZ0 extends com.maddox.il2.engine.HookNamed
    {

        public void computePos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
        {
            super.computePos(actor, loc, loc1);
            loc1.getPoint().z = 0.25D;
        }

        public HookNamedZ0(com.maddox.il2.engine.ActorMesh actormesh, java.lang.String s)
        {
            super(actormesh, s);
        }

        public HookNamedZ0(com.maddox.il2.engine.Mesh mesh, java.lang.String s)
        {
            super(mesh, s);
        }
    }

    private class Segment
    {

        public com.maddox.JGP.Point3d posIn;
        public com.maddox.JGP.Point3d posOut;
        public float length;
        public long timeIn;
        public long timeOut;
        public float speedIn;
        public float speedOut;

        private Segment()
        {
        }

    }

    public class FiringDevice
    {

        private int id;
        private com.maddox.il2.objects.weapons.Gun gun;
        private com.maddox.il2.ai.ground.Aim aime;
        private float headYaw;
        private float gunPitch;











        public FiringDevice()
        {
        }
    }

    public static class ShipProperties
    {

        public java.lang.String meshName;
        public java.lang.String soundName;
        public com.maddox.il2.ai.StrengthProperties stre;
        public int WEAPONS_MASK;
        public int HITBY_MASK;
        public float ATTACK_MAX_DISTANCE;
        public float SLIDER_DIST;
        public float SPEED;
        public float DELAY_RESPAWN_MIN;
        public float DELAY_RESPAWN_MAX;
        public com.maddox.il2.objects.ships.ShipGunProperties guns[];
        public int nGuns;

        public ShipProperties()
        {
            meshName = null;
            soundName = null;
            stre = new StrengthProperties();
            WEAPONS_MASK = 4;
            HITBY_MASK = -2;
            ATTACK_MAX_DISTANCE = 1.0F;
            SLIDER_DIST = 1.0F;
            SPEED = 1.0F;
            DELAY_RESPAWN_MIN = 15F;
            DELAY_RESPAWN_MAX = 30F;
            guns = null;
        }
    }

    public static class ShipGunProperties
    {

        public java.lang.Class gunClass;
        public int WEAPONS_MASK;
        public boolean TRACKING_ONLY;
        public float ATTACK_MAX_DISTANCE;
        public float ATTACK_MAX_RADIUS;
        public float ATTACK_MAX_HEIGHT;
        public int ATTACK_FAST_TARGETS;
        public float FAST_TARGETS_ANGLE_ERROR;
        public com.maddox.il2.ai.AnglesRange HEAD_YAW_RANGE;
        public float HEAD_STD_YAW;
        public float GUN_MIN_PITCH;
        public float GUN_STD_PITCH;
        public float GUN_MAX_PITCH;
        public float HEAD_MAX_YAW_SPEED;
        public float GUN_MAX_PITCH_SPEED;
        public float DELAY_AFTER_SHOOT;
        public float CHAINFIRE_TIME;
        public com.maddox.JGP.Point3d fireOffset;
        public com.maddox.il2.engine.Orient fireOrient;

        public ShipGunProperties()
        {
            gunClass = null;
            WEAPONS_MASK = 4;
            TRACKING_ONLY = false;
            ATTACK_MAX_DISTANCE = 1.0F;
            ATTACK_MAX_RADIUS = 1.0F;
            ATTACK_MAX_HEIGHT = 1.0F;
            ATTACK_FAST_TARGETS = 1;
            FAST_TARGETS_ANGLE_ERROR = 0.0F;
            HEAD_YAW_RANGE = new AnglesRange(-1F, 1.0F);
            HEAD_STD_YAW = 0.0F;
            GUN_MIN_PITCH = -20F;
            GUN_STD_PITCH = -18F;
            GUN_MAX_PITCH = -15F;
            HEAD_MAX_YAW_SPEED = 720F;
            GUN_MAX_PITCH_SPEED = 60F;
            DELAY_AFTER_SHOOT = 1.0F;
            CHAINFIRE_TIME = 0.0F;
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

    public static final float KmHourToMSec(float f)
    {
        return f / 3.6F;
    }

    private static final long SecsToTicks(float f)
    {
        long l = (long)(0.5D + (double)(f / com.maddox.rts.Time.tickLenFs()));
        return l >= 1L ? l : 1L;
    }

    protected final boolean Head360(com.maddox.il2.objects.ships.FiringDevice firingdevice)
    {
        return prop.guns[firingdevice.id].HEAD_YAW_RANGE.fullcircle();
    }

    public void msgCollisionRequest(com.maddox.il2.engine.Actor actor, boolean aflag[])
    {
        if(actor instanceof com.maddox.il2.objects.bridges.BridgeSegment)
        {
            if(dying != 0)
                aflag[0] = false;
            return;
        }
        if(path == null && (actor instanceof com.maddox.il2.engine.ActorMesh) && ((com.maddox.il2.engine.ActorMesh)actor).isStaticPos())
        {
            aflag[0] = false;
            return;
        } else
        {
            return;
        }
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if(dying != 0)
            return;
        if(isNetMirror())
            return;
        if(actor instanceof com.maddox.il2.objects.ships.WeakBody)
            return;
        if((actor instanceof com.maddox.il2.objects.ships.ShipGeneric) || (actor instanceof com.maddox.il2.objects.ships.BigshipGeneric) || (actor instanceof com.maddox.il2.objects.bridges.BridgeSegment))
            Die(null, -1L, true);
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
        if(wakeupTmr < 0L)
            wakeupTmr = com.maddox.il2.objects.ships.ShipGeneric.SecsToTicks(com.maddox.il2.objects.ships.ShipGeneric.Rnd(DELAY_WAKEUP, DELAY_WAKEUP * 1.2F));
        float f;
        float f1;
        if(shot.powerType == 1)
        {
            f = prop.stre.EXPLHIT_MAX_TNT;
            f1 = prop.stre.EXPLHIT_MAX_TNT;
        } else
        {
            f = prop.stre.SHOT_MIN_ENERGY;
            f1 = prop.stre.SHOT_MAX_ENERGY;
        }
        float f2 = shot.power * com.maddox.il2.objects.ships.ShipGeneric.Rnd(1.0F, 1.1F);
        if(f2 < f)
            return;
        float f3 = f2 / f1;
        life -= f3;
        if(life > 0.0F)
        {
            return;
        } else
        {
            Die(shot.initiator, -1L, true);
            return;
        }
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        if(dying != 0)
            return;
        if(isNetMirror() && explosion.isMirage())
            return;
        if(wakeupTmr < 0L)
            wakeupTmr = com.maddox.il2.objects.ships.ShipGeneric.SecsToTicks(com.maddox.il2.objects.ships.ShipGeneric.Rnd(DELAY_WAKEUP, DELAY_WAKEUP * 1.2F));
        float f = explosion.power;
        com.maddox.il2.ai.Explosion _tmp = explosion;
        if(explosion.powerType == 2 && explosion.chunkName != null)
            f *= 0.45F;
        float f1;
        if(explosion.chunkName != null)
        {
            float f2 = f;
            f2 *= com.maddox.il2.objects.ships.ShipGeneric.Rnd(1.0F, 1.1F);
            if(f2 < prop.stre.EXPLHIT_MIN_TNT)
                return;
            f1 = f2 / prop.stre.EXPLHIT_MAX_TNT;
        } else
        {
            float f3 = explosion.receivedTNT_1meter(this);
            f3 *= com.maddox.il2.objects.ships.ShipGeneric.Rnd(1.0F, 1.1F);
            if(f3 < prop.stre.EXPLNEAR_MIN_TNT)
                return;
            f1 = f3 / prop.stre.EXPLNEAR_MAX_TNT;
        }
        life -= f1;
        if(life > 0.0F)
        {
            return;
        } else
        {
            Die(explosion.initiator, -1L, true);
            return;
        }
    }

    private float computeSeaDepth(com.maddox.JGP.Point3d point3d)
    {
        for(float f = 5F; f <= 355F; f += 10F)
        {
            for(float f1 = 0.0F; f1 < 360F; f1 += 30F)
            {
                float f2 = f * com.maddox.JGP.Geom.cosDeg(f1);
                float f3 = f * com.maddox.JGP.Geom.sinDeg(f1);
                f2 += (float)point3d.x;
                f3 += (float)point3d.y;
                if(!com.maddox.il2.ai.World.land().isWater(f2, f3))
                    return 50F * (f / 355F);
            }

        }

        return 1000F;
    }

    private void computeSinkingParams(long l)
    {
        com.maddox.il2.ai.RangeRandom rangerandom = new RangeRandom(l % 11073L);
        timeForRotation = 40000L + (long)(rangerandom.nextFloat() * 0.0F);
        drownBodyPitch = 50F - rangerandom.nextFloat() * 20F;
        if(rangerandom.nextFloat() < 0.5F)
            drownBodyPitch = -drownBodyPitch;
        drownBodyRoll = 30F - rangerandom.nextFloat() * 60F;
        sinkingDepthSpeed = 0.55F + rangerandom.nextFloat() * 0.0F;
        seaDepth = computeSeaDepth(pos.getAbsPoint());
        seaDepth *= 1.0F + rangerandom.nextFloat() * 0.2F;
    }

    private void showExplode()
    {
        if(mesh() instanceof com.maddox.il2.engine.HierMesh)
            com.maddox.il2.objects.effects.Explosions.Antiaircraft_Explode(pos.getAbsPoint());
    }

    private void Die(com.maddox.il2.engine.Actor actor, long l, boolean flag)
    {
        if(dying != 0)
            return;
        if(l < 0L)
        {
            if(isNetMirror())
            {
                send_DeathRequest(actor);
                return;
            }
            l = com.maddox.il2.net.NetServerParams.getServerTime();
        }
        life = 0.0F;
        dying = 1;
        com.maddox.il2.ai.World.onActorDied(this, actor);
        forgetAllAiming();
        SetEffectsIntens(-1F);
        if(path != null)
        {
            bodyDepth = 0.0F;
            bodyPitch = bodyRoll = 0.0F;
            setMovablePosition(l);
        } else
        {
            bodyDepth = 0.0F;
            bodyPitch = bodyRoll = 0.0F;
            setPosition();
        }
        pos.reset();
        computeSinkingParams(l);
        if(com.maddox.il2.game.Mission.isDeathmatch())
        {
            timeOfDeath = com.maddox.rts.Time.current();
            if(!flag)
                timeOfDeath = 0L;
        } else
        {
            timeOfDeath = l;
        }
        if(flag)
            showExplode();
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
            eraseGuns();
            super.destroy();
            return;
        }
    }

    private boolean isAnyEnemyNear()
    {
        com.maddox.il2.ai.ground.NearestEnemies.set(WeaponsMask());
        com.maddox.il2.engine.Actor actor = com.maddox.il2.ai.ground.NearestEnemies.getAFoundEnemy(pos.getAbsPoint(), 2000D, getArmy());
        return actor != null;
    }

    private final com.maddox.il2.objects.ships.FiringDevice GetFiringDevice(com.maddox.il2.ai.ground.Aim aim)
    {
        for(int i = 0; i < prop.nGuns; i++)
            if(arms[i] != null && arms[i].aime == aim)
                return arms[i];

        java.lang.System.out.println("Internal error 1: Can't find ship gun.");
        return null;
    }

    private final com.maddox.il2.objects.ships.ShipGunProperties GetGunProperties(com.maddox.il2.ai.ground.Aim aim)
    {
        for(int i = 0; i < prop.nGuns; i++)
            if(arms[i].aime == aim)
                return prop.guns[arms[i].id];

        java.lang.System.out.println("Internal error 2: Can't find ship gun.");
        return null;
    }

    private void setGunAngles(com.maddox.il2.objects.ships.FiringDevice firingdevice, float f, float f1)
    {
        firingdevice.headYaw = f;
        firingdevice.gunPitch = f1;
        hierMesh().chunkSetAngles("Head" + firingdevice.id, firingdevice.headYaw, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("Gun" + firingdevice.id, -firingdevice.gunPitch, 0.0F, 0.0F);
    }

    private void eraseGuns()
    {
        if(arms != null)
        {
            for(int i = 0; i < prop.nGuns; i++)
            {
                if(arms[i] == null)
                    continue;
                if(arms[i].aime != null)
                {
                    arms[i].aime.forgetAll();
                    arms[i].aime = null;
                }
                if(arms[i].gun != null)
                {
                    com.maddox.il2.objects.ships.ShipGeneric.destroy(arms[i].gun);
                    arms[i].gun = null;
                }
                arms[i] = null;
            }

            arms = null;
        }
    }

    private void forgetAllAiming()
    {
        if(arms != null)
        {
            for(int i = 0; i < prop.nGuns; i++)
                if(arms[i] != null && arms[i].aime != null)
                    arms[i].aime.forgetAiming();

        }
    }

    private void CreateGuns()
    {
        arms = new com.maddox.il2.objects.ships.FiringDevice[prop.nGuns];
        for(int i = 0; i < prop.nGuns; i++)
        {
            arms[i] = new FiringDevice();
            arms[i].id = i;
            arms[i].gun = null;
            try
            {
                arms[i].gun = (com.maddox.il2.objects.weapons.Gun)prop.guns[i].gunClass.newInstance();
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("Ship: Can't create gun '" + prop.guns[i].gunClass.getName() + "'");
            }
            arms[i].gun.set(this, "ShellStart" + i);
            arms[i].gun.loadBullets(-1);
            com.maddox.il2.engine.Loc loc = new Loc();
            hierMesh().setCurChunk("Head" + i);
            hierMesh().getChunkLocObj(loc);
            prop.guns[i].fireOffset = new Point3d();
            loc.get(prop.guns[i].fireOffset);
            prop.guns[i].fireOrient = new Orient();
            loc.get(prop.guns[i].fireOrient);
            arms[i].aime = new Aim(this, isNetMirror(), SLOWFIRE_K * prop.guns[i].DELAY_AFTER_SHOOT);
        }

    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    private void setDefaultLivePose()
    {
        int i = mesh().hookFind("Ground_Level");
        if(i != -1)
        {
            com.maddox.JGP.Matrix4d matrix4d = new Matrix4d();
            hierMesh().hookMatrix(i, matrix4d);
        }
        if(mesh() instanceof com.maddox.il2.engine.HierMesh)
            hierMesh().chunkSetAngles("Body", 0.0F, 0.0F, 0.0F);
        for(int j = 0; j < prop.nGuns; j++)
            setGunAngles(arms[j], prop.guns[j].HEAD_STD_YAW, prop.guns[j].GUN_STD_PITCH);

        bodyDepth = 0.0F;
        align();
    }

    protected ShipGeneric()
    {
        this(constr_arg1, constr_arg2);
    }

    private ShipGeneric(com.maddox.il2.objects.ships.ShipProperties shipproperties, com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
    {
        super(shipproperties.meshName);
        prop = null;
        arms = null;
        cachedSeg = 0;
        life = 1.0F;
        dying = 0;
        respawnDelay = 0L;
        wakeupTmr = 0L;
        DELAY_WAKEUP = 0.0F;
        SKILL_IDX = 2;
        SLOWFIRE_K = 1.0F;
        pipe = null;
        nose = null;
        tail = null;
        outCommand = new NetMsgFiltered();
        prop = shipproperties;
        actorspawnarg.setStationary(this);
        path = null;
        collide(true);
        drawing(true);
        bodyDepth = 0.0F;
        bodyPitch = bodyRoll = 0.0F;
        bodyYaw = actorspawnarg.orient.getYaw();
        setPosition();
        pos.reset();
        createNetObject(actorspawnarg.netChannel, actorspawnarg.netIdRemote);
        SKILL_IDX = com.maddox.il2.ai.Chief.new_SKILL_IDX;
        SLOWFIRE_K = com.maddox.il2.ai.Chief.new_SLOWFIRE_K;
        DELAY_WAKEUP = com.maddox.il2.ai.Chief.new_DELAY_WAKEUP;
        wakeupTmr = 0L;
        CreateGuns();
        setDefaultLivePose();
        if(!isNetMirror() && prop.nGuns > 0 && DELAY_WAKEUP > 0.0F)
            wakeupTmr = -com.maddox.il2.objects.ships.ShipGeneric.SecsToTicks(com.maddox.il2.objects.ships.ShipGeneric.Rnd(2.0F, 7F));
        if(!interpEnd("move"))
            interpPut(new Move(), "move", com.maddox.rts.Time.current(), null);
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

    public ShipGeneric(java.lang.String s, int i, com.maddox.rts.SectFile sectfile, java.lang.String s1, com.maddox.rts.SectFile sectfile1, java.lang.String s2)
    {
        prop = null;
        arms = null;
        cachedSeg = 0;
        life = 1.0F;
        dying = 0;
        respawnDelay = 0L;
        wakeupTmr = 0L;
        DELAY_WAKEUP = 0.0F;
        SKILL_IDX = 2;
        SLOWFIRE_K = 1.0F;
        pipe = null;
        nose = null;
        tail = null;
        outCommand = new NetMsgFiltered();
        try
        {
            int j = sectfile.sectionIndex(s1);
            java.lang.String s3 = sectfile.var(j, 0);
            java.lang.Object obj = com.maddox.rts.Spawn.get(s3);
            if(obj == null)
                throw new ActorException("Ship: Unknown class of ship (" + s3 + ")");
            prop = ((com.maddox.il2.objects.ships.SPAWN)obj).proper;
            try
            {
                setMesh(prop.meshName);
            }
            catch(java.lang.RuntimeException runtimeexception)
            {
                super.destroy();
                throw runtimeexception;
            }
            if(prop.soundName != null)
                newSound(prop.soundName, true);
            setName(s);
            setArmy(i);
            LoadPath(sectfile1, s2);
            cachedSeg = 0;
            bodyDepth = 0.0F;
            bodyPitch = bodyRoll = 0.0F;
            setMovablePosition(com.maddox.il2.net.NetServerParams.getServerTime());
            pos.reset();
            collide(true);
            drawing(true);
            pipe = null;
            if(mesh().hookFind("Vapor") >= 0)
            {
                com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(this, "Vapor");
                pipe = com.maddox.il2.engine.Eff3DActor.New(this, hooknamed, null, 1.0F, "Effects/Smokes/SmokePipeShip.eff", -1F);
            }
            wake[2] = wake[1] = wake[0] = null;
            tail = null;
            nose = null;
            boolean flag = prop.SLIDER_DIST / 2.5F < 90F;
            if(mesh().hookFind("_Prop") >= 0)
            {
                com.maddox.il2.objects.ships.HookNamedZ0 hooknamedz0 = new HookNamedZ0(this, "_Prop");
                tail = com.maddox.il2.engine.Eff3DActor.New(this, hooknamedz0, null, 1.0F, flag ? "3DO/Effects/Tracers/ShipTrail/PropWakeBoat.eff" : "3DO/Effects/Tracers/ShipTrail/PropWake.eff", -1F);
            }
            if(mesh().hookFind("_Centre") >= 0)
            {
                com.maddox.il2.engine.Loc loc = new Loc();
                com.maddox.il2.engine.Loc loc1 = new Loc();
                com.maddox.il2.engine.HookNamed hooknamed1 = new HookNamed(this, "_Left");
                hooknamed1.computePos(this, new Loc(), loc);
                com.maddox.il2.engine.HookNamed hooknamed2 = new HookNamed(this, "_Right");
                hooknamed2.computePos(this, new Loc(), loc1);
                float f = (float)loc.getPoint().distance(loc1.getPoint());
                com.maddox.il2.objects.ships.HookNamedZ0 hooknamedz0_1 = new HookNamedZ0(this, "_Centre");
                if(mesh().hookFind("_Prop") >= 0)
                {
                    com.maddox.il2.objects.ships.HookNamedZ0 hooknamedz0_3 = new HookNamedZ0(this, "_Prop");
                    com.maddox.il2.engine.Loc loc2 = new Loc();
                    hooknamedz0_1.computePos(this, new Loc(), loc2);
                    com.maddox.il2.engine.Loc loc3 = new Loc();
                    hooknamedz0_3.computePos(this, new Loc(), loc3);
                    float f1 = (float)loc2.getPoint().distance(loc3.getPoint());
                    wake[0] = com.maddox.il2.engine.Eff3DActor.New(this, hooknamedz0_3, new Loc((double)(-f1) * 0.33000000000000002D, 0.0D, 0.0D, 0.0F, 30F, 0.0F), f, flag ? "3DO/Effects/Tracers/ShipTrail/WakeBoat.eff" : "3DO/Effects/Tracers/ShipTrail/Wake.eff", -1F);
                    wake[1] = com.maddox.il2.engine.Eff3DActor.New(this, hooknamedz0_1, new Loc((double)f1 * 0.14999999999999999D, 0.0D, 0.0D, 0.0F, 30F, 0.0F), f, flag ? "3DO/Effects/Tracers/ShipTrail/WakeBoatS.eff" : "3DO/Effects/Tracers/ShipTrail/WakeS.eff", -1F);
                    wake[2] = com.maddox.il2.engine.Eff3DActor.New(this, hooknamedz0_1, new Loc((double)(-f1) * 0.14999999999999999D, 0.0D, 0.0D, 0.0F, 30F, 0.0F), f, flag ? "3DO/Effects/Tracers/ShipTrail/WakeBoatS.eff" : "3DO/Effects/Tracers/ShipTrail/WakeS.eff", -1F);
                } else
                {
                    wake[0] = com.maddox.il2.engine.Eff3DActor.New(this, hooknamedz0_1, new Loc((double)(-f) * 0.29999999999999999D, 0.0D, 0.0D, 0.0F, 30F, 0.0F), f, (double)prop.SLIDER_DIST / 2.5D >= 50D ? "3DO/Effects/Tracers/ShipTrail/Wake.eff" : "3DO/Effects/Tracers/ShipTrail/WakeBoat.eff", -1F);
                }
            }
            if(mesh().hookFind("_Nose") >= 0)
            {
                com.maddox.il2.objects.ships.HookNamedZ0 hooknamedz0_2 = new HookNamedZ0(this, "_Nose");
                nose = com.maddox.il2.engine.Eff3DActor.New(this, hooknamedz0_2, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 30F, 0.0F), 1.0F, "3DO/Effects/Tracers/ShipTrail/SideWave.eff", -1F);
            }
            SetEffectsIntens(0.0F);
            int k = com.maddox.il2.game.Mission.cur().getUnitNetIdRemote(this);
            com.maddox.rts.NetChannel netchannel = com.maddox.il2.game.Mission.cur().getNetMasterChannel();
            if(netchannel == null)
                net = new Master(this);
            else
            if(k != 0)
                net = new Mirror(this, netchannel, k);
            SKILL_IDX = com.maddox.il2.ai.Chief.new_SKILL_IDX;
            SLOWFIRE_K = com.maddox.il2.ai.Chief.new_SLOWFIRE_K;
            DELAY_WAKEUP = com.maddox.il2.ai.Chief.new_DELAY_WAKEUP;
            wakeupTmr = 0L;
            CreateGuns();
            setDefaultLivePose();
            if(!isNetMirror() && prop.nGuns > 0 && DELAY_WAKEUP > 0.0F)
                wakeupTmr = -com.maddox.il2.objects.ships.ShipGeneric.SecsToTicks(com.maddox.il2.objects.ships.ShipGeneric.Rnd(2.0F, 7F));
            if(!interpEnd("move"))
                interpPut(new Move(), "move", com.maddox.rts.Time.current(), null);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("Ship creation failure:");
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
            throw new ActorException();
        }
    }

    private void SetEffectsIntens(float f)
    {
        if(dying != 0)
            f = -1F;
        if(pipe != null)
            if(f >= 0.0F)
            {
                pipe._setIntesity(f);
            } else
            {
                pipe._finish();
                pipe = null;
            }
        for(int i = 0; i < 3; i++)
        {
            if(wake[i] == null)
                continue;
            if(f >= 0.0F)
            {
                wake[i]._setIntesity(f);
            } else
            {
                wake[i]._finish();
                wake[i] = null;
            }
        }

        if(nose != null)
            if(f >= 0.0F)
            {
                nose._setIntesity(f);
            } else
            {
                nose._finish();
                nose = null;
            }
        if(tail != null)
            if(f >= 0.0F)
            {
                tail._setIntesity(f);
            } else
            {
                tail._finish();
                tail = null;
            }
    }

    private void LoadPath(com.maddox.rts.SectFile sectfile, java.lang.String s)
    {
        int i = sectfile.sectionIndex(s);
        if(i < 0)
            throw new ActorException("Ship path: Section [" + s + "] not found");
        int j = sectfile.vars(i);
        if(j < 1)
            throw new ActorException("Ship path must contain at least 2 nodes");
        path = new ArrayList();
        for(int k = 0; k < j; k++)
        {
            java.util.StringTokenizer stringtokenizer = new StringTokenizer(sectfile.line(i, k));
            float f1 = java.lang.Float.valueOf(stringtokenizer.nextToken()).floatValue();
            float f2 = java.lang.Float.valueOf(stringtokenizer.nextToken()).floatValue();
            float f4 = java.lang.Float.valueOf(stringtokenizer.nextToken()).floatValue();
            double d = 0.0D;
            float f7 = 0.0F;
            if(stringtokenizer.hasMoreTokens())
            {
                d = java.lang.Double.valueOf(stringtokenizer.nextToken()).doubleValue();
                if(stringtokenizer.hasMoreTokens())
                {
                    java.lang.Double.valueOf(stringtokenizer.nextToken()).doubleValue();
                    if(stringtokenizer.hasMoreTokens())
                        f7 = java.lang.Float.valueOf(stringtokenizer.nextToken()).floatValue();
                }
            }
            if(k >= j - 1)
                d = 1.0D;
            com.maddox.il2.objects.ships.Segment segment7 = new Segment();
            segment7.posIn = new Point3d(f1, f2, 0.0D);
            if(java.lang.Math.abs(d) < 0.10000000000000001D)
                segment7.timeIn = 0L;
            else
                segment7.timeIn = (long)(d * 60D * 1000D + (d <= 0.0D ? -0.5D : 0.5D));
            if(f7 <= 0.0F && (k == 0 || k == j - 1 || segment7.timeIn == 0L))
                f7 = prop.SPEED;
            segment7.speedIn = f7;
            path.add(segment7);
        }

        for(int l = 0; l < path.size() - 1; l++)
        {
            com.maddox.il2.objects.ships.Segment segment = (com.maddox.il2.objects.ships.Segment)path.get(l);
            com.maddox.il2.objects.ships.Segment segment1 = (com.maddox.il2.objects.ships.Segment)path.get(l + 1);
            if(segment.timeIn > 0L && segment1.timeIn > 0L)
            {
                com.maddox.il2.objects.ships.Segment segment2 = new Segment();
                segment2.posIn = new Point3d(segment.posIn);
                segment2.posIn.add(segment1.posIn);
                segment2.posIn.scale(0.5D);
                segment2.timeIn = 0L;
                segment2.speedIn = (segment.speedIn + segment1.speedIn) * 0.5F;
                path.add(l + 1, segment2);
            }
        }

        int i1 = 0;
        float f = ((com.maddox.il2.objects.ships.Segment)path.get(i1)).length;
        int j1;
        for(; i1 < path.size() - 1; i1 = j1)
        {
            j1 = i1 + 1;
            do
            {
                com.maddox.il2.objects.ships.Segment segment3 = (com.maddox.il2.objects.ships.Segment)path.get(j1);
                if((double)segment3.speedIn > 0.0D)
                    break;
                f += segment3.length;
                j1++;
            } while(true);
            if(j1 - i1 <= 1)
                continue;
            float f3 = ((com.maddox.il2.objects.ships.Segment)path.get(i1)).length;
            float f5 = ((com.maddox.il2.objects.ships.Segment)path.get(i1)).speedIn;
            float f6 = ((com.maddox.il2.objects.ships.Segment)path.get(j1)).speedIn;
            for(int i2 = i1 + 1; i2 < j1; i2++)
            {
                com.maddox.il2.objects.ships.Segment segment6 = (com.maddox.il2.objects.ships.Segment)path.get(i2);
                float f9 = f3 / f;
                segment6.speedIn = f5 * (1.0F - f9) + f6 * f9;
                f += segment6.length;
            }

        }

        long l1 = 0L;
        for(int k1 = 0; k1 < path.size() - 1; k1++)
        {
            com.maddox.il2.objects.ships.Segment segment4 = (com.maddox.il2.objects.ships.Segment)path.get(k1);
            com.maddox.il2.objects.ships.Segment segment5 = (com.maddox.il2.objects.ships.Segment)path.get(k1 + 1);
            if(k1 == 0)
                l1 = segment4.timeIn;
            segment4.posOut = new Point3d(segment5.posIn);
            segment5.posIn = segment4.posOut;
            segment4.length = (float)segment4.posIn.distance(segment5.posIn);
            float f8 = segment4.speedIn;
            float f10 = segment5.speedIn;
            float f11 = (f8 + f10) * 0.5F;
            if(segment4.timeIn > 0L)
                if(segment4.timeIn > l1)
                    segment4.timeIn -= l1;
                else
                    segment4.timeIn = 0L;
            if(segment4.timeIn == 0L && segment5.timeIn > 0L)
            {
                int j2 = (int)(((2.0F * segment4.length) / f8) * 1000F + 0.5F);
                j2 = (int)((long)j2 + l1);
                if(segment5.timeIn > (long)j2)
                    segment5.timeIn -= j2;
                else
                    segment5.timeIn = 0L;
            }
            if(segment4.timeIn > 0L)
            {
                segment4.speedIn = 0.0F;
                segment4.speedOut = f10;
                float f12 = ((2.0F * segment4.length) / f10) * 1000F + 0.5F;
                segment4.timeIn = l1 + segment4.timeIn;
                segment4.timeOut = segment4.timeIn + (long)(int)f12;
                l1 = segment4.timeOut;
                continue;
            }
            if(segment5.timeIn > 0L)
            {
                segment4.speedIn = f8;
                segment4.speedOut = 0.0F;
                float f13 = ((2.0F * segment4.length) / f8) * 1000F + 0.5F;
                segment4.timeIn = l1 + segment4.timeIn;
                segment4.timeOut = segment4.timeIn + (long)(int)f13;
                l1 = segment4.timeOut + segment5.timeIn;
            } else
            {
                segment4.speedIn = f8;
                segment4.speedOut = f10;
                float f14 = (segment4.length / f11) * 1000F + 0.5F;
                segment4.timeIn = l1;
                segment4.timeOut = segment4.timeIn + (long)(int)f14;
                l1 = segment4.timeOut;
            }
        }

        path.remove(path.size() - 1);
    }

    public void align()
    {
        pos.getAbs(p);
        p.z = com.maddox.il2.engine.Engine.land().HQ(p.x, p.y) - (double)bodyDepth;
        pos.setAbs(p);
    }

    private void setMovablePosition(long l)
    {
        if(cachedSeg < 0)
            cachedSeg = 0;
        else
        if(cachedSeg >= path.size())
            cachedSeg = path.size() - 1;
        com.maddox.il2.objects.ships.Segment segment = (com.maddox.il2.objects.ships.Segment)path.get(cachedSeg);
        if(segment.timeIn <= l && l <= segment.timeOut)
        {
            SetEffectsIntens(1.0F);
            setMovablePosition((float)(l - segment.timeIn) / (float)(segment.timeOut - segment.timeIn));
            return;
        }
        if(l > segment.timeOut)
        {
            while(cachedSeg + 1 < path.size()) 
            {
                com.maddox.il2.objects.ships.Segment segment1 = (com.maddox.il2.objects.ships.Segment)path.get(++cachedSeg);
                if(l <= segment1.timeIn)
                {
                    SetEffectsIntens(0.0F);
                    setMovablePosition(0.0F);
                    return;
                }
                if(l <= segment1.timeOut)
                {
                    SetEffectsIntens(1.0F);
                    setMovablePosition((float)(l - segment1.timeIn) / (float)(segment1.timeOut - segment1.timeIn));
                    return;
                }
            }
            SetEffectsIntens(-1F);
            setMovablePosition(1.0F);
            return;
        }
        while(cachedSeg > 0) 
        {
            com.maddox.il2.objects.ships.Segment segment2 = (com.maddox.il2.objects.ships.Segment)path.get(--cachedSeg);
            if(l >= segment2.timeOut)
            {
                SetEffectsIntens(0.0F);
                setMovablePosition(1.0F);
                return;
            }
            if(l >= segment2.timeIn)
            {
                SetEffectsIntens(1.0F);
                setMovablePosition((float)(l - segment2.timeIn) / (float)(segment2.timeOut - segment2.timeIn));
                return;
            }
        }
        SetEffectsIntens(0.0F);
        setMovablePosition(0.0F);
    }

    private void setMovablePosition(float f)
    {
        com.maddox.il2.objects.ships.Segment segment = (com.maddox.il2.objects.ships.Segment)path.get(cachedSeg);
        float f1 = (float)(segment.timeOut - segment.timeIn) * 0.001F;
        float f2 = segment.speedIn;
        float f3 = segment.speedOut;
        float f4 = (f3 - f2) / f1;
        f *= f1;
        float f5 = f2 * f + f4 * f * f * 0.5F;
        int i = cachedSeg;
        float f6 = prop.SLIDER_DIST - (segment.length - f5);
        if(f6 <= 0.0F)
            p1.interpolate(segment.posIn, segment.posOut, (f5 + prop.SLIDER_DIST) / segment.length);
        else
            do
            {
                if(i + 1 >= path.size())
                {
                    p1.interpolate(segment.posIn, segment.posOut, 1.0F + f6 / segment.length);
                    break;
                }
                segment = (com.maddox.il2.objects.ships.Segment)path.get(++i);
                if(f6 <= segment.length)
                {
                    p1.interpolate(segment.posIn, segment.posOut, f6 / segment.length);
                    break;
                }
                f6 -= segment.length;
            } while(true);
        i = cachedSeg;
        segment = (com.maddox.il2.objects.ships.Segment)path.get(i);
        f6 = prop.SLIDER_DIST - f5;
        if(f6 <= 0.0F)
            p2.interpolate(segment.posIn, segment.posOut, (f5 - prop.SLIDER_DIST) / segment.length);
        else
            do
            {
                if(i <= 0)
                {
                    p2.interpolate(segment.posIn, segment.posOut, 0.0F - f6 / segment.length);
                    break;
                }
                segment = (com.maddox.il2.objects.ships.Segment)path.get(--i);
                if(f6 <= segment.length)
                {
                    p2.interpolate(segment.posIn, segment.posOut, 1.0F - f6 / segment.length);
                    break;
                }
                f6 -= segment.length;
            } while(true);
        p.interpolate(p1, p2, 0.5F);
        tmpv.sub(p1, p2);
        if(tmpv.lengthSquared() < 0.0010000000474974513D)
        {
            com.maddox.il2.objects.ships.Segment segment1 = (com.maddox.il2.objects.ships.Segment)path.get(cachedSeg);
            tmpv.sub(segment1.posOut, segment1.posIn);
        }
        float f7 = (float)(java.lang.Math.atan2(tmpv.y, tmpv.x) * 57.295779513082323D);
        setPosition(p, f7);
    }

    private void setPosition(com.maddox.JGP.Point3d point3d, float f)
    {
        bodyYaw = f;
        o.setYPR(bodyYaw, bodyPitch, bodyRoll);
        point3d.z = -bodyDepth;
        pos.setAbs(point3d, o);
    }

    private void setPosition()
    {
        o.setYPR(bodyYaw, bodyPitch, bodyRoll);
        pos.setAbs(o);
        align();
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
        if(abulletproperties[0].power <= 0.0F)
            return 0;
        if(abulletproperties[1].power <= 0.0F)
            return 1;
        if(abulletproperties[0].cumulativePower > 0.0F)
            return 0;
        if(abulletproperties[1].cumulativePower > 0.0F)
            return 1;
        if(abulletproperties[0].powerType == 0)
            return 0;
        if(abulletproperties[1].powerType == 0)
            return 1;
        return abulletproperties[0].powerType != 1 ? 0 : 1;
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

    private void send_DeathCommand(com.maddox.il2.engine.Actor actor)
    {
        if(!isNetMaster())
            return;
        if(com.maddox.il2.game.Mission.isDeathmatch())
        {
            float f = com.maddox.il2.game.Mission.respawnTime("Ship");
            respawnDelay = com.maddox.il2.objects.ships.ShipGeneric.SecsToTicks(com.maddox.il2.objects.ships.ShipGeneric.Rnd(f, f * 1.2F));
        } else
        {
            respawnDelay = 0L;
        }
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        try
        {
            netmsgguaranted.writeByte(68);
            netmsgguaranted.writeLong(timeOfDeath);
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

    private void send_FireCommand(int i, com.maddox.il2.engine.Actor actor, int j, float f)
    {
        if(!isNetMaster())
            return;
        if(!net.isMirrored())
            return;
        if(!com.maddox.il2.engine.Actor.isValid(actor) || !actor.isNet())
            return;
        j &= 0xff;
        if(f < 0.0F)
            try
            {
                outCommand.unLockAndClear();
                outCommand.writeByte(84);
                outCommand.writeByte(i);
                outCommand.writeNetObj(actor.net);
                outCommand.writeByte(j);
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
                outCommand.writeByte(i);
                outCommand.writeFloat(f);
                outCommand.writeNetObj(actor.net);
                outCommand.writeByte(j);
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
            netmsgguaranted.writeLong(-1L);
        else
            netmsgguaranted.writeLong(timeOfDeath);
        net.postTo(netchannel, netmsgguaranted);
    }

    public float getReloadingTime(com.maddox.il2.ai.ground.Aim aim)
    {
        return SLOWFIRE_K * GetGunProperties(aim).DELAY_AFTER_SHOOT;
    }

    public float chainFireTime(com.maddox.il2.ai.ground.Aim aim)
    {
        float f = GetGunProperties(aim).CHAINFIRE_TIME;
        return f > 0.0F ? f * com.maddox.il2.objects.ships.ShipGeneric.Rnd(0.75F, 1.25F) : 0.0F;
    }

    public float probabKeepSameEnemy(com.maddox.il2.engine.Actor actor)
    {
        return 0.75F;
    }

    public float minTimeRelaxAfterFight()
    {
        return 0.1F;
    }

    public void gunStartParking(com.maddox.il2.ai.ground.Aim aim)
    {
        com.maddox.il2.objects.ships.FiringDevice firingdevice = GetFiringDevice(aim);
        com.maddox.il2.objects.ships.ShipGunProperties shipgunproperties = prop.guns[firingdevice.id];
        aim.setRotationForParking(firingdevice.headYaw, firingdevice.gunPitch, shipgunproperties.HEAD_STD_YAW, shipgunproperties.GUN_STD_PITCH, shipgunproperties.HEAD_YAW_RANGE, shipgunproperties.HEAD_MAX_YAW_SPEED, shipgunproperties.GUN_MAX_PITCH_SPEED);
    }

    public void gunInMove(boolean flag, com.maddox.il2.ai.ground.Aim aim)
    {
        com.maddox.il2.objects.ships.FiringDevice firingdevice = GetFiringDevice(aim);
        float f = aim.t();
        float f1 = aim.anglesYaw.getDeg(f);
        float f2 = aim.anglesPitch.getDeg(f);
        setGunAngles(firingdevice, f1, f2);
        pos.inValidate(false);
    }

    public com.maddox.il2.engine.Actor findEnemy(com.maddox.il2.ai.ground.Aim aim)
    {
        if(isNetMirror())
            return null;
        com.maddox.il2.objects.ships.ShipGunProperties shipgunproperties = GetGunProperties(aim);
        com.maddox.il2.engine.Actor actor = null;
        switch(shipgunproperties.ATTACK_FAST_TARGETS)
        {
        case 0: // '\0'
            com.maddox.il2.ai.ground.NearestEnemies.set(shipgunproperties.WEAPONS_MASK, -9999.9F, com.maddox.il2.objects.ships.ShipGeneric.KmHourToMSec(100F));
            break;

        case 1: // '\001'
            com.maddox.il2.ai.ground.NearestEnemies.set(shipgunproperties.WEAPONS_MASK);
            break;

        default:
            com.maddox.il2.ai.ground.NearestEnemies.set(shipgunproperties.WEAPONS_MASK, com.maddox.il2.objects.ships.ShipGeneric.KmHourToMSec(100F), 9999.9F);
            break;
        }
        actor = com.maddox.il2.ai.ground.NearestEnemies.getAFoundEnemy(pos.getAbsPoint(), shipgunproperties.ATTACK_MAX_RADIUS, getArmy());
        if(actor == null)
            return null;
        if(!(actor instanceof com.maddox.il2.ai.ground.Prey))
        {
            java.lang.System.out.println("ship: nearest enemies: non-Prey");
            return null;
        }
        com.maddox.il2.objects.ships.FiringDevice firingdevice = GetFiringDevice(aim);
        com.maddox.il2.engine.BulletProperties bulletproperties = null;
        if(firingdevice.gun.prop != null)
        {
            int i = ((com.maddox.il2.ai.ground.Prey)actor).chooseBulletType(firingdevice.gun.prop.bullet);
            if(i < 0)
                return null;
            bulletproperties = firingdevice.gun.prop.bullet[i];
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
        if(!isNetMirror())
        {
            com.maddox.il2.objects.ships.FiringDevice firingdevice = GetFiringDevice(aim);
            send_FireCommand(firingdevice.id, actor, aim.shotpoint_idx, i != 0 ? f : -1F);
        }
        return true;
    }

    private void Track_Mirror(int i, com.maddox.il2.engine.Actor actor, int j)
    {
        if(actor == null)
            return;
        if(arms == null || arms[i].aime == null)
        {
            return;
        } else
        {
            arms[i].aime.passive_StartFiring(0, actor, j, 0.0F);
            return;
        }
    }

    private void Fire_Mirror(int i, com.maddox.il2.engine.Actor actor, int j, float f)
    {
        if(actor == null)
            return;
        if(arms == null || arms[i].aime == null)
            return;
        if(f <= 0.2F)
            f = 0.2F;
        if(f >= 15F)
            f = 15F;
        arms[i].aime.passive_StartFiring(1, actor, j, f);
    }

    public int targetGun(com.maddox.il2.ai.ground.Aim aim, com.maddox.il2.engine.Actor actor, float f, boolean flag)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor) || !actor.isAlive() || actor.getArmy() == 0)
            return 0;
        com.maddox.il2.objects.ships.FiringDevice firingdevice = GetFiringDevice(aim);
        if(firingdevice.gun instanceof com.maddox.il2.objects.weapons.CannonMidrangeGeneric)
        {
            int i = ((com.maddox.il2.ai.ground.Prey)actor).chooseBulletType(firingdevice.gun.prop.bullet);
            if(i < 0)
                return 0;
            ((com.maddox.il2.objects.weapons.CannonMidrangeGeneric)firingdevice.gun).setBulletType(i);
        }
        boolean flag1 = ((com.maddox.il2.ai.ground.Prey)actor).getShotpointOffset(aim.shotpoint_idx, p1);
        if(!flag1)
            return 0;
        com.maddox.il2.objects.ships.ShipGunProperties shipgunproperties = prop.guns[firingdevice.id];
        float f1 = f * com.maddox.il2.objects.ships.ShipGeneric.Rnd(0.8F, 1.2F);
        if(!com.maddox.il2.ai.Aimer.Aim((com.maddox.il2.ai.BulletAimer)firingdevice.gun, actor, this, f1, p1, shipgunproperties.fireOffset))
            return 0;
        com.maddox.JGP.Point3d point3d = new Point3d();
        com.maddox.il2.ai.Aimer.GetPredictedTargetPosition(point3d);
        com.maddox.JGP.Point3d point3d1 = com.maddox.il2.ai.Aimer.GetHunterFirePoint();
        float f2 = 0.05F;
        double d = point3d.distance(point3d1);
        double d1 = point3d.z;
        point3d.sub(point3d1);
        point3d.scale(com.maddox.il2.objects.ships.ShipGeneric.Rnd(0.995D, 1.0049999999999999D));
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
                float f8 = f7 * 0.01F;
                point3d2.sub(point3d1);
                double d3 = point3d2.x * point3d2.x + point3d2.y * point3d2.y + point3d2.z * point3d2.z;
                if(d3 > 0.01D)
                {
                    float f9 = (float)tmpv.dot(point3d2);
                    f9 /= (float)(d2 * java.lang.Math.sqrt(d3));
                    f9 = (float)java.lang.Math.sqrt(1.0F - f9 * f9);
                    f8 *= 0.4F + 0.6F * f9;
                }
                f8 *= 1.3F;
                f8 *= com.maddox.il2.ai.ground.Aim.AngleErrorKoefForSkill[SKILL_IDX];
                int k = com.maddox.il2.game.Mission.curCloudsType();
                if(k > 2)
                {
                    float f10 = k <= 4 ? 800F : 400F;
                    float f11 = (float)(d / (double)f10);
                    if(f11 > 1.0F)
                    {
                        if(f11 > 10F)
                            return 0;
                        f11 = (f11 - 1.0F) / 9F;
                        f8 *= f11 + 1.0F;
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
            f2 += 10F * f4;
        }
        float f5 = (float)actor.getSpeed(null) - 10F;
        if(f5 > 0.0F)
        {
            float f6 = 83.33334F;
            f5 = f5 < f6 ? f5 / f6 : 1.0F;
            f2 += f5 * shipgunproperties.FAST_TARGETS_ANGLE_ERROR;
        }
        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        if(!((com.maddox.il2.ai.BulletAimer)firingdevice.gun).FireDirection(point3d1, point3d, vector3d))
            return 0;
        float f3;
        if(flag)
        {
            f3 = 99999F;
            d1 = 99999F;
        } else
        {
            f3 = shipgunproperties.HEAD_MAX_YAW_SPEED;
            d1 = shipgunproperties.GUN_MAX_PITCH_SPEED;
        }
        o.add(shipgunproperties.fireOrient, pos.getAbs().getOrient());
        int j = aim.setRotationForTargeting(this, o, point3d1, firingdevice.headYaw, firingdevice.gunPitch, vector3d, f2, f1, shipgunproperties.HEAD_YAW_RANGE, shipgunproperties.GUN_MIN_PITCH, shipgunproperties.GUN_MAX_PITCH, f3, d1, 0.0F);
        return j;
    }

    public void singleShot(com.maddox.il2.ai.ground.Aim aim)
    {
        com.maddox.il2.objects.ships.FiringDevice firingdevice = GetFiringDevice(aim);
        if(!prop.guns[firingdevice.id].TRACKING_ONLY)
            firingdevice.gun.shots(1);
    }

    public void startFire(com.maddox.il2.ai.ground.Aim aim)
    {
        com.maddox.il2.objects.ships.FiringDevice firingdevice = GetFiringDevice(aim);
        if(!prop.guns[firingdevice.id].TRACKING_ONLY)
            firingdevice.gun.shots(-1);
    }

    public void continueFire(com.maddox.il2.ai.ground.Aim aim)
    {
    }

    public void stopFire(com.maddox.il2.ai.ground.Aim aim)
    {
        com.maddox.il2.objects.ships.FiringDevice firingdevice = GetFiringDevice(aim);
        if(!prop.guns[firingdevice.id].TRACKING_ONLY)
            firingdevice.gun.shots(0);
    }

    public boolean isVisibilityLong()
    {
        return true;
    }

    public int zutiGetDying()
    {
        return dying;
    }

    public boolean zutiIsStatic()
    {
        return path == null || path.size() <= 0;
    }

    private com.maddox.il2.objects.ships.ShipProperties prop;
    private com.maddox.il2.objects.ships.FiringDevice arms[];
    private java.util.ArrayList path;
    private int cachedSeg;
    private float bodyDepth;
    private float bodyYaw;
    private float bodyPitch;
    private float bodyRoll;
    private float seaDepth;
    private long timeOfDeath;
    private long timeForRotation;
    private float drownBodyPitch;
    private float drownBodyRoll;
    private float sinkingDepthSpeed;
    private float life;
    private int dying;
    static final int DYING_NONE = 0;
    static final int DYING_SINK = 1;
    static final int DYING_DEAD = 2;
    private long respawnDelay;
    private long wakeupTmr;
    public float DELAY_WAKEUP;
    public int SKILL_IDX;
    public float SLOWFIRE_K;
    private com.maddox.il2.engine.Eff3DActor pipe;
    private com.maddox.il2.engine.Eff3DActor wake[] = {
        null, null, null
    };
    private com.maddox.il2.engine.Eff3DActor nose;
    private com.maddox.il2.engine.Eff3DActor tail;
    private static com.maddox.il2.objects.ships.ShipProperties constr_arg1 = null;
    private static com.maddox.il2.engine.ActorSpawnArg constr_arg2 = null;
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.JGP.Point3d p1 = new Point3d();
    private static com.maddox.JGP.Point3d p2 = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();
    private static com.maddox.JGP.Vector3f n = new Vector3f();
    private static com.maddox.JGP.Vector3d tmpv = new Vector3d();
    private com.maddox.rts.NetMsgFiltered outCommand;






































}
