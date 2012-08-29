package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.rts.Property;

public abstract class B_29X extends Scheme4
  implements TypeTransport
{
  private float[] flapps = { 0.0F, 0.0F, 0.0F, 0.0F };

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i = 0;

    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxammo0")) {
        i = paramString.charAt(7) - '0';
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.15F)) {
          switch (i) {
          case 1:
            if (World.Rnd().nextFloat() < 0.347F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(12, 0);
            }
            if (World.Rnd().nextFloat() >= 0.347F) break;
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(12, 1); break;
          case 2:
            if (World.Rnd().nextFloat() < 0.347F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(13, 0);
            }
            if (World.Rnd().nextFloat() >= 0.347F) break;
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(13, 1); break;
          case 4:
            if (World.Rnd().nextFloat() < 0.223F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(10, 0);
            }
            if (World.Rnd().nextFloat() < 0.223F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(10, 1);
            }
            if (World.Rnd().nextFloat() < 0.223F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(10, 2);
            }
            if (World.Rnd().nextFloat() >= 0.223F) break;
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(10, 3); break;
          case 5:
            if (World.Rnd().nextFloat() < 0.347F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(11, 0);
            }
            if (World.Rnd().nextFloat() >= 0.347F) break;
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(11, 1);
          case 3:
          }
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
            (World.Rnd().nextFloat() >= 0.5F)) break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0);
          Aircraft.debugprintln(this, "*** Aileron Controls Out.."); break;
        case 4:
          if ((getEnergyPastArmor(1.0F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.5F)) break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1); break;
        case 5:
        case 6:
          if ((getEnergyPastArmor(1.0F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.5F)) break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2);
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
            if (World.Rnd().nextFloat() < paramShot.power / 18000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 2);
              Aircraft.debugprintln(this, "*** Engine (" + i + ") Cylinders Hit - Engine Fires..");
            }
          }
          getEnergyPastArmor(25.0F, paramShot);
        }
        if (paramString.endsWith("mag1")) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setMagnetoKnockOut(paramShot.initiator, 0);
          Aircraft.debugprintln(this, "*** Engine (" + i + ") Module: Magneto #0 Destroyed..");
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

        if ((paramString.endsWith("oil")) && 
          (getEnergyPastArmor(0.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setOilState(paramShot.initiator, i, 1);
          Aircraft.debugprintln(this, "*** Engine (" + i + ") Module: Oil Filter Pierced..");
        }

        return;
      }
      if (paramString.startsWith("xxspar")) {
        if ((paramString.startsWith("xxspare1")) && 
          (chunkDamageVisible("Engine1") > 2) && (getEnergyPastArmor(19.6F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.125F)) {
          Aircraft.debugprintln(this, "*** Engine1 Spars Damaged..");
          nextDMGLevels(1, 2, "Engine1_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxspare2")) && 
          (chunkDamageVisible("Engine2") > 2) && (getEnergyPastArmor(19.6F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.125F)) {
          Aircraft.debugprintln(this, "*** Engine2 Spars Damaged..");
          nextDMGLevels(1, 2, "Engine2_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxspare3")) && 
          (chunkDamageVisible("Engine3") > 2) && (getEnergyPastArmor(19.6F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.125F)) {
          Aircraft.debugprintln(this, "*** Engine3 Spars Damaged..");
          nextDMGLevels(1, 2, "Engine3_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxspare4")) && 
          (chunkDamageVisible("Engine4") > 2) && (getEnergyPastArmor(19.6F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.125F)) {
          Aircraft.debugprintln(this, "*** Engine4 Spars Damaged..");
          nextDMGLevels(1, 2, "Engine4_D3", paramShot.initiator);
        }

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
          (chunkDamageVisible("Keel1") > 1) && (getEnergyPastArmor(16.6F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.125F)) {
          Aircraft.debugprintln(this, "*** Keel1 Spars Damaged..");
          nextDMGLevels(1, 2, "Keel1_D" + chunkDamageVisible("Keel1"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparsl")) && 
          (chunkDamageVisible("StabL") > 2) && (getEnergyPastArmor(16.6F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.125F)) {
          Aircraft.debugprintln(this, "*** StabL: Spars Damaged..");
          nextDMGLevels(1, 2, "StabL_D" + chunkDamageVisible("StabL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparsr")) && 
          (chunkDamageVisible("StabR") > 2) && (getEnergyPastArmor(16.6F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.125F)) {
          Aircraft.debugprintln(this, "*** StabR: Spars Damaged..");
          nextDMGLevels(1, 2, "StabR_D" + chunkDamageVisible("StabR"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxspart")) && 
          (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(16.6F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.125F));
        return;
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        i /= 2;
        if (getEnergyPastArmor(0.06F, paramShot) > 0.0F) {
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[i] == 0) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 1);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetTankState(paramShot.initiator, i, 1);
          }
          if (paramShot.powerType == 3) {
            if (paramShot.power < 16100.0F) {
              if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[i] < 4) && (World.Rnd().nextFloat() < 0.21F))
                this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 1);
            }
            else
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, World.Rnd().nextInt(1, 1 + (int)(paramShot.power / 16100.0F)));
          }
          else if (paramShot.power > 16100.0F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, World.Rnd().nextInt(1, 1 + (int)(paramShot.power / 16100.0F)));
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
      if (chunkDamageVisible("VatorL") < 2) {
        hitChunk("VatorL", paramShot);
      }
      return;
    }if (paramString.startsWith("xvatorr")) {
      if (chunkDamageVisible("VatorR") < 2) {
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
    }if (paramString.startsWith("xturret"))
    {
      return;
    }

    if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
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

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 91.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.06F, 0.0F, 93.0F));
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.06F, 0.0F, 93.0F));
    paramHierMesh.chunkSetAngles("GearC6_D0", 0.0F, 54.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC7_D0", 0.0F, 108.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC8_D0", 0.0F, 59.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -81.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.12F, 0.0F, 67.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL8_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.12F, 0.0F, -67.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL9_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.12F, 0.0F, 84.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -81.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.12F, 0.0F, -67.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR8_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.12F, 0.0F, 67.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR9_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.12F, 0.0F, -84.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat);
  }

  public void moveWheelSink()
  {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[2], 0.0F, 0.325F, 0.0F, 0.325F);
    hierMesh().chunkSetLocate("GearC3_D0", Aircraft.xyz, Aircraft.ypr);
    resetYPRmodifier();
    Aircraft.xyz[0] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.325F, 0.0F, -0.325F);
    hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
    resetYPRmodifier();
    Aircraft.xyz[0] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.325F, 0.0F, -0.325F);
    hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
  }

  protected void moveRudder(float paramFloat)
  {
    super.moveRudder(paramFloat);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.getGear() > 0.9F)
      hierMesh().chunkSetAngles("GearC33_D0", 0.0F, -30.0F * paramFloat, 0.0F);
  }

  protected void moveFlap(float paramFloat)
  {
    resetYPRmodifier();
    Aircraft.xyz[0] = (-0.4436F * paramFloat);
    Aircraft.xyz[2] = (0.063F * paramFloat);
    Aircraft.ypr[1] = (30.0F * paramFloat);
    hierMesh().chunkSetLocate("Flap01_D0", Aircraft.xyz, Aircraft.ypr);
    hierMesh().chunkSetLocate("Flap02_D0", Aircraft.xyz, Aircraft.ypr);
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
    case 25:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
      return false;
    case 26:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
      return false;
    case 27:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = false;
      return false;
    case 28:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[3].bIsOperable = false;
      return false;
    case 29:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[4].bIsOperable = false;
      return false;
    case 30:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[5].bIsOperable = false;
      return false;
    case 19:
      killPilot(this, 5);
      killPilot(this, 6);
      killPilot(this, 7);
      killPilot(this, 8);
    case 20:
    case 21:
    case 22:
    case 23:
    case 24:
    case 31:
    case 32: } return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay01_D0", 0.0F, 0.0F, -90.0F * paramFloat);
    hierMesh().chunkSetAngles("Bay02_D0", 0.0F, 0.0F, -90.0F * paramFloat);
    hierMesh().chunkSetAngles("Bay03_D0", 0.0F, 0.0F, -90.0F * paramFloat);
    hierMesh().chunkSetAngles("Bay04_D0", 0.0F, 0.0F, -90.0F * paramFloat);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean) {
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[0] > 3) && 
        (World.Rnd().nextFloat() < 0.0023F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 0, 1);

      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[1] > 3) && 
        (World.Rnd().nextFloat() < 0.0023F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 1, 1);

      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[2] > 3) && 
        (World.Rnd().nextFloat() < 0.0023F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 2, 1);

      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[3] > 3) && 
        (World.Rnd().nextFloat() < 0.0023F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 3, 1);

      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.04F)) nextDMGLevel(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[0] + "0", 0, this);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.04F)) nextDMGLevel(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[1] + "0", 0, this);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[2] > 4) && (World.Rnd().nextFloat() < 0.04F)) nextDMGLevel(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[2] + "0", 0, this);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[3] > 4) && (World.Rnd().nextFloat() < 0.04F)) nextDMGLevel(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[3] + "0", 0, this);

      if ((!(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof RealFlightModel)) || (!((RealFlightModel)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).isRealMode())) {
        for (i = 0; i < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum(); i++) {
          if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[i] <= 3) || (World.Rnd().nextFloat() >= 0.2F)) continue; this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setExtinguisherFire();
        }
      }
    }

    for (int i = 1; i < 7; i++)
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    for (int i = 0; i < 4; i++) {
      float f = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getControlRadiator();
      if (Math.abs(this.flapps[i] - f) > 0.01F) {
        this.flapps[i] = f;
        hierMesh().chunkSetAngles("Water" + (i + 1) + "_D0", 0.0F, -10.0F * f, 0.0F);
        for (int j = 0; j < 8; j++)
          hierMesh().chunkSetAngles("Water" + (i * 8 + j + 5) + "_D0", 0.0F, 0.0F, -20.0F * f);
      }
    }
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      break;
    case 1:
      break;
    case 2:
      break;
    case 3:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
      break;
    case 4:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = false;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[3].bIsOperable = false;
      break;
    case 5:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[4].bIsOperable = false;
    }
  }

  public void doMurderPilot(int paramInt) {
    hierMesh().chunkVisible("Pilot" + (paramInt + 1) + "_D0", false);
    hierMesh().chunkVisible("HMask" + (paramInt + 1) + "_D0", false);
    hierMesh().chunkVisible("Pilot" + (paramInt + 1) + "_D1", true);
    hierMesh().chunkVisible("Head" + (paramInt + 1) + "_D0", false);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f2 < -1.0F) { f2 = -1.0F; bool = false; }
      if (f2 <= 80.0F) break; f2 = 80.0F; bool = false; break;
    case 1:
      if (f2 < -80.0F) { f2 = -80.0F; bool = false; }
      if (f2 <= 1.0F) break; f2 = 1.0F; bool = false; break;
    case 2:
      if (f2 < -1.0F) { f2 = -1.0F; bool = false; }
      if (f2 <= 80.0F) break; f2 = 80.0F; bool = false; break;
    case 3:
      if (f2 < -80.0F) { f2 = -80.0F; bool = false; }
      if (f2 <= 1.0F) break; f2 = 1.0F; bool = false; break;
    case 4:
      if (f1 < -35.0F) { f1 = -35.0F; bool = false; }
      if (f1 > 35.0F) { f1 = 35.0F; bool = false; }
      if (f2 < -45.0F) { f2 = -45.0F; bool = false; }
      if (f2 <= 45.0F) break; f2 = 45.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  static
  {
    Class localClass = B_29X.class;
    Property.set(localClass, "originCountry", PaintScheme.countryUSA);
  }
}