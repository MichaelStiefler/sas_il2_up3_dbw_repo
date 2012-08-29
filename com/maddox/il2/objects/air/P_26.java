package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.CLASS;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class P_26 extends P_26x
{
  public boolean bChangedExts;
  public static boolean bChangedPit = false;

  public P_26()
  {
    this.bChangedExts = false;
  }

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    this.bChangedExts = true;
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      bChangedPit = true;
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    this.bChangedExts = true;
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      bChangedPit = true;
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "P-26");
    Property.set(localClass, "meshName", "3DO/Plane/P-26(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "yearService", 1932.0F);
    Property.set(localClass, "yearExpired", 1946.0F);
    Property.set(localClass, "FlightModel", "FlightModels/P-26.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitP_26.class });

    Property.set(localClass, "LOSElevation", 1.032F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 9, 9, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_ExternalDev01", "_ExternalDev02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 8;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(1, "MGunBrowning303s", 500);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunBrowning303s", 500);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      for (int j = 6; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "4x25";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(1, "MGunBrowning303s", 500);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunBrowning303s", 500);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGunPuW125", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunPuW125", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunPuW125", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunPuW125", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}