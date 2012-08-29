package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunVickers40mmk extends MGunVickers40mms
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 1.6F;
    localGunProperties.maxDeltaAngle = 0.01F;
    localGunProperties.shotFreqDeviation = 0.03F;
    return localGunProperties;
  }
}