package com.maddox.il2.objects.air;

import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.weapons.BombFAB100;
import com.maddox.il2.objects.weapons.BombFAB250;
import com.maddox.rts.Property;

public class I_185M82A extends I_185
{
  public void update(float paramFloat)
  {
    hierMesh().chunkSetAngles("Water1_D0", 0.0F, -20.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator(), 0.0F);
    for (int i = 1; i < 5; i++) {
      hierMesh().chunkSetAngles("Oil" + i + "_D0", 0.0F, -15.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator(), 0.0F);
    }
    super.update(paramFloat);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, -65.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, Aircraft.cvt(paramFloat, 0.02F, 0.1F, 0.0F, -65.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.02F, 0.1F, 0.0F, -65.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 87.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, Aircraft.cvt(paramFloat, 0.02F, 0.1F, 0.0F, -85.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL8_D0", 0.0F, -90.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 87.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, Aircraft.cvt(paramFloat, 0.02F, 0.1F, 0.0F, -85.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR8_D0", 0.0F, -90.0F * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat);
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    Object[] arrayOfObject = this.pos.getBaseAttached();
    if (arrayOfObject == null) return;
    for (int i = 0; i < arrayOfObject.length; i++) {
      if ((arrayOfObject[i] instanceof BombFAB100)) {
        hierMesh().chunkVisible("RackL1_D0", true);
        hierMesh().chunkVisible("RackR1_D0", true);
        return;
      }
      if ((arrayOfObject[i] instanceof BombFAB250)) {
        hierMesh().chunkVisible("RackL2_D0", true);
        hierMesh().chunkVisible("RackR2_D0", true);
        return;
      }
    }
  }

  static
  {
    Class localClass = I_185M82A.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "I-185");
    Property.set(localClass, "meshName", "3DO/Plane/I-185(M-82A)(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());

    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1945.0F);

    Property.set(localClass, "FlightModel", "FlightModels/I-185M-82A.fmd");
    Property.set(localClass, "cockpitClass", CockpitI_185M82.class);
    Property.set(localClass, "LOSElevation", 0.89135F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1, 1, 3, 3, 3, 3, 9, 9, 2, 2, 2, 2, 2, 2 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_CANNON03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShVAKsi 220", "MGunShVAKsi 220", "MGunShVAKsi 220", null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4fab100", new String[] { "MGunShVAKsi 220", "MGunShVAKsi 220", "MGunShVAKsi 220", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2fab250", new String[] { "MGunShVAKsi 220", "MGunShVAKsi 220", "MGunShVAKsi 220", "BombGunFAB250 1", "BombGunFAB250 1", null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "6rs82", new String[] { "MGunShVAKsi 220", "MGunShVAKsi 220", "MGunShVAKsi 220", null, null, null, null, "PylonRO_82_3", "PylonRO_82_3", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}