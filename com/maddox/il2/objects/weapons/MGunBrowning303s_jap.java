package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunBrowning303s_jap extends MGunBrowning303s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.01066849F;
    localGunProperties.bullet[0].kalibr = 4.442132E-005F;
    localGunProperties.bullet[0].speed = 835.0F;
    localGunProperties.bullet[0].power = 0.0018F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.0F;
    localGunProperties.bullet[0].traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
    localGunProperties.bullet[0].traceTrail = null;
    localGunProperties.bullet[0].traceColor = -771686401;
    localGunProperties.bullet[0].timeLife = 2.5F;

    localGunProperties.bullet[1].massa = 0.01066849F;
    localGunProperties.bullet[1].kalibr = 4.442132E-005F;
    localGunProperties.bullet[1].speed = 835.0F;
    localGunProperties.bullet[1].power = 0.0F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 0.0F;
    localGunProperties.bullet[1].traceMesh = null;
    localGunProperties.bullet[1].traceTrail = null;
    localGunProperties.bullet[1].traceColor = 0;
    localGunProperties.bullet[1].timeLife = 1.0F;

    localGunProperties.bullet[2].massa = 0.01066849F;
    localGunProperties.bullet[2].kalibr = 4.442132E-005F;
    localGunProperties.bullet[2].speed = 835.0F;
    localGunProperties.bullet[2].power = 0.0F;
    localGunProperties.bullet[2].powerType = 0;
    localGunProperties.bullet[2].powerRadius = 0.0F;
    localGunProperties.bullet[2].traceMesh = null;
    localGunProperties.bullet[2].traceTrail = null;
    localGunProperties.bullet[2].traceColor = 0;
    localGunProperties.bullet[2].timeLife = 1.0F;

    localGunProperties.bullet[3].massa = 0.01066849F;
    localGunProperties.bullet[3].kalibr = 4.442132E-005F;
    localGunProperties.bullet[3].speed = 835.0F;
    localGunProperties.bullet[3].power = 0.0018F;
    localGunProperties.bullet[3].powerType = 0;
    localGunProperties.bullet[3].powerRadius = 0.0F;
    localGunProperties.bullet[3].traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
    localGunProperties.bullet[3].traceTrail = null;
    localGunProperties.bullet[3].traceColor = -771686401;
    localGunProperties.bullet[3].timeLife = 2.5F;

    localGunProperties.bullet[4].massa = 0.01066849F;
    localGunProperties.bullet[4].kalibr = 4.442132E-005F;
    localGunProperties.bullet[4].speed = 835.0F;
    localGunProperties.bullet[4].power = 0.0018F;
    localGunProperties.bullet[4].powerType = 0;
    localGunProperties.bullet[4].powerRadius = 0.0F;
    localGunProperties.bullet[4].traceMesh = null;
    localGunProperties.bullet[4].traceTrail = null;
    localGunProperties.bullet[4].traceColor = 0;
    localGunProperties.bullet[4].timeLife = 1.0F;

    localGunProperties.bullet[5].massa = 0.01066849F;
    localGunProperties.bullet[5].kalibr = 4.442132E-005F;
    localGunProperties.bullet[5].speed = 835.0F;
    localGunProperties.bullet[5].power = 0.0018F;
    localGunProperties.bullet[5].powerType = 0;
    localGunProperties.bullet[5].powerRadius = 0.0F;
    localGunProperties.bullet[5].traceMesh = null;
    localGunProperties.bullet[5].traceTrail = null;
    localGunProperties.bullet[5].traceColor = 0;
    localGunProperties.bullet[5].timeLife = 1.0F;

    return localGunProperties;
  }
}