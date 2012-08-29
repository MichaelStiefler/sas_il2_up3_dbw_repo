package com.maddox.il2.ai.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.ai.ground.TgtFlak;
import com.maddox.il2.ai.ground.TgtShip;
import com.maddox.il2.ai.ground.TgtTank;
import com.maddox.il2.ai.ground.TgtTrain;
import com.maddox.il2.ai.ground.TgtVehicle;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeBomber;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.air.TypeStormovik;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.bridges.LongBridge;
import java.util.ArrayList;
import java.util.List;

public class NearestTargets
{
  private static final int MAX_OBJECTS = 32;
  private static Actor[] nearAct = new Actor[32];
  private static double[] nearDSq = new double[32];

  public static Actor getEnemy(int paramInt1, int paramInt2, Point3d paramPoint3d, double paramDouble, int paramInt3)
  {
    Class localClass1 = null;
    Class localClass2 = null;

    switch (paramInt1) {
    case 0:
    default:
      localClass1 = Actor.class;
      localClass2 = Actor.class;
      break;
    case 1:
      localClass1 = TgtTank.class;
      localClass2 = TgtTank.class;
      break;
    case 2:
      localClass1 = TgtFlak.class;
      localClass2 = TgtFlak.class;
      break;
    case 3:
      localClass1 = TgtVehicle.class;
      localClass2 = TgtVehicle.class;
      break;
    case 4:
      localClass1 = TgtTrain.class;
      localClass2 = TgtTrain.class;
      break;
    case 5:
      return getBridge(paramInt2, paramPoint3d, paramDouble);
    case 6:
      localClass1 = TgtShip.class;
      localClass2 = TgtShip.class;
      break;
    case 7:
      localClass1 = TypeFighter.class;
      localClass2 = TypeFighter.class;
      break;
    case 8:
      localClass1 = TypeBomber.class;
      localClass2 = TypeStormovik.class;
      break;
    case 9:
      localClass1 = Aircraft.class;
      localClass2 = Aircraft.class;
    }

    List localList = Engine.targets();
    int i = localList.size();
    double d1 = paramDouble * paramDouble;
    int j = 0;
    for (int k = 0; k < i; k++) {
      localActor = (Actor)localList.get(k);

      m = localActor.getArmy();
      if ((m == 0) || (m == paramInt3)) {
        continue;
      }
      if ((!localClass1.isInstance(localActor)) && (!localClass2.isInstance(localActor))) {
        continue;
      }
      if ((paramInt1 == 0) && ((localActor instanceof BridgeSegment))) {
        continue;
      }
      if ((((Prey)localActor).HitbyMask() & paramInt2) == 0) {
        continue;
      }
      Point3d localPoint3d = localActor.pos.getAbsPoint();
      double d2 = (localPoint3d.jdField_x_of_type_Double - paramPoint3d.jdField_x_of_type_Double) * (localPoint3d.jdField_x_of_type_Double - paramPoint3d.jdField_x_of_type_Double) + (localPoint3d.jdField_y_of_type_Double - paramPoint3d.jdField_y_of_type_Double) * (localPoint3d.jdField_y_of_type_Double - paramPoint3d.jdField_y_of_type_Double) + (localPoint3d.jdField_z_of_type_Double - paramPoint3d.jdField_z_of_type_Double) * (localPoint3d.jdField_z_of_type_Double - paramPoint3d.jdField_z_of_type_Double);

      if (d2 > d1)
      {
        continue;
      }
      for (int n = 0; n < j; n++) {
        if (d2 < nearDSq[n])
          break;
      }
      if (n >= j) {
        if (j < 32) {
          nearAct[j] = localActor;
          nearDSq[j] = d2;
          j++;
        }
      }
      else
      {
        int i1;
        if (j < 32) {
          i1 = j - 1;
          j++;
        } else {
          i1 = j - 2;
        }
        for (; i1 >= n; i1--) {
          nearAct[(i1 + 1)] = nearAct[i1];
          nearDSq[(i1 + 1)] = nearDSq[i1];
        }
        nearAct[n] = localActor;
        nearDSq[n] = d2;
      }
    }

    if (j == 0) {
      if (paramInt1 == 0) return getBridge(paramInt2, paramPoint3d, paramDouble);
      return null;
    }
    Actor localActor = nearAct[World.Rnd().nextInt(j)];
    for (int m = 0; m < j; m++) nearAct[m] = null;
    return localActor;
  }

  public static Actor getBridge(int paramInt, Point3d paramPoint3d, double paramDouble)
  {
    if ((0x18 & paramInt) == 0)
      return null;
    ArrayList localArrayList = World.cur().statics.bridges;
    int i = localArrayList.size();
    double d1 = paramDouble * paramDouble;
    Object localObject = null;

    for (int j = 0; j < i; j++) {
      LongBridge localLongBridge = (LongBridge)localArrayList.get(j);
      if (localLongBridge.isAlive()) {
        Point3d localPoint3d = localLongBridge.pos.getAbsPoint();
        double d2 = (localPoint3d.jdField_x_of_type_Double - paramPoint3d.jdField_x_of_type_Double) * (localPoint3d.jdField_x_of_type_Double - paramPoint3d.jdField_x_of_type_Double) + (localPoint3d.jdField_y_of_type_Double - paramPoint3d.jdField_y_of_type_Double) * (localPoint3d.jdField_y_of_type_Double - paramPoint3d.jdField_y_of_type_Double) + (localPoint3d.jdField_z_of_type_Double - paramPoint3d.jdField_z_of_type_Double) * (localPoint3d.jdField_z_of_type_Double - paramPoint3d.jdField_z_of_type_Double);

        if (d2 <= d1) {
          localObject = localLongBridge;
          d1 = d2;
        }
      }
    }
    if (localObject == null) {
      return null;
    }
    i = localObject.NumStateBits() / 2;
    return BridgeSegment.getByIdx(localObject.bridgeIdx(), World.Rnd().nextInt(i));
  }
}