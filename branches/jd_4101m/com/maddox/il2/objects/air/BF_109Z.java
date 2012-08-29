package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class BF_109Z extends Scheme2
  implements TypeFighter
{
  private float kangle = 0.0F;

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = cvt(paramFloat, 0.05F, 0.49F, 0.0F, 1.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", -33.5F * f, 0.0F, 0.0F);
    f = cvt(paramFloat, 0.12F, 0.95F, 0.0F, 1.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, 77.5F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 33.5F * f, 0.0F, 0.0F);
    f = cvt(paramFloat, 0.3F, 0.82F, 0.0F, 1.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 33.5F * f, 0.0F, 0.0F);
    f = cvt(paramFloat, 0.34F, 0.78F, 0.0F, 1.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -77.5F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", -33.5F * f, 0.0F, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }

  protected void moveElevator(float paramFloat)
  {
    hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -30.0F * paramFloat, 0.0F);
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Rudder2_D0", 0.0F, -30.0F * paramFloat, 0.0F);
  }

  public void update(float paramFloat)
  {
    hierMesh().chunkSetAngles("GearL6_D0", 0.0F, -this.FM.Gears.gWheelAngles[0], 0.0F);
    hierMesh().chunkSetAngles("GearR6_D0", 0.0F, -this.FM.Gears.gWheelAngles[1], 0.0F);
    hierMesh().chunkSetAngles("GearC4_D0", 0.0F, -this.FM.Gears.gWheelAngles[2], 0.0F);

    if (this.FM.getSpeed() > 5.0F) {
      hierMesh().chunkSetAngles("SlatL_D0", 0.0F, cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, 1.5F), 0.0F);
      hierMesh().chunkSetAngles("SlatR_D0", 0.0F, cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, 1.5F), 0.0F);
    }
    hierMesh().chunkSetAngles("Flap01L_D0", 0.0F, -20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap01U_D0", 0.0F, 20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap02L_D0", 0.0F, -20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap02U_D0", 0.0F, 20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap01L2_D0", 0.0F, -20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap01U2_D0", 0.0F, 20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap02L2_D0", 0.0F, -20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap02U2_D0", 0.0F, 20.0F * this.kangle, 0.0F);
    this.kangle = (0.95F * this.kangle + 0.05F * this.FM.EI.engines[0].getControlRadiator());
    if (this.kangle > 1.0F) this.kangle = 1.0F;

    super.update(paramFloat);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean) {
      if (this.FM.AS.astateTankStates[2] > 5) {
        this.FM.AS.repairTank(2);
      }
      if (this.FM.AS.astateTankStates[3] > 5) {
        this.FM.AS.repairTank(3);
      }
    }

    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -45.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f, 0.0F);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 3:
    case 4:
      return false;
    case 18:
      if (World.Rnd().nextFloat() >= 0.5F) break;
      cut("Keel2");
      if (World.Rnd().nextFloat() >= 0.5F) break;
      cut("Tail2"); break;
    case 13:
      cut("WingRIn");
      cut("Engine2");
      cut("Tail2");
      this.FM.cut(36, paramInt2, paramActor);
      this.FM.cut(4, paramInt2, paramActor);
      this.FM.cut(20, paramInt2, paramActor);
      if (World.Rnd().nextFloat() >= 0.5F) break;
      cut("StabR");
      this.FM.cut(18, paramInt2, paramActor);
      this.FM.cut(17, paramInt2, paramActor); break;
    case 19:
      cut("StabR");
      this.FM.cut(18, paramInt2, paramActor);
      this.FM.cut(17, paramInt2, paramActor);
      break;
    case 20:
      cut("StabR");
      this.FM.cut(18, paramInt2, paramActor);
      this.FM.cut(17, paramInt2, paramActor);
      break;
    case 36:
      cut("Engine2");
      cut("Nose");
      cut("Tail2");
      this.FM.cut(4, paramInt2, paramActor);
      this.FM.cut(13, paramInt2, paramActor);
      this.FM.cut(20, paramInt2, paramActor);
      break;
    case 9:
      cut("GearL4");
      break;
    case 10:
      cut("GearR4");
    case 5:
    case 6:
    case 7:
    case 8:
    case 11:
    case 12:
    case 14:
    case 15:
    case 16:
    case 17:
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
    case 32:
    case 33:
    case 34:
    case 35: } return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    int j;
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
          getEnergyPastArmor(10.100000381469727D / (Math.abs(v1.z) + 9.999999747378752E-006D), paramShot);
        } else if (paramString.endsWith("p5")) {
          getEnergyPastArmor(10.100000381469727D / (Math.abs(v1.x) + 9.999999747378752E-006D), paramShot);
        } else if (paramString.endsWith("p6")) {
          getEnergyPastArmor(12.100000381469727D / (Math.abs(v1.x) + 9.999999747378752E-006D), paramShot);
        } else if (paramString.endsWith("a1")) {
          if (World.Rnd().nextFloat() < 0.5F) {
            paramShot.powerType = 0;
          }
          getEnergyPastArmor(World.Rnd().nextFloat(5.0F, 7.0F), paramShot);
        }
        return;
      }

      if (paramString.startsWith("xxcontrols")) {
        i = paramString.charAt(10) - '0';
        if (paramString.length() > 11) {
          i = 10 + (paramString.charAt(11) - '0');
        }
        switch (i) {
        case 1:
        case 4:
          if (getEnergyPastArmor(0.1F, paramShot) <= 0.0F) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          debugprintln(this, "*** Aileron Controls: Control Crank Destroyed.."); break;
        case 2:
        case 3:
        case 8:
          if ((getEnergyPastArmor(0.12F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.1F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          debugprintln(this, "*** Aileron Controls: Disabled.."); break;
        case 5:
        case 6:
        case 10:
        case 11:
          if ((getEnergyPastArmor(0.002F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.1F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          debugprintln(this, "*** Elevator Controls: Disabled / Strings Broken.."); break;
        case 7:
        case 9:
          if ((getEnergyPastArmor(2.3F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.2F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          debugprintln(this, "*** Rudder Controls: Disabled.."); break;
        case 12:
          if (getEnergyPastArmor(3.2F, paramShot) <= 0.0F) break;
          debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 13:
          if (getEnergyPastArmor(0.1F, paramShot) <= 0.0F) break;
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
          debugprintln(this, "*** Throttle Quadrant: Hit, Engine Controls Disabled..");
        }

        return;
      }

      if (paramString.startsWith("xxspar")) {
        debugprintln(this, "*** Spar Construction: Hit..");
        if ((paramString.startsWith("xxspart1")) && 
          (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.5F / (float)Math.sqrt(v1.y * v1.y + v1.z * v1.z), paramShot) > 0.0F)) {
          debugprintln(this, "*** Tail1 Spars Broken in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxspart2")) && 
          (chunkDamageVisible("Tail2") > 2) && (getEnergyPastArmor(3.5F / (float)Math.sqrt(v1.y * v1.y + v1.z * v1.z), paramShot) > 0.0F)) {
          debugprintln(this, "*** Tail2 Spars Broken in Half..");
          nextDMGLevels(1, 2, "Tail2_D3", paramShot.initiator);
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

        return;
      }

      if (paramString.startsWith("xxwj")) {
        if (getEnergyPastArmor(12.5F, paramShot) > 0.0F) {
          if (paramString.endsWith("l1")) {
            debugprintln(this, "*** WingL Console Lock Destroyed..");
            nextDMGLevels(4, 2, "WingLIn_D" + chunkDamageVisible("WingLIn"), paramShot.initiator);
          }
          if ((paramString.endsWith("l2")) || (paramString.endsWith("r2"))) {
            debugprintln(this, "*** WingR Console Lock Destroyed..");
            nextDMGLevels(4, 2, "WingRIn_D" + chunkDamageVisible("WingRIn"), paramShot.initiator);
          }
          if (paramString.endsWith("r1")) {
            debugprintln(this, "*** WingR Outer Console Lock Destroyed..");
            nextDMGLevels(4, 2, "WingRMid_D" + chunkDamageVisible("WingRMid"), paramShot.initiator);
          }
        }
        return;
      }

      if (paramString.startsWith("xxlock")) {
        debugprintln(this, "*** Lock Construction: Hit..");
        if ((paramString.startsWith("xxlockr1")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** Rudder1 Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockr2")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** Rudder2 Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder2_D" + chunkDamageVisible("Rudder2"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvr")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** VatorR Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
        }

        return;
      }

      if (paramString.startsWith("xxeng")) {
        i = paramString.charAt(5) - '1';
        debugprintln(this, "*** Engine Module: Hit..");
        if (paramString.endsWith("pipe")) {
          if ((World.Rnd().nextFloat() < 0.1F) && 
            (this.FM.CT.Weapons[0] != null)) {
            this.FM.AS.setJamBullets(0, i);
            debugprintln(this, "*** Engine" + i + ": Nose Nozzle Pipe Bent..");
          }

          getEnergyPastArmor(0.3F, paramShot);
        } else if (paramString.endsWith("prop")) {
          if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.8F))
            if (World.Rnd().nextFloat() < 0.5F) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 3);
              debugprintln(this, "*** Engine" + i + ": Prop Governor Hit, Disabled..");
            } else {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 4);
              debugprintln(this, "*** Engine" + i + ": Prop Governor Hit, Damaged..");
            }
        }
        else if (paramString.endsWith("gear")) {
          if (getEnergyPastArmor(4.6F, paramShot) > 0.0F)
            if (World.Rnd().nextFloat() < 0.5F) {
              this.FM.EI.engines[i].setEngineStuck(paramShot.initiator);
              debugprintln(this, "*** Engine" + i + ": Bullet Jams Reductor Gear..");
            } else {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 3);
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 4);
              debugprintln(this, "*** Engine" + i + ": Reductor Gear Damaged, Prop Governor Failed..");
            }
        }
        else if (paramString.endsWith("supc")) {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F) {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 0);
            debugprintln(this, "*** Engine" + i + ": Supercharger Disabled..");
          }
        } else if (paramString.endsWith("feed")) {
          if ((getEnergyPastArmor(3.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F) && (this.FM.EI.engines[i].getPowerOutput() > 0.7F)) {
            this.FM.AS.hitEngine(paramShot.initiator, i, 100);
            debugprintln(this, "*** Engine" + i + ": Pressurized Fuel Line Pierced, Fuel Flamed..");
          }
        } else if (paramString.endsWith("fuel")) {
          if (getEnergyPastArmor(1.1F, paramShot) > 0.0F) {
            this.FM.EI.engines[i].setEngineStops(paramShot.initiator);
            debugprintln(this, "*** Engine" + i + ": Fuel Line Stalled, Engine Stalled..");
          }
        } else if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(2.1F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 175000.0F) {
              this.FM.AS.setEngineStuck(paramShot.initiator, i);
              debugprintln(this, "*** Engine" + i + ": Bullet Jams Crank Ball Bearing..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, i, 2);
              debugprintln(this, "*** Engine" + i + ": Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[i].getReadyness() + "..");
            }
            this.FM.EI.engines[i].setReadyness(paramShot.initiator, this.FM.EI.engines[i].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));
            debugprintln(this, "*** Engine" + i + ": Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[i].getReadyness() + "..");
          }
          getEnergyPastArmor(22.5F, paramShot);
        } else if ((paramString.endsWith("cyl1")) || (paramString.endsWith("cyl2"))) {
          if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[i].getCylindersRatio() * 1.75F)) {
            this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
            debugprintln(this, "*** Engine" + i + ": Cylinders Hit, " + this.FM.EI.engines[i].getCylindersOperable() + "/" + this.FM.EI.engines[i].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 24000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, i, 3);
              debugprintln(this, "*** Engine" + i + ": Cylinders Hit, Engine Fires..");
            }
            if (World.Rnd().nextFloat() < 0.01F) {
              this.FM.AS.setEngineStuck(paramShot.initiator, i);
              debugprintln(this, "*** Engine" + i + ": Bullet Jams Piston Head..");
            }
            getEnergyPastArmor(22.5F, paramShot);
          }
        } else if ((paramString.endsWith("mag1")) || (paramString.endsWith("mag2"))) {
          j = paramString.charAt(9) - '1';
          this.FM.EI.engines[i].setMagnetoKnockOut(paramShot.initiator, j);
          debugprintln(this, "*** Engine" + i + ": Magneto " + j + " Destroyed..");
        } else if (paramString.endsWith("sync") ? 
          (getEnergyPastArmor(2.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F) : 
          paramString.endsWith("oil1")) {
          this.FM.AS.hitOil(paramShot.initiator, i);
          debugprintln(this, "*** Engine" + i + ": Oil Radiator Hit..");
        }
        return;
      }

      if (paramString.startsWith("xxoil")) {
        if (getEnergyPastArmor(2.2F, paramShot) > 0.0F) {
          i = paramString.charAt(5) - '1';
          if (i == 7) {
            i = 0;
          }
          if (i == 8) {
            i = 1;
          }
          this.FM.AS.hitOil(paramShot.initiator, i);
          getEnergyPastArmor(0.22F, paramShot);
          debugprintln(this, "*** Engine" + i + ": Oil Tank Pierced..");
        }
        return;
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
        case 4:
          if ((getEnergyPastArmor(0.1F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.25F)) break;
          if (this.FM.AS.astateTankStates[3] == 0) {
            debugprintln(this, "*** Fuel Tank: Pierced..");
            this.FM.AS.hitTank(paramShot.initiator, 3, 1);
            this.FM.AS.doSetTankState(paramShot.initiator, 3, 1);
          }
          if ((paramShot.powerType != 3) || (World.Rnd().nextFloat() >= 0.5F)) break;
          this.FM.AS.hitTank(paramShot.initiator, 3, 2);
          debugprintln(this, "*** Fuel Tank: Hit.."); break;
        case 5:
          if ((getEnergyPastArmor(0.1F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.25F)) break;
          if (this.FM.AS.astateTankStates[1] == 0) {
            debugprintln(this, "*** Fuel Tank: Pierced..");
            this.FM.AS.hitTank(paramShot.initiator, 1, 1);
            this.FM.AS.doSetTankState(paramShot.initiator, 1, 1);
          }
          if ((paramShot.powerType != 3) || (World.Rnd().nextFloat() >= 0.5F)) break;
          this.FM.AS.hitTank(paramShot.initiator, 1, 2);
          debugprintln(this, "*** Fuel Tank: Hit..");
        }

        return;
      }

      if (paramString.startsWith("xxcannon")) {
        if (getEnergyPastArmor(4.6F, paramShot) > 0.0F) {
          i = paramString.charAt(9) - '1';
          debugprintln(this, "*** Cannon(" + i + "): Disabled..");
          this.FM.AS.setJamBullets(0, i);
          getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxammo"))
      {
        return;
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
        else if (paramPoint3d.y > 1.765D) {
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
    else if (paramString.startsWith("xtail1")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xtail2")) {
      if (chunkDamageVisible("Tail2") < 3)
        hitChunk("Tail2", paramShot);
    }
    else if (paramString.startsWith("xkeel1")) {
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xkeel2")) {
      if (chunkDamageVisible("Keel2") < 2)
        hitChunk("Keel2", paramShot);
    }
    else if (paramString.startsWith("xrudder1")) {
      if (chunkDamageVisible("Rudder1") < 1)
        hitChunk("Rudder1", paramShot);
    }
    else if (paramString.startsWith("xrudder2")) {
      if (chunkDamageVisible("Rudder2") < 1)
        hitChunk("Rudder2", paramShot);
    }
    else if (paramString.startsWith("xstab")) {
      if ((paramString.startsWith("xstabr")) && 
        (chunkDamageVisible("StabR") < 1)) {
        hitChunk("StabR", paramShot);
      }
    }
    else if (paramString.startsWith("xvator")) {
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

  static
  {
    Class localClass = BF_109Z.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "originCountry", PaintScheme.countryGermany);

    Property.set(localClass, "iconFar_shortClassName", "Bf109");
    Property.set(localClass, "meshName", "3DO/Plane/Bf-109Z/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());

    Property.set(localClass, "yearService", 1944.5F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Bf-109Z.fmd");
    Property.set(localClass, "cockpitClass", CockpitBF_109Z.class);
    Property.set(localClass, "LOSElevation", 0.7498F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 1, 9, 9, 9, 3, 3, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON05", "_CANNON04", "_ExternalDev01", "_ExternalDev03", "_ExternalDev02", "_ExternalBomb03", "_ExternalBomb03", "_ExternalBomb01", "_ExternalBomb02" });

    weaponsRegister(localClass, "default", new String[] { "MGunMK108ki 65", "MGunMK108ki 65", "MGunMK108ki 35", null, "MGunMK108ki 35", null, null, null, null, null, null, null });

    weaponsRegister(localClass, "mk103", new String[] { "MGunMK108ki 65", "MGunMK108ki 65", "MGunMK108ki 35", "MGunMK103ki 35", "MGunMK108ki 35", null, "PylonMk103", null, null, null, null, null });

    weaponsRegister(localClass, "sc500", new String[] { "MGunMK108ki 65", "MGunMK108ki 65", "MGunMK108ki 35", null, "MGunMK108ki 35", null, "PylonETC900", null, "BombGunSC500", null, null, null });

    weaponsRegister(localClass, "sc500sc250", new String[] { "MGunMK108ki 65", "MGunMK108ki 65", "MGunMK108ki 35", null, "MGunMK108ki 35", "PylonETC900", "PylonETC900", "PylonETC900", "BombGunSC500", "BombGunNull", "BombGunSC250", "BombGunSC250" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null });
  }
}