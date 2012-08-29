package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public abstract class P_40SUKAISVOLOCH extends Scheme1
  implements TypeFighter
{
  private float flapps = 0.0F;

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, cvt(paramFloat, 0.04F, 0.5F, 0.0F, -70.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, cvt(paramFloat, 0.02F, 0.09F, 0.0F, -60.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, cvt(paramFloat, 0.02F, 0.09F, 0.0F, -60.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, cvt(paramFloat, 0.01F, 0.79F, 0.0F, -90.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL21_D0", 0.0F, cvt(paramFloat, 0.01F, 0.79F, 0.0F, 94.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, cvt(paramFloat, 0.01F, 0.39F, 0.0F, -53.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, cvt(paramFloat, 0.01F, 0.79F, 0.0F, -30.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, cvt(paramFloat, 0.01F, 0.79F, 0.0F, 100.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, cvt(paramFloat, 0.21F, 0.99F, 0.0F, -90.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR21_D0", 0.0F, cvt(paramFloat, 0.21F, 0.99F, 0.0F, -94.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, cvt(paramFloat, 0.21F, 0.59F, 0.0F, -53.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, cvt(paramFloat, 0.21F, 0.99F, 0.0F, -30.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, cvt(paramFloat, 0.21F, 0.99F, 0.0F, 100.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveWheelSink() {
    resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.26F, 0.0F, 0.26F);
    ypr[1] = cvt(this.FM.CT.getGear(), 0.01F, 0.79F, 0.0F, 94.0F);
    hierMesh().chunkSetLocate("GearL21_D0", xyz, ypr);
    hierMesh().chunkSetAngles("GearL6_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.26F, 0.0F, -25.0F), 0.0F);
    hierMesh().chunkSetAngles("GearL7_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.26F, 0.0F, -68.5F), 0.0F);
    resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.26F, 0.0F, 0.26F);
    ypr[1] = cvt(this.FM.CT.getGear(), 0.01F, 0.79F, 0.0F, -94.0F);
    hierMesh().chunkSetLocate("GearR21_D0", xyz, ypr);
    hierMesh().chunkSetAngles("GearR6_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.26F, 0.0F, -25.0F), 0.0F);
    hierMesh().chunkSetAngles("GearR7_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.26F, 0.0F, -68.5F), 0.0F);
  }
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, paramFloat, 0.0F);
  }

  public void moveCockpitDoor(float paramFloat)
  {
    resetYPRmodifier();
    xyz[1] = cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.63F);
    hierMesh().chunkSetLocate("Blister1_D0", xyz, ypr);
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null)) Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      setDoorSnd(paramFloat);
    }
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      if (this.FM.AS.bIsAboutToBailout) break;
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
    }
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -40.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    setExplosion(paramExplosion);
    if (paramExplosion.chunkName != null) {
      if (paramExplosion.chunkName.startsWith("WingL")) {
        if (World.Rnd().nextFloat() < 0.02F) {
          this.FM.AS.setJamBullets(0, 0);
        }
        if (World.Rnd().nextFloat() < 0.02F) {
          this.FM.AS.setJamBullets(0, 1);
        }
        if (World.Rnd().nextFloat() < 0.02F) {
          this.FM.AS.setJamBullets(0, 2);
        }
      }
      if (paramExplosion.chunkName.startsWith("WingR")) {
        if (World.Rnd().nextFloat() < 0.02F) {
          this.FM.AS.setJamBullets(0, 3);
        }
        if (World.Rnd().nextFloat() < 0.02F) {
          this.FM.AS.setJamBullets(0, 4);
        }
        if (World.Rnd().nextFloat() < 0.02F) {
          this.FM.AS.setJamBullets(0, 5);
        }
      }
    }
    super.msgExplosion(paramExplosion);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(15.0F / (1.0E-005F + (float)Math.abs(v1.x)), paramShot);
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
        } else if (paramString.endsWith("p2")) {
          getEnergyPastArmor(4.0F / (1.0E-005F + (float)Math.abs(v1.x)), paramShot);
        } else if (paramString.endsWith("p3")) {
          getEnergyPastArmor(2.0F / (1.0E-005F + (float)Math.abs(v1.x)), paramShot);
        }
      }
      if (paramString.startsWith("xxcontrols")) {
        if (paramString.endsWith("1")) {
          if (World.Rnd().nextFloat() < 0.3F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
            debugprintln(this, "*** Engine Controls Out..");
          }
          if (World.Rnd().nextFloat() < 0.3F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
            debugprintln(this, "*** Engine Controls Out..");
          }
        } else if (paramString.endsWith("2")) {
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
            debugprintln(this, "*** Evelator Controls Out..");
          }
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 0);
            debugprintln(this, "*** Ailerones Controls Out..");
          }
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);
            debugprintln(this, "*** Rudder Controls Out..");
          }
          if (World.Rnd().nextFloat() < 0.3F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
            debugprintln(this, "*** Engine Controls Out..");
          }
          if (World.Rnd().nextFloat() < 0.3F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
            debugprintln(this, "*** Engine Controls Out..");
          }
        } else if (paramString.endsWith("3")) {
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);
            debugprintln(this, "*** Rudder Controls Out..");
          }
        } else if (((paramString.endsWith("4")) || (paramString.endsWith("5"))) && 
          (World.Rnd().nextFloat() < 0.3F)) {
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          debugprintln(this, "*** Ailerones Controls Out..");
        }
      }

      if (paramString.startsWith("xxeng1")) {
        if ((paramString.endsWith("prp")) && 
          (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
          this.FM.EI.engines[0].setKillPropAngleDevice(paramShot.initiator);
        }

        if ((paramString.endsWith("cas")) && 
          (getEnergyPastArmor(0.7F, paramShot) > 0.0F)) {
          if (World.Rnd().nextFloat(20000.0F, 200000.0F) < paramShot.power) {
            this.FM.AS.setEngineStuck(paramShot.initiator, 0);
            debugprintln(this, "*** Engine Crank Case Hit - Engine Stucks..");
          }
          if (World.Rnd().nextFloat(10000.0F, 50000.0F) < paramShot.power) {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
            debugprintln(this, "*** Engine Crank Case Hit - Engine Damaged..");
          }
          if (World.Rnd().nextFloat(8000.0F, 28000.0F) < paramShot.power) {
            this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, 1);
            debugprintln(this, "*** Engine Crank Case Hit - Cylinder Feed Out, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");
          }
          this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
          debugprintln(this, "*** Engine Crank Case Hit - Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
        }

        if ((paramString.endsWith("cyl")) && 
          (getEnergyPastArmor(0.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 1.75F)) {
          this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
          debugprintln(this, "*** Engine Cylinders Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");
          if (this.FM.AS.astateEngineStates[0] < 1) {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
          }
          if (World.Rnd().nextFloat() < paramShot.power / 24000.0F) {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 3);
            debugprintln(this, "*** Engine Cylinders Hit - Engine Fires..");
          }
          getEnergyPastArmor(25.0F, paramShot);
        }

        if ((paramString.endsWith("sup")) && 
          (getEnergyPastArmor(0.05F, paramShot) > 0.0F)) {
          this.FM.EI.engines[0].setKillCompressor(paramShot.initiator);
        }

        if (paramString.endsWith("sup")) {
          if ((World.Rnd().nextFloat() < 0.3F) && (getEnergyPastArmor(0.05F, paramShot) > 0.0F)) {
            this.FM.EI.engines[0].setKillPropAngleDeviceSpeeds(paramShot.initiator);
          }
          if ((World.Rnd().nextFloat() < 0.3F) && (getEnergyPastArmor(0.05F, paramShot) > 0.0F)) {
            this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, 0);
          }
          if ((World.Rnd().nextFloat() < 0.3F) && (getEnergyPastArmor(0.05F, paramShot) > 0.0F)) {
            this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, 1);
          }
          if ((World.Rnd().nextFloat() < 0.3F) && (getEnergyPastArmor(0.05F, paramShot) > 0.0F)) {
            this.FM.EI.engines[0].setEngineStuck(paramShot.initiator);
          }
          if ((World.Rnd().nextFloat() < 0.3F) && (getEnergyPastArmor(0.05F, paramShot) > 0.0F)) {
            this.FM.EI.engines[0].setEngineStops(paramShot.initiator);
          }
        }
        if (paramString.endsWith("oil")) {
          this.FM.AS.hitOil(paramShot.initiator, 0);
        }
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          this.FM.AS.hitTank(paramShot.initiator, i, 1);
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.11F)) {
            this.FM.AS.hitTank(paramShot.initiator, i, 2);
          }
        }
      }
      if (paramString.startsWith("xxmgun")) {
        i = paramString.charAt(6) - '1';
        this.FM.AS.setJamBullets(0, i);
      }
      if (paramString.startsWith("xxammo")) {
        i = paramString.charAt(6) - '0';
        if (World.Rnd().nextFloat(0.0F, 20000.0F) < paramShot.power) {
          switch (i) {
          case 1:
            this.FM.AS.setJamBullets(0, 2);
            break;
          case 2:
            this.FM.AS.setJamBullets(0, 3);
            break;
          case 3:
            this.FM.AS.setJamBullets(0, 4);
            break;
          case 4:
            this.FM.AS.setJamBullets(0, 5);
          }
        }
      }
      return;
    }

    if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit"))) {
      hitChunk("CF", paramShot);
    }
    if (paramString.startsWith("xcockpit")) {
      if (paramPoint3d.z > 0.75D) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
      }
      if ((paramPoint3d.x > -1.100000023841858D) && 
        (World.Rnd().nextFloat() < 0.1F)) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
      }

      if (World.Rnd().nextFloat() < 0.25F) {
        if (paramPoint3d.y > 0.0D) {
          if (paramPoint3d.x > -1.100000023841858D)
            this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
          else {
            this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
          }
        }
        else if (paramPoint3d.x > -1.100000023841858D)
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
        else {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
        }
      }
    }
    else if (paramString.startsWith("xeng")) {
      hitChunk("Engine1", paramShot);
    } else if (paramString.startsWith("xtail")) {
      hitChunk("Tail1", paramShot);
    } else if (paramString.startsWith("xkeel")) {
      hitChunk("Keel1", paramShot);
    } else if (paramString.startsWith("xrudder")) {
      hitChunk("Rudder1", paramShot);
    } else if (paramString.startsWith("xstabl")) {
      hitChunk("StabL", paramShot);
    } else if (paramString.startsWith("xstabr")) {
      hitChunk("StabR", paramShot);
    } else if (paramString.startsWith("xvatorl")) {
      hitChunk("VatorL", paramShot);
    } else if (paramString.startsWith("xvatorr")) {
      hitChunk("VatorR", paramShot);
    } else if (paramString.startsWith("xwing")) {
      if (paramString.startsWith("xwinglin")) {
        hitChunk("WingLIn", paramShot);
      }
      if (paramString.startsWith("xwingrin")) {
        hitChunk("WingRIn", paramShot);
      }
      if (paramString.startsWith("xwinglmid")) {
        hitChunk("WingLMid", paramShot);
      }
      if (paramString.startsWith("xwingrmid")) {
        hitChunk("WingRMid", paramShot);
      }
      if (paramString.startsWith("xwinglout")) {
        hitChunk("WingLOut", paramShot);
      }
      if (paramString.startsWith("xwingrout"))
        hitChunk("WingROut", paramShot);
    }
    else if (paramString.startsWith("xarone")) {
      if (paramString.startsWith("xaronel")) {
        hitChunk("AroneL", paramShot);
      }
      if (paramString.startsWith("xaroner"))
        hitChunk("AroneR", paramShot);
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

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 35:
      this.FM.AS.setJamBullets(0, 2);
      this.FM.AS.setJamBullets(0, 3);
      break;
    case 38:
      this.FM.AS.setJamBullets(0, 4);
      this.FM.AS.setJamBullets(0, 5);
    }

    switch (paramInt1) {
    case 11:
    case 19:
    case 33:
    case 34:
    case 35:
    case 36:
    case 37:
    case 38:
      hierMesh().chunkVisible("Wire_D0", false);
    case 12:
    case 13:
    case 14:
    case 15:
    case 16:
    case 17:
    case 18:
    case 20:
    case 21:
    case 22:
    case 23:
    case 24:
    case 25:
    case 26:
    case 27:
    case 28:
    case 29:
    case 30:
    case 31:
    case 32: } return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void update(float paramFloat)
  {
    float f = cvt(this.FM.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 27.0F, -11.0F);
    if (Math.abs(this.flapps - f) > 0.2F) {
      this.flapps = f;
      hierMesh().chunkSetAngles("Water2_D0", 0.0F, f, 0.0F);
      hierMesh().chunkSetAngles("Water3_D0", 0.0F, f, 0.0F);
      f = Math.min(f, 10.5F);
      hierMesh().chunkSetAngles("Water1_D0", 0.0F, f, 0.0F);
      hierMesh().chunkSetAngles("Water4_D0", 0.0F, f, 0.0F);
    }
    super.update(paramFloat);
  }

  static
  {
    Class localClass = P_40SUKAISVOLOCH.class;
    Property.set(localClass, "originCountry", PaintScheme.countryUSA);
  }
}