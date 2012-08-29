package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class FW_190D11 extends FW_190
{
  private float kangle;

  public FW_190D11()
  {
    this.kangle = 0.0F;
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 77.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 77.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 157.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 157.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC99_D0", 20.0F * paramFloat, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
    float f = Math.max(-paramFloat * 1500.0F, -94.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, -f, 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat)
  {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.getGear() >= 0.98F)
      hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
  }

  public void update(float paramFloat)
  {
    for (int i = 1; i < 13; i++) {
      hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, -10.0F * this.kangle, 0.0F);
    }
    this.kangle = (0.95F * this.kangle + 0.05F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getControlRadiator());
    super.update(paramFloat);
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && 
      ((getGunByHookName("_CANNON03") instanceof GunEmpty)))
    {
      hierMesh().chunkVisible("30mmL_D0", false);
      hierMesh().chunkVisible("30mmR_D0", false);
    }
    if ((getGunByHookName("_MGUN01") instanceof GunEmpty))
      hierMesh().chunkVisible("30mmL_D0", false);
    if ((getGunByHookName("_MGUN02") instanceof GunEmpty))
      hierMesh().chunkVisible("30mmR_D0", false);
  }

  static
  {
    Class localClass = FW_190D11.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "FW190");
    Property.set(localClass, "meshName", "3DO/Plane/Fw-190D-11(Beta)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar06());
    Property.set(localClass, "yearService", 1943.11F);
    Property.set(localClass, "yearExpired", 1948.0F);
    Property.set(localClass, "FlightModel", "FlightModels/Fw-190D-11.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitFW_190D9.class });

    Property.set(localClass, "LOSElevation", 0.764106F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1, 0, 0, 9, 3, 9 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON03", "_CANNON04", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev02" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 7;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(1, "MGunMK108k", 90);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunMK108k", 90);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      for (int j = 7; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2xMG151";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      for (int k = 7; k < i; k++) {
        arrayOf_WeaponSlot[k] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "1xDroptank";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(1, "MGunMK108k", 90);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunMK108k", 90);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1);
      for (int m = 7; m < i; m++) {
        arrayOf_WeaponSlot[m] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2xMG151+1xDroptank";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1);
      for (int n = 7; n < i; n++) {
        arrayOf_WeaponSlot[n] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int i1 = 0; i1 < i; i1++) {
        arrayOf_WeaponSlot[i1] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}