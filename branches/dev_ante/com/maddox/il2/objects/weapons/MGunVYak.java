package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunVYak extends MGunVYas
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
    localGunProperties.shotFreq = 10.0F;

    localGunProperties.maxDeltaAngle = 0.28F;
    localGunProperties.shotFreqDeviation = 0.03F;

    return localGunProperties;
  }
}