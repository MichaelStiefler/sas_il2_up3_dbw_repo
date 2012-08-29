package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public abstract class MS400X extends Scheme1
  implements TypeFighter
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -85.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 0.0F, -87.0F * paramFloat);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -34.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, 180.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 85.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 0.0F, 87.0F * paramFloat);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -34.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, 180.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("Antenna_D0", 0.0F, -90.0F * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat);
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

  protected void moveFlap(float paramFloat)
  {
    float f = -60.0F * paramFloat;
    hierMesh().chunkSetAngles("FlapL_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("FlapR_D0", 0.0F, f, 0.0F);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(14.2F / (1.0E-005F + (float)Math.abs(Aircraft.v1.jdField_x_of_type_Double)), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        if (paramString.endsWith("1")) {
          if (getEnergyPastArmor(2.2F, paramShot) > 0.0F) {
            debuggunnery("Controls: Control Column: Hit, Controls Destroyed..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0);
          }
        } else if ((paramString.endsWith("2")) || (paramString.endsWith("3"))) {
          if (getEnergyPastArmor(0.002F, paramShot) > 0.0F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
            debuggunnery("Controls: Elevator Controls: Disabled / Strings Broken..");
          }
        } else if (((paramString.endsWith("4")) || (paramString.endsWith("5"))) && 
          (World.Rnd().nextFloat() < 0.3F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0);
          Aircraft.debugprintln(this, "*** Ailerones Controls Out..");
        }

        return;
      }
      if (paramString.startsWith("xxeng1")) {
        if ((paramString.endsWith("prop")) && 
          (getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 0.4F), paramShot) > 0.0F)) {
          debuggunnery("Engine Module: Prop Governor Failed..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setKillPropAngleDevice(paramShot.initiator);
        }

        if ((paramString.endsWith("gear")) && 
          (getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 1.1F), paramShot) > 0.0F)) {
          debuggunnery("Engine Module: Prop Governor Damaged..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setKillPropAngleDeviceSpeeds(paramShot.initiator);
        }

        if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 6.8F), paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 200000.0F) {
              debuggunnery("Engine Module: Crank Case Hit - Engine Stucks..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, 0);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
              debuggunnery("Engine Module: Crank Case Hit - Engine Damaged..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 2);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 28000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setCyliderKnockOut(paramShot.initiator, 1);
              debuggunnery("Engine Module: Crank Case Hit - Cylinder Feed Out, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylinders() + " Left..");
            }
            if (World.Rnd().nextFloat() < 0.08F) {
              debuggunnery("Engine Module: Crank Case Hit - Ball Bearing Jammed - Engine Stuck..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setEngineStuck(paramShot.initiator);
            }
            debuggunnery("Engine Module: Crank Case Hit - Readyness Reduced to " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness() + "..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setReadyness(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
          }
          if (World.Rnd().nextFloat() < 0.01F) {
            debuggunnery("Engine Module: Crank Case Hit - Engine Stalled..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setEngineStops(paramShot.initiator);
          }
          if (World.Rnd().nextFloat() < 0.01F) {
            debuggunnery("Engine Module: Crank Case Hit - Fuel Feed Hit - Engine Flamed..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 10);
          }
          getEnergyPastArmor(16.0F, paramShot);
        }
        if (((paramString.endsWith("cyl1")) || (paramString.endsWith("cyl2"))) && 
          (getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 2.542F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersRatio() * 1.72F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
          debuggunnery("Engine Module: Cylinders Hit, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylinders() + " Left..");
          if (World.Rnd().nextFloat() < 0.01F) {
            debuggunnery("Engine Module: Cylinder Case Broken - Engine Stuck..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setEngineStuck(paramShot.initiator);
          }
          if (World.Rnd().nextFloat() < paramShot.power / 24000.0F) {
            debuggunnery("Engine Module: Cylinders Hit - Engine Fires..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 3);
          }
          getEnergyPastArmor(World.Rnd().nextFloat(3.0F, 46.700001F), paramShot);
        }

        if ((paramString.endsWith("supc")) && 
          (getEnergyPastArmor(0.05F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.89F)) {
          debuggunnery("Engine Module: Supercharger Out..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setKillCompressor(paramShot.initiator);
        }

        if ((paramString.endsWith("eqpt")) && 
          (getEnergyPastArmor(World.Rnd().nextFloat(0.001F, 0.2F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.89F) && 
          (World.Rnd().nextFloat() < 0.11F)) {
          debuggunnery("Engine Module: Compressor Feed Out..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setKillCompressor(paramShot.initiator);
        }

        if (paramString.startsWith("xxeng1mag")) {
          i = paramString.charAt(9) - '1';
          debuggunnery("Engine Module: Magneto " + i + " Destroyed..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setMagnetoKnockOut(paramShot.initiator, i);
        }
        if ((paramString.endsWith("oil1")) && 
          (getEnergyPastArmor(1.27F, paramShot) > 0.0F)) {
          debuggunnery("Engine Module: Oil Radiator Hit..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
        }

        return;
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 1);
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.11F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 2);
          }
        }
        return;
      }
      if (paramString.startsWith("xxcannon")) {
        if (World.Rnd().nextFloat() < 0.1F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 0);
        }
        return;
      }
      if (paramString.startsWith("xxmgun")) {
        i = paramString.charAt(7) - '1';
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, i);
        return;
      }
      if (paramString.startsWith("xxammo")) {
        i = paramString.charAt(7) - '0';
        if (World.Rnd().nextFloat(0.0F, 20000.0F) < paramShot.power) {
          switch (i) {
          case 1:
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 0);
            break;
          case 2:
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 1);
          }
        }

        return;
      }

      if (paramString.startsWith("xxspar")) {
        debuggunnery("Spar Construction: Hit..");
        if ((paramString.startsWith("xxsparli")) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparri")) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlm")) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparrm")) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlo")) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparro")) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        return;
      }

      if (paramString.startsWith("xxlock")) {
        debuggunnery("Lock Construction: Hit..");
        if ((paramString.startsWith("xxlockr")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("Rudder Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvl")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("VatorL Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvr")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("VatorR Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
        }

        if ((paramString.endsWith("al")) && 
          (getEnergyPastArmor(0.35F, paramShot) > 0.0F)) {
          debuggunnery("AroneL Lock Damaged..");
          nextDMGLevels(1, 2, "AroneL_D0", paramShot.initiator);
        }

        if ((paramString.endsWith("ar")) && 
          (getEnergyPastArmor(0.35F, paramShot) > 0.0F)) {
          debuggunnery("AroneR Lock Damaged..");
          nextDMGLevels(1, 2, "AroneR_D0", paramShot.initiator);
        }

        return;
      }
      return;
    }

    if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit"))) {
      hitChunk("CF", paramShot);
    }
    if (!paramString.startsWith("xcockpit"))
    {
      if (paramString.startsWith("xeng")) {
        hitChunk("Engine1", paramShot);
      } else if (paramString.startsWith("xtail")) {
        if (chunkDamageVisible("Tail1") < 3)
          hitChunk("Tail1", paramShot);
      }
      else if (paramString.startsWith("xkeel")) {
        hitChunk("Keel1", paramShot);
      } else if (paramString.startsWith("xrudder")) {
        if (chunkDamageVisible("Rudder1") < 1)
          hitChunk("Rudder1", paramShot);
      }
      else if (paramString.startsWith("xstabl")) {
        hitChunk("StabL", paramShot);
      } else if (paramString.startsWith("xstabr")) {
        hitChunk("StabR", paramShot);
      } else if (paramString.startsWith("xvator")) {
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
          (chunkDamageVisible("AroneL") < 1)) {
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
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 9:
    case 33:
      hierMesh().chunkVisible("GearL3_D0", false);
      break;
    case 10:
    case 36:
      hierMesh().chunkVisible("GearR3_D0", false);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  static
  {
    Class localClass = MS400X.class;
    Property.set(localClass, "originCountry", PaintScheme.countryFrance);
  }
}