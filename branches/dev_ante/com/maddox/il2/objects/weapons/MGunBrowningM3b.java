package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBrowningM3b extends MGunBrowningM3
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bullet[0].traceMesh = null;
    localGunProperties.bullet[0].traceTrail = null;
    localGunProperties.bullet[0].traceColor = 0;
    localGunProperties.bullet[0].power = 0.000115F;
    localGunProperties.bullet[2].traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
    localGunProperties.bullet[2].traceTrail = "Effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[2].traceColor = -771686401;
    localGunProperties.bullet[2].power = 0.000115F;
    return localGunProperties;
  }
}