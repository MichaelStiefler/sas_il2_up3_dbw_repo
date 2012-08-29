package com.maddox.il2.objects.air;

import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class BEAU10 extends BEAU
  implements TypeFighter, TypeStormovik
{
  static Class _mthclass$(String paramString)
  {
    Class localClass;
    try
    {
      localClass = Class.forName(paramString);
    } catch (ClassNotFoundException localClassNotFoundException) {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }

    return localClass;
  }

  static
  {
    Class localClass = BEAU10.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Beaufighter");
    Property.set(localClass, "meshName", "3DO/Plane/BeaufighterMk10(Multi1)/hier.him");

    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "meshName_gb", "3DO/Plane/BeaufighterMk10(GB)/hier.him");

    Property.set(localClass, "PaintScheme_gb", new PaintSchemeBMPar02());
    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1965.5F);
    Property.set(localClass, "FlightModel", "FlightModels/BeaufighterMkX.fmd");

    Property.set(localClass, "cockpitClass", new Class[] { CockpitBEAU21.class, CockpitBEAU10Gun.class });

    Property.set(localClass, "LOSElevation", 0.7394F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 1, 1, 1, 9, 9, 3, 3, 9, 3, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 9, 3, 9, 3, 10, 0, 0 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb02", "_ExternalBomb03", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev04", "_ExternalDev05", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalDev06", "_ExternalBomb04", "_ExternalDev07", "_ExternalBomb05", "_MGUN09", "_MGUN10", "_MGUN11" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 31;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];

      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

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
      arrayOf_WeaponSlot[28] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 1500);

      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      for (int j = 31; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2xfuse250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

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
      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(9, "PylonSpitC", 1);

      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);

      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(9, "PylonSpitC", 1);

      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);

      arrayOf_WeaponSlot[28] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 1500);

      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      for (j = 31; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2xfuse500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

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
      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(9, "PylonSpitC", 1);

      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);

      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(9, "PylonSpitC", 1);

      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);

      arrayOf_WeaponSlot[28] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 1500);

      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      for (j = 31; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2x250f2x250w";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN1", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN1", 1);

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
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(9, "PylonSpitC", 1);

      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);

      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(9, "PylonSpitC", 1);

      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);

      arrayOf_WeaponSlot[28] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 1500);

      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      for (j = 31; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2x500f2x250w";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN1", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN1", 1);

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
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(9, "PylonSpitC", 1);

      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);

      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(9, "PylonSpitC", 1);

      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);

      arrayOf_WeaponSlot[28] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 1500);

      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      for (j = 31; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2x500f2x500w";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN1", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN1", 1);

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
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(9, "PylonSpitC", 1);

      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);

      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(9, "PylonSpitC", 1);

      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);

      arrayOf_WeaponSlot[28] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 1500);

      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      for (j = 31; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "8x60rock";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN2", 1);

      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN3", 1);

      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[23] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 1500);

      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      for (j = 31; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "8x90rock";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN2", 1);

      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN3", 1);

      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGun90", 1);

      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGun90", 1);

      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGun90", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGun90", 1);

      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(2, "RocketGun90", 1);

      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(2, "RocketGun90", 1);

      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(2, "RocketGun90", 1);

      arrayOf_WeaponSlot[23] = new Aircraft._WeaponSlot(2, "RocketGun90", 1);

      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 1500);

      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      for (j = 31; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2xfuse2508x60rock";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN2", 1);

      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN3", 1);

      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[23] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(9, "PylonSpitC", 1);

      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);

      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(9, "PylonSpitC", 1);

      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);

      arrayOf_WeaponSlot[28] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 1500);

      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      for (j = 31; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2xfuse5008x60rock";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN2", 1);

      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN3", 1);

      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[23] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(9, "PylonSpitC", 1);

      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);

      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(9, "PylonSpitC", 1);

      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);

      arrayOf_WeaponSlot[28] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 1500);

      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      for (j = 31; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2xfuse2508x90rock";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN2", 1);

      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN3", 1);

      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGun90", 1);

      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGun90", 1);

      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGun90", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGun90", 1);

      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(2, "RocketGun90", 1);

      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(2, "RocketGun90", 1);

      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(2, "RocketGun90", 1);

      arrayOf_WeaponSlot[23] = new Aircraft._WeaponSlot(2, "RocketGun90", 1);

      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(9, "PylonSpitC", 1);

      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);

      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(9, "PylonSpitC", 1);

      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);

      arrayOf_WeaponSlot[28] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 1500);

      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      for (j = 31; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "1xtorp";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN4", 1);

      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(3, "BombGunTorpMk13a", 1);

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
      arrayOf_WeaponSlot[28] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 1500);

      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      for (j = 31; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "1xtorp2x250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN1", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN1", 1);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);

      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN4", 1);

      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(3, "BombGunTorpMk13a", 1);

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
      arrayOf_WeaponSlot[28] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 1500);

      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      for (j = 31; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "1xtorp8x60rock";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIkpzl", 250);

      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN4", 1);

      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(3, "BombGunTorpMk13a", 1);

      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN2", 1);

      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonBEAUPLN3", 1);

      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[23] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);

      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 1500);

      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      for (j = 31; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (j = 0; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}