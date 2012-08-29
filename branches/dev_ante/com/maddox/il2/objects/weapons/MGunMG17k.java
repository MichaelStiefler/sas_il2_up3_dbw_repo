package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunMG17k extends MGunMG17s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = "3DO/Effects/GunShells/GunShells.eff";
    localGunProperties.shotFreq = 15.833333F;

    localGunProperties.maxDeltaAngle = 0.21F;
    localGunProperties.shotFreqDeviation = 0.04F;

    return localGunProperties;
  }
}