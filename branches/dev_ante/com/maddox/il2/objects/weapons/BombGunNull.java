package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunNull extends BombGun
{
  public void setBombDelay(float paramFloat)
  {
  }

  public void shots(int paramInt)
  {
    bullets(0);
  }

  static {
    Class localClass = BombGunNull.class;
    Property.set(localClass, "bulletClass", BombPhBall.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 1.0F);

    Property.set(localClass, "sound", "weapon.bombgun_phball");
  }
}