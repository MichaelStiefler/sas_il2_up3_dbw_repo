package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public abstract class LA_X extends Scheme1
  implements TypeFighter
{
  protected void moveFlap(float paramFloat)
  {
    float f = -60.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap04_D0", 0.0F, f, 0.0F);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean) {
      if ((this.FM.AS.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.07F)) nextDMGLevel(this.FM.AS.astateEffectChunks[0] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.14F)) this.FM.AS.hitTank(this, 1, 1);
      if ((this.FM.AS.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.14F)) this.FM.AS.hitTank(this, 2, 1);
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.07F)) nextDMGLevel(this.FM.AS.astateEffectChunks[1] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.14F)) this.FM.AS.hitTank(this, 0, 1);
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.07F)) nextDMGLevel(this.FM.AS.astateEffectChunks[2] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.14F)) this.FM.AS.hitTank(this, 0, 1);
    }

    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = paramFloat + 0.24F * (float)Math.sin(paramFloat * 3.141592653589793D);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -85.0F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 112.0F * f, 0.0F);

    f = Math.min(paramFloat * 1.24F + 0.23F * (float)Math.sin(paramFloat * 1.24F * 3.141592653589793D), 1.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 85.0F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 112.0F * f, 0.0F);

    f = Math.max(-paramFloat * 1500.0F, -90.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC99_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);

    if (paramHierMesh.chunkFindCheck("GearL7_D0") != -1) {
      paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, f, 0.0F);
      paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, -f, 0.0F);
    }
  }
  protected void moveGear(float paramFloat) {
    moveGear(hierMesh(), paramFloat);
  }
  public void moveSteering(float paramFloat) { if (this.FM.CT.getGear() < 0.98F) return;
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, paramFloat, 0.0F); }

  public void moveWheelSink() {
    resetYPRmodifier();
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.22F, 0.0F, 0.2F);
    hierMesh().chunkSetLocate("GearL8_D0", xyz, ypr);
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.22F, 0.0F, 0.2F);
    hierMesh().chunkSetLocate("GearR8_D0", xyz, ypr);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 33:
      if (World.Rnd().nextFloat() < 0.233F) this.FM.AS.hitTank(this, 1, 6);
      return super.cutFM(34, paramInt2, paramActor);
    case 36:
      if (World.Rnd().nextFloat() < 0.233F) this.FM.AS.hitTank(this, 2, 6);
      return super.cutFM(37, paramInt2, paramActor);
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void update(float paramFloat)
  {
    if (this.FM.getSpeed() > 5.0F) {
      resetYPRmodifier();
      xyz[1] = cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, -0.05F);
      xyz[2] = cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, 0.025F);
      hierMesh().chunkSetLocate("SlatL_D0", xyz, ypr);
      hierMesh().chunkSetLocate("SlatR_D0", xyz, ypr);
    }
    super.update(paramFloat);
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
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
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(8.5F, paramShot);
        } else if (paramString.endsWith("g1")) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
          getEnergyPastArmor(18.150002F / (1.0E-005F + (float)Math.abs(v1.x)), paramShot);
        } else if (paramString.endsWith("g2")) {
          getEnergyPastArmor(21.780001F / (1.0E-005F + (float)Math.abs(v1.x)), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        if (Pd.x > -0.3549999892711639D) {
          if ((World.Rnd().nextFloat() < 0.1F) || (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
            if (World.Rnd().nextFloat() < 0.1F)
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          }
        }
        else if (Pd.x > -1.284999966621399D) {
          if (World.Rnd().nextFloat() < 0.2F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          }
          if (World.Rnd().nextFloat() < 0.2F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          }
          if (World.Rnd().nextFloat() < 0.2F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          }
        }
        else if (getEnergyPastArmor(0.31F, paramShot) > 0.0F) {
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
        }

        return;
      }
      if (paramString.startsWith("xxspar")) {
        if (((paramString.endsWith("li1")) || (paramString.endsWith("li2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(2.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("ri1")) || (paramString.endsWith("ri2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(2.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("lm1")) || (paramString.endsWith("lm2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(2.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("rm1")) || (paramString.endsWith("rm2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(2.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("lo1")) || (paramString.endsWith("lo2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("ro1")) || (paramString.endsWith("ro2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("sl1")) || (paramString.endsWith("sl2"))) && 
          (chunkDamageVisible("StabL") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** StabL Spars Damaged..");
          nextDMGLevels(1, 2, "StabL_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("sr1")) || (paramString.endsWith("sr2"))) && 
          (chunkDamageVisible("StabR") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** StabR Spars Damaged..");
          nextDMGLevels(1, 2, "StabR_D3", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxlock")) {
        if (paramString.endsWith("al")) {
          if (getEnergyPastArmor(0.35F, paramShot) > 0.0F) {
            debugprintln(this, "*** AroneL Lock Damaged..");
            nextDMGLevels(1, 2, "AroneL_D0", paramShot.initiator);
          }
        } else if (paramString.endsWith("ar")) {
          if (getEnergyPastArmor(0.35F, paramShot) > 0.0F) {
            debugprintln(this, "*** AroneR Lock Damaged..");
            nextDMGLevels(1, 2, "AroneR_D0", paramShot.initiator);
          }
        } else if ((paramString.endsWith("vl1")) || (paramString.endsWith("vl2"))) {
          if (getEnergyPastArmor(0.35F, paramShot) > 0.0F) {
            debugprintln(this, "*** VatorL Lock Damaged..");
            nextDMGLevels(1, 2, "VatorL_D0", paramShot.initiator);
          }
        } else if ((paramString.endsWith("vr1")) || (paramString.endsWith("vr2"))) {
          if (getEnergyPastArmor(0.35F, paramShot) > 0.0F) {
            debugprintln(this, "*** VatorR Lock Damaged..");
            nextDMGLevels(1, 2, "VatorR_D0", paramShot.initiator);
          }
        } else if (((paramString.endsWith("r1")) || (paramString.endsWith("r2"))) && 
          (getEnergyPastArmor(0.35F, paramShot) > 0.0F)) {
          debugprintln(this, "*** Rudder1 Lock Damaged..");
          nextDMGLevels(1, 2, "Rudder1_D0", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxeng1")) {
        if ((paramString.endsWith("prop")) && 
          (getEnergyPastArmor(0.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
          this.FM.EI.engines[0].setKillPropAngleDevice(paramShot.initiator);
        }

        if (paramString.endsWith("base")) {
          if (getEnergyPastArmor(0.2F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 140000.0F) {
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
              debugprintln(this, "*** Engine Crank Case Hit - Engine Stucks..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 85000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
              debugprintln(this, "*** Engine Crank Case Hit - Engine Damaged..");
            }
          }
          else if (World.Rnd().nextFloat() < 0.01F) {
            this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, 1);
          } else {
            this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - 0.002F);
            debugprintln(this, "*** Engine Crank Case Hit - Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
          }

          getEnergyPastArmor(12.0F, paramShot);
        }
        if (paramString.endsWith("cyls")) {
          if ((getEnergyPastArmor(6.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 0.75F)) {
            this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 19000.0F)));
            debugprintln(this, "*** Engine Cylinders Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 48000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
              debugprintln(this, "*** Engine Cylinders Hit - Engine Fires..");
            }
          }
          getEnergyPastArmor(25.0F, paramShot);
        }
        if (paramString.endsWith("supc")) {
          if (getEnergyPastArmor(0.05F, paramShot) > 0.0F) {
            this.FM.EI.engines[0].setKillCompressor(paramShot.initiator);
          }
          getEnergyPastArmor(2.0F, paramShot);
        }
        if (paramString.endsWith("eqpt")) {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F) {
            if ((Pd.y > 0.0D) && (Pd.z < 0.1889999955892563D) && (World.Rnd().nextFloat(0.0F, 16000.0F) < paramShot.power)) {
              this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, 0);
            }
            if ((Pd.y < 0.0D) && (Pd.z < 0.1889999955892563D) && (World.Rnd().nextFloat(0.0F, 16000.0F) < paramShot.power)) {
              this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, 1);
            }
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 4);
            }
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 0);
            }
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
            }
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
            }
          }
          getEnergyPastArmor(2.0F, paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxoil")) {
        this.FM.AS.hitOil(paramShot.initiator, 0);
        getEnergyPastArmor(0.1F, paramShot);
        return;
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F) && (paramShot.powerType == 3)) {
          this.FM.AS.hitTank(paramShot.initiator, i, 2);
        }
        return;
      }
      if (paramString.startsWith("xxshvak")) {
        i = paramString.charAt(7) - '1';
        this.FM.AS.setJamBullets(1, i);
        getEnergyPastArmor(12.0F, paramShot);
        return;
      }
      if (paramString.startsWith("xxpneu")) {
        this.FM.Gears.setHydroOperable(false);
        return;
      }
      return;
    }

    if ((paramString.startsWith("xcf")) || ((paramString.startsWith("xcockpit")) && (Pd.z > 0.7329999804496765D))) {
      hitChunk("CF", paramShot);
    }
    if (paramString.startsWith("xxcockpit")) {
      if (World.Rnd().nextFloat() < 0.1F) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
      }
      if (Pd.y < 0.0D) {
        if (World.Rnd().nextFloat() < 0.25F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
        }
        if (World.Rnd().nextFloat() < 0.25F)
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
      }
      else {
        if (World.Rnd().nextFloat() < 0.25F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
        }
        if (World.Rnd().nextFloat() < 0.25F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
        }
      }
      if ((Pd.z > 0.6389999985694885D) && (Pd.x < -1.041000008583069D))
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
    }
    else if (paramString.startsWith("xeng")) {
      hitChunk("Engine1", paramShot);
    } else if (paramString.startsWith("xtail")) {
      hitChunk("Tail1", paramShot);
    } else if (paramString.startsWith("xkeel")) {
      hitChunk("Keel1", paramShot);
    } else if (paramString.startsWith("xrudder")) {
      if (chunkDamageVisible("Rudder1") < 1)
        hitChunk("Rudder1", paramShot);
    }
    else if (paramString.startsWith("xstab")) {
      if ((paramString.startsWith("xstabl")) && 
        (chunkDamageVisible("StabL") < 2)) {
        hitChunk("StabL", paramShot);
      }

      if ((paramString.startsWith("xstabr")) && 
        (chunkDamageVisible("StabR") < 2)) {
        hitChunk("StabR", paramShot);
      }
    }
    else if (paramString.startsWith("xvator")) {
      if ((paramString.startsWith("xvatorl")) && 
        (chunkDamageVisible("VatorL") < 1)) {
        hitChunk("VatorL", paramShot);
      }

      if ((paramString.startsWith("xvatorr")) && 
        (chunkDamageVisible("VatorR") < 1)) {
        hitChunk("VatorR", paramShot);
      }
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

      if ((paramString.startsWith("xwinglmid")) && 
        (chunkDamageVisible("WingLMid") < 3)) {
        hitChunk("WingLMid", paramShot);
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
      if ((paramString.startsWith("xaronel")) && 
        (chunkDamageVisible("AroneL") < 1)) {
        hitChunk("AroneL", paramShot);
      }

      if ((paramString.startsWith("xaroner")) && 
        (chunkDamageVisible("AroneL") < 1)) {
        hitChunk("AroneR", paramShot);
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

  static
  {
    Class localClass = LA_X.class;
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);
  }
}