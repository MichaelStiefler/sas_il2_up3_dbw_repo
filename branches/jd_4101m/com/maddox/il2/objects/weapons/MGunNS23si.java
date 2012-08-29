package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunNS23si extends MGunNS23s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    return localGunProperties;
  }
}