package com.maddox.il2.ai.ground;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.BulletProperties;

public abstract interface Prey
{
  public abstract int HitbyMask();

  public abstract int chooseBulletType(BulletProperties[] paramArrayOfBulletProperties);

  public abstract int chooseShotpoint(BulletProperties paramBulletProperties);

  public abstract boolean getShotpointOffset(int paramInt, Point3d paramPoint3d);
}