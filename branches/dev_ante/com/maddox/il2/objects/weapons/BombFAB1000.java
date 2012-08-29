package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Mesh;
import com.maddox.rts.Property;

public class BombFAB1000 extends Bomb
{
  private static final int alhambras = 8;

  public BombFAB1000()
  {
    if ((Config.isUSE_RENDER()) && (World.Rnd().nextInt(0, 99) < 10)) {
      setMesh(Property.stringValue(getClass(), "mesh"));
      this.mesh.materialReplace("Ordnance1", "alhambra" + World.Rnd().nextInt(1, 8));
    }
  }

  static
  {
    Class localClass = BombFAB1000.class;
    Property.set(localClass, "mesh", "3DO/Arms/FAB-1000/mono.sim");
    Property.set(localClass, "radius", 500.0F);
    Property.set(localClass, "power", 555.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.57F);
    Property.set(localClass, "massa", 1000.0F);
    Property.set(localClass, "sound", "weapon.bomb_big");
  }
}