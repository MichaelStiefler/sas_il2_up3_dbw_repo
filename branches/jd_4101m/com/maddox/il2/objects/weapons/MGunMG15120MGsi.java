package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunMG15120MGsi extends MGunMG15120MGs
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 11.5F;

    localGunProperties.maxDeltaAngle = 0.25F;

    return localGunProperties;
  }
}