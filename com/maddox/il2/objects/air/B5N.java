package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public abstract class B5N extends Scheme1
{
  private float arrestor;
  private float flapps;

  public B5N()
  {
    this.arrestor = 0.0F;
    this.flapps = 0.0F;
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
    {
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("HMask3_D0", false);
    }
    else {
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
      hierMesh().chunkVisible("HMask2_D0", hierMesh().isChunkVisible("Pilot2_D0"));
      hierMesh().chunkVisible("HMask3_D0", hierMesh().isChunkVisible("Pilot3_D0"));
    }
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 2:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
    }
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
      break;
    case 2:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("HMask3_D0", false);
      hierMesh().chunkVisible("Pilot3_D1", true);
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    int j;
    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxcontrols"))
      {
        if (((paramString.endsWith("1")) || (paramString.endsWith("2"))) && (World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.35F, paramShot) > 0.0F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0);
          Aircraft.debugprintln(this, "*** Aileron Controls Out..");
        }
        if ((paramString.endsWith("3")) && (getEnergyPastArmor(0.2F, paramShot) > 0.0F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 6);
          Aircraft.debugprintln(this, "*** Throttle Quadrant: Hit, Engine Controls Disabled..");
        }
        if ((paramString.endsWith("4")) && (World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
          Aircraft.debugprintln(this, "*** Evelator Controls Out..");
        }
        if ((paramString.endsWith("5")) && (World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(2.2F, paramShot) > 0.0F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2);
          Aircraft.debugprintln(this, "*** Rudder Controls Out..");
        }
        return;
      }
      if (paramString.startsWith("xxeng1"))
      {
        if (paramString.endsWith("case"))
        {
          if (getEnergyPastArmor(0.2F, paramShot) > 0.0F)
          {
            if (World.Rnd().nextFloat() < paramShot.power / 140000.0F)
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, 0);
              Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Stucks..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 85000.0F)
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 2);
              Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Damaged..");
            }
          }
          else if (World.Rnd().nextFloat() < 0.01F)
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setCyliderKnockOut(paramShot.initiator, 1);
          }
          else {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setReadyness(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness() - 0.002F);
            Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Readyness Reduced to " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness() + "..");
          }
          getEnergyPastArmor(12.0F, paramShot);
        }
        if (paramString.endsWith("cyls"))
        {
          if ((getEnergyPastArmor(6.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersRatio() * 0.75F))
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 19000.0F)));
            Aircraft.debugprintln(this, "*** Engine Cylinders Hit, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 48000.0F)
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 2);
              Aircraft.debugprintln(this, "*** Engine Cylinders Hit - Engine Fires..");
            }
          }
          getEnergyPastArmor(25.0F, paramShot);
        }
        if (paramString.startsWith("xxeng1mag"))
        {
          i = paramString.charAt(9) - '1';
          debuggunnery("Engine Module: Magneto " + i + " Destroyed..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setMagnetoKnockOut(paramShot.initiator, i);
        }
        if (paramString.endsWith("oil1"))
        {
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.25F, paramShot) > 0.0F))
            debuggunnery("Engine Module: Oil Radiator Hit..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
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
        if ((paramString.startsWith("xxlockal")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          debuggunnery("Lock Construction: AroneL Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), paramShot.initiator);
        }
        if ((paramString.startsWith("xxlockar")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          debuggunnery("Lock Construction: AroneR Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), paramShot.initiator);
        }
        return;
      }
      if (paramString.startsWith("xxoil"))
      {
        if ((getEnergyPastArmor(0.25F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
          getEnergyPastArmor(0.22F, paramShot);
          debuggunnery("Engine Module: Oil Tank Pierced..");
        }
        return;
      }
      if (paramString.startsWith("xxspar"))
      {
        if ((paramString.endsWith("li1")) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }
        if ((paramString.endsWith("ri1")) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }
        if ((paramString.endsWith("lm1")) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }
        if ((paramString.endsWith("rm1")) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }
        if ((paramString.endsWith("lo1")) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }
        if ((paramString.endsWith("ro1")) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("sl1")) || (paramString.endsWith("sl2"))) && (chunkDamageVisible("StabL") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** StabL Spars Damaged..");
          nextDMGLevels(1, 2, "StabL_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("sr1")) || (paramString.endsWith("sr2"))) && (chunkDamageVisible("StabR") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** StabR Spars Damaged..");
          nextDMGLevels(1, 2, "StabR_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxspark")) && (chunkDamageVisible("Keel1") > 1) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** Keel1 Spars Damaged..");
          nextDMGLevels(1, 2, "Keel1_D2", paramShot.initiator);
        }
        return;
      }
      if (paramString.startsWith("xxtank"))
      {
        i = paramString.charAt(6) - '0';
        j = 0;
        switch (i)
        {
        case 1:
          j = World.Rnd().nextInt(0, 1);
          break;
        case 2:
          j = World.Rnd().nextInt(2, 3);
          break;
        case 3:
          j = World.Rnd().nextInt(1, 2);
        }

        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, j, 1);
          if ((World.Rnd().nextFloat() < 0.05F) || ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.6F)))
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, j, 2);
        }
        return;
      }

      return;
    }

    if (paramString.startsWith("xcf")) {
      hitChunk("CF", paramShot);
    }
    else if (!paramString.startsWith("xblister"))
      if (paramString.startsWith("xeng")) {
        hitChunk("Engine1", paramShot);
      }
      else if (paramString.startsWith("xtail")) {
        hitChunk("Tail1", paramShot);
      }
      else if (paramString.startsWith("xkeel"))
      {
        if (chunkDamageVisible("Keel1") < 2)
          hitChunk("Keel1", paramShot);
      }
      else if (paramString.startsWith("xrudder"))
      {
        if (chunkDamageVisible("Rudder1") < 2)
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
        if ((paramString.startsWith("xvatorl")) && (chunkDamageVisible("VatorL") < 2))
          hitChunk("VatorL", paramShot);
        if ((paramString.startsWith("xvatorr")) && (chunkDamageVisible("VatorR") < 2))
          hitChunk("VatorR", paramShot);
      }
      else if (paramString.startsWith("xwing"))
      {
        if ((paramString.startsWith("xWingLIn")) && (chunkDamageVisible("WingLIn") < 3))
          hitChunk("WingLIn", paramShot);
        if ((paramString.startsWith("xWingRIn")) && (chunkDamageVisible("WingRIn") < 3))
          hitChunk("WingRIn", paramShot);
        if (paramString.startsWith("xWingLMid"))
        {
          if (chunkDamageVisible("WingLMid") < 3)
            hitChunk("WingLMid", paramShot);
          if (World.Rnd().nextFloat() < paramShot.mass + 0.02F)
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
        }
        if ((paramString.startsWith("xWingRMid")) && (chunkDamageVisible("WingRMid") < 3))
          hitChunk("WingRMid", paramShot);
        if ((paramString.startsWith("xWingLOut")) && (chunkDamageVisible("WingLOut") < 3))
          hitChunk("WingLOut", paramShot);
        if ((paramString.startsWith("xWingROut")) && (chunkDamageVisible("WingROut") < 3))
          hitChunk("WingROut", paramShot);
      }
      else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead")))
      {
        i = 0;

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

  protected void moveWingFold(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("WingLMid_D0", 0.0F, 135.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("WingRMid_D0", 0.0F, -135.0F * paramFloat, 0.0F);
  }

  public void moveWingFold(float paramFloat)
  {
    moveWingFold(hierMesh(), paramFloat);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -88.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 88.0F * paramFloat, 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat)
  {
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
  }

  public void moveArrestorHook(float paramFloat)
  {
    hierMesh().chunkSetAngles("Hook1_D0", 0.0F, 53.0F * paramFloat, 0.0F);
  }

  public void moveWheelSink()
  {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.21F, 0.0F, -0.21F);
    hierMesh().chunkSetLocate("GearL25_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.21F, 0.0F, -0.21F);
    hierMesh().chunkSetLocate("GearR25_D0", Aircraft.xyz, Aircraft.ypr);
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.arrestorVAngle != 0.0F)
    {
      f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.arrestorVAngle, -44.0F, 9.0F, 1.0F, 0.0F);
      this.arrestor = (0.8F * this.arrestor + 0.2F * f);
    }
    else {
      f = -28.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.arrestorVSink / 53.0F;
      if ((f < 0.0F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH() > 60.0F))
        Eff3DActor.New(this, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.arrestorHook, null, 1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
      if (f > 0.15F)
        f = 0.15F;
      this.arrestor = (0.3F * this.arrestor + 0.7F * (this.arrestor + f));
      if (this.arrestor < 0.0F)
        this.arrestor = 0.0F;
    }
    if (this.arrestor > this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getArrestor())
      this.arrestor = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getArrestor();
    moveArrestorHook(this.arrestor);
    float f = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator();
    if (Math.abs(this.flapps - f) > 0.02F)
    {
      this.flapps = f;
      for (int i = 1; i < 11; i++)
        hierMesh().chunkSetAngles("Cowflap" + i + "_D0", 0.0F, 22.0F * f, 0.0F);
    }
  }

  static Class _mthclass$(String paramString)
  {
    Class localClass;
    try
    {
      localClass = Class.forName(paramString);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
    return localClass;
  }

  static
  {
    Class localClass = B5N.class;
    Property.set(localClass, "originCountry", PaintScheme.countryJapan);
  }
}