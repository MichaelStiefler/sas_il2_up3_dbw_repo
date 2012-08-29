package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunB20t extends MGunB20s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = false;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 13.333333F;

    localGunProperties.maxDeltaAngle = 0.0F;

    return localGunProperties;
  }
}