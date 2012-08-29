package com.maddox.il2.objects.air;

import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class NW_LA_5F_Late extends LA_X
{
  private static Aircraft._WeaponSlot[] GenerateDefaultConfig(int paramInt)
  {
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[paramInt];
    try
    {
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(1, "MGunShVAKs", 170);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunShVAKs", 200);
    } catch (Exception localException) {
    }
    return arrayOf_WeaponSlot;
  }

  static
  {
    Class localClass = NW_LA_5F_Late.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "La");
    Property.set(localClass, "meshName", "3DO/Plane/La-5F(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1948.0F);
    Property.set(localClass, "FlightModel", "FlightModels/La-5FLate.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitLA_5FN.class });
    Property.set(localClass, "LOSElevation", 0.750618F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1, 3, 3, 9, 9 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 6;

      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      for (int j = 2; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2xFAB50";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(3, "BombGunFAB50", 1);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGunFAB50", 1);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2xFAB100";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(3, "BombGunFAB100", 1);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGunFAB100", 1);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2xDROPTANK";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank80", 1);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank80", 1);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
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