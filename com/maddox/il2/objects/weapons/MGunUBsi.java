package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunUBsi extends MGunUBs
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 15.0F;

    localGunProperties.maxDeltaAngle = 0.11F;

    return localGunProperties;
  }
}