package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public abstract class P_40 extends Scheme1
  implements TypeFighter
{
  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0] != null))
      hierMesh().chunkVisible("Pilon_D0", true);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = Math.max(-paramFloat * 1100.0F, -90.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, f, 0.0F);

    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 90.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 92.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -99.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 92.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, -99.0F * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) {
    float f1 = Math.max(-paramFloat * 1100.0F, -90.0F);
    HierMesh localHierMesh = hierMesh();
    localHierMesh.chunkSetAngles("GearC3_D0", 0.0F, f1, 0.0F);
    localHierMesh.chunkSetAngles("GearC4_D0", 0.0F, f1, 0.0F);

    localHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 90.0F * paramFloat, 0.0F);

    float f2 = Aircraft.cvt(paramFloat, 0.0F, 0.7F, 0.0F, 1.0F);
    localHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 92.0F * f2, 0.0F);
    localHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -45.0F * f2, 0.0F);
    localHierMesh.chunkSetAngles("GearL4_D0", 0.0F, f1, 0.0F);
    localHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -99.0F * f2, 0.0F);

    f2 = Aircraft.cvt(paramFloat, 0.2F, 1.0F, 0.0F, 1.0F);
    localHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 92.0F * f2, 0.0F);
    localHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -45.0F * f2, 0.0F);
    localHierMesh.chunkSetAngles("GearR4_D0", 0.0F, f1, 0.0F);
    localHierMesh.chunkSetAngles("GearR5_D0", 0.0F, -99.0F * f2, 0.0F);
  }
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC99_D0", paramFloat, 0.0F, 0.0F);
  }
  public void moveWheelSink() {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.17F, 0.0F, -0.1689F);
    Aircraft.ypr[1] = (-92.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear());
    hierMesh().chunkSetLocate("GearL5_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.17F, 0.0F, 0.1689F);
    Aircraft.ypr[1] = (-92.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear());
    hierMesh().chunkSetLocate("GearR5_D0", Aircraft.xyz, Aircraft.ypr);
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsAboutToBailout) break;
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
    }
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -60.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap1_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap2_D0", 0.0F, f, 0.0F);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    setExplosion(paramExplosion);
    if (paramExplosion.chunkName != null) {
      if (paramExplosion.chunkName.startsWith("WingL")) {
        if (World.Rnd().nextFloat() < 0.02F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 0);
        }
        if (World.Rnd().nextFloat() < 0.02F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 1);
        }
        if (World.Rnd().nextFloat() < 0.02F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 2);
        }
      }
      if (paramExplosion.chunkName.startsWith("WingR")) {
        if (World.Rnd().nextFloat() < 0.02F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 3);
        }
        if (World.Rnd().nextFloat() < 0.02F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 4);
        }
        if (World.Rnd().nextFloat() < 0.02F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 5);
        }
      }
    }
    super.msgExplosion(paramExplosion);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(15.0F / (1.0E-005F + (float)Math.abs(Aircraft.v1.jdField_x_of_type_Double)), paramShot);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x2);
        } else if (paramString.endsWith("p2")) {
          getEnergyPastArmor(4.0F / (1.0E-005F + (float)Math.abs(Aircraft.v1.jdField_x_of_type_Double)), paramShot);
        } else if (paramString.endsWith("p3")) {
          getEnergyPastArmor(2.0F / (1.0E-005F + (float)Math.abs(Aircraft.v1.jdField_x_of_type_Double)), paramShot);
        }
      }
      if (paramString.startsWith("xxcontrols")) {
        if (paramString.endsWith("1")) {
          if (World.Rnd().nextFloat() < 0.3F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 6);
            Aircraft.debugprintln(this, "*** Engine Controls Out..");
          }
          if (World.Rnd().nextFloat() < 0.3F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 1);
            Aircraft.debugprintln(this, "*** Engine Controls Out..");
          }
        } else if (paramString.endsWith("2")) {
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
            Aircraft.debugprintln(this, "*** Evelator Controls Out..");
          }
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0);
            Aircraft.debugprintln(this, "*** Ailerones Controls Out..");
          }
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2);
            Aircraft.debugprintln(this, "*** Rudder Controls Out..");
          }
          if (World.Rnd().nextFloat() < 0.3F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 6);
            Aircraft.debugprintln(this, "*** Engine Controls Out..");
          }
          if (World.Rnd().nextFloat() < 0.3F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 1);
            Aircraft.debugprintln(this, "*** Engine Controls Out..");
          }
        } else if (paramString.endsWith("3")) {
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2);
            Aircraft.debugprintln(this, "*** Rudder Controls Out..");
          }
        } else if (((paramString.endsWith("4")) || (paramString.endsWith("5"))) && 
          (World.Rnd().nextFloat() < 0.3F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0);
          Aircraft.debugprintln(this, "*** Ailerones Controls Out..");
        }
      }

      if (paramString.startsWith("xxeng1")) {
        if ((paramString.endsWith("prp")) && 
          (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setKillPropAngleDevice(paramShot.initiator);
        }

        if ((paramString.endsWith("cas")) && 
          (getEnergyPastArmor(0.7F, paramShot) > 0.0F)) {
          if (World.Rnd().nextFloat(20000.0F, 200000.0F) < paramShot.power) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, 0);
            Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Stucks..");
          }
          if (World.Rnd().nextFloat(10000.0F, 50000.0F) < paramShot.power) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 2);
            Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Damaged..");
          }
          if (World.Rnd().nextFloat(8000.0F, 28000.0F) < paramShot.power) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setCyliderKnockOut(paramShot.initiator, 1);
            Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Cylinder Feed Out, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylinders() + " Left..");
          }
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setReadyness(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
          Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Readyness Reduced to " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness() + "..");
        }

        if ((paramString.endsWith("cyl")) && 
          (getEnergyPastArmor(0.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersRatio() * 1.75F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
          Aircraft.debugprintln(this, "*** Engine Cylinders Hit, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylinders() + " Left..");
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[0] < 1) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 1);
          }
          if (World.Rnd().nextFloat() < paramShot.power / 24000.0F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 3);
            Aircraft.debugprintln(this, "*** Engine Cylinders Hit - Engine Fires..");
          }
          getEnergyPastArmor(25.0F, paramShot);
        }

        if ((paramString.endsWith("sup")) && 
          (getEnergyPastArmor(0.05F, paramShot) > 0.0F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setKillCompressor(paramShot.initiator);
        }

        if (paramString.endsWith("sup")) {
          if ((World.Rnd().nextFloat() < 0.3F) && (getEnergyPastArmor(0.05F, paramShot) > 0.0F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setKillPropAngleDeviceSpeeds(paramShot.initiator);
          }
          if ((World.Rnd().nextFloat() < 0.3F) && (getEnergyPastArmor(0.05F, paramShot) > 0.0F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setMagnetoKnockOut(paramShot.initiator, 0);
          }
          if ((World.Rnd().nextFloat() < 0.3F) && (getEnergyPastArmor(0.05F, paramShot) > 0.0F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setMagnetoKnockOut(paramShot.initiator, 1);
          }
          if ((World.Rnd().nextFloat() < 0.3F) && (getEnergyPastArmor(0.05F, paramShot) > 0.0F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setEngineStuck(paramShot.initiator);
          }
          if ((World.Rnd().nextFloat() < 0.3F) && (getEnergyPastArmor(0.05F, paramShot) > 0.0F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setEngineStops(paramShot.initiator);
          }
        }
        if (paramString.endsWith("oil")) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
        }
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 1);
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.11F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 2);
          }
        }
      }
      if (paramString.startsWith("xxmgun")) {
        i = paramString.charAt(6) - '1';
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, i);
      }
      if (paramString.startsWith("xxammo")) {
        i = paramString.charAt(6) - '0';
        if (World.Rnd().nextFloat(0.0F, 20000.0F) < paramShot.power) {
          switch (i) {
          case 1:
            if (World.Rnd().nextFloat() < 0.5F)
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 2);
            else {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 1);
            }
            break;
          case 2:
            if (World.Rnd().nextFloat() < 0.5F)
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 0);
            else {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 1);
            }
            break;
          case 3:
            if (World.Rnd().nextFloat() < 0.5F)
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 5);
            else {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 4);
            }
            break;
          case 4:
            if (World.Rnd().nextFloat() < 0.5F)
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 3);
            else {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 4);
            }
          }
        }
      }
      return;
    }

    if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit"))) {
      hitChunk("CF", paramShot);
    }
    if (paramString.startsWith("xcockpit")) {
      if (paramPoint3d.z > 0.75D) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x1);
      }
      if ((paramPoint3d.jdField_x_of_type_Double > -1.100000023841858D) && 
        (World.Rnd().nextFloat() < 0.1F)) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x40);
      }

      if (World.Rnd().nextFloat() < 0.25F) {
        if (paramPoint3d.y > 0.0D) {
          if (paramPoint3d.jdField_x_of_type_Double > -1.100000023841858D)
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x4);
          else {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x8);
          }
        }
        else if (paramPoint3d.jdField_x_of_type_Double > -1.100000023841858D)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x10);
        else {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x20);
        }
      }
    }
    else if (paramString.startsWith("xeng")) {
      hitChunk("Engine1", paramShot);
    } else if (paramString.startsWith("xtail")) {
      hitChunk("Tail1", paramShot);
    } else if (paramString.startsWith("xkeel")) {
      hitChunk("Keel1", paramShot);
    } else if (paramString.startsWith("xrudder")) {
      hitChunk("Rudder1", paramShot);
    } else if (paramString.startsWith("xstabl")) {
      hitChunk("StabL", paramShot);
    } else if (paramString.startsWith("xstabr")) {
      hitChunk("StabR", paramShot);
    } else if (paramString.startsWith("xvatorl")) {
      hitChunk("VatorL", paramShot);
    } else if (paramString.startsWith("xvatorr")) {
      hitChunk("VatorR", paramShot);
    } else if (paramString.startsWith("xwing")) {
      if (paramString.startsWith("xwinglin")) {
        hitChunk("WingLIn", paramShot);
      }
      if (paramString.startsWith("xwingrin")) {
        hitChunk("WingRIn", paramShot);
      }
      if (paramString.startsWith("xwinglmid")) {
        hitChunk("WingLMid", paramShot);
      }
      if (paramString.startsWith("xwingrmid")) {
        hitChunk("WingRMid", paramShot);
      }
      if (paramString.startsWith("xwinglout")) {
        hitChunk("WingLOut", paramShot);
      }
      if (paramString.startsWith("xwingrout"))
        hitChunk("WingROut", paramShot);
    }
    else if (paramString.startsWith("xarone")) {
      if (paramString.startsWith("xaronel")) {
        hitChunk("AroneL", paramShot);
      }
      if (paramString.startsWith("xaroner"))
        hitChunk("AroneR", paramShot);
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
    case 11:
      hierMesh().chunkVisible("Wire_D0", false);
      break;
    case 35:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 0);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 1);
      break;
    case 38:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 4);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 5);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void update(float paramFloat)
  {
    float f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator(), 0.0F, 1.0F, 27.0F, -11.0F);
    hierMesh().chunkSetAngles("Water2_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Water3_D0", 0.0F, f, 0.0F);
    f = Math.min(f, 10.5F);
    hierMesh().chunkSetAngles("Water1_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Water4_D0", 0.0F, f, 0.0F);
    super.update(paramFloat);
  }

  static
  {
    Property.set(CLASS.THIS(), "originCountry", PaintScheme.countryUSA);
  }
}