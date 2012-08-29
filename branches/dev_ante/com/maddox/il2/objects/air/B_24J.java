package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Finger;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.util.ArrayList;

public class B_24J extends B_24
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

  public B_24J()
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
      if (f1 < -85.0F)
      {
        f1 = -85.0F;
        bool = false;
      }
      if (f1 > 85.0F)
      {
        f1 = 85.0F;
        bool = false;
      }
      if (f2 < -32.0F)
      {
        f2 = -32.0F;
        bool = false;
      }
      if (f2 <= 46.0F)
        break;
      f2 = 46.0F;
      bool = false; break;
    case 1:
      if (f2 < -0.0F)
      {
        f2 = -0.0F;
        bool = false;
      }
      if (f2 <= 20.0F)
        break;
      f2 = 20.0F;
      bool = false; break;
    case 2:
      if (f2 < -70.0F)
      {
        f2 = -70.0F;
        bool = false;
      }
      if (f2 <= 7.0F)
        break;
      f2 = 7.0F;
      bool = false; break;
    case 3:
      if (f1 < -35.0F)
      {
        f1 = -35.0F;
        bool = false;
      }
      if (f1 > 64.0F)
      {
        f1 = 64.0F;
        bool = false;
      }
      if (f2 < -37.0F)
      {
        f2 = -37.0F;
        bool = false;
      }
      if (f2 <= 50.0F)
        break;
      f2 = 50.0F;
      bool = false; break;
    case 4:
      if (f1 < -67.0F)
      {
        f1 = -67.0F;
        bool = false;
      }
      if (f1 > 34.0F)
      {
        f1 = 34.0F;
        bool = false;
      }
      if (f2 < -37.0F)
      {
        f2 = -37.0F;
        bool = false;
      }
      if (f2 <= 50.0F)
        break;
      f2 = 50.0F;
      bool = false; break;
    case 5:
      if (f1 < -85.0F)
      {
        f1 = -85.0F;
        bool = false;
      }
      if (f1 > 85.0F)
      {
        f1 = 85.0F;
        bool = false;
      }
      if (f2 < -32.0F)
      {
        f2 = -32.0F;
        bool = false;
      }
      if (f2 <= 46.0F)
        break;
      f2 = 46.0F;
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
          if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.Weapons[3] != null) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.Weapons[3][(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.Weapons[3].length - 1)] != null) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.Weapons[3][(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.Weapons[3].length - 1)].haveBullets()))
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.WeaponControl[3] = true;
            HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightBombdrop");
          }
        }
        else
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.WeaponControl[3] = false;
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

  static
  {
    Class localClass = B_24J.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "B-24");
    Property.set(localClass, "meshName", "3DO/Plane/B-24J-100-CF(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar05());
    Property.set(localClass, "meshName_us", "3DO/Plane/B-24J-100-CF(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar06());
    Property.set(localClass, "noseart", 1);
    Property.set(localClass, "yearService", 1943.5F);
    Property.set(localClass, "yearExpired", 2800.8999F);
    Property.set(localClass, "FlightModel", "FlightModels/B-24J.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitB_24J.class, CockpitB_24J_Bombardier.class, CockpitB_24J_FGunner.class, CockpitB_24J_TGunner.class, CockpitB_24J_AGunner.class, CockpitB_24J_BGunner.class, CockpitB_24J_RGunner.class, CockpitB_24J_LGunner.class });

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 10, 11, 11, 12, 12, 13, 14, 15, 15, 3, 3, 3, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_MGUN09", "_MGUN10", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_BombSpawn08" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 18;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 365);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 365);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(12, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(12, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(13, "MGunBrowning50t", 375);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(14, "MGunBrowning50t", 375);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(15, "MGunBrowning50t", 500);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(15, "MGunBrowning50t", 500);
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      for (int j = 18; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "16x250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 365);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 365);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(12, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(12, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(13, "MGunBrowning50t", 375);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(14, "MGunBrowning50t", 375);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(15, "MGunBrowning50t", 500);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(15, "MGunBrowning50t", 500);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 2);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 2);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 2);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 2);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 2);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 2);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 2);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 2);
      for (int k = 18; k < i; k++) {
        arrayOf_WeaponSlot[k] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "16x500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 365);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 365);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(12, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(12, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(13, "MGunBrowning50t", 375);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(14, "MGunBrowning50t", 375);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(15, "MGunBrowning50t", 500);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(15, "MGunBrowning50t", 500);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 2);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 2);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 2);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 2);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 2);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 2);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 2);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 2);
      for (int m = 18; m < i; m++) {
        arrayOf_WeaponSlot[m] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "8x1000";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 365);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 365);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(12, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(12, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(13, "MGunBrowning50t", 375);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(14, "MGunBrowning50t", 375);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(15, "MGunBrowning50t", 500);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(15, "MGunBrowning50t", 500);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      for (int n = 18; n < i; n++) {
        arrayOf_WeaponSlot[n] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "6x1600";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 365);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 365);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(12, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(12, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(13, "MGunBrowning50t", 375);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(14, "MGunBrowning50t", 375);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(15, "MGunBrowning50t", 500);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(15, "MGunBrowning50t", 500);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun1600lbs", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun1600lbs", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(3, "BombGun1600lbs", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(3, "BombGun1600lbs", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(3, "BombGun1600lbs", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(3, "BombGun1600lbs", 1);
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      for (int i1 = 18; i1 < i; i1++) {
        arrayOf_WeaponSlot[i1] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "4x2000";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 365);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 365);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(12, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(12, "MGunBrowning50t", 610);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(13, "MGunBrowning50t", 375);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(14, "MGunBrowning50t", 375);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(15, "MGunBrowning50t", 500);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(15, "MGunBrowning50t", 500);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun2000lbs", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun2000lbs", 1);
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(3, "BombGun2000lbs", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(3, "BombGun2000lbs", 1);
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      for (int i2 = 18; i2 < i; i2++) {
        arrayOf_WeaponSlot[i2] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int i3 = 0; i3 < i; i3++) {
        arrayOf_WeaponSlot[i3] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}