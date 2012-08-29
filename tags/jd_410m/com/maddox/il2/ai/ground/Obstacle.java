package com.maddox.il2.ai.ground;

public abstract interface Obstacle
{
  public abstract boolean unmovableInFuture();

  public abstract void collisionDeath();
}