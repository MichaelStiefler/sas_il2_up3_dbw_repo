package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class SB_2M100A extends SB
{
  static
  {
    Class localClass = SB_2M100A.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "SB");
    Property.set(localClass, "meshNameDemo", "3DO/Plane/SB-2M-100A(Russian)/hier.him");
    Property.set(localClass, "meshName", "3DO/Plane/SB-2M-100A(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar00());
    Property.set(localClass, "meshName_ru", "3DO/Plane/SB-2M-100A(Russian)/hier.him");
    Property.set(localClass, "PaintScheme_ru", new PaintSchemeBCSPar01());

    Property.set(localClass, "yearService", 1935.0F);
    Property.set(localClass, "yearExpired", 1944.0F);

    Property.set(localClass, "FlightModel", "FlightModels/SB-2M-100A.fmd");

    weaponTriggersRegister(localClass, new int[] { 10, 10, 11, 12, 3, 3, 3, 3, 3, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07" });

    weaponsRegister(localClass, "default", new String[] { "MGunShKASt 960", "MGunShKASt 960", "MGunShKASt 1000", "MGunShKASt 500", null, null, null, null, null, null, null });

    weaponsRegister(localClass, "6xfab50", new String[] { "MGunShKASt 960", "MGunShKASt 960", "MGunShKASt 1000", "MGunShKASt 500", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null });

    weaponsRegister(localClass, "6xfab100", new String[] { "MGunShKASt 700", "MGunShKASt 700", "MGunShKASt 1000", "MGunShKASt 300", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null });

    weaponsRegister(localClass, "1xfab250", new String[] { "MGunShKASt 960", "MGunShKASt 960", "MGunShKASt 1000", "MGunShKASt 500", null, null, null, null, null, null, "BombGunFAB250" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null });
  }
}