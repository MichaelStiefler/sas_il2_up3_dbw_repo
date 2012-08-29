package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunShKASki extends MGunShKASs
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 30.0F;

    localGunProperties.maxDeltaAngle = 0.59F;
    localGunProperties.shotFreqDeviation = 0.04F;

    return localGunProperties;
  }
}