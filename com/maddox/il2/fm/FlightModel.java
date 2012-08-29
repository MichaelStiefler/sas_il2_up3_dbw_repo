package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
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
    this.jdField_turretSkill_of_type_Int = paramInt;
    World.cur(); if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor != World.getPlayerAircraft()) && (!((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).isNetPlayer())) {
      switch (paramInt) {
      case 0:
        this.jdField_SensPitch_of_type_Float *= 0.75F;
        this.jdField_SensRoll_of_type_Float *= 0.5F;
        this.jdField_SensYaw_of_type_Float *= 0.5F;

        break;
      case 1:
        this.jdField_SensRoll_of_type_Float *= 0.75F;

        break;
      case 2:
        break;
      case 3:
        this.jdField_SensPitch_of_type_Float *= 1.1F;
        this.jdField_SensRoll_of_type_Float *= 1.1F;
        this.jdField_SensYaw_of_type_Float *= 1.2F;

        for (int i = 0; i < this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.toughness.length; i++) this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.toughness[i] *= 1.5F;
      }
    }
    else
      Aircraft.debugprintln(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, "Skill adjustment rejected on the Player AI parameters..");
  }

  public void set(HierMesh paramHierMesh)
  {
    this.HM = paramHierMesh;
    this.am = ((ActorHMesh)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
    for (int j = 1; j <= 9; j++)
      if ((this.HM.chunkFindCheck("Turret" + j + "A_D0") < 0) || 
        (this.HM.chunkFindCheck("Turret" + j + "B_D0") < 0))
        break;
    j--;

    this.turret = new Turret[j];
    for (int i = 0; i < j; i++) {
      this.turret[i] = new Turret();
      this.turret[i].indexA = this.HM.chunkFind("Turret" + (i + 1) + "A_D0");
      this.turret[i].indexB = this.HM.chunkFind("Turret" + (i + 1) + "B_D0");
      float tmp232_231 = (tu[2] = 0.0F); tu[1] = tmp232_231; tu[0] = tmp232_231;
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

  private void updateTurret(Turret paramTurret, int paramInt, float paramFloat)
  {
    if (!paramTurret.bIsOperable) {
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[(paramInt + 10)] = false;
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

    this.am = ((ActorHMesh)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
    float f1 = 0.0F;
    float f3 = this.jdField_turretSkill_of_type_Int;
    if (this.jdField_W_of_type_ComMaddoxJGPVector3d.lengthSquared() > 0.25D) {
      f3 *= (1.0F - (float)Math.sqrt(this.jdField_W_of_type_ComMaddoxJGPVector3d.length() - 0.5D));
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
      this.jdField_actor_of_type_ComMaddoxIl2EngineActor.getSpeed(Ve);
      Vt.sub(Ve);

      this.HM.setCurChunk(paramTurret.indexA);
      this.am.getChunkLocAbs(Actor._tmpLoc);
      Ve.sub(Pt, Actor._tmpLoc.getPoint());
      f1 = (float)Ve.length();

      float f2 = 10.0F * (float)Math.sin((float)(Time.current() & 0xFFFF) * 0.003F);
      Vt.scale((f1 + f2) * 0.00149254F);
      Ve.add(Vt);

      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
      paramTurret.Lstart.transformInv(Ve);

      Ve.jdField_y_of_type_Double = (-Ve.jdField_y_of_type_Double);
      this.HM.setCurChunk(paramTurret.indexB);

      paramTurret.Lstart.get(Oo);
      Oo.setAT0(Ve);
      Oo.get(tu);

      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Vt);
      paramTurret.Lstart.transformInv(Vt);
      Vt.normalize();

      this.shoot = ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).turretAngles(paramInt, tu);
    }

    switch (paramTurret.tMode) {
    case 0:
      paramTurret.bIsShooting = false;
      float tmp616_615 = 0.0F; paramTurret.tuLim[1] = tmp616_615; paramTurret.tuLim[0] = tmp616_615;

      if (Time.current() <= paramTurret.timeNext)
        break;
      paramTurret.target = War.GetNearestEnemyAircraft(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 3619.0F, 9);
      if (paramTurret.target == null) {
        paramTurret.target = War.GetNearestEnemyAircraft(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 6822.0F, 9);
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
        paramTurret.timeNext = 0L; } break;
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
        switch (this.jdField_turretSkill_of_type_Int) {
        case 0:
          if (Vt.jdField_x_of_type_Double < -0.9599999785423279D) {
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
            if (Vt.jdField_x_of_type_Double >= -0.3300000131130219D)
              break;
            paramTurret.tMode = 3;
            paramTurret.timeNext = (Time.current() + World.Rnd().nextLong(1000L, 5000L)); } break;
        case 1:
        case 2:
          if (Vt.jdField_x_of_type_Double < -0.910000026226044D) {
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

          break;
        case 3:
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
      if (Time.current() <= paramTurret.timeNext) break;
      paramTurret.tMode = 1; break;
    case 2:
      paramTurret.bIsShooting = true;
      paramTurret.tuLim[0] = (tu[0] * World.Rnd().nextFloat(0.91F + 0.03F * f3, 1.09F - 0.03F * f3) + World.Rnd().nextFloat(-5.0F + 1.666F * f3, 5.0F - 1.666F * f3));

      paramTurret.tuLim[1] = (tu[1] * World.Rnd().nextFloat(0.91F + 0.03F * f3, 1.09F - 0.03F * f3) + World.Rnd().nextFloat(-5.0F + 1.666F * f3, 5.0F - 1.666F * f3));

      if (Time.current() <= paramTurret.timeNext) break;
      paramTurret.tMode = 1;
      if ((this.jdField_turretSkill_of_type_Int != 0) && (this.jdField_turretSkill_of_type_Int != 1)) break;
      paramTurret.tMode = 0;
      paramTurret.timeNext = (Time.current() + World.Rnd().nextLong(100L, ()((f3 + 1.0F) * 700.0F))); break;
    case 4:
      paramTurret.bIsShooting = true;
      this.shoot = true;
      ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).turretAngles(paramInt, paramTurret.tuLim);

      if (isTick(20, 0)) {
        paramTurret.tuLim[0] += World.Rnd().nextFloat(-50.0F, 50.0F);
        paramTurret.tuLim[1] += World.Rnd().nextFloat(-50.0F, 50.0F);
      }
      if (Time.current() <= paramTurret.timeNext) break;
      paramTurret.tMode = 5;
      paramTurret.timeNext = (Time.current() + World.Rnd().nextLong(100L, 1500L));
    }

    this.shoot &= paramTurret.bIsShooting;
    this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[(paramInt + 10)] = this.shoot;
    updateRotation(paramTurret, paramFloat);
  }

  public void hit(int paramInt)
  {
    if (!Actor.isValid(this.jdField_actor_of_type_ComMaddoxIl2EngineActor)) return;
    if (this.jdField_actor_of_type_ComMaddoxIl2EngineActor.isNetMirror()) {
      return;
    }
    super.hit(paramInt);
  }

  public float getSpeed() {
    if (!Actor.isValid(this.jdField_actor_of_type_ComMaddoxIl2EngineActor)) return 0.0F;
    if (this.jdField_actor_of_type_ComMaddoxIl2EngineActor.isNetMirror()) {
      return (float)this.Vwld.length();
    }
    return super.getSpeed();
  }

  public void update(float paramFloat)
  {
    if (this.jdField_actor_of_type_ComMaddoxIl2EngineActor.isNetMirror())
      ((NetAircraft.Mirror)this.jdField_actor_of_type_ComMaddoxIl2EngineActor.net).fmUpdate(paramFloat);
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
    v1.set(1.0D, 0.0D, 0.0D); this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(v1); v1.scale(2000.0D); p2.set(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d); p2.add(v1);
    Engine.land(); if (Landscape.rayHitHQ(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d, p2, p)) {
      float f = (float)(getAltitude() - Engine.land().HQ_Air(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double));
      if (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeDiveBomber)) && 
        (f > 780.0F) && (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() < -70.0F)) {
        ScareEnemies.set(16);
        Engine.collideEnv().getNearestEnemies(p, 75.0D, this.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy(), ScareEnemies.enemies());
      }

      if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeStormovik)) {
        if ((f < 600.0F) && (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() < -15.0F)) {
          ScareEnemies.set(2);
          Engine.collideEnv().getNearestEnemies(p, 45.0D, this.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy(), ScareEnemies.enemies());
        }
      } else if (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeFighter)) && 
        (f < 500.0F) && (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() < -15.0F)) {
        ScareEnemies.set(2);
        Engine.collideEnv().getNearestEnemies(p, 45.0D, this.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy(), ScareEnemies.enemies());
      }
    }
  }

  public void moveCarrier()
  {
    if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLandingOnShip()) {
      if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.landingAirport == null) {
        int i = this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur();
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.last();
        if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action == 2) {
          Actor localActor = this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getTarget();
          if ((localActor != null) && ((localActor instanceof BigshipGeneric))) {
            this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.landingAirport = ((BigshipGeneric)localActor).getAirport();
          }
        }
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.setCur(i);
      }
      if ((Actor.isAlive(this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.landingAirport)) && (!this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLanding()))
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.landingAirport.rebuildLastPoint(this);
    }
  }
}