package com.maddox.il2.ai.ground;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;

public class StaticObstacle
{
  private static final int MAX_COLLISIONS = 3;
  private Actor obj;
  Point3d pos;
  float R;
  int segIdx;
  double along;
  double side;
  private int nCollisions;

  public StaticObstacle()
  {
    clear();
  }

  public void clear()
  {
    this.obj = null;
    this.nCollisions = 0;
  }

  public boolean isActive()
  {
    return this.obj != null;
  }

  public boolean updateState()
  {
    if (!Actor.isValid(this.obj)) {
      clear();
    }

    return isActive();
  }

  public void collision(Actor paramActor, ChiefGround paramChiefGround, UnitData paramUnitData)
  {
    if (!Actor.isValid(paramActor))
    {
      return;
    }

    if (!(paramActor instanceof Obstacle))
    {
      return;
    }

    if (!((Obstacle)paramActor).unmovableInFuture())
    {
      return;
    }

    if (updateState()) {
      if (this.obj == paramActor) {
        this.nCollisions += 1;

        if (this.nCollisions > 3) {
          ((Obstacle)paramActor).collisionDeath();
          clear();
        }

        return;
      }

      int i = paramUnitData.segmentIdx;
      double d = paramChiefGround.computePosAlong(i, paramActor.pos.getAbsPoint());

      if ((i < this.segIdx) || ((i == this.segIdx) && (d <= this.along)))
      {
        this.nCollisions += 1;

        if (this.nCollisions > 3) {
          ((Obstacle)paramActor).collisionDeath();
          clear();
        }

        return;
      }

    }

    this.nCollisions = 1;
    this.obj = paramActor;
    this.pos = new Point3d(paramActor.pos.getAbsPoint());
    this.segIdx = paramUnitData.segmentIdx;
    this.along = paramChiefGround.computePosAlong(this.segIdx, this.pos);
    this.side = paramChiefGround.computePosSide(this.segIdx, this.pos);
    this.R = paramActor.collisionR();
    if (this.R <= 0.0F) this.R = 0.0F;
  }
}