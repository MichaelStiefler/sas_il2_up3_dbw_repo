package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class BF_110G4 extends BF_110
{
  public static boolean bChangedPit = false;

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

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    float f1 = -paramArrayOfFloat[0];
    float f2 = paramArrayOfFloat[1];
    switch (paramInt)
    {
    case 0:
      if (f2 < -19.0F)
      {
        f2 = -19.0F;
        bool = false;
      }
      if (f2 > 30.0F)
      {
        f2 = 30.0F;
        bool = false;
      }
      float f3;
      if (f2 < 0.0F) {
        f3 = Aircraft.cvt(f2, -19.0F, 0.0F, 20.0F, 30.0F);
      }
      else if (f2 < 12.0F)
        f3 = Aircraft.cvt(f2, 0.0F, 12.0F, 30.0F, 35.0F);
      else
        f3 = Aircraft.cvt(f2, 12.0F, 30.0F, 35.0F, 40.0F);
      if (f1 < 0.0F)
      {
        if (f1 < -f3)
        {
          f1 = -f3;
          bool = false;
        }
      }
      else if (f1 > f3)
      {
        f1 = f3;
        bool = false;
      }
      if ((Math.abs(f1) <= 17.799999F) || (Math.abs(f1) >= 25.0F) || (f2 >= -12.0F)) break;
      bool = false;
    }

    paramArrayOfFloat[0] = (-f1);
    paramArrayOfFloat[1] = f2;
    return bool;
  }

  static Class _mthclass$(String paramString)
  {
    try
    {
      return Class.forName(paramString);
    }
    catch (ClassNotFoundException localClassNotFoundException) {
    }
    throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
  }

  static
  {
    Class localClass = BF_110G4.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Bf-110");
    Property.set(localClass, "meshName", "3DO/Plane/Bf-110G-4/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar03());
    Property.set(localClass, "yearService", 1940.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/Bf-110G-4.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitBF_110G4.class, CockpitBF_110G4_Gunner.class });

    Property.set(localClass, "LOSElevation", 0.66895F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1, 1, 1, 0, 0, 10, 10 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_MGUN05", "_MGUN06" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 8;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(1, "MGunMK108ki", 137);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunMK108ki", 137);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMGFFk", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMGFFk", 250);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunMGFFk", 200);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunMGFFk", 200);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(10, "MGunMG15t", 1200);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(10, "MGunMG15t", 1200);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int j = 0; j < i; j++) {
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