package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunM4ki extends MGunM4s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 2.666667F;
    localGunProperties.shotFreqDeviation = 0.02F;

    return localGunProperties;
  }
}