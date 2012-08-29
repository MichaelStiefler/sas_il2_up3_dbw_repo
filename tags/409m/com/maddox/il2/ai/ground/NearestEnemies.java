// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NearestEnemies.java

package com.maddox.il2.ai.ground;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Accumulator;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.Paratrooper;
import java.util.List;

// Referenced classes of package com.maddox.il2.ai.ground:
//            Prey

public class NearestEnemies
    implements com.maddox.il2.engine.Accumulator
{

    public NearestEnemies()
    {
    }

    public static com.maddox.il2.ai.ground.NearestEnemies enemies()
    {
        return enemies;
    }

    public static void set(int i)
    {
        com.maddox.il2.ai.ground.NearestEnemies.set(i, 0.0F, 0.0F);
        useSpeed = false;
    }

    public static void set(int i, float f, float f1)
    {
        enemies.clear();
        usedWeaponsMask = i;
        minSpeed = f;
        maxSpeed = f1;
        useSpeed = true;
    }

    public static void resetGameClear()
    {
        for(int i = 0; i < nearAct.length; i++)
            nearAct[i] = null;

    }

    public void clear()
    {
        nearNUsed = 0;
    }

    public boolean add(com.maddox.il2.engine.Actor actor, double d)
    {
        if(!(actor instanceof com.maddox.il2.ai.ground.Prey) || (((com.maddox.il2.ai.ground.Prey)actor).HitbyMask() & usedWeaponsMask) == 0)
            return true;
        if(useSpeed)
        {
            float f = (float)actor.getSpeed(null);
            if(f < minSpeed || f > maxSpeed)
                return true;
        }
        int i;
        for(i = nearNUsed - 1; i >= 0; i--)
            if(d >= nearDSq[i])
                break;

        if(++i >= nearNUsed)
        {
            if(nearNUsed < 3)
            {
                nearAct[nearNUsed] = actor;
                nearDSq[nearNUsed] = d;
                nearNUsed++;
            }
        } else
        {
            int j;
            if(nearNUsed < 3)
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

            nearAct[i] = actor;
            nearDSq[i] = d;
        }
        return true;
    }

    public static com.maddox.il2.engine.Actor getAFoundEnemy()
    {
        if(nearNUsed <= 0)
            return null;
        else
            return nearAct[nearNUsed != 1 ? com.maddox.il2.ai.World.Rnd().nextInt(nearNUsed) : 0];
    }

    public static com.maddox.il2.engine.Actor getAFoundEnemy(com.maddox.JGP.Point3d point3d, double d, int i)
    {
        double d1 = d * d;
        java.util.List list = com.maddox.il2.engine.Engine.targets();
        int j = list.size();
        boolean flag = false;
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(k);
            if((((com.maddox.il2.ai.ground.Prey)actor).HitbyMask() & usedWeaponsMask) == 0)
                continue;
            int i1 = actor.getArmy();
            if(i1 == 0 || i1 == i)
                continue;
            com.maddox.JGP.Point3d point3d1 = actor.pos.getAbsPoint();
            double d2 = (point3d.x - point3d1.x) * (point3d.x - point3d1.x) + (point3d.y - point3d1.y) * (point3d.y - point3d1.y);
            if(d2 > d1)
                continue;
            if(useSpeed)
            {
                float f = (float)actor.getSpeed(null);
                if(f < minSpeed || f > maxSpeed)
                    continue;
            }
            if(actor instanceof com.maddox.il2.objects.air.Paratrooper)
            {
                d2 += 1.0D + d1;
                flag = true;
            }
            int j1;
            for(j1 = nearNUsed - 1; j1 >= 0; j1--)
                if(d2 >= nearDSq[j1])
                    break;

            if(++j1 >= nearNUsed)
            {
                if(nearNUsed < 3)
                {
                    nearAct[nearNUsed] = actor;
                    nearDSq[nearNUsed] = d2;
                    nearNUsed++;
                }
            } else
            {
                int k1;
                if(nearNUsed < 3)
                {
                    k1 = nearNUsed - 1;
                    nearNUsed++;
                } else
                {
                    k1 = nearNUsed - 2;
                }
                for(; k1 >= j1; k1--)
                {
                    nearAct[k1 + 1] = nearAct[k1];
                    nearDSq[k1 + 1] = nearDSq[k1];
                }

                nearAct[j1] = actor;
                nearDSq[j1] = d2;
            }
        }

        if(nearNUsed <= 0)
            return null;
        if(!flag || (nearAct[0] instanceof com.maddox.il2.objects.air.Paratrooper))
            return nearAct[nearNUsed != 1 ? com.maddox.il2.ai.World.Rnd().nextInt(nearNUsed) : 0];
        int l;
        for(l = 1; l < nearNUsed; l++)
            if(nearAct[l] instanceof com.maddox.il2.objects.air.Paratrooper)
                break;

        return nearAct[l != 1 ? com.maddox.il2.ai.World.Rnd().nextInt(l) : 0];
    }

    public static com.maddox.il2.engine.Actor getAFoundFlyingPlane(com.maddox.JGP.Point3d point3d, double d, int i, float f)
    {
        double d1 = d * d;
        java.util.List list = com.maddox.il2.engine.Engine.targets();
        int j = list.size();
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(k);
            if(actor instanceof com.maddox.il2.objects.air.Aircraft)
            {
                int l = actor.getArmy();
                if(l != 0 && l != i && (((com.maddox.il2.ai.ground.Prey)actor).HitbyMask() & usedWeaponsMask) != 0)
                {
                    com.maddox.JGP.Point3d point3d1 = actor.pos.getAbsPoint();
                    double d2 = (point3d.x - point3d1.x) * (point3d.x - point3d1.x) + (point3d.y - point3d1.y) * (point3d.y - point3d1.y) + (point3d.z - point3d1.z) * (point3d.z - point3d1.z);
                    if(d2 <= d1 && actor.getSpeed(null) >= 10D && point3d1.z - com.maddox.il2.ai.World.land().HQ(point3d1.x, point3d1.y) >= (double)f)
                    {
                        int i1;
                        for(i1 = nearNUsed - 1; i1 >= 0; i1--)
                            if(d2 >= nearDSq[i1])
                                break;

                        if(++i1 >= nearNUsed)
                        {
                            if(nearNUsed < 3)
                            {
                                nearAct[nearNUsed] = actor;
                                nearDSq[nearNUsed] = d2;
                                nearNUsed++;
                            }
                        } else
                        {
                            int j1;
                            if(nearNUsed < 3)
                            {
                                j1 = nearNUsed - 1;
                                nearNUsed++;
                            } else
                            {
                                j1 = nearNUsed - 2;
                            }
                            for(; j1 >= i1; j1--)
                            {
                                nearAct[j1 + 1] = nearAct[j1];
                                nearDSq[j1 + 1] = nearDSq[j1];
                            }

                            nearAct[i1] = actor;
                            nearDSq[i1] = d2;
                        }
                    }
                }
            }
        }

        if(nearNUsed <= 0)
            return null;
        else
            return nearAct[nearNUsed != 1 ? com.maddox.il2.ai.World.Rnd().nextInt(nearNUsed) : 0];
    }

    private static final int MAX_OBJECTS = 3;
    private static com.maddox.il2.engine.Actor nearAct[] = new com.maddox.il2.engine.Actor[3];
    private static double nearDSq[] = new double[3];
    private static int nearNUsed;
    private static com.maddox.il2.ai.ground.NearestEnemies enemies = new NearestEnemies();
    private static int usedWeaponsMask;
    private static boolean useSpeed = false;
    private static float minSpeed;
    private static float maxSpeed;

}
