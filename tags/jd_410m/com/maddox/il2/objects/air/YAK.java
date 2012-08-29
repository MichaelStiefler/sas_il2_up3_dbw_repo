package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public abstract class YAK extends Scheme1
  implements TypeFighter
{
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

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = Math.max(-paramFloat * 1500.0F, -60.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, f, 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 82.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 82.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -85.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -85.0F * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC2_D0", paramFloat, 0.0F, 0.0F);
  }

  protected void moveFlap(float paramFloat)
  {
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -45.0F * paramFloat, 0.0F);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        debuggunnery("Armor: Hit..");
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(World.Rnd().nextFloat(5.0F, 12.7F) / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        } else if (paramString.endsWith("p2")) {
          getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 12.7F) / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        } else if (paramString.endsWith("g1")) {
          getEnergyPastArmor(World.Rnd().nextFloat(20.0F, 30.0F) / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
          if (World.Rnd().nextFloat() < 0.2F) {
            this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
          }

          if (paramShot.power <= 0.0F)
            doRicochetBack(paramShot);
        }
        else if (paramString.endsWith("g2")) {
          getEnergyPastArmor(World.Rnd().nextFloat(20.0F, 30.0F) / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
          if (paramShot.power <= 0.0F) {
            doRicochetBack(paramShot);
          }
        }
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        debuggunnery("Controls: Hit..");
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
          if (getEnergyPastArmor(0.1F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.1F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          }
          if (World.Rnd().nextFloat() < 0.1F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
          }
          if (World.Rnd().nextFloat() >= 0.1F) break;
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 7); break;
        case 2:
          if (getEnergyPastArmor(4.8F, paramShot) <= 0.0F) break;
          debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 3:
        case 4:
          if ((getEnergyPastArmor(0.99F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.5F)) break;
          debuggunnery("Controls: Ailerones Controls: Out..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 5:
        case 6:
          if (getEnergyPastArmor(0.002F, paramShot) <= 0.0F) break;
          debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2); break;
        case 7:
          if (getEnergyPastArmor(4.2F, paramShot) <= 0.0F) break;
          debuggunnery("Controls: Elevator Controls: Disabled..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 1); break;
        case 8:
          if ((getEnergyPastArmor(0.1F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.25F)) break;
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
          debugprintln(this, "*** Throttle Quadrant: Hit, Engine Controls Disabled..");
        }

        return;
      }
      if (paramString.startsWith("xxeng1")) {
        if ((paramString.endsWith("prop")) && 
          (getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 0.4F), paramShot) > 0.0F)) {
          this.FM.EI.engines[0].setKillPropAngleDevice(paramShot.initiator);
          debugprintln(this, "*** Engine Prop Governor Failed..");
        }

        if ((paramString.endsWith("gear")) && 
          (getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 1.1F), paramShot) > 0.0F)) {
          this.FM.EI.engines[0].setKillPropAngleDeviceSpeeds(paramShot.initiator);
          debugprintln(this, "*** Engine Prop Governor Damaged..");
        }

        if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 6.8F), paramShot) > 0.0F) {
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
            if (World.Rnd().nextFloat() < 0.08F) {
              this.FM.EI.engines[0].setEngineStuck(paramShot.initiator);
              debugprintln(this, "*** Engine Crank Case Hit - Ball Bearing Jammed - Engine Stuck..");
            }
            this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
            debugprintln(this, "*** Engine Crank Case Hit - Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
          }
          if (World.Rnd().nextFloat() < 0.01F) {
            this.FM.EI.engines[0].setEngineStops(paramShot.initiator);
            debugprintln(this, "*** Engine Crank Case Hit - Engine Stalled..");
          }
          if (World.Rnd().nextFloat() < 0.01F) {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 10);
            debugprintln(this, "*** Engine Crank Case Hit - Fuel Feed Hit - Engine Flamed..");
          }
          getEnergyPastArmor(6.0F, paramShot);
        }
        if (((paramString.endsWith("cyl1")) || (paramString.endsWith("cyl2"))) && 
          (getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 2.542F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 1.72F)) {
          this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
          debugprintln(this, "*** Engine Cylinders Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");
          if (World.Rnd().nextFloat() < 0.01F) {
            this.FM.EI.engines[0].setEngineStuck(paramShot.initiator);
            debugprintln(this, "*** Engine Cylinder Case Broken - Engine Stuck..");
          }
          if (World.Rnd().nextFloat() < paramShot.power / 24000.0F) {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 3);
            debugprintln(this, "*** Engine Cylinders Hit - Engine Fires..");
          }
          getEnergyPastArmor(World.Rnd().nextFloat(3.0F, 46.700001F), paramShot);
        }

        if ((paramString.endsWith("supc")) && 
          (getEnergyPastArmor(0.05F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.89F)) {
          this.FM.EI.engines[0].setKillCompressor(paramShot.initiator);
          debugprintln(this, "*** Engine Supercharger Out..");
        }

        if ((paramString.endsWith("eqpt")) && 
          (getEnergyPastArmor(World.Rnd().nextFloat(0.001F, 0.2F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.89F)) {
          if (World.Rnd().nextFloat() < 0.11F) {
            this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, World.Rnd().nextInt(0, 1));
            debugprintln(this, "*** Engine Magneto Out..");
          }
          if (World.Rnd().nextFloat() < 0.11F) {
            this.FM.EI.engines[0].setKillCompressor(paramShot.initiator);
            debugprintln(this, "*** Engine Compressor Feed Out..");
          }
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
      if (paramString.startsWith("xxmgun0")) {
        i = paramString.charAt(7) - '1';
        if (getEnergyPastArmor(0.5F, paramShot) > 0.0F) {
          debuggunnery("Armament: Machine Gun (" + i + ") Disabled..");
          this.FM.AS.setJamBullets(0, i);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcannon")) {
        if (getEnergyPastArmor(9.8F, paramShot) > 0.0F) {
          debuggunnery("Armament: Cannon (0) Disabled..");
          this.FM.AS.setJamBullets(1, 0);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxoil")) {
        if ((getEnergyPastArmor(0.25F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          this.FM.AS.hitOil(paramShot.initiator, 0);
          getEnergyPastArmor(0.22F, paramShot);
          debuggunnery("Engine Module: Oil Radiator Pierced..");
        }
        return;
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          if (this.FM.AS.astateTankStates[i] == 0) {
            debuggunnery("Fuel Tank (" + i + "): Pierced..");
            this.FM.AS.hitTank(paramShot.initiator, i, 1);
            this.FM.AS.doSetTankState(paramShot.initiator, i, 1);
          }
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.1F)) {
            this.FM.AS.hitTank(paramShot.initiator, i, 2);
            debuggunnery("Fuel Tank (" + i + "): Hit..");
          }
        }
        return;
      }
      if (paramString.startsWith("xxpnm")) {
        this.FM.AS.setInternalDamage(paramShot.initiator, 1);
        return;
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

        if ((paramString.startsWith("xxspark")) && 
          (World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(6.8F * World.Rnd().nextFloat(1.0F, 1.5F) / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debugprintln(this, "*** Keel Spars Damaged..");
          nextDMGLevels(1, 2, "Keel1_D" + chunkDamageVisible("Keel1"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxspart")) && 
          (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.86F / (float)Math.sqrt(v1.y * v1.y + v1.z * v1.z), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          debuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxwater"));
      return;
    }

    if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit"))) {
      hitChunk("CF", paramShot);
      if (paramString.startsWith("xcockpit")) {
        if ((paramPoint3d.z > 0.716000020503998D) && (World.Rnd().nextFloat() < 0.25F) && 
          (World.Rnd().nextFloat() < 0.2F)) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
        }

        if ((paramPoint3d.z > 0.716000020503998D) && (World.Rnd().nextFloat() < 0.25F) && 
          (World.Rnd().nextFloat() < 0.2F)) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
        }

        if ((paramPoint3d.x > -0.5D) && 
          (World.Rnd().nextFloat() < 0.2F)) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
        }

        if ((paramPoint3d.x > -0.5D) && 
          (World.Rnd().nextFloat() < 0.2F)) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
        }
      }

      return;
    }if (paramString.startsWith("xeng")) {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel")) {
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xrudder")) {
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

      if ((paramString.endsWith("2")) && 
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

  static
  {
    Class localClass = YAK.class;
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);
  }
}