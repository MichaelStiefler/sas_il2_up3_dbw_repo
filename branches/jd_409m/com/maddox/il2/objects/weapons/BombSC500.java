package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Mesh;
import com.maddox.rts.Property;

public class BombSC500 extends Bomb
{
  private static final int alhambras = 6;

  public BombSC500()
  {
    if ((Config.isUSE_RENDER()) && (World.Rnd().nextInt(0, 99) < 25)) {
      setMesh(Property.stringValue(getClass(), "mesh"));
      this.mesh.materialReplace("Ordnance1", "alhambra" + World.Rnd().nextInt(1, 6));
    }
  }

  static
  {
    Class localClass = BombSC500.class;
    Property.set(localClass, "mesh", "3do/arms/SC-500/mono.sim");
    Property.set(localClass, "radius", 82.0F);
    Property.set(localClass, "power", 220.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.4572F);
    Property.set(localClass, "massa", 500.0F);
    Property.set(localClass, "sound", "weapon.bomb_std");
  }
}