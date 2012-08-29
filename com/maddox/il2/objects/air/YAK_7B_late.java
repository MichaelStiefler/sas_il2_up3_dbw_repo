package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class YAK_7B_late extends YAK_7BPF
{
  public static boolean bChangedPit = false;

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers())
      bChangedPit = true;
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers())
      bChangedPit = true;
  }

  static Class _mthclass$(String paramString)
  {
    Class localClass;
    try {
      localClass = Class.forName(paramString);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
    return localClass;
  }

  private static Aircraft._WeaponSlot[] GenerateDefaultConfig(int paramInt)
  {
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[paramInt];
    try
    {
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunUBsi", 260);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunUBsi", 140);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunShVAKki", 120);
    }
    catch (Exception localException)
    {
    }

    return arrayOf_WeaponSlot;
  }

  static
  {
    Class localClass = YAK_7B_late.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Yak");
    Property.set(localClass, "meshName", "3DO/Plane/Yak-7B_late/hier.him");
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1945.0F);
    Property.set(localClass, "cockpitClass", new Class[] { CockpitYAK_7B_late.class });
    Property.set(localClass, "FlightModel", "FlightModels/Yak-9.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 9, 9, 9, 9, 9, 9, 2, 2, 2, 2, 2, 2 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 15;

      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      for (int j = 3; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "6xRS82";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
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