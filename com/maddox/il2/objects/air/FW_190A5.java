package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Property;

public class FW_190A5 extends FW_190
{
  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if ((getGunByHookName("_MGUN01") instanceof GunEmpty)) {
      hierMesh().chunkVisible("7mmC_D0", false);
      hierMesh().chunkVisible("7mmCowl_D0", true);
    }
    if ((getGunByHookName("_CANNON03") instanceof GunEmpty)) {
      hierMesh().chunkVisible("20mmL_D0", false);
    }
    if ((getGunByHookName("_CANNON04") instanceof GunEmpty)) {
      hierMesh().chunkVisible("20mmR_D0", false);
    }
    if (!(getGunByHookName("_ExternalDev05") instanceof GunEmpty)) {
      hierMesh().chunkVisible("Flap01_D0", false);
      hierMesh().chunkVisible("Flap01Holed_D0", true);
    }
    if (!(getGunByHookName("_ExternalDev06") instanceof GunEmpty)) {
      hierMesh().chunkVisible("Flap04_D0", false);
      hierMesh().chunkVisible("Flap04Holed_D0", true);
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 77.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 77.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 157.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 157.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC99_D0", 20.0F * paramFloat, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);

    float f = Math.max(-paramFloat * 1500.0F, -94.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, -f, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    if (this.FM.CT.getGear() < 0.98F) return;
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
  }

  static
  {
    Class localClass = FW_190A5.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "FW190");
    Property.set(localClass, "meshName", "3DO/Plane/Fw-190A-5(Beta)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());

    Property.set(localClass, "yearService", 1943.1F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Fw-190A-5.fmd");
    Property.set(localClass, "cockpitClass", CockpitFW_190A5.class);
    Property.set(localClass, "LOSElevation", 0.764106F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 1, 9, 9, 9, 9, 9, 9, 2, 2, 9, 9, 3, 3, 3, 3, 9, 9, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalRock01", "_ExternalRock02", "_ExternalDev09", "_ExternalDev10", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02", "_ExternalBomb05" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", "MGunMGFFkih 60", "MGunMGFFkih 60", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "u22tank", new String[] { null, null, "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D", null, null, null, null, null, null, null, null, null, null, "PylonETC501FW190", null, null });

    weaponsRegister(localClass, "u21sc2502tank", new String[] { null, null, "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D", null, null, null, null, null, null, null, null, null, null, "PylonETC501FW190", null, "BombGunSC250" });

    weaponsRegister(localClass, "u3", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonETC501FW190", null, null });

    weaponsRegister(localClass, "u31sc250", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, null, null, null, null, null, null, null, null, "PylonETC71", "PylonETC71", null, null, null, null, "PylonETC501FW190", null, "BombGunSC250" });

    weaponsRegister(localClass, "u31ab250", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, null, null, null, null, null, null, null, null, "PylonETC71", "PylonETC71", null, null, null, null, "PylonETC501FW190", null, "BombGunAB250" });

    weaponsRegister(localClass, "u31sc500", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, null, null, null, null, null, null, null, null, "PylonETC71", "PylonETC71", null, null, null, null, "PylonETC501FW190", null, "BombGunSC500" });

    weaponsRegister(localClass, "u31ab500", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, null, null, null, null, null, null, null, null, "PylonETC71", "PylonETC71", null, null, null, null, "PylonETC501FW190", null, "BombGunAB500" });

    weaponsRegister(localClass, "u31sc2504sc50", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, null, null, null, null, null, null, null, null, "PylonETC71", "PylonETC71", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "PylonETC501FW190", null, "BombGunSC250" });

    weaponsRegister(localClass, "u31ab2504sc50", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, null, null, null, null, null, null, null, null, "PylonETC71", "PylonETC71", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "PylonETC501FW190", null, "BombGunAB250" });

    weaponsRegister(localClass, "u82tank", new String[] { null, null, "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D", null, null, null, null, null, null, null, null, null, null, "PylonETC501FW190", null, null });

    weaponsRegister(localClass, "u81sc5002tank", new String[] { null, null, "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D", null, null, null, null, null, null, null, null, null, null, "PylonETC501FW190", null, "BombGunSC500" });

    weaponsRegister(localClass, "u81ab5002tank", new String[] { null, null, "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D", null, null, null, null, null, null, null, null, null, null, "PylonETC501FW190", null, "BombGunAB500" });

    weaponsRegister(localClass, "u171sc5004sc50", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, null, null, null, null, null, null, null, null, "PylonETC71", "PylonETC71", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "PylonETC501FW190", null, "BombGunSC500" });

    weaponsRegister(localClass, "r11tank", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", "MGunMGFFkih 60", "MGunMGFFkih 60", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonETC501FW190", "FuelTankGun_Type_D", null });

    weaponsRegister(localClass, "r11sc500", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", "MGunMGFFkih 60", "MGunMGFFkih 60", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonETC501FW190", null, "BombGunSC500" });

    weaponsRegister(localClass, "r11ab500", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", "MGunMGFFkih 60", "MGunMGFFkih 60", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonETC501FW190", null, "BombGunAB500" });

    weaponsRegister(localClass, "r6wfrgr21", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", "MGunMGFFkih 60", "MGunMGFFkih 60", null, null, null, null, "PylonRO_WfrGr21", "PylonRO_WfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}