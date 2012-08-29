package com.maddox.il2.ai.air;

import com.maddox.il2.engine.Accumulator;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.objects.air.Aircraft;

public class NearestAircraft
  implements Accumulator
{
  private static final int MAX_OBJECTS = 48;
  private static Aircraft[] nearAct = new Aircraft[48];
  private static double[] nearDSq = new double[48];
  private static int nearNUsed;
  private static NearestAircraft enemies = new NearestAircraft();

  public static NearestAircraft enemies() { return enemies; }

  public static void resetGameClear() {
    for (int i = 0; i < 48; i++)
      nearAct[i] = null;
  }

  public static void reset() {
    enemies.clear();
  }

  public void clear()
  {
    nearNUsed = 0;
  }

  public boolean add(Actor paramActor, double paramDouble)
  {
    if (!(paramActor instanceof Aircraft))
    {
      return true;
    }

    for (int i = 0; (i < nearNUsed) && 
      (paramDouble >= nearDSq[i]); i++);
    if (i >= nearNUsed) {
      if (nearNUsed < 48) {
        nearAct[nearNUsed] = ((Aircraft)paramActor);
        nearDSq[nearNUsed] = paramDouble;
        nearNUsed += 1;
      }
    }
    else
    {
      int j;
      if (nearNUsed < 48) {
        j = nearNUsed - 1;
        nearNUsed += 1;
      }
      else {
        j = nearNUsed - 2;
      }
      for (; j >= i; j--) {
        nearAct[(j + 1)] = nearAct[j];
        nearDSq[(j + 1)] = nearDSq[j];
      }
      nearAct[i] = ((Aircraft)paramActor);
      nearDSq[i] = paramDouble;
    }
    return true;
  }

  public static Aircraft getAFoundAircraft()
  {
    if (nearNUsed <= 0) {
      return null;
    }
    return nearAct[com.maddox.il2.ai.World.Rnd().nextInt(nearNUsed)];
  }

  public static Aircraft getAircraft(int paramInt) {
    if (nearNUsed <= 0) {
      return null;
    }
    return nearAct[paramInt];
  }

  public static int lenght() {
    return nearNUsed;
  }
}