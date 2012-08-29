package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBredaSAFAT127g55k extends MGunBredaSAFAT127g55
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.shells = "3DO/Effects/GunShells/GunShells.eff";
    return localGunProperties;
  }
}