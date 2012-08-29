package com.maddox.il2.objects.air;

import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class FW_200C3U4 extends FW_200
  implements TypeBomber
{
  public float fSightCurAltitude = 300.0F;
  public float fSightCurSpeed = 50.0F;
  public float fSightCurForwardAngle = 0.0F;
  public float fSightSetForwardAngle = 0.0F;
  public float fSightCurSideslip = 0.0F;

  public boolean typeBomberToggleAutomation()
  {
    return false;
  }

  public void typeBomberAdjDistanceReset() {
    this.fSightCurForwardAngle = 0.0F;
  }

  public void typeBomberAdjDistancePlus() {
    this.fSightCurForwardAngle += 0.2F;
    if (this.fSightCurForwardAngle > 75.0F)
      this.fSightCurForwardAngle = 75.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)(this.fSightCurForwardAngle * 1.0F)) });
  }

  public void typeBomberAdjDistanceMinus()
  {
    this.fSightCurForwardAngle -= 0.2F;
    if (this.fSightCurForwardAngle < -15.0F)
      this.fSightCurForwardAngle = -15.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)(this.fSightCurForwardAngle * 1.0F)) });
  }

  public void typeBomberAdjSideslipReset()
  {
    this.fSightCurSideslip = 0.0F;
  }

  public void typeBomberAdjSideslipPlus() {
    this.fSightCurSideslip += 1.0F;
    if (this.fSightCurSideslip > 45.0F)
      this.fSightCurSideslip = 45.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 1.0F)) });
  }

  public void typeBomberAdjSideslipMinus()
  {
    this.fSightCurSideslip -= 1.0F;
    if (this.fSightCurSideslip < -45.0F)
      this.fSightCurSideslip = -45.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 1.0F)) });
  }

  public void typeBomberAdjAltitudeReset()
  {
    this.fSightCurAltitude = 300.0F;
  }

  public void typeBomberAdjAltitudePlus() {
    this.fSightCurAltitude += 10.0F;
    if (this.fSightCurAltitude > 6000.0F)
      this.fSightCurAltitude = 6000.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });
  }

  public void typeBomberAdjAltitudeMinus()
  {
    this.fSightCurAltitude -= 10.0F;
    if (this.fSightCurAltitude < 300.0F)
      this.fSightCurAltitude = 300.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });
  }

  public void typeBomberAdjSpeedReset()
  {
    this.fSightCurSpeed = 50.0F;
  }

  public void typeBomberAdjSpeedPlus() {
    this.fSightCurSpeed += 5.0F;
    if (this.fSightCurSpeed > 650.0F)
      this.fSightCurSpeed = 650.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberAdjSpeedMinus()
  {
    this.fSightCurSpeed -= 5.0F;
    if (this.fSightCurSpeed < 50.0F)
      this.fSightCurSpeed = 50.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberUpdate(float paramFloat)
  {
    double d = this.fSightCurSpeed / 3.6D * Math.sqrt(this.fSightCurAltitude * 0.203873598D);

    d -= this.fSightCurAltitude * this.fSightCurAltitude * 1.419E-005D;
    this.fSightSetForwardAngle = (float)Math.toDegrees(Math.atan(d / this.fSightCurAltitude));
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
    paramNetMsgGuaranted.writeFloat(this.fSightCurAltitude);
    paramNetMsgGuaranted.writeFloat(this.fSightCurSpeed);
    paramNetMsgGuaranted.writeFloat(this.fSightCurForwardAngle);
    paramNetMsgGuaranted.writeFloat(this.fSightCurSideslip);
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException
  {
    this.fSightCurAltitude = paramNetMsgInput.readFloat();
    this.fSightCurSpeed = paramNetMsgInput.readFloat();
    this.fSightCurForwardAngle = paramNetMsgInput.readFloat();
    this.fSightCurSideslip = paramNetMsgInput.readFloat();
  }

  static
  {
    Class localClass = FW_200C3U4.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "FW200");
    Property.set(localClass, "meshName", "3DO/Plane/FW-200C-3U4/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar04());
    Property.set(localClass, "yearService", 1941.1F);
    Property.set(localClass, "yearExpired", 1949.0F);
    Property.set(localClass, "FlightModel", "FlightModels/FW-200C-3U4.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitFW200.class, CockpitFW200_Bombardier.class, CockpitFW200_FGunner.class, CockpitFW200_BGunner.class, CockpitFW200_TGunner.class });

    Property.set(localClass, "LOSElevation", 0.73425F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 13, 14, 15, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_BombSpawn01", "_BombSpawn02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_BombSpawn08", "_BombSpawn09", "_BombSpawn10", "_BombSpawn11", "_BombSpawn12", "_BombSpawn13", "_BombSpawn14" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "12sc50", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, null, null, null, null, "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50" });

    Aircraft.weaponsRegister(localClass, "12sc502sc250", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, "BombGunSC250", "BombGunSC250", null, null, "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50" });

    Aircraft.weaponsRegister(localClass, "12sc504sc250", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, "BombGunSC250", "BombGunSC250", "BombGunSC250", "BombGunSC250", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50" });

    Aircraft.weaponsRegister(localClass, "12sc504sc500", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, "BombGunSC500", "BombGunSC500", "BombGunSC500", "BombGunSC500", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50" });

    Aircraft.weaponsRegister(localClass, "12sc502sc1000", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, "BombGunSC1000", "BombGunSC1000", null, null, "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50" });

    Aircraft.weaponsRegister(localClass, "12sc504sc1000", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, "BombGunSC1000", "BombGunSC1000", "BombGunSC1000", "BombGunSC1000", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50" });

    Aircraft.weaponsRegister(localClass, "2sc250", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSC250", "BombGunSC250", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4sc250", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSC250", "BombGunSC250", null, null, "BombGunSC250", "BombGunSC250", null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "6sc250", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSC250", "BombGunSC250", "BombGunSC250", "BombGunSC250", "BombGunSC250", "BombGunSC250", null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2sc500", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSC500", "BombGunSC500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4sc500", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSC500", "BombGunSC500", null, null, "BombGunSC500", "BombGunSC500", null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "6sc500", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSC500", "BombGunSC500", "BombGunSC500", "BombGunSC500", "BombGunSC500", "BombGunSC500", null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2sd500", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSD500", "BombGunSD500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4sd500", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSD500", "BombGunSD500", null, null, "BombGunSD500", "BombGunSD500", null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "6sd500", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSD500", "BombGunSD500", "BombGunSD500", "BombGunSD500", "BombGunSD500", "BombGunSD500", null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2sc1000", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, null, null, "BombGunSC1000", "BombGunSC1000", null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4sc1000", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, "BombGunSC1000", "BombGunSC1000", "BombGunSC1000", "BombGunSC1000", null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2sc1800", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, null, null, "BombGunSC1800", "BombGunSC1800", null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2ab1000", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, null, null, "BombGunAB1000", "BombGunAB1000", null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4ab1000", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, "BombGunAB1000", "BombGunAB1000", "BombGunAB1000", "BombGunAB1000", null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2pc1600", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, null, null, "BombGunPC1600", "BombGunPC1600", null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}