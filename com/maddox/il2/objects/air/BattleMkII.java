package com.maddox.il2.objects.air;

import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Finger;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.util.ArrayList;

public class BattleMkII extends FaireyBattle
  implements TypeBomber
{
  private float bpos;
  private float bcurpos;
  private long btme;

  public BattleMkII()
  {
    this.bpos = 1.0F;
    this.bcurpos = 1.0F;
    this.btme = -1L;
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    float f1 = -paramArrayOfFloat[0];
    float f2 = paramArrayOfFloat[1];
    switch (paramInt)
    {
    case 0:
      if (f1 < -31.0F)
      {
        f1 = -31.0F;
        bool = false;
      }
      if (f1 > 31.0F)
      {
        f1 = 31.0F;
        bool = false;
      }
      if (f2 < -10.0F)
      {
        f2 = -10.0F;
        bool = false;
      }
      if (f2 <= 52.0F)
        break;
      f2 = 52.0F;
      bool = false;
    }

    paramArrayOfFloat[0] = (-f1);
    paramArrayOfFloat[1] = f2;
    return bool;
  }

  public boolean typeBomberToggleAutomation()
  {
    return BombsightNorden.ToggleAutomation();
  }

  public void typeBomberAdjDistanceReset()
  {
    BombsightNorden.AdjDistanceReset();
  }

  public void typeBomberAdjDistancePlus()
  {
    BombsightNorden.AdjDistancePlus();
  }

  public void typeBomberAdjDistanceMinus()
  {
    BombsightNorden.AdjDistanceMinus();
  }

  public void typeBomberAdjSideslipReset()
  {
    BombsightNorden.AdjSideslipReset();
  }

  public void typeBomberAdjSideslipPlus()
  {
    BombsightNorden.AdjSideslipPlus();
  }

  public void typeBomberAdjSideslipMinus()
  {
    BombsightNorden.AdjSideslipMinus();
  }

  public void typeBomberAdjAltitudeReset()
  {
    BombsightNorden.AdjAltitudeReset();
  }

  public void typeBomberAdjAltitudePlus()
  {
    BombsightNorden.AdjAltitudePlus();
  }

  public void typeBomberAdjAltitudeMinus()
  {
    BombsightNorden.AdjAltitudeMinus();
  }

  public void typeBomberAdjSpeedReset()
  {
    BombsightNorden.AdjSpeedReset();
  }

  public void typeBomberAdjSpeedPlus()
  {
    BombsightNorden.AdjSpeedPlus();
  }

  public void typeBomberAdjSpeedMinus()
  {
    BombsightNorden.AdjSpeedMinus();
  }

  public void typeBomberUpdate(float paramFloat)
  {
    BombsightNorden.Update(paramFloat);
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException
  {
  }

  private static Aircraft._WeaponSlot[] GenerateDefaultConfig(int paramInt)
  {
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[paramInt];
    try
    {
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 400);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 485);
    } catch (Exception localException) {
    }
    return arrayOf_WeaponSlot;
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if (this.FM.isPlayers())
    {
      BombsightNorden.SetActiveBombNames(new String[] { "FAB-100" });

      BombsightNorden.ResetAll(1, this);
    }
  }

  static
  {
    Class localClass = BattleMkII.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "FaireyBattle");
    Property.set(localClass, "meshName", "3DO/Plane/FaireyBattle(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar00());
    Property.set(localClass, "yearService", 1937.0F);
    Property.set(localClass, "yearExpired", 1941.5F);
    Property.set(localClass, "FlightModel", "FlightModels/BattleMkII.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitBattleMkII.class, Cockpit_BombsightNordenSimple.class, CockpitBattleMkII_TGunner.class });

    Property.set(localClass, "LOSElevation", 0.7394F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 10, 9, 3, 9, 3, 9, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev02", "_ExternalBomb02", "_ExternalDev03", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 16;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      for (int j = 2; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "4x250lb";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "1x500lb";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(9, "PylonB5NPLN1", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
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
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "6x250lb";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
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