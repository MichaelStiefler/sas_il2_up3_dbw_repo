package com.maddox.il2.net;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Squadron;

public class NetSquadron extends Squadron
{
  private int indexInRegiment = 0;

  public int indexInRegiment()
  {
    return this.indexInRegiment;
  }

  public NetSquadron(Regiment paramRegiment, int paramInt) {
    this.indexInRegiment = paramInt;
    setOwner(paramRegiment);
    setArmy(paramRegiment.getArmy());
  }
}