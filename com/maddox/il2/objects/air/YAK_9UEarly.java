package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class YAK_9UEarly extends YAK
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
    hierMesh().chunkSetAngles("OilRad_D0", 0.0F, 15.0F * this.FM.EI.engines[0].getControlRadiator(), 0.0F);
    hierMesh().chunkSetAngles("Water_luk", 0.0F, 12.0F * this.FM.EI.engines[0].getControlRadiator(), 0.0F);
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
    Class localClass = YAK_9UEarly.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Yak");
    Property.set(localClass, "meshName", "3DO/Plane/Yak-9U(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());
    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1952.8F);
    Property.set(localClass, "FlightModel", "FlightModels/Yak-9UEarly.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitYAK_9U.class });
    Property.set(localClass, "LOSElevation", 0.6432F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);

      int i = 3;

      String str = "default";

      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];

      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunUBsi", 170);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunUBsi", 157);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunShVAKki", 120);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int j = 0; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}