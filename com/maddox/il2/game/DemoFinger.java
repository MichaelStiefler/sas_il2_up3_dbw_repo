// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   DemoFinger.java

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
    implements com.maddox.rts.KeyRecordFinger
{

    public DemoFinger()
    {
    }

    public static void calc()
    {
        AirCRC = TankCRC = CarCRC = WagonCRC = ShipCRC = HumanCRC = OtherCRC = 0;
        java.util.List list = com.maddox.il2.engine.Engine.interpolateAdapter().listeners();
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(j);
            if(actor instanceof com.maddox.il2.objects.air.Aircraft)
                AirCRC = actor.getCRC(AirCRC);
            else
            if(actor instanceof com.maddox.il2.objects.vehicles.tanks.TankGeneric)
                TankCRC = actor.getCRC(TankCRC);
            else
            if(actor instanceof com.maddox.il2.objects.vehicles.cars.CarGeneric)
                CarCRC = actor.getCRC(CarCRC);
            else
            if(actor instanceof com.maddox.il2.objects.trains.Wagon)
                WagonCRC = actor.getCRC(WagonCRC);
            else
            if(actor instanceof com.maddox.il2.objects.ships.ShipGeneric)
                ShipCRC = actor.getCRC(ShipCRC);
            else
            if(actor instanceof com.maddox.il2.objects.humans.Soldier)
                HumanCRC = actor.getCRC(HumanCRC);
            else
            if(actor.isGameActor())
                OtherCRC = actor.getCRC(OtherCRC);
        }

        if(com.maddox.rts.Time.current() != rndtime)
        {
            rndtime = com.maddox.rts.Time.current();
            rnd = com.maddox.il2.ai.World.Rnd().countAccess();
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
        com.maddox.il2.game.DemoFinger.calc();
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

    public void checkFingers(int ai[])
    {
        com.maddox.il2.game.DemoFinger.calc();
        if(ai[0] != AirCRC || ai[1] != TankCRC || ai[2] != CarCRC || ai[3] != WagonCRC || ai[4] != ShipCRC || ai[5] != HumanCRC || ai[6] != OtherCRC || ai[7] != rnd)
        {
            long l = com.maddox.rts.Time.current();
            java.lang.System.out.print("DEMO CRACH time = " + l);
            if(ai[0] != AirCRC)
                java.lang.System.out.print(" IN aircrafts");
            if(ai[1] != TankCRC)
                java.lang.System.out.print(" IN tanks");
            if(ai[2] != CarCRC)
                java.lang.System.out.print(" IN cars");
            if(ai[3] != WagonCRC)
                java.lang.System.out.print(" IN wagons");
            if(ai[4] != ShipCRC)
                java.lang.System.out.print(" IN ships");
            if(ai[5] != HumanCRC)
                java.lang.System.out.print(" IN humans");
            if(ai[6] != OtherCRC)
                java.lang.System.out.print(" IN other ");
            if(ai[7] != rnd)
                java.lang.System.out.print(" IN World.Rnd()");
            java.lang.System.out.println(" ;((((((((((((((((((((((((");
        }
    }

    public static int AirCRC;
    public static int TankCRC;
    public static int CarCRC;
    public static int WagonCRC;
    public static int ShipCRC;
    public static int HumanCRC;
    public static int OtherCRC;
    public static int rnd;
    private static long rndtime = -1L;
    private static int _fingers[] = new int[8];

}
