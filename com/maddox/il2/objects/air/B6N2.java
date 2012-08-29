package com.maddox.il2.objects.air;

import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class B6N2 extends B6N
  implements TypeBomber
{
  public boolean typeBomberToggleAutomation()
  {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
    {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.setTrimAileronControl(0.07F);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.setTrimElevatorControl(-0.23F);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.setTrimRudderControl(0.18F);
    }
    return false;
  }

  public void typeBomberAdjDistanceReset()
  {
    BombsightOPB.AdjDistanceReset();
  }

  public void typeBomberAdjDistancePlus()
  {
    BombsightOPB.AdjDistancePlus();
  }

  public void typeBomberAdjDistanceMinus()
  {
    BombsightOPB.AdjDistanceMinus();
  }

  public void typeBomberAdjSideslipReset()
  {
    BombsightOPB.AdjSideslipReset();
  }

  public void typeBomberAdjSideslipPlus()
  {
    BombsightOPB.AdjSideslipPlus();
  }

  public void typeBomberAdjSideslipMinus()
  {
    BombsightOPB.AdjSideslipMinus();
  }

  public void typeBomberAdjAltitudeReset()
  {
    BombsightOPB.AdjAltitudeReset();
  }

  public void typeBomberAdjAltitudePlus()
  {
    BombsightOPB.AdjAltitudePlus();
  }

  public void typeBomberAdjAltitudeMinus()
  {
    BombsightOPB.AdjAltitudeMinus();
  }

  public void typeBomberAdjSpeedReset()
  {
    BombsightOPB.AdjSpeedReset();
  }

  public void typeBomberAdjSpeedPlus()
  {
    BombsightOPB.AdjSpeedPlus();
  }

  public void typeBomberAdjSpeedMinus()
  {
    BombsightOPB.AdjSpeedMinus();
  }

  public void typeBomberUpdate(float paramFloat)
  {
    BombsightOPB.Update(paramFloat);
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException
  {
  }

  static
  {
    Class localClass = B6N2.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "B6N");
    Property.set(localClass, "meshName", "3DO/Plane/B6N2(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar00());
    Property.set(localClass, "meshName_ja", "3DO/Plane/B6N2(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeFCSPar02());
    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1946.5F);
    Property.set(localClass, "FlightModel", "FlightModels/B6N2.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitB6N2.class, Cockpit_BombsightOPB.class, CockpitB6N2_TGunner.class });

    Property.set(localClass, "LOSElevation", 0.7394F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 11, 9, 3, 9, 3, 9, 3, 3, 9, 9, 3, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev02", "_ExternalBomb02", "_ExternalDev06", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev04", "_ExternalDev05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunVikkersKt 500", "MGunVikkersKt 500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "6x30", new String[] { "MGunVikkersKt 500", "MGunVikkersKt 500", null, null, null, null, null, null, null, "PylonB6NPLN1", "PylonB6NPLN1", "BombGun30kgJ 1", "BombGun30kgJ 1", "BombGun30kgJ 1", "BombGun30kgJ 1", "BombGun30kgJ 1", "BombGun30kgJ 1" });

    Aircraft.weaponsRegister(localClass, "6x50", new String[] { "MGunVikkersKt 500", "MGunVikkersKt 500", null, null, null, null, null, null, null, "PylonB6NPLN1", "PylonB6NPLN1", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1" });

    Aircraft.weaponsRegister(localClass, "1x250", new String[] { "MGunVikkersKt 500", "MGunVikkersKt 500", null, null, "PylonB5NPLN1", "BombGun250kgJ 1", null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x250", new String[] { "MGunVikkersKt 500", "MGunVikkersKt 500", null, null, null, null, "PylonB6NPLN1", "BombGun250kgJ 1", "BombGun250kgJ 1", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1x500", new String[] { "MGunVikkersKt 500", "MGunVikkersKt 500", null, null, "PylonB5NPLN1", "BombGun500kgJ 1", null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1x600", new String[] { "MGunVikkersKt 500", "MGunVikkersKt 500", null, null, "PylonB5NPLN1", "BombGun600kgJ 1", null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1x91", new String[] { "MGunVikkersKt 500", "MGunVikkersKt 500", "PylonB5NPLN0", "BombGunTorpMk13 1", null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}