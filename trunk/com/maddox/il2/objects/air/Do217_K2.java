package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class Do217_K2 extends Do217
  implements TypeX4Carrier, TypeGuidedBombCarrier
{
  public boolean bToFire = false;
  private long tX4Prev = 0L;
  private float deltaAzimuth = 0.0F;
  private float deltaTangage = 0.0F;
  private boolean isGuidingBomb = false;
  private boolean isMasterAlive;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if ((this.thisWeaponsName.startsWith("1xFritzX")) || (this.thisWeaponsName.startsWith("2xFritzX")))
    {
      hierMesh().chunkVisible("WingRackR_D0", true);
      hierMesh().chunkVisible("WingRackL_D0", true);
    }
    if ((this.thisWeaponsName.startsWith("1xHS293")) || (this.thisWeaponsName.startsWith("2xHS293")))
    {
      hierMesh().chunkVisible("WingRackR1_D0", true);
      hierMesh().chunkVisible("WingRackL1_D0", true);
    }
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    float f1 = -paramArrayOfFloat[0];
    float f2 = paramArrayOfFloat[1];
    if (paramInt == 5)
    {
      if (f2 > 5.0F)
      {
        f2 = 5.0F;
        bool = false;
      }
      if (f2 < -5.0F)
      {
        f2 = -5.0F;
        bool = false;
      }

      if (f1 > 5.0F)
      {
        f1 = 5.0F;
        bool = false;
      }
      if (f1 < -5.0F)
      {
        f1 = -5.0F;
        bool = false;
      }
    }
    paramArrayOfFloat[0] = (-f1);
    paramArrayOfFloat[1] = f2;
    return bool;
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
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

  public void typeX4CAdjSidePlus() {
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

  public void typeBomberAdjDistancePlus()
  {
    this.fSightCurForwardAngle += 1.0F;
    if (this.fSightCurForwardAngle > 85.0F)
      this.fSightCurForwardAngle = 85.0F;
    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));

    if (!this.isGuidingBomb) {
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });
    }

    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjDistanceMinus() {
    this.fSightCurForwardAngle -= 1.0F;
    if (this.fSightCurForwardAngle < 0.0F)
      this.fSightCurForwardAngle = 0.0F;
    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));

    if (!this.isGuidingBomb) {
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });
    }

    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjSideslipPlus() {
    if (!this.isGuidingBomb)
    {
      this.fSightCurSideslip += 0.1F;
      if (this.fSightCurSideslip > 3.0F)
        this.fSightCurSideslip = 3.0F;
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 10.0F)) });
    }
  }

  public void typeBomberAdjSideslipMinus()
  {
    if (!this.isGuidingBomb)
    {
      this.fSightCurSideslip -= 0.1F;
      if (this.fSightCurSideslip < -3.0F)
        this.fSightCurSideslip = -3.0F;
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 10.0F)) });
    }
  }

  public void typeBomberAdjAltitudePlus()
  {
    if (!this.isGuidingBomb)
    {
      this.fSightCurAltitude += 10.0F;
      if (this.fSightCurAltitude > 10000.0F)
        this.fSightCurAltitude = 10000.0F;
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });

      this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
    }
  }

  public void typeBomberAdjAltitudeMinus()
  {
    if (!this.isGuidingBomb)
    {
      this.fSightCurAltitude -= 10.0F;
      if (this.fSightCurAltitude < 850.0F)
        this.fSightCurAltitude = 850.0F;
      HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });

      this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
    }
  }

  protected void mydebug(String paramString)
  {
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Do-217");
    Property.set(localClass, "meshName", "3do/plane/Do217_K2/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1945.0F);
    Property.set(localClass, "FlightModel", "FlightModels/Do217K-2.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 10, 11, 12, 13, 14, 15, 15, 15, 15, 3, 3, 3, 3, 3, 3, 3, 3, 9, 9 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN10", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_MGUN09", "_ExternalBomb01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xFritzX", new String[] { "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", "RocketGunFritzX 1", "BombGunNull 1", "RocketGunFritzX 1", "BombGunNull 1", null, null, null, null, null, null });

    weaponsRegister(localClass, "2xHS293", new String[] { "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", null, null, null, null, "RocketGunHS_293 1", "BombGunNull 1", "RocketGunHS_293 1", "BombGunNull 1", null, null });

    weaponsRegister(localClass, "1xFritzX", new String[] { "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", "RocketGunFritzX 1", null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "1xFritzX+1x300LTank", new String[] { "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", "RocketGunFritzX 1", null, null, null, null, null, null, null, "FuelTankGun_Type_D 1", null });

    weaponsRegister(localClass, "1xHS293+1x300LTank", new String[] { "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", null, null, null, null, "RocketGunHS_293 1", null, null, null, null, "FuelTankGun_Type_D 1" });

    weaponsRegister(localClass, "1xFritzX+1x900LTank", new String[] { "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", "RocketGunFritzX 1", null, null, null, null, null, null, null, "FuelTankGun_Tank900 1", null });

    weaponsRegister(localClass, "1xHS293+1x900LTank", new String[] { "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", null, null, null, null, "RocketGunHS_293 1", null, null, null, null, "FuelTankGun_Tank900 1" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}