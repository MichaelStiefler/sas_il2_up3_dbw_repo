package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunDA762si extends MGunShKASs
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 9.166667F;

    return localGunProperties;
  }
}