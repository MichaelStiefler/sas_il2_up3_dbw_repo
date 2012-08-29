package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Mesh;
import com.maddox.rts.Property;

public class BombSC1000 extends Bomb
{
  private static final int alhambras = 1;

  public BombSC1000()
  {
    if ((Config.isUSE_RENDER()) && (World.Rnd().nextInt(0, 99) < 25)) {
      setMesh(Property.stringValue(getClass(), "mesh"));
      this.mesh.materialReplace("Ordnance1", "alhambra" + World.Rnd().nextInt(1, 1));
    }
  }

  static
  {
    Class localClass = BombSC1000.class;
    Property.set(localClass, "mesh", "3DO/Arms/SC-1000/mono.sim");
    Property.set(localClass, "radius", 168.0F);
    Property.set(localClass, "power", 630.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.6604F);
    Property.set(localClass, "massa", 1090.0F);
    Property.set(localClass, "sound", "weapon.bomb_big");
  }
}