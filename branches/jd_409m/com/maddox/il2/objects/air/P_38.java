package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.fm.Squares;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.Property;

public abstract class P_38 extends Scheme2a
  implements TypeFighter, TypeBNZFighter, TypeStormovik
{
  public boolean bChangedPit = true;
  private float steera = 0.0F;

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) this.bChangedPit = true; 
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor) {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) this.bChangedPit = true;
  }

  protected void moveFlap(float paramFloat)
  {
    resetYPRmodifier();
    Aircraft.xyz[1] = (0.3F * paramFloat);
    Aircraft.xyz[2] = (-0.04835F * paramFloat);
    hierMesh().chunkSetLocate("Flap01_D0", Aircraft.xyz, Aircraft.ypr);
    float f = -25.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap04_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap05_D0", 0.0F, f, 0.0F);
  }

  protected void moveElevator(float paramFloat)
  {
    hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -30.0F * paramFloat, 0.0F);
  }

  protected void moveAirBrake(float paramFloat)
  {
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setGCenter(0.0F - 0.2F * paramFloat);
    hierMesh().chunkSetAngles("Brake1_D0", 0.0F, -40.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Brake2_D0", 0.0F, -40.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Brake3_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Brake4_D0", 0.0F, -90.0F * paramFloat, 0.0F);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, -102.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC6_D0", 0.0F, -105.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC7_D0", 0.0F, -140.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC8_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.11F, 0.0F, -95.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -80.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, -40.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL8_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.11F, 0.0F, -80.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL9_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.11F, 0.0F, -80.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -80.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, -40.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR8_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.11F, 0.0F, -80.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR9_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.11F, 0.0F, -80.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL10_D0", 0.0F, -90.0F * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) {
    this.steera = 0.0F;
    moveWheelSink();
    moveGear(hierMesh(), paramFloat);
  }
  public void moveSteering(float paramFloat) {
    this.steera = (0.9F * this.steera + 0.1F * Aircraft.cvt(paramFloat, -50.0F, 50.0F, 50.0F, -50.0F));
    moveWheelSink();
  }
  public void moveWheelSink() {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[2], 0.0F, 0.24F, 0.0F, 0.2406F);
    Aircraft.ypr[1] = this.steera;
    hierMesh().chunkSetLocate("GearC3_D0", Aircraft.xyz, Aircraft.ypr);
    hierMesh().chunkSetAngles("GearC4_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[2], 0.0F, 0.24F, 0.0F, -50.0F), 0.0F);
    hierMesh().chunkSetAngles("GearC5_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[2], 0.0F, 0.24F, 0.0F, -105.0F), 0.0F);

    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.32F, 0.0F, -0.3206F);
    hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
    hierMesh().chunkSetAngles("GearL4_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.32F, 0.0F, -60.0F), 0.0F);
    hierMesh().chunkSetAngles("GearL5_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.32F, 0.0F, -117.5F), 0.0F);

    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.32F, 0.0F, 0.3206F);
    hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
    hierMesh().chunkSetAngles("GearR4_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.32F, 0.0F, -60.0F), 0.0F);
    hierMesh().chunkSetAngles("GearR5_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.32F, 0.0F, -117.5F), 0.0F);
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsAboutToBailout) break;
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
    }
  }

  public void update(float paramFloat)
  {
    hierMesh().chunkSetAngles("Water1_D0", 0.0F, 18.0F - 36.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator(), 0.0F);
    hierMesh().chunkSetAngles("Water2_D0", 0.0F, 18.0F - 36.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator(), 0.0F);
    hierMesh().chunkSetAngles("Water3_D0", 0.0F, 18.0F - 36.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlRadiator(), 0.0F);
    hierMesh().chunkSetAngles("Water4_D0", 0.0F, 18.0F - 36.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlRadiator(), 0.0F);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap() < 0.25F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getAirBrake()) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setFlapsShift(0.25F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getAirBrake());
    super.update(paramFloat);
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Sq.squareElevators > 0.0F)) {
      RealFlightModel localRealFlightModel = (RealFlightModel)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      if ((localRealFlightModel.RealMode) && (localRealFlightModel.indSpeed > 135.0F)) {
        float f = 1.0F + 0.005F * (135.0F - localRealFlightModel.indSpeed);
        if (f < 0.0F) f = 0.0F;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_SensPitch_of_type_Float = (0.63F * f);
        if (localRealFlightModel.indSpeed > 155.0F) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.producedAM.y -= 6000.0F * (155.0F - localRealFlightModel.indSpeed); 
      }
      else {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_SensPitch_of_type_Float = 0.63F;
      }
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    int j;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("1")) {
          getEnergyPastArmor(World.Rnd().nextFloat(20.0F, 30.0F), paramShot);

          Aircraft.debugprintln(this, "*** Armor Glass: Hit..");
          if (paramShot.power <= 0.0F) {
            Aircraft.debugprintln(this, "*** Armor Glass: Bullet Stopped..");
            if (World.Rnd().nextFloat() < 0.5F) {
              doRicochetBack(paramShot);
            }
          }
        }
        if (paramString.endsWith("2")) {
          getEnergyPastArmor(12.7F / (1.0E-005F + (float)Math.abs(Aircraft.v1.jdField_x_of_type_Double)), paramShot);
        }
        if (paramString.endsWith("3")) {
          getEnergyPastArmor(12.7F / (1.0E-005F + (float)Math.abs(Aircraft.v1.jdField_x_of_type_Double)), paramShot);
        }
        if (paramString.endsWith("4")) {
          getEnergyPastArmor(8.9F / (1.0E-005F + (float)Math.abs(Aircraft.v1.jdField_x_of_type_Double)), paramShot);
        }
        if (paramString.endsWith("5")) {
          getEnergyPastArmor(8.9F / (1.0E-005F + (float)Math.abs(Aircraft.v1.z)), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxarcon")) {
        if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 0);
          Aircraft.debugprintln(this, "*** Ailerones Controls Out..");
        }
        return;
      }
      if (paramString.startsWith("xxvatcon")) {
        if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 1);
          Aircraft.debugprintln(this, "*** Elevators Controls Out..");
        }
        return;
      }
      if (paramString.startsWith("xxrudcon")) {
        if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setControlsDamage(paramShot.initiator, 2);
          Aircraft.debugprintln(this, "*** Rudder Controls Out..");
        }
        return;
      }
      if (paramString.startsWith("xxeng1")) {
        if ((getEnergyPastArmor(4.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersRatio() * 0.75F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 6800.0F)));
          Aircraft.debugprintln(this, "*** Engine 0 Cylinders Hit, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylinders() + " Left..");
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[0] < 1) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 1);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetEngineState(paramShot.initiator, 0, 1);
          }
          if (World.Rnd().nextFloat() < paramShot.power / 48000.0F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 3);
            Aircraft.debugprintln(this, "*** Engine 0 Cylinders Hit - Engine Fires..");
          }
          getEnergyPastArmor(25.0F, paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxeng2")) {
        if ((getEnergyPastArmor(4.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getCylindersRatio() * 0.75F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 6800.0F)));
          Aircraft.debugprintln(this, "*** Engine 1 Cylinders Hit, " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getCylindersOperable() + "/" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getCylinders() + " Left..");
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[1] < 1) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 1, 1);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetEngineState(paramShot.initiator, 1, 1);
          }
          if (World.Rnd().nextFloat() < paramShot.power / 48000.0F) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 1, 3);
            Aircraft.debugprintln(this, "*** Engine 1 Cylinders Hit - Engine Fires..");
          }
          getEnergyPastArmor(25.0F, paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxoilradiat1")) {
        if ((getEnergyPastArmor(0.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
          Aircraft.debugprintln(this, "*** Engine Module 0: Oil Radiator Hit..");
        }
        return;
      }
      if (paramString.startsWith("xxoilradiat2")) {
        if ((getEnergyPastArmor(0.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 1);
          Aircraft.debugprintln(this, "*** Engine Module 1: Oil Radiator Hit..");
        }
        return;
      }
      if (paramString.startsWith("xxoiltank1")) {
        if ((getEnergyPastArmor(2.38F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
          Aircraft.debugprintln(this, "*** Engine Module 0: Oil Radiator Hit..");
        }
        return;
      }
      if (paramString.startsWith("xxoiltank2")) {
        if ((getEnergyPastArmor(2.38F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 1);
          Aircraft.debugprintln(this, "*** Engine Module 1: Oil Radiator Hit..");
        }
        return;
      }
      if (paramString.startsWith("xxmagneto1")) {
        i = World.Rnd().nextInt(0, 1);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setMagnetoKnockOut(paramShot.initiator, i);
        Aircraft.debugprintln(this, "*** Engine Module 0: Magneto " + i + " Destroyed..");
        return;
      }
      if (paramString.startsWith("xxmagneto2")) {
        i = World.Rnd().nextInt(0, 1);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].setMagnetoKnockOut(paramShot.initiator, i);
        Aircraft.debugprintln(this, "*** Engine Module 1: Magneto " + i + " Destroyed..");
        return;
      }
      if (paramString.startsWith("xxturbo1")) {
        if (getEnergyPastArmor(1.23F, paramShot) > 0.0F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setKillCompressor(paramShot.initiator);
          Aircraft.debugprintln(this, "*** Engine Module 0: Supercharger Destroyed..");
        }
        return;
      }
      if (paramString.startsWith("xxturbo2")) {
        if (getEnergyPastArmor(1.23F, paramShot) > 0.0F) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].setKillCompressor(paramShot.initiator);
          Aircraft.debugprintln(this, "*** Engine Module 1: Supercharger Destroyed..");
        }
        return;
      }
      if (paramString.startsWith("xxradiat")) {
        i = 0;
        if ((paramString.endsWith("3")) || (paramString.endsWith("4"))) {
          i = 1;
        }
        if (World.Rnd().nextFloat() < 0.25F) {
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[i] == 0) {
            debuggunnery("Engine Module: Water Radiator Pierced..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 1);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetEngineState(paramShot.initiator, i, 1);
          } else if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[i] == 1) {
            debuggunnery("Engine Module: Water Radiator Pierced..");
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, i, 1);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetEngineState(paramShot.initiator, i, 2);
          }
        }
        return;
      }
      if (paramString.startsWith("xxtank")) {
        i = 0; j = paramString.charAt(6) - '0';
        switch (j) { case 1:
          i = 1; break;
        case 2:
          i = 1; break;
        case 3:
          i = 0; break;
        case 4:
          i = 2; break;
        case 5:
          i = 2; break;
        case 6:
          i = 3;
        }
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 1);
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.11F)) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, i, 2);
          }
        }
        return;
      }
      if (paramString.startsWith("xxgun")) {
        i = paramString.charAt(5) - '1';
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, i);
        getEnergyPastArmor(23.5F, paramShot);
        return;
      }
      if (paramString.startsWith("xxcannon")) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 0);
        getEnergyPastArmor(48.599998F, paramShot);
        return;
      }
      if (paramString.startsWith("xxammogun")) {
        i = World.Rnd().nextInt(0, 3);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, i);
        getEnergyPastArmor(23.5F, paramShot);
        return;
      }
      if (paramString.startsWith("xxammocan")) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 0);
        getEnergyPastArmor(23.5F, paramShot);
        return;
      }
      if (paramString.startsWith("xxspar")) {
        Aircraft.debugprintln(this, "*** Spar Construction: Hit..");
        if ((paramString.startsWith("xxsparli")) && 
          (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(19.700001F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparri")) && 
          (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(19.700001F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlm")) && 
          (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparrm")) && 
          (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlo")) && 
          (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparro")) && 
          (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxpark1")) && 
          (chunkDamageVisible("Keel1") > 1) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** Keel1 Spars Damaged..");
          nextDMGLevels(1, 2, "Keel1_D2", paramShot.initiator);
        }

        if ((paramString.startsWith("xxpark2")) && 
          (chunkDamageVisible("Keel2") > 1) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** Keel2 Spars Damaged..");
          nextDMGLevels(1, 2, "Keel2_D2", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparsl")) && 
          (chunkDamageVisible("StabL") > 1) && (getEnergyPastArmor(12.7F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** Stab Spars Damaged..");
          nextDMGLevels(1, 2, "StabL_D2", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxlock")) {
        Aircraft.debugprintln(this, "*** Lock Construction: Hit..");
        if (((paramString.startsWith("xxlockk1")) || (paramString.startsWith("xxlockk2"))) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** Rudder1 Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
        }

        if (((paramString.startsWith("xxlockk3")) || (paramString.startsWith("xxlockk4"))) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** Rudder2 Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder2_D" + chunkDamageVisible("Rudder2"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlocksl")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** Vator Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockal")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** AroneL Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockar")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          Aircraft.debugprintln(this, "*** AroneR Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), paramShot.initiator);
        }

        return;
      }
      return;
    }

    if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit"))) {
      hitChunk("CF", paramShot);
    }
    if (!paramString.startsWith("xblister"))
    {
      if (paramString.startsWith("xengine1")) {
        hitChunk("Engine1", paramShot);
      } else if (paramString.startsWith("xengine2")) {
        hitChunk("Engine2", paramShot);
      } else if (paramString.startsWith("xtail1")) {
        hitChunk("Tail1", paramShot);
      } else if (paramString.startsWith("xtail2")) {
        hitChunk("Tail2", paramShot);
      } else if (paramString.startsWith("xkeel1")) {
        hitChunk("Keel1", paramShot);
      } else if (paramString.startsWith("xkeel2")) {
        hitChunk("Keel2", paramShot);
      } else if (paramString.startsWith("xrudder1")) {
        hitChunk("Rudder1", paramShot);
      } else if (paramString.startsWith("xrudder2")) {
        hitChunk("Rudder2", paramShot);
      } else if (paramString.startsWith("xstabl")) {
        hitChunk("StabL", paramShot);
      } else if (paramString.startsWith("xvatorl")) {
        if (chunkDamageVisible("VatorL") < 1)
          hitChunk("VatorL", paramShot);
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
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    Vector3d localVector3d = new Vector3d();
    Wreckage localWreckage;
    switch (paramInt1) {
    case 11:
      hierMesh().chunkVisible("Wire_D0", false);
      break;
    case 12:
      hierMesh().chunkVisible("Wire_D0", false);
      break;
    case 17:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.cut(17, paramInt2, paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.cut(18, paramInt2, paramActor);
      break;
    case 31:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.cut(31, paramInt2, paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.cut(32, paramInt2, paramActor);
      break;
    case 33:
      localWreckage = new Wreckage(this, hierMesh().chunkFind("Flap03_D0"));
      localWreckage.collide(false);
      localVector3d.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d); localWreckage.setSpeed(localVector3d);
      hierMesh().chunkVisible("Flap03_D0", false);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.cut(19, paramInt2, paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.cut(17, paramInt2, paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.cut(18, paramInt2, paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.cut(31, paramInt2, paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.cut(32, paramInt2, paramActor);
    case 34:
      localWreckage = new Wreckage(this, hierMesh().chunkFind("Flap02_D0"));
      localWreckage.collide(false);
      localVector3d.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d); localWreckage.setSpeed(localVector3d);
      hierMesh().chunkVisible("Flap02_D0", false);
      break;
    case 36:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.cut(20, paramInt2, paramActor);
      localWreckage = new Wreckage(this, hierMesh().chunkFind("Flap05_D0"));
      localWreckage.collide(false);
      localVector3d.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d); localWreckage.setSpeed(localVector3d);
      hierMesh().chunkVisible("Flap05_D0", false);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.cut(17, paramInt2, paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.cut(18, paramInt2, paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.cut(31, paramInt2, paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.cut(32, paramInt2, paramActor);
    case 37:
      localWreckage = new Wreckage(this, hierMesh().chunkFind("Flap04_D0"));
      localWreckage.collide(false);
      localVector3d.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d); localWreckage.setSpeed(localVector3d);
      hierMesh().chunkVisible("Flap04_D0", false);
    case 13:
    case 14:
    case 15:
    case 16:
    case 18:
    case 19:
    case 20:
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
    case 32:
    case 35: } return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  static
  {
    Class localClass = P_38.class;
    Property.set(localClass, "originCountry", PaintScheme.countryUSA);
  }
}