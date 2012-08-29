package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunMK103k extends MGunMK103s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
    localGunProperties.shotFreq = 7.083334F;

    localGunProperties.maxDeltaAngle = 0.35F;
    localGunProperties.shotFreqDeviation = 0.02F;

    return localGunProperties;
  }
}