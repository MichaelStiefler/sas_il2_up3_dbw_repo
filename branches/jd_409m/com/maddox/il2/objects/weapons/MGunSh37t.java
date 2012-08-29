package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunSh37t extends MGunSh37s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = false;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 2.0F;

    return localGunProperties;
  }
}