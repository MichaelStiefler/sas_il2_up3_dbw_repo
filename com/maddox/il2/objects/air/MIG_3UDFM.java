package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class MIG_3UDFM extends MIG_3
{
  private float kangle;

  public MIG_3UDFM()
  {
    this.kangle = 0.0F;
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.forceCockpitDoor(1.0F);
  }

  public void update(float paramFloat)
  {
    hierMesh().chunkSetAngles("WaterFlap_D0", 0.0F, 30.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("OilRad1_D0", 0.0F, -20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("OilRad2_D0", 0.0F, -20.0F * this.kangle, 0.0F);
    this.kangle = (0.95F * this.kangle + 0.05F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getControlRadiator());
    super.update(paramFloat);
  }

  static
  {
    Class localClass = MIG_3UDFM.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "MiG");
    Property.set(localClass, "meshName", "3do/plane/MIG-3ud_fm/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFCSPar01());
    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/MiG-3ud_fm.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitMIG_3.class });

    Property.set(localClass, "LOSElevation", 0.906F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 3, 3, 3, 3, 9, 9, 2, 2, 2, 2, 2, 2 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev03", "_ExternalDev04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 15;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunShKASs", 750);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunShKASs", 750);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunUBk", 300);
      for (int j = 3; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "6xRS-82";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunShKASs", 750);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunShKASs", 750);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunUBk", 300);
      for (int k = 3; k < 7; k++) {
        arrayOf_WeaponSlot[k] = null;
      }
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonRO_82_3", 0);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonRO_82_3", 0);
      for (int m = 9; m < i; m++) {
        arrayOf_WeaponSlot[m] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "4xFAB-50";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunShKASs", 750);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunShKASs", 750);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunUBk", 300);
      for (int n = 3; n < 7; n++) {
        arrayOf_WeaponSlot[n] = new Aircraft._WeaponSlot(3, "BombGunFAB50", 1);
      }
      for (int i1 = 7; i1 < i; i1++) {
        arrayOf_WeaponSlot[i1] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2xFAB-100";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunShKASs", 750);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunShKASs", 750);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunUBk", 300);
      for (int i2 = 3; i2 < 5; i2++) {
        arrayOf_WeaponSlot[i2] = new Aircraft._WeaponSlot(3, "BombGunFAB100", 1);
      }
      for (int i3 = 5; i3 < i; i3++) {
        arrayOf_WeaponSlot[i3] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int i4 = 0; i4 < i; i4++) {
        arrayOf_WeaponSlot[i4] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}