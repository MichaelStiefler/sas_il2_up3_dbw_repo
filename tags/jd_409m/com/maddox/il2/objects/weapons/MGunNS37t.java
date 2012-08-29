package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunNS37t extends MGunNS37s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = false;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 4.333334F;

    return localGunProperties;
  }
}