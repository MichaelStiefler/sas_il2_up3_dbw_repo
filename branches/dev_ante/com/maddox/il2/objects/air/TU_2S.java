package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class TU_2S extends TU_2
  implements TypeBomber
{
  public float fSightCurAltitude = 300.0F;
  public float fSightCurSpeed = 50.0F;
  public float fSightCurForwardAngle = 0.0F;
  public float fSightSetForwardAngle = 0.0F;
  public float fSightCurSideslip = 0.0F;

  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay01_D0", 0.0F, -85.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay02_D0", 0.0F, 85.0F * paramFloat, 0.0F);
  }

  public boolean typeBomberToggleAutomation() {
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
    this.fSightCurAltitude += 50.0F;
    if (this.fSightCurAltitude > 50000.0F)
      this.fSightCurAltitude = 50000.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });
  }

  public void typeBomberAdjAltitudeMinus()
  {
    this.fSightCurAltitude -= 50.0F;
    if (this.fSightCurAltitude < 1000.0F)
      this.fSightCurAltitude = 1000.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });
  }

  public void typeBomberAdjSpeedReset()
  {
    this.fSightCurSpeed = 50.0F;
  }

  public void typeBomberAdjSpeedPlus() {
    this.fSightCurSpeed += 5.0F;
    if (this.fSightCurSpeed > 520.0F)
      this.fSightCurSpeed = 520.0F;
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
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException
  {
    this.fSightCurAltitude = paramNetMsgInput.readFloat();
    this.fSightCurSpeed = paramNetMsgInput.readFloat();
  }

  static
  {
    Class localClass = TU_2S.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Tu-2");
    Property.set(localClass, "meshName", "3DO/Plane/Tu-2S/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar05());
    Property.set(localClass, "yearService", 1942.5F);
    Property.set(localClass, "yearExpired", 1956.6F);
    Property.set(localClass, "FlightModel", "FlightModels/Tu-2S.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitTU_2S.class, CockpitTU_2S_Bombardier.class, CockpitTU_2S_TGunner.class, CockpitTU_2S_RTGunner.class, CockpitTU_2S_BGunner.class });

    Property.set(localClass, "LOSElevation", 0.73425F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1, 10, 11, 12, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "6fab50", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50" });

    Aircraft.weaponsRegister(localClass, "8fab50", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB50", "BombGunFAB50", null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50" });

    Aircraft.weaponsRegister(localClass, "6fab100", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100" });

    Aircraft.weaponsRegister(localClass, "8fab100", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB100", "BombGunFAB100", null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100" });

    Aircraft.weaponsRegister(localClass, "1fab250", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, "BombGunFAB250", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2fab250", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250", null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2fab2506fab100", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250", null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100" });

    Aircraft.weaponsRegister(localClass, "3fab250", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4fab250", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, null, null, null, "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250" });

    Aircraft.weaponsRegister(localClass, "6fab250", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250", null, null, null, "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250" });

    Aircraft.weaponsRegister(localClass, "1fab500", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, "BombGunFAB500", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2fab500", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2fab5006fab50", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50" });

    Aircraft.weaponsRegister(localClass, "2fab5006fab100", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100" });

    Aircraft.weaponsRegister(localClass, "2fab5004fab250", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, null, null, "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250" });

    Aircraft.weaponsRegister(localClass, "3fab500", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4fab500", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, "BombGunFAB500", "BombGunFAB500", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "6fab500", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, null, null, "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500" });

    Aircraft.weaponsRegister(localClass, "1fab1000", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, "BombGunFAB1000", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1fab10002fab250", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250", "BombGunFAB1000", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2fab1000", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2fab10006fab50", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50" });

    Aircraft.weaponsRegister(localClass, "2fab10006fab100", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100" });

    Aircraft.weaponsRegister(localClass, "2fab10002fab250", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", null, "BombGunFAB250", "BombGunFAB250", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2fab10001fab500", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB500", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "3fab1000", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB1000", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1fab2000", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, "BombGunFAB2000", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}