package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class SPITFIRE5C2 extends SPITFIRE
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.0F, 0.6F, 0.0F, -95.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.2F, 1.0F, 0.0F, -95.0F), 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }
  public void moveSteering(float paramFloat) { hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F); }

  public void moveWheelSink() {
    resetYPRmodifier();
    Aircraft.xyz[2] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.247F, 0.0F, -0.247F);
    hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[2] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.247F, 0.0F, 0.247F);
    hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Spit");
    Property.set(localClass, "meshName", "3DO/Plane/SpitfireMkVc(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());
    Property.set(localClass, "meshName_gb", "3DO/Plane/SpitfireMkVc(GB)/hier.him");
    Property.set(localClass, "PaintScheme_gb", new PaintSchemeFMPar04());

    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1946.5F);

    Property.set(localClass, "FlightModel", "FlightModels/SpitfireVB.fmd");
    Property.set(localClass, "cockpitClass", CockpitSpit5C.class);
    Property.set(localClass, "LOSElevation", 0.5926F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 1 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02" });
    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120" });
    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}