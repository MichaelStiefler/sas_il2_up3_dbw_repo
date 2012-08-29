package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunShVAKsi extends MGunShVAKs
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 13.333333F;

    localGunProperties.maxDeltaAngle = 0.12F;

    return localGunProperties;
  }
}