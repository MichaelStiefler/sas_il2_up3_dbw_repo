package com.maddox.il2.objects.air;

import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class SM79i extends SM79
  implements TypeBomber, TypeTransport
{
  public float fSightCurAltitude;
  public float fSightCurSpeed;

  public SM79i()
  {
    this.fSightCurAltitude = 850.0F;
    this.fSightCurSpeed = 150.0F;
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    float f1 = paramArrayOfFloat[0];
    float f2 = paramArrayOfFloat[1];
    switch (paramInt)
    {
    default:
      break;
    case 3:
      if (f1 < -45.0F)
      {
        f1 = -45.0F;
        bool = false;
      }
      if (f1 > 60.0F)
      {
        f1 = 60.0F;
        bool = false;
      }
      if (f2 < -35.0F)
      {
        f2 = -35.0F;
        bool = false;
      }
      if (f2 <= 35.0F)
        break;
      f2 = 35.0F;
      bool = false; break;
    case 0:
      if (f1 < -40.0F)
      {
        f1 = -40.0F;
        bool = false;
      }
      if (f1 > 40.0F)
      {
        f1 = 40.0F;
        bool = false;
      }
      if (f2 < -4.0F)
      {
        f2 = -4.0F;
        bool = false;
      }
      if (f2 <= 70.0F)
        break;
      f2 = 70.0F;
      bool = false; break;
    case 1:
      float f3 = 10.0F;
      float f4 = 1.0F;
      float f5 = 15.0F;
      if (f1 < -40.0F)
      {
        f1 = -40.0F;
        bool = false;
      }
      if (f1 > 40.0F)
      {
        f1 = 40.0F;
        bool = false;
      }
      if (f2 < 0.0F)
      {
        f2 = 0.0F;
        bool = false;
      }
      if (f2 <= 45.0F)
        break;
      f2 = 45.0F;
      bool = false; break;
    case 2:
      if (f1 < -60.0F)
      {
        f1 = -60.0F;
        bool = false;
      }
      if (f1 > 45.0F)
      {
        f1 = 45.0F;
        bool = false;
      }
      if (f2 < -35.0F)
      {
        f2 = -35.0F;
        bool = false;
      }
      if (f2 <= 35.0F)
        break;
      f2 = 35.0F;
      bool = false;
    }

    paramArrayOfFloat[0] = f1;
    paramArrayOfFloat[1] = f2;
    return bool;
  }

  public boolean typeBomberToggleAutomation()
  {
    return false;
  }

  public void typeBomberAdjDistanceReset()
  {
    this.jdField_fSightCurForwardAngle_of_type_Float = 0.0F;
  }

  public void typeBomberAdjDistancePlus()
  {
    this.jdField_fSightCurForwardAngle_of_type_Float += 0.4F;
    if (this.jdField_fSightCurForwardAngle_of_type_Float > 75.0F)
      this.jdField_fSightCurForwardAngle_of_type_Float = 75.0F;
  }

  public void typeBomberAdjDistanceMinus()
  {
    this.jdField_fSightCurForwardAngle_of_type_Float -= 0.4F;
    if (this.jdField_fSightCurForwardAngle_of_type_Float < -15.0F)
      this.jdField_fSightCurForwardAngle_of_type_Float = -15.0F;
  }

  public void typeBomberAdjSideslipReset()
  {
    this.jdField_fSightCurSideslip_of_type_Float = 0.0F;
  }

  public void typeBomberAdjSideslipPlus()
  {
    this.jdField_fSightCurSideslip_of_type_Float = (float)(this.jdField_fSightCurSideslip_of_type_Float + 0.5D);
    if (this.jdField_thisWeaponsName_of_type_JavaLangString.startsWith("1x"))
    {
      if (this.jdField_fSightCurSideslip_of_type_Float > 40.0F)
        this.jdField_fSightCurSideslip_of_type_Float = 40.0F;
      HUD.log(AircraftHotKeys.hudLogWeaponId, "Torpedo Angle  " + this.jdField_fSightCurSideslip_of_type_Float);
    }
    else
    {
      if (this.jdField_fSightCurSideslip_of_type_Float > 10.0F)
        this.jdField_fSightCurSideslip_of_type_Float = 10.0F;
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip " + this.jdField_fSightCurSideslip_of_type_Float);
    }
  }

  public void typeBomberAdjSideslipMinus()
  {
    this.jdField_fSightCurSideslip_of_type_Float = (float)(this.jdField_fSightCurSideslip_of_type_Float - 0.5D);

    if (this.jdField_thisWeaponsName_of_type_JavaLangString.startsWith("1x"))
    {
      if (this.jdField_fSightCurSideslip_of_type_Float < -40.0F)
        this.jdField_fSightCurSideslip_of_type_Float = -40.0F;
      HUD.log(AircraftHotKeys.hudLogWeaponId, "Torpedo Angle  " + this.jdField_fSightCurSideslip_of_type_Float);
    }
    else
    {
      if (this.jdField_fSightCurSideslip_of_type_Float < -10.0F)
        this.jdField_fSightCurSideslip_of_type_Float = -10.0F;
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip " + this.jdField_fSightCurSideslip_of_type_Float);
    }
  }

  public void typeBomberAdjAltitudeReset()
  {
    this.fSightCurAltitude = 300.0F;
  }

  public void typeBomberAdjAltitudePlus()
  {
    this.fSightCurAltitude += 10.0F;
    if (this.fSightCurAltitude > 6000.0F)
      this.fSightCurAltitude = 6000.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });
  }

  public void typeBomberAdjAltitudeMinus()
  {
    this.fSightCurAltitude -= 10.0F;
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
    if (this.fSightCurSpeed > 650.0F)
      this.fSightCurSpeed = 650.0F;
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
    double d = this.fSightCurSpeed / 3.6D * Math.sqrt(this.fSightCurAltitude * 0.203873598D);

    d -= this.fSightCurAltitude * this.fSightCurAltitude * 1.419E-005D;
    this.fSightSetForwardAngle = (float)Math.atan(d / this.fSightCurAltitude);
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
    paramNetMsgGuaranted.writeFloat(this.fSightCurAltitude);
    paramNetMsgGuaranted.writeFloat(this.fSightCurSpeed);
    paramNetMsgGuaranted.writeFloat(this.jdField_fSightCurForwardAngle_of_type_Float);
    paramNetMsgGuaranted.writeFloat(this.jdField_fSightCurSideslip_of_type_Float);
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException
  {
    this.fSightCurAltitude = paramNetMsgInput.readFloat();
    this.fSightCurSpeed = paramNetMsgInput.readFloat();
    this.jdField_fSightCurForwardAngle_of_type_Float = paramNetMsgInput.readFloat();
    this.jdField_fSightCurSideslip_of_type_Float = paramNetMsgInput.readFloat();
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "SM.79");

    Property.set(localClass, "meshName_it", "3do/plane/SM79-I(it)/hier.him");
    Property.set(localClass, "PaintScheme_it", new PaintSchemeBMPar09());

    Property.set(localClass, "meshName", "3do/plane/SM79-I(multi)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar09());

    Property.set(localClass, "yearService", 1937.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/SM79.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitSM79.class, CockpitSM79_Bombardier.class, CockpitSM79_TGunner.class, CockpitSM79_BGunner.class, CockpitSM79_LGunner.class, CockpitSM79_RGunner.class });

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 10, 13, 12, 11, 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_12,7_01", "_12,7_02", "_12,7_00", "_12,7_04", "_12,7_03", "_ExternalDev01", "_ExternalBomb01", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawnC01", "_BombSpawnC02", "_BombSpawnC03", "_BombSpawnC04", "_BombSpawnC05", "_BombSpawnC06", "_BombSpawnC07", "_BombSpawnC08", "_BombSpawnC09", "_BombSpawnC10", "_BombSpawnC11", "_BombSpawnC12" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM127s 500", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM127s 500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "12x100_delay_drop", new String[] { "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM127s 500", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM127s 500", null, null, "BombGunIT_100_M 12", null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "12x50_delay_drop", new String[] { "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM127s 500", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM127s 500", null, null, "BombGunIT_50_M 12", null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "12xCassette", new String[] { "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM127s 500", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM127s 500", null, null, null, null, null, "BombGunSpezzoniera 56", "BombGunSpezzoniera 56", "BombGunSpezzoniera 56", "BombGunSpezzoniera 56", "BombGunSpezzoniera 56", "BombGunSpezzoniera 56", "BombGunSpezzoniera 56", "BombGunSpezzoniera 56", "BombGunSpezzoniera 56", "BombGunSpezzoniera 56", "BombGunSpezzoniera 56", "BombGunSpezzoniera 56" });

    Aircraft.weaponsRegister(localClass, "6x100_delay_drop", new String[] { "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM127s 500", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM127s 500", null, null, "BombGunIT_100_M 6", null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "5x250_delay_drop", new String[] { "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM127s 500", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM127s 500", null, null, null, "BombGunIT_250_T 5", null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x500_delay_drop", new String[] { "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM127s 500", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM127s 500", null, null, null, null, "BombGunIT_500_T 2", null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1xLTW_Torp", new String[] { "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM127s 500", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM127s 500", null, "BombGunTorpFiume 1", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}