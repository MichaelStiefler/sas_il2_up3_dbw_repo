package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBrowning50tdual extends MGunBrowning50s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = false;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 25.0F;
    localGunProperties.bulletsCluster *= 2;
    localGunProperties.weaponType = 1;
    localGunProperties.sound = "weapon.mgun_15_dual";

    localGunProperties.maxDeltaAngle = 0.229F;

    return localGunProperties;
  }
}