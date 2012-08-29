package com.maddox.il2.objects.air;

import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class A6M3_32kai extends A6M
{
  static
  {
    Class var_class = A6M3_32kai.class;

    new NetAircraft.SPAWN(var_class);
    Property.set(var_class, "iconFar_shortClassName", "A6M");
    Property.set(var_class, "meshName", "3DO/Plane/A6M3-32kai(Multi1)/hier.him");
    Property.set(var_class, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(var_class, "meshName_ja", "3DO/Plane/A6M3-32kai(ja)/hier.him");
    Property.set(var_class, "PaintScheme_ja", new PaintSchemeFCSPar01());
    Property.set(var_class, "yearService", 1942.0F);
    Property.set(var_class, "yearExpired", 1945.5F);
    Property.set(var_class, "FlightModel", "FlightModels/A6M3-32.fmd");
    Property.set(var_class, "cockpitClass", CockpitA6M3_32.class);

    Property.set(var_class, "LOSElevation", 1.01885F);
    Aircraft.weaponTriggersRegister(var_class, 
      new int[] { 0, 0, 1, 1, 9, 9, 3, 9, 9, 
      3, 3 });
    Aircraft.weaponHooksRegister(var_class, 
      new String[] { "_MGUN01", "_MGUN02", "_CANNON01", 
      "_CANNON02", "_ExternalBomb01", 
      "_ExternalDev01", "_ExternalBomb02", 
      "_ExternalDev02", "_ExternalDev03", 
      "_ExternalBomb03", 
      "_ExternalBomb04" });
    try
    {
      ArrayList arraylist = new ArrayList();
      Property.set(var_class, "weaponsList", arraylist);
      HashMapInt hashmapint = new HashMapInt();
      Property.set(var_class, "weaponsMap", hashmapint);
      byte byte0 = 12;

      String s = "default";
      Aircraft._WeaponSlot[] a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunMG15spzl", 1000);
      a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunMG15spzl", 1000);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(1, "Type2_30mm", 48);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(1, "Type2_30mm", 48);
      for (int i = 4; i < byte0; i++) {
        a_lweaponslot[i] = null;
      }
      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);

      s = "1xdt";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunMG15spzl", 1000);
      a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunMG15spzl", 1000);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(1, "Type2_30mm", 48);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(1, "Type2_30mm", 48);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank0", 1);
      for (int i = 5; i < byte0; i++) {
        a_lweaponslot[i] = null;
      }
      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);

      s = "none";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      for (int i = 0; i < byte0; i++) {
        a_lweaponslot[i] = null;
      }
      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
    }
    catch (Exception localException)
    {
    }
  }
}