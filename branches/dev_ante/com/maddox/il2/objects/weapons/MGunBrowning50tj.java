package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBrowning50tj extends MGunBrowning50s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = false;
    localGunProperties.shotFreq = 12.5F;

    localGunProperties.maxDeltaAngle = 0.229F;

    return localGunProperties;
  }
}