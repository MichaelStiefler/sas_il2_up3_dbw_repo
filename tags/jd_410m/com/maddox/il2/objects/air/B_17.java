package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public abstract class B_17 extends Scheme4
  implements TypeTransport
{
  private static final float[] anglesL2 = { 0.0F, 0.0F, 0.2833333F, -19.025F, 0.6666666F, -55.466999F, 0.816667F, -71.338997F, 1.0F, -79.5F };

  private static final float[] anglesL3 = { 0.0F, 0.0F, 0.3333333F, 2.333F, 0.6666666F, -36.833F, 0.816667F, -70.944F, 1.0F, -110.0F };

  private static final float[] anglesL4 = { 0.0F, 0.0F, 0.1666667F, 8.5F, 0.5F, 27.5F, 0.8333333F, 49.5F, 1.0F, 61.0F };

  private static final float getAngleValue(float paramFloat, float[] paramArrayOfFloat)
  {
    if (paramFloat <= 0.0F) {
      return paramArrayOfFloat[1];
    }
    if (paramFloat >= 1.0F) {
      return paramArrayOfFloat[(paramArrayOfFloat.length - 1)];
    }
    for (int i = 0; i < 0.5F * paramArrayOfFloat.length; i++) {
      if ((paramArrayOfFloat[(i + i)] < paramFloat) && (paramFloat < paramArrayOfFloat[(i + i + 2)])) {
        float f = (paramFloat - paramArrayOfFloat[(i + i)]) / (paramArrayOfFloat[(i + i + 2)] - paramArrayOfFloat[(i + i)]);
        return paramArrayOfFloat[(i + i + 1)] + f * (paramArrayOfFloat[(i + i + 3)] - paramArrayOfFloat[(i + i + 1)]);
      }
    }
    return 0.0F;
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i = 0; int j = 0;

    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if ((paramString.endsWith("p1")) || (paramString.endsWith("p1")))
          getEnergyPastArmor(2.4F, paramShot);
        else if (paramString.endsWith("p9")) {
          getEnergyPastArmor(16.870000839233398D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
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
        case 2:
          if ((World.Rnd().nextFloat() < 0.125F) && 
            (getEnergyPastArmor(5.2F, paramShot) > 0.0F)) {
            debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
            this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          }

          getEnergyPastArmor(2.0F, paramShot);
          break;
        case 3:
        case 4:
          break;
        case 5:
        case 6:
          if (World.Rnd().nextFloat() >= 0.001F) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 1); break;
        case 7:
        case 8:
        case 9:
        case 10:
          j = i - 7;
          if (getEnergyPastArmor(2.2F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < 0.25F) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, j, 1);
            }
            if (World.Rnd().nextFloat() < 0.25F) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, j, 6);
            }
            if (World.Rnd().nextFloat() < 0.25F) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, j, 7);
            }
          }
          debugprintln(this, "*** Engine (" + j + ") Controls: Hit, Engine Controls (Partially) Disabled..");
          break;
        case 11:
        case 12:
          if ((getEnergyPastArmor(1.0F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.5F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          debugprintln(this, "*** Aileron Controls Out.."); break;
        case 13:
        case 14:
          if ((getEnergyPastArmor(1.0F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.5F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          debugprintln(this, "*** Elevator Controls Out.."); break;
        case 15:
        case 16:
          if ((getEnergyPastArmor(1.0F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.5F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          debugprintln(this, "*** Rudder Controls Out.."); break;
        case 17:
          break;
        case 18:
          break;
        case 19:
        }

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
          (chunkDamageVisible("Keel1") > 1) && (getEnergyPastArmor(16.6F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F)) {
          debugprintln(this, "*** Keel1 Spars Damaged..");
          nextDMGLevels(1, 2, "Keel1_D" + chunkDamageVisible("Keel1"), paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxbomb")) {
        if ((World.Rnd().nextFloat() < 0.001F) && (this.FM.CT.Weapons[3] != null) && (this.FM.CT.Weapons[3][0].haveBullets())) {
          debugprintln(this, "*** Bomb Payload Detonates..");
          this.FM.AS.hitTank(paramShot.initiator, 0, 10);
          this.FM.AS.hitTank(paramShot.initiator, 1, 10);
          this.FM.AS.hitTank(paramShot.initiator, 2, 10);
          this.FM.AS.hitTank(paramShot.initiator, 3, 10);
          nextDMGLevels(3, 2, "CF_D0", paramShot.initiator);
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
        if ((paramString.endsWith("prop")) && 
          (getEnergyPastArmor(0.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
          if (World.Rnd().nextFloat() < 0.5F)
            this.FM.EI.engines[i].setKillPropAngleDevice(paramShot.initiator);
          else {
            this.FM.EI.engines[i].setKillPropAngleDeviceSpeeds(paramShot.initiator);
          }
          getEnergyPastArmor(15.1F, paramShot);
          debugprintln(this, "*** Engine (" + i + ") Module: Prop Governor Fails..");
        }

        if ((paramString.endsWith("oil1")) && 
          (getEnergyPastArmor(0.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
          this.FM.AS.setOilState(paramShot.initiator, i, 1);
          debugprintln(this, "*** Engine (" + i + ") Module: Oil Filter Pierced..");
        }

        if ((paramString.endsWith("supc")) && 
          (getEnergyPastArmor(0.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
          this.FM.EI.engines[i].setKillCompressor(paramShot.initiator);
          debugprintln(this, "*** Engine (" + i + ") Module: Compressor Stops..");
          getEnergyPastArmor(2.6F, paramShot);
        }

        return;
      }
      if (paramString.startsWith("xxoiltank")) {
        i = 1;
        if (paramString.endsWith("2")) {
          i = 2;
        }
        if ((getEnergyPastArmor(0.21F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.2435F)) {
          this.FM.AS.hitOil(paramShot.initiator, i);
        }
        debugprintln(this, "*** Engine (" + i + ") Module: Oil Tank Pierced..");
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
      if (paramString.startsWith("xxactu0")) {
        i = paramString.charAt(7) - '1';
        if ((getEnergyPastArmor(6.3F, paramShot) > 0.0F) && (this.FM.AS.isMaster())) {
          this.FM.turret[i].bIsOperable = false;
        }
        return;
      }
      if (paramString.startsWith("xxammo"))
      {
        i = 10 * (paramString.charAt(6) - '0') + (paramString.charAt(7) - '0');

        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.05F)) {
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
            i = 12;
            j = 0;
            break;
          case 5:
            i = 13;
            j = 0;
            break;
          case 6:
            i = 13;
            j = 1;
            break;
          case 7:
            i = 14;
            j = 0;
            break;
          case 8:
            i = 14;
            j = 1;
            break;
          case 9:
            i = 15;
            j = 0;
            break;
          case 10:
            i = 16;
            j = 0;
            break;
          case 11:
            i = 17;
            j = 0;
            break;
          case 12:
            i = 17;
            j = 1;
          }

          this.FM.AS.setJamBullets(i, j);
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
      if (chunkDamageVisible("Rudder1") < 2) {
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
      if (chunkDamageVisible("VatorL") < 2) {
        hitChunk("VatorL", paramShot);
      }
      return;
    }if (paramString.startsWith("xvatorr")) {
      if (chunkDamageVisible("VatorR") < 2) {
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
      if (chunkDamageVisible("AroneL") < 2) {
        hitChunk("AroneL", paramShot);
      }
      return;
    }if (paramString.startsWith("xaroner")) {
      if (chunkDamageVisible("AroneR") < 2) {
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
    }if (paramString.startsWith("xnose"))
    {
      return;
    }if (paramString.startsWith("xmgun")) {
      i = 10 * (paramString.charAt(5) - '0') + (paramString.charAt(6) - '0');
      if ((getEnergyPastArmor(1.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.35F)) {
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
          i = 12;
          j = 0;
          break;
        case 5:
          i = 13;
          j = 0;
          break;
        case 6:
          i = 13;
          j = 1;
          break;
        case 7:
          i = 14;
          j = 0;
          break;
        case 8:
          i = 14;
          j = 1;
          break;
        case 9:
          i = 15;
          j = 0;
          break;
        case 10:
          i = 16;
          j = 0;
          break;
        case 11:
          i = 17;
          j = 0;
          break;
        case 12:
          i = 17;
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
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 27.0F * paramFloat);
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 0.0F, getAngleValue(paramFloat, anglesL2));
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 0.0F, getAngleValue(paramFloat, anglesL3));
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, 0.0F, getAngleValue(paramFloat, anglesL4));
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, 0.0F, -0.85F * paramFloat);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, 0.0F, 45.0F * paramFloat);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 0.0F, getAngleValue(paramFloat, anglesL2));
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 0.0F, getAngleValue(paramFloat, anglesL3));
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, 0.0F, getAngleValue(paramFloat, anglesL4));
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, 0.0F, -0.85F * paramFloat);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, 0.0F, 45.0F * paramFloat);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
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
    hierMesh().chunkSetAngles("Bay01_D0", 0.0F, 85.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay02_D0", 0.0F, -85.0F * paramFloat, 0.0F);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean) {
      if ((this.FM.AS.astateEngineStates[0] > 3) && 
        (World.Rnd().nextFloat() < 0.0023F)) this.FM.AS.hitTank(this, 0, 1);

      if ((this.FM.AS.astateEngineStates[1] > 3) && 
        (World.Rnd().nextFloat() < 0.0023F)) this.FM.AS.hitTank(this, 1, 1);

      if ((this.FM.AS.astateEngineStates[2] > 3) && 
        (World.Rnd().nextFloat() < 0.0023F)) this.FM.AS.hitTank(this, 2, 1);

      if ((this.FM.AS.astateEngineStates[3] > 3) && 
        (World.Rnd().nextFloat() < 0.0023F)) this.FM.AS.hitTank(this, 3, 1);

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
      this.FM.turret[1].setHealth(paramFloat);
      this.FM.turret[2].setHealth(paramFloat);
      break;
    case 4:
      this.FM.turret[3].setHealth(paramFloat);
      break;
    case 5:
      this.FM.turret[4].setHealth(paramFloat);
      break;
    case 6:
      this.FM.turret[6].setHealth(paramFloat);
      break;
    case 7:
      this.FM.turret[5].setHealth(paramFloat);
      break;
    case 8:
      this.FM.turret[7].setHealth(paramFloat);
      break;
    case 9:
    }
  }

  public void doMurderPilot(int paramInt) {
    hierMesh().chunkVisible("Pilot" + (paramInt + 1) + "_D0", false);
    hierMesh().chunkVisible("HMask" + (paramInt + 1) + "_D0", false);
    hierMesh().chunkVisible("Pilot" + (paramInt + 1) + "_D1", true);
    hierMesh().chunkVisible("Head" + (paramInt + 1) + "_D0", false);
  }

  static
  {
    Class localClass = B_17.class;
    Property.set(localClass, "originCountry", PaintScheme.countryUSA);
  }
}