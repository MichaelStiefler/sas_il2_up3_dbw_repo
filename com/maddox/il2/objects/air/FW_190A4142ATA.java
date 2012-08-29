package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.HUD;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class FW_190A4142ATA extends FW_190
  implements TypeFighter, TypeBNZFighter
{
  public static boolean bChangedPit = false;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if ((getGunByHookName("_MGUN01") instanceof GunEmpty))
    {
      hierMesh().chunkVisible("7mmC_D0", false);
      hierMesh().chunkVisible("7mmCowl_D0", true);
    }
    if ((getGunByHookName("_CANNON03") instanceof GunEmpty))
      hierMesh().chunkVisible("20mmL_D0", false);
    if ((getGunByHookName("_CANNON04") instanceof GunEmpty))
      hierMesh().chunkVisible("20mmR_D0", false);
    if (!(getGunByHookName("_ExternalDev05") instanceof GunEmpty))
    {
      hierMesh().chunkVisible("Flap01_D0", false);
      hierMesh().chunkVisible("Flap01Holed_D0", true);
    }
    if (!(getGunByHookName("_ExternalDev06") instanceof GunEmpty))
    {
      hierMesh().chunkVisible("Flap04_D0", false);
      hierMesh().chunkVisible("Flap04Holed_D0", true);
    }
  }

  public void update(float paramFloat)
  {
    afterburnerhud();
    super.update(paramFloat);
  }

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      bChangedPit = true;
  }

  protected void afterburnerhud()
  {
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getControlAfterburner()))
      HUD.logRightBottom("Start- und Notleistung ENABLED!");
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      bChangedPit = true;
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

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat)
  {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.getGear() < 0.98F)
    {
      return;
    }

    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
  }

  static
  {
    Class localClass = FW_190A4142ATA.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "FW190");
    Property.set(localClass, "meshName", "3do/plane/Fw-190A-4(Beta)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "yearService", 1942.1F);
    Property.set(localClass, "yearExpired", 1948.0F);
    Property.set(localClass, "cockpitClass", new Class[] { CockpitFW_190A4.class });

    Property.set(localClass, "FlightModel", "FlightModels/fw-190a-4_1.42.fmd");
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 1, 9, 9, 9, 9, 9, 9, 2, 2, 9, 9, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalRock01", "_ExternalRock02", "_ExternalDev01", "_ExternalDev02", "_ExternalBomb01" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 17;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunMGFFkih", 90);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunMGFFkih", 90);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      for (int j = 17; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "u1";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      for (int k = 17; k < i; k++) {
        arrayOf_WeaponSlot[k] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "u11sc250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      for (int m = 17; m < i; m++) {
        arrayOf_WeaponSlot[m] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "u11ab250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(3, "BombGunAB250", 1);
      for (int n = 17; n < i; n++) {
        arrayOf_WeaponSlot[n] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "u31sc250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      for (int i1 = 17; i1 < i; i1++) {
        arrayOf_WeaponSlot[i1] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "u31ab250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(3, "BombGunAB250", 1);
      for (int i2 = 17; i2 < i; i2++) {
        arrayOf_WeaponSlot[i2] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "u31sc500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);
      for (int i3 = 17; i3 < i; i3++) {
        arrayOf_WeaponSlot[i3] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "u31ab500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(3, "BombGunAB500", 1);
      for (int i4 = 17; i4 < i; i4++) {
        arrayOf_WeaponSlot[i4] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "u31tank";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1);
      arrayOf_WeaponSlot[16] = null;
      for (int i5 = 17; i5 < i; i5++) {
        arrayOf_WeaponSlot[i5] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "u82tank";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG17si", 950);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17si", 950);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1);
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      for (int i6 = 17; i6 < i; i6++) {
        arrayOf_WeaponSlot[i6] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "u81sc2502tank";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1);
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      for (int i7 = 17; i7 < i; i7++) {
        arrayOf_WeaponSlot[i7] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "u81sc5002tank";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1);
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);
      for (int i8 = 17; i8 < i; i8++) {
        arrayOf_WeaponSlot[i8] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "r1tank300";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG17si", 100);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17si", 100);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunMGFFkih", 90);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunMGFFkih", 90);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1);
      arrayOf_WeaponSlot[16] = null;
      for (int i9 = 17; i9 < i; i9++) {
        arrayOf_WeaponSlot[i9] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "r1sc500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG17si", 100);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17si", 100);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunMGFFkih", 90);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunMGFFkih", 90);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);
      for (int i10 = 17; i10 < i; i10++) {
        arrayOf_WeaponSlot[i10] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "r6wfrgr21";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG17si", 100);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17si", 100);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunMGFFkih", 90);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunMGFFkih", 90);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      for (int i11 = 17; i11 < i; i11++) {
        arrayOf_WeaponSlot[i11] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "None";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int i12 = 0; i12 < i; i12++) {
        arrayOf_WeaponSlot[i12] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}