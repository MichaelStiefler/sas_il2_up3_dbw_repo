package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.HUD;
import com.maddox.rts.CLASS;
import com.maddox.rts.Finger;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.util.ArrayList;

public class JU_88A4torp extends JU_88NEW
  implements TypeStormovik, TypeBomber, TypeScout
{
  public static boolean bChangedPit = false;
  public int diveMechStage = 0;
  public boolean bNDives = false;
  private boolean bDropsBombs = false;
  private long dropStopTime = -1L;
  private boolean bSightAutomation = false;
  private boolean bSightBombDump = false;
  private float fSightCurDistance = 0.0F;
  public float fSightCurForwardAngle = 0.0F;
  public float fSightCurSideslip = 0.0F;
  public float fSightCurAltitude = 850.0F;
  public float fSightCurSpeed = 150.0F;
  public float fSightCurReadyness = 0.0F;
  public float fDiveRecoveryAlt = 850.0F;
  public float fDiveVelocity = 150.0F;
  public float fDiveAngle = 70.0F;

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers())
      bChangedPit = true;
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor) {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers())
      bChangedPit = true;
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    if ((Pitot.Indicator((float)this.FM.Loc.z, this.FM.getSpeed()) > 70.0F) && (this.FM.CT.getFlap() > 0.01D) && (this.FM.CT.FlapsControl != 0.0F))
    {
      this.FM.CT.FlapsControl = 0.0F;
      Actor localActor = this.FM.actor;
      World.cur();
      if (localActor == World.getPlayerAircraft())
        HUD.log("FlapsRaised");
    }
  }

  public void doKillPilot(int paramInt) {
    switch (paramInt) {
    case 1:
      this.FM.turret[0].bIsOperable = false;
      break;
    case 2:
      this.FM.turret[1].bIsOperable = false;
      this.FM.turret[2].bIsOperable = false;
      break;
    case 3:
      this.FM.turret[3].bIsOperable = false;
    }
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
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
    if (Math.abs(this.FM.Or.getKren()) > 4.5D) {
      this.fSightCurReadyness -= 0.0666666F * paramFloat;
      if (this.fSightCurReadyness < 0.0F)
        this.fSightCurReadyness = 0.0F;
    }
    if (this.fSightCurReadyness < 1.0F)
      this.fSightCurReadyness += 0.0333333F * paramFloat;
    else if (this.bSightAutomation)
      return;
  }

  public boolean typeBomberToggleAutomation() {
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

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException
  {
    paramNetMsgGuaranted.writeByte((this.bSightAutomation ? 1 : 0) | (this.bSightBombDump ? 2 : 0));

    paramNetMsgGuaranted.writeFloat(this.fSightCurDistance);
    paramNetMsgGuaranted.writeByte((int)this.fSightCurForwardAngle);
    paramNetMsgGuaranted.writeByte((int)((this.fSightCurSideslip + 3.0F) * 33.333328F));

    paramNetMsgGuaranted.writeFloat(this.fSightCurAltitude);
    paramNetMsgGuaranted.writeByte((int)(this.fSightCurSpeed / 2.5F));
    paramNetMsgGuaranted.writeByte((int)(this.fSightCurReadyness * 200.0F));
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException
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

  public static void moveGear(HierMesh paramHierMesh, float paramFloat) {
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
    if (paramFloat < 0.5F) {
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

  protected void moveGear(float paramFloat) {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, Aircraft.cvt(paramFloat, -65.0F, 65.0F, 65.0F, -65.0F), 0.0F);
  }

  static Class _mthclass$(String paramString)
  {
    Class localClass;
    try
    {
      localClass = Class.forName(paramString);
    } catch (ClassNotFoundException localClassNotFoundException) {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }

    return localClass;
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Ju-88");
    Property.set(localClass, "meshName", "3DO/Plane/Ju-88A-4torp/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/Ju-88A-4torp.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitJU_88A4torp.class, CockpitJU_88A4_BombardierTorp.class, CockpitJU_88A4_RGunnerTorp.class, CockpitJU_88A4_NGunnerTorp.class, CockpitJU_88A4_BGunnerTorp.class });

    Property.set(localClass, "LOSElevation", 1.0976F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 13, 13, 3, 3, 3, 3, 1 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_CANNON01" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 10;

      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunMG81t", 375);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunMG81t", 0);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(12, "MGunMG81t", 1200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(13, "MGunMG81t", 750);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(13, "MGunMG81t", 750);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(1, "MGunMGFFt", 360);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "1xLTW_Torp";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunMG81t", 375);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunMG81t", 0);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(12, "MGunMG81t", 1200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(13, "MGunMG81t", 750);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(13, "MGunMG81t", 750);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunTorpFiume", 1);
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(1, "MGunMGFFt", 360);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2xLTW_Torp_salvo";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunMG81t", 375);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunMG81t", 0);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(12, "MGunMG81t", 1200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(13, "MGunMG81t", 750);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(13, "MGunMG81t", 750);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunTorpFiume", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunTorpFiume", 1);
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(1, "MGunMGFFt", 360);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2xLTW_Torp_spread";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunMG81t", 375);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunMG81t", 0);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(12, "MGunMG81t", 1200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(13, "MGunMG81t", 750);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(13, "MGunMG81t", 750);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunTorpFiume", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunTorpFiume", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(1, "MGunMGFFt", 360);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "1xLTF5Bh_Torp";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunMG81t", 375);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunMG81t", 0);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(12, "MGunMG81t", 1200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(13, "MGunMG81t", 750);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(13, "MGunMG81t", 750);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunTorpF5Bheavy", 1);
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(1, "MGunMGFFt", 360);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2xLTF5Bh_Torp_salvo";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunMG81t", 375);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunMG81t", 0);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(12, "MGunMG81t", 1200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(13, "MGunMG81t", 750);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(13, "MGunMG81t", 750);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunTorpF5Bheavy", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunTorpF5Bheavy", 1);
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(1, "MGunMGFFt", 360);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2xLTF5Bh_Torp_spread";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunMG81t", 375);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunMG81t", 0);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(12, "MGunMG81t", 1200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(13, "MGunMG81t", 750);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(13, "MGunMG81t", 750);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunTorpF5Bheavy", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunTorpF5Bheavy", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(1, "MGunMGFFt", 360);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2xPractice_Torp_spread";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(10, "MGunMG81t", 375);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(11, "MGunMG81t", 0);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(12, "MGunMG81t", 1200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(13, "MGunMG81t", 750);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(13, "MGunMG81t", 750);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunTorpLTF5Practice", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunTorpLTF5Practice", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(1, "MGunMGFFt", 360);
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
      arrayOf_WeaponSlot[9] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}