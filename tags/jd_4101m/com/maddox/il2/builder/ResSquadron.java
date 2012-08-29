package com.maddox.il2.builder;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.Actor;
import com.maddox.rts.Message;
import java.util.ArrayList;

public class ResSquadron extends Actor
{
  private ArrayList attached = new ArrayList();

  public int index()
  {
    int i = Character.getNumericValue(name().charAt(name().length() - 1)) - Character.getNumericValue('0');

    if (i < 0) i = 0;
    if (i > 3) i = 3;
    return i;
  }

  public Regiment regiment() {
    return (Regiment)getOwner();
  }

  public static ResSquadron New(String paramString) {
    ResSquadron localResSquadron = (ResSquadron)Actor.getByName(paramString);
    if (localResSquadron != null)
      return localResSquadron;
    return new ResSquadron(paramString);
  }

  public Object[] getAttached(Object[] paramArrayOfObject)
  {
    return this.attached.toArray(paramArrayOfObject);
  }
  public int getAttachedCount() {
    return this.attached.size();
  }
  public void attach(PathAir paramPathAir) {
    this.attached.add(paramPathAir);
  }
  public void detach(PathAir paramPathAir) {
    int i = this.attached.indexOf(paramPathAir);
    if (i >= 0)
      this.attached.remove(i);
    if (this.attached.size() == 0)
      destroy();
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }
  public ResSquadron(String paramString) {
    setName(paramString);
    Regiment localRegiment = (Regiment)Actor.getByName(paramString.substring(0, paramString.length() - 1));
    setOwner(localRegiment);
    setArmy(localRegiment.getArmy());
  }
  protected void createActorHashCode() {
    makeActorRealHashCode();
  }
}