package com.maddox.il2.engine;

public abstract class InterpolateRef extends Interpolate
{
  public void invokeRef()
  {
    if ((this.actor.pos != null) && (this.actor.pos.base() != null))
      InterpolateAdapter.forceInterpolate(this.actor.pos.base());
  }
}