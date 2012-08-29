package com.maddox.il2.engine;

public class InterpolateTickRef extends InterpolateRef
{
  public boolean tick()
  {
    MsgInterpolateTick.send(this.actor);
    return true;
  }
}