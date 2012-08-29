package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.rts.Property;

public class BombGun5339 extends TorpedoApparatus
{
  public BulletEmitter detach(int paramInt)
  {
    return null;
  }

  static
  {
    Class localClass = BombGun5339.class;
    Property.set(localClass, "bulletClass", Bomb5339.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.01F);
    Property.set(localClass, "external", 0);
    Property.set(localClass, "sound", "weapon.bombgun_torpedo");
  }
}