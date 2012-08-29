package com.maddox.il2.objects.air;

import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class P_51B_10 extends P_51
{
  static
  {
    Class localClass = P_51B_10.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "P-51");
    Property.set(localClass, "meshName", "3DO/Plane/P-51B(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1947.5F);
    Property.set(localClass, "FlightModel", "FlightModels/P-51B-10NA.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitP_51B.class });

    Property.set(localClass, "LOSElevation", 1.03F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 9, 9, 3, 3, 9, 9 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 10;
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      String str = "default";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 350);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 350);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 280);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 280);
      for (int j = 4; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2x250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 350);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 350);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 280);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 280);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
      for (int k = 8; k < i; k++) {
        arrayOf_WeaponSlot[k] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2x500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 350);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 350);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 280);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 280);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      for (int m = 8; m < i; m++) {
        arrayOf_WeaponSlot[m] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2x1000";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 350);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 350);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 280);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 280);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      for (int n = 8; n < i; n++) {
        arrayOf_WeaponSlot[n] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2xTank";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 350);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 350);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 280);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 280);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "PylonP51PLN2", 1);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank75gal2", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank75gal2", 1);
      for (int i1 = 10; i1 < i; i1++) {
        arrayOf_WeaponSlot[i1] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int i2 = 0; i2 < i; i2++) {
        arrayOf_WeaponSlot[i2] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}