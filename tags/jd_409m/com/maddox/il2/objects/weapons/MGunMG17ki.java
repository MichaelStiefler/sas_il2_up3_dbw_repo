package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunMG17ki extends MGunMG17s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 15.833333F;

    localGunProperties.maxDeltaAngle = 0.22F;
    localGunProperties.shotFreqDeviation = 0.04F;

    return localGunProperties;
  }
}