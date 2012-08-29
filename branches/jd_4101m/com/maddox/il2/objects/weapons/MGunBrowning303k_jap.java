package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBrowning303k_jap extends MGunBrowning303s_jap
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = "3DO/Effects/GunShells/GunShells.eff";
    localGunProperties.shotFreq = 16.666666F;

    localGunProperties.maxDeltaAngle = 0.44F;
    localGunProperties.shotFreqDeviation = 0.04F;

    return localGunProperties;
  }
}