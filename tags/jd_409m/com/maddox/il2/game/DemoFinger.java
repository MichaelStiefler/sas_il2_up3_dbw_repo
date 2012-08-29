package com.maddox.il2.game;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.InterpolateAdapter;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.humans.Soldier;
import com.maddox.il2.objects.ships.ShipGeneric;
import com.maddox.il2.objects.trains.Wagon;
import com.maddox.il2.objects.vehicles.cars.CarGeneric;
import com.maddox.il2.objects.vehicles.tanks.TankGeneric;
import com.maddox.rts.KeyRecordFinger;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.util.List;

public class DemoFinger
  implements KeyRecordFinger
{
  public static int AirCRC;
  public static int TankCRC;
  public static int CarCRC;
  public static int WagonCRC;
  public static int ShipCRC;
  public static int HumanCRC;
  public static int OtherCRC;
  public static int rnd;
  private static long rndtime = -1L;
  private static int[] _fingers = new int[8];

  public static void calc() {
    AirCRC = DemoFinger.TankCRC = DemoFinger.CarCRC = DemoFinger.WagonCRC = DemoFinger.ShipCRC = DemoFinger.HumanCRC = DemoFinger.OtherCRC = 0;
    List localList = Engine.interpolateAdapter().listeners();
    int i = localList.size();
    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)localList.get(j);
      if ((localActor instanceof Aircraft)) {
        AirCRC = localActor.getCRC(AirCRC);
      } else if ((localActor instanceof TankGeneric)) {
        TankCRC = localActor.getCRC(TankCRC);
      } else if ((localActor instanceof CarGeneric)) {
        CarCRC = localActor.getCRC(CarCRC);
      } else if ((localActor instanceof Wagon)) {
        WagonCRC = localActor.getCRC(WagonCRC);
      } else if ((localActor instanceof ShipGeneric)) {
        ShipCRC = localActor.getCRC(ShipCRC);
      } else if ((localActor instanceof Soldier)) {
        HumanCRC = localActor.getCRC(HumanCRC); } else {
        if (!localActor.isGameActor())
        {
          continue;
        }
        OtherCRC = localActor.getCRC(OtherCRC);
      }
    }
    if (Time.current() != rndtime) {
      rndtime = Time.current();
      rnd = World.Rnd().countAccess();
    }
  }

  public int checkPeriod()
  {
    return 1000;
  }

  public int countSaveFingers()
  {
    return 8;
  }

  public int[] calculateFingers()
  {
    calc();
    _fingers[0] = AirCRC;
    _fingers[1] = TankCRC;
    _fingers[2] = CarCRC;
    _fingers[3] = WagonCRC;
    _fingers[4] = ShipCRC;
    _fingers[5] = HumanCRC;
    _fingers[6] = OtherCRC;
    _fingers[7] = rnd;
    return _fingers;
  }

  public void checkFingers(int[] paramArrayOfInt)
  {
    calc();
    if ((paramArrayOfInt[0] != AirCRC) || (paramArrayOfInt[1] != TankCRC) || (paramArrayOfInt[2] != CarCRC) || (paramArrayOfInt[3] != WagonCRC) || (paramArrayOfInt[4] != ShipCRC) || (paramArrayOfInt[5] != HumanCRC) || (paramArrayOfInt[6] != OtherCRC) || (paramArrayOfInt[7] != rnd))
    {
      long l = Time.current();
      System.out.print("DEMO CRACH time = " + l);
      if (paramArrayOfInt[0] != AirCRC)
        System.out.print(" IN aircrafts");
      if (paramArrayOfInt[1] != TankCRC)
        System.out.print(" IN tanks");
      if (paramArrayOfInt[2] != CarCRC)
        System.out.print(" IN cars");
      if (paramArrayOfInt[3] != WagonCRC)
        System.out.print(" IN wagons");
      if (paramArrayOfInt[4] != ShipCRC)
        System.out.print(" IN ships");
      if (paramArrayOfInt[5] != HumanCRC)
        System.out.print(" IN humans");
      if (paramArrayOfInt[6] != OtherCRC)
        System.out.print(" IN other ");
      if (paramArrayOfInt[7] != rnd)
        System.out.print(" IN World.Rnd()");
      System.out.println(" ;((((((((((((((((((((((((");
    }
  }
}