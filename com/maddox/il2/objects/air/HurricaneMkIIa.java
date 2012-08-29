package com.maddox.il2.objects.air;

import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class HurricaneMkIIa extends Hurricane
{
  static
  {
    Class localClass = HurricaneMkIIa.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "HurriMkIIa");
    Property.set(localClass, "meshName", "3DO/Plane/HurricaneMkIIa(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "yearService", 1940.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/HurricaneMkII.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitHURRII.class });

    Property.set(localClass, "LOSElevation", 0.965F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 0, 0 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 8;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int j = 0; j < i; j++) {
        arrayOf_WeaponSlot[j] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 337);
      }
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