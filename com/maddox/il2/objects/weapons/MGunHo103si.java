package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunHo103si extends MGunHo103s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.sound = "weapon.MGunBredaSAFAT127s";
    localGunProperties.bUseHookAsRel = true;
    localGunProperties.shells = null;
    localGunProperties.shotFreq = 14.66667F;
    localGunProperties.maxDeltaAngle = 0.12F;
    return localGunProperties;
  }
}