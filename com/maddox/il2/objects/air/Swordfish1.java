package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class Swordfish1 extends Swordfish
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
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Swordfish");

    Property.set(localClass, "meshName", "3DO/Plane/Swordfish1(multi)/hier.him");

    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar01());

    Property.set(localClass, "meshName_gb", "3DO/Plane/Swordfish1(gb)/hier.him");

    Property.set(localClass, "PaintScheme_gb", new PaintSchemeBMPar02f());

    Property.set(localClass, "meshName_rn", "3DO/Plane/Swordfish1(gb)/hier.him");

    Property.set(localClass, "PaintScheme_rn", new PaintSchemeBMPar02f());

    Property.set(localClass, "yearService", 1936.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/Swordfish.fmd");
    Property.set(localClass, "originCountry", PaintScheme.countryBritain);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 10, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_turret1", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11", "_ExternalBomb12", "_ExternalBomb13", "_ExternalBomb14", "_ExternalBomb15", "_ExternalBomb16", "_ExternalBomb17", "_ExternalBomb18", "_ExternalBomb19", "_ExternalBomb20", "_ExternalBomb21", "_ExternalBomb22", "_ExternalBomb23", "_ExternalBomb01", "_ExternalBomb24", "_ExternalBomb25", "_ExternalBomb26", "_ExternalBomb27", "_ExternalBomb28", "_ExternalBomb29", "_ExternalBomb30", "_ExternalBomb31", "_ExternalBomb32", "_ExternalBomb33" });

    weaponsRegister(localClass, "default", new String[] { "MGunVikkersKs 300", "MGunVikkersKt 600", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "1_1xTorpedo", new String[] { "MGunVikkersKs 300", "MGunVikkersKt 600", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunTorpMk13", null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2_3x500lb", new String[] { "MGunVikkersKs 300", "MGunVikkersKt 600", "BombGun500lbsE", "BombGun500lbsE", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGun500lbsE", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "3_1x500lb+4x250lb", new String[] { "MGunVikkersKs 300", "MGunVikkersKt 600", null, null, "BombGun250lbsE", "BombGun250lbsE", "BombGun250lbsE", "BombGun250lbsE", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGun500lbsE", null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "4_1x500lb+8x100lb", new String[] { "MGunVikkersKs 300", "MGunVikkersKt 600", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGun500lbsE", "BombGunNull", "BombGun50kg", "BombGun50kg", "BombGun50kg", "BombGun50kg", "BombGun50kg", "BombGun50kg", "BombGun50kg", "BombGun50kg" });

    weaponsRegister(localClass, "5_3x500lb+8xflare", new String[] { "MGunVikkersKs 300", "MGunVikkersKt 600", "BombGun500lbsE", "BombGun500lbsE", null, null, null, null, "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", null, "BombGun500lbsE", null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "6_1xtorpedo+8xflare", new String[] { "MGunVikkersKs 300", "MGunVikkersKt 600", null, null, null, null, null, null, "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", "BombGunTorpMk13", null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "7_1x500lb+4x250lb+8xflare", new String[] { "MGunVikkersKs 300", "MGunVikkersKt 600", null, null, "BombGun250lbsE", "BombGun250lbsE", "BombGun250lbsE", "BombGun250lbsE", "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunNull", "BombGunNull", "BombGunParaFlareUK", null, "BombGun500lbsE", null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "8_1x500lb+8xFlare_AI", new String[] { "MGunVikkersKs 300", "MGunVikkersKt 600", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGun500lbsE", "BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunParaFlareUK" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}