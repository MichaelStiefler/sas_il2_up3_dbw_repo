package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.weapons.Bomb;
import com.maddox.il2.objects.weapons.FuelTank;
import com.maddox.rts.Property;

public abstract class P_47 extends Scheme1
  implements TypeFighter, TypeBNZFighter, TypeStormovik
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = Math.max(-paramFloat * 980.0F, -98.0F);
    paramHierMesh.chunkSetAngles("GearC99_D0", 0.0F, -55.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, -135.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, -135.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -85.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -86.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -85.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -86.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("LampGear_D0", 0.0F, -85.0F * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, 0.0F, paramFloat);
  }
  public void moveWheelSink() {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.2F, 0.0F, 0.2F);
    hierMesh().chunkSetLocate("GearL25_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.2F, 0.0F, 0.2F);
    hierMesh().chunkSetLocate("GearR25_D0", Aircraft.xyz, Aircraft.ypr);
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsAboutToBailout) break;
      if (hierMesh().isChunkVisible("Blister1_D0")) {
        hierMesh().chunkVisible("Gore1_D0", true);
      }
      hierMesh().chunkVisible("Gore2_D0", true);
    }
  }

  protected void moveFlap(float paramFloat)
  {
    float f = 45.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
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
        debuggunnery("Armor: Hit..");
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(World.Rnd().nextFloat(20.0F, 40.0F) / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x2);
          if (paramShot.power <= 0.0F)
            doRicochetBack(paramShot);
        }
        else if (paramString.endsWith("p2")) {
          getEnergyPastArmor(12.520000457763672D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
        } else if (paramString.endsWith("p3")) {
          getEnergyPastArmor(12.560000419616699D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
        } else if (paramString.endsWith("p4")) {
          getEnergyPastArmor(12.560000419616699D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
        } else if (paramString.endsWith("f1")) {
          getEnergyPastArmor(2.099999904632568D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
          if (paramShot.powerType == 3) {
            paramShot.powerType = 2;
          }
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
          if ((World.Rnd().nextFloat() >= 0.5F) || (getEnergyPastArmor(0.1F, paramShot) <= 0.0F)) break;
          debuggunnery("Controls: Ailerones Controls: Out..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0); break;
        case 5:
        case 6:
          if (getEnergyPastArmor(0.002F, paramShot) <= 0.0F) break;
          debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2); break;
        case 7:
          if (getEnergyPastArmor(4.2F, paramShot) <= 0.0F) break;
          debuggunnery("Controls: Elevator Controls: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
        }

        return;
      }
      if (paramString.startsWith("xxeng1")) {
        debuggunnery("Engine Module: Hit..");
        if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(World.Rnd().nextFloat(0.2F, 0.55F), paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 280000.0F) {
              debuggunnery("Engine Module: Engine Crank Case Hit - Engine Stucks..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, 0);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 100000.0F) {
              debuggunnery("Engine Module: Engine Crank Case Hit - Engine Damaged..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 2);
            }
          }
          getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 24.0F), paramShot);
        }
        if (paramString.endsWith("cyls")) {
          if ((getEnergyPastArmor(0.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersRatio() * 0.66F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 32200.0F)));
            debuggunnery("Engine Module: Cylinders Hit, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 1000000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 2);
              debuggunnery("Engine Module: Cylinders Hit - Engine Fires..");
            }
          }
          getEnergyPastArmor(25.0F, paramShot);
        }
        if (paramString.endsWith("eqpt")) {
          if (getEnergyPastArmor(0.5F, paramShot) > 0.0F) {
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
        if (paramString.endsWith("gear")) {
          if ((getEnergyPastArmor(4.6F, paramShot) > 0.0F) && 
            (World.Rnd().nextFloat() < 0.5F)) {
            debuggunnery("Engine Module: Bullet Jams Reductor Gear..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setEngineStuck(paramShot.initiator);
          }

          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 12.445654F), paramShot);
        }
        if (paramString.startsWith("xxeng1mag")) {
          i = paramString.charAt(9) - '1';
          debuggunnery("Engine Module: Magneto " + i + " Destroyed..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setMagnetoKnockOut(paramShot.initiator, i);
        }
        if (paramString.endsWith("oil1")) {
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.25F, paramShot) > 0.0F))
            debuggunnery("Engine Module: Oil Radiator Hit..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
        }
        if ((paramString.endsWith("prop")) && 
          (getEnergyPastArmor(0.42F, paramShot) > 0.0F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setKillPropAngleDevice(paramShot.initiator);
        }

        if ((paramString.startsWith("xxeng1typ")) && 
          (getEnergyPastArmor(0.42F, paramShot) > 0.0F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setKillPropAngleDeviceSpeeds(paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxhyd")) {
        if (getEnergyPastArmor(World.Rnd().nextFloat(0.25F, 12.39F), paramShot) > 0.0F) {
          debuggunnery("Hydro System: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setInternalDamage(paramShot.initiator, 0);
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
      if (paramString.startsWith("xxmgun0")) {
        i = paramString.charAt(7) - '1';
        if (getEnergyPastArmor(0.5F, paramShot) > 0.0F) {
          debuggunnery("Armament: Machine Gun (" + i + ") Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, i);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxoil")) {
        if ((getEnergyPastArmor(0.25F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
          getEnergyPastArmor(0.22F, paramShot);
          debuggunnery("Engine Module: Oil Tank Pierced..");
        }
        return;
      }
      if (paramString.startsWith("xxpipe")) {
        if (World.Rnd().nextFloat() < 0.0049F) {
          debuggunnery("Engine Module: Turbine Feed Cut, Turbine Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 0);
        }
        return;
      }
      if (paramString.startsWith("xxsupc")) {
        if (getEnergyPastArmor(World.Rnd().nextFloat(0.2F, 12.0F), paramShot) > 0.0F) {
          debuggunnery("Engine Module: Turbine Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 0);
        }
        return;
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if (getEnergyPastArmor(1.8F, paramShot) > 0.0F) {
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[i] == 0) {
            debuggunnery("Fuel Tank (" + i + "): Pierced..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 1);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetTankState(paramShot.initiator, i, 1);
          }
          if ((World.Rnd().nextFloat() < 0.04F) || ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.6F))) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 2);
            debuggunnery("Fuel Tank (" + i + "): Hit..");
          }
        }
      }
      return;
    }

    if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit"))) {
      hitChunk("CF", paramShot);
      if (paramString.startsWith("xcockpit")) {
        if ((paramPoint3d.jdField_z_of_type_Double > 0.716000020503998D) && (World.Rnd().nextFloat() < 0.25F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x1);
        }
        if ((paramPoint3d.jdField_z_of_type_Double > 0.716000020503998D) && (World.Rnd().nextFloat() < 0.25F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x8);
        }
        if ((paramPoint3d.jdField_x_of_type_Double > -0.5D) && 
          (World.Rnd().nextFloat() < 0.2F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x40);
        }

        if ((paramPoint3d.jdField_x_of_type_Double > -0.5D) && 
          (World.Rnd().nextFloat() < 0.2F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x20);
        }
      }

      return;
    }if (paramString.startsWith("xeng")) {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
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
    else if (paramString.startsWith("xstab")) {
      if ((paramString.startsWith("xstabl")) && 
        (chunkDamageVisible("StabL") < 2)) {
        hitChunk("StabL", paramShot);
      }

      if ((paramString.startsWith("xstabr")) && 
        (chunkDamageVisible("StabR") < 1)) {
        hitChunk("StabR", paramShot);
      }
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
    else if (paramString.startsWith("xtank1")) {
      if (getEnergyPastArmor(0.05F, paramShot) > 0.0F) {
        if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[3] == 0) {
          debuggunnery("Fuel Tank (E): Pierced..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 3, 2);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetTankState(paramShot.initiator, 3, 2);
        }

        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 3, 2);
        debuggunnery("Fuel Tank (E): Hit..");
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

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 35:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 0);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 2);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 0);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 2);
      break;
    case 38:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 1);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 3);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 1);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 3);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void update(float paramFloat)
  {
    float f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, -5.0F);
    for (int i = 1; i < 13; i++) {
      hierMesh().chunkSetAngles("Cowflap" + i + "_D0", 0.0F, f, 0.0F);
    }
    f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator(), 0.0F, 1.0F, 25.0F, 0.0F);

    hierMesh().chunkSetAngles("Oilrflap2l_D0", 0.0F, f, 0.0F);

    hierMesh().chunkSetAngles("Oilrflap2r_D0", 0.0F, -f, 0.0F);
    super.update(paramFloat);
  }

  public void replicateDropFuelTanks()
  {
    super.replicateDropFuelTanks();
    hierMesh().chunkVisible("ETank_D0", false);
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetTankState(null, 3, 0);
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    Object[] arrayOfObject = this.pos.getBaseAttached();
    if (arrayOfObject == null) return;
    for (int i = 0; i < arrayOfObject.length; i++) {
      if ((arrayOfObject[i] instanceof FuelTank)) {
        hierMesh().chunkVisible("ETank_D0", true);
        hierMesh().chunkVisible("Rack_D0", true);
      }
    }
    for (int j = 0; j < arrayOfObject.length; j++)
      if ((arrayOfObject[j] instanceof Bomb)) {
        hierMesh().chunkVisible("Rack_D0", true);
        return;
      }
  }

  static
  {
    Class localClass = P_47.class;
    Property.set(localClass, "originCountry", PaintScheme.countryUSA);
  }
}