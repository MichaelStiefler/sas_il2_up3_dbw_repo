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
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public abstract class AR_196 extends Scheme1
  implements TypeSailPlane, TypeScout, TypeStormovik
{
  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 9:
    case 33:
      this.FM.AS.hitTank(this, 0, 100);
      this.FM.Gears.bIsSail = false;
      break;
    case 10:
    case 36:
      this.FM.AS.hitTank(this, 1, 100);
      this.FM.Gears.bIsSail = false;
      break;
    case 31:
    case 32:
      this.FM.cut(31, paramInt2, paramActor);
      this.FM.cut(32, paramInt2, paramActor);
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void moveFlap(float paramFloat)
  {
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -45.0F * paramFloat, 0.0F);
  }

  protected void moveElevator(float paramFloat)
  {
    hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -30.0F * paramFloat, 0.0F);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat)
  {
  }

  public void moveWheelSink() {
  }

  protected void moveRudder(float paramFloat) {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("FRudderL_D0", 45.0F * paramFloat, 0.0F, 0.0F);
    hierMesh().chunkSetAngles("FRudderR_D0", 45.0F * paramFloat, 0.0F, 0.0F);
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

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -35.0F) { f1 = -35.0F; bool = false; }
      if (f1 > 35.0F) { f1 = 35.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 30.0F) break; f2 = 30.0F; bool = false;
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
      this.FM.turret[0].setHealth(paramFloat);
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
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("p1"))
          getEnergyPastArmor(World.Rnd().nextFloat(5.0F, 10.0F), paramShot);
        else if (paramString.endsWith("p2"))
          getEnergyPastArmor(5.25D / (Math.abs(v1.y) + 9.999999747378752E-005D), paramShot);
        else if (paramString.endsWith("p3")) {
          getEnergyPastArmor(World.Rnd().nextFloat(5.0F, 10.0F), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
          if (getEnergyPastArmor(2.2F, paramShot) <= 0.0F) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 2:
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
          break;
        case 3:
          if (getEnergyPastArmor(0.1F, paramShot) <= 0.0F) break;
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6); break;
        case 4:
        case 5:
          if ((getEnergyPastArmor(1.0F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.85F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 6:
        case 9:
          if (getEnergyPastArmor(0.5F, paramShot) <= 0.0F) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 1); break;
        case 7:
        case 8:
          if (getEnergyPastArmor(0.5F, paramShot) <= 0.0F) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
        }

        return;
      }
      if (paramString.startsWith("xxspar")) {
        if (((paramString.endsWith("t1")) || (paramString.endsWith("t2"))) && 
          (World.Rnd().nextFloat() < 0.1F) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(19.9F / (float)Math.sqrt(v1.y * v1.y + v1.z * v1.z), paramShot) > 0.0F)) {
          debuggunnery("*** Tail1 Spars Broken in Half..");
          msgCollision(this, "Tail1_D0", "Tail1_D0");
        }

        if (((paramString.endsWith("li1")) || (paramString.endsWith("li2"))) && 
          (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("ri1")) || (paramString.endsWith("ri2"))) && 
          (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("lm1")) || (paramString.endsWith("lm2"))) && 
          (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("rm1")) || (paramString.endsWith("rm2"))) && 
          (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("lo1")) || (paramString.endsWith("lo2"))) && 
          (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("ro1")) || (paramString.endsWith("ro2"))) && 
          (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxeng1")) {
        if ((paramString.endsWith("prop")) && 
          (getEnergyPastArmor(0.2F, paramShot) > 0.0F)) {
          this.FM.EI.engines[0].setKillPropAngleDevice(paramShot.initiator);
        }

        if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(0.2F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 140000.0F) {
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
            }
          }
          else if (World.Rnd().nextFloat() < 0.04F) {
            this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, 1);
          } else {
            this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - 0.02F);
          }

          getEnergyPastArmor(12.0F, paramShot);
        }
        if (paramString.endsWith("cyls")) {
          if ((getEnergyPastArmor(0.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 1.75F)) {
            this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 19000.0F)));
            if (World.Rnd().nextFloat() < paramShot.power / 48000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
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
        if (paramString.startsWith("xxeng1mag")) {
          i = paramString.charAt(9) - '1';
          this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, i);
        }
        if (paramString.endsWith("oil1")) {
          this.FM.AS.hitOil(paramShot.initiator, 0);
        }
        return;
      }

      if (paramString.startsWith("xxmgun")) {
        this.FM.AS.setJamBullets(0, 0);
      }
      if (paramString.startsWith("xxcannon01")) {
        this.FM.AS.setJamBullets(1, 0);
        getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F), paramShot);
      }
      if (paramString.startsWith("xxcannon02")) {
        this.FM.AS.setJamBullets(1, 1);
        getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F), paramShot);
      }
      if (paramString.startsWith("xxammo")) {
        if (World.Rnd().nextFloat(3800.0F, 30000.0F) < paramShot.power) {
          if (paramString.endsWith("01")) {
            this.FM.AS.setJamBullets(1, 0);
          }
          if (paramString.endsWith("02")) {
            this.FM.AS.setJamBullets(1, 1);
          }
        }
        getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 28.33F), paramShot);
      }

      if (paramString.startsWith("xxoil")) {
        this.FM.AS.hitOil(paramShot.initiator, 0);
        getEnergyPastArmor(0.22F, paramShot);
      }

      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          if (this.FM.AS.astateTankStates[i] == 0) {
            this.FM.AS.hitTank(paramShot.initiator, i, 1);
            this.FM.AS.doSetTankState(paramShot.initiator, i, 1);
          }
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.5F)) {
            this.FM.AS.hitTank(paramShot.initiator, i, 2);
          }
        }
      }
      return;
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
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xrudder")) {
      hitChunk("Rudder1", paramShot);
    } else if (paramString.startsWith("xstabl")) {
      hitChunk("StabL", paramShot);
    } else if (paramString.startsWith("xstabr")) {
      hitChunk("StabR", paramShot);
    } else if (paramString.startsWith("xvatorl")) {
      hitChunk("VatorL", paramShot);
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
    } else if (paramString.startsWith("xengine")) {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xgearl")) {
      hitChunk("GearL2", paramShot);
    } else if (paramString.startsWith("xgearr")) {
      hitChunk("GearR2", paramShot);
    } else if (paramString.startsWith("xturret")) {
      this.FM.AS.setJamBullets(10, 0);
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

  static
  {
    Class localClass = AR_196.class;
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
  }
}