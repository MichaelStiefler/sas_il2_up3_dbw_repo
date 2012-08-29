package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Finger;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.util.ArrayList;

public class TB_3_4M_17_T_DZZ extends TB_3
  implements TypeTransport
{
  public float fSightCurAltitude = 300.0F;
  public float fSightCurSpeed = 50.0F;

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    float f1 = -paramArrayOfFloat[0];
    float f2 = paramArrayOfFloat[1];
    float f3 = Math.abs(f1);
    switch (paramInt)
    {
    case 0:
      if (f2 < -47.0F)
      {
        f2 = -47.0F;
        bool = false;
      }
      if (f2 > 47.0F)
      {
        f2 = 47.0F;
        bool = false;
      }
      if (f3 < 147.0F)
      {
        if (f2 < 0.5964912F * f3 - 117.68421F)
        {
          f2 = 0.5964912F * f3 - 117.68421F;
          bool = false;
        }
      }
      else if (f3 < 157.0F)
      {
        if (f2 < 0.3F * f3 - 74.099998F)
        {
          f2 = 0.3F * f3 - 74.099998F;
          bool = false;
        }
      }
      else if (f2 < 0.217391F * f3 - 61.130436F)
      {
        f2 = 0.217391F * f3 - 61.130436F;
        bool = false;
      }
      if (f3 < 110.0F)
        break;
      if (f3 < 115.0F)
      {
        if ((f2 >= -5.0F) || (f2 <= -20.0F)) break;
        bool = false;
      }
      else if (f3 < 160.0F)
      {
        if (f2 >= -5.0F) break;
        bool = false;
      } else {
        if (f2 >= 15.0F) break;
        bool = false; } break;
    case 1:
      if (f2 < -47.0F)
      {
        f2 = -47.0F;
        bool = false;
      }
      if (f2 > 47.0F)
      {
        f2 = 47.0F;
        bool = false;
      }
      if (f1 < -38.0F)
      {
        if (f2 < -32.0F)
        {
          f2 = -32.0F;
          bool = false;
        }
      }
      else if (f1 < -16.0F)
      {
        if (f2 < 0.5909091F * f1 - 9.545455F)
        {
          f2 = 0.5909091F * f1 - 9.545455F;
          bool = false;
        }
      }
      else if (f1 < 35.0F)
      {
        if (f2 < -19.0F)
        {
          f2 = -19.0F;
          bool = false;
        }
      }
      else if (f1 < 44.0F)
      {
        if (f2 < -3.111111F * f1 + 89.888885F)
        {
          f2 = -3.111111F * f1 + 89.888885F;
          bool = false;
        }
      }
      else if (f1 < 139.0F)
      {
        if (f2 < -47.0F)
        {
          f2 = -47.0F;
          bool = false;
        }
      }
      else if (f1 < 150.0F)
      {
        if (f2 < 1.363636F * f1 - 236.54546F)
        {
          f2 = 1.363636F * f1 - 236.54546F;
          bool = false;
        }
      }
      else if (f2 < -32.0F)
      {
        f2 = -32.0F;
        bool = false;
      }
      if (f1 < -175.7F)
      {
        if (f2 >= 80.800003F) break;
        bool = false;
      }
      else if (f1 < -167.0F)
      {
        if (f2 >= 0.0F) break;
        bool = false;
      }
      else if (f1 < -124.8F)
      {
        if (f2 >= -22.799999F) break;
        bool = false;
      }
      else if (f1 < -82.0F)
      {
        if (f2 >= -16.0F) break;
        bool = false;
      }
      else if (f1 < 24.0F)
      {
        if (f2 >= 0.0F) break;
        bool = false;
      }
      else if (f1 < 32.0F)
      {
        if (f2 >= -8.3F) break;
        bool = false;
      }
      else if (f1 < 80.0F)
      {
        if (f2 >= 0.0F) break;
        bool = false;
      }
      else if (f1 < 174.0F)
      {
        if (f2 >= 0.5F * f1 - 87.0F) break;
        bool = false;
      }
      else if (f1 < 178.7F)
      {
        if (f2 >= 0.0F) break;
        bool = false;
      } else {
        if (f2 >= 80.800003F) break;
        bool = false; } break;
    case 2:
      if (f2 < -47.0F)
      {
        f2 = -47.0F;
        bool = false;
      }
      if (f2 > 47.0F)
      {
        f2 = 47.0F;
        bool = false;
      }
      if (f1 < -68.0F)
      {
        if (f2 < -32.0F)
        {
          f2 = -32.0F;
          bool = false;
        }
      }
      else if (f1 < -22.0F)
      {
        if (f2 < 0.5347826F * f1 + 4.365217F)
        {
          f2 = 0.5347826F * f1 + 4.365217F;
          bool = false;
        }
      }
      else if (f1 < 27.0F)
      {
        if (f2 < -0.3387755F * f1 - 14.853062F)
        {
          f2 = -0.3387755F * f1 - 14.853062F;
          bool = false;
        }
      }
      else if (f1 < 40.0F)
      {
        if (f2 < -1.769231F * f1 + 23.76923F)
        {
          f2 = -1.769231F * f1 + 23.76923F;
          bool = false;
        }
      }
      else if (f1 < 137.0F)
      {
        if (f2 < -47.0F)
        {
          f2 = -47.0F;
          bool = false;
        }
      }
      else if (f1 < 152.0F)
      {
        if (f2 < 1.0F * f1 - 184.0F)
        {
          f2 = 1.0F * f1 - 184.0F;
          bool = false;
        }
      }
      else if (f2 < -32.0F)
      {
        f2 = -32.0F;
        bool = false;
      }
      if (f1 < -172.0F)
      {
        if (f2 >= 2.0F) break;
        bool = false;
      }
      else if (f1 < -123.0F)
      {
        if (f2 >= 30.0F) break;
        bool = false;
      }
      else if (f1 < -102.0F)
      {
        if (f2 >= 0.0F) break;
        bool = false;
      }
      else if (f1 < -36.0F)
      {
        if (f2 >= -9.0F) break;
        bool = false;
      }
      else if (f1 < -5.1F)
      {
        if (f2 >= 0.0F) break;
        bool = false;
      }
      else if (f1 < -1.2F)
      {
        if (f2 >= 24.5F) break;
        bool = false;
      }
      else if (f1 < 62.0F)
      {
        if (f2 >= -0.7436709F * f1 - 0.892496F)
          break;
        f2 = -0.7436709F * f1 - 0.892496F;
        bool = false;
      }
      else if (f1 < 103.0F)
      {
        if (f2 >= -47.0F) break;
        bool = false;
      } else {
        if (f2 >= 0.0F) break;
        bool = false;
      }
    }
    paramArrayOfFloat[0] = (-f1);
    paramArrayOfFloat[1] = f2;
    return bool;
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean)
    {
      for (int i = 0; i < 4; i++)
      {
        if ((this.FM.AS.astateEngineStates[i] <= 3) || (this.FM.EI.engines[i].getReadyness() >= 0.1F))
          continue;
        this.FM.AS.repairEngine(i);
      }
      for (i = 0; i < 4; i++)
      {
        if ((this.FM.AS.astateTankStates[i] <= 3) || (this.FM.AS.astatePilotStates[4] >= 50.0F) || (this.FM.AS.astatePilotStates[7] >= 50.0F) || (World.Rnd().nextFloat() >= 0.1F))
        {
          continue;
        }
        this.FM.AS.repairTank(i);
      }
    }
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -this.FM.Gears.gWheelAngles[0], 0.0F);

    hierMesh().chunkSetAngles("GearR3_D0", 0.0F, -this.FM.Gears.gWheelAngles[1], 0.0F);
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    setExplosion(paramExplosion);
    if ((paramExplosion.chunkName == null) || ((!paramExplosion.chunkName.startsWith("Wing")) && (!paramExplosion.chunkName.startsWith("Tail"))) || (!paramExplosion.chunkName.endsWith("D3")) || (paramExplosion.power >= 0.014F))
    {
      super.msgExplosion(paramExplosion);
    }
  }

  public boolean typeBomberToggleAutomation() {
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
    this.fSightCurAltitude = 300.0F;
  }

  public void typeBomberAdjAltitudePlus()
  {
    this.fSightCurAltitude += 50.0F;
    if (this.fSightCurAltitude > 5000.0F)
      this.fSightCurAltitude = 5000.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });
  }

  public void typeBomberAdjAltitudeMinus()
  {
    this.fSightCurAltitude -= 50.0F;
    if (this.fSightCurAltitude < 300.0F)
      this.fSightCurAltitude = 300.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });
  }

  public void typeBomberAdjSpeedReset()
  {
    this.fSightCurSpeed = 50.0F;
  }

  public void typeBomberAdjSpeedPlus()
  {
    this.fSightCurSpeed += 5.0F;
    if (this.fSightCurSpeed > 350.0F)
      this.fSightCurSpeed = 350.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberAdjSpeedMinus()
  {
    this.fSightCurSpeed -= 5.0F;
    if (this.fSightCurSpeed < 50.0F)
      this.fSightCurSpeed = 50.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberUpdate(float paramFloat)
  {
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
    paramNetMsgGuaranted.writeFloat(this.fSightCurAltitude);
    paramNetMsgGuaranted.writeFloat(this.fSightCurSpeed);
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException
  {
    this.fSightCurAltitude = paramNetMsgInput.readFloat();
    this.fSightCurSpeed = paramNetMsgInput.readFloat();
  }

  private static Aircraft._WeaponSlot[] GenerateDefaultConfig(int paramInt)
  {
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[paramInt];
    try
    {
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunDA762t", 1196);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunDA762t4d", 1156);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(11, "MGunDA762t", 1196);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(11, "MGunDA762t4d", 1156);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(12, "MGunDA762t", 1196);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(12, "MGunDA762t4d", 1156);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
    } catch (Exception localException) {
    }
    return arrayOf_WeaponSlot;
  }

  static
  {
    Class localClass = TB_3_4M_17_T_DZZ.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "TB-3");
    Property.set(localClass, "meshName", "3DO/Plane/TB-3-4M-17/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar00());
    Property.set(localClass, "yearService", 1932.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/TB-3-4M-17.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitTB_3.class, CockpitTB_3_Bombardier_T_DZZ.class, CockpitTB_3_NGunner.class, CockpitTB_3_TGunner1.class, CockpitTB_3_TGunner2.class });

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 10, 11, 11, 12, 12, 9, 9, 9, 9, 3, 3, 3, 3, 3, 3, 3, 3, 9, 9, 9, 9, 3, 3, 3, 3, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11", "_ExternalBomb12", "_BombSpawn01", "_BombSpawn02" });
    try
    {
      int i = 0;

      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int j = 30;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = GenerateDefaultConfig(j);

      for (int k = 14; k < j; k++) {
        arrayOf_WeaponSlot[k] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2cargoa";
      arrayOf_WeaponSlot = GenerateDefaultConfig(j);
      for (i = 14; i < j; i++)
        arrayOf_WeaponSlot[i] = null;
      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(3, "BombGunCargoA", 1);
      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(3, "BombGunCargoA", 1);

      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "4cargoa";
      arrayOf_WeaponSlot = GenerateDefaultConfig(j);
      for (i = 14; i < j; i++)
        arrayOf_WeaponSlot[i] = null;
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(3, "BombGunCargoA", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(3, "BombGunCargoA", 1);
      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(3, "BombGunCargoA", 1);
      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(3, "BombGunCargoA", 1);

      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "6cargoa";
      arrayOf_WeaponSlot = GenerateDefaultConfig(j);
      for (i = 14; i < j; i++)
        arrayOf_WeaponSlot[i] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(3, "BombGunCargoA", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(3, "BombGunCargoA", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(3, "BombGunCargoA", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(3, "BombGunCargoA", 1);
      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(3, "BombGunCargoA", 1);
      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(3, "BombGunCargoA", 1);

      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "1xagent";
      arrayOf_WeaponSlot = GenerateDefaultConfig(j);
      for (i = 14; i < j; i++)
        arrayOf_WeaponSlot[i] = null;
      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(3, "BombGunPara", 1);

      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2xagent";
      arrayOf_WeaponSlot = GenerateDefaultConfig(j);
      for (i = 14; i < j; i++)
        arrayOf_WeaponSlot[i] = null;
      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(3, "BombGunPara", 2);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "3xagent";
      arrayOf_WeaponSlot = GenerateDefaultConfig(j);
      for (i = 14; i < j; i++)
        arrayOf_WeaponSlot[i] = null;
      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(3, "BombGunPara", 3);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "4xagent";
      arrayOf_WeaponSlot = GenerateDefaultConfig(j);
      for (i = 14; i < j; i++)
        arrayOf_WeaponSlot[i] = null;
      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(3, "BombGunPara", 4);

      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "30xpara";
      arrayOf_WeaponSlot = GenerateDefaultConfig(j);
      for (i = 14; i < j; i++)
        arrayOf_WeaponSlot[i] = null;
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(3, "BombGunPara", 30);

      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[j];
      for (k = 0; k < j; k++) {
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