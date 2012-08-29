package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBredaSAFAT77t extends MGunBredaSAFAT77s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = false;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 15.0F;

    localGunProperties.maxDeltaAngle = 0.42F;

    return localGunProperties;
  }
}