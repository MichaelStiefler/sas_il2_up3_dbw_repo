package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class Fulmar1 extends Fulmar
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
    Property.set(localClass, "iconFar_shortClassName", "Fulmar");

    Property.set(localClass, "meshName", "3DO/Plane/Fulmar1(multi)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar02());

    Property.set(localClass, "meshName_gb", "3DO/Plane/Fulmar1(gb)/hier.him");
    Property.set(localClass, "PaintScheme_gb", new PaintSchemeBMPar02f());

    Property.set(localClass, "meshName_rn", "3DO/Plane/Fulmar1(gb)/hier.him");
    Property.set(localClass, "PaintScheme_rn", new PaintSchemeFMPar02f());

    Property.set(localClass, "yearService", 1936.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/FulmarMkI.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 9 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning303k 750", "MGunBrowning303k 750", "MGunBrowning303k 750", "MGunBrowning303k 750", "MGunBrowning303k 750", "MGunBrowning303k 750", "MGunBrowning303k 750", "MGunBrowning303k 750", null, null, null });

    weaponsRegister(localClass, "2x250lb", new String[] { "MGunBrowning303k 750", "MGunBrowning303k 750", "MGunBrowning303k 750", "MGunBrowning303k 750", "MGunBrowning303k 750", "MGunBrowning303k 750", "MGunBrowning303k 750", "MGunBrowning303k 750", "BombGun250lbsE 1", "BombGun250lbsE 1", null });

    weaponsRegister(localClass, "1xDropTank", new String[] { "MGunBrowning303k 750", "MGunBrowning303k 750", "MGunBrowning303k 750", "MGunBrowning303k 750", "MGunBrowning303k 750", "MGunBrowning303k 750", "MGunBrowning303k 750", "MGunBrowning303k 750", null, null, "FuelTankGun_Tank60gal 1" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null });
  }
}