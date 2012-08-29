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
  private float kangle1 = 0.0F;
  private float kangle2 = 0.0F;

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (this.FM.getAltitude() < 3000.0F) {
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
    } else {
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
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, cvt(paramFloat, 0.0F, 0.6F, 0.0F, -80.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, cvt(paramFloat, 0.1F, 0.85F, 0.0F, -114.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, cvt(paramFloat, 0.1F, 0.85F, 0.0F, -59.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, cvt(paramFloat, 0.1F, 0.85F, 0.0F, -116.5F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, cvt(paramFloat, 0.1F, 0.15F, 0.0F, -42.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL8_D0", 0.0F, cvt(paramFloat, 0.1F, 0.15F, 0.0F, -38.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, cvt(paramFloat, 0.2F, 0.95F, 0.0F, -114.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, cvt(paramFloat, 0.2F, 0.95F, 0.0F, -59.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, cvt(paramFloat, 0.2F, 0.95F, 0.0F, -116.5F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, cvt(paramFloat, 0.2F, 0.25F, 0.0F, -42.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR8_D0", 0.0F, cvt(paramFloat, 0.2F, 0.25F, 0.0F, -38.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }
  public void moveWheelSink() {
    resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.14F, 0.0F, 0.14F);
    hierMesh().chunkSetLocate("GearL3_D0", xyz, ypr);
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.14F, 0.0F, 0.14F);
    hierMesh().chunkSetLocate("GearR3_D0", xyz, ypr);
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
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
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if ((paramString.endsWith("p1")) || (paramString.endsWith("p2"))) {
          getEnergyPastArmor(16.65F / (1.0E-005F + (float)Math.abs(v1.x)), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        if (paramString.endsWith("1")) {
          if (getEnergyPastArmor(3.2F, paramShot) > 0.0F) {
            debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
            this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          }
        } else if (paramString.endsWith("2")) {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < 0.33F) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
              this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
              debugprintln(this, "*** Throttle Quadrant: Hit, Engine 1 Controls Disabled..");
            }
            if (World.Rnd().nextFloat() < 0.33F) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 1);
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 6);
              this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
              debugprintln(this, "*** Throttle Quadrant: Hit, Engine 2 Controls Disabled..");
            }
          }
        } else if ((paramString.endsWith("3")) || (paramString.endsWith("4"))) {
          if (World.Rnd().nextFloat() < 0.12F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
            debugprintln(this, "*** Evelator Controls Out..");
          }
        }
        else if (World.Rnd().nextFloat() < 0.12F) {
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          debugprintln(this, "*** Arone Controls Out..");
        }

        return;
      }
      if (paramString.startsWith("xxspar")) {
        if (((paramString.endsWith("t1")) || (paramString.endsWith("t2")) || (paramString.endsWith("t3")) || (paramString.endsWith("t4"))) && 
          (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.5F / (float)Math.sqrt(v1.y * v1.y + v1.z * v1.z), paramShot) > 0.0F)) {
          debugprintln(this, "*** Tail1 Spars Broken in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("li1")) || (paramString.endsWith("li2")) || (paramString.endsWith("li3")) || (paramString.endsWith("li4"))) && 
          (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("ri1")) || (paramString.endsWith("ri2")) || (paramString.endsWith("ri3")) || (paramString.endsWith("ri4"))) && 
          (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("lm1")) || (paramString.endsWith("lm2")) || (paramString.endsWith("lm3")) || (paramString.endsWith("lm4"))) && 
          (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("rm1")) || (paramString.endsWith("rm2")) || (paramString.endsWith("rm3")) || (paramString.endsWith("rm4"))) && 
          (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("lo1")) || (paramString.endsWith("lo2"))) && 
          (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("ro1")) || (paramString.endsWith("ro2"))) && 
          (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxeng")) {
        i = paramString.charAt(5) - '1';
        debugprintln(this, "*** Engine" + i + " Hit..");
        if ((paramString.endsWith("case")) && 
          (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
          if (World.Rnd().nextFloat() < paramShot.power / 200000.0F) {
            this.FM.AS.setEngineStuck(paramShot.initiator, i);
            debugprintln(this, "*** Engine Crank Case Hit - Engine Stucks..");
          }
          if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
            this.FM.AS.hitEngine(paramShot.initiator, i, 2);
            debugprintln(this, "*** Engine Crank Case Hit - Engine Damaged..");
          }
          if (World.Rnd().nextFloat() < paramShot.power / 28000.0F) {
            this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, 1);
            debugprintln(this, "*** Engine Crank Case Hit - Cylinder Feed Out, " + this.FM.EI.engines[i].getCylindersOperable() + "/" + this.FM.EI.engines[i].getCylinders() + " Left..");
          }
          this.FM.EI.engines[i].setReadyness(paramShot.initiator, this.FM.EI.engines[i].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
          debugprintln(this, "*** Engine Crank Case Hit - Readyness Reduced to " + this.FM.EI.engines[i].getReadyness() + "..");
        }

        if ((paramString.endsWith("cyl1")) && 
          (getEnergyPastArmor(0.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[i].getCylindersRatio() * 1.75F)) {
          this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
          debugprintln(this, "*** Engine Cylinders Hit, " + this.FM.EI.engines[i].getCylindersOperable() + "/" + this.FM.EI.engines[i].getCylinders() + " Left..");
          if (this.FM.AS.astateEngineStates[i] < 1) {
            this.FM.AS.hitEngine(paramShot.initiator, i, 1);
          }
          if (World.Rnd().nextFloat() < paramShot.power / 24000.0F) {
            this.FM.AS.hitEngine(paramShot.initiator, i, 3);
            debugprintln(this, "*** Engine Cylinders Hit - Engine Fires..");
          }
          getEnergyPastArmor(25.0F, paramShot);
        }

        if ((paramString.endsWith("supc")) && 
          (getEnergyPastArmor(0.05F, paramShot) > 0.0F)) {
          this.FM.EI.engines[i].setKillCompressor(paramShot.initiator);
        }

        if ((paramString.endsWith("prop")) && 
          (getEnergyPastArmor(0.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
          this.FM.EI.engines[i].setKillPropAngleDevice(paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '0';
        switch (i) {
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
      if ((paramString.startsWith("xxw1")) || (paramString.startsWith("xxoil1"))) {
        if ((World.Rnd().nextFloat() < 0.12F) && (getEnergyPastArmor(2.25F, paramShot) > 0.0F)) {
          this.FM.AS.hitOil(paramShot.initiator, 0);
          debugprintln(this, "*** Oil Radiator L Hit..");
        }
        return;
      }
      if ((paramString.startsWith("xxw2")) || (paramString.startsWith("xxoil1"))) {
        if ((World.Rnd().nextFloat() < 0.12F) && (getEnergyPastArmor(2.25F, paramShot) > 0.0F)) {
          this.FM.AS.hitOil(paramShot.initiator, 1);
          debugprintln(this, "*** Oil Radiator R Hit..");
        }
        return;
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

        return;
      }
      if (paramString.startsWith("xxmgun0")) {
        i = paramString.charAt(7) - '1';
        if (getEnergyPastArmor(0.75F, paramShot) > 0.0F) {
          debuggunnery("Armament: Machine Gun (" + i + ") Disabled..");
          this.FM.AS.setJamBullets(0, i);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcannon0")) {
        i = paramString.charAt(9) - '1';
        if (getEnergyPastArmor(6.29F, paramShot) > 0.0F) {
          debuggunnery("Armament: Cannon (" + i + ") Disabled..");
          this.FM.AS.setJamBullets(1, i);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
        return;
      }
      return;
    }

    if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit"))) {
      hitChunk("CF", paramShot);
      if (paramPoint3d.x > 0.0D) {
        if (World.Rnd().nextFloat() < 0.1F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
        }
        if (paramPoint3d.z > 0.4D)
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
      }
    }
    else if (paramString.startsWith("xengine1")) {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xengine2")) {
      if (chunkDamageVisible("Engine2") < 2)
        hitChunk("Engine2", paramShot);
    }
    else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel")) {
      hitChunk("Keel1", paramShot);
    } else if (paramString.startsWith("xrudder")) {
      hitChunk("Rudder1", paramShot);
    } else if (paramString.startsWith("xstab")) {
      if (paramString.startsWith("xstabl")) {
        hitChunk("StabL", paramShot);
      }
      if (paramString.startsWith("xstabr"))
        hitChunk("StabR", paramShot);
    }
    else if (paramString.startsWith("xvator")) {
      if (paramString.startsWith("xvatorl")) {
        hitChunk("VatorL", paramShot);
      }
      if (paramString.startsWith("xvatorr"))
        hitChunk("VatorR", paramShot);
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

      if (paramString.startsWith("xwinglmid")) {
        if (chunkDamageVisible("WingLMid") < 3) {
          hitChunk("WingLMid", paramShot);
        }
        if (World.Rnd().nextFloat() < paramShot.mass + 0.02F) {
          this.FM.AS.hitOil(paramShot.initiator, 0);
        }
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
      if (paramString.startsWith("xaronel")) {
        hitChunk("AroneL", paramShot);
      }
      if (paramString.startsWith("xaroner"))
        hitChunk("AroneR", paramShot);
    }
    else if (paramString.startsWith("xgear")) {
      if (paramString.endsWith("1")) {
        if (World.Rnd().nextFloat() < 0.05F) {
          debuggunnery("Hydro System: Disabled..");
          this.FM.AS.setInternalDamage(paramShot.initiator, 0);
        }
      }
      else if ((World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 3.435F), paramShot) > 0.0F)) {
        debuggunnery("Undercarriage: Stuck..");
        this.FM.AS.setInternalDamage(paramShot.initiator, 3);
      }
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

  private final void doHitMeATank(Shot paramShot, int paramInt)
  {
    if (getEnergyPastArmor(0.2F, paramShot) > 0.0F)
      if (paramShot.power < 14100.0F) {
        if (this.FM.AS.astateTankStates[paramInt] == 0) {
          this.FM.AS.hitTank(paramShot.initiator, paramInt, 1);
          this.FM.AS.doSetTankState(paramShot.initiator, paramInt, 1);
        }
        if ((this.FM.AS.astateTankStates[paramInt] > 0) && ((World.Rnd().nextFloat() < 0.02F) || ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.25F))))
          this.FM.AS.hitTank(paramShot.initiator, paramInt, 2);
      }
      else {
        this.FM.AS.hitTank(paramShot.initiator, paramInt, World.Rnd().nextInt(0, (int)(paramShot.power / 56000.0F)));
      }
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 11:
    case 17:
    case 19:
      hierMesh().chunkVisible("Wire_D0", false);
      break;
    case 13:
      return false;
    case 12:
    case 14:
    case 15:
    case 16:
    case 18: } return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);

    float f = this.FM.EI.engines[0].getControlRadiator();
    if (Math.abs(this.kangle1 - f) > 0.01F) {
      this.kangle1 = f;
      hierMesh().chunkSetAngles("WaterL_D0", 0.0F, -45.0F * f, 0.0F);
    }
    f = this.FM.EI.engines[1].getControlRadiator();
    if (Math.abs(this.kangle2 - f) > 0.01F) {
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