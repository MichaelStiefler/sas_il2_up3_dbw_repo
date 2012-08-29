package com.maddox.il2.objects.air;

import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class NW_FW_190F3 extends FW_190A5165ATA
{
  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.FM.M.massEmpty -= 0.0F;
  }

  static
  {
    Class localClass = NW_FW_190F3.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "FW190");
    Property.set(localClass, "meshName", "3DO/Plane/Fw-190A-5(Beta)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1948.0F);
    Property.set(localClass, "FlightModel", "FlightModels/Fw-190F-3.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitFW_190A5.class });
    Property.set(localClass, "LOSElevation", 0.764106F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 1, 9, 9, 9, 9, 9, 9, 2, 2, 9, 9, 3, 3, 3, 3, 9, 9, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalRock01", "_ExternalRock02", "_ExternalDev09", "_ExternalDev10", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02", "_ExternalBomb05" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 23;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 250);
      for (int j = 4; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonETC71", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonETC71", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "sc250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 250);
      for (j = 4; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonETC71", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonETC71", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "ab250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 250);
      for (j = 4; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonETC71", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonETC71", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(3, "BombGunAB250", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "sc500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 250);
      for (j = 4; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonETC71", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonETC71", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "1sc2504sc50";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 250);
      for (j = 4; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonETC71", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonETC71", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "1ab2504sc50";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 250);
      for (j = 4; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonETC71", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonETC71", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(3, "BombGunAB250", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "1sc5004sc50";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17si", 900);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 250);
      for (j = 4; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonETC71", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonETC71", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (j = 0; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonETC71", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonETC71", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}