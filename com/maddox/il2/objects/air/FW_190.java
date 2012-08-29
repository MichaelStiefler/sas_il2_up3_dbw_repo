package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.weapons.PylonETC501FW190;
import com.maddox.rts.Property;

public abstract class FW_190 extends Scheme1
  implements TypeFighter, TypeBNZFighter
{
  public void doMurderPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
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

  public void moveCockpitDoor(float paramFloat)
  {
    resetYPRmodifier();
    Aircraft.xyz[0] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, -0.53F);
    hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
    if (Config.isUSE_RENDER())
    {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null))
        Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      setDoorSnd(paramFloat);
    }
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if (World.cur().camouflage == 1)
    {
      hierMesh().chunkVisible("GearL5_D0", false);
      hierMesh().chunkVisible("GearR5_D0", false);
    }
    Object[] arrayOfObject = this.pos.getBaseAttached();
    if (arrayOfObject == null)
      return;
    for (int i = 0; i < arrayOfObject.length; i++) {
      if (!(arrayOfObject[i] instanceof PylonETC501FW190))
        continue;
      hierMesh().chunkVisible("GearL5_D0", false);
      hierMesh().chunkVisible("GearR5_D0", false);
      return;
    }
  }

  public void update(float paramFloat)
  {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.bIsAboutToBailout)
      hierMesh().chunkVisible("Wire_D0", false);
    super.update(paramFloat);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 77.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 77.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 157.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 157.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 20.0F * paramFloat, 0.0F, 0.0F);
    float f = Math.max(-paramFloat * 1500.0F, -94.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, -f, 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public boolean cut(String paramString)
  {
    if (paramString.startsWith("Tail1"))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.hitTank(this, 2, 4);
    return super.cut(paramString);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1)
    {
    case 33:
      return super.cutFM(34, paramInt2, paramActor);
    case 36:
      return super.cutFM(37, paramInt2, paramActor);
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor"))
      {
        Aircraft.debugprintln(this, "*** Armor: Hit..");
        if (paramString.endsWith("p1"))
        {
          getEnergyPastArmor(World.Rnd().nextFloat(50.0F, 50.0F), paramShot);
          if (World.Rnd().nextFloat() < 0.15F)
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState | 0x2);
          Aircraft.debugprintln(this, "*** Armor Glass: Hit..");
          if (paramShot.power <= 0.0F)
          {
            Aircraft.debugprintln(this, "*** Armor Glass: Bullet Stopped..");
            if (World.Rnd().nextFloat() < 0.5F)
              doRicochetBack(paramShot);
          }
        }
        else if (paramString.endsWith("p3"))
        {
          if (paramPoint3d.z < -0.27D)
            getEnergyPastArmor(4.099999904632568D / (Math.abs(Aircraft.v1.z) + 9.999999747378752E-006D), paramShot);
          else
            getEnergyPastArmor(8.100000381469727D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-006D), paramShot);
        }
        else if (paramString.endsWith("p6")) {
          getEnergyPastArmor(8.0D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-006D), paramShot);
        }return;
      }
      if (paramString.startsWith("xxcontrols"))
      {
        i = paramString.charAt(10) - '0';
        switch (i)
        {
        case 7:
        default:
          break;
        case 1:
        case 4:
          if (getEnergyPastArmor(0.1F, paramShot) <= 0.0F)
            break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setControlsDamage(paramShot.initiator, 0);
          Aircraft.debugprintln(this, "*** Aileron Controls: Control Crank Destroyed.."); break;
        case 2:
        case 3:
          if ((getEnergyPastArmor(0.12F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.5F))
            break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setControlsDamage(paramShot.initiator, 0);
          Aircraft.debugprintln(this, "*** Aileron Controls: Disabled.."); break;
        case 5:
          if ((getEnergyPastArmor(0.12F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.5F))
            break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setControlsDamage(paramShot.initiator, 1);
          Aircraft.debugprintln(this, "*** Elevator Controls: Disabled / Strings Broken.."); break;
        case 6:
          if ((getEnergyPastArmor(0.12F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.5F))
            break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setControlsDamage(paramShot.initiator, 2);
          Aircraft.debugprintln(this, "*** Rudder Controls: Disabled / Strings Broken.."); break;
        case 8:
          if (getEnergyPastArmor(3.2F, paramShot) <= 0.0F)
            break;
          Aircraft.debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setControlsDamage(paramShot.initiator, 2);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setControlsDamage(paramShot.initiator, 1);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setControlsDamage(paramShot.initiator, 0);
        }

        return;
      }
      if (paramString.startsWith("xxspar"))
      {
        Aircraft.debugprintln(this, "*** Spar Construction: Hit..");
        if ((paramString.startsWith("xxspart")) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(2.4F / (float)Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** Tail1 Spars Broken in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparli")) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(18.0F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparri")) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(18.0F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparlm")) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(12.7F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparrm")) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(12.7F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparlo")) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(12.7F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparro")) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(12.7F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }
        return;
      }
      if (paramString.startsWith("xxlock"))
      {
        Aircraft.debugprintln(this, "*** Lock Construction: Hit..");
        if ((paramString.startsWith("xxlockr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
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
        return;
      }
      if (paramString.startsWith("xxeng"))
      {
        Aircraft.debugprintln(this, "*** Engine Module: Hit..");
        if (paramString.endsWith("pipe"))
        {
          if ((World.Rnd().nextFloat() < 0.1F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getType() == 0) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.Weapons[1] != null) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.Weapons[1].length != 2))
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setJamBullets(1, 0);
            Aircraft.debugprintln(this, "*** Engine Module: Nose Nozzle Pipe Bent..");
          }
          getEnergyPastArmor(0.3F, paramShot);
        }
        else if (paramString.endsWith("prop"))
        {
          if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.8F))
            if (World.Rnd().nextFloat() < 0.5F)
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setEngineSpecificDamage(paramShot.initiator, 0, 3);
              Aircraft.debugprintln(this, "*** Engine Module: Prop Governor Hit, Disabled..");
            }
            else {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setEngineSpecificDamage(paramShot.initiator, 0, 4);
              Aircraft.debugprintln(this, "*** Engine Module: Prop Governor Hit, Damaged..");
            }
        }
        else if (paramString.endsWith("gear"))
        {
          if (getEnergyPastArmor(4.6F, paramShot) > 0.0F)
            if (World.Rnd().nextFloat() < 0.5F)
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].setEngineStuck(paramShot.initiator);
              Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Reductor Gear..");
            }
            else {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setEngineSpecificDamage(paramShot.initiator, 0, 3);
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setEngineSpecificDamage(paramShot.initiator, 0, 4);
              Aircraft.debugprintln(this, "*** Engine Module: Reductor Gear Damaged, Prop Governor Failed..");
            }
        }
        else if (paramString.endsWith("supc"))
        {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F)
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setEngineSpecificDamage(paramShot.initiator, 0, 0);
            Aircraft.debugprintln(this, "*** Engine Module: Supercharger Disabled..");
          }
          getEnergyPastArmor(0.5F, paramShot);
        }
        else if (paramString.endsWith("feed"))
        {
          if ((getEnergyPastArmor(8.9F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getPowerOutput() > 0.7F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getType() == 0))
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.hitEngine(paramShot.initiator, 0, 100);
            Aircraft.debugprintln(this, "*** Engine Module: Pressurized Fuel Line Pierced, Fuel Flamed..");
          }
          getEnergyPastArmor(1.0F, paramShot);
        }
        else if (paramString.endsWith("fuel"))
        {
          if ((getEnergyPastArmor(1.1F, paramShot) > 0.0F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getType() == 0))
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].setEngineStops(paramShot.initiator);
            Aircraft.debugprintln(this, "*** Engine Module: Fuel Line Stalled, Engine Stalled..");
          }
          getEnergyPastArmor(1.0F, paramShot);
        }
        else if (paramString.endsWith("case"))
        {
          if (getEnergyPastArmor(4.2F, paramShot) > 0.0F)
          {
            if (World.Rnd().nextFloat() < paramShot.power / 175000.0F)
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setEngineStuck(paramShot.initiator, 0);
              Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Crank Ball Bearing..");
            }
            if ((World.Rnd().nextFloat() < paramShot.power / 50000.0F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getType() == 0))
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.hitEngine(paramShot.initiator, 0, 2);
              Aircraft.debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getReadyness() + "..");
            }
            if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getType() == 0)
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].setReadyness(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
            Aircraft.debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getReadyness() + "..");
          }
          getEnergyPastArmor(27.5F, paramShot);
        }
        else if (paramString.startsWith("xxeng1cyl"))
        {
          if (getEnergyPastArmor(2.4F, paramShot) > 0.0F) if (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getCylindersRatio() * (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getType() == 0 ? 1.75F : 0.5F))
            {
              if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getType() == 0)
                this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
              else
                this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 19200.0F)));
              Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getCylinders() + " Left..");
              if ((World.Rnd().nextFloat() < paramShot.power / 96000.0F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getType() == 0))
              {
                this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.hitEngine(paramShot.initiator, 0, 3);
                Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, Engine Fires..");
              }
              if ((World.Rnd().nextFloat() < paramShot.power / 96000.0F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getType() == 1))
              {
                this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.hitEngine(paramShot.initiator, 0, 1);
                Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, Engine Fires..");
              }
              if (World.Rnd().nextFloat() < 0.01F)
              {
                this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setEngineStuck(paramShot.initiator, 0);
                Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Piston Head..");
              }
              getEnergyPastArmor(43.599998F, paramShot);
            }
        }
        else if (paramString.startsWith("xxeng1mag"))
        {
          i = paramString.charAt(9) - '1';
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, i);
          Aircraft.debugprintln(this, "*** Engine Module: Magneto " + i + " Destroyed..");
        }
        else if (paramString.endsWith("sync"))
        {
          if ((getEnergyPastArmor(2.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F))
            Aircraft.debugprintln(this, "*** Engine Module: Gun Synchronized Hit, Nose Guns Lose Authority..");
        }
        else if ((paramString.endsWith("oil1")) && (getEnergyPastArmor(2.4F, paramShot) > 0.0F))
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.hitOil(paramShot.initiator, 0);
          Aircraft.debugprintln(this, "*** Engine Module: Oil Radiator Hit..");
        }
        return;
      }
      if (paramString.startsWith("xxtank"))
      {
        i = paramString.charAt(6) - '0';
        switch (i)
        {
        default:
          break;
        case 1:
          if ((getEnergyPastArmor(0.2F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.5F))
            break;
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.astateTankStates[2] == 0)
          {
            Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.hitTank(paramShot.initiator, 2, 1);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.doSetTankState(paramShot.initiator, 2, 1);
          }
          else if (((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.9F)) || (World.Rnd().nextFloat() < 0.03F))
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.hitTank(paramShot.initiator, 2, 2);
            Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
          }
          if (paramShot.power <= 200000.0F)
            break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.hitTank(paramShot.initiator, 2, 99);
          Aircraft.debugprintln(this, "*** Fuel Tank: Major Hit.."); break;
        case 2:
          if ((getEnergyPastArmor(1.2F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.25F))
            break;
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.astateTankStates[1] == 0)
          {
            Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.hitTank(paramShot.initiator, 1, 1);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.doSetTankState(paramShot.initiator, 1, 1);
          }
          else if (((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.8F)) || (World.Rnd().nextFloat() < 0.03F))
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.hitTank(paramShot.initiator, 1, 2);
            Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
          }
          if (paramShot.power <= 200000.0F)
            break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.hitTank(paramShot.initiator, 1, 99);
          Aircraft.debugprintln(this, "*** Fuel Tank: Major Hit.."); break;
        case 3:
          if ((getEnergyPastArmor(1.2F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.25F))
            break;
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.astateTankStates[0] == 0)
          {
            Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.hitTank(paramShot.initiator, 0, 1);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.doSetTankState(paramShot.initiator, 0, 1);
          }
          else if (((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.8F)) || (World.Rnd().nextFloat() < 0.03F))
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.hitTank(paramShot.initiator, 0, 2);
            Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
          }
          if (paramShot.power <= 200000.0F)
            break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.hitTank(paramShot.initiator, 0, 99);
          Aircraft.debugprintln(this, "*** Fuel Tank: Major Hit..");
        }

        return;
      }
      if (paramString.startsWith("xxmw50"))
      {
        if (World.Rnd().nextFloat() < 0.05F)
        {
          Aircraft.debugprintln(this, "*** MW50 Tank: Pierced..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setInternalDamage(paramShot.initiator, 2);
        }
        return;
      }
      if (paramString.startsWith("xxmgun"))
      {
        if (paramString.endsWith("01"))
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setJamBullets(1, 0);
        if (paramString.endsWith("02"))
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setJamBullets(1, 1);
        getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F), paramShot);
        return;
      }
      if (paramString.startsWith("xxcannon"))
      {
        Aircraft.debugprintln(this, "*** Nose Cannon: Disabled..");
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setJamBullets(0, 0);
        getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F), paramShot);
        return;
      }
      if (paramString.startsWith("xxradiat"))
      {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].setReadyness(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, 0.05F));
        Aircraft.debugprintln(this, "*** Engine Module: Radiator Hit, Readyness Reduced to " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getReadyness() + "..");
      }
      return;
    }
    if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit")))
    {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
      if (paramString.startsWith("xcockpit"))
      {
        if (paramPoint3d.z > 0.4D)
        {
          if (World.Rnd().nextFloat() < 0.2F)
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState | 0x1);
          if (World.Rnd().nextFloat() < 0.2F)
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState | 0x20);
        }
        else if (paramPoint3d.y > 0.0D)
        {
          if (World.Rnd().nextFloat() < 0.2F)
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState | 0x4);
        }
        else if (World.Rnd().nextFloat() < 0.2F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState | 0x10);
        }if ((paramPoint3d.x > 0.2D) && (World.Rnd().nextFloat() < 0.2F))
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState | 0x40);
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
      if ((paramString.startsWith("xstabl")) && (chunkDamageVisible("StabL") < 2))
        hitChunk("StabL", paramShot);
      if ((paramString.startsWith("xstabr")) && (chunkDamageVisible("StabR") < 1))
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

  static Class _mthclass$(String paramString)
  {
    try
    {
      return Class.forName(paramString);
    }
    catch (ClassNotFoundException localClassNotFoundException) {
    }
    throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
  }

  static
  {
    Class localClass = FW_190.class;
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
  }
}