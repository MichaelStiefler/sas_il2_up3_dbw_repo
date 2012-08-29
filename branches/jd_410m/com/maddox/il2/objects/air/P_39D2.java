package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class P_39D2 extends P_39
{
  static
  {
    Class localClass = P_39D2.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "P39");
    Property.set(localClass, "meshName", "3DO/Plane/P-39D-2(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar02());
    Property.set(localClass, "meshName_us", "3DO/Plane/P-39D-2(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar02());

    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/P-39D.fmd");
    Property.set(localClass, "cockpitClass", CockpitP_39N1.class);
    Property.set(localClass, "LOSElevation", 0.8941F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 1, 9, 3, 9 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_CANNON01", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev02" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50si 200", "MGunBrowning50si 200", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunHispanoMkIki 60", null, null, null });

    weaponsRegister(localClass, "m4", new String[] { "MGunBrowning50si 200", "MGunBrowning50si 200", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunM4ki 30", null, null, null });

    weaponsRegister(localClass, "1x500", new String[] { "MGunBrowning50si 200", "MGunBrowning50si 200", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunHispanoMkIki 60", "PylonP39PLN1", "BombGun500lbs 1", null });

    weaponsRegister(localClass, "1x75dt", new String[] { "MGunBrowning50si 200", "MGunBrowning50si 200", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunHispanoMkIki 60", "PylonP39PLN1", null, "FuelTankGun_Tank75gal" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null });
  }
}