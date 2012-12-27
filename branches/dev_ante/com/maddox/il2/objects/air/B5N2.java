package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class B5N2 extends B5N
  implements TypeBomber
{
  public static boolean bChangedPit = false;

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      bChangedPit = true;
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      bChangedPit = true;
  }

  public void doKillPilot(int paramInt)
  {
    super.doKillPilot(paramInt);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      bChangedPit = true;
  }

  public void doMurderPilot(int paramInt)
  {
    super.doMurderPilot(paramInt);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      bChangedPit = true;
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    float f1 = -paramArrayOfFloat[0];
    float f2 = paramArrayOfFloat[1];
    switch (paramInt)
    {
    case 0:
      if (f1 < -31.0F)
      {
        f1 = -31.0F;
        bool = false;
      }
      if (f1 > 31.0F)
      {
        f1 = 31.0F;
        bool = false;
      }
      if (f2 < -10.0F)
      {
        f2 = -10.0F;
        bool = false;
      }
      if (f2 <= 52.0F)
        break;
      f2 = 52.0F;
      bool = false;
    }

    paramArrayOfFloat[0] = (-f1);
    paramArrayOfFloat[1] = f2;
    return bool;
  }

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
    Class localClass = B5N2.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "B5N");
    Property.set(localClass, "meshName", "3DO/Plane/B5N2(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar00());
    Property.set(localClass, "meshName_ja", "3DO/Plane/B5N2(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeFCSPar02());
    Property.set(localClass, "yearService", 1940.0F);
    Property.set(localClass, "yearExpired", 1946.5F);
    Property.set(localClass, "FlightModel", "FlightModels/B5N2.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitB5N2.class, Cockpit_BombsightOPB.class, CockpitB5N2_TGunner.class });

    Property.set(localClass, "LOSElevation", 0.7394F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 9, 3, 9, 3, 9, 3, 3, 3, 9, 3, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev02", "_ExternalBomb02", "_ExternalDev03", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalDev04", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunVikkersKt 350", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "6x30", new String[] { "MGunVikkersKt 350", null, null, null, null, null, null, null, null, "PylonB5NPLN3", "BombGun30kgJ 1", "BombGun30kgJ 1", "BombGun30kgJ 1", "BombGun30kgJ 1", "BombGun30kgJ 1", "BombGun30kgJ 1" });

    Aircraft.weaponsRegister(localClass, "6x50", new String[] { "MGunVikkersKt 350", null, null, null, null, null, null, null, null, "PylonB5NPLN3", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1" });

    Aircraft.weaponsRegister(localClass, "3x100", new String[] { "MGunVikkersKt 350", null, null, null, null, "PylonB5NPLN2", "BombGun100kgJ 1", "BombGun100kgJ 1", "BombGun100kgJ 1", null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1x250", new String[] { "MGunVikkersKt 350", null, null, "PylonB5NPLN1", "BombGun250kgJ 1", null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1x500", new String[] { "MGunVikkersKt 350", null, null, "PylonB5NPLN1", "BombGun500kgJ 1", null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1x600", new String[] { "MGunVikkersKt 350", null, null, "PylonB5NPLN1", "BombGun600kgJ 1", null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1x91", new String[] { "MGunVikkersKt 350", "PylonB5NPLN0", "BombGunTorpMk13 1", null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}