package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class P_39NPOKRYSHKIN extends P_39
  implements TypeAcePlane
{
  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.FM.Skill = 3;
  }

  static
  {
    Class localClass = P_39NPOKRYSHKIN.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "P39");
    Property.set(localClass, "meshName", "3do/plane/P-39N(ofPokryshkin)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeSpecial());

    Property.set(localClass, "FlightModel", "FlightModels/P-39N(ofPokryshkin).fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1, 1, 1, 1, 1, 1, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_CANNON01", "_ExternalBomb01" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning50si 320", "MGunBrowning50si 320", "MGunBrowning303k 1300", "MGunBrowning303k 1300", "MGunBrowning303k 1300", "MGunBrowning303k 1300", "MGunM4ki 60", null });

    Aircraft.weaponsRegister(localClass, "1xFAB250", new String[] { "MGunBrowning50si 320", "MGunBrowning50si 320", "MGunBrowning303k 1300", "MGunBrowning303k 1300", "MGunBrowning303k 1300", "MGunBrowning303k 1300", "MGunM4ki 60", "BombGunFAB250 1" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null });
  }
}