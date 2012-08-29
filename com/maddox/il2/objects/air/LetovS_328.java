package com.maddox.il2.objects.air;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class LetovS_328 extends Letov
{
  public boolean typeBomberToggleAutomation()
  {
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
    Class localClass = LetovS_328.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "S-328");
    Property.set(localClass, "meshName", "3do/Plane/LetovS-328/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar00s());
    Property.set(localClass, "meshName_de", "3do/Plane/LetovS-328_DE/hier.him");
    Property.set(localClass, "PaintScheme_de", new PaintSchemeBMPar00s());
    Property.set(localClass, "meshName_sk", "3do/Plane/LetovS-328_SK/hier.him");
    Property.set(localClass, "PaintScheme_sk", new PaintSchemeBMPar00s());
    Property.set(localClass, "yearService", 1935.0F);
    Property.set(localClass, "yearExpired", 1950.0F);
    Property.set(localClass, "FlightModel", "FlightModels/LetovS-328.fmd");
    Property.set(localClass, "originCountry", PaintScheme.countrySlovakia);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 10, 10, 9, 9, 9, 9, 9, 9, 9, 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalBomb18", "_ExternalBomb19", "_ExternalBomb20", "_ExternalBomb21", "_ExternalBomb22", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11", "_ExternalBomb12", "_ExternalBomb13", "_ExternalBomb14", "_ExternalBomb15", "_ExternalBomb16", "_ExternalBomb17" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "8*10kg", new String[] { "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", null, null, null, null, null, "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4*20kg", new String[] { "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", null, null, "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", null, null, null, null, null, null, null, "BombGun20kgCZ", "BombGun20kgCZ", null, null, "BombGun20kgCZ", "BombGun20kgCZ", null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "6*20kg", new String[] { "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", null, null, null, null, null, "BombGun20kgCZ", "BombGun20kgCZ", "BombGun20kgCZ", "BombGun20kgCZ", null, null, "BombGun20kgCZ", "BombGun20kgCZ", null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2*20kg+6*10kg", new String[] { "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", null, null, null, null, null, "BombGun20kgCZ", "BombGun20kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2*50kgCZ", new String[] { "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGun50kgCZ", "BombGun50kgCZ", null });

    Aircraft.weaponsRegister(localClass, "1*100kgCZ", new String[] { "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGun100kgCZ" });

    Aircraft.weaponsRegister(localClass, "2*50kg", new String[] { "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", null, null, null, null, null, null, null, null, "BombGun50kg", "BombGun50kg", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1*100kg", new String[] { "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", null, null, null, null, null, null, null, null, null, null, "BombGun100kg", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1*ParaFlare", new String[] { "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", null, null, null, null, null, null, "BombGunParaFlare", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2*ParaFlare", new String[] { "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", null, null, null, null, null, null, "BombGunParaFlare", "BombGunParaFlare", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1*ParaFlare+2*20kg", new String[] { "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "BombGunParaFlare", null, null, null, null, null, null, "BombGun20kgCZ", "BombGun20kgCZ", null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2*ParaFlare+6*10kg", new String[] { "MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420", "MGunVz30t 420", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "BombGunParaFlare", "BombGunParaFlare", null, null, null, null, null, "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}