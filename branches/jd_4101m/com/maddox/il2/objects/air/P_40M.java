package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class P_40M extends P_40
{
  private static float f;

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    f = cvt(this.FM.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 5.0F, -17.0F);
    hierMesh().chunkSetAngles("Water2_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Water3_D0", 0.0F, f, 0.0F);
    f = Math.min(f, 0.0F);
    hierMesh().chunkSetAngles("Water1_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Water4_D0", 0.0F, f, 0.0F);
  }

  static
  {
    Class localClass = P_40M.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "P-40");
    Property.set(localClass, "meshName", "3DO/Plane/P-40M(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "meshName_us", "3DO/Plane/P-40M(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar05());
    Property.set(localClass, "meshName_rz", "3DO/Plane/P-40M(RZ)/hier.him");
    Property.set(localClass, "PaintScheme_rz", new PaintSchemeFMPar06());

    Property.set(localClass, "noseart", 1);

    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/P-40M.fmd");
    Property.set(localClass, "cockpitClass", CockpitP_40M.class);
    Property.set(localClass, "LOSElevation", 1.0692F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 3, 9 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalBomb01" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", null, null });

    weaponsRegister(localClass, "500lb", new String[] { "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "BombGun500lbs", null });

    weaponsRegister(localClass, "1000lb", new String[] { "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "BombGun1000lbs", null });

    weaponsRegister(localClass, "droptank", new String[] { "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", null, "FuelTankGun_Tank75gal" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null });
  }
}