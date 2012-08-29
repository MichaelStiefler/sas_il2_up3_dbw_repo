package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public abstract class FW_200 extends Scheme4
  implements TypeBomber, TypeTransport
{
  private static final float[] anglesL6 = { 0.0F, 13.5F, 23.0F, 29.5F, 34.5F, 39.0F, 44.0F, 50.0F, 58.5F, 69.5F, 84.0F };

  private static final float[] anglesL7 = { 0.0F, 3.0F, 5.0F, 6.0F, 7.0F, 8.0F, 10.0F, 12.5F, 15.5F, 18.5F, 22.0F };

  protected void moveFlap(float paramFloat)
  {
    float f = -45.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap04_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap05_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap06_D0", 0.0F, f, 0.0F);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i = 0; int j = 0;

    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxbomb")) {
        if ((World.Rnd().nextFloat() < 0.001F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0].haveBullets())) {
          Aircraft.debugprintln(this, "*** Bomb Payload Detonates..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, 10);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 1, 10);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 2, 10);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 3, 10);
          nextDMGLevels(3, 2, "CF_D0", paramShot.initiator);
        }
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
        case 2:
        case 3:
          if ((getEnergyPastArmor(1.0F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.25F)) break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0); break;
        case 4:
        case 5:
          if ((getEnergyPastArmor(1.0F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.25F)) break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1); break;
        case 6:
          if ((getEnergyPastArmor(1.0F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.25F)) break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2);
        }

        return;
      }
      if (paramString.startsWith("xxspar")) {
        if ((paramString.startsWith("xxsparli")) && 
          (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(19.6F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.125F)) {
          Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparri")) && 
          (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(19.6F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.125F)) {
          Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlm")) && 
          (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(16.799999F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.125F)) {
          Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparrm")) && 
          (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(16.799999F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.125F)) {
          Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlo")) && 
          (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(16.6F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.125F)) {
          Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparro")) && 
          (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(16.6F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.125F)) {
          Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxspark")) && 
          (chunkDamageVisible("Keel1") > 1) && (getEnergyPastArmor(16.6F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** Keel1 Spars Damaged..");
          nextDMGLevels(1, 2, "Keel1_D" + chunkDamageVisible("Keel1"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparn")) && 
          (chunkDamageVisible("Nose") > 1) && (getEnergyPastArmor(37.200001F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** Nose Spars Damaged..");
          nextDMGLevels(1, 2, "Nose_D" + chunkDamageVisible("Nose"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparsl")) && 
          (World.Rnd().nextFloat(0.0F, 0.115F) < paramShot.mass) && (getEnergyPastArmor(16.9F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** StabL Spar Damaged..");
          nextDMGLevels(1, 2, "StabL_D" + chunkDamageVisible("StabL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparsr")) && 
          (World.Rnd().nextFloat(0.0F, 0.115F) < paramShot.mass) && (getEnergyPastArmor(16.9F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** StabR Spar Damaged..");
          nextDMGLevels(1, 2, "StabR_D" + chunkDamageVisible("StabR"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxspart")) && 
          (getEnergyPastArmor(46.599998F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** Tail Spar Damaged..");
          nextDMGLevels(1, 2, "Tail1_D" + chunkDamageVisible("Tail1"), paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxlock")) {
        debuggunnery("Lock Construction: Hit..");
        if ((paramString.startsWith("xxlockr")) && 
          (getEnergyPastArmor(6.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvl")) && 
          (getEnergyPastArmor(6.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: VatorL Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvr")) && 
          (getEnergyPastArmor(6.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: VatorR Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockal")) && 
          (getEnergyPastArmor(6.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: AroneL Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockar")) && 
          (getEnergyPastArmor(6.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: AroneR Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxeng")) {
        i = paramString.charAt(5) - '1';
        if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(0.2F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 140000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, i);
              Aircraft.debugprintln(this, "*** Engine (" + i + ") Crank Case Hit - Engine Stucks..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 85000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 2);
              Aircraft.debugprintln(this, "*** Engine (" + i + ") Crank Case Hit - Engine Damaged..");
            }
          }
          else if (World.Rnd().nextFloat() < 0.005F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setCyliderKnockOut(paramShot.initiator, 1);
          } else {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setReadyness(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getReadyness() - 0.00082F);
            Aircraft.debugprintln(this, "*** Engine (" + i + ") Crank Case Hit - Readyness Reduced to " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getReadyness() + "..");
          }

          getEnergyPastArmor(12.0F, paramShot);
        }
        if (paramString.endsWith("cyls")) {
          if ((getEnergyPastArmor(5.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylindersRatio() * 0.75F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 19000.0F)));
            Aircraft.debugprintln(this, "*** Engine (" + i + ") Cylinders Hit, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 48000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 2);
              Aircraft.debugprintln(this, "*** Engine (" + i + ") Cylinders Hit - Engine Fires..");
            }
          }
          getEnergyPastArmor(25.0F, paramShot);
        }
        if ((paramString.endsWith("prop")) && 
          (getEnergyPastArmor(0.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
          if (World.Rnd().nextFloat() < 0.5F)
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setKillPropAngleDevice(paramShot.initiator);
          else {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setKillPropAngleDeviceSpeeds(paramShot.initiator);
          }
          getEnergyPastArmor(15.1F, paramShot);
          Aircraft.debugprintln(this, "*** Engine (" + i + ") Module: Prop Governor Fails..");
        }

        if ((paramString.endsWith("oil1")) && 
          (getEnergyPastArmor(0.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setOilState(paramShot.initiator, i, 1);
          Aircraft.debugprintln(this, "*** Engine (" + i + ") Module: Oil Filter Pierced..");
        }

        if ((paramString.endsWith("supc")) && 
          (getEnergyPastArmor(0.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setKillCompressor(paramShot.initiator);
          Aircraft.debugprintln(this, "*** Engine (" + i + ") Module: Compressor Stops..");
          getEnergyPastArmor(2.6F, paramShot);
        }

        return;
      }
      if (paramString.startsWith("xxtank")) {
        j = paramString.charAt(6) - '0';
        if (paramString.length() > 7) {
          j = 10 + (paramString.charAt(7) - '0');
        }
        switch (j) { case 1:
        case 2:
        case 3:
        case 4:
        case 5:
          i = World.Rnd().nextInt(1, 2);
          break;
        case 6:
          i = 1;
          break;
        case 7:
        case 8:
        case 9:
          i = 0;
          break;
        case 10:
          i = 2;
          break;
        case 11:
        case 12:
        case 13:
          i = 3;
        }

        if (getEnergyPastArmor(0.03F, paramShot) > 0.0F) {
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[i] == 0) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 2);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetTankState(paramShot.initiator, i, 2);
          }
          if (paramShot.powerType == 3) {
            if (paramShot.power < 14100.0F) {
              if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[i] < 4) && (World.Rnd().nextFloat() < 0.125F))
                this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 1);
            }
            else {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, World.Rnd().nextInt(0, (int)(paramShot.power / 28200.0F)));
            }
          }
        }
        return;
      }
      return;
    }

    if (paramString.startsWith("xcf")) {
      if (chunkDamageVisible("CF") < 3) {
        hitChunk("CF", paramShot);
      }
      return;
    }if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3) {
        hitChunk("Tail1", paramShot);
      }
      return;
    }if (paramString.startsWith("xkeel")) {
      if (chunkDamageVisible("Keel1") < 2) {
        hitChunk("Keel1", paramShot);
      }
      return;
    }if (paramString.startsWith("xrudder")) {
      if (chunkDamageVisible("Rudder1") < 2) {
        hitChunk("Rudder1", paramShot);
      }
      return;
    }if (paramString.startsWith("xstabl")) {
      if (chunkDamageVisible("StabL") < 2) {
        hitChunk("StabL", paramShot);
      }
      return;
    }if (paramString.startsWith("xstabr")) {
      if (chunkDamageVisible("StabR") < 2) {
        hitChunk("StabR", paramShot);
      }
      return;
    }if (paramString.startsWith("xvatorl")) {
      if (chunkDamageVisible("VatorL") < 1) {
        hitChunk("VatorL", paramShot);
      }
      return;
    }if (paramString.startsWith("xvatorr")) {
      if (chunkDamageVisible("VatorR") < 1) {
        hitChunk("VatorR", paramShot);
      }
      return;
    }if (paramString.startsWith("xwinglin")) {
      if (chunkDamageVisible("WingLIn") < 3) {
        hitChunk("WingLIn", paramShot);
      }
      return;
    }if (paramString.startsWith("xwingrin")) {
      if (chunkDamageVisible("WingRIn") < 3) {
        hitChunk("WingRIn", paramShot);
      }
      return;
    }if (paramString.startsWith("xwinglmid")) {
      if (chunkDamageVisible("WingLMid") < 3) {
        hitChunk("WingLMid", paramShot);
      }
      return;
    }if (paramString.startsWith("xwingrmid")) {
      if (chunkDamageVisible("WingRMid") < 3) {
        hitChunk("WingRMid", paramShot);
      }
      return;
    }if (paramString.startsWith("xwinglout")) {
      if (chunkDamageVisible("WingLOut") < 3) {
        hitChunk("WingLOut", paramShot);
      }
      return;
    }if (paramString.startsWith("xwingrout")) {
      if (chunkDamageVisible("WingROut") < 3) {
        hitChunk("WingROut", paramShot);
      }
      return;
    }if (paramString.startsWith("xaronel")) {
      if (chunkDamageVisible("AroneL") < 2) {
        hitChunk("AroneL", paramShot);
      }
      return;
    }if (paramString.startsWith("xaroner")) {
      if (chunkDamageVisible("AroneR") < 2) {
        hitChunk("AroneR", paramShot);
      }
      return;
    }if (paramString.startsWith("xengine1")) {
      if (chunkDamageVisible("Engine1") < 2) {
        hitChunk("Engine1", paramShot);
      }
      return;
    }if (paramString.startsWith("xengine2")) {
      if (chunkDamageVisible("Engine2") < 2) {
        hitChunk("Engine2", paramShot);
      }
      return;
    }if (paramString.startsWith("xengine3")) {
      if (chunkDamageVisible("Engine3") < 2) {
        hitChunk("Engine3", paramShot);
      }
      return;
    }if (paramString.startsWith("xengine4")) {
      if (chunkDamageVisible("Engine4") < 2) {
        hitChunk("Engine4", paramShot);
      }
      return;
    }if (paramString.startsWith("xgear")) {
      if (World.Rnd().nextFloat() < 0.05F) {
        Aircraft.debugprintln(this, "*** Gear Hydro Failed..");
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.setHydroOperable(false);
      }
      return;
    }

    if (paramString.startsWith("xnose")) {
      if (chunkDamageVisible("Nose") < 2) {
        hitChunk("Nose", paramShot);
      }
      return;
    }if (paramString.startsWith("xoil")) {
      i = paramString.charAt(4) - '1';
      if ((getEnergyPastArmor(0.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setOilState(paramShot.initiator, i, 1);
        Aircraft.debugprintln(this, "*** Engine (" + i + ") Module: Oil Filter Pierced (E)..");
      }
      return;
    }if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
      i = 0;
      int k;
      if (paramString.endsWith("a")) {
        i = 1;
        k = paramString.charAt(6) - '1';
      } else if (paramString.endsWith("b")) {
        i = 2;
        k = paramString.charAt(6) - '1';
      } else {
        k = paramString.charAt(5) - '1';
      }
      hitFlesh(k, paramShot, i);
      return;
    }
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    setExplosion(paramExplosion);
    if ((paramExplosion.chunkName != null) && (paramExplosion.power > 0.0F)) {
      if (paramExplosion.chunkName.equals("Tail1_D3")) {
        return;
      }
      if (paramExplosion.chunkName.equals("WingLIn_D3")) {
        return;
      }
      if (paramExplosion.chunkName.equals("WingRIn_D3")) {
        return;
      }
      if (paramExplosion.chunkName.equals("WingLMid_D3")) {
        return;
      }
      if (paramExplosion.chunkName.equals("WingRMid_D3")) {
        return;
      }
      if (paramExplosion.chunkName.equals("WingLOut_D3")) {
        return;
      }
      if (paramExplosion.chunkName.equals("WingROut_D3")) {
        return;
      }
    }
    super.msgExplosion(paramExplosion);
  }

  private static float floatindex(float paramFloat, float[] paramArrayOfFloat)
  {
    int i = (int)paramFloat;
    if (i >= paramArrayOfFloat.length - 1) return paramArrayOfFloat[(paramArrayOfFloat.length - 1)];
    if (i < 0) return paramArrayOfFloat[0];
    if (i == 0) {
      if (paramFloat > 0.0F) return paramArrayOfFloat[0] + paramFloat * (paramArrayOfFloat[1] - paramArrayOfFloat[0]);
      return paramArrayOfFloat[0];
    }
    return paramArrayOfFloat[i] + paramFloat % i * (paramArrayOfFloat[(i + 1)] - paramArrayOfFloat[i]);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, -45.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -43.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -61.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, 166.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, 0.0F, -floatindex(paramFloat * 10.0F, anglesL6));
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, 0.0F, -floatindex(paramFloat * 10.0F, anglesL7));

    paramHierMesh.chunkSetAngles("GearL8_D0", 0.0F, 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.07F, 0.0F, -80.0F));
    paramHierMesh.chunkSetAngles("GearL9_D0", 0.0F, 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.07F, 0.0F, 80.0F));
    paramHierMesh.chunkSetAngles("GearL10_D0", 0.0F, 0.0F, paramFloat < 0.5F ? Aircraft.cvt(paramFloat, 0.01F, 0.07F, 0.0F, -80.0F) : Aircraft.cvt(paramFloat, 0.93F, 0.99F, 80.0F, 0.0F));
    paramHierMesh.chunkSetAngles("GearL11_D0", 0.0F, 0.0F, paramFloat < 0.5F ? Aircraft.cvt(paramFloat, 0.01F, 0.07F, 0.0F, 80.0F) : Aircraft.cvt(paramFloat, 0.93F, 0.99F, -80.0F, 0.0F));

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -43.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -61.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, 166.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, 0.0F, -floatindex(paramFloat * 10.0F, anglesL6));
    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, 0.0F, -floatindex(paramFloat * 10.0F, anglesL7));

    paramHierMesh.chunkSetAngles("GearR8_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.07F, 0.0F, 80.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR9_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.07F, 0.0F, -80.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR10_D0", 0.0F, paramFloat < 0.5F ? Aircraft.cvt(paramFloat, 0.01F, 0.07F, 0.0F, 80.0F) : Aircraft.cvt(paramFloat, 0.93F, 0.99F, 80.0F, 0.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR11_D0", 0.0F, paramFloat < 0.5F ? Aircraft.cvt(paramFloat, 0.01F, 0.07F, 0.0F, -80.0F) : Aircraft.cvt(paramFloat, 0.93F, 0.99F, -80.0F, 0.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveWheelSink() {
    float f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.4615F, 0.0F, -26.5F);
    hierMesh().chunkSetAngles("GearL3_D0", 0.0F, f, 0.0F);
    f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.4615F, 0.0F, 21.5F);
    hierMesh().chunkSetAngles("GearL12_D0", 0.0F, f, 0.0F);
    f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.4615F, 0.0F, 3.5F);
    hierMesh().chunkSetAngles("GearL13_D0", 0.0F, f, 0.0F);

    f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.4615F, 0.0F, -26.5F);
    hierMesh().chunkSetAngles("GearR3_D0", 0.0F, f, 0.0F);
    f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.4615F, 0.0F, 21.5F);
    hierMesh().chunkSetAngles("GearR12_D0", 0.0F, f, 0.0F);
    f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.4615F, 0.0F, 3.5F);
    hierMesh().chunkSetAngles("GearR13_D0", 0.0F, f, 0.0F);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 33:
      hitProp(1, paramInt2, paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].setEngineStuck(paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramActor, 1, World.Rnd().nextInt(0, 9));
    case 34:
      hitProp(0, paramInt2, paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setEngineStuck(paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramActor, 0, World.Rnd().nextInt(2, 8));
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramActor, 1, World.Rnd().nextInt(0, 5));
    case 35:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramActor, 0, World.Rnd().nextInt(0, 4));
      break;
    case 36:
      hitProp(2, paramInt2, paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[2].setEngineStuck(paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramActor, 2, World.Rnd().nextInt(0, 9));
    case 37:
      hitProp(3, paramInt2, paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[3].setEngineStuck(paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramActor, 2, World.Rnd().nextInt(0, 5));
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramActor, 3, World.Rnd().nextInt(2, 8));
    case 38:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramActor, 3, World.Rnd().nextInt(0, 4));
      break;
    case 13:
      killPilot(this, 0);
      killPilot(this, 1);
      hierMesh().chunkVisible("Pilot1_D1", false);
      hierMesh().chunkVisible("Pilot2_D1", false);
      break;
    case 19:
      killPilot(this, 5);
      killPilot(this, 6);
      hierMesh().chunkVisible("Pilot6_D1", false);
      hierMesh().chunkVisible("Pilot7_D1", false);
    case 14:
    case 15:
    case 16:
    case 17:
    case 18:
    case 20:
    case 21:
    case 22:
    case 23:
    case 24:
    case 25:
    case 26:
    case 27:
    case 28:
    case 29:
    case 30:
    case 31:
    case 32: } return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay01_D0", 0.0F, 0.0F, -95.0F * paramFloat);
    hierMesh().chunkSetAngles("Bay02_D0", 0.0F, 0.0F, 95.0F * paramFloat);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    for (int i = 1; i < 8; i++)
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      break;
    case 1:
      break;
    case 2:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
      break;
    case 3:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
      break;
    case 4:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[5].bIsOperable = false;
      break;
    case 5:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = false;
      break;
    case 6:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[3].bIsOperable = false;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[4].bIsOperable = false;
    }
  }

  public void doMurderPilot(int paramInt) {
    if (paramInt > 6) {
      return;
    }
    hierMesh().chunkVisible("Pilot" + (paramInt + 1) + "_D0", false);
    hierMesh().chunkVisible("HMask" + (paramInt + 1) + "_D0", false);
    hierMesh().chunkVisible("Pilot" + (paramInt + 1) + "_D1", true);
    if (paramInt == 0)
      hierMesh().chunkVisible("Head" + (paramInt + 1) + "_D0", false);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f2 < -3.3F) { f2 = -3.3F; bool = false; }
      if (f2 <= 50.0F) break; f2 = 50.0F; bool = false; break;
    case 1:
      if (f1 < -40.0F) { f1 = -40.0F; bool = false; }
      if (f1 > 40.0F) { f1 = 40.0F; bool = false; }
      if (f2 < -60.0F) { f2 = -60.0F; bool = false; }
      if (f2 <= 15.0F) break; f2 = 15.0F; bool = false; break;
    case 2:
      if (f1 < -50.0F) { f1 = -50.0F; bool = false; }
      if (f1 > 50.0F) { f1 = 50.0F; bool = false; }
      if (f2 < -1.0F) { f2 = -1.0F; bool = false; }
      if (f2 <= 50.0F) break; f2 = 50.0F; bool = false; break;
    case 3:
      if (f1 < -35.0F) { f1 = -35.0F; bool = false; }
      if (f1 > 75.0F) { f1 = 75.0F; bool = false; }
      if (f2 < -40.0F) { f2 = -40.0F; bool = false; }
      if (f2 <= 40.0F) break; f2 = 40.0F; bool = false; break;
    case 4:
      if (f1 < -75.0F) { f1 = -75.0F; bool = false; }
      if (f1 > 35.0F) { f1 = 35.0F; bool = false; }
      if (f2 < -40.0F) { f2 = -40.0F; bool = false; }
      if (f2 <= 40.0F) break; f2 = 40.0F; bool = false; break;
    case 5:
      if (f1 < -30.0F) { f1 = -30.0F; bool = false; }
      if (f1 > 30.0F) { f1 = 30.0F; bool = false; }
      if (f2 < -50.0F) { f2 = -50.0F; bool = false; }
      if (f2 <= 6.0F) break; f2 = 6.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  static
  {
    Class localClass = FW_200.class;
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
  }
}