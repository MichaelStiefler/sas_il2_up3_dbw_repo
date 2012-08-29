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
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public abstract class SeaFury extends Scheme1
  implements TypeFighter, TypeDiveBomber
{
  private float arrestorPos = 0.0F;
  private float arrestorVel = 0.0F;
  private static boolean bGearExtending = false;
  private static boolean bGearExtending2 = false;
  private float prevWing = 1.0F;
  private float cGearPos = 0.0F;
  private float cGear = 0.0F;
  private boolean bNeedSetup = true;
  private float flapps = 0.0F;

  protected float arrestor = 0.0F;

  public boolean typeDiveBomberToggleAutomation()
  {
    return false;
  }

  public void typeDiveBomberAdjAltitudeReset()
  {
  }

  public void typeDiveBomberAdjAltitudePlus()
  {
  }

  public void typeDiveBomberAdjAltitudeMinus()
  {
  }

  public void typeDiveBomberAdjVelocityReset()
  {
  }

  public void typeDiveBomberAdjVelocityPlus()
  {
  }

  public void typeDiveBomberAdjVelocityMinus()
  {
  }

  public void typeDiveBomberAdjDiveAngleReset()
  {
  }

  public void typeDiveBomberAdjDiveAnglePlus()
  {
  }

  public void typeDiveBomberAdjDiveAngleMinus()
  {
  }

  public void typeDiveBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
  }

  public void typeDiveBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException
  {
  }

  protected void moveAileron(float paramFloat)
  {
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -30.0F * paramFloat, 0.0F);
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat) {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 0.5F, 0.0F, -100.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 0.2F, 0.0F, -60.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 0.2F, 0.0F, -60.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.6F, 0.0F, -90.809998F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.08F, 0.0F, -82.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.6F, 0.0F, -115.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL9_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.6F, 0.0F, -130.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.2F, 1.0F, 0.0F, -90.809998F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.2F, 0.28F, 0.0F, -82.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, Aircraft.cvt(paramFloat, 0.2F, 1.0F, 0.0F, -115.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR9_D0", 0.0F, Aircraft.cvt(paramFloat, 0.2F, 1.0F, 0.0F, -130.0F), 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }

  public void moveArrestorHook(float paramFloat)
  {
    hierMesh().chunkSetAngles("Hook1_D0", 0.0F, -37.0F * paramFloat, 0.0F);
    this.arrestor = paramFloat;
  }

  public void moveWheelSink()
  {
  }

  protected void moveWingFold(HierMesh paramHierMesh, float paramFloat)
  {
    paramFloat *= 18.0F;
    if (bGearExtending) {
      if (paramFloat < 1.5F) {
        paramHierMesh.chunkSetAngles("REF_WingLMid_D0", 0.0F, Aircraft.cvt(paramFloat, 0.0F, 1.5F, 0.0F, 2.6F), 0.0F);

        paramHierMesh.chunkSetAngles("REF_WingRMid_D0", 0.0F, Aircraft.cvt(paramFloat, 0.0F, 1.5F, 0.0F, 2.6F), 0.0F);
      }
      else if (paramFloat < 2.5F) {
        paramHierMesh.chunkSetAngles("REF_WingLMid_D0", 0.0F, Aircraft.cvt(paramFloat, 1.5F, 2.5F, 2.6F, 5.1F), 0.0F);

        paramHierMesh.chunkSetAngles("REF_WingRMid_D0", 0.0F, Aircraft.cvt(paramFloat, 1.5F, 2.5F, 2.6F, 5.1F), 0.0F);
      }
      else
      {
        paramHierMesh.chunkSetAngles("REF_WingLMid_D0", 0.0F, Aircraft.cvt(paramFloat, 2.5F, 17.9F, 5.1F, 105.0F), 0.0F);

        paramHierMesh.chunkSetAngles("REF_WingRMid_D0", 0.0F, Aircraft.cvt(paramFloat, 2.5F, 16.0F, 5.1F, 105.0F), 0.0F);
      }

    }
    else if (paramFloat < 9.0F) {
      if (paramFloat < 6.8F) {
        paramHierMesh.chunkSetAngles("REF_WingRMid_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 6.8F, 0.0F, 45.0F), 0.0F);
      }
      else
      {
        paramHierMesh.chunkSetAngles("REF_WingRMid_D0", 0.0F, Aircraft.cvt(paramFloat, 6.8F, 9.0F, 45.0F, 50.0F), 0.0F);
      }

      if (paramFloat < 7.5F) {
        paramHierMesh.chunkSetAngles("REF_WingLMid_D0", 0.0F, Aircraft.cvt(paramFloat, 0.75F, 7.5F, 0.0F, 45.0F), 0.0F);
      }
      else
      {
        paramHierMesh.chunkSetAngles("REF_WingLMid_D0", 0.0F, Aircraft.cvt(paramFloat, 7.5F, 9.0F, 45.0F, 50.0F), 0.0F);
      }

    }
    else if (paramFloat < 11.0F) {
      paramHierMesh.chunkSetAngles("REF_WingLMid_D0", 0.0F, Aircraft.cvt(paramFloat, 9.0F, 11.0F, 50.0F, 60.0F), 0.0F);

      paramHierMesh.chunkSetAngles("REF_WingRMid_D0", 0.0F, Aircraft.cvt(paramFloat, 9.0F, 11.0F, 50.0F, 60.0F), 0.0F);
    }
    else
    {
      paramHierMesh.chunkSetAngles("REF_WingLMid_D0", 0.0F, Aircraft.cvt(paramFloat, 11.0F, 15.75F, 60.0F, 105.0F), 0.0F);

      paramHierMesh.chunkSetAngles("REF_WingRMid_D0", 0.0F, Aircraft.cvt(paramFloat, 11.0F, 15.75F, 60.0F, 105.0F), 0.0F);
    }
  }

  public void moveWingFold(float paramFloat)
  {
    if (this.prevWing > paramFloat)
      bGearExtending = false;
    else
      bGearExtending = true;
    this.prevWing = paramFloat;
    if (paramFloat < 0.001F) {
      setGunPodsOn(true);
      hideWingWeapons(false);
    } else {
      setGunPodsOn(false);
      this.FM.CT.WeaponControl[0] = false;
      hideWingWeapons(true);
    }
    moveWingFold(hierMesh(), paramFloat);
  }

  public void moveCockpitDoor(float paramFloat) {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.625F);
    Aircraft.xyz[2] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.06845F);
    Aircraft.ypr[2] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 1.0F);
    hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
    resetYPRmodifier();
    Aircraft.xyz[2] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.13F);
    Aircraft.ypr[2] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, -8.0F);
    hierMesh().chunkSetLocate("Pilot1_D0", Aircraft.xyz, Aircraft.ypr);
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null))
      {
        Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      }setDoorSnd(paramFloat);
    }
  }

  protected void moveFlap(float paramFloat) {
    float f = -80.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap04_D0", 0.0F, f, 0.0F);
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("HMask1_D0", false);
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("p1"))
          getEnergyPastArmor(6.78F, paramShot);
        else if (paramString.endsWith("g1")) {
          getEnergyPastArmor(9.96F / (1.0E-005F + (float)Math.abs(Aircraft.v1.x)), paramShot);
        }
        else if (paramString.endsWith("g2")) {
          getEnergyPastArmor(World.Rnd().nextFloat(30.0F, 50.0F) / (1.0E-005F + (float)Math.abs(Aircraft.v1.x)), paramShot);
        }

      }
      else if (paramString.startsWith("xxcontrols")) {
        if (paramString.endsWith("1")) {
          if (World.Rnd().nextFloat() < 0.12F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
            Aircraft.debugprintln(this, "*** Evelator Controls Out..");
          }

          if (World.Rnd().nextFloat() < 0.12F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);
            Aircraft.debugprintln(this, "*** Rudder Controls Out..");
          }

          if (World.Rnd().nextFloat() < 0.12F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 0);
            Aircraft.debugprintln(this, "*** Arone Controls Out..");
          }
        }
        else if (paramString.endsWith("2")) {
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F))
          {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
            Aircraft.debugprintln(this, "*** Evelator Controls Out..");
          }
        }
        else if ((paramString.endsWith("3")) && (World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F))
        {
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          Aircraft.debugprintln(this, "*** Rudder Controls Out..");
        }
      } else if (paramString.startsWith("xxspar")) {
        if (((paramString.endsWith("t1")) || (paramString.endsWith("t2")) || (paramString.endsWith("t3")) || (paramString.endsWith("t4"))) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.5F / (float)Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** Tail1 Spars Broken in Half..");

          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("li1")) || (paramString.endsWith("li2")) || (paramString.endsWith("li3")) || (paramString.endsWith("li4"))) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("ri1")) || (paramString.endsWith("ri2")) || (paramString.endsWith("ri3")) || (paramString.endsWith("ri4"))) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("lm1")) || (paramString.endsWith("lm2")) || (paramString.endsWith("lm3")) || (paramString.endsWith("lm4"))) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");

          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("rm1")) || (paramString.endsWith("rm2")) || (paramString.endsWith("rm3")) || (paramString.endsWith("rm4"))) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");

          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("lo1")) || (paramString.endsWith("lo2"))) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");

          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("ro1")) || (paramString.endsWith("ro2"))) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");

          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("sl1")) || (paramString.endsWith("sl2")) || (paramString.endsWith("sl3")) || (paramString.endsWith("sl4")) || (paramString.endsWith("sl5"))) && (chunkDamageVisible("StabL") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** StabL Spars Damaged..");
          nextDMGLevels(1, 2, "StabL_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("sr1")) || (paramString.endsWith("sr2")) || (paramString.endsWith("sr3")) || (paramString.endsWith("sr4")) || (paramString.endsWith("sr5"))) && (chunkDamageVisible("StabR") > 2) && (getEnergyPastArmor(3.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** StabR Spars Damaged..");
          nextDMGLevels(1, 2, "StabR_D3", paramShot.initiator);
        }
        if (paramString.endsWith("e1"))
          getEnergyPastArmor(6.0F, paramShot);
      } else if (paramString.startsWith("xxeng1")) {
        if ((paramString.endsWith("prp")) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F))
        {
          this.FM.EI.engines[0].setKillPropAngleDevice(paramShot.initiator);
        }if ((paramString.endsWith("cas")) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F))
        {
          if (World.Rnd().nextFloat() < paramShot.power / 200000.0F) {
            this.FM.AS.setEngineStuck(paramShot.initiator, 0);
            Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Stucks..");
          }

          if (World.Rnd().nextFloat() < paramShot.power / 50000.0F) {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
            Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Damaged..");
          }

          if (World.Rnd().nextFloat() < paramShot.power / 28000.0F) {
            this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, 1);
            Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Cylinder Feed Out, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");
          }

          this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));

          Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
        }

        if ((paramString.endsWith("cyl")) && (getEnergyPastArmor(0.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 1.75F))
        {
          this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));

          Aircraft.debugprintln(this, "*** Engine Cylinders Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");

          if (this.FM.AS.astateEngineStates[0] < 1)
            this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
          if (World.Rnd().nextFloat() < paramShot.power / 24000.0F) {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 3);
            Aircraft.debugprintln(this, "*** Engine Cylinders Hit - Engine Fires..");
          }

          getEnergyPastArmor(25.0F, paramShot);
        }
        if ((paramString.endsWith("sup")) && (getEnergyPastArmor(0.05F, paramShot) > 0.0F))
        {
          this.FM.EI.engines[0].setKillCompressor(paramShot.initiator);
        }
      } else if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F))
        {
          this.FM.AS.hitTank(paramShot.initiator, i, 1);
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.11F))
            this.FM.AS.hitTank(paramShot.initiator, i, 2);
        }
      } else {
        if ((paramString.startsWith("xxmgunl1")) && (getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.FM.AS.setJamBullets(0, 0);
        }if ((paramString.startsWith("xxmgunr1")) && (getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.FM.AS.setJamBullets(0, 1);
        }if ((paramString.startsWith("xxmgunl2")) && (getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.FM.AS.setJamBullets(0, 2);
        }if ((paramString.startsWith("xxmgunr2")) && (getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.FM.AS.setJamBullets(0, 3);
        }if ((paramString.startsWith("xxhispa1")) && (getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.FM.AS.setJamBullets(1, 0);
        }if ((paramString.startsWith("xxhispa2")) && (getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.FM.AS.setJamBullets(1, 1);
        }if ((paramString.startsWith("xxhispa3")) && (getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.FM.AS.setJamBullets(1, 2);
        }if ((paramString.startsWith("xxhispa4")) && (getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
        {
          this.FM.AS.setJamBullets(1, 3);
        }
      }
    } else if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit"))) {
      hitChunk("CF", paramShot);
      if (paramPoint3d.x > -2.2D) {
        if (World.Rnd().nextFloat() < 0.1F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
        }
        if ((paramPoint3d.x < -1.0D) && (paramPoint3d.z > 0.55D)) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
        }
        if (paramPoint3d.z > 0.65D) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
        }
        if (Math.abs(Aircraft.v1.x) < 0.800000011920929D) {
          if (paramPoint3d.y > 0.0D) {
            if (World.Rnd().nextFloat() < 0.1F) {
              this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
            }

            if (World.Rnd().nextFloat() < 0.1F)
              this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
          }
          else
          {
            if (World.Rnd().nextFloat() < 0.1F) {
              this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
            }

            if (World.Rnd().nextFloat() < 0.1F) {
              this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
            }
          }
        }
      }
    }
    else if (paramString.startsWith("xeng")) {
      if (chunkDamageVisible("Engine1") < 3)
        hitChunk("Engine1", paramShot);
    } else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    } else if (paramString.startsWith("xkeel")) {
      hitChunk("Keel1", paramShot);
    } else if (paramString.startsWith("xrudder")) {
      hitChunk("Rudder1", paramShot);
    } else if (paramString.startsWith("xstab")) {
      if ((paramString.startsWith("xstabl")) && (chunkDamageVisible("StabL") < 3))
        hitChunk("StabL", paramShot);
      if ((paramString.startsWith("xstabr")) && (chunkDamageVisible("StabR") < 3))
        hitChunk("StabR", paramShot);
    } else if (paramString.startsWith("xvator")) {
      if (paramString.startsWith("xvatorl"))
        hitChunk("VatorL", paramShot);
      if (paramString.startsWith("xvatorr"))
        hitChunk("VatorR", paramShot);
    } else if (paramString.startsWith("xwing")) {
      if ((paramString.startsWith("xwinglin")) && (chunkDamageVisible("WingLIn") < 3))
      {
        hitChunk("WingLIn", paramShot);
      }if ((paramString.startsWith("xwingrin")) && (chunkDamageVisible("WingRIn") < 3))
      {
        hitChunk("WingRIn", paramShot);
      }if (paramString.startsWith("xwinglmid")) {
        if (chunkDamageVisible("WingLMid") < 3)
          hitChunk("WingLMid", paramShot);
        if (World.Rnd().nextFloat() < paramShot.mass + 0.02F)
          this.FM.AS.hitOil(paramShot.initiator, 0);
      }
      if ((paramString.startsWith("xwingrmid")) && (chunkDamageVisible("WingRMid") < 3))
      {
        hitChunk("WingRMid", paramShot);
      }if ((paramString.startsWith("xwinglout")) && (chunkDamageVisible("WingLOut") < 3))
      {
        hitChunk("WingLOut", paramShot);
      }if ((paramString.startsWith("xwingrout")) && (chunkDamageVisible("WingROut") < 3))
      {
        hitChunk("WingROut", paramShot);
      }
    } else if (paramString.startsWith("xarone")) {
      if (paramString.startsWith("xaronel"))
        hitChunk("AroneL", paramShot);
      if (paramString.startsWith("xaroner"))
        hitChunk("AroneR", paramShot);
    } else if (paramString.startsWith("xoil")) {
      if (getEnergyPastArmor(0.5F, paramShot) > 0.0F) {
        debuggunnery("Engine Module: Oil Radiator Hit, Oil Radiator Pierced..");

        this.FM.AS.hitOil(paramShot.initiator, 0);
      }
    } else if (paramString.startsWith("xwater")) {
      if (this.FM.AS.astateEngineStates[0] == 0) {
        debuggunnery("Engine Module: Water Radiator Pierced..");
        this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
        this.FM.AS.doSetEngineState(paramShot.initiator, 0, 1);
      } else if (this.FM.AS.astateEngineStates[0] == 1) {
        debuggunnery("Engine Module: Water Radiator Pierced..");
        this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
        this.FM.AS.doSetEngineState(paramShot.initiator, 0, 2);
      }
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
      }hitFlesh(j, paramShot, i);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean) {
    super.rareAction(paramFloat, paramBoolean);
    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
  }

  private static final float filter(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    float f1 = (float)Math.exp(-paramFloat1 / paramFloat4);
    float f2 = paramFloat2 + (paramFloat3 - paramFloat2) * f1;
    if (f2 < paramFloat2) {
      f2 += paramFloat5 * paramFloat1;
      if (f2 > paramFloat2)
        f2 = paramFloat2;
    } else if (f2 > paramFloat2) {
      f2 -= paramFloat5 * paramFloat1;
      if (f2 < paramFloat2)
        f2 = paramFloat2;
    }
    return f2;
  }

  static
  {
    Class localClass = SeaFury.class;

    Property.set(localClass, "originCountry", PaintScheme.countryUSA);
  }
}