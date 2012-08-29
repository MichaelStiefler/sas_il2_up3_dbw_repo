package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunShKASk extends MGunShKASs
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = "3DO/Effects/GunShells/GunShells.eff";
    localGunProperties.shotFreq = 30.0F;

    localGunProperties.maxDeltaAngle = 0.62F;
    localGunProperties.shotFreqDeviation = 0.04F;

    return localGunProperties;
  }
}