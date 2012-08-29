package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBrowning50kAPI extends MGunBrowning50APIT
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = "3DO/Effects/GunShells/GunShells.eff";
    localGunProperties.shotFreq = 12.5F;

    localGunProperties.maxDeltaAngle = 0.349F;
    localGunProperties.shotFreqDeviation = 0.08F;

    return localGunProperties;
  }
}