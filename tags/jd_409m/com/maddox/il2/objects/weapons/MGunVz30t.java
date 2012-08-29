package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunVz30t extends MGunVz30sS328
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bUseHookAsRel = false;
    localGunProperties.maxDeltaAngle = 0.25F;
    return localGunProperties;
  }
}