package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public abstract class KI_43 extends Scheme1
  implements TypeFighter
{
  protected void moveFan(float paramFloat)
  {
    super.moveFan(paramFloat);
    hierMesh().chunkSetAngles(Aircraft.Props[0][0], 0.0F, this.propPos[0], 0.0F);
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

  public void moveCockpitDoor(float paramFloat)
  {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.59F);
    hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null)) Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      setDoorSnd(paramFloat);
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

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -85.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -85.0F * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveWheelSink() {
    resetYPRmodifier();
    float f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.245F, 0.0F, 0.245F);
    Aircraft.xyz[1] = f;
    hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
    f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.245F, 0.0F, 0.245F);
    Aircraft.xyz[1] = f;
    hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
  }
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
  }

  protected void moveFlap(float paramFloat)
  {
    resetYPRmodifier();
    Aircraft.xyz[0] = (-0.26F * paramFloat);
    Aircraft.ypr[0] = (-6.0F * paramFloat);
    Aircraft.ypr[1] = (16.0F * paramFloat);
    Aircraft.ypr[2] = (1.0F * paramFloat);
    hierMesh().chunkSetLocate("Flap01_D0", Aircraft.xyz, Aircraft.ypr);
    resetYPRmodifier();
    Aircraft.xyz[0] = (0.26F * paramFloat);
    Aircraft.ypr[0] = (6.0F * paramFloat);
    Aircraft.ypr[1] = (-16.0F * paramFloat);
    Aircraft.ypr[2] = (1.0F * paramFloat);
    hierMesh().chunkSetLocate("Flap02_D0", Aircraft.xyz, Aircraft.ypr);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxammo")) {
        if ((paramString.startsWith("xxammol")) && 
          (World.Rnd().nextFloat() < 0.25F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 0);
        }

        if ((paramString.startsWith("xxammor")) && 
          (World.Rnd().nextFloat() < 0.25F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 1);
        }

        getEnergyPastArmor(16.0F, paramShot);
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        debuggunnery("Controls: Hit..");
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
        case 2:
          if ((getEnergyPastArmor(0.99F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.175F)) break;
          debuggunnery("Controls: Ailerones Controls: Out..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0); break;
        case 3:
          if ((getEnergyPastArmor(0.99F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.675F)) break;
          if (World.Rnd().nextFloat() < 0.25F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          }
          if (World.Rnd().nextFloat() < 0.25F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 6);
          }
          if (World.Rnd().nextFloat() >= 0.25F) break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 7); break;
        case 4:
          if ((getEnergyPastArmor(0.22F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.02F)) break;
          debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2); break;
        case 5:
          if ((getEnergyPastArmor(4.2F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.11F)) break;
          debuggunnery("Controls: Elevator Controls: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
        }

        return;
      }
      if (paramString.startsWith("xxctank")) {
        if (World.Rnd().nextFloat() < 0.5F) {
          hitBone("xxtank1", paramShot, paramPoint3d);
        }
        if (World.Rnd().nextFloat() < 0.5F) {
          hitBone("xxtank2", paramShot, paramPoint3d);
        }
        if (World.Rnd().nextFloat() < 0.5F) {
          hitBone("xxtank3", paramShot, paramPoint3d);
        }
        if (World.Rnd().nextFloat() < 0.5F) {
          hitBone("xxtank4", paramShot, paramPoint3d);
        }
        return;
      }
      if (paramString.startsWith("xxeng1")) {
        if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(0.2F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 140000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, 0);
              Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Stucks..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 85000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 2);
              Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Damaged..");
            }
          }
          else if (World.Rnd().nextFloat() < 0.01F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setCyliderKnockOut(paramShot.initiator, 1);
          } else {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setReadyness(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness() - 0.002F);
            Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Readyness Reduced to " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness() + "..");
          }

          getEnergyPastArmor(12.0F, paramShot);
        }
        if (paramString.endsWith("cyls")) {
          if ((getEnergyPastArmor(5.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersRatio() * 0.75F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 19000.0F)));
            Aircraft.debugprintln(this, "*** Engine Cylinders Hit, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 48000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 2);
              Aircraft.debugprintln(this, "*** Engine Cylinders Hit - Engine Fires..");
            }
          }
          getEnergyPastArmor(25.0F, paramShot);
        }
        if (paramString.endsWith("mag1")) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setMagnetoKnockOut(paramShot.initiator, 0);
          Aircraft.debugprintln(this, "*** Engine Module: Magneto #0 Destroyed..");
          getEnergyPastArmor(25.0F, paramShot);
        }
        if (paramString.endsWith("mag2")) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setMagnetoKnockOut(paramShot.initiator, 1);
          Aircraft.debugprintln(this, "*** Engine Module: Magneto #1 Destroyed..");
          getEnergyPastArmor(25.0F, paramShot);
        }

        if (paramString.endsWith("oil1")) {
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.25F, paramShot) > 0.0F))
            debuggunnery("Engine Module: Oil Radiator Hit..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
        }
        return;
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

        return;
      }
      if (paramString.startsWith("xxmgun01")) {
        if (getEnergyPastArmor(0.75F, paramShot) > 0.0F) {
          debuggunnery("Armament: Machine Gun 01 Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 0);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxmgun02")) {
        if (getEnergyPastArmor(0.75F, paramShot) > 0.0F) {
          debuggunnery("Armament: Machine Gun 02 Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 1);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxoil")) {
        if ((getEnergyPastArmor(0.25F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.125F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
          getEnergyPastArmor(0.22F, paramShot);
          debuggunnery("Engine Module: Oil Tank Pierced..");
        }
        return;
      }
      if (paramString.startsWith("xxpnm")) {
        if (getEnergyPastArmor(World.Rnd().nextFloat(0.25F, 12.39F), paramShot) > 0.0F) {
          debuggunnery("Pneumo System: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setInternalDamage(paramShot.initiator, 1);
        }
        return;
      }
      if (paramString.startsWith("xxpnm"))
      {
        getEnergyPastArmor(World.Rnd().nextFloat(0.25F, 12.39F), paramShot);
        return;
      }

      if (paramString.startsWith("xxspar")) {
        Aircraft.debugprintln(this, "*** Spar Construction: Hit..");
        if (((paramString.endsWith("li1")) || (paramString.endsWith("li2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(2.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("ri1")) || (paramString.endsWith("ri2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(2.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("lm1")) || (paramString.endsWith("lm2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(2.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("rm1")) || (paramString.endsWith("rm2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(2.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("lo1")) || (paramString.endsWith("lo2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("ro1")) || (paramString.endsWith("ro2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparsl")) && 
          (World.Rnd().nextFloat(0.0F, 0.115F) < paramShot.mass) && (getEnergyPastArmor(6.8F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** StabL Spar Damaged..");
          nextDMGLevels(1, 2, "StabL_D" + chunkDamageVisible("StabL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparsr")) && 
          (World.Rnd().nextFloat(0.0F, 0.115F) < paramShot.mass) && (getEnergyPastArmor(6.8F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** StabR Spar Damaged..");
          nextDMGLevels(1, 2, "StabR_D" + chunkDamageVisible("StabR"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxspark")) && 
          (World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(6.8F * World.Rnd().nextFloat(1.0F, 1.5F) / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** Keel Spars Damaged..");
          nextDMGLevels(1, 2, "Keel1_D" + chunkDamageVisible("Keel1"), paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.45F)) {
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[i] == 0) {
            debuggunnery("Fuel Tank (" + i + "): Pierced..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 1);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetTankState(paramShot.initiator, i, 1);
          }
          if ((World.Rnd().nextFloat() < 0.08F) || ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.5F))) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 4);
            debuggunnery("Fuel Tank (" + i + "): Hit..");
          }
        }
        return;
      }
      return;
    }

    if ((paramString.startsWith("xcf")) || (paramString.startsWith("xblister"))) {
      hitChunk("CF", paramShot);
      if (paramString.startsWith("xblister")) {
        if (paramPoint3d.jdField_x_of_type_Double < -0.721D) {
          if (paramPoint3d.jdField_y_of_type_Double > 0.198D)
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x4);
          else if (paramPoint3d.jdField_y_of_type_Double < -0.198D)
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x20);
          else
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x1);
        }
        else
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x2);
      }
      else {
        if ((paramPoint3d.jdField_x_of_type_Double > -0.5590000000000001D) && (paramPoint3d.jdField_x_of_type_Double < 0.396D) && (paramPoint3d.z > -0.135D)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x40);
        }
        if (World.Rnd().nextFloat() < 0.125F)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x8);
        else if (World.Rnd().nextFloat() < 0.125F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x10);
        }
      }
      return;
    }if (paramString.startsWith("xeng")) {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xtail")) {
      hitChunk("Tail1", paramShot);
    } else if (paramString.startsWith("xkeel")) {
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xrudder")) {
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
      if (World.Rnd().nextFloat() < 0.05F) {
        debuggunnery("Hydro System: Disabled..");
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setInternalDamage(paramShot.initiator, 0);
      }
      if ((World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 3.435F), paramShot) > 0.0F)) {
        debuggunnery("Undercarriage: Stuck..");
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setInternalDamage(paramShot.initiator, 3);
      }
    } else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
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

  static
  {
    Class localClass = KI_43.class;
    Property.set(localClass, "originCountry", PaintScheme.countryJapan);
  }
}