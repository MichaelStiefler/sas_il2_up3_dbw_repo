package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;

public abstract class DreamEnv
{
  public static final double SQUARE_SIZE = 200.0D;
  public static final double FIRE_SIZE = 7600.0D;
  public static final double UPDATE_TIME = 1.0D;

  public boolean isSleep(double paramDouble1, double paramDouble2)
  {
    return true; } 
  public boolean isSleep(Point3d paramPoint3d) { return true; } 
  public boolean isSleep(int paramInt1, int paramInt2) { return true;
  }

  protected void doChanges()
  {
  }

  protected void changedListenerPos(Actor paramActor, Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
  }

  protected void addListener(Actor paramActor)
  {
  }

  protected void removeListener(Actor paramActor)
  {
  }

  protected void addGlobalListener(Actor paramActor)
  {
  }

  protected void removeGlobalListener(Actor paramActor)
  {
  }

  public void resetGlobalListener(Actor paramActor)
  {
  }

  protected void changedFirePos(Actor paramActor, Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
  }

  protected void addFire(Actor paramActor)
  {
  }

  protected void removeFire(Actor paramActor)
  {
  }

  public void resetGameClear()
  {
  }

  public void resetGameCreate()
  {
  }

  protected void clearFire()
  {
  }
}