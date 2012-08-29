package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunHo103ki extends MGunHo103s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 15.0F;

    localGunProperties.maxDeltaAngle = 0.24F;
    localGunProperties.shotFreqDeviation = 0.02F;

    return localGunProperties;
  }
}