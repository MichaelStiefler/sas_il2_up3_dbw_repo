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
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;

public class I_15xyz extends Scheme1
  implements TypeFighter, TypeTNBFighter
{
  public static boolean bChangedPit = false;
  private float suspension = 0.0F;
  public float suspR = 0.0F;
  public float suspL = 0.0F;

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor) {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      bChangedPit = true;
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor) {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      bChangedPit = true;
  }

  public boolean cut(String paramString)
  {
    boolean bool = super.cut(paramString);
    if (paramString.equalsIgnoreCase("WingLIn"))
      hierMesh().chunkVisible("WingLMid_CAP", true);
    else if (paramString.equalsIgnoreCase("WingRIn"))
      hierMesh().chunkVisible("WingRMid_CAP", true);
    return bool;
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    if (paramInt1 == 19)
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.hitCentreGear();
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void update(float paramFloat)
  {
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.FlapsControl = 0.0F;

    float f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, 10.0F);

    hierMesh().chunkSetAngles("Water_D0", 0.0F, f, 0.0F);
    super.update(paramFloat);
  }

  protected void moveFlap(float paramFloat)
  {
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    if (paramHierMesh.chunkFindCheck("SkiR1_D0") != -1)
    {
      float f1 = 15.0F;
      float f2 = (float)(Math.random() * 2.0D) - 1.0F;
      float f3 = (float)(Math.random() * 2.0D) - 1.0F;
      float f4 = (float)(Math.random() * 2.0D) - 1.0F;
      float f5 = (float)(Math.random() * 2.0D) - 1.0F;

      float f6 = f1 / 20.0F;

      paramHierMesh.chunkSetAngles("SkiR1_D0", 0.0F, -f1, 0.0F);
      paramHierMesh.chunkSetAngles("SkiL1_D0", 0.0F, -f1, 0.0F);
      paramHierMesh.chunkSetAngles("SkiC_D0", 0.0F, f1, 0.0F);

      paramHierMesh.chunkSetAngles("LSkiFrontDownWire1_d0", 0.0F, -f6 * 4.0F, f6 * 12.4F);
      paramHierMesh.chunkSetAngles("LSkiFrontDownWire2_d0", 0.0F, -f6 * 4.0F, f6 * 12.4F);

      Aircraft.ypr[0] = 0.0F;
      Aircraft.ypr[1] = 0.0F;
      Aircraft.ypr[2] = 0.0F;
      Aircraft.xyz[0] = (-0.16F * f6);
      Aircraft.xyz[1] = 0.0F;
      Aircraft.xyz[2] = 0.0F;
      paramHierMesh.chunkSetLocate("LSkiFrontUpWire_d0", Aircraft.xyz, Aircraft.ypr);

      paramHierMesh.chunkSetAngles("LWire1_d0", 0.0F, 6.5F * f6 + f6 * -20.0F * f4, f6 * 60.0F);
      paramHierMesh.chunkSetAngles("LWire12_d0", 0.0F, 6.5F * f6 + f6 * 20.0F * f5, f6 * 70.0F);

      float f7 = f6 * -5.0F;
      float f8 = f6 * -10.0F;
      float f9 = f6 * -15.0F;

      float f10 = f6 * 5.0F * f4;
      float f11 = f6 * 10.0F * f4;
      float f12 = f6 * -5.0F * f5;

      float f13 = f6 * 5.0F * f2;
      float f14 = f6 * 10.0F * f2;
      float f15 = f6 * -5.0F * f3;

      paramHierMesh.chunkSetAngles("LWire2_d0", 0.0F, f11, f7);
      paramHierMesh.chunkSetAngles("LWire3_d0", 0.0F, f10, f8);
      paramHierMesh.chunkSetAngles("LWire4_d0", 0.0F, f11, f8);
      paramHierMesh.chunkSetAngles("LWire5_d0", 0.0F, f10, f8);
      paramHierMesh.chunkSetAngles("LWire6_d0", 0.0F, f11, f9);
      paramHierMesh.chunkSetAngles("LWire7_d0", 0.0F, f10, f8);
      paramHierMesh.chunkSetAngles("LWire8_d0", 0.0F, f11, f9);
      paramHierMesh.chunkSetAngles("LWire9_d0", 0.0F, f10, f7);
      paramHierMesh.chunkSetAngles("LWire10_d0", 0.0F, f11, f7);
      paramHierMesh.chunkSetAngles("LWire11_d0", 0.0F, f10, f7);
      paramHierMesh.chunkSetAngles("LWire13_d0", 0.0F, f12, f8);
      paramHierMesh.chunkSetAngles("LWire14_d0", 0.0F, f12, f9);
      paramHierMesh.chunkSetAngles("LWire15_d0", 0.0F, f12, f8);
      paramHierMesh.chunkSetAngles("LWire16_d0", 0.0F, f12, f9);
      paramHierMesh.chunkSetAngles("LWire17_d0", 0.0F, 0.0F, f8);
      paramHierMesh.chunkSetAngles("LWire18_d0", 0.0F, f12, f8);
      paramHierMesh.chunkSetAngles("LWire19_d0", 0.0F, f12, f8);
      paramHierMesh.chunkSetAngles("LWire20_d0", 0.0F, f12, f8);
      paramHierMesh.chunkSetAngles("LWire21_d0", 0.0F, f12, f8);
      paramHierMesh.chunkSetAngles("LWire22_d0", 0.0F, f12, f8);

      paramHierMesh.chunkSetAngles("RSkiFrontDownWire1_d0", 0.0F, f6 * 4.0F, f6 * 12.4F);
      paramHierMesh.chunkSetAngles("RSkiFrontDownWire2_d0", 0.0F, f6 * 4.0F, f6 * 12.4F);

      paramHierMesh.chunkSetLocate("RSkiFrontUpWire_d0", Aircraft.xyz, Aircraft.ypr);

      paramHierMesh.chunkSetAngles("RWire1_d0", 0.0F, -6.5F * f6 + f6 * -20.0F * f2, f6 * 60.0F);
      paramHierMesh.chunkSetAngles("RWire12_d0", 0.0F, -6.5F * f6 + f6 * 20.0F * f3, f6 * 70.0F);

      paramHierMesh.chunkSetAngles("RWire2_d0", 0.0F, f14, f7);
      paramHierMesh.chunkSetAngles("RWire3_d0", 0.0F, f13, f8);
      paramHierMesh.chunkSetAngles("RWire4_d0", 0.0F, f14, f8);
      paramHierMesh.chunkSetAngles("RWire5_d0", 0.0F, f13, f9);
      paramHierMesh.chunkSetAngles("RWire6_d0", 0.0F, f14, f8);
      paramHierMesh.chunkSetAngles("RWire7_d0", 0.0F, f13, f8);
      paramHierMesh.chunkSetAngles("RWire8_d0", 0.0F, f14, f8);
      paramHierMesh.chunkSetAngles("RWire9_d0", 0.0F, f13, f7);
      paramHierMesh.chunkSetAngles("RWire10_d0", 0.0F, f14, f7);
      paramHierMesh.chunkSetAngles("RWire11_d0", 0.0F, f13, f7);

      paramHierMesh.chunkSetAngles("RWire13_d0", 0.0F, f15, f8);
      paramHierMesh.chunkSetAngles("RWire14_d0", 0.0F, f15, f8);
      paramHierMesh.chunkSetAngles("RWire15_d0", 0.0F, f15, f9);
      paramHierMesh.chunkSetAngles("RWire16_d0", 0.0F, f15, f8);
      paramHierMesh.chunkSetAngles("RWire17_d0", 0.0F, 0.0F, f9);
      paramHierMesh.chunkSetAngles("RWire18_d0", 0.0F, f15, f8);
      paramHierMesh.chunkSetAngles("RWire19_d0", 0.0F, f15, f8);
      paramHierMesh.chunkSetAngles("RWire20_d0", 0.0F, f15, f8);
      paramHierMesh.chunkSetAngles("RWire21_d0", 0.0F, f15, f8);
      paramHierMesh.chunkSetAngles("RWire22_d0", 0.0F, f15, f8);
    }
  }

  protected void moveGear(float paramFloat)
  {
  }

  public void moveWheelSink()
  {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround())
      this.suspension += 0.008F;
    else {
      this.suspension -= 0.008F;
    }
    if (this.suspension < 0.0F)
    {
      this.suspension = 0.0F;
      if (!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.bTailwheelLocked = true;
      }
    }
    if (this.suspension > 0.1F) {
      this.suspension = 0.1F;
    }
    Aircraft.xyz[0] = 0.0F;
    Aircraft.ypr[0] = 0.0F;
    Aircraft.ypr[1] = 0.0F;
    Aircraft.ypr[2] = 0.0F;
    Aircraft.xyz[2] = 0.0F;
    float f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed(), 0.0F, 25.0F, 0.0F, 1.0F);

    this.suspL = (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0] * f + this.suspension);

    Aircraft.xyz[1] = (-Aircraft.cvt(this.suspL, 0.0F, 0.24F, 0.0F, 0.24F));
    hierMesh().chunkSetLocate("GearL2_D0", Aircraft.xyz, Aircraft.ypr);

    this.suspR = (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1] * f + this.suspension);
    Aircraft.xyz[1] = (-Aircraft.cvt(this.suspR, 0.0F, 0.24F, 0.0F, 0.24F));
    hierMesh().chunkSetLocate("GearR2_D0", Aircraft.xyz, Aircraft.ypr);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        Aircraft.debugprintln(this, "*** Armor: Hit..");
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(8.100000381469727D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-006D), paramShot);
        }
      }
      else
      {
        if (paramString.startsWith("xxcontrols")) {
          i = paramString.charAt(10) - '0';
          switch (i) {
          case 1:
            if (getEnergyPastArmor(2.3F, paramShot) <= 0.0F) break;
            if (World.Rnd().nextFloat() < 0.25F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2);
              Aircraft.debugprintln(this, "*** Rudder Controls: Disabled..");
            }

            if (World.Rnd().nextFloat() >= 0.25F) break;
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
            Aircraft.debugprintln(this, "*** Elevator Controls: Disabled..");
          case 2:
          case 3:
            if (getEnergyPastArmor(1.5F, paramShot) > 0.0F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0);
              Aircraft.debugprintln(this, "*** Aileron Controls: Control Crank Destroyed..");
            }

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
            if ((getEnergyPastArmor(0.449999988079071D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.05F))
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

        if ((paramString.startsWith("xxtank1")) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F))
        {
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[0] == 0) {
            Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");

            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, 2);
          }
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.999F))
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
    else if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit"))) {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
      if (paramString.startsWith("xcf1")) {
        if ((paramPoint3d.jdField_x_of_type_Double > -1.147D) && (paramPoint3d.jdField_x_of_type_Double < -0.869D) && (paramPoint3d.jdField_z_of_type_Double > 0.653D))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x2);
        }
        if (World.Rnd().nextFloat() < 0.012F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x1);
        }
        if ((paramPoint3d.jdField_x_of_type_Double > -1.195D) && (paramPoint3d.jdField_x_of_type_Double < -0.904D) && (paramPoint3d.jdField_z_of_type_Double > 0.203D))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x40);
        }
        if ((paramPoint3d.jdField_x_of_type_Double > -1.402D) && (paramPoint3d.jdField_x_of_type_Double < -1.125D) && (paramPoint3d.jdField_z_of_type_Double > -0.032D) && (paramPoint3d.jdField_z_of_type_Double < 0.501D))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x4);
        }
      }
    } else if (paramString.startsWith("xeng")) {
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

  public void rareAction(float paramFloat, boolean paramBoolean) {
    super.rareAction(paramFloat, paramBoolean);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
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

  public void msgExplosion(Explosion paramExplosion) {
    setExplosion(paramExplosion);
    if ((paramExplosion.chunkName != null) && (paramExplosion.power > 0.0F) && (paramExplosion.chunkName.startsWith("Tail1")))
    {
      if (World.Rnd().nextFloat(0.0F, 0.038F) < paramExplosion.power)
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramExplosion.initiator, 1);
      if (World.Rnd().nextFloat(0.0F, 0.042F) < paramExplosion.power)
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramExplosion.initiator, 2);
    }
    super.msgExplosion(paramExplosion);
  }
}