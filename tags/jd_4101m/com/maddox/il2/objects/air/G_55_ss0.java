package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class G_55_ss0 extends G_55xyz
{
  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "G.55");
    Property.set(localClass, "meshName_it", "3DO/Plane/G-55_ss0(it)/hier.him");
    Property.set(localClass, "PaintScheme_it", new PaintSchemeBMPar09());
    Property.set(localClass, "meshName", "3DO/Plane/G-55_ss0(multi)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar02());
    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1948.5F);
    Property.set(localClass, "FlightModel", "FlightModels/G-55_ss0.fmd");
    Property.set(localClass, "LOSElevation", 0.9119F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01" });

    weaponsRegister(localClass, "default", new String[] { "MGunBredaSAFAT127g55 300", "MGunBredaSAFAT127g55 300", "MGunBredaSAFAT127g55k 300", "MGunBredaSAFAT127g55k 300", "MGunMG15120t 200" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null });
  }
}