package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class SPITFIREXIIclpearly extends SPITFIRE
  implements TypeFighter, TypeBNZFighter
{
  public static boolean bChangedPit = false;
  private float flapps;

  public SPITFIREXIIclpearly()
  {
    this.flapps = 0.0F;
  }

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      bChangedPit = true;
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      bChangedPit = true;
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.0F, 0.6F, 0.0F, -95.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.2F, 1.0F, 0.0F, -95.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, -75.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.09F, 0.0F, -75.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.09F, 0.0F, -75.0F), 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat)
  {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -85.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap04_D0", 0.0F, f, 0.0F);
  }

  public void moveWheelSink()
  {
    resetYPRmodifier();
    Aircraft.xyz[2] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.247F, 0.0F, -0.247F);
    hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[2] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.247F, 0.0F, 0.247F);
    hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
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

  static
  {
    Class localClass = SPITFIREXIIclpearly.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Spit XII");
    Property.set(localClass, "meshName", "3DO/Plane/SPITXIIearly/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01bis());
    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1946.5F);
    Property.set(localClass, "FlightModel", "FlightModels/SpitfireXIIclippedearly.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitSpit14C.class });

    Property.set(localClass, "LOSElevation", 0.5926F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 1, 9, 9, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02", "_ExternalDev08", "_ExternalDev01", "_ExternalBomb01" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 9;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      for (int j = 9; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "30gal";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankSpit30New", 1);
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      for (int k = 9; k < i; k++) {
        arrayOf_WeaponSlot[k] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "45gal";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankSpit45New", 1);
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      for (int m = 9; m < i; m++) {
        arrayOf_WeaponSlot[m] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "90gal";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankSpit90New", 1);
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      for (int n = 9; n < i; n++) {
        arrayOf_WeaponSlot[n] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "250lb";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonSpitC", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      for (int i1 = 9; i1 < i; i1++) {
        arrayOf_WeaponSlot[i1] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "500lb";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonSpitC", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      for (int i2 = 9; i2 < i; i2++) {
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