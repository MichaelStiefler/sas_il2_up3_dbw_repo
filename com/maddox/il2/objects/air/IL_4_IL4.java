package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class IL_4_IL4 extends IL_4_DB3F
  implements TypeBomber
{
  static
  {
    Class localClass = IL_4_IL4.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "DB-3");
    Property.set(localClass, "meshName", "3DO/Plane/Il-4/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar04());

    Property.set(localClass, "yearService", 1936.0F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Il-4.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 3, 3, 3, 3, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_BombSpawn01", "_BombSpawn02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "10fab50", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", null, null, null, null, "BombGunFAB50 5", "BombGunFAB50 5" });

    Aircraft.weaponsRegister(localClass, "10fab100", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", null, null, null, null, "BombGunFAB100 5", "BombGunFAB100 5" });

    Aircraft.weaponsRegister(localClass, "3fab250", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB250", "BombGunNull", "BombGunFAB250", "BombGunFAB250", null, null });

    Aircraft.weaponsRegister(localClass, "3fab25010fab50", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB250", "BombGunNull", "BombGunFAB250", "BombGunFAB250", "BombGunFAB50 5", "BombGunFAB50 5" });

    Aircraft.weaponsRegister(localClass, "3fab25010fab100", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB250", "BombGunNull", "BombGunFAB250", "BombGunFAB250", "BombGunFAB100 5", "BombGunFAB100 5" });

    Aircraft.weaponsRegister(localClass, "1fab500", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB500", null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1fab5002fab250", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB500", "BombGunNull", "BombGunFAB250", "BombGunFAB250", null, null });

    Aircraft.weaponsRegister(localClass, "3fab500", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB500", "BombGunNull", "BombGunFAB500", "BombGunFAB500", null, null });

    Aircraft.weaponsRegister(localClass, "3fab50010fab50", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB500", "BombGunNull", "BombGunFAB500", "BombGunFAB500", "BombGunFAB50 5", "BombGunFAB50 5" });

    Aircraft.weaponsRegister(localClass, "1fab1000", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB1000", null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1fab100010fab50", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB1000", "BombGunNull", null, null, "BombGunFAB50 5", "BombGunFAB50 5" });

    Aircraft.weaponsRegister(localClass, "torp1", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGun4512", null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null });
  }
}