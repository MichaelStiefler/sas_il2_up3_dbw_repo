package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class I_16TYPE28 extends I_16
  implements TypeFighter, TypeTNBFighter
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

  protected void moveFlap(float paramFloat)
  {
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -55.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Flap03_D0", 0.0F, -55.0F * paramFloat, 0.0F);
  }

  private static Aircraft._WeaponSlot[] GenerateDefaultConfig(int paramInt)
  {
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[paramInt];
    try
    {
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunShKASk", 650);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunShKASk", 650);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunShVAKk", 120);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunShVAKk", 120);
    } catch (Exception localException) {
    }
    return arrayOf_WeaponSlot;
  }

  static
  {
    Class localClass = I_16TYPE28.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "I-16");
    Property.set(localClass, "meshName", "3DO/Plane/I-16type28(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "meshName_ru", "3DO/Plane/I-16type28/hier.him");
    Property.set(localClass, "PaintScheme_ru", new PaintSchemeFCSPar01());
    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1943.0F);
    Property.set(localClass, "cockpitClass", new Class[] { CockpitI_16TYPE24.class });

    Property.set(localClass, "FlightModel", "FlightModels/I-16type28.fmd");
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 9, 9, 9, 9, 9, 9, 9, 9 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 20;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      for (int j = 4; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2xFAB50";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGunFAB50", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunFAB50", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2xFAB100";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGunFAB100", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunFAB100", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2tank100";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100i16", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100i16", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "6rs82";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "6rs82tank";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(2, "RocketGunRS82", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(9, "PylonRO_82_1", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100i16", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100i16", 1);
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