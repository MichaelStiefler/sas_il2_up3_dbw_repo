package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import java.util.AbstractCollection;
import java.util.List;

public abstract class CollideEnv
{
  public boolean isDoCollision()
  {
    return false;
  }

  protected void doCollision(List paramList)
  {
  }

  protected void doBulletMoveAndCollision()
  {
  }

  public void getSphere(AbstractCollection paramAbstractCollection, Point3d paramPoint3d, double paramDouble)
  {
  }

  public Actor getLine(Point3d paramPoint3d1, Point3d paramPoint3d2, boolean paramBoolean, Actor paramActor, Point3d paramPoint3d3)
  {
    return null;
  }
  public Actor getLine(Point3d paramPoint3d1, Point3d paramPoint3d2, boolean paramBoolean, ActorFilter paramActorFilter, Point3d paramPoint3d3) { return null;
  }

  public void getFiltered(AbstractCollection paramAbstractCollection, Point3d paramPoint3d, double paramDouble, ActorFilter paramActorFilter)
  {
  }

  public void getNearestEnemies(Point3d paramPoint3d, double paramDouble, int paramInt, Accumulator paramAccumulator)
  {
  }

  public void getNearestEnemiesCyl(Point3d paramPoint3d, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt, Accumulator paramAccumulator)
  {
  }

  protected void changedPos(Actor paramActor, Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
  }

  protected void add(Actor paramActor)
  {
  }

  protected void remove(Actor paramActor)
  {
  }

  protected void changedPosStatic(Actor paramActor, Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
  }

  protected void addStatic(Actor paramActor)
  {
  }

  protected void removeStatic(Actor paramActor)
  {
  }

  protected void clear()
  {
  }

  protected void resetGameClear()
  {
  }

  protected void resetGameCreate()
  {
  }
}