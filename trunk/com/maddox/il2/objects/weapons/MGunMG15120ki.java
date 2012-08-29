package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunMG15120ki extends MGunMG15120s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 12.0F;

    localGunProperties.maxDeltaAngle = 0.28F;
    localGunProperties.shotFreqDeviation = 0.02F;

    return localGunProperties;
  }
}