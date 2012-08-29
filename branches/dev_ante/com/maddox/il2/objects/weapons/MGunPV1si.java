package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunPV1si extends MGunShKASs
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.emitI = 2.5F;
    localGunProperties.emitR = 1.5F;
    localGunProperties.shotFreq = 16.0F;
    localGunProperties.maxDeltaAngle = 0.3F;
    return localGunProperties;
  }
}