package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
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
  }

  private void flutter()
  {
    ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).msgCollision(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, "CF_D0", "CF_D0");
  }
  public Vector3d getW() {
    return this.Wtrue;
  }

  public void update(float paramFloat)
  {
    this.jdField_CT_of_type_ComMaddoxIl2FmControls.update(paramFloat, (getSpeed() + 50.0F) * 0.5F, this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface, false);
    this.jdField_Wing_of_type_ComMaddoxIl2FmPolares.setFlaps(this.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap());
    this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.update(paramFloat);
    super.update(paramFloat);

    this.jdField_Gravity_of_type_Float = (this.jdField_M_of_type_ComMaddoxIl2FmMass.getFullMass() * Atmosphere.g());
    this.jdField_M_of_type_ComMaddoxIl2FmMass.computeFullJ(this.jdField_J_of_type_ComMaddoxJGPVector3d, this.J0);

    if (isTick(44, 0)) {
      this.AS.update(paramFloat * 44.0F);
      ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).rareAction(paramFloat * 44.0F, true);
      this.jdField_M_of_type_ComMaddoxIl2FmMass.computeParasiteMass(this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons);
      this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.computeParasiteDrag(this.jdField_CT_of_type_ComMaddoxIl2FmControls, this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons);

      if ((((Pilot)((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel).get_task() == 7) || (((Maneuver)((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel).get_maneuver() == 43)) {
        putScareShpere();
      }
      if (World.Rnd().nextInt(0, 99) < 25) {
        Aircraft localAircraft1 = (Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor;
        if ((localAircraft1.aircIndex() == 0) && 
          (!(localAircraft1 instanceof TypeFighter))) {
          int i = this.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy() - 1 & 0x1;
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

    this.jdField_Vwind_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);

    this.jdField_Vair_of_type_ComMaddoxJGPVector3d.sub(this.jdField_Vwld_of_type_ComMaddoxJGPVector3d, this.jdField_Vwind_of_type_ComMaddoxJGPVector3d);

    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(this.jdField_Vair_of_type_ComMaddoxJGPVector3d, this.jdField_Vflow_of_type_ComMaddoxJGPVector3d);
    this.Density = Atmosphere.density((float)this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double);

    this.jdField_AOA_of_type_Float = FMMath.RAD2DEG(-(float)Math.atan2(this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double, this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double));
    this.jdField_AOS_of_type_Float = FMMath.RAD2DEG((float)Math.atan2(this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double, this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double));

    this.jdField_V2_of_type_Float = (float)this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.lengthSquared();
    this.jdField_V_of_type_Float = (float)Math.sqrt(this.jdField_V2_of_type_Float);

    this.jdField_Mach_of_type_Float = (this.jdField_V_of_type_Float / Atmosphere.sonicSpeed((float)this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double));
    if (this.jdField_Mach_of_type_Float > 0.8F) this.jdField_Mach_of_type_Float = 0.8F;
    this.Kq = (1.0F / (float)Math.sqrt(1.0F - this.jdField_Mach_of_type_Float * this.jdField_Mach_of_type_Float));

    this.jdField_q__of_type_Float = (this.Density * this.jdField_V2_of_type_Float * 0.5F);

    if (!this.callSuperUpdate) return;

    double d1 = this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - this.jdField_Gears_of_type_ComMaddoxIl2FmGear.screenHQ;
    if (d1 < 0.0D) d1 = 0.0D;

    this.Cw.jdField_x_of_type_Double = (-this.jdField_q__of_type_Float * (this.jdField_Wing_of_type_ComMaddoxIl2FmPolares.new_Cx(this.jdField_AOA_of_type_Float) + 2.0F * this.GearCX * this.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() + 2.0F * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.dragAirbrakeCx * this.jdField_CT_of_type_ComMaddoxIl2FmControls.getAirBrake()));

    this.Cw.jdField_z_of_type_Double = (this.jdField_q__of_type_Float * this.jdField_Wing_of_type_ComMaddoxIl2FmPolares.new_Cy(this.jdField_AOA_of_type_Float) * this.Kq);
    if ((this.jdField_fmsfxCurrentType_of_type_Int != 0) && 
      (this.jdField_fmsfxCurrentType_of_type_Int == 1)) {
      this.Cw.jdField_z_of_type_Double *= Aircraft.cvt(this.fmsfxPrevValue, 0.003F, 0.8F, 1.0F, 0.0F);
    }

    if (d1 < 0.4D * this.jdField_Length_of_type_Float) {
      double d2 = 1.0D - d1 / (0.4D * this.jdField_Length_of_type_Float);
      double d3 = 1.0D + 0.3D * d2;
      double d4 = 1.0D + 0.3D * d2;
      this.Cw.jdField_z_of_type_Double *= d3;
      this.Cw.jdField_x_of_type_Double *= d4;
    }

    this.Cw.jdField_y_of_type_Double = (-this.jdField_q__of_type_Float * this.jdField_Wing_of_type_ComMaddoxIl2FmPolares.new_Cz(this.jdField_AOS_of_type_Float));

    for (int k = 0; k < this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum(); k++) {
      this.Cw.jdField_x_of_type_Double += -this.jdField_q__of_type_Float * (0.8F * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.dragEngineCx[k]);
    }

    this.Fw.scale(this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLIn + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLMid + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLOut + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingRIn + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingRMid + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingROut, this.Cw);

    this.Fw.jdField_x_of_type_Double -= this.jdField_q__of_type_Float * (this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.dragParasiteCx + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.dragProducedCx);

    this.jdField_AF_of_type_ComMaddoxJGPVector3d.set(this.Fw);
    this.jdField_AF_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double -= this.jdField_AOS_of_type_Float * this.jdField_q__of_type_Float * 0.1F;

    if (FMMath.isNAN(this.jdField_AF_of_type_ComMaddoxJGPVector3d)) { this.jdField_AF_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D); flutter();
    } else if (this.jdField_AF_of_type_ComMaddoxJGPVector3d.length() > this.jdField_Gravity_of_type_Float * 50.0F) { this.jdField_AF_of_type_ComMaddoxJGPVector3d.normalize(); this.jdField_AF_of_type_ComMaddoxJGPVector3d.scale(this.jdField_Gravity_of_type_Float * 50.0F); flutter();
    }

    this.jdField_AM_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D); this.Wtrue.set(0.0D, 0.0D, 0.0D);

    this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double = ((this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLIn * this.jdField_Arms_of_type_ComMaddoxIl2FmArm.WING_ROOT + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLMid * this.jdField_Arms_of_type_ComMaddoxIl2FmArm.WING_MIDDLE + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLOut * this.jdField_Arms_of_type_ComMaddoxIl2FmArm.WING_END - this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingRIn * this.jdField_Arms_of_type_ComMaddoxIl2FmArm.WING_ROOT - this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingRMid * this.jdField_Arms_of_type_ComMaddoxIl2FmArm.WING_MIDDLE - this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingROut * this.jdField_Arms_of_type_ComMaddoxIl2FmArm.WING_END) * this.Cw.jdField_z_of_type_Double);

    if (this.jdField_fmsfxCurrentType_of_type_Int != 0) {
      if (this.jdField_fmsfxCurrentType_of_type_Int == 2) {
        this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double = ((-this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingRIn * this.jdField_Arms_of_type_ComMaddoxIl2FmArm.WING_ROOT - this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingRMid * this.jdField_Arms_of_type_ComMaddoxIl2FmArm.WING_MIDDLE - this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingROut * this.jdField_Arms_of_type_ComMaddoxIl2FmArm.WING_END) * this.Cw.jdField_z_of_type_Double);

        if (Time.current() >= this.jdField_fmsfxTimeDisable_of_type_Long) {
          doRequestFMSFX(0, 0);
        }
      }
      if (this.jdField_fmsfxCurrentType_of_type_Int == 3) {
        this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double = ((this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLIn * this.jdField_Arms_of_type_ComMaddoxIl2FmArm.WING_ROOT + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLMid * this.jdField_Arms_of_type_ComMaddoxIl2FmArm.WING_MIDDLE + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLOut * this.jdField_Arms_of_type_ComMaddoxIl2FmArm.WING_END) * this.Cw.jdField_z_of_type_Double);

        if (Time.current() >= this.jdField_fmsfxTimeDisable_of_type_Long) {
          doRequestFMSFX(0, 0);
        }

      }

    }

    if (Math.abs(this.jdField_AOA_of_type_Float) > 33.0F) this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double = (1.0F * (this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftStab * this.jdField_Arms_of_type_ComMaddoxIl2FmArm.HOR_STAB) * (this.jdField_q__of_type_Float * this.jdField_Tail_of_type_ComMaddoxIl2FmPolares.new_Cy(this.jdField_AOA_of_type_Float) * this.Kq));
    if (Math.abs(this.jdField_AOS_of_type_Float) > 33.0F) this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double = (1.0F * ((0.2F + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftKeel) * this.jdField_Arms_of_type_ComMaddoxIl2FmArm.VER_STAB) * (this.jdField_q__of_type_Float * this.jdField_Tail_of_type_ComMaddoxIl2FmPolares.new_Cy(this.jdField_AOS_of_type_Float) * this.Kq));
    float f1 = this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLIn + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLMid + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLOut;
    float f2 = this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingRIn + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingRMid + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingROut;
    float f3 = (float)this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.lengthSquared() - 120.0F;
    if (f3 < 0.0F) f3 = 0.0F;
    if (this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double < 0.0D) f3 = 0.0F;
    if (f3 > 15000.0F) f3 = 15000.0F;
    float f4;
    if (((Maneuver)((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel).get_maneuver() != 20) {
      f4 = f1 - f2;
      float f5;
      if ((!getOp(19)) && (d1 > 20.0D) && (f3 > 10.0F)) {
        this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double += 5.0F * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareWing * this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double;
        this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double += 80.0F * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareWing * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getPropDirSign();
        if ((this.jdField_AOA_of_type_Float > 20.0F) || (this.jdField_AOA_of_type_Float < -20.0F)) {
          f5 = 1.0F;
          if (this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double < 0.0D) f5 = -1.0F;
          this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double += 30.0F * f5 * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareWing * (3.0D * this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double + this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double);
          this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double -= 50.0F * f5 * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareWing * (this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double + 3.0D * this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double);
        }
      }
      else {
        if (!this.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround()) {
          f5 = this.jdField_AOA_of_type_Float * 3.0F;
          if (f5 > 25.0F) f5 = 25.0F;
          if (f5 < -25.0F) f5 = -25.0F;
          if (!getOp(34)) {
            this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double -= f5 * f2 * f3;
          }
          else if (!getOp(37)) {
            this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double += f5 * f1 * f3;
          }
          else if ((((Maneuver)((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel).get_maneuver() == 44) && ((this.jdField_AOA_of_type_Float > 15.0F) || (this.jdField_AOA_of_type_Float < -12.0F))) {
            if ((f4 > 0.0F) && (this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double > 0.0D)) this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double = -9.999999747378752E-005D;
            if ((f4 < 0.0F) && (this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double < 0.0D)) this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double = 9.999999747378752E-005D;
            if (f3 > 1000.0F) f3 = 1000.0F;
            if (this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double < 0.0D) {
              this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double -= 3.0F * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareWing * f3;
              if (this.jdField_AOA_of_type_Float > 0.0F) this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double += 40.0F * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareWing * f3; else
                this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double -= 40.0F * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareWing * f3;
            }
            else {
              this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double += 3.0F * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareWing * f3;
              if (this.jdField_AOA_of_type_Float > 0.0F) this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double -= 40.0F * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareWing * f3; else
                this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double += 40.0F * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareWing * f3;
            }
          }
        }
        if (this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftKeel > 0.1F) this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double += this.jdField_AOS_of_type_Float * this.jdField_q__of_type_Float * 0.5F; else
          this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double += this.jdField_AOS_of_type_Float * this.jdField_q__of_type_Float * 1.0F;
        double d6 = 1.0D;
        if (d1 < 1.5D * this.jdField_Length_of_type_Float) {
          d6 += (d1 - 1.5D * this.jdField_Length_of_type_Float) / this.jdField_Length_of_type_Float;
          if (d6 < 0.0D) d6 = 0.0D;
        }
        if ((this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double < 20.0D) && (Math.abs(this.jdField_AOS_of_type_Float) < 33.0F)) this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double += d6 * this.jdField_AF_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double;

        float f6 = (float)this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double;
        if (f6 > 150.0F) f6 = 150.0F;
        float f7 = this.SensYaw;
        if (f7 > 0.2F) f7 = 0.2F;
        f8 = this.SensRoll;
        if (f8 > 4.0F) f8 = 4.0F;

        double d8 = 20.0D - Math.abs(this.jdField_AOA_of_type_Float);
        if (d8 < this.jdField_minElevCoeff_of_type_Float) d8 = this.jdField_minElevCoeff_of_type_Float;
        double d10 = this.jdField_AOA_of_type_Float - this.jdField_CT_of_type_ComMaddoxIl2FmControls.getElevator() * d8;
        double d11 = 0.017D * Math.abs(this.jdField_AOA_of_type_Float);
        if (d11 < 1.0D) d11 = 1.0D;
        if (d10 > 90.0D) d10 = -(180.0D - d10);
        if (d10 < -90.0D) d10 = 180.0D - d10;
        d10 *= d11;

        double d12 = 12.0D - Math.abs(this.jdField_AOS_of_type_Float);
        if (d12 < 0.0D) d12 = 0.0D;
        double d13 = this.jdField_AOS_of_type_Float - this.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder() * d12;
        double d14 = 0.01D * Math.abs(this.jdField_AOS_of_type_Float);
        if (d14 < 1.0D) d14 = 1.0D;
        if (d13 > 90.0D) d13 = -(180.0D - d13);
        if (d13 < -90.0D) d13 = 180.0D - d13;
        d13 *= d14;

        float f10 = this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareWing;
        if (f10 < 1.0F) f10 = 1.0F;
        f10 = 1.0F / f10;
        this.Wtrue.jdField_x_of_type_Double = (this.jdField_CT_of_type_ComMaddoxIl2FmControls.getAileron() * f6 * f8 * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareAilerons * f10 * 1.0F);
        this.Wtrue.jdField_y_of_type_Double = (d10 * f6 * this.SensPitch * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareElevators * f10 * 0.01200000010430813D);
        this.Wtrue.jdField_z_of_type_Double = (d13 * f6 * f7 * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareRudders * f10 * 0.1500000059604645D);
      }
    } else {
      f4 = 1.0F;
      if (this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double < 0.0D) f4 = -1.0F;
      this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double += 30.0F * f4 * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareWing * (3.0D * this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double + this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double);
      this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double -= 50.0F * f4 * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareWing * (this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double + 3.0D * this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double);
    }
    if ((this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareElevators < 0.1F) && (d1 > 20.0D) && (f3 > 10.0F)) this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double += this.jdField_Gravity_of_type_Float * 0.4F;

    this.jdField_AM_of_type_ComMaddoxJGPVector3d.add(this.jdField_producedAM_of_type_ComMaddoxJGPVector3d);
    this.jdField_AF_of_type_ComMaddoxJGPVector3d.add(this.jdField_producedAF_of_type_ComMaddoxJGPVector3d);
    this.jdField_producedAM_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
    this.jdField_producedAF_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);

    this.jdField_AF_of_type_ComMaddoxJGPVector3d.add(this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.producedF);

    if (this.jdField_W_of_type_ComMaddoxJGPVector3d.lengthSquared() > 36.0D) this.jdField_W_of_type_ComMaddoxJGPVector3d.scale(6.0D / this.jdField_W_of_type_ComMaddoxJGPVector3d.length());

    double d5 = 0.1D + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareWing;
    if (d5 < 1.0D) d5 = 1.0D;
    d5 = 1.0D / d5;
    this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double *= (1.0D - 0.12D * (0.2D + f1 + f2) * d5);
    this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double *= (1.0D - 0.5D * (0.2D + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftStab) * d5);
    this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double *= (1.0D - 0.5D * (0.2D + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftKeel) * d5);

    this.jdField_GF_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
    this.jdField_GM_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
    this.jdField_Gears_of_type_ComMaddoxIl2FmGear.roughness = 0.5D;
    this.jdField_Gears_of_type_ComMaddoxIl2FmGear.ground(this, true);
    this.jdField_GM_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double *= 0.1D;
    this.jdField_GM_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double *= 0.4D;
    this.jdField_GM_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double *= 0.8D;
    int m = 2;
    if (((this.jdField_GF_of_type_ComMaddoxJGPVector3d.lengthSquared() == 0.0D) && (this.jdField_GM_of_type_ComMaddoxJGPVector3d.lengthSquared() == 0.0D)) || (this.jdField_brakeShoe_of_type_Boolean)) m = 1;

    this.jdField_SummF_of_type_ComMaddoxJGPVector3d.add(this.jdField_AF_of_type_ComMaddoxJGPVector3d, this.jdField_GF_of_type_ComMaddoxJGPVector3d);
    this.jdField_ACmeter_of_type_ComMaddoxJGPVector3d.set(this.jdField_SummF_of_type_ComMaddoxJGPVector3d);
    this.jdField_ACmeter_of_type_ComMaddoxJGPVector3d.scale(1.0F / this.jdField_Gravity_of_type_Float);

    this.TmpV.set(0.0D, 0.0D, -this.jdField_Gravity_of_type_Float);
    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(this.TmpV);
    this.jdField_GF_of_type_ComMaddoxJGPVector3d.add(this.TmpV);

    this.jdField_SummF_of_type_ComMaddoxJGPVector3d.add(this.jdField_AF_of_type_ComMaddoxJGPVector3d, this.jdField_GF_of_type_ComMaddoxJGPVector3d);
    this.jdField_SummM_of_type_ComMaddoxJGPVector3d.add(this.jdField_AM_of_type_ComMaddoxJGPVector3d, this.jdField_GM_of_type_ComMaddoxJGPVector3d);
    double d7 = 1.0D / this.jdField_M_of_type_ComMaddoxIl2FmMass.mass;
    this.LocalAccel.scale(d7, this.jdField_SummF_of_type_ComMaddoxJGPVector3d);

    if (FMMath.isNAN(this.jdField_AM_of_type_ComMaddoxJGPVector3d)) { this.jdField_AM_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D); flutter();
    } else if (this.jdField_AM_of_type_ComMaddoxJGPVector3d.length() > this.jdField_Gravity_of_type_Float * 100.0F) {
      this.jdField_AM_of_type_ComMaddoxJGPVector3d.normalize(); this.jdField_AM_of_type_ComMaddoxJGPVector3d.scale(this.jdField_Gravity_of_type_Float * 100.0F);
      flutter();
    }

    this.jdField_dryFriction_of_type_Float = (float)(this.jdField_dryFriction_of_type_Float - 0.01D);
    if (this.jdField_Gears_of_type_ComMaddoxIl2FmGear.gearsChanged) this.jdField_dryFriction_of_type_Float = 1.0F;
    if (this.jdField_Gears_of_type_ComMaddoxIl2FmGear.nOfPoiOnGr > 0) this.jdField_dryFriction_of_type_Float += 0.02F;
    if (this.jdField_dryFriction_of_type_Float < 1.0F) this.jdField_dryFriction_of_type_Float = 1.0F;
    if (this.jdField_dryFriction_of_type_Float > 32.0F) this.jdField_dryFriction_of_type_Float = 32.0F;
    float f8 = 4.0F * (0.25F - this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getPowerOutput());
    if (f8 < 0.0F) f8 = 0.0F;
    f8 *= f8;
    f8 *= this.jdField_dryFriction_of_type_Float;
    float f9 = f8 * this.jdField_M_of_type_ComMaddoxIl2FmMass.mass * this.jdField_M_of_type_ComMaddoxIl2FmMass.mass;
    if ((!this.jdField_brakeShoe_of_type_Boolean) && (
      ((this.jdField_Gears_of_type_ComMaddoxIl2FmGear.nOfPoiOnGr == 0) && (this.jdField_Gears_of_type_ComMaddoxIl2FmGear.nOfGearsOnGr < 3)) || (f8 == 0.0F) || (this.jdField_SummM_of_type_ComMaddoxJGPVector3d.lengthSquared() > 2.0F * f9) || (this.jdField_SummF_of_type_ComMaddoxJGPVector3d.lengthSquared() > 80.0F * f9) || (this.jdField_W_of_type_ComMaddoxJGPVector3d.lengthSquared() > 0.00014F * f8) || (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.lengthSquared() > 0.09F * f8)))
    {
      double d9 = 1.0D / m;
      for (int n = 0; n < m; n++) {
        this.jdField_SummF_of_type_ComMaddoxJGPVector3d.add(this.jdField_AF_of_type_ComMaddoxJGPVector3d, this.jdField_GF_of_type_ComMaddoxJGPVector3d);
        this.jdField_SummM_of_type_ComMaddoxJGPVector3d.add(this.jdField_AM_of_type_ComMaddoxJGPVector3d, this.jdField_GM_of_type_ComMaddoxJGPVector3d);

        this.jdField_AW_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double = (((this.jdField_J_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double - this.jdField_J_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double) * this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double * this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double + this.jdField_SummM_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double) / this.jdField_J_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double);
        this.jdField_AW_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double = (((this.jdField_J_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double - this.jdField_J_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double) * this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double * this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double + this.jdField_SummM_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double) / this.jdField_J_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double);
        this.jdField_AW_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double = (((this.jdField_J_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double - this.jdField_J_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double) * this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double * this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double + this.jdField_SummM_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double) / this.jdField_J_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double);
        this.TmpV.scale(d9 * paramFloat, this.jdField_AW_of_type_ComMaddoxJGPVector3d);
        this.jdField_W_of_type_ComMaddoxJGPVector3d.add(this.TmpV);

        this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(this.jdField_W_of_type_ComMaddoxJGPVector3d, this.Vn);
        this.Wtrue.add(this.jdField_W_of_type_ComMaddoxJGPVector3d);
        this.TmpV.scale(d9 * paramFloat, this.Wtrue);
        this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.increment((float)(-FMMath.RAD2DEG(this.TmpV.jdField_z_of_type_Double)), (float)(-FMMath.RAD2DEG(this.TmpV.jdField_y_of_type_Double)), (float)FMMath.RAD2DEG(this.TmpV.jdField_x_of_type_Double));

        this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(this.Vn, this.jdField_W_of_type_ComMaddoxJGPVector3d);

        this.TmpV.scale(d7, this.jdField_SummF_of_type_ComMaddoxJGPVector3d);
        this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(this.TmpV);
        this.Accel.set(this.TmpV);
        this.TmpV.scale(d9 * paramFloat);
        this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.add(this.TmpV);
        this.TmpV.scale(d9 * paramFloat, this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
        this.TmpP.set(this.TmpV);
        this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.add(this.TmpP);
        if (FMMath.isNAN(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d)) {
          i1 = 0;
        }

        this.jdField_GF_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
        this.jdField_GM_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
        if (n < m - 1) {
          this.jdField_Gears_of_type_ComMaddoxIl2FmGear.ground(this, true);
          this.jdField_GM_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double *= 0.1D;
          this.jdField_GM_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double *= 0.4D;
          this.jdField_GM_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double *= 0.8D;

          this.TmpV.set(0.0D, 0.0D, -this.jdField_Gravity_of_type_Float);
          this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(this.TmpV);
          this.jdField_GF_of_type_ComMaddoxJGPVector3d.add(this.TmpV);
        }

      }

      for (int i1 = 0; i1 < 3; i1++) {
        this.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelAngles[i1] = ((this.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelAngles[i1] + (float)Math.toDegrees(Math.atan(this.jdField_Gears_of_type_ComMaddoxIl2FmGear.gVelocity[i1] * paramFloat / 0.375D))) % 360.0F);

        this.jdField_Gears_of_type_ComMaddoxIl2FmGear.gVelocity[i1] *= 0.949999988079071D;
      }

      this.jdField_HM_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("GearL1_D0", 0.0F, -this.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelAngles[0], 0.0F);
      this.jdField_HM_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("GearR1_D0", 0.0F, -this.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelAngles[1], 0.0F);
      this.jdField_HM_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("GearC1_D0", 0.0F, -this.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelAngles[2], 0.0F);
    }
  }
}