package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.RealFlightModel;
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

public class ME_410D extends ME_210
  implements TypeFighter, TypeBNZFighter, TypeStormovik, TypeStormovikArmored, TypeX4Carrier
{
  public boolean bToFire = false;
  private long tX4Prev = 0L;
  private float kangle = 0.0F;
  private float deltaAzimuth = 0.0F;
  private float deltaTangage = 0.0F;
  private boolean bSightAutomation = false;
  private boolean bSightBombDump = false;
  private float fSightCurDistance = 0.0F;
  public float fSightCurForwardAngle = 0.0F;
  public float fSightCurSideslip = 0.0F;
  public float fSightCurAltitude = 850.0F;
  public float fSightCurSpeed = 150.0F;
  public float fSightCurReadyness = 0.0F;
  public static boolean bChangedPit = false;

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

  static Class _mthclass$(String paramString) {
    Class localClass;
    try {
      localClass = Class.forName(paramString);
    } catch (ClassNotFoundException localClassNotFoundException) {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }

    return localClass;
  }

  public boolean typeBomberToggleAutomation() {
    this.bSightAutomation = (!this.bSightAutomation);
    this.bSightBombDump = false;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAutomation" + (this.bSightAutomation ? "ON" : "OFF"));

    return this.bSightAutomation;
  }

  public void typeBomberAdjDistanceReset() {
    this.fSightCurDistance = 0.0F;
    this.fSightCurForwardAngle = 0.0F;
  }

  public void typeBomberAdjDistancePlus() {
    this.fSightCurForwardAngle += 1.0F;
    if (this.fSightCurForwardAngle > 85.0F)
      this.fSightCurForwardAngle = 85.0F;
    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));

    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });

    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjDistanceMinus() {
    this.fSightCurForwardAngle -= 1.0F;
    if (this.fSightCurForwardAngle < 0.0F)
      this.fSightCurForwardAngle = 0.0F;
    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));

    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });

    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjSideslipReset() {
    this.fSightCurSideslip = 0.0F;
  }

  public void typeBomberAdjSideslipPlus() {
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
    this.fSightCurAltitude = 850.0F;
  }

  public void typeBomberAdjAltitudePlus() {
    this.fSightCurAltitude += 10.0F;
    if (this.fSightCurAltitude > 6000.0F)
      this.fSightCurAltitude = 6000.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });

    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjAltitudeMinus()
  {
    this.fSightCurAltitude -= 10.0F;
    if (this.fSightCurAltitude < 850.0F)
      this.fSightCurAltitude = 850.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });

    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjSpeedReset()
  {
    this.fSightCurSpeed = 250.0F;
  }

  public void typeBomberAdjSpeedPlus() {
    this.fSightCurSpeed += 10.0F;
    if (this.fSightCurSpeed > 900.0F)
      this.fSightCurSpeed = 900.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberAdjSpeedMinus()
  {
    this.fSightCurSpeed -= 10.0F;
    if (this.fSightCurSpeed < 150.0F)
      this.fSightCurSpeed = 150.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberUpdate(float paramFloat)
  {
    if (Math.abs(this.FM.Or.getKren()) > 4.5D) {
      this.fSightCurReadyness -= 0.0666666F * paramFloat;
      if (this.fSightCurReadyness < 0.0F)
        this.fSightCurReadyness = 0.0F;
    }
    if (this.fSightCurReadyness < 1.0F) {
      this.fSightCurReadyness += 0.0333333F * paramFloat;
    } else if (this.bSightAutomation) {
      this.fSightCurDistance -= this.fSightCurSpeed / 3.6F * paramFloat;
      if (this.fSightCurDistance < 0.0F) {
        this.fSightCurDistance = 0.0F;
        typeBomberToggleAutomation();
      }
      this.fSightCurForwardAngle = (float)Math.toDegrees(Math.atan(this.fSightCurDistance / this.fSightCurAltitude));

      if (this.fSightCurDistance < this.fSightCurSpeed / 3.6F * Math.sqrt(this.fSightCurAltitude * 0.203874F))
      {
        this.bSightBombDump = true;
      }if (this.bSightBombDump)
        if (this.FM.isTick(3, 0)) {
          if ((this.FM.CT.Weapons[3] != null) && (this.FM.CT.Weapons[3][(this.FM.CT.Weapons[3].length - 1)] != null) && (this.FM.CT.Weapons[3][(this.FM.CT.Weapons[3].length - 1)].haveBullets()))
          {
            this.FM.CT.WeaponControl[3] = true;
            HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightBombdrop");
          }
        }
        else
          this.FM.CT.WeaponControl[3] = false;
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
    paramNetMsgGuaranted.writeFloat(this.fSightCurSpeed);
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

    this.fSightCurAltitude = paramNetMsgInput.readFloat();
    this.fSightCurSpeed = paramNetMsgInput.readFloat();
    this.fSightCurReadyness = (paramNetMsgInput.readUnsignedByte() / 200.0F);
  }

  public void rareAction(float paramFloat, boolean paramBoolean) {
    super.rareAction(paramFloat, paramBoolean);
    if (((!(this.FM instanceof RealFlightModel)) || (!((RealFlightModel)this.FM).isRealMode())) && (paramBoolean) && ((this.FM instanceof Pilot)))
    {
      Pilot localPilot = (Pilot)this.FM;
      if ((localPilot.get_maneuver() == 63) && (localPilot.target != null)) {
        Point3d localPoint3d = new Point3d(localPilot.target.Loc);
        localPoint3d.sub(this.FM.Loc);
        this.FM.Or.transformInv(localPoint3d);
        if (((localPoint3d.x > 4000.0D) && (localPoint3d.x < 5500.0D)) || ((localPoint3d.x > 100.0D) && (localPoint3d.x < 5000.0D) && (World.Rnd().nextFloat() < 0.33F) && (Time.current() > this.tX4Prev + 10000L)))
        {
          this.bToFire = true;
          this.tX4Prev = Time.current();
        }
      }
    }
  }

  public void typeX4CAdjSidePlus() {
    this.deltaAzimuth = 1.0F;
  }

  public void typeX4CAdjSideMinus() {
    this.deltaAzimuth = -1.0F;
  }

  public void typeX4CAdjAttitudePlus() {
    this.deltaTangage = 1.0F;
  }

  public void typeX4CAdjAttitudeMinus() {
    this.deltaTangage = -1.0F;
  }

  public void typeX4CResetControls() {
    this.deltaAzimuth = (this.deltaTangage = 0.0F);
  }

  public float typeX4CgetdeltaAzimuth() {
    return this.deltaAzimuth;
  }

  public float typeX4CgetdeltaTangage() {
    return this.deltaTangage;
  }

  static
  {
    Class localClass = ME_410D.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Me-410");
    Property.set(localClass, "meshName", "3DO/Plane/ME-410-D/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/Me-410D.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitME_410D.class, Cockpit410D_Bombardier.class });

    Property.set(localClass, "LOSElevation", 0.66895F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 10, 10, 3, 3, 3, 0, 0, 9, 0, 9, 9, 1, 9, 1, 9, 1, 9, 1, 9, 1, 9, 1, 9, 1, 1, 9, 1, 1, 9, 9, 9, 9, 2, 2, 2, 2, 9, 2, 9, 2, 9, 2, 2, 9, 2, 2, 9, 3, 9, 3, 9, 3, 9, 3, 9, 3, 9, 1, 9, 1, 9, 1, 9, 1 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_MGUN03", "_MGUN04", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_CANNON03", "_CANNON04", "_ExternalDev03", "_CANNON05", "_ExternalDev04", "_ExternalDev05", "_MGUN05", "_ExternalDev06", "_MGUN06", "_ExternalDev07", "_CANNON07", "_ExternalDev08", "_CANNON08", "_ExternalDev09", "_MGUN09", "_ExternalDev10", "_MGUN10", "_ExternalDev11", "_MGUN11", "_MGUN12", "_ExternalDev12", "_MGUN13", "_MGUN14", "_ExternalDev13", "_ExternalDev14", "_ExternalDev15", "_ExternalDev16", "_ExternalRock01", "_ExternalRock03", "_ExternalRock02", "_ExternalRock04", "_ExternalDev17", "_ExternalRock05", "_ExternalDev18", "_ExternalRock06", "_ExternalDev19", "_ExternalRock07", "_ExternalRock07", "_ExternalDev20", "_ExternalRock08", "_ExternalRock08", "_ExternalDev21", "_ExternalBomb01", "_ExternalDev22", "_ExternalBomb02", "_ExternalDev23", "_ExternalBomb03", "_ExternalDev24", "_ExternalBomb04", "_ExternalDev25", "_ExternalBomb05", "_ExternalDev26", "_MGUN15", "_ExternalDev27", "_MGUN16", "_ExternalDev28", "_MGUN17", "_ExternalDev29", "_MGUN18" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 68;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];

      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (int j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "U2";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

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
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(9, "Pylon410GUNPOD", 1);

      arrayOf_WeaponSlot[23] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 250);

      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(9, "Pylon410GUNPOD", 1);

      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 250);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "U4";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(1, "MGunMK214A", 36);

      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "Pylon410BK5", 1);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "U5";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "U6";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = new Aircraft._WeaponSlot(9, "Pylon410MG213", 1);

      arrayOf_WeaponSlot[61] = new Aircraft._WeaponSlot(1, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[62] = new Aircraft._WeaponSlot(9, "Pylon410MG213", 1);

      arrayOf_WeaponSlot[63] = new Aircraft._WeaponSlot(1, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "R2";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "Pylon410GUNPOD", 1);

      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(1, "MGunMK108ki", 100);

      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(9, "Pylon410GUNPOD", 1);

      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(1, "MGunMK108ki", 100);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "R3";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

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
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(9, "Pylon410Mk103", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(1, "MGunMK103kh", 100);

      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "Pylon410Mk103", 1);

      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(1, "MGunMK103kh", 100);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "R4";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "PylonBF110R3", 1);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "R5";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

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
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(9, "Pylon410DGUNPOD", 1);

      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 100);

      arrayOf_WeaponSlot[28] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 100);

      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(9, "Pylon410DGUNPOD", 1);

      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 100);

      arrayOf_WeaponSlot[31] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 100);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "R11";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = new Aircraft._WeaponSlot(9, "Pylon410MG213", 1);

      arrayOf_WeaponSlot[61] = new Aircraft._WeaponSlot(1, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[62] = new Aircraft._WeaponSlot(9, "Pylon410MG213", 1);

      arrayOf_WeaponSlot[63] = new Aircraft._WeaponSlot(1, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "R12";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = new Aircraft._WeaponSlot(9, "Pylon410MG213", 1);

      arrayOf_WeaponSlot[65] = new Aircraft._WeaponSlot(1, "MGunMK213ki", 110);

      arrayOf_WeaponSlot[66] = new Aircraft._WeaponSlot(9, "Pylon410MG213", 1);

      arrayOf_WeaponSlot[67] = new Aircraft._WeaponSlot(1, "MGunMK213ki", 110);

      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "R13";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(1, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(1, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "PylonBF110R3", 1);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "R4U2";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "PylonBF110R3", 1);

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
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(9, "Pylon410GUNPOD", 1);

      arrayOf_WeaponSlot[23] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 250);

      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(9, "Pylon410GUNPOD", 1);

      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 250);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "U4R4";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(0, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(0, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "PylonBF110R3", 1);

      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(1, "MGunMK214A", 36);

      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "Pylon410BK5", 1);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "U5R12";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = new Aircraft._WeaponSlot(9, "Pylon410MG213", 1);

      arrayOf_WeaponSlot[65] = new Aircraft._WeaponSlot(1, "MGunMK213ki", 110);

      arrayOf_WeaponSlot[66] = new Aircraft._WeaponSlot(9, "Pylon410MG213", 1);

      arrayOf_WeaponSlot[67] = new Aircraft._WeaponSlot(1, "MGunMK213ki", 110);

      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "U6R13";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(0, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(0, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "PylonBF110R3", 1);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = new Aircraft._WeaponSlot(9, "Pylon410MG213", 1);

      arrayOf_WeaponSlot[61] = new Aircraft._WeaponSlot(1, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[62] = new Aircraft._WeaponSlot(9, "Pylon410MG213", 1);

      arrayOf_WeaponSlot[63] = new Aircraft._WeaponSlot(1, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "R2R4";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "PylonBF110R3", 1);

      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "Pylon410GUNPOD", 1);

      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(1, "MGunMK108ki", 100);

      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(9, "Pylon410GUNPOD", 1);

      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(1, "MGunMK108ki", 100);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "R3R4";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "PylonBF110R3", 1);

      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(9, "Pylon410Mk103", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(1, "MGunMK103kh", 100);

      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "Pylon410Mk103", 1);

      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(1, "MGunMK103kh", 100);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "R4R5";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "PylonBF110R3", 1);

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
      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(9, "Pylon410DGUNPOD", 1);

      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 250);

      arrayOf_WeaponSlot[28] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 250);

      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(9, "Pylon410DGUNPOD", 1);

      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 250);

      arrayOf_WeaponSlot[31] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 250);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "M5";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

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
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[33] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[34] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[35] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[36] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[37] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[38] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[39] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[40] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[41] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[42] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[43] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "U2M5";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

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
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(9, "Pylon410GUNPOD", 1);

      arrayOf_WeaponSlot[23] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 250);

      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(9, "Pylon410GUNPOD", 1);

      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 250);

      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[33] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[34] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[35] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[36] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[37] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[38] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[39] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[40] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[41] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[42] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[43] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "U4M5";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(1, "MGunMK214A", 36);

      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "Pylon410BK5", 1);

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
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[33] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[34] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[35] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[36] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[37] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[38] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[39] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[40] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[41] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[42] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[43] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "R2M5";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "Pylon410GUNPOD", 1);

      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(1, "MGunMK108ki", 100);

      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(9, "Pylon410GUNPOD", 1);

      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(1, "MGunMK108ki", 100);

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
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[33] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[34] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[35] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[36] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[37] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[38] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[39] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[40] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[41] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[42] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[43] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "R3M5";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

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
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(9, "Pylon410Mk103", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(1, "MGunMK103kh", 100);

      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "Pylon410Mk103", 1);

      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(1, "MGunMK103kh", 100);

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
      arrayOf_WeaponSlot[32] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[33] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[34] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[35] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[36] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[37] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[38] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[39] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[40] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[41] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[42] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[43] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "R5M5";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

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
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(9, "Pylon410DGUNPOD", 1);

      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 100);

      arrayOf_WeaponSlot[28] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 100);

      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(9, "Pylon410DGUNPOD", 1);

      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 100);

      arrayOf_WeaponSlot[31] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 100);

      arrayOf_WeaponSlot[32] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[33] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[34] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[35] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[36] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[37] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[38] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[39] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[40] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[41] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[42] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[43] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "U2R4M5";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "PylonBF110R3", 1);

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
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(9, "Pylon410GUNPOD", 1);

      arrayOf_WeaponSlot[23] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 250);

      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(9, "Pylon410GUNPOD", 1);

      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 250);

      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[33] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[34] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[35] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[36] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[37] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[38] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[39] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[40] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[41] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[42] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[43] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "U4R4M5";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(0, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(0, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "PylonBF110R3", 1);

      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(1, "MGunMK214A", 36);

      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "Pylon410BK5", 1);

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
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[33] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[34] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[35] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[36] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[37] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[38] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[39] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[40] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[41] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[42] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[43] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "R2R4M5";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "PylonBF110R3", 1);

      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "Pylon410GUNPOD", 1);

      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(1, "MGunMK108ki", 100);

      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(9, "Pylon410GUNPOD", 1);

      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(1, "MGunMK108ki", 100);

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
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[33] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[34] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[35] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[36] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[37] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[38] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[39] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[40] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[41] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[42] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[43] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "R3R4M5";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "PylonBF110R3", 1);

      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(9, "Pylon410Mk103", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(1, "MGunMK103kh", 100);

      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "Pylon410Mk103", 1);

      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(1, "MGunMK103kh", 100);

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
      arrayOf_WeaponSlot[32] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[33] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[34] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[35] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[36] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[37] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[38] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[39] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[40] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[41] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[42] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);

      arrayOf_WeaponSlot[43] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);

      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "X4";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

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
      arrayOf_WeaponSlot[44] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[45] = new Aircraft._WeaponSlot(2, "RocketGunX4", 1);

      arrayOf_WeaponSlot[46] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);

      arrayOf_WeaponSlot[47] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[48] = new Aircraft._WeaponSlot(2, "RocketGunX4", 1);

      arrayOf_WeaponSlot[49] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);

      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "U4X4";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(1, "MGunMK214A", 36);

      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "Pylon410BK5", 1);

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
      arrayOf_WeaponSlot[44] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[45] = new Aircraft._WeaponSlot(2, "RocketGunX4", 1);

      arrayOf_WeaponSlot[46] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);

      arrayOf_WeaponSlot[47] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[48] = new Aircraft._WeaponSlot(2, "RocketGunX4", 1);

      arrayOf_WeaponSlot[49] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);

      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "U6R10";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(1, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(1, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "PylonBF110R3", 1);

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
      arrayOf_WeaponSlot[44] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[45] = new Aircraft._WeaponSlot(2, "RocketGunX4", 1);

      arrayOf_WeaponSlot[46] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);

      arrayOf_WeaponSlot[47] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[48] = new Aircraft._WeaponSlot(2, "RocketGunX4", 1);

      arrayOf_WeaponSlot[49] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);

      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "U6R10R13";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(1, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(1, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "PylonBF110R3", 1);

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
      arrayOf_WeaponSlot[44] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[45] = new Aircraft._WeaponSlot(2, "RocketGunX4", 1);

      arrayOf_WeaponSlot[46] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);

      arrayOf_WeaponSlot[47] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[48] = new Aircraft._WeaponSlot(2, "RocketGunX4", 1);

      arrayOf_WeaponSlot[49] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);

      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = new Aircraft._WeaponSlot(9, "Pylon410MG213", 1);

      arrayOf_WeaponSlot[61] = new Aircraft._WeaponSlot(1, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[62] = new Aircraft._WeaponSlot(9, "Pylon410MG213", 1);

      arrayOf_WeaponSlot[63] = new Aircraft._WeaponSlot(1, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "R3X4";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

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
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(9, "Pylon410Mk103", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(1, "MGunMK103kh", 100);

      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "Pylon410Mk103", 1);

      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(1, "MGunMK103kh", 100);

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
      arrayOf_WeaponSlot[44] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[45] = new Aircraft._WeaponSlot(2, "RocketGunX4", 1);

      arrayOf_WeaponSlot[46] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);

      arrayOf_WeaponSlot[47] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[48] = new Aircraft._WeaponSlot(2, "RocketGunX4", 1);

      arrayOf_WeaponSlot[49] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);

      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "R5X4";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

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
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(9, "Pylon410DGUNPOD", 1);

      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 100);

      arrayOf_WeaponSlot[28] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 100);

      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(9, "Pylon410DGUNPOD", 1);

      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 100);

      arrayOf_WeaponSlot[31] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 100);

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
      arrayOf_WeaponSlot[44] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[45] = new Aircraft._WeaponSlot(2, "RocketGunX4", 1);

      arrayOf_WeaponSlot[46] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);

      arrayOf_WeaponSlot[47] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[48] = new Aircraft._WeaponSlot(2, "RocketGunX4", 1);

      arrayOf_WeaponSlot[49] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);

      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "X4R12";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

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
      arrayOf_WeaponSlot[44] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[45] = new Aircraft._WeaponSlot(2, "RocketGunX4", 1);

      arrayOf_WeaponSlot[46] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);

      arrayOf_WeaponSlot[47] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[48] = new Aircraft._WeaponSlot(2, "RocketGunX4", 1);

      arrayOf_WeaponSlot[49] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);

      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = new Aircraft._WeaponSlot(9, "Pylon410MG213", 1);

      arrayOf_WeaponSlot[65] = new Aircraft._WeaponSlot(1, "MGunMK213ki", 110);

      arrayOf_WeaponSlot[66] = new Aircraft._WeaponSlot(9, "Pylon410MG213", 1);

      arrayOf_WeaponSlot[67] = new Aircraft._WeaponSlot(1, "MGunMK213ki", 110);

      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "R34x50SC";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

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
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(9, "Pylon410Mk103", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(1, "MGunMK103kh", 100);

      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "Pylon410Mk103", 1);

      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(1, "MGunMK103kh", 100);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[51] = new Aircraft._WeaponSlot(2, "BombGunSC50", 1);

      arrayOf_WeaponSlot[52] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[53] = new Aircraft._WeaponSlot(2, "BombGunSC50", 1);

      arrayOf_WeaponSlot[54] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[55] = new Aircraft._WeaponSlot(2, "BombGunSC50", 1);

      arrayOf_WeaponSlot[56] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[57] = new Aircraft._WeaponSlot(2, "BombGunSC50", 1);

      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "R124x50";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[51] = new Aircraft._WeaponSlot(2, "BombGunSC50", 1);

      arrayOf_WeaponSlot[52] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[53] = new Aircraft._WeaponSlot(2, "BombGunSC50", 1);

      arrayOf_WeaponSlot[54] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[55] = new Aircraft._WeaponSlot(2, "BombGunSC50", 1);

      arrayOf_WeaponSlot[56] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[57] = new Aircraft._WeaponSlot(2, "BombGunSC50", 1);

      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = new Aircraft._WeaponSlot(9, "Pylon410MG213", 1);

      arrayOf_WeaponSlot[65] = new Aircraft._WeaponSlot(1, "MGunMK213ki", 110);

      arrayOf_WeaponSlot[66] = new Aircraft._WeaponSlot(9, "Pylon410MG213", 1);

      arrayOf_WeaponSlot[67] = new Aircraft._WeaponSlot(1, "MGunMK213ki", 110);

      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "8x50SC";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunSC50", 4);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunSC50", 4);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "8x50SC2x250SC";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunSC50", 4);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunSC50", 4);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[55] = new Aircraft._WeaponSlot(2, "BombGunSC250", 1);

      arrayOf_WeaponSlot[56] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[57] = new Aircraft._WeaponSlot(2, "BombGunSC250", 1);

      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "1x250SC";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2x250SC";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "4x250SC";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[55] = new Aircraft._WeaponSlot(2, "BombGunSC250", 1);

      arrayOf_WeaponSlot[56] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[57] = new Aircraft._WeaponSlot(2, "BombGunSC250", 1);

      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "1x500SC";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2x500SC";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2x500SC2x250SC";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[55] = new Aircraft._WeaponSlot(2, "BombGunSC250", 1);

      arrayOf_WeaponSlot[56] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[57] = new Aircraft._WeaponSlot(2, "BombGunSC250", 1);

      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "1x250AB";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunAB250", 1);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2x250AB";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunAB250", 1);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunAB250", 1);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "1x500AB";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunAB500", 1);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2x500AB";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunAB500", 1);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunAB500", 1);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "12x50SC";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunSC50", 4);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunSC50", 4);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[51] = new Aircraft._WeaponSlot(2, "BombGunSC50", 1);

      arrayOf_WeaponSlot[52] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[53] = new Aircraft._WeaponSlot(2, "BombGunSC50", 1);

      arrayOf_WeaponSlot[54] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[55] = new Aircraft._WeaponSlot(2, "BombGunSC50", 1);

      arrayOf_WeaponSlot[56] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[57] = new Aircraft._WeaponSlot(2, "BombGunSC50", 1);

      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2X250SC4x50SC";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[51] = new Aircraft._WeaponSlot(2, "BombGunSC50", 1);

      arrayOf_WeaponSlot[52] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[53] = new Aircraft._WeaponSlot(2, "BombGunSC50", 1);

      arrayOf_WeaponSlot[54] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[55] = new Aircraft._WeaponSlot(2, "BombGunSC50", 1);

      arrayOf_WeaponSlot[56] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[57] = new Aircraft._WeaponSlot(2, "BombGunSC50", 1);

      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "1X500SC4x50SC";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131ki", 500);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[51] = new Aircraft._WeaponSlot(2, "BombGunSC50", 1);

      arrayOf_WeaponSlot[52] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[53] = new Aircraft._WeaponSlot(2, "BombGunSC50", 1);

      arrayOf_WeaponSlot[54] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[55] = new Aircraft._WeaponSlot(2, "BombGunSC50", 1);

      arrayOf_WeaponSlot[56] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);

      arrayOf_WeaponSlot[57] = new Aircraft._WeaponSlot(2, "BombGunSC50", 1);

      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "TORPEDO";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120k", 250);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);

      arrayOf_WeaponSlot[59] = new Aircraft._WeaponSlot(3, "BombGun4512", 1);

      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "U6TORPEDO";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(10, "MGunMG131tj", 500);

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
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);

      arrayOf_WeaponSlot[59] = new Aircraft._WeaponSlot(3, "BombGun4512", 1);

      arrayOf_WeaponSlot[60] = new Aircraft._WeaponSlot(9, "Pylon410MG213", 1);

      arrayOf_WeaponSlot[61] = new Aircraft._WeaponSlot(1, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[62] = new Aircraft._WeaponSlot(9, "Pylon410MG213", 1);

      arrayOf_WeaponSlot[63] = new Aircraft._WeaponSlot(1, "MGunMG213MGki", 270);

      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;
      arrayOf_WeaponSlot[67] = null;
      for (j = 68; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (j = 0; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}