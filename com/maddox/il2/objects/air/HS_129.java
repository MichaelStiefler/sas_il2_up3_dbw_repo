package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public abstract class HS_129 extends Scheme2
  implements TypeStormovik
{
  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsAboutToBailout) break;
      if (hierMesh().isChunkVisible("Blister1_D0")) {
        hierMesh().chunkVisible("Gore1_D0", true);
      }
      hierMesh().chunkVisible("Gore2_D0", true);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -45.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("Step_D0", 0.0F, 0.0F, -5.0F * paramFloat);
    float f = Math.max(-paramFloat * 1500.0F, -90.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -f, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) { case 33:
      return super.cutFM(34, paramInt2, paramActor);
    case 36:
      return super.cutFM(37, paramInt2, paramActor);
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);

    if (paramShot.chunkName.startsWith("CF")) {
      if ((Math.sqrt(Aircraft.v1.jdField_y_of_type_Double * Aircraft.v1.jdField_y_of_type_Double + Aircraft.v1.jdField_z_of_type_Double * Aircraft.v1.jdField_z_of_type_Double) > 0.1389999985694885D) && (World.Rnd().nextFloat() < 0.12F)) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 2, (int)World.Rnd().nextFloat(0.0F, paramShot.mass * 9.21F));
      }
      if (Aircraft.v1.jdField_x_of_type_Double < -0.9739999771118164D) {
        if ((World.Rnd().nextFloat() < 0.3F) && 
          (-paramShot.power * Aircraft.v1.jdField_x_of_type_Double > 19200.0D)) {
          killPilot(paramShot.initiator, 0);
          paramShot.chunkName = "CF_D0";
        }
      }
      else if (Aircraft.v1.jdField_x_of_type_Double < 0.765999972820282D) {
        if (World.Rnd().nextFloat() < 0.12F) {
          if (paramShot.power * Math.sqrt(Aircraft.v1.jdField_y_of_type_Double * Aircraft.v1.jdField_y_of_type_Double + Aircraft.v1.jdField_z_of_type_Double * Aircraft.v1.jdField_z_of_type_Double) > 1600.0F * (Aircraft.v1.jdField_z_of_type_Double > -0.5D ? 6.0F : 1.0F)) {
            killPilot(paramShot.initiator, 0);
            paramShot.chunkName = "CF_D0";
          }
        }
      }
      else if ((World.Rnd().nextFloat() < 0.06F) && 
        (paramShot.power * Aircraft.v1.jdField_x_of_type_Double > 12800.0D)) {
        killPilot(paramShot.initiator, 0);
        paramShot.chunkName = "CF_D0";
      }

    }

    if (paramShot.chunkName.startsWith("Pilot")) {
      killPilot(paramShot.initiator, 0);
      paramShot.chunkName = "CF_D0";
    }

    if ((paramShot.chunkName.startsWith("WingLIn")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.25F))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, 1);
    if ((paramShot.chunkName.startsWith("WingRIn")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.25F)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 1, 1);
    }
    if (paramShot.chunkName.startsWith("Engine1")) {
      if (World.Rnd().nextFloat(0.0F, 3.0F) < paramShot.mass) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 1);
      if ((Aircraft.v1.jdField_y_of_type_Double > 0.8999999761581421D) && (World.Rnd().nextFloat() < 0.067F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 5);
    }
    if (paramShot.chunkName.startsWith("Engine2")) {
      if (World.Rnd().nextFloat(0.0F, 3.0F) < paramShot.mass) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 1, 1);
      if ((Aircraft.v1.jdField_y_of_type_Double < -0.8999999761581421D) && (World.Rnd().nextFloat() < 0.067F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 1, 5);
    }

    super.msgShot(paramShot);
  }

  static
  {
    Class localClass = HS_129.class;
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
  }
}