package com.maddox.il2.ai.ground;

import com.maddox.il2.engine.Accumulator;
import com.maddox.il2.engine.Actor;

public class ScareEnemies
  implements Accumulator
{
  private int usedWeaponsMask;
  private static ScareEnemies enemies = new ScareEnemies();

  public static ScareEnemies enemies() { return enemies; }

  public static void set(int paramInt) {
    enemies.usedWeaponsMask = paramInt;
  }

  public void clear()
  {
  }

  public boolean add(Actor paramActor, double paramDouble)
  {
    if ((!(paramActor instanceof Coward)) || (!(paramActor instanceof Prey)) || ((((Prey)paramActor).HitbyMask() & this.usedWeaponsMask) == 0))
    {
      return true;
    }

    ((Coward)paramActor).scare();
    return true;
  }
}