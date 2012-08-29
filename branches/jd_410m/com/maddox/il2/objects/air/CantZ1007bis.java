package com.maddox.il2.objects.air;

import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class CantZ1007bis extends CantZ1007
  implements TypeBomber, TypeTransport
{
  public float fSightCurAltitude;
  public float fSightCurSpeed;

  public CantZ1007bis()
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
      if (f2 < -7.0F)
      {
        f2 = -7.0F;
        bool = false;
      }
      if (f2 <= 80.0F)
        break;
      f2 = 80.0F;
      bool = false; break;
    case 1:
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
      if (f2 < -25.0F)
      {
        f2 = -25.0F;
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
    this.fSightCurForwardAngle = 0.0F;
  }

  public void typeBomberAdjDistancePlus()
  {
    this.fSightCurForwardAngle += 0.4F;
    if (this.fSightCurForwardAngle > 75.0F)
      this.fSightCurForwardAngle = 75.0F;
  }

  public void typeBomberAdjDistanceMinus()
  {
    this.fSightCurForwardAngle -= 0.4F;
    if (this.fSightCurForwardAngle < -15.0F)
      this.fSightCurForwardAngle = -15.0F;
  }

  public void typeBomberAdjSideslipReset()
  {
    this.fSightCurSideslip = 0.0F;
  }

  public void typeBomberAdjSideslipPlus()
  {
    this.fSightCurSideslip = (float)(this.fSightCurSideslip + 0.5D);
    if (this.thisWeaponsName.startsWith("1x"))
    {
      if (this.fSightCurSideslip > 40.0F)
        this.fSightCurSideslip = 40.0F;
      HUD.log(AircraftHotKeys.hudLogWeaponId, "Torpedo Angle  " + this.fSightCurSideslip);
    }
    else
    {
      if (this.fSightCurSideslip > 10.0F)
        this.fSightCurSideslip = 10.0F;
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip " + this.fSightCurSideslip);
    }
  }

  public void typeBomberAdjSideslipMinus()
  {
    this.fSightCurSideslip = (float)(this.fSightCurSideslip - 0.5D);

    if (this.thisWeaponsName.startsWith("1x"))
    {
      if (this.fSightCurSideslip < -40.0F)
        this.fSightCurSideslip = -40.0F;
      HUD.log(AircraftHotKeys.hudLogWeaponId, "Torpedo Angle  " + this.fSightCurSideslip);
    }
    else
    {
      if (this.fSightCurSideslip < -10.0F)
        this.fSightCurSideslip = -10.0F;
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip " + this.fSightCurSideslip);
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
    paramNetMsgGuaranted.writeFloat(this.fSightCurForwardAngle);
    paramNetMsgGuaranted.writeFloat(this.fSightCurSideslip);
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException
  {
    this.fSightCurAltitude = paramNetMsgInput.readFloat();
    this.fSightCurSpeed = paramNetMsgInput.readFloat();
    this.fSightCurForwardAngle = paramNetMsgInput.readFloat();
    this.fSightCurSideslip = paramNetMsgInput.readFloat();
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

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "CantZ");

    Property.set(localClass, "meshName_it", "3do/plane/CantZ1007bis(it)/hier.him");
    Property.set(localClass, "PaintScheme_it", new PaintSchemeBMPar09());

    Property.set(localClass, "meshName", "3do/plane/CantZ1007bis(multi)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar09());

    Property.set(localClass, "yearService", 1940.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/CantZ1007.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 13, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_BombSpawn08", "_BombSpawn09", "_BombSpawn10", "_BombSpawn11", "_BombSpawn12", "_BombSpawn13", "_BombSpawn14", "_BombSpawn15", "_BombSpawn16", "_BombSpawn17", "_BombSpawn18", "_BombSpawn19", "_BombSpawn20", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10" });

    weaponsRegister(localClass, "default", new String[] { "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "12x50", new String[] { "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "12x100", new String[] { "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "6x100", new String[] { "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", "BombGunIT_100Kg", "BombGunNull", "BombGunIT_100Kg", "BombGunNull", "BombGunIT_100Kg", "BombGunNull", "BombGunIT_100Kg", "BombGunNull", "BombGunIT_100Kg", "BombGunNull", "BombGunIT_100Kg", "BombGunNull", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2x250+3x100", new String[] { "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, null, null, "BombGunNull", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_250Kg", "BombGunNull", "BombGunNull", "BombGunIT_250Kg", null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2x500", new String[] { "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunIT_500Kg", "BombGunNull", "BombGunNull", "BombGunIT_500Kg", null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2x250+3x100+3x100x2wing", new String[] { "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, null, null, "BombGunNull", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_250Kg", "BombGunNull", "BombGunNull", "BombGunIT_250Kg", null, null, null, null, "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", null, null, null, null });

    weaponsRegister(localClass, "2x250+3x100+3x50x2wing", new String[] { "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, null, null, "BombGunNull", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_250Kg", "BombGunNull", "BombGunNull", "BombGunIT_250Kg", null, null, null, null, "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", null, null, null, null });

    weaponsRegister(localClass, "2x250+1x250x2wing", new String[] { "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, null, null, null, null, null, null, "BombGunIT_250Kg", "BombGunNull", "BombGunNull", "BombGunIT_250Kg", null, null, null, null, null, null, null, null, null, null, "BombGunIT_250Kg", "BombGunIT_250Kg", null, null });

    weaponsRegister(localClass, "2x250+2x250x2wing", new String[] { "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, null, null, null, null, null, null, "BombGunIT_250Kg", "BombGunNull", "BombGunNull", "BombGunIT_250Kg", null, null, null, null, null, null, null, null, null, null, "BombGunIT_250Kg", "BombGunIT_250Kg", "BombGunIT_250Kg", "BombGunIT_250Kg" });

    weaponsRegister(localClass, "2x500+3x50x2wing", new String[] { "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunIT_500Kg", "BombGunNull", "BombGunNull", "BombGunIT_500Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", null, null, null, null });

    weaponsRegister(localClass, "2x500+3x100x2wing", new String[] { "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunIT_500Kg", "BombGunNull", "BombGunNull", "BombGunIT_500Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", null, null, null, null });

    weaponsRegister(localClass, "2x500+1x250x2wing", new String[] { "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunIT_500Kg", "BombGunNull", "BombGunNull", "BombGunIT_500Kg", null, null, null, null, null, null, "BombGunIT_250Kg", "BombGunIT_250Kg", null, null });

    weaponsRegister(localClass, "2xMotobombaFFF", new String[] { "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunTorpFFF", "BombGunNull", "BombGunNull", "BombGunTorpFFF1", null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}