package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

public class CannonQFMkXII_120mm extends com.maddox.il2.objects.weapons.CannonAntiAirGeneric
{

    public CannonQFMkXII_120mm()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 15545F;
        gunproperties.fireMesh = null;
        gunproperties.fire = "Effects/Bigship/GunFire150mm/Fire.eff";
        gunproperties.sprite = null;
        gunproperties.smoke = "Effects/BigShip/GunFire150mm/Burst.eff";
        gunproperties.shells = null;
        gunproperties.emitColor = new Color3f(1.0F, 0.5F, 0.2F);
        gunproperties.emitI = 5F;
        gunproperties.emitR = 13F;
        gunproperties.emitTime = 0.3F;
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 15F;
        bulletproperties.power = 3.4F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 136F;
        bulletproperties.kalibr = 0.12F;
        bulletproperties.massa = 23F;
        bulletproperties.speed = 808F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0x1010101;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 0.9F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 122F;
        bulletproperties.kalibr = 0.12F;
        bulletproperties.massa = 22.7F;
        bulletproperties.speed = 808F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0x1010101;
        return 38F;
    }

  public Bullet createNextBullet(Vector3d paramVector3d1, int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d2, long paramLong)
  {
    return new BulletAntiAirBigGermany(paramVector3d1, paramInt, paramGunGeneric, paramLoc, paramVector3d2, paramLong, this.explodeAtHeight);
  }
}
