package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.objects.effects.Explosions;

public class BulletAntiAirSmallGermany extends BulletAntiAirGeneric
{
  public BulletAntiAirSmallGermany(int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d, long paramLong1, long paramLong2)
  {
    super(paramInt, paramGunGeneric, paramLoc, paramVector3d, paramLong1 + paramLong2, -1.0F, paramLong2 != 0L);
  }

  protected void explodeInAir_Effect(Point3d paramPoint3d) {
    Explosions.AirFlak(paramPoint3d, 3);
  }
}