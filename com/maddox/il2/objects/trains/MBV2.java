// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MBV2.java

package com.maddox.il2.objects.trains;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Aimer;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.AnglesForkExtended;
import com.maddox.il2.ai.AnglesRange;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Aim;
import com.maddox.il2.ai.ground.HunterInterface;
import com.maddox.il2.ai.ground.NearestEnemies;
import com.maddox.il2.ai.ground.Predator;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.weapons.CannonMidrangeGeneric;
import com.maddox.il2.objects.weapons.CannonZIS3;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.il2.objects.weapons.MMGunShKASt;
import com.maddox.rts.ObjState;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.trains:
//            Wagon, Train, WagonSpawn

public class MBV2 extends com.maddox.il2.objects.trains.Wagon
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener, com.maddox.il2.ai.ground.Predator, com.maddox.il2.ai.ground.HunterInterface
{
    public class FiringDevice
    {

        private int id;
        private com.maddox.il2.objects.weapons.Gun gun;
        private com.maddox.il2.ai.ground.Aim aime;
        private float headYaw;
        private float gunPitch;
        private com.maddox.JGP.Point3d fireOffset;
        private com.maddox.il2.engine.Orient fireOrient;
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
        public java.lang.Class gunClass;















        public FiringDevice()
        {
        }
    }

    public static class SPAWN
        implements com.maddox.il2.objects.trains.WagonSpawn
    {

        public com.maddox.il2.objects.trains.Wagon wagonSpawn(com.maddox.il2.objects.trains.Train train)
        {
            return new MBV2(train);
        }

        public SPAWN()
        {
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

    public static final float KmHourToMSec(float f)
    {
        return f / 3.6F;
    }

    private static java.lang.String getMeshName(int i)
    {
        java.lang.String s = "summer";
        switch(com.maddox.il2.ai.World.cur().camouflage)
        {
        case 1: // '\001'
            s = "winter";
            break;
        }
        return "3do/Trains/MBV2" + (i == 1 ? "_Dmg" : "") + "/" + s + "/hier.him";
    }

    public static java.lang.String getMeshNameForEditor()
    {
        return com.maddox.il2.objects.trains.MBV2.getMeshName(0);
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

    protected void hiddenexplode()
    {
        eraseGuns();
        super.hiddenexplode();
    }

    protected void explode(com.maddox.il2.engine.Actor actor)
    {
        eraseGuns();
        super.explode(actor);
    }

    private final com.maddox.il2.objects.trains.FiringDevice GetFiringDevice(com.maddox.il2.ai.ground.Aim aim)
    {
        for(int i = 0; i < 7; i++)
            if(arms[i] != null && arms[i].aime == aim)
                return arms[i];

        java.lang.System.out.println("Internal error: Can't find train gun.");
        return null;
    }

    private void setGunAngles(com.maddox.il2.objects.trains.FiringDevice firingdevice, float f, float f1)
    {
        firingdevice.headYaw = f;
        firingdevice.gunPitch = f1;
        hierMesh().chunkSetAngles("Head" + firingdevice.id, firingdevice.headYaw, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("Gun" + firingdevice.id, -firingdevice.gunPitch, 0.0F, 0.0F);
        pos.inValidate(false);
    }

    private void eraseGuns()
    {
        if(arms != null)
        {
            for(int i = 0; i < 7; i++)
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
                    com.maddox.rts.ObjState.destroy(arms[i].gun);
                    arms[i].gun = null;
                }
                arms[i].fireOffset = null;
                arms[i] = null;
            }

            arms = null;
        }
    }

    protected void forgetAllAiming()
    {
        if(arms != null)
        {
            for(int i = 0; i < 7; i++)
                if(arms[i] != null && arms[i].aime != null)
                    arms[i].aime.forgetAiming();

        }
    }

    private void fillGunProperties(int i, int j, boolean flag, float f, float f1, float f2, int k, 
            float f3, com.maddox.il2.ai.AnglesRange anglesrange, float f4, float f5, float f6, float f7, float f8, 
            float f9, float f10, float f11)
    {
        arms[i].WEAPONS_MASK = j;
        arms[i].TRACKING_ONLY = flag;
        arms[i].ATTACK_MAX_DISTANCE = f;
        arms[i].ATTACK_MAX_RADIUS = f1;
        arms[i].ATTACK_MAX_HEIGHT = f2;
        arms[i].ATTACK_FAST_TARGETS = k;
        arms[i].FAST_TARGETS_ANGLE_ERROR = f3;
        arms[i].HEAD_YAW_RANGE = anglesrange;
        arms[i].HEAD_STD_YAW = f4;
        arms[i].GUN_MIN_PITCH = f5;
        arms[i].GUN_STD_PITCH = f6;
        arms[i].GUN_MAX_PITCH = f7;
        arms[i].HEAD_MAX_YAW_SPEED = f8;
        arms[i].GUN_MAX_PITCH_SPEED = f9;
        arms[i].DELAY_AFTER_SHOOT = f10;
        arms[i].CHAINFIRE_TIME = f11;
    }

    public MBV2(com.maddox.il2.objects.trains.Train train)
    {
        super(train, com.maddox.il2.objects.trains.MBV2.getMeshName(0), com.maddox.il2.objects.trains.MBV2.getMeshName(1));
        arms = new com.maddox.il2.objects.trains.FiringDevice[7];
        try
        {
            life = 0.036F;
            ignoreTNT = 16F;
            killTNT = 42F;
            bodyMaterial = 2;
            for(int i = 0; i < 3; i++)
            {
                arms[i] = new FiringDevice();
                arms[i].id = i;
                arms[i].gun = new CannonZIS3();
                if(arms[i].gun == null)
                {
                    java.lang.System.out.println("Train: Gun is not created");
                } else
                {
                    arms[i].gun.set(this, "ShellStart" + i);
                    arms[i].gun.loadBullets(-1);
                }
                com.maddox.il2.engine.Loc loc = new Loc();
                hierMesh().setCurChunk("Head" + i);
                hierMesh().getChunkLocObj(loc);
                arms[i].fireOffset = new Point3d();
                loc.get(arms[i].fireOffset);
                arms[i].fireOrient = new Orient();
                loc.get(arms[i].fireOrient);
                arms[i].aime = new Aim(this, isNetMirror());
                try
                {
                    arms[i].gunClass = java.lang.Class.forName("com.maddox.il2.objects.weapons.CannonZIS3");
                }
                catch(java.lang.Exception exception1) { }
            }

            for(int j = 3; j < 7; j++)
            {
                arms[j] = new FiringDevice();
                arms[j].id = j;
                arms[j].gun = new MMGunShKASt();
                if(arms[j].gun == null)
                {
                    java.lang.System.out.println("Train: Gun is not created");
                } else
                {
                    arms[j].gun.set(this, "ShellStart" + j);
                    arms[j].gun.loadBullets(-1);
                }
                com.maddox.il2.engine.Loc loc1 = new Loc();
                hierMesh().setCurChunk("Head" + j);
                hierMesh().getChunkLocObj(loc1);
                arms[j].fireOffset = new Point3d();
                loc1.get(arms[j].fireOffset);
                arms[j].fireOrient = new Orient();
                loc1.get(arms[j].fireOrient);
                arms[j].aime = new Aim(this, isNetMirror());
                try
                {
                    arms[j].gunClass = java.lang.Class.forName("com.maddox.il2.objects.weapons.MMGunShKASt");
                }
                catch(java.lang.Exception exception2) { }
            }

            fillGunProperties(0, com.maddox.il2.objects.weapons.Gun.getProperties(arms[0].gunClass).weaponType, false, 4000F, 4000F, 4000F, 0, 0.0F, new AnglesRange(-138F, 138F), 0.0F, -5F, 0.0F, 25F, 7.5F, 7.5F, 9F, 0.0F);
            fillGunProperties(1, com.maddox.il2.objects.weapons.Gun.getProperties(arms[1].gunClass).weaponType, false, 4000F, 4000F, 4000F, 0, 0.0F, new AnglesRange(-159F, 159F), 0.0F, -5F, 0.0F, 25F, 7.5F, 7.5F, 9F, 0.0F);
            fillGunProperties(2, com.maddox.il2.objects.weapons.Gun.getProperties(arms[2].gunClass).weaponType, false, 4000F, 4000F, 4000F, 0, 0.0F, new AnglesRange(-140F, 140F), 0.0F, -5F, 0.0F, 25F, 7.5F, 7.5F, 9F, 0.0F);
            fillGunProperties(3, com.maddox.il2.objects.weapons.Gun.getProperties(arms[3].gunClass).weaponType, false, 2200F, 2200F, 2200F, 1, 0.0F, new AnglesRange(-45F, 45F), 0.0F, -45F, 0.0F, 45F, 38F, 18F, 1.2F, 6.5F);
            fillGunProperties(4, com.maddox.il2.objects.weapons.Gun.getProperties(arms[4].gunClass).weaponType, false, 2200F, 2200F, 2200F, 1, 0.0F, new AnglesRange(-45F, 45F), 0.0F, -45F, 0.0F, 45F, 38F, 18F, 1.2F, 6.5F);
            fillGunProperties(5, com.maddox.il2.objects.weapons.Gun.getProperties(arms[5].gunClass).weaponType, false, 2200F, 2200F, 2200F, 1, 0.0F, new AnglesRange(-45F, 45F), 0.0F, -45F, 0.0F, 45F, 38F, 18F, 1.2F, 6.5F);
            fillGunProperties(6, com.maddox.il2.objects.weapons.Gun.getProperties(arms[6].gunClass).weaponType, false, 2200F, 2200F, 2200F, 1, 0.0F, new AnglesRange(-45F, 45F), 0.0F, -45F, 0.0F, 45F, 38F, 18F, 1.2F, 6.5F);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    void place(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, boolean flag, boolean flag1)
    {
        super.place(point3d, point3d1, flag, flag1);
        if(arms == null)
            return;
        for(int i = 0; i < 7; i++)
            arms[i].aime.tick_();

    }

    private final com.maddox.il2.objects.trains.FiringDevice GetGunProperties(com.maddox.il2.ai.ground.Aim aim)
    {
        for(int i = 0; i < 7; i++)
            if(arms[i].aime == aim)
                return arms[i];

        java.lang.System.out.println("Internal error 2: Can't find ship gun.");
        return null;
    }

    public int WeaponsMask()
    {
        int i = 0;
        for(int j = 0; j < 7; j++)
            i |= arms[j].WEAPONS_MASK;

        return i;
    }

    public float AttackMaxDistance()
    {
        float f = 0.0F;
        for(int i = 0; i < 7; i++)
            if(arms[i].ATTACK_MAX_DISTANCE > f)
                f = arms[i].ATTACK_MAX_DISTANCE;

        return f;
    }

    public float getReloadingTime(com.maddox.il2.ai.ground.Aim aim)
    {
        for(int i = 0; i < 7; i++)
            if(arms[i].aime == aim)
                return arms[i].DELAY_AFTER_SHOOT;

        return 0.0F;
    }

    public float chainFireTime(com.maddox.il2.ai.ground.Aim aim)
    {
        float f = 0.0F;
        for(int i = 0; i < 7; i++)
            if(arms[i].aime == aim)
                f = arms[i].CHAINFIRE_TIME;

        return f <= 0.0F ? 0.0F : f * com.maddox.il2.objects.trains.MBV2.Rnd(0.75F, 1.25F);
    }

    public float probabKeepSameEnemy(com.maddox.il2.engine.Actor actor)
    {
        return 0.75F;
    }

    public float minTimeRelaxAfterFight()
    {
        return 0.0F;
    }

    public void gunStartParking(com.maddox.il2.ai.ground.Aim aim)
    {
        com.maddox.il2.objects.trains.FiringDevice firingdevice = GetFiringDevice(aim);
        boolean flag = false;
        for(int i = 0; i < 7; i++)
            if(arms[i].aime == aim)
                firingdevice = arms[i];

        aim.setRotationForParking(firingdevice.headYaw, firingdevice.gunPitch, firingdevice.HEAD_STD_YAW, firingdevice.GUN_STD_PITCH, firingdevice.HEAD_YAW_RANGE, firingdevice.HEAD_MAX_YAW_SPEED, firingdevice.GUN_MAX_PITCH_SPEED);
    }

    public void gunInMove(boolean flag, com.maddox.il2.ai.ground.Aim aim)
    {
        com.maddox.il2.objects.trains.FiringDevice firingdevice = GetFiringDevice(aim);
        float f = aim.t();
        float f1 = aim.anglesYaw.getDeg(f);
        float f2 = aim.anglesPitch.getDeg(f);
        setGunAngles(firingdevice, f1, f2);
    }

    public com.maddox.il2.engine.Actor findEnemy(com.maddox.il2.ai.ground.Aim aim)
    {
        if(isNetMirror())
            return null;
        int i = 0;
        for(int j = 0; j < 7; j++)
            if(arms[j].aime == aim)
                i = arms[j].id;

        com.maddox.il2.engine.Actor actor = null;
        switch(arms[i].ATTACK_FAST_TARGETS)
        {
        case 0: // '\0'
            com.maddox.il2.ai.ground.NearestEnemies.set(arms[i].WEAPONS_MASK, -9999.9F, com.maddox.il2.objects.trains.MBV2.KmHourToMSec(100F));
            break;

        case 1: // '\001'
            com.maddox.il2.ai.ground.NearestEnemies.set(arms[i].WEAPONS_MASK);
            break;

        default:
            com.maddox.il2.ai.ground.NearestEnemies.set(arms[i].WEAPONS_MASK, com.maddox.il2.objects.trains.MBV2.KmHourToMSec(100F), 9999.9F);
            break;
        }
        actor = com.maddox.il2.ai.ground.NearestEnemies.getAFoundEnemy(pos.getAbsPoint(), arms[i].ATTACK_MAX_RADIUS, getArmy());
        if(actor == null)
            return null;
        if(!(actor instanceof com.maddox.il2.ai.ground.Prey))
        {
            java.lang.System.out.println("trplatf4: nearest enemies: non-Prey");
            return null;
        }
        com.maddox.il2.objects.trains.FiringDevice firingdevice = GetFiringDevice(aim);
        com.maddox.il2.engine.BulletProperties bulletproperties = null;
        if(firingdevice.gun.prop != null)
        {
            int k = ((com.maddox.il2.ai.ground.Prey)actor).chooseBulletType(firingdevice.gun.prop.bullet);
            if(k < 0)
                return null;
            bulletproperties = firingdevice.gun.prop.bullet[k];
        }
        int l = ((com.maddox.il2.ai.ground.Prey)actor).chooseShotpoint(bulletproperties);
        if(l < 0)
        {
            return null;
        } else
        {
            aim.shotpoint_idx = l;
            return actor;
        }
    }

    public boolean enterToFireMode(int i, com.maddox.il2.engine.Actor actor, float f, com.maddox.il2.ai.ground.Aim aim)
    {
        if(!isNetMirror())
        {
            com.maddox.il2.objects.trains.FiringDevice firingdevice = GetFiringDevice(aim);
            send_FireCommand(firingdevice.id, actor, aim.shotpoint_idx, i == 0 ? -1F : f);
        }
        return true;
    }

    protected void Track_Mirror(int i, com.maddox.il2.engine.Actor actor, int j)
    {
        if(IsDamaged())
            return;
        if(actor == null)
            return;
        if(arms == null || arms[i].aime == null)
            return;
        if(i < 0 || i >= 7)
        {
            return;
        } else
        {
            arms[i].aime.passive_StartFiring(0, actor, j, 0.0F);
            return;
        }
    }

    protected void Fire_Mirror(int i, com.maddox.il2.engine.Actor actor, int j, float f)
    {
        if(IsDamaged())
            return;
        if(actor == null)
            return;
        if(arms == null || arms[i].aime == null)
            return;
        if(i < 0 || i >= 7)
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
        com.maddox.il2.objects.trains.FiringDevice firingdevice = GetFiringDevice(aim);
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
        float f1 = f * com.maddox.il2.objects.trains.MBV2.Rnd(0.8F, 1.2F);
        if(!com.maddox.il2.ai.Aimer.Aim((com.maddox.il2.ai.BulletAimer)firingdevice.gun, actor, this, f1, p1, firingdevice.fireOffset))
            return 0;
        com.maddox.JGP.Point3d point3d = new Point3d();
        com.maddox.il2.ai.Aimer.GetPredictedTargetPosition(point3d);
        com.maddox.JGP.Point3d point3d1 = com.maddox.il2.ai.Aimer.GetHunterFirePoint();
        float f2 = 0.19F;
        double d = point3d.distance(point3d1);
        double d1 = point3d.z;
        point3d.sub(point3d1);
        point3d.scale(com.maddox.il2.objects.trains.MBV2.Rnd(0.97999999999999998D, 1.02D));
        point3d.add(point3d1);
        if(f1 > 0.001F)
        {
            com.maddox.JGP.Point3d point3d2 = new Point3d();
            actor.pos.getAbs(point3d2);
            tmpv.sub(point3d, point3d2);
            double d2 = tmpv.length();
            if(d2 > 0.001D)
            {
                float f6 = (float)d2 / f1;
                if(f6 > 200F)
                    f6 = 200F;
                float f8 = f6 * 0.01F;
                point3d2.sub(point3d1);
                double d3 = point3d2.x * point3d2.x + point3d2.y * point3d2.y + point3d2.z * point3d2.z;
                if(d3 > 0.01D)
                {
                    float f9 = (float)tmpv.dot(point3d2);
                    f9 /= (float)(d2 * java.lang.Math.sqrt(d3));
                    f9 = (float)java.lang.Math.sqrt(1.0F - f9 * f9);
                    f8 *= 0.4F + 0.6F * f9;
                }
                f8 *= 0.5F;
                int k = com.maddox.il2.game.Mission.curCloudsType();
                if(k > 2)
                {
                    float f10 = k > 4 ? 400F : 800F;
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
            float f3 = (-com.maddox.il2.ai.World.Sun().ToSun.z - 0.15F) / 0.13F;
            if(f3 >= 1.0F)
                f3 = 1.0F;
            if((actor instanceof com.maddox.il2.objects.air.Aircraft) && com.maddox.rts.Time.current() - ((com.maddox.il2.objects.air.Aircraft)actor).tmSearchlighted < 1000L)
                f3 = 0.0F;
            f2 += 10F * f3;
        }
        float f4 = (float)actor.getSpeed(null) - 10F;
        float f5 = 83.33334F;
        f4 = f4 >= f5 ? 1.0F : f4 / f5;
        f2 += f4 * 2.0F;
        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        if(!((com.maddox.il2.ai.BulletAimer)firingdevice.gun).FireDirection(point3d1, point3d, vector3d))
            return 0;
        float f7;
        if(flag)
        {
            f7 = 99999F;
            d1 = 99999D;
        } else
        {
            f7 = firingdevice.HEAD_MAX_YAW_SPEED;
            d1 = firingdevice.GUN_MAX_PITCH_SPEED;
        }
        com.maddox.il2.engine.Orient orient = new Orient();
        orient.add(arms[firingdevice.id].fireOrient, pos.getAbs().getOrient());
        orient.setYPR(orient.getYaw(), orient.getPitch(), orient.getRoll());
        int j = aim.setRotationForTargeting(this, orient, point3d1, arms[firingdevice.id].headYaw, arms[firingdevice.id].gunPitch, vector3d, f2, f1, arms[firingdevice.id].HEAD_YAW_RANGE, arms[firingdevice.id].GUN_MIN_PITCH, arms[firingdevice.id].GUN_MAX_PITCH, f7, (float)d1, 0.0F);
        return j;
    }

    public void singleShot(com.maddox.il2.ai.ground.Aim aim)
    {
        com.maddox.il2.objects.trains.FiringDevice firingdevice = GetFiringDevice(aim);
        if(!firingdevice.TRACKING_ONLY)
            firingdevice.gun.shots(1);
    }

    public void startFire(com.maddox.il2.ai.ground.Aim aim)
    {
        com.maddox.il2.objects.trains.FiringDevice firingdevice = GetFiringDevice(aim);
        if(!firingdevice.TRACKING_ONLY)
            firingdevice.gun.shots(-1);
    }

    public void continueFire(com.maddox.il2.ai.ground.Aim aim)
    {
    }

    public void stopFire(com.maddox.il2.ai.ground.Aim aim)
    {
        com.maddox.il2.objects.trains.FiringDevice firingdevice = GetFiringDevice(aim);
        if(!firingdevice.TRACKING_ONLY)
            firingdevice.gun.shots(0);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private static java.lang.Class cls;
    private static final int N_FIRING_DEVICES = 7;
    private com.maddox.il2.objects.trains.FiringDevice arms[];
    private static com.maddox.JGP.Vector3d tmpv = new Vector3d();
    private static com.maddox.JGP.Point3d p1 = new Point3d();

    static 
    {
        cls = com.maddox.il2.objects.trains.MBV2.class;
        cls = com.maddox.il2.objects.trains.MBV2.class;
        com.maddox.rts.Spawn.add(cls, new SPAWN());
    }
}
