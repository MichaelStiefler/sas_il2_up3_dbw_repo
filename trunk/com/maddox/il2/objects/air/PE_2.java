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
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public abstract class PE_2 extends Scheme2a
{
  public float fSightCurAltitude = 300.0F;
  public float fSightCurSpeed = 50.0F;
  public float fSightCurForwardAngle = 0.0F;
  public float fSightSetForwardAngle = 0.0F;
  public float fSightCurSideslip = 0.0F;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.FM.AS.wantBeaconsNet(true);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC99_D0", 0.0F, 75.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 112.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -27.0F * (float)Math.sin(paramFloat * 3.141592653589793D), 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -170.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 112.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -27.0F * (float)Math.sin(paramFloat * 3.141592653589793D), 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, 170.5F * paramFloat, 0.0F);

    float f = Math.max(-paramFloat * 1500.0F, -90.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, 0.833333F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, -0.833333F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, -f, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    if (this.FM.CT.getGear() < 0.98F) return;
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, 0.0F, -paramFloat);
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Head2_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxArmor")) {
        debuggunnery("Armor: Hit..");
        if (paramString.endsWith("p1"))
          getEnergyPastArmor(12.699999809265137D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        else if (paramString.endsWith("p2"))
          getEnergyPastArmor(12.699999809265137D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        else if (paramString.endsWith("p3"))
          getEnergyPastArmor(12.699999809265137D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        else if (paramString.endsWith("p4")) {
          getEnergyPastArmor(9.0D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        debuggunnery("Controls: Hit..");
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 3:
          if ((getEnergyPastArmor(4.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
            debuggunnery("Controls: Elevator Controls: Disabled..");
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          }
          if ((getEnergyPastArmor(0.002F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.11F)) break;
          debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2); break;
        case 4:
        case 5:
          if ((getEnergyPastArmor(0.99F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.5F)) break;
          debuggunnery("Controls: Ailerones Controls: Out..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
        }

        return;
      }
      if (paramString.startsWith("xxbomb")) {
        if ((World.Rnd().nextFloat() < 0.01F) && (this.FM.CT.Weapons[3] != null) && (this.FM.CT.Weapons[3][0].haveBullets())) {
          debuggunnery("*** Bomb Payload Detonates..");
          this.FM.AS.hitTank(paramShot.initiator, 0, 100);
          this.FM.AS.hitTank(paramShot.initiator, 1, 100);
          this.FM.AS.hitTank(paramShot.initiator, 2, 100);
          this.FM.AS.hitTank(paramShot.initiator, 3, 100);
          nextDMGLevels(3, 2, "CF_D0", paramShot.initiator);
        }
        return;
      }
      if (paramString.startsWith("xxeng1")) {
        if ((paramString.endsWith("prop")) && 
          (getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 0.4F), paramShot) > 0.0F)) {
          this.FM.EI.engines[0].setKillPropAngleDevice(paramShot.initiator);
          debugprintln(this, "*** Engine1 Prop Governor Failed..");
        }

        if ((paramString.endsWith("gear")) && 
          (getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 1.1F), paramShot) > 0.0F)) {
          this.FM.EI.engines[0].setKillPropAngleDeviceSpeeds(paramShot.initiator);
          debugprintln(this, "*** Engine1 Prop Governor Damaged..");
        }

        if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 6.8F), paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 200000.0F) {
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
              debugprintln(this, "*** Engine1 Crank Case Hit - Engine Stucks..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
              debugprintln(this, "*** Engine1 Crank Case Hit - Engine Damaged..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 28000.0F) {
              this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, 1);
              debugprintln(this, "*** Engine1 Crank Case Hit - Cylinder Feed Out, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");
            }
            if (World.Rnd().nextFloat() < 0.08F) {
              this.FM.EI.engines[0].setEngineStuck(paramShot.initiator);
              debugprintln(this, "*** Engine1 Crank Case Hit - Ball Bearing Jammed - Engine Stuck..");
            }
            this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
            debugprintln(this, "*** Engine1 Crank Case Hit - Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
          }
          if (World.Rnd().nextFloat() < 0.01F) {
            this.FM.EI.engines[0].setEngineStops(paramShot.initiator);
            debugprintln(this, "*** Engine1 Crank Case Hit - Engine Stalled..");
          }
          if (World.Rnd().nextFloat() < 0.01F) {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 10);
            debugprintln(this, "*** Engine1 Crank Case Hit - Fuel Feed Hit - Engine Flamed..");
          }
          getEnergyPastArmor(6.0F, paramShot);
        }
        if (((paramString.endsWith("cyl1")) || (paramString.endsWith("cyl2"))) && 
          (getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 2.542F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 1.72F)) {
          this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
          debugprintln(this, "*** Engine1 Cylinders Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");
          if (World.Rnd().nextFloat() < 0.01F) {
            this.FM.EI.engines[0].setEngineStuck(paramShot.initiator);
            debugprintln(this, "*** Engine1 Cylinder Case Broken - Engine Stuck..");
          }
          if (World.Rnd().nextFloat() < paramShot.power / 24000.0F) {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 3);
            debugprintln(this, "*** Engine1 Cylinders Hit - Engine Fires..");
          }
          getEnergyPastArmor(World.Rnd().nextFloat(3.0F, 46.700001F), paramShot);
        }

        if ((paramString.endsWith("supc")) && 
          (getEnergyPastArmor(0.05F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.89F)) {
          this.FM.EI.engines[0].setKillCompressor(paramShot.initiator);
          debugprintln(this, "*** Engine1 Supercharger Out..");
        }

        if ((paramString.endsWith("eqpt")) && 
          (getEnergyPastArmor(World.Rnd().nextFloat(0.001F, 0.2F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.89F)) {
          if (World.Rnd().nextFloat() < 0.11F) {
            this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, World.Rnd().nextInt(0, 1));
            debugprintln(this, "*** Engine1 Magneto Out..");
          }
          if (World.Rnd().nextFloat() < 0.11F) {
            this.FM.EI.engines[0].setKillCompressor(paramShot.initiator);
            debugprintln(this, "*** Engine1 Compressor Feed Out..");
          }
        }

        return;
      }
      if (paramString.startsWith("xxeng2")) {
        if ((paramString.endsWith("prop")) && 
          (getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 0.4F), paramShot) > 0.0F)) {
          this.FM.EI.engines[1].setKillPropAngleDevice(paramShot.initiator);
          debugprintln(this, "*** Engine2 Prop Governor Failed..");
        }

        if ((paramString.endsWith("gear")) && 
          (getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 1.1F), paramShot) > 0.0F)) {
          this.FM.EI.engines[1].setKillPropAngleDeviceSpeeds(paramShot.initiator);
          debugprintln(this, "*** Engine2 Prop Governor Damaged..");
        }

        if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 6.8F), paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 200000.0F) {
              this.FM.AS.setEngineStuck(paramShot.initiator, 1);
              debugprintln(this, "*** Engine2 Crank Case Hit - Engine Stucks..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, 1, 2);
              debugprintln(this, "*** Engine2 Crank Case Hit - Engine Damaged..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 28000.0F) {
              this.FM.EI.engines[1].setCyliderKnockOut(paramShot.initiator, 1);
              debugprintln(this, "*** Engine2 Crank Case Hit - Cylinder Feed Out, " + this.FM.EI.engines[1].getCylindersOperable() + "/" + this.FM.EI.engines[1].getCylinders() + " Left..");
            }
            if (World.Rnd().nextFloat() < 0.08F) {
              this.FM.EI.engines[1].setEngineStuck(paramShot.initiator);
              debugprintln(this, "*** Engine2 Crank Case Hit - Ball Bearing Jammed - Engine Stuck..");
            }
            this.FM.EI.engines[1].setReadyness(paramShot.initiator, this.FM.EI.engines[1].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
            debugprintln(this, "*** Engine2 Crank Case Hit - Readyness Reduced to " + this.FM.EI.engines[1].getReadyness() + "..");
          }
          if (World.Rnd().nextFloat() < 0.01F) {
            this.FM.EI.engines[1].setEngineStops(paramShot.initiator);
            debugprintln(this, "*** Engine2 Crank Case Hit - Engine Stalled..");
          }
          if (World.Rnd().nextFloat() < 0.01F) {
            this.FM.AS.hitEngine(paramShot.initiator, 1, 10);
            debugprintln(this, "*** Engine2 Crank Case Hit - Fuel Feed Hit - Engine Flamed..");
          }
          getEnergyPastArmor(6.0F, paramShot);
        }
        if (((paramString.endsWith("cyl1")) || (paramString.endsWith("cyl2"))) && 
          (getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 2.542F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[1].getCylindersRatio() * 1.72F)) {
          this.FM.EI.engines[1].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
          debugprintln(this, "*** Engine2 Cylinders Hit, " + this.FM.EI.engines[1].getCylindersOperable() + "/" + this.FM.EI.engines[1].getCylinders() + " Left..");
          if (World.Rnd().nextFloat() < 0.01F) {
            this.FM.EI.engines[1].setEngineStuck(paramShot.initiator);
            debugprintln(this, "*** Engine2 Cylinder Case Broken - Engine Stuck..");
          }
          if (World.Rnd().nextFloat() < paramShot.power / 24000.0F) {
            this.FM.AS.hitEngine(paramShot.initiator, 1, 3);
            debugprintln(this, "*** Engine2 Cylinders Hit - Engine Fires..");
          }
          getEnergyPastArmor(World.Rnd().nextFloat(3.0F, 46.700001F), paramShot);
        }

        if ((paramString.endsWith("supc")) && 
          (getEnergyPastArmor(0.05F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.89F)) {
          this.FM.EI.engines[1].setKillCompressor(paramShot.initiator);
          debugprintln(this, "*** Engine2 Supercharger Out..");
        }

        if ((paramString.endsWith("eqpt")) && 
          (getEnergyPastArmor(World.Rnd().nextFloat(0.001F, 0.2F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.89F)) {
          if (World.Rnd().nextFloat() < 0.11F) {
            this.FM.EI.engines[1].setMagnetoKnockOut(paramShot.initiator, World.Rnd().nextInt(0, 1));
            debugprintln(this, "*** Engine2 Magneto Out..");
          }
          if (World.Rnd().nextFloat() < 0.11F) {
            this.FM.EI.engines[1].setKillCompressor(paramShot.initiator);
            debugprintln(this, "*** Engine2 Compressor Feed Out..");
          }
        }

        return;
      }
      if (paramString.startsWith("xxlock")) {
        debuggunnery("Lock Construction: Hit..");
        if (paramString.startsWith("xxlockr")) {
          if (((paramString.startsWith("xxlockr1")) || (paramString.startsWith("xxlockr2"))) && 
            (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
            debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
            nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
          }

          if (((paramString.startsWith("xxlockr3")) || (paramString.startsWith("xxlockr4"))) && 
            (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
            debuggunnery("Lock Construction: Rudder2 Lock Shot Off..");
            nextDMGLevels(3, 2, "Rudder2_D" + chunkDamageVisible("Rudder2"), paramShot.initiator);
          }
        }

        if ((paramString.startsWith("xxlockvl")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: VatorL Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvR")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: VatorR Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockal")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: AroneL Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockar")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: AroneR Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxMgun0")) {
        i = paramString.charAt(7) - '1';
        if (getEnergyPastArmor(0.5F, paramShot) > 0.0F) {
          debuggunnery("Armament: Machine Gun (" + i + ") Disabled..");
          this.FM.AS.setJamBullets(0, i);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxoil1")) {
        if ((getEnergyPastArmor(0.25F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          this.FM.AS.hitOil(paramShot.initiator, 0);
          getEnergyPastArmor(0.22F, paramShot);
          debuggunnery("Engine Module: Oil Radiator 1 Pierced..");
        }
        return;
      }
      if (paramString.startsWith("xxoil2")) {
        if ((getEnergyPastArmor(0.25F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          this.FM.AS.hitOil(paramShot.initiator, 1);
          getEnergyPastArmor(0.22F, paramShot);
          debuggunnery("Engine Module: Oil Radiator 2 Pierced..");
        }
        return;
      }
      if (paramString.startsWith("xxprib")) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
        getEnergyPastArmor(4.88F, paramShot);
        return;
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '0';
        switch (i) {
        case 1:
          doHitMeATank(paramShot, 1);
          doHitMeATank(paramShot, 2);
          break;
        case 2:
          doHitMeATank(paramShot, 1);
          break;
        case 3:
          doHitMeATank(paramShot, 2);
          break;
        case 4:
          doHitMeATank(paramShot, 0);
          break;
        case 5:
          doHitMeATank(paramShot, 3);
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

        if ((paramString.startsWith("xxspart")) && 
          (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(7.5F / (float)Math.sqrt(v1.y * v1.y + v1.z * v1.z), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.2F)) {
          debuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxwater"));
      return;
    }

    if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit"))) {
      hitChunk("CF", paramShot);
      if (paramString.startsWith("xcockpit")) {
        if (paramPoint3d.x > 2.2D) {
          if (World.Rnd().nextFloat() < 0.73F)
            this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
        }
        else if (paramPoint3d.y > 0.0D)
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
        else {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
        }
        if (World.Rnd().nextFloat() < 0.125F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
        }
        if (World.Rnd().nextFloat() < 0.125F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
        }
      }
      return;
    }if (paramString.startsWith("xnose")) {
      if (chunkDamageVisible("Nose1") < 2) {
        hitChunk("Nose1", paramShot);
        if (World.Rnd().nextFloat() < 0.125F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
        }
        if (World.Rnd().nextFloat() < 0.125F)
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
      }
    }
    else if (paramString.startsWith("xengine1")) {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xengine2")) {
      if (chunkDamageVisible("Engine2") < 2)
        hitChunk("Engine2", paramShot);
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
    else if (paramString.startsWith("xstab")) {
      if (paramString.startsWith("xstabl")) {
        hitChunk("StabL", paramShot);
      }
      if (paramString.startsWith("xstabr"))
        hitChunk("StabR", paramShot);
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

      if ((paramString.endsWith("2")) && 
        (World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 3.435F), paramShot) > 0.0F)) {
        debuggunnery("Undercarriage: Stuck..");
        this.FM.AS.setInternalDamage(paramShot.initiator, 3);
      }
    }
    else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
      i = 0;
      int j;
      if (paramString.startsWith("xhead")) {
        j = paramString.charAt(5) - '1';
      }
      else if (paramString.endsWith("a")) {
        i = 1;
        j = paramString.charAt(6) - '1';
      } else {
        i = 2;
        j = paramString.charAt(6) - '1';
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

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean) {
      if (this.FM.AS.astateEngineStates[0] > 3) {
        if ((this.FM.AS.astateTankStates[1] < 4) && (World.Rnd().nextFloat() < 0.025F)) {
          this.FM.AS.hitTank(this, 1, 1);
        }
        if ((this.FM.getSpeedKMH() > 200.0F) && (World.Rnd().nextFloat() < 0.025F)) {
          nextDMGLevel("Keel1_D0", 0, this);
        }
        if ((this.FM.getSpeedKMH() > 200.0F) && (World.Rnd().nextFloat() < 0.025F)) {
          nextDMGLevel("StabL_D0", 0, this);
        }
        if (World.Rnd().nextFloat() < 0.25F) {
          nextDMGLevel("WingLIn_D0", 0, this);
        }
      }
      if (this.FM.AS.astateEngineStates[1] > 3) {
        if ((this.FM.AS.astateTankStates[2] < 4) && (World.Rnd().nextFloat() < 0.025F)) {
          this.FM.AS.hitTank(this, 2, 1);
        }
        if ((this.FM.getSpeedKMH() > 200.0F) && (World.Rnd().nextFloat() < 0.025F)) {
          nextDMGLevel("Keel2_D0", 0, this);
        }
        if ((this.FM.getSpeedKMH() > 200.0F) && (World.Rnd().nextFloat() < 0.025F)) {
          nextDMGLevel("StabR_D0", 0, this);
        }
        if (World.Rnd().nextFloat() < 0.25F) {
          nextDMGLevel("WingRIn_D0", 0, this);
        }
      }
    }
    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else {
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
    }
    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask2_D0", false);
    else {
      hierMesh().chunkVisible("HMask2_D0", hierMesh().isChunkVisible("Pilot2_D0"));
    }
    if (hierMesh().chunkFindCheck("HMask3_D0") > 0) {
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask3_D0", false);
      else {
        hierMesh().chunkVisible("HMask3_D0", hierMesh().isChunkVisible("Pilot3_D0"));
      }
    }
    if (hierMesh().chunkFindCheck("HMask3a_D0") > 0) {
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask3a_D0", false);
      else {
        hierMesh().chunkVisible("HMask3a_D0", hierMesh().isChunkVisible("Pilot3a_D0"));
      }
    }
    if (hierMesh().chunkFindCheck("HMask3b_D0") > 0) {
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask3b_D0", false);
      else {
        hierMesh().chunkVisible("HMask3b_D0", hierMesh().isChunkVisible("Pilot3b_D0"));
      }
    }
    if (hierMesh().chunkFindCheck("HMask3c_D0") > 0)
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask3c_D0", false);
      else
        hierMesh().chunkVisible("HMask3c_D0", hierMesh().isChunkVisible("Pilot3c_D0"));
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
      this.FM.turret[3].setHealth(paramFloat);
    }
  }

  public void doRemoveBodyFromPlane(int paramInt) {
    super.doRemoveBodyFromPlane(paramInt);
    if (paramInt >= 3) {
      doRemoveBodyChunkFromPlane("Pilot3a");
      doRemoveBodyChunkFromPlane("Head3a");
      doRemoveBodyChunkFromPlane("Pilot3b");
      doRemoveBodyChunkFromPlane("Head3b");
      doRemoveBodyChunkFromPlane("Pilot3c");
      doRemoveBodyChunkFromPlane("Head3c");
    }
  }

  protected void moveAirBrake(float paramFloat)
  {
    if ((this instanceof TypeDiveBomber)) {
      hierMesh().chunkSetAngles("Brake01_D0", 0.0F, 90.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("Brake02_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    }
  }

  protected void moveBayDoor(float paramFloat)
  {
    if ((this instanceof TypeBomber)) {
      hierMesh().chunkSetAngles("Bay1_D0", 0.0F, -85.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("Bay2_D0", 0.0F, 85.0F * paramFloat, 0.0F);
    }
    hierMesh().chunkSetAngles("BayL1_D0", 0.0F, -65.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("BayL2_D0", 0.0F, 65.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("BayR1_D0", 0.0F, 65.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("BayR2_D0", 0.0F, -65.0F * paramFloat, 0.0F);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) { case 33:
      return super.cutFM(34, paramInt2, paramActor);
    case 36:
      return super.cutFM(37, paramInt2, paramActor);
    case 11:
      hierMesh().chunkVisible("WireL_D0", false);
      break;
    case 12:
      hierMesh().chunkVisible("WireR_D0", false);
      break;
    case 3:
      this.FM.AS.setEngineState(this, 0, 0);
      break;
    case 4:
      this.FM.AS.setEngineState(this, 1, 0);
      break;
    case 13:
      return false;
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void update(float paramFloat)
  {
    if (this.FM.AS.bIsAboutToBailout) {
      hierMesh().chunkVisible("WireL_D0", false);
      hierMesh().chunkVisible("WireR_D0", false);
    }
    super.update(paramFloat);
  }

  public boolean typeDiveBomberToggleAutomation()
  {
    return false;
  }

  public void typeDiveBomberAdjAltitudeReset()
  {
  }

  public void typeDiveBomberAdjAltitudePlus()
  {
  }

  public void typeDiveBomberAdjAltitudeMinus()
  {
  }

  public void typeDiveBomberAdjVelocityReset()
  {
  }

  public void typeDiveBomberAdjVelocityPlus() {
  }

  public void typeDiveBomberAdjVelocityMinus() {
  }

  public void typeDiveBomberAdjDiveAngleReset() {
  }

  public void typeDiveBomberAdjDiveAnglePlus() {
  }

  public void typeDiveBomberAdjDiveAngleMinus() {
  }

  public void typeDiveBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
  }

  public void typeDiveBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
  }

  static {
    Class localClass = PE_2.class;
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);
  }
}