package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunN57ki extends MGunN57s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 4.283333F;
    localGunProperties.shotFreqDeviation = 0.02F;

    return localGunProperties;
  }
}