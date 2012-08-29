package com.maddox.il2.ai;

import com.maddox.il2.engine.Actor;
import com.maddox.rts.Message;

public class Squadron extends Actor
{
  public Wing[] wing = new Wing[4];

  public int indexInRegiment()
  {
    int i = name().charAt(name().length() - 1);
    return i - 48;
  }

  public Regiment regiment() {
    return (Regiment)getOwner();
  }

  public int getWingsNumber()
  {
    int j = 0;
    for (int i = 0; i < this.wing.length; i++) { if (this.wing[i] == null) continue; j++; }
    return j;
  }

  public static Squadron New(String paramString) {
    Squadron localSquadron = (Squadron)Actor.getByName(paramString);
    if (localSquadron != null)
      return localSquadron;
    return new Squadron(paramString);
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }
  public Squadron(String paramString) {
    setName(paramString);
    int i = indexInRegiment();
    if ((i < 0) || (i > 3))
      throw new RuntimeException("Squadron '" + paramString + "' NOT valid");
    String str = paramString.substring(0, paramString.length() - 1);
    Regiment localRegiment = (Regiment)Actor.getByName(str);
    if (localRegiment == null)
      throw new RuntimeException("Regiment '" + str + "' NOT found");
    setOwner(localRegiment);
    setArmy(localRegiment.getArmy());
  }

  protected Squadron()
  {
  }
}