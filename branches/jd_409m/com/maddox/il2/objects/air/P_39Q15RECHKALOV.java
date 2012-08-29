package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class P_39Q15RECHKALOV extends P_39
  implements TypeAcePlane
{
  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.FM.Skill = 3;
  }

  static
  {
    Class localClass = P_39Q15RECHKALOV.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "P39");
    Property.set(localClass, "meshName", "3do/plane/P-39Q-15(ofRechkalov)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeSpecial());

    Property.set(localClass, "FlightModel", "FlightModels/P-39Q-15(ofRechkalov).fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_ExternalBomb01" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning50si 320", "MGunBrowning50si 320", "MGunM4ki 60", null });

    Aircraft.weaponsRegister(localClass, "1xFAB250", new String[] { "MGunBrowning50si 320", "MGunBrowning50si 320", "MGunM4ki 60", "BombGunFAB250 1" });
    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}