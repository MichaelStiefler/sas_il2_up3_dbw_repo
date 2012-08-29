package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class F9F extends Scheme1
  implements TypeFighter, TypeBNZFighter, TypeFighterAceMaker
{
  private float oldctl = -1.0F;
  private float curctl = -1.0F;
  private float arrestor2 = 0.0F;
  private float prevWing = 1.0F;
  public float AirBrakeControl = 0.0F;
  public int k14Mode = 0;
  public int k14WingspanType = 0;
  public float k14Distance = 200.0F;

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else {
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
    }

    if ((this.FM.AP.way.isLanding()) && (this.FM.getSpeed() > this.FM.VmaxFLAPS * 2.0D))
      this.FM.CT.AirBrakeControl = 1.0F;
    else if ((this.FM.AP.way.isLanding()) && (this.FM.getSpeed() < this.FM.VmaxFLAPS * 1.5D))
      this.FM.CT.AirBrakeControl = 0.0F;
  }

  public boolean typeFighterAceMakerToggleAutomation()
  {
    this.k14Mode += 1;
    if (this.k14Mode > 2)
      this.k14Mode = 0;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerMode" + this.k14Mode);
    return true;
  }

  public void typeFighterAceMakerAdjDistanceReset()
  {
  }

  public void typeFighterAceMakerAdjDistancePlus() {
    this.k14Distance += 10.0F;
    if (this.k14Distance > 800.0F)
      this.k14Distance = 800.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerInc");
  }

  public void typeFighterAceMakerAdjDistanceMinus() {
    this.k14Distance -= 10.0F;
    if (this.k14Distance < 200.0F)
      this.k14Distance = 200.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerDec");
  }

  public void typeFighterAceMakerAdjSideslipReset()
  {
  }

  public void typeFighterAceMakerAdjSideslipPlus() {
    this.k14WingspanType -= 1;
    if (this.k14WingspanType < 0)
      this.k14WingspanType = 0;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerWing" + this.k14WingspanType);
  }

  public void typeFighterAceMakerAdjSideslipMinus()
  {
    this.k14WingspanType += 1;
    if (this.k14WingspanType > 9)
      this.k14WingspanType = 9;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerWing" + this.k14WingspanType);
  }

  public void typeFighterAceMakerReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
    paramNetMsgGuaranted.writeByte(this.k14Mode);
    paramNetMsgGuaranted.writeByte(this.k14WingspanType);
    paramNetMsgGuaranted.writeFloat(this.k14Distance);
  }

  public void typeFighterAceMakerReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException
  {
    this.k14Mode = paramNetMsgInput.readByte();
    this.k14WingspanType = paramNetMsgInput.readByte();
    this.k14Distance = paramNetMsgInput.readFloat();
  }

  public void moveArrestorHook(float paramFloat)
  {
    hierMesh().chunkSetAngles("Hook1_D0", 0.0F, 12.2F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Hook2_D0", 0.0F, 0.0F, 0.0F);
  }

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

  protected void moveWingFold(HierMesh paramHierMesh, float paramFloat) {
    paramHierMesh.chunkSetAngles("WingLMid_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 70.0F), 0.0F);

    paramHierMesh.chunkSetAngles("WingRMid_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, -70.0F), 0.0F);
  }

  public void moveWingFold(float paramFloat)
  {
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

  public static void moveGear(HierMesh paramHierMesh, float paramFloat) {
    float f = Math.max(-paramFloat * 1500.0F, -90.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, -100.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, -40.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC7_D0", 0.0F, 0.0F, 0.0F);

    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.1F, 0.0F, 80.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.04F, 0.0F, -80.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearC7_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 70.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -70.0F * paramFloat, 0.0F);

    if (paramFloat < 0.5F)
      paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.02F, 0.5F, 0.0F, -90.0F), 0.0F);
    else {
      paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.72F, 0.98F, -90.0F, 0.0F), 0.0F);
    }

    if (paramFloat < 0.5F)
      paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.02F, 0.4F, 0.0F, 90.0F), 0.0F);
    else {
      paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.72F, 0.98F, 90.0F, 0.0F), 0.0F);
    }

    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, 70.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, -70.0F * paramFloat, 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveWheelSink()
  {
    float f = Aircraft.cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.19075F, 0.0F, 1.0F);

    resetYPRmodifier();
    Aircraft.xyz[0] = (-0.19075F * f);
    hierMesh().chunkSetLocate("GearC6_D0", Aircraft.xyz, Aircraft.ypr);
  }

  protected void moveRudder(float paramFloat) {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, 30.0F * paramFloat, 0.0F);
    if (this.FM.CT.GearControl > 0.5F)
      hierMesh().chunkSetAngles("GearC7_D0", 0.0F, -60.0F * paramFloat, 0.0F);
  }

  protected void moveFlap(float paramFloat) {
    float f = 55.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, 0.0F, f);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, 0.0F, f);
    hierMesh().chunkSetAngles("Flap03_D0", 0.0F, 0.0F, f);
    hierMesh().chunkSetAngles("Flap04_D0", 0.0F, 0.0F, f);
  }

  protected void moveFan(float paramFloat)
  {
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        debuggunnery("Armor: Hit..");
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(13.350000381469727D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot);

          if (paramShot.power <= 0.0F)
            doRicochetBack(paramShot);
        } else if (paramString.endsWith("p2")) {
          getEnergyPastArmor(8.770001F, paramShot);
        } else if (paramString.endsWith("g1")) {
          getEnergyPastArmor(World.Rnd().nextFloat(40.0F, 60.0F) / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot);

          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);

          if (paramShot.power <= 0.0F)
            doRicochetBack(paramShot);
        }
      } else if (paramString.startsWith("xxcontrols")) {
        debuggunnery("Controls: Hit..");
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
        case 2:
          if ((World.Rnd().nextFloat() >= 0.5F) || (getEnergyPastArmor(1.1F, paramShot) <= 0.0F))
            break;
          debuggunnery("Controls: Ailerones Controls: Out..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 3:
        case 4:
          if ((getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 2.93F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F))
          {
            debuggunnery("Controls: Elevator Controls: Disabled / Strings Broken..");

            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          }
          if ((getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 2.93F), paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.25F)) {
            break;
          }
          debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");

          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
        }

      }
      else if (paramString.startsWith("xxeng1")) {
        debuggunnery("Engine Module: Hit..");
        if (paramString.endsWith("bloc")) {
          getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 60.0F) / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot);
        }

        if ((paramString.endsWith("cams")) && (getEnergyPastArmor(0.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 20.0F))
        {
          this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));

          debuggunnery("Engine Module: Engine Cams Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");

          if (World.Rnd().nextFloat() < paramShot.power / 24000.0F) {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
            debuggunnery("Engine Module: Engine Cams Hit - Engine Fires..");
          }

          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.75F))
          {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
            debuggunnery("Engine Module: Engine Cams Hit (2) - Engine Fires..");
          }
        }

        if ((paramString.endsWith("eqpt")) && (World.Rnd().nextFloat() < paramShot.power / 24000.0F))
        {
          this.FM.AS.hitEngine(paramShot.initiator, 0, 3);
          debuggunnery("Engine Module: Hit - Engine Fires..");
        }

        if (paramString.endsWith("exht"));
      }
      else if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F))
        {
          if (this.FM.AS.astateTankStates[i] == 0) {
            debuggunnery("Fuel Tank (" + i + "): Pierced..");
            this.FM.AS.hitTank(paramShot.initiator, i, 1);
            this.FM.AS.doSetTankState(paramShot.initiator, i, 1);
          }
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.075F))
          {
            this.FM.AS.hitTank(paramShot.initiator, i, 2);
            debuggunnery("Fuel Tank (" + i + "): Hit..");
          }
        }
      } else if (paramString.startsWith("xxspar")) {
        debuggunnery("Spar Construction: Hit..");
        if ((paramString.startsWith("xxsparlm")) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          debuggunnery("Spar Construction: WingLMid Spars Damaged..");

          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparrm")) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          debuggunnery("Spar Construction: WingRMid Spars Damaged..");

          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparlo")) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          debuggunnery("Spar Construction: WingLOut Spars Damaged..");

          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparro")) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          debuggunnery("Spar Construction: WingROut Spars Damaged..");

          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }
      } else if (paramString.startsWith("xxhyd")) {
        this.FM.AS.setInternalDamage(paramShot.initiator, 3);
      } else if (paramString.startsWith("xxpnm")) {
        this.FM.AS.setInternalDamage(paramShot.initiator, 1);
      }
    } else {
      if (paramString.startsWith("xcockpit")) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);

        getEnergyPastArmor(0.05F, paramShot);
      }

      if ((paramString.startsWith("xxhispa1")) && (getEnergyPastArmor(4.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.75F))
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

      if (paramString.startsWith("xcf")) {
        hitChunk("CF", paramShot);
      } else if (paramString.startsWith("xnose")) {
        hitChunk("Nose", paramShot);
      } else if (paramString.startsWith("xtail")) {
        if (chunkDamageVisible("Tail1") < 3)
          hitChunk("Tail1", paramShot);
      } else if (paramString.startsWith("xkeel")) {
        if (chunkDamageVisible("Keel1") < 2)
          hitChunk("Keel1", paramShot);
      } else if (paramString.startsWith("xrudder")) {
        hitChunk("Rudder1", paramShot);
      } else if (paramString.startsWith("xstab")) {
        if ((paramString.startsWith("xstabl")) && (chunkDamageVisible("StabL") < 2))
        {
          hitChunk("StabL", paramShot);
        }if ((paramString.startsWith("xstabr")) && (chunkDamageVisible("StabR") < 1))
        {
          hitChunk("StabR", paramShot);
        }
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
        }if ((paramString.startsWith("xwinglmid")) && (chunkDamageVisible("WingLMid") < 3))
        {
          hitChunk("WingLMid", paramShot);
        }if ((paramString.startsWith("xwingrmid")) && (chunkDamageVisible("WingRMid") < 3))
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
      } else if (paramString.startsWith("xgear")) {
        if ((paramString.endsWith("1")) && (World.Rnd().nextFloat() < 0.05F)) {
          debuggunnery("Hydro System: Disabled..");
          this.FM.AS.setInternalDamage(paramShot.initiator, 0);
        }
        if ((paramString.endsWith("2")) && (World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 3.435F), paramShot) > 0.0F))
        {
          debuggunnery("Undercarriage: Stuck..");
          this.FM.AS.setInternalDamage(paramShot.initiator, 3);
        }
      } else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead")))
      {
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
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor) {
    switch (paramInt1) {
    case 19:
      this.FM.EI.engines[0].setEngineDies(paramActor);
      return super.cutFM(paramInt1, paramInt2, paramActor);
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void update(float paramFloat)
  {
    if ((this.FM.AS.isMaster()) && (Config.isUSE_RENDER())) {
      if ((this.FM.EI.engines[0].getPowerOutput() > 0.5F) && (this.FM.EI.engines[0].getStage() == 6))
      {
        if (this.FM.EI.engines[0].getPowerOutput() > 0.75F)
          this.FM.AS.setSootState(this, 0, 5);
        else
          this.FM.AS.setSootState(this, 0, 4);
      }
      else this.FM.AS.setSootState(this, 0, 0);
    }

    super.update(paramFloat);

    if (this.FM.CT.getArrestor() > 0.9F)
      if (this.FM.Gears.arrestorVAngle != 0.0F) { this.arrestor2 = Aircraft.cvt(this.FM.Gears.arrestorVAngle, -65.0F, 3.0F, 45.0F, -23.0F);

        hierMesh().chunkSetAngles("Hook2_D0", 0.0F, this.arrestor2, 0.0F);
        if (this.FM.Gears.arrestorVAngle >= -35.0F);
      } else {
        float f = -41.0F * this.FM.Gears.arrestorVSink;
        if ((f < 0.0F) && (this.FM.getSpeedKMH() > 60.0F)) {
          Eff3DActor.New(this, this.FM.Gears.arrestorHook, null, 1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
        }

        if ((f > 0.0F) && (this.FM.CT.getArrestor() < 0.9F))
          f = 0.0F;
        if (f > 6.2F)
          f = 6.2F;
        this.arrestor2 += f;
        if (this.arrestor2 < -23.0F)
          this.arrestor2 = -23.0F;
        else if (this.arrestor2 > 45.0F)
          this.arrestor2 = 45.0F;
        hierMesh().chunkSetAngles("Hook2_D0", 0.0F, this.arrestor2, 0.0F);
      }
  }

  protected void moveAirBrake(float paramFloat)
  {
    hierMesh().chunkSetAngles("Brake01_D0", 0.0F, -60.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Brake02_D0", 0.0F, -60.0F * paramFloat, 0.0F);
  }

  static
  {
    Class localClass = F9F.class;

    Property.set(localClass, "originCountry", PaintScheme.countryUSA);
  }
}