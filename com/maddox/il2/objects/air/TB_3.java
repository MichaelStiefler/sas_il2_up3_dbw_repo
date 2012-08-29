package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.rts.Property;

public abstract class TB_3 extends Scheme4
  implements TypeTransport, TypeBomber
{
  private boolean bDynamoOperational = true;
  private float dynamoOrient = 0.0F;
  private boolean bDynamoRotary = false;
  private int pk;

  protected void moveFan(float paramFloat)
  {
    if (this.bDynamoOperational) {
      this.pk = Math.abs((int)(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length() / 14.0D));
      if (this.pk >= 1) this.pk = 1;
    }
    if (this.bDynamoRotary != (this.pk == 1)) {
      this.bDynamoRotary = (this.pk == 1);
      hierMesh().chunkVisible("Gener_D0", !this.bDynamoRotary);
      hierMesh().chunkVisible("Generrot_D0", this.bDynamoRotary);
    }
    this.dynamoOrient = (this.bDynamoRotary ? (this.dynamoOrient - 17.987F) % 360.0F : (float)(this.dynamoOrient - this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length() * 1.544401526451111D) % 360.0F);
    hierMesh().chunkSetAngles("Gener_D0", 0.0F, this.dynamoOrient, 0.0F);
    super.moveFan(paramFloat);
  }

  public void moveWheelSink()
  {
    resetYPRmodifier();
    float f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.5F, 0.0F, 0.5F);
    hierMesh().chunkSetAngles("GearL2_D0", 0.0F, 25.0F * f, 0.0F);
    Aircraft.xyz[2] = (-0.21F * f);
    hierMesh().chunkSetLocate("GearL6_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[2] = (-0.69F * f);
    hierMesh().chunkSetLocate("GearL7_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[2] = (-0.87F * f);
    hierMesh().chunkSetLocate("GearL8_D0", Aircraft.xyz, Aircraft.ypr);

    f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.5F, 0.0F, 0.5F);
    hierMesh().chunkSetAngles("GearR2_D0", 0.0F, -25.0F * f, 0.0F);
    Aircraft.xyz[2] = (-0.21F * f);
    hierMesh().chunkSetLocate("GearR6_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[2] = (-0.69F * f);
    hierMesh().chunkSetLocate("GearR7_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[2] = (-0.87F * f);
    hierMesh().chunkSetLocate("GearR8_D0", Aircraft.xyz, Aircraft.ypr);

    f = -this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Or.getTangage() + 10.42765F;
    hierMesh().chunkSetAngles("GearL4_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("GearR4_D0", 0.0F, f, 0.0F);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("p1"))
          getEnergyPastArmor(0.78F, paramShot);
        else if (paramString.endsWith("p2"))
          getEnergyPastArmor(0.78F, paramShot);
        else if ((paramString.endsWith("g1")) || (paramString.endsWith("g2"))) {
          getEnergyPastArmor(2.0F * World.Rnd().nextFloat(0.9F, 1.21F), paramShot);
        }
      }
      if (paramString.startsWith("xxcontrols")) {
        if (World.Rnd().nextFloat() < 0.12F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
          Aircraft.debugprintln(this, "*** Evelator Controls Out..");
        }
        if (World.Rnd().nextFloat() < 0.12F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2);
          Aircraft.debugprintln(this, "*** Rudder Controls Out..");
        }
      }
      if (paramString.startsWith("xxspar")) {
        if (((paramString.endsWith("t1")) || (paramString.endsWith("t2")) || (paramString.endsWith("t3")) || (paramString.endsWith("t4"))) && 
          (World.Rnd().nextFloat() < 0.1F) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(12.9F / (float)Math.sqrt(Aircraft.v1.jdField_y_of_type_Double * Aircraft.v1.jdField_y_of_type_Double + Aircraft.v1.jdField_z_of_type_Double * Aircraft.v1.jdField_z_of_type_Double), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** Tail1 Spars Broken in Half..");
          msgCollision(this, "Tail1_D0", "Tail1_D0");
        }

        if (((paramString.endsWith("li1")) || (paramString.endsWith("li2")) || (paramString.endsWith("li3")) || (paramString.endsWith("li4"))) && 
          (World.Rnd().nextFloat() < 0.25F) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("ri1")) || (paramString.endsWith("ri2")) || (paramString.endsWith("ri3")) || (paramString.endsWith("ri4"))) && 
          (World.Rnd().nextFloat() < 0.25F) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("lm1")) || (paramString.endsWith("lm2")) || (paramString.endsWith("lm3")) || (paramString.endsWith("lm4"))) && 
          (World.Rnd().nextFloat() < 0.25F) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("rm1")) || (paramString.endsWith("rm2")) || (paramString.endsWith("rm3")) || (paramString.endsWith("rm4"))) && 
          (World.Rnd().nextFloat() < 0.25F) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("lo1")) || (paramString.endsWith("lo2")) || (paramString.endsWith("lo3")) || (paramString.endsWith("lo4"))) && 
          (World.Rnd().nextFloat() < 0.25F) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("ro1")) || (paramString.endsWith("ro2")) || (paramString.endsWith("ro3")) || (paramString.endsWith("ro4"))) && 
          (World.Rnd().nextFloat() < 0.25F) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if ((paramString.endsWith("e1")) && 
          (getEnergyPastArmor(28.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.45F)) {
          Aircraft.debugprintln(this, "*** Engine1 Suspension Broken in Half..");
          nextDMGLevels(3, 2, "Engine1_D0", paramShot.initiator);
        }

        if ((paramString.endsWith("e2")) && 
          (getEnergyPastArmor(28.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.45F)) {
          Aircraft.debugprintln(this, "*** Engine2 Suspension Broken in Half..");
          nextDMGLevels(3, 2, "Engine2_D0", paramShot.initiator);
        }

        if ((paramString.endsWith("e3")) && 
          (getEnergyPastArmor(28.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.45F)) {
          Aircraft.debugprintln(this, "*** Engine3 Suspension Broken in Half..");
          nextDMGLevels(3, 2, "Engine3_D0", paramShot.initiator);
        }

        if ((paramString.endsWith("e4")) && 
          (getEnergyPastArmor(28.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.45F)) {
          Aircraft.debugprintln(this, "*** Engine4 Suspension Broken in Half..");
          nextDMGLevels(3, 2, "Engine4_D0", paramShot.initiator);
        }
      }

      if ((paramString.startsWith("xxbomb")) && 
        (World.Rnd().nextFloat() < 0.01F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0].haveBullets())) {
        Aircraft.debugprintln(this, "*** Bomb Payload Detonates..");
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, 10);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 1, 10);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 2, 10);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 3, 10);
        msgCollision(this, "CF_D0", "CF_D0");
      }

      if (paramString.startsWith("xxeng")) {
        i = paramString.charAt(5) - '1';
        if (paramString.endsWith("base")) {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 200000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, i);
              Aircraft.debugprintln(this, "*** Engine" + (i + 1) + " Crank Case Hit - Engine Stucks..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 2);
              Aircraft.debugprintln(this, "*** Engine" + (i + 1) + " Crank Case Hit - Engine Damaged..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 14000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setCyliderKnockOut(paramShot.initiator, 1);
              Aircraft.debugprintln(this, "*** Engine" + (i + 1) + " Crank Case Hit - Cylinder Feed Out, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylinders() + " Left..");
            }
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setReadyness(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
            Aircraft.debugprintln(this, "*** Engine" + (i + 1) + " Crank Case Hit - Readyness Reduced to " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getReadyness() + "..");
          }
        } else if (paramString.endsWith("cyl")) {
          if ((getEnergyPastArmor(0.5F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylindersRatio())) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
            Aircraft.debugprintln(this, "*** Engine" + (i + 1) + " Cylinders Hit, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylinders() + " Left..");
            if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[i] < 1) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 1);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 24000.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 3);
              Aircraft.debugprintln(this, "*** Engine" + (i + 1) + " Cylinders Hit - Engine Fires..");
            }
            getEnergyPastArmor(25.0F, paramShot);
          }
        } else if (paramString.endsWith("wat")) {
          if (World.Rnd().nextFloat() < 0.03F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setMagnetoKnockOut(paramShot.initiator, 0);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setMagnetoKnockOut(paramShot.initiator, 1);
          }
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, i);
        }
      }
      if (paramString.startsWith("xxoil")) {
        i = paramString.charAt(5) - '1';
        if (getEnergyPastArmor(0.023F, paramShot) > 0.0F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, i);
          getEnergyPastArmor(0.12F, paramShot);
        }
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        i /= 2;
        if ((getEnergyPastArmor(0.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 1);
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.11F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 2);
          }
        }
      }
      return;
    }

    if (paramString.startsWith("xcf")) {
      hitChunk("CF", paramShot);
      if (paramPoint3d.jdField_x_of_type_Double > 1.0D) {
        if (World.Rnd().nextFloat() < 0.05F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x40);
        }
        if ((Aircraft.v1.jdField_x_of_type_Double < -0.800000011920929D) && (World.Rnd().nextFloat() < 0.2F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x2);
        }
        if ((Aircraft.v1.jdField_x_of_type_Double < -0.8999999761581421D) && (World.Rnd().nextFloat() < 0.2F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x1);
        }
        if (Math.abs(Aircraft.v1.jdField_x_of_type_Double) < 0.800000011920929D) {
          if (paramPoint3d.jdField_y_of_type_Double > 0.0D) {
            if (World.Rnd().nextFloat() < 0.1F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x4);
            }
            if (World.Rnd().nextFloat() < 0.1F)
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x8);
          }
          else {
            if (World.Rnd().nextFloat() < 0.1F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x10);
            }
            if (World.Rnd().nextFloat() < 0.1F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x20);
            }
          }
        }
      }
    }
    if ((paramString.startsWith("xtail")) && 
      (chunkDamageVisible("Tail1") < 3)) {
      hitChunk("Tail1", paramShot);
    }

    if ((paramString.startsWith("xkeel")) && 
      (chunkDamageVisible("Keel1") < 3)) {
      hitChunk("Keel1", paramShot);
    }

    if ((paramString.startsWith("xrudder")) && 
      (chunkDamageVisible("Rudder1") < 1)) {
      hitChunk("Rudder1", paramShot);
    }

    if ((paramString.startsWith("xstabl")) && 
      (chunkDamageVisible("StabL") < 3)) {
      hitChunk("StabL", paramShot);
    }

    if ((paramString.startsWith("xstabr")) && 
      (chunkDamageVisible("StabR") < 3)) {
      hitChunk("StabR", paramShot);
    }

    if (paramString.startsWith("xvatorl")) {
      hitChunk("VatorL", paramShot);
    }
    if (paramString.startsWith("xvatorr")) {
      hitChunk("VatorR", paramShot);
    }
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

    if (paramString.startsWith("xaronel")) {
      hitChunk("AroneL", paramShot);
    }
    if (paramString.startsWith("xaroner")) {
      hitChunk("AroneR", paramShot);
    }
    if (paramString.startsWith("xgearl")) {
      hitChunk("GearL2", paramShot);
    }
    if (paramString.startsWith("xgearr")) {
      hitChunk("GearR2", paramShot);
    }
    if ((paramString.startsWith("xengine1")) && 
      (chunkDamageVisible("Engine1") < 3)) {
      hitChunk("Engine1", paramShot);
    }

    if ((paramString.startsWith("xengine2")) && 
      (chunkDamageVisible("Engine2") < 3)) {
      hitChunk("Engine2", paramShot);
    }

    if ((paramString.startsWith("xengine3")) && 
      (chunkDamageVisible("Engine3") < 3)) {
      hitChunk("Engine3", paramShot);
    }

    if ((paramString.startsWith("xengine4")) && 
      (chunkDamageVisible("Engine4") < 3)) {
      hitChunk("Engine4", paramShot);
    }

    if (paramString.startsWith("xturret")) {
      if (paramString.startsWith("xturret1")) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(10, 0);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(10, 1);
      }
      if (paramString.startsWith("xturret2")) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(11, 0);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(11, 1);
      }
      if (paramString.startsWith("xturret3")) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(11, 0);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(11, 1);
      }
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
    }
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 33:
      hitProp(0, paramInt2, paramActor);
      hitProp(1, paramInt2, paramActor);
      break;
    case 36:
      hitProp(2, paramInt2, paramActor);
      hitProp(3, paramInt2, paramActor);
      break;
    case 19:
      killPilot(this, 5);
      killPilot(this, 6);
      return false;
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void moveFlap(float paramFloat)
  {
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
      break;
    case 4:
      break;
    case 5:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
      break;
    case 6:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = false;
      break;
    case 7:
    }
  }

  public void doMurderPilot(int paramInt) {
    if ((paramInt != 3) && (paramInt != 4) && (paramInt != 7))
      hierMesh().chunkSetAngles("Pilot" + (paramInt + 1) + "_D0", 0.0F, 10.0F, -25.0F);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if ((paramBoolean) && (
      (!(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof RealFlightModel)) || (!((RealFlightModel)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).isRealMode())))
      for (int i = 0; i < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum(); i++) {
        if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[i] <= 3) || (World.Rnd().nextFloat() >= 0.2F)) continue; this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setExtinguisherFire();
      }
  }

  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay1_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay3_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay4_D0", 0.0F, -90.0F * paramFloat, 0.0F);
  }

  static
  {
    Class localClass = TB_3.class;
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);
  }
}