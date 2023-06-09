package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class P_36A3 extends P_36
{
  private float kangle = 0.0F;

  public void update(float paramFloat) { for (int i = 1; i < 12; i++) {
      hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, -10.0F * this.kangle, 0.0F);
    }
    this.kangle = (0.95F * this.kangle + 0.05F * this.FM.EI.engines[0].getControlRadiator());
    super.update(paramFloat);
  }

  static
  {
    Class localClass = P_36A3.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "P-36");
    Property.set(localClass, "meshName", "3DO/Plane/Hawk75A-3(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "meshName_us", "3DO/Plane/Hawk75A-3(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar06());

    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/P-36A-3.fmd");

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50si 200", "MGunBrowning50si 200", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}