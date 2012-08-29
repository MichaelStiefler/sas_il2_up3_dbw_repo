package com.maddox.il2.objects.air;

import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class F_86F_25E extends F_86F
{
  private static Aircraft._WeaponSlot[] GenerateDefaultConfig(int paramInt)
  {
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[paramInt];
    try {
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowningM3", 270);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowningM3", 270);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowningM3b", 270);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowningM3b", 270);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowningM3c", 270);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowningM3c", 270);
    }
    catch (Exception localException)
    {
    }
    return arrayOf_WeaponSlot;
  }

  protected void moveSlats(float paramFloat) {
    resetYPRmodifier();
    Aircraft.xyz[0] = Aircraft.cvt(this.FM.getAOA(), 6.8F, 15.0F, 0.0F, -0.15F);
    Aircraft.xyz[1] = Aircraft.cvt(this.FM.getAOA(), 6.8F, 15.0F, 0.0F, 0.1F);
    Aircraft.xyz[2] = Aircraft.cvt(this.FM.getAOA(), 6.8F, 15.0F, 0.0F, -0.065F);
    hierMesh().chunkSetAngles("SlatL_D0", 0.0F, Aircraft.cvt(this.FM.getAOA(), 6.8F, 15.0F, 0.0F, 8.5F), 0.0F);
    hierMesh().chunkSetLocate("SlatL_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[1] = Aircraft.cvt(this.FM.getAOA(), 6.8F, 15.0F, 0.0F, -0.1F);
    hierMesh().chunkSetAngles("SlatR_D0", 0.0F, Aircraft.cvt(this.FM.getAOA(), 6.8F, 15.0F, 0.0F, 8.5F), 0.0F);
    hierMesh().chunkSetLocate("SlatR_D0", Aircraft.xyz, Aircraft.ypr);
  }

  protected void slatsOff() {
    if (this.bSlatsOff) return;
    resetYPRmodifier();
    Aircraft.xyz[0] = -0.15F;
    Aircraft.xyz[1] = 0.1F;
    Aircraft.xyz[2] = -0.065F;
    hierMesh().chunkSetAngles("SlatL_D0", 0.0F, 8.5F, 0.0F);
    hierMesh().chunkSetLocate("SlatL_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[1] = -0.1F;
    hierMesh().chunkSetAngles("SlatR_D0", 0.0F, 8.5F, 0.0F);
    hierMesh().chunkSetLocate("SlatR_D0", Aircraft.xyz, Aircraft.ypr);
    this.bSlatsOff = true;
  }
  static Class _mthclass$(String paramString) {
    Class localClass;
    try { localClass = Class.forName(paramString);
    } catch (ClassNotFoundException localClassNotFoundException) {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }

    return localClass;
  }

  static
  {
    Class localClass = F_86F_25E.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "F-86");
    Property.set(localClass, "meshName", "3DO/Plane/F-86F(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar06());
    Property.set(localClass, "meshName_us", "3DO/Plane/F-86F(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar06());
    Property.set(localClass, "yearService", 1949.9F);
    Property.set(localClass, "yearExpired", 1960.3F);
    Property.set(localClass, "FlightModel", "FlightModels/F-86F-25E.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitF_86F.class });
    Property.set(localClass, "LOSElevation", 0.725F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 9, 9, 9, 9, 3, 3, 9, 9, 9, 9, 9, 9, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 38;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      for (int j = 6; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2x207gal";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonF86_Outboard", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonF86_Outboard", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank207galL", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank207galR", 1);
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2x120gal";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonF86_Outboard", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonF86_Outboard", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankC120galL", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankC120galR", 1);
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2x1000lbs";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonF86_Bombs", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonF86_Bombs", 1);
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
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