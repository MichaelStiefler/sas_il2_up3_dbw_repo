package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class IL_4_DB3T extends IL_4
  implements TypeBomber
{
  static
  {
    Class localClass = IL_4_DB3T.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "DB-3");
    Property.set(localClass, "meshName", "3DO/Plane/DB-3T/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar01());

    Property.set(localClass, "yearService", 1936.0F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/DB-3T.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", null });

    Aircraft.weaponsRegister(localClass, "torp1", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGun4512" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}