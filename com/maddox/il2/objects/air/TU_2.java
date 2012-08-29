package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public abstract class TU_2 extends Scheme2a
  implements TypeTransport
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = Math.max(-paramFloat * 1500.0F, -90.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, 0.777778F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, 0.777778F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -0.777778F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, -0.777778F * f, 0.0F);

    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 70.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -105.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -105.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 135.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 135.0F * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat);
  }

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);

    if ((paramShot.chunkName.startsWith("WingLMid")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, 1);
    if ((paramShot.chunkName.startsWith("WingRMid")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 3, 1);
    if ((paramShot.chunkName.startsWith("WingLIn")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 1, 1);
    if ((paramShot.chunkName.startsWith("WingRIn")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 2, 1);
    if ((paramShot.chunkName.startsWith("Engine1")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 1);
    if ((paramShot.chunkName.startsWith("Engine2")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 1, 1);
    }
    super.msgShot(paramShot);
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      break;
    case 1:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
      break;
    case 2:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
      break;
    case 3:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = false;
    }
  }

  public void doMurderPilot(int paramInt) {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("Head1_D0", false);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      break;
    case 2:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("HMask3_D0", false);
      hierMesh().chunkVisible("Pilot3_D1", true);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F) {
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("HMask3_D0", false);
    } else {
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
      hierMesh().chunkVisible("HMask2_D0", hierMesh().isChunkVisible("Pilot2_D0"));
      hierMesh().chunkVisible("HMask3_D0", hierMesh().isChunkVisible("Pilot3_D0"));
    }
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -30.0F) { f1 = -30.0F; bool = false; }
      if (f1 > 30.0F) { f1 = 30.0F; bool = false; }
      if (f2 > 30.0F) { f2 = 30.0F; bool = false; }
      float f3 = Math.abs(f1);
      if (f3 < 4.5F) {
        if (f2 >= -0.111F * f3) break; f2 = -0.111F * f3; bool = false;
      } else if (f3 < 14.8F) {
        if (f2 < 1.08108F - 0.35135F * f3) { f2 = 1.08108F - 0.35135F * f3; bool = false; }
        if ((f2 >= -0.9369F + 0.0971F * f3) || (f2 <= -2.4369F + 0.0971F * f3)) break; bool = false;
      } else if (f3 < 17.200001F) {
        if (f2 < 1.08108F - 0.35135F * f3) { f2 = 1.08108F - 0.35135F * f3; bool = false; }
        if ((f2 >= 8.5F) && (f2 <= -5.5F)) break; bool = false;
      } else if (f3 < 23.0F) {
        if (f2 >= 1.08108F - 0.35135F * f3) break; f2 = 1.08108F - 0.35135F * f3; bool = false;
      } else {
        if (f2 >= -7.0F) break; f2 = -7.0F; bool = false; } break;
    case 1:
      if (f1 < -20.0F) { f1 = -20.0F; bool = false; }
      if (f1 > 45.0F) { f1 = 45.0F; bool = false; }
      if (f1 < -15.5F) {
        if (f2 < 0.7778F + 0.3889F * f1) { f2 = 0.7778F + 0.3889F * f1; bool = false; }
        if (f2 > 23.0F) { f2 = 23.0F; bool = false; }
        if ((f2 >= -0.2182F - 0.1091F * f1) && (f2 <= -7.509F - 0.2545F * f1)) break; bool = false;
      } else if (f1 < 2.0F) {
        if (f2 < 0.7778F + 0.3889F * f1) { f2 = 0.7778F + 0.3889F * f1; bool = false; }
        if (f2 > 40.0F) { f2 = 40.0F; bool = false; }
        if ((f2 >= -0.2182D - 0.1091F * f1) && (f2 <= -7.509F - 0.2545F * f1)) break; bool = false;
      } else if (f1 < 14.0F) {
        if (f2 < 0.186172F * f1) { f2 = 0.186172F * f1; bool = false; }
        if (f2 > 40.0F) { f2 = 40.0F; bool = false; }
        if ((f2 >= 0.2034F + 0.1017F * f1) && (f2 <= -6.5254F + 0.2373F * f1)) break; bool = false;
      } else if (f1 < 27.5F) {
        if (f2 < 0.186172F * f1) { f2 = 0.186172F * f1; bool = false; }
        if (f2 > 45.839001F - 0.7742F * f1) { f2 = 45.839001F - 0.7742F * f1; bool = false; }
        if ((f2 >= 0.2034F + 0.1017F * f1) && (f2 <= -6.5254F + 0.2373F * f1)) break; bool = false;
      } else if (f1 < 38.0F) {
        if (f2 < -4.2132F + 0.5714F * f1) { f2 = -4.2132F + 0.5714F * f1; bool = false; }
        if (f2 <= 45.839001F - 0.7742F * f1) break; f2 = 45.839001F - 0.7742F * f1; bool = false; } else {
        if (f1 >= 45.0F) break;
        if (f2 < -7.5F) { f2 = -7.5F; bool = false; }
        if (f2 <= 45.839001F - 0.7742F * f1) break; f2 = 45.839001F - 0.7742F * f1; bool = false; } break;
    case 2:
      if (f1 < -30.0F) { f1 = -30.0F; bool = false; }
      if (f1 > 30.0F) { f1 = 30.0F; bool = false; }
      if (f2 < -50.0F) { f2 = -50.0F; bool = false; }
      if (f2 <= 1.0F) break; f2 = 1.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  static
  {
    Class localClass = TU_2.class;
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);
  }
}