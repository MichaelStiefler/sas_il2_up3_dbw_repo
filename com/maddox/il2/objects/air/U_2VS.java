package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class U_2VS extends U_2
{
  public void doKillPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      break;
    case 1:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
    }
  }

  public void doMurderPilot(int paramInt) {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsAboutToBailout) break;
      hierMesh().chunkVisible("Gore1_D0", true); break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsAboutToBailout) break;
      hierMesh().chunkVisible("Gore2_D0", true);
    }
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
    Class localClass = U_2VS.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "U-2");
    Property.set(localClass, "meshName", "3do/plane/U-2VS/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());

    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1967.8F);

    Property.set(localClass, "FlightModel", "FlightModels/U-2VS.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 3, 3, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunUBt 250", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2ao10", new String[] { "MGunUBt 250", "BombGunAO10 1", "BombGunAO10 1", null, null });

    Aircraft.weaponsRegister(localClass, "4ao10", new String[] { "MGunUBt 250", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1" });

    Aircraft.weaponsRegister(localClass, "2fab50", new String[] { "MGunUBt 250", "BombGunFAB50", "BombGunFAB50", null, null });

    Aircraft.weaponsRegister(localClass, "4fab50", new String[] { "MGunUBt 250", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50" });

    Aircraft.weaponsRegister(localClass, "2fab100", new String[] { "MGunUBt 250", "BombGunFAB100", "BombGunFAB100", null, null });

    Aircraft.weaponsRegister(localClass, "2x4", new String[] { "MGunUBt 250", "BombGunFAB100", "BombGunFAB100", "BombGunFAB50", "BombGunFAB50" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null });
  }
}