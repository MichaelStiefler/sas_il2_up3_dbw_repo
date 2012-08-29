package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBrowning303Trak extends MGunBrowning303s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = "3DO/Effects/GunShells/GunShells.eff";
    localGunProperties.emitI = 2.5F;
    localGunProperties.emitR = 1.5F;
    localGunProperties.shotFreq = 16.66667F;
    localGunProperties.traceFreq = 0;
    localGunProperties.maxDeltaAngle = 0.44F;
    localGunProperties.shotFreqDeviation = 0.04F;
    return localGunProperties;
  }
}