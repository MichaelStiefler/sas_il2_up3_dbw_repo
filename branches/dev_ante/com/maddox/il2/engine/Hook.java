package com.maddox.il2.engine;

public abstract class Hook
{
  public void computePos(Actor paramActor, Loc paramLoc1, Loc paramLoc2)
  {
    throw new ActorException("computePos for hook not implemented");
  }

  public String chunkName() {
    return "Body";
  }
  public int chunkNum() {
    return -1;
  }

  public void baseChanged(Actor paramActor)
  {
  }
}