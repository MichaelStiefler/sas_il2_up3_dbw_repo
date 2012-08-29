package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class BF_110C4B extends BF_110
{
  static
  {
    Class localClass = BF_110C4B.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Bf-110");
    Property.set(localClass, "meshName", "3DO/Plane/Bf-110C-4B/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar01());

    Property.set(localClass, "yearService", 1940.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Bf-110C-4.fmd");

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 1, 10, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02", "_MGUN05", "_ExternalBomb01", "_ExternalBomb02" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1000", "MGunMG17ki 1048", "MGunMGFFki 180", "MGunMGFFki 180", "MGunMG15t 750", null, null });

    weaponsRegister(localClass, "2sc250", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1000", "MGunMG17ki 1048", "MGunMGFFki 180", "MGunMGFFki 180", "MGunMG15t 750", "BombGunSC250", "BombGunSC250" });

    weaponsRegister(localClass, "2ab250", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1000", "MGunMG17ki 1048", "MGunMGFFki 180", "MGunMGFFki 180", "MGunMG15t 750", "BombGunAB250", "BombGunAB250" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null });
  }
}