// Source File Name: BombMk12.java
// Author:           Storebror
// Last Modified by: Storebror 2011-06-01
package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.rts.Property;

public class BombMk12 extends Bomb {

  public BombMk12() {
    if (Config.isUSE_RENDER() && World.Rnd().nextInt(0, 99) < 20) {
      setMesh(Property.stringValue(getClass(), "mesh"));
      mesh.materialReplace("Ordnance1", "alhambra" + World.Rnd().nextInt(1, 1));
    }
  }
  private static final int alhambras = 1;

  static {
    Class class1 = com.maddox.il2.objects.weapons.BombMk12.class;
    Property.set(class1, "mesh", "3DO/Arms/MK12/mono.sim");
    Property.set(class1, "radius", 5000F);
    Property.set(class1, "power", 14000000F);
    Property.set(class1, "powerType", 0);
    Property.set(class1, "kalibr", 1.0F);
    Property.set(class1, "massa", 520F);
    Property.set(class1, "sound", "weapon.bomb_big");
    Property.set(class1, "newEffect", 1);
    Property.set(class1, "nuke", 1);
  }
}
