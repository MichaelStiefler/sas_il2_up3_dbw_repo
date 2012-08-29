package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class B_25J1 extends B_25
  implements TypeBomber, TypeStormovik, TypeStormovikArmored
{
  public static boolean bChangedPit = false;

  private boolean bSightAutomation = false;
  private boolean bSightBombDump = false;
  private float fSightCurDistance = 0.0F;
  public float fSightCurForwardAngle = 0.0F;
  public float fSightCurSideslip = 0.0F;
  public float fSightCurAltitude = 3000.0F;
  public float fSightCurSpeed = 200.0F;
  public float fSightCurReadyness = 0.0F;

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 19:
      killPilot(this, 4);
      hierMesh().chunkVisible("Wire2_D0", false);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.FM.AS.wantBeaconsNet(true);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    for (int i = 1; i < 7; i++)
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers()) bChangedPit = true; 
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor) {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers()) bChangedPit = true;
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -23.0F) { f1 = -23.0F; bool = false; }
      if (f1 > 23.0F) { f1 = 23.0F; bool = false; }
      if (f2 < -25.0F) { f2 = -25.0F; bool = false; }
      if (f2 <= 15.0F) break; f2 = 15.0F; bool = false; break;
    case 1:
      if (f2 < 0.0F) { f2 = 0.0F; bool = false; }
      if (f2 <= 73.0F) break; f2 = 73.0F; bool = false; break;
    case 2:
      if (f1 < -38.0F) { f1 = -38.0F; bool = false; }
      if (f1 > 38.0F) { f1 = 38.0F; bool = false; }
      if (f2 < -41.0F) { f2 = -41.0F; bool = false; }
      if (f2 <= 43.0F) break; f2 = 43.0F; bool = false; break;
    case 3:
      if (f1 < -85.0F) { f1 = -85.0F; bool = false; }
      if (f1 > 22.0F) { f1 = 22.0F; bool = false; }
      if (f2 < -40.0F) { f2 = -40.0F; bool = false; }
      if (f2 <= 32.0F) break; f2 = 32.0F; bool = false; break;
    case 4:
      if (f1 < -34.0F) { f1 = -34.0F; bool = false; }
      if (f1 > 30.0F) { f1 = 30.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 32.0F) break; f2 = 32.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt) {
    case 0:
    case 1:
      break;
    case 2:
      this.FM.turret[0].setHealth(paramFloat);
      break;
    case 3:
      this.FM.turret[1].setHealth(paramFloat);
      break;
    case 4:
      this.FM.turret[2].setHealth(paramFloat);
      break;
    case 5:
      this.FM.turret[3].setHealth(paramFloat);
      this.FM.turret[4].setHealth(paramFloat);
    }
  }

  private static final float toMeters(float paramFloat)
  {
    return 0.3048F * paramFloat;
  }
  private static final float toMetersPerSecond(float paramFloat) {
    return 0.4470401F * paramFloat;
  }

  public boolean typeBomberToggleAutomation()
  {
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
    if (this.fSightCurForwardAngle > 85.0F) {
      this.fSightCurForwardAngle = 85.0F;
    }
    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });
    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjDistanceMinus()
  {
    this.fSightCurForwardAngle -= 1.0F;
    if (this.fSightCurForwardAngle < 0.0F) {
      this.fSightCurForwardAngle = 0.0F;
    }
    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });
    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjSideslipReset()
  {
    this.fSightCurSideslip = 0.0F;
  }

  public void typeBomberAdjSideslipPlus() {
    this.fSightCurSideslip += 0.05F;
    if (this.fSightCurSideslip > 3.0F) {
      this.fSightCurSideslip = 3.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Float(this.fSightCurSideslip * 10.0F) });
  }

  public void typeBomberAdjSideslipMinus() {
    this.fSightCurSideslip -= 0.05F;
    if (this.fSightCurSideslip < -3.0F) {
      this.fSightCurSideslip = -3.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Float(this.fSightCurSideslip * 10.0F) });
  }

  public void typeBomberAdjAltitudeReset() {
    this.fSightCurAltitude = 3000.0F;
  }

  public void typeBomberAdjAltitudePlus() {
    this.fSightCurAltitude += 50.0F;
    if (this.fSightCurAltitude > 50000.0F) {
      this.fSightCurAltitude = 50000.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new Object[] { new Integer((int)this.fSightCurAltitude) });
    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjAltitudeMinus() {
    this.fSightCurAltitude -= 50.0F;
    if (this.fSightCurAltitude < 1000.0F) {
      this.fSightCurAltitude = 1000.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new Object[] { new Integer((int)this.fSightCurAltitude) });
    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjSpeedReset() {
    this.fSightCurSpeed = 200.0F;
  }

  public void typeBomberAdjSpeedPlus() {
    this.fSightCurSpeed += 10.0F;
    if (this.fSightCurSpeed > 450.0F) {
      this.fSightCurSpeed = 450.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberAdjSpeedMinus() {
    this.fSightCurSpeed -= 10.0F;
    if (this.fSightCurSpeed < 100.0F) {
      this.fSightCurSpeed = 100.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberUpdate(float paramFloat) {
    if (Math.abs(this.FM.Or.getKren()) > 4.5D) {
      this.fSightCurReadyness -= 0.0666666F * paramFloat;
      if (this.fSightCurReadyness < 0.0F) {
        this.fSightCurReadyness = 0.0F;
      }
    }
    if (this.fSightCurReadyness < 1.0F) {
      this.fSightCurReadyness += 0.0333333F * paramFloat;
    } else if (this.bSightAutomation)
    {
      this.fSightCurDistance -= toMetersPerSecond(this.fSightCurSpeed) * paramFloat;
      if (this.fSightCurDistance < 0.0F) {
        this.fSightCurDistance = 0.0F;
        typeBomberToggleAutomation();
      }
      this.fSightCurForwardAngle = (float)Math.toDegrees(Math.atan(this.fSightCurDistance / toMeters(this.fSightCurAltitude)));
      if (this.fSightCurDistance < toMetersPerSecond(this.fSightCurSpeed) * Math.sqrt(toMeters(this.fSightCurAltitude) * 0.203874F)) {
        this.bSightBombDump = true;
      }
      if (this.bSightBombDump)
        if (this.FM.isTick(3, 0)) {
          if ((this.FM.CT.Weapons[3] != null) && (this.FM.CT.Weapons[3][(this.FM.CT.Weapons[3].length - 1)] != null) && (this.FM.CT.Weapons[3][(this.FM.CT.Weapons[3].length - 1)].haveBullets())) {
            this.FM.CT.WeaponControl[3] = true;
            HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightBombdrop");
          }
        }
        else this.FM.CT.WeaponControl[3] = false;
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

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
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
    Class localClass = B_25J1.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "B-25");
    Property.set(localClass, "meshName", "3DO/Plane/B-25J-1(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar03());
    Property.set(localClass, "meshName_us", "3DO/Plane/B-25J-1(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeBMPar03());

    Property.set(localClass, "noseart", 1);

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1956.6F);

    Property.set(localClass, "FlightModel", "FlightModels/B-25J.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitB25J.class, CockpitB25J_Bombardier.class, CockpitB25J_FGunner.class, CockpitB25J_TGunner.class, CockpitB25J_AGunner.class, CockpitB25J_RGunner.class, CockpitB25J_LGunner.class });

    Property.set(localClass, "LOSElevation", 0.73425F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 10, 11, 11, 12, 12, 13, 14, 9, 9, 9, 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 9, 9, 9, 9, 9, 9, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2 });

    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN13", "_MGUN14", "_MGUN15", "_MGUN16", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN11", "_MGUN12", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_BombSpawn02", "_BombSpawn03", "_BombSpawn01", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn01", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50ki 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 400", "MGunBrowning50t 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "40xParaF", new String[] { "MGunBrowning50ki 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 400", "MGunBrowning50t 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunParafrag8 20", "BombGunParafrag8 20", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "12x100lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 400", "MGunBrowning50t 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGun50kg 6", "BombGun50kg 6", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "3x250lbs3x500lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 400", "MGunBrowning50t 400", null, null, null, null, null, null, null, null, null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", "BombGunNull 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "6x250lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 400", "MGunBrowning50t 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGun250lbs 3", "BombGun250lbs 3", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "4x500lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 400", "MGunBrowning50t 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGun500lbs 2", "BombGun500lbs 2", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "4x500lbs1x1000lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 400", "MGunBrowning50t 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGun1000lbs 1", "BombGunNull 1", null, null, null, null, "BombGun500lbs 2", "BombGun500lbs 2", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "6x500lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 400", "MGunBrowning50t 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGun500lbs 3", "BombGun500lbs 3", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2x1000lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 400", "MGunBrowning50t 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "3x1000lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 400", "MGunBrowning50t 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGun1000lbs 1", "BombGunNull 1", null, null, null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "10x50kg", new String[] { "MGunBrowning50ki 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 400", "MGunBrowning50t 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunFAB50 5", "BombGunFAB50 5", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "8x100kg", new String[] { "MGunBrowning50ki 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50kpzl 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50t 400", "MGunBrowning50t 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunFAB100 4", "BombGunFAB100 4", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}