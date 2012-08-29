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
import com.maddox.rts.Property;

public abstract class Hurricane extends Scheme1
  implements TypeFighter, TypeTNBFighter
{
  private float kangle = 0.0F;

  public void update(float paramFloat) { hierMesh().chunkSetAngles("Water1_D0", 0.0F, -22.5F * this.kangle, 0.0F);
    this.kangle = (0.95F * this.kangle + 0.05F * this.FM.EI.engines[0].getControlRadiator());
    super.update(paramFloat);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL8_D0", 0.0F, -26.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -100.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -152.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR8_D0", 0.0F, -26.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -100.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -152.0F * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC2_D0", paramFloat, 0.0F, 0.0F);
  }
  public void moveWheelSink() {
    resetYPRmodifier();
    xyz[2] = (-cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.25F, 0.0F, 0.25F));
    hierMesh().chunkSetLocate("GearL10_D0", xyz, ypr);
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.25F, 0.0F, 0.25F);
    hierMesh().chunkSetLocate("GearR10_D0", xyz, ypr);
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      if (this.FM.AS.bIsAboutToBailout) break;
      if (hierMesh().isChunkVisible("Blister1_D0")) {
        hierMesh().chunkVisible("Gore1_D0", true);
      }
      hierMesh().chunkVisible("Gore2_D0", true);
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("p1"))
          getEnergyPastArmor(0.78F, paramShot);
        else if ((paramString.endsWith("g1")) || (paramString.endsWith("g2"))) {
          getEnergyPastArmor(8.0F / (1.0E-005F + (float)Math.abs(v1.x)), paramShot);
        }
      }
      if (paramString.startsWith("xxcontrols")) {
        if (paramString.endsWith("1")) {
          if (World.Rnd().nextFloat() < 0.12F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
            debugprintln(this, "*** Evelator Controls Out..");
          }
          if (World.Rnd().nextFloat() < 0.12F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);
            debugprintln(this, "*** Rudder Controls Out..");
          }
          if (World.Rnd().nextFloat() < 0.12F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 0);
            debugprintln(this, "*** Arone Controls Out..");
          }
        } else if (paramString.endsWith("2")) {
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
            debugprintln(this, "*** Evelator Controls Out..");
          }
        } else if ((paramString.endsWith("3")) && 
          (World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          debugprintln(this, "*** Rudder Controls Out..");
        }
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

        if (((paramString.endsWith("sl1")) || (paramString.endsWith("sl2")) || (paramString.endsWith("sl3")) || (paramString.endsWith("sl4")) || (paramString.endsWith("sl5"))) && 
          (chunkDamageVisible("StabL") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** StabL Spars Damaged..");
          nextDMGLevels(1, 2, "StabL_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("sr1")) || (paramString.endsWith("sr2")) || (paramString.endsWith("sr3")) || (paramString.endsWith("sr4")) || (paramString.endsWith("sr5"))) && 
          (chunkDamageVisible("StabR") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** StabR Spars Damaged..");
          nextDMGLevels(1, 2, "StabR_D3", paramShot.initiator);
        }

        if (paramString.endsWith("e1")) {
          getEnergyPastArmor(6.0F, paramShot);
        }
      }

      if (paramString.startsWith("xxeng1")) {
        if ((paramString.endsWith("prp")) && 
          (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
          this.FM.EI.engines[0].setKillPropAngleDevice(paramShot.initiator);
        }

        if ((paramString.endsWith("cas")) && 
          (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
          if (World.Rnd().nextFloat() < paramShot.power / 200000.0F) {
            this.FM.AS.setEngineStuck(paramShot.initiator, 0);
            debugprintln(this, "*** Engine Crank Case Hit - Engine Stucks..");
          }
          if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
            debugprintln(this, "*** Engine Crank Case Hit - Engine Damaged..");
          }
          if (World.Rnd().nextFloat() < paramShot.power / 28000.0F) {
            this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, 1);
            debugprintln(this, "*** Engine Crank Case Hit - Cylinder Feed Out, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");
          }
          this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
          debugprintln(this, "*** Engine Crank Case Hit - Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
        }

        if ((paramString.endsWith("cyl")) && 
          (getEnergyPastArmor(0.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 1.75F)) {
          this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
          debugprintln(this, "*** Engine Cylinders Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");
          if (this.FM.AS.astateEngineStates[0] < 1) {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
          }
          if (World.Rnd().nextFloat() < paramShot.power / 24000.0F) {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 3);
            debugprintln(this, "*** Engine Cylinders Hit - Engine Fires..");
          }
          getEnergyPastArmor(25.0F, paramShot);
        }

        if ((paramString.endsWith("sup")) && 
          (getEnergyPastArmor(0.05F, paramShot) > 0.0F)) {
          this.FM.EI.engines[0].setKillCompressor(paramShot.initiator);
        }
      }

      if (paramString.startsWith("xxoil")) {
        this.FM.AS.hitOil(paramShot.initiator, 0);
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          this.FM.AS.hitTank(paramShot.initiator, i, 1);
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.11F)) {
            this.FM.AS.hitTank(paramShot.initiator, i, 2);
          }
        }
      }

      if (paramString.startsWith("xxmgunl4")) {
        this.FM.AS.setJamBullets(0, World.Rnd().nextInt(0, 3) * 2 + 1 - 1);
      }
      if (paramString.startsWith("xxmgunr4")) {
        this.FM.AS.setJamBullets(0, World.Rnd().nextInt(1, 4) * 2 - 1);
      }
      if (paramString.startsWith("xxmgunl2")) {
        this.FM.AS.setJamBullets(0, World.Rnd().nextInt(0, 1) * 2 + 9 - 1);
      }
      if (paramString.startsWith("xxmgunr2")) {
        this.FM.AS.setJamBullets(0, World.Rnd().nextInt(5, 6) * 2 - 1);
      }
      if ((paramString.startsWith("xxammol4")) && 
        (paramShot.power > 27000.0F) && (World.Rnd().nextFloat() < 0.1F)) {
        this.FM.AS.setJamBullets(0, 0);
        this.FM.AS.setJamBullets(0, 2);
        this.FM.AS.setJamBullets(0, 4);
        this.FM.AS.setJamBullets(0, 6);
      }

      if ((paramString.startsWith("xxammor4")) && 
        (paramShot.power > 27000.0F) && (World.Rnd().nextFloat() < 0.1F)) {
        this.FM.AS.setJamBullets(0, 1);
        this.FM.AS.setJamBullets(0, 3);
        this.FM.AS.setJamBullets(0, 5);
        this.FM.AS.setJamBullets(0, 7);
      }

      if ((paramString.startsWith("xxammol2")) && 
        (paramShot.power > 27000.0F) && (World.Rnd().nextFloat() < 0.1F)) {
        this.FM.AS.setJamBullets(0, 8);
        this.FM.AS.setJamBullets(0, 10);
      }

      if ((paramString.startsWith("xxammor2")) && 
        (paramShot.power > 27000.0F) && (World.Rnd().nextFloat() < 0.1F)) {
        this.FM.AS.setJamBullets(0, 9);
        this.FM.AS.setJamBullets(0, 11);
      }

      if (paramString.startsWith("xxhispa1")) {
        this.FM.AS.setJamBullets(0, 0);
      }
      if (paramString.startsWith("xxhispa2")) {
        this.FM.AS.setJamBullets(0, 1);
      }
      if (paramString.startsWith("xxhispa3")) {
        this.FM.AS.setJamBullets(0, 2);
      }
      if (paramString.startsWith("xxhispa4")) {
        this.FM.AS.setJamBullets(0, 3);
      }
      if (paramString.startsWith("xxshvak1")) {
        this.FM.AS.setJamBullets(1, 0);
      }
      if (paramString.startsWith("xxshvak2")) {
        this.FM.AS.setJamBullets(1, 1);
      }
      if (paramString.startsWith("xxshkas1")) {
        this.FM.AS.setJamBullets(0, 0);
      }
      if (paramString.startsWith("xxshkas2")) {
        this.FM.AS.setJamBullets(0, 1);
      }

      return;
    }

    if ((paramString.startsWith("xcf")) || (paramString.startsWith("xoil"))) {
      hitChunk("CF", paramShot);
      if (paramPoint3d.x > -2.200000047683716D) {
        if (World.Rnd().nextFloat() < 0.1F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
        }
        if ((v1.x < -0.800000011920929D) && (World.Rnd().nextFloat() < 0.2F)) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
        }
        if ((v1.x < -0.8999999761581421D) && (World.Rnd().nextFloat() < 0.2F)) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
        }
        if (Math.abs(v1.x) < 0.800000011920929D) {
          if (paramPoint3d.y > 0.0D) {
            if (World.Rnd().nextFloat() < 0.1F) {
              this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
            }
            if (World.Rnd().nextFloat() < 0.1F)
              this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
          }
          else {
            if (World.Rnd().nextFloat() < 0.1F) {
              this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
            }
            if (World.Rnd().nextFloat() < 0.1F)
              this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
          }
        }
      }
    }
    else if (paramString.startsWith("xeng")) {
      if (chunkDamageVisible("Engine1") < 3)
        hitChunk("Engine1", paramShot);
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
      if ((paramString.startsWith("xstabl")) && 
        (chunkDamageVisible("StabL") < 3)) {
        hitChunk("StabL", paramShot);
      }

      if ((paramString.startsWith("xstabr")) && 
        (chunkDamageVisible("StabR") < 3)) {
        hitChunk("StabR", paramShot);
      }
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

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 11:
      hierMesh().chunkVisible("Wire_D0", false);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  static
  {
    Class localClass = Hurricane.class;
    Property.set(localClass, "originCountry", PaintScheme.countryBritain);
  }
}