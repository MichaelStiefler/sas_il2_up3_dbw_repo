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

public abstract class P_51 extends Scheme1
  implements TypeFighter, TypeBNZFighter, TypeStormovik
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = paramFloat > 0.5F ? cvt(paramFloat, 0.8F, 1.0F, 95.0F, 0.0F) : cvt(paramFloat, 0.0F, 0.2F, 0.0F, 95.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, -105.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, 90.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -85.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, f, 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 85.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, -f, 0.0F);
  }
  protected void moveGear(float paramFloat) {
    moveGear(hierMesh(), paramFloat);
    resetYPRmodifier();
    xyz[1] = cvt(paramFloat, 0.0F, 0.4F, 0.0F, -0.1F);
    hierMesh().chunkSetLocate("GearL9_D0", xyz, ypr);
    if (((this instanceof P_51B)) || ((this instanceof P_51C)))
      hierMesh().chunkSetLocate("GearR9_D0", xyz, ypr); 
  }
  public void moveSteering(float paramFloat) {
  }

  public void moveWheelSink() {
    resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.2F, 0.0F, 0.2F);
    hierMesh().chunkSetLocate("GearL3_D0", xyz, ypr);
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.2F, 0.0F, 0.2F);
    hierMesh().chunkSetLocate("GearR3_D0", xyz, ypr);
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt) {
    case 0:
    }
  }

  public void doMurderPilot(int paramInt) {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
    }
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -52.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap04_D0", 0.0F, f, 0.0F);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        debuggunnery("Armor: Hit..");
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(World.Rnd().nextFloat(5.0F, 28.096001F), paramShot);
        } else if (paramString.endsWith("p2")) {
          getEnergyPastArmor(13.350000381469727D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        } else if (paramString.endsWith("p3")) {
          getEnergyPastArmor(World.Rnd().nextFloat(20.0F, 40.0F) / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
          if (paramShot.power <= 0.0F) {
            doRicochetBack(paramShot);
          }
        }
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        debuggunnery("Controls: Hit..");
        i = paramString.charAt(10) - '0';
        if (paramString.endsWith("10")) {
          i = 10;
        }
        switch (i) {
        case 1:
          if (getEnergyPastArmor(2.2F, paramShot) <= 0.0F) break;
          debuggunnery("Controls: Control Column: Hit, Controls Destroyed..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          if (World.Rnd().nextFloat() < 0.25F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          }
          if (World.Rnd().nextFloat() >= 0.25F) break;
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6); break;
        case 2:
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
          break;
        case 3:
        case 4:
          if ((World.Rnd().nextFloat() >= 0.5F) || (getEnergyPastArmor(0.1F, paramShot) <= 0.0F)) break;
          debuggunnery("Controls: Ailerones Controls: Out..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 5:
        case 6:
        case 8:
        case 9:
          if (getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 2.93F), paramShot) <= 0.0F) break;
          debuggunnery("Controls: Elevator Controls: Disabled / Strings Broken..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 1); break;
        case 7:
        case 10:
          if (getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 2.93F), paramShot) <= 0.0F) break;
          debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
        }

        return;
      }
      if (paramString.startsWith("xxeng1")) {
        debuggunnery("Engine Module: Hit..");
        if ((paramString.endsWith("prop")) && 
          (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
          this.FM.EI.engines[0].setKillPropAngleDevice(paramShot.initiator);
        }

        if ((paramString.endsWith("case")) && 
          (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
          if (World.Rnd().nextFloat() < paramShot.power / 200000.0F) {
            this.FM.AS.setEngineStuck(paramShot.initiator, 0);
            debuggunnery("Engine Module: Engine Crank Case Hit - Engine Stucks..");
          }
          if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
            debuggunnery("Engine Module: Engine Crank Case Hit - Engine Damaged..");
          }
          if (World.Rnd().nextFloat() < paramShot.power / 28000.0F) {
            this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, 1);
            debuggunnery("Engine Module: Engine Crank Case Hit - Cylinder Feed Out, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");
          }
          this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
          debuggunnery("Engine Module: Engine Crank Case Hit - Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
        }

        if ((paramString.endsWith("cyls")) && 
          (getEnergyPastArmor(0.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 1.75F)) {
          this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
          debuggunnery("Engine Module: Engine Cylinders Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");
          if (this.FM.AS.astateEngineStates[0] < 1) {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
          }
          if (World.Rnd().nextFloat() < paramShot.power / 24000.0F) {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 3);
            debuggunnery("Engine Module: Engine Cylinders Hit - Engine Fires..");
          }
          getEnergyPastArmor(25.0F, paramShot);
        }

        if ((paramString.endsWith("supc")) && 
          (getEnergyPastArmor(0.05F, paramShot) > 0.0F)) {
          this.FM.EI.engines[0].setKillCompressor(paramShot.initiator);
        }

        if (paramString.endsWith("eqpt")) {
          if ((World.Rnd().nextFloat() < 0.3F) && (getEnergyPastArmor(0.05F, paramShot) > 0.0F)) {
            this.FM.EI.engines[0].setKillPropAngleDeviceSpeeds(paramShot.initiator);
          }
          if ((World.Rnd().nextFloat() < 0.3F) && (getEnergyPastArmor(0.05F, paramShot) > 0.0F)) {
            this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, 0);
          }
          if ((World.Rnd().nextFloat() < 0.3F) && (getEnergyPastArmor(0.05F, paramShot) > 0.0F)) {
            this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, 1);
          }
          if ((World.Rnd().nextFloat() < 0.3F) && (getEnergyPastArmor(0.05F, paramShot) > 0.0F)) {
            this.FM.EI.engines[0].setEngineStuck(paramShot.initiator);
          }
          if ((World.Rnd().nextFloat() < 0.3F) && (getEnergyPastArmor(0.05F, paramShot) > 0.0F)) {
            this.FM.EI.engines[0].setEngineStops(paramShot.initiator);
          }
        }
        if (paramString.endsWith("oil1")) {
          this.FM.AS.hitOil(paramShot.initiator, 0);
        }
        if ((paramString.endsWith("wat1")) && 
          (World.Rnd().nextFloat() < 0.05F)) {
          this.FM.AS.hitOil(paramShot.initiator, 0);
        }

        if (paramString.startsWith("xxeng1mag")) {
          i = paramString.charAt(9) - '1';
          this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, i);
          debuggunnery("Engine Module: Magneto " + i + " Destroyed..");
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
      if (paramString.startsWith("xxoil")) {
        if ((getEnergyPastArmor(0.25F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          this.FM.AS.hitOil(paramShot.initiator, 0);
          getEnergyPastArmor(0.22F, paramShot);
          debuggunnery("Engine Module: Oil Tank Pierced..");
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
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.075F)) {
            this.FM.AS.hitTank(paramShot.initiator, i, 2);
            debuggunnery("Fuel Tank (" + i + "): Hit..");
          }
        }
      }
      return;
    }

    if (paramString.startsWith("xblister")) {
      this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
    }
    if (paramString.startsWith("xcf")) {
      hitChunk("CF", paramShot);
    } else if (paramString.startsWith("xeng")) {
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

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 35:
      this.FM.AS.setJamBullets(0, 0);
      this.FM.AS.setJamBullets(1, 0);
      this.FM.AS.setJamBullets(1, 2);
      break;
    case 38:
      this.FM.AS.setJamBullets(0, 1);
      this.FM.AS.setJamBullets(1, 1);
      this.FM.AS.setJamBullets(1, 3);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void update(float paramFloat)
  {
    float f = this.FM.EI.engines[0].getControlRadiator();
    hierMesh().chunkSetAngles("Oil_D0", 0.0F, 15.0F * f, 0.0F);
    hierMesh().chunkSetAngles("Water_D0", 0.0F, -12.0F + 15.0F * f, 0.0F);
    super.update(paramFloat);
  }

  static
  {
    Class localClass = P_51.class;
    Property.set(localClass, "originCountry", PaintScheme.countryUSA);
  }
}