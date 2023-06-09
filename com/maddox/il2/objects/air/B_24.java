package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public abstract class B_24 extends Scheme4
  implements TypeTransport
{
  private float fCSink = 0.0F;
  private float fCSteer = 0.0F;

  private float[] flapps = { 0.0F, 0.0F, 0.0F, 0.0F };
  private float fGunPos = 1.0F;
  private int iGunPos = 1;
  private long btme = -1L;

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Rudder2_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    this.fCSteer = (22.200001F * paramFloat);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -35.5F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i = 0; int j = 0;

    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxammo")) {
        i = paramString.charAt(6) - '0';
        if (paramString.length() > 7) {
          i = 10;
        }
        if ((getEnergyPastArmor(6.87F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.05F)) {
          switch (i) {
          case 1:
            i = 10;
            j = 0;
            break;
          case 2:
            i = 10;
            j = 1;
            break;
          case 3:
            i = 11;
            j = 0;
            break;
          case 4:
            i = 11;
            j = 1;
            break;
          case 5:
            i = 12;
            j = 0;
            break;
          case 6:
            i = 12;
            j = 1;
            break;
          case 7:
            i = 13;
            j = 0;
            break;
          case 8:
            i = 14;
            j = 0;
            break;
          case 9:
            i = 15;
            j = 0;
            break;
          case 10:
            i = 15;
            j = 1;
          }

          this.FM.AS.setJamBullets(i, j);
          return;
        }
      }
      if (paramString.startsWith("xxcontrols")) {
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
        case 2:
          if ((getEnergyPastArmor(1.0F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.5F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          debugprintln(this, "*** Aileron Controls Out.."); break;
        case 3:
          if ((World.Rnd().nextFloat() < 0.125F) && 
            (getEnergyPastArmor(5.2F, paramShot) > 0.0F)) {
            debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
            this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          }

          getEnergyPastArmor(2.0F, paramShot);
          break;
        case 4:
        case 5:
        case 6:
          if ((World.Rnd().nextFloat() < 0.252F) && 
            (getEnergyPastArmor(5.2F, paramShot) > 0.0F)) {
            if (World.Rnd().nextFloat() < 0.125F) {
              this.FM.AS.setControlsDamage(paramShot.initiator, 2);
            }
            if (World.Rnd().nextFloat() < 0.125F) {
              this.FM.AS.setControlsDamage(paramShot.initiator, 1);
            }
          }

          getEnergyPastArmor(2.0F, paramShot);
        }

        return;
      }
      if (paramString.startsWith("xxeng")) {
        i = paramString.charAt(5) - '1';
        if (paramString.endsWith("case")) {
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
        if (paramString.endsWith("cyls")) {
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
        if (paramString.endsWith("mag1")) {
          this.FM.EI.engines[i].setMagnetoKnockOut(paramShot.initiator, 0);
          debugprintln(this, "*** Engine (" + i + ") Module: Magneto #0 Destroyed..");
          getEnergyPastArmor(25.0F, paramShot);
        }
        if (paramString.endsWith("mag2")) {
          this.FM.EI.engines[i].setMagnetoKnockOut(paramShot.initiator, 1);
          debugprintln(this, "*** Engine (" + i + ") Module: Magneto #1 Destroyed..");
          getEnergyPastArmor(25.0F, paramShot);
        }
        if ((paramString.endsWith("oil1")) && 
          (getEnergyPastArmor(0.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
          this.FM.AS.setOilState(paramShot.initiator, i, 1);
          debugprintln(this, "*** Engine (" + i + ") Module: Oil Filter Pierced..");
        }

        return;
      }
      if (paramString.startsWith("xxlock")) {
        debuggunnery("Lock Construction: Hit..");
        if ((paramString.startsWith("xxlockr1")) && 
          (getEnergyPastArmor(6.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockr2")) && 
          (getEnergyPastArmor(6.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: Rudder2 Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder2_D" + chunkDamageVisible("Rudder2"), paramShot.initiator);
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
      if (paramString.startsWith("xxpnm")) {
        if (getEnergyPastArmor(World.Rnd().nextFloat(0.25F, 1.22F), paramShot) > 0.0F) {
          debuggunnery("Pneumo System: Disabled..");
          this.FM.AS.setInternalDamage(paramShot.initiator, 1);
        }
        return;
      }
      if (paramString.startsWith("xxradio")) {
        getEnergyPastArmor(World.Rnd().nextFloat(5.0F, 25.0F), paramShot);

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

        if ((paramString.startsWith("xxspark1")) && 
          (chunkDamageVisible("Keel1") > 1) && (getEnergyPastArmor(16.6F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debugprintln(this, "*** Keel1 Spars Damaged..");
          nextDMGLevels(1, 2, "Keel1_D" + chunkDamageVisible("Keel1"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxspark2")) && 
          (chunkDamageVisible("Keel2") > 1) && (getEnergyPastArmor(16.6F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debugprintln(this, "*** Keel2 Spars Damaged..");
          nextDMGLevels(1, 2, "Keel2_D" + chunkDamageVisible("Keel2"), paramShot.initiator);
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
        i = paramString.charAt(6) - '1';
        if (getEnergyPastArmor(0.06F, paramShot) > 0.0F) {
          if (this.FM.AS.astateTankStates[i] == 0) {
            this.FM.AS.hitTank(paramShot.initiator, i, 1);
            this.FM.AS.doSetTankState(paramShot.initiator, i, 1);
          }
          if (paramShot.powerType == 3) {
            if (paramShot.power < 16100.0F) {
              if ((this.FM.AS.astateTankStates[i] < 4) && (World.Rnd().nextFloat() < 0.21F))
                this.FM.AS.hitTank(paramShot.initiator, i, 1);
            }
            else
              this.FM.AS.hitTank(paramShot.initiator, i, World.Rnd().nextInt(1, 1 + (int)(paramShot.power / 16100.0F)));
          }
          else if (paramShot.power > 16100.0F) {
            this.FM.AS.hitTank(paramShot.initiator, i, World.Rnd().nextInt(1, 1 + (int)(paramShot.power / 16100.0F)));
          }
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
    }if (paramString.startsWith("xkeel1")) {
      if (chunkDamageVisible("Keel1") < 2) {
        hitChunk("Keel1", paramShot);
      }
      return;
    }if (paramString.startsWith("xkeel2")) {
      if (chunkDamageVisible("Keel2") < 2) {
        hitChunk("Keel2", paramShot);
      }
      return;
    }if (paramString.startsWith("xrudder1")) {
      if (chunkDamageVisible("Rudder1") < 1) {
        hitChunk("Rudder1", paramShot);
      }
      return;
    }if (paramString.startsWith("xrudder2")) {
      if (chunkDamageVisible("Rudder2") < 1) {
        hitChunk("Rudder2", paramShot);
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
    }if (paramString.startsWith("xgear")) {
      if (World.Rnd().nextFloat() < 0.05F) {
        debugprintln(this, "*** Gear Hydro Failed..");
        this.FM.Gears.setHydroOperable(false);
      }
      return;
    }if (paramString.startsWith("xturret"))
    {
      return;
    }if (paramString.startsWith("xmgun")) {
      i = 10 * (paramString.charAt(5) - '0') + (paramString.charAt(6) - '0');
      if ((getEnergyPastArmor(6.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.35F)) {
        switch (i) {
        case 1:
          i = 10;
          j = 0;
          break;
        case 2:
          i = 10;
          j = 1;
          break;
        case 3:
          i = 11;
          j = 0;
          break;
        case 4:
          i = 11;
          j = 1;
          break;
        case 5:
          i = 12;
          j = 0;
          break;
        case 6:
          i = 12;
          j = 1;
          break;
        case 7:
          i = 13;
          j = 0;
          break;
        case 8:
          i = 14;
          j = 0;
          break;
        case 9:
          i = 15;
          j = 0;
          break;
        case 10:
          i = 15;
          j = 1;
        }

        this.FM.AS.setJamBullets(i, j);
      }
      return;
    }if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
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
      return;
    }
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    setExplosion(paramExplosion);
    if ((paramExplosion.chunkName != null) && (paramExplosion.power > 0.0F)) {
      if (paramExplosion.chunkName.equals("Tail1_D3")) {
        return;
      }
      if (paramExplosion.chunkName.equals("WingLIn_D3")) {
        return;
      }
      if (paramExplosion.chunkName.equals("WingRIn_D3")) {
        return;
      }
      if (paramExplosion.chunkName.equals("WingLMid_D3")) {
        return;
      }
      if (paramExplosion.chunkName.equals("WingRMid_D3")) {
        return;
      }
      if (paramExplosion.chunkName.equals("WingLOut_D3")) {
        return;
      }
      if (paramExplosion.chunkName.equals("WingROut_D3")) {
        return;
      }
    }
    super.msgExplosion(paramExplosion);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, cvt(paramFloat, 0.05F, 0.75F, 0.0F, -55.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC10_D0", 0.0F, cvt(paramFloat, 0.05F, 0.75F, 0.0F, -60.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC8_D0", 0.0F, cvt(paramFloat, 0.01F, 0.1F, 0.0F, -140.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC9_D0", 0.0F, cvt(paramFloat, 0.01F, 0.1F, 0.0F, -140.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, cvt(paramFloat, 0.12F, 0.99F, 0.0F, -95.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, cvt(paramFloat, 0.12F, 0.99F, 0.0F, -30.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, cvt(paramFloat, 0.12F, 0.99F, 0.0F, 180.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, cvt(paramFloat, 0.12F, 0.99F, 0.0F, -98.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, cvt(paramFloat, 0.12F, 0.99F, 0.0F, -20.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, cvt(paramFloat, 0.02F, 0.82F, 0.0F, -95.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, cvt(paramFloat, 0.02F, 0.82F, 0.0F, -30.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, cvt(paramFloat, 0.02F, 0.82F, 0.0F, 180.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, cvt(paramFloat, 0.02F, 0.82F, 0.0F, -98.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, cvt(paramFloat, 0.02F, 0.82F, 0.0F, -20.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) {
    moveGear(hierMesh(), paramFloat);
  }
  public void moveWheelSink() { this.fCSink = cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.5F, 0.0F, 0.5F);
    resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.456F, 0.0F, 0.2821F);
    hierMesh().chunkSetLocate("GearL3_D0", xyz, ypr);
    resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.456F, 0.0F, 0.2821F);
    hierMesh().chunkSetLocate("GearR3_D0", xyz, ypr);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 33:
      hitProp(1, paramInt2, paramActor);
      this.FM.EI.engines[1].setEngineStuck(paramActor);
      this.FM.AS.hitTank(paramActor, 1, World.Rnd().nextInt(0, 9));
    case 34:
      hitProp(0, paramInt2, paramActor);
      this.FM.EI.engines[0].setEngineStuck(paramActor);
      this.FM.AS.hitTank(paramActor, 0, World.Rnd().nextInt(2, 8));
      this.FM.AS.hitTank(paramActor, 1, World.Rnd().nextInt(0, 5));
    case 35:
      this.FM.AS.hitTank(paramActor, 0, World.Rnd().nextInt(0, 4));
      break;
    case 36:
      hitProp(2, paramInt2, paramActor);
      this.FM.EI.engines[2].setEngineStuck(paramActor);
      this.FM.AS.hitTank(paramActor, 2, World.Rnd().nextInt(0, 9));
    case 37:
      hitProp(3, paramInt2, paramActor);
      this.FM.EI.engines[3].setEngineStuck(paramActor);
      this.FM.AS.hitTank(paramActor, 2, World.Rnd().nextInt(0, 5));
      this.FM.AS.hitTank(paramActor, 3, World.Rnd().nextInt(2, 8));
    case 38:
      this.FM.AS.hitTank(paramActor, 3, World.Rnd().nextInt(0, 4));
      break;
    case 25:
      this.FM.turret[0].bIsOperable = false;
      return false;
    case 26:
      this.FM.turret[1].bIsOperable = false;
      return false;
    case 27:
      this.FM.turret[2].bIsOperable = false;
      return false;
    case 28:
      this.FM.turret[3].bIsOperable = false;
      return false;
    case 29:
      this.FM.turret[4].bIsOperable = false;
      return false;
    case 30:
      this.FM.turret[5].bIsOperable = false;
      return false;
    case 19:
      killPilot(this, 5);
      killPilot(this, 6);
      killPilot(this, 7);
      killPilot(this, 8);
      cut("StabL");
      cut("StabR");
      break;
    case 13:
      killPilot(this, 0);
      killPilot(this, 1);
      killPilot(this, 2);
      killPilot(this, 3);
      break;
    case 17:
      cut("Keel1");
      hierMesh().chunkVisible("Keel1_CAP", false);
      break;
    case 18:
      cut("Keel2");
      hierMesh().chunkVisible("Keel2_CAP", false);
    case 14:
    case 15:
    case 16:
    case 20:
    case 21:
    case 22:
    case 23:
    case 24:
    case 31:
    case 32: } return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void moveBayDoor(float paramFloat)
  {
    for (int i = 1; i < 10; i++) {
      hierMesh().chunkSetAngles("Bay0" + i + "_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    }
    for (i = 10; i < 13; i++)
      hierMesh().chunkSetAngles("Bay" + i + "_D0", 0.0F, -30.0F * paramFloat, 0.0F);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean) {
      if ((this.FM.AS.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.04F)) nextDMGLevel(this.FM.AS.astateEffectChunks[0] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.04F)) nextDMGLevel(this.FM.AS.astateEffectChunks[1] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[2] > 4) && (World.Rnd().nextFloat() < 0.04F)) nextDMGLevel(this.FM.AS.astateEffectChunks[2] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[3] > 4) && (World.Rnd().nextFloat() < 0.04F)) nextDMGLevel(this.FM.AS.astateEffectChunks[3] + "0", 0, this);

      if ((!(this.FM instanceof RealFlightModel)) || (!((RealFlightModel)this.FM).isRealMode())) {
        for (i = 0; i < this.FM.EI.getNum(); i++) {
          if ((this.FM.AS.astateEngineStates[i] <= 3) || (World.Rnd().nextFloat() >= 0.2F)) continue; this.FM.EI.engines[i].setExtinguisherFire();
        }
      }
    }

    for (int i = 1; i < 10; i++)
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt) {
    case 0:
      break;
    case 1:
      break;
    case 2:
      this.FM.turret[0].setHealth(paramFloat);
      break;
    case 3:
      break;
    case 4:
      this.FM.turret[1].setHealth(paramFloat);
      break;
    case 5:
      this.FM.turret[2].setHealth(paramFloat);
      break;
    case 6:
      this.FM.turret[3].setHealth(paramFloat);
      break;
    case 7:
      this.FM.turret[4].setHealth(paramFloat);
      break;
    case 8:
      this.FM.turret[5].setHealth(paramFloat);
    }
  }

  public void doMurderPilot(int paramInt) {
    hierMesh().chunkVisible("Pilot" + (paramInt + 1) + "_D0", false);
    hierMesh().chunkVisible("HMask" + (paramInt + 1) + "_D0", false);
    hierMesh().chunkVisible("Pilot" + (paramInt + 1) + "_D1", true);
    hierMesh().chunkVisible("Head" + (paramInt + 1) + "_D0", false);
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    for (int i = 0; i < 4; i++) {
      float f = this.FM.EI.engines[i].getControlRadiator();
      if (Math.abs(this.flapps[i] - f) > 0.01F) {
        this.flapps[i] = f;
        for (int j = 1; j < 9; j++) {
          hierMesh().chunkSetAngles("Water" + (i * 8 + j) + "_D0", 0.0F, -20.0F * f, 0.0F);
        }
      }
    }

    if (this.FM.CT.getGear() > 0.9F) {
      resetYPRmodifier();
      xyz[1] = this.fCSink;
      ypr[1] = this.fCSteer;
      hierMesh().chunkSetLocate("GearC3_D0", xyz, ypr);
    }

    if (this.iGunPos == 0) {
      if (this.fGunPos > 0.0F) {
        this.fGunPos -= 0.2F * paramFloat;
        resetYPRmodifier();
        xyz[1] = (-0.635F + 0.635F * this.fGunPos);
        hierMesh().chunkSetLocate("Turret3C_D0", xyz, ypr);
      }
    }
    else if ((this.iGunPos == 1) && 
      (this.fGunPos < 1.0F)) { this.fGunPos += 0.2F * paramFloat;
      resetYPRmodifier();
      xyz[1] = (-0.635F + 0.635F * this.fGunPos);
      hierMesh().chunkSetLocate("Turret3C_D0", xyz, ypr);
      if ((this.fGunPos <= 0.8F) || (this.fGunPos >= 0.9F)); }
    if (this.FM.AS.isMaster()) {
      if ((this.FM.turret[2].target != null) && (this.FM.AS.astatePilotStates[5] < 90)) {
        this.iGunPos = 1;
      }
      if (Time.current() > this.btme) {
        this.btme = (Time.current() + World.Rnd().nextLong(5000L, 12000L));
        if (this.FM.turret[2].target == null)
        {
          this.iGunPos = 0;
        }
      }
    }
  }

  static
  {
    Class localClass = B_24.class;
    Property.set(localClass, "originCountry", PaintScheme.countryUSA);
  }
}