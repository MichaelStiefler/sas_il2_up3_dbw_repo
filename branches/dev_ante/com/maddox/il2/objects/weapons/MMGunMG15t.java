package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MMGunMG15t extends MGunMG15t
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.sound = "weapon.mgun_07_900t";

    return localGunProperties;
  }
}