package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunN37ki extends MGunN37s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 5.183333F;
    localGunProperties.shotFreqDeviation = 0.03F;

    return localGunProperties;
  }
}