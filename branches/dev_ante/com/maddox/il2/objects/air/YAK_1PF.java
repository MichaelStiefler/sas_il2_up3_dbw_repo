package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class YAK_1PF extends YAK
  implements TypeTNBFighter
{
  public void update(float paramFloat)
  {
    hierMesh().chunkSetAngles("Wind_luk", 0.0F, 32.0F * this.FM.EI.engines[0].getControlRadiator(), 0.0F);
    hierMesh().chunkSetAngles("Water_luk", 0.0F, 32.0F * this.FM.EI.engines[0].getControlRadiator(), 0.0F);
    super.update(paramFloat);
  }

  public void update_windluk(float paramFloat)
  {
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

  private static void FillBomb(Aircraft._WeaponSlot[] paramArrayOf_WeaponSlot)
  {
    try
    {
      paramArrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunShKASsi", 750);
      paramArrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunShKASsi", 750);
      paramArrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunShVAKki", 120);
      paramArrayOf_WeaponSlot[3] = null;
      paramArrayOf_WeaponSlot[4] = null;
      paramArrayOf_WeaponSlot[5] = null;
      paramArrayOf_WeaponSlot[6] = null;
      paramArrayOf_WeaponSlot[7] = null;
      paramArrayOf_WeaponSlot[8] = null;
      paramArrayOf_WeaponSlot[9] = null;
      paramArrayOf_WeaponSlot[10] = null;
      paramArrayOf_WeaponSlot[11] = null;
      paramArrayOf_WeaponSlot[12] = null;
      paramArrayOf_WeaponSlot[13] = null;
      paramArrayOf_WeaponSlot[14] = null;
      paramArrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(3, "BombGunFAB100", 1);
      paramArrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(3, "BombGunFAB100", 1);
    }
    catch (Exception localException)
    {
    }
  }

  static
  {
    Class localClass = YAK_1PF.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Yak");
    Property.set(localClass, "meshName", "3DO/Plane/Yak-1(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar02());
    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/Yak-1PF.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitYAK_1FAIRING.class });
    Property.set(localClass, "LOSElevation", 0.6609F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 9, 9, 9, 9, 9, 9, 2, 2, 2, 2, 2, 2, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalBomb01", "_ExternalBomb02" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);

      int i = 17;

      String str = "default";

      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];

      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunShKASsi", 750);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunShKASsi", 750);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunShVAKki", 120);

      for (int j = 3; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2fab100";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      FillBomb(arrayOf_WeaponSlot);

      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (j = 0; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}