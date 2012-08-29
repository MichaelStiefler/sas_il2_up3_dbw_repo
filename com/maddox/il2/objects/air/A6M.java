package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.il2.objects.weapons.FuelTank;
import com.maddox.rts.Property;

public abstract class A6M extends Scheme1
  implements TypeFighter, TypeTNBFighter
{
  protected float arrestor = 0.0F;

  static
  {
    Class var_class = A6M.class;

    Property.set(var_class, "originCountry", PaintScheme.countryJapan);
  }

  public void moveArrestorHook(float f)
  {
    hierMesh().chunkSetAngles("Hook1_D0", 0.0F, -37.0F * f, 0.0F);
    this.arrestor = f;
  }

  public void moveCockpitDoor(float f) {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.6F);
    hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().cockpits != null) && 
        (Main3D.cur3D().cockpits[0] != null))
        Main3D.cur3D().cockpits[0].onDoorMoved(f);
      setDoorSnd(f);
    }
  }

  public void doMurderPilot(int i) {
    switch (i) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
    }
  }

  public void update(float f) {
    for (int i = 1; i < 9; i++)
      hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, 
        -32.0F * 
        this.FM.EI.engines[0].getControlRadiator(), 
        0.0F);
    super.update(f);
  }

  public void rareAction(float f, boolean bool) {
    super.rareAction(f, bool);
    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", 
        hierMesh().isChunkVisible("Pilot1_D0"));
  }

  public static void moveGear(HierMesh hiermesh, float f) {
    hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 46.5F * f, 0.0F);
    hiermesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);

    hiermesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(f, 0.01F, 0.55F, 0.0F, 80.5F), 0.0F);
    hiermesh.chunkSetAngles("GearR6_D0", 0.0F, Aircraft.cvt(f, 0.01F, 0.55F, 0.0F, -135.0F), 0.0F);
    hiermesh.chunkSetAngles("GearR7_D0", 0.0F, Aircraft.cvt(f, 0.01F, 0.06F, 0.0F, 69.0F), 0.0F);
    hiermesh.chunkSetAngles("GearR8_D0", 0.0F, Aircraft.cvt(f, 0.01F, 0.55F, 0.0F, -4.42F), 0.0F);
    hiermesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(f, 0.45F, 1.0F, 0.0F, -80.5F), 0.0F);
    hiermesh.chunkSetAngles("GearL6_D0", 0.0F, Aircraft.cvt(f, 0.45F, 1.0F, 0.0F, 135.0F), 0.0F);
    hiermesh.chunkSetAngles("GearL7_D0", 0.0F, Aircraft.cvt(f, 0.45F, 0.5F, 0.0F, -69.0F), 0.0F);
    hiermesh.chunkSetAngles("GearL8_D0", 0.0F, Aircraft.cvt(f, 0.45F, 1.0F, 0.0F, 4.42F), 0.0F);
  }

  protected void moveGear(float f) {
    moveGear(hierMesh(), f);
  }

  public void moveWheelSink() {
    resetYPRmodifier();
    float f = Aircraft.cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.16015F, 0.0F, 
      0.16015F);
    Aircraft.xyz[2] = f;
    hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
    f = Aircraft.cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.16015F, 0.0F, 
      0.16015F);
    Aircraft.xyz[2] = f;
    hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
  }

  public void moveSteering(float f) {
    if (f > 77.5F) {
      f = 77.5F;
      this.FM.Gears.steerAngle = f;
    }
    if (f < -77.5F) {
      f = -77.5F;
      this.FM.Gears.steerAngle = f;
    }
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
  }

  protected void moveFlap(float f) {
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -60.0F * f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -60.0F * f, 0.0F);
  }

  protected void hitBone(String string, Shot shot, Point3d point3d) {
    if (string.startsWith("xx")) {
      if (string.startsWith("xxcontrols")) {
        debuggunnery("Controls: Hit..");
        int i = string.charAt(10) - '0';
        switch (i) {
        case 1:
        case 2:
          if ((getEnergyPastArmor(0.99F, shot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.15F)) break;
          debuggunnery("Controls: Ailerones Controls: Out..");
          this.FM.AS.setControlsDamage(shot.initiator, 0);

          break;
        case 3:
          if ((getEnergyPastArmor(0.99F, shot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.675F)) break;
          if (World.Rnd().nextFloat() < 0.25F)
            this.FM.AS.setEngineSpecificDamage(shot.initiator, 0, 
              1);
          if (World.Rnd().nextFloat() < 0.25F)
            this.FM.AS.setEngineSpecificDamage(shot.initiator, 0, 
              6);
          if (World.Rnd().nextFloat() >= 0.25F) break;
          this.FM.AS.setEngineSpecificDamage(shot.initiator, 0, 
            7);

          break;
        case 4:
          if ((getEnergyPastArmor(4.2F, shot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.13F)) break;
          debuggunnery(
            "Controls: Elevator Controls: Disabled..");
          this.FM.AS.setControlsDamage(shot.initiator, 1);

          break;
        case 5:
          if ((getEnergyPastArmor(0.1F, shot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.08F)) break;
          debuggunnery(
            "Controls: Rudder Controls: Disabled / Strings Broken..");
          this.FM.AS.setControlsDamage(shot.initiator, 2);
        default:
          break;
        }
      } else if (string.startsWith("xxeng1")) {
        if (string.endsWith("case")) {
          if (getEnergyPastArmor(0.2F, shot) > 0.0F) {
            if (World.Rnd().nextFloat() < shot.power / 140000.0F) {
              this.FM.AS.setEngineStuck(shot.initiator, 0);
              Aircraft.debugprintln(
                this, 
                "*** Engine Crank Case Hit - Engine Stucks..");
            }
            if (World.Rnd().nextFloat() < shot.power / 85000.0F) {
              this.FM.AS.hitEngine(shot.initiator, 0, 2);
              Aircraft.debugprintln(
                this, 
                "*** Engine Crank Case Hit - Engine Damaged..");
            }
          } else if (World.Rnd().nextFloat() < 0.01F) {
            this.FM.EI.engines[0].setCyliderKnockOut(shot.initiator, 1);
          } else {
            this.FM.EI.engines[0].setReadyness(shot.initiator, 
              this.FM.EI.engines[0]
              .getReadyness() - 
              0.002F);
            Aircraft.debugprintln(
              this, 
              "*** Engine Crank Case Hit - Readyness Reduced to " + 
              this.FM.EI.engines[0].getReadyness() + "..");
          }
          getEnergyPastArmor(12.0F, shot);
        }
        if (string.endsWith("cyls")) {
          if (getEnergyPastArmor(5.85F, shot) > 0.0F)
          {
            if (World.Rnd().nextFloat() < 
              this.FM.EI.engines[0].getCylindersRatio() * 0.75F) {
              this.FM.EI.engines[0].setCyliderKnockOut(
                shot.initiator, 
                World.Rnd().nextInt(1, 
                (int)(shot.power / 
                19000.0F)));
              Aircraft.debugprintln(
                this, 
                "*** Engine Cylinders Hit, " + 
                this.FM.EI.engines[0].getCylindersOperable() + "/" + 
                this.FM.EI.engines[0].getCylinders() + " Left..");
              if (World.Rnd().nextFloat() < shot.power / 48000.0F) {
                this.FM.AS.hitEngine(shot.initiator, 0, 2);
                Aircraft.debugprintln(
                  this, 
                  "*** Engine Cylinders Hit - Engine Fires..");
              }
            }
          }
          getEnergyPastArmor(25.0F, shot);
        }
        if (string.endsWith("mag1")) {
          this.FM.EI.engines[0].setMagnetoKnockOut(shot.initiator, 0);
          Aircraft.debugprintln(
            this, "*** Engine Module: Magneto #0 Destroyed..");
          getEnergyPastArmor(25.0F, shot);
        }
        if (string.endsWith("mag2")) {
          this.FM.EI.engines[0].setMagnetoKnockOut(shot.initiator, 1);
          Aircraft.debugprintln(
            this, "*** Engine Module: Magneto #1 Destroyed..");
          getEnergyPastArmor(25.0F, shot);
        }
        if (string.endsWith("oil1")) {
          this.FM.AS.hitOil(shot.initiator, 0);
          Aircraft.debugprintln(
            this, "*** Engine Module: Oil Radiator Hit..");
        }
      } else if (string.startsWith("xxlock")) {
        debuggunnery("Lock Construction: Hit..");
        if (string.startsWith("xxlockr"))
        {
          if (getEnergyPastArmor(5.5F * World.Rnd()
            .nextFloat(1.0F, 
            1.5F), 
            shot) > 0.0F) {
            debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
            nextDMGLevels(3, 2, 
              "Rudder1_D" + chunkDamageVisible("Rudder1"), 
              shot.initiator);
          }
        }
        if (string.startsWith("xxlockvl"))
        {
          if (getEnergyPastArmor(5.5F * World.Rnd()
            .nextFloat(1.0F, 
            1.5F), 
            shot) > 0.0F) {
            debuggunnery("Lock Construction: VatorL Lock Shot Off..");
            nextDMGLevels(3, 2, 
              "VatorL_D" + chunkDamageVisible("VatorL"), 
              shot.initiator);
          }
        }
        if (string.startsWith("xxlockvr"))
        {
          if (getEnergyPastArmor(5.5F * World.Rnd()
            .nextFloat(1.0F, 
            1.5F), 
            shot) > 0.0F) {
            debuggunnery("Lock Construction: VatorR Lock Shot Off..");
            nextDMGLevels(3, 2, 
              "VatorR_D" + chunkDamageVisible("VatorR"), 
              shot.initiator);
          }
        }
        if (string.startsWith("xxlockal"))
        {
          if (getEnergyPastArmor(5.5F * World.Rnd()
            .nextFloat(1.0F, 
            1.5F), 
            shot) > 0.0F) {
            debuggunnery("Lock Construction: AroneL Lock Shot Off..");
            nextDMGLevels(3, 2, 
              "AroneL_D" + chunkDamageVisible("AroneL"), 
              shot.initiator);
          }
        }
        if (string.startsWith("xxlockar"))
        {
          if (getEnergyPastArmor(5.5F * World.Rnd()
            .nextFloat(1.0F, 
            1.5F), 
            shot) > 0.0F) {
            debuggunnery("Lock Construction: AroneR Lock Shot Off..");
            nextDMGLevels(3, 2, 
              "AroneR_D" + chunkDamageVisible("AroneR"), 
              shot.initiator);
          }
        }
      } else if (string.startsWith("xxmgun0")) {
        int i = string.charAt(7) - '1';
        if (getEnergyPastArmor(0.75F, shot) > 0.0F) {
          debuggunnery("Armament: Machine Gun (" + i + 
            ") Disabled..");
          this.FM.AS.setJamBullets(0, i);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), 
            shot);
        }
      } else if (string.startsWith("xxcannon0")) {
        int i = string.charAt(9) - '1';
        if (getEnergyPastArmor(6.29F, shot) > 0.0F) {
          debuggunnery("Armament: Cannon (" + i + ") Disabled..");
          this.FM.AS.setJamBullets(1, i);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), 
            shot);
        }
      } else if (string.startsWith("xxammo")) {
        if ((string.startsWith("xxammol")) && 
          (World.Rnd().nextFloat() < 0.01F)) {
          debuggunnery("Armament: Machine Gun (0) Chain Broken..");
          this.FM.AS.setJamBullets(0, 0);
        }
        if ((string.startsWith("xxammor")) && 
          (World.Rnd().nextFloat() < 0.01F)) {
          debuggunnery("Armament: Machine Gun (1) Chain Broken..");
          this.FM.AS.setJamBullets(0, 1);
        }
        if (string.startsWith("xxammowl")) {
          if (World.Rnd().nextFloat() < 0.01F) {
            debuggunnery(
              "Armament: Cannon Gun (0) Chain Broken..");
            this.FM.AS.setJamBullets(1, 0);
          }
          if (World.Rnd().nextFloat() < 0.01F) {
            debuggunnery(
              "Armament: Cannon Gun (0) Payload Detonates..");
            this.FM.AS.hitTank(shot.initiator, 0, 99);
            nextDMGLevels(3, 2, 
              "WingLIn_D" + 
              chunkDamageVisible("WingLIn"), 
              shot.initiator);
          }
        }
        if (string.startsWith("xxammowr")) {
          if (World.Rnd().nextFloat() < 0.01F) {
            debuggunnery(
              "Armament: Cannon Gun (1) Chain Broken..");
            this.FM.AS.setJamBullets(1, 1);
          }
          if (World.Rnd().nextFloat() < 0.01F) {
            debuggunnery(
              "Armament: Cannon Gun (1) Payload Detonates..");
            this.FM.AS.hitTank(shot.initiator, 1, 99);
            nextDMGLevels(3, 2, 
              "WingRIn_D" + 
              chunkDamageVisible("WingRIn"), 
              shot.initiator);
          }
        }
        getEnergyPastArmor(16.0F, shot);
      } else if (string.startsWith("xxoil")) {
        if ((getEnergyPastArmor(0.25F, shot) > 0.0F) && 
          (World.Rnd().nextFloat() < 0.125F)) {
          this.FM.AS.hitOil(shot.initiator, 0);
          getEnergyPastArmor(0.22F, shot);
          debuggunnery("Engine Module: Oil Tank Pierced..");
        }
      } else if (string.startsWith("xxtank")) {
        int i = string.charAt(6) - '1';
        if ((i < 3) && (getEnergyPastArmor(0.1F, shot) > 0.0F) && 
          (World.Rnd().nextFloat() < 0.45F)) {
          if (this.FM.AS.astateTankStates[i] == 0) {
            debuggunnery("Fuel Tank (" + i + "): Pierced..");
            this.FM.AS.hitTank(shot.initiator, i, 2);
            this.FM.AS.doSetTankState(shot.initiator, i, 2);
          }
          if ((shot.powerType == 3) && 
            (World.Rnd().nextFloat() < 0.995F)) {
            this.FM.AS.hitTank(shot.initiator, i, 4);
            debuggunnery("Fuel Tank (" + i + "): Hit..");
          }
        }
      } else if (string.startsWith("xxpnm")) {
        this.FM.AS.setInternalDamage(shot.initiator, 1);
      } else if (string.startsWith("xxspar")) {
        Aircraft.debugprintln(this, "*** Spar Construction: Hit..");
        if ((string.startsWith("xxsparli")) && 
          (World.Rnd().nextFloat(0.0F, 0.115F) < shot.mass))
        {
          if (getEnergyPastArmor(6.8F * World.Rnd()
            .nextFloat(1.0F, 
            1.5F), 
            shot) > 0.0F) {
            Aircraft.debugprintln(this, "*** WingLIn Spar Damaged..");
            nextDMGLevels(1, 2, 
              "WingLIn_D" + chunkDamageVisible("WingLIn"), 
              shot.initiator);
          }
        }
        if ((string.startsWith("xxsparri")) && 
          (World.Rnd().nextFloat(0.0F, 0.115F) < shot.mass))
        {
          if (getEnergyPastArmor(6.8F * World.Rnd()
            .nextFloat(1.0F, 
            1.5F), 
            shot) > 0.0F) {
            Aircraft.debugprintln(this, "*** WingRIn Spar Damaged..");
            nextDMGLevels(1, 2, 
              "WingRIn_D" + chunkDamageVisible("WingRIn"), 
              shot.initiator);
          }
        }
        if ((string.startsWith("xxsparlm")) && 
          (World.Rnd().nextFloat(0.0F, 0.115F) < shot.mass))
        {
          if (getEnergyPastArmor(6.8F * World.Rnd()
            .nextFloat(1.0F, 
            1.5F), 
            shot) > 0.0F) {
            Aircraft.debugprintln(this, "*** WingLMid Spar Damaged..");
            nextDMGLevels(1, 2, 
              "WingLMid_D" + 
              chunkDamageVisible("WingLMid"), 
              shot.initiator);
          }
        }
        if ((string.startsWith("xxsparrm")) && 
          (World.Rnd().nextFloat(0.0F, 0.115F) < shot.mass))
        {
          if (getEnergyPastArmor(6.8F * World.Rnd()
            .nextFloat(1.0F, 
            1.5F), 
            shot) > 0.0F) {
            Aircraft.debugprintln(this, "*** WingRMid Spar Damaged..");
            nextDMGLevels(1, 2, 
              "WingRMid_D" + 
              chunkDamageVisible("WingRMid"), 
              shot.initiator);
          }
        }
        if ((string.startsWith("xxsparlo")) && 
          (World.Rnd().nextFloat(0.0F, 0.115F) < shot.mass))
        {
          if (getEnergyPastArmor(6.8F * World.Rnd()
            .nextFloat(1.0F, 
            1.5F), 
            shot) > 0.0F) {
            Aircraft.debugprintln(this, "*** WingLOut Spar Damaged..");
            nextDMGLevels(1, 2, 
              "WingLOut_D" + 
              chunkDamageVisible("WingLOut"), 
              shot.initiator);
          }
        }
        if ((string.startsWith("xxsparro")) && 
          (World.Rnd().nextFloat(0.0F, 0.115F) < shot.mass))
        {
          if (getEnergyPastArmor(6.8F * World.Rnd()
            .nextFloat(1.0F, 
            1.5F), 
            shot) > 0.0F) {
            Aircraft.debugprintln(this, "*** WingROut Spar Damaged..");
            nextDMGLevels(1, 2, 
              "WingROut_D" + 
              chunkDamageVisible("WingROut"), 
              shot.initiator);
          }
        }
        if ((string.startsWith("xxspark")) && 
          (World.Rnd().nextFloat() < 0.5F))
        {
          if (getEnergyPastArmor(
            6.8F * World.Rnd().nextFloat(1.0F, 1.5F) / (
            Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), 
            shot) > 0.0F) {
            Aircraft.debugprintln(this, "*** Keel Spars Damaged..");
            nextDMGLevels(1, 2, 
              "Keel1_D" + chunkDamageVisible("Keel1"), 
              shot.initiator);
          }
        }
        if ((string.startsWith("xxspart")) && 
          (chunkDamageVisible("Tail1") > 2))
        {
          if ((getEnergyPastArmor(3.859999895095825D / 
            Math.sqrt(Aircraft.v1.y * 
            Aircraft.v1.y + 
            Aircraft.v1.z * 
            Aircraft.v1.z), 
            shot) > 0.0F) && 
            (World.Rnd().nextFloat() < 0.25F)) {
            debuggunnery(
              "Spar Construction: Tail1 Ribs Hit, Breaking in Half..");
            nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
          }
        }
      } else {
        string.startsWith("xxradio");
      }
    }
    else if ((string.startsWith("xcf")) || (string.startsWith("xblister"))) {
      hitChunk("CF", shot);
      if ((point3d.x > -2.42D) && (point3d.x < 0.32D)) {
        if (World.Rnd().nextFloat() < 0.12F) {
          if (point3d.y > 0.0D)
            this.FM.AS.setCockpitState(shot.initiator, 
              this.FM.AS.astateCockpitState | 0x4);
          else
            this.FM.AS.setCockpitState(shot.initiator, 
              this.FM.AS.astateCockpitState | 0x10);
        }
        if (World.Rnd().nextFloat() < 0.12F) {
          if (point3d.y > 0.0D)
            this.FM.AS.setCockpitState(shot.initiator, 
              this.FM.AS.astateCockpitState | 0x8);
          else
            this.FM.AS.setCockpitState(shot.initiator, 
              this.FM.AS.astateCockpitState | 0x20);
        }
        if ((point3d.x < -0.27D) && (point3d.z > 0.7D))
          this.FM.AS.setCockpitState(shot.initiator, 
            this.FM.AS.astateCockpitState | 0x1);
        if ((point3d.x > -0.27D) && (point3d.z > 0.7D))
          this.FM.AS.setCockpitState(shot.initiator, 
            this.FM.AS.astateCockpitState | 0x2);
        if (World.Rnd().nextFloat() < 0.12F)
          this.FM.AS.setCockpitState(shot.initiator, 
            this.FM.AS.astateCockpitState | 0x40);
      }
    } else if (string.startsWith("xeng")) {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", shot);
    } else if (string.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", shot);
    } else if (string.startsWith("xkeel")) {
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", shot);
    } else if (string.startsWith("xrudder")) {
      if (chunkDamageVisible("Rudder1") < 1)
        hitChunk("Rudder1", shot);
    } else if (string.startsWith("xstab")) {
      if (string.startsWith("xstabl"))
        hitChunk("StabL", shot);
      if (string.startsWith("xstabr"))
        hitChunk("StabR", shot);
    } else if (string.startsWith("xvator")) {
      if ((string.startsWith("xvatorl")) && 
        (chunkDamageVisible("VatorL") < 1))
        hitChunk("VatorL", shot);
      if ((string.startsWith("xvatorr")) && 
        (chunkDamageVisible("VatorR") < 1))
        hitChunk("VatorR", shot);
    } else if (string.startsWith("xwing")) {
      if ((string.startsWith("xwinglin")) && 
        (chunkDamageVisible("WingLIn") < 3))
        hitChunk("WingLIn", shot);
      if ((string.startsWith("xwingrin")) && 
        (chunkDamageVisible("WingRIn") < 3))
        hitChunk("WingRIn", shot);
      if ((string.startsWith("xwinglmid")) && 
        (chunkDamageVisible("WingLMid") < 3))
        hitChunk("WingLMid", shot);
      if ((string.startsWith("xwingrmid")) && 
        (chunkDamageVisible("WingRMid") < 3))
        hitChunk("WingRMid", shot);
      if ((string.startsWith("xwinglout")) && 
        (chunkDamageVisible("WingLOut") < 3))
        hitChunk("WingLOut", shot);
      if ((string.startsWith("xwingrout")) && 
        (chunkDamageVisible("WingROut") < 3))
        hitChunk("WingROut", shot);
    } else if (string.startsWith("xarone")) {
      if ((string.startsWith("xaronel")) && 
        (chunkDamageVisible("AroneL") < 1))
        hitChunk("AroneL", shot);
      if ((string.startsWith("xaroner")) && 
        (chunkDamageVisible("AroneR") < 1))
        hitChunk("AroneR", shot);
    } else if (string.startsWith("xgear")) {
      if (World.Rnd().nextFloat() < 0.05F) {
        debuggunnery("Hydro System: Disabled..");
        this.FM.AS.setInternalDamage(shot.initiator, 0);
      }
      if (World.Rnd().nextFloat() < 0.1F)
      {
        if (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 3.435F), 
          shot) > 0.0F) {
          debuggunnery("Undercarriage: Stuck..");
          this.FM.AS.setInternalDamage(shot.initiator, 3);
        }
      }
    } else if (string.startsWith("xtank1")) {
      if (getEnergyPastArmor(0.05F, shot) > 0.0F) {
        if (this.FM.AS.astateTankStates[3] == 0) {
          debuggunnery("Fuel Tank (E): Pierced..");
          this.FM.AS.hitTank(shot.initiator, 3, 2);
          this.FM.AS.doSetTankState(shot.initiator, 3, 2);
        }
        if ((World.Rnd().nextFloat() < 0.37F) && (shot.powerType == 3)) {
          this.FM.AS.hitTank(shot.initiator, 3, 2);
          debuggunnery("Fuel Tank (E): Hit..");
        }
      }
    } else if ((string.startsWith("xpilot")) || (string.startsWith("xhead"))) {
      int i = 0;
      int i_0_;
      int i_0_;
      if (string.endsWith("a")) {
        i = 1;
        i_0_ = string.charAt(6) - '1';
      }
      else
      {
        int i_0_;
        if (string.endsWith("b")) {
          i = 2;
          i_0_ = string.charAt(6) - '1';
        } else {
          i_0_ = string.charAt(5) - '1';
        }
      }hitFlesh(i_0_, shot, i);
    }
  }

  public void replicateDropFuelTanks() {
    super.replicateDropFuelTanks();
    hierMesh().chunkVisible("ETank_D0", false);
    this.FM.AS.doSetTankState(null, 3, 0);
  }

  public void onAircraftLoaded() {
    super.onAircraftLoaded();
    Object[] objects = this.pos.getBaseAttached();
    if (objects != null)
      for (int i = 0; i < objects.length; i++)
        if ((objects[i] instanceof FuelTank))
          hierMesh().chunkVisible("ETank_D0", true);
  }
}