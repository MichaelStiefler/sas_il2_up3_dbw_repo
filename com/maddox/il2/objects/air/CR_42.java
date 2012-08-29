package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class CR_42 extends CR_42X
{
  static
  {
    Class localClass = CR_42.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "CR.42");
    Property.set(localClass, "meshName", "3DO/Plane/CR42(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar00());
    Property.set(localClass, "meshName_it", "3DO/Plane/CR42(it)/hier.him");
    Property.set(localClass, "PaintScheme_it", new PaintSchemeFCSPar01());

    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1943.0F);

    Property.set(localClass, "FlightModel", "FlightModels/CR42.fmd");
    Property.set(localClass, "cockpitClass", CockpitCR42.class);
    Property.set(localClass, "LOSElevation", 0.742F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02" });

    weaponsRegister(localClass, "default", new String[] { "MGunBredaSAFAT127siCR42 400", "MGunBredaSAFAT127siCR42 400", null, null });

    weaponsRegister(localClass, "2sc50", new String[] { "MGunBredaSAFAT127siCR42 400", "MGunBredaSAFAT127siCR42 400", "BombGunSC50 1", "BombGunSC50 1" });

    weaponsRegister(localClass, "2sc70", new String[] { "MGunBredaSAFAT127siCR42 400", "MGunBredaSAFAT127siCR42 400", "BombGunSC70 1", "BombGunSC70 1" });

    weaponsRegister(localClass, "2x50", new String[] { "MGunBredaSAFAT127siCR42 400", "MGunBredaSAFAT127siCR42 400", "BombGun50kg 1", "BombGun50kg 1" });

    weaponsRegister(localClass, "2x100", new String[] { "MGunBredaSAFAT127siCR42 400", "MGunBredaSAFAT127siCR42 400", "BombGun100kg 1", "BombGun100kg 1" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}