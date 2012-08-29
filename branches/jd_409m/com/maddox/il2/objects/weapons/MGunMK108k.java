package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunMK108k extends MGunMK108s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
    localGunProperties.shotFreq = 11.0F;

    localGunProperties.maxDeltaAngle = 0.43F;
    localGunProperties.shotFreqDeviation = 0.02F;

    return localGunProperties;
  }
}