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
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class HE_111Z extends Scheme5
  implements TypeTransport
{
  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if ((paramString.endsWith("p1")) || (paramString.endsWith("p6"))) {
          if (v1.z > 0.5D)
            getEnergyPastArmor(5.0D / v1.z, paramShot);
          else if (v1.x > 0.9396926164627075D)
            getEnergyPastArmor(10.0D / v1.x * World.Rnd().nextFloat(1.0F, 1.2F), paramShot);
        }
        else if ((paramString.endsWith("p2")) || (paramString.endsWith("p7")))
          getEnergyPastArmor(5.0D / (Math.abs(v1.z) + 9.999999747378752E-005D), paramShot);
        else if ((paramString.endsWith("p3a")) || (paramString.endsWith("p3b")) || (paramString.endsWith("p8a")) || (paramString.endsWith("p8b")))
          getEnergyPastArmor(8.0D / (Math.abs(v1.x) * World.Rnd().nextFloat(1.0F, 1.2F) + 9.999999747378752E-005D), paramShot);
        else if ((paramString.endsWith("p4")) || (paramString.endsWith("p9"))) {
          if (v1.x > 0.7071067690849304D)
            getEnergyPastArmor(8.0D / (v1.x * World.Rnd().nextFloat(1.0F, 1.2F) + 0.001000000047497451D), paramShot);
          else if (v1.x > -0.7071067690849304D)
            getEnergyPastArmor(6.0F, paramShot);
        }
        else if ((paramString.endsWith("o1")) || (paramString.endsWith("o2")) || (paramString.endsWith("o3")) || (paramString.endsWith("o4")) || (paramString.endsWith("o5"))) {
          if (v1.x > 0.7071067690849304D)
            getEnergyPastArmor(8.0D / (v1.x * World.Rnd().nextFloat(1.0F, 1.2F) + 9.999999747378752E-005D), paramShot);
          else {
            getEnergyPastArmor(5.0F, paramShot);
          }
        }
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
        case 2:
        case 8:
          if (getEnergyPastArmor(1.0F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.12F) {
            debuggunnery("Controls: Evelator Controls Out..");
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          }
          if (World.Rnd().nextFloat() >= 0.12F) break;
          debuggunnery("Controls:  Rudder Controls Out..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2); break;
        case 3:
        case 4:
        case 7:
          if ((getEnergyPastArmor(1.0F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.25F)) break;
          debuggunnery("Controls: Ailerons Controls Out..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 5:
        case 6:
        }

        return;
      }
      if (paramString.startsWith("xxspar")) {
        if (((paramString.endsWith("ta1")) || (paramString.endsWith("ta2"))) && 
          (World.Rnd().nextFloat() < 0.1F) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(12.9F / (float)Math.sqrt(v1.y * v1.y + v1.z * v1.z), paramShot) > 0.0F)) {
          debuggunnery("Tail1 Spars Broken in Half..");
          nextDMGLevels(3, 2, "Tail1_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("ta3")) || (paramString.endsWith("ta4"))) && 
          (World.Rnd().nextFloat() < 0.1F) && (chunkDamageVisible("Tail2") > 2) && (getEnergyPastArmor(12.9F / (float)Math.sqrt(v1.y * v1.y + v1.z * v1.z), paramShot) > 0.0F)) {
          debuggunnery("Tail2 Spars Broken in Half..");
          nextDMGLevels(3, 2, "Tail2_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("li1")) || (paramString.endsWith("li2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("ri1")) || (paramString.endsWith("ri2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("lm1")) || (paramString.endsWith("lm2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.8600000143051148D * Math.abs(v1.x)) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("rm1")) || (paramString.endsWith("rm2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.8600000143051148D * Math.abs(v1.x)) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("lo1")) || (paramString.endsWith("lo2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(v1.x)) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("ro1")) || (paramString.endsWith("ro2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(v1.x)) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("k1")) || (paramString.endsWith("k2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(v1.x)) && (chunkDamageVisible("Keel1") > 1) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("Keel1 Spars Damaged..");
          nextDMGLevels(1, 2, "Keel1_D2", paramShot.initiator);
        }

        if (((paramString.endsWith("k3")) || (paramString.endsWith("k4"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(v1.x)) && (chunkDamageVisible("Keel1") > 1) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("Keel2 Spars Damaged..");
          nextDMGLevels(1, 2, "Keel2_D2", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxengine")) {
        i = paramString.charAt(8) - '1';
        if (paramString.endsWith("prop")) {
          if ((getEnergyPastArmor(2.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
            debuggunnery("Engine" + (i + 1) + " Governor Failed..");
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 3);
          }
        } else if (paramString.endsWith("base")) {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 200000.0F) {
              debuggunnery("Engine" + (i + 1) + " Crank Case Hit - Engine Stucks..");
              this.FM.AS.setEngineStuck(paramShot.initiator, i);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
              debuggunnery("Engine" + (i + 1) + " Crank Case Hit - Engine Damaged..");
              this.FM.AS.hitEngine(paramShot.initiator, i, 2);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 28000.0F) {
              this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, 1);
              debuggunnery("Engine" + (i + 1) + " Crank Case Hit - Cylinder Feed Out, " + this.FM.EI.engines[i].getCylindersOperable() + "/" + this.FM.EI.engines[i].getCylinders() + " Left..");
            }
            this.FM.EI.engines[i].setReadyness(paramShot.initiator, this.FM.EI.engines[i].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
            debuggunnery("Engine" + (i + 1) + " Crank Case Hit - Readyness Reduced to " + this.FM.EI.engines[i].getReadyness() + "..");
          }
        } else if (paramString.endsWith("cyls")) {
          if ((getEnergyPastArmor(1.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[i].getCylindersRatio() * 1.75F)) {
            this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
            debuggunnery("Engine" + (i + 1) + " Cylinders Hit, " + this.FM.EI.engines[i].getCylindersOperable() + "/" + this.FM.EI.engines[i].getCylinders() + " Left..");
            if (this.FM.AS.astateEngineStates[i] < 1) {
              this.FM.AS.hitEngine(paramShot.initiator, i, 1);
              this.FM.AS.doSetEngineState(paramShot.initiator, i, 1);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 24000.0F) {
              debuggunnery("Engine" + (i + 1) + " Cylinders Hit - Engine Fires..");
              this.FM.AS.hitEngine(paramShot.initiator, i, 3);
            }
            getEnergyPastArmor(25.0F, paramShot);
          }
        } else if (paramString.endsWith("supc")) {
          if ((getEnergyPastArmor(0.05F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.89F)) {
            debuggunnery("Engine" + (i + 1) + " Supercharger Out..");
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 0);
          }
        } else if ((paramString.endsWith("oil1")) && 
          (getEnergyPastArmor(0.21F, paramShot) > 0.0F)) {
          this.FM.AS.hitOil(paramShot.initiator, i);
          getEnergyPastArmor(0.42F, paramShot);
        }

        return;
      }
      if (paramString.startsWith("xxoil")) {
        return;
      }

      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if (getEnergyPastArmor(0.2F, paramShot) > 0.0F) {
          if (paramShot.power < 14100.0F) {
            if (this.FM.AS.astateTankStates[i] == 0) {
              this.FM.AS.hitTank(paramShot.initiator, i, 1);
              this.FM.AS.doSetTankState(paramShot.initiator, i, 1);
            }
            if ((this.FM.AS.astateTankStates[i] < 4) && (World.Rnd().nextFloat() < 0.03F)) {
              this.FM.AS.hitTank(paramShot.initiator, i, 1);
            }
            if ((paramShot.powerType == 3) && (this.FM.AS.astateTankStates[i] > 2) && (World.Rnd().nextFloat() < 0.03F))
              this.FM.AS.hitTank(paramShot.initiator, i, 10);
          }
          else {
            this.FM.AS.hitTank(paramShot.initiator, i, World.Rnd().nextInt(0, (int)(paramShot.power / 20000.0F)));
          }
        }
        return;
      }
      return;
    }

    if (paramString.startsWith("xcf")) {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
    }
    else if (paramString.startsWith("xnose")) {
      if (chunkDamageVisible("Nose") < 3)
        hitChunk("Nose", paramShot);
    }
    else if (paramString.startsWith("xtail1")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xtail2")) {
      if (chunkDamageVisible("Tail2") < 3)
        hitChunk("Tail2", paramShot);
    }
    else if (paramString.startsWith("xkeel1")) {
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xkeel2")) {
      if (chunkDamageVisible("Keel2") < 2)
        hitChunk("Keel2", paramShot);
    }
    else if (paramString.startsWith("xrudder1")) {
      hitChunk("Rudder1", paramShot);
    } else if (paramString.startsWith("xrudder2")) {
      hitChunk("Rudder2", paramShot);
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
    } else if (paramString.startsWith("xengine")) {
      i = paramString.charAt(7) - '1';
      if (chunkDamageVisible("Engine" + (i + 1)) < 2) {
        hitChunk("Engine" + (i + 1), paramShot);
      }
      this.FM.EI.engines[i].setReadyness(paramShot.initiator, this.FM.EI.engines[i].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 128000.9F));
      debuggunnery("Engine" + (i + 1) + " Hit - Readyness Reduced to " + this.FM.EI.engines[i].getReadyness() + "..");
    } else if (paramString.startsWith("xgear")) {
      if (World.Rnd().nextFloat() < 0.1F) {
        debuggunnery("Gear Hydro Failed..");
        this.FM.Gears.setHydroOperable(false);
      }
    } else if (paramString.startsWith("xturret")) {
      i = paramString.charAt(7) - '1';
      this.FM.AS.setJamBullets(10 + i, 0);
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

  public void hitProp(int paramInt1, int paramInt2, Actor paramActor)
  {
    super.hitProp(paramInt1, paramInt2, paramActor);
    if ((paramInt1 == 1) || (paramInt1 == 2))
      super.hitProp(4, paramInt2, paramActor);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean) {
      if (this.FM.AS.astateEngineStates[0] > 3) {
        if (World.Rnd().nextFloat() < 0.5F) this.FM.AS.hitTank(this, 0, 1);
        if (World.Rnd().nextFloat() < 0.5F) this.FM.AS.hitTank(this, 1, 1);
      }
      if (this.FM.AS.astateEngineStates[1] > 3) {
        if (World.Rnd().nextFloat() < 0.5F) this.FM.AS.hitTank(this, 2, 1);
        if (World.Rnd().nextFloat() < 0.5F) this.FM.AS.hitTank(this, 3, 1);
      }
      if ((this.FM.AS.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.07F)) nextDMGLevel(this.FM.AS.astateEffectChunks[0] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.07F)) nextDMGLevel(this.FM.AS.astateEffectChunks[1] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[2] > 4) && (World.Rnd().nextFloat() < 0.07F)) nextDMGLevel(this.FM.AS.astateEffectChunks[2] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[3] > 4) && (World.Rnd().nextFloat() < 0.07F)) nextDMGLevel(this.FM.AS.astateEffectChunks[3] + "0", 0, this);
    }

    for (int i = 1; i < 5; i++) {
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else {
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
      }
    }
    for (i = 7; i < 10; i++)
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = Math.max(-paramFloat * 1100.0F, -80.0F);

    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 60.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 0.0F, 95.0F * paramFloat);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -13.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, 36.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, -40.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, -f * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL8_D0", 0.0F, f * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL9_D0", 0.0F, -f * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL10_D0", 0.0F, f * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 0.0F, 95.0F * paramFloat);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -13.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, 36.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, -40.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, f * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR8_D0", 0.0F, -f * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR9_D0", 0.0F, f * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR10_D0", 0.0F, -f * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -40.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap1_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap2_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap3_D0", 0.0F, f, 0.0F);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -35.0F) { f1 = -35.0F; bool = false; }
      if (f1 > 15.0F) { f1 = 15.0F; bool = false; }
      if (f2 < -27.0F) { f2 = -27.0F; bool = false; }
      if (f2 <= 13.0F) break; f2 = 13.0F; bool = false; break;
    case 1:
      if (f1 < -42.0F) { f1 = -42.0F; bool = false; }
      if (f1 > 42.0F) { f1 = 42.0F; bool = false; }
      if (f2 < 0.0F) { f2 = 0.0F; bool = false; }
      if (f2 <= 60.0F) break; f2 = 60.0F; bool = false; break;
    case 2:
      if (f1 < -35.0F) { f1 = -35.0F; bool = false; }
      if (f1 > 40.0F) { f1 = 40.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 36.0F) break; f2 = 36.0F; bool = false; break;
    case 3:
      if (f1 < -55.0F) { f1 = -55.0F; bool = false; }
      if (f1 > 23.0F) { f1 = 23.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 45.0F) break; f2 = 45.0F; bool = false; break;
    case 4:
      if (f1 < -25.0F) { f1 = -25.0F; bool = false; }
      if (f1 > 15.0F) { f1 = 15.0F; bool = false; }
      if (f2 < -40.0F) { f2 = -40.0F; bool = false; }
      if (f2 <= 0.0F) break; f2 = 0.0F; bool = false; break;
    case 5:
      if (f1 < -42.0F) { f1 = -42.0F; bool = false; }
      if (f1 > 42.0F) { f1 = 42.0F; bool = false; }
      if (f2 < 0.0F) { f2 = 0.0F; bool = false; }
      if (f2 <= 60.0F) break; f2 = 60.0F; bool = false; break;
    case 6:
      if (f1 < -35.0F) { f1 = -35.0F; bool = false; }
      if (f1 > 40.0F) { f1 = 40.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 36.0F) break; f2 = 36.0F; bool = false; break;
    case 7:
      if (f1 < -23.0F) { f1 = -23.0F; bool = false; }
      if (f1 > 55.0F) { f1 = 55.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 45.0F) break; f2 = 45.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 37:
      hitProp(3, paramInt2, paramActor);
      super.cutFM(10, paramInt2, paramActor);
      break;
    case 11:
      hierMesh().chunkVisible("Wire_D0", false);
      break;
    case 19:
      hierMesh().chunkVisible("Wire_D0", false);
    case 20:
      cut("GearC2");
      break;
    case 33:
      hitProp(0, paramInt2, paramActor);
      hitProp(1, paramInt2, paramActor);
    case 36:
      hitProp(2, paramInt2, paramActor);
      hitProp(4, paramInt2, paramActor);
    case 13:
      killPilot(paramActor, 5);
      killPilot(paramActor, 6);
      killPilot(paramActor, 7);
      killPilot(paramActor, 8);
      super.cutFM(36, paramInt2, paramActor);
      super.cutFM(13, paramInt2, paramActor);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt) {
    case 1:
      this.FM.turret[0].setHealth(paramFloat);
      break;
    case 2:
      this.FM.turret[1].setHealth(paramFloat);
      break;
    case 3:
      this.FM.turret[2].setHealth(paramFloat);
      break;
    case 4:
      this.FM.turret[3].setHealth(paramFloat);
      break;
    case 6:
      this.FM.turret[4].setHealth(paramFloat);
      break;
    case 7:
      this.FM.turret[5].setHealth(paramFloat);
      break;
    case 8:
      this.FM.turret[6].setHealth(paramFloat);
      break;
    case 9:
      this.FM.turret[7].setHealth(paramFloat);
    case 5:
    }
  }

  public void doMurderPilot(int paramInt) {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("Head1_D0", false);
      if (!isChunkAnyDamageVisible("CF")) break;
      hierMesh().chunkVisible("Gore1_D0", true); break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      if (!isChunkAnyDamageVisible("CF")) break;
      hierMesh().chunkVisible("Gore2_D0", true); break;
    case 2:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("Pilot3_D1", true);
      if (!isChunkAnyDamageVisible("CF")) break;
      hierMesh().chunkVisible("Gore3_D0", true); break;
    case 3:
      hierMesh().chunkVisible("Pilot4_D0", false);
      hierMesh().chunkVisible("Pilot4_D1", true);
      break;
    case 5:
      if (!isChunkAnyDamageVisible("Nose")) break;
      hierMesh().chunkVisible("Gore4_D0", true); break;
    case 6:
      hierMesh().chunkVisible("Pilot7_D0", false);
      hierMesh().chunkVisible("Pilot7_D1", true);
      if (!isChunkAnyDamageVisible("Nose")) break;
      hierMesh().chunkVisible("Gore5_D0", true); break;
    case 7:
      hierMesh().chunkVisible("Pilot8_D0", false);
      hierMesh().chunkVisible("Pilot8_D1", true);
      if (!isChunkAnyDamageVisible("Nose")) break;
      hierMesh().chunkVisible("Gore6_D0", true); break;
    case 8:
      hierMesh().chunkVisible("Pilot9_D0", false);
      hierMesh().chunkVisible("Pilot9_D1", true);
    case 4:
    }
  }

  static
  {
    Class localClass = HE_111Z.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Zwilling");
    Property.set(localClass, "meshName", "3DO/Plane/He-111Z/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);

    Property.set(localClass, "yearService", 1941.9F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/He-111Z.fmd");

    weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 13, 14, 15, 16, 17 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG15t 150", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMGFFt 450", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null });
  }
}