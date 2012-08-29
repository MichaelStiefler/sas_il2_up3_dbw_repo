package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MMGunBrowning50tdual extends MGunBrowning50tdual
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.sound = "weapon.mgun_15_dual_t";
    return localGunProperties;
  }
}