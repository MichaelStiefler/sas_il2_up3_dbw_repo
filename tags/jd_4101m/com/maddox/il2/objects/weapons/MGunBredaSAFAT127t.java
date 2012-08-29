package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBredaSAFAT127t extends MGunBredaSAFAT127s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = false;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 11.666667F;

    localGunProperties.maxDeltaAngle = 0.229F;

    return localGunProperties;
  }
}