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
import com.maddox.il2.objects.weapons.MGunMK108ki;
import com.maddox.rts.Property;

public abstract class BF_110 extends Scheme2a
  implements TypeFighter, TypeBNZFighter, TypeStormovik
{
  private float kangle0 = 0.0F;
  private float kangle1 = 0.0F;
  private float slpos = 0.0F;

  protected void moveAileron(float paramFloat)
  {
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, 30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, 30.0F * paramFloat, 0.0F);
  }

  public void update(float paramFloat)
  {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed() > 5.0F) {
      this.slpos = (0.7F * this.slpos + 0.13F * (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAOA() > 6.6F ? 0.07F : 0.0F));
      resetYPRmodifier();
      Aircraft.xyz[0] = this.slpos;
      hierMesh().chunkSetLocate("SlatL_D0", Aircraft.xyz, Aircraft.ypr);
      hierMesh().chunkSetLocate("SlatR_D0", Aircraft.xyz, Aircraft.ypr);
    }
    hierMesh().chunkSetAngles("WaterL_D0", 0.0F, 15.0F - 30.0F * this.kangle0, 0.0F);
    this.kangle0 = (0.95F * this.kangle0 + 0.05F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator());
    hierMesh().chunkSetAngles("WaterR_D0", 0.0F, 15.0F - 30.0F * this.kangle1, 0.0F);
    this.kangle1 = (0.95F * this.kangle1 + 0.05F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlRadiator());
    super.update(paramFloat);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor")) {
        debuggunnery("Armor: Hit..");
        if (paramString.endsWith("p1")) {
          if (Math.abs(paramPoint3d.jdField_y_of_type_Double) > 0.231D)
            getEnergyPastArmor(8.585000038146973D / (Math.abs(Aircraft.v1.jdField_y_of_type_Double) + 9.999999747378752E-005D), paramShot);
          else
            getEnergyPastArmor(1.0F, paramShot);
        }
        else if (paramString.endsWith("p2")) {
          getEnergyPastArmor(World.Rnd().nextFloat(1.96F, 3.4839F), paramShot);
        } else if (paramString.endsWith("p3")) {
          if (paramPoint3d.jdField_z_of_type_Double < 0.08D) {
            getEnergyPastArmor(8.585000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot); } else {
            if (paramPoint3d.jdField_z_of_type_Double < 0.09D) {
              debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
              return;
            }
            if ((paramPoint3d.jdField_y_of_type_Double > 0.175D) && (paramPoint3d.jdField_y_of_type_Double < 0.287D) && (paramPoint3d.jdField_z_of_type_Double < 0.177D)) {
              debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
              return;
            }
            if ((paramPoint3d.jdField_y_of_type_Double > -0.334D) && (paramPoint3d.jdField_y_of_type_Double < -0.177D) && (paramPoint3d.jdField_z_of_type_Double < 0.204D)) {
              debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
              return;
            }
            if ((paramPoint3d.jdField_z_of_type_Double > 0.288D) && (Math.abs(paramPoint3d.jdField_y_of_type_Double) < 0.077D))
              getEnergyPastArmor(World.Rnd().nextFloat(8.5F, 12.46F) / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
            else
              getEnergyPastArmor(10.510000228881836D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
          }
        }
        else if (paramString.endsWith("p4")) {
          getEnergyPastArmor(World.Rnd().nextFloat(20.0F, 30.0F), paramShot);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x2);
          debuggunnery("Armor: Armor Glass: Hit..");
          if (paramShot.power <= 0.0F) {
            debuggunnery("Armor: Armor Glass: Bullet Stopped..");
            if (World.Rnd().nextFloat() < 0.96F)
              doRicochetBack(paramShot);
          }
        }
        else if (paramString.endsWith("p5")) {
          getEnergyPastArmor(5.510000228881836D / (Math.abs(Aircraft.v1.jdField_z_of_type_Double) + 9.999999747378752E-005D), paramShot);
        } else if (paramString.endsWith("p6")) {
          if (paramPoint3d.jdField_z_of_type_Double > 0.448D) {
            if ((paramPoint3d.jdField_z_of_type_Double > 0.609D) && (Math.abs(paramPoint3d.jdField_y_of_type_Double) > 0.251D)) {
              debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
              return;
            }
            getEnergyPastArmor(10.604999542236328D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
          }
          else if (Math.abs(paramPoint3d.jdField_y_of_type_Double) > 0.264D) {
            if (paramPoint3d.jdField_z_of_type_Double > 0.021D) {
              getEnergyPastArmor(8.510000228881836D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
            } else {
              debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
              return;
            }
          } else {
            if ((paramPoint3d.jdField_z_of_type_Double < -0.352D) && (Math.abs(paramPoint3d.jdField_y_of_type_Double) < 0.04D)) {
              debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
              return;
            }
            getEnergyPastArmor(8.060000419616699D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
          }

        }
        else if (paramString.endsWith("p7")) {
          getEnergyPastArmor(6.059999942779541D / (Math.abs(Aircraft.v1.jdField_z_of_type_Double) + 9.999999747378752E-005D), paramShot);
        } else if (paramString.endsWith("p8")) {
          if (((paramPoint3d.jdField_y_of_type_Double > 0.112D) && (paramPoint3d.jdField_z_of_type_Double < -0.319D)) || ((paramPoint3d.jdField_y_of_type_Double < -0.065D) && (paramPoint3d.jdField_z_of_type_Double > 0.038D) && (paramPoint3d.jdField_z_of_type_Double < 0.204D))) {
            debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
            return;
          }
          getEnergyPastArmor(8.060000419616699D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
        }
        else if (paramString.endsWith("p9")) {
          if ((paramPoint3d.jdField_z_of_type_Double > 0.611D) && (paramPoint3d.jdField_z_of_type_Double < 0.674D) && (Math.abs(paramPoint3d.jdField_y_of_type_Double) < 0.0415D)) {
            debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
            return;
          }
          getEnergyPastArmor(8.060000419616699D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
        }

        return;
      }

      if (paramString.startsWith("xxcontrols")) {
        debuggunnery("Controls: Hit..");
        if (World.Rnd().nextFloat() < 0.99F) {
          return;
        }
        i = paramString.charAt(10) - '0';
        if (paramString.endsWith("10")) {
          i = 10;
        }
        if (paramString.endsWith("11")) {
          i = 11;
        }
        switch (i) {
        case 1:
          if (getEnergyPastArmor(2.2F, paramShot) <= 0.0F) break;
          debuggunnery("Controls: Control Column: Hit, Controls Destroyed..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0); break;
        case 2:
          if (getEnergyPastArmor(World.Rnd().nextFloat(0.02F, 2.351F), paramShot) <= 0.0F) break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 6);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 1, 1);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 1, 6);
          debuggunnery("Controls: Throttle Quadrant: Hit, Engine Controls Disabled.."); break;
        case 3:
          if (getEnergyPastArmor(3.5F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.25F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0);
            debuggunnery("Controls: Aileron Controls: Fuselage Line Destroyed..");
          }
          if (World.Rnd().nextFloat() < 0.25F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
            debuggunnery("Controls: Elevator Controls: Fuselage Line Destroyed..");
          }
          if (World.Rnd().nextFloat() >= 0.25F) break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2);
          debuggunnery("Controls: Rudder Controls: Fuselage Line Destroyed.."); break;
        case 4:
        case 5:
          if (getEnergyPastArmor(0.002F, paramShot) <= 0.0F) break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
          debuggunnery("Controls: Elevator Controls: Disabled / Strings Broken.."); break;
        case 6:
        case 7:
        case 10:
        case 11:
          if ((getEnergyPastArmor(0.12F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.5F)) break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0);
          debuggunnery("Controls: Aileron Controls: Disabled.."); break;
        case 8:
        case 9:
          if (getEnergyPastArmor(6.8F, paramShot) <= 0.0F) break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0);
          debuggunnery("Controls: Aileron Controls: Crank Destroyed..");
        }

        return;
      }

      if (paramString.startsWith("xxspar")) {
        debuggunnery("Spar Construction: Hit..");
        if ((paramString.startsWith("xxspart")) && 
          (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.5F / (float)Math.sqrt(Aircraft.v1.jdField_y_of_type_Double * Aircraft.v1.jdField_y_of_type_Double + Aircraft.v1.jdField_z_of_type_Double * Aircraft.v1.jdField_z_of_type_Double), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F)) {
          debuggunnery("Spar Construction: Tail1 Spars Broken in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparli")) && 
          (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparri")) && 
          (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlm")) && 
          (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(17.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparrm")) && 
          (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(17.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlo")) && 
          (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(13.2F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparro")) && 
          (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(13.2F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        return;
      }

      if (paramString.startsWith("xxwj")) {
        if (getEnergyPastArmor(World.Rnd().nextFloat(12.5F, 55.959999F), paramShot) > 0.0F) {
          if (paramString.endsWith("l")) {
            debuggunnery("Spar Construction: WingL Console Lock Destroyed..");
            nextDMGLevels(4, 2, "WingLIn_D" + chunkDamageVisible("WingLIn"), paramShot.initiator);
          } else {
            debuggunnery("Spar Construction: WingR Console Lock Destroyed..");
            nextDMGLevels(4, 2, "WingRIn_D" + chunkDamageVisible("WingRIn"), paramShot.initiator);
          }
        }
        return;
      }

      if (paramString.startsWith("xxlock")) {
        debuggunnery("Lock Construction: Hit..");
        if (paramString.startsWith("xxlockr")) {
          i = paramString.charAt(6) - '0';
          if (getEnergyPastArmor(6.56F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F) {
            if (i < 3) {
              debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
              nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
            } else {
              debuggunnery("Lock Construction: Rudder2 Lock Shot Off..");
              nextDMGLevels(3, 2, "Rudder2_D" + chunkDamageVisible("Rudder2"), paramShot.initiator);
            }
          }
        }
        if ((paramString.startsWith("xxlockvl")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: VatorL Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvr")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: VatorR Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
        }

        return;
      }

      if (paramString.startsWith("xxeng")) {
        i = paramString.charAt(5) - '1';
        debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Hit..");
        if (paramString.endsWith("prop")) {
          if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.8F))
            if (World.Rnd().nextFloat() < 0.5F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, i, 3);
              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Prop Governor Hit, Disabled..");
            } else {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, i, 4);
              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Prop Governor Hit, Damaged..");
            }
        }
        else if (paramString.endsWith("gear")) {
          if (getEnergyPastArmor(4.6F, paramShot) > 0.0F)
            if (World.Rnd().nextFloat() < 0.5F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setEngineStuck(paramShot.initiator);
              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Bullet Jams Reductor Gear..");
            } else {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, i, 3);
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, i, 4);
              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Reductor Gear Damaged, Prop Governor Failed..");
            }
        }
        else if (paramString.endsWith("supc")) {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, i, 0);
            debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Supercharger Disabled..");
          }
        } else if (paramString.endsWith("feed")) {
          if ((getEnergyPastArmor(3.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getPowerOutput() > 0.7F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 100);
            debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Pressurized Fuel Line Pierced, Fuel Flamed..");
          }
        } else if (paramString.endsWith("fuel")) {
          if (getEnergyPastArmor(1.1F, paramShot) > 0.0F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setEngineStops(paramShot.initiator);
            debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Fuel Line Stalled, Engine Stalled..");
          }
        } else if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(2.1F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 175000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, i);
              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Bullet Jams Crank Ball Bearing..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 2);
              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Crank Case Hit, Readyness Reduced to " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getReadyness() + "..");
            }
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setReadyness(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
            debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Crank Case Hit, Readyness Reduced to " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getReadyness() + "..");
          }
          getEnergyPastArmor(World.Rnd().nextFloat(22.5F, 33.599998F), paramShot);
        } else if ((paramString.endsWith("cyl1")) || (paramString.endsWith("cyl2")))
        {
          if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylindersRatio() * 1.75F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
            debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Cylinders Hit, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 24000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 3);
              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Cylinders Hit, Engine Fires..");
            }
            if (World.Rnd().nextFloat() < 0.01F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, i);
              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Bullet Jams Piston Head..");
            }
            getEnergyPastArmor(22.5F, paramShot);
          }
        } else if ((paramString.endsWith("mag1")) || (paramString.endsWith("mag2")))
        {
          int j = paramString.charAt(9) - '1';
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setMagnetoKnockOut(paramShot.initiator, j);
          debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Magneto " + j + " Destroyed..");
        } else if (paramString.endsWith("oil1")) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, i);
          debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Oil Radiator Hit..");
        }
        return;
      }

      if (paramString.startsWith("xxoil")) {
        if (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 2.345F), paramShot) > 0.0F) {
          i = paramString.charAt(5) - '1';
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, i);
          getEnergyPastArmor(0.22F, paramShot);
          debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Oil Tank Pierced..");
        }
        return;
      }
      if (paramString.startsWith("xxw")) {
        if (getEnergyPastArmor(World.Rnd().nextFloat(0.1F, 0.75F), paramShot) > 0.0F) {
          i = paramString.charAt(3) - '1';
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[i] == 0) {
            debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Water Radiator Pierced..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 2);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetEngineState(paramShot.initiator, i, 2);
          }
          getEnergyPastArmor(2.22F, paramShot);
        }
        return;
      }

      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(World.Rnd().nextFloat(0.1F, 2.23F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[i] == 0) {
            debuggunnery("Fuel Tank " + (i + 1) + ": Pierced..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 1);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetTankState(paramShot.initiator, i, 1);
          } else if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[i] == 1) {
            debuggunnery("Fuel Tank " + (i + 1) + ": Pierced..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 1);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetTankState(paramShot.initiator, i, 2);
          }
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.5F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 2, 2);
            debuggunnery("Fuel Tank " + (i + 1) + ": Hit..");
          }
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

      if (paramString.startsWith("xxmgun")) {
        if (paramString.endsWith("01")) {
          debuggunnery("Cowling Gun: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 0);
        }
        if (paramString.endsWith("02")) {
          debuggunnery("Cowling Gun: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 1);
        }
        if (paramString.endsWith("03")) {
          debuggunnery("Cowling Gun: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 2);
        }
        if (paramString.endsWith("04")) {
          debuggunnery("Cowling Gun: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 3);
        }
        getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 12.96F), paramShot);
      }
      if (paramString.startsWith("xxcannon")) {
        if (paramString.endsWith("01")) {
          debuggunnery("Cowling Cannon: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 0);
        }
        if (paramString.endsWith("02")) {
          debuggunnery("Cowling Cannon: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 1);
        }
        getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F), paramShot);
      }
      if (paramString.startsWith("xxmgff")) {
        if (paramString.endsWith("01")) {
          debuggunnery("Cowling Cannon (MGFF): Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 0);
        }
        if (paramString.endsWith("02")) {
          debuggunnery("Cowling Cannon (MGFF): Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 1);
        }
        getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F), paramShot);
      }
      if (paramString.startsWith("xxmk")) {
        if (paramString.endsWith("01")) {
          debuggunnery("Cowling Cannon (Mk 108): Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 0);
        }
        if (paramString.endsWith("02")) {
          debuggunnery("Cowling Cannon (Mk 108): Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 1);
        }
        getEnergyPastArmor(World.Rnd().nextFloat(24.5F, 96.870003F), paramShot);
      }
      return;
    }

    if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit")) || (paramString.startsWith("xnose")))
    {
      if (chunkDamageVisible("CF") < 3) {
        hitChunk("CF", paramShot);
      }
      if (paramString.startsWith("xcockpit")) {
        if ((paramPoint3d.jdField_x_of_type_Double > 1.857D) && (paramPoint3d.jdField_z_of_type_Double > 0.416D)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x2);
        }
        if (World.Rnd().nextFloat() < 0.12F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x40);
        }
        if (World.Rnd().nextFloat() < 0.12F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x4);
        }
        if (World.Rnd().nextFloat() < 0.12F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x8);
        }
        if (World.Rnd().nextFloat() < 0.12F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x10);
        }
        if (World.Rnd().nextFloat() < 0.12F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x20);
        }
        if (World.Rnd().nextFloat() < 0.12F)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x1);
      }
    }
    else if (paramString.startsWith("xengine")) {
      i = paramString.charAt(7) - '0';
      if (chunkDamageVisible("Engine" + i) < 2)
        hitChunk("Engine" + i, paramShot);
    }
    else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel")) {
      i = paramString.charAt(5) - '0';
      if (chunkDamageVisible("Keel" + i) < 3)
        hitChunk("Keel" + i, paramShot);
    }
    else if (paramString.startsWith("xrudder")) {
      i = paramString.charAt(7) - '0';
      if (chunkDamageVisible("Rudder" + i) < 1)
        hitChunk("Rudder" + i, paramShot);
    }
    else if (paramString.startsWith("xstab")) {
      if ((paramString.startsWith("xstabl")) && 
        (chunkDamageVisible("StabL") < 3)) {
        hitChunk("StabL", paramShot);
      }

      if ((paramString.startsWith("xstabr")) && 
        (chunkDamageVisible("StabR") < 3)) {
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
      if (paramString.startsWith("xaronel")) {
        hitChunk("AroneL", paramShot);
      }
      if (paramString.startsWith("xaroner"))
        hitChunk("AroneR", paramShot);
    }
    else if (paramString.startsWith("xgear")) {
      if ((paramString.endsWith("1")) && 
        (World.Rnd().nextFloat() < 0.05F)) {
        debuggunnery("Hydro System: Disabled..");
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setInternalDamage(paramShot.initiator, 0);
      }

      if (paramString.endsWith("2")) {
        if ((World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(6.8F, 29.35F), paramShot) > 0.0F)) {
          debuggunnery("Undercarriage: Stuck..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setInternalDamage(paramShot.initiator, 3);
        }
        String str = "" + paramString.charAt(5);
        hitChunk("Gear" + str.toUpperCase() + "2", paramShot);
      }
    } else if (paramString.startsWith("xturret")) {
      if (getEnergyPastArmor(0.25F, paramShot) > 0.0F) {
        debuggunnery("Armament System: Turret Machine Gun(s): Disabled..");
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(10, 0);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(10, 1);
        getEnergyPastArmor(World.Rnd().nextFloat(0.1F, 26.35F), paramShot);
      }
    } else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
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
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 120.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, Aircraft.cvt(paramFloat, 0.0F, 0.1F, 0.0F, -50.5F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.0F, 0.1F, 0.0F, -50.5F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 120.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, Aircraft.cvt(paramFloat, 0.0F, 0.1F, 0.0F, -50.5F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.0F, 0.1F, 0.0F, -50.5F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC2_D0", paramFloat, 0.0F, 0.0F);
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      break;
    case 1:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
    }
  }

  public void doMurderPilot(int paramInt) {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("HMask1_D0", false);
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsAboutToBailout) break;
      hierMesh().chunkVisible("Gore1_D0", true); break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      hierMesh().chunkVisible("HMask2_D0", false);
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsAboutToBailout) break;
      hierMesh().chunkVisible("Gore2_D0", true);
    }
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
    case 12:
    case 18:
    case 19:
      hierMesh().chunkVisible("Wire_D0", false);
      break;
    case 34:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(this, 0, 2);
      if (World.Rnd().nextFloat() >= 0.66F) break;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(this, 0, 2); break;
    case 37:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(this, 1, 2);
      if (World.Rnd().nextFloat() >= 0.66F) break;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(this, 1, 2);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean) {
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[0] > 3) && 
        (World.Rnd().nextFloat() < 0.06F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 0, 1);

      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[1] > 3) && 
        (World.Rnd().nextFloat() < 0.06F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 1, 1);

      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[0] > 5) && (World.Rnd().nextFloat() < 0.02F)) nextDMGLevel(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[0] + "0", 0, this);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[1] > 5) && (World.Rnd().nextFloat() < 0.02F)) nextDMGLevel(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[1] + "0", 0, this);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[2] > 5) && (World.Rnd().nextFloat() < 0.02F)) nextDMGLevel(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[2] + "0", 0, this);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[3] > 5) && (World.Rnd().nextFloat() < 0.02F)) nextDMGLevel(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[3] + "0", 0, this);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.08F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 1, 1);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.08F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 0, 1);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[2] > 4) && (World.Rnd().nextFloat() < 0.08F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 3, 1);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[3] > 4) && (World.Rnd().nextFloat() < 0.08F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 2, 1);
    }

    for (int i = 1; i < 3; i++)
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -37.0F) { f1 = -37.0F; bool = false; }
      if (f1 > 37.0F) { f1 = 37.0F; bool = false; }
      if (f2 < -19.0F) { f2 = -19.0F; bool = false; }
      if (f2 > 27.0F) { f2 = 27.0F; bool = false; }
      if ((Math.abs(f1) <= 17.799999F) || (Math.abs(f1) >= 25.0F) || (f2 >= -12.0F)) break;
      bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if ((this instanceof BF_110G2)) {
      Object[] arrayOfObject = this.pos.getBaseAttached();
      if (arrayOfObject == null) return;
      for (int i = 0; i < arrayOfObject.length; i++) {
        if ((arrayOfObject[i] instanceof Bomb)) {
          hierMesh().chunkVisible("Rack_D0", true);
        }
        if ((arrayOfObject[i] instanceof MGunMK108ki)) {
          hierMesh().chunkVisible("Nose_D0", false);
          hierMesh().chunkVisible("Nose_D1", true);
        }
      }
    }
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -45.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap1_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap2_D0", 0.0F, f, 0.0F);
  }

  static
  {
    Class localClass = BF_110.class;
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
  }
}