package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Property;

public class HE_111H12 extends HE_111
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

    if (this.thisWeaponsName.endsWith("X"))
    {
      hierMesh().chunkVisible("Bombsight_D0", true);
    }
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt) {
    case 0:
      break;
    case 1:
      this.FM.turret[0].setHealth(paramFloat);
      break;
    case 2:
      this.FM.turret[1].setHealth(paramFloat);
      this.FM.turret[2].setHealth(paramFloat);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    if (paramBoolean) {
      if (this.FM.AS.astateEngineStates[0] > 3) {
        if (World.Rnd().nextFloat() < 0.05F) this.FM.AS.hitTank(this, 0, 1);
        if (World.Rnd().nextFloat() < 0.05F) this.FM.AS.hitTank(this, 1, 1);
      }
      if (this.FM.AS.astateEngineStates[1] > 3) {
        if (World.Rnd().nextFloat() < 0.05F) this.FM.AS.hitTank(this, 2, 1);
        if (World.Rnd().nextFloat() < 0.05F) this.FM.AS.hitTank(this, 3, 1);
      }
      if ((this.FM.AS.astateTankStates[0] > 5) && (World.Rnd().nextFloat() < 0.02F)) nextDMGLevel(this.FM.AS.astateEffectChunks[0] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[1] > 5) && (World.Rnd().nextFloat() < 0.02F)) nextDMGLevel(this.FM.AS.astateEffectChunks[1] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[1] > 5) && (World.Rnd().nextFloat() < 0.125F)) this.FM.AS.hitTank(this, 2, 1);
      if ((this.FM.AS.astateTankStates[2] > 5) && (World.Rnd().nextFloat() < 0.125F)) this.FM.AS.hitTank(this, 1, 1);
      if ((this.FM.AS.astateTankStates[2] > 5) && (World.Rnd().nextFloat() < 0.02F)) nextDMGLevel(this.FM.AS.astateEffectChunks[2] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[3] > 5) && (World.Rnd().nextFloat() < 0.02F)) nextDMGLevel(this.FM.AS.astateEffectChunks[3] + "0", 0, this);
    }

    for (int i = 1; i < 3; i++) {
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else {
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
      }

    }

    mydebug("========================== isGuidingBomb = " + this.isGuidingBomb);
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
    mydebug("Chimata typeX4CAdjSidePlus, deltaAzimuth = " + this.deltaAzimuth);
  }

  public void typeX4CAdjSideMinus() {
    this.deltaAzimuth = -0.002F;
    mydebug("Chimata typeX4CAdjSideMinus, deltaAzimuth = " + this.deltaAzimuth);
  }

  public void typeX4CAdjAttitudePlus() {
    this.deltaTangage = 0.002F;
    mydebug("Chimata typeX4CAdjAttitudePlus, deltaTangage = " + this.deltaTangage);
  }

  public void typeX4CAdjAttitudeMinus() {
    this.deltaTangage = -0.002F;
    mydebug("Chimata typeX4CAdjAttitudeMinus, deltaTangage = " + this.deltaTangage);
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

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    if (paramString.equals("xxarmorg1"))
      getEnergyPastArmor(5.0F, paramShot);
    else
      super.hitBone(paramString, paramShot, paramPoint3d);
  }

  protected void mydebug(String paramString)
  {
  }

  static
  {
    Class localClass = HE_111H12.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "He-111");
    Property.set(localClass, "meshName", "3do/plane/He-111H-12/hier_h12.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1948.0F);
    Property.set(localClass, "FlightModel", "FlightModels/He-111H-12.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitHE_111H12.class, CockpitHE_111H12_Bombardier.class, CockpitHE_111H6_NGunner.class, CockpitHE_111H12_TGunner.class });

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 11, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02" });

    weaponsRegister(localClass, "default", new String[] { "MGunMGFFt 250", "MGunMG131t 600", null, null });
    weaponsRegister(localClass, "1xHS-293", new String[] { "MGunMGFFt 250", "MGunMG131t 600", "RocketGunHS_293", null });
    weaponsRegister(localClass, "1xFritzX", new String[] { "MGunMGFFt 250", "MGunMG131t 600", null, "RocketGunFritzX" });
    weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}