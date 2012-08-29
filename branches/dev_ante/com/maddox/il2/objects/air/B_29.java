package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class B_29 extends B_29X
  implements TypeBomber
{
  public static boolean bChangedPit = false;
  private boolean bSightAutomation;
  private boolean bSightBombDump;
  private float fSightCurDistance;
  public float fSightCurForwardAngle;
  public float fSightCurSideslip;
  public float fSightCurAltitude;
  public float fSightCurSpeed;
  public float fSightCurReadyness;

  public B_29()
  {
    this.bSightAutomation = false;
    this.bSightBombDump = false;
    this.fSightCurDistance = 0.0F;
    this.fSightCurForwardAngle = 0.0F;
    this.fSightCurSideslip = 0.0F;
    this.fSightCurAltitude = 3000.0F;
    this.fSightCurSpeed = 200.0F;
    this.fSightCurReadyness = 0.0F;
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1)
    {
    case 19:
      killPilot(this, 4);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    for (int i = 1; i < 7; i++)
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

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

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    float f1 = -paramArrayOfFloat[0];
    float f2 = paramArrayOfFloat[1];
    switch (paramInt)
    {
    default:
      break;
    case 0:
      if (f1 < -23.0F)
      {
        f1 = -23.0F;
        bool = false;
      }
      if (f1 > 23.0F)
      {
        f1 = 23.0F;
        bool = false;
      }
      if (f2 < -25.0F)
      {
        f2 = -25.0F;
        bool = false;
      }
      if (f2 <= 15.0F)
        break;
      f2 = 15.0F;
      bool = false; break;
    case 1:
      if (f2 < 0.0F)
      {
        f2 = 0.0F;
        bool = false;
      }
      if (f2 <= 73.0F)
        break;
      f2 = 73.0F;
      bool = false; break;
    case 2:
      if (f1 < -38.0F)
      {
        f1 = -38.0F;
        bool = false;
      }
      if (f1 > 38.0F)
      {
        f1 = 38.0F;
        bool = false;
      }
      if (f2 < -41.0F)
      {
        f2 = -41.0F;
        bool = false;
      }
      if (f2 <= 43.0F)
        break;
      f2 = 43.0F;
      bool = false; break;
    case 3:
      if (f1 < -85.0F)
      {
        f1 = -85.0F;
        bool = false;
      }
      if (f1 > 22.0F)
      {
        f1 = 22.0F;
        bool = false;
      }
      if (f2 < -40.0F)
      {
        f2 = -40.0F;
        bool = false;
      }
      if (f2 <= 32.0F)
        break;
      f2 = 32.0F;
      bool = false; break;
    case 4:
      if (f1 < -34.0F)
      {
        f1 = -34.0F;
        bool = false;
      }
      if (f1 > 30.0F)
      {
        f1 = 30.0F;
        bool = false;
      }
      if (f2 < -30.0F)
      {
        f2 = -30.0F;
        bool = false;
      }
      if (f2 <= 32.0F)
        break;
      f2 = 32.0F;
      bool = false;
    }

    paramArrayOfFloat[0] = (-f1);
    paramArrayOfFloat[1] = f2;
    return bool;
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 2:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
      break;
    case 3:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
      break;
    case 4:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = false;
      break;
    case 5:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[3].bIsOperable = false;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[4].bIsOperable = false;
    }
  }

  private static final float toMeters(float paramFloat)
  {
    return 0.3048F * paramFloat;
  }

  private static final float toMetersPerSecond(float paramFloat)
  {
    return 0.4470401F * paramFloat;
  }

  public boolean typeBomberToggleAutomation()
  {
    this.bSightAutomation = (!this.bSightAutomation);
    this.bSightBombDump = false;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAutomation" + (this.bSightAutomation ? "ON" : "OFF"));
    return this.bSightAutomation;
  }

  public void typeBomberAdjDistanceReset()
  {
    this.fSightCurDistance = 0.0F;
    this.fSightCurForwardAngle = 0.0F;
  }

  public void typeBomberAdjDistancePlus()
  {
    this.fSightCurForwardAngle += 1.0F;
    if (this.fSightCurForwardAngle > 85.0F)
      this.fSightCurForwardAngle = 85.0F;
    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });

    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjDistanceMinus()
  {
    this.fSightCurForwardAngle -= 1.0F;
    if (this.fSightCurForwardAngle < 0.0F)
      this.fSightCurForwardAngle = 0.0F;
    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });

    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjSideslipReset()
  {
    this.fSightCurSideslip = 0.0F;
  }

  public void typeBomberAdjSideslipPlus()
  {
    this.fSightCurSideslip += 0.1F;
    if (this.fSightCurSideslip > 3.0F)
      this.fSightCurSideslip = 3.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 10.0F)) });
  }

  public void typeBomberAdjSideslipMinus()
  {
    this.fSightCurSideslip -= 0.1F;
    if (this.fSightCurSideslip < -3.0F)
      this.fSightCurSideslip = -3.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 10.0F)) });
  }

  public void typeBomberAdjAltitudeReset()
  {
    this.fSightCurAltitude = 3000.0F;
  }

  public void typeBomberAdjAltitudePlus()
  {
    this.fSightCurAltitude += 50.0F;
    if (this.fSightCurAltitude > 50000.0F)
      this.fSightCurAltitude = 50000.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new Object[] { new Integer((int)this.fSightCurAltitude) });

    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjAltitudeMinus()
  {
    this.fSightCurAltitude -= 50.0F;
    if (this.fSightCurAltitude < 1000.0F)
      this.fSightCurAltitude = 1000.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new Object[] { new Integer((int)this.fSightCurAltitude) });

    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjSpeedReset()
  {
    this.fSightCurSpeed = 200.0F;
  }

  public void typeBomberAdjSpeedPlus()
  {
    this.fSightCurSpeed += 10.0F;
    if (this.fSightCurSpeed > 450.0F)
      this.fSightCurSpeed = 450.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberAdjSpeedMinus()
  {
    this.fSightCurSpeed -= 10.0F;
    if (this.fSightCurSpeed < 100.0F)
      this.fSightCurSpeed = 100.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberUpdate(float paramFloat)
  {
    if (Math.abs(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Or.getKren()) > 4.5D)
    {
      this.fSightCurReadyness -= 0.0666666F * paramFloat;
      if (this.fSightCurReadyness < 0.0F)
        this.fSightCurReadyness = 0.0F;
    }
    if (this.fSightCurReadyness < 1.0F) {
      this.fSightCurReadyness += 0.0333333F * paramFloat;
    }
    else if (this.bSightAutomation)
    {
      this.fSightCurDistance -= toMetersPerSecond(this.fSightCurSpeed) * paramFloat;
      if (this.fSightCurDistance < 0.0F)
      {
        this.fSightCurDistance = 0.0F;
        typeBomberToggleAutomation();
      }
      this.fSightCurForwardAngle = (float)Math.toDegrees(Math.atan(this.fSightCurDistance / toMeters(this.fSightCurAltitude)));
      if (this.fSightCurDistance < toMetersPerSecond(this.fSightCurSpeed) * Math.sqrt(toMeters(this.fSightCurAltitude) * 0.203874F))
        this.bSightBombDump = true;
      if (this.bSightBombDump)
        if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isTick(3, 0))
        {
          if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3].length - 1)] != null) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3].length - 1)].haveBullets()))
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[3] = true;
            HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightBombdrop");
          }
        }
        else
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[3] = false;
    }
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
    paramNetMsgGuaranted.writeByte((this.bSightAutomation ? 1 : 0) | (this.bSightBombDump ? 2 : 0));
    paramNetMsgGuaranted.writeFloat(this.fSightCurDistance);
    paramNetMsgGuaranted.writeByte((int)this.fSightCurForwardAngle);
    paramNetMsgGuaranted.writeByte((int)((this.fSightCurSideslip + 3.0F) * 33.333328F));
    paramNetMsgGuaranted.writeFloat(this.fSightCurAltitude);
    paramNetMsgGuaranted.writeByte((int)(this.fSightCurSpeed / 2.5F));
    paramNetMsgGuaranted.writeByte((int)(this.fSightCurReadyness * 200.0F));
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException
  {
    int i = paramNetMsgInput.readUnsignedByte();
    this.bSightAutomation = ((i & 0x1) != 0);
    this.bSightBombDump = ((i & 0x2) != 0);
    this.fSightCurDistance = paramNetMsgInput.readFloat();
    this.fSightCurForwardAngle = paramNetMsgInput.readUnsignedByte();
    this.fSightCurSideslip = (-3.0F + paramNetMsgInput.readUnsignedByte() / 33.333328F);
    this.fSightCurAltitude = paramNetMsgInput.readFloat();
    this.fSightCurSpeed = (paramNetMsgInput.readUnsignedByte() * 2.5F);
    this.fSightCurReadyness = (paramNetMsgInput.readUnsignedByte() / 200.0F);
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
    Class localClass = B_29.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "B-29");
    Property.set(localClass, "meshName", "3DO/Plane/B-29(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar05());
    Property.set(localClass, "meshName_us", "3DO/Plane/B-29(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar06());
    Property.set(localClass, "noseart", 1);
    Property.set(localClass, "yearService", 1943.5F);
    Property.set(localClass, "yearExpired", 2800.8999F);
    Property.set(localClass, "FlightModel", "FlightModels/B-29.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitB29.class, CockpitB29_Bombardier.class, CockpitB29_TGunner.class, CockpitB29_T2Gunner.class, CockpitB29_AGunner.class });

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 10, 10, 10, 11, 11, 12, 12, 13, 13, 14, 14, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_MGUN09", "_MGUN10", "_MGUN11", "_MGUN12", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1x1600", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, null, "BombGun1600lbs 1" });

    Aircraft.weaponsRegister(localClass, "6x300", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, "BombGun300lbs 3", "BombGun300lbs 3" });

    Aircraft.weaponsRegister(localClass, "20x100", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun50kg 3", "BombGun50kg 3", "BombGun50kg 7", "BombGun50kg 7" });

    Aircraft.weaponsRegister(localClass, "4x500", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, "BombGun500lbs 2", "BombGun500lbs 2" });

    Aircraft.weaponsRegister(localClass, "2x1000", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, "BombGun1000lbs 1", "BombGun1000lbs 1" });

    Aircraft.weaponsRegister(localClass, "1x2000", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, null, "BombGun2000lbs 1" });

    Aircraft.weaponsRegister(localClass, "4x1000", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, "BombGun1000lbs 2", "BombGun1000lbs 2" });

    Aircraft.weaponsRegister(localClass, "2x2000", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, "BombGun2000lbs 1", "BombGun2000lbs 1" });

    Aircraft.weaponsRegister(localClass, "16x300", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun300lbs 8", "BombGun300lbs 8", null, null });

    Aircraft.weaponsRegister(localClass, "10x500", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun500lbs 5", "BombGun500lbs 5", null, null });

    Aircraft.weaponsRegister(localClass, "20x250", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun250lbs 8", "BombGun250lbs 8", "BombGun250lbs 2", "BombGun250lbs 2" });

    Aircraft.weaponsRegister(localClass, "6x1600", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun1600lbs 1", "BombGun1600lbs 1", "BombGun1600lbs 2", "BombGun1600lbs 2" });

    Aircraft.weaponsRegister(localClass, "20x500", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun500lbs 8", "BombGun500lbs 8", "BombGun500lbs 2", "BombGun500lbs 2" });

    Aircraft.weaponsRegister(localClass, "12x1000", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun1000lbs 1", "BombGun1000lbs 1", "BombGun1000lbs 2", "BombGun1000lbs 2" });

    Aircraft.weaponsRegister(localClass, "6x2000", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun2000lbs 1", "BombGun2000lbs 1", "BombGun2000lbs 2", "BombGun2000lbs 2" });

    Aircraft.weaponsRegister(localClass, "12x1600", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun1600lbs 1", "BombGun1600lbs 1", "BombGun1600lbs 2", "BombGun1600lbs 2" });

    Aircraft.weaponsRegister(localClass, "20x1000", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun1000lbs 8", "BombGun1000lbs 8", "BombGun1000lbs 2", "BombGun1000lbs 2" });

    Aircraft.weaponsRegister(localClass, "10x2000", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun2000lbs 3", "BombGun2000lbs 3", "BombGun2000lbs 2", "BombGun2000lbs 2" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}