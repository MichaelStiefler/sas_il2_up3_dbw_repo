package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Mesh;
import com.maddox.rts.Property;

public class BombHC4000 extends Bomb
{
  private static final int alhambras = 1;

  static
  {
    Class class1 = BombHC4000.class;
    Property.set(class1, "mesh", "3DO/Arms/hc4000/hc4000.sim");
    Property.set(class1, "radius", 610.0F);
    Property.set(class1, "power", 1660.0F);
    Property.set(class1, "powerType", 0);
    Property.set(class1, "kalibr", 1.0F);
    Property.set(class1, "massa", 1786.0F);
    Property.set(class1, "sound", "weapon.bomb_big");
  }

  public BombHC4000()
  {
    if ((Config.isUSE_RENDER()) && (World.Rnd().nextInt(0, 99) < 20))
    {
      setMesh(Property.stringValue(getClass(), "mesh"));
      this.mesh.materialReplace("Ordnance1", "alhambra" + World.Rnd().nextInt(1, 1));
    }
  }

  static Class _mthclass$(String s)
  {
    try
    {
      class1 = Class.forName(s);
    }
    catch (ClassNotFoundException classnotfoundexception)
    {
      Class class1;
      throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }
    Class class1;
    return class1;
  }
}