package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class G_55_Late extends G_55xyz
{
  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "G.55");

    Property.set(localClass, "meshName_it", "3DO/Plane/G-55(it)/hier.him");
    Property.set(localClass, "PaintScheme_it", new PaintSchemeBMPar09());

    Property.set(localClass, "meshName", "3DO/Plane/G-55(multi)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar02());

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/G-55-late.fmd");

    Property.set(localClass, "LOSElevation", 0.9119F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 1 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03" });

    weaponsRegister(localClass, "default", new String[] { "MGunBredaSAFAT127g55 300", "MGunBredaSAFAT127g55 300", "MGunMG15120t 200", "MGunMG15120kh 250", "MGunMG15120kh 250" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null });
  }
}