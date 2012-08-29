package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunDA762t4d extends MGunShKASs
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = false;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 10.0F;

    return localGunProperties;
  }
}