package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorException;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Mat;
import com.maddox.rts.Property;

public class PNodes extends PPoint
{
  public double timeoutMin = 0.0D;
  public double speed;
  public float[] posXYZ;
  private static Point3d _prevP;
  private static Point3d _curP;
  private static Point3d _movePrevP;

  public double len()
  {
    if (this.posXYZ == null) return 0.0D;
    if (this.posXYZ.length / 4 <= 1) return 0.0D;
    double d1 = this.posXYZ[0];
    double d2 = this.posXYZ[1];
    double d3 = this.posXYZ[2];
    _prevP.set(d1, d2, d3);
    double d4 = 0.0D;
    for (int i = 1; i < this.posXYZ.length / 4; i++) {
      d1 = this.posXYZ[(i * 4 + 0)];
      d2 = this.posXYZ[(i * 4 + 1)];
      d3 = this.posXYZ[(i * 4 + 2)];
      _curP.set(d1, d2, d3);
      d4 += _prevP.distance(_curP);
      _prevP.set(_curP);
    }
    return d4;
  }

  public double computeTime(PNodes paramPNodes)
  {
    double d1 = len();
    if (d1 == 0.0D) return 0.0D;
    double d2 = 0.0D;

    int i = this.posXYZ.length / 4;
    double d3 = this.posXYZ[0];
    double d4 = this.posXYZ[1];
    double d5 = this.posXYZ[2];
    _prevP.set(d3, d4, d5);
    double d6 = 0.0D;
    for (int j = 1; j < i; j++) {
      d3 = this.posXYZ[(j * 4 + 0)];
      d4 = this.posXYZ[(j * 4 + 1)];
      d5 = this.posXYZ[(j * 4 + 2)];
      _curP.set(d3, d4, d5);
      double d7 = _prevP.distance(_curP);
      double d8 = this.speed * (d6 / d1) + paramPNodes.speed * ((d1 - d6) / d1);
      d6 += d7;
      double d9 = this.speed * (d6 / d1) + paramPNodes.speed * ((d1 - d6) / d1);
      double d10 = (d8 + d9) / 2.0D;
      if (j == 1) {
        if (this.timeoutMin != 0.0D) d8 = 0.0D; else
          d8 = this.speed;
      }
      if (j == i - 1) {
        if (paramPNodes.timeoutMin != 0.0D) d9 = 0.0D; else
          d9 = paramPNodes.speed;
      }
      if ((d8 == 0.0D) && (d9 == 0.0D))
        d10 /= 2.0D;
      else {
        d10 = (d8 + d9) / 2.0D;
      }
      d2 += d7 / d10;

      _prevP.set(_curP);
    }
    return d2;
  }

  private void syncPathPos() {
    Path localPath = (Path)getOwner();
    if (localPath.points() <= 1) return;
    PNodes localPNodes = (PNodes)localPath.point(0);
    _curP.set(localPNodes.posXYZ[0], localPNodes.posXYZ[1], localPNodes.posXYZ[2]);

    Point3d localPoint3d = localPNodes.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
    if (!localPoint3d.equals(_curP)) {
      localPNodes.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(_curP);
      localPNodes.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();

      _curP.set(localPNodes.posXYZ[4], localPNodes.posXYZ[5], localPNodes.posXYZ[6]);

      ((PathChief)localPath).placeUnits(localPNodes.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint(), _curP);
    }
    for (int i = 1; i < localPath.points(); i++) {
      localPNodes = (PNodes)localPath.point(i - 1);
      int j = localPNodes.posXYZ.length / 4 - 1;
      _curP.set(localPNodes.posXYZ[(j * 4 + 0)], localPNodes.posXYZ[(j * 4 + 1)], localPNodes.posXYZ[(j * 4 + 2)]);

      localPath.point(i).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(_curP);
      localPath.point(i).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
    }
  }

  public void moveTo(Point3d paramPoint3d) {
    Path localPath = (Path)getOwner();
    int i = localPath.moveType;
    if (i >= 0) {
      PathFind.setMoverType(i);
    }
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(_movePrevP);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(paramPoint3d);
    align();
    int j = localPath.points();
    if (j == 1) {
      if (!PathFind.setStartPoint(0, this)) {
        this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(_movePrevP);
        throw new ActorException("PathPoint not Reacheable");
      }
    } else {
      int k = localPath.pointIndx(this);
      PNodes localPNodes1;
      if (k == j - 1) {
        localPNodes1 = (PNodes)localPath.point(k - 1);
        if ((!PathFind.setStartPoint(0, localPNodes1)) || (!PathFind.isPointReacheable(0, this)))
        {
          this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(_movePrevP);
          throw new ActorException("PathPoint not Reacheable");
        }
        localPNodes1.posXYZ = PathFind.buildPath(0, this);
      }
      else if (k == 0) {
        localPNodes1 = (PNodes)localPath.point(k + 1);
        if ((!PathFind.setStartPoint(0, localPNodes1)) || (!PathFind.isPointReacheable(0, this)))
        {
          this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(_movePrevP);
          throw new ActorException("PathPoint not Reacheable");
        }
        this.posXYZ = PathFind.buildPath(0, this);
        reversePathXY(this.posXYZ);
      }
      else {
        localPNodes1 = (PNodes)localPath.point(k - 1);
        PNodes localPNodes2 = (PNodes)localPath.point(k + 1);
        if ((!PathFind.setStartPoint(0, localPNodes1)) || (!PathFind.setStartPoint(1, localPNodes2)) || (!PathFind.isPointReacheable(0, this)) || (!PathFind.isPointReacheable(1, this)))
        {
          this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(_movePrevP);
          throw new ActorException("PathPoint not Reacheable");
        }
        localPNodes1.posXYZ = PathFind.buildPath(0, this);
        this.posXYZ = PathFind.buildPath(1, this);
        reversePathXY(this.posXYZ);
      }
    }
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
    syncPathPos();
  }

  public void destroy()
  {
    if (isDestroyed()) return;
    Path localPath = (Path)getOwner();
    if (!Actor.isValid(localPath)) {
      super.destroy();
      return;
    }
    int i = localPath.points();
    if (i > 1) {
      int j = localPath.pointIndx(this);
      PNodes localPNodes1;
      if (j == i - 1)
      {
        localPNodes1 = (PNodes)localPath.point(j - 1);
        localPNodes1.posXYZ = null;
      } else if (j != 0)
      {
        localPNodes1 = (PNodes)localPath.point(j - 1);
        PNodes localPNodes2 = (PNodes)localPath.point(j + 1);
        if ((!PathFind.setStartPoint(0, localPNodes1)) || (!PathFind.isPointReacheable(0, localPNodes2)))
        {
          throw new ActorException("PathPoint not Reacheable");
        }
        localPNodes1.posXYZ = PathFind.buildPath(0, localPNodes2);
      }
    }
    super.destroy();
    localPath.computeTimes();
    PathChief localPathChief = (PathChief)localPath;
    localPathChief.updateUnitsPos();
  }

  private void reversePathXY(float[] paramArrayOfFloat) {
    int i = paramArrayOfFloat.length / 4;

    int j = 0;
    int k = i - 1;
    while (j < k) {
      float f1 = paramArrayOfFloat[(j * 4 + 0)];
      float f2 = paramArrayOfFloat[(j * 4 + 1)];
      float f3 = paramArrayOfFloat[(j * 4 + 2)];
      float f4 = paramArrayOfFloat[(j * 4 + 3)];
      paramArrayOfFloat[(j * 4 + 0)] = paramArrayOfFloat[(k * 4 + 0)];
      paramArrayOfFloat[(j * 4 + 1)] = paramArrayOfFloat[(k * 4 + 1)];
      paramArrayOfFloat[(j * 4 + 2)] = paramArrayOfFloat[(k * 4 + 2)];
      paramArrayOfFloat[(j * 4 + 3)] = paramArrayOfFloat[(k * 4 + 3)];
      paramArrayOfFloat[(k * 4 + 0)] = f1;
      paramArrayOfFloat[(k * 4 + 1)] = f2;
      paramArrayOfFloat[(k * 4 + 2)] = f3;
      paramArrayOfFloat[(k * 4 + 3)] = f4;
      j++; k--;
    }
  }

  public PNodes(Path paramPath, PPoint paramPPoint, Mat paramMat, Point3d paramPoint3d) {
    super(paramPath, paramPPoint, paramMat, paramPoint3d);
    if (paramPoint3d == null)
      return;
    int i = paramPath.moveType;
    if (i >= 0) {
      PathFind.setMoverType(i);
    }
    int j = paramPath.points();
    if (j == 1) {
      if (!PathFind.setStartPoint(0, this)) {
        super.destroy();
        throw new ActorException("PathPoint not Reacheable");
      }
    }
    else {
      int k = paramPath.pointIndx(this);
      PNodes localPNodes1;
      if (k == j - 1) {
        localPNodes1 = (PNodes)paramPath.point(k - 1);
        if ((!PathFind.setStartPoint(0, localPNodes1)) || (!PathFind.isPointReacheable(0, this)))
        {
          super.destroy();
          throw new ActorException("PathPoint not Reacheable");
        }
        localPNodes1.posXYZ = PathFind.buildPath(0, this);
      }
      else if (k == 0) {
        localPNodes1 = (PNodes)paramPath.point(k + 1);
        if ((!PathFind.setStartPoint(0, this)) || (!PathFind.isPointReacheable(0, localPNodes1)))
        {
          super.destroy();
          throw new ActorException("PathPoint not Reacheable");
        }
        this.posXYZ = PathFind.buildPath(0, localPNodes1);
      }
      else {
        localPNodes1 = (PNodes)paramPath.point(k - 1);
        PNodes localPNodes2 = (PNodes)paramPath.point(k + 1);
        if ((!PathFind.setStartPoint(0, localPNodes1)) || (!PathFind.setStartPoint(1, localPNodes2)) || (!PathFind.isPointReacheable(0, this)) || (!PathFind.isPointReacheable(1, this)))
        {
          super.destroy();
          throw new ActorException("PathPoint not Reacheable");
        }
        localPNodes1.posXYZ = PathFind.buildPath(0, this);
        this.posXYZ = PathFind.buildPath(1, this);
        reversePathXY(this.posXYZ);
      }
    }
    if (((paramPath instanceof PathChief)) && (Actor.isValid(paramPath))) {
      PathChief localPathChief = (PathChief)paramPath;
      localPathChief.updateUnitsPos();
    }
    syncPathPos();
    paramPath.computeTimes();
  }

  public PNodes(Path paramPath, PPoint paramPPoint, Point3d paramPoint3d) {
    this(paramPath, paramPPoint, (Mat)null, paramPoint3d);
    if (paramPPoint != null)
      this.icon = paramPPoint.icon;
  }

  public PNodes(Path paramPath, PPoint paramPPoint, String paramString, Point3d paramPoint3d) {
    this(paramPath, paramPPoint, IconDraw.get(paramString), paramPoint3d);
  }

  static
  {
    Property.set(PNodes.class, "iconName", "icons/tank.mat");

    _prevP = new Point3d();
    _curP = new Point3d();

    _movePrevP = new Point3d();
  }
}