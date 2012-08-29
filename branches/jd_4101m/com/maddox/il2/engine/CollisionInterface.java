package com.maddox.il2.engine;

public abstract interface CollisionInterface
{
  public abstract boolean collision_isEnabled();

  public abstract double collision_getCylinderR();

  public abstract void collision_processing(Actor paramActor);
}