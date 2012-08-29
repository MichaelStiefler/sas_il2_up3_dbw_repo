package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBrowning303MOsi extends MGunBrowning303MOs
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.emitI = 2.5F;
    localGunProperties.emitR = 1.5F;
    localGunProperties.shotFreq = 11.66F;
    return localGunProperties;
  }
}