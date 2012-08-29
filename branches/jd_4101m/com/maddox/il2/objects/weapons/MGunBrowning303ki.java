package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBrowning303ki extends MGunBrowning303s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 16.666666F;

    localGunProperties.maxDeltaAngle = 0.44F;
    localGunProperties.shotFreqDeviation = 0.04F;

    return localGunProperties;
  }
}