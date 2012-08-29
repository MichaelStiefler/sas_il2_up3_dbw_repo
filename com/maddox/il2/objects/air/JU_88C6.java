package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.CLASS;
import com.maddox.rts.Finger;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.util.ArrayList;

public class JU_88C6 extends JU_88NEW
  implements TypeStormovik, TypeBomber, TypeScout
{
  public static boolean bChangedPit = false;
  public int diveMechStage;
  public boolean bNDives;
  private boolean bDropsBombs;
  private long dropStopTime;
  private boolean bSightAutomation;
  private boolean bSightBombDump;
  private float fSightCurDistance;
  public float fSightCurForwardAngle;
  public float fSightCurSideslip;
  public float fSightCurAltitude;
  public float fSightCurSpeed;
  public float fSightCurReadyness;
  public float fDiveRecoveryAlt;
  public float fDiveVelocity;
  public float fDiveAngle;
  static Class class$com$maddox$il2$objects$air$CockpitJU_88C6_BGunner;
  static Class class$com$maddox$il2$objects$air$CockpitJU_88C6_NGunner;

  public JU_88C6()
  {
    this.diveMechStage = 0;
    this.bNDives = false;
    this.bDropsBombs = false;
    this.dropStopTime = -1L;
    this.bSightAutomation = false;
    this.bSightBombDump = false;
    this.fSightCurDistance = 0.0F;
    this.fSightCurForwardAngle = 0.0F;
    this.fSightCurSideslip = 0.0F;
    this.fSightCurAltitude = 850.0F;
    this.fSightCurSpeed = 150.0F;
    this.fSightCurReadyness = 0.0F;
    this.fDiveRecoveryAlt = 850.0F;
    this.fDiveVelocity = 150.0F;
    this.fDiveAngle = 70.0F;
  }

  public void update(float paramFloat)
  {
    for (int i = 1; i < 11; i++)
    {
      hierMesh().chunkSetAngles("Radl" + i + "_D0", 0.0F, 30.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator(), 0.0F);
      hierMesh().chunkSetAngles("Radr" + i + "_D0", 0.0F, 30.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlRadiator(), 0.0F);
    }

    super.update(paramFloat);
  }

  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay1_D0", 0.0F, 85.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -85.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay3_D0", 0.0F, 85.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay4_D0", 0.0F, -85.0F * paramFloat, 0.0F);
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

  public void doKillPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 2:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
    }
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      hierMesh().chunkVisible("HMask2_D0", false);
      break;
    case 2:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("Pilot3_D1", true);
      hierMesh().chunkVisible("HMask3_D0", false);
    }
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
      return;
  }

  public boolean typeBomberToggleAutomation()
  {
    this.bSightAutomation = false;
    this.bSightBombDump = false;
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
    this.fSightCurAltitude = (this.fDiveRecoveryAlt = paramNetMsgInput.readFloat());
    this.fSightCurSpeed = (this.fDiveVelocity = paramNetMsgInput.readUnsignedByte() * 2.5F);
    this.fSightCurReadyness = (paramNetMsgInput.readUnsignedByte() / 200.0F);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = Math.max(-paramFloat * 1600.0F, -80.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
    f = paramFloat >= 0.5F ? Math.abs(Math.min(1.0F - paramFloat, 0.1F)) : Math.abs(Math.min(paramFloat, 0.1F));
    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, -450.0F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, 450.0F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, 1200.0F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, -1200.0F * f, 0.0F);
    if (paramFloat < 0.5F)
    {
      paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, 900.0F * f, 0.0F);
      paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -900.0F * f, 0.0F);
      paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -900.0F * f, 0.0F);
      paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, 900.0F * f, 0.0F);
    }
    paramHierMesh.chunkSetAngles("GearR8_D0", 0.0F, 0.0F, 95.0F * paramFloat);
    paramHierMesh.chunkSetAngles("GearL8_D0", 0.0F, 0.0F, 95.0F * paramFloat);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -130.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -130.0F * paramFloat, 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat)
  {
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, paramFloat, 0.0F);
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Ju-88");
    Property.set(localClass, "meshName", "3DO/Plane/Ju-88C-6/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/Ju-88C-6.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitJU_88C6.class, CockpitJU_88C6_RGunner.class });

    Property.set(localClass, "LOSElevation", 1.0976F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 0, 0, 0, 1, 1, 1, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN03", "_MG1701", "_MG1702", "_MG1703", "_MGFF01", "_MGFF02", "_MGFF03", "_BombSpawn01", "_BombSpawn02" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 12;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunMG131t", 700);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17t", 800);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG17t", 800);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG17t", 800);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunMGFFk", 180);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunMGFFt", 180);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunMGFFt", 180);
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "10xSC50";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunMG131t", 700);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17t", 800);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG17t", 800);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG17t", 800);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunMGFFk", 180);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunMGFFt", 180);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunMGFFt", 180);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunSC50", 5);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunSC50", 5);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "20xSC50";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunMG131t", 700);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17t", 800);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG17t", 800);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG17t", 800);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunMGFFk", 180);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunMGFFt", 180);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunMGFFt", 180);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunSC50", 10);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunSC50", 10);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
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
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}