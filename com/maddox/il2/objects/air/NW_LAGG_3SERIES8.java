package com.maddox.il2.objects.air;

import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class NW_LAGG_3SERIES8 extends LAGG_3
  implements TypeTNBFighter
{
  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.FM.M.massEmpty -= 17.700001F;
  }

  private static Aircraft._WeaponSlot[] GenerateDefaultConfig(int paramInt)
  {
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[paramInt];
    try
    {
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunUBs", 220);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunVYak", 90);
    } catch (Exception localException) {
    }
    return arrayOf_WeaponSlot;
  }

  static
  {
    Class localClass = NW_LAGG_3SERIES8.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "LaGG");
    Property.set(localClass, "meshName", "3do/plane/LaGG-3series29/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1944.5F);
    Property.set(localClass, "FlightModel", "FlightModels/LaGG-3series4.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitLAGG_3SERIES4.class });
    Property.set(localClass, "LOSElevation", 0.69445F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 1, 3, 3, 9, 2, 9, 2, 9, 2, 9, 2, 9, 2, 9, 2, 9, 2, 9, 2, 9, 9 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_CANNON01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalRock01", "_ExternalDev02", "_ExternalRock02", "_ExternalDev03", "_ExternalRock03", "_ExternalDev04", "_ExternalRock04", "_ExternalDev05", "_ExternalRock05", "_ExternalDev06", "_ExternalRock06", "_ExternalDev07", "_ExternalRock07", "_ExternalDev08", "_ExternalRock08", "_ExternalBomb01", "_ExternalBomb02" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 23;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      for (int j = 2; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2xFAB100";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(3, "BombGunFAB100", 1);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGunFAB100", 1);
      for (j = 4; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "8xRS82";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "ShVAK";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunShVAKk", 120);
      for (j = 2; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "ShVAK_2xFAB100";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunShVAKk", 120);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(3, "BombGunFAB100", 1);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGunFAB100", 1);
      for (j = 5; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "ShVAK_8xRS82";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunShVAKk", 120);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
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