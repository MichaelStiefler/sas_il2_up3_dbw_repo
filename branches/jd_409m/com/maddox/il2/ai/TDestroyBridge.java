package com.maddox.il2.ai;

import com.maddox.il2.engine.Actor;

class TDestroyBridge extends Target
{
  Actor actor;

  public void destroy()
  {
    super.destroy(); this.actor = null;
  }
  protected boolean checkActorDied(Actor paramActor) {
    if (this.actor == paramActor) {
      setTaskCompleteFlag(true);
      setDiedFlag(true);
      return true;
    }
    return false;
  }

  protected boolean checkTimeoutOff() {
    setDiedFlag(true);
    return true;
  }

  public TDestroyBridge(int paramInt1, int paramInt2, String paramString) {
    super(paramInt1, paramInt2);
    this.actor = Actor.getByName(paramString);
  }
}