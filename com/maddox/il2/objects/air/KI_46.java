package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public abstract class KI_46 extends Scheme2
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float tmp33_32 = (Aircraft.xyz[2] = Aircraft.ypr[0] = Aircraft.ypr[1] = Aircraft.ypr[2] = 0.0F); Aircraft.xyz[1] = tmp33_32; Aircraft.xyz[0] = tmp33_32;
    Aircraft.xyz[1] = (0.415F * paramFloat);
    paramHierMesh.chunkSetLocate("GearC2_D0", Aircraft.xyz, Aircraft.ypr);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.05F, 0.0F, -50.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.05F, 0.0F, -50.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.88F, 0.0F, -120.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.11F, 0.0F, -70.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.11F, 0.0F, -70.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.18F, 0.98F, 0.0F, -120.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.18F, 0.24F, 0.0F, -70.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.18F, 0.24F, 0.0F, -70.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    resetYPRmodifier();
    Aircraft.xyz[1] = (0.415F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.getGear());
    Aircraft.ypr[1] = paramFloat;
    hierMesh().chunkSetLocate("GearC2_D0", Aircraft.xyz, Aircraft.ypr);
  }
  public void moveWheelSink() {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.16F, 0.0F, 0.16F);
    hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
    hierMesh().chunkSetAngles("GearL6_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.16F, 0.0F, -32.0F), 0.0F);
    hierMesh().chunkSetAngles("GearL7_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.16F, 0.0F, -64.0F), 0.0F);
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.16F, 0.0F, 0.16F);
    hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
    hierMesh().chunkSetAngles("GearR6_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.16F, 0.0F, -32.0F), 0.0F);
    hierMesh().chunkSetAngles("GearR7_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.16F, 0.0F, -64.0F), 0.0F);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    for (int i = 1; i < 3; i++)
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -40.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 33:
      hitProp(0, paramInt2, paramActor);
      break;
    case 36:
      hitProp(1, paramInt2, paramActor);
      break;
    case 34:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(this, 0, 3);
      if (World.Rnd().nextInt(0, 99) >= 66) break; this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(this, 0, 1); break;
    case 37:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(this, 1, 3);
      if (World.Rnd().nextInt(0, 99) >= 66) break; this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(this, 1, 1);
    case 35:
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    int j;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxcontrol")) {
        i = paramString.charAt(9) - '0';
        switch (i) {
        case 1:
          if (getEnergyPastArmor(1.0F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.12F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
            debuggunnery("Evelator Controls Out..");
          }
          if (World.Rnd().nextFloat() >= 0.12F) break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2);
          debuggunnery("Rudder Controls Out.."); break;
        case 2:
        case 3:
          if ((getEnergyPastArmor(1.0F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.25F)) break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0);
          debuggunnery("Ailerons Controls Out..");
        }

      }

      if (paramString.startsWith("xxeng")) {
        i = paramString.charAt(5) - '1';
        if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(1.7F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 140000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, i);
              debuggunnery("*** Engine" + i + " Crank Case Hit - Engine Stucks..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 2);
              debuggunnery("*** Engine" + i + " Crank Case Hit - Engine Damaged..");
            }
          }
          else if (World.Rnd().nextFloat() < 0.04F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setCyliderKnockOut(paramShot.initiator, 1);
          } else {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setReadyness(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getReadyness() - 0.02F);
            debuggunnery("*** Engine" + i + " Crank Case Hit - Readyness Reduced to " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getReadyness() + "..");
          }

          getEnergyPastArmor(12.0F, paramShot);
        }
        if (paramString.endsWith("cyls")) {
          if ((getEnergyPastArmor(0.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylindersRatio() * 0.9878F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 19000.0F)));
            debuggunnery("*** Engine" + i + " Cylinders Hit, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 48000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 2);
              debuggunnery("*** Engine Cylinders Hit - Engine Fires..");
            }
          }
          getEnergyPastArmor(25.0F, paramShot);
        }
        if (paramString.endsWith("supc")) {
          if (getEnergyPastArmor(0.05F, paramShot) > 0.0F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setKillCompressor(paramShot.initiator);
          }
          getEnergyPastArmor(2.0F, paramShot);
        }
        if (paramString.endsWith("gear")) {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F) {
            if ((Aircraft.Pd.jdField_y_of_type_Double > 0.0D) && (Aircraft.Pd.jdField_z_of_type_Double < 0.1889999955892563D) && (World.Rnd().nextFloat(0.0F, 16000.0F) < paramShot.power)) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setMagnetoKnockOut(paramShot.initiator, 0);
            }
            if ((Aircraft.Pd.jdField_y_of_type_Double < 0.0D) && (Aircraft.Pd.jdField_z_of_type_Double < 0.1889999955892563D) && (World.Rnd().nextFloat(0.0F, 16000.0F) < paramShot.power)) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setMagnetoKnockOut(paramShot.initiator, 1);
            }
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, i, 4);
            }
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, i, 0);
            }
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, i, 6);
            }
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, i, 1);
            }
          }
          getEnergyPastArmor(2.0F, paramShot);
        }
        if ((paramString.endsWith("oil1")) && 
          (getEnergyPastArmor(4.21F, paramShot) > 0.0F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, i);
          getEnergyPastArmor(0.42F, paramShot);
        }

        return;
      }
      if (paramString.startsWith("xxfuel")) {
        j = paramString.charAt(6) - '0';
        switch (j) {
        case 1:
        default:
          if (World.Rnd().nextFloat() < 0.33F) {
            hitBone("xxfuel2", paramShot, paramPoint3d);
          }
          if (World.Rnd().nextFloat() < 0.33F) {
            hitBone("xxfuel3", paramShot, paramPoint3d);
          }
          if (World.Rnd().nextFloat() < 0.33F) {
            hitBone("xxfuel5", paramShot, paramPoint3d);
          }
          if (World.Rnd().nextFloat() < 0.33F) {
            hitBone("xxfuel6", paramShot, paramPoint3d);
          }
          return;
        case 2:
          i = 1;
          break;
        case 3:
          i = World.Rnd().nextInt(0, 1);
          break;
        case 4:
          i = 0;
          break;
        case 5:
          i = 2;
          break;
        case 6:
          i = World.Rnd().nextInt(2, 3);
          break;
        case 7:
          i = 3;
        }

        if (getEnergyPastArmor(0.6F, paramShot) > 0.0F) {
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[i] == 0) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 2);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetTankState(paramShot.initiator, i, 2);
          }
          if ((World.Rnd().nextFloat() < 0.02F) || ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.8F))) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 2);
          }
        }
        return;
      }
      if (paramString.startsWith("xxgun")) {
        i = paramString.charAt(5) - '1';
        if ((getEnergyPastArmor(6.8F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, i);
        }
        getEnergyPastArmor(22.700001F, paramShot);
        return;
      }
      if (paramString.startsWith("xxlock")) {
        if (paramString.startsWith("xxlockal")) {
          if (getEnergyPastArmor(4.35F, paramShot) > 0.0F) {
            debuggunnery("*** AroneL Lock Damaged..");
            nextDMGLevels(1, 2, "AroneL_D0", paramShot.initiator);
          }
        } else if (paramString.startsWith("xxlockar")) {
          if (getEnergyPastArmor(4.35F, paramShot) > 0.0F) {
            debuggunnery("*** AroneR Lock Damaged..");
            nextDMGLevels(1, 2, "AroneR_D0", paramShot.initiator);
          }
        } else if (paramString.startsWith("xxlocksl")) {
          if (getEnergyPastArmor(4.32F, paramShot) > 0.0F) {
            debuggunnery("*** VatorL Lock Damaged..");
            nextDMGLevels(1, 2, "VatorL_D0", paramShot.initiator);
          }
        } else if (paramString.startsWith("xxlocksr")) {
          if (getEnergyPastArmor(4.32F, paramShot) > 0.0F) {
            debuggunnery("*** VatorR Lock Damaged..");
            nextDMGLevels(1, 2, "VatorR_D0", paramShot.initiator);
          }
        } else if ((paramString.startsWith("xxlockk")) && 
          (getEnergyPastArmor(4.32F, paramShot) > 0.0F)) {
          debuggunnery("*** Rudder1 Lock Damaged..");
          nextDMGLevels(1, 2, "Rudder1_D0", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxpar")) {
        if ((paramString.startsWith("xxpark")) && 
          (chunkDamageVisible("Keel1") > 1) && (getEnergyPastArmor(12.699999809265137D / (1.000100016593933D - Math.abs(Aircraft.v1.jdField_y_of_type_Double)), paramShot) > 0.0F)) {
          debuggunnery("*** Keel1 Spars Damaged..");
          nextDMGLevels(1, 2, "Keel1_D2", paramShot.initiator);
        }

        if ((paramString.startsWith("xxparli")) && 
          (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(12.800000190734863D / (1.000100016593933D - Math.abs(Aircraft.v1.jdField_y_of_type_Double)), paramShot) > 0.0F)) {
          debuggunnery("*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxparri")) && 
          (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(12.800000190734863D / (1.000100016593933D - Math.abs(Aircraft.v1.jdField_y_of_type_Double)), paramShot) > 0.0F)) {
          debuggunnery("*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxparlm")) && 
          (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(12.800000190734863D / (1.000100016593933D - Math.abs(Aircraft.v1.jdField_y_of_type_Double)), paramShot) > 0.0F)) {
          debuggunnery("*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxparrm")) && 
          (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(12.800000190734863D / (1.000100016593933D - Math.abs(Aircraft.v1.jdField_y_of_type_Double)), paramShot) > 0.0F)) {
          debuggunnery("*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxparlo")) && 
          (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(12.800000190734863D / (1.000100016593933D - Math.abs(Aircraft.v1.jdField_y_of_type_Double)), paramShot) > 0.0F)) {
          debuggunnery("*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxparro")) && 
          (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(12.800000190734863D / (1.000100016593933D - Math.abs(Aircraft.v1.jdField_y_of_type_Double)), paramShot) > 0.0F)) {
          debuggunnery("*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxparrs")) && 
          (chunkDamageVisible("StabL") > 1) && (getEnergyPastArmor(12.800000190734863D / (1.000100016593933D - Math.abs(Aircraft.v1.jdField_y_of_type_Double)), paramShot) > 0.0F)) {
          debuggunnery("*** StabL Spars Damaged..");
          nextDMGLevels(1, 2, "StabL_D2", paramShot.initiator);
        }

        if ((paramString.startsWith("xxparls")) && 
          (chunkDamageVisible("StabR") > 1) && (getEnergyPastArmor(12.800000190734863D / (1.000100016593933D - Math.abs(Aircraft.v1.jdField_y_of_type_Double)), paramShot) > 0.0F)) {
          debuggunnery("*** StabR Spars Damaged..");
          nextDMGLevels(1, 2, "StabR_D2", paramShot.initiator);
        }

        return;
      }
      return;
    }

    if (paramString.startsWith("xcf")) {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
    }
    else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel")) {
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xrudder")) {
      if (chunkDamageVisible("Rudder1") < 1)
        hitChunk("Rudder1", paramShot);
    }
    else if (paramString.startsWith("xstabl")) {
      hitChunk("StabL", paramShot);
    } else if (paramString.startsWith("xstabr")) {
      hitChunk("StabR", paramShot);
    } else if (paramString.startsWith("xvatorl")) {
      if (chunkDamageVisible("VatorL") < 1)
        hitChunk("VatorL", paramShot);
    }
    else if (paramString.startsWith("xvatorr")) {
      if (chunkDamageVisible("VatorR") < 1)
        hitChunk("VatorR", paramShot);
    }
    else if (paramString.startsWith("xwinglin")) {
      if (chunkDamageVisible("WingLIn") < 3)
        hitChunk("WingLIn", paramShot);
    }
    else if (paramString.startsWith("xwingrin")) {
      if (chunkDamageVisible("WingRIn") < 3)
        hitChunk("WingRIn", paramShot);
    }
    else if (paramString.startsWith("xwinglmid")) {
      if (chunkDamageVisible("WingLMid") < 3)
        hitChunk("WingLMid", paramShot);
    }
    else if (paramString.startsWith("xwingrmid")) {
      if (chunkDamageVisible("WingRMid") < 3)
        hitChunk("WingRMid", paramShot);
    }
    else if (paramString.startsWith("xwinglout")) {
      if (chunkDamageVisible("WingLOut") < 3)
        hitChunk("WingLOut", paramShot);
    }
    else if (paramString.startsWith("xwingrout")) {
      if (chunkDamageVisible("WingROut") < 3)
        hitChunk("WingROut", paramShot);
    }
    else if (paramString.startsWith("xaronel")) {
      if (chunkDamageVisible("AroneL") < 1)
        hitChunk("AroneL", paramShot);
    }
    else if (paramString.startsWith("xaroner")) {
      if (chunkDamageVisible("AroneR") < 1)
        hitChunk("AroneR", paramShot);
    }
    else if (paramString.startsWith("xengine1")) {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xengine2")) {
      if (chunkDamageVisible("Engine2") < 2)
        hitChunk("Engine2", paramShot);
    }
    else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
      i = 0;

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

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
    }
  }

  static
  {
    Class localClass = KI_46.class;
    Property.set(localClass, "originCountry", PaintScheme.countryJapan);
  }
}