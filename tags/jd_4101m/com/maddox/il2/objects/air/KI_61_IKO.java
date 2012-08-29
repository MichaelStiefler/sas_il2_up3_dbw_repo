package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.Property;

public class KI_61_IKO extends KI_61
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, cvt(paramFloat, 0.01F, 0.9F, 0.0F, 72.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, cvt(paramFloat, 0.01F, 0.21F, 0.0F, 57.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, cvt(paramFloat, 0.01F, 0.21F, 0.0F, -57.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, cvt(paramFloat, 0.1F, 0.82F, 0.0F, -82.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, cvt(paramFloat, 0.1F, 0.82F, 0.0F, -90.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, cvt(paramFloat, 0.1F, 0.16F, 0.0F, 86.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, cvt(paramFloat, 0.34F, 0.91F, 0.0F, -82.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, cvt(paramFloat, 0.34F, 0.91F, 0.0F, 90.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, cvt(paramFloat, 0.34F, 0.4F, 0.0F, -86.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC5_D0", 0.0F, -paramFloat, 0.0F);
  }

  static
  {
    Class localClass = KI_61_IKO.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Ki-61");
    Property.set(localClass, "meshName", "3DO/Plane/Ki-61-I(Ko)(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar03());
    Property.set(localClass, "meshName_ja", "3DO/Plane/Ki-61-I(Ko)(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeBCSPar01());

    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1946.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Ki-61-IKo.fmd");
    Property.set(localClass, "cockpitClass", CockpitKI_61.class);
    Property.set(localClass, "LOSElevation", 0.81055F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 3, 3, 9, 9 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02" });

    weaponsRegister(localClass, "default", new String[] { "MGunHo103si 250", "MGunHo103si 250", "MGunBrowning303k_jap 500", "MGunBrowning303k_jap 500", null, null, null, null });

    weaponsRegister(localClass, "1x250", new String[] { "MGunHo103si 250", "MGunHo103si 250", "MGunBrowning303k_jap 500", "MGunBrowning303k_jap 500", null, "BombGun250kgJ 1", null, null });

    weaponsRegister(localClass, "2x250", new String[] { "MGunHo103si 250", "MGunHo103si 250", "MGunBrowning303k_jap 500", "MGunBrowning303k_jap 500", "BombGun250kgJ 1", "BombGun250kgJ 1", null, null });

    weaponsRegister(localClass, "2x150dt", new String[] { "MGunHo103si 250", "MGunHo103si 250", "MGunBrowning303k_jap 500", "MGunBrowning303k_jap 500", null, null, "FuelTankGun_TankKi61Underwing", "FuelTankGun_TankKi61Underwing" });

    weaponsRegister(localClass, "1x150dt", new String[] { "MGunHo103si 250", "MGunHo103si 250", "MGunBrowning303k_jap 500", "MGunBrowning303k_jap 500", null, null, null, "FuelTankGun_TankKi61Underwing" });

    weaponsRegister(localClass, "1x150dt+1x250kg", new String[] { "MGunHo103si 250", "MGunHo103si 250", "MGunBrowning303k_jap 500", "MGunBrowning303k_jap 500", null, "BombGun250kgJ 1", "FuelTankGun_TankKi61Underwing", null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null });
  }
}