package com.maddox.il2.engine;

import com.maddox.rts.Message;
import com.maddox.rts.Time;

public abstract class Interpolate
{
  public Actor actor = null;

  public Object id = null;

  public Message msgEnd = null;
  public long timeBegin;
  public boolean bExecuted = false;

  public void begin()
  {
  }

  public boolean tick()
  {
    return false;
  }

  public void sleep()
  {
  }

  public void wakeup(long paramLong)
  {
  }

  public void cancel()
  {
  }

  public void end()
  {
    if ((this.msgEnd != null) && (Actor.isValid(this.actor)))
      if (this.actor.isRealTime()) this.msgEnd.post(64, this.actor, Time.currentReal()); else
        this.msgEnd.post(0, this.actor, Time.current());
  }

  public void doDestroy()
  {
  }

  public final void canceling()
  {
    if ((this.actor != null) && (this.actor.interp != null))
      this.actor.interp.cancel(this);
  }

  public final void ending()
  {
    if ((this.actor != null) && (this.actor.interp != null))
      this.actor.interp.end(this);
  }
}