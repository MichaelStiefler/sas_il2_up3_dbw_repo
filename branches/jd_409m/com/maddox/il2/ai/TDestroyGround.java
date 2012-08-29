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
  private static Point3d p = new Point3d();

  protected boolean checkActorDied(Actor paramActor)
  {
    if (paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos == null) return false;
    paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(p);
    p.jdField_z_of_type_Double = this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().jdField_z_of_type_Double;
    if (this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().distance(p) <= this.r) {
      if (!Target.isStaticActor(paramActor)) return false;

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
    World.land(); this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosStatic(this, new Point3d(paramInt3, paramInt4, Landscape.HQ(paramInt3, paramInt4)), new Orient());
    this.countActors = Target.countStaticActors(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint(), this.r);
    if (this.countActors == 0) {
      setTaskCompleteFlag(true);
      setDiedFlag(true);
    } else {
      this.countActors = Math.round(this.countActors * this.destructLevel / 100.0F);
      if (this.countActors == 0)
        this.countActors = 1;
    }
  }
}