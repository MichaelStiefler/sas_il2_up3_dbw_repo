package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunHo5ki extends MGunHo5s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 14.166667F;

    localGunProperties.maxDeltaAngle = 0.24F;
    localGunProperties.shotFreqDeviation = 0.02F;

    return localGunProperties;
  }
}