package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class MOSQUITO6CC extends MOSQUITO
  implements TypeFighter, TypeStormovik
{
  private static Aircraft._WeaponSlot[] GenerateDefaultConfig(int paramInt)
  {
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[paramInt];
    try
    {
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303kipzl", 500);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303kipzl", 500);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303kipzl", 500);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303kipzl", 500);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 150);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 150);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 150);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 150);
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
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Mosquito");
    Property.set(localClass, "meshName", "3DO/Plane/Mosquito_FB_MkVICC(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());
    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1946.5F);
    Property.set(localClass, "FlightModel", "FlightModels/Mosquito-FBMkVI.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitMosquito6.class });

    Property.set(localClass, "LOSElevation", 0.6731F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 1, 1, 1, 3, 3, 3, 3, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalBomb01", "_ExternalBomb02", "_BombSpawn01", "_BombSpawn02", "_ExternalDev05", "_ExternalDev06", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 22;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      for (int j = 8; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2x250";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
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
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "4x250";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
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
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2x500";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
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
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "4x500";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
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
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "8x60rock";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN3", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN4", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "8x60rock+2x250";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN3", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN4", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "8x60rock+2x500";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN3", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN4", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
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