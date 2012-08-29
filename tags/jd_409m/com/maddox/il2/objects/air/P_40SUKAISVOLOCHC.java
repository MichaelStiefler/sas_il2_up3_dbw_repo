package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.Property;

public class P_40SUKAISVOLOCHC extends P_40SUKAISVOLOCH
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.04F, 0.5F, 0.0F, -70.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.02F, 0.09F, 0.0F, -60.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.02F, 0.09F, 0.0F, -60.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.79F, 0.0F, -90.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL21_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.79F, 0.0F, 94.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.39F, 0.0F, -53.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.11F, 0.0F, -100.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.79F, 0.0F, 100.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.21F, 0.99F, 0.0F, -90.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR21_D0", 0.0F, Aircraft.cvt(paramFloat, 0.21F, 0.99F, 0.0F, -94.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, Aircraft.cvt(paramFloat, 0.21F, 0.59F, 0.0F, -53.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.21F, 0.31F, 0.0F, -100.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.21F, 0.99F, 0.0F, 100.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, paramFloat, 0.0F);
  }

  static
  {
    Class localClass = P_40SUKAISVOLOCHC.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "P-40");
    Property.set(localClass, "meshName", "3DO/Plane/P-40C(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar02());
    Property.set(localClass, "meshName_us", "3DO/Plane/P-40C(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFCSPar02());

    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/P-40C.fmd");
    Property.set(localClass, "cockpitClass", CockpitP_40C.class);
    Property.set(localClass, "LOSElevation", 1.0728F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 9, 3, 9 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning50si 250", "MGunBrowning50si 250", "MGunBrowning303k 300", "MGunBrowning303k 240", "MGunBrowning303k 300", "MGunBrowning303k 240", null, null, null });

    Aircraft.weaponsRegister(localClass, "1x75dt", new String[] { "MGunBrowning50si 250", "MGunBrowning50si 250", "MGunBrowning303k 300", "MGunBrowning303k 240", "MGunBrowning303k 300", "MGunBrowning303k 240", "PylonP39PLN1", null, "FuelTankGun_Tank75gal" });

    Aircraft.weaponsRegister(localClass, "1x100", new String[] { "MGunBrowning50si 250", "MGunBrowning50si 250", "MGunBrowning303k 300", "MGunBrowning303k 240", "MGunBrowning303k 300", "MGunBrowning303k 240", "PylonP39PLN1", "BombGunFAB50 1", null });

    Aircraft.weaponsRegister(localClass, "1x250", new String[] { "MGunBrowning50si 250", "MGunBrowning50si 250", "MGunBrowning303k 300", "MGunBrowning303k 240", "MGunBrowning303k 300", "MGunBrowning303k 240", "PylonP39PLN1", "BombGun250lbs 1", null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null });
  }
}