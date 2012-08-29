package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class IL_2T extends IL_2
{
  static
  {
    Class localClass = IL_2T.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "IL2");
    Property.set(localClass, "meshName", "3do/plane/Il-2T(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar03());
    Property.set(localClass, "meshName_ru", "3do/plane/Il-2T/hier.him");
    Property.set(localClass, "PaintScheme_ru", new PaintSchemeBCSPar03());

    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Il-2M3.fmd");

    Property.set(localClass, "cockpitClass", new Class[] { CockpitIL_2_1942.class, CockpitIL2_Gunner.class });

    Property.set(localClass, "LOSElevation", 0.81F);
    Property.set(localClass, "Handicap", 1.2F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 10, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb07" });
    weaponsRegister(localClass, "default", new String[] { "MGunShKASi 250", "MGunShKASi 250", "MGunUBt 150", null });
    weaponsRegister(localClass, "1x45-12", new String[] { "MGunShKASi 250", "MGunShKASi 250", "MGunUBt 150", "BombGun4512" });
    weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}