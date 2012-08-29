package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class LAGG_3IT extends LAGG_3
{
  public void update(float paramFloat)
  {
    if (this.FM.getSpeed() > 5.0F) {
      hierMesh().chunkSetAngles("SlatL_D0", 0.0F, cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, 1.2F), 0.0F);
      hierMesh().chunkSetAngles("SlatR_D0", 0.0F, cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, 1.2F), 0.0F);
    }
    super.update(paramFloat);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 80.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -80.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -100.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 100.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", -75.0F * paramFloat, 0.0F, 0.0F);
    float f = Math.max(-paramFloat * 1200.0F, -80.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, -f, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat);
  }

  static
  {
    Class localClass = LAGG_3IT.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "LaGG");
    Property.set(localClass, "meshName", "3do/plane/LaGG-3IT/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());

    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/LaGG-3IT.fmd");
    Property.set(localClass, "cockpitClass", CockpitLAGG_3SERIES66.class);
    Property.set(localClass, "LOSElevation", 0.69445F);

    weaponTriggersRegister(localClass, new int[] { 0, 1 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_CANNON01" });

    weaponsRegister(localClass, "default", new String[] { "MGunUBs 220", "MGunNS37ki 22" });

    weaponsRegister(localClass, "none", new String[] { null, null });
  }
}