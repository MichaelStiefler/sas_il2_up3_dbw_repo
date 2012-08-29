package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunVz30sS328 extends MGunVz30s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.01F;
    localGunProperties.bullet[0].kalibr = 4.35483E-005F;
    localGunProperties.bullet[0].speed = 860.0F;
    localGunProperties.bullet[0].power = 0.0F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.0F;
    localGunProperties.bullet[0].traceMesh = null;
    localGunProperties.bullet[0].traceTrail = null;
    localGunProperties.bullet[0].traceColor = 0;
    localGunProperties.bullet[0].timeLife = 3.0F;

    localGunProperties.bullet[1].massa = 0.01F;
    localGunProperties.bullet[1].kalibr = 8.0E-005F;
    localGunProperties.bullet[1].speed = 860.0F;
    localGunProperties.bullet[1].power = 0.0F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 0.0F;
    localGunProperties.bullet[1].traceMesh = null;
    localGunProperties.bullet[1].traceTrail = null;
    localGunProperties.bullet[1].traceColor = 0;
    localGunProperties.bullet[1].timeLife = 3.0F;

    localGunProperties.bullet[2].massa = 0.01F;
    localGunProperties.bullet[2].kalibr = 4.35483E-005F;
    localGunProperties.bullet[2].speed = 860.0F;
    localGunProperties.bullet[2].power = 0.0F;
    localGunProperties.bullet[2].powerType = 0;
    localGunProperties.bullet[2].powerRadius = 0.0F;
    localGunProperties.bullet[2].traceMesh = null;
    localGunProperties.bullet[2].traceTrail = null;
    localGunProperties.bullet[2].traceColor = 0;
    localGunProperties.bullet[2].timeLife = 3.0F;

    localGunProperties.bullet[3].massa = 0.01F;
    localGunProperties.bullet[3].kalibr = 8.0E-005F;
    localGunProperties.bullet[3].speed = 860.0F;
    localGunProperties.bullet[3].power = 0.0F;
    localGunProperties.bullet[3].powerType = 0;
    localGunProperties.bullet[3].powerRadius = 0.0F;
    localGunProperties.bullet[3].traceMesh = null;
    localGunProperties.bullet[3].traceTrail = null;
    localGunProperties.bullet[3].traceColor = 0;
    localGunProperties.bullet[3].timeLife = 3.0F;

    localGunProperties.bullet[4].massa = 0.01F;
    localGunProperties.bullet[4].kalibr = 4.35483E-005F;
    localGunProperties.bullet[4].speed = 860.0F;
    localGunProperties.bullet[4].power = 0.002F;
    localGunProperties.bullet[4].powerType = 0;
    localGunProperties.bullet[4].powerRadius = 0.0F;
    localGunProperties.bullet[4].traceMesh = "3DO/Effects/Tracers/20mmRed/mono.sim";
    localGunProperties.bullet[4].traceTrail = null;
    localGunProperties.bullet[4].traceColor = -771739905;
    localGunProperties.bullet[4].timeLife = 3.0F;

    return localGunProperties;
  }
}