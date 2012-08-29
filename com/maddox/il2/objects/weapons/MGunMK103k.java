package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunMK103k extends MGunMK103s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bUseHookAsRel = true;
    localGunProperties.fireMesh = null;
    localGunProperties.fire = "3DO/Effects/GunFire/30mm/GunFire.eff";
    localGunProperties.sprite = "3DO/Effects/GunFire/30mm/GunFlare.eff";
    localGunProperties.smoke = "effects/smokes/Gun.eff";
    localGunProperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
    localGunProperties.sound = "weapon.mgun_30_500";
    localGunProperties.emitI = 2.5F;
    localGunProperties.emitR = 1.5F;
    localGunProperties.emitTime = 0.03F;
    localGunProperties.shotFreq = 7.083333F;
    localGunProperties.maxDeltaAngle = 0.35F;
    localGunProperties.shotFreqDeviation = 0.02F;
    return localGunProperties;
  }
}