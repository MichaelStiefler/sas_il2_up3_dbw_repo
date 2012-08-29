package com.maddox.il2.objects.air;

import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class SeaFuryMkI extends SeaFury
  implements TypeFighter, TypeStormovik
{
  static Class _mthclass$(String paramString)
  {
    Class localClass;
    try
    {
      localClass = Class.forName(paramString);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
    return localClass;
  }

  static
  {
    Class localClass = SeaFuryMkI.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "SeaFury");
    Property.set(localClass, "meshName", "3DO/Plane/SeaFuryMkI/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "yearService", 1946.0F);
    Property.set(localClass, "yearExpired", 1955.5F);
    Property.set(localClass, "FlightModel", "FlightModels/SeaFuryMkI.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitTemp5.class });

    Property.set(localClass, "LOSElevation", 0.7394F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1, 1, 1, 9, 9, 9, 9, 3, 3, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev05", "_ExternalDev06", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 20;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
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
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      for (int j = 20; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2x45galtanks";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankTempest", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankTempest", 1);
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
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      for (int k = 20; k < i; k++) {
        arrayOf_WeaponSlot[k] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2x90galtanks";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank154gal", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank154gal", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN1", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN2", 1);
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
      for (int m = 20; m < i; m++) {
        arrayOf_WeaponSlot[m] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2x250lbbomb";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN1", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN2", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
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
      for (int n = 20; n < i; n++) {
        arrayOf_WeaponSlot[n] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2x500lbbomb";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN1", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN2", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      for (int i1 = 20; i1 < i; i1++) {
        arrayOf_WeaponSlot[i1] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2x1000lbbomb";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN1", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN2", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
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
      for (int i2 = 20; i2 < i; i2++) {
        arrayOf_WeaponSlot[i2] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "8x60lbrock";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunHispanoMkIk", 200);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN3", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN4", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      for (int i3 = 20; i3 < i; i3++) {
        arrayOf_WeaponSlot[i3] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int i4 = 0; i4 < i; i4++) {
        arrayOf_WeaponSlot[i4] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}