package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Finger;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.util.ArrayList;

public class B_25D5 extends B_25_Strafer
  implements TypeBomber, TypeStormovik, TypeStormovikArmored
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

  public B_25D5()
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
    this.bpos = 1.0F;
    this.bcurpos = 1.0F;
    this.btme = -1L;
    bChangedPit = false;
    this.bSightAutomation = false;
    this.bSightBombDump = false;
    this.fSightCurDistance = 0.0F;
    this.fSightCurForwardAngle = 0.0F;
    this.fSightCurSideslip = 0.0F;
    this.fSightCurAltitude = 3000.0F;
    this.fSightCurSpeed = 200.0F;
    this.fSightCurReadyness = 0.0F;
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("Head1_D0", false);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      break;
    case 3:
      hierMesh().chunkVisible("Pilot4_D0", false);
      hierMesh().chunkVisible("HMask4_D0", false);
      hierMesh().chunkVisible("Pilot4_D1", true);
      break;
    case 5:
      hierMesh().chunkVisible("Pilot6_D0", false);
      hierMesh().chunkVisible("HMask6_D0", false);
      hierMesh().chunkVisible("Pilot6_D1", true);
    case 2:
    case 4:
    }
  }

  public void update(float paramFloat) {
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

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
    {
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("HMask4_D0", false);
      hierMesh().chunkVisible("HMask5_D0", false);
    }
    else {
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
      hierMesh().chunkVisible("HMask2_D0", hierMesh().isChunkVisible("Pilot2_D0"));
      hierMesh().chunkVisible("HMask4_D0", hierMesh().isChunkVisible("Pilot4_D0"));
      hierMesh().chunkVisible("HMask5_D0", hierMesh().isChunkVisible("Pilot5_D0"));
    }
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
      return false;
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

  public void doKillPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 3:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
      break;
    case 4:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = false;
    }
  }

  private static Aircraft._WeaponSlot[] GenerateDefaultConfig(int paramInt)
  {
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[paramInt];
    try
    {
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kiAPI", 500);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kiAPI", 500);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kiAPI", 500);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kiAPI", 500);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kpzlAPI", 400);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kpzlAPI", 400);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(0, "MGunBrowning50kpzlAPI", 400);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(0, "MGunBrowning50kpzlAPI", 400);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(11, "MGunBrowning50tAPI", 450);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(11, "MGunBrowning50tAPI", 450);
    } catch (Exception localException) {
    }
    return arrayOf_WeaponSlot;
  }

  static
  {
    Class localClass = B_25D5.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "B-25");
    Property.set(localClass, "meshName", "3DO/Plane/B-25D-5(Multi)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar03B25());
    Property.set(localClass, "meshName_us", "3DO/Plane/B-25D-5(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeBMPar03B25());
    Property.set(localClass, "noseart", 1);
    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1956.6F);
    Property.set(localClass, "FlightModel", "FlightModels/B-25D5-Strafer.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitB25D5.class, CockpitB25D5_TGunner.class });

    Property.set(localClass, "LOSElevation", 0.73425F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 11, 11, 9, 9, 9, 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 9, 9, 9, 9, 9, 9, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN17", "_MGUN18", "_MGUN19", "_MGUN20", "_MGUN13", "_MGUN14", "_MGUN15", "_MGUN16", "_MGUN03", "_MGUN04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_BombSpawn02", "_BombSpawn03", "_BombSpawn01", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn01", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 48;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      for (int j = 10; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "60xParaF";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
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
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(3, "BombGunParafrag8", 20);
      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(3, "BombGunParafrag8", 20);
      arrayOf_WeaponSlot[31] = new Aircraft._WeaponSlot(3, "BombGunParafrag8", 20);
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "60xParaF6x100lbs";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
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
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(3, "BombGunParafrag8", 30);
      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(3, "BombGunParafrag8", 30);
      arrayOf_WeaponSlot[31] = new Aircraft._WeaponSlot(3, "BombGun50kg", 6);
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "12x100lbs";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
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
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(3, "BombGun50kg", 6);
      arrayOf_WeaponSlot[31] = new Aircraft._WeaponSlot(3, "BombGun50kg", 6);
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "3x250lbs1x1000lbs";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
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
      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
      arrayOf_WeaponSlot[28] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "6x250lbs";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
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
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 3);
      arrayOf_WeaponSlot[31] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 3);
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "8x250lbs";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(9, "PylonB25PLN2", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "PylonB25PLN2", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonB25PLN2", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonB25PLN2", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "4x500lbs";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
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
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 2);
      arrayOf_WeaponSlot[31] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 2);
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2x1000lbs";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
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
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[31] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "1x2000lbs";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
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
      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(3, "BombGun2000lbs", 1);
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
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