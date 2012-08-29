package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.Loc;

public class BulletParabolaGeneric extends Bullet
{
  public BulletParabolaGeneric(Vector3d paramVector3d1, int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d2, long paramLong)
  {
    super(paramVector3d1, paramInt, paramGunGeneric, paramLoc, paramVector3d2, paramLong);
  }

  public void move(float paramFloat)
  {
    if (this.gun == null) return;

    this.p0.set(this.p1);
    this.p1.scaleAdd(paramFloat, this.speed, this.p0);
    this.speed.z += this.gun.bulletAG[indx()] * paramFloat;
  }

  public void timeOut()
  {
  }
}