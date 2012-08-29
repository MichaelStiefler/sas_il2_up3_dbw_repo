package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Mesh;
import com.maddox.rts.Property;

public class BombFAB5000 extends Bomb
{
  private static final int alhambras = 1;

  public BombFAB5000()
  {
    if ((Config.isUSE_RENDER()) && (World.Rnd().nextInt(0, 99) < 20)) {
      setMesh(Property.stringValue(getClass(), "mesh"));
      this.mesh.materialReplace("Ordnance1", "alhambra" + World.Rnd().nextInt(1, 1));
    }
  }

  static
  {
    Class localClass = BombFAB5000.class;
    Property.set(localClass, "mesh", "3DO/Arms/FAB-5000/mono.sim");
    Property.set(localClass, "radius", 2500.0F);
    Property.set(localClass, "power", 3260.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 1.0F);
    Property.set(localClass, "massa", 5000.0F);
    Property.set(localClass, "sound", "weapon.bomb_big");
  }
}