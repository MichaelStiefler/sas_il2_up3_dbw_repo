package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBrowningM3 extends MGunBrowning50APIT
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 20.0F;
    localGunProperties.traceFreq = 5;
    localGunProperties.maxDeltaAngle = 0.229F;
    localGunProperties.bulletsCluster = 3;
    localGunProperties.emitI = 8.0F;
    localGunProperties.emitR = 1.5F;
    return localGunProperties;
  }
}