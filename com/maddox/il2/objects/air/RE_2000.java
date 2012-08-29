package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class RE_2000 extends RE_2000xyz
{
  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "RE.2000");
    Property.set(localClass, "meshName_hu", "3DO/Plane/RE-2000(hu)/hier.him");
    Property.set(localClass, "PaintScheme_hu", new PaintSchemeFMPar02());
    Property.set(localClass, "meshName_it", "3DO/Plane/RE-2000(it)/hier.him");
    Property.set(localClass, "PaintScheme_it", new PaintSchemeBMPar09());
    Property.set(localClass, "meshName", "3DO/Plane/RE-2000(multi)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar02());
    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1948.5F);
    Property.set(localClass, "cockpitClass", new Class[] { CockpitRE2000.class });

    Property.set(localClass, "FlightModel", "FlightModels/RE-2000.fmd");
    Property.set(localClass, "LOSElevation", 0.9119F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_BOMB100KG01", "_BOMB100KG02", "_BOMBCASSETTE01", "_BOMBCASSETTE02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBredaSAFAT127re 300", "MGunBredaSAFAT127re 300", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x100_Kg_Bombs", new String[] { "MGunBredaSAFAT127re 300", "MGunBredaSAFAT127re 300", "BombGunIT_100_M 1", "BombGunIT_100_M 1", null, null });

    Aircraft.weaponsRegister(localClass, "4xCassette", new String[] { "MGunBredaSAFAT127re 300", "MGunBredaSAFAT127re 300", null, null, "BombGunSpezzoniera 44", "BombGunSpezzoniera 44" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}