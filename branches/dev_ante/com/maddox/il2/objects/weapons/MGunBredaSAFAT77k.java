package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBredaSAFAT77k extends MGunBredaSAFAT77s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = "3DO/Effects/GunShells/GunShells.eff";
    localGunProperties.shotFreq = 15.0F;

    localGunProperties.maxDeltaAngle = 0.44F;
    localGunProperties.shotFreqDeviation = 0.02F;

    return localGunProperties;
  }
}