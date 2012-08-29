package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class ME_262A1AU4 extends ME_262
  implements TypeStormovik
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 103.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC21_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.11F, 0.0F, -90.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC6_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.11F, 0.0F, -90.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 73.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 73.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, 88.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, 88.0F * paramFloat, 0.0F);

    float f = Math.max(-paramFloat * 1500.0F, -90.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, f, 0.0F);
  }
  protected void moveGear(float paramFloat) {
    moveGear(hierMesh(), paramFloat);
  }
  public void moveWheelSink() { resetYPRmodifier();
    float f = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Gears.gWheelSinking[2];
    Aircraft.xyz[1] = Aircraft.cvt(f, 0.0F, 0.19F, 0.0F, 0.19F);
    hierMesh().chunkSetLocate("GearC22_D0", Aircraft.xyz, Aircraft.ypr);
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.getGear() > 0.75F)
      hierMesh().chunkSetAngles("GearC21_D0", 0.0F, -90.0F + 40.0F * paramFloat, 0.0F);
  }

  static
  {
    Class localClass = ME_262A1AU4.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Me 262");
    Property.set(localClass, "meshName", "3DO/Plane/Me-262A-1aU4/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Me-262A-1aU4.fmd");
    Property.set(localClass, "cockpitClass", CockpitME_262.class);
    Property.set(localClass, "LOSElevation", 0.74615F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 1 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMK214A 28" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null });
  }
}