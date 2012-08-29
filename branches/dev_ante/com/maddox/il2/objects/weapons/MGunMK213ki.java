package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunMK213ki extends MGunMK213s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.emitI = 2.5F;
    localGunProperties.emitR = 1.5F;
    localGunProperties.shotFreq = 20.0F;
    localGunProperties.maxDeltaAngle = 0.43F;
    localGunProperties.shotFreqDeviation = 0.02F;
    return localGunProperties;
  }
}