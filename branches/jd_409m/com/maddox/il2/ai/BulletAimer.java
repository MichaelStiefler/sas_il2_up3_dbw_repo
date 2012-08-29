package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;

public abstract interface BulletAimer
{
  public abstract float TravelTime(Point3d paramPoint3d1, Point3d paramPoint3d2);

  public abstract boolean FireDirection(Point3d paramPoint3d1, Point3d paramPoint3d2, Vector3d paramVector3d);
}