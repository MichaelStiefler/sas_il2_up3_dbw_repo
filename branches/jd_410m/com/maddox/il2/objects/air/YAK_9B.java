package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class YAK_9B extends YAK
  implements TypeBNZFighter, TypeStormovik
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = Math.max(-paramFloat * 1500.0F, -80.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 80.0F * paramFloat, 0.0F);

    f = Math.max(-paramFloat * 1500.0F, -60.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, f, 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 82.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 82.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -85.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -85.0F * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); }

  public void moveSteering(float paramFloat)
  {
  }

  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay1_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
  }

  public void update(float paramFloat)
  {
    hierMesh().chunkSetAngles("Water_luk", 0.0F, 12.0F * this.FM.EI.engines[0].getControlRadiator(), 0.0F);
    super.update(paramFloat);

    int i = 0;
    if (this.FM.CT.Weapons[3] != null) {
      for (int j = 0; j < this.FM.CT.Weapons[3].length; j++) {
        if ((this.FM.CT.Weapons[3][j] != null) && (this.FM.CT.Weapons[3][j].haveBullets())) {
          i++;
        }
      }
    }
    float f = 0.14F;
    switch (i) {
    case 0:
    default:
      this.FM.setGCenter(0.1F);
      this.FM.setGC_Gear_Shift(0.0F);
      break;
    case 1:
      this.FM.setGCenter(0.1F - f);
      this.FM.setGC_Gear_Shift(0.0F + f);
    case 2:
      this.FM.setGCenter(0.1F - 2.0F * f);
      this.FM.setGC_Gear_Shift(0.0F + 2.0F * f);
      break;
    case 3:
      this.FM.setGCenter(0.1F - 3.0F * f);
      this.FM.setGC_Gear_Shift(0.0F + 3.0F * f);
      break;
    case 4:
      this.FM.setGCenter(0.1F - 4.0F * f);
      this.FM.setGC_Gear_Shift(0.0F + 4.0F * f);
    }
  }

  static
  {
    Class localClass = YAK_9B.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Yak");
    Property.set(localClass, "meshName", "3DO/Plane/Yak-9B(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1952.8F);

    Property.set(localClass, "FlightModel", "FlightModels/Yak-9B.fmd");
    Property.set(localClass, "cockpitClass", CockpitYAK_9D.class);
    Property.set(localClass, "LOSElevation", 0.6432F);

    weaponTriggersRegister(localClass, new int[] { 0, 1, 3, 3, 3, 3, 3, 3, 3, 3, 9, 9, 9, 9 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_CANNON01", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_BombSpawn08", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04" });

    weaponsRegister(localClass, "default", new String[] { "MGunUBsi 200", "MGunShVAKki 120", null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2fab100", new String[] { "MGunUBsi 200", "MGunShVAKki 120", null, null, "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "3fab100", new String[] { "MGunUBsi 200", "MGunShVAKki 120", "BombGunFAB100 1", "BombGunNull 1", "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "4fab100", new String[] { "MGunUBsi 200", "MGunShVAKki 120", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2ptab", new String[] { "MGunUBsi 200", "MGunShVAKki 120", null, null, null, null, null, null, "BombGunPTAB25", "BombGunPTAB25", "PylonKMB", "PylonKMB", "PylonKMB", "PylonKMB" });

    weaponsRegister(localClass, "4ptab", new String[] { "MGunUBsi 200", "MGunShVAKki 120", null, null, null, null, "BombGunPTAB25", "BombGunPTAB25", "BombGunPTAB25", "BombGunPTAB25", "PylonKMB", "PylonKMB", "PylonKMB", "PylonKMB" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}