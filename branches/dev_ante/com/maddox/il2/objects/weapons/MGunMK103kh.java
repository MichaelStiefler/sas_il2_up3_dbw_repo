package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunMK103kh extends MGunMK103k
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bEnablePause = true;
    localGunProperties.shells = null;

    return localGunProperties;
  }
}