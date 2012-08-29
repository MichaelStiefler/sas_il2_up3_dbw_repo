package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class KI_43_II extends KI_43
{
  private float flapps = 0.0F;

  public void update(float paramFloat)
  {
    super.update(paramFloat);

    float f = this.FM.EI.engines[0].getControlRadiator();
    if (Math.abs(this.flapps - f) > 0.01F) {
      this.flapps = f;
      for (int i = 1; i < 15; i++)
        hierMesh().chunkSetAngles("Cowflap" + i + "_D0", 26.0F * f, 0.0F, 0.0F);
    }
  }

  static
  {
    Class localClass = KI_43_II.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Ki-43");
    Property.set(localClass, "meshName", "3DO/Plane/Ki-43-II(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "meshName_ja", "3DO/Plane/Ki-43-II(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeFCSPar05());

    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Ki-43-II.fmd");
    Property.set(localClass, "cockpitClass", CockpitKI_43_II.class);
    Property.set(localClass, "LOSElevation", 0.4252F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 9, 9, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_ExternalDev01", "_ExternalDev02", "_ExternalBomb01", "_ExternalBomb02" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50s_jap 250", "MGunBrowning50s_jap 250", null, null, null, null });

    weaponsRegister(localClass, "2x250", new String[] { "MGunBrowning50s_jap 250", "MGunBrowning50s_jap 250", "PylonKI43PLN1", "PylonKI43PLN1", "BombGun250kgJ", "BombGun250kgJ" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}