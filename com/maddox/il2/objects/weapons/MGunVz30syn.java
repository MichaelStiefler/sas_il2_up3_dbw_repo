package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunVz30syn extends MGunVz30s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.shotFreq = 13.0F;
    localGunProperties.shotFreqDeviation = 0.04F;
    localGunProperties.emitI = 2.0F;
    return localGunProperties;
  }
}