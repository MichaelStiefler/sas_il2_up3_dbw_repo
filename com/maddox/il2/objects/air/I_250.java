package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class I_250 extends Scheme2
  implements TypeFighter, TypeBNZFighter
{
  private float flapps = 0.0F;
  private float fdor = 0.0F;
  private float pdor = 0.0F;

  public void update(float paramFloat) { super.update(paramFloat);
    float f;
    if (Config.isUSE_RENDER()) {
      this.pdor = (0.9F * this.pdor + 0.1F * this.fdor);
      hierMesh().chunkSetAngles("Door1_D0", 0.0F, -20.0F + 20.0F * this.pdor, 0.0F);
      hierMesh().chunkSetAngles("Door2_D0", 0.0F, -20.0F + 20.0F * this.pdor, 0.0F);
      f = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator();
      if (Math.abs(this.flapps - f) > 0.01F) {
        this.flapps = f;
        for (int i = 1; i < 5; i++) {
          hierMesh().chunkSetAngles("Radiator" + i + "_D0", 0.0F, -20.0F * f, 0.0F);
        }
      }
      f = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlThrottle();
      if (Math.abs(this.fdor - f) > 0.01F) {
        this.fdor = f;
      }
    }
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isMaster()) {
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getStage() == 1) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].setStage(this, 6);
        HUD.log("EngineI" + (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getStage() == 6 ? '1' : '0'));
      }
      f = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getThrustOutput() * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlThrottle();
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getStage() == 6) && (f > 0.32F)) {
        if (f > 0.75F)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setSootState(this, 1, 3);
        else
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setSootState(this, 1, 2);
      }
      else {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setSootState(this, 1, 0);
      }
    }
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getStage() == 6) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setEngineMomentMax(4337.5869F);
      f = 1.01F * (0.12F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getw() + 0.88F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getw());
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].setw(f);
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getStage() != 6) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].setw(0.7F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getw()); 
    }
    else {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setEngineMomentMax(5224.1021F);
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC99_D0", 0.0F, Aircraft.cvt(paramFloat, 0.15F, 0.45F, 0.0F, -50.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
    float tmp61_60 = (Aircraft.ypr[2] = Aircraft.xyz[0] = Aircraft.xyz[1] = Aircraft.xyz[2] = 0.0F); Aircraft.ypr[1] = tmp61_60; Aircraft.ypr[0] = tmp61_60;
    Aircraft.ypr[2] = Aircraft.cvt(paramFloat, 0.05F, 0.25F, 0.0F, -45.0F);
    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.05F, 0.25F, 0.0F, 0.3F);
    Aircraft.xyz[2] = Aircraft.cvt(paramFloat, 0.05F, 0.25F, 0.0F, 0.125F);
    paramHierMesh.chunkSetLocate("GearC3_D0", Aircraft.xyz, Aircraft.ypr);
    paramHierMesh.chunkSetLocate("GearC4_D0", Aircraft.xyz, Aircraft.ypr);

    if (paramFloat < 0.5F)
      paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.02F, 0.4F, 0.0F, -90.0F), 0.0F);
    else {
      paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.72F, 0.98F, -90.0F, 0.0F), 0.0F);
    }
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.95F, 0.0F, -82.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.95F, 0.0F, -82.0F), 0.0F);

    if (paramFloat < 0.5F)
      paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.02F, 0.4F, 0.0F, -90.0F), 0.0F);
    else {
      paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.72F, 0.98F, -90.0F, 0.0F), 0.0F);
    }
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.25F, 0.9F, 0.0F, -82.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.25F, 0.9F, 0.0F, -82.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -Aircraft.cvt(paramFloat, -78.0F, 78.0F, -78.0F, 78.0F), 0.0F);
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i = 0;

    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor")) {
        debuggunnery("Armor: Hit..");
        if (paramString.startsWith("xxarmorp")) {
          getEnergyPastArmor(12.88F, paramShot);
        }
        if (paramString.startsWith("xxarmorg")) {
          getEnergyPastArmor(33.959999084472656D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
          if (paramShot.power <= 0.0F) {
            if (Math.abs(Aircraft.v1.jdField_x_of_type_Double) > 0.8659999966621399D)
              doRicochet(paramShot);
            else {
              doRicochetBack(paramShot);
            }
          }
        }
        return;
      }

      if (paramString.startsWith("xxcontrols")) {
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
        case 3:
          if (getEnergyPastArmor(4.1F, paramShot) <= 0.0F) break;
          Aircraft.debugprintln(this, "*** Aileron Controls Crank: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0); break;
        case 2:
        case 4:
          if ((getEnergyPastArmor(2.1F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.25F)) break;
          Aircraft.debugprintln(this, "*** Aileron Controls: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0); break;
        case 5:
        case 6:
          if (getEnergyPastArmor(0.3F, paramShot) <= 0.0F) break;
          Aircraft.debugprintln(this, "*** Rudder Controls: Disabled / Strings Broken..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2); break;
        case 8:
          if (getEnergyPastArmor(3.2F, paramShot) <= 0.0F) break;
          Aircraft.debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0); break;
        case 9:
          if (getEnergyPastArmor(0.1F, paramShot) <= 0.0F) break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x8);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 6);
          Aircraft.debugprintln(this, "*** Throttle Quadrant: Hit, Engine Controls Disabled..");
        case 7:
        }

        return;
      }

      if (paramString.startsWith("xxeng1")) {
        debuggunnery("Engine Module: Hit..");
        if (paramString.endsWith("prop")) {
          if ((getEnergyPastArmor(3.6F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.8F))
            if (World.Rnd().nextFloat() < 0.5F) {
              debuggunnery("Engine Module: Prop Governor Hit, Disabled..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 3);
            } else {
              debuggunnery("Engine Module: Prop Governor Hit, Oil Pipes Damaged..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 4);
            }
        }
        else if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(2.2F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 175000.0F) {
              debuggunnery("Engine Module: Crank Case Hit, Bullet Jams Ball Bearings..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, 0);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
              debuggunnery("Engine Module: Crank Case Hit, Readyness Reduced to " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness() + "..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 2);
            }
          }
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setReadyness(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
          debuggunnery("Engine Module: Crank Case Hit, Readyness Reduced to " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness() + "..");
          getEnergyPastArmor(22.5F, paramShot);
        } else if (paramString.startsWith("xxeng1cyl")) {
          if ((getEnergyPastArmor(1.3F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersRatio() * 1.75F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
            debuggunnery("Engine Module: Cylinders Assembly Hit, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylinders() + " Operating..");
            if (World.Rnd().nextFloat() < paramShot.power / 48000.0F) {
              debuggunnery("Engine Module: Cylinders Assembly Hit, Engine Fires..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 3);
            }
            if (World.Rnd().nextFloat() < 0.01F) {
              debuggunnery("Engine Module: Cylinders Assembly Hit, Bullet Jams Piston Head..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, 0);
            }
            getEnergyPastArmor(22.5F, paramShot);
          }
          if ((Math.abs(paramPoint3d.jdField_y_of_type_Double) < 0.137999996542931D) && 
            (getEnergyPastArmor(3.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
            if (World.Rnd().nextFloat() < 0.1F) {
              debuggunnery("Engine Module: Feed Lines Hit, Engine Stalled..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setEngineStops(paramShot.initiator);
            }
            if (World.Rnd().nextFloat() < 0.05F) {
              debuggunnery("Engine Module: Feed Gear Hit, Engine Jams..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, 0);
            }
            if (World.Rnd().nextFloat() < 0.1F) {
              debuggunnery("Engine Module: Feed Gear Hit, Half Cylinder Feed Cut-Out..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setCyliderKnockOut(paramShot.initiator, 6);
            }
          }
        }

        return;
      }

      if (paramString.startsWith("xxeng2")) {
        if ((paramString.startsWith("xxeng2supc")) && 
          (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** Engine Module(s): Supercharger Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 0);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 1, 0);
        }

        if (paramString.startsWith("xxeng2case")) {
          if (getEnergyPastArmor(3.2F, paramShot) > 0.0F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setCyliderKnockOut(paramShot.initiator, 1);
          }
          if ((World.Rnd().nextFloat(0.009F, 0.1357F) < paramShot.mass) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getStage() == 6)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 3);
          }
          getEnergyPastArmor(14.296F, paramShot);
        }
        return;
      }

      if (paramString.startsWith("xxspar")) {
        debuggunnery("Spar Construction: Hit..");
        if ((paramString.startsWith("xxsparli")) && 
          (chunkDamageVisible("WingLIn") > 2) && (World.Rnd().nextFloat() > Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLIn Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparri")) && 
          (chunkDamageVisible("WingRIn") > 2) && (World.Rnd().nextFloat() > Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingRIn Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlm")) && 
          (chunkDamageVisible("WingLMid") > 2) && (World.Rnd().nextFloat() > Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLMid Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparrm")) && 
          (chunkDamageVisible("WingRMid") > 2) && (World.Rnd().nextFloat() > Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingRMid Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlo")) && 
          (chunkDamageVisible("WingLOut") > 2) && (World.Rnd().nextFloat() > Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLOut Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparro")) && 
          (chunkDamageVisible("WingROut") > 2) && (World.Rnd().nextFloat() > Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingROut Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxspart")) && 
          (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.86F / (float)Math.sqrt(Aircraft.v1.jdField_y_of_type_Double * Aircraft.v1.jdField_y_of_type_Double + Aircraft.v1.jdField_z_of_type_Double * Aircraft.v1.jdField_z_of_type_Double), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          debuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }

      }

      if (paramString.startsWith("xxlock")) {
        debuggunnery("Lock Construction: Hit..");
        if ((paramString.startsWith("xxlockr")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvl")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: VatorL Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvr")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: VatorR Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockal")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: AroneL Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockar")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: AroneR Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), paramShot.initiator);
        }

      }

      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F) && (paramShot.powerType == 3)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 1);
        }
        return;
      }

      if (paramString.startsWith("xxmgun")) {
        if (paramString.endsWith("01")) {
          debuggunnery("Armament System: Centre Gun: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 0);
        }
        if (paramString.endsWith("02")) {
          debuggunnery("Armament System: Left Machine Gun: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 0);
        }
        if (paramString.endsWith("03")) {
          debuggunnery("Armament System: Right Machine Gun: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 1);
        }
        getEnergyPastArmor(World.Rnd().nextFloat(0.3F, 12.6F), paramShot);
        return;
      }

      return;
    }

    if (paramString.startsWith("xcockpit")) {
      if (paramPoint3d.jdField_z_of_type_Double > 0.775D) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x1);
        if ((Aircraft.v1.jdField_x_of_type_Double < -0.9D) && (getEnergyPastArmor(12.0D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x2);
        }
      }
      else if (paramPoint3d.jdField_y_of_type_Double > 0.0D) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x4);
      } else {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x10);
      }

      if (World.Rnd().nextFloat() < 0.067F) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x8);
      }
      if (World.Rnd().nextFloat() < 0.067F) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x20);
      }
      if (World.Rnd().nextFloat() < 0.067F) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x40);
      }
    }
    if (paramString.startsWith("xcf")) {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
    }
    else if (paramString.startsWith("xeng")) {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel")) {
      hitChunk("Keel1", paramShot);
    } else if (paramString.startsWith("xrudder")) {
      if (chunkDamageVisible("Rudder1") < 1)
        hitChunk("Rudder1", paramShot);
    }
    else if (paramString.startsWith("xstab")) {
      if (paramString.startsWith("xstabl")) {
        hitChunk("StabL", paramShot);
      }
      if (paramString.startsWith("xstabr"))
        hitChunk("StabR", paramShot);
    }
    else if (paramString.startsWith("xvator")) {
      if ((paramString.startsWith("xvatorl")) && 
        (chunkDamageVisible("VatorL") < 1)) {
        hitChunk("VatorL", paramShot);
      }

      if ((paramString.startsWith("xvatorr")) && 
        (chunkDamageVisible("VatorR") < 1)) {
        hitChunk("VatorR", paramShot);
      }
    }
    else if (paramString.startsWith("xwing")) {
      if ((paramString.startsWith("xwinglin")) && 
        (chunkDamageVisible("WingLIn") < 3)) {
        hitChunk("WingLIn", paramShot);
      }

      if ((paramString.startsWith("xwingrin")) && 
        (chunkDamageVisible("WingRIn") < 3)) {
        hitChunk("WingRIn", paramShot);
      }

      if ((paramString.startsWith("xwinglmid")) && 
        (chunkDamageVisible("WingLMid") < 3)) {
        hitChunk("WingLMid", paramShot);
      }

      if ((paramString.startsWith("xwingrmid")) && 
        (chunkDamageVisible("WingRMid") < 3)) {
        hitChunk("WingRMid", paramShot);
      }

      if ((paramString.startsWith("xwinglout")) && 
        (chunkDamageVisible("WingLOut") < 3)) {
        hitChunk("WingLOut", paramShot);
      }

      if ((paramString.startsWith("xwingrout")) && 
        (chunkDamageVisible("WingROut") < 3)) {
        hitChunk("WingROut", paramShot);
      }
    }
    else if (paramString.startsWith("xarone")) {
      if ((paramString.startsWith("xaronel")) && 
        (chunkDamageVisible("AroneL") < 1)) {
        hitChunk("AroneL", paramShot);
      }

      if ((paramString.startsWith("xaroner")) && 
        (chunkDamageVisible("AroneR") < 1)) {
        hitChunk("AroneR", paramShot);
      }
    }
    else if (paramString.startsWith("xgear")) {
      if ((paramString.endsWith("1")) && 
        (World.Rnd().nextFloat() < 0.05F)) {
        debuggunnery("Hydro System: Disabled..");
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setInternalDamage(paramShot.initiator, 0);
      }

      if (((paramString.endsWith("2a")) || (paramString.endsWith("2b"))) && 
        (World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 3.435F), paramShot) > 0.0F)) {
        debuggunnery("Undercarriage: Stuck..");
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setInternalDamage(paramShot.initiator, 3);
      }
    }
    else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
      i = 0;
      int j;
      if (paramString.endsWith("a")) {
        i = 1;
        j = paramString.charAt(6) - '1';
      } else if (paramString.endsWith("b")) {
        i = 2;
        j = paramString.charAt(6) - '1';
      } else {
        j = paramString.charAt(5) - '1';
      }
      hitFlesh(j, paramShot, i);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 19:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineDies(this, 1);
      return cut(Aircraft.partNames()[paramInt1]);
    case 3:
    case 4:
      return false;
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void moveCockpitDoor(float paramFloat)
  {
    resetYPRmodifier();
    Aircraft.xyz[2] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, -0.6F);
    hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null)) Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      setDoorSnd(paramFloat);
    }
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "I-250");
    Property.set(localClass, "meshName", "3DO/Plane/I-250(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar06());
    Property.set(localClass, "meshName_ru", "3DO/Plane/I-250(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme_ru", new PaintSchemeFCSPar05());
    Property.set(localClass, "meshName_gb", "3DO/Plane/I-250(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme_gb", new PaintSchemeBCSPar02());
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);

    Property.set(localClass, "yearService", 1946.0F);
    Property.set(localClass, "yearExpired", 1956.0F);

    Property.set(localClass, "FlightModel", "FlightModels/I-250.fmd");
    Property.set(localClass, "cockpitClass", CockpitI_250.class);
    Property.set(localClass, "LOSElevation", 0.61825F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 1, 1 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_CANNON03" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunB20ki 100", "MGunB20s 100", "MGunB20s 100" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null });
  }
}