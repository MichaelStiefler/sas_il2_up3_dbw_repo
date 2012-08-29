package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunMG81ki extends MGunMG81s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 26.666666F;

    localGunProperties.maxDeltaAngle = 0.56F;
    localGunProperties.shotFreqDeviation = 0.04F;

    return localGunProperties;
  }
}