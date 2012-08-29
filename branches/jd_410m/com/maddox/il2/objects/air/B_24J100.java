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

public class B_24J100 extends B_24
  implements TypeBomber, TypeX4Carrier, TypeGuidedBombCarrier
{
  public boolean bToFire = false;
  private long tX4Prev = 0L;
  private float deltaAzimuth = 0.0F;
  private float deltaTangage = 0.0F;
  private boolean isGuidingBomb = false;
  private boolean isMasterAlive;
  public static boolean bChangedPit = false;
  private boolean bSightAutomation;
  private boolean bSightBombDump;
  public float fSightCurDistance;
  public float fSightCurForwardAngle;
  public float fSightCurSideslip;
  public float fSightCurAltitude;
  public float fSightCurSpeed;
  public float fSightCurReadyness;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if (this.thisWeaponsName.endsWith("Bat"))
    {
      hierMesh().chunkVisible("BatWingRackR_D0", true);
      hierMesh().chunkVisible("BatWingRackL_D0", true);
      return;
    }
  }

  public boolean typeGuidedBombCisMasterAlive()
  {
    return this.isMasterAlive;
  }

  public void typeGuidedBombCsetMasterAlive(boolean paramBoolean)
  {
    this.isMasterAlive = paramBoolean;
  }

  public boolean typeGuidedBombCgetIsGuiding()
  {
    return this.isGuidingBomb;
  }

  public void typeGuidedBombCsetIsGuiding(boolean paramBoolean)
  {
    this.isGuidingBomb = paramBoolean;
  }

  public void typeX4CAdjSidePlus()
  {
    this.deltaAzimuth = 0.002F;
  }

  public void typeX4CAdjSideMinus()
  {
    this.deltaAzimuth = -0.002F;
  }

  public void typeX4CAdjAttitudePlus()
  {
    this.deltaTangage = 0.002F;
  }

  public void typeX4CAdjAttitudeMinus()
  {
    this.deltaTangage = -0.002F;
  }

  public void typeX4CResetControls()
  {
    this.deltaAzimuth = (this.deltaTangage = 0.0F);
  }

  public float typeX4CgetdeltaAzimuth() {
    return this.deltaAzimuth;
  }

  public float typeX4CgetdeltaTangage() {
    return this.deltaTangage;
  }

  public B_24J100()
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

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -85.0F) { f1 = -85.0F; bool = false; }
      if (f1 > 85.0F) { f1 = 85.0F; bool = false; }
      if (f2 < -32.0F) { f2 = -32.0F; bool = false; }
      if (f2 <= 46.0F) break; f2 = 46.0F; bool = false; break;
    case 1:
      if (f2 < -0.0F) { f2 = -0.0F; bool = false; }
      if (f2 <= 20.0F) break; f2 = 20.0F; bool = false; break;
    case 2:
      if (f2 < -70.0F) { f2 = -70.0F; bool = false; }
      if (f2 <= 7.0F) break; f2 = 7.0F; bool = false; break;
    case 3:
      if (f1 < -35.0F) { f1 = -35.0F; bool = false; }
      if (f1 > 64.0F) { f1 = 64.0F; bool = false; }
      if (f2 < -37.0F) { f2 = -37.0F; bool = false; }
      if (f2 <= 50.0F) break; f2 = 50.0F; bool = false; break;
    case 4:
      if (f1 < -67.0F) { f1 = -67.0F; bool = false; }
      if (f1 > 34.0F) { f1 = 34.0F; bool = false; }
      if (f2 < -37.0F) { f2 = -37.0F; bool = false; }
      if (f2 <= 50.0F) break; f2 = 50.0F; bool = false; break;
    case 5:
      if (f1 < -85.0F) { f1 = -85.0F; bool = false; }
      if (f1 > 85.0F) { f1 = 85.0F; bool = false; }
      if (f2 < -32.0F) { f2 = -32.0F; bool = false; }
      if (f2 <= 46.0F) break; f2 = 46.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
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
    if (this.fSightCurForwardAngle > 85.0F)
      this.fSightCurForwardAngle = 85.0F;
    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));

    if (!this.isGuidingBomb) {
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });
    }

    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjDistanceMinus()
  {
    this.fSightCurForwardAngle -= 1.0F;
    if (this.fSightCurForwardAngle < 0.0F)
      this.fSightCurForwardAngle = 0.0F;
    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));

    if (!this.isGuidingBomb) {
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });
    }

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
    if (this.fSightCurSideslip > 3.0F) {
      this.fSightCurSideslip = 3.0F;
    }
    if (!this.isGuidingBomb)
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 10.0F)) });
  }

  public void typeBomberAdjSideslipMinus()
  {
    this.fSightCurSideslip -= 0.1F;
    if (this.fSightCurSideslip < -3.0F) {
      this.fSightCurSideslip = -3.0F;
    }
    if (!this.isGuidingBomb)
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 10.0F)) });
  }

  public void typeBomberAdjAltitudeReset()
  {
    this.fSightCurAltitude = 3000.0F;
  }

  public void typeBomberAdjAltitudePlus()
  {
    this.fSightCurAltitude += 50.0F;
    if (this.fSightCurAltitude > 50000.0F) {
      this.fSightCurAltitude = 50000.0F;
    }
    if (!this.isGuidingBomb) {
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new Object[] { new Integer((int)this.fSightCurAltitude) });
    }

    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjAltitudeMinus()
  {
    this.fSightCurAltitude -= 50.0F;
    if (this.fSightCurAltitude < 1000.0F) {
      this.fSightCurAltitude = 1000.0F;
    }
    if (!this.isGuidingBomb) {
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new Object[] { new Integer((int)this.fSightCurAltitude) });
    }

    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjSpeedReset()
  {
    this.fSightCurSpeed = 200.0F;
  }

  public void typeBomberAdjSpeedPlus()
  {
    this.fSightCurSpeed += 10.0F;
    if (this.fSightCurSpeed > 450.0F) {
      this.fSightCurSpeed = 450.0F;
    }
    if (!this.isGuidingBomb)
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberAdjSpeedMinus()
  {
    this.fSightCurSpeed -= 10.0F;
    if (this.fSightCurSpeed < 100.0F) {
      this.fSightCurSpeed = 100.0F;
    }
    if (!this.isGuidingBomb)
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH", new Object[] { new Integer((int)this.fSightCurSpeed) });
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
        if (this.FM.isTick(3, 0))
        {
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
    Class localClass = B_24J100.class;
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

    weaponTriggersRegister(localClass, new int[] { 10, 10, 11, 11, 12, 12, 13, 14, 15, 15, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_MGUN09", "_MGUN10", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_BombSpawn08", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50t 365", "MGunBrowning50t 365", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "16x500", new String[] { "MGunBrowning50t 365", "MGunBrowning50t 365", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun500lbs 2", "BombGun500lbs 2", "BombGun500lbs 2", "BombGun500lbs 2", "BombGun500lbs 2", "BombGun500lbs 2", "BombGun500lbs 2", "BombGun500lbs 2", null, null, null, null });

    weaponsRegister(localClass, "16xRazon", new String[] { "MGunBrowning50t 365", "MGunBrowning50t 365", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 500", "MGunBrowning50t 500", "RocketGunRazon 2", "RocketGunRazon 2", "RocketGunRazon 2", "RocketGunRazon 2", "RocketGunRazon 2", "RocketGunRazon 2", "RocketGunRazon 2", "RocketGunRazon 2", null, null, null, null });

    weaponsRegister(localClass, "8xRazon", new String[] { "MGunBrowning50t 365", "MGunBrowning50t 365", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 500", "MGunBrowning50t 500", "RocketGunRazon 2", "RocketGunRazon 2", "RocketGunRazon 2", "RocketGunRazon 2", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xBat", new String[] { "MGunBrowning50t 365", "MGunBrowning50t 365", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, null, null, null, null, null, null, "RocketGunBat 1", "BombGunNull 1", "BombGunNull 1", "RocketGunBat 1" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}