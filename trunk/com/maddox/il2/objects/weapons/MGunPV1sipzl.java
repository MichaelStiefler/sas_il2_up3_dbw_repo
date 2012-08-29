package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunPV1sipzl extends MGunPV1
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 10.7F;
    localGunProperties.shotFreqDeviation = 0.04F;
    return localGunProperties;
  }
}