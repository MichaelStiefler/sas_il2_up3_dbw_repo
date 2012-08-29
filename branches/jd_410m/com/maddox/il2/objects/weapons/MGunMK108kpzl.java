package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.GunProperties;

public class MGunMK108kpzl extends MGunMK108s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
    localGunProperties.shotFreq = 11.0F;

    localGunProperties.maxDeltaAngle = 0.43F;

    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 0.0F;
    localGunProperties.emitR = 3.0F;
    localGunProperties.emitTime = 0.03F;
    localGunProperties.shotFreqDeviation = 0.02F;

    return localGunProperties;
  }
}