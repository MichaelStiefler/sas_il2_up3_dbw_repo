package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunMK108ki extends MGunMK108s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 11.0F;

    localGunProperties.maxDeltaAngle = 0.43F;
    localGunProperties.shotFreqDeviation = 0.02F;

    return localGunProperties;
  }
}