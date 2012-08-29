package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.objects.effects.Explosions;

public class BulletAntiAirBigUSSR extends BulletAntiAirGeneric
{
  public BulletAntiAirBigUSSR(int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d, long paramLong, float paramFloat)
  {
    super(paramInt, paramGunGeneric, paramLoc, paramVector3d, paramLong, paramFloat, false);
  }

  protected void explodeInAir_Effect(Point3d paramPoint3d) {
    Explosions.AirFlak(paramPoint3d, 0);
  }
}