package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Property;

public class FW_190A4 extends FW_190
{
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

  static
  {
    Class localClass = FW_190A4.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "FW190");
    Property.set(localClass, "meshName", "3DO/Plane/Fw-190A-4(Beta)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());

    Property.set(localClass, "yearService", 1942.6F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Fw-190A-4.fmd");
    Property.set(localClass, "cockpitClass", CockpitFW_190A4.class);
    Property.set(localClass, "LOSElevation", 0.764106F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 1, 9, 9, 9, 9, 9, 9, 2, 2, 9, 9, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalRock01", "_ExternalRock02", "_ExternalDev01", "_ExternalDev02", "_ExternalBomb01" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "MGunMGFFkih 60", "MGunMGFFkih 60", null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "u1", new String[] { null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, null, null, null, null, "PylonETC501FW190", null, null });

    Aircraft.weaponsRegister(localClass, "u11sc250", new String[] { null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, null, null, null, null, "PylonETC501FW190", null, "BombGunSC250" });

    Aircraft.weaponsRegister(localClass, "u11ab250", new String[] { null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, null, null, null, null, "PylonETC501FW190", null, "BombGunAB250" });

    Aircraft.weaponsRegister(localClass, "u31sc250", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, null, null, null, null, "PylonETC501FW190", null, "BombGunSC250" });

    Aircraft.weaponsRegister(localClass, "u31ab250", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, null, null, null, null, "PylonETC501FW190", null, "BombGunAB250" });

    Aircraft.weaponsRegister(localClass, "u31sc500", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, null, null, null, null, "PylonETC501FW190", null, "BombGunSC500" });

    Aircraft.weaponsRegister(localClass, "u31ab500", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, null, null, null, null, "PylonETC501FW190", null, "BombGunAB500" });

    Aircraft.weaponsRegister(localClass, "u31tank", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, null, null, null, null, "PylonETC501FW190", "FuelTankGun_Type_D", null });

    Aircraft.weaponsRegister(localClass, "u82tank", new String[] { "MGunMG17si 950", "MGunMG17si 950", "MGunMG15120MGs 200", "MGunMG15120MGs 200", null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D", null, null, null, null, "PylonETC501FW190", null, null });

    Aircraft.weaponsRegister(localClass, "u81sc2502tank", new String[] { null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D", null, null, null, null, "PylonETC501FW190", null, "BombGunSC250" });

    Aircraft.weaponsRegister(localClass, "u81sc5002tank", new String[] { null, null, "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D", null, null, null, null, "PylonETC501FW190", null, "BombGunSC500" });

    Aircraft.weaponsRegister(localClass, "r1tank300", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "MGunMGFFkih 60", "MGunMGFFkih 60", null, null, null, null, null, null, null, null, "PylonETC501FW190", "FuelTankGun_Type_D", null });

    Aircraft.weaponsRegister(localClass, "r1sc500", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "MGunMGFFkih 60", "MGunMGFFkih 60", null, null, null, null, null, null, null, null, "PylonETC501FW190", null, "BombGunSC500" });

    Aircraft.weaponsRegister(localClass, "r6wfrgr21", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "MGunMGFFkih 60", "MGunMGFFkih 60", null, null, null, null, "PylonRO_WfrGr21", "PylonRO_WfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}