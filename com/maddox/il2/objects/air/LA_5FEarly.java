package com.maddox.il2.objects.air;

import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class LA_5FEarly extends LA_X
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
    Class localClass = LA_5FEarly.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "La");
    Property.set(localClass, "meshName", "3DO/Plane/La-5(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar02());
    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1948.0F);
    Property.set(localClass, "FlightModel", "FlightModels/La-5FEarly.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitLA_5.class });
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

      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];

      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunShVAKs", 170);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunShVAKs", 200);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2xFAB50";

      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];

      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunShVAKs", 170);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunShVAKs", 200);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(3, "BombGunFAB50", 1);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGunFAB50", 1);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2xFAB100";

      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];

      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunShVAKs", 170);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunShVAKs", 200);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(3, "BombGunFAB100", 1);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGunFAB100", 1);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2xDROPTANK";

      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];

      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunShVAKs", 170);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunShVAKs", 200);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank80", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank80", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int j = 0; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}