package com.maddox.il2.objects.air;

import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class Mig_15bislate extends Mig_15F
{
  public final void update(float f)
  {
    super.update(f);

    if ((gmnr() >= 0.95F) && 
      (!this.curst))
      this.FM.CT.AirBrakeControl = 1.0F;
  }

  private static Aircraft._WeaponSlot[] GenerateDefaultConfig(int paramInt) {
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[paramInt];
    try {
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunNS37ki", 40);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunVYaki", 80);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunVYaki", 80);
    }
    catch (Exception localException)
    {
    }
    return arrayOf_WeaponSlot;
  }
  static Class _mthclass$(String paramString) {
    Class localClass;
    try {
      localClass = Class.forName(paramString);
    } catch (ClassNotFoundException localClassNotFoundException) {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }

    return localClass;
  }

  static
  {
    Class localClass = Mig_15bislate.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "MiG-15");
    Property.set(localClass, "meshName_ru", "3DO/Plane/MiG-15bislate(ru)/hier.him");
    Property.set(localClass, "PaintScheme_ru", new PaintSchemeFCSPar06());
    Property.set(localClass, "meshName", "3DO/Plane/MiG-15bislate(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar06());
    Property.set(localClass, "yearService", 1951.4F);
    Property.set(localClass, "yearExpired", 1960.3F);
    Property.set(localClass, "FlightModel", "FlightModels/MiG-15F.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitMig_15F.class });
    Property.set(localClass, "LOSElevation", 0.725F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_CANNON03" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);

      int i = 3;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      for (int j = 3; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
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