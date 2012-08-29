package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Finger;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.util.ArrayList;

public class TBM1 extends TBF
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

  public TBM1()
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

  protected void moveFlap(float paramFloat)
  {
    float f = -38.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap04_D0", 0.0F, f, 0.0F);
  }

  protected void moveWingFold(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("WingLMid_D0", 0.0F, -75.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("WingRMid_D0", 0.0F, 75.0F * paramFloat, 0.0F);
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

  public void moveCockpitDoor(float paramFloat)
  {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.625F);
    Aircraft.xyz[2] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.06845F);
    Aircraft.ypr[2] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 1.0F);
    hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
    resetYPRmodifier();
    Aircraft.xyz[2] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.13F);
    Aircraft.ypr[2] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, -8.0F);
    hierMesh().chunkSetLocate("Pilot1_D0", Aircraft.xyz, Aircraft.ypr);
    if (Config.isUSE_RENDER())
    {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null))
        Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      setDoorSnd(paramFloat);
    }
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
      if (f2 > 89.0F)
      {
        f2 = 89.0F;
        bool = false;
      }
      if (f2 < -30.0F)
      {
        f2 = -30.0F;
        bool = false;
      }
      float f3 = Math.abs(f1);
      if (f2 >= Aircraft.cvt(f3, 137.0F, 180.0F, -1.0F, 46.0F)) break;
      f2 = Aircraft.cvt(f3, 137.0F, 180.0F, -1.0F, 46.0F); break;
    case 1:
      if (f1 < -23.0F)
      {
        f1 = -23.0F;
        bool = false;
      }
      if (f1 > 39.0F)
      {
        f1 = 39.0F;
        bool = false;
      }
      if (f2 < -60.0F)
      {
        f2 = -60.0F;
        bool = false;
      }
      if (f2 <= 31.0F)
        break;
      f2 = 31.0F;
      bool = false;
    }

    paramArrayOfFloat[0] = (-f1);
    paramArrayOfFloat[1] = f2;
    return bool;
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
    if (this.fSightCurForwardAngle > 87.0F)
      this.fSightCurForwardAngle = 87.0F;
    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });

    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjDistanceMinus()
  {
    this.fSightCurForwardAngle -= 1.0F;
    if (this.fSightCurForwardAngle < -45.0F)
      this.fSightCurForwardAngle = -45.0F;
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
    this.fSightCurAltitude += 10.0F;
    if (this.fSightCurAltitude > 50000.0F)
      this.fSightCurAltitude = 50000.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new Object[] { new Integer((int)this.fSightCurAltitude) });

    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjAltitudeMinus()
  {
    this.fSightCurAltitude -= 10.0F;
    if (this.fSightCurAltitude < 500.0F)
      this.fSightCurAltitude = 500.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new Object[] { new Integer((int)this.fSightCurAltitude) });

    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjSpeedReset()
  {
    this.fSightCurSpeed = 200.0F;
  }

  public void typeBomberAdjSpeedPlus()
  {
    this.fSightCurSpeed += 2.0F;
    if (this.fSightCurSpeed > 450.0F)
      this.fSightCurSpeed = 450.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberAdjSpeedMinus()
  {
    this.fSightCurSpeed -= 2.0F;
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
    Class localClass = TBM1.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "TBM-1");
    Property.set(localClass, "meshName", "3DO/Plane/TBF-1(Multi1)/TBM1.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar01());
    Property.set(localClass, "meshName_us", "3DO/Plane/TBF-1(USA)/TBM1.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeBMPar01());
    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1946.5F);
    Property.set(localClass, "FlightModel", "FlightModels/TBM1.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitTBM.class, CockpitTBM1_Bombardier.class, CockpitTBM1_TGunner.class });

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 10, 11, 3, 3, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 10;
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      String str1 = "default";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303si", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 400);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(11, "MGunBrowning303t", 500);
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      localArrayList.add(str1);
      localHashMapInt.put(Finger.Int(str1), arrayOf_WeaponSlot);
      String str2 = "6x100";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303si", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 400);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(11, "MGunBrowning303t", 500);
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGunFAB50", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunFAB50", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunFAB50", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunFAB50", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunFAB50", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(3, "BombGunFAB50", 1);
      localArrayList.add(str2);
      localHashMapInt.put(Finger.Int(str2), arrayOf_WeaponSlot);
      String str3 = "12x100";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303si", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 400);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(11, "MGunBrowning303t", 500);
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGunFAB50", 2);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunFAB50", 2);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunFAB50", 2);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunFAB50", 2);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunFAB50", 2);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(3, "BombGunFAB50", 2);
      localArrayList.add(str3);
      localHashMapInt.put(Finger.Int(str3), arrayOf_WeaponSlot);
      String str4 = "2x500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303si", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 400);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(11, "MGunBrowning303t", 500);
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      localArrayList.add(str4);
      localHashMapInt.put(Finger.Int(str4), arrayOf_WeaponSlot);
      String str5 = "4x500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303si", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 400);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(11, "MGunBrowning303t", 500);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      localArrayList.add(str5);
      localHashMapInt.put(Finger.Int(str5), arrayOf_WeaponSlot);
      String str6 = "1x1000";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303si", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 400);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(11, "MGunBrowning303t", 500);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      localArrayList.add(str6);
      localHashMapInt.put(Finger.Int(str6), arrayOf_WeaponSlot);
      String str7 = "2x1000";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303si", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 400);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(11, "MGunBrowning303t", 500);
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      localArrayList.add(str7);
      localHashMapInt.put(Finger.Int(str7), arrayOf_WeaponSlot);
      String str8 = "1x1600";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303si", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 400);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(11, "MGunBrowning303t", 500);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGun1600lbs", 1);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      localArrayList.add(str8);
      localHashMapInt.put(Finger.Int(str8), arrayOf_WeaponSlot);
      String str9 = "1x2000";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303si", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 400);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(11, "MGunBrowning303t", 500);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGun2000lbs", 1);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      localArrayList.add(str9);
      localHashMapInt.put(Finger.Int(str9), arrayOf_WeaponSlot);
      String str10 = "1xtorp";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303si", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 400);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(11, "MGunBrowning303t", 500);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGunTorpMK13_1", 1);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      localArrayList.add(str10);
      localHashMapInt.put(Finger.Int(str10), arrayOf_WeaponSlot);
      str1 = "none";
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
      localArrayList.add(str1);
      localHashMapInt.put(Finger.Int(str1), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}