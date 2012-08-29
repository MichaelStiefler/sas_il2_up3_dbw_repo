package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public abstract class MIG_3 extends Scheme1
  implements TypeFighter
{
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
    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor")) {
        debugprintln(this, "*** Armor: Hit..");
        if (paramString.endsWith("p1"))
          getEnergyPastArmor(9.100000381469727D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        else if (paramString.endsWith("p2"))
          getEnergyPastArmor(9.100000381469727D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        else if (paramString.endsWith("p3"))
          getEnergyPastArmor(5.6F, paramShot);
        else if (paramString.endsWith("t3"))
          getEnergyPastArmor(6.199999809265137D / (Math.abs(v1.z) + 9.999999747378752E-006D), paramShot);
        else if (paramString.endsWith("t4")) {
          getEnergyPastArmor(6.199999809265137D / (Math.abs(v1.z) + 9.999999747378752E-006D), paramShot);
        }
        return;
      }

      if (paramString.startsWith("xxcontrols")) {
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
          if (getEnergyPastArmor(2.2F, paramShot) <= 0.0F) break;
          debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 2:
        case 3:
          if (getEnergyPastArmor(0.3F, paramShot) <= 0.0F) break;
          debugprintln(this, "*** Rudder Controls: Disabled / Strings Broken..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2); break;
        case 4:
          if (getEnergyPastArmor(9.6F, paramShot) <= 0.0F) break;
          debugprintln(this, "*** Elevator Controls: Disabled..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 1); break;
        case 5:
        case 7:
          if ((getEnergyPastArmor(2.1F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.5F)) break;
          debugprintln(this, "*** Aileron Controls: Disabled..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 6:
        case 8:
          if (getEnergyPastArmor(1.45F, paramShot) <= 0.0F) break;
          debugprintln(this, "*** Aileron Controls: Control Crank Link Destroyed..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
        }

      }

      if (paramString.startsWith("xxspar")) {
        debugprintln(this, "*** Spar Construction: Hit..");
        if ((paramString.startsWith("xxsparlm")) && 
          (World.Rnd().nextFloat(0.0F, 0.115F) < paramShot.mass) && (getEnergyPastArmor(6.8F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLMid Spar Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D" + chunkDamageVisible("WingLMid"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparrm")) && 
          (World.Rnd().nextFloat(0.0F, 0.115F) < paramShot.mass) && (getEnergyPastArmor(6.8F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRMid Spar Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D" + chunkDamageVisible("WingRMid"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlo")) && 
          (World.Rnd().nextFloat(0.0F, 0.115F) < paramShot.mass) && (getEnergyPastArmor(6.8F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLOut Spar Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D" + chunkDamageVisible("WingLOut"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparro")) && 
          (World.Rnd().nextFloat(0.0F, 0.115F) < paramShot.mass) && (getEnergyPastArmor(6.8F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingROut Spar Damaged..");
          nextDMGLevels(1, 2, "WingROut_D" + chunkDamageVisible("WingROut"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparsl")) && 
          (World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(6.8F * World.Rnd().nextFloat(1.0F, 1.5F) / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debugprintln(this, "*** StabL Spars Damaged..");
          nextDMGLevels(1, 2, "StabL_D" + chunkDamageVisible("StabL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparsr")) && 
          (World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(6.8F * World.Rnd().nextFloat(1.0F, 1.5F) / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debugprintln(this, "*** StabR Spars Damaged..");
          nextDMGLevels(1, 2, "StabR_D" + chunkDamageVisible("StabR"), paramShot.initiator);
        }

      }

      if (paramString.startsWith("xxlock")) {
        debugprintln(this, "*** Lock Construction: Hit..");
        if ((paramString.startsWith("xxlockr")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** Rudder Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvl")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** VatorL Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvr")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** VatorR Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
        }

      }

      if (paramString.startsWith("xxeng")) {
        debugprintln(this, "*** Engine Module: Hit..");
        if (paramString.endsWith("prop")) {
          if ((getEnergyPastArmor(3.6F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.8F))
            if (World.Rnd().nextFloat() < 0.5F) {
              debugprintln(this, "*** Engine Module: Prop Governor Hit, Disabled..");
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 3);
            } else {
              debugprintln(this, "*** Engine Module: Prop Governor Hit, Damaged..");
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 4);
            }
        }
        else if (paramString.endsWith("gear")) {
          if ((getEnergyPastArmor(4.6F, paramShot) > 0.0F) && 
            (World.Rnd().nextFloat() < 0.25F)) {
            debugprintln(this, "*** Engine Module: Bullet Jams Reductor Gear..");
            this.FM.EI.engines[0].setEngineStuck(paramShot.initiator);
          }
        }
        else if (paramString.endsWith("supc")) {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F) {
            debugprintln(this, "*** Engine Module: Supercharger Disabled..");
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 0);
          }

        }
        else if (paramString.endsWith("fue1")) {
          if (getEnergyPastArmor(1.1F, paramShot) > 0.0F) {
            debugprintln(this, "*** Engine Module: Fuel Line Pierced, Engine Fired..");
            this.FM.AS.hitEngine(paramShot.initiator, 0, 100);
          }
        } else if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(2.2F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 175000.0F) {
              debugprintln(this, "*** Engine Module: Bullet Jams Crank Ball Bearing..");
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
              debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
              this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
            }
          }
          this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
          debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
          getEnergyPastArmor(22.5F, paramShot);
        } else if (paramString.endsWith("cyl1")) {
          if ((getEnergyPastArmor(1.3F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 1.75F)) {
            this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
            debugprintln(this, "*** Engine Module: Cylinders Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 48000.0F) {
              debugprintln(this, "*** Engine Module: Cylinders Hit, Engine Fires..");
              this.FM.AS.hitEngine(paramShot.initiator, 0, 3);
            }
            if (World.Rnd().nextFloat() < 0.01F) {
              debugprintln(this, "*** Engine Module: Bullet Jams Piston Head..");
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
            }
            getEnergyPastArmor(22.5F, paramShot);
          }
          if ((Math.abs(paramPoint3d.y) < 0.137999996542931D) && 
            (getEnergyPastArmor(3.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
            if (World.Rnd().nextFloat() < 0.1F) {
              debugprintln(this, "*** Engine Module: Feed Gear Hit, Engine Stalled..");
              this.FM.EI.engines[0].setEngineStops(paramShot.initiator);
            }
            if (World.Rnd().nextFloat() < 0.05F) {
              debugprintln(this, "*** Engine Module: Feed Gear Hit, Engine Jams..");
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
            }
            if (World.Rnd().nextFloat() < 0.1F) {
              debugprintln(this, "*** Engine Module: Feed Gear Hit, Cylinders Feed Cut..");
              this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, 6);
            }
          }
        }
        else if (paramString.startsWith("xxeng1mag")) {
          i = paramString.charAt(9) - '1';
          debugprintln(this, "*** Engine Module: Magneto " + i + " Destroyed..");
          this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, i);
        } else if ((paramString.startsWith("xxeng1oil")) && 
          (getEnergyPastArmor(0.5F, paramShot) > 0.0F)) {
          debugprintln(this, "*** Engine Module: Oil Radiator Hit..");
          this.FM.AS.hitOil(paramShot.initiator, 0);
        }

      }

      if ((paramString.startsWith("xxoil")) && 
        (getEnergyPastArmor(2.1F, paramShot) > 0.0F)) {
        debugprintln(this, "*** Engine Module: Oil Tank Pierced..");
        this.FM.AS.hitOil(paramShot.initiator, 0);
        getEnergyPastArmor(4.96F, paramShot);
      }

      if ((paramString.startsWith("xxw1")) && 
        (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
        debugprintln(this, "*** Engine Module: Water Filter Pierced..");
        this.FM.AS.hitOil(paramShot.initiator, 0);
        getEnergyPastArmor(4.96F, paramShot);
      }

      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.32F)) {
          if (this.FM.AS.astateTankStates[i] == 0) {
            debugprintln(this, "*** Fuel Tank" + i + ": Pierced..");
            this.FM.AS.hitTank(paramShot.initiator, i, 2);
            this.FM.AS.doSetTankState(paramShot.initiator, i, 2);
          }
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.5F)) {
            debugprintln(this, "*** Fuel Tank" + i + ": Hit..");
            this.FM.AS.hitTank(paramShot.initiator, i, 2);
          }
        }
      }

      if (paramString.startsWith("xxmgun")) {
        if (paramString.endsWith("01")) {
          debugprintln(this, "*** Cowling Gun: Disabled..");
          this.FM.AS.setJamBullets(0, 0);
        }
        if (paramString.endsWith("02")) {
          debugprintln(this, "*** Cowling Gun: Disabled..");
          this.FM.AS.setJamBullets(0, 1);
        }
        if (paramString.endsWith("03")) {
          debugprintln(this, "*** Cowling Gun: Disabled..");
          this.FM.AS.setJamBullets(1, 0);
        }
        getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 12.96F), paramShot);
      }
      if (paramString.startsWith("xxcannon")) {
        if (paramString.endsWith("01")) {
          debugprintln(this, "*** Cowling Cannon: Disabled..");
          this.FM.AS.setJamBullets(1, 0);
        }
        if (paramString.endsWith("02")) {
          debugprintln(this, "*** Cowling Cannon: Disabled..");
          this.FM.AS.setJamBullets(1, 1);
        }
        getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F), paramShot);
      }

      if ((paramString.startsWith("xxpnm")) && 
        (getEnergyPastArmor(4.8F, paramShot) > 0.0F)) {
        debugprintln(this, "*** Pneumo System: Off-Line..");
        this.FM.AS.setInternalDamage(paramShot.initiator, 1);
      }

      if ((paramString.startsWith("xxhyd")) && 
        (getEnergyPastArmor(4.8F, paramShot) > 0.0F)) {
        debugprintln(this, "*** Hydro System: Off-Line..");
        this.FM.AS.setInternalDamage(paramShot.initiator, 0);
      }

      if (paramString.startsWith("xxins1")) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
      }

      return;
    }

    if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit")) || (paramString.startsWith("xwater"))) {
      if (chunkDamageVisible("CF") < 3) {
        hitChunk("CF", paramShot);
      }
      if (paramString.startsWith("xcockpit")) {
        if (paramPoint3d.z > 0.6D) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
        }
        else if (paramPoint3d.y > 0.0D)
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
        else {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
        }
      }
    }
    else if (paramString.startsWith("xeng")) {
      hitChunk("Engine1", paramShot);
    } else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel")) {
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
        (chunkDamageVisible("StabR") < 1)) {
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
        (chunkDamageVisible("WingLMid") < 2)) {
        hitChunk("WingLMid", paramShot);
      }

      if ((paramString.startsWith("xwingrin")) && 
        (chunkDamageVisible("WingRMid") < 2)) {
        hitChunk("WingRMid", paramShot);
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
      if (paramString.startsWith("xaronel")) {
        hitChunk("AroneL", paramShot);
      }
      if (paramString.startsWith("xaroner"))
        hitChunk("AroneR", paramShot);
    }
    else if (paramString.startsWith("xgear")) {
      if ((paramString.endsWith("1")) && 
        (World.Rnd().nextFloat() < 0.05F)) {
        debugprintln(this, "*** Hydro System (Wheel): Off-Line..");
        this.FM.AS.setInternalDamage(paramShot.initiator, 0);
      }

      if ((paramString.endsWith("2")) && 
        (World.Rnd().nextFloat() < 0.1F)) {
        debugprintln(this, "*** Gears: Stuck..");
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

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -88.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -88.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -40.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -40.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -78.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -78.0F * paramFloat, 0.0F);

    float f = Math.max(-paramFloat * 1500.0F, -90.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, f, 0.0F);

    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 70.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, -80.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, -80.0F * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat);
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    setExplosion(paramExplosion);

    if ((!(this instanceof MIG_3U)) && (paramExplosion.chunkName != null)) {
      if (paramExplosion.chunkName.startsWith("CF")) {
        if (World.Rnd().nextFloat() < 0.005F) this.FM.AS.setControlsDamage(paramExplosion.initiator, 0);
        if (World.Rnd().nextFloat() < 0.005F) this.FM.AS.setControlsDamage(paramExplosion.initiator, 1);
        if (World.Rnd().nextFloat() < 0.005F) this.FM.AS.setControlsDamage(paramExplosion.initiator, 2);
        if ((paramExplosion.power > 0.0041F) && (World.Rnd().nextFloat() < 0.025F))
          this.FM.AS.hitTank(paramExplosion.initiator, 0, World.Rnd().nextInt(0, (int)(paramExplosion.power * 808.0F)));
        if (paramExplosion.power > 0.011F) {
          this.FM.AS.hitTank(paramExplosion.initiator, 0, World.Rnd().nextInt(0, (int)(1.0F + paramExplosion.power * 808.0F)));
          this.FM.AS.hitPilot(paramExplosion.initiator, 0, (int)(paramExplosion.power * 15333.0F * World.Rnd().nextFloat()));
        }
      }

      if (paramExplosion.chunkName.startsWith("Engine")) {
        if (paramExplosion.power > 0.011F) {
          this.FM.AS.hitEngine(paramExplosion.initiator, 0, (int)(1.0F + paramExplosion.power * 666.0F * World.Rnd().nextFloat(0.5F, 1.0F)));
          this.FM.AS.hitOil(paramExplosion.initiator, 0);
        } else {
          this.FM.AS.hitEngine(paramExplosion.initiator, 0, (int)(paramExplosion.power * 450.0F * World.Rnd().nextFloat(0.5F, 1.0F)));
        }
      }

      if (paramExplosion.chunkName.startsWith("WingLMid")) {
        if ((paramExplosion.power > 0.0041F) && (World.Rnd().nextFloat() < 0.05F))
          this.FM.AS.hitTank(paramExplosion.initiator, 2, World.Rnd().nextInt(0, (int)(paramExplosion.power * 909.0F)));
        if (paramExplosion.power > 0.011F) {
          this.FM.AS.hitTank(paramExplosion.initiator, 2, World.Rnd().nextInt(0, (int)(1.0F + paramExplosion.power * 707.0F)));
        }
      }
      if (paramExplosion.chunkName.startsWith("WingRMid")) {
        if ((paramExplosion.power > 0.0041F) && (World.Rnd().nextFloat() < 0.05F))
          this.FM.AS.hitTank(paramExplosion.initiator, 3, World.Rnd().nextInt(0, (int)(paramExplosion.power * 909.0F)));
        if (paramExplosion.power > 0.011F) {
          this.FM.AS.hitTank(paramExplosion.initiator, 3, World.Rnd().nextInt(0, (int)(1.0F + paramExplosion.power * 707.0F)));
        }
      }
      if (paramExplosion.chunkName.startsWith("Tail")) {
        if (World.Rnd().nextFloat() < 0.01F) this.FM.AS.setControlsDamage(paramExplosion.initiator, 1);
        if (World.Rnd().nextFloat() < 0.11F) this.FM.AS.setControlsDamage(paramExplosion.initiator, 2);
      }
    }

    super.msgExplosion(paramExplosion);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if ((paramBoolean) && 
      (this.FM.AS.astateTankStates[0] > 5)) {
      this.FM.AS.repairTank(0);
    }

    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
  }

  static
  {
    Class localClass = MIG_3.class;
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);
  }
}