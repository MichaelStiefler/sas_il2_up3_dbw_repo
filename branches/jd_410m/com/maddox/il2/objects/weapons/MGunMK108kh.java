package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunMK108kh extends MGunMK108k
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bEnablePause = true;

    localGunProperties.maxDeltaAngle = 0.43F;

    return localGunProperties;
  }
}