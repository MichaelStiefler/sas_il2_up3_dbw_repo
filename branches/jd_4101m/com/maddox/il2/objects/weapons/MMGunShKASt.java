package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MMGunShKASt extends MGunShKASt
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.sound = "weapon.mgun_07_1500t";

    return localGunProperties;
  }
}