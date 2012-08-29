package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunM4t extends MGunM4s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = false;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 2.166667F;

    return localGunProperties;
  }
}