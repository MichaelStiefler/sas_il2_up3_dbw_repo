package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class HurricaneMkI_BoB extends Hurricane
  implements TypeFighter, TypeTNBFighter
{
  private float flapps;
  public static boolean bChangedPit = false;

  public HurricaneMkI_BoB()
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

  protected void moveFlap(float paramFloat)
  {
    float f = -85.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap04_D0", 0.0F, f, 0.0F);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL8_D0", 0.0F, -26.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -100.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -152.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR8_D0", 0.0F, -26.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -100.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -152.0F * paramFloat, 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat)
  {
    hierMesh().chunkSetAngles("GearC2_D0", paramFloat, 0.0F, 0.0F);
  }

  public void moveWheelSink()
  {
    resetYPRmodifier();
    Aircraft.xyz[2] = (-Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.25F, 0.0F, 0.25F));
    hierMesh().chunkSetLocate("GearL10_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[2] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.25F, 0.0F, 0.25F);
    hierMesh().chunkSetLocate("GearR10_D0", Aircraft.xyz, Aircraft.ypr);
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
    Class localClass = HurricaneMkI_BoB.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Hurricane");
    Property.set(localClass, "meshName", "3DO/Plane/HurricaneMkI(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());
    Property.set(localClass, "yearService", 1940.0F);
    Property.set(localClass, "yearExpired", 1943.5F);
    Property.set(localClass, "FlightModel", "FlightModels/HurricaneMkI_BoB.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitHURRI.class });

    Property.set(localClass, "LOSElevation", 0.5926F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 0, 0 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 8;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 334);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 334);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 334);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 356);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 334);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 334);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 334);
      for (int j = 8; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int k = 0; k < i; k++) {
        arrayOf_WeaponSlot[k] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}