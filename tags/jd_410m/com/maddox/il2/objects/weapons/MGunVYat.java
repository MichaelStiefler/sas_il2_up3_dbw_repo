package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunVYat extends MGunVYas
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = false;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 10.0F;

    localGunProperties.maxDeltaAngle = 0.44F;

    return localGunProperties;
  }
}