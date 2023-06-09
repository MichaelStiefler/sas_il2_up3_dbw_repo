package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class KI_46_OTSU extends KI_46
  implements TypeFighter
{
  static
  {
    Class localClass = KI_46_OTSU.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Ki-46");
    Property.set(localClass, "meshName", "3DO/Plane/Ki-46(Otsu)(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar02());

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Ki-46-IIIKai.fmd");

    weaponTriggersRegister(localClass, new int[] { 1, 1 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02" });

    weaponsRegister(localClass, "default", new String[] { "MGunHo5ki 200", "MGunHo5ki 200" });

    weaponsRegister(localClass, "none", new String[] { null, null });
  }
}