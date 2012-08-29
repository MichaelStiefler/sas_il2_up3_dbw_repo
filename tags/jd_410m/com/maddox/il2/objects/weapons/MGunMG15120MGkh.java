package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunMG15120MGkh extends MGunMG15120MGk
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bEnablePause = true;

    localGunProperties.maxDeltaAngle = 0.28F;

    return localGunProperties;
  }
}