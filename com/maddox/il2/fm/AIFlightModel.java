package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.air.TypeStormovik;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.Time;

public class AIFlightModel extends FlightModel
{
  private long w;
  public float Density;
  public float Kq;
  protected boolean callSuperUpdate = true;
  protected boolean dataDrawn = false;

  Vector3d Cw = new Vector3d();
  Vector3d Fw = new Vector3d();

  protected Vector3d Wtrue = new Vector3d();

  private Point3d TmpP = new Point3d();
  private Vector3d Vn = new Vector3d();
  private Vector3d TmpV = new Vector3d();
  private Vector3d TmpVd = new Vector3d();
  private Loc L = new Loc();

  public AIFlightModel(String paramString)
  {
    super(paramString);
    this.w = Time.current();
  }

  private void flutter()
  {
    ((Aircraft)this.actor).msgCollision(this.actor, "CF_D0", "CF_D0");
  }
  public Vector3d getW() {
    return this.Wtrue;
  }

  public void update(float paramFloat)
  {
    this.CT.update(paramFloat, (getSpeed() + 50.0F) * 0.5F, this.EI, false);
    this.Wing.setFlaps(this.CT.getFlap());
    this.EI.update(paramFloat);
    super.update(paramFloat);

    this.Gravity = (this.M.getFullMass() * Atmosphere.g());
    this.M.computeFullJ(this.J, this.J0);

    if (isTick(44, 0)) {
      this.AS.update(paramFloat * 44.0F);
      ((Aircraft)this.actor).rareAction(paramFloat * 44.0F, true);
      this.M.computeParasiteMass(this.CT.Weapons);
      this.Sq.computeParasiteDrag(this.CT, this.CT.Weapons);

      if ((((Pilot)((Aircraft)this.actor).FM).get_task() == 7) || (((Maneuver)(Maneuver)((Aircraft)this.actor).FM).get_maneuver() == 43)) {
        putScareShpere();
      }
      if (World.Rnd().nextInt(0, 99) < 25) {
        Aircraft localAircraft1 = (Aircraft)this.actor;
        if ((localAircraft1.aircIndex() == 0) && 
          (!(localAircraft1 instanceof TypeFighter))) {
          int i = this.actor.getArmy() - 1 & 0x1;
          int j = (int)(Time.current() / 60000L);
          Aircraft localAircraft2;
          if ((j > Voice.cur().SpeakBombersUnderAttack[i]) || (j > Voice.cur().SpeakBombersUnderAttack1[i]) || (j > Voice.cur().SpeakBombersUnderAttack2[i]))
          {
            localAircraft2 = War.getNearestEnemy(localAircraft1, 6000.0F);
            if ((localAircraft2 != null) && (((localAircraft2 instanceof TypeFighter)) || ((localAircraft2 instanceof TypeStormovik)))) {
              Voice.speakBombersUnderAttack(localAircraft1, false);
            }
          }
          if (j > Voice.cur().SpeakNearBombers[i]) {
            localAircraft2 = War.getNearestFriendlyFighter(localAircraft1, 4000.0F);
            if (localAircraft2 != null) Voice.speakNearBombers(localAircraft1);

          }

        }

      }

    }

    if ((World.cur().diffCur.Wind_N_Turbulence) && (!this.Gears.onGround()) && (Time.current() > this.w + 50L))
    {
      World.wind().getVectorAI(this.Loc, this.Vwind);
    }
    else this.Vwind.set(0.0D, 0.0D, 0.0D);

    this.Vair.sub(this.Vwld, this.Vwind);

    this.Or.transformInv(this.Vair, this.Vflow);
    this.Density = Atmosphere.density((float)this.Loc.z);

    this.AOA = RAD2DEG(-(float)Math.atan2(this.Vflow.z, this.Vflow.x));
    this.AOS = RAD2DEG((float)Math.atan2(this.Vflow.y, this.Vflow.x));

    this.V2 = (float)this.Vflow.lengthSquared();
    this.V = (float)Math.sqrt(this.V2);

    this.Mach = (this.V / Atmosphere.sonicSpeed((float)this.Loc.z));
    if (this.Mach > 0.8F) this.Mach = 0.8F;
    this.Kq = (1.0F / (float)Math.sqrt(1.0F - this.Mach * this.Mach));

    this.q_ = (this.Density * this.V2 * 0.5F);

    if (!this.callSuperUpdate) return;

    double d1 = this.Loc.z - this.Gears.screenHQ;
    if (d1 < 0.0D) d1 = 0.0D;

    this.Cw.x = (-this.q_ * (this.Wing.new_Cx(this.AOA) + 2.0F * this.GearCX * this.CT.getGear() + 2.0F * this.Sq.dragAirbrakeCx * this.CT.getAirBrake()));

    this.Cw.z = (this.q_ * this.Wing.new_Cy(this.AOA) * this.Kq);
    if ((this.fmsfxCurrentType != 0) && 
      (this.fmsfxCurrentType == 1)) {
      this.Cw.z *= Aircraft.cvt(this.fmsfxPrevValue, 0.003F, 0.8F, 1.0F, 0.0F);
    }

    if (d1 < 0.4D * this.Length) {
      double d2 = 1.0D - d1 / (0.4D * this.Length);
      double d3 = 1.0D + 0.3D * d2;
      double d5 = 1.0D + 0.3D * d2;
      this.Cw.z *= d3;
      this.Cw.x *= d5;
    }

    this.Cw.y = (-this.q_ * this.Wing.new_Cz(this.AOS));

    for (int k = 0; k < this.EI.getNum(); k++) {
      this.Cw.x += -this.q_ * (0.8F * this.Sq.dragEngineCx[k]);
    }

    this.Fw.scale(this.Sq.liftWingLIn + this.Sq.liftWingLMid + this.Sq.liftWingLOut + this.Sq.liftWingRIn + this.Sq.liftWingRMid + this.Sq.liftWingROut, this.Cw);

    this.Fw.x -= this.q_ * (this.Sq.dragParasiteCx + this.Sq.dragProducedCx);

    this.AF.set(this.Fw);
    this.AF.y -= this.AOS * this.q_ * 0.1F;

    if (isNAN(this.AF)) { this.AF.set(0.0D, 0.0D, 0.0D); flutter();
    } else if (this.AF.length() > this.Gravity * 50.0F) { this.AF.normalize(); this.AF.scale(this.Gravity * 50.0F); flutter();
    }

    this.AM.set(0.0D, 0.0D, 0.0D); this.Wtrue.set(0.0D, 0.0D, 0.0D);

    this.AM.x = ((this.Sq.liftWingLIn * this.Arms.WING_ROOT + this.Sq.liftWingLMid * this.Arms.WING_MIDDLE + this.Sq.liftWingLOut * this.Arms.WING_END - this.Sq.liftWingRIn * this.Arms.WING_ROOT - this.Sq.liftWingRMid * this.Arms.WING_MIDDLE - this.Sq.liftWingROut * this.Arms.WING_END) * this.Cw.z);

    if (this.fmsfxCurrentType != 0) {
      if (this.fmsfxCurrentType == 2) {
        this.AM.x = ((-this.Sq.liftWingRIn * this.Arms.WING_ROOT - this.Sq.liftWingRMid * this.Arms.WING_MIDDLE - this.Sq.liftWingROut * this.Arms.WING_END) * this.Cw.z);

        if (Time.current() >= this.fmsfxTimeDisable) {
          doRequestFMSFX(0, 0);
        }
      }
      if (this.fmsfxCurrentType == 3) {
        this.AM.x = ((this.Sq.liftWingLIn * this.Arms.WING_ROOT + this.Sq.liftWingLMid * this.Arms.WING_MIDDLE + this.Sq.liftWingLOut * this.Arms.WING_END) * this.Cw.z);

        if (Time.current() >= this.fmsfxTimeDisable) {
          doRequestFMSFX(0, 0);
        }

      }

    }

    if (Math.abs(this.AOA) > 33.0F) this.AM.y = (1.0F * (this.Sq.liftStab * this.Arms.HOR_STAB) * (this.q_ * this.Tail.new_Cy(this.AOA) * this.Kq));
    if (Math.abs(this.AOS) > 33.0F) this.AM.z = (1.0F * ((0.2F + this.Sq.liftKeel) * this.Arms.VER_STAB) * (this.q_ * this.Tail.new_Cy(this.AOS) * this.Kq));
    float f1 = this.Sq.liftWingLIn + this.Sq.liftWingLMid + this.Sq.liftWingLOut;
    float f2 = this.Sq.liftWingRIn + this.Sq.liftWingRMid + this.Sq.liftWingROut;
    float f3 = (float)this.Vflow.lengthSquared() - 120.0F;
    if (f3 < 0.0F) f3 = 0.0F;
    if (this.Vflow.x < 0.0D) f3 = 0.0F;
    if (f3 > 15000.0F) f3 = 15000.0F;
    float f4;
    if (((Maneuver)(Maneuver)((Aircraft)this.actor).FM).get_maneuver() != 20) {
      f4 = f1 - f2;
      float f5;
      if ((!getOp(19)) && (d1 > 20.0D) && (f3 > 10.0F)) {
        this.AM.y += 5.0F * this.Sq.squareWing * this.Vflow.x;
        this.AM.z += 80.0F * this.Sq.squareWing * this.EI.getPropDirSign();
        if ((this.AOA > 20.0F) || (this.AOA < -20.0F)) {
          f5 = 1.0F;
          if (this.W.z < 0.0D) f5 = -1.0F;
          this.AM.z += 30.0F * f5 * this.Sq.squareWing * (3.0D * this.Vflow.z + this.Vflow.x);
          this.AM.x -= 50.0F * f5 * this.Sq.squareWing * (this.Vflow.z + 3.0D * this.Vflow.x);
        }
      }
      else {
        if (!this.Gears.onGround()) {
          f5 = this.AOA * 3.0F;
          if (f5 > 25.0F) f5 = 25.0F;
          if (f5 < -25.0F) f5 = -25.0F;
          if (!getOp(34)) {
            this.AM.x -= f5 * f2 * f3;
          }
          else if (!getOp(37)) {
            this.AM.x += f5 * f1 * f3;
          }
          else if ((((Maneuver)(Maneuver)((Aircraft)this.actor).FM).get_maneuver() == 44) && ((this.AOA > 15.0F) || (this.AOA < -12.0F))) {
            if ((f4 > 0.0F) && (this.W.z > 0.0D)) this.W.z = -9.999999747378752E-005D;
            if ((f4 < 0.0F) && (this.W.z < 0.0D)) this.W.z = 9.999999747378752E-005D;
            if (f3 > 1000.0F) f3 = 1000.0F;
            if (this.W.z < 0.0D) {
              this.AM.z -= 3.0F * this.Sq.squareWing * f3;
              if (this.AOA > 0.0F) this.AM.x += 40.0F * this.Sq.squareWing * f3; else
                this.AM.x -= 40.0F * this.Sq.squareWing * f3;
            }
            else {
              this.AM.z += 3.0F * this.Sq.squareWing * f3;
              if (this.AOA > 0.0F) this.AM.x -= 40.0F * this.Sq.squareWing * f3; else
                this.AM.x += 40.0F * this.Sq.squareWing * f3;
            }
          }
        }
        if (this.Sq.liftKeel > 0.1F) this.AM.z += this.AOS * this.q_ * 0.5F; else
          this.AM.x += this.AOS * this.q_ * 1.0F;
        double d6 = 1.0D;
        if (d1 < 1.5D * this.Length) {
          d6 += (d1 - 1.5D * this.Length) / this.Length;
          if (d6 < 0.0D) d6 = 0.0D;
        }
        if ((this.Vflow.x < 20.0D) && (Math.abs(this.AOS) < 33.0F)) this.AM.y += d6 * this.AF.z;

        float f6 = (float)this.Vflow.x;
        if (f6 > 150.0F) f6 = 150.0F;
        float f7 = this.SensYaw;
        if (f7 > 0.2F) f7 = 0.2F;
        f8 = this.SensRoll;
        if (f8 > 4.0F) f8 = 4.0F;

        double d8 = 20.0D - Math.abs(this.AOA);
        if (d8 < this.minElevCoeff) d8 = this.minElevCoeff;
        double d10 = this.AOA - this.CT.getElevator() * d8;
        double d11 = 0.017D * Math.abs(this.AOA);
        if (d11 < 1.0D) d11 = 1.0D;
        if (d10 > 90.0D) d10 = -(180.0D - d10);
        if (d10 < -90.0D) d10 = 180.0D - d10;
        d10 *= d11;

        double d12 = 12.0D - Math.abs(this.AOS);
        if (d12 < 0.0D) d12 = 0.0D;
        double d13 = this.AOS - this.CT.getRudder() * d12;
        double d14 = 0.01D * Math.abs(this.AOS);
        if (d14 < 1.0D) d14 = 1.0D;
        if (d13 > 90.0D) d13 = -(180.0D - d13);
        if (d13 < -90.0D) d13 = 180.0D - d13;
        d13 *= d14;

        float f10 = this.Sq.squareWing;
        if (f10 < 1.0F) f10 = 1.0F;
        f10 = 1.0F / f10;
        this.Wtrue.x = (this.CT.getAileron() * f6 * f8 * this.Sq.squareAilerons * f10 * 1.0F);
        this.Wtrue.y = (d10 * f6 * this.SensPitch * this.Sq.squareElevators * f10 * 0.01200000010430813D);
        this.Wtrue.z = (d13 * f6 * f7 * this.Sq.squareRudders * f10 * 0.1500000059604645D);
      }
    } else {
      f4 = 1.0F;
      if (this.W.z < 0.0D) f4 = -1.0F;
      this.AM.z += 30.0F * f4 * this.Sq.squareWing * (3.0D * this.Vflow.z + this.Vflow.x);
      this.AM.x -= 50.0F * f4 * this.Sq.squareWing * (this.Vflow.z + 3.0D * this.Vflow.x);
    }
    if ((this.Sq.squareElevators < 0.1F) && (d1 > 20.0D) && (f3 > 10.0F)) this.AM.y += this.Gravity * 0.4F;

    this.AM.add(this.producedAM);
    this.AF.add(this.producedAF);
    this.producedAM.set(0.0D, 0.0D, 0.0D);
    this.producedAF.set(0.0D, 0.0D, 0.0D);

    this.AF.add(this.EI.producedF);

    if (this.W.lengthSquared() > 36.0D) this.W.scale(6.0D / this.W.length());

    double d4 = 0.1D + this.Sq.squareWing;
    if (d4 < 1.0D) d4 = 1.0D;
    d4 = 1.0D / d4;
    this.W.x *= (1.0D - 0.12D * (0.2D + f1 + f2) * d4);
    this.W.y *= (1.0D - 0.5D * (0.2D + this.Sq.liftStab) * d4);
    this.W.z *= (1.0D - 0.5D * (0.2D + this.Sq.liftKeel) * d4);

    this.GF.set(0.0D, 0.0D, 0.0D);
    this.GM.set(0.0D, 0.0D, 0.0D);
    this.Gears.roughness = 0.5D;
    this.Gears.ground(this, true);
    this.GM.x *= 0.1D;
    this.GM.y *= 0.4D;
    this.GM.z *= 0.8D;
    int m = 2;
    if (((this.GF.lengthSquared() == 0.0D) && (this.GM.lengthSquared() == 0.0D)) || (this.brakeShoe)) m = 1;

    this.SummF.add(this.AF, this.GF);
    this.ACmeter.set(this.SummF);
    this.ACmeter.scale(1.0F / this.Gravity);

    this.TmpV.set(0.0D, 0.0D, -this.Gravity);
    this.Or.transformInv(this.TmpV);
    this.GF.add(this.TmpV);

    this.SummF.add(this.AF, this.GF);
    this.SummM.add(this.AM, this.GM);
    double d7 = 1.0D / this.M.mass;
    this.LocalAccel.scale(d7, this.SummF);

    if (isNAN(this.AM)) { this.AM.set(0.0D, 0.0D, 0.0D); flutter();
    } else if (this.AM.length() > this.Gravity * 100.0F) {
      this.AM.normalize(); this.AM.scale(this.Gravity * 100.0F);
      flutter();
    }

    this.dryFriction = (float)(this.dryFriction - 0.01D);
    if (this.Gears.gearsChanged) this.dryFriction = 1.0F;
    if (this.Gears.nOfPoiOnGr > 0) this.dryFriction += 0.02F;
    if (this.dryFriction < 1.0F) this.dryFriction = 1.0F;
    if (this.dryFriction > 32.0F) this.dryFriction = 32.0F;
    float f8 = 4.0F * (0.25F - this.EI.getPowerOutput());
    if (f8 < 0.0F) f8 = 0.0F;
    f8 *= f8;
    f8 *= this.dryFriction;
    float f9 = f8 * this.M.mass * this.M.mass;
    if ((!this.brakeShoe) && (
      ((this.Gears.nOfPoiOnGr == 0) && (this.Gears.nOfGearsOnGr < 3)) || (f8 == 0.0F) || (this.SummM.lengthSquared() > 2.0F * f9) || (this.SummF.lengthSquared() > 80.0F * f9) || (this.W.lengthSquared() > 0.00014F * f8) || (this.Vwld.lengthSquared() > 0.09F * f8)))
    {
      double d9 = 1.0D / m;
      for (int n = 0; n < m; n++) {
        this.SummF.add(this.AF, this.GF);
        this.SummM.add(this.AM, this.GM);

        this.AW.x = (((this.J.y - this.J.z) * this.W.y * this.W.z + this.SummM.x) / this.J.x);
        this.AW.y = (((this.J.z - this.J.x) * this.W.z * this.W.x + this.SummM.y) / this.J.y);
        this.AW.z = (((this.J.x - this.J.y) * this.W.x * this.W.y + this.SummM.z) / this.J.z);
        this.TmpV.scale(d9 * paramFloat, this.AW);
        this.W.add(this.TmpV);

        this.Or.transform(this.W, this.Vn);
        this.Wtrue.add(this.W);
        this.TmpV.scale(d9 * paramFloat, this.Wtrue);
        this.Or.increment((float)(-RAD2DEG(this.TmpV.z)), (float)(-RAD2DEG(this.TmpV.y)), (float)RAD2DEG(this.TmpV.x));

        this.Or.transformInv(this.Vn, this.W);

        this.TmpV.scale(d7, this.SummF);
        this.Or.transform(this.TmpV);
        this.Accel.set(this.TmpV);
        this.TmpV.scale(d9 * paramFloat);
        this.Vwld.add(this.TmpV);
        this.TmpV.scale(d9 * paramFloat, this.Vwld);
        this.TmpP.set(this.TmpV);
        this.Loc.add(this.TmpP);
        if (isNAN(this.Loc)) {
          int i1 = 0;
        }

        this.GF.set(0.0D, 0.0D, 0.0D);
        this.GM.set(0.0D, 0.0D, 0.0D);
        if (n < m - 1) {
          this.Gears.ground(this, true);
          this.GM.x *= 0.1D;
          this.GM.y *= 0.4D;
          this.GM.z *= 0.8D;

          this.TmpV.set(0.0D, 0.0D, -this.Gravity);
          this.Or.transformInv(this.TmpV);
          this.GF.add(this.TmpV);
        }

      }

      for (n = 0; n < 3; n++) {
        this.Gears.gWheelAngles[n] = ((this.Gears.gWheelAngles[n] + (float)Math.toDegrees(Math.atan(this.Gears.gVelocity[n] * paramFloat / 0.375D))) % 360.0F);

        this.Gears.gVelocity[n] *= 0.949999988079071D;
      }

      this.HM.chunkSetAngles("GearL1_D0", 0.0F, -this.Gears.gWheelAngles[0], 0.0F);
      this.HM.chunkSetAngles("GearR1_D0", 0.0F, -this.Gears.gWheelAngles[1], 0.0F);
      this.HM.chunkSetAngles("GearC1_D0", 0.0F, -this.Gears.gWheelAngles[2], 0.0F);
    }
  }
}