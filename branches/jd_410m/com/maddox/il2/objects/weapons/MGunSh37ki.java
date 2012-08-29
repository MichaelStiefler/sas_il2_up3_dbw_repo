package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunSh37ki extends MGunSh37s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 2.0F;

    localGunProperties.maxDeltaAngle = 0.42F;
    localGunProperties.shotFreqDeviation = 0.02F;

    return localGunProperties;
  }
}