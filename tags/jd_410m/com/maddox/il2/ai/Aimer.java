package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;

public class Aimer
{
  private static float idleT;
  private static Point3d hunterPos = new Point3d();
  private static float DTresult;
  private static Point3d targPos = new Point3d();

  private static Point3d targShotOffs = new Point3d();

  private static Loc hunterLoc = new Loc();

  private static final boolean DT(float paramFloat, BulletAimer paramBulletAimer, Actor paramActor)
  {
    paramActor.futurePosition(paramFloat, targPos);
    targPos.add(targShotOffs);

    float f = paramBulletAimer.TravelTime(hunterPos, targPos);
    if (f < 0.0F)
      return false;
    DTresult = idleT + f - paramFloat;
    return true;
  }

  public static final boolean Aim(BulletAimer paramBulletAimer, Actor paramActor1, Actor paramActor2, float paramFloat, Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    if (paramFloat < 0.0F) {
      return false;
    }

    idleT = paramFloat;

    if (paramPoint3d1 == null)
      targShotOffs.set(0.0D, 0.0D, 0.0D);
    else {
      paramActor1.pos.getAbsOrient().transform(paramPoint3d1, targShotOffs);
    }

    paramActor2.pos.getAbs(hunterLoc);

    paramActor2.futurePosition(idleT, hunterLoc.getPoint());

    if (paramPoint3d2 == null)
      hunterPos.set(hunterLoc.getPoint());
    else {
      hunterLoc.transform(paramPoint3d2, hunterPos);
    }

    float f1 = idleT;
    if (!DT(f1, paramBulletAimer, paramActor1)) {
      return false;
    }
    float f3 = DTresult;

    if (f3 < 0.001F) return true;

    float f2 = f1 + f3;
    if (!DT(f2, paramBulletAimer, paramActor1))
    {
      f2 = f1 + f3 * 0.5F;
      if (!DT(f2, paramBulletAimer, paramActor1))
      {
        return false;
      }
    }
    float f4 = DTresult;

    if (f4 > 0.0F)
    {
      if ((DT((f1 + f2) / 2.0F, paramBulletAimer, paramActor1)) && (DTresult <= 0.0F)) {
        f2 = (f1 + f2) / 2.0F;
      }
      else
      {
        if (f2 - f1 < 1.0E-004F) {
          f2 = f1 + 0.3F;
        }
        else if (Math.abs(f4 - f3) < 1.0E-004F) { f2 = f1 + 2.0F * (f2 - f1);
        } else {
          f2 -= f4 * (f2 - f1) / (f4 - f3);
          if (f2 <= f1) f2 = f1 + 2.0F * (f2 - f1);
        }

        boolean bool = DT(f2, paramBulletAimer, paramActor1);
        if ((!bool) || (DTresult > 0.0F)) {
          if (!bool) {
            return false;
          }

          f2 += DTresult * 2.0F;
          bool = DT(f2, paramBulletAimer, paramActor1);
          if ((!bool) || (DTresult > 0.0F)) {
            return false;
          }
        }
      }
      f4 = DTresult;
    }

    float f5 = 0.0F;
    for (int i = 5; i > 0; i--) {
      if ((f4 > -0.001F) || (f2 - f1 < 0.001F))
        return true;
      if (f3 - f4 < 1.0E-004F) { f5 = (f1 + f2) / 2.0F;
      } else {
        f5 = f2 - f4 * (f2 - f1) / (f4 - f3);
        if ((f5 <= f1) || (f5 >= f2)) f5 = (f1 + f2) / 2.0F;
      }
      if (!DT(f5, paramBulletAimer, paramActor1)) {
        return false;
      }

      if (DTresult <= 0.0F) {
        f2 = f5; f4 = DTresult;
      }
      else {
        f1 = f5; f3 = DTresult;
      }
    }

    if (!DT((f1 + f2) / 2.0F, paramBulletAimer, paramActor1))
    {
      return DT(f5, paramBulletAimer, paramActor1);
    }

    return true;
  }

  public static final void GetPredictedTargetPosition(Point3d paramPoint3d)
  {
    paramPoint3d.set(targPos);
  }

  public static final Point3d GetHunterFirePoint()
  {
    return hunterPos;
  }
}