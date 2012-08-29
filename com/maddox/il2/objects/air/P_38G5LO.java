package com.maddox.il2.objects.air;

import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.io.PrintStream;
import java.util.ArrayList;

public class P_38G5LO extends P_38mod
{
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
    return localClass; } 
  static { Class localClass = P_38G5LO.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "P-38");
    Property.set(localClass, "meshNameDemo", "3DO/Plane/P-38G-5-LO(USA)/hier.him");
    Property.set(localClass, "meshName", "3DO/Plane/P-38G-5-LO(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());
    Property.set(localClass, "meshName_us", "3DO/Plane/P-38G-5-LO(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar04());
    Property.set(localClass, "noseart", 1);
    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/P-38G-5-LO.fmd");

    Property.set(localClass, "cockpitClass", new Class[] { CockpitP_38G5LO.class });

    Property.set(localClass, "LOSElevation", 0.69215F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 9, 9, 3, 3, 9, 9, 2, 2, 2, 2, 2, 2, 9, 9, 2, 2, 2, 2, 2, 2, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 9, 9, 1, 1, 1, 1 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalDev03", "_ExternalDev04", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalDev05", "_ExternalDev06", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", "_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalDev07", "_ExternalDev08", "_ExternalRock23", "_ExternalRock24", "_ExternalRock25", "_ExternalRock26", "_ExternalRock27", "_ExternalRock28", "_ExternalRock29", "_ExternalRock30", "_ExternalRock31", "_ExternalRock32", "_ExternalRock33", "_ExternalRock34", "_ExternalRock35", "_ExternalRock36", "_ExternalDev09", "_ExternalDev10", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08" });

    ArrayList localArrayList = new ArrayList();
    Property.set(localClass, "weaponsList", localArrayList);
    HashMapInt localHashMapInt = new HashMapInt();
    Property.set(localClass, "weaponsMap", localHashMapInt);
    int i = 59;
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
    String str;
    try { str = "default";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIki", 150);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
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
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;

      for (int j = 59; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException1)
    {
      System.out.println("Error P38G-5-LO Weapons 'default ' NOT initialized:" + localException1.getMessage());
    }

    try
    {
      str = "droptanks";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIki", 150);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankP38", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankP38", 1);
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
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
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;

      for (int k = 59; k < i; k++)
        arrayOf_WeaponSlot[k] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException2)
    {
      System.out.println("Error P38G-5-LO Weapons 'droptanks ' NOT initialized:" + localException2.getMessage());
    }

    try
    {
      str = "1xdroptank1x500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIki", 150);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankP38", 1);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[9] = null;
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
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;

      for (int m = 59; m < i; m++)
        arrayOf_WeaponSlot[m] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException3)
    {
      System.out.println("Error P38G-5-LO Weapons '1xdroptank1x500 ' NOT initialized:" + localException3.getMessage());
    }

    try
    {
      str = "1xdroptank1x1000";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIki", 150);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankP38", 1);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[9] = null;
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
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;

      for (int n = 59; n < i; n++)
        arrayOf_WeaponSlot[n] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException4)
    {
      System.out.println("Error P38G-5-LO Weapons '1xdroptank1x1000 ' NOT initialized:" + localException4.getMessage());
    }

    try
    {
      str = "2x250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIki", 150);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
      arrayOf_WeaponSlot[9] = null;
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
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;

      for (int i1 = 59; i1 < i; i1++)
        arrayOf_WeaponSlot[i1] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException5)
    {
      System.out.println("Error P38G-5-LO Weapons '2x250 ' NOT initialized:" + localException5.getMessage());
    }

    try
    {
      str = "2x500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIki", 150);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[9] = null;
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
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;

      for (int i2 = 59; i2 < i; i2++)
        arrayOf_WeaponSlot[i2] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException6)
    {
      System.out.println("Error P38G-5-LO Weapons '2x500 ' NOT initialized:" + localException6.getMessage());
    }

    try
    {
      str = "2x1000";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIki", 150);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[9] = null;
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
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;

      for (int i3 = 59; i3 < i; i3++)
        arrayOf_WeaponSlot[i3] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException7)
    {
      System.out.println("Error P38G-5-LO Weapons '2x1000 ' NOT initialized:" + localException7.getMessage());
    }

    try
    {
      str = "2x154Napalm";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIki", 150);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGun154Napalm", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun154Napalm", 1);
      arrayOf_WeaponSlot[9] = null;
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
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;

      for (int i4 = 59; i4 < i; i4++)
        arrayOf_WeaponSlot[i4] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException8)
    {
      System.out.println("Error P38G5LO Weapons '2x154Napalm ' NOT initialized:" + localException8.getMessage());
    }

    try
    {
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
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;

      for (int i5 = 59; i5 < i; i5++)
        arrayOf_WeaponSlot[i5] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException9)
    {
      System.out.println("Error P38G-5-LO Weapons 'none ' NOT initialized:" + localException9.getMessage());
    }
  }
}