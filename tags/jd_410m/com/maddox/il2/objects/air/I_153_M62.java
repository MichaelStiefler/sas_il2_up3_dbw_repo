package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class I_153_M62 extends Scheme1
  implements TypeFighter, TypeTNBFighter
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

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 11:
      this.FM.cut(11, paramInt2, paramActor);
      return false;
    case 19:
      this.FM.cut(19, paramInt2, paramActor);
      return false;
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void update(float paramFloat)
  {
    this.FM.CT.FlapsControl = 0.0F;
    if ((this.FM instanceof Maneuver)) ((Maneuver)this.FM).actionTimerStart = 0L;
    float f = cvt(this.FM.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, -20.0F);
    hierMesh().chunkSetAngles("Water_D0", 0.0F, f, 0.0F);
    super.update(paramFloat);
  }

  protected void moveFlap(float paramFloat)
  {
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -95.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 95.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 15.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 15.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, 170.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, 170.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, 85.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -85.0F * paramFloat, 0.0F);

    float f = Math.max(-paramFloat * 1500.0F, -100.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, -f, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor")) {
        debugprintln(this, "*** Armor: Hit..");
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(8.100000381469727D / (Math.abs(v1.x) + 9.999999747378752E-006D), paramShot);
        }
        return;
      }

      if (paramString.startsWith("xxcontrols")) {
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
          if (getEnergyPastArmor(2.3F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.25F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);
            debugprintln(this, "*** Rudder Controls: Disabled..");
          }
          if (World.Rnd().nextFloat() >= 0.25F) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          debugprintln(this, "*** Elevator Controls: Disabled..");
        case 2:
        case 3:
          if (getEnergyPastArmor(1.5F, paramShot) > 0.0F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 0);
            debugprintln(this, "*** Aileron Controls: Control Crank Destroyed..");
          }
        }

      }

      if (paramString.startsWith("xxspar")) {
        debugprintln(this, "*** Spar Construction: Hit..");
        if ((paramString.startsWith("xxspart")) && 
          (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.5F / (float)Math.sqrt(v1.y * v1.y + v1.z * v1.z), paramShot) > 0.0F)) {
          debugprintln(this, "*** Tail1 Spars Broken in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparli")) && 
          (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D" + chunkDamageVisible("WingLIn"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparri")) && 
          (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D" + chunkDamageVisible("WingRIn"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlm")) && 
          (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D" + chunkDamageVisible("WingLMid"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparrm")) && 
          (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D" + chunkDamageVisible("WingRMid"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlo")) && 
          (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D" + chunkDamageVisible("WingLOut"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparro")) && 
          (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D" + chunkDamageVisible("WingROut"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxstabl")) && 
          (getEnergyPastArmor(16.200001F, paramShot) > 0.0F)) {
          debugprintln(this, "*** StabL Spars Damaged..");
          nextDMGLevels(1, 2, "StabL_D" + chunkDamageVisible("StabL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxstabr")) && 
          (getEnergyPastArmor(16.200001F, paramShot) > 0.0F)) {
          debugprintln(this, "*** StabR Spars Damaged..");
          nextDMGLevels(1, 2, "StabR_D" + chunkDamageVisible("StabR"), paramShot.initiator);
        }

      }

      if (paramString.startsWith("xxlock")) {
        debugprintln(this, "*** Lock Construction: Hit..");
        if ((paramString.startsWith("xxlockr")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F)) {
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

        if ((paramString.startsWith("xxlockal")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** AroneL Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockar")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** AroneR Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), paramShot.initiator);
        }

      }

      if (paramString.startsWith("xxeng")) {
        debugprintln(this, "*** Engine Module: Hit..");
        if (paramString.endsWith("prop")) {
          if ((getEnergyPastArmor(0.449999988079071D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.05F)) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 3);
            debugprintln(this, "*** Engine Module: Prop Governor Hit, Disabled..");
          }
        } else if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(5.1F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 175000.0F) {
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
              debugprintln(this, "*** Engine Module: Bullet Jams Crank Ball Bearing..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
              debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
            }
            this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
            debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
          }
          getEnergyPastArmor(22.5F, paramShot);
        } else if (paramString.startsWith("xxeng1cyls")) {
          if ((getEnergyPastArmor(World.Rnd().nextFloat(1.5F, 23.9F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 1.12F)) {
            this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
            debugprintln(this, "*** Engine Module: Cylinders Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 48000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, 0, 3);
              debugprintln(this, "*** Engine Module: Cylinders Hit, Engine Fires..");
            }
            if (World.Rnd().nextFloat() < 0.005F) {
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
              debugprintln(this, "*** Engine Module: Bullet Jams Piston Head..");
            }
            getEnergyPastArmor(22.5F, paramShot);
          }
        } else if (paramString.endsWith("eqpt")) {
          if ((getEnergyPastArmor(2.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
            if (World.Rnd().nextFloat() < 0.1F) {
              this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, 0);
              debugprintln(this, "*** Engine Module: Magneto 0 Destroyed..");
            }
            if (World.Rnd().nextFloat() < 0.1F) {
              this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, 1);
              debugprintln(this, "*** Engine Module: Magneto 1 Destroyed..");
            }
            if (World.Rnd().nextFloat() < 0.1F) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
              debugprintln(this, "*** Engine Module: Prop Controls Cut..");
            }
            if (World.Rnd().nextFloat() < 0.1F) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
              debugprintln(this, "*** Engine Module: Throttle Controls Cut..");
            }
            if (World.Rnd().nextFloat() < 0.1F) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 7);
              debugprintln(this, "*** Engine Module: Mix Controls Cut..");
            }
          }
        } else if (paramString.endsWith("oil1")) {
          this.FM.AS.hitOil(paramShot.initiator, 0);
          debugprintln(this, "*** Engine Module: Oil Radiator Hit..");
        }
      }

      if (paramString.startsWith("xxoil")) {
        this.FM.AS.hitOil(paramShot.initiator, 0);
        getEnergyPastArmor(0.22F, paramShot);
        debugprintln(this, "*** Engine Module: Oil Tank Pierced..");
      }

      if ((paramString.startsWith("xxtank1")) && 
        (getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
        if (this.FM.AS.astateTankStates[0] == 0) {
          debugprintln(this, "*** Fuel Tank: Pierced..");
          this.FM.AS.hitTank(paramShot.initiator, 0, 1);
        }
        if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.999F)) {
          this.FM.AS.hitTank(paramShot.initiator, 0, 2);
          debugprintln(this, "*** Fuel Tank: Hit..");
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
        if (paramString.endsWith("04")) {
          debugprintln(this, "*** Cowling Gun: Disabled..");
          this.FM.AS.setJamBullets(1, 1);
        }
        getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 28.33F), paramShot);
      }
      return;
    }

    if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit"))) {
      if (chunkDamageVisible("CF") < 3) {
        hitChunk("CF", paramShot);
      }
      if (paramString.startsWith("xcf1")) {
        if ((paramPoint3d.x > -1.147D) && (paramPoint3d.x < -0.869D) && (paramPoint3d.z > 0.653D)) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
        }
        if (World.Rnd().nextFloat() < 0.012F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
        }
        if ((paramPoint3d.x > -1.195D) && (paramPoint3d.x < -0.904D) && (paramPoint3d.z > 0.203D)) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
        }
        if ((paramPoint3d.x > -1.402D) && (paramPoint3d.x < -1.125D) && (paramPoint3d.z > -0.032D) && (paramPoint3d.z < 0.501D))
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
      }
    }
    else if (paramString.startsWith("xeng")) {
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

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
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

  public void msgExplosion(Explosion paramExplosion)
  {
    setExplosion(paramExplosion);
    if ((paramExplosion.chunkName != null) && (paramExplosion.power > 0.0F) && 
      (paramExplosion.chunkName.startsWith("Tail1"))) {
      if (World.Rnd().nextFloat(0.0F, 0.038F) < paramExplosion.power) {
        this.FM.AS.setControlsDamage(paramExplosion.initiator, 1);
      }
      if (World.Rnd().nextFloat(0.0F, 0.042F) < paramExplosion.power) {
        this.FM.AS.setControlsDamage(paramExplosion.initiator, 2);
      }
    }

    super.msgExplosion(paramExplosion);
  }

  static
  {
    Class localClass = I_153_M62.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "I-153");
    Property.set(localClass, "meshName", "3DO/Plane/I-153/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar00());
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);

    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1943.0F);

    Property.set(localClass, "FlightModel", "FlightModels/I-153-M62.fmd");
    Property.set(localClass, "cockpitClass", CockpitI_153.class);
    Property.set(localClass, "LOSElevation", 0.84305F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 9, 9, 9, 9, 9, 9, 9, 9 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08" });

    weaponsRegister(localClass, "default", new String[] { "MGunShKASsi 700", "MGunShKASsi 750", "MGunShKASsi 500", "MGunShKASsi 520", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "4xAO10", new String[] { "MGunShKASsi 700", "MGunShKASsi 750", "MGunShKASsi 500", "MGunShKASsi 520", null, null, null, null, null, null, null, null, "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xAO10_2xFAB50", new String[] { "MGunShKASsi 700", "MGunShKASsi 750", "MGunShKASsi 500", "MGunShKASsi 520", null, null, null, null, null, null, null, null, "BombGunAO10 1", "BombGunAO10 1", "BombGunFAB50 1", "BombGunFAB50 1", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xFAB50", new String[] { "MGunShKASsi 700", "MGunShKASsi 750", "MGunShKASsi 500", "MGunShKASsi 520", null, null, null, null, null, null, null, null, null, null, "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "4xFAB50", new String[] { "MGunShKASsi 700", "MGunShKASsi 750", "MGunShKASsi 500", "MGunShKASsi 520", null, null, null, null, null, null, null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xFAB100", new String[] { "MGunShKASsi 700", "MGunShKASsi 750", "MGunShKASsi 500", "MGunShKASsi 520", null, null, null, null, null, null, null, null, null, null, "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xFAB50_2xFAB100", new String[] { "MGunShKASsi 700", "MGunShKASsi 750", "MGunShKASsi 500", "MGunShKASsi 520", null, null, null, null, null, null, null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "8xRS82", new String[] { "MGunShKASsi 700", "MGunShKASsi 750", "MGunShKASsi 500", "MGunShKASsi 520", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", null, null, null, null, "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}