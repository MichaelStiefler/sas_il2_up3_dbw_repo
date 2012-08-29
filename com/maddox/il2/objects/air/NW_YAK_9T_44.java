package com.maddox.il2.objects.air;

import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class NW_YAK_9T_44 extends YAK_9TX
{
  private static Aircraft._WeaponSlot[] GenerateDefaultConfig(int paramInt)
  {
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[paramInt];
    try
    {
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunUBsi", 200);
    } catch (Exception localException) {
    }
    return arrayOf_WeaponSlot;
  }

  static
  {
    Class localClass = NW_YAK_9T_44.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Yak");
    Property.set(localClass, "meshName", "3DO/Plane/Yak-9M(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());
    Property.set(localClass, "meshName_fr", "3DO/Plane/Yak-9M(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme_fr", new PaintSchemeFCSPar05());
    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1952.8F);
    Property.set(localClass, "FlightModel", "FlightModels/Yak-9T-44.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitYAK_9T.class });
    Property.set(localClass, "LOSElevation", 0.661F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 1, 1, 9 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_CANNON01", "_CANNON02", "_ExternalDev01" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 4;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunNS37ki", 30);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(9, "PylonMG15120Internal", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int j = 0; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(9, "PylonMG15120Internal", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}