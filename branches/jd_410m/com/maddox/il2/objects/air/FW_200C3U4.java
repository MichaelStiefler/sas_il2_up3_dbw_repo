package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class FW_200C3U4 extends FW_200
  implements TypeBomber, TypeX4Carrier, TypeGuidedBombCarrier
{
  private float deltaAzimuth = 0.0F;
  private float deltaTangage = 0.0F;
  private boolean isGuidingBomb = false;
  private boolean isMasterAlive;

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

  public boolean typeGuidedBombCisMasterAlive() {
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

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();

    if (this.thisWeaponsName.startsWith("2HS293"))
    {
      hierMesh().chunkVisible("Hs293RackL", true);
      hierMesh().chunkVisible("Hs293RackR", true);
    }
  }

  static
  {
    Class localClass = FW_200C3U4.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "FW200");
    Property.set(localClass, "meshName", "3DO/Plane/FW-200C-3U4/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar03());

    Property.set(localClass, "yearService", 1941.1F);
    Property.set(localClass, "yearExpired", 1949.0F);

    Property.set(localClass, "FlightModel", "FlightModels/FW-200C-3U4.fmd");

    weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 13, 14, 15, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_BombSpawn01", "_BombSpawn02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb06", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_BombSpawn08", "_BombSpawn09", "_BombSpawn10", "_BombSpawn11", "_BombSpawn12", "_BombSpawn13", "_BombSpawn14" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "12sc50", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, null, null, null, null, null, null, null, null, "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50" });

    weaponsRegister(localClass, "12sc502sc250", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, "BombGunSC250", "BombGunSC250", null, null, null, null, null, null, "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50" });

    weaponsRegister(localClass, "12sc504sc250", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, "BombGunSC250", "BombGunSC250", "BombGunSC250", "BombGunSC250", null, null, null, null, "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50" });

    weaponsRegister(localClass, "12sc504sc500", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, "BombGunSC500", "BombGunSC500", "BombGunSC500", "BombGunSC500", null, null, null, null, "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50" });

    weaponsRegister(localClass, "12sc502sc1000", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, "BombGunSC1000", "BombGunSC1000", null, null, null, null, null, null, "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50" });

    weaponsRegister(localClass, "12sc504sc1000", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, "BombGunSC1000", "BombGunSC1000", "BombGunSC1000", "BombGunSC1000", null, null, null, null, "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50" });

    weaponsRegister(localClass, "2sc250", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSC250", "BombGunSC250", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "4sc250", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSC250", "BombGunSC250", null, null, "BombGunSC250", "BombGunSC250", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "6sc250", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSC250", "BombGunSC250", "BombGunSC250", "BombGunSC250", "BombGunSC250", "BombGunSC250", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2sc500", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSC500", "BombGunSC500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "4sc500", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSC500", "BombGunSC500", null, null, "BombGunSC500", "BombGunSC500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "6sc500", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSC500", "BombGunSC500", "BombGunSC500", "BombGunSC500", "BombGunSC500", "BombGunSC500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2sd500", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSD500", "BombGunSD500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "4sd500", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSD500", "BombGunSD500", null, null, "BombGunSD500", "BombGunSD500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "6sd500", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", "BombGunSD500", "BombGunSD500", "BombGunSD500", "BombGunSD500", "BombGunSD500", "BombGunSD500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2sc1000", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, null, null, "BombGunSC1000", "BombGunSC1000", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "4sc1000", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, "BombGunSC1000", "BombGunSC1000", "BombGunSC1000", "BombGunSC1000", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2sc1800", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, null, null, "BombGunSC1800", "BombGunSC1800", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2ab1000", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, null, null, "BombGunAB1000", "BombGunAB1000", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "4ab1000", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, "BombGunAB1000", "BombGunAB1000", "BombGunAB1000", "BombGunAB1000", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2pc1600", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, null, null, "BombGunPC1600", "BombGunPC1600", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2HS293", new String[] { "MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125", "MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null, null, null, null, null, "RocketGunHS_293 1", "BombGunNull 1", "RocketGunHS_293 1", "BombGunNull 1", null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}