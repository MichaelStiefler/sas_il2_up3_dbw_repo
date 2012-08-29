package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class KI_27_KO extends KI_27
{
  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Ki-27");
    Property.set(localClass, "meshName", "3DO/Plane/Ki-27(Ko)(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "meshName_ja", "3DO/Plane/Ki-27(Ko)(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeBCSPar01());

    Property.set(localClass, "yearService", 1938.0F);
    Property.set(localClass, "yearExpired", 1946.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Ki-27.fmd");
    Property.set(localClass, "cockpitClass", CockpitKI_27KO.class);
    Property.set(localClass, "LOSElevation", 0.74185F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 3, 3, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning303s 500", "MGunBrowning303s 500", null, null, null, null });

    weaponsRegister(localClass, "4x25", new String[] { "MGunBrowning303s 500", "MGunBrowning303s 500", "BombGun30kgJ 1", "BombGun30kgJ 1", "BombGun30kgJ 1", "BombGun30kgJ 1" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}