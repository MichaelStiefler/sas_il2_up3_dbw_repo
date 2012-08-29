package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public abstract class I_16FixedSkis extends Scheme1
  implements TypeFighter
{
  private float skiAngleL = 0.0F;
  private float skiAngleR = 0.0F;
  private float spring = 0.15F;
  private float wireRandomizer1 = 1.0F;
  private float wireRandomizer2 = 1.0F;
  private float wireRandomizer3 = 1.0F;
  private float wireRandomizer4 = 1.0F;

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        Aircraft.debugprintln(this, "*** Armor: Hit..");
        if (paramString.endsWith("p1"))
          getEnergyPastArmor(8.26F / (Math.abs((float)Aircraft.v1.jdField_x_of_type_Double) + 1.0E-005F), paramShot);
      }
      else
      {
        if (paramString.startsWith("xxcontrols")) {
          i = paramString.charAt(10) - '0';
          switch (i) {
          case 1:
          case 2:
            if (getEnergyPastArmor(1.5F, paramShot) <= 0.0F) break;
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0);
            Aircraft.debugprintln(this, "*** Aileron Controls: Control Crank Destroyed.."); break;
          case 3:
            if ((getEnergyPastArmor(8.6F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.75F))
              break;
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
            Aircraft.debugprintln(this, "*** Elevator Controls: Disabled.."); break;
          case 4:
          case 5:
            if ((getEnergyPastArmor(2.3F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.31F))
              break;
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2);
            Aircraft.debugprintln(this, "*** Rudder Controls: Disabled..");
          }

        }

        if (paramString.startsWith("xxspar")) {
          Aircraft.debugprintln(this, "*** Spar Construction: Hit..");

          if ((paramString.startsWith("xxspart")) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.5F / (float)Math.sqrt(Aircraft.v1.jdField_y_of_type_Double * Aircraft.v1.jdField_y_of_type_Double + Aircraft.v1.jdField_z_of_type_Double * Aircraft.v1.jdField_z_of_type_Double), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** Tail1 Spars Broken in Half..");

            nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
          }
          if ((paramString.startsWith("xxsparli")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");

            nextDMGLevels(1, 2, "WingLIn_D" + chunkDamageVisible("WingLIn"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxsparri")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");

            nextDMGLevels(1, 2, "WingRIn_D" + chunkDamageVisible("WingRIn"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxsparlm")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");

            nextDMGLevels(1, 2, "WingLMid_D" + chunkDamageVisible("WingLMid"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxsparrm")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");

            nextDMGLevels(1, 2, "WingRMid_D" + chunkDamageVisible("WingRMid"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxsparlo")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");

            nextDMGLevels(1, 2, "WingLOut_D" + chunkDamageVisible("WingLOut"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxsparro")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");

            nextDMGLevels(1, 2, "WingROut_D" + chunkDamageVisible("WingROut"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxstabl")) && (getEnergyPastArmor(16.200001F, paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** StabL Spars Damaged..");

            nextDMGLevels(1, 2, "StabL_D" + chunkDamageVisible("StabL"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxstabr")) && (getEnergyPastArmor(16.200001F, paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** StabR Spars Damaged..");

            nextDMGLevels(1, 2, "StabR_D" + chunkDamageVisible("StabR"), paramShot.initiator);
          }

        }

        if (paramString.startsWith("xxlock")) {
          Aircraft.debugprintln(this, "*** Lock Construction: Hit..");

          if ((paramString.startsWith("xxlockr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** Rudder Lock Shot Off..");

            nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxlockvl")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** VatorL Lock Shot Off..");

            nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxlockvr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** VatorR Lock Shot Off..");

            nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxlockal")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** AroneL Lock Shot Off..");

            nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxlockar")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
          {
            Aircraft.debugprintln(this, "*** AroneR Lock Shot Off..");

            nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), paramShot.initiator);
          }

        }

        if (paramString.startsWith("xxeng")) {
          Aircraft.debugprintln(this, "*** Engine Module: Hit..");
          if (paramString.endsWith("prop")) {
            if ((getEnergyPastArmor(0.45F / (Math.abs((float)Aircraft.v1.jdField_x_of_type_Double) + 1.0E-004F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.05F))
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 3);

              Aircraft.debugprintln(this, "*** Engine Module: Prop Governor Hit, Disabled..");
            }

          }
          else if (paramString.endsWith("case")) {
            if (getEnergyPastArmor(5.1F, paramShot) > 0.0F) {
              if (World.Rnd().nextFloat() < paramShot.power / 175000.0F)
              {
                this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, 0);
                Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Crank Ball Bearing..");
              }

              if (World.Rnd().nextFloat() < paramShot.power / 50000.0F)
              {
                this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 2);
                Aircraft.debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness() + "..");
              }

              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setReadyness(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));

              Aircraft.debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness() + "..");
            }

            getEnergyPastArmor(22.5F, paramShot);
          } else if (paramString.startsWith("xxeng1cyls")) {
            if ((getEnergyPastArmor(World.Rnd().nextFloat(1.5F, 23.9F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersRatio() * 1.12F))
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));

              Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylinders() + " Left..");

              if (World.Rnd().nextFloat() < paramShot.power / 48000.0F)
              {
                this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 3);
                Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, Engine Fires..");
              }

              if (World.Rnd().nextFloat() < 0.005F) {
                this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, 0);
                Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Piston Head..");
              }

              getEnergyPastArmor(22.5F, paramShot);
            }
          } else if (paramString.endsWith("eqpt")) {
            if ((getEnergyPastArmor(2.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F))
            {
              if (World.Rnd().nextFloat() < 0.1F) {
                this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setMagnetoKnockOut(paramShot.initiator, 0);

                Aircraft.debugprintln(this, "*** Engine Module: Magneto 0 Destroyed..");
              }

              if (World.Rnd().nextFloat() < 0.1F) {
                this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setMagnetoKnockOut(paramShot.initiator, 1);

                Aircraft.debugprintln(this, "*** Engine Module: Magneto 1 Destroyed..");
              }

              if (World.Rnd().nextFloat() < 0.1F) {
                this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 6);

                Aircraft.debugprintln(this, "*** Engine Module: Prop Controls Cut..");
              }

              if (World.Rnd().nextFloat() < 0.1F) {
                this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 1);

                Aircraft.debugprintln(this, "*** Engine Module: Throttle Controls Cut..");
              }

              if (World.Rnd().nextFloat() < 0.1F) {
                this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 7);

                Aircraft.debugprintln(this, "*** Engine Module: Mix Controls Cut..");
              }
            }

          }
          else if (paramString.endsWith("oil1")) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
            Aircraft.debugprintln(this, "*** Engine Module: Oil Radiator Hit..");
          }
        }

        if (paramString.startsWith("xxoil")) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
          getEnergyPastArmor(0.22F, paramShot);
          Aircraft.debugprintln(this, "*** Engine Module: Oil Tank Pierced..");
        }

        if ((paramString.startsWith("xxtank1")) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.3F))
        {
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[0] == 0) {
            Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");

            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, 2);
          }
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.75F))
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, 2);
            Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
          }
        }
        if (paramString.startsWith("xxmgun")) {
          if (paramString.endsWith("01")) {
            Aircraft.debugprintln(this, "*** Cowling Gun: Disabled..");

            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 0);
          }
          if (paramString.endsWith("02")) {
            Aircraft.debugprintln(this, "*** Cowling Gun: Disabled..");

            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 1);
          }
          if (paramString.endsWith("03")) {
            Aircraft.debugprintln(this, "*** Cowling Gun: Disabled..");

            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 0);
          }
          if (paramString.endsWith("04")) {
            Aircraft.debugprintln(this, "*** Cowling Gun: Disabled..");

            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 1);
          }
          getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 28.33F), paramShot);
        }
      }
    }
    else if (paramString.startsWith("xcf")) {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
      if (World.Rnd().nextFloat() < 0.07F) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x2);
      }
      if (World.Rnd().nextFloat() < 0.07F) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x1);
      }
      if (World.Rnd().nextFloat() < 0.07F) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x40);
      }
      if (World.Rnd().nextFloat() < 0.07F) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x4);
      }
      if (World.Rnd().nextFloat() < 0.07F)
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x10);
    }
    else if (paramString.startsWith("xeng")) {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    } else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    } else if (paramString.startsWith("xkeel")) {
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    } else if (paramString.startsWith("xrudder")) {
      if (chunkDamageVisible("Rudder1") < 1)
        hitChunk("Rudder1", paramShot);
    } else if (paramString.startsWith("xstab")) {
      if ((paramString.startsWith("xstabl")) && (chunkDamageVisible("StabL") < 2))
        hitChunk("StabL", paramShot);
      if ((paramString.startsWith("xstabr")) && (chunkDamageVisible("StabR") < 1))
        hitChunk("StabR", paramShot);
    } else if (paramString.startsWith("xvator")) {
      if ((paramString.startsWith("xvatorl")) && (chunkDamageVisible("VatorL") < 1))
      {
        hitChunk("VatorL", paramShot);
      }if ((paramString.startsWith("xvatorr")) && (chunkDamageVisible("VatorR") < 1))
      {
        hitChunk("VatorR", paramShot);
      }
    } else if (paramString.startsWith("xwing")) {
      if ((paramString.startsWith("xwinglin")) && (chunkDamageVisible("WingLIn") < 3))
      {
        hitChunk("WingLIn", paramShot);
      }if ((paramString.startsWith("xwingrin")) && (chunkDamageVisible("WingRIn") < 3))
      {
        hitChunk("WingRIn", paramShot);
      }if ((paramString.startsWith("xwinglmid")) && (chunkDamageVisible("WingLMid") < 3))
      {
        hitChunk("WingLMid", paramShot);
      }if ((paramString.startsWith("xwingrmid")) && (chunkDamageVisible("WingRMid") < 3))
      {
        hitChunk("WingRMid", paramShot);
      }if ((paramString.startsWith("xwinglout")) && (chunkDamageVisible("WingLOut") < 3))
      {
        hitChunk("WingLOut", paramShot);
      }if ((paramString.startsWith("xwingrout")) && (chunkDamageVisible("WingROut") < 3))
      {
        hitChunk("WingROut", paramShot);
      }
    } else if (paramString.startsWith("xarone")) {
      if ((paramString.startsWith("xaronel")) && (chunkDamageVisible("AroneL") < 1))
      {
        hitChunk("AroneL", paramShot);
      }if ((paramString.startsWith("xaroner")) && (chunkDamageVisible("AroneR") < 1))
      {
        hitChunk("AroneR", paramShot);
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
      }hitFlesh(j, paramShot, i);
    }
  }

  public void doMurderPilot(int paramInt) {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean) {
    super.rareAction(paramFloat, paramBoolean);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    setExplosion(paramExplosion);
    if (paramExplosion.chunkName != null) {
      if (paramExplosion.chunkName.startsWith("CF")) {
        if (World.Rnd().nextFloat() < 0.01F)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramExplosion.initiator, 0);
        if (World.Rnd().nextFloat() < 0.01F)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramExplosion.initiator, 1);
        if (World.Rnd().nextFloat() < 0.01F)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramExplosion.initiator, 2);
        if (World.Rnd().nextFloat() < 0.01F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitPilot(paramExplosion.initiator, 0, (int)(paramExplosion.power * 8924.0F * World.Rnd().nextFloat()));
        }
      }

      if (paramExplosion.chunkName.startsWith("Tail")) {
        if (World.Rnd().nextFloat() < 0.01F)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramExplosion.initiator, 1);
        if (World.Rnd().nextFloat() < 0.01F)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramExplosion.initiator, 2);
      }
    }
    super.msgExplosion(paramExplosion);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f1 = 16.6F;

    float f2 = (float)(Math.random() * 2.0D) - 1.0F;
    float f3 = (float)(Math.random() * 2.0D) - 1.0F;
    float f4 = (float)(Math.random() * 2.0D) - 1.0F;
    float f5 = (float)(Math.random() * 2.0D) - 1.0F;

    float f6 = f1 / 20.0F;

    paramHierMesh.chunkSetAngles("SkiR1_D0", 0.0F, f1, 0.0F);
    paramHierMesh.chunkSetAngles("SkiL1_D0", 0.0F, -f1, 0.0F);
    paramHierMesh.chunkSetAngles("SkiC1_D0", 0.0F, -f1, 0.0F);

    paramHierMesh.chunkSetAngles("SkiFrontDownWireL1_d0", 0.0F, -f6 * 10.5F, 0.0F);
    paramHierMesh.chunkSetAngles("SkiFrontDownWireL2_d0", 0.0F, -f6 * 11.2F, 0.0F);
    paramHierMesh.chunkSetAngles("SkiFrontUpWireL_d0", 0.0F, -f6 * -8.1F, 0.0F);

    paramHierMesh.chunkSetAngles("WireL1_d0", 0.0F, -f6 * 56.5F, f6 * -37.0F * f4);
    paramHierMesh.chunkSetAngles("WireL12_d0", 0.0F, -f6 * 77.300003F, f6 * 28.0F * f5);

    float f7 = f6 * -5.0F;

    float f8 = f6 * 10.0F;
    float f9 = f6 * 15.0F;
    float f10 = f6 * 20.0F;
    float f11 = f6 * 5.0F * f2;
    float f12 = f6 * 5.0F * f4;
    float f13 = f6 * 10.0F * f2;
    float f14 = f6 * 10.0F * f4;
    float f15 = f6 * -5.0F * f3;
    float f16 = f6 * -5.0F * f5;

    paramHierMesh.chunkSetAngles("WireL2_d0", 0.0F, f7, f14);
    paramHierMesh.chunkSetAngles("WireL3_d0", 0.0F, f8, f12);
    paramHierMesh.chunkSetAngles("WireL4_d0", 0.0F, f9, f14);
    paramHierMesh.chunkSetAngles("WireL5_d0", 0.0F, f9, f12);
    paramHierMesh.chunkSetAngles("WireL6_d0", 0.0F, f9, f14);
    paramHierMesh.chunkSetAngles("WireL7_d0", 0.0F, f8, f12);
    paramHierMesh.chunkSetAngles("WireL8_d0", 0.0F, f9, f14);
    paramHierMesh.chunkSetAngles("WireL9_d0", 0.0F, f9, f12);
    paramHierMesh.chunkSetAngles("WireL10_d0", 0.0F, f8, f14);
    paramHierMesh.chunkSetAngles("WireL11_d0", 0.0F, f7, f12);
    paramHierMesh.chunkSetAngles("WireL13_d0", 0.0F, f8, f16);
    paramHierMesh.chunkSetAngles("WireL14_d0", 0.0F, f9, f16);
    paramHierMesh.chunkSetAngles("WireL15_d0", 0.0F, f9, f16);
    paramHierMesh.chunkSetAngles("WireL16_d0", 0.0F, f10, f16);
    paramHierMesh.chunkSetAngles("WireL17_d0", 0.0F, f8, 0.0F);
    paramHierMesh.chunkSetAngles("WireL18_d0", 0.0F, f8, f16);
    paramHierMesh.chunkSetAngles("WireL19_d0", 0.0F, f9, f16);
    paramHierMesh.chunkSetAngles("WireL20_d0", 0.0F, f8, f16);
    paramHierMesh.chunkSetAngles("WireL21_d0", 0.0F, f8, f16);
    paramHierMesh.chunkSetAngles("WireL22_d0", 0.0F, f9, f16);

    paramHierMesh.chunkSetAngles("SkiFrontDownWireR1_d0", 0.0F, -f6 * 10.5F, 0.0F);
    paramHierMesh.chunkSetAngles("SkiFrontDownWireR2_d0", 0.0F, -f6 * 11.2F, 0.0F);
    paramHierMesh.chunkSetAngles("SkiFrontUpWireR_d0", 0.0F, -f6 * -8.1F, 0.0F);

    paramHierMesh.chunkSetAngles("WireR1_d0", 0.0F, -f6 * 56.5F, f6 * -37.0F * f2);
    paramHierMesh.chunkSetAngles("WireR12_d0", 0.0F, -f6 * 77.300003F, f6 * 28.0F * f3);

    paramHierMesh.chunkSetAngles("WireR2_d0", 0.0F, f7, f13);
    paramHierMesh.chunkSetAngles("WireR3_d0", 0.0F, f8, f11);
    paramHierMesh.chunkSetAngles("WireR4_d0", 0.0F, f8, f13);
    paramHierMesh.chunkSetAngles("WireR5_d0", 0.0F, f9, f11);
    paramHierMesh.chunkSetAngles("WireR6_d0", 0.0F, f9, f13);
    paramHierMesh.chunkSetAngles("WireR7_d0", 0.0F, f9, f11);
    paramHierMesh.chunkSetAngles("WireR8_d0", 0.0F, f8, f13);
    paramHierMesh.chunkSetAngles("WireR9_d0", 0.0F, f9, f11);
    paramHierMesh.chunkSetAngles("WireR10_d0", 0.0F, f9, f13);
    paramHierMesh.chunkSetAngles("WireR11_d0", 0.0F, f7, f11);
    paramHierMesh.chunkSetAngles("WireR13_d0", 0.0F, f8, f15);
    paramHierMesh.chunkSetAngles("WireR14_d0", 0.0F, f8, f15);
    paramHierMesh.chunkSetAngles("WireR15_d0", 0.0F, f9, f15);
    paramHierMesh.chunkSetAngles("WireR16_d0", 0.0F, f10, f15);
    paramHierMesh.chunkSetAngles("WireR17_d0", 0.0F, f9, 0.0F);
    paramHierMesh.chunkSetAngles("WireR18_d0", 0.0F, f8, f15);
    paramHierMesh.chunkSetAngles("WireR19_d0", 0.0F, f8, f15);
    paramHierMesh.chunkSetAngles("WireR20_d0", 0.0F, f9, f15);
    paramHierMesh.chunkSetAngles("WireR21_d0", 0.0F, f8, f15);
    paramHierMesh.chunkSetAngles("WireR22_d0", 0.0F, f9, f15);
  }

  protected void moveGear(float paramFloat)
  {
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasBrakeControl = false;
    this.wireRandomizer1 = ((float)(Math.random() * 2.0D) - 1.0F);
    this.wireRandomizer2 = ((float)(Math.random() * 2.0D) - 1.0F);
    this.wireRandomizer3 = ((float)(Math.random() * 2.0D) - 1.0F);
    this.wireRandomizer4 = ((float)(Math.random() * 2.0D) - 1.0F);
  }

  public void sfxWheels()
  {
  }

  public void update(float paramFloat)
  {
    hierMesh().chunkSetAngles("Engine1P_D0", 0.0F, 19.0F + 19.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator(), 0.0F);
    super.update(paramFloat);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 33:
      return super.cutFM(34, paramInt2, paramActor);
    case 36:
      return super.cutFM(37, paramInt2, paramActor);
    case 17:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.hit(17);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.hit(17);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.hit(17);
      return false;
    case 18:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.hit(18);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.hit(18);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.hit(18);
      return false;
    case 19:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.hitCentreGear();
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 0, 2);
      return false;
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void moveFan(float paramFloat)
  {
    if (Config.isUSE_RENDER())
    {
      int i = 0;
      float f1 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getAileron();
      float f2 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getElevator();
      hierMesh().chunkSetAngles("Stick_D0", 0.0F, 12.0F * f1, Aircraft.cvt(f2, -1.0F, 1.0F, -12.0F, 18.0F));
      hierMesh().chunkSetAngles("pilotarm2_d0", Aircraft.cvt(f1, -1.0F, 1.0F, 14.0F, -16.0F), 0.0F, Aircraft.cvt(f1, -1.0F, 1.0F, 6.0F, -8.0F) - (Aircraft.cvt(f2, -1.0F, 0.0F, -36.0F, 0.0F) + Aircraft.cvt(f2, 0.0F, 1.0F, 0.0F, 32.0F)));
      hierMesh().chunkSetAngles("pilotarm1_d0", 0.0F, 0.0F, Aircraft.cvt(f1, -1.0F, 1.0F, -16.0F, 14.0F) + Aircraft.cvt(f2, -1.0F, 0.0F, -62.0F, 0.0F) + Aircraft.cvt(f2, 0.0F, 1.0F, 0.0F, 44.0F));

      float f3 = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed(), 30.0F, 80.0F, 1.0F, 0.0F);
      float f4 = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed(), 0.0F, 30.0F, 0.0F, 0.5F);

      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0] > 0.0F)
      {
        i = 1;
        this.skiAngleL = (0.5F * this.skiAngleL + 0.5F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage());

        if (this.skiAngleL > 20.0F)
        {
          this.skiAngleL -= this.spring;
        }

        hierMesh().chunkSetAngles("SkiL1_D0", World.Rnd().nextFloat(-f4, f4), World.Rnd().nextFloat(-f4 * 2.0F, f4 * 2.0F) - this.skiAngleL, World.Rnd().nextFloat(f4, f4));
      }
      else
      {
        if (this.skiAngleL > f3 * -10.0F + 0.01D)
        {
          this.skiAngleL -= this.spring;
          i = 1;
        }
        else if (this.skiAngleL < f3 * -10.0F - 0.01D)
        {
          this.skiAngleL += this.spring;
          i = 1;
        }

        hierMesh().chunkSetAngles("SkiL1_D0", 0.0F, -this.skiAngleL, 0.0F);
      }

      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1] > 0.0F)
      {
        i = 1;
        this.skiAngleR = (0.5F * this.skiAngleR + 0.5F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage());

        if (this.skiAngleR > 20.0F)
        {
          this.skiAngleR -= this.spring;
        }

        hierMesh().chunkSetAngles("SkiR1_D0", World.Rnd().nextFloat(-f4, f4), World.Rnd().nextFloat(-f4 * 2.0F, f4 * 2.0F) + this.skiAngleR, World.Rnd().nextFloat(f4, f4));

        if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0] == 0.0F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getRoll() < 365.0F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getRoll() > 355.0F))
        {
          this.skiAngleL = this.skiAngleR;
          hierMesh().chunkSetAngles("SkiL1_D0", World.Rnd().nextFloat(-f4, f4), World.Rnd().nextFloat(-f4 * 2.0F, f4 * 2.0F) - this.skiAngleL, World.Rnd().nextFloat(f4, f4));
        }

      }
      else
      {
        if (this.skiAngleR > f3 * -10.0F + 0.01D)
        {
          this.skiAngleR -= this.spring;
          i = 1;
        }
        else if (this.skiAngleR < f3 * -10.0F - 0.01D)
        {
          this.skiAngleR += this.spring;
          i = 1;
        }
        hierMesh().chunkSetAngles("SkiR1_D0", 0.0F, this.skiAngleR, 0.0F);
      }

      if ((i == 0) && (f3 == 0.0F))
      {
        super.moveFan(paramFloat);
        return;
      }

      hierMesh().chunkSetAngles("SkiC1_D0", 0.0F, -((this.skiAngleL + this.skiAngleR) / 2.0F), 0.0F);

      float f5 = this.skiAngleL / 20.0F;

      hierMesh().chunkSetAngles("SkiFrontDownWireL1_d0", 0.0F, -f5 * 10.5F, 0.0F);
      hierMesh().chunkSetAngles("SkiFrontDownWireL2_d0", 0.0F, -f5 * 11.2F, 0.0F);
      hierMesh().chunkSetAngles("SkiFrontUpWireL_d0", 0.0F, -f5 * -8.1F, 0.0F);
      float f6;
      float f7;
      float f8;
      float f9;
      float f10;
      float f11;
      float f12;
      float f13;
      if (this.skiAngleL < 0.0F)
      {
        hierMesh().chunkSetAngles("WireL1_d0", 0.0F, f5 * -15.0F, 0.0F);
        hierMesh().chunkSetAngles("WireL12_d0", 0.0F, f5 * -15.0F, 0.0F);
        hierMesh().chunkSetAngles("WireL2_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireL3_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireL4_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireL5_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireL6_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireL7_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireL8_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireL9_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireL10_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireL11_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireL13_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireL14_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireL15_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireL16_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireL17_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireL18_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireL19_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireL20_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireL21_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireL22_d0", 0.0F, 0.0F, 0.0F);
      }
      else
      {
        f6 = 1.0F;
        if (f5 < 0.5F)
          f6 = Aircraft.cvt(f5, 0.0F, 0.5F, 1.0F, 0.95F);
        else {
          f6 = Aircraft.cvt(f5, 0.5F, 1.0F, 0.95F, 1.0F);
        }
        hierMesh().chunkSetAngles("WireL1_d0", 0.0F, -f5 * (56.5F * f6), f5 * (-37.0F * f3) * this.wireRandomizer3);
        hierMesh().chunkSetAngles("WireL12_d0", 0.0F, -f5 * (77.300003F * f6), f5 * (28.0F * f3) * this.wireRandomizer4);

        f7 = f5 * -5.0F;
        f8 = f5 * 10.0F;
        f9 = f5 * 15.0F;
        f10 = f5 * 20.0F;
        f11 = f5 * (5.0F * f3) * this.wireRandomizer3;
        f12 = f5 * (10.0F * f3) * this.wireRandomizer3;
        f13 = f5 * (-5.0F * f3) * this.wireRandomizer4;

        hierMesh().chunkSetAngles("WireL2_d0", 0.0F, f7, f12);
        hierMesh().chunkSetAngles("WireL3_d0", 0.0F, f8, f11);
        hierMesh().chunkSetAngles("WireL4_d0", 0.0F, f9, f12);
        hierMesh().chunkSetAngles("WireL5_d0", 0.0F, f9, f11);
        hierMesh().chunkSetAngles("WireL6_d0", 0.0F, f9, f12);
        hierMesh().chunkSetAngles("WireL7_d0", 0.0F, f8, f11);
        hierMesh().chunkSetAngles("WireL8_d0", 0.0F, f9, f12);
        hierMesh().chunkSetAngles("WireL9_d0", 0.0F, f9, f11);
        hierMesh().chunkSetAngles("WireL10_d0", 0.0F, f8, f12);
        hierMesh().chunkSetAngles("WireL11_d0", 0.0F, f7, f11);
        hierMesh().chunkSetAngles("WireL13_d0", 0.0F, f8, f13);
        hierMesh().chunkSetAngles("WireL14_d0", 0.0F, f9, f13);
        hierMesh().chunkSetAngles("WireL15_d0", 0.0F, f9, f13);
        hierMesh().chunkSetAngles("WireL16_d0", 0.0F, f10, f13);
        hierMesh().chunkSetAngles("WireL17_d0", 0.0F, f8, 0.0F);
        hierMesh().chunkSetAngles("WireL18_d0", 0.0F, f8, f13);
        hierMesh().chunkSetAngles("WireL19_d0", 0.0F, f9, f13);
        hierMesh().chunkSetAngles("WireL20_d0", 0.0F, f8, f13);
        hierMesh().chunkSetAngles("WireL21_d0", 0.0F, f8, f13);
        hierMesh().chunkSetAngles("WireL22_d0", 0.0F, f9, f13);
      }

      f5 = this.skiAngleR / 20.0F;

      hierMesh().chunkSetAngles("SkiFrontDownWireR1_d0", 0.0F, -f5 * 10.5F, 0.0F);
      hierMesh().chunkSetAngles("SkiFrontDownWireR2_d0", 0.0F, -f5 * 11.2F, 0.0F);
      hierMesh().chunkSetAngles("SkiFrontUpWireR_d0", 0.0F, -f5 * -8.1F, 0.0F);

      if (this.skiAngleR < 0.0F)
      {
        hierMesh().chunkSetAngles("WireR1_d0", 0.0F, f5 * -15.0F, 0.0F);
        hierMesh().chunkSetAngles("WireR12_d0", 0.0F, f5 * -15.0F, 0.0F);
        hierMesh().chunkSetAngles("WireR2_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireR3_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireR4_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireR5_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireR6_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireR7_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireR8_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireR9_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireR10_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireR11_d0", 0.0F, 0.0F, 0.0F);

        hierMesh().chunkSetAngles("WireR13_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireR14_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireR15_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireR16_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireR17_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireR18_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireR19_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireR20_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireR21_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("WireR22_d0", 0.0F, 0.0F, 0.0F);
      }
      else
      {
        f6 = 1.0F;
        if (f5 < 0.5F)
          f6 = Aircraft.cvt(f5, 0.0F, 0.5F, 1.0F, 0.95F);
        else {
          f6 = Aircraft.cvt(f5, 0.5F, 1.0F, 0.95F, 1.0F);
        }
        hierMesh().chunkSetAngles("WireR1_d0", 0.0F, -f5 * (56.5F * f6), f5 * (-37.0F * f3) * this.wireRandomizer1);
        hierMesh().chunkSetAngles("WireR12_d0", 0.0F, -f5 * (77.300003F * f6), f5 * (28.0F * f3) * this.wireRandomizer2);

        f7 = f5 * -5.0F;
        f8 = f5 * 10.0F;
        f9 = f5 * 15.0F;
        f10 = f5 * 20.0F;
        f11 = f5 * (5.0F * f3) * this.wireRandomizer1;
        f12 = f5 * (10.0F * f3) * this.wireRandomizer1;
        f13 = f5 * (-5.0F * f3) * this.wireRandomizer2;

        hierMesh().chunkSetAngles("WireR2_d0", 0.0F, f7, f12);
        hierMesh().chunkSetAngles("WireR3_d0", 0.0F, f8, f11);
        hierMesh().chunkSetAngles("WireR4_d0", 0.0F, f8, f12);
        hierMesh().chunkSetAngles("WireR5_d0", 0.0F, f9, f11);
        hierMesh().chunkSetAngles("WireR6_d0", 0.0F, f9, f12);
        hierMesh().chunkSetAngles("WireR7_d0", 0.0F, f9, f11);
        hierMesh().chunkSetAngles("WireR8_d0", 0.0F, f8, f12);
        hierMesh().chunkSetAngles("WireR9_d0", 0.0F, f9, f11);
        hierMesh().chunkSetAngles("WireR10_d0", 0.0F, f9, f12);
        hierMesh().chunkSetAngles("WireR11_d0", 0.0F, f7, f11);

        hierMesh().chunkSetAngles("WireR13_d0", 0.0F, f8, f13);
        hierMesh().chunkSetAngles("WireR14_d0", 0.0F, f8, f13);
        hierMesh().chunkSetAngles("WireR15_d0", 0.0F, f9, f13);
        hierMesh().chunkSetAngles("WireR16_d0", 0.0F, f10, f13);
        hierMesh().chunkSetAngles("WireR17_d0", 0.0F, f9, 0.0F);
        hierMesh().chunkSetAngles("WireR18_d0", 0.0F, f8, f13);
        hierMesh().chunkSetAngles("WireR19_d0", 0.0F, f8, f13);
        hierMesh().chunkSetAngles("WireR20_d0", 0.0F, f9, f13);
        hierMesh().chunkSetAngles("WireR21_d0", 0.0F, f8, f13);
        hierMesh().chunkSetAngles("WireR22_d0", 0.0F, f9, f13);
      }
    }
    super.moveFan(paramFloat);
  }

  static {
    Class localClass = I_16FixedSkis.class;
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);
  }
}