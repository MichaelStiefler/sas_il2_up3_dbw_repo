package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class P_80A extends P_80
{
  static
  {
    Class localClass = P_80A.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "YP-80");
    Property.set(localClass, "meshName", "3DO/Plane/P-80(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "meshName_us", "3DO/Plane/P-80(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar06());
    Property.set(localClass, "meshNameDemo", "3DO/Plane/P-80(USA)/hier.him");

    Property.set(localClass, "yearService", 1944.9F);
    Property.set(localClass, "yearExpired", 1948.3F);

    Property.set(localClass, "FlightModel", "FlightModels/P-80A.fmd");
    Property.set(localClass, "cockpitClass", CockpitYP_80.class);
    Property.set(localClass, "LOSElevation", 0.965F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}