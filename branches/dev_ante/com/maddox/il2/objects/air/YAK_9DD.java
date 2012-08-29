package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class YAK_9DD extends YAK
  implements TypeBNZFighter
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = 0.0F;
    f = Math.max(1500.0F * -paramFloat, -80.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, paramFloat * 80.0F, 0.0F);
    f = Math.max(1500.0F * -paramFloat, -60.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, paramFloat * 82.5F, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, paramFloat * 82.5F, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, paramFloat * -85.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, paramFloat * -85.0F, 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat)
  {
  }

  public void update(float paramFloat)
  {
    hierMesh().chunkSetAngles("Water_luk", 0.0F, this.FM.EI.engines[0].getControlRadiator() * 12.0F, 0.0F);
    super.update(paramFloat);
  }

  static Class _mthclass$(String paramString)
  {
    Class localClass;
    try
    {
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
    Class localClass = YAK_9DD.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Yak");
    Property.set(localClass, "meshName", "3DO/Plane/Yak-9DD(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());
    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1952.8F);
    Property.set(localClass, "FlightModel", "FlightModels/Yak-9DD.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitYAK_9D.class });
    Property.set(localClass, "LOSElevation", 0.6432F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 1;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];

      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunShVAKki", 120);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}