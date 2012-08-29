package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class TU_2S extends TU_2
  implements TypeBomber
{
  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay01_D0", 0.0F, -85.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay02_D0", 0.0F, 85.0F * paramFloat, 0.0F);
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
    Class localClass = TU_2S.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Tu-2");
    Property.set(localClass, "meshName", "3DO/Plane/Tu-2S/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar05());

    Property.set(localClass, "yearService", 1942.5F);
    Property.set(localClass, "yearExpired", 1956.6F);

    Property.set(localClass, "FlightModel", "FlightModels/Tu-2S.fmd");

    weaponTriggersRegister(localClass, new int[] { 1, 1, 10, 11, 12, 3, 3, 3, 3, 3, 3, 3, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06" });

    weaponsRegister(localClass, "default", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "6fab50", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50" });

    weaponsRegister(localClass, "8fab50", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB50", "BombGunFAB50", null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50" });

    weaponsRegister(localClass, "6fab100", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100" });

    weaponsRegister(localClass, "8fab100", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB100", "BombGunFAB100", null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100" });

    weaponsRegister(localClass, "1fab250", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, "BombGunFAB250", null, null, null, null, null, null });

    weaponsRegister(localClass, "2fab250", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250", null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2fab2506fab100", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250", null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100" });

    weaponsRegister(localClass, "3fab250", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", null, null, null, null, null, null });

    weaponsRegister(localClass, "4fab250", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, null, null, null, "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250" });

    weaponsRegister(localClass, "6fab250", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250", null, null, null, "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250" });

    weaponsRegister(localClass, "1fab500", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, "BombGunFAB500", null, null, null, null, null, null });

    weaponsRegister(localClass, "2fab500", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2fab5006fab50", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50" });

    weaponsRegister(localClass, "2fab5006fab100", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100" });

    weaponsRegister(localClass, "2fab5004fab250", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, null, null, "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250" });

    weaponsRegister(localClass, "3fab500", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", null, null, null, null, null, null });

    weaponsRegister(localClass, "4fab500", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, "BombGunFAB500", "BombGunFAB500", null, null, null, null });

    weaponsRegister(localClass, "6fab500", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, null, null, "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500" });

    weaponsRegister(localClass, "1fab1000", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, "BombGunFAB1000", null, null, null, null, null, null });

    weaponsRegister(localClass, "1fab10002fab250", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250", "BombGunFAB1000", null, null, null, null, null, null });

    weaponsRegister(localClass, "2fab1000", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2fab10006fab50", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50" });

    weaponsRegister(localClass, "2fab10006fab100", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100" });

    weaponsRegister(localClass, "2fab10002fab250", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", null, "BombGunFAB250", "BombGunFAB250", null, null, null, null });

    weaponsRegister(localClass, "2fab10001fab500", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB500", null, null, null, null, null, null });

    weaponsRegister(localClass, "3fab1000", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB1000", null, null, null, null, null, null });

    weaponsRegister(localClass, "1fab2000", new String[] { "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, "BombGunFAB2000", null, null, null, null, null, null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}