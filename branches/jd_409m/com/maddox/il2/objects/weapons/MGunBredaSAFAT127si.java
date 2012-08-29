package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBredaSAFAT127si extends MGunBredaSAFAT127s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 8.75F;

    localGunProperties.maxDeltaAngle = 0.229F;

    return localGunProperties;
  }
}