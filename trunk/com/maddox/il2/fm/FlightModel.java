package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.ScareEnemies;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft.Mirror;
import com.maddox.il2.objects.air.TypeDiveBomber;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.air.TypeStormovik;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.rts.Time;

public class FlightModel extends FlightModelMain
{
  public Turret[] turret;
  protected HierMesh HM;
  ActorHMesh am;
  private boolean shoot;
  public boolean turnOffCollisions = false;
  public boolean brakeShoe = false;
  public Loc brakeShoeLoc = new Loc();
  public Actor brakeShoeLastCarrier = null;
  public boolean canChangeBrakeShoe = false;
  public static final int _FIRST_TURRET = 10;
  private final float tAcc1 = 0.05F;
  private final float tAcc2 = 8.0F;
  private final float tAcc3 = 2.666667F;
  private static Vector3d v2 = new Vector3d();
  private static Vector3d v3 = new Vector3d();

  private static Point3d Pt = new Point3d();
  private static Vector3d Ve = new Vector3d();
  private static Vector3d Vt = new Vector3d();

  private static Orient Oo = new Orient();
  private static float[] tu = new float[3];
  public float dryFriction = 1.0F;

  static Point3d p = new Point3d();
  static Vector3d v1 = new Vector3d();
  static Point3d p2 = new Point3d();

  public FlightModel(String paramString)
  {
    super(paramString);
  }

  public void setSkill(int paramInt) {
    if (paramInt < 0) paramInt = 0;
    if (paramInt > 3) paramInt = 3;
    this.Skill = paramInt;
    this.turretSkill = paramInt;
    World.cur(); if ((this.actor != World.getPlayerAircraft()) && (!((Aircraft)this.actor).isNetPlayer())) {
      switch (paramInt) {
      case 0:
        this.SensPitch *= 0.75F;
        this.SensRoll *= 0.5F;
        this.SensYaw *= 0.5F;

        break;
      case 1:
        this.SensRoll *= 0.7F;
        this.SensPitch *= 0.75F;
        this.SensYaw *= 0.7F;

        break;
      case 2:
        this.SensRoll *= 0.88F;
        this.SensPitch *= 0.92F;
        this.SensYaw *= 0.9F;
        break;
      case 3:
        this.SensPitch *= 1.0F;
        this.SensRoll *= 1.0F;
        this.SensYaw *= 1.0F;
      }

    }
    else
    {
      Aircraft.debugprintln(this.actor, "Skill adjustment rejected on the Player AI parameters..");
    }
  }

  public void set(HierMesh paramHierMesh)
  {
    this.HM = paramHierMesh;
    this.am = ((ActorHMesh)this.actor);
    for (int j = 1; (j <= 9) && 
      (this.HM.chunkFindCheck("Turret" + j + "A_D0") >= 0) && 
      (this.HM.chunkFindCheck("Turret" + j + "B_D0") >= 0); j++);
    j--;

    this.turret = new Turret[j];

    for (int i = 0; i < j; i++) {
      this.turret[i] = new Turret();
      this.turret[i].indexA = this.HM.chunkFind("Turret" + (i + 1) + "A_D0");
      this.turret[i].indexB = this.HM.chunkFind("Turret" + (i + 1) + "B_D0");
      float tmp234_233 = (tu[2] = 0.0F); tu[1] = tmp234_233; tu[0] = tmp234_233;
      this.HM.setCurChunk(this.turret[i].indexA); this.am.hierMesh().chunkSetAngles(tu);
      this.HM.setCurChunk(this.turret[i].indexB); this.am.hierMesh().chunkSetAngles(tu);
      this.am.getChunkLoc(this.turret[i].Lstart);
    }
    this.Gears.set(paramHierMesh);
  }

  private void updateRotation(Turret paramTurret, float paramFloat)
  {
    tu[0] = paramTurret.tuLim[0];
    tu[1] = paramTurret.tuLim[1];

    float f1 = 10.0F * paramFloat;
    float f2 = tu[0] - paramTurret.tu[0];
    if (f2 < -f1) f2 = -f1; else if (f2 > f1) f2 = f1;
    tu[0] = (paramTurret.tu[0] + f2); paramTurret.tu[0] = tu[0];
    float f3 = tu[1] - paramTurret.tu[1];
    if (f3 < -f1) f3 = -f1; else if (f3 > f1) f3 = f1;
    tu[1] = (paramTurret.tu[1] + f3); paramTurret.tu[1] = tu[1];
    if ((f2 == 0.0F) && (f3 == 0.0F)) return;

    float f4 = tu[0]; tu[0] = 0.0F;
    this.HM.setCurChunk(paramTurret.indexB);
    this.am.hierMesh().chunkSetAngles(tu);
    tu[1] = f4;
    this.HM.setCurChunk(paramTurret.indexA);
    this.am.hierMesh().chunkSetAngles(tu);
  }

  private boolean isNightTargetVisible(Actor paramActor, float paramFloat)
  {
    float f1 = World.Sun().sunMultiplier;
    int i = Mission.curCloudsType();
    float f2 = Mission.curCloudsHeight();
    float f3 = 500.0F;

    if (this.am.pos.getAbs().getZ() < paramActor.pos.getAbs().getZ())
    {
      if (paramActor.pos.getAbs().getZ() < f2 + f3)
      {
        if (i > 2)
          f1 *= 1.2F;
        else if (i > 3)
          f1 *= 1.3F;
      }
      f1 *= 1.1F;
    }
    else if (paramActor.pos.getAbs().getZ() > f2 + f3)
    {
      if (i > 2)
        f1 *= 1.2F;
      else if (i > 3) {
        f1 *= 1.3F;
      }
    }
    v3.sub(this.am.pos.getAbsPoint(), paramActor.pos.getAbsPoint());

    float f4 = cvt(paramFloat, 0.0F, 3.0F, 0.75F, 1.2F);
    float f5 = cvt((float)v3.length(), 0.0F, 800.0F, 1.0F, 0.1F);
    float f6 = cvt(f1, 0.095F, 1.0F, 1.0E-005F, 0.05F) * f5 * f4;
    float f7 = World.Rnd().nextFloat();

    return f7 < f6;
  }

  private boolean isComingFromTheSun(Actor paramActor)
  {
    if ((paramActor instanceof Aircraft))
    {
      if (World.Sun().ToSun.z > 0.0F)
      {
        if ((Mission.curCloudsType() > 3) && (paramActor.pos.getAbs().getZ() < Mission.curCloudsHeight() + 200.0F))
          return false;
        v3.set(World.Sun().ToSun.x, World.Sun().ToSun.y, World.Sun().ToSun.z);
        v2.sub(this.am.pos.getAbsPoint(), paramActor.pos.getAbsPoint());
        float f1 = (float)v2.length();
        v2.normalize();
        double d = v3.angle(v2);
        float f2 = cvt(f1, 100.0F, 3000.0F, 2.9F, 3.0F);
        if (d > f2)
          return true;
      }
    }
    return false;
  }

  private boolean isTargetExposed(Actor paramActor)
  {
    if ((paramActor instanceof Aircraft))
    {
      Aircraft localAircraft = (Aircraft)paramActor;

      if ((localAircraft.FM.AS.bLandingLightOn) || (localAircraft.FM.AS.bNavLightsOn) || (localAircraft.FM.CT.WeaponControl[0] != 0) || (localAircraft.FM.CT.WeaponControl[1] != 0))
      {
        return true;
      }
      if (World.Sun().ToMoon.z > 0.0F)
      {
        if ((Mission.curCloudsType() > 3) && (paramActor.pos.getAbs().getZ() < Mission.curCloudsHeight() + 200.0F))
          return false;
        v3.set(World.Sun().ToMoon.x, World.Sun().ToMoon.y, World.Sun().ToMoon.z);
        v2.sub(this.am.pos.getAbsPoint(), paramActor.pos.getAbsPoint());
        float f1 = (float)v2.length();

        v2.normalize();
        double d = v3.angle(v2);

        float f2 = cvt(f1, 100.0F, 3000.0F, 2.9F, 3.0F);
        if (d > f2)
          return true;
      }
      return false;
    }
    return false;
  }

  private void updateTurret(Turret paramTurret, int paramInt, float paramFloat)
  {
    if (!paramTurret.bIsOperable) {
      this.CT.WeaponControl[(paramInt + 10)] = false;
      return;
    }
    if (!paramTurret.bIsAIControlled)
    {
      float tmp43_42 = (tu[2] = 0.0F); tu[1] = tmp43_42; tu[0] = tmp43_42;
      tu[1] = paramTurret.tu[0];

      this.HM.setCurChunk(paramTurret.indexA);
      this.am.hierMesh().chunkSetAngles(tu);
      tu[1] = paramTurret.tu[1];

      this.HM.setCurChunk(paramTurret.indexB);
      this.am.hierMesh().chunkSetAngles(tu);
      return;
    }

    if ((paramTurret.indexA == -1) || (paramTurret.indexB == -1)) {
      return;
    }
    this.am = ((ActorHMesh)this.actor);
    float f1 = 0.0F;
    float f3 = this.turretSkill * paramTurret.health;
    if (this.W.lengthSquared() > 0.25D) {
      f3 *= (1.0F - (float)Math.sqrt(this.W.length() - 0.5D));
    }
    if (getOverload() > 0.5F)
      f3 *= Aircraft.cvt(getOverload(), 0.0F, 5.0F, 1.0F, 0.0F);
    else if (getOverload() < -0.25F) {
      f3 *= Aircraft.cvt(getOverload(), -1.0F, 0.0F, 0.0F, 1.0F);
    }

    if ((paramTurret.target != null) && ((paramTurret.target instanceof Aircraft)) && (((Aircraft)paramTurret.target).FM.isTakenMortalDamage())) {
      paramTurret.target = null;
    }
    if (paramTurret.target == null)
    {
      if (paramTurret.tMode != 0) {
        paramTurret.tMode = 0;
        paramTurret.timeNext = Time.current();
      }
    }
    else {
      paramTurret.target.pos.getAbs(Pt);

      paramTurret.target.getSpeed(Vt);
      this.actor.getSpeed(Ve);
      Vt.sub(Ve);

      this.HM.setCurChunk(paramTurret.indexA);
      this.am.getChunkLocAbs(Actor._tmpLoc);
      Ve.sub(Pt, Actor._tmpLoc.getPoint());
      f1 = (float)Ve.length();

      float f2 = (14.0F - 3.0F * f3) * (float)Math.sin((float)(Time.current() & 0xFFFF) * 0.003F);
      Vt.scale((f1 + f2) * 0.00149254F);
      Ve.add(Vt);

      this.Or.transformInv(Ve);
      paramTurret.Lstart.transformInv(Ve);

      Ve.y = (-Ve.y);
      this.HM.setCurChunk(paramTurret.indexB);

      paramTurret.Lstart.get(Oo);
      Oo.setAT0(Ve);
      Oo.get(tu);

      this.Or.transformInv(Vt);
      paramTurret.Lstart.transformInv(Vt);
      Vt.normalize();

      this.shoot = ((Aircraft)this.actor).turretAngles(paramInt, tu);
    }

    switch (paramTurret.tMode)
    {
    case 0:
      if (World.Sun().ToSun.z < -0.22F) {
        paramTurret.bIsShooting = false;
        float tmp659_658 = 0.0F; paramTurret.tuLim[1] = tmp659_658; paramTurret.tuLim[0] = tmp659_658;
        if (Time.current() <= paramTurret.timeNext) break;
        paramTurret.target = War.GetNearestEnemyAircraft(this.actor, 1000.0F, 9);
        if (paramTurret.target == null)
        {
          paramTurret.target = War.GetNearestEnemyAircraft(this.actor, 3000.0F, 9);

          if (paramTurret.target == null) {
            paramTurret.timeNext = (Time.current() + World.Rnd().nextLong(3200L, 10000L));
          } else if (isTargetExposed(paramTurret.target))
          {
            paramTurret.tMode = 1;
            paramTurret.timeNext = 0L;
          }
          else {
            paramTurret.timeNext = (Time.current() + World.Rnd().nextLong(1500L, 4000L));
          }
        }
        else {
          paramTurret.timeNext = (Time.current() + World.Rnd().nextLong(100L, 500L));
          if ((!isNightTargetVisible(paramTurret.target, f3)) && (!isTargetExposed(paramTurret.target)))
          {
            break;
          }
          paramTurret.tMode = 1;
          paramTurret.timeNext = 0L;
        }

      }
      else
      {
        paramTurret.bIsShooting = false;
        float tmp860_859 = 0.0F; paramTurret.tuLim[1] = tmp860_859; paramTurret.tuLim[0] = tmp860_859;

        if (Time.current() <= paramTurret.timeNext)
          break;
        paramTurret.target = War.GetNearestEnemyAircraft(this.actor, 3619.0F, 9);
        if (paramTurret.target == null) {
          paramTurret.target = War.GetNearestEnemyAircraft(this.actor, 6822.0F, 9);
          if (paramTurret.target == null)
          {
            paramTurret.timeNext = (Time.current() + World.Rnd().nextLong(1000L, 10000L));
          }
          else
            paramTurret.timeNext = (Time.current() + World.Rnd().nextLong(100L, 3000L));
        }
        else
        {
          paramTurret.tMode = 1;
          paramTurret.timeNext = 0L; } 
      }break;
    case 1:
      paramTurret.bIsShooting = false;
      paramTurret.tuLim[0] = tu[0];
      paramTurret.tuLim[1] = tu[1];

      if (!isTick(39, 16)) break;
      if (((!this.shoot) && (f1 > 550.0F)) || (World.Rnd().nextFloat() < 0.1F)) {
        paramTurret.tMode = 0;
        paramTurret.timeNext = Time.current();
      }
      if (((World.Rnd().nextInt() & 0xFF) >= 32.0F * (f3 + 1.0F)) && (f1 >= 148.0F + 27.0F * f3))
        break;
      if (f1 < 450.0F + 66.599998F * f3) {
        if (f3 <= 0.0F) {
          if (Vt.x < -0.9599999785423279D) {
            switch (World.Rnd().nextInt(1, 3)) {
            case 1:
              paramTurret.tMode = 5;
              paramTurret.timeNext = (Time.current() + World.Rnd().nextLong(500L, 1200L));

              break;
            case 2:
              paramTurret.tuLim[0] += World.Rnd().nextFloat(-15.0F, 15.0F);
              paramTurret.tuLim[1] += World.Rnd().nextFloat(-10.0F, 10.0F);
            case 3:
              paramTurret.tMode = 3;
              paramTurret.timeNext = (Time.current() + World.Rnd().nextLong(500L, 10000L));
            }
          }
          else {
            if (Vt.x >= -0.3300000131130219D)
              break;
            paramTurret.tMode = 3;
            paramTurret.timeNext = (Time.current() + World.Rnd().nextLong(1000L, 5000L));
          }

        }
        else if (f3 <= 2.0F) {
          if (Vt.x < -0.910000026226044D) {
            if (World.Rnd().nextBoolean())
              paramTurret.tMode = 3;
            else {
              paramTurret.tMode = 2;
            }

            paramTurret.timeNext = (Time.current() + World.Rnd().nextLong(500L, 2200L));
          }
          else {
            if (World.Rnd().nextFloat() < 0.5F)
              paramTurret.tMode = 2;
            else {
              paramTurret.tMode = 3;
            }

            paramTurret.timeNext = (Time.current() + World.Rnd().nextLong(1500L, 7500L));
          }

        }
        else
        {
          paramTurret.tMode = 2;

          paramTurret.timeNext = (Time.current() + World.Rnd().nextLong(500L, 7500L));
        }

      }
      else
      {
        if (f1 >= 902.0F + 88.0F * f3) break;
        paramTurret.tMode = 3;
        paramTurret.timeNext = (Time.current() + World.Rnd().nextLong(100L, 1000L)); } break;
    case 5:
      paramTurret.bIsShooting = false;
      if (Time.current() <= paramTurret.timeNext) break;
      paramTurret.tMode = 0;
      paramTurret.timeNext = 0L; break;
    case 3:
      paramTurret.bIsShooting = true;

      paramTurret.tuLim[0] = (tu[0] * World.Rnd().nextFloat(0.85F + 0.05F * f3, 1.15F - 0.05F * f3) + World.Rnd().nextFloat(-8.0F + 2.666667F * f3, 8.0F - 2.666667F * f3));

      paramTurret.tuLim[1] = (tu[1] * World.Rnd().nextFloat(0.85F + 0.05F * f3, 1.15F - 0.05F * f3) + World.Rnd().nextFloat(-8.0F + 2.666667F * f3, 8.0F - 2.666667F * f3));

      if (Time.current() <= paramTurret.timeNext) break;
      paramTurret.tMode = 1; break;
    case 2:
      paramTurret.bIsShooting = true;
      paramTurret.tuLim[0] = (tu[0] * World.Rnd().nextFloat(0.85F + 0.05F * f3, 1.15F - 0.05F * f3) + World.Rnd().nextFloat(-8.0F + 2.666667F * f3, 8.0F - 2.666667F * f3));

      paramTurret.tuLim[1] = (tu[1] * World.Rnd().nextFloat(0.85F + 0.05F * f3, 1.15F - 0.05F * f3) + World.Rnd().nextFloat(-8.0F + 2.666667F * f3, 8.0F - 2.666667F * f3));

      if (Time.current() <= paramTurret.timeNext) break;
      paramTurret.tMode = 1;
      if (f3 > 1.0F) break;
      paramTurret.tMode = 0;
      paramTurret.timeNext = (Time.current() + World.Rnd().nextLong(100L, ()((f3 + 1.0F) * 700.0F))); break;
    case 4:
      paramTurret.bIsShooting = true;
      this.shoot = true;
      ((Aircraft)this.actor).turretAngles(paramInt, paramTurret.tuLim);

      if (isTick(20, 0)) {
        paramTurret.tuLim[0] += World.Rnd().nextFloat(-50.0F, 50.0F);
        paramTurret.tuLim[1] += World.Rnd().nextFloat(-50.0F, 50.0F);
      }
      if (Time.current() <= paramTurret.timeNext) break;
      paramTurret.tMode = 5;
      paramTurret.timeNext = (Time.current() + World.Rnd().nextLong(100L, 1500L));
    }

    this.shoot &= paramTurret.bIsShooting;
    if (this.shoot)
      this.shoot = (!isComingFromTheSun(paramTurret.target));
    this.CT.WeaponControl[(paramInt + 10)] = this.shoot;
    updateRotation(paramTurret, paramFloat);
  }

  public void hit(int paramInt)
  {
    if (!Actor.isValid(this.actor)) return;
    if (this.actor.isNetMirror()) {
      return;
    }
    super.hit(paramInt);
  }

  public float getSpeed() {
    if (!Actor.isValid(this.actor)) return 0.0F;
    if (this.actor.isNetMirror()) {
      return (float)this.Vwld.length();
    }
    return super.getSpeed();
  }

  public void update(float paramFloat)
  {
    if (this.actor.isNetMirror())
      ((NetAircraft.Mirror)this.actor.net).fmUpdate(paramFloat);
    else
      FMupdate(paramFloat);
  }

  public final void FMupdate(float paramFloat)
  {
    if (this.turret != null) {
      int j = this.turret.length;
      for (int i = 0; i < j; i++) updateTurret(this.turret[i], i, paramFloat);
    }
    super.update(paramFloat);
  }

  protected void putScareShpere() {
    v1.set(1.0D, 0.0D, 0.0D); this.Or.transform(v1); v1.scale(2000.0D); p2.set(this.Loc); p2.add(v1);
    Engine.land(); if (Landscape.rayHitHQ(this.Loc, p2, p)) {
      float f = (float)(getAltitude() - Engine.land().HQ_Air(this.Loc.x, this.Loc.y));
      if (((this.actor instanceof TypeDiveBomber)) && 
        (f > 780.0F) && (this.Or.getTangage() < -70.0F)) {
        ScareEnemies.set(16);
        Engine.collideEnv().getNearestEnemies(p, 75.0D, this.actor.getArmy(), ScareEnemies.enemies());
      }

      if ((this.actor instanceof TypeStormovik)) {
        if ((f < 600.0F) && (this.Or.getTangage() < -15.0F)) {
          ScareEnemies.set(2);
          Engine.collideEnv().getNearestEnemies(p, 45.0D, this.actor.getArmy(), ScareEnemies.enemies());
        }
      } else if (((this.actor instanceof TypeFighter)) && 
        (f < 500.0F) && (this.Or.getTangage() < -15.0F)) {
        ScareEnemies.set(2);
        Engine.collideEnv().getNearestEnemies(p, 45.0D, this.actor.getArmy(), ScareEnemies.enemies());
      }
    }
  }

  public void moveCarrier()
  {
    if (this.AP.way.isLandingOnShip()) {
      if (this.AP.way.landingAirport == null) {
        int i = this.AP.way.Cur();
        this.AP.way.last();
        if (this.AP.way.curr().Action == 2) {
          Actor localActor = this.AP.way.curr().getTarget();
          if ((localActor != null) && ((localActor instanceof BigshipGeneric))) {
            this.AP.way.landingAirport = ((BigshipGeneric)localActor).getAirport();
          }
        }
        this.AP.way.setCur(i);
      }
      if ((Actor.isAlive(this.AP.way.landingAirport)) && (!this.AP.way.isLanding()))
        this.AP.way.landingAirport.rebuildLastPoint(this);
    }
  }

  protected static float cvt(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    paramFloat1 = Math.min(Math.max(paramFloat1, paramFloat2), paramFloat3);
    return paramFloat4 + (paramFloat5 - paramFloat4) * (paramFloat1 - paramFloat2) / (paramFloat3 - paramFloat2);
  }
}