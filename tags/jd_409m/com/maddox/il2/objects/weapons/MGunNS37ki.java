package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunNS37ki extends MGunNS37s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 4.333334F;
    localGunProperties.shotFreqDeviation = 0.03F;

    return localGunProperties;
  }
}