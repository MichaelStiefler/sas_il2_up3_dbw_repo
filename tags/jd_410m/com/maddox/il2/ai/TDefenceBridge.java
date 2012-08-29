package com.maddox.il2.ai;

import com.maddox.il2.engine.Actor;

class TDefenceBridge extends Target
{
  Actor actor;

  public void destroy()
  {
    super.destroy(); this.actor = null;
  }
  protected boolean checkActorDied(Actor paramActor) {
    if (this.actor == paramActor) {
      setDiedFlag(true);
      return true;
    }
    return false;
  }

  protected boolean checkTimeoutOff() {
    setTaskCompleteFlag(true);
    setDiedFlag(true);
    return true;
  }

  public TDefenceBridge(int paramInt1, int paramInt2, String paramString) {
    super(paramInt1, paramInt2);
    this.actor = Actor.getByName(paramString);
  }
}