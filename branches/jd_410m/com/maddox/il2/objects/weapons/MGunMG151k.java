package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunMG151k extends MGunMG151s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = "3DO/Effects/GunShells/GunShells.eff";
    localGunProperties.shotFreq = 11.666667F;

    localGunProperties.maxDeltaAngle = 0.25F;
    localGunProperties.shotFreqDeviation = 0.02F;

    return localGunProperties;
  }
}