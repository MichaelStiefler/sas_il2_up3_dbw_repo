package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class YAK_9UT extends YAK
  implements TypeBNZFighter
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

  public void update(float paramFloat)
  {
    hierMesh().chunkSetAngles("OilRad_D0", 0.0F, this.FM.EI.engines[0].getControlRadiator() * 15.0F, 0.0F);
    hierMesh().chunkSetAngles("Water_luk", 0.0F, this.FM.EI.engines[0].getControlRadiator() * 12.0F, 0.0F);
    super.update(paramFloat);
  }

  static
  {
    Class localClass = YAK_9UT.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Yak");
    Property.set(localClass, "meshName", "3DO/Plane/Yak-9UT(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());

    Property.set(localClass, "yearService", 1945.0F);
    Property.set(localClass, "yearExpired", 1952.8F);

    Property.set(localClass, "FlightModel", "FlightModels/Yak-9UT.fmd");
    Property.set(localClass, "cockpitClass", CockpitYAK_9U.class);
    Property.set(localClass, "LOSElevation", 0.6432F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01" });

    weaponsRegister(localClass, "default", new String[] { "MGunShVAKsi 120", "MGunShVAKsi 120", "MGunSh37ki 30" });

    weaponsRegister(localClass, "none", new String[] { null, null, null });
  }
}