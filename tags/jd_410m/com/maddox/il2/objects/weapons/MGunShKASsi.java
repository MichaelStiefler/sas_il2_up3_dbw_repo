package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunShKASsi extends MGunShKASs
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 30.0F;

    localGunProperties.maxDeltaAngle = 0.3F;

    return localGunProperties;
  }
}