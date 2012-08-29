package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class I_16TYPE24SAFONOV extends I_16
  implements TypeAcePlane
{
  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.FM.Skill = 3;
  }

  static
  {
    Class localClass = I_16TYPE24SAFONOV.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "I-16");
    Property.set(localClass, "meshName", "3DO/Plane/I-16type24(ofSafonov)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeSpecial());

    Property.set(localClass, "FlightModel", "FlightModels/I-16type24(ofSafonov).fmd");

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1 });
    weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02" });
    weaponsRegister(localClass, "default", new String[] { "MGunShKASsi 240", "MGunShKASsi 240", "MGunShVAKk 120", "MGunShVAKk 120" });
    weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}