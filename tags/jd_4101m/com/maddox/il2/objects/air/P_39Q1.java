package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class P_39Q1 extends P_39
{
  static
  {
    Class localClass = P_39Q1.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "P39");
    Property.set(localClass, "meshName", "3do/plane/P-39Q-1/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/P-39Q-1.fmd");
    Property.set(localClass, "cockpitClass", CockpitP_39Q1.class);
    Property.set(localClass, "LOSElevation", 0.8941F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_ExternalBomb01" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50si 200", "MGunBrowning50si 200", "MGunBrowning50kh 300", "MGunBrowning50kh 300", "MGunM4ki 30", null });

    weaponsRegister(localClass, "1xFAB250", new String[] { "MGunBrowning50si 200", "MGunBrowning50si 200", "MGunBrowning50kh 300", "MGunBrowning50kh 300", "MGunM4ki 30", "BombGunFAB250 1" });
    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}