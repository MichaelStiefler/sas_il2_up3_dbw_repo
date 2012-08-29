package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunHo115k extends MGunHo115s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
    localGunProperties.shotFreq = 10.0F;

    localGunProperties.maxDeltaAngle = 0.43F;
    localGunProperties.shotFreqDeviation = 0.02F;

    return localGunProperties;
  }
}