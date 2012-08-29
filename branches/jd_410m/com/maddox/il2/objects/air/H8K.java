package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public abstract class H8K extends Scheme4
  implements TypeSailPlane, TypeTransport
{
  private static Point3d tmpp = new Point3d();

  private float[] flapps = { 0.0F, 0.0F, 0.0F, 0.0F };

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    int j;
    for (int i = 0; i < 3; i++) for (j = 0; j < 2; j++) {
        if (this.FM.Gears.clpGearEff[i][j] != null) {
          tmpp.set(this.FM.Gears.clpGearEff[i][j].pos.getAbsPoint());
          tmpp.z = 0.01D;
          this.FM.Gears.clpGearEff[i][j].pos.setAbs(tmpp);
          this.FM.Gears.clpGearEff[i][j].pos.reset();
        }
      }

    for (i = 0; i < 4; i++) {
      float f = this.FM.EI.engines[i].getControlRadiator();
      if (Math.abs(this.flapps[i] - f) > 0.01F) {
        this.flapps[i] = f;
        for (j = 1; j < 9; j++)
          hierMesh().chunkSetAngles("Water" + (i * 8 + j) + "_D0", 0.0F, -40.0F * f, 0.0F);
      }
    }
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -45.5F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i = 0;

    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxammo")) {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.15F)) {
          this.FM.AS.setJamBullets(10 + i, 0);
        }
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
        case 2:
        case 3:
          if ((getEnergyPastArmor(1.0F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.5F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          debugprintln(this, "*** Aileron Controls Out.."); break;
        case 6:
          if ((getEnergyPastArmor(1.0F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.5F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 1); break;
        case 4:
        case 5:
          if ((getEnergyPastArmor(1.0F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.5F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
        }

        return;
      }
      if (paramString.startsWith("xxengine")) {
        i = paramString.charAt(8) - '1';
        if (paramString.endsWith("base")) {
          if (getEnergyPastArmor(0.2F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 140000.0F) {
              this.FM.AS.setEngineStuck(paramShot.initiator, i);
              debugprintln(this, "*** Engine (" + i + ") Crank Case Hit - Engine Stucks..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 85000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, i, 2);
              debugprintln(this, "*** Engine (" + i + ") Crank Case Hit - Engine Damaged..");
            }
          }
          else if (World.Rnd().nextFloat() < 0.005F) {
            this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, 1);
          } else {
            this.FM.EI.engines[i].setReadyness(paramShot.initiator, this.FM.EI.engines[i].getReadyness() - 0.00082F);
            debugprintln(this, "*** Engine (" + i + ") Crank Case Hit - Readyness Reduced to " + this.FM.EI.engines[i].getReadyness() + "..");
          }

          getEnergyPastArmor(12.0F, paramShot);
        }
        if (paramString.endsWith("cyl")) {
          if ((getEnergyPastArmor(5.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[i].getCylindersRatio() * 0.75F)) {
            this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 19000.0F)));
            debugprintln(this, "*** Engine (" + i + ") Cylinders Hit, " + this.FM.EI.engines[i].getCylindersOperable() + "/" + this.FM.EI.engines[i].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 18000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, i, 2);
              debugprintln(this, "*** Engine (" + i + ") Cylinders Hit - Engine Fires..");
            }
          }
          getEnergyPastArmor(25.0F, paramShot);
        }
        if ((paramString.endsWith("sup")) && 
          (getEnergyPastArmor(0.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
          this.FM.EI.engines[i].setKillCompressor(paramShot.initiator);
          debugprintln(this, "*** Engine (" + i + ") Module: Compressor Stops..");
          getEnergyPastArmor(2.6F, paramShot);
        }

        return;
      }
      if (paramString.startsWith("xxgun")) {
        i = paramString.charAt(5) - '1';
        if ((getEnergyPastArmor(5.7F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.96F)) {
          this.FM.AS.setJamBullets(10 + i, 0);
          getEnergyPastArmor(29.950001F, paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxlock")) {
        debuggunnery("Lock Construction: Hit..");
        if ((paramString.startsWith("xxlockr")) && 
          (getEnergyPastArmor(6.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvl")) && 
          (getEnergyPastArmor(6.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: VatorL Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvr")) && 
          (getEnergyPastArmor(6.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: VatorR Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockal")) && 
          (getEnergyPastArmor(6.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: AroneL Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockar")) && 
          (getEnergyPastArmor(6.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: AroneR Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxoil")) {
        i = paramString.charAt(5) - '1';
        if ((getEnergyPastArmor(0.21F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.2435F)) {
          this.FM.AS.hitOil(paramShot.initiator, i);
        }
        debugprintln(this, "*** Engine (" + i + ") Module: Oil Tank Pierced..");
        return;
      }
      if (paramString.startsWith("xxspar")) {
        if ((paramString.startsWith("xxsparli")) && 
          (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(19.6F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.125F)) {
          debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparri")) && 
          (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(19.6F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.125F)) {
          debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlm")) && 
          (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(16.799999F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.125F)) {
          debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparrm")) && 
          (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(16.799999F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.125F)) {
          debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlo")) && 
          (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(16.6F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.125F)) {
          debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparro")) && 
          (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(16.6F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.125F)) {
          debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxspark")) && 
          (chunkDamageVisible("Keel1") > 1) && (getEnergyPastArmor(16.6F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.125F)) {
          debugprintln(this, "*** Keel1 Spars Damaged..");
          nextDMGLevels(1, 2, "Keel1_D" + chunkDamageVisible("Keel1"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparsl")) && 
          (chunkDamageVisible("StabL") > 1) && (getEnergyPastArmor(16.6F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.125F)) {
          debugprintln(this, "*** StabL: Spars Damaged..");
          nextDMGLevels(1, 2, "StabL_D" + chunkDamageVisible("StabL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparsr")) && 
          (chunkDamageVisible("StabR") > 1) && (getEnergyPastArmor(16.6F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.125F)) {
          debugprintln(this, "*** StabR: Spars Damaged..");
          nextDMGLevels(1, 2, "StabR_D" + chunkDamageVisible("StabR"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxspart")) && 
          (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(16.6F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.125F)) {
          debugprintln(this, "*** Tail1: Spars Damaged..");
          nextDMGLevels(1, 2, "Tail1_D" + chunkDamageVisible("Tail1"), paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '0';
        if (paramString.length() == 8) {
          i = 10;
        }
        switch (i) {
        case 1:
        case 2:
        case 3:
          doHitMeATank(paramShot, 2);
          break;
        case 4:
        case 5:
        case 6:
          doHitMeATank(paramShot, 3);
          break;
        case 7:
        case 8:
        case 9:
        case 10:
          doHitMeATank(paramShot, World.Rnd().nextInt(0, 1));
        }

        return;
      }
      return;
    }

    if (paramString.startsWith("xcf")) {
      if (chunkDamageVisible("CF") < 3) {
        hitChunk("CF", paramShot);
      }
      return;
    }if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3) {
        hitChunk("Tail1", paramShot);
      }
      return;
    }if (paramString.startsWith("xkeel")) {
      if (chunkDamageVisible("Keel1") < 2) {
        hitChunk("Keel1", paramShot);
      }
      return;
    }if (paramString.startsWith("xrudder")) {
      if (chunkDamageVisible("Rudder1") < 1) {
        hitChunk("Rudder1", paramShot);
      }
      return;
    }if (paramString.startsWith("xstabl")) {
      if (chunkDamageVisible("StabL") < 2) {
        hitChunk("StabL", paramShot);
      }
      return;
    }if (paramString.startsWith("xstabr")) {
      if (chunkDamageVisible("StabR") < 2) {
        hitChunk("StabR", paramShot);
      }
      return;
    }if (paramString.startsWith("xvatorl")) {
      if (chunkDamageVisible("VatorL") < 1) {
        hitChunk("VatorL", paramShot);
      }
      return;
    }if (paramString.startsWith("xvatorr")) {
      if (chunkDamageVisible("VatorR") < 1) {
        hitChunk("VatorR", paramShot);
      }
      return;
    }if (paramString.startsWith("xwinglin")) {
      if (chunkDamageVisible("WingLIn") < 3) {
        hitChunk("WingLIn", paramShot);
      }
      return;
    }if (paramString.startsWith("xwingrin")) {
      if (chunkDamageVisible("WingRIn") < 3) {
        hitChunk("WingRIn", paramShot);
      }
      return;
    }if (paramString.startsWith("xwinglmid")) {
      if (chunkDamageVisible("WingLMid") < 3) {
        hitChunk("WingLMid", paramShot);
      }
      return;
    }if (paramString.startsWith("xwingrmid")) {
      if (chunkDamageVisible("WingRMid") < 3) {
        hitChunk("WingRMid", paramShot);
      }
      return;
    }if (paramString.startsWith("xwinglout")) {
      if (chunkDamageVisible("WingLOut") < 3) {
        hitChunk("WingLOut", paramShot);
      }
      return;
    }if (paramString.startsWith("xwingrout")) {
      if (chunkDamageVisible("WingROut") < 3) {
        hitChunk("WingROut", paramShot);
      }
      return;
    }if (paramString.startsWith("xaronel")) {
      if (chunkDamageVisible("AroneL") < 1) {
        hitChunk("AroneL", paramShot);
      }
      return;
    }if (paramString.startsWith("xaroner")) {
      if (chunkDamageVisible("AroneR") < 1) {
        hitChunk("AroneR", paramShot);
      }
      return;
    }if (paramString.startsWith("xengine1")) {
      if (chunkDamageVisible("Engine1") < 2) {
        hitChunk("Engine1", paramShot);
      }
      return;
    }if (paramString.startsWith("xengine2")) {
      if (chunkDamageVisible("Engine2") < 2) {
        hitChunk("Engine2", paramShot);
      }
      return;
    }if (paramString.startsWith("xengine3")) {
      if (chunkDamageVisible("Engine3") < 2) {
        hitChunk("Engine3", paramShot);
      }
      return;
    }if (paramString.startsWith("xengine4")) {
      if (chunkDamageVisible("Engine4") < 2) {
        hitChunk("Engine4", paramShot);
      }
      return;
    }if (paramString.startsWith("xnose")) {
      if (chunkDamageVisible("Nose") < 2) {
        hitChunk("Nose", paramShot);
      }
      return;
    }if (paramString.startsWith("xgearr")) {
      hitChunk("GearR2", paramShot);
      return;
    }if (paramString.startsWith("xgearl")) {
      hitChunk("GearL2", paramShot);
      return;
    }if (paramString.startsWith("xturret"))
    {
      return;
    }if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
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
      return;
    }
  }

  private final void doHitMeATank(Shot paramShot, int paramInt) {
    if (getEnergyPastArmor(0.2F, paramShot) > 0.0F)
      if (paramShot.power < 14100.0F) {
        if ((paramShot.powerType == 3) && (this.FM.AS.astateTankStates[paramInt] > 0) && (World.Rnd().nextFloat() < 0.25F)) {
          this.FM.AS.hitTank(paramShot.initiator, paramInt, World.Rnd().nextInt(1, 2));
        } else if (this.FM.AS.astateTankStates[paramInt] == 0) {
          this.FM.AS.hitTank(paramShot.initiator, paramInt, 1);
          this.FM.AS.doSetTankState(paramShot.initiator, paramInt, 1);
        }
      }
      else this.FM.AS.hitTank(paramShot.initiator, paramInt, World.Rnd().nextInt(0, (int)(paramShot.power / 56000.0F)));
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 19:
      killPilot(this, 5);
      break;
    case 13:
      killPilot(this, 2);
      break;
    case 33:
      hitProp(1, paramInt2, paramActor);
    case 34:
      hitProp(0, paramInt2, paramActor);
      break;
    case 35:
      this.FM.AS.hitTank(paramActor, 0, World.Rnd().nextInt(0, 5));
      break;
    case 36:
      hitProp(2, paramInt2, paramActor);
    case 37:
      hitProp(3, paramInt2, paramActor);
      break;
    case 38:
      this.FM.AS.hitTank(paramActor, 3, World.Rnd().nextInt(0, 5));
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

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    for (int i = 1; i < 7; i++)
      if (i != 5)
        if (this.FM.getAltitude() < 3000.0F)
          hierMesh().chunkVisible("HMask" + i + "_D0", false);
        else
          hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  static
  {
    Class localClass = H8K.class;
    Property.set(localClass, "originCountry", PaintScheme.countryJapan);
  }
}