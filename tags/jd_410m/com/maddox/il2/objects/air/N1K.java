package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public abstract class N1K extends Scheme1
  implements TypeFighter
{
  protected float flapps = 0.0F;

  protected float curFlaps = 0.0F;
  protected float mBar = 0.0F;
  protected float desiredPosition = 0.0F;
  private boolean autoEng = false;

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
    }
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);

    if ((this.FM.CT.FlapsControl <= 0.32F) && (this.FM.CT.FlapsControl >= 0.05F)) {
      if ((this == World.getPlayerAircraft()) && (!this.autoEng)) {
        HUD.log("FlapsAuto");
        this.autoEng = true;
      }
      float f = Pitot.Indicator((float)this.FM.Loc.z, this.FM.getSpeed());

      this.mBar = (f * f / (this.FM.getOverload() * 10.0F));

      if ((this.mBar < 315.0F) && (this.FM.getOverload() > 0.0F) && (f < 128.0F)) {
        this.desiredPosition = ((315.0F - this.mBar) / 140.0F);
        if (this.desiredPosition > 1.0F)
          this.desiredPosition = 1.0F;
        if (this.curFlaps < this.desiredPosition)
        {
          this.curFlaps = flapsMovement(paramFloat, this.desiredPosition, this.curFlaps, 999.0F, cvt(this.FM.getSpeedKMH(), 150.0F, 280.0F, 0.15F, 0.08F));
        }
        else this.curFlaps = flapsMovement(paramFloat, this.desiredPosition, this.curFlaps, 999.0F, cvt(this.FM.getSpeedKMH(), 150.0F, 280.0F, 0.15F, 0.25F));

        this.FM.CT.forceFlaps(this.curFlaps);
      } else {
        this.desiredPosition = 0.0F;
        this.curFlaps = flapsMovement(paramFloat, this.desiredPosition, this.curFlaps, 999.0F, cvt(this.FM.getSpeedKMH(), 150.0F, 280.0F, 0.15F, 0.25F));
        this.FM.CT.forceFlaps(this.curFlaps);
      }
      if (Math.abs(this.desiredPosition - this.curFlaps) >= 0.02F)
        sfxFlaps(true);
      else
        sfxFlaps(false);
    } else {
      this.autoEng = false;
    }

    if (this.FM.CT.BrakeControl > 0.4F)
      this.FM.CT.BrakeControl = 0.4F;
  }

  private float flapsMovement(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    float f1 = (float)Math.exp(-paramFloat1 / paramFloat4);

    float f2 = paramFloat2 + (paramFloat3 - paramFloat2) * f1;
    if (f2 < paramFloat2) { f2 += paramFloat5 * paramFloat1; if (f2 > paramFloat2) f2 = paramFloat2; 
    }
    else if (f2 > paramFloat2) { f2 -= paramFloat5 * paramFloat1; if (f2 < paramFloat2) f2 = paramFloat2; 
    }
    return f2;
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float tmp33_32 = (xyz[2] = ypr[0] = ypr[1] = ypr[2] = 0.0F); xyz[1] = tmp33_32; xyz[0] = tmp33_32;

    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, cvt(paramFloat, 0.75F, 0.95F, 0.0F, -60.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, cvt(paramFloat, 0.05F, 0.95F, 0.0F, -85.0F), 0.0F);
    xyz[1] = cvt(paramFloat, 0.4F, 0.75F, 0.0F, -0.205F);
    paramHierMesh.chunkSetLocate("GearL3_D0", xyz, ypr);
    xyz[1] = cvt(paramFloat, 0.75F, 0.94F, 0.0F, -0.05F);
    paramHierMesh.chunkSetLocate("GearL4_D0", xyz, ypr);
    if (paramFloat > 0.75F)
      paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, cvt(paramFloat, 0.75F, 0.95F, -45.0F, -65.0F), 0.0F);
    else {
      paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, cvt(paramFloat, 0.4F, 0.75F, 0.0F, -45.0F), 0.0F);
    }
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, cvt(paramFloat, 0.05F, 0.95F, 0.0F, -130.0F), 0.0F);
    if (paramFloat > 0.75F)
      paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, cvt(paramFloat, 0.725F, 0.95F, -86.0F, -133.0F), 0.0F);
    else {
      paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, cvt(paramFloat, 0.05F, 0.725F, 0.0F, -86.0F), 0.0F);
    }
    xyz[1] = cvt(paramFloat, 0.05F, 0.95F, 0.0F, -0.1F);
    paramHierMesh.chunkSetLocate("GearL8_D0", xyz, ypr);
    paramHierMesh.chunkSetAngles("GearL9_D0", 0.0F, cvt(paramFloat, 0.05F, 0.115F, 0.0F, -85.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL10_D0", 0.0F, cvt(paramFloat, 0.05F, 0.85F, 0.0F, -85.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, cvt(paramFloat, 0.05F, 0.95F, 0.0F, -85.0F), 0.0F);
    xyz[1] = cvt(paramFloat, 0.4F, 0.75F, 0.0F, -0.205F);
    paramHierMesh.chunkSetLocate("GearR3_D0", xyz, ypr);
    xyz[1] = cvt(paramFloat, 0.75F, 0.94F, 0.0F, -0.05F);
    paramHierMesh.chunkSetLocate("GearR4_D0", xyz, ypr);
    if (paramFloat > 0.75F)
      paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, cvt(paramFloat, 0.75F, 0.95F, -45.0F, -65.0F), 0.0F);
    else {
      paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, cvt(paramFloat, 0.4F, 0.75F, 0.0F, -45.0F), 0.0F);
    }
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, cvt(paramFloat, 0.05F, 0.95F, 0.0F, -130.0F), 0.0F);
    if (paramFloat > 0.75F)
      paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, cvt(paramFloat, 0.725F, 0.95F, -86.0F, -133.0F), 0.0F);
    else {
      paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, cvt(paramFloat, 0.05F, 0.725F, 0.0F, -86.0F), 0.0F);
    }
    xyz[1] = cvt(paramFloat, 0.05F, 0.95F, 0.0F, -0.1F);
    paramHierMesh.chunkSetLocate("GearR8_D0", xyz, ypr);
    paramHierMesh.chunkSetAngles("GearR9_D0", 0.0F, cvt(paramFloat, 0.05F, 0.115F, 0.0F, -85.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR10_D0", 0.0F, cvt(paramFloat, 0.05F, 0.85F, 0.0F, -85.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) {
    moveGear(hierMesh(), paramFloat);
  }
  public void moveWheelSink() {
  }
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }

  protected void moveAileron(float paramFloat)
  {
    if (paramFloat > 0.0F) {
      hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -16.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -28.5F * paramFloat, 0.0F);
    } else {
      hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -16.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -28.5F * paramFloat, 0.0F);
    }
  }

  protected void moveElevator(float paramFloat)
  {
    if (paramFloat > 0.0F) {
      hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -35.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -35.0F * paramFloat, 0.0F);
    } else {
      hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -24.5F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -24.5F * paramFloat, 0.0F);
    }
  }

  protected void moveFlap(float paramFloat)
  {
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -30.0F * paramFloat, 0.0F);
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -33.0F * paramFloat, 0.0F);
  }

  public void moveCockpitDoor(float paramFloat)
  {
    resetYPRmodifier();
    xyz[1] = cvt(paramFloat, 0.1F, 0.99F, 0.0F, -0.61F);
    hierMesh().chunkSetLocate("Blister1_D0", xyz, ypr);
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null)) Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      setDoorSnd(paramFloat);
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("p1")) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
          if (getEnergyPastArmor(World.Rnd().nextFloat(32.5F, 65.0F) / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) < 0.0F)
            doRicochetBack(paramShot);
        }
        else if (paramString.endsWith("p2")) {
          getEnergyPastArmor(13.130000114440918D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        } else if (paramString.endsWith("p3")) {
          getEnergyPastArmor(World.Rnd().nextFloat(8.7F, 9.81F) / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcanon0")) {
        i = paramString.charAt(8) - '1';
        if (getEnergyPastArmor(6.29F, paramShot) > 0.0F) {
          debuggunnery("Armament: Cannon (" + i + ") Disabled..");
          this.FM.AS.setJamBullets(1, i);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        debuggunnery("Controls: Hit..");
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
        case 2:
        case 3:
        case 4:
          if ((getEnergyPastArmor(0.99F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.175F)) break;
          debuggunnery("Controls: Ailerones Controls: Out..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 5:
        case 6:
          if ((getEnergyPastArmor(0.22F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.275F)) break;
          debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2); break;
        case 7:
          if ((getEnergyPastArmor(4.2F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.175F)) break;
          debuggunnery("Controls: Elevator Controls: Disabled..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 1); break;
        case 8:
          if (getEnergyPastArmor(3.2F, paramShot) <= 0.0F) break;
          debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 9:
          if (getEnergyPastArmor(0.1F, paramShot) <= 0.0F) break;
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
          debugprintln(this, "*** Throttle Quadrant: Hit, Engine Controls Disabled..");
        }

        return;
      }
      if (paramString.startsWith("xxeng1")) {
        if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(0.2F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 140000.0F) {
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
              debugprintln(this, "*** Engine Crank Case Hit - Engine Stucks..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 85000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
              debugprintln(this, "*** Engine Crank Case Hit - Engine Damaged..");
            }
          }
          else if (World.Rnd().nextFloat() < 0.01F) {
            this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, 1);
          } else {
            this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - 0.002F);
            debugprintln(this, "*** Engine Crank Case Hit - Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
          }

          getEnergyPastArmor(12.0F, paramShot);
        }
        if (paramString.endsWith("cyls")) {
          if ((getEnergyPastArmor(5.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 0.75F)) {
            this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 19000.0F)));
            debugprintln(this, "*** Engine Cylinders Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 48000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
              debugprintln(this, "*** Engine Cylinders Hit - Engine Fires..");
            }
          }
          getEnergyPastArmor(25.0F, paramShot);
        }
        if (paramString.endsWith("gear")) {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 4);
            }
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 0);
            }
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
            }
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
            }
          }
          getEnergyPastArmor(2.0F, paramShot);
        }
        if ((paramString.endsWith("prop")) && 
          (getEnergyPastArmor(0.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
          this.FM.EI.engines[0].setKillPropAngleDevice(paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxlock")) {
        debuggunnery("Lock Construction: Hit..");
        if ((paramString.startsWith("xxlockr")) && 
          (getEnergyPastArmor(2.5F, paramShot) > 0.0F)) {
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
      if (paramString.startsWith("xxmgun0")) {
        i = paramString.charAt(7) - '1';
        if (getEnergyPastArmor(0.75F, paramShot) > 0.0F) {
          debuggunnery("Armament: Machine Gun (" + i + ") Disabled..");
          this.FM.AS.setJamBullets(0, i);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxoil")) {
        if ((getEnergyPastArmor(0.25F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.125F)) {
          this.FM.AS.hitOil(paramShot.initiator, 0);
          getEnergyPastArmor(0.22F, paramShot);
          debuggunnery("Engine Module: Oil Tank Pierced..");
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
        if (i > 3) {
          return;
        }
        if ((getEnergyPastArmor(0.8F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.45F)) {
          if (this.FM.AS.astateTankStates[i] == 0) {
            debuggunnery("Fuel Tank (" + i + "): Pierced..");
            this.FM.AS.hitTank(paramShot.initiator, i, 1);
            this.FM.AS.doSetTankState(paramShot.initiator, i, 1);
          }
          if ((World.Rnd().nextFloat() < 0.008F) || ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.6F))) {
            this.FM.AS.hitTank(paramShot.initiator, i, 1);
            debuggunnery("Fuel Tank (" + i + "): Hit..");
          }
        }
        return;
      }
      return;
    }

    if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcock"))) {
      hitChunk("CF", paramShot);

      return;
    }if (paramString.startsWith("xeng")) {
      if ((chunkDamageVisible("Engine1") < 2) && (getEnergyPastArmor(1.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 3000.0F) < paramShot.power))
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xtail")) {
      if ((getEnergyPastArmor(2.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 5000.0F) < paramShot.power)) hitChunk("Tail1", paramShot); 
    }
    else if (paramString.startsWith("xkeel")) {
      if ((getEnergyPastArmor(1.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 3000.0F) < paramShot.power)) hitChunk("Keel1", paramShot); 
    }
    else if (paramString.startsWith("xrudder")) {
      if ((chunkDamageVisible("Rudder1") < 1) && (getEnergyPastArmor(2.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 3000.0F) < paramShot.power) && 
        (getEnergyPastArmor(1.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 3000.0F) < paramShot.power)) hitChunk("Rudder1", paramShot);
    }
    else if (paramString.startsWith("xstab")) {
      if ((paramString.startsWith("xstabl")) && 
        (getEnergyPastArmor(1.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 3000.0F) < paramShot.power)) hitChunk("StabL", paramShot);

      if ((paramString.startsWith("xstabr")) && 
        (getEnergyPastArmor(1.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 3000.0F) < paramShot.power)) hitChunk("StabR", paramShot);
    }
    else if (paramString.startsWith("xvator")) {
      if ((paramString.startsWith("xvatorl")) && 
        (chunkDamageVisible("VatorL") < 1) && (getEnergyPastArmor(1.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 3000.0F) < paramShot.power)) {
        hitChunk("VatorL", paramShot);
      }

      if ((paramString.startsWith("xvatorr")) && 
        (chunkDamageVisible("VatorR") < 1) && (getEnergyPastArmor(1.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 3000.0F) < paramShot.power)) {
        hitChunk("VatorR", paramShot);
      }
    }
    else if (paramString.startsWith("xwing")) {
      if ((paramString.startsWith("xwinglin")) && 
        (getEnergyPastArmor(2.5F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 5000.0F) < paramShot.power)) hitChunk("WingLIn", paramShot);

      if ((paramString.startsWith("xwingrin")) && 
        (getEnergyPastArmor(2.5F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 5000.0F) < paramShot.power)) hitChunk("WingRIn", paramShot);

      if ((paramString.startsWith("xwinglmid")) && 
        (getEnergyPastArmor(2.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 4000.0F) < paramShot.power)) hitChunk("WingLMid", paramShot);

      if ((paramString.startsWith("xwingrmid")) && 
        (getEnergyPastArmor(2.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 4000.0F) < paramShot.power)) hitChunk("WingRMid", paramShot);

      if ((paramString.startsWith("xwinglout")) && 
        (getEnergyPastArmor(1.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 3000.0F) < paramShot.power)) hitChunk("WingLOut", paramShot);

      if ((paramString.startsWith("xwingrout")) && 
        (getEnergyPastArmor(1.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat(0.0F, 3000.0F) < paramShot.power)) hitChunk("WingROut", paramShot);
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
      if (World.Rnd().nextFloat() < 0.05F) {
        debuggunnery("Hydro System: Disabled..");
        this.FM.AS.setInternalDamage(paramShot.initiator, 0);
      }
      if ((World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 3.435F), paramShot) > 0.0F)) {
        debuggunnery("Undercarriage: Stuck..");
        this.FM.AS.setInternalDamage(paramShot.initiator, 3);
      }
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
    Class localClass = N1K.class;
    Property.set(localClass, "originCountry", PaintScheme.countryJapan);
  }
}