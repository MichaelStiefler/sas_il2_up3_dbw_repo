// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NearestAircraft.java

package com.maddox.il2.ai.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Accumulator;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.objects.air.Aircraft;

public class NearestAircraft
    implements com.maddox.il2.engine.Accumulator
{

    public NearestAircraft()
    {
    }

    public static com.maddox.il2.ai.air.NearestAircraft enemies()
    {
        return enemies;
    }

    public static void resetGameClear()
    {
        for(int i = 0; i < 48; i++)
            nearAct[i] = null;

    }

    public static void reset()
    {
        enemies.clear();
    }

    public void clear()
    {
        nearNUsed = 0;
    }

    public boolean add(com.maddox.il2.engine.Actor actor, double d)
    {
        if(!(actor instanceof com.maddox.il2.objects.air.Aircraft))
            return true;
        int i;
        for(i = 0; i < nearNUsed && d >= nearDSq[i]; i++);
        if(i >= nearNUsed)
        {
            if(nearNUsed < 48)
            {
                nearAct[nearNUsed] = (com.maddox.il2.objects.air.Aircraft)actor;
                nearDSq[nearNUsed] = d;
                nearNUsed++;
            }
        } else
        {
            int j;
            if(nearNUsed < 48)
            {
                j = nearNUsed - 1;
                nearNUsed++;
            } else
            {
                j = nearNUsed - 2;
            }
            for(; j >= i; j--)
            {
                nearAct[j + 1] = nearAct[j];
                nearDSq[j + 1] = nearDSq[j];
            }

            nearAct[i] = (com.maddox.il2.objects.air.Aircraft)actor;
            nearDSq[i] = d;
        }
        return true;
    }

    public static com.maddox.il2.objects.air.Aircraft getAFoundAircraft()
    {
        if(nearNUsed <= 0)
            return null;
        else
            return nearAct[nearNUsed != 1 ? com.maddox.il2.ai.World.Rnd().nextInt(nearNUsed) : 0];
    }

    public static com.maddox.il2.objects.air.Aircraft getAircraft(int i)
    {
        if(nearNUsed <= 0)
            return null;
        else
            return nearAct[i];
    }

    public static int lenght()
    {
        return nearNUsed;
    }

    private static final int MAX_OBJECTS = 48;
    private static com.maddox.il2.objects.air.Aircraft nearAct[] = new com.maddox.il2.objects.air.Aircraft[48];
    private static double nearDSq[] = new double[48];
    private static int nearNUsed;
    private static com.maddox.il2.ai.air.NearestAircraft enemies = new NearestAircraft();

}
