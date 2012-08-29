package com.maddox.il2.ai.ground;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Accumulator;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.Paratrooper;
import java.util.List;

public class NearestEnemies
  implements Accumulator
{
  private static final int MAX_OBJECTS = 3;
  private static Actor[] nearAct = new Actor[3];
  private static double[] nearDSq = new double[3];
  private static int nearNUsed;
  private static NearestEnemies enemies = new NearestEnemies();
  private static int usedWeaponsMask;
  private static boolean useSpeed = false;
  private static float minSpeed;
  private static float maxSpeed;

  public static NearestEnemies enemies()
  {
    return enemies;
  }

  public static void set(int paramInt) {
    set(paramInt, 0.0F, 0.0F);
    useSpeed = false;
  }
  public static void set(int paramInt, float paramFloat1, float paramFloat2) {
    enemies.clear();
    usedWeaponsMask = paramInt;
    minSpeed = paramFloat1;
    maxSpeed = paramFloat2;
    useSpeed = true;
  }

  public static void resetGameClear() {
    for (int i = 0; i < nearAct.length; i++)
      nearAct[i] = null;
  }

  public void clear()
  {
    nearNUsed = 0;
  }

  public boolean add(Actor paramActor, double paramDouble)
  {
    if ((!(paramActor instanceof Prey)) || ((((Prey)paramActor).HitbyMask() & usedWeaponsMask) == 0))
    {
      return true;
    }

    if (useSpeed) {
      float f = (float)paramActor.getSpeed(null);
      if ((f < minSpeed) || (f > maxSpeed)) {
        return true;
      }

    }

    for (int i = nearNUsed - 1; i >= 0; i--) {
      if (paramDouble >= nearDSq[i]) {
        break;
      }
    }
    i++;
    if (i >= nearNUsed) {
      if (nearNUsed < 3) {
        nearAct[nearNUsed] = paramActor;
        nearDSq[nearNUsed] = paramDouble;
        nearNUsed += 1;
      }
    }
    else
    {
      int j;
      if (nearNUsed < 3) {
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
      nearAct[i] = paramActor;
      nearDSq[i] = paramDouble;
    }
    return true;
  }

  public static Actor getAFoundEnemy()
  {
    if (nearNUsed <= 0) {
      return null;
    }
    return nearAct[World.Rnd().nextInt(nearNUsed)];
  }

  public static Actor getAFoundEnemy(Point3d paramPoint3d, double paramDouble, int paramInt) {
    double d1 = paramDouble * paramDouble;
    List localList = Engine.targets();
    int i = localList.size();

    int j = 0;

    for (int k = 0; k < i; k++) {
      Actor localActor = (Actor)localList.get(k);

      if ((((Prey)localActor).HitbyMask() & usedWeaponsMask) == 0)
      {
        continue;
      }

      int n = localActor.getArmy();
      if ((n == 0) || (n == paramInt))
      {
        continue;
      }
      Point3d localPoint3d = localActor.pos.getAbsPoint();
      double d2 = (paramPoint3d.jdField_x_of_type_Double - localPoint3d.jdField_x_of_type_Double) * (paramPoint3d.jdField_x_of_type_Double - localPoint3d.jdField_x_of_type_Double) + (paramPoint3d.jdField_y_of_type_Double - localPoint3d.jdField_y_of_type_Double) * (paramPoint3d.jdField_y_of_type_Double - localPoint3d.jdField_y_of_type_Double);

      if (d2 > d1)
      {
        continue;
      }
      if (useSpeed) {
        float f = (float)localActor.getSpeed(null);
        if ((f < minSpeed) || (f > maxSpeed))
        {
          continue;
        }
      }
      if ((localActor instanceof Paratrooper))
      {
        d2 += 1.0D + d1;
        j = 1;
      }

      for (int i1 = nearNUsed - 1; i1 >= 0; i1--) {
        if (d2 >= nearDSq[i1]) {
          break;
        }
      }
      i1++;
      if (i1 >= nearNUsed) {
        if (nearNUsed < 3) {
          nearAct[nearNUsed] = localActor;
          nearDSq[nearNUsed] = d2;
          nearNUsed += 1;
        }
      }
      else
      {
        int i2;
        if (nearNUsed < 3) {
          i2 = nearNUsed - 1;
          nearNUsed += 1;
        }
        else {
          i2 = nearNUsed - 2;
        }
        for (; i2 >= i1; i2--) {
          nearAct[(i2 + 1)] = nearAct[i2];
          nearDSq[(i2 + 1)] = nearDSq[i2];
        }
        nearAct[i1] = localActor;
        nearDSq[i1] = d2;
      }
    }

    if (nearNUsed <= 0) {
      return null;
    }

    if ((j == 0) || ((nearAct[0] instanceof Paratrooper))) {
      return nearAct[World.Rnd().nextInt(nearNUsed)];
    }

    int m = 1;
    while (m < nearNUsed) {
      if ((nearAct[m] instanceof Paratrooper)) {
        break;
      }
      m++;
    }

    return nearAct[World.Rnd().nextInt(m)];
  }

  public static Actor getAFoundFlyingPlane(Point3d paramPoint3d, double paramDouble, int paramInt, float paramFloat)
  {
    double d1 = paramDouble * paramDouble;
    List localList = Engine.targets();
    int i = localList.size();

    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)localList.get(j);

      if (!(localActor instanceof Aircraft))
      {
        continue;
      }
      int k = localActor.getArmy();
      if ((k == 0) || (k == paramInt))
      {
        continue;
      }
      if ((((Prey)localActor).HitbyMask() & usedWeaponsMask) == 0)
      {
        continue;
      }
      Point3d localPoint3d = localActor.pos.getAbsPoint();

      double d2 = (paramPoint3d.jdField_x_of_type_Double - localPoint3d.jdField_x_of_type_Double) * (paramPoint3d.jdField_x_of_type_Double - localPoint3d.jdField_x_of_type_Double) + (paramPoint3d.jdField_y_of_type_Double - localPoint3d.jdField_y_of_type_Double) * (paramPoint3d.jdField_y_of_type_Double - localPoint3d.jdField_y_of_type_Double) + (paramPoint3d.jdField_z_of_type_Double - localPoint3d.jdField_z_of_type_Double) * (paramPoint3d.jdField_z_of_type_Double - localPoint3d.jdField_z_of_type_Double);

      if (d2 > d1)
      {
        continue;
      }
      if (localActor.getSpeed(null) < 10.0D)
      {
        continue;
      }
      if (localPoint3d.jdField_z_of_type_Double - World.land().HQ(localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double) < paramFloat)
      {
        continue;
      }

      for (int m = nearNUsed - 1; m >= 0; m--) {
        if (d2 >= nearDSq[m]) {
          break;
        }
      }
      m++;
      if (m >= nearNUsed) {
        if (nearNUsed < 3) {
          nearAct[nearNUsed] = localActor;
          nearDSq[nearNUsed] = d2;
          nearNUsed += 1;
        }
      }
      else
      {
        int n;
        if (nearNUsed < 3) {
          n = nearNUsed - 1;
          nearNUsed += 1;
        }
        else {
          n = nearNUsed - 2;
        }
        for (; n >= m; n--) {
          nearAct[(n + 1)] = nearAct[n];
          nearDSq[(n + 1)] = nearDSq[n];
        }
        nearAct[m] = localActor;
        nearDSq[m] = d2;
      }
    }
    if (nearNUsed <= 0) {
      return null;
    }
    return nearAct[World.Rnd().nextInt(nearNUsed)];
  }
}