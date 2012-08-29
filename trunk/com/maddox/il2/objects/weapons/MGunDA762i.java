package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunDA762i extends MGunDA762s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 10.0F;

    return localGunProperties;
  }
}