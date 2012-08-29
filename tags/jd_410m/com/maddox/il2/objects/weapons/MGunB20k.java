package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunB20k extends MGunB20s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = "3DO/Effects/GunShells/GunShells.eff";
    localGunProperties.shotFreq = 13.333333F;

    localGunProperties.maxDeltaAngle = 0.14F;
    localGunProperties.shotFreqDeviation = 0.02F;

    return localGunProperties;
  }
}