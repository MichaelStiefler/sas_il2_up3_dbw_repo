package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.CLASS;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Property;

public abstract class DO_335 extends Scheme2
  implements TypeFighter
{
  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 3:
      this.FM.setGCenter(-1.5F);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, cvt(paramFloat, 0.21F, 0.63F, 0.0F, -115.5F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC7_D0", 0.0F, cvt(paramFloat, 0.21F, 0.63F, 0.0F, -137.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC8_D0", 0.0F, cvt(paramFloat, 0.21F, 0.63F, 0.0F, -148.5F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC9_D0", 0.0F, cvt(paramFloat, 0.21F, 0.63F, 0.0F, 1.0F), 0.0F);
    float tmp117_116 = (xyz[2] = ypr[0] = ypr[1] = ypr[2] = 0.0F); xyz[1] = tmp117_116; xyz[0] = tmp117_116;
    xyz[1] = cvt(paramFloat, 0.21F, 0.63F, 0.0F, 0.09F);
    paramHierMesh.chunkSetLocate("GearC10_D0", xyz, ypr);
    paramHierMesh.chunkSetAngles("GearC11_D0", 0.0F, cvt(paramFloat, 0.21F, 0.26F, 0.0F, -80.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC12_D0", 0.0F, cvt(paramFloat, 0.21F, 0.26F, 0.0F, -80.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, cvt(paramFloat, 0.12F, 0.75F, 0.0F, -86.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, cvt(paramFloat, 0.12F, 0.75F, 0.0F, -4.0F), 0.0F);
    float tmp257_256 = (xyz[2] = ypr[0] = ypr[1] = ypr[2] = 0.0F); xyz[1] = tmp257_256; xyz[0] = tmp257_256;
    xyz[1] = cvt(paramFloat, 0.12F, 0.75F, 0.0F, 0.23F);
    paramHierMesh.chunkSetLocate("GearL7_D0", xyz, ypr);
    paramHierMesh.chunkSetAngles("GearL8_D0", 0.0F, cvt(paramFloat, 0.12F, 0.75F, 0.0F, -20.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL9_D0", 0.0F, cvt(paramFloat, 0.12F, 0.75F, 0.0F, -155.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL10_D0", 0.0F, cvt(paramFloat, 0.12F, 0.200325F, 0.0F, -85.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL13_D0", 0.0F, cvt(paramFloat, 0.12F, 0.200325F, 0.0F, -123.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, cvt(paramFloat, 0.03F, 0.95F, 0.0F, -86.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, cvt(paramFloat, 0.03F, 0.95F, 0.0F, -4.0F), 0.0F);
    float tmp435_434 = (xyz[2] = ypr[0] = ypr[1] = ypr[2] = 0.0F); xyz[1] = tmp435_434; xyz[0] = tmp435_434;
    xyz[1] = cvt(paramFloat, 0.03F, 0.95F, 0.0F, 0.23F);
    paramHierMesh.chunkSetLocate("GearR7_D0", xyz, ypr);
    paramHierMesh.chunkSetAngles("GearR8_D0", 0.0F, cvt(paramFloat, 0.03F, 0.95F, 0.0F, -20.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR9_D0", 0.0F, cvt(paramFloat, 0.03F, 0.95F, 0.0F, -155.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR10_D0", 0.0F, cvt(paramFloat, 0.03F, 0.1473F, 0.0F, -85.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR13_D0", 0.0F, cvt(paramFloat, 0.03F, 0.1473F, 0.0F, -123.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC4_D0", 0.0F, -paramFloat, 0.0F);
  }
  public void moveWheelSink() {
    resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.4719F, 0.0F, 0.52255F);
    hierMesh().chunkSetLocate("GearC3_D0", xyz, ypr);
    hierMesh().chunkSetAngles("GearC5_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.4719F, 0.0F, -65.0F), 0.0F);
    hierMesh().chunkSetAngles("GearC6_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.4719F, 0.0F, -130.0F), 0.0F);
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.27625F, 0.0F, 0.27625F);
    hierMesh().chunkSetLocate("GearL3_D0", xyz, ypr);
    hierMesh().chunkSetAngles("GearL4_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.27625F, 0.0F, -33.0F), 0.0F);
    hierMesh().chunkSetAngles("GearL5_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.27625F, 0.0F, -66.0F), 0.0F);
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.27625F, 0.0F, 0.27625F);
    hierMesh().chunkSetLocate("GearR3_D0", xyz, ypr);
    hierMesh().chunkSetAngles("GearR4_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.27625F, 0.0F, -33.0F), 0.0F);
    hierMesh().chunkSetAngles("GearR5_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.27625F, 0.0F, -66.0F), 0.0F);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -45.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap1_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap2_D0", 0.0F, f, 0.0F);
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Rudder2_D0", 0.0F, -30.0F * paramFloat, 0.0F);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    int j;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(35.889999389648438D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
          if (paramShot.power <= 0.0F)
            doRicochetBack(paramShot);
        }
        else if (paramString.endsWith("p2")) {
          getEnergyPastArmor(12.71F, paramShot);
        } else if (paramString.endsWith("p3")) {
          getEnergyPastArmor(12.710000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcannon")) {
        if (paramString.endsWith("01")) {
          debuggunnery("Armament System: Left Cannon: Disabled..");
          this.FM.AS.setJamBullets(0, 0);
        }
        if (paramString.endsWith("02")) {
          debuggunnery("Armament System: Right Cannon: Disabled..");
          this.FM.AS.setJamBullets(0, 1);
        }
        getEnergyPastArmor(World.Rnd().nextFloat(6.98F, 24.35F), paramShot);
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
        case 4:
          if (getEnergyPastArmor(1.2F, paramShot) <= 0.0F) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          debugprintln(this, "*** Aileron Controls: Control Crank Destroyed.."); break;
        case 2:
        case 3:
          if ((getEnergyPastArmor(4.0D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.5F)) break;
          debuggunnery("Controls: Aileron Wiring Hit, Aileron Controls Disabled..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 5:
        case 6:
          if (getEnergyPastArmor(1.0F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.12F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
            debuggunnery("Evelator Controls Out..");
          }
          if (World.Rnd().nextFloat() >= 0.12F) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          debuggunnery("Rudder Controls Out..");
        }

        return;
      }

      if (paramString.startsWith("xxeng")) {
        i = paramString.charAt(5) - '1';
        debugprintln(this, "*** Engine Module (" + i + "): Hit..");
        if ((paramString.endsWith("prop")) || (paramString.endsWith("prop3"))) {
          if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.8F))
            if (World.Rnd().nextFloat() < 0.5F) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 3);
              debugprintln(this, "*** Engine Module: Prop Governor Hit, Disabled..");
            } else {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 4);
              debugprintln(this, "*** Engine Module: Prop Governor Hit, Damaged..");
            }
        }
        else if ((paramString.endsWith("prop1")) || (paramString.endsWith("prop2"))) {
          if ((getEnergyPastArmor(4.6F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.8F)) {
            this.FM.AS.setInternalDamage(paramShot.initiator, 5);
            debugprintln(this, "*** Engine Module: Drive Shaft Damaged..");
          }
        } else if (paramString.endsWith("gear")) {
          if (getEnergyPastArmor(4.6F, paramShot) > 0.0F)
            if (World.Rnd().nextFloat() < 0.5F) {
              this.FM.EI.engines[i].setEngineStuck(paramShot.initiator);
              debugprintln(this, "*** Engine Module: Bullet Jams Reductor Gear..");
            } else {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 3);
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 4);
              debugprintln(this, "*** Engine Module: Reductor Gear Damaged, Prop Governor Failed..");
            }
        }
        else if (paramString.endsWith("supc")) {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 0);
            debugprintln(this, "*** Engine Module: Supercharger Disabled..");
          }
        } else if (paramString.endsWith("feed")) {
          if ((getEnergyPastArmor(3.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F) && (this.FM.EI.engines[i].getPowerOutput() > 0.7F)) {
            this.FM.AS.hitEngine(paramShot.initiator, i, 100);
            debugprintln(this, "*** Engine Module: Pressurized Fuel Line Pierced, Fuel Flamed..");
          }
        } else if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(2.1F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 175000.0F) {
              this.FM.AS.setEngineStuck(paramShot.initiator, i);
              debugprintln(this, "*** Engine Module: Bullet Jams Crank Ball Bearing..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, i, 2);
              debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[i].getReadyness() + "..");
            }
            this.FM.EI.engines[i].setReadyness(paramShot.initiator, this.FM.EI.engines[i].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
            debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[i].getReadyness() + "..");
          }
          getEnergyPastArmor(22.5F, paramShot);
        } else if ((paramString.startsWith("xxeng1cyl")) || (paramString.startsWith("xxeng2cyl"))) {
          if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[i].getCylindersRatio() * 1.75F)) {
            this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
            debugprintln(this, "*** Engine Module: Cylinders Hit, " + this.FM.EI.engines[i].getCylindersOperable() + "/" + this.FM.EI.engines[i].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 24000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, i, 3);
              debugprintln(this, "*** Engine Module: Cylinders Hit, Engine Fires..");
            }
            if (World.Rnd().nextFloat() < 0.01F) {
              this.FM.AS.setEngineStuck(paramShot.initiator, i);
              debugprintln(this, "*** Engine Module: Bullet Jams Piston Head..");
            }
            getEnergyPastArmor(22.5F, paramShot);
          }
        } else if ((paramString.startsWith("xxeng1mag")) || (paramString.startsWith("xxeng2mag"))) {
          j = paramString.charAt(9) - '1';
          this.FM.EI.engines[i].setMagnetoKnockOut(paramShot.initiator, j);
          debugprintln(this, "*** Engine Module: Magneto " + j + " Destroyed..");
        } else if (paramString.endsWith("sync")) {
          if ((getEnergyPastArmor(2.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
            debugprintln(this, "*** Engine Module: Gun Synchronized Hit, Nose Guns Lose Authority..");
            this.FM.AS.setJamBullets(0, 0);
            this.FM.AS.setJamBullets(0, 1);
          }
        } else if (paramString.endsWith("oil1")) {
          this.FM.AS.hitOil(paramShot.initiator, i);
          debugprintln(this, "*** Engine Module: Oil Radiator Hit..");
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

        return;
      }
      if (paramString.startsWith("xxspar")) {
        debuggunnery("Spar Construction: Hit..");
        if ((paramString.startsWith("xxsparli")) && 
          (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLIn Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparri")) && 
          (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingRIn Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlm")) && 
          (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLMid Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparrm")) && 
          (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingRMid Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlo")) && 
          (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLOut Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparro")) && 
          (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingROut Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxspart")) && 
          (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.86F / (float)Math.sqrt(v1.y * v1.y + v1.z * v1.z), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          debuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }

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
            if (World.Rnd().nextFloat() < 0.02F) {
              this.FM.AS.hitTank(paramShot.initiator, i, 1);
            }
            if ((paramShot.powerType == 3) && (this.FM.AS.astateTankStates[i] > 2) && (World.Rnd().nextFloat() < 0.4F))
              this.FM.AS.hitTank(paramShot.initiator, i, 10);
          }
          else {
            this.FM.AS.hitTank(paramShot.initiator, i, World.Rnd().nextInt(0, (int)(paramShot.power / 56000.0F)));
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
    else if (paramString.startsWith("xcockpit")) {
      if (paramPoint3d.z > 0.4D) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
        if (World.Rnd().nextFloat() < 0.1F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
        }
      }
      else if (paramPoint3d.y > 0.0D) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
      } else {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
      }

      if (paramPoint3d.x > 0.2D)
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
    }
    else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
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
      if (chunkDamageVisible("Rudder1") < 1)
        hitChunk("Rudder1", paramShot);
    }
    else if (paramString.startsWith("xrudder2")) {
      if (chunkDamageVisible("Rudder2") < 1)
        hitChunk("Rudder2", paramShot);
    }
    else if (paramString.startsWith("xstabl")) {
      hitChunk("StabL", paramShot);
    } else if (paramString.startsWith("xstabr")) {
      hitChunk("StabR", paramShot);
    } else if (paramString.startsWith("xvatorl")) {
      if (chunkDamageVisible("VatorL") < 1)
        hitChunk("VatorL", paramShot);
    }
    else if (paramString.startsWith("xvatorr")) {
      if (chunkDamageVisible("VatorR") < 1)
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
      hitChunk("AroneL", paramShot);
    } else if (paramString.startsWith("xaroner")) {
      hitChunk("AroneR", paramShot);
    } else if (paramString.startsWith("xengine1")) {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xgear")) {
      if (World.Rnd().nextFloat() < 0.1F) {
        debuggunnery("*** Gear Hydro Failed..");
        this.FM.Gears.setHydroOperable(false);
      }
    } else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
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

  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay01_D0", 0.0F, -95.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay02_D0", 0.0F, -95.0F * paramFloat, 0.0F);
  }

  public void doEjectCatapult()
  {
    new MsgAction(false, this) {
      public void doAction(Object paramObject) { Aircraft localAircraft = (Aircraft)paramObject;
        if (!Actor.isValid(localAircraft)) return;
        Loc localLoc1 = new Loc();
        Loc localLoc2 = new Loc();
        Vector3d localVector3d = new Vector3d(0.0D, 0.0D, 10.0D);
        HookNamed localHookNamed = new HookNamed(localAircraft, "_ExternalSeat01");
        localAircraft.pos.getAbs(localLoc2);

        localHookNamed.computePos(localAircraft, localLoc2, localLoc1);

        localLoc1.transform(localVector3d);
        localVector3d.x += localAircraft.FM.Vwld.x;
        localVector3d.y += localAircraft.FM.Vwld.y;
        localVector3d.z += localAircraft.FM.Vwld.z;
        new EjectionSeat(2, localLoc1, localVector3d, localAircraft);
      }
    };
    hierMesh().chunkVisible("Seat_D0", false);
  }

  static
  {
    Class localClass = CLASS.THIS();
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
  }
}