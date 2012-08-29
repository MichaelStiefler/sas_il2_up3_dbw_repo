package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Finger;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.util.ArrayList;

public class JU_52_3MG6E extends JU_52
  implements TypeBomber
{
  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    if (paramArrayOfFloat[0] < -50.0F)
    {
      paramArrayOfFloat[0] = -50.0F;
      bool = false;
    }
    else if (paramArrayOfFloat[0] > 50.0F)
    {
      paramArrayOfFloat[0] = 50.0F;
      bool = false;
    }
    float f = Math.abs(paramArrayOfFloat[0]);
    if (f < 20.0F)
    {
      if (paramArrayOfFloat[1] < -1.0F)
      {
        paramArrayOfFloat[1] = -1.0F;
        bool = false;
      }
    }
    else if (paramArrayOfFloat[1] < -5.0F)
    {
      paramArrayOfFloat[1] = -5.0F;
      bool = false;
    }
    if (paramArrayOfFloat[1] > 45.0F)
    {
      paramArrayOfFloat[1] = 45.0F;
      bool = false;
    }
    return bool;
  }

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);
    if ((paramShot.chunkName.startsWith("WingLIn")) && (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, 1);
    if ((paramShot.chunkName.startsWith("WingRIn")) && (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 1, 1);
    if ((paramShot.chunkName.startsWith("Engine1")) && (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 1);
    if ((paramShot.chunkName.startsWith("Engine2")) && (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 1, 1);
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[0] > 2) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[1] > 2))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setCapableOfBMP(false, paramShot.initiator);
    super.msgShot(paramShot);
  }

  public boolean typeBomberToggleAutomation()
  {
    return false;
  }

  public void typeBomberAdjDistanceReset()
  {
  }

  public void typeBomberAdjDistancePlus()
  {
  }

  public void typeBomberAdjDistanceMinus()
  {
  }

  public void typeBomberAdjSideslipReset()
  {
  }

  public void typeBomberAdjSideslipPlus()
  {
  }

  public void typeBomberAdjSideslipMinus()
  {
  }

  public void typeBomberAdjAltitudeReset()
  {
  }

  public void typeBomberAdjAltitudePlus()
  {
  }

  public void typeBomberAdjAltitudeMinus()
  {
  }

  public void typeBomberAdjSpeedReset()
  {
  }

  public void typeBomberAdjSpeedPlus()
  {
  }

  public void typeBomberAdjSpeedMinus()
  {
  }

  public void typeBomberUpdate(float paramFloat)
  {
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
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunMG15t", 250);
    } catch (Exception localException) {
    }
    return arrayOf_WeaponSlot;
  }

  static
  {
    Class localClass = JU_52_3MG6E.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "FlightModel", "FlightModels/Ju-52_3mg4e.fmd");
    Property.set(localClass, "meshName", "3do/plane/Ju-52_3mg4e/hier.him");
    Property.set(localClass, "iconFar_shortClassName", "Ju-52");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "cockpitClass", new Class[] { CockpitJU524E.class, CockpitJU525E_GunnerOpen.class });

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_BombSpawn01" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 2;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      for (int j = 1; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "18xPara";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(3, "BombGunPara", 18);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2xCargoA";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(3, "BombGunCargoA", 2);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "5xCargoA";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(3, "BombGunCargoA", 5);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "1xagent";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(3, "BombGunPara", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2xagent";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(3, "BombGunPara", 2);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "3xagent";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(3, "BombGunPara", 3);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "4xagent";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(3, "BombGunPara", 4);
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