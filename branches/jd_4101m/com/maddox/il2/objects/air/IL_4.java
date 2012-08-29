package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;

public abstract class IL_4 extends Scheme2
{
  private long tme = 0L;
  private int[] radist = { 0, 0, 0 };

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = Math.max(-paramFloat * 1100.0F, -75.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 0.0F, 100.0F * paramFloat);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -20.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL8_D0", 0.0F, -125.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 0.0F, 100.0F * paramFloat);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -20.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR8_D0", 0.0F, -125.0F * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC2_D0", paramFloat, 0.0F, 0.0F);
  }

  public void moveWheelSink()
  {
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (paramBoolean) {
      if (this.FM.AS.astateEngineStates[0] > 3) {
        if (World.Rnd().nextFloat() < 0.03F) this.FM.AS.hitTank(this, 0, 1);
        if (World.Rnd().nextFloat() < 0.03F) this.FM.AS.hitTank(this, 1, 1);
      }
      if (this.FM.AS.astateEngineStates[1] > 3) {
        if (World.Rnd().nextFloat() < 0.03F) this.FM.AS.hitTank(this, 2, 1);
        if (World.Rnd().nextFloat() < 0.03F) this.FM.AS.hitTank(this, 3, 1);
      }
      if ((this.FM.AS.astateTankStates[0] > 5) && (World.Rnd().nextFloat() < 0.03F)) nextDMGLevel(this.FM.AS.astateEffectChunks[0] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[1] > 5) && (World.Rnd().nextFloat() < 0.03F)) nextDMGLevel(this.FM.AS.astateEffectChunks[1] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[1] > 5) && (World.Rnd().nextFloat() < 0.02F)) this.FM.AS.hitTank(this, 2, 1);
      if ((this.FM.AS.astateTankStates[2] > 5) && (World.Rnd().nextFloat() < 0.02F)) this.FM.AS.hitTank(this, 1, 1);
      if ((this.FM.AS.astateTankStates[2] > 5) && (World.Rnd().nextFloat() < 0.03F)) nextDMGLevel(this.FM.AS.astateEffectChunks[2] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[3] > 5) && (World.Rnd().nextFloat() < 0.03F)) nextDMGLevel(this.FM.AS.astateEffectChunks[3] + "0", 0, this);
    }

    if (this.FM.getAltitude() < 3000.0F) {
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("HMask2a_D0", false);
      hierMesh().chunkVisible("HMask3_D0", false);
      hierMesh().chunkVisible("HMask3a_D0", false);
    } else {
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
      hierMesh().chunkVisible("HMask2_D0", hierMesh().isChunkVisible("Pilot2_D0"));
      hierMesh().chunkVisible("HMask2a_D0", hierMesh().isChunkVisible("Pilot2a_D0"));
      hierMesh().chunkVisible("HMask3_D0", hierMesh().isChunkVisible("Pilot3_D0"));
      hierMesh().chunkVisible("HMask3a_D0", hierMesh().isChunkVisible("Pilot3a_D0"));
    }
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -52.0F) { f1 = -52.0F; bool = false; }
      if (f1 > 52.0F) { f1 = 52.0F; bool = false; }
      if (f2 < -60.0F) { f2 = -60.0F; bool = false; }
      if (f2 <= 90.0F) break; f2 = 90.0F; bool = false; break;
    case 1:
      if ((f1 < 2.0F) && (f1 > -2.0F) && (f2 < 25.0F)) bool = false;
      if (f2 < -10.0F) { f2 = -10.0F; bool = false; }
      if (f2 <= 99.0F) break; f2 = 99.0F; bool = false; break;
    case 2:
      if (f1 < -25.0F) { f1 = -25.0F; bool = false; }
      if (f1 > 25.0F) { f1 = 25.0F; bool = false; }
      if (f2 < -60.0F) { f2 = -60.0F; bool = false; }
      if (f2 <= 4.0F) break; f2 = 4.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 33:
      hitProp(0, paramInt2, paramActor);
      break;
    case 36:
      hitProp(1, paramInt2, paramActor);
      break;
    case 34:
      this.FM.AS.hitEngine(this, 0, 2);
      if (World.Rnd().nextInt(0, 99) >= 66) break; this.FM.AS.hitEngine(this, 0, 2); break;
    case 37:
      this.FM.AS.hitEngine(this, 1, 2);
      if (World.Rnd().nextInt(0, 99) >= 66) break; this.FM.AS.hitEngine(this, 1, 2);
    case 35:
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("p1"))
          getEnergyPastArmor(World.Rnd().nextFloat(12.5F, 16.0F), paramShot);
        else if (paramString.endsWith("p2"))
          getEnergyPastArmor(1.009999990463257D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        else if (paramString.endsWith("p3"))
          getEnergyPastArmor(World.Rnd().nextFloat(12.5F, 16.0F), paramShot);
        else if (paramString.endsWith("p4")) {
          getEnergyPastArmor(World.Rnd().nextFloat(12.5F, 16.0F), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        i = paramString.charAt(10) - '0';
        if (paramString.endsWith("10"))
          i = 10;
        else if (paramString.endsWith("11"))
          i = 11;
        else if (paramString.endsWith("12")) {
          i = 12;
        }
        switch (i) {
        case 1:
          if (getEnergyPastArmor(2.2F, paramShot) <= 0.0F) break;
          debuggunnery("*** Control Column: Hit, Controls Destroyed..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 2:
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
          break;
        case 3:
          if (getEnergyPastArmor(2.2F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.1F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          }
          if (World.Rnd().nextFloat() < 0.1F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          }
          if (World.Rnd().nextFloat() >= 0.1F) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 4:
          if (getEnergyPastArmor(2.2F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.1F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          }
          if (World.Rnd().nextFloat() < 0.1F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
          }
          if (World.Rnd().nextFloat() >= 0.1F) break;
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 7); break;
        case 5:
          if (getEnergyPastArmor(2.2F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.1F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 1);
          }
          if (World.Rnd().nextFloat() < 0.1F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 6);
          }
          if (World.Rnd().nextFloat() >= 0.1F) break;
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 7); break;
        case 6:
        case 7:
          if ((getEnergyPastArmor(0.12F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.5F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          debuggunnery("*** Aileron Controls: Disabled.."); break;
        case 8:
          if (getEnergyPastArmor(0.12F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.25F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          }
          if (World.Rnd().nextFloat() < 0.25F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
          }
          if (World.Rnd().nextFloat() >= 0.25F) break;
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 7); break;
        case 9:
          if (getEnergyPastArmor(0.12F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.25F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 1);
          }
          if (World.Rnd().nextFloat() < 0.25F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 6);
          }
          if (World.Rnd().nextFloat() >= 0.25F) break;
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 7); break;
        case 10:
        case 11:
          if (getEnergyPastArmor(0.002F, paramShot) <= 0.0F) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          debuggunnery("*** Elevator Controls: Disabled / Strings Broken.."); break;
        case 12:
          if (getEnergyPastArmor(2.3F, paramShot) <= 0.0F) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          debuggunnery("*** Rudder Controls: Disabled..");
        }

        return;
      }
      if (paramString.startsWith("xxspar")) {
        if ((paramString.startsWith("xxsparli")) && 
          (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(26.799999237060547D / (1.000100016593933D - Math.abs(v1.y)), paramShot) > 0.0F)) {
          debuggunnery("*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparri")) && 
          (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(26.799999237060547D / (1.000100016593933D - Math.abs(v1.y)), paramShot) > 0.0F)) {
          debuggunnery("*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlm")) && 
          (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(26.799999237060547D / (1.000100016593933D - Math.abs(v1.y)), paramShot) > 0.0F)) {
          debuggunnery("*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparrm")) && 
          (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(26.799999237060547D / (1.000100016593933D - Math.abs(v1.y)), paramShot) > 0.0F)) {
          debuggunnery("*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlo")) && 
          (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(26.799999237060547D / (1.000100016593933D - Math.abs(v1.y)), paramShot) > 0.0F)) {
          debuggunnery("*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparro")) && 
          (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(26.799999237060547D / (1.000100016593933D - Math.abs(v1.y)), paramShot) > 0.0F)) {
          debuggunnery("*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("e1")) || (paramString.endsWith("e2"))) && 
          (getEnergyPastArmor(32.0F, paramShot) > 0.0F)) {
          debuggunnery("*** Engine1 Suspension Broken in Half..");
          nextDMGLevels(3, 2, "Engine1_D0", paramShot.initiator);
        }

        if (((paramString.endsWith("e3")) || (paramString.endsWith("e4"))) && 
          (getEnergyPastArmor(32.0F, paramShot) > 0.0F)) {
          debuggunnery("*** Engine2 Suspension Broken in Half..");
          nextDMGLevels(3, 2, "Engine2_D0", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxlock")) {
        if (paramString.startsWith("xxlockal")) {
          if (getEnergyPastArmor(4.35F, paramShot) > 0.0F) {
            debuggunnery("*** AroneL Lock Damaged..");
            nextDMGLevels(1, 2, "AroneL_D0", paramShot.initiator);
          }
        } else if (paramString.startsWith("xxlockar")) {
          if (getEnergyPastArmor(4.35F, paramShot) > 0.0F) {
            debuggunnery("*** AroneR Lock Damaged..");
            nextDMGLevels(1, 2, "AroneR_D0", paramShot.initiator);
          }
        } else if (paramString.startsWith("xxlockvl")) {
          if (getEnergyPastArmor(4.32F, paramShot) > 0.0F) {
            debuggunnery("*** VatorL Lock Damaged..");
            nextDMGLevels(1, 2, "VatorL_D0", paramShot.initiator);
          }
        } else if (paramString.startsWith("xxlockvr")) {
          if (getEnergyPastArmor(4.32F, paramShot) > 0.0F) {
            debuggunnery("*** VatorR Lock Damaged..");
            nextDMGLevels(1, 2, "VatorR_D0", paramShot.initiator);
          }
        } else if ((paramString.startsWith("xxlockr")) && 
          (getEnergyPastArmor(4.32F, paramShot) > 0.0F)) {
          debuggunnery("*** Rudder1 Lock Damaged..");
          nextDMGLevels(1, 2, "Rudder1_D0", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxbomb")) {
        if ((World.Rnd().nextFloat() < 0.01F) && (this.FM.CT.Weapons[3] != null) && (this.FM.CT.Weapons[3][0].haveBullets())) {
          debuggunnery("*** Bomb Payload Detonates.. CF_D" + chunkDamageVisible("CF"));
          this.FM.AS.hitTank(paramShot.initiator, 0, 100);
          this.FM.AS.hitTank(paramShot.initiator, 1, 100);
          this.FM.AS.hitTank(paramShot.initiator, 2, 100);
          this.FM.AS.hitTank(paramShot.initiator, 3, 100);
          nextDMGLevels(3, 2, "CF_D" + chunkDamageVisible("CF"), paramShot.initiator);
        }
        return;
      }
      if (paramString.startsWith("xxeng")) {
        i = paramString.charAt(5) - '1';
        if ((paramString.endsWith("prop")) && 
          (getEnergyPastArmor(1.2F, paramShot) > 0.0F)) {
          this.FM.EI.engines[i].setKillPropAngleDevice(paramShot.initiator);
        }

        if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(1.7F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 140000.0F) {
              this.FM.AS.setEngineStuck(paramShot.initiator, i);
              debuggunnery("*** Engine" + i + " Crank Case Hit - Engine Stucks..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, i, 2);
              debuggunnery("*** Engine" + i + " Crank Case Hit - Engine Damaged..");
            }
          }
          else if (World.Rnd().nextFloat() < 0.04F) {
            this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, 1);
          } else {
            this.FM.EI.engines[i].setReadyness(paramShot.initiator, this.FM.EI.engines[i].getReadyness() - 0.02F);
            debuggunnery("*** Engine" + i + " Crank Case Hit - Readyness Reduced to " + this.FM.EI.engines[i].getReadyness() + "..");
          }

          getEnergyPastArmor(12.0F, paramShot);
        }
        if (paramString.endsWith("cyls")) {
          if ((getEnergyPastArmor(0.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[i].getCylindersRatio() * 0.9878F)) {
            this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 19000.0F)));
            debuggunnery("*** Engine" + i + " Cylinders Hit, " + this.FM.EI.engines[i].getCylindersOperable() + "/" + this.FM.EI.engines[i].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 48000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
              debuggunnery("*** Engine Cylinders Hit - Engine Fires..");
            }
          }
          getEnergyPastArmor(25.0F, paramShot);
        }
        if (paramString.endsWith("supc")) {
          if (getEnergyPastArmor(0.05F, paramShot) > 0.0F) {
            this.FM.EI.engines[i].setKillCompressor(paramShot.initiator);
          }
          getEnergyPastArmor(2.0F, paramShot);
        }
        if (paramString.endsWith("eqpt")) {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F) {
            if ((Pd.y > 0.0D) && (Pd.z < 0.1889999955892563D) && (World.Rnd().nextFloat(0.0F, 16000.0F) < paramShot.power)) {
              this.FM.EI.engines[i].setMagnetoKnockOut(paramShot.initiator, 0);
            }
            if ((Pd.y < 0.0D) && (Pd.z < 0.1889999955892563D) && (World.Rnd().nextFloat(0.0F, 16000.0F) < paramShot.power)) {
              this.FM.EI.engines[i].setMagnetoKnockOut(paramShot.initiator, 1);
            }
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 4);
            }
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 0);
            }
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 6);
            }
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 1);
            }
          }
          getEnergyPastArmor(2.0F, paramShot);
        }
        if (paramString.endsWith("mag1")) {
          this.FM.EI.engines[i].setMagnetoKnockOut(paramShot.initiator, 0);
          debuggunnery("*** Engine" + i + " Magneto 1 Destroyed..");
        }
        if (paramString.endsWith("mag2")) {
          this.FM.EI.engines[i].setMagnetoKnockOut(paramShot.initiator, 1);
          debuggunnery("*** Engine" + i + " Magneto 2 Destroyed..");
        }
        return;
      }
      if (paramString.startsWith("xxoil")) {
        i = paramString.charAt(5) - '1';
        if (getEnergyPastArmor(4.21F, paramShot) > 0.0F) {
          this.FM.AS.hitOil(paramShot.initiator, i);
          getEnergyPastArmor(0.42F, paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if (getEnergyPastArmor(0.6F, paramShot) > 0.0F) {
          if (this.FM.AS.astateTankStates[i] == 0) {
            this.FM.AS.hitTank(paramShot.initiator, i, 1);
            this.FM.AS.doSetTankState(paramShot.initiator, i, 1);
          }
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.6F)) {
            this.FM.AS.hitTank(paramShot.initiator, i, 2);
          }
        }
        return;
      }
      if (paramString.startsWith("xxammo")) {
        if (World.Rnd().nextFloat() < 0.1F) {
          this.FM.AS.setJamBullets(10, 0);
        }
        return;
      }
      if (paramString.startsWith("xxpnm")) {
        if (getEnergyPastArmor(World.Rnd().nextFloat(0.25F, 12.39F), paramShot) > 0.0F) {
          debuggunnery("Pneumo System: Disabled..");
          this.FM.AS.setInternalDamage(paramShot.initiator, 1);
        }
        return;
      }
      return;
    }

    if ((!paramString.startsWith("xcockpit")) || 
      (paramString.startsWith("xcf"))) {
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
      if (chunkDamageVisible("Rudder1") < 2)
        hitChunk("Rudder1", paramShot);
    }
    else if (paramString.startsWith("xstabl")) {
      hitChunk("StabL", paramShot);
    } else if (paramString.startsWith("xstabr")) {
      hitChunk("StabR", paramShot);
    } else if (paramString.startsWith("xvatorl")) {
      if (chunkDamageVisible("VatorL") < 2)
        hitChunk("VatorL", paramShot);
    }
    else if (paramString.startsWith("xvatorr")) {
      if (chunkDamageVisible("VatorR") < 2)
        hitChunk("VatorR", paramShot);
    }
    else if (paramString.startsWith("xwinglin")) {
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
      if (chunkDamageVisible("AroneL") < 2)
        hitChunk("AroneL", paramShot);
    }
    else if (paramString.startsWith("xaroner")) {
      if (chunkDamageVisible("AroneR") < 2)
        hitChunk("AroneR", paramShot);
    }
    else if (paramString.startsWith("xengine1")) {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xengine2")) {
      if (chunkDamageVisible("Engine2") < 2)
        hitChunk("Engine2", paramShot);
    }
    else if (paramString.startsWith("xgear")) {
      if ((paramString.endsWith("1")) && 
        (World.Rnd().nextFloat() < 0.05F)) {
        debuggunnery("Hydro System: Disabled..");
        this.FM.AS.setInternalDamage(paramShot.initiator, 0);
      }

      if ((paramString.endsWith("2")) && 
        (World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(12.88F, 16.959999F), paramShot) > 0.0F)) {
        debuggunnery("Undercarriage: Stuck..");
        this.FM.AS.setInternalDamage(paramShot.initiator, 3);
      }
    }
    else if (paramString.startsWith("xturret")) {
      if (paramString.startsWith("xturret1")) {
        debuggunnery("Armament System: Turret Machine Gun(s): Disabled..");
        this.FM.AS.setJamBullets(10, 0);
        getEnergyPastArmor(World.Rnd().nextFloat(0.1F, 66.349998F), paramShot);
      }
      if (paramString.startsWith("xturret2")) {
        debuggunnery("Armament System: Turret Machine Gun(s): Disabled..");
        this.FM.AS.setJamBullets(11, 0);
        getEnergyPastArmor(World.Rnd().nextFloat(0.1F, 66.349998F), paramShot);
      }
      if (paramString.startsWith("xturret3")) {
        debuggunnery("Armament System: Turret Machine Gun(s): Disabled..");
        this.FM.AS.setJamBullets(12, 0);
        getEnergyPastArmor(World.Rnd().nextFloat(0.1F, 66.349998F), paramShot);
      }
    } else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
      i = 0;
      int j;
      if ((paramString.endsWith("a")) || (paramString.endsWith("a2"))) {
        i = 1;
        j = paramString.charAt(6) - '1';
      } else if ((paramString.endsWith("b")) || (paramString.endsWith("b2"))) {
        i = 2;
        j = paramString.charAt(6) - '1';
      } else {
        j = paramString.charAt(5) - '1';
      }
      hitFlesh(j, paramShot, i);
    }
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    setExplosion(paramExplosion);
    if ((paramExplosion.chunkName != null) && (paramExplosion.power > 0.0F)) {
      if (paramExplosion.chunkName.equals("Tail1_D3")) {
        return;
      }
      if (paramExplosion.chunkName.equals("WingLIn_D3")) {
        return;
      }
      if (paramExplosion.chunkName.equals("WingRIn_D3")) {
        return;
      }
      if (paramExplosion.chunkName.equals("WingLMid_D3")) {
        return;
      }
      if (paramExplosion.chunkName.equals("WingRMid_D3")) {
        return;
      }
      if (paramExplosion.chunkName.equals("WingLOut_D3")) {
        return;
      }
      if (paramExplosion.chunkName.equals("WingROut_D3")) {
        return;
      }
      if (paramExplosion.chunkName.equals("Engine1_D2")) {
        return;
      }
      if (paramExplosion.chunkName.equals("Engine2_D2")) {
        return;
      }
    }
    super.msgExplosion(paramExplosion);
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt) {
    case 0:
      break;
    case 1:
      this.FM.turret[0].setHealth(paramFloat);
      break;
    case 2:
      this.FM.turret[1].setHealth(paramFloat);
      this.FM.turret[2].setHealth(paramFloat);
    }
  }

  public void doMurderPilot(int paramInt) {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      break;
    case 1:
      if (hierMesh().isChunkVisible("Pilot2_D0")) {
        hierMesh().chunkVisible("Pilot2_D0", false);
        hierMesh().chunkVisible("Pilot2_D1", true);
      } else {
        hierMesh().chunkVisible("Pilot2a_D0", false);
        hierMesh().chunkVisible("Pilot2a_D0", true);
      }
      break;
    case 2:
      if (hierMesh().isChunkVisible("Pilot3_D0")) {
        hierMesh().chunkVisible("Pilot3_D0", false);
        hierMesh().chunkVisible("Pilot3_D1", true);
      } else {
        hierMesh().chunkVisible("Pilot3a_D0", false);
        hierMesh().chunkVisible("Pilot3a_D0", true);
      }
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

  public void typeBomberAdjSideslipPlus() {
  }

  public void typeBomberAdjSideslipMinus() {
  }

  public void typeBomberAdjAltitudeReset() {
  }

  public void typeBomberAdjAltitudePlus() {
  }

  public void typeBomberAdjAltitudeMinus() {
  }

  public void typeBomberAdjSpeedReset() {
  }

  public void typeBomberAdjSpeedPlus() {
  }

  public void typeBomberAdjSpeedMinus() {
  }

  public void typeBomberUpdate(float paramFloat) {
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
  }

  protected void moveBayDoor(float paramFloat) {
    hierMesh().chunkSetAngles("Bay1_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    setRadist(0, 0);
    setRadist(1, 0);
    setRadist(2, 0);
    hierMesh().chunkVisible("Turret3a_D0", false);
  }

  public void update(float paramFloat)
  {
    if (Time.current() > this.tme) {
      this.tme = (Time.current() + World.Rnd().nextLong(1000L, 5000L));
      if (this.FM.turret.length != 0) {
        Actor localActor = null;
        if (this.FM.turret[0].bIsOperable != (this.radist[1] == 0)) {
          localActor = this.FM.turret[0].target;
          if (localActor != null) {
            setRadist(1, 1);
          } else {
            localActor = this.FM.turret[1].target;
            if (localActor == null) {
              localActor = this.FM.turret[2].target;
            }
            if (localActor != null) {
              setRadist(1, 1);
              this.FM.turret[0].target = localActor;
            } else {
              setRadist(1, 0);
            }
          }
        }
        if (this.FM.turret[1].bIsOperable) {
          localActor = this.FM.turret[1].target;
          if ((localActor != null) && 
            (Actor.isValid(localActor))) {
            this.pos.getAbs(tmpLoc2);
            localActor.pos.getAbs(tmpLoc3);
            tmpLoc2.transformInv(tmpLoc3.getPoint());
            if (tmpLoc3.getPoint().z < 0.0D) {
              setRadist(2, 1);
            }
          }
        }
        else if (this.FM.turret[2].bIsOperable) {
          localActor = this.FM.turret[2].target;
          if ((localActor != null) && 
            (Actor.isValid(localActor))) {
            this.pos.getAbs(tmpLoc2);
            localActor.pos.getAbs(tmpLoc3);
            tmpLoc2.transformInv(tmpLoc3.getPoint());
            if (tmpLoc3.getPoint().z > 0.0D) {
              setRadist(2, 0);
            }
          }
        }
      }
    }

    super.update(paramFloat);
  }

  private void setRadist(int paramInt1, int paramInt2)
  {
    this.radist[paramInt1] = paramInt2;
    if (this.FM.AS.astatePilotStates[paramInt1] > 90) return;
    switch (paramInt1) {
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2a_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("HMask2a_D0", false);
      this.FM.turret[0].bIsOperable = false;
      switch (paramInt2) {
      case 0:
        hierMesh().chunkVisible("Pilot2_D0", true);
        hierMesh().chunkVisible("HMask2_D0", this.FM.Loc.z > 3000.0D);
        break;
      case 1:
        hierMesh().chunkVisible("Pilot2a_D0", true);
        hierMesh().chunkVisible("HMask2a_D0", this.FM.Loc.z > 3000.0D);
        this.FM.turret[0].bIsOperable = true;
      }

      break;
    case 2:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("Pilot3a_D0", false);
      hierMesh().chunkVisible("HMask3_D0", false);
      hierMesh().chunkVisible("HMask3a_D0", false);
      this.FM.turret[1].bIsOperable = false;
      this.FM.turret[2].bIsOperable = false;
      switch (paramInt2) {
      case 0:
        hierMesh().chunkVisible("Pilot3_D0", true);
        hierMesh().chunkVisible("HMask3_D0", this.FM.Loc.z > 3000.0D);
        this.FM.turret[1].bIsOperable = true;
        break;
      case 1:
        hierMesh().chunkVisible("Pilot3a_D0", true);
        hierMesh().chunkVisible("HMask3a_D0", this.FM.Loc.z > 3000.0D);
        this.FM.turret[2].bIsOperable = true;
      }
    }
  }

  static
  {
    Class localClass = IL_4.class;
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);
  }
}