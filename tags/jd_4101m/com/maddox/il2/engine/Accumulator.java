package com.maddox.il2.engine;

public abstract interface Accumulator
{
  public abstract void clear();

  public abstract boolean add(Actor paramActor, double paramDouble);
}