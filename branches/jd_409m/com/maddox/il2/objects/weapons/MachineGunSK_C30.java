package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

public class MachineGunSK_C30 extends CannonAntiAirGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 8500.0F;

    paramGunProperties.sound = "weapon.zenitka_37c";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.timeLife = 10.5F;
    localBulletProperties.addExplTime = 1.5F;

    localBulletProperties.power = 0.365F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 80.0F;

    localBulletProperties.kalibr = 0.037F;
    localBulletProperties.massa = 0.742F;
    localBulletProperties.speed = 1000.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmBlue/mono.sim";
    localBulletProperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localBulletProperties.traceColor = -755040256;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.power = 0.0F;

    localBulletProperties.kalibr = 0.037F;
    localBulletProperties.massa = 0.742F;
    localBulletProperties.speed = 1000.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
    localBulletProperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localBulletProperties.traceColor = -770532113;

    return 83.0F;
  }

  public Bullet createNextBullet(int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d, long paramLong)
  {
    return new BulletAntiAirBigGermany(paramInt, paramGunGeneric, paramLoc, paramVector3d, paramLong, this.explodeAtHeight);
  }
}