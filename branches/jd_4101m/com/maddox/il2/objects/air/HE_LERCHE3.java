package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class HE_LERCHE3 extends Scheme2
  implements TypeFighter, TypeX4Carrier
{
  public Loc suka = new Loc();

  private float pictVBrake = 0.0F;
  private float pictAileron = 0.0F;
  private float pictVator = 0.0F;
  private float pictRudder = 0.0F;
  private float pictBlister = 0.0F;
  public boolean bToFire = false;
  private long tX4Prev = 0L;

  private static final float[] fcA = { 0.0F, 0.04F, 0.1F, 0.04F, 0.02F, -0.02F, -0.04F, -0.1F, -0.04F };
  private static final float[] fcE = { 0.98F, 0.48F, 0.1F, -0.48F, -0.7F, -0.7F, -0.48F, 0.1F, 0.48F };
  private static final float[] fcR = { 0.02F, 0.48F, 0.8F, 0.48F, 0.28F, -0.28F, -0.48F, -0.8F, -0.48F };

  private float deltaAzimuth = 0.0F;
  private float deltaTangage = 0.0F;

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 3:
    case 4:
      return false;
    case 13:
      killPilot(this, 0);
      break;
    case 19:
      hitProp(1, paramInt2, paramActor);
      this.FM.Gears.cgear = false;
      this.FM.Gears.lgear = false;
      this.FM.Gears.rgear = false;
      break;
    case 11:
    case 12:
      this.FM.Gears.cgear = false;
      break;
    case 17:
      this.FM.Gears.lgear = false;
      break;
    case 18:
      this.FM.Gears.rgear = false;
      break;
    case 7:
    case 9:
    case 10:
      return false;
    case 5:
    case 6:
    case 8:
    case 14:
    case 15:
    case 16: } return super.cutFM(paramInt1, paramInt2, paramActor);
  }
  protected void moveAileron(float paramFloat) {
  }
  protected void moveElevator(float paramFloat) {
  }
  protected void moveFlap(float paramFloat) {
  }
  protected void moveRudder(float paramFloat) {
  }
  public static void moveGear(HierMesh paramHierMesh, float paramFloat) {
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
  }
  public void moveWheelSink() { resetYPRmodifier();
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.63F, 0.0F, 0.63F);
    hierMesh().chunkSetLocate("GearL2_D0", xyz, ypr);
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.63F, 0.0F, 0.63F);
    hierMesh().chunkSetLocate("GearR2_D0", xyz, ypr);
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.63F, 0.0F, 0.63F);
    hierMesh().chunkSetLocate("GearC2_D0", xyz, ypr);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    int j;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(65.989997863769531D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
          if (paramShot.power <= 0.0F)
            doRicochetBack(paramShot);
        }
        else if (paramString.endsWith("p2")) {
          getEnergyPastArmor(16.209999F, paramShot);
        } else if (paramString.endsWith("g1")) {
          getEnergyPastArmor(34.209999084472656D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
        }
        return;
      }
      if (paramString.startsWith("xxcannon")) {
        if (paramString.endsWith("1")) {
          debuggunnery("Armament System: Left Cannon: Disabled..");
          this.FM.AS.setJamBullets(0, 0);
        }
        if (paramString.endsWith("2")) {
          debuggunnery("Armament System: Right Cannon: Disabled..");
          this.FM.AS.setJamBullets(0, 1);
        }
        getEnergyPastArmor(World.Rnd().nextFloat(6.98F, 24.35F), paramShot);
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        if (getEnergyPastArmor(1.0F, paramShot) > 0.0F) {
          if (World.Rnd().nextFloat() < 0.12F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
            debuggunnery("Evelator Controls Out..");
          }
          if (World.Rnd().nextFloat() < 0.12F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);
            debuggunnery("Rudder Controls Out..");
          }
        }
        return;
      }

      if (paramString.startsWith("xxeng")) {
        i = paramString.charAt(5) - '1';
        debugprintln(this, "*** Engine Module (" + i + "): Hit..");
        if (paramString.endsWith("prop")) {
          if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.8F))
            if (World.Rnd().nextFloat() < 0.5F) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 3);
              debugprintln(this, "*** Engine Module: Prop Governor Hit, Disabled..");
            } else {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 4);
              debugprintln(this, "*** Engine Module: Prop Governor Hit, Damaged..");
            }
        }
        else if (paramString.endsWith("pipe")) {
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
      if (paramString.startsWith("xxrad")) {
        if (getEnergyPastArmor(2.2F, paramShot) > 0.0F) {
          i = paramString.charAt(5) - '1';
          if (i < 3) {
            this.FM.AS.hitOil(paramShot.initiator, 0);
            debugprintln(this, "*** Engine Module A: Oil Radiator Hit..");
          } else {
            this.FM.AS.hitOil(paramShot.initiator, 1);
            debugprintln(this, "*** Engine Module B: Oil Radiator Hit..");
          }
        }
        return;
      }
      if (paramString.startsWith("xxinst1")) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
        return;
      }
      if (paramString.startsWith("xxinst2")) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
        return;
      }
      return;
    }

    if (paramString.startsWith("xcf")) {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
    }
    else if (paramString.startsWith("xnose")) {
      if (chunkDamageVisible("Nose") < 2) {
        hitChunk("CF", paramShot);
      }
      this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
      if (paramPoint3d.y > 0.0D)
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
      else
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
    }
    else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel1")) {
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xrudder1")) {
      if (chunkDamageVisible("Rudder1") < 1)
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

  public void update(float paramFloat)
  {
    int i = 0;

    super.update(paramFloat);

    this.FM.setGCenter(cvt(this.FM.getSpeedKMH(), 0.0F, 240.0F, -10.5F, 0.0F));

    float f1 = this.FM.CT.getAirBrake();
    int j;
    if (Math.abs(this.pictVBrake - f1) > 0.001F) {
      this.pictVBrake = f1;
      resetYPRmodifier();
      xyz[2] = cvt(this.pictVBrake, 0.0F, 1.0F, 0.0F, 0.525F);
      for (j = 1; j < 10; j++) {
        hierMesh().chunkSetLocate("Flap0" + j + "A_D0", xyz, ypr);
      }
      i = 1;
    }
    f1 = this.FM.CT.getAileron();
    if (Math.abs(this.pictAileron - f1) > 0.01F) {
      this.pictAileron = f1;
      i = 1;
    }
    f1 = this.FM.CT.getRudder();
    if (Math.abs(this.pictRudder - f1) > 0.01F) {
      this.pictRudder = f1;
      i = 1;
    }
    f1 = this.FM.CT.getElevator();
    if (Math.abs(this.pictVator - f1) > 0.01F) {
      this.pictVator = f1;
      i = 1;
    }
    if (i != 0) {
      for (j = 0; j < 9; j++) {
        f1 = -60.0F * this.pictVBrake * (fcA[j] * this.pictAileron + fcE[j] * this.pictVator + fcR[j] * this.pictRudder);
        hierMesh().chunkSetAngles("Flap0" + (j + 1) + "B_D0", f1, 0.0F, 0.0F);
      }
      hierMesh().chunkSetAngles("AroneC_D0", 0.0F, 45.0F * this.pictAileron, 0.0F);
      hierMesh().chunkSetAngles("AroneL_D0", 0.0F, 45.0F * this.pictAileron, 0.0F);
      hierMesh().chunkSetAngles("AroneR_D0", 0.0F, 45.0F * this.pictAileron, 0.0F);
      hierMesh().chunkSetAngles("Rudder1_D0", 34.0F * this.pictRudder, 0.0F, 0.0F);
      hierMesh().chunkSetAngles("VatorL_D0", -34.0F * this.pictVator, 0.0F, 0.0F);
      hierMesh().chunkSetAngles("VatorR_D0", 34.0F * this.pictVator, 0.0F, 0.0F);
      hierMesh().chunkSetAngles("RFlap01_D0", 60.0F - 60.0F * this.pictVBrake, 0.0F, 0.0F);
      hierMesh().chunkSetAngles("RFlap02_D0", -60.0F + 60.0F * this.pictVBrake, 0.0F, 0.0F);
      hierMesh().chunkSetAngles("RFlap03_D0", -60.0F + 60.0F * this.pictVBrake, 0.0F, 0.0F);
      hierMesh().chunkSetAngles("RFlap04_D0", 60.0F - 60.0F * this.pictVBrake, 0.0F, 0.0F);
    }
    if (this.FM.AS.astateBailoutStep > 1) {
      if (this.pictBlister < 1.0F) {
        this.pictBlister += 3.0F * paramFloat;
      }
      hierMesh().chunkSetAngles("Blister2_D0", -110.0F * this.pictBlister, 0.0F, 0.0F);
    }

    float f2 = this.FM.EI.getPowerOutput() * cvt(this.FM.getSpeedKMH(), 0.0F, 600.0F, 2.0F, 0.0F);
    if (this.FM.CT.getAirBrake() > 0.5F)
    {
      if (this.FM.Or.getTangage() > 5.0F) {
        this.FM.getW().scale(cvt(this.FM.Or.getTangage(), 45.0F, 90.0F, 1.0F, 0.1F));

        f1 = this.FM.Or.getTangage();
        if (Math.abs(this.FM.Or.getKren()) > 90.0F) {
          f1 = 90.0F + (90.0F - f1);
        }

        float f3 = f1 - 90.0F;
        this.FM.CT.trimElevator = cvt(f3, -20.0F, 20.0F, 0.5F, -0.5F);

        f3 = this.FM.Or.getKren();
        if (Math.abs(f3) > 90.0F) {
          if (f3 > 0.0F)
            f3 = 180.0F - f3;
          else {
            f3 = -180.0F - f3;
          }
        }
        this.FM.CT.trimAileron = cvt(f3, -20.0F, 20.0F, 0.5F, -0.5F);

        this.FM.CT.trimRudder = cvt(f3, -15.0F, 15.0F, 0.04F, -0.04F);
      }
    }
    else {
      this.FM.CT.trimAileron = 0.0F;
      this.FM.CT.trimElevator = 0.0F;
      this.FM.CT.trimRudder = 0.0F;
    }

    this.FM.Or.increment(f2 * (this.FM.CT.getRudder() + this.FM.CT.getTrimRudderControl()), f2 * (this.FM.CT.getElevator() + this.FM.CT.getTrimElevatorControl()), f2 * (this.FM.CT.getAileron() + this.FM.CT.getTrimAileronControl()));
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if ((((this.FM instanceof RealFlightModel)) && (((RealFlightModel)this.FM).isRealMode())) || (!paramBoolean) || (!(this.FM instanceof Pilot)))
    {
      return;
    }
    Pilot localPilot = (Pilot)this.FM;
    if ((localPilot.get_maneuver() == 63) && (localPilot.target != null)) {
      Point3d localPoint3d = new Point3d(localPilot.target.Loc);
      localPoint3d.sub(this.FM.Loc);
      this.FM.Or.transformInv(localPoint3d);
      if (((localPoint3d.x > 4000.0D) && (localPoint3d.x < 5500.0D)) || ((localPoint3d.x > 100.0D) && (localPoint3d.x < 5000.0D) && (World.Rnd().nextFloat() < 0.33F)))
      {
        if (Time.current() > this.tX4Prev + 10000L) {
          this.bToFire = true;
          this.tX4Prev = Time.current();
        }
      }
    }
  }

  public void typeX4CAdjSidePlus()
  {
    this.deltaAzimuth = 1.0F;
  }

  public void typeX4CAdjSideMinus() {
    this.deltaAzimuth = -1.0F;
  }

  public void typeX4CAdjAttitudePlus() {
    this.deltaTangage = 1.0F;
  }

  public void typeX4CAdjAttitudeMinus() {
    this.deltaTangage = -1.0F;
  }

  public void typeX4CResetControls() {
    this.deltaAzimuth = (this.deltaTangage = 0.0F);
  }

  public float typeX4CgetdeltaAzimuth() {
    return this.deltaAzimuth;
  }

  public float typeX4CgetdeltaTangage() {
    return this.deltaTangage;
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Lerche");
    Property.set(localClass, "meshName", "3DO/Plane/He-LercheIIIb(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar06());

    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
    Property.set(localClass, "yearService", 1945.0F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/He-LercheIIIB2.fmd");

    Property.set(localClass, "cockpitClass", CockpitHE_LIIIB.class);
    Property.set(localClass, "LOSElevation", 1.00705F);

    weaponTriggersRegister(localClass, new int[] { 1, 1, 2, 2, 2, 2, 2, 2 });
    weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_ExternalRock01", "_ExternalRock01", "_ExternalRock02", "_ExternalRock02", "_ExternalRock03", "_ExternalRock03" });

    weaponsRegister(localClass, "default", new String[] { "MGunMK108ki 80", "MGunMK108ki 80", null, null, null, null, null, null });

    weaponsRegister(localClass, "3x4", new String[] { "MGunMK108ki 20", "MGunMK108ki 20", "RocketGunX4 1", "BombGunNull 1", "RocketGunX4 1", "BombGunNull 1", "RocketGunX4 1", "BombGunNull 1" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null });
  }
}