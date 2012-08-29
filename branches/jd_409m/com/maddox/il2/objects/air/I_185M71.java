package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class I_185M71 extends I_185
{
  public void update(float paramFloat)
  {
    hierMesh().chunkSetAngles("Water1_D0", 0.0F, -20.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator(), 0.0F);
    for (int i = 1; i < 9; i++) {
      hierMesh().chunkSetAngles("Oil" + i + "_D0", 0.0F, -15.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator(), 0.0F);
    }
    super.update(paramFloat);
  }

  static
  {
    Class localClass = I_185M71.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "I-185");
    Property.set(localClass, "meshName", "3DO/Plane/I-185(M-71)(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());

    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1945.0F);

    Property.set(localClass, "FlightModel", "FlightModels/I-185M-71.fmd");
    Property.set(localClass, "cockpitClass", CockpitI_185M71.class);
    Property.set(localClass, "LOSElevation", 0.89135F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1, 1 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_CANNON03" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShVAKsi 220", "MGunShVAKsi 220", "MGunShVAKsi 220" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null });
  }
}