package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public abstract class CW21xyz extends Scheme1
  implements TypeFighter
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -85.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -85.0F * paramFloat, 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat)
  {
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, paramFloat, 0.0F);
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt)
    {
    default:
      break;
    case 0:
      if (this.FM.AS.bIsAboutToBailout)
        break;
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("pilotarm2_d0", false);
      hierMesh().chunkVisible("pilotarm1_d0", false);
    }
  }

  public void doRemoveBodyFromPlane(int paramInt)
  {
    super.doRemoveBodyFromPlane(paramInt);
    hierMesh().chunkVisible("pilotarm2_d0", false);
    hierMesh().chunkVisible("pilotarm1_d0", false);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -40.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
  }

  public void moveCockpitDoor(float paramFloat)
  {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, -0.49F);
    Aircraft.ypr[2] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 9.0F);
    hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);

    if (Config.isUSE_RENDER())
    {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null))
        Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      setDoorSnd(paramFloat);
    }
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 30.0F * paramFloat, 0.0F, 0.0F);
  }

  protected void moveElevator(float paramFloat)
  {
    hierMesh().chunkSetAngles("VatorL_D0", 0.0F, 0.0F, -30.0F * paramFloat);
    hierMesh().chunkSetAngles("VatorR_D0", 0.0F, 0.0F, -30.0F * paramFloat);
  }

  protected void moveFan(float paramFloat)
  {
    if (Config.isUSE_RENDER())
    {
      super.moveFan(paramFloat);
      float f1 = this.FM.CT.getAileron();
      float f2 = this.FM.CT.getElevator();

      hierMesh().chunkSetAngles("Stick_D0", 0.0F, cvt(f1, -1.0F, 1.0F, -10.0F, 10.5F), cvt(f2, -1.0F, 1.0F, -11.5F, 9.5F));
      hierMesh().chunkSetAngles("pilotarm2_d0", cvt(f1, -1.0F, 1.0F, 14.0F, -16.0F), 0.0F, cvt(f1, -1.0F, 1.0F, 6.0F, -8.0F) - cvt(f2, -1.0F, 1.0F, -37.0F, 35.0F));
      hierMesh().chunkSetAngles("pilotarm1_d0", 0.0F, 0.0F, cvt(f1, -1.0F, 1.0F, -16.0F, 14.0F) + cvt(f2, -1.0F, 0.0F, -61.0F, 0.0F) + cvt(f2, 0.0F, 1.0F, 0.0F, 43.0F));
    }
  }

  public void missionStarting()
  {
    super.missionStarting();
    hierMesh().chunkVisible("pilotarm2_d0", true);
    hierMesh().chunkVisible("pilotarm1_d0", true);
  }

  public void prepareCamouflage() {
    super.prepareCamouflage();
    hierMesh().chunkVisible("pilotarm2_d0", true);
    hierMesh().chunkVisible("pilotarm1_d0", true);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1)
    {
    case 19:
      this.FM.Gears.hitCentreGear();
    case 11:
    case 36:
    case 37:
    case 38:
      hierMesh().chunkVisible("Wire_D0", false);
      break;
    case 3:
      if (World.Rnd().nextInt(0, 99) < 1)
      {
        this.FM.AS.hitEngine(this, 0, 4);
        hitProp(0, paramInt2, paramActor);
        this.FM.EI.engines[0].setEngineStuck(paramActor);
        return cut("engine1");
      }

      this.FM.AS.setEngineDies(this, 0);
      return false;
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    if (paramString.startsWith("xxarmor"))
    {
      Aircraft.debugprintln(this, "*** Armor: Hit..");

      if (paramString.endsWith("p1")) {
        getEnergyPastArmor(4.0F / (1.0E-005F + (float)Math.abs(Aircraft.v1.x)), paramShot);
      }
      else if (paramString.endsWith("p2")) {
        getEnergyPastArmor(2.0F / (1.0E-005F + (float)Math.abs(Aircraft.v1.x)), paramShot);
      }
      return;
    }
    int i;
    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxammo"))
      {
        if ((paramString.startsWith("xxammol1")) && (World.Rnd().nextFloat() < 0.25F))
          this.FM.AS.setJamBullets(0, 0);
        if ((paramString.startsWith("xxammol2")) && (World.Rnd().nextFloat() < 0.25F))
          this.FM.AS.setJamBullets(0, 1);
        if ((paramString.startsWith("xxammor1")) && (World.Rnd().nextFloat() < 0.25F))
          this.FM.AS.setJamBullets(0, 2);
        if ((paramString.startsWith("xxammor2")) && (World.Rnd().nextFloat() < 0.25F))
          this.FM.AS.setJamBullets(0, 3);
        getEnergyPastArmor(16.0F, paramShot);
        return;
      }
      if (paramString.startsWith("xxcontrols"))
      {
        debuggunnery("Controls: Hit..");
        i = paramString.charAt(10) - '0';
        switch (i)
        {
        default:
          break;
        case 1:
        case 2:
          if ((getEnergyPastArmor(0.99F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.175F))
            break;
          debuggunnery("Controls: Ailerones Controls: Out..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 3:
          if ((getEnergyPastArmor(0.99F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.675F))
            break;
          if (World.Rnd().nextFloat() < 0.2F)
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 0);
          if (World.Rnd().nextFloat() < 0.2F)
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          if (World.Rnd().nextFloat() < 0.2F)
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
          if (World.Rnd().nextFloat() >= 0.2F) break;
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 7); break;
        case 4:
          if ((getEnergyPastArmor(0.22F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.02F))
            break;
          debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2); break;
        case 5:
          if (getEnergyPastArmor(3.2F, paramShot) <= 0.0F)
            break;
          Aircraft.debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 6:
          if (getEnergyPastArmor(0.1F, paramShot) <= 0.0F)
            break;
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 7);
          Aircraft.debugprintln(this, "*** Throttle Quadrant: Hit, Engine Controls Disabled.."); break;
        case 7:
          if ((getEnergyPastArmor(4.2F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.11F))
            break;
          debuggunnery("Controls: Elevator Controls: Disabled..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
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
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
              Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Stucks..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 85000.0F)
            {
              this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
              Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Damaged..");
            }
          }
          else if (World.Rnd().nextFloat() < 0.01F)
          {
            this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, 1);
          }
          else {
            this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - 0.002F);
            Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
          }
          getEnergyPastArmor(12.0F, paramShot);
        }

        if (paramString.endsWith("cyls"))
        {
          if ((getEnergyPastArmor(5.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 0.75F))
          {
            this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 19000.0F)));
            Aircraft.debugprintln(this, "*** Engine Cylinders Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 48000.0F)
            {
              this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
              Aircraft.debugprintln(this, "*** Engine Cylinders Hit - Engine Fires..");
            }
          }
          getEnergyPastArmor(25.0F, paramShot);
        }

        if (paramString.endsWith("mag1"))
        {
          this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, 0);
          Aircraft.debugprintln(this, "*** Engine Module: Magneto #0 Destroyed..");
          getEnergyPastArmor(25.0F, paramShot);
        }

        if (paramString.endsWith("mag2"))
        {
          this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, 1);
          Aircraft.debugprintln(this, "*** Engine Module: Magneto #1 Destroyed..");
          getEnergyPastArmor(25.0F, paramShot);
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

      if (paramString.startsWith("xxmgun1"))
      {
        if (getEnergyPastArmor(0.75F, paramShot) > 0.0F)
        {
          debuggunnery("Armament: Machine Gun 01 Disabled..");
          this.FM.AS.setJamBullets(0, 0);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxmgun2"))
      {
        if (getEnergyPastArmor(0.75F, paramShot) > 0.0F)
        {
          debuggunnery("Armament: Machine Gun 02 Disabled..");
          this.FM.AS.setJamBullets(0, 1);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
        return;
      }

      if (paramString.startsWith("xxmgun3"))
      {
        if (getEnergyPastArmor(0.75F, paramShot) > 0.0F)
        {
          debuggunnery("Armament: Machine Gun 01 Disabled..");
          this.FM.AS.setJamBullets(0, 2);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxmgun4"))
      {
        if (getEnergyPastArmor(0.75F, paramShot) > 0.0F)
        {
          debuggunnery("Armament: Machine Gun 02 Disabled..");
          this.FM.AS.setJamBullets(0, 3);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
        return;
      }

      if (paramString.startsWith("xxoil"))
      {
        if ((getEnergyPastArmor(0.25F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.125F))
        {
          this.FM.AS.hitOil(paramShot.initiator, 0);
          getEnergyPastArmor(0.22F, paramShot);
          debuggunnery("Engine Module: Oil Tank Pierced..");
        }
        return;
      }

      if (paramString.startsWith("xxspar"))
      {
        Aircraft.debugprintln(this, "*** Spar Construction: Hit..");
        if ((paramString.endsWith("li1")) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(2.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }
        if ((paramString.endsWith("ri1")) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(2.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }
        if ((paramString.endsWith("lm1")) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(2.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }
        if ((paramString.endsWith("rm1")) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(2.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }
        if ((paramString.endsWith("lo1")) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }
        if ((paramString.endsWith("ro1")) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxspart1")) && (World.Rnd().nextFloat(0.0F, 0.115F) < paramShot.mass) && (getEnergyPastArmor(6.8F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** Tail Spars Damaged..");
          nextDMGLevels(1, 2, "Tail1_D" + chunkDamageVisible("Tail1"), paramShot.initiator);
        }
        if ((paramString.startsWith("xxspart2")) && (World.Rnd().nextFloat(0.0F, 0.115F) < paramShot.mass) && (getEnergyPastArmor(6.8F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** Tail Spars Damaged..");
          nextDMGLevels(1, 2, "Tail1_D" + chunkDamageVisible("Tail1"), paramShot.initiator);
        }
        if ((paramString.startsWith("xxspart3")) && (World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(6.8F * World.Rnd().nextFloat(1.0F, 1.5F) / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** Tail Spars Damaged..");
          nextDMGLevels(1, 2, "Tail1_D" + chunkDamageVisible("Tail1"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxspart4")) && (World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(6.8F * World.Rnd().nextFloat(1.0F, 1.5F) / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** Tail Spars Damaged..");
          nextDMGLevels(1, 2, "Tail1_D" + chunkDamageVisible("Tail1"), paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxtank"))
      {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.45F))
        {
          if (this.FM.AS.astateTankStates[i] == 0)
          {
            debuggunnery("Fuel Tank (" + i + "): Pierced..");
            this.FM.AS.hitTank(paramShot.initiator, i, 1);
            this.FM.AS.doSetTankState(paramShot.initiator, i, 1);
          }
          if ((World.Rnd().nextFloat() < 0.08F) || ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.5F)))
          {
            this.FM.AS.hitTank(paramShot.initiator, i, 4);
            debuggunnery("Fuel Tank (" + i + "): Hit..");
          }
        }
        return;
      }

      return;
    }

    if (paramString.startsWith("xcf"))
    {
      hitChunk("CF", paramShot);
      return;
    }
    if (paramString.startsWith("xcockpit"))
    {
      if (paramPoint3d.z < 0.5D)
      {
        if (paramPoint3d.y > 0.1D)
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
        else if (paramPoint3d.y < -0.1D)
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
        if (paramPoint3d.x > -0.5D)
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
      }
      else
      {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
      }

      return;
    }
    if (paramString.startsWith("xeng"))
    {
      if (chunkDamageVisible("Engine1") < 3)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xtail"))
    {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel"))
    {
      if (chunkDamageVisible("Keel1") < 3)
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
        this.FM.AS.setInternalDamage(paramShot.initiator, 0);
      }
      if ((World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 3.435F), paramShot) > 0.0F))
      {
        debuggunnery("Undercarriage: Stuck..");
        this.FM.AS.setInternalDamage(paramShot.initiator, 3);
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

  static
  {
    Class localClass = CW21xyz.class;
    Property.set(localClass, "originCountry", PaintScheme.countryUSA);
  }
}