package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.objects.weapons.ToKGUtils;
import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class JU_88A4torp extends JU_88NEW
  implements TypeStormovik, TypeBomber, TypeScout, TypeHasToKG
{
  public static boolean bChangedPit = false;
  public int diveMechStage;
  public boolean bNDives;
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
  protected float fAOB;
  protected float fShipSpeed;
  protected int spreadAngle;

  public JU_88A4torp()
  {
    this.diveMechStage = 0;
    this.bNDives = false;
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
    this.fAOB = 0.0F;
    this.fShipSpeed = 15.0F;
    this.spreadAngle = 0;
  }

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers())
      bChangedPit = true;
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers())
      bChangedPit = true;
  }

  public void doWoundPilot(int paramInt, float paramFloat) {
    switch (paramInt) {
    case 1:
      this.FM.turret[0].setHealth(paramFloat);
      break;
    case 2:
      this.FM.turret[1].setHealth(paramFloat);
      this.FM.turret[2].setHealth(paramFloat);
      break;
    case 3:
      this.FM.turret[3].setHealth(paramFloat);
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
      break;
    case 3:
      hierMesh().chunkVisible("Pilot4_D0", false);
      hierMesh().chunkVisible("Pilot4_D1", true);
      hierMesh().chunkVisible("HMask4_D0", false);
    }
  }

  public void typeBomberUpdate(float paramFloat)
  {
    if (Math.abs(this.FM.Or.getKren()) > 4.5D)
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

  public void typeBomberAdjDistancePlus() {
    this.spreadAngle += 1;
    if (this.spreadAngle > 30) {
      this.spreadAngle = 30;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "TOKGSpread", new Object[] { new Integer(this.spreadAngle) });
    this.FM.AS.setSpreadAngle(this.spreadAngle);
  }

  public void typeBomberAdjDistanceMinus() {
    this.spreadAngle -= 1;
    if (this.spreadAngle < 0) {
      this.spreadAngle = 0;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "TOKGSpread", new Object[] { new Integer(this.spreadAngle) });
    this.FM.AS.setSpreadAngle(this.spreadAngle);
  }

  public void typeBomberAdjSideslipReset()
  {
  }

  public void typeBomberAdjSideslipPlus()
  {
    this.fAOB += 1.0F;
    if (this.fAOB > 180.0F) {
      this.fAOB = 180.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "TOKGAOB", new Object[] { new Integer((int)this.fAOB) });
    ToKGUtils.setTorpedoGyroAngle(this.FM, this.fAOB, this.fShipSpeed);
  }

  public void typeBomberAdjSideslipMinus()
  {
    this.fAOB -= 1.0F;
    if (this.fAOB < -180.0F) {
      this.fAOB = -180.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "TOKGAOB", new Object[] { new Integer((int)this.fAOB) });
    ToKGUtils.setTorpedoGyroAngle(this.FM, this.fAOB, this.fShipSpeed);
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
    this.fShipSpeed += 1.0F;
    if (this.fShipSpeed > 35.0F) {
      this.fShipSpeed = 35.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "TOKGSpeed", new Object[] { new Integer((int)this.fShipSpeed) });
    ToKGUtils.setTorpedoGyroAngle(this.FM, this.fAOB, this.fShipSpeed);
  }

  public void typeBomberAdjSpeedMinus()
  {
    this.fShipSpeed -= 1.0F;
    if (this.fShipSpeed < 0.0F) {
      this.fShipSpeed = 0.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "TOKGSpeed", new Object[] { new Integer((int)this.fShipSpeed) });
    ToKGUtils.setTorpedoGyroAngle(this.FM, this.fAOB, this.fShipSpeed);
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

  public void moveSteering(float paramFloat)
  {
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, Aircraft.cvt(paramFloat, -65.0F, 65.0F, 65.0F, -65.0F), 0.0F);
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    if ((Pitot.Indicator((float)this.FM.Loc.z, this.FM.getSpeed()) > 70.0F) && (this.FM.CT.getFlap() > 0.01D) && (this.FM.CT.FlapsControl != 0.0F))
    {
      this.FM.CT.FlapsControl = 0.0F;
      World.cur(); if (this.FM.actor == World.getPlayerAircraft()) HUD.log("FlapsRaised");
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    for (int i = 1; i < 4; i++)
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  public boolean isSalvo()
  {
    return this.thisWeaponsName.indexOf("salvo") != -1;
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
    Property.set(localClass, "FlightModel", "FlightModels/Ju-88A-4.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitJU_88A4torp.class, CockpitJU_88A4torp_Observer.class, CockpitJU_88A4torp_NGunner.class, CockpitJU_88A4torp_RGunner.class, CockpitJU_88A4torp_BGunner.class });

    Property.set(localClass, "LOSElevation", 1.0976F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 13, 13, 3, 3, 3, 3, 1 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_CANNON01" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, "MGunMGFFt 180" });

    weaponsRegister(localClass, "1xLTW_Torp", new String[] { "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", null, "BombGunTorpFiume 1", null, null, "MGunMGFFt 180" });

    weaponsRegister(localClass, "2xLTW_Torp_salvo", new String[] { "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunTorpFiumeL 1", "BombGunTorpFiumeR 1", null, null, "MGunMGFFt 180" });

    weaponsRegister(localClass, "2xLTW_Torp_spread", new String[] { "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunTorpFiumeL 1", "BombGunNull 1", "BombGunNull 1", "BombGunTorpFiumeR 1", "MGunMGFFt 180" });

    weaponsRegister(localClass, "1xLTF5Bh_Torp", new String[] { "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", null, "BombGunTorpF5Bheavy 1", null, null, "MGunMGFFt 180" });

    weaponsRegister(localClass, "2xLTF5Bh_Torp_salvo", new String[] { "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunTorpF5BheavyL 1", "BombGunTorpF5BheavyR 1", null, null, "MGunMGFFt 180" });

    weaponsRegister(localClass, "2xLTF5Bh_Torp_spread", new String[] { "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunTorpF5BheavyL 1", "BombGunNull 1", "BombGunNull 1", "BombGunTorpF5BheavyR 1", "MGunMGFFt 180" });

    weaponsRegister(localClass, "2xPractice_Torp_spread", new String[] { "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunTorpLTF5PracticeL 1", "BombGunNull 1", "BombGunNull 1", "BombGunTorpLTF5PracticeR 1", "MGunMGFFt 180" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null });
  }
}