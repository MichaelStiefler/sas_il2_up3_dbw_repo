package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBrowning50ki extends MGunBrowning50k
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.shells = null;
    localGunProperties.emitI = 2.5F;
    localGunProperties.emitR = 1.5F;
    return localGunProperties;
  }
}