package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class YAK_9TALBERT extends YAK_9TX
  implements TypeAcePlane
{
  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.FM.Skill = 3;
  }

  static
  {
    Class localClass = YAK_9TALBERT.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Yak");
    Property.set(localClass, "meshName", "3DO/Plane/Yak-9T(ofAlbert)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeSpecial());

    Property.set(localClass, "FlightModel", "FlightModels/Yak-9T.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 1 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_CANNON01" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunUBsi 200", "MGunNS37ki 30" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null });
  }
}