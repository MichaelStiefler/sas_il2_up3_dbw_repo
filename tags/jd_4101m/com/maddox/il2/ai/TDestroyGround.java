package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosStatic;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orient;

class TDestroyGround extends Target
{
  double r;
  int countActors = 0;
  int destructLevel;
  public static Point3d p = new Point3d();

  protected boolean checkActorDied(Actor paramActor)
  {
    if (paramActor.pos == null) return false;
    paramActor.pos.getAbs(p);
    p.z = this.pos.getAbsPoint().z;
    if (this.pos.getAbsPoint().distance(p) <= this.r) {
      if (!isStaticActor(paramActor)) return false;

      this.countActors -= 1;
      if (this.countActors <= 0) {
        setTaskCompleteFlag(true);
        setDiedFlag(true);
        return true;
      }
    }
    return false;
  }

  protected boolean checkTimeoutOff()
  {
    setDiedFlag(true);
    return true;
  }

  public TDestroyGround(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    super(paramInt1, paramInt2);
    this.r = paramInt5;
    this.destructLevel = paramInt6;
    World.land(); this.pos = new ActorPosStatic(this, new Point3d(paramInt3, paramInt4, Landscape.HQ(paramInt3, paramInt4)), new Orient());
    this.countActors = countStaticActors(this.pos.getAbsPoint(), this.r);
    if (this.countActors == 0) {
      setTaskCompleteFlag(true);
      setDiedFlag(true);
    } else {
      this.countActors = Math.round(this.countActors * this.destructLevel / 100.0F);
      if (this.countActors == 0)
        this.countActors = 1;
    }
  }

  public boolean zutiIsOverTarged(double paramDouble1, double paramDouble2) {
    double d1 = this.r * this.r;
    double d2 = (paramDouble1 - p.x) * (paramDouble1 - p.x) + (paramDouble2 - p.y) * (paramDouble2 - p.y);

    return d2 < d1;
  }
}