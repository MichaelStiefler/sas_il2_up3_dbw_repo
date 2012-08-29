package com.maddox.il2.objects.air;

import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.objects.weapons.ToKGUtils;
import com.maddox.il2.objects.weapons.Torpedo;
import com.maddox.rts.Property;

public class HE_111H6 extends HE_111
  implements TypeHasToKG
{
  protected float fAOB;
  protected float fShipSpeed;
  public boolean hasToKG = false;
  protected int spreadAngle = 0;

  public void update(float paramFloat)
  {
    if (this.FM.turret[5].tMode == 2) {
      this.FM.turret[5].tMode = 4;
    }
    super.update(paramFloat);
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    Object[] arrayOfObject = this.pos.getBaseAttached();
    if (arrayOfObject != null)
    {
      for (int i = 0; i < arrayOfObject.length; i++)
      {
        if (!(arrayOfObject[i] instanceof Torpedo))
          continue;
        this.hasToKG = true;
        return;
      }
    }
  }

  public void typeBomberAdjSideslipPlus()
  {
    if (this.hasToKG)
    {
      this.fAOB += 1.0F;
      if (this.fAOB > 180.0F) {
        this.fAOB = 180.0F;
      }
      HUD.log(AircraftHotKeys.hudLogWeaponId, "TOKGAOB", new Object[] { new Integer((int)this.fAOB) });
      ToKGUtils.setTorpedoGyroAngle(this.FM, this.fAOB, this.fShipSpeed);
    }
    else {
      super.typeBomberAdjSideslipPlus();
    }
  }

  public void typeBomberAdjSideslipMinus() {
    if (this.hasToKG)
    {
      this.fAOB -= 1.0F;
      if (this.fAOB < -180.0F) {
        this.fAOB = -180.0F;
      }
      HUD.log(AircraftHotKeys.hudLogWeaponId, "TOKGAOB", new Object[] { new Integer((int)this.fAOB) });
      ToKGUtils.setTorpedoGyroAngle(this.FM, this.fAOB, this.fShipSpeed);
    }
    else {
      super.typeBomberAdjSideslipMinus();
    }
  }

  public void typeBomberAdjSpeedPlus()
  {
    if (this.hasToKG)
    {
      this.fShipSpeed += 1.0F;
      if (this.fShipSpeed > 35.0F) {
        this.fShipSpeed = 35.0F;
      }
      HUD.log(AircraftHotKeys.hudLogWeaponId, "TOKGSpeed", new Object[] { new Integer((int)this.fShipSpeed) });
      ToKGUtils.setTorpedoGyroAngle(this.FM, this.fAOB, this.fShipSpeed);
    }
    else {
      super.typeBomberAdjSpeedPlus();
    }
  }

  public void typeBomberAdjSpeedMinus() {
    if (this.hasToKG)
    {
      this.fShipSpeed -= 1.0F;
      if (this.fShipSpeed < 0.0F) {
        this.fShipSpeed = 0.0F;
      }
      HUD.log(AircraftHotKeys.hudLogWeaponId, "TOKGSpeed", new Object[] { new Integer((int)this.fShipSpeed) });
      ToKGUtils.setTorpedoGyroAngle(this.FM, this.fAOB, this.fShipSpeed);
    }
    else {
      super.typeBomberAdjSpeedMinus();
    }
  }

  public void typeBomberAdjAltitudeReset() {
    if (!this.hasToKG)
      super.typeBomberAdjAltitudeReset();
  }

  public void typeBomberAdjAltitudePlus() {
    if (!this.hasToKG)
      super.typeBomberAdjAltitudePlus();
  }

  public void typeBomberAdjAltitudeMinus() {
    if (!this.hasToKG)
      super.typeBomberAdjAltitudeMinus();
  }

  public void typeBomberAdjSpeedReset() {
    if (!this.hasToKG)
      super.typeBomberAdjSpeedReset(); 
  }

  public void typeBomberAdjDistanceReset() {
    if (!this.hasToKG)
      super.typeBomberAdjDistanceReset();
  }

  public void typeBomberAdjDistancePlus() {
    if (!this.hasToKG) {
      super.typeBomberAdjDistancePlus();
    }
    else {
      this.spreadAngle += 1;
      if (this.spreadAngle > 30) {
        this.spreadAngle = 30;
      }
      HUD.log(AircraftHotKeys.hudLogWeaponId, "TOKGSpread", new Object[] { new Integer(this.spreadAngle) });
      this.FM.AS.setSpreadAngle(this.spreadAngle);
    }
  }

  public void typeBomberAdjDistanceMinus() {
    if (!this.hasToKG) {
      super.typeBomberAdjDistanceMinus();
    }
    else {
      this.spreadAngle -= 1;
      if (this.spreadAngle < 0) {
        this.spreadAngle = 0;
      }
      HUD.log(AircraftHotKeys.hudLogWeaponId, "TOKGSpread", new Object[] { new Integer(this.spreadAngle) });
      this.FM.AS.setSpreadAngle(this.spreadAngle);
    }
  }

  public void typeBomberAdjSideslipReset() {
    if (!this.hasToKG)
      super.typeBomberAdjSideslipReset();
  }

  public boolean isSalvo()
  {
    return this.thisWeaponsName.indexOf("spread") == -1;
  }

  static
  {
    Class localClass = HE_111H6.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "He-111");
    Property.set(localClass, "meshName", "3do/plane/He-111H-6/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());

    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/He-111H-6.fmd");

    Property.set(localClass, "cockpitClass", new Class[] { CockpitHE_111H6.class, CockpitHE_111H6_Bombardier.class, CockpitHE_111H6_NGunner.class, CockpitHE_111H2_TGunner.class, CockpitHE_111H2_BGunner.class, CockpitHE_111H2_LGunner.class, CockpitHE_111H2_RGunner.class });

    weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 13, 14, 15, 3, 3, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04" });

    weaponsRegister(localClass, "default", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", null, null, null, null });

    weaponsRegister(localClass, "4xSD250", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSD500", "BombGunSD500", null, null });

    weaponsRegister(localClass, "4xSC500", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC500", "BombGunSC500", null, null });

    weaponsRegister(localClass, "4xAB500", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunAB500", "BombGunAB500", null, null });

    weaponsRegister(localClass, "1SC1000", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC1000", null, null, null });

    weaponsRegister(localClass, "2SC1000", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC1000", "BombGunSC1000", null, null });

    weaponsRegister(localClass, "2PC1600", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunPC1600", null, null, null });

    weaponsRegister(localClass, "1SC1800", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC1800", null, null, null });

    weaponsRegister(localClass, "2SC2000", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC2000", null, null, null });

    weaponsRegister(localClass, "2xTorp", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunTorpF5BheavyL", "BombGunTorpF5BheavyR", null, null });

    weaponsRegister(localClass, "2xTorp_spread", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunTorpF5BheavyL", "BombGunNull", "BombGunNull", "BombGunTorpF5BheavyR" });

    weaponsRegister(localClass, "2xTorp_LTW", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunTorpFiumeL", "BombGunTorpFiumeR", null, null });

    weaponsRegister(localClass, "2xTorp_LTW_spread", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunTorpFiumeL", "BombGunNull", "BombGunNull", "BombGunTorpFiumeR" });

    weaponsRegister(localClass, "2xTorp_Practice_spread", new String[] { "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunTorpLTF5PracticeL", "BombGunNull", "BombGunNull", "BombGunTorpLTF5PracticeR" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null });
  }
}