package com.maddox.il2.engine;

public class InterpolateTick extends Interpolate
{
  public boolean tick()
  {
    MsgInterpolateTick.send(this.actor);
    return true;
  }
}