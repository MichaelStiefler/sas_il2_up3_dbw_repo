package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class BF_110C4 extends BF_110
{
  static
  {
    Class localClass = BF_110C4.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Bf-110");
    Property.set(localClass, "meshName", "3DO/Plane/Bf-110C-4/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar01());

    Property.set(localClass, "yearService", 1940.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Bf-110C-4.fmd");

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 1, 10 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02", "_MGUN05" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMGFFk 180", "MGunMGFFk 180", "MGunMG15t 750" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null });
  }
}