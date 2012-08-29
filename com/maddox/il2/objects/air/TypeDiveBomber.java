package com.maddox.il2.objects.air;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import java.io.IOException;

public abstract interface TypeDiveBomber
{
  public abstract boolean typeDiveBomberToggleAutomation();

  public abstract void typeDiveBomberAdjAltitudeReset();

  public abstract void typeDiveBomberAdjAltitudePlus();

  public abstract void typeDiveBomberAdjAltitudeMinus();

  public abstract void typeDiveBomberAdjVelocityReset();

  public abstract void typeDiveBomberAdjVelocityPlus();

  public abstract void typeDiveBomberAdjVelocityMinus();

  public abstract void typeDiveBomberAdjDiveAngleReset();

  public abstract void typeDiveBomberAdjDiveAnglePlus();

  public abstract void typeDiveBomberAdjDiveAngleMinus();

  public abstract void typeDiveBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException;

  public abstract void typeDiveBomberReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException;
}