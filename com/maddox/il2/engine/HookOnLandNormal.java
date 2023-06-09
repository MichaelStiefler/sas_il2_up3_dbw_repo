package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;

public class HookOnLandNormal extends Hook
{
  public double dz = 0.1D;

  private static Vector3f normal = new Vector3f();
  private static Point3d p = new Point3d();
  private static Orient o = new Orient();

  public void computePos(Actor paramActor, Loc paramLoc1, Loc paramLoc2)
  {
    paramLoc2.get(p, o);
    p.z = (Engine.land().HQ(p.x, p.y) + this.dz);
    Engine.land().N(p.x, p.y, normal);
    o.orient(normal);
    paramLoc2.set(p, o);
  }

  public HookOnLandNormal(double paramDouble)
  {
    this.dz = paramDouble;
  }
}