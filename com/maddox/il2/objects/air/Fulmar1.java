package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Finger;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.util.ArrayList;

public class Fulmar1 extends Fulmar
{
  public boolean typeBomberToggleAutomation()
  {
    return false;
  }
  public void typeBomberAdjDistanceReset() {
  }
  public void typeBomberAdjDistancePlus() {
  }
  public void typeBomberAdjDistanceMinus() {
  }
  public void typeBomberAdjSideslipReset() {
  }
  public void typeBomberAdjSideslipPlus() {
  }
  public void typeBomberAdjSideslipMinus() {
  }
  public void typeBomberAdjAltitudeReset() {
  }
  public void typeBomberAdjAltitudePlus() {
  }
  public void typeBomberAdjAltitudeMinus() {
  }
  public void typeBomberAdjSpeedReset() {
  }
  public void typeBomberAdjSpeedPlus() {
  }
  public void typeBomberAdjSpeedMinus() {
  }

  public void typeBomberUpdate(float paramFloat) {
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
  }

  static {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Fulmar");
    Property.set(localClass, "meshName", "3DO/Plane/Fulmar1/hier.him");

    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar02f());
    Property.set(localClass, "yearService", 1936.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/FulmarMkI.fmd");

    Property.set(localClass, "cockpitClass", new Class[] { CockpitFulmar.class });

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 9 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 11;

      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2x250lb";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[10] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "1xDropTank";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 750);
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank60gal", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];

      for (int j = 0; j < 11; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}