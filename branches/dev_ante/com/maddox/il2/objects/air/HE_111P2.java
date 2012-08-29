package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class HE_111P2 extends HE_111
{
  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay1_D0", 0.0F, 74.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -94.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay3_D0", 0.0F, 74.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay4_D0", 0.0F, -94.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay5_D0", 0.0F, 74.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay6_D0", 0.0F, -94.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay7_D0", 0.0F, 74.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay8_D0", 0.0F, -94.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay9_D0", 0.0F, -74.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay10_D0", 0.0F, 94.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay11_D0", 0.0F, -74.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay12_D0", 0.0F, 94.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay13_D0", 0.0F, -74.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay14_D0", 0.0F, 94.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay15_D0", 0.0F, -74.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay16_D0", 0.0F, 94.0F * paramFloat, 0.0F);
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

  static {
    Class localClass = HE_111P2.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "He-111");
    Property.set(localClass, "meshName", "3do/plane/He-111H-2/hier_He111P2.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "yearService", 1939.5F);
    Property.set(localClass, "yearExpired", 1948.0F);
    Property.set(localClass, "FlightModel", "FlightModels/He-111P2.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitHE_111H2.class, CockpitHE_111H2_Bombardier.class, CockpitHE_111H2_NGunner.class, CockpitHE_111H2_TGunner.class, CockpitHE_111H2_BGunner.class, CockpitHE_111H2_LGunner.class, CockpitHE_111H2_RGunner.class });

    Property.set(localClass, "LOSElevation", 0.742F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 13, 14, 3, 3, 3, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_BombSpawn08" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 13;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunMG15t", 150);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunMG15t", 1000);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(12, "MGunMG15t", 750);
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "16xSC50";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunMG15t", 150);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunMG15t", 1000);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(12, "MGunMG15t", 750);
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunSC50", 2);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunSC50", 2);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunSC50", 2);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunSC50", 2);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(3, "BombGunSC50", 2);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGunSC50", 2);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGunSC50", 2);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(3, "BombGunSC50", 2);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "32xSC50";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunMG15t", 150);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunMG15t", 1000);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(12, "MGunMG15t", 750);
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunSC50", 4);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunSC50", 4);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunSC50", 4);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunSC50", 4);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(3, "BombGunSC50", 4);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGunSC50", 4);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGunSC50", 4);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(3, "BombGunSC50", 4);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "16XSC70";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunMG15t", 150);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunMG15t", 1000);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(12, "MGunMG15t", 750);
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunSC70", 2);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunSC70", 2);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunSC70", 2);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunSC70", 2);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(3, "BombGunSC70", 2);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGunSC70", 2);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGunSC70", 2);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(3, "BombGunSC70", 2);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "24XSC70";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunMG15t", 150);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunMG15t", 1000);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(12, "MGunMG15t", 750);
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunSC70", 3);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunSC70", 3);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunSC70", 3);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunSC70", 3);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(3, "BombGunSC70", 3);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGunSC70", 3);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGunSC70", 3);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(3, "BombGunSC70", 3);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "4XSC250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunMG15t", 150);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunMG15t", 1000);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(12, "MGunMG15t", 750);
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "8XSC250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunMG15t", 150);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunMG15t", 1000);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(12, "MGunMG15t", 750);
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}