package com.maddox.il2.objects.air;

import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class HurricaneMkIaT extends Hurricane
{
  static Class _mthclass$(String paramString)
  {
    Class localClass;
    try
    {
      localClass = Class.forName(paramString); } catch (ClassNotFoundException localClassNotFoundException) { throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }

    return localClass;
  }

  static
  {
    Class localClass = HurricaneMkIaT.class; new NetAircraft.SPAWN(localClass); Property.set(localClass, "iconFar_shortClassName", "Hurri"); Property.set(localClass, "meshName", "3DO/Plane/HurricaneMkIT(Multi1)/hier.him"); Property.set(localClass, "PaintScheme", new PaintSchemeFMPar00()); Property.set(localClass, "yearService", 1938.0F);

    Property.set(localClass, "yearExpired", 1945.5F); Property.set(localClass, "FlightModel", "FlightModels/HurricaneMkI_BoB.fmd"); Property.set(localClass, "cockpitClass", new Class[] { CockpitHURRI.class }); Property.set(localClass, "LOSElevation", 0.965F); Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 0, 0 }); Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08" });
    try { ArrayList localArrayList = new ArrayList(); Property.set(localClass, "weaponsList", localArrayList); HashMapInt localHashMapInt = new HashMapInt(); Property.set(localClass, "weaponsMap", localHashMapInt); int i = 8; String str = "default"; Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i]; arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 334); arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 334); arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 334); arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 356); arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 334); arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 334); arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 308); arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 334); localArrayList.add(str); localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot); str = "none"; arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i]; arrayOf_WeaponSlot[0] = null; arrayOf_WeaponSlot[1] = null; arrayOf_WeaponSlot[2] = null; arrayOf_WeaponSlot[3] = null; arrayOf_WeaponSlot[4] = null; arrayOf_WeaponSlot[5] = null; arrayOf_WeaponSlot[6] = null; arrayOf_WeaponSlot[7] = null; localArrayList.add(str); localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}