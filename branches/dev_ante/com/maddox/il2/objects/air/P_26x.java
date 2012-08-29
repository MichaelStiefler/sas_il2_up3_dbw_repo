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
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public abstract class P_26x extends Scheme1
  implements TypeFighter, TypeTNBFighter
{
  public static boolean bChangedPit = false;
  private float flapps;

  public P_26x()
  {
    this.flapps = 0.0F;
  }

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      bChangedPit = true;
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      bChangedPit = true;
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    Object[] arrayOfObject = this.pos.getBaseAttached();
    if (arrayOfObject != null)
    {
      int i = 0;

      while (i < arrayOfObject.length)
      {
        if ((arrayOfObject[i] instanceof BombGun))
        {
          for (int j = 1; j < 5; j++) {
            hierMesh().chunkVisible("Pilon0" + j + "_D0", true);
          }
          break;
        }
        i++;
      }
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
    }
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    float f = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator();
    if (Math.abs(this.flapps - f) > 0.01F)
    {
      this.flapps = f;
      for (int i = 1; i < 9; i++)
        hierMesh().chunkSetAngles("Cowflap" + i + "_D0", 0.0F, -30.0F * f, 0.0F);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
  }

  public void moveWheelSink()
  {
    resetYPRmodifier();
    float f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.15F, 0.0F, 0.15F);
    Aircraft.xyz[1] = f;
    hierMesh().chunkSetLocate("GearL2_D0", Aircraft.xyz, Aircraft.ypr);
    f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.15F, 0.0F, 0.15F);
    Aircraft.xyz[1] = f;
    hierMesh().chunkSetLocate("GearR2_D0", Aircraft.xyz, Aircraft.ypr);
  }

  protected void moveFlap(float paramFloat)
  {
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -45.0F * paramFloat, 0.0F);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxcontrols"))
      {
        debuggunnery("Controls: Hit..");
        i = paramString.charAt(10) - '0';
        switch (i)
        {
        case 1:
        case 2:
        case 3:
        case 4:
          if (getEnergyPastArmor(4.2F, paramShot) <= 0.0F)
            break;
          debuggunnery("Controls: Ailerones Controls: Out..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0); break;
        case 5:
          if ((getEnergyPastArmor(4.2F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.12F))
            break;
          debuggunnery("Controls: Elevator Controls: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1); break;
        case 6:
          if ((getEnergyPastArmor(0.22F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.12F))
            break;
          debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2); break;
        case 7:
          if (getEnergyPastArmor(3.2F, paramShot) <= 0.0F)
            break;
          Aircraft.debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0);
        }

      }
      else if (paramString.startsWith("xxeng"))
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
          if ((getEnergyPastArmor(5.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersRatio() * 0.75F))
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
        if (paramString.endsWith("gear"))
          getEnergyPastArmor(44.200001F, paramShot);
        if (paramString.startsWith("xxeng1oil"))
        {
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.25F, paramShot) > 0.0F))
            debuggunnery("Engine Module: Oil Radiator Hit..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
        }
      }
      else if (paramString.startsWith("xxlock"))
      {
        debuggunnery("Lock Construction: Hit..");
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
      }
      else if (paramString.startsWith("xxmgun01"))
      {
        if (getEnergyPastArmor(0.75F, paramShot) > 0.0F)
        {
          debuggunnery("Armament: MGUN (0) Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 0);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
      }
      else if (paramString.startsWith("xxmgun02"))
      {
        if (getEnergyPastArmor(0.75F, paramShot) > 0.0F)
        {
          debuggunnery("Armament: MGUN (1) Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 1);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
      }
      else if (paramString.startsWith("xxoil"))
      {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x40);
        if ((getEnergyPastArmor(0.25F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.125F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
          getEnergyPastArmor(0.22F, paramShot);
          debuggunnery("Engine Module: Oil Tank Pierced..");
        }
      }
      else if (paramString.startsWith("xxspar"))
      {
        Aircraft.debugprintln(this, "*** Spar Construction: Hit..");
        if ((paramString.endsWith("li1")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3" + chunkDamageVisible("WingLIn"), paramShot.initiator);
        }
        if ((paramString.endsWith("ri1")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3" + chunkDamageVisible("WingRIn"), paramShot.initiator);
        }
        if ((paramString.endsWith("lm1")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3" + chunkDamageVisible("WingLMid"), paramShot.initiator);
        }
        if ((paramString.endsWith("rm1")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3" + chunkDamageVisible("WingRMid"), paramShot.initiator);
        }
        if ((paramString.endsWith("lo1")) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 2.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }
        if ((paramString.endsWith("ro1")) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.jdField_x_of_type_Double)) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 2.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxspart")) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.86F / (float)Math.sqrt(Aircraft.v1.jdField_y_of_type_Double * Aircraft.v1.jdField_y_of_type_Double + Aircraft.v1.jdField_z_of_type_Double * Aircraft.v1.jdField_z_of_type_Double), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.15F))
        {
          debuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }
      }
      else if (paramString.startsWith("xxtank"))
      {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(1.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.4F))
        {
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[i] == 0)
          {
            debuggunnery("Fuel Tank (" + i + "): Pierced..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 1);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetTankState(paramShot.initiator, i, 1);
          }
          if ((World.Rnd().nextFloat() < 0.003F) || ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.4F)))
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 2);
            debuggunnery("Fuel Tank (" + i + "): Hit..");
          }
        }
      }
    }
    else if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcock")))
    {
      hitChunk("CF", paramShot);
      if (paramPoint3d.jdField_z_of_type_Double > 0.568D)
      {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x1);
        if (paramPoint3d.jdField_x_of_type_Double > -0.093D)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x2);
      }
      else {
        if (paramPoint3d.jdField_y_of_type_Double > 0.0D)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x10);
        else
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x4);
        if (World.Rnd().nextFloat() < 0.1F)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x8);
        if (World.Rnd().nextFloat() < 0.1F)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x20);
        if (World.Rnd().nextFloat() < 0.1F)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x40);
      }
    }
    else if (paramString.startsWith("xeng"))
    {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xtail"))
    {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel"))
    {
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xrudder"))
    {
      if (chunkDamageVisible("Rudder1") < 1)
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
      if ((paramString.startsWith("xvatorl")) && (chunkDamageVisible("VatorL") < 1))
        hitChunk("VatorL", paramShot);
      if ((paramString.startsWith("xvatorr")) && (chunkDamageVisible("VatorR") < 1))
        hitChunk("VatorR", paramShot);
    }
    else if (paramString.startsWith("xwing"))
    {
      if ((paramString.startsWith("xwinglin")) && (chunkDamageVisible("WingLIn") < 3))
        hitChunk("WingLIn", paramShot);
      if ((paramString.startsWith("xwingrin")) && (chunkDamageVisible("WingRIn") < 3))
        hitChunk("WingRIn", paramShot);
      if ((paramString.startsWith("xwinglmid")) && (chunkDamageVisible("WingLMid") < 3))
        hitChunk("WingLMid", paramShot);
      if ((paramString.startsWith("xwingrmid")) && (chunkDamageVisible("WingRMid") < 3))
        hitChunk("WingRMid", paramShot);
      if ((paramString.startsWith("xwinglout")) && (chunkDamageVisible("WingLOut") < 3))
        hitChunk("WingLOut", paramShot);
      if ((paramString.startsWith("xwingrout")) && (chunkDamageVisible("WingROut") < 3))
        hitChunk("WingROut", paramShot);
    }
    else if (paramString.startsWith("xarone"))
    {
      if ((paramString.startsWith("xaronel")) && (chunkDamageVisible("AroneL") < 1))
        hitChunk("AroneL", paramShot);
      if ((paramString.startsWith("xaroner")) && (chunkDamageVisible("AroneR") < 1))
        hitChunk("AroneR", paramShot);
    }
    else if (paramString.startsWith("xgear"))
    {
      if (World.Rnd().nextFloat() < 0.05F)
      {
        debuggunnery("Hydro System: Disabled..");
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setInternalDamage(paramShot.initiator, 0);
      }
      if ((World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 3.435F), paramShot) > 0.0F))
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

  protected boolean haveFlaps()
  {
    return false;
  }

  static
  {
    Class localClass = CLASS.THIS();
    Property.set(localClass, "originCountry", PaintScheme.countryUSA);
  }
}