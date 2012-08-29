package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public abstract class BLENHEIM extends Scheme2
  implements TypeBomber, TypeTransport
{
  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay1_D0", 0.0F, 0.0F, 90.0F * paramFloat);
    hierMesh().chunkSetAngles("Bay2_D0", 0.0F, 0.0F, -90.0F * paramFloat);
    hierMesh().chunkSetAngles("Bay3_D0", 0.0F, 0.0F, -90.0F * paramFloat);
    hierMesh().chunkSetAngles("Bay4_D0", 0.0F, 0.0F, 90.0F * paramFloat);
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if (Config.isUSE_RENDER())
    {
      if (World.cur().camouflage == 1) {
        hierMesh().chunkVisible("GearL1_D0", false);
        hierMesh().chunkVisible("GearL3_D0", false);
        hierMesh().chunkVisible("GearL4_D0", false);
        hierMesh().chunkVisible("GearL5_D0", false);
        hierMesh().chunkVisible("GearLS1_D0", true);
        hierMesh().chunkVisible("GearLS5_D0", true);
        hierMesh().chunkVisible("GearLS7_D0", true);
        hierMesh().chunkVisible("GearLS8_D0", true);
        hierMesh().chunkVisible("GearLS9_D0", true);
        hierMesh().chunkVisible("GearLS10_D0", true);
        hierMesh().chunkVisible("GearR1_D0", false);
        hierMesh().chunkVisible("GearR3_D0", false);
        hierMesh().chunkVisible("GearR4_D0", false);
        hierMesh().chunkVisible("GearR5_D0", false);
        hierMesh().chunkVisible("GearRS1_D0", true);
        hierMesh().chunkVisible("GearRS5_D0", true);
        hierMesh().chunkVisible("GearRS7_D0", true);
        hierMesh().chunkVisible("GearRS8_D0", true);
        hierMesh().chunkVisible("GearRS9_D0", true);
        hierMesh().chunkVisible("GearRS10_D0", true);
        hierMesh().chunkVisible("GearC1_D0", false);
        hierMesh().chunkVisible("GearCS1_D0", true);
        this.FM.CT.bHasBrakeControl = false;
      }
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, 10.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -150.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearLS1_D0", 0.0F, -100.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearLS8_D0", 0.0F, 80.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, 10.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -150.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearRS1_D0", 0.0F, -100.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearRS8_D0", 0.0F, 80.0F * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC2_D0", -paramFloat, 0.0F, 0.0F);
  }
  public void moveWheelSink() {
    resetYPRmodifier();
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.25F, 0.0F, 0.23F);
    hierMesh().chunkSetLocate("GearL99_D0", xyz, ypr);
    hierMesh().chunkSetAngles("GearLS7_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.25F, 0.0F, 15.0F), 0.0F);
    hierMesh().chunkSetAngles("GearLS8_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.25F, 80.0F, 57.0F), 0.0F);
    hierMesh().chunkSetAngles("GearLS9_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.25F, 0.0F, -8.0F), 0.0F);
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.25F, 0.0F, 0.2F);
    hierMesh().chunkSetLocate("GearLS10_D0", xyz, ypr);
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.25F, 0.0F, 0.23F);
    hierMesh().chunkSetLocate("GearR99_D0", xyz, ypr);
    hierMesh().chunkSetAngles("GearRS7_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.25F, 0.0F, 15.0F), 0.0F);
    hierMesh().chunkSetAngles("GearRS8_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.25F, 80.0F, 57.0F), 0.0F);
    hierMesh().chunkSetAngles("GearRS9_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.25F, 0.0F, -8.0F), 0.0F);
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.25F, 0.0F, 0.2F);
    hierMesh().chunkSetLocate("GearRS10_D0", xyz, ypr);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean) {
      if ((this.FM.AS.astateEngineStates[0] > 3) && 
        (World.Rnd().nextFloat() < 0.39F)) this.FM.AS.hitTank(this, 0, 1);

      if ((this.FM.AS.astateEngineStates[1] > 3) && 
        (World.Rnd().nextFloat() < 0.39F)) this.FM.AS.hitTank(this, 1, 1);

      if ((this.FM.AS.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.035F)) nextDMGLevel(this.FM.AS.astateEffectChunks[0] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.035F)) nextDMGLevel(this.FM.AS.astateEffectChunks[1] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[2] > 4) && (World.Rnd().nextFloat() < 0.035F)) nextDMGLevel(this.FM.AS.astateEffectChunks[2] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[3] > 4) && (World.Rnd().nextFloat() < 0.035F)) nextDMGLevel(this.FM.AS.astateEffectChunks[3] + "0", 0, this);
    }

    for (int i = 1; i < 4; i++)
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt)
    {
    case 0:
      if (f2 < -5.0F) { f2 = -5.0F; bool = false; }
      if (f2 <= 45.0F) break; f2 = 45.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 33:
      hitProp(0, paramInt2, paramActor);
      return super.cutFM(34, paramInt2, paramActor);
    case 36:
      hitProp(1, paramInt2, paramActor);
      return super.cutFM(37, paramInt2, paramActor);
    case 13:
      killPilot(this, 0);
      killPilot(this, 1);
      return false;
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    int j;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("p1"))
          getEnergyPastArmor(5.0F, paramShot);
        else if (paramString.endsWith("p2"))
          getEnergyPastArmor(5.0F, paramShot);
        else if (paramString.endsWith("p3"))
          getEnergyPastArmor(5.0F, paramShot);
        else if ((paramString.endsWith("e1")) || (paramString.endsWith("e2"))) {
          getEnergyPastArmor(5.0F, paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
          if ((World.Rnd().nextFloat() >= 0.1F) && (paramShot.mass <= 0.092F)) break;
          if (World.Rnd().nextFloat() < 0.1F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          }
          if (World.Rnd().nextFloat() >= 0.5F) break;
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
        case 2:
          if ((World.Rnd().nextFloat() < 0.1F) || (paramShot.mass > 0.092F)) {
            if (World.Rnd().nextFloat() < 0.1F) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 1);
            }
            if (World.Rnd().nextFloat() < 0.5F) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 6);
            }
          }
        case 3:
        case 4:
          if (getEnergyPastArmor(1.0F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < 0.12F) {
              this.FM.AS.setControlsDamage(paramShot.initiator, 1);
              debugprintln(this, "*** Evelator Controls Out..");
            }
            if (World.Rnd().nextFloat() < 0.12F) {
              this.FM.AS.setControlsDamage(paramShot.initiator, 2);
              debugprintln(this, "*** Rudder Controls Out.."); } 
          }break;
        case 5:
        case 6:
          if ((getEnergyPastArmor(0.2F, paramShot) > 0.0F) && 
            (World.Rnd().nextFloat() < 0.5F)) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 0);
            debugprintln(this, "*** Aileron Controls Out..");
          }
        }

      }

      if (paramString.startsWith("xxspar")) {
        if (((paramString.endsWith("li1")) || (paramString.endsWith("li2"))) && 
          (World.Rnd().nextFloat() < cvt((float)Math.abs(v1.x), 0.0F, 1.0F, 1.0F, 0.08F)) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("ri1")) || (paramString.endsWith("ri2"))) && 
          (World.Rnd().nextFloat() < cvt((float)Math.abs(v1.x), 0.0F, 1.0F, 1.0F, 0.08F)) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("lm1")) || (paramString.endsWith("lm2"))) && 
          (World.Rnd().nextFloat() < cvt((float)Math.abs(v1.x), 0.0F, 1.0F, 1.0F, 0.08F)) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("rm1")) || (paramString.endsWith("rm2"))) && 
          (World.Rnd().nextFloat() < cvt((float)Math.abs(v1.x), 0.0F, 1.0F, 1.0F, 0.08F)) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("lo1")) || (paramString.endsWith("lo2"))) && 
          (World.Rnd().nextFloat() < cvt((float)Math.abs(v1.x), 0.0F, 1.0F, 1.0F, 0.33F)) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("ro1")) || (paramString.endsWith("ro2"))) && 
          (World.Rnd().nextFloat() < cvt((float)Math.abs(v1.x), 0.0F, 1.0F, 1.0F, 0.33F)) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("k1")) || (paramString.endsWith("k2"))) && 
          (World.Rnd().nextFloat() < cvt((float)Math.abs(v1.x), 0.0F, 1.0F, 1.0F, 0.26F)) && (chunkDamageVisible("Keel1") > 1) && (getEnergyPastArmor(8.6F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** Keel1 Spars Damaged..");
          nextDMGLevels(1, 2, "Keel1_D2", paramShot.initiator);
        }
      }

      if (paramString.startsWith("xxstruts")) {
        if ((paramString.endsWith("1")) && 
          (World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(26.0F, paramShot) > 0.0F)) {
          debugprintln(this, "*** Engine1 Suspension Broken in Half..");
          nextDMGLevels(3, 2, "Engine1_D0", paramShot.initiator);
        }

        if ((paramString.endsWith("2")) && 
          (World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(26.0F, paramShot) > 0.0F)) {
          debugprintln(this, "*** Engine2 Suspension Broken in Half..");
          nextDMGLevels(3, 2, "Engine2_D0", paramShot.initiator);
        }
      }

      if ((paramString.startsWith("xxbomb")) && 
        (World.Rnd().nextFloat() < 0.01F) && (this.FM.CT.Weapons[3] != null) && (this.FM.CT.Weapons[3][0].haveBullets())) {
        debugprintln(this, "*** Bomb Payload Detonates..");
        this.FM.AS.hitTank(paramShot.initiator, 0, 10);
        this.FM.AS.hitTank(paramShot.initiator, 1, 10);
        this.FM.AS.hitTank(paramShot.initiator, 2, 10);
        this.FM.AS.hitTank(paramShot.initiator, 3, 10);
        nextDMGLevels(3, 2, "CF_D0", paramShot.initiator);
      }

      if (paramString.startsWith("xxprop")) {
        i = 0;
        if (paramString.endsWith("2")) {
          i = 1;
        }
        this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 4);
        debugprintln(this, "*** Engine" + (i + 1) + " Governor Damaged..");
        if ((getEnergyPastArmor(2.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 3);
          debugprintln(this, "*** Engine" + (i + 1) + " Governor Failed..");
        }
      }
      if (paramString.startsWith("xxengine")) {
        i = 0;
        if (paramString.startsWith("xxengine2")) {
          i = 1;
        }
        if ((getEnergyPastArmor(World.Rnd().nextFloat(0.1F, 4.3F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[i].getCylindersRatio() * 1.12F)) {
          this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 9124.0F)));
          debugprintln(this, "*** Engine" + (i + 1) + " Cylindres Damaged, " + this.FM.EI.engines[i].getCylindersOperable() + "/" + this.FM.EI.engines[i].getCylinders() + " left..");
        }
        if (World.Rnd().nextFloat(0.0F, 18000.0F) < paramShot.power) {
          this.FM.AS.hitEngine(paramShot.initiator, i, 1);
        }
        this.FM.AS.hitOil(paramShot.initiator, i);
      }
      if (paramString.startsWith("xxoil")) {
        i = 0;
        if (paramString.startsWith("xxoil2")) {
          i = 1;
        }
        this.FM.AS.hitOil(paramShot.initiator, i);
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if ((i < 4) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
          if (paramShot.power < 14100.0F) {
            if (this.FM.AS.astateTankStates[i] < 1) {
              this.FM.AS.hitTank(paramShot.initiator, i, 2);
            }
            if ((this.FM.AS.astateTankStates[i] < 4) && (World.Rnd().nextFloat() < 0.1F)) {
              this.FM.AS.hitTank(paramShot.initiator, i, 1);
            }
            if ((paramShot.powerType == 3) && (this.FM.AS.astateTankStates[i] > 2) && (World.Rnd().nextFloat() < 0.07F))
              this.FM.AS.hitTank(paramShot.initiator, i, 10);
          }
          else {
            this.FM.AS.hitTank(paramShot.initiator, i, World.Rnd().nextInt(0, (int)(paramShot.power / 20000.0F)));
          }
        }
        if ((i == 4) && 
          (getEnergyPastArmor(1.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.06F)) {
          for (j = 0; j < 4; j++) {
            this.FM.AS.hitOil(paramShot.initiator, j);
          }
        }
      }

      if ((paramString.startsWith("xxammo")) && 
        (World.Rnd().nextFloat() < 0.01F)) {
        this.FM.AS.hitTank(paramShot.initiator, 2, 10);
        this.FM.AS.explodeTank(paramShot.initiator, 2);
      }

      if (paramString.startsWith("xxmgun1")) {
        this.FM.AS.setJamBullets(0, 0);
      }
      return;
    }

    if (paramString.startsWith("xcf")) {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
    }
    else if (paramString.startsWith("xnose")) {
      hitChunk("Nose1", paramShot);
      if (paramShot.power > 33000.0F) {
        this.FM.AS.hitPilot(paramShot.initiator, 0, World.Rnd().nextInt(30, 192));
        this.FM.AS.hitPilot(paramShot.initiator, 1, World.Rnd().nextInt(30, 192));
      }

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
      if (chunkDamageVisible("Engine1") < 2) {
        hitChunk("Engine1", paramShot);
      }
      this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 96000.0F));
      debugprintln(this, "*** Engine1 Hit - Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
    } else if (paramString.startsWith("xengine2")) {
      if (chunkDamageVisible("Engine2") < 2) {
        hitChunk("Engine2", paramShot);
      }
      this.FM.EI.engines[1].setReadyness(paramShot.initiator, this.FM.EI.engines[1].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 96000.0F));
      debugprintln(this, "*** Engine2 Hit - Readyness Reduced to " + this.FM.EI.engines[1].getReadyness() + "..");
    } else if (paramString.startsWith("xgear")) {
      if (World.Rnd().nextFloat() < 0.1F) {
        debugprintln(this, "*** Gear Actuator Failed..");
        this.FM.AS.setInternalDamage(paramShot.initiator, 3);
      }
    } else if (paramString.startsWith("xturret")) {
      if (paramString.startsWith("xturret1"))
        this.FM.AS.setJamBullets(10, 0);
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

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt) {
    case 2:
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
      break;
    case 2:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("Pilot3_D1", true);
      hierMesh().chunkVisible("HMask3_D0", false);
    }
  }

  public boolean typeBomberToggleAutomation()
  {
    return false;
  }

  public void typeBomberAdjDistanceReset()
  {
  }

  public void typeBomberAdjDistancePlus()
  {
  }

  public void typeBomberAdjDistanceMinus()
  {
  }

  public void typeBomberAdjSideslipReset()
  {
  }

  public void typeBomberAdjSideslipPlus()
  {
  }

  public void typeBomberAdjSideslipMinus()
  {
  }

  public void typeBomberAdjAltitudeReset()
  {
  }

  public void typeBomberAdjAltitudePlus()
  {
  }

  public void typeBomberAdjAltitudeMinus()
  {
  }

  public void typeBomberAdjSpeedReset()
  {
  }

  public void typeBomberAdjSpeedPlus()
  {
  }

  public void typeBomberAdjSpeedMinus()
  {
  }

  public void typeBomberUpdate(float paramFloat)
  {
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException
  {
  }

  static
  {
    Class localClass = BLENHEIM.class;
    Property.set(localClass, "originCountry", PaintScheme.countryBritain);
  }
}