package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public abstract class P_36 extends Scheme1
  implements TypeFighter
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, -70.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, cvt(paramFloat, 0.02F, 0.17F, 0.0F, -60.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, cvt(paramFloat, 0.02F, 0.17F, 0.0F, -60.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL21_D0", 0.0F, 94.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -40.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, 100.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR21_D0", 0.0F, -94.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -40.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, 100.0F * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, paramFloat, 0.0F);
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      if (this.FM.AS.bIsAboutToBailout) break;
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
    }
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

  public void msgExplosion(Explosion paramExplosion)
  {
    setExplosion(paramExplosion);
    if (paramExplosion.chunkName != null) {
      if (paramExplosion.chunkName.startsWith("WingL")) {
        if (World.Rnd().nextFloat() < 0.02F) {
          this.FM.AS.setJamBullets(0, 2);
        }
        if (World.Rnd().nextFloat() < 0.02F) {
          this.FM.AS.setJamBullets(0, 3);
        }
      }
      if (paramExplosion.chunkName.startsWith("WingR")) {
        if (World.Rnd().nextFloat() < 0.02F) {
          this.FM.AS.setJamBullets(0, 4);
        }
        if (World.Rnd().nextFloat() < 0.02F) {
          this.FM.AS.setJamBullets(0, 5);
        }
      }
    }
    super.msgExplosion(paramExplosion);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    int j;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(15.0F / (1.0E-005F + (float)Math.abs(v1.x)), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        if (paramString.endsWith("1")) {
          if (World.Rnd().nextFloat() < 0.3F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
            debugprintln(this, "*** Engine Controls Out..");
          }
          if (World.Rnd().nextFloat() < 0.3F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
            debugprintln(this, "*** Engine Controls Out..");
          }
        } else if (paramString.endsWith("2")) {
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
            debugprintln(this, "*** Evelator Controls Out..");
          }
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 0);
            debugprintln(this, "*** Ailerones Controls Out..");
          }
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);
            debugprintln(this, "*** Rudder Controls Out..");
          }
          if (World.Rnd().nextFloat() < 0.3F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
            debugprintln(this, "*** Engine Controls Out..");
          }
          if (World.Rnd().nextFloat() < 0.3F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
            debugprintln(this, "*** Engine Controls Out..");
          }
        } else if ((paramString.endsWith("3")) && 
          (World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          debugprintln(this, "*** Rudder Controls Out..");
        }

        return;
      }
      if (paramString.startsWith("xxeng")) {
        if ((paramString.startsWith("xxengkart")) && 
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

        if ((paramString.startsWith("xxengcy")) && 
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

        return;
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if (i == 1)
          i = 2;
        else if (i == 2) {
          i = 1;
        }
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          this.FM.AS.hitTank(paramShot.initiator, i, 1);
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.11F)) {
            this.FM.AS.hitTank(paramShot.initiator, i, 2);
          }
        }
        return;
      }
      if (paramString.startsWith("xxmgun")) {
        i = 0; j = paramString.charAt(6) - '0';
        switch (j) { case 1:
          i = 2; break;
        case 2:
          i = 1; break;
        case 3:
          i = 3; break;
        case 4:
          i = 0; break;
        case 5:
          i = 4; break;
        case 6:
          i = 5;
        }
        this.FM.AS.setJamBullets(0, i);
        getEnergyPastArmor(25.1F, paramShot);
        return;
      }
      if (paramString.startsWith("xxammo")) {
        i = paramString.charAt(6) - '0';
        if (World.Rnd().nextFloat(0.0F, 2000.0F) < paramShot.power) {
          switch (i) {
          case 1:
            this.FM.AS.setJamBullets(0, 1);
            break;
          case 2:
            if (World.Rnd().nextFloat() < 0.5F)
              this.FM.AS.setJamBullets(0, 2);
            else {
              this.FM.AS.setJamBullets(0, 3);
            }
            break;
          case 3:
            this.FM.AS.setJamBullets(0, 0);
            break;
          case 4:
            if (World.Rnd().nextFloat() < 0.5F)
              this.FM.AS.setJamBullets(0, 4);
            else {
              this.FM.AS.setJamBullets(0, 5);
            }
          }
        }
        return;
      }
      if (paramString.startsWith("xxspar")) {
        if ((paramString.endsWith("li1")) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.endsWith("ri1")) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.endsWith("lm1")) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.endsWith("rm1")) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.endsWith("lo1")) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(4.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.endsWith("ro1")) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(4.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxpar")) {
        if (((paramString.endsWith("sl1")) || (paramString.endsWith("sl2"))) && 
          (chunkDamageVisible("StabL") > 1) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** StabL Spars Damaged..");
          nextDMGLevels(1, 2, "StabL_D2", paramShot.initiator);
        }

        if (((paramString.endsWith("sr1")) || (paramString.endsWith("sr2"))) && 
          (chunkDamageVisible("StabR") > 1) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** StabR Spars Damaged..");
          nextDMGLevels(1, 2, "StabR_D2", paramShot.initiator);
        }

        if (((paramString.endsWith("k1")) || (paramString.endsWith("k2"))) && 
          (chunkDamageVisible("Keel1") > 1) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** Keel1 Spars Damaged..");
          nextDMGLevels(1, 2, "Keel1_D2", paramShot.initiator);
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
        } else if ((paramString.endsWith("sl1")) || (paramString.endsWith("sl2"))) {
          if (getEnergyPastArmor(0.35F, paramShot) > 0.0F) {
            debugprintln(this, "*** VatorL Lock Damaged..");
            nextDMGLevels(1, 2, "VatorL_D0", paramShot.initiator);
          }
        } else if ((paramString.endsWith("sr1")) || (paramString.endsWith("sr2"))) {
          if (getEnergyPastArmor(0.35F, paramShot) > 0.0F) {
            debugprintln(this, "*** VatorR Lock Damaged..");
            nextDMGLevels(1, 2, "VatorR_D0", paramShot.initiator);
          }
        } else if (((paramString.endsWith("k1")) || (paramString.endsWith("k2"))) && 
          (getEnergyPastArmor(0.35F, paramShot) > 0.0F)) {
          debugprintln(this, "*** Rudder1 Lock Damaged..");
          nextDMGLevels(1, 2, "Rudder1_D0", paramShot.initiator);
        }

        return;
      }
      return;
    }

    if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit"))) {
      hitChunk("CF", paramShot);
    }
    if (!paramString.startsWith("xcockpit"))
    {
      if (paramString.startsWith("xeng")) {
        hitChunk("Engine1", paramShot);
      } else if (paramString.startsWith("xtail")) {
        hitChunk("Tail1", paramShot);
      } else if (paramString.startsWith("xkeel")) {
        hitChunk("Keel1", paramShot);
      } else if (paramString.startsWith("xrudder")) {
        hitChunk("Rudder1", paramShot);
      } else if (paramString.startsWith("xstabl")) {
        hitChunk("StabL", paramShot);
      } else if (paramString.startsWith("xstabr")) {
        hitChunk("StabR", paramShot);
      } else if (paramString.startsWith("xvatorl")) {
        hitChunk("VatorL", paramShot);
      } else if (paramString.startsWith("xvatorr")) {
        hitChunk("VatorR", paramShot);
      } else if (paramString.startsWith("xwing")) {
        if (paramString.startsWith("xwinglin")) {
          hitChunk("WingLIn", paramShot);
        }
        if (paramString.startsWith("xwingrin")) {
          hitChunk("WingRIn", paramShot);
        }
        if (paramString.startsWith("xwinglmid")) {
          hitChunk("WingLMid", paramShot);
        }
        if (paramString.startsWith("xwingrmid")) {
          hitChunk("WingRMid", paramShot);
        }
        if (paramString.startsWith("xwinglout")) {
          hitChunk("WingLOut", paramShot);
        }
        if (paramString.startsWith("xwingrout"))
          hitChunk("WingROut", paramShot);
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
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 11:
    case 33:
    case 34:
    case 35:
    case 36:
    case 37:
    case 38:
      hierMesh().chunkVisible("Wire_D0", false);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  static
  {
    Class localClass = P_36.class;
    Property.set(localClass, "originCountry", PaintScheme.countryUSA);
  }
}