package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunPV1i extends MGunPV1
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    return localGunProperties;
  }
}