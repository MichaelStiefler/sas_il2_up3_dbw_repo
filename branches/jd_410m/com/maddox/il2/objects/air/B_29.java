package com.maddox.il2.objects.air;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class B_29 extends B_29X
  implements TypeBomber, TypeX4Carrier, TypeGuidedBombCarrier
{
  public boolean bToFire = false;
  private long tX4Prev = 0L;
  private float deltaAzimuth = 0.0F;
  private float deltaTangage = 0.0F;
  private boolean isGuidingBomb = false;
  private boolean isMasterAlive;

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

  public float typeX4CgetdeltaAzimuth()
  {
    return this.deltaAzimuth;
  }

  public float typeX4CgetdeltaTangage()
  {
    return this.deltaTangage;
  }

  public boolean typeBomberToggleAutomation()
  {
    return false;
  }

  public void typeBomberAdjDistanceReset()
  {
  }

  public void typeBomberAdjDistancePlus()
  {
  }

  public void typeBomberAdjDistanceMinus()
  {
  }

  public void typeBomberAdjSideslipReset()
  {
  }

  public void typeBomberAdjSideslipPlus() {
  }

  public void typeBomberAdjSideslipMinus() {
  }

  public void typeBomberAdjAltitudeReset() {
  }

  public void typeBomberAdjAltitudePlus() {
  }

  public void typeBomberAdjAltitudeMinus() {
  }

  public void typeBomberAdjSpeedReset() {
  }

  public void typeBomberAdjSpeedPlus() {
  }

  public void typeBomberAdjSpeedMinus() {
  }

  public void typeBomberUpdate(float paramFloat) {
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
  }

  static {
    Class localClass = B_29.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "B-29");

    Property.set(localClass, "meshName", "3DO/Plane/B-29(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar05());
    Property.set(localClass, "meshName_us", "3DO/Plane/B-29(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar06());

    Property.set(localClass, "noseart", 1);

    Property.set(localClass, "yearService", 1943.5F);
    Property.set(localClass, "yearExpired", 2800.8999F);

    Property.set(localClass, "FlightModel", "FlightModels/B-29.fmd");

    weaponTriggersRegister(localClass, new int[] { 10, 10, 10, 10, 11, 11, 12, 12, 13, 13, 14, 14, 3, 3, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_MGUN09", "_MGUN10", "_MGUN11", "_MGUN12", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, null, null });

    weaponsRegister(localClass, "1x1600", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, null, "BombGun1600lbs 1" });

    weaponsRegister(localClass, "6x300", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, "BombGun300lbs 3", "BombGun300lbs 3" });

    weaponsRegister(localClass, "20x100", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun50kg 3", "BombGun50kg 3", "BombGun50kg 7", "BombGun50kg 7" });

    weaponsRegister(localClass, "4x500", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, "BombGun500lbs 2", "BombGun500lbs 2" });

    weaponsRegister(localClass, "2x1000", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, "BombGun1000lbs 1", "BombGun1000lbs 1" });

    weaponsRegister(localClass, "1x2000", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, null, "BombGun2000lbs 1" });

    weaponsRegister(localClass, "4x1000", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, "BombGun1000lbs 2", "BombGun1000lbs 2" });

    weaponsRegister(localClass, "2x2000", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, "BombGun2000lbs 1", "BombGun2000lbs 1" });

    weaponsRegister(localClass, "16x300", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun300lbs 8", "BombGun300lbs 8", null, null });

    weaponsRegister(localClass, "10x500", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun500lbs 5", "BombGun500lbs 5", null, null });

    weaponsRegister(localClass, "20x250", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun250lbs 8", "BombGun250lbs 8", "BombGun250lbs 2", "BombGun250lbs 2" });

    weaponsRegister(localClass, "6x1600", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun1600lbs 1", "BombGun1600lbs 1", "BombGun1600lbs 2", "BombGun1600lbs 2" });

    weaponsRegister(localClass, "20x500", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun500lbs 8", "BombGun500lbs 8", "BombGun500lbs 2", "BombGun500lbs 2" });

    weaponsRegister(localClass, "12x1000", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun1000lbs 1", "BombGun1000lbs 1", "BombGun1000lbs 2", "BombGun1000lbs 2" });

    weaponsRegister(localClass, "6x2000", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun2000lbs 1", "BombGun2000lbs 1", "BombGun2000lbs 2", "BombGun2000lbs 2" });

    weaponsRegister(localClass, "12x1600", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun1600lbs 1", "BombGun1600lbs 1", "BombGun1600lbs 2", "BombGun1600lbs 2" });

    weaponsRegister(localClass, "20x1000", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun1000lbs 8", "BombGun1000lbs 8", "BombGun1000lbs 2", "BombGun1000lbs 2" });

    weaponsRegister(localClass, "10x2000", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun2000lbs 3", "BombGun2000lbs 3", "BombGun2000lbs 2", "BombGun2000lbs 2" });

    weaponsRegister(localClass, "4xRazon", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, "RocketGunRazon 2", "RocketGunRazon 2" });

    weaponsRegister(localClass, "10xRazon", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "RocketGunRazon 5", "RocketGunRazon 5", null, null });

    weaponsRegister(localClass, "20xRazon", new String[] { "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 500", "RocketGunRazon 8", "RocketGunRazon 8", "RocketGunRazon 2", "RocketGunRazon 2" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}