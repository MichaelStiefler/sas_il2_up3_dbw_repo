package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunHispanoMkIt extends MGunHispanoMkIs
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = false;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 10.833333F;

    localGunProperties.maxDeltaAngle = 0.24F;

    return localGunProperties;
  }
}