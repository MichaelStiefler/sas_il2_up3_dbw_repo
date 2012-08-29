// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NearestTargets.java

package com.maddox.il2.ai.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.bridges.LongBridge;
import java.util.ArrayList;
import java.util.List;

public class NearestTargets
{

    public NearestTargets()
    {
    }

    public static com.maddox.il2.engine.Actor getEnemy(int i, int j, com.maddox.JGP.Point3d point3d, double d, int k)
    {
        java.lang.Class class1 = null;
        java.lang.Class class2 = null;
        switch(i)
        {
        case 0: // '\0'
        default:
            class1 = com.maddox.il2.engine.Actor.class;
            class2 = com.maddox.il2.engine.Actor.class;
            break;

        case 1: // '\001'
            class1 = com.maddox.il2.ai.ground.TgtTank.class;
            class2 = com.maddox.il2.ai.ground.TgtTank.class;
            break;

        case 2: // '\002'
            class1 = com.maddox.il2.ai.ground.TgtFlak.class;
            class2 = com.maddox.il2.ai.ground.TgtFlak.class;
            break;

        case 3: // '\003'
            class1 = com.maddox.il2.ai.ground.TgtVehicle.class;
            class2 = com.maddox.il2.ai.ground.TgtVehicle.class;
            break;

        case 4: // '\004'
            class1 = com.maddox.il2.ai.ground.TgtTrain.class;
            class2 = com.maddox.il2.ai.ground.TgtTrain.class;
            break;

        case 5: // '\005'
            return com.maddox.il2.ai.air.NearestTargets.getBridge(j, point3d, d);

        case 6: // '\006'
            class1 = com.maddox.il2.ai.ground.TgtShip.class;
            class2 = com.maddox.il2.ai.ground.TgtShip.class;
            break;

        case 7: // '\007'
            class1 = com.maddox.il2.objects.air.TypeFighter.class;
            class2 = com.maddox.il2.objects.air.TypeFighter.class;
            break;

        case 8: // '\b'
            class1 = com.maddox.il2.objects.air.TypeBomber.class;
            class2 = com.maddox.il2.objects.air.TypeStormovik.class;
            break;

        case 9: // '\t'
            class1 = com.maddox.il2.objects.air.Aircraft.class;
            class2 = com.maddox.il2.objects.air.Aircraft.class;
            break;
        }
        java.util.List list = com.maddox.il2.engine.Engine.targets();
        int l = list.size();
        double d1 = d * d;
        int i1 = 0;
        for(int j1 = 0; j1 < l; j1++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(j1);
            int k1 = actor.getArmy();
            if(k1 != 0 && k1 != k && (class1.isInstance(actor) || class2.isInstance(actor)) && (i != 0 || !(actor instanceof com.maddox.il2.objects.bridges.BridgeSegment)) && (((com.maddox.il2.ai.ground.Prey)actor).HitbyMask() & j) != 0)
            {
                com.maddox.JGP.Point3d point3d1 = actor.pos.getAbsPoint();
                double d2 = (point3d1.x - point3d.x) * (point3d1.x - point3d.x) + (point3d1.y - point3d.y) * (point3d1.y - point3d.y) + (point3d1.z - point3d.z) * (point3d1.z - point3d.z);
                if(d2 <= d1)
                {
                    int i2;
                    for(i2 = 0; i2 < i1; i2++)
                        if(d2 < nearDSq[i2])
                            break;

                    if(i2 >= i1)
                    {
                        if(i1 < 32)
                        {
                            nearAct[i1] = actor;
                            nearDSq[i1] = d2;
                            i1++;
                        }
                    } else
                    {
                        int j2;
                        if(i1 < 32)
                        {
                            j2 = i1 - 1;
                            i1++;
                        } else
                        {
                            j2 = i1 - 2;
                        }
                        for(; j2 >= i2; j2--)
                        {
                            nearAct[j2 + 1] = nearAct[j2];
                            nearDSq[j2 + 1] = nearDSq[j2];
                        }

                        nearAct[i2] = actor;
                        nearDSq[i2] = d2;
                    }
                }
            }
        }

        if(i1 == 0)
            if(i == 0)
                return com.maddox.il2.ai.air.NearestTargets.getBridge(j, point3d, d);
            else
                return null;
        com.maddox.il2.engine.Actor actor1 = nearAct[i1 != 1 ? com.maddox.il2.ai.World.Rnd().nextInt(i1) : 0];
        for(int l1 = 0; l1 < i1; l1++)
            nearAct[l1] = null;

        return actor1;
    }

    public static com.maddox.il2.engine.Actor getBridge(int i, com.maddox.JGP.Point3d point3d, double d)
    {
        if((0x18 & i) == 0)
            return null;
        java.util.ArrayList arraylist = com.maddox.il2.ai.World.cur().statics.bridges;
        int j = arraylist.size();
        double d1 = d * d;
        com.maddox.il2.objects.bridges.LongBridge longbridge = null;
        for(int l = 0; l < j; l++)
        {
            com.maddox.il2.objects.bridges.LongBridge longbridge1 = (com.maddox.il2.objects.bridges.LongBridge)arraylist.get(l);
            if(longbridge1.isAlive())
            {
                com.maddox.JGP.Point3d point3d1 = longbridge1.pos.getAbsPoint();
                double d2 = (point3d1.x - point3d.x) * (point3d1.x - point3d.x) + (point3d1.y - point3d.y) * (point3d1.y - point3d.y) + (point3d1.z - point3d.z) * (point3d1.z - point3d.z);
                if(d2 <= d1)
                {
                    longbridge = longbridge1;
                    d1 = d2;
                }
            }
        }

        if(longbridge == null)
        {
            return null;
        } else
        {
            int k = longbridge.NumStateBits() / 2;
            return com.maddox.il2.objects.bridges.BridgeSegment.getByIdx(longbridge.bridgeIdx(), com.maddox.il2.ai.World.Rnd().nextInt(k));
        }
    }

    private static final int MAX_OBJECTS = 32;
    private static com.maddox.il2.engine.Actor nearAct[] = new com.maddox.il2.engine.Actor[32];
    private static double nearDSq[] = new double[32];

}
