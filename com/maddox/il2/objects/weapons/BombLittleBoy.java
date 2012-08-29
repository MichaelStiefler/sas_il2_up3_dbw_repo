package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Mesh;
import com.maddox.rts.Property;

public class BombLittleBoy extends Bomb
{
  private static final int alhambras = 1;

  public BombLittleBoy()
  {
    if ((Config.isUSE_RENDER()) && (World.Rnd().nextInt(0, 99) < 20)) {
      setMesh(Property.stringValue(getClass(), "mesh"));
      this.mesh.materialReplace("Ordnance1", "alhambra" + World.Rnd().nextInt(1, 1));
    }
  }

  static
  {
    Class localClass = BombLittleBoy.class;

    Property.set(localClass, "mesh", "3DO/Arms/LittleBoy/mono.sim");
    Property.set(localClass, "radius", 3200.0F);
    Property.set(localClass, "power", 8000000.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 1.0F);
    Property.set(localClass, "massa", 4000.0F);
    Property.set(localClass, "sound", "weapon.bomb_big");
    Property.set(localClass, "newEffect", 1);
    Property.set(localClass, "nuke", 1);
  }
}