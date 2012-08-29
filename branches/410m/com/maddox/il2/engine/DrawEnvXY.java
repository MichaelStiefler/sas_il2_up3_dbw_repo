// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   DrawEnvXY.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapXY16;
import com.maddox.util.HashMapXY16Hash;
import com.maddox.util.HashMapXY16List;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Referenced classes of package com.maddox.il2.engine:
//            DrawEnv, Actor, VisibilityLong, LandPlate, 
//            Engine, EngineProfile, ActorDraw, Camera, 
//            Landscape, LandConf, ActorPos, ActorFilter

public class DrawEnvXY extends com.maddox.il2.engine.DrawEnv
{
    static class MaxDist
    {

        public float z;
        public double dist;

        MaxDist()
        {
        }
    }


    public void preRender(double d, double d1, double d2, float f, 
            int i, java.util.List list, java.util.List list1, java.util.List list2, boolean flag)
    {
        if(flag)
        {
            if(list != null)
                list.clear();
            if(list1 != null)
                list1.clear();
            if(list2 != null)
                list2.clear();
        }
        if((i & 4) != 0)
        {
            double d3 = d - (double)f;
            double d6 = d1 - (double)f;
            double d9 = d + (double)f;
            double d13 = d1 + (double)f;
            int l = (int)d3 / 200;
            int l1 = (int)d9 / 200;
            int l2 = (int)d6 / 200;
            int l3 = (int)d13 / 200;
            for(int l4 = l2; l4 <= l3; l4++)
            {
                for(int l6 = l; l6 <= l1; l6++)
                {
                    com.maddox.util.HashMapExt hashmapext = mapXY.get(l4, l6);
                    if(hashmapext == null)
                        continue;
                    for(java.util.Map.Entry entry = hashmapext.nextEntry(null); entry != null; entry = hashmapext.nextEntry(entry))
                    {
                        com.maddox.il2.engine.Engine.cur.profile.drawAll++;
                        com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getKey();
                        if(!com.maddox.il2.engine.Actor.isValid(actor) || (actor.flags & 1) == 0 || actor.draw == null)
                            continue;
                        int l10 = actor.draw.preRender(actor);
                        if(l10 > 0)
                            preRender(actor, l10, list, list1, list2);
                    }

                }

            }

        }
        if((i & 8) != 0)
        {
            double d4 = d - (double)f;
            double d7 = d1 - (double)f;
            double d10 = d + (double)f;
            double d14 = d1 + (double)f;
            int i1 = (int)d4 / 1600;
            int i2 = (int)d10 / 1600;
            int i3 = (int)d7 / 1600;
            int i4 = (int)d14 / 1600;
            for(int i5 = i3; i5 <= i4; i5++)
            {
                for(int i7 = i1; i7 <= i2; i7++)
                {
                    com.maddox.util.HashMapExt hashmapext1 = mapXYLong.get(i5, i7);
                    if(hashmapext1 == null)
                        continue;
                    for(java.util.Map.Entry entry1 = hashmapext1.nextEntry(null); entry1 != null; entry1 = hashmapext1.nextEntry(entry1))
                    {
                        com.maddox.il2.engine.Engine.cur.profile.drawAll++;
                        com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)entry1.getKey();
                        if(!com.maddox.il2.engine.Actor.isValid(actor1) || (actor1.flags & 1) == 0 || actor1.draw == null)
                            continue;
                        int i11 = actor1.draw.preRender(actor1);
                        if(i11 > 0)
                            preRender(actor1, i11, list, list1, list2);
                    }

                }

            }

        }
        double d5 = 0.0D;
        double d8 = 1.0D;
        if((i & 2) != 0 || (i & 1) != 0)
        {
            com.maddox.il2.engine.Camera.GetUniformDistParams(uniformCoof);
            if(uniformCoof[0] > 0.0F)
            {
                d8 = 1.0F / uniformCoof[0];
                d5 = uniformCoof[1];
            }
        }
        if((i & 2) != 0)
            if(com.maddox.il2.engine.Engine.land() != null && !com.maddox.il2.engine.Engine.land().config.bBig)
            {
                int j = com.maddox.il2.engine.Landscape.Compute2DBoundBoxOfVisibleLand(f, -50, 50, box);
                if(j == 0)
                    return;
                box[0] -= 80F;
                box[1] -= 80F;
                box[2] += 80F;
                box[3] += 80F;
                int j1 = (int)box[0] / 200;
                int j2 = (int)box[1] / 200;
                int j3 = (int)box[2] / 200;
                int j4 = (int)box[3] / 200;
                int j5 = ((j4 - j2) + 1) * ((j3 - j1) + 1);
                if(j5 <= 0)
                    return;
                int j7 = j5 + 31 >> 5;
                if(bitsVisibilityOfLandCells == null || bitsVisibilityOfLandCells.length < j7)
                    bitsVisibilityOfLandCells = new int[j7];
                j = com.maddox.il2.engine.Landscape.ComputeVisibilityOfLandCells(f, j1, j2, (j3 - j1) + 1, (j4 - j2) + 1, 200, 80, -50, 50, bitsVisibilityOfLandCells);
                if(j != 0)
                {
                    int k5 = 0;
                    int k7 = j2;
                    do
                    {
                        if(k7 > j4)
                            break;
                        for(int l8 = j1; l8 <= j3;)
                        {
label0:
                            {
                                if((bitsVisibilityOfLandCells[k5 >> 5] & 1 << (k5 & 0x1f)) == 0)
                                    break label0;
                                java.util.List list3 = lstXY.get(k7, l8);
                                if(list3 == null)
                                    break label0;
                                com.maddox.il2.engine.MaxDist maxdist = (com.maddox.il2.engine.MaxDist)distXY.get(k7, l8);
                                if(maxdist != null)
                                {
                                    double d21 = ((double)l8 + 0.5D) * 200D;
                                    double d23 = ((double)k7 + 0.5D) * 200D;
                                    double d27 = (d - d21) * (d - d21) + (d1 - d23) * (d1 - d23) + (d2 - (double)maxdist.z) * (d2 - (double)maxdist.z);
                                    double d32 = (maxdist.dist - d5) * d8 + 280D;
                                    if(d27 > d32 * d32)
                                        break label0;
                                }
                                int j11 = list3.size();
                                for(int j12 = 0; j12 < j11; j12++)
                                {
                                    com.maddox.il2.engine.Actor actor2 = (com.maddox.il2.engine.Actor)list3.get(j12);
                                    if(!com.maddox.il2.engine.Actor.isValid(actor2) || (actor2.flags & 1) == 0 || actor2.draw == null)
                                        continue;
                                    if(actor2.draw.uniformMaxDist > 0.0F)
                                    {
                                        actor2.pos.getRender(p);
                                        double d25 = (d - p.x) * (d - p.x) + (d1 - p.y) * (d1 - p.y) + (d2 - p.z) * (d2 - p.z);
                                        double d29 = ((double)actor2.draw.uniformMaxDist - d5) * d8;
                                        if(d25 > d29 * d29)
                                            continue;
                                    }
                                    com.maddox.il2.engine.Engine.cur.profile.drawAll++;
                                    int j13 = actor2.draw.preRender(actor2);
                                    if(j13 > 0)
                                        preRender(actor2, j13, list, list1, list2);
                                }

                            }
                            l8++;
                            k5++;
                        }

                        k7++;
                    } while(true);
                }
            } else
            {
                double d11 = d - (double)f;
                double d15 = d1 - (double)f;
                double d17 = d + (double)f;
                double d19 = d1 + (double)f;
                int l5 = (int)d11 / 200;
                int l7 = (int)d17 / 200;
                int i9 = (int)d15 / 200;
                int l9 = (int)d19 / 200;
                for(int j10 = i9; j10 <= l9; j10++)
                {
                    for(int k11 = l5; k11 <= l7; k11++)
                    {
                        java.util.List list5 = lstXY.get(j10, k11);
                        if(list5 == null)
                            continue;
                        int l12 = list5.size();
                        for(int k13 = 0; k13 < l12; k13++)
                        {
                            com.maddox.il2.engine.Actor actor4 = (com.maddox.il2.engine.Actor)list5.get(k13);
                            if(!com.maddox.il2.engine.Actor.isValid(actor4) || (actor4.flags & 1) == 0 || actor4.draw == null)
                                continue;
                            if(actor4.draw.uniformMaxDist > 0.0F)
                            {
                                actor4.pos.getRender(p);
                                double d30 = (d - p.x) * (d - p.x) + (d1 - p.y) * (d1 - p.y) + (d2 - p.z) * (d2 - p.z);
                                double d34 = ((double)actor4.draw.uniformMaxDist - d5) * d8;
                                if(d30 > d34 * d34)
                                    continue;
                            }
                            com.maddox.il2.engine.Engine.cur.profile.drawAll++;
                            int j14 = actor4.draw.preRender(actor4);
                            if(j14 > 0)
                                preRender(actor4, j14, list, list1, list2);
                        }

                    }

                }

            }
        if((i & 1) != 0)
            if(com.maddox.il2.engine.Engine.land() != null && !com.maddox.il2.engine.Engine.land().config.bBig)
            {
                int k = com.maddox.il2.engine.Landscape.Compute2DBoundBoxOfVisibleLand(f, -1, 1, box);
                if(k == 0)
                    return;
                box[0] -= 150F;
                box[1] -= 150F;
                box[2] += 150F;
                box[3] += 150F;
                int k1 = (int)box[0] / 1600;
                int k2 = (int)box[1] / 1600;
                int k3 = (int)box[2] / 1600;
                int k4 = (int)box[3] / 1600;
                int i6 = ((k4 - k2) + 1) * ((k3 - k1) + 1);
                if(i6 <= 0)
                    return;
                int i8 = i6 + 31 >> 5;
                if(bitsVisibilityOfLandCells == null || bitsVisibilityOfLandCells.length < i8)
                    bitsVisibilityOfLandCells = new int[i8];
                k = com.maddox.il2.engine.Landscape.ComputeVisibilityOfLandCells(f, k1, k2, (k3 - k1) + 1, (k4 - k2) + 1, 1600, 150, -1, 1, bitsVisibilityOfLandCells);
                if(k != 0)
                {
                    int j6 = 0;
                    int j8 = k2;
                    do
                    {
                        if(j8 > k4)
                            break;
                        for(int j9 = k1; j9 <= k3;)
                        {
label1:
                            {
                                if((bitsVisibilityOfLandCells[j6 >> 5] & 1 << (j6 & 0x1f)) == 0)
                                    break label1;
                                java.util.List list4 = lstXYPlate.get(j8, j9);
                                if(list4 == null)
                                    break label1;
                                com.maddox.il2.engine.MaxDist maxdist1 = (com.maddox.il2.engine.MaxDist)distXYPlate.get(j8, j9);
                                if(maxdist1 != null)
                                {
                                    double d22 = ((double)j9 + 0.5D) * 200D * 8D;
                                    double d24 = ((double)j8 + 0.5D) * 200D * 8D;
                                    double d28 = (d - d22) * (d - d22) + (d1 - d24) * (d1 - d24) + (d2 - (double)maxdist1.z) * (d2 - (double)maxdist1.z);
                                    double d33 = (maxdist1.dist - d5) * d8 + 2240D;
                                    if(d28 > d33 * d33)
                                        break label1;
                                }
                                int l11 = list4.size();
                                for(int k12 = 0; k12 < l11; k12++)
                                {
                                    com.maddox.il2.engine.Actor actor3 = (com.maddox.il2.engine.Actor)list4.get(k12);
                                    if(!com.maddox.il2.engine.Actor.isValid(actor3) || (actor3.flags & 1) == 0 || actor3.draw == null)
                                        continue;
                                    if(actor3.draw.uniformMaxDist > 0.0F)
                                    {
                                        actor3.pos.getRender(p);
                                        double d26 = (d - p.x) * (d - p.x) + (d1 - p.y) * (d1 - p.y) + (d2 - p.z) * (d2 - p.z);
                                        double d31 = ((double)actor3.draw.uniformMaxDist - d5) * d8;
                                        if(d26 > d31 * d31)
                                            continue;
                                    }
                                    com.maddox.il2.engine.Engine.cur.profile.drawAll++;
                                    int l13 = actor3.draw.preRender(actor3);
                                    if(l13 <= 0)
                                        continue;
                                    if((l13 & 2) != 0)
                                        list1.add(actor3);
                                    else
                                    if((l13 & 1) != 0)
                                        list.add(actor3);
                                    com.maddox.il2.engine.Engine.cur.profile.draw++;
                                }

                            }
                            j9++;
                            j6++;
                        }

                        j8++;
                    } while(true);
                }
            } else
            {
                double d12 = d - (double)f;
                double d16 = d1 - (double)f;
                double d18 = d + (double)f;
                double d20 = d1 + (double)f;
                int k6 = (int)d12 / 1600;
                int k8 = (int)d18 / 1600;
                int k9 = (int)d16 / 1600;
                int i10 = (int)d20 / 1600;
                for(int k10 = k9; k10 <= i10; k10++)
                {
                    for(int i12 = k6; i12 <= k8; i12++)
                    {
                        java.util.List list6 = lstXYPlate.get(k10, i12);
                        if(list6 == null)
                            continue;
                        int i13 = list6.size();
                        for(int i14 = 0; i14 < i13; i14++)
                        {
                            com.maddox.il2.engine.Engine.cur.profile.drawAll++;
                            com.maddox.il2.engine.Actor actor5 = (com.maddox.il2.engine.Actor)list6.get(i14);
                            if(!com.maddox.il2.engine.Actor.isValid(actor5) || (actor5.flags & 1) == 0 || actor5.draw == null)
                                continue;
                            int k14 = actor5.draw.preRender(actor5);
                            if(k14 <= 0)
                                continue;
                            if((k14 & 2) != 0)
                                list1.add(actor5);
                            else
                            if((k14 & 1) != 0)
                                list.add(actor5);
                            com.maddox.il2.engine.Engine.cur.profile.draw++;
                        }

                    }

                }

            }
    }

    public void getFiltered(java.util.AbstractCollection abstractcollection, double d, double d1, double d2, 
            double d3, int i, com.maddox.il2.engine.ActorFilter actorfilter)
    {
        if(d2 < d)
        {
            double d4 = d;
            d = d2;
            d2 = d4;
        }
        if(d3 < d1)
        {
            double d5 = d1;
            d1 = d3;
            d3 = d5;
        }
        double d6 = (d2 + d) / 2D;
        double d7 = (d3 + d1) / 2D;
        int j = (int)d / 200;
        int i1 = (int)d2 / 200;
        int l1 = (int)d1 / 200;
        int k2 = (int)d3 / 200;
        if((i & 4) != 0)
        {
            for(int j3 = l1; j3 <= k2; j3++)
            {
                for(int j4 = j; j4 <= i1; j4++)
                {
                    com.maddox.util.HashMapExt hashmapext = mapXY.get(j3, j4);
                    if(hashmapext == null)
                        continue;
                    for(java.util.Map.Entry entry = hashmapext.nextEntry(null); entry != null; entry = hashmapext.nextEntry(entry))
                    {
                        com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getKey();
                        if(!com.maddox.il2.engine.Actor.isValid(actor))
                            continue;
                        com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
                        double d8 = (point3d.x - d6) * (point3d.x - d6) + (point3d.y - d7) * (point3d.y - d7);
                        if(actorfilter.isUse(actor, d8) && abstractcollection != null)
                            abstractcollection.add(actor);
                    }

                }

            }

        }
        if((i & 2) != 0)
        {
            for(int k3 = l1; k3 <= k2; k3++)
            {
                for(int k4 = j; k4 <= i1; k4++)
                {
                    java.util.List list = lstXY.get(k3, k4);
                    if(list == null)
                        continue;
                    int j5 = list.size();
                    for(int l5 = 0; l5 < j5; l5++)
                    {
                        com.maddox.il2.engine.Actor actor2 = (com.maddox.il2.engine.Actor)list.get(l5);
                        if(!com.maddox.il2.engine.Actor.isValid(actor2))
                            continue;
                        com.maddox.JGP.Point3d point3d2 = actor2.pos.getAbsPoint();
                        double d10 = (point3d2.x - d6) * (point3d2.x - d6) + (point3d2.y - d7) * (point3d2.y - d7);
                        if(actorfilter.isUse(actor2, d10) && abstractcollection != null)
                            abstractcollection.add(actor2);
                    }

                }

            }

        }
        if((i & 8) != 0)
        {
            int k = (int)d / 1600;
            int j1 = (int)d2 / 1600;
            int i2 = (int)d1 / 1600;
            int l2 = (int)d3 / 1600;
            for(int l3 = i2; l3 <= l2; l3++)
            {
                for(int l4 = k; l4 <= j1; l4++)
                {
                    com.maddox.util.HashMapExt hashmapext1 = mapXYLong.get(l3, l4);
                    if(hashmapext1 == null)
                        continue;
                    for(java.util.Map.Entry entry1 = hashmapext1.nextEntry(null); entry1 != null; entry1 = hashmapext1.nextEntry(entry1))
                    {
                        com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)entry1.getKey();
                        if(!com.maddox.il2.engine.Actor.isValid(actor1))
                            continue;
                        com.maddox.JGP.Point3d point3d1 = actor1.pos.getAbsPoint();
                        double d9 = (point3d1.x - d6) * (point3d1.x - d6) + (point3d1.y - d7) * (point3d1.y - d7);
                        if(actorfilter.isUse(actor1, d9) && abstractcollection != null)
                            abstractcollection.add(actor1);
                    }

                }

            }

        }
        if((i & 1) != 0)
        {
            int l = (int)d / 1600;
            int k1 = (int)d2 / 1600;
            int j2 = (int)d1 / 1600;
            int i3 = (int)d3 / 1600;
            for(int i4 = j2; i4 <= i3; i4++)
            {
                for(int i5 = l; i5 <= k1; i5++)
                {
                    java.util.List list1 = lstXYPlate.get(i4, i5);
                    if(list1 == null)
                        continue;
                    int k5 = list1.size();
                    for(int i6 = 0; i6 < k5; i6++)
                    {
                        com.maddox.il2.engine.Actor actor3 = (com.maddox.il2.engine.Actor)list1.get(i6);
                        if(!com.maddox.il2.engine.Actor.isValid(actor3))
                            continue;
                        com.maddox.JGP.Point3d point3d3 = actor3.pos.getAbsPoint();
                        double d11 = (point3d3.x - d6) * (point3d3.x - d6) + (point3d3.y - d7) * (point3d3.y - d7);
                        if(actorfilter.isUse(actor3, d11) && abstractcollection != null)
                            abstractcollection.add(actor3);
                    }

                }

            }

        }
    }

    public void getFiltered(java.util.AbstractMap abstractmap, double d, double d1, double d2, 
            double d3, int i, com.maddox.il2.engine.ActorFilter actorfilter)
    {
        if(d2 < d)
        {
            double d4 = d;
            d = d2;
            d2 = d4;
        }
        if(d3 < d1)
        {
            double d5 = d1;
            d1 = d3;
            d3 = d5;
        }
        double d6 = (d2 + d) / 2D;
        double d7 = (d3 + d1) / 2D;
        int j = (int)d / 200;
        int i1 = (int)d2 / 200;
        int l1 = (int)d1 / 200;
        int k2 = (int)d3 / 200;
        if((i & 4) != 0)
        {
            for(int j3 = l1; j3 <= k2; j3++)
            {
                for(int j4 = j; j4 <= i1; j4++)
                {
                    com.maddox.util.HashMapExt hashmapext = mapXY.get(j3, j4);
                    if(hashmapext == null)
                        continue;
                    for(java.util.Map.Entry entry = hashmapext.nextEntry(null); entry != null; entry = hashmapext.nextEntry(entry))
                    {
                        com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getKey();
                        if(!com.maddox.il2.engine.Actor.isValid(actor))
                            continue;
                        com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
                        double d8 = (point3d.x - d6) * (point3d.x - d6) + (point3d.y - d7) * (point3d.y - d7);
                        if(actorfilter.isUse(actor, d8) && abstractmap != null)
                            abstractmap.put(actor, null);
                    }

                }

            }

        }
        if((i & 2) != 0)
        {
            for(int k3 = l1; k3 <= k2; k3++)
            {
                for(int k4 = j; k4 <= i1; k4++)
                {
                    java.util.List list = lstXY.get(k3, k4);
                    if(list == null)
                        continue;
                    int j5 = list.size();
                    for(int l5 = 0; l5 < j5; l5++)
                    {
                        com.maddox.il2.engine.Actor actor2 = (com.maddox.il2.engine.Actor)list.get(l5);
                        if(!com.maddox.il2.engine.Actor.isValid(actor2))
                            continue;
                        com.maddox.JGP.Point3d point3d2 = actor2.pos.getAbsPoint();
                        double d10 = (point3d2.x - d6) * (point3d2.x - d6) + (point3d2.y - d7) * (point3d2.y - d7);
                        if(actorfilter.isUse(actor2, d10) && abstractmap != null)
                            abstractmap.put(actor2, null);
                    }

                }

            }

        }
        if((i & 8) != 0)
        {
            int k = (int)d / 1600;
            int j1 = (int)d2 / 1600;
            int i2 = (int)d1 / 1600;
            int l2 = (int)d3 / 1600;
            for(int l3 = i2; l3 <= l2; l3++)
            {
                for(int l4 = k; l4 <= j1; l4++)
                {
                    com.maddox.util.HashMapExt hashmapext1 = mapXYLong.get(l3, l4);
                    if(hashmapext1 == null)
                        continue;
                    for(java.util.Map.Entry entry1 = hashmapext1.nextEntry(null); entry1 != null; entry1 = hashmapext1.nextEntry(entry1))
                    {
                        com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)entry1.getKey();
                        if(!com.maddox.il2.engine.Actor.isValid(actor1))
                            continue;
                        com.maddox.JGP.Point3d point3d1 = actor1.pos.getAbsPoint();
                        double d9 = (point3d1.x - d6) * (point3d1.x - d6) + (point3d1.y - d7) * (point3d1.y - d7);
                        if(actorfilter.isUse(actor1, d9) && abstractmap != null)
                            abstractmap.put(actor1, null);
                    }

                }

            }

        }
        if((i & 1) != 0)
        {
            int l = (int)d / 1600;
            int k1 = (int)d2 / 1600;
            int j2 = (int)d1 / 1600;
            int i3 = (int)d3 / 1600;
            for(int i4 = j2; i4 <= i3; i4++)
            {
                for(int i5 = l; i5 <= k1; i5++)
                {
                    java.util.List list1 = lstXYPlate.get(i4, i5);
                    if(list1 == null)
                        continue;
                    int k5 = list1.size();
                    for(int i6 = 0; i6 < k5; i6++)
                    {
                        com.maddox.il2.engine.Actor actor3 = (com.maddox.il2.engine.Actor)list1.get(i6);
                        if(!com.maddox.il2.engine.Actor.isValid(actor3))
                            continue;
                        com.maddox.JGP.Point3d point3d3 = actor3.pos.getAbsPoint();
                        double d11 = (point3d3.x - d6) * (point3d3.x - d6) + (point3d3.y - d7) * (point3d3.y - d7);
                        if(actorfilter.isUse(actor3, d11) && abstractmap != null)
                            abstractmap.put(actor3, null);
                    }

                }

            }

        }
    }

    public void staticTrimToSize()
    {
        lstXY.allValuesTrimToSize();
        lstXYPlate.allValuesTrimToSize();
    }

    public void setUniformMaxDist(int i, int j, float f)
    {
        com.maddox.il2.engine.MaxDist maxdist = new MaxDist();
        maxdist.dist = f;
        float f1 = com.maddox.il2.engine.Landscape.H(((float)i + 0.5F) * 200F, ((float)j + 0.5F) * 200F);
        maxdist.z = f1;
        distXY.put(j, i, maxdist);
        i /= 8;
        j /= 8;
        maxdist = (com.maddox.il2.engine.MaxDist)distXYPlate.get(j, i);
        if(maxdist != null)
        {
            if(maxdist.dist < (double)f)
                maxdist.dist = f;
        } else
        {
            com.maddox.il2.engine.MaxDist maxdist1 = new MaxDist();
            maxdist1.dist = f;
            maxdist1.z = f1;
            distXYPlate.put(j, i, maxdist1);
        }
    }

    protected void changedPos(com.maddox.il2.engine.Actor actor, com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        if((actor instanceof com.maddox.il2.engine.VisibilityLong) && ((com.maddox.il2.engine.VisibilityLong)actor).isVisibilityLong())
        {
            int i = (int)point3d.x / 1600;
            int k = (int)point3d.y / 1600;
            int i1 = (int)point3d1.x / 1600;
            int k1 = (int)point3d1.y / 1600;
            if(i != i1 || k != k1)
            {
                mapXYLong.remove(k, i, actor);
                mapXYLong.put(k1, i1, actor, null);
            }
        } else
        {
            int j = (int)point3d.x / 200;
            int l = (int)point3d.y / 200;
            int j1 = (int)point3d1.x / 200;
            int l1 = (int)point3d1.y / 200;
            if(j != j1 || l != l1)
            {
                mapXY.remove(l, j, actor);
                mapXY.put(l1, j1, actor, null);
            }
        }
    }

    protected void add(com.maddox.il2.engine.Actor actor)
    {
        if(actor.pos != null)
        {
            com.maddox.JGP.Point3d point3d = actor.pos.getCurrentPoint();
            if((actor instanceof com.maddox.il2.engine.VisibilityLong) && ((com.maddox.il2.engine.VisibilityLong)actor).isVisibilityLong())
            {
                int i = (int)point3d.x / 1600;
                int k = (int)point3d.y / 1600;
                mapXYLong.put(k, i, actor, null);
            } else
            {
                int j = (int)point3d.x / 200;
                int l = (int)point3d.y / 200;
                mapXY.put(l, j, actor, null);
            }
        }
    }

    protected void remove(com.maddox.il2.engine.Actor actor)
    {
        if(actor.pos != null)
        {
            com.maddox.JGP.Point3d point3d = actor.pos.getCurrentPoint();
            if((actor instanceof com.maddox.il2.engine.VisibilityLong) && ((com.maddox.il2.engine.VisibilityLong)actor).isVisibilityLong())
            {
                int i = (int)point3d.x / 1600;
                int k = (int)point3d.y / 1600;
                mapXYLong.remove(k, i, actor);
            } else
            {
                int j = (int)point3d.x / 200;
                int l = (int)point3d.y / 200;
                mapXY.remove(l, j, actor);
            }
        }
    }

    protected void changedPosStatic(com.maddox.il2.engine.Actor actor, com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        if(actor instanceof com.maddox.il2.engine.LandPlate)
        {
            int i = (int)point3d.x / 1600;
            int k = (int)point3d.y / 1600;
            int i1 = (int)point3d1.x / 1600;
            int k1 = (int)point3d1.y / 1600;
            if(i != i1 || k != k1)
            {
                lstXYPlate.remove(k, i, actor);
                lstXYPlate.put(k1, i1, actor);
            }
        } else
        {
            int j = (int)point3d.x / 200;
            int l = (int)point3d.y / 200;
            int j1 = (int)point3d1.x / 200;
            int l1 = (int)point3d1.y / 200;
            if(j != j1 || l != l1)
            {
                lstXY.remove(l, j, actor);
                lstXY.put(l1, j1, actor);
            }
        }
    }

    protected void addStatic(com.maddox.il2.engine.Actor actor)
    {
        if(actor.pos != null)
        {
            com.maddox.JGP.Point3d point3d = actor.pos.getCurrentPoint();
            if(actor instanceof com.maddox.il2.engine.LandPlate)
            {
                int i = (int)point3d.x / 1600;
                int k = (int)point3d.y / 1600;
                lstXYPlate.put(k, i, actor);
            } else
            {
                int j = (int)point3d.x / 200;
                int l = (int)point3d.y / 200;
                lstXY.put(l, j, actor);
            }
        }
    }

    protected void removeStatic(com.maddox.il2.engine.Actor actor)
    {
        if(actor.pos != null)
        {
            com.maddox.JGP.Point3d point3d = actor.pos.getCurrentPoint();
            if(actor instanceof com.maddox.il2.engine.LandPlate)
            {
                int i = (int)point3d.x / 1600;
                int k = (int)point3d.y / 1600;
                lstXYPlate.remove(k, i, actor);
            } else
            {
                int j = (int)point3d.x / 200;
                int l = (int)point3d.y / 200;
                lstXY.remove(l, j, actor);
            }
        }
    }

    protected void clear()
    {
        mapXY.clear();
        mapXYLong.clear();
        lstXY.clear();
        lstXYPlate.clear();
        distXY.clear();
        distXYPlate.clear();
    }

    public void resetGameClear()
    {
        java.util.ArrayList arraylist = new ArrayList();
        java.util.ArrayList arraylist1 = new ArrayList();
        mapXY.allValues(arraylist);
        for(int i = 0; i < arraylist.size(); i++)
        {
            com.maddox.util.HashMapExt hashmapext = (com.maddox.util.HashMapExt)arraylist.get(i);
            arraylist1.addAll(hashmapext.keySet());
            com.maddox.il2.engine.Engine.destroyListGameActors(arraylist1);
        }

        arraylist.clear();
        mapXYLong.allValues(arraylist);
        for(int j = 0; j < arraylist.size(); j++)
        {
            com.maddox.util.HashMapExt hashmapext1 = (com.maddox.util.HashMapExt)arraylist.get(j);
            arraylist1.addAll(hashmapext1.keySet());
            com.maddox.il2.engine.Engine.destroyListGameActors(arraylist1);
        }

        arraylist.clear();
        lstXY.allValues(arraylist);
        lstXYPlate.allValues(arraylist);
        for(int k = 0; k < arraylist.size(); k++)
        {
            java.util.ArrayList arraylist2 = (java.util.ArrayList)arraylist.get(k);
            arraylist1.addAll(arraylist2);
            com.maddox.il2.engine.Engine.destroyListGameActors(arraylist1);
        }

        arraylist.clear();
        distXY.clear();
        distXYPlate.clear();
    }

    public void resetGameCreate()
    {
        clear();
    }

    protected DrawEnvXY()
    {
        box = new float[4];
        uniformCoof = new float[2];
        mapXY = new HashMapXY16Hash(7);
        mapXYLong = new HashMapXY16Hash(7);
        lstXY = new HashMapXY16List(7);
        lstXYPlate = new HashMapXY16List(7);
        distXY = new HashMapXY16();
        distXYPlate = new HashMapXY16();
    }

    public static final int STEP = 200;
    public static final int MUL_PLATE = 8;
    public static final int STEPPLATE = 1600;
    public static final int MUL_LONG = 8;
    public static final int STEPLONG = 1600;
    private static int bitsVisibilityOfLandCells[] = null;
    private float box[];
    private static com.maddox.JGP.Point3d p = new Point3d();
    private float uniformCoof[];
    private com.maddox.util.HashMapXY16List lstXY;
    private com.maddox.util.HashMapXY16List lstXYPlate;
    private com.maddox.util.HashMapXY16Hash mapXY;
    private com.maddox.util.HashMapXY16Hash mapXYLong;
    private com.maddox.util.HashMapXY16 distXY;
    private com.maddox.util.HashMapXY16 distXYPlate;

}
