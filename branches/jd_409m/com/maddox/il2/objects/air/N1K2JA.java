package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class N1K2JA extends N1K
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.35F, 0.95F, 0.0F, -82.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, Aircraft.cvt(paramFloat, 0.35F, 0.4F, 0.0F, -90.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, Aircraft.cvt(paramFloat, 0.35F, 0.95F, 0.0F, -48.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL8_D0", 0.0F, Aircraft.cvt(paramFloat, 0.35F, 0.95F, 0.0F, -58.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.65F, 0.0F, -82.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.1F, 0.0F, -90.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.65F, 0.0F, -48.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR8_D0", 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.65F, 0.0F, -58.0F), 0.0F);
    float tmp185_184 = (Aircraft.xyz[2] = Aircraft.ypr[0] = Aircraft.ypr[1] = Aircraft.ypr[2] = 0.0F); Aircraft.xyz[1] = tmp185_184; Aircraft.xyz[0] = tmp185_184;
    Aircraft.xyz[0] = Aircraft.cvt(paramFloat, 0.0F, 1.0F, -0.075F, 0.0F);
    Aircraft.ypr[1] = Aircraft.cvt(paramFloat, 0.0F, 1.0F, 40.0F, 0.0F);
    paramHierMesh.chunkSetLocate("GearC2_D0", Aircraft.xyz, Aircraft.ypr);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveWheelSink() {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.getGear() == 1.0F) {
      hierMesh().chunkSetAngles("GearC2_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[2], 0.0F, 0.1F, 0.0F, 20.0F), 0.0F);
    }

    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.23F, 0.0F, 0.23F);
    hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
    hierMesh().chunkSetAngles("GearL4_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.23F, 0.0F, -42.0F), 0.0F);
    hierMesh().chunkSetAngles("GearL5_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.23F, 0.0F, -45.0F), 0.0F);
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.27625F, 0.0F, 0.27625F);
    hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
    hierMesh().chunkSetAngles("GearR4_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.27625F, 0.0F, -33.0F), 0.0F);
    hierMesh().chunkSetAngles("GearR5_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.27625F, 0.0F, -66.0F), 0.0F);
  }
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);

    float f = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getControlRadiator();
    if (Math.abs(this.jdField_flapps_of_type_Float - f) > 0.01F) {
      this.jdField_flapps_of_type_Float = f;
      for (int i = 1; i < 9; i++)
        hierMesh().chunkSetAngles("Cowflap" + i + "_D0", 0.0F, -20.0F * f, 0.0F);
    }
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "N1K");
    Property.set(localClass, "meshName", "3DO/Plane/N1K2-Ja(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "meshName_ja", "3DO/Plane/N1K2-Ja(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeFCSPar05());

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/N1K2-Ja.fmd");
    Property.set(localClass, "cockpitClass", CockpitN1K2JA.class);
    Property.set(localClass, "LOSElevation", 1.1716F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1, 1, 1, 3, 3, 3, 3, 9, 9 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunHo5k 200", "MGunHo5k 200", "MGunHo5k 200", "MGunHo5k 200", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1x400dt", new String[] { "MGunHo5k 200", "MGunHo5k 200", "MGunHo5k 200", "MGunHo5k 200", null, null, null, null, "PylonN1K1PLN1", "FuelTankGun_TankN1K1" });

    Aircraft.weaponsRegister(localClass, "2x60", new String[] { "MGunHo5k 200", "MGunHo5k 200", "MGunHo5k 200", "MGunHo5k 200", "BombGun60kgJ 1", "BombGun60kgJ 1", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4x60", new String[] { "MGunHo5k 200", "MGunHo5k 200", "MGunHo5k 200", "MGunHo5k 200", "BombGun60kgJ 1", "BombGun60kgJ 1", "BombGun60kgJ 1", "BombGun60kgJ 1", null, null });

    Aircraft.weaponsRegister(localClass, "2x100", new String[] { "MGunHo5k 200", "MGunHo5k 200", "MGunHo5k 200", "MGunHo5k 200", "BombGun100kgJ 1", "BombGun100kgJ 1", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4x100", new String[] { "MGunHo5k 200", "MGunHo5k 200", "MGunHo5k 200", "MGunHo5k 200", "BombGun100kgJ 1", "BombGun100kgJ 1", "BombGun100kgJ 1", "BombGun100kgJ 1", null, null });

    Aircraft.weaponsRegister(localClass, "2x250", new String[] { "MGunHo5k 200", "MGunHo5k 200", "MGunHo5k 200", "MGunHo5k 200", "BombGun250kgJ 1", "BombGun250kgJ 1", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null });
  }
}