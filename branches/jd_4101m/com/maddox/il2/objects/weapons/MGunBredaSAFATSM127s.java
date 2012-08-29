package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBredaSAFATSM127s extends MGunBredaSAFAT127s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bUseHookAsRel = false;
    localGunProperties.shotFreq = 11.666667F;
    return localGunProperties;
  }
}