package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunHo5t extends MGunHo5s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = false;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 14.166667F;

    localGunProperties.maxDeltaAngle = 0.24F;

    return localGunProperties;
  }
}