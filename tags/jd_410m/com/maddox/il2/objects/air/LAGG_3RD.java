package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class LAGG_3RD extends LAGG_3
{
  private float oldctl = -1.0F;
  private float curctl = -1.0F;

  public void update(float paramFloat)
  {
    if (this.FM.AS.isMaster()) {
      if (this.curctl == -1.0F) {
        this.curctl = (this.oldctl = this.FM.EI.engines[0].getControlThrottle());
      } else {
        this.curctl = this.FM.EI.engines[0].getControlThrottle();
        if (((this.curctl - this.oldctl) / paramFloat > 3.0F) && (this.FM.EI.engines[0].getRPM() < 2100.0F) && (this.FM.EI.engines[0].getStage() == 6) && (World.Rnd().nextFloat() < 0.25F)) {
          this.FM.AS.hitEngine(this, 0, 100);
        }
        if (((this.curctl - this.oldctl) / paramFloat < -3.0F) && (this.FM.EI.engines[0].getRPM() < 2100.0F) && (this.FM.EI.engines[0].getStage() == 6)) {
          if ((World.Rnd().nextFloat() < 0.25F) && ((this.FM instanceof RealFlightModel)) && (((RealFlightModel)this.FM).isRealMode())) {
            this.FM.EI.engines[0].setEngineStops(this);
          }
          if ((World.Rnd().nextFloat() < 0.75F) && ((this.FM instanceof RealFlightModel)) && (((RealFlightModel)this.FM).isRealMode())) {
            this.FM.EI.engines[0].setKillCompressor(this);
          }
        }
        this.oldctl = this.curctl;
      }
    }

    if ((Config.isUSE_RENDER()) && 
      (this.FM.AS.isMaster())) {
      if ((this.FM.EI.engines[0].getPowerOutput() > 0.8F) && (this.FM.EI.engines[0].getStage() == 6)) {
        if (this.FM.EI.engines[0].getPowerOutput() > 0.95F)
          this.FM.AS.setSootState(this, 0, 3);
        else
          this.FM.AS.setSootState(this, 0, 2);
      }
      else {
        this.FM.AS.setSootState(this, 0, 0);
      }

    }

    if (this.FM.getSpeed() > 5.0F) {
      hierMesh().chunkSetAngles("SlatL_D0", 0.0F, cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, 1.2F), 0.0F);
      hierMesh().chunkSetAngles("SlatR_D0", 0.0F, cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, 1.2F), 0.0F);
    }

    super.update(paramFloat);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 80.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -80.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 100.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 100.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC99_D0", 0.0F, -75.0F * paramFloat, 0.0F);
    float f = Math.max(-paramFloat * 1200.0F, -80.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, -f, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, paramFloat, 0.0F);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxammo")) {
        if (getEnergyPastArmor(4.81F, paramShot) > 0.0F) {
          if (World.Rnd().nextFloat() < 0.12F) {
            this.FM.AS.setJamBullets(1, 0);
          }
          if (World.Rnd().nextFloat() < 0.12F) {
            this.FM.AS.setJamBullets(1, 1);
          }
          if (World.Rnd().nextFloat() < 0.012F) {
            this.FM.AS.explodeTank(paramShot.initiator, 0);
          }
          getEnergyPastArmor(8.98F, paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(12.7F / (1.0E-005F + (float)Math.abs(v1.x)), paramShot);
        } else if (paramString.endsWith("g1")) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
          getEnergyPastArmor(18.150002F / (1.0E-005F + (float)Math.abs(v1.x)), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        if (Pd.x > -0.3549999892711639D) {
          if ((World.Rnd().nextFloat() < 0.1F) || (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
            if (World.Rnd().nextFloat() < 0.1F)
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          }
        }
        else if (Pd.x > -1.284999966621399D) {
          if (World.Rnd().nextFloat() < 0.2F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          }
          if (World.Rnd().nextFloat() < 0.2F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          }
          if (World.Rnd().nextFloat() < 0.2F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          }
        }
        else if (getEnergyPastArmor(0.31F, paramShot) > 0.0F) {
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
        }

        return;
      }
      if (paramString.startsWith("xxspar")) {
        if (((paramString.endsWith("li1")) || (paramString.endsWith("li2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(2.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("ri1")) || (paramString.endsWith("ri2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(2.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("lm1")) || (paramString.endsWith("lm2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(2.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("rm1")) || (paramString.endsWith("rm2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(2.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("lo1")) || (paramString.endsWith("lo2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("ro1")) || (paramString.endsWith("ro2"))) && 
          (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("sl1")) || (paramString.endsWith("sl2"))) && 
          (chunkDamageVisible("StabL") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** StabL Spars Damaged..");
          nextDMGLevels(1, 2, "StabL_D3", paramShot.initiator);
        }

        if (((paramString.endsWith("sr1")) || (paramString.endsWith("sr2"))) && 
          (chunkDamageVisible("StabR") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** StabR Spars Damaged..");
          nextDMGLevels(1, 2, "StabR_D3", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxlock")) {
        if (paramString.endsWith("al")) {
          if (getEnergyPastArmor(0.35F, paramShot) > 0.0F) {
            debugprintln(this, "*** AroneL Lock Damaged..");
            nextDMGLevels(1, 2, "AroneL_D0", paramShot.initiator);
          }
        } else if (paramString.endsWith("ar")) {
          if (getEnergyPastArmor(0.35F, paramShot) > 0.0F) {
            debugprintln(this, "*** AroneR Lock Damaged..");
            nextDMGLevels(1, 2, "AroneR_D0", paramShot.initiator);
          }
        } else if ((paramString.endsWith("vl1")) || (paramString.endsWith("vl2"))) {
          if (getEnergyPastArmor(0.35F, paramShot) > 0.0F) {
            debugprintln(this, "*** VatorL Lock Damaged..");
            nextDMGLevels(1, 2, "VatorL_D0", paramShot.initiator);
          }
        } else if ((paramString.endsWith("vr1")) || (paramString.endsWith("vr2"))) {
          if (getEnergyPastArmor(0.35F, paramShot) > 0.0F) {
            debugprintln(this, "*** VatorR Lock Damaged..");
            nextDMGLevels(1, 2, "VatorR_D0", paramShot.initiator);
          }
        } else if (((paramString.endsWith("r1")) || (paramString.endsWith("r2"))) && 
          (getEnergyPastArmor(0.35F, paramShot) > 0.0F)) {
          debugprintln(this, "*** Rudder1 Lock Damaged..");
          nextDMGLevels(1, 2, "Rudder1_D0", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxeng1")) {
        if ((paramPoint3d.x > 1.054D) && (paramPoint3d.x < 1.417D)) {
          this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, 6));
        }
        if (World.Rnd().nextFloat(0.009F, 0.1357F) < paramShot.mass) {
          this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
        }
        getEnergyPastArmor(14.296F, paramShot);
        return;
      }
      if (paramString.startsWith("xxoil")) {
        this.FM.AS.hitOil(paramShot.initiator, 0);
        getEnergyPastArmor(0.1F, paramShot);
        return;
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F) && (paramShot.powerType == 3)) {
          this.FM.AS.hitTank(paramShot.initiator, i, 2);
        }
        return;
      }
      if (paramString.startsWith("xxshvak")) {
        i = paramString.charAt(7) - '1';
        this.FM.AS.setJamBullets(1, i);
        getEnergyPastArmor(12.0F, paramShot);
        return;
      }
      if (paramString.startsWith("xxpneu")) {
        this.FM.Gears.setHydroOperable(false);
        return;
      }
      return;
    }

    if (((paramString.startsWith("xcf")) || ((paramString.startsWith("xcockpit")) && (Pd.z > 0.7329999804496765D))) && 
      (chunkDamageVisible("CF") < 3)) {
      hitChunk("CF", paramShot);
    }

    if (paramString.startsWith("xxcockpit")) {
      if (World.Rnd().nextFloat() < 0.1F) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
      }
      if (Pd.y < 0.0D) {
        if (World.Rnd().nextFloat() < 0.25F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
        }
        if (World.Rnd().nextFloat() < 0.25F)
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
      }
      else {
        if (World.Rnd().nextFloat() < 0.25F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
        }
        if (World.Rnd().nextFloat() < 0.25F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
        }
      }
      if ((Pd.z > 0.6389999985694885D) && (Pd.x < -1.041000008583069D))
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
    }
    else if (paramString.startsWith("xeng")) {
      hitChunk("Engine1", paramShot);
    } else if (paramString.startsWith("xtail")) {
      hitChunk("Tail1", paramShot);
    } else if (paramString.startsWith("xkeel")) {
      hitChunk("Keel1", paramShot);
    } else if (paramString.startsWith("xrudder")) {
      if (chunkDamageVisible("Rudder1") < 1)
        hitChunk("Rudder1", paramShot);
    }
    else if (paramString.startsWith("xstab")) {
      if ((paramString.startsWith("xstabl")) && 
        (chunkDamageVisible("StabL") < 2)) {
        hitChunk("StabL", paramShot);
      }

      if ((paramString.startsWith("xstabr")) && 
        (chunkDamageVisible("StabR") < 2)) {
        hitChunk("StabR", paramShot);
      }
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
        (chunkDamageVisible("AroneL") < 1)) {
        hitChunk("AroneR", paramShot);
      }
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

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "LaGG");
    Property.set(localClass, "meshName", "3DO/Plane/LaGG-3RD(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/LaGG-3RD.fmd");
    Property.set(localClass, "cockpitClass", CockpitLAGG_3RD.class);
    Property.set(localClass, "LOSElevation", 0.90695F);

    weaponTriggersRegister(localClass, new int[] { 1, 1 });
    weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02" });

    weaponsRegister(localClass, "default", new String[] { "MGunShVAKki 240", "MGunShVAKki 240" });

    weaponsRegister(localClass, "none", new String[] { null, null });
  }
}