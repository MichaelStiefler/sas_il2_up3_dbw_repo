package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class MIG_3AM38 extends MIG_3
{
  private float kangle = 0.0F;

  public void update(float paramFloat) { if (this.FM.getSpeed() > 5.0F) {
      hierMesh().chunkSetAngles("SlatL_D0", 0.0F, cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, 0.9F), 0.0F);
      hierMesh().chunkSetAngles("SlatR_D0", 0.0F, cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, 0.9F), 0.0F);
    }
    hierMesh().chunkSetAngles("WaterFlap_D0", 0.0F, 30.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("OilRad1_D0", 0.0F, -20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("OilRad2_D0", 0.0F, -20.0F * this.kangle, 0.0F);
    this.kangle = (0.95F * this.kangle + 0.05F * this.FM.EI.engines[0].getControlRadiator());
    super.update(paramFloat);
  }

  static
  {
    Class localClass = MIG_3AM38.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "MiG");
    Property.set(localClass, "meshName", "3DO/Plane/MiG-3AM-38(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());

    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/MiG-3AM-38.fmd");
    Property.set(localClass, "cockpitClass", CockpitMIG_3.class);
    Property.set(localClass, "LOSElevation", 0.906F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03" });

    weaponsRegister(localClass, "default", new String[] { "MGunShKASs 750", "MGunShKASs 750", "MGunUBk 310" });

    weaponsRegister(localClass, "none", new String[] { null, null, null });
  }
}