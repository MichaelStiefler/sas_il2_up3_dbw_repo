package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBredaSAFAT127siCR42 extends MGunBredaSAFAT127s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.shotFreq = 10.0F;
    return localGunProperties;
  }
}