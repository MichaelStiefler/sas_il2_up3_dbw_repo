package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunMG15ki extends MGunMG15s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 15.833333F;

    localGunProperties.maxDeltaAngle = 0.5F;
    localGunProperties.shotFreqDeviation = 0.04F;

    return localGunProperties;
  }
}