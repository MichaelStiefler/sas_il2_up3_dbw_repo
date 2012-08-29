package com.maddox.il2.objects.air;

import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class NW_FW_190G2 extends FW_190A5158ATA
{
  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.FM.M.massEmpty -= 81.0F;
  }

  static
  {
    Class localClass = NW_FW_190G2.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "FW190");
    Property.set(localClass, "meshName", "3DO/Plane/Fw-190A-5(Beta)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "yearService", 1942.6F);
    Property.set(localClass, "yearExpired", 1948.0F);
    Property.set(localClass, "FlightModel", "FlightModels/fw-190a5-1_58Ata.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitFW_190A5.class });

    Property.set(localClass, "LOSElevation", 0.764106F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 1, 9, 9, 9, 9, 9, 9, 2, 2, 9, 9, 3, 3, 3, 3, 9, 9, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalRock01", "_ExternalRock02", "_ExternalDev09", "_ExternalDev10", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02", "_ExternalBomb05" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 23;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 150);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 150);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      for (int j = 8; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "u81sc250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 150);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 150);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      for (int k = 8; k < i; k++) {
        arrayOf_WeaponSlot[k] = null;
      }
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "u81sc500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 150);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 150);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      for (int m = 8; m < i; m++) {
        arrayOf_WeaponSlot[m] = null;
      }
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "u82tank";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 150);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 150);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1);
      for (int n = 10; n < i; n++) {
        arrayOf_WeaponSlot[n] = null;
      }
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "u81sc2502tank";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 150);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 150);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1);
      for (int i1 = 10; i1 < i; i1++) {
        arrayOf_WeaponSlot[i1] = null;
      }
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "u81sc5002tank";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 150);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 150);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1);
      for (int i2 = 10; i2 < i; i2++) {
        arrayOf_WeaponSlot[i2] = null;
      }
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "sc5002sc250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 150);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 150);
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
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonETC71", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonETC71", 1);
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);
      for (int i3 = 23; i3 < i; i3++) {
        arrayOf_WeaponSlot[i3] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "sc1000";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 150);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 150);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(3, "BombGunSC1000", 1);
      for (int i4 = 23; i4 < i; i4++) {
        arrayOf_WeaponSlot[i4] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "sc10002tank";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 150);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 150);
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
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(3, "BombGunSC1000", 1);
      for (int i5 = 23; i5 < i; i5++) {
        arrayOf_WeaponSlot[i5] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "sc10002sc250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 150);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 150);
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
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonETC71", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonETC71", 1);
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(3, "BombGunSC1000", 1);
      for (int i6 = 23; i6 < i; i6++) {
        arrayOf_WeaponSlot[i6] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int i7 = 0; i7 < i; i7++) {
        arrayOf_WeaponSlot[i7] = null;
      }
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}