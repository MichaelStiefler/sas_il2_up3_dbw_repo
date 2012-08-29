package com.maddox.il2.objects.air;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import java.io.IOException;

public abstract interface TypeFighterAceMaker
{
  public abstract boolean typeFighterAceMakerToggleAutomation();

  public abstract void typeFighterAceMakerAdjDistanceReset();

  public abstract void typeFighterAceMakerAdjDistancePlus();

  public abstract void typeFighterAceMakerAdjDistanceMinus();

  public abstract void typeFighterAceMakerAdjSideslipReset();

  public abstract void typeFighterAceMakerAdjSideslipPlus();

  public abstract void typeFighterAceMakerAdjSideslipMinus();

  public abstract void typeFighterAceMakerReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException;

  public abstract void typeFighterAceMakerReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException;
}