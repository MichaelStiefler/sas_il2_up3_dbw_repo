package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBrowning50k extends MGunBrowning50s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = "3DO/Effects/GunShells/GunShells.eff";
    localGunProperties.emitI = 2.5F;
    localGunProperties.emitR = 1.5F;
    localGunProperties.shotFreq = 12.5F;
    localGunProperties.maxDeltaAngle = 0.229F;
    localGunProperties.shotFreqDeviation = 0.02F;
    localGunProperties.bullet[0].traceColor = -761369619;
    return localGunProperties;
  }
}