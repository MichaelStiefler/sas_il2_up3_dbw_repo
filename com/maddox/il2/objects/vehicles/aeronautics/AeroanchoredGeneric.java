// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AeroanchoredGeneric.java

package com.maddox.il2.objects.vehicles.aeronautics;

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
import com.maddox.il2.ai.ground.Obstacle;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.Statics;
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

// Referenced classes of package com.maddox.il2.objects.vehicles.aeronautics:
//            Balloon, Rope

public abstract class AeroanchoredGeneric extends com.maddox.il2.engine.ActorMesh
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener, com.maddox.il2.ai.ground.Prey, com.maddox.il2.ai.ground.Obstacle, com.maddox.il2.objects.ActorAlign
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
                    java.lang.System.out.println("Aeroanchored: Parameter [" + s + "]:<" + s1 + "> " + "not found");
                else
                    java.lang.System.out.println("Aeroanchored: Value of [" + s + "]:<" + s1 + "> (" + f2 + ")" + " is out of range (" + f + ";" + f1 + ")");
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
                java.lang.System.out.print("Aeroanchored: Parameter [" + s + "]:<" + s1 + "> ");
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

        private static com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredProperties LoadAeroanchoredProperties(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.Class class1)
        {
            com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredProperties aeroanchoredproperties = new AeroanchoredProperties();
            aeroanchoredproperties.meshASummer = com.maddox.il2.objects.vehicles.aeronautics.SPAWN.getS(sectfile, s, "MeshAnchorSummer");
            aeroanchoredproperties.meshADesert = com.maddox.il2.objects.vehicles.aeronautics.SPAWN.getS(sectfile, s, "MeshAnchorDesert", aeroanchoredproperties.meshASummer);
            aeroanchoredproperties.meshAWinter = com.maddox.il2.objects.vehicles.aeronautics.SPAWN.getS(sectfile, s, "MeshAnchorWinter", aeroanchoredproperties.meshASummer);
            aeroanchoredproperties.meshASummerDead = com.maddox.il2.objects.vehicles.aeronautics.SPAWN.getS(sectfile, s, "MeshAnchorSummerDamage", aeroanchoredproperties.meshASummer);
            aeroanchoredproperties.meshADesertDead = com.maddox.il2.objects.vehicles.aeronautics.SPAWN.getS(sectfile, s, "MeshAnchorDesertDamage", aeroanchoredproperties.meshASummerDead);
            aeroanchoredproperties.meshAWinterDead = com.maddox.il2.objects.vehicles.aeronautics.SPAWN.getS(sectfile, s, "MeshAnchorWinterDamage", aeroanchoredproperties.meshASummerDead);
            aeroanchoredproperties.meshBSummer = com.maddox.il2.objects.vehicles.aeronautics.SPAWN.getS(sectfile, s, "MeshBalloonSummer");
            aeroanchoredproperties.meshBDesert = com.maddox.il2.objects.vehicles.aeronautics.SPAWN.getS(sectfile, s, "MeshBalloonDesert", aeroanchoredproperties.meshBSummer);
            aeroanchoredproperties.meshBWinter = com.maddox.il2.objects.vehicles.aeronautics.SPAWN.getS(sectfile, s, "MeshBalloonWinter", aeroanchoredproperties.meshBSummer);
            aeroanchoredproperties.meshRSummer = com.maddox.il2.objects.vehicles.aeronautics.SPAWN.getS(sectfile, s, "MeshRopeSummer");
            aeroanchoredproperties.meshRDesert = com.maddox.il2.objects.vehicles.aeronautics.SPAWN.getS(sectfile, s, "MeshRopeDesert", aeroanchoredproperties.meshRSummer);
            aeroanchoredproperties.meshRWinter = com.maddox.il2.objects.vehicles.aeronautics.SPAWN.getS(sectfile, s, "MeshRopeWinter", aeroanchoredproperties.meshRSummer);
            java.lang.String s1 = com.maddox.il2.objects.vehicles.aeronautics.SPAWN.getS(sectfile, s, "PanzerType", null);
            if(s1 == null)
                s1 = "Car";
            aeroanchoredproperties.fnShotPanzer = com.maddox.il2.ai.TableFunctions.GetFunc2(s1 + "ShotPanzer");
            aeroanchoredproperties.fnExplodePanzer = com.maddox.il2.ai.TableFunctions.GetFunc2(s1 + "ExplodePanzer");
            aeroanchoredproperties.HEIGHT = com.maddox.il2.objects.vehicles.aeronautics.SPAWN.getF(sectfile, s, "Height", 25F, 4001F);
            aeroanchoredproperties.ROPE_SEG_LENGTH = com.maddox.il2.objects.vehicles.aeronautics.SPAWN.getF(sectfile, s, "RopeSegLength", 10F, 1000F);
            aeroanchoredproperties.PANZER_TNT_TYPE = com.maddox.il2.objects.vehicles.aeronautics.SPAWN.getF(sectfile, s, "PanzerSubtype", 0.0F, 100F);
            aeroanchoredproperties.PANZER_BODY = com.maddox.il2.objects.vehicles.aeronautics.SPAWN.getF(sectfile, s, "PanzerBody", 0.001F, 9.999F);
            aeroanchoredproperties.HITBY_MASK = aeroanchoredproperties.PANZER_BODY <= 0.015F ? -1 : -2;
            aeroanchoredproperties.explodeName = com.maddox.il2.objects.vehicles.aeronautics.SPAWN.getS(sectfile, s, "Explode", "Aeroanchor");
            com.maddox.rts.Property.set(class1, "iconName", "icons/" + com.maddox.il2.objects.vehicles.aeronautics.SPAWN.getS(sectfile, s, "Icon") + ".mat");
            com.maddox.rts.Property.set(class1, "meshName", aeroanchoredproperties.meshASummer);
            return aeroanchoredproperties;
        }

        public com.maddox.il2.engine.Actor actorSpawn(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            switch(com.maddox.il2.ai.World.cur().camouflage)
            {
            case 1: // '\001'
                proper.meshAName = proper.meshAWinter;
                proper.meshANameDead = proper.meshAWinterDead;
                proper.meshRName = proper.meshRWinter;
                proper.meshBName = proper.meshBWinter;
                break;

            case 2: // '\002'
                proper.meshAName = proper.meshADesert;
                proper.meshANameDead = proper.meshADesertDead;
                proper.meshRName = proper.meshRDesert;
                proper.meshBName = proper.meshBDesert;
                break;

            default:
                proper.meshAName = proper.meshASummer;
                proper.meshANameDead = proper.meshASummerDead;
                proper.meshRName = proper.meshRSummer;
                proper.meshBName = proper.meshBSummer;
                break;
            }
            com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric aeroanchoredgeneric = null;
            try
            {
                com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric.constr_arg1 = proper;
                com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric.constr_arg2 = actorspawnarg;
                aeroanchoredgeneric = (com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric)cls.newInstance();
                com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric.constr_arg1 = null;
                com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric.constr_arg2 = null;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric.constr_arg1 = null;
                com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric.constr_arg2 = null;
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                java.lang.System.out.println("SPAWN: Can't create Aeroanchored object [class:" + cls.getName() + "]");
                return null;
            }
            return aeroanchoredgeneric;
        }

        public java.lang.Class cls;
        public com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredProperties proper;

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
                proper = com.maddox.il2.objects.vehicles.aeronautics.SPAWN.LoadAeroanchoredProperties(com.maddox.il2.objects.Statics.getTechnicsFile(), s1, class1);
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
                byte byte0 = netmsginput.readByte();
                switch(byte0)
                {
                case 73: // 'I'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(netmsginput, 0);
                        post(netmsgguaranted);
                    }
                    int i = netmsginput.readUnsignedByte();
                    handleStartCommandFromMaster((i & 1) == 0, (i & 2) == 0, (i & 4) == 0);
                    return true;

                case 82: // 'R'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted(netmsginput, 0);
                        post(netmsgguaranted1);
                    }
                    handleStartCommandFromMaster(false, false, false);
                    return true;

                case 97: // 'a'
                case 98: // 'b'
                case 114: // 'r'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted2 = new NetMsgGuaranted(netmsginput, 1);
                        post(netmsgguaranted2);
                    }
                    com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                    com.maddox.il2.engine.Actor actor = netobj != null ? ((com.maddox.il2.engine.ActorNet)netobj).actor() : null;
                    applyDamage(byte0, actor, 1.0F, false);
                    return true;
                }
                return true;
            }
            switch(netmsginput.readByte())
            {
            case 97: // 'a'
            case 98: // 'b'
            case 114: // 'r'
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
            byte byte0 = netmsginput.readByte();
            switch(byte0)
            {
            case 97: // 'a'
            case 98: // 'b'
            case 114: // 'r'
                int i = netmsginput.readUnsignedByte();
                com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                com.maddox.il2.engine.Actor actor = netobj != null ? ((com.maddox.il2.engine.ActorNet)netobj).actor() : null;
                float f = (float)(i + 1) / 256F;
                applyDamage(byte0, actor, f, true);
                break;
            }
            return true;
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
            if(isNetMirror() && mirrorSendDmgDelay-- <= 0)
            {
                mirrorSendDmgDelay = 1;
                mirrorsend_DamageIfNeed();
            }
            if(!rope.dead)
                return true;
            if(respawnDelay-- > 0L)
                return true;
            if(!com.maddox.il2.game.Mission.isDeathmatch())
                return false;
            if(!isNetMaster())
            {
                respawnDelay = 10000L;
                return true;
            }
            anchor.resetToLive();
            rope.resetToLive();
            balloon.resetToLive();
            balloon.resetToLive();
            setMesh(prop.meshAName);
            setDefaultPose();
            recreateBalloon();
            recreateRope();
            setDiedFlag(false);
            if(balloonActor != null)
                balloonActor.setDiedFlag(false);
            if(ropeActor != null)
                ropeActor.setDiedFlag(false);
            mastersend_RespawnCommand();
            return true;
        }

        Move()
        {
        }
    }

    public static class PartState
    {

        void resetToLive()
        {
            damage = 0.0F;
            mirror_initiator = null;
            dead = false;
        }

        void resetToDead()
        {
            damage = 0.0F;
            mirror_initiator = null;
            dead = true;
        }

        private float damage;
        private com.maddox.il2.engine.Actor mirror_initiator;
        private boolean dead;







        public PartState()
        {
        }
    }

    public static class AeroanchoredProperties
    {

        public java.lang.String meshAName;
        public java.lang.String meshANameDead;
        public java.lang.String meshRName;
        public java.lang.String meshBName;
        public java.lang.String meshASummer;
        public java.lang.String meshADesert;
        public java.lang.String meshAWinter;
        public java.lang.String meshASummerDead;
        public java.lang.String meshADesertDead;
        public java.lang.String meshAWinterDead;
        public java.lang.String meshRSummer;
        public java.lang.String meshRDesert;
        public java.lang.String meshRWinter;
        public java.lang.String meshBSummer;
        public java.lang.String meshBDesert;
        public java.lang.String meshBWinter;
        public float HEIGHT;
        public float ROPE_SEG_LENGTH;
        public com.maddox.util.TableFunction2 fnShotPanzer;
        public com.maddox.util.TableFunction2 fnExplodePanzer;
        public float PANZER_BODY;
        public float PANZER_TNT_TYPE;
        public int HITBY_MASK;
        public java.lang.String explodeName;

        public AeroanchoredProperties()
        {
            meshAName = null;
            meshANameDead = null;
            meshRName = null;
            meshBName = null;
            meshASummer = null;
            meshADesert = null;
            meshAWinter = null;
            meshASummerDead = null;
            meshADesertDead = null;
            meshAWinterDead = null;
            meshRSummer = null;
            meshRDesert = null;
            meshRWinter = null;
            meshBSummer = null;
            meshBDesert = null;
            meshBWinter = null;
            HEIGHT = 0.001F;
            ROPE_SEG_LENGTH = 0.001F;
            fnShotPanzer = null;
            fnExplodePanzer = null;
            PANZER_BODY = 0.001F;
            PANZER_TNT_TYPE = 1.0F;
            HITBY_MASK = -1;
            explodeName = null;
        }
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

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        anchorDamaged(actor, 1.0F);
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        shot.bodyMaterial = 2;
        if(anchor.dead)
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
                anchorDamaged(shot.initiator, 1.0F);
                return;
            }
        float f = com.maddox.il2.ai.Shot.panzerThickness(pos.getAbsOrient(), shot.v, false, prop.PANZER_BODY, prop.PANZER_BODY, prop.PANZER_BODY, prop.PANZER_BODY, prop.PANZER_BODY, prop.PANZER_BODY);
        f *= com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric.Rnd(0.93F, 1.07F);
        float f1 = prop.fnShotPanzer.Value(shot.power, f);
        if(f1 < 1000F && (f1 <= 1.0F || RndB(1.0F / f1)))
            anchorDamaged(shot.initiator, 1.0F);
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        if(anchor.dead)
            return;
        if(isNetMirror() && explosion.isMirage())
            return;
        if(explosion.power <= 0.0F)
            return;
        com.maddox.il2.ai.Explosion _tmp = explosion;
        if(explosion.powerType == 1)
        {
            if(com.maddox.il2.objects.vehicles.tanks.TankGeneric.splintersKill(explosion, prop.fnShotPanzer, com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric.Rnd(0.0F, 1.0F), com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric.Rnd(0.0F, 1.0F), this, 0.7F, 0.0F, prop.PANZER_BODY, prop.PANZER_BODY, prop.PANZER_BODY, prop.PANZER_BODY, prop.PANZER_BODY, prop.PANZER_BODY))
                anchorDamaged(explosion.initiator, 1.0F);
            return;
        }
        com.maddox.il2.ai.Explosion _tmp1 = explosion;
        if(explosion.powerType == 2 && explosion.chunkName != null)
        {
            anchorDamaged(explosion.initiator, 1.0F);
            return;
        }
        float f;
        if(explosion.chunkName != null)
            f = 0.5F * explosion.power;
        else
            f = explosion.receivedTNTpower(this);
        f *= com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric.Rnd(0.95F, 1.05F);
        float f1 = prop.fnExplodePanzer.Value(f, prop.PANZER_TNT_TYPE);
        if(f1 < 1000F && (f1 <= 1.0F || RndB(1.0F / f1)))
            anchorDamaged(explosion.initiator, 1.0F);
    }

    private void ShowExplode(float f)
    {
        if(f > 0.0F)
            f = com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric.Rnd(f, f * 1.6F);
    }

    private void anchorDamaged(com.maddox.il2.engine.Actor actor, float f)
    {
        if(anchor.dead)
            return;
        if(isNetMirror())
        {
            anchor.damage+= = f;
            anchor.mirror_initiator = actor;
        } else
        {
            applyDamage(97, actor, f, true);
        }
    }

    public void destroy()
    {
        if(isDestroyed())
            return;
        if(balloonActor != null)
        {
            balloonActor.destroy();
            balloonActor = null;
        }
        if(ropeActor != null)
        {
            ropeActor.destroy();
            ropeActor = null;
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

    private void setDefaultPose()
    {
        heightAboveLandSurface = 0.0F;
        int i = mesh().hookFind("Ground_Level");
        if(i != -1)
        {
            com.maddox.JGP.Matrix4d matrix4d = new Matrix4d();
            mesh().hookMatrix(i, matrix4d);
            heightAboveLandSurface = (float)(-matrix4d.m23);
        }
        Align();
    }

    protected AeroanchoredGeneric()
    {
        this(constr_arg1, constr_arg2);
    }

    private AeroanchoredGeneric(com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredProperties aeroanchoredproperties, com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
    {
        super(aeroanchoredproperties.meshAName);
        prop = null;
        anchor = new PartState();
        rope = new PartState();
        balloon = new PartState();
        balloonActor = null;
        ropeActor = null;
        respawnDelay = 0L;
        mirrorSendDmgDelay = 0;
        prop = aeroanchoredproperties;
        actorspawnarg.setStationary(this);
        collide(true);
        drawing(true);
        anchor.resetToLive();
        balloon.resetToLive();
        rope.resetToLive();
        createNetObject(actorspawnarg.netChannel, actorspawnarg.netIdRemote);
        setDefaultPose();
        recreateBalloon();
        recreateRope();
        setDiedFlag(false);
        if(balloonActor != null)
            balloonActor.setDiedFlag(false);
        if(ropeActor != null)
            ropeActor.setDiedFlag(false);
        startMove();
    }

    private void Align()
    {
        pos.getAbs(p);
        p.z = com.maddox.il2.engine.Engine.land().HQ(p.x, p.y) + (double)heightAboveLandSurface;
        o.setYPR(pos.getAbsOrient().getYaw(), 0.0F, 0.0F);
        com.maddox.il2.engine.Engine.land().N(p.x, p.y, n);
        o.orient(n);
        pos.setAbs(p, o);
        pos.reset();
    }

    public void align()
    {
        Align();
        recreateBalloon();
        recreateRope();
    }

    private float computeTopPossibleHeight(double d, double d1)
    {
        d = d * 1.1699999999999999D + d1 * 3.71D;
        d -= (int)d;
        int i = (short)(int)(16001D + d * 16000D);
        com.maddox.il2.ai.RangeRandom rangerandom = new RangeRandom(i);
        return rangerandom.nextFloat(9050F, 10900F);
    }

    private void recreateBalloon()
    {
        if(balloonActor != null)
        {
            balloonActor.destroy();
            balloonActor = null;
        }
        if(!balloon.dead)
        {
            pos.getAbs(p);
            float f = prop.HEIGHT;
            float f1 = computeTopPossibleHeight(p.x, p.y);
            balloonActor = new Balloon(this, f, f1, rope.dead);
        }
    }

    private void recreateRope()
    {
        if(ropeActor != null)
        {
            ropeActor.destroy();
            ropeActor = null;
        }
        if(!rope.dead)
        {
            float f = prop.HEIGHT;
            ropeActor = new Rope(this, f, prop.ROPE_SEG_LENGTH, prop.meshRName);
        }
    }

    private void _balloon_Trouble(com.maddox.il2.engine.Actor actor, float f)
    {
        if(balloonActor == null)
            return;
        if(balloon.dead)
            java.lang.System.out.println("***Ballon: strange trouble");
        if(isNetMirror())
        {
            balloon.damage+= = f;
            balloon.mirror_initiator = actor;
        } else
        {
            applyDamage(98, actor, f, true);
        }
    }

    void balloonCollision(com.maddox.il2.engine.Actor actor)
    {
        _balloon_Trouble(actor, 1.0F);
    }

    void balloonShot(com.maddox.il2.engine.Actor actor)
    {
        _balloon_Trouble(actor, 1.0F);
    }

    void balloonExplosion(com.maddox.il2.engine.Actor actor)
    {
        _balloon_Trouble(actor, 1.0F);
    }

    void balloonDisappeared()
    {
        if(!balloon.dead)
            java.lang.System.out.println("***balloon disappeared strangely");
        balloonActor = null;
    }

    private void _rope_Trouble(com.maddox.il2.engine.Actor actor, float f)
    {
        if(ropeActor == null)
            return;
        if(rope.dead)
            java.lang.System.out.println("***Rope: strange trouble");
        if(isNetMirror())
        {
            rope.damage+= = f;
            rope.mirror_initiator = actor;
        } else
        {
            applyDamage(114, actor, f, true);
        }
    }

    void ropeCollision(com.maddox.il2.engine.Actor actor)
    {
        _rope_Trouble(actor, 1.0F);
    }

    void ropeDisappeared()
    {
        if(!rope.dead)
            java.lang.System.out.println("***rope disappeared strangely");
        ropeActor = null;
    }

    public void startMove()
    {
        if(!interpEnd("move"))
            interpPut(new Move(), "move", com.maddox.rts.Time.current(), null);
    }

    public int HitbyMask()
    {
        return prop.HITBY_MASK;
    }

    public int chooseBulletType(com.maddox.il2.engine.BulletProperties abulletproperties[])
    {
        if(anchor.dead)
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
        return !anchor.dead ? 0 : -1;
    }

    public boolean getShotpointOffset(int i, com.maddox.JGP.Point3d point3d)
    {
        if(anchor.dead)
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

    private void mastersend_AKillCommand(int i, com.maddox.il2.engine.Actor actor)
    {
        if(!isNetMaster())
            return;
        if(com.maddox.il2.game.Mission.isDeathmatch())
        {
            float f = com.maddox.il2.game.Mission.respawnTime("Aeroanchored");
            respawnDelay = com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric.SecsToTicks(com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric.Rnd(f, f * 1.2F));
        } else
        {
            respawnDelay = 0L;
        }
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        try
        {
            netmsgguaranted.writeByte(i);
            netmsgguaranted.writeNetObj(actor != null ? ((com.maddox.rts.NetObj) (actor.net)) : null);
            net.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void mastersend_RespawnCommand()
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

    public void netFirstUpdate(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        netmsgguaranted.writeByte(73);
        int i = (anchor.dead ? 0 : 1) + (rope.dead ? 0 : 2) + (balloon.dead ? 0 : 4);
        netmsgguaranted.writeByte(i);
        net.postTo(netchannel, netmsgguaranted);
    }

    private void mirrorsend_DamageIfNeed()
    {
        if(!anchor.dead && anchor.damage >= 0.00390625F)
        {
            _mirrorsend_ADamageRequest(97, anchor);
            mirrorSendDmgDelay = 7;
        }
        if(!rope.dead && rope.damage >= 0.00390625F)
        {
            _mirrorsend_ADamageRequest(114, rope);
            mirrorSendDmgDelay = 7;
        }
        if(!balloon.dead && balloon.damage >= 0.00390625F)
        {
            _mirrorsend_ADamageRequest(98, balloon);
            mirrorSendDmgDelay = 7;
        }
    }

    private void _mirrorsend_ADamageRequest(int i, com.maddox.il2.objects.vehicles.aeronautics.PartState partstate)
    {
        float f = partstate.damage;
        com.maddox.il2.engine.Actor actor = partstate.mirror_initiator;
        partstate.damage = 0.0F;
        partstate.mirror_initiator = null;
        if(net.masterChannel() instanceof com.maddox.rts.NetChannelInStream)
            return;
        int j = (int)(f * 256F) - 1;
        if(j < 0)
            j = 0;
        else
        if(j > 255)
            j = 255;
        try
        {
            com.maddox.rts.NetMsgFiltered netmsgfiltered = new NetMsgFiltered();
            netmsgfiltered.writeByte(i);
            netmsgfiltered.writeByte(j);
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

    private void applyDamage(int i, com.maddox.il2.engine.Actor actor, float f, boolean flag)
    {
        switch(i)
        {
        case 97: // 'a'
            if(anchor.dead)
                return;
            anchor.damage+= = f;
            if(anchor.damage < 1.0F)
                return;
            if(flag)
                mastersend_AKillCommand(97, actor);
            if(balloonActor != null)
                balloonActor.somebodyKilled(97);
            if(ropeActor != null)
                ropeActor.somebodyKilled(97);
            applyDamage(114, actor, 1.0F, false);
            anchor.resetToDead();
            ShowExplode(14F);
            setMesh(prop.meshANameDead);
            setDefaultPose();
            break;

        case 114: // 'r'
            if(rope.dead)
                return;
            rope.damage+= = f;
            if(rope.damage < 1.0F)
                return;
            if(flag)
                mastersend_AKillCommand(114, actor);
            if(ropeActor == null)
            {
                java.lang.System.out.println("***balloon: strange kill r");
                return;
            }
            if(balloonActor != null)
                balloonActor.somebodyKilled(114);
            rope.resetToDead();
            if(ropeActor != null)
                ropeActor.somebodyKilled(114);
            break;

        case 98: // 'b'
            if(balloon.dead)
                return;
            balloon.damage+= = f;
            if(balloon.damage < 1.0F)
                return;
            if(flag)
                mastersend_AKillCommand(98, actor);
            if(balloonActor == null)
            {
                java.lang.System.out.println("***balloon: strange kill b");
                return;
            }
            if(ropeActor != null)
                ropeActor.somebodyKilled(98);
            applyDamage(114, actor, 1.0F, false);
            balloon.resetToDead();
            if(balloonActor != null)
                balloonActor.somebodyKilled(98);
            break;
        }
        if(!getDiedFlag())
        {
            com.maddox.il2.ai.World.onActorDied(this, actor);
            if(balloonActor != null)
                com.maddox.il2.ai.World.onActorDied(balloonActor, actor);
            if(ropeActor != null)
                com.maddox.il2.ai.World.onActorDied(ropeActor, actor);
        }
    }

    private void handleStartCommandFromMaster(boolean flag, boolean flag1, boolean flag2)
    {
        if(flag || flag2)
            flag1 = true;
        if(flag)
            anchor.resetToDead();
        else
            anchor.resetToLive();
        if(flag1)
            rope.resetToDead();
        else
            rope.resetToLive();
        if(flag2)
            balloon.resetToDead();
        else
            balloon.resetToLive();
        setMesh(flag ? prop.meshANameDead : prop.meshAName);
        setDefaultPose();
        recreateBalloon();
        recreateRope();
        boolean flag3 = anchor.dead || rope.dead || balloon.dead;
        setDiedFlag(flag3);
        if(balloonActor != null)
            balloonActor.setDiedFlag(flag3);
        if(ropeActor != null)
            ropeActor.setDiedFlag(flag3);
    }

    public void createNetObject(com.maddox.rts.NetChannel netchannel, int i)
    {
        if(netchannel == null)
            net = new Master(this);
        else
            net = new Mirror(this, netchannel, i);
    }

    com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredProperties prop;
    com.maddox.il2.objects.vehicles.aeronautics.PartState anchor;
    com.maddox.il2.objects.vehicles.aeronautics.PartState rope;
    com.maddox.il2.objects.vehicles.aeronautics.PartState balloon;
    private com.maddox.il2.objects.vehicles.aeronautics.Balloon balloonActor;
    private com.maddox.il2.objects.vehicles.aeronautics.Rope ropeActor;
    private long respawnDelay;
    private int mirrorSendDmgDelay;
    private float heightAboveLandSurface;
    private static com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredProperties constr_arg1 = null;
    private static com.maddox.il2.engine.ActorSpawnArg constr_arg2 = null;
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();
    private static com.maddox.JGP.Vector3f n = new Vector3f();
    private static com.maddox.JGP.Vector3d tmpv = new Vector3d();
















}
