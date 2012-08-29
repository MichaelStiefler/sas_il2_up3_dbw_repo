package com.maddox.il2.objects.air;

import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class MOSQUITO4 extends MOSQUITO
  implements TypeBomber
{
  public float fSightCurAltitude = 300.0F;
  public float fSightCurSpeed = 50.0F;

  public boolean typeBomberToggleAutomation() {
    return false;
  }

  public void typeBomberAdjDistanceReset() {
  }

  public void typeBomberAdjDistancePlus() {
  }

  public void typeBomberAdjDistanceMinus() {
  }

  public void typeBomberAdjSideslipReset() {
  }

  public void typeBomberAdjSideslipPlus() {
  }

  public void typeBomberAdjSideslipMinus() {
  }

  public void typeBomberAdjAltitudeReset() {
    this.fSightCurAltitude = 300.0F;
  }

  public void typeBomberAdjAltitudePlus() {
    this.fSightCurAltitude += 50.0F;
    if (this.fSightCurAltitude > 5000.0F) {
      this.fSightCurAltitude = 5000.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });
  }

  public void typeBomberAdjAltitudeMinus() {
    this.fSightCurAltitude -= 50.0F;
    if (this.fSightCurAltitude < 300.0F) {
      this.fSightCurAltitude = 300.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });
  }

  public void typeBomberAdjSpeedReset() {
    this.fSightCurSpeed = 50.0F;
  }

  public void typeBomberAdjSpeedPlus() {
    this.fSightCurSpeed += 5.0F;
    if (this.fSightCurSpeed > 350.0F) {
      this.fSightCurSpeed = 350.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberAdjSpeedMinus() {
    this.fSightCurSpeed -= 5.0F;
    if (this.fSightCurSpeed < 50.0F) {
      this.fSightCurSpeed = 50.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberUpdate(float paramFloat) {
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
    paramNetMsgGuaranted.writeFloat(this.fSightCurAltitude);
    paramNetMsgGuaranted.writeFloat(this.fSightCurSpeed);
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
    this.fSightCurAltitude = paramNetMsgInput.readFloat();
    this.fSightCurSpeed = paramNetMsgInput.readFloat();
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Mosquito");
    Property.set(localClass, "meshName", "3DO/Plane/Mosquito_B_MkIV(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());
    Property.set(localClass, "meshName_gb", "3DO/Plane/Mosquito_B_MkIV(GB)/hier.him");
    Property.set(localClass, "PaintScheme_gb", new PaintSchemeFMPar06());

    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1946.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Mosquito-BMkIV.fmd");

    weaponTriggersRegister(localClass, new int[] { 0, 3, 3, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_Clip04", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04" });

    weaponsRegister(localClass, "default", new String[] { null, null, null, null, null });

    weaponsRegister(localClass, "4x250", new String[] { null, "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1" });

    weaponsRegister(localClass, "4x500", new String[] { null, "BombGun500lbsE 1", "BombGun500lbsE 1", "BombGun500lbsE 1", "BombGun500lbsE 1" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null });
  }
}