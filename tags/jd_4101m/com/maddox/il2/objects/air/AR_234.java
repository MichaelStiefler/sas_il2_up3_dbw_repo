package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.rts.CLASS;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Property;

public abstract class AR_234 extends Scheme2
  implements TypeBomber, TypeDiveBomber
{
  private float[] oldctl = { -1.0F, -1.0F };
  private float[] curctl = { -1.0F, -1.0F };
  private static final float[] gear6 = { 0.0F, -3.0F, -3.5F, -1.0F, 7.0F };
  private static final float[] gear7 = { 0.0F, -0.09835F, -0.21265F, -0.3185F, -0.3917F };

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
    }
  }

  private static final float floatindex(float paramFloat, float[] paramArrayOfFloat)
  {
    int i = (int)paramFloat;
    if (i >= paramArrayOfFloat.length - 1) return paramArrayOfFloat[(paramArrayOfFloat.length - 1)];
    if (i < 0) return paramArrayOfFloat[0];
    if (i == 0) {
      if (paramFloat > 0.0F) return paramArrayOfFloat[0] + paramFloat * (paramArrayOfFloat[1] - paramArrayOfFloat[0]);
      return paramArrayOfFloat[0];
    }
    return paramArrayOfFloat[i] + paramFloat % i * (paramArrayOfFloat[(i + 1)] - paramArrayOfFloat[i]);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, cvt(paramFloat, 0.2F, 0.65F, 0.0F, -92.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, cvt(paramFloat, 0.2F, 0.65F, 0.0F, -62.5F), 0.0F);
    if (paramFloat < 0.525F) {
      paramHierMesh.chunkSetAngles("GearC6_D0", 0.0F, cvt(paramFloat, 0.2F, 0.525F, 0.0F, -46.0F), 0.0F);
      paramHierMesh.chunkSetAngles("GearC7_D0", 0.0F, cvt(paramFloat, 0.2F, 0.525F, 0.0F, -0.25F), 0.0F);
    } else {
      paramHierMesh.chunkSetAngles("GearC6_D0", 0.0F, cvt(paramFloat, 0.525F, 0.65F, -46.0F, -73.5F), 0.0F);
      paramHierMesh.chunkSetAngles("GearC7_D0", 0.0F, cvt(paramFloat, 0.525F, 0.65F, -0.25F, -7.5F), 0.0F);
    }
    float tmp168_167 = (ypr[2] = xyz[0] = xyz[1] = xyz[2] = 0.0F); ypr[1] = tmp168_167; ypr[0] = tmp168_167;
    xyz[1] = cvt(paramFloat, 0.2F, 0.65F, 0.0F, -0.2935F);
    paramHierMesh.chunkSetLocate("GearC8_D0", xyz, ypr);
    paramHierMesh.chunkSetAngles("GearC9_D0", 0.0F, cvt(paramFloat, 0.07F, 0.32F, 0.0F, -98.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC10_D0", 0.0F, cvt(paramFloat, 0.07F, 0.32F, 0.0F, -98.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, cvt(paramFloat, 0.3F, 0.8F, 0.0F, -90.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, floatindex(cvt(paramFloat, 0.3F, 0.8F, 0.0F, 3.0F), gear6), 0.0F);
    float tmp314_313 = (ypr[2] = xyz[0] = xyz[1] = xyz[2] = 0.0F); ypr[1] = tmp314_313; ypr[0] = tmp314_313;
    xyz[1] = floatindex(cvt(paramFloat, 0.3F, 0.8F, 0.0F, 3.0F), gear7);
    paramHierMesh.chunkSetLocate("GearL7_D0", xyz, ypr);
    if (paramFloat < 0.6F) {
      paramHierMesh.chunkSetAngles("GearL8_D0", 0.0F, cvt(paramFloat, 0.3F, 0.6F, 0.0F, -76.5F), 0.0F);
      paramHierMesh.chunkSetAngles("GearL9_D0", 0.0F, cvt(paramFloat, 0.1F, 0.3F, 0.0F, -44.0F), 0.0F);
    } else {
      paramHierMesh.chunkSetAngles("GearL8_D0", 0.0F, cvt(paramFloat, 0.6F, 0.8F, -76.5F, -62.0F), 0.0F);
      paramHierMesh.chunkSetAngles("GearL9_D0", 0.0F, cvt(paramFloat, 0.6F, 0.8F, -44.0F, 0.0F), 0.0F);
    }

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, cvt(paramFloat, 0.3F, 0.8F, 0.0F, -90.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, floatindex(cvt(paramFloat, 0.3F, 0.8F, 0.0F, 3.0F), gear6), 0.0F);
    float tmp515_514 = (ypr[2] = xyz[0] = xyz[1] = xyz[2] = 0.0F); ypr[1] = tmp515_514; ypr[0] = tmp515_514;
    xyz[1] = floatindex(cvt(paramFloat, 0.3F, 0.8F, 0.0F, 3.0F), gear7);
    paramHierMesh.chunkSetLocate("GearR7_D0", xyz, ypr);
    if (paramFloat < 0.6F) {
      paramHierMesh.chunkSetAngles("GearR8_D0", 0.0F, cvt(paramFloat, 0.3F, 0.6F, 0.0F, -76.5F), 0.0F);
      paramHierMesh.chunkSetAngles("GearR9_D0", 0.0F, cvt(paramFloat, 0.1F, 0.3F, 0.0F, -44.0F), 0.0F);
    } else {
      paramHierMesh.chunkSetAngles("GearR8_D0", 0.0F, cvt(paramFloat, 0.6F, 0.8F, -76.5F, -62.0F), 0.0F);
      paramHierMesh.chunkSetAngles("GearR9_D0", 0.0F, cvt(paramFloat, 0.6F, 0.8F, -44.0F, 0.0F), 0.0F);
    }
  }
  protected void moveGear(float paramFloat) {
    moveGear(hierMesh(), paramFloat);
  }
  public void moveWheelSink() { resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.32F, 0.0F, -0.3274F);
    hierMesh().chunkSetLocate("GearL3_D0", xyz, ypr);
    hierMesh().chunkSetAngles("GearL4_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.32F, 0.0F, -46.0F), 0.0F);
    hierMesh().chunkSetAngles("GearL5_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.32F, 0.0F, -46.0F), 0.0F);
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.32F, 0.0F, -0.3274F);
    hierMesh().chunkSetLocate("GearR3_D0", xyz, ypr);
    hierMesh().chunkSetAngles("GearR4_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.32F, 0.0F, -46.0F), 0.0F);
    hierMesh().chunkSetAngles("GearR5_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.32F, 0.0F, -46.0F), 0.0F); }

  public void moveSteering(float paramFloat) {
    if (this.FM.CT.getGear() > 0.8F)
      hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i = 0;

    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor")) {
        debuggunnery("Armor: Hit..");
        if (paramString.startsWith("xxarmorp")) {
          getEnergyPastArmor(15.149999618530273D / (Math.abs(v1.x) + 9.999999747378752E-006D), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcannon")) {
        if (paramString.endsWith("01")) {
          debuggunnery("Armament System: Left Rear Cannon: Disabled..");
          this.FM.AS.setJamBullets(0, 0);
        }
        if (paramString.endsWith("02")) {
          debuggunnery("Armament System: Right Rear Cannon: Disabled..");
          this.FM.AS.setJamBullets(0, 1);
        }
        getEnergyPastArmor(World.Rnd().nextFloat(6.98F, 24.35F), paramShot);
        return;
      }

      if (paramString.startsWith("xxcontrols")) {
        i = paramString.charAt(10) - '0';
        if (paramString.length() == 12) {
          i = 10 + (paramString.charAt(11) - '0');
        }
        switch (i) {
        case 1:
          if (getEnergyPastArmor(3.2F, paramShot) <= 0.0F) break;
          debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 2:
          if (getEnergyPastArmor(0.1F, paramShot) <= 0.0F) break;
          debugprintln(this, "*** Throttle Quadrant: Hit, Engine Controls Disabled..");
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
          if (World.Rnd().nextFloat() < 0.5F)
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          else
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 1); break;
        case 3:
          if (getEnergyPastArmor(1.0F, paramShot) <= 0.0F) break;
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1); break;
        case 7:
          if (getEnergyPastArmor(1.0F, paramShot) <= 0.0F) break;
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1); break;
        case 4:
        case 6:
        case 8:
        case 10:
          if ((getEnergyPastArmor(2.1F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.25F)) break;
          debugprintln(this, "*** Aileron Controls: Disabled..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 5:
        case 9:
          if (getEnergyPastArmor(4.1F, paramShot) <= 0.0F) break;
          debugprintln(this, "*** Aileron Controls Crank: Disabled..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 11:
        case 12:
          if (getEnergyPastArmor(0.3F, paramShot) <= 0.0F) break;
          debugprintln(this, "*** Rudder Controls: Disabled / Strings Broken..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
        }

        return;
      }

      if (paramString.startsWith("xxEng")) {
        i = paramString.charAt(5) - '1';
        if (paramPoint3d.x > 0.0D) {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F) {
            debugprintln(this, "*** Engine Module(s): Supercharger Disabled..");
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 0);
          }
        } else {
          if (getEnergyPastArmor(3.2F, paramShot) > 0.0F) {
            this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, 1);
          }
          if ((World.Rnd().nextFloat(0.009F, 0.1357F) < paramShot.mass) && (this.FM.EI.engines[i].getStage() == 6)) {
            this.FM.AS.hitEngine(paramShot.initiator, i, 1);
          }
          getEnergyPastArmor(14.296F, paramShot);
        }
        return;
      }

      if (paramString.startsWith("xxLock")) {
        debuggunnery("Lock Construction: Hit..");
        if ((paramString.startsWith("xxLockR")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxLockVL")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: VatorL Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxLockVR")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: VatorR Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxLockAL")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: AroneL Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxLockAR")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: AroneR Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), paramShot.initiator);
        }

      }

      if (paramString.startsWith("xxSpar")) {
        debuggunnery("Spar Construction: Hit..");
        if ((paramString.startsWith("xxSparLI")) && 
          (chunkDamageVisible("WingLIn") > 2) && (World.Rnd().nextFloat() > Math.abs(v1.x) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLIn Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxSparrI")) && 
          (chunkDamageVisible("WingRIn") > 2) && (World.Rnd().nextFloat() > Math.abs(v1.x) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingRIn Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxSparlm")) && 
          (chunkDamageVisible("WingLMid") > 2) && (World.Rnd().nextFloat() > Math.abs(v1.x) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLMid Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxSparrm")) && 
          (chunkDamageVisible("WingRMid") > 2) && (World.Rnd().nextFloat() > Math.abs(v1.x) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingRMid Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxSparlo")) && 
          (chunkDamageVisible("WingLOut") > 2) && (World.Rnd().nextFloat() > Math.abs(v1.x) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingLOut Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxSparro")) && 
          (chunkDamageVisible("WingROut") > 2) && (World.Rnd().nextFloat() > Math.abs(v1.x) + 0.119999997317791D) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F)) {
          debuggunnery("Spar Construction: WingROut Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxSpart")) && 
          (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.86F / (float)Math.sqrt(v1.y * v1.y + v1.z * v1.z), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          debuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }

      }

      if (paramString.startsWith("xxTank")) {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F) && (paramShot.powerType == 3)) {
          this.FM.AS.hitTank(paramShot.initiator, i, 2);
        }
        return;
      }

      return;
    }

    if (paramString.startsWith("xcf")) {
      if (chunkDamageVisible("CF") < 3) {
        hitChunk("CF", paramShot);
      }
      if (paramPoint3d.x > 0.5D) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
      }
      else if (paramPoint3d.y > 0.0D)
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
      else {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
      }

      if (World.Rnd().nextFloat() < 0.05F)
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
    }
    else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel")) {
      hitChunk("Keel1", paramShot);
    } else if (paramString.startsWith("xrudder")) {
      if (chunkDamageVisible("Rudder1") < 1)
        hitChunk("Rudder1", paramShot);
    }
    else if (paramString.startsWith("xstab")) {
      if (paramString.startsWith("xstabl")) {
        hitChunk("StabL", paramShot);
      }
      if (paramString.startsWith("xstabr"))
        hitChunk("StabR", paramShot);
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
        (chunkDamageVisible("AroneR") < 1)) {
        hitChunk("AroneR", paramShot);
      }
    }
    else if (paramString.startsWith("xengine1")) {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xengine2")) {
      if (chunkDamageVisible("Engine2") < 2)
        hitChunk("Engine2", paramShot);
    }
    else if (paramString.startsWith("xgear")) {
      if ((paramString.endsWith("1")) && 
        (World.Rnd().nextFloat() < 0.05F)) {
        debuggunnery("Hydro System: Disabled..");
        this.FM.AS.setInternalDamage(paramShot.initiator, 0);
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

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if ((paramBoolean) && 
      (World.Rnd().nextFloat() < 0.04F)) {
      if ((this.FM.AS.astateEngineStates[0] > 3) && 
        (World.Rnd().nextFloat() < 0.12F)) {
        this.FM.AS.explodeEngine(this, 0);
        msgCollision(this, "WingLIn_D0", "WingLIn_D0");
        if (World.Rnd().nextBoolean()) this.FM.AS.hitTank(this, 0, World.Rnd().nextInt(1, 8)); else {
          this.FM.AS.hitTank(this, 1, World.Rnd().nextInt(1, 8));
        }
      }
      if ((this.FM.AS.astateEngineStates[1] > 3) && 
        (World.Rnd().nextFloat() < 0.12F)) {
        this.FM.AS.explodeEngine(this, 1);
        msgCollision(this, "WingRIn_D0", "WingRIn_D0");
        if (World.Rnd().nextBoolean()) this.FM.AS.hitTank(this, 0, World.Rnd().nextInt(1, 8)); else {
          this.FM.AS.hitTank(this, 1, World.Rnd().nextInt(1, 8));
        }
      }

    }

    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
  }

  public void doEjectCatapult()
  {
    new MsgAction(false, this) {
      public void doAction(Object paramObject) { Aircraft localAircraft = (Aircraft)paramObject;
        if (!Actor.isValid(localAircraft)) return;
        Loc localLoc1 = new Loc();
        Loc localLoc2 = new Loc();
        Vector3d localVector3d = new Vector3d(0.0D, 0.0D, 10.0D);
        HookNamed localHookNamed = new HookNamed(localAircraft, "_ExternalSeat01");
        localAircraft.pos.getAbs(localLoc2);

        localHookNamed.computePos(localAircraft, localLoc2, localLoc1);

        localLoc1.transform(localVector3d);
        localVector3d.x += localAircraft.FM.Vwld.x;
        localVector3d.y += localAircraft.FM.Vwld.y;
        localVector3d.z += localAircraft.FM.Vwld.z;
        new EjectionSeat(3, localLoc1, localVector3d, localAircraft);
      }
    };
    hierMesh().chunkVisible("Seat_D0", false);
  }

  public void update(float paramFloat)
  {
    if (this.FM.AS.isMaster()) {
      for (int i = 0; i < 2; i++) {
        if (this.curctl[i] == -1.0F)
        {
          float tmp57_54 = this.FM.EI.engines[i].getControlThrottle(); this.oldctl[i] = tmp57_54; this.curctl[i] = tmp57_54;
        } else {
          this.curctl[i] = this.FM.EI.engines[i].getControlThrottle();
          if (((this.curctl[i] - this.oldctl[i]) / paramFloat > 3.0F) && (this.FM.EI.engines[i].getRPM() < 2400.0F) && (this.FM.EI.engines[i].getStage() == 6) && (World.Rnd().nextFloat() < 0.25F)) {
            this.FM.AS.hitEngine(this, i, 100);
          }
          if (((this.curctl[i] - this.oldctl[i]) / paramFloat < -3.0F) && (this.FM.EI.engines[i].getRPM() < 2400.0F) && (this.FM.EI.engines[i].getStage() == 6)) {
            if ((World.Rnd().nextFloat() < 0.25F) && ((this.FM instanceof RealFlightModel)) && (((RealFlightModel)this.FM).isRealMode())) {
              this.FM.EI.engines[i].setEngineStops(this);
            }
            if ((World.Rnd().nextFloat() < 0.75F) && ((this.FM instanceof RealFlightModel)) && (((RealFlightModel)this.FM).isRealMode())) {
              this.FM.EI.engines[i].setKillCompressor(this);
            }
          }
          this.oldctl[i] = this.curctl[i];
        }
      }

      if (Config.isUSE_RENDER()) {
        if ((this.FM.EI.engines[0].getPowerOutput() > 0.8F) && (this.FM.EI.engines[0].getStage() == 6)) {
          if (this.FM.EI.engines[0].getPowerOutput() > 0.95F)
            this.FM.AS.setSootState(this, 0, 3);
          else
            this.FM.AS.setSootState(this, 0, 2);
        }
        else {
          this.FM.AS.setSootState(this, 0, 0);
        }
        if ((this.FM.EI.engines[1].getPowerOutput() > 0.8F) && (this.FM.EI.engines[1].getStage() == 6)) {
          if (this.FM.EI.engines[1].getPowerOutput() > 0.95F)
            this.FM.AS.setSootState(this, 1, 3);
          else
            this.FM.AS.setSootState(this, 1, 2);
        }
        else {
          this.FM.AS.setSootState(this, 1, 0);
        }
      }
    }
    super.update(paramFloat);
  }

  static
  {
    Class localClass = CLASS.THIS();
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
  }
}