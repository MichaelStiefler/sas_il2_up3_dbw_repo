package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public abstract class N1K extends Scheme1
  implements TypeFighter
{
  protected float flapps = 0.0F;

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

  public void update(float paramFloat)
  {
    super.update(paramFloat);

    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl < 0.2F) {
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getOverload() > 3.5F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl < 0.05F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH() > 120.0F)) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 0.19F;
        if (this == World.getPlayerAircraft())
          HUD.log("FlapsCombat");
      }
      else if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getOverload() < 2.5F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl > 0.185F)) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 0.0F;
        if (this == World.getPlayerAircraft()) {
          HUD.log("FlapsRaised");
        }
      }
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.forceFlaps(0.05F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl + 0.95F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap());
    }

    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.BrakeControl > 0.4F)
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.BrakeControl = 0.4F;
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
    float tmp33_32 = (Aircraft.xyz[2] = Aircraft.ypr[0] = Aircraft.ypr[1] = Aircraft.ypr[2] = 0.0F); Aircraft.xyz[1] = tmp33_32; Aircraft.xyz[0] = tmp33_32;

    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.75F, 0.95F, 0.0F, -60.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.95F, 0.0F, -85.0F), 0.0F);
    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.4F, 0.75F, 0.0F, -0.205F);
    paramHierMesh.chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.75F, 0.94F, 0.0F, -0.05F);
    paramHierMesh.chunkSetLocate("GearL4_D0", Aircraft.xyz, Aircraft.ypr);
    if (paramFloat > 0.75F)
      paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.75F, 0.95F, -45.0F, -65.0F), 0.0F);
    else {
      paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.4F, 0.75F, 0.0F, -45.0F), 0.0F);
    }
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.95F, 0.0F, -130.0F), 0.0F);
    if (paramFloat > 0.75F)
      paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, Aircraft.cvt(paramFloat, 0.725F, 0.95F, -86.0F, -133.0F), 0.0F);
    else {
      paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.725F, 0.0F, -86.0F), 0.0F);
    }
    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.05F, 0.95F, 0.0F, -0.1F);
    paramHierMesh.chunkSetLocate("GearL8_D0", Aircraft.xyz, Aircraft.ypr);
    paramHierMesh.chunkSetAngles("GearL9_D0", 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.115F, 0.0F, -85.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL10_D0", 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.85F, 0.0F, -85.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.95F, 0.0F, -85.0F), 0.0F);
    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.4F, 0.75F, 0.0F, -0.205F);
    paramHierMesh.chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.75F, 0.94F, 0.0F, -0.05F);
    paramHierMesh.chunkSetLocate("GearR4_D0", Aircraft.xyz, Aircraft.ypr);
    if (paramFloat > 0.75F)
      paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.75F, 0.95F, -45.0F, -65.0F), 0.0F);
    else {
      paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.4F, 0.75F, 0.0F, -45.0F), 0.0F);
    }
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.95F, 0.0F, -130.0F), 0.0F);
    if (paramFloat > 0.75F)
      paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, Aircraft.cvt(paramFloat, 0.725F, 0.95F, -86.0F, -133.0F), 0.0F);
    else {
      paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.725F, 0.0F, -86.0F), 0.0F);
    }
    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.05F, 0.95F, 0.0F, -0.1F);
    paramHierMesh.chunkSetLocate("GearR8_D0", Aircraft.xyz, Aircraft.ypr);
    paramHierMesh.chunkSetAngles("GearR9_D0", 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.115F, 0.0F, -85.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR10_D0", 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.85F, 0.0F, -85.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) {
    moveGear(hierMesh(), paramFloat);
  }
  public void moveWheelSink() {
  }
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }

  protected void moveAileron(float paramFloat)
  {
    if (paramFloat > 0.0F) {
      hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -16.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -28.5F * paramFloat, 0.0F);
    } else {
      hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -16.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -28.5F * paramFloat, 0.0F);
    }
  }

  protected void moveElevator(float paramFloat)
  {
    if (paramFloat > 0.0F) {
      hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -35.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -35.0F * paramFloat, 0.0F);
    } else {
      hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -24.5F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -24.5F * paramFloat, 0.0F);
    }
  }

  protected void moveFlap(float paramFloat)
  {
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -30.0F * paramFloat, 0.0F);
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -33.0F * paramFloat, 0.0F);
  }

  public void moveCockpitDoor(float paramFloat)
  {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.1F, 0.99F, 0.0F, -0.61F);
    hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null)) Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      setDoorSnd(paramFloat);
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("p1")) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x2);
          if (getEnergyPastArmor(World.Rnd().nextFloat(32.5F, 65.0F) / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) < 0.0F)
            doRicochetBack(paramShot);
        }
        else if (paramString.endsWith("p2")) {
          getEnergyPastArmor(13.130000114440918D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
        } else if (paramString.endsWith("p3")) {
          getEnergyPastArmor(World.Rnd().nextFloat(8.7F, 9.81F) / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcanon0")) {
        i = paramString.charAt(8) - '1';
        if (getEnergyPastArmor(6.29F, paramShot) > 0.0F) {
          debuggunnery("Armament: Cannon (" + i + ") Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, i);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        debuggunnery("Controls: Hit..");
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
        case 2:
        case 3:
        case 4:
          if ((getEnergyPastArmor(0.99F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.175F)) break;
          debuggunnery("Controls: Ailerones Controls: Out..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0); break;
        case 5:
        case 6:
          if ((getEnergyPastArmor(0.22F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.275F)) break;
          debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2); break;
        case 7:
          if ((getEnergyPastArmor(4.2F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.175F)) break;
          debuggunnery("Controls: Elevator Controls: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1); break;
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
        if (paramString.endsWith("gear")) {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 4);
            }
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 0);
            }
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 6);
            }
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 1);
            }
          }
          getEnergyPastArmor(2.0F, paramShot);
        }
        if ((paramString.endsWith("prop")) && 
          (getEnergyPastArmor(0.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setKillPropAngleDevice(paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxlock")) {
        debuggunnery("Lock Construction: Hit..");
        if ((paramString.startsWith("xxlockr")) && 
          (getEnergyPastArmor(2.5F, paramShot) > 0.0F)) {
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
      if (paramString.startsWith("xxmgun0")) {
        i = paramString.charAt(7) - '1';
        if (getEnergyPastArmor(0.75F, paramShot) > 0.0F) {
          debuggunnery("Armament: Machine Gun (" + i + ") Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, i);
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
      if (paramString.startsWith("xxspar")) {
        debuggunnery("Spar Construction: Hit..");
        if ((paramString.startsWith("xxsparli")) && 
          (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLIn Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparri")) && 
          (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingRIn Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlm")) && 
          (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLMid Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparrm")) && 
          (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingRMid Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlo")) && 
          (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLOut Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparro")) && 
          (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingROut Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxspart")) && 
          (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.86F / (float)Math.sqrt(Aircraft.v1.jdField_y_of_type_Double * Aircraft.v1.jdField_y_of_type_Double + Aircraft.v1.jdField_z_of_type_Double * Aircraft.v1.jdField_z_of_type_Double), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          debuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if (i > 3) {
          return;
        }
        if ((getEnergyPastArmor(0.8F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.45F)) {
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[i] == 0) {
            debuggunnery("Fuel Tank (" + i + "): Pierced..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 1);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetTankState(paramShot.initiator, i, 1);
          }
          if ((World.Rnd().nextFloat() < 0.008F) || ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.6F))) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 1);
            debuggunnery("Fuel Tank (" + i + "): Hit..");
          }
        }
        return;
      }
      return;
    }

    if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcock"))) {
      hitChunk("CF", paramShot);

      return;
    }if (paramString.startsWith("xeng")) {
      if ((chunkDamageVisible("Engine1") < 2) && (getEnergyPastArmor(1.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 3000.0F) < paramShot.power))
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xtail")) {
      if ((getEnergyPastArmor(2.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 5000.0F) < paramShot.power)) hitChunk("Tail1", paramShot); 
    }
    else if (paramString.startsWith("xkeel")) {
      if ((getEnergyPastArmor(1.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 3000.0F) < paramShot.power)) hitChunk("Keel1", paramShot); 
    }
    else if (paramString.startsWith("xrudder")) {
      if ((chunkDamageVisible("Rudder1") < 1) && (getEnergyPastArmor(2.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 3000.0F) < paramShot.power) && 
        (getEnergyPastArmor(1.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 3000.0F) < paramShot.power)) hitChunk("Rudder1", paramShot);
    }
    else if (paramString.startsWith("xstab")) {
      if ((paramString.startsWith("xstabl")) && 
        (getEnergyPastArmor(1.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 3000.0F) < paramShot.power)) hitChunk("StabL", paramShot);

      if ((paramString.startsWith("xstabr")) && 
        (getEnergyPastArmor(1.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 3000.0F) < paramShot.power)) hitChunk("StabR", paramShot);
    }
    else if (paramString.startsWith("xvator")) {
      if ((paramString.startsWith("xvatorl")) && 
        (chunkDamageVisible("VatorL") < 1) && (getEnergyPastArmor(1.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 3000.0F) < paramShot.power)) {
        hitChunk("VatorL", paramShot);
      }

      if ((paramString.startsWith("xvatorr")) && 
        (chunkDamageVisible("VatorR") < 1) && (getEnergyPastArmor(1.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 3000.0F) < paramShot.power)) {
        hitChunk("VatorR", paramShot);
      }
    }
    else if (paramString.startsWith("xwing")) {
      if ((paramString.startsWith("xwinglin")) && 
        (getEnergyPastArmor(2.5F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 5000.0F) < paramShot.power)) hitChunk("WingLIn", paramShot);

      if ((paramString.startsWith("xwingrin")) && 
        (getEnergyPastArmor(2.5F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 5000.0F) < paramShot.power)) hitChunk("WingRIn", paramShot);

      if ((paramString.startsWith("xwinglmid")) && 
        (getEnergyPastArmor(2.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 4000.0F) < paramShot.power)) hitChunk("WingLMid", paramShot);

      if ((paramString.startsWith("xwingrmid")) && 
        (getEnergyPastArmor(2.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 4000.0F) < paramShot.power)) hitChunk("WingRMid", paramShot);

      if ((paramString.startsWith("xwinglout")) && 
        (getEnergyPastArmor(1.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 3000.0F) < paramShot.power)) hitChunk("WingLOut", paramShot);

      if ((paramString.startsWith("xwingrout")) && 
        (getEnergyPastArmor(1.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 3000.0F) < paramShot.power)) hitChunk("WingROut", paramShot);
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
    Class localClass = N1K.class;
    Property.set(localClass, "originCountry", PaintScheme.countryJapan);
  }
}