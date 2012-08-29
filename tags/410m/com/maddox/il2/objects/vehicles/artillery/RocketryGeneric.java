// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RocketryGeneric.java

package com.maddox.il2.objects.vehicles.artillery;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector2d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.StrengthProperties;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiPadObject;
import com.maddox.il2.gui.GUI;
import com.maddox.il2.gui.GUIPad;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.maddox.il2.objects.vehicles.artillery:
//            RocketryRocket

public class RocketryGeneric extends com.maddox.il2.engine.ActorMesh
    implements com.maddox.il2.engine.MsgCollisionListener, com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener, com.maddox.il2.ai.ground.Prey
{
    class Mirror extends com.maddox.il2.engine.ActorNet
    {

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            char c = (char)netmsginput.readByte();
            if(netmsginput.isGuaranted())
                switch(c)
                {
                case 97: // 'a'
                case 98: // 'b'
                case 101: // 'e'
                case 102: // 'f'
                case 108: // 'l'
                case 114: // 'r'
                case 120: // 'x'
                    if(!isMaster())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(netmsginput, 1);
                        postTo(masterChannel(), netmsgguaranted);
                    }
                    return true;

                case 65: // 'A'
                case 66: // 'B'
                case 68: // 'D'
                case 69: // 'E'
                case 70: // 'F'
                case 76: // 'L'
                case 82: // 'R'
                case 88: // 'X'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted(netmsginput, 1);
                        post(netmsgguaranted1);
                    }
                    int i = netmsginput.readUnsignedByte();
                    int i1 = netmsginput.readUnsignedShort();
                    com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                    com.maddox.il2.engine.Actor actor = netobj != null ? ((com.maddox.il2.engine.ActorNet)netobj).actor() : null;
                    if(c != 'D')
                        handleRocketCommand_Both(c, i, i1, actor, false);
                    else
                        handleDamageRamp_Both(1.0F, actor, false);
                    return true;

                case 80: // 'P'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted2 = new NetMsgGuaranted(netmsginput, 0);
                        post(netmsgguaranted2);
                    }
                    int j = netmsginput.readUnsignedShort();
                    int j1 = netmsginput.readUnsignedByte();
                    int l1 = netmsginput.readUnsignedShort();
                    handlePrepareLaunchCommand_Mirror(j, j1, l1);
                    return true;

                case 83: // 'S'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted3 = new NetMsgGuaranted(netmsginput, 0);
                        post(netmsgguaranted3);
                    }
                    int k = netmsginput.readUnsignedByte();
                    handleRespawnCommand_Mirror(k);
                    return true;

                case 73: // 'I'
                    boolean flag = netmsginput.readByte() != 0;
                    int k1 = netmsginput.readUnsignedByte();
                    int i2 = netmsginput.readUnsignedByte();
                    if(i2 > 10)
                        i2 = 10;
                    com.maddox.il2.objects.vehicles.artillery.RocketInGame arocketingame[] = new com.maddox.il2.objects.vehicles.artillery.RocketInGame[i2];
                    for(int j2 = 0; j2 < i2; j2++)
                    {
                        arocketingame[j2] = new RocketInGame();
                        arocketingame[j2].idR = netmsginput.readUnsignedByte();
                        arocketingame[j2].timeAfterStartS = netmsginput.readFloat();
                        arocketingame[j2].randseed = netmsginput.readUnsignedShort();
                    }

                    handleInitCommand_Mirror(flag, k1, arocketingame);
                    return true;

                case 67: // 'C'
                case 71: // 'G'
                case 72: // 'H'
                case 74: // 'J'
                case 75: // 'K'
                case 77: // 'M'
                case 78: // 'N'
                case 79: // 'O'
                case 81: // 'Q'
                case 84: // 'T'
                case 85: // 'U'
                case 86: // 'V'
                case 87: // 'W'
                case 89: // 'Y'
                case 90: // 'Z'
                case 91: // '['
                case 92: // '\\'
                case 93: // ']'
                case 94: // '^'
                case 95: // '_'
                case 96: // '`'
                case 99: // 'c'
                case 100: // 'd'
                case 103: // 'g'
                case 104: // 'h'
                case 105: // 'i'
                case 106: // 'j'
                case 107: // 'k'
                case 109: // 'm'
                case 110: // 'n'
                case 111: // 'o'
                case 112: // 'p'
                case 113: // 'q'
                case 115: // 's'
                case 116: // 't'
                case 117: // 'u'
                case 118: // 'v'
                case 119: // 'w'
                default:
                    return false;
                }
            switch(c)
            {
            case 45: // '-'
                if(!com.maddox.il2.net.NetMissionTrack.isPlaying())
                {
                    out.unLockAndSet(netmsginput, 1);
                    out.setIncludeTime(false);
                    postRealTo(com.maddox.rts.Message.currentRealTime(), net.masterChannel(), out);
                }
                return true;

            case 89: // 'Y'
                if(isMirrored())
                {
                    out.unLockAndSet(netmsginput, 0);
                    out.setIncludeTime(true);
                    postReal(com.maddox.rts.Message.currentRealTime(), out);
                }
                long l = (long)((double)netmsginput.readFloat() * 1000D);
                long l2 = com.maddox.rts.Message.currentGameTime() + l;
                int k2 = netmsginput.readUnsignedByte();
                handleSyncLaunchCommand_Mirror(l2, k2);
                return true;
            }
            return false;
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
            char c = (char)netmsginput.readByte();
            if(netmsginput.isGuaranted())
                switch(c)
                {
                case 97: // 'a'
                case 98: // 'b'
                case 101: // 'e'
                case 102: // 'f'
                case 108: // 'l'
                case 114: // 'r'
                case 120: // 'x'
                    int i = netmsginput.readUnsignedByte();
                    int j = netmsginput.readUnsignedShort();
                    com.maddox.rts.NetObj netobj1 = netmsginput.readNetObj();
                    com.maddox.il2.engine.Actor actor1 = netobj1 != null ? ((com.maddox.il2.engine.ActorNet)netobj1).actor() : null;
                    handleRocketCommand_Both(c, i, j, actor1, true);
                    return true;

                case 99: // 'c'
                case 100: // 'd'
                case 103: // 'g'
                case 104: // 'h'
                case 105: // 'i'
                case 106: // 'j'
                case 107: // 'k'
                case 109: // 'm'
                case 110: // 'n'
                case 111: // 'o'
                case 112: // 'p'
                case 113: // 'q'
                case 115: // 's'
                case 116: // 't'
                case 117: // 'u'
                case 118: // 'v'
                case 119: // 'w'
                default:
                    return false;
                }
            if(c != '-')
            {
                return false;
            } else
            {
                float f = (float)netmsginput.readUnsignedShort() / 65000F;
                com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                com.maddox.il2.engine.Actor actor = netobj != null ? ((com.maddox.il2.engine.ActorNet)netobj).actor() : null;
                handleDamageRamp_Both(f, actor, true);
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
            if(com.maddox.rts.Time.current() < actionTimeMS)
                return true;
            if(damage >= 1.0F)
            {
                if(!com.maddox.il2.game.Mission.isDeathmatch())
                    return false;
                if(!isNetMaster())
                {
                    actionTimeMS = com.maddox.rts.Time.current() + 0x1869fL;
                    return true;
                } else
                {
                    damage = 0.0F;
                    actionTimeMS = com.maddox.rts.Time.current() + (1000L * (long)launchIntervalS) / 2L;
                    setMesh(meshNames.ramp);
                    setDiedFlag(false);
                    sendRespawn_Master();
                    return true;
                }
            }
            if(isNetMaster())
                prepareLaunch_Master(launchIntervalS / 2);
            else
                actionTimeMS = com.maddox.rts.Time.current() + 0x1869fL;
            return true;
        }

        Move()
        {
        }
    }

    public static class RocketInGame
    {

        public int idR;
        public float timeAfterStartS;
        public int randseed;

        public RocketInGame()
        {
        }
    }

    public static class TrajSeg
    {

        public double t0;
        public double t;
        public com.maddox.JGP.Point3d pos0;
        public com.maddox.JGP.Vector3d v0;
        public com.maddox.JGP.Vector3d a;

        public TrajSeg()
        {
            pos0 = new Point3d();
            v0 = new Vector3d();
            a = new Vector3d();
        }
    }

    public static class RocketryProperties
    {

        public java.lang.String name;
        public com.maddox.il2.objects.vehicles.artillery.MeshesNames summerNames;
        public com.maddox.il2.objects.vehicles.artillery.MeshesNames desertNames;
        public com.maddox.il2.objects.vehicles.artillery.MeshesNames winterNames;
        public java.lang.String soundName;
        public boolean air;
        public float MASS_FULL;
        public float MASS_TNT;
        public float EXPLOSION_RADIUS;
        public float TAKEOFF_SPEED;
        public float MAX_SPEED;
        public float SPEEDUP_TIME;
        public float FLY_HEIGHT;
        public float HIT_ANGLE;
        public float MAX_ERR_HEIGHT;
        public float MAX_ERR_HIT_DISTANCE;
        public com.maddox.il2.ai.StrengthProperties stre;
        public float DMG_WARHEAD_MM;
        public float DMG_WARHEAD_PROB;
        public float DMG_FUEL_MM;
        public float DMG_FUEL_PROB;
        public float DMG_ENGINE_MM;
        public float DMG_ENGINE_PROB;
        public float DMG_WING_MM;
        public float DMG_WING_PROB;
        public float DMG_WARHEAD_TNT;
        public float DMG_WING_TNT;

        public RocketryProperties()
        {
            name = null;
            summerNames = new MeshesNames();
            desertNames = new MeshesNames();
            winterNames = new MeshesNames();
            soundName = null;
            air = false;
            MASS_FULL = 200F;
            MASS_TNT = 100F;
            EXPLOSION_RADIUS = 500F;
            TAKEOFF_SPEED = 1.0F;
            MAX_SPEED = 1.0F;
            SPEEDUP_TIME = 1.0F;
            FLY_HEIGHT = 1.0F;
            HIT_ANGLE = 30F;
            MAX_ERR_HEIGHT = 0.0F;
            MAX_ERR_HIT_DISTANCE = 0.0F;
            stre = new StrengthProperties();
            DMG_WARHEAD_MM = 0.0F;
            DMG_WARHEAD_PROB = 0.0F;
            DMG_FUEL_MM = 0.0F;
            DMG_FUEL_PROB = 0.0F;
            DMG_ENGINE_MM = 0.0F;
            DMG_ENGINE_PROB = 0.0F;
            DMG_WING_MM = 0.0F;
            DMG_WING_PROB = 0.0F;
            DMG_WARHEAD_TNT = 0.0F;
            DMG_WING_TNT = 0.0F;
        }
    }

    public static class MeshesNames
    {

        public void setNull()
        {
            ramp = null;
            ramp_d = null;
            wagon = null;
            rocket = null;
        }

        public java.lang.String ramp;
        public java.lang.String ramp_d;
        public java.lang.String wagon;
        public java.lang.String rocket;

        public MeshesNames()
        {
        }
    }


    private final boolean Corpse()
    {
        return damage >= 1.0F;
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public boolean isStaticPos()
    {
        return true;
    }

    public static final float RndF(float f, float f1)
    {
        return com.maddox.il2.ai.World.Rnd().nextFloat(f, f1);
    }

    public static final int RndI(int i, int j)
    {
        return com.maddox.il2.ai.World.Rnd().nextInt(i, j);
    }

    private static final long SecsToTicks(float f)
    {
        long l = (long)(0.5D + (double)(f / com.maddox.rts.Time.tickLenFs()));
        return l >= 1L ? l : 1L;
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if(Corpse())
            return;
        if(!com.maddox.il2.engine.Actor.isValid(actor))
            return;
        if(actor.net != null && actor.net.isMirror())
            return;
        if(actor instanceof com.maddox.il2.objects.Wreckage)
            return;
        if(!(actor instanceof com.maddox.il2.objects.air.Aircraft))
            return;
        if(actor.getSpeed(null) < 28D)
            return;
        if(s1 != null && (s1.startsWith("Wing") || s1.startsWith("Stab") || s1.startsWith("Arone") || s1.startsWith("Vator") || s1.startsWith("Keel") || s1.startsWith("Rudder") || s1.startsWith("Pilot")))
            return;
        if(isNetMirror())
            sendRampDamage_Mirror(1.0F, actor);
        else
            handleDamageRamp_Both(1.0F, actor, true);
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        shot.bodyMaterial = 2;
        if(Corpse())
            return;
        if(shot.power <= 0.0F)
            return;
        if(isNetMirror() && shot.isMirage())
            return;
        float f = shot.power;
        if(shot.powerType == 1)
            f = shot.power / 2.4E-007F;
        f *= com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.RndF(1.0F, 1.1F);
        if(f < prop.stre.SHOT_MIN_ENERGY)
            return;
        float f1 = f / prop.stre.SHOT_MAX_ENERGY;
        if(isNetMirror())
            sendRampDamage_Mirror(f1, shot.initiator);
        else
            handleDamageRamp_Both(f1, shot.initiator, true);
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        if(Corpse())
            return;
        if(isNetMirror() && explosion.isMirage())
            return;
        float f = explosion.power;
        com.maddox.il2.ai.Explosion _tmp = explosion;
        if(explosion.powerType == 2 && explosion.chunkName != null)
            f *= 4F;
        float f1 = -1F;
        if(explosion.chunkName != null)
        {
            float f2 = f;
            f2 *= com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.RndF(1.0F, 1.1F);
            if(f2 >= prop.stre.EXPLHIT_MIN_TNT)
                f1 = f2 / prop.stre.EXPLHIT_MAX_TNT;
        }
        if(f1 < 0.0F)
        {
            float f3 = explosion.receivedTNT_1meter(this);
            f3 *= com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.RndF(1.0F, 1.1F);
            if(f3 >= prop.stre.EXPLNEAR_MIN_TNT)
                f1 = f3 / prop.stre.EXPLNEAR_MAX_TNT;
        }
        if(f1 > 0.0F)
            if(isNetMirror())
                sendRampDamage_Mirror(f1, explosion.initiator);
            else
                handleDamageRamp_Both(f1, explosion.initiator, true);
    }

    private static void getHookOffset(com.maddox.il2.engine.Mesh mesh, java.lang.String s, boolean flag, com.maddox.JGP.Point3d point3d)
    {
        int i = mesh.hookFind(s);
        if(i == -1)
        {
            if(flag)
            {
                java.lang.System.out.println("Rocketry: Hook [" + s + "] not found");
                throw new RuntimeException("Can't work");
            }
            point3d.set(0.0D, 0.0D, 0.0D);
        }
        com.maddox.JGP.Matrix4d matrix4d = new Matrix4d();
        mesh.hookMatrix(i, matrix4d);
        point3d.set(matrix4d.m03, matrix4d.m13, matrix4d.m23);
    }

    com.maddox.il2.objects.vehicles.artillery.TrajSeg[] _computeFallTrajectory_(int i, com.maddox.JGP.Point3d point3d, com.maddox.JGP.Vector3d vector3d)
    {
        com.maddox.il2.objects.vehicles.artillery.TrajSeg atrajseg[] = new com.maddox.il2.objects.vehicles.artillery.TrajSeg[1];
        for(int j = 0; j < atrajseg.length; j++)
            atrajseg[j] = new TrajSeg();

        atrajseg[0].pos0.set(point3d);
        atrajseg[0].v0.set(vector3d);
        atrajseg[0].a.set(0.0D, 0.0D, -3D);
        atrajseg[0].t0 = 0.0D;
        atrajseg[0].t = 500D;
        return atrajseg;
    }

    com.maddox.il2.objects.vehicles.artillery.TrajSeg[] _computeWagonTrajectory_(int i)
    {
        rndSeed.setSeed(i);
        com.maddox.JGP.Vector2d vector2d = new Vector2d();
        vector2d.set(endPos_wagon.x - begPos_wagon.x, endPos_wagon.y - begPos_wagon.y);
        vector2d.normalize();
        com.maddox.il2.objects.vehicles.artillery.TrajSeg atrajseg[] = new com.maddox.il2.objects.vehicles.artillery.TrajSeg[2];
        for(int j = 0; j < atrajseg.length; j++)
            atrajseg[j] = new TrajSeg();

        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        float f = prop.TAKEOFF_SPEED;
        vector3d.sub(endPos_wagon, begPos_wagon);
        double d = vector3d.length();
        vector3d.scale(1.0D / d);
        atrajseg[0].v0.set(0.0D, 0.0D, 0.0D);
        atrajseg[0].pos0.set(begPos_wagon);
        atrajseg[0].t = (2D * d) / (0.0D + (double)f);
        atrajseg[0].a.set(vector3d);
        atrajseg[0].a.scale((double)f / atrajseg[0].t);
        f = prop.TAKEOFF_SPEED * rndSeed.nextFloat(0.85F, 0.97F);
        double d1 = vector3d.z * d;
        double d2 = java.lang.Math.sqrt(d * d - d1 * d1);
        atrajseg[1].v0.set(d2, 0.0D, d1);
        atrajseg[1].v0.normalize();
        atrajseg[1].v0.scale(f);
        atrajseg[1].pos0.set(0.0D, 0.0D, endPos_wagon.z);
        atrajseg[1].t = 30D + 2D * (atrajseg[1].v0.z / 9.8000000000000007D);
        atrajseg[1].a.set(0.0D, 0.0D, -9.8000000000000007D);
        for(int k = 0; k <= 1; k++)
            if(atrajseg[k].t <= 0.0D)
                return null;

        atrajseg[0].t0 = 0.0D;
        for(int l = 1; l <= 1; l++)
            atrajseg[l].t0 = atrajseg[l - 1].t0 + atrajseg[l - 1].t;

        for(int i1 = 1; i1 <= 1; i1++)
        {
            atrajseg[i1].pos0.set(vector2d.x * atrajseg[i1].pos0.x, vector2d.y * atrajseg[i1].pos0.x, atrajseg[i1].pos0.z);
            atrajseg[i1].pos0.add(endPos_wagon.x, endPos_wagon.y, 0.0D);
            atrajseg[i1].v0.set(vector2d.x * atrajseg[i1].v0.x, vector2d.y * atrajseg[i1].v0.x, atrajseg[i1].v0.z);
            atrajseg[i1].a.set(vector2d.x * atrajseg[i1].a.x, vector2d.y * atrajseg[i1].a.x, atrajseg[i1].a.z);
        }

        return atrajseg;
    }

    private com.maddox.il2.objects.vehicles.artillery.TrajSeg[] _computeAirTrajectory_(int i)
    {
        rndSeed.setSeed(i);
        com.maddox.JGP.Point3d point3d = new Point3d();
        point3d.set(targetPos);
        double d = rndSeed.nextFloat(0.0F, 359.99F);
        float f = rndSeed.nextFloat(0.0F, prop.MAX_ERR_HIT_DISTANCE);
        point3d.add(com.maddox.JGP.Geom.cosDeg(d) * f, com.maddox.JGP.Geom.sinDeg(d) * f, 0.0D);
        d = prop.FLY_HEIGHT + rndSeed.nextFloat(-prop.MAX_ERR_HEIGHT, prop.MAX_ERR_HEIGHT);
        com.maddox.JGP.Point3d point3d1 = new Point3d();
        point3d1.set(pos.getAbsPoint());
        point3d1.z = d;
        java.lang.Object obj = new Point2d(point3d.x, point3d.y);
        com.maddox.JGP.Point2d point2d = new Point2d(point3d1.x, point3d1.y);
        double d1 = ((com.maddox.JGP.Point2d) (obj)).distance(point2d);
        obj = new Vector2d();
        ((com.maddox.JGP.Vector2d) (obj)).set(point3d.x - point3d1.x, point3d.y - point3d1.y);
        ((com.maddox.JGP.Vector2d) (obj)).normalize();
        float f1 = prop.MAX_SPEED;
        com.maddox.il2.objects.vehicles.artillery.TrajSeg atrajseg[] = new com.maddox.il2.objects.vehicles.artillery.TrajSeg[3];
        for(int j = 0; j < atrajseg.length; j++)
            atrajseg[j] = new TrajSeg();

        atrajseg[0].v0.set(f1, 0.0D, 0.0D);
        atrajseg[1].v0.set(f1, 0.0D, 0.0D);
        atrajseg[2].v0.set(f1 * com.maddox.JGP.Geom.cosDeg(prop.HIT_ANGLE), 0.0D, -f1 * com.maddox.JGP.Geom.sinDeg(prop.HIT_ANGLE));
        atrajseg[0].pos0.set(0.0D, 0.0D, d);
        atrajseg[2].pos0.set(d1, 0.0D, point3d.z);
        atrajseg[1].t = (2D * (atrajseg[2].pos0.z - d)) / (atrajseg[1].v0.z + atrajseg[2].v0.z);
        atrajseg[1].pos0.set(atrajseg[2].pos0.x - 0.5D * (atrajseg[1].v0.x + atrajseg[2].v0.x) * atrajseg[1].t, 0.0D, d);
        atrajseg[2].t = 100D;
        atrajseg[0].t = (2D * (atrajseg[1].pos0.x - atrajseg[0].pos0.x)) / (atrajseg[0].v0.x + atrajseg[1].v0.x);
        for(int k = 0; k <= 2; k++)
            if(atrajseg[k].t <= 0.0D)
                return null;

        atrajseg[0].a.set(0.0D, 0.0D, 0.0D);
        for(int l = 1; l <= 1; l++)
        {
            atrajseg[l].a.sub(atrajseg[l + 1].v0, atrajseg[l].v0);
            atrajseg[l].a.scale(1.0D / atrajseg[l].t);
        }

        atrajseg[2].a.set(0.0D, 0.0D, 0.0D);
        atrajseg[0].t0 = 0.0D;
        for(int i1 = 1; i1 <= 2; i1++)
            atrajseg[i1].t0 = atrajseg[i1 - 1].t0 + atrajseg[i1 - 1].t;

        for(int j1 = 0; j1 <= 2; j1++)
        {
            atrajseg[j1].pos0.set(((com.maddox.JGP.Vector2d) (obj)).x * atrajseg[j1].pos0.x, ((com.maddox.JGP.Vector2d) (obj)).y * atrajseg[j1].pos0.x, atrajseg[j1].pos0.z);
            atrajseg[j1].pos0.add(point3d1.x, point3d1.y, 0.0D);
            atrajseg[j1].v0.set(((com.maddox.JGP.Vector2d) (obj)).x * atrajseg[j1].v0.x, ((com.maddox.JGP.Vector2d) (obj)).y * atrajseg[j1].v0.x, atrajseg[j1].v0.z);
            atrajseg[j1].a.set(((com.maddox.JGP.Vector2d) (obj)).x * atrajseg[j1].a.x, ((com.maddox.JGP.Vector2d) (obj)).y * atrajseg[j1].a.x, atrajseg[j1].a.z);
        }

        return atrajseg;
    }

    private com.maddox.il2.objects.vehicles.artillery.TrajSeg[] _computeRampTrajectory_(int i, boolean flag)
    {
        rndSeed.setSeed(i);
        com.maddox.JGP.Point3d point3d = new Point3d();
        point3d.set(targetPos);
        double d = rndSeed.nextFloat(0.0F, 359.99F);
        float f = rndSeed.nextFloat(0.0F, prop.MAX_ERR_HIT_DISTANCE);
        point3d.add(com.maddox.JGP.Geom.cosDeg(d) * f, com.maddox.JGP.Geom.sinDeg(d) * f, 0.0D);
        java.lang.Object obj = new Point2d(point3d.x, point3d.y);
        com.maddox.JGP.Point2d point2d = new Point2d(endPos_rocket.x, endPos_rocket.y);
        d = ((com.maddox.JGP.Point2d) (obj)).distance(point2d);
        obj = new Vector2d();
        ((com.maddox.JGP.Vector2d) (obj)).set(point3d.x - endPos_rocket.x, point3d.y - endPos_rocket.y);
        ((com.maddox.JGP.Vector2d) (obj)).normalize();
        float f1 = flag ? 0.5F : 1.0F;
        double d1 = f1 * prop.FLY_HEIGHT + f1 * rndSeed.nextFloat(-prop.MAX_ERR_HEIGHT, prop.MAX_ERR_HEIGHT);
        float f2 = flag ? prop.TAKEOFF_SPEED + 1.0F : prop.MAX_SPEED;
        com.maddox.il2.objects.vehicles.artillery.TrajSeg atrajseg[] = new com.maddox.il2.objects.vehicles.artillery.TrajSeg[6];
        for(int j = 0; j < atrajseg.length; j++)
            atrajseg[j] = new TrajSeg();

        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        vector3d.sub(endPos_rocket, begPos_rocket);
        double d2 = vector3d.length();
        vector3d.scale(1.0D / d2);
        atrajseg[0].v0.set(0.0D, 0.0D, 0.0D);
        atrajseg[0].pos0.set(begPos_rocket);
        atrajseg[0].t = (2D * d2) / (0.0D + (double)prop.TAKEOFF_SPEED);
        atrajseg[0].a.set(vector3d);
        atrajseg[0].a.scale((double)prop.TAKEOFF_SPEED / atrajseg[0].t);
        double d3 = vector3d.z * d2;
        double d4 = java.lang.Math.sqrt(d2 * d2 - d3 * d3);
        atrajseg[1].v0.set(d4, 0.0D, d3);
        atrajseg[1].v0.normalize();
        atrajseg[1].v0.scale(prop.TAKEOFF_SPEED);
        atrajseg[2].v0.set(prop.TAKEOFF_SPEED, 0.0D, 0.0D);
        atrajseg[3].v0.set(f2, 0.0D, 0.0D);
        atrajseg[4].v0.set(f2, 0.0D, 0.0D);
        atrajseg[5].v0.set(f2 * com.maddox.JGP.Geom.cosDeg(prop.HIT_ANGLE), 0.0D, -f2 * com.maddox.JGP.Geom.sinDeg(prop.HIT_ANGLE));
        atrajseg[1].pos0.set(0.0D, 0.0D, endPos_rocket.z);
        atrajseg[1].t = (2D * (d1 - atrajseg[1].pos0.z)) / (atrajseg[1].v0.z + 0.0D);
        atrajseg[2].pos0.set(atrajseg[1].pos0.x + 0.5D * (atrajseg[1].v0.x + atrajseg[2].v0.x) * atrajseg[1].t, 0.0D, d1);
        atrajseg[2].t = flag ? 0.5D : prop.SPEEDUP_TIME;
        atrajseg[3].pos0.set(atrajseg[2].pos0.x + 0.5D * (atrajseg[2].v0.x + atrajseg[3].v0.x) * atrajseg[2].t, 0.0D, d1);
        atrajseg[5].pos0.set(d, 0.0D, point3d.z);
        atrajseg[4].t = (2D * (atrajseg[5].pos0.z - d1)) / (atrajseg[4].v0.z + atrajseg[5].v0.z);
        atrajseg[4].pos0.set(atrajseg[5].pos0.x - 0.5D * (atrajseg[4].v0.x + atrajseg[5].v0.x) * atrajseg[4].t, 0.0D, d1);
        atrajseg[5].t = 100D;
        atrajseg[3].t = (2D * (atrajseg[4].pos0.x - atrajseg[3].pos0.x)) / (atrajseg[3].v0.x + atrajseg[4].v0.x);
        for(int k = 0; k <= 5; k++)
            if(atrajseg[k].t <= 0.0D)
                return null;

        for(int l = 1; l <= 4; l++)
        {
            atrajseg[l].a.sub(atrajseg[l + 1].v0, atrajseg[l].v0);
            atrajseg[l].a.scale(1.0D / atrajseg[l].t);
        }

        atrajseg[5].a.set(0.0D, 0.0D, 0.0D);
        atrajseg[0].t0 = 0.0D;
        for(int i1 = 1; i1 <= 5; i1++)
            atrajseg[i1].t0 = atrajseg[i1 - 1].t0 + atrajseg[i1 - 1].t;

        for(int j1 = 1; j1 <= 5; j1++)
        {
            atrajseg[j1].pos0.set(((com.maddox.JGP.Vector2d) (obj)).x * atrajseg[j1].pos0.x, ((com.maddox.JGP.Vector2d) (obj)).y * atrajseg[j1].pos0.x, atrajseg[j1].pos0.z);
            atrajseg[j1].pos0.add(endPos_rocket.x, endPos_rocket.y, 0.0D);
            atrajseg[j1].v0.set(((com.maddox.JGP.Vector2d) (obj)).x * atrajseg[j1].v0.x, ((com.maddox.JGP.Vector2d) (obj)).y * atrajseg[j1].v0.x, atrajseg[j1].v0.z);
            atrajseg[j1].a.set(((com.maddox.JGP.Vector2d) (obj)).x * atrajseg[j1].a.x, ((com.maddox.JGP.Vector2d) (obj)).y * atrajseg[j1].a.x, atrajseg[j1].a.z);
        }

        return atrajseg;
    }

    private com.maddox.il2.objects.vehicles.artillery.TrajSeg[] computeNormalTrajectory(int i)
    {
        com.maddox.il2.objects.vehicles.artillery.TrajSeg atrajseg[] = null;
        if(prop.air)
        {
            atrajseg = _computeAirTrajectory_(i);
        } else
        {
            atrajseg = _computeRampTrajectory_(i, false);
            if(atrajseg == null)
                atrajseg = _computeRampTrajectory_(i, true);
        }
        return atrajseg;
    }

    private RocketryGeneric(com.maddox.il2.objects.vehicles.artillery.RocketryProperties rocketryproperties, com.maddox.il2.objects.vehicles.artillery.MeshesNames meshesnames, java.lang.String s, int i, com.maddox.rts.NetChannel netchannel, int j, double d, double d1, float f, com.maddox.JGP.Point2d point2d, float f1, float f2, 
            int k)
    {
        super(meshesnames.ramp);
        prop = null;
        meshNames = null;
        targetPos = null;
        begPos_wagon = new Point3d();
        endPos_wagon = new Point3d();
        begPos_rocket = new Point3d();
        endPos_rocket = new Point3d();
        launchIntervalS = 0;
        rs = new ArrayList();
        damage = 0.0F;
        actionTimeMS = 0L;
        prop = rocketryproperties;
        meshNames = meshesnames;
        countRockets = k;
        if(countRockets == 0 || point2d == null)
        {
            countRockets = 0;
            point2d = null;
            targetPos = null;
        } else
        {
            targetPos = new Point3d();
            targetPos.set(point2d.x, point2d.y, com.maddox.il2.engine.Engine.land().HQ(point2d.x, point2d.y));
        }
        setName(s);
        setArmy(i);
        com.maddox.JGP.Point3d point3d = new Point3d();
        if(prop.air)
        {
            point3d.set(d, d1, prop.FLY_HEIGHT);
        } else
        {
            point3d.set(d, d1, com.maddox.il2.engine.Engine.land().HQ(d, d1));
            com.maddox.JGP.Point3d point3d1 = new Point3d();
            com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getHookOffset(mesh(), "Ground_Level", false, point3d1);
            point3d.z -= point3d1.z;
        }
        com.maddox.il2.engine.Orient orient = new Orient();
        if(targetPos == null)
        {
            orient.set(f, 0.0F, 0.0F);
        } else
        {
            com.maddox.JGP.Vector3d vector3d = new Vector3d();
            vector3d.sub(targetPos, point3d);
            vector3d.z = 0.0D;
            orient.setAT0(vector3d);
        }
        pos.setAbs(point3d, orient);
        pos.reset();
        if(prop.air)
        {
            collide(false);
            drawing(false);
        } else
        {
            collide(true);
            drawing(true);
        }
        if(prop.air)
        {
            begPos_wagon = null;
            endPos_wagon = null;
            begPos_rocket = null;
            endPos_rocket = null;
        } else
        {
            com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getHookOffset(mesh(), "_begWagon", true, begPos_wagon);
            pos.getAbs().transform(begPos_wagon);
            com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getHookOffset(mesh(), "_endWagon", true, endPos_wagon);
            pos.getAbs().transform(endPos_wagon);
            com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getHookOffset(mesh(), "_begRocket", true, begPos_rocket);
            pos.getAbs().transform(begPos_rocket);
            com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getHookOffset(mesh(), "_endRocket", true, endPos_rocket);
            pos.getAbs().transform(endPos_rocket);
        }
        int l = (int)(f1 * 60F + 0.5F);
        if(l < 0)
            l = 0;
        if(l > 14400)
            l = 14400;
        launchIntervalS = (int)(f2 * 60F + 0.5F);
        if(launchIntervalS < 180)
            launchIntervalS = 180;
        if(launchIntervalS > 14400)
            launchIntervalS = 14400;
        damage = 0.0F;
        actionTimeMS = 0x7fffffffffffffffL;
        createNetObject(netchannel, j);
        if(targetPos != null)
            if(isNetMaster())
            {
                long l1 = 1000L * (long)l - (1000L * (long)launchIntervalS) / 2L;
                if(l1 <= 0L)
                    prepareLaunch_Master(l);
                else
                    actionTimeMS = com.maddox.rts.Time.current() + l1;
            } else
            {
                actionTimeMS = 0x7fffffffffffffffL;
            }
        if(!interpEnd("move"))
            interpPut(new Move(), "move", com.maddox.rts.Time.current(), null);
    }

    public int HitbyMask()
    {
        return -2;
    }

    public int chooseBulletType(com.maddox.il2.engine.BulletProperties abulletproperties[])
    {
        if(Corpse())
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
        return abulletproperties[0].powerType != 0 ? 0 : 1;
    }

    public int chooseShotpoint(com.maddox.il2.engine.BulletProperties bulletproperties)
    {
        return !Corpse() ? 0 : -1;
    }

    public boolean getShotpointOffset(int i, com.maddox.JGP.Point3d point3d)
    {
        if(Corpse())
            return false;
        if(i != 0)
            return false;
        if(point3d != null)
            point3d.set(0.0D, 0.0D, 0.0D);
        return true;
    }

    private com.maddox.il2.objects.vehicles.artillery.RocketryRocket findMyRocket(int i)
    {
        for(int j = 0; j < rs.size(); j++)
            if(((com.maddox.il2.objects.vehicles.artillery.RocketryRocket)rs.get(j)).idR == i)
                return (com.maddox.il2.objects.vehicles.artillery.RocketryRocket)rs.get(j);

        return null;
    }

    void forgetRocket(com.maddox.il2.objects.vehicles.artillery.RocketryRocket rocketryrocket)
    {
        for(int i = 0; i < rs.size(); i++)
            if((com.maddox.il2.objects.vehicles.artillery.RocketryRocket)rs.get(i) == rocketryrocket)
            {
                rs.remove(i);
                return;
            }

    }

    private void killWrongRockets(int i)
    {
        for(int j = 0; j < rs.size(); j++)
        {
            int k = ((com.maddox.il2.objects.vehicles.artillery.RocketryRocket)rs.get(j)).idR;
            byte byte0 = (byte)(k - i);
            if(byte0 >= 0 && byte0 <= 20)
            {
                ((com.maddox.il2.objects.vehicles.artillery.RocketryRocket)rs.get(j)).silentDeath();
                rs.remove(j);
                j--;
            }
        }

    }

    private void sendRampDamage_Mirror(float f, com.maddox.il2.engine.Actor actor)
    {
        if(!isNetMirror())
            return;
        if(net.masterChannel() instanceof com.maddox.rts.NetChannelInStream)
            return;
        int i = (int)(f * 65000F);
        if(i <= 0)
            return;
        if(i > 65000)
            i = 65000;
        try
        {
            com.maddox.rts.NetMsgFiltered netmsgfiltered = new NetMsgFiltered();
            netmsgfiltered.writeByte(45);
            netmsgfiltered.writeShort(i);
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

    private void sendRespawn_Master()
    {
        if(!net.isMirrored())
            return;
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        try
        {
            netmsgguaranted.writeByte(83);
            netmsgguaranted.writeByte(nextFreeIdR);
            net.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void prepareLaunch_Master(int i)
    {
        if(countRockets <= 0)
        {
            countRockets = 0;
            actionTimeMS = 0x7fffffffffffffffL;
            return;
        }
        long l = com.maddox.rts.Time.current();
        int j = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.RndI(0, 65535);
        int k = nextFreeIdR;
        nextFreeIdR = nextFreeIdR + 1 & 0xff;
        long l1 = l + 1000L * (long)i;
        actionTimeMS = l1 + (1000L * (long)launchIntervalS) / 2L;
        killWrongRockets(k);
        com.maddox.il2.objects.vehicles.artillery.TrajSeg atrajseg[] = computeNormalTrajectory(j);
        if(atrajseg == null)
            return;
        com.maddox.il2.objects.vehicles.artillery.RocketryRocket rocketryrocket = new RocketryRocket(this, meshNames.rocket, k, j, l1, l, atrajseg);
        if(rocketryrocket.isDamaged())
        {
            rocketryrocket.silentDeath();
            return;
        }
        com.maddox.il2.game.Main.cur().mission.getClass();
        java.lang.Object obj = new ZutiPadObject(rocketryrocket, com.maddox.il2.game.Main.cur().mission.zutiRadar_RefreshInterval > 0);
        obj.type = 3;
        com.maddox.il2.gui.GUI.pad.zutiPadObjects.put(new Integer(((com.maddox.il2.game.ZutiPadObject) (obj)).hashCode()), obj);
        rs.add(rocketryrocket);
        if(countRockets < 1000)
            countRockets--;
        if(!net.isMirrored())
            return;
        obj = new NetMsgGuaranted();
        try
        {
            ((com.maddox.rts.NetMsgGuaranted) (obj)).writeByte(80);
            ((com.maddox.rts.NetMsgGuaranted) (obj)).writeShort(i);
            ((com.maddox.rts.NetMsgGuaranted) (obj)).writeByte(k);
            ((com.maddox.rts.NetMsgGuaranted) (obj)).writeShort(j);
            net.post(((com.maddox.rts.NetMsgGuaranted) (obj)));
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void handleInitCommand_Mirror(boolean flag, int i, com.maddox.il2.objects.vehicles.artillery.RocketInGame arocketingame[])
    {
        for(int j = 0; j < rs.size(); j++)
            ((com.maddox.il2.objects.vehicles.artillery.RocketryRocket)rs.get(j)).silentDeath();

        rs.clear();
        damage = flag ? 0.0F : 1.0F;
        setMesh(flag ? meshNames.ramp : meshNames.ramp_d);
        if(targetPos == null)
            return;
        long l = com.maddox.rts.Time.current();
        for(int k = 0; k < arocketingame.length; k++)
        {
            com.maddox.il2.objects.vehicles.artillery.TrajSeg atrajseg[] = computeNormalTrajectory(arocketingame[k].randseed);
            if(atrajseg == null)
                continue;
            com.maddox.il2.objects.vehicles.artillery.RocketryRocket rocketryrocket = new RocketryRocket(this, meshNames.rocket, arocketingame[k].idR, arocketingame[k].randseed, l - (long)(1000D * (double)arocketingame[k].timeAfterStartS), l, atrajseg);
            if(rocketryrocket.isDamaged())
            {
                rocketryrocket.silentDeath();
            } else
            {
                com.maddox.il2.game.Main.cur().mission.getClass();
                com.maddox.il2.game.ZutiPadObject zutipadobject = new ZutiPadObject(rocketryrocket, com.maddox.il2.game.Main.cur().mission.zutiRadar_RefreshInterval > 0);
                zutipadobject.type = 3;
                com.maddox.il2.gui.GUI.pad.zutiPadObjects.put(new Integer(zutipadobject.hashCode()), zutipadobject);
                rs.add(rocketryrocket);
            }
        }

    }

    private void handleRespawnCommand_Mirror(int i)
    {
        killWrongRockets(i);
        actionTimeMS = com.maddox.rts.Time.current() + 0x1869fL;
        if(damage >= 1.0F)
        {
            damage = 0.0F;
            setMesh(meshNames.ramp);
            setDiedFlag(false);
        } else
        {
            damage = 0.0F;
        }
    }

    private void handlePrepareLaunchCommand_Mirror(int i, int j, int k)
    {
        killWrongRockets(j);
        if(targetPos == null)
            return;
        long l = com.maddox.rts.Time.current();
        com.maddox.il2.objects.vehicles.artillery.TrajSeg atrajseg[] = computeNormalTrajectory(k);
        if(atrajseg == null)
            return;
        com.maddox.il2.objects.vehicles.artillery.RocketryRocket rocketryrocket = new RocketryRocket(this, meshNames.rocket, j, k, l + (long)(i * 1000), l, atrajseg);
        if(rocketryrocket.isDamaged())
            rocketryrocket.silentDeath();
        else
            rs.add(rocketryrocket);
    }

    private void handleSyncLaunchCommand_Mirror(long l, int i)
    {
        killWrongRockets(i + 1 & 0xff);
        if(targetPos == null)
            return;
        com.maddox.il2.objects.vehicles.artillery.RocketryRocket rocketryrocket = findMyRocket(i);
        if(rocketryrocket != null)
            rocketryrocket.changeLaunchTimeIfCan(l);
    }

    public void sendRocketStateChange(com.maddox.il2.objects.vehicles.artillery.RocketryRocket rocketryrocket, char c, com.maddox.il2.engine.Actor actor)
    {
        boolean flag = isNetMaster();
        int i = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.RndI(0, 65535);
        if(flag)
        {
            handleRocketCommand_Both(c, rocketryrocket.idR, i, actor, true);
            return;
        }
        if(!isNetMirror())
            return;
        if(net.masterChannel() instanceof com.maddox.rts.NetChannelInStream)
            return;
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        try
        {
            netmsgguaranted.writeByte(java.lang.Character.toLowerCase(c));
            netmsgguaranted.writeByte(rocketryrocket.idR);
            netmsgguaranted.writeShort(i);
            netmsgguaranted.writeNetObj(actor != null ? ((com.maddox.rts.NetObj) (actor.net)) : null);
            net.postTo(net.masterChannel(), netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void handleRocketCommand_Both(char c, int i, int j, com.maddox.il2.engine.Actor actor, boolean flag)
    {
        if(targetPos == null)
            return;
        com.maddox.il2.objects.vehicles.artillery.RocketryRocket rocketryrocket = findMyRocket(i);
        if(rocketryrocket == null)
            return;
        c = rocketryrocket.handleCommand(c, j, actor);
        if(c == 0 || !flag || !net.isMirrored())
            return;
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        try
        {
            netmsgguaranted.writeByte(java.lang.Character.toUpperCase(c));
            netmsgguaranted.writeByte(i);
            netmsgguaranted.writeShort(j);
            netmsgguaranted.writeNetObj(actor != null ? ((com.maddox.rts.NetObj) (actor.net)) : null);
            net.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void handleDamageRamp_Both(float f, com.maddox.il2.engine.Actor actor, boolean flag)
    {
        if(prop.air)
            return;
        if(f <= 0.0F)
            return;
        if(damage >= 1.0F)
            return;
        damage += f;
        if(damage >= 1.0F)
            damage = 1.0F;
        else
            return;
        com.maddox.il2.ai.World.onActorDied(this, actor);
        setMesh(meshNames.ramp_d);
        actionTimeMS = com.maddox.rts.Time.current();
        if(com.maddox.il2.game.Mission.isDeathmatch())
            actionTimeMS += com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.SecsToTicks(com.maddox.il2.game.Mission.respawnTime("Artillery") * com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.RndF(1.0F, 1.2F));
        for(int i = 0; i < rs.size(); i++)
        {
            com.maddox.il2.objects.vehicles.artillery.RocketryRocket rocketryrocket = (com.maddox.il2.objects.vehicles.artillery.RocketryRocket)rs.get(i);
            int j = rocketryrocket.idR;
            if(rocketryrocket.isOnRamp())
            {
                handleRocketCommand_Both('X', j, com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.RndI(0, 65535), actor, flag);
                i = 0;
            }
        }

        com.maddox.il2.objects.effects.Explosions.ExplodeBridge(begPos_wagon, endPos_wagon, 1.2F);
        if(!flag || !net.isMirrored())
            return;
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        try
        {
            netmsgguaranted.writeByte(68);
            netmsgguaranted.writeByte(nextFreeIdR);
            netmsgguaranted.writeShort(com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.RndI(0, 65535));
            netmsgguaranted.writeNetObj(actor != null ? ((com.maddox.rts.NetObj) (actor.net)) : null);
            net.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void createNetObject(com.maddox.rts.NetChannel netchannel, int i)
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
        netmsgguaranted.writeByte(damage < 1.0F ? 1 : 0);
        netmsgguaranted.writeByte(nextFreeIdR);
        int i = 0;
        for(int j = 0; j < rs.size(); j++)
        {
            com.maddox.il2.objects.vehicles.artillery.RocketryRocket rocketryrocket = (com.maddox.il2.objects.vehicles.artillery.RocketryRocket)rs.get(j);
            if(!rocketryrocket.isDamaged())
                i++;
        }

        if(i > 10)
            i = 10;
        netmsgguaranted.writeByte(i);
        for(int k = rs.size() - 1; k >= 0; k--)
        {
            com.maddox.il2.objects.vehicles.artillery.RocketryRocket rocketryrocket1 = (com.maddox.il2.objects.vehicles.artillery.RocketryRocket)rs.get(k);
            if(rocketryrocket1.isDamaged())
                continue;
            netmsgguaranted.writeByte(rocketryrocket1.idR);
            float f = (float)((double)(com.maddox.rts.Time.current() - rocketryrocket1.timeOfStartMS) * 0.001D);
            netmsgguaranted.writeFloat(f);
            netmsgguaranted.writeShort(rocketryrocket1.randseed);
            if(--i <= 0)
                break;
        }

        net.postTo(netchannel, netmsgguaranted);
    }

    public static com.maddox.il2.objects.vehicles.artillery.RocketryGeneric New(java.lang.String s, java.lang.String s1, com.maddox.rts.NetChannel netchannel, int i, int j, double d, double d1, float f, float f1, int k, float f2, com.maddox.JGP.Point2d point2d)
    {
        com.maddox.il2.objects.vehicles.artillery.RocketryProperties rocketryproperties = (com.maddox.il2.objects.vehicles.artillery.RocketryProperties)rocketryMap.get(s1);
        if(rocketryproperties == null)
        {
            java.lang.System.out.println("***** Rocketry: Unknown type [" + s1 + "]");
            return null;
        }
        com.maddox.il2.objects.vehicles.artillery.MeshesNames meshesnames = null;
        switch(com.maddox.il2.ai.World.cur().camouflage)
        {
        case 1: // '\001'
            meshesnames = rocketryproperties.winterNames;
            break;

        case 2: // '\002'
            meshesnames = rocketryproperties.desertNames;
            break;

        default:
            meshesnames = rocketryproperties.summerNames;
            break;
        }
        return new RocketryGeneric(rocketryproperties, meshesnames, s, j, netchannel, i, d, d1, f, point2d, f1, f2, k);
    }

    public static final float KmHourToMSec(float f)
    {
        return f / 3.6F;
    }

    private static float getF(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.String s1, float f, float f1)
    {
        float f2 = sectfile.get(s, s1, -9865.345F);
        if(f2 == -9865.345F || f2 < f || f2 > f1)
        {
            if(f2 == -9865.345F)
                java.lang.System.out.println("Rocketry: Parameter [" + s + "]:<" + s1 + "> " + "not found");
            else
                java.lang.System.out.println("Rocketry: Value of [" + s + "]:<" + s1 + "> (" + f2 + ")" + " is out of range (" + f + ";" + f1 + ")");
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
            java.lang.System.out.print("Rocketry: Parameter [" + s + "]:<" + s1 + "> ");
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

    public static void PreLoad(java.lang.String s)
    {
        com.maddox.rts.Property.set(com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.class, "iconName", "icons/objV1.mat");
        rocketryMap = new HashMap();
        com.maddox.rts.SectFile sectfile = new SectFile(s, 0);
        int i = sectfile.sections();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.objects.vehicles.artillery.RocketryProperties rocketryproperties = new RocketryProperties();
            rocketryproperties.name = sectfile.sectionName(j);
            rocketryproperties.summerNames.setNull();
            rocketryproperties.desertNames.setNull();
            rocketryproperties.winterNames.setNull();
            rocketryproperties.summerNames.ramp = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getS(sectfile, rocketryproperties.name, "MeshSummer_ramp", "");
            if(rocketryproperties.summerNames.ramp == "")
            {
                rocketryproperties.air = true;
                rocketryproperties.summerNames.ramp = "3do/primitive/coord/mono.sim";
                rocketryproperties.summerNames.ramp_d = rocketryproperties.summerNames.ramp;
                rocketryproperties.desertNames = rocketryproperties.summerNames;
                rocketryproperties.winterNames = rocketryproperties.summerNames;
            } else
            {
                rocketryproperties.air = false;
                rocketryproperties.desertNames.ramp = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getS(sectfile, rocketryproperties.name, "MeshDesert_ramp", rocketryproperties.summerNames.ramp);
                rocketryproperties.winterNames.ramp = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getS(sectfile, rocketryproperties.name, "MeshWinter_ramp", rocketryproperties.summerNames.ramp);
                rocketryproperties.summerNames.ramp_d = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getS(sectfile, rocketryproperties.name, "MeshSummerDamage_ramp");
                rocketryproperties.desertNames.ramp_d = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getS(sectfile, rocketryproperties.name, "MeshDesertDamage_ramp", rocketryproperties.summerNames.ramp_d);
                rocketryproperties.winterNames.ramp_d = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getS(sectfile, rocketryproperties.name, "MeshWinterDamage_ramp", rocketryproperties.summerNames.ramp_d);
                rocketryproperties.summerNames.wagon = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getS(sectfile, rocketryproperties.name, "MeshSummer_wagon");
                rocketryproperties.desertNames.wagon = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getS(sectfile, rocketryproperties.name, "MeshDesert_wagon", rocketryproperties.summerNames.wagon);
                rocketryproperties.winterNames.wagon = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getS(sectfile, rocketryproperties.name, "MeshWinter_wagon", rocketryproperties.summerNames.wagon);
            }
            rocketryproperties.summerNames.rocket = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getS(sectfile, rocketryproperties.name, "MeshSummer_rocket");
            rocketryproperties.desertNames.rocket = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getS(sectfile, rocketryproperties.name, "MeshDesert_rocket", rocketryproperties.summerNames.rocket);
            rocketryproperties.winterNames.rocket = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getS(sectfile, rocketryproperties.name, "MeshWinter_rocket", rocketryproperties.summerNames.rocket);
            rocketryproperties.soundName = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getS(sectfile, rocketryproperties.name, "SoundMove");
            rocketryproperties.MASS_FULL = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getF(sectfile, rocketryproperties.name, "MassFull", 0.5F, 100000F);
            rocketryproperties.MASS_TNT = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getF(sectfile, rocketryproperties.name, "MassTNT", 0.0F, 1E+007F);
            rocketryproperties.EXPLOSION_RADIUS = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getF(sectfile, rocketryproperties.name, "ExplosionRadius", 0.01F, 100000F);
            if(!rocketryproperties.air)
            {
                rocketryproperties.TAKEOFF_SPEED = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getF(sectfile, rocketryproperties.name, "TakeoffSpeed", 1.0F, 3000F);
                rocketryproperties.TAKEOFF_SPEED = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.KmHourToMSec(rocketryproperties.TAKEOFF_SPEED);
            }
            rocketryproperties.MAX_SPEED = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getF(sectfile, rocketryproperties.name, "MaxSpeed", rocketryproperties.TAKEOFF_SPEED, 3000F);
            rocketryproperties.MAX_SPEED = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.KmHourToMSec(rocketryproperties.MAX_SPEED);
            if(!rocketryproperties.air)
                rocketryproperties.SPEEDUP_TIME = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getF(sectfile, rocketryproperties.name, "SpeedupTime", 1.0F, 10000F);
            rocketryproperties.FLY_HEIGHT = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getF(sectfile, rocketryproperties.name, "FlyHeight", 100F, 15000F);
            rocketryproperties.HIT_ANGLE = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getF(sectfile, rocketryproperties.name, "HitAngle", 5F, 89F);
            rocketryproperties.MAX_ERR_HEIGHT = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getF(sectfile, rocketryproperties.name, "MaxErrHeight", 0.0F, 2000F);
            rocketryproperties.MAX_ERR_HIT_DISTANCE = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getF(sectfile, rocketryproperties.name, "MaxErrHitDistance", 0.0F, 10000F);
            if(!rocketryproperties.air && !rocketryproperties.stre.read("Rocketry", sectfile, null, rocketryproperties.name))
                throw new RuntimeException("Can't register Rocketry data");
            rocketryproperties.DMG_WARHEAD_MM = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getF(sectfile, rocketryproperties.name, "DmgWarhead_mm", 0.0F, 2000F);
            rocketryproperties.DMG_WARHEAD_PROB = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getF(sectfile, rocketryproperties.name, "DmgWarhead_prob", 0.0F, 1.0F);
            rocketryproperties.DMG_FUEL_MM = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getF(sectfile, rocketryproperties.name, "DmgFuel_mm", 0.0F, 2000F);
            rocketryproperties.DMG_FUEL_PROB = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getF(sectfile, rocketryproperties.name, "DmgFuel_prob", 0.0F, 1.0F);
            rocketryproperties.DMG_ENGINE_MM = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getF(sectfile, rocketryproperties.name, "DmgEngine_mm", 0.0F, 2000F);
            rocketryproperties.DMG_ENGINE_PROB = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getF(sectfile, rocketryproperties.name, "DmgEngine_prob", 0.0F, 1.0F);
            rocketryproperties.DMG_WING_MM = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getF(sectfile, rocketryproperties.name, "DmgWing_mm", 0.0F, 2000F);
            rocketryproperties.DMG_WING_PROB = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getF(sectfile, rocketryproperties.name, "DmgWing_prob", 0.0F, 1.0F);
            rocketryproperties.DMG_WARHEAD_TNT = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getF(sectfile, rocketryproperties.name, "DmgWarhead_tnt", 0.0F, 1000000F);
            rocketryproperties.DMG_WING_TNT = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.getF(sectfile, rocketryproperties.name, "DmgWing_tnt", 0.0F, 1000000F);
            rocketryMap.put(rocketryproperties.name, rocketryproperties);
        }

    }

    private static java.util.HashMap rocketryMap = new HashMap();
    com.maddox.il2.objects.vehicles.artillery.RocketryProperties prop;
    com.maddox.il2.objects.vehicles.artillery.MeshesNames meshNames;
    private com.maddox.JGP.Point3d targetPos;
    private com.maddox.JGP.Point3d begPos_wagon;
    private com.maddox.JGP.Point3d endPos_wagon;
    private com.maddox.JGP.Point3d begPos_rocket;
    private com.maddox.JGP.Point3d endPos_rocket;
    private int launchIntervalS;
    private java.util.ArrayList rs;
    private int nextFreeIdR;
    private float damage;
    private long actionTimeMS;
    private int countRockets;
    private static com.maddox.il2.ai.RangeRandom rndSeed = new RangeRandom();














}
