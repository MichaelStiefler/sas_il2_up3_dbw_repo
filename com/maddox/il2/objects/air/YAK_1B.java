package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class YAK_1B extends YAK
  implements TypeTNBFighter
{
  public void update(float paramFloat)
  {
    hierMesh().chunkSetAngles("Wind_luk", 0.0F, this.FM.EI.engines[0].getControlRadiator() * 19.0F, 0.0F);
    hierMesh().chunkSetAngles("Water_luk", 0.0F, this.FM.EI.engines[0].getControlRadiator() * 15.0F, 0.0F);
    super.update(paramFloat);
  }
  public void update_windluk(float paramFloat) {
    super.update(paramFloat);
  }

  static
  {
    Class localClass = YAK_1B.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Yak");
    Property.set(localClass, "meshName", "3DO/Plane/Yak-1B(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar02());
    Property.set(localClass, "meshName_fr", "3DO/Plane/Yak-1B(fr)/hier.him");
    Property.set(localClass, "PaintScheme_fr", new PaintSchemeFCSPar05());

    Property.set(localClass, "yearService", 1942.6F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Yak-1B.fmd");

    Property.set(localClass, "cockpitClass", CockpitYAK_1.class);
    Property.set(localClass, "LOSElevation", 0.6759F);

    weaponTriggersRegister(localClass, new int[] { 0, 1, 9, 9, 9, 9, 9, 9, 2, 2, 2, 2, 2, 2, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_CANNON01", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalBomb01", "_ExternalBomb02" });

    weaponsRegister(localClass, "default", new String[] { "MGunUBsi 201", "MGunShVAKki 120", null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "6rs82", new String[] { "MGunUBsi 201", "MGunShVAKki 120", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", null, null });

    weaponsRegister(localClass, "2fab100", new String[] { "MGunUBsi 201", "MGunShVAKki 120", null, null, null, null, null, null, null, null, null, null, null, null, "BombGunFAB100 1", "BombGunFAB100 1" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}