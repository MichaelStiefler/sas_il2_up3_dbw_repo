package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public abstract class TBF extends Scheme1
  implements TypeStormovik, TypeBomber
{
  private float arrestor;
  private float flapps;

  public TBF()
  {
    this.arrestor = 0.0F;
    this.flapps = 0.0F;
  }

  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay01_D0", 0.0F, -115.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay02_D0", 0.0F, -70.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay03_D0", 0.0F, -115.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay04_D0", 0.0F, -70.0F * paramFloat, 0.0F);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("LLamp_D0", 0.0F, 90.0F * paramFloat, 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveCockpitDoor(float paramFloat)
  {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.625F);
    Aircraft.xyz[2] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.06845F);
    Aircraft.ypr[2] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 1.0F);
    hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
    resetYPRmodifier();
    Aircraft.xyz[2] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.13F);
    Aircraft.ypr[2] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, -8.0F);
    hierMesh().chunkSetLocate("Pilot1_D0", Aircraft.xyz, Aircraft.ypr);
    if (Config.isUSE_RENDER())
    {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null))
        Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      setDoorSnd(paramFloat);
    }
  }

  public void moveSteering(float paramFloat)
  {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
    {
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("HMask3_D0", false);
    }
    else {
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
      hierMesh().chunkVisible("HMask3_D0", hierMesh().isChunkVisible("Pilot3_D0"));
    }
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 1:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
      break;
    case 2:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
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
    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor"))
      {
        debuggunnery("Armor: Hit..");
        if (paramString.equals("xxarmorc1")) {
          getEnergyPastArmor(World.Rnd().nextFloat(10.0F, 15.0F) / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
        }
        else if (paramString.startsWith("xxarmorrt"))
        {
          if (paramString.endsWith("1"))
            getEnergyPastArmor(World.Rnd().nextFloat(10.0F, 60.0F) / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
          if (paramString.endsWith("2"))
            getEnergyPastArmor(19.100000381469727D / (Math.abs(Aircraft.v1.jdField_z_of_type_Double) + 9.999999747378752E-005D), paramShot);
          if (paramString.endsWith("3"))
            getEnergyPastArmor(12.699999809265137D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
          if ((paramString.endsWith("4")) || (paramString.endsWith("5")))
            getEnergyPastArmor(9.5D / (Math.abs(Aircraft.v1.jdField_y_of_type_Double) + 9.999999747378752E-005D), paramShot);
        }
        else if (paramString.startsWith("xxarmort"))
        {
          if (paramString.endsWith("1"))
            getEnergyPastArmor(9.5D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
          if (paramString.endsWith("2"))
            getEnergyPastArmor(19.100000381469727D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
          if (paramString.endsWith("3"))
            getEnergyPastArmor(World.Rnd().nextFloat(9.5F, 19.1F) / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxammo"))
      {
        if ((paramString.endsWith("rg")) && (getEnergyPastArmor(1.2F, paramShot) > 0.0F))
        {
          if (World.Rnd().nextFloat() < 0.12F)
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(11, 0);
          if (World.Rnd().nextFloat() < 0.12F)
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.explodeTank(paramShot.initiator, 3);
        }
        if ((paramString.endsWith("wl")) && (getEnergyPastArmor(1.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F))
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 0);
        if ((paramString.endsWith("wr")) && (getEnergyPastArmor(1.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F))
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 1);
        return;
      }
      if (paramString.startsWith("xxcontrols"))
      {
        i = paramString.charAt(10) - '0';
        switch (i)
        {
        default:
          break;
        case 1:
        case 2:
          if ((World.Rnd().nextFloat() >= 0.5F) || (getEnergyPastArmor(0.1F, paramShot) <= 0.0F))
            break;
          debuggunnery("Controls: Ailerones Controls: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0); break;
        case 3:
          if ((World.Rnd().nextFloat() >= 0.95F) || (getEnergyPastArmor(4.253F, paramShot) <= 0.0F))
            break;
          debuggunnery("Controls: Rudder Controls: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2); break;
        case 4:
        case 5:
          if ((World.Rnd().nextFloat() >= 0.25F) || (getEnergyPastArmor(0.1F, paramShot) <= 0.0F))
            break;
          debuggunnery("Controls: Elevator Controls: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
        }

        return;
      }
      if (paramString.startsWith("xxeng1"))
      {
        debuggunnery("Engine Module: Hit..");
        if (paramString.endsWith("case"))
        {
          if (getEnergyPastArmor(World.Rnd().nextFloat(0.2F, 0.55F), paramShot) > 0.0F)
          {
            if (World.Rnd().nextFloat() < paramShot.power / 280000.0F)
            {
              debuggunnery("Engine Module: Engine Crank Case Hit - Engine Stucks..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, 0);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 100000.0F)
            {
              debuggunnery("Engine Module: Engine Crank Case Hit - Engine Damaged..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 2);
            }
          }
          getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 24.0F), paramShot);
        }
        if (paramString.endsWith("cyls"))
        {
          if ((getEnergyPastArmor(0.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersRatio() * 0.66F))
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 32200.0F)));
            debuggunnery("Engine Module: Cylinders Hit, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 1000000.0F)
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 2);
              debuggunnery("Engine Module: Cylinders Hit - Engine Fires..");
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
      if (paramString.startsWith("xxgun"))
      {
        i = 0;
        if (paramString.endsWith("r"))
          i = 1;
        if (getEnergyPastArmor(0.5F, paramShot) > 0.0F)
        {
          debuggunnery("Armament: Machine Gun (" + i + ") Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, i);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
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
      if (paramString.startsWith("xxpnm"))
      {
        if (getEnergyPastArmor(World.Rnd().nextFloat(0.25F, 12.39F), paramShot) > 0.0F)
        {
          debuggunnery("Pneumo System: Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setInternalDamage(paramShot.initiator, 1);
        }
        return;
      }
      if (paramString.startsWith("xxradio"))
      {
        getEnergyPastArmor(World.Rnd().nextFloat(2.0F, 26.98F), paramShot);
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
        if (((paramString.endsWith("sl1")) || (paramString.endsWith("sl2")) || (paramString.endsWith("sl3")) || (paramString.endsWith("sl4")) || (paramString.endsWith("sl5"))) && (chunkDamageVisible("StabL") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** StabL Spars Damaged..");
          nextDMGLevels(1, 2, "StabL_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("sr1")) || (paramString.endsWith("sr2")) || (paramString.endsWith("sr3")) || (paramString.endsWith("sr4")) || (paramString.endsWith("sr5"))) && (chunkDamageVisible("StabR") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** StabR Spars Damaged..");
          nextDMGLevels(1, 2, "StabR_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxspark")) && (chunkDamageVisible("Keel1") > 1) && (World.Rnd().nextFloat() > Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 0.1000000014901161D) && (getEnergyPastArmor(3.400000095367432D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F))
        {
          debuggunnery("Spar Construction: Keel Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "Keel1_D2", paramShot.initiator);
        }
        return;
      }
      if (paramString.startsWith("xxtank"))
      {
        i = paramString.charAt(6) - '1';
        if (getEnergyPastArmor(0.1F, paramShot) > 0.0F)
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 1);
          if ((World.Rnd().nextFloat() < 0.02F) || ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.11F)))
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 2);
        }
        return;
      }

      return;
    }

    if (paramString.startsWith("xcf"))
    {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
    }
    else if (paramString.startsWith("xeng")) {
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
    else if (paramString.startsWith("xarone"))
    {
      if (paramString.startsWith("xaronel"))
        hitChunk("AroneL", paramShot);
      if (paramString.startsWith("xaroner"))
        hitChunk("AroneR", paramShot);
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

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    float f1 = -paramArrayOfFloat[0];
    float f2 = paramArrayOfFloat[1];
    switch (paramInt)
    {
    default:
      break;
    case 0:
      if (f1 < -120.0F)
      {
        f1 = -120.0F;
        bool = false;
      }
      if (f1 > 150.0F)
      {
        f1 = 150.0F;
        bool = false;
      }
      if (f2 < -2.0F)
      {
        f2 = -2.0F;
        bool = false;
      }
      if (f2 <= 89.0F)
        break;
      f2 = 89.0F;
      bool = false; break;
    case 1:
      if (f1 < -45.0F)
      {
        f1 = -45.0F;
        bool = false;
      }
      if (f1 > 45.0F)
      {
        f1 = 45.0F;
        bool = false;
      }
      if (f2 < -40.0F)
      {
        f2 = -40.0F;
        bool = false;
      }
      if (f2 <= 20.0F)
        break;
      f2 = 20.0F;
      bool = false;
    }

    paramArrayOfFloat[0] = (-f1);
    paramArrayOfFloat[1] = f2;
    return bool;
  }

  protected void moveWingFold(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("WingLMid_D0", 0.0F, -80.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("WingRMid_D0", 0.0F, -80.0F * paramFloat, 0.0F);
  }

  public void moveWingFold(float paramFloat)
  {
    if (paramFloat < 0.001F)
    {
      setGunPodsOn(true);
      hideWingWeapons(false);
    }
    else {
      setGunPodsOn(false);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[0] = false;
      hideWingWeapons(true);
    }
    moveWingFold(hierMesh(), paramFloat);
  }

  public void moveArrestorHook(float paramFloat)
  {
    resetYPRmodifier();
    Aircraft.xyz[0] = (-1.45F * paramFloat);
    Aircraft.ypr[1] = (-this.arrestor);
    hierMesh().chunkSetLocate("Hook1_D0", Aircraft.xyz, Aircraft.ypr);
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    float f1 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getArrestor();
    float f2 = 45.0F * f1 * f1 * f1 * f1 * f1;
    if (f1 > 0.01F)
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.arrestorVAngle != 0.0F) {
        this.arrestor = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.arrestorVAngle, -f2, f2, -f2, f2);
        moveArrestorHook(f1);
        if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.arrestorVAngle < -35.0F); } else {
        f3 = 40.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.arrestorVSink;
        if ((f3 > 0.0F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH() > 60.0F))
          Eff3DActor.New(this, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.arrestorHook, null, 1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
        this.arrestor += f3;
        if (this.arrestor > f2)
          this.arrestor = f2;
        if (this.arrestor < -f2)
          this.arrestor = (-f2);
        moveArrestorHook(f1);
      }
    float f3 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator();
    if (Math.abs(this.flapps - f3) > 0.01F)
    {
      this.flapps = f3;
      for (int i = 1; i < 5; i++)
        hierMesh().chunkSetAngles("Cowflap" + i + "_D0", 0.0F, -20.0F * f3, 0.0F);
    }
  }

  public boolean typeBomberToggleAutomation()
  {
    return false;
  }

  public void typeBomberAdjDistanceReset()
  {
  }

  public void typeBomberAdjDistancePlus()
  {
  }

  public void typeBomberAdjDistanceMinus()
  {
  }

  public void typeBomberAdjSideslipReset()
  {
  }

  public void typeBomberAdjSideslipPlus()
  {
  }

  public void typeBomberAdjSideslipMinus()
  {
  }

  public void typeBomberAdjAltitudeReset()
  {
  }

  public void typeBomberAdjAltitudePlus()
  {
  }

  public void typeBomberAdjAltitudeMinus()
  {
  }

  public void typeBomberAdjSpeedReset()
  {
  }

  public void typeBomberAdjSpeedPlus()
  {
  }

  public void typeBomberAdjSpeedMinus()
  {
  }

  public void typeBomberUpdate(float paramFloat)
  {
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException
  {
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
    Class localClass = TBF.class;
    Property.set(localClass, "originCountry", PaintScheme.countryUSA);
  }
}