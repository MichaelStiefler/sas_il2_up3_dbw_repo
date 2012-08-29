package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;
import java.io.PrintStream;

public abstract class RE_2000xyz extends Scheme1
  implements TypeFighter, TypeStormovik
{
  private static final float[] gear8scale = { 0.0F, 3.75F, 13.75F, 27.68F, 42.75F, 57.610001F, 70.93F, 82.25F, 91.0F, 96.699997F, 98.800003F };

  private Maneuver maneuver = null;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();

    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.computePlaneLandPose(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel);
    mydebuggunnery("H = " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.H);
    mydebuggunnery("Pitch = " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.Pitch);

    if (this.jdField_thisWeaponsName_of_type_JavaLangString.endsWith("Bombs"))
    {
      hierMesh().chunkVisible("RackL_D0", true);
      hierMesh().chunkVisible("RackR_D0", true);
    }
    if (this.jdField_thisWeaponsName_of_type_JavaLangString.endsWith("Cassette"))
      hierMesh().chunkVisible("Cassette_d0", true);
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

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1)
    {
    case 19:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.hitCentreGear();
      break;
    case 3:
      if (World.Rnd().nextInt(0, 99) < 1)
      {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(this, 0, 4);
        hitProp(0, paramInt2, paramActor);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setEngineStuck(paramActor);
        return cut("engine1");
      }

      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineDies(this, 0);
      return false;
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void rareAction(float paramFloat, boolean paramBoolean) {
    super.rareAction(paramFloat, paramBoolean);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else {
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
    }
    if (paramBoolean)
    {
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[0] > 3) && (World.Rnd().nextFloat() < 0.39F)) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 0, 1);
      }
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[0] + "0", 0, this);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[1] + "0", 0, this);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[2] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[2] + "0", 0, this);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[3] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[3] + "0", 0, this);
    }
  }

  public void moveCockpitDoor(float paramFloat) {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.625F);
    hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null))
      {
        Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      }setDoorSnd(paramFloat);
    }
  }

  private static final float floatindex(float paramFloat, float[] paramArrayOfFloat) {
    int i = (int)paramFloat;
    if (i >= paramArrayOfFloat.length - 1)
      return paramArrayOfFloat[(paramArrayOfFloat.length - 1)];
    if (i < 0)
      return paramArrayOfFloat[0];
    if (i == 0) {
      if (paramFloat > 0.0F)
        return paramArrayOfFloat[0] + paramFloat * (paramArrayOfFloat[1] - paramArrayOfFloat[0]);
      return paramArrayOfFloat[0];
    }
    return paramArrayOfFloat[i] + paramFloat % i * (paramArrayOfFloat[(i + 1)] - paramArrayOfFloat[i]);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 100.0F * paramFloat, 0.0F);

    if (paramFloat < 0.25F)
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -95.0F * paramFloat / 0.25F, 0.0F);
    else {
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -95.0F, 0.0F);
    }

    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -100.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 100.0F * paramFloat, 0.0F);

    if (paramFloat < 0.25F)
      paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 95.0F * paramFloat / 0.25F, 0.0F);
    else {
      paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 95.0F, 0.0F);
    }

    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -100.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.11F, 0.67F, 0.0F, -85.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.0F, 0.09F, 0.0F, -80.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.0F, 0.09F, 0.0F, 80.0F), 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, paramFloat, 0.0F);
  }

  public void moveWheelSink()
  {
    float f = 95.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.getGear();

    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.2085F, 0.0F, -0.2F);
    Aircraft.ypr[1] = (-f);
    hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.ypr[1] = f;
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.2085F, 0.0F, -0.2F);
    hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -35.0F * paramFloat;
    hierMesh().chunkSetAngles("FlapL1_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("FlapR1_D0", 0.0F, f, 0.0F);
    f = -50.0F * paramFloat;
    hierMesh().chunkSetAngles("FlapL2_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("FlapR2_D0", 0.0F, f, 0.0F);
  }

  public void update(float paramFloat)
  {
    for (int i = 1; i < 7; i++)
    {
      hierMesh().chunkSetAngles("RadiatorL" + i + "_D0", 0.0F, 30.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator(), 0.0F);

      hierMesh().chunkSetAngles("RadiatorR" + i + "_D0", 0.0F, 30.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator(), 0.0F);
    }

    super.update(paramFloat);
  }

  protected void setControlDamage(Shot paramShot, int paramInt)
  {
    if (World.Rnd().nextFloat() < 0.002F)
    {
      if (getEnergyPastArmor(4.0F, paramShot) > 0.0F)
      {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, paramInt);
        mydebuggunnery(paramInt + " Controls Out... //0 = AILERON, 1 = ELEVATOR, 2 = RUDDER");
      }
    }
  }

  protected void moveAileron(float paramFloat)
  {
    float f = -(paramFloat * 23.0F);
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, f, 0.0F);

    f = -(paramFloat * 23.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, f, 0.0F);
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -27.0F * paramFloat, 0.0F);
  }

  protected void moveElevator(float paramFloat)
  {
    if (paramFloat < 0.0F)
    {
      hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -20.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -20.0F * paramFloat, 0.0F);
    }
    else
    {
      hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -30.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i = 0;

    String str = paramString.toLowerCase();

    System.out.println("HitBone called! " + str);
    System.out.println("IN: " + paramShot.power);
    int j;
    if (str.startsWith("xx")) {
      if (str.startsWith("xxarmor")) {
        mydebuggunnery("Armor: Hit..");
        if (str.startsWith("xxarmorp")) {
          j = str.charAt(8) - '0';
          switch (j) {
          case 1:
            getEnergyPastArmor(22.760000228881836D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);

            if (paramShot.power > 0.0F) break;
            doRicochetBack(paramShot); break;
          case 2:
            getEnergyPastArmor(9.366F, paramShot);
            break;
          case 3:
            getEnergyPastArmor(12.699999809265137D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot);
          }

        }

      }
      else if (str.startsWith("xxcontrols")) {
        mydebuggunnery("Controls: Hit..");
        j = str.charAt(10) - '0';
        switch (j) {
        case 1:
        case 2:
          if (getEnergyPastArmor(0.25F / ((float)Math.sqrt(Aircraft.v1.jdField_y_of_type_Double * Aircraft.v1.jdField_y_of_type_Double + Aircraft.v1.jdField_z_of_type_Double * Aircraft.v1.jdField_z_of_type_Double) + 1.0E-004F), paramShot) <= 0.0F)
          {
            break;
          }

          if (World.Rnd().nextFloat() < 0.05F) {
            mydebuggunnery("Controls: Elevator Wiring Hit, Elevator Controls Disabled..");

            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
          }
          if (World.Rnd().nextFloat() >= 0.75F) break;
          mydebuggunnery("Controls: Rudder Wiring Hit, Rudder Controls Disabled..");

          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2); break;
        case 3:
          if (getEnergyPastArmor(3.2F, paramShot) <= 0.0F) break;
          mydebuggunnery("*** Control Column: Hit, Controls Destroyed..");

          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0); break;
        case 4:
          if (getEnergyPastArmor(0.1F, paramShot) <= 0.0F) break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitState(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState | 0x8);

          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 1);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, 0, 6);
          mydebuggunnery("Quadrant: Hit, Engine Controls Disabled.."); break;
        case 5:
        case 7:
          if (getEnergyPastArmor(0.1F, paramShot) <= 0.0F) break;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0);
          mydebuggunnery("*** Aileron Controls: Control Crank Destroyed.."); break;
        case 6:
        case 8:
          if ((getEnergyPastArmor(4.0D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.5F))
          {
            break;
          }
          mydebuggunnery("Controls: Aileron Wiring Hit, Aileron Controls Disabled..");

          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0);
        }

      }
      else if (str.startsWith("xxspar")) {
        mydebuggunnery("Spar Construction: Hit..");
        if ((str.startsWith("xxspartli")) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F))
        {
          mydebuggunnery("Spar Construction: WingLIn Spar Hit, Breaking in Half..");

          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }
        if ((str.startsWith("xxspartri")) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F))
        {
          mydebuggunnery("Spar Construction: WingRIn Spar Hit, Breaking in Half..");

          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }
        if ((str.startsWith("xxspartlo")) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F))
        {
          mydebuggunnery("Spar Construction: WingLMid Spar Hit, Breaking in Half..");

          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }
        if ((str.startsWith("xxspartro")) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.jdField_x_of_type_Double) + 9.999999747378752E-005D), paramShot) > 0.0F))
        {
          mydebuggunnery("Spar Construction: WingRMid Spar Hit, Breaking in Half..");

          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }
        if ((str.startsWith("xxspart")) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.86F / (float)Math.sqrt(Aircraft.v1.jdField_y_of_type_Double * Aircraft.v1.jdField_y_of_type_Double + Aircraft.v1.jdField_z_of_type_Double * Aircraft.v1.jdField_z_of_type_Double), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F))
        {
          mydebuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");

          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }
      } else {
        if (str.startsWith("xxlock")) {
          mydebuggunnery("Lock Construction: Hit..");
          if ((str.startsWith("xxlockr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            mydebuggunnery("Lock Construction: Rudder1 Lock Shot Off..");

            nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
          }

          if ((str.startsWith("xxlockvl")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            mydebuggunnery("Lock Construction: VatorL Lock Shot Off..");

            nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
          }

          if ((str.startsWith("xxlockvr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            mydebuggunnery("Lock Construction: VatorR Lock Shot Off..");

            nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
          }

          if ((str.startsWith("xxlockall")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            mydebuggunnery("Lock Construction: AroneL Lock Shot Off..");

            nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), paramShot.initiator);
          }

          if ((str.startsWith("xxlockalr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            mydebuggunnery("Lock Construction: AroneR Lock Shot Off..");

            nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), paramShot.initiator);
          }

        }

        if (str.startsWith("xxeng")) {
          if (((str.endsWith("prop")) || (str.endsWith("pipe"))) && (getEnergyPastArmor(0.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F))
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setKillPropAngleDevice(paramShot.initiator);
          }if (str.endsWith("case")) {
            if (getEnergyPastArmor(0.2F, paramShot) > 0.0F) {
              if (World.Rnd().nextFloat() < paramShot.power / 140000.0F)
              {
                this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramShot.initiator, 0);
                mydebuggunnery("*** Engine Crank Case Hit - Engine Stucks..");
              }

              if (World.Rnd().nextFloat() < paramShot.power / 85000.0F)
              {
                this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 2);
                mydebuggunnery("*** Engine Crank Case Hit - Engine Damaged..");
              }
            }
            else if (World.Rnd().nextFloat() < 0.01F) {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setCyliderKnockOut(paramShot.initiator, 1);
            }
            else {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setReadyness(paramShot.initiator, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness() - 0.002F);

              mydebuggunnery("*** Engine Crank Case Hit - Readyness Reduced to " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness() + "..");
            }

            getEnergyPastArmor(12.0F, paramShot);
          }
          if (str.endsWith("cyls")) {
            if ((getEnergyPastArmor(6.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersRatio() * 0.75F))
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 19000.0F)));

              mydebuggunnery("*** Engine Cylinders Hit, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylinders() + " Left..");

              if (World.Rnd().nextFloat() < paramShot.power / 48000.0F)
              {
                this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 2);
                mydebuggunnery("*** Engine Cylinders Hit - Engine Fires..");
              }
            }

            getEnergyPastArmor(25.0F, paramShot);
          }
          if (str.endsWith("supc")) {
            if (getEnergyPastArmor(0.05F, paramShot) > 0.0F)
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setKillCompressor(paramShot.initiator);
            getEnergyPastArmor(2.0F, paramShot);
          }
          if (str.startsWith("xxeng1oil")) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
            mydebuggunnery("*** Engine Module: Oil Radiator Hit..");
          }

          mydebuggunnery("*** Engine state = " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[0]);
        }
        else if (str.startsWith("xxtank"))
        {
          j = str.charAt(6) - '1';
          if ((getEnergyPastArmor(0.12F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F))
          {
            if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[j] == 0) {
              mydebuggunnery("Fuel System: Fuel Tank Pierced..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, j, 1);
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetTankState(paramShot.initiator, j, 1);
            } else if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[j] == 1) {
              mydebuggunnery("Fuel System: Fuel Tank Pierced (2)..");

              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, j, 1);
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetTankState(paramShot.initiator, j, 2);
            }
            if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.5F))
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, j, 2);
              mydebuggunnery("Fuel System: Fuel Tank Pierced, State Shifted..");
            }
          }

          mydebuggunnery("Tank State: " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[j]);
        }
        else if (str.startsWith("xxmgun")) {
          if (str.endsWith("01")) {
            mydebuggunnery("Armament System: Left Machine Gun: Disabled..");

            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 0);
          }
          if (str.endsWith("02")) {
            mydebuggunnery("Armament System: Right Machine Gun: Disabled..");

            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 0);
          }
          getEnergyPastArmor(World.Rnd().nextFloat(0.3F, 12.6F), paramShot);
        }
      }
    }
    else if (str.startsWith("xcf")) {
      setControlDamage(paramShot, 0);
      setControlDamage(paramShot, 1);
      setControlDamage(paramShot, 2);
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
    }
    else if (str.startsWith("xeng")) {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    } else if (str.startsWith("xtail")) {
      setControlDamage(paramShot, 1);
      setControlDamage(paramShot, 2);
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    } else if (str.startsWith("xkeel")) {
      hitChunk("Keel1", paramShot);
    } else if (str.startsWith("xrudder")) {
      setControlDamage(paramShot, 2);
      if (chunkDamageVisible("Rudder1") < 1)
        hitChunk("Rudder1", paramShot);
    } else if (str.startsWith("xstab")) {
      if (str.startsWith("xstabl"))
        hitChunk("StabL", paramShot);
      if (str.startsWith("xstabr"))
        hitChunk("StabR", paramShot);
    } else if (str.startsWith("xvator")) {
      if ((str.startsWith("xvatorl")) && (chunkDamageVisible("VatorL") < 1))
      {
        hitChunk("VatorL", paramShot);
      }if ((str.startsWith("xvatorr")) && (chunkDamageVisible("VatorR") < 1))
      {
        hitChunk("VatorR", paramShot);
      }
    } else if (str.startsWith("xwing")) {
      if ((str.startsWith("xwinglin")) && (chunkDamageVisible("WingLIn") < 3))
      {
        if (str.startsWith("xwinglin1"))
        {
          j = 0;
          if ((getEnergyPastArmor(0.12F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.2F))
          {
            if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[j] == 0) {
              mydebuggunnery("Fuel System: Fuel Tank Pierced..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, j, 1);
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetTankState(paramShot.initiator, j, 1);
            } else if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[j] == 1) {
              mydebuggunnery("Fuel System: Fuel Tank Pierced (2)..");

              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, j, 1);
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetTankState(paramShot.initiator, j, 2);
            }
            if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.5F))
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, j, 2);
              mydebuggunnery("Fuel System: Fuel Tank Pierced, State Shifted..");
            }

            mydebuggunnery("*** Tank " + (j + 1) + " state = " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[j]);
          }
        }
        setControlDamage(paramShot, 0);
        hitChunk("WingLIn", paramShot);
      }
      if ((str.startsWith("xwingrin")) && (chunkDamageVisible("WingRIn") < 3))
      {
        if (str.startsWith("xwingrin1"))
        {
          j = 2;
          if ((getEnergyPastArmor(0.12F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.2F))
          {
            if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[j] == 0) {
              mydebuggunnery("Fuel System: Fuel Tank Pierced..");
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, j, 1);
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetTankState(paramShot.initiator, j, 1);
            } else if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[j] == 1) {
              mydebuggunnery("Fuel System: Fuel Tank Pierced (2)..");

              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, j, 1);
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetTankState(paramShot.initiator, j, 2);
            }
            if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.5F))
            {
              this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, j, 2);
              mydebuggunnery("Fuel System: Fuel Tank Pierced, State Shifted..");
            }

            mydebuggunnery("*** Tank " + (j + 1) + " state = " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[j]);
          }
        }
        setControlDamage(paramShot, 0);
        hitChunk("WingRIn", paramShot);
      }
      if ((str.startsWith("xwinglmid")) && (chunkDamageVisible("WingLMid") < 3))
      {
        setControlDamage(paramShot, 0);
        hitChunk("WingLMid", paramShot);
      }
      if ((str.startsWith("xwingrmid")) && (chunkDamageVisible("WingRMid") < 3))
      {
        setControlDamage(paramShot, 0);
        hitChunk("WingRMid", paramShot);
      }
      if ((str.startsWith("xwinglout")) && (chunkDamageVisible("WingLOut") < 3))
      {
        hitChunk("WingLOut", paramShot);
      }if ((str.startsWith("xwingrout")) && (chunkDamageVisible("WingROut") < 3))
      {
        hitChunk("WingROut", paramShot);
      }
    } else if (str.startsWith("xarone")) {
      if ((str.startsWith("xaronel")) && (chunkDamageVisible("AroneL") < 1))
      {
        hitChunk("AroneL", paramShot);
      }if ((str.startsWith("xaroner")) && (chunkDamageVisible("AroneR") < 1))
      {
        hitChunk("AroneR", paramShot);
      }
    } else if (str.startsWith("xgear")) {
      if ((World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 3.435F), paramShot) > 0.0F))
      {
        mydebuggunnery("Undercarriage: Stuck..");
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.setHydroOperable(false);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setInternalDamage(paramShot.initiator, 3);
      }
    } else if (str.startsWith("xoil")) {
      if (World.Rnd().nextFloat() < 0.12F) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
        mydebuggunnery("*** Engine Module: Oil Radiator Hit..");
      }
    }
    else if ((!str.startsWith("xblister")) && ((str.startsWith("xpilot")) || (str.startsWith("xhead"))))
    {
      j = 0;
      int k;
      if (str.endsWith("a")) {
        j = 1;
        k = str.charAt(6) - '1';
      } else if (str.endsWith("b")) {
        j = 2;
        k = str.charAt(6) - '1';
      } else {
        k = str.charAt(5) - '1';
      }hitFlesh(k, paramShot, j);
    }
    System.out.println("OUT: " + paramShot.power);
  }

  protected void mydebuggunnery(String paramString)
  {
    System.out.println(paramString);
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "originCountry", PaintScheme.countryItaly);
  }
}