package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunM9si extends MGunM9s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 2.5F;

    return localGunProperties;
  }
}