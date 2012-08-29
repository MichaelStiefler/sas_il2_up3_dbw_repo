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
import com.maddox.rts.Property;

public abstract class BEAU extends Scheme2
{
  private float[] flapps = { 0.0F, 0.0F };

  public void moveCockpitDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Blister1_D0", 0.0F, cvt(paramFloat, 0.01F, 0.99F, 0.0F, -120.0F), 0.0F);
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null)) Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      setDoorSnd(paramFloat);
    }
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 33:
    case 34:
      hitProp(0, paramInt2, paramActor);
      cut("Engine1");
      break;
    case 36:
    case 37:
      hitProp(1, paramInt2, paramActor);
      cut("Engine2");
    case 35:
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, -60.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, cvt(paramFloat, 0.01F, 0.89F, 0.0F, -103.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, cvt(paramFloat, 0.01F, 0.29F, 0.0F, -63.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, cvt(paramFloat, 0.01F, 0.29F, 0.0F, -58.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, cvt(paramFloat, 0.11F, 0.99F, 0.0F, -103.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, cvt(paramFloat, 0.11F, 0.39F, 0.0F, -63.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, cvt(paramFloat, 0.11F, 0.39F, 0.0F, -58.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveWheelSink() {
    resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.2F, 0.0F, 0.165F);
    hierMesh().chunkSetLocate("GearL25_D0", xyz, ypr);
    resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.2F, 0.0F, 0.165F);
    hierMesh().chunkSetLocate("GearR25_D0", xyz, ypr);
  }
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("e1")) {
          getEnergyPastArmor(12.100000381469727D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        }
        if (paramString.endsWith("e2")) {
          getEnergyPastArmor(12.100000381469727D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        }
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(12.7F, paramShot);
        }
        if (paramString.endsWith("p2")) {
          getEnergyPastArmor(12.7F, paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
          if (getEnergyPastArmor(1.5F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.15F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
            debuggunnery("*** Engine1 Throttle Controls Out..");
          }
          if (World.Rnd().nextFloat() < 0.15F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
            debuggunnery("*** Engine1 Prop Controls Out..");
          }
          if (World.Rnd().nextFloat() >= 0.15F) break;
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 7);
          debuggunnery("*** Engine1 Mix Controls Out.."); break;
        case 2:
          if (getEnergyPastArmor(1.5F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.15F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 1);
            debuggunnery("*** Engine2 Throttle Controls Out..");
          }
          if (World.Rnd().nextFloat() < 0.15F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 6);
            debuggunnery("*** Engine2 Prop Controls Out..");
          }
          if (World.Rnd().nextFloat() >= 0.15F) break;
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 7);
          debuggunnery("*** Engine2 Mix Controls Out.."); break;
        case 3:
        case 4:
          if (getEnergyPastArmor(6.0F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.5F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
            debuggunnery("Evelator Controls Out..");
          }
          if (World.Rnd().nextFloat() >= 0.5F) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          debuggunnery("Rudder Controls Out.."); break;
        case 5:
        case 6:
          if ((getEnergyPastArmor(1.5F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.25F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          debuggunnery("Ailerons Controls Out..");
        }

        return;
      }
      if (paramString.startsWith("xxengine")) {
        i = 0;
        if (paramString.startsWith("xxengine2")) {
          i = 1;
        }
        debuggunnery("Engine Module[" + i + "]: Hit..");
        if (getEnergyPastArmor(World.Rnd().nextFloat(0.2F, 0.55F), paramShot) > 0.0F) {
          if (World.Rnd().nextFloat() < paramShot.power / 280000.0F) {
            debuggunnery("Engine Module: Engine Crank Case Hit - Engine Stucks..");
            this.FM.AS.setEngineStuck(paramShot.initiator, i);
          }
          if (World.Rnd().nextFloat() < paramShot.power / 100000.0F) {
            debuggunnery("Engine Module: Engine Crank Case Hit - Engine Damaged..");
            this.FM.AS.hitEngine(paramShot.initiator, i, 2);
          }
        }
        if ((getEnergyPastArmor(0.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[i].getCylindersRatio() * 0.66F)) {
          this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 32200.0F)));
          debuggunnery("Engine Module: Cylinders Hit, " + this.FM.EI.engines[i].getCylindersOperable() + "/" + this.FM.EI.engines[i].getCylinders() + " Left..");
          if (World.Rnd().nextFloat() < paramShot.power / 1000000.0F) {
            this.FM.AS.hitEngine(paramShot.initiator, i, 2);
            debuggunnery("Engine Module: Cylinders Hit - Engine Fires..");
          }
        }
        getEnergyPastArmor(25.0F, paramShot);
        return;
      }
      if (paramString.startsWith("xxmgun")) {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F)) {
          this.FM.AS.setJamBullets(0, i);
          getEnergyPastArmor(11.98F, paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxoil")) {
        i = 0;
        if (paramString.endsWith("2")) {
          i = 1;
        }
        if ((getEnergyPastArmor(0.21F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.2435F)) {
          this.FM.AS.hitOil(paramShot.initiator, i);
        }
        debugprintln(this, "*** Engine (" + i + ") Module: Oil Tank Pierced..");
        return;
      }
      if ((paramString.startsWith("xxprop1")) && 
        (getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.8F)) {
        if (World.Rnd().nextFloat() < 0.5F) {
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 3);
          debugprintln(this, "*** Engine1 Module: Prop Governor Hit, Disabled..");
        } else {
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 4);
          debugprintln(this, "*** Engine1 Module: Prop Governor Hit, Damaged..");
        }
      }

      if ((paramString.startsWith("xxprop2")) && 
        (getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.8F)) {
        if (World.Rnd().nextFloat() < 0.5F) {
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 3);
          debugprintln(this, "*** Engine2 Module: Prop Governor Hit, Disabled..");
        } else {
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 4);
          debugprintln(this, "*** Engine2 Module: Prop Governor Hit, Damaged..");
        }
      }

      if (paramString.startsWith("xxspar")) {
        if ((paramString.startsWith("xxsparli")) && 
          (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparri")) && 
          (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlm")) && 
          (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparrm")) && 
          (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlo")) && 
          (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparro")) && 
          (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxspark")) && 
          (chunkDamageVisible("Keel1") > 1) && (getEnergyPastArmor(9.6F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** Keel1 Spars Damaged..");
          nextDMGLevels(1, 2, "Keel1_D2", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxstruts")) {
        if ((paramString.startsWith("xxstruts1")) && 
          (chunkDamageVisible("Engine1") > 1) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 8.0F), paramShot) > 0.0F)) {
          debuggunnery("*** Engine1 Spars Damaged..");
          nextDMGLevels(1, 2, "Engine1_D2", paramShot.initiator);
        }

        if ((paramString.startsWith("xxstruts2")) && 
          (chunkDamageVisible("Engine2") > 1) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 8.0F), paramShot) > 0.0F)) {
          debuggunnery("*** Engine2 Spars Damaged..");
          nextDMGLevels(1, 2, "Engine2_D2", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(2.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 10.25F)) {
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
      return;
    }

    if (paramString.startsWith("xcf")) {
      if (chunkDamageVisible("CF") < 3) {
        hitChunk("CF", paramShot);
      }
      if (paramPoint3d.x > 0.5D) {
        if (paramPoint3d.z > 0.913D) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
        }
        if (paramPoint3d.z > 0.341D) {
          if (paramPoint3d.x < 1.402D) {
            if (paramPoint3d.y > 0.0D)
              this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
            else
              this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
          }
          else {
            this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
          }
        }
        else if (paramPoint3d.y > 0.0D)
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
        else {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
        }

        if ((paramPoint3d.x > 1.691D) && (paramPoint3d.x < 1.98D))
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
      }
    }
    else if (paramString.startsWith("xtail")) {
      hitChunk("Tail1", paramShot);
    } else if (paramString.startsWith("xkeel1")) {
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xrudder1")) {
      hitChunk("Rudder1", paramShot);
    } else if (paramString.startsWith("xstabl")) {
      hitChunk("StabL", paramShot);
    } else if (paramString.startsWith("xstabr")) {
      hitChunk("StabR", paramShot);
    } else if (paramString.startsWith("xvatorl")) {
      hitChunk("VatorL", paramShot);
    } else if (paramString.startsWith("xvatorr")) {
      hitChunk("VatorR", paramShot);
    } else if (paramString.startsWith("xwinglin")) {
      if (chunkDamageVisible("WingLIn") < 3)
        hitChunk("WingLIn", paramShot);
    }
    else if (paramString.startsWith("xwingrin")) {
      if (chunkDamageVisible("WingRIn") < 3)
        hitChunk("WingRIn", paramShot);
    }
    else if (paramString.startsWith("xwinglmid")) {
      if (chunkDamageVisible("WingLMid") < 3)
        hitChunk("WingLMid", paramShot);
    }
    else if (paramString.startsWith("xwingrmid")) {
      if (chunkDamageVisible("WingRMid") < 3)
        hitChunk("WingRMid", paramShot);
    }
    else if (paramString.startsWith("xwinglout")) {
      if (chunkDamageVisible("WingLOut") < 3)
        hitChunk("WingLOut", paramShot);
    }
    else if (paramString.startsWith("xwingrout")) {
      if (chunkDamageVisible("WingROut") < 3)
        hitChunk("WingROut", paramShot);
    }
    else if (paramString.startsWith("xaronel")) {
      hitChunk("AroneL", paramShot);
    } else if (paramString.startsWith("xaroner")) {
      hitChunk("AroneR", paramShot);
    } else if (paramString.startsWith("xengine1")) {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xengine2")) {
      if (chunkDamageVisible("Engine2") < 2)
        hitChunk("Engine2", paramShot);
    }
    else if (paramString.startsWith("xgearl")) {
      hitChunk("GearL2", paramShot);
    } else if (paramString.startsWith("xgearr")) {
      hitChunk("GearR2", paramShot);
    } else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
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

    for (int i = 1; i < 3; i++)
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    for (int i = 0; i < 2; i++) {
      float f = this.FM.EI.engines[i].getControlRadiator();
      if (Math.abs(this.flapps[i] - f) > 0.01F) {
        this.flapps[i] = f;
        for (int j = 1; j < 23; j++)
          hierMesh().chunkSetAngles("Water" + (j + 22 * i) + "_D0", 0.0F, -20.0F * f, 0.0F);
      }
    }
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("Head1_D0", false);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
    }
  }

  static
  {
    Class localClass = BEAU.class;
    Property.set(localClass, "originCountry", PaintScheme.countryBritain);
  }
}