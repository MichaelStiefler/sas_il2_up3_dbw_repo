package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public abstract class SBD extends Scheme1
{
  private float arrestor = 0.0F;

  private float flapps = 0.0F;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.FM.AS.wantBeaconsNet(true);
  }

  protected void moveAirBrake(float paramFloat)
  {
    hierMesh().chunkSetAngles("Brake01_D0", 0.0F, -50.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Brake02_D0", 0.0F, -50.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -50.0F * Math.max(paramFloat, this.FM.CT.getFlap()), 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -50.0F * Math.max(paramFloat, this.FM.CT.getFlap()), 0.0F);
    hierMesh().chunkSetAngles("Flap03_D0", 0.0F, -50.0F * Math.max(paramFloat, this.FM.CT.getFlap()), 0.0F);
  }

  protected void moveFlap(float paramFloat)
  {
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -50.0F * Math.max(paramFloat, this.FM.CT.getAirBrake()), 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -50.0F * Math.max(paramFloat, this.FM.CT.getAirBrake()), 0.0F);
    hierMesh().chunkSetAngles("Flap03_D0", 0.0F, -50.0F * Math.max(paramFloat, this.FM.CT.getAirBrake()), 0.0F);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, cvt(paramFloat, 0.01F, 0.99F, 0.0F, -102.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, cvt(paramFloat, 0.01F, 0.99F, 0.0F, -117.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, cvt(paramFloat, 0.21F, 0.99F, 0.0F, -102.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, cvt(paramFloat, 0.21F, 0.99F, 0.0F, -117.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveWheelSink() {
    resetYPRmodifier();
    float f = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.252F, 0.0F, -0.231F);
    xyz[1] = f;
    hierMesh().chunkSetLocate("GearL3_D0", xyz, ypr);
    f = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.252F, 0.0F, -0.231F);
    xyz[1] = f;
    hierMesh().chunkSetLocate("GearR3_D0", xyz, ypr);
  }
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
  }
  public void moveArrestorHook(float paramFloat) {
    hierMesh().chunkSetAngles("Hook1_D0", 0.0F, -73.5F * this.arrestor, 0.0F);
  }

  public void moveCockpitDoor(float paramFloat)
  {
    resetYPRmodifier();
    xyz[1] = cvt(paramFloat, 0.01F, 0.99F, 0.0F, -0.7F);
    hierMesh().chunkSetLocate("Blister1_D0", xyz, ypr);
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null)) Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      setDoorSnd(paramFloat);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (this.FM.getAltitude() < 3000.0F) {
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
    } else {
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
      hierMesh().chunkVisible("HMask2_D0", hierMesh().isChunkVisible("Pilot2_D0"));
    }
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt) {
    case 1:
      this.FM.turret[0].setHealth(paramFloat);
    }
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
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        debuggunnery("Armor: Hit..");
        if (paramString.endsWith("t1")) {
          getEnergyPastArmor(World.Rnd().nextFloat(21.0F, 42.0F) / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
          if (paramShot.power <= 0.0F)
            doRicochetBack(paramShot);
        }
        else if (paramString.endsWith("t2")) {
          getEnergyPastArmor(World.Rnd().nextFloat(12.2F, 12.9F) / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        } else if (paramString.endsWith("t3")) {
          getEnergyPastArmor(8.9F, paramShot);
        } else if (paramString.endsWith("t4")) {
          getEnergyPastArmor(World.Rnd().nextFloat(12.2F, 12.9F) / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        } else if (paramString.endsWith("t5")) {
          getEnergyPastArmor(World.Rnd().nextFloat(12.2F, 12.9F) / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        } else if (paramString.endsWith("t6")) {
          getEnergyPastArmor(World.Rnd().nextFloat(12.2F, 12.9F) / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        if (((paramString.endsWith("s1")) || (paramString.endsWith("s2"))) && 
          (getEnergyPastArmor(4.8F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          debugprintln(this, "*** Elevator Cables: Hit, Controls Destroyed..");
        }

        if ((paramString.endsWith("s3")) && 
          (getEnergyPastArmor(3.2F, paramShot) > 0.0F)) {
          debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
        }

        if ((paramString.endsWith("s4")) && 
          (getEnergyPastArmor(0.2F, paramShot) > 0.0F)) {
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
          debugprintln(this, "*** Throttle Quadrant: Hit, Engine Controls Disabled..");
        }

        if (((paramString.endsWith("s5")) || (paramString.endsWith("s6")) || (paramString.endsWith("s7")) || (paramString.endsWith("s8"))) && 
          (World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.35F, paramShot) > 0.0F)) {
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          debugprintln(this, "*** Aileron Controls Out..");
        }

        return;
      }
      if (paramString.startsWith("xxeng1")) {
        debuggunnery("Engine Module: Hit..");
        if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(World.Rnd().nextFloat(0.2F, 0.55F), paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 280000.0F) {
              debuggunnery("Engine Module: Engine Crank Case Hit - Engine Stucks..");
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 100000.0F) {
              debuggunnery("Engine Module: Engine Crank Case Hit - Engine Damaged..");
              this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
            }
          }
          getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 24.0F), paramShot);
        }
        if (paramString.endsWith("cyls")) {
          if ((getEnergyPastArmor(0.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 0.66F)) {
            this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 32200.0F)));
            debuggunnery("Engine Module: Cylinders Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 1000000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
              debuggunnery("Engine Module: Cylinders Hit - Engine Fires..");
            }
          }
          getEnergyPastArmor(25.0F, paramShot);
        }
        if (paramString.startsWith("xxeng1mag")) {
          i = paramString.charAt(9) - '1';
          debuggunnery("Engine Module: Magneto " + i + " Destroyed..");
          this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, i);
        }
        if (paramString.endsWith("oil1")) {
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.25F, paramShot) > 0.0F))
            debuggunnery("Engine Module: Oil Radiator Hit..");
          this.FM.AS.hitOil(paramShot.initiator, 0);
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
      if (paramString.startsWith("xxmgun1")) {
        if ((getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F)) {
          this.FM.AS.setJamBullets(0, 0);
          getEnergyPastArmor(11.98F, paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxmgun2")) {
        if ((getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F)) {
          this.FM.AS.setJamBullets(0, 1);
          getEnergyPastArmor(11.98F, paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxmgun3")) {
        if ((getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F)) {
          this.FM.AS.setJamBullets(10, World.Rnd().nextInt(0, 1));
          getEnergyPastArmor(11.98F, paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxoil")) {
        if ((getEnergyPastArmor(2.25F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          this.FM.AS.hitOil(paramShot.initiator, 0);
          getEnergyPastArmor(6.75F, paramShot);
          debuggunnery("Engine Module: Oil Tank Pierced..");
        }
        return;
      }
      if (paramString.startsWith("xxspar")) {
        debugprintln(this, "*** Spar Construction: Hit..");
        if ((paramString.startsWith("xxsparli")) && 
          (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(19.700001F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparri")) && 
          (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(19.700001F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlm")) && 
          (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparrm")) && 
          (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlo")) && 
          (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparro")) && 
          (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxspart")) && 
          (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debuggunnery("*** Tail1 Spars Damaged..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(2.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 10.25F)) {
          if (this.FM.AS.astateTankStates[i] == 0) {
            debuggunnery("Fuel Tank (" + i + "): Pierced..");
            this.FM.AS.hitTank(paramShot.initiator, i, 1);
            this.FM.AS.doSetTankState(paramShot.initiator, i, 1);
          }
          if ((World.Rnd().nextFloat() < 0.02F) || ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.5F))) {
            this.FM.AS.hitTank(paramShot.initiator, i, 2);
            debuggunnery("Fuel Tank (" + i + "): Hit..");
          }
        }
        return;
      }
      return;
    }

    if (paramString.startsWith("xCF")) {
      if (chunkDamageVisible("CF") < 3) {
        hitChunk("CF", paramShot);
      }

    }
    else if (!paramString.startsWith("xBlister"))
    {
      if (paramString.startsWith("xEng")) {
        hitChunk("Engine1", paramShot);
      } else if (paramString.startsWith("xtail")) {
        if (chunkDamageVisible("Tail1") < 3)
          hitChunk("Tail1", paramShot);
      }
      else if (paramString.startsWith("xKeel")) {
        hitChunk("Keel1", paramShot);
      } else if (paramString.startsWith("xRudder")) {
        if (chunkDamageVisible("Rudder1") < 2)
          hitChunk("Rudder1", paramShot);
      }
      else if (paramString.startsWith("xStab")) {
        if (paramString.startsWith("xStabL")) {
          hitChunk("StabL", paramShot);
        }
        if (paramString.startsWith("xStabR"))
          hitChunk("StabR", paramShot);
      }
      else if (paramString.startsWith("xvator")) {
        if ((paramString.startsWith("xvatorl")) && 
          (chunkDamageVisible("VatorL") < 2)) {
          hitChunk("VatorL", paramShot);
        }

        if ((paramString.startsWith("xvatorr")) && 
          (chunkDamageVisible("VatorR") < 2)) {
          hitChunk("VatorR", paramShot);
        }
      }
      else if (paramString.startsWith("xWing")) {
        if ((paramString.startsWith("xWingLIn")) && 
          (chunkDamageVisible("WingLIn") < 3)) {
          hitChunk("WingLIn", paramShot);
        }

        if ((paramString.startsWith("xWingRIn")) && 
          (chunkDamageVisible("WingRIn") < 3)) {
          hitChunk("WingRIn", paramShot);
        }

        if (paramString.startsWith("xWingLMid")) {
          if (chunkDamageVisible("WingLMid") < 3) {
            hitChunk("WingLMid", paramShot);
          }
          if (World.Rnd().nextFloat() < paramShot.mass + 0.02F) {
            this.FM.AS.hitOil(paramShot.initiator, 0);
          }
        }
        if ((paramString.startsWith("xWingRMid")) && 
          (chunkDamageVisible("WingRMid") < 3)) {
          hitChunk("WingRMid", paramShot);
        }

        if ((paramString.startsWith("xWingLOut")) && 
          (chunkDamageVisible("WingLOut") < 3)) {
          hitChunk("WingLOut", paramShot);
        }

        if ((paramString.startsWith("xWingROut")) && 
          (chunkDamageVisible("WingROut") < 3)) {
          hitChunk("WingROut", paramShot);
        }
      }
      else if (paramString.startsWith("xArone")) {
        if (paramString.startsWith("xAroneL")) {
          hitChunk("AroneL", paramShot);
        }
        if (paramString.startsWith("xAroneR"))
          hitChunk("AroneR", paramShot);
      }
      else if (paramString.startsWith("xGearL")) {
        hitChunk("GearL2", paramShot);
      } else if (paramString.startsWith("xGearR")) {
        hitChunk("GearR2", paramShot);
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
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 19:
      this.FM.CT.bHasArrestorControl = false;
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);

    if (this.FM.CT.getArrestor() > 0.001F)
    {
      float f;
      if (this.FM.Gears.arrestorVAngle != 0.0F) {
        f = cvt(this.FM.Gears.arrestorVAngle, -73.5F, 3.5F, 1.0F, 0.0F);
        this.arrestor = (0.8F * this.arrestor + 0.2F * f);
        moveArrestorHook(this.arrestor);
      } else {
        f = -1.224F * this.FM.Gears.arrestorVSink;
        if ((f < 0.0F) && (this.FM.getSpeedKMH() > 60.0F)) {
          Eff3DActor.New(this, this.FM.Gears.arrestorHook, null, 1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
        }
        if (f > 0.2F) {
          f = 0.2F;
        }
        if (f > 0.0F)
          this.arrestor = (0.7F * this.arrestor + 0.3F * (this.arrestor + f));
        else {
          this.arrestor = (0.3F * this.arrestor + 0.7F * (this.arrestor + f));
        }
        if (this.arrestor < 0.0F)
          this.arrestor = 0.0F;
        else if (this.arrestor > this.FM.CT.getArrestor()) {
          this.arrestor = this.FM.CT.getArrestor();
        }
        moveArrestorHook(this.arrestor);
      }
    }
  }

  static
  {
    Class localClass = SBD.class;
    Property.set(localClass, "originCountry", PaintScheme.countryUSA);
  }
}