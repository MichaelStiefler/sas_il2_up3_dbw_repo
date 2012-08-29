package com.maddox.il2.objects.air;

import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class LAGG_3SERIES1 extends LAGG_3
  implements TypeTNBFighter
{
  private static Aircraft._WeaponSlot[] GenerateDefaultConfig(int paramInt)
  {
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[paramInt];
    try
    {
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(1, "MGunUBs", 220);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunUBs", 220);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunShKASs", 325);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunShKASs", 325);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunUBk", 220);
    } catch (Exception localException) {
    }
    return arrayOf_WeaponSlot;
  }

  static
  {
    Class localClass = LAGG_3SERIES1.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "LaGG");
    Property.set(localClass, "meshName", "3do/plane/LaGG-3series4/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1944.5F);
    Property.set(localClass, "FlightModel", "FlightModels/LaGG-3series4.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitLAGG_3SERIES4.class });
    Property.set(localClass, "LOSElevation", 0.69445F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1, 0, 0, 1, 3, 3, 9, 2, 9, 2, 9, 2, 9, 2, 9, 2, 9, 2, 9, 2, 9, 2, 9, 9 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalRock01", "_ExternalDev02", "_ExternalRock02", "_ExternalDev03", "_ExternalRock03", "_ExternalDev04", "_ExternalRock04", "_ExternalDev05", "_ExternalRock05", "_ExternalDev06", "_ExternalRock06", "_ExternalDev07", "_ExternalRock07", "_ExternalDev08", "_ExternalRock08", "_ExternalBomb01", "_ExternalBomb02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunUBs 220", "MGunUBs 220", "MGunShKASs 325", "MGunShKASs 325", "MGunShVAKk 120", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "8xRS82", new String[] { "MGunUBs 220", "MGunUBs 220", "MGunShKASs 325", "MGunShKASs 325", "MGunShVAKk 120", null, null, "PylonRO_82_1", "RocketGunRS82", "PylonRO_82_1", "RocketGunRS82", "PylonRO_82_1", "RocketGunRS82", "PylonRO_82_1", "RocketGunRS82", "PylonRO_82_1", "RocketGunRS82", "PylonRO_82_1", "RocketGunRS82", "PylonRO_82_1", "RocketGunRS82", "PylonRO_82_1", "RocketGunRS82", null, null });

    Aircraft.weaponsRegister(localClass, "2xFAB50", new String[] { "MGunUBs 220", "MGunUBs 220", "MGunShKASs 325", "MGunShKASs 325", "MGunShVAKk 120", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2xDROPTANK", new String[] { "MGunUBs 220", "MGunUBs 220", "MGunShKASs 325", "MGunShKASs 325", "MGunShVAKk 120", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "FuelTankGun_Tank80", "FuelTankGun_Tank80" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 25;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      for (int j = 5; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2xFAB50";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunFAB50", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunFAB50", 1);
      for (j = 7; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2xDROPTANK";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      for (j = 5; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      arrayOf_WeaponSlot[23] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank80", 1);
      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank80", 1);
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