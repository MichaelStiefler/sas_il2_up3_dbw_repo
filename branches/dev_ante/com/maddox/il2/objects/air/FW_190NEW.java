package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.weapons.PylonETC501FW190;
import com.maddox.rts.Property;

public abstract class FW_190NEW extends Scheme1
  implements TypeFighter, TypeBNZFighter
{
  static
  {
    Class class1 = FW_190NEW.class;
    Property.set(class1, "originCountry", PaintScheme.countryGermany);
  }

  public void doMurderPilot(int i)
  {
    switch (i)
    {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
    }
  }

  public void rareAction(float f, boolean flag)
  {
    super.rareAction(f, flag);
    if (this.FM.getAltitude() < 3000.0F)
    {
      hierMesh().chunkVisible("HMask1_D0", false);
    }
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if (World.cur().camouflage == 1)
    {
      hierMesh().chunkVisible("GearL5_D0", false);
      hierMesh().chunkVisible("GearR5_D0", false);
    }
    Object[] aobj = this.pos.getBaseAttached();
    if (aobj == null)
    {
      return;
    }
    for (int i = 0; i < aobj.length; i++)
    {
      if (!(aobj[i] instanceof PylonETC501FW190))
        continue;
      hierMesh().chunkVisible("GearL5_D0", false);
      hierMesh().chunkVisible("GearR5_D0", false);
      return;
    }
  }

  public void update(float f)
  {
    if (this.FM.AS.bIsAboutToBailout)
    {
      hierMesh().chunkVisible("Wire_D0", false);
    }
    super.update(f);
  }

  public static void moveGear(HierMesh hiermesh, float f)
  {
    hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 77.0F * f, 0.0F);
    hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 77.0F * f, 0.0F);
    hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 157.0F * f, 0.0F);
    hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 157.0F * f, 0.0F);
    hiermesh.chunkSetAngles("GearC2_D0", 20.0F * f, 0.0F, 0.0F);
    float f1 = Math.max(-f * 1500.0F, -94.0F);
    hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -f1, 0.0F);
    hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -f1, 0.0F);
  }

  protected void moveGear(float f)
  {
    moveGear(hierMesh(), f);
  }

  public boolean cut(String s)
  {
    if (s.startsWith("Tail1"))
    {
      this.FM.AS.hitTank(this, 2, 4);
    }
    return super.cut(s);
  }

  protected boolean cutFM(int i, int j, Actor actor)
  {
    switch (i)
    {
    case 33:
      return super.cutFM(34, j, actor);
    case 36:
      return super.cutFM(37, j, actor);
    case 34:
    case 35: } return super.cutFM(i, j, actor);
  }

  protected void hitBone(String s, Shot shot, Point3d point3d)
  {
    if (s.startsWith("xx"))
    {
      if (s.startsWith("xxarmor"))
      {
        Aircraft.debugprintln(this, "*** Armor: Hit..");
        if (s.endsWith("p1"))
        {
          getEnergyPastArmor(World.Rnd().nextFloat(50.0F, 50.0F), shot);
          if (World.Rnd().nextFloat() < 0.15F)
          {
            this.FM.AS.setCockpitState(shot.initiator, this.FM.AS.astateCockpitState | 0x2);
          }
          Aircraft.debugprintln(this, "*** Armor Glass: Hit..");
          if (shot.power <= 0.0F)
          {
            Aircraft.debugprintln(this, "*** Armor Glass: Bullet Stopped..");
            if (World.Rnd().nextFloat() < 0.5F)
            {
              doRicochetBack(shot);
            }
          }
        }
        else if (s.endsWith("p3"))
        {
          if (point3d.z < -0.27D)
          {
            getEnergyPastArmor(4.099999904632568D / (Math.abs(Aircraft.v1.z) + 9.999999747378752E-006D), shot);
          }
          else {
            getEnergyPastArmor(8.100000381469727D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-006D), shot);
          }
        }
        else if (s.endsWith("p6"))
        {
          getEnergyPastArmor(8.0D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-006D), shot);
        }
        return;
      }
      if (s.startsWith("xxcontrols"))
      {
        int i = s.charAt(10) - '0';
        switch (i)
        {
        case 7:
        default:
          break;
        case 1:
        case 4:
          if (getEnergyPastArmor(0.1F, shot) <= 0.0F)
            break;
          this.FM.AS.setControlsDamage(shot.initiator, 0);
          Aircraft.debugprintln(this, "*** Aileron Controls: Control Crank Destroyed..");

          break;
        case 2:
        case 3:
          if ((getEnergyPastArmor(0.12F, shot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.5F))
            break;
          this.FM.AS.setControlsDamage(shot.initiator, 0);
          Aircraft.debugprintln(this, "*** Aileron Controls: Disabled..");

          break;
        case 5:
          if ((getEnergyPastArmor(0.12F, shot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.5F))
            break;
          this.FM.AS.setControlsDamage(shot.initiator, 1);
          Aircraft.debugprintln(this, "*** Elevator Controls: Disabled / Strings Broken..");

          break;
        case 6:
          if ((getEnergyPastArmor(0.12F, shot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.5F))
            break;
          this.FM.AS.setControlsDamage(shot.initiator, 2);
          Aircraft.debugprintln(this, "*** Rudder Controls: Disabled / Strings Broken..");

          break;
        case 8:
          if (getEnergyPastArmor(3.2F, shot) <= 0.0F)
            break;
          Aircraft.debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
          this.FM.AS.setControlsDamage(shot.initiator, 2);
          this.FM.AS.setControlsDamage(shot.initiator, 1);
          this.FM.AS.setControlsDamage(shot.initiator, 0);
        }

        return;
      }
      if (s.startsWith("xxspar"))
      {
        Aircraft.debugprintln(this, "*** Spar Construction: Hit..");
        if ((s.startsWith("xxspart")) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(2.4F / (float)Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z), shot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** Tail1 Spars Broken in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
        }
        if ((s.startsWith("xxsparli")) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(23.0F * World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
        }
        if ((s.startsWith("xxsparri")) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(23.0F * World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
        }
        if ((s.startsWith("xxsparlm")) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(23.700001F * World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
        }
        if ((s.startsWith("xxsparrm")) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(23.700001F * World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
        }
        if ((s.startsWith("xxsparlo")) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(23.700001F * World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
        }
        if ((s.startsWith("xxsparro")) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(23.700001F * World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
        }
        return;
      }
      if (s.startsWith("xxlock"))
      {
        Aircraft.debugprintln(this, "*** Lock Construction: Hit..");
        if ((s.startsWith("xxlockr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** Rudder Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), shot.initiator);
        }
        if ((s.startsWith("xxlockvl")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** VatorL Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), shot.initiator);
        }
        if ((s.startsWith("xxlockvr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** VatorR Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), shot.initiator);
        }
        return;
      }
      if (s.startsWith("xxeng"))
      {
        Aircraft.debugprintln(this, "*** Engine Module: Hit..");
        if (s.endsWith("pipe"))
        {
          if ((World.Rnd().nextFloat() < 0.1F) && (this.FM.EI.engines[0].getType() == 0) && (this.FM.CT.Weapons[1] != null) && (this.FM.CT.Weapons[1].length != 2))
          {
            this.FM.AS.setJamBullets(1, 0);
            Aircraft.debugprintln(this, "*** Engine Module: Nose Nozzle Pipe Bent..");
          }
          getEnergyPastArmor(0.3F, shot);
        }
        else if (s.endsWith("prop"))
        {
          if ((getEnergyPastArmor(0.1F, shot) > 0.0F) && (World.Rnd().nextFloat() < 0.9F))
          {
            if (World.Rnd().nextFloat() < 0.5F)
            {
              this.FM.AS.setEngineSpecificDamage(shot.initiator, 0, 3);
              Aircraft.debugprintln(this, "*** Engine Module: Prop Governor Hit, Disabled..");
            }
            else {
              this.FM.AS.setEngineSpecificDamage(shot.initiator, 0, 4);
              Aircraft.debugprintln(this, "*** Engine Module: Prop Governor Hit, Damaged..");
            }
          }
        }
        else if (s.endsWith("gear"))
        {
          if (getEnergyPastArmor(4.6F, shot) > 0.0F)
          {
            if (World.Rnd().nextFloat() < 0.5F)
            {
              this.FM.EI.engines[0].setEngineStuck(shot.initiator);
              Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Reductor Gear..");
            }
            else {
              this.FM.AS.setEngineSpecificDamage(shot.initiator, 0, 3);
              this.FM.AS.setEngineSpecificDamage(shot.initiator, 0, 4);
              Aircraft.debugprintln(this, "*** Engine Module: Reductor Gear Damaged, Prop Governor Failed..");
            }
          }
        }
        else if (s.endsWith("supc"))
        {
          if (getEnergyPastArmor(0.1F, shot) > 0.0F)
          {
            this.FM.AS.setEngineSpecificDamage(shot.initiator, 0, 0);
            Aircraft.debugprintln(this, "*** Engine Module: Supercharger Disabled..");
          }
          getEnergyPastArmor(0.5F, shot);
        }
        else if (s.endsWith("feed"))
        {
          if ((getEnergyPastArmor(8.9F, shot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F) && (this.FM.EI.engines[0].getPowerOutput() > 0.7F) && (this.FM.EI.engines[0].getType() == 0))
          {
            this.FM.AS.hitEngine(shot.initiator, 0, 100);
            Aircraft.debugprintln(this, "*** Engine Module: Pressurized Fuel Line Pierced, Fuel Flamed..");
          }
          getEnergyPastArmor(1.0F, shot);
        }
        else if (s.endsWith("fuel"))
        {
          if ((getEnergyPastArmor(1.1F, shot) > 0.0F) && (this.FM.EI.engines[0].getType() == 0))
          {
            this.FM.EI.engines[0].setEngineStops(shot.initiator);
            Aircraft.debugprintln(this, "*** Engine Module: Fuel Line Stalled, Engine Stalled..");
          }
          getEnergyPastArmor(1.0F, shot);
        }
        else if (s.endsWith("case"))
        {
          if (getEnergyPastArmor(4.2F, shot) > 0.0F)
          {
            if (World.Rnd().nextFloat() < shot.power / 175000.0F)
            {
              this.FM.AS.setEngineStuck(shot.initiator, 0);
              Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Crank Ball Bearing..");
            }
            if ((World.Rnd().nextFloat() < shot.power / 50000.0F) && (this.FM.EI.engines[0].getType() == 0))
            {
              this.FM.AS.hitEngine(shot.initiator, 0, 2);
              Aircraft.debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
            }
            if (this.FM.EI.engines[0].getType() == 0)
            {
              this.FM.EI.engines[0].setReadyness(shot.initiator, this.FM.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, shot.power / 48000.0F));
            }
            Aircraft.debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
          }
          getEnergyPastArmor(27.5F, shot);
        }
        else if (s.startsWith("xxeng1cyl"))
        {
          if (getEnergyPastArmor(2.4F, shot) > 0.0F) if (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * (this.FM.EI.engines[0].getType() != 0 ? 0.5F : 1.75F))
            {
              if (this.FM.EI.engines[0].getType() == 0)
              {
                this.FM.EI.engines[0].setCyliderKnockOut(shot.initiator, World.Rnd().nextInt(1, (int)(shot.power / 4800.0F)));
              }
              else {
                this.FM.EI.engines[0].setCyliderKnockOut(shot.initiator, World.Rnd().nextInt(1, (int)(shot.power / 19200.0F)));
              }
              Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");
              if ((World.Rnd().nextFloat() < shot.power / 48000.0F) && (this.FM.EI.engines[0].getType() == 0))
              {
                this.FM.AS.hitEngine(shot.initiator, 0, 3);
                Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, Engine Fires..");
              }
              if ((World.Rnd().nextFloat() < shot.power / 48000.0F) && (this.FM.EI.engines[0].getType() == 1))
              {
                this.FM.AS.hitEngine(shot.initiator, 0, 1);
                Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, Engine Fires..");
              }
              if (World.Rnd().nextFloat() < 0.01F)
              {
                this.FM.AS.setEngineStuck(shot.initiator, 0);
                Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Piston Head..");
              }
              getEnergyPastArmor(43.599998F, shot);
            }
        }
        else if (s.startsWith("xxeng1mag"))
        {
          int j = s.charAt(9) - '1';
          this.FM.EI.engines[0].setMagnetoKnockOut(shot.initiator, j);
          Aircraft.debugprintln(this, "*** Engine Module: Magneto " + j + " Destroyed..");
        }
        else if (s.endsWith("sync"))
        {
          if ((getEnergyPastArmor(2.1F, shot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F))
          {
            Aircraft.debugprintln(this, "*** Engine Module: Gun Synchronized Hit, Nose Guns Lose Authority..");
          }
        }
        else if ((s.endsWith("oil1")) && (getEnergyPastArmor(2.4F, shot) > 0.0F))
        {
          this.FM.AS.hitOil(shot.initiator, 0);
          Aircraft.debugprintln(this, "*** Engine Module: Oil Radiator Hit..");
        }
        return;
      }
      if (s.startsWith("xxtank"))
      {
        int k = s.charAt(6) - '0';
        switch (k)
        {
        default:
          break;
        case 1:
          if ((getEnergyPastArmor(0.2F, shot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.5F))
            break;
          if (this.FM.AS.astateTankStates[2] == 0)
          {
            Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
            this.FM.AS.hitTank(shot.initiator, 2, 1);
            this.FM.AS.doSetTankState(shot.initiator, 2, 1);
          }
          else if (((shot.powerType == 3) && (World.Rnd().nextFloat() < 0.9F)) || (World.Rnd().nextFloat() < 0.03F))
          {
            this.FM.AS.hitTank(shot.initiator, 2, 2);
            Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
          }
          if (shot.power <= 200000.0F)
            break;
          this.FM.AS.hitTank(shot.initiator, 2, 99);
          Aircraft.debugprintln(this, "*** Fuel Tank: Major Hit..");

          break;
        case 2:
          if ((getEnergyPastArmor(1.2F, shot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.25F))
          {
            break;
          }
          if (this.FM.AS.astateTankStates[1] == 0)
          {
            Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
            this.FM.AS.hitTank(shot.initiator, 1, 1);
            this.FM.AS.doSetTankState(shot.initiator, 1, 1);
          }
          else if (((shot.powerType == 3) && (World.Rnd().nextFloat() < 0.8F)) || (World.Rnd().nextFloat() < 0.03F))
          {
            this.FM.AS.hitTank(shot.initiator, 1, 2);
            Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
          }
          if (shot.power <= 200000.0F)
            break;
          this.FM.AS.hitTank(shot.initiator, 1, 99);
          Aircraft.debugprintln(this, "*** Fuel Tank: Major Hit..");

          break;
        case 3:
          if ((getEnergyPastArmor(1.2F, shot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.25F))
          {
            break;
          }
          if (this.FM.AS.astateTankStates[0] == 0)
          {
            Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
            this.FM.AS.hitTank(shot.initiator, 0, 1);
            this.FM.AS.doSetTankState(shot.initiator, 0, 1);
          }
          else if (((shot.powerType == 3) && (World.Rnd().nextFloat() < 0.8F)) || (World.Rnd().nextFloat() < 0.03F))
          {
            this.FM.AS.hitTank(shot.initiator, 0, 2);
            Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
          }
          if (shot.power <= 200000.0F)
            break;
          this.FM.AS.hitTank(shot.initiator, 0, 99);
          Aircraft.debugprintln(this, "*** Fuel Tank: Major Hit..");
        }

        return;
      }
      if (s.startsWith("xxmw50"))
      {
        if (World.Rnd().nextFloat() < 0.05F)
        {
          Aircraft.debugprintln(this, "*** MW50 Tank: Pierced..");
          this.FM.AS.setInternalDamage(shot.initiator, 2);
        }
        return;
      }
      if (s.startsWith("xxmgun"))
      {
        if (s.endsWith("01"))
        {
          this.FM.AS.setJamBullets(1, 0);
        }
        if (s.endsWith("02"))
        {
          this.FM.AS.setJamBullets(1, 1);
        }
        getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F), shot);
        return;
      }
      if (s.startsWith("xxcannon"))
      {
        Aircraft.debugprintln(this, "*** Nose Cannon: Disabled..");
        this.FM.AS.setJamBullets(0, 0);
        getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F), shot);
        return;
      }
      if (s.startsWith("xxradiat"))
      {
        this.FM.EI.engines[0].setReadyness(shot.initiator, this.FM.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, 0.05F));
        Aircraft.debugprintln(this, "*** Engine Module: Radiator Hit, Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
      }
      return;
    }
    if ((s.startsWith("xcf")) || (s.startsWith("xcockpit")))
    {
      if (chunkDamageVisible("CF") < 3)
      {
        hitChunk("CF", shot);
      }
      if (s.startsWith("xcockpit"))
      {
        if (point3d.z > 0.4D)
        {
          if (World.Rnd().nextFloat() < 0.2F)
          {
            this.FM.AS.setCockpitState(shot.initiator, this.FM.AS.astateCockpitState | 0x1);
          }
          if (World.Rnd().nextFloat() < 0.2F)
          {
            this.FM.AS.setCockpitState(shot.initiator, this.FM.AS.astateCockpitState | 0x20);
          }
        }
        else if (point3d.y > 0.0D)
        {
          if (World.Rnd().nextFloat() < 0.2F)
          {
            this.FM.AS.setCockpitState(shot.initiator, this.FM.AS.astateCockpitState | 0x4);
          }
        }
        else if (World.Rnd().nextFloat() < 0.2F)
        {
          this.FM.AS.setCockpitState(shot.initiator, this.FM.AS.astateCockpitState | 0x10);
        }
        if ((point3d.x > 0.2D) && (World.Rnd().nextFloat() < 0.2F))
        {
          this.FM.AS.setCockpitState(shot.initiator, this.FM.AS.astateCockpitState | 0x40);
        }
      }
    }
    else if (s.startsWith("xeng"))
    {
      if (chunkDamageVisible("Engine1") < 2)
      {
        hitChunk("Engine1", shot);
      }
    }
    else if (s.startsWith("xtail"))
    {
      if (chunkDamageVisible("Tail1") < 3)
      {
        hitChunk("Tail1", shot);
      }
    }
    else if (s.startsWith("xkeel"))
    {
      if (chunkDamageVisible("Keel1") < 2)
      {
        hitChunk("Keel1", shot);
      }
    }
    else if (s.startsWith("xrudder"))
    {
      if (chunkDamageVisible("Rudder1") < 1)
      {
        hitChunk("Rudder1", shot);
      }
    }
    else if (s.startsWith("xstab"))
    {
      if ((s.startsWith("xstabl")) && (chunkDamageVisible("StabL") < 2))
      {
        hitChunk("StabL", shot);
      }
      if ((s.startsWith("xstabr")) && (chunkDamageVisible("StabR") < 1))
      {
        hitChunk("StabR", shot);
      }
    }
    else if (s.startsWith("xvator"))
    {
      if ((s.startsWith("xvatorl")) && (chunkDamageVisible("VatorL") < 1))
      {
        hitChunk("VatorL", shot);
      }
      if ((s.startsWith("xvatorr")) && (chunkDamageVisible("VatorR") < 1))
      {
        hitChunk("VatorR", shot);
      }
    }
    else if (s.startsWith("xwing"))
    {
      if ((s.startsWith("xwinglin")) && (chunkDamageVisible("WingLIn") < 3))
      {
        hitChunk("WingLIn", shot);
      }
      if ((s.startsWith("xwingrin")) && (chunkDamageVisible("WingRIn") < 3))
      {
        hitChunk("WingRIn", shot);
      }
      if ((s.startsWith("xwinglmid")) && (chunkDamageVisible("WingLMid") < 3))
      {
        hitChunk("WingLMid", shot);
      }
      if ((s.startsWith("xwingrmid")) && (chunkDamageVisible("WingRMid") < 3))
      {
        hitChunk("WingRMid", shot);
      }
      if ((s.startsWith("xwinglout")) && (chunkDamageVisible("WingLOut") < 3))
      {
        hitChunk("WingLOut", shot);
      }
      if ((s.startsWith("xwingrout")) && (chunkDamageVisible("WingROut") < 3))
      {
        hitChunk("WingROut", shot);
      }
    }
    else if (s.startsWith("xarone"))
    {
      if (s.startsWith("xaronel"))
      {
        hitChunk("AroneL", shot);
      }
      if (s.startsWith("xaroner"))
      {
        hitChunk("AroneR", shot);
      }
    }
    else if ((s.startsWith("xpilot")) || (s.startsWith("xhead")))
    {
      byte byte0 = 0;
      int l;
      int l;
      if (s.endsWith("a"))
      {
        byte0 = 1;
        l = s.charAt(6) - '1';
      }
      else
      {
        int l;
        if (s.endsWith("b"))
        {
          byte0 = 2;
          l = s.charAt(6) - '1';
        }
        else {
          l = s.charAt(5) - '1';
        }
      }
      hitFlesh(l, shot, byte0);
    }

    if (s.startsWith("xxammo"))
    {
      Aircraft.debugprintln(this, "*** Ammo: Hit..");
      if (s.endsWith("05"))
      {
        getEnergyPastArmor(World.Rnd().nextFloat(50.0F, 50.0F), shot);
      }if (World.Rnd().nextFloat() < 0.08F) {
        debuggunnery("Armament: Cannon Gun (1) Payload Detonates..");
        this.FM.AS.hitTank(shot.initiator, 1, 99);
        nextDMGLevels(3, 2, "WingRMid_D" + chunkDamageVisible("WingRMid"), shot.initiator);
      }
    }

    if (s.endsWith("06"))
    {
      getEnergyPastArmor(World.Rnd().nextFloat(50.0F, 50.0F), shot);
    }if (World.Rnd().nextFloat() < 0.08F)
    {
      nextDMGLevels(3, 2, "WingRMid_D", shot.initiator);
    }
  }

  static Class _mthclass$(String s)
  {
    try
    {
      class1 = Class.forName(s);
    }
    catch (ClassNotFoundException classnotfoundexception)
    {
      Class class1;
      throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }
    Class class1;
    return class1;
  }
}