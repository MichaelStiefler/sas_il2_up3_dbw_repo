// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Platform4.java

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
import com.maddox.il2.engine.Sun;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.weapons.CannonMidrangeGeneric;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.il2.objects.weapons.MachineGunFlak30_20mm;
import com.maddox.rts.ObjState;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.trains:
//            Wagon, Train, WagonSpawn

public class Platform4 extends com.maddox.il2.objects.trains.Wagon
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener, com.maddox.il2.ai.ground.Predator, com.maddox.il2.ai.ground.HunterInterface
{
    public static class SPAWN
        implements com.maddox.il2.objects.trains.WagonSpawn
    {

        public com.maddox.il2.objects.trains.Wagon wagonSpawn(com.maddox.il2.objects.trains.Train train)
        {
            return new Platform4(train);
        }

        public SPAWN()
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
        private com.maddox.JGP.Point3d fireOffset;













        public FiringDevice()
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

    private static java.lang.String getMeshName(int i)
    {
        switch(com.maddox.il2.ai.World.cur().camouflage)
        {
        case 0: // '\0'
        case 1: // '\001'
        case 2: // '\002'
        default:
            return "3do/Trains/Platform4" + (i != 1 ? "" : "_Dmg") + "/hier.him";
        }
    }

    public static java.lang.String getMeshNameForEditor()
    {
        return com.maddox.il2.objects.trains.Platform4.getMeshName(0);
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
        for(int i = 0; i < 2; i++)
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
            for(int i = 0; i < 2; i++)
                if(arms[i] != null)
                {
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
            for(int i = 0; i < 2; i++)
                if(arms[i] != null && arms[i].aime != null)
                    arms[i].aime.forgetAiming();

        }
    }

    public Platform4(com.maddox.il2.objects.trains.Train train)
    {
        super(train, com.maddox.il2.objects.trains.Platform4.getMeshName(0), com.maddox.il2.objects.trains.Platform4.getMeshName(1));
        arms = new com.maddox.il2.objects.trains.FiringDevice[2];
        HEAD_YAW_RANGE = new AnglesRange(-180F, 180F);
        try
        {
            life = 0.012F;
            ignoreTNT = 0.4F;
            killTNT = 3F;
            bodyMaterial = 2;
            for(int i = 0; i < 2; i++)
            {
                arms[i] = new FiringDevice();
                arms[i].id = i;
                arms[i].gun = new MachineGunFlak30_20mm();
                if(arms[i].gun == null)
                {
                    java.lang.System.out.println("Train: Gun is not created");
                } else
                {
                    arms[i].gun.set(this, "ShellStart" + i);
                    arms[i].gun.loadBullets(-1);
                }
                setGunAngles(arms[i], 0.0F, 39.5F);
                com.maddox.il2.engine.Loc loc = new Loc();
                hierMesh().setCurChunk("Gun" + i);
                hierMesh().getChunkLocObj(loc);
                arms[i].fireOffset = new Point3d();
                loc.get(arms[i].fireOffset);
                arms[i].aime = new Aim(this, isNetMirror());
            }

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
        for(int i = 0; i < 2; i++)
            arms[i].aime.tick_();

    }

    public int WeaponsMask()
    {
        return 2;
    }

    public float AttackMaxDistance()
    {
        return 2200F;
    }

    public float getReloadingTime(com.maddox.il2.ai.ground.Aim aim)
    {
        return 1.2F;
    }

    public float chainFireTime(com.maddox.il2.ai.ground.Aim aim)
    {
        return 6.5F * com.maddox.il2.objects.trains.Platform4.Rnd(0.75F, 1.25F);
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
        aim.setRotationForParking(firingdevice.headYaw, firingdevice.gunPitch, 0.0F, 39.5F, HEAD_YAW_RANGE, 38F, 18F);
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
        com.maddox.il2.engine.Actor actor = null;
        com.maddox.il2.ai.ground.NearestEnemies.set(WeaponsMask());
        actor = com.maddox.il2.ai.ground.NearestEnemies.getAFoundEnemy(pos.getAbsPoint(), AttackMaxDistance(), getArmy());
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
            com.maddox.il2.objects.trains.FiringDevice firingdevice = GetFiringDevice(aim);
            send_FireCommand(firingdevice.id, actor, aim.shotpoint_idx, i != 0 ? f : -1F);
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
        if(i < 0 || i >= 2)
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
        if(i < 0 || i >= 2)
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
        float f1 = f * com.maddox.il2.objects.trains.Platform4.Rnd(0.8F, 1.2F);
        if(!com.maddox.il2.ai.Aimer.Aim((com.maddox.il2.ai.BulletAimer)firingdevice.gun, actor, this, f1, p1, firingdevice.fireOffset))
            return 0;
        com.maddox.JGP.Point3d point3d = new Point3d();
        com.maddox.il2.ai.Aimer.GetPredictedTargetPosition(point3d);
        com.maddox.JGP.Point3d point3d1 = com.maddox.il2.ai.Aimer.GetHunterFirePoint();
        float f2 = 0.19F;
        double d = point3d.distance(point3d1);
        double d1 = point3d.z;
        point3d.sub(point3d1);
        point3d.scale(com.maddox.il2.objects.trains.Platform4.Rnd(0.97999999999999998D, 1.02D));
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
                f8 *= 0.5F;
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
        float f6 = 83.33334F;
        f5 = f5 < f6 ? f5 / f6 : 1.0F;
        f2 += f5 * 2.0F;
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
            f3 = 38F;
            d1 = 18F;
        }
        int j = aim.setRotationForTargeting(this, pos.getAbs().getOrient(), point3d1, firingdevice.headYaw, firingdevice.gunPitch, vector3d, f2, f1, HEAD_YAW_RANGE, -10F, 89F, f3, d1, 0.0F);
        return j;
    }

    public void singleShot(com.maddox.il2.ai.ground.Aim aim)
    {
        com.maddox.il2.objects.trains.FiringDevice firingdevice = GetFiringDevice(aim);
        firingdevice.gun.shots(1);
    }

    public void startFire(com.maddox.il2.ai.ground.Aim aim)
    {
        com.maddox.il2.objects.trains.FiringDevice firingdevice = GetFiringDevice(aim);
        firingdevice.gun.shots(-1);
    }

    public void continueFire(com.maddox.il2.ai.ground.Aim aim)
    {
    }

    public void stopFire(com.maddox.il2.ai.ground.Aim aim)
    {
        com.maddox.il2.objects.trains.FiringDevice firingdevice = GetFiringDevice(aim);
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
    private static final int N_FIRING_DEVICES = 2;
    private com.maddox.il2.objects.trains.FiringDevice arms[];
    private static final float ATTACK_MAX_DISTANCE = 2200F;
    private static final float GUN_MIN_PITCH = -10F;
    private static final float GUN_MAX_PITCH = 89F;
    private static final float HEAD_MAX_YAW_SPEED = 38F;
    private static final float GUN_MAX_PITCH_SPEED = 18F;
    private com.maddox.il2.ai.AnglesRange HEAD_YAW_RANGE;
    private static final float DELAY_AFTER_SHOOT = 1.2F;
    private static final float CHAINFIRE_TIME = 6.5F;
    private static com.maddox.JGP.Vector3d tmpv = new Vector3d();
    private static com.maddox.JGP.Point3d p1 = new Point3d();

    static 
    {
        cls = com.maddox.il2.objects.trains.Platform4.class;
        com.maddox.rts.Spawn.add(cls, new SPAWN());
    }
}
