package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public abstract class A_20 extends Scheme2
{
  private float[] flapps = { 0.0F, 0.0F };

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
    case 2:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("HMask3_D0", false);
      hierMesh().chunkVisible("Pilot3_D1", true);
      break;
    case 3:
      hierMesh().chunkVisible("Pilot4_D0", false);
      hierMesh().chunkVisible("HMask4_D0", false);
      hierMesh().chunkVisible("Pilot4_D1", true);
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
      break;
    case 19:
      killPilot(this, 2);
      killPilot(this, 1);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, -105.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC33_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, cvt(paramFloat, 0.0F, 0.1F, 0.0F, -90.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC6_D0", 0.0F, cvt(paramFloat, 0.0F, 0.1F, 0.0F, -90.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 116.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -135.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, cvt(paramFloat, 0.0F, 0.1F, 0.0F, -70.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, cvt(paramFloat, 0.0F, 0.1F, 0.0F, -72.5F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 116.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -135.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, cvt(paramFloat, 0.0F, 0.1F, 0.0F, -70.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, cvt(paramFloat, 0.0F, 0.1F, 0.0F, -72.5F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveWheelSink() {
    resetYPRmodifier();
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.288F, 0.0F, 0.288F);
    hierMesh().chunkSetLocate("GearL3_D0", xyz, ypr);
    resetYPRmodifier();
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.288F, 0.0F, 0.288F);
    hierMesh().chunkSetLocate("GearR3_D0", xyz, ypr);
    resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.122F, 0.0F, 0.122F);
    hierMesh().chunkSetLocate("GearC3_D0", xyz, ypr);
  }

  protected void moveRudder(float paramFloat)
  {
    if (this.FM.CT.getGear() > 0.98F) {
      hierMesh().chunkSetAngles("GearC33_D0", 0.0F, 36.0F * paramFloat, 0.0F);
    }
    super.moveRudder(paramFloat);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxammo0")) {
        if (World.Rnd().nextFloat() < 0.25F) {
          this.FM.AS.setJamBullets(0, World.Rnd().nextInt(0, 5));
        }
        getEnergyPastArmor(11.4F, paramShot);
        return;
      }
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(12.100000381469727D / Math.abs(v1.x), paramShot);
        }
        if (paramString.endsWith("p2")) {
          getEnergyPastArmor(World.Rnd().nextFloat(20.0F, 60.0F) / Math.abs(v1.x), paramShot);
          if (paramShot.power <= 0.0F) {
            debugprintln(this, "*** Armor Glass: Bullet Stopped..");
            doRicochetBack(paramShot);
          }
        }
        if (paramString.endsWith("p3")) {
          getEnergyPastArmor(12.7F, paramShot);
        }
        if (paramString.endsWith("p4")) {
          getEnergyPastArmor(12.699999809265137D / (Math.abs(v1.x) + 9.999999974752427E-007D), paramShot);
        }
        if (paramString.endsWith("p5")) {
          getEnergyPastArmor(12.699999809265137D / (Math.abs(v1.z) + 9.999999974752427E-007D), paramShot);
        }
        if (paramString.endsWith("p6")) {
          getEnergyPastArmor(4.099999904632568D / (Math.abs(v1.x) + 9.999999974752427E-007D), paramShot);
        }
        if (paramString.endsWith("p8")) {
          getEnergyPastArmor(9.529999732971191D / (Math.abs(v1.x) + 9.999999974752427E-007D), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        i = paramString.charAt(10) - '0';
        if (paramString.length() == 12) {
          i = 10 + (paramString.charAt(11) - '0');
        }
        switch (i) {
        case 1:
        case 3:
          if (getEnergyPastArmor(3.0F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.5F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
            debuggunnery("Evelator Controls Out..");
          }
          if (World.Rnd().nextFloat() < 0.5F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);
            debuggunnery("Rudder Controls Out..");
          }
          if (World.Rnd().nextFloat() >= 0.5F) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          debuggunnery("Aileron Controls Out.."); break;
        case 2:
          getEnergyPastArmor(1.5F, paramShot);

          break;
        case 4:
          if (getEnergyPastArmor(1.5F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.15F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
            debuggunnery("*** Engine1 Throttle Controls Out..");
          }
          if (World.Rnd().nextFloat() >= 0.15F) break;
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
          debuggunnery("*** Engine1 Prop Controls Out.."); break;
        case 5:
          if (getEnergyPastArmor(1.5F, paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.15F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 1);
            debuggunnery("*** Engine2 Throttle Controls Out..");
          }
          if (World.Rnd().nextFloat() >= 0.15F) break;
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 6);
          debuggunnery("*** Engine2 Prop Controls Out.."); break;
        case 6:
        case 7:
          if ((getEnergyPastArmor(1.5F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.25F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          debuggunnery("Ailerons Controls Out.."); break;
        case 10:
        case 11:
          if ((getEnergyPastArmor(1.5F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.12F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          debuggunnery("Evelator Controls Out.."); break;
        case 12:
          if ((getEnergyPastArmor(1.5F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.12F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          debuggunnery("Rudder Controls Out..");
        case 8:
        case 9:
        }

        return;
      }
      if (paramString.startsWith("xxeng")) {
        i = 0;
        if (paramString.startsWith("xxeng2")) {
          i = 1;
        }

        debuggunnery("Engine Module[" + i + "]: Hit..");
        if (paramString.endsWith("case")) {
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
          getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 24.0F), paramShot);
        }
        if (paramString.endsWith("cyls")) {
          if ((getEnergyPastArmor(0.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[i].getCylindersRatio() * 0.66F)) {
            this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 32200.0F)));
            debuggunnery("Engine Module: Cylinders Hit, " + this.FM.EI.engines[i].getCylindersOperable() + "/" + this.FM.EI.engines[i].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 1000000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, i, 2);
              debuggunnery("Engine Module: Cylinders Hit - Engine Fires..");
            }
          }
          getEnergyPastArmor(25.0F, paramShot);
        }
        if ((paramString.endsWith("eqpt")) || ((paramString.endsWith("cyls")) && (World.Rnd().nextFloat() < 0.01F))) {
          if (getEnergyPastArmor(0.5F, paramShot) > 0.0F) {
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
        if ((paramString.endsWith("mag1")) || (paramString.endsWith("mag2"))) {
          debuggunnery("Engine Module: Magneto " + i + " Destroyed..");
          this.FM.EI.engines[i].setMagnetoKnockOut(paramShot.initiator, i);
        }
        if (paramString.endsWith("oil1")) {
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.25F, paramShot) > 0.0F))
            debuggunnery("Engine Module: Oil Radiator Hit..");
          this.FM.AS.hitOil(paramShot.initiator, i);
        }
        if ((paramString.endsWith("prop")) && 
          (getEnergyPastArmor(0.42F, paramShot) > 0.0F)) {
          this.FM.EI.engines[i].setKillPropAngleDevice(paramShot.initiator);
        }

        if ((paramString.endsWith("supc")) && 
          (getEnergyPastArmor(World.Rnd().nextFloat(0.2F, 12.0F), paramShot) > 0.0F)) {
          debuggunnery("Engine Module: Turbine Disabled..");
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 0);
        }

        return;
      }
      if (paramString.startsWith("xxlock")) {
        debugprintln(this, "*** Lock Construction: Hit..");
        if ((paramString.startsWith("xxlockk1")) && 
          (getEnergyPastArmor(6.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F)) {
          debugprintln(this, "*** Rudder1 Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlocksl")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** VatorL Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlocksr")) && 
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
      if (paramString.startsWith("xxpnm")) {
        if (getEnergyPastArmor(World.Rnd().nextFloat(0.25F, 1.22F), paramShot) > 0.0F) {
          debuggunnery("Pneumo System: Disabled..");
          this.FM.AS.setInternalDamage(paramShot.initiator, 1);
        }
        return;
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

        if (((paramString.endsWith("e1")) || (paramString.endsWith("e2"))) && 
          (getEnergyPastArmor(28.0F, paramShot) > 0.0F)) {
          debuggunnery("*** Engine1 Suspension Broken in Half..");
          nextDMGLevels(3, 2, "Engine1_D0", paramShot.initiator);
        }

        if (((paramString.endsWith("e3")) || (paramString.endsWith("e4"))) && 
          (getEnergyPastArmor(28.0F, paramShot) > 0.0F)) {
          debuggunnery("*** Engine2 Suspension Broken in Half..");
          nextDMGLevels(3, 2, "Engine2_D0", paramShot.initiator);
        }

        if ((paramString.startsWith("xxspark")) && 
          (chunkDamageVisible("Keel1") > 1) && (getEnergyPastArmor(9.6F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** Keel1 Spars Damaged..");
          nextDMGLevels(1, 2, "Keel1_D2", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparsl")) && 
          (chunkDamageVisible("StabL") > 1) && (getEnergyPastArmor(11.2F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** StabL Spars Damaged..");
          nextDMGLevels(1, 2, "StabL_D2", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparsr")) && 
          (chunkDamageVisible("StabR") > 1) && (getEnergyPastArmor(11.2F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** StabR Spars Damaged..");
          nextDMGLevels(1, 2, "StabR_D2", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '0';
        switch (i) {
        case 1:
          doHitMeATank(paramShot, 0);
          break;
        case 2:
          doHitMeATank(paramShot, 1);
          break;
        case 3:
          doHitMeATank(paramShot, 2);
          break;
        case 4:
          doHitMeATank(paramShot, 3);
          break;
        case 5:
          doHitMeATank(paramShot, 1);
          doHitMeATank(paramShot, 2);
        }

        return;
      }
      return;
    }

    if (paramString.startsWith("xcf")) {
      if (chunkDamageVisible("CF") < 3) {
        hitChunk("CF", paramShot);
      }
      if (paramPoint3d.x > 1.471D) {
        if ((paramPoint3d.z > 0.5520000000000001D) && (paramPoint3d.x > 2.37D)) {
          if (paramPoint3d.y > 0.0D)
            this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
          else {
            this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
          }
        }
        if ((paramPoint3d.z > 0.0D) && (paramPoint3d.z < 0.539D)) {
          if (paramPoint3d.y > 0.0D)
            this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
          else {
            this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
          }
        }
        if ((paramPoint3d.x < 2.407D) && (paramPoint3d.z > 0.5520000000000001D)) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
        }
        if ((paramPoint3d.x > 2.6D) && (paramPoint3d.z > 0.693D)) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
        }
        if (World.Rnd().nextFloat() < 0.12F)
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
      if (chunkDamageVisible("AroneL") < 1)
        hitChunk("AroneL", paramShot);
    }
    else if (paramString.startsWith("xaroner")) {
      if (chunkDamageVisible("AroneR") < 1)
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
      if (World.Rnd().nextFloat() < 0.1F) {
        debuggunnery("*** Gear Hydro Failed..");
        this.FM.Gears.setHydroOperable(false);
      }
    } else if (paramString.startsWith("xturret")) {
      if (paramString.startsWith("xturret1b")) {
        this.FM.AS.setJamBullets(10, 0);
        this.FM.AS.setJamBullets(10, 1);
      }
      if (paramString.endsWith("2b"))
        this.FM.AS.setJamBullets(11, 0);
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

  private final void doHitMeATank(Shot paramShot, int paramInt)
  {
    if (getEnergyPastArmor(0.2F, paramShot) > 0.0F)
      if (paramShot.power < 14100.0F) {
        if (this.FM.AS.astateTankStates[paramInt] == 0) {
          this.FM.AS.hitTank(paramShot.initiator, paramInt, 1);
          this.FM.AS.doSetTankState(paramShot.initiator, paramInt, 1);
        }
        if ((this.FM.AS.astateTankStates[paramInt] > 0) && ((World.Rnd().nextFloat() < 0.02F) || ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.25F))))
          this.FM.AS.hitTank(paramShot.initiator, paramInt, 2);
      }
      else {
        this.FM.AS.hitTank(paramShot.initiator, paramInt, World.Rnd().nextInt(0, (int)(paramShot.power / 56000.0F)));
      }
  }

  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay1_D0", 0.0F, -160.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -160.0F * paramFloat, 0.0F);
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    for (int i = 0; i < 2; i++) {
      float f = this.FM.EI.engines[i].getControlRadiator();
      if (Math.abs(this.flapps[i] - f) > 0.01F) {
        this.flapps[i] = f;
        for (int j = 1; j < 7; j++) {
          String str = "RAD" + (i == 0 ? "L" : "R") + "0" + j + "_D0";
          hierMesh().chunkSetAngles(str, 0.0F, -20.0F * f, 0.0F);
        }
      }
    }
  }

  static
  {
    Class localClass = A_20.class;
    Property.set(localClass, "originCountry", PaintScheme.countryUSA);
  }
}