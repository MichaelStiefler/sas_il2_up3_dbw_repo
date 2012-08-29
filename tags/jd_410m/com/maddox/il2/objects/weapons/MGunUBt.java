package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunUBt extends MGunUBs
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = false;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 17.5F;
    localGunProperties.bulletsCluster = 1;

    localGunProperties.maxDeltaAngle = 0.25F;

    return localGunProperties;
  }
}