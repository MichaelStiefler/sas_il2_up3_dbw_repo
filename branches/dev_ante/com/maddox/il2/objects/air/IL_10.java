package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class IL_10 extends Scheme1
  implements TypeStormovikArmored
{
  private float kangle = 0.0F;

  public void update(float paramFloat) { super.update(paramFloat);

    World.cur(); if ((this == World.getPlayerAircraft()) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret.length > 0) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astatePilotStates[1] < 90) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsAIControlled))
    {
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getOverload() > 7.0F) || (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getOverload() < -0.7F)) Voice.speakRearGunShake();
    }
    this.kangle = (0.95F * this.kangle + 0.05F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator());
    if (this.kangle > 1.0F) this.kangle = 1.0F;
    hierMesh().chunkSetAngles("radiator1_D0", 0.0F, -23.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("radiator2_D0", 0.0F, -70.0F * this.kangle, 0.0F);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.47F, 0.0F, -45.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.6F, 0.0F, -85.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.6F, 0.0F, -90.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.6F, 0.0F, -69.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.11F, 0.0F, -70.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.11F, 0.0F, -70.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.44F, 0.99F, 0.0F, -85.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, Aircraft.cvt(paramFloat, 0.44F, 0.99F, 0.0F, -90.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.44F, 0.99F, 0.0F, -69.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, Aircraft.cvt(paramFloat, 0.44F, 0.54F, 0.0F, -70.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, Aircraft.cvt(paramFloat, 0.44F, 0.54F, 0.0F, -70.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }
  public void moveWheelSink() {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() < 0.99F) {
      return;
    }
    resetYPRmodifier();
    Aircraft.ypr[1] = -90.0F;

    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.228F, 0.0F, -0.228F);
    hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.228F, 0.0F, 0.228F);
    hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
  }

  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay1_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    if (paramArrayOfFloat[0] < -43.0F) { paramArrayOfFloat[0] = -43.0F; bool = false;
    } else if (paramArrayOfFloat[0] > 43.0F) { paramArrayOfFloat[0] = 43.0F; bool = false; }
    if (paramArrayOfFloat[1] < -2.0F) { paramArrayOfFloat[1] = -2.0F; bool = false; }
    if (paramArrayOfFloat[1] > 56.0F) { paramArrayOfFloat[1] = 56.0F; bool = false; }
    return bool;
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      break;
    case 1:
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret.length == 0) return;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
    }
  }

  public void doMurderPilot(int paramInt) {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i = 0;

    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor")) {
        debuggunnery("Armor: Hit..");
        if (paramString.startsWith("xxarmorp")) {
          i = paramString.charAt(8) - '0';
          switch (i) {
          case 1:
            getEnergyPastArmor(12.880000114440918D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
            if ((paramShot.power > 0.0F) || (World.Rnd().nextFloat() >= 0.23F)) break;
            doRicochet(paramShot); break;
          case 2:
          case 5:
          case 7:
          case 8:
            getEnergyPastArmor(8.0D / (Math.abs(Aircraft.v1.jdField_y_of_type_Double) + 9.999999747378752E-005D), paramShot);
            paramShot.powerType = 0;
            if ((paramShot.power > 0.0F) || (Math.abs(Aircraft.v1.jdField_x_of_type_Double) <= 0.8659999966621399D)) break;
            doRicochet(paramShot); break;
          case 3:
            getEnergyPastArmor(16.0D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
            break;
          case 4:
            getEnergyPastArmor(20.200000762939453D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
            if (paramShot.power > 0.0F) break;
            doRicochetBack(paramShot); break;
          case 6:
            getEnergyPastArmor(8.0D / (Math.abs(Aircraft.v1.jdField_z_of_type_Double) + 9.999999747378752E-005D), paramShot);
            paramShot.powerType = 0;
            if ((paramShot.power > 0.0F) || (Math.abs(Aircraft.v1.jdField_z_of_type_Double) >= 0.4399999976158142D)) break;
            doRicochet(paramShot);
          }

        }

        return;
      }

      if (paramString.startsWith("xxcontrols")) {
        debuggunnery("Controls: Hit..");
        i = paramString.charAt(10) - '0';
        if (paramString.endsWith("10")) {
          i = 10;
        }
        switch (i) {
        case 5:
          if (getEnergyPastArmor(0.25F / ((float)Math.sqrt(Aircraft.v1.jdField_y_of_type_Double * Aircraft.v1.jdField_y_of_type_Double + Aircraft.v1.jdField_z_of_type_Double * Aircraft.v1.jdField_z_of_type_Double) + 1.0E-004F), paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.05F) {
            debuggunnery("Controls: Elevator Wiring Hit, Elevator Controls Disabled..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
          }
          if (World.Rnd().nextFloat() >= 0.75F) break;
          debuggunnery("Controls: Rudder Wiring Hit, Rudder Controls Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2); break;
        case 3:
        case 4:
          if ((getEnergyPastArmor(4.0D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.5F)) break;
          debuggunnery("Controls: Aileron Wiring Hit, Aileron Controls Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0);
        }

      }

      if (paramString.startsWith("xxspar")) {
        debuggunnery("Spar Construction: Hit..");
        if ((paramString.startsWith("xxsparli")) && 
          (chunkDamageVisible("WingLIn") > 2) && (World.Rnd().nextFloat() > Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLIn Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparri")) && 
          (chunkDamageVisible("WingRIn") > 2) && (World.Rnd().nextFloat() > Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingRIn Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlm")) && 
          (chunkDamageVisible("WingLMid") > 2) && (World.Rnd().nextFloat() > Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLMid Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparrm")) && 
          (chunkDamageVisible("WingRMid") > 2) && (World.Rnd().nextFloat() > Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingRMid Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlo")) && 
          (chunkDamageVisible("WingLOut") > 2) && (World.Rnd().nextFloat() > Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLOut Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparro")) && 
          (chunkDamageVisible("WingROut") > 2) && (World.Rnd().nextFloat() > Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingROut Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxspart")) && 
          (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.86F / (float)Math.sqrt(Aircraft.v1.jdField_y_of_type_Double * Aircraft.v1.jdField_y_of_type_Double + Aircraft.v1.jdField_z_of_type_Double * Aircraft.v1.jdField_z_of_type_Double), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          debuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }

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

      }

      if (paramString.startsWith("xxeng")) {
        debuggunnery("Engine Module: Hit..");
        if (paramString.endsWith("prop")) {
          if ((getEnergyPastArmor(3.6F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.8F))
            if (World.Rnd().nextFloat() < 0.5F) {
              debuggunnery("Engine Module: Prop Governor Hit, Disabled..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 3);
            } else {
              debuggunnery("Engine Module: Prop Governor Hit, Oil Pipes Damaged..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 4);
            }
        }
        else if (paramString.endsWith("gear")) {
          if ((getEnergyPastArmor(4.6F, paramShot) > 0.0F) && 
            (World.Rnd().nextFloat() < 0.25F)) {
            debuggunnery("Engine Module: Reductor Hit, Bullet Jams Reductor Gear..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setEngineStuck(paramShot.initiator);
          }
        }
        else if (paramString.endsWith("feed")) {
          if ((getEnergyPastArmor(3.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
            if (World.Rnd().nextFloat() < 0.1F) {
              debuggunnery("Engine Module: Feed Lines Hit, Engine Stalled..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setEngineStops(paramShot.initiator);
            }
            if (World.Rnd().nextFloat() < 0.05F) {
              debuggunnery("Engine Module: Feed Gear Hit, Engine Jams..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, 0);
            }
            if (World.Rnd().nextFloat() < 0.1F) {
              debuggunnery("Engine Module: Feed Gear Hit, Half Cylinder Feed Cut-Out..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setCyliderKnockOut(paramShot.initiator, 6);
            }
          }
        } else if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(2.2F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 175000.0F) {
              debuggunnery("Engine Module: Crank Case Hit, Bullet Jams Ball Bearings..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, 0);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
              debuggunnery("Engine Module: Crank Case Hit, Readyness Reduced to " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness() + "..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 2);
            }
          }
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setReadyness(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
          debuggunnery("Engine Module: Crank Case Hit, Readyness Reduced to " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness() + "..");
          getEnergyPastArmor(22.5F, paramShot);
        } else if (paramString.endsWith("cyls")) {
          if ((getEnergyPastArmor(1.3F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersRatio() * 1.75F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
            debuggunnery("Engine Module: Cylinders Assembly Hit, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylinders() + " Operating..");
            if (World.Rnd().nextFloat() < paramShot.power / 48000.0F) {
              debuggunnery("Engine Module: Cylinders Assembly Hit, Engine Fires..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 3);
            }
            if (World.Rnd().nextFloat() < 0.01F) {
              debuggunnery("Engine Module: Cylinders Assembly Hit, Bullet Jams Piston Head..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, 0);
            }
            getEnergyPastArmor(22.5F, paramShot);
          }
          if ((Math.abs(paramPoint3d.jdField_y_of_type_Double) < 0.137999996542931D) && 
            (getEnergyPastArmor(3.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
            if (World.Rnd().nextFloat() < 0.1F) {
              debuggunnery("Engine Module: Feed Lines Hit, Engine Stalled..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setEngineStops(paramShot.initiator);
            }
            if (World.Rnd().nextFloat() < 0.05F) {
              debuggunnery("Engine Module: Feed Gear Hit, Engine Jams..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, 0);
            }
            if (World.Rnd().nextFloat() < 0.1F) {
              debuggunnery("Engine Module: Feed Gear Hit, Half Cylinder Feed Cut-Out..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setCyliderKnockOut(paramShot.initiator, 6);
            }
          }
        }
        else if ((paramString.startsWith("xxeng1oil")) && 
          (getEnergyPastArmor(0.5F, paramShot) > 0.0F)) {
          debuggunnery("Engine Module: Oil Radiator Hit, Oil Radiator Pierced..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
        }

      }

      if ((paramString.startsWith("xxOil")) && 
        (getEnergyPastArmor(3.5F, paramShot) > 0.0F)) {
        debuggunnery("Engine Module: Oil Tank Hit..");
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
      }

      if ((paramString.startsWith("xxtank")) && 
        (getEnergyPastArmor(0.12F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
        if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[0] == 0) {
          debuggunnery("Fuel System: Fuel Tank Pierced..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, 1);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetTankState(paramShot.initiator, 0, 1);
        } else if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[i] == 1) {
          debuggunnery("Fuel System: Fuel Tank Pierced..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, 1);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetTankState(paramShot.initiator, 0, 2);
        }
        if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.5F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, 2);
          debuggunnery("Fuel System: Fuel Tank Pierced, State Shifted..");
        }

      }

      if (paramString.startsWith("xxmgun")) {
        if (paramString.endsWith("01")) {
          debuggunnery("Armament System: Left Machine Gun: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 0);
        }
        if (paramString.endsWith("02")) {
          debuggunnery("Armament System: Right Machine Gun: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 1);
        }
        getEnergyPastArmor(World.Rnd().nextFloat(0.3F, 12.6F), paramShot);
      }
      if (paramString.startsWith("xxcannon")) {
        if ((paramString.endsWith("01")) && 
          (getEnergyPastArmor(0.25F, paramShot) > 0.0F)) {
          debuggunnery("Armament System: Left Cannon: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 0);
        }

        if ((paramString.endsWith("02")) && 
          (getEnergyPastArmor(0.25F, paramShot) > 0.0F)) {
          debuggunnery("Armament System: Right Cannon: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 1);
        }

        getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F), paramShot);
      }
      if (paramString.startsWith("xxbomb")) {
        if ((World.Rnd().nextFloat() < 0.00345F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0].haveBullets())) {
          debuggunnery("Armament System: Bomb Payload Detonated..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, 10);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 1, 10);
          nextDMGLevels(3, 2, "CF_D0", paramShot.initiator);
        }
        return;
      }

      return;
    }

    if (paramString.startsWith("xcockpit")) {
      if (paramPoint3d.jdField_z_of_type_Double > 0.775D) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x1);
        if ((Aircraft.v1.jdField_x_of_type_Double < -0.9D) && (getEnergyPastArmor(12.0D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x2);
        }
      }
      else if (paramPoint3d.jdField_y_of_type_Double > 0.0D) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x4);
      } else {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x10);
      }

      if (World.Rnd().nextFloat() < 0.067F) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x8);
      }
      if (World.Rnd().nextFloat() < 0.067F) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x20);
      }
      if (World.Rnd().nextFloat() < 0.067F) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x40);
      }
    }
    if (paramString.startsWith("xcf")) {
      if (paramPoint3d.jdField_z_of_type_Double < 0.672D) {
        getEnergyPastArmor(6.0D / (Math.abs(Math.sqrt(Aircraft.v1.jdField_y_of_type_Double * Aircraft.v1.jdField_y_of_type_Double + Aircraft.v1.jdField_z_of_type_Double * Aircraft.v1.jdField_z_of_type_Double)) + 9.999999747378752E-005D), paramShot);
        if ((paramShot.power <= 0.0F) && (Math.abs(Aircraft.v1.jdField_x_of_type_Double) > 0.8659999966621399D)) {
          doRicochet(paramShot);
        }
      }
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
    }
    else if (paramString.startsWith("xeng")) {
      if (paramPoint3d.jdField_z_of_type_Double > 0.549D)
        getEnergyPastArmor(2.0D / (Math.abs(Aircraft.v1.jdField_z_of_type_Double) + 9.999999747378752E-005D), paramShot);
      else if (paramPoint3d.jdField_x_of_type_Double > 2.819D)
        getEnergyPastArmor(6.0D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
      else {
        getEnergyPastArmor(4.0D / (Math.abs(Math.sqrt(Aircraft.v1.jdField_y_of_type_Double * Aircraft.v1.jdField_y_of_type_Double + Aircraft.v1.jdField_z_of_type_Double * Aircraft.v1.jdField_z_of_type_Double)) + 9.999999747378752E-005D), paramShot);
      }
      if ((Math.abs(Aircraft.v1.jdField_x_of_type_Double) > 0.8659999966621399D) && (
        (paramShot.power <= 0.0F) || (World.Rnd().nextFloat() < 0.1F))) {
        doRicochet(paramShot);
      }

      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel")) {
      hitChunk("Keel1", paramShot);
    } else if (paramString.startsWith("xrudder")) {
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
    else if (paramString.startsWith("xturret")) {
      if (getEnergyPastArmor(0.25F, paramShot) > 0.0F) {
        debuggunnery("Armament System: Turret Machine Gun(s): Disabled..");
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(10, 0);
        getEnergyPastArmor(World.Rnd().nextFloat(0.1F, 26.35F), paramShot);
      }
    } else if (paramString.startsWith("xhelm")) {
      getEnergyPastArmor(World.Rnd().nextFloat(2.0F, 3.56F), paramShot);
      if (paramShot.power <= 0.0F)
        doRicochetBack(paramShot);
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

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    for (int i = 1; i < 3; i++)
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F) {
        if (hierMesh().chunkFindCheck("HMask" + i + "_D0") != -1) {
          hierMesh().chunkVisible("HMask" + i + "_D0", false);
        }
      }
      else if (hierMesh().chunkFindCheck("HMask" + i + "_D0") != -1)
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Il-10");
    Property.set(localClass, "meshName", "3DO/Plane/Il-10(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar05());
    Property.set(localClass, "meshName_ru", "3DO/Plane/Il-10(ru)/hier.him");
    Property.set(localClass, "PaintScheme_ru", new PaintSchemeFMPar03());
    Property.set(localClass, "meshName_pl", "3DO/Plane/Il-10(ru)/hier.him");
    Property.set(localClass, "PaintScheme_pl", new PaintSchemeBMPar05());
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);

    Property.set(localClass, "yearService", 1945.0F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Il-10.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitIL_10.class, CockpitIL_10_TGunner.class });

    Property.set(localClass, "LOSElevation", 0.93155F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 10, 2, 2, 2, 2, 9, 9, 9, 9, 3, 3, 3, 3, 9, 9 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_MGUN03", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalBomb01", "_ExternalBomb02", "_BombSpawn01", "_BombSpawn02", "_ExternalDev01", "_ExternalDev02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4xRS82", new String[] { "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4xBRS82", new String[] { "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4xRS132", new String[] { "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4xM13", new String[] { "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", "RocketGunM13", "RocketGunM13", "RocketGunM13", "RocketGunM13", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "176xAJ-2", new String[] { "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", null, null, null, null, null, null, null, null, null, null, "BombGunAmpoule 88", "BombGunAmpoule 88", "PylonKMB", "PylonKMB" });

    Aircraft.weaponsRegister(localClass, "144xPTAB2_5", new String[] { "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", null, null, null, null, null, null, null, null, null, null, "BombGunPTAB25 72", "BombGunPTAB25 72", "PylonKMB", "PylonKMB" });

    Aircraft.weaponsRegister(localClass, "12xAO10", new String[] { "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", null, null, null, null, null, null, null, null, null, null, "BombGunAO10 6", "BombGunAO10 6", "PylonKMB", "PylonKMB" });

    Aircraft.weaponsRegister(localClass, "4xFAB50", new String[] { "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", null, null, null, null, null, null, null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null });

    Aircraft.weaponsRegister(localClass, "4xFAB50_4xRS82", new String[] { "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null });

    Aircraft.weaponsRegister(localClass, "2xFAB100", new String[] { "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", null, null, null, null, null, null, null, null, "BombGunFAB100", "BombGunFAB100", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2xFAB1004BRS82", new String[] { "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "BombGunFAB100", "BombGunFAB100", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2xFAB1004BRS132", new String[] { "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "BombGunFAB100", "BombGunFAB100", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4xFAB100", new String[] { "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", null, null, null, null, null, null, null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}