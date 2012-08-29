package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public abstract class G4M extends Scheme2
{
  private boolean bSightAutomation = false;
  private boolean bSightBombDump = false;
  private float fSightCurDistance = 0.0F;
  public float fSightCurForwardAngle = 0.0F;
  public float fSightCurSideslip = 0.0F;
  public float fSightCurAltitude = 850.0F;
  public float fSightCurSpeed = 150.0F;
  public float fSightCurReadyness = 0.0F;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.FM.AS.wantBeaconsNet(true);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, cvt(paramFloat, 0.01F, 0.1F, 0.0F, -90.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, cvt(paramFloat, 0.01F, 0.1F, 0.0F, -90.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, cvt(paramFloat, 0.01F, 0.1F, 0.0F, -90.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, cvt(paramFloat, 0.01F, 0.1F, 0.0F, -90.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC2_D0", paramFloat, 0.0F, 0.0F);
  }
  public void moveWheelSink() {
    resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.2375F, 0.0F, 0.2375F);
    hierMesh().chunkSetLocate("GearL3_D0", xyz, ypr);
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.2375F, 0.0F, 0.2375F);
    hierMesh().chunkSetLocate("GearR3_D0", xyz, ypr);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (paramBoolean) {
      if (this.FM.AS.astateEngineStates[0] > 3) {
        if (World.Rnd().nextFloat() < 0.02F) this.FM.AS.hitTank(this, 0, 1);
        if (World.Rnd().nextFloat() < 0.02F) this.FM.AS.hitTank(this, 1, 1);
      }
      if (this.FM.AS.astateEngineStates[1] > 3) {
        if (World.Rnd().nextFloat() < 0.02F) this.FM.AS.hitTank(this, 2, 1);
        if (World.Rnd().nextFloat() < 0.02F) this.FM.AS.hitTank(this, 3, 1);
      }
      if ((this.FM.AS.astateTankStates[0] > 5) && (World.Rnd().nextFloat() < 0.02F)) this.FM.AS.hitTank(this, 1, 1);
      if ((this.FM.AS.astateTankStates[1] > 5) && (World.Rnd().nextFloat() < 0.02F)) this.FM.AS.hitTank(this, 0, 1);
      if ((this.FM.AS.astateTankStates[1] > 5) && (World.Rnd().nextFloat() < 0.02F)) this.FM.AS.hitTank(this, 2, 1);
      if ((this.FM.AS.astateTankStates[2] > 5) && (World.Rnd().nextFloat() < 0.02F)) this.FM.AS.hitTank(this, 1, 1);
      if ((this.FM.AS.astateTankStates[2] > 5) && (World.Rnd().nextFloat() < 0.02F)) this.FM.AS.hitTank(this, 3, 1);
      if ((this.FM.AS.astateTankStates[3] > 5) && (World.Rnd().nextFloat() < 0.02F)) this.FM.AS.hitTank(this, 2, 1);
    }
    int i;
    if (this.FM.getAltitude() < 3000.0F) {
      for (i = 1; i < 8; i++)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
    }
    else
      for (i = 1; i < 8; i++)
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot1_D0"));
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -35.0F) { f1 = -35.0F; bool = false; }
      if (f1 > 35.0F) { f1 = 35.0F; bool = false; }
      if (f2 < -45.0F) { f2 = -45.0F; bool = false; }
      if (f2 <= 25.0F) break; f2 = 25.0F; bool = false; break;
    case 1:
      if (f1 < -45.0F) { f1 = -45.0F; bool = false; }
      if (f1 > 45.0F) { f1 = 45.0F; bool = false; }
      if (f2 < -45.0F) { f2 = -45.0F; bool = false; }
      if (f2 <= 25.0F) break; f2 = 25.0F; bool = false; break;
    case 2:
      if (f1 < -50.0F) { f1 = -50.0F; bool = false; }
      if (f1 > 50.0F) { f1 = 50.0F; bool = false; }
      if (f2 > cvt(Math.abs(f1), 0.0F, 50.0F, 40.0F, 25.0F)) {
        f2 = cvt(Math.abs(f1), 0.0F, 50.0F, 40.0F, 25.0F);
      }
      if (f2 >= cvt(Math.abs(f1), 0.0F, 50.0F, -10.0F, -3.5F)) break;
      f2 = cvt(Math.abs(f1), 0.0F, 50.0F, -10.0F, -3.5F); break;
    case 3:
      if (f1 < -55.0F) { f1 = -55.0F; bool = false; }
      if (f1 > 30.0F) { f1 = 30.0F; bool = false; }
      if (f2 < -40.0F) { f2 = -40.0F; bool = false; }
      if (f2 <= 45.0F) break; f2 = 45.0F; bool = false; break;
    case 4:
      if (f1 < -30.0F) { f1 = -30.0F; bool = false; }
      if (f1 > 55.0F) { f1 = 55.0F; bool = false; }
      if (f2 < -40.0F) { f2 = -40.0F; bool = false; }
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
      break;
    case 36:
      hitProp(1, paramInt2, paramActor);
      break;
    case 34:
      this.FM.AS.hitEngine(this, 0, 2);
      if (World.Rnd().nextInt(0, 99) >= 66) break; this.FM.AS.hitEngine(this, 0, 2); break;
    case 37:
      this.FM.AS.hitEngine(this, 1, 2);
      if (World.Rnd().nextInt(0, 99) >= 66) break; this.FM.AS.hitEngine(this, 1, 2); break;
    case 19:
      killPilot(paramActor, 6);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    int j;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxcontrol")) {
        i = paramString.charAt(9) - '0';
        switch (i) {
        case 1:
          if (getEnergyPastArmor(2.2F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.1F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          }
          if (World.Rnd().nextFloat() >= 0.1F) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 1); break;
        case 2:
        case 3:
          if ((getEnergyPastArmor(2.2F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.25F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
        }

        return;
      }
      if (paramString.startsWith("xxeng")) {
        i = paramString.charAt(5) - '1';
        if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(1.7F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat(20000.0F, 140000.0F) < paramShot.power) {
              this.FM.AS.setEngineStuck(paramShot.initiator, i);
              debuggunnery("*** Engine" + i + " Crank Case Hit - Engine Stucks..");
            }
            if (World.Rnd().nextFloat(10000.0F, 50000.0F) < paramShot.power) {
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
        if (paramString.endsWith("gear")) {
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
        if ((paramString.endsWith("oil1")) && 
          (getEnergyPastArmor(4.21F, paramShot) > 0.0F)) {
          this.FM.AS.hitOil(paramShot.initiator, i);
          getEnergyPastArmor(0.42F, paramShot);
        }

        return;
      }
      if (paramString.startsWith("xxfuel")) {
        j = paramString.charAt(6) - '0';
        switch (j) {
        case 1:
        default:
          if (World.Rnd().nextFloat() < 0.1F) {
            hitBone("xxfuel2", paramShot, paramPoint3d);
          }
          if (World.Rnd().nextFloat() < 0.1F) {
            hitBone("xxfuel3", paramShot, paramPoint3d);
          }
          if (World.Rnd().nextFloat() < 0.1F) {
            hitBone("xxfuel5", paramShot, paramPoint3d);
          }
          if (World.Rnd().nextFloat() < 0.1F) {
            hitBone("xxfuel6", paramShot, paramPoint3d);
          }
          return;
        case 2:
          i = 1;
          break;
        case 3:
          i = World.Rnd().nextInt(0, 1);
          break;
        case 4:
          i = 0;
          break;
        case 5:
          i = 2;
          break;
        case 6:
          i = World.Rnd().nextInt(2, 3);
          break;
        case 7:
          i = 3;
        }

        if ((getEnergyPastArmor(0.8F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.2F)) {
          if (this.FM.AS.astateTankStates[i] == 0) {
            this.FM.AS.hitTank(paramShot.initiator, i, 2);
            this.FM.AS.doSetTankState(paramShot.initiator, i, 2);
          }
          if (World.Rnd().nextFloat() < 0.15F) {
            this.FM.AS.hitTank(paramShot.initiator, i, 1);
          }
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.5F)) {
            this.FM.AS.hitTank(paramShot.initiator, i, 2);
          }
        }
        return;
      }
      if (paramString.startsWith("xxgun")) {
        i = paramString.charAt(5) - '1';
        if (World.Rnd().nextFloat() < 0.5F) {
          this.FM.AS.setJamBullets(10 + i, 0);
        }
        getEnergyPastArmor(22.700001F, paramShot);
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
        } else if (paramString.startsWith("xxlocksl")) {
          if (getEnergyPastArmor(4.32F, paramShot) > 0.0F) {
            debuggunnery("*** VatorL Lock Damaged..");
            nextDMGLevels(1, 2, "VatorL_D0", paramShot.initiator);
          }
        } else if (paramString.startsWith("xxlocksr")) {
          if (getEnergyPastArmor(4.32F, paramShot) > 0.0F) {
            debuggunnery("*** VatorR Lock Damaged..");
            nextDMGLevels(1, 2, "VatorR_D0", paramShot.initiator);
          }
        } else if ((paramString.startsWith("xxlockk")) && 
          (getEnergyPastArmor(4.32F, paramShot) > 0.0F)) {
          debuggunnery("*** Rudder1 Lock Damaged..");
          nextDMGLevels(1, 2, "Rudder1_D0", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxpar")) {
        if ((paramString.startsWith("xxpark")) && 
          (chunkDamageVisible("Keel1") > 1) && (getEnergyPastArmor(10.699999809265137D / (1.000100016593933D - Math.abs(v1.y)), paramShot) > 0.0F)) {
          debuggunnery("*** Keel1 Spars Damaged..");
          nextDMGLevels(1, 2, "Keel1_D2", paramShot.initiator);
        }

        if ((paramString.startsWith("xxparli")) && 
          (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(12.800000190734863D / (1.000100016593933D - Math.abs(v1.y)), paramShot) > 0.0F)) {
          debuggunnery("*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxparri")) && 
          (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(12.800000190734863D / (1.000100016593933D - Math.abs(v1.y)), paramShot) > 0.0F)) {
          debuggunnery("*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxparlm")) && 
          (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(10.800000190734863D / (1.000100016593933D - Math.abs(v1.y)), paramShot) > 0.0F)) {
          debuggunnery("*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxparrm")) && 
          (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(10.800000190734863D / (1.000100016593933D - Math.abs(v1.y)), paramShot) > 0.0F)) {
          debuggunnery("*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxparlo")) && 
          (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(8.800000190734863D / (1.000100016593933D - Math.abs(v1.y)), paramShot) > 0.0F)) {
          debuggunnery("*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxparro")) && 
          (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(8.800000190734863D / (1.000100016593933D - Math.abs(v1.y)), paramShot) > 0.0F)) {
          debuggunnery("*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxparrs")) && 
          (chunkDamageVisible("StabL") > 1) && (getEnergyPastArmor(8.800000190734863D / (1.000100016593933D - Math.abs(v1.y)), paramShot) > 0.0F)) {
          debuggunnery("*** StabL Spars Damaged..");
          nextDMGLevels(1, 2, "StabL_D2", paramShot.initiator);
        }

        if ((paramString.startsWith("xxparls")) && 
          (chunkDamageVisible("StabR") > 1) && (getEnergyPastArmor(8.800000190734863D / (1.000100016593933D - Math.abs(v1.y)), paramShot) > 0.0F)) {
          debuggunnery("*** StabR Spars Damaged..");
          nextDMGLevels(1, 2, "StabR_D2", paramShot.initiator);
        }

        return;
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
      if (chunkDamageVisible("Rudder1") < 2)
        hitChunk("Rudder1", paramShot);
    }
    else if (paramString.startsWith("xstabl")) {
      if (chunkDamageVisible("StabL") < 2)
        hitChunk("StabL", paramShot);
    }
    else if (paramString.startsWith("xstabr")) {
      if (chunkDamageVisible("StabR") < 2)
        hitChunk("StabR", paramShot);
    }
    else if (paramString.startsWith("xvatorl")) {
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
    else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
      i = 0;

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

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt) {
    case 2:
      this.FM.turret[0].setHealth(paramFloat);
      break;
    case 3:
      this.FM.turret[2].setHealth(paramFloat);
      break;
    case 4:
      this.FM.turret[4].setHealth(paramFloat);
      break;
    case 5:
      this.FM.turret[3].setHealth(paramFloat);
      break;
    case 6:
      this.FM.turret[1].setHealth(paramFloat);
    }
  }

  public void doMurderPilot(int paramInt) {
    hierMesh().chunkVisible("Pilot" + (paramInt + 1) + "_D0", false);
    hierMesh().chunkVisible("Head" + (paramInt + 1) + "_D0", false);
    hierMesh().chunkVisible("Pilot" + (paramInt + 1) + "_D1", true);
  }

  public boolean typeBomberToggleAutomation()
  {
    this.bSightAutomation = (!this.bSightAutomation);
    this.bSightBombDump = false;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAutomation" + (this.bSightAutomation ? "ON" : "OFF"));
    return this.bSightAutomation;
  }

  public void typeBomberAdjDistanceReset() {
    this.fSightCurDistance = 0.0F;
    this.fSightCurForwardAngle = 0.0F;
  }

  public void typeBomberAdjDistancePlus() {
    this.fSightCurForwardAngle += 1.0F;
    if (this.fSightCurForwardAngle > 85.0F) {
      this.fSightCurForwardAngle = 85.0F;
    }
    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });
    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjDistanceMinus()
  {
    this.fSightCurForwardAngle -= 1.0F;
    if (this.fSightCurForwardAngle < 0.0F) {
      this.fSightCurForwardAngle = 0.0F;
    }
    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });
    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjSideslipReset()
  {
    this.fSightCurSideslip = 0.0F;
  }

  public void typeBomberAdjSideslipPlus() {
    this.fSightCurSideslip += 0.05F;
    if (this.fSightCurSideslip > 3.0F) {
      this.fSightCurSideslip = 3.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Float(this.fSightCurSideslip * 10.0F) });
  }

  public void typeBomberAdjSideslipMinus() {
    this.fSightCurSideslip -= 0.05F;
    if (this.fSightCurSideslip < -3.0F) {
      this.fSightCurSideslip = -3.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Float(this.fSightCurSideslip * 10.0F) });
  }

  public void typeBomberAdjAltitudeReset() {
    this.fSightCurAltitude = 850.0F;
  }

  public void typeBomberAdjAltitudePlus() {
    this.fSightCurAltitude += 10.0F;
    if (this.fSightCurAltitude > 10000.0F) {
      this.fSightCurAltitude = 10000.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });
    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjAltitudeMinus() {
    this.fSightCurAltitude -= 10.0F;
    if (this.fSightCurAltitude < 850.0F) {
      this.fSightCurAltitude = 850.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });
    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjSpeedReset() {
    this.fSightCurSpeed = 150.0F;
  }

  public void typeBomberAdjSpeedPlus() {
    this.fSightCurSpeed += 10.0F;
    if (this.fSightCurSpeed > 600.0F) {
      this.fSightCurSpeed = 600.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberAdjSpeedMinus() {
    this.fSightCurSpeed -= 10.0F;
    if (this.fSightCurSpeed < 150.0F) {
      this.fSightCurSpeed = 150.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberUpdate(float paramFloat) {
    if (Math.abs(this.FM.Or.getKren()) > 4.5D) {
      this.fSightCurReadyness -= 0.0666666F * paramFloat;
      if (this.fSightCurReadyness < 0.0F) {
        this.fSightCurReadyness = 0.0F;
      }
    }
    if (this.fSightCurReadyness < 1.0F) {
      this.fSightCurReadyness += 0.0333333F * paramFloat;
    } else if (this.bSightAutomation)
    {
      this.fSightCurDistance -= this.fSightCurSpeed / 3.6F * paramFloat;
      if (this.fSightCurDistance < 0.0F) {
        this.fSightCurDistance = 0.0F;
        typeBomberToggleAutomation();
      }
      this.fSightCurForwardAngle = (float)Math.toDegrees(Math.atan(this.fSightCurDistance / this.fSightCurAltitude));
      if (this.fSightCurDistance < this.fSightCurSpeed / 3.6F * Math.sqrt(this.fSightCurAltitude * 0.203874F)) {
        this.bSightBombDump = true;
      }
      if (this.bSightBombDump)
        if (this.FM.isTick(3, 0)) {
          if ((this.FM.CT.Weapons[3] != null) && (this.FM.CT.Weapons[3][(this.FM.CT.Weapons[3].length - 1)] != null) && (this.FM.CT.Weapons[3][(this.FM.CT.Weapons[3].length - 1)].haveBullets())) {
            this.FM.CT.WeaponControl[3] = true;
            HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightBombdrop");
          }
        }
        else this.FM.CT.WeaponControl[3] = false;
    }
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
    paramNetMsgGuaranted.writeByte((this.bSightAutomation ? 1 : 0) | (this.bSightBombDump ? 2 : 0));
    paramNetMsgGuaranted.writeFloat(this.fSightCurDistance);
    paramNetMsgGuaranted.writeByte((int)this.fSightCurForwardAngle);
    paramNetMsgGuaranted.writeByte((int)((this.fSightCurSideslip + 3.0F) * 33.333328F));
    paramNetMsgGuaranted.writeFloat(this.fSightCurAltitude);
    paramNetMsgGuaranted.writeByte((int)(this.fSightCurSpeed / 2.5F));
    paramNetMsgGuaranted.writeByte((int)(this.fSightCurReadyness * 200.0F));
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
    int i = paramNetMsgInput.readUnsignedByte();
    this.bSightAutomation = ((i & 0x1) != 0);
    this.bSightBombDump = ((i & 0x2) != 0);
    this.fSightCurDistance = paramNetMsgInput.readFloat();
    this.fSightCurForwardAngle = paramNetMsgInput.readUnsignedByte();
    this.fSightCurSideslip = (-3.0F + paramNetMsgInput.readUnsignedByte() / 33.333328F);
    this.fSightCurAltitude = paramNetMsgInput.readFloat();
    this.fSightCurSpeed = (paramNetMsgInput.readUnsignedByte() * 2.5F);
    this.fSightCurReadyness = (paramNetMsgInput.readUnsignedByte() / 200.0F);
  }

  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay1_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
  }

  static
  {
    Class localClass = G4M.class;
    Property.set(localClass, "originCountry", PaintScheme.countryJapan);
  }
}