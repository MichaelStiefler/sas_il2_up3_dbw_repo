package com.maddox.il2.ai.ground;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;

public class UnitMove
{
  public Point3d pos;
  public float totalTime;
  public Vector3f normal;
  public boolean dontrun;
  public float walkSpeed;

  UnitMove(float paramFloat, Vector3f paramVector3f)
  {
    this.pos = null;
    this.totalTime = paramFloat;
    this.normal = new Vector3f(paramVector3f);
    this.dontrun = false;
  }

  public UnitMove(float paramFloat1, Point3d paramPoint3d, float paramFloat2, Vector3f paramVector3f, float paramFloat3)
  {
    this.pos = new Point3d(paramPoint3d);
    this.pos.z += paramFloat1;
    this.totalTime = paramFloat2;
    this.normal = new Vector3f(paramVector3f);
    if (paramFloat3 > 0.0F) {
      this.dontrun = true;
      this.walkSpeed = paramFloat3;
    }
    else {
      this.dontrun = false;
    }
  }
  public boolean IsLandAligned() {
    return this.normal.z < 0.0F;
  }
}