package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunUBki extends MGunUBs
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 15.833333F;

    localGunProperties.maxDeltaAngle = 0.21F;
    localGunProperties.shotFreqDeviation = 0.02F;

    return localGunProperties;
  }
}