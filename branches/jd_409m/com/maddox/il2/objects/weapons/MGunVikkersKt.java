package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunVikkersKt extends MGunVikkersKs
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = false;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 15.833333F;
    localGunProperties.maxDeltaAngle = 0.22F;

    return localGunProperties;
  }
}