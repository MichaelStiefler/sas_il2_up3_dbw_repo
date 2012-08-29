package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunMG15si extends MGunMG15s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 15.0F;

    return localGunProperties;
  }
}