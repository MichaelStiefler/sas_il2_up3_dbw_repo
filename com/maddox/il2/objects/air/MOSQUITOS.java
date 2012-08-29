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

public abstract class MOSQUITOS extends Scheme2
{
  private float kangle1;
  private float kangle2;

  static
  {
    Class class1 = CLASS.THIS();
    Property.set(class1, "originCountry", PaintScheme.countryBritain);
  }

  public MOSQUITOS()
  {
    this.kangle1 = 0.0F;
    this.kangle2 = 0.0F;
  }

  public void rareAction(float f, boolean flag)
  {
    super.rareAction(f, flag);
    if (this.FM.getAltitude() < 3000.0F)
    {
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
    }
    else {
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
      hierMesh().chunkVisible("HMask2_D0", hierMesh().isChunkVisible("Pilot2_D0"));
    }
  }

  protected void moveBayDoor(float f)
  {
    hierMesh().chunkSetAngles("Bay01_D0", 0.0F, -90.0F * f, 0.0F);
    hierMesh().chunkSetAngles("Bay02_D0", 0.0F, -90.0F * f, 0.0F);
  }

  public static void moveGear(HierMesh hiermesh, float f)
  {
    hiermesh.chunkSetAngles("GearC2_D0", 0.0F, Aircraft.cvt(f, 0.0F, 0.6F, 0.0F, -80.0F), 0.0F);
    hiermesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
    hiermesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(f, 0.1F, 0.85F, 0.0F, -114.0F), 0.0F);
    hiermesh.chunkSetAngles("GearL4_D0", 0.0F, Aircraft.cvt(f, 0.1F, 0.85F, 0.0F, -59.0F), 0.0F);
    hiermesh.chunkSetAngles("GearL5_D0", 0.0F, Aircraft.cvt(f, 0.1F, 0.85F, 0.0F, -116.5F), 0.0F);
    hiermesh.chunkSetAngles("GearL6_D0", 0.0F, 0.0F, 0.0F);
    hiermesh.chunkSetAngles("GearL7_D0", 0.0F, Aircraft.cvt(f, 0.1F, 0.15F, 0.0F, -42.0F), 0.0F);
    hiermesh.chunkSetAngles("GearL8_D0", 0.0F, Aircraft.cvt(f, 0.1F, 0.15F, 0.0F, -38.0F), 0.0F);
    hiermesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(f, 0.2F, 0.95F, 0.0F, -114.0F), 0.0F);
    hiermesh.chunkSetAngles("GearR4_D0", 0.0F, Aircraft.cvt(f, 0.2F, 0.95F, 0.0F, -59.0F), 0.0F);
    hiermesh.chunkSetAngles("GearR5_D0", 0.0F, Aircraft.cvt(f, 0.2F, 0.95F, 0.0F, -116.5F), 0.0F);
    hiermesh.chunkSetAngles("GearR6_D0", 0.0F, 0.0F, 0.0F);
    hiermesh.chunkSetAngles("GearR7_D0", 0.0F, Aircraft.cvt(f, 0.2F, 0.25F, 0.0F, -42.0F), 0.0F);
    hiermesh.chunkSetAngles("GearR8_D0", 0.0F, Aircraft.cvt(f, 0.2F, 0.25F, 0.0F, -38.0F), 0.0F);
  }

  protected void moveGear(float f)
  {
    moveGear(hierMesh(), f);
  }

  public void moveSteering(float f)
  {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
  }

  public void moveWheelSink()
  {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.14F, 0.0F, 0.14F);
    hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[1] = Aircraft.cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.14F, 0.0F, 0.14F);
    hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
  }

  public void doMurderPilot(int i)
  {
    switch (i)
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

  protected void hitBone(String s, Shot shot, Point3d point3d)
  {
    if (s.startsWith("xx"))
    {
      if (s.startsWith("xxarmor"))
      {
        if ((s.endsWith("p1")) || (s.endsWith("p2")))
        {
          getEnergyPastArmor(16.65F / (1.0E-005F + (float)Math.abs(Aircraft.v1.x)), shot);
        }
      }
      else if (s.startsWith("xxcontrols"))
      {
        if (s.endsWith("1"))
        {
          if (getEnergyPastArmor(3.2F, shot) > 0.0F)
          {
            Aircraft.debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
            this.FM.AS.setControlsDamage(shot.initiator, 2);
            this.FM.AS.setControlsDamage(shot.initiator, 1);
            this.FM.AS.setControlsDamage(shot.initiator, 0);
          }
        }
        else if (s.endsWith("2"))
        {
          if (getEnergyPastArmor(0.1F, shot) > 0.0F)
          {
            if (World.Rnd().nextFloat() < 0.33F)
            {
              this.FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
              this.FM.AS.setEngineSpecificDamage(shot.initiator, 0, 6);
              this.FM.AS.setCockpitState(shot.initiator, this.FM.AS.astateCockpitState | 0x4);
              Aircraft.debugprintln(this, "*** Throttle Quadrant: Hit, Engine 1 Controls Disabled..");
            }
            if (World.Rnd().nextFloat() < 0.33F)
            {
              this.FM.AS.setEngineSpecificDamage(shot.initiator, 1, 1);
              this.FM.AS.setEngineSpecificDamage(shot.initiator, 1, 6);
              this.FM.AS.setCockpitState(shot.initiator, this.FM.AS.astateCockpitState | 0x8);
              Aircraft.debugprintln(this, "*** Throttle Quadrant: Hit, Engine 2 Controls Disabled..");
            }
          }
        }
        else if ((s.endsWith("3")) || (s.endsWith("4")))
        {
          if (World.Rnd().nextFloat() < 0.12F)
          {
            this.FM.AS.setControlsDamage(shot.initiator, 1);
            Aircraft.debugprintln(this, "*** Evelator Controls Out..");
          }
        }
        else if (World.Rnd().nextFloat() < 0.12F)
        {
          this.FM.AS.setControlsDamage(shot.initiator, 0);
          Aircraft.debugprintln(this, "*** Arone Controls Out..");
        }
      }
      else if (s.startsWith("xxspar"))
      {
        if (((s.endsWith("t1")) || (s.endsWith("t2")) || (s.endsWith("t3")) || (s.endsWith("t4"))) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.5F / (float)Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z), shot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** Tail1 Spars Broken in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
        }
        if (((s.endsWith("li1")) || (s.endsWith("li2")) || (s.endsWith("li3")) || (s.endsWith("li4"))) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
        }
        if (((s.endsWith("ri1")) || (s.endsWith("ri2")) || (s.endsWith("ri3")) || (s.endsWith("ri4"))) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
        }
        if (((s.endsWith("lm1")) || (s.endsWith("lm2")) || (s.endsWith("lm3")) || (s.endsWith("lm4"))) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
        }
        if (((s.endsWith("rm1")) || (s.endsWith("rm2")) || (s.endsWith("rm3")) || (s.endsWith("rm4"))) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
        }
        if (((s.endsWith("lo1")) || (s.endsWith("lo2"))) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
        }
        if (((s.endsWith("ro1")) || (s.endsWith("ro2"))) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
        }
      }
      else if (s.startsWith("xxeng"))
      {
        int i = s.charAt(5) - '1';
        Aircraft.debugprintln(this, "*** Engine" + i + " Hit..");
        if ((s.endsWith("case")) && (getEnergyPastArmor(0.1F, shot) > 0.0F))
        {
          if (World.Rnd().nextFloat() < shot.power / 200000.0F)
          {
            this.FM.AS.setEngineStuck(shot.initiator, i);
            Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Stucks..");
          }
          if (World.Rnd().nextFloat() < shot.power / 50000.0F)
          {
            this.FM.AS.hitEngine(shot.initiator, i, 2);
            Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Damaged..");
          }
          if (World.Rnd().nextFloat() < shot.power / 28000.0F)
          {
            this.FM.EI.engines[i].setCyliderKnockOut(shot.initiator, 1);
            Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Cylinder Feed Out, " + this.FM.EI.engines[i].getCylindersOperable() + "/" + this.FM.EI.engines[i].getCylinders() + " Left..");
          }
          this.FM.EI.engines[i].setReadyness(shot.initiator, this.FM.EI.engines[i].getReadyness() - World.Rnd().nextFloat(0.0F, shot.power / 48000.0F));
          Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Readyness Reduced to " + this.FM.EI.engines[i].getReadyness() + "..");
        }
        if ((s.endsWith("cyl1")) && (getEnergyPastArmor(0.45F, shot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[i].getCylindersRatio() * 1.75F))
        {
          this.FM.EI.engines[i].setCyliderKnockOut(shot.initiator, World.Rnd().nextInt(1, (int)(shot.power / 4800.0F)));
          Aircraft.debugprintln(this, "*** Engine Cylinders Hit, " + this.FM.EI.engines[i].getCylindersOperable() + "/" + this.FM.EI.engines[i].getCylinders() + " Left..");
          if (this.FM.AS.astateEngineStates[i] < 1)
          {
            this.FM.AS.hitEngine(shot.initiator, i, 1);
          }
          if (World.Rnd().nextFloat() < shot.power / 24000.0F)
          {
            this.FM.AS.hitEngine(shot.initiator, i, 3);
            Aircraft.debugprintln(this, "*** Engine Cylinders Hit - Engine Fires..");
          }
          getEnergyPastArmor(25.0F, shot);
        }
        if ((s.endsWith("supc")) && (getEnergyPastArmor(0.05F, shot) > 0.0F))
        {
          this.FM.EI.engines[i].setKillCompressor(shot.initiator);
        }
        if ((s.endsWith("prop")) && (getEnergyPastArmor(0.2F, shot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F))
        {
          this.FM.EI.engines[i].setKillPropAngleDevice(shot.initiator);
        }
      }
      else if (s.startsWith("xxtank"))
      {
        int j = s.charAt(6) - '0';
        switch (j)
        {
        case 1:
        case 2:
          doHitMeATank(shot, 0);
          break;
        case 3:
          doHitMeATank(shot, 1);
          break;
        case 4:
        case 5:
          doHitMeATank(shot, 2);
        default:
          break;
        }
      } else if ((s.startsWith("xxw1")) || (s.startsWith("xxoil1")))
      {
        if ((World.Rnd().nextFloat() < 0.12F) && (getEnergyPastArmor(2.25F, shot) > 0.0F))
        {
          this.FM.AS.hitOil(shot.initiator, 0);
          Aircraft.debugprintln(this, "*** Oil Radiator L Hit..");
        }
      }
      else if ((s.startsWith("xxw2")) || (s.startsWith("xxoil1")))
      {
        if ((World.Rnd().nextFloat() < 0.12F) && (getEnergyPastArmor(2.25F, shot) > 0.0F))
        {
          this.FM.AS.hitOil(shot.initiator, 1);
          Aircraft.debugprintln(this, "*** Oil Radiator R Hit..");
        }
      }
      else if (s.startsWith("xxlock"))
      {
        debuggunnery("Lock Construction: Hit..");
        if ((s.startsWith("xxlockr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F))
        {
          debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), shot.initiator);
        }
        if ((s.startsWith("xxlockvl")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F))
        {
          debuggunnery("Lock Construction: VatorL Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), shot.initiator);
        }
        if ((s.startsWith("xxlockvr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F))
        {
          debuggunnery("Lock Construction: VatorR Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), shot.initiator);
        }
      }
      else if (s.startsWith("xxmgun0"))
      {
        int k = s.charAt(7) - '1';
        if (getEnergyPastArmor(0.75F, shot) > 0.0F)
        {
          debuggunnery("Armament: Machine Gun (" + k + ") Disabled..");
          this.FM.AS.setJamBullets(0, k);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), shot);
        }
      }
      else if (s.startsWith("xxcannon0"))
      {
        int l = s.charAt(9) - '1';
        if (getEnergyPastArmor(6.29F, shot) > 0.0F)
        {
          debuggunnery("Armament: Cannon (" + l + ") Disabled..");
          this.FM.AS.setJamBullets(1, l);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), shot);
        }
      }
    }
    else if ((s.startsWith("xcf")) || (s.startsWith("xcockpit")))
    {
      hitChunk("CF", shot);
      if (point3d.x > 0.0D)
      {
        if (World.Rnd().nextFloat() < 0.1F)
        {
          this.FM.AS.setCockpitState(shot.initiator, this.FM.AS.astateCockpitState | 0x40);
        }
        if (point3d.z > 0.4D)
        {
          this.FM.AS.setCockpitState(shot.initiator, this.FM.AS.astateCockpitState | 0x1);
        }
      }
    }
    else if (s.startsWith("xengine1"))
    {
      if (chunkDamageVisible("Engine1") < 2)
      {
        hitChunk("Engine1", shot);
      }
    }
    else if (s.startsWith("xengine2"))
    {
      if (chunkDamageVisible("Engine2") < 2)
      {
        hitChunk("Engine2", shot);
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
      hitChunk("Keel1", shot);
    }
    else if (s.startsWith("xrudder"))
    {
      hitChunk("Rudder1", shot);
    }
    else if (s.startsWith("xstab"))
    {
      if (s.startsWith("xstabl"))
      {
        hitChunk("StabL", shot);
      }
      if (s.startsWith("xstabr"))
      {
        hitChunk("StabR", shot);
      }
    }
    else if (s.startsWith("xvator"))
    {
      if (s.startsWith("xvatorl"))
      {
        hitChunk("VatorL", shot);
      }
      if (s.startsWith("xvatorr"))
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
      if (s.startsWith("xwinglmid"))
      {
        if (chunkDamageVisible("WingLMid") < 3)
        {
          hitChunk("WingLMid", shot);
        }
        if (World.Rnd().nextFloat() < shot.mass + 0.02F)
        {
          this.FM.AS.hitOil(shot.initiator, 0);
        }
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
    else if (s.startsWith("xgear"))
    {
      if (s.endsWith("1"))
      {
        if (World.Rnd().nextFloat() < 0.05F)
        {
          debuggunnery("Hydro System: Disabled..");
          this.FM.AS.setInternalDamage(shot.initiator, 0);
        }
      }
      else if ((World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 3.435F), shot) > 0.0F))
      {
        debuggunnery("Undercarriage: Stuck..");
        this.FM.AS.setInternalDamage(shot.initiator, 3);
      }
    }
    else if ((s.startsWith("xpilot")) || (s.startsWith("xhead")))
    {
      byte byte0 = 0;
      int i1;
      int i1;
      if (s.endsWith("a"))
      {
        byte0 = 1;
        i1 = s.charAt(6) - '1';
      }
      else
      {
        int i1;
        if (s.endsWith("b"))
        {
          byte0 = 2;
          i1 = s.charAt(6) - '1';
        }
        else {
          i1 = s.charAt(5) - '1';
        }
      }
      hitFlesh(i1, shot, byte0);
    }
  }

  private final void doHitMeATank(Shot shot, int i)
  {
    if (getEnergyPastArmor(0.2F, shot) > 0.0F)
    {
      if (shot.power < 14100.0F)
      {
        if (this.FM.AS.astateTankStates[i] == 0)
        {
          this.FM.AS.hitTank(shot.initiator, i, 1);
          this.FM.AS.doSetTankState(shot.initiator, i, 1);
        }
        if ((this.FM.AS.astateTankStates[i] > 0) && ((World.Rnd().nextFloat() < 0.02F) || ((shot.powerType == 3) && (World.Rnd().nextFloat() < 0.25F))))
        {
          this.FM.AS.hitTank(shot.initiator, i, 2);
        }
      }
      else {
        this.FM.AS.hitTank(shot.initiator, i, World.Rnd().nextInt(0, (int)(shot.power / 56000.0F)));
      }
    }
  }

  protected boolean cutFM(int i, int j, Actor actor)
  {
    switch (i)
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

    return super.cutFM(i, j, actor);
  }

  public void update(float f)
  {
    super.update(f);
    float f1 = this.FM.EI.engines[0].getControlRadiator();
    if (Math.abs(this.kangle1 - f1) > 0.01F)
    {
      this.kangle1 = f1;
      hierMesh().chunkSetAngles("WaterL_D0", 0.0F, -45.0F * f1, 0.0F);
    }
    f1 = this.FM.EI.engines[1].getControlRadiator();
    if (Math.abs(this.kangle2 - f1) > 0.01F)
    {
      this.kangle2 = f1;
      hierMesh().chunkSetAngles("WaterR_D0", 0.0F, -45.0F * f1, 0.0F);
    }
  }
}