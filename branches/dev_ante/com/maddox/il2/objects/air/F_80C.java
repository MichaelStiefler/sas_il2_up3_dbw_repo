package com.maddox.il2.objects.air;

import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class F_80C extends X_80
{
  public static boolean bChangedPit = false;

  private static Aircraft._WeaponSlot[] GenerateDefaultConfig(int paramInt)
  {
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[paramInt];
    try
    {
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50ki", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50ki", 300);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50ki", 300);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50ki", 300);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50ki", 300);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50ki", 300);
    } catch (Exception localException) {
    }
    return arrayOf_WeaponSlot;
  }

  static Class _mthclass$(String paramString)
  {
    try
    {
      return Class.forName(paramString);
    }
    catch (ClassNotFoundException localClassNotFoundException) {
    }
    throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
  }

  static
  {
    Class localClass = F_80C.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "P-80C");
    Property.set(localClass, "meshName", "3DO/Plane/P-80C/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "yearService", 1946.9F);
    Property.set(localClass, "yearExpired", 1955.3F);
    Property.set(localClass, "FlightModel", "FlightModels/P-80C.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitF_80C.class });

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 9, 9, 9, 9, 3, 3, 9, 9, 9, 9, 9, 9, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 38;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      for (int j = 6; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2x500lb";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2x1000lb";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "8xHVAR";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[23] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "8xHVAR+2x250lb";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[23] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "LR-LongRange";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank154gal", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank154gal", 1);
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
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "LR-2x500lb";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank154gal", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank154gal", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "LR-2x1000lb";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank154gal", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank154gal", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "LR-8xHVAR";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank154gal", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank154gal", 1);
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[23] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "LR-8xHVAR+2x250lb";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank154gal", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank154gal", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[23] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "ExtraLongRange";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank154gal", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank154gal", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank75gal2", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank75gal2", 1);
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int k = 0; k < i; k++) {
        arrayOf_WeaponSlot[k] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}