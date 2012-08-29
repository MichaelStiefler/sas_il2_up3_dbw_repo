package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunVickers40mmAPk extends MGunVickers40mmAPs
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.emitI = 2.5F;
    localGunProperties.emitR = 1.5F;
    localGunProperties.shotFreq = 1.6F;
    localGunProperties.maxDeltaAngle = 0.09F;
    localGunProperties.shotFreqDeviation = 0.04F;
    return localGunProperties;
  }
}