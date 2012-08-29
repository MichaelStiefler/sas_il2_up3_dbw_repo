package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public abstract class BF_109 extends Scheme1
  implements TypeFighter
{
  protected void moveFlap(float paramFloat)
  {
    float f = -45.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
  }

  public void moveWheelSink()
  {
    resetYPRmodifier();
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.3F, 0.0F, 0.3F);
    hierMesh().chunkSetLocate("GearL99_D0", xyz, ypr);
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.3F, 0.0F, 0.3F);
    hierMesh().chunkSetLocate("GearR99_D0", xyz, ypr);
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("HMask1_D0", false);
      if (this.FM.AS.bIsAboutToBailout) break;
      if (hierMesh().isChunkVisible("Blister1_D0")) {
        hierMesh().chunkVisible("Gore1_D0", true);
      }
      hierMesh().chunkVisible("Gore2_D0", true);
    }
  }

  public boolean cut(String paramString)
  {
    if (paramString.startsWith("Tail1")) {
      this.FM.AS.hitTank(this, 2, 100);
    }
    return super.cut(paramString);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if ((paramBoolean) && 
      (this.FM.AS.astateTankStates[0] > 5)) {
      this.FM.AS.repairTank(0);
    }

    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor")) {
        debugprintln(this, "*** Armor: Hit..");
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(World.Rnd().nextFloat(20.0F, 30.0F), paramShot);
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
          debugprintln(this, "*** Armor Glass: Hit..");
          if (paramShot.power <= 0.0F) {
            debugprintln(this, "*** Armor Glass: Bullet Stopped..");
            if (World.Rnd().nextFloat() < 0.5F)
              doRicochetBack(paramShot);
          }
        }
        else if (paramString.endsWith("p2")) {
          getEnergyPastArmor(0.5F, paramShot);
        } else if (paramString.endsWith("p3")) {
          if (paramPoint3d.z < -0.27D)
            getEnergyPastArmor(4.099999904632568D / (Math.abs(v1.z) + 9.999999747378752E-006D), paramShot);
          else
            getEnergyPastArmor(8.100000381469727D / (Math.abs(v1.x) + 9.999999747378752E-006D), paramShot);
        }
        else if (paramString.endsWith("p4")) {
          getEnergyPastArmor(9.0D / (Math.abs(v1.z) + 9.999999747378752E-006D), paramShot);
        } else if (paramString.endsWith("p5")) {
          getEnergyPastArmor(9.0D / (Math.abs(v1.x) + 9.999999747378752E-006D), paramShot);
        } else if (paramString.endsWith("p6")) {
          getEnergyPastArmor(9.0D / (Math.abs(v1.x) + 9.999999747378752E-006D), paramShot);
        } else if (paramString.endsWith("a1")) {
          if (World.Rnd().nextFloat() < 0.5F) {
            paramShot.powerType = 0;
          }
          getEnergyPastArmor(World.Rnd().nextFloat(1.0F, 3.0F), paramShot);
        }
        return;
      }

      if (paramString.startsWith("xxcontrols")) {
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
        case 4:
          if (getEnergyPastArmor(0.1F, paramShot) <= 0.0F) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          debugprintln(this, "*** Aileron Controls: Control Crank Destroyed.."); break;
        case 2:
        case 3:
          if ((getEnergyPastArmor(0.12F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.1F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          debugprintln(this, "*** Aileron Controls: Disabled.."); break;
        case 5:
        case 6:
          if ((getEnergyPastArmor(0.002F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.1F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          debugprintln(this, "*** Elevator Controls: Disabled / Strings Broken.."); break;
        case 7:
          if ((getEnergyPastArmor(2.3F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.2F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          debugprintln(this, "*** Rudder Controls: Disabled.."); break;
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

      }

      if (paramString.startsWith("xxspar")) {
        debugprintln(this, "*** Spar Construction: Hit..");
        if ((paramString.startsWith("xxspart")) && 
          (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.5F / (float)Math.sqrt(v1.y * v1.y + v1.z * v1.z), paramShot) > 0.0F)) {
          debugprintln(this, "*** Tail1 Spars Broken in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparli")) && 
          (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparri")) && 
          (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlm")) && 
          (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparrm")) && 
          (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlo")) && 
          (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparro")) && 
          (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

      }

      if ((paramString.startsWith("xxwj")) && 
        (getEnergyPastArmor(12.5F, paramShot) > 0.0F)) {
        if (paramString.endsWith("l")) {
          debugprintln(this, "*** WingL Console Lock Destroyed..");
          nextDMGLevels(4, 2, "WingLIn_D" + chunkDamageVisible("WingLIn"), paramShot.initiator);
        } else {
          debugprintln(this, "*** WingR Console Lock Destroyed..");
          nextDMGLevels(4, 2, "WingRIn_D" + chunkDamageVisible("WingRIn"), paramShot.initiator);
        }

      }

      if (paramString.startsWith("xxlock")) {
        debugprintln(this, "*** Lock Construction: Hit..");
        if ((paramString.startsWith("xxlockr")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** Rudder Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvl")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** VatorL Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvr")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** VatorR Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
        }

      }

      if (paramString.startsWith("xxeng")) {
        debugprintln(this, "*** Engine Module: Hit..");
        if (paramString.endsWith("pipe")) {
          if ((World.Rnd().nextFloat() < 0.1F) && 
            (this.FM.CT.Weapons[1] != null) && (this.FM.CT.Weapons[1].length != 2)) {
            this.FM.AS.setJamBullets(1, 0);
            debugprintln(this, "*** Engine Module: Nose Nozzle Pipe Bent..");
          }

          getEnergyPastArmor(0.3F, paramShot);
        } else if (paramString.endsWith("prop")) {
          if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.8F))
            if (World.Rnd().nextFloat() < 0.5F) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 3);
              debugprintln(this, "*** Engine Module: Prop Governor Hit, Disabled..");
            } else {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 4);
              debugprintln(this, "*** Engine Module: Prop Governor Hit, Damaged..");
            }
        }
        else if (paramString.endsWith("gear")) {
          if (getEnergyPastArmor(4.6F, paramShot) > 0.0F)
            if (World.Rnd().nextFloat() < 0.5F) {
              this.FM.EI.engines[0].setEngineStuck(paramShot.initiator);
              debugprintln(this, "*** Engine Module: Bullet Jams Reductor Gear..");
            } else {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 3);
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 4);
              debugprintln(this, "*** Engine Module: Reductor Gear Damaged, Prop Governor Failed..");
            }
        }
        else if (paramString.endsWith("supc")) {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 0);
            debugprintln(this, "*** Engine Module: Supercharger Disabled..");
          }
        } else if (paramString.endsWith("feed")) {
          if ((getEnergyPastArmor(3.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F) && (this.FM.EI.engines[0].getPowerOutput() > 0.7F)) {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 100);
            debugprintln(this, "*** Engine Module: Pressurized Fuel Line Pierced, Fuel Flamed..");
          }
        } else if (paramString.endsWith("fuel")) {
          if (getEnergyPastArmor(1.1F, paramShot) > 0.0F) {
            this.FM.EI.engines[0].setEngineStops(paramShot.initiator);
            debugprintln(this, "*** Engine Module: Fuel Line Stalled, Engine Stalled..");
          }
        } else if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(2.1F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 175000.0F) {
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
              debugprintln(this, "*** Engine Module: Bullet Jams Crank Ball Bearing..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
              debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
            }
            this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
            debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
          }
          getEnergyPastArmor(22.5F, paramShot);
        } else if (paramString.startsWith("xxeng1cyl")) {
          if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 1.75F)) {
            this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
            debugprintln(this, "*** Engine Module: Cylinders Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 24000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, 0, 3);
              debugprintln(this, "*** Engine Module: Cylinders Hit, Engine Fires..");
            }
            if (World.Rnd().nextFloat() < 0.01F) {
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
              debugprintln(this, "*** Engine Module: Bullet Jams Piston Head..");
            }
            getEnergyPastArmor(22.5F, paramShot);
          }
        } else if (paramString.startsWith("xxeng1mag")) {
          i = paramString.charAt(9) - '1';
          this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, i);
          debugprintln(this, "*** Engine Module: Magneto " + i + " Destroyed..");
        } else if (paramString.endsWith("sync")) {
          if ((getEnergyPastArmor(2.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
            debugprintln(this, "*** Engine Module: Gun Synchronized Hit, Nose Guns Lose Authority..");
            this.FM.AS.setJamBullets(0, 0);
            this.FM.AS.setJamBullets(0, 1);
          }
        } else if (paramString.endsWith("oil1")) {
          this.FM.AS.hitOil(paramShot.initiator, 0);
          debugprintln(this, "*** Engine Module: Oil Radiator Hit..");
        }
      }

      if (paramString.startsWith("xxoil")) {
        this.FM.AS.hitOil(paramShot.initiator, 0);
        getEnergyPastArmor(0.22F, paramShot);
        debugprintln(this, "*** Engine Module: Oil Tank Pierced..");
      }

      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '0';
        switch (i) {
        case 1:
        case 2:
          if ((getEnergyPastArmor(0.1F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.25F)) break;
          if (this.FM.AS.astateTankStates[2] == 0) {
            debugprintln(this, "*** Fuel Tank: Pierced..");
            this.FM.AS.hitTank(paramShot.initiator, 2, 1);
            this.FM.AS.doSetTankState(paramShot.initiator, 2, 1);
          }
          if ((paramShot.powerType != 3) || (World.Rnd().nextFloat() >= 0.5F)) break;
          this.FM.AS.hitTank(paramShot.initiator, 2, 2);
          debugprintln(this, "*** Fuel Tank: Hit.."); break;
        case 3:
          if (World.Rnd().nextFloat() >= 0.05F) break;
          debugprintln(this, "*** MW50 Tank: Pierced..");
          this.FM.AS.setInternalDamage(paramShot.initiator, 2); break;
        case 4:
          if ((getEnergyPastArmor(1.7F, paramShot) <= 0.0F) || (((paramShot.powerType != 3) || (World.Rnd().nextFloat() >= 0.5F)) && (World.Rnd().nextFloat() >= 0.25F)))
          {
            break;
          }
          debugprintln(this, "*** Nitrogen Oxyde Tank: Pierced, Nitros Flamed..");
          this.FM.AS.hitTank(paramShot.initiator, 0, 100);
          this.FM.AS.hitTank(paramShot.initiator, 1, 100);
          this.FM.AS.hitTank(paramShot.initiator, 2, 100);
        }

      }

      if (paramString.startsWith("xxmgun")) {
        if (paramString.endsWith("01")) {
          debugprintln(this, "*** Cowling Gun: Disabled..");
          this.FM.AS.setJamBullets(0, 0);
        }
        if (paramString.endsWith("02")) {
          debugprintln(this, "*** Cowling Gun: Disabled..");
          this.FM.AS.setJamBullets(0, 1);
        }
        if (paramString.endsWith("l")) {
          debugprintln(this, "*** Wing Gun (L): Disabled..");
          this.FM.AS.setJamBullets(1, 0);
        }
        if (paramString.endsWith("r")) {
          debugprintln(this, "*** Wing Gun (L): Disabled..");
          this.FM.AS.setJamBullets(1, 1);
        }
        getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 12.96F), paramShot);
      }
      if (paramString.startsWith("xxcannon")) {
        debugprintln(this, "*** Nose Cannon: Disabled..");
        this.FM.AS.setJamBullets(1, 0);
        getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F), paramShot);
      }
      if (paramString.startsWith("xxammo")) {
        if (World.Rnd().nextFloat(3800.0F, 30000.0F) < paramShot.power) {
          if (paramString.endsWith("01")) {
            debugprintln(this, "*** Cowling Gun: Ammo Feed Chain Broken..");
            this.FM.AS.setJamBullets(0, 0);
          }
          if (paramString.endsWith("02")) {
            debugprintln(this, "*** Cowling Gun: Ammo Feed Chain Broken..");
            this.FM.AS.setJamBullets(0, 1);
          }
          if (paramString.endsWith("l")) {
            debugprintln(this, "*** Wing Gun (L): Ammo Feed Drum Damaged..");
            this.FM.AS.setJamBullets(1, 0);
          }
          if (paramString.endsWith("r")) {
            debugprintln(this, "*** Wing Gun (R): Ammo Feed Drum Damaged..");
            this.FM.AS.setJamBullets(1, 1);
          }
        }
        getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 28.33F), paramShot);
      }
      return;
    }

    if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit"))) {
      if (chunkDamageVisible("CF") < 3) {
        hitChunk("CF", paramShot);
      }
      if (paramString.startsWith("xcockpit")) {
        if (paramPoint3d.z > 0.4D) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
          if (World.Rnd().nextFloat() < 0.1F) {
            this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
          }
        }
        else if (paramPoint3d.y > 0.0D) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
        } else {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
        }

        if (paramPoint3d.x > 0.2D)
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
      }
    }
    else if (paramString.startsWith("xeng")) {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel")) {
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xrudder")) {
      if (chunkDamageVisible("Rudder1") < 1)
        hitChunk("Rudder1", paramShot);
    }
    else if (paramString.startsWith("xstab")) {
      if ((paramString.startsWith("xstabl")) && 
        (chunkDamageVisible("StabL") < 2)) {
        hitChunk("StabL", paramShot);
      }

      if ((paramString.startsWith("xstabr")) && 
        (chunkDamageVisible("StabR") < 1)) {
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

  public void msgExplosion(Explosion paramExplosion)
  {
    setExplosion(paramExplosion);
    if ((paramExplosion.chunkName != null) && (paramExplosion.power > 0.0F) && 
      (paramExplosion.chunkName.startsWith("Tail1"))) {
      if (World.Rnd().nextFloat(0.0F, 0.038F) < paramExplosion.power) {
        this.FM.AS.setControlsDamage(paramExplosion.initiator, 1);
      }
      if (World.Rnd().nextFloat(0.0F, 0.042F) < paramExplosion.power) {
        this.FM.AS.setControlsDamage(paramExplosion.initiator, 2);
      }
    }

    super.msgExplosion(paramExplosion);
  }

  static
  {
    Class localClass = BF_109.class;
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
  }
}