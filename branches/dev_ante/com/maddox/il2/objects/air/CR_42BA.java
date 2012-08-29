package com.maddox.il2.objects.air;

import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class CR_42BA extends CR_42X
{
  static
  {
    Class localClass = CR_42BA.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "CR.42");
    Property.set(localClass, "meshName", "3DO/Plane/CR42(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar00());
    Property.set(localClass, "meshName_it", "3DO/Plane/CR42(it)/hier.him");
    Property.set(localClass, "PaintScheme_it", new PaintSchemeFCSPar01());
    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1943.0F);
    Property.set(localClass, "FlightModel", "FlightModels/CR42.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitCR42BA.class });

    Property.set(localClass, "LOSElevation", 0.742F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 4;
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      String str = "default";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBredaSAFAT127si", 400);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBredaSAFAT127si", 400);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(3, "BombGunIT_50_M", 1);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGunIT_50_M", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2x100";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBredaSAFAT127si", 400);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBredaSAFAT127si", 400);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(3, "BombGunIT_100_M", 1);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGunIT_100_M", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}