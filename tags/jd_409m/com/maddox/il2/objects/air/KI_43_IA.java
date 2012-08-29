package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class KI_43_IA extends KI_43
{
  private float flapps = 0.0F;

  public void update(float paramFloat)
  {
    super.update(paramFloat);

    float f = this.FM.EI.engines[0].getControlRadiator();
    if (Math.abs(this.flapps - f) > 0.01F) {
      this.flapps = f;
      for (int i = 1; i < 18; i++) {
        hierMesh().chunkSetAngles("Cowflap" + i + "_D0", 0.0F, -22.0F * f, 0.0F);
      }
      for (int j = 2; j < 10; j++) {
        hierMesh().chunkSetAngles("Cowflap" + j + "_D0", 0.0F, 22.0F * f, 0.0F);
      }
      for (int k = 11; k < 12; k++)
        hierMesh().chunkSetAngles("Cowflap" + k + "_D0", 0.0F, 22.0F * f, 0.0F);
    }
  }

  static
  {
    Class localClass = KI_43_IA.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Ki-43");

    Property.set(localClass, "meshName", "3DO/Plane/Ki-43-Ia(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "meshName_ja", "3DO/Plane/Ki-43-Ia(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeFCSPar05());

    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Ki-43-Ia.fmd");
    Property.set(localClass, "cockpitClass", CockpitKI_43.class);
    Property.set(localClass, "LOSElevation", 0.5265F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning303s_jap 500", "MGunBrowning303s_jap 500" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null });
  }
}