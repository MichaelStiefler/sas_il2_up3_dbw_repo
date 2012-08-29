package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import java.io.IOException;

public class BombsightNorden
{
  public static int TYPE_METRICK = 0;
  public static int TYPE_ENGLISH = 1;
  public static int TYPE_NAVAL = 2;
  public static int TypeCurrent = 0;
  public static float fSightCurAltitude;
  public static float fSightCurSpeed;
  public static float fSightCurForwardAngle;
  public static float fSightCurSideslip;
  public static float fSightCurReadyness;
  private static int nNumBombsToRelease;
  private static int nNumBombsReleased;
  private static float fBombDropDelay;
  private static float fDelayLeft;
  private static boolean bSightAutomation;
  private static boolean bSightBombDump;
  private static float fSightCurDistance;
  private static Aircraft currentCraft;
  private static BombDescriptor[] BombDescs = { new BombDescriptor("FAB-100", new double[] { 0.0002072D, 0.0001216D, 0.0001417D, 0.0001036D, 0.0001295D, 8.967E-005D, 0.0001107D, 0.0001108D, 9.13E-005D, 9.449E-005D, 9.347E-005D, 0.0001042D, 9.184E-005D, 0.0001318D, 0.0001329D, 0.0001268D, 0.0001261D }), new BombDescriptor("FAB-250", new double[] { 0.0002072D, 0.0002216D, 0.0002084D, 0.0002036D, 0.0002095D, 0.0001897D, 0.0001964D, 0.0001858D, 0.0001802D, 0.0001945D, 0.0002026D, 0.0002042D, 0.0002149D, 0.0002033D, 0.0002262D, 0.0002518D, 0.0002672D }), new BombDescriptor("FAB-500", new double[] { 0.0002072D, 0.0003216D, 0.0004084D, 0.0004536D, 0.0004895D, 0.000523D, 0.0005107D, 0.0005108D, 0.0005135D, 0.0004945D, 0.0005117D, 0.0005375D, 0.0005534D, 0.0005747D, 0.0005996000000000001D, 0.0006393D, 0.0006555000000000001D }), new BombDescriptor("FAB-1000", new double[] { 0.0002072D, 0.0001216D, 0.0001417D, 0.0001536D, 0.0001695D, 0.0001897D, 0.0001964D, 0.0001858D, 0.0002024D, 0.0002145D, 0.0002389D, 0.0002542D, 0.0002765D, 0.000289D, 0.0003062D, 0.0003143D, 0.0003261D }), new BombDescriptor("FAB-2000", new double[] { 7.229E-006D, 2.155E-005D, 7.503000000000001E-005D, 0.0001036D, 8.952E-005D, 0.000123D, 0.0001678D, 0.0001858D, 0.0002024D, 0.0002145D, 0.0002389D, 0.0002708D, 0.0003072D, 0.0003318D, 0.0003862D, 0.0004268D, 0.0004672D }), new BombDescriptor("FAB-5000", new double[] { 7.229E-006D, 2.155E-005D, 8.365E-006D, 3.615E-006D, 4.952E-005D, 5.634E-005D, 8.214E-005D, 0.0001108D, 0.0001135D, 0.0001345D, 0.0001662D, 0.0001875D, 0.0002149D, 0.0002318D, 0.0003062D, 0.0003893D, 0.0004672D }) };
  private static int nCurrentBombIndex;
  private static int nCurrentBombStringIndex;
  private static String[] ActiveBombNames = null;

  public BombsightNorden()
  {
    ResetAll(0, null);
  }

  private static final float toMeters(float paramFloat)
  {
    switch (TypeCurrent)
    {
    case 0:
      return paramFloat;
    case 1:
    case 2:
      return 0.3048F * paramFloat;
    }
    return 0.0F;
  }

  private static final float toMetersPerSecond(float paramFloat)
  {
    switch (TypeCurrent)
    {
    case 0:
      return paramFloat / 3.7F;
    case 1:
      return 0.4470401F * paramFloat;
    case 2:
      return 0.514F * paramFloat;
    }
    return 0.0F;
  }

  private static final float fromMeters(float paramFloat)
  {
    switch (TypeCurrent)
    {
    case 0:
      return paramFloat;
    case 1:
    case 2:
      return 3.2808F * paramFloat;
    }
    return 0.0F;
  }

  private static final float fromMetersPerSecond(float paramFloat)
  {
    switch (TypeCurrent)
    {
    case 0:
      return paramFloat * 3.6F;
    case 1:
      return paramFloat * 2.237F;
    case 2:
      return paramFloat * 1.946F;
    }
    return 0.0F;
  }

  private static void SetCurrentBombIndex()
  {
    nCurrentBombIndex = 0;

    if (null == ActiveBombNames) {
      return;
    }
    if (nCurrentBombStringIndex >= ActiveBombNames.length) {
      nCurrentBombStringIndex = 0;
    }
    for (int i = 0; i < BombDescs.length; i++)
    {
      if (!BombDescs[i].sBombName.equals(ActiveBombNames[nCurrentBombStringIndex]))
        continue;
      nCurrentBombIndex = i;
      break;
    }
  }

  public static void ResetAll(int paramInt, Aircraft paramAircraft)
  {
    TypeCurrent = paramInt;
    switch (TypeCurrent)
    {
    case 0:
      fSightCurAltitude = 3000.0F;
      fSightCurSpeed = 400.0F;
      break;
    case 1:
    case 2:
      fSightCurAltitude = 9000.0F;
      fSightCurSpeed = 250.0F;
      break;
    default:
      fSightCurAltitude = 0.0F;
      fSightCurSpeed = 0.0F;
    }
    fSightCurForwardAngle = 0.0F;
    fSightCurSideslip = 0.0F;

    fSightCurDistance = 0.0F;
    fSightCurReadyness = 0.0F;
    bSightAutomation = false;
    bSightBombDump = false;

    currentCraft = paramAircraft;

    nNumBombsToRelease = 0;
    nNumBombsReleased = 0;
    fBombDropDelay = 0.25F;
    fDelayLeft = 0.0F;

    nCurrentBombIndex = 0;
    nCurrentBombStringIndex = 0;
    SetCurrentBombIndex();
    RecalculateDistance();
  }

  public static void SetActiveBombNames(String[] paramArrayOfString)
  {
    ActiveBombNames = paramArrayOfString;
  }

  public static boolean ToggleAutomation()
  {
    bSightAutomation = !bSightAutomation;
    bSightBombDump = false;
    nNumBombsReleased = 0;
    fDelayLeft = 0.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAutomation" + (bSightAutomation ? "ON" : "OFF"));
    return bSightAutomation;
  }

  public static void AdjDistanceReset()
  {
    fSightCurDistance = 0.0F;
    fSightCurForwardAngle = 0.0F;
  }

  public static void AdjDistancePlus()
  {
    fSightCurForwardAngle += 1.0F;
    if (fSightCurForwardAngle > 85.0F)
      fSightCurForwardAngle = 85.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)fSightCurForwardAngle) });

    RecalculateDistance();
    if (bSightAutomation)
      ToggleAutomation();
  }

  public static void AdjDistanceMinus()
  {
    fSightCurForwardAngle -= 1.0F;
    if (fSightCurForwardAngle < 0.0F)
      fSightCurForwardAngle = 0.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)fSightCurForwardAngle) });

    RecalculateDistance();
    if (bSightAutomation)
      ToggleAutomation();
  }

  public static void AdjSideslipReset()
  {
    fSightCurSideslip = 0.0F;
  }

  public static void AdjSideslipPlus()
  {
    nNumBombsToRelease += 1;
    if (nNumBombsToRelease > 10)
      nNumBombsToRelease = 0;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "Number of bomb drops: " + nNumBombsToRelease);
  }

  public static void AdjSideslipMinus()
  {
    fBombDropDelay += 0.25F;
    if (fBombDropDelay > 5.0F)
      fBombDropDelay = 0.25F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "Bomb drop delay (sec): " + fBombDropDelay);
  }

  public static void AdjAltitudeReset()
  {
    switch (TypeCurrent)
    {
    case 0:
      fSightCurAltitude = 3000.0F;
      break;
    case 1:
    case 2:
      fSightCurAltitude = 9000.0F;
      break;
    default:
      fSightCurAltitude = 0.0F;
    }
    RecalculateDistance();
  }

  public static void AdjAltitudePlus()
  {
    switch (TypeCurrent)
    {
    case 0:
      fSightCurAltitude += 50.0F;
      if (fSightCurAltitude > 10000.0F)
        fSightCurAltitude = 10000.0F;
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)fSightCurAltitude) });

      break;
    case 1:
    case 2:
      fSightCurAltitude += 50.0F;
      if (fSightCurAltitude > 30000.0F)
        fSightCurAltitude = 30000.0F;
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new Object[] { new Integer((int)fSightCurAltitude) });

      break;
    }

    RecalculateDistance();
  }

  public static void AdjAltitudeMinus()
  {
    switch (TypeCurrent)
    {
    case 0:
      fSightCurAltitude -= 50.0F;
      if (fSightCurAltitude < 500.0F)
        fSightCurAltitude = 500.0F;
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)fSightCurAltitude) });

      break;
    case 1:
    case 2:
      fSightCurAltitude -= 50.0F;
      if (fSightCurAltitude < 1500.0F)
        fSightCurAltitude = 1500.0F;
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new Object[] { new Integer((int)fSightCurAltitude) });

      break;
    }

    RecalculateDistance();
  }

  public static void AdjSpeedReset()
  {
    switch (TypeCurrent)
    {
    case 0:
      fSightCurSpeed = 400.0F;
      break;
    case 1:
    case 2:
      fSightCurSpeed = 250.0F;
      break;
    default:
      fSightCurSpeed = 0.0F;
    }
  }

  public static void AdjSpeedPlus()
  {
    switch (TypeCurrent)
    {
    case 0:
      fSightCurSpeed += 5.0F;
      if (fSightCurSpeed > 600.0F)
        fSightCurSpeed = 600.0F;
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)fSightCurSpeed) });

      break;
    case 1:
    case 2:
      fSightCurSpeed += 5.0F;
      if (fSightCurSpeed > 450.0F)
        fSightCurSpeed = 450.0F;
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH", new Object[] { new Integer((int)fSightCurSpeed) });

      break;
    }
  }

  public static void AdjSpeedMinus()
  {
    switch (TypeCurrent)
    {
    case 0:
      fSightCurSpeed -= 5.0F;
      if (fSightCurSpeed < 150.0F)
        fSightCurSpeed = 150.0F;
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)fSightCurSpeed) });

      break;
    case 1:
    case 2:
      fSightCurSpeed -= 5.0F;
      if (fSightCurSpeed < 100.0F)
        fSightCurSpeed = 100.0F;
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH", new Object[] { new Integer((int)fSightCurSpeed) });

      break;
    }
  }

  public static void RecalculateDistance()
  {
    fSightCurDistance = toMeters(fSightCurAltitude) * (float)Math.tan(Math.toRadians(fSightCurForwardAngle));
  }

  public static void OnCCIP(float paramFloat1, float paramFloat2)
  {
    fSightCurSpeed = fromMetersPerSecond(paramFloat1);
    fSightCurAltitude = fromMeters(paramFloat2);
    RecalculateDistance();
  }

  public static void Update(float paramFloat)
  {
    if (null == currentCraft)
      return;
    if (Math.abs(currentCraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Or.getKren()) > 4.5D)
    {
      fSightCurReadyness -= 0.0666666F * paramFloat;
      if (fSightCurReadyness < 0.0F)
        fSightCurReadyness = 0.0F;
    }
    if (fSightCurReadyness < 1.0F) {
      fSightCurReadyness += 0.0333333F * paramFloat;
    } else if (bSightAutomation)
    {
      double d1 = toMetersPerSecond(fSightCurSpeed);
      double d2 = toMeters(fSightCurAltitude);
      fSightCurDistance = (float)(fSightCurDistance - d1 * paramFloat);
      if (fSightCurDistance < 0.0F)
      {
        fSightCurDistance = 0.0F;
        ToggleAutomation();
        return;
      }
      fSightCurForwardAngle = (float)Math.toDegrees(Math.atan(fSightCurDistance / d2));

      double d3 = d1 * Math.sqrt(d2 * 0.203873598D);
      double d4 = BombDescs[nCurrentBombIndex].GetCorrectionCoeff(d2);
      d3 += d4 * d2 * d1;
      if (fSightCurDistance < d3)
        bSightBombDump = true;
      if (bSightBombDump)
      {
        if ((currentCraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (currentCraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][(currentCraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3].length - 1)] != null) && (currentCraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][(currentCraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3].length - 1)].haveBullets()))
        {
          if (fDelayLeft <= 0.0F)
          {
            fDelayLeft = fBombDropDelay;
            if ((nNumBombsReleased < nNumBombsToRelease) || (0 == nNumBombsToRelease))
            {
              currentCraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[3] = true;
              HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightBombdrop");
              nNumBombsReleased += 1;
            }
            else
            {
              currentCraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[3] = false;
              ToggleAutomation();
            }
          }
          else
          {
            fDelayLeft -= paramFloat;
            currentCraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[3] = false;
          }
        }
        else
        {
          currentCraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[3] = false;
          ToggleAutomation();
        }
      }
    }
  }

  public static void ReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
    paramNetMsgGuaranted.writeByte((bSightAutomation ? 1 : 0) | (bSightBombDump ? 2 : 0));
    paramNetMsgGuaranted.writeFloat(fSightCurDistance);
    paramNetMsgGuaranted.writeByte((int)fSightCurForwardAngle);
    paramNetMsgGuaranted.writeByte((int)((fSightCurSideslip + 3.0F) * 33.333328F));
    paramNetMsgGuaranted.writeFloat(fSightCurAltitude);
    paramNetMsgGuaranted.writeByte((int)(fSightCurSpeed / 2.5F));
    paramNetMsgGuaranted.writeByte((int)(fSightCurReadyness * 200.0F));
  }

  public static void ReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException
  {
    int i = paramNetMsgInput.readUnsignedByte();
    bSightAutomation = (i & 0x1) != 0;
    bSightBombDump = (i & 0x2) != 0;
    fSightCurDistance = paramNetMsgInput.readFloat();
    fSightCurForwardAngle = paramNetMsgInput.readUnsignedByte();
    fSightCurSideslip = -3.0F + paramNetMsgInput.readUnsignedByte() / 33.333328F;
    fSightCurAltitude = paramNetMsgInput.readFloat();
    fSightCurSpeed = paramNetMsgInput.readUnsignedByte() * 2.5F;
    fSightCurReadyness = paramNetMsgInput.readUnsignedByte() / 200.0F;
  }
}