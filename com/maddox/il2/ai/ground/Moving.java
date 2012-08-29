package com.maddox.il2.ai.ground;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Time;

public class Moving
{
  public Point3d srcPos;
  public Point3d dstPos;
  public AnglesFork angles;
  public long moveTotTime;
  public long moveCurTime;
  public long rotatTotTime;
  public long rotatCurTime;
  public Vector3f normal;
  public boolean rotatingInPlace;
  public boolean movingForward;

  private static final long SecsToTicks(float paramFloat)
  {
    long l = ()(0.5D + paramFloat / Time.tickLenFs());
    return l < 1L ? 1L : l;
  }
  public boolean IsLandAligned() { return this.normal.z < 0.0F; } 
  public static final float distance2D(Point3d paramPoint3d1, Point3d paramPoint3d2) {
    return (float)Math.sqrt((paramPoint3d1.jdField_x_of_type_Double - paramPoint3d2.jdField_x_of_type_Double) * (paramPoint3d1.jdField_x_of_type_Double - paramPoint3d2.jdField_x_of_type_Double) + (paramPoint3d1.jdField_y_of_type_Double - paramPoint3d2.jdField_y_of_type_Double) * (paramPoint3d1.jdField_y_of_type_Double - paramPoint3d2.jdField_y_of_type_Double));
  }

  public Moving()
  {
    this.moveCurTime = (this.rotatCurTime = -1L);
    this.srcPos = new Point3d();
    this.dstPos = new Point3d();
    this.normal = null;
    this.angles = new AnglesFork();
    this.rotatingInPlace = false;
    this.movingForward = false;
  }

  public void switchToStay(float paramFloat)
  {
    this.dstPos = null;
    this.moveTotTime = (this.moveCurTime = SecsToTicks(paramFloat));
    this.rotatCurTime = -1L;
    this.rotatingInPlace = false;
    this.movingForward = false;
  }

  public void switchToAsk()
  {
    switchToStay(0.0F);
    this.moveCurTime = (this.rotatCurTime = -1L);
  }

  public void set(UnitMove paramUnitMove, Actor paramActor, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.dstPos = new Point3d();
    this.normal = new Vector3f();
    this.normal.set(paramUnitMove.normal);
    this.srcPos.set(paramActor.pos.getAbsPoint());
    this.moveTotTime = (this.moveCurTime = SecsToTicks(paramUnitMove.totalTime));
    if (paramUnitMove.pos == null) {
      switchToStay(paramUnitMove.totalTime);
      return;
    }
    this.dstPos.set(paramUnitMove.pos);

    this.movingForward = true;

    this.angles.setDeg(paramActor.pos.getAbsOrient().getYaw());
    double d1 = this.dstPos.jdField_x_of_type_Double - this.srcPos.jdField_x_of_type_Double;
    double d2 = this.dstPos.jdField_y_of_type_Double - this.srcPos.jdField_y_of_type_Double;
    if (Math.abs(d1) + Math.abs(d2) > 1.E-005D) {
      this.angles.setDstRad((float)Math.atan2(d2, d1));
    }
    int i = 0;
    if ((paramFloat2 > 0.0F) && (this.angles.getAbsDiffDeg() > 90.0F)) {
      i = 1;
      this.angles.reverseDst();
      this.movingForward = false;
    }

    this.rotatingInPlace = false;
    float f1 = 0.0F; float f2 = 0.0F; float f3 = paramUnitMove.totalTime;

    if (this.angles.getAbsDiffDeg() > 0.02F) {
      if (this.angles.getAbsDiffDeg() <= paramFloat4) {
        f1 = this.angles.getAbsDiffDeg() / (paramFloat3 * 0.2F);
        if (f1 > f3) {
          f4 = f1 * 0.2F;
          if (paramUnitMove.dontrun) {
            f4 *= 0.6F;
          }
          if (f4 > f3)
            f1 = f3 = f4;
          else
            f1 = f3;
        }
      }
      else {
        this.rotatingInPlace = true;
        f1 = this.angles.getAbsDiffDeg() / paramFloat3;
        if (f1 > f3) {
          f3 = f1;
        }
        f3 -= f1;
      }

    }

    f2 = f3;
    float f4 = distance2D(this.srcPos, this.dstPos);
    float f5 = paramUnitMove.dontrun ? paramUnitMove.walkSpeed : i != 0 ? paramFloat2 : paramFloat1;
    if (f4 / f5 > f2) {
      f2 = f4 / f5;
    }

    this.rotatTotTime = SecsToTicks(f1);
    this.moveTotTime = SecsToTicks(f2);

    this.moveCurTime = (f2 > 0.0F ? this.moveTotTime : -1L);
    this.rotatCurTime = (f1 > 0.0F ? this.rotatTotTime : -1L);

    if (((this.rotatCurTime < 0L) && (this.moveCurTime <= 1L)) || (distance2D(this.srcPos, this.dstPos) < 0.05F))
    {
      switchToStay(((UnitInterface)paramActor).StayInterval());
    }
  }

  public void switchToRotate(Actor paramActor, float paramFloat1, float paramFloat2)
  {
    if (this.normal == null) {
      this.movingForward = false;
      this.moveTotTime = (this.moveCurTime = -1L);
      this.rotatTotTime = (this.rotatCurTime = -1L);
      return;
    }

    this.dstPos = new Point3d();
    this.srcPos.set(paramActor.pos.getAbsPoint());
    this.dstPos.set(this.srcPos);

    this.rotatingInPlace = true;
    this.angles.setDeg(paramActor.pos.getAbsOrient().getYaw());
    this.angles.setDstDeg(this.angles.getSrcDeg() + paramFloat1);
    float f = this.angles.getAbsDiffDeg() / paramFloat2;
    this.rotatTotTime = SecsToTicks(f);
    this.rotatCurTime = (this.rotatTotTime > 0L ? this.rotatTotTime : -1L);

    this.movingForward = true;
    this.moveTotTime = -1L;
    this.moveCurTime = -1L;
  }
}