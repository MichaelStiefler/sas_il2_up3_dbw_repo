package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public abstract class MOSQUITO extends Scheme2
{
  private float kangle1;
  private float kangle2;

  public MOSQUITO()
  {
    this.kangle1 = 0.0F;
    this.kangle2 = 0.0F;
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if (this.thisWeaponsName.startsWith("8x"))
    {
      hierMesh().chunkVisible("Rack1_D0", false);
      hierMesh().chunkVisible("Rack2_D0", false);
    }
    else {
      hierMesh().chunkVisible("Rack1_D0", true);
      hierMesh().chunkVisible("Rack2_D0", true);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
    {
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
    }
    else {
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
      hierMesh().chunkVisible("HMask2_D0", hierMesh().isChunkVisible("Pilot2_D0"));
    }
  }

  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay01_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay02_D0", 0.0F, -90.0F * paramFloat, 0.0F);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.0F, 0.6F, 0.0F, -80.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 0.85F, 0.0F, -114.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 0.85F, 0.0F, -59.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 0.85F, 0.0F, -116.5F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 0.15F, 0.0F, -42.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL8_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 0.15F, 0.0F, -38.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.2F, 0.95F, 0.0F, -114.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.2F, 0.95F, 0.0F, -59.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.2F, 0.95F, 0.0F, -116.5F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, Aircraft.cvt(paramFloat, 0.2F, 0.25F, 0.0F, -42.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR8_D0", 0.0F, Aircraft.cvt(paramFloat, 0.2F, 0.25F, 0.0F, -38.0F), 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat)
  {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }

  public void moveWheelSink()
  {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.14F, 0.0F, 0.14F);
    hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.14F, 0.0F, 0.14F);
    hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor"))
      {
        if ((paramString.endsWith("p1")) || (paramString.endsWith("p2")))
          getEnergyPastArmor(16.65F / (1.0E-005F + (float)Math.abs(Aircraft.v1.x)), paramShot);
        return;
      }
      if (paramString.startsWith("xxcontrols"))
      {
        if (paramString.endsWith("1"))
        {
          if (getEnergyPastArmor(3.2F, paramShot) > 0.0F)
          {
            Aircraft.debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0);
          }
        }
        else if (paramString.endsWith("2"))
        {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F)
          {
            if (World.Rnd().nextFloat() < 0.33F)
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 1);
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 6);
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x4);
              Aircraft.debugprintln(this, "*** Throttle Quadrant: Hit, Engine 1 Controls Disabled..");
            }
            if (World.Rnd().nextFloat() < 0.33F)
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 1, 1);
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 1, 6);
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x8);
              Aircraft.debugprintln(this, "*** Throttle Quadrant: Hit, Engine 2 Controls Disabled..");
            }
          }
        }
        else if ((paramString.endsWith("3")) || (paramString.endsWith("4")))
        {
          if (World.Rnd().nextFloat() < 0.12F)
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
            Aircraft.debugprintln(this, "*** Evelator Controls Out..");
          }
        }
        else if (World.Rnd().nextFloat() < 0.12F)
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0);
          Aircraft.debugprintln(this, "*** Arone Controls Out..");
        }
        return;
      }
      if (paramString.startsWith("xxspar"))
      {
        if (((paramString.endsWith("t1")) || (paramString.endsWith("t2")) || (paramString.endsWith("t3")) || (paramString.endsWith("t4"))) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.5F / (float)Math.sqrt(Aircraft.v1.jdField_y_of_type_Double * Aircraft.v1.jdField_y_of_type_Double + Aircraft.v1.jdField_z_of_type_Double * Aircraft.v1.jdField_z_of_type_Double), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** Tail1 Spars Broken in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("li1")) || (paramString.endsWith("li2")) || (paramString.endsWith("li3")) || (paramString.endsWith("li4"))) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("ri1")) || (paramString.endsWith("ri2")) || (paramString.endsWith("ri3")) || (paramString.endsWith("ri4"))) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("lm1")) || (paramString.endsWith("lm2")) || (paramString.endsWith("lm3")) || (paramString.endsWith("lm4"))) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("rm1")) || (paramString.endsWith("rm2")) || (paramString.endsWith("rm3")) || (paramString.endsWith("rm4"))) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("lo1")) || (paramString.endsWith("lo2"))) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("ro1")) || (paramString.endsWith("ro2"))) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }
        return;
      }
      if (paramString.startsWith("xxeng"))
      {
        i = paramString.charAt(5) - '1';
        Aircraft.debugprintln(this, "*** Engine" + i + " Hit..");
        if ((paramString.endsWith("case")) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F))
        {
          if (World.Rnd().nextFloat() < paramShot.power / 200000.0F)
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, i);
            Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Stucks..");
          }
          if (World.Rnd().nextFloat() < paramShot.power / 50000.0F)
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 2);
            Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Damaged..");
          }
          if (World.Rnd().nextFloat() < paramShot.power / 28000.0F)
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setCyliderKnockOut(paramShot.initiator, 1);
            Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Cylinder Feed Out, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylinders() + " Left..");
          }
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setReadyness(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
          Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Readyness Reduced to " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getReadyness() + "..");
        }
        if ((paramString.endsWith("cyl1")) && (getEnergyPastArmor(0.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylindersRatio() * 1.75F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
          Aircraft.debugprintln(this, "*** Engine Cylinders Hit, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylinders() + " Left..");
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[i] < 1)
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 1);
          if (World.Rnd().nextFloat() < paramShot.power / 24000.0F)
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 3);
            Aircraft.debugprintln(this, "*** Engine Cylinders Hit - Engine Fires..");
          }
          getEnergyPastArmor(25.0F, paramShot);
        }
        if ((paramString.endsWith("supc")) && (getEnergyPastArmor(0.05F, paramShot) > 0.0F))
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setKillCompressor(paramShot.initiator);
        if ((paramString.endsWith("prop")) && (getEnergyPastArmor(0.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F))
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].setKillPropAngleDevice(paramShot.initiator);
        return;
      }
      if (paramString.startsWith("xxtank"))
      {
        i = paramString.charAt(6) - '0';
        switch (i)
        {
        case 1:
        case 2:
          doHitMeATank(paramShot, 0);
          break;
        case 3:
          doHitMeATank(paramShot, 1);
          break;
        case 4:
        case 5:
          doHitMeATank(paramShot, 2);
        }

        return;
      }
      if ((paramString.startsWith("xxw1")) || (paramString.startsWith("xxoil1")))
      {
        if ((World.Rnd().nextFloat() < 0.12F) && (getEnergyPastArmor(2.25F, paramShot) > 0.0F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
          Aircraft.debugprintln(this, "*** Oil Radiator L Hit..");
        }
        return;
      }
      if ((paramString.startsWith("xxw2")) || (paramString.startsWith("xxoil1")))
      {
        if ((World.Rnd().nextFloat() < 0.12F) && (getEnergyPastArmor(2.25F, paramShot) > 0.0F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 1);
          Aircraft.debugprintln(this, "*** Oil Radiator R Hit..");
        }
        return;
      }
      if (paramString.startsWith("xxlock"))
      {
        debuggunnery("Lock Construction: Hit..");
        if ((paramString.startsWith("xxlockr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
        }
        if ((paramString.startsWith("xxlockvl")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          debuggunnery("Lock Construction: VatorL Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
        }
        if ((paramString.startsWith("xxlockvr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          debuggunnery("Lock Construction: VatorR Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
        }
        return;
      }
      if (paramString.startsWith("xxmgun0"))
      {
        i = paramString.charAt(7) - '1';
        if (getEnergyPastArmor(0.75F, paramShot) > 0.0F)
        {
          debuggunnery("Armament: Machine Gun (" + i + ") Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, i);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcannon0"))
      {
        i = paramString.charAt(9) - '1';
        if (getEnergyPastArmor(6.29F, paramShot) > 0.0F)
        {
          debuggunnery("Armament: Cannon (" + i + ") Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, i);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
        return;
      }

      return;
    }

    if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit")))
    {
      hitChunk("CF", paramShot);
      if (paramPoint3d.x > 0.0D)
      {
        if (World.Rnd().nextFloat() < 0.1F)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x40);
        if (paramPoint3d.jdField_z_of_type_Double > 0.4D)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x1);
      }
    }
    else if (paramString.startsWith("xengine1"))
    {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xengine2"))
    {
      if (chunkDamageVisible("Engine2") < 2)
        hitChunk("Engine2", paramShot);
    }
    else if (paramString.startsWith("xtail"))
    {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel")) {
      hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xrudder")) {
      hitChunk("Rudder1", paramShot);
    }
    else if (paramString.startsWith("xstab"))
    {
      if (paramString.startsWith("xstabl"))
        hitChunk("StabL", paramShot);
      if (paramString.startsWith("xstabr"))
        hitChunk("StabR", paramShot);
    }
    else if (paramString.startsWith("xvator"))
    {
      if (paramString.startsWith("xvatorl"))
        hitChunk("VatorL", paramShot);
      if (paramString.startsWith("xvatorr"))
        hitChunk("VatorR", paramShot);
    }
    else if (paramString.startsWith("xwing"))
    {
      if ((paramString.startsWith("xwinglin")) && (chunkDamageVisible("WingLIn") < 3))
        hitChunk("WingLIn", paramShot);
      if ((paramString.startsWith("xwingrin")) && (chunkDamageVisible("WingRIn") < 3))
        hitChunk("WingRIn", paramShot);
      if (paramString.startsWith("xwinglmid"))
      {
        if (chunkDamageVisible("WingLMid") < 3)
          hitChunk("WingLMid", paramShot);
        if (World.Rnd().nextFloat() < paramShot.mass + 0.02F)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
      }
      if ((paramString.startsWith("xwingrmid")) && (chunkDamageVisible("WingRMid") < 3))
        hitChunk("WingRMid", paramShot);
      if ((paramString.startsWith("xwinglout")) && (chunkDamageVisible("WingLOut") < 3))
        hitChunk("WingLOut", paramShot);
      if ((paramString.startsWith("xwingrout")) && (chunkDamageVisible("WingROut") < 3))
        hitChunk("WingROut", paramShot);
    }
    else if (paramString.startsWith("xarone"))
    {
      if (paramString.startsWith("xaronel"))
        hitChunk("AroneL", paramShot);
      if (paramString.startsWith("xaroner"))
        hitChunk("AroneR", paramShot);
    }
    else if (paramString.startsWith("xgear"))
    {
      if (paramString.endsWith("1"))
      {
        if (World.Rnd().nextFloat() < 0.05F)
        {
          debuggunnery("Hydro System: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setInternalDamage(paramShot.initiator, 0);
        }
      }
      else if ((World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 3.435F), paramShot) > 0.0F))
      {
        debuggunnery("Undercarriage: Stuck..");
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setInternalDamage(paramShot.initiator, 3);
      }
    }
    else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead")))
    {
      i = 0;
      int j;
      if (paramString.endsWith("a"))
      {
        i = 1;
        j = paramString.charAt(6) - '1';
      }
      else if (paramString.endsWith("b"))
      {
        i = 2;
        j = paramString.charAt(6) - '1';
      }
      else {
        j = paramString.charAt(5) - '1';
      }
      hitFlesh(j, paramShot, i);
    }
  }

  private final void doHitMeATank(Shot paramShot, int paramInt)
  {
    if (getEnergyPastArmor(0.2F, paramShot) > 0.0F)
      if (paramShot.power < 14100.0F)
      {
        if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[paramInt] == 0)
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, paramInt, 1);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetTankState(paramShot.initiator, paramInt, 1);
        }
        if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[paramInt] > 0) && ((World.Rnd().nextFloat() < 0.02F) || ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.25F))))
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, paramInt, 2);
      }
      else {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, paramInt, World.Rnd().nextInt(0, (int)(paramShot.power / 56000.0F)));
      }
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1)
    {
    case 13:
      return false;
    case 11:
    case 17:
    case 19:
      hierMesh().chunkVisible("Wire_D0", false);
    case 12:
    case 14:
    case 15:
    case 16:
    case 18:
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    float f = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator();
    if (Math.abs(this.kangle1 - f) > 0.01F)
    {
      this.kangle1 = f;
      hierMesh().chunkSetAngles("WaterL_D0", 0.0F, -45.0F * f, 0.0F);
    }
    f = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlRadiator();
    if (Math.abs(this.kangle2 - f) > 0.01F)
    {
      this.kangle2 = f;
      hierMesh().chunkSetAngles("WaterR_D0", 0.0F, -45.0F * f, 0.0F);
    }
  }

  static
  {
    Class localClass = CLASS.THIS();
    Property.set(localClass, "originCountry", PaintScheme.countryBritain);
  }
}