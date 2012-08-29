package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class YAK_9RLR_DZZ extends YAK
  implements TypeBNZFighter, TypeScout
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = Math.max(-paramFloat * 1500.0F, -80.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 80.0F * paramFloat, 0.0F);
    f = Math.max(-paramFloat * 1500.0F, -60.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 82.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 82.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -85.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -85.0F * paramFloat, 0.0F);
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
    hierMesh().chunkSetAngles("Water_luk", 0.0F, 12.0F * this.FM.EI.engines[0].getControlRadiator(), 0.0F);
    super.update(paramFloat);
  }

  private static Aircraft._WeaponSlot[] GenerateDefaultConfig(int paramInt)
  {
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[paramInt];
    try
    {
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunShVAKki", 120);
    } catch (Exception localException) {
    }
    return arrayOf_WeaponSlot;
  }

  static
  {
    Class localClass = YAK_9RLR_DZZ.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Yak");
    Property.set(localClass, "meshName", "3DO/Plane/Yak-9D(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());
    Property.set(localClass, "meshName_fr", "3DO/Plane/Yak-9D(fr)/hier.him");
    Property.set(localClass, "PaintScheme_fr", new PaintSchemeFCSPar05());
    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1952.8F);
    Property.set(localClass, "FlightModel", "FlightModels/Yak-9D.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitYAK_9D.class });

    Property.set(localClass, "LOSElevation", 0.6432F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShVAKki 120" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 3;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      for (int j = 1; j < i; j++) {
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