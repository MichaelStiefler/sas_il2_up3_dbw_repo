package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public abstract class ME_210 extends Scheme2
{
  private float kangle0 = 0.0F;
  private float kangle1 = 0.0F;
  private float slpos = 0.0F;
  private float llpos = 0.0F;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    hierMesh().chunkVisible("Oil1_D0", true);
    hierMesh().chunkVisible("Oil2_D0", true);
  }

  public void update(float paramFloat)
  {
    if (this.FM.getSpeed() > 5.0F) {
      this.slpos = (0.7F * this.slpos + 0.13F * (this.FM.getAOA() > 6.6F ? 0.07F : 0.0F));
      resetYPRmodifier();
      xyz[0] = this.slpos;
      hierMesh().chunkSetLocate("SlatL_D0", xyz, ypr);
      xyz[0] = (-this.slpos);
      hierMesh().chunkSetLocate("SlatR_D0", xyz, ypr);
    }
    hierMesh().chunkSetAngles("WaterL_D0", 0.0F, -17.0F * this.kangle0, 0.0F);
    hierMesh().chunkSetAngles("WaterL1_D0", 0.0F, -17.0F * this.kangle0, 0.0F);
    hierMesh().chunkSetAngles("OilL_D0", 0.0F, -30.0F * this.kangle0, 0.0F);
    this.kangle0 = (0.95F * this.kangle0 + 0.05F * this.FM.EI.engines[0].getControlRadiator());
    hierMesh().chunkSetAngles("WaterR_D0", 0.0F, -17.0F * this.kangle1, 0.0F);
    hierMesh().chunkSetAngles("WaterR1_D0", 0.0F, 17.0F * this.kangle1, 0.0F);
    hierMesh().chunkSetAngles("OilR_D0", 0.0F, -30.0F * this.kangle1, 0.0F);
    this.kangle1 = (0.95F * this.kangle1 + 0.05F * this.FM.EI.engines[1].getControlRadiator());
    this.FM.turret[1].target = this.FM.turret[0].target;
    if (this.FM.AS.bLandingLightOn) {
      if (this.llpos < 1.0F) {
        this.llpos += 0.5F * paramFloat;
        hierMesh().chunkSetAngles("LLamp_D0", 0.0F, 0.0F, 90.0F * this.llpos);
      }
    }
    else if (this.llpos > 0.0F) {
      this.llpos -= 0.5F * paramFloat;
      hierMesh().chunkSetAngles("LLamp_D0", 0.0F, 0.0F, 90.0F * this.llpos);
    }

    super.update(paramFloat);
  }

  protected void moveAirBrake(float paramFloat)
  {
    int i;
    if (paramFloat > 0.05F) {
      for (i = 1; i < 23; i++) {
        hierMesh().chunkVisible("Brake" + (i < 10 ? "0" + i : new StringBuffer().append("").append(i).toString()) + "_D0", true);
      }
      hierMesh().chunkSetAngles("Brake01_D0", 0.0F, 90.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("Brake02_D0", 0.0F, 90.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("Brake03_D0", 0.0F, 90.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("Brake04_D0", 0.0F, 90.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("Brake05_D0", 0.0F, 90.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("Brake06_D0", 0.0F, 90.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("Brake07_D0", 0.0F, 90.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("Brake08_D0", 0.0F, 90.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("Brake09_D0", 0.0F, 90.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("Brake10_D0", 0.0F, 90.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("Brake12_D0", 0.0F, 90.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("Brake13_D0", 0.0F, 90.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("Brake14_D0", 0.0F, 90.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("Brake15_D0", 0.0F, 90.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("Brake16_D0", 0.0F, 90.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("Brake17_D0", 0.0F, 90.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("Brake18_D0", 0.0F, 90.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("Brake19_D0", 0.0F, 90.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("Brake20_D0", 0.0F, 90.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("Brake21_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    } else {
      for (i = 1; i < 23; i++)
        hierMesh().chunkVisible("Brake" + (i < 10 ? "0" + i : new StringBuffer().append("").append(i).toString()) + "_D0", false);
    }
  }

  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay1_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor")) {
        debuggunnery("Armor: Hit..");
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 45.200001F) / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        } else if ((paramString.endsWith("p2")) || (paramString.endsWith("p2")))
        {
          getEnergyPastArmor(World.Rnd().nextFloat(8.0F, 12.0F), paramShot);
          if (paramShot.power <= 0.0F) {
            debugprintln(this, "*** Armor: Nose Armor: Bullet Reflected..");
            doRicochetBack(paramShot);
          }
        } else if (paramString.endsWith("p4")) {
          getEnergyPastArmor(World.Rnd().nextFloat(20.0F, 30.0F) / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
          debuggunnery("Armor: Armor Glass: Hit..");
          if (paramShot.power <= 0.0F) {
            debuggunnery("Armor: Armor Glass: Bullet Stopped..");
            doRicochetBack(paramShot);
          }
        } else if (paramString.endsWith("p5")) {
          getEnergyPastArmor(10.100000381469727D / (Math.abs(v1.z) + 9.999999747378752E-005D), paramShot);
        } else if (paramString.endsWith("p6")) {
          getEnergyPastArmor(12.0D + World.Rnd().nextFloat(10.0F, 30.0F) / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        } else if (paramString.endsWith("p7")) {
          getEnergyPastArmor(8.079999923706055D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        } else if (paramString.endsWith("p8")) {
          getEnergyPastArmor(1.009999990463257D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
          if (paramShot.powerType == 3)
            paramShot.powerType = 0;
        }
        else if (paramString.endsWith("p9")) {
          getEnergyPastArmor(5.050000190734863D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        } else if (paramString.endsWith("p10")) {
          getEnergyPastArmor(9.090000152587891D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        } else if (paramString.endsWith("p11")) {
          getEnergyPastArmor(64.639999389648438D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
          if (paramShot.power <= 0.0F)
            doRicochetBack(paramShot);
        }
        else if (paramString.startsWith("xxarmore")) {
          getEnergyPastArmor(5.050000190734863D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        } else if (paramString.startsWith("xxarmorw")) {
          getEnergyPastArmor(5.050000190734863D / (Math.abs(v1.z) + 9.999999747378752E-005D), paramShot);
        }
        return;
      }

      if (paramString.startsWith("xxcontrols")) {
        debuggunnery("Controls: Hit..");
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
          if (getEnergyPastArmor(2.45F, paramShot) <= 0.0F) break;
          debuggunnery("Controls: Barbette Controls: Disabled..");
          this.FM.turret[0].bIsOperable = false;
          if (this.FM.CT.Weapons[10] == null) break;
          if (this.FM.CT.Weapons[10][0] != null) {
            this.FM.AS.setJamBullets(10, 0);
          }
          if (this.FM.CT.Weapons[10][1] == null) break;
          this.FM.AS.setJamBullets(10, 1); break;
        case 2:
        case 3:
          if (getEnergyPastArmor(0.002F, paramShot) <= 0.0F) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken.."); break;
        case 4:
          if (getEnergyPastArmor(World.Rnd().nextFloat(6.8F, 12.0F), paramShot) <= 0.0F) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          debuggunnery("Controls: Elevator Controls: Destroyed.."); break;
        case 5:
        case 6:
        case 7:
        case 8:
          if ((getEnergyPastArmor(0.12F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.5F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          debuggunnery("Controls: Aileron Controls: Disabled..");
        }

        return;
      }

      if (paramString.startsWith("xxspar")) {
        debuggunnery("Spar Construction: Hit..");
        if ((paramString.startsWith("xxsparli")) && 
          (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparri")) && 
          (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlm")) && 
          (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparrm")) && 
          (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlo")) && 
          (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparro")) && 
          (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        return;
      }

      if (paramString.startsWith("xxlock")) {
        debuggunnery("Lock Construction: Hit..");
        if ((paramString.startsWith("xxlockr")) && 
          (getEnergyPastArmor(6.56F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvl")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: VatorL Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvr")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: VatorR Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockal")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: AroneL Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockar")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: AroneR Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), paramShot.initiator);
        }

        return;
      }

      if (paramString.startsWith("xxeng")) {
        i = paramString.charAt(5) - '1';
        debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Hit..");
        if (paramString.endsWith("prop")) {
          if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.8F))
            if (World.Rnd().nextFloat() < 0.5F) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 3);
              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Prop Governor Hit, Disabled..");
            } else {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 4);
              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Prop Governor Hit, Damaged..");
            }
        }
        else if (paramString.endsWith("gear")) {
          if (getEnergyPastArmor(4.6F, paramShot) > 0.0F)
            if (World.Rnd().nextFloat() < 0.5F) {
              this.FM.EI.engines[i].setEngineStuck(paramShot.initiator);
              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Bullet Jams Reductor Gear..");
            } else {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 3);
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 4);
              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Reductor Gear Damaged, Prop Governor Failed..");
            }
        }
        else if (paramString.endsWith("supc")) {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 0);
            debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Supercharger Disabled..");
          }
        } else if (paramString.endsWith("feed")) {
          if ((getEnergyPastArmor(3.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F) && (this.FM.EI.engines[i].getPowerOutput() > 0.7F)) {
            this.FM.AS.hitEngine(paramShot.initiator, i, 100);
            debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Pressurized Fuel Line Pierced, Fuel Flamed..");
          }
        } else if (paramString.endsWith("fuel")) {
          if (getEnergyPastArmor(1.1F, paramShot) > 0.0F) {
            this.FM.EI.engines[i].setEngineStops(paramShot.initiator);
            debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Fuel Line Stalled, Engine Stalled..");
          }
        } else if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(2.1F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 175000.0F) {
              this.FM.AS.setEngineStuck(paramShot.initiator, i);
              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Bullet Jams Crank Ball Bearing..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, i, 2);
              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[i].getReadyness() + "..");
            }
            this.FM.EI.engines[i].setReadyness(paramShot.initiator, this.FM.EI.engines[i].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
            debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[i].getReadyness() + "..");
          }
          getEnergyPastArmor(World.Rnd().nextFloat(22.5F, 33.599998F), paramShot);
        } else if ((paramString.endsWith("cyl1")) || (paramString.endsWith("cyl2")))
        {
          if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[i].getCylindersRatio() * 1.75F)) {
            this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
            debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Cylinders Hit, " + this.FM.EI.engines[i].getCylindersOperable() + "/" + this.FM.EI.engines[i].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 24000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, i, 3);
              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Cylinders Hit, Engine Fires..");
            }
            if (World.Rnd().nextFloat() < 0.01F) {
              this.FM.AS.setEngineStuck(paramShot.initiator, i);
              debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Bullet Jams Piston Head..");
            }
            getEnergyPastArmor(22.5F, paramShot);
          }
        } else if ((paramString.endsWith("mag1")) || (paramString.endsWith("mag2")))
        {
          int j = paramString.charAt(9) - '1';
          this.FM.EI.engines[i].setMagnetoKnockOut(paramShot.initiator, j);
          debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Magneto " + j + " Destroyed..");
        } else if (paramString.endsWith("oil1")) {
          this.FM.AS.hitOil(paramShot.initiator, i);
          debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Oil Radiator Hit..");
        }
        return;
      }

      if (paramString.startsWith("xxoil")) {
        if (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 6.879F), paramShot) > 0.0F) {
          i = paramString.charAt(5) - '1';
          this.FM.AS.hitOil(paramShot.initiator, i);
          getEnergyPastArmor(0.22F, paramShot);
          debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Oil Tank Pierced..");
        }
        return;
      }
      if (paramString.startsWith("xxw")) {
        if (getEnergyPastArmor(World.Rnd().nextFloat(0.1F, 4.27F), paramShot) > 0.0F) {
          i = paramString.charAt(3) - '1';
          if (this.FM.AS.astateEngineStates[i] == 0) {
            debuggunnery("Engine Module (" + (i == 0 ? "Left" : "Right") + "): Water Radiator Pierced..");
            this.FM.AS.hitEngine(paramShot.initiator, i, 2);
            this.FM.AS.doSetEngineState(paramShot.initiator, i, 2);
          }
          getEnergyPastArmor(2.22F, paramShot);
        }
        return;
      }

      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '0';
        switch (i) {
        case 1:
          i = 0;
          break;
        case 2:
        case 3:
          i = 1;
          break;
        case 4:
        case 5:
          i = 2;
          break;
        case 6:
          i = 3;
        }

        if ((getEnergyPastArmor(World.Rnd().nextFloat(0.1F, 2.23F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          if (this.FM.AS.astateTankStates[i] == 0) {
            debuggunnery("Fuel Tank " + (i + 1) + ": Pierced..");
            this.FM.AS.hitTank(paramShot.initiator, i, 1);
            this.FM.AS.doSetTankState(paramShot.initiator, i, 1);
          } else if (this.FM.AS.astateTankStates[i] == 1) {
            debuggunnery("Fuel Tank " + (i + 1) + ": Pierced..");
            this.FM.AS.hitTank(paramShot.initiator, i, 1);
            this.FM.AS.doSetTankState(paramShot.initiator, i, 2);
          }
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.5F)) {
            this.FM.AS.hitTank(paramShot.initiator, 2, 2);
            debuggunnery("Fuel Tank " + (i + 1) + ": Hit..");
          }
        }
        return;
      }

      if (paramString.startsWith("xxmgun")) {
        if (paramString.endsWith("01")) {
          debuggunnery("Nose Gun: Disabled..");
          this.FM.AS.setJamBullets(0, 0);
        }
        if (paramString.endsWith("02")) {
          debuggunnery("Nose Gun: Disabled..");
          this.FM.AS.setJamBullets(0, 1);
        }
        getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 12.96F), paramShot);
      }
      if (paramString.startsWith("xxcannon")) {
        if (paramString.endsWith("01")) {
          debuggunnery("Nose Cannon: Disabled..");
          this.FM.AS.setJamBullets(1, 0);
        }
        if (paramString.endsWith("02")) {
          debuggunnery("Nose Cannon: Disabled..");
          this.FM.AS.setJamBullets(1, 1);
        }
        getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F), paramShot);
      }
      if (paramString.startsWith("xxbarbette")) {
        if ((getEnergyPastArmor(World.Rnd().nextFloat(2.58F, 28.370001F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          this.FM.turret[0].bIsOperable = false;
          if (this.FM.CT.Weapons[10] != null) {
            if (this.FM.CT.Weapons[10][0] != null) {
              this.FM.AS.setJamBullets(10, 0);
            }
            if (this.FM.CT.Weapons[10][1] != null) {
              this.FM.AS.setJamBullets(10, 1);
            }
          }
        }
        getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F), paramShot);
      }
      if ((paramString.startsWith("xxbomb")) && 
        (World.Rnd().nextFloat() < 0.01F) && (this.FM.CT.Weapons[3] != null) && (this.FM.CT.Weapons[3][0].haveBullets())) {
        debuggunnery("Bomb Payload Detonates..");
        this.FM.AS.hitTank(paramShot.initiator, 0, 10);
        this.FM.AS.hitTank(paramShot.initiator, 1, 10);
        this.FM.AS.hitTank(paramShot.initiator, 2, 10);
        this.FM.AS.hitTank(paramShot.initiator, 3, 10);
        nextDMGLevels(3, 2, "CF_D0", paramShot.initiator);
      }

      return;
    }

    if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit")) || (paramString.startsWith("xnose")))
    {
      if (chunkDamageVisible("CF") < 3) {
        hitChunk("CF", paramShot);
      }
      if (!paramString.startsWith("xcockpit"));
    }
    else if (paramString.startsWith("xengine")) {
      i = paramString.charAt(7) - '0';
      if (chunkDamageVisible("Engine" + i) < 2)
        hitChunk("Engine" + i, paramShot);
    }
    else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel")) {
      if (chunkDamageVisible("Keel1") < 3)
        hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xrudder")) {
      if (chunkDamageVisible("Rudder1") < 1)
        hitChunk("Rudder1", paramShot);
    }
    else if (paramString.startsWith("xstab")) {
      if ((paramString.startsWith("xstabl")) && 
        (chunkDamageVisible("StabL") < 3)) {
        hitChunk("StabL", paramShot);
      }

      if ((paramString.startsWith("xstabr")) && 
        (chunkDamageVisible("StabR") < 3)) {
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

      if (paramString.endsWith("2")) {
        if ((World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(6.8F, 29.35F), paramShot) > 0.0F)) {
          debuggunnery("Undercarriage: Stuck..");
          this.FM.AS.setInternalDamage(paramShot.initiator, 3);
        }
        String str = "" + paramString.charAt(5);
        hitChunk("Gear" + str.toUpperCase() + "2", paramShot);
      }
    } else if (paramString.startsWith("xturret")) {
      i = paramString.charAt(7) - '1';
      if (getEnergyPastArmor(0.25F, paramShot) > 0.0F) {
        debuggunnery("Armament System: Turret (" + (i + 1) + ") Machine Gun: Disabled..");
        this.FM.AS.setJamBullets(10, i);
        getEnergyPastArmor(World.Rnd().nextFloat(0.1F, 26.35F), paramShot);
      }
    } else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
      i = 0;
      int k;
      if (paramString.endsWith("a")) {
        i = 1;
        k = paramString.charAt(6) - '1';
      } else if (paramString.endsWith("b")) {
        i = 2;
        k = paramString.charAt(6) - '1';
      } else {
        k = paramString.charAt(5) - '1';
      }
      hitFlesh(k, paramShot, i);
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = 10.0F * Math.min(paramFloat, 0.1F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC9_D0", 0.0F, 80.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 130.0F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, 130.0F * f, 0.0F);

    f = 10.0F * (paramFloat < 0.5F ? Math.min(paramFloat, 0.1F) : Math.min(1.0F - paramFloat, 0.1F));
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -115.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL9_D0", 0.0F, -85.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -140.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -55.0F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -55.0F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -115.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR9_D0", 0.0F, -85.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -140.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -55.0F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, -55.0F * f, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    if (this.FM.CT.getGear() < 0.99F) {
      return;
    }
    hierMesh().chunkSetAngles("GearC2_D0", paramFloat, 0.0F, 0.0F);
  }
  public void moveWheelSink() {
    if (this.FM.CT.getGear() < 0.99F) {
      return;
    }
    resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.35F, 0.0F, 0.35F);
    ypr[1] = -85.0F;
    hierMesh().chunkSetLocate("GearL9_D0", xyz, ypr);
    xyz[1] = (-cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.35F, 0.0F, 0.35F));
    ypr[1] = -85.0F;
    hierMesh().chunkSetLocate("GearR9_D0", xyz, ypr);
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
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      if (this.FM.AS.bIsAboutToBailout) break;
      hierMesh().chunkVisible("Gore1_D0", true); break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      if (this.FM.AS.bIsAboutToBailout) break;
      hierMesh().chunkVisible("Gore2_D0", true);
    }
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
    case 11:
    case 19:
      hierMesh().chunkVisible("Wire_D0", false);
      break;
    case 34:
      this.FM.AS.hitEngine(this, 0, 2);
      if (World.Rnd().nextFloat() >= 0.66F) break;
      this.FM.AS.hitEngine(this, 0, 2); break;
    case 37:
      this.FM.AS.hitEngine(this, 1, 2);
      if (World.Rnd().nextFloat() >= 0.66F) break;
      this.FM.AS.hitEngine(this, 1, 2);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean) {
      if ((this.FM.AS.astateEngineStates[0] > 3) && 
        (World.Rnd().nextFloat() < 0.06F)) this.FM.AS.hitTank(this, 1, 1);

      if ((this.FM.AS.astateEngineStates[1] > 3) && 
        (World.Rnd().nextFloat() < 0.06F)) this.FM.AS.hitTank(this, 2, 1);

      if ((this.FM.AS.astateTankStates[0] > 5) && (World.Rnd().nextFloat() < 0.02F)) nextDMGLevel(this.FM.AS.astateEffectChunks[0] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[1] > 5) && (World.Rnd().nextFloat() < 0.02F)) nextDMGLevel(this.FM.AS.astateEffectChunks[1] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[2] > 5) && (World.Rnd().nextFloat() < 0.02F)) nextDMGLevel(this.FM.AS.astateEffectChunks[2] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[3] > 5) && (World.Rnd().nextFloat() < 0.02F)) nextDMGLevel(this.FM.AS.astateEffectChunks[3] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.08F)) this.FM.AS.hitTank(this, 1, 1);
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.08F)) this.FM.AS.hitTank(this, 0, 1);
      if ((this.FM.AS.astateTankStates[2] > 4) && (World.Rnd().nextFloat() < 0.08F)) this.FM.AS.hitTank(this, 3, 1);
      if ((this.FM.AS.astateTankStates[3] > 4) && (World.Rnd().nextFloat() < 0.08F)) this.FM.AS.hitTank(this, 2, 1);
    }

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
      if (f1 < -65.0F) { f1 = -65.0F; bool = false; }
      if (f1 > 65.0F) { f1 = 65.0F; bool = false; }
      if (f2 < -10.0F) { f2 = -10.0F; bool = false; }
      if (f2 <= 40.0F) break; f2 = 40.0F; bool = false; break;
    case 1:
      if (f1 < -65.0F) { f1 = -65.0F; bool = false; }
      if (f1 > 65.0F) { f1 = 65.0F; bool = false; }
      if (f2 < -40.0F) { f2 = -40.0F; bool = false; }
      if (f2 <= 10.0F) break; f2 = 10.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -45.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap1_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap2_D0", 0.0F, f, 0.0F);
  }

  static
  {
    Class localClass = ME_210.class;
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
  }
}