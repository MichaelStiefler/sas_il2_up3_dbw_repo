package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Finger;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.util.ArrayList;

public class P2V5 extends P2V
  implements TypeBomber
{
  private float bpos;
  private float bcurpos;
  private long btme;
  public static boolean bChangedPit = false;
  private boolean bSightAutomation;
  private boolean bSightBombDump;
  private float fSightCurDistance;
  public float fSightCurForwardAngle;
  public float fSightCurSideslip;
  public float fSightCurAltitude;
  public float fSightCurSpeed;
  public float fSightCurReadyness;

  public P2V5()
  {
    this.bpos = 1.0F;
    this.bcurpos = 1.0F;
    this.btme = -1L;
    this.bSightAutomation = false;
    this.bSightBombDump = false;
    this.fSightCurDistance = 0.0F;
    this.fSightCurForwardAngle = 0.0F;
    this.fSightCurSideslip = 0.0F;
    this.fSightCurAltitude = 3000.0F;
    this.fSightCurSpeed = 200.0F;
    this.fSightCurReadyness = 0.0F;
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isMaster())
    {
      if (this.bpos == 0.0F)
      {
        if (this.bcurpos > this.bpos)
        {
          this.bcurpos -= 0.2F * paramFloat;
          if (this.bcurpos < 0.0F)
            this.bcurpos = 0.0F;
        }
        resetYPRmodifier();
        Aircraft.xyz[1] = (-0.31F + 0.31F * this.bcurpos);
        hierMesh().chunkSetLocate("Turret3A_D0", Aircraft.xyz, Aircraft.ypr);
      }
      else if (this.bpos == 1.0F)
      {
        if (this.bcurpos < this.bpos)
        {
          this.bcurpos += 0.2F * paramFloat;
          if (this.bcurpos > 1.0F)
          {
            this.bcurpos = 1.0F;
            this.bpos = 0.5F;
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = true;
          }
        }
        resetYPRmodifier();
        Aircraft.xyz[1] = (-0.3F + 0.3F * this.bcurpos);
        hierMesh().chunkSetLocate("Turret3A_D0", Aircraft.xyz, Aircraft.ypr);
      }
      if (Time.current() > this.btme)
      {
        this.btme = (Time.current() + World.Rnd().nextLong(5000L, 12000L));
        if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].target == null)
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = false;
          this.bpos = 0.0F;
        }
        if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].target != null) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astatePilotStates[4] < 90))
          this.bpos = 1.0F;
      }
    }
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

  public void doKillPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 2:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
      break;
    case 3:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = false;
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
      if (f2 <= 88.0F)
        break;
      f2 = 88.0F;
      bool = false; break;
    case 2:
      if (f2 < -88.0F)
      {
        f2 = -88.0F;
        bool = false;
      }
      if (f2 <= 2.0F)
        break;
      f2 = 2.0F;
      bool = false;
    }

    paramArrayOfFloat[0] = (-f1);
    paramArrayOfFloat[1] = f2;
    return bool;
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
    Class localClass = P2V5.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "P2V5");
    Property.set(localClass, "meshName", "3DO/Plane/P2V5(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "meshName_us", "3DO/Plane/P2V5(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeBMPar02());
    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1956.6F);
    Property.set(localClass, "FlightModel", "FlightModels/P-2V.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitP2V5.class, CockpitP2V5_Bombardier.class, CockpitP2V5_FGunner.class, CockpitP2V5_TGunner.class });

    Property.set(localClass, "LOSElevation", 0.73425F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 11, 11, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN03", "_MGUN04", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 25;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
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
      for (int j = 25; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "06x500lb";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 3);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 3);
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
      for (int k = 25; k < i; k++) {
        arrayOf_WeaponSlot[k] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "06x500lb+16xHVARRockets";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 3);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 3);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      for (int m = 25; m < i; m++) {
        arrayOf_WeaponSlot[m] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "08x500lb";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 4);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 4);
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
      for (int n = 25; n < i; n++) {
        arrayOf_WeaponSlot[n] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "10x500lb";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 5);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 5);
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
      for (int i1 = 25; i1 < i; i1++) {
        arrayOf_WeaponSlot[i1] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "04x1000lb";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 2);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 2);
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
      for (int i2 = 25; i2 < i; i2++) {
        arrayOf_WeaponSlot[i2] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "04x1000lb+16xHVARRockets";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 2);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 2);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      for (int i3 = 25; i3 < i; i3++) {
        arrayOf_WeaponSlot[i3] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "02x2000lb";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(3, "BombGun2000lbs", 2);
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
      for (int i4 = 25; i4 < i; i4++) {
        arrayOf_WeaponSlot[i4] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "16xHVARRockets";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      for (int i5 = 25; i5 < i; i5++) {
        arrayOf_WeaponSlot[i5] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "Mk34Torpedo";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(3, "BombGunTorpMk34", 2);
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
      for (int i6 = 25; i6 < i; i6++) {
        arrayOf_WeaponSlot[i6] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "Mk34Torpedo+16xHVARRockets";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(3, "BombGunTorpMk34", 2);
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      for (int i7 = 25; i7 < i; i7++) {
        arrayOf_WeaponSlot[i7] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "12xMk53Charge";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGunMk53Charge", 6);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGunMk53Charge", 6);
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
      for (int i8 = 25; i8 < i; i8++) {
        arrayOf_WeaponSlot[i8] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "12xMk53Charge+16xHVARRockets";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGunMk53Charge", 6);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGunMk53Charge", 6);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      for (int i9 = 25; i9 < i; i9++) {
        arrayOf_WeaponSlot[i9] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "04x1000lb+06xMk24Flare";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(2, "BombGunMk24Flare", 6);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 4);
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
      for (int i10 = 25; i10 < i; i10++) {
        arrayOf_WeaponSlot[i10] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "Mk34Torpedo+06xMk24Flare";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(2, "BombGunMk24Flare", 6);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGunTorpMk34", 2);
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
      for (int i11 = 25; i11 < i; i11++) {
        arrayOf_WeaponSlot[i11] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "12xMk53Charge+06xMk24Flare";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(2, "BombGunMk24Flare", 6);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGunMk53Charge", 6);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGunMk53Charge", 6);
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
      for (int i12 = 25; i12 < i; i12++) {
        arrayOf_WeaponSlot[i12] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "04x1000lb+06xMk25Marker";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(2, "BombGunMk25Marker", 6);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 4);
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
      for (int i13 = 25; i13 < i; i13++) {
        arrayOf_WeaponSlot[i13] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "Mk34Torpedo+06xMk25Marker";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(2, "BombGunMk25Marker", 6);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGunTorpMk34", 2);
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
      for (int i14 = 25; i14 < i; i14++) {
        arrayOf_WeaponSlot[i14] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "12xMk53Charge+06xMk25Marker";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 450);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(2, "BombGunMk25Marker", 6);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGunMk53Charge", 6);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGunMk53Charge", 6);
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
      for (int i15 = 25; i15 < i; i15++) {
        arrayOf_WeaponSlot[i15] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "None";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int i16 = 0; i16 < i; i16++) {
        arrayOf_WeaponSlot[i16] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}