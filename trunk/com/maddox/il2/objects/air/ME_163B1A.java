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
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Property;

public class ME_163B1A extends Scheme1
  implements TypeFighter, TypeBNZFighter
{
  private boolean bCockpitNVentilated = false;
  public boolean bCartAttached = true;
  private Eff3DActor flame = null; private Eff3DActor dust = null; private Eff3DActor trail = null; private Eff3DActor sprite = null; private Eff3DActor turboexhaust = null;
  private float oldThtl;
  private float oldVwld;
  private float dynamoOrient = 0.0F;
  private boolean bDynamoRotary = false;
  private int pk = 0;

  public void destroy()
  {
    if (Actor.isValid(this.flame)) this.flame.destroy();
    if (Actor.isValid(this.dust)) this.dust.destroy();
    if (Actor.isValid(this.trail)) this.trail.destroy();
    if (Actor.isValid(this.sprite)) this.sprite.destroy();
    if (Actor.isValid(this.turboexhaust)) this.turboexhaust.destroy();
    super.destroy();
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if (Config.isUSE_RENDER()) {
      this.flame = Eff3DActor.New(this, findHook("_Engine1EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboHWK109F.eff", -1.0F);
      this.dust = Eff3DActor.New(this, findHook("_Engine1EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboHWK109D.eff", -1.0F);
      this.trail = Eff3DActor.New(this, findHook("_Engine1EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboHWK109T.eff", -1.0F);
      this.sprite = Eff3DActor.New(this, findHook("_Engine1EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboHWK109S.eff", -1.0F);
      this.turboexhaust = Eff3DActor.New(this, findHook("_Engine1ES_02"), null, 1.0F, "3DO/Effects/Aircraft/WhiteOxySmallGND.eff", -1.0F);
      Eff3DActor.setIntesity(this.flame, 0.0F);
      Eff3DActor.setIntesity(this.dust, 0.0F);
      Eff3DActor.setIntesity(this.trail, 0.0F);
      Eff3DActor.setIntesity(this.sprite, 0.0F);
      Eff3DActor.setIntesity(this.turboexhaust, 1.0F);
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("1")) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
          if (getEnergyPastArmor(World.Rnd().nextFloat(30.0F, 90.0F) / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot) < 0.0F)
            doRicochet(paramShot);
        }
        else if (paramString.endsWith("2")) {
          getEnergyPastArmor(13.130000114440918D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
          if ((getEnergyPastArmor(2.2F / (float)Math.sqrt(v1.y * v1.y + v1.z * v1.z), paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.25F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 2); break;
        case 2:
          if (getEnergyPastArmor(2.2F / (float)Math.sqrt(v1.y * v1.y + v1.z * v1.z), paramShot) <= 0.0F) break;
          if (World.Rnd().nextFloat() < 0.25F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          }
          if (World.Rnd().nextFloat() >= 0.25F) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 3:
        case 4:
          if ((getEnergyPastArmor(2.2F, paramShot) <= 0.0F) || 
            (World.Rnd().nextFloat() >= 0.1F)) break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
        }

        return;
      }
      if (paramString.startsWith("xxspar")) {
        if ((paramString.startsWith("xxsparli")) && 
          (chunkDamageVisible("WingLIn") > 2) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (getEnergyPastArmor(17.799999237060547D / (1.000100016593933D - Math.abs(v1.y)), paramShot) > 0.0F)) {
          debuggunnery("*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparri")) && 
          (chunkDamageVisible("WingRIn") > 2) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (getEnergyPastArmor(17.799999237060547D / (1.000100016593933D - Math.abs(v1.y)), paramShot) > 0.0F)) {
          debuggunnery("*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlm")) && 
          (chunkDamageVisible("WingLMid") > 2) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (getEnergyPastArmor(17.799999237060547D / (1.000100016593933D - Math.abs(v1.y)), paramShot) > 0.0F)) {
          debuggunnery("*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparrm")) && 
          (chunkDamageVisible("WingRMid") > 2) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (getEnergyPastArmor(17.799999237060547D / (1.000100016593933D - Math.abs(v1.y)), paramShot) > 0.0F)) {
          debuggunnery("*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlo")) && 
          (chunkDamageVisible("WingLOut") > 2) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (getEnergyPastArmor(17.799999237060547D / (1.000100016593933D - Math.abs(v1.y)), paramShot) > 0.0F)) {
          debuggunnery("*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparro")) && 
          (chunkDamageVisible("WingROut") > 2) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(v1.x)) && (getEnergyPastArmor(17.799999237060547D / (1.000100016593933D - Math.abs(v1.y)), paramShot) > 0.0F)) {
          debuggunnery("*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxspark")) && 
          (chunkDamageVisible("Keel1") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** Keel Spars Damaged..");
          nextDMGLevels(1, 2, "Keel1_D3", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxlock")) {
        if ((paramString.startsWith("xxlockal")) && 
          (getEnergyPastArmor(4.35F, paramShot) > 0.0F)) {
          debuggunnery("*** AroneL Lock Damaged..");
          nextDMGLevels(1, 2, "AroneL_D0", paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockar")) && 
          (getEnergyPastArmor(4.35F, paramShot) > 0.0F)) {
          debuggunnery("*** AroneR Lock Damaged..");
          nextDMGLevels(1, 2, "AroneR_D0", paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockfl")) && 
          (getEnergyPastArmor(4.35F, paramShot) > 0.0F)) {
          debuggunnery("*** VatorL Lock Damaged..");
          nextDMGLevels(1, 2, "VatorL_D0", paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockfr")) && 
          (getEnergyPastArmor(4.35F, paramShot) > 0.0F)) {
          debuggunnery("*** VatorR Lock Damaged..");
          nextDMGLevels(1, 2, "VatorR_D0", paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockr")) && 
          (getEnergyPastArmor(4.32F, paramShot) > 0.0F)) {
          debuggunnery("*** Rudder1 Lock Damaged..");
          nextDMGLevels(1, 2, "Rudder1_D0", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxeng")) {
        i = paramString.charAt(8) - '0';
        switch (i) {
        case 1:
          if (World.Rnd().nextFloat() < 0.01F) {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 100);
          }
          if (Pd.x >= -2.700000047683716D) break;
          this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.01F, 0.35F)); break;
        case 2:
          if ((getEnergyPastArmor(4.96F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.25F)) break;
          this.FM.AS.hitEngine(paramShot.initiator, 0, 100); break;
        case 3:
          getEnergyPastArmor(5.808F, paramShot);
        }

        return;
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '0';
        switch (i) {
        case 1:
          if (getEnergyPastArmor(World.Rnd().nextFloat(1.0F, 7.9F), paramShot) > 0.0F)
            this.FM.AS.hitTank(paramShot.initiator, 3, 1); break;
        case 2:
        case 3:
          if (getEnergyPastArmor(World.Rnd().nextFloat(1.0F, 7.9F), paramShot) <= 0.0F) break;
          this.FM.AS.hitTank(paramShot.initiator, 2, 1);
          this.bCockpitNVentilated = true;
        case 4:
          if (getEnergyPastArmor(World.Rnd().nextFloat(1.0F, 7.9F), paramShot) > 0.0F)
            this.FM.AS.hitTank(paramShot.initiator, 0, World.Rnd().nextInt(1, 4)); break;
        case 5:
          if (getEnergyPastArmor(World.Rnd().nextFloat(1.0F, 7.9F), paramShot) > 0.0F) {
            this.FM.AS.hitTank(paramShot.initiator, 1, World.Rnd().nextInt(1, 4));
          }
        }

        return;
      }
      if (paramString.startsWith("xxammo")) {
        i = paramString.charAt(6) - '0';
        if (World.Rnd().nextFloat() < 0.1F) {
          if (i == 0)
            this.FM.AS.setJamBullets(1, 1);
          else {
            this.FM.AS.setJamBullets(1, 0);
          }
        }
        return;
      }
      if ((paramString.startsWith("xxgunl")) && 
        (getEnergyPastArmor(World.Rnd().nextFloat(2.0F, 35.599998F), paramShot) > 0.0F)) {
        this.FM.AS.setJamBullets(1, 0);
      }

      if ((paramString.startsWith("xxgunr")) && 
        (getEnergyPastArmor(World.Rnd().nextFloat(2.0F, 35.599998F), paramShot) > 0.0F)) {
        this.FM.AS.setJamBullets(1, 1);
      }

      if (paramString.startsWith("xxeqpt"));
      return;
    }

    if (paramString.startsWith("xcf")) {
      if ((Pd.x > 2.01D) && (getEnergyPastArmor(11.11F / ((float)Math.sqrt(v1.y * v1.y + v1.z * v1.z) + 1.0E-004F), paramShot) <= 0.0F)) {
        doRicochet(paramShot);
        return;
      }
      if ((Pd.x > 0.8D) && (Pd.x < 2.0D)) {
        if (Pd.z > 0.425D) {
          if (World.Rnd().nextFloat() < 0.5F) {
            this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
          }
        }
        else if (Pd.y > 0.0D) {
          if (World.Rnd().nextFloat() < 0.5F)
            this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
          else {
            this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
          }
        }
        else if (World.Rnd().nextFloat() < 0.5F)
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
        else {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
        }

      }

      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
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
    else if (paramString.startsWith("xaronel")) {
      if (chunkDamageVisible("AroneL") < 2)
        hitChunk("AroneL", paramShot);
    }
    else if (paramString.startsWith("xaroner")) {
      if (chunkDamageVisible("AroneR") < 2)
        hitChunk("AroneR", paramShot);
    }
    else if (paramString.startsWith("xflapl")) {
      if (chunkDamageVisible("VatorL") < 1)
        hitChunk("VatorL", paramShot);
    }
    else if (paramString.startsWith("xflapr")) {
      if (chunkDamageVisible("VatorR") < 1)
        hitChunk("VatorR", paramShot);
    }
    else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
      i = 0;
      int j;
      if ((paramString.endsWith("a")) || (paramString.endsWith("a2"))) {
        i = 1;
        j = paramString.charAt(6) - '1';
      } else if ((paramString.endsWith("b")) || (paramString.endsWith("b2"))) {
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
      (this.bCockpitNVentilated)) {
      this.FM.AS.hitPilot(this, 0, 1);
    }

    if (Config.isUSE_RENDER()) {
      if ((this.oldVwld < 20.0F) && (this.FM.getSpeed() > 20.0F)) {
        Eff3DActor.finish(this.turboexhaust);
        this.turboexhaust = Eff3DActor.New(this, findHook("_Engine1ES_02"), null, 1.0F, "3DO/Effects/Aircraft/WhiteOxySmallTSPD.eff", -1.0F);
      }

      if ((this.oldVwld > 20.0F) && (this.FM.getSpeed() < 20.0F)) {
        Eff3DActor.finish(this.turboexhaust);
        this.turboexhaust = Eff3DActor.New(this, findHook("_Engine1ES_02"), null, 1.0F, "3DO/Effects/Aircraft/WhiteOxySmallGND.eff", -1.0F);
      }

      this.oldVwld = this.FM.getSpeed();
    }
  }

  public void doMurderPilot(int paramInt)
  {
    if (paramInt != 0) return;
    hierMesh().chunkVisible("Pilot1_D0", false);
    hierMesh().chunkVisible("Head1_D0", false);
    hierMesh().chunkVisible("Pilot1_D1", true);
    hierMesh().chunkVisible("HMask1_D0", false);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, -15.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
  }
  protected void moveGear(float paramFloat) {
    HierMesh localHierMesh = hierMesh();
    if (this.bCartAttached) {
      if (paramFloat < 1.0F) {
        hierMesh().chunkVisible("GearL1_D0", false);
        hierMesh().chunkVisible("GearR1_D0", false);
        if (hierMesh().isChunkVisible("Cart_D0")) {
          hierMesh().chunkVisible("CartDrop_D0", true);
          cut("CartDrop");
        }
        hierMesh().chunkVisible("Cart_D0", false);

        this.bCartAttached = false;

        this.FM.setCapableOfTaxiing(false);
        this.FM.CT.bHasBrakeControl = false;
      }
    } else {
      resetYPRmodifier();
      xyz[1] = (-0.3F + 0.1125F * paramFloat);
      ypr[1] = 88.0F;
      localHierMesh.chunkSetLocate("Cart_D0", xyz, ypr);
    }
    localHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    localHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    localHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    localHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    localHierMesh.chunkSetAngles("GearL6_D0", 0.0F, -45.0F * paramFloat, 0.0F);

    localHierMesh.chunkSetAngles("GearC2_D0", 0.0F, -15.0F * paramFloat, 0.0F);
    localHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
  }
  public void moveWheelSink() {
    if ((!this.bCartAttached) && (this.FM.CT.getGear() > 0.99F)) {
      float f = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.066F, -45.0F, 0.0F);
      hierMesh().chunkSetAngles("GearL2_D0", 0.0F, f, 0.0F);
      hierMesh().chunkSetAngles("GearL3_D0", 0.0F, f, 0.0F);
      hierMesh().chunkSetAngles("GearL4_D0", 0.0F, f, 0.0F);
      hierMesh().chunkSetAngles("GearL5_D0", 0.0F, f, 0.0F);
      hierMesh().chunkSetAngles("GearL6_D0", 0.0F, f, 0.0F);
    }
  }

  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }

  protected void moveElevator(float paramFloat)
  {
    reflectControls();
  }
  protected void moveAileron(float paramFloat) {
    reflectControls();
  }
  private void reflectControls() {
    HierMesh localHierMesh = hierMesh();
    float f1 = -20.0F * this.FM.CT.getAileron();
    float f2 = 20.0F * this.FM.CT.getElevator();
    localHierMesh.chunkSetAngles("AroneL_D0", 0.0F, f1 + f2, 0.0F);
    localHierMesh.chunkSetAngles("AroneR_D0", 0.0F, f1 - f2, 0.0F);
    localHierMesh.chunkSetAngles("VatorL_D0", 0.0F, 0.5F * f2, 0.0F);
    localHierMesh.chunkSetAngles("VatorR_D0", 0.0F, 0.5F * f2, 0.0F);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -50.0F * paramFloat;
    hierMesh().chunkSetAngles("Brake01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Brake02_D0", 0.0F, f, 0.0F);
  }

  protected void moveFan(float paramFloat)
  {
    this.pk = Math.abs((int)(this.FM.Vwld.length() / 14.0D));
    if (this.pk >= 1) this.pk = 1;
    if (this.bDynamoRotary != (this.pk == 1)) {
      this.bDynamoRotary = (this.pk == 1);
      hierMesh().chunkVisible("Prop1_D0", !this.bDynamoRotary);
      hierMesh().chunkVisible("PropRot1_D0", this.bDynamoRotary);
    }
    this.dynamoOrient = (this.bDynamoRotary ? (this.dynamoOrient - 17.987F) % 360.0F : (float)(this.dynamoOrient - this.FM.Vwld.length() * 1.544401526451111D) % 360.0F);
    hierMesh().chunkSetAngles("Prop1_D0", 0.0F, this.dynamoOrient, 0.0F);
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    if (this.bCartAttached) {
      moveGear(this.FM.CT.getGear());
      if (((this.FM instanceof RealFlightModel)) && (((RealFlightModel)this.FM).isRealMode()))
        this.FM.CT.bHasBrakeControl = false;
      else {
        this.FM.CT.bHasBrakeControl = true;
      }

    }

    if (this.FM.AS.isMaster()) {
      if (Config.isUSE_RENDER()) {
        if ((this.FM.EI.engines[0].getw() > 0.0F) && (this.FM.EI.engines[0].getStage() == 6))
          this.FM.AS.setSootState(this, 0, 1);
        else {
          this.FM.AS.setSootState(this, 0, 0);
        }

      }

      if (this.oldThtl < 0.35F)
        this.FM.EI.setThrottle(0.0F);
      else if (this.oldThtl < 0.65F)
        this.FM.EI.setThrottle(0.35F);
      else if (this.oldThtl < 1.0F)
        this.FM.EI.setThrottle(0.65F);
      else {
        this.FM.EI.setThrottle(1.0F);
      }
      if (this.oldThtl != this.FM.CT.PowerControl) {
        this.oldThtl = this.FM.CT.PowerControl;
        if (((this.FM instanceof RealFlightModel)) && (((RealFlightModel)this.FM).isRealMode())) {
          HUD.log(AircraftHotKeys.hudLogPowerId, "Power", new Object[] { new Integer(Math.round(this.oldThtl * 100.0F)) });
        }

      }

      if (this.oldThtl == 0.0F) {
        if (!this.FM.Gears.onGround()) {
          if (((this.FM instanceof RealFlightModel)) && (((RealFlightModel)this.FM).isRealMode()) && (this.FM.EI.engines[0].getStage() == 6)) {
            HUD.log("EngineI0");
          }
          this.FM.EI.engines[0].setEngineStops(this);
        }
      } else {
        if (((this.FM instanceof RealFlightModel)) && (((RealFlightModel)this.FM).isRealMode()) && (this.FM.EI.engines[0].getStage() == 0) && (this.FM.M.fuel > 0.0F)) {
          HUD.log("EngineI1");
        }
        this.FM.EI.engines[0].setStage(this, 6);
      }
    }
  }

  public void doSetSootState(int paramInt1, int paramInt2)
  {
    switch (paramInt2) {
    case 0:
      Eff3DActor.setIntesity(this.flame, 0.0F);
      Eff3DActor.setIntesity(this.dust, 0.0F);
      Eff3DActor.setIntesity(this.trail, 0.0F);
      Eff3DActor.setIntesity(this.sprite, 0.0F);
      break;
    case 1:
      Eff3DActor.setIntesity(this.flame, 1.0F);
      Eff3DActor.setIntesity(this.dust, 1.0F);
      Eff3DActor.setIntesity(this.trail, 1.0F);
      Eff3DActor.setIntesity(this.sprite, 1.0F);
    }
  }

  static
  {
    Class localClass = ME_163B1A.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Me-163");
    Property.set(localClass, "meshName", "3DO/Plane/Me-163B-1a/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1946.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Me-163B-1a.fmd");
    Property.set(localClass, "cockpitClass", CockpitME_163.class);
    Property.set(localClass, "LOSElevation", 0.87325F);

    weaponTriggersRegister(localClass, new int[] { 1, 1 });
    weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02" });

    weaponsRegister(localClass, "default", new String[] { "MGunMK108kpzl 60", "MGunMK108kpzl 60" });

    weaponsRegister(localClass, "none", new String[] { null, null });
  }
}