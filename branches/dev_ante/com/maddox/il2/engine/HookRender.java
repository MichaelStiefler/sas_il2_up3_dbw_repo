package com.maddox.il2.engine;

public abstract class HookRender extends Hook
{
  public boolean computeRenderPos(Actor paramActor, Loc paramLoc1, Loc paramLoc2)
  {
    throw new ActorException("computeRenderPos for hook not implemented");
  }
}