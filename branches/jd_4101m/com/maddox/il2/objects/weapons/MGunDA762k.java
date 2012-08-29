package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunDA762k extends MGunDA762s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = "3DO/Effects/GunShells/GunShells.eff";
    localGunProperties.shotFreq = 10.0F;
    localGunProperties.shotFreqDeviation = 0.04F;

    return localGunProperties;
  }
}