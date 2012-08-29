package com.maddox.il2.objects.air;

import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class HurricaneMkIId extends Hurricane
  implements TypeFighter, TypeStormovik, TypeStormovikArmored
{
  static
  {
    Class localClass = HurricaneMkIId.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Hurri");
    Property.set(localClass, "meshName", "3DO/Plane/HurricaneMkIId(Multi1)/hier.him");

    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/HurricaneMkIId.fmd");

    Property.set(localClass, "cockpitClass", CockpitHURRII.class);

    Property.set(localClass, "LOSElevation", 0.66895F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 4;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];

      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303Trak", 334);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303Trak", 334);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunVickers40mmAPk", 15);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunVickers40mmAPk", 15);

      for (int j = 4; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "VickersS40mmHE";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303Trak", 334);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303Trak", 334);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunVickers40mmk", 15);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunVickers40mmk", 15);

      for (j = 4; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "None";
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