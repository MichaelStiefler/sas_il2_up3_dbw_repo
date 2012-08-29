// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Aim.java

package com.maddox.il2.ai.ground;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.AnglesForkExtended;
import com.maddox.il2.ai.AnglesRange;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Time;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.ai.ground:
//            HunterInterface

public class Aim
{

    private static final int SecsToTicks(float f)
    {
        int i = (int)(0.5F + f / com.maddox.rts.Time.tickLenFs());
        return i >= 1 ? i : 1;
    }

    private static final float TicksToSecs(long l)
    {
        return (float)l * com.maddox.rts.Time.tickLenFs();
    }

    private static final float Rnd(float f, float f1)
    {
        return com.maddox.il2.ai.World.Rnd().nextFloat(f, f1);
    }

    private static final boolean RndB(float f)
    {
        return com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < f;
    }

    public com.maddox.il2.engine.Actor getEnemy()
    {
        return enemy;
    }

    public float t()
    {
        return 1.0F - (float)timeCur / (float)timeTot;
    }

    public boolean isInFiringMode()
    {
        return curState == 3 || curState == 4;
    }

    public boolean isInAimingMode()
    {
        return curState != 0;
    }

    public Aim(com.maddox.il2.ai.ground.HunterInterface hunterinterface, boolean flag)
    {
        passive = flag;
        bodyRotation = false;
        hunter = hunterinterface;
        id = globalid++;
        enemy = null;
        shotpoint_idx = -1;
        anglesYaw = new AnglesForkExtended(true, 0.0F, 0.0F);
        anglesPitch = new AnglesFork(0.0F);
        curState = S(0);
        waitforparkingTimer = 0x98967f;
        timeTot = 0L;
        passive_resetAutoParking();
        if(passive)
        {
            waitenemyTimer = com.maddox.il2.ai.ground.Aim.SecsToTicks(9999F);
            reloadingTimer = 0;
        } else
        {
            waitenemyTimer = com.maddox.il2.ai.ground.Aim.SecsToTicks(com.maddox.il2.ai.ground.Aim.Rnd(1.7F, 3.6F));
            reloadingTimer = com.maddox.il2.ai.ground.Aim.SecsToTicks(com.maddox.il2.ai.ground.Aim.Rnd(1.0F, 10F));
        }
    }

    public Aim(com.maddox.il2.ai.ground.HunterInterface hunterinterface, boolean flag, float f)
    {
        this(hunterinterface, flag);
        if(!passive)
            reloadingTimer = com.maddox.il2.ai.ground.Aim.SecsToTicks(com.maddox.il2.ai.ground.Aim.Rnd(1.0F, 10F + f));
    }

    private final int S(int i)
    {
        return i;
    }

    public void forgetAll()
    {
        forgetAiming();
        hunter = null;
    }

    public void forgetAiming()
    {
        if(curState == 4 && hunter != null)
            hunter.stopFire(this);
        forgetEnemy();
    }

    private void forgetEnemy()
    {
        enemy = null;
        shotpoint_idx = -1;
        curState = S(0);
        waitforparkingTimer = com.maddox.il2.ai.ground.Aim.SecsToTicks(com.maddox.il2.ai.ground.Aim.Rnd(17F, 25F));
        timeTot = 0L;
        if(passive)
            passive_resetAutoParking();
    }

    private void passive_resetAutoParking()
    {
        passive_NumTrackingStepsBeforeAutoparking = 75;
    }

    public int tick_()
    {
        switch(curState)
        {
        case 0: // '\0'
            if(--reloadingTimer < 0)
                reloadingTimer = 0;
            if(timeTot > 0L)
            {
                timeCur--;
                hunter.gunInMove(true, this);
                if(timeCur <= 0L)
                    timeTot = 0L;
                waitforparkingTimer = 0x98967f;
            } else
            if(--waitforparkingTimer <= 0)
            {
                hunter.gunStartParking(this);
                waitforparkingTimer = 0x98967f;
            }
            if(enemy != null)
            {
                java.lang.System.out.println("*** Aim: ENEMY EXISTS!!!!!!!!!");
                S(curState);
                try
                {
                    byte byte0 = 6;
                    int k3 = 0;
                    int l3 = byte0 / k3;
                    java.lang.System.out.println("****** c:" + l3);
                }
                catch(java.lang.Exception exception)
                {
                    java.lang.System.out.println("Trace:" + exception);
                    exception.printStackTrace();
                }
                enemy = null;
                shotpoint_idx = -1;
                return 0;
            }
            if(--waitenemyTimer > 0)
                return 0;
            if(passive)
            {
                waitenemyTimer = com.maddox.il2.ai.ground.Aim.SecsToTicks(9999F);
                return 0;
            }
            waitenemyTimer = com.maddox.il2.ai.ground.Aim.SecsToTicks(com.maddox.il2.ai.ground.Aim.Rnd(1.7F, 3.6F));
            shotpoint_idx = 0;
            enemy = hunter.findEnemy(this);
            if(enemy == null)
            {
                shotpoint_idx = -1;
                return 0;
            }
            waitenemyTimer /= 2;
            int i = hunter.targetGun(this, enemy, 0.22F, false);
            if(i == 0)
                forgetEnemy();
            else
            if(reloadingTimer <= 0)
            {
                if(i == 1)
                {
                    curState = S(3);
                    if(!hunter.enterToFireMode(1, enemy, com.maddox.il2.ai.ground.Aim.TicksToSecs(timeTot), this))
                        forgetEnemy();
                } else
                {
                    curState = S(2);
                    if(!hunter.enterToFireMode(0, enemy, 0.0F, this))
                        forgetEnemy();
                }
            } else
            {
                curState = S(1);
                if(!hunter.enterToFireMode(0, enemy, 0.0F, this))
                    forgetEnemy();
            }
            return 0;

        case 1: // '\001'
            if(--reloadingTimer <= 0)
            {
                int j = hunter.targetGun(this, enemy, 0.22F, false);
                if(j == 1)
                {
                    curState = S(3);
                    if(!hunter.enterToFireMode(1, enemy, com.maddox.il2.ai.ground.Aim.TicksToSecs(timeTot), this))
                        forgetEnemy();
                } else
                if(j == 2)
                {
                    int k = hunter.targetGun(this, enemy, 0.8F, false);
                    if(k == 1)
                    {
                        curState = S(3);
                        if(!hunter.enterToFireMode(1, enemy, com.maddox.il2.ai.ground.Aim.TicksToSecs(timeTot), this))
                            forgetEnemy();
                    } else
                    if(k == 2)
                        curState = S(2);
                    else
                        forgetEnemy();
                } else
                {
                    forgetEnemy();
                }
                return 0;
            }
            if(--timeCur <= 0L)
            {
                hunter.gunInMove(false, this);
                int l = hunter.targetGun(this, enemy, 0.8F, false);
                if(l == 0)
                    forgetEnemy();
            } else
            {
                hunter.gunInMove(false, this);
            }
            return 0;

        case 2: // '\002'
            if(--timeCur <= 0L)
            {
                hunter.gunInMove(false, this);
                int i1;
                if(passive)
                {
                    i1 = 2;
                    if(--passive_NumTrackingStepsBeforeAutoparking <= 0)
                    {
                        forgetEnemy();
                        return 0;
                    }
                } else
                {
                    i1 = hunter.targetGun(this, enemy, 0.22F, false);
                }
                if(i1 == 1)
                {
                    curState = S(3);
                    if(!hunter.enterToFireMode(1, enemy, com.maddox.il2.ai.ground.Aim.TicksToSecs(timeTot), this))
                        forgetEnemy();
                } else
                if(i1 == 2)
                {
                    int j1 = hunter.targetGun(this, enemy, 0.8F, false);
                    if(passive && j1 == 1)
                        j1 = 2;
                    if(j1 == 1)
                    {
                        curState = S(3);
                        if(!hunter.enterToFireMode(1, enemy, com.maddox.il2.ai.ground.Aim.TicksToSecs(timeTot), this))
                            forgetEnemy();
                    } else
                    if(j1 != 2)
                        forgetEnemy();
                } else
                {
                    forgetEnemy();
                }
            } else
            {
                hunter.gunInMove(false, this);
            }
            return 0;

        case 3: // '\003'
            if(--timeCur > 0L)
            {
                hunter.gunInMove(false, this);
                return 0;
            }
            anglesYaw.makeSrcSameAsDst();
            anglesPitch.makeSrcSameAsDst();
            hunter.gunInMove(false, this);
            fireTimer = (int)(0.5F + hunter.chainFireTime(this) / 0.75F);
            if(fireTimer > 0)
            {
                int k1 = hunter.targetGun(this, enemy, 0.75F, false);
                if(k1 == 0)
                {
                    timeTot = (int)((float)com.maddox.il2.ai.ground.Aim.SecsToTicks(0.75F) * com.maddox.il2.ai.ground.Aim.Rnd(0.8F, 1.2F));
                    timeCur = 0L;
                    fireTimer = 1;
                }
                hunter.startFire(this);
                curState = S(4);
            } else
            {
                hunter.singleShot(this);
                if(passive)
                {
                    curState = S(2);
                    int l1 = hunter.targetGun(this, enemy, 0.8F, false);
                    if(l1 == 0)
                        forgetEnemy();
                    else
                        passive_resetAutoParking();
                } else
                {
                    reloadingTimer = com.maddox.il2.ai.ground.Aim.SecsToTicks(hunter.getReloadingTime(this));
                    if(com.maddox.il2.ai.ground.Aim.RndB(hunter.probabKeepSameEnemy(enemy)))
                    {
                        curState = S(1);
                        int i2 = hunter.targetGun(this, enemy, 0.8F, false);
                        if(i2 == 0)
                            forgetEnemy();
                    } else
                    {
                        int i3 = com.maddox.il2.ai.ground.Aim.SecsToTicks(hunter.minTimeRelaxAfterFight());
                        if(waitenemyTimer < i3)
                            waitenemyTimer = (int)((float)i3 * com.maddox.il2.ai.ground.Aim.Rnd(1.0F, 1.2F));
                        forgetEnemy();
                    }
                }
            }
            return 0;

        case 4: // '\004'
            if(--timeCur > 0L)
            {
                hunter.gunInMove(false, this);
                return 0;
            }
            anglesYaw.makeSrcSameAsDst();
            anglesPitch.makeSrcSameAsDst();
            hunter.gunInMove(false, this);
            if(--fireTimer > 0)
            {
                int j2 = hunter.targetGun(this, enemy, 0.75F, false);
                if(j2 == 0)
                {
                    hunter.stopFire(this);
                    timeTot = (int)((float)com.maddox.il2.ai.ground.Aim.SecsToTicks(0.75F) * com.maddox.il2.ai.ground.Aim.Rnd(0.8F, 1.2F));
                    timeCur = 0L;
                    fireTimer = 1;
                } else
                {
                    hunter.continueFire(this);
                }
                return 0;
            }
            hunter.stopFire(this);
            if(passive)
            {
                curState = S(2);
                int k2 = hunter.targetGun(this, enemy, 0.8F, false);
                if(k2 == 0)
                    forgetEnemy();
                else
                    passive_resetAutoParking();
            } else
            {
                reloadingTimer = com.maddox.il2.ai.ground.Aim.SecsToTicks(hunter.getReloadingTime(this));
                if(com.maddox.il2.ai.ground.Aim.RndB(hunter.probabKeepSameEnemy(enemy)))
                {
                    curState = S(1);
                    int l2 = hunter.targetGun(this, enemy, 0.8F, false);
                    if(l2 == 0)
                        forgetEnemy();
                } else
                {
                    int j3 = com.maddox.il2.ai.ground.Aim.SecsToTicks(hunter.minTimeRelaxAfterFight());
                    if(waitenemyTimer < j3)
                        waitenemyTimer = (int)((float)j3 * com.maddox.il2.ai.ground.Aim.Rnd(1.0F, 1.2F));
                    forgetEnemy();
                }
            }
            return 0;
        }
        java.lang.System.out.println("Error in Aim: unexpected state");
        return 0;
    }

    public void passive_StartFiring(int i, com.maddox.il2.engine.Actor actor, int j, float f)
    {
        passive_resetAutoParking();
        int k;
        if(i == 0)
        {
            if(curState == 3 || curState == 4)
                return;
            if(actor == enemy && curState == 2)
            {
                shotpoint_idx = j;
                return;
            }
            forgetAiming();
            enemy = actor;
            shotpoint_idx = j;
            curState = S(2);
            k = hunter.targetGun(this, enemy, 0.8F, false);
        } else
        {
            i = 1;
            if(curState == 3 || curState == 4)
                return;
            forgetAiming();
            enemy = actor;
            shotpoint_idx = j;
            curState = S(3);
            k = hunter.targetGun(this, enemy, f, true);
        }
        if(k == 0)
            forgetEnemy();
        else
        if(!hunter.enterToFireMode(i, enemy, com.maddox.il2.ai.ground.Aim.TicksToSecs(timeTot), this))
            forgetEnemy();
    }

    public int setRotationForTargeting(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Orient orient, com.maddox.JGP.Point3d point3d, float f, float f1, com.maddox.JGP.Vector3d vector3d, float f2, 
            float f3, com.maddox.il2.ai.AnglesRange anglesrange, float f4, float f5, float f6, float f7, float f8)
    {
        if(f3 <= 0.0F)
            f3 = 0.0F;
        if(f2 > 0.0F)
        {
            com.maddox.JGP.Vector3d vector3d1 = new Vector3d();
            vector3d1.x = com.maddox.il2.ai.World.Rnd().nextDouble(-1D, 1.0D);
            vector3d1.y = com.maddox.il2.ai.World.Rnd().nextDouble(-1D, 1.0D);
            vector3d1.z = com.maddox.il2.ai.World.Rnd().nextDouble(-1D, 1.0D);
            if(vector3d1.length() > 0.0001D)
            {
                float f10 = com.maddox.JGP.Geom.tanDeg(f2);
                vector3d1.scale((double)f10 / vector3d1.length());
                com.maddox.JGP.Vector3d vector3d2 = new Vector3d();
                vector3d2.set(vector3d);
                vector3d2.add(vector3d1);
                if(vector3d2.length() > 0.01D)
                {
                    vector3d2.normalize();
                    vector3d.set(vector3d2);
                }
            }
        }
        java.lang.Object obj = new Vector3d();
        orient.transformInv(vector3d, ((com.maddox.JGP.Tuple3d) (obj)));
        com.maddox.il2.engine.Orient orient1 = new Orient();
        orient1.setAT0(((com.maddox.JGP.Vector3d) (obj)));
        float f9 = anglesrange.transformIntoRangeSpace(orient1.getYaw());
        float f11 = com.maddox.il2.ai.AnglesFork.signedAngleDeg(orient1.getPitch());
        if(f5 >= 90F)
        {
            f5 = 90F;
            if(!anglesrange.fullcircle())
            {
                java.lang.System.out.println("*** Aim: zenith without full circle!");
                return 0;
            }
            if(f7 <= 0.001F || f6 <= 0.001F)
            {
                java.lang.System.out.println("*** Aim: zenith speed(s)!");
                return 0;
            }
            if(f8 > 0.0F)
            {
                java.lang.System.out.println("*** Aim: zenith with body rotation!");
                return 0;
            }
            if(f11 < f4)
            {
                f11 = f4;
                obj = new Orient();
                ((com.maddox.il2.engine.Orient) (obj)).setYPR(f9, f11, 0.0F);
                vector3d.set(1.0D, 0.0D, 0.0D);
                ((com.maddox.il2.engine.Orient) (obj)).transform(vector3d);
                orient.transform(vector3d);
            }
        } else
        if(f11 < f4 || f11 > f5)
            return 0;
        checkBegPoint.set(point3d);
        checkEndPoint.set(vector3d);
        checkEndPoint.scale(250D);
        checkEndPoint.add(checkBegPoint);
        obj = com.maddox.il2.engine.Engine.collideEnv().getLine(checkBegPoint, checkEndPoint, false, actor, null);
        if(obj != null && (((com.maddox.il2.engine.Actor) (obj)).getArmy() == 0 || ((com.maddox.il2.engine.Actor) (obj)).getArmy() == actor.getArmy() || !((com.maddox.il2.engine.Actor) (obj)).isAlive()))
            return 0;
        if(anglesrange.fullcircle())
            bodyRotation = false;
        else
        if(anglesrange.isInside(f9))
        {
            bodyRotation = false;
        } else
        {
            if(f8 <= 0.0F)
                return 0;
            bodyRotation = true;
            f6 = f8;
        }
        anglesYaw.setDeg(anglesrange.fullcircle(), f, f9);
        anglesPitch.setDeg(f1, f11);
        boolean flag = false;
        float f12 = anglesYaw.getAbsDiffDeg();
        float f14 = anglesPitch.getAbsDiffDeg();
        if(f5 >= 90F)
        {
            float f16 = f12 / f6;
            float f17 = f14 / f7;
            anglesYaw.setDeg(f, f9 + 180F);
            anglesPitch.setDeg(f1, 180F - f11);
            float f18 = anglesYaw.getAbsDiffDeg();
            float f19 = anglesPitch.getAbsDiffDeg();
            float f20 = f18 / f6;
            float f21 = f19 / f7;
            if(java.lang.Math.max(f16, f17) < java.lang.Math.max(f20, f21))
            {
                anglesYaw.setDeg(f, f9);
                anglesPitch.setDeg(f1, f11);
            } else
            {
                f12 = f18;
                f14 = f19;
            }
        }
        if(f12 > 0.2F && (f3 < 0.0001F || f12 / f3 > f6))
        {
            float f13 = f6 * f3;
            if(anglesYaw.getDiffDeg() <= 0.0F)
                f13 = -f13;
            anglesYaw.setDeg(f, f + f13);
            flag = true;
        }
        if(f14 > 0.2F && (f3 < 0.0001F || f14 / f3 > f7))
        {
            float f15 = f7 * f3;
            if(anglesPitch.getDiffDeg() <= 0.0F)
                f15 = -f15;
            anglesPitch.setDeg(f1, f1 + f15);
            flag = true;
        }
        if(bodyRotation)
            anglesYaw.setDeg(0.0F, anglesYaw.getDiffDeg());
        timeTot = timeCur = com.maddox.il2.ai.ground.Aim.SecsToTicks(f3);
        return flag ? 2 : 1;
    }

    public boolean setRotationForParking(float f, float f1, float f2, float f3, com.maddox.il2.ai.AnglesRange anglesrange, float f4, float f5)
    {
        bodyRotation = false;
        anglesYaw.setDeg(anglesrange.fullcircle(), f, f2);
        anglesPitch.setDeg(f1, f3);
        float f6 = anglesYaw.getAbsDiffDeg() / f4;
        float f7 = anglesPitch.getAbsDiffDeg() / f5;
        timeTot = timeCur = com.maddox.il2.ai.ground.Aim.SecsToTicks(java.lang.Math.max(f6, f7));
        return timeTot > 1L;
    }

    public static final int RES_FAILED = 0;
    public static final int RES_OK = 1;
    public static final int RES_NOT_ENOUGH_TIME = 2;
    private static final float TIME_FINDENEMY_MIN = 1.7F;
    private static final float TIME_FINDENEMY_MAX = 3.6F;
    private static final float TIME_PARKING_MIN = 17F;
    private static final float TIME_PARKING_MAX = 25F;
    private static final float TIME_TRACKING_STEP = 0.8F;
    private static final int PASSIVE_NUMTRACKINGSTEPSBEFOREAUTOPARKING = 75;
    private static final float TIME_TOSTARTFIRE_STEP = 0.22F;
    private static final float TIME_CHAINFIRE_STEP = 0.75F;
    public static final float DISTANCE_WAKEUP = 2000F;
    public static final float AngleErrorKoefForSkill[] = {
        2.3F, 1.5F, 1.0F, 0.5F
    };
    public com.maddox.il2.ai.AnglesForkExtended anglesYaw;
    public com.maddox.il2.ai.AnglesFork anglesPitch;
    public long timeTot;
    public long timeCur;
    public boolean bodyRotation;
    private boolean passive;
    private int curState;
    private static final int ST_ENEMYSEARCH = 0;
    private static final int ST_LOADINGTRACKING = 1;
    private static final int ST_TRACKING = 2;
    private static final int ST_TOFIREPOSITION = 3;
    private static final int ST_FIRING = 4;
    private com.maddox.il2.ai.ground.HunterInterface hunter;
    private com.maddox.il2.engine.Actor enemy;
    public int shotpoint_idx;
    private int waitenemyTimer;
    private int waitforparkingTimer;
    private int passive_NumTrackingStepsBeforeAutoparking;
    private int reloadingTimer;
    private int fireTimer;
    private static com.maddox.JGP.Point3d checkBegPoint = new Point3d();
    private static com.maddox.JGP.Point3d checkEndPoint = new Point3d();
    private static int globalid = 0;
    private int id;

}
