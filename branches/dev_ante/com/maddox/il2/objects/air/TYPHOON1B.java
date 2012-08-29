package com.maddox.il2.objects.air;

import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class TYPHOON1B extends TEMPEST
{
  static
  {
    Class localClass = TYPHOON1B.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Typhoon");
    Property.set(localClass, "meshName", "3DO/Plane/TyphoonMkIB(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1946.5F);
    Property.set(localClass, "FlightModel", "FlightModels/Typhoon1B.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitTYPHOON1B.class });

    Property.set(localClass, "LOSElevation", 0.93655F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1, 1, 1, 9, 9, 9, 9, 3, 3, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev05", "_ExternalDev06", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 20;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 140);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 140);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 140);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 140);
      for (int j = 4; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2x500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 140);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 140);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 140);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 140);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN1", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN2", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      for (int k = 10; k < i; k++) {
        arrayOf_WeaponSlot[k] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2x1000";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 140);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 140);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 140);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 140);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN1", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN2", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      for (int m = 10; m < i; m++) {
        arrayOf_WeaponSlot[m] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "8x5";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 140);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 140);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 140);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 140);
      for (int n = 4; n < 10; n++) {
        arrayOf_WeaponSlot[n] = null;
      }
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN3", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN4", 1);
      for (int i1 = 12; i1 < i; i1++) {
        arrayOf_WeaponSlot[i1] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5BEAU", 1);
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2xdt";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 140);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 140);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 140);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 140);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankTempest", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankTempest", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN1", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonTEMPESTPLN2", 1);
      for (int i2 = 8; i2 < i; i2++) {
        arrayOf_WeaponSlot[i2] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int i3 = 0; i3 < i; i3++) {
        arrayOf_WeaponSlot[i3] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}