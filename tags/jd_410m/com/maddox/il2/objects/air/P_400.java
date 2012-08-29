package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class P_400 extends P_39
{
  static
  {
    Class localClass = P_400.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "P-400");
    Property.set(localClass, "meshName", "3DO/Plane/P-400(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar02());
    Property.set(localClass, "meshName_us", "3DO/Plane/P-400(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar02());

    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/P-400.fmd");
    Property.set(localClass, "cockpitClass", CockpitP_39N1.class);
    Property.set(localClass, "LOSElevation", 0.8941F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 1, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_CANNON01", "_ExternalBomb01" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50si 200", "MGunBrowning50si 200", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunHispanoMkIki 60", null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null });
  }
}