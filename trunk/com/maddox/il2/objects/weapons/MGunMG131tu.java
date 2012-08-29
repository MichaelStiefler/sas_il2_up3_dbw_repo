package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunMG131tu extends MGunMG131s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = false;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 15.0F;

    localGunProperties.maxDeltaAngle = 0.21F;

    return localGunProperties;
  }
}