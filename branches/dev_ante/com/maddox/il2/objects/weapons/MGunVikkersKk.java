package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunVikkersKk extends MGunVikkersKs
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = "3DO/Effects/GunShells/GunShells.eff";
    localGunProperties.shotFreq = 15.833333F;
    localGunProperties.maxDeltaAngle = 0.22F;
    localGunProperties.shotFreqDeviation = 0.02F;

    return localGunProperties;
  }
}