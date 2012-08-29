package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunB20si extends MGunB20s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 10.0F;

    localGunProperties.maxDeltaAngle = 0.12F;

    return localGunProperties;
  }
}