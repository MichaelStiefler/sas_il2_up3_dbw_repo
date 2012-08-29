package com.maddox.il2.ai;

import com.maddox.il2.ai.ground.ChiefGround;
import com.maddox.il2.engine.Actor;

class TEscort extends Target
{
  String nameTarget;
  Actor actor;
  int destructLevel;
  int alive = -1;

  public void destroy() { super.destroy(); this.actor = null; }

  boolean checkActor() {
    if ((this.actor != null) && (this.alive > 0))
      return true;
    this.actor = Actor.getByName(this.nameTarget);
    if ((this.actor != null) && (this.alive == -1)) {
      if (((this.actor instanceof ChiefGround)) && (((ChiefGround)this.actor).isPacked()) && (this.actor.isAlive()))
      {
        return false;
      }int i = this.actor.getOwnerAttachedCount();
      if (i > 0) {
        this.alive = (i - Math.round(i * this.destructLevel / 100.0F));
        if (this.alive == 0) this.alive = 1;
      }
    }
    return this.actor != null;
  }

  protected boolean checkPeriodic() {
    if (!checkActor())
      return false;
    if (!Actor.isValid(this.actor)) {
      setDiedFlag(true);
      return true;
    }
    int i = this.actor.getOwnerAttachedCount();
    if (((i == 0) && (!(this.actor instanceof Wing))) || (this.alive == -1))
    {
      return false;
    }int j = 0;
    int k = 0;
    for (int m = 0; m < i; m++) {
      Actor localActor = (Actor)this.actor.getOwnerAttached(m);
      if (Actor.isAlive(localActor)) {
        j++;
        if (localActor.isTaskComplete())
          k++;
      }
    }
    if (j < this.alive) {
      setDiedFlag(true);
      return true;
    }
    if (k >= this.alive) {
      setTaskCompleteFlag(true);
      setDiedFlag(true);
      return true;
    }
    return false;
  }

  protected boolean checkActorDied(Actor paramActor) {
    if (!checkActor())
      return false;
    if (this.actor == paramActor) {
      setDiedFlag(true);
      return true;
    }
    return checkPeriodic();
  }

  protected boolean checkTaskComplete(Actor paramActor)
  {
    if (!checkActor())
      return false;
    if (paramActor == this.actor) {
      setTaskCompleteFlag(true);
      setDiedFlag(true);
      return true;
    }
    return checkPeriodic();
  }

  protected boolean checkTimeoutOff()
  {
    setTaskCompleteFlag(true);
    setDiedFlag(true);
    return true;
  }

  public TEscort(int paramInt1, int paramInt2, String paramString, int paramInt3) {
    super(paramInt1, paramInt2);
    this.nameTarget = paramString;
    this.destructLevel = paramInt3;
  }
}