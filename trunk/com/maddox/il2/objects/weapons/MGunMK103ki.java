package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunMK103ki extends MGunMK103s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 7.083334F;

    localGunProperties.maxDeltaAngle = 0.35F;
    localGunProperties.shotFreqDeviation = 0.02F;

    return localGunProperties;
  }
}