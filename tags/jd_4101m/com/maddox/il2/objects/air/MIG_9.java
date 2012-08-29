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
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public abstract class MIG_9 extends Scheme2
  implements TypeFighter, TypeBNZFighter
{
  public static boolean bChangedPit = false;

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers()) bChangedPit = true; 
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor) {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers()) bChangedPit = true;
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -45.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);

    if (this.FM.AS.isMaster()) {
      float f = this.FM.EI.engines[0].getThrustOutput() * this.FM.EI.engines[0].getControlThrottle();
      if ((this.FM.EI.engines[0].getStage() == 6) && (f > 0.75F)) {
        if (f > 0.92F)
          this.FM.AS.setSootState(this, 0, 3);
        else
          this.FM.AS.setSootState(this, 0, 2);
      }
      else {
        this.FM.AS.setSootState(this, 0, 0);
      }
      f = this.FM.EI.engines[1].getThrustOutput() * this.FM.EI.engines[1].getControlThrottle();
      if ((this.FM.EI.engines[1].getStage() == 6) && (f > 0.75F)) {
        if (f > 0.92F)
          this.FM.AS.setSootState(this, 1, 3);
        else
          this.FM.AS.setSootState(this, 1, 2);
      }
      else
        this.FM.AS.setSootState(this, 1, 0);
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, cvt(paramFloat, 0.05F, 0.75F, 0.0F, -70.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC8_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, cvt(paramFloat, 0.05F, 0.75F, 0.0F, -95.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC6_D0", 0.0F, cvt(paramFloat, 0.05F, 0.15F, 0.0F, -90.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC7_D0", 0.0F, cvt(paramFloat, 0.05F, 0.15F, 0.0F, -90.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, cvt(paramFloat, 0.02F, 0.12F, 0.0F, -90.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, cvt(paramFloat, 0.05F, 0.75F, 0.0F, -87.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, cvt(paramFloat, 0.22F, 0.32F, 0.0F, -90.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, cvt(paramFloat, 0.25F, 0.95F, 0.0F, -87.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC8_D0", 0.0F, -cvt(paramFloat, -40.0F, 40.0F, -40.0F, 40.0F), 0.0F);
  }
  public void moveWheelSink() {
    float f = cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.11295F, 0.0F, -25.0F);
    hierMesh().chunkSetAngles("GearC4_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, f, 0.0F);

    f = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.1293F, 0.0F, -26.0F);
    hierMesh().chunkSetAngles("GearL3_D0", 0.0F, f, 0.0F);
    f = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.1293F, 0.0F, -30.0F);
    hierMesh().chunkSetAngles("GearL5_D0", 0.0F, f, 0.0F);
    f = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.1293F, 0.0F, -4.0F);
    hierMesh().chunkSetAngles("GearL8_D0", 0.0F, f, 0.0F);

    f = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.1293F, 0.0F, -26.0F);
    hierMesh().chunkSetAngles("GearR3_D0", 0.0F, f, 0.0F);
    f = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.1293F, 0.0F, -30.0F);
    hierMesh().chunkSetAngles("GearR5_D0", 0.0F, f, 0.0F);
    f = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.1293F, 0.0F, -4.0F);
    hierMesh().chunkSetAngles("GearR8_D0", 0.0F, f, 0.0F);
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i = 0;

    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxammo")) {
        if ((World.Rnd().nextFloat(0.0F, 20000.0F) < paramShot.power) && (World.Rnd().nextFloat() < 0.25F)) {
          this.FM.AS.setJamBullets(0, World.Rnd().nextInt(0, 1));
        }
        getEnergyPastArmor(11.4F, paramShot);
        return;
      }

      if (paramString.startsWith("xxarmor")) {
        debuggunnery("Armor: Hit..");
        if (paramString.startsWith("xxarmorg")) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
          getEnergyPastArmor(56.259998321533203D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
          if (paramShot.power <= 0.0F) {
            if (Math.abs(v1.x) > 0.8659999966621399D)
              doRicochet(paramShot);
            else {
              doRicochetBack(paramShot);
            }
          }
        }
        if (paramString.startsWith("xxarmorp")) {
          getEnergyPastArmor(12.880000114440918D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcannon")) {
        if (paramString.endsWith("01")) {
          debuggunnery("Armament System: Left Cannon: Disabled..");
          this.FM.AS.setJamBullets(0, 0);
        }
        if (paramString.endsWith("02")) {
          debuggunnery("Armament System: Right Cannon: Disabled..");
          this.FM.AS.setJamBullets(0, 1);
        }
        if (paramString.endsWith("03")) {
          debuggunnery("Armament System: Central Cannon: Disabled..");
          this.FM.AS.setJamBullets(1, 0);
        }
        getEnergyPastArmor(World.Rnd().nextFloat(6.98F, 24.35F), paramShot);
        return;
      }

      if (paramString.startsWith("xxcontrols")) {
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
        case 4:
          if (getEnergyPastArmor(4.1F, paramShot) <= 0.0F) break;
          debugprintln(this, "*** Aileron Controls Crank: Disabled..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 2:
        case 3:
          if ((getEnergyPastArmor(2.1F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.25F)) break;
          debugprintln(this, "*** Aileron Controls: Disabled..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 5:
        case 6:
          if (getEnergyPastArmor(0.3F, paramShot) <= 0.0F) break;
          debugprintln(this, "*** Rudder Controls: Disabled / Strings Broken..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2); break;
        case 7:
          if (getEnergyPastArmor(3.2F, paramShot) <= 0.0F) break;
          debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 8:
          if (getEnergyPastArmor(0.1F, paramShot) <= 0.0F) break;
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
          if (World.Rnd().nextFloat() < 0.5F)
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          else {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 1);
          }
          debugprintln(this, "*** Throttle Quadrant: Hit, Engine Controls Disabled..");
        }

        return;
      }

      if (paramString.startsWith("xxEng")) {
        i = paramString.charAt(5) - '1';
        if (paramPoint3d.x > 1.203D) {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F) {
            debugprintln(this, "*** Engine Module(s): Supercharger Disabled..");
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 0);
          }
        } else {
          if (getEnergyPastArmor(3.2F, paramShot) > 0.0F) {
            this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, 1);
          }
          if ((World.Rnd().nextFloat(0.009F, 0.1357F) < paramShot.mass) && (this.FM.EI.engines[1].getStage() == 6)) {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
          }
          getEnergyPastArmor(14.296F, paramShot);
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

      }

      if (paramString.startsWith("xxspar")) {
        debuggunnery("Spar Construction: Hit..");
        if ((paramString.startsWith("xxsparli")) && 
          (chunkDamageVisible("WingLIn") > 2) && (World.Rnd().nextFloat() > Math.abs(v1.x) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLIn Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparri")) && 
          (chunkDamageVisible("WingRIn") > 2) && (World.Rnd().nextFloat() > Math.abs(v1.x) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingRIn Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlm")) && 
          (chunkDamageVisible("WingLMid") > 2) && (World.Rnd().nextFloat() > Math.abs(v1.x) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLMid Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparrm")) && 
          (chunkDamageVisible("WingRMid") > 2) && (World.Rnd().nextFloat() > Math.abs(v1.x) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingRMid Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlo")) && 
          (chunkDamageVisible("WingLOut") > 2) && (World.Rnd().nextFloat() > Math.abs(v1.x) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLOut Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparro")) && 
          (chunkDamageVisible("WingROut") > 2) && (World.Rnd().nextFloat() > Math.abs(v1.x) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingROut Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxspart")) && 
          (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.86F / (float)Math.sqrt(v1.y * v1.y + v1.z * v1.z), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          debuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }

      }

      if (paramString.startsWith("xxTank")) {
        i = paramString.charAt(6) - '0';
        switch (i) {
        case 1:
          doHitMeATank(paramShot, 0);
          break;
        case 2:
          doHitMeATank(paramShot, 1);
          break;
        case 3:
          doHitMeATank(paramShot, 2);
          break;
        case 4:
          doHitMeATank(paramShot, 3);
          break;
        case 5:
          doHitMeATank(paramShot, 2);
          break;
        case 6:
          doHitMeATank(paramShot, 3);
          break;
        case 7:
          doHitMeATank(paramShot, 2);
          break;
        case 8:
          doHitMeATank(paramShot, 3);
        }

        return;
      }

      return;
    }

    if (paramString.startsWith("xcockpit")) {
      if (paramPoint3d.z > 0.5D) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
      }
      else if (paramPoint3d.y > 0.0D)
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
      else {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
      }

      if (World.Rnd().nextFloat() < 0.067F) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
      }
    }
    if (paramString.startsWith("xcf")) {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
    }
    else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel")) {
      hitChunk("Keel1", paramShot);
    } else if (paramString.startsWith("xNose")) {
      hitChunk("Nose1", paramShot);
    } else if (paramString.startsWith("xrudder")) {
      if (chunkDamageVisible("Rudder1") < 1)
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
        (chunkDamageVisible("AroneR") < 1)) {
        hitChunk("AroneR", paramShot);
      }
    }
    else if (paramString.startsWith("xgear")) {
      if ((paramString.endsWith("1")) && 
        (World.Rnd().nextFloat() < 0.05F)) {
        debuggunnery("Hydro System: Disabled..");
        this.FM.AS.setInternalDamage(paramShot.initiator, 0);
      }

      if (((paramString.endsWith("2a")) || (paramString.endsWith("2b"))) && 
        (World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 3.435F), paramShot) > 0.0F)) {
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

  private final void doHitMeATank(Shot paramShot, int paramInt) {
    if (getEnergyPastArmor(0.2F, paramShot) > 0.0F)
      if (paramShot.power < 14100.0F) {
        if (this.FM.AS.astateTankStates[paramInt] == 0) {
          this.FM.AS.hitTank(paramShot.initiator, paramInt, 1);
          this.FM.AS.doSetTankState(paramShot.initiator, paramInt, 1);
        }
        if ((this.FM.AS.astateTankStates[paramInt] > 0) && ((World.Rnd().nextFloat() < 0.02F) || ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.25F))))
          this.FM.AS.hitTank(paramShot.initiator, paramInt, 1);
      }
      else {
        this.FM.AS.hitTank(paramShot.initiator, paramInt, World.Rnd().nextInt(0, (int)(paramShot.power / 58899.0F)));
      }
  }

  public void moveCockpitDoor(float paramFloat)
  {
    resetYPRmodifier();
    if (paramFloat < 0.1F)
      xyz[1] = cvt(paramFloat, 0.01F, 0.08F, 0.0F, -0.1F);
    else {
      xyz[1] = cvt(paramFloat, 0.17F, 0.99F, -0.1F, -0.6F);
    }
    hierMesh().chunkSetLocate("Blister1_D0", xyz, ypr);
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null)) Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      setDoorSnd(paramFloat);
    }
  }

  static
  {
    Class localClass = CLASS.THIS();
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);
  }
}