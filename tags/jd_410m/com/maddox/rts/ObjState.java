package com.maddox.rts;

import java.io.PrintStream;

public abstract class ObjState
  implements MessageComponent, Destroy
{
  public States states;

  public Object THIS()
  {
    return this;
  }
  public Object getSwitchListener(Message paramMessage) {
    return this.states;
  }

  public void destroy() {
    if (this.states != null) {
      this.states.setState(-2);
      this.states.destroy();
      this.states = null;
    }
  }

  public boolean isDestroyed() {
    return (this.states == null) || (this.states.getState() == -2);
  }

  public static void destroy(Destroy paramDestroy)
  {
    if ((paramDestroy != null) && 
      (!paramDestroy.isDestroyed()))
      paramDestroy.destroy(); 
  }

  public States states() {
    return this.states;
  }
  public int state() { return this.states.getState(); }

  protected void finalize() {
    if (!isDestroyed())
    {
      System.out.println("ObjState.finalize: Object of " + getClass().getName() + " NOT destroyed");
    }
  }
}