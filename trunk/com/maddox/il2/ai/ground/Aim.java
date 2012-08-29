package com.maddox.il2.ai.ground;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
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

public class Aim
{
  public static final int RES_FAILED = 0;
  public static final int RES_OK = 1;
  public static final int RES_NOT_ENOUGH_TIME = 2;
  private static final float TIME_FINDENEMY_MIN = 1.7F;
  private static final float TIME_FINDENEMY_MAX = 3.6F;
  private static final float TIME_PARKING_MIN = 17.0F;
  private static final float TIME_PARKING_MAX = 25.0F;
  private static final float TIME_TRACKING_STEP = 0.8F;
  private static final int PASSIVE_NUMTRACKINGSTEPSBEFOREAUTOPARKING = 75;
  private static final float TIME_TOSTARTFIRE_STEP = 0.22F;
  private static final float TIME_CHAINFIRE_STEP = 0.75F;
  public static final float DISTANCE_WAKEUP = 2000.0F;
  public static final float[] AngleErrorKoefForSkill = { 2.3F, 1.5F, 1.0F, 0.5F };
  public AnglesForkExtended anglesYaw;
  public AnglesFork anglesPitch;
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
  private HunterInterface hunter;
  private Actor enemy;
  public int shotpoint_idx;
  private int waitenemyTimer;
  private int waitforparkingTimer;
  private int passive_NumTrackingStepsBeforeAutoparking;
  private int reloadingTimer;
  private int fireTimer;
  private static Point3d checkBegPoint = new Point3d();
  private static Point3d checkEndPoint = new Point3d();

  private static int globalid = 0;
  private int id;

  private static final int SecsToTicks(float paramFloat)
  {
    int i = (int)(0.5F + paramFloat / Time.tickLenFs());
    return i < 1 ? 1 : i;
  }

  private static final float TicksToSecs(long paramLong) {
    return (float)paramLong * Time.tickLenFs();
  }

  private static final float Rnd(float paramFloat1, float paramFloat2) {
    return World.Rnd().nextFloat(paramFloat1, paramFloat2);
  }
  private static final boolean RndB(float paramFloat) {
    return World.Rnd().nextFloat(0.0F, 1.0F) < paramFloat;
  }

  public Actor getEnemy()
  {
    return this.enemy;
  }

  public float t()
  {
    return 1.0F - (float)this.timeCur / (float)this.timeTot;
  }

  public boolean isInFiringMode() {
    return (this.curState == 3) || (this.curState == 4);
  }
  public boolean isInAimingMode() {
    return this.curState != 0;
  }

  public Aim(HunterInterface paramHunterInterface, boolean paramBoolean)
  {
    this.passive = paramBoolean;

    this.bodyRotation = false;

    this.hunter = paramHunterInterface;

    this.id = (globalid++);
    this.enemy = null;
    this.shotpoint_idx = -1;

    this.anglesYaw = new AnglesForkExtended(true, 0.0F, 0.0F);
    this.anglesPitch = new AnglesFork(0.0F);

    this.curState = S(0);
    this.waitforparkingTimer = 9999999;
    this.timeTot = 0L;

    passive_resetAutoParking();

    if (this.passive) {
      this.waitenemyTimer = SecsToTicks(9999.0F);
      this.reloadingTimer = 0;
    } else {
      this.waitenemyTimer = SecsToTicks(Rnd(1.7F, 3.6F));
      this.reloadingTimer = SecsToTicks(Rnd(1.0F, 10.0F));
    }
  }

  public Aim(HunterInterface paramHunterInterface, boolean paramBoolean, float paramFloat) {
    this(paramHunterInterface, paramBoolean);
    if (!this.passive)
      this.reloadingTimer = SecsToTicks(Rnd(1.0F, 10.0F + paramFloat));
  }

  private final int S(int paramInt)
  {
    return paramInt;
  }

  public void forgetAll()
  {
    forgetAiming();
    this.hunter = null;
  }

  public void forgetAiming()
  {
    if ((this.curState == 4) && (this.hunter != null)) {
      this.hunter.stopFire(this);
    }
    forgetEnemy();
  }

  private void forgetEnemy()
  {
    this.enemy = null;
    this.shotpoint_idx = -1;
    this.curState = S(0);
    this.waitforparkingTimer = SecsToTicks(Rnd(17.0F, 25.0F));
    this.timeTot = 0L;
    if (this.passive)
      passive_resetAutoParking();
  }

  private void passive_resetAutoParking()
  {
    this.passive_NumTrackingStepsBeforeAutoparking = 75;
  }

  public int tick_()
  {
    int i;
    int k;
    switch (this.curState)
    {
    case 0:
      if (--this.reloadingTimer < 0) {
        this.reloadingTimer = 0;
      }

      if (this.timeTot > 0L) {
        this.timeCur -= 1L;
        this.hunter.gunInMove(true, this);
        if (this.timeCur <= 0L) {
          this.timeTot = 0L;
        }
        this.waitforparkingTimer = 9999999;
      }
      else if (--this.waitforparkingTimer <= 0) {
        this.hunter.gunStartParking(this);
        this.waitforparkingTimer = 9999999;
      }

      if (this.enemy != null) {
        System.out.println("*** Aim: ENEMY EXISTS!!!!!!!!!");
        S(this.curState);
        try {
          int j = 6; int m = 0; int n = j / m;
          System.out.println("****** c:" + n);
        } catch (Exception localException) {
          System.out.println("Trace:" + localException);
          localException.printStackTrace();
        }
        this.enemy = null;
        this.shotpoint_idx = -1;
        return 0;
      }

      if (--this.waitenemyTimer > 0) {
        return 0;
      }

      if (this.passive) {
        this.waitenemyTimer = SecsToTicks(9999.0F);
        return 0;
      }
      this.waitenemyTimer = SecsToTicks(Rnd(1.7F, 3.6F));

      this.shotpoint_idx = 0;
      this.enemy = this.hunter.findEnemy(this);

      if (this.enemy == null) {
        this.shotpoint_idx = -1;
        return 0;
      }

      this.waitenemyTimer /= 2;

      i = this.hunter.targetGun(this, this.enemy, 0.22F, false);
      if (i == 0) {
        forgetEnemy();
      } else if (this.reloadingTimer <= 0) {
        if (i == 1) {
          this.curState = S(3);
          if (!this.hunter.enterToFireMode(1, this.enemy, TicksToSecs(this.timeTot), this))
            forgetEnemy();
        }
        else {
          this.curState = S(2);
          if (!this.hunter.enterToFireMode(0, this.enemy, 0.0F, this))
            forgetEnemy();
        }
      }
      else {
        this.curState = S(1);
        if (!this.hunter.enterToFireMode(0, this.enemy, 0.0F, this)) {
          forgetEnemy();
        }
      }
      return 0;
    case 1:
      if (--this.reloadingTimer <= 0)
      {
        i = this.hunter.targetGun(this, this.enemy, 0.22F, false);

        if (i == 1) {
          this.curState = S(3);
          if (!this.hunter.enterToFireMode(1, this.enemy, TicksToSecs(this.timeTot), this))
            forgetEnemy();
        }
        else if (i == 2) {
          i = this.hunter.targetGun(this, this.enemy, 0.8F, false);
          if (i == 1) {
            this.curState = S(3);
            if (!this.hunter.enterToFireMode(1, this.enemy, TicksToSecs(this.timeTot), this))
              forgetEnemy();
          }
          else if (i == 2) {
            this.curState = S(2);
          } else {
            forgetEnemy();
          }
        } else {
          forgetEnemy();
        }
        return 0;
      }

      if (--this.timeCur <= 0L) {
        this.hunter.gunInMove(false, this);

        i = this.hunter.targetGun(this, this.enemy, 0.8F, false);
        if (i == 0)
          forgetEnemy();
      }
      else {
        this.hunter.gunInMove(false, this);
      }
      return 0;
    case 2:
      if (--this.timeCur <= 0L) {
        this.hunter.gunInMove(false, this);

        if (this.passive)
        {
          i = 2;

          if (--this.passive_NumTrackingStepsBeforeAutoparking <= 0) {
            forgetEnemy();
            return 0;
          }
        } else {
          i = this.hunter.targetGun(this, this.enemy, 0.22F, false);
        }

        if (i == 1)
        {
          this.curState = S(3);
          if (!this.hunter.enterToFireMode(1, this.enemy, TicksToSecs(this.timeTot), this))
            forgetEnemy();
        }
        else if (i == 2)
        {
          i = this.hunter.targetGun(this, this.enemy, 0.8F, false);
          if ((this.passive) && (i == 1))
          {
            i = 2;
          }
          if (i == 1)
          {
            this.curState = S(3);
            if (!this.hunter.enterToFireMode(1, this.enemy, TicksToSecs(this.timeTot), this))
              forgetEnemy();
          }
          else if (i != 2)
          {
            forgetEnemy();
          }
        }
        else {
          forgetEnemy();
        }
      } else {
        this.hunter.gunInMove(false, this);
      }
      return 0;
    case 3:
      if (--this.timeCur > 0L) {
        this.hunter.gunInMove(false, this);
        return 0;
      }

      this.anglesYaw.makeSrcSameAsDst();
      this.anglesPitch.makeSrcSameAsDst();

      this.hunter.gunInMove(false, this);

      this.fireTimer = (int)(0.5F + this.hunter.chainFireTime(this) / 0.75F);

      if (this.fireTimer > 0) {
        i = this.hunter.targetGun(this, this.enemy, 0.75F, false);
        if (i == 0)
        {
          this.timeTot = (int)(SecsToTicks(0.75F) * Rnd(0.8F, 1.2F));
          this.timeCur = 0L;
          this.fireTimer = 1;
        }
        this.hunter.startFire(this);
        this.curState = S(4);
      } else {
        this.hunter.singleShot(this);

        if (this.passive) {
          this.curState = S(2);
          i = this.hunter.targetGun(this, this.enemy, 0.8F, false);
          if (i == 0)
            forgetEnemy();
          else
            passive_resetAutoParking();
        }
        else
        {
          this.reloadingTimer = SecsToTicks(this.hunter.getReloadingTime(this));

          if (RndB(this.hunter.probabKeepSameEnemy(this.enemy)))
          {
            this.curState = S(1);
            i = this.hunter.targetGun(this, this.enemy, 0.8F, false);
            if (i == 0)
              forgetEnemy();
          }
          else {
            k = SecsToTicks(this.hunter.minTimeRelaxAfterFight());
            if (this.waitenemyTimer < k) {
              this.waitenemyTimer = (int)(k * Rnd(1.0F, 1.2F));
            }
            forgetEnemy();
          }
        }
      }
      return 0;
    case 4:
      if (--this.timeCur > 0L) {
        this.hunter.gunInMove(false, this);
        return 0;
      }

      this.anglesYaw.makeSrcSameAsDst();
      this.anglesPitch.makeSrcSameAsDst();

      this.hunter.gunInMove(false, this);

      if (--this.fireTimer > 0) {
        i = this.hunter.targetGun(this, this.enemy, 0.75F, false);
        if (i == 0)
        {
          this.hunter.stopFire(this);
          this.timeTot = (int)(SecsToTicks(0.75F) * Rnd(0.8F, 1.2F));
          this.timeCur = 0L;
          this.fireTimer = 1;
        } else {
          this.hunter.continueFire(this);
        }
        return 0;
      }

      this.hunter.stopFire(this);

      if (this.passive) {
        this.curState = S(2);
        i = this.hunter.targetGun(this, this.enemy, 0.8F, false);
        if (i == 0)
          forgetEnemy();
        else
          passive_resetAutoParking();
      }
      else
      {
        this.reloadingTimer = SecsToTicks(this.hunter.getReloadingTime(this));

        if (RndB(this.hunter.probabKeepSameEnemy(this.enemy)))
        {
          this.curState = S(1);
          i = this.hunter.targetGun(this, this.enemy, 0.8F, false);
          if (i == 0)
            forgetEnemy();
        }
        else {
          k = SecsToTicks(this.hunter.minTimeRelaxAfterFight());
          if (this.waitenemyTimer < k) {
            this.waitenemyTimer = (int)(k * Rnd(1.0F, 1.2F));
          }
          forgetEnemy();
        }
      }
      return 0;
    }

    System.out.println("Error in Aim: unexpected state");
    return 0;
  }

  public void passive_StartFiring(int paramInt1, Actor paramActor, int paramInt2, float paramFloat)
  {
    passive_resetAutoParking();
    int i;
    if (paramInt1 == 0) {
      if ((this.curState == 3) || (this.curState == 4))
      {
        return;
      }

      if ((paramActor == this.enemy) && (this.curState == 2))
      {
        this.shotpoint_idx = paramInt2;
        return;
      }

      forgetAiming();
      this.enemy = paramActor;
      this.shotpoint_idx = paramInt2;

      this.curState = S(2);
      i = this.hunter.targetGun(this, this.enemy, 0.8F, false);
    } else {
      paramInt1 = 1;

      if ((this.curState == 3) || (this.curState == 4))
      {
        return;
      }

      forgetAiming();
      this.enemy = paramActor;
      this.shotpoint_idx = paramInt2;

      this.curState = S(3);
      i = this.hunter.targetGun(this, this.enemy, paramFloat, true);
    }

    if (i == 0) {
      forgetEnemy();
    }
    else if (!this.hunter.enterToFireMode(paramInt1, this.enemy, TicksToSecs(this.timeTot), this))
      forgetEnemy();
  }

  public int setRotationForTargeting(Actor paramActor, Orient paramOrient, Point3d paramPoint3d, float paramFloat1, float paramFloat2, Vector3d paramVector3d, float paramFloat3, float paramFloat4, AnglesRange paramAnglesRange, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    if (paramFloat4 <= 0.0F) paramFloat4 = 0.0F;

    if (paramFloat3 > 0.0F) {
      Vector3d localVector3d = new Vector3d();
      localVector3d.x = World.Rnd().nextDouble(-1.0D, 1.0D);
      localVector3d.y = World.Rnd().nextDouble(-1.0D, 1.0D);
      localVector3d.z = World.Rnd().nextDouble(-1.0D, 1.0D);
      if (localVector3d.length() > 0.0001D) {
        f2 = Geom.tanDeg(paramFloat3);
        localVector3d.scale(f2 / localVector3d.length());
        localObject = new Vector3d();
        ((Vector3d)localObject).set(paramVector3d);
        ((Vector3d)localObject).add(localVector3d);
        if (((Vector3d)localObject).length() > 0.01D) {
          ((Vector3d)localObject).normalize();
          paramVector3d.set((Tuple3d)localObject);
        }

      }

    }

    Object localObject = new Vector3d();
    paramOrient.transformInv(paramVector3d, (Tuple3d)localObject);
    Orient localOrient = new Orient();
    localOrient.setAT0((Vector3d)localObject);

    float f1 = paramAnglesRange.transformIntoRangeSpace(localOrient.getYaw());
    float f2 = AnglesFork.signedAngleDeg(localOrient.getPitch());

    if (paramFloat6 >= 90.0F) {
      paramFloat6 = 90.0F;
      if (!paramAnglesRange.fullcircle()) {
        System.out.println("*** Aim: zenith without full circle!");
        return 0;
      }

      if ((paramFloat8 <= 0.001F) || (paramFloat7 <= 0.001F)) {
        System.out.println("*** Aim: zenith speed(s)!");
        return 0;
      }

      if (paramFloat9 > 0.0F) {
        System.out.println("*** Aim: zenith with body rotation!");
        return 0;
      }

      if (f2 < paramFloat5)
      {
        f2 = paramFloat5;

        localObject = new Orient();
        ((Orient)localObject).setYPR(f1, f2, 0.0F);
        paramVector3d.set(1.0D, 0.0D, 0.0D);
        ((Orient)localObject).transform(paramVector3d);

        paramOrient.transform(paramVector3d);
      }

    }
    else if ((f2 < paramFloat5) || (f2 > paramFloat6))
    {
      return 0;
    }

    checkBegPoint.set(paramPoint3d);
    checkEndPoint.set(paramVector3d);
    checkEndPoint.scale(250.0D);
    checkEndPoint.add(checkBegPoint);

    localObject = Engine.collideEnv().getLine(checkBegPoint, checkEndPoint, false, paramActor, null);

    if ((localObject != null) && ((((Actor)localObject).getArmy() == 0) || (((Actor)localObject).getArmy() == paramActor.getArmy()) || (!((Actor)localObject).isAlive())))
    {
      return 0;
    }

    if (paramAnglesRange.fullcircle())
    {
      this.bodyRotation = false;
    }
    else if (paramAnglesRange.isInside(f1))
    {
      this.bodyRotation = false;
    }
    else {
      if (paramFloat9 <= 0.0F)
      {
        return 0;
      }

      this.bodyRotation = true;
      paramFloat7 = paramFloat9;
    }

    this.anglesYaw.setDeg(paramAnglesRange.fullcircle(), paramFloat1, f1);
    this.anglesPitch.setDeg(paramFloat2, f2);

    int i = 0;

    float f3 = this.anglesYaw.getAbsDiffDeg();
    float f4 = this.anglesPitch.getAbsDiffDeg();

    if (paramFloat6 >= 90.0F)
    {
      float f5 = f3 / paramFloat7;
      float f6 = f4 / paramFloat8;

      this.anglesYaw.setDeg(paramFloat1, f1 + 180.0F);
      this.anglesPitch.setDeg(paramFloat2, 180.0F - f2);

      float f7 = this.anglesYaw.getAbsDiffDeg();
      float f8 = this.anglesPitch.getAbsDiffDeg();
      float f9 = f7 / paramFloat7;
      float f10 = f8 / paramFloat8;

      if (Math.max(f5, f6) < Math.max(f9, f10))
      {
        this.anglesYaw.setDeg(paramFloat1, f1);
        this.anglesPitch.setDeg(paramFloat2, f2);
      }
      else {
        f3 = f7;
        f4 = f8;
      }
    }

    if ((f3 > 0.2F) && (
      (paramFloat4 < 1.0E-004F) || (f3 / paramFloat4 > paramFloat7)))
    {
      f3 = paramFloat7 * paramFloat4;
      if (this.anglesYaw.getDiffDeg() <= 0.0F) {
        f3 = -f3;
      }
      this.anglesYaw.setDeg(paramFloat1, paramFloat1 + f3);
      i = 1;
    }

    if ((f4 > 0.2F) && (
      (paramFloat4 < 1.0E-004F) || (f4 / paramFloat4 > paramFloat8)))
    {
      f4 = paramFloat8 * paramFloat4;
      if (this.anglesPitch.getDiffDeg() <= 0.0F) {
        f4 = -f4;
      }
      this.anglesPitch.setDeg(paramFloat2, paramFloat2 + f4);
      i = 1;
    }

    if (this.bodyRotation) {
      this.anglesYaw.setDeg(0.0F, this.anglesYaw.getDiffDeg());
    }

    (this.timeCur = SecsToTicks(paramFloat4)).timeTot = this;
    return i != 0 ? 2 : 1;
  }

  public boolean setRotationForParking(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, AnglesRange paramAnglesRange, float paramFloat5, float paramFloat6)
  {
    this.bodyRotation = false;

    this.anglesYaw.setDeg(paramAnglesRange.fullcircle(), paramFloat1, paramFloat3);
    this.anglesPitch.setDeg(paramFloat2, paramFloat4);

    float f1 = this.anglesYaw.getAbsDiffDeg() / paramFloat5;
    float f2 = this.anglesPitch.getAbsDiffDeg() / paramFloat6;

    (this.timeCur = SecsToTicks(Math.max(f1, f2))).timeTot = this;
    return this.timeTot > 1L;
  }
}