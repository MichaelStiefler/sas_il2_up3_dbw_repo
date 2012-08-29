package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MachineGunFlak38x4_20mm extends MachineGunFlak38_20mm
{
  protected float Specify(GunProperties paramGunProperties)
  {
    super.Specify(paramGunProperties);
    paramGunProperties.shotFreq = 32.0F;
    paramGunProperties.bulletsCluster = 2;
    paramGunProperties.traceFreq = 1;

    CannonMidrangeGeneric.autocomputeSplintersRadiuses(paramGunProperties.bullet);

    return 55.0F;
  }
}