package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.objects.effects.Explosions;

public class BulletAntiAirBigGermany extends BulletAntiAirGeneric
{
  public BulletAntiAirBigGermany(Vector3d paramVector3d1, int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d2, long paramLong, float paramFloat)
  {
    super(paramVector3d1, paramInt, paramGunGeneric, paramLoc, paramVector3d2, paramLong, paramFloat, false);
  }

  protected void explodeInAir_Effect(Point3d paramPoint3d) {
    Explosions.AirFlak(paramPoint3d, 1);
  }
}