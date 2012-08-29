package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public abstract class B6N extends Scheme1
{
  private float arrestor = 0.0F;
  private long tme = 0L;
  private float topGunnerPosition = 1.0F;
  private float curTopGunnerPosition = 1.0F;
  private int radPosNum = 1;

  private float flapps = 0.0F;

  protected void moveFlap(float paramFloat)
  {
    float f = -25.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean) {
      if ((this.FM.AS.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.08F)) nextDMGLevel(this.FM.AS.astateEffectChunks[0] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.08F)) nextDMGLevel(this.FM.AS.astateEffectChunks[1] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[2] > 4) && (World.Rnd().nextFloat() < 0.08F)) nextDMGLevel(this.FM.AS.astateEffectChunks[2] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[3] > 4) && (World.Rnd().nextFloat() < 0.08F)) nextDMGLevel(this.FM.AS.astateEffectChunks[3] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[0] > 5) && (World.Rnd().nextFloat() < 0.16F)) this.FM.AS.hitTank(this, 1, 1);
      if ((this.FM.AS.astateTankStates[1] > 5) && (World.Rnd().nextFloat() < 0.16F)) this.FM.AS.hitTank(this, 1, 0);
      if ((this.FM.AS.astateTankStates[2] > 5) && (World.Rnd().nextFloat() < 0.16F)) this.FM.AS.hitTank(this, 1, 3);
      if ((this.FM.AS.astateTankStates[3] > 5) && (World.Rnd().nextFloat() < 0.16F)) this.FM.AS.hitTank(this, 1, 2);
    }

    for (int i = 1; i < 5; i++)
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  private void setRadist(int paramInt)
  {
    this.radPosNum = paramInt;
    if (this.FM.AS.astatePilotStates[2] > 90) return;
    hierMesh().chunkVisible("HMask3_D0", false);
    hierMesh().chunkVisible("HMask4_D0", false);
    switch (paramInt) {
    case 0:
      this.topGunnerPosition = 0.0F;
      break;
    case 1:
      this.topGunnerPosition = 1.0F;
    }
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -48.0F) { f1 = -48.0F; bool = false; }
      if (f1 > 48.0F) { f1 = 48.0F; bool = false; }
      if (f2 < -7.0F) { f2 = -7.0F; bool = false; }
      if (f2 <= 65.0F) break; f2 = 65.0F; bool = false; break;
    case 1:
      if (f1 < -40.0F) { f1 = -40.0F; bool = false; }
      if (f1 > 40.0F) { f1 = 40.0F; bool = false; }
      if (f2 < -73.0F) { f2 = -73.0F; bool = false; }
      if (f2 <= 4.5F) break; f2 = 4.5F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt) {
    case 0:
      break;
    case 1:
      break;
    case 2:
    case 3:
      this.FM.turret[0].setHealth(paramFloat);
      this.FM.turret[1].setHealth(paramFloat);
    }
  }

  public void doMurderPilot(int paramInt) {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("Gore3_D0", true);
      break;
    case 2:
      if (hierMesh().isChunkVisible("Pilot3_D0")) {
        hierMesh().chunkVisible("Pilot3_D0", false);
        hierMesh().chunkVisible("Pilot3_D1", true);
        hierMesh().chunkVisible("HMask3_D0", false);
      } else {
        hierMesh().chunkVisible("Pilot4_D0", false);
        hierMesh().chunkVisible("Pilot4_D1", true);
        hierMesh().chunkVisible("HMask4_D0", false);
      }
      break;
    case 3:
    }
  }

  public void doRemoveBodyFromPlane(int paramInt)
  {
    super.doRemoveBodyFromPlane(paramInt);
    if (paramInt >= 3) {
      doRemoveBodyChunkFromPlane("Pilot4");
      doRemoveBodyChunkFromPlane("Head4");
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    int j;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxcontrols")) {
        if (((paramString.endsWith("1")) || (paramString.endsWith("2"))) && 
          (World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.35F, paramShot) > 0.0F)) {
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          debugprintln(this, "*** Aileron Controls Out..");
        }

        if ((paramString.endsWith("3")) && 
          (getEnergyPastArmor(0.2F, paramShot) > 0.0F)) {
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
          debugprintln(this, "*** Throttle Quadrant: Hit, Engine Controls Disabled..");
        }

        if ((paramString.endsWith("4")) && 
          (World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          debugprintln(this, "*** Evelator Controls Out..");
        }

        if ((paramString.endsWith("5")) && 
          (World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(2.2F, paramShot) > 0.0F)) {
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          debugprintln(this, "*** Rudder Controls Out..");
        }

        return;
      }
      if (paramString.startsWith("xxeng1")) {
        if (paramString.endsWith("case")) {
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
        if (paramString.startsWith("xxeng1mag")) {
          i = paramString.charAt(9) - '1';
          debuggunnery("Engine Module: Magneto " + i + " Destroyed..");
          this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, i);
        }
        if (paramString.endsWith("oil1")) {
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.25F, paramShot) > 0.0F))
            debuggunnery("Engine Module: Oil Radiator Hit..");
          this.FM.AS.hitOil(paramShot.initiator, 0);
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

        if ((paramString.startsWith("xxlockal")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: AroneL Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockar")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: AroneR Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxoil")) {
        if ((getEnergyPastArmor(0.25F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          this.FM.AS.hitOil(paramShot.initiator, 0);
          getEnergyPastArmor(0.22F, paramShot);
          debuggunnery("Engine Module: Oil Tank Pierced..");
        }
        return;
      }
      if (paramString.startsWith("xxspar")) {
        if ((paramString.endsWith("li1")) && 
          (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.endsWith("ri1")) && 
          (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.endsWith("lm1")) && 
          (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.endsWith("rm1")) && 
          (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.endsWith("lo1")) && 
          (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.endsWith("ro1")) && 
          (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
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

        if ((paramString.startsWith("xxspark")) && 
          (chunkDamageVisible("Keel1") > 1) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** Keel1 Spars Damaged..");
          nextDMGLevels(1, 2, "Keel1_D2", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '0';
        j = 0;
        switch (i) {
        case 1:
          j = World.Rnd().nextInt(0, 0);
          break;
        case 2:
          j = World.Rnd().nextInt(0, 1);
          break;
        case 3:
          j = World.Rnd().nextInt(1, 1);
          break;
        case 4:
          j = World.Rnd().nextInt(2, 2);
          break;
        case 5:
          j = World.Rnd().nextInt(2, 3);
          break;
        case 6:
          j = World.Rnd().nextInt(2, 3);
        }

        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          this.FM.AS.hitTank(paramShot.initiator, j, 1);
          if ((World.Rnd().nextFloat() < 0.05F) || ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.6F))) {
            this.FM.AS.hitTank(paramShot.initiator, j, 2);
          }
        }
        return;
      }
      return;
    }

    if (paramString.startsWith("xcf")) {
      hitChunk("CF", paramShot);
    }
    else if (!paramString.startsWith("xblister"))
    {
      if (paramString.startsWith("xeng")) {
        hitChunk("Engine1", paramShot);
      } else if (paramString.startsWith("xtail")) {
        hitChunk("Tail1", paramShot);
      } else if (paramString.startsWith("xkeel")) {
        if (chunkDamageVisible("Keel1") < 2)
          hitChunk("Keel1", paramShot);
      }
      else if (paramString.startsWith("xrudder")) {
        if (chunkDamageVisible("Rudder1") < 2)
          hitChunk("Rudder1", paramShot);
      }
      else if (paramString.startsWith("xstab")) {
        if (paramString.startsWith("xstabl")) {
          hitChunk("StabL", paramShot);
        }
        if (paramString.startsWith("xstabr"))
          hitChunk("StabR", paramShot);
      }
      else if (paramString.startsWith("xvator")) {
        if ((paramString.startsWith("xvatorl")) && 
          (chunkDamageVisible("VatorL") < 2)) {
          hitChunk("VatorL", paramShot);
        }

        if ((paramString.startsWith("xvatorr")) && 
          (chunkDamageVisible("VatorR") < 2)) {
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
      } else {
        if (paramString.startsWith("xArr"))
          return;
        if (paramString.startsWith("xgearc")) {
          hitChunk("GearC2", paramShot);
        } else if (paramString.startsWith("xgearl")) {
          hitChunk("GearL2", paramShot);
        } else if (paramString.startsWith("xgearr")) {
          hitChunk("GearR2", paramShot);
        } else if (paramString.startsWith("xturret1")) {
          if (getEnergyPastArmor(0.25F, paramShot) > 0.0F) {
            debuggunnery("Armament System: Turret Machine Gun(s): Disabled..");
            this.FM.AS.setJamBullets(10, 0);
            getEnergyPastArmor(World.Rnd().nextFloat(0.1F, 26.35F), paramShot);
          }
        } else if (paramString.startsWith("xturret1")) {
          if (getEnergyPastArmor(0.25F, paramShot) > 0.0F) {
            debuggunnery("Armament System: Turret Machine Gun(s): Disabled..");
            this.FM.AS.setJamBullets(11, 0);
            getEnergyPastArmor(World.Rnd().nextFloat(0.1F, 26.35F), paramShot);
          }
        } else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
          i = 0;

          if (paramString.endsWith("a")) {
            i = 1;
            j = paramString.charAt(6) - '1';
          } else if (paramString.endsWith("b")) {
            i = 2;
            j = paramString.charAt(6) - '1';
          } else {
            j = paramString.charAt(5) - '1';
          }
          if (j == 3) {
            j = 2;
          }
          hitFlesh(j, paramShot, i);
        }
      }
    }
  }

  protected void moveWingFold(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("WingLMid_D0", 0.0F, cvt(paramFloat, 0.01F, 0.99F, 0.0F, -135.0F), 0.0F);
    paramHierMesh.chunkSetAngles("WingRMid_D0", 0.0F, cvt(paramFloat, 0.01F, 0.99F, 0.0F, -135.0F), 0.0F);
  }
  public void moveWingFold(float paramFloat) {
    moveWingFold(hierMesh(), paramFloat);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 19:
      this.FM.CT.bHasArrestorControl = false;
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, cvt(paramFloat, 0.1F, 0.7F, 0.0F, -83.5F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, cvt(paramFloat, 0.1F, 0.15F, 0.0F, -88.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, cvt(paramFloat, 0.1F, 0.7F, 0.0F, -56.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, cvt(paramFloat, 0.1F, 0.7F, 0.0F, -180.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, cvt(paramFloat, 0.3F, 0.9F, 0.0F, -83.5F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, cvt(paramFloat, 0.3F, 0.35F, 0.0F, -88.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, cvt(paramFloat, 0.3F, 0.9F, 0.0F, -56.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, cvt(paramFloat, 0.3F, 0.9F, 0.0F, -180.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
  }
  public void moveArrestorHook(float paramFloat) {
    hierMesh().chunkSetAngles("Hook1_D0", 0.0F, -56.0F * paramFloat, 0.0F);
  }

  public void moveWheelSink()
  {
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);

    if (Time.current() > this.tme) {
      this.tme = (Time.current() + World.Rnd().nextLong(1000L, 5000L));
      if (this.FM.turret.length != 0)
      {
        byte tmp97_96 = (byte)Math.max(this.FM.AS.astatePilotStates[2], this.FM.AS.astatePilotStates[3]); this.FM.AS.astatePilotStates[3] = tmp97_96; this.FM.AS.astatePilotStates[2] = tmp97_96;
        Actor localActor = null;
        for (int j = 0; j < 2; j++) {
          if (this.FM.turret[j].bIsOperable) {
            localActor = this.FM.turret[j].target;
          }
        }
        for (j = 1; j < 2; j++) {
          this.FM.turret[j].target = localActor;
        }
        if ((localActor != null) && 
          (Actor.isValid(localActor))) {
          this.pos.getAbs(tmpLoc2); localActor.pos.getAbs(tmpLoc3); tmpLoc2.transformInv(tmpLoc3.getPoint());
          if (tmpLoc3.getPoint().z > 0.0D)
            setRadist(1);
          else {
            setRadist(0);
          }
        }
      }
    }

    if (this.FM.AS.astatePilotStates[2] < 90) {
      if (this.topGunnerPosition > 0.5F) {
        this.curTopGunnerPosition += 0.1F * paramFloat;
        if (this.curTopGunnerPosition > 1.0F)
          this.curTopGunnerPosition = 1.0F;
      }
      else {
        this.curTopGunnerPosition -= 0.1F * paramFloat;
        if (this.curTopGunnerPosition < 0.0F) {
          this.curTopGunnerPosition = 0.0F;
        }
      }
      if ((this.curTopGunnerPosition <= 0.1F) || 
        (this.curTopGunnerPosition < 0.9F));
      this.FM.turret[0].bIsOperable = true;
      this.FM.turret[1].bIsOperable = true;
    }

    hierMesh().chunkVisible("Turret2B_D0", this.curTopGunnerPosition <= 0.01F);
    hierMesh().chunkVisible("Gun_D0", this.curTopGunnerPosition > 0.01F);
    resetYPRmodifier();
    xyz[0] = cvt(this.curTopGunnerPosition, 0.0F, 0.15F, 0.43105F, 0.0F);
    xyz[1] = cvt(this.curTopGunnerPosition, 0.0F, 0.15F, 0.396F, 0.0F);
    xyz[2] = cvt(this.curTopGunnerPosition, 0.0F, 0.15F, -0.7906F, 0.0F);
    hierMesh().chunkSetLocate("Gun_D0", xyz, ypr);

    hierMesh().chunkSetAngles("Blister4_D0", 0.0F, cvt(this.curTopGunnerPosition, 0.25F, 0.35F, -30.0F, 0.0F), 0.0F);
    hierMesh().chunkSetAngles("Blister5_D0", 0.0F, cvt(this.curTopGunnerPosition, 0.25F, 0.35F, -180.0F, 0.0F), 0.0F);

    hierMesh().chunkSetAngles("Blister3_D0", 0.0F, cvt(this.curTopGunnerPosition, 0.4F, 0.5F, -32.0F, 0.0F), 0.0F);
    hierMesh().chunkSetAngles("Blister6_D0", 0.0F, cvt(this.curTopGunnerPosition, 0.4F, 0.5F, -40.0F, 0.0F), 0.0F);

    if (this.FM.AS.astatePilotStates[2] < 90) {
      hierMesh().chunkVisible("Pilot4_D0", this.curTopGunnerPosition <= 0.75F);
      resetYPRmodifier();
      xyz[1] = cvt(this.curTopGunnerPosition, 0.5F, 0.75F, 0.0F, -0.45715F);
      xyz[2] = cvt(this.curTopGunnerPosition, 0.5F, 0.75F, 0.0F, 0.239F);
      ypr[2] = cvt(this.curTopGunnerPosition, 0.5F, 0.75F, 0.0F, -40.0F);
      hierMesh().chunkSetLocate("Pilot4_D0", xyz, ypr);
    }

    if (this.FM.AS.astatePilotStates[2] < 90) {
      hierMesh().chunkVisible("Pilot3_D0", this.curTopGunnerPosition > 0.75F);
      resetYPRmodifier();
      xyz[1] = cvt(this.curTopGunnerPosition, 0.75F, 1.0F, 0.0443F, 0.0F);
      xyz[2] = cvt(this.curTopGunnerPosition, 0.75F, 1.0F, -0.1485F, 0.0F);
      ypr[2] = cvt(this.curTopGunnerPosition, 0.75F, 1.0F, -45.0F, 0.0F);
      hierMesh().chunkSetLocate("Pilot3_D0", xyz, ypr);
    }

    if (this.FM.Gears.arrestorVAngle != 0.0F) {
      f = cvt(this.FM.Gears.arrestorVAngle, -51.0F, 5.0F, 1.0F, 0.0F);
      this.arrestor = (0.8F * this.arrestor + 0.2F * f);
    } else {
      f = -51.0F * this.FM.Gears.arrestorVSink / 56.0F;
      if ((f < 0.0F) && (this.FM.getSpeedKMH() > 60.0F)) {
        Eff3DActor.New(this, this.FM.Gears.arrestorHook, null, 1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
      }
      if (f > 0.15F) {
        f = 0.15F;
      }
      this.arrestor = (0.3F * this.arrestor + 0.7F * (this.arrestor + f));
      if (this.arrestor < 0.0F) {
        this.arrestor = 0.0F;
      }
    }
    if (this.arrestor > this.FM.CT.getArrestor()) {
      this.arrestor = this.FM.CT.getArrestor();
    }
    moveArrestorHook(this.arrestor);

    float f = this.FM.EI.engines[0].getControlRadiator();
    if (Math.abs(this.flapps - f) > 0.02F) {
      this.flapps = f;
      for (int i = 1; i < 11; i++) {
        hierMesh().chunkSetAngles("Cowflap" + i + "_D0", 0.0F, -22.0F * f, 0.0F);
      }
    }

    if (this.FM.AS.astateBailoutStep > 1)
      hierMesh().chunkVisible("Turret2B_D0", hierMesh().isChunkVisible("Blister5_D0"));
  }

  static
  {
    Class localClass = CLASS.THIS();
    Property.set(localClass, "originCountry", PaintScheme.countryJapan);
  }
}