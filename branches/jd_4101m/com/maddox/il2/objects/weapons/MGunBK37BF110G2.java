package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBK37BF110G2 extends MGunBK37
{
  public void setConvDistance(float paramFloat1, float paramFloat2)
  {
    super.setConvDistance(paramFloat1, paramFloat2 - 0.5F);
  }

  public GunProperties createProperties() {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bullet[0].speed = 1170.0F;
    return localGunProperties;
  }
}