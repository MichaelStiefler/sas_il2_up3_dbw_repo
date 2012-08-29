package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class LAGG_3SERIES35 extends LAGG_3
  implements TypeTNBFighter
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
    float f = Math.max(-paramFloat * 1400.0F, -80.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, -f, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat);
  }

  static
  {
    Class localClass = LAGG_3SERIES35.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "LaGG");
    Property.set(localClass, "meshName", "3DO/Plane/LaGG-3series35/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar03());

    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1944.5F);

    Property.set(localClass, "FlightModel", "FlightModels/LaGG-3series35.fmd");
    Property.set(localClass, "cockpitClass", CockpitLAGG_3SERIES4.class);
    Property.set(localClass, "LOSElevation", 0.69445F);

    weaponTriggersRegister(localClass, new int[] { 0, 1, 3, 3, 9, 2, 9, 2, 9, 2, 9, 2, 9, 2, 9, 2, 9, 2, 9, 2, 9, 9 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_CANNON01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalRock01", "_ExternalDev02", "_ExternalRock02", "_ExternalDev03", "_ExternalRock03", "_ExternalDev04", "_ExternalRock04", "_ExternalDev05", "_ExternalRock05", "_ExternalDev06", "_ExternalRock06", "_ExternalDev07", "_ExternalRock07", "_ExternalDev08", "_ExternalRock08", "_ExternalBomb01", "_ExternalBomb02" });

    weaponsRegister(localClass, "default", new String[] { "MGunUBs 200", "MGunShVAKk 160", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "kinderwud", new String[] { "MGunUBs 200", "MGunVYak 90", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xFAB50", new String[] { "MGunUBs 200", "MGunShVAKk 160", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xDROPTANK", new String[] { "MGunUBs 200", "MGunShVAKk 160", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "FuelTankGun_Tank80", "FuelTankGun_Tank80" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}