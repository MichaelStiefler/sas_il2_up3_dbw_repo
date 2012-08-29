package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.GunProperties;

public class MGunBrowning50kpzlAPI extends MGunBrowning50APIT
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 0.0F;
    localGunProperties.emitR = 3.0F;
    localGunProperties.emitTime = 0.03F;
    return localGunProperties;
  }
}