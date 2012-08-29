package com.maddox.il2.objects.air;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import java.io.IOException;

public abstract interface TypeBomber
{
  public abstract boolean typeBomberToggleAutomation();

  public abstract void typeBomberAdjDistanceReset();

  public abstract void typeBomberAdjDistancePlus();

  public abstract void typeBomberAdjDistanceMinus();

  public abstract void typeBomberAdjSideslipReset();

  public abstract void typeBomberAdjSideslipPlus();

  public abstract void typeBomberAdjSideslipMinus();

  public abstract void typeBomberAdjAltitudeReset();

  public abstract void typeBomberAdjAltitudePlus();

  public abstract void typeBomberAdjAltitudeMinus();

  public abstract void typeBomberAdjSpeedReset();

  public abstract void typeBomberAdjSpeedPlus();

  public abstract void typeBomberAdjSpeedMinus();

  public abstract void typeBomberUpdate(float paramFloat);

  public abstract void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException;

  public abstract void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException;
}