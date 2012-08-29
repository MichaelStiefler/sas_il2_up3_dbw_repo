package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBrowning303k extends MGunBrowning303s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bUseHookAsRel = true;
    localGunProperties.fireMesh = "3DO/Effects/GunFire/7mm/mono.sim";
    localGunProperties.fire = null;
    localGunProperties.sprite = "3DO/Effects/GunFire/7mm/GunFlare.eff";
    localGunProperties.smoke = "effects/smokes/SmallGun.eff";
    localGunProperties.shells = "3DO/Effects/GunShells/GunShells.eff";
    localGunProperties.shotFreq = 16.66667F;
    localGunProperties.maxDeltaAngle = 0.44F;
    localGunProperties.shotFreqDeviation = 0.04F;
    return localGunProperties;
  }
}