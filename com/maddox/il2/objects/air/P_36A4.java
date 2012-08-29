package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class P_36A4 extends P_36
{
  private float kangle;

  public P_36A4()
  {
    this.kangle = 0.0F;
  }

  public void update(float paramFloat)
  {
    for (int i = 1; i < 9; i++) {
      hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, -10.0F * this.kangle, 0.0F);
    }
    this.kangle = (0.95F * this.kangle + 0.05F * this.FM.EI.engines[0].getControlRadiator());
    super.update(paramFloat);
  }

  static
  {
    Class localClass = P_36A4.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "P-36");
    Property.set(localClass, "meshName", "3DO/Plane/Hawk75A-4(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "meshName_us", "3DO/Plane/Hawk75A-4(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFCSPar02());
    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/P-36A-4.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitP_36.class });

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning50si 200", "MGunBrowning50si 200", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}