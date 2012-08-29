package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBredaSAFAT127ki extends MGunBredaSAFAT127s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 11.666667F;

    localGunProperties.maxDeltaAngle = 0.229F;
    localGunProperties.shotFreqDeviation = 0.02F;

    return localGunProperties;
  }
}